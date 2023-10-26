package com.hbm.tileentity.machine;

import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.Library;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.fluid.IFluidUser;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityChimneyBase extends TileEntityLoadedBase implements IFluidUser, INBTPacketReceiver {

	public long ashTick = 0;
	public long sootTick = 0;
	public int onTicks;
	
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			if(this.worldObj.getTotalWorldTime() % 20 == 0) {
				FluidType[] types = new FluidType[] {Fluids.SMOKE, Fluids.SMOKE_LEADED, Fluids.SMOKE_POISON};
				
				for(FluidType type : types) {
					trySubscribe(type, this.worldObj, this.xCoord + 2, this.yCoord, this.zCoord, Library.POS_X);
					trySubscribe(type, this.worldObj, this.xCoord - 2, this.yCoord, this.zCoord, Library.NEG_X);
					trySubscribe(type, this.worldObj, this.xCoord, this.yCoord, this.zCoord + 2, Library.POS_Z);
					trySubscribe(type, this.worldObj, this.xCoord, this.yCoord, this.zCoord - 2, Library.NEG_Z);
				}
			}
			
			if(this.ashTick > 0 || this.sootTick > 0) {

				TileEntity below = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord);
				
				if(below instanceof TileEntityAshpit) {
					TileEntityAshpit ashpit = (TileEntityAshpit) below;
					ashpit.ashLevelFly += this.ashTick;
					ashpit.ashLevelSoot += this.sootTick;
				}
				this.ashTick = 0;
				this.sootTick = 0;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("onTicks", this.onTicks);
			INBTPacketReceiver.networkPack(this, data, 150);
			
			if(this.onTicks > 0) this.onTicks--;
			
		} else {
			
			if(this.onTicks > 0) {
				spawnParticles();
			}
		}
	}

	public boolean cpaturesAsh() {
		return true;
	}
	
	public boolean cpaturesSoot() {
		return false;
	}
	
	public void spawnParticles() { }
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.onTicks = nbt.getInteger("onTicks");
	}

	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return (dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH || dir == ForgeDirection.EAST || dir == ForgeDirection.WEST) &&
				(type == Fluids.SMOKE || type == Fluids.SMOKE_LEADED || type == Fluids.SMOKE_POISON);
	}

	@Override
	public long transferFluid(FluidType type, int pressure, long fluid) {
		this.onTicks = 20;

		if(cpaturesAsh()) this.ashTick += fluid;
		if(cpaturesSoot()) this.sootTick += fluid;
		
		fluid *= getPollutionMod();

		if(type == Fluids.SMOKE) PollutionHandler.incrementPollution(this.worldObj, this.xCoord, this.yCoord, this.zCoord, PollutionType.SOOT, fluid / 100F);
		if(type == Fluids.SMOKE_LEADED) PollutionHandler.incrementPollution(this.worldObj, this.xCoord, this.yCoord, this.zCoord, PollutionType.HEAVYMETAL, fluid / 100F);
		if(type == Fluids.SMOKE_POISON) PollutionHandler.incrementPollution(this.worldObj, this.xCoord, this.yCoord, this.zCoord, PollutionType.POISON, fluid / 100F);
		
		return 0;
	}
	
	public abstract double getPollutionMod();

	@Override
	public long getDemand(FluidType type, int pressure) {
		return 1_000_000;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {};
	}
}
