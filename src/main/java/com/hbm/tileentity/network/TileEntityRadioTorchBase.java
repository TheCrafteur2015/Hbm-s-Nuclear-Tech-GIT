package com.hbm.tileentity.network;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.tileentity.INBTPacketReceiver;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityRadioTorchBase extends TileEntity implements INBTPacketReceiver, IControlReceiver {

	/** channel we're broadcasting on/listening to */
	public String channel = "";
	/** previous redstone state for input/output, needed for state change detection */
	public int lastState = 0;
	/** last update tick, needed for receivers listening for changes */
	public long lastUpdate;
	/** switches state change mode to tick-based polling */
	public boolean polling = false;
	/** switches redstone passthrough to custom signal mapping */
	public boolean customMap = false;
	/** custom mapping */
	public String[] mapping = new String[16];

	@Override
	public void updateEntity() {

		if(!this.worldObj.isRemote) {
			
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("p", this.polling);
			data.setBoolean("m", this.customMap);
			if(this.channel != null) data.setString("c", this.channel);
			for(int i = 0; i < 16; i++) if(this.mapping[i] != null) data.setString("m" + i, this.mapping[i]);
			INBTPacketReceiver.networkPack(this, data, 50);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.polling = nbt.getBoolean("p");
		this.customMap = nbt.getBoolean("m");
		this.lastState = nbt.getInteger("l");
		this.lastUpdate = nbt.getLong("u");
		this.channel = nbt.getString("c");
		for(int i = 0; i < 16; i++) this.mapping[i] = nbt.getString("m" + i);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("p", this.polling);
		nbt.setBoolean("m", this.customMap);
		nbt.setInteger("l", this.lastState);
		nbt.setLong("u", this.lastUpdate);
		if(this.channel != null) nbt.setString("c", this.channel);
		for(int i = 0; i < 16; i++) if(this.mapping[i] != null) nbt.setString("m" + i, this.mapping[i]);
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.polling = nbt.getBoolean("p");
		this.customMap = nbt.getBoolean("m");
		this.channel = nbt.getString("c");
		for(int i = 0; i < 16; i++) this.mapping[i] = nbt.getString("m" + i);
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setByte("l", (byte) this.lastState);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		int last = this.lastState;
		this.lastState = pkt.func_148857_g().getByte("l");
		if(this.lastState != last) {
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return player.getDistance(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5) < 16D;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("p")) this.polling = data.getBoolean("p");
		if(data.hasKey("m")) this.customMap = data.getBoolean("m");
		if(data.hasKey("c")) this.channel = data.getString("c");
		for(int i = 0; i < 16; i++) if(data.hasKey("m" + i)) this.mapping[i] = data.getString("m" + i);
		
		markDirty();
	}
}
