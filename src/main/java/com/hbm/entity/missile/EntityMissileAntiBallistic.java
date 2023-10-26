package com.hbm.entity.missile;

import java.util.List;

import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.explosion.ExplosionLarge;

import api.hbm.entity.IRadarDetectable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityMissileAntiBallistic extends Entity implements IRadarDetectable {

	int activationTimer;

	public EntityMissileAntiBallistic(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	@Override
	public void onUpdate() {

		if(this.activationTimer < 40) {
			this.activationTimer++;

			this.motionY = 1.5D;

			setLocationAndAngles(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ, 0, 0);
			rotation();

			if(!this.worldObj.isRemote && this.posY < 400)
				this.worldObj.spawnEntityInWorld(new EntitySmokeFX(this.worldObj, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0));

		} else {

			if(this.activationTimer == 40) {
				ExplosionLarge.spawnParticlesRadial(this.worldObj, this.posX, this.posY, this.posZ, 15);
				this.activationTimer = 100;
			}

			for(int i = 0; i < 5; i++) {

				targetMissile();

				setLocationAndAngles(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ, 0, 0);
				rotation();

				if(!this.worldObj.isRemote && this.posY < 400)
					this.worldObj.spawnEntityInWorld(new EntitySmokeFX(this.worldObj, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0));

				List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(this.posX - 5, this.posY - 5, this.posZ - 5, this.posX + 5, this.posY + 5, this.posZ + 5));

				for(Entity e : list) {
					if(e instanceof EntityMissileBaseAdvanced || e instanceof EntityMissileCustom) {
						ExplosionLarge.explode(this.worldObj, this.posX, this.posY, this.posZ, 15F, true, false, true);
						setDead();
						return;
					}
				}
			}
		}
		
		if(this.posY > 2000)
			setDead();

		if(this.worldObj.getBlock((int) this.posX, (int) this.posY, (int) this.posZ) != Blocks.air && this.worldObj.getBlock((int) this.posX, (int) this.posY, (int) this.posZ) != Blocks.water && this.worldObj.getBlock((int) this.posX, (int) this.posY, (int) this.posZ) != Blocks.flowing_water) {

			if(!this.worldObj.isRemote) {
				ExplosionLarge.explode(this.worldObj, this.posX, this.posY, this.posZ, 10F, true, true, true);
			}
			setDead();
			return;
		}

	}

	protected void rotation() {
		float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

		for(this.rotationPitch = (float) (Math.atan2(this.motionY, f2) * 180.0D / Math.PI) - 90; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
			;
		}

		while(this.rotationPitch - this.prevRotationPitch >= 180.0F) {
			this.prevRotationPitch += 360.0F;
		}

		while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
			this.prevRotationYaw -= 360.0F;
		}

		while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
			this.prevRotationYaw += 360.0F;
		}
	}

	private void targetMissile() {

		List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(this.posX - 500, 0, this.posZ - 500, this.posX + 500, 5000, this.posZ + 500));

		Entity target = null;
		double closest = 1000D;

		for(Entity e : list) {
			if(e instanceof EntityMissileBaseAdvanced || e instanceof EntityMissileCustom) {
				double dis = Math.sqrt(Math.pow(e.posX - this.posX, 2) + Math.pow(e.posY - this.posY, 2) + Math.pow(e.posZ - this.posZ, 2));

				if(dis < closest) {
					closest = dis;
					target = e;
				}
			}
		}

		if(target != null) {

			Vec3 vec = Vec3.createVectorHelper(target.posX - this.posX, target.posY - this.posY, target.posZ - this.posZ);

			vec.normalize();

			this.motionX = vec.xCoord * 0.065D;
			this.motionY = vec.yCoord * 0.065D;
			this.motionZ = vec.zCoord * 0.065D;
		}
	}

	@Override
	protected void entityInit() {

	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {

	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return distance < 500000;
	}

	@Override
	public RadarTargetType getTargetType() {
		return RadarTargetType.MISSILE_AB;
	}

}
