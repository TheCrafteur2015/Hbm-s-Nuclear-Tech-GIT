package com.hbm.entity.effect;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityRagingVortex extends EntityBlackHole {
	
	int timer = 0;

	public EntityRagingVortex(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
	}

	public EntityRagingVortex(World world, float size) {
		super(world);
		this.dataWatcher.updateObject(16, size);
	}
	
	@Override
	public void onUpdate() {
		
		this.timer++;
		
		if(this.timer <= 20)
			this.timer -= 20;
		
		float pulse = (float)(Math.sin(this.timer) * Math.PI / 20D) * 0.35F;
		
		float dec = 0.0F;
		
		if(this.rand.nextInt(100) == 0) {
			dec = 0.1F;
			this.worldObj.createExplosion(null, this.posX, this.posY, this.posZ, 10F, false);
		}
		
		this.dataWatcher.updateObject(16, this.dataWatcher.getWatchableObjectFloat(16) - pulse - dec);
		if(this.dataWatcher.getWatchableObjectFloat(16) <= 0) {
			setDead();
			return;
		}
		
		super.onUpdate();
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		
		this.timer = nbt.getInteger("vortexTimer");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		
		nbt.setInteger("vortexTimer", this.timer);
	}
}