package com.hbm.sound;

import java.util.ArrayList;
import java.util.List;

import com.hbm.tileentity.machine.TileEntityBroadcaster;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class SoundLoopBroadcaster extends SoundLoopMachine {
	
	public static List<SoundLoopBroadcaster> list = new ArrayList<>();
	public float intendedVolume = 25.0F;

	public SoundLoopBroadcaster(ResourceLocation path, TileEntity te) {
		super(path, te);
		SoundLoopBroadcaster.list.add(this);
		this.field_147666_i = ISound.AttenuationType.NONE;
	}

	@Override
	public void update() {
		super.update();
		
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		float f = 0;
		
		if(player != null) {
			f = (float)Math.sqrt(Math.pow(this.xPosF - player.posX, 2) + Math.pow(this.yPosF - player.posY, 2) + Math.pow(this.zPosF - player.posZ, 2));
			this.volume = func(f, this.intendedVolume);
			
			if(!(player.worldObj.getTileEntity((int)this.xPosF, (int)this.yPosF, (int)this.zPosF) instanceof TileEntityBroadcaster)) {
				this.donePlaying = true;
				this.volume = 0;
			}
		} else {
			this.volume = this.intendedVolume;
		}
	}
	
	public TileEntity getTE() {
		return this.te;
	}
	
	public float func(float f, float v) {
		return (f / v) * -2 + 2;
	}

}
