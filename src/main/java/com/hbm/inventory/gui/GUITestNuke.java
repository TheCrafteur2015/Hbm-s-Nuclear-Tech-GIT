package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerTestNuke;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.bomb.TileEntityTestNuke;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUITestNuke extends GuiContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/weapon/gunBombSchematicSmall.png");
	private TileEntityTestNuke testNuke;
	
	public GUITestNuke(InventoryPlayer invPlayer, TileEntityTestNuke tedf) {
		super(new ContainerTestNuke(invPlayer, tedf));
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
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUITestNuke.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	
		switch(this.testNuke.getNukeTier())
		{
		case 0:
			drawTexturedModalRect(this.guiLeft + 142, this.guiTop + 36, 176, 0, 16, 16); break;
		case 1:
			drawTexturedModalRect(this.guiLeft + 142, this.guiTop + 36, 176, 16, 16, 16); break;
		case 2:
			drawTexturedModalRect(this.guiLeft + 142, this.guiTop + 36, 176, 32, 16, 16); break;
		case 3:
			drawTexturedModalRect(this.guiLeft + 142, this.guiTop + 36, 176, 48, 16, 16); break;
		case 4:
			drawTexturedModalRect(this.guiLeft + 142, this.guiTop + 36, 176, 64, 16, 16); break;
		case 5:
			drawTexturedModalRect(this.guiLeft + 142, this.guiTop + 36, 176, 80, 16, 16); break;
		case 6:
			drawTexturedModalRect(this.guiLeft + 142, this.guiTop + 36, 176, 96, 16, 16); break;
		case 7:
			drawTexturedModalRect(this.guiLeft + 142, this.guiTop + 36, 176, 112, 16, 16); break;
		case 8:
			drawTexturedModalRect(this.guiLeft + 142, this.guiTop + 36, 176, 128, 16, 16); break;
		case 9:
			drawTexturedModalRect(this.guiLeft + 142, this.guiTop + 36, 176, 144, 16, 16); break;
		case 999:
			drawTexturedModalRect(this.guiLeft + 142, this.guiTop + 36, 176, 160, 16, 16); break;
		default:
		}
	}

}
