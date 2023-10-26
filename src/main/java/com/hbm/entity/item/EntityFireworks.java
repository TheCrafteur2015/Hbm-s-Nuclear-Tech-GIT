package com.hbm.entity.item;

import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityFireworks extends Entity {
	
	int color;
	int character;

	public EntityFireworks(World world) {
		super(world);
	}

	public EntityFireworks(World world, double x, double y, double z, int color, int character) {
		super(world);
		setPositionAndRotation(x, y, z, 0.0F, 0.0F);
		this.color = color;
		this.character = character;
	}

	@Override
	protected void entityInit() { }
	
	@Override
	public void onUpdate() {
		
		moveEntity(0.0, 3.0D, 0.0);
		this.worldObj.spawnParticle("flame", this.posX, this.posY, this.posZ, 0.0, -0.3, 0.0);
		this.worldObj.spawnParticle("smoke", this.posX, this.posY, this.posZ, 0.0, -0.2, 0.0);
		
		if(!this.worldObj.isRemote) {
			
			this.ticksExisted++;
			
			if(this.ticksExisted > 30) {
				
				this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "fireworks.blast", 20, 1F + this.rand.nextFloat() * 0.2F);
				
				setDead();
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "fireworks");
				data.setInteger("color", this.color);
				data.setInteger("char", this.character);
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, this.posX, this.posY, this.posZ), new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 300));
			}
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.character = nbt.getInteger("char");
		this.color = nbt.getInteger("color");
		this.ticksExisted = nbt.getInteger("ticksExisted");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("char", this.character);
		nbt.setInteger("color", this.color);
		nbt.setInteger("ticksExisted", this.ticksExisted);
	}

}
