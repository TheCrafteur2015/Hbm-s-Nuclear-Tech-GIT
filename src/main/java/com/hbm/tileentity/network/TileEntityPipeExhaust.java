package com.hbm.tileentity.network;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.util.Compat;

import api.hbm.fluid.IFluidConductor;
import api.hbm.fluid.IPipeNet;
import api.hbm.fluid.PipeNet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPipeExhaust extends TileEntity implements IFluidConductor {
	
	public IPipeNet[] nets = new IPipeNet[3];
	
	public FluidType[] getSmokes() {
		return new FluidType[] {Fluids.SMOKE, Fluids.SMOKE_LEADED, Fluids.SMOKE_POISON};
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote && canUpdate()) {
			
			for(int i = 0; i < 3; i++) this.nets[i] = null;

			for(FluidType type : getSmokes()) {
				connect(type);
				
				if(getPipeNet(type) == null) {
					setPipeNet(type, new PipeNet(type).joinLink(this));
				}
			}
		}
	}
	
	protected void connect(FluidType type) {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			
			TileEntity te = Compat.getTileStandard(this.worldObj, this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);
			
			if(te instanceof IFluidConductor) {
				
				IFluidConductor conductor = (IFluidConductor) te;
				
				if(!conductor.canConnect(type, dir.getOpposite()))
					continue;
				
				if(getPipeNet(type) == null && conductor.getPipeNet(type) != null) {
					conductor.getPipeNet(type).joinLink(this);
				}
				
				if(getPipeNet(type) != null && conductor.getPipeNet(type) != null && getPipeNet(type) != conductor.getPipeNet(type)) {
					conductor.getPipeNet(type).joinNetworks(getPipeNet(type));
				}
			}
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();
		
		if(!this.worldObj.isRemote) {
			
			for(int i = 0; i < 3; i++) {
				if(this.nets[i] != null) {
					this.nets[i].destroy();
				}
			}
		}
	}
	@Override
	public boolean canUpdate() {
		
		if(isInvalid()) return false;
		
		for(IPipeNet net : this.nets) {
			if(net == null || !net.isValid()) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN && (type == Fluids.SMOKE || type == Fluids.SMOKE_LEADED || type == Fluids.SMOKE_POISON);
	}

	@Override
	public long getDemand(FluidType type, int pressure) {
		return 0;
	}

	@Override
	public IPipeNet getPipeNet(FluidType type) {

		if(type == Fluids.SMOKE) return this.nets[0];
		if(type == Fluids.SMOKE_LEADED) return this.nets[1];
		if(type == Fluids.SMOKE_POISON) return this.nets[2];
		return null;
	}

	@Override
	public void setPipeNet(FluidType type, IPipeNet network) {

		if(type == Fluids.SMOKE) this.nets[0] = network;
		if(type == Fluids.SMOKE_LEADED) this.nets[1] = network;
		if(type == Fluids.SMOKE_POISON) this.nets[2] = network;
	}
}
