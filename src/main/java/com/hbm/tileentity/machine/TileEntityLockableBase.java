package com.hbm.tileentity.machine;

import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemKey;
import com.hbm.items.tool.ItemKeyPin;
import com.hbm.main.MainRegistry;
import com.hbm.util.ArmorUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityLockableBase extends TileEntity {
	
	protected int lock;
	private boolean isLocked = false;
	protected double lockMod = 0.1D;

	public boolean isLocked() {
		return this.isLocked;
	}
	
	public void lock() {
		
		if(this.lock == 0) {
			MainRegistry.logger.error("A block has been set to locked state before setting pins, this should not happen and may cause errors! " + toString());
		}
		
		this.isLocked = true;
	}
	
	public void setPins(int pins) {
		this.lock = pins;
	}
	
	public int getPins() {
		return this.lock;
	}
	
	public void setMod(double mod) {
		this.lockMod = mod;
	}
	
	public double getMod() {
		return this.lockMod;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.lock = nbt.getInteger("lock");
		this.isLocked = nbt.getBoolean("isLocked");
		this.lockMod = nbt.getDouble("lockMod");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setInteger("lock", this.lock);
		nbt.setBoolean("isLocked", this.isLocked);
		nbt.setDouble("lockMod", this.lockMod);
	}

	public boolean canAccess(EntityPlayer player) {
		
		if(!this.isLocked) {
			return true;
		} else {
			ItemStack stack = player.getHeldItem();
			
			if(stack != null && stack.getItem() instanceof ItemKey &&
					ItemKeyPin.getPins(stack) == this.lock) {
	        	this.worldObj.playSoundAtEntity(player, "hbm:block.lockOpen", 1.0F, 1.0F);
				return true;
			}
			
			if(stack != null && stack.getItem() == ModItems.key_red) {
	        	this.worldObj.playSoundAtEntity(player, "hbm:block.lockOpen", 1.0F, 1.0F);
				return true;
			}
			
			return tryPick(player);
		}
	}
	
	private boolean tryPick(EntityPlayer player) {

		boolean canPick = false;
		ItemStack stack = player.getHeldItem();
		double chanceOfSuccess = this.lockMod * 100;
		
		if(stack != null && stack.getItem() == ModItems.pin && player.inventory.hasItem(ModItems.screwdriver)) {
			
			stack.stackSize--;
			canPick = true;
		}
		
		if(stack != null && stack.getItem() == ModItems.screwdriver && player.inventory.hasItem(ModItems.pin)) {
			
			player.inventory.consumeInventoryItem(ModItems.pin);
			player.inventoryContainer.detectAndSendChanges();
			canPick = true;
		}
		
		if(canPick) {
			
			if(ArmorUtil.checkArmorPiece(player, ModItems.jackt, 2) || ArmorUtil.checkArmorPiece(player, ModItems.jackt2, 2))
				chanceOfSuccess *= 100D;
			
			double rand = player.worldObj.rand.nextDouble() * 100;
			
			if(chanceOfSuccess > rand) {
        		this.worldObj.playSoundAtEntity(player, "hbm:item.pinUnlock", 1.0F, 1.0F);
				return true;
			}

    		this.worldObj.playSoundAtEntity(player, "hbm:item.pinBreak", 1.0F, 0.8F + player.worldObj.rand.nextFloat() * 0.2F);
		}
		
		return false;
	}

}
