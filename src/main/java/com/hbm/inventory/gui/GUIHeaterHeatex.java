package com.hbm.inventory.gui;

import java.util.Arrays;

import org.apache.commons.lang3.math.NumberUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerHeaterHeatex;
import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityHeaterHeatex;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIHeaterHeatex extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_heatex.png");
	private TileEntityHeaterHeatex heater;
	private GuiTextField fieldCycles;
	private GuiTextField fieldDelay;

	public GUIHeaterHeatex(InventoryPlayer invPlayer, TileEntityHeaterHeatex tedf) {
		super(new ContainerHeaterHeatex(invPlayer, tedf));
		this.heater = tedf;
		
		this.xSize = 176;
		this.ySize = 204;
	}

	@Override
	public void initGui() {

		super.initGui();

		Keyboard.enableRepeatEvents(true);
		this.fieldCycles = new GuiTextField(this.fontRendererObj, this.guiLeft + 74, this.guiTop + 31, 28, 10);
		initText(this.fieldCycles);
		this.fieldCycles.setText(String.valueOf(this.heater.amountToCool));
		
		this.fieldDelay = new GuiTextField(this.fontRendererObj, this.guiLeft + 74, this.guiTop + 49, 28, 10);
		initText(this.fieldDelay);
		this.fieldDelay.setText(String.valueOf(this.heater.tickDelay));
	}
	
	protected void initText(GuiTextField field) {
		field.setTextColor(0x00ff00);
		field.setDisabledTextColour(0x00ff00);
		field.setEnableBackgroundDrawing(false);
		field.setMaxStringLength(4);
	}

	@Override
	public void drawScreen(int x, int y, float f) {
		super.drawScreen(x, y, f);

		this.heater.tanks[0].renderTankInfo(this, x, y, this.guiLeft + 44, this.guiTop + 36, 16, 52);
		this.heater.tanks[1].renderTankInfo(this, x, y, this.guiLeft + 116, this.guiTop + 36, 16, 52);

		if(this.guiLeft + 70 <= x && this.guiLeft + 70 + 36 > x && this.guiTop + 26 < y && this.guiTop + 26 + 18 >= y) {
			func_146283_a(Arrays.asList(new String[] { "Amount per cycle" }), x, y);
		}
		if(this.guiLeft + 70 <= x && this.guiLeft + 70 + 36 > x && this.guiTop + 44 < y && this.guiTop + 44 + 18 >= y) {
			func_146283_a(Arrays.asList(new String[] { "Cycle tick delay" }), x, y);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format(this.heater.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIHeaterHeatex.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		this.heater.tanks[0].renderTank(this.guiLeft + 44, this.guiTop + 88, this.zLevel, 16, 52);
		this.heater.tanks[1].renderTank(this.guiLeft + 116, this.guiTop + 88, this.zLevel, 16, 52);

		this.fieldCycles.drawTextBox();
		this.fieldDelay.drawTextBox();
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		this.fieldCycles.mouseClicked(x, y, i);
		this.fieldDelay.mouseClicked(x, y, i);
	}

	@Override
	protected void keyTyped(char c, int i) {

		if(this.fieldCycles.textboxKeyTyped(c, i)) {
			int cyc = Math.max(NumberUtils.toInt(this.fieldCycles.getText()), 1);
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("toCool", cyc);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, this.heater.xCoord, this.heater.yCoord, this.heater.zCoord));
			return;
		}
		if(this.fieldDelay.textboxKeyTyped(c, i)) {
			int delay = Math.max(NumberUtils.toInt(this.fieldDelay.getText()), 1);
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("delay", delay);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, this.heater.xCoord, this.heater.yCoord, this.heater.zCoord));
			return;
		}
		
		super.keyTyped(c, i);
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
}
