package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemModCladding extends ItemArmorMod {
	
	public double rad;
	
	public ItemModCladding(double rad) {
		super(ArmorModHandler.cladding, true, true, true, true);
		this.rad = rad;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add(EnumChatFormatting.YELLOW + "+" + this.rad + " rad-resistance");
		list.add("");
		super.addInformation(itemstack, player, list, bool);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		list.add(EnumChatFormatting.YELLOW + "  " + stack.getDisplayName() + " (+" + this.rad + " radiation resistence)");
	}
}
