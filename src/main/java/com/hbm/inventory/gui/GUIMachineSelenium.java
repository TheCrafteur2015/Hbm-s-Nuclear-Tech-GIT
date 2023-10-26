package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineSelenium;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineSeleniumEngine;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIMachineSelenium extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_selenium.png");
	private TileEntityMachineSeleniumEngine selenium;

	public GUIMachineSelenium(InventoryPlayer invPlayer, TileEntityMachineSeleniumEngine tedf) {
		super(new ContainerMachineSelenium(invPlayer, tedf));
		this.selenium = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.selenium.tank.renderTankInfo(this, mouseX, mouseY, this.guiLeft + 116, this.guiTop + 18, 16, 52);
		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 8, this.guiTop + 108, 160, 16, this.selenium.power, this.selenium.powerCap);

		List<String> text = new ArrayList<>();
		text.add(EnumChatFormatting.YELLOW + "Accepted Fuels:");
		
		for(FluidType type : Fluids.getInNiceOrder()) {
			long energy = TileEntityMachineSeleniumEngine.getHEFromFuel(type);
			
			if(energy > 0)
				text.add("  " + type.getLocalizedName() + " (" + BobMathUtil.getShortNumber(energy) + "HE/t)");
		}

		text.add(EnumChatFormatting.ITALIC + "(These numbers are base values,");
		text.add(EnumChatFormatting.ITALIC + "actual output is based");
		text.add(EnumChatFormatting.ITALIC + "on piston count)");
		
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 36, 16, 16, this.guiLeft - 8, this.guiTop + 36 + 16, text.toArray(new String[0]));
		
		String[] text1 = new String[] { "Fuel consumption rate:",
				"  1 mB/t",
				"  20 mB/s",
				"(Consumption rate per piston)" };
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 36 + 16, 16, 16, this.guiLeft - 8, this.guiTop + 36 + 16, text1);
		
		if(this.selenium.pistonCount < 3) {
			
			String[] text2 = new String[] { "Error: At least three pistons are",
					"required to operate this radial engine!" };
			this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 36 + 32, 16, 16, this.guiLeft - 8, this.guiTop + 36 + 16 + 32, text2);
		}
		
		if(!this.selenium.hasAcceptableFuel()) {
			
			String[] text2 = new String[] { "Error: The currently set fuel type",
					"is not supported by this engine!" };
			this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 36 + 48, 16, 16, this.guiLeft - 8, this.guiTop + 36 + 16 + 32, text2);
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.selenium.hasCustomInventoryName() ? this.selenium.getInventoryName() : I18n.format(this.selenium.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMachineSelenium.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		if(this.selenium.power > 0) {
			int i = (int)this.selenium.getPowerScaled(160);
			
			i = (int) Math.min(i, 160);
			
			drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 108, 0, 222, i, 16);
		}
		
		if(this.selenium.tank.getFill() > 0 && this.selenium.hasAcceptableFuel() && this.selenium.pistonCount > 2)
		{
			drawTexturedModalRect(this.guiLeft + 115, this.guiTop + 71, 192, 0, 18, 18);
		}
		
		if(this.selenium.pistonCount > 0)
		{
			int k = this.selenium.pistonCount;
			drawTexturedModalRect(this.guiLeft + 26, this.guiTop + 81, 176, 52 + 16 * k - 16, 16, 16);
		}
		
		if(this.selenium.pistonCount < 3)
			drawInfoPanel(this.guiLeft - 16, this.guiTop + 36 + 32, 16, 16, 6);
		
		if(!this.selenium.hasAcceptableFuel())
			drawInfoPanel(this.guiLeft - 16, this.guiTop + 36 + 48, 16, 16, 7);
		
		drawInfoPanel(this.guiLeft - 16, this.guiTop + 36, 16, 16, 2);
		drawInfoPanel(this.guiLeft - 16, this.guiTop + 36 + 16, 16, 16, 3);
		
		this.selenium.tank.renderTank(this.guiLeft + 80 + 36, this.guiTop + 70, this.zLevel, 16, 52);
	}
}
