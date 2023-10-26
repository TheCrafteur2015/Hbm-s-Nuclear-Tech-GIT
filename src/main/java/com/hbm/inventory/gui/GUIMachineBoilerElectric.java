package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineBoilerElectric;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineBoilerElectric;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineBoilerElectric extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_boiler_electric.png");
	private TileEntityMachineBoilerElectric diFurnace;
	
	public GUIMachineBoilerElectric(InventoryPlayer invPlayer, TileEntityMachineBoilerElectric tedf) {
		super(new ContainerMachineBoilerElectric(invPlayer, tedf));
		this.diFurnace = tedf;

		this.xSize = 176;
		this.ySize = 168;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.diFurnace.tanks[0].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 62, this.guiTop + 69 - 52, 16, 52);
		this.diFurnace.tanks[1].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 134, this.guiTop + 69 - 52, 16, 52);

		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 102, this.guiTop + 16, 8, 18, mouseX, mouseY, new String[] { String.valueOf((int)((double)this.diFurnace.heat / 100D)) + "°C"});
		
		String[] text = new String[] { "Heat produced:",
				"  1.5°C/t",
				"  or 30°C/s",
				"Heat consumed:",
				"  0.15°C/t",
				"  or 3.0°C/s (base)",
				"  0.25°C/t",
				"  or 5.0°C/t (once boiling point is reached)",
				"  0.4°C/t",
				"  or 8.0°C/t (for every subsequent multiple of boiling point)" };
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 36, 16, 16, this.guiLeft - 8, this.guiTop + 36 + 16, text);
		
		String[] text1 = new String[] { "Boiling rate:",
				"  Base rate * amount of full multiples",
				"  of boiling points reached" };
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 36 + 16, 16, 16, this.guiLeft - 8, this.guiTop + 36 + 16, text1);
		
		if(this.diFurnace.tanks[1].getTankType().name().equals(Fluids.NONE.name())) {
			
			String[] text2 = new String[] { "Error: Liquid can not be boiled!" };
			this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 36 + 32, 16, 16, this.guiLeft - 8, this.guiTop + 36 + 16 + 32, text2);
		}
		
		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 123, this.guiTop + 69 - 34, 7, 34, this.diFurnace.power, TileEntityMachineBoilerElectric.maxPower);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.diFurnace.hasCustomInventoryName() ? this.diFurnace.getInventoryName() : I18n.format(this.diFurnace.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMachineBoilerElectric.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		if(this.diFurnace.isInvalid() && this.diFurnace.getWorldObj().getTileEntity(this.diFurnace.xCoord, this.diFurnace.yCoord, this.diFurnace.zCoord) instanceof TileEntityMachineBoilerElectric)
			this.diFurnace = (TileEntityMachineBoilerElectric) this.diFurnace.getWorldObj().getTileEntity(this.diFurnace.xCoord, this.diFurnace.yCoord, this.diFurnace.zCoord);

		if(this.diFurnace.power > 0)
			drawTexturedModalRect(this.guiLeft + 97, this.guiTop + 34, 176, 0, 18, 18);

		int j = (int)this.diFurnace.getHeatScaled(17);
		drawTexturedModalRect(this.guiLeft + 103, this.guiTop + 33 - j, 194, 16 - j, 6, j);

		int i = (int)this.diFurnace.getPowerScaled(34);
		drawTexturedModalRect(this.guiLeft + 123, this.guiTop + 69 - i, 200, 34 - i, 7, i);

		drawInfoPanel(this.guiLeft - 16, this.guiTop + 36, 16, 16, 2);
		drawInfoPanel(this.guiLeft - 16, this.guiTop + 36 + 16, 16, 16, 3);
		
		if(this.diFurnace.tanks[1].getTankType().name().equals(Fluids.NONE.name())) {
			drawInfoPanel(this.guiLeft - 16, this.guiTop + 36 + 32, 16, 16, 6);
		}
		
		this.diFurnace.tanks[0].renderTank(this.guiLeft + 62, this.guiTop + 69, this.zLevel, 16, 52);
		this.diFurnace.tanks[1].renderTank(this.guiLeft + 134, this.guiTop + 69, this.zLevel, 16, 52);
	}
}
