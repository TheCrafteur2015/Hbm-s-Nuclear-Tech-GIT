package com.hbm.calc;

import com.hbm.interfaces.IOilSource;

public class UnionOfTileEntitiesAndBooleansForOil {
	
	public UnionOfTileEntitiesAndBooleansForOil(IOilSource tileentity, boolean bool)
	{
		this.source = tileentity;
		this.ticked = bool;
	}

	public IOilSource source;
	public boolean ticked = false;
}
