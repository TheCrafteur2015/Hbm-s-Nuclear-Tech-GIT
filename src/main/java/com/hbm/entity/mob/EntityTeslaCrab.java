package com.hbm.entity.mob;

import java.util.ArrayList;
import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.TileEntityTesla;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityTeslaCrab extends EntityCyberCrab {
	
	public List<double[]> targets = new ArrayList<>();

	public EntityTeslaCrab(World p_i1733_1_) {
		super(p_i1733_1_);
        setSize(0.75F, 1.25F);
        this.ignoreFrustumCheck = true;
	}

    @Override
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5F);
    }
    
    @Override
	public void onLivingUpdate() {
    	
    	this.targets = TileEntityTesla.zap(this.worldObj, this.posX, this.posY + 1, this.posZ, 3, this);
    	
        super.onLivingUpdate();
    }

    @Override
	protected Item getDropItem()
    {
        return ModItems.wire_advanced_alloy;
    }

    @Override
	protected void dropRareDrop(int p_70600_1_) {
    	dropItem(ModItems.coil_copper, 1);
    }

}
