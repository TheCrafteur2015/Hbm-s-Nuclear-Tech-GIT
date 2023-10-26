package com.hbm.inventory.gui;

import java.util.Arrays;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerCraneRouter;
import com.hbm.lib.RefStrings;
import com.hbm.module.ModulePatternMatcher;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.network.TileEntityCraneRouter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUICraneRouter extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/storage/gui_crane_router.png");
	private TileEntityCraneRouter router;

	public GUICraneRouter(InventoryPlayer invPlayer, TileEntityCraneRouter tedf) {
		super(new ContainerCraneRouter(invPlayer, tedf));
		this.router = tedf;
		
		this.xSize = 256;
		this.ySize = 201;
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		for(int j = 0; j < 2; j++) {
			for(int k = 0; k < 3; k++) {
				
				if(this.guiLeft + 7 + j * 222 <= x && this.guiLeft + 7 + j * 222 + 18 > x && this.guiTop + 16 + k * 26 < y && this.guiTop + 16 + k * 26 + 18 >= y) {

					this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
					NBTTagCompound data = new NBTTagCompound();
					data.setInteger("toggle", j * 3 + k);
					PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, this.router.xCoord, this.router.yCoord, this.router.zCoord));
				}
			}
		}
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);

		for(int j = 0; j < 2; j++) {
			for(int k = 0; k < 3; k++) {
				
				if(this.guiLeft + 7 + j * 222 <= x && this.guiLeft + 7 + j * 222 + 18 > x && this.guiTop + 16 + k * 26 < y && this.guiTop + 16 + k * 26 + 18 >= y) {
					
					String[] text = new String[2];
					int index = j * 3 + k;
					
					switch(this.router.modes[index]) {
					case 0: text = new String[] { "OFF" }; break;
					case 1: text[0] = "WHITELIST"; text[1] = "Route if filter matches"; break;
					case 2: text[0] = "BLACKLIST"; text[1] = "Route if filter doesn't match"; break;
					case 3: text[0] = "WILDCARD"; text[1] = "Route if no other route is valid"; break;
					}
					
					func_146283_a(Arrays.asList(text), x, y);
				}
			}
		}

		if(this.mc.thePlayer.inventory.getItemStack() == null) {
			for(int i = 0; i < 30; ++i) {
				Slot slot = (Slot) this.inventorySlots.inventorySlots.get(i);
				ModulePatternMatcher matcher = this.router.patterns[i / 5];
				int index = i % 5;
				
				if(isMouseOverSlot(slot, x, y) && matcher.modes[index] != null) {
					
					String label = EnumChatFormatting.YELLOW + "";
					
					switch(matcher.modes[index]) {
					case "exact": label += "Item and meta match"; break;
					case "wildcard": label += "Item matches"; break;
					default: label += "Ore dict key matches: " + matcher.modes[index]; break;
					}
					
					func_146283_a(Arrays.asList(new String[] { EnumChatFormatting.RED + "Right click to change", label}), x, y - 30);
				}
			}
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.router.hasCustomInventoryName() ? this.router.getInventoryName() : I18n.format(this.router.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 5, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8 + 39, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUICraneRouter.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 256, 93);
		drawTexturedModalRect(this.guiLeft + 39, this.guiTop + 93, 39, 93, 176, 108);

		for(int j = 0; j < 2; j++) {
			for(int k = 0; k < 3; k++) {
				int index = j * 3 + k;
				int mode = this.router.modes[index];
				drawTexturedModalRect(this.guiLeft + 7 + j * 222, this.guiTop + 16 + k * 26, 238, 93 + mode * 18, 18, 18);
			}
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LMENU))
		for(int i = 0; i < this.inventorySlots.inventorySlots.size(); i++) {
			Slot s = this.inventorySlots.getSlot(i);

			this.fontRendererObj.drawStringWithShadow(i + "", this.guiLeft + s.xDisplayPosition + 2, this.guiTop + s.yDisplayPosition, 0xffffff);
			this.fontRendererObj.drawStringWithShadow(s.getSlotIndex() + "", this.guiLeft + s.xDisplayPosition + 2, this.guiTop + s.yDisplayPosition + 8, 0xff8080);
		}
	}
}
