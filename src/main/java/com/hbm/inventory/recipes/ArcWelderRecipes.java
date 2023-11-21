package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;

import net.minecraft.item.ItemStack;

@SuppressWarnings("all")
public class ArcWelderRecipes extends SerializableRecipe {
	
	public static List<ArcWelderRecipe> recipes = new ArrayList<>();

	@Override
	public void registerDefaults() {
		ArcWelderRecipes.recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.motor, 2), 100, 200L,
				new OreDictStack(OreDictManager.IRON.plate(), 2), new ComparableStack(ModItems.coil_copper), new ComparableStack(ModItems.coil_copper_torus)));
		ArcWelderRecipes.recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.motor, 2), 100, 400L,
				new OreDictStack(OreDictManager.STEEL.plate(), 1), new ComparableStack(ModItems.coil_copper), new ComparableStack(ModItems.coil_copper_torus)));

		ArcWelderRecipes.recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.wire_dense, 1, Mats.MAT_ALLOY.id), 100, 10_000L,
				new ComparableStack(ModItems.wire_advanced_alloy, 8)));
		ArcWelderRecipes.recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.wire_dense, 1, Mats.MAT_GOLD.id), 100, 10_000L,
				new ComparableStack(ModItems.wire_gold, 8)));

		ArcWelderRecipes.recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.circuit_copper), 100, 1_000L, new FluidStack(Fluids.GAS, 250),
				new ComparableStack(ModItems.circuit_aluminium, 1), new OreDictStack(OreDictManager.NETHERQUARTZ.dust()), new ComparableStack(ModItems.wire_copper, 8)));
		ArcWelderRecipes.recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.circuit_red_copper), 100, 2_500L, new FluidStack(Fluids.PETROLEUM, 250),
				new ComparableStack(ModItems.circuit_copper, 1), new OreDictStack(OreDictManager.GOLD.dust()), new ComparableStack(ModItems.wire_red_copper, 8)));
		ArcWelderRecipes.recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.circuit_gold), 100, 10_000L, new FluidStack(Fluids.UNSATURATEDS, 250),
				new ComparableStack(ModItems.circuit_red_copper, 1), new OreDictStack(OreDictManager.ANY_PLASTIC.ingot()), new ComparableStack(ModItems.wire_gold, 8)));
		ArcWelderRecipes.recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.circuit_schrabidium), 100, 50_000L, new FluidStack(Fluids.SOURGAS, 250),
				new ComparableStack(ModItems.circuit_gold, 1), new OreDictStack(OreDictManager.DESH.ingot()), new ComparableStack(ModItems.wire_schrabidium, 8)));

		//earlygame welded parts
		ArcWelderRecipes.recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_IRON.id), 100, 100L,
				new OreDictStack(OreDictManager.IRON.plateCast(), 2)));
		//high-demand mid-game parts
		ArcWelderRecipes.recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_STEEL.id), 100, 500L,
				new OreDictStack(OreDictManager.STEEL.plateCast(), 2)));
		//literally just the combination oven
		ArcWelderRecipes.recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_COPPER.id), 200, 1_000L,
				new OreDictStack(OreDictManager.CU.plateCast(), 2)));
		
		//mid-game, single combustion engine running on LPG
		ArcWelderRecipes.recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_TITANIUM.id), 600, 50_000L,
				new OreDictStack(OreDictManager.TI.plateCast(), 2)));
		//mid-game PWR
		ArcWelderRecipes.recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_ZIRCONIUM.id), 600, 10_000L,
				new OreDictStack(OreDictManager.ZR.plateCast(), 2)));
		//late-game fusion
		ArcWelderRecipes.recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_TCALLOY.id), 1_200, 1_000_000L, new FluidStack(Fluids.OXYGEN, 1_000),
				new OreDictStack(OreDictManager.TCALLOY.plateCast(), 2)));
		ArcWelderRecipes.recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_CDALLOY.id), 1_200, 1_000_000L, new FluidStack(Fluids.OXYGEN, 1_000),
				new OreDictStack(OreDictManager.CDALLOY.plateCast(), 2)));
		ArcWelderRecipes.recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_TUNGSTEN.id), 1_200, 250_000L, new FluidStack(Fluids.OXYGEN, 1_000),
				new OreDictStack(OreDictManager.W.plateCast(), 2)));
		//pre-DFC
		ArcWelderRecipes.recipes.add(new ArcWelderRecipe(new ItemStack(ModItems.plate_welded, 1, Mats.MAT_OSMIRIDIUM.id), 6_000, 20_000_000L, new FluidStack(Fluids.REFORMGAS, 16_000),
				new OreDictStack(OreDictManager.OSMIRIDIUM.plateCast(), 2)));
	}
	
	public static HashMap getRecipes() {

		HashMap<Object, Object> recipes = new HashMap<>();
		
		for(ArcWelderRecipe recipe : ArcWelderRecipes.recipes) {
			
			int size = recipe.ingredients.length + (recipe.fluid != null ? 1 : 0);
			Object[] array = new Object[size];
			
			for(int i = 0; i < recipe.ingredients.length; i++) {
				array[i] = recipe.ingredients[i];
			}
			
			if(recipe.fluid != null) array[size - 1] = ItemFluidIcon.make(recipe.fluid);
			
			recipes.put(array, recipe.output);
		}
		
		return recipes;
	}
	
	public static ArcWelderRecipe getRecipe(ItemStack... inputs) {
		
		outer:
		for(ArcWelderRecipe recipe : ArcWelderRecipes.recipes) {

			List<AStack> recipeList = new ArrayList<>();
			for(AStack ingredient : recipe.ingredients) recipeList.add(ingredient);
			
			for (ItemStack inputStack : inputs) {
				
				if(inputStack != null) {
					
					boolean hasMatch = false;
					for (AStack recipeStack : recipeList) {
						if(recipeStack.matchesRecipe(inputStack, true) && inputStack.stackSize >= recipeStack.stacksize) {
							hasMatch = true;
							recipeList.remove(recipeStack);
							break;
						}
					}

					if(!hasMatch) {
						continue outer;
					}
				}
			}
			
			if(recipeList.isEmpty()) return recipe;
		}
		
		return null;
	}

	@Override
	public String getFileName() {
		return "hbmArcWelder.json";
	}

	@Override
	public Object getRecipeObject() {
		return ArcWelderRecipes.recipes;
	}

	@Override
	public void deleteRecipes() {
		ArcWelderRecipes.recipes.clear();
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;
		
		AStack[] inputs = SerializableRecipe.readAStackArray(obj.get("inputs").getAsJsonArray());
		FluidStack fluid = obj.has("fluid") ? SerializableRecipe.readFluidStack(obj.get("fluid").getAsJsonArray()) : null;
		ItemStack output = SerializableRecipe.readItemStack(obj.get("output").getAsJsonArray());
		int duration = obj.get("duration").getAsInt();
		long consumption = obj.get("consumption").getAsLong();
		
		ArcWelderRecipes.recipes.add(new ArcWelderRecipe(output, duration, consumption, fluid, inputs));
	}

	@Override
	public void writeRecipe(Object obj, JsonWriter writer) throws IOException {
		ArcWelderRecipe recipe = (ArcWelderRecipe) obj;
		
		writer.name("inputs").beginArray();
		for(AStack aStack : recipe.ingredients) {
			SerializableRecipe.writeAStack(aStack, writer);
		}
		writer.endArray();
		
		if(recipe.fluid != null) {
			writer.name("fluid");
			SerializableRecipe.writeFluidStack(recipe.fluid, writer);
		}
		
		writer.name("output");
		SerializableRecipe.writeItemStack(recipe.output, writer);

		writer.name("duration").value(recipe.duration);
		writer.name("consumption").value(recipe.consumption);
	}
	
	public static class ArcWelderRecipe {
		
		public AStack[] ingredients;
		public FluidStack fluid;
		public ItemStack output;
		public int duration;
		public long consumption;
		
		public ArcWelderRecipe(ItemStack output, int duration, long consumption, FluidStack fluid, AStack... ingredients) {
			this.ingredients = ingredients;
			this.fluid = fluid;
			this.output = output;
			this.duration = duration;
			this.consumption = consumption;
		}
		
		public ArcWelderRecipe(ItemStack output, int duration, long consumption, AStack... ingredients) {
			this(output, duration, consumption, null, ingredients);
		}
	}
}
