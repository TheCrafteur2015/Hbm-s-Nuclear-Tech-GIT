package com.hbm.wiaj.actors;

import com.hbm.wiaj.JarScene;
import com.hbm.wiaj.WorldInAJar;

import net.minecraft.nbt.NBTTagCompound;

public class ActorTileEntity extends ActorBase {
	
	ITileActorRenderer renderer;
	
	public ActorTileEntity(ITileActorRenderer renderer) {
		this.renderer = renderer;
	}
	
	public ActorTileEntity(ITileActorRenderer renderer, NBTTagCompound data) {
		this(renderer);
		this.data = data;
	}

	@Override
	public void drawForegroundComponent(int w, int h, int ticks, float interp) { }

	@Override
	public void drawBackgroundComponent(WorldInAJar world, int ticks, float interp) {
		this.renderer.renderActor(world, ticks, interp, this.data);
	}

	@Override
	public void updateActor(JarScene scene) {
		this.renderer.updateActor(scene.script.ticksElapsed, this.data);
	}
}
