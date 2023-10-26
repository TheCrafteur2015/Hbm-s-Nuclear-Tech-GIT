package com.hbm.tileentity.machine;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.inventory.container.ContainerMachineCMBFactory;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineCMBFactory;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class TileEntityMachineCMBFactory extends TileEntityLoadedBase implements ISidedInventory, IEnergyUser, IFluidContainer, IFluidAcceptor, IFluidStandardReceiver, IGUIProvider {

	private ItemStack slots[];
	
	public long power = 0;
	public int process = 0;
	public int soundCycle = 0;
	public static final long maxPower = 100000000;
	public static final int processSpeed = 200;
	public FluidTank tank;

	private static final int[] slots_top = new int[] {1, 3};
	private static final int[] slots_bottom = new int[] {0, 2, 4};
	private static final int[] slots_side = new int[] {0, 2};
	
	private String customName;
	
	public TileEntityMachineCMBFactory() {
		this.slots = new ItemStack[6];
		this.tank = new FluidTank(Fluids.WATZ, 8000, 0);
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
		return hasCustomInventoryName() ? this.customName : "container.machineCMB";
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
		switch(i)
		{
		case 0:
			if(stack.getItem() instanceof IBatteryItem)
				return true;
			break;
		case 1:
			if(stack.getItem() == ModItems.ingot_magnetized_tungsten || stack.getItem() == ModItems.powder_magnetized_tungsten)
				return true;
			break;
		case 2:
			if(stack.getItem() == ModItems.bucket_mud)
				return true;
			break;
		case 3:
			if(stack.getItem() == ModItems.ingot_advanced_alloy || stack.getItem() == ModItems.powder_advanced_alloy)
				return true;
			break;
		}
		
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
		
		this.power = nbt.getLong("power");
		this.tank.readFromNBT(nbt, "watz");
		this.process = nbt.getShort("process");
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
		nbt.setLong("power", this.power);
		this.tank.writeToNBT(nbt, "watz");
		nbt.setShort("process", (short) this.process);
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
	public int[] getAccessibleSlotsFromSide(int p_94128_1_)
    {
        return p_94128_1_ == 0 ? TileEntityMachineCMBFactory.slots_bottom : (p_94128_1_ == 1 ? TileEntityMachineCMBFactory.slots_top : TileEntityMachineCMBFactory.slots_side);
    }

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		if(i == 4)
			return true;
		if(i == 0)
			if (itemStack.getItem() instanceof IBatteryItem && ((IBatteryItem)itemStack.getItem()).getCharge(itemStack) == 0)
				return true;
		if(i == 2)
			if(itemStack.getItem() == Items.bucket)
				return true;
		
		return false;
	}
	
	public long getPowerScaled(long i) {
		return (this.power * i) / TileEntityMachineCMBFactory.maxPower;
	}
	
	public int getProgressScaled(int i) {
		return (this.process * i) / TileEntityMachineCMBFactory.processSpeed;
	}
	
	public boolean canProcess() {
		
		boolean b = false;
		
		if(this.tank.getFill() >= 1 && this.power >= 100000 && this.slots[1] != null && this.slots[3] != null && (this.slots[4] == null || this.slots[4].stackSize <= 60))
		{
			boolean flag0 = this.slots[1].getItem() == ModItems.ingot_magnetized_tungsten || this.slots[1].getItem() == ModItems.powder_magnetized_tungsten;
			boolean flag1 = this.slots[3].getItem() == ModItems.ingot_advanced_alloy || this.slots[3].getItem() == ModItems.powder_advanced_alloy;
			
			b = flag0 && flag1;
		}
		
		return  b;
	}
	
	public boolean isProcessing() {
		return this.process > 0;
	}
	
	public void process() {
		this.tank.setFill(this.tank.getFill() - 1);
		this.power -= 100000;
		
		this.process++;
		
		if(this.process >= TileEntityMachineCMBFactory.processSpeed) {
			
			this.slots[1].stackSize--;
			if (this.slots[1].stackSize == 0) {
				this.slots[1] = null;
			}

			this.slots[3].stackSize--;
			if (this.slots[3].stackSize == 0) {
				this.slots[3] = null;
			}
			
			if(this.slots[4] == null)
			{
				this.slots[4] = new ItemStack(ModItems.ingot_combine_steel, 4);
			} else {
				
				this.slots[4].stackSize += 4;
			}
			
			this.process = 0;
		}
	}
	
	@Override
	public void updateEntity() {

		if (!this.worldObj.isRemote) {
			
			updateConnections();
			
			this.power = Library.chargeTEFromItems(this.slots, 0, this.power, TileEntityMachineCMBFactory.maxPower);
			
			this.tank.loadTank(2, 5, this.slots);
			this.tank.updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);

			if (canProcess()) {
				process();
				if(this.soundCycle == 0)
			        this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "minecart.base", 1.0F, 1.5F);
				this.soundCycle++;
					
				if(this.soundCycle >= 25)
					this.soundCycle = 0;
			} else {
				this.process = 0;
			}

			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(this.xCoord, this.yCoord, this.zCoord, this.power), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
		}
	}
	
	private void updateConnections() {
		this.updateStandardConnections(this.worldObj, this);
		this.subscribeToAllAround(this.tank.getTankType(), this);
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
		return TileEntityMachineCMBFactory.maxPower;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		this.tank.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		this.tank.setTankType(type);
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		return type.name().equals(this.tank.getTankType().name()) ? this.tank.getMaxFill() : 0;
	}

	@Override
	public int getFluidFill(FluidType type) {
		return type.name().equals(this.tank.getTankType().name()) ? this.tank.getFill() : 0;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type.name().equals(this.tank.getTankType().name()))
			this.tank.setFill(i);
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { this.tank };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { this.tank };
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineCMBFactory(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineCMBFactory(player.inventory, this);
	}
}
