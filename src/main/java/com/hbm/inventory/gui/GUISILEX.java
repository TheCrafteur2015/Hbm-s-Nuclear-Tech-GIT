package com.hbm.inventory.gui;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerSILEX;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.SILEXRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFELCrystal.EnumWavelengths;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntitySILEX;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GUISILEX extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_silex.png");
	private TileEntitySILEX silex;
	int offset = 0;

	public GUISILEX(InventoryPlayer invPlayer, TileEntitySILEX laser) {
		super(new ContainerSILEX(invPlayer, laser));
		this.silex = laser;

		this.xSize = 176;
		this.ySize = 222;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		this.silex.tank.renderTankInfo(this, mouseX, mouseY, this.guiLeft + 8, this.guiTop + 42, 52, 7);
		
		if(this.silex.current != null) {
			this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 27, this.guiTop + 72, 16, 52, mouseX, mouseY, new String[] { this.silex.currentFill + "/" + TileEntitySILEX.maxFill + "mB", this.silex.current.toStack().getDisplayName() });
		}
		
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 10, this.guiTop + 92, 10, 10, mouseX, mouseY, new String[] { "Void contents" });
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		if(this.guiLeft + 10 <= x && this.guiLeft + 10 + 12 > x && this.guiTop + 92 < y && this.guiTop + 92 + 12 >= y) {

			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.silex.xCoord, this.silex.yCoord, this.silex.zCoord, 0, 0));
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.silex.hasCustomInventoryName() ? this.silex.getInventoryName() : I18n.format(this.silex.getInventoryName());

		this.fontRendererObj.drawString(name, (this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2) - 54, 8, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		
		if(this.silex.mode != EnumWavelengths.NULL) {
			this.fontRendererObj.drawString(this.silex.mode.textColor + I18nUtil.resolveKey(this.silex.mode.name), 100 + (32 - this.fontRendererObj.getStringWidth(I18nUtil.resolveKey(this.silex.mode.name)) / 2), 16, 0);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUISILEX.texture);
		
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		if(this.silex.mode != EnumWavelengths.NULL) {
			float freq = 0.1F * (float)Math.pow(2, this.silex.mode.ordinal());
			int color = (this.silex.mode != EnumWavelengths.VISIBLE) ? this.silex.mode.guiColor : Color.HSBtoRGB(this.silex.getWorldObj().getTotalWorldTime() / 50.0F, 0.5F, 1F) & 16777215;
			drawWave(81, 46, 16, 84, 0.5F, freq, color, 3F, 1F);
		}
		
		if(this.silex.tank.getFill() > 0) {
			
			if(this.silex.tank.getTankType() == Fluids.ACID || TileEntitySILEX.fluidConversion.containsKey(this.silex.tank.getTankType()) || SILEXRecipes.getOutput(new ItemStack(ModItems.fluid_icon, 1, this.silex.tank.getTankType().getID())) != null) {
				drawTexturedModalRect(this.guiLeft + 7, this.guiTop + 41, 176, 118, 54, 9);
			} else {
				drawTexturedModalRect(this.guiLeft + 7, this.guiTop + 41, 176, 109, 54, 9);
			}
		}

		int p = this.silex.getProgressScaled(69);
		drawTexturedModalRect(this.guiLeft + 45, this.guiTop + 82, 176, 0, p, 43);

		int f = this.silex.getFillScaled(52);
		drawTexturedModalRect(this.guiLeft + 26, this.guiTop + 124 - f, 176, 109 - f, 16, f);

		int i = this.silex.getFluidScaled(52);
		drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 42, 176, this.silex.tank.getTankType() == Fluids.ACID ? 43 : 50, i, 7);
	}
	
	private void drawWave(int x, int y, int height, int width, float resolution, float freq, int color, float thickness, float mult) {
		float samples = ((float)width) / resolution;
		float scale = ((float)height)/2F;
		float offset = (float)((float)this.silex.getWorldObj().getTotalWorldTime() % (4*Math.PI/freq));//((width/3)*Math.PI/3));//(2.05F*width*freq));
		for(int i = 1; i < samples; i++) {
			double currentX = offset + x + i*resolution;
			double nextX = offset + x + (i+1)*resolution;
			double currentY = y + scale*Math.sin(freq*currentX);
			double nextY = y + scale*Math.sin(freq*nextX);
			drawLine(thickness, color, currentX-offset, currentY, nextX-offset, nextY);
			
			
		}
	}
	
	private void drawLine(float width, int color, double x1, double y1, double x2, double y2) {
		
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glLineWidth(width);
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawing(1);
		tessellator.setColorOpaque_I(color);
		
		tessellator.addVertex(this.guiLeft + x1, this.guiTop + y1, this.zLevel);
		tessellator.addVertex(this.guiLeft + x2, this.guiTop + y2, this.zLevel);
		tessellator.draw();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();
	}
}