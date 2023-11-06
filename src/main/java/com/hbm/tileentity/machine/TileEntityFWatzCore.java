package com.hbm.tileentity.machine;

import java.util.Random;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.IReactor;
import com.hbm.inventory.container.ContainerFWatzCore;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIFWatzCore;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.fauxpointtwelve.DirPos;
import com.hbm.world.machine.FWatz;

import api.hbm.energy.IEnergyGenerator;
import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class TileEntityFWatzCore extends TileEntityLoadedBase implements ISidedInventory, IReactor, IEnergyGenerator, IFluidContainer, IFluidAcceptor, IFluidStandardReceiver, IGUIProvider {

	public long power;
	public final static long maxPower = 10000000000L;
	public boolean cooldown = false;

	public FluidTank tanks[];
	
	Random rand = new Random();
	
	private ItemStack slots[];
	
	private String customName;

	public TileEntityFWatzCore() {
		this.slots = new ItemStack[7];
		this.tanks = new FluidTank[3];
		this.tanks[0] = new FluidTank(Fluids.COOLANT, 128000);
		this.tanks[1] = new FluidTank(Fluids.AMAT, 64000);
		this.tanks[2] = new FluidTank(Fluids.ASCHRAB, 64000);
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
		return hasCustomInventoryName() ? this.customName : "container.fusionaryWatzPlant";
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
			return true;
		}
	}
	
	@Override
	public void openInventory() {}
	
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return true;
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
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return null;
	}

	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
		return false;
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
		return false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);

		this.power = nbt.getLong("power");
		this.tanks[0].readFromNBT(nbt, "cool");
		this.tanks[1].readFromNBT(nbt, "amat");
		this.tanks[2].readFromNBT(nbt, "aschrab");
		
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
		this.tanks[0].writeToNBT(nbt, "cool");
		this.tanks[1].writeToNBT(nbt, "amat");
		this.tanks[2].writeToNBT(nbt, "aschrab");
		
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
	public boolean isStructureValid(World world) {
		return FWatz.checkHull(world, this.xCoord, this.yCoord, this.zCoord);
	}

	@Override
	public boolean isCoatingValid(World world) {
		{
			return true;
		}
	}

	@Override
	public boolean hasFuse() {
		return this.slots[1] != null && (this.slots[1].getItem() == ModItems.fuse || this.slots[1].getItem() == ModItems.screwdriver);
	}
	
	@Override
	public int getCoolantScaled(int i) {
		return 0;
	}
	
	@Override
	public long getPowerScaled(long i) {
		return (this.power/100 * i) / (TileEntityFWatzCore.maxPower/100);
	}
	
	@Override
	public int getWaterScaled(int i) {
		return 0;
	}
	
	@Override
	public int getHeatScaled(int i) {
		return 0;
	}
	
	public int getSingularityType() {
		
		if(this.slots[2] != null) {
			Item item = this.slots[2].getItem();

			if(item == ModItems.singularity)
				return 1;
			if(item == ModItems.singularity_counter_resonant)
				return 2;
			if(item == ModItems.singularity_super_heated)
				return 3;
			if(item == ModItems.black_hole)
				return 4;
			if(item == ModItems.overfuse)
				return 5;
		}
		
		return 0;
	}
	
	public DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(this.xCoord + 10, this.yCoord - 11, this.zCoord, Library.POS_X),
				new DirPos(this.xCoord - 10, this.yCoord - 11, this.zCoord, Library.NEG_X),
				new DirPos(this.xCoord, this.yCoord - 11, this.zCoord + 10, Library.POS_Z),
				new DirPos(this.xCoord, this.yCoord - 11, this.zCoord - 10, Library.NEG_Z)
		};
	}

	@Override
	public void updateEntity() {
		if(!this.worldObj.isRemote && isStructureValid(this.worldObj)) {

			for(DirPos pos : getConPos()) {
				sendPower(this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());

				this.trySubscribe(this.tanks[1].getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				this.trySubscribe(this.tanks[2].getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}

			if (hasFuse() && getSingularityType() > 0) {
				if(this.cooldown) {
					
					int i = getSingularityType();

					if(i == 1)
						this.tanks[0].setFill(this.tanks[0].getFill() + 1500);
					if(i == 2)
						this.tanks[0].setFill(this.tanks[0].getFill() + 3000);
					if(i == 3)
						this.tanks[0].setFill(this.tanks[0].getFill() + 750);
					if(i == 4)
						this.tanks[0].setFill(this.tanks[0].getFill() + 7500);
					if(i == 5)
						this.tanks[0].setFill(this.tanks[0].getFill() + 15000);
					
					if(this.tanks[0].getFill() >= this.tanks[0].getMaxFill()) {
						this.cooldown = false;
						this.tanks[0].setFill(this.tanks[0].getMaxFill());
					}
					
				} else {
					int i = getSingularityType();
					
					if(i == 1 && this.tanks[1].getFill() - 75 >= 0 && this.tanks[2].getFill() - 75 >= 0) {
						this.tanks[0].setFill(this.tanks[0].getFill() - 150);
						this.tanks[1].setFill(this.tanks[1].getFill() - 75);
						this.tanks[2].setFill(this.tanks[2].getFill() - 75);
						this.power += 5000000;
					}
					if(i == 2 && this.tanks[1].getFill() - 75 >= 0 && this.tanks[2].getFill() - 35 >= 0) {
						this.tanks[0].setFill(this.tanks[0].getFill() - 75);
						this.tanks[1].setFill(this.tanks[1].getFill() - 35);
						this.tanks[2].setFill(this.tanks[2].getFill() - 30);
						this.power += 2500000;
					}
					if(i == 3 && this.tanks[1].getFill() - 75 >= 0 && this.tanks[2].getFill() - 140 >= 0) {
						this.tanks[0].setFill(this.tanks[0].getFill() - 300);
						this.tanks[1].setFill(this.tanks[1].getFill() - 75);
						this.tanks[2].setFill(this.tanks[2].getFill() - 140);
						this.power += 10000000;
					}
					if(i == 4 && this.tanks[1].getFill() - 100 >= 0 && this.tanks[2].getFill() - 100 >= 0) {
						this.tanks[0].setFill(this.tanks[0].getFill() - 100);
						this.tanks[1].setFill(this.tanks[1].getFill() - 100);
						this.tanks[2].setFill(this.tanks[2].getFill() - 100);
						this.power += 10000000;
					}
					if(i == 5 && this.tanks[1].getFill() - 15 >= 0 && this.tanks[2].getFill() - 15 >= 0) {
						this.tanks[0].setFill(this.tanks[0].getFill() - 150);
						this.tanks[1].setFill(this.tanks[1].getFill() - 15);
						this.tanks[2].setFill(this.tanks[2].getFill() - 15);
						this.power += 100000000;
					}
					
					if(this.power > TileEntityFWatzCore.maxPower)
						this.power = TileEntityFWatzCore.maxPower;
					
					if(this.tanks[0].getFill() <= 0) {
						this.cooldown = true;
						this.tanks[0].setFill(0);
					}
				}
			}
			
			if(this.power > TileEntityFWatzCore.maxPower)
				this.power = TileEntityFWatzCore.maxPower;
			
			this.power = Library.chargeItemsFromTE(this.slots, 0, this.power, TileEntityFWatzCore.maxPower);
			
			this.tanks[1].loadTank(3, 5, this.slots);
			this.tanks[2].loadTank(4, 6, this.slots);
			
			for(int i = 0; i < 3; i++)
				this.tanks[i].updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
		}
		
		if(isRunning() && (this.tanks[1].getFill() <= 0 || this.tanks[2].getFill() <= 0 || !hasFuse() || getSingularityType() == 0) || this.cooldown || !isStructureValid(this.worldObj))
			emptyPlasma();
		
		if(!isRunning() && this.tanks[1].getFill() >= 100 && this.tanks[2].getFill() >= 100 && hasFuse() && getSingularityType() > 0 && !this.cooldown && isStructureValid(this.worldObj))
			fillPlasma();

		if(!this.worldObj.isRemote)
			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(this.xCoord, this.yCoord, this.zCoord, this.power), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
	}
	
	public void fillPlasma() {
		if(!this.worldObj.isRemote)
			FWatz.fillPlasma(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
	}
	
	public void emptyPlasma() {
		if(!this.worldObj.isRemote)
			FWatz.emptyPlasma(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
	}
	
	public boolean isRunning() {
		return FWatz.getPlasma(this.worldObj, this.xCoord, this.yCoord, this.zCoord) && isStructureValid(this.worldObj);
	}
	
	@Override
	public long getMaxPower() {
		return TileEntityFWatzCore.maxPower;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
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
		if(type.getName().equals(this.tanks[1].getTankType().getName()))
			this.tanks[1].setFill(i);
		else if(type.getName().equals(this.tanks[2].getTankType().getName()))
			this.tanks[2].setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type.getName().equals(this.tanks[1].getTankType().getName()))
			return this.tanks[1].getFill();
		else if(type.getName().equals(this.tanks[2].getTankType().getName()))
			return this.tanks[2].getFill();
		else
			return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type.getName().equals(this.tanks[1].getTankType().getName()))
			return this.tanks[1].getMaxFill();
		else if(type.getName().equals(this.tanks[2].getTankType().getName()))
			return this.tanks[2].getMaxFill();
		else
			return 0;
	}
	
	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { this.tanks[1], this.tanks[2] };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return this.tanks;
	}
	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerFWatzCore(player.inventory, this);
	}
	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIFWatzCore(player.inventory, this);
	}
}
