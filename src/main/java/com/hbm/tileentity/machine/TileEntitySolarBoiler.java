package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;

public class TileEntitySolarBoiler extends TileEntityLoadedBase implements IFluidAcceptor, IFluidSource, IFluidStandardTransceiver {

	private FluidTank water;
	private FluidTank steam;
	public List<IFluidAcceptor> list = new ArrayList<>();
	public int heat;

	public HashSet<ChunkCoordinates> primary = new HashSet<>();
	public HashSet<ChunkCoordinates> secondary = new HashSet<>();
	
	public TileEntitySolarBoiler() {
		this.water = new FluidTank(Fluids.WATER, 16000);
		this.steam = new FluidTank(Fluids.STEAM, 1600000);
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			//if(worldObj.getTotalWorldTime() % 5 == 0) {
			fillFluidInit(Fluids.STEAM);
			//}

			trySubscribe(this.water.getTankType(), this.worldObj, this.xCoord, this.yCoord + 3, this.zCoord, Library.POS_Y);
			trySubscribe(this.water.getTankType(), this.worldObj, this.xCoord, this.yCoord - 1, this.zCoord, Library.NEG_Y);
			
			int process = this.heat / 50;
			process = Math.min(process, this.water.getFill());
			process = Math.min(process, (this.steam.getMaxFill() - this.steam.getFill()) / 100);
			
			if(process < 0)
				process = 0;

			this.water.setFill(this.water.getFill() - process);
			this.steam.setFill(this.steam.getFill() + process * 100);

			this.sendFluid(this.steam, this.worldObj, this.xCoord, this.yCoord + 3, this.zCoord, Library.POS_Y);
			this.sendFluid(this.steam, this.worldObj, this.xCoord, this.yCoord - 1, this.zCoord, Library.NEG_Y);
			
			this.heat = 0;
		} else {
			
			//a delayed queue of mirror positions because we can't expect the boiler to always tick first
			this.secondary.clear();
			this.secondary.addAll(this.primary);
			this.primary.clear();
		}
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if(index == 0)
			this.water.setFill(fill);
		if(index == 1)
			this.steam.setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		if(type == Fluids.WATER)
			this.water.setFill(fill);
		if(type == Fluids.STEAM)
			this.steam.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if(index == 0)
			this.water.setTankType(type);
		if(index == 1)
			this.steam.setTankType(type);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type == Fluids.WATER)
			return this.water.getFill();
		if(type == Fluids.STEAM)
			return this.steam.getFill();
		
		return 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(this.xCoord, this.yCoord + 3, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord - 1, this.zCoord, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, this.worldObj, type);
	}
	
	@Override
	public boolean getTact() {
		return this.worldObj.getTotalWorldTime() % 2 == 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type == Fluids.WATER)
			return this.water.getMaxFill();
		if(type == Fluids.STEAM)
			return this.steam.getMaxFill();
		
		return 0;
	}
	
	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return this.list;
	}
	
	@Override
	public void clearFluidList(FluidType type) {
		this.list.clear();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.water.readFromNBT(nbt, "water");
		this.steam.readFromNBT(nbt, "steam");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		this.water.writeToNBT(nbt, "water");
		this.steam.writeToNBT(nbt, "steam");
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(
					this.xCoord - 1,
					this.yCoord,
					this.zCoord - 1,
					this.xCoord + 2,
					this.yCoord + 3,
					this.zCoord + 2
					);
		}
		
		return this.bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { this.steam };
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { this.water };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { this.water, this.steam };
	}
}
