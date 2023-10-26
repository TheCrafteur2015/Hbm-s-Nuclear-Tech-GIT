package com.hbm.crafting;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ItemGenericPart.EnumPartType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBattery;
import com.hbm.items.tool.ItemBlowtorch;
import com.hbm.items.tool.ItemModMinecart;
import com.hbm.items.tool.ItemModMinecart.EnumCartBase;
import com.hbm.items.tool.ItemModMinecart.EnumMinecart;
import com.hbm.items.tool.ItemToolAbilityFueled;
import com.hbm.main.CraftingManager;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * For mining and utility tools
 * @author hbm
 */
public class ToolRecipes {
	
	public static void register() {
		
		//Regular tools
		ToolRecipes.addSword(	OreDictManager.STEEL.ingot(), ModItems.steel_sword);
		ToolRecipes.addPickaxe(	OreDictManager.STEEL.ingot(), ModItems.steel_pickaxe);
		ToolRecipes.addAxe(		OreDictManager.STEEL.ingot(), ModItems.steel_axe);
		ToolRecipes.addShovel(	OreDictManager.STEEL.ingot(), ModItems.steel_shovel);
		ToolRecipes.addHoe(		OreDictManager.STEEL.ingot(), ModItems.steel_hoe);
		ToolRecipes.addSword(	OreDictManager.TI.ingot(), ModItems.titanium_sword);
		ToolRecipes.addPickaxe(	OreDictManager.TI.ingot(), ModItems.titanium_pickaxe);
		ToolRecipes.addAxe(		OreDictManager.TI.ingot(), ModItems.titanium_axe);
		ToolRecipes.addShovel(	OreDictManager.TI.ingot(), ModItems.titanium_shovel);
		ToolRecipes.addHoe(		OreDictManager.TI.ingot(), ModItems.titanium_hoe);
		ToolRecipes.addSword(	OreDictManager.CO.ingot(), ModItems.cobalt_sword);
		ToolRecipes.addPickaxe(	OreDictManager.CO.ingot(), ModItems.cobalt_pickaxe);
		ToolRecipes.addAxe(		OreDictManager.CO.ingot(), ModItems.cobalt_axe);
		ToolRecipes.addShovel(	OreDictManager.CO.ingot(), ModItems.cobalt_shovel);
		ToolRecipes.addHoe(		OreDictManager.CO.ingot(), ModItems.cobalt_hoe);
		ToolRecipes.addSword(	OreDictManager.ALLOY.ingot(), ModItems.alloy_sword);
		ToolRecipes.addPickaxe(	OreDictManager.ALLOY.ingot(), ModItems.alloy_pickaxe);
		ToolRecipes.addAxe(		OreDictManager.ALLOY.ingot(), ModItems.alloy_axe);
		ToolRecipes.addShovel(	OreDictManager.ALLOY.ingot(), ModItems.alloy_shovel);
		ToolRecipes.addHoe(		OreDictManager.ALLOY.ingot(), ModItems.alloy_hoe);
		ToolRecipes.addSword(	OreDictManager.CMB.ingot(), ModItems.cmb_sword);
		ToolRecipes.addPickaxe(	OreDictManager.CMB.ingot(), ModItems.cmb_pickaxe);
		ToolRecipes.addAxe(		OreDictManager.CMB.ingot(), ModItems.cmb_axe);
		ToolRecipes.addShovel(	OreDictManager.CMB.ingot(), ModItems.cmb_shovel);
		ToolRecipes.addHoe(		OreDictManager.CMB.ingot(), ModItems.cmb_hoe);
		ToolRecipes.addSword(	OreDictManager.DESH.ingot(), ModItems.desh_sword);
		ToolRecipes.addPickaxe(	OreDictManager.DESH.ingot(), ModItems.desh_pickaxe);
		ToolRecipes.addAxe(		OreDictManager.DESH.ingot(), ModItems.desh_axe);
		ToolRecipes.addShovel(	OreDictManager.DESH.ingot(), ModItems.desh_shovel);
		ToolRecipes.addHoe(		OreDictManager.DESH.ingot(), ModItems.desh_hoe);
		
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.elec_sword, 1), new Object[] { "RPR", "RPR", " B ", 'P', OreDictManager.POLYMER.ingot(), 'D', OreDictManager.DURA.ingot(), 'R', ModItems.bolt_dura_steel, 'M', ModItems.motor, 'B', ModItems.battery_lithium });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.elec_pickaxe, 1), new Object[] { "RDM", " PB", " P ", 'P', OreDictManager.POLYMER.ingot(), 'D', OreDictManager.DURA.ingot(), 'R', ModItems.bolt_dura_steel, 'M', ModItems.motor, 'B', ModItems.battery_lithium });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.elec_axe, 1), new Object[] { " DP", "RRM", " PB", 'P', OreDictManager.POLYMER.ingot(), 'D', OreDictManager.DURA.ingot(), 'R', ModItems.bolt_dura_steel, 'M', ModItems.motor, 'B', ModItems.battery_lithium });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.elec_shovel, 1), new Object[] { "  P", "RRM", "  B", 'P', OreDictManager.POLYMER.ingot(), 'D', OreDictManager.DURA.ingot(), 'R', ModItems.bolt_dura_steel, 'M', ModItems.motor, 'B', ModItems.battery_lithium });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.centri_stick, 1), new Object[] { ModItems.centrifuge_element, ModItems.energy_core, OreDictManager.KEY_STICK });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.smashing_hammer, 1), new Object[] { "STS", "SPS", " P ", 'S', OreDictManager.STEEL.block(), 'T', OreDictManager.W.block(), 'P', OreDictManager.POLYMER.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.meteorite_sword, 1), new Object[] { "  B", "GB ", "SG ", 'B', ModItems.blade_meteorite, 'G', OreDictManager.GOLD.plate(), 'S', OreDictManager.KEY_STICK });

		//Drax
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.drax, 1), new Object[] { "BDS", "CDC", "FMF", 'B', ModItems.starmetal_pickaxe, 'S', ModItems.starmetal_shovel, 'C', OreDictManager.CO.ingot(), 'F', ModItems.fusion_core, 'D', OreDictManager.DESH.ingot(), 'M', ModItems.motor_desh });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.drax_mk2, 1), new Object[] { "SCS", "IDI", "FEF", 'S', OreDictManager.STAR.ingot(), 'C', ModItems.crystal_trixite, 'I', OreDictManager.BIGMT.ingot(), 'D', ModItems.drax, 'F', ModItems.fusion_core, 'E', ModItems.circuit_targeting_tier5 });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.drax_mk3, 1), new Object[] { "ECE", "CDC", "SBS", 'E', ModBlocks.block_euphemium_cluster, 'C', ModItems.crystal_schrabidium, 'D', ModItems.drax_mk2, 'S', ModItems.circuit_targeting_tier6, 'B', ItemBattery.getFullBattery(ModItems.battery_spark) });

		//Super pickaxes
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.bismuth_pickaxe, 1), new Object[] { " BM", "BPB", "TB ", 'B', ModItems.ingot_bismuth, 'M', ModItems.ingot_meteorite, 'P', ModItems.starmetal_pickaxe, 'T', ModItems.bolt_tungsten });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.volcanic_pickaxe, 1), new Object[] { " BM", "BPB", "TB ", 'B', ModItems.gem_volcanic, 'M', ModItems.ingot_meteorite, 'P', ModItems.starmetal_pickaxe, 'T', ModItems.bolt_tungsten });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.chlorophyte_pickaxe, 1), new Object[] { " SD", "APS", "FA ", 'S', ModItems.blades_steel, 'D', ModItems.powder_chlorophyte, 'A', OreDictManager.FIBER.ingot(), 'P', ModItems.bismuth_pickaxe, 'F', ModItems.bolt_dura_steel });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.chlorophyte_pickaxe, 1), new Object[] { " SD", "APS", "FA ", 'S', ModItems.blades_steel, 'D', ModItems.powder_chlorophyte, 'A', OreDictManager.FIBER.ingot(), 'P', ModItems.volcanic_pickaxe, 'F', ModItems.bolt_dura_steel });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mese_pickaxe, 1), new Object[] { " SD", "APS", "FA ", 'S', ModItems.blades_desh, 'D', ModItems.powder_dineutronium, 'A', ModItems.plate_paa, 'P', ModItems.chlorophyte_pickaxe, 'F', ModItems.shimmer_handle });

		//Chainsaws
		CraftingManager.addRecipeAuto(ItemToolAbilityFueled.getEmptyTool(ModItems.chainsaw), new Object[] { "CCH", "BBP", "CCE", 'H', ModItems.hull_small_steel, 'B', ModItems.blades_steel, 'P', ModItems.piston_selenium, 'C', ModBlocks.chain, 'E', ModItems.canister_empty });

		//Misc
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.euphemium_stopper, 1), new Object[] { "I", "S", "S", 'I', OreDictManager.EUPH.ingot(), 'S', OreDictManager.KEY_STICK });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.crowbar, 1), new Object[] { "II", " I", " I", 'I', OreDictManager.STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.bottle_opener, 1), new Object[] { "S", "P", 'S', OreDictManager.STEEL.plate(), 'P', OreDictManager.KEY_PLANKS });

		//Matches
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.matchstick, 16), new Object[] { "I", "S", 'I', OreDictManager.S.dust(), 'S', OreDictManager.KEY_STICK });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.matchstick, 24), new Object[] { "I", "S", 'I', OreDictManager.P_RED.dust(), 'S', OreDictManager.KEY_STICK });

		//Gavels
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.wood_gavel, 1), new Object[] { "SWS", " R ", " R ", 'S', OreDictManager.KEY_SLAB, 'W', OreDictManager.KEY_LOG, 'R', OreDictManager.KEY_STICK });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.lead_gavel, 1), new Object[] { "PIP", "IGI", "PIP", 'P', ModItems.pellet_buckshot, 'I', OreDictManager.PB.ingot(), 'G', ModItems.wood_gavel });

		//Misc weapons
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.saw, 1), new Object[] { "IIL", "PP ", 'P', OreDictManager.STEEL.plate(), 'I', OreDictManager.STEEL.ingot(), 'L', Items.leather });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.bat, 1), new Object[] { "P", "P", "S", 'S', OreDictManager.STEEL.plate(), 'P', OreDictManager.KEY_PLANKS });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.bat_nail, 1), new Object[] { ModItems.bat, OreDictManager.STEEL.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.golf_club, 1), new Object[] { "IP", " P", " P", 'P', OreDictManager.STEEL.plate(), 'I', OreDictManager.STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.pipe_rusty, 1), new Object[] { "II", " I", " I", 'I', OreDictManager.IRON.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.pipe_lead, 1), new Object[] { "II", " I", " I", 'I', OreDictManager.PB.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.ullapool_caber, 1), new Object[] { "ITI", " S ", " S ", 'I', OreDictManager.IRON.plate(), 'T', Blocks.tnt, 'S', OreDictManager.KEY_STICK });

		//Utility
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.designator, 1), new Object[] { "  A", "#B#", "#B#", '#', OreDictManager.IRON.plate(), 'A', OreDictManager.STEEL.plate(), 'B', ModItems.circuit_red_copper });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.designator_range, 1), new Object[] { "RRD", "PIC", "  P", 'P', OreDictManager.STEEL.plate(), 'R', Items.redstone, 'C', ModItems.circuit_gold, 'D', ModItems.designator, 'I', OreDictManager.STEEL.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.designator_manual, 1), new Object[] { "  A", "#C#", "#B#", '#', OreDictManager.POLYMER.ingot(), 'A', OreDictManager.PB.plate(), 'B', ModItems.circuit_gold, 'C', ModItems.designator });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.designator_arty_range, 1), new Object[] { "M", "C", "P", 'M', ModItems.magnetron, 'C', ModItems.circuit_gold, 'P', OreDictManager.ANY_PLASTIC.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.linker, 1), new Object[] { "I I", "ICI", "GGG", 'I', OreDictManager.IRON.plate(), 'G', OreDictManager.GOLD.plate(), 'C', ModItems.circuit_gold });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.oil_detector, 1), new Object[] { "W I", "WCI", "PPP", 'W', ModItems.wire_gold, 'I', OreDictManager.CU.ingot(), 'C', ModItems.circuit_red_copper, 'P', OreDictManager.STEEL.plate528() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.turret_chip, 1), new Object[] { "WWW", "CPC", "WWW", 'W', ModItems.wire_gold, 'P', OreDictManager.POLYMER.ingot(), 'C', ModItems.circuit_gold, });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.survey_scanner, 1), new Object[] { "SWS", " G ", "PCP", 'W', ModItems.wire_gold, 'P', OreDictManager.POLYMER.ingot(), 'C', ModItems.circuit_gold, 'S', OreDictManager.STEEL.plate528(), 'G', OreDictManager.GOLD.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.geiger_counter, 1), new Object[] { "GPP", "WCS", "WBB", 'W', ModItems.wire_gold, 'P', ModItems.plate_polymer, 'C', ModItems.circuit_red_copper, 'G', OreDictManager.GOLD.ingot(), 'S', OreDictManager.STEEL.plate528(), 'B', ModItems.ingot_beryllium });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.dosimeter, 1), new Object[] { "WGW", "WCW", "WBW", 'W', OreDictManager.KEY_PLANKS, 'G', OreDictManager.KEY_ANYPANE, 'C', ModItems.circuit_aluminium, 'B', OreDictManager.BE.ingot() });
		CraftingManager.addShapelessAuto(new ItemStack(ModBlocks.geiger), new Object[] { ModItems.geiger_counter });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.digamma_diagnostic), new Object[] { ModItems.geiger_counter, OreDictManager.PO210.billet(), OreDictManager.ASBESTOS.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.pollution_detector, 1), new Object[] { "SFS", "SCS", " S ", 'S', OreDictManager.STEEL.plate(), 'F', ModItems.filter_coal, 'C', ModItems.circuit_copper });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.defuser, 1), new Object[] { " PS", "P P", " P ", 'P', OreDictManager.POLYMER.ingot(), 'S', OreDictManager.STEEL.plate() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.coltan_tool, 1), new Object[] { "ACA", "CXC", "ACA", 'A', OreDictManager.ALLOY.ingot(), 'C', OreDictManager.CINNABAR.crystal(), 'X', Items.compass });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.reacher, 1), new Object[] { "BIB", "P P", "B B", 'B', ModItems.bolt_tungsten, 'I', OreDictManager.W.ingot(), 'P', ModItems.plate_polymer });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.bismuth_tool, 1), new Object[] { "TBT", "SRS", "SCS", 'T', OreDictManager.TA.nugget(), 'B', ModItems.nugget_bismuth, 'S', OreDictManager.ANY_RESISTANTALLOY.ingot(), 'R', ModItems.reacher, 'C', ModItems.circuit_aluminium });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.sat_designator, 1), new Object[] { "RRD", "PIC", "  P", 'P', OreDictManager.GOLD.plate(), 'R', Items.redstone, 'C', ModItems.circuit_gold, 'D', ModItems.sat_chip, 'I', OreDictManager.GOLD.ingot() });

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.mirror_tool), new Object[] { " A ", " IA", "I  ", 'A', OreDictManager.AL.ingot(), 'I', OreDictManager.IRON.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.rbmk_tool), new Object[] { " A ", " IA", "I  ", 'A', OreDictManager.PB.ingot(), 'I', OreDictManager.IRON.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.power_net_tool), new Object[] { "WRW", " I ", " B ", 'W', ModItems.wire_red_copper, 'R', OreDictManager.REDSTONE.dust(), 'I', OreDictManager.IRON.ingot(), 'B', ModItems.battery_su });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.analysis_tool), new Object[] { "  G", " S ", "S  ", 'G', OreDictManager.KEY_ANYPANE, 'S', OreDictManager.STEEL.ingot() });

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.kit_toolbox_empty), new Object[] { "CCC", "CIC", 'C', OreDictManager.CU.plate(), 'I', OreDictManager.IRON.ingot() });

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.screwdriver, 1), new Object[] { "  I", " I ", "S  ", 'S', OreDictManager.STEEL.ingot(), 'I', OreDictManager.IRON.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.screwdriver_desh, 1), new Object[] { "  I", " I ", "S  ", 'S', OreDictManager.ANY_PLASTIC.ingot(), 'I', OreDictManager.DESH.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.hand_drill), new Object[] { " D", "S ", " S", 'D', OreDictManager.DURA.ingot(), 'S', OreDictManager.KEY_STICK });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.hand_drill_desh), new Object[] { " D", "S ", " S", 'D', OreDictManager.DESH.ingot(), 'S', OreDictManager.ANY_PLASTIC.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.chemistry_set), new Object[] { "GIG", "GCG", 'G', OreDictManager.KEY_ANYGLASS, 'I', OreDictManager.IRON.ingot(), 'C', OreDictManager.CU.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.chemistry_set_boron), new Object[] { "GIG", "GCG", 'G', ModBlocks.glass_boron, 'I', OreDictManager.STEEL.ingot(), 'C', OreDictManager.CO.ingot() });
		CraftingManager.addRecipeAuto(ItemBlowtorch.getEmptyTool(ModItems.blowtorch), new Object[] { "CC ", " I ", "CCC", 'C', OreDictManager.CU.plate528(), 'I', OreDictManager.IRON.ingot() });
		CraftingManager.addRecipeAuto(ItemBlowtorch.getEmptyTool(ModItems.acetylene_torch), new Object[] { "SS ", " PS", " T ", 'S', OreDictManager.STEEL.plate528(), 'P', OreDictManager.ANY_PLASTIC.ingot(), 'T', ModItems.tank_steel });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.boltgun), new Object[] { "DPS", " RD", " D ", 'D', OreDictManager.DURA.ingot(), 'P', DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_PNEUMATIC), 'R', OreDictManager.RUBBER.ingot(), 'S', ModItems.hull_small_steel });
		
		//Bobmazon
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.bobmazon_materials), new Object[] { Items.book, Items.gold_nugget, Items.string });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.bobmazon_machines), new Object[] { Items.book, Items.gold_nugget, OreDictManager.KEY_RED });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.bobmazon_weapons), new Object[] { Items.book, Items.gold_nugget, OreDictManager.KEY_GRAY });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.bobmazon_tools), new Object[] { Items.book, Items.gold_nugget, OreDictManager.KEY_GREEN });
		
		//Carts
		CraftingManager.addRecipeAuto(ItemModMinecart.createCartItem(EnumCartBase.WOOD, EnumMinecart.EMPTY), new Object[] { "P P", "WPW", 'P',OreDictManager.KEY_SLAB, 'W', OreDictManager.KEY_PLANKS });
		CraftingManager.addRecipeAuto(ItemModMinecart.createCartItem(EnumCartBase.STEEL, EnumMinecart.EMPTY), new Object[] { "P P", "IPI", 'P', OreDictManager.STEEL.plate(), 'I', OreDictManager.STEEL.ingot() });
		CraftingManager.addShapelessAuto(ItemModMinecart.createCartItem(EnumCartBase.PAINTED, EnumMinecart.EMPTY), new Object[] { ItemModMinecart.createCartItem(EnumCartBase.STEEL, EnumMinecart.EMPTY), OreDictManager.KEY_RED });
		
		for(EnumCartBase base : EnumCartBase.values()) {
			
			if(EnumMinecart.DESTROYER.supportsBase(base))	CraftingManager.addRecipeAuto(ItemModMinecart.createCartItem(base, EnumMinecart.DESTROYER), new Object[] { "S S", "BLB", "SCS", 'S', OreDictManager.STEEL.ingot(), 'B', ModItems.blades_steel, 'L', Fluids.LAVA.getDict(1000), 'C', ItemModMinecart.createCartItem(base, EnumMinecart.EMPTY) });
			if(EnumMinecart.POWDER.supportsBase(base))		CraftingManager.addRecipeAuto(ItemModMinecart.createCartItem(base, EnumMinecart.POWDER), new Object[] { "PPP", "PCP", "PPP", 'P', Items.gunpowder, 'C', ItemModMinecart.createCartItem(base, EnumMinecart.EMPTY) });
			if(EnumMinecart.SEMTEX.supportsBase(base))		CraftingManager.addRecipeAuto(ItemModMinecart.createCartItem(base, EnumMinecart.SEMTEX), new Object[] { "S", "C", 'S', ModBlocks.semtex, 'C', ItemModMinecart.createCartItem(base, EnumMinecart.EMPTY) });
		}
		net.minecraft.item.crafting.CraftingManager.getInstance().addRecipe(DictFrame.fromOne(ModItems.cart, EnumMinecart.CRATE), new Object[] { "C", "S", 'C', ModBlocks.crate_steel, 'S', Items.minecart }).func_92100_c();
		
		//Configged
		if(GeneralConfig.enableLBSM && GeneralConfig.enableLBSMSimpleToolRecipes) {
			ToolRecipes.addSword(	OreDictManager.CO.block(), ModItems.cobalt_decorated_sword);
			ToolRecipes.addPickaxe(	OreDictManager.CO.block(), ModItems.cobalt_decorated_pickaxe);
			ToolRecipes.addAxe(		OreDictManager.CO.block(), ModItems.cobalt_decorated_axe);
			ToolRecipes.addShovel(	OreDictManager.CO.block(), ModItems.cobalt_decorated_shovel);
			ToolRecipes.addHoe(		OreDictManager.CO.block(), ModItems.cobalt_decorated_hoe);
			ToolRecipes.addSword(	OreDictManager.STAR.ingot(), ModItems.starmetal_sword);
			ToolRecipes.addPickaxe(	OreDictManager.STAR.ingot(), ModItems.starmetal_pickaxe);
			ToolRecipes.addAxe(		OreDictManager.STAR.ingot(), ModItems.starmetal_axe);
			ToolRecipes.addShovel(	OreDictManager.STAR.ingot(), ModItems.starmetal_shovel);
			ToolRecipes.addHoe(		OreDictManager.STAR.ingot(), ModItems.starmetal_hoe);
			ToolRecipes.addSword(	OreDictManager.SA326.ingot(), ModItems.schrabidium_sword);
			ToolRecipes.addPickaxe(	OreDictManager.SA326.ingot(), ModItems.schrabidium_pickaxe);
			ToolRecipes.addAxe(		OreDictManager.SA326.ingot(), ModItems.schrabidium_axe);
			ToolRecipes.addShovel(	OreDictManager.SA326.ingot(), ModItems.schrabidium_shovel);
			ToolRecipes.addHoe(		OreDictManager.SA326.ingot(), ModItems.schrabidium_hoe);
		} else {
			/*
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.cobalt_decorated_sword, 1), new Object[] { " I ", " I ", "SBS", 'I', CO.ingot(), 'S', ModItems.ingot_meteorite_forged, 'B', ModItems.cobalt_sword });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.cobalt_decorated_pickaxe, 1), new Object[] { "III", " B ", " S ", 'I', CO.ingot(), 'S', ModItems.ingot_meteorite_forged, 'B', ModItems.cobalt_pickaxe });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.cobalt_decorated_axe, 1), new Object[] { "II", "IB", " S", 'I', CO.ingot(), 'S', ModItems.ingot_meteorite_forged, 'B', ModItems.cobalt_axe });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.cobalt_decorated_shovel, 1), new Object[] { "I", "B", "S", 'I', CO.ingot(), 'S', ModItems.ingot_meteorite_forged, 'B', ModItems.cobalt_shovel });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.cobalt_decorated_hoe, 1), new Object[] { "II", " B", " S", 'I', CO.ingot(), 'S', ModItems.ingot_meteorite_forged, 'B', ModItems.cobalt_hoe });

			 */
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.starmetal_sword, 1), new Object[] { " I ", " B ", "ISI", 'I', OreDictManager.STAR.ingot(), 'S', ModItems.ring_starmetal, 'B', ModItems.cobalt_decorated_sword });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.starmetal_pickaxe, 1), new Object[] { "ISI", " B ", " I ", 'I', OreDictManager.STAR.ingot(), 'S', ModItems.ring_starmetal, 'B', ModItems.cobalt_decorated_pickaxe });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.starmetal_axe, 1), new Object[] { "IS", "IB", " I", 'I', OreDictManager.STAR.ingot(), 'S', ModItems.ring_starmetal, 'B', ModItems.cobalt_decorated_axe });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.starmetal_shovel, 1), new Object[] { "I", "B", "I", 'I', OreDictManager.STAR.ingot(), 'B', ModItems.cobalt_decorated_shovel });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.starmetal_hoe, 1), new Object[] { "IS", " B", " I", 'I', OreDictManager.STAR.ingot(), 'S', ModItems.ring_starmetal, 'B', ModItems.cobalt_decorated_hoe });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.schrabidium_sword, 1), new Object[] { "I", "W", "S", 'I', OreDictManager.SA326.block(), 'W', ModItems.desh_sword, 'S', OreDictManager.POLYMER.ingot() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.schrabidium_pickaxe, 1), new Object[] { "BSB", " W ", " P ", 'B', ModItems.blades_desh, 'S', OreDictManager.SA326.block(), 'W', ModItems.desh_pickaxe, 'P', OreDictManager.POLYMER.ingot() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.schrabidium_axe, 1), new Object[] { "BS", "BW", " P", 'B', ModItems.blades_desh, 'S', OreDictManager.SA326.block(), 'W', ModItems.desh_axe, 'P', OreDictManager.POLYMER.ingot() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.schrabidium_shovel, 1), new Object[] { "B", "W", "P", 'B', OreDictManager.SA326.block(), 'W', ModItems.desh_shovel, 'P', OreDictManager.POLYMER.ingot() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.schrabidium_hoe, 1), new Object[] { "IW", " S", " S", 'I', OreDictManager.SA326.ingot(), 'W', ModItems.desh_hoe, 'S', OreDictManager.POLYMER.ingot() });
		}
	}

	//Common wrappers
	public static void addSword(Object ingot, Item sword) {
		ToolRecipes.addTool(ingot, sword, ToolRecipes.patternSword);
	}
	public static void addPickaxe(Object ingot, Item pick) {
		ToolRecipes.addTool(ingot, pick, ToolRecipes.patternPick);
	}
	public static void addAxe(Object ingot, Item axe) {
		ToolRecipes.addTool(ingot, axe, ToolRecipes.patternAxe);
	}
	public static void addShovel(Object ingot, Item shovel) {
		ToolRecipes.addTool(ingot, shovel, ToolRecipes.patternShovel);
	}
	public static void addHoe(Object ingot, Item hoe) {
		ToolRecipes.addTool(ingot, hoe, ToolRecipes.patternHoe);
	}
	
	public static void addTool(Object ingot, Item tool, String[] pattern) {
		CraftingManager.addRecipeAuto(new ItemStack(tool), new Object[] { pattern, 'X', ingot, '#', OreDictManager.KEY_STICK });
	}
	
	public static final String[] patternSword = new String[] {"X", "X", "#"};
	public static final String[] patternPick = new String[] {"XXX", " # ", " # "};
	public static final String[] patternAxe = new String[] {"XX", "X#", " #"};
	public static final String[] patternShovel = new String[] {"X", "#", "#"};
	public static final String[] patternHoe = new String[] {"XX", " #", " #"};
}
