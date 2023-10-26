package com.hbm.tileentity.network;

import java.util.ArrayList;
import java.util.List;

import api.hbm.energy.IEnergyConductor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityConnector extends TileEntityPylonBase {

	@Override
	public ConnectionType getConnectionType() {
		return ConnectionType.SINGLE;
	}

	@Override
	public Vec3[] getMountPos() {
		return new Vec3[] {Vec3.createVectorHelper(0.5, 0.5, 0.5)};
	}

	@Override
	public double getMaxWireLength() {
		return 10;
	}
	
	@Override
	public List<int[]> getConnectionPoints() {
		List<int[]> pos = new ArrayList<>(this.connected);
		
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata()).getOpposite();
		//pos.add(new int[] {xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ});
		
		TileEntity te = this.worldObj.getTileEntity(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);
		
		if(te instanceof IEnergyConductor) {
			
			IEnergyConductor conductor = (IEnergyConductor) te;
			
			if(conductor.canConnect(dir.getOpposite())) {
				
				if(getPowerNet() == null && conductor.getPowerNet() != null) {
					conductor.getPowerNet().joinLink(this);
				}
				
				if(getPowerNet() != null && conductor.getPowerNet() != null && getPowerNet() != conductor.getPowerNet()) {
					conductor.getPowerNet().joinNetworks(getPowerNet());
				}
			}
		}
		
		return pos;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) { //i've about had it with your fucking bullshit
		return ForgeDirection.getOrientation(getBlockMetadata()).getOpposite() == dir;
	}
}
