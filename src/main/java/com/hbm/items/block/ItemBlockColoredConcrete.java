package com.hbm.items.block;

import com.hbm.blocks.generic.BlockConcreteColored;

import net.minecraft.block.Block;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;

public class ItemBlockColoredConcrete extends ItemBlockBlastInfo {

	public ItemBlockColoredConcrete(Block block) {
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + "." + ItemDye.field_150923_a[BlockConcreteColored.func_150032_b(stack.getItemDamage())];
	}
}
