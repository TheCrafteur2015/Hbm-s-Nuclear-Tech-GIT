package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityAMSBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAMSBase extends Container {

private TileEntityAMSBase amsBase;

	private int heat;
	private int warning;
	private int mode;
	
	public ContainerAMSBase(InventoryPlayer invPlayer, TileEntityAMSBase tedf) {
		this.amsBase = tedf;

		//Cool 1 In
		addSlotToContainer(new Slot(tedf, 0, 8, 18));
		//Cool 1 Out
		addSlotToContainer(new Slot(tedf, 1, 8, 54));
		//Cool 2 In
		addSlotToContainer(new Slot(tedf, 2, 152, 18));
		//Cool 2 Out
		addSlotToContainer(new Slot(tedf, 3, 152, 54));
		//Fuel 1 In
		addSlotToContainer(new Slot(tedf, 4, 8, 72));
		//Fuel 1 Out
		addSlotToContainer(new Slot(tedf, 5, 8, 108));
		//Fuel 2 In
		addSlotToContainer(new Slot(tedf, 6, 152, 72));
		//Fuel 2 Out
		addSlotToContainer(new Slot(tedf, 7, 152, 108));
		//Moderator
		addSlotToContainer(new Slot(tedf, 8, 80, 45));
		addSlotToContainer(new Slot(tedf, 9, 62, 63));
		addSlotToContainer(new Slot(tedf, 10, 98, 63));
		addSlotToContainer(new Slot(tedf, 11, 80, 81));
		//Core
		addSlotToContainer(new Slot(tedf, 12, 80, 63));
		//Sat Chips
		addSlotToContainer(new Slot(tedf, 13, 62, 108));
		addSlotToContainer(new Slot(tedf, 14, 62 + 18, 108));
		addSlotToContainer(new Slot(tedf, 15, 62 + 36, 108));
		
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
			
            if (par2 <= 3) {
				if (!mergeItemStack(var5, 4, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else
				return null;
			
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
		return this.amsBase.isUseableByPlayer(player);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for (Object element : this.crafters) {
			ICrafting par1 = (ICrafting)element;
			
			if(this.heat != this.amsBase.heat)
			{
				par1.sendProgressBarUpdate(this, 0, this.amsBase.heat);
			}
			
			if(this.warning != this.amsBase.warning)
			{
				par1.sendProgressBarUpdate(this, 2, this.amsBase.warning);
			}
			
			if(this.mode != this.amsBase.mode)
			{
				par1.sendProgressBarUpdate(this, 4, this.amsBase.mode);
			}
		}

		this.heat = this.amsBase.heat;
		this.warning = this.amsBase.warning;
		this.mode = this.amsBase.mode;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0)
		{
			this.amsBase.heat = j;
		}
		if(i == 1)
		{
			this.amsBase.efficiency = j;
		}
		if(i == 2)
		{
			this.amsBase.warning = j;
		}
		if(i == 3)
		{
			this.amsBase.field = j;
		}
		if(i == 4)
		{
			this.amsBase.mode = j;
		}
	}
}
