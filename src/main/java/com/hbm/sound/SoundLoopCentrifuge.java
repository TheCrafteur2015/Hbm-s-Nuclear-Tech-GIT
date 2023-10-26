package com.hbm.sound;

import java.util.ArrayList;
import java.util.List;

import com.hbm.tileentity.machine.TileEntityMachineCentrifuge;
import com.hbm.tileentity.machine.TileEntityMachineGasCent;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class SoundLoopCentrifuge extends SoundLoopMachine {
	
	public static List<SoundLoopCentrifuge> list = new ArrayList<>();

	public SoundLoopCentrifuge(ResourceLocation path, TileEntity te) {
		super(path, te);
		SoundLoopCentrifuge.list.add(this);
	}

	@Override
	public void update() {
		super.update();
		
		if(this.te instanceof TileEntityMachineCentrifuge) {
			TileEntityMachineCentrifuge plant = (TileEntityMachineCentrifuge)this.te;
			
			if(this.volume != 1)
				this.volume = 1;
			
			if(!plant.isProgressing)
				this.donePlaying = true;
		}
		
		if(this.te instanceof TileEntityMachineGasCent) {
			TileEntityMachineGasCent plant = (TileEntityMachineGasCent)this.te;
			
			if(this.volume != 1)
				this.volume = 1;
			
			if(!plant.isProgressing)
				this.donePlaying = true;
		}
		
		if(!Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(this)) {
			stop();
		}
	}
	
	public TileEntity getTE() {
		return this.te;
	}

}
