package com.hbm.entity.mob.siege;

import com.hbm.handler.SiegeOrchestrator;

import api.hbm.entity.IRadiationImmune;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;

public class EntitySiegeZombie extends EntityMob implements IRadiationImmune {

	public EntitySiegeZombie(World world) {
		super(world);
		getNavigator().setBreakDoors(true);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
		this.tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(4, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(5, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		setSize(0.6F, 1.8F);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float damage) {
		
		if(isEntityInvulnerable() || SiegeOrchestrator.isSiegeMob(source.getEntity()))
			return false;
		
		SiegeTier tier = getTier();
		
		if(tier.fireProof && source.isFireDamage()) {
			extinguish();
			return false;
		}
		
		if(tier.noFall && source == DamageSource.fall)
			return false;
		
		//noFF can't be harmed by other mobs
		if(tier.noFriendlyFire && source instanceof EntityDamageSource && !(((EntityDamageSource) source).getEntity() instanceof EntityPlayer))
			return false;
		
		damage -= tier.dt;
		
		if(damage < 0) {
			this.worldObj.playSoundAtEntity(this, "random.break", 5F, 1.0F + this.rand.nextFloat() * 0.5F);
			return false;
		}
		
		damage *= (1F - tier.dr);
		
		return super.attackEntityFrom(source, damage);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		getDataWatcher().addObject(12, (int) 0);
		getDataWatcher().addObject(13, (byte) 0);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
	}
	
	public void setTier(SiegeTier tier) {
		getDataWatcher().updateObject(12, tier.id);

		getEntityAttribute(SharedMonsterAttributes.movementSpeed).applyModifier(new AttributeModifier("Tier Speed Mod", tier.speedMod, 1));
		getEntityAttribute(SharedMonsterAttributes.attackDamage).applyModifier(new AttributeModifier("Tier Damage Mod", tier.damageMod, 1));
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(tier.health);
		setHealth(getMaxHealth());
	}
	
	public SiegeTier getTier() {
		SiegeTier tier = SiegeTier.tiers[getDataWatcher().getWatchableObjectInt(12)];
		return tier != null ? tier : SiegeTier.CLAY;
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	protected String getLivingSound() {
		return "hbm:entity.siegeIdle";
	}

	@Override
	protected String getHurtSound() {
		return "hbm:entity.siegeHurt";
	}

	@Override
	protected String getDeathSound() {
		return "hbm:entity.siegeDeath";
	}

	@Override
	protected void dropFewItems(boolean byPlayer, int fortune) {
		
		if(byPlayer) {
			for(ItemStack drop : getTier().dropItem) {
				entityDropItem(drop.copy(), 0F);
			}
		}
	}

	@Override
	public void onUpdate() {
		
		super.onUpdate();
		if(!this.worldObj.isRemote) {
			this.dataWatcher.updateObject(13, (byte)(getAttackTarget() != null ? 1 : 0));
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("siegeTier", getTier().id);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setTier(SiegeTier.tiers[nbt.getInteger("siegeTier")]);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		setTier(SiegeTier.tiers[this.rand.nextInt(SiegeTier.getLength())]);
		return super.onSpawnWithEgg(data);
	}
}