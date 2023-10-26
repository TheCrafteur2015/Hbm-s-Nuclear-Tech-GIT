package com.hbm.inventory.gui;

import java.util.Arrays;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.network.TileEntityRadioTelex;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GuiScreenRadioTelex extends GuiScreen {
	
	protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_telex.png");
	protected TileEntityRadioTelex telex;
	protected int xSize = 256;
	protected int ySize = 244;
	protected int guiLeft;
	protected int guiTop;
	protected GuiTextField txFrequency;
	protected GuiTextField rxFrequency;
	protected boolean textFocus = false;
	
	protected String[] txBuffer;
	protected int cursorPos = 0;
	
	public GuiScreenRadioTelex(TileEntityRadioTelex tile) {
		this.telex = tile;
		this.txBuffer = new String[tile.txBuffer.length];
		
		for(int i = 0; i < this.txBuffer.length; i++) {
			this.txBuffer[i] = tile.txBuffer[i];
		}
		
		for(int i = 4; i > 0; i--) {
			if(!this.txBuffer[i].isEmpty()) {
				this.cursorPos = i;
				break;
			}
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;

		Keyboard.enableRepeatEvents(true);

		this.txFrequency = new GuiTextField(this.fontRendererObj, this.guiLeft + 29, this.guiTop + 110, 90, 14);
		this.txFrequency.setTextColor(0x00ff00);
		this.txFrequency.setDisabledTextColour(0x00ff00);
		this.txFrequency.setEnableBackgroundDrawing(false);
		this.txFrequency.setMaxStringLength(10);
		this.txFrequency.setText(this.telex.txChannel == null ? "" : this.telex.txChannel);

		this.rxFrequency = new GuiTextField(this.fontRendererObj, this.guiLeft + 29, this.guiTop + 224, 90, 14);
		this.rxFrequency.setTextColor(0x00ff00);
		this.rxFrequency.setDisabledTextColour(0x00ff00);
		this.rxFrequency.setEnableBackgroundDrawing(false);
		this.rxFrequency.setMaxStringLength(10);
		this.rxFrequency.setText(this.telex.rxChannel == null ? "" : this.telex.rxChannel);
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

		if(checkClick(x, y, 7, 85, 18, 18)) func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.GOLD + "BELL", "Plays a bell when this character is received"}), x, y);
		if(checkClick(x, y, 27, 85, 18, 18)) func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.GOLD + "PRINT", "Forces recipient to print message after transmission ends"}), x, y);
		if(checkClick(x, y, 47, 85, 18, 18)) func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.GOLD + "CLEAR SCREEN", "Wipes message buffer when this character is received"}), x, y);
		if(checkClick(x, y, 67, 85, 18, 18)) func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.GOLD + "FORMAT", "Inserts format character for message formatting"}), x, y);
		if(checkClick(x, y, 87, 85, 18, 18)) func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.GOLD + "PAUSE", "Pauses message transmission for one second"}), x, y);

		if(checkClick(x, y, 127, 105, 18, 18)) func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.GREEN + "SAVE ID"}), x, y);
		if(checkClick(x, y, 147, 105, 18, 18)) func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.YELLOW + "SEND MESSAGE"}), x, y);
		if(checkClick(x, y, 167, 105, 18, 18)) func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.RED + "DELETE MESSAGE BUFFER"}), x, y);
		
		if(checkClick(x, y, 127, 219, 18, 18)) func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.GREEN + "SAVE ID"}), x, y);
		if(checkClick(x, y, 147, 219, 18, 18)) func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.AQUA + "PRINT MESSAGE"}), x, y);
		if(checkClick(x, y, 167, 219, 18, 18)) func_146283_a(Arrays.asList(new String[] {EnumChatFormatting.RED + "CLEAR SCREEN"}), x, y);
	}

	private void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GuiScreenRadioTelex.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		this.txFrequency.drawTextBox();
		this.rxFrequency.drawTextBox();
		
		for(int line = 0; line < 5; line++) {
			String text = this.txBuffer[line];
			int y = 11 + 14 * line;
			
			String format = EnumChatFormatting.RESET + "";
			
			for(int index = 0; index < text.length(); index++) {
				int x = 11 + 7 * index;
				char c = text.charAt(index);
				x += (7 - this.fontRendererObj.getCharWidth(c)) / 2;
				if(c == '§' && text.length() > index + 1) {
					format = "\u00a7" + text.charAt(index + 1);
					x -= 3;
				}
				String glyph = format + c;
				if(c == '\u0007') glyph = EnumChatFormatting.RED + "B";
				if(c == '\u000c') glyph = EnumChatFormatting.RED + "P";
				if(c == '\u007f') glyph = EnumChatFormatting.RED + "<";
				if(c == '\u0016') glyph = EnumChatFormatting.RED + "W";
				this.fontRendererObj.drawString(glyph, this.guiLeft + x, this.guiTop + y, 0x00ff00);
			}

			if(System.currentTimeMillis() % 1000 < 500 && this.textFocus) {
				int x = Math.max(11 + 7 * (text.length() - 1) + 7, 11);
				if(this.cursorPos == line) {
					this.fontRendererObj.drawString("|", this.guiLeft + x, this.guiTop + y, 0x00ff00);
				}
			}
		}
		
		for(int line = 0; line < 5; line++) {
			String text = this.telex.rxBuffer[line];
			int y = 145 + 14 * line;
			
			String format = EnumChatFormatting.RESET + "";
			
			int x = 11;
			
			for(int index = 0; index < text.length(); index++) {
				
				char c = text.charAt(index);
				x += (7 - this.fontRendererObj.getCharWidth(c)) / 2;
				if(c == '§' && text.length() > index + 1) {
					format = "\u00a7" + text.charAt(index + 1);
					c = ' ';
				} else if(c == '§') {
					c = ' ';
				} else if(index > 0 && text.charAt(index - 1) == '§') {
					c = ' ';
					x -= 14;
				}
				String glyph = format + c;
				this.fontRendererObj.drawString(glyph, this.guiLeft + x, this.guiTop + y, 0x00ff00);
				x += 7;
			}
		}

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glLineWidth(3F);
		Random rand = new Random(this.telex.sendingChar);
		Tessellator tess = Tessellator.instance;
		tess.startDrawing(GL11.GL_LINES);
		tess.setColorOpaque_I(0x00ff00);
		double offset = 0;
		for(int i = 0; i < 48; i++) {
			tess.addVertex(this.guiLeft + 199 + i, this.guiTop + 93.5 + offset, this.zLevel + 10);
			if(this.telex.sendingChar != ' ' && i > 4 && i < 43) offset = rand.nextGaussian() * 7; else offset = 0;
			offset = MathHelper.clamp_double(offset, -7D, 7D);
			tess.addVertex(this.guiLeft + 199 + i + 1, this.guiTop + 93.5 + offset, this.zLevel + 10);
		}
		tess.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		this.txFrequency.mouseClicked(x, y, i);
		this.rxFrequency.mouseClicked(x, y, i);
		
		if(this.guiLeft + 7 <= x && this.guiLeft + 7 + 242 > x && this.guiTop + 7 < y && this.guiTop + 7 + 74 >= y) {
			this.textFocus = true;
		} else {
			this.textFocus = false;
		}
		
		char character = '\0';
		String cmd = null;
		
		/* special characters */
		// BEL
		if(checkClick(x, y, 7, 85, 18, 18)) character = '\u0007'; // bell
		// PRT
		if(checkClick(x, y, 27, 85, 18, 18)) character = '\u000c'; // form feed
		// CLS
		if(checkClick(x, y, 47, 85, 18, 18)) character = '\u007f'; // delete
		// FMT
		if(checkClick(x, y, 67, 85, 18, 18)) character = '§'; // minecraft formatting character
		// PSE
		if(checkClick(x, y, 87, 85, 18, 18)) character = '\u0016'; // synchronous idle
		
		// SVE
		if(checkClick(x, y, 127, 105, 18, 18) || checkClick(x, y, 127, 219, 18, 18)) cmd = "sve"; // save channel
		// SND
		if(checkClick(x, y, 147, 105, 18, 18)) cmd = "snd"; // send message in TX buffer
		// DEL
		if(checkClick(x, y, 167, 105, 18, 18)) { // delete message in TX buffer
			cmd = "rxdel";
			for(int j = 0; j < 5; j++) this.txBuffer[j] = "";
			NBTTagCompound data = new NBTTagCompound();
			for(int j = 0; j < 5; j++) data.setString("tx" + j, this.txBuffer[j]);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, this.telex.xCoord, this.telex.yCoord, this.telex.zCoord));
		}
		// PRT
		if(checkClick(x, y, 147, 219, 18, 18)) cmd = "rxprt"; // print message in RX buffer
		// CLS
		if(checkClick(x, y, 167, 219, 18, 18)) cmd = "rxcls"; // delete message in RX buffer
		
		if(cmd != null) {
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setString("cmd", cmd);
			
			if("snd".equals(cmd)) {
				for(int j = 0; j < 5; j++) data.setString("tx" + j, this.txBuffer[j]);
			}
			
			if("sve".equals(cmd)) {
				data.setString("txChan", this.txFrequency.getText());
				data.setString("rxChan", this.rxFrequency.getText());
			}
			
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, this.telex.xCoord, this.telex.yCoord, this.telex.zCoord));
		}
		
		if(character != '\0') {
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			setTextFocus();
			submitChar(character);
		}
	}
	
	protected boolean checkClick(int x, int y, int left, int top, int sizeX, int sizeY) {
		return this.guiLeft + left <= x && this.guiLeft + left + sizeX > x && this.guiTop + top < y && this.guiTop + top + sizeY >= y;
	}
	
	protected void setTextFocus() {
		this.textFocus = true;
		this.txFrequency.setFocused(false);
		this.rxFrequency.setFocused(false);
	}

	@Override
	protected void keyTyped(char c, int i) {

		if(this.txFrequency.textboxKeyTyped(c, i) || this.rxFrequency.textboxKeyTyped(c, i)) return;
		
		if(this.textFocus) {
			
			if(i == 1) {
				this.textFocus = false;
				return;
			}

			if(i == Keyboard.KEY_UP) this.cursorPos--;
			if(i == Keyboard.KEY_DOWN) this.cursorPos++;
			
			this.cursorPos = MathHelper.clamp_int(this.cursorPos, 0, 4);
			
			if(ChatAllowedCharacters.isAllowedCharacter(c)) {
				submitChar(c);
				return;
			}
			
			if(i == Keyboard.KEY_BACK && this.txBuffer[this.cursorPos].length() > 0) {
				this.txBuffer[this.cursorPos] = this.txBuffer[this.cursorPos].substring(0, this.txBuffer[this.cursorPos].length() - 1);
			}
		}
		
		if(i == 1 || i == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			this.mc.thePlayer.closeScreen();
			this.mc.setIngameFocus();
		}
	}
	
	protected void submitChar(char c) {
		String line = this.txBuffer[this.cursorPos];
		
		if(line.length() < TileEntityRadioTelex.lineWidth) {
			this.txBuffer[this.cursorPos] = line + c;
		}
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		NBTTagCompound data = new NBTTagCompound();
		for(int j = 0; j < 5; j++) data.setString("tx" + j, this.txBuffer[j]);
		PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, this.telex.xCoord, this.telex.yCoord, this.telex.zCoord));
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
