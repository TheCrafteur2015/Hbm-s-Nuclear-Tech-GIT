package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.util.Tuple.Pair;

import net.minecraft.item.ItemStack;

public class FractionRecipes extends SerializableRecipe {

	public static final int heavy_frac_bitu = 30;
	public static final int heavy_frac_smear = 70;
	public static final int smear_frac_heat = 60;
	public static final int smear_frac_lube = 40;
	public static final int napht_frac_heat = 40;
	public static final int napht_frac_diesel = 60;
	public static final int light_frac_diesel = 40;
	public static final int light_frac_kero = 60;

	public static final int ncrack_frac_heat = 30;
	public static final int ncrack_frac_diesel = 70;
	public static final int lcrack_frac_kero = 70;
	public static final int lcrack_frac_petro = 30;
	public static final int coal_frac_coalgas = 30;
	public static final int coal_frac_oil = 70;
	public static final int creo_frac_coaloil = 10;
	public static final int creo_frac_bitu = 90;
	public static final int reform_frac_arom = 40;
	public static final int reform_frac_xyle = 60;
	public static final int hvac_frac_smear = 40;
	public static final int hvac_frac_heat = 60;
	public static final int lvac_frac_kero = 70;
	public static final int lvac_frac_gas = 30;
	
	private static Map<FluidType, Pair<FluidStack, FluidStack>> fractions = new HashMap<>();
	
	@Override
	public void registerDefaults() {
		FractionRecipes.fractions.put(Fluids.HEAVYOIL,			new Pair<>(new FluidStack(Fluids.BITUMEN,			FractionRecipes.heavy_frac_bitu),	new FluidStack(Fluids.SMEAR,				FractionRecipes.heavy_frac_smear)));
		FractionRecipes.fractions.put(Fluids.SMEAR,				new Pair<>(new FluidStack(Fluids.HEATINGOIL,		FractionRecipes.smear_frac_heat),	new FluidStack(Fluids.LUBRICANT,			FractionRecipes.smear_frac_lube)));
		FractionRecipes.fractions.put(Fluids.NAPHTHA,			new Pair<>(new FluidStack(Fluids.HEATINGOIL,		FractionRecipes.napht_frac_heat),	new FluidStack(Fluids.DIESEL,				FractionRecipes.napht_frac_diesel)));
		FractionRecipes.fractions.put(Fluids.NAPHTHA_CRACK,		new Pair<>(new FluidStack(Fluids.HEATINGOIL,		FractionRecipes.ncrack_frac_heat),	new FluidStack(Fluids.DIESEL_CRACK,			FractionRecipes.ncrack_frac_diesel)));
		FractionRecipes.fractions.put(Fluids.LIGHTOIL,			new Pair<>(new FluidStack(Fluids.DIESEL,			FractionRecipes.light_frac_diesel),	new FluidStack(Fluids.KEROSENE,				FractionRecipes.light_frac_kero)));
		FractionRecipes.fractions.put(Fluids.LIGHTOIL_CRACK,	new Pair<>(new FluidStack(Fluids.KEROSENE,		FractionRecipes.lcrack_frac_kero),	new FluidStack(Fluids.PETROLEUM,			FractionRecipes.lcrack_frac_petro)));
		FractionRecipes.fractions.put(Fluids.COALOIL,			new Pair<>(new FluidStack(Fluids.COALGAS,			FractionRecipes.coal_frac_coalgas),	new FluidStack(Fluids.OIL,					FractionRecipes.coal_frac_oil)));
		FractionRecipes.fractions.put(Fluids.COALCREOSOTE,		new Pair<>(new FluidStack(Fluids.COALOIL,			FractionRecipes.creo_frac_coaloil),	new FluidStack(Fluids.BITUMEN,				FractionRecipes.creo_frac_bitu)));
		FractionRecipes.fractions.put(Fluids.HEAVYOIL_VACUUM,	new Pair<>(new FluidStack(Fluids.SMEAR,			FractionRecipes.hvac_frac_smear),	new FluidStack(Fluids.HEATINGOIL_VACUUM,	FractionRecipes.hvac_frac_heat)));
		FractionRecipes.fractions.put(Fluids.REFORMATE,			new Pair<>(new FluidStack(Fluids.AROMATICS,		FractionRecipes.reform_frac_arom),	new FluidStack(Fluids.XYLENE,				FractionRecipes.reform_frac_xyle)));
		FractionRecipes.fractions.put(Fluids.LIGHTOIL_VACUUM,	new Pair<>(new FluidStack(Fluids.KEROSENE,		FractionRecipes.lvac_frac_kero),	new FluidStack(Fluids.REFORMGAS,			FractionRecipes.lvac_frac_gas)));
		FractionRecipes.fractions.put(Fluids.SOURGAS,			new Pair<>(new FluidStack(Fluids.GAS,				30),				new FluidStack(Fluids.PETROLEUM,			20)));
		FractionRecipes.fractions.put(Fluids.EGG,				new Pair<>(new FluidStack(Fluids.CHOLESTEROL,		50),				new FluidStack(Fluids.RADIOSOLVENT,			50)));
		FractionRecipes.fractions.put(Fluids.OIL_COKER,			new Pair<>(new FluidStack(Fluids.CRACKOIL,		30),				new FluidStack(Fluids.HEATINGOIL,			70)));
		FractionRecipes.fractions.put(Fluids.NAPHTHA_COKER,		new Pair<>(new FluidStack(Fluids.NAPHTHA_CRACK,	75),				new FluidStack(Fluids.LIGHTOIL_CRACK,		25)));
		FractionRecipes.fractions.put(Fluids.GAS_COKER,			new Pair<>(new FluidStack(Fluids.AROMATICS,		25),				new FluidStack(Fluids.CARBONDIOXIDE,		75)));
		FractionRecipes.fractions.put(Fluids.CHLOROCALCITE_MIX, new Pair<>(new FluidStack(Fluids.CHLOROCALCITE_CLEANED, 50), new FluidStack(Fluids.COLLOID, 50)));
	}
	
	public static Pair<FluidStack, FluidStack> getFractions(FluidType oil) {
		return FractionRecipes.fractions.get(oil);
	}
	
	public static HashMap<Object, Object> getFractionRecipesForNEI() {

		HashMap<Object, Object> recipes = new HashMap<>();
		
		for(Entry<FluidType, Pair<FluidStack, FluidStack>> recipe : FractionRecipes.fractions.entrySet()) {
			ItemStack[] out = new ItemStack[] {
					ItemFluidIcon.make(recipe.getValue().getKey()),
					ItemFluidIcon.make(recipe.getValue().getValue())
			};
			
			recipes.put(ItemFluidIcon.make(recipe.getKey(), 100), out);
		}
		
		return recipes;
	}
	
	@Override
	public String getFileName() {
		return "hbmFractions.json";
	}
	
	@Override
	public String getComment() {
		return "Inputs are always 100mB, set output quantities accordingly.";
	}
	
	@Override
	public Object getRecipeObject() {
		return FractionRecipes.fractions;
	}

	@Override
	public void deleteRecipes() {
		FractionRecipes.fractions.clear();
	}
	
	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;

		FluidType input = Fluids.fromName(obj.get("input").getAsString());
		FluidStack output1 = readFluidStack(obj.get("output1").getAsJsonArray());
		FluidStack output2 = readFluidStack(obj.get("output2").getAsJsonArray());
		
		FractionRecipes.fractions.put(input, new Pair<>(output1, output2));
	}
	
	
	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<FluidType, Pair<FluidStack, FluidStack>> rec = (Entry<FluidType, Pair<FluidStack, FluidStack>>) recipe;
		
		writer.name("input").value(rec.getKey().getName());
		writer.name("output1"); writeFluidStack(rec.getValue().getKey(), writer);
		writer.name("output2"); writeFluidStack(rec.getValue().getValue(), writer);
	}
}
