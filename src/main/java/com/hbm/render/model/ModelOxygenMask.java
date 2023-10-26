
package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class ModelOxygenMask extends ModelBiped {
	// fields
	ModelRenderer mask;
	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape5;
	ModelRenderer Shape6;

	public ModelOxygenMask() {
		this.textureWidth = 64;
		this.textureHeight = 32;

		this.mask = new ModelRenderer(this, 0, 0);
		this.Shape1 = new ModelRenderer(this, 0, 0);
		this.Shape1.addBox(0F, 0F, 0F, 8, 8, 3);
		this.Shape1.setRotationPoint(0F - 4, 0F - 8 + 0.075F / 2, 0F - 4);
		this.Shape1.setTextureSize(64, 32);
		this.Shape1.mirror = true;
		setRotation(this.Shape1, 0F, 0F, 0F);
		convertToChild(this.mask, this.Shape1);
		this.Shape2 = new ModelRenderer(this, 22, 0);
		this.Shape2.addBox(0F, 0F, 0F, 2, 2, 1);
		this.Shape2.setRotationPoint(1F - 4, 3F - 8 + 0.075F / 2, -0.5333334F - 4);
		this.Shape2.setTextureSize(64, 32);
		this.Shape2.mirror = true;
		setRotation(this.Shape2, 0F, 0F, 0F);
		convertToChild(this.mask, this.Shape2);
		this.Shape3 = new ModelRenderer(this, 22, 0);
		this.Shape3.addBox(0F, 0F, 0F, 2, 2, 1);
		this.Shape3.setRotationPoint(5F - 4, 3F - 8 + 0.075F / 2, -0.5F - 4);
		this.Shape3.setTextureSize(64, 32);
		this.Shape3.mirror = true;
		setRotation(this.Shape3, 0F, 0F, 0F);
		convertToChild(this.mask, this.Shape3);
		this.Shape4 = new ModelRenderer(this, 0, 11);
		this.Shape4.addBox(0F, 0F, 0F, 2, 2, 2);
		this.Shape4.setRotationPoint(3F - 4, 5F - 8 + 0.075F / 2, 0F - 4);
		this.Shape4.setTextureSize(64, 32);
		this.Shape4.mirror = true;
		setRotation(this.Shape4, -0.7853982F, 0F, 0F);
		convertToChild(this.mask, this.Shape4);
		this.Shape5 = new ModelRenderer(this, 0, 15);
		this.Shape5.addBox(0F, 2F, -0.5F, 3, 4, 3);
		this.Shape5.setRotationPoint(2.5F - 4, 5F - 8 + 0.075F / 2, 0F - 4);
		this.Shape5.setTextureSize(64, 32);
		this.Shape5.mirror = true;
		setRotation(this.Shape5, -0.7853982F, 0F, 0F);
		convertToChild(this.mask, this.Shape5);
		this.Shape6 = new ModelRenderer(this, 0, 22);
		this.Shape6.addBox(0F, 0F, 0F, 8, 1, 5);
		this.Shape6.setRotationPoint(0F - 4, 3F - 8 + 0.075F / 2, 3F - 4);
		this.Shape6.setTextureSize(64, 32);
		this.Shape6.mirror = true;
		setRotation(this.Shape6, 0F, 0F, 0F);
		
		
		convertToChild(this.mask, this.Shape6);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		EntityPlayer player = (EntityPlayer) entity;
		if (player.isSneaking()) {
			this.isSneak = true;
		} else {
			this.isSneak = false;
		}
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.mask.rotationPointX = this.bipedHead.rotationPointX;
		this.mask.rotationPointY = this.bipedHead.rotationPointY;
		this.mask.rotateAngleY = this.bipedHead.rotateAngleY;
		this.mask.rotateAngleX = this.bipedHead.rotateAngleX;
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		GL11.glPushMatrix();
		GL11.glScalef(1.15F, 1.15F, 1.15F);
		this.mask.render(par7);
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
