package com.hbm.inventory.gui;

import java.util.Arrays;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.network.TileEntityRadioTorchBase;
import com.hbm.tileentity.network.TileEntityRadioTorchSender;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIScreenRadioTorch extends GuiScreen {

	protected ResourceLocation texture;
	protected static final ResourceLocation textureSender = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_rtty_sender.png");
	protected static final ResourceLocation textureReceiver = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_rtty_receiver.png");
	protected TileEntityRadioTorchBase radio;
	protected String title = "";
	protected int xSize = 256;
	protected int ySize = 204;
	protected int guiLeft;
	protected int guiTop;
	protected GuiTextField frequency;
	protected GuiTextField[] remap;
	
	public GUIScreenRadioTorch(TileEntityRadioTorchBase radio) {
		this.radio = radio;
		
		if(radio instanceof TileEntityRadioTorchSender) {
			this.texture = GUIScreenRadioTorch.textureSender;
			this.title = "container.rttySender";
		} else {
			this.texture = GUIScreenRadioTorch.textureReceiver;
			this.title = "container.rttyReceiver";
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;

		Keyboard.enableRepeatEvents(true);
		
		int oX = 4;
		int oY = 4;
		int in = this.radio instanceof TileEntityRadioTorchSender ? 18 : 0;

		this.frequency = new GuiTextField(this.fontRendererObj, this.guiLeft + 25 + oX, this.guiTop + 17 + oY, 90 - oX * 2, 14);
		this.frequency.setTextColor(0x00ff00);
		this.frequency.setDisabledTextColour(0x00ff00);
		this.frequency.setEnableBackgroundDrawing(false);
		this.frequency.setMaxStringLength(10);
		this.frequency.setText(this.radio.channel == null ? "" : this.radio.channel);
		
		this.remap = new GuiTextField[16];
		
		for(int i = 0; i < 16; i++) {
			this.remap[i] = new GuiTextField(this.fontRendererObj, this.guiLeft + 7 + (130 * (i / 8)) + oX + in, this.guiTop + 53 + (18 * (i % 8)) + oY, 90 - oX * 2, 14);
			this.remap[i].setTextColor(0x00ff00);
			this.remap[i].setDisabledTextColour(0x00ff00);
			this.remap[i].setEnableBackgroundDrawing(false);
			this.remap[i].setMaxStringLength(15);
			this.remap[i].setText(this.radio.mapping[i] == null ? "" : this.radio.mapping[i]);
		}
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
		String name = I18nUtil.resolveKey(this.title);
		this.fontRendererObj.drawString(name, this.guiLeft + this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, this.guiTop + 6, 4210752);

		if(this.guiLeft + 137 <= x && this.guiLeft + 137 + 18 > x && this.guiTop + 17 < y && this.guiTop + 17 + 18 >= y) {
			func_146283_a(Arrays.asList(new String[] { this.radio.customMap ? "Custom Mapping" : "Redstone Passthrough" }), x, y);
		}
		if(this.guiLeft + 173 <= x && this.guiLeft + 173 + 18 > x && this.guiTop + 17 < y && this.guiTop + 17 + 18 >= y) {
			func_146283_a(Arrays.asList(new String[] { this.radio.polling ? "Polling" : "State Change" }), x, y);
		}
		if(this.guiLeft + 209 <= x && this.guiLeft + 209 + 18 > x && this.guiTop + 17 < y && this.guiTop + 17 + 18 >= y) {
			func_146283_a(Arrays.asList(new String[] { "Save Settings" }), x, y);
		}
	}

	private void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(this.texture);
		
		if(this.radio.customMap) {
			drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
			drawTexturedModalRect(this.guiLeft + 137, this.guiTop + 17, 0, 204, 18, 18);
			if(this.radio.polling) drawTexturedModalRect(this.guiLeft + 173, this.guiTop + 17, 0, 222, 18, 18);
			for(int j = 0; j < 16; j++) {
				this.remap[j].drawTextBox();
			}
		} else {
			drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, 35);
			drawTexturedModalRect(this.guiLeft, this.guiTop + 35, 0, 197, this.xSize, 7);
			if(this.radio.polling) drawTexturedModalRect(this.guiLeft + 173, this.guiTop + 17, 0, 222, 18, 18);
		}
		
		this.frequency.drawTextBox();
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
		
		this.frequency.mouseClicked(x, y, i);
		
		if(this.radio.customMap) {
			for(int j = 0; j < 16; j++) this.remap[j].mouseClicked(x, y, i);
		}
		
		if(this.guiLeft + 137 <= x && this.guiLeft + 137 + 18 > x && this.guiTop + 17 < y && this.guiTop + 17 + 18 >= y) {
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("m", !this.radio.customMap);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, this.radio.xCoord, this.radio.yCoord, this.radio.zCoord));
		}
		
		if(this.guiLeft + 173 <= x && this.guiLeft + 173 + 18 > x && this.guiTop + 17 < y && this.guiTop + 17 + 18 >= y) {
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("p", !this.radio.polling);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, this.radio.xCoord, this.radio.yCoord, this.radio.zCoord));
		}
		
		if(this.guiLeft + 209 <= x && this.guiLeft + 209 + 18 > x && this.guiTop + 17 < y && this.guiTop + 17 + 18 >= y) {
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setString("c", this.frequency.getText());
			for(int j = 0; j < 16; j++) data.setString("m" + j, this.remap[j].getText());
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, this.radio.xCoord, this.radio.yCoord, this.radio.zCoord));
		}
	}

	@Override
	protected void keyTyped(char c, int i) {
		
		if(this.frequency.textboxKeyTyped(c, i))
			return;

		if(this.radio.customMap) {
			for(int j = 0; j < 16; j++) if(this.remap[j].textboxKeyTyped(c, i)) return;
		}
		
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
