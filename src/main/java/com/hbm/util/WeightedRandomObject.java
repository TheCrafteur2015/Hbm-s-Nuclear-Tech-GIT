package com.hbm.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;

public class WeightedRandomObject extends WeightedRandom.Item {
	
	Object item;

	public WeightedRandomObject(Object o, int weight) {
		super(weight);
		this.item = o;
	}
	
	public ItemStack asStack() {
		
		if(this.item instanceof ItemStack)
			return ((ItemStack) this.item).copy();
		
		return null;
	}
	
	public Item asItem() {
		
		if(this.item instanceof Item)
			return (Item) this.item;
		
		return null;
	}
}
