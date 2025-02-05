package com.hbm.inventory.container;

import com.hbm.inventory.FluidContainerRegistry;
import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.SlotTakeOnly;
import com.hbm.inventory.SlotUpgrade;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.tileentity.machine.TileEntityMachineCyclotron;

import api.hbm.energy.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineCyclotron extends Container {

	private TileEntityMachineCyclotron cyclotron;
	
	public ContainerMachineCyclotron(InventoryPlayer invPlayer, TileEntityMachineCyclotron tile) {
		
		this.cyclotron = tile;
		
		//Input
		addSlotToContainer(new Slot(tile, 0, 17, 18));
		addSlotToContainer(new Slot(tile, 1, 17, 36));
		addSlotToContainer(new Slot(tile, 2, 17, 54));
		//Targets
		addSlotToContainer(new Slot(tile, 3, 107, 18));
		addSlotToContainer(new Slot(tile, 4, 107, 36));
		addSlotToContainer(new Slot(tile, 5, 107, 54));
		//Output
		addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tile, 6, 143, 18));
		addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tile, 7, 143, 36));
		addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tile, 8, 143, 54));
		//AMAT In
		addSlotToContainer(new Slot(tile, 9, 143, 90));
		//AMAT Out
		addSlotToContainer(new SlotTakeOnly(tile, 10, 143, 108));
		//Coolant In
		addSlotToContainer(new Slot(tile, 11, 62, 72));
		//Coolant Out
		addSlotToContainer(new SlotTakeOnly(tile, 12, 62, 90));
		//Battery
		addSlotToContainer(new Slot(tile, 13, 62, 108));
		//Upgrades
		addSlotToContainer(new SlotUpgrade(tile, 14, 17, 90));
		addSlotToContainer(new SlotUpgrade(tile, 15, 17, 108));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 56));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142 + 56));
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		
		ItemStack var3 = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			var3 = stack.copy();

			if(index <= 15) {
				if(!mergeItemStack(stack, 16, this.inventorySlots.size(), true)) {
					return null;
				}
				
			} else {
				
				if(stack.getItem() instanceof IBatteryItem || stack.getItem() == ModItems.battery_creative) {
					if(!mergeItemStack(stack, 13, 14, true))
						return null;
					
				} else if(FluidContainerRegistry.getFluidContent(stack, Fluids.COOLANT) > 0) {
					if(!mergeItemStack(stack, 11, 12, true))
						return null;
					
				} else if(FluidContainerRegistry.getFullContainer(stack, Fluids.AMAT) != null) {
					if(!mergeItemStack(stack, 9, 10, true))
						return null;
					
				} else if(stack.getItem() instanceof ItemMachineUpgrade) {
					if(!mergeItemStack(stack, 14, 15, true))
						if(!mergeItemStack(stack, 15, 16, true))
							return null;
					
				} else {
					
					if(stack.getItem() == ModItems.part_lithium ||
							stack.getItem() == ModItems.part_beryllium ||
							stack.getItem() == ModItems.part_carbon ||
							stack.getItem() == ModItems.part_copper ||
							stack.getItem() == ModItems.part_plutonium) {
						
						if(!mergeItemStack(stack, 0, 3, true))
							return null;
					} else {
						
						if(!mergeItemStack(stack, 3, 6, true))
							return null;
					}
				}
			}

			if(stack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}

		return var3;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.cyclotron.isUseableByPlayer(player);
	}
}
