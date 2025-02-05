package com.hbm.entity.mob.ai;

import com.hbm.entity.projectile.EntityBulletBaseNT;
import com.hbm.handler.BulletConfigSyncingUtil;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.Vec3;

public class EntityAIMaskmanMinigun extends EntityAIBase {
	
	private EntityCreature owner;
    private EntityLivingBase target;
    int delay;
    int timer;

	public EntityAIMaskmanMinigun(EntityCreature owner, boolean checkSight, boolean nearbyOnly, int delay) {
		this.owner = owner;
		this.delay = delay;
		this.timer = delay;
	}

	@Override
	public boolean shouldExecute() {
		
        EntityLivingBase entity = this.owner.getAttackTarget();

        if(entity == null) {
            return false;
            
        } else {
            this.target = entity;
            double dist = Vec3.createVectorHelper(this.target.posX - this.owner.posX, this.target.posY - this.owner.posY, this.target.posZ - this.owner.posZ).lengthVector();
            return dist > 5 && dist < 10;
        }
	}
	
	@Override
    public boolean continueExecuting() {
        return shouldExecute() || !this.owner.getNavigator().noPath();
    }

	@Override
    public void updateTask() {
    	
		this.timer--;
		
		if(this.timer <= 0) {
			this.timer = this.delay;

			EntityBulletBaseNT bullet = new EntityBulletBaseNT(this.owner.worldObj, BulletConfigSyncingUtil.MASKMAN_BULLET, this.owner, this.target, 1.0F, 0);
			this.owner.worldObj.spawnEntityInWorld(bullet);
			this.owner.playSound("hbm:weapon.calShoot", 1.0F, 1.0F);
		}
		
		this.owner.rotationYaw = this.owner.rotationYawHead;
    }
}
