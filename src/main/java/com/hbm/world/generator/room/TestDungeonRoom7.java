package com.hbm.world.generator.room;

import com.hbm.blocks.ModBlocks;
import com.hbm.world.generator.CellularDungeon;
import com.hbm.world.generator.CellularDungeonRoom;
import com.hbm.world.generator.DungeonToolbox;

import net.minecraft.world.World;

public class TestDungeonRoom7 extends CellularDungeonRoom {

	public TestDungeonRoom7(CellularDungeon parent) {
		super(parent);
	}

	@Override
	public void generateMain(World world, int x, int y, int z) {
		
		super.generateMain(world, x, y, z);

		DungeonToolbox.generateBox(world, x, y, z, this.parent.width, 1, this.parent.width, ModBlocks.meteor_polished);
		DungeonToolbox.generateBox(world, x + 2, y, z + 2, this.parent.width - 4, 1, this.parent.width - 4, ModBlocks.deco_red_copper);
		DungeonToolbox.generateBox(world, x + 3, y, z + 3, this.parent.width - 6, 1, this.parent.width - 6, ModBlocks.meteor_polished);
		DungeonToolbox.generateBox(world, x + 4, y, z + 4, this.parent.width - 8, 1, this.parent.width - 8, ModBlocks.deco_red_copper);

		world.setBlock(x + this.parent.width / 2, y, z + this.parent.width / 2, ModBlocks.meteor_battery);
		world.setBlock(x + this.parent.width / 2, y + 1, z + this.parent.width / 2, ModBlocks.tesla);
	}
}
