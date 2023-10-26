package com.hbm.tileentity;

import com.hbm.packet.NBTPacket;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidTank;

public abstract class TileEntityTickingBase extends TileEntityLoadedBase implements INBTPacketReceiver {
	
	public TileEntityTickingBase() { }
	
	public abstract String getInventoryName();
	
	public int getGaugeScaled(int i, FluidTank tank) {
		return tank.getFluidAmount() * i / tank.getCapacity();
	}

	//abstracting this method forces child classes to implement it
	//so i don't have to remember the fucking method name
	//was it update? onUpdate? updateTile? did it have any args?
	//shit i don't know man
	@Override
    public abstract void updateEntity();
	
	public void networkPack(NBTTagCompound nbt, int range) {

		if(!this.worldObj.isRemote)
			PacketDispatcher.wrapper.sendToAllAround(new NBTPacket(nbt, this.xCoord, this.yCoord, this.zCoord), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, range));
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) { }
	
	@Deprecated
	public void handleButtonPacket(int value, int meta) { }
}
