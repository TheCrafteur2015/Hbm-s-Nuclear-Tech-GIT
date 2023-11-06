package com.hbm.entity.item;

import com.hbm.entity.cart.EntityMinecartBogie;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityMagnusCartus extends EntityMinecart {
	
	public EntityMinecartBogie bogie;

	public EntityMagnusCartus(World world) {
		super(world);
	}

	public EntityMagnusCartus(World p_i1713_1_, double p_i1713_2_, double p_i1713_4_, double p_i1713_6_) {
		super(p_i1713_1_, p_i1713_2_, p_i1713_4_, p_i1713_6_);
	}

	@Override
	public int getMinecartType() {
		return -1;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(25, 0);
	}
	
	public void setBogie(EntityMinecartBogie bogie) {
		this.bogie = bogie;
		this.dataWatcher.updateObject(25, bogie.getEntityId());
	}
	
	public int getBogieID() {
		return this.dataWatcher.getWatchableObjectInt(25);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(!this.worldObj.isRemote) {
			
			double dist = 3.0D;
			double force = 0.3D;
			
			if(this.bogie == null) {
				Vec3 vec = Vec3.createVectorHelper(dist, 0, 0);
				vec.rotateAroundY(this.rand.nextFloat() * 6.28F);
				EntityMinecartBogie bog = new EntityMinecartBogie(this.worldObj, this.posX + vec.xCoord, this.posY + vec.yCoord, this.posZ + vec.zCoord);
				setBogie(bog);
				this.worldObj.spawnEntityInWorld(bog);
			}
			
			Vec3 delta = Vec3.createVectorHelper(this.posX - this.bogie.posX, this.posY - this.bogie.posY, this.posZ - this.bogie.posZ);
			delta = delta.normalize();
			delta.xCoord *= dist;
			delta.yCoord *= dist;
			delta.zCoord *= dist;

			double x = this.posX - delta.xCoord;
			double y = this.posY - delta.yCoord;
			double z = this.posZ - delta.zCoord;
			
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "vanillaExt");
			data.setString("mode", "reddust");
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, x, y, z), new TargetPoint(this.dimension, x, y, z, 25));
			
			Vec3 pull = Vec3.createVectorHelper(x - this.bogie.posX, y - this.bogie.posY, z - this.bogie.posZ);
			this.bogie.motionX += pull.xCoord * force;
			this.bogie.motionY += pull.yCoord * force;
			this.bogie.motionZ += pull.zCoord * force;
			
			if(pull.lengthVector() > 1) {
				this.motionX -= pull.xCoord * force;
				this.motionY -= pull.yCoord * force;
				this.motionZ -= pull.zCoord * force;
			}
		}
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		int bogieID = nbt.getInteger("bogie");
		Entity e = this.worldObj.getEntityByID(bogieID);
		
		if(e instanceof EntityMinecartBogie) {
			setBogie((EntityMinecartBogie) e);
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("bogie", getBogieID());
	}
}
