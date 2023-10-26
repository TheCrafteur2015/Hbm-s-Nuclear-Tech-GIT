package com.hbm.inventory.gui;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerAutocrafter;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineAutocrafter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIAutocrafter extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_autocrafter.png");
	private TileEntityMachineAutocrafter diFurnace;

	public GUIAutocrafter(InventoryPlayer invPlayer, TileEntityMachineAutocrafter tedf) {
		super(new ContainerAutocrafter(invPlayer, tedf));
		this.diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 240;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);
		
		drawElectricityInfo(this, x, y, this.guiLeft + 17, this.guiTop + 45, 16, 52, this.diFurnace.getPower(), this.diFurnace.getMaxPower());

		if(this.mc.thePlayer.inventory.getItemStack() == null) {
			for(int i = 0; i < 9; ++i) {
				Slot slot = (Slot) this.inventorySlots.inventorySlots.get(i);
	
				if(isMouseOverSlot(slot, x, y) && this.diFurnace.modes[i] != null) {
					
					String label = EnumChatFormatting.YELLOW + "";
					
					switch(this.diFurnace.modes[i]) {
					case "exact": label += "Item and meta match"; break;
					case "wildcard": label += "Item matches"; break;
					default: label += "Ore dict key matches: " + this.diFurnace.modes[i]; break;
					}
					
					func_146283_a(Arrays.asList(new String[] { EnumChatFormatting.RED + "Right click to change", label }), x, y - 30);
				}
			}
			
			Slot slot = (Slot) this.inventorySlots.inventorySlots.get(9);
			
			if(isMouseOverSlot(slot, x, y) && this.diFurnace.slots[9] != null) {
				func_146283_a(Arrays.asList(new String[] { EnumChatFormatting.RED + "Right click to change", EnumChatFormatting.YELLOW + "" + (this.diFurnace.recipeIndex + 1) + " / " + this.diFurnace.recipeCount }), x, y - 30);
			}
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.diFurnace.hasCustomInventoryName() ? this.diFurnace.getInventoryName() : I18n.format(this.diFurnace.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIAutocrafter.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int i = (int)(this.diFurnace.getPower() * 52 / this.diFurnace.getMaxPower());
		drawTexturedModalRect(this.guiLeft + 17, this.guiTop + 97 - i, 176, 52 - i, 16, i);
		
	}

	/**
	 * I love the "private" key word so fucking much I'll spend the next 3 weeks ramming my cock into it
	 * @param slot
	 * @param x
	 * @param y
	 * @return
	 */
	@Override
	protected boolean isMouseOverSlot(Slot slot, int x, int y) {
		return func_146978_c(slot.xDisplayPosition, slot.yDisplayPosition, 16, 16, x, y);
	}
}
