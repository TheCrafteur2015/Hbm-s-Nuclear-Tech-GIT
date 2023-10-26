package com.hbm.inventory.gui;

import java.awt.Color;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerElectrolyserMetal;
import com.hbm.inventory.material.Mats;
import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityElectrolyser;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIElectrolyserMetal extends GuiInfoContainer {
	
	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_electrolyser_metal.png");
	private TileEntityElectrolyser electrolyser;

	public GUIElectrolyserMetal(InventoryPlayer invPlayer, TileEntityElectrolyser electrolyser) {
		super(new ContainerElectrolyserMetal(invPlayer, electrolyser));
		this.electrolyser = electrolyser;

		this.xSize = 210;
		this.ySize = 204;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		this.electrolyser.tanks[3].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 36, this.guiTop + 18, 16, 52);
		
		if(this.electrolyser.leftStack != null) {
			this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 58, this.guiTop + 18, 34, 42, mouseX, mouseY, EnumChatFormatting.YELLOW + I18nUtil.resolveKey(this.electrolyser.leftStack.material.getUnlocalizedName()) + ": " + Mats.formatAmount(this.electrolyser.leftStack.amount, Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)));
		} else {
			this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 58, this.guiTop + 18, 34, 42, mouseX, mouseY, EnumChatFormatting.RED + "Empty");
		}
		
		if(this.electrolyser.rightStack != null) {
			this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 96, this.guiTop + 18, 34, 42, mouseX, mouseY, EnumChatFormatting.YELLOW + I18nUtil.resolveKey(this.electrolyser.rightStack.material.getUnlocalizedName()) + ": " + Mats.formatAmount(this.electrolyser.rightStack.amount, Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)));
		} else {
			this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 96, this.guiTop + 18, 34, 42, mouseX, mouseY, EnumChatFormatting.RED + "Empty");
		}
		
		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 186, this.guiTop + 18, 16, 89, this.electrolyser.power, TileEntityElectrolyser.maxPower);
	}
	
	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		if(this.guiLeft + 8 <= x && this.guiLeft + 8 + 54 > x && this.guiTop + 82 < y && this.guiTop + 82 + 12 >= y) {
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("sgf", true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, this.electrolyser.xCoord, this.electrolyser.yCoord, this.electrolyser.zCoord));
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.electrolyser.hasCustomInventoryName() ? this.electrolyser.getInventoryName() : I18n.format(this.electrolyser.getInventoryName());

		this.fontRendererObj.drawString(name, (this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2) - 16, 7, 0xffffff);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 94, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIElectrolyserMetal.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		if(this.electrolyser.leftStack != null) {
			int p = this.electrolyser.leftStack.amount * 42 / this.electrolyser.maxMaterial;
			Color color = new Color(this.electrolyser.leftStack.material.moltenColor);
			GL11.glColor3f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
			drawTexturedModalRect(this.guiLeft + 58, this.guiTop + 60 - p, 210, 131 - p, 34, p);
		}
		
		if(this.electrolyser.rightStack != null) {
			int p = this.electrolyser.rightStack.amount * 42 / this.electrolyser.maxMaterial;
			Color color = new Color(this.electrolyser.rightStack.material.moltenColor);
			GL11.glColor3f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
			drawTexturedModalRect(this.guiLeft + 96, this.guiTop + 60 - p, 210, 131 - p, 34, p);
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		int p = (int) (this.electrolyser.power * 89 / TileEntityElectrolyser.maxPower);
		drawTexturedModalRect(this.guiLeft + 186, this.guiTop + 107 - p, 210, 89 - p, 16, p);
		
		if(this.electrolyser.power >= this.electrolyser.usage)
			drawTexturedModalRect(this.guiLeft + 190, this.guiTop + 4, 226, 25, 9, 12);
		
		int o = this.electrolyser.progressOre * 26 / this.electrolyser.processOreTime;
		drawTexturedModalRect(this.guiLeft + 7, this.guiTop + 71 - o, 226, 25 - o, 22, o);
		
		this.electrolyser.tanks[3].renderTank(this.guiLeft + 36, this.guiTop + 70, this.zLevel, 16, 52);
	}
}
