package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.bomb.NukeCustom;
import com.hbm.inventory.container.ContainerNukeCustom;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.bomb.TileEntityNukeCustom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUINukeCustom extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/weapon/gunBombSchematic.png");
	private TileEntityNukeCustom testNuke;
	
	public GUINukeCustom(InventoryPlayer invPlayer, TileEntityNukeCustom tedf) {
		super(new ContainerNukeCustom(invPlayer, tedf));
		this.testNuke = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		String[] text;
		
		text = new String[] { EnumChatFormatting.YELLOW + "Conventional Explosives (Level " + this.testNuke.tnt + "/" + Math.min(this.testNuke.tnt, NukeCustom.maxTnt) + ")",
				"Caps at " + NukeCustom.maxTnt,
				"N²-like above level 75",
				EnumChatFormatting.ITALIC + "\"Goes boom\"" };
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 16, this.guiTop + 89, 18, 18, mouseX, mouseY, text);
		
		text = new String[] { EnumChatFormatting.YELLOW + "Nuclear (Level " + this.testNuke.nuke + "/" + this.testNuke.getNukeAdj() + ")",
				"Requires TNT level 16",
				"Caps at " + NukeCustom.maxNuke,
				"Has fallout",
				EnumChatFormatting.ITALIC + "\"Now I am become death, destroyer of worlds.\"" };
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 34, this.guiTop + 89, 18, 18, mouseX, mouseY, text);
		
		text = new String[] { EnumChatFormatting.YELLOW + "Thermonuclear (Level " + this.testNuke.hydro + "/" + this.testNuke.getHydroAdj() + ")",
				"Requires nuclear level 100",
				"Caps at " + NukeCustom.maxHydro,
				"Reduces added fallout by salted stage by 75%",
				EnumChatFormatting.ITALIC + "\"And for my next trick, I'll make",
				EnumChatFormatting.ITALIC + "the island of Elugelab disappear!\"" };
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 52, this.guiTop + 89, 18, 18, mouseX, mouseY, text);
		
		text = new String[] { EnumChatFormatting.YELLOW + "Antimatter (Level " + this.testNuke.amat + "/" + this.testNuke.getAmatAdj() + ")",
				"Requires nuclear level 50",
				"Caps at " + NukeCustom.maxAmat,
				EnumChatFormatting.ITALIC + "\"Antimatter, Balefire, whatever.\"" };
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 70, this.guiTop + 89, 18, 18, mouseX, mouseY, text);
		
		text = new String[] { EnumChatFormatting.YELLOW + "Salted (Level " + this.testNuke.dirty + "/" + Math.min(this.testNuke.dirty, 100) + ")",
				"Extends fallout of nuclear and",
				"thermonuclear stages",
				"Caps at 100",
				EnumChatFormatting.ITALIC + "\"Not to be confused with tablesalt.\"" };
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 88, this.guiTop + 89, 18, 18, mouseX, mouseY, text);
		
		text = new String[] { EnumChatFormatting.YELLOW + "Schrabidium (Level " + this.testNuke.schrab + "/" + this.testNuke.getSchrabAdj() + ")",
				"Requires nuclear level 50",
				"Caps at " + NukeCustom.maxSchrab,
				EnumChatFormatting.ITALIC + "\"For the hundredth time,",
				EnumChatFormatting.ITALIC + "you can't bypass these caps!\"" };
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 106, this.guiTop + 89, 18, 18, mouseX, mouseY, text);
		
		text = new String[] { EnumChatFormatting.YELLOW + "Ice cream (Level unknown)",
				EnumChatFormatting.ITALIC + "\"Probably not ice cream but the label came off.\"" };
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 142, this.guiTop + 89, 18, 18, mouseX, mouseY, text);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.testNuke.hasCustomInventoryName() ? this.testNuke.getInventoryName() : I18n.format(this.testNuke.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUINukeCustom.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		if(this.testNuke.euph > 0)
			drawTexturedModalRect(this.guiLeft + 142, this.guiTop + 89, 176, 108, 18, 18);
		else if(this.testNuke.schrab > 0)
			drawTexturedModalRect(this.guiLeft + 106, this.guiTop + 89, 176, 90, 18, 18);
		else if(this.testNuke.amat > 0)
			drawTexturedModalRect(this.guiLeft + 70, this.guiTop + 89, 176, 54, 18, 18);
		else if(this.testNuke.hydro > 0)
			drawTexturedModalRect(this.guiLeft + 52, this.guiTop + 89, 176, 36, 18, 18);
		else if(this.testNuke.nuke > 0)
			drawTexturedModalRect(this.guiLeft + 34, this.guiTop + 89, 176, 18, 18, 18);
		else if(this.testNuke.tnt > 0)
			drawTexturedModalRect(this.guiLeft + 16, this.guiTop + 89, 176, 0, 18, 18);
		
		if(this.testNuke.dirty > 0 && 
				this.testNuke.nuke > 0 &&
				this.testNuke.amat == 0 &&
				this.testNuke.schrab == 0 &&
				this.testNuke.euph == 0)
			drawTexturedModalRect(this.guiLeft + 88, this.guiTop + 89, 176, 72, 18, 18);
	}
}
