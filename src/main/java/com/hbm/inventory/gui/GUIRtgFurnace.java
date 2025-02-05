package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerRtgFurnace;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityRtgFurnace;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIRtgFurnace extends GuiContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/RTGfurnace.png");
	private TileEntityRtgFurnace diFurnace;

	public GUIRtgFurnace(InventoryPlayer invPlayer, TileEntityRtgFurnace tedf) {
		super(new ContainerRtgFurnace(invPlayer, tedf));
		this.diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.diFurnace.hasCustomInventoryName() ? this.diFurnace.getInventoryName() : I18n.format(this.diFurnace.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIRtgFurnace.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		if(this.diFurnace.hasPower())
		{
			drawTexturedModalRect(this.guiLeft + 55, this.guiTop + 35, 176, 0, 18, 16);
		}
		
		int j1 = this.diFurnace.getDiFurnaceProgressScaled(24);
		drawTexturedModalRect(this.guiLeft + 79, this.guiTop + 34, 176, 16, j1 + 1, 17);
	}
}
