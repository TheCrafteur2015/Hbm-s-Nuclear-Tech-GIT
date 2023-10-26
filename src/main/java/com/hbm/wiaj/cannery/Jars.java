package com.hbm.wiaj.cannery;

import java.util.HashMap;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockNTMFlower.EnumFlowerType;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ItemEnums.EnumPlantType;
import com.hbm.items.ModItems;

public class Jars {

	public static HashMap<ComparableStack, CanneryBase> canneries = new HashMap<>();
	
	public static void initJars() {
		Jars.canneries.put(new ComparableStack(ModBlocks.heater_firebox), new CanneryFirebox());
		Jars.canneries.put(new ComparableStack(ModBlocks.machine_stirling), new CanneryStirling());
		Jars.canneries.put(new ComparableStack(ModBlocks.machine_stirling_steel), new CanneryStirling());
		Jars.canneries.put(new ComparableStack(ModBlocks.machine_gascent), new CanneryCentrifuge());
		Jars.canneries.put(new ComparableStack(ModBlocks.machine_fensu), new CanneryFEnSU());
		Jars.canneries.put(new ComparableStack(ModBlocks.machine_fel), new CannerySILEX());
		Jars.canneries.put(new ComparableStack(ModBlocks.machine_silex), new CannerySILEX());
		Jars.canneries.put(new ComparableStack(ModBlocks.foundry_channel), new CanneryFoundryChannel());
		Jars.canneries.put(new ComparableStack(ModBlocks.machine_crucible), new CanneryCrucible());

		Jars.canneries.put(new ComparableStack(DictFrame.fromOne(ModItems.plant_item, EnumPlantType.MUSTARDWILLOW)), new CanneryWillow());
		Jars.canneries.put(new ComparableStack(DictFrame.fromOne(ModBlocks.plant_flower, EnumFlowerType.CD0)), new CanneryWillow());
	}
}
