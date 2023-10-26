package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ItemAmmoEnums.Ammo357Magnum;
import com.hbm.items.ItemAmmoEnums.Ammo556mm;
import com.hbm.items.ItemAmmoEnums.AmmoLunaticSniper;
import com.hbm.items.ItemEnums.EnumBriquetteType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemStamp;
import com.hbm.items.machine.ItemStamp.StampType;
import com.hbm.util.Tuple.Pair;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PressRecipes extends SerializableRecipe {

	public static HashMap<Pair<AStack, StampType>, ItemStack> recipes = new HashMap();
	
	public static ItemStack getOutput(ItemStack ingredient, ItemStack stamp) {
		
		if(ingredient == null || stamp == null || !(stamp.getItem() instanceof ItemStamp))
			return null;
		
		StampType type = ((ItemStamp) stamp.getItem()).type;
		
		for(Entry<Pair<AStack, StampType>, ItemStack> recipe : PressRecipes.recipes.entrySet()) {
			
			if(recipe.getKey().getValue() == type && recipe.getKey().getKey().matchesRecipe(ingredient, true))
				return recipe.getValue();
		}
		
		return null;
	}

	@Override
	public void registerDefaults() {

		PressRecipes.makeRecipe(StampType.FLAT, new OreDictStack(OreDictManager.NETHERQUARTZ.dust()),					Items.quartz);
		PressRecipes.makeRecipe(StampType.FLAT, new OreDictStack(OreDictManager.LAPIS.dust()),							new ItemStack(Items.dye, 1, 4));
		PressRecipes.makeRecipe(StampType.FLAT, new OreDictStack(OreDictManager.DIAMOND.dust()),						Items.diamond);
		PressRecipes.makeRecipe(StampType.FLAT, new OreDictStack(OreDictManager.EMERALD.dust()),						Items.emerald);
		PressRecipes.makeRecipe(StampType.FLAT, new ComparableStack(ModItems.pellet_coal),				Items.diamond);
		PressRecipes.makeRecipe(StampType.FLAT, new ComparableStack(ModItems.biomass),					ModItems.biomass_compressed);
		PressRecipes.makeRecipe(StampType.FLAT, new OreDictStack(OreDictManager.ANY_COKE.gem()),						ModItems.ingot_graphite);
		PressRecipes.makeRecipe(StampType.FLAT, new ComparableStack(ModItems.meteorite_sword_reforged),	ModItems.meteorite_sword_hardened);

		PressRecipes.makeRecipe(StampType.FLAT, new OreDictStack(OreDictManager.COAL.dust()),							DictFrame.fromOne(ModItems.briquette, EnumBriquetteType.COAL));
		PressRecipes.makeRecipe(StampType.FLAT, new OreDictStack(OreDictManager.LIGNITE.dust()),						DictFrame.fromOne(ModItems.briquette, EnumBriquetteType.LIGNITE));
		PressRecipes.makeRecipe(StampType.FLAT, new ComparableStack(ModItems.powder_sawdust),			DictFrame.fromOne(ModItems.briquette, EnumBriquetteType.WOOD));

		PressRecipes.makeRecipe(StampType.PLATE, new OreDictStack(OreDictManager.IRON.ingot()),		ModItems.plate_iron);
		PressRecipes.makeRecipe(StampType.PLATE, new OreDictStack(OreDictManager.GOLD.ingot()),		ModItems.plate_gold);
		PressRecipes.makeRecipe(StampType.PLATE, new OreDictStack(OreDictManager.TI.ingot()),		ModItems.plate_titanium);
		PressRecipes.makeRecipe(StampType.PLATE, new OreDictStack(OreDictManager.AL.ingot()),		ModItems.plate_aluminium);
		PressRecipes.makeRecipe(StampType.PLATE, new OreDictStack(OreDictManager.STEEL.ingot()),	ModItems.plate_steel);
		PressRecipes.makeRecipe(StampType.PLATE, new OreDictStack(OreDictManager.PB.ingot()),		ModItems.plate_lead);
		PressRecipes.makeRecipe(StampType.PLATE, new OreDictStack(OreDictManager.CU.ingot()),		ModItems.plate_copper);
		PressRecipes.makeRecipe(StampType.PLATE, new OreDictStack(OreDictManager.ALLOY.ingot()),	ModItems.plate_advanced_alloy);
		PressRecipes.makeRecipe(StampType.PLATE, new OreDictStack(OreDictManager.SA326.ingot()),	ModItems.plate_schrabidium);
		PressRecipes.makeRecipe(StampType.PLATE, new OreDictStack(OreDictManager.CMB.ingot()),		ModItems.plate_combine_steel);
		PressRecipes.makeRecipe(StampType.PLATE, new OreDictStack(OreDictManager.BIGMT.ingot()),	ModItems.plate_saturnite);

		PressRecipes.makeRecipe(StampType.WIRE, new OreDictStack(OreDictManager.AL.ingot()),		new ItemStack(ModItems.wire_aluminium, 8));
		PressRecipes.makeRecipe(StampType.WIRE, new OreDictStack(OreDictManager.CU.ingot()),		new ItemStack(ModItems.wire_copper, 8));
		PressRecipes.makeRecipe(StampType.WIRE, new OreDictStack(OreDictManager.W.ingot()),			new ItemStack(ModItems.wire_tungsten, 8));
		PressRecipes.makeRecipe(StampType.WIRE, new OreDictStack(OreDictManager.MINGRADE.ingot()),	new ItemStack(ModItems.wire_red_copper, 8));
		PressRecipes.makeRecipe(StampType.WIRE, new OreDictStack(OreDictManager.GOLD.ingot()),		new ItemStack(ModItems.wire_gold, 8));
		PressRecipes.makeRecipe(StampType.WIRE, new OreDictStack(OreDictManager.SA326.ingot()),		new ItemStack(ModItems.wire_schrabidium, 8));
		PressRecipes.makeRecipe(StampType.WIRE, new OreDictStack(OreDictManager.ALLOY.ingot()),		new ItemStack(ModItems.wire_advanced_alloy, 8));
		PressRecipes.makeRecipe(StampType.WIRE, new OreDictStack(OreDictManager.MAGTUNG.ingot()),	new ItemStack(ModItems.wire_magnetized_tungsten, 8));

		PressRecipes.makeRecipe(StampType.CIRCUIT, new ComparableStack(ModItems.circuit_raw),			ModItems.circuit_aluminium);
		PressRecipes.makeRecipe(StampType.CIRCUIT, new ComparableStack(ModItems.circuit_bismuth_raw),	ModItems.circuit_bismuth);
		PressRecipes.makeRecipe(StampType.CIRCUIT, new ComparableStack(ModItems.circuit_arsenic_raw),	ModItems.circuit_arsenic);
		PressRecipes.makeRecipe(StampType.CIRCUIT, new ComparableStack(ModItems.circuit_tantalium_raw),	ModItems.circuit_tantalium);

		PressRecipes.makeRecipe(StampType.C357, new ComparableStack(ModItems.assembly_iron),			ModItems.ammo_357.stackFromEnum(24, Ammo357Magnum.IRON));
		PressRecipes.makeRecipe(StampType.C357, new ComparableStack(ModItems.assembly_steel),		ModItems.ammo_357.stackFromEnum(24, Ammo357Magnum.LEAD));
		PressRecipes.makeRecipe(StampType.C357, new ComparableStack(ModItems.assembly_lead),			ModItems.ammo_357.stackFromEnum(24, Ammo357Magnum.NUCLEAR));
		PressRecipes.makeRecipe(StampType.C357, new ComparableStack(ModItems.assembly_gold),			ModItems.ammo_357.stackFromEnum(24, Ammo357Magnum.GOLD));
		PressRecipes.makeRecipe(StampType.C357, new ComparableStack(ModItems.assembly_schrabidium),	ModItems.ammo_357.stackFromEnum(6, Ammo357Magnum.SCHRABIDIUM));
		PressRecipes.makeRecipe(StampType.C357, new ComparableStack(ModItems.assembly_nightmare),	ModItems.ammo_357.stackFromEnum(24, Ammo357Magnum.NIGHTMARE1));
		PressRecipes.makeRecipe(StampType.C357, new ComparableStack(ModItems.assembly_desh),			ModItems.ammo_357.stackFromEnum(24, Ammo357Magnum.DESH));
		PressRecipes.makeRecipe(StampType.C357, new OreDictStack(OreDictManager.STEEL.ingot()),						ModItems.ammo_357.stackFromEnum(24, Ammo357Magnum.STEEL));
		
		PressRecipes.makeRecipe(StampType.C44, new ComparableStack(ModItems.assembly_nopip),		new ItemStack(ModItems.ammo_44, 24));
		PressRecipes.makeRecipe(StampType.C44, new ComparableStack(ModItems.assembly_45), 		new ItemStack(ModItems.ammo_45, 32));

		PressRecipes.makeRecipe(StampType.C9, new ComparableStack(ModItems.assembly_smg),		new ItemStack(ModItems.ammo_9mm, 32));
		PressRecipes.makeRecipe(StampType.C9, new ComparableStack(ModItems.assembly_uzi),		new ItemStack(ModItems.ammo_22lr, 32));
		PressRecipes.makeRecipe(StampType.C9, new OreDictStack(OreDictManager.GOLD.ingot()),					ModItems.ammo_556.stackFromEnum(32, Ammo556mm.GOLD));
		PressRecipes.makeRecipe(StampType.C9, new ComparableStack(ModItems.assembly_lacunae),	new ItemStack(ModItems.ammo_5mm, 64));
		PressRecipes.makeRecipe(StampType.C9, new ComparableStack(ModItems.assembly_556),		new ItemStack(ModItems.ammo_556, 32));

		PressRecipes.makeRecipe(StampType.C50, new ComparableStack(ModItems.assembly_calamity),		new ItemStack(ModItems.ammo_50bmg, 12));
		PressRecipes.makeRecipe(StampType.C50, new ComparableStack(ModItems.assembly_actionexpress),	new ItemStack(ModItems.ammo_50ae, 12));
		PressRecipes.makeRecipe(StampType.C50, new ComparableStack(ModItems.assembly_luna), 			ModItems.ammo_luna_sniper.stackFromEnum(4, AmmoLunaticSniper.SABOT));
		PressRecipes.makeRecipe(StampType.C50, new ComparableStack(ModItems.assembly_762), 			new ItemStack(ModItems.ammo_762, 32));
	}

	public static void makeRecipe(StampType type, AStack in, Item out) {
		PressRecipes.recipes.put(new Pair<>(in, type),  new ItemStack(out));
	}
	public static void makeRecipe(StampType type, AStack in, ItemStack out) {
		PressRecipes.recipes.put(new Pair<>(in, type),  out);
	}

	@Override
	public String getFileName() {
		return "hbmPress.json";
	}

	@Override
	public Object getRecipeObject() {
		return PressRecipes.recipes;
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;
		
		AStack input = readAStack(obj.get("input").getAsJsonArray());
		StampType stamp = StampType.valueOf(obj.get("stamp").getAsString().toUpperCase());
		ItemStack output = readItemStack(obj.get("output").getAsJsonArray());
		
		if(stamp != null) {
			PressRecipes.makeRecipe(stamp, input, output);
		}
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<Pair<AStack, StampType>, ItemStack> entry = (Entry<Pair<AStack, StampType>, ItemStack>) recipe;
		
		writer.name("input");
		writeAStack(entry.getKey().getKey(), writer);
		writer.name("stamp").value(entry.getKey().getValue().name().toLowerCase(Locale.US));
		writer.name("output");
		writeItemStack(entry.getValue(), writer);
	}

	@Override
	public void deleteRecipes() {
		PressRecipes.recipes.clear();
	}
}
