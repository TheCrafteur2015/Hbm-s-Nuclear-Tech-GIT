//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2017 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model: Crab
// Model Creator:
// Created on:07.06.2017 - 08:57:57
// Last changed on: 07.06.2017 - 08:57:57

package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelCrab extends ModelBase {
	int textureX = 64;
	int textureY = 32;

	public ModelRenderer modelcrabModel[];

	public ModelCrab() {
		this.textureWidth = this.textureX;
		this.textureHeight = this.textureY;
		this.modelcrabModel = new ModelRenderer[20];
		this.modelcrabModel[0] = new ModelRenderer(this, 1, 1); // Box 1
		this.modelcrabModel[1] = new ModelRenderer(this, 17, 1); // Box 2
		this.modelcrabModel[2] = new ModelRenderer(this, 33, 1); // Box 3
		this.modelcrabModel[3] = new ModelRenderer(this, 49, 1); // Box 4
		this.modelcrabModel[4] = new ModelRenderer(this, 1, 9); // Box 5
		this.modelcrabModel[5] = new ModelRenderer(this, 25, 9); // Box 6
		this.modelcrabModel[6] = new ModelRenderer(this, 41, 9); // Box 7
		this.modelcrabModel[7] = new ModelRenderer(this, 1, 17); // Box 8
		this.modelcrabModel[8] = new ModelRenderer(this, 17, 17); // Box 9
		this.modelcrabModel[9] = new ModelRenderer(this, 57, 9); // Box 10
		this.modelcrabModel[10] = new ModelRenderer(this, 33, 17); // Box 11
		this.modelcrabModel[11] = new ModelRenderer(this, 41, 17); // Box 12
		this.modelcrabModel[12] = new ModelRenderer(this, 49, 17); // Box 13
		this.modelcrabModel[13] = new ModelRenderer(this, 17, 1); // Box 14
		this.modelcrabModel[14] = new ModelRenderer(this, 33, 9); // Box 15
		this.modelcrabModel[15] = new ModelRenderer(this, 49, 9); // Box 16
		this.modelcrabModel[16] = new ModelRenderer(this, 9, 17); // Box 17
		this.modelcrabModel[17] = new ModelRenderer(this, 1, 25); // Box 18
		this.modelcrabModel[18] = new ModelRenderer(this, 17, 25); // Box 19
		this.modelcrabModel[19] = new ModelRenderer(this, 33, 25); // Box 20

		this.modelcrabModel[0].addBox(0F, 0F, 0F, 4, 1, 4, 0F); // Box 1
		this.modelcrabModel[0].setRotationPoint(-2F, -3F, -2F);

		this.modelcrabModel[1].addBox(0F, 0F, 0F, 4, 1, 6, 0F); // Box 2
		this.modelcrabModel[1].setRotationPoint(-2F, -4F, -3F);

		this.modelcrabModel[2].addBox(0F, 0F, 0F, 3, 1, 3, 0F); // Box 3
		this.modelcrabModel[2].setRotationPoint(-1.5F, -5F, -1.5F);

		this.modelcrabModel[3].addBox(0F, 0F, 0F, 4, 1, 2, 0F); // Box 4
		this.modelcrabModel[3].setRotationPoint(-2F, -4.5F, -1F);

		this.modelcrabModel[4].addBox(0F, 0F, 0F, 6, 1, 4, 0F); // Box 5
		this.modelcrabModel[4].setRotationPoint(-3F, -4F, -2F);

		this.modelcrabModel[5].addBox(-0.5F, 0F, 2F, 1, 1, 3, 0F); // Leg 6
		this.modelcrabModel[5].setRotationPoint(0F, -3F, 0F);
		this.modelcrabModel[5].rotateAngleX = -0.17453293F;
		this.modelcrabModel[5].rotateAngleY = 0.78539816F;
		this.modelcrabModel[10].addBox(-0.5F, 1F, 4F, 1, 3, 1, 0F); // Foot 11
		this.modelcrabModel[10].setRotationPoint(0F, -3F, 0F);
		this.modelcrabModel[10].rotateAngleX = 0.17453293F;
		this.modelcrabModel[10].rotateAngleY = 0.78539816F;

		this.modelcrabModel[6].addBox(-0.5F, 0F, 2F, 1, 1, 3, 0F); // Leg 7
		this.modelcrabModel[6].setRotationPoint(0F, -3F, 0F);
		this.modelcrabModel[6].rotateAngleX = -0.17453293F;
		this.modelcrabModel[6].rotateAngleY = -0.78539816F;
		this.modelcrabModel[9].addBox(-0.5F, 1F, 4F, 1, 3, 1, 0F); // Foot 10
		this.modelcrabModel[9].setRotationPoint(0F, -3F, 0F);
		this.modelcrabModel[9].rotateAngleX = 0.17453293F;
		this.modelcrabModel[9].rotateAngleY = -0.78539816F;

		this.modelcrabModel[7].addBox(-0.5F, 0F, 2F, 1, 1, 3, 0F); // Leg 8
		this.modelcrabModel[7].setRotationPoint(0F, -3F, 0F);
		this.modelcrabModel[7].rotateAngleX = -0.17453293F;
		this.modelcrabModel[7].rotateAngleY = -2.35619449F;
		this.modelcrabModel[11].addBox(-0.5F, 1F, 4F, 1, 3, 1, 0F); // Foot 12
		this.modelcrabModel[11].setRotationPoint(0F, -3F, 0F);
		this.modelcrabModel[11].rotateAngleX = 0.17453293F;
		this.modelcrabModel[11].rotateAngleY = -2.35619449F;

		this.modelcrabModel[8].addBox(-0.5F, 0F, 2F, 1, 1, 3, 0F); // Leg 9
		this.modelcrabModel[8].setRotationPoint(0F, -3F, 0F);
		this.modelcrabModel[8].rotateAngleX = -0.17453293F;
		this.modelcrabModel[8].rotateAngleY = 2.35619449F;
		this.modelcrabModel[12].addBox(-0.5F, 1F, 4F, 1, 3, 1, 0F); // Foot 13
		this.modelcrabModel[12].setRotationPoint(0F, -3F, 0F);
		this.modelcrabModel[12].rotateAngleX = 0.17453293F;
		this.modelcrabModel[12].rotateAngleY = 2.35619449F;

		this.modelcrabModel[13].addBox(-0.5F, 0F, 1.5F, 1, 1, 1, 0F); // Fang 14
		this.modelcrabModel[13].setRotationPoint(0F, -3F, 0F);
		this.modelcrabModel[13].rotateAngleX = -0.43633231F;
		this.modelcrabModel[13].rotateAngleY = -0.6981317F;

		this.modelcrabModel[14].addBox(-0.5F, 0F, 1.5F, 1, 1, 1, 0F); // Fang 15
		this.modelcrabModel[14].setRotationPoint(0F, -3F, 0F);
		this.modelcrabModel[14].rotateAngleX = -0.43633231F;
		this.modelcrabModel[14].rotateAngleY = 0.87266463F;

		this.modelcrabModel[15].addBox(-0.5F, 0F, 1.5F, 1, 1, 1, 0F); // Fang 16
		this.modelcrabModel[15].setRotationPoint(0F, -3F, 0F);
		this.modelcrabModel[15].rotateAngleX = -0.43633231F;
		this.modelcrabModel[15].rotateAngleY = -2.26892803F;

		this.modelcrabModel[16].addBox(-0.5F, 0F, 1.5F, 1, 1, 1, 0F); // Fang 17
		this.modelcrabModel[16].setRotationPoint(0F, -3F, 0F);
		this.modelcrabModel[16].rotateAngleX = -0.43633231F;
		this.modelcrabModel[16].rotateAngleY = 2.44346095F;

		this.modelcrabModel[17].addBox(0F, 0F, 0F, 2, 1, 4, 0F); // Box 18
		this.modelcrabModel[17].setRotationPoint(-1F, -4.5F, -2F);

		this.modelcrabModel[18].addBox(0F, 0F, 0F, 5, 1, 3, 0F); // Box 19
		this.modelcrabModel[18].setRotationPoint(-2.5F, -3.5F, -1.5F);

		this.modelcrabModel[19].addBox(0F, 0F, 0F, 3, 1, 5, 0F); // Box 20
		this.modelcrabModel[19].setRotationPoint(-1.5F, -3.5F, -2.5F);

		for (int i = 0; i < 20; i++) {
			this.modelcrabModel[i].setTextureSize(this.textureX, this.textureY);
			this.modelcrabModel[i].mirror = true;
		}

	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		renderAll(f5);
	}

	public void renderAll(float f5) {
		GL11.glPushMatrix();
		GL11.glTranslatef(0, 1.5F, 0);
		GL11.glRotatef(-90, 0, 1, 0);
		for (int i = 0; i < 20; i++) {
			this.modelcrabModel[i].render(f5);
		}
		GL11.glPopMatrix();
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		
		this.modelcrabModel[10].rotateAngleY = 0.78539816F;
		this.modelcrabModel[9].rotateAngleY = -0.78539816F;
		this.modelcrabModel[11].rotateAngleY = -2.35619449F;
		this.modelcrabModel[12].rotateAngleY = 2.35619449F;
		this.modelcrabModel[5].rotateAngleY = this.modelcrabModel[10].rotateAngleY;
		this.modelcrabModel[6].rotateAngleY = this.modelcrabModel[9].rotateAngleY;
		this.modelcrabModel[7].rotateAngleY = this.modelcrabModel[11].rotateAngleY;
		this.modelcrabModel[8].rotateAngleY = this.modelcrabModel[12].rotateAngleY;
		float f9 = -(MathHelper.cos(f * 0.6662F * 2.0F + 0.0F) * 0.4F) * f1;
		//float f10 = -(MathHelper.cos(f * 0.6662F * 2.0F + (float) Math.PI) * 0.4F) * f1;
		//float f11 = -(MathHelper.cos(f * 0.6662F * 2.0F + ((float) Math.PI / 2F)) * 0.4F) * f1;
		//float f12 = -(MathHelper.cos(f * 0.6662F * 2.0F + ((float) Math.PI * 3F / 2F)) * 0.4F) * f1;
		//float f13 = Math.abs(MathHelper.sin(f * 0.6662F + 0.0F) * 0.4F) * f1;
		//float f14 = Math.abs(MathHelper.sin(f * 0.6662F + (float) Math.PI) * 0.4F) * f1;
		//float f15 = Math.abs(MathHelper.sin(f * 0.6662F + ((float) Math.PI / 2F)) * 0.4F) * f1;
		//float f16 = Math.abs(MathHelper.sin(f * 0.6662F + ((float) Math.PI * 3F / 2F)) * 0.4F) * f1;
		f9 *= 1.5;
		this.modelcrabModel[10].rotateAngleY += f9;
		this.modelcrabModel[9].rotateAngleY -= f9;
		this.modelcrabModel[11].rotateAngleY -= f9;
		this.modelcrabModel[12].rotateAngleY += f9;
		this.modelcrabModel[5].rotateAngleY = this.modelcrabModel[10].rotateAngleY;
		this.modelcrabModel[6].rotateAngleY = this.modelcrabModel[9].rotateAngleY;
		this.modelcrabModel[7].rotateAngleY = this.modelcrabModel[11].rotateAngleY;
		this.modelcrabModel[8].rotateAngleY = this.modelcrabModel[12].rotateAngleY;
	}
}