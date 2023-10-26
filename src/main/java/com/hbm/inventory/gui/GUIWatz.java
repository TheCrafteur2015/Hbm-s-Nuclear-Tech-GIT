package com.hbm.inventory.gui;

import java.util.Locale;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerWatz;
import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.util.GaugeUtil;
import com.hbm.render.util.GaugeUtil.Gauge;
import com.hbm.tileentity.machine.TileEntityWatz;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GUIWatz extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_watz.png");
	private TileEntityWatz watz;

	public GUIWatz(InventoryPlayer invPlayer, TileEntityWatz watz) {
		super(new ContainerWatz(invPlayer, watz));
		this.watz = watz;
		
		this.xSize = 176;
		this.ySize = 229;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);

		this.drawCustomInfoStat(x, y, this.guiLeft + 13, this.guiTop + 100, 18, 18, x, y, new String[] { String.format(Locale.US, "%,d", this.watz.heat) + " TU" });
		this.drawCustomInfoStat(x, y, this.guiLeft + 143, this.guiTop + 71, 16, 16, x, y, new String[] { this.watz.isLocked ? "Unlock pellet IO configuration" : "Lock pellet IO configuration" });

		this.watz.tanks[0].renderTankInfo(this, x, y, this.guiLeft + 142, this.guiTop + 23, 6, 45);
		this.watz.tanks[1].renderTankInfo(this, x, y, this.guiLeft + 148, this.guiTop + 23, 6, 45);
		this.watz.tanks[2].renderTankInfo(this, x, y, this.guiLeft + 154, this.guiTop + 23, 6, 45);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 93, 4210752);
		
		double scale = 1.25;
		String flux = String.format(Locale.US, "%,.1f", this.watz.fluxDisplay);
		GL11.glScaled(1 / scale, 1 / scale, 1);
		this.fontRendererObj.drawString(flux, (int) (161 * scale - this.fontRendererObj.getStringWidth(flux)), (int)(107 * scale), 0x00ff00);
		GL11.glScaled(scale, scale, 1);
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		if(this.guiLeft + 142 <= x && this.guiLeft + 142 + 18 > x && this.guiTop + 70 < y && this.guiTop + 70 + 18 >= y) {
			NBTTagCompound control = new NBTTagCompound();
			control.setBoolean("lock", true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(control, this.watz.xCoord, this.watz.yCoord, this.watz.zCoord));
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1F));
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIWatz.texture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float col = MathHelper.clamp_float(1 - (float) Math.log(this.watz.heat / 100_000D + 1) * 0.4F, 0F, 1F);
		GL11.glColor4f(1.0F, col, col, 1.0F);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 131, 122);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		drawTexturedModalRect(this.guiLeft + 131, this.guiTop, 131, 0, 36, 122);
		drawTexturedModalRect(this.guiLeft, this.guiTop + 130, 0, 130, this.xSize, 99);
		drawTexturedModalRect(this.guiLeft + 126, this.guiTop + 31, 176, 31, 9, 60);
		drawTexturedModalRect(this.guiLeft + 105, this.guiTop + 96, 185, 26, 30, 26);
		drawTexturedModalRect(this.guiLeft + 9, this.guiTop + 96, 184, 0, 26, 26);

		if(this.watz.isOn) drawTexturedModalRect(this.guiLeft + 147, this.guiTop + 8, 176, 0, 8, 8);
		if(this.watz.isLocked) drawTexturedModalRect(this.guiLeft + 142, this.guiTop + 70, 210, 0, 18, 18);
		
		GaugeUtil.renderGauge(Gauge.ROUND_SMALL, this.guiLeft + 13, this.guiTop + 100, this.zLevel, 1 - col);

		this.watz.tanks[0].renderTank(this.guiLeft + 143, this.guiTop + 69, this.zLevel, 4, 43);
		this.watz.tanks[1].renderTank(this.guiLeft + 149, this.guiTop + 69, this.zLevel, 4, 43);
		this.watz.tanks[2].renderTank(this.guiLeft + 155, this.guiTop + 69, this.zLevel, 4, 43);
	}
}
