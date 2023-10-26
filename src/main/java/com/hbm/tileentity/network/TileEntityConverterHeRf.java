package com.hbm.tileentity.network;

import com.hbm.calc.Location;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IEnergyConnector;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityConverterHeRf extends TileEntityLoadedBase implements IEnergyConnector, IEnergyHandler {
	
	//Thanks to the great people of Fusion Warfare for helping me with the original implementation of the RF energy API

	@Override
	public void updateEntity() {
		
		if (!this.worldObj.isRemote) {
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
				trySubscribe(this.worldObj, this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ, dir);
		}
	}
	
	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return 0;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return 0;
	}

	@Override
	public long getPower() {
		return 0;
	}

	@Override
	public long getMaxPower() {
		return Integer.MAX_VALUE / 4;
	}

	private long lastTransfer = 0;
	
	@Override
	public long getTransferWeight() {
		
		if(this.lastTransfer > 0) {
			return this.lastTransfer * 2;
		} else {
			return getMaxPower();
		}
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return 0;
	}
	
	private boolean recursionBrake = false;
	
	@Override
	public long transferPower(long power) {
		
		if(this.recursionBrake)
			return power;
		
		this.recursionBrake = true;
		
		// we have to limit the transfer amount because otherwise FEnSUs would overflow the RF output, twice
		long out = Math.min(power, Long.MAX_VALUE / 4);
		int toRF = (int) Math.min(Integer.MAX_VALUE, out * 4);
		int rfTransferred = 0;
		int totalTransferred = 0;

		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {

			Location loc = new Location(this.worldObj, this.xCoord, this.yCoord, this.zCoord).add(dir);
			TileEntity entity = loc.getTileEntity();

			if(entity != null && entity instanceof IEnergyReceiver) {
				
				IEnergyReceiver receiver = (IEnergyReceiver) entity;
				rfTransferred = receiver.receiveEnergy(dir.getOpposite(), toRF, false);
				totalTransferred += rfTransferred;
				
				toRF -= rfTransferred; //to prevent energy duping
			}
		}

		this.recursionBrake = false;
		this.lastTransfer = totalTransferred / 4;
		
		return power - (totalTransferred / 4);
	}
}
