package com.hbm.tileentity.machine.rbmk;

import com.hbm.packet.NBTPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fluids.FluidTank;

/**
 * Base class for RBMK components that have GUI slots and thus have to handle
 * those things Yes it's a copy pasted MachineBase class, thank the lack of
 * multiple inheritance for that
 * 
 * @author hbm
 *
 */
public abstract class TileEntityRBMKSlottedBase extends TileEntityRBMKActiveBase implements ISidedInventory, IGUIProvider {

	public ItemStack slots[];

	private String customName;

	public TileEntityRBMKSlottedBase(int scount) {
		this.slots = new ItemStack[scount];
	}

	@Override
	public int getSizeInventory() {
		return this.slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return this.slots[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if(this.slots[i] != null) {
			ItemStack itemStack = this.slots[i];
			this.slots[i] = null;
			return itemStack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		this.slots[i] = itemStack;
		if(itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return hasCustomInventoryName() ? this.customName : getName();
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}

	public void setCustomName(String name) {
		this.customName = name;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return false;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if(this.slots[slot] != null) {
			if(this.slots[slot].stackSize <= amount) {
				ItemStack itemStack = this.slots[slot];
				this.slots[slot] = null;
				return itemStack;
			}
			ItemStack itemStack1 = this.slots[slot].splitStack(amount);
			if(this.slots[slot].stackSize == 0) {
				this.slots[slot] = null;
			}

			return itemStack1;
		} else {
			return null;
		}
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[] {};
	}

	public int getGaugeScaled(int i, FluidTank tank) {
		return tank.getFluidAmount() * i / tank.getCapacity();
	}

	@Override
	public void networkPack(NBTTagCompound nbt, int range) {

		if(!this.worldObj.isRemote)
			PacketDispatcher.wrapper.sendToAllAround(new NBTPacket(nbt, this.xCoord, this.yCoord, this.zCoord), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, range));
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		super.networkUnpack(nbt);
	}

	public void handleButtonPacket(int value, int meta) {
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		if(!TileEntityRBMKBase.diag) {
			NBTTagList list = nbt.getTagList("items", 10);
	
			for(int i = 0; i < list.tagCount(); i++) {
				NBTTagCompound nbt1 = list.getCompoundTagAt(i);
				byte b0 = nbt1.getByte("slot");
				if(b0 >= 0 && b0 < this.slots.length) {
					this.slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
				}
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		if(!TileEntityRBMKBase.diag) {
			NBTTagList list = new NBTTagList();
	
			for(int i = 0; i < this.slots.length; i++) {
				if(this.slots[i] != null) {
					NBTTagCompound nbt1 = new NBTTagCompound();
					nbt1.setByte("slot", (byte) i);
					this.slots[i].writeToNBT(nbt1);
					list.appendTag(nbt1);
				}
			}
			nbt.setTag("items", list);
		}
	}
}
