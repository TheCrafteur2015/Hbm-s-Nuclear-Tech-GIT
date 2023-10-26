package com.hbm.handler;

import com.hbm.entity.missile.EntitySiegeDropship;
import com.hbm.entity.mob.siege.EntitySiegeCraft;
import com.hbm.entity.mob.siege.EntitySiegeSkeleton;
import com.hbm.entity.mob.siege.EntitySiegeTunneler;
import com.hbm.entity.mob.siege.EntitySiegeUFO;
import com.hbm.entity.mob.siege.EntitySiegeZombie;
import com.hbm.entity.mob.siege.SiegeTier;
import com.hbm.util.ChatBuilder;
import com.hbm.util.GameRuleHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class SiegeOrchestrator {

	public static boolean lastWave = false;
	
	public static int level = 0;
	public static int levelCounter = 0;
	
	public static int siegeMobCount = 0;
	
	public static void update(World world) {
		
		//abort loop if sieges are disabled
		if(world.isRemote || !SiegeOrchestrator.siegeEnabled(world))
			return;

		int waveTime = SiegeOrchestrator.getWaveDuration(world);
		int pauseTime = SiegeOrchestrator.getPauseDuration(world);
		int interval = waveTime + pauseTime;
		//whether we're in a wave or pause, pauses apply first in an interval
		boolean wave = (int)(world.getTotalWorldTime() % interval) >= pauseTime;
		
		//send a server-wide message when the wave starts and ends
		if(!SiegeOrchestrator.lastWave && wave) {
			MinecraftServer.getServer().getConfigurationManager().sendChatMsg(ChatBuilder.start("[SIEGE MODE] A new wave is starting!").color(EnumChatFormatting.RED).flush());
		} else if(SiegeOrchestrator.lastWave && !wave) {
			MinecraftServer.getServer().getConfigurationManager().sendChatMsg(ChatBuilder.start("[SIEGE MODE] The wave has ended!").color(EnumChatFormatting.RED).flush());
		}
		
		SiegeOrchestrator.lastWave = wave;
		
		//if we're on pause, do nothing
		if(!wave)
			return;
		
		int spawnDelay = SiegeOrchestrator.getSpawnDelay(world);
		boolean threshold = SiegeOrchestrator.spawnThresholdEnabled(world);
		int thresholdSize = SiegeOrchestrator.getSpawnThreshold(world);
		
		//if threshold is enabled, don't go into the spawn loop if the entity count exceeds the threshold
		if(!(threshold && SiegeOrchestrator.siegeMobCount > thresholdSize)) {
			for(Object o : world.playerEntities) {
				EntityPlayer player = (EntityPlayer) o;
				
				if((world.getTotalWorldTime() + player.getEntityId()) % spawnDelay == 0) {
					SiegeOrchestrator.perPlayerSpawn(player);
				}
			}
		}
		
		int countCap = SiegeOrchestrator.getTierDelay(world);
		int prevLevel = SiegeOrchestrator.level;
		SiegeOrchestrator.levelCounter++;
		
		//if the counter has reached the cap, tick up the tier and reset the counter
		while(SiegeOrchestrator.levelCounter >= countCap) {
			SiegeOrchestrator.levelCounter -= countCap;
			SiegeOrchestrator.level++;
		}
		
		//if the counter is below 0, bring up the counter and deduct a tier
		while(SiegeOrchestrator.levelCounter < 0) {
			SiegeOrchestrator.levelCounter += countCap;
			SiegeOrchestrator.level--;
		}
		
		//if the tier has changed, send a broadcast
		if(prevLevel != SiegeOrchestrator.level) {
			MinecraftServer.getServer().getConfigurationManager().sendChatMsg(ChatBuilder.start("[SIEGE MODE] The siege tier is now " + (SiegeOrchestrator.level + 1) + "!").color(EnumChatFormatting.RED).flush());
		}
		
		//every 10s we recount the loaded siege mobs
		if(world.getTotalWorldTime() % 200 == 0) {
			SiegeOrchestrator.refreshMobCount(world);
		}
	}
	
	public static void perPlayerSpawn(EntityPlayer player) {
		
		Vec3 vec = Vec3.createVectorHelper(SiegeOrchestrator.getSpawnDist(player.worldObj), 0, 0);
		vec.rotateAroundY((float)(player.getRNG().nextFloat() * Math.PI));
		
		double x = player.posX + vec.xCoord;
		double z = player.posZ + vec.zCoord;
		
		if(SiegeOrchestrator.enableMissileSpawn(player.worldObj)) {
			EntitySiegeDropship ship = new EntitySiegeDropship(player.worldObj, x, 300, z);
			player.worldObj.spawnEntityInWorld(ship);
		}
	}
	
	public static void playerDeathHook(EntityPlayer player, DamageSource source) {
		
		if(!player.worldObj.isRemote) {
			if(SiegeOrchestrator.isSiegeMob(source.getEntity())) {
				SiegeOrchestrator.levelCounter -= SiegeOrchestrator.getTierSubDeath(player.worldObj);
			}
		}
	}
	
	public static void mobDeathHook(EntityLivingBase entity, DamageSource source) {
		
		if(!entity.worldObj.isRemote) {
			if(SiegeOrchestrator.isSiegeMob(entity)) {
				SiegeOrchestrator.levelCounter += SiegeOrchestrator.getTierAddKill(entity.worldObj);
			}
		}
	}
	
	public static void spawnRandomMob(World world, double x, double y, double z, EntityPlayer target) {
		
		if(world.isRemote)
			return;
		
		SiegeTier tier = SiegeTier.tiers[SiegeOrchestrator.level];
		if(tier == null)
			tier = SiegeTier.DNT;
		
		EntityLiving entity;
		
		float f = world.rand.nextFloat();
		
		if(target != null && f < 0.25F && target.posY + 15 < y) {
			entity = new EntitySiegeTunneler(world);
			((EntitySiegeTunneler)entity).setTier(tier);
		} else if(f < 0.1F) {
			entity = new EntitySiegeUFO(world);
			((EntitySiegeUFO)entity).setTier(tier);
		} else if(f < 0.4F) {
			entity = new EntitySiegeSkeleton(world);
			((EntitySiegeSkeleton)entity).setTier(tier);
		} else {
			entity = new EntitySiegeZombie(world);
			((EntitySiegeZombie)entity).setTier(tier);
		}
		
		entity.setPositionAndRotation(x, y, z, (float)Math.PI * 2F, 0F);
		
		if(target != null) {
			entity.setAttackTarget(target);
		}
		
		world.spawnEntityInWorld(entity);
	}
	
	private static void refreshMobCount(World world) {
		
		SiegeOrchestrator.siegeMobCount = 0;
		
		for(Object o : world.loadedEntityList) {
			Entity entity = (Entity) o;
			
			if(SiegeOrchestrator.isSiegeMob(entity)) {
				SiegeOrchestrator.siegeMobCount++;
			}
		}
	}
	
	public static boolean isSiegeMob(Entity entity) {

		if((entity instanceof EntitySiegeZombie) || (entity instanceof EntitySiegeSkeleton) || (entity instanceof EntitySiegeUFO)
				|| (entity instanceof EntitySiegeTunneler)) return true;
		if(entity instanceof EntitySiegeCraft) return true;
		
		return false;
	}

	public static final String KEY_SAVE_RULES = "siegeSaveRules";
	public static final String KEY_ENABLE_SIEGES = "siegeEnable";
	public static final String KEY_WAVE_DURATION = "siegeWaveDuration";
	public static final String KEY_PAUSE_DURATION = "siegePauseDuration";
	public static final String KEY_ENABLE_DROPS = "siegeEnableDropships";
	public static final String KEY_ENABLE_SPAWNS = "siegeEnableMobSpawning";
	public static final String KEY_ENABLE_BASES = "siegeEnableBases";
	public static final String KEY_ENABLE_MISSILES = "siegeEnableMissiles";
	public static final String KEY_SPAWN_DIST = "siegeSpawnDist";
	public static final String KEY_SPAWN_DELAY = "siegeSpawnDelay";
	public static final String KEY_TIER_DELAY = "siegeTierDuration";
	public static final String KEY_TIER_ADD_KILL = "siegeTierAddKill";
	public static final String KEY_TIER_ADD_DROP = "siegeTierAddDrop";
	public static final String KEY_TIER_SUB_DEATH = "siegeTierSubDeath";
	public static final String KEY_SPAWN_THRESHOLD = "siegeEnableSpawnThreshold";
	public static final String KEY_SPAWN_THRESHOLD_COUNT = "siegeSpawnThreshold";
	public static final String KEY_EXPANSION_THRESHOLD_COUNT = "siegeExpansionThreshold";
	
	public static void createGameRules(World world) {

		GameRules rules = world.getGameRules();
		
		if(!rules.getGameRuleBooleanValue(SiegeOrchestrator.KEY_SAVE_RULES)) {
			rules.setOrCreateGameRule(SiegeOrchestrator.KEY_SAVE_RULES, "true");
			rules.setOrCreateGameRule(SiegeOrchestrator.KEY_ENABLE_SIEGES, "false");
			rules.setOrCreateGameRule(SiegeOrchestrator.KEY_WAVE_DURATION, "" + (20 * 60 * 20));
			rules.setOrCreateGameRule(SiegeOrchestrator.KEY_PAUSE_DURATION, "" + (10 * 60 * 20));
			rules.setOrCreateGameRule(SiegeOrchestrator.KEY_ENABLE_DROPS, "true");
			rules.setOrCreateGameRule(SiegeOrchestrator.KEY_ENABLE_SPAWNS, "false");
			rules.setOrCreateGameRule(SiegeOrchestrator.KEY_ENABLE_BASES, "true");
			rules.setOrCreateGameRule(SiegeOrchestrator.KEY_ENABLE_MISSILES, "true");
			rules.setOrCreateGameRule(SiegeOrchestrator.KEY_SPAWN_DIST, "64");
			rules.setOrCreateGameRule(SiegeOrchestrator.KEY_SPAWN_DELAY, "" + (10 * 20));
			rules.setOrCreateGameRule(SiegeOrchestrator.KEY_TIER_DELAY, "" + (15 * 60 * 20));
			rules.setOrCreateGameRule(SiegeOrchestrator.KEY_TIER_ADD_KILL, "" + (1 * 20));
			rules.setOrCreateGameRule(SiegeOrchestrator.KEY_TIER_SUB_DEATH, "" + (15 * 20));
			rules.setOrCreateGameRule(SiegeOrchestrator.KEY_SPAWN_THRESHOLD, "true");
			rules.setOrCreateGameRule(SiegeOrchestrator.KEY_SPAWN_THRESHOLD_COUNT, "50");
			rules.setOrCreateGameRule(SiegeOrchestrator.KEY_EXPANSION_THRESHOLD_COUNT, "20");
		}
	}
	
	public static boolean siegeEnabled(World world) {
		return world.getGameRules().getGameRuleBooleanValue(SiegeOrchestrator.KEY_ENABLE_SIEGES);
	}
	
	public static int getWaveDuration(World world) {
		return GameRuleHelper.getIntegerMinimum(world, SiegeOrchestrator.KEY_WAVE_DURATION, 20 * 60 * 10, 1);
	}
	
	public static int getPauseDuration(World world) {
		return GameRuleHelper.getIntegerMinimum(world, SiegeOrchestrator.KEY_PAUSE_DURATION, 10 * 60 * 10, 0);
	}
	
	public static double getSpawnDist(World world) {
		return GameRuleHelper.getDoubleMinimum(world, SiegeOrchestrator.KEY_SPAWN_DIST, 64, 0);
	}
	
	public static int getSpawnDelay(World world) {
		return GameRuleHelper.getIntegerMinimum(world, SiegeOrchestrator.KEY_SPAWN_DELAY, 10 * 20, 1);
	}
	
	public static int getTierDelay(World world) {
		return GameRuleHelper.getIntegerMinimum(world, SiegeOrchestrator.KEY_TIER_DELAY, 15 * 60 * 20, 1);
	}
	
	public static int getTierAddKill(World world) {
		return GameRuleHelper.getIntegerMinimum(world, SiegeOrchestrator.KEY_TIER_ADD_KILL, 1 * 20, 0);
	}
	
	public static int getTierAddDrop(World world) {
		return GameRuleHelper.getIntegerMinimum(world, SiegeOrchestrator.KEY_TIER_ADD_DROP, 5 * 20, 0);
	}
	
	public static int getTierSubDeath(World world) {
		return GameRuleHelper.getIntegerMinimum(world, SiegeOrchestrator.KEY_TIER_SUB_DEATH, 15 * 20, 0);
	}
	
	public static boolean spawnThresholdEnabled(World world) {
		return world.getGameRules().getGameRuleBooleanValue(SiegeOrchestrator.KEY_SPAWN_THRESHOLD);
	}
	
	public static int getSpawnThreshold(World world) {
		return GameRuleHelper.getIntegerMinimum(world, SiegeOrchestrator.KEY_SPAWN_THRESHOLD_COUNT, 50, 1);
	}
	
	public static int getExpansionThreshold(World world) {
		return GameRuleHelper.getIntegerMinimum(world, SiegeOrchestrator.KEY_EXPANSION_THRESHOLD_COUNT, 20, 1);
	}
	
	public static boolean enableBaseSpawning(World world) {
		return world.getGameRules().getGameRuleBooleanValue(SiegeOrchestrator.KEY_ENABLE_BASES);
	}
	
	public static boolean enableMobSpawning(World world) {
		return world.getGameRules().getGameRuleBooleanValue(SiegeOrchestrator.KEY_ENABLE_SPAWNS);
	}
	
	public static boolean enableMissileSpawn(World world) {
		return world.getGameRules().getGameRuleBooleanValue(SiegeOrchestrator.KEY_ENABLE_MISSILES);
	}
}
