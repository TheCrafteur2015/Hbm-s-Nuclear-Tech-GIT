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

public class CrackingRecipes extends SerializableRecipe {
	
	//cracking in percent
	public static final int oil_crack_oil = 80;
	public static final int oil_crack_petro = 20;
	public static final int bitumen_crack_oil = 80;
	public static final int bitumen_crack_aroma = 20;
	public static final int smear_crack_napht = 60;
	public static final int smear_crack_petro = 40;
	public static final int gas_crack_petro = 30;
	public static final int gas_crack_unsat = 20;
	public static final int diesel_crack_kero = 40;
	public static final int diesel_crack_petro = 30;
	public static final int kero_crack_petro = 60;
	public static final int wood_crack_aroma = 10;
	public static final int wood_crack_heat = 40;
	public static final int xyl_crack_aroma = 80;
	public static final int xyl_crack_petro = 20;
	
	private static Map<FluidType, Pair<FluidStack, FluidStack>> cracking = new HashMap();
	
	@Override
	public void registerDefaults() {
		CrackingRecipes.cracking.put(Fluids.OIL,				new Pair(new FluidStack(Fluids.CRACKOIL,		CrackingRecipes.oil_crack_oil),		new FluidStack(Fluids.PETROLEUM,	CrackingRecipes.oil_crack_petro)));
		CrackingRecipes.cracking.put(Fluids.BITUMEN,			new Pair(new FluidStack(Fluids.OIL,				CrackingRecipes.bitumen_crack_oil),	new FluidStack(Fluids.AROMATICS,	CrackingRecipes.bitumen_crack_aroma)));
		CrackingRecipes.cracking.put(Fluids.SMEAR,				new Pair(new FluidStack(Fluids.NAPHTHA,			CrackingRecipes.smear_crack_napht),	new FluidStack(Fluids.PETROLEUM,	CrackingRecipes.smear_crack_petro)));
		CrackingRecipes.cracking.put(Fluids.GAS,				new Pair(new FluidStack(Fluids.PETROLEUM,		CrackingRecipes.gas_crack_petro),	new FluidStack(Fluids.UNSATURATEDS,	CrackingRecipes.gas_crack_unsat)));
		CrackingRecipes.cracking.put(Fluids.DIESEL,				new Pair(new FluidStack(Fluids.KEROSENE,		CrackingRecipes.diesel_crack_kero),	new FluidStack(Fluids.PETROLEUM,	CrackingRecipes.diesel_crack_petro)));
		CrackingRecipes.cracking.put(Fluids.DIESEL_CRACK,		new Pair(new FluidStack(Fluids.KEROSENE,		CrackingRecipes.diesel_crack_kero),	new FluidStack(Fluids.PETROLEUM,	CrackingRecipes.diesel_crack_petro)));
		CrackingRecipes.cracking.put(Fluids.KEROSENE,			new Pair(new FluidStack(Fluids.PETROLEUM,		CrackingRecipes.kero_crack_petro),	new FluidStack(Fluids.NONE,			0)));
		CrackingRecipes.cracking.put(Fluids.WOODOIL,			new Pair(new FluidStack(Fluids.HEATINGOIL,		CrackingRecipes.wood_crack_heat),	new FluidStack(Fluids.AROMATICS,	CrackingRecipes.wood_crack_aroma)));
		CrackingRecipes.cracking.put(Fluids.XYLENE,				new Pair(new FluidStack(Fluids.AROMATICS,		CrackingRecipes.xyl_crack_aroma),	new FluidStack(Fluids.PETROLEUM,	CrackingRecipes.xyl_crack_petro)));
		CrackingRecipes.cracking.put(Fluids.HEATINGOIL_VACUUM,	new Pair(new FluidStack(Fluids.HEATINGOIL,		80),				new FluidStack(Fluids.REFORMGAS,	20)));
		CrackingRecipes.cracking.put(Fluids.REFORMATE,			new Pair(new FluidStack(Fluids.UNSATURATEDS,	40),				new FluidStack(Fluids.REFORMGAS,	60)));
	}
	
	public static Pair<FluidStack, FluidStack> getCracking(FluidType oil) {
		return CrackingRecipes.cracking.get(oil);
	}
	
	protected static Map<FluidType, Pair<FluidStack, FluidStack>> getCrackingRecipes() {
		return CrackingRecipes.cracking;
	}
	
	public static HashMap<Object, Object> getCrackingRecipesForNEI() {

		HashMap<Object, Object> recipes = new HashMap();
		
		for(Entry<FluidType, Pair<FluidStack, FluidStack>> recipe : CrackingRecipes.cracking.entrySet()) {
			ItemStack[] in = new ItemStack[] {
					ItemFluidIcon.make(recipe.getKey(), 100),
					ItemFluidIcon.make(Fluids.STEAM, 200)
			};
			ItemStack[] out = new ItemStack[] {
					ItemFluidIcon.make(recipe.getValue().getKey()),
					ItemFluidIcon.make(recipe.getValue().getValue()),
					ItemFluidIcon.make(Fluids.SPENTSTEAM, 2)
			};
			
			recipes.put(in, recipe.getValue().getValue().type == Fluids.NONE ? new ItemStack[] {ItemFluidIcon.make(recipe.getValue().getKey()), ItemFluidIcon.make(Fluids.SPENTSTEAM, 2)} : out);
		}
		
		return recipes;
	}
	
	@Override
	public String getFileName() {
		return "hbmCracking.json";
	}
	
	@Override
	public String getComment() {
		return "Inputs are always 100mB, set output quantities accordingly. The steam in/outputs are fixed, using 200mB of steam per 100mB of input.";
	}
	
	@Override
	public Object getRecipeObject() {
		return CrackingRecipes.cracking;
	}

	@Override
	public void deleteRecipes() {
		CrackingRecipes.cracking.clear();
	}
	
	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;

		FluidType input = Fluids.fromName(obj.get("input").getAsString());
		FluidStack output1 = readFluidStack(obj.get("output1").getAsJsonArray());
		FluidStack output2 = readFluidStack(obj.get("output2").getAsJsonArray());
		
		CrackingRecipes.cracking.put(input, new Pair(output1, output2));
	}
	
	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<FluidType, Pair<FluidStack, FluidStack>> rec = (Entry<FluidType, Pair<FluidStack, FluidStack>>) recipe;
		
		writer.name("input").value(rec.getKey().getName());
		writer.name("output1"); writeFluidStack(rec.getValue().getKey(), writer);
		writer.name("output2"); writeFluidStack(rec.getValue().getValue(), writer);
	}
}
