package com.hbm.sound;

import java.util.ArrayList;
import java.util.List;

import com.hbm.tileentity.machine.TileEntityMachineAssembler;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class SoundLoopAssembler extends SoundLoopMachine {
	
	public static List<SoundLoopAssembler> list = new ArrayList<>();

	public SoundLoopAssembler(ResourceLocation path, TileEntity te) {
		super(path, te);
		SoundLoopAssembler.list.add(this);
	}

	@Override
	public void update() {
		super.update();
		
		if(this.te instanceof TileEntityMachineAssembler) {
			TileEntityMachineAssembler drill = (TileEntityMachineAssembler)this.te;
			
			if(this.volume != 3)
				this.volume = 3;
			
			if(!drill.isProgressing)
				this.donePlaying = true;
		}
	}
	
	public TileEntity getTE() {
		return this.te;
	}

}
