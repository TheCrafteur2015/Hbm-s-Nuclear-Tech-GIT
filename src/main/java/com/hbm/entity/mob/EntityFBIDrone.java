package com.hbm.entity.mob;

import com.hbm.entity.grenade.EntityGrenadeStrong;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityFBIDrone extends EntityUFOBase {

	private int attackCooldown;

	public EntityFBIDrone(World world) {
		super(world);
	}

	@Override
	protected void updateEntityActionState() {
		super.updateEntityActionState();
		if(this.courseChangeCooldown > 0) this.courseChangeCooldown--;
		if(this.scanCooldown > 0) this.scanCooldown--;

		if(!this.worldObj.isRemote) {
			
			if(this.attackCooldown > 0) this.attackCooldown--;
			
			if(this.target != null && this.attackCooldown <= 0) {
				
				Vec3 vec = Vec3.createVectorHelper(this.posX - this.target.posX, this.posY - this.target.posY, this.posZ - this.target.posZ);
				if(Math.abs(vec.xCoord) < 5 && Math.abs(vec.zCoord) < 5 && vec.yCoord > 3) {
					this.attackCooldown = 60;
					EntityGrenadeStrong grenade = new EntityGrenadeStrong(this.worldObj);
					grenade.setPosition(this.posX, this.posY, this.posZ);
					this.worldObj.spawnEntityInWorld(grenade);
				}
			}
		}
		
		if(this.courseChangeCooldown > 0) {
			approachPosition(this.target == null ? 0.25D : 0.5D);
		}
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(35.0D);
	}

	@Override
	protected int getScanRange() {
		return 100;
	}

	@Override
	protected int targetHeightOffset() {
		return 7 + this.rand.nextInt(4);
	}

	@Override
	protected int wanderHeightOffset() {
		return 7 + this.rand.nextInt(4);
	}
}
