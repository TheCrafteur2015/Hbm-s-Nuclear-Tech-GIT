//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2017 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model: Pylon
// Model Creator:
// Created on:13.06.2017 - 11:17:46
// Last changed on: 13.06.2017 - 11:17:46

package com.hbm.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPylon extends ModelBase
{
	int textureX = 64;
	int textureY = 128;

	public ModelRenderer pylonModel[];

	public ModelPylon()
	{
	    this.textureWidth = 64;
	    this.textureHeight = 128;
		this.pylonModel = new ModelRenderer[4];
		this.pylonModel[0] = new ModelRenderer(this, 0, 96); // Box 0
		this.pylonModel[1] = new ModelRenderer(this, 1, 1); // Box 1
		this.pylonModel[2] = new ModelRenderer(this, 24, 1); // Box 2
		this.pylonModel[3] = new ModelRenderer(this, 25, 17); // Box 3

		this.pylonModel[0].addBox(0F, 0F, 0F, 16, 16, 16, 0F); // Box 0
		this.pylonModel[0].setRotationPoint(-8F, -6F, -8F);

		this.pylonModel[1].addBox(0F, 0F, 0F, 4, 73, 4, 0F); // Box 1
		this.pylonModel[1].setRotationPoint(-2F, -79F, -2F);

		this.pylonModel[2].addBox(0F, 0F, 0F, 6, 4, 6, 0F); // Box 2
		this.pylonModel[2].setRotationPoint(-3F, -74F, -3F);

		this.pylonModel[3].addBox(0F, 0F, 0F, 6, 2, 6, 0F); // Box 3
		this.pylonModel[3].setRotationPoint(-3F, -78F, -3F);


		for(int i = 0; i < this.pylonModel.length; i++) {
			this.pylonModel[i].setTextureSize(this.textureX, this.textureY);
			this.pylonModel[i].mirror = true;
		}
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		for(int i = 0; i < 4; i++)
		{
			this.pylonModel[i].render(f5);
		}
	}

	public void renderAll(float f5)
	{
		for(int i = 0; i < 4; i++)
		{
			this.pylonModel[i].render(f5);
		}
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
	    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
}