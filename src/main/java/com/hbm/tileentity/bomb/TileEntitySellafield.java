package com.hbm.tileentity.bomb;

import java.util.List;

import com.hbm.lib.ModDamageSource;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntitySellafield extends TileEntity {
		
	public double radius = 7.5D;
	
	@Override
	public void updateEntity() {
		
		List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(this.xCoord + 0.5D - this.radius, this.yCoord + 0.5D - this.radius, this.zCoord + 0.5D - this.radius, this.xCoord + 0.5D + this.radius, this.yCoord + 0.5D + this.radius, this.zCoord + 0.5D + this.radius));
		
		for(Object o : list) {
			
			if(o instanceof EntityLivingBase) {
				
				EntityLivingBase entity = (EntityLivingBase) o;
				
				if(Math.sqrt(Math.pow(this.xCoord + 0.5D - entity.posX, 2) + Math.pow(this.yCoord + 0.5D - entity.posY, 2) + Math.pow(this.zCoord + 0.5D - entity.posZ, 2)) <= this.radius) {
					//Library.applyRadiation(entity, 5 * 60, 100, 4 * 60, 75);
					entity.attackEntityFrom(ModDamageSource.radiation, entity.getHealth() * 0.5F);
				}
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.radius = nbt.getDouble("radius");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setDouble("radius", this.radius);
	}

}
