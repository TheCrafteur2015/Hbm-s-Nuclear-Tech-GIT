package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineOilWell;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.oil.TileEntityOilDrillBase;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineOilWell extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_well.png");
	private TileEntityOilDrillBase derrick;
	
	public GUIMachineOilWell(InventoryPlayer invPlayer, TileEntityOilDrillBase tedf) {
		super(new ContainerMachineOilWell(invPlayer, tedf));
		this.derrick = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.derrick.tanks[0].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 62, this.guiTop + 69 - 52, 16, 52);
		this.derrick.tanks[1].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 107, this.guiTop + 69 - 52, 16, 52);
		
		if(this.derrick.tanks.length >= 3) {
			this.derrick.tanks[2].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 40, this.guiTop + 37, 6, 32);
		}
		
		String[] upgradeText = new String[4];
		upgradeText[0] = I18nUtil.resolveKey("desc.gui.upgrade");
		upgradeText[1] = I18nUtil.resolveKey("desc.gui.upgrade.speed");
		upgradeText[2] = I18nUtil.resolveKey("desc.gui.upgrade.power");
		upgradeText[3] = I18nUtil.resolveKey("desc.gui.upgrade.afterburner");
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 156, this.guiTop + 3, 8, 8, mouseX, mouseY, upgradeText);
		
		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 8, this.guiTop + 17, 16, 34, this.derrick.power, this.derrick.getMaxPower());
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.derrick.hasCustomInventoryName() ? this.derrick.getInventoryName() : I18n.format(this.derrick.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMachineOilWell.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int i = (int)(this.derrick.getPower() * 34 / this.derrick.getMaxPower());
		drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 51 - i, 176, 34 - i, 16, i);
		
		int k = this.derrick.indicator;
		
		if(k != 0)
			drawTexturedModalRect(this.guiLeft + 35, this.guiTop + 17, 176 + (k - 1) * 16, 52, 16, 16);
		
		if(this.derrick.tanks.length < 3) {
			drawTexturedModalRect(this.guiLeft + 34, this.guiTop + 36, 192, 0, 18, 34);
		}
		
		this.derrick.tanks[0].renderTank(this.guiLeft + 62, this.guiTop + 69, this.zLevel, 16, 52);
		this.derrick.tanks[1].renderTank(this.guiLeft + 107, this.guiTop + 69, this.zLevel, 16, 52);
		
		if(this.derrick.tanks.length > 2) {
			this.derrick.tanks[2].renderTank(this.guiLeft + 40, this.guiTop + 69, this.zLevel, 6, 32);
		}
		
		drawInfoPanel(this.guiLeft + 156, this.guiTop + 3, 8, 8, 8);
	}
}
