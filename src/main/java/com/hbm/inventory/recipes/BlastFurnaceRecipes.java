package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.CheckForNull;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.handler.imc.IMCBlastFurnace;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.util.Tuple.Triplet;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Magic!
 * 
 * @author UFFR
 */
public class BlastFurnaceRecipes extends SerializableRecipe {

	private static final ArrayList<Triplet<Object, Object, ItemStack>> blastFurnaceRecipes = new ArrayList<>();
	private static final ArrayList<ComparableStack> hiddenRecipes = new ArrayList<>();

	@Override
	public void registerDefaults() {
		/* STEEL */
		BlastFurnaceRecipes.addRecipe(OreDictManager.IRON,			OreDictManager.COAL,										new ItemStack(ModItems.ingot_steel, 1));
		BlastFurnaceRecipes.addRecipe(OreDictManager.IRON,			OreDictManager.ANY_COKE,									new ItemStack(ModItems.ingot_steel, 1));
		BlastFurnaceRecipes.addRecipe(OreDictManager.IRON.ore(),	OreDictManager.COAL,										new ItemStack(ModItems.ingot_steel, 2));
		BlastFurnaceRecipes.addRecipe(OreDictManager.IRON.ore(),	OreDictManager.ANY_COKE,									new ItemStack(ModItems.ingot_steel, 3));
		BlastFurnaceRecipes.addRecipe(OreDictManager.IRON.ore(),	new ComparableStack(ModItems.powder_flux),	new ItemStack(ModItems.ingot_steel, 3));
		
		BlastFurnaceRecipes.addRecipe(OreDictManager.CU,									OreDictManager.REDSTONE,										new ItemStack(ModItems.ingot_red_copper, 2));
		BlastFurnaceRecipes.addRecipe(OreDictManager.STEEL,								OreDictManager.MINGRADE,										new ItemStack(ModItems.ingot_advanced_alloy, 2));
		BlastFurnaceRecipes.addRecipe(OreDictManager.W,									OreDictManager.COAL,											new ItemStack(ModItems.neutron_reflector, 2));
		BlastFurnaceRecipes.addRecipe(OreDictManager.W,									OreDictManager.ANY_COKE,										new ItemStack(ModItems.neutron_reflector, 2));
		BlastFurnaceRecipes.addRecipe(new ComparableStack(ModItems.canister_full, 1, Fluids.GASOLINE.getID()), "slimeball",	new ItemStack(ModItems.canister_napalm));
		BlastFurnaceRecipes.addRecipe(OreDictManager.W,									OreDictManager.SA326.nugget(),									new ItemStack(ModItems.ingot_magnetized_tungsten));
		BlastFurnaceRecipes.addRecipe(OreDictManager.STEEL,								OreDictManager.TC99.nugget(),									new ItemStack(ModItems.ingot_tcalloy));
		BlastFurnaceRecipes.addRecipe(OreDictManager.GOLD.plate(),							ModItems.plate_mixed,							new ItemStack(ModItems.plate_paa, 2));
		BlastFurnaceRecipes.addRecipe(OreDictManager.BIGMT,								ModItems.powder_meteorite,						new ItemStack(ModItems.ingot_starmetal, 2));
		BlastFurnaceRecipes.addRecipe(OreDictManager.CO,									ModBlocks.block_meteor,							new ItemStack(ModItems.ingot_meteorite));
		BlastFurnaceRecipes.addRecipe(ModItems.meteorite_sword_hardened,	OreDictManager.CO,												new ItemStack(ModItems.meteorite_sword_alloyed));
		BlastFurnaceRecipes.addRecipe(ModBlocks.block_meteor,				OreDictManager.CO,												new ItemStack(ModItems.ingot_meteorite));

		if(GeneralConfig.enableLBSM && GeneralConfig.enableLBSMSimpleChemsitry) {
			BlastFurnaceRecipes.addRecipe(ModItems.canister_empty, OreDictManager.COAL, new ItemStack(ModItems.canister_full, 1, Fluids.OIL.getID()));
		}

		if(!IMCBlastFurnace.buffer.isEmpty()) {
			BlastFurnaceRecipes.blastFurnaceRecipes.addAll(IMCBlastFurnace.buffer);
			MainRegistry.logger.info("Fetched " + IMCBlastFurnace.buffer.size() + " IMC blast furnace recipes!");
			IMCBlastFurnace.buffer.clear();
		}

		BlastFurnaceRecipes.hiddenRecipes.add(new ComparableStack(ModItems.meteorite_sword_alloyed));
	}

	private static void addRecipe(Object in1, Object in2, ItemStack out) {

		if(in1 instanceof Item) in1 = new ComparableStack((Item) in1);
		if(in1 instanceof Block) in1 = new ComparableStack((Block) in1);
		if(in2 instanceof Item) in2 = new ComparableStack((Item) in2);
		if(in2 instanceof Block) in2 = new ComparableStack((Block) in2);
		
		BlastFurnaceRecipes.blastFurnaceRecipes.add(new Triplet<>(in1, in2, out));
	}

	@CheckForNull
	public static ItemStack getOutput(ItemStack in1, ItemStack in2) {
		for(Triplet<Object, Object, ItemStack> recipe : BlastFurnaceRecipes.blastFurnaceRecipes) {
			AStack[] recipeItem1 = BlastFurnaceRecipes.getRecipeStacks(recipe.getX());
			AStack[] recipeItem2 = BlastFurnaceRecipes.getRecipeStacks(recipe.getY());

			if((BlastFurnaceRecipes.doStacksMatch(recipeItem1, in1) && BlastFurnaceRecipes.doStacksMatch(recipeItem2, in2)) || (BlastFurnaceRecipes.doStacksMatch(recipeItem2, in1) && BlastFurnaceRecipes.doStacksMatch(recipeItem1, in2))) {
				return recipe.getZ().copy();
			} else {
				continue;
			}
		}
		return null;
	}

	private static boolean doStacksMatch(AStack[] recipe, ItemStack in) {
		boolean flag = false;
		byte i = 0;
		while(!flag && i < recipe.length) {
			flag = recipe[i].matchesRecipe(in, true);
			i++;
		}
		return flag;
	}

	private static AStack[] getRecipeStacks(Object in) {
		
		AStack[] recipeItem1 = new AStack[0];
		
		if(in instanceof DictFrame) {
			DictFrame recipeItem = (DictFrame) in;
			recipeItem1 = new AStack[] { new OreDictStack(recipeItem.ingot()), new OreDictStack(recipeItem.plate()), new OreDictStack(recipeItem.gem()), new OreDictStack(recipeItem.dust()) };
		
		} else if(in instanceof AStack) {
			recipeItem1 = new AStack[] { (AStack) in };
		
		} else if(in instanceof String) {
			recipeItem1 = new AStack[] { new OreDictStack((String) in) };
		
		}/* else if(in instanceof List<?>) {
			List<?> oreList = (List<?>) in;
			recipeItem1 = new AStack[oreList.size()];
			for(int i = 0; i < oreList.size(); i++)
				recipeItem1[i] = new OreDictStack((String) oreList.get(i));
		
		}*/

		return recipeItem1;
	}

	
	public static Map<List<ItemStack>[], ItemStack> getRecipesForNEI() {
		HashMap<List<ItemStack>[], ItemStack> recipes = new HashMap<>();

		for(Triplet<Object, Object, ItemStack> recipe : BlastFurnaceRecipes.blastFurnaceRecipes) {
			if(!BlastFurnaceRecipes.hiddenRecipes.contains(new ComparableStack(recipe.getZ()))) {
				ItemStack nothing = new ItemStack(ModItems.nothing).setStackDisplayName("If you're reading this, an error has occured! Check the console.");
				List<ItemStack> in1 = new ArrayList<>();
				List<ItemStack> in2 = new ArrayList<>();
				in1.add(nothing);
				in2.add(nothing);

				for(AStack stack : BlastFurnaceRecipes.getRecipeStacks(recipe.getX())) {
					if(stack.extractForNEI().isEmpty())
						continue;
					else {
						in1.remove(nothing);
						in1.addAll(stack.extractForNEI());
						break;
					}
				}
				if(in1.contains(nothing)) {
					MainRegistry.logger.error("Blast furnace cannot compile recipes for NEI: apparent nonexistent item #1 in recipe for item: " + recipe.getZ().getDisplayName());
				}
				for(AStack stack : BlastFurnaceRecipes.getRecipeStacks(recipe.getY())) {
					if(stack.extractForNEI().isEmpty()) {
						continue;
					} else {
						in2.remove(nothing);
						in2.addAll(stack.extractForNEI());
						break;
					}
				}
				if(in2.contains(nothing)) {
					MainRegistry.logger.error("Blast furnace cannot compile recipes for NEI: apparent nonexistent item #2 in recipe for item: " + recipe.getZ().getDisplayName());
				}

				List<ItemStack>[] inputs = new List[2];
				inputs[0] = in1;
				inputs[1] = in2;
				recipes.put(inputs, recipe.getZ());
			}
		}
		return ImmutableMap.copyOf(recipes);
	}

	public static List<Triplet<AStack[], AStack[], ItemStack>> getRecipes() {
		List<Triplet<AStack[], AStack[], ItemStack>> subRecipes = new ArrayList<>();
		for(Triplet<Object, Object, ItemStack> recipe : BlastFurnaceRecipes.blastFurnaceRecipes) {
			subRecipes.add(new Triplet<>(BlastFurnaceRecipes.getRecipeStacks(recipe.getX()), BlastFurnaceRecipes.getRecipeStacks(recipe.getY()), recipe.getZ()));
		}
		return ImmutableList.copyOf(subRecipes);
	}

	@Override
	public String getFileName() {
		return "hbmBlastFurnace.json";
	}
	
	@Override
	public String getComment() {
		return "Inputs can use the unique 'dictframe' type which is an ore dictionary material suffix. The recipes will accept most ore dictionary entries equivalent to one ingot (gems, dust, plates, etc).";
	}

	@Override
	public Object getRecipeObject() {
		return BlastFurnaceRecipes.blastFurnaceRecipes;
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject rec = (JsonObject) recipe;
		
		ItemStack output = SerializableRecipe.readItemStack(rec.get("output").getAsJsonArray());
		
		Object input1 = null;
		Object input2 = null;
		
		JsonArray array1 = rec.get("input1").getAsJsonArray();
		if(array1.get(0).getAsString().equals("item")) input1 = SerializableRecipe.readAStack(array1);
		if(array1.get(0).getAsString().equals("dict")) input1 = ((OreDictStack) SerializableRecipe.readAStack(array1)).name;
		if(array1.get(0).getAsString().equals("dictframe")) input1 = BlastFurnaceRecipes.readDictFrame(array1);
		
		JsonArray array2 = rec.get("input2").getAsJsonArray();
		if(array2.get(0).getAsString().equals("item")) input2 = SerializableRecipe.readAStack(array2);
		if(array2.get(0).getAsString().equals("dict")) input2 = ((OreDictStack) SerializableRecipe.readAStack(array2)).name;
		if(array2.get(0).getAsString().equals("dictframe")) input2 = BlastFurnaceRecipes.readDictFrame(array2);
		
		if(input1 != null && input2 != null) {
			BlastFurnaceRecipes.addRecipe(input1, input2, output);
			
			if(rec.has("hidden") && rec.get("hidden").getAsBoolean()) {
				BlastFurnaceRecipes.hiddenRecipes.add(new ComparableStack(output));
			}
		}
	}

	
	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Triplet<Object, Object, ItemStack> rec = (Triplet<Object, Object, ItemStack>) recipe;
		writer.name("output");
		SerializableRecipe.writeItemStack(rec.getZ(), writer);
		
		writer.name("input1");
		if(rec.getX() instanceof ComparableStack) SerializableRecipe.writeAStack((ComparableStack) rec.getX(), writer);
		if(rec.getX() instanceof String) SerializableRecipe.writeAStack(new OreDictStack((String) rec.getX()), writer);
		if(rec.getX() instanceof DictFrame) BlastFurnaceRecipes.writeDictFrame((DictFrame) rec.getX(), writer);
		
		writer.name("input2");
		if(rec.getY() instanceof ComparableStack) SerializableRecipe.writeAStack((ComparableStack) rec.getY(), writer);
		if(rec.getY() instanceof String) SerializableRecipe.writeAStack(new OreDictStack((String) rec.getY()), writer);
		if(rec.getY() instanceof DictFrame) BlastFurnaceRecipes.writeDictFrame((DictFrame) rec.getY(), writer);
		
		if(BlastFurnaceRecipes.hiddenRecipes.contains(new ComparableStack(rec.getZ()))) {
			writer.name("hidden").value(true);
		}
	}
	
	public static void writeDictFrame(DictFrame frame, JsonWriter writer) throws IOException {
		writer.beginArray();
		writer.setIndent("");
		writer.value("dictframe");
		writer.value(frame.mats[0]);
		writer.endArray();
		writer.setIndent("  ");
	}
	
	public static DictFrame readDictFrame(JsonArray array) {
		return new DictFrame(array.get(1).getAsString());
	}

	@Override
	public void deleteRecipes() {
		BlastFurnaceRecipes.blastFurnaceRecipes.clear();
		BlastFurnaceRecipes.hiddenRecipes.clear();
	}
}
