package com.hbm.tileentity.machine.rbmk;

import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.fluid.IFluidStandardReceiver;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRBMKInlet extends TileEntityLoadedBase implements IFluidStandardReceiver {
	
	public FluidTank water;
	
	public TileEntityRBMKInlet() {
		this.water = new FluidTank(Fluids.WATER, 32000);
	}
	
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			this.subscribeToAllAround(this.water.getTankType(), this);
			
			for(int i = 2; i < 6; i++) {
				ForgeDirection dir = ForgeDirection.getOrientation(i);
				Block b = this.worldObj.getBlock(this.xCoord + dir.offsetX, this.yCoord, this.zCoord + dir.offsetZ);
				
				if(b instanceof RBMKBase) {
					int[] pos = ((RBMKBase)b).findCore(this.worldObj, this.xCoord + dir.offsetX, this.yCoord, this.zCoord + dir.offsetZ);
					
					if(pos != null) {
						TileEntity te = this.worldObj.getTileEntity(pos[0], pos[1], pos[2]);
						
						if(te instanceof TileEntityRBMKBase) {
							TileEntityRBMKBase rbmk = (TileEntityRBMKBase) te;
							
							int prov = Math.min(TileEntityRBMKBase.maxWater - rbmk.water, this.water.getFill());
							rbmk.water += prov;
							this.water.setFill(this.water.getFill() - prov);
						}
					}
				}
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.water.readFromNBT(nbt, "tank");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.water.writeToNBT(nbt, "tank");
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {this.water};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {this.water};
	}

}
