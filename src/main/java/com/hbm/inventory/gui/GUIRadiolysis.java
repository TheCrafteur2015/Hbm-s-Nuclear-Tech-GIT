package com.hbm.inventory.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerRadiolysis;
import com.hbm.items.machine.ItemRTGPellet;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineRadiolysis;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIRadiolysis extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_radiolysis.png");
	private TileEntityMachineRadiolysis radiolysis;

	public GUIRadiolysis(InventoryPlayer invPlayer, TileEntityMachineRadiolysis tedf) {
		super(new ContainerRadiolysis(invPlayer, tedf));
		this.radiolysis = tedf;
		
		this.xSize = 230;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		this.radiolysis.tanks[0].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 61, this.guiTop + 17, 8, 52);
		this.radiolysis.tanks[1].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 87, this.guiTop + 17, 12, 16);
		this.radiolysis.tanks[2].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 87, this.guiTop + 53, 12, 16);
		
		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 8, this.guiTop + 17, 16, 34, this.radiolysis.power, TileEntityMachineRadiolysis.maxPower);
		
		String[] descText = I18nUtil.resolveKeyArray("desc.gui.radiolysis.desc");
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 16, 16, 16, this.guiLeft - 8, this.guiTop + 16 + 16, descText);
		
		String[] heatText = I18nUtil.resolveKeyArray("desc.gui.rtg.heat", this.radiolysis.heat);
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 16 + 18, 16, 16, this.guiLeft - 8, this.guiTop + 16 + 18 + 16, heatText);
		
		List<ItemRTGPellet> pellets = ItemRTGPellet.pelletList;
		String[] pelletText = new String[pellets.size() + 1];
		pelletText[0] = I18nUtil.resolveKey("desc.gui.rtg.pellets");
		
		for(int i = 0; i < pellets.size(); i++) {
			ItemRTGPellet pellet = pellets.get(i);
			pelletText[i + 1] = I18nUtil.resolveKey("desc.gui.rtg.pelletPower", I18nUtil.resolveKey(pellet.getUnlocalizedName() + ".name"), pellet.getHeat() * 10);
		}
		
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 16 + 36, 16, 16, this.guiLeft - 8, this.guiTop + 16 + 36 + 16, pelletText);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		
		String name = this.radiolysis.hasCustomInventoryName() ? this.radiolysis.getInventoryName() : I18n.format(this.radiolysis.getInventoryName());
		
		this.fontRendererObj.drawString(name, 88 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIRadiolysis.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int i = (int)(this.radiolysis.getPower() * 34 / this.radiolysis.getMaxPower());
		drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 51 - i, 240, 34 - i, 16, i);
		
		this.radiolysis.tanks[0].renderTank(this.guiLeft + 61, this.guiTop + 69, this.zLevel, 8, 52);
		
		for(byte j = 0; j < 2; j++) {
			this.radiolysis.tanks[j + 1].renderTank(this.guiLeft + 87, this.guiTop + 33 + j * 36, this.zLevel, 12, 16);
		}
		
		drawInfoPanel(this.guiLeft - 16, this.guiTop + 16, 16, 16, 10);
		drawInfoPanel(this.guiLeft - 16, this.guiTop + 16 + 18, 16, 16, 2);
		drawInfoPanel(this.guiLeft - 16, this.guiTop + 16 + 36, 16, 16, 3);
	}
}
