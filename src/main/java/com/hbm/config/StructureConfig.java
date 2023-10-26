package com.hbm.config;

import java.util.Locale;

import com.hbm.main.MainRegistry;

import net.minecraftforge.common.config.Configuration;

public class StructureConfig {
	
	public static boolean enableStructures = true;
	
	public static int structureMinChunks = 8;
	public static int structureMaxChunks = 24;
	
	public static double lootAmountFactor = 1D;
	
	public static void loadFromConfig(Configuration config) {
		
		final String CATEGORY_STRUCTURES = CommonConfig.CATEGORY_STRUCTURES;
		StructureConfig.enableStructures = CommonConfig.createConfigBool(config, CATEGORY_STRUCTURES, "5.00_enableStructures", "Switch for whether structures using the MapGenStructure system spawn.", true);
		
		StructureConfig.structureMinChunks = CommonConfig.createConfigInt(config, CATEGORY_STRUCTURES, "5.01_structureMinChunks", "Minimum non-zero distance between structures in chunks (Settings lower than 8 may be problematic).", 8);
		StructureConfig.structureMaxChunks = CommonConfig.createConfigInt(config, CATEGORY_STRUCTURES, "5.02_structureMaxChunks", "Maximum non-zero distance between structures in chunks.", 24);
		
		StructureConfig.lootAmountFactor = CommonConfig.createConfigDouble(config, CATEGORY_STRUCTURES, "5.03_lootAmountFactor", "General factor for loot spawns. Applies to spawned IInventories, not loot blocks.", 1D);
		
		StructureConfig.structureMinChunks = CommonConfig.setDef(StructureConfig.structureMinChunks, 8);
		StructureConfig.structureMaxChunks = CommonConfig.setDef(StructureConfig.structureMaxChunks, 24);
		
		if(StructureConfig.structureMinChunks > StructureConfig.structureMaxChunks) {
			MainRegistry.logger.error("Fatal error config: Minimum value has been set higher than the maximum value!");
			MainRegistry.logger.error(String.format(Locale.US, "Errored values will default back to %1$d and %2$d respectively, PLEASE REVIEW CONFIGURATION DESCRIPTION BEFORE MEDDLING WITH VALUES!", 8, 24));
			StructureConfig.structureMinChunks = 8;
			StructureConfig.structureMaxChunks = 24;
		}
			
	}
}
