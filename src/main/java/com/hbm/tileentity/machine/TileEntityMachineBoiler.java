package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineBoiler;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.container.ContainerMachineBoiler;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineBoiler;
import com.hbm.inventory.recipes.MachineRecipes;
import com.hbm.lib.Library;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;

public class TileEntityMachineBoiler extends TileEntityLoadedBase implements ISidedInventory, IFluidContainer, IFluidAcceptor, IFluidSource, IFluidStandardTransceiver, IGUIProvider {

	private ItemStack slots[];
	
	public int burnTime;
	public int heat = 2000;
	public static final int maxHeat = 50000;
	public int age = 0;
	public List<IFluidAcceptor> list = new ArrayList<>();
	public FluidTank[] tanks;
	
	private String customName;
	
	public TileEntityMachineBoiler() {
		this.slots = new ItemStack[7];
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.OIL, 8000);
		this.tanks[1] = new FluidTank(Fluids.HOTOIL, 8000);
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
		return hasCustomInventoryName() ? this.customName : "container.machineBoiler";
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
	
	//You scrubs aren't needed for anything (right now)
	@Override
	public void openInventory() {}
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return i == 4 && TileEntityFurnace.getItemBurnTime(stack) > 0;
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

		this.heat = nbt.getInteger("heat");
		this.burnTime = nbt.getInteger("burnTime");
		this.tanks[0].readFromNBT(nbt, "water");
		this.tanks[1].readFromNBT(nbt, "steam");
		this.slots = new ItemStack[getSizeInventory()];
		
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
		nbt.setInteger("heat", this.heat);
		nbt.setInteger("burnTime", this.burnTime);
		this.tanks[0].writeToNBT(nbt, "water");
		this.tanks[1].writeToNBT(nbt, "steam");
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
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 4 };
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 4 && !isItemValidForSlot(i, itemStack);
	}
	
	public int getHeatScaled(int i) {
		return (this.heat * i) / TileEntityMachineBoiler.maxHeat;
	}
	
	@Override
	public void updateEntity() {
		
		boolean mark = false;
		
		if(!this.worldObj.isRemote) {
			
			this.subscribeToAllAround(this.tanks[0].getTankType(), this);
			this.sendFluidToAll(this.tanks[1], this);
			
			this.age++;
			if(this.age >= 20)
			{
				this.age = 0;
			}
			
			if(this.age == 9 || this.age == 19)
				fillFluidInit(this.tanks[1].getTankType());

			this.tanks[0].setType(0, 1, this.slots);
			this.tanks[0].loadTank(2, 3, this.slots);
			
			Object[] outs = MachineRecipes.getBoilerOutput(this.tanks[0].getTankType());
			
			if(outs == null) {
				this.tanks[1].setTankType(Fluids.NONE);
			} else {
				this.tanks[1].setTankType((FluidType) outs[0]);
			}
			
			this.tanks[1].unloadTank(5, 6, this.slots);
			
			for(int i = 0; i < 2; i++)
				this.tanks[i].updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
			
			boolean flag1 = false;
			
			if(this.heat > 2000) {
				this.heat -= 15;
			}
			
			if(this.burnTime > 0) {
				this.burnTime--;
				if(this.worldObj.getTotalWorldTime() % 20 == 0) PollutionHandler.incrementPollution(this.worldObj, this.xCoord, this.yCoord, this.zCoord, PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND);
				this.heat += 50;
				flag1 = true;
			}
			
			if(this.burnTime == 0 && flag1) {
				mark = true;
			}
			
			if(this.burnTime <= 0 && this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) == ModBlocks.machine_boiler_on)
				MachineBoiler.updateBlockState(false, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			
			if(this.heat > TileEntityMachineBoiler.maxHeat)
				this.heat = TileEntityMachineBoiler.maxHeat;
			
			if(this.burnTime == 0 && TileEntityFurnace.getItemBurnTime(this.slots[4]) > 0) {
				this.burnTime = (int) (TileEntityFurnace.getItemBurnTime(this.slots[4]) * 0.25);
				this.slots[4].stackSize--;
				
				if(this.slots[4].stackSize <= 0) {
					
					if(this.slots[4].getItem().getContainerItem() != null)
						this.slots[4] = new ItemStack(this.slots[4].getItem().getContainerItem());
					else
						this.slots[4] = null;
				}
				
				if(!flag1) {
					mark = true;
				}
			}
			
			if(this.burnTime > 0 && this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) == ModBlocks.machine_boiler_off)
				MachineBoiler.updateBlockState(true, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			
			if(outs != null) {
				
				for(int i = 0; i < (this.heat / ((Integer)outs[3]).intValue()); i++) {
					if(this.tanks[0].getFill() >= ((Integer)outs[2]).intValue() && this.tanks[1].getFill() + ((Integer)outs[1]).intValue() <= this.tanks[1].getMaxFill()) {
						this.tanks[0].setFill(this.tanks[0].getFill() - ((Integer)outs[2]).intValue());
						this.tanks[1].setFill(this.tanks[1].getFill() + ((Integer)outs[1]).intValue());
						
						if(i == 0)
							this.heat -= 25;
						else
							this.heat -= 40;
					}
				}
			}
			
			if(this.heat < 2000) {
				this.heat = 2000;
			}

			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(this.xCoord, this.yCoord, this.zCoord, this.heat, 0), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(this.xCoord, this.yCoord, this.zCoord, this.burnTime, 1), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
		}
		
		if(mark) {
			markDirty();
		}
	}
	
	public boolean isItemValid() {

		if(this.slots[1] != null && TileEntityFurnace.getItemBurnTime(this.slots[1]) > 0)
		{
			return true;
		}
		
		return false;
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
		if(this.age >= 0 && this.age < 10)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type.getName().equals(this.tanks[0].getTankType().getName()))
			this.tanks[0].setFill(i);
		else if(type.getName().equals(this.tanks[1].getTankType().getName()))
			this.tanks[1].setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type.getName().equals(this.tanks[0].getTankType().getName()))
			return this.tanks[0].getFill();
		else if(type.getName().equals(this.tanks[1].getTankType().getName()))
			return this.tanks[1].getFill();
		
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type.getName().equals(this.tanks[0].getTankType().getName()))
			return this.tanks[0].getMaxFill();
		
		return 0;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if(index < 2 && this.tanks[index] != null)
			this.tanks[index].setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if(index < 2 && this.tanks[index] != null)
			this.tanks[index].setTankType(type);
	}
	
	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return this.list;
	}
	
	@Override
	public void clearFluidList(FluidType type) {
		this.list.clear();
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {this.tanks[1]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {this.tanks[0]};
	}

	@Override
	public FluidTank[] getAllTanks() {
		return this.tanks;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineBoiler(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineBoiler(player.inventory, this);
	}
}
