package com.hbm.sound;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.logic.EntityBomber;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.util.ResourceLocation;

public class MovingSoundBomber extends MovingSound {

	public static List<MovingSoundBomber> globalSoundList = new ArrayList<>();
	public EntityBomber bomber;
	
	public MovingSoundBomber(ResourceLocation loc, EntityBomber bomber) {
		super(loc);
		this.bomber = bomber;
		MovingSoundBomber.globalSoundList.add(this);
		this.repeat = true;
		this.field_147666_i = ISound.AttenuationType.NONE;
	}

	@Override
	public void update() {
		
		float iVolume = 150;
		
		if(this.bomber == null || this.bomber.isDead || this.bomber.health <= 0) {
			stop();
		} else {
			this.xPosF = (float)this.bomber.posX;
			this.yPosF = (float)this.bomber.posY;
			this.zPosF = (float)this.bomber.posZ;
			
			EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
			float f = 0;
			
			if(player != null) {
				f = (float)Math.sqrt(Math.pow(this.xPosF - player.posX, 2) + Math.pow(this.yPosF - player.posY, 2) + Math.pow(this.zPosF - player.posZ, 2));
				this.volume = (f / iVolume) * -2 + 2;
			} else {
				this.volume = iVolume;
			}
		}
		
		if(!Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(this)) {
			stop();
		}
		
	}
	
	public void stop() {
		this.donePlaying = true;
		this.repeat = false;
		
		MovingSoundBomber.globalSoundList.remove(this);
	}
	
	public void setPitch(float f) {
		this.field_147663_c = f;
	}
	
	public void setVolume(float f) {
		this.volume = f;
	}
	
	public void setDone(boolean b) {
		this.donePlaying = b;
	}

}
