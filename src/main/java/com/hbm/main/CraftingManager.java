package com.hbm.main;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockEnums.DecoCabinetEnum;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockConcreteColoredExt.EnumConcreteType;
import com.hbm.blocks.generic.BlockGenericStairs;
import com.hbm.blocks.generic.BlockMultiSlab;
import com.hbm.blocks.generic.BlockNTMFlower.EnumFlowerType;
import com.hbm.config.GeneralConfig;
import com.hbm.crafting.ArmorRecipes;
import com.hbm.crafting.ConsumableRecipes;
import com.hbm.crafting.MineralRecipes;
import com.hbm.crafting.PowderRecipes;
import com.hbm.crafting.RodRecipes;
import com.hbm.crafting.SmeltingRecipes;
import com.hbm.crafting.ToolRecipes;
import com.hbm.crafting.WeaponRecipes;
import com.hbm.crafting.handlers.CargoShellCraftingHandler;
import com.hbm.crafting.handlers.MKUCraftingHandler;
import com.hbm.crafting.handlers.RBMKFuelCraftingHandler;
import com.hbm.crafting.handlers.ScrapsCraftingHandler;
import com.hbm.crafting.handlers.ToolboxCraftingHandler;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.material.Mats;
import com.hbm.items.ItemAmmoEnums.Ammo50BMG;
import com.hbm.items.ItemAmmoEnums.Ammo5mm;
import com.hbm.items.ItemEnums.EnumLegendaryType;
import com.hbm.items.ItemEnums.EnumPlantType;
import com.hbm.items.ItemGenericPart.EnumPartType;
import com.hbm.items.ModItems;
import com.hbm.items.food.ItemConserve.EnumFoodType;
import com.hbm.items.machine.ItemBattery;
import com.hbm.items.special.ItemCircuitStarComponent.CircuitComponentType;
import com.hbm.items.special.ItemHolotapeImage.EnumHoloImage;
import com.hbm.items.special.ItemPlasticScrap.ScrapType;
import com.hbm.items.tool.ItemDrone.EnumDroneType;
import com.hbm.items.tool.ItemGuideBook.BookType;
import com.hbm.util.EnchantmentUtil;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class CraftingManager {
	
	public static void mainRegistry() {
		
		CraftingManager.AddCraftingRec();
		SmeltingRecipes.AddSmeltingRec();
		
		MineralRecipes.register();
		RodRecipes.register();
		ToolRecipes.register();
		ArmorRecipes.register();
		WeaponRecipes.register();
		ConsumableRecipes.register();
		PowderRecipes.register();
		
		GameRegistry.addRecipe(new RBMKFuelCraftingHandler());
		GameRegistry.addRecipe(new MKUCraftingHandler());
		GameRegistry.addRecipe(new ToolboxCraftingHandler());
		GameRegistry.addRecipe(new CargoShellCraftingHandler());
		GameRegistry.addRecipe(new ScrapsCraftingHandler());
		
		RecipeSorter.register("hbm:rbmk", RBMKFuelCraftingHandler.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		RecipeSorter.register("hbm:toolbox", ToolboxCraftingHandler.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		RecipeSorter.register("hbm:cargo", CargoShellCraftingHandler.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		RecipeSorter.register("hbm:scraps", ScrapsCraftingHandler.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		RecipeSorter.register("hbm:mku", MKUCraftingHandler.class, RecipeSorter.Category.SHAPED, "after:minecraft:shaped before:minecraft:shapeless");
	}

	public static void AddCraftingRec() {

		for(Object[] array : BlockMultiSlab.recipeGen) {
			CraftingManager.addRecipeAuto(new ItemStack((Block) array[1], 6, (int) array[2]), new Object[] { "###", '#', (Block) array[0] });
		}
		for(Object[] array : BlockGenericStairs.recipeGen) {
			CraftingManager.addRecipeAuto(new ItemStack((Block) array[2], 4), new Object[] { "#  ",  "## ",  "###", '#', new ItemStack((Block) array[0], 1, (int) array[1]) });
		}
		
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.redstone_sword, 1), new Object[] { "R", "R", "S", 'R', OreDictManager.REDSTONE.block(), 'S', OreDictManager.KEY_STICK });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.big_sword, 1), new Object[] { "QIQ", "QIQ", "GSG", 'G', Items.gold_ingot, 'S', OreDictManager.KEY_STICK, 'I', Items.iron_ingot, 'Q', Items.quartz});

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.board_copper, 1), new Object[] { "TTT", "TTT", 'T', OreDictManager.CU.plate() });
		CraftingManager.addRecipeAuto(Mats.MAT_IRON.make(ModItems.plate_cast), new Object[] { "BPB", "BPB", "BPB", 'B', ModItems.bolt_tungsten, 'P', OreDictManager.IRON.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.hazmat_cloth_red, 1), new Object[] { "C", "R", "C", 'C', ModItems.hazmat_cloth, 'R', OreDictManager.REDSTONE.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.hazmat_cloth_grey, 1), new Object[] { " P ", "ICI", " L ", 'C', ModItems.hazmat_cloth_red, 'P', OreDictManager.IRON.plate(), 'L', OreDictManager.PB.plate(), 'I', ModItems.plate_polymer });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.asbestos_cloth, 8), new Object[] { "SCS", "CPC", "SCS", 'S', Items.string, 'P', OreDictManager.BR.dust(), 'C', Blocks.wool });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.bolt_dura_steel, 4), new Object[] { "D", "D", 'D', OreDictManager.DURA.ingot()});
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.pipes_steel, 1), new Object[] { "B", "B", "B", 'B', OreDictManager.STEEL.block() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.bolt_tungsten, 4), new Object[] { "D", "D", 'D', OreDictManager.W.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.bolt_compound, 1), new Object[] { "PDP", "PTP", "PDP", 'D', ModItems.bolt_dura_steel, 'T', ModItems.bolt_tungsten, 'P', OreDictManager.TI.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.pellet_coal, 1), new Object[] { "PFP", "FOF", "PFP", 'P', OreDictManager.COAL.dust(), 'F', Items.flint, 'O', ModBlocks.gravel_obsidian });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.plate_polymer, 8), new Object[] { "DD", 'D', OreDictManager.ANY_PLASTIC.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.plate_polymer, 16), new Object[] { "DD", 'D', OreDictManager.FIBER.ingot()});
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.plate_polymer, 16), new Object[] { "DD", 'D', OreDictManager.ASBESTOS.ingot()});
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.plate_polymer, 4), new Object[] { "SWS", 'S', Items.string, 'W', Blocks.wool });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.plate_polymer, 4), new Object[] { "BB", 'B', "ingotBrick" });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.plate_polymer, 4), new Object[] { "BB", 'B', "ingotNetherBrick" });

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.marker_structure, 1), new Object[] { "L", "G", "R", 'L', OreDictManager.LAPIS.dust(), 'G', Items.glowstone_dust, 'R', Blocks.redstone_torch });

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.circuit_raw, 1), new Object[] { "A", "R", "S", 'S', OreDictManager.STEEL.plate(), 'R', OreDictManager.REDSTONE.dust(), 'A', ModItems.wire_aluminium });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.circuit_bismuth_raw, 1), new Object[] { "RPR", "ABA", "RPR", 'R', OreDictManager.REDSTONE.dust(), 'P', OreDictManager.ANY_PLASTIC.ingot(), 'A', (GeneralConfig.enable528 ? ModItems.circuit_tantalium : OreDictManager.ASBESTOS.ingot()), 'B', ModItems.ingot_bismuth });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.circuit_tantalium_raw, 1), new Object[] { "RWR", "PTP", "RWR", 'R', OreDictManager.REDSTONE.dust(), 'W', ModItems.wire_gold, 'P', OreDictManager.CU.plate(), 'T', OreDictManager.TA.nugget() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.circuit_targeting_tier1, 1), new Object[] { "CPC", 'C', ModItems.circuit_aluminium, 'P', OreDictManager.REDSTONE.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.circuit_targeting_tier2, 1), new Object[] { "CPC", 'C', ModItems.circuit_copper, 'P', OreDictManager.NETHERQUARTZ.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.circuit_targeting_tier3, 1), new Object[] { "CPC", 'C', ModItems.circuit_red_copper, 'P', OreDictManager.GOLD.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.circuit_targeting_tier4, 1), new Object[] { "CPC", 'C', ModItems.circuit_gold, 'P', OreDictManager.LAPIS.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.circuit_targeting_tier5, 1), new Object[] { "CPC", 'C', ModItems.circuit_schrabidium, 'P', OreDictManager.DIAMOND.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.circuit_targeting_tier6, 1), new Object[] { "P", "D", "C", 'C', ModItems.circuit_targeting_tier5, 'D', ModItems.battery_potatos, 'P', ModItems.powder_spark_mix });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.circuit_aluminium, 2), new Object[] { ModItems.circuit_targeting_tier1 });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.circuit_copper, 2), new Object[] { ModItems.circuit_targeting_tier2 });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.circuit_red_copper, 2), new Object[] { ModItems.circuit_targeting_tier3 });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.circuit_gold, 2), new Object[] { ModItems.circuit_targeting_tier4 });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.circuit_schrabidium, 2), new Object[] { ModItems.circuit_targeting_tier5 });

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.cell_empty, 6), new Object[] { " S ", "G G", " S ", 'S', OreDictManager.STEEL.plate(), 'G', OreDictManager.KEY_ANYPANE });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.cell_deuterium, 8), new Object[] { "DDD", "DTD", "DDD", 'D', ModItems.cell_empty, 'T', ModItems.mike_deut });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.particle_empty, 2), new Object[] { "STS", "G G", "STS", 'S', OreDictManager.STEEL.plate(), 'T', OreDictManager.W.ingot(), 'G', OreDictManager.KEY_ANYPANE });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.particle_copper, 1), new Object[] { ModItems.particle_empty, OreDictManager.CU.dust(), ModItems.pellet_charged });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.particle_lead, 1), new Object[] { ModItems.particle_empty, OreDictManager.PB.dust(), ModItems.pellet_charged });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.cell_antimatter, 1), new Object[] { ModItems.particle_aproton, ModItems.particle_aelectron, ModItems.cell_empty });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.particle_amat, 1), new Object[] { ModItems.particle_aproton, ModItems.particle_aelectron, ModItems.particle_empty });

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.canister_empty, 2), new Object[] { "S ", "AA", "AA", 'S', OreDictManager.STEEL.plate(), 'A', OreDictManager.AL.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gas_empty, 2), new Object[] { "S ", "AA", "AA", 'A', OreDictManager.STEEL.plate(), 'S', OreDictManager.CU.plate() });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.block_waste_painted, 1), new Object[] { OreDictManager.KEY_YELLOW, ModBlocks.block_waste });


		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ingot_aluminium, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_aluminium });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ingot_copper, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_copper });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ingot_tungsten, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_tungsten });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ingot_red_copper, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_red_copper });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ingot_advanced_alloy, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_advanced_alloy });
		CraftingManager.addRecipeAuto(new ItemStack(Items.gold_ingot, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_gold });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ingot_schrabidium, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_schrabidium });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ingot_magnetized_tungsten, 1), new Object[] { "###", "###", "###", '#', ModItems.wire_magnetized_tungsten });

		CraftingManager.addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.sugar, ModItems.powder_sawdust, ModItems.powder_sawdust, ModItems.powder_sawdust, ModItems.powder_sawdust, ModItems.powder_sawdust });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.sugar, ModItems.powder_sawdust, ModItems.powder_sawdust, Items.apple, Items.apple, Items.apple });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.sugar, ModItems.powder_sawdust, ModItems.powder_sawdust, Items.reeds, Items.reeds, Items.reeds });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.sugar, ModItems.powder_sawdust, ModItems.powder_sawdust, Items.rotten_flesh, Items.rotten_flesh, Items.rotten_flesh });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.sugar, ModItems.powder_sawdust, ModItems.powder_sawdust, Items.carrot, Items.carrot, Items.carrot, Items.carrot, Items.carrot, Items.carrot });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.sugar, ModItems.powder_sawdust, ModItems.powder_sawdust, Items.potato, Items.potato, Items.potato, Items.potato, Items.potato, Items.potato });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.sugar, ModItems.powder_sawdust, ModItems.powder_sawdust, OreDictManager.KEY_SAPLING, OreDictManager.KEY_SAPLING, OreDictManager.KEY_SAPLING, OreDictManager.KEY_SAPLING, OreDictManager.KEY_SAPLING, OreDictManager.KEY_SAPLING });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.sugar, ModItems.powder_sawdust, ModItems.powder_sawdust, OreDictManager.KEY_LEAVES, OreDictManager.KEY_LEAVES, OreDictManager.KEY_LEAVES, OreDictManager.KEY_LEAVES, OreDictManager.KEY_LEAVES, OreDictManager.KEY_LEAVES });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.sugar, ModItems.powder_sawdust, ModItems.powder_sawdust, Blocks.pumpkin });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.sugar, ModItems.powder_sawdust, ModItems.powder_sawdust, Blocks.melon_block });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.sugar, ModItems.powder_sawdust, ModItems.powder_sawdust, Items.wheat, Items.wheat, Items.wheat, Items.wheat, Items.wheat, Items.wheat });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { DictFrame.fromOne(ModBlocks.plant_flower, EnumFlowerType.WEED), DictFrame.fromOne(ModBlocks.plant_flower, EnumFlowerType.WEED), DictFrame.fromOne(ModBlocks.plant_flower, EnumFlowerType.WEED), DictFrame.fromOne(ModBlocks.plant_flower, EnumFlowerType.WEED), DictFrame.fromOne(ModBlocks.plant_flower, EnumFlowerType.WEED), DictFrame.fromOne(ModBlocks.plant_flower, EnumFlowerType.WEED) });

		//addRecipeAuto(new ItemStack(ModItems.part_lithium), new Object[] { "P", "D", "P", 'P', STEEL.plate(), 'D', LI.dust() });
		//addRecipeAuto(new ItemStack(ModItems.part_beryllium), new Object[] { "P", "D", "P", 'P', STEEL.plate(), 'D', BE.dust() });
		//addRecipeAuto(new ItemStack(ModItems.part_carbon), new Object[] { "P", "D", "P", 'P', STEEL.plate(), 'D', COAL.dust() });
		//addRecipeAuto(new ItemStack(ModItems.part_copper), new Object[] { "P", "D", "P", 'P', STEEL.plate(), 'D', CU.dust() });
		//addRecipeAuto(new ItemStack(ModItems.part_plutonium), new Object[] { "P", "D", "P", 'P', STEEL.plate(), 'D', "dustPlutonium" });

		//addRecipeAuto(new ItemStack(ModItems.pellet_rtg, 1), new Object[] { "IPI", "PPP", "IPI", 'I', IRON.plate(), 'P', "tinyPu238" });
		//addRecipeAuto(new ItemStack(ModItems.pellet_rtg_weak, 1), new Object[] { "IUI", "UPU", "IUI", 'I', IRON.plate(), 'P', "tinyPu238", 'U', "tinyU238" });
		//addRecipeAuto(new ItemStack(ModItems.tritium_deuterium_cake, 1), new Object[] { "DLD", "LTL", "DLD", 'L', "ingotLithium", 'D', ModItems.cell_deuterium, 'T', ModItems.cell_tritium });

		//addRecipeAuto(new ItemStack(ModItems.pellet_schrabidium, 1), new Object[] { "IPI", "PPP", "IPI", 'I', IRON.plate(), 'P', SA326.ingot() });
		//addRecipeAuto(new ItemStack(ModItems.pellet_hes, 1), new Object[] { "IPI", "PPP", "IPI", 'I', IRON.plate(), 'P', ModItems.ingot_hes });
		//addRecipeAuto(new ItemStack(ModItems.pellet_mes, 1), new Object[] { "IPI", "PPP", "IPI", 'I', IRON.plate(), 'P', ModItems.ingot_schrabidium_fuel });
		//addRecipeAuto(new ItemStack(ModItems.pellet_les, 1), new Object[] { "IPI", "PPP", "IPI", 'I', IRON.plate(), 'P', ModItems.ingot_les });
		//addRecipeAuto(new ItemStack(ModItems.pellet_beryllium, 1), new Object[] { "IPI", "PPP", "IPI", 'I', IRON.plate(), 'P', BE.ingot() });
		//addRecipeAuto(new ItemStack(ModItems.pellet_neptunium, 1), new Object[] { "IPI", "PPP", "IPI", 'I', IRON.plate(), 'P', "ingotNeptunium" });
		//addRecipeAuto(new ItemStack(ModItems.pellet_lead, 1), new Object[] { "IPI", "PPP", "IPI", 'I', IRON.plate(), 'P', PB.ingot() });
		//addRecipeAuto(new ItemStack(ModItems.pellet_advanced, 1), new Object[] { "IPI", "PPP", "IPI", 'I', IRON.plate(), 'P', ModItems.ingot_advanced_alloy });

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.coil_copper, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_red_copper, 'I', OreDictManager.IRON.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.coil_advanced_alloy, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_advanced_alloy, 'I', OreDictManager.IRON.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.coil_gold, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_gold, 'I', OreDictManager.IRON.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.coil_copper_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', OreDictManager.IRON.plate(), 'C', ModItems.coil_copper });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.coil_advanced_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', OreDictManager.IRON.plate(), 'C', ModItems.coil_advanced_alloy });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.coil_gold_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', OreDictManager.IRON.plate(), 'C', ModItems.coil_gold });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.coil_copper_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', OreDictManager.STEEL.plate(), 'C', ModItems.coil_copper });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.coil_advanced_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', OreDictManager.STEEL.plate(), 'C', ModItems.coil_advanced_alloy });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.coil_gold_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', OreDictManager.STEEL.plate(), 'C', ModItems.coil_gold });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.coil_tungsten, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_tungsten, 'I', OreDictManager.IRON.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.coil_magnetized_tungsten, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ModItems.wire_magnetized_tungsten, 'I', OreDictManager.IRON.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.tank_steel, 2), new Object[] { "STS", "S S", "STS", 'S', OreDictManager.STEEL.plate(), 'T', OreDictManager.TI.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.motor, 2), new Object[] { " R ", "ICI", "ITI", 'R', ModItems.wire_red_copper, 'T', ModItems.coil_copper_torus, 'I', OreDictManager.IRON.plate(), 'C', ModItems.coil_copper });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.motor, 2), new Object[] { " R ", "ICI", " T ", 'R', ModItems.wire_red_copper, 'T', ModItems.coil_copper_torus, 'I', OreDictManager.STEEL.plate(), 'C', ModItems.coil_copper });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.motor_desh, 1), new Object[] { "PCP", "DMD", "PCP", 'P', OreDictManager.ANY_PLASTIC.ingot(), 'C', ModItems.coil_gold_torus, 'D', OreDictManager.DESH.ingot(), 'M', ModItems.motor });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.motor_bismuth, 1), new Object[] { "BCB", "SDS", "BCB", 'B', OreDictManager.BI.nugget(), 'C', ModBlocks.hadron_coil_alloy, 'S', OreDictManager.STEEL.plateCast(), 'D', OreDictManager.DURA.ingot() });
		//addRecipeAuto(new ItemStack(ModItems.centrifuge_element, 1), new Object[] { " T ", "WTW", "RMR", 'R', ModItems.wire_red_copper, 'T', ModItems.tank_steel, 'M', ModItems.motor, 'W', ModItems.coil_tungsten });
		//addRecipeAuto(new ItemStack(ModItems.centrifuge_tower, 1), new Object[] { "LL", "EE", "EE", 'E', ModItems.centrifuge_element, 'L', KEY_BLUE });
		//addRecipeAuto(new ItemStack(ModItems.reactor_core, 1), new Object[] { "LNL", "N N", "LNL", 'N', getReflector(), 'L', PB.plate() });
		//addRecipeAuto(new ItemStack(ModItems.rtg_unit, 1), new Object[] { "TIT", "PCP", "TIT", 'T', ModItems.thermo_element, 'I', PB.ingot(), 'P', ModItems.board_copper, 'C', ModItems.circuit_copper });
		//addRecipeAuto(new ItemStack(ModItems.thermo_unit_empty, 1), new Object[] { "TTT", " S ", "P P", 'S', STEEL.ingot(), 'P', TI.plate(), 'T', ModItems.coil_copper_torus });
		//addRecipeAuto(new ItemStack(ModItems.levitation_unit, 1), new Object[] { "CSC", "TAT", "PSP", 'C', ModItems.coil_copper, 'S', ModItems.nugget_schrabidium, 'T', ModItems.coil_tungsten, 'P', TI.plate(), 'A', STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.deuterium_filter, 1), new Object[] { "TST", "SCS", "TST", 'T', OreDictManager.ANY_RESISTANTALLOY.ingot(), 'S', OreDictManager.S.dust(), 'C', ModItems.catalyst_clay });

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.hull_small_steel, 3), new Object[] { "PPP", "   ", "PPP", 'P', OreDictManager.STEEL.plate(), 'I', OreDictManager.STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.hull_small_aluminium, 3), new Object[] { "PPP", "   ", "PPP", 'P', OreDictManager.AL.plate(), 'I', OreDictManager.AL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.hull_big_steel, 1), new Object[] { "III", "   ", "III", 'P', OreDictManager.STEEL.plate(), 'I', OreDictManager.STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.hull_big_aluminium, 1), new Object[] { "III", "   ", "III", 'P', OreDictManager.AL.plate(), 'I', OreDictManager.AL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.hull_big_titanium, 1), new Object[] { "III", "   ", "III", 'P', OreDictManager.TI.plate(), 'I', OreDictManager.TI.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.fins_flat, 1), new Object[] { "IP", "PP", "IP", 'P', OreDictManager.STEEL.plate(), 'I', OreDictManager.STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.fins_small_steel, 1), new Object[] { " PP", "PII", " PP", 'P', OreDictManager.STEEL.plate(), 'I', OreDictManager.STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.fins_big_steel, 1), new Object[] { " PI", "III", " PI", 'P', OreDictManager.STEEL.plate(), 'I', OreDictManager.STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.fins_tri_steel, 1), new Object[] { " PI", "IIB", " PI", 'P', OreDictManager.STEEL.plate(), 'I', OreDictManager.STEEL.ingot(), 'B', OreDictManager.STEEL.block() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.fins_quad_titanium, 1), new Object[] { " PP", "III", " PP", 'P', OreDictManager.TI.plate(), 'I', OreDictManager.TI.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.sphere_steel, 1), new Object[] { "PIP", "I I", "PIP", 'P', OreDictManager.STEEL.plate(), 'I', OreDictManager.STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.pedestal_steel, 1), new Object[] { "P P", "P P", "III", 'P', OreDictManager.STEEL.plate(), 'I', OreDictManager.STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.lemon, 1), new Object[] { " D ", "DSD", " D ", 'D', OreDictManager.KEY_YELLOW, 'S', "stone" });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.blade_titanium, 2), new Object[] { "TP", "TP", "TT", 'P', OreDictManager.TI.plate(), 'T', OreDictManager.TI.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.turbine_titanium, 1), new Object[] { "BBB", "BSB", "BBB", 'B', ModItems.blade_titanium, 'S', OreDictManager.STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.rotor_steel, 3), new Object[] { "CCC", "SSS", "CCC", 'C', ModItems.coil_gold, 'S', OreDictManager.STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.generator_steel, 1), new Object[] { "RRR", "CCC", "SSS", 'C', ModItems.coil_gold_torus, 'S', OreDictManager.STEEL.ingot(), 'R', ModItems.rotor_steel });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.shimmer_head, 1), new Object[] { "SSS", "DTD", "SSS", 'S', OreDictManager.STEEL.ingot(), 'D', OreDictManager.DESH.block(), 'T', OreDictManager.W.block() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.shimmer_axe_head, 1), new Object[] { "PII", "PBB", "PII", 'P', OreDictManager.STEEL.plate(), 'B', OreDictManager.DESH.block(), 'I', OreDictManager.W.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.shimmer_handle, 1), new Object[] { "GP", "GP", "GP", 'G', OreDictManager.GOLD.plate(), 'P', OreDictManager.ANY_PLASTIC.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.shimmer_sledge, 1), new Object[] { "H", "G", "G", 'G', ModItems.shimmer_handle, 'H', ModItems.shimmer_head });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.shimmer_axe, 1), new Object[] { "H", "G", "G", 'G', ModItems.shimmer_handle, 'H', ModItems.shimmer_axe_head });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.definitelyfood, 1), new Object[] { "DDD", "SDS", "DDD", 'D', Blocks.dirt, 'S', OreDictManager.STEEL.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.turbine_tungsten, 1), new Object[] { "BBB", "BSB", "BBB", 'B', ModItems.blade_tungsten, 'S', OreDictManager.DURA.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ring_starmetal, 1), new Object[] { " S ", "S S", " S ", 'S', OreDictManager.STAR.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.flywheel_beryllium, 1), new Object[] { "IBI", "BTB", "IBI", 'B', OreDictManager.BE.block(), 'I', OreDictManager.IRON.plateCast(), 'T', ModItems.bolt_compound });

		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_poison), new Object[] { DictFrame.fromOne(ModBlocks.plant_flower, EnumFlowerType.NIGHTSHADE) });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.syringe_metal_stimpak), new Object[] { ModItems.syringe_metal_empty, Items.carrot, DictFrame.fromOne(ModBlocks.plant_flower, EnumFlowerType.FOXGLOVE) }); //xander root and broc flower
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.pill_herbal), new Object[] { OreDictManager.COAL.dust(), Items.poisonous_potato, Items.nether_wart, DictFrame.fromOne(ModBlocks.plant_flower, EnumFlowerType.FOXGLOVE) });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.plant_item, EnumPlantType.ROPE, 1), new Object[] { Items.string, Items.string, Items.string });
		CraftingManager.addRecipeAuto(DictFrame.fromOne(ModItems.plant_item, EnumPlantType.ROPE, 4), new Object[] { "W", "W", "W", 'W', DictFrame.fromOne(ModBlocks.plant_flower, EnumFlowerType.WEED) });
		CraftingManager.addShapelessAuto(new ItemStack(Items.string, 3), new Object[] { DictFrame.fromOne(ModBlocks.plant_flower, EnumFlowerType.WEED) });
		CraftingManager.addRecipeAuto(new ItemStack(Items.paper, 3), new Object[] { "SSS", 'S', ModItems.powder_sawdust });

		ItemStack infinity = new ItemStack(Items.enchanted_book);
		EnchantmentUtil.addEnchantment(infinity, Enchantment.infinity, 1);
		CraftingManager.addRecipeAuto(infinity, new Object[] { "SBS", "BDB", "SBS", 'S', ModItems.ammo_50bmg.stackFromEnum(Ammo50BMG.STAR), 'B', ModItems.ammo_5mm.stackFromEnum(Ammo5mm.STAR), 'D', ModItems.powder_magic });
		ItemStack unbreaking = new ItemStack(Items.enchanted_book);
		EnchantmentUtil.addEnchantment(unbreaking, Enchantment.unbreaking, 3);
		CraftingManager.addRecipeAuto(unbreaking, new Object[] { "SBS", "BDB", "SBS", 'S', OreDictManager.BIGMT.ingot(), 'B', ModItems.plate_armor_lunar, 'D', ModItems.powder_magic });
		ItemStack thorns = new ItemStack(Items.enchanted_book);
		EnchantmentUtil.addEnchantment(thorns, Enchantment.thorns, 3);
		CraftingManager.addRecipeAuto(thorns, new Object[] { "SBS", "BDB", "SBS", 'S', ModBlocks.barbed_wire, 'B', ModBlocks.spikes, 'D', ModItems.powder_magic });

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.wrench, 1), new Object[] { " S ", " IS", "I  ", 'S', OreDictManager.STEEL.ingot(), 'I', OreDictManager.IRON.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.wrench_flipped, 1), new Object[] { "S", "D", "W", 'S', Items.iron_sword, 'D', ModItems.ducttape, 'W', ModItems.wrench });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.memespoon, 1), new Object[] { "CGC", "PSP", "IAI", 'C', ModItems.powder_cloud, 'G', OreDictManager.TH232.block(), 'P', ModItems.photo_panel, 'S', ModItems.steel_shovel, 'I', ModItems.plate_polymer, 'A', "ingotAustralium" });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.cbt_device, 1), new Object[] { ModItems.bolt_tungsten, ModItems.wrench });

		CraftingManager.addShapelessAuto(new ItemStack(ModItems.toothpicks, 3), new Object[] { OreDictManager.KEY_STICK, OreDictManager.KEY_STICK, OreDictManager.KEY_STICK });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ducttape, 6), new Object[] { "FSF", "SPS", "FSF", 'F', Items.string, 'S', OreDictManager.KEY_SLIME, 'P', Items.paper });

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.radio_torch_sender, 4), new Object[] { "G", "R", "I", 'G', "dustGlowstone", 'R', Blocks.redstone_torch, 'I', OreDictManager.NETHERQUARTZ.gem() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.radio_torch_receiver, 4), new Object[] { "G", "R", "I", 'G', "dustGlowstone", 'R', Blocks.redstone_torch, 'I', OreDictManager.IRON.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.radio_torch_counter, 4), new Object[] { "G", "R", "I", 'G', "dustGlowstone", 'R', Blocks.redstone_torch, 'I', ModItems.circuit_aluminium });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.radio_telex, 2), new Object[] { "SCR", "W#W", "WWW", 'S', ModBlocks.radio_torch_sender, 'C', ModItems.crt_display, 'R', ModBlocks.radio_torch_receiver, 'W', OreDictManager.KEY_PLANKS, '#', ModItems.circuit_aluminium });
		
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.conveyor, 16), new Object[] { "LLL", "I I", "LLL", 'L', Items.leather, 'I', OreDictManager.IRON.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.conveyor, 16), new Object[] { "RSR", "I I", "RSR", 'I', OreDictManager.IRON.ingot(), 'R', DictFrame.fromOne(ModItems.plant_item, EnumPlantType.ROPE), 'S', OreDictManager.IRON.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.conveyor, 64), new Object[] { "LLL", "I I", "LLL", 'L', OreDictManager.RUBBER.ingot(), 'I', OreDictManager.IRON.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.conveyor_express, 8), new Object[] { "CCC", "CLC", "CCC", 'C', ModBlocks.conveyor, 'L', Fluids.LUBRICANT.getDict(1_000) });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.conveyor_double, 3), new Object[] { "CPC", "CPC", "CPC", 'C', ModBlocks.conveyor, 'P', OreDictManager.IRON.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.conveyor_triple, 3), new Object[] { "CPC", "CPC", "CPC", 'C', ModBlocks.conveyor_double, 'P', OreDictManager.STEEL.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.conveyor_chute, 3), new Object[] { "IGI", "IGI", "ICI" , 'I', OreDictManager.IRON.ingot(), 'G', ModBlocks.steel_grate, 'C', ModBlocks.conveyor });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.conveyor_lift, 3), new Object[] { "IGI", "IGI", "ICI" , 'I', OreDictManager.IRON.ingot(), 'G', ModBlocks.chain, 'C', ModBlocks.conveyor });

		//addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_difurnace_off), 1), new Object[] { "T T", "PHP", "TFT", 'T', W.ingot(), 'P', ModItems.board_copper, 'H', Blocks.hopper, 'F', Blocks.furnace });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_difurnace_extension, 1), new Object[] { " C ", "BGB", "BGB", 'C', OreDictManager.CU.plate(), 'B', ModItems.ingot_firebrick, 'G', ModBlocks.steel_grate });
		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_uf6_tank), 1), new Object[] { "WTW", "WTW", "SRS", 'S', OreDictManager.IRON.plate(), 'W', ModItems.coil_tungsten, 'T', ModItems.tank_steel, 'W', ModItems.coil_tungsten,'R', OreDictManager.MINGRADE.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_puf6_tank), 1), new Object[] { "WTW", "WTW", "SRS", 'S', OreDictManager.STEEL.plate(), 'W', ModItems.coil_tungsten, 'T', ModItems.tank_steel, 'W', ModItems.coil_tungsten,'R', OreDictManager.MINGRADE.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_nuke_furnace_off), 1), new Object[] { "SSS", "LFL", "CCC", 'S', OreDictManager.STEEL.plate(), 'C', ModItems.board_copper, 'L', OreDictManager.PB.plate(), 'F', Item.getItemFromBlock(Blocks.furnace) });
		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_electric_furnace_off), 1), new Object[] { "BBB", "WFW", "RRR", 'B', OreDictManager.BE.ingot(), 'R', ModItems.coil_tungsten, 'W', ModItems.board_copper, 'F', Item.getItemFromBlock(Blocks.furnace) });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_arc_furnace_off, 1), new Object[] { "ITI", "PFP", "ITI", 'I', OreDictManager.W.ingot(), 'T', ModBlocks.machine_transformer, 'P', ModItems.board_copper, 'F', Blocks.furnace });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.red_wire_coated, 16), new Object[] { "WRW", "RIR", "WRW", 'W', ModItems.plate_polymer, 'I', OreDictManager.MINGRADE.ingot(), 'R', ModItems.wire_red_copper });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.red_cable_paintable, 16), new Object[] { "WRW", "RIR", "WRW", 'W', OreDictManager.STEEL.plate(), 'I', OreDictManager.MINGRADE.ingot(), 'R', ModItems.wire_red_copper });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.cable_switch, 1), new Object[] { "S", "W", 'S', Blocks.lever, 'W', ModBlocks.red_wire_coated });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.cable_detector, 1), new Object[] { "S", "W", 'S', OreDictManager.REDSTONE.dust(), 'W', ModBlocks.red_wire_coated });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.cable_diode, 1), new Object[] { " Q ", "CAC", " Q ", 'Q', OreDictManager.NETHERQUARTZ.gem(), 'C', ModBlocks.red_cable, 'A', OreDictManager.AL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_detector, 1), new Object[] { "IRI", "CTC", "IRI", 'I', ModItems.plate_polymer, 'R', OreDictManager.REDSTONE.dust(), 'C', ModItems.wire_red_copper, 'T', ModItems.coil_tungsten });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.red_cable, 16), new Object[] { " W ", "RRR", " W ", 'W', ModItems.plate_polymer, 'R', ModItems.wire_red_copper });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.red_cable_classic, 1), new Object[] { ModBlocks.red_cable });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.red_cable, 1), new Object[] { ModBlocks.red_cable_classic });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.red_cable_gauge), new Object[] { ModBlocks.red_wire_coated, OreDictManager.STEEL.ingot(), ModItems.circuit_aluminium });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.red_connector, 4), new Object[] { "C", "I", "S", 'C', ModItems.coil_copper, 'I', ModItems.plate_polymer, 'S', OreDictManager.STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.red_pylon, 4), new Object[] { "CWC", "PWP", " T ", 'C', ModItems.coil_copper, 'W', OreDictManager.KEY_PLANKS, 'P', ModItems.plate_polymer, 'T', ModBlocks.red_wire_coated });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_battery_potato, 1), new Object[] { "PCP", "WRW", "PCP", 'P', ItemBattery.getEmptyBattery(ModItems.battery_potato), 'C', OreDictManager.CU.ingot(), 'R', OreDictManager.REDSTONE.block(), 'W', OreDictManager.KEY_PLANKS });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.capacitor_bus, 1), new Object[] { "PIP", "PIP", "PIP", 'P', ModItems.plate_polymer, 'I', OreDictManager.MINGRADE.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.capacitor_copper, 1), new Object[] { "PPP", "PCP", "WWW", 'P', OreDictManager.STEEL.plate(), 'C', OreDictManager.CU.block(), 'W', OreDictManager.KEY_PLANKS });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.capacitor_gold, 1), new Object[] { "PPP", "ICI", "WWW", 'P', OreDictManager.STEEL.plate(), 'I', OreDictManager.ANY_PLASTIC.ingot(), 'C', OreDictManager.GOLD.block(), 'W', OreDictManager.STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.capacitor_niobium, 1), new Object[] { "PPP", "ICI", "WWW", 'P', OreDictManager.STEEL.plate(), 'I', OreDictManager.RUBBER.ingot(), 'C', OreDictManager.NB.block(), 'W', OreDictManager.STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.capacitor_tantalium, 1), new Object[] { "PPP", "ICI", "WWW", 'P', OreDictManager.STEEL.plate(), 'I', OreDictManager.ANY_RESISTANTALLOY.ingot(), 'C', OreDictManager.TA.block(), 'W', OreDictManager.STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.capacitor_schrabidate, 1), new Object[] { "PPP", "ICI", "WWW", 'P', OreDictManager.STEEL.plate(), 'I', OreDictManager.ANY_RESISTANTALLOY.ingot(), 'C', OreDictManager.SBD.block(), 'W', OreDictManager.STEEL.ingot() });
		//addRecipeAuto(new ItemStack(ModBlocks.machine_coal_off, 1), new Object[] { "STS", "SCS", "SFS", 'S', STEEL.ingot(), 'T', ModItems.tank_steel, 'C', MINGRADE.ingot(), 'F', Blocks.furnace });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_wood_burner, 1), new Object[] { "PPP", "CFC", "I I" , 'P', OreDictManager.STEEL.plate528(), 'C', ModItems.coil_copper, 'I', OreDictManager.IRON.ingot(), 'F', Blocks.furnace});
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_boiler_off, 1), new Object[] { "SPS", "TFT", "SPS", 'S', OreDictManager.STEEL.ingot(), 'P', ModItems.board_copper, 'T', ModItems.tank_steel, 'F', Blocks.furnace });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_boiler_electric_off, 1), new Object[] { "SPS", "TFT", "SPS", 'S', OreDictManager.DESH.ingot(), 'P', ModItems.board_copper, 'T', ModItems.tank_steel, 'F', ModBlocks.machine_electric_furnace_off });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_turbine, 1), new Object[] { "SMS", "PTP", "SMS", 'S', OreDictManager.STEEL.ingot(), 'T', ModItems.turbine_titanium, 'M', ModItems.coil_copper, 'P', OreDictManager.ANY_PLASTIC.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_converter_he_rf, 1), new Object[] { "SSS", "CRB", "SSS", 'S', OreDictManager.STEEL.ingot(), 'C', ModItems.coil_copper, 'R', ModItems.coil_copper_torus, 'B', OreDictManager.REDSTONE.block() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_converter_rf_he, 1), new Object[] { "SSS", "BRC", "SSS", 'S', OreDictManager.BE.ingot(), 'C', ModItems.coil_copper, 'R', ModItems.coil_copper_torus, 'B', OreDictManager.REDSTONE.block() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.crate_template, 1), new Object[] { "IPI", "P P", "IPI", 'I', OreDictManager.IRON.ingot(), 'P', Items.paper });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.crate_iron, 1), new Object[] { "PPP", "I I", "III", 'P', OreDictManager.IRON.plate(), 'I', OreDictManager.IRON.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.crate_steel, 1), new Object[] { "PPP", "I I", "III", 'P', OreDictManager.STEEL.plate(), 'I', OreDictManager.STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.crate_desh, 1), new Object[] { " D ", "DSD", " D ", 'D', ModItems.plate_desh, 'S', ModBlocks.crate_steel });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.crate_tungsten, 1), new Object[] { "BPB", "PCP", "BPB", 'B', OreDictManager.W.block(), 'P', ModItems.board_copper, 'C', ModBlocks.crate_steel });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.safe, 1), new Object[] { "LAL", "ACA", "LAL", 'L', OreDictManager.PB.plate(), 'A', OreDictManager.ALLOY.plate(), 'C', ModBlocks.crate_steel });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.mass_storage, 1, 0), new Object[] { "ICI", "CLC", "ICI", 'I', OreDictManager.TI.ingot(), 'C', ModBlocks.crate_steel, 'L', ModItems.circuit_copper });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.mass_storage, 1, 1), new Object[] { "PCP", "PMP", "PPP", 'P', OreDictManager.DESH.ingot(), 'C', ModItems.circuit_red_copper, 'M', new ItemStack(ModBlocks.mass_storage, 1, 0) });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.mass_storage, 1, 2), new Object[] { "PCP", "PMP", "PPP", 'P', OreDictManager.ANY_RESISTANTALLOY.ingot(), 'C', ModItems.circuit_gold, 'M', new ItemStack(ModBlocks.mass_storage, 1, 1) });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.mass_storage, 1, 3), new Object[] { "PPP", "PIP", "PPP", 'P', OreDictManager.KEY_PLANKS, 'I', OreDictManager.IRON.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_autocrafter, 1), new Object[] { "SCS", "MWM", "SCS", 'S', OreDictManager.STEEL.plate(), 'C', ModItems.circuit_copper, 'M', ModItems.motor, 'W', Blocks.crafting_table });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_waste_drum, 1), new Object[] { "LRL", "BRB", "LRL", 'L', OreDictManager.PB.ingot(), 'B', Blocks.iron_bars, 'R', ModItems.rod_quad_empty });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_press, 1), new Object[] { "IRI", "IPI", "IBI", 'I', OreDictManager.IRON.ingot(), 'R', Blocks.furnace, 'B', OreDictManager.IRON.block(), 'P', Blocks.piston });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_siren, 1), new Object[] { "SIS", "ICI", "SRS", 'S', OreDictManager.STEEL.plate(), 'I', ModItems.plate_polymer, 'C', ModItems.circuit_copper, 'R', OreDictManager.REDSTONE.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_microwave, 1), new Object[] { "III", "SGM", "IDI", 'I', ModItems.plate_polymer, 'S', OreDictManager.STEEL.plate(), 'G', OreDictManager.KEY_ANYPANE, 'M', ModItems.magnetron, 'D', ModItems.motor });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_solar_boiler), new Object[] { "SHS", "DHD", "SHS", 'S', OreDictManager.STEEL.ingot(), 'H', ModItems.hull_big_steel, 'D', OreDictManager.KEY_BLACK });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.solar_mirror, 3), new Object[] { "AAA", " B ", "SSS", 'A', OreDictManager.AL.plate(), 'B', ModBlocks.steel_beam, 'S', OreDictManager.STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.anvil_iron, 1), new Object[] { "III", " B ", "III", 'I', OreDictManager.IRON.ingot(), 'B', OreDictManager.IRON.block() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.anvil_lead, 1), new Object[] { "III", " B ", "III", 'I', OreDictManager.PB.ingot(), 'B', OreDictManager.PB.block() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.anvil_murky, 1), new Object[] { "UUU", "UAU", "UUU", 'U', ModItems.undefined, 'A', ModBlocks.anvil_steel });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_fraction_tower), new Object[] { "H", "G", "H", 'H', OreDictManager.STEEL.plateWelded(), 'G', ModBlocks.steel_grate });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.fraction_spacer), new Object[] { "BHB", 'H', ModItems.hull_big_steel, 'B', Blocks.iron_bars });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.furnace_iron), new Object[] { "III", "IFI", "BBB", 'I', OreDictManager.IRON.ingot(), 'F', Blocks.furnace, 'B', Blocks.stonebrick });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_mixer), new Object[] { "PIP", "GCG", "PMP", 'P', OreDictManager.STEEL.plate(), 'I', OreDictManager.DURA.ingot(), 'G', OreDictManager.KEY_ANYPANE, 'C', ModItems.circuit_copper, 'M', ModItems.motor });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.fan), new Object[] { "BPB", "PRP", "BPB", 'B', ModItems.bolt_tungsten, 'P', OreDictManager.IRON.plate(), 'R', OreDictManager.REDSTONE.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.piston_inserter), new Object[] { "ITI", "TPT", "ITI", 'P', DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_PNEUMATIC), 'I', OreDictManager.IRON.plate(), 'T', ModItems.bolt_tungsten });
		
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.muffler, 1), new Object[] { "III", "IWI", "III", 'I', ModItems.plate_polymer, 'W', Blocks.wool });

		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.factory_titanium_hull), 8), new Object[] { "PIP", "I I", "PIP", 'P', OreDictManager.TI.plate(), 'I', OreDictManager.TI.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.factory_advanced_hull), 8), new Object[] { "PIP", "I I", "PIP", 'P', OreDictManager.ALLOY.plate(), 'I', OreDictManager.ALLOY.ingot() });

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.arc_electrode, 1), new Object[] { "C", "T", "C", 'C', OreDictManager.GRAPHITE.ingot(), 'T', ModItems.bolt_tungsten });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.arc_electrode_desh, 1), new Object[] { "C", "T", "C", 'C', OreDictManager.DESH.dust(), 'T', ModItems.arc_electrode });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.detonator, 1), new Object[] { " W", "SC", "CE", 'S', OreDictManager.STEEL.plate(), 'W', ModItems.wire_red_copper, 'C', ModItems.circuit_red_copper, 'E', OreDictManager.STEEL.ingot() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.detonator_multi, 1), new Object[] { ModItems.detonator, ModItems.circuit_targeting_tier3 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.detonator_laser, 1), new Object[] { "RRD", "PIC", "  P", 'P', OreDictManager.STEEL.plate(), 'R', OreDictManager.REDSTONE.dust(), 'C', ModItems.circuit_targeting_tier3, 'D', OreDictManager.DIAMOND.gem(), 'I', OreDictManager.STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.detonator_laser, 1), new Object[] { "RRD", "PIC", "  P", 'P', OreDictManager.STEEL.plate(), 'R', OreDictManager.REDSTONE.dust(), 'C', ModItems.circuit_targeting_tier3, 'D', OreDictManager.EMERALD.gem(), 'I', OreDictManager.STEEL.ingot() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.detonator_deadman, 1), new Object[] { ModItems.detonator, ModItems.defuser, ModItems.ducttape });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.detonator_de, 1), new Object[] { "T", "D", "T", 'T', Blocks.tnt, 'D', ModItems.detonator_deadman });

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.singularity, 1), new Object[] { "ESE", "SBS", "ESE", 'E', OreDictManager.EUPH.nugget(), 'S', ModItems.cell_anti_schrabidium, 'B', OreDictManager.SA326.block() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.singularity_counter_resonant, 1), new Object[] { "CTC", "TST", "CTC", 'C', OreDictManager.CMB.plate(), 'T', OreDictManager.MAGTUNG.ingot(), 'S', ModItems.singularity });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.singularity_super_heated, 1), new Object[] { "CTC", "TST", "CTC", 'C', OreDictManager.ALLOY.plate(), 'T', ModItems.powder_power, 'S', ModItems.singularity });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.black_hole, 1), new Object[] { "SSS", "SCS", "SSS", 'C', ModItems.singularity, 'S', ModItems.crystal_xen });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.crystal_xen, 1), new Object[] { "EEE", "EIE", "EEE", 'E', ModItems.powder_power, 'I', OreDictManager.EUPH.ingot() });

		CraftingManager.addShapelessAuto(new ItemStack(ModItems.fuse, 1), new Object[] { OreDictManager.STEEL.plate(), ModItems.plate_polymer, ModItems.wire_tungsten });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.overfuse, 1), new Object[] { ModItems.bolt_tungsten, OreDictManager.NP237.dust(), OreDictManager.I.dust(), OreDictManager.TH232.dust(), OreDictManager.AT.dust(), OreDictManager.ND.dust(), ModItems.board_copper, ModItems.black_hole, OreDictManager.CS.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.overfuse, 1), new Object[] { ModItems.bolt_tungsten, OreDictManager.ST.dust(), OreDictManager.BR.dust(), OreDictManager.CO.dust(), OreDictManager.TS.dust(), OreDictManager.NB.dust(), ModItems.board_copper, ModItems.black_hole, OreDictManager.CE.dust() });
		
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.blades_steel, 1), new Object[] { " P ", "PIP", " P ", 'P', OreDictManager.STEEL.plate(), 'I', OreDictManager.STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.blades_titanium, 1), new Object[] { " P ", "PIP", " P ", 'P', OreDictManager.TI.plate(), 'I', OreDictManager.TI.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.blades_advanced_alloy, 1), new Object[] { " P ", "PIP", " P ", 'P', OreDictManager.ALLOY.plate(), 'I', OreDictManager.ALLOY.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.blades_desh, 1), new Object[] { " S ", "PBP", " S ", 'S', OreDictManager.BIGMT.plate(), 'P', ModItems.plate_desh, 'B', ModItems.blades_advanced_alloy }); //4 desh ingots still needed to do anything
		
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.blades_steel, 1), new Object[] { "PIP", 'P', OreDictManager.STEEL.plate(), 'I', new ItemStack(ModItems.blades_steel, 1, OreDictionary.WILDCARD_VALUE) });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.blades_titanium, 1), new Object[] { "PIP", 'P', OreDictManager.TI.plate(), 'I', new ItemStack(ModItems.blades_titanium, 1, OreDictionary.WILDCARD_VALUE) });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.blades_advanced_alloy, 1), new Object[] { "PIP", 'P', OreDictManager.ALLOY.plate(), 'I', new ItemStack(ModItems.blades_advanced_alloy, 1, OreDictionary.WILDCARD_VALUE) });
		
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.laser_crystal_co2, 1), new Object[] { "QDQ", "NCN", "QDQ", 'Q', ModBlocks.glass_quartz, 'D', OreDictManager.DESH.ingot(), 'N', OreDictManager.NB.ingot(), 'C', new ItemStack(ModItems.fluid_tank_full, 1, Fluids.CARBONDIOXIDE.getID()) });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.laser_crystal_bismuth, 1), new Object[] {"QUQ", "BCB", "QTQ", 'Q', ModBlocks.glass_quartz, 'U', OreDictManager.U.ingot(), 'T', OreDictManager.TH232.ingot(), 'B', ModItems.nugget_bismuth, 'C', ModItems.crystal_rare });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.laser_crystal_cmb, 1), new Object[] {"QBQ", "CSC", "QBQ", 'Q', ModBlocks.glass_quartz, 'B', OreDictManager.CMB.ingot(), 'C', OreDictManager.SBD.ingot(), 'S', ModItems.cell_anti_schrabidium });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.laser_crystal_dnt, 1), new Object[] {"QDQ", "SBS", "QDQ", 'Q', ModBlocks.glass_quartz, 'D', OreDictManager.DNT.ingot(), 'B', ModItems.egg_balefire, 'S', ModItems.powder_spark_mix });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.laser_crystal_digamma, 1), new Object[] {"QUQ", "UEU", "QUQ", 'Q', ModBlocks.glass_quartz, 'U', ModItems.undefined, 'E', ModItems.ingot_electronium } );

		Item[] bricks = new Item[] {Items.brick, Items.netherbrick};
		
		for(Item brick : bricks) {
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.stamp_stone_flat, 1), new Object[] { "III", "SSS", 'I', brick, 'S', "stone" });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.stamp_iron_flat, 1), new Object[] { "III", "SSS", 'I', brick, 'S', OreDictManager.IRON.ingot() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.stamp_steel_flat, 1), new Object[] { "III", "SSS", 'I', brick, 'S', OreDictManager.STEEL.ingot() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.stamp_titanium_flat, 1), new Object[] { "III", "SSS", 'I', brick, 'S', OreDictManager.TI.ingot() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.stamp_obsidian_flat, 1), new Object[] { "III", "SSS", 'I', brick, 'S', Blocks.obsidian });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.stamp_desh_flat, 1), new Object[] { "BDB", "DSD", "BDB", 'B', brick, 'D', OreDictManager.DESH.ingot(), 'S', OreDictManager.BIGMT.ingot() });
		}
		
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mechanism_revolver_1, 1), new Object[] { "ICI", "CAC", "ICI", 'I', OreDictManager.IRON.plate(), 'C', OreDictManager.CU.ingot(), 'A', OreDictManager.AL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mechanism_revolver_2, 1), new Object[] { "ATA", "TDT", "ATA", 'A', OreDictManager.ALLOY.plate(), 'T', OreDictManager.W.ingot(), 'D', OreDictManager.DURA.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mechanism_rifle_1, 1), new Object[] { "ICI", "MAM", "ICI", 'I', OreDictManager.IRON.plate(), 'C', OreDictManager.CU.ingot(), 'A', OreDictManager.AL.ingot(), 'M', ModItems.mechanism_revolver_1 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mechanism_rifle_2, 1), new Object[] { "ATA", "MDM", "ATA", 'A', OreDictManager.ALLOY.plate(), 'T', OreDictManager.W.ingot(), 'D', OreDictManager.DURA.ingot(), 'M', ModItems.mechanism_revolver_2 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mechanism_launcher_1, 1), new Object[] { "TTT", "SSS", "BBI", 'T', OreDictManager.TI.plate(), 'S', OreDictManager.STEEL.ingot(), 'B', ModItems.bolt_tungsten, 'I', OreDictManager.MINGRADE.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mechanism_launcher_2, 1), new Object[] { "TTT", "SSS", "BBI", 'T', OreDictManager.ALLOY.plate(), 'S', OreDictManager.ANY_PLASTIC.ingot(), 'B', ModItems.bolt_dura_steel, 'I', OreDictManager.DESH.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mechanism_special, 1), new Object[] { "PCI", "ISS", "PCI", 'P', ModItems.plate_desh, 'C', ModItems.coil_advanced_alloy, 'I', OreDictManager.STAR.ingot(), 'S', ModItems.circuit_targeting_tier3 });

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.watz_pump, 1), new Object[] { "MPM", "PCP", "PSP", 'M', ModItems.motor_desh, 'P', OreDictManager.ANY_RESISTANTALLOY.plateCast(), 'C', OreDictManager.KEY_CIRCUIT_BISMUTH, 'S', ModItems.pipes_steel });
		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.fwatz_cooler), 1), new Object[] { "IPI", "IPI", "IPI", 'I', OreDictManager.TI.ingot(), 'P', OreDictManager.TI.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.fwatz_tank), 1), new Object[] { "CGC", "GGG", "CGC", 'C', OreDictManager.CMB.plate(), 'G', OreDictManager.KEY_ANYPANE });
		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.fwatz_scaffold), 1), new Object[] { "IPI", "P P", "IPI", 'I', OreDictManager.W.ingot(), 'P', OreDictManager.getReflector() });

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.reinforced_stone, 4), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.cobblestone, 'B', Blocks.stone });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.brick_light, 4), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.fence, 'B', Blocks.brick_block });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.brick_asbestos, 2), new Object[] { " A ", "ABA", " A ", 'B', ModBlocks.brick_light, 'A', OreDictManager.ASBESTOS.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.concrete, 4), new Object[] { "CC", "CC", 'C', ModBlocks.concrete_smooth });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.concrete_pillar, 6), new Object[] { "CBC", "CBC", "CBC", 'C', ModBlocks.concrete_smooth, 'B', Blocks.iron_bars });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.brick_concrete, 4), new Object[] { " C ", "CBC", " C ", 'C', ModBlocks.concrete_smooth, 'B', Items.clay_ball });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.brick_concrete, 4), new Object[] { " C ", "CBC", " C ", 'C', ModBlocks.concrete, 'B', Items.clay_ball });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.brick_concrete_mossy, 8), new Object[] { "CCC", "CVC", "CCC", 'C', ModBlocks.brick_concrete, 'V', Blocks.vine });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.brick_concrete_cracked, 6), new Object[] { " C " , "C C", " C ", 'C', ModBlocks.brick_concrete });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.brick_concrete_broken, 6), new Object[] { " C " , "C C", " C ", 'C', ModBlocks.brick_concrete_cracked });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.ducrete, 4), new Object[] { "DD", "DD", 'D', ModBlocks.ducrete_smooth });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.brick_ducrete, 4), new Object[] {"CDC", "DLD", "CDC", 'D', ModBlocks.ducrete_smooth, 'C', Items.clay_ball, 'L', ModItems.plate_lead });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.brick_ducrete, 4), new Object[] {"CDC", "DLD", "CDC", 'D', ModBlocks.ducrete, 'C', Items.clay_ball, 'L', ModItems.plate_lead });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.reinforced_ducrete, 4), new Object[] {"DSD", "SUS", "DSD", 'D', ModBlocks.brick_ducrete, 'S', ModItems.plate_steel, 'U', OreDictManager.U238.billet() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.brick_obsidian, 4), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.iron_bars, 'B', Blocks.obsidian });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.meteor_polished, 4), new Object[] { "CC", "CC", 'C', ModBlocks.block_meteor_broken });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.meteor_pillar, 2), new Object[] { "C", "C", 'C', ModBlocks.meteor_polished });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.meteor_brick, 4), new Object[] { "CC", "CC", 'C', ModBlocks.meteor_polished });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.meteor_brick_mossy, 8), new Object[] { "CCC", "CVC", "CCC", 'C', ModBlocks.meteor_brick, 'V', Blocks.vine });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.meteor_brick_cracked, 6), new Object[] { " C " , "C C", " C ", 'C', ModBlocks.meteor_brick });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.meteor_battery, 1), new Object[] { "MSM", "MWM", "MSM", 'M', ModBlocks.meteor_polished, 'S', OreDictManager.STAR.block(), 'W', ModItems.wire_schrabidium });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.tile_lab, 4), new Object[] { "CBC", "CBC", "CBC", 'C', Items.brick, 'B', OreDictManager.ASBESTOS.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.tile_lab_cracked, 6), new Object[] { " C " , "C C", " C ", 'C', ModBlocks.tile_lab });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.tile_lab_broken, 6), new Object[] { " C " , "C C", " C ", 'C', ModBlocks.tile_lab_cracked });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.asphalt_light, 1), new Object[] { ModBlocks.asphalt, Items.glowstone_dust });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.asphalt, 1), new Object[] { ModBlocks.asphalt_light });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.block_niter_reinforced, 1), new Object[] { "TCT", "CNC", "TCT", 'T', OreDictManager.ANY_RESISTANTALLOY.ingot(), 'C', ModBlocks.concrete, 'N', OreDictManager.KNO.block() });
		
		String[] dyes = { "Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "LightGray", "Gray", "Pink", "Lime", "Yellow", "LightBlue", "Magenta", "Orange", "White" };
		
		for(int i = 0; i < 16; i++) {
			String dyeName = "dye" + dyes[15 - i];
			CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.concrete_colored, 8, i), new Object[] { "CCC", "CDC", "CCC", 'C', ModBlocks.concrete_smooth, 'D', dyeName });
		}
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth, 1), new Object[] { ModBlocks.concrete_colored });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth, 1), new Object[] { ModBlocks.concrete_colored_ext });
		
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.concrete_colored_ext, 6, EnumConcreteType.MACHINE.ordinal()), new Object[] { "CCC", "1 2", "CCC", 'C', ModBlocks.concrete_smooth, '1', OreDictManager.KEY_BROWN, '2', OreDictManager.KEY_GRAY });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.concrete_colored_ext, 6, EnumConcreteType.MACHINE_STRIPE.ordinal()), new Object[] { "CCC", "1 2", "CCC", 'C', ModBlocks.concrete_smooth, '1', OreDictManager.KEY_BROWN, '2', OreDictManager.KEY_BLACK });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.concrete_colored_ext, 6, EnumConcreteType.INDIGO.ordinal()), new Object[] { "CCC", "1 2", "CCC", 'C', ModBlocks.concrete_smooth, '1', OreDictManager.KEY_BLUE, '2', OreDictManager.KEY_PURPLE });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.concrete_colored_ext, 6, EnumConcreteType.PURPLE.ordinal()), new Object[] { "CCC", "1 2", "CCC", 'C', ModBlocks.concrete_smooth, '1', OreDictManager.KEY_PURPLE, '2', OreDictManager.KEY_PURPLE });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.concrete_colored_ext, 6, EnumConcreteType.PINK.ordinal()), new Object[] { "CCC", "1 2", "CCC", 'C', ModBlocks.concrete_smooth, '1', OreDictManager.KEY_PINK, '2', OreDictManager.KEY_RED });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.concrete_colored_ext, 6, EnumConcreteType.HAZARD.ordinal()), new Object[] { "CCC", "1 2", "CCC", 'C', ModBlocks.concrete_smooth, '1', OreDictManager.KEY_YELLOW, '2', OreDictManager.KEY_BLACK });

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.gneiss_tile, 4), new Object[] { "CC", "CC", 'C', ModBlocks.stone_gneiss });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.gneiss_brick, 4), new Object[] { "CC", "CC", 'C', ModBlocks.gneiss_tile });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.gneiss_chiseled, 1), new Object[] { ModBlocks.gneiss_tile });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.depth_brick, 4), new Object[] { "CC", "CC", 'C', ModBlocks.stone_depth });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.depth_tiles, 4), new Object[] { "CC", "CC", 'C', ModBlocks.depth_brick });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.depth_nether_brick, 4), new Object[] { "CC", "CC", 'C', ModBlocks.stone_depth_nether });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.depth_nether_tiles, 4), new Object[] { "CC", "CC", 'C', ModBlocks.depth_nether_brick });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.basalt_polished, 4), new Object[] { "CC", "CC", 'C', ModBlocks.basalt_smooth });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.basalt_brick, 4), new Object[] { "CC", "CC", 'C', ModBlocks.basalt_polished });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.basalt_tiles, 4), new Object[] { "CC", "CC", 'C', ModBlocks.basalt_brick });


		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_brick), 4), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.iron_bars, 'B', ModBlocks.brick_concrete });
		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.brick_compound), 4), new Object[] { "FBF", "BTB", "FBF", 'F', ModItems.bolt_tungsten, 'B', ModBlocks.reinforced_brick, 'T', OreDictManager.ANY_TAR.any() });
		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_glass), 4), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.iron_bars, 'B', Blocks.glass });
		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_glass_pane), 16), new Object[] { "   ", "GGG", "GGG", 'G', ModBlocks.reinforced_glass});
		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_laminate_pane), 16), new Object[] { "   ", "LLL", "LLL", 'L', ModBlocks.reinforced_laminate});
		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_light), 1), new Object[] { "FFF", "FBF", "FFF", 'F', Blocks.iron_bars, 'B', Blocks.glowstone });
		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_lamp_off), 1), new Object[] { "FFF", "FBF", "FFF", 'F', Blocks.iron_bars, 'B', Blocks.redstone_lamp });
		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_sand), 4), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.iron_bars, 'B', Blocks.sandstone });

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.lamp_tritium_green_off, 1), new Object[] { "GPG", "1T2", "GPG", 'G', OreDictManager.KEY_ANYGLASS, 'P', OreDictManager.P_RED.dust(), 'T', ModItems.cell_tritium, '1', "dustSulfur", '2', OreDictManager.CU.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.lamp_tritium_blue_off, 1), new Object[] { "GPG", "1T2", "GPG", 'G', OreDictManager.KEY_ANYGLASS, 'P',OreDictManager.P_RED.dust(), 'T', ModItems.cell_tritium, '1', OreDictManager.AL.dust(), '2', OreDictManager.ST.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.lantern, 1), new Object[] { "PGP", " S ", " S ", 'P', OreDictManager.KEY_ANYPANE, 'G', Items.glowstone_dust, 'S', ModBlocks.steel_beam });
		
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.barbed_wire, 16), new Object[] { "AIA", "I I", "AIA", 'A', ModItems.wire_aluminium, 'I', OreDictManager.IRON.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.barbed_wire_fire, 8), new Object[] { "BBB", "BIB", "BBB", 'B', ModBlocks.barbed_wire, 'I', OreDictManager.P_RED.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.barbed_wire_poison, 8), new Object[] { "BBB", "BIB", "BBB", 'B', ModBlocks.barbed_wire, 'I', ModItems.powder_poison });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.barbed_wire_acid, 8), new Object[] { "BBB", "BIB", "BBB", 'B', ModBlocks.barbed_wire, 'I', new ItemStack(ModItems.fluid_tank_full, 1, Fluids.ACID.getID()) });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.barbed_wire_wither, 8), new Object[] { "BBB", "BIB", "BBB", 'B', ModBlocks.barbed_wire, 'I', new ItemStack(Items.skull, 1, 1) });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.barbed_wire_ultradeath, 4), new Object[] { "BCB", "CIC", "BCB", 'B', ModBlocks.barbed_wire, 'C', ModItems.powder_cloud, 'I', ModItems.nuclear_waste });

		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.tape_recorder), 4), new Object[] { "TST", "SSS", 'T', OreDictManager.W.ingot(), 'S', OreDictManager.STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_poles), 16), new Object[] { "S S", "SSS", "S S", 'S', OreDictManager.STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.pole_top), 1), new Object[] { "T T", "TRT", "BBB", 'T', OreDictManager.W.ingot(), 'B', OreDictManager.BE.ingot(), 'R', OreDictManager.MINGRADE.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.pole_satellite_receiver), 1), new Object[] { "SS ", "SCR", "SS ", 'S', OreDictManager.STEEL.ingot(), 'C', ModItems.circuit_red_copper, 'R', ModItems.wire_red_copper });
		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_beam), 8), new Object[] { "S", "S", "S", 'S', OreDictManager.STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_wall), 4), new Object[] { "SSS", "SSS", 'S', OreDictManager.STEEL.ingot() });
		CraftingManager.addShapelessAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_corner)), new Object[] { ModBlocks.steel_wall, ModBlocks.steel_wall });
		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_roof), 2), new Object[] { "SSS", 'S', OreDictManager.STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_scaffold), 8), new Object[] { "SSS", " S ", "SSS", 'S', OreDictManager.STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_beam), 8), new Object[] { "S", "S", "S", 'S', ModBlocks.steel_scaffold });
		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.chain), 8), new Object[] { "S", "S", "S", 'S', ModBlocks.steel_beam });
		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_grate), 4), new Object[] { "SS", "SS", 'S', ModBlocks.steel_beam });
		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_grate_wide), 4), new Object[] { "SS", 'S', ModBlocks.steel_grate });
		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_grate), 1), new Object[] { "SS", 'S', ModBlocks.steel_grate_wide });
		

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.steel_scaffold, 8, 0), new Object[] { "SSS", "SDS", "SSS", 'S', ModBlocks.steel_scaffold, 'D', "dyeGray" });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.steel_scaffold, 8, 1), new Object[] { "SSS", "SDS", "SSS", 'S', ModBlocks.steel_scaffold, 'D', "dyeRed" });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.steel_scaffold, 8, 2), new Object[] { "SSS", "SDS", "SSS", 'S', ModBlocks.steel_scaffold, 'D', "dyeWhite" });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.steel_scaffold, 8, 3), new Object[] { "SSS", "SDS", "SSS", 'S', ModBlocks.steel_scaffold, 'D', "dyeYellow" });
		
		CraftingManager.reg2();
	}
	
	public static void reg2() {

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.stamp_357, 1), new Object[] { "RSR", "III", " C ", 'R', OreDictManager.REDSTONE.dust(), 'S', ModItems.stamp_iron_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_357 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.stamp_44, 1), new Object[] { "RSR", "III", " C ", 'R', OreDictManager.REDSTONE.dust(), 'S', ModItems.stamp_iron_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_44 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.stamp_9, 1), new Object[] { "RSR", "III", " C ", 'R', OreDictManager.REDSTONE.dust(), 'S', ModItems.stamp_iron_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_9 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.stamp_50, 1), new Object[] { "RSR", "III", " C ", 'R', OreDictManager.REDSTONE.dust(), 'S', ModItems.stamp_iron_flat, 'I', ModItems.plate_polymer, 'C', ModItems.casing_50 });

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.stamp_desh_357, 1), new Object[] { "RSR", "III", " C ", 'R', ModItems.ingot_dura_steel, 'S', ModItems.stamp_desh_flat, 'I', ModItems.ingot_saturnite, 'C', ModItems.casing_357 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.stamp_desh_44, 1), new Object[] { "RSR", "III", " C ", 'R', ModItems.ingot_dura_steel, 'S', ModItems.stamp_desh_flat, 'I', ModItems.ingot_saturnite, 'C', ModItems.casing_44 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.stamp_desh_9, 1), new Object[] { "RSR", "III", " C ", 'R', ModItems.ingot_dura_steel, 'S', ModItems.stamp_desh_flat, 'I', ModItems.ingot_saturnite, 'C', ModItems.casing_9 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.stamp_desh_50, 1), new Object[] { "RSR", "III", " C ", 'R', ModItems.ingot_dura_steel, 'S', ModItems.stamp_desh_flat, 'I', ModItems.ingot_saturnite, 'C', ModItems.casing_50 });
		
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.sat_dock, 1), new Object[] { "SSS", "PCP", 'S', OreDictManager.STEEL.ingot(), 'P', OreDictManager.ANY_PLASTIC.ingot(), 'C', ModBlocks.crate_iron });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.book_guide, 1), new Object[] { "IBI", "LBL", "IBI", 'B', Items.book, 'I', OreDictManager.KEY_BLACK, 'L', OreDictManager.KEY_BLUE });

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.rail_wood, 16), new Object[] { "S S", "SRS", "S S", 'S', Items.stick, 'R', DictFrame.fromOne(ModItems.plant_item, EnumPlantType.ROPE) });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.rail_narrow, 64), new Object[] { "S S", "S S", "S S", 'S', ModBlocks.steel_beam });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.rail_highspeed, 16), new Object[] { "S S", "SIS", "S S", 'S', OreDictManager.STEEL.ingot(), 'I', OreDictManager.IRON.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.rail_booster, 6), new Object[] { "S S", "CIC", "SRS", 'S', OreDictManager.STEEL.ingot(), 'I', OreDictManager.IRON.plate(), 'R', OreDictManager.MINGRADE.ingot(), 'C', ModItems.coil_copper });

		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.bomb_multi), 1), new Object[] { "AAD", "CHF", "AAD", 'A', ModItems.wire_aluminium, 'C', ModItems.circuit_aluminium, 'H', ModItems.hull_small_aluminium, 'F', ModItems.fins_quad_titanium, 'D', OreDictManager.KEY_WHITE });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_ice, 4), new Object[] { Items.snowball, OreDictManager.KNO.dust(), OreDictManager.REDSTONE.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_poison, 4), new Object[] { Items.spider_eye, OreDictManager.REDSTONE.dust(), OreDictManager.NETHERQUARTZ.gem() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.pellet_gas, 2), new Object[] { Items.water_bucket, "dustGlowstone", OreDictManager.STEEL.plate() });

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.flame_pony, 1), new Object[] { " O ", "DPD", " O ", 'D', "dyePink", 'O', OreDictManager.KEY_YELLOW, 'P', Items.paper });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.flame_conspiracy, 1), new Object[] { " S ", "STS", " S ", 'S', Fluids.KEROSENE.getDict(1000), 'T', OreDictManager.STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.flame_politics, 1), new Object[] { " I ", "IPI", " I ", 'P', Items.paper, 'I', OreDictManager.KEY_BLACK });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.flame_opinion, 1), new Object[] { " R ", "RPR", " R ", 'P', Items.paper, 'R', OreDictManager.KEY_RED });
		
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.solid_fuel_presto, 1), new Object[] { " P ", "SRS", " P ", 'P', Items.paper, 'S', ModItems.solid_fuel, 'R', OreDictManager.REDSTONE.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.solid_fuel_presto_triplet, 1), new Object[] { ModItems.solid_fuel_presto, ModItems.solid_fuel_presto, ModItems.solid_fuel_presto, ModItems.ball_dynamite });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.solid_fuel_presto_bf, 1), new Object[] { " P ", "SRS", " P ", 'P', Items.paper, 'S', ModItems.solid_fuel_bf, 'R', OreDictManager.REDSTONE.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.solid_fuel_presto_triplet_bf, 1), new Object[] { ModItems.solid_fuel_presto_bf, ModItems.solid_fuel_presto_bf, ModItems.solid_fuel_presto_bf, ModItems.ingot_c4 });
		
		CraftingManager.addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.flame_war), 1), new Object[] { "WHW", "CTP", "WOW", 'W', Item.getItemFromBlock(Blocks.planks), 'T', Item.getItemFromBlock(Blocks.tnt), 'H', ModItems.flame_pony, 'C', ModItems.flame_conspiracy, 'P', ModItems.flame_politics, 'O', ModItems.flame_opinion });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.det_cord, 4), new Object[] { " P ", "PGP", " P ", 'P', Items.paper, 'G', Items.gunpowder });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.det_charge, 1), new Object[] { "PDP", "DTD", "PDP", 'P', OreDictManager.STEEL.plate(), 'D', ModBlocks.det_cord, 'T', OreDictManager.ANY_PLASTICEXPLOSIVE.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.det_nuke, 1), new Object[] { "PDP", "DCD", "PDP", 'P', ModItems.plate_desh, 'D', ModBlocks.det_charge, 'C', ModItems.man_core });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.det_miner, 4), new Object[] { "FFF", "ITI", "ITI", 'F', Items.flint, 'I', OreDictManager.IRON.plate(), 'T', ModItems.ball_dynamite });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.det_miner, 12), new Object[] { "FFF", "ITI", "ITI", 'F', Items.flint, 'I', OreDictManager.STEEL.plate(), 'T', OreDictManager.ANY_PLASTICEXPLOSIVE.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.emp_bomb, 1), new Object[] { "LML", "LCL", "LML", 'L', OreDictManager.PB.plate(), 'M', ModItems.magnetron, 'C', ModItems.circuit_gold });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.charge_dynamite, 1), new Object[] { ModItems.stick_dynamite, ModItems.stick_dynamite, ModItems.stick_dynamite, ModItems.ducttape });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.charge_miner, 1), new Object[] { " F ", "FCF", " F ", 'F', Items.flint, 'C', ModBlocks.charge_dynamite });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.charge_semtex, 1), new Object[] { ModItems.stick_semtex, ModItems.stick_semtex, ModItems.stick_semtex, ModItems.ducttape });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.charge_c4, 1), new Object[] { ModItems.stick_c4, ModItems.stick_c4, ModItems.stick_c4, ModItems.ducttape });

		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_generic), new Object[] { " A ", "PRP", "PRP", 'A', ModItems.wire_aluminium, 'P', OreDictManager.AL.plate(), 'R', OreDictManager.REDSTONE.dust() });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced), new Object[] { " A ", "PSP", "PLP", 'A', ModItems.wire_red_copper, 'P', OreDictManager.CU.plate(), 'S', "sulfur", 'L', OreDictManager.PB.dust() });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced), new Object[] { " A ", "PLP", "PSP", 'A', ModItems.wire_red_copper, 'P', OreDictManager.CU.plate(), 'S', "sulfur", 'L', OreDictManager.PB.dust() });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced), new Object[] { " A ", "PSP", "PLP", 'A', ModItems.wire_red_copper, 'P', OreDictManager.CU.plate(), 'S', "dustSulfur", 'L', OreDictManager.PB.dust() });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced), new Object[] { " A ", "PLP", "PSP", 'A', ModItems.wire_red_copper, 'P', OreDictManager.CU.plate(), 'S', "dustSulfur", 'L', OreDictManager.PB.dust() });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_lithium), new Object[] { "A A", "PSP", "PLP", 'A', ModItems.wire_gold, 'P', OreDictManager.TI.plate(), 'S', OreDictManager.LI.dust(), 'L', OreDictManager.CO.dust() });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_lithium), new Object[] { "A A", "PLP", "PSP", 'A', ModItems.wire_gold, 'P', OreDictManager.TI.plate(), 'S', OreDictManager.LI.dust(), 'L', OreDictManager.CO.dust() });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium), new Object[] { " A ", "PNP", "PSP", 'A', ModItems.wire_schrabidium, 'P', OreDictManager.SA326.plate(), 'S', OreDictManager.SA326.dust(), 'N', OreDictManager.NP237.dust() });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium), new Object[] { " A ", "PSP", "PNP", 'A', ModItems.wire_schrabidium, 'P', OreDictManager.SA326.plate(), 'S', OreDictManager.SA326.dust(), 'N', OreDictManager.NP237.dust() });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark), new Object[] { "P", "S", "S", 'P', ModItems.plate_dineutronium, 'S', ModItems.powder_spark_mix });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_trixite), new Object[] { " A ", "PSP", "PTP", 'A', ModItems.wire_aluminium, 'P', OreDictManager.AL.plate(), 'S', ModItems.powder_power, 'T', ModItems.crystal_trixite });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_trixite), new Object[] { " A ", "PTP", "PSP", 'A', ModItems.wire_aluminium, 'P', OreDictManager.AL.plate(), 'S', ModItems.powder_power, 'T', ModItems.crystal_trixite });
		CraftingManager.addRecipeAuto(ItemBattery.getFullBattery(ModItems.energy_core), new Object[] { "PCW", "TRD", "PCW", 'P', OreDictManager.ALLOY.plate(), 'C', ModItems.coil_advanced_alloy, 'W', ModItems.wire_advanced_alloy, 'R', ModItems.cell_tritium, 'D', ModItems.cell_deuterium, 'T', OreDictManager.W.ingot() });
		CraftingManager.addRecipeAuto(ItemBattery.getFullBattery(ModItems.energy_core), new Object[] { "PCW", "TDR", "PCW", 'P', OreDictManager.ALLOY.plate(), 'C', ModItems.coil_advanced_alloy, 'W', ModItems.wire_advanced_alloy, 'R', ModItems.cell_tritium, 'D', ModItems.cell_deuterium, 'T', OreDictManager.W.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.hev_battery, 4), new Object[] { " W ", "IEI", "ICI", 'W', ModItems.wire_gold, 'I', ModItems.plate_polymer, 'E', OreDictManager.REDSTONE.dust(), 'C', OreDictManager.CO.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.hev_battery, 4), new Object[] { " W ", "ICI", "IEI", 'W', ModItems.wire_gold, 'I', ModItems.plate_polymer, 'E', OreDictManager.REDSTONE.dust(), 'C', OreDictManager.CO.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.hev_battery, 1), new Object[] { ModBlocks.hev_battery });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.hev_battery, 1), new Object[] { ModItems.hev_battery });

		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_red_cell), new Object[] { "WBW", "PBP", "WBW", 'W', ModItems.wire_aluminium, 'P', OreDictManager.AL.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_generic) });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell), new Object[] { "WBW", "PBP", "WBW", 'W', ModItems.wire_red_copper, 'P', OreDictManager.CU.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced) });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell), new Object[] { "WBW", "PBP", "WBW", 'W', ModItems.wire_gold, 'P', OreDictManager.TI.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_lithium) });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell), new Object[] { "WBW", "PBP", "WBW", 'W', ModItems.wire_schrabidium, 'P', OreDictManager.SA326.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_schrabidium) });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_red_cell_6), new Object[] { "BBB", "WPW", "BBB", 'W', ModItems.wire_aluminium, 'P', OreDictManager.AL.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_red_cell) });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell_4), new Object[] { "BWB", "WPW", "BWB", 'W', ModItems.wire_red_copper, 'P', OreDictManager.CU.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell) });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_3), new Object[] { "WPW", "BBB", "WPW", 'W', ModItems.wire_gold, 'P', OreDictManager.TI.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell) });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell_2), new Object[] { "WPW", "BWB", "WPW", 'W', ModItems.wire_schrabidium, 'P', OreDictManager.SA326.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell) });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_red_cell_24), new Object[] { "BWB", "WPW", "BWB", 'W', ModItems.wire_aluminium, 'P', OreDictManager.AL.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_red_cell_6) });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell_12), new Object[] { "WPW", "BBB", "WPW", 'W', ModItems.wire_red_copper, 'P', OreDictManager.CU.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell_4) });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_6), new Object[] { "WPW", "BWB", "WPW", 'W', ModItems.wire_gold, 'P', OreDictManager.TI.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_3) });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell_4), new Object[] { "WPW", "BWB", "WPW", 'W', ModItems.wire_schrabidium, 'P', OreDictManager.SA326.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell_2) });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_6), new Object[] { "BW", "PW", "BW", 'W', ModItems.wire_magnetized_tungsten, 'P', ModItems.powder_spark_mix, 'B', ItemBattery.getEmptyBattery(ModItems.battery_spark) });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_25), new Object[] { "W W", "SCS", "PSP", 'W', ModItems.wire_magnetized_tungsten, 'P', ModItems.plate_dineutronium, 'S', ModItems.powder_spark_mix, 'C', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_6) });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_100), new Object[] { "W W", "BPB", "SSS", 'W', ModItems.wire_magnetized_tungsten, 'P', ModItems.plate_dineutronium, 'S', ModItems.powder_spark_mix, 'B', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_25) });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_1000), new Object[] { "PCP", "CSC", "PCP", 'S', ModItems.singularity_spark, 'P', ModItems.powder_spark_mix, 'C', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_100) });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_2500), new Object[] { "SCS", "CVC", "SCS", 'C', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_100), 'V', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_1000), 'S', ModItems.powder_spark_mix });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_10000), new Object[] { "OSO", "SVS", "OSO", 'S', ModItems.singularity_spark, 'V', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_2500), 'O', ModItems.ingot_osmiridium });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_power), new Object[] { "YSY", "SCS", "YSY", 'S', ModItems.singularity_spark, 'C', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_10000), 'Y', ModItems.billet_yharonite });

		CraftingManager.addRecipeAuto(ItemBattery.getFullBattery(ModItems.battery_su), new Object[] { "P", "R", "C", 'P', Items.paper, 'R', OreDictManager.REDSTONE.dust(), 'C', OreDictManager.COAL.dust() });
		CraftingManager.addRecipeAuto(ItemBattery.getFullBattery(ModItems.battery_su), new Object[] { "P", "C", "R", 'P', Items.paper, 'R', OreDictManager.REDSTONE.dust(), 'C', OreDictManager.COAL.dust() });
		CraftingManager.addRecipeAuto(ItemBattery.getFullBattery(ModItems.battery_su_l), new Object[] { " W ", "CPC", "RPR", 'W', ModItems.wire_aluminium, 'P', Items.paper, 'R', OreDictManager.REDSTONE.dust(), 'C', OreDictManager.COAL.dust() });
		CraftingManager.addRecipeAuto(ItemBattery.getFullBattery(ModItems.battery_su_l), new Object[] { " W ", "RPR", "CPC", 'W', ModItems.wire_aluminium, 'P', Items.paper, 'R', OreDictManager.REDSTONE.dust(), 'C', OreDictManager.COAL.dust() });
		CraftingManager.addRecipeAuto(ItemBattery.getFullBattery(ModItems.battery_su_l), new Object[] { " W ", "CPC", "RPR", 'W', ModItems.wire_copper, 'P', Items.paper, 'R', OreDictManager.REDSTONE.dust(), 'C', OreDictManager.COAL.dust() });
		CraftingManager.addRecipeAuto(ItemBattery.getFullBattery(ModItems.battery_su_l), new Object[] { " W ", "RPR", "CPC", 'W', ModItems.wire_copper, 'P', Items.paper, 'R', OreDictManager.REDSTONE.dust(), 'C', OreDictManager.COAL.dust() });
		CraftingManager.addShapelessAuto(ItemBattery.getFullBattery(ModItems.battery_potato), new Object[] { Items.potato, ModItems.wire_aluminium, ModItems.wire_copper });
		CraftingManager.addShapelessAuto(ItemBattery.getFullBattery(ModItems.battery_potatos), new Object[] { ItemBattery.getFullBattery(ModItems.battery_potato), ModItems.turret_chip, OreDictManager.REDSTONE.dust() });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_steam), new Object[] { "PMP", "ISI", "PCP", 'P', OreDictManager.CU.plate(), 'M', ModItems.motor, 'C', ModItems.coil_tungsten, 'S', new ItemStack(ModItems.fluid_tank_full, 1, Fluids.WATER.getID()), 'I', ModItems.plate_polymer });
		CraftingManager.addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_steam_large), new Object[] { "MPM", "ISI", "CPC", 'P', ModItems.board_copper, 'M', ModItems.motor, 'C', ModItems.coil_tungsten, 'S', new ItemStack(ModItems.fluid_barrel_full, 1, Fluids.WATER.getID()), 'I', OreDictManager.ANY_PLASTIC.ingot() });

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.battery_sc_uranium), new Object[] { "NBN", "PCP", "NBN", 'N', OreDictManager.GOLD.nugget(), 'B', OreDictManager.U238.billet(), 'P', OreDictManager.PB.plate(), 'C', ModItems.thermo_element });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.battery_sc_technetium), new Object[] { "NBN", "PCP", "NBN", 'N', OreDictManager.GOLD.nugget(), 'B', OreDictManager.TC99.billet(), 'P', OreDictManager.PB.plate(), 'C', ModItems.battery_sc_uranium });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.battery_sc_plutonium), new Object[] { "NBN", "PCP", "NBN", 'N', OreDictManager.TC99.nugget(), 'B', OreDictManager.PU238.billet(), 'P', OreDictManager.PB.plate(), 'C', ModItems.battery_sc_technetium });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.battery_sc_polonium), new Object[] { "NBN", "PCP", "NBN", 'N', OreDictManager.TC99.nugget(), 'B', OreDictManager.PO210.billet(), 'P', OreDictManager.ANY_PLASTIC.ingot(), 'C', ModItems.battery_sc_plutonium });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.battery_sc_gold), new Object[] { "NBN", "PCP", "NBN", 'N', OreDictManager.TA.nugget(), 'B', OreDictManager.AU198.billet(), 'P', OreDictManager.ANY_PLASTIC.ingot(), 'C', ModItems.battery_sc_polonium });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.battery_sc_lead), new Object[] { "NBN", "PCP", "NBN", 'N', OreDictManager.TA.nugget(), 'B', OreDictManager.PB209.billet(), 'P', OreDictManager.ANY_PLASTIC.ingot(), 'C', ModItems.battery_sc_gold });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.battery_sc_americium), new Object[] { "NBN", "PCP", "NBN", 'N', OreDictManager.TA.nugget(), 'B', OreDictManager.AM241.billet(), 'P', OreDictManager.ANY_PLASTIC.ingot(), 'C', ModItems.battery_sc_lead });
		
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.wiring_red_copper, 1), new Object[] { "PPP", "PIP", "PPP", 'P', OreDictManager.STEEL.plate(), 'I', OreDictManager.STEEL.ingot() });
		
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.jetpack_tank, 1), new Object[] { " S ", "BKB", " S ", 'S', OreDictManager.STEEL.plate(), 'B', ModItems.bolt_tungsten, 'K', Fluids.KEROSENE.getDict(1000) });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_kit_1, 4), new Object[] { "I ", "LB", "P ", 'I', ModItems.plate_polymer, 'L', Fluids.LUBRICANT.getDict(1000), 'B', ModItems.bolt_tungsten, 'P', OreDictManager.IRON.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gun_kit_2, 1), new Object[] { "III", "GLG", "PPP", 'I', ModItems.plate_polymer, 'L', ModItems.ducttape, 'G', ModItems.gun_kit_1, 'P', OreDictManager.IRON.plate() });

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.igniter, 1), new Object[] { " W", "SC", "CE", 'S', OreDictManager.STEEL.plate(), 'W', ModItems.wire_schrabidium, 'C', ModItems.circuit_schrabidium, 'E', OreDictManager.EUPH.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.watch, 1), new Object[] { "LYL", "EWE", "LYL", 'E', OreDictManager.EUPH.ingot(), 'L', OreDictManager.KEY_BLUE, 'W', Items.clock, 'Y', ModItems.billet_yharonite });

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.key, 1), new Object[] { "  B", " B ", "P  ", 'P', OreDictManager.STEEL.plate(), 'B', ModItems.bolt_tungsten });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.key_kit, 1), new Object[] { "PKP", "DTD", "PKP", 'P', OreDictManager.GOLD.plate(), 'K', ModItems.key, 'D', OreDictManager.DESH.dust(), 'T', OreDictManager.KEY_TOOL_SCREWDRIVER });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.key_red, 1), new Object[] { "DSC", "SMS", "KSD", 'C', ModItems.circuit_targeting_tier4, 'M', Items.nether_star, 'K', ModItems.key, 'D', OreDictManager.DESH.dust(), 'S', OreDictManager.BIGMT.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.pin, 1), new Object[] { "W ", " W", " W", 'W', ModItems.wire_copper });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.padlock_rusty, 1), new Object[] { "I", "B", "I", 'I', OreDictManager.IRON.ingot(), 'B', ModItems.bolt_tungsten });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.padlock, 1), new Object[] { " P ", "PBP", "PPP", 'P', OreDictManager.STEEL.plate(), 'B', ModItems.bolt_tungsten });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.padlock_reinforced, 1), new Object[] { " P ", "PBP", "PDP", 'P', OreDictManager.ALLOY.plate(), 'D', ModItems.plate_desh, 'B', ModItems.bolt_dura_steel });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.padlock_unbreakable, 1), new Object[] { " P ", "PBP", "PDP", 'P', OreDictManager.BIGMT.plate(), 'D', OreDictManager.DIAMOND.gem(), 'B', ModItems.bolt_dura_steel });
		
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.record_lc, 1), new Object[] { " S ", "SDS", " S ", 'S', OreDictManager.ANY_PLASTIC.ingot(), 'D', OreDictManager.LAPIS.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.record_ss, 1), new Object[] { " S ", "SDS", " S ", 'S', OreDictManager.ANY_PLASTIC.ingot(), 'D', OreDictManager.ALLOY.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.record_vc, 1), new Object[] { " S ", "SDS", " S ", 'S', OreDictManager.ANY_PLASTIC.ingot(), 'D', OreDictManager.CMB.dust() });

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.polaroid, 1), new Object[] { " C ", "RPY", " B ", 'B', OreDictManager.LAPIS.dust(), 'C', OreDictManager.COAL.dust(), 'R', OreDictManager.ALLOY.dust(), 'Y', OreDictManager.GOLD.dust(), 'P', Items.paper });

		CraftingManager.addShapelessAuto(new ItemStack(ModItems.crystal_horn, 1), new Object[] { OreDictManager.NP237.dust(), OreDictManager.I.dust(), OreDictManager.TH232.dust(), OreDictManager.AT.dust(), OreDictManager.ND.dust(), OreDictManager.CS.dust(), ModBlocks.block_meteor, ModBlocks.gravel_obsidian, Items.water_bucket });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.crystal_charred, 1), new Object[] { OreDictManager.ST.dust(), OreDictManager.CO.dust(), OreDictManager.BR.dust(), OreDictManager.NB.dust(), OreDictManager.TS.dust(), OreDictManager.CE.dust(), ModBlocks.block_meteor, OreDictManager.AL.block(), Items.water_bucket });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.crystal_virus, 1), new Object[] { "STS", "THT", "STS", 'S', ModItems.particle_strange, 'T', OreDictManager.W.dust(), 'H', ModItems.crystal_horn });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.crystal_pulsar, 32), new Object[] { "STS", "THT", "STS", 'S', ModItems.cell_uf6, 'T', OreDictManager.AL.dust(), 'H', ModItems.crystal_charred });

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.fluid_duct_neo, 8, 0), new Object[] { "SAS", "   ", "SAS", 'S', OreDictManager.STEEL.plate(), 'A', OreDictManager.AL.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.fluid_duct_neo, 8, 1), new Object[] { "IAI", "   ", "IAI", 'I', OreDictManager.IRON.plate(), 'A', OreDictManager.AL.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.fluid_duct_neo, 8, 2), new Object[] { "ASA", "   ", "ASA", 'S', OreDictManager.STEEL.plate(), 'A', OreDictManager.AL.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.fluid_duct_paintable, 8), new Object[] { "SAS", "A A", "SAS", 'S', OreDictManager.STEEL.ingot(), 'A', OreDictManager.AL.plate() });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.fluid_duct_gauge), new Object[] { ModBlocks.fluid_duct_paintable, OreDictManager.STEEL.ingot(), ModItems.circuit_aluminium });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.fluid_duct, 8), new Object[] { "SAS", " D ", "SAS", 'S', OreDictManager.STEEL.plate(), 'A', OreDictManager.AL.plate(), 'D', ModItems.ducttape });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.fluid_duct_neo, 1, 0), new Object[] { ModBlocks.fluid_duct });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.fluid_duct_solid, 8), new Object[] { "SAS", "ADA", "SAS", 'S', OreDictManager.STEEL.ingot(), 'A', OreDictManager.AL.plate(), 'D', ModItems.ducttape });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.fluid_valve, 1), new Object[] { "S", "W", 'S', Blocks.lever, 'W', ModBlocks.fluid_duct_paintable });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.fluid_switch, 1), new Object[] { "S", "W", 'S', OreDictManager.REDSTONE.dust(), 'W', ModBlocks.fluid_duct_paintable });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.template_folder, 1), new Object[] { "LPL", "BPB", "LPL", 'P', Items.paper, 'L', "dye", 'B', "dye" });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.pellet_antimatter, 1), new Object[] { "###", "###", "###", '#', ModItems.cell_antimatter });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.fluid_tank_empty, 8), new Object[] { "121", "1G1", "121", '1', OreDictManager.AL.plate(), '2', OreDictManager.IRON.plate(), 'G', OreDictManager.KEY_ANYPANE });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.fluid_tank_lead_empty, 4), new Object[] { "LUL", "LTL", "LUL", 'L', OreDictManager.PB.plate(), 'U', OreDictManager.U238.billet(), 'T', ModItems.fluid_tank_empty });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.fluid_barrel_empty, 2), new Object[] { "121", "1G1", "121", '1', OreDictManager.STEEL.plate(), '2', OreDictManager.AL.plate(), 'G', OreDictManager.KEY_ANYPANE });
		
		if(!GeneralConfig.enable528) {
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.inf_water, 1), new Object[] { "222", "131", "222", '1', Items.water_bucket, '2', OreDictManager.AL.plate(), '3', OreDictManager.DIAMOND.gem() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.inf_water_mk2, 1), new Object[] { "BPB", "PTP", "BPB", 'B', ModItems.inf_water, 'P', ModBlocks.fluid_duct_neo, 'T', ModItems.tank_steel });
		}
		
		//not so Temporary Crappy Recipes
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.piston_selenium, 1), new Object[] { "SSS", "STS", " D ", 'S', OreDictManager.STEEL.plate(), 'T', OreDictManager.W.ingot(), 'D', ModItems.bolt_dura_steel });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.catalyst_clay), new Object[] { OreDictManager.IRON.dust(), Items.clay_ball });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.singularity_spark, 1), new Object[] { "XAX", "BCB", "XAX", 'X', ModItems.plate_dineutronium, 'A', ModItems.singularity_counter_resonant, 'B', ModItems.singularity_super_heated, 'C', ModItems.black_hole });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.singularity_spark, 1), new Object[] { "XBX", "ACA", "XBX", 'X', ModItems.plate_dineutronium, 'A', ModItems.singularity_counter_resonant, 'B', ModItems.singularity_super_heated, 'C', ModItems.black_hole });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ams_core_sing, 1), new Object[] { "EAE", "ASA", "EAE", 'E', ModItems.plate_euphemium, 'A', ModItems.cell_anti_schrabidium, 'S', ModItems.singularity });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ams_core_wormhole, 1), new Object[] { "DPD", "PSP", "DPD", 'D', ModItems.plate_dineutronium, 'P', ModItems.powder_spark_mix, 'S', ModItems.singularity });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ams_core_eyeofharmony, 1), new Object[] { "ALA", "LSL", "ALA", 'A', ModItems.plate_dalekanium, 'L', new ItemStack(ModItems.fluid_barrel_full, 1, Fluids.LAVA.getID()), 'S', ModItems.black_hole });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ams_core_thingy), new Object[] { "NSN", "NGN", "G G", 'N', OreDictManager.GOLD.nugget(), 'G', OreDictManager.GOLD.ingot(), 'S', ModItems.battery_spark_cell_10000 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.photo_panel), new Object[] { " G ", "IPI", " C ", 'G', OreDictManager.KEY_ANYPANE, 'I', ModItems.plate_polymer, 'P', OreDictManager.NETHERQUARTZ.dust(), 'C', ModItems.circuit_aluminium });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_satlinker), new Object[] { "PSP", "SCS", "PSP", 'P', OreDictManager.STEEL.plate(), 'S', OreDictManager.STAR.ingot(), 'C', ModItems.sat_chip });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_keyforge), new Object[] { "PCP", "WSW", "WSW", 'P', OreDictManager.STEEL.plate(), 'S', OreDictManager.W.ingot(), 'C', ModItems.padlock, 'W', OreDictManager.KEY_PLANKS });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.sat_chip), new Object[] { "WWW", "CIC", "WWW", 'W', ModItems.wire_red_copper, 'C', ModItems.circuit_red_copper, 'I', OreDictManager.ANY_PLASTIC.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.sat_mapper), new Object[] { "H", "B", 'H', ModItems.sat_head_mapper, 'B', ModItems.sat_base });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.sat_scanner), new Object[] { "H", "B", 'H', ModItems.sat_head_scanner, 'B', ModItems.sat_base });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.sat_radar), new Object[] { "H", "B", 'H', ModItems.sat_head_radar, 'B', ModItems.sat_base });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.sat_laser), new Object[] { "H", "B", 'H', ModItems.sat_head_laser, 'B', ModItems.sat_base });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.sat_resonator), new Object[] { "H", "B", 'H', ModItems.sat_head_resonator, 'B', ModItems.sat_base });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.sat_mapper), new Object[] { ModBlocks.sat_mapper });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.sat_scanner), new Object[] { ModBlocks.sat_scanner });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.sat_radar), new Object[] { ModBlocks.sat_radar });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.sat_laser), new Object[] { ModBlocks.sat_laser });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.sat_resonator), new Object[] { ModBlocks.sat_resonator });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.sat_foeq), new Object[] { ModBlocks.sat_foeq });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.geiger_counter), new Object[] { ModBlocks.geiger });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.sat_mapper), new Object[] { ModItems.sat_mapper });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.sat_scanner), new Object[] { ModItems.sat_scanner });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.sat_radar), new Object[] { ModItems.sat_radar });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.sat_laser), new Object[] { ModItems.sat_laser });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.sat_resonator), new Object[] { ModItems.sat_resonator });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.sat_foeq), new Object[] { ModItems.sat_foeq });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.sat_interface), new Object[] { "ISI", "PCP", "PAP", 'I', OreDictManager.STEEL.ingot(), 'S', OreDictManager.STAR.ingot(), 'P', ModItems.plate_polymer, 'C', ModItems.sat_chip, 'A', ModItems.circuit_gold });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.sat_coord), new Object[] { "SII", "SCA", "SPP", 'I', OreDictManager.STEEL.ingot(), 'S', OreDictManager.STAR.ingot(), 'P', ModItems.plate_polymer, 'C', ModItems.sat_chip, 'A', ModItems.circuit_red_copper });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_spp_bottom), new Object[] { "MDM", "LCL", "LWL", 'M', OreDictManager.MAGTUNG.ingot(), 'D', ModItems.plate_desh, 'L', OreDictManager.PB.plate(), 'C', ModItems.circuit_gold, 'W', ModItems.coil_magnetized_tungsten });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_spp_top), new Object[] { "LWL", "LCL", "MDM", 'M', OreDictManager.MAGTUNG.ingot(), 'D', ModItems.plate_desh, 'L', OreDictManager.PB.plate(), 'C', ModItems.circuit_gold, 'W', ModItems.coil_magnetized_tungsten });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.machine_spp_bottom), new Object[] { ModBlocks.machine_spp_top });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.machine_spp_top), new Object[] { ModBlocks.machine_spp_bottom });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_transformer), new Object[] { "SDS", "MCM", "MCM", 'S', OreDictManager.IRON.ingot(), 'D', OreDictManager.MINGRADE.ingot(), 'M',ModItems.coil_advanced_alloy, 'C', ModItems.circuit_copper });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_transformer_20), new Object[] { "SDS", "MCM", "MCM", 'S', OreDictManager.IRON.ingot(), 'D', OreDictManager.MINGRADE.ingot(), 'M', ModItems.coil_copper, 'C', ModItems.circuit_copper });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_transformer_dnt), new Object[] { "SDS", "MCM", "MCM", 'S', OreDictManager.STAR.ingot(), 'D', OreDictManager.DESH.ingot(), 'M', ModBlocks.fwatz_conductor, 'C', ModItems.circuit_targeting_tier6 });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_transformer_dnt_20), new Object[] { "SDS", "MCM", "MCM", 'S', OreDictManager.STAR.ingot(), 'D', OreDictManager.DESH.ingot(), 'M', ModBlocks.fusion_conductor, 'C', ModItems.circuit_targeting_tier6 });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.radiobox), new Object[] { "PLP", "PSP", "PLP", 'P', OreDictManager.STEEL.plate(), 'S', ModItems.ring_starmetal, 'C', ModItems.fusion_core, 'L', OreDictManager.getReflector() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.radiorec), new Object[] { "  W", "PCP", "PIP", 'W', ModItems.wire_copper, 'P', OreDictManager.STEEL.plate(), 'C', ModItems.circuit_red_copper, 'I', OreDictManager.ANY_PLASTIC.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.jackt), new Object[] { "S S", "LIL", "LIL", 'S', OreDictManager.STEEL.plate(), 'L', Items.leather, 'I', ModItems.plate_polymer });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.jackt2), new Object[] { "S S", "LIL", "III", 'S', OreDictManager.STEEL.plate(), 'L', Items.leather, 'I', ModItems.plate_polymer });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.vent_chlorine), new Object[] { "IGI", "ICI", "IDI", 'I', OreDictManager.IRON.plate(), 'G', Blocks.iron_bars, 'C', ModItems.pellet_gas, 'D', Blocks.dispenser });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.vent_chlorine_seal), new Object[] { "ISI", "SCS", "ISI", 'I', OreDictManager.BIGMT.ingot(), 'S', OreDictManager.STAR.ingot(), 'C', ModItems.chlorine_pinwheel });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.vent_cloud), new Object[] { "IGI", "ICI", "IDI", 'I', OreDictManager.IRON.plate(), 'G', Blocks.iron_bars, 'C', ModItems.grenade_cloud, 'D', Blocks.dispenser });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.vent_pink_cloud), new Object[] { "IGI", "ICI", "IDI", 'I', OreDictManager.IRON.plate(), 'G', Blocks.iron_bars, 'C', ModItems.grenade_pink_cloud, 'D', Blocks.dispenser });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.spikes, 4), new Object[] { "FFF", "BBB", "TTT", 'F', Items.flint, 'B', ModItems.bolt_tungsten, 'T', OreDictManager.W.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.custom_fall, 1), new Object[] { "IIP", "CHW", "IIP", 'I', ModItems.plate_polymer, 'P', OreDictManager.BIGMT.plate(), 'C', ModItems.circuit_red_copper, 'H', ModItems.hull_small_steel, 'W', ModItems.coil_copper });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_controller, 1), new Object[] { "TDT", "DCD", "TDT", 'T', OreDictManager.ANY_RESISTANTALLOY.ingot(), 'D', ModItems.crt_display, 'C', ModItems.circuit_targeting_tier3 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.containment_box, 1), new Object[] { "LUL", "UCU", "LUL", 'L', OreDictManager.PB.plate(), 'U', OreDictManager.U238.billet(), 'C', ModBlocks.crate_steel });

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.absorber, 1), new Object[] { "ICI", "CPC", "ICI", 'I', OreDictManager.CU.ingot(), 'C', OreDictManager.COAL.dust(), 'P', OreDictManager.PB.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.absorber_red, 1), new Object[] { "ICI", "CPC", "ICI", 'I', OreDictManager.TI.ingot(), 'C', OreDictManager.COAL.dust(), 'P', ModBlocks.absorber });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.absorber_green, 1), new Object[] { "ICI", "CPC", "ICI", 'I', OreDictManager.ANY_PLASTIC.ingot(), 'C', ModItems.powder_desh_mix, 'P', ModBlocks.absorber_red });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.absorber_pink, 1), new Object[] { "ICI", "CPC", "ICI", 'I', OreDictManager.BIGMT.ingot(), 'C', ModItems.powder_nitan_mix, 'P', ModBlocks.absorber_green });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.decon, 1), new Object[] { "BGB", "SAS", "BSB", 'B', OreDictManager.BE.ingot(), 'G', Blocks.iron_bars, 'S', OreDictManager.STEEL.ingot(), 'A', ModBlocks.absorber });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_amgen, 1), new Object[] { "ITI", "TAT", "ITI", 'I', OreDictManager.ALLOY.ingot(), 'T', ModItems.thermo_element, 'A', ModBlocks.absorber });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_geo, 1), new Object[] { "ITI", "PCP", "ITI", 'I', OreDictManager.DURA.ingot(), 'T', ModItems.thermo_element, 'P', ModItems.board_copper, 'C', ModBlocks.red_wire_coated });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_minirtg, 1), new Object[] { "LLL", "PPP", "TRT", 'L', OreDictManager.PB.plate(), 'P', OreDictManager.PU238.billet(), 'T', ModItems.thermo_element, 'R', ModItems.rtg_unit });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_powerrtg, 1), new Object[] { "SRS", "PTP", "SRS", 'S', OreDictManager.STAR.ingot(), 'R', ModItems.rtg_unit, 'P', OreDictManager.PO210.billet(), 'T', OreDictManager.TS.dust() });

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.pink_planks, 4), new Object[] { "W", 'W', ModBlocks.pink_log });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.pink_slab, 6), new Object[] { "WWW", 'W', ModBlocks.pink_planks });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.pink_stairs, 6), new Object[] { "W  ", "WW ", "WWW", 'W', ModBlocks.pink_planks });

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.door_metal, 1), new Object[] { "II", "SS", "II", 'I', OreDictManager.IRON.plate(), 'S', OreDictManager.STEEL.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.door_office, 1), new Object[] { "II", "SS", "II", 'I', OreDictManager.KEY_PLANKS, 'S', OreDictManager.IRON.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.door_bunker, 1), new Object[] { "II", "SS", "II", 'I', OreDictManager.STEEL.plate(), 'S', OreDictManager.PB.plate() });

		CraftingManager.addShapelessAuto(new ItemStack(Items.paper, 1), new Object[] { new ItemStack(ModItems.assembly_template, 1, OreDictionary.WILDCARD_VALUE) });
		CraftingManager.addShapelessAuto(new ItemStack(Items.paper, 1), new Object[] { new ItemStack(ModItems.chemistry_template, 1, OreDictionary.WILDCARD_VALUE) });
		CraftingManager.addShapelessAuto(new ItemStack(Items.paper, 1), new Object[] { new ItemStack(ModItems.crucible_template, 1, OreDictionary.WILDCARD_VALUE) });
		CraftingManager.addShapelessAuto(new ItemStack(Items.slime_ball, 16), new Object[] { new ItemStack(Items.dye, 1, 15), new ItemStack(Items.dye, 1, 15), new ItemStack(Items.dye, 1, 15), new ItemStack(Items.dye, 1, 15), Fluids.SULFURIC_ACID.getDict(1000) });

		for(int i = 1; i < Fluids.getAll().length; ++i) {
    		CraftingManager.addShapelessAuto(new ItemStack(ModItems.fluid_duct, 1, i), new Object[] { new ItemStack(ModBlocks.fluid_duct_neo, 1), new ItemStack(ModItems.fluid_identifier, 1, i) });
    		
    		CraftingManager.addShapelessAuto(new ItemStack(ModItems.fluid_duct, 8, i), new Object[] { new ItemStack(ModBlocks.fluid_duct_neo, 1), new ItemStack(ModBlocks.fluid_duct_neo, 1), 
    				new ItemStack(ModBlocks.fluid_duct_neo, 1), new ItemStack(ModBlocks.fluid_duct_neo, 1), new ItemStack(ModBlocks.fluid_duct_neo, 1), 
    				new ItemStack(ModBlocks.fluid_duct_neo, 1), new ItemStack(ModBlocks.fluid_duct_neo, 1), new ItemStack(ModBlocks.fluid_duct_neo, 1), new ItemStack(ModItems.fluid_identifier, 1, i) });
    		
    		CraftingManager.addShapelessAuto(new ItemStack(ModItems.fluid_duct, 1, i), new Object[] { new ItemStack(ModItems.fluid_duct, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.fluid_identifier, 1, i) });
    		
    		CraftingManager.addShapelessAuto(new ItemStack(ModItems.fluid_duct, 8, i), new Object[] { new ItemStack(ModItems.fluid_duct, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.fluid_duct, 1, OreDictionary.WILDCARD_VALUE), 
    				new ItemStack(ModItems.fluid_duct, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.fluid_duct, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.fluid_duct, 1, OreDictionary.WILDCARD_VALUE), 
    				new ItemStack(ModItems.fluid_duct, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.fluid_duct, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.fluid_duct, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.fluid_identifier, 1, i) });
		}

		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.fluid_duct_neo, 1), new Object[] { new ItemStack(ModItems.fluid_duct, 1, OreDictionary.WILDCARD_VALUE) });

		CraftingManager.addShapelessAuto(new ItemStack(ModItems.redstone_depleted, 1), new Object[] { new ItemStack(ModItems.battery_su, 1, OreDictionary.WILDCARD_VALUE) });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.redstone_depleted, 2), new Object[] { new ItemStack(ModItems.battery_su_l, 1, OreDictionary.WILDCARD_VALUE) });
		CraftingManager.addShapelessAuto(new ItemStack(Items.redstone, 1), new Object[] { ModItems.redstone_depleted, ModItems.redstone_depleted });

		CraftingManager.addRecipeAuto(new ItemStack(Blocks.torch, 3), new Object[] { "L", "S", 'L', OreDictManager.LIGNITE.gem(), 'S', OreDictManager.KEY_STICK });
		CraftingManager.addRecipeAuto(new ItemStack(Blocks.torch, 8), new Object[] { "L", "S", 'L', OreDictManager.ANY_COKE.gem(), 'S', OreDictManager.KEY_STICK });

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_missile_assembly, 1), new Object[] { "PWP", "SSS", "CCC", 'P', ModItems.pedestal_steel, 'W', ModItems.wrench, 'S', OreDictManager.STEEL.plate(), 'C', ModBlocks.steel_scaffold });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.struct_launcher, 4), new Object[] { "PPP", "SDS", "CCC", 'P', OreDictManager.STEEL.plate(), 'S', ModBlocks.steel_scaffold, 'D', ModBlocks.deco_pipe_quad, 'C', ModBlocks.concrete_smooth });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.struct_launcher, 4), new Object[] { "PPP", "SDS", "CCC", 'P', OreDictManager.STEEL.plate(), 'S', ModBlocks.steel_scaffold, 'D', ModBlocks.deco_pipe_quad, 'C', ModBlocks.concrete });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.struct_scaffold, 4), new Object[] { "SSS", "DCD", "SSS", 'S', ModBlocks.steel_scaffold, 'D', new ItemStack(ModBlocks.fluid_duct_neo, 1, OreDictionary.WILDCARD_VALUE), 'C', ModBlocks.red_cable });

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.seg_10, 1), new Object[] { "P", "S", "B", 'P', OreDictManager.AL.plate(), 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.steel_beam });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.seg_15, 1), new Object[] { "PP", "SS", "BB", 'P', OreDictManager.TI.plate(), 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.steel_beam });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.seg_20, 1), new Object[] { "PGP", "SSS", "BBB", 'P', OreDictManager.STEEL.plate(), 'G', OreDictManager.GOLD.plate(), 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.steel_beam });

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.obj_tester, 1), new Object[] { "P", "I", "S", 'P', ModItems.polaroid, 'I', ModItems.flame_pony, 'S', OreDictManager.STEEL.plate() });

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.fence_metal, 6), new Object[] { "BIB", "BIB", 'B', Blocks.iron_bars, 'I', Items.iron_ingot });

		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.waste_trinitite), new Object[] { new ItemStack(Blocks.sand, 1, 0), ModItems.trinitite });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.waste_trinitite_red), new Object[] { new ItemStack(Blocks.sand, 1, 1), ModItems.trinitite });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.sand_uranium, 8), new Object[] { "sand", "sand", "sand", "sand", "sand", "sand", "sand", "sand", OreDictManager.U.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.sand_polonium, 8), new Object[] { "sand", "sand", "sand", "sand", "sand", "sand", "sand", "sand", OreDictManager.PO210.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.sand_boron, 8), new Object[] { "sand", "sand", "sand", "sand", "sand", "sand", "sand", "sand", OreDictManager.B.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.sand_lead, 8), new Object[] { "sand", "sand", "sand", "sand", "sand", "sand", "sand", "sand", OreDictManager.PB.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.sand_quartz, 1), new Object[] { "sand", "sand", OreDictManager.NETHERQUARTZ.dust(), OreDictManager.NETHERQUARTZ.dust() });
		
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.rune_blank, 1), new Object[] { "PSP", "SDS", "PSP", 'P', ModItems.powder_magic, 'S', OreDictManager.STAR.ingot(), 'D', OreDictManager.KEY_CIRCUIT_BISMUTH });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rune_isa, 1), new Object[] { ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.singularity_counter_resonant });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rune_dagaz, 1), new Object[] { ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.singularity });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rune_hagalaz, 1), new Object[] { ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.singularity_super_heated });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rune_jera, 1), new Object[] { ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.singularity_spark });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rune_thurisaz, 1), new Object[] { ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.black_hole });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ams_lens, 1), new Object[] { "PDP", "GDG", "PDP", 'P', ModItems.plate_dineutronium, 'G', ModBlocks.reinforced_glass, 'D', Blocks.diamond_block });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ams_catalyst_blank, 1), new Object[] { "TET", "ETE", "TET", 'T', OreDictManager.TS.dust(), 'E', OreDictManager.EUPH.ingot()});
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ams_focus_limiter, 1), new Object[] { "PDP", "GDG", "PDP", 'P', OreDictManager.BIGMT.plate(), 'G', ModBlocks.reinforced_glass, 'D', Blocks.diamond_block });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ams_muzzle, 1), new Object[] { "GDG", "GDG", "PGP", 'P', OreDictManager.BIGMT.plate(), 'G', ModBlocks.reinforced_glass, 'D', Blocks.diamond_block });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ams_catalyst_lithium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_isa, ModItems.rune_jera, ModItems.rune_jera, OreDictManager.LI.dust(), OreDictManager.LI.dust(), OreDictManager.LI.dust(), OreDictManager.LI.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ams_catalyst_beryllium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_dagaz, ModItems.rune_jera, ModItems.rune_jera, OreDictManager.BE.dust(), OreDictManager.BE.dust(), OreDictManager.BE.dust(), OreDictManager.BE.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ams_catalyst_copper, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_dagaz, ModItems.rune_jera, ModItems.rune_jera, OreDictManager.CU.dust(), OreDictManager.CU.dust(), OreDictManager.CU.dust(), OreDictManager.CU.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ams_catalyst_cobalt, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_hagalaz, ModItems.rune_jera, ModItems.rune_jera, OreDictManager.CO.dust(), OreDictManager.CO.dust(), OreDictManager.CO.dust(), OreDictManager.CO.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ams_catalyst_tungsten, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_hagalaz, ModItems.rune_hagalaz, ModItems.rune_jera, ModItems.rune_jera, OreDictManager.W.dust(), OreDictManager.W.dust(), OreDictManager.W.dust(), OreDictManager.W.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ams_catalyst_aluminium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_isa, ModItems.rune_jera, ModItems.rune_thurisaz, OreDictManager.AL.dust(), OreDictManager.AL.dust(), OreDictManager.AL.dust(), OreDictManager.AL.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ams_catalyst_iron, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_dagaz, ModItems.rune_jera, ModItems.rune_thurisaz, OreDictManager.IRON.dust(), OreDictManager.IRON.dust(), OreDictManager.IRON.dust(), OreDictManager.IRON.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ams_catalyst_strontium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_dagaz, ModItems.rune_jera, ModItems.rune_thurisaz, OreDictManager.ST.dust(), OreDictManager.ST.dust(), OreDictManager.ST.dust(), OreDictManager.ST.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ams_catalyst_niobium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_hagalaz, ModItems.rune_jera, ModItems.rune_thurisaz, OreDictManager.NB.dust(), OreDictManager.NB.dust(), OreDictManager.NB.dust(), OreDictManager.NB.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ams_catalyst_cerium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_hagalaz, ModItems.rune_hagalaz, ModItems.rune_jera, ModItems.rune_thurisaz, OreDictManager.CE.dust(), OreDictManager.CE.dust(), OreDictManager.CE.dust(), OreDictManager.CE.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ams_catalyst_caesium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_isa, ModItems.rune_thurisaz, ModItems.rune_thurisaz, OreDictManager.CS.dust(), OreDictManager.CS.dust(), OreDictManager.CS.dust(), OreDictManager.CS.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ams_catalyst_thorium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_dagaz, ModItems.rune_thurisaz, ModItems.rune_thurisaz, OreDictManager.TH232.dust(), OreDictManager.TH232.dust(), OreDictManager.TH232.dust(), OreDictManager.TH232.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ams_catalyst_euphemium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_dagaz, ModItems.rune_thurisaz, ModItems.rune_thurisaz, OreDictManager.EUPH.dust(), OreDictManager.EUPH.dust(), OreDictManager.EUPH.dust(), OreDictManager.EUPH.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ams_catalyst_schrabidium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_hagalaz, ModItems.rune_thurisaz, ModItems.rune_thurisaz, OreDictManager.SA326.dust(), OreDictManager.SA326.dust(), OreDictManager.SA326.dust(), OreDictManager.SA326.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ams_catalyst_dineutronium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_hagalaz, ModItems.rune_hagalaz, ModItems.rune_thurisaz, ModItems.rune_thurisaz, OreDictManager.DNT.dust(), OreDictManager.DNT.dust(), OreDictManager.DNT.dust(), OreDictManager.DNT.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.dfc_core, 1), new Object[] { "DLD", "LML", "DLD", 'D', ModItems.ingot_bismuth, 'L', OreDictManager.DNT.block(), 'M', OreDictManager.KEY_CIRCUIT_BISMUTH });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.dfc_emitter, 1), new Object[] { "SDS", "TXL", "SDS", 'S', OreDictManager.OSMIRIDIUM.plateWelded(), 'D', ModItems.plate_desh, 'T', ModBlocks.machine_transformer_dnt, 'X', ModItems.crystal_xen, 'L', ModItems.sat_head_laser });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.dfc_receiver, 1), new Object[] { "SDS", "TXL", "SDS", 'S', OreDictManager.OSMIRIDIUM.plateWelded(), 'D', ModItems.plate_desh, 'T', ModBlocks.machine_transformer_dnt, 'X', ModBlocks.block_dineutronium, 'L', ModItems.hull_small_steel });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.dfc_injector, 1), new Object[] { "SDS", "TXL", "SDS", 'S', OreDictManager.OSMIRIDIUM.plateWelded(), 'D', OreDictManager.CMB.plate(), 'T', ModBlocks.machine_fluidtank, 'X', ModItems.motor, 'L', ModItems.pipes_steel });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.dfc_stabilizer, 1), new Object[] { "SDS", "TXL", "SDS", 'S', OreDictManager.OSMIRIDIUM.plateWelded(), 'D', ModItems.plate_desh, 'T', ModItems.singularity_spark, 'X', ModItems.magnet_circular, 'L', ModItems.crystal_xen });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.barrel_plastic, 1), new Object[] { "IPI", "I I", "IPI", 'I', ModItems.plate_polymer, 'P', OreDictManager.AL.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.barrel_iron, 1), new Object[] { "IPI", "I I", "IPI", 'I', OreDictManager.IRON.plate(), 'P', OreDictManager.IRON.ingot() });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.barrel_iron, 1), new Object[] { ModBlocks.barrel_corroded, OreDictManager.ANY_TAR.any() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.barrel_steel, 1), new Object[] { "IPI", "ITI", "IPI", 'I', OreDictManager.STEEL.plate(), 'P', OreDictManager.STEEL.ingot(), 'T', OreDictManager.ANY_TAR.any() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.barrel_tcalloy, 1), new Object[] { "IPI", "I I", "IPI", 'I', "ingotTcAlloy", 'P', OreDictManager.TI.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.barrel_antimatter, 1), new Object[] { "IPI", "IBI", "IPI", 'I', OreDictManager.BIGMT.plate(), 'P', ModItems.coil_advanced_torus, 'B', ModItems.battery_sc_technetium });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.tesla, 1), new Object[] { "CCC", "PIP", "WTW", 'C', ModItems.coil_copper, 'I', OreDictManager.IRON.ingot(), 'P', OreDictManager.ANY_PLASTIC.ingot(), 'T', ModBlocks.machine_transformer, 'W', OreDictManager.KEY_PLANKS });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.struct_plasma_core, 1), new Object[] { "CBC", "BHB", "CBC", 'C', ModItems.circuit_gold, 'B', ModBlocks.machine_lithium_battery, 'H', ModBlocks.fusion_heater });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.struct_watz_core, 1), new Object[] { "CBC", "BHB", "CBC", 'C', ModItems.circuit_schrabidium, 'B', OreDictManager.ANY_RESISTANTALLOY.plateCast(), 'H', ModBlocks.watz_cooler });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.fusion_heater), new Object[] { ModBlocks.fusion_hatch });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.energy_core), new Object[] { ModItems.fusion_core, ModItems.fuse });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.catalytic_converter, 1), new Object[] { "PCP", "PBP", "PCP", 'P', OreDictManager.ANY_HARDPLASTIC.ingot(), 'C', OreDictManager.CO.dust(), 'B', OreDictManager.BI.ingot() });

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.upgrade_nullifier, 1), new Object[] { "SPS", "PUP", "SPS", 'S', OreDictManager.STEEL.plate(), 'P', ModItems.powder_fire, 'U', ModItems.upgrade_template });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.upgrade_smelter, 1), new Object[] { "PHP", "CUC", "DTD", 'P', OreDictManager.CU.plate(), 'H', Blocks.hopper, 'C', ModItems.coil_tungsten, 'U', ModItems.upgrade_template, 'D', ModItems.coil_copper, 'T', ModBlocks.machine_transformer });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.upgrade_shredder, 1), new Object[] { "PHP", "CUC", "DTD", 'P', ModItems.motor, 'H', Blocks.hopper, 'C', ModItems.blades_advanced_alloy, 'U', ModItems.upgrade_smelter, 'D', OreDictManager.TI.plate(), 'T', ModBlocks.machine_transformer });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.upgrade_centrifuge, 1), new Object[] { "PHP", "PUP", "DTD", 'P', ModItems.centrifuge_element, 'H', Blocks.hopper, 'U', ModItems.upgrade_shredder, 'D', OreDictManager.ANY_PLASTIC.ingot(), 'T', ModBlocks.machine_transformer });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.upgrade_crystallizer, 1), new Object[] { "PHP", "CUC", "DTD", 'P', new ItemStack(ModItems.fluid_barrel_full, 1, Fluids.ACID.getID()), 'H', ModItems.circuit_targeting_tier4, 'C', ModBlocks.barrel_steel, 'U', ModItems.upgrade_centrifuge, 'D', ModItems.motor, 'T', ModBlocks.machine_transformer });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.upgrade_screm, 1), new Object[] { "SUS", "SCS", "SUS", 'S', OreDictManager.STEEL.plate(), 'U', ModItems.upgrade_template, 'C', ModItems.crystal_xen });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.upgrade_gc_speed, 1), new Object[] {"GNG", "RUR", "GMG", 'R', OreDictManager.RUBBER.ingot(), 'M', ModItems.motor, 'G', ModItems.coil_gold, 'N', OreDictManager.NB.ingot(), 'U', ModItems.upgrade_template});
		
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.upgrade_stack, 1, 0), new Object[] { " C ", "PUP", " C ", 'C', ModItems.circuit_aluminium, 'P', DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_PNEUMATIC), 'U', ModItems.upgrade_template });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.upgrade_stack, 1, 1), new Object[] { " C ", "PUP", " C ", 'C', ModItems.circuit_copper, 'P', DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_HYDRAULIC), 'U', new ItemStack(ModItems.upgrade_stack, 1, 0) });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.upgrade_stack, 1, 2), new Object[] { " C ", "PUP", " C ", 'C', ModItems.circuit_red_copper, 'P', DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_ELECTRIC), 'U', new ItemStack(ModItems.upgrade_stack, 1, 1) });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.upgrade_ejector, 1, 0), new Object[] { " C ", "PUP", " C ", 'C', ModItems.plate_copper, 'P', ModItems.motor, 'U', ModItems.upgrade_template });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.upgrade_ejector, 1, 1), new Object[] { " C ", "PUP", " C ", 'C', ModItems.plate_gold, 'P', ModItems.motor, 'U', new ItemStack(ModItems.upgrade_ejector, 1, 0) });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.upgrade_ejector, 1, 2), new Object[] { " C ", "PUP", " C ", 'C', ModItems.plate_saturnite, 'P', ModItems.motor, 'U', new ItemStack(ModItems.upgrade_ejector, 1, 1) });
		
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mech_key, 1), new Object[] { "MCM", "MKM", "MMM", 'M', ModItems.ingot_meteorite_forged, 'C', ModItems.coin_maskman, 'K', ModItems.key });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.spawn_ufo, 1), new Object[] { "MMM", "DCD", "MMM", 'M', ModItems.ingot_meteorite, 'D', OreDictManager.DNT.ingot(), 'C', ModItems.coin_worm });
		
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_alloy, 1), new Object[] { "WWW", "WCW", "WWW", 'W', ModItems.wire_advanced_alloy, 'C', ModBlocks.fusion_conductor });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_alloy, 1), new Object[] { "WW", "WW", 'W', OreDictManager.ALLOY.wireDense() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_gold, 1), new Object[] { "PGP", "PCP", "PGP", 'G', OreDictManager.GOLD.dust(), 'C', ModBlocks.hadron_coil_alloy, 'P', OreDictManager.IRON.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_gold, 1), new Object[] { "WG", "GW", 'W', OreDictManager.ALLOY.wireDense(), 'G', OreDictManager.GOLD.wireDense() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_neodymium, 1), new Object[] { "G", "C", "G", 'G', OreDictManager.ND.dust(), 'C', ModBlocks.hadron_coil_gold });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_neodymium, 1), new Object[] { "WG", "GW", 'W', OreDictManager.ND.wireDense(), 'G', OreDictManager.GOLD.wireDense() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_magtung, 1), new Object[] { "WWW", "WCW", "WWW", 'W', ModItems.wire_magnetized_tungsten, 'C', ModBlocks.fwatz_conductor });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_magtung, 1), new Object[] { "WW", "WW", 'W', OreDictManager.MAGTUNG.wireDense() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_schrabidium, 1), new Object[] { "WWW", "WCW", "WWW", 'W', ModItems.wire_schrabidium, 'C', ModBlocks.hadron_coil_magtung });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_schrabidium, 1), new Object[] { "WS", "SW", 'W', OreDictManager.MAGTUNG.wireDense(), 'S', OreDictManager.SA326.wireDense() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_schrabidate, 1), new Object[] { " S ", "SCS", " S ", 'S', OreDictManager.SBD.dust(), 'C', ModBlocks.hadron_coil_schrabidium });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_schrabidate, 1), new Object[] { "WS", "SW", 'W', OreDictManager.SBD.wireDense(), 'S', OreDictManager.SA326.wireDense() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_starmetal, 1), new Object[] { "SNS", "SCS", "SNS", 'S', OreDictManager.STAR.ingot(), 'N', ModBlocks.hadron_coil_neodymium, 'C', ModBlocks.hadron_coil_schrabidate });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_starmetal, 1), new Object[] { "SW", "WS", 'W', OreDictManager.SBD.wireDense(), 'S', OreDictManager.STAR.wireDense() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_chlorophyte, 1), new Object[] { "TCT", "TST", "TCT", 'T', ModItems.coil_tungsten, 'C', ModItems.powder_chlorophyte, 'S', ModBlocks.hadron_coil_starmetal });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_chlorophyte, 1), new Object[] { "TC", "CT", 'T', OreDictManager.W.wireDense(), 'C', ModItems.powder_chlorophyte });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.hadron_diode, 1), new Object[] { "CIC", "ISI", "CIC", 'C', ModBlocks.hadron_coil_alloy, 'I', OreDictManager.STEEL.ingot(), 'S', ModItems.circuit_gold });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.hadron_plating, 16), new Object[] { "CC", "CC", 'C', OreDictManager.STEEL.plateCast()});
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_blue, 1), new Object[] { ModBlocks.hadron_plating, OreDictManager.KEY_BLUE });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_black, 1), new Object[] { ModBlocks.hadron_plating, OreDictManager.KEY_BLACK });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_yellow, 1), new Object[] { ModBlocks.hadron_plating, OreDictManager.KEY_YELLOW });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_striped, 1), new Object[] { ModBlocks.hadron_plating, OreDictManager.KEY_BLACK, OreDictManager.KEY_YELLOW });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_glass, 1), new Object[] { ModBlocks.hadron_plating, OreDictManager.KEY_ANYGLASS });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_voltz, 1), new Object[] { ModBlocks.hadron_plating, OreDictManager.KEY_RED });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.hadron_power, 1), new Object[] { "SFS", "FTF", "SFS", 'S', OreDictManager.BIGMT.ingot(), 'T', ModBlocks.machine_transformer, 'F', ModItems.fuse });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.hadron_power_10m, 1), new Object[] { "HF", 'H', ModBlocks.hadron_power, 'F', ModItems.fuse });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.hadron_power_100m, 1), new Object[] { "HF", 'H', ModBlocks.hadron_power_10m, 'F', ModItems.fuse });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.hadron_power_1g, 1), new Object[] { "HF", 'H', ModBlocks.hadron_power_100m, 'F', ModItems.fuse });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.hadron_power_10g, 1), new Object[] { "HF", 'H', ModBlocks.hadron_power_1g, 'F', ModItems.fuse });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.hadron_analysis, 1), new Object[] { "IPI", "PCP", "IPI", 'I', OreDictManager.TI.ingot(), 'P', OreDictManager.getReflector(), 'C', ModItems.circuit_gold });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.hadron_analysis_glass, 1), new Object[] { ModBlocks.hadron_analysis, OreDictManager.KEY_ANYGLASS });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.hadron_access, 1), new Object[] { "IGI", "CRC", "IPI", 'I', ModItems.plate_polymer, 'G', OreDictManager.KEY_ANYPANE, 'C', ModItems.circuit_aluminium, 'R', OreDictManager.REDSTONE.block(), 'P', ModBlocks.hadron_plating_blue });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.hadron_cooler, 1, 0), new Object[] { "PCP", "CHC", "PCP", 'P', OreDictManager.ANY_RESISTANTALLOY.plateCast(), 'C', ModItems.circuit_gold, 'H', Fluids.HELIUM4.getDict(16_000) });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.hadron_cooler, 1, 1), new Object[] { "PCP", "CHC", "PCP", 'P', OreDictManager.GOLD.plateCast(), 'C', ModItems.motor_bismuth, 'H', new ItemStack(ModBlocks.hadron_cooler, 1, 0) });

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ingot_schrabidium, 8), new Object[] { "UUU", "UPU", "UUU", 'U', OreDictManager.U.ingot(), 'P', new ItemStack(ModItems.particle_higgs).setStackDisplayName("Higgs Boson (Temporary Recipe)") });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ingot_euphemium, 8), new Object[] { "UUU", "UPU", "UUU", 'U', OreDictManager.PU.ingot(), 'P', new ItemStack(ModItems.particle_dark).setStackDisplayName("Dark Matter (Temporary Recipe)") });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ingot_dineutronium, 8), new Object[] { "UUU", "UPU", "UUU", 'U', OreDictManager.SBD.ingot(), 'P', new ItemStack(ModItems.particle_sparkticle).setStackDisplayName("Sparkticle (Temporary Recipe)") });

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.fireworks, 1), new Object[] { "PPP", "PPP", "WIW", 'P', Items.paper, 'W', OreDictManager.KEY_PLANKS, 'I', OreDictManager.IRON.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.safety_fuse, 8), new Object[] { "SSS", "SGS", "SSS", 'S', Items.string, 'G', Items.gunpowder });
		
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.rbmk_lid, 4), new Object[] { "PPP", "CCC", "PPP", 'P', OreDictManager.STEEL.plate(), 'C', ModBlocks.concrete_asbestos });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.rbmk_lid_glass, 4), new Object[] { "LLL", "BBB", "P P", 'P', OreDictManager.STEEL.plate(), 'L', ModBlocks.glass_lead, 'B', ModBlocks.glass_boron });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.rbmk_lid_glass, 4), new Object[] { "BBB", "LLL", "P P", 'P', OreDictManager.STEEL.plate(), 'L', ModBlocks.glass_lead, 'B', ModBlocks.glass_boron });

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.rbmk_moderator, 1), new Object[] { " G ", "GRG", " G ", 'G', OreDictManager.GRAPHITE.block(), 'R', ModBlocks.rbmk_blank });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.rbmk_absorber, 1), new Object[] { "GGG", "GRG", "GGG", 'G', OreDictManager.B.ingot(), 'R', ModBlocks.rbmk_blank });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.rbmk_reflector, 1), new Object[] { "GGG", "GRG", "GGG", 'G', OreDictManager.getReflector(), 'R', ModBlocks.rbmk_blank });
		if(!GeneralConfig.enable528) {
			CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.rbmk_control, 1), new Object[] { " B ", "GRG", " B ", 'G', OreDictManager.GRAPHITE.ingot(), 'B', ModItems.motor, 'R', ModBlocks.rbmk_absorber });
		} else {
			CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.rbmk_control, 1), new Object[] { "CBC", "GRG", "CBC", 'G', OreDictManager.GRAPHITE.ingot(), 'B', ModItems.motor, 'R', ModBlocks.rbmk_absorber, 'C', OreDictManager.CD.ingot() });
		}
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.rbmk_control_mod, 1), new Object[] { "BGB", "GRG", "BGB", 'G', OreDictManager.GRAPHITE.block(), 'R', ModBlocks.rbmk_control, 'B', ModItems.nugget_bismuth });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.rbmk_control_auto, 1), new Object[] { "C", "R", "D", 'C', ModItems.circuit_targeting_tier1, 'R', ModBlocks.rbmk_control, 'D', ModItems.crt_display });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.rbmk_rod_reasim, 1), new Object[] { "ZCZ", "ZRZ", "ZCZ", 'C', ModItems.hull_small_steel, 'R', ModBlocks.rbmk_blank, 'Z', OreDictManager.ZR.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.rbmk_rod_reasim_mod, 1), new Object[] { "BGB", "GRG", "BGB", 'G', OreDictManager.GRAPHITE.block(), 'R', ModBlocks.rbmk_rod_reasim, 'B', OreDictManager.ANY_RESISTANTALLOY.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.rbmk_outgasser, 1), new Object[] { "GHG", "GRG", "GTG", 'G', ModBlocks.steel_grate, 'H', Blocks.hopper, 'T', ModItems.tank_steel, 'R', ModBlocks.rbmk_blank });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.rbmk_storage, 1), new Object[] { "C", "R", "C", 'C', ModBlocks.crate_steel, 'R', ModBlocks.rbmk_blank });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.rbmk_loader, 1), new Object[] { "SCS", "CBC", "SCS", 'S', OreDictManager.STEEL.plate(), 'C', OreDictManager.CU.ingot(), 'B', ModItems.tank_steel });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.rbmk_steam_inlet, 1), new Object[] { "SCS", "CBC", "SCS", 'S', OreDictManager.STEEL.ingot(), 'C', OreDictManager.IRON.plate(), 'B', ModItems.tank_steel });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.rbmk_steam_outlet, 1), new Object[] { "SCS", "CBC", "SCS", 'S', OreDictManager.STEEL.ingot(), 'C', OreDictManager.CU.plate(), 'B', ModItems.tank_steel });
		//addRecipeAuto(new ItemStack(ModBlocks.rbmk_heatex, 1), new Object[] { "SCS", "CBC", "SCS", 'S', STEEL.ingot(), 'C', CU.plate(), 'B', ModItems.pipes_steel });

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.pwr_fuel, 4), new Object[] { "LZL", "L L", "LZL", 'L', OreDictManager.PB.plate528(), 'Z', OreDictManager.ZR.plateWelded() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.pwr_control, 4), new Object[] { "SBS", "MBM", "SBS", 'S', OreDictManager.STEEL.plate528(), 'B', OreDictManager.B.ingot(), 'M', ModItems.motor });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.pwr_channel, 4), new Object[] { "CPC", "BPB", "CPC", 'C', OreDictManager.CU.ingot(), 'P', ModBlocks.deco_pipe_quad, 'B', OreDictManager.ANY_PLASTIC.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.pwr_heatex, 4), new Object[] { "CSC", "SMS", "CSC", 'C', OreDictManager.CU.plateCast(), 'S', OreDictManager.STEEL.plate528(), 'M', ModItems.motor });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.pwr_reflector, 4), new Object[] { "RLR", "LSL", "RLR", 'R', OreDictManager.getReflector(), 'L', OreDictManager.PB.plate528(), 'S', OreDictManager.STEEL.plateCast() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.pwr_casing, 4), new Object[] { "LCL", "CSC", "LCL", 'L', OreDictManager.PB.plate528(), 'C', OreDictManager.ANY_CONCRETE.any(), 'S', OreDictManager.STEEL.plateCast() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.pwr_controller, 1), new Object[] { "CPC", "PSP", "CPC", 'C', ModBlocks.pwr_casing, 'P', OreDictManager.ANY_PLASTIC.ingot(), 'S', !GeneralConfig.enableExpensiveMode ? ModItems.circuit_gold : OreDictManager.STEEL.heavyComp() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.pwr_port, 1), new Object[] { "S", "C", "S", 'S', OreDictManager.STEEL.plate(), 'C', ModBlocks.pwr_casing });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.pwr_neutron_source, 1), new Object[] { "LRL", "ZRZ", "LRL", 'L', OreDictManager.PB.plate528(), 'R', ModItems.billet_ra226be, 'Z', OreDictManager.ZR.plateCast() });

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.deco_rbmk, 8), new Object[] { "R", 'R', ModBlocks.rbmk_blank });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.deco_rbmk_smooth, 1), new Object[] { "R", 'R', ModBlocks.deco_rbmk });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.rbmk_blank, 1), new Object[] { "RRR", "R R", "RRR", 'R', ModBlocks.deco_rbmk });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.rbmk_blank, 1), new Object[] { "RRR", "R R", "RRR", 'R', ModBlocks.deco_rbmk_smooth });

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.ladder_sturdy, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', OreDictManager.KEY_PLANKS });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.ladder_iron, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', OreDictManager.IRON.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.ladder_gold, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', OreDictManager.GOLD.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.ladder_aluminium, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', OreDictManager.AL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.ladder_copper, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', OreDictManager.CU.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.ladder_titanium, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', OreDictManager.TI.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.ladder_lead, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', OreDictManager.PB.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.ladder_cobalt, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', OreDictManager.CO.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.ladder_steel, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', OreDictManager.STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.ladder_tungsten, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', OreDictManager.W.ingot() });

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_storage_drum), new Object[] { "LLL", "L#L", "LLL", 'L', OreDictManager.PB.plate(), '#', ModItems.tank_steel });

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.deco_pipe, 6), new Object[] { "PPP", 'P', ModItems.hull_small_steel });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.deco_pipe, 1), new Object[] { ModBlocks.deco_pipe_rim });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.deco_pipe, 1), new Object[] { ModBlocks.deco_pipe_framed });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.deco_pipe, 1), new Object[] { ModBlocks.deco_pipe_quad });

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', OreDictManager.STEEL.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad, 4), new Object[] { "PP", "PP", 'P', ModBlocks.deco_pipe });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', Blocks.iron_bars });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim, 'C', Blocks.iron_bars });

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', OreDictManager.IRON.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim, 'C', OreDictManager.IRON.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad, 'C', OreDictManager.IRON.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed, 'C', OreDictManager.IRON.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_green, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', OreDictManager.KEY_GREEN });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim_green, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim, 'C', OreDictManager.KEY_GREEN });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad_green, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad, 'C', OreDictManager.KEY_GREEN });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed_green, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed, 'C', OreDictManager.KEY_GREEN });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_green_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_green, 'C', OreDictManager.IRON.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim_green_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim_green, 'C', OreDictManager.IRON.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad_green_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad_green, 'C', OreDictManager.IRON.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed_green_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed_green, 'C', OreDictManager.IRON.dust() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_red, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', OreDictManager.KEY_RED });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim_red, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim, 'C', OreDictManager.KEY_RED });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad_red, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad, 'C', OreDictManager.KEY_RED });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed_red, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed, 'C', OreDictManager.KEY_RED });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_marked, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_green, 'C', OreDictManager.KEY_GREEN });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim_marked, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim_green, 'C', OreDictManager.KEY_GREEN });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad_marked, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad_green, 'C', OreDictManager.KEY_GREEN });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed_marked, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed_green, 'C', OreDictManager.KEY_GREEN });
		
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.deco_emitter), new Object[] { "IDI", "DRD", "IDI", 'I', OreDictManager.IRON.ingot(), 'D', OreDictManager.DIAMOND.gem(), 'R', OreDictManager.REDSTONE.block() });

		CraftingManager.addRecipeAuto(new ItemStack(Items.name_tag), new Object[] { "SB ", "BPB", " BP", 'S', Items.string, 'B', OreDictManager.KEY_SLIME, 'P', Items.paper });
		CraftingManager.addRecipeAuto(new ItemStack(Items.name_tag), new Object[] { "SB ", "BPB", " BP", 'S', Items.string, 'B', OreDictManager.ANY_TAR.any(), 'P', Items.paper });
		CraftingManager.addRecipeAuto(new ItemStack(Items.lead, 4), new Object[] { "RSR", 'R', DictFrame.fromOne(ModItems.plant_item, EnumPlantType.ROPE), 'S', OreDictManager.KEY_SLIME });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.rag, 4), new Object[] { "SW", "WS", 'S', Items.string, 'W', Blocks.wool });

		CraftingManager.addShapelessAuto(new ItemStack(ModItems.solid_fuel, 3), new Object[] { Fluids.HEATINGOIL.getDict(16000), OreDictManager.KEY_TOOL_CHEMISTRYSET });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.canister_full, 2, Fluids.LUBRICANT.getID()), new Object[] { Fluids.HEATINGOIL.getDict(1000), Fluids.UNSATURATEDS.getDict(1000), ModItems.canister_empty, ModItems.canister_empty, OreDictManager.KEY_TOOL_CHEMISTRYSET });

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_condenser), new Object[] { "SIS", "ICI", "SIS", 'S', OreDictManager.STEEL.ingot(), 'I', OreDictManager.IRON.plate(), 'C', ModItems.board_copper });

		CraftingManager.addShapelessAuto(new ItemStack(ModItems.book_guide, 1, BookType.TEST.ordinal()), new Object[] { Items.book, ModItems.canned_conserve.stackFromEnum(EnumFoodType.JIZZ) });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.book_guide, 1, BookType.RBMK.ordinal()), new Object[] { Items.book, Items.potato });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.book_guide, 1, BookType.HADRON.ordinal()), new Object[] { Items.book, ModItems.fuse });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.book_guide, 1, BookType.STARTER.ordinal()), new Object[] { Items.book, Items.iron_ingot });

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.charger), new Object[] { "G", "S", "C", 'G', Items.glowstone_dust, 'S', OreDictManager.STEEL.ingot(), 'C', ModItems.coil_copper });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.charger, 16), new Object[] { "G", "S", "C", 'G', Blocks.glowstone, 'S', OreDictManager.STEEL.block(), 'C', ModItems.coil_copper_torus });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.press_preheater), new Object[] { "CCC", "SLS", "TST", 'C', ModItems.board_copper, 'S', Blocks.stone, 'L', Fluids.LAVA.getDict(1000), 'T', OreDictManager.W.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.fluid_identifier_multi), new Object[] { "D", "C", "P", 'D', "dye", 'C', ModItems.circuit_aluminium, 'P', OreDictManager.ANY_PLASTIC.ingot() });

		CraftingManager.addShapelessAuto(ItemBattery.getEmptyBattery(ModItems.anchor_remote), new Object[] { OreDictManager.DIAMOND.gem(), ModItems.ducttape, ModItems.circuit_red_copper });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.teleanchor), new Object[] { "ODO", "EAE", "ODO", 'O', Blocks.obsidian, 'D', OreDictManager.DIAMOND.gem(), 'E', ModItems.powder_magic, 'A', ModItems.gem_alexandrite });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.field_disturber), new Object[] { "ICI", "CAC", "ICI", 'I', OreDictManager.STAR.ingot(), 'C', OreDictManager.KEY_CIRCUIT_BISMUTH, 'A', ModItems.gem_alexandrite });
		
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.holotape_image, 1, EnumHoloImage.HOLO_RESTORED.ordinal()), new Object[] { new ItemStack(ModItems.holotape_image, 1, EnumHoloImage.HOLO_DIGAMMA.ordinal()), OreDictManager.KEY_TOOL_SCREWDRIVER, ModItems.ducttape, ModItems.armor_polish });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.holotape_damaged), new Object[] { DictFrame.fromOne(ModItems.holotape_image, EnumHoloImage.HOLO_RESTORED), ModBlocks.muffler, ModItems.crt_display, ModItems.gem_alexandrite /* placeholder for amplifier */ });

		CraftingManager.addRecipeAuto(DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_PNEUMATIC, 4), new Object[] { " I ", "CPC", " I ", 'I', OreDictManager.IRON.ingot(), 'C', OreDictManager.CU.ingot(), 'P', OreDictManager.IRON.plate() });
		CraftingManager.addRecipeAuto(DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_HYDRAULIC, 4), new Object[] { " I ", "CPC", " I ", 'I', OreDictManager.STEEL.ingot(), 'C', OreDictManager.TI.ingot(), 'P', Fluids.LUBRICANT.getDict(1000) });
		CraftingManager.addRecipeAuto(DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_ELECTRIC, 4), new Object[] { " I ", "CPC", " I ", 'I', OreDictManager.ANY_RESISTANTALLOY.ingot(), 'C', OreDictManager.ANY_PLASTIC.ingot(), 'P', ModItems.motor });
		
		Object[] craneCasing = new Object[] {
				Blocks.stonebrick, 1,
				OreDictManager.IRON.ingot(), 2,
				OreDictManager.STEEL.ingot(), 4
		};
		
		for(int i = 0; i < craneCasing.length / 2; i++) {
			Object casing = craneCasing[i * 2];
			int amount = (int) craneCasing[i * 2 + 1];
			CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.crane_inserter, amount), new Object[] { "CCC", "C C", "CBC", 'C', casing, 'B', ModBlocks.conveyor });
			CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.crane_extractor, amount), new Object[] { "CCC", "CPC", "CBC", 'C', casing, 'B', ModBlocks.conveyor, 'P', DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_PNEUMATIC) });
			CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.crane_grabber, amount), new Object[] { "C C", "P P", "CBC", 'C', casing, 'B', ModBlocks.conveyor, 'P', DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_PNEUMATIC) });
		}

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.crane_boxer), new Object[] { "WWW", "WPW", "CCC", 'W', OreDictManager.KEY_PLANKS, 'P', DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_PNEUMATIC), 'C', ModBlocks.conveyor });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.crane_unboxer), new Object[] { "WWW", "WPW", "CCC", 'W', OreDictManager.KEY_STICK, 'P', Items.shears, 'C', ModBlocks.conveyor });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.crane_router), new Object[] { "PIP", "ICI", "PIP", 'P', DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_PNEUMATIC), 'I', ModItems.plate_polymer, 'C', ModItems.circuit_copper });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.crane_splitter), new Object[] { "III", "PCP", "III", 'P', DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_PNEUMATIC), 'I', OreDictManager.STEEL.ingot(), 'C', ModItems.circuit_aluminium });

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.machine_conveyor_press), new Object[] { "CPC", "CBC", "CCC", 'C', OreDictManager.CU.plate(), 'P', ModBlocks.machine_epress, 'B', ModBlocks.conveyor });

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.drone, 1, EnumDroneType.PATROL.ordinal()), new Object[] { "PPP", "HCH", " B ", 'P', OreDictManager.ANY_PLASTIC.ingot(), 'H', ModItems.hull_small_steel, 'C', ModItems.circuit_copper, 'B', ModBlocks.crate_steel });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.drone, 1, EnumDroneType.PATROL_CHUNKLOADING.ordinal()), new Object[] { "E", "D", 'E', Items.ender_pearl, 'D', new ItemStack(ModItems.drone, 1, EnumDroneType.PATROL.ordinal()) });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.drone, 1, EnumDroneType.PATROL_EXPRESS.ordinal()), new Object[] { " P ", "KDK", " P ", 'P', OreDictManager.TI.plateWelded(), 'K', Fluids.KEROSENE.getDict(1_000), 'D', new ItemStack(ModItems.drone, 1, EnumDroneType.PATROL.ordinal()) });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.drone, 1, EnumDroneType.PATROL_EXPRESS_CHUNKLOADING.ordinal()), new Object[] { "E", "D", 'E', Items.ender_pearl, 'D', new ItemStack(ModItems.drone, 1, EnumDroneType.PATROL_EXPRESS.ordinal()) });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.drone, 1, EnumDroneType.PATROL_EXPRESS_CHUNKLOADING.ordinal()), new Object[] { " P ", "KDK", " P ", 'P', OreDictManager.TI.plateWelded(), 'K', Fluids.KEROSENE.getDict(1_000), 'D', new ItemStack(ModItems.drone, 1, EnumDroneType.PATROL_CHUNKLOADING.ordinal()) });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.drone, 1, EnumDroneType.PATROL.ordinal()), new Object[] { new ItemStack(ModItems.drone, 1, EnumDroneType.PATROL_CHUNKLOADING.ordinal()) });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.drone, 1, EnumDroneType.PATROL_EXPRESS.ordinal()), new Object[] { new ItemStack(ModItems.drone, 1, EnumDroneType.PATROL_EXPRESS_CHUNKLOADING.ordinal()) });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.drone, 1, EnumDroneType.REQUEST.ordinal()), new Object[] { "E", "D", 'E', ModItems.circuit_red_copper, 'D', new ItemStack(ModItems.drone, 1, EnumDroneType.PATROL_EXPRESS.ordinal()) });

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.drone_linker), new Object[] { "T", "C", 'T', ModBlocks.drone_waypoint, 'C', ModItems.circuit_copper });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.drone_waypoint, 4), new Object[] { "G", "T", "C", 'G', OreDictManager.KEY_GREEN, 'T', Blocks.redstone_torch, 'C', ModItems.circuit_aluminium });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.drone_crate), new Object[] { "T", "C", 'T', ModBlocks.drone_waypoint, 'C', ModBlocks.crate_steel });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.drone_waypoint_request, 4), new Object[] { "G", "T", "C", 'G', OreDictManager.KEY_BLUE, 'T', Blocks.redstone_torch, 'C', ModItems.circuit_copper });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.drone_crate), new Object[] { "T", "C", "B", 'T', ModBlocks.drone_waypoint_request, 'C', ModBlocks.crate_steel, 'B', ModItems.circuit_red_copper });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.drone_crate_requester), new Object[] { "T", "C", "B", 'T', ModBlocks.drone_waypoint_request, 'C', ModBlocks.crate_steel, 'B', OreDictManager.KEY_YELLOW });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.drone_crate_provider), new Object[] { "T", "C", "B", 'T', ModBlocks.drone_waypoint_request, 'C', ModBlocks.crate_steel, 'B', OreDictManager.KEY_ORANGE });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.drone_dock), new Object[] { "T", "C", "B", 'T', ModBlocks.drone_waypoint_request, 'C', ModBlocks.crate_steel, 'B', ModItems.circuit_gold });
		
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.parts_legendary, EnumLegendaryType.TIER1), new Object[] { ModItems.ingot_chainsteel, OreDictManager.ASBESTOS.ingot(), ModItems.gem_alexandrite });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.parts_legendary, EnumLegendaryType.TIER1, 3), new Object[] { DictFrame.fromOne(ModItems.parts_legendary, EnumLegendaryType.TIER2) });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.parts_legendary, EnumLegendaryType.TIER2), new Object[] { ModItems.ingot_chainsteel, ModItems.ingot_bismuth, ModItems.gem_alexandrite, ModItems.gem_alexandrite });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.parts_legendary, EnumLegendaryType.TIER2, 3), new Object[] { DictFrame.fromOne(ModItems.parts_legendary, EnumLegendaryType.TIER3) });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.parts_legendary, EnumLegendaryType.TIER3), new Object[] { ModItems.ingot_chainsteel, ModItems.ingot_smore, ModItems.gem_alexandrite, ModItems.gem_alexandrite, ModItems.gem_alexandrite });

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gear_large, 1, 0), new Object[] { "III", "ICI", "III", 'I', OreDictManager.IRON.plate(), 'C', OreDictManager.CU.ingot()});
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.gear_large, 1, 1), new Object[] { "III", "ICI", "III", 'I', OreDictManager.STEEL.plate(), 'C', OreDictManager.TI.ingot()});
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.sawblade), new Object[] { "III", "ICI", "III", 'I', OreDictManager.STEEL.plate(), 'C', OreDictManager.IRON.ingot()});

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.foundry_basin), new Object[] { "B B", "B B", "BSB", 'B', ModItems.ingot_firebrick, 'S', Blocks.stone_slab });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.foundry_mold), new Object[] { "B B", "BSB", 'B', ModItems.ingot_firebrick, 'S', Blocks.stone_slab });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.foundry_channel, 4), new Object[] { "B B", " S ", 'B', ModItems.ingot_firebrick, 'S', Blocks.stone_slab });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.foundry_tank), new Object[] { "B B", "I I", "BSB", 'B', ModItems.ingot_firebrick, 'I', OreDictManager.STEEL.ingot(), 'S', Blocks.stone_slab });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.foundry_outlet), new Object[] { ModBlocks.foundry_channel, OreDictManager.STEEL.plate() });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.foundry_slagtap), new Object[] { ModBlocks.foundry_channel, Blocks.stonebrick });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mold_base), new Object[] { " B ", "BIB", " B ", 'B', ModItems.ingot_firebrick, 'I', OreDictManager.IRON.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.brick_fire), new Object[] { "BB", "BB", 'B', ModItems.ingot_firebrick });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ingot_firebrick, 4), new Object[] { ModBlocks.brick_fire });
		
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.filing_cabinet, 1, DecoCabinetEnum.STEEL.ordinal()), new Object[] { " P ", "PIP", " P ", 'P', OreDictManager.STEEL.plate(), 'I', ModItems.plate_polymer });
		
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.vinyl_tile, 4), new Object[] { " I ", "IBI", " I ", 'I', ModItems.plate_polymer, 'B', ModBlocks.brick_light });
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.vinyl_tile, 4, 1), new Object[] { "BB", "BB", 'B', new ItemStack(ModBlocks.vinyl_tile, 1, 0) });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.vinyl_tile), new Object[] { new ItemStack(ModBlocks.vinyl_tile, 1, 1) });
		
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.upgrade_5g), new Object[] { ModItems.upgrade_template, ModItems.gem_alexandrite });
		
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.bdcl), new Object[] { OreDictManager.ANY_TAR.any(), Fluids.WATER.getDict(1_000), OreDictManager.KEY_WHITE });
		
		if(GeneralConfig.enableLBSM && GeneralConfig.enableLBSMSimpleCrafting) {
			CraftingManager.addShapelessAuto(new ItemStack(ModItems.cordite, 3), new Object[] { ModItems.ballistite, Items.gunpowder, new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE) });
			CraftingManager.addShapelessAuto(new ItemStack(ModItems.ingot_semtex, 3), new Object[] { Items.slime_ball, Blocks.tnt, OreDictManager.KNO.dust() });
			CraftingManager.addShapelessAuto(new ItemStack(ModItems.canister_full, 1, Fluids.DIESEL.getID()), new Object[] { new ItemStack(ModItems.canister_full, 1, Fluids.OIL.getID()), OreDictManager.REDSTONE.dust(), ModItems.canister_empty });

			CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.ore_uranium, 1), new Object[] { ModBlocks.ore_uranium_scorched, Items.water_bucket });
			CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.ore_uranium, 8), new Object[] { "OOO", "OBO", "OOO", 'O', ModBlocks.ore_uranium_scorched, 'B', Items.water_bucket });
			CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.ore_nether_uranium, 1), new Object[] { ModBlocks.ore_nether_uranium_scorched, Items.water_bucket });
			CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.ore_nether_uranium, 8), new Object[] { "OOO", "OBO", "OOO", 'O', ModBlocks.ore_nether_uranium_scorched, 'B', Items.water_bucket });
			CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.ore_gneiss_uranium, 1), new Object[] { ModBlocks.ore_gneiss_uranium_scorched, Items.water_bucket });
			CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.ore_gneiss_uranium, 8), new Object[] { "OOO", "OBO", "OOO", 'O', ModBlocks.ore_gneiss_uranium_scorched, 'B', Items.water_bucket });

			CraftingManager.addRecipeAuto(new ItemStack(ModItems.plate_iron, 4), new Object[] { "##", "##", '#', OreDictManager.IRON.ingot() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.plate_gold, 4), new Object[] { "##", "##", '#', OreDictManager.GOLD.ingot() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.plate_aluminium, 4), new Object[] { "##", "##", '#', OreDictManager.AL.ingot() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.plate_titanium, 4), new Object[] { "##", "##", '#', OreDictManager.TI.ingot() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.plate_copper, 4), new Object[] { "##", "##", '#', OreDictManager.CU.ingot() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.plate_lead, 4), new Object[] { "##", "##", '#', OreDictManager.PB.ingot() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.plate_steel, 4), new Object[] { "##", "##", '#', OreDictManager.STEEL.ingot() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.plate_schrabidium, 4), new Object[] { "##", "##", '#', OreDictManager.SA326.ingot() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.plate_advanced_alloy, 4), new Object[] { "##", "##", '#', OreDictManager.ALLOY.ingot() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.plate_saturnite, 4), new Object[] { "##", "##", '#', OreDictManager.BIGMT.ingot() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.plate_combine_steel, 4), new Object[] { "##", "##", '#', OreDictManager.CMB.ingot() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.neutron_reflector, 4), new Object[] { "##", "##", '#', OreDictManager.W.ingot() });

			CraftingManager.addRecipeAuto(new ItemStack(ModItems.wire_aluminium, 16), new Object[] { "###", '#', OreDictManager.AL.ingot() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.wire_copper, 16), new Object[] { "###", '#', OreDictManager.CU.ingot() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.wire_tungsten, 16), new Object[] { "###", '#', OreDictManager.W.ingot() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.wire_red_copper, 16), new Object[] { "###", '#', OreDictManager.MINGRADE.ingot() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.wire_advanced_alloy, 16), new Object[] { "###", '#', OreDictManager.ALLOY.ingot() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.wire_gold, 16), new Object[] { "###", '#', OreDictManager.GOLD.ingot() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.wire_schrabidium, 16), new Object[] { "###", '#', OreDictManager.SA326.ingot() });
			
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.book_of_), new Object[] { "BGB", "GAG", "BGB", 'B', ModItems.egg_balefire_shard, 'G', OreDictManager.GOLD.ingot(), 'A', Items.book });
		}

		if(!GeneralConfig.enable528) {
			CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.struct_launcher_core, 1), new Object[] { "SCS", "SIS", "BEB", 'S', ModBlocks.steel_scaffold, 'I', Blocks.iron_bars, 'C', ModItems.circuit_targeting_tier3, 'B', ModBlocks.struct_launcher, 'E', ModBlocks.machine_battery });
			CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.struct_launcher_core_large, 1), new Object[] { "SIS", "ICI", "BEB", 'S', ModItems.circuit_red_copper, 'I', Blocks.iron_bars, 'C', ModItems.circuit_targeting_tier4, 'B', ModBlocks.struct_launcher, 'E', ModBlocks.machine_battery });
			CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.struct_soyuz_core, 1), new Object[] { "CUC", "TST", "TBT", 'C', ModItems.circuit_targeting_tier4, 'U', ModItems.upgrade_power_3, 'T', ModBlocks.barrel_steel, 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.machine_lithium_battery });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.reactor_sensor, 1), new Object[] { "WPW", "CMC", "PPP", 'W', ModItems.wire_tungsten, 'P', OreDictManager.PB.plate(), 'C', ModItems.circuit_targeting_tier3, 'M', ModItems.magnetron });
			CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.rbmk_console, 1), new Object[] { "BBB", "DGD", "DCD", 'B', OreDictManager.B.ingot(), 'D', ModBlocks.deco_rbmk, 'G', OreDictManager.KEY_ANYPANE, 'C', ModItems.circuit_targeting_tier3 });
			CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.rbmk_crane_console, 1), new Object[] { "BCD", "DDD", 'B', OreDictManager.B.ingot(), 'D', ModBlocks.deco_rbmk, 'C', ModItems.circuit_targeting_tier3 });
			CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.hadron_core, 1), new Object[] { "CCC", "DSD", "CCC", 'C', ModBlocks.hadron_coil_alloy, 'D', ModBlocks.hadron_diode, 'S', ModItems.circuit_schrabidium });
			CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.rbmk_rod, 1), new Object[] { "C", "R", "C", 'C', ModItems.hull_small_steel, 'R', ModBlocks.rbmk_blank });
			CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.rbmk_rod_mod, 1), new Object[] { "BGB", "GRG", "BGB", 'G', OreDictManager.GRAPHITE.block(), 'R', ModBlocks.rbmk_rod, 'B', ModItems.nugget_bismuth });
			CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.rbmk_boiler, 1), new Object[] { "CPC", "CRC", "CPC", 'C', ModItems.board_copper, 'P', ModItems.pipes_steel, 'R', ModBlocks.rbmk_blank });
			CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.rbmk_heater, 1), new Object[] { "CIC", "PRP", "CIC", 'C', ModItems.board_copper, 'P', ModItems.pipes_steel, 'R', ModBlocks.rbmk_blank, 'I', OreDictManager.ANY_PLASTIC.ingot() });
			CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.rbmk_cooler, 1), new Object[] { "IGI", "GCG", "IGI", 'C', ModBlocks.rbmk_blank, 'I', ModItems.plate_polymer, 'G', ModBlocks.steel_grate });
		}
		
		CraftingManager.addShapelessAuto(ModItems.circuit_star_component.stackFromEnum(CircuitComponentType.CHIPSET), new Object[] {
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.BRIDGE_BIOS),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.BRIDGE_BUS),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.BRIDGE_CHIPSET),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.BRIDGE_CMOS),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.BRIDGE_IO),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.BRIDGE_NORTH),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.BRIDGE_SOUTH)
		});
		
		CraftingManager.addShapelessAuto(ModItems.circuit_star_component.stackFromEnum(CircuitComponentType.CPU), new Object[] {
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.CPU_CACHE),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.CPU_CLOCK),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.CPU_EXT),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.CPU_LOGIC),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.CPU_REGISTER),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.CPU_SOCKET)
		});
		
		CraftingManager.addShapelessAuto(ModItems.circuit_star_component.stackFromEnum(CircuitComponentType.RAM), new Object[] {
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.MEM_SOCKET),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.MEM_16K_A),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.MEM_16K_B),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.MEM_16K_C),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.MEM_16K_D)
		});
		
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.circuit_star), new Object[] {
				ModItems.circuit_star_component.stackFromEnum(CircuitComponentType.CHIPSET),
				ModItems.circuit_star_component.stackFromEnum(CircuitComponentType.CPU),
				ModItems.circuit_star_component.stackFromEnum(CircuitComponentType.RAM),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.BOARD_TRANSISTOR),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.BOARD_BLANK)
		});

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.sliding_blast_door_skin), "SPS", "DPD", "SPS", 'P', Items.paper, 'D', "dye", 'S', OreDictManager.STEEL.plate());
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.sliding_blast_door_skin, 1, 1), new ItemStack(ModItems.sliding_blast_door_skin, 1, 0));
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.sliding_blast_door_skin, 1, 2), new ItemStack(ModItems.sliding_blast_door_skin, 1, 1));
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.sliding_blast_door_skin), new ItemStack(ModItems.sliding_blast_door_skin, 1, 2));

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.cm_block, 4, 0), " I ", "IPI", " I ", 'I', OreDictManager.STEEL.ingot(), 'P', OreDictManager.STEEL.plateCast());
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.cm_block, 4, 1), " I ", "IPI", " I ", 'I', OreDictManager.ALLOY.ingot(), 'P', OreDictManager.ALLOY.plateCast());
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.cm_block, 4, 2), " I ", "IPI", " I ", 'I', OreDictManager.DESH.ingot(), 'P', OreDictManager.DESH.plateCast());
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.cm_block, 4, 3), " I ", "IPI", " I ", 'I', OreDictManager.ANY_RESISTANTALLOY.ingot(), 'P', OreDictManager.ANY_RESISTANTALLOY.plateCast());
		
		for(int i = 0; i < 4; i++) {
			CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.cm_sheet, 16, i), "BB", "BB", 'B', new ItemStack(ModBlocks.cm_block, 1, i));
			CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.cm_tank, 4, i), " B ", "BGB", " B ", 'B', new ItemStack(ModBlocks.cm_block, 1, i), 'G', OreDictManager.KEY_ANYGLASS);
			CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.cm_port, 1, i), "P", "B", "P", 'B', new ItemStack(ModBlocks.cm_block, 1, i), 'P', OreDictManager.IRON.plate());
		}

		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.cm_engine, 1, 0), " I ", "IMI", " I ", 'I', OreDictManager.STEEL.ingot(), 'M', ModItems.motor);
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.cm_engine, 1, 1), " I ", "IMI", " I ", 'I', OreDictManager.STEEL.ingot(), 'M', ModItems.motor_desh);
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.cm_engine, 1, 2), " I ", "IMI", " I ", 'I', OreDictManager.STEEL.ingot(), 'M', ModItems.motor_bismuth);
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.cm_circuit, 1, 0), " I ", "IMI", " I ", 'I', OreDictManager.STEEL.ingot(), 'M', ModItems.circuit_aluminium);
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.cm_circuit, 1, 1), " I ", "IMI", " I ", 'I', OreDictManager.STEEL.ingot(), 'M', ModItems.circuit_copper);
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.cm_circuit, 1, 2), " I ", "IMI", " I ", 'I', OreDictManager.STEEL.ingot(), 'M', ModItems.circuit_red_copper);
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.cm_circuit, 1, 3), " I ", "IMI", " I ", 'I', OreDictManager.STEEL.ingot(), 'M', ModItems.circuit_gold);
		CraftingManager.addRecipeAuto(new ItemStack(ModBlocks.cm_circuit, 1, 4), " I ", "IMI", " I ", 'I', OreDictManager.STEEL.ingot(), 'M', ModItems.circuit_schrabidium);
	}
	
	
	public static void crumple() {
		
		if(Loader.isModLoaded("Mekanism")) {
			
			List<IRecipe> toDestroy = new ArrayList<>();
			
			Block mb = (Block) Block.blockRegistry.getObject("Mekanism:MachineBlock");
			ItemStack digiminer = new ItemStack(mb, 1, 4);
			
			for(Object o : net.minecraft.item.crafting.CraftingManager.getInstance().getRecipeList()) {
				
				if(o instanceof IRecipe) {
					IRecipe rec = (IRecipe)o;
					ItemStack stack = rec.getRecipeOutput();
					
					if(stack != null && stack.getItem() == digiminer.getItem() && stack.getItemDamage() == digiminer.getItemDamage()) {
						toDestroy.add(rec);
					}
				}
			}
			
			if(toDestroy.size() > 0) {
				net.minecraft.item.crafting.CraftingManager.getInstance().getRecipeList().removeAll(toDestroy);
			}
		}
	}
	
	//option 1: find every entry that needs to be ore dicted and change the recipe method by hand and commit to doing it right in the future
	//option 2: just make the computer do all the stupid work for us
	public static void addRecipeAuto(ItemStack result, Object... ins) {
		
		boolean shouldUseOD = false;
		boolean engage = false;
		
		for (Object ingredient : ins) {
			if(ingredient instanceof String) {
				
				if(engage) {
					shouldUseOD = true;
					break;
				}
			} else {
				engage = true;
			}
		}
		
		if(shouldUseOD)
			GameRegistry.addRecipe(new ShapedOreRecipe(result, ins));
		else
			GameRegistry.addRecipe(result, ins);
	}
	
	public static void addShapelessAuto(ItemStack result, Object... ins) {
		
		boolean shouldUseOD = false;
		
		for (Object ingredient : ins) {
			if(ingredient instanceof String) {
				shouldUseOD = true;
				break;
			}
		}
		
		if(shouldUseOD)
			GameRegistry.addRecipe(new ShapelessOreRecipe(result, ins));
		else
			GameRegistry.addShapelessRecipe(result, ins);
	}
}
