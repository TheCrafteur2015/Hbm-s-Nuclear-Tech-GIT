package com.hbm.inventory.container;

import com.hbm.inventory.SlotSmelting;
import com.hbm.inventory.SlotUpgrade;
import com.hbm.tileentity.machine.TileEntityFurnaceIron;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFurnaceIron extends Container {
	
	protected TileEntityFurnaceIron furnace;
	
	public ContainerFurnaceIron(InventoryPlayer invPlayer, TileEntityFurnaceIron furnace) {
		this.furnace = furnace;

		//input
		addSlotToContainer(new Slot(furnace, 0, 53, 17));
		//fuel
		addSlotToContainer(new Slot(furnace, 1, 53, 53));
		addSlotToContainer(new Slot(furnace, 2, 71, 53));
		//output
		addSlotToContainer(new SlotSmelting(invPlayer.player, furnace, 3, 125, 35));
		//upgrade
		addSlotToContainer(new SlotUpgrade(furnace, 4, 17, 35));
		
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

			if(index <= 4) {
				if(!mergeItemStack(originalStack, 5, this.inventorySlots.size(), true)) {
					return null;
				}
				
				slot.onSlotChange(originalStack, stack);
				
			} else if(!mergeItemStack(originalStack, 0, 5, false)) {
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
