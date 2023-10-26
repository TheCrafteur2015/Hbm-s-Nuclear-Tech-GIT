package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerBombMulti;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.bomb.TileEntityBombMulti;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIBombMulti extends GuiContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/weapon/bombGeneric.png");
	private TileEntityBombMulti testNuke;
	
	public GUIBombMulti(InventoryPlayer invPlayer, TileEntityBombMulti tedf) {
		super(new ContainerBombMulti(invPlayer, tedf));
		this.testNuke = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
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
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIBombMulti.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		if(this.testNuke.return2type() == this.testNuke.return5type())
		switch(this.testNuke.return2type())
		{
		case 1:
			drawTexturedModalRect(this.guiLeft + 124, this.guiTop + 34, 176, 0 * 18, 18, 18); break;
		case 2:
			drawTexturedModalRect(this.guiLeft + 124, this.guiTop + 34, 176, 1 * 18, 18, 18); break;
		case 3:
			drawTexturedModalRect(this.guiLeft + 124, this.guiTop + 34, 176, 2 * 18, 18, 18); break;
		case 4:
			drawTexturedModalRect(this.guiLeft + 124, this.guiTop + 34, 176, 3 * 18, 18, 18); break;
		case 5:
			drawTexturedModalRect(this.guiLeft + 124, this.guiTop + 34, 176, 4 * 18, 18, 18); break;
		case 6:
			drawTexturedModalRect(this.guiLeft + 124, this.guiTop + 34, 176, 5 * 18, 18, 18); break;
		}
		
		if(this.testNuke.return2type() != this.testNuke.return5type())
		{
			drawTexturedModalRect(this.guiLeft + 124, this.guiTop + 34, 176, 7 * 18, 18, 18);
		}
	}
}
