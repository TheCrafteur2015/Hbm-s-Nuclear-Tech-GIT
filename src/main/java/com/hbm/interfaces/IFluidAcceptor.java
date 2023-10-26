package com.hbm.interfaces;

import com.hbm.inventory.fluid.FluidType;

@Deprecated
public interface IFluidAcceptor extends IFluidContainer {

	@Deprecated int getMaxFluidFill(FluidType type);
	
	@Deprecated
	public default void setFluidFillForReceive(int fill, FluidType type) {
		setFluidFill(fill, type);
	}

	@Deprecated
	public default int getFluidFillForReceive(FluidType type) {
		return getFluidFill(type);
	}

	@Deprecated
	public default int getMaxFluidFillForReceive(FluidType type) {
		return getMaxFluidFill(type);
	}

	@Deprecated
	public default void receiveFluid(int amount, FluidType type) {
		setFluidFill(getFluidFill(type) + amount, type);
	}
}
