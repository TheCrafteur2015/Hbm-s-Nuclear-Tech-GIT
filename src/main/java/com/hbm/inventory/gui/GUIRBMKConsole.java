package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.math.NumberUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.RBMKColumn;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GUIRBMKConsole extends GuiScreen {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_rbmk_console.png");
	private TileEntityRBMKConsole console;
	protected int guiLeft;
	protected int guiTop;
	protected int xSize;
	protected int ySize;
	
	private boolean[] selection = new boolean[15 * 15];
	private boolean az5Lid = true;
	private long lastPress = 0;
	
	private GuiTextField field;

	public GUIRBMKConsole(InventoryPlayer invPlayer, TileEntityRBMKConsole tedf) {
		super();
		this.console = tedf;
		
		this.xSize = 244;
		this.ySize = 172;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		
		Keyboard.enableRepeatEvents(true);
		
		this.field = new GuiTextField(this.fontRendererObj, this.guiLeft + 9, this.guiTop + 84, 35, 9);
		this.field.setTextColor(0x00ff00);
		this.field.setDisabledTextColour(0x008000);
		this.field.setEnableBackgroundDrawing(false);
		this.field.setMaxStringLength(3);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		drawDefaultBackground();
		drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		
		int bX = 86;
		int bY = 11;
		int size = 10;

		if(this.guiLeft + 86 <= mouseX && this.guiLeft + 86 + 150 > mouseX && this.guiTop + 11 < mouseY && this.guiTop + 11 + 10150 >= mouseY) {
			int index = ((mouseX - bX - this.guiLeft) / size + (mouseY - bY - this.guiTop) / size * 15);
			
			if(index > 0 && index < this.console.columns.length) {
				RBMKColumn col = this.console.columns[index];
				
				if(col != null) {
					
					List<String> list = new ArrayList<>();
					list.add(col.type.toString());
					list.addAll(col.getFancyStats());
					func_146283_a(list, mouseX, mouseY);
				}
			}
		}

		drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 61, this.guiTop + 70, 10, 10, mouseX, mouseY, new String[]{ "Select all control rods" } );
		drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 72, this.guiTop + 70, 10, 10, mouseX, mouseY, new String[]{ "Deselect all" } );
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 2; j++) {
				int id = i * 2 + j + 1;
				drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 6 + 40 * j, this.guiTop + 8 + 21 * i, 18, 18, mouseX, mouseY, new String[]{ EnumChatFormatting.YELLOW + I18nUtil.resolveKey("rbmk.console." + this.console.screens[id - 1].type.name().toLowerCase(Locale.US), id) } );
				drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 24 + 40 * j, this.guiTop + 8 + 21 * i, 18, 18, mouseX, mouseY, new String[]{ I18nUtil.resolveKey("rbmk.console.assign", id) } );
			}
		}
		
		drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 6, this.guiTop + 70, 10, 10, mouseX, mouseY, new String[]{ "Select red group" } );
		drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 17, this.guiTop + 70, 10, 10, mouseX, mouseY, new String[]{ "Select yellow group" } );
		drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 28, this.guiTop + 70, 10, 10, mouseX, mouseY, new String[]{ "Select green group" } );
		drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 39, this.guiTop + 70, 10, 10, mouseX, mouseY, new String[]{ "Select blue group" } );
		drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 50, this.guiTop + 70, 10, 10, mouseX, mouseY, new String[]{ "Select purple group" } );
	}
	
	public void drawCustomInfoStat(int mouseX, int mouseY, int x, int y, int width, int height, int tPosX, int tPosY, String[] text) {
		
		if(x <= mouseX && x + width > mouseX && y < mouseY && y + height >= mouseY)
			func_146283_a(Arrays.asList(text), tPosX, tPosY);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int i) {
		super.mouseClicked(mouseX, mouseY, i);
		this.field.mouseClicked(mouseX, mouseY, i);
		
		int bX = 86;
		int bY = 11;
		int size = 10;

		//toggle column selection
		if(this.guiLeft + 86 <= mouseX && this.guiLeft + 86 + 150 > mouseX && this.guiTop + 11 < mouseY && this.guiTop + 11 + 150 >= mouseY) {
			
			int index = ((mouseX - bX - this.guiLeft) / size + (mouseY - bY - this.guiTop) / size * 15);
			
			if(index > 0 && index < this.selection.length && this.console.columns[index] != null) {
				this.selection[index] = !this.selection[index];
				
				this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 0.75F + (this.selection[index] ? 0.25F : 0.0F)));
				return;
			}
		}
		
		//clear selection
		if(this.guiLeft + 72 <= mouseX && this.guiLeft + 72 + 10 > mouseX && this.guiTop + 70 < mouseY && this.guiTop + 70 + 10 >= mouseY) {
			this.selection = new boolean[15 * 15];
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 0.5F));
			return;
		}
		
		//select all control rods
		if(this.guiLeft + 61 <= mouseX && this.guiLeft + 61 + 10 > mouseX && this.guiTop + 70 < mouseY && this.guiTop + 70 + 10 >= mouseY) {
			this.selection = new boolean[15 * 15];

			for(int j = 0; j < this.console.columns.length; j++) {
				
				if(this.console.columns[j] != null && this.console.columns[j].type == ColumnType.CONTROL) {
					this.selection[j] = true;
				}
			}
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.5F));
			return;
		}
		
		//select color groups
		for(int k = 0; k < 5; k++) {
			
			if(this.guiLeft + 6 + k * 11 <= mouseX && this.guiLeft + 6 + k * 11 + 10 > mouseX && this.guiTop + 70 < mouseY && this.guiTop + 70 + 10 >= mouseY) {
				this.selection = new boolean[15 * 15];

				for(int j = 0; j < this.console.columns.length; j++) {
					
					if(this.console.columns[j] != null && this.console.columns[j].type == ColumnType.CONTROL && this.console.columns[j].data.getShort("color") == k) {
						this.selection[j] = true;
					}
				}
				
				this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 0.8F + k * 0.1F));
				return;
			}
		}

		//AZ-5
		if(this.guiLeft + 30 <= mouseX && this.guiLeft + 30 + 28 > mouseX && this.guiTop + 138 < mouseY && this.guiTop + 138 + 28 >= mouseY) {
			
			if(this.az5Lid) {
				this.az5Lid = false;
				this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("hbm:block.rbmk_az5_cover"), 0.5F));
			} else if(this.lastPress + 3000 < System.currentTimeMillis()) {
				this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("hbm:block.shutdown"), 1));
				this.lastPress = System.currentTimeMillis();
				
				NBTTagCompound control = new NBTTagCompound();
				control.setDouble("level", 0);

				for(int j = 0; j < this.console.columns.length; j++) {
					if(this.console.columns[j] != null && this.console.columns[j].type == ColumnType.CONTROL)
						control.setInteger("sel_" + j, j);
				}
				
				PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(control, this.console.xCoord, this.console.yCoord, this.console.zCoord));
				this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1F));
			}
			return;
		}

		//save control rod setting
		if(this.guiLeft + 48 <= mouseX && this.guiLeft + 48 + 12 > mouseX && this.guiTop + 82 < mouseY && this.guiTop + 82 + 12 >= mouseY) {
			
			double level;
			
			if(NumberUtils.isCreatable(this.field.getText())) {
				int j = (int)MathHelper.clamp_double(Double.parseDouble(this.field.getText()), 0, 100);
				this.field.setText(j + "");
				level = j * 0.01D;
			} else {
				return;
			}
			
			NBTTagCompound control = new NBTTagCompound();
			control.setDouble("level", level);

			for(int j = 0; j < this.selection.length; j++) {
				if(this.selection[j])
					control.setInteger("sel_" + j, j);
			}
			
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(control, this.console.xCoord, this.console.yCoord, this.console.zCoord));
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1F));
		}
		
		//submit selection for status screen
		
		for(int j = 0; j < 3; j++) {
			for(int k = 0; k < 2; k++) {
				
				int id = j * 2 + k;
				
				if(this.guiLeft + 6 + 40 * k <= mouseX && this.guiLeft + 6 + 40 * k + 18 > mouseX && this.guiTop + 8 + 21 * j < mouseY && this.guiTop + 8 + 21 * j + 18 >= mouseY) {
					NBTTagCompound control = new NBTTagCompound();
					control.setByte("toggle", (byte) id);
					PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(control, this.console.xCoord, this.console.yCoord, this.console.zCoord));
					this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 0.5F));
					return;
				}
				
				if(this.guiLeft + 24 + 40 * k <= mouseX && this.guiLeft + 24 + 40 * k + 18 > mouseX && this.guiTop + 8 + 21 * j < mouseY && this.guiTop + 8 + 21 * j + 18 >= mouseY) {

					NBTTagCompound control = new NBTTagCompound();
					control.setByte("id", (byte) id);

					for(int s = 0; s < this.selection.length; s++) {
						if(this.selection[s]) {
							control.setBoolean("s" + s, true);
						}
					}

					PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(control, this.console.xCoord, this.console.yCoord, this.console.zCoord));
					this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 0.75F));
					return;
				}
			}
		}
	}

	@SuppressWarnings("incomplete-switch") //shut up
	protected void drawGuiContainerBackgroundLayer(float interp, int mX, int mY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIRBMKConsole.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		if(this.az5Lid) {
			drawTexturedModalRect(this.guiLeft + 30, this.guiTop + 138, 228, 172, 28, 28);
		}
		
		for(int j = 0; j < 3; j++) {
			for(int k = 0; k < 2; k++) {
				int id = j * 2 + k;
				drawTexturedModalRect(this.guiLeft + 6 + 40 * k, this.guiTop + 8 + 21 * j, this.console.screens[id].type.offset, 238, 18, 18);
			}
		}
		
		int bX = 86;
		int bY = 11;
		int size = 10;
		
		for(int i = 0; i < this.console.columns.length; i++) {
			
			RBMKColumn col = this.console.columns[i];
			
			if(col == null)
				continue;

			int x = bX + size * (i % 15);
			int y = bY + size * (i / 15);
			
			int tX = col.type.offset;
			int tY = 172;
			
			drawTexturedModalRect(this.guiLeft + x, this.guiTop + y, tX, tY, size, size);
			
			int h = Math.min((int)Math.ceil((col.data.getDouble("heat") - 20) * 10 / col.data.getDouble("maxHeat")), 10);
			drawTexturedModalRect(this.guiLeft + x, this.guiTop + y + size - h, 0, 192 - h, 10, h);
			
			switch(col.type) {
			case ABSORBER: break;
			case BLANK: break;
			case MODERATOR: break;
			case REFLECTOR: break;
			case OUTGASSER: break;
			case BREEDER: break;
			
			case CONTROL:
				int color = col.data.getShort("color");
				if(color > -1)
					drawTexturedModalRect(this.guiLeft + x, this.guiTop + y, color * size, 202, 10, 10);
				
			case CONTROL_AUTO:
				int fr = 8 - (int)Math.ceil((col.data.getDouble("level") * 8));
				drawTexturedModalRect(this.guiLeft + x + 4, this.guiTop + y + 1, 24, 183, 2, fr);
				break;

			case FUEL:
			case FUEL_SIM:
				if(col.data.hasKey("c_heat")) {
					int fh = (int)Math.ceil((col.data.getDouble("c_heat") - 20) * 8 / col.data.getDouble("c_maxHeat"));
					if(fh > 8) fh = 8;
					drawTexturedModalRect(this.guiLeft + x + 1, this.guiTop + y + size - fh - 1, 11, 191 - fh, 2, fh);
					
					int fe = (int)Math.ceil((col.data.getDouble("enrichment")) * 8);
					if(fe > 8) fe = 8;
					drawTexturedModalRect(this.guiLeft + x + 4, this.guiTop + y + size - fe - 1, 14, 191 - fe, 2, fe);
				}
				break;
				
			case BOILER:
				int fw = (int)Math.ceil((col.data.getInteger("water")) * 8 / col.data.getDouble("maxWater"));
				drawTexturedModalRect(this.guiLeft + x + 1, this.guiTop + y + size - fw - 1, 41, 191 - fw, 3, fw);
				int fs = (int)Math.ceil((col.data.getInteger("steam")) * 8 / col.data.getDouble("maxSteam"));
				drawTexturedModalRect(this.guiLeft + x + 6, this.guiTop + y + size - fs - 1, 46, 191 - fs, 3, fs);

				if(col.data.getShort("type") == Fluids.STEAM.getID())
					drawTexturedModalRect(this.guiLeft + x + 4, this.guiTop + y + 1, 44, 183, 2, 2);
				if(col.data.getShort("type") == Fluids.HOTSTEAM.getID())
					drawTexturedModalRect(this.guiLeft + x + 4, this.guiTop + y + 3, 44, 185, 2, 2);
				if(col.data.getShort("type") == Fluids.SUPERHOTSTEAM.getID())
					drawTexturedModalRect(this.guiLeft + x + 4, this.guiTop + y + 5, 44, 187, 2, 2);
				if(col.data.getShort("type") == Fluids.ULTRAHOTSTEAM.getID())
					drawTexturedModalRect(this.guiLeft + x + 4, this.guiTop + y + 7, 44, 189, 2, 2);
				
				break;
				
			case HEATEX:
				int cc = (int)Math.ceil((col.data.getInteger("water")) * 8 / col.data.getDouble("maxWater"));
				drawTexturedModalRect(this.guiLeft + x + 1, this.guiTop + y + size - cc - 1, 131, 191 - cc, 3, cc);
				int hc = (int)Math.ceil((col.data.getInteger("steam")) * 8 / col.data.getDouble("maxSteam"));
				drawTexturedModalRect(this.guiLeft + x + 6, this.guiTop + y + size - hc - 1, 136, 191 - hc, 3, hc);
				break;
			}
			
			if(this.selection[i])
				drawTexturedModalRect(this.guiLeft + x, this.guiTop + y, 0, 192, 10, 10);
		}
		
		this.field.drawTextBox();
	}

	@Override
	protected void keyTyped(char c, int i) {

		if(this.field.textboxKeyTyped(c, i))
			return;
		
		if(i == 1 || i == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			this.mc.thePlayer.closeScreen();
			return;
		}
		
		super.keyTyped(c, i);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
}
