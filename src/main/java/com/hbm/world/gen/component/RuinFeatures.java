package com.hbm.world.gen.component;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class RuinFeatures {
	
	public static void registerComponents() {
		MapGenStructureIO.func_143031_a(NTMRuin1.class, "NTMRuin1");
		MapGenStructureIO.func_143031_a(NTMRuin2.class, "NTMRuin2");
		MapGenStructureIO.func_143031_a(NTMRuin3.class, "NTMRuin3");
		MapGenStructureIO.func_143031_a(NTMRuin4.class, "NTMRuin4");
	}
	
	public static class NTMRuin1 extends Component {
		
		private static ConcreteBricks RandomConcreteBricks = new ConcreteBricks();
		
		public NTMRuin1() {
			super();
		}
		
		public NTMRuin1(Random rand, int minX, int minY, int minZ) {
			super(rand, minX, minY, minZ, 8, 6, 10);
		}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			//System.out.println(this.coordBaseMode);
			if(!setAverageHeight(world, box, this.boundingBox.minY)) {
				return false;
			}
			//System.out.println("" + this.boundingBox.minX + ", " + this.boundingBox.minY + ", " + this.boundingBox.minZ);
			
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 0, 0, this.sizeX, this.sizeZ, -1, box);
			
			int pillarMetaWE = getPillarMeta(4);
			int pillarMetaNS = getPillarMeta(8);
			
			this.fillWithBlocks(world, box, 0, 0, 0, 0, this.sizeY, 0, ModBlocks.concrete_pillar, Blocks.air, false); //Back Wall
			this.fillWithMetadataBlocks(world, box, 1, 3, 0, 3, 3, 0, ModBlocks.concrete_pillar, pillarMetaWE, Blocks.air, 0, false);
			this.fillWithBlocks(world, box, 4, 0, 0, 4, this.sizeY - 1, 0, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithMetadataBlocks(world, box, 5, 3, 0, this.sizeX - 1, 3, 0, ModBlocks.concrete_pillar, pillarMetaWE, Blocks.air, 0, false);
			this.fillWithBlocks(world, box, this.sizeX, 0, 0, this.sizeX, this.sizeY - 1, 0, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 1, 0, 0, 3, 0, 0, false, rand, NTMRuin1.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 5, 0, 0, this.sizeX - 1, 0, 0, false, rand, NTMRuin1.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 1, 1, 0, 1, 2, 0, false, rand, NTMRuin1.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 3, 1, 0, 3, 2, 0, false, rand, NTMRuin1.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 5, 1, 0, 5, 2, 0, false, rand, NTMRuin1.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, this.sizeX - 1, 1, 0, this.sizeX - 1, 2, 0, false, rand, NTMRuin1.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 1, 4, 0, 3, 4, 0, false, rand, NTMRuin1.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 5, 4, 0, this.sizeX - 1, 4, 0, false, rand, NTMRuin1.RandomConcreteBricks);
			this.fillWithMetadataBlocks(world, box, 0, 3, 1, 0, 3, this.sizeZ - 1, ModBlocks.concrete_pillar, pillarMetaNS, Blocks.air, 0, false); //Left Wall
			this.fillWithBlocks(world, box, 0, 0, this.sizeZ, 0, this.sizeY - 1, this.sizeZ, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 0, 0, 1, 0, 0, this.sizeZ - 1, false, rand, NTMRuin1.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 1, 1, 0, 2, 2, false, rand, NTMRuin1.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 1, 4, 0, 2, 6, false, rand, NTMRuin1.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 1, this.sizeZ - 2, 0, 2, this.sizeZ - 1, false, rand, NTMRuin1.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 4, 1, 0, 4, 5, false, rand, NTMRuin1.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 5, 1, 0, 5, 2, false, rand, NTMRuin1.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 4, this.sizeZ - 2, 0, 4, this.sizeZ - 1, false, rand, NTMRuin1.RandomConcreteBricks);
			this.fillWithMetadataBlocks(world, box, 1, 3, this.sizeZ, 3, 3, this.sizeZ, ModBlocks.concrete_pillar, pillarMetaWE, Blocks.air, 0, false); //Front Wall
			this.fillWithBlocks(world, box, 4, 0, this.sizeZ, 4, this.sizeY - 2, this.sizeZ, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithMetadataBlocks(world, box, 5, 3, this.sizeZ, this.sizeX - 1, 3, this.sizeZ, ModBlocks.concrete_pillar, pillarMetaWE, Blocks.air, 0, false);
			this.fillWithBlocks(world, box, this.sizeX, 0, this.sizeZ, this.sizeX, this.sizeY - 2, this.sizeZ, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 1, 0, this.sizeZ, 3, 0, this.sizeZ, false, rand, NTMRuin1.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 5, 0, this.sizeZ, this.sizeX - 1, 0, this.sizeZ, false, rand, NTMRuin1.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 1, 1, this.sizeZ, 1, 2, this.sizeZ, false, rand, NTMRuin1.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 3, 1, this.sizeZ, 3, 2, this.sizeZ, false, rand, NTMRuin1.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 5, 1, this.sizeZ, 5, 2, this.sizeZ, false, rand, NTMRuin1.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, this.sizeX - 1, 1, this.sizeZ, this.sizeX - 1, 2, this.sizeZ, false, rand, NTMRuin1.RandomConcreteBricks);
			this.fillWithMetadataBlocks(world, box, this.sizeX, 3, 1, this.sizeX, 3, 2, ModBlocks.concrete_pillar, pillarMetaNS, Blocks.air, 0, false); //Right Wall
			this.fillWithMetadataBlocks(world, box, this.sizeX, 3, this.sizeZ - 1, this.sizeX, 3, this.sizeZ - 1, ModBlocks.concrete_pillar, pillarMetaNS, Blocks.air, 0, false);
			this.fillWithRandomizedBlocks(world, box, this.sizeX, 0, 1, this.sizeX, 0, 4, false, rand, NTMRuin1.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, this.sizeX, 1, 1, this.sizeX, 2, 2, false, rand, NTMRuin1.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, this.sizeX, 0, 6, this.sizeX, 0, 6, false, rand, NTMRuin1.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, this.sizeX, 0, this.sizeZ - 2, this.sizeX, 1, this.sizeZ - 1, false, rand, NTMRuin1.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, this.sizeX, 2, this.sizeZ - 1, this.sizeX, 2, this.sizeZ - 1, false, rand, NTMRuin1.RandomConcreteBricks);
			
			this.randomlyFillWithBlocks(world, box, rand, 0.25F, 1, 0, 1, this.sizeX - 1, 0, this.sizeZ - 1, Blocks.gravel, Blocks.air, false);
			
			return true;
		}
	}
	
	public static class NTMRuin2 extends Component {
		
		private static ConcreteBricks RandomConcreteBricks = new ConcreteBricks();
		
		public NTMRuin2() {
			super();
		}
		
		public NTMRuin2(Random rand, int minX, int minY, int minZ) {
			super(rand, minX, minY, minZ, 7, 5, 10);
		}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			//System.out.println(this.coordBaseMode);
			if(!setAverageHeight(world, box, this.boundingBox.minY)) {
				return false;
			}
			//System.out.println("" + this.boundingBox.minX + ", " + this.boundingBox.minY + ", " + this.boundingBox.minZ);
			
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 0, 0, this.sizeX, this.sizeZ, -1, box);
			
			int pillarMetaWE = getPillarMeta(4);
			int pillarMetaNS = getPillarMeta(8);
			
			this.fillWithBlocks(world, box, 0, 0, 0, 0, 3, 0, ModBlocks.concrete_pillar, Blocks.air, false); //Back Wall
			this.fillWithMetadataBlocks(world, box, 1, 3, 0, this.sizeX - 1, 3, 0, ModBlocks.concrete_pillar, pillarMetaWE, Blocks.air, 0, false);
			this.fillWithBlocks(world, box, this.sizeX, 0, 0, this.sizeX, this.sizeY, 0, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 1, 0, 0, this.sizeX - 1, 0, 0, false, rand, NTMRuin2.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 1, 1, 0, 1, 2, 0, false, rand, NTMRuin2.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 3, 1, 0, 4, 2, 0, false, rand, NTMRuin2.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, this.sizeX - 1, 1, 0, this.sizeX - 1, 2, 0, false, rand, NTMRuin2.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 3, 4, 0, this.sizeX - 1, 4, 0, false, rand, NTMRuin2.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, this.sizeX - 1, this.sizeY, 0, this.sizeX - 1, this.sizeY, 0, false, rand, NTMRuin2.RandomConcreteBricks);
			this.fillWithMetadataBlocks(world, box, 0, 3, 1, 0, 3, 4, ModBlocks.concrete_pillar, pillarMetaNS, Blocks.air, 0, false); //Left Wall
			this.fillWithBlocks(world, box, 0, 0, 5, 0, 0, 5, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithBlocks(world, box, 0, 0, this.sizeZ, 0, 2, this.sizeZ, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 0, 0, 1, 0, 2, 3, false, rand, NTMRuin2.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 0, this.sizeZ - 3, 0, 0, this.sizeZ - 1, false, rand, NTMRuin2.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 1, this.sizeZ - 1, 0, 1, this.sizeZ - 1, false, rand, NTMRuin2.RandomConcreteBricks);
			this.fillWithMetadataBlocks(world, box, this.sizeX - 1, 3, this.sizeZ, this.sizeX - 1, 3, this.sizeZ, ModBlocks.concrete_pillar, pillarMetaWE, Blocks.air, 0, false); //Front Wall
			this.fillWithBlocks(world, box, this.sizeX, 0, this.sizeZ, this.sizeX, 3, this.sizeZ, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 1, 0, this.sizeZ, this.sizeX - 1, 0, this.sizeZ, false, rand, NTMRuin2.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 1, 1, this.sizeZ, 1, 2, this.sizeZ, false, rand, NTMRuin2.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, this.sizeX - 1, 1, this.sizeZ, this.sizeX - 1, 2, this.sizeZ, false, rand, NTMRuin2.RandomConcreteBricks);
			this.fillWithMetadataBlocks(world, box, this.sizeX, 3, 1, this.sizeX, 3, 4, ModBlocks.concrete_pillar, pillarMetaNS, Blocks.air, 0, false); //Right Wall
			this.fillWithBlocks(world, box, this.sizeX, 0, 5, this.sizeX, 4, 5, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithMetadataBlocks(world, box, this.sizeX, 3, this.sizeZ - 2, this.sizeX, 3, this.sizeZ - 1, ModBlocks.concrete_pillar, pillarMetaNS, Blocks.air, 0, false);
			this.fillWithRandomizedBlocks(world, box, this.sizeX, 0, 1, this.sizeX, 0, 4, false, rand, NTMRuin2.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, this.sizeX, 1, 1, this.sizeX, 2, 1, false, rand, NTMRuin2.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, this.sizeX, 1, 3, this.sizeX, 2, 3, false, rand, NTMRuin2.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, this.sizeX, 1, 4, this.sizeX, 1, 4, false, rand, NTMRuin2.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, this.sizeX, 0, 6, this.sizeX, 0, this.sizeZ - 1, false, rand, NTMRuin2.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, this.sizeX, 1, 6, this.sizeX, 1, 7, false, rand, NTMRuin2.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, this.sizeX, 1, this.sizeZ - 1, this.sizeX, 2, this.sizeZ - 1, false, rand, NTMRuin2.RandomConcreteBricks);
			
			this.randomlyFillWithBlocks(world, box, rand, 0.25F, 1, 0, 1, this.sizeX - 1, 0, this.sizeZ - 1, Blocks.gravel, Blocks.air, false);
			
			return true;
		}
	}
	
	public static class NTMRuin3 extends Component {
		
		private static ConcreteBricks RandomConcreteBricks = new ConcreteBricks();
		
		public NTMRuin3() {
			super();
		}
		
		public NTMRuin3(Random rand, int minX, int minY, int minZ) {
			super(rand, minX, minY, minZ, 8, 3, 10);
		}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			//System.out.println(this.coordBaseMode);
			if(!setAverageHeight(world, box, this.boundingBox.minY)) {
				return false;
			}
			//System.out.println("" + this.boundingBox.minX + ", " + this.boundingBox.minY + ", " + this.boundingBox.minZ);
			
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 0, 0, 0, this.sizeZ, -1, box);
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, this.sizeX, 0, this.sizeX, this.sizeZ, -1, box);
			
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 1, 0, this.sizeX, 0, -1, box);
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 1, 4, this.sizeX, 4, -1, box);
			
			this.fillWithBlocks(world, box, 0, 0, 0, 0, this.sizeY, 0, ModBlocks.concrete_pillar, Blocks.air, false); //Back Wall
			this.fillWithBlocks(world, box, this.sizeX, 0, 0, this.sizeX, 1, 0, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 1, 0, 0, this.sizeX - 1, 0, 0, false, rand, NTMRuin3.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 1, 1, 0, 1, 1, 0, false, rand, NTMRuin3.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 4, 1, 0, 4, 1, 0, false, rand, NTMRuin3.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, this.sizeX - 1, 1, 0, this.sizeX - 1, 1, 0, false, rand, NTMRuin3.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 1, 2, 0, this.sizeX - 2, 2, 0, false, rand, NTMRuin3.RandomConcreteBricks);
			this.fillWithBlocks(world, box, 0, 0, 4, 0, 1, 4, ModBlocks.concrete_pillar, Blocks.air, false); //Left Wall
			placeBlockAtCurrentPosition(world, ModBlocks.concrete_pillar, 0, 0, 0, this.sizeZ, box);
			this.fillWithRandomizedBlocks(world, box, 0, 0, 1, 0, 0, 3, false, rand, NTMRuin3.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 0, 5, 0, 0, this.sizeZ - 1, false, rand, NTMRuin3.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 1, 5, 0, 1, 5, false, rand, NTMRuin3.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 1, 7, 0, 1, 7, false, rand, NTMRuin3.RandomConcreteBricks);
			this.fillWithBlocks(world, box, this.sizeX, 0, 4, this.sizeX, 1, 4, ModBlocks.concrete_pillar, Blocks.air, false); //Right Wall
			this.fillWithBlocks(world, box, this.sizeX, 0, this.sizeZ, this.sizeX, 1, this.sizeZ, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, this.sizeX, 0, 1, this.sizeX, 1, 3, false, rand, NTMRuin3.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, this.sizeX, 0, 5, this.sizeX, 0, 6, false, rand, NTMRuin3.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, this.sizeX, 0, this.sizeZ - 1, this.sizeX, 0, this.sizeZ - 1, false, rand, NTMRuin3.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, this.sizeX - 1, 0, this.sizeZ, this.sizeX - 1, 0, this.sizeZ, false, rand, NTMRuin3.RandomConcreteBricks);
			this.fillWithBlocks(world, box, 4, 0, 4, 4, 2, 4, ModBlocks.concrete_pillar, Blocks.air, false); //Center Wall
			this.fillWithRandomizedBlocks(world, box, 3, 0, 4, 3, 1, 4, false, rand, NTMRuin3.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 5, 0, 4, this.sizeX - 1, 1, 4, false, rand, NTMRuin3.RandomConcreteBricks);
			
			this.randomlyFillWithBlocks(world, box, rand, 0.05F, 1, 0, 1, this.sizeX - 1, 0, 3, Blocks.gravel, Blocks.air, false);
			this.randomlyFillWithBlocks(world, box, rand, 0.05F, 1, 0, 5, this.sizeX - 1, 0, this.sizeZ - 1, Blocks.gravel, Blocks.air, false);
			
			return true;
		}
	}
	
	public static class NTMRuin4 extends Component {
		
		private static ConcreteBricks RandomConcreteBricks = new ConcreteBricks();
		
		public NTMRuin4() {
			super();
		}
		
		public NTMRuin4(Random rand, int minX, int minY, int minZ) {
			super(rand, minX, minY, minZ, 10, 2, 11);
		}

		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			//System.out.println(this.coordBaseMode);
			if(!setAverageHeight(world, box, this.boundingBox.minY)) {
				return false;
			}
			//System.out.println("" + this.boundingBox.minX + ", " + this.boundingBox.minY + ", " + this.boundingBox.minZ);
			
			
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 0, 0, 0, this.sizeZ, -1, box);
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, this.sizeX, 5, this.sizeX, this.sizeZ, -1, box);
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 5, 0, 5, 4, -1, box);
			
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 1, this.sizeZ, this.sizeX - 1, this.sizeZ, -1, box);
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 1, 0, 4, 0, -1, box);
			placeFoundationUnderneath(world, Blocks.stonebrick, 0, 5, 5, this.sizeX - 1, 5, -1, box);
			
			this.fillWithBlocks(world, box, 0, 0, 0, 0, 1, 0, ModBlocks.concrete_pillar, Blocks.air, false); //Back Wall Pt. 1
			this.fillWithBlocks(world, box, 5, 0, 0, 5, this.sizeY, 0, ModBlocks.concrete_pillar, Blocks.air, false);
			this.fillWithRandomizedBlocks(world, box, 1, 0, 0, 4, 0, 0, false, rand, NTMRuin4.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 4, 1, 0, 4, 1, 0, false, rand, NTMRuin4.RandomConcreteBricks);
			this.fillWithBlocks(world, box, 5, 0, 5, 5, this.sizeY, 5, ModBlocks.concrete_pillar, Blocks.air, false); //Right Wall Pt. 1
			this.fillWithRandomizedBlocks(world, box, 5, 0, 1, 5, 0, 4, false, rand, NTMRuin4.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 5, 1, 1, 5, 1, 1, false, rand, NTMRuin4.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 5, 1, 4, 5, 1, 4, false, rand, NTMRuin4.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 5, 2, 1, 5, 2, 4, false, rand, NTMRuin4.RandomConcreteBricks);
			this.fillWithBlocks(world, box, this.sizeX, 0, 5, this.sizeX, 1, 5, ModBlocks.concrete_pillar, Blocks.air, false); //Back Wall Pt. 2
			this.fillWithRandomizedBlocks(world, box, 6, 0, 5, this.sizeX - 1, 0, 5, false, rand, NTMRuin4.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 6, 1, 5, 6, 1, 5, false, rand, NTMRuin4.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, this.sizeX - 1, 1, 5, this.sizeX - 1, 1, 5, false, rand, NTMRuin4.RandomConcreteBricks);
			this.fillWithBlocks(world, box, this.sizeX, 0, this.sizeZ, this.sizeX, 1, this.sizeZ, ModBlocks.concrete_pillar, Blocks.air, false); //Right Wall Pt. 2
			this.fillWithRandomizedBlocks(world, box, this.sizeX, 0, 6, this.sizeX, 0, this.sizeZ - 1, false, rand, NTMRuin4.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, this.sizeX, 1, 6, this.sizeX, 1, this.sizeZ - 3, false, rand, NTMRuin4.RandomConcreteBricks);
			this.fillWithBlocks(world, box, 0, 0, this.sizeZ, 0, 0, this.sizeZ, ModBlocks.concrete_pillar, Blocks.air, false); //Front Wall
			this.fillWithRandomizedBlocks(world, box, 1, 0, this.sizeZ, 1, 0, this.sizeZ, false, rand, NTMRuin4.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 6, 0, this.sizeZ, 7, 0, this.sizeZ, false, rand, NTMRuin4.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, this.sizeX - 1, 0, this.sizeZ, this.sizeX - 1, 0, this.sizeZ, false, rand, NTMRuin4.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 0, 1, 0, 0, this.sizeZ - 1, false, rand, NTMRuin4.RandomConcreteBricks); //Left Wall
			this.fillWithRandomizedBlocks(world, box, 0, 1, 1, 0, 1, 1, false, rand, NTMRuin4.RandomConcreteBricks);
			this.fillWithRandomizedBlocks(world, box, 0, 1, 4, 0, 1, 7, false, rand, NTMRuin4.RandomConcreteBricks);
			
			this.randomlyFillWithBlocks(world, box, rand, 0.05F, 1, 0, 1, 4, 0, 5, Blocks.gravel, Blocks.air, false);
			this.randomlyFillWithBlocks(world, box, rand, 0.05F, 1, 0, 6, this.sizeX - 1, 0, this.sizeZ - 1, Blocks.gravel, Blocks.air, false);
			
			return true;
		}
	}
	
}
