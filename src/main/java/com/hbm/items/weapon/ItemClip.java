package com.hbm.items.weapon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemClip extends Item {
	
	private ItemStack toGive;

	public ItemClip(ItemStack stack) {
		this.toGive = stack;
		setMaxDamage(1);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(this.toGive == null) return stack;
		
		ItemStack ammo = this.toGive.copy();
		
		player.inventory.addItemStackToInventory(ammo);
		
		stack.stackSize--;
		if(stack.stackSize <= 0)
			stack.damageItem(5, player);
		
		return stack;
	}
}
