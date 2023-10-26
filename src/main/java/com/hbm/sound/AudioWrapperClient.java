package com.hbm.sound;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class AudioWrapperClient extends AudioWrapper {
	
	AudioDynamic sound;
	
	public AudioWrapperClient(ResourceLocation source) {
		if(source != null)
			this.sound = new AudioDynamic(source);
	}
	
	@Override
	public void setKeepAlive(int keepAlive) {
		if(this.sound != null)
			this.sound.setKeepAlive(keepAlive);
	}
	
	@Override
	public void keepAlive() {
		if(this.sound != null)
			this.sound.keepAlive();
	}
	
	@Override
	public void updatePosition(float x, float y, float z) {
		if(this.sound != null)
			this.sound.setPosition(x, y, z);
	}

	@Override
	public void updateVolume(float volume) {
		if(this.sound != null)
			this.sound.setVolume(volume);
	}

	@Override
	public void updateRange(float range) {
		if(this.sound != null)
			this.sound.setRange(range);
	}

	@Override
	public void updatePitch(float pitch) {
		if(this.sound != null)
			this.sound.setPitch(pitch);
	}

	@Override
	public float getVolume() {
		if(this.sound != null)
			return this.sound.getVolume();
		return 1;
	}

	@Override
	public float getPitch() {
		if(this.sound != null)
			return this.sound.getPitch();
		return 1;
	}

	@Override
	public void startSound() {
		if(this.sound != null)
			this.sound.start();
	}

	@Override
	public void stopSound() {
		if(this.sound != null)
			this.sound.stop();
	}

	@Override
	public boolean isPlaying() {
		return this.sound.isPlaying();
	}
}
