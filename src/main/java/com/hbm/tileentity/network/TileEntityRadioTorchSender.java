package com.hbm.tileentity.network;

import net.minecraft.block.Block;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRadioTorchSender extends TileEntityRadioTorchBase {

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata()).getOpposite();
			int input = this.worldObj.getIndirectPowerLevelTo(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ, getBlockMetadata());
			
			Block b = this.worldObj.getBlock(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);
			
			if(b.hasComparatorInputOverride()) {
				input = b.getComparatorInputOverride(this.worldObj, this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ, dir.getOpposite().ordinal());
			}
			
			boolean shouldSend = this.polling;
			
			if(input != this.lastState) {
				markDirty();
				this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
				this.lastState = input;
				shouldSend = true;
			}

			if(shouldSend && !this.channel.isEmpty()) {
				RTTYSystem.broadcast(this.worldObj, this.channel, this.customMap ? this.mapping[input] : (input + ""));
			}
		}
		
		super.updateEntity();
	}
}
