package com.hbm.tileentity.machine.pile;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import api.hbm.block.IPileNeutronReceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;

public class TileEntityPileFuel extends TileEntityPileBase implements IPileNeutronReceiver {

	public int heat;
	public static final int maxHeat = 1000;
	public int neutrons;
	public int lastNeutrons;
	public int progress;
	public static final int maxProgress = GeneralConfig.enable528 ? 75000 : 50000; //might double to reduce compact setup's effectiveness

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			dissipateHeat();
			checkRedstone(react());
			transmute();
			
			if(this.heat >= TileEntityPileFuel.maxHeat) {
				this.worldObj.newExplosion(null, this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, 4, true, true);
				this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, ModBlocks.gas_radon_dense);
			}
			
			if(this.worldObj.rand.nextFloat() * 2F <= this.heat / (float)TileEntityPileFuel.maxHeat) {
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "vanillaExt");
				data.setString("mode", "smoke");
				data.setDouble("mY", 0.05);
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, this.xCoord + 0.25 + this.worldObj.rand.nextDouble() * 0.5, this.yCoord + 1, this.zCoord + 0.25 + this.worldObj.rand.nextDouble() * 0.5),
						new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord + 0.5, this.yCoord + 1, this.zCoord + 0.5, 20));
				MainRegistry.proxy.effectNT(data);
			}
			
			if(this.progress >= TileEntityPileFuel.maxProgress) {
				this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, ModBlocks.block_graphite_plutonium, getBlockMetadata() & 7, 3);
			}
		}
		
	}
	
	private void dissipateHeat() {
		this.heat -= (getBlockMetadata() & 4) == 4 ? this.heat * 0.065 : this.heat * 0.05; //remove 5% of the stored heat per tick; 6.5% for windscale
	}
	
	private int react() {
		
		int reaction = (int) (this.neutrons * (1D - ((double)this.heat / (double)TileEntityPileFuel.maxHeat) * 0.5D)); //max heat reduces reaction by 50% due to thermal expansion
		
		this.lastNeutrons = this.neutrons;
		this.neutrons = 0;
		
		int lastProgress = this.progress;
		
		this.progress += reaction;
		
		if(reaction <= 0)
			return lastProgress;
		
		this.heat += reaction;
		
		for(int i = 0; i < 12; i++)
			castRay((int) Math.max(reaction * 0.25, 1), 5);
		
		return lastProgress;
	}
	
	private void checkRedstone(int lastProgress) {
		int lastLevel = MathHelper.clamp_int((lastProgress * 16) / TileEntityPileFuel.maxProgress, 0, 15);
		int newLevel = MathHelper.clamp_int((this.progress * 16) / TileEntityPileFuel.maxProgress, 0, 15);
		if(lastLevel != newLevel)
			this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, getBlockType());
	}
	
	private void transmute() {
		
		if((getBlockMetadata() & 8) == 8) {
			if(this.progress < TileEntityPileFuel.maxProgress - 1000) //Might be subject to change, but 1000 seems like a good number.
				this.progress = TileEntityPileFuel.maxProgress - 1000;
			
			return;
		} else if(this.progress >= TileEntityPileFuel.maxProgress - 1000) {
			this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, getBlockMetadata() | 8, 3);
			return;
		}
	}

	@Override
	public void receiveNeutrons(int n) {
		this.neutrons += n;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.heat = nbt.getInteger("heat");
		this.progress = nbt.getInteger("progress");
		this.neutrons = nbt.getInteger("neutrons");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("heat", this.heat);
		nbt.setInteger("progress", this.progress);
		nbt.setInteger("neutrons", this.neutrons);
	}
}
