package com.hbm.sound;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/** Only used for doors */
public class AudioWrapperClientStartStop extends AudioWrapperClient {

	public String start;
	public String stop;
	public World world;
	public float ssVol;
	public float x, y, z;
	
	public AudioWrapperClientStartStop(World world, ResourceLocation source, String start, String stop, float vol){
		super(source);
		if(this.sound != null){
			this.sound.setVolume(vol);
		}
		this.ssVol = vol;
		this.world = world;
		this.start = start;
		this.stop = stop;
	}
	
	@Override
	public void updatePosition(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
		super.updatePosition(x, y, z);
	}
	
	@Override
	public void startSound(){
		if(this.start != null){
			this.world.playSound(this.x, this.y, this.z, this.start, this.ssVol * 0.2F, 1, false);
		}
		super.startSound();
	}
	
	@Override
	public void stopSound(){
		if(this.stop != null){
			this.world.playSound(this.x, this.y, this.z, this.stop, this.ssVol * 0.2F, 1, false);
		}
		super.stopSound();
	}
	
	@Override
	public float getVolume(){
		return this.ssVol;
	}
}
