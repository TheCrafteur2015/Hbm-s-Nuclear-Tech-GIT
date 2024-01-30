package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerNukeFurnace;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityNukeFurnace;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUINukeFurnace extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_nuke_furnace.png");
	private TileEntityNukeFurnace furnace;

	public GUINukeFurnace(InventoryPlayer invPlayer, TileEntityNukeFurnace tedf) {
		super(new ContainerNukeFurnace(invPlayer, tedf));
		this.furnace = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		String[] text = new String[1];
		int power = this.furnace.dualPower;
		text[0] = power + " operation" + (power > 1 ? "s" : "") + " left";
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 55, this.guiTop + 34, 18, 18, mouseX, mouseY, text);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.furnace.hasCustomInventoryName() ? this.furnace.getInventoryName() : I18n.format(this.furnace.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUINukeFurnace.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		if(this.furnace.hasPower())
			drawTexturedModalRect(this.guiLeft + 55, this.guiTop + 35, 176, 0, 18, 16);
		
		int i = this.furnace.getDiFurnaceProgressScaled(24);
		drawTexturedModalRect(this.guiLeft + 80, this.guiTop + 34, 176, 16, i, 17);
	}
}
