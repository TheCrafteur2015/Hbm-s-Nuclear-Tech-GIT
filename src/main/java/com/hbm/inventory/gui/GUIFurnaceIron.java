package com.hbm.inventory.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerFurnaceIron;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityFurnaceIron;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public class GUIFurnaceIron extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_furnace_iron.png");
	private TileEntityFurnaceIron furnace;

	public GUIFurnaceIron(InventoryPlayer invPlayer, TileEntityFurnaceIron tedf) {
		super(new ContainerFurnaceIron(invPlayer, tedf));
		this.furnace = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);

		if(this.mc.thePlayer.inventory.getItemStack() == null) {
			
			for(int i = 1; i < 3; ++i) {
				Slot slot = (Slot) this.inventorySlots.inventorySlots.get(i);
				
				if(isMouseOverSlot(slot, x, y) && !slot.getHasStack()) {
					
					List<String> bonuses = this.furnace.burnModule.getTimeDesc();
					
					if(!bonuses.isEmpty()) {
						func_146283_a(bonuses, x, y);
					}
				}
			}
		}

		this.drawCustomInfoStat(x, y, this.guiLeft + 52, this.guiTop + 35, 71, 7, x, y, new String[] { (this.furnace.progress * 100 / Math.max(this.furnace.processingTime, 1)) + "%" });
		this.drawCustomInfoStat(x, y, this.guiLeft + 52, this.guiTop + 44, 71, 7, x, y, new String[] { (this.furnace.burnTime / 20) + "s" });
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.furnace.hasCustomInventoryName() ? this.furnace.getInventoryName() : I18n.format(this.furnace.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIFurnaceIron.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int i = this.furnace.progress * 70 / Math.max(this.furnace.processingTime, 1);
		drawTexturedModalRect(this.guiLeft + 53, this.guiTop + 36, 176, 18, i, 5);
		
		int j = this.furnace.burnTime * 70 / Math.max(this.furnace.maxBurnTime, 1);
		drawTexturedModalRect(this.guiLeft + 53, this.guiTop + 45, 176, 23, j, 5);
		
		if(this.furnace.canSmelt())
			drawTexturedModalRect(this.guiLeft + 70, this.guiTop + 16, 176, 0, 18, 18);
	}
}
