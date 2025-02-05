// Date: 22.07.2018 14:26:58
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package com.hbm.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelUziBarrel extends ModelBase {
	// fields
	ModelRenderer Barrel;

	public ModelUziBarrel() {
		this.textureWidth = 32;
		this.textureHeight = 32;

		this.Barrel = new ModelRenderer(this, 0, 0);
		this.Barrel.addBox(0F, 0F, 0F, 12, 2, 2);
		this.Barrel.setRotationPoint(-40F, 2F, 1F);
		this.Barrel.setTextureSize(32, 32);
		this.Barrel.mirror = true;
		setRotation(this.Barrel, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.Barrel.render(f5);
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
