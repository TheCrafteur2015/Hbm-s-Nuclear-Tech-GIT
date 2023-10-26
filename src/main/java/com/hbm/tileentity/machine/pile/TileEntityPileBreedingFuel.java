package com.hbm.tileentity.machine.pile;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;

import api.hbm.block.IPileNeutronReceiver;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityPileBreedingFuel extends TileEntityPileBase implements IPileNeutronReceiver {
	
	public int neutrons;
	public int lastNeutrons;
	public int progress;
	public static final int maxProgress = GeneralConfig.enable528 ? 50000 : 30000;
	
	@Override
	public void updateEntity() {
		if(!this.worldObj.isRemote) {
			react();
			
			if(this.progress >= TileEntityPileBreedingFuel.maxProgress) {
				this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, ModBlocks.block_graphite_tritium, getBlockMetadata(), 3);
			}
		}
	}
	
	private void react() {
		
		this.lastNeutrons = this.neutrons;
		this.progress += this.neutrons;
		
		this.neutrons = 0;
		
		if(this.lastNeutrons <= 0)
			return;
		
		for(int i = 0; i < 2; i++)
			castRay(1, 5);
	}
	
	@Override
	public void receiveNeutrons(int n) {
		this.neutrons += n;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.progress = nbt.getInteger("progress");
		this.neutrons = nbt.getInteger("neutrons");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("progress", this.progress);
		nbt.setInteger("neutrons", this.neutrons);
	}
}
