package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.loader.ModelRendererObj;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;

public class ModelArmorTrenchmaster extends ModelArmorBase {
	
	ModelRendererObj light;
	
	public ModelArmorTrenchmaster(int type) {
		super(type);

		this.head = new ModelRendererObj(ResourceManager.armor_trenchmaster, "Helmet");
		this.light = new ModelRendererObj(ResourceManager.armor_trenchmaster, "Light");
		this.body = new ModelRendererObj(ResourceManager.armor_trenchmaster, "Chest");
		this.leftArm = new ModelRendererObj(ResourceManager.armor_trenchmaster, "LeftArm").setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.rightArm = new ModelRendererObj(ResourceManager.armor_trenchmaster, "RightArm").setRotationPoint(5.0F, 2.0F, 0.0F);
		this.leftLeg = new ModelRendererObj(ResourceManager.armor_trenchmaster, "LeftLeg").setRotationPoint(1.9F, 12.0F, 0.0F);
		this.rightLeg = new ModelRendererObj(ResourceManager.armor_trenchmaster, "RightLeg").setRotationPoint(-1.9F, 12.0F, 0.0F);
		this.leftFoot = new ModelRendererObj(ResourceManager.armor_trenchmaster, "LeftBoot").setRotationPoint(1.9F, 12.0F, 0.0F);
		this.rightFoot = new ModelRendererObj(ResourceManager.armor_trenchmaster, "RightBoot").setRotationPoint(-1.9F, 12.0F, 0.0F);
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		this.head.copyTo(this.light);
		
		GL11.glPushMatrix();
		
		if(this.type == 0) {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.trenchmaster_helmet);
			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			this.head.render(par7);
			GL11.glDisable(GL11.GL_BLEND);
			
			/// START GLOW ///
			float lastX = OpenGlHelper.lastBrightnessX;
			float lastY = OpenGlHelper.lastBrightnessY;
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
			GL11.glDisable(GL11.GL_LIGHTING);
			this.light.render(par7);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopAttrib();
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastX, lastY);
			/// END GLOW ///
		}
		if(this.type == 1) {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.trenchmaster_chest);
			this.body.render(par7);
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.trenchmaster_arm);
			this.leftArm.render(par7);
			this.rightArm.render(par7);
		}
		if(this.type == 2) {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.trenchmaster_leg);
			this.leftLeg.render(par7);
			this.rightLeg.render(par7);
		}
		if(this.type == 3) {
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.trenchmaster_leg);
			this.leftFoot.render(par7);
			this.rightFoot.render(par7);
		}
		
		GL11.glPopMatrix();
	}
}
