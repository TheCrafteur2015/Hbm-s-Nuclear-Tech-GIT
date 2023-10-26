package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMixer;
import com.hbm.inventory.recipes.MixerRecipes;
import com.hbm.inventory.recipes.MixerRecipes.MixerRecipe;
import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityMachineMixer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIMixer extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_mixer.png");
	private TileEntityMachineMixer mixer;

	public GUIMixer(InventoryPlayer player, TileEntityMachineMixer mixer) {
		super(new ContainerMixer(player, mixer));
		this.mixer = mixer;
		
		this.xSize = 176;
		this.ySize = 204;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);
		
		drawElectricityInfo(this, x, y, this.guiLeft + 23, this.guiTop + 23, 16, 52, this.mixer.getPower(), this.mixer.getMaxPower());
		
		MixerRecipe[] recipes = MixerRecipes.getOutput(this.mixer.tanks[2].getTankType());
		
		if(recipes != null && recipes.length > 1) {
			List<String> label = new ArrayList<>();
			label.add(EnumChatFormatting.YELLOW + "Current recipe (" + (this.mixer.recipeIndex + 1) + "/" + recipes.length + "):");
			MixerRecipe recipe = recipes[this.mixer.recipeIndex % recipes.length];
			if(recipe.input1 != null) label.add("-" + recipe.input1.type.getLocalizedName());
			if(recipe.input2 != null) label.add("-" + recipe.input2.type.getLocalizedName());
			if(recipe.solidInput != null) label.add("-" + recipe.solidInput.extractForCyclingDisplay(20).getDisplayName());
			label.add(EnumChatFormatting.RED + "Click to change!");
			this.drawCustomInfoStat(x, y, this.guiLeft + 62, this.guiTop + 22, 12, 12, x, y, label);
		}

		this.mixer.tanks[0].renderTankInfo(this, x, y, this.guiLeft + 43, this.guiTop + 23, 7, 52);
		this.mixer.tanks[1].renderTankInfo(this, x, y, this.guiLeft + 52, this.guiTop + 23, 7, 52);
		this.mixer.tanks[2].renderTankInfo(this, x, y, this.guiLeft + 117, this.guiTop + 23, 16, 52);
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		if(this.guiLeft + 62 <= x && this.guiLeft + 62 + 12 > x && this.guiTop + 22 < y && this.guiTop + 22 + 12 >= y) {

			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("toggle", true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, this.mixer.xCoord, this.mixer.yCoord, this.mixer.zCoord));
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		
		String name = this.mixer.hasCustomInventoryName() ? this.mixer.getInventoryName() : I18n.format(this.mixer.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMixer.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int i = (int) (this.mixer.getPower() * 53 / this.mixer.getMaxPower());
		drawTexturedModalRect(this.guiLeft + 23, this.guiTop + 75 - i, 176, 52 - i, 16, i);
		
		if(this.mixer.processTime > 0 && this.mixer.progress > 0) {
			int j = this.mixer.progress * 53 / this.mixer.processTime;
			drawTexturedModalRect(this.guiLeft + 62, this.guiTop + 36, 192, 0, j, 44);
		}

		this.mixer.tanks[0].renderTank(this.guiLeft + 43, this.guiTop + 75, this.zLevel, 7, 52);
		this.mixer.tanks[1].renderTank(this.guiLeft + 52, this.guiTop + 75, this.zLevel, 7, 52);
		this.mixer.tanks[2].renderTank(this.guiLeft + 117, this.guiTop + 75, this.zLevel, 16, 52);
	}
}
