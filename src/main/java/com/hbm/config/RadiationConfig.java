package com.hbm.config;

import net.minecraftforge.common.config.Configuration;

public class RadiationConfig {

	public static int fogRad = 100;
	public static int fogCh = 20;
	public static double hellRad = 0.1;
	public static int worldRad = 10;
	public static int worldRadThreshold = 20;
	public static boolean worldRadEffects = true;
	public static boolean cleanupDeadDirt = false;

	public static boolean enableContamination = true;
	public static boolean enableChunkRads = true;

	public static boolean disableAsbestos = false;
	public static boolean disableCoal = false;
	public static boolean disableHot = false;
	public static boolean disableExplosive = false;
	public static boolean disableHydro = false;
	public static boolean disableBlinding = false;
	public static boolean disableFibrosis = false;

	public static boolean enablePollution = true;
	public static boolean enableLeadFromBlocks = true;
	public static boolean enableLeadPoisoning = true;
	public static boolean enableSootFog = true;
	public static boolean enablePoison = true;
	public static double buffMobThreshold = 15D;
	public static double sootFogThreshold = 35D;
	public static double sootFogDivisor = 120D;
	
	public static void loadFromConfig(Configuration config) {

		final String CATEGORY_NUKE = CommonConfig.CATEGORY_RADIATION;

		RadiationConfig.fogRad = CommonConfig.createConfigInt(config, CATEGORY_NUKE, "FOG_00_threshold", "Radiation in RADs required for fog to spawn", 100);
		RadiationConfig.fogCh = CommonConfig.createConfigInt(config, CATEGORY_NUKE, "FOG_01_threshold", "1:n chance of fog spawning every second", 20);
		RadiationConfig.hellRad = CommonConfig.createConfigDouble(config, CATEGORY_NUKE, "AMBIENT_00_nether", "RAD/s in the nether", 0.1D);
		RadiationConfig.worldRadEffects = CommonConfig.createConfigBool(config, CATEGORY_NUKE, "RADWORLD_00_toggle", "Whether high radiation levels should perform changes in the world", true);
		RadiationConfig.worldRad = CommonConfig.createConfigInt(config, CATEGORY_NUKE, "RADWORLD_01_amount", "How many block operations radiation can perform per tick", 10);
		RadiationConfig.worldRadThreshold = CommonConfig.createConfigInt(config, CATEGORY_NUKE, "RADWORLD_02_minimum", "The least amount of RADs required for block modification to happen", 20);
		RadiationConfig.cleanupDeadDirt = CommonConfig.createConfigBool(config, CATEGORY_NUKE, "RADWORLD_03_regrow", "Whether dead grass and mycelium should decay into dirt", false);

		RadiationConfig.enableContamination = CommonConfig.createConfigBool(config, CATEGORY_NUKE, "RADIATION_00_enableContamination", "Toggles player contamination (and negative effects from radiation poisoning)", true);
		RadiationConfig.enableChunkRads = CommonConfig.createConfigBool(config, CATEGORY_NUKE, "RADIATION_01_enableChunkRads", "Toggles the world radiation system (chunk radiation only, some blocks use an AoE!)", true);
		
		RadiationConfig.fogCh = CommonConfig.setDef(RadiationConfig.fogCh, 20);

		final String CATEGORY_HAZ = CommonConfig.CATEGORY_HAZARD;

		RadiationConfig.disableAsbestos = CommonConfig.createConfigBool(config, CATEGORY_HAZ, "HAZ_00_disableAsbestos", "When turned off, all asbestos hazards are disabled", false);
		RadiationConfig.disableCoal = CommonConfig.createConfigBool(config, CATEGORY_HAZ, "HAZ_01_disableCoaldust", "When turned off, all coal dust hazards are disabled", false);
		RadiationConfig.disableHot = CommonConfig.createConfigBool(config, CATEGORY_HAZ, "HAZ_02_disableHot", "When turned off, all hot hazards are disabled", false);
		RadiationConfig.disableExplosive = CommonConfig.createConfigBool(config, CATEGORY_HAZ, "HAZ_03_disableExplosive", "When turned off, all explosive hazards are disabled", false);
		RadiationConfig.disableHydro = CommonConfig.createConfigBool(config, CATEGORY_HAZ, "HAZ_04_disableHydroactive", "When turned off, all hydroactive hazards are disabled", false);
		RadiationConfig.disableBlinding = CommonConfig.createConfigBool(config, CATEGORY_HAZ, "HAZ_05_disableBlinding", "When turned off, all blinding hazards are disabled", false);
		RadiationConfig.disableFibrosis = CommonConfig.createConfigBool(config, CATEGORY_HAZ, "HAZ_06_disableFibrosis", "When turned off, all fibrosis hazards are disabled", false);

		final String CATEGORY_POL = CommonConfig.CATEGORY_POLLUTION;
		RadiationConfig.enablePollution = CommonConfig.createConfigBool(config, CATEGORY_POL, "POL_00_enablePollution", "If disabled, none of the polltuion related things will work", true);
		RadiationConfig.enableLeadFromBlocks = CommonConfig.createConfigBool(config, CATEGORY_POL, "POL_01_enableLeadFromBlocks", "Whether breaking blocks in heavy metal polluted areas will poison the player", true);
		RadiationConfig.enableLeadPoisoning = CommonConfig.createConfigBool(config, CATEGORY_POL, "POL_02_enableLeadPoisoning", "Whether being in a heavy metal polluted area will poison the player", true);
		RadiationConfig.enableSootFog = CommonConfig.createConfigBool(config, CATEGORY_POL, "POL_03_enableSootFog", "Whether smog should be visible", true);
		RadiationConfig.enablePoison = CommonConfig.createConfigBool(config, CATEGORY_POL, "POL_04_enablePoison", "Whether being in a poisoned area will affect the player", true);
		RadiationConfig.buffMobThreshold = CommonConfig.createConfigDouble(config, CATEGORY_POL, "POL_05_buffMobThreshold", "The amount of soot required to buff naturally spawning mobs", 15D);
		RadiationConfig.sootFogThreshold = CommonConfig.createConfigDouble(config, CATEGORY_POL, "POL_06_sootFogThreshold", "How much soot is required for smog to become visible", 35D);
		RadiationConfig.sootFogDivisor = CommonConfig.createConfigDouble(config, CATEGORY_POL, "POL_07_sootFogDivisor", "The divisor for smog, higher numbers will require more soot for the same smog density", 120D);
	}
}
