package com.hbm.tileentity.machine;

import java.io.IOException;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.inventory.FluidContainerRegistry;
import com.hbm.inventory.container.ContainerMachineSelenium;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Combustible;
import com.hbm.inventory.fluid.trait.FT_Combustible.FuelGrade;
import com.hbm.inventory.gui.GUIMachineSelenium;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyGenerator;
import api.hbm.fluid.IFluidStandardReceiver;
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
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineSeleniumEngine extends TileEntityLoadedBase implements ISidedInventory, IEnergyGenerator, IFluidContainer, IFluidAcceptor, IFluidStandardReceiver, IConfigurableMachine, IGUIProvider {

	private ItemStack slots[];

	public long power;
	public int soundCycle = 0;
	public long powerCap = 250000;
	public FluidTank tank;
	public int pistonCount = 0;

	public static long maxPower = 250000;
	public static int fluidCap = 16000;
	public static double pistonExp = 1.0D;
	public static boolean shutUp = false;
	public static HashMap<FuelGrade, Double> fuelEfficiency = new HashMap<>();
	static {
		TileEntityMachineSeleniumEngine.fuelEfficiency.put(FuelGrade.LOW,		0.75D);
		TileEntityMachineSeleniumEngine.fuelEfficiency.put(FuelGrade.MEDIUM,	0.5D);
		TileEntityMachineSeleniumEngine.fuelEfficiency.put(FuelGrade.HIGH,		0.25D);
		TileEntityMachineSeleniumEngine.fuelEfficiency.put(FuelGrade.AERO,		0.00D);
	}

	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 1, 2 };
	private static final int[] slots_side = new int[] { 2 };

	private String customName;

	public TileEntityMachineSeleniumEngine() {
		this.slots = new ItemStack[14];
		this.tank = new FluidTank(Fluids.DIESEL, TileEntityMachineSeleniumEngine.fluidCap, 0);
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
		if (this.slots[i] != null) {
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
		if (itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return hasCustomInventoryName() ? this.customName : "container.machineSelenium";
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
		if (this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this) {
			return false;
		} else {
			return player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64;
		}
	}

	// You scrubs aren't needed for anything (right now)
	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		if (i == 9)
			if (FluidContainerRegistry.getFluidContent(stack, this.tank.getTankType()) > 0)
				return true;
		if (i == 13)
			if (stack.getItem() instanceof IBatteryItem)
				return true;

		return false;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (this.slots[i] != null) {
			if (this.slots[i].stackSize <= j) {
				ItemStack itemStack = this.slots[i];
				this.slots[i] = null;
				return itemStack;
			}
			ItemStack itemStack1 = this.slots[i].splitStack(j);
			if (this.slots[i].stackSize == 0) {
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

		this.power = nbt.getLong("powerTime");
		this.powerCap = nbt.getLong("powerCap");
		this.tank.readFromNBT(nbt, "fuel");
		this.slots = new ItemStack[getSizeInventory()];

		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if (b0 >= 0 && b0 < this.slots.length) {
				this.slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("powerTime", this.power);
		nbt.setLong("powerCap", this.powerCap);
		this.tank.writeToNBT(nbt, "fuel");
		NBTTagList list = new NBTTagList();

		for (int i = 0; i < this.slots.length; i++) {
			if (this.slots[i] != null) {
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte) i);
				this.slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return p_94128_1_ == 0 ? TileEntityMachineSeleniumEngine.slots_bottom : (p_94128_1_ == 1 ? TileEntityMachineSeleniumEngine.slots_top : TileEntityMachineSeleniumEngine.slots_side);
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		if (i == 1)
			if (itemStack.getItem() == ModItems.canister_empty || itemStack.getItem() == ModItems.tank_steel)
				return true;
		if (i == 2)
			if (itemStack.getItem() instanceof IBatteryItem && ((IBatteryItem)itemStack.getItem()).getCharge(itemStack) == ((IBatteryItem)itemStack.getItem()).getMaxCharge())
				return true;

		return false;
	}

	public long getPowerScaled(long i) {
		return (this.power * i) / this.powerCap;
	}

	@Override
	public void updateEntity() {
		
		if (!this.worldObj.isRemote) {
			
			this.subscribeToAllAround(this.tank.getTankType(), this);
			sendPower(this.worldObj, this.xCoord, this.yCoord - 1, this.zCoord, ForgeDirection.DOWN);
			
			this.pistonCount = countPistons();

			//Tank Management
			this.tank.setType(11, 12, this.slots);
			this.tank.loadTank(9, 10, this.slots);
			this.tank.updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);

			FluidType type = this.tank.getTankType();
			if(type == Fluids.NITAN)
				this.powerCap = TileEntityMachineSeleniumEngine.maxPower * 10;
			else
				this.powerCap = TileEntityMachineSeleniumEngine.maxPower;
			
			// Battery Item
			this.power = Library.chargeItemsFromTE(this.slots, 13, this.power, this.powerCap);

			if(this.pistonCount > 2)
				generate();

			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(this.xCoord, this.yCoord, this.zCoord, this.power), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(this.xCoord, this.yCoord, this.zCoord, this.pistonCount, 0), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 150));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(this.xCoord, this.yCoord, this.zCoord, (int)this.powerCap, 1), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
		}
	}
	
	public int countPistons() {
		int count = 0;
		
		for(int i = 0; i < 9; i++) {
			if(this.slots[i] != null && this.slots[i].getItem() == ModItems.piston_selenium)
				count++;
		}
		
		return count;
	}
	
	public boolean hasAcceptableFuel() {
		return getHEFromFuel() > 0;
	}
	
	public long getHEFromFuel() {
		return TileEntityMachineSeleniumEngine.getHEFromFuel(this.tank.getTankType());
	}
	
	public static long getHEFromFuel(FluidType type) {
		
		if(type.hasTrait(FT_Combustible.class)) {
			FT_Combustible fuel = type.getTrait(FT_Combustible.class);
			FuelGrade grade = fuel.getGrade();
			double efficiency = TileEntityMachineSeleniumEngine.fuelEfficiency.containsKey(grade) ? TileEntityMachineSeleniumEngine.fuelEfficiency.get(grade) : 0;
			return (long) (fuel.getCombustionEnergy() / 1000L * efficiency);
		}
		
		return 0;
	}

	public void generate() {
		if (hasAcceptableFuel()) {
			if (this.tank.getFill() > 0) {
				
				if(!TileEntityMachineSeleniumEngine.shutUp) {
					if (this.soundCycle == 0) {
						this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "fireworks.blast", 1.0F, 0.5F);
					}
					this.soundCycle++;
	
					if (this.soundCycle >= 3)
						this.soundCycle = 0;
				}

				this.tank.setFill(this.tank.getFill() - this.pistonCount);
				if(this.tank.getFill() < 0)
					this.tank.setFill(0);

				this.power += getHEFromFuel() * Math.pow(this.pistonCount, TileEntityMachineSeleniumEngine.pistonExp);
					
				if(this.power > this.powerCap)
					this.power = this.powerCap;
			}
		}
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return TileEntityMachineSeleniumEngine.maxPower;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
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
		return type == this.tank.getTankType() ? this.tank.getMaxFill() : 0;
	}

	@Override
	public int getFluidFill(FluidType type) {
		return type == this.tank.getTankType() ? this.tank.getFill() : 0;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type == this.tank.getTankType())
			this.tank.setFill(i);
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir == ForgeDirection.DOWN;
	}

	@Override
	public String getConfigName() {
		return "radialengine";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		TileEntityMachineSeleniumEngine.maxPower = IConfigurableMachine.grab(obj, "L:powerCap", TileEntityMachineSeleniumEngine.maxPower);
		TileEntityMachineSeleniumEngine.fluidCap = IConfigurableMachine.grab(obj, "I:fuelCap", TileEntityMachineSeleniumEngine.fluidCap);
		TileEntityMachineSeleniumEngine.pistonExp = IConfigurableMachine.grab(obj, "D:pistonGenExponent", TileEntityMachineSeleniumEngine.pistonExp);
		
		if(obj.has("D[:efficiency")) {
			JsonArray array = obj.get("D[:efficiency").getAsJsonArray();
			for(FuelGrade grade : FuelGrade.values()) {
				TileEntityMachineSeleniumEngine.fuelEfficiency.put(grade, array.get(grade.ordinal()).getAsDouble());
			}
		}
		
		TileEntityMachineSeleniumEngine.shutUp = IConfigurableMachine.grab(obj, "B:shutUp", TileEntityMachineSeleniumEngine.shutUp);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("L:powerCap").value(TileEntityMachineSeleniumEngine.maxPower);
		writer.name("I:fuelCap").value(TileEntityMachineSeleniumEngine.fluidCap);
		writer.name("D:pistonGenExponent").value(TileEntityMachineSeleniumEngine.pistonExp);
		
		String info = "Fuel grades in order: ";
		for(FuelGrade grade : FuelGrade.values()) info += grade.name() + " ";
		info = info.trim();
		writer.name("INFO").value(info);
		
		writer.name("D[:efficiency").beginArray().setIndent("");
		for(FuelGrade grade : FuelGrade.values()) {
			double d = TileEntityMachineSeleniumEngine.fuelEfficiency.containsKey(grade) ? TileEntityMachineSeleniumEngine.fuelEfficiency.get(grade) : 0.0D;
			writer.value(d);
		}
		writer.endArray().setIndent("  ");
		writer.name("B:shutUp").value(TileEntityMachineSeleniumEngine.shutUp);
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {this.tank};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {this.tank};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineSelenium(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineSelenium(player.inventory, this);
	}
}
