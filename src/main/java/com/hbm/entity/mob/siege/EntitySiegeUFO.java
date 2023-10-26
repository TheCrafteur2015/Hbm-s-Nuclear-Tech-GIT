package com.hbm.entity.mob.siege;

import com.hbm.entity.mob.EntityUFOBase;
import com.hbm.entity.projectile.EntitySiegeLaser;
import com.hbm.handler.SiegeOrchestrator;

import api.hbm.entity.IRadiationImmune;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntitySiegeUFO extends EntityUFOBase implements IRadiationImmune {

	private int attackCooldown;
	
	public EntitySiegeUFO(World world) {
		super(world);
		setSize(1.5F, 1F);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		getDataWatcher().addObject(12, (int) 0);
	}
	
	public void setTier(SiegeTier tier) {
		getDataWatcher().updateObject(12, tier.id);

		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(tier.speedMod);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(tier.health * 0.25);
		setHealth(getMaxHealth());
	}
	
	public SiegeTier getTier() {
		SiegeTier tier = SiegeTier.tiers[getDataWatcher().getWatchableObjectInt(12)];
		return tier != null ? tier : SiegeTier.CLAY;
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
			this.worldObj.playSoundAtEntity(this, "random.break", 5F, 1.0F + this.rand.nextFloat() * 0.5F);
			return false;
		}
		
		damage *= (1F - tier.dr);
		
		return super.attackEntityFrom(source, damage);
	}

	@Override
	protected void updateEntityActionState() {
		super.updateEntityActionState();

		if(this.courseChangeCooldown > 0) {
			this.courseChangeCooldown--;
		}
		if(this.scanCooldown > 0) {
			this.scanCooldown--;
		}
		
		if(!this.worldObj.isRemote) {
			if(this.attackCooldown > 0) {
				this.attackCooldown--;
			}
			
			if(this.attackCooldown == 0 && this.target != null) {
				this.attackCooldown = 20 + this.rand.nextInt(5);
				
				double x = this.posX;
				double y = this.posY;
				double z = this.posZ;
				
				Vec3 vec = Vec3.createVectorHelper(this.target.posX - x, this.target.posY + this.target.height * 0.5 - y, this.target.posZ - z).normalize();
				SiegeTier tier = getTier();
				
				EntitySiegeLaser laser = new EntitySiegeLaser(this.worldObj, this);
				laser.setPosition(x, y, z);
				laser.setThrowableHeading(vec.xCoord, vec.yCoord, vec.zCoord, 1F, 0.15F);
				laser.setColor(0x802000);
				laser.setDamage(tier.damageMod);
				laser.setExplosive(tier.laserExplosive);
				laser.setBreakChance(tier.laserBreak);
				if(tier.laserIncendiary) laser.setIncendiary();
				this.worldObj.spawnEntityInWorld(laser);
				playSound("hbm:weapon.ballsLaser", 2.0F, 1.0F);
			}
		}
		
		if(this.courseChangeCooldown > 0) {
			approachPosition(this.target == null ? 0.25D : 0.5D + getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue() * 1);
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

	@Override
	protected void dropFewItems(boolean byPlayer, int fortune) {
		
		if(byPlayer) {
			for(ItemStack drop : getTier().dropItem) {
				entityDropItem(drop.copy(), 0F);
			}
		}
	}
}
