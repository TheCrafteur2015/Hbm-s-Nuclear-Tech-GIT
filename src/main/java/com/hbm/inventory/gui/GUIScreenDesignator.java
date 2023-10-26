package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.packet.ItemDesignatorPacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GUIScreenDesignator extends GuiScreen {
	
    protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_designator.png");
    protected int xSize = 176;
    protected int ySize = 178;
    protected int guiLeft;
    protected int guiTop;
    int shownX;
    int shownZ;
    int currentPage = 0;
    List<ItemStack> stacks = new ArrayList<>();
    List<FolderButton> buttons = new ArrayList<>();
    private final EntityPlayer player;
    
    public GUIScreenDesignator(EntityPlayer player) {
    	
    	this.player = player;
    }
    
    @Override
	public void drawScreen(int mouseX, int mouseY, float f)
    {
        drawDefaultBackground();
        drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
        GL11.glDisable(GL11.GL_LIGHTING);
        drawGuiContainerForegroundLayer(mouseX, mouseY);
        GL11.glEnable(GL11.GL_LIGHTING);
    }
    
    @Override
	public void initGui()
    {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;

		this.shownX = 0;
		this.shownZ = 0;
		ItemStack stack = this.player.getHeldItem();
		
		if(stack != null && stack.getItem() == ModItems.designator_manual && stack.hasTagCompound()) {
			this.shownX = stack.stackTagCompound.getInteger("xCoord");
			this.shownZ = stack.stackTagCompound.getInteger("zCoord");
		}
		
		updateButtons();
    }
    
    private void updateButtons() {
    	this.buttons.clear();

    	this.buttons.add(new FolderButton(this.guiLeft + 25,	this.guiTop + 26, 0, 0, 0, 1, null));
    	this.buttons.add(new FolderButton(this.guiLeft + 52,	this.guiTop + 26, 1, 0, 0, 5, null));
    	this.buttons.add(new FolderButton(this.guiLeft + 79,	this.guiTop + 26, 2, 0, 0, 10, null));
    	this.buttons.add(new FolderButton(this.guiLeft + 106,	this.guiTop + 26, 3, 0, 0, 50, null));
    	this.buttons.add(new FolderButton(this.guiLeft + 133,	this.guiTop + 26, 4, 0, 0, 100, null));

    	this.buttons.add(new FolderButton(this.guiLeft + 25,	this.guiTop + 62, 5, 1, 0, 1, null));
    	this.buttons.add(new FolderButton(this.guiLeft + 52,	this.guiTop + 62, 6, 1, 0, 5, null));
    	this.buttons.add(new FolderButton(this.guiLeft + 79,	this.guiTop + 62, 7, 1, 0, 10, null));
    	this.buttons.add(new FolderButton(this.guiLeft + 106,	this.guiTop + 62, 8, 1, 0, 50, null));
    	this.buttons.add(new FolderButton(this.guiLeft + 133,	this.guiTop + 62, 9, 1, 0, 100, null));

    	this.buttons.add(new FolderButton(this.guiLeft + 133,	this.guiTop + 44, 10, 2, 0, 0, "Set coord to current X position..."));

    	this.buttons.add(new FolderButton(this.guiLeft + 25,	this.guiTop + 26 + 72, 0, 0, 1, 1, null));
    	this.buttons.add(new FolderButton(this.guiLeft + 52,	this.guiTop + 26 + 72, 1, 0, 1, 5, null));
    	this.buttons.add(new FolderButton(this.guiLeft + 79,	this.guiTop + 26 + 72, 2, 0, 1, 10, null));
    	this.buttons.add(new FolderButton(this.guiLeft + 106,	this.guiTop + 26 + 72, 3, 0, 1, 50, null));
    	this.buttons.add(new FolderButton(this.guiLeft + 133,	this.guiTop + 26 + 72, 4, 0, 1, 100, null));

    	this.buttons.add(new FolderButton(this.guiLeft + 25,	this.guiTop + 62 + 72, 5, 1, 1, 1, null));
    	this.buttons.add(new FolderButton(this.guiLeft + 52,	this.guiTop + 62 + 72, 6, 1, 1, 5, null));
    	this.buttons.add(new FolderButton(this.guiLeft + 79,	this.guiTop + 62 + 72, 7, 1, 1, 10, null));
    	this.buttons.add(new FolderButton(this.guiLeft + 106,	this.guiTop + 62 + 72, 8, 1, 1, 50, null));
    	this.buttons.add(new FolderButton(this.guiLeft + 133,	this.guiTop + 62 + 72, 9, 1, 1, 100, null));

    	this.buttons.add(new FolderButton(this.guiLeft + 133,	this.guiTop + 44 + 72, 10, 2, 1, 0, "Set coord to current Z position..."));
    }

    @Override
	protected void mouseClicked(int i, int j, int k) {
    	try {
    		for(FolderButton b : this.buttons)
    			if(b.isMouseOnButton(i, j))
    				b.executeAction();
    	} catch (Exception ex) { }
    }
	
	protected void drawGuiContainerForegroundLayer(int i, int j) {

		//this.fontRendererObj.drawString(I18n.format((currentPage + 1) + "/" + (getPageCount() + 1)), 
		//		guiLeft + this.xSize / 2 - this.fontRendererObj.getStringWidth(I18n.format((currentPage + 1) + "/" + (getPageCount() + 1))) / 2, guiTop + 10, 4210752);
		
		for(FolderButton b : this.buttons)
			if(b.isMouseOnButton(i, j))
				b.drawString(i, j);

		String x = String.valueOf(this.shownX);
		String z = String.valueOf(this.shownZ);
		this.fontRendererObj.drawString("X: " + x, 
				this.guiLeft + this.xSize / 2 - this.fontRendererObj.getStringWidth("X: " + x) / 2, this.guiTop + 50, 4210752);
		this.fontRendererObj.drawString("Z: " + z, 
				this.guiLeft + this.xSize / 2 - this.fontRendererObj.getStringWidth("Z: " + z) / 2, this.guiTop + 50 + 18 * 4, 4210752);
	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIScreenDesignator.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		for(FolderButton b : this.buttons)
			b.drawButton(b.isMouseOnButton(i, j));
	}
	
    @Override
	protected void keyTyped(char p_73869_1_, int p_73869_2_)
    {
        if (p_73869_2_ == 1 || p_73869_2_ == this.mc.gameSettings.keyBindInventory.getKeyCode())
        {
            this.mc.thePlayer.closeScreen();
        }
        
    }
    
    @Override
	public void updateScreen() {
    	if(this.player.getHeldItem() == null || this.player.getHeldItem().getItem() != ModItems.designator_manual)
    		this.player.closeScreen();
    }
	
	class FolderButton {
		
		int xPos;
		int yPos;
		int type;
		int operator;
		int value;
		int reference;
		String info;
		
		public FolderButton(int x, int y, int t, int o, int r, int v, String i) {
			this.xPos = x;
			this.yPos = y;
			this.type = t;
			this.operator = o;
			this.value = v;
			this.reference = r;
			this.info = i;
		}
		
		public void updateButton(int mouseX, int mouseY) {
		}
		
		public boolean isMouseOnButton(int mouseX, int mouseY) {
			return this.xPos <= mouseX && this.xPos + 18 > mouseX && this.yPos < mouseY && this.yPos + 18 >= mouseY;
		}
		
		public void drawButton(boolean b) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(GUIScreenDesignator.texture);
			drawTexturedModalRect(this.xPos, this.yPos, b ? 176 + 18 : 176, this.type * 18, 18, 18);
		}
		
		public void drawString(int x, int y) {
			if(this.info == null || this.info.isEmpty())
				return;
			
			String s = this.info;

			func_146283_a(Arrays.asList(new String[] { s }), x, y);
		}
		
		public void executeAction() {
			GUIScreenDesignator.this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new ItemDesignatorPacket(this.operator, this.value, this.reference));
			
			int result = 0;

			if(this.operator == 0)
				result += this.value;
			if(this.operator == 1)
				result -= this.value;
			if(this.operator == 2) {
				if(this.reference == 0)
					GUIScreenDesignator.this.shownX = (int)Math.round(GUIScreenDesignator.this.player.posX);
				else
					GUIScreenDesignator.this.shownZ = (int)Math.round(GUIScreenDesignator.this.player.posZ);
				return;
			}
			
			if(this.reference == 0)
				GUIScreenDesignator.this.shownX += result;
			else
				GUIScreenDesignator.this.shownZ += result;
		}
		
	}

}
