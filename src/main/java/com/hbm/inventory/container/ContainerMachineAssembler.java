package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.tileentity.machine.TileEntityMachineAssembler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineAssembler extends Container {

private TileEntityMachineAssembler assembler;
	
	public ContainerMachineAssembler(InventoryPlayer invPlayer, TileEntityMachineAssembler te) {
		this.assembler = te;

		//Battery
		addSlotToContainer(new Slot(te, 0, 80, 18));
		//Upgrades
		addSlotToContainer(new Slot(te, 1, 152, 18));
		addSlotToContainer(new Slot(te, 2, 152, 36));
		addSlotToContainer(new Slot(te, 3, 152, 54));
		//Schematic
		addSlotToContainer(new Slot(te, 4, 80, 54));
		//Output
		addSlotToContainer(new SlotCraftingOutput(invPlayer.player, te, 5, 134, 90));
		//Input
		addSlotToContainer(new Slot(te, 6, 8, 18));
		addSlotToContainer(new Slot(te, 7, 26, 18));
		addSlotToContainer(new Slot(te, 8, 8, 36));
		addSlotToContainer(new Slot(te, 9, 26, 36));
		addSlotToContainer(new Slot(te, 10, 8, 54));
		addSlotToContainer(new Slot(te, 11, 26, 54));
		addSlotToContainer(new Slot(te, 12, 8, 72));
		addSlotToContainer(new Slot(te, 13, 26, 72));
		addSlotToContainer(new Slot(te, 14, 8, 90));
		addSlotToContainer(new Slot(te, 15, 26, 90));
		addSlotToContainer(new Slot(te, 16, 8, 108));
		addSlotToContainer(new Slot(te, 17, 26, 108));
		
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
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2)
    {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);
		
		if (var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			SlotCraftingOutput.checkAchievements(p_82846_1_, var5);
			
            if (par2 <= 17) {
				if (!mergeItemStack(var5, 18, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (!mergeItemStack(var5, 6, 18, false))
				if (!mergeItemStack(var5, 0, 4, false))
					return null;
			
			if(var5.stackSize == 0) {
				var4.putStack((ItemStack) null);
			} else {
				var4.onSlotChanged();
			}

			if(var5.stackSize == var3.stackSize) {
				return null;
			}

			var4.onPickupFromSlot(p_82846_1_, var3);
		}

		return var3;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.assembler.isUseableByPlayer(player);
	}
}
