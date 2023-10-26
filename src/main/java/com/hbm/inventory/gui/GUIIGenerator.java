package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerIGenerator;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineIGenerator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIIGenerator extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/generators/gui_igen.png");
	private TileEntityMachineIGenerator igen;

	public GUIIGenerator(InventoryPlayer invPlayer, TileEntityMachineIGenerator tedf) {
		super(new ContainerIGenerator(invPlayer, tedf));
		this.igen = tedf;

		this.xSize = 176;
		this.ySize = 237;
	}

	@Override
	public void drawScreen(int x, int y, float f) {
		super.drawScreen(x, y, f);

		drawElectricityInfo(this, x, y, this.guiLeft + 26, this.guiTop + 134, 142, 16, this.igen.power, TileEntityMachineIGenerator.maxPower);
		
		for(int i = 0; i < 4; i++) {
			int fire = this.igen.burn[i];
			
			this.drawCustomInfoStat(x, y, this.guiLeft + 68 + (i % 2) * 18, this.guiTop + 34 + (i / 2) * 36, 14, 14, x, y, new String[] {(fire / 20) + "s"});
		}
		
		this.drawCustomInfoStat(x, y, this.guiLeft + 113, this.guiTop + 4, 54, 18, x, y, new String[] {"Heat generated"});

		this.igen.tanks[0].renderTankInfo(this, x, y, this.guiLeft + 80, this.guiTop + 112, 72, 16);
		this.igen.tanks[1].renderTankInfo(this, x, y, this.guiLeft + 114, this.guiTop + 33, 16, 70);
		this.igen.tanks[2].renderTankInfo(this, x, y, this.guiLeft + 150, this.guiTop + 33, 18, 70);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.igen.hasCustomInventoryName() ? this.igen.getInventoryName() : I18n.format(this.igen.getInventoryName());
		
		GL11.glPushMatrix();
		double scale = 0.75D;
		GL11.glScaled(scale, scale, 1);
		this.fontRendererObj.drawString(name, 22, 18, 0x303030);
		GL11.glPopMatrix();
		
		String spin = this.igen.spin + "";
		this.fontRendererObj.drawString(spin, 139 - this.fontRendererObj.getStringWidth(spin) / 2, 10, 0x00ff00);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float iinterpolation, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIIGenerator.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		int water = this.igen.tanks[0].getFill() * 72 / this.igen.tanks[0].getMaxFill();
		drawTexturedModalRect(this.guiLeft + 80, this.guiTop + 112, 184, 14, water, 16);

		int power = (int) (this.igen.power * 142 / TileEntityMachineIGenerator.maxPower);
		drawTexturedModalRect(this.guiLeft + 26, this.guiTop + 134, 0, 237, power, 16);
		
		for(int i = 0; i < 4; i++) {
			int fire = this.igen.burn[i];
			
			if(fire > 0) {
				drawTexturedModalRect(this.guiLeft + 68 + (i % 2) * 18, this.guiTop + 34 + (i / 2) * 36, 184, 0, 14, 14);
			}
		}
		
		if(this.igen.hasRTG) {
			drawTexturedModalRect(this.guiLeft + 9, this.guiTop + 34, 176, 0, 4, 89);
			drawTexturedModalRect(this.guiLeft + 51, this.guiTop + 34, 180, 0, 4, 89);
		}

		this.igen.tanks[1].renderTank(this.guiLeft + 114, this.guiTop + 103, this.zLevel, 16, 70);
		this.igen.tanks[2].renderTank(this.guiLeft + 150, this.guiTop + 103, this.zLevel, 16, 70);
	}
}
