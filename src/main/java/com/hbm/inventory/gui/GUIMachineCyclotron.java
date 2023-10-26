package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineCyclotron;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityMachineCyclotron;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineCyclotron extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_cyclotron.png");
	private TileEntityMachineCyclotron cyclotron;

	public GUIMachineCyclotron(InventoryPlayer invPlayer, TileEntityMachineCyclotron tile) {
		super(new ContainerMachineCyclotron(invPlayer, tile));
		this.cyclotron = tile;

		this.xSize = 176;
		this.ySize = 222;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 80, this.guiTop + 72, 7, 52, this.cyclotron.power, TileEntityMachineCyclotron.maxPower);

		this.cyclotron.coolant.renderTankInfo(this, mouseX, mouseY, this.guiLeft + 53, this.guiTop + 72, 7, 52);
		this.cyclotron.amat.renderTankInfo(this, mouseX, mouseY, this.guiLeft + 134, this.guiTop + 90, 7, 34);

		String[] upgradeText = new String[4];
		upgradeText[0] = I18nUtil.resolveKey("desc.gui.upgrade");
		upgradeText[1] = I18nUtil.resolveKey("desc.gui.upgrade.speed");
		upgradeText[2] = I18nUtil.resolveKey("desc.gui.upgrade.effectiveness");
		upgradeText[3] = I18nUtil.resolveKey("desc.gui.upgrade.power");
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 21, this.guiTop + 75, 8, 8, mouseX, mouseY, upgradeText);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.cyclotron.hasCustomInventoryName() ? this.cyclotron.getInventoryName() : I18n.format(this.cyclotron.getInventoryName());

		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		if(this.guiLeft + 97 <= x && this.guiLeft + 97 + 18 > x && this.guiTop + 107 < y && this.guiTop + 107 + 18 >= y) {

			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.cyclotron.xCoord, this.cyclotron.yCoord, this.cyclotron.zCoord, 0, 0));
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMachineCyclotron.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		int k = (int) this.cyclotron.getPowerScaled(52);
		drawTexturedModalRect(this.guiLeft + 80, this.guiTop + 124 - k, 212, 52 - k, 7, k);

		int l = this.cyclotron.getProgressScaled(36);
		drawTexturedModalRect(this.guiLeft + 52, this.guiTop + 26, 176, 0, l, 36);

		if(this.cyclotron.isOn)
			drawTexturedModalRect(this.guiLeft + 97, this.guiTop + 107, 219, 0, 18, 18);

		drawInfoPanel(this.guiLeft + 21, this.guiTop + 75, 8, 8, 8);

		this.cyclotron.coolant.renderTank(this.guiLeft + 53, this.guiTop + 124, this.zLevel, 7, 52);
		this.cyclotron.amat.renderTank(this.guiLeft + 134, this.guiTop + 124, this.zLevel, 7, 34);
	}
}
