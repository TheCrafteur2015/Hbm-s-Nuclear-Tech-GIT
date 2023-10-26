package com.hbm.inventory.container;

import com.hbm.inventory.SlotSmelting;
import com.hbm.tileentity.machine.TileEntityFurnaceSteel;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFurnaceSteel extends Container {
	
	protected TileEntityFurnaceSteel furnace;
	
	public ContainerFurnaceSteel(InventoryPlayer invPlayer, TileEntityFurnaceSteel furnace) {
		this.furnace = furnace;

		//input
		addSlotToContainer(new Slot(furnace, 0, 35, 17));
		addSlotToContainer(new Slot(furnace, 1, 35, 35));
		addSlotToContainer(new Slot(furnace, 2, 35, 53));
		//output
		addSlotToContainer(new SlotSmelting(invPlayer.player, furnace, 3, 125, 17));
		addSlotToContainer(new SlotSmelting(invPlayer.player, furnace, 4, 125, 35));
		addSlotToContainer(new SlotSmelting(invPlayer.player, furnace, 5, 125, 53));
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack stack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack originalStack = slot.getStack();
			stack = originalStack.copy();

			if(index <= 5) {
				if(!mergeItemStack(originalStack, 6, this.inventorySlots.size(), true)) {
					return null;
				}
				
				slot.onSlotChange(originalStack, stack);
				
			} else if(!mergeItemStack(originalStack, 0, 3, false)) {
				return null;
			}

			if(originalStack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}

		return stack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.furnace.isUseableByPlayer(player);
	}
}
