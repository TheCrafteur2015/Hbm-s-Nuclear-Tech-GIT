package com.hbm.tileentity;

import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;

import api.hbm.fluid.IFluidUser;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityMachinePolluting extends TileEntityMachineBase implements IFluidUser {

	public FluidTank smoke;
	public FluidTank smoke_leaded;
	public FluidTank smoke_poison;

	public TileEntityMachinePolluting(int scount, int buffer) {
		super(scount);
		this.smoke = new FluidTank(Fluids.SMOKE, buffer);
		this.smoke_leaded = new FluidTank(Fluids.SMOKE_LEADED, buffer);
		this.smoke_poison = new FluidTank(Fluids.SMOKE_POISON, buffer);
	}
	
	public void pollute(PollutionType type, float amount) {
		FluidTank tank = type == PollutionType.SOOT ? this.smoke : type == PollutionType.HEAVYMETAL ? this.smoke_leaded : this.smoke_poison;
		
		int fluidAmount = (int) Math.ceil(amount * 100);
		tank.setFill(tank.getFill() + fluidAmount);
		
		if(tank.getFill() > tank.getMaxFill()) {
			int overflow = tank.getFill() - tank.getMaxFill();
			tank.setFill(tank.getMaxFill());
			PollutionHandler.incrementPollution(this.worldObj, this.xCoord, this.yCoord, this.zCoord, type, overflow / 100F);
			
			if(this.worldObj.rand.nextInt(3) == 0) this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "random.fizz", 0.1F, 1.5F);
		}
	}
	
	public void sendSmoke(int x, int y, int z, ForgeDirection dir) {
		if(this.smoke.getFill() > 0) this.sendFluid(this.smoke, this.worldObj, x, y, z, dir);
		if(this.smoke_leaded.getFill() > 0) this.sendFluid(this.smoke_leaded, this.worldObj, x, y, z, dir);
		if(this.smoke_poison.getFill() > 0) this.sendFluid(this.smoke_poison, this.worldObj, x, y, z, dir);
	}
	
	public FluidTank[] getSmokeTanks() {
		return new FluidTank[] {this.smoke, this.smoke_leaded, this.smoke_poison};
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.smoke.readFromNBT(nbt, "smoke0");
		this.smoke_leaded.readFromNBT(nbt, "smoke1");
		this.smoke_poison.readFromNBT(nbt, "smoke2");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		this.smoke.writeToNBT(nbt, "smoke0");
		this.smoke_leaded.writeToNBT(nbt, "smoke1");
		this.smoke_poison.writeToNBT(nbt, "smoke2");
	}
}
