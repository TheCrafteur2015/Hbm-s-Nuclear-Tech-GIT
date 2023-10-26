package com.hbm.entity.grenade;

import com.hbm.explosion.ExplosionLarge;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityGrenadeBreach extends EntityGrenadeBase {

    public EntityGrenadeBreach(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityGrenadeBreach(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    public EntityGrenadeBreach(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }

    @Override
    public void explode() {
    	
        if (!this.worldObj.isRemote)
        {
        	if(this.rand.nextInt(10) == 0)
        		setDead();
        	ExplosionLarge.explode(this.worldObj, this.posX, this.posY, this.posZ, 2.5F, false, false, false);
        }
    }
}
