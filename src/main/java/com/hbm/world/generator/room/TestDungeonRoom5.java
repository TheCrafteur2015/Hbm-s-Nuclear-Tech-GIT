package com.hbm.world.generator.room;

import java.util.ArrayList;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.MetaBlock;
import com.hbm.world.generator.CellularDungeon;
import com.hbm.world.generator.CellularDungeonRoom;
import com.hbm.world.generator.DungeonToolbox;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TestDungeonRoom5 extends CellularDungeonRoom {
	
	public TestDungeonRoom5(CellularDungeon parent) {
		super(parent);
	}

	@SuppressWarnings({ "unchecked", "serial", "rawtypes" })
	@Override
	public void generateMain(World world, int x, int y, int z) {
		
		super.generateMain(world, x, y, z);
		DungeonToolbox.generateBox(world, x, y + this.parent.height - 2, z, this.parent.width, 1, this.parent.width, new ArrayList() {{ add(new MetaBlock(Blocks.air)); add(new MetaBlock(Blocks.web)); }});

		DungeonToolbox.generateBox(world, x + 1, y, z + 1, this.parent.width - 2, 1, this.parent.width - 2, new ArrayList() {{
			add(new MetaBlock(ModBlocks.meteor_polished));
			add(new MetaBlock(ModBlocks.meteor_polished));
			add(new MetaBlock(ModBlocks.meteor_polished));
			add(new MetaBlock(ModBlocks.meteor_polished));
			add(new MetaBlock(ModBlocks.meteor_polished));
			add(new MetaBlock(ModBlocks.meteor_spawner)); }});
	}

	@Override
	public void generateWall(World world, int x, int y, int z, ForgeDirection wall, boolean door) {
		
		if(wall != ForgeDirection.SOUTH)
			super.generateWall(world, x, y, z, wall, door);
	}
}