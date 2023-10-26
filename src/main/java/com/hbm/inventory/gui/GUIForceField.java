package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerForceField;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityForceField;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIForceField extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_field.png");
	private TileEntityForceField diFurnace;
	
	public GUIForceField(InventoryPlayer invPlayer, TileEntityForceField tedf) {
		super(new ContainerForceField(invPlayer, tedf));
		this.diFurnace = tedf;

		this.xSize = 176;
		this.ySize = 168;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 8, this.guiTop + 69 - 52, 16, 52, this.diFurnace.power, TileEntityForceField.maxPower);
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 62, this.guiTop + 69 - 52, 16, 52, mouseX, mouseY, new String[]{ this.diFurnace.health + " / " + this.diFurnace.maxHealth + "HP"} );
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.diFurnace.hasCustomInventoryName() ? this.diFurnace.getInventoryName() : I18n.format(this.diFurnace.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

    @Override
	protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);
		
    	if(this.guiLeft + 142 <= x && this.guiLeft + 142 + 18 > x && this.guiTop + 34 < y && this.guiTop + 34 + 18 >= y) {

			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.diFurnace.xCoord, this.diFurnace.yCoord, this.diFurnace.zCoord, 0, 0));
    	}
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIForceField.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		int i = (int)this.diFurnace.getPowerScaled(52);
		drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 69 - i, 176, 52 - i, 16, i);
		
		int j = this.diFurnace.getHealthScaled(52);
		drawTexturedModalRect(this.guiLeft + 62, this.guiTop + 69 - j, 192, 52 - j, 16, j);
		
		if(this.diFurnace.isOn) {
			drawTexturedModalRect(this.guiLeft + 142, this.guiTop + 34, 176, 52, 18, 18);
		}
	}
}
