package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineMiningDrill;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineMiningDrill;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineMiningDrill extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_drill.png");
	private TileEntityMachineMiningDrill diFurnace;

	public GUIMachineMiningDrill(InventoryPlayer invPlayer, TileEntityMachineMiningDrill tedf) {
		super(new ContainerMachineMiningDrill(invPlayer, tedf));
		this.diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 8, this.guiTop + 69 - 52, 16, 52, this.diFurnace.power, TileEntityMachineMiningDrill.maxPower);

		String[] upgradeText = new String[4];
		upgradeText[0] = I18nUtil.resolveKey("desc.gui.upgrade");
		upgradeText[1] = I18nUtil.resolveKey("desc.gui.upgrade.speed");
		upgradeText[2] = I18nUtil.resolveKey("desc.gui.upgrade.effectiveness");
		upgradeText[3] = I18nUtil.resolveKey("desc.gui.upgrade.power");
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 141, this.guiTop + 39, 8, 8, this.guiLeft + 100, this.guiTop + 39 + 16 + 8, upgradeText);
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
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMachineMiningDrill.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		if(this.diFurnace.power > 0) {
			int i = (int)this.diFurnace.getPowerScaled(52);
			drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 69 - i, 176, 52 - i, 16, i);
		}
		
		int k = this.diFurnace.warning;
		if(k == 2)
			drawTexturedModalRect(this.guiLeft + 44, this.guiTop + 17, 192, 0, 16, 16);
		if(k == 1)
			drawTexturedModalRect(this.guiLeft + 44, this.guiTop + 17, 208, 0, 16, 16);
		
		drawInfoPanel(this.guiLeft + 141, this.guiTop + 39, 8, 8, 8);
	}
}
