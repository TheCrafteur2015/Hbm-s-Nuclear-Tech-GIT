package com.hbm.items.tool;

import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCraftingDegradation extends Item {
	
	public ItemCraftingDegradation(int durability) {
		setMaxStackSize(1);
		setMaxDamage(durability);
		setNoRepair();
		setCreativeTab(MainRegistry.controlTab);
	}

	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack) {
		return false;
	}
	
	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		if(this.getMaxDamage() > 0) {
			stack.setItemDamage(stack.getItemDamage() + 1);
			return stack;
			
		} else {
			return stack;
		}
	}
	
	@Override
	public Item setUnlocalizedName(String unlocalizedName) {
		super.setUnlocalizedName(unlocalizedName);
		setTextureName(RefStrings.MODID + ":"+ unlocalizedName);
		return this;
	}
}
