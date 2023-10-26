package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.hbm.inventory.gui.GUIMachineCyclotron;
import com.hbm.inventory.recipes.CyclotronRecipes;
import com.hbm.lib.RefStrings;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class CyclotronRecipeHandler extends TemplateRecipeHandler {
	
    public LinkedList<RecipeTransferRect> transferRectsRec = new LinkedList<>();
    public LinkedList<RecipeTransferRect> transferRectsGui = new LinkedList<>();
    public LinkedList<Class<? extends GuiContainer>> guiRec = new LinkedList<>();
    public LinkedList<Class<? extends GuiContainer>> guiGui = new LinkedList<>();

    public class SmeltingSet extends TemplateRecipeHandler.CachedRecipe
    {
    	PositionedStack input1;
		PositionedStack input2;
        PositionedStack result;
    	
        public SmeltingSet(ItemStack input1, ItemStack input2, ItemStack result) {
        	input1.stackSize = 1;
        	input2.stackSize = 1;
            this.input1 = new PositionedStack(input1, 66 - 45, 6 + 18);
            this.input2 = new PositionedStack(input2, 66 + 9, 42 - 18);
            this.result = new PositionedStack(result, 129, 24);
        }

        @Override
		public List<PositionedStack> getIngredients() {
            return getCycledIngredients(CyclotronRecipeHandler.this.cycleticks / 48, Arrays.asList(new PositionedStack[] {this.input1, this.input2}));
        }

        @Override
		public PositionedStack getResult() {
            return this.result;
        }
    }
    
	@Override
	public String getRecipeName() {
		return "Cyclotron";
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei_cyclotron.png";
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ((outputId.equals("cyclotronProcessing")) && getClass() == CyclotronRecipeHandler.class) {
			Map<Object[], Object> recipes = CyclotronRecipes.getRecipes();
			for (Map.Entry<Object[], Object> recipe : recipes.entrySet()) {
				this.arecipes.add(new SmeltingSet((ItemStack)recipe.getKey()[0], (ItemStack)recipe.getKey()[1], (ItemStack)recipe.getValue()));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		Map<Object[], Object> recipes = CyclotronRecipes.getRecipes();
		for (Map.Entry<Object[], Object> recipe : recipes.entrySet()) {
			if (NEIServerUtils.areStacksSameType((ItemStack)recipe.getValue(), result))
				this.arecipes.add(new SmeltingSet((ItemStack)recipe.getKey()[0], (ItemStack)recipe.getKey()[1], (ItemStack)recipe.getValue()));
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if ((inputId.equals("cyclotronProcessing")) && getClass() == CyclotronRecipeHandler.class) {
			loadCraftingRecipes("cyclotronProcessing", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		Map<Object[], Object> recipes = CyclotronRecipes.getRecipes();
		for (Map.Entry<Object[], Object> recipe : recipes.entrySet()) {
			if (NEIServerUtils.areStacksSameType(ingredient, (ItemStack)recipe.getKey()[0]) || NEIServerUtils.areStacksSameType(ingredient, (ItemStack)recipe.getKey()[1]))
				this.arecipes.add(new SmeltingSet((ItemStack)recipe.getKey()[0], (ItemStack)recipe.getKey()[1], (ItemStack)recipe.getValue()));				
		}
	}

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        //return GUITestDiFurnace.class;
    	return null;
    }
    
    @Override
    public void loadTransferRects() {
        this.transferRectsGui = new LinkedList<>();
        this.guiGui = new LinkedList<>();
        
        this.transferRects.add(new RecipeTransferRect(new Rectangle(83 - 3 + 16 - 52, 5 + 18 + 1, 24, 18), "cyclotronProcessing"));
        this.transferRectsGui.add(new RecipeTransferRect(new Rectangle(47, 15, 36, 36), "cyclotronProcessing"));
        this.guiGui.add(GUIMachineCyclotron.class);
        RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), this.transferRects);
        RecipeTransferRectHandler.registerRectsToGuis(this.guiGui, this.transferRectsGui);
    }

    @Override
    public void drawExtras(int recipe) {

        drawProgressBar(83 - 3 + 16 - 52, 5 + 18 + 1, 100, 118 + 1, 24, 16, 48, 0);
    }

    @Override
    public TemplateRecipeHandler newInstance() {
        return super.newInstance();
    }
}
