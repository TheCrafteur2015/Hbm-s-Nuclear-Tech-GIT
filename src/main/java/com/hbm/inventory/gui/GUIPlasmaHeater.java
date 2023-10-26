package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerPlasmaHeater;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachinePlasmaHeater;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIPlasmaHeater extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_fusion_heater.png");
	private TileEntityMachinePlasmaHeater microwave;
	
	public GUIPlasmaHeater(InventoryPlayer invPlayer, TileEntityMachinePlasmaHeater microwave) {
		super(new ContainerPlasmaHeater(invPlayer, microwave));
		this.microwave = microwave;

		this.xSize = 176;
		this.ySize = 168;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.microwave.tanks[0].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 62, this.guiTop + 17, 16, 52);
		this.microwave.tanks[1].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 134, this.guiTop + 17, 16, 52);
		this.microwave.plasma.renderTankInfo(this, mouseX, mouseY, this.guiLeft + 98, this.guiTop + 17, 16, 52);
		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 8, this.guiTop + 17, 16, 34, this.microwave.power, TileEntityMachinePlasmaHeater.maxPower);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.microwave.hasCustomInventoryName() ? this.microwave.getInventoryName() : I18n.format(this.microwave.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIPlasmaHeater.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		int i = (int)this.microwave.getPowerScaled(34);
		drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 51 - i, 176, 34 - i, 16, i);

		this.microwave.tanks[0].renderTank(this.guiLeft + 62, this.guiTop + 69, this.zLevel, 16, 52);
		this.microwave.tanks[1].renderTank(this.guiLeft + 134, this.guiTop + 69, this.zLevel, 16, 52);
		this.microwave.plasma.renderTank(this.guiLeft + 98, this.guiTop + 69, this.zLevel, 16, 52);
	}
}
