package com.hbm.world.generator.room;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.TrappedBrick.Trap;
import com.hbm.world.generator.CellularDungeon;
import com.hbm.world.generator.TimedGenerator;
import com.hbm.world.generator.TimedGenerator.ITimedJob;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class JungleDungeonRoomMagic extends JungleDungeonRoom {

	public JungleDungeonRoomMagic(CellularDungeon parent) {
		super(parent);
	}

	@Override
	public void generateMain(final World world, final int x, final int y, final int z) {
		super.generateMain(world, x, y, z);
		
		ITimedJob job = new ITimedJob() {

			@Override
			public void work() {

				int ix = world.rand.nextInt(3) + 1;
				int iz = world.rand.nextInt(3) + 1;
				
				Block bl = world.getBlock(x + ix, y, z + iz);
				if(bl == ModBlocks.brick_jungle || bl == ModBlocks.brick_jungle_cracked || bl == ModBlocks.brick_jungle_lava) {
					world.setBlock(x + ix, y, z + iz, ModBlocks.brick_jungle_trap, Trap.MAGIC_CONVERSTION.ordinal(), 3);
				}
			}
		};
		
		TimedGenerator.addOp(world, job);
	}
}
