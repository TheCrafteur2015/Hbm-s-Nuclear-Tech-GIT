package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineTurbofan;
import com.hbm.lib.RefStrings;
import com.hbm.render.util.GaugeUtil;
import com.hbm.render.util.GaugeUtil.Gauge;
import com.hbm.tileentity.machine.TileEntityMachineTurbofan;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineTurbofan extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/generators/gui_turbofan.png");
	private TileEntityMachineTurbofan turbofan;

	public GUIMachineTurbofan(InventoryPlayer invPlayer, TileEntityMachineTurbofan tedf) {
		super(new ContainerMachineTurbofan(invPlayer, tedf));
		this.turbofan = tedf;
		
		this.xSize = 176;
		this.ySize = 203;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.turbofan.tank.renderTankInfo(this, mouseX, mouseY, this.guiLeft + 35, this.guiTop + 17, 34, 52);
		if(this.turbofan.showBlood) this.turbofan.blood.renderTankInfo(this, mouseX, mouseY, this.guiLeft + 98, this.guiTop + 17, 16, 16);
		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 143, this.guiTop + 17, 16, 52, this.turbofan.power, TileEntityMachineTurbofan.maxPower);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.turbofan.hasCustomInventoryName() ? this.turbofan.getInventoryName() : I18n.format(this.turbofan.getInventoryName());
		
		this.fontRendererObj.drawString(name, 43 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMachineTurbofan.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int i = (int)this.turbofan.getPowerScaled(52);
		drawTexturedModalRect(this.guiLeft + 152 - 9, this.guiTop + 69 - i, 176 + 16, 52 - i, 16, i);
		
		if(this.turbofan.afterburner > 0) {
			int a = Math.min(this.turbofan.afterburner, 6);
			drawTexturedModalRect(this.guiLeft + 98, this.guiTop + 44, 176, (a - 1) * 16, 16, 16);
		}

		if(this.turbofan.showBlood) GaugeUtil.renderGauge(Gauge.ROUND_SMALL, this.guiLeft + 97, this.guiTop + 16, this.zLevel, (double) this.turbofan.blood.getFill() / (double) this.turbofan.blood.getMaxFill());
		this.turbofan.tank.renderTank(this.guiLeft + 35, this.guiTop + 69, this.zLevel, 34, 52);
	}
}
