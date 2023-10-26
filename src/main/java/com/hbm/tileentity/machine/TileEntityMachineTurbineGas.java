package com.hbm.tileentity.machine;

import java.util.HashMap;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerMachineTurbineGas;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Combustible;
import com.hbm.inventory.fluid.trait.FT_Combustible.FuelGrade;
import com.hbm.inventory.gui.GUIMachineTurbineGas;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IEnergyGenerator;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineTurbineGas extends TileEntityMachineBase implements IFluidStandardTransceiver, IEnergyGenerator, IControlReceiver, IGUIProvider {
	
	public long power;
	public static final long maxPower = 1000000L;
	
	public int rpm; //0-100, crescent moon gauge, used for calculating the amount of power generated, starts past 10%
	public int temp; //0-800, used for figuring out how much water to boil, starts boiling at 300°C
	public int rpmIdle = 10;
	public int tempIdle = 300;
	
	public int powerSliderPos; //goes from 0 to 60, 0 is idle, 60 is max power
	public int throttle; //the same thing, but goes from 0 to 100
	
	public boolean autoMode;
	public int state = 0; //0 is offline, -1 is startup, 1 is online
	
	public int counter = 0; //used to startup and shutdown
	public int instantPowerOutput;
	
	public FluidTank[] tanks;
	
	private AudioWrapper audio;
	
	public static HashMap<FluidType, Double> fuelMaxCons = new HashMap<>(); //fuel consumption per tick at max power
	
	static {
		TileEntityMachineTurbineGas.fuelMaxCons.put(Fluids.GAS, 50D);			// natgas doesn't burn well so it burns faster to compensate
		TileEntityMachineTurbineGas.fuelMaxCons.put(Fluids.SYNGAS, 10D);		// syngas just fucks
		TileEntityMachineTurbineGas.fuelMaxCons.put(Fluids.OXYHYDROGEN, 100D);	// oxyhydrogen is terrible so it needs to burn a ton for the bare minimum
		TileEntityMachineTurbineGas.fuelMaxCons.put(Fluids.REFORMGAS, 2.5D);	// halved because it's too powerful
		// default to 5 if not in list
	}
	
	//TODO particles from heat exchanger maybe? maybe in a future
	
	public TileEntityMachineTurbineGas() {
		super(2);
		this.tanks = new FluidTank[4];
		this.tanks[0] = new FluidTank(Fluids.GAS, 100000);
		this.tanks[1] = new FluidTank(Fluids.LUBRICANT, 16000);
		this.tanks[2] = new FluidTank(Fluids.WATER, 16000);
		this.tanks[3] = new FluidTank(Fluids.HOTSTEAM, 160000);
	}
	
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			this.throttle = this.powerSliderPos * 100 / 60;
			
			if(this.slots[1] != null && this.slots[1].getItem() instanceof IItemFluidIdentifier) {
				FluidType fluid = ((IItemFluidIdentifier) this.slots[1].getItem()).getType(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.slots[1]);
				if(fluid.hasTrait(FT_Combustible.class) && fluid.getTrait(FT_Combustible.class).getGrade() == FuelGrade.GAS) {
					this.tanks[0].setTankType(fluid);
				}
			}
			
			switch(this.state) { //what to do when turbine offline, starting up and online			
			case 0:
				shutdown();	
				break;
			case -1:
				stopIfNotReady();
				startup();
				break;
			case 1:			
				stopIfNotReady();
				run();
				break;
			default:
				break;
			}
			
			if(this.autoMode) { //power production depending on power requirement
				
				//scales the slider proportionally to the power gauge
				int powerSliderTarget = 60 - (int) (60 * this.power / TileEntityMachineTurbineGas.maxPower);
				
				if(powerSliderTarget > this.powerSliderPos) { //makes the auto slider slide instead of snapping into position
					this.powerSliderPos++;
				}
				else if(powerSliderTarget < this.powerSliderPos) {
					this.powerSliderPos--;
				}
			}
			
			ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
			ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", Math.min(this.power, TileEntityMachineTurbineGas.maxPower)); //set first to get an unmodified view of how much power was generated before deductions from the net
			
			//do net/battery deductions first...
			this.power = Library.chargeItemsFromTE(this.slots, 0, this.power, TileEntityMachineTurbineGas.maxPower);
			sendPower(this.worldObj, this.xCoord - dir.offsetZ * 5, this.yCoord + 1, this.zCoord + dir.offsetX * 5, rot); //sends out power
			
			//...and then cap it. Prevents potential future cases where power would be limited due to the fuel being too strong and the buffer too small.
			if(this.power > TileEntityMachineTurbineGas.maxPower)
				this.power = TileEntityMachineTurbineGas.maxPower;
			
			for(int i = 0; i < 2; i++) { //fuel and lube
				this.trySubscribe(this.tanks[i].getTankType(), this.worldObj, this.xCoord - dir.offsetX * 2 + rot.offsetX, this.yCoord, this.zCoord - dir.offsetZ * 2 + rot.offsetZ, dir.getOpposite());
				this.trySubscribe(this.tanks[i].getTankType(), this.worldObj, this.xCoord + dir.offsetX * 2 + rot.offsetX, this.yCoord, this.zCoord + dir.offsetZ * 2 + rot.offsetZ, dir);
			}
			//water
			this.trySubscribe(this.tanks[2].getTankType(), this.worldObj, this.xCoord - dir.offsetX * 2 + rot.offsetX * -4, this.yCoord, this.zCoord - dir.offsetZ * 2 + rot.offsetZ * -4, dir.getOpposite());
			this.trySubscribe(this.tanks[2].getTankType(), this.worldObj, this.xCoord + dir.offsetX * 2 + rot.offsetX * -4, this.yCoord, this.zCoord + dir.offsetZ * 2 + rot.offsetZ * -4, dir);
			//steam
			this.sendFluid(this.tanks[3], this.worldObj, this.xCoord + dir.offsetZ * 6, this.yCoord + 1, this.zCoord - dir.offsetX * 6, rot.getOpposite());
			
			data.setInteger("rpm",  this.rpm);
			data.setInteger("temp",  this.temp);
			data.setInteger("state", this.state);
			data.setBoolean("automode", this.autoMode);
			data.setInteger("throttle",  this.throttle);
			data.setInteger("slidpos",  this.powerSliderPos);
			
			if(this.state != 1) {
				data.setInteger("counter", this.counter); //sent during startup and shutdown
			} else {
				data.setInteger("instantPow", this.instantPowerOutput); //sent while running
			}
			
			this.tanks[0].writeToNBT(data, "fuel");
			this.tanks[1].writeToNBT(data, "lube");
			this.tanks[2].writeToNBT(data, "water");
			this.tanks[3].writeToNBT(data, "steam");
				
			networkPack(data, 150);
			
		} else { //client side, for sounds n shit
			
			if(this.rpm >= 10 && this.state != -1) { //if conditions are right, play the sound
				
				if(this.audio == null) { //if there is no sound playing, start it
					
					this.audio = MainRegistry.proxy.getLoopedSound("hbm:block.turbinegasRunning", this.xCoord, this.yCoord, this.zCoord, 1.0F, 20F, 1.0F);
					this.audio.startSound();
					
				} else if(!this.audio.isPlaying()) {
					this.audio.stopSound();
					this.audio = MainRegistry.proxy.getLoopedSound("hbm:block.turbinegasRunning", this.xCoord, this.yCoord, this.zCoord, 1.0F, 20F, 1.0F);
					this.audio.startSound();
				}
				
				this.audio.updatePitch((float) (0.55 + 0.1 * this.rpm / 10)); //dynamic pitch update based on rpm
				this.audio.updateVolume(100F); //yeah i need this
				
			} else {
				
				if(this.audio != null) {
					this.audio.stopSound();
					this.audio = null;
				}
			}
		}
	}
	
	private void stopIfNotReady() {
		
		if(this.tanks[0].getFill() == 0 || this.tanks[1].getFill() == 0) {
			this.state = 0;
		}
		if(!hasAcceptableFuel()) {
			this.state = 0;
		}
	}
	
	public boolean hasAcceptableFuel() {
		
		if(this.tanks[0].getTankType().hasTrait(FT_Combustible.class)) {
			return this.tanks[0].getTankType().getTrait(FT_Combustible.class).getGrade() == FuelGrade.GAS;
		}
		
		return false;
	}
	
	private void startup() {
		
		this.counter++;
		
		if(this.counter <= 20) //rpm gauge 0-100-0
			this.rpm = 5 * this.counter;
		else if (this.counter > 20 && this.counter <= 40)
			this.rpm = 100 - 5 * (this.counter - 20);
		else if (this.counter > 50) {
			this.rpm = this.rpmIdle * (this.counter - 50) / 530; //slowly ramps up temp and RPM
			this.temp = this.tempIdle * (this.counter - 50) / 530;
		}
		
		if(this.counter == 50) {
			this.worldObj.playSoundEffect(this.xCoord, this.yCoord + 2, this.zCoord, "hbm:block.turbinegasStartup", 1F, 1.0F);
		}
			
		if(this.counter == 580) {
			this.state = 1;
		}
	}
	
	
	int rpmLast; //used to progressively slow down and cool the turbine without immediatly setting rpm and temp to 0
	int tempLast;
	
	private void shutdown() {
		
		this.autoMode = false;
		this.instantPowerOutput = 0;
		
		if(this.powerSliderPos > 0)
			this.powerSliderPos--;
		
		if(this.rpm <= 10 && this.counter > 0) {
			
			if(this.counter == 225) {
				
				this.worldObj.playSoundEffect(this.xCoord, this.yCoord + 2, this.zCoord, "hbm:block.turbinegasShutdown", 1F, 1.0F);
				
				this.rpmLast = this.rpm;
				this.tempLast = this.temp;
			}
			
			this.counter--;
			
			this.rpm = this.rpmLast * (this.counter) / 225;
			this.temp = this.tempLast * (this.counter) / 225;
			
		} else if(this.rpm > 11) { //quickly slows down the turbine to idle before shutdown
			this.counter = 42069; //absolutely necessary to avoid fuckeries on shutdown
			this.rpm--;
		} else if(this.rpm == 11) {
			this.counter = 225;
			this.rpm--;
		}
	}
	
	/** Dynamically calculates a (hopefully) sensible burn heat from the combustion energy, scales from 300°C - 800°C */
	protected int getFluidBurnTemp(FluidType type) {
		double dFuel = type.hasTrait(FT_Combustible.class) ? type.getTrait(FT_Combustible.class).getCombustionEnergy() : 0;
		return (int) Math.floor(800D - (Math.pow(Math.E, -dFuel / 100_000D)) * 300D);
	}
	
	private void run() {
		
		if((int) (this.throttle * 0.9) > this.rpm - this.rpmIdle) { //simulates the rotor's moment of inertia
			if(this.worldObj.getTotalWorldTime() % 5 == 0) {
				this.rpm++;
			}
		} else if((int) (this.throttle * 0.9) < this.rpm - this.rpmIdle) {
			if(this.worldObj.getTotalWorldTime() % 2 == 0) {
				this.rpm--;
			}
		}
		
		int maxTemp = getFluidBurnTemp(this.tanks[0].getTankType()); // fuelMaxTemp.get(tanks[0].getTankType())
		
		if(this.throttle * 5 * (maxTemp - this.tempIdle) / 500 > this.temp - this.tempIdle) { //simulates the heat exchanger's resistance to temperature variation
			if(this.worldObj.getTotalWorldTime() % 2 == 0) {
				this.temp++;
			}
		} else if(this.throttle * 5 * (maxTemp - this.tempIdle) / 500 < this.temp - this.tempIdle) {
			if(this.worldObj.getTotalWorldTime() % 2 == 0) {
				this.temp--;
			}
		}
		
		double consumption = TileEntityMachineTurbineGas.fuelMaxCons.containsKey(this.tanks[0].getTankType()) ? TileEntityMachineTurbineGas.fuelMaxCons.get(this.tanks[0].getTankType()) : 5D;
		if(this.worldObj.getTotalWorldTime() % 20 == 0 && this.tanks[0].getTankType() != Fluids.OXYHYDROGEN) PollutionHandler.incrementPollution(this.worldObj, this.xCoord, this.yCoord, this.zCoord, PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND * 3);
		makePower(consumption, this.throttle);
	}
	
	
	double fuelToConsume; //used to consume 1 mb of fuel at a time when consumption is <1 mb/tick
	double waterToBoil;
	double waterPerTick = 0;
	
	private void makePower(double consMax, int throttle) {
		
		double idleConsumption = consMax * 0.05D;
		double consumption = idleConsumption + consMax * throttle / 100;
		
		this.fuelToConsume += consumption;
		
		this.tanks[0].setFill(this.tanks[0].getFill() - (int) Math.floor(this.fuelToConsume));
		this.fuelToConsume -= (int) Math.floor(this.fuelToConsume);
		
		if(this.worldObj.getTotalWorldTime() % 10 == 0) //lube consumption 
			this.tanks[1].setFill(this.tanks[1].getFill() - 1);
		
		if(this.tanks[0].getFill() < 0) { //avoids negative amounts of fluid
			this.tanks[0].setFill(0);
			this.state = 0;
		}
		if(this.tanks[1].getFill() < 0) {
			this.tanks[1].setFill(0);
			this.state = 0;
		}
		
		
		long energy = 0; //energy per mb of fuel
		
		if(this.tanks[0].getTankType().hasTrait(FT_Combustible.class)) {
			energy = this.tanks[0].getTankType().getTrait(FT_Combustible.class).getCombustionEnergy() / 1000L;
		}
		
		int rpmEff = this.rpm - this.rpmIdle; // RPM above idle level, 0-90
		
		//consMax*energy is equivalent to power production at 100%
		if(this.instantPowerOutput < (consMax * energy * rpmEff / 90)) { //this shit avoids power rising in steps of 2000 or so HE at a time, instead it does it smoothly
			this.instantPowerOutput += Math.random() * 0.005 * consMax * energy;
			if(this.instantPowerOutput > (consMax * energy * rpmEff / 90))
				this.instantPowerOutput = (int) (consMax * energy * rpmEff / 90);
		}
		else if(this.instantPowerOutput > (consMax * energy * rpmEff / 90)) {
			this.instantPowerOutput -= Math.random() * 0.011 * consMax * energy;
			if(this.instantPowerOutput < (consMax * energy * rpmEff / 90))
				this.instantPowerOutput = (int) (consMax * energy * rpmEff / 90);
		}
		this.power += this.instantPowerOutput;
		
		this.waterPerTick = (consMax * energy * (this.temp - this.tempIdle) / 220000); //it just works fuck you
		
		if(this.tanks[2].getFill() >= Math.ceil(this.waterPerTick)) { //checks if there's enough water to boil
			
			this.waterToBoil += this.waterPerTick;
			
			if(this.tanks[3].getFill() <= 160000 - this.waterToBoil * 10) { //checks if there's room for steam in the tank
				
				this.tanks[2].setFill(this.tanks[2].getFill() - (int) Math.floor(this.waterToBoil));
				this.tanks[3].setFill(this.tanks[3].getFill() + 10 * (int) Math.floor(this.waterToBoil));
				this.waterToBoil -= (int) Math.floor(this.waterToBoil);
			}
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		
		this.power = nbt.getLong("power");
		this.rpm = nbt.getInteger("rpm");
		this.temp = nbt.getInteger("temp");
		this.state = nbt.getInteger("state");
		this.autoMode = nbt.getBoolean("automode");
		this.powerSliderPos = nbt.getInteger("slidpos");
		this.throttle = nbt.getInteger("throttle");			
		
		if(nbt.hasKey("counter"))
			this.counter = nbt.getInteger("counter"); //state 0 and -1
		else
			this.instantPowerOutput = nbt.getInteger("instantPow"); //state 1
		
		this.tanks[0].readFromNBT(nbt, "fuel");
		this.tanks[1].readFromNBT(nbt, "lube");
		this.tanks[2].readFromNBT(nbt, "water");
		this.tanks[3].readFromNBT(nbt, "steam");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.tanks[0].readFromNBT(nbt, "gas");
		this.tanks[1].readFromNBT(nbt, "lube");
		this.tanks[2].readFromNBT(nbt, "water");
		this.tanks[3].readFromNBT(nbt, "densesteam");
		this.autoMode = nbt.getBoolean("automode");
		this.power = nbt.getLong("power");
		this.state = nbt.getInteger("state");
		this.rpm = nbt.getInteger("rpm");
		this.temp = nbt.getInteger("temperature");
		this.powerSliderPos = nbt.getInteger("slidPos");
		this.instantPowerOutput = nbt.getInteger("instPwr");
		this.counter = nbt.getInteger("counter");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		this.tanks[0].writeToNBT(nbt, "gas");
		this.tanks[1].writeToNBT(nbt, "lube");
		this.tanks[2].writeToNBT(nbt, "water");
		this.tanks[3].writeToNBT(nbt, "densesteam");
		nbt.setBoolean("automode", this.autoMode);
		nbt.setLong("power", this.power);
		if(this.state == 1) {
			nbt.setInteger("state", this.state);
			nbt.setInteger("rpm", this.rpm);
			nbt.setInteger("temperature", this.temp);
			nbt.setInteger("slidPos", this.powerSliderPos);
			nbt.setInteger("instPwr", this.instantPowerOutput);
			nbt.setInteger("counter", 225);
		} else {
			nbt.setInteger("state", 0);
			nbt.setInteger("rpm", 0);
			nbt.setInteger("temperature", 20);
			nbt.setInteger("slidPos", 0);
			nbt.setInteger("instpwr", 0);
			nbt.setInteger("counter", 0);
		}
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		
		if(data.hasKey("slidPos"))
			this.powerSliderPos = data.getInteger("slidPos");
		
		if(data.hasKey("autoMode"))
			this.autoMode = data.getBoolean("autoMode");
		
		if(data.hasKey("state"))
			this.state = data.getInteger("state");

		markDirty();
	}
	
	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(this.xCoord - player.posX, this.yCoord - player.posY, this.zCoord - player.posZ).lengthVector() < 25;
	}
	
	@Override
	public void onChunkUnload() {

		if(this.audio != null) {
			this.audio.stopSound();
			this.audio = null;
		}
	}

	@Override
	public void invalidate() {

		super.invalidate();

		if(this.audio != null) {
			this.audio.stopSound();
			this.audio = null;
		}
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
		return TileEntityMachineTurbineGas.maxPower;
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(
					this.xCoord - 5,
					this.yCoord,
					this.zCoord - 5,
					this.xCoord + 6,
					this.yCoord + 3,
					this.zCoord + 6
					);
		}
		
		return this.bb;
	}
		
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public String getName() {
		return "container.turbinegas";
	}

	@Override
	public FluidTank[] getAllTanks() {
		return this.tanks;
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { this.tanks[0], this.tanks[1], this.tanks[2] };
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { this.tanks[3] };
	}
	
	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.DOWN;
	}

	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir != ForgeDirection.DOWN;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineTurbineGas(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineTurbineGas(player.inventory, this);
	}
}