package com.hbm.tileentity.machine.rbmk;

import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.fluid.IFluidStandardSender;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRBMKOutlet extends TileEntityLoadedBase implements IFluidStandardSender {
	
	public FluidTank steam;
	
	public TileEntityRBMKOutlet() {
		this.steam = new FluidTank(Fluids.SUPERHOTSTEAM, 32000);
	}
	
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			for(int i = 2; i < 6; i++) {
				ForgeDirection dir = ForgeDirection.getOrientation(i);
				Block b = this.worldObj.getBlock(this.xCoord + dir.offsetX, this.yCoord, this.zCoord + dir.offsetZ);
				
				if(b instanceof RBMKBase) {
					int[] pos = ((RBMKBase)b).findCore(this.worldObj, this.xCoord + dir.offsetX, this.yCoord, this.zCoord + dir.offsetZ);
					
					if(pos != null) {
						TileEntity te = this.worldObj.getTileEntity(pos[0], pos[1], pos[2]);
						
						if(te instanceof TileEntityRBMKBase) {
							TileEntityRBMKBase rbmk = (TileEntityRBMKBase) te;
							
							int prov = Math.min(this.steam.getMaxFill() - this.steam.getFill(), rbmk.steam);
							rbmk.steam -= prov;
							this.steam.setFill(this.steam.getFill() + prov);
						}
					}
				}
			}
			
			fillFluidInit();
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.steam.readFromNBT(nbt, "tank");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.steam.writeToNBT(nbt, "tank");
	}

	public void fillFluidInit() {
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			this.sendFluid(this.steam, this.worldObj, this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ, dir);
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {this.steam};
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {this.steam};
	}

}
