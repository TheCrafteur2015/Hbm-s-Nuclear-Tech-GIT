package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.imc.IMCCrystallizer;
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
import com.hbm.items.ItemEnums.EnumPlantType;
import com.hbm.items.ItemEnums.EnumTarType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemChemicalDye.EnumChemDye;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.items.special.ItemBedrockOre.EnumBedrockOre;
import com.hbm.items.special.ItemPlasticScrap.ScrapType;
import com.hbm.main.MainRegistry;
import com.hbm.util.Tuple.Pair;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

//This time we're doing this right
//...right?
public class CrystallizerRecipes extends SerializableRecipe {
	
	//'Object' is either a ComparableStack or the key for the ore dict
	private static HashMap<Pair<Object, FluidType>, CrystallizerRecipe> recipes = new HashMap();

	@Override
	public void registerDefaults() {

		int baseTime = 600;
		int utilityTime = 100;
		FluidStack sulfur = new FluidStack(Fluids.SULFURIC_ACID, 500);

		CrystallizerRecipes.registerRecipe(OreDictManager.COAL.ore(),		new CrystallizerRecipe(ModItems.crystal_coal, baseTime));
		CrystallizerRecipes.registerRecipe(OreDictManager.IRON.ore(),		new CrystallizerRecipe(ModItems.crystal_iron, baseTime));
		CrystallizerRecipes.registerRecipe(OreDictManager.GOLD.ore(),		new CrystallizerRecipe(ModItems.crystal_gold, baseTime));
		CrystallizerRecipes.registerRecipe(OreDictManager.REDSTONE.ore(),	new CrystallizerRecipe(ModItems.crystal_redstone, baseTime));
		CrystallizerRecipes.registerRecipe(OreDictManager.LAPIS.ore(),		new CrystallizerRecipe(ModItems.crystal_lapis, baseTime));
		CrystallizerRecipes.registerRecipe(OreDictManager.DIAMOND.ore(),	new CrystallizerRecipe(ModItems.crystal_diamond, baseTime));
		CrystallizerRecipes.registerRecipe(OreDictManager.U.ore(),			new CrystallizerRecipe(ModItems.crystal_uranium, baseTime), sulfur);
		CrystallizerRecipes.registerRecipe(OreDictManager.TH232.ore(),		new CrystallizerRecipe(ModItems.crystal_thorium, baseTime), sulfur);
		CrystallizerRecipes.registerRecipe(OreDictManager.PU.ore(),		new CrystallizerRecipe(ModItems.crystal_plutonium, baseTime), sulfur);
		CrystallizerRecipes.registerRecipe(OreDictManager.TI.ore(),		new CrystallizerRecipe(ModItems.crystal_titanium, baseTime), sulfur);
		CrystallizerRecipes.registerRecipe(OreDictManager.S.ore(),			new CrystallizerRecipe(ModItems.crystal_sulfur, baseTime));
		CrystallizerRecipes.registerRecipe(OreDictManager.KNO.ore(),		new CrystallizerRecipe(ModItems.crystal_niter, baseTime));
		CrystallizerRecipes.registerRecipe(OreDictManager.CU.ore(),		new CrystallizerRecipe(ModItems.crystal_copper, baseTime));
		CrystallizerRecipes.registerRecipe(OreDictManager.W.ore(),			new CrystallizerRecipe(ModItems.crystal_tungsten, baseTime), sulfur);
		CrystallizerRecipes.registerRecipe(OreDictManager.AL.ore(),		new CrystallizerRecipe(ModItems.crystal_aluminium, baseTime));
		CrystallizerRecipes.registerRecipe(OreDictManager.F.ore(),			new CrystallizerRecipe(ModItems.crystal_fluorite, baseTime));
		CrystallizerRecipes.registerRecipe(OreDictManager.BE.ore(),		new CrystallizerRecipe(ModItems.crystal_beryllium, baseTime));
		CrystallizerRecipes.registerRecipe(OreDictManager.PB.ore(),		new CrystallizerRecipe(ModItems.crystal_lead, baseTime));
		CrystallizerRecipes.registerRecipe(OreDictManager.SA326.ore(),		new CrystallizerRecipe(ModItems.crystal_schrabidium, baseTime), sulfur);
		CrystallizerRecipes.registerRecipe(OreDictManager.LI.ore(),		new CrystallizerRecipe(ModItems.crystal_lithium, baseTime), sulfur);
		CrystallizerRecipes.registerRecipe(OreDictManager.STAR.ore(),		new CrystallizerRecipe(ModItems.crystal_starmetal, baseTime), sulfur);
		CrystallizerRecipes.registerRecipe(OreDictManager.CO.ore(),		new CrystallizerRecipe(ModItems.crystal_cobalt, baseTime), sulfur);
		
		CrystallizerRecipes.registerRecipe("oreRareEarth",	new CrystallizerRecipe(ModItems.crystal_rare, baseTime), sulfur);
		CrystallizerRecipes.registerRecipe("oreCinnabar",	new CrystallizerRecipe(ModItems.crystal_cinnebar, baseTime));
		
		CrystallizerRecipes.registerRecipe(new ComparableStack(ModBlocks.ore_nether_fire),	new CrystallizerRecipe(ModItems.crystal_phosphorus, baseTime));
		CrystallizerRecipes.registerRecipe(new ComparableStack(ModBlocks.ore_tikite),		new CrystallizerRecipe(ModItems.crystal_trixite, baseTime), sulfur);
		CrystallizerRecipes.registerRecipe(new ComparableStack(ModBlocks.gravel_diamond),	new CrystallizerRecipe(ModItems.crystal_diamond, baseTime));
		CrystallizerRecipes.registerRecipe(OreDictManager.SRN.ingot(),										new CrystallizerRecipe(ModItems.crystal_schraranium, baseTime));
		
		CrystallizerRecipes.registerRecipe("sand",				new CrystallizerRecipe(ModItems.ingot_fiberglass, utilityTime));
		CrystallizerRecipes.registerRecipe(OreDictManager.REDSTONE.block(),	new CrystallizerRecipe(ModItems.ingot_mercury, baseTime));
		CrystallizerRecipes.registerRecipe(OreDictManager.CINNABAR.crystal(),	new CrystallizerRecipe(new ItemStack(ModItems.ingot_mercury, 3), baseTime));
		CrystallizerRecipes.registerRecipe(OreDictManager.BORAX.dust(),		new CrystallizerRecipe(new ItemStack(ModItems.powder_boron_tiny, 3), baseTime), sulfur);
		CrystallizerRecipes.registerRecipe(OreDictManager.COAL.block(),		new CrystallizerRecipe(ModBlocks.block_graphite, baseTime));

		CrystallizerRecipes.registerRecipe(new ComparableStack(Blocks.cobblestone),			new CrystallizerRecipe(ModBlocks.reinforced_stone, utilityTime));
		CrystallizerRecipes.registerRecipe(new ComparableStack(ModBlocks.gravel_obsidian),	new CrystallizerRecipe(ModBlocks.brick_obsidian, utilityTime));
		CrystallizerRecipes.registerRecipe(new ComparableStack(Items.rotten_flesh),			new CrystallizerRecipe(Items.leather, utilityTime));
		CrystallizerRecipes.registerRecipe(new ComparableStack(ModItems.coal_infernal),		new CrystallizerRecipe(ModItems.solid_fuel, utilityTime));
		CrystallizerRecipes.registerRecipe(new ComparableStack(ModBlocks.stone_gneiss),		new CrystallizerRecipe(ModItems.powder_lithium, utilityTime));
		CrystallizerRecipes.registerRecipe(new ComparableStack(Items.dye, 1, 15),			new CrystallizerRecipe(new ItemStack(Items.slime_ball, 4), 20), new FluidStack(Fluids.SULFURIC_ACID, 250));
		CrystallizerRecipes.registerRecipe(new ComparableStack(Items.bone),					new CrystallizerRecipe(new ItemStack(Items.slime_ball, 16), 20), new FluidStack(Fluids.SULFURIC_ACID, 1_000));
		CrystallizerRecipes.registerRecipe(new ComparableStack(DictFrame.fromOne(ModItems.plant_item, EnumPlantType.MUSTARDWILLOW)), new CrystallizerRecipe(new ItemStack(ModItems.powder_cadmium), 100).setReq(10), new FluidStack(Fluids.RADIOSOLVENT, 250));
		CrystallizerRecipes.registerRecipe(new ComparableStack(DictFrame.fromOne(ModItems.powder_ash, EnumAshType.FULLERENE)), new CrystallizerRecipe(new ItemStack(ModItems.ingot_cft), baseTime).setReq(4), new FluidStack(Fluids.XYLENE, 1_000));
		
		CrystallizerRecipes.registerRecipe(OreDictManager.DIAMOND.dust(), 									new CrystallizerRecipe(Items.diamond, utilityTime));
		CrystallizerRecipes.registerRecipe(OreDictManager.EMERALD.dust(), 									new CrystallizerRecipe(Items.emerald, utilityTime));
		CrystallizerRecipes.registerRecipe(OreDictManager.LAPIS.dust(),									new CrystallizerRecipe(new ItemStack(Items.dye, 1, 4), utilityTime));
		CrystallizerRecipes.registerRecipe(new ComparableStack(ModItems.powder_semtex_mix),	new CrystallizerRecipe(ModItems.ingot_semtex, baseTime));
		CrystallizerRecipes.registerRecipe(new ComparableStack(ModItems.powder_desh_ready),	new CrystallizerRecipe(ModItems.ingot_desh, baseTime));
		CrystallizerRecipes.registerRecipe(new ComparableStack(ModItems.powder_meteorite),	new CrystallizerRecipe(ModItems.fragment_meteorite, utilityTime));
		CrystallizerRecipes.registerRecipe(OreDictManager.CD.dust(),										new CrystallizerRecipe(ModItems.ingot_rubber, baseTime), new FluidStack(Fluids.FISHOIL, 250));
		
		CrystallizerRecipes.registerRecipe(new ComparableStack(ModItems.meteorite_sword_treated),	new CrystallizerRecipe(ModItems.meteorite_sword_etched, baseTime));
		CrystallizerRecipes.registerRecipe(new ComparableStack(ModItems.powder_impure_osmiridium),	new CrystallizerRecipe(ModItems.crystal_osmiridium, baseTime), new FluidStack(Fluids.SCHRABIDIC, 1_000));
		
		for(int i = 0; i < ScrapType.values().length; i++) {
			CrystallizerRecipes.registerRecipe(new ComparableStack(ModItems.scrap_plastic, 1, i), new CrystallizerRecipe(new ItemStack(ModItems.circuit_star_piece, 1, i), baseTime));
		}

		FluidStack nitric = new FluidStack(Fluids.NITRIC_ACID, 500);
		FluidStack organic = new FluidStack(Fluids.SOLVENT, 500);
		FluidStack hiperf = new FluidStack(Fluids.RADIOSOLVENT, 500);
		
		int oreTime = 200;
		
		for(EnumBedrockOre ore : EnumBedrockOre.values()) {
			int i = ore.ordinal();

			CrystallizerRecipes.registerRecipe(new ComparableStack(ModItems.ore_centrifuged, 1, i),			new CrystallizerRecipe(new ItemStack(ModItems.ore_cleaned, 1, i), oreTime));
			CrystallizerRecipes.registerRecipe(new ComparableStack(ModItems.ore_separated, 1, i),			new CrystallizerRecipe(new ItemStack(ModItems.ore_purified, 1, i), oreTime), sulfur);
			CrystallizerRecipes.registerRecipe(new ComparableStack(ModItems.ore_separated, 1, i),			new CrystallizerRecipe(new ItemStack(ModItems.ore_nitrated, 1, i), oreTime), nitric);
			CrystallizerRecipes.registerRecipe(new ComparableStack(ModItems.ore_nitrocrystalline, 1, i),	new CrystallizerRecipe(new ItemStack(ModItems.ore_deepcleaned, 1, i), oreTime), organic);
			CrystallizerRecipes.registerRecipe(new ComparableStack(ModItems.ore_nitrocrystalline, 1, i),	new CrystallizerRecipe(new ItemStack(ModItems.ore_seared, 1, i), oreTime), hiperf);
		}
		
		FluidStack[] dyes = new FluidStack[] {new FluidStack(Fluids.WOODOIL, 100), new FluidStack(Fluids.FISHOIL, 100)};
		for(FluidStack dye : dyes) {
			CrystallizerRecipes.registerRecipe(OreDictManager.COAL.dust(),		new CrystallizerRecipe(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.BLACK, 4), 20), dye);
			CrystallizerRecipes.registerRecipe(OreDictManager.TI.dust(),		new CrystallizerRecipe(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.WHITE, 4), 20), dye);
			CrystallizerRecipes.registerRecipe(OreDictManager.IRON.dust(),		new CrystallizerRecipe(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.RED, 4), 20), dye);
			CrystallizerRecipes.registerRecipe(OreDictManager.W.dust(),		new CrystallizerRecipe(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.YELLOW, 4), 20), dye);
			CrystallizerRecipes.registerRecipe(OreDictManager.CU.dust(),		new CrystallizerRecipe(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.GREEN, 4), 20), dye);
			CrystallizerRecipes.registerRecipe(OreDictManager.CO.dust(),		new CrystallizerRecipe(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.BLUE, 4), 20), dye);
		}

		CrystallizerRecipes.registerRecipe(new ComparableStack(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRUDE)),		new CrystallizerRecipe(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.WAX), 20),	new FluidStack(Fluids.CHLORINE, 250));
		CrystallizerRecipes.registerRecipe(new ComparableStack(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRACK)),		new CrystallizerRecipe(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.WAX), 20),	new FluidStack(Fluids.CHLORINE, 100));
		CrystallizerRecipes.registerRecipe(new ComparableStack(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.PARAFFIN)),	new CrystallizerRecipe(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.WAX), 20),	new FluidStack(Fluids.CHLORINE, 100));
		CrystallizerRecipes.registerRecipe(new ComparableStack(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.WAX)), 		new CrystallizerRecipe(new ItemStack(ModItems.pellet_charged), 200), 				new FluidStack(Fluids.IONGEL, 500));
		CrystallizerRecipes.registerRecipe(new ComparableStack(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.PARAFFIN)), 	new CrystallizerRecipe(new ItemStack(ModItems.pill_red), 200), 						new FluidStack(Fluids.ESTRADIOL, 250));

		CrystallizerRecipes.registerRecipe(OreDictManager.KEY_SAND, new CrystallizerRecipe(Blocks.clay, 20), new FluidStack(Fluids.COLLOID, 1_000));
		CrystallizerRecipes.registerRecipe(new ComparableStack(ModBlocks.sand_quartz), new CrystallizerRecipe(new ItemStack(ModItems.ball_dynamite, 16), 20), new FluidStack(Fluids.NITROGLYCERIN, 1_000));
		CrystallizerRecipes.registerRecipe(OreDictManager.NETHERQUARTZ.dust(), new CrystallizerRecipe(new ItemStack(ModItems.ball_dynamite, 4), 20), new FluidStack(Fluids.NITROGLYCERIN, 250));
		
		/// COMPAT CERTUS QUARTZ ///
		List<ItemStack> quartz = OreDictionary.getOres("crystalCertusQuartz");
		if(quartz != null && !quartz.isEmpty()) {
			ItemStack qItem = quartz.get(0).copy();
			qItem.stackSize = 12;
			CrystallizerRecipes.registerRecipe("oreCertusQuartz", new CrystallizerRecipe(qItem, baseTime));
		}

		/// COMPAT WHITE PHOSPHORUS DUST ///
		List<ItemStack> dustWhitePhosphorus = OreDictionary.getOres(OreDictManager.P_WHITE.dust());
		if(dustWhitePhosphorus != null && !dustWhitePhosphorus.isEmpty()) {
			CrystallizerRecipes.registerRecipe(OreDictManager.P_WHITE.dust(), new CrystallizerRecipe(new ItemStack(ModItems.ingot_phosphorus), utilityTime), new FluidStack(Fluids.AROMATICS, 50));
		}
		
		if(!IMCCrystallizer.buffer.isEmpty()) {
			CrystallizerRecipes.recipes.putAll(IMCCrystallizer.buffer);
			MainRegistry.logger.info("Fetched " + IMCCrystallizer.buffer.size() + " IMC crystallizer recipes!");
			IMCCrystallizer.buffer.clear();
		}
	}
	
	public static CrystallizerRecipe getOutput(ItemStack stack, FluidType type) {
		
		if(stack == null || stack.getItem() == null)
			return null;
		
		ComparableStack comp = new ComparableStack(stack.getItem(), 1, stack.getItemDamage());
		Pair compKey = new Pair(comp, type);
		
		if(CrystallizerRecipes.recipes.containsKey(compKey))
			return CrystallizerRecipes.recipes.get(compKey);
		
		String[] dictKeys = comp.getDictKeys();
		
		for(String key : dictKeys) {
			
			Pair dictKey = new Pair(key, type);

			if(CrystallizerRecipes.recipes.containsKey(dictKey))
				return CrystallizerRecipes.recipes.get(dictKey);
		}
		
		return null;
	}

	public static HashMap getRecipes() {
		
		HashMap<Object, Object> recipes = new HashMap<>();
		
		for(Entry<Pair<Object, FluidType>, CrystallizerRecipe> entry : CrystallizerRecipes.recipes.entrySet()) {
			
			CrystallizerRecipe recipe = entry.getValue();
			
			Pair<Object, FluidType> key = entry.getKey();
			Object input = key.getKey();
			FluidType acid = key.getValue();
			
			if(input instanceof String) {
				OreDictStack stack = new OreDictStack((String) input, recipe.itemAmount);
				recipes.put(new Object[] {ItemFluidIcon.make(acid, recipe.acidAmount), stack}, recipe.output);
			} else {
				ComparableStack stack = ((ComparableStack) input);
				stack = (ComparableStack) stack.copy();
				stack.stacksize = recipe.itemAmount;
				if(stack.item == ModItems.scrap_plastic) continue;
				recipes.put(new Object[] {ItemFluidIcon.make(acid, recipe.acidAmount), stack}, recipe.output);
			}
		}
		
		return recipes;
	}
	
	public static void registerRecipe(Object input, CrystallizerRecipe recipe) {
		CrystallizerRecipes.registerRecipe(input, recipe, new FluidStack(Fluids.ACID, 500));
	}
	
	public static void registerRecipe(Object input, CrystallizerRecipe recipe, FluidStack stack) {
		recipe.acidAmount = stack.fill;
		CrystallizerRecipes.recipes.put(new Pair(input, stack.type), recipe);
	}
	
	public static class CrystallizerRecipe {
		public int acidAmount;
		public int itemAmount = 1;
		public int duration;
		public ItemStack output;
		
		public CrystallizerRecipe(Block output, int duration) { this(new ItemStack(output), duration); }
		public CrystallizerRecipe(Item output, int duration) { this(new ItemStack(output), duration); }
		
		public CrystallizerRecipe setReq(int amount) {
			this.itemAmount = amount;
			return this;
		}
		
		public CrystallizerRecipe(ItemStack output, int duration) {
			this.output = output;
			this.duration = duration;
			this.acidAmount = 500;
		}
	}

	@Override
	public String getFileName() {
		return "hbmCrystallizer.json";
	}

	@Override
	public Object getRecipeObject() {
		return CrystallizerRecipes.recipes;
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;

		ItemStack output = readItemStack(obj.get("output").getAsJsonArray());
		AStack input = readAStack(obj.get("input").getAsJsonArray());
		FluidStack fluid = readFluidStack(obj.get("fluid").getAsJsonArray());
		int duration = obj.get("duration").getAsInt();
		
		CrystallizerRecipe cRecipe = new CrystallizerRecipe(output, duration).setReq(input.stacksize);
		input.stacksize = 1;
		cRecipe.acidAmount = fluid.fill;
		if(input instanceof ComparableStack) {
			CrystallizerRecipes.recipes.put(new Pair(((ComparableStack) input), fluid.type), cRecipe);
		} else if(input instanceof OreDictStack) {
			CrystallizerRecipes.recipes.put(new Pair(((OreDictStack) input).name, fluid.type), cRecipe);
		}
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<Pair, CrystallizerRecipe> rec = (Entry<Pair, CrystallizerRecipe>) recipe;
		CrystallizerRecipe cRecipe = rec.getValue();
		Pair<Object, FluidType> pair = rec.getKey();
		AStack input = pair.getKey() instanceof String ? new OreDictStack((String )pair.getKey()) : ((ComparableStack) pair.getKey()).copy();
		input.stacksize = cRecipe.itemAmount;
		FluidStack fluid = new FluidStack(pair.value, cRecipe.acidAmount);

		writer.name("duration").value(cRecipe.duration);
		writer.name("fluid");
		writeFluidStack(fluid, writer);
		writer.name("input");
		writeAStack(input, writer);
		writer.name("output");
		writeItemStack(cRecipe.output, writer);
	}

	@Override
	public void deleteRecipes() {
		CrystallizerRecipes.recipes.clear();
	}

	@Override
	public String getComment() {
		return "The acidizer also supports stack size requirements for input items, eg. the cadmium recipe requires 10 willow leaves.";
	}
}
