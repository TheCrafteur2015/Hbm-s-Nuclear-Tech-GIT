package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.util.Tuple.Pair;

import net.minecraft.item.ItemStack;

public class CyclotronRecipes extends SerializableRecipe {
	
	public static HashMap<Pair<ComparableStack, AStack>, Pair<ItemStack, Integer>> recipes = new HashMap();

	@Override
	public void registerDefaults() {

		/// LITHIUM START ///
		int liA = 50;

		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_lithium), new OreDictStack("dustLithium"), new ItemStack(ModItems.powder_beryllium), liA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_lithium), new OreDictStack("dustBeryllium"), new ItemStack(ModItems.powder_boron), liA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_lithium), new OreDictStack("dustBoron"), new ItemStack(ModItems.powder_coal), liA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_lithium), new OreDictStack("dustNetherQuartz"), new ItemStack(ModItems.powder_fire), liA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_lithium), new OreDictStack("dustPhosphorus"), new ItemStack(ModItems.sulfur), liA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_lithium), new OreDictStack("dustIron"), new ItemStack(ModItems.powder_cobalt), liA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_lithium), new ComparableStack(ModItems.powder_strontium), new ItemStack(ModItems.powder_zirconium), liA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_lithium), new OreDictStack("dustGold"), new ItemStack(ModItems.ingot_mercury), liA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_lithium), new OreDictStack("dustPolonium"), new ItemStack(ModItems.powder_astatine), liA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_lithium), new OreDictStack("dustLanthanium"), new ItemStack(ModItems.powder_cerium), liA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_lithium), new OreDictStack("dustActinium"), new ItemStack(ModItems.powder_thorium), liA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_lithium), new OreDictStack(OreDictManager.U.dust()), new ItemStack(ModItems.powder_neptunium), liA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_lithium), new OreDictStack(OreDictManager.NP237.dust()), new ItemStack(ModItems.powder_plutonium), liA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_lithium), new ComparableStack(ModItems.powder_reiium), new ItemStack(ModItems.powder_weidanium), liA);
		/// LITHIUM END ///

		/// BERYLLIUM START ///
		int beA = 25;

		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_beryllium), new OreDictStack("dustLithium"), new ItemStack(ModItems.powder_boron), beA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_beryllium), new OreDictStack("dustNetherQuartz"), new ItemStack(ModItems.sulfur), beA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_beryllium), new OreDictStack("dustTitanium"), new ItemStack(ModItems.powder_iron), beA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_beryllium), new OreDictStack("dustCobalt"), new ItemStack(ModItems.powder_copper), beA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_beryllium), new ComparableStack(ModItems.powder_strontium), new ItemStack(ModItems.powder_niobium), beA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_beryllium), new ComparableStack(ModItems.powder_cerium), new ItemStack(ModItems.powder_neodymium), beA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_beryllium), new OreDictStack("dustThorium"), new ItemStack(ModItems.powder_uranium), beA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_beryllium), new ComparableStack(ModItems.powder_weidanium), new ItemStack(ModItems.powder_australium), beA);
		/// BERYLLIUM END ///
		
		/// CARBON START ///
		int caA = 10;

		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_carbon), new OreDictStack("dustBoron"), new ItemStack(ModItems.powder_aluminium), caA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_carbon), new OreDictStack("dustSulfur"), new ItemStack(ModItems.powder_titanium), caA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_carbon), new OreDictStack("dustTitanium"), new ItemStack(ModItems.powder_cobalt), caA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_carbon), new ComparableStack(ModItems.powder_caesium), new ItemStack(ModItems.powder_lanthanium), caA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_carbon), new ComparableStack(ModItems.powder_neodymium), new ItemStack(ModItems.powder_gold), caA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_carbon), new ComparableStack(ModItems.ingot_mercury), new ItemStack(ModItems.powder_polonium), caA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_carbon), new OreDictStack(OreDictManager.PB.dust()), new ItemStack(ModItems.powder_ra226),caA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_carbon), new ComparableStack(ModItems.powder_astatine), new ItemStack(ModItems.powder_actinium), caA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_carbon), new ComparableStack(ModItems.powder_australium), new ItemStack(ModItems.powder_verticium), caA);
		/// CARBON END ///
		
		/// COPPER START ///
		int coA = 15;
		
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_copper), new OreDictStack("dustBeryllium"), new ItemStack(ModItems.powder_quartz), coA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_copper), new OreDictStack("dustCoal"), new ItemStack(ModItems.powder_bromine), coA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_copper), new OreDictStack("dustTitanium"), new ItemStack(ModItems.powder_strontium), coA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_copper), new OreDictStack("dustIron"), new ItemStack(ModItems.powder_niobium), coA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_copper), new ComparableStack(ModItems.powder_bromine), new ItemStack(ModItems.powder_iodine), coA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_copper), new ComparableStack(ModItems.powder_strontium), new ItemStack(ModItems.powder_neodymium), coA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_copper), new ComparableStack(ModItems.powder_niobium), new ItemStack(ModItems.powder_caesium), coA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_copper), new ComparableStack(ModItems.powder_iodine), new ItemStack(ModItems.powder_polonium), coA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_copper), new ComparableStack(ModItems.powder_caesium), new ItemStack(ModItems.powder_actinium), coA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_copper), new OreDictStack("dustGold"), new ItemStack(ModItems.powder_uranium), coA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_copper), new ComparableStack(ModItems.powder_verticium), new ItemStack(ModItems.powder_unobtainium), coA);
		/// COPPER END ///

		/// PLUTONIUM START ///
		int plA = 100;
		
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_plutonium), new OreDictStack("dustPhosphorus"), new ItemStack(ModItems.powder_tennessine), plA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_plutonium), new OreDictStack(OreDictManager.PU.dust()), new ItemStack(ModItems.powder_tennessine), plA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_plutonium), new ComparableStack(ModItems.powder_tennessine), new ItemStack(ModItems.powder_reiium), plA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_plutonium), new ComparableStack(ModItems.pellet_charged), new ItemStack(ModItems.nugget_schrabidium), 1000);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_plutonium), new ComparableStack(ModItems.powder_unobtainium), new ItemStack(ModItems.powder_daffergon), plA);
		CyclotronRecipes.makeRecipe(new ComparableStack(ModItems.part_plutonium), new ComparableStack(ModItems.cell_antimatter), new ItemStack(ModItems.cell_anti_schrabidium), 0);
		/// PLUTONIUM END ///
		
		///TODO: fictional elements
	}
	
	private static void makeRecipe(ComparableStack part, AStack in, ItemStack out, int amat) {
		CyclotronRecipes.recipes.put(new Pair(part, in), new Pair(out, amat));
	}
	
	public static Object[] getOutput(ItemStack stack, ItemStack box) {
		
		if(stack == null || stack.getItem() == null || box == null)
			return null;

		ComparableStack boxStack = new ComparableStack(box).makeSingular();
		ComparableStack comp = new ComparableStack(stack).makeSingular();
		
		//boo hoo we iterate over a hash map, cry me a river
		for(Entry<Pair<ComparableStack, AStack>, Pair<ItemStack, Integer>> entry : CyclotronRecipes.recipes.entrySet()) {
			
			if(entry.getKey().getKey().isApplicable(boxStack) && entry.getKey().getValue().isApplicable(comp)) {
				return new Object[] { entry.getValue().getKey().copy(), entry.getValue().getValue() };
			}
		}
		
		//there's literally 0 reason why this doesn't work yet it refuses, fuck this
		
		/*Pair<ItemStack, Integer> output = recipes.get(new Pair(boxStack, comp));
		
		if(output != null) {
			return new Object[] { output.getKey().copy(), output.getValue() };
		}
		
		for(String name : ItemStackUtil.getOreDictNames(stack)) {
			OreDictStack ods = new OreDictStack(name);
			output = recipes.get(new Pair(new ComparableStack(ModItems.part_beryllium), new OreDictStack("dustCobalt")));
			
			if(output != null) {
				return new Object[] { output.getKey().copy(), output.getValue() };
			}
		}*/
		
		return null;
	}
	
	public static Map<Object[], Object> getRecipes() {
		
		Map<Object[], Object> map = new HashMap<>();
		
		for(Entry<Pair<ComparableStack, AStack>, Pair<ItemStack, Integer>> entry : CyclotronRecipes.recipes.entrySet()) {
			List<ItemStack> stack = entry.getKey().getValue().extractForNEI();
			
			for(ItemStack ingredient : stack) {
				map.put(new ItemStack[] { entry.getKey().getKey().toStack(), ingredient }, entry.getValue().getKey());
			}
		}
		
		return map;
	}

	@Override
	public String getFileName() {
		return "hbmCyclotron.json";
	}

	@Override
	public Object getRecipeObject() {
		return CyclotronRecipes.recipes;
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonArray particle = ((JsonObject)recipe).get("particle").getAsJsonArray();
		JsonArray input = ((JsonObject)recipe).get("input").getAsJsonArray();
		JsonArray output = ((JsonObject)recipe).get("output").getAsJsonArray();
		int antimatter = ((JsonObject)recipe).get("antimatter").getAsInt();
		ItemStack partStack = readItemStack(particle);
		AStack inStack = readAStack(input);
		ItemStack outStack = readItemStack(output);
		
		CyclotronRecipes.recipes.put(new Pair(new ComparableStack(partStack), inStack),  new Pair(outStack, antimatter));
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		try{
			Entry<Pair<ComparableStack, AStack>, Pair<ItemStack, Integer>> rec = (Entry<Pair<ComparableStack, AStack>, Pair<ItemStack, Integer>>) recipe;
			
			writer.name("particle");
			writeItemStack(rec.getKey().getKey().toStack(), writer);
			writer.name("input");
			writeAStack(rec.getKey().getValue(), writer);
			writer.name("output");
			writeItemStack(rec.getValue().getKey(), writer);
			writer.name("antimatter").value(rec.getValue().getValue());
			
		} catch(Exception ex) {
			MainRegistry.logger.error(ex);
			ex.printStackTrace();
		}
	}

	@Override
	public void deleteRecipes() {
		CyclotronRecipes.recipes.clear();
	}

	@Override
	public String getComment() {
		return "The particle item, while being an input, has to be defined as an item stack without ore dictionary support.";
	}
}
