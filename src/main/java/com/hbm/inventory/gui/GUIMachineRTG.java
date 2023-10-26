package com.hbm.inventory.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineRTG;
import com.hbm.items.machine.ItemRTGPellet;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineRTG;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineRTG extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_rtg.png");
	private TileEntityMachineRTG rtg;
	
	public GUIMachineRTG(InventoryPlayer invPlayer, TileEntityMachineRTG tedf) {
		super(new ContainerMachineRTG(invPlayer, tedf));
		this.rtg = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 152, this.guiTop + 69 - 52, 16, 52, this.rtg.power, this.rtg.powerMax);
		
		String[] heatText = I18nUtil.resolveKeyArray("desc.gui.rtg.heat", this.rtg.heat);
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 134, this.guiTop + 17, 16, 52, mouseX, mouseY, heatText);
		
		List<ItemRTGPellet> pellets = ItemRTGPellet.pelletList;
		String[] pelletText = new String[pellets.size() + 1];
		pelletText[0] = I18nUtil.resolveKey("desc.gui.rtg.pellets");
		
		for(int i = 0; i < pellets.size(); i++) {
			ItemRTGPellet pellet = pellets.get(i);
			pelletText[i + 1] = I18nUtil.resolveKey("desc.gui.rtg.pelletPower", I18nUtil.resolveKey(pellet.getUnlocalizedName() + ".name"), pellet.getHeat() * 5);
		}
		
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 36, 16, 16, this.guiLeft - 8, this.guiTop + 36 + 16, pelletText);
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.rtg.hasCustomInventoryName() ? this.rtg.getInventoryName() : I18n.format(this.rtg.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMachineRTG.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		if(this.rtg.hasHeat())
		{
			int i = this.rtg.getHeatScaled(52);
			drawTexturedModalRect(this.guiLeft + 134, this.guiTop + 69 - i, 176, 52 - i, 16, i);
		}
		if(this.rtg.hasPower())
		{
			int i = (int)this.rtg.getPowerScaled(52);
			drawTexturedModalRect(this.guiLeft + 152, this.guiTop + 69 - i, 192, 52 - i, 16, i);
		}
		
		drawInfoPanel(this.guiLeft - 16, this.guiTop + 36, 16, 16, 2);
	}
}
