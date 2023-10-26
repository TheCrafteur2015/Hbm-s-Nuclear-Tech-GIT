package com.hbm.tileentity.machine.pile;

import com.hbm.blocks.ModBlocks;

public class TileEntityPileSource extends TileEntityPileBase {

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			int n = getBlockType() == ModBlocks.block_graphite_source ? 1 : 2;
			
			for(int i = 0; i < 12; i++) {
				castRay(n, 5);
			}
		}
	}
}
