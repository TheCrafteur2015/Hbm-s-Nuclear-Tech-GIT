package com.hbm.handler.nei;

import java.awt.Rectangle;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.gui.GUIMachineCoker;
import com.hbm.inventory.recipes.CokerRecipes;

public class CokingHandler extends NEIUniversalHandler {

	public CokingHandler() {
		super("Coking", ModBlocks.machine_coker, CokerRecipes.getRecipes());
	}

	@Override
	public String getKey() {
		return "ntmCoking";
	}
	
	@Override
	public void loadTransferRects() {
		super.loadTransferRects();
		this.transferRectsGui.add(new RecipeTransferRect(new Rectangle(55, 15, 36, 18), "ntmCoking"));
		this.guiGui.add(GUIMachineCoker.class);
		RecipeTransferRectHandler.registerRectsToGuis(this.guiGui, this.transferRectsGui);
	}
}
