package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IEnergyGenerator;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineMiniRTG extends TileEntityLoadedBase implements IEnergyGenerator {

	public long power;
	boolean tact = false;
	
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {

			if(getBlockType() == ModBlocks.machine_powerrtg)
				this.power += 2500;
			else
				this.power += 700;
			
			if(this.power > getMaxPower())
				this.power = getMaxPower();

			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
				sendPower(this.worldObj, this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ, dir);
		}
	}


	@Override
	public long getMaxPower() {
		
		if(getBlockType() == ModBlocks.machine_powerrtg)
			return 50000;
		
		return 1400;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}
}
