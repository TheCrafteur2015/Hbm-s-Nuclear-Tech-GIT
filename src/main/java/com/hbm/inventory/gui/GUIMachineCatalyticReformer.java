package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineCatalyticReformer;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.oil.TileEntityMachineCatalyticReformer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineCatalyticReformer extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_catalytic_reformer.png");
	private TileEntityMachineCatalyticReformer refinery;

	public GUIMachineCatalyticReformer(InventoryPlayer invPlayer, TileEntityMachineCatalyticReformer tedf) {
		super(new ContainerMachineCatalyticReformer(invPlayer, tedf));
		this.refinery = tedf;
		
		this.xSize = 176;
		this.ySize = 238;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.refinery.tanks[0].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 35, this.guiTop + 70 - 52, 16, 52);
		this.refinery.tanks[1].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 107, this.guiTop + 70 - 52, 16, 52);
		this.refinery.tanks[2].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 125, this.guiTop + 70 - 52, 16, 52);
		this.refinery.tanks[3].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 143, this.guiTop + 70 - 52, 16, 52);
		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 17, this.guiTop + 70 - 52, 16, 52, this.refinery.power, TileEntityMachineCatalyticReformer.maxPower);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.refinery.hasCustomInventoryName() ? this.refinery.getInventoryName() : I18n.format(this.refinery.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 5, 0xffffff);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMachineCatalyticReformer.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int j = (int) (this.refinery.power * 54 / TileEntityMachineCatalyticReformer.maxPower);
		drawTexturedModalRect(this.guiLeft + 17, this.guiTop + 70 - j, 176, 52 - j, 16, j);
		
		this.refinery.tanks[0].renderTank(this.guiLeft + 35, this.guiTop + 70, this.zLevel, 16, 52);
		this.refinery.tanks[1].renderTank(this.guiLeft + 107, this.guiTop + 70, this.zLevel, 16, 52);
		this.refinery.tanks[2].renderTank(this.guiLeft + 125, this.guiTop + 70, this.zLevel, 16, 52);
		this.refinery.tanks[3].renderTank(this.guiLeft + 143, this.guiTop + 70, this.zLevel, 16, 52);
	}
}
