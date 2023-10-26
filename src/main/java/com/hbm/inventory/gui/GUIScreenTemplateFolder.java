package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.AssemblerRecipes;
import com.hbm.inventory.recipes.ChemplantRecipes;
import com.hbm.inventory.recipes.ChemplantRecipes.ChemRecipe;
import com.hbm.inventory.recipes.CrucibleRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemAssemblyTemplate;
import com.hbm.items.machine.ItemCassette;
import com.hbm.items.machine.ItemStamp;
import com.hbm.items.machine.ItemStamp.StampType;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.packet.ItemFolderPacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GUIScreenTemplateFolder extends GuiScreen {

	protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_planner.png");
	protected static final ResourceLocation texture_journal = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_planner_journal.png");
	private boolean isJournal = false;
	protected int xSize = 176;
	protected int ySize = 229;
	protected int guiLeft;
	protected int guiTop;
	int currentPage = 0;
	List<ItemStack> allStacks = new ArrayList<>();
	List<ItemStack> stacks = new ArrayList<>();
	List<FolderButton> buttons = new ArrayList<>();
	private GuiTextField search;

	public GUIScreenTemplateFolder(EntityPlayer player) {

		if(player.getHeldItem() == null)
			return;

		if(player.getHeldItem().getItem() == ModItems.template_folder) {

			// Stamps
			for(ItemStack i : ItemStamp.stamps.get(StampType.PLATE))
				this.allStacks.add(i.copy());
			for(ItemStack i : ItemStamp.stamps.get(StampType.WIRE))
				this.allStacks.add(i.copy());
			for(ItemStack i : ItemStamp.stamps.get(StampType.CIRCUIT))
				this.allStacks.add(i.copy());
			
			// Tracks
			for(int i = 1; i < ItemCassette.TrackType.values().length; i++) {
				this.allStacks.add(new ItemStack(ModItems.siren_track, 1, i));
			}
			// Fluid IDs
			FluidType[] fluids = Fluids.getInNiceOrder();
			for(int i = 1; i < fluids.length; i++) {
				if(!fluids[i].hasNoID()) {
					this.allStacks.add(new ItemStack(ModItems.fluid_identifier, 1, fluids[i].getID()));
				}
			}
			// Assembly Templates
			for(int i = 0; i < AssemblerRecipes.recipeList.size(); i++) {

				ComparableStack comp = AssemblerRecipes.recipeList.get(i);
				if(AssemblerRecipes.hidden.get(comp) == null) {
					this.allStacks.add(ItemAssemblyTemplate.writeType(new ItemStack(ModItems.assembly_template, 1, i), comp));
				}
			}
			// Chemistry Templates
			for(int i = 0; i < ChemplantRecipes.recipes.size(); i++) {
				ChemRecipe chem = ChemplantRecipes.recipes.get(i);
				this.allStacks.add(new ItemStack(ModItems.chemistry_template, 1, chem.getId()));
			}
			
			// Crucible Templates
			for(int i = 0; i < CrucibleRecipes.recipes.size(); i++) {
				this.allStacks.add(new ItemStack(ModItems.crucible_template, 1, CrucibleRecipes.recipes.get(i).getId()));
			}
		} else {

			for(int i = 0; i < AssemblerRecipes.recipeList.size(); i++) {
				
				if(AssemblerRecipes.hidden.get(AssemblerRecipes.recipeList.get(i)) != null &&
						AssemblerRecipes.hidden.get(AssemblerRecipes.recipeList.get(i)).contains(player.getHeldItem().getItem())) {
					
					ComparableStack comp = AssemblerRecipes.recipeList.get(i);
					this.allStacks.add(ItemAssemblyTemplate.writeType(new ItemStack(ModItems.assembly_template, 1, i), comp));
				}
			}
			
			this.isJournal = true;
		}
		
		search(null);
	}
	
	private void search(String sub) {
		
		this.stacks.clear();
		
		this.currentPage = 0;
		
		if(sub == null || sub.isEmpty()) {
			this.stacks.addAll(this.allStacks);
			updateButtons();
			return;
		}
		
		sub = sub.toLowerCase(Locale.US);
		
		outer:
		for(ItemStack stack : this.allStacks) {
			
			for(Object o : stack.getTooltip(MainRegistry.proxy.me(), true)) {
				
				if(o instanceof String) {
					String text = (String) o;
					
					if(text.toLowerCase(Locale.US).contains(sub)) {
						this.stacks.add(stack);
						continue outer;
					}
				}
			}
			
			if(stack.getItem() == ModItems.fluid_identifier) {
				FluidType fluid = Fluids.fromID(stack.getItemDamage());
				
				if(fluid.getLocalizedName().contains(sub)) {
					this.stacks.add(stack);
				}
			}
		}
		
		updateButtons();
	}

	int getPageCount() {
		return (int) Math.ceil((this.stacks.size() - 1) / (5 * 7));
	}

	@Override
	public void updateScreen() {
		if(this.currentPage < 0)
			this.currentPage = 0;
		if(this.currentPage > getPageCount())
			this.currentPage = getPageCount();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		drawDefaultBackground();
		drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		GL11.glDisable(GL11.GL_LIGHTING);
		drawGuiContainerForegroundLayer(mouseX, mouseY);
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	@Override
	public void initGui() {
		super.initGui();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;

		updateButtons();

		Keyboard.enableRepeatEvents(true);
		this.search = new GuiTextField(this.fontRendererObj, this.guiLeft + 61, this.guiTop + 213, 48, 12);
		this.search.setTextColor(0xffffff);
		this.search.setDisabledTextColour(0xffffff);
		this.search.setEnableBackgroundDrawing(false);
		this.search.setMaxStringLength(100);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	protected void updateButtons() {

		if(!this.buttons.isEmpty())
			this.buttons.clear();

		for(int i = this.currentPage * 35; i < Math.min(this.currentPage * 35 + 35, this.stacks.size()); i++) {
			this.buttons.add(new FolderButton(this.guiLeft + 25 + (27 * (i % 5)), this.guiTop + 26 + (27 * (int) Math.floor((i / 5D))) - this.currentPage * 27 * 7, this.stacks.get(i)));
		}

		if(this.currentPage != 0)
			this.buttons.add(new FolderButton(this.guiLeft + 25 - 18, this.guiTop + 26 + (27 * 3), 1, "Previous"));
		if(this.currentPage != getPageCount())
			this.buttons.add(new FolderButton(this.guiLeft + 25 + (27 * 4) + 18, this.guiTop + 26 + (27 * 3), 2, "Next"));
	}

	@Override
	protected void mouseClicked(int i, int j, int k) {
		
		if(i >= this.guiLeft + 45 && i < this.guiLeft + 117 && j >= this.guiTop + 211 && j < this.guiTop + 223) {
			this.search.setFocused(true);
		} else  {
			this.search.setFocused(false);
		}

		try {
			for(FolderButton b : this.buttons)
				if(b.isMouseOnButton(i, j))
					b.executeAction();
		} catch(Exception ex) {
			updateButtons();
		}
	}

	protected void drawGuiContainerForegroundLayer(int i, int j) {

		this.fontRendererObj.drawString(I18n.format((this.currentPage + 1) + "/" + (getPageCount() + 1)), this.guiLeft + this.xSize / 2 - this.fontRendererObj.getStringWidth(I18n.format((this.currentPage + 1) + "/" + (getPageCount() + 1))) / 2, this.guiTop + 10, this.isJournal ? 4210752 : 0xffffff);

		for(FolderButton b : this.buttons)
			if(b.isMouseOnButton(i, j))
				b.drawString(i, j);
	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		if(!this.isJournal)
			Minecraft.getMinecraft().getTextureManager().bindTexture(GUIScreenTemplateFolder.texture);
		else
			Minecraft.getMinecraft().getTextureManager().bindTexture(GUIScreenTemplateFolder.texture_journal);
		
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		if(this.search.isFocused())
			drawTexturedModalRect(this.guiLeft + 45, this.guiTop + 211, 176, 54, 72, 12);

		for(FolderButton b : this.buttons)
			b.drawButton(b.isMouseOnButton(i, j));
		for(FolderButton b : this.buttons)
			b.drawIcon(b.isMouseOnButton(i, j));
		
		this.search.drawTextBox();
	}

	@Override
	protected void keyTyped(char p_73869_1_, int p_73869_2_) {
		
		if (this.search.textboxKeyTyped(p_73869_1_, p_73869_2_)) {
			search(this.search.getText());
			return;
		}
		
		if(p_73869_2_ == 1 || p_73869_2_ == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			this.mc.thePlayer.closeScreen();
		}

	}

	class FolderButton {

		int xPos;
		int yPos;
		// 0: regular, 1: prev, 2: next
		int type;
		String info;
		ItemStack stack;

		public FolderButton(int x, int y, int t, String i) {
			this.xPos = x;
			this.yPos = y;
			this.type = t;
			this.info = i;
		}

		public FolderButton(int x, int y, ItemStack stack) {
			this.xPos = x;
			this.yPos = y;
			this.type = 0;
			this.info = stack.getDisplayName();
			this.stack = stack.copy();
		}

		public void updateButton(int mouseX, int mouseY) {
		}

		public boolean isMouseOnButton(int mouseX, int mouseY) {
			return this.xPos <= mouseX && this.xPos + 18 > mouseX && this.yPos < mouseY && this.yPos + 18 >= mouseY;
		}

		public void drawButton(boolean b) {
			
			if(!GUIScreenTemplateFolder.this.isJournal)
				Minecraft.getMinecraft().getTextureManager().bindTexture(GUIScreenTemplateFolder.texture);
			else
				Minecraft.getMinecraft().getTextureManager().bindTexture(GUIScreenTemplateFolder.texture_journal);
			
			drawTexturedModalRect(this.xPos, this.yPos, b ? 176 + 18 : 176, this.type == 1 ? 18 : (this.type == 2 ? 36 : 0), 18, 18);
		}

		public void drawIcon(boolean b) {
			try {
				RenderHelper.enableGUIStandardItemLighting();
				GL11.glDisable(GL11.GL_LIGHTING);
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) 240 / 1.0F, (float) 240 / 1.0F);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				if(this.stack != null) {
					if(this.stack.getItem() == ModItems.assembly_template)
						GuiScreen.itemRender.renderItemAndEffectIntoGUI(GUIScreenTemplateFolder.this.fontRendererObj, GUIScreenTemplateFolder.this.mc.getTextureManager(), AssemblerRecipes.getOutputFromTempate(this.stack), this.xPos + 1, this.yPos + 1);
					else if(this.stack.getItem() == ModItems.chemistry_template)
						GuiScreen.itemRender.renderItemAndEffectIntoGUI(GUIScreenTemplateFolder.this.fontRendererObj, GUIScreenTemplateFolder.this.mc.getTextureManager(), new ItemStack(ModItems.chemistry_icon, 1, this.stack.getItemDamage()), this.xPos + 1, this.yPos + 1);
					else if(this.stack.getItem() == ModItems.crucible_template)
						GuiScreen.itemRender.renderItemAndEffectIntoGUI(GUIScreenTemplateFolder.this.fontRendererObj, GUIScreenTemplateFolder.this.mc.getTextureManager(), CrucibleRecipes.indexMapping.get(this.stack.getItemDamage()).icon, this.xPos + 1, this.yPos + 1);
					else
						GuiScreen.itemRender.renderItemAndEffectIntoGUI(GUIScreenTemplateFolder.this.fontRendererObj, GUIScreenTemplateFolder.this.mc.getTextureManager(), this.stack, this.xPos + 1, this.yPos + 1);
				}
				GL11.glEnable(GL11.GL_LIGHTING);
			} catch(Exception x) {
			}
		}

		public void drawString(int x, int y) {
			if(this.info == null || this.info.isEmpty())
				return;
			
			if(this.stack != null) {
				renderToolTip(this.stack, x, y);
			} else {
				func_146283_a(Arrays.asList(new String[] { this.info }), x, y);
			}
		}

		public void executeAction() {
			GUIScreenTemplateFolder.this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			if(this.type == 0) {
				PacketDispatcher.wrapper.sendToServer(new ItemFolderPacket(this.stack.copy()));
			} else if(this.type == 1) {
				if(GUIScreenTemplateFolder.this.currentPage > 0)
					GUIScreenTemplateFolder.this.currentPage--;
				updateButtons();
			} else if(this.type == 2) {
				if(GUIScreenTemplateFolder.this.currentPage < getPageCount())
					GUIScreenTemplateFolder.this.currentPage++;
				updateButtons();
			}
		}
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
}
