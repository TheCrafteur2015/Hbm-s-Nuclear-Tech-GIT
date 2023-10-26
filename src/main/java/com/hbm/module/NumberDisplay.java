package com.hbm.module;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

import javax.annotation.Nonnegative;

import org.lwjgl.opengl.GL11;

import com.google.common.annotations.Beta;
import com.hbm.inventory.gui.GuiInfoContainer;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;


/**
 * Seven segment style displays for GUIs, tried to be as adaptable as possible. Still has some bugs that need to be ironed out but it works for the most part.
 * @author UFFR
 *
 */
public class NumberDisplay {
	
	/*
	 * Moved the entire class into its own file to keep GuiInfoContainer tidy.
	 * This new class needs a reference to the GUI because of this so it can render things using the GUI's utils.
	 */
	protected GuiInfoContainer gui;
	
	/** The display's X coordinate **/
	private int displayX;
	/** The display's Y coordinate **/
	private int displayY;
	/** The display's color, in hexadecimal **/
	private int color;
	/** The amount of padding between digits, default 3 **/
	@Nonnegative
	private byte padding = 3;
	/** Does it blink or not, default false, not yet used **/
	private boolean blink = false;
	/** Max number the display can handle **/
	private float maxNum;
	/** Min number the display can handle **/
	private float minNum;
	private boolean customBounds = false;
	// Should it be a decimal number?
	private boolean isFloat = false;
	// How many trailing zeros?
	private byte floatPad = 1;
	/** Does it pad out with zeros **/
	private boolean pads = false;
	/** Max number of digits the display has, default 3 **/
	@Nonnegative
	private byte digitLength = 3;
	private Number numIn = 0;
	private char[] toDisp = {'0', '0', '0'};
	@Nonnegative
	private short dispOffset = 0;
	/** Length and thickness of segments. **/
	private int verticalLength = 5;
	private int horizontalLength = 4;
	private int thickness = 1;
	/**
	 * Construct a new number display
	 * @param dX X coordinate of the display
	 * @param dY Y coordinate of the display
	 * @param c Enum Color to use, invalid enums will default to yellow
	 */
	public NumberDisplay(GuiInfoContainer gui, int x, int y, EnumChatFormatting c)
	{
		this(gui, x, y);
		setColor(enumToColor(c));
	}
	/**
	 * Construct a new number display
	 * @param x X coordinate of the display
	 * @param y Y coordinate of the display
	 * @param color Color to use, valid hexadecimal value required
	 */
	public NumberDisplay(GuiInfoContainer gui, int x, int y, int color)
	{
		this(gui, x, y);
		setColor(color);
	}
	/**
	 * Construct a new number display, color is yellow
	 * @param x X coordinate of the display
	 * @param y Y coordinate of the display
	 */
	public NumberDisplay(GuiInfoContainer gui, int x, int y)
	{
		this.displayX = x;
		this.displayY = y;
		setColor(0xFFFF55);
		this.gui = gui;
	}
	
	/**
	 * Returns a hexadecimal from EnumChatFormatting
	 * @param c Color to use
	 * @return
	 */
	private int enumToColor(EnumChatFormatting c) {
		if(c.isColor()) {
			switch(c) {
			case AQUA:
				return 0x55FFFF;
			case BLACK:
				return 0x000000;
			case BLUE:
				return 0x5555FF;
			case DARK_AQUA:
				return 0x00AAAA;
			case DARK_BLUE:
				return 0x0000AA;
			case DARK_GRAY:
				return 0x555555;
			case DARK_GREEN:
				return 0x00AA00;
			case DARK_PURPLE:
				return 0xAA00AA;
			case DARK_RED:
				return 0xAA0000;
			case GOLD:
				return 0xFFAA00;
			case GRAY:
				return 0xAAAAAA;
			case GREEN:
				return 0x55FF55;
			case LIGHT_PURPLE:
				return 0xFF55FF;
			case RED:
				return 0xFF5555;
			case WHITE:
				return 0xFFFFFF;
			case YELLOW:
				return 0xFFFF55;
			default:
			}
		}
		
		return 0xFFFF55;
	}
	/**
	 * Sets color 
	 * @param color - The color in hexadecimal
	 **/
	public void setColor(int color) {
		this.color = color;
	}
	/**
	 * Draw custom number
	 * @param num - The char array that has the number
	 */
	public void drawNumber(char[] num)
	{
		if (this.blink && !BobMathUtil.getBlink())
			return;
			
		short gap = (short) (this.digitLength - num.length);
		for (int i = 0; i < num.length; i++)
		{
			if (num[i] == '.')
				gap--;
			this.dispOffset = (short) ((this.padding + this.horizontalLength + 2 * this.thickness) * (i + gap));
			drawChar(num[i]);
		}
		if (this.pads)
			padOut(gap);
	}
	/** Draw the previously provided number **/
	public void drawNumber()
	{
		if (this.isFloat)
			formatForFloat();
		drawNumber(this.toDisp);
	}
	public void drawNumber(Number num)
	{
		setNumber(num);
		drawNumber();
	}
	private void padOut(short gap)
	{
		if (gap == 0)
			return;
		for (int i = 0; i < gap; i++)
		{
			this.dispOffset = (short) ((this.padding + this.horizontalLength + 2 * this.thickness) * i);
			drawChar('0');
		}
	}
	
	/** Draw a single character (requires dispOffset to be set) **/
	public void drawChar(char num)
	{
		switch (num)
		{
		case '1':
			drawVertical(1, 0);
			drawVertical(1, 1);
			break;
		case '2':
			drawHorizontal(0);
			drawVertical(1, 0);
			drawHorizontal(1);
			drawVertical(0, 1);
			drawHorizontal(2);
			break;
		case '3':
			drawHorizontal(0);
			drawHorizontal(1);
			drawHorizontal(2);
			drawVertical(1, 0);
			drawVertical(1, 1);
			break;
		case '4':
			drawVertical(0, 0);
			drawVertical(1, 0);
			drawVertical(1, 1);
			drawHorizontal(1);
			break;
		case '5':
			drawHorizontal(0);
			drawHorizontal(1);
			drawHorizontal(2);
			drawVertical(0, 0);
			drawVertical(1, 1);
			break;
		case '6':
			drawHorizontal(0);
			drawHorizontal(1);
			drawHorizontal(2);
			drawVertical(0, 0);
			drawVertical(0, 1);
			drawVertical(1, 1);
			break;
		case '7':
			drawHorizontal(0);
			drawVertical(1, 0);
			drawVertical(1, 1);
			break;
		case '8':
			drawHorizontal(0);
			drawHorizontal(1);
			drawHorizontal(2);
			drawVertical(0, 0);
			drawVertical(1, 0);
			drawVertical(0, 1);
			drawVertical(1, 1);
			break;
		case '9':
			drawHorizontal(0);
			drawHorizontal(1);
			drawHorizontal(2);
			drawVertical(0, 0);
			drawVertical(1, 0);
			drawVertical(1, 1);
			break;
		case '0':
			drawHorizontal(0);
			drawHorizontal(2);
			drawVertical(0, 0);
			drawVertical(0, 1);
			drawVertical(1, 0);
			drawVertical(1, 1);
			break;
		case '-':
			drawHorizontal(1);
			break;
		case '.':
			drawPeriod();
			break;
		case 'E':
		default:
			drawHorizontal(0);
			drawHorizontal(1);
			drawHorizontal(2);
			drawVertical(0, 0);
			drawVertical(0, 1);
			break;
		}
	}
	
	private void drawHorizontal(int pos)
	{
		byte offset = (byte) (pos * (this.verticalLength + this.thickness));
		renderSegment(this.gui.getGuiLeft() + this.displayX + this.dispOffset + this.thickness, this.gui.getGuiTop() + this.displayY + offset, this.horizontalLength, this.thickness);
	}
	
	private void drawPeriod()
	{
		renderSegment(this.gui.getGuiLeft() + this.displayX + this.dispOffset + this.padding - (int) Math.ceil(this.padding / 2) + (this.horizontalLength + this.thickness), 
				this.gui.getGuiLeft() + this.displayY + 2 * (this.verticalLength + this.thickness), this.thickness, this.thickness);
	}
	
	private void drawVertical(int posX, int posY)
	{
		byte offsetX = (byte) (posX * (this.horizontalLength + this.thickness));
		byte offsetY = (byte) (posY * (this.verticalLength + this.thickness));
		renderSegment(this.gui.getGuiLeft() + this.displayX + offsetX + this.dispOffset, this.gui.getGuiTop() + this.displayY + offsetY + this.thickness, this.thickness, this.verticalLength);
	}
	/**
	 * drawTexturedModalRect() for cool kids
	 * @param renX X coordinate to render the part
	 * @param renY Y coordinate to render the part
	 * @param width Relevant for horizontals
	 * @param height Relevant for verticals
	 */
	private void renderSegment(int renX, int renY, int width, int height)
	{
		final Tessellator tess = Tessellator.instance;
		final float z = this.gui.getZLevel();
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		tess.startDrawingQuads();
		tess.setColorOpaque_I(this.color);
		tess.addVertex(renX, renY + height, z);
		tess.addVertex(renX + width, renY + height, z);
		tess.addVertex(renX + width, renY + 0, z);
		tess.addVertex(renX, renY, z);
		tess.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public void setNumber(Number num)
	{
		this.numIn = num;
		if (this.customBounds)
			this.numIn = MathHelper.clamp_double(num.doubleValue(), this.minNum, this.maxNum);
		if (this.isFloat)
			formatForFloat();
		else
		{
			this.toDisp = new Long(Math.round(this.numIn.doubleValue())).toString().toCharArray();
			this.toDisp = truncOrExpand();
		}
	}
	/** Get the set number **/
	public Number getNumber()
	{
		return this.numIn;
	}
	/** Get the char array for display **/
	public char[] getDispNumber()
	{
		return this.toDisp.clone();
	}
	/** Make the display blink **/
	public NumberDisplay setBlinks(boolean doesBlink)
	{
		this.blink = doesBlink;
		return this;
	}
	/** Padding between digits, default 3 **/
	public NumberDisplay setPadding(@Nonnegative int p)
	{
		this.padding = (byte) p;
		return this;
	}
	/** Max number of digits **/
	public NumberDisplay setDigitLength(@Nonnegative int l)
	{
		this.digitLength = (byte) l;
		this.toDisp = truncOrExpand();
		return this;
	}
	/** Set sizes and thickness of horizontal and vertical segments. **/
	public NumberDisplay setSegmentSize(int vertical, int horizontal, int thickness)
	{
		this.verticalLength = vertical;
		this.horizontalLength = horizontal;
		this.thickness = thickness;
		return this;
	}
	/** Set custom number bounds **/
	public NumberDisplay setMaxMin(float max, float min)
	{
		if (min > max)
			throw new IllegalArgumentException("Minimum value is larger than maximum value!");
		this.maxNum = max;
		this.minNum = min;
		this.customBounds = true;
		return this;
	}
	/** Pad out the left side of the number with zeros **/
	public NumberDisplay setPadNumber()
	{
		this.pads = true;
		return this;
	}
	/** Set the number to be a decimal, default zero trailing is 1 **/
	public NumberDisplay setFloat()
	{
		return setFloat(1);
	}
	/** Set the number to be a decimal with specified zero trailing **/
	public NumberDisplay setFloat(@Nonnegative int pad)
	{
		this.floatPad = (byte) pad;
		this.isFloat = true;
		
		formatForFloat();
		
		return this;
	}
	private void formatForFloat()
	{
		BigDecimal bd = new BigDecimal(this.numIn.toString());
		bd = bd.setScale(this.floatPad, RoundingMode.HALF_UP);
		
//		char[] proc = new Double(bd.doubleValue()).toString().toCharArray();
		char[] proc = bd.toString().toCharArray();
		proc = Double.valueOf(BobMathUtil.roundDecimal(this.numIn.doubleValue(), this.floatPad)).toString().toCharArray();
		
		if (proc.length == this.digitLength)
			this.toDisp = proc;
		else
			this.toDisp = truncOrExpand();
	}
	@Beta
	private char[] truncOrExpand()
	{
		if (this.isFloat)
		{
			char[] out = Arrays.copyOf(this.toDisp, this.digitLength);
			for (int i = 0; i < this.digitLength; i++)
				if (out[i] == '\u0000')
					out[i] = '0';
			return out.clone();
		}
		return this.toDisp;
	}
}
