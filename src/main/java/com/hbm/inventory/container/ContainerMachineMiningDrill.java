package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityMachineMiningDrill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineMiningDrill extends Container {

private TileEntityMachineMiningDrill nukeBoy;

	private int warning;
	
	public ContainerMachineMiningDrill(InventoryPlayer invPlayer, TileEntityMachineMiningDrill tedf) {
		
		this.nukeBoy = tedf;

		//Battery
		addSlotToContainer(new Slot(tedf, 0, 44, 53));
		//Outputs
		addSlotToContainer(new Slot(tedf, 1, 80, 17));
		addSlotToContainer(new Slot(tedf, 2, 98, 17));
		addSlotToContainer(new Slot(tedf, 3, 116, 17));
		addSlotToContainer(new Slot(tedf, 4, 80, 35));
		addSlotToContainer(new Slot(tedf, 5, 98, 35));
		addSlotToContainer(new Slot(tedf, 6, 116, 35));
		addSlotToContainer(new Slot(tedf, 7, 80, 53));
		addSlotToContainer(new Slot(tedf, 8, 98, 53));
		addSlotToContainer(new Slot(tedf, 9, 116, 53));
		//Upgrades
		addSlotToContainer(new Slot(tedf, 10, 152, 17));
		addSlotToContainer(new Slot(tedf, 11, 152, 35));
		addSlotToContainer(new Slot(tedf, 12, 152, 53));
		
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
		
		detectAndSendChanges();
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
			
            if (par2 <= 12) {
				if (!mergeItemStack(var5, 13, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (!mergeItemStack(var5, 0, 13, false))
			{
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
		return this.nukeBoy.isUseableByPlayer(player);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for (Object element : this.crafters) {
			ICrafting par1 = (ICrafting) element;

			if(this.warning != this.nukeBoy.warning) {
				par1.sendProgressBarUpdate(this, 1, this.nukeBoy.warning);
			}
		}

		this.warning = this.nukeBoy.warning;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 1) {
			this.nukeBoy.warning = j;
		}
	}
}
