package com.hbm.inventory.container;

import com.hbm.inventory.SlotTakeOnly;
import com.hbm.tileentity.machine.TileEntityMachineBoilerElectric;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineBoilerElectric extends Container {
	
	private TileEntityMachineBoilerElectric diFurnace;
	
	public ContainerMachineBoilerElectric(InventoryPlayer invPlayer, TileEntityMachineBoilerElectric tedf) {
		
		this.diFurnace = tedf;

		//Fluid ID
		addSlotToContainer(new Slot(tedf, 0, 8, 17));
		addSlotToContainer(new SlotTakeOnly(tedf, 1, 8, 53));
		//Input IO
		addSlotToContainer(new Slot(tedf, 2, 44, 17));
		addSlotToContainer(new SlotTakeOnly(tedf, 3, 44, 53));
		//Battery
		addSlotToContainer(new Slot(tedf, 4, 98, 53));
		//Output IO
		addSlotToContainer(new Slot(tedf, 5, 152, 17));
		addSlotToContainer(new SlotTakeOnly(tedf, 6, 152, 53));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142));
		}
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
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
			
            if (par2 <= 6) {
				if (!mergeItemStack(var5, 7, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (!mergeItemStack(var5, 4, 5, false))
			{
				if (!mergeItemStack(var5, 2, 3, false))
					if (!mergeItemStack(var5, 5, 6, false))
						if (!mergeItemStack(var5, 0, 1, false))
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
		return this.diFurnace.isUseableByPlayer(player);
	}
}
