package com.hbm.test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.hbm.util.TimeAnalyzer;
import com.hbm.util.fauxpointtwelve.BlockPos;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCoordIntPair;

public class MK5Frame {

	public HashMap<ChunkCoordIntPair, List<FloatTriplet>> perChunk = new HashMap<>(); //for future: optimize blockmap further by using sub-chunks instead of chunks
	public List<ChunkCoordIntPair> orderedChunks = new ArrayList<>();
	private CoordComparator comparator = new CoordComparator();
	int posX;
	int posY;
	int posZ;
	ExplosionWorld world;

	int strength;
	int length;

	int gspNumMax;
	int gspNum;
	double gspX;
	double gspY;

	public boolean isCollectionComplete = false;

	public MK5Frame(ExplosionWorld world, int x, int y, int z, int strength, int length) {
		this.world = world;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.strength = strength;
		this.length = length;

		// Total number of points
		this.gspNumMax = (int)(2.5 * Math.PI * Math.pow(this.strength,2));
		this.gspNum = 1;

		// The beginning of the generalized spiral points
		this.gspX = Math.PI;
		this.gspY = 0.0;
	}

	private void generateGspUp(){
		
		if (this.gspNum < this.gspNumMax) {
			int k = this.gspNum + 1;
			double hk = -1.0 + 2.0 * (k - 1.0) / (this.gspNumMax - 1.0);
			this.gspX = Math.acos(hk);

			double prev_lon = this.gspY;
			double lon = prev_lon + 3.6 / Math.sqrt(this.gspNumMax) / Math.sqrt(1.0 - hk * hk);
			this.gspY = lon % (Math.PI * 2);
		} else {
			this.gspX = 0.0;
			this.gspY = 0.0;
		}
		this.gspNum++;
	}

	// Get Cartesian coordinates for spherical coordinates
	private Vec3 getSpherical2cartesian(){
		double dx = Math.sin(this.gspX) * Math.cos(this.gspY);
		double dz = Math.sin(this.gspX) * Math.sin(this.gspY);
		double dy = Math.cos(this.gspX);
		return Vec3.createVectorHelper(dx, dy, dz);
	}

	public void collectTip(int count) {
		
		TimeAnalyzer.startCount("collect");

		int amountProcessed = 0;

		while (this.gspNumMax >= this.gspNum){
			// Get Cartesian coordinates for spherical coordinates
			Vec3 vec = getSpherical2cartesian();

			int length = (int)Math.ceil(this.strength);
			float res = this.strength;

			FloatTriplet lastPos = null;
			HashSet<ChunkCoordIntPair> chunkCoords = new HashSet<>();

			for(int i = 0; i < length; i ++) {

				if(i > this.length)
					break;

				float x0 = (float) (this.posX + (vec.xCoord * i));
				float y0 = (float) (this.posY + (vec.yCoord * i));
				float z0 = (float) (this.posZ + (vec.zCoord * i));

				int iX = (int) Math.floor(x0);
				int iY = (int) Math.floor(y0);
				int iZ = (int) Math.floor(z0);

				double fac = 100 - ((double) i) / ((double) length) * 100;
				fac *= 0.07D;
				
				Block block = null;
				boolean withinThreshold = (double) i / (double) length <= ExplosionTests.BUFFER_THRESHOLD;
				
				Float buffered = withinThreshold ? this.buffer.getBufferedResult(iX, iY, iZ) : null;
				
				float f = 0;
				
				if(buffered == null) {
					
					block = this.world.getBlock(iX, iY, iZ);

					if(!block.getMaterial().isLiquid()) {
						f = (float) Math.pow(block.getExplosionResistance(null), 7.5D - fac);
					}
					
					if(withinThreshold) this.buffer.setBufferedResult(iX, iY, iZ, f);
					
				} else {
					f = buffered;
				}

				res -= f;

				if(res > 0 && block != Blocks.air && buffered == null) { // if we already have a buffered result we don't need to move the tip forward since that block is already affected
					lastPos = new FloatTriplet(x0, y0, z0);
					//all-air chunks don't need to be buffered at all
					ChunkCoordIntPair chunkPos = new ChunkCoordIntPair(iX >> 4, iZ >> 4);
					chunkCoords.add(chunkPos);
				}

				if(res <= 0 || i + 1 >= this.length) {
					break;
				}
			}
			
			for(ChunkCoordIntPair pos : chunkCoords) {
				List<FloatTriplet> triplets = this.perChunk.get(pos);
				
				if(triplets == null) {
					triplets = new ArrayList<>();
					this.perChunk.put(pos, triplets); //we re-use the same pos instead of using individualized per-chunk ones to save on RAM
				}
				
				triplets.add(lastPos);
			}
			
			// Raise one generalized spiral points
			generateGspUp();

			amountProcessed++;
			if(amountProcessed >= count) {
				TimeAnalyzer.endCount();
				return;
			}
		}
		
		this.orderedChunks.addAll(this.perChunk.keySet());
		this.orderedChunks.sort(this.comparator);
		
		this.isCollectionComplete = true;
		TimeAnalyzer.endCount();
	}
	
	/* TEST INSERT START */
	private ResultBuffer buffer;
	public MK5Frame setBuffer(ResultBuffer buffer) {
		this.buffer = buffer;
		return this;
	}
	public static interface ResultBuffer {
		Float getBufferedResult(int x, int y, int z);
		void setBufferedResult(int x, int y, int z, float f);
	}
	public static class BufferNone implements ResultBuffer {
		@Override public Float getBufferedResult(int x, int y, int z) { return null; }
		@Override public void setBufferedResult(int x, int y, int z, float f) { }
	}
	public static class BufferMap implements ResultBuffer {
		HashMap<BlockPos, Float> map = new HashMap<>();
		@Override public Float getBufferedResult(int x, int y, int z) { if(y < 0 || y > 255) return null; return this.map.get(new BlockPos(x, y, z)); }
		@Override public void setBufferedResult(int x, int y, int z, float f) { if(y < 0 || y > 255) return; this.map.put(new BlockPos(x, y, z), f); }
	}
	public static class BufferArray implements ResultBuffer {
		BlockPos center; Float[][][] buffer; int size; public BufferArray(int x, int y, int z, int size) { this.size = (int) (size * 2.1); this.center = new BlockPos(x, y, z); this.buffer = new Float[this.size][256][this.size];}
		HashMap<BlockPos, Float> map = new HashMap<>();
		@Override public Float getBufferedResult(int x, int y, int z) {
			if(y < 0 || y > 255) return null;
			int iX = x - this.center.getX() + this.size * 100; int iZ = z - this.center.getZ() + this.size * 100;
			return this.buffer[iX % this.size][y][iZ % this.size];
		}
		@Override public void setBufferedResult(int x, int y, int z, float f) {
			if(y < 0 || y > 255) return;
			int iX = x - this.center.getX() + this.size * 100; int iZ = z - this.center.getZ() + this.size * 100;
			this.buffer[iX % this.size][y][iZ % this.size] = f;
		}
	}
	/* TEST INSERT END */
	
	/** little comparator for roughly sorting chunks by distance to the center */
	public class CoordComparator implements Comparator<ChunkCoordIntPair> {

		@Override
		public int compare(ChunkCoordIntPair o1, ChunkCoordIntPair o2) {

			int chunkX = MK5Frame.this.posX >> 4;
			int chunkZ = MK5Frame.this.posZ >> 4;

			int diff1 = Math.abs((chunkX - o1.chunkXPos)) + Math.abs((chunkZ - o1.chunkZPos));
			int diff2 = Math.abs((chunkX - o2.chunkXPos)) + Math.abs((chunkZ - o2.chunkZPos));
			
			return diff1 > diff2 ? 1 : diff1 < diff2 ? -1 : 0;
		}
	}

	public void processChunk() {
		
		TimeAnalyzer.startCount("processChunk");
		if(this.perChunk.isEmpty()) {
			TimeAnalyzer.endCount();
			return;
		}
		
		ChunkCoordIntPair coord = this.orderedChunks.get(0);
		List<FloatTriplet> list = this.perChunk.get(coord);
		HashSet<BlockPos> toRem = new HashSet<>();
		int chunkX = coord.chunkXPos;
		int chunkZ = coord.chunkZPos;
		
		int enter = (Math.min(
				Math.abs(this.posX - (chunkX << 4)),
				Math.abs(this.posZ - (chunkZ << 4)))) - 16; //jump ahead to cut back on NOPs
		
		for(FloatTriplet triplet : list) {
			float x = triplet.xCoord;
			float y = triplet.yCoord;
			float z = triplet.zCoord;
			Vec3 vec = Vec3.createVectorHelper(x - this.posX, y - this.posY, z - this.posZ);
			double pX = vec.xCoord / vec.lengthVector();
			double pY = vec.yCoord / vec.lengthVector();
			double pZ = vec.zCoord / vec.lengthVector();
			
			boolean inChunk = false;
			for(int i = enter; i < vec.lengthVector(); i++) {
				int x0 = (int) Math.floor(this.posX + pX * i);
				int y0 = (int) Math.floor(this.posY + pY * i);
				int z0 = (int) Math.floor(this.posZ + pZ * i);
				
				if(x0 >> 4 != chunkX || z0 >> 4 != chunkZ) {
					if(inChunk) {
						break;
					} else {
						continue;
					}
				}
				
				inChunk = true;

				if(!this.world.isAirBlock(x0, y0, z0)) {
					toRem.add(new BlockPos(x0, y0, z0));
				}
			}
		}
		
		for(BlockPos pos : toRem) {
			this.world.setBlock(pos.getX(), pos.getY(), pos.getZ(), Blocks.air);
		}
		
		this.perChunk.remove(coord);
		this.orderedChunks.remove(0);
		
		TimeAnalyzer.endCount();
	}
	
	public class FloatTriplet {
		public float xCoord;
		public float yCoord;
		public float zCoord;
		
		public FloatTriplet(float x, float y, float z) {
			this.xCoord = x;
			this.yCoord = y;
			this.zCoord = z;
		}
	}
}
