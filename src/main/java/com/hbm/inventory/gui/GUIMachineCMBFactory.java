package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineCMBFactory;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineCMBFactory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineCMBFactory extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_cmb_manufactory.png");
	private TileEntityMachineCMBFactory diFurnace;

	public GUIMachineCMBFactory(InventoryPlayer invPlayer, TileEntityMachineCMBFactory tedf) {
		super(new ContainerMachineCMBFactory(invPlayer, tedf));
		this.diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.diFurnace.tank.renderTankInfo(this, mouseX, mouseY, this.guiLeft + 26, this.guiTop + 69 - 52, 16, 52);
		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 8, this.guiTop + 106 - 88, 16, 88, this.diFurnace.power, TileEntityMachineCMBFactory.maxPower);
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
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMachineCMBFactory.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		if(this.diFurnace.power > 0) {
			int i = (int)this.diFurnace.getPowerScaled(52);
			drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 69 - i, 176, 52 - i, 16, i);
		}
		
		int j1 = this.diFurnace.getProgressScaled(24);
		drawTexturedModalRect(this.guiLeft + 101 + 9, this.guiTop + 34, 208, 0, j1 + 1, 16);
		
		this.diFurnace.tank.renderTank(this.guiLeft + 26, this.guiTop + 69, this.zLevel, 16, 52);
	}
}
