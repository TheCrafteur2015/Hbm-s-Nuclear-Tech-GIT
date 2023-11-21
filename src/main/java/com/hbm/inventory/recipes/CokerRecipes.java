package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.trait.FT_Combustible;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ItemEnums.EnumCokeType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.util.Tuple.Triplet;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CokerRecipes extends SerializableRecipe {
	
	private static HashMap<FluidType, Triplet<Integer, ItemStack, FluidStack>> recipes = new HashMap<>();

	@Override
	public void registerDefaults() {

		CokerRecipes.registerAuto(Fluids.HEAVYOIL,				Fluids.OIL_COKER);
		CokerRecipes.registerAuto(Fluids.HEAVYOIL_VACUUM,		Fluids.REFORMATE);
		CokerRecipes.registerAuto(Fluids.COALCREOSOTE,			Fluids.NAPHTHA_COKER);
		CokerRecipes.registerAuto(Fluids.SMEAR,					Fluids.OIL_COKER);
		CokerRecipes.registerAuto(Fluids.HEATINGOIL,			Fluids.OIL_COKER);
		CokerRecipes.registerAuto(Fluids.HEATINGOIL_VACUUM,		Fluids.OIL_COKER);
		CokerRecipes.registerAuto(Fluids.RECLAIMED,				Fluids.NAPHTHA_COKER);
		CokerRecipes.registerAuto(Fluids.NAPHTHA,				Fluids.NAPHTHA_COKER);
		CokerRecipes.registerAuto(Fluids.NAPHTHA_CRACK,			Fluids.NAPHTHA_COKER);
		CokerRecipes.registerAuto(Fluids.DIESEL,				Fluids.NAPHTHA_COKER);
		CokerRecipes.registerAuto(Fluids.DIESEL_REFORM,			Fluids.NAPHTHA_COKER);
		CokerRecipes.registerAuto(Fluids.DIESEL_CRACK,			Fluids.GAS_COKER);
		CokerRecipes.registerAuto(Fluids.DIESEL_CRACK_REFORM,	Fluids.GAS_COKER);
		CokerRecipes.registerAuto(Fluids.LIGHTOIL,				Fluids.GAS_COKER);
		CokerRecipes.registerAuto(Fluids.LIGHTOIL_CRACK,		Fluids.GAS_COKER);
		CokerRecipes.registerAuto(Fluids.LIGHTOIL_VACUUM,		Fluids.GAS_COKER);
		CokerRecipes.registerAuto(Fluids.BIOFUEL,				Fluids.GAS_COKER);
		CokerRecipes.registerAuto(Fluids.AROMATICS,				Fluids.GAS_COKER);
		CokerRecipes.registerAuto(Fluids.REFORMATE,				Fluids.GAS_COKER);
		CokerRecipes.registerAuto(Fluids.XYLENE,				Fluids.GAS_COKER);
		CokerRecipes.registerAuto(Fluids.FISHOIL,				Fluids.MERCURY);
		CokerRecipes.registerAuto(Fluids.SUNFLOWEROIL,			Fluids.GAS_COKER);

		CokerRecipes.registerSFAuto(Fluids.WOODOIL, 340_000L, new ItemStack(Items.coal, 1, 1), Fluids.GAS_COKER);
		
		registerRecipe(Fluids.WATZ, 4_000, new ItemStack(ModItems.ingot_mud, 4), null);
		registerRecipe(Fluids.REDMUD, 1_000, new ItemStack(Items.iron_ingot, 1), new FluidStack(Fluids.MERCURY, 50));
		registerRecipe(Fluids.BITUMEN, 16_000, DictFrame.fromOne(ModItems.coke, EnumCokeType.PETROLEUM), new FluidStack(Fluids.OIL_COKER, 1_600));
		registerRecipe(Fluids.LUBRICANT, 12_000, DictFrame.fromOne(ModItems.coke, EnumCokeType.PETROLEUM), new FluidStack(Fluids.OIL_COKER, 1_200));
		registerRecipe(Fluids.CALCIUM_SOLUTION, 125, new ItemStack(ModItems.powder_calcium), new FluidStack(Fluids.SPENTSTEAM, 100));
	}

	private static void registerAuto(FluidType fluid, FluidType type) {
		CokerRecipes.registerSFAuto(fluid, 820_000L, DictFrame.fromOne(ModItems.coke, EnumCokeType.PETROLEUM), type); //3200 burntime * 1.25 burntime bonus * 200 TU/t + 20000TU per operation
	}
	private static void registerSFAuto(FluidType fluid, long tuPerSF, ItemStack fuel, FluidType type) {
		long tuFlammable = fluid.hasTrait(FT_Flammable.class) ? fluid.getTrait(FT_Flammable.class).getHeatEnergy() : 0;
		long tuCombustible = fluid.hasTrait(FT_Combustible.class) ? fluid.getTrait(FT_Combustible.class).getCombustionEnergy() : 0;
		
		long tuPerBucket = Math.max(tuFlammable, tuCombustible);
		
		double penalty = 1;//1.1D; //no penalty
		
		int mB = (int) (tuPerSF * 1000L * penalty / tuPerBucket);

		if(mB > 10_000) mB -= (mB % 1000);
		else if(mB > 1_000) mB -= (mB % 100);
		else if(mB > 100) mB -= (mB % 10);
		
		FluidStack byproduct = type == null ? null : new FluidStack(type, Math.max(10, mB / 10));

		CokerRecipes.registerRecipe(fluid, mB, fuel, byproduct);
	}
	private static void registerRecipe(FluidType type, int quantity, ItemStack output, FluidStack byproduct) {
		CokerRecipes.recipes.put(type, new Triplet<>(quantity, output, byproduct));
	}
	
	public static Triplet<Integer, ItemStack, FluidStack> getOutput(FluidType type) {
		return CokerRecipes.recipes.get(type);
	}

	public static HashMap<ItemStack, ItemStack[]> getRecipes() {
		
		HashMap<ItemStack, ItemStack[]> recipes = new HashMap<>();
		
		for(Entry<FluidType, Triplet<Integer, ItemStack, FluidStack>> entry : CokerRecipes.recipes.entrySet()) {
			
			FluidType type = entry.getKey();
			int amount = entry.getValue().getX();
			ItemStack out = entry.getValue().getY().copy();
			FluidStack byproduct = entry.getValue().getZ();
			

			if(out != null && byproduct != null) recipes.put(ItemFluidIcon.make(type, amount), new ItemStack[] {out, ItemFluidIcon.make(byproduct)});
			if(out != null && byproduct == null) recipes.put(ItemFluidIcon.make(type, amount), new ItemStack[] {out});
			if(out == null && byproduct != null) recipes.put(ItemFluidIcon.make(type, amount), new ItemStack[] {ItemFluidIcon.make(byproduct)});
		}
		
		return recipes;
	}

	@Override
	public String getFileName() {
		return "hbmCoker.json";
	}

	@Override
	public Object getRecipeObject() {
		return CokerRecipes.recipes;
	}

	@Override
	public void deleteRecipes() {
		CokerRecipes.recipes.clear();
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;
		FluidStack in = readFluidStack(obj.get("input").getAsJsonArray());
		ItemStack out = obj.has("output") ? readItemStack(obj.get("output").getAsJsonArray()) : null;
		FluidStack byproduct = obj.has("byproduct") ? readFluidStack(obj.get("byproduct").getAsJsonArray()) : null;
		CokerRecipes.recipes.put(in.type, new Triplet<>(in.fill, out, byproduct));
	}

	
	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<FluidType, Triplet<Integer, ItemStack, FluidStack>> rec = (Entry<FluidType, Triplet<Integer, ItemStack, FluidStack>>) recipe;
		FluidStack in = new FluidStack(rec.getKey(), rec.getValue().getX());
		writer.name("input");
		writeFluidStack(in, writer);
		if(rec.getValue().getY() != null) {
			writer.name("output");
			writeItemStack(rec.getValue().getY(), writer);
		}
		if(rec.getValue().getZ() != null) {
			writer.name("byproduct");
			writeFluidStack(rec.getValue().getZ(), writer);
		}
	}
}
