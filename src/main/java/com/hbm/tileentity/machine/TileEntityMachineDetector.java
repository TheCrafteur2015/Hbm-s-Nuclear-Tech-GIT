package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IEnergyUser;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineDetector extends TileEntityLoadedBase implements IEnergyUser {
	
	long power;

	@Override
    public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			updateConnections();
			
			int meta = getBlockMetadata();
			int state = 0;
			
			if(this.power > 0) {
				state = 1;
				this.power--;
			}
			
			if(meta != state) {
				this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, state, 3);
				markDirty();
			}
		}
	}
	
	private void updateConnections() {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			trySubscribe(this.worldObj, this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ, dir);
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
		return 5;
	}

	@Override
	public ConnectionPriority getPriority() {
		return ConnectionPriority.HIGH;
	}

}
