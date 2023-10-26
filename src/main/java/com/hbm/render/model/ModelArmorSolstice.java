package com.hbm.render.model;

import com.hbm.main.ResourceManager;
import com.hbm.render.loader.ModelRendererObj;

import net.minecraft.util.ResourceLocation;

public class ModelArmorSolstice extends ModelArmorWings {
	
	public ModelArmorSolstice() {
		super(0);

		this.wingLB = new ModelRendererObj(ResourceManager.armor_solstice, "WingLB");
		this.wingLT = new ModelRendererObj(ResourceManager.armor_solstice, "WingLT");
		this.wingRB = new ModelRendererObj(ResourceManager.armor_solstice, "WingRB");
		this.wingRT = new ModelRendererObj(ResourceManager.armor_solstice, "WingRT");
	}
	
	@Override
	protected boolean doesRotateZ() {
		return false;
	}
	
	@Override
	protected ResourceLocation getTexture() {
		return ResourceManager.wings_solstice;
	}
}
