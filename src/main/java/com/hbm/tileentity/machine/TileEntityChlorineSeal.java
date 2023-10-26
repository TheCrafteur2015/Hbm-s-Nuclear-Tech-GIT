package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;

import net.minecraft.tileentity.TileEntity;

public class TileEntityChlorineSeal extends TileEntity {
	
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote && this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord))
			spread(this.xCoord, this.yCoord, this.zCoord, 0);
	}
	
	private void spread(int x, int y, int z, int index) {
		
		if(index > 50)
			return;
		
		if(this.worldObj.getBlock(x, y, z).isReplaceable(this.worldObj, x, y, z))
			this.worldObj.setBlock(x, y, z, ModBlocks.chlorine_gas);
		
		if(this.worldObj.getBlock(x, y, z) != ModBlocks.chlorine_gas && this.worldObj.getBlock(x, y, z) != ModBlocks.vent_chlorine_seal)
			return;
		
		switch(this.worldObj.rand.nextInt(6)) {
		case 0:
			spread(x + 1, y, z, index + 1);
			break;
		case 1:
			spread(x - 1, y, z, index + 1);
			break;
		case 2:
			spread(x, y + 1, z, index + 1);
			break;
		case 3:
			spread(x, y - 1, z, index + 1);
			break;
		case 4:
			spread(x, y, z + 1, index + 1);
			break;
		case 5:
			spread(x, y, z - 1, index + 1);
			break;
		}
	}
}
