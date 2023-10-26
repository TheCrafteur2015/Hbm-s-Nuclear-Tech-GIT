package com.hbm.entity.projectile;

import java.util.List;

import com.hbm.explosion.ExplosionLarge;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityAAShell extends Entity {
	
	public int speedOverride = 1;
	public int fuse = 5;
	public int dFuse = 30;

	public EntityAAShell(World p_i1582_1_) {
		super(p_i1582_1_);
		rotation();
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
	public void onUpdate() {
		if(this.fuse > 0)
			this.fuse --;
		
		if(this.dFuse > 0) {
			this.dFuse --;
		} else {
			explode();
			return;
		}
		
		this.lastTickPosX = this.prevPosX = this.posX;
		this.lastTickPosY = this.prevPosY = this.posY;
		this.lastTickPosZ = this.prevPosZ = this.posZ;

		
		for(int i = 0; i < 5; i++) {

			setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			
			rotation();
			
			if(this.fuse == 0) {
		        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getBoundingBox(this.posX - 5, this.posY - 5, this.posZ - 5, this.posX + 5, this.posY + 5, this.posZ + 5));
		        for(Entity e : list) {
		        	float size = e.width * e.width * e.height;
		        	if(size >= 0.5) {
						explode();
		        		return;
		        	}
		        		
		        }
			}
			
			if(this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ).getMaterial() != Material.air) {
				explode();
				return;
			}
		}
	}
	
	protected void rotation() {
        float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

        for (this.rotationPitch = (float)(Math.atan2(this.motionY, f2) * 180.0D / Math.PI) - 90; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
        {
            ;
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
        {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F)
        {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
        {
            this.prevRotationYaw += 360.0F;
        }
	}

	private void explode() {
		this.worldObj.createExplosion(null, this.posX, this.posY, this.posZ, 20, true);
		ExplosionLarge.spawnParticlesRadial(this.worldObj, this.posX, this.posY, this.posZ, 35);
		if(this.rand.nextInt(15) == 0)
			ExplosionLarge.spawnShrapnels(this.worldObj, this.posX, this.posY, this.posZ, 5 + this.rand.nextInt(11));
		else if(this.rand.nextInt(15) == 0)
			ExplosionLarge.spawnShrapnelShower(this.worldObj, this.posX, this.posY, this.posZ, this.motionX * 2, this.motionY * 2, this.motionZ * 2, 5 + this.rand.nextInt(11), 0.15);
		setDead();
	}
}
