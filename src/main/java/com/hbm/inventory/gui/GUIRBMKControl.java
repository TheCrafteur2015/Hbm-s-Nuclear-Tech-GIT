package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerRBMKControl;
import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKControlManual;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIRBMKControl extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_rbmk_control.png");
	private TileEntityRBMKControlManual rod;

	public GUIRBMKControl(InventoryPlayer invPlayer, TileEntityRBMKControlManual tedf) {
		super(new ContainerRBMKControl(invPlayer, tedf));
		this.rod = tedf;
		
		this.xSize = 176;
		this.ySize = 186;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 71, this.guiTop + 29, 16, 56, mouseX, mouseY, new String[]{ (int)(this.rod.level * 100) + "%" } );
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
		
		for(int k = 0; k < 5; k++) {

			//manual rod control
			if(this.guiLeft + 118 <= x && this.guiLeft + 118 + 30 > x && this.guiTop + 26 + k * 11 < y && this.guiTop + 26 + 10 + k * 11 >= y) {
	
				this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
				NBTTagCompound data = new NBTTagCompound();
				data.setDouble("level", 1.0D - (k * 0.25D));
				PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, this.rod.xCoord, this.rod.yCoord, this.rod.zCoord));
			}

			//color groups
			if(this.guiLeft + 28 <= x && this.guiLeft + 28 + 12 > x && this.guiTop + 26 + k * 11 < y && this.guiTop + 26 + 10 + k * 11 >= y) {
	
				this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
				NBTTagCompound data = new NBTTagCompound();
				data.setInteger("color", k);
				PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, this.rod.xCoord, this.rod.yCoord, this.rod.zCoord));
			}
		}
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
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIRBMKControl.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int height = (int)(56 * (1D - this.rod.level));
		
		if(height > 0)
			drawTexturedModalRect(this.guiLeft + 75, this.guiTop + 29, 176, 56 - height, 8, height);
		
		if(this.rod.color != null) {
			int color = this.rod.color.ordinal();

			drawTexturedModalRect(this.guiLeft + 28, this.guiTop + 26 + color * 11, 184, color * 10, 12, 10);
		}
	}
}
