package com.hbm.inventory.container;

import com.hbm.inventory.SlotSmelting;
import com.hbm.inventory.SlotUpgrade;
import com.hbm.tileentity.machine.TileEntityMachineElectricFurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerElectricFurnace extends Container {

	private TileEntityMachineElectricFurnace diFurnace;

	public ContainerElectricFurnace(InventoryPlayer invPlayer, TileEntityMachineElectricFurnace tedf) {

		this.diFurnace = tedf;

		addSlotToContainer(new Slot(tedf, 0, 56, 53));
		addSlotToContainer(new Slot(tedf, 1, 56, 17));
		addSlotToContainer(new SlotSmelting(invPlayer.player, tedf, 2, 116, 35));
		//Upgrades
		addSlotToContainer(new SlotUpgrade(tedf, 3, 147, 34));

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
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 <= 2) {
				if(!mergeItemStack(var5, 3, this.inventorySlots.size(), true)) {
					return null;
				}

				var4.onSlotChange(var5, var3);
			} else if(!mergeItemStack(var5, 1, 2, false)) {
				if(!mergeItemStack(var5, 0, 1, false))
					return null;
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
		return this.diFurnace.isUseableByPlayer(player);
	}
}
