package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineMissileAssembly;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.util.MissileMultipart;
import com.hbm.render.util.MissilePart;
import com.hbm.render.util.MissilePronter;
import com.hbm.tileentity.machine.TileEntityMachineMissileAssembly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineMissileAssembly extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_missile_assembly.png");
	private TileEntityMachineMissileAssembly assembler;
	
	public GUIMachineMissileAssembly(InventoryPlayer invPlayer, TileEntityMachineMissileAssembly tedf) {
		super(new ContainerMachineMissileAssembly(invPlayer, tedf));
		this.assembler = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);
		
    	if(this.guiLeft + 115 <= x && this.guiLeft + 115 + 18 > x && this.guiTop + 35 < y && this.guiTop + 35 + 18 >= y) {
    		
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.assembler.xCoord, this.assembler.yCoord, this.assembler.zCoord, 0, 0));
    	}
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
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMachineMissileAssembly.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		if(this.assembler.fuselageState() == 1)
			drawTexturedModalRect(this.guiLeft + 49, this.guiTop + 23, 194, 0, 6, 8);
		if(this.assembler.warheadState() == 1)
			drawTexturedModalRect(this.guiLeft + 31, this.guiTop + 23, 194, 0, 6, 8);
		if(this.assembler.chipState() == 1)
			drawTexturedModalRect(this.guiLeft + 13, this.guiTop + 23, 194, 0, 6, 8);
		if(this.assembler.stabilityState() == 1)
			drawTexturedModalRect(this.guiLeft + 67, this.guiTop + 23, 194, 0, 6, 8);
		if(this.assembler.stabilityState() == 0)
			drawTexturedModalRect(this.guiLeft + 67, this.guiTop + 23, 200, 0, 6, 8);
		if(this.assembler.thrusterState() == 1)
			drawTexturedModalRect(this.guiLeft + 85, this.guiTop + 23, 194, 0, 6, 8);
		
		if(this.assembler.canBuild())
			drawTexturedModalRect(this.guiLeft + 115, this.guiTop + 35, 176, 0, 18, 18);
		
		/// DRAW MISSILE START
		GL11.glPushMatrix();

		MissileMultipart missile = new MissileMultipart();
		
		if(this.assembler.getStackInSlot(1) != null)
			missile.warhead = MissilePart.getPart(this.assembler.getStackInSlot(1).getItem());
		
		if(this.assembler.getStackInSlot(2) != null)
			missile.fuselage = MissilePart.getPart(this.assembler.getStackInSlot(2).getItem());
		
		if(this.assembler.getStackInSlot(3) != null)
			missile.fins = MissilePart.getPart(this.assembler.getStackInSlot(3).getItem());
		
		if(this.assembler.getStackInSlot(4) != null)
			missile.thruster = MissilePart.getPart(this.assembler.getStackInSlot(4).getItem());
		
		GL11.glTranslatef(this.guiLeft + 88, this.guiTop + 98, 100);
		GL11.glRotatef(System.currentTimeMillis() / 10 % 360, 0, -1, 0);
		
		double size = 8 * 18;
		double scale = size / Math.max(missile.getHeight(), 6);
		
		GL11.glTranslated(missile.getHeight() / 2 * scale, 0, 0);
		GL11.glScaled(scale, scale, scale);
		
		GL11.glRotatef(90, 1, 0, 0);
		GL11.glRotatef(-90, 0, 0, 1);
		GL11.glScalef(-1, -1, -1);
		
		MissilePronter.prontMissile(missile, Minecraft.getMinecraft().getTextureManager());
		
		GL11.glPopMatrix();
		/// DRAW MISSILE END
	}
}
