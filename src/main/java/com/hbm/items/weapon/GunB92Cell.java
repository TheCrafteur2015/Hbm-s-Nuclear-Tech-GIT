package com.hbm.items.weapon;

import java.util.List;

import com.hbm.items.ModItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class GunB92Cell extends Item {


	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b) {
		if(entity instanceof EntityPlayer && GunB92Cell.getPower(stack) < 25) {
			EntityPlayer player = (EntityPlayer) entity;
			
			for (ItemStack element : player.inventory.mainInventory) {
				if(element != null && element.getItem() == ModItems.gun_b92) {
					int p = GunB92Cell.getPower(element);
					if(p > 1) {
						GunB92Cell.setPower(element, p - 1);
						GunB92Cell.setPower(stack, GunB92Cell.getPower(stack) + 1);
						if(GunB92Cell.getPower(stack) == 25)
							stack.setItemDamage(1);
						return;
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		list.add("Draws energy from the B92, allowing you to");
		list.add("reload it an additional 25 times.");
		list.add("The cell will permanently hold it's charge,");
		list.add("it is not meant to be used as a battery enhancement");
		list.add("for the B92, but rather as a bomb.");
		list.add("");
		list.add("Charges: " + GunB92Cell.getPower(itemstack) + " / 25");
	}

	private static int getPower(ItemStack stack) {
		if (stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			return 0;
		}

		return stack.stackTagCompound.getInteger("energy");

	}

	private static void setPower(ItemStack stack, int i) {
		if (stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}

		stack.stackTagCompound.setInteger("energy", i);

	}
	
	public static ItemStack getFullCell() {
		ItemStack stack = new ItemStack(ModItems.gun_b92_ammo, 1, 1);
		GunB92Cell.setPower(stack, 25);
		return stack.copy();
	}

}
