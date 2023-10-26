package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.gui.GUIMachineChemplant;
import com.hbm.inventory.recipes.ChemplantRecipes;
import com.hbm.inventory.recipes.ChemplantRecipes.ChemRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.lib.RefStrings;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class ChemplantRecipeHandler extends TemplateRecipeHandler {

	public LinkedList<RecipeTransferRect> transferRectsRec = new LinkedList<>();
	public LinkedList<RecipeTransferRect> transferRectsGui = new LinkedList<>();
	public LinkedList<Class<? extends GuiContainer>> guiRec = new LinkedList<>();
	public LinkedList<Class<? extends GuiContainer>> guiGui = new LinkedList<>();

	public class RecipeSet extends TemplateRecipeHandler.CachedRecipe {
		PositionedStack[] itemIn = new PositionedStack[4];
		PositionedStack[] fluidIn = new PositionedStack[2];
		PositionedStack[] itemOut = new PositionedStack[4];
		PositionedStack[] fluidOut = new PositionedStack[2];
		PositionedStack template;

		public RecipeSet(ChemRecipe recipe) {
			
			for(int i = 0; i < recipe.inputs.length; i++) {
				AStack in = recipe.inputs[i];
				if(in == null) continue;
				this.itemIn[i] = new PositionedStack(in.extractForNEI(), 30 + (i % 2) * 18, 24 + (i / 2) * 18);
			}
			
			for(int i = 0; i < recipe.inputFluids.length; i++) {
				FluidStack in = recipe.inputFluids[i];
				if(in == null) continue;
				ItemStack drop = ItemFluidIcon.make(in);
				this.fluidIn[i] = new PositionedStack(drop, 30 + (i % 2) * 18, 6);
			}
			
			for(int i = 0; i < recipe.outputs.length; i++) {
				ItemStack out = recipe.outputs[i];
				if(out == null) continue;
				this.itemOut[i] = new PositionedStack(out, 120 + (i % 2) * 18, 24 + (i / 2) * 18);
			}
			
			for(int i = 0; i < recipe.outputFluids.length; i++) {
				FluidStack out = recipe.outputFluids[i];
				if(out == null) continue;
				ItemStack drop = ItemFluidIcon.make(out);
				this.fluidOut[i] = new PositionedStack(drop, 120 + (i % 2) * 18, 6);
			}
			
			this.template = new PositionedStack(new ItemStack(ModItems.chemistry_template, 1, recipe.getId()), 84, 6);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			List<PositionedStack> stacks = new ArrayList<>();
			
			for(PositionedStack stack : this.itemIn) if(stack != null) stacks.add(stack);
			for(PositionedStack stack : this.fluidIn) if(stack != null) stacks.add(stack);
			stacks.add(this.template);
			
			return getCycledIngredients(ChemplantRecipeHandler.this.cycleticks / 20, stacks);
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> stacks = new ArrayList<>();
			
			for(PositionedStack stack : this.itemOut) if(stack != null) stacks.add(stack);
			for(PositionedStack stack : this.fluidOut) if(stack != null) stacks.add(stack);
			stacks.add(this.template);
			
			return stacks;
		}

		@Override
		public PositionedStack getResult() {
			return null;
		}
	}

	@Override
	public String getRecipeName() {
		return "Chemical Plant";
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei_chemplant.png";
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		
		if((outputId.equals("chemistry")) && getClass() == ChemplantRecipeHandler.class) {
			
			for(ChemRecipe recipe : ChemplantRecipes.recipes) {
				this.arecipes.add(new RecipeSet(recipe));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {

		outer:
		for(ChemRecipe recipe : ChemplantRecipes.recipes) {
			
			for(ItemStack out : recipe.outputs) {
				
				if(out != null && NEIServerUtils.areStacksSameTypeCrafting(result, out)) {
					this.arecipes.add(new RecipeSet(recipe));
					continue outer;
				}
			}
			
			for(FluidStack out : recipe.outputFluids) {
				
				if(out != null) {
					ItemStack drop = ItemFluidIcon.make(out.type, out.fill);
					
					if(compareFluidStacks(result, drop)) {
						this.arecipes.add(new RecipeSet(recipe));
						continue outer;
					}
				}
			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		if((inputId.equals("chemistry")) && getClass() == ChemplantRecipeHandler.class) {
			loadCraftingRecipes("chemistry", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {

		outer:
		for(ChemRecipe recipe : ChemplantRecipes.recipes) {
			
			for(AStack in : recipe.inputs) {
				
				if(in != null) {
					List<ItemStack> stacks = in.extractForNEI();
					
					for(ItemStack stack : stacks) {
						if(NEIServerUtils.areStacksSameTypeCrafting(ingredient, stack)) {
							this.arecipes.add(new RecipeSet(recipe));
							continue outer;
						}
					}
				}
			}
			
			for(FluidStack in : recipe.inputFluids) {
				
				if(in != null) {
					ItemStack drop = ItemFluidIcon.make(in.type, in.fill);
					
					if(compareFluidStacks(ingredient, drop)) {
						this.arecipes.add(new RecipeSet(recipe));
						continue outer;
					}
				}
			}
		}
	}

	private boolean compareFluidStacks(ItemStack sta1, ItemStack sta2) {
		return sta1.getItem() == sta2.getItem() && sta1.getItemDamage() == sta2.getItemDamage();
	}

	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return null;
	}

	@Override
	public void loadTransferRects() {
		this.transferRectsGui = new LinkedList<>();
		this.guiGui = new LinkedList<>();

		this.transferRects.add(new RecipeTransferRect(new Rectangle(138 - 1 - 72, 23, 18 * 3, 18), "chemistry"));
		this.transferRectsGui.add(new RecipeTransferRect(new Rectangle(18 * 2 + 2, 89 - 7 - 11, 18 * 5 - 4, 18 + 16), "chemistry"));
		this.guiGui.add(GUIMachineChemplant.class);
		RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), this.transferRects);
		RecipeTransferRectHandler.registerRectsToGuis(this.guiGui, this.transferRectsGui);
	}

	@Override
	public void drawExtras(int recipe) {

		drawProgressBar(83 - (18 * 4) - 9 + 1, 6, 0, 86, 16, 18 * 3 - 2, 480, 7);
		drawProgressBar(83 - 3 + 16 + 5 - 36, 5 + 18, 16, 86, 18 * 3, 18, 48, 0);
	}

	@Override
	public TemplateRecipeHandler newInstance() {
		return super.newInstance();
	}
}
