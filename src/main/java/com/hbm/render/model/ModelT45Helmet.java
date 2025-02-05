// Date: 25.12.2015 00:20:25
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class ModelT45Helmet extends ModelBiped {
	// fields
	ModelRenderer helmet;
	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape5;
	ModelRenderer Shape6;
	ModelRenderer Shape7;
	ModelRenderer Shape8;

	public ModelT45Helmet() {
		this.textureWidth = 64;
		this.textureHeight = 32;

		this.helmet = new ModelRenderer(this, 0, 0);
		this.Shape1 = new ModelRenderer(this, 0, 0);
		this.Shape1.addBox(0F, 0F, 0F, 8, 8, 8);
		this.Shape1.setRotationPoint(-4F, 0F - 8 + 0.0625F / 2, -4F);
		this.Shape1.setTextureSize(64, 32);
		this.Shape1.mirror = true;
		setRotation(this.Shape1, 0F, 0F, 0F);
		convertToChild(this.helmet, this.Shape1);
		this.Shape2 = new ModelRenderer(this, 32, 0);
		this.Shape2.addBox(0F, 0F, 0F, 2, 2, 1);
		this.Shape2.setRotationPoint(1F, 1F - 8 + 0.0625F / 2 + 1, -5F);
		this.Shape2.setTextureSize(64, 32);
		this.Shape2.mirror = true;
		setRotation(this.Shape2, 0F, 0F, 0F);
		convertToChild(this.helmet, this.Shape2);
		this.Shape3 = new ModelRenderer(this, 40, 6);
		this.Shape3.addBox(0F, 0F, 0F, 1, 1, 4);
		this.Shape3.setRotationPoint(-5F, 1F - 8 + 0.0625F / 2, -5.466667F);
		this.Shape3.setTextureSize(64, 32);
		this.Shape3.mirror = true;
		setRotation(this.Shape3, 0F, 0F, 0F);
		convertToChild(this.helmet, this.Shape3);
		this.Shape4 = new ModelRenderer(this, 40, 0);
		this.Shape4.addBox(0F, 0F, 0F, 4, 2, 2);
		this.Shape4.setRotationPoint(-2F, 5F - 8 + 0.0625F / 2, -4F);
		this.Shape4.setTextureSize(64, 32);
		this.Shape4.mirror = true;
		setRotation(this.Shape4, -0.7853982F, 0F, 0F);
		convertToChild(this.helmet, this.Shape4);
		this.Shape5 = new ModelRenderer(this, 54, 0);
		this.Shape5.addBox(0F, 2F, 0F, 2, 1, 2);
		this.Shape5.setRotationPoint(-1F, 5F - 8 + 0.0625F / 2, -4F);
		this.Shape5.setTextureSize(64, 32);
		this.Shape5.mirror = true;
		setRotation(this.Shape5, -0.7853982F, 0F, 0F);
		convertToChild(this.helmet, this.Shape5);
		this.Shape6 = new ModelRenderer(this, 0, 16);
		this.Shape6.addBox(0F, 0F, 0F, 10, 1, 9);
		this.Shape6.setRotationPoint(-5F, 6F - 8 + 0.0625F / 2, -4.5F);
		this.Shape6.setTextureSize(64, 32);
		this.Shape6.mirror = true;
		setRotation(this.Shape6, 0F, 0F, 0F);
		convertToChild(this.helmet, this.Shape6);
		this.Shape7 = new ModelRenderer(this, 32, 7);
		this.Shape7.addBox(0F, 0F, 0F, 1, 1, 1);
		this.Shape7.setRotationPoint(-1.5F, 5F - 8 + 0.0625F / 2, -4.5F);
		this.Shape7.setTextureSize(64, 32);
		this.Shape7.mirror = true;
		setRotation(this.Shape7, -0.7853982F, 0F, 0F);
		convertToChild(this.helmet, this.Shape7);
		this.Shape8 = new ModelRenderer(this, 32, 5);
		this.Shape8.addBox(0F, 0F, 0F, 1, 1, 1);
		this.Shape8.setRotationPoint(0.5F, 5F - 8 + 0.0625F / 2, -4.5F);
		this.Shape8.setTextureSize(64, 32);
		this.Shape8.mirror = true;
		setRotation(this.Shape8, -0.7853982F, 0F, 0F);
		convertToChild(this.helmet, this.Shape8);
	}

	/*
	 * public void render(Entity entity, float f, float f1, float f2, float f3,
	 * float f4, float f5) { super.render(entity, f, f1, f2, f3, f4, f5);
	 * setRotationAngles(f, f1, f2, f3, f4, f5); Shape1.render(f5);
	 * Shape2.render(f5); Shape3.render(f5); Shape4.render(f5);
	 * Shape5.render(f5); Shape6.render(f5); Shape7.render(f5);
	 * Shape8.render(f5); }
	 */

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {

		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (player.isSneaking()) {
				this.isSneak = true;
			} else {
				this.isSneak = false;
			}
		}

		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.helmet.rotationPointX = this.bipedHead.rotationPointX;
		this.helmet.rotationPointY = this.bipedHead.rotationPointY;
		this.helmet.rotateAngleY = this.bipedHead.rotateAngleY;
		this.helmet.rotateAngleX = this.bipedHead.rotateAngleX;
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		GL11.glPushMatrix();
		GL11.glScalef(1.125F, 1.125F, 1.125F);
		GL11.glScalef(1.0625F, 1.0625F, 1.0625F);
		this.helmet.render(par7);
		GL11.glPopMatrix();
	}

	protected void convertToChild(ModelRenderer parParent, ModelRenderer parChild) {
		// move child rotation point to be relative to parent
		parChild.rotationPointX -= parParent.rotationPointX;
		parChild.rotationPointY -= parParent.rotationPointY;
		parChild.rotationPointZ -= parParent.rotationPointZ;
		// make rotations relative to parent
		parChild.rotateAngleX -= parParent.rotateAngleX;
		parChild.rotateAngleY -= parParent.rotateAngleY;
		parChild.rotateAngleZ -= parParent.rotateAngleZ;
		// create relationship
		parParent.addChild(parChild);
	}

}