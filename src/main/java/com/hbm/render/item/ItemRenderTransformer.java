package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.render.util.RenderItemStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderTransformer implements IItemRenderer {

	double[] rtp;
	double[] ttp;
	double[] stp;
	double[] rfp;
	double[] tfp;
	double[] sfp;
	double[] rir;
	double[] tir;
	double[] sir;
	
	public ItemRenderTransformer(double[] rtp, double[] ttp, double[] stp, double[] rfp, double[] tfp, double[] sfp, double[] rir, double[] tir, double[] sir) {
		this.rtp = rtp;
		this.ttp = ttp;
		this.stp = stp;
		this.rfp = rfp;
		this.tfp = tfp;
		this.sfp = sfp;
		this.rir = rir;
		this.tir = tir;
		this.sir = sir;
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type != ItemRenderType.ENTITY;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			GL11.glRotated(this.rfp[1], 0, 1, 0);
			GL11.glRotated(this.rfp[2], 0, 0, 1);
			GL11.glRotated(this.rfp[0], 1, 0, 0);
			GL11.glTranslated(0.5, 0.5, 0);
			GL11.glTranslated(-0.5, -0.5, 0);
			GL11.glRotated(180, 0, 1, 0);
			GL11.glRotated(-90, 0, 0, 1);
			GL11.glTranslated(0.5, 0.5, 0);
			GL11.glScaled(this.sfp[0] * 2, this.sfp[1] * 2, this.sfp[2] * 2);
			GL11.glTranslated(-0.5, -0.5, 0.25);
			break;
		case EQUIPPED:
			GL11.glRotated(this.rtp[1], 0, 1, 0);
			GL11.glRotated(this.rtp[2], 0, 0, 1);
			GL11.glRotated(this.rtp[0], 1, 0, 0);
			GL11.glTranslated(this.ttp[0], this.ttp[1], this.ttp[2]);
			GL11.glRotated(45, 0, 0, 1);
			GL11.glRotated(180, 0, 1, 0);
			GL11.glRotated(-45, 0, 0, 1);
			GL11.glScaled(this.stp[0], this.stp[1], this.stp[2]);
			break;
		case INVENTORY:
			GL11.glRotated(this.rir[0], 1, 0, 0);
			GL11.glRotated(this.rir[1], 0, 1, 0);
			GL11.glRotated(this.rir[2], 0, 0, 1);
			GL11.glTranslated(this.tir[0] * 0.0625, this.tir[1] * 0.0625, this.tir[2] * 0.0625);
			GL11.glTranslated(8, 8, 0);
			GL11.glScaled(this.sir[0], this.sir[1], this.sir[2]);
			GL11.glTranslated(-8, -8, 0);
			break;
			
		default: break;
		}

		if(data.length > 1 && data[1] instanceof EntityLivingBase) {
			EntityLivingBase entity = (EntityLivingBase) data[1];
			IIcon iicon = entity.getItemIcon(item, 0);

			if(iicon == null) {
				return;
			}

			Minecraft.getMinecraft().getTextureManager().bindTexture(Minecraft.getMinecraft().getTextureManager().getResourceLocation(item.getItemSpriteNumber()));
			TextureUtil.func_152777_a(false, false, 1.0F);
			Tessellator tessellator = Tessellator.instance;
			ItemRenderer.renderItemIn2D(tessellator, iicon.getMaxU(), iicon.getMinV(), iicon.getMinU(), iicon.getMaxV(), iicon.getIconWidth(), iicon.getIconHeight(), 0.0625F);
		} else {
			RenderItemStack.renderItemStackNoEffect(0, 0, 0, item);
		}
	}

}
