package com.hbm.tileentity.machine;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.util.fauxpointtwelve.DirPos;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityMachinePumpSteam extends TileEntityMachinePumpBase {

	public FluidTank steam;
	public FluidTank lps;
	
	public TileEntityMachinePumpSteam() {
		super();
		this.water = new FluidTank(Fluids.WATER, 100_000);
		this.steam = new FluidTank(Fluids.STEAM, 1_000);
		this.lps = new FluidTank(Fluids.SPENTSTEAM, 10);
	}
	
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			for(DirPos pos : getConPos()) {
				trySubscribe(this.steam.getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				if(this.lps.getFill() > 0) {
					this.sendFluid(this.lps, this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				}
			}
		}
		
		super.updateEntity();
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {this.water, this.steam, this.lps};
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {this.water, this.lps};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {this.steam};
	}
	
	@Override
	protected NBTTagCompound getSync() {
		NBTTagCompound data = super.getSync();
		this.steam.writeToNBT(data, "s");
		this.lps.writeToNBT(data, "l");
		return data;
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		super.networkUnpack(nbt);
		this.steam.readFromNBT(nbt, "s");
		this.lps.readFromNBT(nbt, "l");
	}

	@Override
	protected boolean canOperate() {
		return this.steam.getFill() >= 100 && this.lps.getMaxFill() - this.lps.getFill() > 0 && this.water.getFill() < this.water.getMaxFill();
	}

	@Override
	protected void operate() {
		this.steam.setFill(this.steam.getFill() - 100);
		this.lps.setFill(this.lps.getFill() + 1);
		this.water.setFill(Math.min(this.water.getFill() + 1000, this.water.getMaxFill()));
	}
}
