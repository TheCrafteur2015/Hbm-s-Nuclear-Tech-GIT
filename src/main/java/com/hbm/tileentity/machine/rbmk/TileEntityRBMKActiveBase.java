package com.hbm.tileentity.machine.rbmk;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Base class for all RBMK components that have a GUI
 * @author hbm
 *
 */
public abstract class TileEntityRBMKActiveBase extends TileEntityRBMKBase {
	
	public abstract String getName();

	public boolean isUseableByPlayer(EntityPlayer player) {
		if(this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this) {
			return false;
		} else {
			return player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 128;
		}
	}
}
