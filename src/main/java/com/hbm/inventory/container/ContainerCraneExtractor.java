package com.hbm.inventory.container;

import com.hbm.inventory.SlotPattern;
import com.hbm.inventory.SlotUpgrade;
import com.hbm.items.ModItems;
import com.hbm.tileentity.network.TileEntityCraneExtractor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCraneExtractor extends Container {
	
	protected TileEntityCraneExtractor extractor;
	
	public ContainerCraneExtractor(InventoryPlayer invPlayer, TileEntityCraneExtractor extractor) {
		this.extractor = extractor;
		
		//filter
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				addSlotToContainer(new SlotPattern(extractor, j + i * 3, 71 + j * 18, 17 + i * 18));
			}
		}
		
		//buffer
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				addSlotToContainer(new Slot(extractor, 9 + j + i * 3, 8 + j * 18, 17 + i * 18));
			}
		}
		
		//upgrades
		addSlotToContainer(new SlotUpgrade(extractor, 18, 152, 23));
		addSlotToContainer(new SlotUpgrade(extractor, 19, 152, 47));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 103 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 161));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(slot);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			
			if(slot < 9) { //filters
				return null;
			}

			if(slot <= this.extractor.getSizeInventory() - 1) {
				if(!mergeItemStack(var5, this.extractor.getSizeInventory(), this.inventorySlots.size(), true)) {
					return null;
				}
			} else {
				
				if(var3.getItem() == ModItems.upgrade_stack) {
					 if(!mergeItemStack(var5, 18, 19, false))
						 return null;
				} else if(var3.getItem() == ModItems.upgrade_ejector) {
					 if(!mergeItemStack(var5, 19, 20, false))
						 return null;
				} else if(!mergeItemStack(var5, 9, this.extractor.getSizeInventory(), false)) {
					 return null;
				}
				
				return null;
			}

			if(var5.stackSize == 0) {
				var4.putStack((ItemStack) null);
			} else {
				var4.onSlotChanged();
			}

			var4.onPickupFromSlot(player, var5);
		}

		return var3;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.extractor.isUseableByPlayer(player);
	}

	@Override
	public ItemStack slotClick(int index, int button, int mode, EntityPlayer player) {
		
		//L/R: 0
		//M3: 3
		//SHIFT: 1
		//DRAG: 5
		
		if(index < 0 || index > 8) {
			return super.slotClick(index, button, mode, player);
		}

		Slot slot = getSlot(index);
		
		ItemStack ret = null;
		ItemStack held = player.inventory.getItemStack();
		
		if(slot.getHasStack())
			ret = slot.getStack().copy();
		
		if(button == 1 && mode == 0 && slot.getHasStack()) {
			this.extractor.nextMode(index);
			return ret;
			
		} else {
			slot.putStack(held != null ? held.copy() : null);
			
			if(slot.getHasStack()) {
				slot.getStack().stackSize = 1;
			}
			
			slot.onSlotChanged();
			this.extractor.matcher.initPatternStandard(this.extractor.getWorldObj(), slot.getStack(), index);
			
			return ret;
		}
	}
}
