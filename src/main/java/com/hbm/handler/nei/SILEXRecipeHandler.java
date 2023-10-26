package com.hbm.handler.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.hbm.inventory.gui.GUISILEX;
import com.hbm.inventory.recipes.SILEXRecipes;
import com.hbm.inventory.recipes.SILEXRecipes.SILEXRecipe;
import com.hbm.items.machine.ItemFELCrystal.EnumWavelengths;
import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;
import com.hbm.util.WeightedRandomObject;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class SILEXRecipeHandler extends TemplateRecipeHandler {

	public LinkedList<RecipeTransferRect> transferRectsRec = new LinkedList<>();
	public LinkedList<RecipeTransferRect> transferRectsGui = new LinkedList<>();
	public LinkedList<Class<? extends GuiContainer>> guiRec = new LinkedList<>();
	public LinkedList<Class<? extends GuiContainer>> guiGui = new LinkedList<>();

	public class RecipeSet extends TemplateRecipeHandler.CachedRecipe {

		PositionedStack input;
		List<PositionedStack> outputs;
		List<Double> chances;
		double produced;
		EnumWavelengths crystalStrength;

		public RecipeSet(Object input, SILEXRecipe recipe) {
			
			this.input = new PositionedStack(input, 12, 24);
			this.outputs = new ArrayList<>();
			this.chances = new ArrayList<>();
			this.produced = recipe.fluidProduced / recipe.fluidConsumed;
			this.crystalStrength = recipe.laserStrength;
			
			double weight = 0;
			
			for(WeightedRandomObject obj : recipe.outputs) {
				weight += obj.itemWeight;
			}
			
			int sep = recipe.outputs.size() > 4 ? 3 : 2;
			
			for(int i = 0; i < recipe.outputs.size(); i++) {
				
				WeightedRandomObject obj = recipe.outputs.get(i);
				
				if(i < sep) {
					this.outputs.add(new PositionedStack(obj.asStack(), 68, 24 + i * 18 - 9 * ((Math.min(recipe.outputs.size(), sep) + 1) / 2)));
				} else {
					this.outputs.add(new PositionedStack(obj.asStack(), 116, 24 + (i - sep) * 18 - 9 * ((Math.min(recipe.outputs.size() - sep, sep) + 1) / 2)));
				}
				
				this.chances.add(100 * obj.itemWeight / weight);
			}
			
			/*for(WeightedRandomObject obj : recipe.outputs) {
				outputs.add(new PositionedStack(obj.asStack(), 65, 24 + off - 9 * ((recipe.outputs.size()) / 2) + 1));
				off += 18;
			}*/
		}

		@Override
		public List<PositionedStack> getIngredients() {
			return getCycledIngredients(SILEXRecipeHandler.this.cycleticks / 48, Arrays.asList(this.input));
		}

		@Override
		public List<PositionedStack> getOtherStacks() {
			return this.outputs;
		}

		@Override
		public PositionedStack getResult() {
			return this.outputs.get(0);
		}
	}

	@Override
	public String getRecipeName() {
		return "SILEX";
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {

		if(outputId.equals("silex") && getClass() == SILEXRecipeHandler.class) {

			Map<Object, SILEXRecipe> recipes = SILEXRecipes.getRecipes();
			
			for (Map.Entry<Object, SILEXRecipe> recipe : recipes.entrySet()) {
				this.arecipes.add(new RecipeSet(recipe.getKey(), recipe.getValue()));
			}

		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {

		Map<Object, SILEXRecipe> recipes = SILEXRecipes.getRecipes();

		for(Map.Entry<Object, SILEXRecipe> recipe : recipes.entrySet()) {

			for(WeightedRandomObject out : recipe.getValue().outputs) {
				
				if(NEIServerUtils.areStacksSameTypeCrafting(out.asStack(), result)) {
					this.arecipes.add(new RecipeSet(recipe.getKey(), recipe.getValue()));
				}
			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients) {

		if(inputId.equals("silex") && getClass() == SILEXRecipeHandler.class) {
			loadCraftingRecipes("silex", new Object[0]);
		} else {
			super.loadUsageRecipes(inputId, ingredients);
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {

		Map<Object, SILEXRecipe> recipes = SILEXRecipes.getRecipes();

		for(Map.Entry<Object, SILEXRecipe> recipe : recipes.entrySet()) {
			
			if(recipe.getKey() instanceof ItemStack) {

				if (NEIServerUtils.areStacksSameTypeCrafting(ingredient, (ItemStack)recipe.getKey()))
					this.arecipes.add(new RecipeSet(recipe.getKey(), recipe.getValue()));
				
			} else if (recipe.getKey() instanceof ArrayList) {
				
				for(Object o : (ArrayList)recipe.getKey()) {
					ItemStack stack = (ItemStack)o;

					if (NEIServerUtils.areStacksSameTypeCrafting(ingredient, stack))
						this.arecipes.add(new RecipeSet(stack, recipe.getValue()));
				}
			}
		}
	}

	@Override
	public void loadTransferRects() {
		this.transferRectsGui = new LinkedList<>();
		this.guiGui = new LinkedList<>();

		this.transferRects.add(new RecipeTransferRect(new Rectangle(42, 34 - 11, 24, 18), "silex"));
		this.transferRectsGui.add(new RecipeTransferRect(new Rectangle(39, 60, 60, 50), "silex"));
		this.guiGui.add(GUISILEX.class);
		RecipeTransferRectHandler.registerRectsToGuis(getRecipeTransferRectGuis(), this.transferRects);
		RecipeTransferRectHandler.registerRectsToGuis(this.guiGui, this.transferRectsGui);
	}

	@Override
	public void drawExtras(int recipe) {

		RecipeSet rec = (RecipeSet) this.arecipes.get(recipe);

		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

		/*int index = 0;
		for(Double chance : rec.chances) {
			fontRenderer.drawString(((int)(chance * 10D) / 10D) + "%", 84, 28 + index * 18 - 9 * ((rec.chances.size() + 1) / 2), 0x404040);
			index++;
		}*/
		
		for(int i = 0; i < rec.chances.size(); i++) {
			
			double chance = rec.chances.get(i);
			
			PositionedStack sta = rec.outputs.get(i);
			
			fontRenderer.drawString(((int)(chance * 10D) / 10D) + "%", sta.relx + 18, sta.rely + 4, 0x404040);
		}
		
		String am = ((int)(rec.produced * 10D) / 10D) + "x";
		fontRenderer.drawString(am, 52 - fontRenderer.getStringWidth(am) / 2, 43, 0x404040);
		
		String wavelength = (rec.crystalStrength == EnumWavelengths.NULL) ? EnumChatFormatting.WHITE + "N/A" : rec.crystalStrength.textColor + I18nUtil.resolveKey(rec.crystalStrength.name);
		fontRenderer.drawString(wavelength, (33 - fontRenderer.getStringWidth(wavelength) / 2), 8, 0x404040);
		
		
	}

	@Override
	public int recipiesPerPage() {
		return 2;
	}

	@Override
	public String getGuiTexture() {
		return RefStrings.MODID + ":textures/gui/nei/gui_nei_silex.png";
	}
}