package com.hbm.entity.missile;

import java.util.List;

import com.hbm.explosion.ExplosionLarge;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMissileDoomsday extends EntityMissileBaseAdvanced {

	public EntityMissileDoomsday(World p_i1582_1_) {
		super(p_i1582_1_);
	}
	
	public EntityMissileDoomsday(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
		ExplosionLarge.explode(this.worldObj, this.posX, this.posY, this.posZ, 10.0F, true, true, true);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if(this.motionY <= 0) {
			if(!this.worldObj.isRemote) {
				setDead();
				EntityBombletTheta bomblet1 = new EntityBombletTheta(this.worldObj);
				EntityBombletTheta bomblet2 = new EntityBombletTheta(this.worldObj);
				EntityBombletTheta bomblet3 = new EntityBombletTheta(this.worldObj);
				EntityBombletTheta bomblet4 = new EntityBombletTheta(this.worldObj);
				EntityBombletTheta bomblet5 = new EntityBombletTheta(this.worldObj);
				EntityBombletTheta bomblet6 = new EntityBombletTheta(this.worldObj);
				bomblet1.motionX = this.motionX * (this.rand.nextFloat() + 0.5F);
				bomblet1.motionY = this.motionY * (this.rand.nextFloat() + 0.5F);
				bomblet1.motionZ = this.motionZ * (this.rand.nextFloat() + 0.5F);
				bomblet2.motionX = this.motionX * (this.rand.nextFloat() + 0.5F);
				bomblet2.motionY = this.motionY * (this.rand.nextFloat() + 0.5F);
				bomblet2.motionZ = this.motionZ * (this.rand.nextFloat() + 0.5F);
				bomblet3.motionX = this.motionX * (this.rand.nextFloat() + 0.5F);
				bomblet3.motionY = this.motionY * (this.rand.nextFloat() + 0.5F);
				bomblet3.motionZ = this.motionZ * (this.rand.nextFloat() + 0.5F);
				bomblet4.motionX = this.motionX * (this.rand.nextFloat() + 0.5F);
				bomblet4.motionY = this.motionY * (this.rand.nextFloat() + 0.5F);
				bomblet4.motionZ = this.motionZ * (this.rand.nextFloat() + 0.5F);
				bomblet5.motionX = this.motionX * (this.rand.nextFloat() + 0.5F);
				bomblet5.motionY = this.motionY * (this.rand.nextFloat() + 0.5F);
				bomblet5.motionZ = this.motionZ * (this.rand.nextFloat() + 0.5F);
				bomblet6.motionX = this.motionX * (this.rand.nextFloat() + 0.5F);
				bomblet6.motionY = this.motionY * (this.rand.nextFloat() + 0.5F);
				bomblet6.motionZ = this.motionZ * (this.rand.nextFloat() + 0.5F);
				bomblet1.posX = this.posX;
				bomblet1.posY = this.posY;
				bomblet1.posZ = this.posZ;
				bomblet2.posX = this.posX;
				bomblet2.posY = this.posY;
				bomblet2.posZ = this.posZ;
				bomblet3.posX = this.posX;
				bomblet3.posY = this.posY;
				bomblet3.posZ = this.posZ;
				bomblet4.posX = this.posX;
				bomblet4.posY = this.posY;
				bomblet4.posZ = this.posZ;
				bomblet5.posX = this.posX;
				bomblet5.posY = this.posY;
				bomblet5.posZ = this.posZ;
				bomblet6.posX = this.posX;
				bomblet6.posY = this.posY;
				bomblet6.posZ = this.posZ;

				bomblet1.decelY = this.decelY;
				bomblet2.decelY = this.decelY;
				bomblet3.decelY = this.decelY;
				bomblet4.decelY = this.decelY;
				bomblet5.decelY = this.decelY;
				bomblet6.decelY = this.decelY;
				bomblet1.accelXZ = this.accelXZ;
				bomblet2.accelXZ = this.accelXZ;
				bomblet3.accelXZ = this.accelXZ;
				bomblet4.accelXZ = this.accelXZ;
				bomblet5.accelXZ = this.accelXZ;
				bomblet6.accelXZ = this.accelXZ;
				this.worldObj.spawnEntityInWorld(bomblet1);
				this.worldObj.spawnEntityInWorld(bomblet2);
				this.worldObj.spawnEntityInWorld(bomblet3);
				this.worldObj.spawnEntityInWorld(bomblet4);
				this.worldObj.spawnEntityInWorld(bomblet5);
				this.worldObj.spawnEntityInWorld(bomblet6);
				ExplosionLarge.spawnParticles(this.worldObj, this.posX, this.posY, this.posZ, ExplosionLarge.cloudFunction(25));
				ExplosionLarge.spawnTracers(this.worldObj, this.posX, this.posY, this.posZ, 10);
			}
		}
	}

	@Override
	public List<ItemStack> getDebris() {
		return null;
	}

	@Override
	public ItemStack getDebrisRareDrop() {
		return null;
	}

	@Override
	public RadarTargetType getTargetType() {
		return RadarTargetType.MISSILE_TIER4;
	}
}
