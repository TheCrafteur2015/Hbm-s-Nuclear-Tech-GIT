package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineTurbine;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineTurbine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineTurbine extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_turbine.png");
	private TileEntityMachineTurbine diFurnace;
	
	public GUIMachineTurbine(InventoryPlayer invPlayer, TileEntityMachineTurbine tedf) {
		super(new ContainerMachineTurbine(invPlayer, tedf));
		this.diFurnace = tedf;

		this.xSize = 176;
		this.ySize = 168;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.diFurnace.tanks[0].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 62, this.guiTop + 69 - 52, 16, 52);
		this.diFurnace.tanks[1].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 134, this.guiTop + 69 - 52, 16, 52);
		
		if(this.diFurnace.tanks[1].getTankType() == Fluids.NONE) {
			
			String[] text2 = new String[] { "Error: Invalid fluid!" };
			this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 36 + 32, 16, 16, this.guiLeft - 8, this.guiTop + 36 + 16 + 32, text2);
		}
		
		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 123, this.guiTop + 69 - 34, 7, 34, this.diFurnace.power, TileEntityMachineTurbine.maxPower);
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
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMachineTurbine.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		if(this.diFurnace.tanks[0].getTankType() == Fluids.STEAM) drawTexturedModalRect(this.guiLeft + 99, this.guiTop + 18, 183, 0, 14, 14);
		if(this.diFurnace.tanks[0].getTankType() == Fluids.HOTSTEAM) drawTexturedModalRect(this.guiLeft + 99, this.guiTop + 18, 183, 14, 14, 14);
		if(this.diFurnace.tanks[0].getTankType() == Fluids.SUPERHOTSTEAM) drawTexturedModalRect(this.guiLeft + 99, this.guiTop + 18, 183, 28, 14, 14);
		if(this.diFurnace.tanks[0].getTankType() == Fluids.ULTRAHOTSTEAM) drawTexturedModalRect(this.guiLeft + 99, this.guiTop + 18, 183, 42, 14, 14);

		int i = (int)this.diFurnace.getPowerScaled(34);
		drawTexturedModalRect(this.guiLeft + 123, this.guiTop + 69 - i, 176, 34 - i, 7, i);
		
		if(this.diFurnace.tanks[1].getTankType() == Fluids.NONE) {
			drawInfoPanel(this.guiLeft - 16, this.guiTop + 36 + 32, 16, 16, 6);
		}
		
		this.diFurnace.tanks[0].renderTank(this.guiLeft + 62, this.guiTop + 69, this.zLevel, 16, 52);
		this.diFurnace.tanks[1].renderTank(this.guiLeft + 134, this.guiTop + 69, this.zLevel, 16, 52);
	}
}
