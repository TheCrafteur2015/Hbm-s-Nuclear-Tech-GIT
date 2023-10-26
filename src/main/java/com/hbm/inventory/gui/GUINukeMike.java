package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerNukeMike;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.bomb.TileEntityNukeMike;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUINukeMike extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/weapon/ivyMikeSchematic.png");
	private TileEntityNukeMike testNuke;
	
	public GUINukeMike(InventoryPlayer invPlayer, TileEntityNukeMike tedf) {
		super(new ContainerNukeMike(invPlayer, tedf));
		this.testNuke = tedf;
		
		this.xSize = 176;
		this.ySize = 217;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		String[] descText = I18nUtil.resolveKeyArray("desc.gui.nukeMike.desc");
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 16, 16, 16, this.guiLeft - 8, this.guiTop + 16 + 16, descText);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.testNuke.hasCustomInventoryName() ? this.testNuke.getInventoryName() : I18n.format(this.testNuke.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 4, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUINukeMike.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		if(this.testNuke.isReady() && !this.testNuke.isFilled())
		{
			drawTexturedModalRect(this.guiLeft + 5, this.guiTop + 35, 177, 1, 16, 16);
		}

		if(this.testNuke.isReady() && this.testNuke.isFilled())
		{
			drawTexturedModalRect(this.guiLeft + 5, this.guiTop + 35, 177, 19, 16, 16);
		}
		
		if(this.testNuke.getStackInSlot(5) != null && this.testNuke.getStackInSlot(5).getItem() == ModItems.mike_core)
			drawTexturedModalRect(this.guiLeft + 75, this.guiTop + 25, 176, 49, 80, 36);
		
		if(this.testNuke.getStackInSlot(6) != null && this.testNuke.getStackInSlot(6).getItem() == ModItems.mike_deut)
			drawTexturedModalRect(this.guiLeft + 79, this.guiTop + 30, 180, 88, 58, 26);
		
		if(this.testNuke.getStackInSlot(7) != null && this.testNuke.getStackInSlot(7).getItem() == ModItems.mike_cooling_unit)
			drawTexturedModalRect(this.guiLeft + 140, this.guiTop + 30, 240, 88, 12, 26);
		
		for(int i = 0; i < 4; i++) {
			if(this.testNuke.getStackInSlot(i) != null && this.testNuke.getStackInSlot(i).getItem() == ModItems.explosive_lenses)
				switch(i) {
				case 0: drawTexturedModalRect(this.guiLeft + 24, this.guiTop + 20 , 209, 1, 23, 23); break;
				case 2: drawTexturedModalRect(this.guiLeft + 47, this.guiTop + 20 , 232, 1, 23, 23); break;
				case 1: drawTexturedModalRect(this.guiLeft + 24, this.guiTop + 43 , 209, 24, 23, 23); break;
				case 3: drawTexturedModalRect(this.guiLeft + 47, this.guiTop + 43 , 232, 24, 23, 23); break;
				}
		}
		
		drawInfoPanel(this.guiLeft - 16, this.guiTop + 16, 16, 16, 2);
	}
}