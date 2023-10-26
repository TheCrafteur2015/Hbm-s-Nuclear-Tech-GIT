package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerReactorZirnox;
import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityReactorZirnox;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIReactorZirnox extends GuiInfoContainer {

	// fuck you
	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID, "textures/gui/reactors/gui_zirnox.png");
	private TileEntityReactorZirnox zirnox;

	public GUIReactorZirnox(InventoryPlayer invPlayer, TileEntityReactorZirnox tile) {
		super(new ContainerReactorZirnox(invPlayer, tile));
		this.zirnox = tile;

		this.xSize = 203;
		this.ySize = 256;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.zirnox.steam.renderTankInfo(this, mouseX, mouseY, this.guiLeft + 160, this.guiTop + 108, 18, 12);
		this.zirnox.carbonDioxide.renderTankInfo(this, mouseX, mouseY, this.guiLeft + 142, this.guiTop + 108, 18, 12);
		this.zirnox.water.renderTankInfo(this, mouseX, mouseY, this.guiLeft + 178, this.guiTop + 108, 18, 12);
		drawCustomInfo(this, mouseX, mouseY, this.guiLeft + 160, this.guiTop + 33, 18, 17, new String[] { "Temperature:", "   " + Math.round((this.zirnox.heat) * 0.00001 * 780 + 20) + "°C" });
		drawCustomInfo(this, mouseX, mouseY, this.guiLeft + 178, this.guiTop + 33, 18, 17, new String[] { "Pressure:", "   " + Math.round((this.zirnox.pressure) * 0.00001 * 30) + " bar" });
		
		String[] coolantText = I18nUtil.resolveKeyArray("desc.gui.zirnox.coolant");
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 36, 16, 16, this.guiLeft - 8, this.guiTop + 36 + 16, coolantText);
		
		String[] pressureText = I18nUtil.resolveKeyArray("desc.gui.zirnox.pressure");
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 36 + 16, 16, 16, this.guiLeft - 8, this.guiTop + 36 + 16 + 16, pressureText);

		if(this.zirnox.water.getFill() <= 0) {
			String[] warning1 = I18nUtil.resolveKeyArray("desc.gui.zirnox.warning1");
			this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 36 + 32, 16, 16, this.guiLeft - 8, this.guiTop + 36 + 32 + 16, warning1);
		}

		if(this.zirnox.carbonDioxide.getFill() < 4000) {
			String[] warning2 = I18nUtil.resolveKeyArray("desc.gui.zirnox.warning2");
			this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 36 + 32 + 16, 16, 16, this.guiLeft - 8, this.guiTop + 36 + 32 + 16 + 16, warning2);
		}

	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		NBTTagCompound control = new NBTTagCompound();

		if(this.guiLeft + 144 <= x && this.guiLeft + 144 + 14 > x && this.guiTop + 35 < y && this.guiTop + 35 + 14 >= y) {
			control.setBoolean("control", true);

			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(control, this.zirnox.xCoord, this.zirnox.yCoord, this.zirnox.zCoord));
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("hbm:block.rbmk_az5_cover"), 0.5F));
		}

		if(this.guiLeft + 151 <= x && this.guiLeft + 151 + 36 > x && this.guiTop + 51 < y && this.guiTop + 51 + 36 >= y) {
			control.setBoolean("vent", true); // sus impostre like amogus

			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(control, this.zirnox.xCoord, this.zirnox.yCoord, this.zirnox.zCoord));
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("hbm:block.rbmk_az5_cover"), 0.5F));
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.zirnox.hasCustomInventoryName() ? this.zirnox.getInventoryName() : I18n.format(this.zirnox.getInventoryName());

		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIReactorZirnox.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		int s = this.zirnox.getGaugeScaled(6, 0);
		drawTexturedModalRect(this.guiLeft + 160, this.guiTop + 108, 238, 0 + 12 * s, 18, 12);

		int c = this.zirnox.getGaugeScaled(6, 1);
		drawTexturedModalRect(this.guiLeft + 142, this.guiTop + 108, 238, 0 + 12 * c, 18, 12);

		int w = this.zirnox.getGaugeScaled(6, 2);
		drawTexturedModalRect(this.guiLeft + 178, this.guiTop + 108, 238, 0 + 12 * w, 18, 12);

		int h = this.zirnox.getGaugeScaled(12, 3);
		drawTexturedModalRect(this.guiLeft + 160, this.guiTop + 33, 220, 0 + 18 * h, 18, 17);

		int p = this.zirnox.getGaugeScaled(12, 4);
		drawTexturedModalRect(this.guiLeft + 178, this.guiTop + 33, 220, 0 + 18 * p, 18, 17);

		if(this.zirnox.isOn) {
			for(int x = 0; x < 4; x++)
				for(int y = 0; y < 4; y++)
					drawTexturedModalRect(this.guiLeft + 7 + 36 * x, this.guiTop + 15 + 36 * y, 238, 238, 18, 18);
			for(int x = 0; x < 3; x++)
				for(int y = 0; y < 3; y++)
					drawTexturedModalRect(this.guiLeft + 25 + 36 * x, this.guiTop + 33 + 36 * y, 238, 238, 18, 18);
			drawTexturedModalRect(this.guiLeft + 142, this.guiTop + 15, 220, 238, 18, 18);
		}

		drawInfoPanel(this.guiLeft - 16, this.guiTop + 36, 16, 16, 2);
		drawInfoPanel(this.guiLeft - 16, this.guiTop + 36 + 16, 16, 16, 3);

		if(this.zirnox.water.getFill() <= 0)
			drawInfoPanel(this.guiLeft - 16, this.guiTop + 36 + 32, 16, 16, 6);

		if(this.zirnox.carbonDioxide.getFill() <= 4000)
			drawInfoPanel(this.guiLeft - 16, this.guiTop + 36 + 32 + 16, 16, 16, 6);
	}

}