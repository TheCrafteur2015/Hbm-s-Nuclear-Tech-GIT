package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineDiesel;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineDiesel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineDiesel extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/GUIDiesel.png");
	private TileEntityMachineDiesel diesel;

	public GUIMachineDiesel(InventoryPlayer invPlayer, TileEntityMachineDiesel tedf) {
		super(new ContainerMachineDiesel(invPlayer, tedf));
		this.diesel = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.diesel.tank.renderTankInfo(this, mouseX, mouseY, this.guiLeft + 80, this.guiTop + 69 - 52, 16, 52);
		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 152, this.guiTop + 69 - 52, 16, 52, this.diesel.power, this.diesel.powerCap);
		
		String[] text = new String[] { "Fuel consumption rate:",
				"  1 mB/t",
				"  20 mB/s",
				"(Consumption rate is constant)" };
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 36, 16, 16, this.guiLeft - 8, this.guiTop + 36 + 16, text);
		
		if(!this.diesel.hasAcceptableFuel()) {
			
			String[] text2 = new String[] { "Error: The currently set fuel type",
					"is not supported by this engine!" };
			this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 36 + 32, 16, 16, this.guiLeft - 8, this.guiTop + 36 + 16 + 32, text2);
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.diesel.hasCustomInventoryName() ? this.diesel.getInventoryName() : I18n.format(this.diesel.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMachineDiesel.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		if(this.diesel.power > 0) {
			int i = (int)this.diesel.getPowerScaled(52);
			drawTexturedModalRect(this.guiLeft + 152, this.guiTop + 69 - i, 176, 52 - i, 16, i);
		}
		
		if(this.diesel.tank.getFill() > 0 && this.diesel.hasAcceptableFuel())
		{
			drawTexturedModalRect(this.guiLeft + 43 + 18 * 4, this.guiTop + 34, 208, 0, 18, 18);
		}

		drawInfoPanel(this.guiLeft - 16, this.guiTop + 36, 16, 16, 2);
		
		if(!this.diesel.hasAcceptableFuel())
			drawInfoPanel(this.guiLeft - 16, this.guiTop + 36 + 32, 16, 16, 6);
		
		this.diesel.tank.renderTank(this.guiLeft + 80, this.guiTop + 69, this.zLevel, 16, 52);
	}
}
