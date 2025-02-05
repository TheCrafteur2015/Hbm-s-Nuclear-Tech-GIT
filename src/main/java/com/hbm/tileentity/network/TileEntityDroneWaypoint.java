package com.hbm.tileentity.network;

import java.util.List;

import com.hbm.entity.item.EntityDeliveryDrone;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.util.fauxpointtwelve.BlockPos;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityDroneWaypoint extends TileEntity implements INBTPacketReceiver, IDroneLinkable {
	
	public int height = 5;
	public int nextX = -1;
	public int nextY = -1;
	public int nextZ = -1;

	
	@Override
	public void updateEntity() {
		
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata());
		
		if(!this.worldObj.isRemote) {
			
			if(this.nextY != -1) {
				List<EntityDeliveryDrone> drones = this.worldObj.getEntitiesWithinAABB(EntityDeliveryDrone.class, AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1).offset(dir.offsetX * this.height, dir.offsetY * this.height, dir.offsetZ * this.height));
				for(EntityDeliveryDrone drone : drones) {
					if(Vec3.createVectorHelper(drone.motionX, drone.motionY, drone.motionZ).lengthVector() < 0.05) {
						drone.setTarget(this.nextX + 0.5, this.nextY, this.nextZ + 0.5);
					}
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("height", this.height);
			data.setIntArray("pos", new int[] {this.nextX, this.nextY, this.nextZ});
			INBTPacketReceiver.networkPack(this, data, 15);
		} else {
			
			if(this.nextY != -1 && this.worldObj.getTotalWorldTime() % 2 == 0) {
				double x = this.xCoord + this.height * dir.offsetX + 0.5;
				double y = this.yCoord + this.height * dir.offsetY + 0.5;
				double z = this.zCoord + this.height * dir.offsetZ + 0.5;
				
				this.worldObj.spawnParticle("reddust", x, y, z, 0, 0, 0);
			}
		}
	}

	@Override
	public BlockPos getPoint() {
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata());
		return new BlockPos(this.xCoord, this.yCoord, this.zCoord).offset(dir, this.height);
	}

	@Override
	public void setNextTarget(int x, int y, int z) {
		this.nextX = x;
		this.nextY = y;
		this.nextZ = z;
		markDirty();
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.height = nbt.getInteger("height");
		int[] pos = nbt.getIntArray("pos");
		this.nextX = pos[0];
		this.nextY = pos[1];
		this.nextZ = pos[2];
	}
	
	public void addHeight(int h) {
		this.height += h;
		this.height = MathHelper.clamp_int(this.height, 1, 15);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.height = nbt.getInteger("height");
		int[] pos = nbt.getIntArray("pos");
		this.nextX = pos[0];
		this.nextY = pos[1];
		this.nextZ = pos[2];
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("height", this.height);
		nbt.setIntArray("pos", new int[] {this.nextX, this.nextY, this.nextZ});
	}
}
