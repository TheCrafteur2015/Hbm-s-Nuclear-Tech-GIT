package com.hbm.tileentity.machine;

import java.io.IOException;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.inventory.FluidContainerRegistry;
import com.hbm.inventory.container.ContainerMachineDiesel;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Combustible;
import com.hbm.inventory.fluid.trait.FT_Combustible.FuelGrade;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Leaded;
import com.hbm.inventory.gui.GUIMachineDiesel;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachinePolluting;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyGenerator;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineDiesel extends TileEntityMachinePolluting implements IEnergyGenerator, IFluidContainer, IFluidAcceptor, IFluidStandardTransceiver, IConfigurableMachine, IGUIProvider {

	public long power;
	public int soundCycle = 0;
	public long powerCap = TileEntityMachineDiesel.maxPower;
	public FluidTank tank;
	
	/* CONFIGURABLE CONSTANTS */
	public static long maxPower = 50000;
	public static int fluidCap = 16000;
	public static HashMap<FuelGrade, Double> fuelEfficiency = new HashMap<>();
	static {
		TileEntityMachineDiesel.fuelEfficiency.put(FuelGrade.MEDIUM,	0.5D);
		TileEntityMachineDiesel.fuelEfficiency.put(FuelGrade.HIGH,		0.75D);
		TileEntityMachineDiesel.fuelEfficiency.put(FuelGrade.AERO,		0.1D);
	}
	public static boolean shutUp = false;

	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 1, 2 };
	private static final int[] slots_side = new int[] { 2 };

	public TileEntityMachineDiesel() {
		super(5, 100);
		this.tank = new FluidTank(Fluids.DIESEL, 4_000, 0);
	}

	@Override
	public String getName() {
		return "container.machineDiesel";
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		if (i == 0)
			if (FluidContainerRegistry.getFluidContent(stack, this.tank.getTankType()) > 0)
				return true;
		if (i == 2)
			if (stack.getItem() instanceof IBatteryItem)
				return true;

		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.power = nbt.getLong("powerTime");
		this.powerCap = nbt.getLong("powerCap");
		this.tank.readFromNBT(nbt, "fuel");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("powerTime", this.power);
		nbt.setLong("powerCap", this.powerCap);
		this.tank.writeToNBT(nbt, "fuel");
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 0 ? TileEntityMachineDiesel.slots_bottom : (side == 1 ? TileEntityMachineDiesel.slots_top : TileEntityMachineDiesel.slots_side);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		if(i == 1) {
			if(stack.getItem() == ModItems.canister_empty || stack.getItem() == ModItems.tank_steel) {
				return true;
			}
		}
		if(i == 2) {
			if(stack.getItem() instanceof IBatteryItem && ((IBatteryItem) stack.getItem()).getCharge(stack) == ((IBatteryItem) stack.getItem()).getMaxCharge()) {
				return true;
			}
		}

		return false;
	}

	public long getPowerScaled(long i) {
		return (this.power * i) / this.powerCap;
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				sendPower(this.worldObj, this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ, dir);
				sendSmoke(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ, dir);
			}

			//Tank Management
			FluidType last = this.tank.getTankType();
			if(this.tank.setType(3, 4, this.slots)) this.unsubscribeToAllAround(last, this);
			this.tank.loadTank(0, 1, this.slots);
			this.tank.updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
			
			this.subscribeToAllAround(this.tank.getTankType(), this);

			FluidType type = this.tank.getTankType();
			if(type == Fluids.NITAN)
				this.powerCap = TileEntityMachineDiesel.maxPower * 10;
			else
				this.powerCap = TileEntityMachineDiesel.maxPower;
			
			// Battery Item
			this.power = Library.chargeItemsFromTE(this.slots, 2, this.power, this.powerCap);

			generate();

			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("power", (int) this.power);
			data.setInteger("powerCap", (int) this.powerCap);
			networkPack(data, 50);
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {

		this.power = data.getInteger("power");
		this.powerCap = data.getInteger("powerCap");
	}
	
	public boolean hasAcceptableFuel() {
		return getHEFromFuel() > 0;
	}
	
	public long getHEFromFuel() {
		return TileEntityMachineDiesel.getHEFromFuel(this.tank.getTankType());
	}
	
	public static long getHEFromFuel(FluidType type) {
		
		if(type.hasTrait(FT_Combustible.class)) {
			FT_Combustible fuel = type.getTrait(FT_Combustible.class);
			FuelGrade grade = fuel.getGrade();
			double efficiency = TileEntityMachineDiesel.fuelEfficiency.containsKey(grade) ? TileEntityMachineDiesel.fuelEfficiency.get(grade) : 0;
			
			if(fuel.getGrade() != FuelGrade.LOW) {
				return (long) (fuel.getCombustionEnergy() / 1000L * efficiency);
			}
		}
		
		return 0;
	}

	public void generate() {
		
		if(hasAcceptableFuel()) {
			if (this.tank.getFill() > 0) {
				
				if(!TileEntityMachineDiesel.shutUp) {
					if (this.soundCycle == 0) {
						this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "fireworks.blast", 0.75F * getVolume(3), 0.5F);
					}
					this.soundCycle++;
				}

				if(this.soundCycle >= 3)
					this.soundCycle = 0;

				this.tank.setFill(this.tank.getFill() - 1);
				if(this.tank.getFill() < 0)
					this.tank.setFill(0);
				
				pollute(PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND * 0.5F);
				if(this.tank.getTankType().hasTrait(FT_Leaded.class)) pollute(PollutionType.HEAVYMETAL, PollutionHandler.HEAVY_METAL_PER_SECOND * 0.5F);

				if(this.power + getHEFromFuel() <= this.powerCap) {
					this.power += getHEFromFuel();
				} else {
					this.power = this.powerCap;
				}
			}
		}
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
	public long getMaxPower() {
		return TileEntityMachineDiesel.maxPower;
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
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {this.tank};
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { this.tank };
	}

	@Override
	public String getConfigName() {
		return "dieselgen";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		TileEntityMachineDiesel.maxPower = IConfigurableMachine.grab(obj, "L:powerCap", TileEntityMachineDiesel.maxPower);
		TileEntityMachineDiesel.fluidCap = IConfigurableMachine.grab(obj, "I:fuelCap", TileEntityMachineDiesel.fluidCap);
		
		if(obj.has("D[:efficiency")) {
			JsonArray array = obj.get("D[:efficiency").getAsJsonArray();
			for(FuelGrade grade : FuelGrade.values()) {
				TileEntityMachineDiesel.fuelEfficiency.put(grade, array.get(grade.ordinal()).getAsDouble());
			}
		}
		TileEntityMachineDiesel.shutUp = IConfigurableMachine.grab(obj, "B:shutUp", TileEntityMachineDiesel.shutUp);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("L:powerCap").value(TileEntityMachineDiesel.maxPower);
		writer.name("I:fuelCap").value(TileEntityMachineDiesel.fluidCap);
		
		String info = "Fuel grades in order: ";
		for(FuelGrade grade : FuelGrade.values()) info += grade.name() + " ";
		info = info.trim();
		writer.name("INFO").value(info);
		
		writer.name("D[:efficiency").beginArray().setIndent("");
		for(FuelGrade grade : FuelGrade.values()) {
			double d = TileEntityMachineDiesel.fuelEfficiency.containsKey(grade) ? TileEntityMachineDiesel.fuelEfficiency.get(grade) : 0.0D;
			writer.value(d);
		}
		writer.endArray().setIndent("  ");
		writer.name("B:shutUp").value(TileEntityMachineDiesel.shutUp);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineDiesel(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineDiesel(player.inventory, this);
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return getSmokeTanks();
	}
}
