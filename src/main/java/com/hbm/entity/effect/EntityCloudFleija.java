package com.hbm.entity.effect;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityCloudFleija extends Entity {
	
	public int maxAge = 100;
	public int age;
    public float scale = 0;

	public EntityCloudFleija(World p_i1582_1_) {
		super(p_i1582_1_);
		setSize(1, 4);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
		this.age = 0;
    	this.scale = 0;
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(16, Integer.valueOf(0));
	}

    @Override
	@SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float p_70070_1_)
    {
        return 15728880;
    }

    @Override
	public float getBrightness(float p_70013_1_)
    {
        return 1.0F;
    }

	public EntityCloudFleija(World p_i1582_1_, int maxAge) {
		super(p_i1582_1_);
		setSize(20, 40);
		this.isImmuneToFire = true;
		setMaxAge(maxAge);
	}

    @Override
	public void onUpdate() {
        this.age++;
        this.worldObj.spawnEntityInWorld(new EntityLightningBolt(this.worldObj, this.posX, this.posY + 200, this.posZ));
        
        if(this.age >= getMaxAge())
        {
    		this.age = 0;
        	setDead();
        }
        
        this.scale++;
    }

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		this.age = p_70037_1_.getShort("age");
		this.scale = p_70037_1_.getShort("scale");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		p_70014_1_.setShort("age", (short)this.age);
		p_70014_1_.setShort("scale", (short)this.scale);
		
	}
	
	public void setMaxAge(int i) {
		this.dataWatcher.updateObject(16, Integer.valueOf(i));
	}
	
	public int getMaxAge() {
		return this.dataWatcher.getWatchableObjectInt(16);
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 25000;
    }
}
