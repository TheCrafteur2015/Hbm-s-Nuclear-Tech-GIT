package com.hbm.tileentity.machine.oil;

import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.container.ContainerMachineOilWell;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineOilWell;
import com.hbm.lib.Library;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.util.fauxpointtwelve.DirPos;
import com.hbm.world.feature.OilSpot;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

public class TileEntityMachineFrackingTower extends TileEntityOilDrillBase implements IFluidAcceptor {

	protected static int maxPower = 5_000_000;
	protected static int consumption = 5000;
	protected static int solutionRequired = 10;
	protected static int delay = 20;
	protected static int oilPerDepsoit = 1000;
	protected static int gasPerDepositMin = 100;
	protected static int gasPerDepositMax = 500;
	protected static double drainChance = 0.02D;
	protected static int oilPerBedrockDepsoit = 100;
	protected static int gasPerBedrockDepositMin = 10;
	protected static int gasPerBedrockDepositMax = 50;
	protected static int destructionRange = 75;

	public TileEntityMachineFrackingTower() {
		super();
		this.tanks = new FluidTank[3];
		this.tanks[0] = new FluidTank(Fluids.OIL, 64_000);
		this.tanks[1] = new FluidTank(Fluids.GAS, 64_000);
		this.tanks[2] = new FluidTank(Fluids.FRACKSOL, 64_000);
	}

	@Override
	public String getName() {
		return "container.frackingTower";
	}

	@Override
	public long getMaxPower() {
		return TileEntityMachineFrackingTower.maxPower;
	}

	@Override
	public int getPowerReq() {
		return TileEntityMachineFrackingTower.consumption;
	}

	@Override
	public int getDelay() {
		return TileEntityMachineFrackingTower.delay;
	}

	@Override
	public int getDrillDepth() {
		return 0;
	}

	@Override
	public boolean canPump() {
		boolean b = this.tanks[2].getFill() >= TileEntityMachineFrackingTower.solutionRequired;
		
		if(!b) {
			this.indicator = 3;
		}
		
		return b;
	}

	@Override
	public boolean canSuckBlock(Block b) {
		return super.canSuckBlock(b) || b == ModBlocks.ore_bedrock_oil;
	}

	@Override
	public void doSuck(int x, int y, int z) {
		super.doSuck(x, y, z);
		
		if(this.worldObj.getBlock(x, y, z) == ModBlocks.ore_bedrock_oil) {
			onSuck(x, y, z);
		}
	}

	@Override
	public void onSuck(int x, int y, int z) {
		
		Block b = this.worldObj.getBlock(x, y, z);
		
		int oil = 0;
		int gas = 0;

		if(b == ModBlocks.ore_oil) {
			oil = TileEntityMachineFrackingTower.oilPerDepsoit;
			gas = TileEntityMachineFrackingTower.gasPerDepositMin + this.worldObj.rand.nextInt(TileEntityMachineFrackingTower.gasPerDepositMax - TileEntityMachineFrackingTower.gasPerDepositMin + 1);
			
			if(this.worldObj.rand.nextDouble() < TileEntityMachineFrackingTower.drainChance) {
				this.worldObj.setBlock(x, y, z, ModBlocks.ore_oil_empty);
			}
		}
		if(b == ModBlocks.ore_bedrock_oil) {
			oil = TileEntityMachineFrackingTower.oilPerBedrockDepsoit;
			gas = TileEntityMachineFrackingTower.gasPerBedrockDepositMin + this.worldObj.rand.nextInt(TileEntityMachineFrackingTower.gasPerBedrockDepositMax - TileEntityMachineFrackingTower.gasPerBedrockDepositMin + 1);
		}
		
		this.tanks[0].setFill(this.tanks[0].getFill() + oil);
		if(this.tanks[0].getFill() > this.tanks[0].getMaxFill()) this.tanks[0].setFill(this.tanks[0].getMaxFill());
		this.tanks[1].setFill(this.tanks[1].getFill() + gas);
		if(this.tanks[1].getFill() > this.tanks[1].getMaxFill()) this.tanks[1].setFill(this.tanks[1].getMaxFill());
		
		this.tanks[2].setFill(this.tanks[2].getFill() - TileEntityMachineFrackingTower.solutionRequired);

		OilSpot.generateOilSpot(this.worldObj, this.xCoord, this.zCoord, TileEntityMachineFrackingTower.destructionRange, 10, false);
	}

	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord - 1, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord + 1, getTact(), type);
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		return type == this.tanks[2].getTankType() ? this.tanks[2].getMaxFill() : 0;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { this.tanks[0], this.tanks[1] };
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { this.tanks[2] };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return this.tanks;
	}

	@Override
	public DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(this.xCoord + 1, this.yCoord, this.zCoord, Library.POS_X),
				new DirPos(this.xCoord - 1, this.yCoord, this.zCoord, Library.NEG_X),
				new DirPos(this.xCoord, this.yCoord, this.zCoord + 1, Library.POS_Z),
				new DirPos(this.xCoord, this.yCoord, this.zCoord - 1, Library.NEG_Z)
		};
	}

	@Override
	protected void updateConnections() {
		for(DirPos pos : getConPos()) {
			this.trySubscribe(this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(this.tanks[2].getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}

	@Override
	public String getConfigName() {
		return "frackingtower";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		TileEntityMachineFrackingTower.maxPower = IConfigurableMachine.grab(obj, "I:powerCap", TileEntityMachineFrackingTower.maxPower);
		TileEntityMachineFrackingTower.consumption = IConfigurableMachine.grab(obj, "I:consumption", TileEntityMachineFrackingTower.consumption);
		TileEntityMachineFrackingTower.solutionRequired = IConfigurableMachine.grab(obj, "I:solutionRequired", TileEntityMachineFrackingTower.solutionRequired);
		TileEntityMachineFrackingTower.delay = IConfigurableMachine.grab(obj, "I:delay", TileEntityMachineFrackingTower.delay);
		TileEntityMachineFrackingTower.oilPerDepsoit = IConfigurableMachine.grab(obj, "I:oilPerDeposit", TileEntityMachineFrackingTower.oilPerDepsoit);
		TileEntityMachineFrackingTower.gasPerDepositMin = IConfigurableMachine.grab(obj, "I:gasPerDepositMin", TileEntityMachineFrackingTower.gasPerDepositMin);
		TileEntityMachineFrackingTower.gasPerDepositMax = IConfigurableMachine.grab(obj, "I:gasPerDepositMax", TileEntityMachineFrackingTower.gasPerDepositMax);
		TileEntityMachineFrackingTower.drainChance = IConfigurableMachine.grab(obj, "D:drainChance", TileEntityMachineFrackingTower.drainChance);
		TileEntityMachineFrackingTower.oilPerBedrockDepsoit = IConfigurableMachine.grab(obj, "I:oilPerBedrockDeposit", TileEntityMachineFrackingTower.oilPerBedrockDepsoit);
		TileEntityMachineFrackingTower.gasPerBedrockDepositMin = IConfigurableMachine.grab(obj, "I:gasPerBedrockDepositMin", TileEntityMachineFrackingTower.gasPerBedrockDepositMin);
		TileEntityMachineFrackingTower.gasPerBedrockDepositMax = IConfigurableMachine.grab(obj, "I:gasPerBedrockDepositMax", TileEntityMachineFrackingTower.gasPerBedrockDepositMax);
		TileEntityMachineFrackingTower.destructionRange = IConfigurableMachine.grab(obj, "I:destructionRange", TileEntityMachineFrackingTower.destructionRange);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("I:powerCap").value(TileEntityMachineFrackingTower.maxPower);
		writer.name("I:consumption").value(TileEntityMachineFrackingTower.consumption);
		writer.name("I:solutionRequired").value(TileEntityMachineFrackingTower.solutionRequired);
		writer.name("I:delay").value(TileEntityMachineFrackingTower.delay);
		writer.name("I:oilPerDeposit").value(TileEntityMachineFrackingTower.oilPerDepsoit);
		writer.name("I:gasPerDepositMin").value(TileEntityMachineFrackingTower.gasPerDepositMin);
		writer.name("I:gasPerDepositMax").value(TileEntityMachineFrackingTower.gasPerDepositMax);
		writer.name("D:drainChance").value(TileEntityMachineFrackingTower.drainChance);
		writer.name("I:oilPerBedrockDeposit").value(TileEntityMachineFrackingTower.oilPerBedrockDepsoit);
		writer.name("I:gasPerBedrockDepositMin").value(TileEntityMachineFrackingTower.gasPerBedrockDepositMin);
		writer.name("I:gasPerBedrockDepositMax").value(TileEntityMachineFrackingTower.gasPerBedrockDepositMax);
		writer.name("I:destructionRange").value(TileEntityMachineFrackingTower.destructionRange);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineOilWell(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineOilWell(player.inventory, this);
	}
}
