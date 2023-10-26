package com.hbm.tileentity.deco;

import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBomber extends TileEntity {

	public int yaw;
	public int pitch;
	public int type = 1;

	@Override
	public void updateEntity() {

		if (!this.worldObj.isRemote) {
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(this.xCoord, this.yCoord, this.zCoord, this.yaw, 0), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 150));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(this.xCoord, this.yCoord, this.zCoord, this.pitch, 1), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 150));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(this.xCoord, this.yCoord, this.zCoord, this.type, 2), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 150));
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.yaw = nbt.getInteger("bomberYaw");
		this.pitch = nbt.getInteger("bomberPitch");
		this.type = nbt.getInteger("bomberType");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("bomberYaw", this.yaw);
		nbt.setInteger("bomberPitch", this.pitch);
		nbt.setInteger("bomberType", this.type);
	}

}
