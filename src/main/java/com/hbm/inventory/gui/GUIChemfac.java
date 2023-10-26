package com.hbm.inventory.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerChemfac;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineChemfac;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public class GUIChemfac extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_chemfac.png");
	private TileEntityMachineChemfac chemfac;

	public GUIChemfac(InventoryPlayer invPlayer, TileEntityMachineChemfac tedf) {
		super(new ContainerChemfac(invPlayer, tedf));
		this.chemfac = tedf;
		
		this.xSize = 256;
		this.ySize = 256;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 234, this.guiTop + 25, 16, 52, this.chemfac.power, this.chemfac.getMaxPower());
		
		for(int i = 0; i < 8; i ++) {

			int offX = this.guiLeft + 110 * (i % 2);
			int offY = this.guiTop + 38 * (i / 2);
			this.chemfac.tanks[i * 4 + 0].renderTankInfo(this, mouseX, mouseY, offX + 40, offY + 45 - 32, 5, 34);
			this.chemfac.tanks[i * 4 + 1].renderTankInfo(this, mouseX, mouseY, offX + 45, offY + 45 - 32, 5, 34);
			this.chemfac.tanks[i * 4 + 2].renderTankInfo(this, mouseX, mouseY, offX + 102, offY + 45 - 32, 5, 34);
			this.chemfac.tanks[i * 4 + 3].renderTankInfo(this, mouseX, mouseY, offX + 107, offY + 45 - 32, 5, 34);
		}

		this.chemfac.water.renderTankInfo(this, mouseX, mouseY, this.guiLeft + 233, this.guiTop + 108, 9, 54);
		this.chemfac.steam.renderTankInfo(this, mouseX, mouseY, this.guiLeft + 242, this.guiTop + 108, 9, 54);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) { }

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int mX, int mY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIChemfac.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, 167);
		drawTexturedModalRect(this.guiLeft + 26, this.guiTop + 167, 26, 167, 230, 44);
		drawTexturedModalRect(this.guiLeft + 26, this.guiTop + 211, 26, 211, 176, 45);
		
		int p = (int) (this.chemfac.power * 52 / this.chemfac.getMaxPower());
		drawTexturedModalRect(this.guiLeft + 234, this.guiTop + 77 - p, 0, 219 - p, 16, p);
		
		if(this.chemfac.power > 0)
			drawTexturedModalRect(this.guiLeft + 238, this.guiTop + 11, 0, 219, 9, 12);
		
		for(int i = 0; i < 8; i ++) {

			int offX = this.guiLeft + 110 * (i % 2);
			int offY = this.guiTop + 38 * (i / 2);
			
			int prog = this.chemfac.progress[i];
			int j = prog * 17 / Math.max(this.chemfac.maxProgress[i], 1);
			Minecraft.getMinecraft().getTextureManager().bindTexture(GUIChemfac.texture);
			drawTexturedModalRect(offX + 51, offY + 16, 202, 247, j, 11);
			
			this.chemfac.tanks[i * 4 + 0].renderTank(offX + 41, offY + 46, this.zLevel, 3, 32);
			this.chemfac.tanks[i * 4 + 1].renderTank(offX + 46, offY + 46, this.zLevel, 3, 32);
			this.chemfac.tanks[i * 4 + 2].renderTank(offX + 103, offY + 46, this.zLevel, 3, 32);
			this.chemfac.tanks[i * 4 + 3].renderTank(offX + 108, offY + 46, this.zLevel, 3, 32);
		}

		this.chemfac.water.renderTank(this.guiLeft + 234, this.guiTop + 161, this.zLevel, 7, 52);
		this.chemfac.steam.renderTank(this.guiLeft + 243, this.guiTop + 161, this.zLevel, 7, 52);
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LMENU))
		for(int i = 0; i < this.inventorySlots.inventorySlots.size(); i++) {
			Slot s = this.inventorySlots.getSlot(i);

			this.fontRendererObj.drawStringWithShadow(i + "", this.guiLeft + s.xDisplayPosition + 2, this.guiTop + s.yDisplayPosition, 0xffffff);
			this.fontRendererObj.drawStringWithShadow(s.getSlotIndex() + "", this.guiLeft + s.xDisplayPosition + 2, this.guiTop + s.yDisplayPosition + 8, 0xff8080);
		}
	}
}
