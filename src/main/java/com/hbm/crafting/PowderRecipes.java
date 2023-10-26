package com.hbm.crafting;

import com.hbm.config.GeneralConfig;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemChemicalDye.EnumChemDye;
import com.hbm.main.CraftingManager;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * For recipes mostly involving or resulting in powder
 * @author hbm
 */
public class PowderRecipes {
	
	public static void register() {

		//Explosives
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ballistite, 3), new Object[] { Items.gunpowder, OreDictManager.KNO.dust(), Items.sugar });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ball_dynamite, 2), new Object[] { OreDictManager.KNO.dust(), Items.sugar, Blocks.sand, OreDictManager.KEY_TOOL_CHEMISTRYSET });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ball_tnt, 4), new Object[] { Fluids.AROMATICS.getDict(1000), OreDictManager.KNO.dust(), OreDictManager.KEY_TOOL_CHEMISTRYSET });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ingot_c4, 4), new Object[] { Fluids.UNSATURATEDS.getDict(1000), OreDictManager.KNO.dust(), OreDictManager.KEY_TOOL_CHEMISTRYSET });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_semtex_mix, 3), new Object[] { ModItems.solid_fuel, ModItems.cordite, OreDictManager.KNO.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_semtex_mix, 1), new Object[] { ModItems.solid_fuel, ModItems.ballistite, OreDictManager.KNO.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(Items.clay_ball, 4), new Object[] { OreDictManager.KEY_SAND, ModItems.dust, ModItems.dust, Fluids.WATER.getDict(1_000) });
		CraftingManager.addShapelessAuto(new ItemStack(Items.clay_ball, 4), new Object[] { Blocks.clay }); //clay uncrafting because placing and breaking it isn't worth anyone's time
		
		//Other
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ingot_steel_dusted, 1), new Object[] { OreDictManager.STEEL.ingot(), OreDictManager.COAL.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_bakelite, 2), new Object[] { Fluids.AROMATICS.getDict(1000), Fluids.PETROLEUM.getDict(1000), OreDictManager.KEY_TOOL_CHEMISTRYSET });

		//Gunpowder
		CraftingManager.addShapelessAuto(new ItemStack(Items.gunpowder, 3), new Object[] { OreDictManager.S.dust(), OreDictManager.KNO.dust(), OreDictManager.COAL.gem() });
		CraftingManager.addShapelessAuto(new ItemStack(Items.gunpowder, 3), new Object[] { OreDictManager.S.dust(), OreDictManager.KNO.dust(), new ItemStack(Items.coal, 1, 1) });
		CraftingManager.addShapelessAuto(new ItemStack(Items.gunpowder, 3), new Object[] { OreDictManager.S.dust(), OreDictManager.KNO.dust(), OreDictManager.COAL.gem() });
		CraftingManager.addShapelessAuto(new ItemStack(Items.gunpowder, 3), new Object[] { OreDictManager.S.dust(), OreDictManager.KNO.dust(), new ItemStack(Items.coal, 1, 1) });
		
		//Blends
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_power, 3), new Object[] { "dustGlowstone", OreDictManager.DIAMOND.dust(), OreDictManager.MAGTUNG.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_nitan_mix, 6), new Object[] { OreDictManager.NP237.dust(), OreDictManager.I.dust(), OreDictManager.TH232.dust(), OreDictManager.AT.dust(), OreDictManager.ND.dust(), OreDictManager.CS.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_nitan_mix, 6), new Object[] { OreDictManager.ST.dust(), OreDictManager.CO.dust(), OreDictManager.BR.dust(), OreDictManager.TS.dust(), OreDictManager.NB.dust(), OreDictManager.CE.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_spark_mix, 3), new Object[] { OreDictManager.DESH.dust(), OreDictManager.EUPH.dust(), ModItems.powder_power });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_meteorite, 4), new Object[] { OreDictManager.IRON.dust(), OreDictManager.CU.dust(), OreDictManager.LI.dust(), OreDictManager.NETHERQUARTZ.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_thermite, 4), new Object[] { OreDictManager.IRON.dust(), OreDictManager.IRON.dust(), OreDictManager.IRON.dust(), OreDictManager.AL.dust() });
		
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_desh_mix, 1), new Object[] { OreDictManager.B.dustTiny(), OreDictManager.B.dustTiny(), OreDictManager.LA.dustTiny(), OreDictManager.LA.dustTiny(), OreDictManager.CE.dustTiny(), OreDictManager.CO.dustTiny(), OreDictManager.LI.dustTiny(), OreDictManager.ND.dustTiny(), OreDictManager.NB.dustTiny() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_desh_mix, 9), new Object[] { OreDictManager.B.dust(), OreDictManager.B.dust(), OreDictManager.LA.dust(), OreDictManager.LA.dust(), OreDictManager.CE.dust(), OreDictManager.CO.dust(), OreDictManager.LI.dust(), OreDictManager.ND.dust(), OreDictManager.NB.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_desh_ready, 1), new Object[] { ModItems.powder_desh_mix, ModItems.ingot_mercury, ModItems.ingot_mercury, OreDictManager.COAL.dust() });

		//Metal powders
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_magnetized_tungsten, 1), new Object[] { OreDictManager.W.dust(), OreDictManager.SA326.nugget() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_tcalloy, 1), new Object[] { OreDictManager.STEEL.dust(), OreDictManager.TC99.nugget() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_steel, 1), new Object[] { OreDictManager.IRON.dust(), OreDictManager.COAL.dust() });

		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_flux, 1), new Object[] { new ItemStack(Items.coal, 1, 1), OreDictManager.KEY_SAND });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_flux, 2), new Object[] { OreDictManager.COAL.dust(), OreDictManager.KEY_SAND });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_flux, 4), new Object[] { OreDictManager.F.dust(), OreDictManager.KEY_SAND });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_flux, 8), new Object[] { OreDictManager.PB.dust(), OreDictManager.S.dust(), OreDictManager.KEY_SAND });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_flux, 12), new Object[] { OreDictManager.CA.dust(), OreDictManager.KEY_SAND });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_flux, 16), new Object[] { OreDictManager.BORAX.dust(), OreDictManager.KEY_SAND });

		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_fertilizer, 4), new Object[] { OreDictManager.CA.dust(), OreDictManager.P_RED.dust(), OreDictManager.KNO.dust(), OreDictManager.S.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_fertilizer, 4), new Object[] { OreDictManager.ANY_ASH.any(), OreDictManager.P_RED.dust(), OreDictManager.KNO.dust(), OreDictManager.S.dust() });

		if(GeneralConfig.enableLBSM && GeneralConfig.enableLBSMSimpleCrafting) {
			CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_advanced_alloy, 4), new Object[] { OreDictManager.REDSTONE.dust(), OreDictManager.IRON.dust(), OreDictManager.COAL.dust(), OreDictManager.CU.dust() });
			CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_advanced_alloy, 4), new Object[] { OreDictManager.IRON.dust(), OreDictManager.COAL.dust(), OreDictManager.MINGRADE.dust(), OreDictManager.MINGRADE.dust() });
			CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_advanced_alloy, 4), new Object[] { OreDictManager.REDSTONE.dust(), OreDictManager.CU.dust(), OreDictManager.STEEL.dust(), OreDictManager.STEEL.dust() });
			CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_advanced_alloy, 2), new Object[] { OreDictManager.MINGRADE.dust(), OreDictManager.STEEL.dust() });
			CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_red_copper, 2), new Object[] { OreDictManager.REDSTONE.dust(), OreDictManager.CU.dust() });
			CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_dura_steel, 2), new Object[] { OreDictManager.STEEL.dust(), OreDictManager.W.dust() });
			CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_dura_steel, 2), new Object[] { OreDictManager.STEEL.dust(), OreDictManager.CO.dust() });
			CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_dura_steel, 4), new Object[] { OreDictManager.IRON.dust(), OreDictManager.COAL.dust(), OreDictManager.W.dust(), OreDictManager.W.dust() });
			CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_dura_steel, 4), new Object[] { OreDictManager.IRON.dust(), OreDictManager.COAL.dust(), OreDictManager.CO.dust(), OreDictManager.CO.dust() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.ingot_firebrick, 4), new Object[] { "BN", "NB", 'B', Items.brick, 'N', Items.netherbrick });
		}
		
		//Unleash the colores
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.GRAY, 2),			new Object[] { DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.BLACK),		DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.WHITE) });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.SILVER, 2),		new Object[] { DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.GRAY),		DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.WHITE) });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.ORANGE, 2),		new Object[] { DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.RED),		DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.YELLOW) });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.LIME, 2),			new Object[] { DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.GREEN),		DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.WHITE) });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.CYAN, 2),			new Object[] { DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.BLUE),		DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.GREEN) });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.PURPLE, 2),		new Object[] { DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.RED),		DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.BLUE) });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.BROWN, 2),		new Object[] { DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.ORANGE),	DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.BLACK) });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.MAGENTA, 2),		new Object[] { DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.PINK),		DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.PURPLE) });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.LIGHTBLUE, 2),	new Object[] { DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.BLUE),		DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.WHITE) });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.PINK, 2),			new Object[] { DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.RED),		DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.WHITE) });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.GREEN, 2),		new Object[] { DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.BLUE),		DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.YELLOW) });
		
		for(int i = 0; i < 15; i++) CraftingManager.addShapelessAuto(new ItemStack(ModItems.crayon, 4, i), new Object[] { new ItemStack(ModItems.chemical_dye, 1, i), OreDictManager.ANY_TAR.any() });

	}
}
