package com.hbm.inventory.container;

import com.hbm.tileentity.machine.rbmk.TileEntityRBMKControlAuto;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerRBMKControlAuto extends Container {

	public ContainerRBMKControlAuto(InventoryPlayer invPlayer, TileEntityRBMKControlAuto tedf) {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 20));
			}
		}

		for(int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142 + 20));
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_) {
		return null;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
}
