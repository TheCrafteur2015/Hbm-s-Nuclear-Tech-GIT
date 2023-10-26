package com.hbm.entity.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class EntityDroneBase extends Entity {
	
	protected int turnProgress;
	protected double syncPosX;
	protected double syncPosY;
	protected double syncPosZ;
	@SideOnly(Side.CLIENT) protected double velocityX;
	@SideOnly(Side.CLIENT) protected double velocityY;
	@SideOnly(Side.CLIENT) protected double velocityZ;

	public double targetX = -1;
	public double targetY = -1;
	public double targetZ = -1;

	public EntityDroneBase(World world) {
		super(world);
		setSize(1.5F, 2.0F);
	}
	
	public void setTarget(double x, double y, double z) {
		this.targetX = x;
		this.targetY = y;
		this.targetZ = z;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public boolean canAttackWithItem() {
		return true;
	}

	@Override
	public boolean hitByEntity(Entity attacker) {

		if(attacker instanceof EntityPlayer) {
			setDead();
		}
		
		return false;
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(10, new Byte((byte) 0));
	}
	
	/**
	 * 0: Empty<br>
	 * 1: Crate<br>
	 * 2: Barrel<br>
	 */
	public void setAppearance(int style) {
		this.dataWatcher.updateObject(10, (byte) style);
	}
	
	public int getAppearance() {
		return this.dataWatcher.getWatchableObjectByte(10);
	}
	
	@Override
	public void onUpdate() {
		
		if(this.worldObj.isRemote) {
			if(this.turnProgress > 0) {
				double interpX = this.posX + (this.syncPosX - this.posX) / (double) this.turnProgress;
				double interpY = this.posY + (this.syncPosY - this.posY) / (double) this.turnProgress;
				double interpZ = this.posZ + (this.syncPosZ - this.posZ) / (double) this.turnProgress;
				--this.turnProgress;
				setPosition(interpX, interpY, interpZ);
			} else {
				setPosition(this.posX, this.posY, this.posZ);
			}

			this.worldObj.spawnParticle("smoke", this.posX + 1.125, this.posY + 0.75, this.posZ, 0, -0.2, 0);
			this.worldObj.spawnParticle("smoke", this.posX - 1.125, this.posY + 0.75, this.posZ, 0, -0.2, 0);
			this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.75, this.posZ + 1.125, 0, -0.2, 0);
			this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.75, this.posZ - 1.125, 0, -0.2, 0);
		} else {

			this.motionX = 0;
			this.motionY = 0;
			this.motionZ = 0;
			
			if(this.targetY != -1) {
				
				Vec3 dist = Vec3.createVectorHelper(this.targetX - this.posX, this.targetY - this.posY, this.targetZ - this.posZ);
				double speed = getSpeed();
				
				if(dist.lengthVector() >= speed) {
					dist = dist.normalize();
					this.motionX = dist.xCoord * speed;
					this.motionY = dist.yCoord * speed;
					this.motionZ = dist.zCoord * speed;
				}
			}
			
			moveEntity(this.motionX, this.motionY, this.motionZ);
		}
	}
	
	public double getSpeed() {
		return 0.125D;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void setVelocity(double motionX, double motionY, double motionZ) {
		this.velocityX = this.motionX = motionX;
		this.velocityY = this.motionY = motionY;
		this.velocityZ = this.motionZ = motionZ;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int theNumberThree) {
		this.syncPosX = x;
		this.syncPosY = y;
		this.syncPosZ = z;
		this.turnProgress = theNumberThree;
		this.motionX = this.velocityX;
		this.motionY = this.velocityY;
		this.motionZ = this.velocityZ;
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {

		nbt.setDouble("tX", this.targetX);
		nbt.setDouble("tY", this.targetY);
		nbt.setDouble("tZ", this.targetZ);

		nbt.setByte("app", this.dataWatcher.getWatchableObjectByte(10));
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {

		if(nbt.hasKey("tY")) {
			this.targetX = nbt.getDouble("tX");
			this.targetY = nbt.getDouble("tY");
			this.targetZ = nbt.getDouble("tZ");
		}
		
		this.dataWatcher.updateObject(10, nbt.getByte("app"));
	}
}
