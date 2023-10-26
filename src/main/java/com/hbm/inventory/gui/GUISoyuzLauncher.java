package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerSoyuzLauncher;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntitySoyuzLauncher;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUISoyuzLauncher extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_soyuz.png");
	private TileEntitySoyuzLauncher launcher;
	
	public GUISoyuzLauncher(InventoryPlayer invPlayer, TileEntitySoyuzLauncher tedf) {
		super(new ContainerSoyuzLauncher(invPlayer, tedf));
		this.launcher = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.launcher.tanks[0].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 8, this.guiTop + 36, 16, 52);
		this.launcher.tanks[1].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 26, this.guiTop + 36, 16, 52);
		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 49, this.guiTop + 72, 6, 34, this.launcher.power, TileEntitySoyuzLauncher.maxPower);

		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 43, this.guiTop + 17, 18, 18, mouseX, mouseY, new String[]{"The Soyuz goes here"} );
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 43, this.guiTop + 35, 18, 18, mouseX, mouseY, new String[]{"Designator only for CARGO MODE"} );
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 133, this.guiTop + 17, 18, 18, mouseX, mouseY, new String[]{"The payload for SATELLITE MODE"} );
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 133, this.guiTop + 35, 18, 18, mouseX, mouseY, new String[]{"The orbital module for special payloads"} );
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 88, this.guiTop + 17, 18, 18, mouseX, mouseY, new String[]{"SATELLITE MODE"} );
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 88, this.guiTop + 35, 18, 18, mouseX, mouseY, new String[]{"CARGO MODE"} );
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);
		
    	if(this.guiLeft + 88 <= x && this.guiLeft + 88 + 18 > x && this.guiTop + 17 < y && this.guiTop + 17 + 18 >= y) {
    		
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.launcher.xCoord, this.launcher.yCoord, this.launcher.zCoord, 0, 0));
    	}
		
    	if(this.guiLeft + 88 <= x && this.guiLeft + 88 + 18 > x && this.guiTop + 35 < y && this.guiTop + 35 + 18 >= y) {
    		
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.launcher.xCoord, this.launcher.yCoord, this.launcher.zCoord, 1, 0));
    	}
		
    	if(this.guiLeft + 151 <= x && this.guiLeft + 151 + 18 > x && this.guiTop + 17 < y && this.guiTop + 17 + 18 >= y) {
    		
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.launcher.xCoord, this.launcher.yCoord, this.launcher.zCoord, 0, 1));
    	}
    }

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.launcher.hasCustomInventoryName() ? this.launcher.getInventoryName() : I18n.format(this.launcher.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		
		String secs = "" + this.launcher.countdown / 20;
		String cents = "" + (this.launcher.countdown % 20) * 5;
		if(secs.length() == 1)
			secs = "0" + secs;
		if(cents.length() == 1)
			cents += "0";
		
		float scale = 0.5F;
		GL11.glScalef(scale, scale, 1);
		this.fontRendererObj.drawString(secs + ":" + cents, (int)(153.5F / scale), (int)(37.5F / scale), 0xff0000);
		GL11.glScalef(1/scale, 1/scale, 1);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUISoyuzLauncher.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int i = (int)this.launcher.getPowerScaled(34);
		drawTexturedModalRect(this.guiLeft + 49, this.guiTop + 106 - i, 194, 52 - i, 6, i);
		
		drawTexturedModalRect(this.guiLeft + 61, this.guiTop + 17, 176 + (this.launcher.hasRocket() ? 18 : 0), 0, 18, 18);
		int j = this.launcher.designator();
		
		if(j > 0)
			drawTexturedModalRect(this.guiLeft + 61, this.guiTop + 35, 176 + (j - 1) * 18, 0, 18, 18);
		
		int k = this.launcher.mode;
		drawTexturedModalRect(this.guiLeft + 88, this.guiTop + 17 + k * 18, 176, 18 + k * 18, 18, 18);
		
		int l = this.launcher.orbital();
		
		if(l > 0)
			drawTexturedModalRect(this.guiLeft + 115, this.guiTop + 35, 176 + (l - 1) * 18, 0, 18, 18);
		
		int m = this.launcher.satellite();
		
		if(m > 0)
			drawTexturedModalRect(this.guiLeft + 115, this.guiTop + 17, 176 + (m - 1) * 18, 0, 18, 18);
		
		if(this.launcher.starting)
			drawTexturedModalRect(this.guiLeft + 151, this.guiTop + 17, 176, 54, 18, 18);
		
		if(this.launcher.hasFuel())
			drawTexturedModalRect(this.guiLeft + 13, this.guiTop + 23, 212, 0, 6, 8);
		else
			drawTexturedModalRect(this.guiLeft + 13, this.guiTop + 23, 218, 0, 6, 8);
		
		if(this.launcher.hasOxy())
			drawTexturedModalRect(this.guiLeft + 31, this.guiTop + 23, 212, 0, 6, 8);
		else
			drawTexturedModalRect(this.guiLeft + 31, this.guiTop + 23, 218, 0, 6, 8);
		
		if(this.launcher.hasPower())
			drawTexturedModalRect(this.guiLeft + 49, this.guiTop + 59, 212, 0, 6, 8);
		else
			drawTexturedModalRect(this.guiLeft + 49, this.guiTop + 59, 218, 0, 6, 8);
		
		this.launcher.tanks[0].renderTank(this.guiLeft + 8, this.guiTop + 88, this.zLevel, 16, 52);
		this.launcher.tanks[1].renderTank(this.guiLeft + 26, this.guiTop + 88, this.zLevel, 16, 52);
	}
}
