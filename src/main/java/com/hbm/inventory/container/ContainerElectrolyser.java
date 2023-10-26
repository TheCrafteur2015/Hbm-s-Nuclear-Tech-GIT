package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.tileentity.machine.TileEntityElectrolyser;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerElectrolyser extends Container {
	
	TileEntityElectrolyser electrolyser;
	
	public ContainerElectrolyser(InventoryPlayer invPlayer, TileEntityElectrolyser tile) {
		
		this.electrolyser = tile;
		
		//0 - battery
		//1-2 - upgrade slots
		
		//3-4 - fluid ID
		//5-10 - fluid I/O
		//11-13 - dissolved outputs
		
		//14 - crystal input
		//15 - niter input
		//16-17 - casting slots
		//18-23 - other material outputs
		
		addSlotToContainer(new Slot(tile, 0, 186, 109));
		
		addSlotToContainer(new Slot(tile, 1, 186, 140));
		addSlotToContainer(new Slot(tile, 2, 186, 158));
		
		addSlotToContainer(new Slot(tile, 3, 6, 18));
		addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tile, 4, 6, 54));
		
		addSlotToContainer(new Slot(tile, 5, 24, 18));
		addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tile, 6, 24, 54));
		
		addSlotToContainer(new Slot(tile, 7, 78, 18));
		addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tile, 8, 78, 54));
		
		addSlotToContainer(new Slot(tile, 9, 134, 18));
		addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tile, 10, 134, 54));
		
		for(int i = 0; i < 3; i++) {
			addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tile, 11+i, 154, 18+(18*i)));
		}
		
		addSlotToContainer(new Slot(tile, 14, 10, 90));
		
		addSlotToContainer(new Slot(tile, 15, 37, 122));
		
		addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tile, 16, 60, 112));
		addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tile, 16, 98, 112));
		
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 3; j++) {
				addSlotToContainer(new SlotCraftingOutput(invPlayer.player, tile, 17+(i*3)+j, 136+(18*i), 86+(18*j)));
			}
		}
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 165 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 223));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.electrolyser.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		return null;
	}

}
