package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.container.ContainerElectricFurnace;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineElectricFurnace;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineElectricFurnace extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/GUIElectricFurnace.png");
	private TileEntityMachineElectricFurnace diFurnace;

	public GUIMachineElectricFurnace(InventoryPlayer invPlayer, TileEntityMachineElectricFurnace tedf) {
		super(new ContainerElectricFurnace(invPlayer, tedf));
		this.diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 20, this.guiTop + 69 - 52, 16, 52, this.diFurnace.power, TileEntityMachineElectricFurnace.maxPower);
		
		String[] upgradeText = new String[3];
		upgradeText[0] = I18nUtil.resolveKey("desc.gui.upgrade");
		upgradeText[1] = I18nUtil.resolveKey("desc.gui.upgrade.speed");
		upgradeText[2] = I18nUtil.resolveKey("desc.gui.upgrade.power");
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 151, this.guiTop + 19, 8, 8, mouseX, mouseY, upgradeText);
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
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMachineElectricFurnace.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		//failsafe TE clone
		//if initial TE invalidates, new TE is fetched
		//if initial ZE is still present, it'll be used instead
		//works so that container packets can still be used
		//efficiency!
		if(this.diFurnace.isInvalid() && this.diFurnace.getWorldObj().getTileEntity(this.diFurnace.xCoord, this.diFurnace.yCoord, this.diFurnace.zCoord) instanceof TileEntityMachineElectricFurnace)
			this.diFurnace = (TileEntityMachineElectricFurnace) this.diFurnace.getWorldObj().getTileEntity(this.diFurnace.xCoord, this.diFurnace.yCoord, this.diFurnace.zCoord);
		
		if(this.diFurnace.hasPower()) {
			int i = (int)this.diFurnace.getPowerScaled(52);
			drawTexturedModalRect(this.guiLeft + 20, this.guiTop + 69 - i, 200, 52 - i, 16, i);
		}
		
		if(this.diFurnace.getWorldObj().getBlock(this.diFurnace.xCoord, this.diFurnace.yCoord, this.diFurnace.zCoord) == ModBlocks.machine_electric_furnace_on) {
			drawTexturedModalRect(this.guiLeft + 56, this.guiTop + 35, 176, 0, 16, 16);
		}
		
		int j1 = this.diFurnace.getProgressScaled(24);
		drawTexturedModalRect(this.guiLeft + 79, this.guiTop + 34, 176, 17, j1 + 1, 17);
		
		drawInfoPanel(this.guiLeft + 151, this.guiTop + 19, 8, 8, 8);
	}

}
