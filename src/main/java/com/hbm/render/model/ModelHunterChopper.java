// Date: 25.07.2016 14:23:39
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package com.hbm.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHunterChopper extends ModelBase {
	// fields
	ModelRenderer RotorPivotStem;
	ModelRenderer RotorPivotTop;
	ModelRenderer RotorPivotPlate;
	ModelRenderer TorsoBaseCenter;
	ModelRenderer TorsoPlateLeft;
	ModelRenderer TorsoBaseBottom;
	ModelRenderer TorsoPlateRight;
	ModelRenderer TorsoPlateBottom;
	ModelRenderer WingLeftPlate;
	ModelRenderer WingRightPlate;
	ModelRenderer WingLeft;
	ModelRenderer WingLeftFront;
	ModelRenderer WingLeftTip;
	ModelRenderer WingRight;
	ModelRenderer WingRightFront;
	ModelRenderer WingRightTip;
	ModelRenderer TorsoBaseBack;
	ModelRenderer TorsoBoxBottom;
	ModelRenderer TorsoPlateBack;
	ModelRenderer TorsoBoxBack;
	ModelRenderer TorsoPlateLeftBack;
	ModelRenderer TorsoPlateRightBack;
	ModelRenderer TailFrontBase;
	ModelRenderer TailFrontPlate;
	ModelRenderer TailBackBase;
	ModelRenderer TailRotorFront;
	ModelRenderer TailRotorTop;
	ModelRenderer TailRotorBack;
	ModelRenderer TailRotorBottom;
	ModelRenderer TailRotorBlades;
	ModelRenderer TailRotorPivot;
	ModelRenderer HeadNeck;
	ModelRenderer HeadBack;
	ModelRenderer HeadBase;
	ModelRenderer HeadTop;
	ModelRenderer HeadFront;
	ModelRenderer HeadLeft;
	ModelRenderer HeadRight;
	ModelRenderer HeadFrontTop;
	ModelRenderer TorsoRotorBottom;
	ModelRenderer TorsoRotorFront;
	ModelRenderer TorsoRotorBack;
	ModelRenderer TorsoRotorBlades;
	ModelRenderer TorsoRotorPivot;
	ModelRenderer RotorBlades;
	ModelRenderer Antenna1;
	ModelRenderer Antenna2;
	ModelRenderer GunPivot;
	ModelRenderer GunBarrel;
	ModelRenderer GunBack;
	float f = 0.1F;

	public ModelHunterChopper() {
		this.textureWidth = 256;
		this.textureHeight = 128;
		
		this.RotorPivotStem = new ModelRenderer(this, 40, 22);
		this.RotorPivotStem.addBox(0F, 0F, 0F, 1, 4, 1);
		this.RotorPivotStem.setRotationPoint(-0.5F, 0F, -0.5F);
		this.RotorPivotStem.setTextureSize(256, 128);
		this.RotorPivotStem.mirror = true;
		setRotation(this.RotorPivotStem, 0F, 0F, 0F);
		this.RotorPivotTop = new ModelRenderer(this, 40, 27);
		this.RotorPivotTop.addBox(0F, 0F, 0F, 3, 1, 3);
		this.RotorPivotTop.setRotationPoint(-1.5F, -1F, -1.5F);
		this.RotorPivotTop.setTextureSize(256, 128);
		this.RotorPivotTop.mirror = true;
		setRotation(this.RotorPivotTop, 0F, 0F, 0F);
		this.RotorPivotPlate = new ModelRenderer(this, 40, 31);
		this.RotorPivotPlate.addBox(0F, 0F, 0F, 6, 1, 6);
		this.RotorPivotPlate.setRotationPoint(-3F, 1.5F, -3F);
		this.RotorPivotPlate.setTextureSize(256, 128);
		this.RotorPivotPlate.mirror = true;
		setRotation(this.RotorPivotPlate, 0F, 0F, 0F);
		this.TorsoBaseCenter = new ModelRenderer(this, 70, 0);
		this.TorsoBaseCenter.addBox(0F, 0F, 0F, 14, 4, 2);
		this.TorsoBaseCenter.setRotationPoint(-8F, 4F, -1F);
		this.TorsoBaseCenter.setTextureSize(256, 128);
		this.TorsoBaseCenter.mirror = true;
		setRotation(this.TorsoBaseCenter, 0F, 0F, 0F);
		this.TorsoPlateLeft = new ModelRenderer(this, 70, 6);
		this.TorsoPlateLeft.addBox(0F, -4F, 0F, 14, 4, 1);
		this.TorsoPlateLeft.setRotationPoint(-8F, 8F, -2F);
		this.TorsoPlateLeft.setTextureSize(256, 128);
		this.TorsoPlateLeft.mirror = true;
		setRotation(this.TorsoPlateLeft, -0.2268928F, 0F, 0F);
		this.TorsoBaseBottom = new ModelRenderer(this, 70, 11);
		this.TorsoBaseBottom.addBox(0F, 0F, 0F, 7, 2, 4);
		this.TorsoBaseBottom.setRotationPoint(-4F, 8F, -2F);
		this.TorsoBaseBottom.setTextureSize(256, 128);
		this.TorsoBaseBottom.mirror = true;
		setRotation(this.TorsoBaseBottom, 0F, 0F, 0F);
		this.TorsoPlateRight = new ModelRenderer(this, 70, 17);
		this.TorsoPlateRight.addBox(0F, -4F, -1F, 14, 4, 1);
		this.TorsoPlateRight.setRotationPoint(-8F, 8F, 2F);
		this.TorsoPlateRight.setTextureSize(256, 128);
		this.TorsoPlateRight.mirror = true;
		setRotation(this.TorsoPlateRight, 0.2268928F, 0F, 0F);
		this.TorsoPlateBottom = new ModelRenderer(this, 70, 22);
		this.TorsoPlateBottom.addBox(-5F, -2F, 0F, 5, 2, 4);
		this.TorsoPlateBottom.setRotationPoint(-4F, 10F, -2F);
		this.TorsoPlateBottom.setTextureSize(256, 128);
		this.TorsoPlateBottom.mirror = true;
		setRotation(this.TorsoPlateBottom, 0F, 0F, 0.2094395F);
		this.WingLeftPlate = new ModelRenderer(this, 110, 0);
		this.WingLeftPlate.addBox(0F, -3F, 0F, 9, 3, 1);
		this.WingLeftPlate.setRotationPoint(-8F, 9F, -3F);
		this.WingLeftPlate.setTextureSize(256, 128);
		this.WingLeftPlate.mirror = true;
		setRotation(this.WingLeftPlate, -0.2268928F, 0F, 0F);
		this.WingRightPlate = new ModelRenderer(this, 130, 0);
		this.WingRightPlate.addBox(0F, -3F, 0F, 9, 3, 1);
		this.WingRightPlate.setRotationPoint(-8F, 9F, 2F);
		this.WingRightPlate.setTextureSize(256, 128);
		this.WingRightPlate.mirror = true;
		setRotation(this.WingRightPlate, 0.2268928F, 0F, 0F);
		this.WingLeft = new ModelRenderer(this, 110, 4);
		this.WingLeft.addBox(0F, 0F, 0F, 3, 1, 6);
		this.WingLeft.setRotationPoint(-3F, 10F, -8F);
		this.WingLeft.setTextureSize(256, 128);
		this.WingLeft.mirror = true;
		setRotation(this.WingLeft, 0.3490659F, 0F, 0F);
		this.WingLeftFront = new ModelRenderer(this, 110, 11);
		this.WingLeftFront.addBox(0F, 0F, 0F, 2, 1, 7);
		this.WingLeftFront.setRotationPoint(-3F, 10F, -8F);
		this.WingLeftFront.setTextureSize(256, 128);
		this.WingLeftFront.mirror = true;
		setRotation(this.WingLeftFront, 0.3490659F, -0.3490659F, -0.1745329F);
		this.WingLeftTip = new ModelRenderer(this, 110, 19);
		this.WingLeftTip.addBox(0F, 0F, 0F, 5, 2, 1);
		this.WingLeftTip.setRotationPoint(-4F, 9F, -8F);
		this.WingLeftTip.setTextureSize(256, 128);
		this.WingLeftTip.mirror = true;
		setRotation(this.WingLeftTip, 0F, 0F, 0F);
		this.WingRight = new ModelRenderer(this, 130, 4);
		this.WingRight.addBox(0F, 0F, -6F, 3, 1, 6);
		this.WingRight.setRotationPoint(-3F, 10F, 8F);
		this.WingRight.setTextureSize(256, 128);
		this.WingRight.mirror = true;
		setRotation(this.WingRight, -0.3490659F, 0F, 0F);
		this.WingRightFront = new ModelRenderer(this, 130, 11);
		this.WingRightFront.addBox(0F, 0F, -7F, 2, 1, 7);
		this.WingRightFront.setRotationPoint(-3F, 10F, 8F);
		this.WingRightFront.setTextureSize(256, 128);
		this.WingRightFront.mirror = true;
		setRotation(this.WingRightFront, -0.3490659F, 0.3490659F, -0.1745329F);
		this.WingRightTip = new ModelRenderer(this, 130, 19);
		this.WingRightTip.addBox(0F, 0F, 0F, 5, 2, 1);
		this.WingRightTip.setRotationPoint(-4F, 9F, 7F);
		this.WingRightTip.setTextureSize(256, 128);
		this.WingRightTip.mirror = true;
		setRotation(this.WingRightTip, 0F, 0F, 0F);
		this.TorsoBaseBack = new ModelRenderer(this, 70, 28);
		this.TorsoBaseBack.addBox(0F, 0F, 0F, 3, 2, 3);
		this.TorsoBaseBack.setRotationPoint(3F, 7.5F, -1.5F);
		this.TorsoBaseBack.setTextureSize(256, 128);
		this.TorsoBaseBack.mirror = true;
		setRotation(this.TorsoBaseBack, 0F, 0F, 0F);
		this.TorsoBoxBottom = new ModelRenderer(this, 70, 33);
		this.TorsoBoxBottom.addBox(0F, -2F, 0F, 7, 2, 2);
		this.TorsoBoxBottom.setRotationPoint(-3F, 10F, -1F);
		this.TorsoBoxBottom.setTextureSize(256, 128);
		this.TorsoBoxBottom.mirror = true;
		setRotation(this.TorsoBoxBottom, 0F, 0F, 0.1570796F);
		this.TorsoPlateBack = new ModelRenderer(this, 70, 37);
		this.TorsoPlateBack.addBox(0F, 0F, 0F, 3, 1, 2);
		this.TorsoPlateBack.setRotationPoint(6F, 4F, -1F);
		this.TorsoPlateBack.setTextureSize(256, 128);
		this.TorsoPlateBack.mirror = true;
		setRotation(this.TorsoPlateBack, 0F, 0F, 0.2268928F);
		this.TorsoBoxBack = new ModelRenderer(this, 70, 40);
		this.TorsoBoxBack.addBox(0F, 0F, 0F, 2, 4, 2);
		this.TorsoBoxBack.setRotationPoint(6F, 5F, -1F);
		this.TorsoBoxBack.setTextureSize(256, 128);
		this.TorsoBoxBack.mirror = true;
		setRotation(this.TorsoBoxBack, 0F, 0F, 0F);
		this.TorsoPlateLeftBack = new ModelRenderer(this, 70, 46);
		this.TorsoPlateLeftBack.addBox(0F, -4F, -1F, 3, 4, 1);
		this.TorsoPlateLeftBack.setRotationPoint(6F, 8.5F, -1F);
		this.TorsoPlateLeftBack.setTextureSize(256, 128);
		this.TorsoPlateLeftBack.mirror = true;
		setRotation(this.TorsoPlateLeftBack, -0.2268928F, 0F, 0F);
		this.TorsoPlateRightBack = new ModelRenderer(this, 70, 51);
		this.TorsoPlateRightBack.addBox(0F, -4F, 0F, 3, 4, 1);
		this.TorsoPlateRightBack.setRotationPoint(6F, 8.5F, 1F);
		this.TorsoPlateRightBack.setTextureSize(256, 128);
		this.TorsoPlateRightBack.mirror = true;
		setRotation(this.TorsoPlateRightBack, 0.2268928F, 0F, 0F);
		this.TailFrontBase = new ModelRenderer(this, 24, 54);
		this.TailFrontBase.addBox(0F, 0F, 0F, 5, 2, 2);
		this.TailFrontBase.setRotationPoint(8F, 6F, -1F);
		this.TailFrontBase.setTextureSize(256, 128);
		this.TailFrontBase.mirror = true;
		setRotation(this.TailFrontBase, 0F, 0F, 0F);
		this.TailFrontPlate = new ModelRenderer(this, 24, 58);
		this.TailFrontPlate.addBox(-5F, 0F, 0F, 5, 1, 2);
		this.TailFrontPlate.setRotationPoint(13F, 6F, -1F);
		this.TailFrontPlate.setTextureSize(256, 128);
		this.TailFrontPlate.mirror = true;
		setRotation(this.TailFrontPlate, 0F, 0F, 0.2268928F);
		this.TailBackBase = new ModelRenderer(this, 24, 61);
		this.TailBackBase.addBox(0F, 0F, 0F, 4, 2, 1);
		this.TailBackBase.setRotationPoint(13F, 6F, -0.5F);
		this.TailBackBase.setTextureSize(256, 128);
		this.TailBackBase.mirror = true;
		setRotation(this.TailBackBase, 0F, 0F, 0F);
		this.TailRotorFront = new ModelRenderer(this, 24, 64);
		this.TailRotorFront.addBox(0F, 0F, 0F, 1, 3, 1);
		this.TailRotorFront.setRotationPoint(15.5F, 8F, -0.5F);
		this.TailRotorFront.setTextureSize(256, 128);
		this.TailRotorFront.mirror = true;
		setRotation(this.TailRotorFront, 0F, 0F, -0.2268928F);
		this.TailRotorTop = new ModelRenderer(this, 24, 68);
		this.TailRotorTop.addBox(0F, 0F, 0F, 3, 1, 1);
		this.TailRotorTop.setRotationPoint(17F, 6F, -0.5F);
		this.TailRotorTop.setTextureSize(256, 128);
		this.TailRotorTop.mirror = true;
		setRotation(this.TailRotorTop, 0F, 0F, 0F);
		this.TailRotorBack = new ModelRenderer(this, 24, 70);
		this.TailRotorBack.addBox(0F, 0F, 0F, 1, 4, 1);
		this.TailRotorBack.setRotationPoint(20F, 6F, -0.5F);
		this.TailRotorBack.setTextureSize(256, 128);
		this.TailRotorBack.mirror = true;
		setRotation(this.TailRotorBack, 0F, 0F, 0F);
		this.TailRotorBottom = new ModelRenderer(this, 24, 75);
		this.TailRotorBottom.addBox(0F, 0F, 0F, 3, 1, 1);
		this.TailRotorBottom.setRotationPoint(18F, 10F, -0.5F);
		this.TailRotorBottom.setTextureSize(256, 128);
		this.TailRotorBottom.mirror = true;
		setRotation(this.TailRotorBottom, 0F, 0F, 0F);
		this.TailRotorBlades = new ModelRenderer(this, 120, 120);
		this.TailRotorBlades.addBox(-1.5F, -1.5F, 0F, 3, 3, 0);
		this.TailRotorBlades.setRotationPoint(17F + 1.5F, 7F + 1.5F, 0F);
		this.TailRotorBlades.setTextureSize(256, 128);
		this.TailRotorBlades.mirror = true;
		setRotation(this.TailRotorBlades, 0F, 0F, 0F);
		this.TailRotorPivot = new ModelRenderer(this, 24, 77);
		this.TailRotorPivot.addBox(0F, 0F, 0F, 1, 2, 1);
		this.TailRotorPivot.setRotationPoint(18F, 8F, -0.5F);
		this.TailRotorPivot.setTextureSize(256, 128);
		this.TailRotorPivot.mirror = true;
		setRotation(this.TailRotorPivot, 0F, 0F, 0F);
		this.HeadNeck = new ModelRenderer(this, 0, 40);
		this.HeadNeck.addBox(-1F, 0F, 0F, 1, 6, 3);
		this.HeadNeck.setRotationPoint(-7F, 4F, -1.5F);
		this.HeadNeck.setTextureSize(256, 128);
		this.HeadNeck.mirror = true;
		setRotation(this.HeadNeck, 0F, 0F, 0.2268928F);
		this.HeadBack = new ModelRenderer(this, 0, 49);
		this.HeadBack.addBox(0F, 0F, 0F, 1, 7, 4);
		this.HeadBack.setRotationPoint(-8.5F, 3.5F, -2F);
		this.HeadBack.setTextureSize(256, 128);
		this.HeadBack.mirror = true;
		setRotation(this.HeadBack, 0F, 0F, 0.2268928F);
		this.HeadBase = new ModelRenderer(this, 0, 60);
		this.HeadBase.addBox(-2F, 1F, 0F, 2, 6, 4);
		this.HeadBase.setRotationPoint(-8.5F, 3.5F, -2F);
		this.HeadBase.setTextureSize(256, 128);
		this.HeadBase.mirror = true;
		setRotation(this.HeadBase, 0F, 0F, 0.2268928F);
		this.HeadTop = new ModelRenderer(this, 0, 70);
		this.HeadTop.addBox(-2F, 0F, 0F, 2, 2, 4);
		this.HeadTop.setRotationPoint(-8.5F, 3.5F, -2F);
		this.HeadTop.setTextureSize(256, 128);
		this.HeadTop.mirror = true;
		setRotation(this.HeadTop, 0F, 0F, -0.2268928F);
		this.HeadFront = new ModelRenderer(this, 0, 76);
		this.HeadFront.addBox(0F, 0F, 0F, 2, 4, 2);
		this.HeadFront.setRotationPoint(-13F, 5F, -1F);
		this.HeadFront.setTextureSize(256, 128);
		this.HeadFront.mirror = true;
		setRotation(this.HeadFront, 0F, 0F, 0F);
		this.HeadLeft = new ModelRenderer(this, 0, 82);
		this.HeadLeft.addBox(-3F, 0F, 0F, 3, 4, 1);
		this.HeadLeft.setRotationPoint(-10F, 5F, -2F);
		this.HeadLeft.setTextureSize(256, 128);
		this.HeadLeft.mirror = true;
		setRotation(this.HeadLeft, 0F, 0.3490659F, 0F);
		this.HeadRight = new ModelRenderer(this, 0, 87);
		this.HeadRight.addBox(-3F, 0F, -1F, 3, 4, 1);
		this.HeadRight.setRotationPoint(-10F, 5F, 2F);
		this.HeadRight.setTextureSize(256, 128);
		this.HeadRight.mirror = true;
		setRotation(this.HeadRight, 0F, -0.3490659F, 0F);
		this.HeadFrontTop = new ModelRenderer(this, 0, 92);
		this.HeadFrontTop.addBox(-3F, 0F, 0F, 3, 1, 2);
		this.HeadFrontTop.setRotationPoint(-10.5F, 4F, -1F);
		this.HeadFrontTop.setTextureSize(256, 128);
		this.HeadFrontTop.mirror = true;
		setRotation(this.HeadFrontTop, 0F, 0F, -0.3490659F);
		this.TorsoRotorBottom = new ModelRenderer(this, 0, 0);
		this.TorsoRotorBottom.addBox(0F, 0F, 0F, 3, 1, 1);
		this.TorsoRotorBottom.setRotationPoint(-7F, 11.5F, -0.5F);
		this.TorsoRotorBottom.setTextureSize(256, 128);
		this.TorsoRotorBottom.mirror = true;
		setRotation(this.TorsoRotorBottom, 0F, 0F, 0F);
		this.TorsoRotorFront = new ModelRenderer(this, 0, 2);
		this.TorsoRotorFront.addBox(0F, 0F, 0F, 1, 3, 1);
		this.TorsoRotorFront.setRotationPoint(-8F, 9F, -0.5F);
		this.TorsoRotorFront.setTextureSize(256, 128);
		this.TorsoRotorFront.mirror = true;
		setRotation(this.TorsoRotorFront, 0F, 0F, 0F);
		this.TorsoRotorBack = new ModelRenderer(this, 0, 6);
		this.TorsoRotorBack.addBox(0F, 0F, 0F, 1, 2, 1);
		this.TorsoRotorBack.setRotationPoint(-4F, 10F, -0.5F);
		this.TorsoRotorBack.setTextureSize(256, 128);
		this.TorsoRotorBack.mirror = true;
		setRotation(this.TorsoRotorBack, 0F, 0F, 0F);
		this.TorsoRotorBlades = new ModelRenderer(this, 112, 120);
		this.TorsoRotorBlades.addBox(-1.5F, -1.5F, 0F, 3, 3, 0);
		this.TorsoRotorBlades.setRotationPoint(-7F + 1.5F, 8.5F + 1.5F, 0F);
		this.TorsoRotorBlades.setTextureSize(256, 128);
		this.TorsoRotorBlades.mirror = true;
		setRotation(this.TorsoRotorBlades, 0F, 0F, 0F);
		this.TorsoRotorPivot = new ModelRenderer(this, 0, 9);
		this.TorsoRotorPivot.addBox(0F, 0F, 0F, 1, 2, 1);
		this.TorsoRotorPivot.setRotationPoint(-6F, 8.5F, -0.5F);
		this.TorsoRotorPivot.setTextureSize(256, 128);
		this.TorsoRotorPivot.mirror = true;
		setRotation(this.TorsoRotorPivot, 0F, 0F, 0F);
		this.RotorBlades = new ModelRenderer(this, 76, 68);
		this.RotorBlades.addBox(-30F, 0F, -30F, 60, 0, 60);
		this.RotorBlades.setRotationPoint(0F, 1.5F, 0F);
		this.RotorBlades.setTextureSize(256, 128);
		this.RotorBlades.mirror = true;
		setRotation(this.RotorBlades, 0F, 0F, 0F);
		this.Antenna1 = new ModelRenderer(this, 0, 95);
		this.Antenna1.addBox(0F, 0F, 0F, 4, 1, 1);
		this.Antenna1.setRotationPoint(-14F, 4F, 0.5F);
		this.Antenna1.setTextureSize(256, 128);
		this.Antenna1.mirror = true;
		setRotation(this.Antenna1, 0F, 0F, 0F);
		this.Antenna2 = new ModelRenderer(this, 0, 97);
		this.Antenna2.addBox(0F, 0F, 0F, 2, 1, 1);
		this.Antenna2.setRotationPoint(-15F, 7F, 0F);
		this.Antenna2.setTextureSize(256, 128);
		this.Antenna2.mirror = true;
		setRotation(this.Antenna2, 0F, 0F, 0F);
		this.GunPivot = new ModelRenderer(this, 0, 106);
		this.GunPivot.addBox(0F, 0F, 0F, 1, 2, 2);
		this.GunPivot.setRotationPoint(-11F, 10F, -1F);
		this.GunPivot.setTextureSize(256, 128);
		this.GunPivot.mirror = true;
		setRotation(this.GunPivot, 0F, 0F, 0F);
		this.GunBarrel = new ModelRenderer(this, 0, 110);
		this.GunBarrel.addBox(-6F, 0F, -0.5F, 6, 1, 1);
		this.GunBarrel.setRotationPoint(-10.5F, 11.5F, 0F);
		this.GunBarrel.setTextureSize(256, 128);
		this.GunBarrel.mirror = true;
		setRotation(this.GunBarrel, 0F, 0, 0F);
		this.GunBack = new ModelRenderer(this, 0, 112);
		this.GunBack.addBox(0F, -1F, -1F, 2, 2, 2);
		this.GunBack.setRotationPoint(-10.5F, 12F, 0F);
		this.GunBack.setTextureSize(256, 128);
		this.GunBack.mirror = true;
		setRotation(this.GunBack, 0F, 0, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.RotorPivotStem.render(f5);
		this.RotorPivotTop.render(f5);
		this.RotorPivotPlate.render(f5);
		this.TorsoBaseCenter.render(f5);
		this.TorsoPlateLeft.render(f5);
		this.TorsoBaseBottom.render(f5);
		this.TorsoPlateRight.render(f5);
		this.TorsoPlateBottom.render(f5);
		this.WingLeftPlate.render(f5);
		this.WingRightPlate.render(f5);
		this.WingLeft.render(f5);
		this.WingLeftFront.render(f5);
		this.WingLeftTip.render(f5);
		this.WingRight.render(f5);
		this.WingRightFront.render(f5);
		this.WingRightTip.render(f5);
		this.TorsoBaseBack.render(f5);
		this.TorsoBoxBottom.render(f5);
		this.TorsoPlateBack.render(f5);
		this.TorsoBoxBack.render(f5);
		this.TorsoPlateLeftBack.render(f5);
		this.TorsoPlateRightBack.render(f5);
		this.TailFrontBase.render(f5);
		this.TailFrontPlate.render(f5);
		this.TailBackBase.render(f5);
		this.TailRotorFront.render(f5);
		this.TailRotorTop.render(f5);
		this.TailRotorBack.render(f5);
		this.TailRotorBottom.render(f5);
		this.TailRotorBlades.render(f5);
		this.TailRotorPivot.render(f5);
		this.HeadNeck.render(f5);
		this.HeadBack.render(f5);
		this.HeadBase.render(f5);
		this.HeadTop.render(f5);
		this.HeadFront.render(f5);
		this.HeadLeft.render(f5);
		this.HeadRight.render(f5);
		this.HeadFrontTop.render(f5);
		this.TorsoRotorBottom.render(f5);
		this.TorsoRotorFront.render(f5);
		this.TorsoRotorBack.render(f5);
		this.TorsoRotorBlades.render(f5);
		this.TorsoRotorPivot.render(f5);
		this.RotorBlades.render(f5);
		this.Antenna1.render(f5);
		this.Antenna2.render(f5);
		//GunPivot.render(f5);
		//GunBarrel.render(f5);
		//GunBack.render(f5);
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
	
	public void renderAll(float f5) {
		this.RotorPivotStem.render(f5);
		this.RotorPivotTop.render(f5);
		this.RotorPivotPlate.render(f5);
		this.TorsoBaseCenter.render(f5);
		this.TorsoPlateLeft.render(f5);
		this.TorsoBaseBottom.render(f5);
		this.TorsoPlateRight.render(f5);
		this.TorsoPlateBottom.render(f5);
		this.WingLeftPlate.render(f5);
		this.WingRightPlate.render(f5);
		this.WingLeft.render(f5);
		this.WingLeftFront.render(f5);
		this.WingLeftTip.render(f5);
		this.WingRight.render(f5);
		this.WingRightFront.render(f5);
		this.WingRightTip.render(f5);
		this.TorsoBaseBack.render(f5);
		this.TorsoBoxBottom.render(f5);
		this.TorsoPlateBack.render(f5);
		this.TorsoBoxBack.render(f5);
		this.TorsoPlateLeftBack.render(f5);
		this.TorsoPlateRightBack.render(f5);
		this.TailFrontBase.render(f5);
		this.TailFrontPlate.render(f5);
		this.TailBackBase.render(f5);
		this.TailRotorFront.render(f5);
		this.TailRotorTop.render(f5);
		this.TailRotorBack.render(f5);
		this.TailRotorBottom.render(f5);
		this.TailRotorBlades.render(f5);
		this.TailRotorPivot.render(f5);
		this.HeadNeck.render(f5);
		this.HeadBack.render(f5);
		this.HeadBase.render(f5);
		this.HeadTop.render(f5);
		this.HeadFront.render(f5);
		this.HeadLeft.render(f5);
		this.HeadRight.render(f5);
		this.HeadFrontTop.render(f5);
		this.TorsoRotorBottom.render(f5);
		this.TorsoRotorFront.render(f5);
		this.TorsoRotorBack.render(f5);
		this.TorsoRotorBlades.render(f5);
		this.TorsoRotorPivot.render(f5);
		this.RotorBlades.render(f5);
		this.Antenna1.render(f5);
		this.Antenna2.render(f5);
		//GunPivot.render(f5);
		//GunBarrel.render(f5);
		//GunBack.render(f5);

		this.RotorBlades.rotateAngleY += this.f * 5;
		this.TorsoRotorBlades.rotateAngleZ += this.f * 5;
		this.TailRotorBlades.rotateAngleZ += this.f * 5;
	}

}
