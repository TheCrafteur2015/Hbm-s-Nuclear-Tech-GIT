package com.hbm.entity.logic;

import com.hbm.main.MainRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

public abstract class EntityExplosionChunkloading extends Entity implements IChunkLoader {

	private Ticket loaderTicket;
	private ChunkCoordIntPair loadedChunk;

	public EntityExplosionChunkloading(World world) {
		super(world);
	}

	@Override
	protected void entityInit() {
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, this.worldObj, Type.ENTITY));
	}

	@Override
	public void init(Ticket ticket) {
		if(!this.worldObj.isRemote && ticket != null) {
			if(this.loaderTicket == null) {
				this.loaderTicket = ticket;
				this.loaderTicket.bindEntity(this);
				this.loaderTicket.getModData();
			}
			ForgeChunkManager.forceChunk(this.loaderTicket, new ChunkCoordIntPair(this.chunkCoordX, this.chunkCoordZ));
		}
	}

	public void loadChunk(int x, int z) {
		
		if(this.loadedChunk == null) {
			this.loadedChunk = new ChunkCoordIntPair(x, z);
			ForgeChunkManager.forceChunk(this.loaderTicket, this.loadedChunk);
		}
	}
	
	public void clearChunkLoader() {
		if(!this.worldObj.isRemote && this.loaderTicket != null && this.loadedChunk != null) {
			ForgeChunkManager.unforceChunk(this.loaderTicket, this.loadedChunk);
		}
	}
}
