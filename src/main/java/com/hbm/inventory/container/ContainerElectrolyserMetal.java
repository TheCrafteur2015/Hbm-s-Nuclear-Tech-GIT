package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.tileentity.machine.TileEntityElectrolyser;

import api.hbm.energy.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerElectrolyserMetal extends Container {

	private TileEntityElectrolyser electrolyser;

	public ContainerElectrolyserMetal(InventoryPlayer invPlayer, TileEntityElectrolyser tedf) {
		this.electrolyser = tedf;

		//Battery
		addSlotToContainer(new Slot(tedf, 0, 186, 109));
		//Upgrades
		addSlotToContainer(new Slot(tedf, 1, 186, 140));
		addSlotToContainer(new Slot(tedf, 2, 186, 158));
		//Input
		addSlotToContainer(new Slot(tedf, 14, 10, 22));
		//Outputs
		addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 15, 136, 18));
		addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 16, 154, 18));
		addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 17, 136, 36));
		addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 18, 154, 36));
		addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 19, 136, 54));
		addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 20, 154, 54));

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

			if(par2 <= 10) {
				if(!mergeItemStack(var5, 11, this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(var3.getItem() instanceof IBatteryItem || var3.getItem() == ModItems.battery_creative) {
					if(!mergeItemStack(var5, 0, 1, false)) {
						return null;
					}
				} else if(var3.getItem() instanceof ItemMachineUpgrade) {
					if(!mergeItemStack(var5, 1, 3, false)) {
						return null;
					}
				} else {
					if(!mergeItemStack(var5, 3, 4, false)) {
						return null;
					}
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
		return this.electrolyser.isUseableByPlayer(player);
	}
}
