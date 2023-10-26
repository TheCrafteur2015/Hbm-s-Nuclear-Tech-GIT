package com.hbm.inventory.recipes;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.items.ItemAmmoEnums;
import com.hbm.items.ItemGenericPart.EnumPartType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemAssemblyTemplate;
import com.hbm.items.machine.ItemDrillbit.EnumDrillType;
import com.hbm.items.machine.ItemPistons.EnumPistonType;
import com.hbm.items.weapon.ItemAmmoHIMARS;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.common.Loader;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class AssemblerRecipes {

	public static File config;
	public static File template;
	private static final Gson gson = new Gson();
	public static HashMap<ComparableStack, AStack[]> recipes = new HashMap<>();
	public static HashMap<ComparableStack, Integer> time = new HashMap<>();
	public static List<ComparableStack> recipeList = new ArrayList<>();
	public static HashMap<ComparableStack, HashSet<Item>> hidden = new HashMap<>();
	
	/**
	 * Pre-Init phase: Finds the recipe config (if exists) and checks if a template is present, if not it generates one.
	 * @param dir The suggested config folder
	 */
	public static void preInit(File dir) {
		
		if(dir == null || !dir.isDirectory())
			return;
		
		AssemblerRecipes.template = dir;
		
		List<File> files = Arrays.asList(dir.listFiles());
		
		for(File file : files) {
			if(file.getName().equals("hbmAssembler.json")) {
				AssemblerRecipes.config = file;
			}
		}
	}
	
	public static void loadRecipes() {
		
		if(AssemblerRecipes.config == null) {
			AssemblerRecipes.registerDefaults();
		} else {
			AssemblerRecipes.loadJSONRecipes();
		}
		
		AssemblerRecipes.generateList();
		AssemblerRecipes.saveTemplateJSON(AssemblerRecipes.template);
	}
	
	/**
	 * Generates an ordered list of outputs, used by the template item to generate subitems
	 */
	private static void generateList() {
		
		List<ComparableStack> list = new ArrayList<>(AssemblerRecipes.recipes.keySet());
		Collections.sort(list);
		AssemblerRecipes.recipeList = list;
	}
	
	public static ItemStack getOutputFromTempate(ItemStack stack) {
		
		if(stack != null && stack.getItem() instanceof ItemAssemblyTemplate) {
			
			ComparableStack comp = ItemAssemblyTemplate.readType(stack);
			
			//NEW
			if(comp != null) {
				return comp.toStack();
			}
			
			//LEGACY
			int i = stack.getItemDamage();
			if(i >= 0 && i < AssemblerRecipes.recipeList.size()) {
				return AssemblerRecipes.recipeList.get(i).toStack();
			}
		}
		
		return null;
	}
	
	public static List<AStack> getRecipeFromTempate(ItemStack stack) {
		
		if(stack != null && stack.getItem() instanceof ItemAssemblyTemplate) {
			
			//NEW
			ComparableStack compStack = ItemAssemblyTemplate.readType(stack);
			if(compStack != null) {
				AStack[] ret = AssemblerRecipes.recipes.get(compStack);
				return ret == null ? null : Arrays.asList(ret);
			}
			
			//LEGACY
			int i = stack.getItemDamage();
			if(i >= 0 && i < AssemblerRecipes.recipeList.size()) {
				ItemStack out = AssemblerRecipes.recipeList.get(i).toStack();
				
				if(out != null) {
					ComparableStack comp = new ComparableStack(out);
					AStack[] ret = AssemblerRecipes.recipes.get(comp);
					return ret == null ? null : Arrays.asList(ret);
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Registers regular recipes if there's no custom configuration
	 */
	@SuppressWarnings("serial")
	private static void registerDefaults() {
		
		boolean exp = GeneralConfig.enableExpensiveMode;
		
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.plate_iron, 2), new AStack[] {new OreDictStack(OreDictManager.IRON.ingot(), 3), },30);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.plate_gold, 2), new AStack[] {new OreDictStack(OreDictManager.GOLD.ingot(), 3), },30);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.plate_titanium, 2), new AStack[] {new OreDictStack(OreDictManager.TI.ingot(), 3), },30);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.plate_aluminium, 2), new AStack[] {new OreDictStack(OreDictManager.AL.ingot(), 3), },30);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.plate_steel, 2), new AStack[] {new OreDictStack(OreDictManager.STEEL.ingot(), 3), },30);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.plate_lead, 2), new AStack[] {new OreDictStack(OreDictManager.PB.ingot(), 3), },30);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.plate_copper, 2), new AStack[] {new OreDictStack(OreDictManager.CU.ingot(), 3), },30);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.plate_advanced_alloy, 2), new AStack[] {new OreDictStack(OreDictManager.ALLOY.ingot(), 3), },30);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.plate_schrabidium, 2), new AStack[] {new OreDictStack(OreDictManager.SA326.ingot(), 3), },30);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.plate_combine_steel, 2), new AStack[] {new OreDictStack(OreDictManager.CMB.ingot(), 3), },30);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.plate_saturnite, 2), new AStack[] {new OreDictStack(OreDictManager.BIGMT.ingot(), 3), },30);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.plate_mixed, 4), new AStack[] {new OreDictStack(OreDictManager.ALLOY.plate(), 2), new OreDictStack(OreDictManager.getReflector(), 1), new OreDictStack(OreDictManager.BIGMT.plate(), 1) },50);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.wire_aluminium, 6), new AStack[] {new OreDictStack(OreDictManager.AL.ingot(), 1), },20);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.wire_copper, 6), new AStack[] {new OreDictStack(OreDictManager.CU.ingot(), 1), },20);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.wire_tungsten, 6), new AStack[] {new OreDictStack(OreDictManager.W.ingot(), 1), },20);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.wire_red_copper, 6), new AStack[] {new OreDictStack(OreDictManager.MINGRADE.ingot(), 1), },20);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.wire_advanced_alloy, 6), new AStack[] {new OreDictStack(OreDictManager.ALLOY.ingot(), 1), },20);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.wire_gold, 6), new AStack[] {new ComparableStack(Items.gold_ingot, 1), },20);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.wire_schrabidium, 6), new AStack[] {new OreDictStack(OreDictManager.SA326.ingot(), 1), },20);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.wire_magnetized_tungsten, 6), new AStack[] {new OreDictStack(OreDictManager.MAGTUNG.ingot(), 1), },20);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.hazmat_cloth, 4), new AStack[] {new OreDictStack(OreDictManager.PB.dust(), 4), new ComparableStack(Items.string, 8), },50);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.asbestos_cloth, 4), new AStack[] {new OreDictStack(OreDictManager.ASBESTOS.ingot(), 2), new ComparableStack(Items.string, 6), new ComparableStack(Blocks.wool, 1), },50);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.filter_coal, 1), new AStack[] {new OreDictStack(OreDictManager.COAL.dust(), 4), new ComparableStack(Items.string, 2), new ComparableStack(Items.paper, 1), },50);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.centrifuge_element, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.plate528(), 4), new OreDictStack(OreDictManager.TI.plate528(), 4), new ComparableStack(ModItems.motor, 1), }, 100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.magnet_circular, 1), new AStack[] {new ComparableStack(ModBlocks.fusion_conductor, 5), new OreDictStack(OreDictManager.STEEL.ingot(), 4), new OreDictStack(OreDictManager.ALLOY.plate(), 6), },150);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.reactor_core, 1), new AStack[] {new OreDictStack(OreDictManager.PB.ingot(), 8), new OreDictStack(OreDictManager.BE.ingot(), 6), new OreDictStack(OreDictManager.STEEL.plate(), 16), new OreDictStack(OreDictManager.getReflector(), 8), new OreDictStack(OreDictManager.FIBER.ingot(), 2) },100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.rtg_unit, 1), new AStack[] {new ComparableStack(ModItems.thermo_element, 2), new ComparableStack(ModItems.board_copper, 1), new OreDictStack(OreDictManager.PB.ingot(), 2), new OreDictStack(OreDictManager.STEEL.plate(), 2), new ComparableStack(ModItems.circuit_copper, 1), },100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.levitation_unit, 1), new AStack[] {new ComparableStack(ModItems.coil_copper, 4), new ComparableStack(ModItems.coil_tungsten, 2), new OreDictStack(OreDictManager.TI.plate(), 6), new ComparableStack(ModItems.nugget_schrabidium, 2), },100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.drill_titanium, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.ingot(), 2), new OreDictStack(OreDictManager.DURA.ingot(), 2), new ComparableStack(ModItems.bolt_dura_steel, 2), new OreDictStack(OreDictManager.TI.plate(), 6), },100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.entanglement_kit, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.plate(), 8), new OreDictStack(OreDictManager.DURA.ingot(), 4), new OreDictStack(OreDictManager.CU.plate(), 24), new ComparableStack(ModBlocks.hadron_coil_gold, 4), new OreDictStack(Fluids.XENON.getDict(1_000))},200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.dysfunctional_reactor, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.plate(), 15), new OreDictStack(OreDictManager.PB.ingot(), 5), new ComparableStack(ModItems.rod_quad_empty, 10), new OreDictStack("dyeBrown", 3), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.missile_assembly, 1), new AStack[] {new ComparableStack(ModItems.hull_small_steel, 1), new ComparableStack(ModItems.hull_small_aluminium, 4), new OreDictStack(OreDictManager.STEEL.ingot(), 2), new OreDictStack(OreDictManager.TI.plate(), 6), new ComparableStack(ModItems.wire_aluminium, 6), new ComparableStack(ModItems.canister_full, 3, Fluids.KEROSENE.getID()), new ComparableStack(ModItems.circuit_targeting_tier1, 1), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.missile_carrier, 1), new AStack[] {new ComparableStack(ModItems.fluid_barrel_full, 16, Fluids.KEROSENE.getID()), new ComparableStack(ModItems.thruster_medium, 4), new ComparableStack(ModItems.thruster_large, 1), new ComparableStack(ModItems.hull_big_titanium, 6), new ComparableStack(ModItems.hull_big_steel, 2), new ComparableStack(ModItems.hull_small_aluminium, 12), new OreDictStack(OreDictManager.TI.plate(), 24), new ComparableStack(ModItems.plate_polymer, 128), new ComparableStack(ModBlocks.det_cord, 8), new ComparableStack(ModItems.circuit_targeting_tier3, 12), new ComparableStack(ModItems.circuit_targeting_tier4, 3), },4800);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.warhead_generic_small, 1), new AStack[] {new OreDictStack(OreDictManager.TI.plate(), 5), new OreDictStack(OreDictManager.STEEL.plate(), 3), new ComparableStack(Blocks.tnt, 2), },100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.warhead_generic_medium, 1), new AStack[] {new OreDictStack(OreDictManager.TI.plate(), 8), new OreDictStack(OreDictManager.STEEL.plate(), 5), new OreDictStack(OreDictManager.ANY_HIGHEXPLOSIVE.ingot(), 4), },150);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.warhead_generic_large, 1), new AStack[] {new OreDictStack(OreDictManager.TI.plate(), 15), new OreDictStack(OreDictManager.STEEL.plate(), 8), new OreDictStack(OreDictManager.ANY_HIGHEXPLOSIVE.ingot(), 8), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.warhead_incendiary_small, 1), new AStack[] {new ComparableStack(ModItems.warhead_generic_small, 1), new OreDictStack(OreDictManager.P_RED.dust(), 4), },100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.warhead_incendiary_medium, 1), new AStack[] {new ComparableStack(ModItems.warhead_generic_medium, 1), new OreDictStack(OreDictManager.P_RED.dust(), 8), },150);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.warhead_incendiary_large, 1), new AStack[] {new ComparableStack(ModItems.warhead_generic_large, 1), new OreDictStack(OreDictManager.P_RED.dust(), 16), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.warhead_cluster_small, 1), new AStack[] {new ComparableStack(ModItems.warhead_generic_small, 1), new ComparableStack(ModItems.pellet_cluster, 4), },100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.warhead_cluster_medium, 1), new AStack[] {new ComparableStack(ModItems.warhead_generic_medium, 1), new ComparableStack(ModItems.pellet_cluster, 8), },150);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.warhead_cluster_large, 1), new AStack[] {new ComparableStack(ModItems.warhead_generic_large, 1), new ComparableStack(ModItems.pellet_cluster, 16), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.warhead_buster_small, 1), new AStack[] {new ComparableStack(ModItems.warhead_generic_small, 1), new ComparableStack(ModBlocks.det_cord, 8), },100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.warhead_buster_medium, 1), new AStack[] {new ComparableStack(ModItems.warhead_generic_medium, 1), new ComparableStack(ModBlocks.det_cord, 4), new ComparableStack(ModBlocks.det_charge, 4), },150);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.warhead_buster_large, 1), new AStack[] {new ComparableStack(ModItems.warhead_generic_large, 1), new ComparableStack(ModBlocks.det_charge, 8), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.warhead_nuclear, 1), new AStack[] {new ComparableStack(ModItems.boy_shielding, 1), new ComparableStack(ModItems.boy_target, 1), new ComparableStack(ModItems.boy_bullet, 1), new ComparableStack(ModItems.boy_propellant, 1), new ComparableStack(ModItems.wire_red_copper, 6), new OreDictStack(OreDictManager.TI.plate(), 20), new OreDictStack(OreDictManager.STEEL.plate(), 12), },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.warhead_mirv, 1), new AStack[] {new OreDictStack(OreDictManager.TI.plate(), 20), new OreDictStack(OreDictManager.STEEL.plate(), 12), new OreDictStack(OreDictManager.PU239.ingot(), 1), new ComparableStack(ModItems.ball_tatb, 8), new OreDictStack(OreDictManager.BE.ingot(), 4), new OreDictStack(OreDictManager.LI.ingot(), 4), new ComparableStack(ModItems.cell_deuterium, 6), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.warhead_volcano, 1), new AStack[] {new OreDictStack(OreDictManager.TI.plate(), 24), new OreDictStack(OreDictManager.STEEL.plate(), 16), new ComparableStack(ModBlocks.det_nuke, 3), new OreDictStack(OreDictManager.U238.block(), 24), new ComparableStack(ModItems.circuit_tantalium, 5) }, 600);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.warhead_thermo_endo, 1), new AStack[] {new ComparableStack(ModBlocks.therm_endo, 2), new OreDictStack(OreDictManager.TI.plate(), 12), new OreDictStack(OreDictManager.STEEL.plate(), 6), },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.warhead_thermo_exo, 1), new AStack[] {new ComparableStack(ModBlocks.therm_exo, 2), new OreDictStack(OreDictManager.TI.plate(), 12), new OreDictStack(OreDictManager.STEEL.plate(), 6), },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.fuel_tank_small, 1), new AStack[] {new ComparableStack(ModItems.canister_full, 6, Fluids.ETHANOL.getID()), new OreDictStack(OreDictManager.TI.plate(), 6), new OreDictStack(OreDictManager.STEEL.plate(), 2), },100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.fuel_tank_medium, 1), new AStack[] {new ComparableStack(ModItems.canister_full, 8, Fluids.KEROSENE.getID()), new OreDictStack(OreDictManager.TI.plate(), 12), new OreDictStack(OreDictManager.STEEL.plate(), 4), },150);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.fuel_tank_large, 1), new AStack[] {new ComparableStack(ModItems.canister_full, 12, Fluids.KEROSENE.getID()), new OreDictStack(OreDictManager.TI.plate(), 24), new OreDictStack(OreDictManager.STEEL.plate(), 8), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.thruster_small, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.plate(), 4), new OreDictStack(OreDictManager.W.ingot(), 4), new ComparableStack(ModItems.wire_aluminium, 4), },100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.thruster_medium, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.plate(), 8), new OreDictStack(OreDictManager.W.ingot(), 8), new ComparableStack(ModItems.motor, 1), new ComparableStack(ModItems.wire_copper, 16), },150);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.thruster_large, 1), new AStack[] {new OreDictStack(OreDictManager.DURA.ingot(), 16), new OreDictStack(OreDictManager.W.ingot(), 16), new ComparableStack(ModItems.motor, 2), new ComparableStack(ModItems.wire_gold, 32), new ComparableStack(ModItems.circuit_red_copper, 1), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.thruster_nuclear, 1), new AStack[] {new OreDictStack(OreDictManager.DURA.ingot(), 32), new OreDictStack(OreDictManager.B.ingot(), 8), new OreDictStack(OreDictManager.PB.plate(), 16), new ComparableStack(ModItems.pipes_steel), new ComparableStack(ModItems.circuit_gold, 1) },600);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.sat_base, 1), new AStack[] {new ComparableStack(ModItems.thruster_large, 1), new OreDictStack(OreDictManager.STEEL.plate(), 6), new ComparableStack(ModItems.plate_desh, 4), new ComparableStack(ModItems.hull_big_titanium, 3), new ComparableStack(ModItems.fluid_barrel_full, 1, Fluids.KEROSENE.getID()), new ComparableStack(ModItems.photo_panel, 24), new ComparableStack(ModItems.board_copper, 12), new ComparableStack(ModItems.circuit_gold, 6), new ComparableStack(ModItems.battery_lithium_cell_6, 1), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.sat_head_mapper, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.ingot(), 4), new OreDictStack(OreDictManager.STEEL.plate(), 6), new ComparableStack(ModItems.hull_small_steel, 3), new ComparableStack(ModItems.plate_desh, 2), new ComparableStack(ModItems.circuit_gold, 2), new OreDictStack(OreDictManager.RUBBER.ingot(), 12), new OreDictStack(OreDictManager.REDSTONE.dust(), 6), new ComparableStack(Items.diamond, 1), new ComparableStack(Blocks.glass_pane, 6), },400);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.sat_head_scanner, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.ingot(), 6), new OreDictStack(OreDictManager.TI.plate(), 32), new ComparableStack(ModItems.plate_desh, 6), new ComparableStack(ModItems.magnetron, 6), new ComparableStack(ModItems.coil_advanced_torus, 2), new ComparableStack(ModItems.circuit_gold, 6), new OreDictStack(OreDictManager.RUBBER.ingot(), 6), new ComparableStack(Items.diamond, 1), },400);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.sat_head_radar, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.ingot(), 4), new OreDictStack(OreDictManager.TI.plate(), 32), new ComparableStack(ModItems.magnetron, 12), new OreDictStack(OreDictManager.RUBBER.ingot(), 16), new ComparableStack(ModItems.wire_red_copper, 16), new ComparableStack(ModItems.coil_gold, 3), new ComparableStack(ModItems.circuit_gold, 5), new ComparableStack(Items.diamond, 1), },400);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.sat_head_laser, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.ingot(), 12), new OreDictStack(OreDictManager.W.ingot(), 16), new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 6), new OreDictStack(OreDictManager.RUBBER.ingot(), 16), new ComparableStack(ModItems.board_copper, 24), new ComparableStack(ModItems.circuit_targeting_tier5, 2), new OreDictStack(OreDictManager.REDSTONE.dust(), 16), new ComparableStack(Items.diamond, 5), new ComparableStack(Blocks.glass_pane, 16), },450);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.sat_head_resonator, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.ingot(), 32), new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 48), new OreDictStack(OreDictManager.RUBBER.ingot(), 8), new ComparableStack(ModItems.crystal_xen, 1), new OreDictStack(OreDictManager.STAR.ingot(), 7), new ComparableStack(ModItems.circuit_targeting_tier5, 6), new ComparableStack(ModItems.circuit_targeting_tier6, 2), },1000);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.sat_foeq, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.plate(), 8), new OreDictStack(OreDictManager.TI.plate(), 12), new ComparableStack(ModItems.plate_desh, 8), new ComparableStack(ModItems.hull_big_titanium, 3), new ComparableStack(ModItems.fluid_barrel_full, 1, Fluids.HYDROGEN.getID()), new ComparableStack(ModItems.photo_panel, 16), new ComparableStack(ModItems.thruster_nuclear, 1), new ComparableStack(ModItems.ingot_uranium_fuel, 6), new ComparableStack(ModItems.circuit_targeting_tier5, 6), new ComparableStack(ModItems.magnetron, 3), new ComparableStack(ModItems.battery_lithium_cell_6, 1), },1200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.sat_miner, 1), new AStack[] {new OreDictStack(OreDictManager.BIGMT.plate(), 24), new ComparableStack(ModItems.plate_desh, 8), new ComparableStack(ModItems.motor, 2), new ComparableStack(ModItems.drill_titanium, 2), new ComparableStack(ModItems.circuit_targeting_tier4, 2), new ComparableStack(ModItems.fluid_barrel_full, 1, Fluids.KEROSENE.getID()), new ComparableStack(ModItems.thruster_small, 1), new ComparableStack(ModItems.photo_panel, 12), new ComparableStack(ModItems.centrifuge_element, 4), new ComparableStack(ModItems.magnetron, 3), new OreDictStack(OreDictManager.RUBBER.ingot(), 12), new ComparableStack(ModItems.battery_lithium_cell_6, 1), },600);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.sat_lunar_miner, 1), new AStack[] {new ComparableStack(ModItems.ingot_meteorite, 4), new ComparableStack(ModItems.plate_desh, 4), new ComparableStack(ModItems.motor_desh, 2), new ComparableStack(ModItems.drill_titanium, 2), new ComparableStack(ModItems.circuit_targeting_tier4, 2), new ComparableStack(ModItems.fluid_barrel_full, 1, Fluids.KEROSENE.getID()), new ComparableStack(ModItems.thruster_small, 1), new ComparableStack(ModItems.photo_panel, 12), new ComparableStack(ModItems.magnetron, 3), new OreDictStack(OreDictManager.RUBBER.ingot(), 12), new ComparableStack(ModItems.battery_lithium_cell_6, 1), },600);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.chopper_head, 1), new AStack[] {new ComparableStack(ModBlocks.reinforced_glass, 2), new ComparableStack(ModBlocks.fwatz_computer, 1), new OreDictStack(OreDictManager.CMB.ingot(), 22), new ComparableStack(ModItems.wire_magnetized_tungsten, 4), },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.chopper_gun, 1), new AStack[] {new OreDictStack(OreDictManager.CMB.plate(), 4), new OreDictStack(OreDictManager.CMB.ingot(), 2), new ComparableStack(ModItems.wire_tungsten, 6), new ComparableStack(ModItems.coil_magnetized_tungsten, 1), new ComparableStack(ModItems.motor, 1), },150);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.chopper_torso, 1), new AStack[] {new OreDictStack(OreDictManager.CMB.ingot(), 26), new ComparableStack(ModBlocks.fwatz_computer, 1), new ComparableStack(ModItems.wire_magnetized_tungsten, 4), new ComparableStack(ModItems.motor, 2), new ComparableStack(ModItems.chopper_blades, 2), },350);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.chopper_tail, 1), new AStack[] {new OreDictStack(OreDictManager.CMB.plate(), 8), new OreDictStack(OreDictManager.CMB.ingot(), 5), new ComparableStack(ModItems.wire_magnetized_tungsten, 4), new ComparableStack(ModItems.motor, 1), new ComparableStack(ModItems.chopper_blades, 2), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.chopper_wing, 1), new AStack[] {new OreDictStack(OreDictManager.CMB.plate(), 6), new OreDictStack(OreDictManager.CMB.ingot(), 3), new ComparableStack(ModItems.wire_magnetized_tungsten, 2), },150);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.chopper_blades, 1), new AStack[] {new OreDictStack(OreDictManager.CMB.plate(), 8), new OreDictStack(OreDictManager.STEEL.plate(), 2), new OreDictStack(OreDictManager.CMB.ingot(), 2), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.circuit_aluminium, 1), new AStack[] {new ComparableStack(ModItems.circuit_raw, 1), },50);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.circuit_copper, 1), new AStack[] {new ComparableStack(ModItems.circuit_aluminium, 1), new ComparableStack(ModItems.wire_copper, 4), new OreDictStack(OreDictManager.NETHERQUARTZ.dust(), 1), new OreDictStack(OreDictManager.CU.plate(), 1), },100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.circuit_red_copper, 1), new AStack[] {new ComparableStack(ModItems.circuit_copper, 1), new ComparableStack(ModItems.wire_red_copper, 4), new OreDictStack(OreDictManager.GOLD.dust(), 1), new ComparableStack(ModItems.plate_polymer, 1), },150);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.crt_display, 8), new AStack[] {new OreDictStack(OreDictManager.AL.dust(), 2), new ComparableStack(Blocks.glass_pane, 2), new ComparableStack(ModItems.wire_tungsten, 4), new ComparableStack(ModItems.hull_small_steel, 1) }, 100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.tritium_deuterium_cake, 1), new AStack[] {new ComparableStack(ModItems.cell_deuterium, 6), new ComparableStack(ModItems.cell_tritium, 2), new OreDictStack(OreDictManager.LI.ingot(), 4), },150);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.pellet_cluster, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.plate(), 4), new ComparableStack(Blocks.tnt, 1), }, 50);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.pellet_buckshot, 1), new AStack[] {new OreDictStack(OreDictManager.PB.nugget(), 6), }, 50);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.australium_iii, 1), new AStack[] {new ComparableStack(ModItems.nugget_australium, 6), new OreDictStack(OreDictManager.STEEL.ingot(), 1), new OreDictStack(OreDictManager.STEEL.plate(), 6), new OreDictStack(OreDictManager.CU.plate(), 2), new ComparableStack(ModItems.wire_copper, 6), },150);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.magnetron, 1), new AStack[] {new OreDictStack(OreDictManager.ALLOY.ingot(), 1), new OreDictStack(OreDictManager.ALLOY.plate(), 2), new ComparableStack(ModItems.wire_tungsten, 1), new ComparableStack(ModItems.coil_tungsten, 1), },100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.pellet_schrabidium, 1), new AStack[] {new OreDictStack(OreDictManager.SA326.ingot(), 5), new OreDictStack(OreDictManager.IRON.plate(), 2), }, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.pellet_hes, 1), new AStack[] {new ComparableStack(ModItems.ingot_hes, 5), new OreDictStack(OreDictManager.IRON.plate(), 2), }, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.pellet_mes, 1), new AStack[] {new ComparableStack(ModItems.ingot_schrabidium_fuel, 5), new OreDictStack(OreDictManager.IRON.plate(), 2), }, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.pellet_les, 1), new AStack[] {new ComparableStack(ModItems.ingot_les, 5), new OreDictStack(OreDictManager.IRON.plate(), 2), }, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.pellet_beryllium, 1), new AStack[] {new OreDictStack(OreDictManager.BE.ingot(), 5), new OreDictStack(OreDictManager.IRON.plate(), 2), }, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.pellet_neptunium, 1), new AStack[] {new OreDictStack(OreDictManager.NP237.ingot(), 5), new OreDictStack(OreDictManager.IRON.plate(), 2), }, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.pellet_lead, 1), new AStack[] {new OreDictStack(OreDictManager.PB.ingot(), 5), new OreDictStack(OreDictManager.IRON.plate(), 2), }, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.pellet_advanced, 1), new AStack[] {new OreDictStack(OreDictManager.DESH.ingot(), 5), new OreDictStack(OreDictManager.IRON.plate(), 2), }, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.upgrade_template, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.plate528(), 1), new OreDictStack(OreDictManager.IRON.plate528(), 4), new OreDictStack(OreDictManager.CU.plate528(), 2), new ComparableStack(ModItems.wire_copper, 6), },100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.upgrade_speed_1, 1), new AStack[] {new ComparableStack(ModItems.upgrade_template, 1), new OreDictStack(OreDictManager.MINGRADE.dust(), 4), new OreDictStack(OreDictManager.REDSTONE.dust(), 6), new ComparableStack(ModItems.wire_red_copper, 4), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.upgrade_speed_2, 1), new AStack[] {new ComparableStack(ModItems.upgrade_speed_1, 1), new OreDictStack(OreDictManager.MINGRADE.dust(), 2), new OreDictStack(OreDictManager.REDSTONE.dust(), 4), new ComparableStack(ModItems.circuit_red_copper, 4), new ComparableStack(ModItems.wire_red_copper, 4), new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 2), },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.upgrade_speed_3, 1), new AStack[] {new ComparableStack(ModItems.upgrade_speed_2, 1), new OreDictStack(OreDictManager.MINGRADE.dust(), 2), new OreDictStack(OreDictManager.REDSTONE.dust(), 6), new OreDictStack(OreDictManager.DESH.ingot(), 4), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.upgrade_effect_1, 1), new AStack[] {new ComparableStack(ModItems.upgrade_template, 1), new OreDictStack(OreDictManager.DURA.dust(), 4), new OreDictStack(OreDictManager.STEEL.dust(), 6), new ComparableStack(ModItems.wire_red_copper, 4), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.upgrade_effect_2, 1), new AStack[] {new ComparableStack(ModItems.upgrade_effect_1, 1), new OreDictStack(OreDictManager.DURA.dust(), 2), new OreDictStack(OreDictManager.STEEL.dust(), 4), new ComparableStack(ModItems.circuit_red_copper, 4), new ComparableStack(ModItems.wire_red_copper, 4), new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 2), },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.upgrade_effect_3, 1), new AStack[] {new ComparableStack(ModItems.upgrade_effect_2, 1), new OreDictStack(OreDictManager.DURA.dust(), 2), new OreDictStack(OreDictManager.STEEL.dust(), 6), new OreDictStack(OreDictManager.DESH.ingot(), 4), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.upgrade_power_1, 1), new AStack[] {new ComparableStack(ModItems.upgrade_template, 1), new OreDictStack(OreDictManager.LAPIS.dust(), 4), new ComparableStack(Items.glowstone_dust, 6), new ComparableStack(ModItems.wire_red_copper, 4), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.upgrade_power_2, 1), new AStack[] {new ComparableStack(ModItems.upgrade_power_1, 1), new OreDictStack(OreDictManager.LAPIS.dust(), 2), new ComparableStack(Items.glowstone_dust, 4), new ComparableStack(ModItems.circuit_red_copper, 4), new ComparableStack(ModItems.wire_red_copper, 4), new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 2), },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.upgrade_power_3, 1), new AStack[] {new ComparableStack(ModItems.upgrade_power_2, 1), new OreDictStack(OreDictManager.LAPIS.dust(), 2), new ComparableStack(Items.glowstone_dust, 6), new OreDictStack(OreDictManager.DESH.ingot(), 4), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.upgrade_fortune_1, 1), new AStack[] {new ComparableStack(ModItems.upgrade_template, 1), new OreDictStack(OreDictManager.DIAMOND.dust(), 4), new OreDictStack(OreDictManager.IRON.dust(), 6), new ComparableStack(ModItems.wire_red_copper, 4), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.upgrade_fortune_2, 1), new AStack[] {new ComparableStack(ModItems.upgrade_fortune_1, 1), new OreDictStack(OreDictManager.DIAMOND.dust(), 2), new OreDictStack(OreDictManager.IRON.dust(), 4), new ComparableStack(ModItems.circuit_red_copper, 4), new ComparableStack(ModItems.wire_red_copper, 4), new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 2), },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.upgrade_fortune_3, 1), new AStack[] {new ComparableStack(ModItems.upgrade_fortune_2, 1), new OreDictStack(OreDictManager.DIAMOND.dust(), 2), new OreDictStack(OreDictManager.IRON.dust(), 6), new OreDictStack(OreDictManager.DESH.ingot(), 4), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.upgrade_afterburn_1, 1), new AStack[] {new ComparableStack(ModItems.upgrade_template, 1), new OreDictStack(OreDictManager.POLYMER.dust(), 4), new OreDictStack(OreDictManager.W.dust(), 6), new ComparableStack(ModItems.wire_red_copper, 4), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.upgrade_afterburn_2, 1), new AStack[] {new ComparableStack(ModItems.upgrade_afterburn_1, 1), new OreDictStack(OreDictManager.POLYMER.dust(), 2), new OreDictStack(OreDictManager.W.dust(), 4), new ComparableStack(ModItems.circuit_red_copper, 4), new ComparableStack(ModItems.wire_red_copper, 4), new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 2), },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.upgrade_afterburn_3, 1), new AStack[] {new ComparableStack(ModItems.upgrade_afterburn_2, 1), new OreDictStack(OreDictManager.POLYMER.dust(), 2), new OreDictStack(OreDictManager.W.dust(), 6), new OreDictStack(OreDictManager.DESH.ingot(), 4), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.upgrade_radius, 1), new AStack[] {new ComparableStack(ModItems.upgrade_template, 1), new ComparableStack(Items.glowstone_dust, 6), new OreDictStack(OreDictManager.DIAMOND.dust(), 4), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.upgrade_health, 1), new AStack[] {new ComparableStack(ModItems.upgrade_template, 1), new ComparableStack(Items.glowstone_dust, 6), new OreDictStack(OreDictManager.TI.dust(), 4), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.upgrade_overdrive_1, 1), new AStack[] {new ComparableStack(ModItems.upgrade_speed_3, 1), new ComparableStack(ModItems.upgrade_effect_3, 1), new OreDictStack(OreDictManager.DESH.ingot(), 8), new ComparableStack(ModItems.powder_power, 16), new ComparableStack(ModItems.crystal_lithium, 4), new ComparableStack(ModItems.circuit_schrabidium, 1), }, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.upgrade_overdrive_2, 1), new AStack[] {new ComparableStack(ModItems.upgrade_overdrive_1, 1), new ComparableStack(ModItems.upgrade_afterburn_1, 1), new ComparableStack(ModItems.upgrade_speed_3, 1), new ComparableStack(ModItems.upgrade_effect_3, 1), new ComparableStack(ModItems.crystal_lithium, 8), new ComparableStack(ModItems.circuit_tantalium, 16), }, 300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.upgrade_overdrive_3, 1), new AStack[] {new ComparableStack(ModItems.upgrade_overdrive_2, 1), new ComparableStack(ModItems.upgrade_afterburn_1, 1), new ComparableStack(ModItems.upgrade_speed_3, 1), new ComparableStack(ModItems.upgrade_effect_3, 1), new ComparableStack(ModItems.crystal_lithium, 16), new OreDictStack(OreDictManager.KEY_CIRCUIT_BISMUTH), }, 500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.redcoil_capacitor, 1), new AStack[] {new OreDictStack(OreDictManager.GOLD.plate(), 3), new ComparableStack(ModItems.fuse, 1), new ComparableStack(ModItems.wire_advanced_alloy, 4), new ComparableStack(ModItems.coil_advanced_alloy, 6), new ComparableStack(Blocks.redstone_block, 2), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.titanium_filter, 1), new AStack[] {new OreDictStack(OreDictManager.PB.plate(), 3), new ComparableStack(ModItems.fuse, 1), new ComparableStack(ModItems.wire_tungsten, 4), new OreDictStack(OreDictManager.TI.plate(), 6), new OreDictStack(OreDictManager.U238.ingot(), 2), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.part_lithium, 1), new AStack[] {new ComparableStack(ModItems.plate_polymer, 1), new OreDictStack(OreDictManager.LI.dust(), 1), },50);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.part_beryllium, 1), new AStack[] {new ComparableStack(ModItems.plate_polymer, 1), new OreDictStack(OreDictManager.BE.dust(), 1), },50);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.part_carbon, 1), new AStack[] {new ComparableStack(ModItems.plate_polymer, 1), new OreDictStack(OreDictManager.COAL.dust(), 1), },50);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.part_copper, 1), new AStack[] {new ComparableStack(ModItems.plate_polymer, 1), new OreDictStack(OreDictManager.CU.dust(), 1), },50);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.part_plutonium, 1), new AStack[] {new ComparableStack(ModItems.plate_polymer, 1), new ComparableStack(ModItems.powder_plutonium, 1), },50);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.thermo_element, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.plate(), 1), new ComparableStack(ModItems.wire_red_copper, 2), new OreDictStack(OreDictManager.NETHERQUARTZ.dust(), 2), }, 60);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.plate_dalekanium, 1), new AStack[] {new ComparableStack(ModBlocks.block_meteor, 1), },50);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.block_meteor, 1), new AStack[] {new ComparableStack(ModItems.fragment_meteorite, 100), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.cmb_brick, 8), new AStack[] {new OreDictStack(OreDictManager.CMB.ingot(), 1), new OreDictStack(OreDictManager.CMB.plate(), 8), },100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.cmb_brick_reinforced, 8), new AStack[] {new ComparableStack(ModBlocks.block_magnetized_tungsten, 4), new ComparableStack(ModBlocks.brick_concrete, 4), new ComparableStack(ModBlocks.cmb_brick, 1), new OreDictStack(OreDictManager.STEEL.plate(), 4), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.seal_frame, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.ingot(), 3), new ComparableStack(ModItems.wire_aluminium, 4), new OreDictStack(OreDictManager.REDSTONE.dust(), 2), new ComparableStack(ModBlocks.steel_roof, 5), },50);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.seal_controller, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.ingot(), 3), new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 4), new OreDictStack(OreDictManager.MINGRADE.ingot(), 1), new OreDictStack(OreDictManager.REDSTONE.dust(), 4), new ComparableStack(ModBlocks.steel_roof, 5), },100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_centrifuge, 1), new AStack[] {new ComparableStack(ModItems.centrifuge_element, 1), new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 2), new OreDictStack(OreDictManager.STEEL.plate528(), 8), new OreDictStack(OreDictManager.CU.plate(), 8), new ComparableStack(ModItems.circuit_copper, 1), }, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_gascent, 1), new AStack[] {new ComparableStack(ModItems.centrifuge_element, 4), new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 4), new OreDictStack(OreDictManager.DESH.ingot(), 2), new OreDictStack(OreDictManager.STEEL.plate528(), 8), new ComparableStack(ModItems.coil_tungsten, 4), new ComparableStack(ModItems.circuit_red_copper, 1) }, 300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_rtg_furnace_off, 1), new AStack[] {new ComparableStack(Blocks.furnace, 1), new ComparableStack(ModItems.rtg_unit, 3), new OreDictStack(OreDictManager.PB.plate528(), 6), new OreDictStack(OreDictManager.getReflector(), 4), new OreDictStack(OreDictManager.CU.plate(), 2), },150);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_diesel, 1), new AStack[] {new ComparableStack(ModItems.hull_small_steel, 1), new ComparableStack(ModItems.piston_selenium, 1), new OreDictStack(OreDictManager.STEEL.plateCast(), 1), new ComparableStack(ModItems.coil_copper, 4), }, 60);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_selenium, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.ingot(), 4), new OreDictStack(OreDictManager.TI.plate(), 6), new OreDictStack(OreDictManager.CU.plate(), 8), new ComparableStack(ModItems.hull_big_steel, 1), new ComparableStack(ModItems.hull_small_steel, 9), new ComparableStack(ModItems.pedestal_steel, 1), new ComparableStack(ModItems.coil_copper, 4), },250);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_rtg_grey, 1), new AStack[] {new ComparableStack(ModItems.rtg_unit, 3), new OreDictStack(OreDictManager.STEEL.plate528(), 4), new ComparableStack(ModItems.wire_red_copper, 4), new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 3), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_battery, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.plateWelded(), 1), new OreDictStack(OreDictManager.S.dust(), 12), new OreDictStack(OreDictManager.PB.dust(), 12), new OreDictStack(OreDictManager.MINGRADE.ingot(), 2), new ComparableStack(ModItems.wire_red_copper, 4), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_lithium_battery, 1), new AStack[] {new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 4), new OreDictStack(OreDictManager.CO.dust(), 12), new OreDictStack(OreDictManager.LI.dust(), 12), new OreDictStack(OreDictManager.ALLOY.ingot(), 2), new ComparableStack(ModItems.wire_red_copper, 4), },400);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_schrabidium_battery, 1), new AStack[] {new OreDictStack(OreDictManager.DESH.ingot(), 4), new OreDictStack(OreDictManager.NP237.dust(), 12), new OreDictStack(OreDictManager.SA326.dust(), 12), new OreDictStack(OreDictManager.SA326.ingot(), 2), new ComparableStack(ModItems.wire_schrabidium, 4), },800);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_dineutronium_battery, 1), new AStack[] {new OreDictStack(OreDictManager.DNT.ingot(), 24), new ComparableStack(ModItems.powder_spark_mix, 12), new ComparableStack(ModItems.battery_spark_cell_1000, 1), new OreDictStack(OreDictManager.CMB.ingot(), 32), new ComparableStack(ModItems.coil_magnetized_tungsten, 8), },1600);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_shredder, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.plate528(), 8), new ComparableStack(ModItems.motor, 2), new ComparableStack(ModBlocks.steel_beam, 2), new ComparableStack(Blocks.iron_bars, 2) },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_well, 1), new AStack[] {new ComparableStack(ModBlocks.steel_scaffold, 20), new ComparableStack(ModBlocks.steel_beam, 8), new ComparableStack(ModItems.tank_steel, 2), new ComparableStack(ModItems.motor, 1), new ComparableStack(ModItems.pipes_steel, 3), new ComparableStack(ModItems.drill_titanium, 1), new ComparableStack(ModItems.wire_red_copper, 6), },250);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_pumpjack, 1), new AStack[] {new ComparableStack(ModBlocks.steel_scaffold, 8), new OreDictStack(OreDictManager.STEEL.plateWelded(), 8), new ComparableStack(ModItems.pipes_steel, 4), new ComparableStack(ModItems.tank_steel, 4), new OreDictStack(OreDictManager.STEEL.plate(), 32), new ComparableStack(ModItems.drill_titanium, 1), new ComparableStack(ModItems.motor, 2) }, 400);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_flare, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.ingot(), 12), new OreDictStack(OreDictManager.IRON.ingot(), 12), new OreDictStack(OreDictManager.CU.plate528(), 4), new ComparableStack(ModItems.tank_steel, 1), new ComparableStack(ModBlocks.deco_pipe_quad, 8), new ComparableStack(ModItems.hull_small_steel, 4), new ComparableStack(ModItems.thermo_element, 3), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_coker, 1), new AStack[] {!exp ? new OreDictStack(OreDictManager.STEEL.plateWelded(), 3) : new OreDictStack(OreDictManager.STEEL.heavyComp(), 2), new OreDictStack(OreDictManager.IRON.ingot(), 16), new OreDictStack(OreDictManager.CU.plate528(), 8), new OreDictStack(OreDictManager.RUBBER.ingot(), 4), new ComparableStack(ModItems.tank_steel, 2), new ComparableStack(ModBlocks.steel_grate, 4) },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_refinery, 1), new AStack[] {!exp ? new OreDictStack(OreDictManager.STEEL.plateWelded(), 3) : new OreDictStack(OreDictManager.STEEL.heavyComp(), 1), new OreDictStack(OreDictManager.CU.plate528(), 16), new ComparableStack(ModItems.hull_big_steel, 6), new ComparableStack(ModItems.pipes_steel, 2), new ComparableStack(ModItems.plate_polymer, 8), new ComparableStack(ModItems.circuit_red_copper, 1) },350);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_epress, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.plate(), 8), new ComparableStack(ModItems.plate_polymer, 4), new ComparableStack(ModItems.part_generic, 2, EnumPartType.PISTON_HYDRAULIC.ordinal()), new ComparableStack(ModItems.circuit_copper, 1) }, 100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_chemplant, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.ingot(), 8), new OreDictStack(OreDictManager.CU.plate528(), 6), new ComparableStack(ModItems.tank_steel, 4), new ComparableStack(ModItems.hull_big_steel, 1), new ComparableStack(ModItems.coil_tungsten, 3), new ComparableStack(ModItems.circuit_copper, 2), new ComparableStack(ModItems.circuit_red_copper, 1), new ComparableStack(ModItems.plate_polymer, 8), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_crystallizer, 1), new AStack[] {new ComparableStack(ModItems.hull_big_steel, 4), new ComparableStack(ModItems.pipes_steel, 1), new OreDictStack(OreDictManager.DESH.ingot(), 4), new ComparableStack(ModItems.motor, 2), new ComparableStack(ModItems.blades_advanced_alloy, 2), new OreDictStack(OreDictManager.STEEL.plateWelded(), 4), new OreDictStack(OreDictManager.TI.plate(), 16), new ComparableStack(Blocks.glass, 4), new ComparableStack(ModItems.circuit_gold, 1), },400);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_fluidtank, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.ingot(), 2), new OreDictStack(OreDictManager.STEEL.plate528(), 6), new ComparableStack(ModItems.hull_big_steel, 4), new OreDictStack(OreDictManager.ANY_TAR.any(), 4), },150);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_bat9000, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.plate528(), 16), new OreDictStack(OreDictManager.ANY_RESISTANTALLOY.plateWelded(), 2), new ComparableStack(ModBlocks.steel_scaffold, 16), new OreDictStack(OreDictManager.ANY_TAR.any(), 16), },150);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_orbus, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.ingot(), 12), new OreDictStack(OreDictManager.ANY_RESISTANTALLOY.plateWelded(), 8), new OreDictStack(OreDictManager.BIGMT.plate(), 12), new ComparableStack(ModItems.coil_advanced_alloy, 12), new ComparableStack(ModItems.battery_sc_polonium, 1) }, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_mining_laser, 1), new AStack[] {new ComparableStack(ModItems.tank_steel, 3), !exp ? new OreDictStack(OreDictManager.STEEL.plate528(), 16) : new OreDictStack(OreDictManager.STEEL.heavyComp(), 3), new ComparableStack(ModItems.crystal_redstone, 3), new ComparableStack(Items.diamond, 3), new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 4), new ComparableStack(ModItems.motor, 3), !exp ? new OreDictStack(OreDictManager.DURA.ingot(), 4) : new OreDictStack(OreDictManager.DESH.heavyComp(), 1), new ComparableStack(ModItems.bolt_dura_steel, 6), new ComparableStack(ModBlocks.machine_battery, 3), },400);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_turbofan, 1), new AStack[] {!exp ? new ComparableStack(ModItems.hull_big_steel, 1) : new OreDictStack(OreDictManager.STEEL.heavyComp(), 1), new ComparableStack(ModItems.hull_big_titanium, 3), new ComparableStack(ModItems.hull_small_steel, 2), new ComparableStack(ModItems.turbine_tungsten, 1), new ComparableStack(ModItems.turbine_titanium, 7), new ComparableStack(ModItems.bolt_compound, 8), new OreDictStack(OreDictManager.MINGRADE.ingot(), 12), new ComparableStack(ModItems.wire_red_copper, 24), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_turbinegas, 1), new AStack[] {!exp ? new ComparableStack(ModItems.hull_big_steel, 4) : new OreDictStack(OreDictManager.STEEL.heavyComp(), 2), new ComparableStack(ModItems.hull_small_steel, 6), new ComparableStack(ModItems.generator_steel, 2), new ComparableStack(ModItems.bolt_compound, 4), new ComparableStack(ModBlocks.steel_scaffold, 8), new ComparableStack(ModBlocks.deco_pipe_quad, 4), new ComparableStack(ModItems.turbine_tungsten, 3), new ComparableStack(ModItems.motor, 2), new ComparableStack(ModItems.ingot_rubber, 4), new ComparableStack(ModItems.circuit_red_copper, 3)}, 600);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_teleporter, 1), new AStack[] {new OreDictStack(OreDictManager.TI.ingot(), 8), new OreDictStack(OreDictManager.ALLOY.plate528(), 12), new ComparableStack(ModItems.wire_gold, 32), new ComparableStack(ModItems.entanglement_kit, 1), new ComparableStack(ModBlocks.machine_battery, 1) },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_schrabidium_transmutator, 1), new AStack[] {new OreDictStack(OreDictManager.MAGTUNG.ingot(), 1), !exp ? new OreDictStack(OreDictManager.TI.ingot(), 24) : new OreDictStack(OreDictManager.TI.heavyComp(), 2), !exp ? new OreDictStack(OreDictManager.ALLOY.plate(), 18) : new OreDictStack(OreDictManager.ALLOY.heavyComp(), 1), new OreDictStack(OreDictManager.STEEL.plateWelded(), 12), new ComparableStack(ModItems.plate_desh, 6), new OreDictStack(OreDictManager.RUBBER.ingot(), 8), new ComparableStack(ModBlocks.machine_battery, 5), new ComparableStack(ModItems.circuit_gold, 5), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.fusion_conductor, 1), new AStack[] {new ComparableStack(ModItems.coil_advanced_alloy, 5), }, 100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.fusion_center, 1), new AStack[] {new OreDictStack(OreDictManager.ANY_HARDPLASTIC.ingot(), 4), new OreDictStack(OreDictManager.STEEL.plate528(), 6), new ComparableStack(ModItems.wire_advanced_alloy, 24), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.fusion_motor, 1), new AStack[] {new OreDictStack(OreDictManager.TI.ingot(), 4), new OreDictStack(OreDictManager.STEEL.ingot(), 2), new ComparableStack(ModItems.motor, 4), },250);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.fusion_heater, 4), new AStack[] {new OreDictStack(OreDictManager.W.plateWelded(), 2), new OreDictStack(OreDictManager.STEEL.plateWelded(), 2), new OreDictStack(OreDictManager.getReflector(), 2), new ComparableStack(ModItems.magnetron, 2) }, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.watz_element, 3), new AStack[] {new OreDictStack(OreDictManager.STEEL.plateCast(), 2), new OreDictStack(OreDictManager.ZR.ingot(), 2), new OreDictStack(OreDictManager.BIGMT.ingot(), 2), new OreDictStack(OreDictManager.ANY_HARDPLASTIC.ingot(), 4)},200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.watz_cooler, 3), new AStack[] {new OreDictStack(OreDictManager.STEEL.plateCast(), 2), new OreDictStack(OreDictManager.CU.plateCast(), 4), new OreDictStack(OreDictManager.RUBBER.ingot(), 2), }, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.watz_end, 3), new AStack[] {new OreDictStack(OreDictManager.ANY_RESISTANTALLOY.plateWelded()), new OreDictStack(OreDictManager.B.ingot(), 3), new OreDictStack(OreDictManager.STEEL.plateWelded(), 2), }, 100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.fwatz_hatch, 1), new AStack[] {new OreDictStack(OreDictManager.W.ingot(), 6), new OreDictStack(OreDictManager.CMB.plate(), 4), },250);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.fwatz_conductor, 1), new AStack[] {new OreDictStack(OreDictManager.CMB.plate(), 2), new ComparableStack(ModItems.coil_magnetized_tungsten, 5), },250);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.fwatz_computer, 1), new AStack[] {new ComparableStack(ModBlocks.block_meteor, 1), new ComparableStack(ModItems.wire_magnetized_tungsten, 16), new OreDictStack(OreDictManager.DIAMOND.dust(), 6), new OreDictStack(OreDictManager.MAGTUNG.dust(), 6), new OreDictStack(OreDictManager.DESH.dust(), 4), },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.fwatz_core, 1), new AStack[] {new ComparableStack(ModBlocks.block_meteor, 1), new ComparableStack(ModItems.wire_magnetized_tungsten, 24), new OreDictStack(OreDictManager.DIAMOND.dust(), 8), new OreDictStack(OreDictManager.MAGTUNG.dust(), 12), new OreDictStack(OreDictManager.DESH.dust(), 8), new ComparableStack(ModItems.upgrade_power_3, 1), new ComparableStack(ModItems.upgrade_speed_3, 1), new OreDictStack(OreDictManager.KEY_CIRCUIT_BISMUTH, 8)},450);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.nuke_gadget, 1), new AStack[] {new ComparableStack(ModItems.sphere_steel, 1), new ComparableStack(ModItems.fins_flat, 2), new ComparableStack(ModItems.pedestal_steel, 1), new ComparableStack(ModItems.circuit_targeting_tier3, 1), new ComparableStack(ModItems.wire_gold, 6), new OreDictStack("dyeGray", 6), },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.nuke_boy, 1), new AStack[] {new ComparableStack(ModItems.hull_small_steel, 2), new ComparableStack(ModItems.fins_small_steel, 1), new ComparableStack(ModItems.circuit_targeting_tier2, 1), new ComparableStack(ModItems.wire_aluminium, 6), new OreDictStack("dyeBlue", 4), },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.nuke_man, 1), new AStack[] {new ComparableStack(ModItems.sphere_steel, 1), new ComparableStack(ModItems.hull_big_steel, 2), new ComparableStack(ModItems.fins_big_steel, 1), new ComparableStack(ModItems.circuit_targeting_tier2, 2), new ComparableStack(ModItems.wire_copper, 6), new OreDictStack("dyeYellow", 6), },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.nuke_mike, 1), new AStack[] {new ComparableStack(ModItems.sphere_steel, 1), new ComparableStack(ModItems.hull_big_aluminium, 4), new OreDictStack(OreDictManager.AL.plate(), 3), new ComparableStack(ModItems.circuit_targeting_tier4, 3), new ComparableStack(ModItems.wire_gold, 18), new OreDictStack("dyeLightGray", 12), },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.nuke_tsar, 1), new AStack[] {new ComparableStack(ModItems.sphere_steel, 1), new ComparableStack(ModItems.hull_big_titanium, 6), new ComparableStack(ModItems.hull_small_steel, 2), new ComparableStack(ModItems.fins_tri_steel, 1), new ComparableStack(ModItems.circuit_targeting_tier4, 5), new ComparableStack(ModItems.wire_gold, 24), new ComparableStack(ModItems.wire_tungsten, 12), new OreDictStack("dyeBlack", 6), },600);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.nuke_prototype, 1), new AStack[] {new ComparableStack(ModItems.dysfunctional_reactor, 1), new ComparableStack(ModItems.hull_small_steel, 2), new ComparableStack(ModItems.ingot_euphemium, 3), new ComparableStack(ModItems.circuit_targeting_tier5, 1), new ComparableStack(ModItems.wire_gold, 16), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.nuke_fleija, 1), new AStack[] {new ComparableStack(ModItems.hull_small_aluminium, 1), new ComparableStack(ModItems.fins_quad_titanium, 1), new ComparableStack(ModItems.circuit_targeting_tier4, 2), new ComparableStack(ModItems.wire_gold, 8), new OreDictStack("dyeWhite", 4), },400);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.nuke_solinium, 1), new AStack[] {new ComparableStack(ModItems.hull_small_steel, 2), new ComparableStack(ModItems.fins_quad_titanium, 1), new ComparableStack(ModItems.circuit_targeting_tier4, 3), new ComparableStack(ModItems.wire_gold, 10), new ComparableStack(ModItems.pipes_steel, 4), new OreDictStack("dyeGray", 4), },400);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.nuke_n2, 1), new AStack[] {new ComparableStack(ModItems.hull_big_steel, 3), new ComparableStack(ModItems.hull_small_steel, 2), new ComparableStack(ModItems.wire_magnetized_tungsten, 12), new ComparableStack(ModItems.pipes_steel, 6), new ComparableStack(ModItems.circuit_targeting_tier4, 3), new OreDictStack("dyeBlack", 12), },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.nuke_fstbmb, 1), new AStack[] {new ComparableStack(ModItems.sphere_steel, 1), new ComparableStack(ModItems.hull_big_titanium, 6), new ComparableStack(ModItems.fins_big_steel, 1), new ComparableStack(ModItems.powder_magic, 8), new ComparableStack(ModItems.wire_gold, 12), new ComparableStack(ModItems.circuit_targeting_tier4, 4), new OreDictStack("dyeGray", 6), },600);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.nuke_custom, 1), new AStack[] {new ComparableStack(ModItems.hull_small_steel, 2), new ComparableStack(ModItems.fins_small_steel, 1), new ComparableStack(ModItems.circuit_gold, 1), new ComparableStack(ModItems.wire_gold, 12), new OreDictStack("dyeGray", 4), },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.float_bomb, 1), new AStack[] {new OreDictStack(OreDictManager.TI.plate(), 12), new ComparableStack(ModItems.levitation_unit, 1), new ComparableStack(ModItems.circuit_gold, 4), new ComparableStack(ModItems.wire_gold, 6), },250);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.therm_endo, 1), new AStack[] {new OreDictStack(OreDictManager.TI.plate(), 12), new ComparableStack(ModItems.powder_ice, 32), new ComparableStack(ModItems.circuit_gold, 1), new ComparableStack(ModItems.coil_gold, 4), },250);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.therm_exo, 1), new AStack[] {new OreDictStack(OreDictManager.TI.plate(), 12), new OreDictStack(OreDictManager.P_RED.dust(), 32), new ComparableStack(ModItems.circuit_gold, 1), new ComparableStack(ModItems.coil_gold, 4), },250);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.launch_pad, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.ingot(), 4), new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 2), new OreDictStack(OreDictManager.STEEL.plate(), 12), new ComparableStack(ModBlocks.machine_battery, 1), new ComparableStack(ModItems.circuit_gold, 2), },250);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.spawn_chopper, 1), new AStack[] {new ComparableStack(ModItems.chopper_blades, 5), new ComparableStack(ModItems.chopper_gun, 1), new ComparableStack(ModItems.chopper_head, 1), new ComparableStack(ModItems.chopper_tail, 1), new ComparableStack(ModItems.chopper_torso, 1), new ComparableStack(ModItems.chopper_wing, 2), },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.missile_generic, 1), new AStack[] {new ComparableStack(ModItems.warhead_generic_small, 1), new ComparableStack(ModItems.fuel_tank_small, 1), new ComparableStack(ModItems.thruster_small, 1), new OreDictStack(OreDictManager.TI.plate(), 6), new ComparableStack(ModItems.circuit_targeting_tier1, 1), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.missile_incendiary, 1), new AStack[] {new ComparableStack(ModItems.warhead_incendiary_small, 1), new ComparableStack(ModItems.fuel_tank_small, 1), new ComparableStack(ModItems.thruster_small, 1), new OreDictStack(OreDictManager.TI.plate(), 6), new ComparableStack(ModItems.circuit_targeting_tier1, 1), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.missile_cluster, 1), new AStack[] {new ComparableStack(ModItems.warhead_cluster_small, 1), new ComparableStack(ModItems.fuel_tank_small, 1), new ComparableStack(ModItems.thruster_small, 1), new OreDictStack(OreDictManager.TI.plate(), 6), new ComparableStack(ModItems.circuit_targeting_tier1, 1), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.missile_buster, 1), new AStack[] {new ComparableStack(ModItems.warhead_buster_small, 1), new ComparableStack(ModItems.fuel_tank_small, 1), new ComparableStack(ModItems.thruster_small, 1), new OreDictStack(OreDictManager.TI.plate(), 6), new ComparableStack(ModItems.circuit_targeting_tier1, 1), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.missile_strong, 1), new AStack[] {new ComparableStack(ModItems.warhead_generic_medium, 1), new ComparableStack(ModItems.fuel_tank_medium, 1), new ComparableStack(ModItems.thruster_medium, 1), new OreDictStack(OreDictManager.TI.plate(), 10), new OreDictStack(OreDictManager.STEEL.plate(), 14), new ComparableStack(ModItems.circuit_targeting_tier2, 1), },250);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.missile_incendiary_strong, 1), new AStack[] {new ComparableStack(ModItems.warhead_incendiary_medium, 1), new ComparableStack(ModItems.fuel_tank_medium, 1), new ComparableStack(ModItems.thruster_medium, 1), new OreDictStack(OreDictManager.TI.plate(), 10), new OreDictStack(OreDictManager.STEEL.plate(), 14), new ComparableStack(ModItems.circuit_targeting_tier2, 1), },250);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.missile_cluster_strong, 1), new AStack[] {new ComparableStack(ModItems.warhead_cluster_medium, 1), new ComparableStack(ModItems.fuel_tank_medium, 1), new ComparableStack(ModItems.thruster_medium, 1), new OreDictStack(OreDictManager.TI.plate(), 10), new OreDictStack(OreDictManager.STEEL.plate(), 14), new ComparableStack(ModItems.circuit_targeting_tier2, 1), },250);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.missile_buster_strong, 1), new AStack[] {new ComparableStack(ModItems.warhead_buster_medium, 1), new ComparableStack(ModItems.fuel_tank_medium, 1), new ComparableStack(ModItems.thruster_medium, 1), new OreDictStack(OreDictManager.TI.plate(), 10), new OreDictStack(OreDictManager.STEEL.plate(), 14), new ComparableStack(ModItems.circuit_targeting_tier2, 1), },250);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.missile_emp_strong, 1), new AStack[] {new ComparableStack(ModBlocks.emp_bomb, 3), new ComparableStack(ModItems.fuel_tank_medium, 1), new ComparableStack(ModItems.thruster_medium, 1), new OreDictStack(OreDictManager.TI.plate(), 10), new OreDictStack(OreDictManager.STEEL.plate(), 14), new ComparableStack(ModItems.circuit_targeting_tier2, 1), },250);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.missile_burst, 1), new AStack[] {new ComparableStack(ModItems.warhead_generic_large, 1), new ComparableStack(ModItems.fuel_tank_large, 1), new ComparableStack(ModItems.thruster_large, 1), new OreDictStack(OreDictManager.TI.plate(), 14), new OreDictStack(OreDictManager.STEEL.plate(), 20), new OreDictStack(OreDictManager.AL.plate(), 12), new ComparableStack(ModItems.circuit_targeting_tier3, 1), },350);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.missile_inferno, 1), new AStack[] {new ComparableStack(ModItems.warhead_incendiary_large, 1), new ComparableStack(ModItems.fuel_tank_large, 1), new ComparableStack(ModItems.thruster_large, 1), new OreDictStack(OreDictManager.TI.plate(), 14), new OreDictStack(OreDictManager.STEEL.plate(), 20), new OreDictStack(OreDictManager.AL.plate(), 12), new ComparableStack(ModItems.circuit_targeting_tier3, 1), },350);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.missile_rain, 1), new AStack[] {new ComparableStack(ModItems.warhead_cluster_large, 1), new ComparableStack(ModItems.fuel_tank_large, 1), new ComparableStack(ModItems.thruster_large, 1), new OreDictStack(OreDictManager.TI.plate(), 14), new OreDictStack(OreDictManager.STEEL.plate(), 20), new OreDictStack(OreDictManager.AL.plate(), 12), new ComparableStack(ModItems.circuit_targeting_tier3, 1), },350);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.missile_drill, 1), new AStack[] {new ComparableStack(ModItems.warhead_buster_large, 1), new ComparableStack(ModItems.fuel_tank_large, 1), new ComparableStack(ModItems.thruster_large, 1), new OreDictStack(OreDictManager.TI.plate(), 14), new OreDictStack(OreDictManager.STEEL.plate(), 20), new OreDictStack(OreDictManager.AL.plate(), 12), new ComparableStack(ModItems.circuit_targeting_tier3, 1), },350);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.missile_nuclear, 1), new AStack[] {new ComparableStack(ModItems.warhead_nuclear, 1), new ComparableStack(ModItems.fuel_tank_large, 1), new ComparableStack(ModItems.thruster_large, 1), new OreDictStack(OreDictManager.TI.plate(), 20), new OreDictStack(OreDictManager.STEEL.plate(), 24), new OreDictStack(OreDictManager.AL.plate(), 16), new ComparableStack(ModItems.circuit_targeting_tier4, 1), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.missile_nuclear_cluster, 1), new AStack[] {new ComparableStack(ModItems.warhead_mirv, 1), new ComparableStack(ModItems.fuel_tank_large, 1), new ComparableStack(ModItems.thruster_large, 1), new OreDictStack(OreDictManager.TI.plate(), 20), new OreDictStack(OreDictManager.STEEL.plate(), 24), new OreDictStack(OreDictManager.AL.plate(), 16), new ComparableStack(ModItems.circuit_targeting_tier5, 1), },600);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.missile_volcano, 1), new AStack[] {new ComparableStack(ModItems.warhead_volcano, 1), new ComparableStack(ModItems.fuel_tank_large, 1), new ComparableStack(ModItems.thruster_large, 1), new OreDictStack(OreDictManager.TI.plate(), 20), new OreDictStack(OreDictManager.STEEL.plate(), 24), new OreDictStack(OreDictManager.AL.plate(), 16), new ComparableStack(ModItems.circuit_targeting_tier5, 1), },600);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.missile_endo, 1), new AStack[] {new ComparableStack(ModItems.warhead_thermo_endo, 1), new ComparableStack(ModItems.fuel_tank_large, 1), new ComparableStack(ModItems.thruster_large, 1), new OreDictStack(OreDictManager.TI.plate(), 14), new OreDictStack(OreDictManager.STEEL.plate(), 20), new OreDictStack(OreDictManager.AL.plate(), 12), new ComparableStack(ModItems.circuit_targeting_tier4, 1), },350);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.missile_exo, 1), new AStack[] {new ComparableStack(ModItems.warhead_thermo_exo, 1), new ComparableStack(ModItems.fuel_tank_large, 1), new ComparableStack(ModItems.thruster_large, 1), new OreDictStack(OreDictManager.TI.plate(), 14), new OreDictStack(OreDictManager.STEEL.plate(), 20), new OreDictStack(OreDictManager.AL.plate(), 12), new ComparableStack(ModItems.circuit_targeting_tier4, 1), },350);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.gun_defabricator, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.ingot(), 2), new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 8), new OreDictStack(OreDictManager.IRON.plate(), 5), new ComparableStack(ModItems.mechanism_special, 3), new ComparableStack(Items.diamond, 1), new ComparableStack(ModItems.plate_dalekanium, 3), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.gun_osipr_ammo, 24), new AStack[] {new OreDictStack(OreDictManager.STEEL.plate(), 2), new OreDictStack(OreDictManager.REDSTONE.dust(), 1), new ComparableStack(Items.glowstone_dust, 1), },50);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.gun_osipr_ammo2, 1), new AStack[] {new OreDictStack(OreDictManager.CMB.plate(), 4), new OreDictStack(OreDictManager.REDSTONE.dust(), 7), new ComparableStack(ModItems.powder_power, 3), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.grenade_fire, 1), new AStack[] {new ComparableStack(ModItems.grenade_frag, 1), new OreDictStack(OreDictManager.P_RED.dust(), 1), new OreDictStack(OreDictManager.CU.plate(), 2), },150);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.grenade_shrapnel, 1), new AStack[] {new ComparableStack(ModItems.grenade_frag, 1), new ComparableStack(ModItems.pellet_buckshot, 1), new OreDictStack(OreDictManager.STEEL.plate(), 2), },150);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.grenade_cluster, 1), new AStack[] {new ComparableStack(ModItems.grenade_frag, 1), new ComparableStack(ModItems.pellet_cluster, 1), new OreDictStack(OreDictManager.STEEL.plate(), 2), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.grenade_flare, 1), new AStack[] {new ComparableStack(ModItems.grenade_generic, 1), new ComparableStack(Items.glowstone_dust, 1), new OreDictStack(OreDictManager.AL.plate(), 2), },100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.grenade_electric, 1), new AStack[] {new ComparableStack(ModItems.grenade_generic, 1), new ComparableStack(ModItems.circuit_red_copper, 1), new OreDictStack(OreDictManager.GOLD.plate(), 2), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.grenade_pulse, 4), new AStack[] {new OreDictStack(OreDictManager.STEEL.plate(), 1), new OreDictStack(OreDictManager.IRON.plate(), 3), new ComparableStack(ModItems.wire_red_copper, 6), new ComparableStack(Items.diamond, 1), },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.grenade_plasma, 2), new AStack[] {new OreDictStack(OreDictManager.STEEL.plate(), 3), new OreDictStack(OreDictManager.ALLOY.plate(), 1), new ComparableStack(ModItems.coil_advanced_torus, 1), new ComparableStack(ModItems.cell_deuterium, 1), new ComparableStack(ModItems.cell_tritium, 1), },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.grenade_tau, 2), new AStack[] {new OreDictStack(OreDictManager.PB.plate(), 3), new OreDictStack(OreDictManager.ALLOY.plate(), 1), new ComparableStack(ModItems.coil_advanced_torus, 1), new ComparableStack(ModItems.gun_xvl1456_ammo, 1), },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.grenade_schrabidium, 1), new AStack[] {new ComparableStack(ModItems.grenade_flare, 1), new OreDictStack(OreDictManager.SA326.dust(), 1), new OreDictStack(OreDictManager.getReflector(), 2), },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.grenade_nuclear, 1), new AStack[] {new OreDictStack(OreDictManager.IRON.plate(), 1), new OreDictStack(OreDictManager.STEEL.plate(), 1), new OreDictStack(OreDictManager.PU239.nugget(), 2), new ComparableStack(ModItems.wire_red_copper, 2), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.grenade_zomg, 1), new AStack[] {new ComparableStack(ModItems.plate_paa, 3), new OreDictStack(OreDictManager.getReflector(), 1), new ComparableStack(ModItems.coil_magnetized_tungsten, 3), new ComparableStack(ModItems.powder_power, 3), },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.grenade_black_hole, 1), new AStack[] {new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 6), new OreDictStack(OreDictManager.getReflector(), 3), new ComparableStack(ModItems.coil_magnetized_tungsten, 2), new ComparableStack(ModItems.black_hole, 1), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.early_explosive_lenses, 1), new AStack[] {new OreDictStack(OreDictManager.AL.plate(), 8), new ComparableStack(ModItems.wire_gold, 16), new ComparableStack(ModBlocks.det_cord, 8), new OreDictStack(OreDictManager.CU.plate(), 2), new OreDictStack(OreDictManager.ANY_HIGHEXPLOSIVE.ingot(), 20), new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 4)},400); //8 HE lenses (polymer inserts since no baratol) w/ bridge-wire detonators, aluminum pushers, & duraluminum shell
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.explosive_lenses, 1), new AStack[] {new OreDictStack(OreDictManager.AL.plate(), 8), new ComparableStack(ModItems.wire_red_copper, 16), new OreDictStack(OreDictManager.ANY_PLASTICEXPLOSIVE.ingot(), 4), new OreDictStack(OreDictManager.CU.plate(), 2), new ComparableStack(ModItems.ball_tatb, 16), new OreDictStack(OreDictManager.RUBBER.ingot(), 2)},500); //8 HE (To use 16 PBX ingots; rubber inserts) lenses w/ improved bridge-wire detonators, thin aluminum pushers, & duraluminum shell
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.gadget_wireing, 1), new AStack[] {new OreDictStack(OreDictManager.IRON.plate(), 1), new ComparableStack(ModItems.wire_gold, 12), },100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.gadget_core, 1), new AStack[] {new OreDictStack(OreDictManager.PU239.nugget(), 7), new OreDictStack(OreDictManager.U238.nugget(), 3), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.boy_shielding, 1), new AStack[] {new OreDictStack(OreDictManager.getReflector(), 12), new OreDictStack(OreDictManager.STEEL.plate528(), 4), },150);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.boy_target, 1), new AStack[] {new OreDictStack(OreDictManager.U235.nugget(), 18), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.boy_bullet, 1), new AStack[] {new OreDictStack(OreDictManager.U235.nugget(), 9), },100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.boy_propellant, 1), new AStack[] {new ComparableStack(ModItems.cordite, 8), new OreDictStack(OreDictManager.IRON.plate528(), 8), new OreDictStack(OreDictManager.AL.plate528(), 4), new ComparableStack(ModItems.wire_red_copper, 4), },100); 
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.boy_igniter, 1), new AStack[] {new OreDictStack(OreDictManager.ANY_HIGHEXPLOSIVE.ingot(), 1), new OreDictStack(OreDictManager.AL.plate528(), 6), new OreDictStack(OreDictManager.STEEL.plate528(), 1), new ComparableStack(ModItems.circuit_red_copper, 1), new ComparableStack(ModItems.wire_red_copper, 3), },150); //HE for gating purposes
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.man_igniter, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.plate528(), 6), new ComparableStack(ModItems.circuit_red_copper, 1), new ComparableStack(ModItems.wire_red_copper, 9), },150);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.man_core, 1), new AStack[] {new OreDictStack(OreDictManager.PU239.nugget(), 8), new OreDictStack(OreDictManager.BE.nugget(), 2), },250);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mike_core, 1), new AStack[] {new OreDictStack(OreDictManager.U238.nugget(), 24), new OreDictStack(OreDictManager.PB.ingot(), 6), },250);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mike_deut, 1), new AStack[] {new OreDictStack(OreDictManager.IRON.plate528(), 12), new OreDictStack(OreDictManager.STEEL.plate528(), 16), new ComparableStack(ModItems.cell_deuterium, 10), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mike_cooling_unit, 1), new AStack[] {new OreDictStack(OreDictManager.IRON.plate528(), 8), new ComparableStack(ModItems.coil_copper, 5), new ComparableStack(ModItems.coil_tungsten, 5), new ComparableStack(ModItems.motor, 2), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.fleija_igniter, 1), new AStack[] {new OreDictStack(OreDictManager.TI.plate528(), 6), new ComparableStack(ModItems.wire_schrabidium, 2), new ComparableStack(ModItems.circuit_schrabidium, 1), },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.fleija_core, 1), new AStack[] {new OreDictStack(OreDictManager.U235.nugget(), 8), new OreDictStack(OreDictManager.NP237.nugget(), 2), new OreDictStack(OreDictManager.BE.nugget(), 4), new ComparableStack(ModItems.coil_copper, 2), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.fleija_propellant, 1), new AStack[] {new OreDictStack(OreDictManager.ANY_HIGHEXPLOSIVE.ingot(), 3), new OreDictStack(OreDictManager.SA326.plate(), 8), },400);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.solinium_igniter, 1), new AStack[] {new OreDictStack(OreDictManager.TI.plate528(), 4), new ComparableStack(ModItems.wire_advanced_alloy, 2), new ComparableStack(ModItems.circuit_schrabidium, 1), new ComparableStack(ModItems.coil_gold, 1), },400);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.solinium_core, 1), new AStack[] {new OreDictStack(OreDictManager.SA327.nugget(), 9), new OreDictStack(OreDictManager.EUPH.nugget(), 1), },400);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.solinium_propellant, 1), new AStack[] {new OreDictStack(OreDictManager.ANY_HIGHEXPLOSIVE.ingot(), 3), new OreDictStack(OreDictManager.getReflector(), 2), new ComparableStack(ModItems.plate_polymer, 6), new ComparableStack(ModItems.wire_tungsten, 6), new ComparableStack(ModItems.biomass_compressed, 4), },350);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.schrabidium_hammer, 1), new AStack[] {new OreDictStack(OreDictManager.SA326.block(), 35), new ComparableStack(ModItems.billet_yharonite, 128), new ComparableStack(Items.nether_star, 3), new ComparableStack(ModItems.fragment_meteorite, 512), },1000);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.component_limiter, 1), new AStack[] {new ComparableStack(ModItems.hull_big_steel, 2), new OreDictStack(OreDictManager.STEEL.plate(), 32), new OreDictStack(OreDictManager.TI.plate(), 18), new ComparableStack(ModItems.plate_desh, 12), new ComparableStack(ModItems.pipes_steel, 4), new ComparableStack(ModItems.circuit_gold, 8), new ComparableStack(ModItems.circuit_schrabidium, 4), new OreDictStack(OreDictManager.STAR.ingot(), 14), new ComparableStack(ModItems.plate_dalekanium, 5), new ComparableStack(ModItems.powder_magic, 16), new ComparableStack(ModBlocks.fwatz_computer, 3), },2500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.component_emitter, 1), new AStack[] {new ComparableStack(ModItems.hull_big_steel, 3), new ComparableStack(ModItems.hull_big_titanium, 2), new OreDictStack(OreDictManager.STEEL.plate(), 32), new OreDictStack(OreDictManager.PB.plate(), 24), new ComparableStack(ModItems.plate_desh, 24), new ComparableStack(ModItems.pipes_steel, 8), new ComparableStack(ModItems.circuit_gold, 12), new ComparableStack(ModItems.circuit_schrabidium, 8), new OreDictStack(OreDictManager.STAR.ingot(), 26), new ComparableStack(ModItems.powder_magic, 48), new ComparableStack(ModBlocks.fwatz_computer, 2), new ComparableStack(ModItems.crystal_xen, 1), },2500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.ams_limiter, 1), new AStack[] {new ComparableStack(ModItems.board_copper, 6), new OreDictStack(OreDictManager.STEEL.plate(), 24), new ComparableStack(ModBlocks.steel_scaffold, 20), new ComparableStack(ModItems.crystal_diamond, 1)}, 600);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.ams_emitter, 1), new AStack[] {new ComparableStack(ModItems.board_copper, 24), new OreDictStack(OreDictManager.STEEL.plate(), 32), new ComparableStack(ModBlocks.steel_scaffold, 40), new ComparableStack(ModItems.crystal_redstone, 5), new ComparableStack(ModBlocks.machine_lithium_battery)}, 600);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.ams_base, 1), new AStack[] {new ComparableStack(ModItems.board_copper, 12), new OreDictStack(OreDictManager.STEEL.plate(), 28), new ComparableStack(ModBlocks.steel_scaffold, 30), new ComparableStack(ModBlocks.steel_grate, 8), new ComparableStack(ModBlocks.barrel_steel, 2)}, 600);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_radar, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.plate528(), 16), new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 4), new ComparableStack(ModItems.plate_polymer, 24), new ComparableStack(ModItems.magnetron, 10), new ComparableStack(ModItems.motor, 3), new ComparableStack(ModItems.circuit_gold, 4), new ComparableStack(ModItems.coil_copper, 12), new ComparableStack(ModItems.crt_display, 4), },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_forcefield, 1), new AStack[] {new OreDictStack(OreDictManager.ALLOY.plate528(), 8), new ComparableStack(ModItems.plate_desh, 4), new ComparableStack(ModItems.coil_gold_torus, 6), new ComparableStack(ModItems.coil_magnetized_tungsten, 12), new ComparableStack(ModItems.motor, 1), new ComparableStack(ModItems.upgrade_radius, 1), new ComparableStack(ModItems.upgrade_health, 1), new ComparableStack(ModItems.circuit_targeting_tier5, 1), new ComparableStack(ModBlocks.machine_transformer, 1), },1000);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_thruster_10_kerosene, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new ComparableStack(ModBlocks.deco_pipe_quad, 1), new OreDictStack(OreDictManager.W.ingot(), 4), new OreDictStack(OreDictManager.STEEL.plate(), 4), },100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_thruster_10_solid, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new ComparableStack(ModItems.coil_tungsten, 1), new OreDictStack(OreDictManager.DURA.ingot(), 4), new OreDictStack(OreDictManager.STEEL.plate(), 4), },100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_thruster_10_xenon, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new OreDictStack(OreDictManager.STEEL.plate(), 4), new ComparableStack(ModItems.pipes_steel, 2), new ComparableStack(ModItems.arc_electrode, 4), },100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_thruster_15_kerosene, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModBlocks.deco_pipe_quad, 1), new OreDictStack(OreDictManager.W.ingot(), 8), new OreDictStack(OreDictManager.STEEL.plate(), 6), new OreDictStack(OreDictManager.DESH.ingot(), 4), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_thruster_15_kerosene_dual, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModBlocks.deco_pipe_quad, 1), new OreDictStack(OreDictManager.W.ingot(), 4), new OreDictStack(OreDictManager.STEEL.plate(), 6), new OreDictStack(OreDictManager.DESH.ingot(), 1), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_thruster_15_kerosene_triple, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModBlocks.deco_pipe_quad, 1), new OreDictStack(OreDictManager.W.ingot(), 6), new OreDictStack(OreDictManager.STEEL.plate(), 6), new OreDictStack(OreDictManager.DESH.ingot(), 2), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_thruster_15_solid, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new OreDictStack(OreDictManager.STEEL.plate(), 6), new OreDictStack(OreDictManager.DURA.ingot(), 6), new ComparableStack(ModItems.coil_tungsten, 3), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_thruster_15_solid_hexdecuple, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new OreDictStack(OreDictManager.STEEL.plate(), 6), new OreDictStack(OreDictManager.DURA.ingot(), 12), new ComparableStack(ModItems.coil_tungsten, 6), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_thruster_15_hydrogen, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModBlocks.deco_pipe_quad, 1), new OreDictStack(OreDictManager.W.ingot(), 8), new OreDictStack(OreDictManager.STEEL.plate(), 6), new ComparableStack(ModItems.tank_steel, 1), new OreDictStack(OreDictManager.DESH.ingot(), 4), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_thruster_15_hydrogen_dual, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModBlocks.deco_pipe_quad, 1), new OreDictStack(OreDictManager.W.ingot(), 4), new OreDictStack(OreDictManager.STEEL.plate(), 6), new ComparableStack(ModItems.tank_steel, 1), new OreDictStack(OreDictManager.DESH.ingot(), 1), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_thruster_15_balefire_short, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModItems.plate_polymer, 8), new ComparableStack(ModBlocks.pwr_fuel, 1), new OreDictStack(OreDictManager.DESH.ingot(), 8), new OreDictStack(OreDictManager.BIGMT.plate(), 12), new ComparableStack(ModItems.board_copper, 2), new ComparableStack(ModItems.ingot_uranium_fuel, 4), new ComparableStack(ModItems.pipes_steel, 2), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_thruster_15_balefire, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModItems.plate_polymer, 16), new ComparableStack(ModBlocks.pwr_fuel, 2), new OreDictStack(OreDictManager.DESH.ingot(), 16), new OreDictStack(OreDictManager.BIGMT.plate(), 24), new ComparableStack(ModItems.board_copper, 4), new ComparableStack(ModItems.ingot_uranium_fuel, 8), new ComparableStack(ModItems.pipes_steel, 2), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_thruster_15_balefire_large, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModItems.plate_polymer, 16), new ComparableStack(ModBlocks.pwr_fuel, 2), new OreDictStack(OreDictManager.DESH.ingot(), 24), new OreDictStack(OreDictManager.BIGMT.plate(), 32), new ComparableStack(ModItems.board_copper, 4), new ComparableStack(ModItems.ingot_uranium_fuel, 8), new ComparableStack(ModItems.pipes_steel, 2), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_thruster_20_kerosene, 1), new AStack[] {new ComparableStack(ModItems.seg_20, 1), new ComparableStack(ModBlocks.deco_pipe_quad, 1), new OreDictStack(OreDictManager.W.ingot(), 16), new OreDictStack(OreDictManager.STEEL.plate(), 12), new OreDictStack(OreDictManager.DESH.ingot(), 8), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_thruster_20_kerosene_dual, 1), new AStack[] {new ComparableStack(ModItems.seg_20, 1), new ComparableStack(ModBlocks.deco_pipe_quad, 1), new OreDictStack(OreDictManager.W.ingot(), 8), new OreDictStack(OreDictManager.STEEL.plate(), 6), new OreDictStack(OreDictManager.DESH.ingot(), 4), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_thruster_20_kerosene_triple, 1), new AStack[] {new ComparableStack(ModItems.seg_20, 1), new ComparableStack(ModBlocks.deco_pipe_quad, 1), new OreDictStack(OreDictManager.W.ingot(), 12), new OreDictStack(OreDictManager.STEEL.plate(), 8), new OreDictStack(OreDictManager.DESH.ingot(), 6), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_thruster_20_solid, 1), new AStack[] {new ComparableStack(ModItems.seg_20, 1), new ComparableStack(ModItems.coil_tungsten, 8), new OreDictStack(OreDictManager.DURA.ingot(), 16), new OreDictStack(OreDictManager.STEEL.plate(), 12), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_thruster_20_solid_multi, 1), new AStack[] {new ComparableStack(ModItems.seg_20, 1), new ComparableStack(ModItems.coil_tungsten, 12), new OreDictStack(OreDictManager.DURA.ingot(), 18), new OreDictStack(OreDictManager.STEEL.plate(), 12), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_thruster_20_solid_multier, 1), new AStack[] {new ComparableStack(ModItems.seg_20, 1), new ComparableStack(ModItems.coil_tungsten, 16), new OreDictStack(OreDictManager.DURA.ingot(), 20), new OreDictStack(OreDictManager.STEEL.plate(), 12), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_fuselage_10_kerosene, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 2), new ComparableStack(ModBlocks.steel_scaffold, 3), new OreDictStack(OreDictManager.TI.plate(), 12), new OreDictStack(OreDictManager.STEEL.plate(), 3), },100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_fuselage_10_solid, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 2), new ComparableStack(ModBlocks.steel_scaffold, 3), new OreDictStack(OreDictManager.TI.plate(), 12), new OreDictStack(OreDictManager.AL.plate(), 3), },100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_fuselage_10_xenon, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 2), new ComparableStack(ModBlocks.steel_scaffold, 3), new OreDictStack(OreDictManager.TI.plate(), 12), new ComparableStack(ModItems.board_copper, 3), },100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_fuselage_10_long_kerosene, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 2), new ComparableStack(ModBlocks.steel_scaffold, 6), new OreDictStack(OreDictManager.TI.plate(), 24), new OreDictStack(OreDictManager.STEEL.plate(), 6), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_fuselage_10_long_solid, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 2), new ComparableStack(ModBlocks.steel_scaffold, 6), new OreDictStack(OreDictManager.TI.plate(), 24), new OreDictStack(OreDictManager.AL.plate(), 6), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_fuselage_10_15_kerosene, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModBlocks.steel_scaffold, 9), new OreDictStack(OreDictManager.TI.plate(), 36), new OreDictStack(OreDictManager.STEEL.plate(), 9), },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_fuselage_10_15_solid, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModBlocks.steel_scaffold, 9), new OreDictStack(OreDictManager.TI.plate(), 36), new OreDictStack(OreDictManager.AL.plate(), 9), },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_fuselage_10_15_hydrogen, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModBlocks.steel_scaffold, 9), new OreDictStack(OreDictManager.TI.plate(), 36), new OreDictStack(OreDictManager.IRON.plate(), 9), },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_fuselage_10_15_balefire, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModBlocks.steel_scaffold, 9), new OreDictStack(OreDictManager.TI.plate(), 36), new OreDictStack(OreDictManager.BIGMT.plate(), 9), },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_fuselage_15_kerosene, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 2), new ComparableStack(ModBlocks.steel_scaffold, 12), new OreDictStack(OreDictManager.TI.plate(), 48), new OreDictStack(OreDictManager.STEEL.plate(), 12), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_fuselage_15_solid, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 2), new ComparableStack(ModBlocks.steel_scaffold, 12), new OreDictStack(OreDictManager.TI.plate(), 48), new OreDictStack(OreDictManager.AL.plate(), 12), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_fuselage_15_hydrogen, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 2), new ComparableStack(ModBlocks.steel_scaffold, 12), new OreDictStack(OreDictManager.TI.plate(), 48), new OreDictStack(OreDictManager.IRON.plate(), 12), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_fuselage_10_15_balefire, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModBlocks.steel_scaffold, 9), new OreDictStack(OreDictManager.TI.plate(), 36), new OreDictStack(OreDictManager.BIGMT.plate(), 9), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_fuselage_15_20_kerosene, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModItems.seg_20, 1), new ComparableStack(ModBlocks.steel_scaffold, 16), new OreDictStack(OreDictManager.TI.plate(), 64), new OreDictStack(OreDictManager.STEEL.plate(), 16), },600);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_fuselage_15_20_solid, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModItems.seg_20, 1), new ComparableStack(ModBlocks.steel_scaffold, 16), new OreDictStack(OreDictManager.TI.plate(), 64), new OreDictStack(OreDictManager.AL.plate(), 16), },600);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_warhead_10_he, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new OreDictStack(OreDictManager.STEEL.plate(), 6), new OreDictStack(OreDictManager.ANY_HIGHEXPLOSIVE.ingot(), 3), new ComparableStack(ModItems.circuit_targeting_tier2, 1), },100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_warhead_10_incendiary, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new OreDictStack(OreDictManager.TI.plate(), 4), new OreDictStack(OreDictManager.P_RED.dust(), 3), new OreDictStack(OreDictManager.ANY_HIGHEXPLOSIVE.ingot(), 2), new ComparableStack(ModItems.circuit_targeting_tier2, 1), },100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_warhead_10_buster, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new OreDictStack(OreDictManager.TI.plate(), 4), new ComparableStack(ModBlocks.det_charge, 1), new ComparableStack(ModBlocks.det_cord, 4), new ComparableStack(ModItems.board_copper, 4), new ComparableStack(ModItems.circuit_targeting_tier3, 1), },100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_warhead_10_nuclear, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new OreDictStack(OreDictManager.STEEL.plate(), 6), new OreDictStack(OreDictManager.PU239.ingot(), 1), new OreDictStack(OreDictManager.getReflector(), 2), new OreDictStack(OreDictManager.ANY_HIGHEXPLOSIVE.ingot(), 4), new ComparableStack(ModItems.circuit_targeting_tier3, 1), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_warhead_10_nuclear_large, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new OreDictStack(OreDictManager.STEEL.plate(), 8), new OreDictStack(OreDictManager.AL.plate(), 4), new OreDictStack(OreDictManager.PU239.ingot(), 2), new ComparableStack(ModBlocks.det_charge, 4), new ComparableStack(ModItems.circuit_targeting_tier4, 1), },300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_warhead_10_taint, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new OreDictStack(OreDictManager.STEEL.plate(), 12), new ComparableStack(ModBlocks.det_cord, 2), new ComparableStack(ModItems.powder_magic, 12), new ComparableStack(ModItems.bucket_mud, 1), },100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_warhead_10_cloud, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new OreDictStack(OreDictManager.STEEL.plate(), 12), new ComparableStack(ModBlocks.det_cord, 2), new ComparableStack(ModItems.grenade_pink_cloud, 2), },100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_warhead_15_he, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new OreDictStack(OreDictManager.STEEL.plate(), 16), new ComparableStack(ModBlocks.det_charge, 4), new ComparableStack(ModItems.circuit_targeting_tier3, 1), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_warhead_15_incendiary, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new OreDictStack(OreDictManager.STEEL.plate(), 16), new ComparableStack(ModBlocks.det_charge, 2), new OreDictStack(OreDictManager.P_RED.dust(), 8), new ComparableStack(ModItems.circuit_targeting_tier3, 1), },200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_warhead_15_nuclear, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new OreDictStack(OreDictManager.STEEL.plate(), 24), new OreDictStack(OreDictManager.TI.plate(), 12), new OreDictStack(OreDictManager.PU239.ingot(), 3), new ComparableStack(ModBlocks.det_charge, 6), new ComparableStack(ModItems.circuit_targeting_tier4, 1), },500);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_warhead_15_n2, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new OreDictStack(OreDictManager.STEEL.plate(), 8), new OreDictStack(OreDictManager.TI.plate(), 20), new ComparableStack(ModBlocks.det_charge, 24), new ComparableStack(Blocks.redstone_block, 12), new OreDictStack(OreDictManager.MAGTUNG.dust(), 6), new ComparableStack(ModItems.circuit_targeting_tier4, 1), },400);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.mp_warhead_15_balefire, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new OreDictStack(OreDictManager.getReflector(), 16), new ComparableStack(ModItems.powder_magic, 6), new ComparableStack(ModItems.egg_balefire_shard, 4), new OreDictStack(OreDictManager.ANY_HIGHEXPLOSIVE.ingot(), 8), new ComparableStack(ModItems.circuit_targeting_tier4, 1), }, 60);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.missile_soyuz, 1), new AStack[] {new ComparableStack(ModItems.rocket_fuel, 40), new ComparableStack(ModBlocks.det_cord, 20), new ComparableStack(ModItems.thruster_medium, 12), new ComparableStack(ModItems.thruster_small, 12), new ComparableStack(ModItems.tank_steel, 10), new ComparableStack(ModItems.circuit_targeting_tier4, 2), new ComparableStack(ModItems.circuit_targeting_tier3, 8), new OreDictStack(OreDictManager.RUBBER.ingot(), 64), new ComparableStack(ModItems.fins_small_steel, 4), new ComparableStack(ModItems.hull_big_titanium, 32), new ComparableStack(ModItems.hull_big_steel, 18), new OreDictStack(OreDictManager.FIBER.ingot(), 64), },600);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.missile_soyuz_lander, 1), new AStack[] {new ComparableStack(ModItems.rocket_fuel, 10), new ComparableStack(ModItems.thruster_small, 3), new ComparableStack(ModItems.tank_steel, 2), new ComparableStack(ModItems.circuit_targeting_tier3, 4), new ComparableStack(ModItems.plate_polymer, 32), new ComparableStack(ModItems.hull_big_aluminium, 2), new ComparableStack(ModItems.sphere_steel, 1), new OreDictStack(OreDictManager.FIBER.ingot(), 12), },600);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.fusion_shield_tungsten, 1), new AStack[] {new OreDictStack(OreDictManager.W.block(), 32), new OreDictStack(OreDictManager.getReflector(), 96)}, 600);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.fusion_shield_desh, 1), new AStack[] {new OreDictStack(OreDictManager.DESH.block(), 16), new OreDictStack(OreDictManager.CO.block(), 16), new OreDictStack(OreDictManager.BIGMT.plate(), 96)}, 600);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.fusion_shield_chlorophyte, 1), new AStack[] {new OreDictStack(OreDictManager.W.block(), 16), new OreDictStack(OreDictManager.DURA.block(), 16), new OreDictStack(OreDictManager.getReflector(), 48), new ComparableStack(ModItems.powder_chlorophyte, 48)}, 600);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_fensu, 1), new AStack[] {
				new ComparableStack(ModItems.ingot_electronium, 32),
				new ComparableStack(ModBlocks.machine_dineutronium_battery, 16),
				!exp ? new OreDictStack(OreDictManager.ANY_RESISTANTALLOY.plateWelded(), 64) : new OreDictStack(OreDictManager.ANY_RESISTANTALLOY.heavyComp(), 64),
				new OreDictStack(OreDictManager.DURA.block(), 16),
				new OreDictStack(OreDictManager.STAR.block(), 64),
				new ComparableStack(ModBlocks.machine_transformer_dnt, 8),
				new ComparableStack(ModItems.coil_magnetized_tungsten, 24),
				new ComparableStack(ModItems.powder_magic, 64),
				new ComparableStack(ModItems.plate_dineutronium, 24),
				new ComparableStack(ModItems.ingot_u238m2),
				new ComparableStack(ModItems.ingot_cft, 128)
			}, 1200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.struct_iter_core, 1), new AStack[] {
				!exp ? new OreDictStack(OreDictManager.ANY_RESISTANTALLOY.plateWelded(), 6) : new OreDictStack(OreDictManager.ANY_RESISTANTALLOY.heavyComp(), 2),
				!exp ? new OreDictStack(OreDictManager.W.plateWelded(), 6) : new OreDictStack(OreDictManager.W.heavyComp(), 1),
				new OreDictStack(OreDictManager.getReflector(), 12),
				new ComparableStack(ModItems.coil_advanced_alloy, 12),
				new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 8),
				new ComparableStack(ModItems.circuit_red_copper, 8),
				new OreDictStack(OreDictManager.KEY_CIRCUIT_BISMUTH, 1)
			}, 600);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_combustion_engine, 1), new AStack[] {
				new OreDictStack(OreDictManager.STEEL.plate528(), 12),
				new OreDictStack(OreDictManager.IRON.plate(), 8),
				new OreDictStack(OreDictManager.CU.ingot(), 8),
				new ComparableStack(ModItems.generator_steel, 1),
				new ComparableStack(ModItems.tank_steel, 2),
				new ComparableStack(ModItems.bolt_tungsten, 8),
				new ComparableStack(ModItems.wire_red_copper, 24),
				new ComparableStack(ModItems.circuit_copper, 1)
			}, 300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.piston_set, 1, EnumPistonType.STEEL.ordinal()), new AStack[] {
				new OreDictStack(OreDictManager.STEEL.plate(), 16),
				new OreDictStack(OreDictManager.CU.plate(), 4),
				new OreDictStack(OreDictManager.W.ingot(), 8),
				new ComparableStack(ModItems.bolt_tungsten, 16)
			}, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.piston_set, 1, EnumPistonType.DURA.ordinal()), new AStack[] {
				new OreDictStack(OreDictManager.DURA.ingot(), 24),
				new OreDictStack(OreDictManager.TI.plate(), 8),
				new OreDictStack(OreDictManager.W.ingot(), 8),
				new ComparableStack(ModItems.bolt_dura_steel, 16)
			}, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.piston_set, 1, EnumPistonType.DESH.ordinal()), new AStack[] {
				new OreDictStack(OreDictManager.DESH.ingot(), 24),
				new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 12),
				new OreDictStack(OreDictManager.CU.plate(), 24),
				new OreDictStack(OreDictManager.W.ingot(), 16),
				new ComparableStack(ModItems.bolt_compound, 16)
			}, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.piston_set, 1, EnumPistonType.STARMETAL.ordinal()), new AStack[] {
				new OreDictStack(OreDictManager.STAR.ingot(), 24),
				new OreDictStack(OreDictManager.RUBBER.ingot(), 16),
				new OreDictStack(OreDictManager.BIGMT.plate(), 24),
				new OreDictStack(OreDictManager.NB.ingot(), 16),
				new ComparableStack(ModItems.bolt_compound, 16)
			}, 200);

		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_excavator, 1), new AStack[] {
				new ComparableStack(Blocks.stonebrick, 8),
				new OreDictStack(OreDictManager.STEEL.ingot(), 16),
				new OreDictStack(OreDictManager.IRON.ingot(), 16),
				new ComparableStack(ModBlocks.steel_scaffold, 16),
				new ComparableStack(ModItems.motor, 2),
				new ComparableStack(ModItems.tank_steel, 1),
				new ComparableStack(ModItems.circuit_red_copper, 1)
			}, 300);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.drillbit, 1, EnumDrillType.STEEL.ordinal()), new AStack[] {
				new OreDictStack(OreDictManager.STEEL.ingot(), 12),
				new OreDictStack(OreDictManager.W.ingot(), 4)
			}, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.drillbit, 1, EnumDrillType.STEEL_DIAMOND.ordinal()), new AStack[] {
				new ComparableStack(ModItems.drillbit, 1, EnumDrillType.STEEL.ordinal()),
				new OreDictStack(OreDictManager.DIAMOND.dust(), 16)
			}, 100);
		
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.drillbit, 1, EnumDrillType.HSS.ordinal()), new AStack[] {
				new OreDictStack(OreDictManager.DURA.ingot(), 12),
				new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 12),
				new OreDictStack(OreDictManager.TI.ingot(), 8)
			}, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.drillbit, 1, EnumDrillType.HSS_DIAMOND.ordinal()), new AStack[] {
				new ComparableStack(ModItems.drillbit, 1, EnumDrillType.HSS.ordinal()),
				new OreDictStack(OreDictManager.DIAMOND.dust(), 24)
			}, 100);
		
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.drillbit, 1, EnumDrillType.DESH.ordinal()), new AStack[] {
				new OreDictStack(OreDictManager.DESH.ingot(), 16),
				new OreDictStack(OreDictManager.RUBBER.ingot(), 12),
				new OreDictStack(OreDictManager.NB.ingot(), 4)
			}, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.drillbit, 1, EnumDrillType.DESH_DIAMOND.ordinal()), new AStack[] {
				new ComparableStack(ModItems.drillbit, 1, EnumDrillType.DESH.ordinal()),
				new OreDictStack(OreDictManager.DIAMOND.dust(), 32)
			}, 100);
		
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.drillbit, 1, EnumDrillType.TCALLOY.ordinal()), new AStack[] {
				new OreDictStack(OreDictManager.ANY_RESISTANTALLOY.ingot(), 20),
				new OreDictStack(OreDictManager.DESH.ingot(), 12),
				new OreDictStack(OreDictManager.RUBBER.ingot(), 8)
			}, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.drillbit, 1, EnumDrillType.TCALLOY_DIAMOND.ordinal()), new AStack[] {
				new ComparableStack(ModItems.drillbit, 1, EnumDrillType.TCALLOY.ordinal()),
				new OreDictStack(OreDictManager.DIAMOND.dust(), 48)
			}, 100);
		
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.drillbit, 1, EnumDrillType.FERRO.ordinal()), new AStack[] {
				new OreDictStack(OreDictManager.FERRO.ingot(), 24),
				new OreDictStack(OreDictManager.ANY_RESISTANTALLOY.ingot(), 12),
				new OreDictStack(OreDictManager.BI.ingot(), 4),
			}, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.drillbit, 1, EnumDrillType.FERRO_DIAMOND.ordinal()), new AStack[] {
				new ComparableStack(ModItems.drillbit, 1, EnumDrillType.FERRO.ordinal()),
				new OreDictStack(OreDictManager.DIAMOND.dust(), 56)
			}, 100);
		
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_large_turbine, 1), new AStack[] {
				!exp ? new OreDictStack(OreDictManager.STEEL.plate528(), 12) : new OreDictStack(OreDictManager.STEEL.heavyComp(), 1),
				new OreDictStack(OreDictManager.RUBBER.ingot(), 4),
				new ComparableStack(ModItems.turbine_titanium, 3),
				new ComparableStack(ModItems.generator_steel, 1),
				new ComparableStack(ModItems.bolt_compound, 3),
				new ComparableStack(ModItems.pipes_steel, 1),
				new ComparableStack(ModItems.circuit_aluminium, 1),
			}, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_chungus, 1), new AStack[] {
				new ComparableStack(ModItems.hull_big_steel, 6),
				!exp ? new OreDictStack(OreDictManager.STEEL.plateWelded(), 16) : new OreDictStack(OreDictManager.STEEL.heavyComp(), 3),
				!exp ? new OreDictStack(OreDictManager.TI.plate528(), 12) : new OreDictStack(OreDictManager.TI.heavyComp(), 1),
				new OreDictStack(OreDictManager.ANY_RESISTANTALLOY.ingot(), 16),
				new ComparableStack(ModItems.turbine_tungsten, 5),
				new ComparableStack(ModItems.turbine_titanium, 3),
				new ComparableStack(ModItems.flywheel_beryllium, 1),
				new ComparableStack(ModItems.generator_steel, 10),
				new ComparableStack(ModItems.bolt_compound, 16),
				new ComparableStack(ModItems.pipes_steel, 3)
			}, 600);
		
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_condenser_powered, 1), new AStack[] {
				!exp ? new OreDictStack(OreDictManager.STEEL.plateWelded(), 8) : new OreDictStack(OreDictManager.STEEL.heavyComp(), 3),
				new OreDictStack(OreDictManager.ANY_RESISTANTALLOY.plateWelded(), 4),
				!exp ? new OreDictStack(OreDictManager.CU.plate528(), 16) : new OreDictStack(OreDictManager.CU.heavyComp(), 3),
				new ComparableStack(ModItems.motor_desh, 3),
				new ComparableStack(ModItems.pipes_steel, 4),
				new OreDictStack(Fluids.LUBRICANT.getDict(1_000), 4)
			}, 600);

		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.pellet_chlorophyte, 2), new AStack[] {
				new ComparableStack(ModItems.powder_chlorophyte, 1),
				new OreDictStack(OreDictManager.PB.nugget(), 12),
			}, 50);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.pellet_mercury, 2), new AStack[] {
				new ComparableStack(ModItems.ingot_mercury, 1),
				new OreDictStack(OreDictManager.PB.nugget(), 12),
			}, 50);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.pellet_meteorite, 2), new AStack[] {
				new ComparableStack(ModItems.powder_meteorite, 1),
				new OreDictStack(OreDictManager.PB.nugget(), 12),
			}, 50);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.pellet_canister, 2), new AStack[] {
				new OreDictStack(OreDictManager.IRON.ingot(), 3),
			}, 50);
		
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_cyclotron, 1), new AStack[] {
				new ComparableStack(ModBlocks.machine_lithium_battery, 3),
				new ComparableStack(ModBlocks.hadron_coil_neodymium, 8),
				new ComparableStack(ModItems.wire_advanced_alloy, 96),
				!exp ? new OreDictStack(OreDictManager.STEEL.ingot(), 16) : new OreDictStack(OreDictManager.STEEL.heavyComp(), 3),
				new OreDictStack(OreDictManager.STEEL.plate528(), 32),
				new OreDictStack(OreDictManager.AL.plate528(), 32),
				new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 24),
				new OreDictStack(OreDictManager.RUBBER.ingot(), 24),
				new ComparableStack(ModItems.board_copper, 8),
				new ComparableStack(ModItems.circuit_red_copper, 8),
				new ComparableStack(ModItems.circuit_gold, 3),
			}, 600);
		
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.reactor_zirnox, 1), new AStack[] {
				!exp ? new ComparableStack(ModItems.hull_big_steel, 4) : new OreDictStack(OreDictManager.STEEL.heavyComp(), 1),
				new ComparableStack(ModBlocks.steel_scaffold, 4),
				new OreDictStack(OreDictManager.ANY_CONCRETE.any(), 16),
				new ComparableStack(ModBlocks.deco_pipe_quad, 8),
				new ComparableStack(ModItems.motor, 4),
				new OreDictStack(OreDictManager.B.ingot(), 8),
				new OreDictStack(OreDictManager.GRAPHITE.ingot(), 16),
				new ComparableStack(ModItems.circuit_red_copper, 3)
			}, 600);

		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.gun_zomg, 1), new AStack[] {
				new ComparableStack(ModItems.crystal_xen, 2),
				new ComparableStack(ModItems.singularity_counter_resonant, 1),
				new ComparableStack(ModItems.mechanism_special, 3),
				new ComparableStack(ModItems.plate_paa, 12),
				new OreDictStack(OreDictManager.getReflector(), 8),
				new ComparableStack(ModItems.coil_magnetized_tungsten, 5),
				new ComparableStack(ModItems.powder_magic, 4),
				new OreDictStack(OreDictManager.ASBESTOS.ingot(), 8)
			}, 200);

		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.ammo_75bolt, 2, ItemAmmoEnums.Ammo75Bolt.STOCK.ordinal()), new AStack[] {
				new OreDictStack(OreDictManager.STEEL.plate(), 2),
				new OreDictStack(OreDictManager.CU.plate(), 1),
				new ComparableStack(ModItems.casing_50, 5),
				new OreDictStack(OreDictManager.ANY_PLASTICEXPLOSIVE.ingot(), 2),
				new ComparableStack(ModItems.cordite, 3),
				new OreDictStack(OreDictManager.U238.ingot(), 1)
			}, 60);

		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.ammo_75bolt, 2, ItemAmmoEnums.Ammo75Bolt.INCENDIARY.ordinal()), new AStack[] {
				new OreDictStack(OreDictManager.STEEL.plate(), 2),
				new OreDictStack(OreDictManager.CU.plate(), 1),
				new ComparableStack(ModItems.casing_50, 5),
				new OreDictStack(OreDictManager.ANY_PLASTICEXPLOSIVE.ingot(), 3),
				new ComparableStack(ModItems.cordite, 3),
				new OreDictStack(OreDictManager.P_WHITE.ingot(), 3)
			}, 60);

		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.ammo_75bolt, 2, ItemAmmoEnums.Ammo75Bolt.HE.ordinal()), new AStack[] {
				new OreDictStack(OreDictManager.STEEL.plate(), 2),
				new OreDictStack(OreDictManager.CU.plate(), 1),
				new ComparableStack(ModItems.casing_50, 5),
				new OreDictStack(OreDictManager.ANY_HIGHEXPLOSIVE.ingot(), 5),
				new ComparableStack(ModItems.cordite, 5),
				new OreDictStack(OreDictManager.REDSTONE.dust(), 3)
			}, 60);

		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.spawn_worm, 1), new AStack[] {
				new OreDictStack(OreDictManager.TI.block(), 75),
				new ComparableStack(ModItems.motor, 75),
				new ComparableStack(ModBlocks.glass_trinitite, 25),
				new OreDictStack(OreDictManager.REDSTONE.dust(), 75),
				new ComparableStack(ModItems.wire_gold, 75),
				new OreDictStack(OreDictManager.PO210.block(), 10),
				new ComparableStack(ModItems.plate_armor_titanium, 50),
				new ComparableStack(ModItems.coin_worm, 1)
			}, 1200);

		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.sat_gerald, 1), new AStack[] {
				new ComparableStack(ModItems.burnt_bark, 1),
				new ComparableStack(ModItems.combine_scrap, 1),
				new ComparableStack(ModItems.crystal_horn, 1),
				new ComparableStack(ModItems.crystal_charred, 1),
				new ComparableStack(ModBlocks.pink_log, 1),
				new ComparableStack(ModItems.mp_warhead_15_balefire, 1),
				new ComparableStack(ModBlocks.det_nuke, 16),
				new OreDictStack(OreDictManager.STAR.ingot(), 32),
				new ComparableStack(ModItems.coin_creeper, 1),
				new ComparableStack(ModItems.coin_radiation, 1),
				new ComparableStack(ModItems.coin_maskman, 1),
				new ComparableStack(ModItems.coin_worm, 1),
			}, 1200);
		
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.vault_door, 1), new AStack[] {
				new OreDictStack(OreDictManager.STEEL.ingot(), 32),
				new OreDictStack(OreDictManager.W.ingot(), 32),
				new OreDictStack(OreDictManager.PB.plate(), 16),
				new OreDictStack(OreDictManager.ALLOY.plate(), 4),
				new ComparableStack(ModItems.plate_polymer, 4),
				new ComparableStack(ModItems.bolt_tungsten, 8),
				new ComparableStack(ModItems.bolt_dura_steel, 8),
				new ComparableStack(ModItems.motor, 3),
			}, 200);
		
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.blast_door, 1), new AStack[] {
				new OreDictStack(OreDictManager.STEEL.ingot(), 8),
				new OreDictStack(OreDictManager.W.ingot(), 8),
				new OreDictStack(OreDictManager.PB.plate(), 6),
				new OreDictStack(OreDictManager.ALLOY.plate(), 3),
				new ComparableStack(ModItems.plate_polymer, 3),
				new ComparableStack(ModItems.bolt_tungsten, 3),
				new ComparableStack(ModItems.bolt_dura_steel, 3),
				new ComparableStack(ModItems.motor, 1),
			}, 300);
		
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.fire_door, 1), new AStack[] {
				new OreDictStack(OreDictManager.STEEL.ingot(), 8),
				new OreDictStack(OreDictManager.STEEL.plate(), 8),
				new OreDictStack(OreDictManager.ALLOY.plate(), 4),
				new ComparableStack(ModItems.bolt_tungsten, 4),
				new ComparableStack(ModItems.motor, 2),
			}, 200);

		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.turret_chekhov, 1), new AStack[] {
				new ComparableStack(ModBlocks.machine_battery, 1),
				new OreDictStack(OreDictManager.STEEL.ingot(), 16),
				new OreDictStack(OreDictManager.DURA.ingot(), 4),
				new ComparableStack(ModItems.motor, 3),
				new ComparableStack(ModItems.circuit_targeting_tier3, 1),
				new ComparableStack(ModItems.pipes_steel, 1),
				new ComparableStack(ModItems.mechanism_rifle_2, 1),
				new ComparableStack(ModBlocks.crate_iron, 1),
				new ComparableStack(ModItems.crt_display, 1)
			}, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.turret_friendly, 1), new AStack[] {
				new ComparableStack(ModBlocks.machine_battery, 1),
				new OreDictStack(OreDictManager.STEEL.ingot(), 16),
				new OreDictStack(OreDictManager.DURA.ingot(), 4),
				new ComparableStack(ModItems.motor, 3),
				new ComparableStack(ModItems.circuit_targeting_tier2, 1),
				new ComparableStack(ModItems.pipes_steel, 1),
				new ComparableStack(ModItems.mechanism_rifle_1, 1),
				new ComparableStack(ModBlocks.crate_iron, 1),
				new ComparableStack(ModItems.crt_display, 1)
			}, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.turret_jeremy, 1), new AStack[] {
				new ComparableStack(ModBlocks.machine_battery, 1),
				new OreDictStack(OreDictManager.STEEL.ingot(), 16),
				new OreDictStack(OreDictManager.DURA.ingot(), 4),
				new ComparableStack(ModItems.motor, 2),
				new ComparableStack(ModItems.circuit_targeting_tier4, 1),
				new ComparableStack(ModItems.motor_desh, 1),
				new ComparableStack(ModItems.hull_small_steel, 3),
				new ComparableStack(ModItems.mechanism_launcher_2, 1),
				new ComparableStack(ModBlocks.crate_steel, 1),
				new ComparableStack(ModItems.crt_display, 1)
			}, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.turret_tauon, 1), new AStack[] {
				new ComparableStack(ModBlocks.machine_lithium_battery, 1),
				new OreDictStack(OreDictManager.STEEL.ingot(), 16),
				new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 4),
				new ComparableStack(ModItems.motor, 2),
				new ComparableStack(ModItems.circuit_targeting_tier4, 1),
				new ComparableStack(ModItems.motor_desh, 1),
				new OreDictStack(OreDictManager.CU.ingot(), 32),
				new ComparableStack(ModItems.mechanism_special, 1),
				new ComparableStack(ModItems.battery_lithium, 1),
				new ComparableStack(ModItems.crt_display, 1)
			}, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.turret_richard, 1), new AStack[] {
				new ComparableStack(ModBlocks.machine_battery, 1),
				new OreDictStack(OreDictManager.STEEL.ingot(), 16),
				new OreDictStack(OreDictManager.DURA.ingot(), 4),
				new ComparableStack(ModItems.motor, 2),
				new ComparableStack(ModItems.circuit_targeting_tier4, 1),
				new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 2),
				new ComparableStack(ModItems.hull_small_steel, 8),
				new ComparableStack(ModItems.mechanism_launcher_2, 1),
				new ComparableStack(ModBlocks.crate_steel, 1),
				new ComparableStack(ModItems.crt_display, 1)
			}, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.turret_howard, 1), new AStack[] {
				new ComparableStack(ModBlocks.machine_battery, 1),
				new OreDictStack(OreDictManager.STEEL.ingot(), 24),
				new OreDictStack(OreDictManager.DURA.ingot(), 6),
				new ComparableStack(ModItems.motor, 2),
				new ComparableStack(ModItems.motor_desh, 2),
				new ComparableStack(ModItems.circuit_targeting_tier3, 2),
				new ComparableStack(ModItems.pipes_steel, 2),
				new ComparableStack(ModItems.mechanism_rifle_2, 2),
				new ComparableStack(ModBlocks.crate_steel, 1),
				new ComparableStack(ModItems.crt_display, 1)
			}, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.turret_maxwell, 1), new AStack[] {
				new ComparableStack(ModBlocks.machine_lithium_battery, 1),
				new OreDictStack(OreDictManager.STEEL.ingot(), 24),
				new OreDictStack(OreDictManager.DURA.ingot(), 6),
				new ComparableStack(ModItems.motor, 2),
				new ComparableStack(ModItems.circuit_targeting_tier4, 2),
				new ComparableStack(ModItems.pipes_steel, 1),
				new ComparableStack(ModItems.mechanism_special, 3),
				new ComparableStack(ModItems.magnetron, 16),
				new OreDictStack(OreDictManager.ANY_RESISTANTALLOY.ingot(), 8),
				new ComparableStack(ModItems.crt_display, 1)
			}, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.turret_fritz, 1), new AStack[] {
				new ComparableStack(ModBlocks.machine_battery, 1),
				new OreDictStack(OreDictManager.STEEL.ingot(), 16),
				new OreDictStack(OreDictManager.DURA.ingot(), 4),
				new ComparableStack(ModItems.motor, 3),
				new ComparableStack(ModItems.circuit_targeting_tier3, 1),
				new ComparableStack(ModItems.pipes_steel, 1),
				new ComparableStack(ModItems.mechanism_launcher_1, 1),
				new ComparableStack(ModBlocks.barrel_steel, 1),
				new ComparableStack(ModItems.crt_display, 1)
			}, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.turret_arty, 1), new AStack[] {
				new ComparableStack(ModBlocks.machine_battery, 1),
				new OreDictStack(OreDictManager.STEEL.ingot(), 128),
				new OreDictStack(OreDictManager.DURA.ingot(), 32),
				new ComparableStack(ModItems.motor_desh, 5),
				new ComparableStack(ModItems.circuit_targeting_tier4, 1),
				new ComparableStack(ModItems.pipes_steel, 1),
				new ComparableStack(ModItems.mechanism_launcher_2, 3),
				new ComparableStack(ModBlocks.machine_radar, 1),
				new ComparableStack(ModItems.crt_display, 1)
			}, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.turret_himars, 1), new AStack[] {
				new ComparableStack(ModBlocks.machine_battery, 1),
				new OreDictStack(OreDictManager.STEEL.ingot(), 128),
				new OreDictStack(OreDictManager.DURA.ingot(), 64),
				new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 64),
				new ComparableStack(ModItems.motor_desh, 5),
				new ComparableStack(ModItems.circuit_targeting_tier4, 3),
				new ComparableStack(ModItems.mechanism_launcher_2, 5),
				new ComparableStack(ModBlocks.machine_radar, 1),
				new ComparableStack(ModItems.crt_display, 1)
			}, 300);
		
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.ammo_himars, 1, ItemAmmoHIMARS.SMALL), new AStack[] {
				new OreDictStack(OreDictManager.STEEL.plate(), 24),
				new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 12),
				new ComparableStack(ModItems.rocket_fuel, 48),
				new OreDictStack(OreDictManager.ANY_HIGHEXPLOSIVE.ingot(), 48),
				new ComparableStack(ModItems.circuit_copper, 12)
			}, 100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.ammo_himars, 1, ItemAmmoHIMARS.SMALL_HE), new AStack[] {
				new OreDictStack(OreDictManager.STEEL.plate(), 24),
				new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 24),
				new ComparableStack(ModItems.rocket_fuel, 48),
				new OreDictStack(OreDictManager.ANY_PLASTICEXPLOSIVE.ingot(), 18),
				new OreDictStack(OreDictManager.ANY_HIGHEXPLOSIVE.ingot(), 48),
				new ComparableStack(ModItems.circuit_copper, 12)
			}, 100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.ammo_himars, 1, ItemAmmoHIMARS.SMALL_WP), new AStack[] {
				new OreDictStack(OreDictManager.STEEL.plate(), 24),
				new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 24),
				new ComparableStack(ModItems.rocket_fuel, 48),
				new OreDictStack(OreDictManager.P_WHITE.ingot(), 18),
				new OreDictStack(OreDictManager.ANY_HIGHEXPLOSIVE.ingot(), 48),
				new ComparableStack(ModItems.circuit_copper, 12)
			}, 100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.ammo_himars, 1, ItemAmmoHIMARS.SMALL_TB), new AStack[] {
				new OreDictStack(OreDictManager.STEEL.plate(), 24),
				new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 24),
				new ComparableStack(ModItems.rocket_fuel, 48),
				new ComparableStack(ModItems.ball_tatb, 32),
				new OreDictStack(Fluids.KEROSENE_REFORM.getDict(1_000), 12),
				new OreDictStack(Fluids.ACID.getDict(1_000), 12),
				new ComparableStack(ModItems.circuit_copper, 12)
			}, 100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.ammo_himars, 1, ItemAmmoHIMARS.SMALL_MINI_NUKE), new AStack[] {
				new OreDictStack(OreDictManager.STEEL.plate(), 24),
				new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 24),
				new ComparableStack(ModItems.rocket_fuel, 48),
				new ComparableStack(ModItems.ball_tatb, 6),
				new OreDictStack(OreDictManager.PU239.nugget(), 12),
				new OreDictStack(OreDictManager.getReflector(), 12),
				new ComparableStack(ModItems.circuit_copper, 12)
			}, 100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.ammo_himars, 1, ItemAmmoHIMARS.SMALL_LAVA), new AStack[] {
				new OreDictStack(OreDictManager.STEEL.plate(), 24),
				new OreDictStack(OreDictManager.ANY_HARDPLASTIC.ingot(), 12),
				new ComparableStack(ModItems.rocket_fuel, 32),
				new ComparableStack(ModItems.ball_tatb, 4),
				new OreDictStack(OreDictManager.VOLCANIC.gem(), 1),
				new ComparableStack(ModItems.circuit_copper, 6)
			}, 100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.ammo_himars, 1, ItemAmmoHIMARS.LARGE), new AStack[] {
				new OreDictStack(OreDictManager.STEEL.plate(), 24),
				new OreDictStack(OreDictManager.ANY_HARDPLASTIC.ingot(), 12),
				new ComparableStack(ModItems.rocket_fuel, 36),
				new ComparableStack(ModItems.ball_tatb, 16),
				new ComparableStack(ModItems.circuit_gold, 2),
			}, 100);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.ammo_himars, 1, ItemAmmoHIMARS.LARGE_TB), new AStack[] {
				new OreDictStack(OreDictManager.STEEL.plate(), 24),
				new OreDictStack(OreDictManager.ANY_HARDPLASTIC.ingot(), 12),
				new ComparableStack(ModItems.rocket_fuel, 36),
				new ComparableStack(ModItems.ball_tatb, 24),
				new OreDictStack(Fluids.KEROSENE_REFORM.getDict(1_000), 16),
				new OreDictStack(Fluids.ACID.getDict(1_000), 16),
				new ComparableStack(ModItems.circuit_gold, 2),
			}, 100);
		
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_silex, 1), new AStack[] {
				new ComparableStack(Blocks.glass, 12),
				new ComparableStack(ModItems.motor, 2),
				new OreDictStack(OreDictManager.DURA.ingot(), 4),
				!exp ? new OreDictStack(OreDictManager.STEEL.plate528(), 8) : new OreDictStack(OreDictManager.STEEL.heavyComp(), 1),
				new OreDictStack(OreDictManager.DESH.ingot(), 2),
				new ComparableStack(ModItems.tank_steel, 1),
				new ComparableStack(ModItems.pipes_steel, 1),
				new ComparableStack(ModItems.crystal_diamond, 1)
			}, 400);
		AssemblerRecipes.makeRecipe(new ComparableStack(Item.getItemFromBlock(ModBlocks.machine_fel), 1), new AStack[] {
				new ComparableStack(ModBlocks.fusion_conductor, 16),
				new ComparableStack(ModBlocks.machine_lithium_battery, 2),
				new OreDictStack(OreDictManager.STEEL.ingot(), 16),
				!exp ? new OreDictStack(OreDictManager.STEEL.plate528(), 24) : new OreDictStack(OreDictManager.STEEL.heavyComp(), 1),
				new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 8),
				new ComparableStack(ModItems.circuit_red_copper, 4),
				new ComparableStack(ModItems.wire_red_copper, 64),
				new ComparableStack(ModItems.coil_advanced_torus, 16),
				new ComparableStack(ModItems.circuit_gold, 1)
			}, 400);
		
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.rbmk_blank, 1), new AStack[] {
				new ComparableStack(ModBlocks.concrete_asbestos, 4),
				!exp ? new OreDictStack(OreDictManager.STEEL.plate528(), 4) : new OreDictStack(OreDictManager.STEEL.plateCast(), 16),
				new OreDictStack(OreDictManager.CU.ingot(), 4),
				new ComparableStack(ModItems.plate_polymer, 4)
			}, 100);
		
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.multitool_hit, 1), new AStack[] {
				new OreDictStack(OreDictManager.ANY_RESISTANTALLOY.ingot(), 4),
				new OreDictStack(OreDictManager.STEEL.plate(), 4),
				new ComparableStack(ModItems.wire_gold, 12),
				new ComparableStack(ModItems.motor, 4),
				new ComparableStack(ModItems.circuit_tantalium, 16)
			}, 100);
		
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_assemfac, 1), new AStack[] {
				!exp ? new OreDictStack(OreDictManager.STEEL.ingot(), 48) : new OreDictStack(OreDictManager.STEEL.heavyComp(), 2),
				new OreDictStack(OreDictManager.ANY_RESISTANTALLOY.ingot(), 8),
				new OreDictStack(OreDictManager.B.ingot(), 4),
				new OreDictStack(OreDictManager.RUBBER.ingot(), 16),
				new OreDictStack(OreDictManager.KEY_ANYPANE, 64),
				new ComparableStack(ModItems.motor, 18),
				new ComparableStack(ModItems.bolt_tungsten, 12),
				new ComparableStack(ModItems.pipes_steel, 1),
				new ComparableStack(ModItems.circuit_gold, 3)
			}, 400);
		
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_chemfac, 1), new AStack[] {
				!exp ? new OreDictStack(OreDictManager.STEEL.ingot(), 48) : new OreDictStack(OreDictManager.STEEL.heavyComp(), 2),
				new OreDictStack(OreDictManager.ANY_RESISTANTALLOY.ingot(), 8),
				new OreDictStack(OreDictManager.NB.ingot(), 4),
				new OreDictStack(OreDictManager.RUBBER.ingot(), 16),
				new ComparableStack(ModItems.hull_big_steel, 12),
				new ComparableStack(ModItems.tank_steel, 8),
				new ComparableStack(ModItems.motor_desh, 4),
				new ComparableStack(ModItems.coil_tungsten, 24),
				new ComparableStack(ModItems.pipes_steel, 1),
				new ComparableStack(ModItems.circuit_gold, 3)
			}, 400);
		
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.missile_shuttle, 1), new AStack[] {
				new ComparableStack(ModItems.missile_generic, 2),
				new ComparableStack(ModItems.missile_strong, 1),
				new OreDictStack(OreDictManager.KEY_ORANGE, 5),
				new ComparableStack(ModItems.canister_full, 24, Fluids.GASOLINE_LEADED.getID()),
				new OreDictStack(OreDictManager.FIBER.ingot(), 12),
				new ComparableStack(ModItems.circuit_copper, 2),
				new OreDictStack(OreDictManager.ANY_PLASTICEXPLOSIVE.ingot(), 8),
				new OreDictStack(OreDictManager.KEY_ANYPANE, 6),
				new OreDictStack(OreDictManager.STEEL.plate(), 4),
			}, 100);
		
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_difurnace_rtg_off, 1), new AStack[] {
				new ComparableStack(ModBlocks.machine_difurnace_off, 1),
				new ComparableStack(ModItems.rtg_unit, 3),
				new OreDictStack(OreDictManager.DESH.ingot(), 4),
				new OreDictStack(OreDictManager.PB.plate528(), 6),
				new OreDictStack(OreDictManager.getReflector(), 8),
				new OreDictStack(OreDictManager.CU.plate(), 12)
			}, 150);

		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_vacuum_distill, 1), new AStack[] {
				!exp ? new OreDictStack(OreDictManager.STEEL.plateCast(), 16) : new OreDictStack(OreDictManager.STEEL.heavyComp(), 4),
				!exp ? new OreDictStack(OreDictManager.CU.plate528(), 16) : new OreDictStack(OreDictManager.CU.heavyComp(), 4),
				new OreDictStack(OreDictManager.ANY_RESISTANTALLOY.ingot(), 4),
				new ComparableStack(ModItems.sphere_steel, 1),
				new ComparableStack(ModItems.pipes_steel, 1),
				new ComparableStack(ModItems.motor_desh, 3),
				new OreDictStack(OreDictManager.KEY_CIRCUIT_BISMUTH, 1)
			}, 200);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_catalytic_reformer, 1), new AStack[] {
				!exp ? new OreDictStack(OreDictManager.STEEL.plateCast(), 12) : new OreDictStack(OreDictManager.STEEL.heavyComp(), 4),
				!exp ? new OreDictStack(OreDictManager.CU.plate528(), 8) : new OreDictStack(OreDictManager.CU.heavyComp(), 2),
				new OreDictStack(OreDictManager.NB.ingot(), 8),
				new OreDictStack(OreDictManager.ANY_RESISTANTALLOY.ingot(), 4),
				new ComparableStack(ModItems.hull_big_steel, 3),
				new ComparableStack(ModItems.pipes_steel, 1),
				new ComparableStack(ModItems.motor, 1),
				new ComparableStack(ModItems.circuit_red_copper, 3)
			}, 200);

		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_compressor, 1), new AStack[] {
				new OreDictStack(OreDictManager.STEEL.plateCast(), 8),
				new OreDictStack(OreDictManager.CU.plate528(), 4),
				new ComparableStack(ModItems.hull_big_steel, 2),
				new ComparableStack(ModItems.motor, 3),
				new ComparableStack(ModItems.circuit_red_copper, 1)
			}, 200);

		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_electrolyser, 1), new AStack[] {
				!exp ? new OreDictStack(OreDictManager.STEEL.plateCast(), 8) : new OreDictStack(OreDictManager.STEEL.heavyComp(), 2),
				!exp ? new OreDictStack(OreDictManager.CU.plate528(), 16) : new OreDictStack(OreDictManager.CU.heavyComp(), 1),
				new OreDictStack(OreDictManager.RUBBER.ingot(), 8),
				new ComparableStack(ModItems.ingot_firebrick, 16),
				new ComparableStack(ModItems.tank_steel, 3),
				new ComparableStack(ModItems.coil_copper, 16),
				new ComparableStack(ModItems.circuit_gold, 2)
			}, 200);
		
		AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.euphemium_capacitor, 1), new AStack[]
				{
						new OreDictStack(OreDictManager.NB.ingot(), 4),
						new ComparableStack(ModItems.redcoil_capacitor, 1),
						new ComparableStack(ModItems.ingot_euphemium, 4),
						new ComparableStack(ModItems.circuit_tantalium, 6),
						new ComparableStack(ModItems.powder_nitan_mix, 18),
				}, 600);

		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.block_cap_nuka, 1), new AStack[] { new ComparableStack(ModItems.cap_nuka, 128) }, 10);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.block_cap_quantum, 1), new AStack[] { new ComparableStack(ModItems.cap_quantum, 128) }, 10);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.block_cap_sparkle, 1), new AStack[] { new ComparableStack(ModItems.cap_sparkle, 128) }, 10);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.block_cap_rad, 1), new AStack[] { new ComparableStack(ModItems.cap_rad, 128) }, 10);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.block_cap_korl, 1), new AStack[] { new ComparableStack(ModItems.cap_korl, 128) }, 10);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.block_cap_fritz, 1), new AStack[] { new ComparableStack(ModItems.cap_fritz, 128) }, 10);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.block_cap_sunset, 1), new AStack[] { new ComparableStack(ModItems.cap_sunset, 128) }, 10);
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.block_cap_star, 1), new AStack[] { new ComparableStack(ModItems.cap_star, 128) }, 10);

		if(!GeneralConfig.enable528) {
			AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_hephaestus, 1), new AStack[] { new ComparableStack(ModItems.pipes_steel, 1), !exp ? new OreDictStack(OreDictManager.STEEL.ingot(), 24) : new OreDictStack(OreDictManager.STEEL.heavyComp(), 2), !exp ? new OreDictStack(OreDictManager.CU.plate(), 24) : new OreDictStack(OreDictManager.CU.heavyComp(), 2), new OreDictStack(OreDictManager.NB.ingot(), 4), new OreDictStack(OreDictManager.RUBBER.ingot(), 12), new ComparableStack(ModBlocks.glass_quartz, 16) }, 150);
			AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_radgen, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.ingot(), 8), new OreDictStack(OreDictManager.STEEL.plate(), 32), new ComparableStack(ModItems.coil_magnetized_tungsten, 6), new ComparableStack(ModItems.wire_magnetized_tungsten, 24), new ComparableStack(ModItems.circuit_gold, 4), new ComparableStack(ModItems.reactor_core, 3), new OreDictStack(OreDictManager.STAR.ingot(), 1), new OreDictStack("dyeRed", 1), },400);
			AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_reactor_breeding, 1), new AStack[] {new ComparableStack(ModItems.reactor_core, 1), new OreDictStack(OreDictManager.STEEL.ingot(), 12), new OreDictStack(OreDictManager.PB.plate(), 16), new ComparableStack(ModBlocks.reinforced_glass, 4), new OreDictStack(OreDictManager.ASBESTOS.ingot(), 4), new OreDictStack(OreDictManager.ANY_RESISTANTALLOY.ingot(), 4), new ComparableStack(ModItems.crt_display, 1)},150);
			AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.reactor_research, 1), new AStack[] {new OreDictStack(OreDictManager.STEEL.ingot(), 8), new OreDictStack(OreDictManager.ANY_RESISTANTALLOY.ingot(), 4), new ComparableStack(ModItems.motor_desh, 2), new OreDictStack(OreDictManager.B.ingot(), 5), new OreDictStack(OreDictManager.PB.plate(), 8), new ComparableStack(ModItems.crt_display, 3), new ComparableStack(ModItems.circuit_copper, 2), },300);
		
		} else {
			AssemblerRecipes.addTantalium(new ComparableStack(ModBlocks.machine_centrifuge, 1), 5);
			AssemblerRecipes.addTantalium(new ComparableStack(ModBlocks.machine_gascent, 1), 25);
			AssemblerRecipes.addTantalium(new ComparableStack(ModBlocks.machine_crystallizer, 1), 15);
			AssemblerRecipes.addTantalium(new ComparableStack(ModBlocks.machine_large_turbine, 1), 10);
			AssemblerRecipes.addTantalium(new ComparableStack(ModBlocks.machine_chungus, 1), 50);
			AssemblerRecipes.addTantalium(new ComparableStack(ModBlocks.machine_refinery, 1), 3);
			AssemblerRecipes.addTantalium(new ComparableStack(ModBlocks.machine_silex, 1), 15);
			AssemblerRecipes.addTantalium(new ComparableStack(ModBlocks.machine_radar, 1), 20);
			AssemblerRecipes.addTantalium(new ComparableStack(ModBlocks.machine_mining_laser, 1), 30);
			AssemblerRecipes.addTantalium(new ComparableStack(ModBlocks.machine_vacuum_distill, 1), 50);
			AssemblerRecipes.addTantalium(new ComparableStack(ModBlocks.machine_catalytic_reformer, 1), 50);
			
			AssemblerRecipes.addTantalium(new ComparableStack(ModBlocks.turret_chekhov, 1), 3);
			AssemblerRecipes.addTantalium(new ComparableStack(ModBlocks.turret_friendly, 1), 3);
			AssemblerRecipes.addTantalium(new ComparableStack(ModBlocks.turret_jeremy, 1), 3);
			AssemblerRecipes.addTantalium(new ComparableStack(ModBlocks.turret_tauon, 1), 3);
			AssemblerRecipes.addTantalium(new ComparableStack(ModBlocks.turret_richard, 1), 3);
			AssemblerRecipes.addTantalium(new ComparableStack(ModBlocks.turret_howard, 1), 3);
			AssemblerRecipes.addTantalium(new ComparableStack(ModBlocks.turret_maxwell, 1), 3);
			AssemblerRecipes.addTantalium(new ComparableStack(ModBlocks.turret_fritz, 1), 3);
			AssemblerRecipes.addTantalium(new ComparableStack(ModBlocks.launch_pad, 1), 5);
			
			AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_cyclotron, 1), new AStack[] {
					new ComparableStack(ModBlocks.machine_lithium_battery, 3),
					new ComparableStack(ModBlocks.hadron_coil_neodymium, 8),
					new ComparableStack(ModItems.wire_advanced_alloy, 64),
					new OreDictStack(OreDictManager.STEEL.ingot(), 16),
					new OreDictStack(OreDictManager.STEEL.plate528(), 32),
					new OreDictStack(OreDictManager.AL.plate528(), 32),
					new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 24),
					new OreDictStack(OreDictManager.RUBBER.ingot(), 24),
					new ComparableStack(ModItems.board_copper, 8),
					new ComparableStack(ModItems.circuit_red_copper, 8),
					new ComparableStack(ModItems.circuit_gold, 3),
					new ComparableStack(ModItems.circuit_tantalium, 50),
				}, 600);
			
			AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.rbmk_console, 1), new AStack[] {
					new OreDictStack(OreDictManager.STEEL.ingot(), 16),
					new OreDictStack(OreDictManager.AL.plate528(), 32),
					new ComparableStack(ModItems.plate_polymer, 16),
					new ComparableStack(ModItems.circuit_gold, 5),
					new ComparableStack(ModItems.circuit_tantalium, 20),
					new ComparableStack(ModItems.crt_display, 8),
				}, 300);
			
			AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.rbmk_crane_console, 1), new AStack[] {
					new OreDictStack(OreDictManager.STEEL.ingot(), 16),
					new OreDictStack(OreDictManager.AL.plate528(), 8),
					new ComparableStack(ModItems.plate_polymer, 4),
					new ComparableStack(ModItems.circuit_gold, 1),
					new ComparableStack(ModItems.circuit_tantalium, 10),
				}, 300);
			
			AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.hadron_core, 1), new AStack[] {
					new ComparableStack(ModBlocks.hadron_coil_alloy, 24),
					new OreDictStack(OreDictManager.STEEL.ingot(), 8),
					new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 16),
					new OreDictStack(OreDictManager.ANY_RESISTANTALLOY.ingot(), 8),
					new ComparableStack(ModItems.circuit_gold, 5),
					new ComparableStack(ModItems.circuit_schrabidium, 5),
					new ComparableStack(ModItems.circuit_tantalium, 192),
					new ComparableStack(ModItems.crt_display, 1),
				}, 300);
			
			AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.struct_launcher_core, 1), new AStack[] {
					new ComparableStack(ModBlocks.machine_battery, 3),
					new ComparableStack(ModBlocks.steel_scaffold, 10),
					new OreDictStack(OreDictManager.STEEL.ingot(), 16),
					new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 8),
					new ComparableStack(ModItems.circuit_red_copper, 5),
					new ComparableStack(ModItems.circuit_tantalium, 15),
				}, 200);
			
			AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.struct_launcher_core_large, 1), new AStack[] {
					new ComparableStack(ModBlocks.machine_battery, 5),
					new ComparableStack(ModBlocks.steel_scaffold, 10),
					new OreDictStack(OreDictManager.STEEL.ingot(), 24),
					new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 12),
					new ComparableStack(ModItems.circuit_gold, 5),
					new ComparableStack(ModItems.circuit_tantalium, 25),
				}, 200);
			
			AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.struct_soyuz_core, 1), new AStack[] {
					new ComparableStack(ModBlocks.machine_lithium_battery, 5),
					new ComparableStack(ModBlocks.steel_scaffold, 24),
					new OreDictStack(OreDictManager.STEEL.ingot(), 32),
					new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 24),
					new ComparableStack(ModItems.circuit_gold, 5),
					new ComparableStack(ModItems.upgrade_power_3, 3),
					new ComparableStack(ModItems.circuit_tantalium, 100),
				}, 200);
		}
		
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_fracking_tower), new AStack[] {
						new ComparableStack(ModBlocks.steel_scaffold, 40),
						new ComparableStack(ModBlocks.concrete_smooth, 64),
						new ComparableStack(ModItems.drill_titanium),
						new ComparableStack(ModItems.motor_desh, 2),
						!exp ? new ComparableStack(ModItems.plate_desh, 6) : new OreDictStack(OreDictManager.DESH.heavyComp()),
						new OreDictStack(OreDictManager.NB.ingot(), 8),
						new ComparableStack(ModItems.tank_steel, 24),
						new ComparableStack(ModItems.pipes_steel, 2)
				}, 600);
		
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_catalytic_cracker), new AStack[] {
				new ComparableStack(ModBlocks.steel_scaffold, 16),
				!exp ? new ComparableStack(ModItems.hull_big_steel, 4) : new OreDictStack(OreDictManager.STEEL.heavyComp()),
				new ComparableStack(ModItems.tank_steel, 3),
				new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 4),
				new OreDictStack(OreDictManager.NB.ingot(), 2),
				new ComparableStack(ModItems.catalyst_clay, 12),
				}, 300);
		
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_liquefactor), new AStack[] {
				new OreDictStack(OreDictManager.STEEL.ingot(), 8),
				new OreDictStack(OreDictManager.NB.ingot(), 2),
				new OreDictStack(OreDictManager.CU.plate528(), 12),
				new OreDictStack(OreDictManager.ANY_TAR.any(), 8),
				new ComparableStack(ModItems.catalyst_clay, 4),
				new ComparableStack(ModItems.coil_tungsten, 8),
				new ComparableStack(ModItems.tank_steel, 2),
				new ComparableStack(ModItems.inf_water_mk2, 2)
				}, 200);
		
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_solidifier), new AStack[] {
				new OreDictStack(OreDictManager.ANY_CONCRETE.any(), 8),
				new OreDictStack(OreDictManager.NB.ingot(), 2),
				new OreDictStack(OreDictManager.AL.plate528(), 12),
				new OreDictStack(OreDictManager.ANY_PLASTIC.ingot(), 4),
				new ComparableStack(ModItems.hull_big_steel, 3),
				new ComparableStack(ModItems.catalyst_clay, 4),
				new ComparableStack(ModItems.coil_copper, 4),
				new ComparableStack(ModItems.tank_steel, 2)
				}, 200);
		
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.machine_radiolysis), new AStack[] {
				!exp ? new OreDictStack(OreDictManager.STEEL.ingot(), 12) : new OreDictStack(OreDictManager.STEEL.heavyComp()),
				new OreDictStack(OreDictManager.ANY_RESISTANTALLOY.ingot(), 4),
				new OreDictStack(OreDictManager.DURA.ingot(), 10),
				new OreDictStack(OreDictManager.RUBBER.ingot(), 4),
				new OreDictStack(OreDictManager.PB.plate528(), 12),
				new ComparableStack(ModItems.board_copper, 4),
				new ComparableStack(ModItems.thermo_element, 10),
				new ComparableStack(ModItems.wire_red_copper, 8),
				new ComparableStack(ModItems.tank_steel, 3)
			}, 200);
		
		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.transition_seal, 1), new AStack[]{
				new ComparableStack(ModBlocks.cmb_brick_reinforced, 16),
				new OreDictStack(OreDictManager.STEEL.plate(), 64),
				new OreDictStack(OreDictManager.ALLOY.plate(), 40),
				new ComparableStack(ModItems.plate_polymer, 36),
				new OreDictStack(OreDictManager.STEEL.block(), 24),
				new ComparableStack(ModItems.motor_desh, 16),
				new ComparableStack(ModItems.bolt_dura_steel, 12),
				new OreDictStack(OreDictManager.KEY_YELLOW, 4)
			}, 1200);

		AssemblerRecipes.makeRecipe(new ComparableStack(ModBlocks.sliding_blast_door, 1), new AStack[] {
				new OreDictStack(OreDictManager.STEEL.plate(), 16),
				new OreDictStack(OreDictManager.W.ingot(), 8),
				new ComparableStack(ModBlocks.reinforced_glass, 4),
				new ComparableStack(ModItems.plate_polymer, 4),
				new ComparableStack(ModItems.bolt_dura_steel, 8),
				new ComparableStack(ModItems.motor, 2)
		}, 200);
		
		if(Loader.isModLoaded("Mekanism")) {
			
			Block mb = (Block) Block.blockRegistry.getObject("Mekanism:MachineBlock");
			
			if(mb != null) {
	
				AssemblerRecipes.makeRecipe(new ComparableStack(mb, 1, 4), new AStack[] {
						new OreDictStack(OreDictManager.DURA.ingot(), 16),
						new OreDictStack(OreDictManager.DESH.ingot(), 8),
						new OreDictStack(OreDictManager.STEEL.plate(), 48),
						new OreDictStack(OreDictManager.CU.plate(), 24),
						new ComparableStack(ModItems.pipes_steel, 8),
						new ComparableStack(ModItems.circuit_gold, 8),
						new ComparableStack(ModItems.wire_advanced_alloy, 24),
						new ComparableStack(ModBlocks.fusion_conductor, 12),
						new ComparableStack(ModBlocks.machine_lithium_battery, 3),
						new ComparableStack(ModItems.crystal_redstone, 12),
						new ComparableStack(ModItems.crystal_diamond, 8),
						new ComparableStack(ModItems.motor_desh, 16)
					}, 15 * 60 * 20);
			}
		}
		
		for(NTMMaterial mat : Mats.orderedList) {
			if(mat.shapes.contains(MaterialShapes.CASTPLATE) && mat.shapes.contains(MaterialShapes.HEAVY_COMPONENT)) {
				AssemblerRecipes.makeRecipe(new ComparableStack(ModItems.heavy_component, 1, mat.id), new AStack[] { new OreDictStack(MaterialShapes.CASTPLATE.name() + mat.names[0], 256) }, 12_000);
			}
		}
		
		/// HIDDEN ///
		AssemblerRecipes.hidden.put(new ComparableStack(ModBlocks.machine_radgen, 1), new HashSet<Item>() {{ add(ModItems.journal_pip); }});
		AssemblerRecipes.hidden.put(new ComparableStack(ModBlocks.nuke_fstbmb, 1), new HashSet<Item>() {{ add(ModItems.journal_pip); add(ModItems.journal_bj); }});
		AssemblerRecipes.hidden.put(new ComparableStack(ModItems.mp_warhead_10_cloud, 1), new HashSet<Item>() {{ add(ModItems.journal_pip); }});
		AssemblerRecipes.hidden.put(new ComparableStack(ModItems.mp_warhead_10_taint, 1), new HashSet<Item>() {{ add(ModItems.journal_pip); }});
		AssemblerRecipes.hidden.put(new ComparableStack(ModItems.mp_warhead_15_balefire, 1), new HashSet<Item>() {{ add(ModItems.journal_bj); }});
		AssemblerRecipes.hidden.put(new ComparableStack(ModItems.sat_gerald, 1), new HashSet<Item>() {{ add(ModItems.journal_bj); }});
		AssemblerRecipes.hidden.put(new ComparableStack(ModItems.missile_soyuz, 1), new HashSet<Item>() {{ add(ModItems.journal_bj); }});
		AssemblerRecipes.hidden.put(new ComparableStack(ModItems.missile_soyuz_lander, 1), new HashSet<Item>() {{ add(ModItems.journal_bj); }});
	}
	
	public static void makeRecipe(ComparableStack out, AStack[] in, int duration) {
		
		if(out == null || Item.itemRegistry.getNameForObject(out.item) == null) {
			MainRegistry.logger.error("Canceling assembler registration, item was null!");
			return;
		}
		
		AssemblerRecipes.recipes.put(out, in);
		AssemblerRecipes.time.put(out, duration);
	}
	
	public static void addTantalium(ComparableStack out, int amount) {
		
		AStack[] ins = AssemblerRecipes.recipes.get(out);
		
		if(ins != null) {
			
			AStack[] news = new AStack[ins.length + 1];
			
			for(int i = 0; i < ins.length; i++)
				news[i] = ins[i];
			
			news[news.length - 1] = new ComparableStack(ModItems.circuit_tantalium, amount);
			
			AssemblerRecipes.recipes.put(out, news);
		}
	}
	
	/*
	 *  {
	 *    recipes : [
	 *      {
	 *        output : [ "item", "hbm:item.tank_steel", 1, 0 ],
	 *        duration : 100,
	 *        input : [
	 *          [ "dict", "blockSteel", 6 ],
	 *          [ "dict", "plateTitanium", 2 ],
	 *          [ "dict", "dyeGray", 1 ],
	 *        ]
	 *      },
	 *      {
	 *        output : [ "item", "hbm:plate_gold", 2, 0 ],
	 *        duration : 20,
	 *        input : [
	 *          [ "dict", "ingotGold", 3 ],
	 *          [ "item", "hbm:item.wire_gold", 5 ]
	 *        ]
	 *      }
	 *    ]
	 *  }
	 */
	private static void loadJSONRecipes() {
		
		try {
			JsonObject json = AssemblerRecipes.gson.fromJson(new FileReader(AssemblerRecipes.config), JsonObject.class);
			
			JsonElement recipes = json.get("recipes");
			
			if(recipes instanceof JsonArray) {
				
				JsonArray recArray = recipes.getAsJsonArray();
				
				//go through the recipes array
				for(JsonElement recipe : recArray) {
					
					if(recipe.isJsonObject()) {
						
						JsonObject recObj = recipe.getAsJsonObject();
						
						JsonElement input = recObj.get("input");
						JsonElement output = recObj.get("output");
						JsonElement duration = recObj.get("duration");
						
						int time = 100;
						
						if(duration.isJsonPrimitive()) {
							if(duration.getAsJsonPrimitive().isNumber()) {
								time = Math.max(1, duration.getAsJsonPrimitive().getAsInt());
							}
						}
						
						if(!(input instanceof JsonArray)) {
							MainRegistry.logger.error("Error reading recipe, no input found!");
							continue;
						}
						
						if(!(output instanceof JsonArray)) {
							MainRegistry.logger.error("Error reading recipe, no output found!");
							continue;
						}
						
						Object outp = AssemblerRecipes.parseJsonArray(output.getAsJsonArray());
						List<Object> inp = new ArrayList<>();
						
						for(JsonElement in : input.getAsJsonArray()) {
							
							if(in.isJsonArray()) {
								Object i = AssemblerRecipes.parseJsonArray(in.getAsJsonArray());

								if(i instanceof ComparableStack || i instanceof OreDictStack)
									inp.add(i);
							}
						}
						
						if(outp instanceof ComparableStack) {
							AssemblerRecipes.recipes.put((ComparableStack) outp, Arrays.copyOf(inp.toArray(), inp.size(), AStack[].class));
							AssemblerRecipes.time.put((ComparableStack) outp, time);
						}
					}
				}
			}
			
		} catch (Exception e) {
			//shush
		}
	}
	
	private static Object parseJsonArray(JsonArray array) {
		
		boolean dict = false;
		String item = "";
		int stacksize = 1;
		int meta = 0;
		
		if(array.size() < 2)
			return null;
		
		//is index 0 "item" or "dict"?
		if(array.get(0).isJsonPrimitive()) {
			
			if(array.get(0).getAsString().equals("item")) {
				dict = false;
			} else if(array.get(0).getAsString().equals("dict")) {
				dict = true;
			} else {
				
				MainRegistry.logger.error("Error reading recipe, stack array does not have 'item' or 'dict' label!");
				return null;
			}
			
		} else {
			
			MainRegistry.logger.error("Error reading recipe, label is not a valid data type!");
			return null;
		}
		
		//is index 1 a string
		if(array.get(1).isJsonPrimitive()) {
			
			item = array.get(1).getAsString();
			
		} else {
			MainRegistry.logger.error("Error reading recipe, item string is not a valid data type!");
			return null;
		}
		
		//if index 2 exists, eval it as a stacksize
		if(array.size() > 2 && array.get(2).isJsonPrimitive()) {
			
			if(array.get(2).getAsJsonPrimitive().isNumber()) {
				
				stacksize = Math.max(1, array.get(2).getAsJsonPrimitive().getAsNumber().intValue());
				
			} else {
				
				MainRegistry.logger.error("Error reading recipe, stack size is not a valid data type!");
				return null;
			}
		}
		
		//ore dict implementation
		if(dict) {
			
			if(OreDictionary.doesOreNameExist(item)) {
				return new OreDictStack(item, stacksize);
			} else {
				
				MainRegistry.logger.error("Error reading recipe, ore dict name does not exist!");
				return null;
			}
		
		//comparable stack
		} else {
			
			//if index 4 exists, eval it as a meta
			if(array.size() > 3 && array.get(3).isJsonPrimitive()) {
				
				if(array.get(3).getAsJsonPrimitive().isNumber()) {
					
					meta = Math.max(0, array.get(3).getAsJsonPrimitive().getAsNumber().intValue());
					
				} else {
					
					MainRegistry.logger.error("Error reading recipe, metadata is not a valid data type!");
					return null;
				}
			}
			
			Item it = (Item)Item.itemRegistry.getObject(item);
			
			if(it == null) {
				
				MainRegistry.logger.error("Item could not be found!");
				return null;
			}
			
			return new ComparableStack(it, stacksize, meta);
		}
	}
	
	public static void saveTemplateJSON(File dir) {
		
		AssemblerRecipes.template = new File(dir.getAbsolutePath() + File.separatorChar + "_hbmAssembler.json");
		
		try {
			
			JsonWriter writer = new JsonWriter(new FileWriter(AssemblerRecipes.template));
			writer.setIndent("  ");
			
			writer.beginObject();

			writer.name("recipes").beginArray();
			
			for(ComparableStack output : AssemblerRecipes.recipeList) {
				
				writer.beginObject();
				writer.name("output").beginArray();
				writer.setIndent("");
				writer.value("item");
				writer.value(Item.itemRegistry.getNameForObject(output.toStack().getItem()));
				writer.value(output.stacksize);
				if(output.meta > 0)
					writer.value(output.meta);
				writer.endArray();
				writer.setIndent("  ");
				

				writer.name("input").beginArray();
				
				AStack[] inputs = AssemblerRecipes.recipes.get(output);
				for(AStack astack : inputs) {
					
					writer.beginArray();
					writer.setIndent("");
					
					if(astack instanceof ComparableStack) {
						ComparableStack comp = (ComparableStack) astack;
						
						writer.value("item");
						writer.value(Item.itemRegistry.getNameForObject(comp.toStack().getItem()));
						writer.value(comp.stacksize);
						if(comp.meta > 0)
							writer.value(comp.meta);
					}
					
					if(astack instanceof OreDictStack) {
						OreDictStack ore = (OreDictStack) astack;
						
						writer.value("dict");
						writer.value(ore.name);
						writer.value(ore.stacksize);
					}
					
					writer.endArray();
					writer.setIndent("  ");
				}
				
				writer.endArray();
				
				writer.name("duration").value(AssemblerRecipes.time.get(output));
				
				writer.endObject();
			}

			writer.endArray();
			writer.endObject();
			writer.close();
			
		} catch(IOException e) {
			//shush
		}
	}

	public static Map<ItemStack, List<Object>> getRecipes() {
		
		Map<ItemStack, List<Object>> recipes = new HashMap<>();
		
		for(Entry<ComparableStack, AStack[]> entry : AssemblerRecipes.recipes.entrySet()) {
			
			List<Object> value = new ArrayList<>();
			
			for(AStack o : entry.getValue()) {
				
				if(o instanceof ComparableStack) {
					value.add(((ComparableStack)o).toStack());
					
				} else if(o instanceof OreDictStack) {
					value.add(((OreDictStack)o).extractForNEI());
				}
			}
			
			recipes.put(entry.getKey().toStack(), value);
		}
		
		return recipes;
	}
}
