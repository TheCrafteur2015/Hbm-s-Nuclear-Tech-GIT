package com.hbm.inventory.recipes.anvil;

import java.util.Arrays;
import java.util.List;

import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.items.special.ItemHot;

import net.minecraft.item.ItemStack;

public class AnvilSmithingHotRecipe extends AnvilSmithingRecipe {

	public AnvilSmithingHotRecipe(int tier, ItemStack out, AStack left, AStack right) {
		super(tier, out, left, right);
	}
	
	@Override
	public boolean doesStackMatch(ItemStack input, AStack recipe) {
		
		if(input != null && input.getItem() instanceof ItemHot) {
			double heat = ItemHot.getHeat(input);
			
			if(heat < 0.5D)
				return false;
		}
		
		return recipe.matchesRecipe(input, false);
	}
	
	@Override
	public ItemStack getOutput(ItemStack left, ItemStack right) {
		
		if(left.getItem() instanceof ItemHot && right.getItem() instanceof ItemHot && this.output.getItem() instanceof ItemHot) {
			
			double h1 = ItemHot.getHeat(left);
			double h2 = ItemHot.getHeat(right);
			
			ItemStack out = this.output.copy();
			ItemHot.heatUp(out, (h1 + h2) / 2D);
			
			return out;
		}
		
		return this.output.copy();
	}
	
	@Override
	public List<ItemStack> getLeft() {
		return Arrays.asList(new ItemStack[] {getHot(this.left)});
	}
	
	@Override
	public List<ItemStack> getRight() {
		return Arrays.asList(new ItemStack[] {getHot(this.right)});
	}
	
	private ItemStack getHot(AStack stack) {
		ItemStack first = stack.extractForNEI().get(0);
		
		if(first.getItem() instanceof ItemHot) {
			ItemHot.heatUp(first);
		}
		
		return first;
	}
}
