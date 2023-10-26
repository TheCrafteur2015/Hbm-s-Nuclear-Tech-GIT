package com.hbm.tileentity.machine;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardTransceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityDeuteriumExtractor extends TileEntityMachineBase implements IEnergyUser, IFluidStandardTransceiver {
	
	public long power = 0;
	public FluidTank[] tanks;

	public TileEntityDeuteriumExtractor() {
		super(0);
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.WATER, 1000);
		this.tanks[1] = new FluidTank(Fluids.HEAVYWATER, 100);
	}

	@Override
	public String getName() {
		return "container.deuterium";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			updateConnections();
			
			if(hasPower() && hasEnoughWater() && this.tanks[1].getMaxFill() > this.tanks[1].getFill()) {
				int convert = Math.min(this.tanks[1].getMaxFill(), this.tanks[0].getFill()) / 50;
				convert = Math.min(convert, this.tanks[1].getMaxFill() - this.tanks[1].getFill());
				
				this.tanks[0].setFill(this.tanks[0].getFill() - convert * 50); //dividing first, then multiplying, will remove any rounding issues
				this.tanks[1].setFill(this.tanks[1].getFill() + convert);
				this.power -= getMaxPower() / 20;
			}
			
			this.subscribeToAllAround(this.tanks[0].getTankType(), this);
			this.sendFluidToAll(this.tanks[1], this);

			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			this.tanks[0].writeToNBT(data, "water");
			this.tanks[1].writeToNBT(data, "heavyWater");
			
			networkPack(data, 50);
		}
	}
	
	protected void updateConnections() {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			this.trySubscribe(this.worldObj, this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ, dir);
	}

	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.power = data.getLong("power");
		this.tanks[0].readFromNBT(data, "water");
		this.tanks[1].readFromNBT(data, "heavyWater");
	}

	public boolean hasPower() {
		return this.power >= getMaxPower() / 20;
	}

	public boolean hasEnoughWater() {
		return this.tanks[0].getFill() >= 100;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.power = nbt.getLong("power");
		this.tanks[0].readFromNBT(nbt, "water");
		this.tanks[1].readFromNBT(nbt, "heavyWater");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", this.power);
		this.tanks[0].writeToNBT(nbt, "water");
		this.tanks[1].writeToNBT(nbt, "heavyWater");
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
		return 10_000;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { this.tanks[1] };
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { this.tanks[0] };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return this.tanks;
	}
}