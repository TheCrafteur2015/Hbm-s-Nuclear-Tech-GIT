package com.hbm.saveddata;

import java.util.HashMap;
import java.util.Map.Entry;

import com.hbm.saveddata.satellites.Satellite;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class SatelliteSavedData extends WorldSavedData {
	
	public final HashMap<Integer, Satellite> sats = new HashMap<>();

	/**
	 * Constructor used for deserialization
	 * @param name - Map data name
	 */
	public SatelliteSavedData(String name) {
		super(name);
	}

	/**
	 * Default constructor for satellites map data.
	 */
    public SatelliteSavedData() {
        super("satellites");
        markDirty();
    }
    
    public boolean isFreqTaken(int freq) {
    	return getSatFromFreq(freq) != null;
    }
    
    public Satellite getSatFromFreq(int freq) {
    	return this.sats.get(freq);
    }

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		int satCount = nbt.getInteger("satCount");
		
		for(int i = 0; i < satCount; i++) {
			Satellite sat = Satellite.create(nbt.getInteger("sat_id_" + i));
			sat.readFromNBT((NBTTagCompound) nbt.getTag("sat_data_" + i));
			
			int freq = nbt.getInteger("sat_freq_" + i);
			
			this.sats.put(freq, sat);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("satCount", this.sats.size());
		
		int i = 0;

    	for(Entry<Integer, Satellite> struct : this.sats.entrySet()) {
    		NBTTagCompound data = new NBTTagCompound();
    		struct.getValue().writeToNBT(data);
    		
    		nbt.setInteger("sat_id_" + i, struct.getValue().getID());
    		nbt.setTag("sat_data_" + i, data);
    		nbt.setInteger("sat_freq_" + i, struct.getKey());
			i++;
    	}
	}
	
	public static SatelliteSavedData getData(World worldObj) {
		SatelliteSavedData data = (SatelliteSavedData)worldObj.perWorldStorage.loadData(SatelliteSavedData.class, "satellites");
	    if(data == null) {
	        worldObj.perWorldStorage.setData("satellites", new SatelliteSavedData());
	        
	        data = (SatelliteSavedData)worldObj.perWorldStorage.loadData(SatelliteSavedData.class, "satellites");
	    }
	    
	    return data;
	}
}
