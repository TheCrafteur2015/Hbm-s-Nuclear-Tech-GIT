package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineChemplant;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineChemplant;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineChemplant extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_chemplant.png");
	private TileEntityMachineChemplant chemplant;
	
	public GUIMachineChemplant(InventoryPlayer invPlayer, TileEntityMachineChemplant tedf) {
		super(new ContainerMachineChemplant(invPlayer, tedf));
		this.chemplant = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.chemplant.tanks[0].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 8, this.guiTop + 52 - 34, 16, 34);
		this.chemplant.tanks[1].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 26, this.guiTop + 52 - 34, 16, 34);
		this.chemplant.tanks[2].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 134, this.guiTop + 52 - 34, 16, 34);
		this.chemplant.tanks[3].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 152, this.guiTop + 52 - 34, 16, 34);
		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 44, this.guiTop + 70 - 52, 16, 52, this.chemplant.power, TileEntityMachineChemplant.maxPower);
		
		if(this.chemplant.getStackInSlot(4) == null || this.chemplant.getStackInSlot(4).getItem()!= ModItems.chemistry_template) {

			String[] warningText = I18nUtil.resolveKeyArray("desc.gui.chemplant.warning");
			this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 36, 16, 16, this.guiLeft - 8, this.guiTop + 36 + 16, warningText);
		}
		
		String[] templateText = I18nUtil.resolveKeyArray("desc.gui.template");
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 16, 16, 16, this.guiLeft - 8, this.guiTop + 16 + 16, templateText);
		
		String[] upgradeText = new String[3];
		upgradeText[0] = I18nUtil.resolveKey("desc.gui.upgrade");
		upgradeText[1] = I18nUtil.resolveKey("desc.gui.upgrade.speed");
		upgradeText[2] = I18nUtil.resolveKey("desc.gui.upgrade.power");
		
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 105, this.guiTop + 40, 8, 8, this.guiLeft + 105, this.guiTop + 40 + 16, upgradeText);
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.chemplant.hasCustomInventoryName() ? this.chemplant.getInventoryName() : I18n.format(this.chemplant.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMachineChemplant.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int i = (int) (this.chemplant.power * 52 / TileEntityMachineChemplant.maxPower);
		drawTexturedModalRect(this.guiLeft + 44, this.guiTop + 70 - i, 176, 52 - i, 16, i);

		int j = this.chemplant.progress * 90 / this.chemplant.maxProgress;
		drawTexturedModalRect(this.guiLeft + 43, this.guiTop + 89, 0, 222, j, 18);

		drawInfoPanel(this.guiLeft + 105, this.guiTop + 40, 8, 8, 8);
		
		if(this.chemplant.getStackInSlot(4) == null || this.chemplant.getStackInSlot(4).getItem()!= ModItems.chemistry_template) {

			drawInfoPanel(this.guiLeft - 16, this.guiTop + 36, 16, 16, 6);
		}
		
		drawInfoPanel(this.guiLeft - 16, this.guiTop + 16, 16, 16, 11);
		
		this.chemplant.tanks[0].renderTank(this.guiLeft + 8, this.guiTop + 52, this.zLevel, 16, 34);
		this.chemplant.tanks[1].renderTank(this.guiLeft + 26, this.guiTop + 52, this.zLevel, 16, 34);
		this.chemplant.tanks[2].renderTank(this.guiLeft + 134, this.guiTop + 52, this.zLevel, 16, 34);
		this.chemplant.tanks[3].renderTank(this.guiLeft + 152, this.guiTop + 52, this.zLevel, 16, 34);
	}
}
