package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerBarrel;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.storage.TileEntityBarrel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIBarrel extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/storage/gui_barrel.png");
	private TileEntityBarrel tank;

	public GUIBarrel(InventoryPlayer invPlayer, TileEntityBarrel tedf) {
		super(new ContainerBarrel(invPlayer, tedf));
		this.tank = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.tank.tank.renderTankInfo(this, mouseX, mouseY, this.guiLeft + 71, this.guiTop + 69 - 52, 34, 52);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.tank.hasCustomInventoryName() ? this.tank.getInventoryName() : I18n.format(this.tank.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);
		
    	if(this.guiLeft + 151 <= x && this.guiLeft + 151 + 18 > x && this.guiTop + 35 < y && this.guiTop + 35 + 18 >= y) {
    		
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.tank.xCoord, this.tank.yCoord, this.tank.zCoord, 0, 0));
    	}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIBarrel.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int i = this.tank.mode;
		drawTexturedModalRect(this.guiLeft + 151, this.guiTop + 34, 176, i * 18, 18, 18);
		
		/*Minecraft.getMinecraft().getTextureManager().bindTexture(tank.tank.getSheet());
		tank.tank.renderTank(this, guiLeft + 71, guiTop + 69, tank.tank.getTankType().textureX() * FluidTank.x, tank.tank.getTankType().textureY() * FluidTank.y, 16, 52);
		tank.tank.renderTank(this, guiLeft + 71 + 16, guiTop + 69, tank.tank.getTankType().textureX() * FluidTank.x, tank.tank.getTankType().textureY() * FluidTank.y, 16, 52);
		tank.tank.renderTank(this, guiLeft + 71 + 32, guiTop + 69, tank.tank.getTankType().textureX() * FluidTank.x, tank.tank.getTankType().textureY() * FluidTank.y, 2, 52);*/
		
		this.tank.tank.renderTank(this.guiLeft + 71, this.guiTop + 69, this.zLevel, 34, 52);
	}
}
