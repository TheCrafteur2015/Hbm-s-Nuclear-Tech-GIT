package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.Library;
import com.hbm.saveddata.TomSaveData;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.fluid.IFluidStandardTransceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCondenser extends TileEntityLoadedBase implements IFluidAcceptor, IFluidSource, IFluidStandardTransceiver, INBTPacketReceiver {

	public int age = 0;
	public FluidTank[] tanks;
	public List<IFluidAcceptor> list = new ArrayList<>();
	
	public int waterTimer = 0;
	
	public TileEntityCondenser() {
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.SPENTSTEAM, 100);
		this.tanks[1] = new FluidTank(Fluids.WATER, 100);
	}
	
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			this.age++;
			if(this.age >= 2) {
				this.age = 0;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			this.tanks[0].writeToNBT(data, "0");
			
			if(this.waterTimer > 0)
				this.waterTimer--;

			int convert = Math.min(this.tanks[0].getFill(), this.tanks[1].getMaxFill() - this.tanks[1].getFill());
			if(extraCondition(convert)) {
				this.tanks[0].setFill(this.tanks[0].getFill() - convert);
				
				if(convert > 0)
					this.waterTimer = 20;
				
				int light = this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, this.xCoord, this.yCoord, this.zCoord);
				
				if(TomSaveData.forWorld(this.worldObj).fire > 1e-5 && light > 7) { // Make both steam and water evaporate during firestorms...
					this.tanks[1].setFill(this.tanks[1].getFill() - convert);
				} else {
					this.tanks[1].setFill(this.tanks[1].getFill() + convert);
				}
				
				postConvert(convert);
			}
			
			this.tanks[1].writeToNBT(data, "1");
			
			this.subscribeToAllAround(this.tanks[0].getTankType(), this);
			this.sendFluidToAll(this.tanks[1], this);
			
			fillFluidInit(this.tanks[1].getTankType());
			data.setByte("timer", (byte) this.waterTimer);
			packExtra(data);
			INBTPacketReceiver.networkPack(this, data, 150);
		}
	}
	
	public void packExtra(NBTTagCompound data) { }
	public boolean extraCondition(int convert) { return true; }
	public void postConvert(int convert) { }

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.tanks[0].readFromNBT(nbt, "0");
		this.tanks[1].readFromNBT(nbt, "1");
		this.waterTimer = nbt.getByte("timer");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.tanks[0].readFromNBT(nbt, "water");
		this.tanks[1].readFromNBT(nbt, "steam");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.tanks[0].writeToNBT(nbt, "water");
		this.tanks[1].writeToNBT(nbt, "steam");
	}

	@Override
	public void fillFluidInit(FluidType type) {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			fillFluid(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, this.worldObj, type);
	}
	
	@Override
	public boolean getTact() {
		if(this.age == 0)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type.getName().equals(this.tanks[0].getTankType().getName()))
			this.tanks[0].setFill(i);
		else if(type.getName().equals(this.tanks[1].getTankType().getName()))
			this.tanks[1].setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type.getName().equals(this.tanks[0].getTankType().getName()))
			return this.tanks[0].getFill();
		else if(type.getName().equals(this.tanks[1].getTankType().getName()))
			return this.tanks[1].getFill();
		
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type.getName().equals(this.tanks[0].getTankType().getName()))
			return this.tanks[0].getMaxFill();
		
		return 0;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if(index < 2 && this.tanks[index] != null)
			this.tanks[index].setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if(index < 2 && this.tanks[index] != null)
			this.tanks[index].setTankType(type);
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
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {this.tanks [1]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {this.tanks [0]};
	}

	@Override
	public FluidTank[] getAllTanks() {
		return this.tanks;
	}
}
