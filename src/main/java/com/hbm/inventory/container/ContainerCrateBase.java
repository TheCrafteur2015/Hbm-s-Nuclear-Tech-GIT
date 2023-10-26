package com.hbm.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCrateBase extends Container {
	
	protected IInventory crate;

	public ContainerCrateBase(IInventory tedf) {
		this.crate = tedf;
		this.crate.openInventory();
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 <= this.crate.getSizeInventory() - 1) {
				if(!mergeItemStack(var5, this.crate.getSizeInventory(), this.inventorySlots.size(), true)) {
					return null;
				}
			} else if(!mergeItemStack(var5, 0, this.crate.getSizeInventory(), false)) {
				return null;
			}

			if(var5.stackSize == 0) {
				var4.putStack((ItemStack) null);
			} else {
				var4.onSlotChanged();
			}

			var4.onPickupFromSlot(p_82846_1_, var5);
		}

		return var3;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.crate.isUseableByPlayer(player);
	}
	
	@Override
	public void onContainerClosed(EntityPlayer p_75134_1_) {
		super.onContainerClosed(p_75134_1_);
		this.crate.closeInventory();
	}
}
