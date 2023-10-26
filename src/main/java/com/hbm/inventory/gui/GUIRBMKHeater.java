package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerRBMKHeater;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKHeater;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIRBMKHeater extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_rbmk_heater.png");
	private TileEntityRBMKHeater rod;

	public GUIRBMKHeater(InventoryPlayer invPlayer, TileEntityRBMKHeater tedf) {
		super(new ContainerRBMKHeater(invPlayer, tedf));
		this.rod = tedf;
		
		this.xSize = 176;
		this.ySize = 186;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.rod.feed.renderTankInfo(this, mouseX, mouseY, this.guiLeft + 68, this.guiTop + 24, 16, 58);
		this.rod.steam.renderTankInfo(this, mouseX, mouseY, this.guiLeft + 126, this.guiTop + 24, 16, 58);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.rod.hasCustomInventoryName() ? this.rod.getInventoryName() : I18n.format(this.rod.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIRBMKHeater.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		this.rod.feed.renderTank(this.guiLeft + 68, this.guiTop + 82, this.zLevel, 14, 58);
		this.rod.steam.renderTank(this.guiLeft + 126, this.guiTop + 82, this.zLevel, 14, 58);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIRBMKHeater.texture);

		drawTexturedModalRect(this.guiLeft + 72, this.guiTop + 72, 176, 0, 10, 10);
		drawTexturedModalRect(this.guiLeft + 130, this.guiTop + 72, 186, 0, 10, 10);
	}
}
