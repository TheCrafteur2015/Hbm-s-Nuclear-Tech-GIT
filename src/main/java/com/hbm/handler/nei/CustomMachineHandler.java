package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.CustomMachineConfigJSON;
import com.hbm.config.CustomMachineConfigJSON.MachineConfiguration;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.recipes.CustomMachineRecipes;
import com.hbm.inventory.recipes.CustomMachineRecipes.CustomMachineRecipe;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;
import com.hbm.util.ItemStackUtil;
import com.hbm.util.Tuple.Pair;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class CustomMachineHandler extends TemplateRecipeHandler {
	
	public LinkedList<RecipeTransferRect> transferRectsRec = new LinkedList<>();
	public LinkedList<Class<? extends GuiContainer>> guiRec = new LinkedList<>();
	
	public MachineConfiguration conf;

	@Override
	public TemplateRecipeHandler newInstance() { // brick by brick, suck my dick
		try {
			return new CustomMachineHandler(this.conf);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public CustomMachineHandler(MachineConfiguration conf) {
		super();
		this.conf = conf;
		loadTransferRects();
		RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), this.transferRects);
	}
	
	public class RecipeSet extends TemplateRecipeHandler.CachedRecipe {

		List<PositionedStack> inputs = new ArrayList<>();
		PositionedStack machine;
		List<PositionedStack> outputs = new ArrayList<>();
		
		public RecipeSet(CustomMachineRecipe recipe) {
			for(int i = 0; i < 3; i++) if(recipe.inputFluids.length > i) this.inputs.add(new PositionedStack(ItemFluidIcon.make(recipe.inputFluids[i]), 12 + i * 18, 6));
			for(int i = 0; i < 3; i++) if(recipe.inputItems.length > i) this.inputs.add(new PositionedStack(recipe.inputItems[i].extractForNEI(), 12 + i * 18, 24));
			for(int i = 3; i < 6; i++) if(recipe.inputItems.length > i) this.inputs.add(new PositionedStack(recipe.inputItems[i].extractForNEI(), 12 + (i - 3) * 18, 42));

			for(int i = 0; i < 3; i++) if(recipe.outputFluids.length > i) this.outputs.add(new PositionedStack(ItemFluidIcon.make(recipe.outputFluids[i]), 102 + i * 18, 6));
			
			for(int i = 0; i < 3; i++) if(recipe.outputItems.length > i) {
				Pair<ItemStack, Float> pair = recipe.outputItems[i];
				ItemStack out = pair.getKey().copy();
				if(pair.getValue() != 1) {
					ItemStackUtil.addTooltipToStack(out, EnumChatFormatting.RED + "" + (((int)(pair.getValue() * 1000)) / 10D) + "%");
				}
				this.outputs.add(new PositionedStack(out, 102 + i * 18, 24));
			}
			
			for(int i = 3; i < 6; i++) if(recipe.outputItems.length > i) {
				Pair<ItemStack, Float> pair = recipe.outputItems[i];
				ItemStack out = pair.getKey().copy();
				if(pair.getValue() != 1) {
					ItemStackUtil.addTooltipToStack(out, EnumChatFormatting.RED + "" + (((int)(pair.getValue() * 1000)) / 10D) + "%");
				}
				this.outputs.add(new PositionedStack(out, 102 + (i - 3) * 18, 42));
			}
			
			this.machine = new PositionedStack(new ItemStack(ModBlocks.custom_machine, 1, 100 + CustomMachineConfigJSON.niceList.indexOf(CustomMachineHandler.this.conf)), 75, 42);
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(CustomMachineHandler.this.cycleticks / 20, this.inputs);
		}

		@Override
		public PositionedStack getResult() {
			return this.outputs.get(0);
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			List<PositionedStack> other = new ArrayList<>();
			other.addAll(this.inputs);
			other.add(this.machine);
			other.addAll(this.outputs);
			return getCycledIngredients(CustomMachineHandler.this.cycleticks / 20, other);
		}
	}

	@Override
	public String getRecipeName() {
		return (I18nUtil.resolveKey("tile.cm_" + this.conf.unlocalizedName + ".name").startsWith("tile.cm_")) ? this.conf.localizedName : I18nUtil.resolveKey("tile.cm_" + this.conf.unlocalizedName + ".name");
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei_custom.png";
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		
		if(outputId.equals("ntm_" + this.conf.unlocalizedName)) {
			
			List<CustomMachineRecipe> recipes = CustomMachineRecipes.recipes.get(this.conf.recipeKey);
			
			if(recipes != null) for(CustomMachineRecipe recipe : recipes) {
				this.arecipes.add(new RecipeSet(recipe));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}
	
	@Override
	public void loadCraftingRecipes(ItemStack result) {
		
		List<CustomMachineRecipe> recipes = CustomMachineRecipes.recipes.get(this.conf.recipeKey);
		
		if(recipes != null) outer:for(CustomMachineRecipe recipe : recipes) {
			
			for(Pair<ItemStack, Float> stack : recipe.outputItems) {
				
				if(NEIServerUtils.areStacksSameTypeCrafting(stack.getKey(), result)) {
					this.arecipes.add(new RecipeSet(recipe));
					continue outer;
				}
			}
			
			for(FluidStack fluid : recipe.outputFluids) {
				ItemStack drop = ItemFluidIcon.make(fluid);
				
				if(CustomMachineHandler.compareFluidStacks(result, drop)) {
					this.arecipes.add(new RecipeSet(recipe));
					continue outer;
				}
			}
		}
	}
	
	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {
		
		if(inputId.equals("ntm_" + this.conf.unlocalizedName)) {
			loadCraftingRecipes("ntm_" + this.conf.unlocalizedName, new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}
	
	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		
		List<CustomMachineRecipe> recipes = CustomMachineRecipes.recipes.get(this.conf.recipeKey);

		if(recipes != null) outer:for(CustomMachineRecipe recipe : recipes) {
			
			for(AStack stack : recipe.inputItems) {
				
				List<ItemStack> stacks = stack.extractForNEI();
				
				for(ItemStack sta : stacks) {
					if(NEIServerUtils.areStacksSameTypeCrafting(ingredient, sta)) {
						this.arecipes.add(new RecipeSet(recipe));
						continue outer;
					}
				}
			}
			
			for(FluidStack fluid : recipe.inputFluids) {
				ItemStack drop = ItemFluidIcon.make(fluid);
				
				if(CustomMachineHandler.compareFluidStacks(ingredient, drop)) {
					this.arecipes.add(new RecipeSet(recipe));
					continue outer;
				}
			}
		}
	}

	public static boolean compareFluidStacks(ItemStack sta1, ItemStack sta2) {
		return sta1.getItem() == sta2.getItem() && sta1.getItemDamage() == sta2.getItemDamage();
	}
	
	@Override
	public void loadTransferRects() {
		if(this.conf == null) return;
		this.transferRects.add(new RecipeTransferRect(new Rectangle(65, 23, 36, 18), "ntm_" + this.conf.unlocalizedName));
		RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), this.transferRects);
	}
}
