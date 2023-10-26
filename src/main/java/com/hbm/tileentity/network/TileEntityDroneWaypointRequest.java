package com.hbm.tileentity.network;

import com.hbm.tileentity.network.RequestNetwork.PathNode;
import com.hbm.util.fauxpointtwelve.BlockPos;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityDroneWaypointRequest extends TileEntityRequestNetwork {

	public int height = 5;
	
	@Override
	public BlockPos getCoord() {
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata());
		return new BlockPos(this.xCoord + dir.offsetX * this.height, this.yCoord + dir.offsetY * this.height, this.zCoord + dir.offsetZ * this.height);
	}
	
	public void addHeight(int h) {
		this.height += h;
		this.height = MathHelper.clamp_int(this.height, 1, 15);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.height = nbt.getInteger("height");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("height", this.height);
	}

	@Override
	public PathNode createNode(BlockPos pos) {
		return new PathNode(pos, this.reachableNodes);
	}
}
