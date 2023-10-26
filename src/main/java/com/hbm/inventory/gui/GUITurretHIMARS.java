package com.hbm.inventory.gui;

import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.turret.TileEntityTurretBaseNT;
import com.hbm.tileentity.turret.TileEntityTurretHIMARS;
import com.hbm.util.I18nUtil;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUITurretHIMARS extends GUITurretBase {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/weapon/gui_turret_himars.png");

	public GUITurretHIMARS(InventoryPlayer invPlayer, TileEntityTurretBaseNT tedf) {
		super(invPlayer, tedf);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		TileEntityTurretHIMARS arty = (TileEntityTurretHIMARS) this.turret;
		String mode = arty.mode == TileEntityTurretHIMARS.MODE_AUTO ? "artillery" : "manual";
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 151, this.guiTop + 16, 18, 18, mouseX, mouseY, I18nUtil.resolveKeyArray("turret.arty." + mode));
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		if(this.guiLeft + 151 <= x && this.guiLeft + 151 + 18 > x && this.guiTop + 16 < y && this.guiTop + 16 + 18 >= y) {

			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.turret.xCoord, this.turret.yCoord, this.turret.zCoord, 0, 5));
			return;
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int mX, int mY) {
		super.drawGuiContainerBackgroundLayer(p_146976_1_, mX, mY);
		
		short mode = ((TileEntityTurretHIMARS)this.turret).mode;
		if(mode == TileEntityTurretHIMARS.MODE_MANUAL) drawTexturedModalRect(this.guiLeft + 151, this.guiTop + 16, 210, 0, 18, 18);
	}

	@Override
	protected ResourceLocation getTexture() {
		return GUITurretHIMARS.texture;
	}
}
