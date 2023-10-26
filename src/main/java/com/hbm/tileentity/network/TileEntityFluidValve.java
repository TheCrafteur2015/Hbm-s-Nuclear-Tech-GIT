package com.hbm.tileentity.network;

import api.hbm.fluid.PipeNet;

public class TileEntityFluidValve extends TileEntityPipeBaseNT {
	
	@Override
	public boolean canUpdate() {
		return this.worldObj != null && getBlockMetadata() == 1 && super.canUpdate();
	}

	public void updateState() {
		
		if(getBlockMetadata() == 0 && this.network != null) {
			this.network.destroy();
			this.network = null;
		}
		
		if(getBlockMetadata() == 1) {
			connect();
			
			if(getPipeNet(this.type) == null) {
				new PipeNet(this.type).joinLink(this);
			}
		}
	}
}
