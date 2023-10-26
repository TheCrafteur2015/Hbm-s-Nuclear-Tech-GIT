package com.hbm.saveddata.satellites;

import com.hbm.entity.projectile.EntityTom;
import com.hbm.main.MainRegistry;
import com.hbm.saveddata.SatelliteSavedData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class SatelliteHorizons extends Satellite {
	
	boolean used = false;
	
	public SatelliteHorizons() {
		this.satIface = Interfaces.SAT_COORD;
	}

	@Override
	public void onOrbit(World world, double x, double y, double z) {

		for(Object p : world.playerEntities)
			((EntityPlayer)p).triggerAchievement(MainRegistry.horizonsStart);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setBoolean("used", this.used);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		this.used = nbt.getBoolean("used");
	}
	
	@Override
	public void onCoordAction(World world, EntityPlayer player, int x, int y, int z) {
		
		if(this.used)
			return;
		
		this.used = true;
		SatelliteSavedData.getData(world).markDirty();
		
		EntityTom tom = new EntityTom(world);
		tom.setPosition(x + 0.5, 600, z + 0.5);
		
		IChunkProvider provider = world.getChunkProvider();
		provider.loadChunk(x >> 4, z >> 4);
		
		world.spawnEntityInWorld(tom);

		for(Object p : world.playerEntities)
			((EntityPlayer)p).triggerAchievement(MainRegistry.horizonsEnd);
		
		//not necessary but JUST to make sure
		if(!world.isRemote) {
			
			MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText(EnumChatFormatting.RED + "Horizons has been activated."));
		}
	}
}
