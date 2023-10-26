package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityTickingBase;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityHadronDiode extends TileEntityTickingBase {
	
	int age = 0;
	boolean fatherIAskOfYouToUpdateMe = false;
	
	public DiodeConfig[] sides = new DiodeConfig[6];

	@Override
	public String getInventoryName() {
		return "";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			this.age++;
			
			if(this.age >= 20) {
				this.age = 0;
				sendSides();
			}
		} else {
			
			if(this.fatherIAskOfYouToUpdateMe) {
				this.fatherIAskOfYouToUpdateMe = false;
				this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			}
		}
	}
	
	public void sendSides() {
		
		NBTTagCompound data = new NBTTagCompound();
		
		for(int i = 0; i < 6; i++) {
			
			if(this.sides[i] != null)
				data.setInteger("" + i, this.sides[i].ordinal());
		}
		
		networkPack(data, 250);
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		
		for(int i = 0; i < 6; i++) {
			this.sides[i] = DiodeConfig.values()[nbt.getInteger("" + i)];
		}
		
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	}
	
	public DiodeConfig getConfig(int side) {
		
		if(ForgeDirection.getOrientation(side) == ForgeDirection.UNKNOWN)
			return DiodeConfig.NONE;
		
		DiodeConfig conf = this.sides[side];
		
		if(conf == null)
			return DiodeConfig.NONE;
		
		return conf;
	}
	
	public void setConfig(int side, int config) {
		this.sides[side] = DiodeConfig.values()[config];
		markDirty();
		sendSides();
	}
	
	public static enum DiodeConfig {
		NONE,
		IN,
		OUT
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		for(int i = 0; i < 6; i++) {
			this.sides[i] = DiodeConfig.values()[nbt.getInteger("side_" + i)];
		}
		
		this.fatherIAskOfYouToUpdateMe = true;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		for(int i = 0; i < 6; i++) {
			
			if(this.sides[i] != null) {
				nbt.setInteger("side_" + i, this.sides[i].ordinal());
			}
		}
	}
}
