package com.hbm.inventory.transfer;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

public class TransferSourceTileEntity extends TransferSourceSided {
	
	protected TileEntity tile;
	
	public TransferSourceTileEntity fromTile(TileEntity tile) {
		this.tile = tile;
		return this;
	}

	@Override
	public List<ItemStack> offer() {
		
		List<ItemStack> list = new ArrayList<>();
		
		if(this.tile instanceof ISidedInventory) {
			ISidedInventory inventory = (ISidedInventory) this.tile;
			int[] access = TransferSourceTileEntity.masquerade(inventory, this.fromSide.ordinal());
			
			for(int i : access) {
				ItemStack stack = inventory.getStackInSlot(i);
				
				if(stack != null && inventory.canExtractItem(i, stack, this.fromSide.ordinal())) {
					list.add(stack.copy());
				}
			}
			
			return list;
		}
		
		if(this.tile instanceof IInventory) {
			IInventory inventory = (IInventory) this.tile;
			
			for(int i = 0; i < inventory.getSizeInventory(); i++) {
				ItemStack stack = inventory.getStackInSlot(i);
				
				if(stack != null) {
					list.add(stack.copy());
				}
			}
			
			return list;
		}
		
		return list;
	}
	
	public static int[] masquerade(ISidedInventory sided, int side) {
		
		if(sided instanceof TileEntityFurnace) {
			return new int[] {2};
		}
		
		return sided.getAccessibleSlotsFromSide(side);
	}

	@Override
	public void remove(List<ItemStack> toRem) {
	}
}
