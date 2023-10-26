package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerNukeGadget;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.bomb.TileEntityNukeGadget;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUINukeGadget extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/weapon/gadgetSchematic.png");
	private TileEntityNukeGadget testNuke;
	
	public GUINukeGadget(InventoryPlayer invPlayer, TileEntityNukeGadget tedf) {
		super(new ContainerNukeGadget(invPlayer, tedf));
		this.testNuke = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		String[] descText = I18nUtil.resolveKeyArray("desc.gui.nukeGadget.desc");
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 16, 16, 16, this.guiLeft - 8, this.guiTop + 16 + 16, descText);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.testNuke.hasCustomInventoryName() ? this.testNuke.getInventoryName() : I18n.format(this.testNuke.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUINukeGadget.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		if(this.testNuke.exp1())
		{
			drawTexturedModalRect(this.guiLeft + 82, this.guiTop + 19, 176, 0, 24, 24);
		}
		
		if(this.testNuke.exp2())
		{
			drawTexturedModalRect(this.guiLeft + 106, this.guiTop + 19, 200, 0, 24, 24);
		}
		
		if(this.testNuke.exp3())
		{
			drawTexturedModalRect(this.guiLeft + 82, this.guiTop + 43, 176, 24, 24, 24);
		}
		
		if(this.testNuke.exp4())
		{
			drawTexturedModalRect(this.guiLeft + 106, this.guiTop + 43, 200, 24, 24, 24);
		}

		if(this.testNuke.isReady())
		{
			drawTexturedModalRect(this.guiLeft + 134, this.guiTop + 35, 176, 48, 16, 16);
		}
		
		drawInfoPanel(this.guiLeft - 16, this.guiTop + 16, 16, 16, 2);
	}
}
