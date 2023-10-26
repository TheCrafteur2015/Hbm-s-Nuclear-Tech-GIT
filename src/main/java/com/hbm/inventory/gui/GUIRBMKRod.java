package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerRBMKRod;
import com.hbm.items.machine.ItemRBMKRod;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKRod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIRBMKRod extends GuiContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_rbmk_element.png");
	private TileEntityRBMKRod rod;

	public GUIRBMKRod(InventoryPlayer invPlayer, TileEntityRBMKRod tedf) {
		super(new ContainerRBMKRod(invPlayer, tedf));
		this.rod = tedf;
		
		this.xSize = 176;
		this.ySize = 186;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.rod.hasCustomInventoryName() ? this.rod.getInventoryName() : I18n.format(this.rod.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIRBMKRod.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		if(this.rod.slots[0] != null && this.rod.slots[0].getItem() instanceof ItemRBMKRod) {
			drawTexturedModalRect(this.guiLeft + 34, this.guiTop + 21, 176, 0, 18, 67);
			
			double depletion = 1D - ItemRBMKRod.getEnrichment(this.rod.slots[0]);
			int d = (int)(depletion * 67);
			drawTexturedModalRect(this.guiLeft + 34, this.guiTop + 21, 194, 0, 18, d);
			
			double xenon = ItemRBMKRod.getPoisonLevel(this.rod.slots[0]);
			int x = (int)(xenon * 58);
			drawTexturedModalRect(this.guiLeft + 126, this.guiTop + 82 - x, 212, 58 - x, 14, x);
		}
	}
}
