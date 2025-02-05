package com.hbm.tileentity.network;

import com.hbm.packet.NBTPacket;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * "Multiple inheritance is bad because...uhhhh...i guess if you do it wrong then it can lead to bad things"
 * ~ genuinely retarded people on StackOverflow
 * like yeah, doing things wrong can lead to bad things
 * no shit
 * just like how java operates already
 * you fucking dork
 * 
 * this class has to extend TileEntityRequestNetwork for all the network stuff to work
 * but it also needs slots and all the container boilerplate crap
 * since multiple inheritance is a sin punishable by stoning, i had to cram the entire contents of TileEntityMachineBase into this class
 * is this good code? is this what you wanted? was it worth avoiding those hypothetical scenarios where multiple inheritance is le bad?
 * i believe that neither heaven nor hell awaits me when all is said and done
 * saint peter will send me to southend
 * 
 * @author hbm
 */
public abstract class TileEntityRequestNetworkContainer extends TileEntityRequestNetwork implements ISidedInventory {

	public ItemStack slots[];
	
	private String customName;
	
	public TileEntityRequestNetworkContainer(int scount) {
		this.slots = new ItemStack[scount];
	}

	@Override public int getSizeInventory() { return this.slots.length; }
	@Override public ItemStack getStackInSlot(int i) { return this.slots[i]; }
	@Override public void openInventory() { }
	@Override public void closeInventory() { }
	@Override public boolean isItemValidForSlot(int slot, ItemStack itemStack) { return false; }
	@Override public boolean canInsertItem(int slot, ItemStack itemStack, int side) { return isItemValidForSlot(slot, itemStack); }
	@Override public boolean canExtractItem(int slot, ItemStack itemStack, int side) { return false; }
	@Override public int[] getAccessibleSlotsFromSide(int side) { return new int[] { }; }
	
	public void markChanged() {
		this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if(this.slots[i] != null)
		{
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
		if(itemStack != null && itemStack.stackSize > getInventoryStackLimit())
		{
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	@Override public String getInventoryName() { return hasCustomInventoryName() ? this.customName : getName(); }
	public abstract String getName();
	@Override public boolean hasCustomInventoryName() { return this.customName != null && this.customName.length() > 0; }
	public void setCustomName(String name) { this.customName = name; }
	@Override public int getInventoryStackLimit() { return 64; }

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this) {
			return false;
		} else {
			return player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 128;
		}
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
	
	public void networkPack(NBTTagCompound nbt, int range) {
		if(!this.worldObj.isRemote) PacketDispatcher.wrapper.sendToAllAround(new NBTPacket(nbt, this.xCoord, this.yCoord, this.zCoord), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, range));
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);
		
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if(b0 >= 0 && b0 < this.slots.length)
			{
				this.slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagList list = new NBTTagList();
		
		for(int i = 0; i < this.slots.length; i++)
		{
			if(this.slots[i] != null)
			{
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte)i);
				this.slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}
}
