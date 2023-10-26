package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.packet.ItemBobmazonPacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.util.ResourceLocation;

public class GUIScreenBobmazon extends GuiScreen {
	
    protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_bobmazon.png");
    protected int xSize = 176;
    protected int ySize = 229;
    protected int guiLeft;
    protected int guiTop;
    int currentPage = 0;
    List<Offer> offers = new ArrayList<>();
    List<FolderButton> buttons = new ArrayList<>();
    private final EntityPlayer player;
    
    public GUIScreenBobmazon(EntityPlayer player, List<Offer> offers) {
    	
    	this.player = player;

    	this.offers = offers;
    }
    
    int getPageCount() {
    	return (int)Math.ceil((this.offers.size() - 1) / 3);
    }
    
    @Override
	public void updateScreen() {
    	if(this.currentPage < 0)
    		this.currentPage = 0;
    	if(this.currentPage > getPageCount())
    		this.currentPage = getPageCount();
    	
    	if(this.player.getHeldItem() != null && this.player.getHeldItem().getItem() == ModItems.bobmazon_hidden && this.player.getDisplayName().equals("SolsticeUnlimitd"))
    		this.mc.thePlayer.closeScreen();
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

        updateButtons();
    }
    
    protected void updateButtons() {
        
        if(!this.buttons.isEmpty())
        	this.buttons.clear();
        
        for(int i = this.currentPage * 3; i < Math.min(this.currentPage * 3 + 3, this.offers.size()); i++) {
    		this.buttons.add(new FolderButton(this.guiLeft + 34, this.guiTop + 35 + (54 * (int)Math.floor(i)) - this.currentPage * 3 * 54, this.offers.get(i)));
        }

        if(this.currentPage != 0)
        	this.buttons.add(new FolderButton(this.guiLeft + 25 - 18, this.guiTop + 26 + (27 * 3), 1, "Previous"));
        if(this.currentPage != getPageCount())
        	this.buttons.add(new FolderButton(this.guiLeft + 25 + (27 * 4) + 18, this.guiTop + 26 + (27 * 3), 2, "Next"));
    }

    @Override
	protected void mouseClicked(int i, int j, int k) {
    	try {
    		for(FolderButton b : this.buttons)
    			if(b.isMouseOnButton(i, j))
    				b.executeAction();
    	} catch (Exception ex) {
    		updateButtons();
    	}
    }
	
	protected void drawGuiContainerForegroundLayer(int i, int j) {

		this.fontRendererObj.drawString(I18n.format((this.currentPage + 1) + "/" + (getPageCount() + 1)), 
				this.guiLeft + this.xSize / 2 - this.fontRendererObj.getStringWidth(I18n.format((this.currentPage + 1) + "/" + (getPageCount() + 1))) / 2, this.guiTop + 205, 4210752);
		
		for(FolderButton b : this.buttons)
			if(b.isMouseOnButton(i, j))
				b.drawString(i, j);
	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GUIScreenBobmazon.texture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		for(FolderButton b : this.buttons)
			b.drawButton(b.isMouseOnButton(i, j));
		for(FolderButton b : this.buttons)
			b.drawIcon(b.isMouseOnButton(i, j));
        
        for(int d = this.currentPage * 3; d < Math.min(this.currentPage * 3 + 3, this.offers.size()); d++) {
    		this.offers.get(d).drawRequirement(this, this.guiLeft + 34, this.guiTop + 53 + (54 * (int)Math.floor(d)) - this.currentPage * 3 * 54);
        }
	}
	
    @Override
	protected void keyTyped(char p_73869_1_, int p_73869_2_)
    {
        if (p_73869_2_ == 1 || p_73869_2_ == this.mc.gameSettings.keyBindInventory.getKeyCode())
        {
            this.mc.thePlayer.closeScreen();
        }
        
    }
	
	class FolderButton {
		
		int xPos;
		int yPos;
		//0: regular, 1: prev, 2: next
		int type;
		String info;
		Offer offer;
		
		public FolderButton(int x, int y, int t, String i) {
			this.xPos = x;
			this.yPos = y;
			this.type = t;
			this.info = i;
		}
		
		public FolderButton(int x, int y, Offer offer) {
			this.xPos = x;
			this.yPos = y;
			this.type = 0;
			this.offer = offer;
		}
		
		public void updateButton(int mouseX, int mouseY) {
		}
		
		public boolean isMouseOnButton(int mouseX, int mouseY) {
			return this.xPos <= mouseX && this.xPos + 18 > mouseX && this.yPos < mouseY && this.yPos + 18 >= mouseY;
		}
		
		public void drawButton(boolean b) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(GUIScreenBobmazon.texture);
			drawTexturedModalRect(this.xPos, this.yPos, b ? 176 + 18 : 176, this.type == 1 ? 18 : (this.type == 2 ? 36 : 0), 18, 18);
		}
		
		public void drawIcon(boolean b) {
			try {
		        GL11.glDisable(GL11.GL_LIGHTING);
				if(this.offer != null) {
					GuiScreen.itemRender.renderItemAndEffectIntoGUI(GUIScreenBobmazon.this.fontRendererObj, GUIScreenBobmazon.this.mc.getTextureManager(), this.offer.offer, this.xPos + 1, this.yPos + 1);
				}
		        GL11.glEnable(GL11.GL_LIGHTING);
			} catch(Exception x) { }
		}
		
		public void drawString(int x, int y) {
			if(this.info == null || this.info.isEmpty())
				return;
			
			func_146283_a(Arrays.asList(new String[] { this.info }), x, y);
		}
		
		public void executeAction() {
			GUIScreenBobmazon.this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			if(this.type == 0) {
				PacketDispatcher.wrapper.sendToServer(new ItemBobmazonPacket(GUIScreenBobmazon.this.player, this.offer));
			} else if(this.type == 1) {
				if(GUIScreenBobmazon.this.currentPage > 0)
					GUIScreenBobmazon.this.currentPage--;
				updateButtons();
			} else if(this.type == 2) {
				if(GUIScreenBobmazon.this.currentPage < getPageCount())
					GUIScreenBobmazon.this.currentPage++;
				updateButtons();
			}
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	public static class Offer {
		
		public ItemStack offer;
		public Requirement requirement;
		public int cost;
		public int rating;
		public String comment;
		public String author;
		
		public Offer(ItemStack offer, Requirement requirement, int cost, int rating, String comment, String author) {
			this.offer = offer;
			this.requirement = requirement;
			this.cost = cost;
			this.rating = rating * 4 - 1;
			this.comment = comment;
			this.author = author;
		}
		
		public Offer(ItemStack offer, Requirement requirement, int cost) {
			this.offer = offer;
			this.requirement = requirement;
			this.cost = cost;
			this.rating = 0;
			this.comment = "No Ratings";
			this.author = "";
		}
		
		public void drawRequirement(GUIScreenBobmazon gui, int x, int y) {
			try {

				RenderHelper.enableGUIStandardItemLighting();
				GL11.glColor3f(1F, 1F, 1F);
				Minecraft.getMinecraft().getTextureManager().bindTexture(GUIScreenBobmazon.texture);
				gui.drawTexturedModalRect(x + 19, y - 4, 176, 62, 39, 8);
				gui.drawTexturedModalRect(x + 19, y - 4, 176, 54, this.rating, 8);
				
				String count = "";
				if(this.offer.stackSize > 1)
					count = " x" + this.offer.stackSize;

				GL11.glPushMatrix();
				
				float scale = 0.65F;
				GL11.glScalef(scale, scale, scale);
				gui.fontRendererObj.drawString(I18n.format(this.offer.getDisplayName()) + count, (int)((x + 20) / scale), (int)((y - 12) / scale), 4210752);
				
				GL11.glPopMatrix();
				
				String price = this.cost + " Cap";
				if(this.cost != 1)
					price += "s";

				gui.fontRendererObj.drawString(price, x + 62, y - 3, 4210752);
				
				GL11.glPushMatrix();
					
					GL11.glScalef(0.5F, 0.5F, 0.5F);
					
					if(!this.author.isEmpty())
						gui.fontRendererObj.drawString("- " + this.author, (x + 20) * 2, (y + 18) * 2, 0x222222);
					gui.fontRendererObj.drawString(this.comment, (x + 20) * 2, (y + 8) * 2, 0x222222);
					
				GL11.glPopMatrix();
				
		        GL11.glDisable(GL11.GL_LIGHTING);
				if(this.offer != null) {
					GuiScreen.itemRender.renderItemAndEffectIntoGUI(gui.fontRendererObj, gui.mc.getTextureManager(), this.requirement.achievement.theItemStack, x + 1, y + 1);
				}
		        GL11.glEnable(GL11.GL_LIGHTING);
		        
			} catch(Exception ex) { }
		}
		
	}
	
	public enum Requirement {

		STEEL(MainRegistry.achBlastFurnace),
		ASSEMBLY(MainRegistry.achAssembly),
		CHEMICS(MainRegistry.achChemplant),
		OIL(MainRegistry.achDesh),
		NUCLEAR(MainRegistry.achTechnetium),
		HIDDEN(MainRegistry.bobHidden);
		
		private Requirement(Achievement achievement) {
			this.achievement = achievement;
		}
		
		public boolean fullfills(EntityPlayerMP player) {
			
			return player.func_147099_x().hasAchievementUnlocked(this.achievement);
		}
		
		public Achievement achievement;
	}

}
