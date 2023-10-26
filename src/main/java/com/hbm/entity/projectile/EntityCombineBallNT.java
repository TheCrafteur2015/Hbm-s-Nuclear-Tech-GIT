package com.hbm.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityCombineBallNT extends EntityBulletBaseNT {

	public EntityCombineBallNT(World world, int config, EntityLivingBase shooter) {
		super(world, config, shooter);
		this.overrideDamage = 1000;
	}

	@Override
	public void setDead() {
		super.setDead();
		this.worldObj.createExplosion(getThrower(), this.posX, this.posY, this.posZ, 2, false);
	}
}
