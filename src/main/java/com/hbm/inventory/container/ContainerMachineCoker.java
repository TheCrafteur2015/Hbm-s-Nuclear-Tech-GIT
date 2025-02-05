package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.tileentity.machine.oil.TileEntityMachineCoker;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineCoker extends Container {

	private TileEntityMachineCoker coker;
	
	public ContainerMachineCoker(InventoryPlayer invPlayer, TileEntityMachineCoker tedf) {
		
		this.coker = tedf;
		
		addSlotToContainer(new Slot(tedf, 0, 35, 72));
		addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 1, 97, 27));
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 122 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 180));
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 <= 1) {
				if(!mergeItemStack(var5, 2, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(var3.getItem() instanceof IItemFluidIdentifier) {
					if(!mergeItemStack(var5, 0, 1, false)) {
						return null;
					}
				} else {
					return null;
				}
			}

			if(var5.stackSize == 0) {
				var4.putStack((ItemStack) null);
			} else {
				var4.onSlotChanged();
			}
		}

		return var3;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.coker.isUseableByPlayer(player);
	}
}
