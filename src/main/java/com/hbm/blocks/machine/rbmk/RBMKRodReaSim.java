package com.hbm.blocks.machine.rbmk;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.TileEntityProxyInventory;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKRodReaSim;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class RBMKRodReaSim extends RBMKRod {
	
	public RBMKRodReaSim(boolean moderated) {
		super(moderated);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= BlockDummyable.offset)
			return new TileEntityRBMKRodReaSim();
		if(hasExtra(meta))
			return new TileEntityProxyInventory();
		return null;
	}
}