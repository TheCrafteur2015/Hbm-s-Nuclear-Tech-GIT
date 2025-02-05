package com.hbm.tileentity.machine.pile;

import com.hbm.blocks.machine.pile.BlockGraphiteNeutronDetector;

import api.hbm.block.IPileNeutronReceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPileNeutronDetector extends TileEntity implements IPileNeutronReceiver {
	
	public int lastNeutrons;
	public int neutrons;
	public int maxNeutrons = 10;
	
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			//lastNeutrons is used to reduce the responsiveness of control rods; should cut down on sound/updates whilst keeping them still useful for automatic control.
			//Even with it, the auto rods are *very* subject to triggering on and off rapidly - this is necessary, as rays in smaller piles aren't guarenteed to consistently flood all surrounding areas
			if(this.neutrons >= this.maxNeutrons && (getBlockMetadata() & 8) > 0)
				((BlockGraphiteNeutronDetector)this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord)).triggerRods(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			if(this.neutrons < this.maxNeutrons && this.lastNeutrons < this.maxNeutrons && (getBlockMetadata() & 8) == 0)
				((BlockGraphiteNeutronDetector)this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord)).triggerRods(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			
			this.lastNeutrons = this.neutrons;
			this.neutrons = 0;
		}
	}

	@Override
	public void receiveNeutrons(int n) {
		this.neutrons += n;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("maxNeutrons", this.maxNeutrons);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.maxNeutrons = nbt.getInteger("maxNeutrons");
	}
}
