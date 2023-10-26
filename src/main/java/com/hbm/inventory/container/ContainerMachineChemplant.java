package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.SlotTakeOnly;
import com.hbm.tileentity.machine.TileEntityMachineChemplant;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineChemplant extends Container {

	private TileEntityMachineChemplant nukeBoy;

	public ContainerMachineChemplant(InventoryPlayer invPlayer, TileEntityMachineChemplant tedf) {
		this.nukeBoy = tedf;

		// Battery
		addSlotToContainer(new Slot(tedf, 0, 80, 18));
		// Upgrades
		addSlotToContainer(new Slot(tedf, 1, 116, 18));
		addSlotToContainer(new Slot(tedf, 2, 116, 36));
		addSlotToContainer(new Slot(tedf, 3, 116, 54));
		// Schematic
		addSlotToContainer(new Slot(tedf, 4, 80, 54));
		// Outputs
		addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 5, 134, 90));
		addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 6, 152, 90));
		addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 7, 134, 108));
		addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 8, 152, 108));
		// Fluid Output In
		addSlotToContainer(new Slot(tedf, 9, 134, 54));
		addSlotToContainer(new Slot(tedf, 10, 152, 54));
		// Fluid Outputs Out
		addSlotToContainer(new SlotTakeOnly(tedf, 11, 134, 72));
		addSlotToContainer(new SlotTakeOnly(tedf, 12, 152, 72));
		// Input
		addSlotToContainer(new Slot(tedf, 13, 8, 90));
		addSlotToContainer(new Slot(tedf, 14, 26, 90));
		addSlotToContainer(new Slot(tedf, 15, 8, 108));
		addSlotToContainer(new Slot(tedf, 16, 26, 108));
		// Fluid Input In
		addSlotToContainer(new Slot(tedf, 17, 8, 54));
		addSlotToContainer(new Slot(tedf, 18, 26, 54));
		// Fluid Input Out
		addSlotToContainer(new SlotTakeOnly(tedf, 19, 8, 72));
		addSlotToContainer(new SlotTakeOnly(tedf, 20, 26, 72));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 56));
			}
		}

		for(int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142 + 56));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			SlotCraftingOutput.checkAchievements(p_82846_1_, var5);

			if(par2 <= 20) {
				if(!mergeItemStack(var5, 21, this.inventorySlots.size(), true)) {
					return null;
				}
			} else if(!mergeItemStack(var5, 4, 5, false))
				if(!mergeItemStack(var5, 13, 19, false))
					return null;

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
		return this.nukeBoy.isUseableByPlayer(player);
	}
}
