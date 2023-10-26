package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.loader.ModelRendererObj;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class ModelArmorBJ extends ModelArmorBase {
	
	ModelRendererObj jetpack;
	
	public ModelArmorBJ(int type) {
		super(type);

		this.head = new ModelRendererObj(ResourceManager.armor_bj, "Head");
		this.body = new ModelRendererObj(ResourceManager.armor_bj, "Body");
		this.jetpack = new ModelRendererObj(ResourceManager.armor_bj, "Jetpack");
		this.leftArm = new ModelRendererObj(ResourceManager.armor_bj, "LeftArm").setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.rightArm = new ModelRendererObj(ResourceManager.armor_bj, "RightArm").setRotationPoint(5.0F, 2.0F, 0.0F);
		this.leftLeg = new ModelRendererObj(ResourceManager.armor_bj, "LeftLeg").setRotationPoint(1.9F, 12.0F, 0.0F);
		this.rightLeg = new ModelRendererObj(ResourceManager.armor_bj, "RightLeg").setRotationPoint(-1.9F, 12.0F, 0.0F);
		this.leftFoot = new ModelRendererObj(ResourceManager.armor_bj, "LeftFoot").setRotationPoint(1.9F, 12.0F, 0.0F);
		this.rightFoot = new ModelRendererObj(ResourceManager.armor_bj, "RightFoot").setRotationPoint(-1.9F, 12.0F, 0.0F);
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		this.body.copyTo(this.jetpack);
		
		GL11.glPushMatrix();
		
		if(this.type == 0) {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.bj_eyepatch);
			this.head.render(par7);
		}
		if(this.type == 1 || this.type == 5) {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.bj_chest);
			this.body.render(par7);
			
			if(this.type == 5) {
				Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.bj_jetpack);
				this.jetpack.render(par7);
			}
			
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.bj_arm);
			this.leftArm.render(par7);
			this.rightArm.render(par7);
		}
		if(this.type == 2) {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.bj_leg);
			this.leftLeg.render(par7);
			this.rightLeg.render(par7);
		}
		if(this.type == 3) {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.bj_leg);
			this.leftFoot.render(par7);
			this.rightFoot.render(par7);
		}
		
		GL11.glPopMatrix();
	}
}
