package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IEnergyGenerator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineAmgen extends TileEntityLoadedBase implements IEnergyGenerator {

	public long power;
	public long maxPower = 500;
	
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {

			Block block = this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord);
			
			if(block == ModBlocks.machine_amgen) {
				float rad = ChunkRadiationManager.proxy.getRadiation(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
				this.power += rad;
				ChunkRadiationManager.proxy.decrementRad(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 5F);
				
			} else if(block == ModBlocks.machine_geo) {
				checkGeoInteraction(this.xCoord, this.yCoord + 1, this.zCoord);
				checkGeoInteraction(this.xCoord, this.yCoord - 1, this.zCoord);
			}
			
			if(this.power > this.maxPower)
				this.power = this.maxPower;
			
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
				sendPower(this.worldObj, this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ, dir);
		}
	}
	
	private void checkGeoInteraction(int x, int y, int z) {
		
		Block b = this.worldObj.getBlock(x, y, z);
		
		if(b == ModBlocks.geysir_water) {
			this.power += 75;
		} else if(b == ModBlocks.geysir_chlorine) {
			this.power += 100;
		} else if(b == ModBlocks.geysir_vapor) {
			this.power += 50;
		} else if(b == ModBlocks.geysir_nether) {
			this.power += 500;
		} else if(b == Blocks.lava) {
			this.power += 100;
			
			if(this.worldObj.rand.nextInt(6000) == 0) {
				this.worldObj.setBlock(this.xCoord, this.yCoord - 1, this.zCoord, Blocks.obsidian);
			}
		} else if(b == Blocks.flowing_lava) {
			this.power += 25;
			
			if(this.worldObj.rand.nextInt(3000) == 0) {
				this.worldObj.setBlock(this.xCoord, this.yCoord - 1, this.zCoord, Blocks.cobblestone);
			}
		}
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
		return this.maxPower;
	}
}
