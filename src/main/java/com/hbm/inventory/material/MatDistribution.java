package com.hbm.inventory.material;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.BlockEnums.EnumStoneType;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ItemEnums.EnumAshType;
import com.hbm.items.ModItems;
import com.hbm.util.Compat;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MatDistribution extends SerializableRecipe {

	@Override
	public void registerDefaults() {
		//vanilla crap
		MatDistribution.registerOre("stone", Mats.MAT_STONE, MaterialShapes.BLOCK.q(1));
		MatDistribution.registerOre("cobblestone", Mats.MAT_STONE, MaterialShapes.BLOCK.q(1));
		MatDistribution.registerEntry(Blocks.obsidian, Mats.MAT_OBSIDIAN, MaterialShapes.BLOCK.q(1));
		MatDistribution.registerEntry(Blocks.rail, Mats.MAT_IRON, MaterialShapes.INGOT.q(6, 16));
		MatDistribution.registerEntry(Blocks.golden_rail, Mats.MAT_GOLD, MaterialShapes.INGOT.q(6, 6), Mats.MAT_REDSTONE, MaterialShapes.DUST.q(1, 6));
		MatDistribution.registerEntry(Blocks.detector_rail, Mats.MAT_IRON, MaterialShapes.INGOT.q(6, 6), Mats.MAT_REDSTONE, MaterialShapes.DUST.q(1, 6));
		MatDistribution.registerEntry(Items.minecart, Mats.MAT_IRON, MaterialShapes.INGOT.q(5));
		
		//castables
		MatDistribution.registerEntry(ModItems.blade_titanium,			Mats.MAT_TITANIUM,		MaterialShapes.INGOT.q(2));
		MatDistribution.registerEntry(ModItems.blade_tungsten,			Mats.MAT_TUNGSTEN,		MaterialShapes.INGOT.q(2));
		MatDistribution.registerEntry(ModItems.blades_steel,			Mats.MAT_STEEL,			MaterialShapes.INGOT.q(4));
		MatDistribution.registerEntry(ModItems.blades_titanium,			Mats.MAT_TITANIUM, 		MaterialShapes.INGOT.q(4));
		MatDistribution.registerEntry(ModItems.blades_advanced_alloy,	Mats.MAT_ALLOY,			MaterialShapes.INGOT.q(4));
		MatDistribution.registerEntry(ModItems.stamp_stone_flat,		Mats.MAT_STONE,			MaterialShapes.INGOT.q(3));
		MatDistribution.registerEntry(ModItems.stamp_iron_flat,			Mats.MAT_IRON,			MaterialShapes.INGOT.q(3));
		MatDistribution.registerEntry(ModItems.stamp_steel_flat,		Mats.MAT_STEEL,			MaterialShapes.INGOT.q(3));
		MatDistribution.registerEntry(ModItems.stamp_titanium_flat,		Mats.MAT_TITANIUM,		MaterialShapes.INGOT.q(3));
		MatDistribution.registerEntry(ModItems.stamp_obsidian_flat,		Mats.MAT_OBSIDIAN,		MaterialShapes.INGOT.q(3));
		MatDistribution.registerEntry(ModItems.hull_small_steel,		Mats.MAT_STEEL,			MaterialShapes.INGOT.q(2));
		MatDistribution.registerEntry(ModItems.hull_small_aluminium,	Mats.MAT_ALUMINIUM,		MaterialShapes.INGOT.q(2));
		MatDistribution.registerEntry(ModItems.hull_big_steel,			Mats.MAT_STEEL,			MaterialShapes.INGOT.q(6));
		MatDistribution.registerEntry(ModItems.hull_big_aluminium,		Mats.MAT_ALUMINIUM,		MaterialShapes.INGOT.q(6));
		MatDistribution.registerEntry(ModItems.hull_big_titanium,		Mats.MAT_TITANIUM,		MaterialShapes.INGOT.q(6));
		MatDistribution.registerEntry(ModItems.pipes_steel,				Mats.MAT_STEEL,			MaterialShapes.BLOCK.q(3));

		//actual ores
		if(!Compat.isModLoaded(Compat.MOD_GT6)) {
			MatDistribution.registerOre(OreDictManager.IRON.ore(), Mats.MAT_IRON, MaterialShapes.INGOT.q(2), Mats.MAT_TITANIUM, MaterialShapes.NUGGET.q(3), Mats.MAT_STONE, MaterialShapes.QUART.q(1));
			MatDistribution.registerOre(OreDictManager.TI.ore(), Mats.MAT_TITANIUM, MaterialShapes.INGOT.q(2), Mats.MAT_IRON, MaterialShapes.NUGGET.q(3), Mats.MAT_STONE, MaterialShapes.QUART.q(1));
			MatDistribution.registerOre(OreDictManager.W.ore(), Mats.MAT_TUNGSTEN, MaterialShapes.INGOT.q(2), Mats.MAT_STONE, MaterialShapes.QUART.q(1));
			MatDistribution.registerOre(OreDictManager.AL.ore(), Mats.MAT_ALUMINIUM, MaterialShapes.INGOT.q(2), Mats.MAT_STONE, MaterialShapes.QUART.q(1));
		}
		
		MatDistribution.registerOre(OreDictManager.COAL.ore(), Mats.MAT_CARBON, MaterialShapes.GEM.q(3), Mats.MAT_STONE, MaterialShapes.QUART.q(1));
		MatDistribution.registerOre(OreDictManager.GOLD.ore(), Mats.MAT_GOLD, MaterialShapes.INGOT.q(2), Mats.MAT_LEAD, MaterialShapes.NUGGET.q(3), Mats.MAT_STONE, MaterialShapes.QUART.q(1));
		MatDistribution.registerOre(OreDictManager.U.ore(), Mats.MAT_URANIUM, MaterialShapes.INGOT.q(2), Mats.MAT_LEAD, MaterialShapes.NUGGET.q(3), Mats.MAT_STONE, MaterialShapes.QUART.q(1));
		MatDistribution.registerOre(OreDictManager.TH232.ore(), Mats.MAT_THORIUM, MaterialShapes.INGOT.q(2), Mats.MAT_URANIUM, MaterialShapes.NUGGET.q(3), Mats.MAT_STONE, MaterialShapes.QUART.q(1));
		MatDistribution.registerOre(OreDictManager.CU.ore(), Mats.MAT_COPPER, MaterialShapes.INGOT.q(2), Mats.MAT_STONE, MaterialShapes.QUART.q(1));
		MatDistribution.registerOre(OreDictManager.PB.ore(), Mats.MAT_LEAD, MaterialShapes.INGOT.q(2), Mats.MAT_GOLD, MaterialShapes.NUGGET.q(1), Mats.MAT_STONE, MaterialShapes.QUART.q(1));
		MatDistribution.registerOre(OreDictManager.BE.ore(), Mats.MAT_BERYLLIUM, MaterialShapes.INGOT.q(2), Mats.MAT_STONE, MaterialShapes.QUART.q(1));
		MatDistribution.registerOre(OreDictManager.CO.ore(), Mats.MAT_COBALT, MaterialShapes.INGOT.q(1), Mats.MAT_STONE, MaterialShapes.QUART.q(1));
		MatDistribution.registerOre(OreDictManager.REDSTONE.ore(), Mats.MAT_REDSTONE, MaterialShapes.INGOT.q(4), Mats.MAT_STONE, MaterialShapes.QUART.q(1));

		MatDistribution.registerOre(OreDictManager.HEMATITE.ore(), Mats.MAT_HEMATITE, MaterialShapes.INGOT.q(4));
		MatDistribution.registerOre(OreDictManager.MALACHITE.ore(), Mats.MAT_MALACHITE, MaterialShapes.INGOT.q(4));
		
		MatDistribution.registerEntry(DictFrame.fromOne(ModBlocks.stone_resource, EnumStoneType.LIMESTONE), Mats.MAT_FLUX, MaterialShapes.DUST.q(10));
		MatDistribution.registerEntry(ModItems.powder_flux, Mats.MAT_FLUX, MaterialShapes.DUST.q(1));
		MatDistribution.registerEntry(new ItemStack(Items.coal, 1, 1), Mats.MAT_CARBON, MaterialShapes.NUGGET.q(3));

		MatDistribution.registerEntry(DictFrame.fromOne(ModItems.powder_ash, EnumAshType.WOOD), Mats.MAT_CARBON, MaterialShapes.NUGGET.q(1));
		MatDistribution.registerEntry(DictFrame.fromOne(ModItems.powder_ash, EnumAshType.COAL), Mats.MAT_CARBON, MaterialShapes.NUGGET.q(2));
		MatDistribution.registerEntry(DictFrame.fromOne(ModItems.powder_ash, EnumAshType.MISC), Mats.MAT_CARBON, MaterialShapes.NUGGET.q(1));
	}
	
	public static void registerEntry(Object key, Object... matDef) {
		ComparableStack comp = null;

		if(key instanceof Item) comp = new ComparableStack((Item) key);
		if(key instanceof Block) comp = new ComparableStack((Block) key);
		if(key instanceof ItemStack) comp = new ComparableStack((ItemStack) key);
		
		if((comp == null) || (matDef.length % 2 == 1)) return;
		
		List<MaterialStack> stacks = new ArrayList<>();
		
		for(int i = 0; i < matDef.length; i += 2) {
			stacks.add(new MaterialStack((NTMMaterial) matDef[i], (int) matDef[i + 1]));
		}
		
		if(stacks.isEmpty()) return;
		
		Mats.materialEntries.put(comp, stacks);
	}
	
	public static void registerOre(String key, Object... matDef) {
		if(matDef.length % 2 == 1) return;
		
		List<MaterialStack> stacks = new ArrayList<>();
		
		for(int i = 0; i < matDef.length; i += 2) {
			stacks.add(new MaterialStack((NTMMaterial) matDef[i], (int) matDef[i + 1]));
		}
		
		if(stacks.isEmpty()) return;
		
		Mats.materialOreEntries.put(key, stacks);
	}

	@Override
	public String getFileName() {
		return "hbmCrucibleSmelting.json";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object getRecipeObject() {
		List entries = new ArrayList<>();
		entries.addAll(Mats.materialEntries.entrySet());
		entries.addAll(Mats.materialOreEntries.entrySet());
		return entries;
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;
		AStack input = readAStack(obj.get("input").getAsJsonArray());
		List<MaterialStack> materials = new ArrayList<>();
		JsonArray output = obj.get("output").getAsJsonArray();
		for(int i = 0; i < output.size(); i++) {
			JsonArray entry = output.get(i).getAsJsonArray();
			String mat = entry.get(0).getAsString();
			int amount = entry.get(1).getAsInt();
			materials.add(new MaterialStack(Mats.matByName.get(mat), amount));
		}
		if(input instanceof ComparableStack) {
			Mats.materialEntries.put((ComparableStack) input, materials);
		} else if(input instanceof OreDictStack) {
			Mats.materialOreEntries.put(((OreDictStack) input).name, materials);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		AStack toSmelt = null;
		Entry entry = (Entry) recipe;
		List<MaterialStack> materials = (List<MaterialStack>) entry.getValue();
		if(entry.getKey() instanceof String) {
			toSmelt = new OreDictStack((String) entry.getKey());
		} else if(entry.getKey() instanceof ComparableStack) {
			toSmelt = (ComparableStack) entry.getKey();
		}
		if(toSmelt == null) return;
		writer.name("input");
		writeAStack(toSmelt, writer);
		writer.name("output").beginArray();
		writer.setIndent("");
		for(MaterialStack stack : materials) {
			writer.beginArray();
			writer.value(stack.material.names[0]).value(stack.amount);
			writer.endArray();
		}
		writer.endArray();
		writer.setIndent("  ");
	}

	@Override
	public void deleteRecipes() {
		Mats.materialEntries.clear();
		Mats.materialOreEntries.clear();
	}
	
	@Override
	public String getComment() {
		return 
			"Defines a set of items that can be smelted. Smelting generated from the ore dictionary (prefix + material) is auto-generated and cannot be\n" +
			"changed. This config only changes fixed items (like recycling for certain metallic objects) and ores (with variable byproducts).\n" +
			"Amounts used are in quanta (1 quantum is 1/72 of an ingot or 1/8 of a nugget). Material names are the ore dict suffixes, case-sensitive.";
	}
}
