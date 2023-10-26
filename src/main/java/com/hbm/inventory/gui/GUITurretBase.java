package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerTurretBase;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.turret.TileEntityTurretBaseNT;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

@SuppressWarnings("deprecation")
public abstract class GUITurretBase extends GuiInfoContainer {

	protected static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/weapon/gui_turret_base.png");
	protected TileEntityTurretBaseNT turret;
	protected GuiTextField field;
	int index;
	
	public GUITurretBase(InventoryPlayer invPlayer, TileEntityTurretBaseNT tedf) {
		super(new ContainerTurretBase(invPlayer, tedf));
		this.turret = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void initGui() {

		super.initGui();

		Keyboard.enableRepeatEvents(true);
		this.field = new GuiTextField(this.fontRendererObj, this.guiLeft + 10, this.guiTop + 65, 50, 14);
		this.field.setTextColor(-1);
		this.field.setDisabledTextColour(-1);
		this.field.setEnableBackgroundDrawing(false);
		this.field.setMaxStringLength(25);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 152, this.guiTop + 45, 16, 52, this.turret.power, this.turret.getMaxPower());

		String on = EnumChatFormatting.GREEN + I18nUtil.resolveKey("turret.on");
		String off = EnumChatFormatting.RED + I18nUtil.resolveKey("turret.off");
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 8, this.guiTop + 30, 10, 10, mouseX, mouseY, I18nUtil.resolveKeyArray("turret.players", this.turret.targetPlayers ? on : off));
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 22, this.guiTop + 30, 10, 10, mouseX, mouseY, I18nUtil.resolveKeyArray("turret.animals", this.turret.targetAnimals ? on : off));
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 36, this.guiTop + 30, 10, 10, mouseX, mouseY, I18nUtil.resolveKeyArray("turret.mobs", this.turret.targetMobs ? on : off));
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 50, this.guiTop + 30, 10, 10, mouseX, mouseY, I18nUtil.resolveKeyArray("turret.machines", this.turret.targetMachines ? on : off));


		if(this.mc.thePlayer.inventory.getItemStack() == null && this.guiLeft + 79 <= mouseX && this.guiLeft + 79 + 54 > mouseX && this.guiTop + 62 < mouseY && this.guiTop + 62 + 54 >= mouseY) {
			
			boolean draw = true;
			for(int i = 1; i < 10; i++) {
				if(isMouseOverSlot(this.inventorySlots.getSlot(i), mouseX, mouseY) && this.inventorySlots.getSlot(i).getHasStack()) {
					draw = false;
					break;
				}
			}
			
			if(draw) {
				List<ItemStack> list = new ArrayList<>(this.turret.getAmmoTypesForDisplay());
				List<Object[]> lines = new ArrayList<>();
				ItemStack selected = list.get(0);
				
				if(list.size() > 1) {
					int cycle = (int) ((System.currentTimeMillis() % (1000 * list.size())) / 1000);
					selected = list.get(cycle).copy();
					selected.stackSize = 0;
					list.set(cycle, selected);
				}
				
				if(list.size() < 10) {
					lines.add(list.toArray());
				} else if(list.size() < 24) {
					lines.add(list.subList(0, list.size() / 2).toArray());
					lines.add(list.subList(list.size() / 2, list.size()).toArray());
				} else {
					int bound0 = (int) Math.ceil(list.size() / 3D);
					int bound1 = (int) Math.ceil(list.size() / 3D * 2D);
					lines.add(list.subList(0, bound0).toArray());
					lines.add(list.subList(bound0, bound1).toArray());
					lines.add(list.subList(bound1, list.size()).toArray());
				}
				
				lines.add(new Object[] {I18nUtil.resolveKey(selected.getDisplayName())});
				drawStackText(lines, mouseX, mouseY, this.fontRendererObj);
			}
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
		
		boolean flag = x >= this.field.xPosition && x < this.field.xPosition + this.field.width && y >= this.field.yPosition && y < this.field.yPosition + this.field.height;
		this.field.setFocused(flag);

		if(this.guiLeft + 115 <= x && this.guiLeft + 115 + 18 > x && this.guiTop + 26 < y && this.guiTop + 26 + 18 >= y) {

			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.turret.xCoord, this.turret.yCoord, this.turret.zCoord, 0, 0));
			return;
		}

		if(this.guiLeft + 8 <= x && this.guiLeft + 8 + 10 > x && this.guiTop + 30 < y && this.guiTop + 30 + 10 >= y) {

			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.turret.xCoord, this.turret.yCoord, this.turret.zCoord, 0, 1));
			return;
		}

		if(this.guiLeft + 22 <= x && this.guiLeft + 22 + 10 > x && this.guiTop + 30 < y && this.guiTop + 30 + 10 >= y) {

			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.turret.xCoord, this.turret.yCoord, this.turret.zCoord, 0, 2));
			return;
		}

		if(this.guiLeft + 36 <= x && this.guiLeft + 36 + 10 > x && this.guiTop + 30 < y && this.guiTop + 30 + 10 >= y) {

			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.turret.xCoord, this.turret.yCoord, this.turret.zCoord, 0, 3));
			return;
		}

		if(this.guiLeft + 50 <= x && this.guiLeft + 50 + 10 > x && this.guiTop + 30 < y && this.guiTop + 30 + 10 >= y) {

			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(this.turret.xCoord, this.turret.yCoord, this.turret.zCoord, 0, 4));
			return;
		}
		
		int count = getCount();
		
		if(count > 0) {
			
			if(this.guiLeft + 7 <= x && this.guiLeft + 7 + 18 > x && this.guiTop + 80 < y && this.guiTop + 80 + 18 >= y) {
	
				this.index--;
				if(this.index < 0)
					this.index = count - 1;
				this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
				return;
			}

			if(this.guiLeft + 43 <= x && this.guiLeft + 43 + 18 > x && this.guiTop + 80 < y && this.guiTop + 80 + 18 >= y) {
	
				this.index++;
				this.index %= count;
				this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
				return;
			}
		}
		
		if(this.guiLeft + 7 <= x && this.guiLeft + 7 + 18 > x && this.guiTop + 98 < y && this.guiTop + 98 + 18 >= y) {
			
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			
			if(this.field.getText().isEmpty())
				return;
			
			NBTTagCompound data = new NBTTagCompound();
			data.setString("name", this.field.getText());
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, this.turret.xCoord, this.turret.yCoord, this.turret.zCoord));
			
			this.field.setText("");
			return;
		}

		if(this.guiLeft + 43 <= x && this.guiLeft + 43 + 18 > x && this.guiTop + 98 < y && this.guiTop + 98 + 18 >= y) {
			
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("del", this.index);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, this.turret.xCoord, this.turret.yCoord, this.turret.zCoord));
			return;
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.turret.hasCustomInventoryName() ? this.turret.getInventoryName() : I18n.format(this.turret.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		
		List<String> names = this.turret.getWhitelist();
		
		String n = EnumChatFormatting.ITALIC + I18nUtil.resolveKey("turret.none");
		
		while(this.index >= getCount())
			this.index--;
		
		if(this.index < 0)
			this.index = 0;
		
		if(names != null) {
			n = names.get(this.index);
		}
		
		String t = this.field.getText();
		
		String cursor = System.currentTimeMillis() % 1000 < 500 ? " " : "||";
		
		if(this.field.isFocused())
			t = t.substring(0, this.field.getCursorPosition()) + cursor + t.substring(this.field.getCursorPosition(), t.length());
		
		double scale = 2;
		
		GL11.glScaled(1D / scale, 1D / scale, 1);
		this.fontRendererObj.drawString(n, (int)(12 * scale), (int)(51 * scale), 0x00ff00);
		this.fontRendererObj.drawString(t, (int)(12 * scale), (int)(69 * scale), 0x00ff00);
		GL11.glScaled(scale, scale, 1);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int mX, int mY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(getTexture());
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		if(this.guiLeft + 7 <= mX && this.guiLeft + 7 + 18 > mX && this.guiTop + 80 < mY && this.guiTop + 80 + 18 >=mY) {
			drawTexturedModalRect(this.guiLeft + 7, this.guiTop + 80, 176, 58, 18, 18);
		}
		if(this.guiLeft + 43 <= mX && this.guiLeft + 43 + 18 > mX && this.guiTop + 80 < mY && this.guiTop + 80 + 18 >=mY) {
			drawTexturedModalRect(this.guiLeft + 43, this.guiTop + 80, 194, 58, 18, 18);
		}
		if(this.guiLeft + 7 <= mX && this.guiLeft + 7 + 18 > mX && this.guiTop + 98 < mY && this.guiTop + 98 + 18 >=mY) {
			drawTexturedModalRect(this.guiLeft + 7, this.guiTop + 98, 176, 76, 18, 18);
		}
		if(this.guiLeft + 43 <= mX && this.guiLeft + 43 + 18 > mX && this.guiTop + 98 < mY && this.guiTop + 98 + 18 >=mY) {
			drawTexturedModalRect(this.guiLeft + 43, this.guiTop + 98, 194, 76, 18, 18);
		}
		
		int i = this.turret.getPowerScaled(53);
		drawTexturedModalRect(this.guiLeft + 152, this.guiTop + 97 - i, 194, 52 - i, 16, i);
		
		if(this.turret.isOn)
			drawTexturedModalRect(this.guiLeft + 115, this.guiTop + 26, 176, 40, 18, 18);
		
		if(this.turret.targetPlayers)
			drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 30, 176, 0, 10, 10);
		
		if(this.turret.targetAnimals)
			drawTexturedModalRect(this.guiLeft + 22, this.guiTop + 30, 176, 10, 10, 10);
		
		if(this.turret.targetMobs)
			drawTexturedModalRect(this.guiLeft + 36, this.guiTop + 30, 176, 20, 10, 10);
		
		if(this.turret.targetMachines)
			drawTexturedModalRect(this.guiLeft + 50, this.guiTop + 30, 176, 30, 10, 10);
		
		int tallies = this.turret.stattrak;
		
		if(tallies >= 36) {
			
			drawTexturedModalRect(this.guiLeft + 77, this.guiTop + 50, 176, 120, 63, 6);
			
		} else {
			
			int steps = (int)Math.ceil(tallies / 5D);
			
			for(int s = 0; s < steps; s++) {
				
				int m = tallies % 5;
				
				if(s < steps - 1 || m == 0) {
					drawTexturedModalRect(this.guiLeft + 77 + 9 * s, this.guiTop + 50, 194, 94, 9, 6);
				} else {
					
					drawTexturedModalRect(this.guiLeft + 77 + 9 * s, this.guiTop + 50, 176, 94, m * 2, 6);
				}
			}
		}
	}
	
	protected ResourceLocation getTexture() {
		return GUITurretBase.texture;
	}
	
	private int getCount() {
		
		List<String> names = this.turret.getWhitelist();
		
		if(names == null)
			return 0;
		
		return names.size();
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override
	protected void keyTyped(char p_73869_1_, int p_73869_2_) {
		
		if(this.field.textboxKeyTyped(p_73869_1_, p_73869_2_)) {
			
		} else {
			super.keyTyped(p_73869_1_, p_73869_2_);
		}
	}
}
