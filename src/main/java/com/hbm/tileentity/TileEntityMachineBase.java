package com.hbm.tileentity;

import com.hbm.blocks.ModBlocks;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.BufPacket;
import com.hbm.packet.NBTPacket;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidTank;

@SuppressWarnings("deprecation")
public abstract class TileEntityMachineBase extends TileEntityLoadedBase implements ISidedInventory, INBTPacketReceiver, IBufPacketReceiver {

	public ItemStack slots[];
	
	private String customName;
	
	public TileEntityMachineBase(int scount) {
		this.slots = new ItemStack[scount];
	}
	
	/** The "chunks is modified, pls don't forget to save me" effect of markDirty, minus the block updates */
	public void markChanged() {
		this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
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

	@Override
	public String getInventoryName() {
		return hasCustomInventoryName() ? this.customName : getName();
	}
	
	public abstract String getName();

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
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this) {
			return false;
		} else {
			return player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 128;
		}
	}
	
	@Override
	public void openInventory() {}
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
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
	public boolean canInsertItem(int slot, ItemStack itemStack, int side) {
		return isItemValidForSlot(slot, itemStack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { };
	}
	
	public int getGaugeScaled(int i, FluidTank tank) {
		return tank.getFluidAmount() * i / tank.getCapacity();
	}

	//abstracting this method forces child classes to implement it
	//so i don't have to remember the fucking method name
	//was it update? onUpdate? updateTile? did it have any args?
	//shit i don't know man
	@Override
	public abstract void updateEntity();
	
	@Deprecated public void updateGauge(int val, int id, int range) {
		if(!worldObj.isRemote) PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(xCoord, yCoord, zCoord, val, id), new TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, range));
	}
	@Deprecated public void processGauge(int val, int id) { }
	
	@Deprecated public void networkPack(NBTTagCompound nbt, int range) {
		if(!worldObj.isRemote) PacketDispatcher.wrapper.sendToAllAround(new NBTPacket(nbt, xCoord, yCoord, zCoord), new TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, range));
	}
	@Override
	@Deprecated public void networkUnpack(NBTTagCompound nbt) { }
	
	/** Sends a sync packet that uses ByteBuf for efficient information-cramming */
	public void networkPackNT(int range) {
		if(!worldObj.isRemote) PacketDispatcher.wrapper.sendToAllAround(new BufPacket(xCoord, yCoord, zCoord, this), new TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, range));
	}

	@Override public void serialize(ByteBuf buf) { }
	@Override public void deserialize(ByteBuf buf) { }
	
	@Deprecated
	public void handleButtonPacket(int value, int meta) { }
	
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
	
	public int countMufflers() {
		
		int count = 0;
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			if(this.worldObj.getBlock(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ) == ModBlocks.muffler)
				count++;
		
		return count;
	}
	
	public float getVolume(int toSilence) {
		
		float volume = 1 - (countMufflers() / (float)toSilence);
		
		return Math.max(volume, 0);
	}
}
