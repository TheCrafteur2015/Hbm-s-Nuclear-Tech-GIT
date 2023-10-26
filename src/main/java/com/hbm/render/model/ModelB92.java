// Date: 05.02.2018 14:57:42
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package com.hbm.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelB92 extends ModelBase {
	// fields
	ModelRenderer Muzzle1;
	ModelRenderer Barrel1;
	ModelRenderer Barrel2;
	ModelRenderer Grip;
	ModelRenderer Front1;
	ModelRenderer Front2;
	ModelRenderer Body;
	ModelRenderer Top;
	ModelRenderer GripBottom;
	ModelRenderer Handle;
	ModelRenderer HandleBack;
	ModelRenderer Frame1;
	ModelRenderer Frame2;
	ModelRenderer Frame3;
	ModelRenderer Trigger;
	ModelRenderer BackPlate1;
	ModelRenderer Back;
	ModelRenderer BackPlate2;
	ModelRenderer Pump1;
	ModelRenderer Pump2;
	ModelRenderer BodyPlate;

	public ModelB92() {
		this.textureWidth = 64;
		this.textureHeight = 64;

		this.Muzzle1 = new ModelRenderer(this, 22, 36);
		this.Muzzle1.addBox(0F, 0F, 0F, 2, 3, 2);
		this.Muzzle1.setRotationPoint(-24F, 0.5F, -1F);
		this.Muzzle1.setTextureSize(64, 64);
		this.Muzzle1.mirror = true;
		setRotation(this.Muzzle1, 0F, 0F, 0F);
		this.Barrel1 = new ModelRenderer(this, 0, 0);
		this.Barrel1.addBox(0F, 0F, 0F, 24, 2, 3);
		this.Barrel1.setRotationPoint(-24F, 1F, -1.5F);
		this.Barrel1.setTextureSize(64, 64);
		this.Barrel1.mirror = true;
		setRotation(this.Barrel1, 0F, 0F, 0F);
		this.Barrel2 = new ModelRenderer(this, 0, 5);
		this.Barrel2.addBox(0F, 0F, 0F, 22, 1, 2);
		this.Barrel2.setRotationPoint(-22F, 0.5F, -1F);
		this.Barrel2.setTextureSize(64, 64);
		this.Barrel2.mirror = true;
		setRotation(this.Barrel2, 0F, 0F, 0F);
		this.Grip = new ModelRenderer(this, 0, 8);
		this.Grip.addBox(0F, 0F, 0F, 20, 3, 4);
		this.Grip.setRotationPoint(-20F, 3F, -2F);
		this.Grip.setTextureSize(64, 64);
		this.Grip.mirror = true;
		setRotation(this.Grip, 0F, 0F, 0F);
		this.Front1 = new ModelRenderer(this, 10, 36);
		this.Front1.addBox(0F, 0F, 0F, 2, 4, 4);
		this.Front1.setRotationPoint(-22F, 0.5F, -2F);
		this.Front1.setTextureSize(64, 64);
		this.Front1.mirror = true;
		setRotation(this.Front1, 0F, 0F, 0F);
		this.Front2 = new ModelRenderer(this, 0, 36);
		this.Front2.addBox(0F, 0F, 0F, 2, 6, 3);
		this.Front2.setRotationPoint(-22F, 0F, -1.5F);
		this.Front2.setTextureSize(64, 64);
		this.Front2.mirror = true;
		setRotation(this.Front2, 0F, 0F, 0F);
		this.Body = new ModelRenderer(this, 0, 15);
		this.Body.addBox(0F, 0F, 0F, 15, 7, 4);
		this.Body.setRotationPoint(0F, 0.5F, -2F);
		this.Body.setTextureSize(64, 64);
		this.Body.mirror = true;
		setRotation(this.Body, 0F, 0F, 0F);
		this.Top = new ModelRenderer(this, 28, 60);
		this.Top.addBox(0F, 0F, 0F, 15, 1, 3);
		this.Top.setRotationPoint(0F, 0F, -1.5F);
		this.Top.setTextureSize(64, 64);
		this.Top.mirror = true;
		setRotation(this.Top, 0F, 0F, 0F);
		this.GripBottom = new ModelRenderer(this, 24, 43);
		this.GripBottom.addBox(0F, 0F, 0F, 18, 1, 2);
		this.GripBottom.setRotationPoint(-18F, 5.5F, -1F);
		this.GripBottom.setTextureSize(64, 64);
		this.GripBottom.mirror = true;
		setRotation(this.GripBottom, 0F, 0F, 0F);
		this.Handle = new ModelRenderer(this, 0, 45);
		this.Handle.addBox(0F, 0F, 0F, 6, 15, 4);
		this.Handle.setRotationPoint(6F, 7F, -2F);
		this.Handle.setTextureSize(64, 64);
		this.Handle.mirror = true;
		setRotation(this.Handle, 0F, 0F, -0.2268928F);
		this.HandleBack = new ModelRenderer(this, 20, 46);
		this.HandleBack.addBox(5.5F, 0F, 0F, 1, 15, 3);
		this.HandleBack.setRotationPoint(6F, 7F, -1.5F);
		this.HandleBack.setTextureSize(64, 64);
		this.HandleBack.mirror = true;
		setRotation(this.HandleBack, 0F, 0F, -0.2268928F);
		this.Frame1 = new ModelRenderer(this, 28, 57);
		this.Frame1.addBox(0F, 0F, 0F, 7, 1, 2);
		this.Frame1.setRotationPoint(0.5F, 11F, -1F);
		this.Frame1.setTextureSize(64, 64);
		this.Frame1.mirror = true;
		setRotation(this.Frame1, 0F, 0F, 0F);
		this.Frame2 = new ModelRenderer(this, 28, 51);
		this.Frame2.addBox(0F, 0F, 0F, 2, 4, 2);
		this.Frame2.setRotationPoint(-2F, 6.5F, -1F);
		this.Frame2.setTextureSize(64, 64);
		this.Frame2.mirror = true;
		setRotation(this.Frame2, 0F, 0F, 0F);
		this.Frame3 = new ModelRenderer(this, 46, 57);
		this.Frame3.addBox(0F, -1F, 0F, 3, 1, 2);
		this.Frame3.setRotationPoint(-2F, 10.5F, -1F);
		this.Frame3.setTextureSize(64, 64);
		this.Frame3.mirror = true;
		setRotation(this.Frame3, 0F, 0F, 0.5235988F);
		this.Trigger = new ModelRenderer(this, 36, 53);
		this.Trigger.addBox(0F, 0F, 0F, 2, 3, 1);
		this.Trigger.setRotationPoint(4F, 7F, -0.5F);
		this.Trigger.setTextureSize(64, 64);
		this.Trigger.mirror = true;
		setRotation(this.Trigger, 0F, 0F, 0.1919862F);
		this.BackPlate1 = new ModelRenderer(this, 56, 53);
		this.BackPlate1.addBox(-1F, 0F, 0F, 1, 4, 3);
		this.BackPlate1.setRotationPoint(15F, 0F, -1.5F);
		this.BackPlate1.setTextureSize(64, 64);
		this.BackPlate1.mirror = true;
		setRotation(this.BackPlate1, 0F, 0F, -0.5235988F);
		this.Back = new ModelRenderer(this, 42, 49);
		this.Back.addBox(0F, 0F, 0F, 2, 4, 4);
		this.Back.setRotationPoint(15F, 3.5F, -2F);
		this.Back.setTextureSize(64, 64);
		this.Back.mirror = true;
		setRotation(this.Back, 0F, 0F, 0F);
		this.BackPlate2 = new ModelRenderer(this, 48, 5);
		this.BackPlate2.addBox(-2F, 0F, 0F, 2, 4, 4);
		this.BackPlate2.setRotationPoint(15F, 0.5F, -2F);
		this.BackPlate2.setTextureSize(64, 64);
		this.BackPlate2.mirror = true;
		setRotation(this.BackPlate2, 0F, 0F, -0.4886922F);
		this.Pump1 = new ModelRenderer(this, 46, 29);
		this.Pump1.addBox(0F, 0F, 0F, 7, 2, 2);
		this.Pump1.setRotationPoint(10F, 1F, -1F);
		this.Pump1.setTextureSize(64, 64);
		this.Pump1.mirror = true;
		setRotation(this.Pump1, 0F, 0F, 0F);
		this.Pump2 = new ModelRenderer(this, 44, 33);
		this.Pump2.addBox(0F, 0F, 0F, 3, 3, 7);
		this.Pump2.setRotationPoint(17F, 0.5F, -3.5F);
		this.Pump2.setTextureSize(64, 64);
		this.Pump2.mirror = true;
		setRotation(this.Pump2, 0F, 0F, 0F);
		this.BodyPlate = new ModelRenderer(this, 0, 26);
		this.BodyPlate.addBox(0F, 0F, 0F, 14, 5, 5);
		this.BodyPlate.setRotationPoint(1.5F, 2F, -2.5F);
		this.BodyPlate.setTextureSize(64, 64);
		this.BodyPlate.mirror = true;
		setRotation(this.BodyPlate, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.Muzzle1.render(f5);
		this.Barrel1.render(f5);
		this.Barrel2.render(f5);
		this.Grip.render(f5);
		this.Front1.render(f5);
		this.Front2.render(f5);
		this.Body.render(f5);
		this.Top.render(f5);
		this.GripBottom.render(f5);
		this.Handle.render(f5);
		this.HandleBack.render(f5);
		this.Frame1.render(f5);
		this.Frame2.render(f5);
		this.Frame3.render(f5);
		this.Trigger.render(f5);
		this.BackPlate1.render(f5);
		this.Back.render(f5);
		this.BackPlate2.render(f5);
		this.Pump1.render(f5);
		this.Pump2.render(f5);
		this.BodyPlate.render(f5);
	}

	public void renderAnim(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, float tran) {

		this.Pump1.offsetX += tran;
		this.Pump2.offsetX += tran;
		
		render(entity, f, f1, f2, f3, f4, f5);
		
		this.Pump1.offsetX -= tran;
		this.Pump2.offsetX -= tran;
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
