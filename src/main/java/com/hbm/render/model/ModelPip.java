//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2017 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model: Pip
// Model Creator:
// Created on:09.12.2017 - 12:48:52
// Last changed on: 09.12.2017 - 12:48:52

package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPip extends ModelBase
{

	public ModelRenderer pipModel[];
	
	int textureX = 128;
	int textureY = 128;

	public ModelPip()
	{
	    this.textureWidth = 128;
	    this.textureHeight = 128;
		this.pipModel = new ModelRenderer[39];
		this.pipModel[0] = new ModelRenderer(this, 1, 1); // Box 0
		this.pipModel[1] = new ModelRenderer(this, 49, 1); // Box 1
		this.pipModel[2] = new ModelRenderer(this, 97, 1); // Box 2
		this.pipModel[3] = new ModelRenderer(this, 1, 9); // Box 3
		this.pipModel[4] = new ModelRenderer(this, 25, 9); // Box 4
		this.pipModel[5] = new ModelRenderer(this, 49, 9); // Box 5
		this.pipModel[6] = new ModelRenderer(this, 73, 9); // Box 6
		this.pipModel[7] = new ModelRenderer(this, 81, 17); // Box 7
		this.pipModel[8] = new ModelRenderer(this, 97, 17); // Box 10
		this.pipModel[9] = new ModelRenderer(this, 1, 25); // Box 12
		this.pipModel[10] = new ModelRenderer(this, 1, 9); // Box 14
		this.pipModel[11] = new ModelRenderer(this, 17, 25); // Box 15
		this.pipModel[12] = new ModelRenderer(this, 41, 25); // Box 16
		this.pipModel[13] = new ModelRenderer(this, 49, 9); // Box 17
		this.pipModel[14] = new ModelRenderer(this, 113, 17); // Box 18
		this.pipModel[15] = new ModelRenderer(this, 73, 17); // Box 19
		this.pipModel[16] = new ModelRenderer(this, 65, 25); // Box 20
		this.pipModel[17] = new ModelRenderer(this, 113, 25); // Box 22
		this.pipModel[18] = new ModelRenderer(this, 65, 25); // Box 23
		this.pipModel[19] = new ModelRenderer(this, 1, 33); // Box 24
		this.pipModel[20] = new ModelRenderer(this, 81, 33); // Box 25
		this.pipModel[21] = new ModelRenderer(this, 1, 41); // Box 26
		this.pipModel[22] = new ModelRenderer(this, 89, 33); // Box 27
		this.pipModel[23] = new ModelRenderer(this, 105, 33); // Box 28
		this.pipModel[24] = new ModelRenderer(this, 33, 41); // Box 29
		this.pipModel[25] = new ModelRenderer(this, 49, 41); // Box 30
		this.pipModel[26] = new ModelRenderer(this, 65, 41); // Box 31
		this.pipModel[27] = new ModelRenderer(this, 105, 41); // Box 32
		this.pipModel[28] = new ModelRenderer(this, 1, 49); // Box 33
		this.pipModel[29] = new ModelRenderer(this, 17, 49); // Box 34
		this.pipModel[30] = new ModelRenderer(this, 33, 49); // Box 35
		this.pipModel[31] = new ModelRenderer(this, 57, 49); // Box 36
		this.pipModel[32] = new ModelRenderer(this, 81, 49); // Box 37
		this.pipModel[33] = new ModelRenderer(this, 105, 49); // Box 38
		this.pipModel[34] = new ModelRenderer(this, 1, 57); // Box 39
		this.pipModel[35] = new ModelRenderer(this, 89, 25); // Box 40
		this.pipModel[36] = new ModelRenderer(this, 97, 41); // Box 41
		this.pipModel[37] = new ModelRenderer(this, 49, 49); // Box 42
		this.pipModel[38] = new ModelRenderer(this, 73, 57); // Box 43

		this.pipModel[0].addBox(0F, 0F, 0F, 20, 3, 2, 0F); // Box 0
		this.pipModel[0].setRotationPoint(-20F, -5.5F, -1F);

		this.pipModel[1].addBox(0F, 0F, 0F, 20, 2, 3, 0F); // Box 1
		this.pipModel[1].setRotationPoint(-20F, -5F, -1.5F);

		this.pipModel[2].addBox(0F, 0F, 0F, 10, 8, 3, 0F); // Box 2
		this.pipModel[2].setRotationPoint(0F, -6F, -1.5F);

		this.pipModel[3].addBox(0F, 0F, 0F, 6, 5, 5, 0F); // Box 3
		this.pipModel[3].setRotationPoint(2F, -5F, -2.5F);

		this.pipModel[4].addBox(0F, 0F, 0F, 6, 6, 4, 0F); // Box 4
		this.pipModel[4].setRotationPoint(2F, -5.5F, -2F);

		this.pipModel[5].addBox(0F, 0F, 0F, 6, 4, 6, 0F); // Box 5
		this.pipModel[5].setRotationPoint(2F, -4.5F, -3F);

		this.pipModel[6].addBox(0F, 0F, 0F, 7, 2, 1, 0F); // Box 6
		this.pipModel[6].setRotationPoint(-7F, -2.5F, -0.5F);

		this.pipModel[7].addBox(-6F, 0F, 0F, 6, 3, 1, 0F); // Box 7
		this.pipModel[7].setRotationPoint(15F, -2.5F, -1.5F);
		this.pipModel[7].rotateAngleZ = 0.61086524F;

		this.pipModel[8].addBox(0F, 0F, 0F, 5, 5, 3, 0F); // Box 10
		this.pipModel[8].setRotationPoint(10F, -2.5F, -1.5F);

		this.pipModel[9].addBox(-6F, 0F, 0F, 6, 3, 1, 0F); // Box 12
		this.pipModel[9].setRotationPoint(15F, -2.5F, 0.5F);
		this.pipModel[9].rotateAngleZ = 0.61086524F;

		this.pipModel[10].addBox(-6F, 0F, 0F, 1, 3, 1, 0F); // Box 14
		this.pipModel[10].setRotationPoint(15F, -2.5F, -0.5F);
		this.pipModel[10].rotateAngleZ = 0.61086524F;

		this.pipModel[11].addBox(0F, 0F, 0F, 6, 6, 4, 0F); // Box 15
		this.pipModel[11].setRotationPoint(12F, 4F, -2F);

		this.pipModel[12].addBox(-5F, -7F, 0F, 5, 7, 4, 0F); // Box 16
		this.pipModel[12].setRotationPoint(18F, 4F, -2F);
		this.pipModel[12].rotateAngleZ = -0.52359878F;

		this.pipModel[13].addBox(0F, -2F, 0F, 1, 2, 1, 0F); // Box 17
		this.pipModel[13].setRotationPoint(12F, -2.5F, -0.5F);
		this.pipModel[13].rotateAngleZ = 0.34906585F;

		this.pipModel[14].addBox(-0.5F, -3F, 0F, 3, 1, 1, 0F); // Box 18
		this.pipModel[14].setRotationPoint(12F, -2.5F, -0.5F);
		this.pipModel[14].rotateAngleZ = 0.34906585F;

		this.pipModel[15].addBox(0F, 0F, 0F, 1, 3, 5, 0F); // Box 19
		this.pipModel[15].setRotationPoint(8F, -4F, -2.5F);

		this.pipModel[16].addBox(0F, 0F, 0F, 1, 3, 5, 0F); // Box 20
		this.pipModel[16].setRotationPoint(1F, -4F, -2.5F);

		this.pipModel[17].addBox(0F, 0F, 0F, 5, 3, 2, 0F); // Box 22
		this.pipModel[17].setRotationPoint(5F, 2F, -1F);

		this.pipModel[18].addBox(-1F, -0.5F, 0F, 1, 3, 1, 0F); // Box 23
		this.pipModel[18].setRotationPoint(9F, 2F, -0.5F);
		this.pipModel[18].rotateAngleZ = 0.43633231F;

		this.pipModel[19].addBox(0F, 0F, 0F, 5, 1, 2, 0F); // Box 24
		this.pipModel[19].setRotationPoint(-19.5F, -6.5F, -1F);

		this.pipModel[20].addBox(0F, 0F, 0F, 4, 1, 1, 0F); // Box 25
		this.pipModel[20].setRotationPoint(-19F, -7.5F, -0.5F);
		this.pipModel[20].rotateAngleZ = 0.26179939F;

		this.pipModel[21].addBox(0F, 0F, 0F, 15, 1, 2, 0F); // Box 26
		this.pipModel[21].setRotationPoint(-4F, -7F, -1F);

		this.pipModel[22].addBox(1F, 0F, 0F, 3, 4, 3, 0F); // Box 27
		this.pipModel[22].setRotationPoint(-3F, -12F, -1.5F);

		this.pipModel[23].addBox(1F, 0F, 0F, 3, 3, 4, 0F); // Box 28
		this.pipModel[23].setRotationPoint(-3F, -11.5F, -2F);

		this.pipModel[24].addBox(0F, 0F, 0F, 3, 3, 4, 0F); // Box 29
		this.pipModel[24].setRotationPoint(6F, -11.5F, -2F);

		this.pipModel[25].addBox(0F, 0F, 0F, 3, 4, 3, 0F); // Box 30
		this.pipModel[25].setRotationPoint(6F, -12F, -1.5F);

		this.pipModel[26].addBox(0F, 0F, 0F, 13, 3, 3, 0F); // Box 31
		this.pipModel[26].setRotationPoint(-3F, -11.5F, -1.5F);

		this.pipModel[27].addBox(0F, 0F, 0F, 3, 4, 3, 0F); // Box 32
		this.pipModel[27].setRotationPoint(-6F, -12F, -1.5F);

		this.pipModel[28].addBox(0F, 0F, 0F, 3, 3, 4, 0F); // Box 33
		this.pipModel[28].setRotationPoint(-6F, -11.5F, -2F);

		this.pipModel[29].addBox(0F, 0F, 0F, 4, 5, 3, 0F); // Box 34
		this.pipModel[29].setRotationPoint(-10F, -12.5F, -1.5F);

		this.pipModel[30].addBox(0F, 0F, 0F, 4, 3, 5, 0F); // Box 35
		this.pipModel[30].setRotationPoint(-10F, -11.5F, -2.5F);

		this.pipModel[31].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Box 36
		this.pipModel[31].setRotationPoint(-10F, -12F, -2F);

		this.pipModel[32].addBox(0F, 0F, 0F, 5, 3, 4, 0F); // Box 37
		this.pipModel[32].setRotationPoint(10F, -11.5F, -2F);

		this.pipModel[33].addBox(0F, 0F, 0F, 5, 4, 3, 0F); // Box 38
		this.pipModel[33].setRotationPoint(10F, -12F, -1.5F);

		this.pipModel[34].addBox(0F, 0F, 0F, 3, 4, 4, 0F); // Box 39
		this.pipModel[34].setRotationPoint(2F, -12F, -2F);

		this.pipModel[35].addBox(0F, 0F, 0F, 3, 1, 1, 0F); // Box 40
		this.pipModel[35].setRotationPoint(-2F, -8F, -0.5F);

		this.pipModel[36].addBox(0F, 0F, 0F, 3, 1, 1, 0F); // Box 41
		this.pipModel[36].setRotationPoint(6F, -8F, -0.5F);

		this.pipModel[37].addBox(0F, 0F, 0F, 2, 1, 2, 0F); // Box 42
		this.pipModel[37].setRotationPoint(2.5F, -12.5F, -1F);

		this.pipModel[38].addBox(0F, 0F, 0F, 3, 1, 3, 0F); // Box 43
		this.pipModel[38].setRotationPoint(2F, -13.5F, -1.5F);

		
		for(int i = 0; i < 39; i++)
		{
			this.pipModel[i].setTextureSize(this.textureX, this.textureY);
			this.pipModel[i].mirror = true;
		}

	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
	    super.render(entity, f, f1, f2, f3, f4, f5);
	    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		GL11.glDisable(GL11.GL_CULL_FACE);
		for(int i = 0; i < 39; i++)
		{
			this.pipModel[i].render(f5);
		}
		GL11.glEnable(GL11.GL_CULL_FACE);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
	    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
}