package com.hbm.tileentity.machine;

import java.util.Random;

import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.PacketDispatcher;

import api.hbm.energy.IBatteryItem;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

@SuppressWarnings("deprecation")
public class TileEntityMachineShredderLarge extends TileEntity implements ISidedInventory {

	private ItemStack slots[];

	public long power;
	public static final long maxPower = 100000;
	public int progress;
	public int maxProgress = 100;
	
	Random rand = new Random();
	
	private String customName;
	
	public TileEntityMachineShredderLarge() {
		this.slots = new ItemStack[31];
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
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		this.slots[i] = itemStack;
		if(itemStack != null && itemStack.stackSize > getInventoryStackLimit())
			itemStack.stackSize = getInventoryStackLimit();
	}

	@Override
	public String getInventoryName() {
		return hasCustomInventoryName() ? this.customName : "container.assembler";
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
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this)
			return false;
		else
			return player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 128;
	}
	
	//You scrubs aren't needed for anything (right now)
	@Override
	public void openInventory() {}
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		if(i == 0)
			if(itemStack.getItem() instanceof IBatteryItem)
				return true;
		if(i == 1)
			return true;
		return false;
	}
	
	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(this.slots[i] != null) {
			if(this.slots[i].stackSize <= j) {
				ItemStack itemStack = this.slots[i];
				this.slots[i] = null;
				return itemStack;
			}
			ItemStack itemStack1 = this.slots[i].splitStack(j);
			if (this.slots[i].stackSize == 0)
				this.slots[i] = null;
			return itemStack1;
		} else
			return null;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);
		
		this.power = nbt.getLong("powerTime");
		this.slots = new ItemStack[getSizeInventory()];
		
		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if(b0 >= 0 && b0 < this.slots.length)
				this.slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("powerTime", this.power);
		NBTTagList list = new NBTTagList();
		
		for(int i = 0; i < this.slots.length; i++) {
			if(this.slots[i] != null) {
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte)i);
				this.slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[] {0};
    }

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return false;
	}
	
	public long getPowerScaled(long i) {
		return (this.power * i) / TileEntityMachineShredderLarge.maxPower;
	}
	
	public int getProgressScaled(int i) {
		return (this.progress * i) / this.maxProgress;
	}
	
	@Override
	public void updateEntity() {
		if(!this.worldObj.isRemote)
			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(this.xCoord, this.yCoord, this.zCoord, this.power), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 150));
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

}
