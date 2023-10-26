// Date: 26.11.2016 20:13:04
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCryolator extends ModelBase {
	// fields
	ModelRenderer Body;
	ModelRenderer Barrel1;
	ModelRenderer Barrel2;
	ModelRenderer BarrelBack;
	ModelRenderer BarrelNozzle;
	ModelRenderer TankF1;
	ModelRenderer TankF2;
	ModelRenderer TankF3;
	ModelRenderer TankB1;
	ModelRenderer TankB2;
	ModelRenderer TankB3;
	ModelRenderer Drum1;
	ModelRenderer Drum2;
	ModelRenderer BodyBACK;
	ModelRenderer PipeLPlate;
	ModelRenderer BodyBottom;
	ModelRenderer Handle;
	ModelRenderer Trigger;
	ModelRenderer CoolingBlock;
	ModelRenderer BodyCenter;
	ModelRenderer PipeRPlate;
	ModelRenderer StockTop;
	ModelRenderer StockFront;
	ModelRenderer StockBack;
	ModelRenderer StockBottom;
	ModelRenderer PipeLPlateB;
	ModelRenderer PipeRPlateB;
	ModelRenderer StockConnector;
	ModelRenderer PipeTL;
	ModelRenderer PipeBL;
	ModelRenderer PipeTR;
	ModelRenderer PipeBR;
	ModelRenderer PipeL;
	ModelRenderer PipiR;
	ModelRenderer ConnectorFront;
	ModelRenderer ConnectorBFront;
	ModelRenderer ConnectorBBack;
	ModelRenderer Connector;
	ModelRenderer ConnectorStrut;
	ModelRenderer PipeLarge;
	ModelRenderer PipeLargeBack;
	ModelRenderer PipiLargeConnector;
	ModelRenderer PlateFront;
	ModelRenderer ScaffoldFront;
	ModelRenderer ScaffoldBottom;
	ModelRenderer ScaffoldBack;

	public ModelCryolator() {
		this.textureWidth = 128;
		this.textureHeight = 64;

		this.Body = new ModelRenderer(this, 0, 6);
		this.Body.addBox(0F, 0F, 0F, 15, 2, 3);
		this.Body.setRotationPoint(-12F, 0F, -1.5F);
		this.Body.setTextureSize(128, 64);
		this.Body.mirror = true;
		setRotation(this.Body, 0F, 0F, 0F);
		this.Barrel1 = new ModelRenderer(this, 0, 57);
		this.Barrel1.addBox(0F, 0F, 0F, 2, 4, 3);
		this.Barrel1.setRotationPoint(-15F, 0F, -1.5F);
		this.Barrel1.setTextureSize(128, 64);
		this.Barrel1.mirror = true;
		setRotation(this.Barrel1, 0F, 0F, 0F);
		this.Barrel2 = new ModelRenderer(this, 10, 57);
		this.Barrel2.addBox(0F, 0F, 0F, 2, 3, 4);
		this.Barrel2.setRotationPoint(-15F, 0.5F, -2F);
		this.Barrel2.setTextureSize(128, 64);
		this.Barrel2.mirror = true;
		setRotation(this.Barrel2, 0F, 0F, 0F);
		this.BarrelBack = new ModelRenderer(this, 0, 51);
		this.BarrelBack.addBox(0F, 0F, 0F, 1, 3, 3);
		this.BarrelBack.setRotationPoint(-13F, 0.5F, -1.5F);
		this.BarrelBack.setTextureSize(128, 64);
		this.BarrelBack.mirror = true;
		setRotation(this.BarrelBack, 0F, 0F, 0F);
		this.BarrelNozzle = new ModelRenderer(this, 0, 49);
		this.BarrelNozzle.addBox(0F, 0F, 0F, 1, 1, 1);
		this.BarrelNozzle.setRotationPoint(-15.5F, 0.5F, -0.5F);
		this.BarrelNozzle.setTextureSize(128, 64);
		this.BarrelNozzle.mirror = true;
		setRotation(this.BarrelNozzle, 0F, 0F, 0F);
		this.TankF1 = new ModelRenderer(this, 22, 55);
		this.TankF1.addBox(0F, 0F, 0F, 4, 5, 4);
		this.TankF1.setRotationPoint(-10.5F, 2F, -2F);
		this.TankF1.setTextureSize(128, 64);
		this.TankF1.mirror = true;
		setRotation(this.TankF1, 0F, 0F, 0F);
		this.TankF2 = new ModelRenderer(this, 38, 56);
		this.TankF2.addBox(0F, 0F, 0F, 6, 4, 4);
		this.TankF2.setRotationPoint(-11.5F, 2.5F, -2F);
		this.TankF2.setTextureSize(128, 64);
		this.TankF2.mirror = true;
		setRotation(this.TankF2, 0F, 0F, 0F);
		this.TankF3 = new ModelRenderer(this, 58, 55);
		this.TankF3.addBox(0F, 0F, 0F, 4, 4, 5);
		this.TankF3.setRotationPoint(-10.5F, 2.5F, -2.5F);
		this.TankF3.setTextureSize(128, 64);
		this.TankF3.mirror = true;
		setRotation(this.TankF3, 0F, 0F, 0F);
		this.TankB1 = new ModelRenderer(this, 8, 52);
		this.TankB1.addBox(0F, 0F, 0F, 4, 2, 3);
		this.TankB1.setRotationPoint(-5F, 3F, -1.5F);
		this.TankB1.setTextureSize(128, 64);
		this.TankB1.mirror = true;
		setRotation(this.TankB1, 0F, 0F, 0F);
		this.TankB2 = new ModelRenderer(this, 0, 42);
		this.TankB2.addBox(0F, 0F, 0F, 2, 4, 3);
		this.TankB2.setRotationPoint(-4F, 2F, -1.5F);
		this.TankB2.setTextureSize(128, 64);
		this.TankB2.mirror = true;
		setRotation(this.TankB2, 0F, 0F, 0F);
		this.TankB3 = new ModelRenderer(this, 0, 36);
		this.TankB3.addBox(0F, 0F, 0F, 2, 2, 4);
		this.TankB3.setRotationPoint(-4F, 3F, -2F);
		this.TankB3.setTextureSize(128, 64);
		this.TankB3.mirror = true;
		setRotation(this.TankB3, 0F, 0F, 0F);
		this.Drum1 = new ModelRenderer(this, 38, 49);
		this.Drum1.addBox(0F, 0F, 0F, 6, 4, 3);
		this.Drum1.setRotationPoint(-2F, 6F, -4F);
		this.Drum1.setTextureSize(128, 64);
		this.Drum1.mirror = true;
		setRotation(this.Drum1, 0F, 0F, 0F);
		this.Drum2 = new ModelRenderer(this, 24, 46);
		this.Drum2.addBox(0F, 0F, 0F, 4, 6, 3);
		this.Drum2.setRotationPoint(-1F, 5F, -4F);
		this.Drum2.setTextureSize(128, 64);
		this.Drum2.mirror = true;
		setRotation(this.Drum2, 0F, 0F, 0F);
		this.BodyBACK = new ModelRenderer(this, 38, 44);
		this.BodyBACK.addBox(0F, 0F, 0F, 7, 2, 3);
		this.BodyBACK.setRotationPoint(3F, 0F, -1.5F);
		this.BodyBACK.setTextureSize(128, 64);
		this.BodyBACK.mirror = true;
		setRotation(this.BodyBACK, 0F, 0F, 0.2617994F);
		this.PipeLPlate = new ModelRenderer(this, 10, 46);
		this.PipeLPlate.addBox(0F, 0F, 0F, 2, 4, 2);
		this.PipeLPlate.setRotationPoint(-1F, 1F, -3F);
		this.PipeLPlate.setTextureSize(128, 64);
		this.PipeLPlate.mirror = true;
		setRotation(this.PipeLPlate, 0F, 0F, 0F);
		this.BodyBottom = new ModelRenderer(this, 26, 41);
		this.BodyBottom.addBox(0F, 0F, 0F, 4, 3, 2);
		this.BodyBottom.setRotationPoint(3F, 2F, -1F);
		this.BodyBottom.setTextureSize(128, 64);
		this.BodyBottom.mirror = true;
		setRotation(this.BodyBottom, 0F, 0F, 0F);
		this.Handle = new ModelRenderer(this, 0, 29);
		this.Handle.addBox(0F, 0F, 0F, 2, 5, 2);
		this.Handle.setRotationPoint(5F, 5F, -1F);
		this.Handle.setTextureSize(128, 64);
		this.Handle.mirror = true;
		setRotation(this.Handle, 0F, 0F, -0.2617994F);
		this.Trigger = new ModelRenderer(this, 10, 43);
		this.Trigger.addBox(0F, 0F, 0F, 3, 2, 1);
		this.Trigger.setRotationPoint(3F, 5F, -0.5F);
		this.Trigger.setTextureSize(128, 64);
		this.Trigger.mirror = true;
		setRotation(this.Trigger, 0F, 0F, 0F);
		this.CoolingBlock = new ModelRenderer(this, 58, 46);
		this.CoolingBlock.addBox(0F, 0F, 0F, 3, 6, 3);
		this.CoolingBlock.setRotationPoint(-1F, 6F, 0.5F);
		this.CoolingBlock.setTextureSize(128, 64);
		this.CoolingBlock.mirror = true;
		setRotation(this.CoolingBlock, 0F, 0F, 0.2617994F);
		this.BodyCenter = new ModelRenderer(this, 12, 33);
		this.BodyCenter.addBox(0F, 0F, 0F, 3, 8, 2);
		this.BodyCenter.setRotationPoint(-0.5F, 2F, -1F);
		this.BodyCenter.setTextureSize(128, 64);
		this.BodyCenter.mirror = true;
		setRotation(this.BodyCenter, 0F, 0F, 0F);
		this.PipeRPlate = new ModelRenderer(this, 0, 21);
		this.PipeRPlate.addBox(0F, 0F, 0F, 2, 6, 2);
		this.PipeRPlate.setRotationPoint(-1F, 1F, 1F);
		this.PipeRPlate.setTextureSize(128, 64);
		this.PipeRPlate.mirror = true;
		setRotation(this.PipeRPlate, 0F, 0F, 0F);
		this.StockTop = new ModelRenderer(this, 38, 39);
		this.StockTop.addBox(0F, 0F, 0F, 6, 2, 3);
		this.StockTop.setRotationPoint(9F, 1.8F, -1.5F);
		this.StockTop.setTextureSize(128, 64);
		this.StockTop.mirror = true;
		setRotation(this.StockTop, 0F, 0F, 0F);
		this.StockFront = new ModelRenderer(this, 30, 34);
		this.StockFront.addBox(0F, 0F, 0F, 2, 5, 2);
		this.StockFront.setRotationPoint(10F, 3F, -1F);
		this.StockFront.setTextureSize(128, 64);
		this.StockFront.mirror = true;
		setRotation(this.StockFront, 0F, 0F, 0F);
		this.StockBack = new ModelRenderer(this, 38, 32);
		this.StockBack.addBox(0F, 0F, 0F, 2, 5, 2);
		this.StockBack.setRotationPoint(13F, 3F, -1F);
		this.StockBack.setTextureSize(128, 64);
		this.StockBack.mirror = true;
		setRotation(this.StockBack, 0F, 0F, 0F);
		this.StockBottom = new ModelRenderer(this, 46, 35);
		this.StockBottom.addBox(0F, 0F, 0F, 1, 2, 2);
		this.StockBottom.setRotationPoint(12F, 6F, -1F);
		this.StockBottom.setTextureSize(128, 64);
		this.StockBottom.mirror = true;
		setRotation(this.StockBottom, 0F, 0F, 0F);
		this.PipeLPlateB = new ModelRenderer(this, 8, 28);
		this.PipeLPlateB.addBox(0F, 0F, 0F, 3, 3, 2);
		this.PipeLPlateB.setRotationPoint(4F, 2F, -3F);
		this.PipeLPlateB.setTextureSize(128, 64);
		this.PipeLPlateB.mirror = true;
		setRotation(this.PipeLPlateB, 0F, 0F, 0F);
		this.PipeRPlateB = new ModelRenderer(this, 8, 23);
		this.PipeRPlateB.addBox(0F, 0F, 0F, 3, 3, 2);
		this.PipeRPlateB.setRotationPoint(4F, 2F, 1F);
		this.PipeRPlateB.setTextureSize(128, 64);
		this.PipeRPlateB.mirror = true;
		setRotation(this.PipeRPlateB, 0F, 0F, 0F);
		this.StockConnector = new ModelRenderer(this, 22, 30);
		this.StockConnector.addBox(0F, -1F, 0F, 5, 1, 3);
		this.StockConnector.setRotationPoint(6F, 5F, -1.5F);
		this.StockConnector.setTextureSize(128, 64);
		this.StockConnector.mirror = true;
		setRotation(this.StockConnector, 0F, 0F, 0.3490659F);
		this.PipeTL = new ModelRenderer(this, 58, 44);
		this.PipeTL.addBox(0F, 0F, 0F, 12, 1, 1);
		this.PipeTL.setRotationPoint(-3F, 2.3F, -2.5F);
		this.PipeTL.setTextureSize(128, 64);
		this.PipeTL.mirror = true;
		setRotation(this.PipeTL, 0F, 0F, 0F);
		this.PipeBL = new ModelRenderer(this, 56, 40);
		this.PipeBL.addBox(0F, 0F, 0F, 10, 1, 1);
		this.PipeBL.setRotationPoint(-1F, 3.7F, -2.5F);
		this.PipeBL.setTextureSize(128, 64);
		this.PipeBL.mirror = true;
		setRotation(this.PipeBL, 0F, 0F, 0F);
		this.PipeTR = new ModelRenderer(this, 56, 42);
		this.PipeTR.addBox(0F, 0F, 0F, 12, 1, 1);
		this.PipeTR.setRotationPoint(-3F, 2.3F, 1.5F);
		this.PipeTR.setTextureSize(128, 64);
		this.PipeTR.mirror = true;
		setRotation(this.PipeTR, 0F, 0F, 0F);
		this.PipeBR = new ModelRenderer(this, 56, 38);
		this.PipeBR.addBox(0F, 0F, 0F, 10, 1, 1);
		this.PipeBR.setRotationPoint(-1F, 3.7F, 1.5F);
		this.PipeBR.setTextureSize(128, 64);
		this.PipeBR.mirror = true;
		setRotation(this.PipeBR, 0F, 0F, 0F);
		this.PipeL = new ModelRenderer(this, 52, 36);
		this.PipeL.addBox(0F, 0F, 0F, 11, 1, 1);
		this.PipeL.setRotationPoint(-13F, 1.5F, -2.5F);
		this.PipeL.setTextureSize(128, 64);
		this.PipeL.mirror = true;
		setRotation(this.PipeL, 0F, 0F, 0F);
		this.PipiR = new ModelRenderer(this, 52, 34);
		this.PipiR.addBox(0F, 0F, 0F, 11, 1, 1);
		this.PipiR.setRotationPoint(-13F, 1.5F, 1.5F);
		this.PipiR.setTextureSize(128, 64);
		this.PipiR.mirror = true;
		setRotation(this.PipiR, 0F, 0F, 0F);
		this.ConnectorFront = new ModelRenderer(this, 0, 17);
		this.ConnectorFront.addBox(0F, 0F, 0F, 3, 3, 1);
		this.ConnectorFront.setRotationPoint(-14.5F, 4F, -0.5F);
		this.ConnectorFront.setTextureSize(128, 64);
		this.ConnectorFront.mirror = true;
		setRotation(this.ConnectorFront, 0F, 0F, 0F);
		this.ConnectorBFront = new ModelRenderer(this, 8, 18);
		this.ConnectorBFront.addBox(0F, 0F, -1F, 1, 4, 1);
		this.ConnectorBFront.setRotationPoint(-14F, 6F, 0.5F);
		this.ConnectorBFront.setTextureSize(128, 64);
		this.ConnectorBFront.mirror = true;
		setRotation(this.ConnectorBFront, -0.7853982F, 0F, 0F);
		this.ConnectorBBack = new ModelRenderer(this, 12, 18);
		this.ConnectorBBack.addBox(0F, 0F, -1F, 1, 4, 1);
		this.ConnectorBBack.setRotationPoint(-3F, 6F, 0.5F);
		this.ConnectorBBack.setTextureSize(128, 64);
		this.ConnectorBBack.mirror = true;
		setRotation(this.ConnectorBBack, -0.7853982F, 0F, 0F);
		this.Connector = new ModelRenderer(this, 18, 26);
		this.Connector.addBox(0F, 3.5F, -1.5F, 12, 2, 2);
		this.Connector.setRotationPoint(-14F, 6F, 0.5F);
		this.Connector.setTextureSize(128, 64);
		this.Connector.mirror = true;
		setRotation(this.Connector, -0.7853982F, 0F, 0F);
		this.ConnectorStrut = new ModelRenderer(this, 18, 24);
		this.ConnectorStrut.addBox(0F, 2F, -1F, 10, 1, 1);
		this.ConnectorStrut.setRotationPoint(-13F, 6F, 0.5F);
		this.ConnectorStrut.setTextureSize(128, 64);
		this.ConnectorStrut.mirror = true;
		setRotation(this.ConnectorStrut, -0.7853982F, 0F, 0F);
		this.PipeLarge = new ModelRenderer(this, 46, 30);
		this.PipeLarge.addBox(0F, 0F, 0F, 8, 2, 2);
		this.PipeLarge.setRotationPoint(-9.5F, 7F, -1F);
		this.PipeLarge.setTextureSize(128, 64);
		this.PipeLarge.mirror = true;
		setRotation(this.PipeLarge, 0F, 0F, 0F);
		this.PipeLargeBack = new ModelRenderer(this, 46, 22);
		this.PipeLargeBack.addBox(0F, 0F, 0F, 2, 7, 1);
		this.PipeLargeBack.setRotationPoint(-4F, 2F, 2F);
		this.PipeLargeBack.setTextureSize(128, 64);
		this.PipeLargeBack.mirror = true;
		setRotation(this.PipeLargeBack, 0F, 0F, 0F);
		this.PipiLargeConnector = new ModelRenderer(this, 40, 23);
		this.PipiLargeConnector.addBox(0F, 0F, 0F, 2, 2, 1);
		this.PipiLargeConnector.setRotationPoint(-4F, 7F, 1F);
		this.PipiLargeConnector.setTextureSize(128, 64);
		this.PipiLargeConnector.mirror = true;
		setRotation(this.PipiLargeConnector, 0F, 0F, 0F);
		this.PlateFront = new ModelRenderer(this, 20, 15);
		this.PlateFront.addBox(0F, 0F, 0F, 1, 4, 5);
		this.PlateFront.setRotationPoint(-13F, 1.5F, -2.5F);
		this.PlateFront.setTextureSize(128, 64);
		this.PlateFront.mirror = true;
		setRotation(this.PlateFront, 0F, 0F, 0.6108652F);
		this.ScaffoldFront = new ModelRenderer(this, 0, 13);
		this.ScaffoldFront.addBox(0F, 0F, 0F, 1, 1, 3);
		this.ScaffoldFront.setRotationPoint(-13F, 7F, -0.5F);
		this.ScaffoldFront.setTextureSize(128, 64);
		this.ScaffoldFront.mirror = true;
		setRotation(this.ScaffoldFront, 0F, 0F, 0F);
		this.ScaffoldBottom = new ModelRenderer(this, 0, 11);
		this.ScaffoldBottom.addBox(0F, 0F, 0F, 8, 1, 1);
		this.ScaffoldBottom.setRotationPoint(-12F, 7F, 1.5F);
		this.ScaffoldBottom.setTextureSize(128, 64);
		this.ScaffoldBottom.mirror = true;
		setRotation(this.ScaffoldBottom, 0F, 0F, 0F);
		this.ScaffoldBack = new ModelRenderer(this, 32, 18);
		this.ScaffoldBack.addBox(0F, 0F, 0F, 1, 5, 1);
		this.ScaffoldBack.setRotationPoint(-5F, 2.5F, 1.5F);
		this.ScaffoldBack.setTextureSize(128, 64);
		this.ScaffoldBack.mirror = true;
		setRotation(this.ScaffoldBack, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.Body.render(f5);
		this.Barrel1.render(f5);
		this.Barrel2.render(f5);
		this.BarrelBack.render(f5);
		GL11.glDisable(GL11.GL_CULL_FACE);
		this.BarrelNozzle.render(f5);
		GL11.glEnable(GL11.GL_CULL_FACE);
		this.TankF1.render(f5);
		this.TankF2.render(f5);
		this.TankF3.render(f5);
		this.TankB1.render(f5);
		this.TankB2.render(f5);
		this.TankB3.render(f5);
		this.Drum1.render(f5);
		this.Drum2.render(f5);
		this.BodyBACK.render(f5);
		GL11.glDisable(GL11.GL_CULL_FACE);
		this.PipeLPlate.render(f5);
		GL11.glEnable(GL11.GL_CULL_FACE);
		this.BodyBottom.render(f5);
		this.Handle.render(f5);
		this.Trigger.render(f5);
		this.CoolingBlock.render(f5);
		this.BodyCenter.render(f5);
		GL11.glDisable(GL11.GL_CULL_FACE);
		this.PipeRPlate.render(f5);
		GL11.glEnable(GL11.GL_CULL_FACE);
		this.StockTop.render(f5);
		this.StockFront.render(f5);
		this.StockBack.render(f5);
		this.StockBottom.render(f5);
		GL11.glDisable(GL11.GL_CULL_FACE);
		this.PipeLPlateB.render(f5);
		this.PipeRPlateB.render(f5);
		GL11.glEnable(GL11.GL_CULL_FACE);
		this.StockConnector.render(f5);
		this.PipeTL.render(f5);
		this.PipeBL.render(f5);
		this.PipeTR.render(f5);
		this.PipeBR.render(f5);
		this.PipeL.render(f5);
		this.PipiR.render(f5);
		this.ConnectorFront.render(f5);
		this.ConnectorBFront.render(f5);
		this.ConnectorBBack.render(f5);
		this.Connector.render(f5);
		this.ConnectorStrut.render(f5);
		this.PipeLarge.render(f5);
		this.PipeLargeBack.render(f5);
		this.PipiLargeConnector.render(f5);
		this.PlateFront.render(f5);
		this.ScaffoldFront.render(f5);
		this.ScaffoldBottom.render(f5);
		this.ScaffoldBack.render(f5);
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
