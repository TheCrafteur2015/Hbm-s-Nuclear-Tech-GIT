package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMachineInserter extends TileEntity implements ISidedInventory, IFluidContainer, IFluidSource, IFluidAcceptor {

	private ItemStack slots[];
	
	//public static final int maxFill = 64 * 3;
	public FluidTank tanks[];

	private static final int[] slots_top = new int[] {0};
	private static final int[] slots_bottom = new int[] {0};
	private static final int[] slots_side = new int[] {0};
	public int age = 0;
	public List<IFluidAcceptor> list1 = new ArrayList<>();
	public List<IFluidAcceptor> list2 = new ArrayList<>();
	public List<IFluidAcceptor> list3 = new ArrayList<>();
	
	private String customName;
	
	public TileEntityMachineInserter() {
		this.slots = new ItemStack[9];
		this.tanks = new FluidTank[3];
		this.tanks[0] = new FluidTank(Fluids.NONE, 32000, 0);
		this.tanks[1] = new FluidTank(Fluids.NONE, 32000, 0);
		this.tanks[2] = new FluidTank(Fluids.NONE, 32000, 0);
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
		return hasCustomInventoryName() ? this.customName : "container.inserter";
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
		{
			return false;
		}else{
			return player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <=64;
		}
	}
	
	@Override
	public void openInventory() {}
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return false;
	}
	
	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(this.slots[i] != null)
		{
			if(this.slots[i].stackSize <= j)
			{
				ItemStack itemStack = this.slots[i];
				this.slots[i] = null;
				return itemStack;
			}
			ItemStack itemStack1 = this.slots[i].splitStack(j);
			if (this.slots[i].stackSize == 0)
			{
				this.slots[i] = null;
			}
			
			return itemStack1;
		} else {
			return null;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);
		
		this.slots = new ItemStack[getSizeInventory()];

		this.tanks[0].readFromNBT(nbt, "content1");
		this.tanks[1].readFromNBT(nbt, "content2");
		this.tanks[2].readFromNBT(nbt, "content3");
		
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

		this.tanks[0].writeToNBT(nbt, "content1");
		this.tanks[1].writeToNBT(nbt, "content2");
		this.tanks[2].writeToNBT(nbt, "content3");
		
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
	
	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_)
    {
        return p_94128_1_ == 0 ? TileEntityMachineInserter.slots_bottom : (p_94128_1_ == 1 ? TileEntityMachineInserter.slots_top : TileEntityMachineInserter.slots_side);
    }

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return false;
	}
	
	@Override
	public void updateEntity() {

		if(!this.worldObj.isRemote)
		{
			this.age++;
			if(this.age >= 20)
			{
				this.age = 0;
			}
			
			if(this.age == 9 || this.age == 19) {
				if(dna1())
					fillFluidInit(this.tanks[0].getTankType());
				if(dna2())
					fillFluidInit(this.tanks[1].getTankType());
				if(dna3())
					fillFluidInit(this.tanks[2].getTankType());
			}

			this.tanks[0].setType(1, 2, this.slots);
			this.tanks[1].setType(4, 5, this.slots);
			this.tanks[2].setType(7, 8, this.slots);
			this.tanks[0].updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
			this.tanks[1].updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
			this.tanks[2].updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
		}
	}
	
	public boolean dna1() {
		if(this.slots[0] != null && (this.slots[0].getItem() == ModItems.fuse || this.slots[0].getItem() == ModItems.screwdriver))
			return true;
		return false;
	}
	
	public boolean dna2() {
		if(this.slots[3] != null && (this.slots[3].getItem() == ModItems.fuse || this.slots[3].getItem() == ModItems.screwdriver))
			return true;
		return false;
	}
	
	public boolean dna3() {
		if(this.slots[6] != null && (this.slots[6].getItem() == ModItems.fuse || this.slots[6].getItem() == ModItems.screwdriver))
			return true;
		return false;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord + 1, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord - 1, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord + 1, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord - 1, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, this.worldObj, type);
	}

	@Override
	public boolean getTact() {
		if (this.age >= 0 && this.age < 10) {
			return true;
		}

		return false;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if(index < 3 && this.tanks[index] != null)
			this.tanks[index].setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if(index < 3 && this.tanks[index] != null)
			this.tanks[index].setTankType(type);
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type.name().equals(this.tanks[0].getTankType().name()))
			this.tanks[0].setFill(i);
		else if(type.name().equals(this.tanks[1].getTankType().name()))
			this.tanks[1].setFill(i);
		else if(type.name().equals(this.tanks[2].getTankType().name()))
			this.tanks[2].setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type.name().equals(this.tanks[0].getTankType().name()))
			return this.tanks[0].getFill();
		else if(type.name().equals(this.tanks[1].getTankType().name()))
			return this.tanks[1].getFill();
		else if(type.name().equals(this.tanks[2].getTankType().name()))
			return this.tanks[2].getFill();
		else
			return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type.name().equals(this.tanks[0].getTankType().name()))
			return this.tanks[0].getMaxFill();
		else if(type.name().equals(this.tanks[1].getTankType().name()))
			return this.tanks[1].getMaxFill();
		else if(type.name().equals(this.tanks[2].getTankType().name()))
			return this.tanks[2].getMaxFill();
		else
			return 0;
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		if(type.name().equals(this.tanks[0].getTankType().name()))
			return this.list1;
		if(type.name().equals(this.tanks[1].getTankType().name()))
			return this.list2;
		if(type.name().equals(this.tanks[2].getTankType().name()))
			return this.list3;
		return new ArrayList<>();
	}

	@Override
	public void clearFluidList(FluidType type) {
		if(type.name().equals(this.tanks[0].getTankType().name()))
			this.list1.clear();
		if(type.name().equals(this.tanks[1].getTankType().name()))
			this.list2.clear();
		if(type.name().equals(this.tanks[2].getTankType().name()))
			this.list3.clear();
	}
}
