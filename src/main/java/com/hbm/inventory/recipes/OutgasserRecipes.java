package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ItemEnums.EnumTarType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.util.Tuple.Pair;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class OutgasserRecipes extends SerializableRecipe {
	
	public static Map<AStack, Pair<ItemStack, FluidStack>> recipes = new HashMap<>();

	@Override
	public void registerDefaults() {

		/* lithium to tritium */
		OutgasserRecipes.recipes.put(new OreDictStack(OreDictManager.LI.block()),		new Pair<>(null, new FluidStack(Fluids.TRITIUM, 10_000)));
		OutgasserRecipes.recipes.put(new OreDictStack(OreDictManager.LI.ingot()),		new Pair<>(null, new FluidStack(Fluids.TRITIUM, 1_000)));
		OutgasserRecipes.recipes.put(new OreDictStack(OreDictManager.LI.dust()),		new Pair<>(null, new FluidStack(Fluids.TRITIUM, 1_000)));
		OutgasserRecipes.recipes.put(new OreDictStack(OreDictManager.LI.dustTiny()),	new Pair<>(null, new FluidStack(Fluids.TRITIUM, 100)));

		/* gold to gold-198 */
		OutgasserRecipes.recipes.put(new OreDictStack(OreDictManager.GOLD.ingot()),		new Pair<>(new ItemStack(ModItems.ingot_au198), null));
		OutgasserRecipes.recipes.put(new OreDictStack(OreDictManager.GOLD.nugget()),	new Pair<>(new ItemStack(ModItems.nugget_au198), null));
		OutgasserRecipes.recipes.put(new OreDictStack(OreDictManager.GOLD.dust()),		new Pair<>(new ItemStack(ModItems.powder_au198), null));

		/* thorium to thorium fuel */
		OutgasserRecipes.recipes.put(new OreDictStack(OreDictManager.TH232.ingot()),	new Pair<>(new ItemStack(ModItems.ingot_thorium_fuel), null));
		OutgasserRecipes.recipes.put(new OreDictStack(OreDictManager.TH232.nugget()),	new Pair<>(new ItemStack(ModItems.nugget_thorium_fuel), null));
		OutgasserRecipes.recipes.put(new OreDictStack(OreDictManager.TH232.billet()),	new Pair<>(new ItemStack(ModItems.billet_thorium_fuel), null));

		/* mushrooms to glowing mushrooms */
		OutgasserRecipes.recipes.put(new ComparableStack(Blocks.brown_mushroom),	new Pair<>(new ItemStack(ModBlocks.mush), null));
		OutgasserRecipes.recipes.put(new ComparableStack(Blocks.red_mushroom),	new Pair<>(new ItemStack(ModBlocks.mush), null));
		OutgasserRecipes.recipes.put(new ComparableStack(Items.mushroom_stew),	new Pair<>(new ItemStack(ModItems.glowing_stew), null));

		OutgasserRecipes.recipes.put(new OreDictStack(OreDictManager.COAL.gem()),		new Pair<>(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.COAL, 1), new FluidStack(Fluids.SYNGAS, 50)));
		OutgasserRecipes.recipes.put(new OreDictStack(OreDictManager.COAL.dust()),		new Pair<>(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.COAL, 1), new FluidStack(Fluids.SYNGAS, 50)));
		OutgasserRecipes.recipes.put(new OreDictStack(OreDictManager.COAL.block()),		new Pair<>(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.COAL, 9), new FluidStack(Fluids.SYNGAS, 500)));
		
		OutgasserRecipes.recipes.put(new OreDictStack(OreDictManager.PVC.ingot()),		new Pair<>(new ItemStack(ModItems.ingot_c4), new FluidStack(Fluids.COLLOID, 250)));

		OutgasserRecipes.recipes.put(new ComparableStack(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.COAL)),	new Pair<>(null, new FluidStack(Fluids.COALOIL, 100)));
		OutgasserRecipes.recipes.put(new ComparableStack(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.WAX)),	new Pair<>(null, new FluidStack(Fluids.RADIOSOLVENT, 100)));
	}
	
	public static Pair<ItemStack, FluidStack> getOutput(ItemStack input) {
		
		ComparableStack comp = new ComparableStack(input).makeSingular();
		
		if(OutgasserRecipes.recipes.containsKey(comp)) {
			return OutgasserRecipes.recipes.get(comp);
		}
		
		String[] dictKeys = comp.getDictKeys();
		
		for(String key : dictKeys) {
			OreDictStack dict = new OreDictStack(key);
			if(OutgasserRecipes.recipes.containsKey(dict)) {
				return OutgasserRecipes.recipes.get(dict);
			}
		}
		
		return null;
	}

	public static HashMap getRecipes() {
		
		HashMap<Object, Object[]> recipes = new HashMap<>();
		
		for(Entry<AStack, Pair<ItemStack, FluidStack>> entry : OutgasserRecipes.recipes.entrySet()) {
			
			AStack input = entry.getKey();
			ItemStack solidOutput = entry.getValue().getKey();
			FluidStack fluidOutput = entry.getValue().getValue();

			if(solidOutput != null && fluidOutput != null) recipes.put(input, new Object[] {solidOutput, ItemFluidIcon.make(fluidOutput)});
			if(solidOutput != null && fluidOutput == null) recipes.put(input, new Object[] {solidOutput});
			if(solidOutput == null && fluidOutput != null) recipes.put(input, new Object[] {ItemFluidIcon.make(fluidOutput)});
		}
		
		return recipes;
	}

	@Override
	public String getFileName() {
		return "hbmIrradiation.json";
	}

	@Override
	public Object getRecipeObject() {
		return OutgasserRecipes.recipes;
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;
		
		AStack input = readAStack(obj.get("input").getAsJsonArray());
		ItemStack solidOutput = null;
		FluidStack fluidOutput = null;
		
		if(obj.has("solidOutput")) {
			solidOutput = readItemStack(obj.get("solidOutput").getAsJsonArray());
		}
		
		if(obj.has("fluidOutput")) {
			fluidOutput = readFluidStack(obj.get("fluidOutput").getAsJsonArray());
		}
		
		if(solidOutput != null || fluidOutput != null) {
			OutgasserRecipes.recipes.put(input, new Pair<>(solidOutput, fluidOutput));
		}
	}

	
	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<AStack, Pair<ItemStack, FluidStack>> rec = (Entry<AStack, Pair<ItemStack, FluidStack>>) recipe;
		
		writer.name("input");
		writeAStack(rec.getKey(), writer);
		
		if(rec.getValue().getKey() != null) {
			writer.name("solidOutput");
			writeItemStack(rec.getValue().getKey(), writer);
		}
		
		if(rec.getValue().getValue() != null) {
			writer.name("fluidOutput");
			writeFluidStack(rec.getValue().getValue(), writer);
		}
	}

	@Override
	public void deleteRecipes() {
		OutgasserRecipes.recipes.clear();
	}
}
