package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerITER;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityITER;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIITER extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_fusion_multiblock.png");
	private TileEntityITER iter;
	
	public GUIITER(InventoryPlayer invPlayer, TileEntityITER laser) {
		super(new ContainerITER(invPlayer, laser));
		this.iter = laser;

		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 71, this.guiTop + 108, 34, 16, this.iter.power, TileEntityITER.maxPower);

		this.iter.tanks[0].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 26, this.guiTop + 54, 16, 52);	//Water
		this.iter.tanks[1].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 134, this.guiTop + 54, 16, 52);	//Steam
		this.iter.plasma.renderTankInfo(this, mouseX, mouseY, this.guiLeft + 71, this.guiTop + 54, 34, 34);	//Plasma

		String text = "Magnets are " + ((this.iter.isOn && this.iter.power >= TileEntityITER.powerReq) ? "ON" : "OFF");
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 76, this.guiTop + 94, 24, 12, mouseX, mouseY, new String[] { text });
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);
		
    	if(this.guiLeft + 52 <= x && this.guiLeft + 52 + 18 > x && this.guiTop + 107 < y && this.guiTop + 107 + 18 >= y) {
    		
    		//toggle the magnets
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.iter.xCoord, this.iter.yCoord, this.iter.zCoord, 0, 0));
    	}
    }
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.iter.hasCustomInventoryName() ? this.iter.getInventoryName() : I18n.format(this.iter.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIITER.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		if(this.iter.isOn)
			drawTexturedModalRect(this.guiLeft + 52, this.guiTop + 107, 176, 0, 18, 18);
		
		if(this.iter.isOn && this.iter.power >= TileEntityITER.powerReq)
			drawTexturedModalRect(this.guiLeft + 76, this.guiTop + 94, 194, 0, 24, 12);
		
		if(this.iter.getShield() >= this.iter.plasma.getTankType().temperature)
			drawTexturedModalRect(this.guiLeft + 97, this.guiTop + 17, 218, 0, 18, 18);
		
		int i = (int)this.iter.getPowerScaled(34);
		drawTexturedModalRect(this.guiLeft + 71, this.guiTop + 108, 176, 25, i, 16);
		
		int j = (int)this.iter.getProgressScaled(17);
		drawTexturedModalRect(this.guiLeft + 44, this.guiTop + 22, 176, 18, j, 7);

		for(int t = 0; t < 2; t++) {
			this.iter.tanks[t].renderTank(this.guiLeft + 26 + 108 * t, this.guiTop + 106, this.zLevel, 16, 52);
		}
		
		this.iter.plasma.renderTank(this.guiLeft + 71, this.guiTop + 88, this.zLevel, 34, 34);
	}
}
