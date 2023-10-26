package com.hbm.inventory.gui;

import java.util.Locale;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerFurnaceCombo;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityFurnaceCombination;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIFurnaceCombo extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_furnace_combination.png");
	private TileEntityFurnaceCombination furnace;

	public GUIFurnaceCombo(InventoryPlayer invPlayer, TileEntityFurnaceCombination tedf) {
		super(new ContainerFurnaceCombo(invPlayer, tedf));
		this.furnace = tedf;
		
		this.xSize = 176;
		this.ySize = 186;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);
		
		this.furnace.tank.renderTankInfo(this, x, y, this.guiLeft + 118, this.guiTop + 18, 16, 52);
		
		this.drawCustomInfoStat(x, y, this.guiLeft + 44, this.guiTop + 36, 39, 7, x, y, new String[] { String.format(Locale.US, "%,d", this.furnace.progress) + " / " + String.format(Locale.US, "%,d", TileEntityFurnaceCombination.processTime) + "TU" });
		this.drawCustomInfoStat(x, y, this.guiLeft + 44, this.guiTop + 45, 39, 7, x, y, new String[] { String.format(Locale.US, "%,d", this.furnace.heat) + " / " + String.format(Locale.US, "%,d", TileEntityFurnaceCombination.maxHeat) + "TU" });
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.furnace.hasCustomInventoryName() ? this.furnace.getInventoryName() : I18n.format(this.furnace.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIFurnaceCombo.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int p = this.furnace.progress * 38 / TileEntityFurnaceCombination.processTime;
		drawTexturedModalRect(this.guiLeft + 45, this.guiTop + 37, 176, 0, p, 5);
		
		int h = this.furnace.heat * 37 / TileEntityFurnaceCombination.maxHeat;
		drawTexturedModalRect(this.guiLeft + 45, this.guiTop + 46, 176, 5, h, 5);
		
		this.furnace.tank.renderTank(this.guiLeft + 118, this.guiTop + 70, this.zLevel, 16, 52);
	}
}
