package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IEnergyGenerator;
import net.minecraft.init.Blocks;

public class TileEntityMachineSPP extends TileEntityLoadedBase implements IEnergyGenerator {
	
	public long power;
	public static final long maxPower = 100000;
	public int age = 0;
	public int gen = 0;
	
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {

			sendPower(this.worldObj, this.xCoord + 1, this.yCoord, this.zCoord, Library.POS_X);
			sendPower(this.worldObj, this.xCoord - 1, this.yCoord, this.zCoord, Library.NEG_X);
			sendPower(this.worldObj, this.xCoord, this.yCoord, this.zCoord + 1, Library.POS_Z);
			sendPower(this.worldObj, this.xCoord, this.yCoord, this.zCoord - 1, Library.NEG_Z);
			sendPower(this.worldObj, this.xCoord, this.yCoord - 1, this.zCoord, Library.NEG_Y);
			
			if(this.worldObj.getTotalWorldTime() % 20 == 0)
				this.gen = checkStructure() * 15;
			
			if(this.gen > 0)
				this.power += this.gen;
			if(this.power > TileEntityMachineSPP.maxPower)
				this.power = TileEntityMachineSPP.maxPower;
		}
		
	}
	
	public int checkStructure() {

		int h = 0;
		
		for(int i = this.yCoord + 1; i < 254; i++)
			if(this.worldObj.getBlock(this.xCoord, i, this.zCoord) == ModBlocks.machine_spp_top) {
				h = i;
				break;
			}
		
		for(int i = this.yCoord + 1; i < h; i++)
			if(!checkSegment(i))
				return 0;

		
		return h - this.yCoord - 1;
	}
	
	public boolean checkSegment(int y) {
		
		//   BBB
		//   BAB
		//   BBB
		
		return (this.worldObj.getBlock(this.xCoord + 1, y, this.zCoord) != Blocks.air &&
				this.worldObj.getBlock(this.xCoord + 1, y, this.zCoord + 1) != Blocks.air &&
				this.worldObj.getBlock(this.xCoord + 1, y, this.zCoord - 1) != Blocks.air &&
				this.worldObj.getBlock(this.xCoord - 1, y, this.zCoord + 1) != Blocks.air &&
				this.worldObj.getBlock(this.xCoord - 1, y, this.zCoord) != Blocks.air &&
				this.worldObj.getBlock(this.xCoord - 1, y, this.zCoord - 1) != Blocks.air &&
				this.worldObj.getBlock(this.xCoord, y, this.zCoord + 1) != Blocks.air &&
				this.worldObj.getBlock(this.xCoord, y, this.zCoord - 1) != Blocks.air &&
				this.worldObj.getBlock(this.xCoord, y, this.zCoord) == Blocks.air);
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getMaxPower() {
		return TileEntityMachineSPP.maxPower;
	}

}
