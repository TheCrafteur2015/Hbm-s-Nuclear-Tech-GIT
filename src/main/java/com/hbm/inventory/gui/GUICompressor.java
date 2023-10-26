package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerCompressor;
import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityMachineCompressor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUICompressor extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_compressor.png");
	private TileEntityMachineCompressor compressor;

	public GUICompressor(InventoryPlayer invPlayer, TileEntityMachineCompressor tedf) {
		super(new ContainerCompressor(invPlayer, tedf));
		this.compressor = tedf;
		
		this.xSize = 176;
		this.ySize = 204;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.compressor.tanks[0].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 17, this.guiTop + 18, 16, 52);
		this.compressor.tanks[1].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 107, this.guiTop + 18, 16, 52);
		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 152, this.guiTop + 18, 16, 52, this.compressor.power, TileEntityMachineCompressor.maxPower);

		for(int j = 0; j < 5; j++) drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 43 + j * 11, this.guiTop + 46, 8, 14, mouseX, mouseY, j + " PU -> " + (j + 1) + " PU");
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
		
		for(int j = 0; j < 5; j++) {
	
			if(this.guiLeft + 43 + j * 11 <= x && this.guiLeft + 43 + 8 + j * 11 > x && this.guiTop + 46 < y && this.guiTop + 46 + 14 >= y) {

				this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
				NBTTagCompound data = new NBTTagCompound();
				data.setInteger("compression", j);
				PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, this.compressor.xCoord, this.compressor.yCoord, this.compressor.zCoord));
			}
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		
		String name = this.compressor.hasCustomInventoryName() ? this.compressor.getInventoryName() : I18n.format(this.compressor.getInventoryName());
		
		this.fontRendererObj.drawString(name, 70 - this.fontRendererObj.getStringWidth(name) / 2, 6, 0xC7C1A3);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUICompressor.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		if(this.compressor.power >= this.compressor.powerRequirement) {
			drawTexturedModalRect(this.guiLeft + 156, this.guiTop + 4, 176, 52, 9, 12);
		}

		drawTexturedModalRect(this.guiLeft + 43 + this.compressor.tanks[0].getPressure() * 11, this.guiTop + 46, 193, 18, 8, 124);
		
		int i = this.compressor.progress * 55 / this.compressor.processTime;
		drawTexturedModalRect(this.guiLeft + 42, this.guiTop + 26, 192, 0, i, 17);
		
		int j = (int) (this.compressor.power * 52 / TileEntityMachineCompressor.maxPower);
		drawTexturedModalRect(this.guiLeft + 152, this.guiTop + 70 - j, 176, 52 - j, 16, j);
		
		this.compressor.tanks[0].renderTank(this.guiLeft + 17, this.guiTop + 70, this.zLevel, 16, 52);
		this.compressor.tanks[1].renderTank(this.guiLeft + 107, this.guiTop + 70, this.zLevel, 16, 52);
	}
}
