package com.hbm.inventory.container;

import com.hbm.tileentity.bomb.TileEntityNukeFleija;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerNukeFleija extends Container {

private TileEntityNukeFleija nukeTsar;
	
	public ContainerNukeFleija(InventoryPlayer invPlayer, TileEntityNukeFleija tedf) {
		
		this.nukeTsar = tedf;
		
		addSlotToContainer(new Slot(tedf, 0, 8, 36));
		addSlotToContainer(new Slot(tedf, 1, 152, 36));
		addSlotToContainer(new Slot(tedf, 2, 44, 18));
		addSlotToContainer(new Slot(tedf, 3, 44, 36));
		addSlotToContainer(new Slot(tedf, 4, 44, 54));
		addSlotToContainer(new Slot(tedf, 5, 80, 18));
		addSlotToContainer(new Slot(tedf, 6, 98, 18));
		addSlotToContainer(new Slot(tedf, 7, 80, 36));
		addSlotToContainer(new Slot(tedf, 8, 98, 36));
		addSlotToContainer(new Slot(tedf, 9, 80, 54));
		addSlotToContainer(new Slot(tedf, 10, 98, 54));
		
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
			
            if (par2 <= 10) {
				if (!mergeItemStack(var5, 11, this.inventorySlots.size(), true))
				{
					return null;
				}
			} else {
				return null;
			}
            
			if (var5.stackSize == 0)
			{
				var4.putStack((ItemStack) null);
			}
			else
			{
				var4.onSlotChanged();
			}
		}
		
		return var3;
    }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.nukeTsar.isUseableByPlayer(player);
	}
}
