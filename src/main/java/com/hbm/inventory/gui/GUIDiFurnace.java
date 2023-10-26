package com.hbm.inventory.gui;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerDiFurnace;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityDiFurnace;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

public class GUIDiFurnace extends GuiContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/GUIDiFurnace.png");
	private TileEntityDiFurnace diFurnace;

	public GUIDiFurnace(InventoryPlayer invPlayer, TileEntityDiFurnace tedf) {
		super(new ContainerDiFurnace(invPlayer, tedf));
		this.diFurnace = tedf;

		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);

		if(this.mc.thePlayer.inventory.getItemStack() == null) {
			for(int i = 0; i < 3; i++) {
				Slot slot = (Slot) this.inventorySlots.inventorySlots.get(i);
				
				if(isMouseOverSlot(slot, x, y)) {
					
					String label = EnumChatFormatting.YELLOW + "Accepts items from: ";
					byte dir = i == 0 ? this.diFurnace.sideUpper : i == 1 ? this.diFurnace.sideLower : this.diFurnace.sideFuel;
					label += ForgeDirection.getOrientation(dir);
					
					func_146283_a(Arrays.asList(new String[] { label }), x, y - (slot.getHasStack() ? 15 : 0));
					
					return;
				}
			}
		}
	}
	
	protected boolean isMouseOverSlot(Slot slot, int x, int y) {
		return func_146978_c(slot.xDisplayPosition, slot.yDisplayPosition, 16, 16, x, y);
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
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIDiFurnace.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		if(this.diFurnace.isInvalid() && this.diFurnace.getWorldObj().getTileEntity(this.diFurnace.xCoord, this.diFurnace.yCoord, this.diFurnace.zCoord) instanceof TileEntityDiFurnace)
			this.diFurnace = (TileEntityDiFurnace) this.diFurnace.getWorldObj().getTileEntity(this.diFurnace.xCoord, this.diFurnace.yCoord, this.diFurnace.zCoord);

		if(this.diFurnace.hasPower()) {
			int i1 = this.diFurnace.getPowerRemainingScaled(52);
			drawTexturedModalRect(this.guiLeft + 44, this.guiTop + 70 - i1, 201, 53 - i1, 16, i1);
		}

		int j1 = this.diFurnace.getDiFurnaceProgressScaled(24);
		drawTexturedModalRect(this.guiLeft + 101, this.guiTop + 35, 176, 14, j1 + 1, 17);

		if(this.diFurnace.hasPower() && (this.diFurnace.canProcess() || j1 > 0)) {
			drawTexturedModalRect(this.guiLeft + 63, this.guiTop + 37, 176, 0, 14, 14);
		}
	}

}
