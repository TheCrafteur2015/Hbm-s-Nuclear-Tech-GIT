package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerSolidifier;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.oil.TileEntityMachineSolidifier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUISolidifier extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_solidifier.png");
	private TileEntityMachineSolidifier solidifier;

	public GUISolidifier(InventoryPlayer invPlayer, TileEntityMachineSolidifier tedf) {
		super(new ContainerSolidifier(invPlayer, tedf));
		this.solidifier = tedf;
		
		this.xSize = 176;
		this.ySize = 204;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.solidifier.tank.renderTankInfo(this, mouseX, mouseY, this.guiLeft + 35, this.guiTop + 36, 16, 52);
		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 134, this.guiTop + 18, 16, 52, this.solidifier.power, TileEntityMachineSolidifier.maxPower);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		
		String name = this.solidifier.hasCustomInventoryName() ? this.solidifier.getInventoryName() : I18n.format(this.solidifier.getInventoryName());
		
		this.fontRendererObj.drawString(name, 70 - this.fontRendererObj.getStringWidth(name) / 2, 6, 0xC7C1A3);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUISolidifier.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		int i = (int)(this.solidifier.getPower() * 52 / this.solidifier.getMaxPower());
		drawTexturedModalRect(this.guiLeft + 134, this.guiTop + 70 - i, 176, 52 - i, 16, i);
		
		int j = this.solidifier.progress * 42 / this.solidifier.processTime;
		drawTexturedModalRect(this.guiLeft + 42, this.guiTop + 17, 192, 0, j, 35);
		
		if(i > 0)
			drawTexturedModalRect(this.guiLeft + 138, this.guiTop + 4, 176, 52, 9, 12);
		
		this.solidifier.tank.renderTank(this.guiLeft + 35, this.guiTop + 88, this.zLevel, 16, 52);
	}
}
