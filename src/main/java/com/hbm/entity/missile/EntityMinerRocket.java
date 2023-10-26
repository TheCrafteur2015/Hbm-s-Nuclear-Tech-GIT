package com.hbm.entity.missile;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.util.ParticleUtil;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityMinerRocket extends Entity {
	//0 landing, 1 unloading, 2 lifting
	public int timer = 0;

	public EntityMinerRocket(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
        setSize(1F, 3F);
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(16, 0);
		this.dataWatcher.addObject(17, 0);
	}
	
	@Override
	public void onUpdate() {
		if(this.dataWatcher.getWatchableObjectInt(16) == 0)
			this.motionY = -0.75;
		if(this.dataWatcher.getWatchableObjectInt(16) == 1)
			this.motionY = 0;
		if(this.dataWatcher.getWatchableObjectInt(16) == 2)
			this.motionY = 1;
		
		this.motionX = 0;
		this.motionZ = 0;
		
		setPositionAndRotation(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ, 0.0F, 0.0F);

		if(this.dataWatcher.getWatchableObjectInt(16) == 0 && this.worldObj.getBlock((int)(this.posX - 0.5), (int)(this.posY - 0.5), (int)(this.posZ - 0.5)) == ModBlocks.sat_dock) {
			this.dataWatcher.updateObject(16, 1);
			this.motionY = 0;
			this.posY = (int)this.posY;
		} else if(this.worldObj.getBlock((int)(this.posX - 0.5), (int)(this.posY + 1), (int)(this.posZ - 0.5)).getMaterial() != Material.air && !this.worldObj.isRemote && this.dataWatcher.getWatchableObjectInt(16) != 1) {
			setDead();
			ExplosionLarge.explodeFire(this.worldObj, this.posX - 0.5, this.posY, this.posZ - 0.5, 10F, true, false, true);
			//worldObj.setBlock((int)(posX - 0.5), (int)(posY + 0.5), (int)(posZ - 0.5), Blocks.dirt);
		}
		
		if(this.dataWatcher.getWatchableObjectInt(16) == 1) {
			if(!this.worldObj.isRemote && this.ticksExisted % 4 == 0)
				ExplosionLarge.spawnShock(this.worldObj, this.posX, this.posY, this.posZ, 1 + this.rand.nextInt(3), 1 + this.rand.nextGaussian());
			
			this.timer++;
			
			if(this.timer > 100) {
				this.dataWatcher.updateObject(16, 2);
			}
		}
		
		if(this.dataWatcher.getWatchableObjectInt(16) != 1 && !this.worldObj.isRemote && this.ticksExisted % 2 == 0) {
			ParticleUtil.spawnGasFlame(this.worldObj, this.posX, this.posY - 0.5, this.posZ, 0.0, -1.0, 0.0);
		}
		
		if(this.dataWatcher.getWatchableObjectInt(16) == 2 && this.posY > 300)
			setDead();
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.dataWatcher.updateObject(16, nbt.getInteger("mode"));
		this.dataWatcher.updateObject(17, nbt.getInteger("sat"));
		this.timer = nbt.getInteger("timer");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("mode", this.dataWatcher.getWatchableObjectInt(16));
		nbt.setInteger("sat", this.dataWatcher.getWatchableObjectInt(17));
		nbt.setInteger("timer", this.timer);
	}
}
