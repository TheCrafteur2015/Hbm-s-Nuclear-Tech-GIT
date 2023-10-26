package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.hbm.inventory.gui.GUIMachineBoiler;
import com.hbm.inventory.gui.GUIMachineBoilerElectric;
import com.hbm.inventory.recipes.MachineRecipes;
import com.hbm.lib.RefStrings;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class BoilerRecipeHandler extends TemplateRecipeHandler {
	
    public LinkedList<RecipeTransferRect> transferRectsRec = new LinkedList<>();
    public LinkedList<RecipeTransferRect> transferRectsGui = new LinkedList<>();
    public LinkedList<Class<? extends GuiContainer>> guiRec = new LinkedList<>();
    public LinkedList<Class<? extends GuiContainer>> guiGui = new LinkedList<>();

    public class SmeltingSet extends TemplateRecipeHandler.CachedRecipe
    {
    	PositionedStack input;
        PositionedStack result;
    	
        public SmeltingSet(ItemStack input, ItemStack result) {
        	input.stackSize = 1;
            this.input = new PositionedStack(input, 21 + 9, 6 + 18);
            this.result = new PositionedStack(result, 120, 24);
        }

        @Override
		public List<PositionedStack> getIngredients() {
            return getCycledIngredients(BoilerRecipeHandler.this.cycleticks / 48, Arrays.asList(new PositionedStack[] {this.input}));
        }

        @Override
		public PositionedStack getResult() {
            return this.result;
        }
    }
    
	@Override
	public String getRecipeName() {
		return "Boiler";
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei_boiler.png";
	}

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return null;
    }

    @Override
    public TemplateRecipeHandler newInstance() {
        return super.newInstance();
    }
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if ((outputId.equals("ntmboiler")) && getClass() == BoilerRecipeHandler.class) {
			Map<Object, Object> recipes = MachineRecipes.instance().getBoilerRecipes();
			for (Map.Entry<Object, Object> recipe : recipes.entrySet()) {
				this.arecipes.add(new SmeltingSet((ItemStack)recipe.getKey(), 
						(ItemStack)recipe.getValue()));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		Map<Object, Object> recipes = MachineRecipes.instance().getBoilerRecipes();
		for (Map.Entry<Object, Object> recipe : recipes.entrySet()) {
			if (compareFluidStacks((ItemStack)recipe.getValue(), result) || 
					compareFluidStacks((ItemStack)recipe.getValue(), result))
				this.arecipes.add(new SmeltingSet((ItemStack)recipe.getKey(), 
						(ItemStack)recipe.getValue()));
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if ((inputId.equals("ntmboiler")) && getClass() == BoilerRecipeHandler.class) {
			loadCraftingRecipes("ntmboiler", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		Map<Object, Object> recipes = MachineRecipes.instance().getBoilerRecipes();
		for (Map.Entry<Object, Object> recipe : recipes.entrySet()) {
			if (compareFluidStacks(ingredient, (ItemStack)recipe.getKey()))
				this.arecipes.add(new SmeltingSet((ItemStack)recipe.getKey(), 
						(ItemStack)recipe.getValue()));				
		}
	}
	
	private boolean compareFluidStacks(ItemStack sta1, ItemStack sta2) {
		return sta1.getItem() == sta2.getItem() && sta1.getItemDamage() == sta2.getItemDamage();
	}

    @Override
    public void drawExtras(int recipe) {
        drawProgressBar(80, 23, 0, 85, 6, 17, 240, 3);
    }
    
    @Override
    public void loadTransferRects() {
        this.transferRectsGui = new LinkedList<>();
        this.guiGui = new LinkedList<>();

        this.transferRects.add(new RecipeTransferRect(new Rectangle(138 - 1 - 36 - 27 - 9, 23, 36, 18), "ntmboiler"));
        this.transferRectsGui.add(new RecipeTransferRect(new Rectangle(18 * 2 + 2 + 36, 89 - 29 - 18 - 18, 18, 18 * 2), "ntmboiler"));
        this.guiGui.add(GUIMachineBoiler.class);
        this.guiGui.add(GUIMachineBoilerElectric.class);
        RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), this.transferRects);
        RecipeTransferRectHandler.registerRectsToGuis(this.guiGui, this.transferRectsGui);
    }
}
