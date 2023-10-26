package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineTurbineGas;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.trait.FT_Combustible;
import com.hbm.inventory.fluid.trait.FT_Combustible.FuelGrade;
import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityMachineTurbineGas;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIMachineTurbineGas extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/generators/gui_turbinegas.png");
	private static ResourceLocation gauge_tex = new ResourceLocation(RefStrings.MODID + ":textures/gui/gauges/button_big.png");
	private TileEntityMachineTurbineGas turbinegas;
	
	int yStart;
	int slidStart;
	
	public GUIMachineTurbineGas(InventoryPlayer invPlayer, TileEntityMachineTurbineGas te) {
		super(new ContainerMachineTurbineGas(invPlayer, te));
		this.turbinegas = te;
		
		this.xSize = 176;
		this.ySize = 223;
	}
	
	//@Override
	@Override
	protected void mouseClicked(int x, int y, int i) {
		
		super.mouseClicked(x, y, i);
		
		this.slidStart = this.turbinegas.powerSliderPos;
		this.yStart = y;
		
		if(Math.sqrt(Math.pow((x - this.guiLeft - 88), 2) + Math.pow((y - this.guiTop - 40), 2)) <= 8) { //start-stop circular button
			
			if(this.turbinegas.counter == 0 || this.turbinegas.counter == 579) {
				
				int state = this.turbinegas.state - 1; //offline(0) to startup(-1), online(1) to offline(0)
				
				this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
				
				NBTTagCompound data = new NBTTagCompound();
				data.setInteger("state", state);
				PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, this.turbinegas.xCoord, this.turbinegas.yCoord, this.turbinegas.zCoord));
			}
			else 
				return;
		}	
		
		if(this.turbinegas.state == 1 && x > this.guiLeft + 74 && x <= this.guiLeft + 74 + 29 && y >= this.guiTop + 86 && y < this.guiTop + 86 + 13) { //auto mode button
			
			boolean automode = !this.turbinegas.autoMode;
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("autoMode", automode);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, this.turbinegas.xCoord, this.turbinegas.yCoord, this.turbinegas.zCoord));
		}
		
		if(this.turbinegas.state == 1 && (this.guiTop + 97 - this.slidStart) <= this.yStart && (this.guiTop + 103 - this.slidStart) > this.yStart && this.guiLeft + 36 < x && this.guiLeft + 52 >= x) { //power slider
			
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("autoMode", false); //if you click the slider with automode on, turns off automode
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, this.turbinegas.xCoord, this.turbinegas.yCoord, this.turbinegas.zCoord));
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
		}
	}
	
	@Override
	protected void mouseClickMove(int x, int y, int p_146273_3_, long p_146273_4_) {
		
		super.mouseClickMove(x, y, p_146273_3_, p_146273_4_);
	
		int slidPos = this.turbinegas.powerSliderPos;
	
		if(!this.turbinegas.autoMode && this.turbinegas.state == 1 && this.guiLeft + 36 < x && this.guiLeft + 52 >= x && this.guiTop + 37 < y && this.guiTop + 103 >= y) { //area in which the slider can move
		
			if((this.guiTop + 97 - this.slidStart) <= this.yStart && (this.guiTop + 103 - this.slidStart) > this.yStart) {
				slidPos = this.guiTop + 100 - y;
			
				if(slidPos > 60) 
					slidPos = 60;
				else if(slidPos < 0)
					slidPos = 0;
			
				NBTTagCompound data = new NBTTagCompound();
				data.setDouble("slidPos", slidPos);
				PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, this.turbinegas.xCoord, this.turbinegas.yCoord, this.turbinegas.zCoord));
			}
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		
		super.drawScreen(mouseX, mouseY, f);

		drawElectricityInfo(this, mouseX, mouseY, this.guiLeft + 26, this.guiTop + 108, 142, 16, this.turbinegas.power, this.turbinegas.getMaxPower());
		
		if(this.turbinegas.powerSliderPos == 0)
			this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 36, this.guiTop + 36, 16, 66, mouseX, mouseY, new String[] {"Turbine idle"});
		else
			this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 36, this.guiTop + 36, 16, 66, mouseX, mouseY, new String[] {(this.turbinegas.powerSliderPos) * 100 / 60 + "% power"});	
		
		if(this.turbinegas.temp >= 20)
			this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 133, this.guiTop + 23, 8, 72, mouseX, mouseY, new String[] {"Temperature: " + (this.turbinegas.temp) + "°C"});
		else
			this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft + 133, this.guiTop + 23, 8, 72, mouseX, mouseY, new String[] {"Temperature: 20°C"});
		
		this.turbinegas.tanks[0].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 8, this.guiTop + 16, 16, 48);
		this.turbinegas.tanks[1].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 8, this.guiTop + 70, 16, 32);
		this.turbinegas.tanks[2].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 147, this.guiTop + 61, 16, 36);
		this.turbinegas.tanks[3].renderTankInfo(this, mouseX, mouseY, this.guiLeft + 147, this.guiTop + 21, 16, 36);
		
		String[] info = I18nUtil.resolveKeyArray("desc.gui.turbinegas.automode");
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 34, 16, 16, this.guiLeft - 8, this.guiTop + 44 + 16, info);
		
		List<String> fuels = new ArrayList<>();
		fuels.add(I18nUtil.resolveKey("desc.gui.turbinegas.fuels"));
		for(FluidType type : Fluids.getInNiceOrder()) {
			if(type.hasTrait(FT_Combustible.class) && type.getTrait(FT_Combustible.class).getGrade() == FuelGrade.GAS) {
				fuels.add("  " + type.getLocalizedName());
			}
		}
		this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 34 + 16, 16, 16, this.guiLeft - 8, this.guiTop + 44 + 16, fuels);
		
		String[] warning = I18nUtil.resolveKeyArray("desc.gui.turbinegas.warning");
		if(this.turbinegas.tanks[0].getFill() < 5000 || this.turbinegas.tanks[1].getFill() < 1000)
			this.drawCustomInfoStat(mouseX, mouseY, this.guiLeft - 16, this.guiTop + 34 + 32, 16, 16, this.guiLeft - 8, this.guiTop + 44 + 16, warning);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float iinterpolation, int x, int y) {
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMachineTurbineGas.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize); //the main thing
		
		if(this.turbinegas.autoMode)
			drawTexturedModalRect(this.guiLeft + 74, this.guiTop + 86, 194, 11, 29, 13); //auto mode button
		else
			drawTexturedModalRect(this.guiLeft + 74, this.guiTop + 86, 194, 24, 29, 13);
		
		switch(this.turbinegas.state) {
			case 0:
				drawTexturedModalRect(this.guiLeft + 80, this.guiTop + 32, 178, 38, 16, 16); //red button
			break;
			case -1:
				drawTexturedModalRect(this.guiLeft + 80, this.guiTop + 32, 194, 38, 16, 16); //orange button
				displayStartup();
			break;
			case 1:
				drawTexturedModalRect(this.guiLeft + 80, this.guiTop + 32, 210, 38, 16, 16); //green button
				drawPowerMeterDisplay(20 * this.turbinegas.instantPowerOutput);
			break;
			default:
			break;
		}
		
		drawTexturedModalRect(this.guiLeft + 36, this.guiTop + 97 - this.turbinegas.powerSliderPos, 178, 0, 16, 6); //power slider
		
		int power = (int) (this.turbinegas.power * 142 / TileEntityMachineTurbineGas.maxPower); //power storage
		drawTexturedModalRect(this.guiLeft + 26, this.guiTop + 109, 0, 223, power, 16);
		
		drawRPMGauge(this.turbinegas.rpm);
		drawThermometer(this.turbinegas.temp);
		
		drawInfoPanel(this.guiLeft - 16, this.guiTop + 34, 16, 16, 3); //info
		drawInfoPanel(this.guiLeft - 16, this.guiTop + 34 + 16, 16, 16, 2); //fuels
		if((this.turbinegas.tanks[0].getFill()) < 5000 || this.turbinegas.tanks[1].getFill() < 1000)
			drawInfoPanel(this.guiLeft - 16, this.guiTop + 34 + 32, 16, 16, 7);
		if(this.turbinegas.tanks[0].getFill() == 0 || this.turbinegas.tanks[1].getFill() == 0)
			drawInfoPanel(this.guiLeft - 16, this.guiTop + 34 + 32, 16, 16, 6);
		
		this.turbinegas.tanks[0].renderTank(this.guiLeft + 8, this.guiTop + 65, this.zLevel, 16, 48);
		this.turbinegas.tanks[1].renderTank(this.guiLeft + 8, this.guiTop + 103, this.zLevel, 16, 32);
		this.turbinegas.tanks[2].renderTank(this.guiLeft + 147, this.guiTop + 98, this.zLevel, 16, 36);
		this.turbinegas.tanks[3].renderTank(this.guiLeft + 147, this.guiTop + 58, this.zLevel, 16, 36);
	}
	
	int numberToDisplay = 0; //for startup
	int digitNumber = 0; 
	int exponent = 0;
	
	public void displayStartup() {
		
		@SuppressWarnings("unused")
		boolean displayOn = true;
		
		if(this.numberToDisplay < 888888 && this.turbinegas.counter < 60) { //48 frames needed to complete
			
			this.digitNumber++;
			if(this.digitNumber == 9) {
				this.digitNumber = 1;
				this.exponent++;
			}
			this.numberToDisplay += Math.pow(10, this.exponent);
		}
		
		if(this.turbinegas.counter > 50)
			this.numberToDisplay = 0;
		
		drawPowerMeterDisplay(this.numberToDisplay);
	}
	
	protected void drawPowerMeterDisplay(int number) { //display code
		
		int firstDigitX = 66;
		int firstDigitY = 62;
		
		int[] digit = new int[6];
		
		for(int i = 5; i >= 0; i--) { //creates an array of digits that represent the numbers
			
			digit[i] = number % 10;
			
			number = number / 10;
			
			drawTexturedModalRect(this.guiLeft + firstDigitX + i * 8, this.guiTop + 9 + firstDigitY, 194 + digit[i] * 5, 0, 5, 11);
		}
		
		int uselessZeros = 0;
		
		for(int i = 0; i < 5; i++) { //counts how much zeros there are before the number, to display 57 instead of 000057
			
			if(digit[i] == 0)
				uselessZeros++;
			else
				break;
		}
		
		for(int i = 0; i < uselessZeros; i++) { //turns off the useless zeros
			
			drawTexturedModalRect(this.guiLeft + firstDigitX + i * 8, this.guiTop + 9 + firstDigitY, 244, 0, 5, 11);
		}
	}
	
	protected void drawThermometer(int temp) {
		
		int xPos = this.guiLeft + 136;
		int yPos = this.guiTop + 28;
		
		int width = 2;
		int height = 64;
		
		int maxTemp = 800;
		
		double uMin = (176D / 256D);
		double uMax = (178D / 256D);
		double vMin = ((64D - 64 * temp / maxTemp) / 256D);
		double vMax = (64D / 256D);
		
		GL11.glEnable(GL11.GL_BLEND);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMachineTurbineGas.texture);
		
		Tessellator tessellator = Tessellator.instance;
		
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(xPos, yPos + height, this.zLevel, uMin, vMax);
		tessellator.addVertexWithUV(xPos + width, yPos + height, this.zLevel, uMax, vMax);
		tessellator.addVertexWithUV(xPos + width, yPos + 64 - (64 * temp / maxTemp), this.zLevel,uMax, vMin);
		tessellator.addVertexWithUV(xPos, yPos + 64 - (64 * temp / maxTemp), this.zLevel, uMin, vMin);
		tessellator.draw();

		GL11.glDisable(GL11.GL_BLEND);
	}
	
	protected void drawRPMGauge(int position) {
		
		int xPos = this.guiLeft + 64;
		int yPos = this.guiTop + 16;
		
		int squareSideLenght = 48;
		
		double uMin = (48D / 4848D) * position;
		double uMax = (48D / 4848D) * (position + 1);
		double vMin = 0D;
		double vMax = 1D;
		
		GL11.glEnable(GL11.GL_BLEND);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIMachineTurbineGas.gauge_tex); //long boi
		
		Tessellator tessellator = Tessellator.instance;
		
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(xPos, yPos + squareSideLenght, this.zLevel, uMin, vMax);
		tessellator.addVertexWithUV(xPos + squareSideLenght, yPos + squareSideLenght, this.zLevel, uMax, vMax);
		tessellator.addVertexWithUV(xPos + squareSideLenght, yPos, this.zLevel,uMax, vMin);
		tessellator.addVertexWithUV(xPos, yPos, this.zLevel, uMin, vMin);
		tessellator.draw();

		GL11.glDisable(GL11.GL_BLEND);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		//useless piece of shit, at least for now
		String name = this.turbinegas.hasCustomInventoryName() ? this.turbinegas.getInventoryName() : I18n.format(this.turbinegas.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 94, 4210752);
	}
}