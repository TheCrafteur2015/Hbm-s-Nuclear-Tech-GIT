package com.hbm.tileentity.bomb;

import com.hbm.entity.item.EntityFireworks;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityFireworks extends TileEntity {

	public int color = 0xff0000;
	public String message = "NUCLEAR TECH";
	public int charges;
	
	int index;
	int delay;
	
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			if(this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord) && !this.message.isEmpty() && this.charges > 0) {
				
				this.delay--;
				
				if(this.delay <= 0) {
					this.delay = 30;
					
					int c = (int)(this.message.charAt(this.index));
					
					int mod = this.index % 9;

					double offX = (mod / 3 - 1) * 0.3125;
					double offZ = (mod % 3 - 1) * 0.3125;
					
					EntityFireworks fireworks = new EntityFireworks(this.worldObj, this.xCoord + 0.5 + offX, this.yCoord + 1.5, this.zCoord + 0.5 + offZ, this.color, c);
					this.worldObj.spawnEntityInWorld(fireworks);
					
					this.worldObj.playSoundAtEntity(fireworks, "hbm:weapon.rocketFlame", 3.0F, 1.0F);
					
					this.charges--;
					markDirty();
					
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "vanillaExt");
					data.setString("mode", "flame");
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, this.xCoord + 0.5 + offX, this.yCoord + 1.125, this.zCoord + 0.5 + offZ), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord + 0.5 + offX, this.yCoord + 1.125, this.zCoord + 0.5 + offZ, 100));
					
					this.index++;
					
					if(this.index >= this.message.length()) {
						this.index = 0;
						this.delay = 100;
					}
				}
				
			} else {
				this.delay = 0;
				this.index = 0;
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.charges = nbt.getInteger("charges");
		this.color = nbt.getInteger("color");
		this.message = nbt.getString("message");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setInteger("charges", this.charges);
		nbt.setInteger("color", this.color);
		nbt.setString("message", this.message);
	}
}
