package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.hbm.blocks.machine.NTMAnvil;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.gui.GUIAnvil;
import com.hbm.inventory.recipes.anvil.AnvilRecipes;
import com.hbm.inventory.recipes.anvil.AnvilRecipes.AnvilConstructionRecipe;
import com.hbm.inventory.recipes.anvil.AnvilRecipes.AnvilOutput;
import com.hbm.inventory.recipes.anvil.AnvilRecipes.OverlayType;
import com.hbm.lib.RefStrings;
import com.hbm.util.ItemStackUtil;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class AnvilRecipeHandler extends TemplateRecipeHandler {

	public LinkedList<RecipeTransferRect> transferRectsRec = new LinkedList<>();
	public LinkedList<RecipeTransferRect> transferRectsGui = new LinkedList<>();
	public LinkedList<Class<? extends GuiContainer>> guiRec = new LinkedList<>();
	public LinkedList<Class<? extends GuiContainer>> guiGui = new LinkedList<>();

	public class RecipeSet extends TemplateRecipeHandler.CachedRecipe {
		
		List<PositionedStack> input = new ArrayList<>();
		List<PositionedStack> output = new ArrayList<>();
		PositionedStack anvil;
		int tier;
		OverlayType shape;

		public RecipeSet(List<Object> in, List<Object> out, int tier) {

			//not the prettiest of solutions but certainly the most pleasant to work with
			int inLine = 1;
			int outLine = 1;
			int inOX = 0;
			int inOY = 0;
			int outOX = 0;
			int outOY = 0;
			int anvX = 0;
			int anvY = 31;
			
			if(in.size() == 1 && out.size() == 1) {
				this.shape = OverlayType.SMITHING;
				inOX = 48;
				inOY = 24;
				outOX = 102;
				outOY = 24;
				anvX = 75;
			} else if(in.size() == 1 && out.size() > 1) {
				this.shape = OverlayType.RECYCLING;
				outLine = 6;
				inOX = 12;
				inOY = 24;
				outOX = 48;
				outOY = 6;
				anvX = 30;
			} else if(in.size() > 1 && out.size() == 1) {
				this.shape = OverlayType.CONSTRUCTION;
				inLine = 6;
				inOX = 12;
				inOY = 6;
				outOX = 138;
				outOY = 24;
				anvX = 120;
			} else {
				this.shape = OverlayType.NONE;
				inLine = 4;
				outLine = 4;
				inOX = 3;
				inOY = 6;
				outOX = 93;
				outOY = 6;
				anvX = 75;
			}
			
			for(int i = 0; i < in.size(); i++) {
				this.input.add(new PositionedStack(in.get(i), inOX + 18 * (i % inLine), inOY + 18 * (i / inLine)));
			}
			
			for(int i = 0; i < out.size(); i++) {
				this.output.add(new PositionedStack(out.get(i), outOX + 18 * (i % outLine), outOY + 18 * (i / outLine)));
			}
			
			this.anvil = new PositionedStack(NTMAnvil.getAnvilsFromTier(tier), anvX, anvY);
			
			this.tier = tier;
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(AnvilRecipeHandler.this.cycleticks / 20, this.input);
		}

		@Override
		public PositionedStack getResult() {
			return this.output.get(0);
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> other = new ArrayList<>();
			other.addAll(this.output);
			other.add(this.anvil);
			return getCycledIngredients(AnvilRecipeHandler.this.cycleticks / 20, other);
		}
	}

	@Override
	public String getRecipeName() {
		return "Anvil";
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		
		if(outputId.equals("ntmAnvil")) {
			List<AnvilConstructionRecipe> recipes = AnvilRecipes.getConstruction();
			
			for(AnvilConstructionRecipe recipe : recipes) {
				addRecipeToList(recipe);
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {

		List<AnvilConstructionRecipe> recipes = AnvilRecipes.getConstruction();
		
		for(AnvilConstructionRecipe recipe : recipes) {
			
			for(AnvilOutput out : recipe.output) {
				if(NEIServerUtils.areStacksSameTypeCrafting(out.stack, result)) {
					addRecipeToList(recipe);
					break;
				}
			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		
		if(inputId.equals("ntmAnvil")) {
			loadCraftingRecipes("ntmAnvil", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {

		List<AnvilConstructionRecipe> recipes = AnvilRecipes.getConstruction();
		
		for(AnvilConstructionRecipe recipe : recipes) {
			
			outer:
			for(AStack in : recipe.input) {
				
				List<ItemStack> stacks = in.extractForNEI();
				for(ItemStack stack : stacks) {
					if(NEIServerUtils.areStacksSameTypeCrafting(stack, ingredient)) {
						addRecipeToList(recipe);
						break outer;
					}
				}
			}
		}
	}
	
	private void addRecipeToList(AnvilConstructionRecipe recipe) {
		
		List<Object> ins = new ArrayList<>();
		for(AStack input : recipe.input) {
			ins.add(input.extractForNEI());
		}
		
		List<Object> outs = new ArrayList<>();
		for(AnvilOutput output : recipe.output) {
			
			ItemStack stack = output.stack.copy();
			if(output.chance != 1) {
				ItemStackUtil.addTooltipToStack(stack, EnumChatFormatting.RED + "" + (((int)(output.chance * 1000)) / 10D) + "%");
			}
			
			outs.add(stack);
		}
		
		this.arecipes.add(new RecipeSet(ins, outs, recipe.tierLower));
	}

	@Override
	public void loadTransferRects() {
		
		//hey asshole, stop nulling my fucking lists
		this.transferRectsGui = new LinkedList<>();
		this.guiGui = new LinkedList<>();

		this.transferRectsGui.add(new RecipeTransferRect(new Rectangle(11, 42, 36, 18), "ntmAnvil"));
		this.transferRectsGui.add(new RecipeTransferRect(new Rectangle(65, 42, 36, 18), "ntmAnvil"));
		
		this.guiGui.add(GUIAnvil.class);
		RecipeTransferRectHandler.registerRectsToGuis(this.guiGui, this.transferRectsGui);
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei_anvil.png";
	}

	@Override
	public void drawBackground(int recipe) {
		super.drawBackground(recipe);
		
		RecipeSet set = (RecipeSet) this.arecipes.get(recipe);
		
		switch(set.shape) {
		case NONE:
			GuiDraw.drawTexturedModalRect(2, 5, 5, 87, 72, 54);			//in
			GuiDraw.drawTexturedModalRect(92, 5, 5, 87, 72, 54);		//out
			GuiDraw.drawTexturedModalRect(74, 14, 131, 96, 18, 36);		//operation
			break;
		case SMITHING:
			GuiDraw.drawTexturedModalRect(47, 23, 113, 105, 18, 18);	//in
			GuiDraw.drawTexturedModalRect(101, 23, 113, 105, 18, 18);	//out
			GuiDraw.drawTexturedModalRect(74, 14, 149, 96, 18, 36);		//operation
			break;
		case CONSTRUCTION:
			GuiDraw.drawTexturedModalRect(11, 5, 5, 87, 108, 54);		//in
			GuiDraw.drawTexturedModalRect(137, 23, 113, 105, 18, 18);	//out
			GuiDraw.drawTexturedModalRect(119, 14, 167, 96, 18, 36);	//operation
			break;
		case RECYCLING:
			GuiDraw.drawTexturedModalRect(11, 23, 113, 105, 18, 18);	//in
			GuiDraw.drawTexturedModalRect(47, 5, 5, 87, 108, 54);		//out
			GuiDraw.drawTexturedModalRect(29, 14, 185, 96, 18, 36);		//operation
			break;
		}
	}
}
