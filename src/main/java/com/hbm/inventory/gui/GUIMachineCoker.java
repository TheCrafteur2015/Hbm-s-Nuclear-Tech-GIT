package com.hbm.inventory.gui;

import java.util.Locale;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineCoker;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.oil.TileEntityMachineCoker;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineCoker extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_coker.png");
	private TileEntityMachineCoker refinery;

	public GUIMachineCoker(InventoryPlayer invPlayer, TileEntityMachineCoker tedf) {
		super(new ContainerMachineCoker(invPlayer, tedf));
		this.refinery = tedf;
		
		this.xSize = 176;
		this.ySize = 204;
	}
	
	@Override
	public void drawScreen(int x, int y, float f) {
		super.drawScreen(x, y, f);

		this.refinery.tanks[0].renderTankInfo(this, x, y, this.guiLeft + 35, this.guiTop + 18, 16, 52);
		this.refinery.tanks[1].renderTankInfo(this, x, y, this.guiLeft + 125, this.guiTop + 18, 16, 52);
		
		this.drawCustomInfoStat(x, y, this.guiLeft + 60, this.guiTop + 45, 54, 7, x, y, new String[] { String.format(Locale.US, "%,d", this.refinery.progress) + " / " + String.format(Locale.US, "%,d", TileEntityMachineCoker.processTime) + "TU" });
		this.drawCustomInfoStat(x, y, this.guiLeft + 60, this.guiTop + 54, 54, 7, x, y, new String[] { String.format(Locale.US, "%,d", this.refinery.heat) + " / " + String.format(Locale.US, "%,d", TileEntityMachineCoker.maxHeat) + "TU" });
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.refinery.hasCustomInventoryName() ? this.refinery.getInventoryName() : I18n.format(this.refinery.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 0xC7C1A3);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMachineCoker.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int p = this.refinery.progress * 53 / TileEntityMachineCoker.processTime;
		drawTexturedModalRect(this.guiLeft + 61, this.guiTop + 46, 176, 0, p, 5);
		
		int h = this.refinery.heat * 52 / TileEntityMachineCoker.maxHeat;
		drawTexturedModalRect(this.guiLeft + 61, this.guiTop + 55, 176, 5, h, 5);
		
		this.refinery.tanks[0].renderTank(this.guiLeft + 35, this.guiTop + 70, this.zLevel, 16, 52);
		this.refinery.tanks[1].renderTank(this.guiLeft + 125, this.guiTop + 70, this.zLevel, 16, 52);
	}
}
