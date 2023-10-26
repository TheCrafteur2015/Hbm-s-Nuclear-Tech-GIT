package com.hbm.inventory.gui;

import java.util.Locale;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerOilburner;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityHeaterOilburner;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIOilburner extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_oilburner.png");
	private TileEntityHeaterOilburner diFurnace;

	public GUIOilburner(InventoryPlayer invPlayer, TileEntityHeaterOilburner tedf) {
		super(new ContainerOilburner(invPlayer, tedf));
		this.diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 203;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);
		
		this.drawCustomInfoStat(x, y, this.guiLeft + 116, this.guiTop + 17, 16, 52, x, y, new String[] { String.format(Locale.US, "%,d", Math.min(this.diFurnace.heatEnergy, TileEntityHeaterOilburner.maxHeatEnergy)) + " / " + String.format(Locale.US, "%,d", TileEntityHeaterOilburner.maxHeatEnergy) + " TU" });

		if(this.diFurnace.tank.getTankType().hasTrait(FT_Flammable.class)) {
			this.drawCustomInfoStat(x, y, this.guiLeft + 79, this.guiTop + 34, 18, 18, x, y, new String[] { this.diFurnace.setting + " mB/t", String.format(Locale.US, "%,d", (int)(this.diFurnace.tank.getTankType().getTrait(FT_Flammable.class).getHeatEnergy() / 1000) * this.diFurnace.setting) + " TU/t" });
		}
		
		this.diFurnace.tank.renderTankInfo(this, x, y, this.guiLeft + 44, this.guiTop + 17, 16, 52);
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
		
		if(this.guiLeft + 80 <= x && this.guiLeft + 80 + 16 > x && this.guiTop + 54 < y && this.guiTop + 54 + 14 >= y) {
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("toggle", true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, this.diFurnace.xCoord, this.diFurnace.yCoord, this.diFurnace.zCoord));
			
		}
		
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
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIOilburner.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int i = this.diFurnace.heatEnergy * 52 / TileEntityHeaterOilburner.maxHeatEnergy;
		drawTexturedModalRect(this.guiLeft + 116, this.guiTop + 69 - i, 194, 52 - i, 16, i);
		
		if(this.diFurnace.isOn) {
			drawTexturedModalRect(this.guiLeft + 70, this.guiTop + 54, 210, 0, 35, 14);

			if(this.diFurnace.tank.getFill() > 0 && this.diFurnace.tank.getTankType().hasTrait(FT_Flammable.class)) {
				drawTexturedModalRect(this.guiLeft + 79, this.guiTop + 34, 176, 0, 18, 18);
			}
		}
		
		this.diFurnace.tank.renderTank(this.guiLeft + 44, this.guiTop + 69, this.zLevel, 16, 52);
	}
}
