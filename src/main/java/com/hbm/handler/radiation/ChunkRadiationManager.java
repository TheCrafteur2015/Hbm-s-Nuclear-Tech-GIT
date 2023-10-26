package com.hbm.handler.radiation;

import com.hbm.config.RadiationConfig;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;

public class ChunkRadiationManager {
	
	public static ChunkRadiationHandler proxy = /*new ChunkRadiationHandlerNT();*/ new ChunkRadiationHandlerSimple();

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		if(RadiationConfig.enableChunkRads) ChunkRadiationManager.proxy.receiveWorldLoad(event);
	}
	
	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event) {
		if(RadiationConfig.enableChunkRads) ChunkRadiationManager.proxy.receiveWorldUnload(event);
	}

	@SubscribeEvent
	public void onChunkLoad(ChunkDataEvent.Load event) {
		if(RadiationConfig.enableChunkRads) ChunkRadiationManager.proxy.receiveChunkLoad(event);
	}
	
	@SubscribeEvent
	public void onChunkSave(ChunkDataEvent.Save event) {
		if(RadiationConfig.enableChunkRads) ChunkRadiationManager.proxy.receiveChunkSave(event);
	}
	
	@SubscribeEvent
	public void onChunkUnload(ChunkEvent.Unload event) {
		if(RadiationConfig.enableChunkRads) ChunkRadiationManager.proxy.receiveChunkUnload(event);
	}
	
	int eggTimer = 0;
	
	@SubscribeEvent
	public void updateSystem(TickEvent.ServerTickEvent event) {
		
		if(RadiationConfig.enableChunkRads && event.side == Side.SERVER && event.phase == Phase.END) {
			
			this.eggTimer++;
			
			if(this.eggTimer >= 20) {
				ChunkRadiationManager.proxy.updateSystem();
				this.eggTimer = 0;
			}
			
			if(RadiationConfig.worldRadEffects) {
				ChunkRadiationManager.proxy.handleWorldDestruction();
			}
			
			ChunkRadiationManager.proxy.receiveWorldTick(event);
		}
	}
}
