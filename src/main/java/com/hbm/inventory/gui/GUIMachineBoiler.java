package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineBoiler;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineBoiler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineBoiler extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_boiler.png");
	private TileEntityMachineBoiler diFurnace;
	
	public GUIMachineBoiler(InventoryPlayer invPlayer, TileEntityMachineBoiler tedf) {
		super(new ContainerMachineBoiler(invPlayer, tedf));
		this.diFurnace = tedf;

		this.xSize = 176;
		this.ySize = 168;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		TileEntityMachineBoiler dud = this.diFurnace;
		
		if(this.diFurnace.isInvalid() && this.diFurnace.getWorldObj().getTileEntity(this.diFurnace.xCoord, this.diFurnace.yCoord, this.diFurnace.zCoord) instanceof TileEntityMachineBoiler)
			dud = (TileEntityMachineBoiler) this.diFurnace.getWorldObj().getTileEntity(this.diFurnace.xCoord, this.diFurnace.yCoord, this.diFurnace.zCoord);

		dud.tanks[0].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 62, this.guiTop + 69 - 52, 16, 52);
		dud.tanks[1].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 134, this.guiTop + 69 - 52, 16, 52);

		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 102, this.guiTop + 16, 8, 18, mouseX, mouseY, new String[] { String.valueOf((int)(dud.heat / 100D)) + "°C"});
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 97, this.guiTop + 34, 18, 18, mouseX, mouseY, new String[] { String.valueOf((int)(Math.ceil(dud.burnTime / 20D))) + "s"});
		
		String[] text = new String[] { "Heat produced:",
				"  0.5°C/t",
				"  or 10°C/s",
				"Heat consumed:",
				"  0.15°C/t",
				"  or 3.0°C/s (base)",
				"  0.25°C/t",
				"  or 5.0°C/s (once boiling point is reached)",
				"  0.4°C/t",
				"  or 8.0°C/s (for every subsequent multiple of boiling point)" };
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 36, 16, 16, this.guiLeft - 8, this.guiTop + 36 + 16, text);
		
		String[] text1 = new String[] { "Boiling rate:",
				"  Base rate * amount of full multiples",
				"  of boiling points reached" };
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 36 + 16, 16, 16, this.guiLeft - 8, this.guiTop + 36 + 16, text1);
		
		if(dud.tanks[1].getTankType().getName().equals(Fluids.NONE.getName())) {
			
			String[] text2 = new String[] { "Error: Liquid can not be boiled!" };
			this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 36 + 32, 16, 16, this.guiLeft - 8, this.guiTop + 36 + 16 + 32, text2);
		}
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
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMachineBoiler.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		//"It just works" -Todd Howard
		TileEntityMachineBoiler dud = this.diFurnace;
		
		if(this.diFurnace.isInvalid() && this.diFurnace.getWorldObj().getTileEntity(this.diFurnace.xCoord, this.diFurnace.yCoord, this.diFurnace.zCoord) instanceof TileEntityMachineBoiler)
			dud = (TileEntityMachineBoiler) this.diFurnace.getWorldObj().getTileEntity(this.diFurnace.xCoord, this.diFurnace.yCoord, this.diFurnace.zCoord);

		if(dud.burnTime > 0)
			drawTexturedModalRect(this.guiLeft + 97, this.guiTop + 34, 176, 0, 18, 18);

		int j = dud.getHeatScaled(17);
		drawTexturedModalRect(this.guiLeft + 103, this.guiTop + 33 - j, 194, 16 - j, 6, j);

		drawInfoPanel(this.guiLeft - 16, this.guiTop + 36, 16, 16, 2);
		drawInfoPanel(this.guiLeft - 16, this.guiTop + 36 + 16, 16, 16, 3);
		
		if(dud.tanks[1].getTankType().getName().equals(Fluids.NONE.getName())) {
			drawInfoPanel(this.guiLeft - 16, this.guiTop + 36 + 32, 16, 16, 6);
		}
		
		dud.tanks[0].renderTank(this.guiLeft + 62, this.guiTop + 69, this.zLevel, 16, 52);
		dud.tanks[1].renderTank(this.guiLeft + 134, this.guiTop + 69, this.zLevel, 16, 52);
	}
}
