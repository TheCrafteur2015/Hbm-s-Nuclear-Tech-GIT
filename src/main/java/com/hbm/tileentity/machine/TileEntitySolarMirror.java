package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityTickingBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.EnumSkyBlock;

public class TileEntitySolarMirror extends TileEntityTickingBase {

	public int tX;
	public int tY;
	public int tZ;
	public boolean isOn;

	@Override
	public String getInventoryName() {
		return null;
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			if(this.worldObj.getTotalWorldTime() % 20 == 0)
				sendUpdate();
			
			if(this.tY < this.yCoord) {
				this.isOn = false;
				return;
			}
			
			int sun = this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, this.xCoord, this.yCoord, this.zCoord) - this.worldObj.skylightSubtracted - 11;
			
			if(sun <= 0 || !this.worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord + 1, this.zCoord)) {
				this.isOn = false;
				return;
			}
			
			this.isOn = true;
			
			TileEntity te = this.worldObj.getTileEntity(this.tX, this.tY - 1, this.tZ);
			
			if(te instanceof TileEntitySolarBoiler) {
				TileEntitySolarBoiler boiler = (TileEntitySolarBoiler)te;
				boiler.heat += sun;
			}
		} else {
			
			TileEntity te = this.worldObj.getTileEntity(this.tX, this.tY - 1, this.tZ);
			
			if(this.isOn && te instanceof TileEntitySolarBoiler) {
				TileEntitySolarBoiler boiler = (TileEntitySolarBoiler)te;
				boiler.primary.add(new ChunkCoordinates(this.xCoord, this.yCoord, this.zCoord));
			}
			
			if(this.worldObj.getTotalWorldTime() % 20 == 0)
				this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
	}

	public void sendUpdate() {

		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("posX", this.tX);
		data.setInteger("posY", this.tY);
		data.setInteger("posZ", this.tZ);
		data.setBoolean("isOn", this.isOn);
		networkPack(data, 200);
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.tX = nbt.getInteger("posX");
		this.tY = nbt.getInteger("posY");
		this.tZ = nbt.getInteger("posZ");
		this.isOn = nbt.getBoolean("isOn");
	}
	
	public void setTarget(int x, int y, int z) {
		this.tX = x;
		this.tY = y;
		this.tZ = z;
		markDirty();
		sendUpdate();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.tX = nbt.getInteger("targetX");
		this.tY = nbt.getInteger("targetY");
		this.tZ = nbt.getInteger("targetZ");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("targetX", this.tX);
		nbt.setInteger("targetY", this.tY);
		nbt.setInteger("targetZ", this.tZ);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(
					this.xCoord - 25,
					this.yCoord - 25,
					this.zCoord - 25,
					this.xCoord + 25,
					this.yCoord + 25,
					this.zCoord + 25
					);
		}
		
		return this.bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
