package com.hbm.inventory.gui;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineWoodBurner;
import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityMachineWoodBurner;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIMachineWoodBurner extends GuiInfoContainer {

	private TileEntityMachineWoodBurner burner;
	private final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/generators/gui_wood_burner_alt.png");

	public GUIMachineWoodBurner(InventoryPlayer invPlayer, TileEntityMachineWoodBurner tedf) {
		super(new ContainerMachineWoodBurner(invPlayer, tedf));
		this.burner = tedf;

		this.xSize = 176;
		this.ySize = 186;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		this.drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 143, this.guiTop + 18, 16, 34, this.burner.power, TileEntityMachineWoodBurner.maxPower);

		if(this.mc.thePlayer.inventory.getItemStack() == null) {

			Slot slot = (Slot) this.inventorySlots.inventorySlots.get(0);
			if(this.isMouseOverSlot(slot, mouseX, mouseY) && !slot.getHasStack()) {
				List<String> bonuses = TileEntityMachineWoodBurner.burnModule.getDesc();
				if(!bonuses.isEmpty()) {
					this.func_146283_a(bonuses, mouseX, mouseY);
				}
			}
		}

		if(this.burner.liquidBurn) this.burner.tank.renderTankInfo(this, mouseX, mouseY, this.guiLeft + 80, this.guiTop + 18, 16, 52);

		if(!this.burner.liquidBurn && this.guiLeft + 16 <= mouseX && this.guiLeft + 16 + 8 > mouseX && this.guiTop + 17 < mouseY && this.guiTop + 17 + 54 >= mouseY) {
			this.func_146283_a(Arrays.asList(new String[] { (this.burner.burnTime / 20) + "s" }), mouseX, mouseY);
		}

		if(this.guiLeft + 53 <= mouseX && this.guiLeft + 53 + 16 > mouseX && this.guiTop + 17 < mouseY && this.guiTop + 17 + 15 >= mouseY) {
			this.func_146283_a(Arrays.asList(new String[] { this.burner.isOn ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF" }), mouseX, mouseY);
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		if(this.guiLeft + 53 <= x && this.guiLeft + 53 + 16 > x && this.guiTop + 17 < y && this.guiTop + 17 + 15 >= y) {
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("toggle", false);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, this.burner.xCoord, this.burner.yCoord, this.burner.zCoord));
		}

		if(this.guiLeft + 46 <= x && this.guiLeft + 46 + 30 > x && this.guiTop + 37 < y && this.guiTop + 37 + 14 >= y) {
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("switch", false);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, this.burner.xCoord, this.burner.yCoord, this.burner.zCoord));
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.burner.hasCustomInventoryName() ? this.burner.getInventoryName() : I18n.format(this.burner.getInventoryName());

		this.fontRendererObj.drawString(name, 70 - this.fontRendererObj.getStringWidth(name) / 2, 6, 0xffffff);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(this.texture);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		if(this.burner.liquidBurn) {
			this.drawTexturedModalRect(this.guiLeft + 16, this.guiTop + 17, 176, 52, 60, 54);
			this.drawTexturedModalRect(this.guiLeft + 79, this.guiTop + 17, 176, 106, 36, 54);
		}

		if(this.burner.isOn) {
			this.drawTexturedModalRect(this.guiLeft + 53, this.guiTop + 17, 196, 0, 16, 15);
		}

		int p = (int) (this.burner.power * 34 / TileEntityMachineWoodBurner.maxPower);
		this.drawTexturedModalRect(this.guiLeft + 143, this.guiTop + 52 - p, 176, 52 - p, 16, p);

		if(this.burner.maxBurnTime > 0 && !this.burner.liquidBurn) {
			int b = (int) (this.burner.burnTime * 52 / this.burner.maxBurnTime);
			this.drawTexturedModalRect(this.guiLeft + 17, this.guiTop + 70 - b, 192, 52 - b, 4, b);
		}

		if(this.burner.liquidBurn) this.burner.tank.renderTank(this.guiLeft + 80, this.guiTop + 70, this.zLevel, 16, 52);
	}
}
