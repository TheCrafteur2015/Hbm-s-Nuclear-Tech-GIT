package com.hbm.inventory.gui;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerCraneGrabber;
import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.network.TileEntityCraneGrabber;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUICraneGrabber extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/storage/gui_crane_grabber.png");
	private TileEntityCraneGrabber grabber;

	public GUICraneGrabber(InventoryPlayer invPlayer, TileEntityCraneGrabber tedf) {
		super(new ContainerCraneGrabber(invPlayer, tedf));
		this.grabber = tedf;
		
		this.xSize = 176;
		this.ySize = 185;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);

		if(this.mc.thePlayer.inventory.getItemStack() == null) {
			for(int i = 0; i < 9; ++i) {
				Slot slot = (Slot) this.inventorySlots.inventorySlots.get(i);
	
				if(isMouseOverSlot(slot, x, y) && this.grabber.matcher.modes[i] != null) {
					
					String label = EnumChatFormatting.YELLOW + "";
					
					switch(this.grabber.matcher.modes[i]) {
					case "exact": label += "Item and meta match"; break;
					case "wildcard": label += "Item matches"; break;
					default: label += "Ore dict key matches: " + this.grabber.matcher.modes[i]; break;
					}
					
					func_146283_a(Arrays.asList(new String[] { EnumChatFormatting.RED + "Right click to change", label }), x, y - 30);
				}
			}
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		if(this.guiLeft + 97 <= x && this.guiLeft + 97 + 14 > x && this.guiTop + 30 < y && this.guiTop + 30 + 26 >= y) {

			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("whitelist", true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, this.grabber.xCoord, this.grabber.yCoord, this.grabber.zCoord));
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.grabber.hasCustomInventoryName() ? this.grabber.getInventoryName() : I18n.format(this.grabber.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUICraneGrabber.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		if(this.grabber.isWhitelist) {
			drawTexturedModalRect(this.guiLeft + 108, this.guiTop + 33, 176, 0, 3, 6);
		} else {
			drawTexturedModalRect(this.guiLeft + 108, this.guiTop + 47, 176, 0, 3, 6);
		}
	}
}
