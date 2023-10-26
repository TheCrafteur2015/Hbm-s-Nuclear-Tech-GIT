package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineGasFlare;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.oil.TileEntityMachineGasFlare;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIMachineGasFlare extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/generators/gui_flare_stack.png");
	private TileEntityMachineGasFlare flare;
	
	public GUIMachineGasFlare(InventoryPlayer invPlayer, TileEntityMachineGasFlare tedf) {
		super(new ContainerMachineGasFlare(invPlayer, tedf));
		this.flare = tedf;
		
		this.xSize = 176;
		this.ySize = 203;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 79, this.guiTop + 16, 35, 10, mouseX, mouseY, I18nUtil.resolveKeyArray("flare.valve"));
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 79, this.guiTop + 50, 35, 14, mouseX, mouseY, I18nUtil.resolveKeyArray("flare.ignition"));
		
		this.flare.tank.renderTankInfo(this, mouseX, mouseY, this.guiLeft + 35, this.guiTop + 69 - 52, 16, 52);
		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 143, this.guiTop + 69 - 52, 16, 52, this.flare.power, TileEntityMachineGasFlare.maxPower);
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		if(this.guiLeft + 89 <= x && this.guiLeft + 89 + 16 > x && this.guiTop + 16 < y && this.guiTop + 16 + 10 >= y) {
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("valve", true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, this.flare.xCoord, this.flare.yCoord, this.flare.zCoord));
			
		} else if(this.guiLeft + 89 <= x && this.guiLeft + 89 + 16 > x && this.guiTop + 50 < y && this.guiTop + 50 + 14 >= y) {
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("dial", true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, this.flare.xCoord, this.flare.yCoord, this.flare.zCoord));
		}
		
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		//String name = this.flare.hasCustomInventoryName() ? this.flare.getInventoryName() : I18n.format(this.flare.getInventoryName());
		
		//this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMachineGasFlare.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		int j = (int)this.flare.getPowerScaled(52);
		drawTexturedModalRect(this.guiLeft + 143, this.guiTop + 69 - j, 176, 94 - j, 16, j);

		if(this.flare.isOn)  drawTexturedModalRect(this.guiLeft + 79, this.guiTop + 15, 176, 0, 35, 10);
		if(this.flare.doesBurn)  drawTexturedModalRect(this.guiLeft + 79, this.guiTop + 49, 176, 10, 35, 14);
		
		if(this.flare.isOn && this.flare.doesBurn && this.flare.tank.getFill() > 0 && this.flare.tank.getTankType().hasTrait(FT_Flammable.class))
			drawTexturedModalRect(this.guiLeft + 88, this.guiTop + 29, 176, 24, 18, 18);
		
		this.flare.tank.renderTank(this.guiLeft + 35, this.guiTop + 69, this.zLevel, 16, 52);
	}
}
