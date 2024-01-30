package com.hbm.tileentity.monitors;

import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author  Gabriel Roche
 * @since   
 * @version 
 */
public class TileEntityMonitorPWR extends TileEntityMachineBase {
	
	private int value;
	
	public TileEntityMonitorPWR() {
		super(1);
		this.value = 0;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		this.value = nbt.getInteger("Value");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("Value", this.value);
	}
	
	@Override
	public String getName() {
		return "container.monitorPWR";
	}
	
	@Override
	public void updateEntity() {
		
	}
	
}