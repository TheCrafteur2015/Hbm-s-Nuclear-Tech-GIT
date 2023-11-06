package com.hbm.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.HashBiMap;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.MetaBlock;
import com.hbm.main.MainRegistry;
import com.hbm.util.Compat;
import com.hbm.util.Tuple.Triplet;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class FalloutConfigJSON {
	
	public static final List<FalloutEntry> entries = new ArrayList<>();
	public static Random rand = new Random();

	public static final Gson gson = new Gson();
	
	public static void initialize() {
		File folder = MainRegistry.configHbmDir;

		File config = new File(folder.getAbsolutePath() + File.separatorChar + "hbmFallout.json");
		File template = new File(folder.getAbsolutePath() + File.separatorChar + "_hbmFallout.json");
		
		FalloutConfigJSON.initDefault();
		
		if(!config.exists()) {
			FalloutConfigJSON.writeDefault(template);
		} else {
			List<FalloutEntry> conf = FalloutConfigJSON.readConfig(config);
			
			if(conf != null) {
				FalloutConfigJSON.entries.clear();
				FalloutConfigJSON.entries.addAll(conf);
			}
		}
	}

	
	private static void initDefault() {
		
		double woodEffectRange = 65D;
		/* destroy all leaves within the radios, kill all leaves outside of it */
		FalloutConfigJSON.entries.add(new FalloutEntry()	.mB(Blocks.leaves)			.prim(new Triplet<>(Blocks.air, 0, 1))				.max(woodEffectRange));
		FalloutConfigJSON.entries.add(new FalloutEntry()	.mB(Blocks.leaves2)			.prim(new Triplet<>(Blocks.air, 0, 1))				.max(woodEffectRange));
		FalloutConfigJSON.entries.add(new FalloutEntry()	.mB(ModBlocks.waste_leaves)	.prim(new Triplet<>(Blocks.air, 0, 1))				.max(woodEffectRange));
		FalloutConfigJSON.entries.add(new FalloutEntry()	.mB(Blocks.leaves)			.prim(new Triplet<>(ModBlocks.waste_leaves, 0, 1))	.min(woodEffectRange));
		FalloutConfigJSON.entries.add(new FalloutEntry()	.mB(Blocks.leaves2)			.prim(new Triplet<>(ModBlocks.waste_leaves, 0, 1))	.min(woodEffectRange));
		
		FalloutConfigJSON.entries.add(new FalloutEntry()	.mB(Blocks.log)							.prim(new Triplet(ModBlocks.waste_log, 0, 1))		.max(woodEffectRange));
		FalloutConfigJSON.entries.add(new FalloutEntry()	.mB(Blocks.log2)						.prim(new Triplet(ModBlocks.waste_log, 0, 1))		.max(woodEffectRange));
		FalloutConfigJSON.entries.add(new FalloutEntry()	.mB(Blocks.red_mushroom_block).mM(10)	.prim(new Triplet(ModBlocks.waste_log, 0, 1))		.max(woodEffectRange));
		FalloutConfigJSON.entries.add(new FalloutEntry()	.mB(Blocks.brown_mushroom_block).mM(10)	.prim(new Triplet(ModBlocks.waste_log, 0, 1))		.max(woodEffectRange));
		FalloutConfigJSON.entries.add(new FalloutEntry()	.mB(Blocks.red_mushroom_block)			.prim(new Triplet(Blocks.air, 0, 1))				.max(woodEffectRange));
		FalloutConfigJSON.entries.add(new FalloutEntry()	.mB(Blocks.brown_mushroom_block)		.prim(new Triplet(Blocks.air, 0, 1))				.max(woodEffectRange));
		FalloutConfigJSON.entries.add(new FalloutEntry()	.mB(Blocks.planks)						.prim(new Triplet(ModBlocks.waste_planks, 0, 1))	.max(woodEffectRange));

		FalloutEntry stoneCore = new FalloutEntry().prim(new Triplet(ModBlocks.sellafield, 1, 1)).max(5).sol(true);
		FalloutEntry stoneInner = new FalloutEntry().prim(new Triplet(ModBlocks.sellafield, 0, 1)).min(5).max(15).sol(true);
		FalloutEntry stoneOuter = new FalloutEntry().prim(new Triplet(ModBlocks.sellafield_slaked, 0, 1)).min(15).max(50).sol(true);
		
		FalloutConfigJSON.entries.add(stoneCore.clone().mB(Blocks.stone));
		FalloutConfigJSON.entries.add(stoneInner.clone().mB(Blocks.stone));
		FalloutConfigJSON.entries.add(stoneOuter.clone().mB(Blocks.stone));
		FalloutConfigJSON.entries.add(stoneCore.clone().mB(Blocks.gravel));
		FalloutConfigJSON.entries.add(stoneInner.clone().mB(Blocks.gravel));
		FalloutConfigJSON.entries.add(stoneOuter.clone().mB(Blocks.gravel));
		/* recontaminate slaked sellafield */
		FalloutConfigJSON.entries.add(stoneCore.clone().mB(ModBlocks.sellafield_slaked));
		FalloutConfigJSON.entries.add(stoneInner.clone().mB(ModBlocks.sellafield_slaked));
		
		FalloutConfigJSON.entries.add(new FalloutEntry()
				.mB(Blocks.grass)
				.prim(new Triplet(ModBlocks.waste_earth, 0, 1)));
		FalloutConfigJSON.entries.add(new FalloutEntry()
				.mB(Blocks.mycelium)
				.prim(new Triplet(ModBlocks.waste_mycelium, 0, 1)));
		FalloutConfigJSON.entries.add(new FalloutEntry()
				.mB(Blocks.sand).mM(0)
				.prim(new Triplet(ModBlocks.waste_trinitite, 0, 1))
				.c(0.05));
		FalloutConfigJSON.entries.add(new FalloutEntry()
				.mB(Blocks.sand).mM(1)
				.prim(new Triplet(ModBlocks.waste_trinitite_red, 0, 1))
				.c(0.05));
		FalloutConfigJSON.entries.add(new FalloutEntry()
				.mB(Blocks.clay)
				.prim(new Triplet(Blocks.hardened_clay, 0, 1)));
		FalloutConfigJSON.entries.add(new FalloutEntry()
				.mB(Blocks.mossy_cobblestone)
				.prim(new Triplet(Blocks.coal_ore, 0, 1)));
		FalloutConfigJSON.entries.add(new FalloutEntry()
				.mB(Blocks.coal_ore)
				.prim(new Triplet(Blocks.diamond_ore, 0, 3), new Triplet(Blocks.emerald_ore, 0, 2))
				.c(0.5));
		FalloutConfigJSON.entries.add(new FalloutEntry()
				.mB(ModBlocks.ore_lignite)
				.prim(new Triplet(Blocks.diamond_ore, 0, 1))
				.c(0.2));
		FalloutConfigJSON.entries.add(new FalloutEntry()
				.mB(ModBlocks.ore_uranium)
				.prim(new Triplet(ModBlocks.ore_schrabidium, 0, 1), new Triplet(ModBlocks.ore_uranium_scorched, 0, 99)));
		FalloutConfigJSON.entries.add(new FalloutEntry()
				.mB(ModBlocks.ore_nether_uranium)
				.prim(new Triplet(ModBlocks.ore_nether_schrabidium, 0, 1), new Triplet(ModBlocks.ore_nether_uranium_scorched, 0, 99)));
		FalloutConfigJSON.entries.add(new FalloutEntry()
				.mB(ModBlocks.ore_gneiss_uranium)
				.prim(new Triplet(ModBlocks.ore_gneiss_schrabidium, 0, 1), new Triplet(ModBlocks.ore_gneiss_uranium_scorched, 0, 99)));
		
		/// COMPAT ///
		Block deepslate = Compat.tryLoadBlock(Compat.MOD_EF, "deepslate");
		if(deepslate != null) { //identical to stone
			FalloutConfigJSON.entries.add(stoneCore.clone().mB(deepslate));
			FalloutConfigJSON.entries.add(stoneInner.clone().mB(deepslate));
			FalloutConfigJSON.entries.add(stoneOuter.clone().mB(deepslate));
		}
		Block stone = Compat.tryLoadBlock(Compat.MOD_EF, "stone");
		if(stone != null) { //identical to stone
			FalloutConfigJSON.entries.add(stoneCore.clone().mB(stone));
			FalloutConfigJSON.entries.add(stoneInner.clone().mB(stone));
			FalloutConfigJSON.entries.add(stoneOuter.clone().mB(stone));
		}
	}
	
	private static void writeDefault(File file) {

		try {
			JsonWriter writer = new JsonWriter(new FileWriter(file));
			writer.setIndent("  ");					//pretty formatting
			writer.beginObject();					//initial '{'
			writer.name("entries").beginArray();	//all recipes are stored in an array called "entries"
			
			for(FalloutEntry entry : FalloutConfigJSON.entries) {
				writer.beginObject();				//begin object for a single recipe
				entry.write(writer);				//serialize here
				writer.endObject();					//end recipe object
			}
			
			writer.endArray();						//end recipe array
			writer.endObject();						//final '}'
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private static List<FalloutEntry> readConfig(File config) {
		
		try {
			JsonObject json = FalloutConfigJSON.gson.fromJson(new FileReader(config), JsonObject.class);
			JsonArray recipes = json.get("entries").getAsJsonArray();
			List<FalloutEntry> conf = new ArrayList<>();
			
			for(JsonElement recipe : recipes) {
				conf.add(FalloutEntry.readEntry(recipe));
			}
			return conf;
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}

	public static class FalloutEntry {
		private Block matchesBlock = null;
		private int matchesMeta = -1;
		private Material matchesMaterial = null;
		private boolean matchesOpaque = false;

		//Block / Meta / Weight
		private Triplet<Block, Integer, Integer>[] primaryBlocks = null;
		private Triplet<Block, Integer, Integer>[] secondaryBlocks = null;
		private double primaryChance = 1.0D;
		private double minDist = 0.0D;
		private double maxDist = 100.0D;
		
		private boolean isSolid = false;
		
		@Override
		public FalloutEntry clone() {
			FalloutEntry entry = new FalloutEntry();
			entry.mB(this.matchesBlock);
			entry.mM(this.matchesMeta);
			entry.mMa(this.matchesMaterial);
			entry.mO(this.matchesOpaque);
			entry.prim(this.primaryBlocks);
			entry.sec(this.secondaryBlocks);
			entry.min(this.minDist);
			entry.max(this.maxDist);
			entry.sol(this.isSolid);
			
			return entry;
		}

		public FalloutEntry mB(Block block) { this.matchesBlock = block; return this; }
		public FalloutEntry mM(int meta) { this.matchesMeta = meta; return this; }
		public FalloutEntry mMa(Material mat) { this.matchesMaterial = mat; return this; }
		public FalloutEntry mO(boolean opaque) { this.matchesOpaque = opaque; return this; }

		
		public FalloutEntry prim(Triplet<Block, Integer, Integer>... blocks) { this.primaryBlocks = blocks; return this; }
		
		public FalloutEntry sec(Triplet<Block, Integer, Integer>... blocks) { this.secondaryBlocks = blocks; return this; }
		public FalloutEntry c(double chance) { this.primaryChance = chance; return this; }
		public FalloutEntry min(double min) { this.minDist = min; return this; }
		public FalloutEntry max(double max) { this.maxDist = max; return this; }
		public FalloutEntry sol(boolean solid) { this.isSolid = solid; return this; }
		
		public boolean eval(World world, int x, int y, int z, Block b, int meta, double dist) {
			
			if((this.matchesBlock != null && b != this.matchesBlock) || (this.matchesMaterial != null && b.getMaterial() != this.matchesMaterial)) return false;
			if(this.matchesMeta != -1 && meta != this.matchesMeta) return false;
			if(this.matchesOpaque && !b.isOpaqueCube()) return false;
			if(dist > this.maxDist || dist < this.minDist) return false;
			
			if(this.primaryChance == 1D || FalloutConfigJSON.rand.nextDouble() < this.primaryChance) {
				
				if(this.primaryBlocks == null) return false;
				
				MetaBlock block = chooseRandomOutcome(this.primaryBlocks);
				world.setBlock(x, y, z, block.block, block.meta, 3);
				return true;
				
			} else {
				
				if(this.secondaryBlocks == null) return false;
				
				MetaBlock block = chooseRandomOutcome(this.secondaryBlocks);
				world.setBlock(x, y, z, block.block, block.meta, 3);
				return true;
			}
		}
		
		private MetaBlock chooseRandomOutcome(Triplet<Block, Integer, Integer>[] blocks) {
			
			int weight = 0;
			
			for(Triplet<Block, Integer, Integer> choice : blocks) {
				weight += choice.getZ();
			}
			
			int r = FalloutConfigJSON.rand.nextInt(weight);
			
			for(Triplet<Block, Integer, Integer> choice : blocks) {
				r -= choice.getZ();
				
				if(r <= 0) {
					return new MetaBlock(choice.getX(), choice.getY());
				}
			}
			
			return new MetaBlock(blocks[0].getX(), blocks[0].getY());
		}
		
		public boolean isSolid() {
			return this.isSolid;
		}
		
		public void write(JsonWriter writer) throws IOException {
			if(this.matchesBlock != null) writer.name("matchesBlock").value(Block.blockRegistry.getNameForObject(this.matchesBlock));
			if(this.matchesMeta != -1) writer.name("matchesMeta").value(this.matchesMeta);
			if(this.matchesOpaque) writer.name("mustBeOpaque").value(true);
			
			if(this.matchesMaterial != null) {
				String matName = FalloutConfigJSON.matNames.inverse().get(this.matchesMaterial);
				if(matName != null) {
					writer.name("matchesMaterial").value(matName);
				}
			}
			if(this.isSolid) writer.name("restrictDepth").value(true);

			if(this.primaryBlocks != null) { writer.name("primarySubstitution"); FalloutEntry.writeMetaArray(writer, this.primaryBlocks); }
			if(this.secondaryBlocks != null) { writer.name("secondarySubstitutions"); FalloutEntry.writeMetaArray(writer, this.secondaryBlocks); }

			if(this.primaryChance != 1D) writer.name("chance").value(this.primaryChance);

			if(this.minDist != 0.0D) writer.name("minimumDistancePercent").value(this.minDist);
			if(this.maxDist != 100.0D) writer.name("maximumDistancePercent").value(this.maxDist);
			
		}
		
		private static FalloutEntry readEntry(JsonElement recipe) {
			FalloutEntry entry = new FalloutEntry();
			if(!recipe.isJsonObject()) return null;
			
			JsonObject obj = recipe.getAsJsonObject();

			if(obj.has("matchesBlock")) entry.mB((Block) Block.blockRegistry.getObject(obj.get("matchesBlock").getAsString()));
			if(obj.has("matchesMeta")) entry.mM(obj.get("matchesMeta").getAsInt());
			if(obj.has("mustBeOpaque")) entry.mO(obj.get("mustBeOpaque").getAsBoolean());
			if(obj.has("matchesMaterial")) entry.mMa(FalloutConfigJSON.matNames.get(obj.get("mustBeOpaque").getAsString()));
			if(obj.has("restrictDepth")) entry.sol(obj.get("restrictDepth").getAsBoolean());

			if(obj.has("primarySubstitution")) entry.prim(FalloutEntry.readMetaArray(obj.get("primarySubstitution")));
			if(obj.has("secondarySubstitutions")) entry.sec(FalloutEntry.readMetaArray(obj.get("secondarySubstitutions")));

			if(obj.has("chance")) entry.c(obj.get("chance").getAsDouble());

			if(obj.has("minimumDistancePercent")) entry.min(obj.get("minimumDistancePercent").getAsDouble());
			if(obj.has("maximumDistancePercent")) entry.max(obj.get("maximumDistancePercent").getAsDouble());
			
			return entry;
		}
		
		private static void writeMetaArray(JsonWriter writer, Triplet<Block, Integer, Integer>[] array) throws IOException {
			writer.beginArray();
			writer.setIndent("");
			
			for(Triplet<Block, Integer, Integer> meta : array) {
				writer.beginArray();
				writer.value(Block.blockRegistry.getNameForObject(meta.getX()));
				writer.value(meta.getY());
				writer.value(meta.getZ());
				writer.endArray();
			}
			
			writer.endArray();
			writer.setIndent("  ");
		}
		
		
		private static Triplet<Block, Integer, Integer>[] readMetaArray(JsonElement jsonElement) {
			
			if(!jsonElement.isJsonArray()) return null;
			
			JsonArray array = jsonElement.getAsJsonArray();
			Triplet<Block, Integer, Integer>[] metaArray = new Triplet[array.size()];
			
			for(int i = 0; i < metaArray.length; i++) {
				JsonElement metaBlock = array.get(i);
				
				if(!metaBlock.isJsonArray()) {
					throw new IllegalStateException("Could not read meta block " + metaBlock.toString());
				}
				
				JsonArray mBArray = metaBlock.getAsJsonArray();
				
				metaArray[i] = new Triplet(Block.blockRegistry.getObject(mBArray.get(0).getAsString()), mBArray.get(1).getAsInt(), mBArray.get(2).getAsInt());
			}
			
			return metaArray;
		}
	}
	
	public static HashBiMap<String, Material> matNames = HashBiMap.create();
	
	static {
		FalloutConfigJSON.matNames.put("grass", Material.grass);
		FalloutConfigJSON.matNames.put("ground", Material.ground);
		FalloutConfigJSON.matNames.put("wood", Material.wood);
		FalloutConfigJSON.matNames.put("rock", Material.rock);
		FalloutConfigJSON.matNames.put("iron", Material.iron);
		FalloutConfigJSON.matNames.put("anvil", Material.anvil);
		FalloutConfigJSON.matNames.put("water", Material.water);
		FalloutConfigJSON.matNames.put("lava", Material.lava);
		FalloutConfigJSON.matNames.put("leaves", Material.leaves);
		FalloutConfigJSON.matNames.put("plants", Material.plants);
		FalloutConfigJSON.matNames.put("vine", Material.vine);
		FalloutConfigJSON.matNames.put("sponge", Material.sponge);
		FalloutConfigJSON.matNames.put("cloth", Material.cloth);
		FalloutConfigJSON.matNames.put("fire", Material.fire);
		FalloutConfigJSON.matNames.put("sand", Material.sand);
		FalloutConfigJSON.matNames.put("circuits", Material.circuits);
		FalloutConfigJSON.matNames.put("carpet", Material.carpet);
		FalloutConfigJSON.matNames.put("redstoneLight", Material.redstoneLight);
		FalloutConfigJSON.matNames.put("tnt", Material.tnt);
		FalloutConfigJSON.matNames.put("coral", Material.coral);
		FalloutConfigJSON.matNames.put("ice", Material.ice);
		FalloutConfigJSON.matNames.put("packedIce", Material.packedIce);
		FalloutConfigJSON.matNames.put("snow", Material.snow);
		FalloutConfigJSON.matNames.put("craftedSnow", Material.craftedSnow);
		FalloutConfigJSON.matNames.put("cactus", Material.cactus);
		FalloutConfigJSON.matNames.put("clay", Material.clay);
		FalloutConfigJSON.matNames.put("gourd", Material.gourd);
		FalloutConfigJSON.matNames.put("dragonEgg", Material.dragonEgg);
		FalloutConfigJSON.matNames.put("portal", Material.portal);
		FalloutConfigJSON.matNames.put("cake", Material.cake);
		FalloutConfigJSON.matNames.put("web", Material.web);
	}
}
