package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineAssembler;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineAssembler;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineAssembler extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_assembler.png");
	private TileEntityMachineAssembler assembler;
	
	public GUIMachineAssembler(InventoryPlayer invPlayer, TileEntityMachineAssembler tedf) {
		super(new ContainerMachineAssembler(invPlayer, tedf));
		this.assembler = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 116, this.guiTop + 70 - 52, 16, 52, this.assembler.power, this.assembler.getMaxPower());
		
		if(this.assembler.getStackInSlot(4) == null || this.assembler.getStackInSlot(4).getItem()!= ModItems.assembly_template) {

			String[] warnText = I18nUtil.resolveKeyArray("desc.gui.assembler.warning");
			this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 36, 16, 16, this.guiLeft - 8, this.guiTop + 36 + 16, warnText);
		}
		
		String[] templateText = I18nUtil.resolveKeyArray("desc.gui.template");
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 16, 16, 16, this.guiLeft - 8, this.guiTop + 16 + 16, templateText);
		
		String[] upgradeText = new String[3];
		upgradeText[0] = I18nUtil.resolveKey("desc.gui.upgrade");
		upgradeText[1] = I18nUtil.resolveKey("desc.gui.upgrade.speed");
		upgradeText[2] = I18nUtil.resolveKey("desc.gui.upgrade.power");
		
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 141, this.guiTop + 40, 8, 8, this.guiLeft + 225, this.guiTop + 40 + 16 + 8, upgradeText);
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.assembler.hasCustomInventoryName() ? this.assembler.getInventoryName() : I18n.format(this.assembler.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMachineAssembler.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int i = (int) (this.assembler.power * 52 / this.assembler.getMaxPower());
		drawTexturedModalRect(this.guiLeft + 116, this.guiTop + 70 - i, 176, 52 - i, 16, i);

		if(this.assembler.isProgressing) {
			int j = this.assembler.progress[0] * 83 / this.assembler.maxProgress[0];
			drawTexturedModalRect(this.guiLeft + 45, this.guiTop + 82, 2, 222, j, 32);
		}
		
		if(this.assembler.getStackInSlot(4) == null || this.assembler.getStackInSlot(4).getItem()!= ModItems.assembly_template) {
			drawInfoPanel(this.guiLeft - 16, this.guiTop + 36, 16, 16, 6);
		}
		
		drawInfoPanel(this.guiLeft - 16, this.guiTop + 16, 16, 16, 11);
		drawInfoPanel(this.guiLeft + 141, this.guiTop + 40, 8, 8, 8);
	}
}
