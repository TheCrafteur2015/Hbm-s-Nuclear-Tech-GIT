// Date: 14.11.2016 12:21:58
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package com.hbm.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBaleflare extends ModelBase {
	// fields
	ModelRenderer Shape9;
	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape5;
	ModelRenderer Shape6;
	ModelRenderer Shape7;

	public ModelBaleflare() {
		this.textureWidth = 128;
		this.textureHeight = 32;

		this.Shape9 = new ModelRenderer(this, 0, 0);
		this.Shape9.addBox(0F, 0F, 0F, 22, 5, 3);
		this.Shape9.setRotationPoint(-11F, -2.5F, -1.5F);
		this.Shape9.setTextureSize(128, 32);
		this.Shape9.mirror = true;
		setRotation(this.Shape9, 0F, 0F, 0F);
		this.Shape1 = new ModelRenderer(this, 0, 8);
		this.Shape1.addBox(0F, 0F, 0F, 22, 3, 5);
		this.Shape1.setRotationPoint(-11F, -1.5F, -2.5F);
		this.Shape1.setTextureSize(128, 32);
		this.Shape1.mirror = true;
		setRotation(this.Shape1, 0F, 0F, 0F);
		this.Shape2 = new ModelRenderer(this, 0, 16);
		this.Shape2.addBox(0F, 0F, 0F, 1, 3, 3);
		this.Shape2.setRotationPoint(-12F, -1.5F, -1.5F);
		this.Shape2.setTextureSize(128, 32);
		this.Shape2.mirror = true;
		setRotation(this.Shape2, 0F, 0F, 0F);
		this.Shape3 = new ModelRenderer(this, 8, 16);
		this.Shape3.addBox(0F, 0F, 0F, 2, 3, 3);
		this.Shape3.setRotationPoint(11F, -1.5F, -1.5F);
		this.Shape3.setTextureSize(128, 32);
		this.Shape3.mirror = true;
		setRotation(this.Shape3, 0F, 0F, 0F);
		this.Shape4 = new ModelRenderer(this, 18, 16);
		this.Shape4.addBox(0F, 0F, 0F, 4, 1, 3);
		this.Shape4.setRotationPoint(13F, 1.5F, -1.5F);
		this.Shape4.setTextureSize(128, 32);
		this.Shape4.mirror = true;
		setRotation(this.Shape4, 0F, 0F, 0F);
		this.Shape5 = new ModelRenderer(this, 32, 16);
		this.Shape5.addBox(0F, 0F, 0F, 4, 1, 3);
		this.Shape5.setRotationPoint(13F, -2.5F, -1.5F);
		this.Shape5.setTextureSize(128, 32);
		this.Shape5.mirror = true;
		setRotation(this.Shape5, 0F, 0F, 0F);
		this.Shape6 = new ModelRenderer(this, 46, 16);
		this.Shape6.addBox(0F, 0F, 0F, 4, 3, 1);
		this.Shape6.setRotationPoint(13F, -1.5F, -2.5F);
		this.Shape6.setTextureSize(128, 32);
		this.Shape6.mirror = true;
		setRotation(this.Shape6, 0F, 0F, 0F);
		this.Shape7 = new ModelRenderer(this, 56, 16);
		this.Shape7.addBox(0F, 0F, 0F, 4, 3, 1);
		this.Shape7.setRotationPoint(13F, -1.5F, 1.5F);
		this.Shape7.setTextureSize(128, 32);
		this.Shape7.mirror = true;
		setRotation(this.Shape7, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.Shape9.render(f5);
		this.Shape1.render(f5);
		this.Shape2.render(f5);
		this.Shape3.render(f5);
		this.Shape4.render(f5);
		this.Shape5.render(f5);
		this.Shape6.render(f5);
		this.Shape7.render(f5);
	}

	public void renderAll(float f5) {
		this.Shape9.render(f5);
		this.Shape1.render(f5);
		this.Shape2.render(f5);
		this.Shape3.render(f5);
		this.Shape4.render(f5);
		this.Shape5.render(f5);
		this.Shape6.render(f5);
		this.Shape7.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

}
