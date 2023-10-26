package com.hbm.inventory.gui;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import com.hbm.config.WeaponConfig;
import com.hbm.inventory.container.ContainerMachineRadar;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityMachineRadar;
import com.hbm.util.I18nUtil;

import api.hbm.entity.IRadarDetectable.RadarTargetType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineRadar extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_radar.png");
	private TileEntityMachineRadar diFurnace;

	public GUIMachineRadar(InventoryPlayer invPlayer, TileEntityMachineRadar tedf) {
		super(new ContainerMachineRadar(invPlayer, tedf));
		this.diFurnace = tedf;
		GUIMachineRadar.texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_radar.png");
		
		this.xSize = 216;
		this.ySize = 234;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 8, this.guiTop + 221, 200, 7, this.diFurnace.power, TileEntityMachineRadar.maxPower);

		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 10, this.guiTop + 98, 8, 8, mouseX, mouseY, I18nUtil.resolveKeyArray("radar.detectMissiles") );
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 10, this.guiTop + 108, 8, 8, mouseX, mouseY, I18nUtil.resolveKeyArray("radar.detectPlayers"));
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 10, this.guiTop + 118, 8, 8, mouseX, mouseY, I18nUtil.resolveKeyArray("radar.smartMode"));
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 10, this.guiTop + 128, 8, 8, mouseX, mouseY, I18nUtil.resolveKeyArray("radar.redMode"));

		if(!this.diFurnace.nearbyMissiles.isEmpty()) {
			for(int[] m : this.diFurnace.nearbyMissiles) {
				int x = this.guiLeft + (int)((m[0] - this.diFurnace.xCoord) / ((double)WeaponConfig.radarRange * 2 + 1) * (200D - 8D)) + 108;
				int z = this.guiTop + (int)((m[1] - this.diFurnace.zCoord) / ((double)WeaponConfig.radarRange * 2 + 1) * (200D - 8D)) + 117;
				
				if(mouseX + 4 > x && mouseX - 4 < x && 
						mouseY + 4 > z && mouseY - 4 < z) {

					
					String[] text = new String[] { RadarTargetType.values()[m[2]].name, m[0] + " / " + m[1], "Alt.: " + m[3] };
					
					func_146283_a(Arrays.asList(text), x, z);
					
					return;
				}
			}
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		if(this.guiLeft -10 <= x && this.guiLeft + -10 + 8 > x && this.guiTop + 98 < y && this.guiTop + 98 + 8 >= y) {
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.diFurnace.xCoord, this.diFurnace.yCoord, this.diFurnace.zCoord, 0, 0));
		}

		if(this.guiLeft -10 <= x && this.guiLeft + -10 + 8 > x && this.guiTop + 108 < y && this.guiTop + 108 + 8 >= y) {
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.diFurnace.xCoord, this.diFurnace.yCoord, this.diFurnace.zCoord, 0, 1));
		}

		if(this.guiLeft -10 <= x && this.guiLeft + -10 + 8 > x && this.guiTop + 118 < y && this.guiTop + 118 + 8 >= y) {
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.diFurnace.xCoord, this.diFurnace.yCoord, this.diFurnace.zCoord, 0, 2));
		}

		if(this.guiLeft -10 <= x && this.guiLeft + -10 + 8 > x && this.guiTop + 128 < y && this.guiTop + 128 + 8 >= y) {
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.diFurnace.xCoord, this.diFurnace.yCoord, this.diFurnace.zCoord, 0, 3));
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format("container.radar");
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMachineRadar.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		drawTexturedModalRect(this.guiLeft - 14, this.guiTop + 94, 216, 198, 14, 46);
		
		if(this.diFurnace.scanMissiles || (this.diFurnace.jammed && this.diFurnace.getWorldObj().rand.nextBoolean()))
			drawTexturedModalRect(this.guiLeft - 10, this.guiTop + 98, 230, 202, 8, 8);
		
		if(this.diFurnace.scanPlayers || (this.diFurnace.jammed && this.diFurnace.getWorldObj().rand.nextBoolean()))
			drawTexturedModalRect(this.guiLeft - 10, this.guiTop + 108, 230, 212, 8, 8);
		
		if(this.diFurnace.smartMode || (this.diFurnace.jammed && this.diFurnace.getWorldObj().rand.nextBoolean()))
			drawTexturedModalRect(this.guiLeft - 10, this.guiTop + 118, 230, 222, 8, 8);
		
		if(this.diFurnace.redMode || (this.diFurnace.jammed && this.diFurnace.getWorldObj().rand.nextBoolean()))
			drawTexturedModalRect(this.guiLeft - 10, this.guiTop + 128, 230, 232, 8, 8);
		
		if(this.diFurnace.power > 0) {
			int i = (int)this.diFurnace.getPowerScaled(200);
			drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 221, 0, 234, i, 16);
		}
		
		if(this.diFurnace.jammed) {
			
			for(int i = 0; i < 5; i++) {
				for(int j = 0; j < 5; j++) {
					drawTexturedModalRect(this.guiLeft + 8 + i * 40, this.guiTop + 17 + j * 40, 216, 118 + this.diFurnace.getWorldObj().rand.nextInt(41), 40, 40);
				}
			}
			
			return;
		}
		
		if(!this.diFurnace.nearbyMissiles.isEmpty()) {
			for(int[] m : this.diFurnace.nearbyMissiles) {
				int x = (int)((m[0] - this.diFurnace.xCoord) / ((double)WeaponConfig.radarRange * 2 + 1) * (200D - 8D)) - 4;
				int z = (int)((m[1] - this.diFurnace.zCoord) / ((double)WeaponConfig.radarRange * 2 + 1) * (200D - 8D)) - 4;
				int t = m[2];

				drawTexturedModalRect(this.guiLeft + 108 + x, this.guiTop + 117 + z, 216, 8 * t, 8, 8);
			}
		}
	}
}
