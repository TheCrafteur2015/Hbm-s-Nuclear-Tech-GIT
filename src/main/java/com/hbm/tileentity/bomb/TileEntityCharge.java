package com.hbm.tileentity.bomb;

import com.hbm.blocks.bomb.BlockChargeBase;
import com.hbm.packet.NBTPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.INBTPacketReceiver;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

@SuppressWarnings("deprecation")
public class TileEntityCharge extends TileEntity implements INBTPacketReceiver {
	
	public boolean started;
	public int timer;

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			if(this.started) {
				this.timer--;
				
				if(this.timer % 20 == 0 && this.timer > 0)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:weapon.fstbmbPing", 1.0F, 1.0F);
				
				if(this.timer <= 0) {
					((BlockChargeBase)getBlockType()).explode(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("timer", this.timer);
			data.setBoolean("started", this.started);
			PacketDispatcher.wrapper.sendToAllAround(new NBTPacket(data, this.xCoord, this.yCoord, this.zCoord), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 100));
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.timer = data.getInteger("timer");
		this.started = data.getBoolean("started");
	}
	
	public String getMinutes() {
		
		String mins = "" + (this.timer / 1200);
		
		if(mins.length() == 1)
			mins = "0" + mins;
		
		return mins;
	}
	
	public String getSeconds() {
		
		String mins = "" + ((this.timer / 20) % 60);
		
		if(mins.length() == 1)
			mins = "0" + mins;
		
		return mins;
	}
}
