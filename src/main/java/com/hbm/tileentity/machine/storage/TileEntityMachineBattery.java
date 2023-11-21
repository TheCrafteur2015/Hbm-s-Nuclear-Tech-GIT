package com.hbm.tileentity.machine.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineBattery;
import com.hbm.config.GeneralConfig;
import com.hbm.inventory.container.ContainerMachineBattery;
import com.hbm.inventory.gui.GUIMachineBattery;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IPersistentNBT;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyConductor;
import api.hbm.energy.IEnergyConnector;
import api.hbm.energy.IEnergyUser;
import api.hbm.energy.IPowerNet;
import api.hbm.energy.PowerNet;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")})
public class TileEntityMachineBattery extends TileEntityMachineBase implements IEnergyUser, IPersistentNBT, SimpleComponent, IGUIProvider {
	
	public long[] log = new long[20];
	public long delta = 0;
	public long power = 0;
	public long prevPowerState = 0;
	public int pingPongTicks = 0;
	
	//0: input only
	//1: buffer
	//2: output only
	//3: nothing
	public static final int mode_input = 0;
	public static final int mode_buffer = 1;
	public static final int mode_output = 2;
	public static final int mode_none = 3;
	public short redLow = 0;
	public short redHigh = 2;
	public ConnectionPriority priority = ConnectionPriority.LOW;
	
	//public boolean conducts = false;
	public byte lastRedstone = 0;
	
	private static final int[] slots_top = new int[] {0};
	private static final int[] slots_bottom = new int[] {0, 1};
	private static final int[] slots_side = new int[] {1};
	
	private String customName;
	
	private String openComputerName;
	
	public TileEntityMachineBattery() {
		this(null);
		this.openComputerName = "ntm_energy_storage";
	}
	
	public TileEntityMachineBattery(MachineBattery block) {
		super(2);
		this.slots = new ItemStack[2];
		if (block != null) {
			this.openComputerName = "ntm_battery";
			if(block == ModBlocks.machine_battery_potato)       this.openComputerName += "_potato";
			if(block == ModBlocks.machine_lithium_battery)      this.openComputerName += "_lithium";
			if(block == ModBlocks.machine_schrabidium_battery)  this.openComputerName += "_schrabidium";
			if(block == ModBlocks.machine_dineutronium_battery) this.openComputerName += "_dineutronium";
		}
	}

	@Override
	public String getName() {
		return "container.battery";
	}

	@Override
	public String getInventoryName() {
		return hasCustomInventoryName() ? this.customName : getName();
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}
	
	@Override
	public void setCustomName(String name) {
		this.customName = name;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		
		switch(i) {
		case 0:
		case 1:
			if(stack.getItem() instanceof IBatteryItem)
				return true;
			break;
		}
		
		return true;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.power = nbt.getLong("power");
		this.redLow = nbt.getShort("redLow");
		this.redHigh = nbt.getShort("redHigh");
		this.lastRedstone = nbt.getByte("lastRedstone");
		this.priority = ConnectionPriority.values()[nbt.getByte("priority")];
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", this.power);
		nbt.setShort("redLow", this.redLow);
		nbt.setShort("redHigh", this.redHigh);
		nbt.setByte("lastRedstone", this.lastRedstone);
		nbt.setByte("priority", (byte)this.priority.ordinal());
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return p_94128_1_ == 0 ? TileEntityMachineBattery.slots_bottom : (p_94128_1_ == 1 ? TileEntityMachineBattery.slots_top : TileEntityMachineBattery.slots_side);
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		
		if(itemStack.getItem() instanceof IBatteryItem) {
			if((i == 0 && ((IBatteryItem)itemStack.getItem()).getCharge(itemStack) == 0) || (i == 1 && ((IBatteryItem)itemStack.getItem()).getCharge(itemStack) == ((IBatteryItem)itemStack.getItem()).getMaxCharge())) {
				return true;
			}
		}
			
		return false;
	}

	public long getPowerRemainingScaled(long i) {
		return (this.power * i) / getMaxPower();
	}
	
	public byte getComparatorPower() {
		if(this.power == 0) return 0;
		double frac = (double) this.power / (double) getMaxPower() * 15D;
		return (byte) (MathHelper.clamp_int((int) frac + 1, 0, 15)); //to combat eventual rounding errors with the FEnSU's stupid maxPower
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote && this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) instanceof MachineBattery) {
			
			long prevPower = this.power;
			
			this.power = Library.chargeItemsFromTE(this.slots, 1, this.power, getMaxPower());
			
			//////////////////////////////////////////////////////////////////////
			transmitPowerFairly();
			//////////////////////////////////////////////////////////////////////
			
			byte comp = getComparatorPower();
			if(comp != this.lastRedstone)
				markDirty();
			this.lastRedstone = comp;
			
			this.power = Library.chargeTEFromItems(this.slots, 0, this.power, getMaxPower());

			long avg = (this.power + prevPower) / 2;
			this.delta = avg - this.log[0];
			
			for(int i = 1; i < this.log.length; i++) {
				this.log[i - 1] = this.log[i];
			}
			
			if(GeneralConfig.enable528) {
				long threshold = this.getMaxPower() / 3;
				if(Math.abs(prevPower - power) > threshold && Math.abs(prevPower - prevPowerState) > threshold) {
					this.pingPongTicks++;
					if(this.pingPongTicks > 10) {
						worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
						worldObj.newExplosion(null, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 10F, false, false);
					}
				} else {
					if(this.pingPongTicks > 0) this.pingPongTicks--;
				}
			}
			
			this.log[19] = avg;
			
			prevPowerState = power;
			
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setLong("power", avg);
			nbt.setLong("delta", this.delta);
			nbt.setShort("redLow", this.redLow);
			nbt.setShort("redHigh", this.redHigh);
			nbt.setByte("priority", (byte) this.priority.ordinal());
			networkPack(nbt, 20);
		}
	}
	
	protected void transmitPowerFairly() {
		
		short mode = getRelevantMode();
		
		//HasSets to we don'T have any duplicates
		Set<IPowerNet> nets = new HashSet<>();
		Set<IEnergyConnector> consumers = new HashSet<>();
		
		//iterate over all sides
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			
			TileEntity te = this.worldObj.getTileEntity(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);
			
			//if it's a cable, buffer both the network and all subscribers of the net
			if(te instanceof IEnergyConductor) {
				IEnergyConductor con = (IEnergyConductor) te;
				if(con.canConnect(dir.getOpposite()) && con.getPowerNet() != null) {
					nets.add(con.getPowerNet());
					con.getPowerNet().unsubscribe(this);
					consumers.addAll(con.getPowerNet().getSubscribers());
				}
				
			//if it's just a consumer, buffer it as a subscriber
			} else if(te instanceof IEnergyConnector) {
				IEnergyConnector con = (IEnergyConnector) te;
				if(con.canConnect(dir.getOpposite())) {
					consumers.add((IEnergyConnector) te);
				}
			}
		}

		//send power to buffered consumers, independent of nets
		if(this.power > 0 && (mode == TileEntityMachineBattery.mode_buffer || mode == TileEntityMachineBattery.mode_output)) {
			List<IEnergyConnector> con = new ArrayList<>();
			con.addAll(consumers);
			
			if(PowerNet.trackingInstances == null) {
				PowerNet.trackingInstances = new ArrayList<>();
			}
			PowerNet.trackingInstances.clear();
			
			nets.forEach(x -> {
				if(x instanceof PowerNet) PowerNet.trackingInstances.add((PowerNet) x);
			});
			
			long toSend = Math.min(this.power, this.getMaxTransfer());
			long powerRemaining = this.power - toSend;
			this.power = PowerNet.fairTransfer(con, toSend) + powerRemaining;
		}
		
		//resubscribe to buffered nets, if necessary
		if(mode == TileEntityMachineBattery.mode_buffer || mode == TileEntityMachineBattery.mode_input) {
			nets.forEach(x -> x.subscribe(this));
		}
	}
	
	@Deprecated protected void transmitPower() {
		
		short mode = getRelevantMode();
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			
			TileEntity te = this.worldObj.getTileEntity(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);
			
			// first we make sure we're not subscribed to the network that we'll be supplying
			if(te instanceof IEnergyConductor) {
				IEnergyConductor con = (IEnergyConductor) te;
				
				if(con.getPowerNet() != null && con.getPowerNet().isSubscribed(this))
					con.getPowerNet().unsubscribe(this);
			}
			
			//then we add energy
			if(mode == TileEntityMachineBattery.mode_buffer || mode == TileEntityMachineBattery.mode_output) {
				if(te instanceof IEnergyConnector) {
					IEnergyConnector con = (IEnergyConnector) te;
					
					long max = getMaxTransfer();
					long toTransfer = Math.min(max, this.power);
					long remainder = this.power - toTransfer;
					this.power = toTransfer;
					
					long oldPower = this.power;
					long transfer = this.power - con.transferPower(this.power);
					this.power = oldPower - transfer;
					
					this.power += remainder;
				}
			}
			
			//then we subscribe if possible
			if(te instanceof IEnergyConductor) {
				IEnergyConductor con = (IEnergyConductor) te;
				
				if(con.getPowerNet() != null) {
					if(mode == TileEntityMachineBattery.mode_output || mode == TileEntityMachineBattery.mode_none) {
						if(con.getPowerNet().isSubscribed(this)) {
							con.getPowerNet().unsubscribe(this);
						}
					} else if(!con.getPowerNet().isSubscribed(this)) {
						con.getPowerNet().subscribe(this);
					}
				}
			}
		}
	}
	
	public long getMaxTransfer() {
		return this.getMaxPower() / 20;
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) { 

		this.power = nbt.getLong("power");
		this.delta = nbt.getLong("delta");
		this.redLow = nbt.getShort("redLow");
		this.redHigh = nbt.getShort("redHigh");
		this.priority = ConnectionPriority.values()[nbt.getByte("priority")];
	}

	@Override
	public long getPower() {
		return this.power;
	}
	
	public short getRelevantMode() {
		
		if(this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord)) {
			return this.redHigh;
		} else {
			return this.redLow;
		}
	}
	
	private long bufferedMax;

	@Override
	public long getMaxPower() {
		
		if(this.bufferedMax == 0) {
			this.bufferedMax = ((MachineBattery)this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord)).maxPower;
		}
		
		return this.bufferedMax;
	}
	
	/*
	 * SATAN - TECH
	 */
	@Override
	public long transferPower(long power) {
		long overshoot = 0;
		
		// if power exceeds our transfer limit, truncate
		if(power > getMaxTransfer()) {
			overshoot += power - getMaxTransfer();
			power = getMaxTransfer();
		}
		
		// this check is in essence the same as the default implementation, but re-arranged to never overflow the int64 range
		// if the remaining power exceeds the power cap, truncate again
		long freespace = this.getMaxPower() - this.getPower();
		
		if(freespace < power) {
			overshoot += power - freespace;
			power = freespace;
		}
		
		// what remains is sure to not exceed the transfer limit and the power cap (and therefore the int64 range)
		this.setPower(this.getPower() + power);
		this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
		
		return overshoot;
	}
	
	@Override
	public long getTransferWeight() {

		int mode = getRelevantMode();
		
		if(mode == TileEntityMachineBattery.mode_output || mode == TileEntityMachineBattery.mode_none) {
			return 0;
		}
		
		return Math.min(Math.max(getMaxPower() - getPower(), 0), this.getMaxTransfer());
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return true;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public ConnectionPriority getPriority() {
		return this.priority;
	}
	
	// do some opencomputer stuff
	@Override
	public String getComponentName() {
		return this.openComputerName;
	}

	@Callback(direct = true, limit = 8)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getEnergyInfo(Context context, Arguments args) {
		return new Object[] {getPower(), getMaxPower()};
	}

	@Callback(direct = true, limit = 8)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		return new Object[] {getPower(), getMaxPower()};
	}

	@Override
	public void writeNBT(NBTTagCompound nbt) {
		NBTTagCompound data = new NBTTagCompound();
		data.setLong("power", power);
		data.setLong("prevPowerState", prevPowerState);
		data.setShort("redLow", redLow);
		data.setShort("redHigh", redHigh);
		data.setInteger("priority", this.priority.ordinal());
		nbt.setTag(IPersistentNBT.NBT_PERSISTENT_KEY, data);
	}

	@Override
	public void readNBT(NBTTagCompound nbt) {
		NBTTagCompound data = nbt.getCompoundTag(IPersistentNBT.NBT_PERSISTENT_KEY);
		this.power = data.getLong("power");
		this.prevPowerState = data.getLong("prevPowerState");
		this.redLow = data.getShort("redLow");
		this.redHigh = data.getShort("redHigh");
		this.priority = ConnectionPriority.values()[data.getInteger("priority")];
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineBattery(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineBattery(player.inventory, this);
	}
}
