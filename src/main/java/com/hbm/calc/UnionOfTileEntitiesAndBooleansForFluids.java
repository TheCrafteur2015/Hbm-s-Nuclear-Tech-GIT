package com.hbm.calc;

import com.hbm.interfaces.IFluidSource;

public class UnionOfTileEntitiesAndBooleansForFluids {
	
	public UnionOfTileEntitiesAndBooleansForFluids(IFluidSource tileentity, boolean bool)
	{
		this.source = tileentity;
		this.ticked = bool;
	}

	public IFluidSource source;
	public boolean ticked = false;
}
