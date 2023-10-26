package com.hbm.entity.grenade;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGrenade;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityGrenadeIFNull extends EntityGrenadeBouncyBase {

    public EntityGrenadeIFNull(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityGrenadeIFNull(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    public EntityGrenadeIFNull(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }

    @Override
    public void explode() {
    	
        if (!this.worldObj.isRemote)
        {
            setDead();

    		for(int a = -3; a <= 3; a++)
        		for(int b = -3; b <= 3; b++)
            		for(int c = -3; c <= 3; c++)
            			this.worldObj.setBlockToAir((int)this.posX + a, (int)this.posY + b, (int)this.posZ + c);
    		
    		List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getBoundingBox((int)this.posX + 0.5 - 3, (int)this.posY + 0.5 - 3, (int)this.posZ + 0.5 - 3, (int)this.posX + 0.5 + 3, (int)this.posY + 0.5 + 3, (int)this.posZ + 0.5 + 3));
    		
    		for(Object o : list) {
    			if(o instanceof EntityLivingBase) {
    				EntityLivingBase e = (EntityLivingBase)o;
    				
    				e.setHealth(0);
    			} else if(o instanceof Entity) {
    				Entity e = (Entity)o;
    				
    				e.setDead();
    			}
    		}
        }
    }

	@Override
	protected int getMaxTimer() {
		return ItemGrenade.getFuseTicks(ModItems.grenade_if_null);
	}

	@Override
	protected double getBounceMod() {
		return 0.25D;
	}
}
