package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.loader.ModelRendererObj;

import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;

public class ModelMan extends ModelArmorBase {

	public ModelMan() {
		super(0);

		this.head = new ModelRendererObj(ResourceManager.player_manly_af, "Head");
		this.body = new ModelRendererObj(ResourceManager.player_manly_af, "Body");
		this.leftArm = new ModelRendererObj(ResourceManager.player_manly_af, "LeftArm").setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.rightArm = new ModelRendererObj(ResourceManager.player_manly_af, "RightArm").setRotationPoint(5.0F, 2.0F, 0.0F);
		this.leftLeg = new ModelRendererObj(ResourceManager.player_manly_af, "LeftLeg").setRotationPoint(1.9F, 12.0F, 0.0F);
		this.rightLeg = new ModelRendererObj(ResourceManager.player_manly_af, "RightLeg").setRotationPoint(-1.9F, 12.0F, 0.0F);
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
		bindTexture(ResourceManager.player_manly_tex);
		this.head.render(0.0625F);
		this.body.render(0.0625F);
		this.leftArm.render(0.0625F);
		this.rightArm.render(0.0625F);
		this.leftLeg.render(0.0625F);
		this.rightLeg.render(0.0625F);
		GL11.glPopMatrix();
	}

	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7, RenderPlayer render) {
		this.isSneak = par1Entity.isSneaking();
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);

		this.head.copyRotationFrom(render.modelBipedMain.bipedHead);
		this.body.copyRotationFrom(render.modelBipedMain.bipedBody);
		this.leftArm.copyRotationFrom(render.modelBipedMain.bipedLeftArm);
		this.rightArm.copyRotationFrom(render.modelBipedMain.bipedRightArm);
		this.leftLeg.copyRotationFrom(render.modelBipedMain.bipedLeftLeg);
		this.rightLeg.copyRotationFrom(render.modelBipedMain.bipedRightLeg);
		
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
		bindTexture(ResourceManager.player_manly_tex);
		this.head.render(0.0625F);
		this.body.render(0.0625F);
		this.leftArm.render(0.0625F);
		this.rightArm.render(0.0625F);
		this.leftLeg.render(0.0625F);
		this.rightLeg.render(0.0625F);
		GL11.glPopMatrix();
	}
}
