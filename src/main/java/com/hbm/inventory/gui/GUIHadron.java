package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerHadron;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityHadron;
import com.hbm.tileentity.machine.TileEntityHadron.EnumHadronState;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIHadron extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_hadron.png");
	private TileEntityHadron hadron;
	
	public GUIHadron(InventoryPlayer invPlayer, TileEntityHadron laser) {
		super(new ContainerHadron(invPlayer, laser));
		this.hadron = laser;

		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 62, this.guiTop + 108, 70, 16, this.hadron.power, TileEntityHadron.maxPower);

		if(this.hadron.hopperMode)
			this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 142, this.guiTop + 89, 18, 18, mouseX, mouseY, I18nUtil.resolveKeyArray("hadron.hopper1"));
		else
			this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 142, this.guiTop + 89, 18, 18, mouseX, mouseY, I18nUtil.resolveKeyArray("hadron.hopper0"));
		
		if(this.hadron.analysisOnly)
			this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 142, this.guiTop + 107, 18, 18, mouseX, mouseY, I18nUtil.resolveKeyArray("hadron.modeLine"));
		else
			this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 142, this.guiTop + 107, 18, 18, mouseX, mouseY, I18nUtil.resolveKeyArray("hadron.modeCircular"));
		
		List<String> stats = new ArrayList<>();
		stats.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("hadron.stats"));
		stats.add((this.hadron.stat_success ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + I18n.format("hadron." + this.hadron.stat_state.name().toLowerCase(Locale.US)));
		if(this.hadron.state.showCoord) stats.add(EnumChatFormatting.RED + I18nUtil.resolveKey("hadron.stats_coord", this.hadron.stat_x, this.hadron.stat_y, this.hadron.stat_z));
		stats.add(EnumChatFormatting.GRAY + I18nUtil.resolveKey("hadron.stats_momentum", String.format(Locale.US, "%,d", this.hadron.stat_charge)));
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 41, this.guiTop + 92, 25, 11, mouseX, mouseY, stats.toArray(new String[0]));

		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 4, this.guiTop + 36, 16, 16, this.guiLeft + 4, this.guiTop + 36 + 16, new String[] {"Initial particle momentum: 750"});
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		// Toggle hadron
		if(this.guiLeft + 19 <= x && this.guiLeft + 19 + 18 > x && this.guiTop + 89 < y && this.guiTop + 89 + 18 >= y) {
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.hadron.xCoord, this.hadron.yCoord, this.hadron.zCoord, 0, 0));
		}

		// Toggle analysis chamber
		if(this.guiLeft + 142 <= x && this.guiLeft + 142 + 18 > x && this.guiTop + 107 < y && this.guiTop + 107 + 18 >= y) {
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.hadron.xCoord, this.hadron.yCoord, this.hadron.zCoord, 0, 1));
		}

		// Toggle hopper mode
		if(this.guiLeft + 142 <= x && this.guiLeft + 142 + 18 > x && this.guiTop + 89 < y && this.guiTop + 89 + 18 >= y) {
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.hadron.xCoord, this.hadron.yCoord, this.hadron.zCoord, 0, 2));
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.hadron.hasCustomInventoryName() ? this.hadron.getInventoryName() : I18n.format(this.hadron.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		
		String state = I18n.format("hadron." + this.hadron.state.name().toLowerCase(Locale.US));
		this.fontRendererObj.drawString(state, this.xSize / 2 - this.fontRendererObj.getStringWidth(state) / 2, 76, this.hadron.state.color);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIHadron.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		if(this.hadron.isOn)
			drawTexturedModalRect(this.guiLeft + 16, this.guiTop + 89, 206, 0, 18, 18);
		
		if(this.hadron.analysisOnly)
			drawTexturedModalRect(this.guiLeft + 142, this.guiTop + 107, 206, 18, 18, 18);
		
		if(this.hadron.hopperMode)
			drawTexturedModalRect(this.guiLeft + 142, this.guiTop + 89, 206, 36, 18, 18);

		if(this.hadron.state == EnumHadronState.SUCCESS) {
			drawTexturedModalRect(this.guiLeft + 73, this.guiTop + 29, 176, 0, 30, 30);
		}
		if(this.hadron.state == EnumHadronState.NORESULT) {
			drawTexturedModalRect(this.guiLeft + 73, this.guiTop + 29, 176, 30, 30, 30);
		}
		if(this.hadron.state == EnumHadronState.ERROR_GENERIC) {
			drawTexturedModalRect(this.guiLeft + 73, this.guiTop + 29, 176, 106, 30, 30);
		}
		
		int i = this.hadron.getPowerScaled(70);
		drawTexturedModalRect(this.guiLeft + 62, this.guiTop + 108, 176, 60, i, 16);

		int color = this.hadron.state.color;
		float red = (color & 0xff0000) >> 16;
		float green = (color & 0x00ff00) >> 8;
		float blue = (color & 0x0000ff);
		
		GL11.glColor4f(red, green, blue, 1.0F);
		drawTexturedModalRect(this.guiLeft + 45, this.guiTop + 73, 0, 222, 86, 14);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		drawInfoPanel(this.guiLeft - 4, this.guiTop + 36, 16, 16, 2);
	}
}
