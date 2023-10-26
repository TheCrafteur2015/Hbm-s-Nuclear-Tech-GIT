package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.hbm.interfaces.Untested;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.gui.GUIMachineEPress;
import com.hbm.inventory.gui.GUIMachinePress;
import com.hbm.inventory.recipes.PressRecipes;
import com.hbm.items.machine.ItemStamp;
import com.hbm.items.machine.ItemStamp.StampType;
import com.hbm.lib.RefStrings;
import com.hbm.util.Tuple.Pair;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

@Untested
public class PressRecipeHandler extends TemplateRecipeHandler {

	public LinkedList<RecipeTransferRect> transferRectsRec = new LinkedList<>();
	public LinkedList<RecipeTransferRect> transferRectsGui = new LinkedList<>();
	public LinkedList<Class<? extends GuiContainer>> guiRec = new LinkedList<>();
	public LinkedList<Class<? extends GuiContainer>> guiGui = new LinkedList<>();

	public class SmeltingSet extends TemplateRecipeHandler.CachedRecipe {
		PositionedStack input;
		PositionedStack result;
		PositionedStack stamp;

		public SmeltingSet(Object stamp, AStack input, ItemStack result) {
			input.stacksize = 1;
			this.input = new PositionedStack(input.extractForNEI(), 83 - 35, 5 + 36 + 1);
			this.result = new PositionedStack(result, 83 + 28, 5 + 18 + 1);
			this.stamp = new PositionedStack(stamp, 83 - 35, 6, false);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(PressRecipeHandler.this.cycleticks / 48, Arrays.asList(this.input, this.stamp));
		}

		@Override
		public PositionedStack getResult() {
			return this.result;
		}
	}

	@Override
	public String getRecipeName() {
		return "Press";
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei_press.png";
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if((outputId.equals("pressing")) && getClass() == PressRecipeHandler.class) {
			
			HashMap<Pair<AStack, StampType>, ItemStack> recipes = PressRecipes.recipes;
			
			for(Map.Entry<Pair<AStack, StampType>, ItemStack> recipe : recipes.entrySet()) {
				this.arecipes.add(new SmeltingSet(ItemStamp.stamps.get(recipe.getKey().getValue()), recipe.getKey().getKey(), recipe.getValue()));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		
		HashMap<Pair<AStack, StampType>, ItemStack> recipes = PressRecipes.recipes;
		
		for(Map.Entry<Pair<AStack, StampType>, ItemStack> recipe : recipes.entrySet()) {
			if(NEIServerUtils.areStacksSameType(recipe.getValue(), result))
				this.arecipes.add(new SmeltingSet(ItemStamp.stamps.get(recipe.getKey().getValue()), recipe.getKey().getKey(), recipe.getValue()));
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if((inputId.equals("pressing")) && getClass() == PressRecipeHandler.class) {
			loadCraftingRecipes("pressing", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		
		HashMap<Pair<AStack, StampType>, ItemStack> recipes = PressRecipes.recipes;
		
		for(Map.Entry<Pair<AStack, StampType>, ItemStack> recipe : recipes.entrySet()) {
			AStack in = recipe.getKey().getKey();
			StampType stamp = recipe.getKey().getValue();
			
			if(in.matchesRecipe(ingredient, true))
				this.arecipes.add(new SmeltingSet(ItemStamp.stamps.get(recipe.getKey().getValue()), new ComparableStack(ingredient), recipe.getValue()));
			else if(ingredient.getItem() instanceof ItemStamp && ((ItemStamp)ingredient.getItem()).type == stamp)
				this.arecipes.add(new SmeltingSet(ingredient, recipe.getKey().getKey(), recipe.getValue()));
		}
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		// return GUIMachineShredder.class;
		return null;
	}

	@Override
	public void loadTransferRects() {
		this.transferRectsGui = new LinkedList<>();
		this.guiGui = new LinkedList<>();

		this.transferRects.add(new RecipeTransferRect(new Rectangle(74 + 6, 23, 24, 18), "pressing"));
		this.transferRectsGui.add(new RecipeTransferRect(new Rectangle(74 + 6 + 18, 23, 24, 18), "pressing"));
		this.guiGui.add(GUIMachinePress.class);
		this.guiGui.add(GUIMachineEPress.class);
		RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), this.transferRects);
		RecipeTransferRectHandler.registerRectsToGuis(this.guiGui, this.transferRectsGui);
	}

	@Override
	public void drawExtras(int recipe) {
		drawProgressBar(47, 24, 0, 86, 18, 18, 20, 1);
	}

	@Override
	public TemplateRecipeHandler newInstance() {
		return super.newInstance();
	}
}
