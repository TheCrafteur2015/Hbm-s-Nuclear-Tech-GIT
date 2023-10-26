package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMicrowave;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityMicrowave;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMicrowave extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_microwave.png");
	private TileEntityMicrowave microwave;
	
	public GUIMicrowave(InventoryPlayer invPlayer, TileEntityMicrowave microwave) {
		super(new ContainerMicrowave(invPlayer, microwave));
		this.microwave = microwave;

		this.xSize = 176;
		this.ySize = 168;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 8, this.guiTop + 51 - 34, 16, 34, this.microwave.power, TileEntityMicrowave.maxPower);
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);

	    if(this.guiLeft + 43 <= x && this.guiLeft + 43 + 18 > x && this.guiTop + 25 < y && this.guiTop + 25 + 18 >= y) {
	    	PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.microwave.xCoord, this.microwave.yCoord, this.microwave.zCoord, 0, 0));
	    }
	    if(this.guiLeft + 43 <= x && this.guiLeft + 43 + 18 > x && this.guiTop + 43 < y && this.guiTop + 43 + 18 >= y) {
	    	PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.microwave.xCoord, this.microwave.yCoord, this.microwave.zCoord, 1, 0));
	    }
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
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMicrowave.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		int i = (int)this.microwave.getPowerScaled(34);
		drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 51 - i, 176, 34 - i, 16, i);
		
		int j = Math.min(this.microwave.getProgressScaled(23), 22);
		drawTexturedModalRect(this.guiLeft + 104, this.guiTop + 34, 192, 0, j, 16);

		int k = this.microwave.getSpeedScaled(34);
		drawTexturedModalRect(this.guiLeft + 62, this.guiTop + 60 - k, 214, 34 - k, 4, k);
	}
}
