package com.hbm.util.fauxpointtwelve;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class DirPos extends BlockPos {
	
	protected ForgeDirection dir;

	public DirPos(int x, int y, int z, ForgeDirection dir) {
		super(x, y, z);
		this.dir = dir;
	}

	public DirPos(TileEntity te, ForgeDirection dir) {
		super(te);
		this.dir = dir;
	}

	public DirPos(double x, double y, double z, ForgeDirection dir) {
		super(x, y, z);
		this.dir = dir;
	}
	
	@Override
	public DirPos rotate(Rotation rotationIn) {
		switch(rotationIn) {
		case NONE:
		default: return this;
		case CLOCKWISE_90: return new DirPos(-getZ(), getY(), getX(), getDir().getRotation(ForgeDirection.UP));
		case CLOCKWISE_180: return new DirPos(-getX(), getY(), -getZ(), getDir().getOpposite());
		case COUNTERCLOCKWISE_90: return new DirPos(getZ(), getY(), -getX(), getDir().getRotation(ForgeDirection.DOWN));
		}
	}
	
	public ForgeDirection getDir() {
		return this.dir;
	}
}
