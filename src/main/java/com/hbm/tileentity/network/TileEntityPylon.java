package com.hbm.tileentity.network;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPylon extends TileEntityPylonBase {

	@Override
	public ConnectionType getConnectionType() {
		return ConnectionType.SINGLE;
	}

	@Override
	public Vec3[] getMountPos() {
		return new Vec3[] {Vec3.createVectorHelper(0.5, 5.4, 0.5)};
	}

	@Override
	public double getMaxWireLength() {
		return 25D;
	}
	
	@Override
	public List<int[]> getConnectionPoints() {
		List<int[]> pos = new ArrayList<>(this.connected);
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			pos.add(new int[] {this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ});
		}
		return pos;
	}
}
