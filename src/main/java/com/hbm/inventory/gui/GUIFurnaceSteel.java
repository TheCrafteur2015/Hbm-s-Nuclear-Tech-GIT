package com.hbm.inventory.gui;

import java.util.Locale;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerFurnaceSteel;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityFurnaceSteel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIFurnaceSteel extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_furnace_steel.png");
	private TileEntityFurnaceSteel furnace;

	public GUIFurnaceSteel(InventoryPlayer invPlayer, TileEntityFurnaceSteel tedf) {
		super(new ContainerFurnaceSteel(invPlayer, tedf));
		this.furnace = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);
		
		for(int i = 0; i < 3; i++) {
			this.drawCustomInfoStat(x, y, this.guiLeft + 53, this.guiTop + 17 + 18 * i, 70, 7, x, y, new String[] { String.format(Locale.US, "%,d", this.furnace.progress[i]) + " / " + String.format(Locale.US, "%,d", TileEntityFurnaceSteel.processTime) + "TU" });
			this.drawCustomInfoStat(x, y, this.guiLeft + 53, this.guiTop + 26 + 18 * i, 70, 7, x, y, new String[] { "Bonus: " + this.furnace.bonus[i] + "%" });
		}
		
		this.drawCustomInfoStat(x, y, this.guiLeft + 151, this.guiTop + 18, 9, 50, x, y, new String[] { String.format(Locale.US, "%,d", this.furnace.heat) + " / " + String.format(Locale.US, "%,d", TileEntityFurnaceSteel.maxHeat) + "TU" });
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
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIFurnaceSteel.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int h = this.furnace.heat * 48 / TileEntityFurnaceSteel.maxHeat;
		drawTexturedModalRect(this.guiLeft + 152, this.guiTop + 67 - h, 176, 76 - h, 7, h);
		
		for(int i = 0; i < 3; i++) {
			int p = this.furnace.progress[i] * 69 / TileEntityFurnaceSteel.processTime;
			drawTexturedModalRect(this.guiLeft + 54, this.guiTop + 18 + 18 * i, 176, 18, p, 5);
			int b = this.furnace.bonus[i] * 69 / 100;
			drawTexturedModalRect(this.guiLeft + 54, this.guiTop + 27 + 18 * i, 176, 23, b, 5);
			
			if(this.furnace.wasOn)
				drawTexturedModalRect(this.guiLeft + 16, this.guiTop + 16 + 18 * i, 176, 0, 18, 18);
		}
	}
}
