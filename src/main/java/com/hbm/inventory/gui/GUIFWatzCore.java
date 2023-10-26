package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerFWatzCore;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityFWatzCore;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIFWatzCore extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_fwatz_multiblock.png");
	private TileEntityFWatzCore diFurnace;

	public GUIFWatzCore(InventoryPlayer invPlayer, TileEntityFWatzCore tedf) {
		super(new ContainerFWatzCore(invPlayer, tedf));
		this.diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.diFurnace.tanks[0].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 8, this.guiTop + 88 - 70, 16, 70);
		this.diFurnace.tanks[1].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 134, this.guiTop + 88 - 70, 16, 70);
		this.diFurnace.tanks[2].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 152, this.guiTop + 88 - 70, 16, 70);
		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 26, this.guiTop + 106 - 88, 16, 88, this.diFurnace.power, TileEntityFWatzCore.maxPower);
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
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIFWatzCore.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		int k = (int)this.diFurnace.getPowerScaled(88);
		drawTexturedModalRect(this.guiLeft + 26, this.guiTop + 106 - k, 192, 88 - k, 16, k);
		
		if(this.diFurnace.isRunning())
			drawTexturedModalRect(this.guiLeft + 64, this.guiTop + 29, 176, 88, 48, 48);
		
		int m = this.diFurnace.getSingularityType();
		drawTexturedModalRect(this.guiLeft + 98, this.guiTop + 109, 240, 4 * m, 16, 4);
		
		this.diFurnace.tanks[0].renderTank(this.guiLeft + 8, this.guiTop + 88, this.zLevel, 16, 70);
		this.diFurnace.tanks[1].renderTank(this.guiLeft + 134, this.guiTop + 88, this.zLevel, 16, 70);
		this.diFurnace.tanks[2].renderTank(this.guiLeft + 152, this.guiTop + 88, this.zLevel, 16, 70);
	}
}
