package com.hbm.tileentity.bomb;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.Landmine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityLandmine extends TileEntity {
	
	private boolean isPrimed = false;

	
	@Override
	public void updateEntity() {

		if(!this.worldObj.isRemote) {
			Block block = this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord);
			double range = 1;
			double height = 1;

			if (block == ModBlocks.mine_ap) {
				range = 1.5D;
			}
			if (block == ModBlocks.mine_he) {
				range = 2;
				height = 5;
			}
			if (block == ModBlocks.mine_shrap) {
				range = 1.5D;
			}
			if (block == ModBlocks.mine_fat) {
				range = 2.5D;
			}
			
			if(!this.isPrimed)
				range *= 2;
	
			List<Object> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(null,
					AxisAlignedBB.getBoundingBox(this.xCoord - range, this.yCoord - height, this.zCoord - range, this.xCoord + range + 1, this.yCoord + height, this.zCoord + range + 1));
	
			boolean flag = false;
			for(Object o : list) {

				if(o instanceof EntityLivingBase) {

					flag = true;

					if(this.isPrimed) {
						//why did i do it like that?
						((Landmine) block).explode(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
					}

					return;
				}
			}

			if(!this.isPrimed && !flag) {

				this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:weapon.fstbmbStart", 3.0F, 1.0F);
				this.isPrimed = true;
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.isPrimed = nbt.getBoolean("primed");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setBoolean("primed", this.isPrimed);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
}
