package com.hbm.entity.projectile;

import com.hbm.entity.mob.EntityGlyphid;
import com.hbm.lib.ModDamageSource;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class EntityAcidBomb extends EntityThrowableInterp {
	
	public float damage = 1.5F;

	public EntityAcidBomb(World world) {
		super(world);
	}

	public EntityAcidBomb(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		
		if(this.worldObj.isRemote) return;
		
		if(mop.typeOfHit == MovingObjectType.ENTITY) {
			
			if(!(mop.entityHit instanceof EntityGlyphid)) {
				mop.entityHit.attackEntityFrom(ModDamageSource.acid, this.damage);
				setDead();
			}
		}
		
		if(mop.typeOfHit == MovingObjectType.BLOCK)
			setDead();
	}

	@Override
	public double getGravityVelocity() {
		return 0.04D;
	}
	
	@Override
	protected float getAirDrag() {
		return 1.0F;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setFloat("damage", this.damage);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.damage = nbt.getFloat("damage");
	}
}
