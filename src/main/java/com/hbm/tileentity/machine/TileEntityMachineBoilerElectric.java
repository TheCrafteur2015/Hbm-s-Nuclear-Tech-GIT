package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineBoiler;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.container.ContainerMachineBoilerElectric;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineBoilerElectric;
import com.hbm.inventory.recipes.MachineRecipes;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyUser;
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
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineBoilerElectric extends TileEntityLoadedBase implements ISidedInventory, IFluidContainer, IFluidAcceptor, IFluidSource, IEnergyUser, IFluidStandardTransceiver, IGUIProvider {

	private ItemStack slots[];
	
	public long power;
	public int heat = 2000;
	public static final long maxPower = 10000;
	public static final int maxHeat = 80000;
	public int age = 0;
	public List<IFluidAcceptor> list = new ArrayList<>();
	public FluidTank[] tanks;
	
	private String customName;
	
	public TileEntityMachineBoilerElectric() {
		this.slots = new ItemStack[7];
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.OIL, 16000);
		this.tanks[1] = new FluidTank(Fluids.HOTOIL, 16000);
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
		return hasCustomInventoryName() ? this.customName : "container.machineElectricBoiler";
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
		
		if(i == 4)
			if(stack != null && stack.getItem() instanceof IBatteryItem)
				return true;
		
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

		this.heat = nbt.getInteger("heat");
		this.power = nbt.getLong("power");
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
		nbt.setLong("power", this.power);
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
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return false;
	}
	
	public int getHeatScaled(int i) {
		return (this.heat * i) / TileEntityMachineBoilerElectric.maxHeat;
	}
	
	public long getPowerScaled(int i) {
		return (this.power * i) / TileEntityMachineBoilerElectric.maxPower;
	}
	
	@Override
	public void updateEntity() {
		
		boolean mark = false;
		
		if(!this.worldObj.isRemote)
		{
			updateConnections();
			this.subscribeToAllAround(this.tanks[0].getTankType(), this);
			this.sendFluidToAll(this.tanks[1], this);
			
			this.age++;
			if(this.age >= 20)
			{
				this.age = 0;
			}
			
			if(this.age == 9 || this.age == 19)
				fillFluidInit(this.tanks[1].getTankType());

			this.power = Library.chargeTEFromItems(this.slots, 4, this.power, TileEntityMachineBoilerElectric.maxPower);
			
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
			
			if(this.heat > 2000) {
				this.heat -= 30;
			}
			
			if(this.power >= 150) {
				this.power -= 150;
				this.heat += Math.min(((double)this.power / (double)TileEntityMachineBoilerElectric.maxPower * 300), 150);
			} else {
				this.heat -= 100;
			}
			
			if(this.power <= 0 && this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) == ModBlocks.machine_boiler_electric_on) {
				this.power = 0;
				MachineBoiler.updateBlockState(false, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
				mark = true;
			}
			
			if(this.heat > TileEntityMachineBoilerElectric.maxHeat)
				this.heat = TileEntityMachineBoilerElectric.maxHeat;
			
			if(this.power > 0 && this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) == ModBlocks.machine_boiler_electric_off) {
				MachineBoiler.updateBlockState(true, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
				mark = true;
			}
			
			if(outs != null) {
				
				for(int i = 0; i < (this.heat / ((Integer)outs[3]).intValue()); i++) {
					if(this.tanks[0].getFill() >= ((Integer)outs[2]).intValue() && this.tanks[1].getFill() + ((Integer)outs[1]).intValue() <= this.tanks[1].getMaxFill()) {
						this.tanks[0].setFill(this.tanks[0].getFill() - ((Integer)outs[2]).intValue());
						this.tanks[1].setFill(this.tanks[1].getFill() + ((Integer)outs[1]).intValue());
						
						if(i == 0)
							this.heat -= 35;
						else
							this.heat -= 50;
					}
				}
			}
			
			if(this.heat < 2000) {
				this.heat = 2000;
			}

			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(this.xCoord, this.yCoord, this.zCoord, this.power), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(this.xCoord, this.yCoord, this.zCoord, this.heat, 0), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
		}
		
		if(mark) {
			markDirty();
		}
	}
	
	private void updateConnections() {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			this.trySubscribe(this.worldObj, this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ, dir);
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
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getPower() {
		return this.power;
		
	}

	@Override
	public long getMaxPower() {
		return TileEntityMachineBoilerElectric.maxPower;
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
		return new ContainerMachineBoilerElectric(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineBoilerElectric(player.inventory, this);
	}
}
