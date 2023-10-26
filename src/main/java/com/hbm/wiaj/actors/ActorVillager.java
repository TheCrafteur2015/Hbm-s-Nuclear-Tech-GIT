package com.hbm.wiaj.actors;

import org.lwjgl.opengl.GL11;

import com.hbm.wiaj.JarScene;
import com.hbm.wiaj.WorldInAJar;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.nbt.NBTTagCompound;

public class ActorVillager implements ISpecialActor {
	
	EntityVillager villager = new EntityVillager(Minecraft.getMinecraft().theWorld);
	NBTTagCompound data = new NBTTagCompound();
	
	public ActorVillager() { }
	
	public ActorVillager(NBTTagCompound data) {
		this.data = data;
	}

	@Override
	public void drawForegroundComponent(int w, int h, int ticks, float interp) { }

	@Override
	public void drawBackgroundComponent(WorldInAJar world, int ticks, float interp) {
		double x = this.data.getDouble("x");
		double y = this.data.getDouble("y");
		double z = this.data.getDouble("z");
		double yaw = this.data.getDouble("yaw");
		GL11.glTranslated(x, y, z);
		GL11.glRotated(yaw, 0, 1, 0);
		RenderManager.instance.renderEntityWithPosYaw(this.villager, 0D, 0D, 0D, 0F, interp);
	}

	@Override
	public void updateActor(JarScene scene) {
		this.villager.limbSwingAmount += (1F - this.villager.limbSwingAmount) * 0.4F;
		this.villager.limbSwing += this.villager.limbSwingAmount;
	}

	@Override
	public void setActorData(NBTTagCompound data) {
		
	}

	@Override
	public void setDataPoint(String tag, Object o) {
		
	}
}
