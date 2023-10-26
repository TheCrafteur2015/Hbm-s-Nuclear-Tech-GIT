package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerLaunchTable;
import com.hbm.items.weapon.ItemCustomMissile;
import com.hbm.items.weapon.ItemMissile.PartSize;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.util.MissileMultipart;
import com.hbm.render.util.MissilePronter;
import com.hbm.tileentity.bomb.TileEntityLaunchTable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GUIMachineLaunchTable extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/weapon/gui_launch_table.png");
	private TileEntityLaunchTable launcher;
	
	public GUIMachineLaunchTable(InventoryPlayer invPlayer, TileEntityLaunchTable tedf) {
		super(new ContainerLaunchTable(invPlayer, tedf));
		this.launcher = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.launcher.tanks[0].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 116, this.guiTop + 36, 16, 34);
		this.launcher.tanks[1].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 134, this.guiTop + 36, 16, 34);
		drawCustomInfo(this, mouseX, mouseY, this.guiLeft + 152, this.guiTop + 88 - 52, 16, 52, new String[] { "Solid Fuel: " + this.launcher.solid + "l" });
		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 134, this.guiTop + 113, 34, 6, this.launcher.power, TileEntityLaunchTable.maxPower);
		drawCustomInfo(this, mouseX, mouseY, this.guiLeft + 7, this.guiTop + 98, 18, 18, new String[] { "Size 10 & 10/15" });
		drawCustomInfo(this, mouseX, mouseY, this.guiLeft + 25, this.guiTop + 98, 18, 18, new String[] { "Size 15 & 15/20" });
		drawCustomInfo(this, mouseX, mouseY, this.guiLeft + 43, this.guiTop + 98, 18, 18, new String[] { "Size 20" });

		String[] text = new String[] { "Accepts custom missiles", "of all sizes, as long as the", "correct size setting is selected." };
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 36, 16, 16, this.guiLeft - 8, this.guiTop + 36 + 16, text);
		
		String[] text1 = new String[] { "Detonator can only trigger center block." };
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 36 + 16, 16, 16, this.guiLeft - 8, this.guiTop + 36 + 16, text1);
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);
		
    	if(this.guiLeft + 7 <= x && this.guiLeft + 7 + 18 > x && this.guiTop + 98 < y && this.guiTop + 98 + 18 >= y) {
    		
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.launcher.xCoord, this.launcher.yCoord, this.launcher.zCoord, PartSize.SIZE_10.ordinal(), 0));
    	}
		
    	if(this.guiLeft + 25 <= x && this.guiLeft + 25 + 18 > x && this.guiTop + 98 < y && this.guiTop + 98 + 18 >= y) {
    		
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.launcher.xCoord, this.launcher.yCoord, this.launcher.zCoord, PartSize.SIZE_15.ordinal(), 0));
    	}
		
    	if(this.guiLeft + 43 <= x && this.guiLeft + 43 + 18 > x && this.guiTop + 98 < y && this.guiTop + 98 + 18 >= y) {
    		
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.launcher.xCoord, this.launcher.yCoord, this.launcher.zCoord, PartSize.SIZE_20.ordinal(), 0));
    	}
    }

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.launcher.hasCustomInventoryName() ? this.launcher.getInventoryName() : I18n.format(this.launcher.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMachineLaunchTable.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int i = (int)this.launcher.getPowerScaled(34);
		drawTexturedModalRect(this.guiLeft + 134, this.guiTop + 113, 176, 96, i, 6);
		
		int j = (int)this.launcher.getSolidScaled(52);
		drawTexturedModalRect(this.guiLeft + 152, this.guiTop + 88 - j, 176, 96 - j, 16, j);
		
		if(this.launcher.isMissileValid())
			drawTexturedModalRect(this.guiLeft + 25, this.guiTop + 35, 176, 26, 18, 18);
		
		if(this.launcher.hasDesignator())
			drawTexturedModalRect(this.guiLeft + 25, this.guiTop + 71, 176, 26, 18, 18);
		
		if(this.launcher.liquidState() == 1)
			drawTexturedModalRect(this.guiLeft + 121, this.guiTop + 23, 176, 0, 6, 8);
		if(this.launcher.liquidState() == 0)
			drawTexturedModalRect(this.guiLeft + 121, this.guiTop + 23, 182, 0, 6, 8);
		
		if(this.launcher.oxidizerState() == 1)
			drawTexturedModalRect(this.guiLeft + 139, this.guiTop + 23, 176, 0, 6, 8);
		if(this.launcher.oxidizerState() == 0)
			drawTexturedModalRect(this.guiLeft + 139, this.guiTop + 23, 182, 0, 6, 8);
		
		if(this.launcher.solidState() == 1)
			drawTexturedModalRect(this.guiLeft + 157, this.guiTop + 23, 176, 0, 6, 8);
		if(this.launcher.solidState() == 0)
			drawTexturedModalRect(this.guiLeft + 157, this.guiTop + 23, 182, 0, 6, 8);
		
		switch(this.launcher.padSize) {
		
		case SIZE_10:
			drawTexturedModalRect(this.guiLeft + 7, this.guiTop + 98, 176, 8, 18, 18);
			break;
		case SIZE_15:
			drawTexturedModalRect(this.guiLeft + 25, this.guiTop + 98, 194, 8, 18, 18);
			break;
		case SIZE_20:
			drawTexturedModalRect(this.guiLeft + 43, this.guiTop + 98, 212, 8, 18, 18);
			break;
		default:
			break;
		
		}
		
		drawInfoPanel(this.guiLeft - 16, this.guiTop + 36, 16, 16, 2);
		drawInfoPanel(this.guiLeft - 16, this.guiTop + 36 + 16, 16, 16, 11);
		
		this.launcher.tanks[0].renderTank(this.guiLeft + 116, this.guiTop + 70, this.zLevel, 16, 34);
		this.launcher.tanks[1].renderTank(this.guiLeft + 134, this.guiTop + 70, this.zLevel, 16, 34);
		
		/// DRAW MISSILE START
		GL11.glPushMatrix();

		MissileMultipart missile;
		
		if(this.launcher.isMissileValid()) {
			ItemStack custom = this.launcher.getStackInSlot(0);
			
			missile = new MissileMultipart();
			
			missile = MissileMultipart.loadFromStruct(ItemCustomMissile.getStruct(custom));
		
			GL11.glTranslatef(this.guiLeft + 88, this.guiTop + 115, 100);
			
			double size = 5 * 18;
			double scale = size / Math.max(missile.getHeight(), 6);

			GL11.glRotatef(90, 0, 1, 0);
			GL11.glTranslated(missile.getHeight() / 2D * scale, 0, 0);
			GL11.glScaled(scale, scale, scale);
			
			GL11.glScalef(-1, -1, -1);
			
			MissilePronter.prontMissile(missile, Minecraft.getMinecraft().getTextureManager());
		}
		
		GL11.glPopMatrix();
		/// DRAW MISSILE END
	}
}
