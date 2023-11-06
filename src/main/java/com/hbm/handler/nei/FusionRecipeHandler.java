package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.hbm.inventory.gui.GUIITER;
import com.hbm.inventory.recipes.FusionRecipes;
import com.hbm.lib.RefStrings;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class FusionRecipeHandler extends TemplateRecipeHandler {
	
    public LinkedList<RecipeTransferRect> transferRectsRec = new LinkedList<>();
    public LinkedList<RecipeTransferRect> transferRectsGui = new LinkedList<>();
    public LinkedList<Class<? extends GuiContainer>> guiRec = new LinkedList<>();
    public LinkedList<Class<? extends GuiContainer>> guiGui = new LinkedList<>();

    public class SmeltingSet extends TemplateRecipeHandler.CachedRecipe {
    	
		PositionedStack input;
        PositionedStack result;
    	
        public SmeltingSet(ItemStack in, ItemStack out) {
        	
        	this.input = new PositionedStack(in, 30, 24);
            this.result = new PositionedStack(out, 120, 24);
        }

        @Override
		public List<PositionedStack> getIngredients() {
        	
            return new ArrayList() {
            	private static final long serialVersionUID = 1L;
            	{
            		add(SmeltingSet.this.input); }};
        }

        @Override
		public PositionedStack getResult() {
            return this.result;
        }
    }
    
	@Override
	public String getRecipeName() {
		return "Fusion Reactor";
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		
		if(outputId.equals("fusion") && getClass() == FusionRecipeHandler.class) {
			
			Map<ItemStack, ItemStack> recipes = FusionRecipes.getRecipes();
			
			for(Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()) {
				this.arecipes.add(new SmeltingSet(recipe.getKey(), recipe.getValue()));
			}
			
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		
		Map<ItemStack, ItemStack> recipes = FusionRecipes.getRecipes();
		
		for(Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()) {
			
			if(NEIServerUtils.areStacksSameTypeCrafting(recipe.getValue(), result)) {
				this.arecipes.add(new SmeltingSet(recipe.getKey(), recipe.getValue()));
			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		
		if(inputId.equals("fusion") && getClass() == FusionRecipeHandler.class) {
			loadCraftingRecipes("fusion", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		
		Map<ItemStack, ItemStack> recipes = FusionRecipes.getRecipes();
		
		for(Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()) {
			
			if(NEIServerUtils.areStacksSameTypeCrafting(recipe.getKey(), ingredient)) {
				this.arecipes.add(new SmeltingSet(recipe.getKey(), recipe.getValue()));
			}
		}
	}
    
    @Override
    public void loadTransferRects() {
        this.transferRectsGui = new LinkedList<>();
        this.guiGui = new LinkedList<>();

        this.transferRects.add(new RecipeTransferRect(new Rectangle(52 - 5, 34 - 11, 18 * 4, 18), "fusion"));
        this.transferRectsGui.add(new RecipeTransferRect(new Rectangle(115 - 5, 17 - 11, 18, 18), "fusion"));
        this.guiGui.add(GUIITER.class);
        RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), this.transferRects);
        RecipeTransferRectHandler.registerRectsToGuis(this.guiGui, this.transferRectsGui);
    }

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei_fusion.png";
	}
}
