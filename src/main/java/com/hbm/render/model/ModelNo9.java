package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.loader.ModelRendererObj;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ModelNo9 extends ModelArmorBase {

	public ModelRendererObj lamp;
	public ModelRendererObj insig;
	
	public ModelNo9(int type) {
		super(type);

		this.head = new ModelRendererObj(ResourceManager.armor_no9, "Helmet");
		this.insig = new ModelRendererObj(ResourceManager.armor_no9, "Insignia");
		this.lamp = new ModelRendererObj(ResourceManager.armor_no9, "Flame");
		this.body = new ModelRendererObj(null);
		this.leftArm = new ModelRendererObj(null).setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.rightArm = new ModelRendererObj(null).setRotationPoint(5.0F, 2.0F, 0.0F);
		this.leftLeg = new ModelRendererObj(null).setRotationPoint(1.9F, 12.0F, 0.0F);
		this.rightLeg = new ModelRendererObj(null).setRotationPoint(-1.9F, 12.0F, 0.0F);
		this.leftFoot = new ModelRendererObj(null).setRotationPoint(1.9F, 12.0F, 0.0F);
		this.rightFoot = new ModelRendererObj(null).setRotationPoint(-1.9F, 12.0F, 0.0F);
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		this.head.copyTo(this.insig);
		this.head.copyTo(this.lamp);
		
		GL11.glPushMatrix();
		
		if(this.type == 0) {
			GL11.glShadeModel(GL11.GL_SMOOTH);
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.no9);
			this.head.render(par7);
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.no9_insignia);
			this.insig.render(par7);
			
			if(par1Entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) par1Entity;
				ItemStack helmet = player.getEquipmentInSlot(4);
				
				if(helmet != null && helmet.hasTagCompound() && helmet.getTagCompound().getBoolean("isOn")) {
					GL11.glColor3f(1F, 1F, 0.8F);
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
					GL11.glDisable(GL11.GL_LIGHTING);
					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
					this.lamp.render(par7);
					GL11.glEnable(GL11.GL_LIGHTING);
					GL11.glPopAttrib();
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					GL11.glColor3f(1F, 1F, 1F);
					GL11.glShadeModel(GL11.GL_FLAT);
				}
			}
		}
		
		GL11.glPopMatrix();
	}
}
