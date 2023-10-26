package com.hbm.inventory.gui;

import org.apache.commons.lang3.math.NumberUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerNukeFstbmb;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.bomb.TileEntityNukeBalefire;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GUINukeFstbmb extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/weapon/fstbmbSchematic.png");
	private TileEntityNukeBalefire bomb;
	private GuiTextField timer;

	public GUINukeFstbmb(InventoryPlayer invPlayer, TileEntityNukeBalefire bomb) {
		super(new ContainerNukeFstbmb(invPlayer, bomb));
		this.bomb = bomb;

		this.xSize = 176;
		this.ySize = 222;
	}

	@Override
	public void initGui() {

		super.initGui();

		Keyboard.enableRepeatEvents(true);
		this.timer = new GuiTextField(this.fontRendererObj, this.guiLeft + 94, this.guiTop + 40, 29, 12);
		this.timer.setTextColor(0xff0000);
		this.timer.setDisabledTextColour(0x800000);
		this.timer.setEnableBackgroundDrawing(false);
		this.timer.setMaxStringLength(3);
		this.timer.setText(String.valueOf(this.bomb.timer / 20));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
		this.timer.mouseClicked(x, y, i);

		if(!this.bomb.started) {
			if(this.guiLeft + 142 <= x && this.guiLeft + 142 + 18 > x && this.guiTop + 35 < y && this.guiTop + 35 + 18 >= y) {

				PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.bomb.xCoord, this.bomb.yCoord, this.bomb.zCoord, 0, 0));
			}
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {

		String name = this.bomb.hasCustomInventoryName() ? this.bomb.getInventoryName() : I18n.format(this.bomb.getInventoryName());

		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);

		if(this.bomb.hasBattery()) {
			String timer = this.bomb.getMinutes() + ":" + this.bomb.getSeconds();
			double scale = 0.75;
			GL11.glScaled(scale, scale, scale);
			this.fontRendererObj.drawString(timer, (int) ((69 - this.fontRendererObj.getStringWidth(timer) / 2) * (1 / scale)), (int) (95.5 * (1 / scale)), 0xff0000);

			GL11.glScaled(1 / scale, 1 / scale, 1 / scale);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUINukeFstbmb.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		if(this.bomb.hasEgg())
			drawTexturedModalRect(this.guiLeft + 19, this.guiTop + 90, 176, 0, 30, 16);

		int battery = this.bomb.getBattery();

		if(battery == 1)
			drawTexturedModalRect(this.guiLeft + 88, this.guiTop + 93, 176, 16, 18, 10);
		else if(battery == 2)
			drawTexturedModalRect(this.guiLeft + 88, this.guiTop + 93, 194, 16, 18, 10);

		if(this.bomb.started)
			drawTexturedModalRect(this.guiLeft + 142, this.guiTop + 35, 176, 26, 18, 18);

		this.timer.drawTextBox();
	}

	@Override
	protected void keyTyped(char p_73869_1_, int p_73869_2_) {

		if(this.timer.textboxKeyTyped(p_73869_1_, p_73869_2_)) {

			if(NumberUtils.isNumber(this.timer.getText())) {
				int j = MathHelper.clamp_int((int) Double.parseDouble(this.timer.getText()), 1, 999);
				PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.bomb.xCoord, this.bomb.yCoord, this.bomb.zCoord, j, 1));
			}

		} else {
			super.keyTyped(p_73869_1_, p_73869_2_);
		}
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
}
