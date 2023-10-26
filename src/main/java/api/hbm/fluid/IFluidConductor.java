package api.hbm.fluid;

import com.hbm.inventory.fluid.FluidType;

public interface IFluidConductor extends IFluidConnector {

	public IPipeNet getPipeNet(FluidType type);
	
	public void setPipeNet(FluidType type, IPipeNet network);
	
	@Override
	public default long transferFluid(FluidType type, int pressure, long amount) {
		
		if(getPipeNet(type) == null)
			return amount;

		return getPipeNet(type).transferFluid(amount, pressure);
	}
}
