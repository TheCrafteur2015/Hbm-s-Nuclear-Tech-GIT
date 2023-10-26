package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.tileentity.machine.TileEntityDiFurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerDiFurnace extends Container {

	private TileEntityDiFurnace diFurnace;

	public ContainerDiFurnace(InventoryPlayer invPlayer, TileEntityDiFurnace tedf) {

		this.diFurnace = tedf;

		addSlotToContainer(new Slot(tedf, 0, 80, 18));
		addSlotToContainer(new Slot(tedf, 1, 80, 54));
		addSlotToContainer(new Slot(tedf, 2, 8, 36));
		addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tedf, 3, 134, 36));

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
	public ItemStack slotClick(int index, int button, int mode, EntityPlayer player) {
		
		if(index >= 0 && index < 3 && button == 1 && mode == 0) {
			Slot slot = getSlot(index);
			if(!slot.getHasStack() && player.inventory.getItemStack() == null) {
				if(!player.worldObj.isRemote) {
					if(index == 0) this.diFurnace.sideUpper = (byte) ((this.diFurnace.sideUpper + 1) % 6);
					if(index == 1) this.diFurnace.sideLower = (byte) ((this.diFurnace.sideLower + 1) % 6);
					if(index == 2) this.diFurnace.sideFuel = (byte) ((this.diFurnace.sideFuel + 1) % 6);
					
					this.diFurnace.markDirty();
				}
				return null;
			}
		}
		
		return super.slotClick(index, button, mode, player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 <= 3) {
				if(!mergeItemStack(var5, 4, this.inventorySlots.size(), true)) {
					return null;
				}
			} else if(!mergeItemStack(var5, 0, 3, false)) {
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
