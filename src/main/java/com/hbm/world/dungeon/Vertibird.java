//Schematic to java Structure by jajo_11 | inspired by "MITHION'S .SCHEMATIC TO JAVA CONVERTINGTOOL"

package com.hbm.world.dungeon;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.lib.HbmChestContents;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class Vertibird extends WorldGenerator
{
	Block Block2 = ModBlocks.deco_steel;
	Block Block1 = ModBlocks.deco_tungsten;
	Block Block4 = ModBlocks.reinforced_glass;
	Block Block3 = ModBlocks.deco_titanium;
	
	protected Block[] GetValidSpawnBlocks()
	{
		return new Block[]
		{
			Blocks.sand,
			Blocks.sandstone,
		};
	}

	public boolean LocationIsValidSpawn(World world, int x, int y, int z)
 {

		Block checkBlock = world.getBlock(x, y - 1, z);
		Block blockAbove = world.getBlock(x, y , z);
		Block blockBelow = world.getBlock(x, y - 2, z);

		for (Block i : GetValidSpawnBlocks())
		{
			if (blockAbove != Blocks.air)
			{
				return false;
			}
			if (checkBlock == i)
			{
				return true;
			}
			else if (checkBlock == Blocks.snow_layer && blockBelow == i)
			{
				return true;
			}
			else if (checkBlock.getMaterial() == Material.plants && blockBelow == i)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z)
	{
		int i = rand.nextInt(1);

		if(i == 0)
		{
		    generate_r0(world, rand, x, y, z);
		}

       return true;

	}

	public boolean generate_r0(World world, Random rand, int x, int y, int z)
	{
		int yOffset = 3 + rand.nextInt(4);
		
		if(!LocationIsValidSpawn(world, x + 13, y, z + 10))
		{
			return false;
		}

		world.setBlock(x + 13, y + 0 - yOffset, z + 2, this.Block1, 0, 3);
		world.setBlock(x + 12, y + 0 - yOffset, z + 7, this.Block1, 0, 3);
		world.setBlock(x + 14, y + 0 - yOffset, z + 7, this.Block1, 0, 3);
		world.setBlock(x + 13, y + 0 - yOffset, z + 9, this.Block1, 0, 3);
		world.setBlock(x + 12, y + 1 - yOffset, z + 1, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 1 - yOffset, z + 1, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 1 - yOffset, z + 1, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 1 - yOffset, z + 2, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 1 - yOffset, z + 2, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 1 - yOffset, z + 2, this.Block2, 0, 3);
		world.setBlock(x + 11, y + 1 - yOffset, z + 3, this.Block1, 0, 3);
		world.setBlock(x + 12, y + 1 - yOffset, z + 3, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 1 - yOffset, z + 3, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 1 - yOffset, z + 3, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 1 - yOffset, z + 3, this.Block1, 0, 3);
		world.setBlock(x + 11, y + 1 - yOffset, z + 4, this.Block1, 0, 3);
		world.setBlock(x + 12, y + 1 - yOffset, z + 4, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 1 - yOffset, z + 4, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 1 - yOffset, z + 4, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 1 - yOffset, z + 4, this.Block1, 0, 3);
		world.setBlock(x + 11, y + 1 - yOffset, z + 5, this.Block1, 0, 3);
		world.setBlock(x + 12, y + 1 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 1 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 1 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 1 - yOffset, z + 5, this.Block1, 0, 3);
		world.setBlock(x + 12, y + 1 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 1 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 1 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 1 - yOffset, z + 7, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 1 - yOffset, z + 7, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 1 - yOffset, z + 7, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 1 - yOffset, z + 8, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 1 - yOffset, z + 8, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 1 - yOffset, z + 8, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 1 - yOffset, z + 9, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 1 - yOffset, z + 9, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 1 - yOffset, z + 9, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 2 - yOffset, z + 0, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 2 - yOffset, z + 0, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 2 - yOffset, z + 0, this.Block2, 0, 3);
		world.setBlock(x + 11, y + 2 - yOffset, z + 1, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 2 - yOffset, z + 1, this.Block2, 0, 3);
		world.setBlock(x + 11, y + 2 - yOffset, z + 2, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 2 - yOffset, z + 2, Blocks.stone_stairs, 2, 3);
		world.setBlock(x + 14, y + 2 - yOffset, z + 2, Blocks.stone_stairs, 2, 3);
		world.setBlock(x + 15, y + 2 - yOffset, z + 2, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 2 - yOffset, z + 3, this.Block3, 0, 3);
		world.setBlock(x + 15, y + 2 - yOffset, z + 4, this.Block3, 0, 3);
		world.setBlock(x + 10, y + 2 - yOffset, z + 5, this.Block3, 0, 3);
		world.setBlock(x + 15, y + 2 - yOffset, z + 5, this.Block3, 0, 3);
		world.setBlock(x + 10, y + 2 - yOffset, z + 6, this.Block3, 0, 3);
		world.setBlock(x + 11, y + 2 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 2 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 10, y + 2 - yOffset, z + 7, this.Block3, 0, 3);
		world.setBlock(x + 11, y + 2 - yOffset, z + 7, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 2 - yOffset, z + 7, Blocks.chest, 2, 3);
		if(world.getBlock(x + 14, y + 2 - yOffset, z + 7) == Blocks.chest)
		{
			WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.vertibird, (TileEntityChest)world.getTileEntity(x + 14, y + 2 - yOffset, z + 7), 8);
		}
		world.setBlock(x + 15, y + 2 - yOffset, z + 7, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 2 - yOffset, z + 8, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 2 - yOffset, z + 8, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 2 - yOffset, z + 8, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 2 - yOffset, z + 9, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 2 - yOffset, z + 9, this.Block3, 0, 3);
		world.setBlock(x + 14, y + 2 - yOffset, z + 9, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 2 - yOffset, z + 10, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 2 - yOffset, z + 10, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 2 - yOffset, z + 10, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 2 - yOffset, z + 11, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 2 - yOffset, z + 11, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 2 - yOffset, z + 11, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 2 - yOffset, z + 18, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 3 - yOffset, z + 0, this.Block4, 0, 3);
		world.setBlock(x + 13, y + 3 - yOffset, z + 0, this.Block4, 0, 3);
		world.setBlock(x + 14, y + 3 - yOffset, z + 0, this.Block4, 0, 3);
		world.setBlock(x + 11, y + 3 - yOffset, z + 1, this.Block4, 0, 3);
		world.setBlock(x + 15, y + 3 - yOffset, z + 1, this.Block4, 0, 3);
		world.setBlock(x + 11, y + 3 - yOffset, z + 2, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 3 - yOffset, z + 2, this.Block2, 0, 3);
		world.setBlock(x + 11, y + 3 - yOffset, z + 3, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 3 - yOffset, z + 3, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 3 - yOffset, z + 4, this.Block3, 0, 3);
		world.setBlock(x + 15, y + 3 - yOffset, z + 5, this.Block3, 0, 3);
		world.setBlock(x + 10, y + 3 - yOffset, z + 6, this.Block3, 0, 3);
		world.setBlock(x + 11, y + 3 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 3 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 10, y + 3 - yOffset, z + 7, this.Block3, 0, 3);
		world.setBlock(x + 11, y + 3 - yOffset, z + 7, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 3 - yOffset, z + 7, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 3 - yOffset, z + 8, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 3 - yOffset, z + 8, ModBlocks.machine_battery, 2, 3);
		world.setBlock(x + 14, y + 3 - yOffset, z + 8, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 3 - yOffset, z + 9, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 3 - yOffset, z + 9, ModBlocks.red_wire_coated, 0, 3);
		world.setBlock(x + 14, y + 3 - yOffset, z + 9, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 3 - yOffset, z + 10, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 3 - yOffset, z + 10, this.Block3, 0, 3);
		world.setBlock(x + 14, y + 3 - yOffset, z + 10, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 3 - yOffset, z + 11, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 3 - yOffset, z + 11, this.Block3, 0, 3);
		world.setBlock(x + 14, y + 3 - yOffset, z + 11, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 3 - yOffset, z + 12, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 3 - yOffset, z + 12, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 3 - yOffset, z + 12, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 3 - yOffset, z + 17, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 3 - yOffset, z + 18, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 4 - yOffset, z + 0, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 4 - yOffset, z + 0, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 4 - yOffset, z + 0, this.Block2, 0, 3);
		world.setBlock(x + 11, y + 4 - yOffset, z + 1, this.Block4, 0, 3);
		world.setBlock(x + 12, y + 4 - yOffset, z + 1, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 4 - yOffset, z + 1, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 4 - yOffset, z + 1, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 4 - yOffset, z + 1, this.Block4, 0, 3);
		world.setBlock(x + 11, y + 4 - yOffset, z + 2, this.Block4, 0, 3);
		world.setBlock(x + 15, y + 4 - yOffset, z + 2, this.Block4, 0, 3);
		world.setBlock(x + 11, y + 4 - yOffset, z + 3, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 4 - yOffset, z + 3, this.Block2, 0, 3);
		world.setBlock(x + 11, y + 4 - yOffset, z + 4, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 4 - yOffset, z + 4, this.Block2, 0, 3);
		world.setBlock(x + 4, y + 4 - yOffset, z + 5, this.Block1, 0, 3);
		world.setBlock(x + 11, y + 4 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 4 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 22, y + 4 - yOffset, z + 5, this.Block1, 0, 3);
		world.setBlock(x + 11, y + 4 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 4 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 4 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 4 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 11, y + 4 - yOffset, z + 7, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 4 - yOffset, z + 7, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 4 - yOffset, z + 7, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 4 - yOffset, z + 7, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 4 - yOffset, z + 7, this.Block2, 0, 3);
		world.setBlock(x + 11, y + 4 - yOffset, z + 8, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 4 - yOffset, z + 8, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 4 - yOffset, z + 8, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 4 - yOffset, z + 8, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 4 - yOffset, z + 8, this.Block2, 0, 3);
		world.setBlock(x + 11, y + 4 - yOffset, z + 9, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 4 - yOffset, z + 9, ModBlocks.machine_generator, 0, 3);
		world.setBlock(x + 13, y + 4 - yOffset, z + 9, ModBlocks.red_wire_coated, 0, 3);
		world.setBlock(x + 14, y + 4 - yOffset, z + 9, ModBlocks.machine_generator, 0, 3);
		world.setBlock(x + 15, y + 4 - yOffset, z + 9, this.Block2, 0, 3);
		world.setBlock(x + 11, y + 4 - yOffset, z + 10, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 4 - yOffset, z + 10, ModBlocks.machine_generator, 0, 3);
		world.setBlock(x + 13, y + 4 - yOffset, z + 10, ModBlocks.red_wire_coated, 0, 3);
		world.setBlock(x + 14, y + 4 - yOffset, z + 10, ModBlocks.machine_generator, 0, 3);
		world.setBlock(x + 15, y + 4 - yOffset, z + 10, this.Block2, 0, 3);
		world.setBlock(x + 11, y + 4 - yOffset, z + 11, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 4 - yOffset, z + 11, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 4 - yOffset, z + 11, this.Block3, 0, 3);
		world.setBlock(x + 14, y + 4 - yOffset, z + 11, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 4 - yOffset, z + 11, this.Block2, 0, 3);
		world.setBlock(x + 11, y + 4 - yOffset, z + 12, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 4 - yOffset, z + 12, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 4 - yOffset, z + 12, this.Block3, 0, 3);
		world.setBlock(x + 14, y + 4 - yOffset, z + 12, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 4 - yOffset, z + 12, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 4 - yOffset, z + 13, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 4 - yOffset, z + 13, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 4 - yOffset, z + 13, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 4 - yOffset, z + 14, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 4 - yOffset, z + 15, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 4 - yOffset, z + 16, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 4 - yOffset, z + 17, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 4 - yOffset, z + 18, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 5 - yOffset, z + 1, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 5 - yOffset, z + 1, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 5 - yOffset, z + 1, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 5 - yOffset, z + 2, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 5 - yOffset, z + 2, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 5 - yOffset, z + 2, this.Block2, 0, 3);
		world.setBlock(x + 11, y + 5 - yOffset, z + 3, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 5 - yOffset, z + 3, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 5 - yOffset, z + 3, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 5 - yOffset, z + 3, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 5 - yOffset, z + 3, this.Block2, 0, 3);
		world.setBlock(x + 4, y + 5 - yOffset, z + 4, this.Block2, 0, 3);
		world.setBlock(x + 11, y + 5 - yOffset, z + 4, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 5 - yOffset, z + 4, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 5 - yOffset, z + 4, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 5 - yOffset, z + 4, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 5 - yOffset, z + 4, this.Block2, 0, 3);
		world.setBlock(x + 22, y + 5 - yOffset, z + 4, this.Block2, 0, 3);
		world.setBlock(x + 3, y + 5 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 4, y + 5 - yOffset, z + 5, this.Block1, 0, 3);
		world.setBlock(x + 5, y + 5 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 8, y + 5 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 9, y + 5 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 10, y + 5 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 11, y + 5 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 5 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 5 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 5 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 5 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 16, y + 5 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 17, y + 5 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 18, y + 5 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 21, y + 5 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 22, y + 5 - yOffset, z + 5, this.Block1, 0, 3);
		world.setBlock(x + 23, y + 5 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 4, y + 5 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 11, y + 5 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 5 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 5 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 5 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 5 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 22, y + 5 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 11, y + 5 - yOffset, z + 7, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 5 - yOffset, z + 7, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 5 - yOffset, z + 7, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 5 - yOffset, z + 7, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 5 - yOffset, z + 7, this.Block2, 0, 3);
		world.setBlock(x + 11, y + 5 - yOffset, z + 8, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 5 - yOffset, z + 8, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 5 - yOffset, z + 8, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 5 - yOffset, z + 8, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 5 - yOffset, z + 8, this.Block2, 0, 3);
		world.setBlock(x + 11, y + 5 - yOffset, z + 9, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 5 - yOffset, z + 9, ModBlocks.machine_generator, 0, 3);
		world.setBlock(x + 13, y + 5 - yOffset, z + 9, ModBlocks.red_wire_coated, 0, 3);
		world.setBlock(x + 14, y + 5 - yOffset, z + 9, ModBlocks.machine_generator, 0, 3);
		world.setBlock(x + 15, y + 5 - yOffset, z + 9, this.Block2, 0, 3);
		world.setBlock(x + 11, y + 5 - yOffset, z + 10, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 5 - yOffset, z + 10, ModBlocks.machine_generator, 0, 3);
		world.setBlock(x + 13, y + 5 - yOffset, z + 10, ModBlocks.red_wire_coated, 0, 3);
		world.setBlock(x + 14, y + 5 - yOffset, z + 10, ModBlocks.machine_generator, 0, 3);
		world.setBlock(x + 15, y + 5 - yOffset, z + 10, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 5 - yOffset, z + 11, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 5 - yOffset, z + 11, ModBlocks.red_wire_coated, 0, 3);
		world.setBlock(x + 14, y + 5 - yOffset, z + 11, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 5 - yOffset, z + 12, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 5 - yOffset, z + 12, ModBlocks.red_wire_coated, 0, 3);
		world.setBlock(x + 14, y + 5 - yOffset, z + 12, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 5 - yOffset, z + 13, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 5 - yOffset, z + 13, ModBlocks.red_wire_coated, 0, 3);
		world.setBlock(x + 14, y + 5 - yOffset, z + 13, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 5 - yOffset, z + 14, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 5 - yOffset, z + 14, ModBlocks.red_wire_coated, 0, 3);
		world.setBlock(x + 14, y + 5 - yOffset, z + 14, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 5 - yOffset, z + 15, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 5 - yOffset, z + 15, ModBlocks.red_wire_coated, 0, 3);
		world.setBlock(x + 14, y + 5 - yOffset, z + 15, this.Block2, 0, 3);
		world.setBlock(x + 9, y + 5 - yOffset, z + 16, this.Block2, 0, 3);
		world.setBlock(x + 10, y + 5 - yOffset, z + 16, this.Block2, 0, 3);
		world.setBlock(x + 11, y + 5 - yOffset, z + 16, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 5 - yOffset, z + 16, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 5 - yOffset, z + 16, ModBlocks.red_wire_coated, 0, 3);
		world.setBlock(x + 14, y + 5 - yOffset, z + 16, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 5 - yOffset, z + 16, this.Block2, 0, 3);
		world.setBlock(x + 16, y + 5 - yOffset, z + 16, this.Block2, 0, 3);
		world.setBlock(x + 17, y + 5 - yOffset, z + 16, this.Block2, 0, 3);
		world.setBlock(x + 8, y + 5 - yOffset, z + 17, this.Block2, 0, 3);
		world.setBlock(x + 9, y + 5 - yOffset, z + 17, this.Block2, 0, 3);
		world.setBlock(x + 10, y + 5 - yOffset, z + 17, this.Block2, 0, 3);
		world.setBlock(x + 11, y + 5 - yOffset, z + 17, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 5 - yOffset, z + 17, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 5 - yOffset, z + 17, ModBlocks.red_wire_coated, 0, 3);
		world.setBlock(x + 14, y + 5 - yOffset, z + 17, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 5 - yOffset, z + 17, this.Block2, 0, 3);
		world.setBlock(x + 16, y + 5 - yOffset, z + 17, this.Block2, 0, 3);
		world.setBlock(x + 17, y + 5 - yOffset, z + 17, this.Block2, 0, 3);
		world.setBlock(x + 18, y + 5 - yOffset, z + 17, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 5 - yOffset, z + 18, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 5 - yOffset, z + 18, ModBlocks.red_wire_coated, 0, 3);
		world.setBlock(x + 14, y + 5 - yOffset, z + 18, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 5 - yOffset, z + 19, this.Block1, 0, 3);
		world.setBlock(x + 12, y + 6 - yOffset, z + 1, this.Block1, 0, 3);
		world.setBlock(x + 14, y + 6 - yOffset, z + 1, this.Block1, 0, 3);
		world.setBlock(x + 12, y + 6 - yOffset, z + 3, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 6 - yOffset, z + 3, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 6 - yOffset, z + 3, this.Block2, 0, 3);
		world.setBlock(x + 4, y + 6 - yOffset, z + 4, this.Block2, 0, 3);
		world.setBlock(x + 11, y + 6 - yOffset, z + 4, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 6 - yOffset, z + 4, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 6 - yOffset, z + 4, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 6 - yOffset, z + 4, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 6 - yOffset, z + 4, this.Block2, 0, 3);
		world.setBlock(x + 22, y + 6 - yOffset, z + 4, this.Block2, 0, 3);
		world.setBlock(x + 3, y + 6 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 4, y + 6 - yOffset, z + 5, this.Block1, 0, 3);
		world.setBlock(x + 5, y + 6 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 6, y + 6 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 7, y + 6 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 8, y + 6 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 9, y + 6 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 10, y + 6 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 11, y + 6 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 6 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 6 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 6 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 6 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 16, y + 6 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 17, y + 6 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 18, y + 6 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 19, y + 6 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 20, y + 6 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 21, y + 6 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 22, y + 6 - yOffset, z + 5, this.Block1, 0, 3);
		world.setBlock(x + 23, y + 6 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 4, y + 6 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 11, y + 6 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 6 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 6 - yOffset, z + 6, Blocks.chest, 2, 3);
		if(world.getBlock(x + 13, y + 6 - yOffset, z + 6) == Blocks.chest)
		{
			WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.vertibird, (TileEntityChest)world.getTileEntity(x + 13, y + 6 - yOffset, z + 6), 8);
		}
		world.setBlock(x + 14, y + 6 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 6 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 22, y + 6 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 11, y + 6 - yOffset, z + 7, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 6 - yOffset, z + 7, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 6 - yOffset, z + 7, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 6 - yOffset, z + 7, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 6 - yOffset, z + 7, this.Block2, 0, 3);
		world.setBlock(x + 11, y + 6 - yOffset, z + 8, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 6 - yOffset, z + 8, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 6 - yOffset, z + 8, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 6 - yOffset, z + 8, this.Block2, 0, 3);
		world.setBlock(x + 15, y + 6 - yOffset, z + 8, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 6 - yOffset, z + 9, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 6 - yOffset, z + 9, this.Block3, 0, 3);
		world.setBlock(x + 14, y + 6 - yOffset, z + 9, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 6 - yOffset, z + 10, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 6 - yOffset, z + 10, this.Block3, 0, 3);
		world.setBlock(x + 14, y + 6 - yOffset, z + 10, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 6 - yOffset, z + 11, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 6 - yOffset, z + 11, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 6 - yOffset, z + 11, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 6 - yOffset, z + 12, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 6 - yOffset, z + 12, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 6 - yOffset, z + 12, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 6 - yOffset, z + 13, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 6 - yOffset, z + 14, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 6 - yOffset, z + 17, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 6 - yOffset, z + 18, this.Block2, 0, 3);
		world.setBlock(x + 4, y + 7 - yOffset, z + 4, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 7 - yOffset, z + 4, this.Block2, 0, 3);
		world.setBlock(x + 22, y + 7 - yOffset, z + 4, this.Block2, 0, 3);
		world.setBlock(x + 3, y + 7 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 4, y + 7 - yOffset, z + 5, this.Block1, 0, 3);
		world.setBlock(x + 5, y + 7 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 7 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 7 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 7 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 21, y + 7 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 22, y + 7 - yOffset, z + 5, this.Block1, 0, 3);
		world.setBlock(x + 23, y + 7 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 4, y + 7 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 7 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 7 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 7 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 22, y + 7 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 7 - yOffset, z + 7, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 7 - yOffset, z + 7, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 7 - yOffset, z + 7, this.Block2, 0, 3);
		world.setBlock(x + 12, y + 7 - yOffset, z + 8, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 7 - yOffset, z + 8, this.Block2, 0, 3);
		world.setBlock(x + 14, y + 7 - yOffset, z + 8, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 7 - yOffset, z + 9, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 7 - yOffset, z + 10, this.Block2, 0, 3);
		world.setBlock(x + 13, y + 7 - yOffset, z + 11, this.Block2, 0, 3);
		world.setBlock(x + 4, y + 8 - yOffset, z + 4, this.Block2, 0, 3);
		world.setBlock(x + 22, y + 8 - yOffset, z + 4, this.Block2, 0, 3);
		world.setBlock(x + 3, y + 8 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 4, y + 8 - yOffset, z + 5, this.Block1, 0, 3);
		world.setBlock(x + 5, y + 8 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 21, y + 8 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 22, y + 8 - yOffset, z + 5, this.Block1, 0, 3);
		world.setBlock(x + 23, y + 8 - yOffset, z + 5, this.Block2, 0, 3);
		world.setBlock(x + 4, y + 8 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 22, y + 8 - yOffset, z + 6, this.Block2, 0, 3);
		world.setBlock(x + 4, y + 9 - yOffset, z + 5, this.Block1, 0, 3);
		world.setBlock(x + 22, y + 9 - yOffset, z + 5, this.Block1, 0, 3);
		world.setBlock(x + 4, y + 10 - yOffset, z + 1, this.Block3, 0, 3);
		world.setBlock(x + 22, y + 10 - yOffset, z + 1, this.Block3, 0, 3);
		world.setBlock(x + 4, y + 10 - yOffset, z + 2, this.Block3, 0, 3);
		world.setBlock(x + 22, y + 10 - yOffset, z + 2, this.Block3, 0, 3);
		world.setBlock(x + 4, y + 10 - yOffset, z + 3, this.Block3, 0, 3);
		world.setBlock(x + 22, y + 10 - yOffset, z + 3, this.Block3, 0, 3);
		world.setBlock(x + 4, y + 10 - yOffset, z + 4, this.Block3, 0, 3);
		world.setBlock(x + 22, y + 10 - yOffset, z + 4, this.Block3, 0, 3);
		world.setBlock(x + 0, y + 10 - yOffset, z + 5, this.Block3, 0, 3);
		world.setBlock(x + 1, y + 10 - yOffset, z + 5, this.Block3, 0, 3);
		world.setBlock(x + 2, y + 10 - yOffset, z + 5, this.Block3, 0, 3);
		world.setBlock(x + 3, y + 10 - yOffset, z + 5, this.Block3, 0, 3);
		world.setBlock(x + 4, y + 10 - yOffset, z + 5, this.Block1, 0, 3);
		world.setBlock(x + 5, y + 10 - yOffset, z + 5, this.Block3, 0, 3);
		world.setBlock(x + 6, y + 10 - yOffset, z + 5, this.Block3, 0, 3);
		world.setBlock(x + 7, y + 10 - yOffset, z + 5, this.Block3, 0, 3);
		world.setBlock(x + 8, y + 10 - yOffset, z + 5, this.Block3, 0, 3);
		world.setBlock(x + 18, y + 10 - yOffset, z + 5, this.Block3, 0, 3);
		world.setBlock(x + 19, y + 10 - yOffset, z + 5, this.Block3, 0, 3);
		world.setBlock(x + 20, y + 10 - yOffset, z + 5, this.Block3, 0, 3);
		world.setBlock(x + 21, y + 10 - yOffset, z + 5, this.Block3, 0, 3);
		world.setBlock(x + 22, y + 10 - yOffset, z + 5, this.Block1, 0, 3);
		world.setBlock(x + 23, y + 10 - yOffset, z + 5, this.Block3, 0, 3);
		world.setBlock(x + 24, y + 10 - yOffset, z + 5, this.Block3, 0, 3);
		world.setBlock(x + 25, y + 10 - yOffset, z + 5, this.Block3, 0, 3);
		world.setBlock(x + 26, y + 10 - yOffset, z + 5, this.Block3, 0, 3);
		world.setBlock(x + 4, y + 10 - yOffset, z + 6, this.Block3, 0, 3);
		world.setBlock(x + 22, y + 10 - yOffset, z + 6, this.Block3, 0, 3);
		world.setBlock(x + 4, y + 10 - yOffset, z + 7, this.Block3, 0, 3);
		world.setBlock(x + 22, y + 10 - yOffset, z + 7, this.Block3, 0, 3);
		world.setBlock(x + 4, y + 10 - yOffset, z + 8, this.Block3, 0, 3);
		world.setBlock(x + 22, y + 10 - yOffset, z + 8, this.Block3, 0, 3);
		world.setBlock(x + 4, y + 10 - yOffset, z + 9, this.Block3, 0, 3);
		world.setBlock(x + 22, y + 10 - yOffset, z + 9, this.Block3, 0, 3);

		generate_r02_last(world, rand, x, y, z, yOffset);
		return true;

	}
	public boolean generate_r02_last(World world, Random rand, int x, int y, int z, int yOffset)
	{

		world.setBlock(x + 12, y + 2 - yOffset, z + 1, Blocks.lever, 3, 3);
		world.setBlock(x + 14, y + 2 - yOffset, z + 1, Blocks.lever, 3, 3);
		if(GeneralConfig.enableDebugMode)
			System.out.print("[Debug] Successfully spawned Vertibird at " + x + " " + y +" " + z + "\n");
		return true;

	}

}