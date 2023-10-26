package com.hbm.saveddata;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

public class BroadcastSavedData extends WorldSavedData {
	
	public int bcCount;
	
	public List<BroadcastSaveStructure> broadcasts = new ArrayList<>();

	public BroadcastSavedData(String p_i2141_1_) {
		super(p_i2141_1_);
	}

    public BroadcastSavedData()
    {
        super("broadcasts");
        markDirty();
    }
    
    public boolean isIdTaken(int id) {
    	
    	return getBroadcastFromId(id) != null;
    }
    
    public BroadcastSaveStructure getBroadcastFromId(int id) {
    	
    	for(BroadcastSaveStructure bc : this.broadcasts)
    		if(bc.broadcastID == id)
    			return bc;
    	
    	return null;
    }

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		this.bcCount = nbt.getInteger("bcCount");
		
		for(int i = 0; i < this.bcCount; i++) {
			BroadcastSaveStructure struct = new BroadcastSaveStructure();
			struct.readFromNBT(nbt, i);
			
			this.broadcasts.add(struct);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("bcCount", this.broadcasts.size());
		
		for(int i = 0; i < this.broadcasts.size(); i++) {
			this.broadcasts.get(i).writeToNBT(nbt, i);
		}
	}

}
