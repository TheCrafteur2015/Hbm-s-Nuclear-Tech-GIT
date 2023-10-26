package com.hbm.inventory.gui;

import java.util.Arrays;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityRadioRec;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIRadioRec extends GuiScreen {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_radio.png");
	protected TileEntityRadioRec radio;
	protected int xSize = 256;
	protected int ySize = 204;
	protected int guiLeft;
	protected int guiTop;
	protected GuiTextField frequency;
	
	public GUIRadioRec(TileEntityRadioRec radio) {
		this.radio = radio;

		this.xSize = 220;
		this.ySize = 42;
	}

	@Override
	public void initGui() {
		super.initGui();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;

		Keyboard.enableRepeatEvents(true);
		
		int oX = 4;
		int oY = 4;

		this.frequency = new GuiTextField(this.fontRendererObj, this.guiLeft + 25 + oX, this.guiTop + 17 + oY, 90 - oX * 2, 14);
		this.frequency.setTextColor(0x00ff00);
		this.frequency.setDisabledTextColour(0x00ff00);
		this.frequency.setEnableBackgroundDrawing(false);
		this.frequency.setMaxStringLength(10);
		this.frequency.setText(this.radio.channel == null ? "" : this.radio.channel);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		drawDefaultBackground();
		drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		GL11.glDisable(GL11.GL_LIGHTING);
		drawGuiContainerForegroundLayer(mouseX, mouseY);
		GL11.glEnable(GL11.GL_LIGHTING);
	}


	private void drawGuiContainerForegroundLayer(int x, int y) {
		String name = I18nUtil.resolveKey("container.radio");
		this.fontRendererObj.drawString(name, this.guiLeft + this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, this.guiTop + 6, 4210752);

		if(this.guiLeft + 137 <= x && this.guiLeft + 137 + 18 > x && this.guiTop + 17 < y && this.guiTop + 17 + 18 >= y) {
			func_146283_a(Arrays.asList(new String[] { "Save Settings" }), x, y);
		}
		if(this.guiLeft + 173 <= x && this.guiLeft + 173 + 18 > x && this.guiTop + 17 < y && this.guiTop + 17 + 18 >= y) {
			func_146283_a(Arrays.asList(new String[] { "Toggle" }), x, y);
		}
	}

	private void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIRadioRec.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		if(this.radio.isOn) {
			drawTexturedModalRect(this.guiLeft + 173, this.guiTop + 17, 0, 42, 18, 18);
		}
		
		this.frequency.drawTextBox();
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
		
		this.frequency.mouseClicked(x, y, i);
		
		if(this.guiLeft + 137 <= x && this.guiLeft + 137 + 18 > x && this.guiTop + 17 < y && this.guiTop + 17 + 18 >= y) {
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setString("channel", this.frequency.getText());
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, this.radio.xCoord, this.radio.yCoord, this.radio.zCoord));
		}
		
		if(this.guiLeft + 173 <= x && this.guiLeft + 173 + 18 > x && this.guiTop + 17 < y && this.guiTop + 17 + 18 >= y) {
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("isOn", !this.radio.isOn);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, this.radio.xCoord, this.radio.yCoord, this.radio.zCoord));
		}
	}

	@Override
	protected void keyTyped(char c, int i) {
		
		if(this.frequency.textboxKeyTyped(c, i))
			return;
		
		if(i == 1 || i == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			this.mc.thePlayer.closeScreen();
			this.mc.setIngameFocus();
		}
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
