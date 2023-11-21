package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineArcWelder;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineArcWelder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineArcWelder extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_arc_welder.png");
	private TileEntityMachineArcWelder welder;

	public GUIMachineArcWelder(InventoryPlayer playerInv, TileEntityMachineArcWelder tile) {
		super(new ContainerMachineArcWelder(playerInv, tile));
		
		this.welder = tile;
		this.xSize = 176;
		this.ySize = 204;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);

		welder.tank.renderTankInfo(this, x, y, guiLeft + 35, guiTop + 63, 34, 16);
		this.drawElectricityInfo(this, x, y, guiLeft + 152, guiTop + 18, 16, 52, welder.getPower(), welder.getMaxPower());
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.welder.hasCustomInventoryName() ? this.welder.getInventoryName() : I18n.format(this.welder.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2 - 18, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMachineArcWelder.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int p = (int) (this.welder.power * 52 / this.welder.maxPower);
		drawTexturedModalRect(this.guiLeft + 152, this.guiTop + 70 - p, 176, 52 - p, 16, p);
		
		int i = this.welder.progress * 33 / this.welder.processTime;
		drawTexturedModalRect(this.guiLeft + 72, this.guiTop + 37, 192, 0, i, 14);
		
		if(this.welder.power >= this.welder.consumption) {
			drawTexturedModalRect(this.guiLeft + 156, this.guiTop + 4, 176, 52, 9, 12);
		}
		
		this.welder.tank.renderTank(this.guiLeft + 35, this.guiTop + 79, this.zLevel, 34, 16, 1);
	}
}
