package com.hbm.inventory.recipes.anvil;

import java.util.List;

import com.hbm.inventory.OreNames;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.items.ModItems;
import com.hbm.util.ItemStackUtil;

import net.minecraft.item.ItemStack;

public class AnvilSmithingMold extends AnvilSmithingRecipe {
	
	OreDictStack matchesPrefix;
	ItemStack[] matchesStack;

	public AnvilSmithingMold(int meta, AStack demo, Object o) {
		super(1, new ItemStack(ModItems.mold, 1, meta), demo, new ComparableStack(ModItems.mold_base));
		
		if(o instanceof OreDictStack)
			this.matchesPrefix = (OreDictStack) o;
		if(o instanceof ItemStack[])
			this.matchesStack = (ItemStack[]) o;
	}
	
	@Override
	public boolean matches(ItemStack left, ItemStack right) {
		if(!doesStackMatch(right, this.right)) return false;
		
		if(this.matchesPrefix != null && left.stackSize == this.matchesPrefix.stacksize) {
			List<String> names = ItemStackUtil.getOreDictNames(left);
			
			for(String name : names) {
				
				for(String otherPrefix : OreNames.prefixes) {
					if(otherPrefix.length() > this.matchesPrefix.name.length() && name.startsWith(otherPrefix)) {
						return false; //ignore if there's a longer prefix that matches (i.e. a more accurate match)
					}
				}
				
				if(name.startsWith(this.matchesPrefix.name)) {
					return true;
				}
			}
		}
		
		if(this.matchesStack != null) {
			
			for(ItemStack stack : this.matchesStack) {
				if(left.getItem() == stack.getItem() && left.getItemDamage() == stack.getItemDamage() && left.stackSize  == stack.stackSize) {
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public int matchesInt(ItemStack left, ItemStack right) {
		return matches(left, right) ? 0 : -1;
	}
	
	@Override
	public int amountConsumed(int index, boolean mirrored) {
		return index;
	}
}
