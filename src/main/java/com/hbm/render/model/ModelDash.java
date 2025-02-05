//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2017 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model: ModelDash
// Model Creator: 
// Created on: 01.11.2017 - 20:07:57
// Last changed on: 01.11.2017 - 20:07:57

package com.hbm.render.model; //Path where the model is located

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelDash extends ModelBase //Same as Filename
{
	int textureX = 64;
	int textureY = 64;
	ModelRenderer[] bodyModel;

	public ModelDash() //Same as Filename
	{
	    this.textureWidth = 64;
	    this.textureHeight = 64;
		this.bodyModel = new ModelRenderer[16];
		this.bodyModel[0] = new ModelRenderer(this, 1, 1); // Box 0
		this.bodyModel[1] = new ModelRenderer(this, 25, 1); // Box 1
		this.bodyModel[2] = new ModelRenderer(this, 1, 9); // Box 2
		this.bodyModel[3] = new ModelRenderer(this, 33, 9); // Box 3
		this.bodyModel[4] = new ModelRenderer(this, 57, 1); // Box 4
		this.bodyModel[5] = new ModelRenderer(this, 1, 17); // Box 5
		this.bodyModel[6] = new ModelRenderer(this, 9, 17); // Box 6
		this.bodyModel[7] = new ModelRenderer(this, 17, 17); // Box 7
		this.bodyModel[8] = new ModelRenderer(this, 41, 17); // Box 8
		this.bodyModel[9] = new ModelRenderer(this, 1, 25); // Box 9
		this.bodyModel[10] = new ModelRenderer(this, 17, 25); // Box 10
		this.bodyModel[11] = new ModelRenderer(this, 41, 25); // Box 11
		this.bodyModel[12] = new ModelRenderer(this, 49, 25); // Box 12
		this.bodyModel[13] = new ModelRenderer(this, 1, 33); // Box 13
		this.bodyModel[14] = new ModelRenderer(this, 17, 33); // Box 14
		this.bodyModel[15] = new ModelRenderer(this, 25, 33); // Box 16

		this.bodyModel[0].addBox(0F, 0F, 0F, 7, 3, 3, 0F); // Box 0
		this.bodyModel[0].setRotationPoint(0F, 0F, -1.5F);

		this.bodyModel[1].addBox(0F, 0F, -1F, 12, 3, 2, 0F); // Box 1
		this.bodyModel[1].setRotationPoint(-12F, 1F, 0F);

		this.bodyModel[2].addBox(0F, 0F, -1F, 12, 3, 2, 0F); // Box 2
		this.bodyModel[2].setRotationPoint(-12F, 1F, 0F);
		this.bodyModel[2].rotateAngleX = 2.0943951F;

		this.bodyModel[3].addBox(0F, 0F, -1F, 12, 3, 2, 0F); // Box 3
		this.bodyModel[3].setRotationPoint(-12F, 1F, 0F);
		this.bodyModel[3].rotateAngleX = -2.0943951F;

		this.bodyModel[4].addBox(0F, 1.5F, -0.5F, 1, 1, 1, 0F); // Box 4
		this.bodyModel[4].setRotationPoint(-12.5F, 1F, 0F);

		this.bodyModel[5].addBox(0F, 1.5F, -0.5F, 1, 1, 1, 0F); // Box 5
		this.bodyModel[5].setRotationPoint(-12.5F, 1F, 0F);
		this.bodyModel[5].rotateAngleX = 2.0943951F;

		this.bodyModel[6].addBox(0F, 1.5F, -0.5F, 1, 1, 1, 0F); // Box 6
		this.bodyModel[6].setRotationPoint(-12.5F, 1F, 0F);
		this.bodyModel[6].rotateAngleX = -2.0943951F;

		this.bodyModel[7].addBox(0F, 0F, 0F, 7, 1, 2, 0F); // Box 7
		this.bodyModel[7].setRotationPoint(0F, -0.5F, -1F);

		this.bodyModel[8].addBox(0F, 0F, 0F, 7, 2, 2, 0F); // Box 8
		this.bodyModel[8].setRotationPoint(7F, 0.5F, -1F);

		this.bodyModel[9].addBox(0F, 0F, 0F, 2, 2, 2, 0F); // Box 9
		this.bodyModel[9].setRotationPoint(12F, 2.5F, -1F);

		this.bodyModel[10].addBox(-6F, -2F, 0F, 6, 2, 2, 0F); // Box 10
		this.bodyModel[10].setRotationPoint(12F, 4.5F, -1F);
		this.bodyModel[10].rotateAngleZ = 0.29670597F;

		this.bodyModel[11].addBox(0F, 0F, 0F, 1, 3, 1, 0F); // Box 11
		this.bodyModel[11].setRotationPoint(6F, 3F, -0.5F);
		this.bodyModel[11].rotateAngleZ = -0.26179939F;

		this.bodyModel[12].addBox(0F, 0F, 0F, 5, 2, 2, 0F); // Box 12
		this.bodyModel[12].setRotationPoint(0F, 3F, -1F);

		this.bodyModel[13].addBox(0F, 0F, 0F, 6, 1, 1, 0F); // Box 13
		this.bodyModel[13].setRotationPoint(2F, 5F, -0.5F);

		this.bodyModel[14].addBox(0F, 0F, 0F, 1, 4, 1, 0F); // Box 14
		this.bodyModel[14].setRotationPoint(0.5F, 1F, -1.5F);
		this.bodyModel[14].rotateAngleX = -0.61086524F;

		this.bodyModel[15].addBox(0F, 0F, 0F, 3, 1, 1, 0F); // Box 16
		this.bodyModel[15].setRotationPoint(3F, -1F, -0.5F);
		
		for(int i = 0; i < 16; i++)
		{
			this.bodyModel[i].setTextureSize(this.textureX, this.textureY);
			this.bodyModel[i].mirror = true;
		}
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
	    super.render(entity, f, f1, f2, f3, f4, f5);
	    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		for(int i = 0; i < 16; i++)
		{
			this.bodyModel[i].render(f5);
		}
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
	    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
}