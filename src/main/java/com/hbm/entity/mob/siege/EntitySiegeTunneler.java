package com.hbm.entity.mob.siege;

import com.hbm.entity.mob.EntityBurrowingSwingingBase;
import com.hbm.handler.SiegeOrchestrator;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;

//cursed code ahead
//no, this time it's vanilla that is cursed
//for NO FUCKING REASON IN PARTICULAR, EntityCreatue mobs aren't synchronized to clients AT ALL
//yet EntityMob that inherits EntityCreature and adds ABSOLUTELY FUCKING NOTHING other than base functionality works
//why?
//is that some sort of elaborate prank i'm not smart enough to understand?
//well it ain't fucking funny
//this stupid fucking random ass bullshit is the P R E C I S E reason i loathe working with entities
//honest to fucking god was the entire mojang dev team on crack when they wrote this?
public class EntitySiegeTunneler extends EntityBurrowingSwingingBase {

	public EntitySiegeTunneler(World world) {
		super(world);
		this.tasks.addTask(0, new EntityAISwimming(this));
		setSize(1F, 1F);
		this.yOffset = 0.5F;
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
		
		//noFF can't be harmed by other mobs
		if(tier.noFriendlyFire && source instanceof EntityDamageSource && !(((EntityDamageSource) source).getEntity() instanceof EntityPlayer))
			return false;
		
		damage -= tier.dt;
		
		if(damage < 0) {
			//worldObj.playSoundAtEntity(this, "random.break", 5F, 1.0F + rand.nextFloat() * 0.5F);
			return false;
		}
		
		damage *= (1F - tier.dr);
		
		return super.attackEntityFrom(source, damage);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		getDataWatcher().addObject(12, (int) 0);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.15D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
	}
	
	public void setTier(SiegeTier tier) {
		getDataWatcher().updateObject(12, tier.id);

		getEntityAttribute(SharedMonsterAttributes.attackDamage).applyModifier(new AttributeModifier("Tier Damage Mod", tier.damageMod, 1));
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(tier.health * 0.5);
		setHealth(getMaxHealth());
	}
	
	public SiegeTier getTier() {
		SiegeTier tier = SiegeTier.tiers[getDataWatcher().getWatchableObjectInt(12)];
		return tier != null ? tier : SiegeTier.CLAY;
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
