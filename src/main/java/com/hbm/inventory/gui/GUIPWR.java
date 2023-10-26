package com.hbm.inventory.gui;

import java.util.Locale;

import org.apache.commons.lang3.math.NumberUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerPWR;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.util.GaugeUtil;
import com.hbm.tileentity.machine.TileEntityPWRController;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GUIPWR extends GuiInfoContainer {

	protected TileEntityPWRController controller;
	private final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_pwr.png");
	
	private GuiTextField field;
	
	public GUIPWR(InventoryPlayer inventory, TileEntityPWRController controller) {
		super(new ContainerPWR(inventory, controller));
		this.controller = controller;
		
		this.xSize = 176;
		this.ySize = 188;
	}

	@Override
	public void initGui() {
		super.initGui();
		
		Keyboard.enableRepeatEvents(true);
		
		this.field = new GuiTextField(this.fontRendererObj, this.guiLeft + 57, this.guiTop + 63, 30, 8);
		this.field.setTextColor(0x00ff00);
		this.field.setDisabledTextColour(0x008000);
		this.field.setEnableBackgroundDrawing(false);
		this.field.setMaxStringLength(3);
		
		this.field.setText((100 - this.controller.rodTarget) + "");
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);
		
		this.drawCustomInfoStat(x, y, this.guiLeft + 115, this.guiTop + 31, 18, 18, x, y, new String[] { "Core: " + String.format(Locale.US, "%,d", this.controller.coreHeat) + " / " + String.format(Locale.US, "%,d", TileEntityPWRController.coreHeatCapacity) + " TU" });
		this.drawCustomInfoStat(x, y, this.guiLeft + 151, this.guiTop + 31, 18, 18, x, y, new String[] { "Hull: " + String.format(Locale.US, "%,d", this.controller.hullHeat) + " / " + String.format(Locale.US, "%,d", TileEntityPWRController.hullHeatCapacity) + " TU" });

		this.drawCustomInfoStat(x, y, this.guiLeft + 52, this.guiTop + 31, 36, 18, x, y, new String[] { ((int) (this.controller.progress * 100 / this.controller.processTime)) + "%" });
		this.drawCustomInfoStat(x, y, this.guiLeft + 52, this.guiTop + 53, 54, 4, x, y, "Control rod level: " + (100 - this.controller.rodLevel) + "%");
		
		if(this.controller.typeLoaded != -1 && this.controller.amountLoaded > 0) {
			ItemStack display = new ItemStack(ModItems.pwr_fuel, 1, this.controller.typeLoaded);
			if(this.guiLeft + 88 <= x && this.guiLeft + 88 + 18 > x && this.guiTop + 4 < y && this.guiTop + 4 + 18 >= y) renderToolTip(display, x, y);
		}
		
		this.controller.tanks[0].renderTankInfo(this, x, y, this.guiLeft + 8, this.guiTop + 5, 16, 52);
		this.controller.tanks[1].renderTankInfo(this, x, y, this.guiLeft + 26, this.guiTop + 5, 16, 52);
	}

	@Override
	protected void drawItemStack(ItemStack stack, int x, int y, String label) {
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 0.0F, 32.0F);
		this.zLevel = 200.0F;
		GuiScreen.itemRender.zLevel = 200.0F;
		FontRenderer font = null;
		if(stack != null) font = stack.getItem().getFontRenderer(stack);
		if(font == null) font = this.fontRendererObj;
		GuiScreen.itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), stack, x, y);
		GL11.glScaled(0.5, 0.5, 0.5);
		GuiScreen.itemRender.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), stack, (x + font.getStringWidth(label) / 4) * 2, (y + 15) * 2, label);
		this.zLevel = 0.0F;
		GuiScreen.itemRender.zLevel = 0.0F;
		GL11.glPopMatrix();
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		
		double scale = 1.25;
		String flux = String.format(Locale.US, "%,.1f", this.controller.flux);
		GL11.glScaled(1 / scale, 1 / scale, 1);
		this.fontRendererObj.drawString(flux, (int) (165 * scale - this.fontRendererObj.getStringWidth(flux)), (int)(64 * scale), 0x00ff00);
		GL11.glScaled(scale, scale, 1);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(this.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		if(this.controller.hullHeat > TileEntityPWRController.hullHeatCapacity * 0.8 || this.controller.coreHeat > TileEntityPWRController.coreHeatCapacity * 0.8)
			drawTexturedModalRect(this.guiLeft + 147, this.guiTop, 176, 14, 26, 26);

		int p = (int) (this.controller.progress * 33 / this.controller.processTime);
		drawTexturedModalRect(this.guiLeft + 54, this.guiTop + 33, 176, 0, p, 14);

		int c = (int) (this.controller.rodLevel * 52 / 100);
		drawTexturedModalRect(this.guiLeft + 53, this.guiTop + 54, 176, 40, c, 2);

		//GaugeUtil.renderGauge(Gauge.ROUND_SMALL, guiLeft + 115, guiTop + 31, this.zLevel, (double) controller.coreHeat / (double) controller.coreHeatCapacity);
		//GaugeUtil.renderGauge(Gauge.ROUND_SMALL, guiLeft + 151, guiTop + 31, this.zLevel, (double) controller.hullHeat / (double) controller.hullHeatCapacity);

		GaugeUtil.drawSmoothGauge(this.guiLeft + 124, this.guiTop + 40, this.zLevel, (double) this.controller.coreHeat / (double) TileEntityPWRController.coreHeatCapacity, 5, 2, 1, 0x7F0000);
		GaugeUtil.drawSmoothGauge(this.guiLeft + 160, this.guiTop + 40, this.zLevel, (double) this.controller.hullHeat / (double) TileEntityPWRController.hullHeatCapacity, 5, 2, 1, 0x7F0000);
		
		if(this.controller.typeLoaded != -1 && this.controller.amountLoaded > 0) {
			ItemStack display = new ItemStack(ModItems.pwr_fuel, 1, this.controller.typeLoaded);
			drawItemStack(display, this.guiLeft + 89, this.guiTop + 5, EnumChatFormatting.YELLOW + "" + this.controller.amountLoaded + "/" + this.controller.rodCount);
			RenderHelper.enableGUIStandardItemLighting();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
		
		GL11.glDisable(GL11.GL_LIGHTING);

		this.controller.tanks[0].renderTank(this.guiLeft + 8, this.guiTop + 57, this.zLevel, 16, 52);
		this.controller.tanks[1].renderTank(this.guiLeft + 26, this.guiTop + 57, this.zLevel, 16, 52);
		
		this.field.drawTextBox();
	}
	
	/*private void drawGauge(int x, int y, double d) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		d = MathHelper.clamp_double(d, 0, 1);
		
		float angle = (float) Math.toRadians(-d * 270 - 45);
		Vec3 tip = Vec3.createVectorHelper(0, 5, 0);
		Vec3 left = Vec3.createVectorHelper(1, -2, 0);
		Vec3 right = Vec3.createVectorHelper(-1, -2, 0);

		tip.rotateAroundZ(angle);
		left.rotateAroundZ(angle);
		right.rotateAroundZ(angle);
		
		Tessellator tess = Tessellator.instance;
		tess.startDrawing(GL11.GL_TRIANGLES);
		tess.setColorOpaque_F(0F, 0F, 0F);
		double mult = 1.5;
		tess.addVertex(x + tip.xCoord * mult, y + tip.yCoord * mult, this.zLevel);
		tess.addVertex(x + left.xCoord * mult, y + left.yCoord * mult, this.zLevel);
		tess.addVertex(x + right.xCoord * mult, y + right.yCoord * mult, this.zLevel);
		tess.setColorOpaque_F(0.75F, 0F, 0F);
		tess.addVertex(x + tip.xCoord, y + tip.yCoord, this.zLevel);
		tess.addVertex(x + left.xCoord, y + left.yCoord, this.zLevel);
		tess.addVertex(x + right.xCoord, y + right.yCoord, this.zLevel);
		tess.draw();
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}*/

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int i) {
		super.mouseClicked(mouseX, mouseY, i);
		this.field.mouseClicked(mouseX, mouseY, i);
		
		if(this.guiLeft + 88 <= mouseX && this.guiLeft + 88 + 18 > mouseX && this.guiTop + 58 < mouseY && this.guiTop + 58 + 18 >= mouseY) {
			
			if(NumberUtils.isNumber(this.field.getText())) {
				int level = (int)MathHelper.clamp_double(Double.parseDouble(this.field.getText()), 0, 100);
				this.field.setText(level + "");
				
				NBTTagCompound control = new NBTTagCompound();
				control.setInteger("control", 100 - level);
				PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(control, this.controller.xCoord, this.controller.yCoord, this.controller.zCoord));
				this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1F));
				
			}
		}
	}

	@Override
	protected void keyTyped(char c, int i) {
		if(this.field.textboxKeyTyped(c, i)) return;
		super.keyTyped(c, i);
	}
}
