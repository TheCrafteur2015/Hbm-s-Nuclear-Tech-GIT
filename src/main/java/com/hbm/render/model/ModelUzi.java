// Date: 22.07.2018 14:18:53
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelUzi extends ModelBase {
	// fields
	ModelRenderer BodyBack;
	ModelRenderer BodyBottom;
	ModelRenderer BodySide;
	ModelRenderer Ejector;
	ModelRenderer BodyFront;
	ModelRenderer GripFront;
	ModelRenderer StockFront;
	ModelRenderer StockPlate;
	ModelRenderer HandleBase;
	ModelRenderer Handle;
	ModelRenderer HandleBack;
	ModelRenderer HandleBottom;
	ModelRenderer HandlePlate;
	ModelRenderer HandleTop;
	ModelRenderer Muzzle1;
	ModelRenderer Muzzle2;
	ModelRenderer StockBack;
	ModelRenderer StockPlate1;
	ModelRenderer Stock1;
	ModelRenderer Stock2;
	ModelRenderer Stcok3;
	ModelRenderer Stock4;
	ModelRenderer Hump1;
	ModelRenderer Hump2;
	ModelRenderer Hump3;
	ModelRenderer Hump4;
	ModelRenderer Hump5;
	ModelRenderer Hump6;
	ModelRenderer SightBack;
	ModelRenderer Sight1;
	ModelRenderer Sight2;
	ModelRenderer Hump7;
	ModelRenderer Hump8;
	ModelRenderer Hump9;
	ModelRenderer Hump10;
	ModelRenderer Hump11;
	ModelRenderer Hump12;
	ModelRenderer Sight3;
	ModelRenderer SightFront;
	ModelRenderer Knob1;
	ModelRenderer Knob2;
	ModelRenderer Bolt1;
	ModelRenderer Bolt2;
	ModelRenderer Mag;
	ModelRenderer Trigger;
	ModelRenderer Frame1;
	ModelRenderer Frame2;

	public ModelUzi() {
		this.textureWidth = 128;
		this.textureHeight = 64;

		this.BodyBack = new ModelRenderer(this, 0, 0);
		this.BodyBack.addBox(0F, 0F, 0F, 14, 6, 4);
		this.BodyBack.setRotationPoint(0F, 0F, 0F);
		this.BodyBack.setTextureSize(128, 64);
		this.BodyBack.mirror = true;
		setRotation(this.BodyBack, 0F, 0F, 0F);
		this.BodyBottom = new ModelRenderer(this, 82, 0);
		this.BodyBottom.addBox(0F, 0F, 0F, 6, 3, 4);
		this.BodyBottom.setRotationPoint(-6F, 3F, 0F);
		this.BodyBottom.setTextureSize(128, 64);
		this.BodyBottom.mirror = true;
		setRotation(this.BodyBottom, 0F, 0F, 0F);
		this.BodySide = new ModelRenderer(this, 102, 0);
		this.BodySide.addBox(0F, 0F, 0F, 6, 3, 2);
		this.BodySide.setRotationPoint(-6F, 0F, 0F);
		this.BodySide.setTextureSize(128, 64);
		this.BodySide.mirror = true;
		setRotation(this.BodySide, 0F, 0F, 0F);
		this.Ejector = new ModelRenderer(this, 102, 5);
		this.Ejector.addBox(0F, 0F, 0F, 6, 3, 2);
		this.Ejector.setRotationPoint(-6F, 0.5F, 1.5F);
		this.Ejector.setTextureSize(128, 64);
		this.Ejector.mirror = true;
		setRotation(this.Ejector, 0F, 0F, 0F);
		this.BodyFront = new ModelRenderer(this, 36, 0);
		this.BodyFront.addBox(0F, 0F, 0F, 19, 6, 4);
		this.BodyFront.setRotationPoint(-25F, 0F, 0F);
		this.BodyFront.setTextureSize(128, 64);
		this.BodyFront.mirror = true;
		setRotation(this.BodyFront, 0F, 0F, 0F);
		this.GripFront = new ModelRenderer(this, 0, 10);
		this.GripFront.addBox(0F, 0F, 0F, 14, 5, 5);
		this.GripFront.setRotationPoint(-24.5F, 1.5F, -0.5F);
		this.GripFront.setTextureSize(128, 64);
		this.GripFront.mirror = true;
		setRotation(this.GripFront, 0F, 0F, 0F);
		this.StockFront = new ModelRenderer(this, 38, 10);
		this.StockFront.addBox(0F, 0F, 0F, 14, 3, 4);
		this.StockFront.setRotationPoint(3F, 6F, 0F);
		this.StockFront.setTextureSize(128, 64);
		this.StockFront.mirror = true;
		setRotation(this.StockFront, 0F, 0F, 0F);
		this.StockPlate = new ModelRenderer(this, 118, 0);
		this.StockPlate.addBox(0F, -4F, 0F, 1, 4, 4);
		this.StockPlate.setRotationPoint(3F, 9F, 0F);
		this.StockPlate.setTextureSize(128, 64);
		this.StockPlate.mirror = true;
		setRotation(this.StockPlate, 0F, 0F, -0.3490659F);
		this.HandleBase = new ModelRenderer(this, 0, 20);
		this.HandleBase.addBox(0F, 0F, 0F, 12, 3, 5);
		this.HandleBase.setRotationPoint(-10F, 4F, -0.5F);
		this.HandleBase.setTextureSize(128, 64);
		this.HandleBase.mirror = true;
		setRotation(this.HandleBase, 0F, 0F, 0F);
		this.Handle = new ModelRenderer(this, 0, 28);
		this.Handle.addBox(0F, 0F, 0F, 4, 10, 3);
		this.Handle.setRotationPoint(-5F, 7F, 0.5F);
		this.Handle.setTextureSize(128, 64);
		this.Handle.mirror = true;
		setRotation(this.Handle, 0F, 0F, 0F);
		this.HandleBack = new ModelRenderer(this, 14, 28);
		this.HandleBack.addBox(0F, 0F, 0F, 2, 4, 3);
		this.HandleBack.setRotationPoint(-1F, 11F, 0.5F);
		this.HandleBack.setTextureSize(128, 64);
		this.HandleBack.mirror = true;
		setRotation(this.HandleBack, 0F, 0F, 0F);
		this.HandleBottom = new ModelRenderer(this, 14, 35);
		this.HandleBottom.addBox(-2F, 0F, 0F, 2, 3, 3);
		this.HandleBottom.setRotationPoint(1F, 15F, 0.5F);
		this.HandleBottom.setTextureSize(128, 64);
		this.HandleBottom.mirror = true;
		setRotation(this.HandleBottom, 0F, 0F, 0.7853982F);
		this.HandlePlate = new ModelRenderer(this, 24, 28);
		this.HandlePlate.addBox(-2F, -3F, 0F, 2, 3, 3);
		this.HandlePlate.setRotationPoint(1F, 11F, 0.5F);
		this.HandlePlate.setTextureSize(128, 64);
		this.HandlePlate.mirror = true;
		setRotation(this.HandlePlate, 0F, 0F, -0.4886922F);
		this.HandleTop = new ModelRenderer(this, 24, 34);
		this.HandleTop.addBox(-2F, 0F, 0F, 2, 3, 3);
		this.HandleTop.setRotationPoint(1F, 7F, 0.5F);
		this.HandleTop.setTextureSize(128, 64);
		this.HandleTop.mirror = true;
		setRotation(this.HandleTop, 0F, 0F, 0.5235988F);
		this.Muzzle1 = new ModelRenderer(this, 82, 12);
		this.Muzzle1.addBox(0F, 0F, 0F, 3, 3, 2);
		this.Muzzle1.setRotationPoint(-28F, 1.5F, 1F);
		this.Muzzle1.setTextureSize(128, 64);
		this.Muzzle1.mirror = true;
		setRotation(this.Muzzle1, 0F, 0F, 0F);
		this.Muzzle2 = new ModelRenderer(this, 82, 7);
		this.Muzzle2.addBox(0F, 0F, 0F, 3, 2, 3);
		this.Muzzle2.setRotationPoint(-28F, 2F, 0.5F);
		this.Muzzle2.setTextureSize(128, 64);
		this.Muzzle2.mirror = true;
		setRotation(this.Muzzle2, 0F, 0F, 0F);
		this.StockBack = new ModelRenderer(this, 34, 20);
		this.StockBack.addBox(0F, 0F, 0F, 1, 11, 3);
		this.StockBack.setRotationPoint(14F, 7F, 0.5F);
		this.StockBack.setTextureSize(128, 64);
		this.StockBack.mirror = true;
		setRotation(this.StockBack, 0F, 0F, 0F);
		this.StockPlate1 = new ModelRenderer(this, 42, 17);
		this.StockPlate1.addBox(-1F, -11F, 0F, 1, 11, 3);
		this.StockPlate1.setRotationPoint(15F, 18F, 0.5F);
		this.StockPlate1.setTextureSize(128, 64);
		this.StockPlate1.mirror = true;
		setRotation(this.StockPlate1, 0F, 0F, 0.0959931F);
		this.Stock1 = new ModelRenderer(this, 50, 17);
		this.Stock1.addBox(-1F, -3F, 0F, 1, 3, 4);
		this.Stock1.setRotationPoint(17F, 6F, 0F);
		this.Stock1.setTextureSize(128, 64);
		this.Stock1.mirror = true;
		setRotation(this.Stock1, 0F, 0F, -0.3490659F);
		this.Stock2 = new ModelRenderer(this, 60, 17);
		this.Stock2.addBox(0F, -3F, 0F, 1, 3, 4);
		this.Stock2.setRotationPoint(14F, 6F, 0F);
		this.Stock2.setTextureSize(128, 64);
		this.Stock2.mirror = true;
		setRotation(this.Stock2, 0F, 0F, 0.3490659F);
		this.Stcok3 = new ModelRenderer(this, 50, 24);
		this.Stcok3.addBox(0F, 0F, 0F, 1, 3, 4);
		this.Stcok3.setRotationPoint(15F, 3F, 0F);
		this.Stcok3.setTextureSize(128, 64);
		this.Stcok3.mirror = true;
		setRotation(this.Stcok3, 0F, 0F, 0F);
		this.Stock4 = new ModelRenderer(this, 60, 24);
		this.Stock4.addBox(0F, 0F, 0F, 2, 5, 3);
		this.Stock4.setRotationPoint(13.5F, 1.5F, 0.5F);
		this.Stock4.setTextureSize(128, 64);
		this.Stock4.mirror = true;
		setRotation(this.Stock4, 0F, 0F, 0F);
		this.Hump1 = new ModelRenderer(this, 74, 10);
		this.Hump1.addBox(-2F, -3F, 0F, 2, 3, 1);
		this.Hump1.setRotationPoint(14F, 0F, 0F);
		this.Hump1.setTextureSize(128, 64);
		this.Hump1.mirror = true;
		setRotation(this.Hump1, 0F, 0F, -0.2617994F);
		this.Hump2 = new ModelRenderer(this, 74, 14);
		this.Hump2.addBox(0F, -3F, 0F, 2, 3, 1);
		this.Hump2.setRotationPoint(10F, 0F, 0F);
		this.Hump2.setTextureSize(128, 64);
		this.Hump2.mirror = true;
		setRotation(this.Hump2, 0F, 0F, 0.2617994F);
		this.Hump3 = new ModelRenderer(this, 74, 18);
		this.Hump3.addBox(0F, 0F, 0F, 2, 1, 1);
		this.Hump3.setRotationPoint(11F, -3F, 0F);
		this.Hump3.setTextureSize(128, 64);
		this.Hump3.mirror = true;
		setRotation(this.Hump3, 0F, 0F, 0F);
		this.Hump4 = new ModelRenderer(this, 70, 20);
		this.Hump4.addBox(-2F, -3F, 0F, 2, 3, 1);
		this.Hump4.setRotationPoint(14F, 0F, 3F);
		this.Hump4.setTextureSize(128, 64);
		this.Hump4.mirror = true;
		setRotation(this.Hump4, 0F, 0F, -0.2617994F);
		this.Hump5 = new ModelRenderer(this, 70, 24);
		this.Hump5.addBox(0F, -3F, 0F, 2, 3, 1);
		this.Hump5.setRotationPoint(10F, 0F, 3F);
		this.Hump5.setTextureSize(128, 64);
		this.Hump5.mirror = true;
		setRotation(this.Hump5, 0F, 0F, 0.2617994F);
		this.Hump6 = new ModelRenderer(this, 70, 28);
		this.Hump6.addBox(0F, 0F, 0F, 2, 1, 1);
		this.Hump6.setRotationPoint(11F, -3F, 3F);
		this.Hump6.setTextureSize(128, 64);
		this.Hump6.mirror = true;
		setRotation(this.Hump6, 0F, 0F, 0F);
		this.SightBack = new ModelRenderer(this, 80, 17);
		this.SightBack.addBox(0F, 0F, 0F, 1, 1, 1);
		this.SightBack.setRotationPoint(11.5F, -2F, 1.5F);
		this.SightBack.setTextureSize(128, 64);
		this.SightBack.mirror = true;
		setRotation(this.SightBack, 0F, 0F, 0F);
		this.Sight1 = new ModelRenderer(this, 80, 19);
		this.Sight1.addBox(0F, 0F, 0F, 1, 1, 1);
		this.Sight1.setRotationPoint(11.5F, -1F, 1.5F);
		this.Sight1.setTextureSize(128, 64);
		this.Sight1.mirror = true;
		setRotation(this.Sight1, 0F, 0F, 0F);
		this.Sight2 = new ModelRenderer(this, 84, 17);
		this.Sight2.addBox(0F, 0F, 0F, 2, 1, 2);
		this.Sight2.setRotationPoint(11F, -0.5F, 1F);
		this.Sight2.setTextureSize(128, 64);
		this.Sight2.mirror = true;
		setRotation(this.Sight2, 0F, 0F, 0F);
		this.Hump7 = new ModelRenderer(this, 94, 7);
		this.Hump7.addBox(-2F, -3F, 0F, 2, 3, 1);
		this.Hump7.setRotationPoint(-21F, 0F, 0F);
		this.Hump7.setTextureSize(128, 64);
		this.Hump7.mirror = true;
		setRotation(this.Hump7, 0F, 0F, -0.2617994F);
		this.Hump8 = new ModelRenderer(this, 94, 11);
		this.Hump8.addBox(0F, -3F, 0F, 2, 3, 1);
		this.Hump8.setRotationPoint(-25F, 0F, 0F);
		this.Hump8.setTextureSize(128, 64);
		this.Hump8.mirror = true;
		setRotation(this.Hump8, 0F, 0F, 0.2617994F);
		this.Hump9 = new ModelRenderer(this, 94, 15);
		this.Hump9.addBox(0F, 0F, 0F, 2, 1, 1);
		this.Hump9.setRotationPoint(-24F, -3F, 0F);
		this.Hump9.setTextureSize(128, 64);
		this.Hump9.mirror = true;
		setRotation(this.Hump9, 0F, 0F, 0F);
		this.Hump10 = new ModelRenderer(this, 100, 10);
		this.Hump10.addBox(-2F, -3F, 0F, 2, 3, 1);
		this.Hump10.setRotationPoint(-21F, 0F, 3F);
		this.Hump10.setTextureSize(128, 64);
		this.Hump10.mirror = true;
		setRotation(this.Hump10, 0F, 0F, -0.2617994F);
		this.Hump11 = new ModelRenderer(this, 100, 14);
		this.Hump11.addBox(0F, -3F, 0F, 2, 3, 1);
		this.Hump11.setRotationPoint(-25F, 0F, 3F);
		this.Hump11.setTextureSize(128, 64);
		this.Hump11.mirror = true;
		setRotation(this.Hump11, 0F, 0F, 0.2617994F);
		this.Hump12 = new ModelRenderer(this, 100, 18);
		this.Hump12.addBox(0F, 0F, 0F, 2, 1, 1);
		this.Hump12.setRotationPoint(-24F, -3F, 3F);
		this.Hump12.setTextureSize(128, 64);
		this.Hump12.mirror = true;
		setRotation(this.Hump12, 0F, 0F, 0F);
		this.Sight3 = new ModelRenderer(this, 34, 34);
		this.Sight3.addBox(0F, 0F, 0F, 2, 1, 2);
		this.Sight3.setRotationPoint(-24F, -0.5F, 1F);
		this.Sight3.setTextureSize(128, 64);
		this.Sight3.mirror = true;
		setRotation(this.Sight3, 0F, 0F, 0F);
		this.SightFront = new ModelRenderer(this, 34, 37);
		this.SightFront.addBox(0F, 0F, 0F, 1, 2, 0);
		this.SightFront.setRotationPoint(-23.5F, -1.5F, 2F);
		this.SightFront.setTextureSize(128, 64);
		this.SightFront.mirror = true;
		setRotation(this.SightFront, 0F, 0F, 0F);
		this.Knob1 = new ModelRenderer(this, 42, 31);
		this.Knob1.addBox(0F, 0F, 0F, 2, 2, 2);
		this.Knob1.setRotationPoint(-15F, -2F, 1F);
		this.Knob1.setTextureSize(128, 64);
		this.Knob1.mirror = true;
		setRotation(this.Knob1, 0F, 0F, 0F);
		this.Knob2 = new ModelRenderer(this, 50, 31);
		this.Knob2.addBox(-1F, 0F, -1F, 2, 2, 2);
		this.Knob2.setRotationPoint(-14F, -2F, 2F);
		this.Knob2.setTextureSize(128, 64);
		this.Knob2.mirror = true;
		setRotation(this.Knob2, 0F, 0.7853982F, 0F);
		this.Bolt1 = new ModelRenderer(this, 42, 35);
		this.Bolt1.addBox(0F, 0F, 0F, 1, 1, 1);
		this.Bolt1.setRotationPoint(11.5F, -2F, -0.5F);
		this.Bolt1.setTextureSize(128, 64);
		this.Bolt1.mirror = true;
		setRotation(this.Bolt1, 0F, 0F, 0F);
		this.Bolt2 = new ModelRenderer(this, 46, 35);
		this.Bolt2.addBox(0F, 0F, 0F, 1, 1, 1);
		this.Bolt2.setRotationPoint(11.5F, -2F, 3.5F);
		this.Bolt2.setTextureSize(128, 64);
		this.Bolt2.mirror = true;
		setRotation(this.Bolt2, 0F, 0F, 0F);
		this.Mag = new ModelRenderer(this, 118, 8);
		this.Mag.addBox(0F, 0F, 0F, 3, 10, 2);
		this.Mag.setRotationPoint(-4.5F, 17F, 1F);
		this.Mag.setTextureSize(128, 64);
		this.Mag.mirror = true;
		setRotation(this.Mag, 0F, 0F, 0F);
		this.Trigger = new ModelRenderer(this, 0, 41);
		this.Trigger.addBox(-1F, 0F, 0F, 1, 3, 1);
		this.Trigger.setRotationPoint(-6F, 7F, 1.5F);
		this.Trigger.setTextureSize(128, 64);
		this.Trigger.mirror = true;
		setRotation(this.Trigger, 0F, 0F, 0.3490659F);
		this.Frame1 = new ModelRenderer(this, 4, 41);
		this.Frame1.addBox(0F, 0F, 0F, 3, 4, 2);
		this.Frame1.setRotationPoint(-10F, 7F, 1F);
		this.Frame1.setTextureSize(128, 64);
		this.Frame1.mirror = true;
		setRotation(this.Frame1, 0F, 0F, 0F);
		this.Frame2 = new ModelRenderer(this, 14, 41);
		this.Frame2.addBox(0F, -1F, 0F, 3, 1, 2);
		this.Frame2.setRotationPoint(-7F, 11F, 1F);
		this.Frame2.setTextureSize(128, 64);
		this.Frame2.mirror = true;
		setRotation(this.Frame2, 0F, 0F, -0.4363323F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		GL11.glDisable(GL11.GL_CULL_FACE);
		this.BodyBack.render(f5);
		this.BodyBottom.render(f5);
		this.BodySide.render(f5);
		this.Ejector.render(f5);
		this.BodyFront.render(f5);
		this.GripFront.render(f5);
		this.StockFront.render(f5);
		this.StockPlate.render(f5);
		this.HandleBase.render(f5);
		this.Handle.render(f5);
		this.HandleBack.render(f5);
		this.HandleBottom.render(f5);
		this.HandlePlate.render(f5);
		this.HandleTop.render(f5);
		this.Muzzle1.render(f5);
		this.Muzzle2.render(f5);
		this.StockBack.render(f5);
		this.StockPlate1.render(f5);
		this.Stock1.render(f5);
		this.Stock2.render(f5);
		this.Stcok3.render(f5);
		this.Stock4.render(f5);
		this.Hump1.render(f5);
		this.Hump2.render(f5);
		this.Hump3.render(f5);
		this.Hump4.render(f5);
		this.Hump5.render(f5);
		this.Hump6.render(f5);
		GL11.glDisable(GL11.GL_CULL_FACE);
		this.SightBack.render(f5);
		GL11.glEnable(GL11.GL_CULL_FACE);
		this.Sight1.render(f5);
		this.Sight2.render(f5);
		this.Hump7.render(f5);
		this.Hump8.render(f5);
		this.Hump9.render(f5);
		this.Hump10.render(f5);
		this.Hump11.render(f5);
		this.Hump12.render(f5);
		this.Sight3.render(f5);
		this.SightFront.render(f5);
		this.Knob1.render(f5);
		this.Knob2.render(f5);
		this.Bolt1.render(f5);
		this.Bolt2.render(f5);
		this.Mag.render(f5);
		GL11.glDisable(GL11.GL_CULL_FACE);
		this.Trigger.render(f5);
		this.Frame1.render(f5);
		this.Frame2.render(f5);
		GL11.glEnable(GL11.GL_CULL_FACE);
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
