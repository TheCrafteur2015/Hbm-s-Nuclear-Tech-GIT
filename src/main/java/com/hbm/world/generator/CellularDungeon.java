package com.hbm.world.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.inventory.RecipesCommon.MetaBlock;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class CellularDungeon {
	
	//a buffer "map" of the rooms being generated before being spawned in
	CellularDungeonRoom[][] cells;
	ForgeDirection[][] doors;
	//the order in which the buffer should be processed
	List<int[]> order = new ArrayList<>();
	
	//the size of the cell array x
	int dimX;
	//the size of the cell array z
	int dimZ;
	//the base width (and length) of a room
	public int width;
	//the height of a room
	public int height;
	//list of random floor blocks with equal weight
	public List<MetaBlock> floor = new ArrayList<>();
	//list of random ceiling blocks with equal weight
	public List<MetaBlock> ceiling = new ArrayList<>();
	//list of random wall blocks with equal weight
	public List<MetaBlock> wall = new ArrayList<>();
	//the rooms that the dungeon can use
	public List<CellularDungeonRoom> rooms = new ArrayList<>();
	int tries;
	int branches;
	
	public CellularDungeon(int width, int height, int dimX, int dimZ, int tries, int branches) {

		this.dimX = dimX;
		this.dimZ = dimZ;
		this.width = width;
		this.height = height;
		this.tries = tries;
		this.branches = branches;
	}
	
	public CellularDungeon(int width, int height, int dimX, int dimZ, int tries, int branches, MetaBlock floor, MetaBlock ceiling, MetaBlock wall) {

		this.dimX = dimX;
		this.dimZ = dimZ;
		this.width = width;
		this.height = height;
		this.tries = tries;
		this.branches = branches;
		this.floor.add(floor);
		this.ceiling.add(ceiling);
		this.wall.add(wall);
	}
	
	public void generate(World world, int x, int y, int z, Random rand) {

		x -= this.dimX * this.width / 2;
		z -= this.dimZ * this.width / 2;
		
		compose(rand);
		
		for(int[] coord : this.order) {
			
			if(coord == null || coord.length != 2)
				continue;
			
			int dx = coord[0];
			int dz = coord[1];
			
			if(this.cells[dx][dz] != null) {
				
				if(this.doors[dx][dz] == null)
					this.doors[dx][dz] = ForgeDirection.UNKNOWN;
				
				this.cells[dx][dz].generate(world, x + dx * (this.width - 1), y, z + dz * (this.width - 1), this.doors[dx][dz]);
			}
		}
	}
	
	int rec = 0;
	public void compose(Random rand) {

		this.cells = new CellularDungeonRoom[this.dimX][this.dimZ];
		this.doors = new ForgeDirection[this.dimX][this.dimZ];
		this.order.clear();

		int startX = this.dimX / 2;
		int startZ = this.dimZ / 2;

		this.cells[startX][startZ] = DungeonToolbox.getRandom(this.rooms, rand);
		this.doors[startX][startZ] = ForgeDirection.UNKNOWN;
		this.order.add(new int[] { startX, startZ });
		
		this.rec = 0;
		addRoom(startX, startZ, rand, ForgeDirection.UNKNOWN, DungeonToolbox.getRandom(this.rooms, rand));
	}
	
	//if x and z are occupied, it will just use the next nearby random space
	private boolean addRoom(int x, int z, Random rand, ForgeDirection door, CellularDungeonRoom room) {
		
		this.rec++;
		if(this.rec > this.tries)
			return false;
		
		if(x < 0 || z < 0 || x >= this.dimX || z >= this.dimZ)
			return false;
		
		if(this.cells[x][z] != null) {

			ForgeDirection dir = CellularDungeon.getRandomDir(rand);
			addRoom(x + dir.offsetX, z + dir.offsetZ, rand, dir.getOpposite(), DungeonToolbox.getRandom(this.rooms, rand));
			return false;
		}
		
		if(room.daisyChain == null || addRoom(x + room.daisyDirection.offsetX, z + room.daisyDirection.offsetZ, rand, ForgeDirection.UNKNOWN, room.daisyChain)) {
			this.cells[x][z] = room;
			this.doors[x][z] = door;
			this.order.add(new int[] { x, z });
		}
		
		for(int i = 0; i < this.branches; i++) {
			ForgeDirection dir = CellularDungeon.getRandomDir(rand);
			addRoom(x + dir.offsetX, z + dir.offsetZ, rand, dir.getOpposite(), DungeonToolbox.getRandom(this.rooms, rand));
		}
		
		return true;
	}
	
	public static ForgeDirection getRandomDir(Random rand) {
		
		return ForgeDirection.getOrientation(rand.nextInt(4) + 2);
	}
}
