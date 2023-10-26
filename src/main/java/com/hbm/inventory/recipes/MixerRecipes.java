package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ItemEnums.EnumAshType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraftforge.oredict.OreDictionary;

public class MixerRecipes extends SerializableRecipe {

	public static HashMap<FluidType, MixerRecipe[]> recipes = new HashMap();
	
	@Override
	public void registerDefaults() {
		MixerRecipes.register(Fluids.COOLANT, new MixerRecipe(2_000, 50).setStack1(new FluidStack(Fluids.WATER, 1_800)).setSolid(new OreDictStack(OreDictManager.KNO.dust())));
		MixerRecipes.register(Fluids.CRYOGEL, new MixerRecipe(2_000, 50).setStack1(new FluidStack(Fluids.COOLANT, 1_800)).setSolid(new ComparableStack(ModItems.powder_ice)));
		MixerRecipes.register(Fluids.NITAN, new MixerRecipe(1_000, 50).setStack1(new FluidStack(Fluids.KEROSENE, 600)).setStack2(new FluidStack(Fluids.MERCURY, 200)).setSolid(new ComparableStack(ModItems.powder_nitan_mix)));
		MixerRecipes.register(Fluids.FRACKSOL,
				new MixerRecipe(1_000, 20).setStack1(new FluidStack(Fluids.SULFURIC_ACID, 900)).setStack2(new FluidStack(Fluids.PETROLEUM, 100)),
				new MixerRecipe(1_000, 20).setStack1(new FluidStack(Fluids.WATER, 1000)).setStack2(new FluidStack(Fluids.PETROLEUM, 100)).setSolid(new OreDictStack(OreDictManager.S.dust())));
		MixerRecipes.register(Fluids.ENDERJUICE, new MixerRecipe(100, 100).setStack1(new FluidStack(Fluids.XPJUICE, 500)).setSolid(new OreDictStack(OreDictManager.DIAMOND.dust())));
		MixerRecipes.register(Fluids.SALIENT, new MixerRecipe(1000, 20).setStack1(new FluidStack(Fluids.SEEDSLURRY, 500)).setStack2(new FluidStack(Fluids.BLOOD, 500)));
		MixerRecipes.register(Fluids.COLLOID, new MixerRecipe(500, 20).setStack1(new FluidStack(Fluids.WATER, 500)).setSolid(new ComparableStack(ModItems.dust)));
		MixerRecipes.register(Fluids.PHOSGENE, new MixerRecipe(1000, 20).setStack1(new FluidStack(Fluids.UNSATURATEDS, 500)).setStack2(new FluidStack(Fluids.CHLORINE, 500)));
		MixerRecipes.register(Fluids.MUSTARDGAS, new MixerRecipe(1000, 20).setStack1(new FluidStack(Fluids.REFORMGAS, 750)).setStack2(new FluidStack(Fluids.CHLORINE, 250)).setSolid(new OreDictStack(OreDictManager.S.dust())));
		MixerRecipes.register(Fluids.IONGEL, new MixerRecipe(1_000, 50).setStack1(new FluidStack(Fluids.WATER, 1000)).setStack2(new FluidStack(Fluids.HYDROGEN, 200)).setSolid(new ComparableStack(ModItems.pellet_charged)));
		MixerRecipes.register(Fluids.EGG, new MixerRecipe(1_000, 50).setStack1(new FluidStack(Fluids.RADIOSOLVENT, 500)).setSolid(new ComparableStack(Items.egg)));
		MixerRecipes.register(Fluids.FISHOIL, new MixerRecipe(100, 50).setSolid(new ComparableStack(Items.fish, 1, OreDictionary.WILDCARD_VALUE)));
		MixerRecipes.register(Fluids.SUNFLOWEROIL, new MixerRecipe(100, 50).setSolid(new ComparableStack(Blocks.double_plant, 1, 0)));
		MixerRecipes.register(Fluids.FULLERENE, new MixerRecipe(250, 50).setStack1(new FluidStack(Fluids.RADIOSOLVENT, 500)).setSolid(new ComparableStack(DictFrame.fromOne(ModItems.powder_ash, EnumAshType.SOOT))));

		MixerRecipes.register(Fluids.SOLVENT, new MixerRecipe(1000, 50).setStack1(new FluidStack(Fluids.NAPHTHA, 500)).setStack2(new FluidStack(Fluids.AROMATICS, 500)));
		MixerRecipes.register(Fluids.SULFURIC_ACID, new MixerRecipe(500, 50).setStack1(new FluidStack(Fluids.ACID, 800)).setSolid(new OreDictStack(OreDictManager.S.dust())));
		MixerRecipes.register(Fluids.NITRIC_ACID, new MixerRecipe(500, 50).setStack1(new FluidStack(Fluids.SULFURIC_ACID, 500)).setSolid(new OreDictStack(OreDictManager.KNO.dust())));
		MixerRecipes.register(Fluids.RADIOSOLVENT, new MixerRecipe(1000, 50).setStack1(new FluidStack(Fluids.REFORMGAS, 750)).setStack2(new FluidStack(Fluids.CHLORINE, 250)));
		MixerRecipes.register(Fluids.SCHRABIDIC, new MixerRecipe(16_000, 100).setStack1(new FluidStack(Fluids.SAS3, 8_000)).setStack2(new FluidStack(Fluids.ACID, 6_000)).setSolid(new ComparableStack(ModItems.pellet_charged)));
		
		MixerRecipes.register(Fluids.PETROIL, new MixerRecipe(1_000, 30).setStack1(new FluidStack(Fluids.RECLAIMED, 800)).setStack2(new FluidStack(Fluids.LUBRICANT, 200)));
		MixerRecipes.register(Fluids.LUBRICANT,
				new MixerRecipe(1_000, 20).setStack1(new FluidStack(Fluids.HEATINGOIL, 500)).setStack2(new FluidStack(Fluids.UNSATURATEDS, 500)),
				new MixerRecipe(1_000, 20).setStack1(new FluidStack(Fluids.FISHOIL, 800)).setStack2(new FluidStack(Fluids.ETHANOL, 200)),
				new MixerRecipe(1_000, 20).setStack1(new FluidStack(Fluids.SUNFLOWEROIL, 800)).setStack2(new FluidStack(Fluids.ETHANOL, 200)));
		MixerRecipes.register(Fluids.BIOFUEL,
				new MixerRecipe(250, 20).setStack1(new FluidStack(Fluids.FISHOIL, 500)).setStack2(new FluidStack(Fluids.WOODOIL, 500)),
				new MixerRecipe(200, 20).setStack1(new FluidStack(Fluids.SUNFLOWEROIL, 500)).setStack2(new FluidStack(Fluids.WOODOIL, 500)));
		MixerRecipes.register(Fluids.NITROGLYCERIN,
				new MixerRecipe(1000, 20).setStack1(new FluidStack(Fluids.PETROLEUM, 1_000)).setStack2(new FluidStack(Fluids.NITRIC_ACID, 1_000)),
				new MixerRecipe(1000, 20).setStack1(new FluidStack(Fluids.FISHOIL, 500)).setStack2(new FluidStack(Fluids.NITRIC_ACID, 500)));
		
		MixerRecipes.register(Fluids.THORIUM_SALT, new MixerRecipe(1_000, 30).setStack1(new FluidStack(Fluids.CHLORINE, 1000)).setSolid(new OreDictStack(OreDictManager.TH232.dust())));

		MixerRecipes.register(Fluids.SYNGAS, new MixerRecipe(1_000, 50).setStack1(new FluidStack(Fluids.COALOIL, 500)).setStack2(new FluidStack(Fluids.STEAM, 500)));
		MixerRecipes.register(Fluids.OXYHYDROGEN, new MixerRecipe(1_000, 50).setStack1(new FluidStack(Fluids.HYDROGEN, 500)).setStack2(new FluidStack(Fluids.OXYGEN, 500)));

		MixerRecipes.register(Fluids.PETROIL_LEADED, new MixerRecipe(12_000, 40).setStack1(new FluidStack(Fluids.PETROIL, 10_000)).setSolid(new ComparableStack(ModItems.antiknock)));
		MixerRecipes.register(Fluids.GASOLINE_LEADED, new MixerRecipe(12_000, 40).setStack1(new FluidStack(Fluids.GASOLINE, 10_000)).setSolid(new ComparableStack(ModItems.antiknock)));
		MixerRecipes.register(Fluids.COALGAS_LEADED, new MixerRecipe(12_000, 40).setStack1(new FluidStack(Fluids.COALGAS, 10_000)).setSolid(new ComparableStack(ModItems.antiknock)));

		MixerRecipes.register(Fluids.DIESEL_REFORM, new MixerRecipe(1_000, 50).setStack1(new FluidStack(Fluids.DIESEL, 900)).setStack2(new FluidStack(Fluids.REFORMATE, 100)));
		MixerRecipes.register(Fluids.DIESEL_CRACK_REFORM, new MixerRecipe(1_000, 50).setStack1(new FluidStack(Fluids.DIESEL_CRACK, 900)).setStack2(new FluidStack(Fluids.REFORMATE, 100)));
		MixerRecipes.register(Fluids.KEROSENE_REFORM, new MixerRecipe(1_000, 50).setStack1(new FluidStack(Fluids.KEROSENE, 900)).setStack2(new FluidStack(Fluids.REFORMATE, 100)));
		
		MixerRecipes.register(Fluids.CHLOROCALCITE_SOLUTION, new MixerRecipe(500, 50).setStack1(new FluidStack(Fluids.WATER, 250)).setStack2(new FluidStack(Fluids.NITRIC_ACID, 250)).setSolid(new OreDictStack(OreDictManager.CHLOROCALCITE.dust())));
		MixerRecipes.register(Fluids.CHLOROCALCITE_MIX, new MixerRecipe(1000, 50).setStack1(new FluidStack(Fluids.CHLOROCALCITE_SOLUTION, 500)).setStack2(new FluidStack(Fluids.SULFURIC_ACID, 500)).setSolid(new ComparableStack(ModItems.powder_flux)));
	}
	
	public static void register(FluidType type, MixerRecipe... rec) {
		MixerRecipes.recipes.put(type, rec);
	}
	
	public static MixerRecipe[] getOutput(FluidType type) {
		return MixerRecipes.recipes.get(type);
	}
	
	public static MixerRecipe getOutput(FluidType type, int index) {
		MixerRecipe[] recs = MixerRecipes.recipes.get(type);
		
		if(recs == null) return null;
		
		return recs[index % recs.length];
	}
	
	@Override
	public String getFileName() {
		return "hbmMixer.json";
	}
	
	@Override
	public Object getRecipeObject() {
		return MixerRecipes.recipes;
	}

	@Override
	public void deleteRecipes() {
		MixerRecipes.recipes.clear();
	}
	
	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;
		
		FluidType outputType = Fluids.fromName(obj.get("outputType").getAsString());
		JsonArray recipeArray = obj.get("recipes").getAsJsonArray();
		MixerRecipe[] array = new MixerRecipe[recipeArray.size()];
		
		for(int i = 0; i < recipeArray.size(); i++) {
			JsonObject sub = recipeArray.get(i).getAsJsonObject();
			MixerRecipe mix = new MixerRecipe(sub.get("outputAmount").getAsInt(), sub.get("duration").getAsInt());
	
			if(sub.has("input1")) mix.setStack1(readFluidStack(sub.get("input1").getAsJsonArray()));
			if(sub.has("input2")) mix.setStack2(readFluidStack(sub.get("input2").getAsJsonArray()));
			if(sub.has("solidInput")) mix.setSolid(readAStack(sub.get("solidInput").getAsJsonArray()));
			
			array[i] = mix;
			
		}
		
		MixerRecipes.recipes.put(outputType, array);
	}
	
	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<FluidType, MixerRecipe[]> rec = (Entry<FluidType, MixerRecipe[]>) recipe;
		MixerRecipe[] recipes = rec.getValue();
		
		writer.name("outputType").value(rec.getKey().getName());
		writer.name("recipes").beginArray();
		
		for(MixerRecipe mix : recipes) {
			writer.beginObject();
			writer.name("duration").value(mix.processTime);
			writer.name("outputAmount").value(mix.output);
			
			if(mix.input1 != null) { writer.name("input1"); writeFluidStack(mix.input1, writer); }
			if(mix.input2 != null) { writer.name("input2"); writeFluidStack(mix.input2, writer); }
			if(mix.solidInput != null) { writer.name("solidInput"); writeAStack(mix.solidInput, writer); }
			writer.endObject();
		}
		writer.endArray();
	}

	public static HashMap getRecipes() {
		
		HashMap<Object[], Object> recipes = new HashMap<>();
		
		for(Entry<FluidType, MixerRecipe[]> entry : MixerRecipes.recipes.entrySet()) {
			
			FluidType type = entry.getKey();
			MixerRecipe[] recs = entry.getValue();
			
			for(MixerRecipe recipe : recs) {
				FluidStack output = new FluidStack(type, recipe.output);
	
				List<Object> objects = new ArrayList<>();
				if(recipe.input1 != null) objects.add(ItemFluidIcon.make(recipe.input1));
				if(recipe.input2 != null) objects.add(ItemFluidIcon.make(recipe.input2));
				if(recipe.solidInput != null) objects.add(recipe.solidInput);
				
				recipes.put(objects.toArray(), ItemFluidIcon.make(output));
			}
		}
		
		return recipes;
	}
	
	public static class MixerRecipe {
		public FluidStack input1;
		public FluidStack input2;
		public AStack solidInput;
		public int processTime;
		public int output;
		
		protected MixerRecipe(int output, int processTime) {
			this.output = output;
			this.processTime = processTime;
		}

		protected MixerRecipe setStack1(FluidStack stack) { this.input1 = stack; return this; }
		protected MixerRecipe setStack2(FluidStack stack) { this.input2 = stack; return this; }
		protected MixerRecipe setSolid(AStack stack) { this.solidInput = stack; return this; }
	}
}
