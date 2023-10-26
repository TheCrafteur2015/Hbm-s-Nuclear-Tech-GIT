package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineBattery;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.storage.TileEntityMachineBattery;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIMachineBattery extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/storage/gui_battery.png");
	private TileEntityMachineBattery battery;

	public GUIMachineBattery(InventoryPlayer invPlayer, TileEntityMachineBattery tedf) {
		super(new ContainerMachineBattery(invPlayer, tedf));
		this.battery = tedf;

		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 62, this.guiTop + 69 - 52, 52, 52, this.battery.power, this.battery.getMaxPower());

		String deltaText = BobMathUtil.getShortNumber(Math.abs(this.battery.delta)) + "HE/s";

		if(this.battery.delta > 0) deltaText = EnumChatFormatting.GREEN + "+" + deltaText;
		else if(this.battery.delta < 0) deltaText = EnumChatFormatting.RED + "-" + deltaText;
		else deltaText = EnumChatFormatting.YELLOW + "+" + deltaText;

		String[] info = { BobMathUtil.getShortNumber(this.battery.power) + "/" + BobMathUtil.getShortNumber(this.battery.getMaxPower()) + "HE", deltaText };

		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 62, this.guiTop + 69 - 52, 52, 52, mouseX, mouseY, info);
		
		String lang = null;
		switch(this.battery.priority) {
		case LOW: lang = "low"; break;
		case NORMAL: lang = "normal"; break;
		case HIGH: lang = "high"; break;
		}
		
		List<String> priority = new ArrayList<>();
		priority.add(I18nUtil.resolveKey("battery.priority." + lang));
		priority.add(I18nUtil.resolveKey("battery.priority.recommended"));
		String[] desc = I18nUtil.resolveKeyArray("battery.priority." + lang + ".desc");
		for(String s : desc) priority.add(s);
		
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 152, this.guiTop + 35, 16, 16, mouseX, mouseY, priority);
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		if(this.guiLeft + 133 <= x && this.guiLeft + 133 + 18 > x && this.guiTop + 16 < y && this.guiTop + 16 + 18 >= y) {
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.battery.xCoord, this.battery.yCoord, this.battery.zCoord, 0, 0));
		}

		if(this.guiLeft + 133 <= x && this.guiLeft + 133 + 18 > x && this.guiTop + 52 < y && this.guiTop + 52 + 18 >= y) {
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.battery.xCoord, this.battery.yCoord, this.battery.zCoord, 0, 1));
		}

		if(this.guiLeft + 152 <= x && this.guiLeft + 152 + 16 > x && this.guiTop + 35 < y && this.guiTop + 35 + 16 >= y) {
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.battery.xCoord, this.battery.yCoord, this.battery.zCoord, 0, 2));
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.battery.hasCustomInventoryName() ? this.battery.getInventoryName() : I18n.format(this.battery.getInventoryName());
		name += (" (" + this.battery.power + " HE)");

		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMachineBattery.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		if(this.battery.power > 0) {
			int i = (int) this.battery.getPowerRemainingScaled(52);
			drawTexturedModalRect(this.guiLeft + 62, this.guiTop + 69 - i, 176, 52 - i, 52, i);
		}

		int i = this.battery.redLow;
		drawTexturedModalRect(this.guiLeft + 133, this.guiTop + 16, 176, 52 + i * 18, 18, 18);

		int j = this.battery.redHigh;
		drawTexturedModalRect(this.guiLeft + 133, this.guiTop + 52, 176, 52 + j * 18, 18, 18);
		
		drawTexturedModalRect(this.guiLeft + 152, this.guiTop + 35, 194, 52 + this.battery.priority.ordinal() * 16, 16, 16);
	}
}
