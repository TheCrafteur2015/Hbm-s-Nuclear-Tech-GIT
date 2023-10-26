package com.hbm.sound;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public abstract class MovingSoundPlayerLoop extends MovingSound {

	public static List<MovingSoundPlayerLoop> globalSoundList = new ArrayList<>();
	public List<Entity> playerForSound = new ArrayList<>();
	public Entity player;
	public enum EnumHbmSound { soundTauLoop, soundChopperLoop, soundCrashingLoop, soundMineLoop };
	public EnumHbmSound type;
	public boolean init;

	public MovingSoundPlayerLoop(ResourceLocation res, Entity player, EnumHbmSound type) {
		super(res);
		this.player = player;
		this.type = type;
		this.init = false;
		this.repeat = true;
		if(MovingSoundPlayerLoop.getSoundByPlayer(player, type) == null)
			MovingSoundPlayerLoop.globalSoundList.add(this);
	}

	@Override
	public void update() {
		
		if(this.player != null) {
			this.xPosF = (float)this.player.posX;
			this.yPosF = (float)this.player.posY;
			this.zPosF = (float)this.player.posZ;
		}
		
		if(this.player == null || this.player.isDead)
			stop();
	}
	
	public void stop() {
		this.donePlaying = true;
		this.repeat = false;
		while(MovingSoundPlayerLoop.getSoundByPlayer(this.player, this.type) != null)
			MovingSoundPlayerLoop.globalSoundList.remove(MovingSoundPlayerLoop.getSoundByPlayer(this.player, this.type));
		
		this.player = null;
	}
	
	public static MovingSoundPlayerLoop getSoundByPlayer(Entity player, EnumHbmSound type) {
		
		for(MovingSoundPlayerLoop sound : MovingSoundPlayerLoop.globalSoundList) {
			if(sound.player == player && sound.type == type)
				return sound;
		}
		
		return null;
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
