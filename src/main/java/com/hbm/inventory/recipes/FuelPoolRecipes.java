package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemPWRFuel.EnumPWRFuel;

import net.minecraft.item.ItemStack;

public class FuelPoolRecipes extends SerializableRecipe {
	
	public static final HashMap<ComparableStack, ItemStack> recipes = new HashMap<>();
	public static final FuelPoolRecipes instance = new FuelPoolRecipes();

	@Override
	public void registerDefaults() {
		FuelPoolRecipes.recipes.put(new ComparableStack(ModItems.waste_natural_uranium, 1, 1), new ItemStack(ModItems.waste_natural_uranium));
		FuelPoolRecipes.recipes.put(new ComparableStack(ModItems.waste_uranium, 1, 1), new ItemStack(ModItems.waste_uranium));
		FuelPoolRecipes.recipes.put(new ComparableStack(ModItems.waste_thorium, 1, 1), new ItemStack(ModItems.waste_thorium));
		FuelPoolRecipes.recipes.put(new ComparableStack(ModItems.waste_mox, 1, 1), new ItemStack(ModItems.waste_mox));
		FuelPoolRecipes.recipes.put(new ComparableStack(ModItems.waste_plutonium, 1, 1), new ItemStack(ModItems.waste_plutonium));
		FuelPoolRecipes.recipes.put(new ComparableStack(ModItems.waste_u233, 1, 1), new ItemStack(ModItems.waste_u233));
		FuelPoolRecipes.recipes.put(new ComparableStack(ModItems.waste_u235, 1, 1), new ItemStack(ModItems.waste_u235));
		FuelPoolRecipes.recipes.put(new ComparableStack(ModItems.waste_schrabidium, 1, 1), new ItemStack(ModItems.waste_schrabidium));
		FuelPoolRecipes.recipes.put(new ComparableStack(ModItems.waste_zfb_mox, 1, 1), new ItemStack(ModItems.waste_zfb_mox));
		FuelPoolRecipes.recipes.put(new ComparableStack(ModItems.waste_plate_u233, 1, 1), new ItemStack(ModItems.waste_plate_u233));
		FuelPoolRecipes.recipes.put(new ComparableStack(ModItems.waste_plate_u235, 1, 1), new ItemStack(ModItems.waste_plate_u235));
		FuelPoolRecipes.recipes.put(new ComparableStack(ModItems.waste_plate_mox, 1, 1), new ItemStack(ModItems.waste_plate_mox));
		FuelPoolRecipes.recipes.put(new ComparableStack(ModItems.waste_plate_pu239, 1, 1), new ItemStack(ModItems.waste_plate_pu239));
		FuelPoolRecipes.recipes.put(new ComparableStack(ModItems.waste_plate_sa326, 1, 1), new ItemStack(ModItems.waste_plate_sa326));
		FuelPoolRecipes.recipes.put(new ComparableStack(ModItems.waste_plate_ra226be, 1, 1), new ItemStack(ModItems.waste_plate_ra226be));
		FuelPoolRecipes.recipes.put(new ComparableStack(ModItems.waste_plate_pu238be, 1, 1), new ItemStack(ModItems.waste_plate_pu238be));
		
		for(EnumPWRFuel pwr : EnumPWRFuel.values()) FuelPoolRecipes.recipes.put(new ComparableStack(ModItems.pwr_fuel_hot, 1, pwr.ordinal()), new ItemStack(ModItems.pwr_fuel_depleted, 1, pwr.ordinal()));
	}

	@Override
	public String getFileName() {
		return "hbmFuelpool.json";
	}

	@Override
	public Object getRecipeObject() {
		return FuelPoolRecipes.recipes;
	}

	@Override
	public void deleteRecipes() {
		FuelPoolRecipes.recipes.clear();
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonElement input = ((JsonObject)recipe).get("input");
		JsonElement output = ((JsonObject)recipe).get("output");
		ItemStack in = readItemStack((JsonArray) input);
		ItemStack out = readItemStack((JsonArray) output);
		FuelPoolRecipes.recipes.put(new ComparableStack(in), out);
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<ComparableStack, ItemStack> entry = (Entry<ComparableStack, ItemStack>) recipe;
		ItemStack in = entry.getKey().toStack();
		ItemStack out = entry.getValue();

		writer.name("input");
		writeItemStack(in, writer);
		writer.name("output");
		writeItemStack(out, writer);
	}
}
