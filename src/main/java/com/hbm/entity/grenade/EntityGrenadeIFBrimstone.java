package com.hbm.entity.grenade;

import com.hbm.entity.projectile.EntityBullet;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGrenade;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityGrenadeIFBrimstone extends EntityGrenadeBouncyBase {

    public EntityGrenadeIFBrimstone(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityGrenadeIFBrimstone(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    public EntityGrenadeIFBrimstone(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }
    
    @Override
    public void onUpdate() {
    	super.onUpdate();
    	
    	if(this.timer > (getMaxTimer() * 0.65)) {
    		
    		if(!this.worldObj.isRemote) {
	    		EntityBullet fragment;
	
	    		fragment = new EntityBullet(this.worldObj, (EntityPlayer) this.thrower, 3.0F, 35, 45, false, "tauDay");
	    		fragment.setDamage(this.rand.nextInt(301) + 100);
	
	    		fragment.motionX = this.rand.nextGaussian();
	    		fragment.motionY = this.rand.nextGaussian();
	    		fragment.motionZ = this.rand.nextGaussian();
	    		fragment.shootingEntity = this.thrower;

	    		fragment.posX = this.posX;
	    		fragment.posY = this.posY;
	    		fragment.posZ = this.posZ;
	
	    		fragment.setIsCritical(true);
	
	    		this.worldObj.spawnEntityInWorld(fragment);
    		}
    	}
    }

    @Override
    public void explode() {
    	
        if (!this.worldObj.isRemote)
        {
            setDead();
    		
    		this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 5, false, false);
    		
    		for(int i = 0; i < 100; i++) {
	    		EntityBullet fragment;
	
	    		fragment = new EntityBullet(this.worldObj, (EntityPlayer) this.thrower, 3.0F, 35, 45, false, "tauDay");
	    		fragment.setDamage(this.rand.nextInt(301) + 100);
	
	    		fragment.motionX = this.rand.nextGaussian() * 0.25;
	    		fragment.motionY = this.rand.nextGaussian() * 0.25;
	    		fragment.motionZ = this.rand.nextGaussian() * 0.25;
	    		fragment.shootingEntity = this.thrower;

	    		fragment.posX = this.posX;
	    		fragment.posY = this.posY;
	    		fragment.posZ = this.posZ;
	
	    		fragment.setIsCritical(true);
	
	    		this.worldObj.spawnEntityInWorld(fragment);
    		}
        }
    }

	@Override
	protected int getMaxTimer() {
		return ItemGrenade.getFuseTicks(ModItems.grenade_if_brimstone);
	}

	@Override
	protected double getBounceMod() {
		return 0.25D;
	}
}
