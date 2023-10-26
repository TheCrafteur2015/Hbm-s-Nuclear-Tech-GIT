package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerAMSBase;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityAMSBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIAMSBase extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_ams_base.png");
	private TileEntityAMSBase base;
	
	public GUIAMSBase(InventoryPlayer invPlayer, TileEntityAMSBase tedf) {
		super(new ContainerAMSBase(invPlayer, tedf));
		this.base = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.base.tanks[0].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 26, this.guiTop + 70 - 52, 16, 52);
		this.base.tanks[1].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 134, this.guiTop + 70 - 52, 16, 52);
		this.base.tanks[2].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 26, this.guiTop + 124 - 52, 16, 52);
		this.base.tanks[3].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 134, this.guiTop + 124 - 52, 16, 52);
		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 116, this.guiTop + 124 - 104, 7, 104, this.base.power, TileEntityAMSBase.maxPower);
		drawCustomInfo(this, mouseX, mouseY, this.guiLeft + 44, this.guiTop + 124 - 106, 7, 106, new String[] { "Restriction Field:", this.base.field + "%" });
		drawCustomInfo(this, mouseX, mouseY, this.guiLeft + 53, this.guiTop + 124 - 106, 7, 106, new String[] { "Efficiency:", this.base.efficiency + "%" });
		drawCustomInfo(this, mouseX, mouseY, this.guiLeft + 125, this.guiTop + 124 - 106, 7, 106, new String[] { "Heat:", this.base.heat + "/" + TileEntityAMSBase.maxHeat });

		if(!this.base.hasResonators()) {
			String[] text = new String[] { "Error: Three satellite ID-chips linked",
				"to xenium resonators are required",
				"for this machine to work!" };
			this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 36, 16, 16, this.guiLeft - 8, this.guiTop + 36 + 16, text);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.base.hasCustomInventoryName() ? this.base.getInventoryName() : I18n.format(this.base.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIAMSBase.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int i = (int)this.base.getPowerScaled(106);
		drawTexturedModalRect(this.guiLeft + 116, this.guiTop + 124 - i, 206, 106 - i, 7, i);
		
		int j = this.base.getFieldScaled(106);
		drawTexturedModalRect(this.guiLeft + 44, this.guiTop + 124 - j, 192, 106 - j, 7, j);
		
		int k = this.base.getEfficiencyScaled(106);
		drawTexturedModalRect(this.guiLeft + 53, this.guiTop + 124 - k, 199, 106 - k, 7, k);
		
		int l = this.base.getHeatScaled(106);
		drawTexturedModalRect(this.guiLeft + 125, this.guiTop + 124 - l, 213, 106 - l, 7, l);
		
		int m = this.base.mode;
		if(m > 0)
		drawTexturedModalRect(this.guiLeft + 80, this.guiTop + 108, 176, 32 + 16 * m, 16, 16);
		
		int n = this.base.warning;
		if(n > 0)
		drawTexturedModalRect(this.guiLeft + 80, this.guiTop + 18, 176, 32 + 16 * n, 16, 16);
		
		if(this.base.color > -1) {
			GL11.glColor3ub((byte)((this.base.color & 0xFF0000) >> 16), (byte)((this.base.color & 0x00FF00) >> 8), (byte)((this.base.color & 0x0000FF) >> 0));
			drawTexturedModalRect(this.guiLeft + 61, this.guiTop + 44, 176, 160, 54, 54);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			drawTexturedModalRect(this.guiLeft + 61, this.guiTop + 44, 176, 106, 54, 54);
		}

		if(!this.base.hasResonators())
			drawInfoPanel(this.guiLeft - 16, this.guiTop + 36, 16, 16, 6);

		this.base.tanks[0].renderTank(this.guiLeft + 26, this.guiTop + 70, this.zLevel, 16, 52);
		
		this.base.tanks[1].renderTank(this.guiLeft + 134, this.guiTop + 70, this.zLevel, 16, 52);
		
		this.base.tanks[2].renderTank(this.guiLeft + 26, this.guiTop + 124, this.zLevel, 16, 52);
		
		this.base.tanks[3].renderTank(this.guiLeft + 134, this.guiTop + 124, this.zLevel, 16, 52);
	}
}
