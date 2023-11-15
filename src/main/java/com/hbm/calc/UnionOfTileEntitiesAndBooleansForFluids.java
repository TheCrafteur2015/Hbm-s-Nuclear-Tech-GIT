package com.hbm.calc;

import com.hbm.interfaces.IFluidSource;

@SuppressWarnings("deprecation")
public class UnionOfTileEntitiesAndBooleansForFluids {
	
	public IFluidSource source;
	public boolean ticked = false;
	
	public UnionOfTileEntitiesAndBooleansForFluids(IFluidSource tileentity, boolean bool) {
		this.source = tileentity;
		this.ticked = bool;
	}
	
}