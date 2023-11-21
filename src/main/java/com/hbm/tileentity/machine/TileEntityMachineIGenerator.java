package com.hbm.tileentity.machine;

import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.BlockDummyable;
import com.hbm.config.GeneralConfig;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.container.ContainerIGenerator;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.inventory.gui.GUIIGenerator;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.RTGUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyGenerator;
import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@SuppressWarnings("deprecation")
public class TileEntityMachineIGenerator extends TileEntityMachineBase implements IFluidAcceptor, IEnergyGenerator, IFluidStandardReceiver, IConfigurableMachine, IGUIProvider {
	
	public long power;
	public int spin;
	public int[] burn = new int[4];
	public boolean hasRTG = false;
	public int[] RTGSlots = new int[]{ 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };

	@SideOnly(Side.CLIENT)
	public float rotation;
	@SideOnly(Side.CLIENT)
	public float prevRotation;
	
	public FluidTank[] tanks;
	
	public int age = 0;
	
	public static final int coalConRate = 75;
	
	/* CONFIGURABLE */
	public static long maxPower = 1_000_000;
	public static int waterCap = 16000;
	public static int oilCap = 16000;
	public static int lubeCap = 4000;
	public static int coalGenRate = 20;
	public static double rtgHeatMult = 0.15D;
	public static double waterPowerMult = 1.0D;
	public static double lubePowerMult = 1.5D;
	public static double heatExponent = 1.15D;
	public static int waterRate = 10;
	public static int lubeRate = 1;
	public static long fluidHeatDiv = 1_000L;

	@Override
	public String getConfigName() {
		return "igen";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		TileEntityMachineIGenerator.maxPower = IConfigurableMachine.grab(obj, "L:powerCap", TileEntityMachineIGenerator.maxPower);
		TileEntityMachineIGenerator.waterCap = IConfigurableMachine.grab(obj, "I:waterCap", TileEntityMachineIGenerator.waterCap);
		TileEntityMachineIGenerator.oilCap = IConfigurableMachine.grab(obj, "I:oilCap", TileEntityMachineIGenerator.oilCap);
		TileEntityMachineIGenerator.lubeCap = IConfigurableMachine.grab(obj, "I:lubeCap", TileEntityMachineIGenerator.lubeCap);
		TileEntityMachineIGenerator.coalGenRate = IConfigurableMachine.grab(obj, "I:solidFuelRate", TileEntityMachineIGenerator.coalGenRate);
		TileEntityMachineIGenerator.rtgHeatMult = IConfigurableMachine.grab(obj, "D:rtgHeatMult", TileEntityMachineIGenerator.rtgHeatMult);
		TileEntityMachineIGenerator.waterPowerMult = IConfigurableMachine.grab(obj, "D:waterPowerMult", TileEntityMachineIGenerator.waterPowerMult);
		TileEntityMachineIGenerator.lubePowerMult = IConfigurableMachine.grab(obj, "D:lubePowerMult", TileEntityMachineIGenerator.lubePowerMult);
		TileEntityMachineIGenerator.heatExponent = IConfigurableMachine.grab(obj, "D:heatExponent", TileEntityMachineIGenerator.heatExponent);
		TileEntityMachineIGenerator.waterRate = IConfigurableMachine.grab(obj, "I:waterRate", TileEntityMachineIGenerator.waterRate);
		TileEntityMachineIGenerator.lubeRate = IConfigurableMachine.grab(obj, "I:lubeRate", TileEntityMachineIGenerator.lubeRate);
		TileEntityMachineIGenerator.fluidHeatDiv = IConfigurableMachine.grab(obj, "D:fluidHeatDiv", TileEntityMachineIGenerator.fluidHeatDiv);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("L:powerCap").value(TileEntityMachineIGenerator.maxPower);
		writer.name("I:waterCap").value(TileEntityMachineIGenerator.waterCap);
		writer.name("I:oilCap").value(TileEntityMachineIGenerator.oilCap);
		writer.name("I:lubeCap").value(TileEntityMachineIGenerator.lubeCap);
		writer.name("I:solidFuelRate").value(TileEntityMachineIGenerator.coalGenRate);
		writer.name("D:rtgHeatMult").value(TileEntityMachineIGenerator.rtgHeatMult);
		writer.name("D:waterPowerMult").value(TileEntityMachineIGenerator.waterPowerMult);
		writer.name("D:lubePowerMult").value(TileEntityMachineIGenerator.lubePowerMult);
		writer.name("D:heatExponent").value(TileEntityMachineIGenerator.heatExponent);
		writer.name("I:waterRate").value(TileEntityMachineIGenerator.waterRate);
		writer.name("I:lubeRate").value(TileEntityMachineIGenerator.lubeRate);
		writer.name("D:fluidHeatDiv").value(TileEntityMachineIGenerator.fluidHeatDiv);
	}

	public TileEntityMachineIGenerator() {
		super(21);
		this.tanks = new FluidTank[3];
		this.tanks[0] = new FluidTank(Fluids.WATER, TileEntityMachineIGenerator.waterCap);
		this.tanks[1] = new FluidTank(Fluids.HEATINGOIL, TileEntityMachineIGenerator.oilCap);
		this.tanks[2] = new FluidTank(Fluids.LUBRICANT, TileEntityMachineIGenerator.lubeCap);
	}

	@Override
	public String getName() {
		return "container.iGenerator";
	}
	
	protected DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
		return new DirPos[] {
			new DirPos(xCoord + dir.offsetX * -4, yCoord, zCoord + dir.offsetZ * -4, dir.getOpposite()),
			new DirPos(xCoord + dir.offsetX * -2, yCoord - 1, zCoord + dir.offsetZ * -2, ForgeDirection.DOWN),
			new DirPos(xCoord + dir.offsetX * -1, yCoord - 1, zCoord + dir.offsetZ * -1, ForgeDirection.DOWN),
			new DirPos(xCoord, yCoord - 1, zCoord, ForgeDirection.DOWN),
			new DirPos(xCoord + dir.offsetX * 3, yCoord, zCoord + dir.offsetZ * 3, dir),
		};
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			boolean con = GeneralConfig.enableLBSM && GeneralConfig.enableLBSMIGen;
			
			this.power = Library.chargeItemsFromTE(this.slots, 0, this.power, TileEntityMachineIGenerator.maxPower);
			
			for(DirPos dir : getConPos()) {
				sendPower(this.worldObj, dir.getX(), dir.getY(), dir.getZ(), dir.getDir());
				
				for(FluidTank tank : this.tanks) {
					this.trySubscribe(tank.getTankType(), this.worldObj, dir.getX(), dir.getY(), dir.getZ(), dir.getDir());
				}
			}
			
			this.tanks[1].setType(9, 10, this.slots);
			this.tanks[0].loadTank(1, 2, this.slots);
			this.tanks[1].loadTank(9, 10, this.slots);
			this.tanks[2].loadTank(7, 8, this.slots);
			
			this.spin = 0;
			
			/// LIQUID FUEL ///
			if(this.tanks[1].getFill() > 0) {
				int pow = getPowerFromFuel(con);
				
				if(pow > 0) {
					this.tanks[1].setFill(this.tanks[1].getFill() - 1);
					this.spin += pow;
				}
			}
			
			///SOLID FUEL ///
			for(int i = 0; i < 4; i++) {
				
				// POWER GEN //
				if(this.burn[i] > 0) {
					this.burn[i]--;
					this.spin += con ? TileEntityMachineIGenerator.coalConRate : TileEntityMachineIGenerator.coalGenRate;
					
				// REFUELING //
				} else {
					int slot = i + 3;
					
					if(this.slots[slot] != null) {
						ItemStack fuel = this.slots[slot];
						int burnTime = TileEntityFurnace.getItemBurnTime(fuel) / 2;
						
						if(burnTime > 0) {
							
							if(fuel.getItem() == Items.coal) //1200 (1600)
								burnTime *= con ? 1.5 : 1.1;
							if(fuel.getItem() == ModItems.solid_fuel) //3200 (3200)
								burnTime *= con ? 2 : 1.1;
							if(fuel.getItem() == ModItems.solid_fuel_presto) //16000 (8000)
								burnTime *= con ? 4 : 1.1;
							if(fuel.getItem() == ModItems.solid_fuel_presto_triplet) //80000 (40000)
								burnTime *= con ? 4 : 1.1;
							
							this.burn[i] = burnTime;
							
							this.slots[slot].stackSize--;
							
							if(this.slots[slot].stackSize <= 0) {
								
								if(this.slots[slot].getItem().hasContainerItem(this.slots[slot])) {
									this.slots[slot] = this.slots[slot].getItem().getContainerItem(this.slots[slot]);
								} else {
									this.slots[slot] = null;
								}
							}
						}
					}
				}
			}
			
			// RTG ///
			this.hasRTG = RTGUtil.hasHeat(this.slots, this.RTGSlots);
			this.spin += RTGUtil.updateRTGs(this.slots, this.RTGSlots) * (con ? 0.2 : TileEntityMachineIGenerator.rtgHeatMult);
			
			if(this.spin > 0) {
				
				int powerGen = this.spin;
				
				if(this.tanks[0].getFill() >= 10) {
					powerGen += this.spin * TileEntityMachineIGenerator.waterPowerMult;
					this.tanks[0].setFill(this.tanks[0].getFill() - TileEntityMachineIGenerator.waterRate);
				}
				
				if(this.tanks[2].getFill() >= 1) {
					powerGen += this.spin * TileEntityMachineIGenerator.lubePowerMult;
					this.tanks[2].setFill(this.tanks[2].getFill() - TileEntityMachineIGenerator.lubeRate);
				}
				
				this.power += Math.pow(powerGen, TileEntityMachineIGenerator.heatExponent);
				
				if(this.power > TileEntityMachineIGenerator.maxPower)
					this.power = TileEntityMachineIGenerator.maxPower;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setInteger("spin", this.spin);
			data.setIntArray("burn", this.burn);
			data.setBoolean("hasRTG", this.hasRTG);
			networkPack(data, 150);
			
			for(int i = 0; i < 3; i++)
				this.tanks[i].updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
			
		} else {
			
			this.prevRotation = this.rotation;
			
			if(this.spin > 0) {
				this.rotation += 15;
			}
			
			if(this.rotation >= 360) {
				this.rotation -= 360;
				this.prevRotation -= 360;
			}
		}
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return i >= 3 && i <= 6 && TileEntityFurnace.getItemBurnTime(itemStack) > 0;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 3, 4, 5, 6 };
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.spin = nbt.getInteger("spin");
		this.burn = nbt.getIntArray("burn");
		this.hasRTG = nbt.getBoolean("hasRTG");
	}
	
	public int getPowerFromFuel(boolean con) {
		FluidType type = this.tanks[1].getTankType();
		return type.hasTrait(FT_Flammable.class) ? (int)(type.getTrait(FT_Flammable.class).getHeatEnergy() / (con ? 5000L : TileEntityMachineIGenerator.fluidHeatDiv)) : 0;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		this.tanks[index].setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		
		if(type == Fluids.WATER)
			this.tanks[0].setFill(fill);
		else if(type == Fluids.LUBRICANT)
			this.tanks[2].setFill(fill);
		else if(this.tanks[1].getTankType() == type)
			this.tanks[1].setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		this.tanks[index].setTankType(type);
	}

	@Override
	public int getFluidFill(FluidType type) {
		
		for(int i = 0; i < 3; i++)
			if(this.tanks[i].getTankType() == type)
				return this.tanks[i].getFill();
		
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		
		for(int i = 0; i < 3; i++)
			if(this.tanks[i].getTankType() == type)
				return this.tanks[i].getMaxFill();
		
		return 0;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		for(int i = 0; i < 3; i++)
			this.tanks[i].readFromNBT(nbt, "tank_" + i);
		
		this.power = nbt.getLong("power");
		this.burn = nbt.getIntArray("burn");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		for(int i = 0; i < 3; i++)
			this.tanks[i].writeToNBT(nbt, "tank_" + i);
		
		nbt.setLong("power", this.power);
		nbt.setIntArray("burn", this.burn);
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
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return TileEntityMachineIGenerator.maxPower;
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { this.tanks[0], this.tanks[1], this.tanks[2] };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return this.tanks;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerIGenerator(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIIGenerator(player.inventory, this);
	}
}
