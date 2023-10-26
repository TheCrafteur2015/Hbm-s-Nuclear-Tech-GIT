package com.hbm.saveddata;

import java.util.Arrays;

import net.minecraft.nbt.NBTTagCompound;

public class BroadcastSaveStructure {

	public int broadcastID;
	public BroadcastType broadcastType;
	public int posX;
	public int posZ;
	
	public BroadcastSaveStructure() { }
	
	public BroadcastSaveStructure(int id, BroadcastType type) {
		this.broadcastID = id;
		this.broadcastType = type;
	}
	
	public enum BroadcastType {
		
		DEMO;
		
		public static BroadcastType getEnum(int i) {
			if(i < BroadcastType.values().length)
				return BroadcastType.values()[i];
			else
				return BroadcastType.DEMO;
		}
		
		public int getID() {
			return Arrays.asList(BroadcastType.values()).indexOf(this);
		}
	}

	public void readFromNBT(NBTTagCompound nbt, int index) {
		this.broadcastID = nbt.getInteger("bc_" + index + "_id");
		this.broadcastType = BroadcastType.getEnum(nbt.getInteger("bc_" + index + "_type"));
		this.posX = nbt.getInteger("bc_" + index + "_x");
		this.posZ = nbt.getInteger("bc_" + index + "_z");
	}

	public void writeToNBT(NBTTagCompound nbt, int index) {
		nbt.setInteger("bc_" + index + "_id", this.broadcastID);
		nbt.setInteger("bc_" + index + "_type", this.broadcastType.getID());
		nbt.setInteger("bc_" + index + "_x", this.posX);
		nbt.setInteger("bc_" + index + "_z", this.posZ);
	}

}
