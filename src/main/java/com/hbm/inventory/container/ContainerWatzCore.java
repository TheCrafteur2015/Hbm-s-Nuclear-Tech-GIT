package com.hbm.inventory.container;

import com.hbm.items.ModItems;
import com.hbm.items.special.WatzFuel;
import com.hbm.tileentity.machine.TileEntityWatzCore;

import api.hbm.energy.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerWatzCore extends Container {
	
	private TileEntityWatzCore diFurnace;
	
	public ContainerWatzCore(InventoryPlayer invPlayer, TileEntityWatzCore tedf) {
		
		this.diFurnace = tedf;
		
		addSlotToContainer(new Slot(tedf, 0, 8, 18));
		addSlotToContainer(new Slot(tedf, 1, 26, 18));
		addSlotToContainer(new Slot(tedf, 2, 44, 18));
		addSlotToContainer(new Slot(tedf, 3, 62, 18));
		addSlotToContainer(new Slot(tedf, 4, 80, 18));
		addSlotToContainer(new Slot(tedf, 5, 98, 18));
		addSlotToContainer(new Slot(tedf, 6, 8, 36));
		addSlotToContainer(new Slot(tedf, 7, 26, 36));
		addSlotToContainer(new Slot(tedf, 8, 44, 36));
		addSlotToContainer(new Slot(tedf, 9, 62, 36));
		addSlotToContainer(new Slot(tedf, 10, 80, 36));
		addSlotToContainer(new Slot(tedf, 11, 98, 36));
		addSlotToContainer(new Slot(tedf, 12, 8, 54));
		addSlotToContainer(new Slot(tedf, 13, 26, 54));
		addSlotToContainer(new Slot(tedf, 14, 44, 54));
		addSlotToContainer(new Slot(tedf, 15, 62, 54));
		addSlotToContainer(new Slot(tedf, 16, 80, 54));
		addSlotToContainer(new Slot(tedf, 17, 98, 54));
		addSlotToContainer(new Slot(tedf, 18, 8, 72));
		addSlotToContainer(new Slot(tedf, 19, 26, 72));
		addSlotToContainer(new Slot(tedf, 20, 44, 72));
		addSlotToContainer(new Slot(tedf, 21, 62, 72));
		addSlotToContainer(new Slot(tedf, 22, 80, 72));
		addSlotToContainer(new Slot(tedf, 23, 98, 72));
		addSlotToContainer(new Slot(tedf, 24, 8, 90));
		addSlotToContainer(new Slot(tedf, 25, 26, 90));
		addSlotToContainer(new Slot(tedf, 26, 44, 90));
		addSlotToContainer(new Slot(tedf, 27, 62, 90));
		addSlotToContainer(new Slot(tedf, 28, 80, 90));
		addSlotToContainer(new Slot(tedf, 29, 98, 90));
		addSlotToContainer(new Slot(tedf, 30, 8, 108));
		addSlotToContainer(new Slot(tedf, 31, 26, 108));
		addSlotToContainer(new Slot(tedf, 32, 44, 108));
		addSlotToContainer(new Slot(tedf, 33, 62, 108));
		addSlotToContainer(new Slot(tedf, 34, 80, 108));
		addSlotToContainer(new Slot(tedf, 35, 98, 108));
		//Mud Input
		addSlotToContainer(new Slot(tedf, 36, 134, 108 - 18));
		//Battery
		addSlotToContainer(new Slot(tedf, 37, 152, 108 - 18));
		//Filter
		addSlotToContainer(new Slot(tedf, 38, 116, 63));
		//Mud Output
		addSlotToContainer(new Slot(tedf, 39, 134, 108));
		
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

			if(par2 <= 39) {
				if(!mergeItemStack(var5, 40, this.inventorySlots.size(), true)) {
					return null;
				}
				
			} else {
				
				if(var5.getItem() == ModItems.titanium_filter) {
					if(!mergeItemStack(var5, 38, 39, false)) {
						return null;
					}
				} else if(var5.getItem() instanceof WatzFuel) {
					if(!mergeItemStack(var5, 0, 36, false)) {
						return null;
					}
				} else if(var5.getItem() instanceof IBatteryItem) {
					if(!mergeItemStack(var5, 37, 38, false)) {
						return null;
					}
				} else {
					 if(!mergeItemStack(var5, 36, 37, false)) {
							return null;
					 }
				}
			}

			if(var5.stackSize == 0) {
				var4.putStack((ItemStack) null);
			} else {
				var4.onSlotChanged();
			}

			var4.onPickupFromSlot(p_82846_1_, var5);
		}

		return var3;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.diFurnace.isUseableByPlayer(player);
	}
}
