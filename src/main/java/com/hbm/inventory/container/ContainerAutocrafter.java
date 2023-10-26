package com.hbm.inventory.container;

import com.hbm.inventory.SlotPattern;
import com.hbm.tileentity.machine.TileEntityMachineAutocrafter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAutocrafter extends Container {

	private TileEntityMachineAutocrafter autocrafter;

	public ContainerAutocrafter(InventoryPlayer invPlayer, TileEntityMachineAutocrafter tedf) {
		this.autocrafter = tedf;

		/* TEMPLATE */
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				addSlotToContainer(new SlotPattern(tedf, j + i * 3, 44 + j * 18, 22 + i * 18));
			}
		}
		addSlotToContainer(new SlotPattern(tedf, 9, 116, 40));

		/* RECIPE */
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				addSlotToContainer(new Slot(tedf, j + i * 3 + 10, 44 + j * 18, 86 + i * 18));
			}
		}
		addSlotToContainer(new Slot(tedf, 19, 116, 104));
		
		//Battery
		addSlotToContainer(new Slot(tedf, 20, 17, 99));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 158 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 216));
		}
	}

	@Override
	public ItemStack slotClick(int index, int button, int mode, EntityPlayer player) {
		
		//L/R: 0
		//M3: 3
		//SHIFT: 1
		//DRAG: 5

		if(index < 0 || index > 9) {
			return super.slotClick(index, button, mode, player);
		}

		Slot slot = getSlot(index);
		
		ItemStack ret = null;
		ItemStack held = player.inventory.getItemStack();
		
		if(slot.getHasStack())
			ret = slot.getStack().copy();
		
		//Don't allow any interaction for the template's output
		if(index == 9) {
			
			if(button == 1 && mode == 0 && slot.getHasStack()) {
				this.autocrafter.nextTemplate();
				detectAndSendChanges();
			}
			
			return ret;
		}
		
		if(button == 1 && mode == 0 && slot.getHasStack()) {
			this.autocrafter.nextMode(index);
			return ret;
			
		} else {
	
			slot.putStack(held != null ? held.copy() : null);
			
			if(slot.getHasStack()) {
				slot.getStack().stackSize = 1;
			}
			
			slot.onSlotChanged();
			this.autocrafter.initPattern(slot.getStack(), index);
			this.autocrafter.updateTemplateGrid();
			
			return ret;
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		return null;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.autocrafter.isUseableByPlayer(player);
	}
}
