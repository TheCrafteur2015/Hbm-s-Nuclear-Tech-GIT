package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.projectile.EntityBulletBaseNT;
import com.hbm.entity.projectile.EntityBulletBaseNT.IBulletImpactBehaviorNT;
import com.hbm.entity.projectile.EntityBulletBaseNT.IBulletUpdateBehaviorNT;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ItemAmmoEnums.AmmoCoilgun;
import com.hbm.items.ItemAmmoEnums.AmmoFireExt;
import com.hbm.items.ItemAmmoEnums.AmmoFlamethrower;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.ExplosionKnockbackPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;
import com.hbm.tileentity.IRepairable;
import com.hbm.tileentity.IRepairable.EnumExtinguishType;
import com.hbm.util.CompatExternal;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

public class GunEnergyFactory {
	
	public static GunConfiguration getChemConfig() {
		
		GunConfiguration config = new GunConfiguration();
		config.rateOfFire = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.allowsInfinity = false;
		config.ammoCap = 3_000;
		config.durability = 90_000;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.crosshair = Crosshair.CIRCLE;
		
		config.name = "Chemical Thrower";
		config.manufacturer = EnumGunManufacturer.LANGFORD;
		
		config.config = new ArrayList<>();
		
		return config;
	}
	
	public static GunConfiguration getEMPConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 30;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 10;
		config.firingDuration = 0;
		config.ammoCap = 0;
		config.durability = 1500;
		config.reloadType = GunConfiguration.RELOAD_NONE;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_SPLIT;
		config.firingSound = "hbm:weapon.teslaShoot";
		
		config.name = "EMP Orb Projector";
		config.manufacturer = EnumGunManufacturer.MWT;
		
		config.config = new ArrayList<>();
		config.config.add(BulletConfigSyncingUtil.SPECIAL_EMP);
		
		return config;
	}
	
	public static GunConfiguration getFlamerConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 1;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 20;
		config.reloadSoundEnd = false;
		config.firingDuration = 0;
		config.ammoCap = 100;
		config.durability = 1000;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCLE;
		config.firingSound = "hbm:weapon.flamethrowerShoot";
		config.reloadSound = "hbm:weapon.flamerReload";
		
		config.name = "Heavy Duty Flamer";
		config.manufacturer = EnumGunManufacturer.MWT;

		config.comment.add("Dragon-slaying: Advanced techniques, part 1:");
		config.comment.add("Try not to get eaten by the dragon.");
		config.comment.add("");
		config.comment.add("Hope that helps.");
		
		config.config = new ArrayList<>();
		config.config.add(BulletConfigSyncingUtil.FLAMER_NORMAL);
		config.config.add(BulletConfigSyncingUtil.FLAMER_NAPALM);
		config.config.add(BulletConfigSyncingUtil.FLAMER_WP);
		config.config.add(BulletConfigSyncingUtil.FLAMER_VAPORIZER);
		config.config.add(BulletConfigSyncingUtil.FLAMER_GAS);
		
		return config;
	}
	
	public static GunConfiguration getZOMGConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 1;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 10;
		config.reloadSoundEnd = false;
		config.firingDuration = 0;
		config.durability = 100000;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.ammoCap = 1000;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_ARROWS;
		config.firingSound = "hbm:weapon.zomgShoot";
		config.reloadSound = "hbm:weapon.b92Reload";
		
		config.name = "EMC101 Prismatic Negative Energy Cannon";
		config.manufacturer = EnumGunManufacturer.MWT;

		config.comment.add("Taste the rainbow!");
		
		config.config = new ArrayList<>();
		config.config.add(BulletConfigSyncingUtil.ZOMG_BOLT);
		
		return config;
	}

	public static GunConfiguration getExtConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 1;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.reloadDuration = 20;
		config.reloadSoundEnd = false;
		config.firingDuration = 0;
		config.ammoCap = 300; //good for 15 seconds of continued spray
		config.durability = 10000;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCLE;
		config.firingSound = "hbm:weapon.extinguisher";
		config.reloadSound = "hbm:weapon.flamerReload";
		
		config.name = "PROTEX Fire Exinguisher 6kg";
		config.manufacturer = EnumGunManufacturer.GLORIA;
		
		config.config = new ArrayList<>();
		config.config.add(BulletConfigSyncingUtil.FEXT_NORMAL);
		config.config.add(BulletConfigSyncingUtil.FEXT_FOAM);
		config.config.add(BulletConfigSyncingUtil.FEXT_SAND);
		
		return config;
	}
	
	public static GunConfiguration getCoilgunConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 5;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 20;
		config.firingDuration = 0;
		config.ammoCap = 1;
		config.durability = 2_500;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.CIRCLE;
		config.firingSound = "hbm:weapon.coilgunShoot";
		config.reloadSoundEnd = false;
		config.reloadSound = "hbm:weapon.coilgunReload";
		
		config.name = "ArmsKore Coilgun";
		config.manufacturer = EnumGunManufacturer.DRG;
		
		config.config = new ArrayList<>();
		config.config.add(BulletConfigSyncingUtil.COIL_NORMAL);
		config.config.add(BulletConfigSyncingUtil.COIL_DU);
		config.config.add(BulletConfigSyncingUtil.COIL_RUBBER);
		
		return config;
	}

	public static GunConfiguration getCryoCannonConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 1;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_AUTO;
		config.firingDuration = 0;
		config.ammoCap = 1_000;
		config.durability = 10_000;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CIRCLE;
		
		config.name = "Cryo Cannon";
		config.manufacturer = EnumGunManufacturer.DRG;
		
		config.config = new ArrayList<>();
		config.config.add(BulletConfigSyncingUtil.CRYO_NORMAL);
		
		return config;
	}
	
	public static GunConfiguration getVortexConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		return config;
		
	}
	
	public static BulletConfiguration getOrbusConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.ammo = new ComparableStack(ModItems.gun_emp_ammo);
		
		bullet.velocity = 1F;
		bullet.spread = 0.0F;
		bullet.wear = 10;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.dmgMin = 10;
		bullet.dmgMax = 12;
		bullet.gravity = 0D;
		bullet.maxAge = 100;
		bullet.doesRicochet = false;
		bullet.doesPenetrate = true;
		bullet.doesBreakGlass = false;
		bullet.style = BulletConfiguration.STYLE_ORB;
		bullet.plink = BulletConfiguration.PLINK_NONE;
		bullet.emp = 10;
		
		bullet.damageType = ModDamageSource.s_emp;
		bullet.dmgProj = false;
		bullet.dmgBypass = true;
		
		bullet.effects = new ArrayList<>();
		bullet.effects.add(new PotionEffect(Potion.moveSlowdown.id, 10 * 20, 1));
		bullet.effects.add(new PotionEffect(Potion.weakness.id, 10 * 20, 4));
		
		return bullet;
	}
	
	public static BulletConfiguration getCoilConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_coilgun, 1, AmmoCoilgun.STOCK.ordinal());
		
		bullet.velocity = 7.5F;
		bullet.spread = 0.0F;
		bullet.wear = 10;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.dmgMin = 35;
		bullet.dmgMax = 45;
		bullet.gravity = 0D;
		bullet.maxAge = 50;
		bullet.doesPenetrate = true;
		bullet.isSpectral = true;

		bullet.style = BulletConfiguration.STYLE_BOLT;
		bullet.trail = BulletConfiguration.BOLT_NIGHTMARE;
		bullet.vPFX = "fireworks";
		
		bullet.bntUpdate = (entity) -> GunEnergyFactory.breakInPath(entity, 1.25F);
		
		return bullet;
	}
	
	public static BulletConfiguration getCoilDUConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_coilgun, 1, AmmoCoilgun.DU.ordinal());
		
		bullet.velocity = 7.5F;
		bullet.spread = 0.0F;
		bullet.wear = 25;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.dmgMin = 65;
		bullet.dmgMax = 80;
		bullet.gravity = 0D;
		bullet.maxAge = 50;
		bullet.doesPenetrate = true;
		bullet.isSpectral = true;

		bullet.style = BulletConfiguration.STYLE_BOLT;
		bullet.trail = BulletConfiguration.BOLT_NIGHTMARE;
		bullet.vPFX = "fireworks";
		
		bullet.bntUpdate = (entity) -> GunEnergyFactory.breakInPath(entity, 2.5F);
		
		return bullet;
	}
	
	public static BulletConfiguration getCoilRubberConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_coilgun, 1, AmmoCoilgun.RUBBER.ordinal());
		
		bullet.velocity = 5F;
		bullet.spread = 0.0F;
		bullet.wear = 10;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.dmgMin = 10;
		bullet.dmgMax = 20;
		bullet.gravity = 0D;
		bullet.maxAge = 50;
		bullet.doesPenetrate = false;
		bullet.isSpectral = false;
		bullet.doesRicochet = true;
		bullet.ricochetAngle = 30;
		bullet.HBRC = 90;
		bullet.LBRC = 100;
		bullet.bounceMod = 1;
		bullet.selfDamageDelay = 2;

		bullet.style = BulletConfiguration.STYLE_PELLET;
		
		bullet.bntHurt = (entity, hit) -> {
			Vec3 vec = Vec3.createVectorHelper(entity.motionX, entity.motionY, entity.motionZ);
			vec = vec.normalize();
			vec.xCoord *= 10;
			vec.yCoord *= 10;
			vec.zCoord *= 10;
			hit.motionX += vec.xCoord;
			hit.motionY += vec.yCoord;
			hit.motionZ += vec.zCoord;
			
			if(hit instanceof EntityPlayerMP) {
				PacketDispatcher.wrapper.sendTo(new ExplosionKnockbackPacket(vec), (EntityPlayerMP) hit);
			}
		};
		
		return bullet;
	}
	
	public static void breakInPath(EntityBulletBaseNT entity, float threshold) {
		
		if(entity.worldObj.isRemote) return;
		
		Vec3 vec = Vec3.createVectorHelper(entity.posX - entity.prevPosX, entity.posY - entity.prevPosY, entity.posZ - entity.prevPosZ);
		double motion = Math.max(vec.lengthVector(), 0.1);
		vec = vec.normalize();
		
		for(double d = 0; d < motion; d += 0.5) {

			int x = (int) Math.floor(entity.posX - vec.xCoord * d);
			int y = (int) Math.floor(entity.posY - vec.yCoord * d);
			int z = (int) Math.floor(entity.posZ - vec.zCoord * d);
			
			Block b = entity.worldObj.getBlock(x, y, z);
			float hardness = b.getBlockHardness(entity.worldObj, x, y, z);
			
			if(b.getMaterial() != Material.air && hardness >= 0 && hardness < threshold) {
				entity.worldObj.func_147480_a(x, y, z, false);
			}
		}
	}
	
	public static BulletConfiguration getFlameConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_fuel.stackFromEnum(AmmoFlamethrower.DIESEL));
		bullet.ammoCount = 100;
		
		bullet.velocity = 0.75F;
		bullet.spread = 0.025F;
		bullet.wear = 1;
		bullet.bulletsMin = 3;
		bullet.bulletsMax = 5;
		bullet.dmgMin = 2;
		bullet.dmgMax = 4;
		bullet.gravity = 0.01D;
		bullet.maxAge = 60;
		bullet.doesRicochet = false;
		bullet.doesPenetrate = true;
		bullet.doesBreakGlass = false;
		bullet.style = BulletConfiguration.STYLE_NONE;
		bullet.plink = BulletConfiguration.PLINK_NONE;
		bullet.vPFX = "flame";
		bullet.incendiary = 10;
		
		bullet.damageType = ModDamageSource.s_flamethrower;
		bullet.dmgProj = false;
		bullet.dmgFire = true;
		
		bullet.bntImpact = new IBulletImpactBehaviorNT() {

			@Override
			public void behaveBlockHit(EntityBulletBaseNT bullet, int x, int y, int z, int sideHit) {
				
				if(!bullet.worldObj.isRemote) {
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "vanillaburst");
					data.setString("mode", "flame");
					data.setInteger("count", 15);
					data.setDouble("motion", 0.1D);
					
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, bullet.posX, bullet.posY, bullet.posZ), new TargetPoint(bullet.dimension, bullet.posX, bullet.posY, bullet.posZ, 50));
				}
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getNapalmConfig() {
		
		BulletConfiguration bullet = GunEnergyFactory.getFlameConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_fuel.stackFromEnum(AmmoFlamethrower.NAPALM));
		bullet.wear = 2;
		bullet.dmgMin = 4;
		bullet.dmgMax = 6;
		bullet.maxAge = 200;
		
		return bullet;
	}
	
	public static BulletConfiguration getPhosphorusConfig() {
		
		BulletConfiguration bullet = GunEnergyFactory.getFlameConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_fuel.stackFromEnum(AmmoFlamethrower.PHOSPHORUS));
		bullet.wear = 2;
		bullet.spread = 0.0F;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		bullet.dmgMin = 4;
		bullet.dmgMax = 6;
		bullet.maxAge = 200;
		bullet.vPFX = "smoke";
		
		bullet.bntImpact = BulletConfigFactory.getPhosphorousEffect(5, 60 * 20, 25, 0.25, 0.1F);
		
		return bullet;
	}
	
	public static BulletConfiguration getVaporizerConfig() {
		
		BulletConfiguration bullet = GunEnergyFactory.getFlameConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_fuel.stackFromEnum(AmmoFlamethrower.VAPORIZER));
		bullet.wear = 4;
		bullet.spread = 0.25F;
		bullet.bulletsMin = 8;
		bullet.bulletsMax = 10;
		bullet.dmgMin = 6;
		bullet.dmgMax = 10;
		bullet.maxAge = 15;
		bullet.vPFX = "flame";
		bullet.incendiary = 0;
		
		bullet.dmgBypass = true;
		
		PotionEffect eff = new PotionEffect(HbmPotion.phosphorus.id, 20 * 20, 0, true);
		eff.getCurativeItems().clear();
		bullet.effects = new ArrayList<>();
		bullet.effects.add(new PotionEffect(eff));
		
		return bullet;
	}
	
	public static BulletConfiguration getGasConfig() {
		
		BulletConfiguration bullet = GunEnergyFactory.getFlameConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_fuel.stackFromEnum(AmmoFlamethrower.CHLORINE));
		bullet.wear = 1;
		bullet.spread = 0.05F;
		bullet.gravity = 0D;
		bullet.bulletsMin = 5;
		bullet.bulletsMax = 7;
		bullet.dmgMin = 0;
		bullet.dmgMax = 0;
		bullet.vPFX = "cloud";
		bullet.incendiary = 0;
		
		bullet.dmgFire = false;
		
		bullet.bntImpact = BulletConfigFactory.getGasEffect(5, 60 * 20);
		
		return bullet;
	}
	
	public static BulletConfiguration getFextConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_fireext.stackFromEnum(AmmoFireExt.WATER));
		bullet.ammoCount = 300;
		
		bullet.velocity = 0.75F;
		bullet.spread = 0.025F;
		bullet.wear = 1;
		bullet.bulletsMin = 2;
		bullet.bulletsMax = 3;
		bullet.dmgMin = 0;
		bullet.dmgMax = 0;
		bullet.gravity = 0.04D;
		bullet.maxAge = 100;
		bullet.doesRicochet = false;
		bullet.doesPenetrate = true;
		bullet.doesBreakGlass = false;
		bullet.style = BulletConfiguration.STYLE_NONE;
		bullet.plink = BulletConfiguration.PLINK_NONE;
		
		bullet.bntHurt = (bulletEntity, target) -> { target.extinguish(); };
		
		bullet.bntImpact = new IBulletImpactBehaviorNT() {

			@Override
			public void behaveBlockHit(EntityBulletBaseNT bullet, int x, int y, int z, int sideHit) {
				
				if(!bullet.worldObj.isRemote) {
					
					int ix = (int)Math.floor(bullet.posX);
					int iy = (int)Math.floor(bullet.posY);
					int iz = (int)Math.floor(bullet.posZ);
					
					boolean fizz = false;
					
					for(int i = -1; i <= 1; i++) {
						for(int j = -1; j <= 1; j++) {
							for(int k = -1; k <= 1; k++) {
								
								if(bullet.worldObj.getBlock(ix + i, iy + j, iz + k) == Blocks.fire) {
									bullet.worldObj.setBlock(ix + i, iy + j, iz + k, Blocks.air);
									fizz = true;
								}
							}
						}
					}
					
					TileEntity core = CompatExternal.getCoreFromPos(bullet.worldObj, ix, iy, iz);
					if(core instanceof IRepairable) {
						((IRepairable) core).tryExtinguish(bullet.worldObj, ix, iy, iz, EnumExtinguishType.WATER);
					}
					
					if(fizz)
						bullet.worldObj.playSoundEffect(bullet.posX, bullet.posY, bullet.posZ, "random.fizz", 1.0F, 1.5F + bullet.worldObj.rand.nextFloat() * 0.5F);
				}
			}
		};
		
		bullet.bntUpdate = new IBulletUpdateBehaviorNT() {

			@Override
			public void behaveUpdate(EntityBulletBaseNT bullet) {
				
				if(bullet.worldObj.isRemote) {
					
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "vanillaExt");
					data.setString("mode", "blockdust");
					data.setInteger("block", Block.getIdFromBlock(Blocks.water));
					data.setDouble("posX", bullet.posX);
					data.setDouble("posY", bullet.posY);
					data.setDouble("posZ", bullet.posZ);
					data.setDouble("mX", bullet.motionX + bullet.worldObj.rand.nextGaussian() * 0.05);
					data.setDouble("mY", bullet.motionY - 0.2 + bullet.worldObj.rand.nextGaussian() * 0.05);
					data.setDouble("mZ", bullet.motionZ + bullet.worldObj.rand.nextGaussian() * 0.05);
					MainRegistry.proxy.effectNT(data);
				} else {

					int x = (int)Math.floor(bullet.posX);
					int y = (int)Math.floor(bullet.posY);
					int z = (int)Math.floor(bullet.posZ);
					
					if(bullet.worldObj.getBlock(x, y, z) == ModBlocks.volcanic_lava_block && bullet.worldObj.getBlockMetadata(x, y, z) == 0) {
						bullet.worldObj.setBlock(x, y, z, Blocks.obsidian);
						bullet.setDead();
					}
				}
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getFextFoamConfig() {
		
		BulletConfiguration bullet = GunEnergyFactory.getFextConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_fireext.stackFromEnum(AmmoFireExt.FOAM));
		bullet.spread = 0.05F;
		
		bullet.bntImpact = new IBulletImpactBehaviorNT() {

			@Override
			public void behaveBlockHit(EntityBulletBaseNT bullet, int x, int y, int z, int sideHit) {
				
				if(!bullet.worldObj.isRemote) {
					
					int ix = (int)Math.floor(bullet.posX);
					int iy = (int)Math.floor(bullet.posY);
					int iz = (int)Math.floor(bullet.posZ);
					
					boolean fizz = false;
					
					for(int i = -1; i <= 1; i++) {
						for(int j = -1; j <= 1; j++) {
							for(int k = -1; k <= 1; k++) {
								
								Block b = bullet.worldObj.getBlock(ix + i, iy + j, iz + k);
								
								if(b.getMaterial() == Material.fire) {
									bullet.worldObj.setBlock(ix + i, iy + j, iz + k, Blocks.air);
									fizz = true;
								}
							}
						}
					}
					
					Block b = bullet.worldObj.getBlock(ix, iy, iz);
					
					TileEntity core = CompatExternal.getCoreFromPos(bullet.worldObj, ix, iy, iz);
					if(core instanceof IRepairable) {
						((IRepairable) core).tryExtinguish(bullet.worldObj, ix, iy, iz, EnumExtinguishType.FOAM);
						return;
					}
					
					if(b.isReplaceable(bullet.worldObj, ix, iy, iz) && ModBlocks.foam_layer.canPlaceBlockAt(bullet.worldObj, ix, iy, iz)) {
						
						if(b != ModBlocks.foam_layer) {
							bullet.worldObj.setBlock(ix, iy, iz, ModBlocks.foam_layer);
						} else {
							int meta = bullet.worldObj.getBlockMetadata(ix, iy, iz);
							
							if(meta < 6)
								bullet.worldObj.setBlockMetadataWithNotify(ix, iy, iz, meta + 1, 3);
							else
								bullet.worldObj.setBlock(ix, iy, iz, ModBlocks.block_foam);
						}
					}
					
					if(fizz)
						bullet.worldObj.playSoundEffect(bullet.posX, bullet.posY, bullet.posZ, "random.fizz", 1.0F, 1.5F + bullet.worldObj.rand.nextFloat() * 0.5F);
				}
			}
		};
		
		bullet.bntUpdate = new IBulletUpdateBehaviorNT() {

			@Override
			public void behaveUpdate(EntityBulletBaseNT bullet) {
				
				if(bullet.worldObj.isRemote) {
					
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "vanillaExt");
					data.setString("mode", "blockdust");
					data.setInteger("block", Block.getIdFromBlock(ModBlocks.block_foam));
					data.setDouble("posX", bullet.posX);
					data.setDouble("posY", bullet.posY);
					data.setDouble("posZ", bullet.posZ);
					data.setDouble("mX", bullet.motionX + bullet.worldObj.rand.nextGaussian() * 0.05);
					data.setDouble("mY", bullet.motionY - 0.2 + bullet.worldObj.rand.nextGaussian() * 0.05);
					data.setDouble("mZ", bullet.motionZ + bullet.worldObj.rand.nextGaussian() * 0.05);
					MainRegistry.proxy.effectNT(data);
				}
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getFextSandConfig() {
		
		BulletConfiguration bullet = GunEnergyFactory.getFextConfig();
		
		bullet.ammo = new ComparableStack(ModItems.ammo_fireext.stackFromEnum(AmmoFireExt.SAND));
		bullet.spread = 0.1F;
		
		bullet.bntHurt = null; // does not extinguish entities
		
		bullet.bntImpact = new IBulletImpactBehaviorNT() {

			@Override
			public void behaveBlockHit(EntityBulletBaseNT bullet, int x, int y, int z, int sideHit) {
				
				if(!bullet.worldObj.isRemote) {
					
					int ix = (int)Math.floor(bullet.posX);
					int iy = (int)Math.floor(bullet.posY);
					int iz = (int)Math.floor(bullet.posZ);
					
					Block b = bullet.worldObj.getBlock(ix, iy, iz);
					
					TileEntity core = CompatExternal.getCoreFromPos(bullet.worldObj, ix, iy, iz);
					if(core instanceof IRepairable) {
						((IRepairable) core).tryExtinguish(bullet.worldObj, ix, iy, iz, EnumExtinguishType.SAND);
						return;
					}
					
					if((b.isReplaceable(bullet.worldObj, ix, iy, iz) || b == ModBlocks.sand_boron_layer) && ModBlocks.sand_boron_layer.canPlaceBlockAt(bullet.worldObj, ix, iy, iz)) {
						
						if(b != ModBlocks.sand_boron_layer) {
							bullet.worldObj.setBlock(ix, iy, iz, ModBlocks.sand_boron_layer);
						} else {
							int meta = bullet.worldObj.getBlockMetadata(ix, iy, iz);
							
							if(meta < 6)
								bullet.worldObj.setBlockMetadataWithNotify(ix, iy, iz, meta + 1, 3);
							else
								bullet.worldObj.setBlock(ix, iy, iz, ModBlocks.sand_boron);
						}
						
						if(b.getMaterial() == Material.fire)
							bullet.worldObj.playSoundEffect(bullet.posX, bullet.posY, bullet.posZ, "random.fizz", 1.0F, 1.5F + bullet.worldObj.rand.nextFloat() * 0.5F);
					}
				}
			}
		};
		
		bullet.bntUpdate = new IBulletUpdateBehaviorNT() {

			@Override
			public void behaveUpdate(EntityBulletBaseNT bullet) {
				
				if(bullet.worldObj.isRemote) {
					
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "vanillaExt");
					data.setString("mode", "blockdust");
					data.setInteger("block", Block.getIdFromBlock(ModBlocks.sand_boron));
					data.setDouble("posX", bullet.posX);
					data.setDouble("posY", bullet.posY);
					data.setDouble("posZ", bullet.posZ);
					data.setDouble("mX", bullet.motionX + bullet.worldObj.rand.nextGaussian() * 0.1);
					data.setDouble("mY", bullet.motionY - 0.2 + bullet.worldObj.rand.nextGaussian() * 0.1);
					data.setDouble("mZ", bullet.motionZ + bullet.worldObj.rand.nextGaussian() * 0.1);
					MainRegistry.proxy.effectNT(data);
				}
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getZOMGBoltConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.ammo = new ComparableStack(ModItems.nugget_euphemium);
		bullet.ammoCount = 1000;
		bullet.wear = 1;
		bullet.velocity = 1F;
		bullet.spread = 0.125F;
		bullet.maxAge = 100;
		bullet.gravity = 0D;
		bullet.bulletsMin = 5;
		bullet.bulletsMax = 5;
		bullet.dmgMin = 10000;
		bullet.dmgMax = 25000;
		bullet.liveAfterImpact = true;
		
		bullet.damageType = ModDamageSource.s_zomg_prefix;
		bullet.dmgProj = false;
		bullet.dmgBypass = true;

		bullet.style = BulletConfiguration.STYLE_BOLT;
		bullet.trail = BulletConfiguration.BOLT_ZOMG;
		
		bullet.effects = new ArrayList<>();
		bullet.effects.add(new PotionEffect(HbmPotion.bang.id, 10 * 20, 0));
		
		bullet.bntImpact = new IBulletImpactBehaviorNT() {

			@Override
			public void behaveBlockHit(EntityBulletBaseNT bullet, int x, int y, int z, int sideHit) {
				
				if(!bullet.worldObj.isRemote) {
					ExplosionChaos.explodeZOMG(bullet.worldObj, (int)Math.floor(bullet.posX), (int)Math.floor(bullet.posY), (int)Math.floor(bullet.posZ), 5);
					bullet.worldObj.playSoundEffect(bullet.posX, bullet.posY, bullet.posZ, "hbm:entity.bombDet", 5.0F, 1.0F);
    				ExplosionLarge.spawnParticles(bullet.worldObj, bullet.posX, bullet.posY, bullet.posZ, 5);
				}
			}
		};
		
		return bullet;
	}
	
	public static BulletConfiguration getCryoConfig() {
		BulletConfiguration bullet = new BulletConfiguration();
		bullet.ammo = new ComparableStack(ModItems.gun_cryolator_ammo);
		bullet.ammoCount = 100;
		bullet.bulletsMin = 1;
		bullet.bulletsMax = 1;
		return bullet;
	}

	public static BulletConfiguration getTurbineConfig() {
		
		BulletConfiguration bullet = new BulletConfiguration();
		
		bullet.ammo = new ComparableStack(ModItems.nothing);
		bullet.dmgMin = 100;
		bullet.dmgMax = 150;
		bullet.velocity = 1F;
		bullet.gravity = 0.0;
		bullet.maxAge = 200;
		bullet.style = BulletConfiguration.STYLE_BLADE;
		bullet.destroysBlocks = true;
		bullet.doesRicochet = false;
		
		return bullet;
	}

	public static BulletConfiguration getTurretConfig() {
		BulletConfiguration bullet = GunEnergyFactory.getFlameConfig();
		bullet.spread *= 2F;
		bullet.gravity = 0.0025D;
		return bullet;
	}
}
