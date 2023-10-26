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
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ItemEnums.EnumTarType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.util.Tuple.Pair;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SolidificationRecipes extends SerializableRecipe {

	public static final int SF_OIL =		200;
	public static final int SF_CRACK =		200;
	public static final int SF_HEAVY =		150;
	public static final int SF_BITUMEN =	100;
	public static final int SF_SMEAR =		100;
	public static final int SF_HEATING =	50;
	public static final int SF_RECLAIMED =	100;
	public static final int SF_PETROIL =	125;
	public static final int SF_LUBE =		100;
	public static final int SF_NAPH =		150;
	public static final int SF_DIESEL =		200;
	public static final int SF_LIGHT =		225;
	public static final int SF_KEROSENE =	275;
	public static final int SF_GAS =		375;
	public static final int SF_PETROLEUM =	300;
	public static final int SF_LPG =		150;
	public static final int SF_BIOGAS =		1750;
	public static final int SF_BIOFUEL =	750;
	public static final int SF_COALOIL =	200;
	public static final int SF_CREOSOTE =	200;
	public static final int SF_WOOD =		1000;
	//mostly for alternate chemistry, dump into SF if not desired
	public static final int SF_AROMA =		1000;
	public static final int SF_UNSAT =		1000;
	//in the event that these compounds are STILL too useless, add unsat + gas -> kerosene recipe for all those missile junkies
	//aromatics can be idfk wax or soap or sth, perhaps artificial lubricant?
	//on that note, add more leaded variants
	
	private static HashMap<FluidType, Pair<Integer, ItemStack>> recipes = new HashMap();

	@Override
	public void registerDefaults() {
		
		SolidificationRecipes.registerRecipe(Fluids.WATER,		1000,			Blocks.ice);
		SolidificationRecipes.registerRecipe(Fluids.LAVA,		1000,			Blocks.obsidian);
		SolidificationRecipes.registerRecipe(Fluids.MERCURY,		125,			ModItems.ingot_mercury);
		SolidificationRecipes.registerRecipe(Fluids.BIOGAS,		250,			ModItems.biomass_compressed);
		SolidificationRecipes.registerRecipe(Fluids.SALIENT,		1280,			new ItemStack(ModItems.bio_wafer, 8)); //4 (food val) * 2 (sat mod) * 2 (constant) * 10 (quanta) * 8 (batch size)
		SolidificationRecipes.registerRecipe(Fluids.ENDERJUICE,	100,			Items.ender_pearl);
		SolidificationRecipes.registerRecipe(Fluids.WATZ,		1000,			ModItems.ingot_mud);
		SolidificationRecipes.registerRecipe(Fluids.REDMUD,		1000,			Items.iron_ingot);
		SolidificationRecipes.registerRecipe(Fluids.SODIUM,		100,			ModItems.powder_sodium);

		SolidificationRecipes.registerRecipe(Fluids.OIL,				SolidificationRecipes.SF_OIL,			DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRUDE));
		SolidificationRecipes.registerRecipe(Fluids.CRACKOIL,		SolidificationRecipes.SF_CRACK,		DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRACK));
		SolidificationRecipes.registerRecipe(Fluids.COALOIL,			SolidificationRecipes.SF_COALOIL,		DictFrame.fromOne(ModItems.oil_tar, EnumTarType.COAL));
		SolidificationRecipes.registerRecipe(Fluids.HEAVYOIL,		SolidificationRecipes.SF_HEAVY,		DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRUDE));
		SolidificationRecipes.registerRecipe(Fluids.HEAVYOIL_VACUUM,	SolidificationRecipes.SF_HEAVY,		DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRUDE));
		SolidificationRecipes.registerRecipe(Fluids.BITUMEN,			SolidificationRecipes.SF_BITUMEN,		DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRUDE));
		SolidificationRecipes.registerRecipe(Fluids.COALCREOSOTE,	SolidificationRecipes.SF_CREOSOTE,	DictFrame.fromOne(ModItems.oil_tar, EnumTarType.COAL));
		SolidificationRecipes.registerRecipe(Fluids.WOODOIL,			SolidificationRecipes.SF_WOOD,		DictFrame.fromOne(ModItems.oil_tar, EnumTarType.WOOD));
		SolidificationRecipes.registerRecipe(Fluids.LUBRICANT,		SolidificationRecipes.SF_LUBE,		DictFrame.fromOne(ModItems.oil_tar, EnumTarType.PARAFFIN));

		SolidificationRecipes.registerRecipe(Fluids.BALEFIRE,		250,			ModItems.solid_fuel_bf);
		
		SolidificationRecipes.registerSFAuto(Fluids.SMEAR);
		SolidificationRecipes.registerSFAuto(Fluids.HEATINGOIL);
		SolidificationRecipes.registerSFAuto(Fluids.HEATINGOIL_VACUUM);
		SolidificationRecipes.registerSFAuto(Fluids.RECLAIMED);
		SolidificationRecipes.registerSFAuto(Fluids.PETROIL);
		SolidificationRecipes.registerSFAuto(Fluids.NAPHTHA);
		SolidificationRecipes.registerSFAuto(Fluids.NAPHTHA_CRACK);
		SolidificationRecipes.registerSFAuto(Fluids.DIESEL);
		SolidificationRecipes.registerSFAuto(Fluids.DIESEL_REFORM);
		SolidificationRecipes.registerSFAuto(Fluids.DIESEL_CRACK);
		SolidificationRecipes.registerSFAuto(Fluids.DIESEL_CRACK_REFORM);
		SolidificationRecipes.registerSFAuto(Fluids.LIGHTOIL);
		SolidificationRecipes.registerSFAuto(Fluids.LIGHTOIL_CRACK);
		SolidificationRecipes.registerSFAuto(Fluids.LIGHTOIL_VACUUM);
		SolidificationRecipes.registerSFAuto(Fluids.KEROSENE);
		//registerSFAuto(GAS);
		SolidificationRecipes.registerSFAuto(Fluids.SOURGAS);
		SolidificationRecipes.registerSFAuto(Fluids.REFORMGAS);
		SolidificationRecipes.registerSFAuto(Fluids.SYNGAS);
		SolidificationRecipes.registerSFAuto(Fluids.PETROLEUM);
		SolidificationRecipes.registerSFAuto(Fluids.LPG);
		//registerSFAuto(BIOGAS);
		SolidificationRecipes.registerSFAuto(Fluids.BIOFUEL);
		SolidificationRecipes.registerSFAuto(Fluids.AROMATICS);
		SolidificationRecipes.registerSFAuto(Fluids.UNSATURATEDS);
		SolidificationRecipes.registerSFAuto(Fluids.REFORMATE);
		SolidificationRecipes.registerSFAuto(Fluids.XYLENE);
		SolidificationRecipes.registerSFAuto(Fluids.BALEFIRE, 24000000L, ModItems.solid_fuel_bf); //holy shit this is energy dense*/
		
	}

	private static void registerSFAuto(FluidType fluid) {
		SolidificationRecipes.registerSFAuto(fluid, 1_440_000L, ModItems.solid_fuel); //3200 burntime * 1.5 burntime bonus * 300 TU/t
	}
	private static void registerSFAuto(FluidType fluid, long tuPerSF, Item fuel) {
		long tuPerBucket = fluid.getTrait(FT_Flammable.class).getHeatEnergy();
		double penalty = 1.25D;
		
		int mB = (int) (tuPerSF * 1000L * penalty / tuPerBucket);

		if(mB > 10_000) mB -= (mB % 1000);
		else if(mB > 1_000) mB -= (mB % 100);
		else if(mB > 100) mB -= (mB % 10);

		SolidificationRecipes.registerRecipe(fluid, mB, fuel);
	}

	private static void registerRecipe(FluidType type, int quantity, Item output) { SolidificationRecipes.registerRecipe(type, quantity, new ItemStack(output)); }
	private static void registerRecipe(FluidType type, int quantity, Block output) { SolidificationRecipes.registerRecipe(type, quantity, new ItemStack(output)); }
	private static void registerRecipe(FluidType type, int quantity, ItemStack output) {
		SolidificationRecipes.recipes.put(type, new Pair<>(quantity, output));
	}
	
	public static Pair<Integer, ItemStack> getOutput(FluidType type) {
		return SolidificationRecipes.recipes.get(type);
	}

	public static HashMap<ItemStack, ItemStack> getRecipes() {
		
		HashMap<ItemStack, ItemStack> recipes = new HashMap<>();
		
		for(Entry<FluidType, Pair<Integer, ItemStack>> entry : SolidificationRecipes.recipes.entrySet()) {
			
			FluidType type = entry.getKey();
			int amount = entry.getValue().getKey();
			ItemStack out = entry.getValue().getValue().copy();
			
			recipes.put(ItemFluidIcon.make(type, amount), out);
		}
		
		return recipes;
	}

	@Override
	public String getFileName() {
		return "hbmSolidifier.json";
	}

	@Override
	public Object getRecipeObject() {
		return SolidificationRecipes.recipes;
	}

	@Override
	public void deleteRecipes() {
		SolidificationRecipes.recipes.clear();
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;
		FluidStack in = readFluidStack(obj.get("input").getAsJsonArray());
		ItemStack out = readItemStack(obj.get("output").getAsJsonArray());
		SolidificationRecipes.recipes.put(in.type, new Pair(in.fill, out));
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<FluidType, Pair<Integer, ItemStack>> rec = (Entry<FluidType, Pair<Integer, ItemStack>>) recipe;
		FluidStack in = new FluidStack(rec.getKey(), rec.getValue().getKey());
		writer.name("input");
		writeFluidStack(in, writer);
		writer.name("output");
		writeItemStack(rec.getValue().getValue(), writer);
	}
}
