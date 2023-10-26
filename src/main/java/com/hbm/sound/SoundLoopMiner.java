package com.hbm.sound;

import java.util.ArrayList;
import java.util.List;

import com.hbm.tileentity.machine.TileEntityMachineMiningDrill;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class SoundLoopMiner extends SoundLoopMachine {
	
	public static List<SoundLoopMiner> list = new ArrayList<>();

	public SoundLoopMiner(ResourceLocation path, TileEntity te) {
		super(path, te);
		SoundLoopMiner.list.add(this);
	}

	@Override
	public void update() {
		super.update();
		
		if(this.te instanceof TileEntityMachineMiningDrill) {
			TileEntityMachineMiningDrill drill = (TileEntityMachineMiningDrill)this.te;
			
			if(this.volume != 3)
				this.volume = 3;
			
			if(drill.torque <= 0.5F)
				this.donePlaying = true;
		}
	}
	
	public TileEntity getTE() {
		return this.te;
	}

}
