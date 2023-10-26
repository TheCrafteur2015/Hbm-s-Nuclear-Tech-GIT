package com.hbm.world.generator;

import com.hbm.inventory.RecipesCommon.MetaBlock;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class CellularDungeonRoom {
	
	protected CellularDungeon parent;
	protected CellularDungeonRoom daisyChain = null;
	protected ForgeDirection daisyDirection = ForgeDirection.UNKNOWN;
	
	public CellularDungeonRoom(CellularDungeon parent) {
		this.parent = parent;
	}
	
	//per generation, only one door can be made. rooms having multiple doors will be the consequence of daisychaining.
	//the initial room will use an invalid type to not spawn any doors.
	public void generate(World world, int x, int y, int z, ForgeDirection door) {
		
		generateMain(world, x, y, z);
		
		for(int i = 2; i < 6; i++) {
			ForgeDirection dir = ForgeDirection.getOrientation(i);
			generateWall(world, x, y, z, dir, dir == door);
		}
	}
	
	public void generateMain(World world, int x, int y, int z) {
		
		DungeonToolbox.generateBox(world, x, y, z, this.parent.width, 1, this.parent.width, this.parent.floor);
		DungeonToolbox.generateBox(world, x, y + 1, z, this.parent.width, this.parent.height - 1, this.parent.width, new MetaBlock(Blocks.air));
		DungeonToolbox.generateBox(world, x, y + this.parent.height - 1, z, this.parent.width, 1, this.parent.width, this.parent.ceiling);
	}
	
	public void generateWall(World world, int x, int y, int z, ForgeDirection wall, boolean door) {
		
		if(wall == ForgeDirection.NORTH) {
			DungeonToolbox.generateBox(world, x, y + 1, z, this.parent.width, this.parent.height - 2, 1, this.parent.wall);
			
			if(door)
				DungeonToolbox.generateBox(world, x + this.parent.width / 2, y + 1, z, 1, 2, 1, new MetaBlock(Blocks.air));
		}
		
		if(wall == ForgeDirection.SOUTH) {
			DungeonToolbox.generateBox(world, x, y + 1, z + this.parent.width - 1, this.parent.width, this.parent.height - 2, 1, this.parent.wall);
			
			if(door)
				DungeonToolbox.generateBox(world, x + this.parent.width / 2, y + 1, z + this.parent.width - 1, 1, 2, 1, new MetaBlock(Blocks.air));
		}
		
		if(wall == ForgeDirection.WEST) {
			DungeonToolbox.generateBox(world, x, y + 1, z, 1, this.parent.height - 2, this.parent.width, this.parent.wall);
			
			if(door)
				DungeonToolbox.generateBox(world, x, y + 1, z + this.parent.width / 2, 1, 2, 1, new MetaBlock(Blocks.air));
		}
		
		if(wall == ForgeDirection.EAST) {
			DungeonToolbox.generateBox(world, x + this.parent.width - 1, y + 1, z, 1, this.parent.height - 2, this.parent.width, this.parent.wall);
			
			if(door)
				DungeonToolbox.generateBox(world, x + this.parent.width - 1, y + 1, z + this.parent.width / 2, 1, 2, 1, new MetaBlock(Blocks.air));
		}
	}

}
