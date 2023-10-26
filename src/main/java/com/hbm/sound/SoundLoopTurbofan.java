package com.hbm.sound;

import java.util.ArrayList;
import java.util.List;

import com.hbm.tileentity.machine.TileEntityMachineTurbofan;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class SoundLoopTurbofan extends SoundLoopMachine {
	
	public static List<SoundLoopTurbofan> list = new ArrayList<>();

	public SoundLoopTurbofan(ResourceLocation path, TileEntity te) {
		super(path, te);
		SoundLoopTurbofan.list.add(this);
	}

	@Override
	public void update() {
		super.update();
		
		if(this.te instanceof TileEntityMachineTurbofan) {
			TileEntityMachineTurbofan drill = (TileEntityMachineTurbofan)this.te;
			
			if(this.volume != 10)
				this.volume = 10;
			
			if(!drill.wasOn)
				this.donePlaying = true;
		}
	}
	
	public TileEntity getTE() {
		return this.te;
	}

}
