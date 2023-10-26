package com.hbm.inventory.gui;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.SlotPattern;
import com.hbm.inventory.container.ContainerMachineCustom;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityCustomMachine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIMachineCustom extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_custom.png");
	private TileEntityCustomMachine custom;

	public GUIMachineCustom(InventoryPlayer invPlayer, TileEntityCustomMachine tedf) {
		super(new ContainerMachineCustom(invPlayer, tedf));
		this.custom = tedf;
		
		this.xSize = 176;
		this.ySize = 256;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);
		
		drawElectricityInfo(this, x, y, this.guiLeft + 150, this.guiTop + 18, 16, 52, this.custom.power, this.custom.config.maxPower);

		if(this.mc.thePlayer.inventory.getItemStack() == null) {
			for (Object element : this.inventorySlots.inventorySlots) {
				Slot slot = (Slot) element;
				int tileIndex = slot.getSlotIndex();
	
				if(isMouseOverSlot(slot, x, y) && slot instanceof SlotPattern && this.custom.matcher.modes[tileIndex - 10] != null) {
					
					String label = EnumChatFormatting.YELLOW + "";
					
					switch(this.custom.matcher.modes[tileIndex - 10]) {
					case "exact": label += "Item and meta match"; break;
					case "wildcard": label += "Item matches"; break;
					default: label += "Ore dict key matches: " + this.custom.matcher.modes[tileIndex - 10]; break;
					}
					
					func_146283_a(Arrays.asList(new String[] { EnumChatFormatting.RED + "Right click to change", label }), x, y - 30);
				}
			}
		}
		
		for(int i = 0; i < this.custom.inputTanks.length; i++) {
			this.custom.inputTanks[i].renderTankInfo(this, x, y, this.guiLeft + 8 + 18 * i, this.guiTop + 18, 16, 34);
		}
		
		for(int i = 0; i < this.custom.outputTanks.length; i++) {
			this.custom.outputTanks[i].renderTankInfo(this, x, y, this.guiLeft + 78 + 18 * i, this.guiTop + 18, 16, 34);
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.custom.getInventoryName();
		this.fontRendererObj.drawString(name, 68 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMachineCustom.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int p = this.custom.progress * 90 / this.custom.maxProgress;
		drawTexturedModalRect(this.guiLeft + 78, this.guiTop + 119, 192, 0, Math.min(p, 44), 16);
		if(p > 44) {
			p-= 44;
			drawTexturedModalRect(this.guiLeft + 78 + 44, this.guiTop + 119, 192, 16, p, 16);
		}
		
		int e = (int) (this.custom.power * 52 / this.custom.config.maxPower);
		drawTexturedModalRect(this.guiLeft + 150, this.guiTop + 70 - e, 176, 52 - e, 16, e);
		
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 3; j++) {
				int index = i * 3 + j;
				if(this.custom.config.itemInCount <= index) {
					drawTexturedModalRect(this.guiLeft + 7 + j * 18, this.guiTop + 71 + i * 18, 192 + j * 18, 86 + i * 18, 18, 18);
					drawTexturedModalRect(this.guiLeft + 7 + j * 18, this.guiTop + 107 + i * 18, 192 + j * 18, 86 + i * 18, 18, 18);
				}
				if(this.custom.config.itemOutCount <= index) {
					drawTexturedModalRect(this.guiLeft + 77 + j * 18, this.guiTop + 71 + i * 18, 192 + j * 18, 86 + i * 18, 18, 18);
				}
			}
		}
		
		for(int i = 0; i < 3; i++) {
			if(this.custom.config.fluidInCount <= i) {
				drawTexturedModalRect(this.guiLeft + 7 + i * 18, this.guiTop + 17, 192 + i * 18, 32, 18, 54);
			}
			if(this.custom.config.fluidOutCount <= i) {
				drawTexturedModalRect(this.guiLeft + 77 + i * 18, this.guiTop + 17, 192 + i * 18, 32, 18, 36);
			}
		}
		
		for(int i = 0; i < this.custom.inputTanks.length; i++) {
			this.custom.inputTanks[i].renderTank(this.guiLeft + 8 + 18 * i, this.guiTop + 52, this.zLevel, 16, 34);
		}
		
		for(int i = 0; i < this.custom.outputTanks.length; i++) {
			this.custom.outputTanks[i].renderTank(this.guiLeft + 78 + 18 * i, this.guiTop + 52, this.zLevel, 16, 34);
		}
	}

}
