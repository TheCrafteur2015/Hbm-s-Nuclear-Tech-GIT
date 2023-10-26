package com.hbm.sound;

import com.hbm.entity.mob.EntityHunterChopper;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class MovingSoundCrashing extends MovingSoundPlayerLoop {

	public MovingSoundCrashing(ResourceLocation p_i45104_1_, Entity player, EnumHbmSound type) {
		super(p_i45104_1_, player, type);
	}

	@Override
	public void update() {
		super.update();
		
		if(this.player instanceof EntityHunterChopper && !((EntityHunterChopper)this.player).getIsDying())
			stop();
	}
}
