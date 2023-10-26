package com.hbm.items.machine;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemLens extends Item {
	
	public long maxDamage;
	
	public ItemLens(long maxDamage) {
		this.maxDamage = maxDamage;
	}
	
	public static long getLensDamage(ItemStack stack) {
		
		if(!stack.hasTagCompound()) {
			stack.stackTagCompound = new NBTTagCompound();
			return 0;
		}
		
		return stack.stackTagCompound.getLong("damage");
	}

	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		long damage = ItemLens.getLensDamage(stack);
		int percent = (int) ((this.maxDamage - damage) * 100 / this.maxDamage);
		
		list.add("Durability: " + (this.maxDamage - damage) + "/" + this.maxDamage + " (" + percent + "%)");
	}
	
	public static void setLensDamage(ItemStack stack, long damage) {
		
		if(!stack.hasTagCompound()) {
			stack.stackTagCompound = new NBTTagCompound();
		}
		
		stack.stackTagCompound.setLong("damage", damage);
	}
	
    @Override
	public double getDurabilityForDisplay(ItemStack stack)
    {
        return (double)ItemLens.getLensDamage(stack) / (double)this.maxDamage;
    }
    
    @Override
	public boolean showDurabilityBar(ItemStack stack)
    {
        return getDurabilityForDisplay(stack) != 0;
    }

}
