package com.hbm.config;

import net.minecraftforge.common.config.Configuration;

public class MobConfig {

	public static boolean enableMaskman = true;
	public static int maskmanDelay = 60 * 60 * 60;
	public static int maskmanChance = 3;
	public static int maskmanMinRad = 50;
	public static boolean maskmanUnderground = true;
	
	public static boolean enableRaids = false;
	public static int raidDelay = 30 * 60 * 60;
	public static int raidChance = 3;
	public static int raidAmount = 15;
	public static int raidDrones = 5;
	public static int raidAttackDelay = 40;
	public static int raidAttackReach = 2;
	public static int raidAttackDistance = 32;

	public static boolean enableElementals = true;
	public static int elementalDelay = 30 * 60 * 60;
	public static int elementalChance = 2;
	public static int elementalAmount = 10;
	public static int elementalDistance = 32;

	public static boolean enableDucks = true;
	public static boolean enableMobGear = true;
	
	public static boolean enableHives = true;
	public static int hiveSpawn = 256;
	public static double scoutThreshold = 0.1;
	public static double tier2Threshold = 1;
	public static double tier3Threshold = 10;
	public static double tier4Threshold = 50;
	public static double tier5Threshold = 100;
	public static double spawnMax = 50;
	public static double targetingThreshold = 1;
	
	
	public static void loadFromConfig(Configuration config) {

		final String CATEGORY = CommonConfig.CATEGORY_MOBS;

		MobConfig.enableMaskman = CommonConfig.createConfigBool(config, CATEGORY, "12.M00_enableMaskman", "Whether mask man should spawn", true);
		MobConfig.maskmanDelay = CommonConfig.createConfigInt(config, CATEGORY, "12.M01_maskmanDelay", "How many world ticks need to pass for a check to be performed", 60 * 60 * 60);
		MobConfig.maskmanChance = CommonConfig.createConfigInt(config, CATEGORY, "12.M02_maskmanChance", "1:x chance to spawn mask man, must be at least 1", 3);
		MobConfig.maskmanMinRad = CommonConfig.createConfigInt(config, CATEGORY, "12.M03_maskmanMinRad", "The amount of radiation needed for mask man to spawn", 50);
		MobConfig.maskmanUnderground = CommonConfig.createConfigBool(config, CATEGORY, "12.M04_maskmanUnderound", "Whether players need to be underground for mask man to spawn", true);

		MobConfig.enableRaids = CommonConfig.createConfigBool(config, CATEGORY, "12.F00_enableFBIRaids", "Whether there should be FBI raids", false);
		MobConfig.raidDelay = CommonConfig.createConfigInt(config, CATEGORY, "12.F01_raidDelay", "How many world ticks need to pass for a check to be performed", 30 * 60 * 60);
		MobConfig.raidChance = CommonConfig.createConfigInt(config, CATEGORY, "12.F02_raidChance", "1:x chance to spawn a raid, must be at least 1", 3);
		MobConfig.raidAmount = CommonConfig.createConfigInt(config, CATEGORY, "12.F03_raidAmount", "How many FBI agents are spawned each raid", 15);
		MobConfig.raidAttackDelay = CommonConfig.createConfigInt(config, CATEGORY, "12.F04_raidAttackDelay", "Time between individual attempts to break machines", 40);
		MobConfig.raidAttackReach = CommonConfig.createConfigInt(config, CATEGORY, "12.F05_raidAttackReach", "How far away machines can be broken", 2);
		MobConfig.raidAttackDistance = CommonConfig.createConfigInt(config, CATEGORY, "12.F06_raidAttackDistance", "How far away agents will spawn from the targeted player", 32);
		MobConfig.raidDrones = CommonConfig.createConfigInt(config, CATEGORY, "12.F07_raidDrones", "How many quadcopter drones are spawned each raid", 5);

		MobConfig.enableElementals = CommonConfig.createConfigBool(config, CATEGORY, "12.E00_enableMeltdownElementals", "Whether there should be radiation elementals", true);
		MobConfig.elementalDelay = CommonConfig.createConfigInt(config, CATEGORY, "12.E01_elementalDelay", "How many world ticks need to pass for a check to be performed", 30 * 60 * 60);
		MobConfig.elementalChance = CommonConfig.createConfigInt(config, CATEGORY, "12.E02_elementalChance", "1:x chance to spawn elementals, must be at least 1", 2);
		MobConfig.elementalAmount = CommonConfig.createConfigInt(config, CATEGORY, "12.E03_elementalAmount", "How many elementals are spawned each raid", 10);
		MobConfig.elementalDistance = CommonConfig.createConfigInt(config, CATEGORY, "12.E04_elementalAttackDistance", "How far away elementals will spawn from the targeted player", 32);
		
		MobConfig.enableDucks = CommonConfig.createConfigBool(config, CATEGORY, "12.D00_enableDucks", "Whether pressing O should allow the player to duck", true);
		MobConfig.enableMobGear = CommonConfig.createConfigBool(config, CATEGORY, "12.D01_enableMobGear", "Whether zombies and skeletons should have additional gear when spawning", true);

		MobConfig.enableHives = CommonConfig.createConfigBool(config, CATEGORY, "12.G00_enableHives", "Whether glyphid hives should spawn", true);
		MobConfig.hiveSpawn = CommonConfig.createConfigInt(config, CATEGORY, "12.G01_hiveSpawn", "The average amount of chunks per hive", 256);
		MobConfig.scoutThreshold = CommonConfig.createConfigDouble(config, CATEGORY, "12.G02_scoutThreshold", "Minimum amount of soot for scouts to spawn", 0.1);
		MobConfig.tier2Threshold = CommonConfig.createConfigDouble(config, CATEGORY, "12.G03_tier2Threshold", "Minimum amount of soot for tier 2 glyphids to spawn", 1);
		MobConfig.tier3Threshold = CommonConfig.createConfigDouble(config, CATEGORY, "12.G04_tier3Threshold", "Minimum amount of soot for tier 3 glyphids to spawn", 10);
		MobConfig.tier4Threshold = CommonConfig.createConfigDouble(config, CATEGORY, "12.G05_tier4Threshold", "Minimum amount of soot for tier 4 glyphids to spawn", 50);
		MobConfig.tier5Threshold = CommonConfig.createConfigDouble(config, CATEGORY, "12.G06_tier5Threshold", "Minimum amount of soot for tier 5 glyphids to spawn", 100);
		MobConfig.spawnMax = CommonConfig.createConfigDouble(config, CATEGORY, "12.G07_spawnMax", "Maximum amount of glyphids being able to exist at once through natural spawning", 50);
		MobConfig.targetingThreshold = CommonConfig.createConfigDouble(config, CATEGORY, "12.G08_targetingThreshold", "Minimum amount of soot required for glyphids' extended targeting range to activate", 1D);
	}
}
