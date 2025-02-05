package com.hbm.inventory.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.interfaces.Untested;
import com.hbm.inventory.container.ContainerMachineDiFurnaceRTG;
import com.hbm.items.machine.ItemRTGPellet;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityDiFurnaceRTG;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GUIMachineDiFurnaceRTG extends GuiInfoContainer {
	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID, "textures/gui/processing/gui_rtg_difurnace.png");
	private TileEntityDiFurnaceRTG bFurnace;

	public GUIMachineDiFurnaceRTG(InventoryPlayer playerInv, TileEntityDiFurnaceRTG te) {
		super(new ContainerMachineDiFurnaceRTG(playerInv, te));
		this.bFurnace = te;
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	@Untested
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		String[] descText = I18nUtil.resolveKeyArray("desc.gui.rtgBFurnace.desc");
		String[] heatText = I18nUtil.resolveKeyArray("desc.gui.rtg.heat", this.bFurnace.getPower());
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 15, this.guiTop + 36 + 16, 16, 16, this.guiLeft - 8, this.guiTop + 36 + 16, descText);
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 58, this.guiTop + 36, 18, 16, mouseX, mouseY, heatText);
		
		List<ItemRTGPellet> pellets = ItemRTGPellet.pelletList;
		String[] pelletText = new String[pellets.size() + 1];
		pelletText[0] = I18nUtil.resolveKey("desc.gui.rtg.pellets");
		
		for(int i = 0; i < pellets.size(); i++) {
			ItemRTGPellet pellet = pellets.get(i);
			pelletText[i + 1] = I18nUtil.resolveKey("desc.gui.rtg.pelletHeat", I18nUtil.resolveKey(pellet.getUnlocalizedName() + ".name"), pellet.getHeat());
		}
		
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 15, this.guiTop + 36, 16, 16, this.guiLeft - 8, this.guiTop + 36 + 16, pelletText);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
		String name = this.bFurnace.hasCustomInventoryName() ? this.bFurnace.getInventoryName() : I18n.format(this.bFurnace.getInventoryName());

		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMachineDiFurnaceRTG.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		if(this.bFurnace.isInvalid()) {
			TileEntity te = this.bFurnace.getWorldObj().getTileEntity(this.bFurnace.xCoord, this.bFurnace.yCoord, this.bFurnace.zCoord);
			if(te instanceof TileEntityDiFurnaceRTG) {
				this.bFurnace = (TileEntityDiFurnaceRTG) te;
			}
		}

		if(this.bFurnace.getPower() >= 15)
			drawTexturedModalRect(this.guiLeft + 58, this.guiTop + 36, 176, 31, 18, 16);

		int p = this.bFurnace.getDiFurnaceProgressScaled(24);
		drawTexturedModalRect(this.guiLeft + 101, this.guiTop + 35, 176, 14, p + 1, 17);
		drawInfoPanel(this.guiLeft - 15, this.guiTop + 36, 16, 16, 2);
		drawInfoPanel(this.guiLeft - 15, this.guiTop + 36 + 16, 16, 16, 3);
	}

}
