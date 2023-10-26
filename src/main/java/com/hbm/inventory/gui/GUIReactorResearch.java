package com.hbm.inventory.gui;

import org.apache.commons.lang3.math.NumberUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerReactorResearch;
import com.hbm.lib.RefStrings;
import com.hbm.module.NumberDisplay;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityReactorResearch;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GUIReactorResearch extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_research_reactor.png");
	private TileEntityReactorResearch reactor;
	private final NumberDisplay[] displays = new NumberDisplay[3];
	byte timer;
	
	private GuiTextField field;

	public GUIReactorResearch(InventoryPlayer invPlayer, TileEntityReactorResearch te) {
		super(new ContainerReactorResearch(invPlayer, te));
		this.reactor = te;
		this.xSize = 176;
		this.ySize = 222;
		this.displays[0] = new NumberDisplay(this, 14, 25, 0x08FF00).setDigitLength(4);
		this.displays[1] = new NumberDisplay(this, 12, 63, 0x08FF00).setDigitLength(3);
		this.displays[2] = new NumberDisplay(this, 5, 101, 0x08FF00).setDigitLength(3);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		
		Keyboard.enableRepeatEvents(true);
		
		this.field = new GuiTextField(this.fontRendererObj, this.guiLeft + 8, this.guiTop + 99, 33, 16);
		this.field.setEnableBackgroundDrawing(false);
		this.field.setMaxStringLength(3);
		
		this.field.setText(String.valueOf((int)(this.reactor.level * 100)));
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		String[] text = new String[] {
				"The reactor has to be submerged",
				"in water on its sides to cool.",
				"The neutron flux is provided to",
				"adjacent breeding reactors."
		};
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 14, this.guiTop + 23, 16, 16, this.guiLeft - 6, this.guiTop + 23 + 16, text);
		
		String[] text2 = new String[] {
				"This reactor is fueled with plate fuel.",
				"The reaction needs a neutron source to start."
		};
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 14, this.guiTop + 61, 16, 16, this.guiLeft - 6, this.guiTop + 61 + 16, text2);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.reactor.hasCustomInventoryName() ? this.reactor.getInventoryName() : I18n.format(this.reactor.getInventoryName());
		final String[] labels = { "Flux", "Heat", "Control" };
		
		this.fontRendererObj.drawString(name, 121 - this.fontRendererObj.getStringWidth(name) / 2, 6, 15066597);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		this.fontRendererObj.drawString(labels[0], 6, 13, 15066597);
		this.fontRendererObj.drawString(labels[1], 6, 51, 15066597);
		this.fontRendererObj.drawString(labels[2], 6, 89, 15066597);
	}

    @Override
	protected void mouseClicked(int mouseX, int mouseY, int i) {
    	super.mouseClicked(mouseX, mouseY, i);
    	this.field.mouseClicked(mouseX, mouseY, i);
    	
    	if(this.guiLeft + 8 <= mouseX && this.guiLeft + 8 + 33 > mouseX && this.guiTop + 99 < mouseY && this.guiTop + 99 + 16 >= mouseY)
    		this.displays[2].setBlinks(true);
    	else
    		this.displays[2].setBlinks(false);
    	
    	if(this.guiLeft + 44 <= mouseX && this.guiLeft + 44 + 11 > mouseX && this.guiTop + 97 < mouseY && this.guiTop + 97 + 20 >= mouseY) {
			
    		double level;
    		
			if(NumberUtils.isNumber(this.field.getText())) {
				int j = (int)MathHelper.clamp_double(Double.parseDouble(this.field.getText()), 0, 100);
				this.field.setText(j + "");
				level = j * 0.01D;
			} else {
				return;
			}
			
			NBTTagCompound control = new NBTTagCompound();
			control.setDouble("level", level);
			this.timer = 15;
			
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(control, this.reactor.xCoord, this.reactor.yCoord, this.reactor.zCoord));
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("hbm:block.rbmk_az5_cover"), 0.5F));
		}
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIReactorResearch.texture);
		
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		if(this.reactor.level <= 0.5D) {
			for(int x = 0; x < 3; x++)
				for(int y = 0; y < 3; y++)
					drawTexturedModalRect(this.guiLeft + 81 + 36 * x, this.guiTop + 26 + 36 * y, 176, 0, 8, 8);
			for(int x = 0; x < 2; x++)
				for(int y = 0; y < 2; y++)
					drawTexturedModalRect(this.guiLeft + 99 + 36 * x, this.guiTop + 44 + 36 * y, 176, 0, 8, 8);
		}
		
		if(this.timer > 0) {
			drawTexturedModalRect(this.guiLeft + 44, this.guiTop + 97, 176, 8, 11, 20);
			this.timer--;
		}
		
		for(byte i = 0; i < 2; i++)
			this.displays[i].drawNumber(this.reactor.getDisplayData()[i]);
		
		if(NumberUtils.isDigits(this.field.getText())) {
			int level = (int)MathHelper.clamp_double(Double.parseDouble(this.field.getText()), 0, 100);
			this.field.setText(level + "");
			this.displays[2].drawNumber(level);
		} else {
			this.field.setText(0 + "");
			this.displays[2].drawNumber(0);
		}

		drawInfoPanel(this.guiLeft - 14, this.guiTop + 23, 16, 16, 3);
		drawInfoPanel(this.guiLeft - 14, this.guiTop + 61, 16, 16, 2);
		
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
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
}
