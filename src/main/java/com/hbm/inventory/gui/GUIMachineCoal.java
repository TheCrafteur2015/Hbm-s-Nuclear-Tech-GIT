package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineCoal;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineCoal;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineCoal extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/GUICoal.png");
	private TileEntityMachineCoal diFurnace;

	public GUIMachineCoal(InventoryPlayer invPlayer, TileEntityMachineCoal tedf) {
		super(new ContainerMachineCoal(invPlayer, tedf));
		this.diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.diFurnace.tank.renderTankInfo(this, mouseX, mouseY, this.guiLeft + 8, this.guiTop + 69 - 52, 16, 52);
		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 152, this.guiTop + 69 - 52, 16, 52, this.diFurnace.power, TileEntityMachineCoal.maxPower);
		
		String[] text = new String[] { "Power generation rate:",
				" 25 HE/t",
				" 500 HE/s",
				"Accepts all furnace fuels.",
				"(All fuels burn half as long in this generator",
				"as in a regular furnace)" };
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 36, 16, 16, this.guiLeft - 8, this.guiTop + 36 + 16, text);
		
		String[] text1 = new String[] { "Water consumption rate:",
				"  1 mB/t",
				"  20 mB/s",
				"(Consumption rate is constant)" };
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 36 + 16, 16, 16, this.guiLeft - 8, this.guiTop + 36 + 16, text1);
		
		if(this.diFurnace.tank.getFill() <= 0) {
			
			String[] text2 = new String[] { "Error: Water is required for",
					"the generator to function properly!" };
			this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 36 + 32, 16, 16, this.guiLeft - 8, this.guiTop + 36 + 16 + 32, text2);
		}
		
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 79, this.guiTop + 34, 18, 18, mouseX, mouseY, new String[] { String.valueOf((int)(Math.ceil((double)this.diFurnace.burnTime / 20D))) + "s"});
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
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMachineCoal.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		//It's as horrifying as it is functional.
		if(this.diFurnace.isInvalid() && this.diFurnace.getWorldObj().getTileEntity(this.diFurnace.xCoord, this.diFurnace.yCoord, this.diFurnace.zCoord) instanceof TileEntityMachineCoal)
			this.diFurnace = (TileEntityMachineCoal) this.diFurnace.getWorldObj().getTileEntity(this.diFurnace.xCoord, this.diFurnace.yCoord, this.diFurnace.zCoord);
		
		if(this.diFurnace.power > 0) {
			int i = (int)this.diFurnace.getPowerScaled(52);
			drawTexturedModalRect(this.guiLeft + 152, this.guiTop + 69 - i, 176, 52 - i, 16, i);
		}
		
		if(this.diFurnace.burnTime > 0)
		{
			drawTexturedModalRect(this.guiLeft + 79, this.guiTop + 34, 208, 0, 18, 18);
		}

		if(this.diFurnace.tank.getFill() <= 0)
			drawInfoPanel(this.guiLeft - 16, this.guiTop + 36 + 32, 16, 16, 6);
		
		drawInfoPanel(this.guiLeft - 16, this.guiTop + 36, 16, 16, 2);
		drawInfoPanel(this.guiLeft - 16, this.guiTop + 36 + 16, 16, 16, 3);
		
		this.diFurnace.tank.renderTank(this.guiLeft + 8, this.guiTop + 69, this.zLevel, 16, 52);
	}
}
