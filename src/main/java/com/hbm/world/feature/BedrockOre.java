package com.hbm.world.feature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockBedrockOreTE.TileEntityBedrockOre;
import com.hbm.config.WorldConfig;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemBedrockOre.EnumBedrockOre;
import com.hbm.util.WeightedRandomGeneric;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BedrockOre {

	public static List<WeightedRandomGeneric<BedrockOreDefinition>> weightedOres = new ArrayList<>();
	public static List<WeightedRandomGeneric<BedrockOreDefinition>> weightedOresNether = new ArrayList<>();
	
	public static HashMap<String, BedrockOreDefinition> replacements = new HashMap();
	
	public static void init() {
		BedrockOre.registerBedrockOre(BedrockOre.weightedOres, new BedrockOreDefinition(EnumBedrockOre.IRON,					1),													WorldConfig.bedrockIronSpawn);
		BedrockOre.registerBedrockOre(BedrockOre.weightedOres, new BedrockOreDefinition(EnumBedrockOre.COPPER,				1),													WorldConfig.bedrockCopperSpawn);
		BedrockOre.registerBedrockOre(BedrockOre.weightedOres, new BedrockOreDefinition(EnumBedrockOre.BORAX,					3,	new FluidStack(Fluids.SULFURIC_ACID, 500)),		WorldConfig.bedrockBoraxSpawn);
		BedrockOre.registerBedrockOre(BedrockOre.weightedOres, new BedrockOreDefinition(EnumBedrockOre.CHLOROCALCITE,			3,	new FluidStack(Fluids.SULFURIC_ACID, 500)),		WorldConfig.bedrockChlorocalciteSpawn);
		BedrockOre.registerBedrockOre(BedrockOre.weightedOres, new BedrockOreDefinition(EnumBedrockOre.ASBESTOS,				2),													WorldConfig.bedrockAsbestosSpawn);
		BedrockOre.registerBedrockOre(BedrockOre.weightedOres, new BedrockOreDefinition(EnumBedrockOre.NIOBIUM,				2,	new FluidStack(Fluids.ACID, 500)),				WorldConfig.bedrockNiobiumSpawn);
		BedrockOre.registerBedrockOre(BedrockOre.weightedOres, new BedrockOreDefinition(EnumBedrockOre.TITANIUM,				2,	new FluidStack(Fluids.SULFURIC_ACID, 500)),		WorldConfig.bedrockTitaniumSpawn);
		BedrockOre.registerBedrockOre(BedrockOre.weightedOres, new BedrockOreDefinition(EnumBedrockOre.TUNGSTEN,				2,	new FluidStack(Fluids.ACID, 500)),				WorldConfig.bedrockTungstenSpawn);
		BedrockOre.registerBedrockOre(BedrockOre.weightedOres, new BedrockOreDefinition(EnumBedrockOre.GOLD,					1),													WorldConfig.bedrockGoldSpawn);
		BedrockOre.registerBedrockOre(BedrockOre.weightedOres, new BedrockOreDefinition(EnumBedrockOre.URANIUM,				4,	new FluidStack(Fluids.SULFURIC_ACID, 500)),		WorldConfig.bedrockUraniumSpawn);
		BedrockOre.registerBedrockOre(BedrockOre.weightedOres, new BedrockOreDefinition(EnumBedrockOre.THORIUM,				4,	new FluidStack(Fluids.SULFURIC_ACID, 500)),		WorldConfig.bedrockThoriumSpawn);
		BedrockOre.registerBedrockOre(BedrockOre.weightedOres, new BedrockOreDefinition(EnumBedrockOre.FLUORITE,				1),													WorldConfig.bedrockFluoriteSpawn);
		BedrockOre.registerBedrockOre(BedrockOre.weightedOres, new BedrockOreDefinition(new ItemStack(Items.coal, 8),			1,	0x202020),										WorldConfig.bedrockCoalSpawn);
		BedrockOre.registerBedrockOre(BedrockOre.weightedOres, new BedrockOreDefinition(new ItemStack(ModItems.niter, 4),		2,	0x808080,	new FluidStack(Fluids.ACID, 500)),	WorldConfig.bedrockNiterSpawn);
		BedrockOre.registerBedrockOre(BedrockOre.weightedOres, new BedrockOreDefinition(new ItemStack(Items.redstone, 4),		1,	0xd01010),										WorldConfig.bedrockRedstoneSpawn);

		BedrockOre.registerBedrockOre(BedrockOre.weightedOresNether, new BedrockOreDefinition(new ItemStack(Items.glowstone_dust, 4),		1,	0xF9FF4D),							WorldConfig.bedrockGlowstoneSpawn);
		BedrockOre.registerBedrockOre(BedrockOre.weightedOresNether, new BedrockOreDefinition(new ItemStack(ModItems.powder_fire, 4),		1,	0xD7341F),							WorldConfig.bedrockPhosphorusSpawn);
		BedrockOre.registerBedrockOre(BedrockOre.weightedOresNether, new BedrockOreDefinition(new ItemStack(Items.quartz, 4),				1,	0xF0EFDD),							WorldConfig.bedrockQuartzSpawn);

		BedrockOre.replacements.put(EnumBedrockOre.IRON.oreName, new BedrockOreDefinition(EnumBedrockOre.HEMATITE, 1));
		BedrockOre.replacements.put(EnumBedrockOre.COPPER.oreName, new BedrockOreDefinition(EnumBedrockOre.MALACHITE, 1));
	}
	
	public static void registerBedrockOre(List<WeightedRandomGeneric<BedrockOreDefinition>> list, BedrockOreDefinition def, int weight) {
		WeightedRandomGeneric<BedrockOreDefinition> weighted = new WeightedRandomGeneric<>(def, weight);
		list.add(weighted);
	}

	public static void generate(World world, int x, int z, ItemStack stack, FluidStack acid, int color, int tier) {
		BedrockOre.generate(world, x, z, stack, acid, color, tier, ModBlocks.stone_depth);
	}

	public static void generate(World world, int x, int z, ItemStack stack, FluidStack acid, int color, int tier, Block depthRock) {
		
		for(int ix = x - 1; ix <= x + 1; ix++) {
			for(int iz = z - 1; iz <= z + 1; iz++) {
				
				Block b = world.getBlock(ix, 0, iz);
				if(b.isReplaceableOreGen(world, ix, 0, iz, Blocks.bedrock)) {
					if((ix == x && iz == z) || world.rand.nextBoolean()) {
						
						world.setBlock(ix, 0, iz, ModBlocks.ore_bedrock);
						TileEntityBedrockOre ore = (TileEntityBedrockOre) world.getTileEntity(ix, 0, iz);
						ore.resource = stack;
						ore.color = color;
						ore.shape = world.rand.nextInt(10);
						ore.acidRequirement = acid;
						ore.tier = tier;
						world.markBlockForUpdate(ix, 0, iz);
						world.markTileEntityChunkModified(ix, 0, iz, ore);
					}
				}
			}
		}
		
		for(int ix = x - 3; ix <= x + 3; ix++) {
			for(int iz = z - 3; iz <= z + 3; iz++) {
				
				for(int iy = 1; iy < 7; iy++) {
					if(iy < 3 || world.getBlock(ix, iy, iz) == Blocks.bedrock) {
						
						Block b = world.getBlock(ix, iy, iz);
						if(b.isReplaceableOreGen(world, ix, iy, iz, Blocks.stone) || b.isReplaceableOreGen(world, ix, iy, iz, Blocks.bedrock)) {
							world.setBlock(ix, iy, iz, depthRock);
						}
					}
				}
			}
		}
	}
	
	public static class BedrockOreDefinition {
		public ItemStack stack;
		public FluidStack acid;
		public String id;
		public int tier;
		public int color;
		
		public BedrockOreDefinition(ItemStack stack, int tier, int color) {
			this(stack, tier, color, null);
		}
		
		public BedrockOreDefinition(ItemStack stack, int tier, int color, FluidStack acid) {
			this.stack = stack;
			this.id = stack.toString();
			this.tier = tier;
			this.color = color;
			this.acid = acid;
		}
		
		public BedrockOreDefinition(EnumBedrockOre type, int tier) {
			this(type, tier, null);
		}
		
		public BedrockOreDefinition(EnumBedrockOre type, int tier, FluidStack acid) {
			this.stack = DictFrame.fromOne(ModItems.ore_bedrock, type);
			this.id = "ore" + type.oreName;
			this.color = type.color;
			this.tier = tier;
			this.acid = acid;
		}
	}
}
