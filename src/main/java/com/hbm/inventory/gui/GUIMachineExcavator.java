package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineExcavator;
import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityMachineExcavator;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIMachineExcavator extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_mining_drill.png");
	private TileEntityMachineExcavator drill;

	public GUIMachineExcavator(InventoryPlayer inventory, TileEntityMachineExcavator tile) {
		super(new ContainerMachineExcavator(inventory, tile));
		
		this.drill = tile;
		
		this.xSize = 242;
		this.ySize = 204;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);

		this.drawCustomInfoStat(x, y, this.guiLeft + 6, this.guiTop + 42, 20, 40, x, y, I18nUtil.resolveKey("excavator.drill"));
		this.drawCustomInfoStat(x, y, this.guiLeft + 30, this.guiTop + 42, 20, 40, x, y, I18nUtil.resolveKey("excavator.crusher"));
		this.drawCustomInfoStat(x, y, this.guiLeft + 54, this.guiTop + 42, 20, 40, x, y, I18nUtil.resolveKey("excavator.walling"));
		this.drawCustomInfoStat(x, y, this.guiLeft + 78, this.guiTop + 42, 20, 40, x, y, I18nUtil.resolveKey("excavator.veinminer"));
		this.drawCustomInfoStat(x, y, this.guiLeft + 102, this.guiTop + 42, 20, 40, x, y, I18nUtil.resolveKey("excavator.silktouch"));
		
		drawElectricityInfo(this, x, y, this.guiLeft + 220, this.guiTop + 18, 16, 52, this.drill.getPower(), TileEntityMachineExcavator.maxPower);
		this.drill.tank.renderTankInfo(this, x, y, this.guiLeft + 202, this.guiTop + 18, 16, 52);
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		String toggle = null;
		
		if(this.guiLeft + 6 <= x && this.guiLeft + 6 + 20 > x && this.guiTop + 42 < y && this.guiTop + 42 + 40 >= y) toggle = "drill";
		if(this.guiLeft + 30 <= x && this.guiLeft + 30 + 20 > x && this.guiTop + 42 < y && this.guiTop + 42 + 40 >= y) toggle = "crusher";
		if(this.guiLeft + 54 <= x && this.guiLeft + 54 + 20 > x && this.guiTop + 42 < y && this.guiTop + 42 + 40 >= y) toggle = "walling";
		if(this.guiLeft + 78 <= x && this.guiLeft + 78 + 20 > x && this.guiTop + 42 < y && this.guiTop + 42 + 40 >= y) toggle = "veinminer";
		if(this.guiLeft + 102 <= x && this.guiLeft + 102 + 20 > x && this.guiTop + 42 < y && this.guiTop + 42 + 40 >= y) toggle = "silktouch";

		if(toggle != null) {
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("hbm:block.leverLarge"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean(toggle, true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, this.drill.xCoord, this.drill.yCoord, this.drill.zCoord));
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8 + 33, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMachineExcavator.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 242, 96);
		drawTexturedModalRect(this.guiLeft + 33, this.guiTop + 104, 33, 104, 176, 100);
		
		int i = (int) (this.drill.getPower() * 52 / this.drill.getMaxPower());
		drawTexturedModalRect(this.guiLeft + 220, this.guiTop + 70 - i, 229, 156 - i, 16, i);
		
		if(this.drill.getPower() > this.drill.getPowerConsumption()) {
			drawTexturedModalRect(this.guiLeft + 224, this.guiTop + 4, 239, 156, 9, 12);
		}
		
		if(this.drill.getInstalledDrill() == null && System.currentTimeMillis() % 1000 < 500) {
			drawTexturedModalRect(this.guiLeft + 171, this.guiTop + 74, 209, 154, 18, 18);
		}
		
		if(this.drill.enableDrill) {
			drawTexturedModalRect(this.guiLeft + 6, this.guiTop + 42, 209, 114, 20, 40);
			if(this.drill.getInstalledDrill() != null && this.drill.getPower() >= this.drill.getPowerConsumption()) drawTexturedModalRect(this.guiLeft + 11, this.guiTop + 5, 209, 104, 10, 10);
			else if(System.currentTimeMillis() % 1000 < 500) drawTexturedModalRect(this.guiLeft + 11, this.guiTop + 5, 219, 104, 10, 10);
		}
		
		if(this.drill.enableCrusher) {
			drawTexturedModalRect(this.guiLeft + 30, this.guiTop + 42, 209, 114, 20, 40);
			drawTexturedModalRect(this.guiLeft + 35, this.guiTop + 5, 209, 104, 10, 10);
		}
		
		if(this.drill.enableWalling) {
			drawTexturedModalRect(this.guiLeft + 54, this.guiTop + 42, 209, 114, 20, 40);
			drawTexturedModalRect(this.guiLeft + 59, this.guiTop + 5, 209, 104, 10, 10);
		}
		
		if(this.drill.enableVeinMiner) {
			drawTexturedModalRect(this.guiLeft + 78, this.guiTop + 42, 209, 114, 20, 40);
			if(this.drill.canVeinMine()) drawTexturedModalRect(this.guiLeft + 83, this.guiTop + 5, 209, 104, 10, 10);
			else if(System.currentTimeMillis() % 1000 < 500) drawTexturedModalRect(this.guiLeft + 83, this.guiTop + 5, 219, 104, 10, 10);
			
		}
		
		if(this.drill.enableSilkTouch) {
			drawTexturedModalRect(this.guiLeft + 102, this.guiTop + 42, 209, 114, 20, 40);
			if(this.drill.canSilkTouch()) drawTexturedModalRect(this.guiLeft + 107, this.guiTop + 5, 209, 104, 10, 10);
			else if(System.currentTimeMillis() % 1000 < 500) drawTexturedModalRect(this.guiLeft + 107, this.guiTop + 5, 219, 104, 10, 10);
		}
		
		this.drill.tank.renderTank(this.guiLeft + 202, this.guiTop + 70, this.zLevel, 16, 52);
	}
}
