package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerRBMKOutgasser;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKOutgasser;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIRBMKOutgasser extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_rbmk_outgasser.png");
	private TileEntityRBMKOutgasser rod;

	public GUIRBMKOutgasser(InventoryPlayer invPlayer, TileEntityRBMKOutgasser tedf) {
		super(new ContainerRBMKOutgasser(invPlayer, tedf));
		this.rod = tedf;
		
		this.xSize = 176;
		this.ySize = 186;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		this.rod.gas.renderTankInfo(this, mouseX, mouseY, this.guiLeft + 112, this.guiTop + 21, 16, 48);
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
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIRBMKOutgasser.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int progress = (int) (this.rod.progress * 13 / TileEntityRBMKOutgasser.duration);
		drawTexturedModalRect(this.guiLeft + 82, this.guiTop + 50, 176, 0, progress, 6);
		
		int gas = (int) (this.rod.gas.getFill() * 42 / this.rod.gas.getMaxFill());
		drawTexturedModalRect(this.guiLeft + 115, this.guiTop + 66 - gas, 188, 42 - gas, 10, gas);
	}
}
