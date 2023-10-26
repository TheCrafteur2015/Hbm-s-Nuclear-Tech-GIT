package com.hbm.config;

import net.minecraftforge.common.config.Configuration;

public class ToolConfig {

	public static int recursionDepth = 500;
	public static boolean recursiveStone = true;
	public static boolean recursiveNetherrack = true;

	public static boolean abilityHammer = true;
	public static boolean abilityVein = true;
	public static boolean abilityLuck = true;
	public static boolean abilitySilk = true;
	public static boolean abilityFurnace = true;
	public static boolean abilityShredder = true;
	public static boolean abilityCentrifuge = true;
	public static boolean abilityCrystallizer = true;
	public static boolean abilityMercury = true;
	public static boolean abilityExplosion = true;
	
	public static void loadFromConfig(Configuration config) {

		final String CATEGORY_TOOLS = CommonConfig.CATEGORY_TOOLS;
		ToolConfig.recursionDepth = CommonConfig.createConfigInt(config, CATEGORY_TOOLS, "11.00_recursionDepth", "Limits veinminer's recursive function. Usually not an issue, unless you're using bukkit which is especially sensitive for some reason.", 1000);
		ToolConfig.recursiveStone = CommonConfig.createConfigBool(config, CATEGORY_TOOLS, "11.01_recursionStone", "Determines whether veinminer can break stone", false);
		ToolConfig.recursiveNetherrack = CommonConfig.createConfigBool(config, CATEGORY_TOOLS, "11.02_recursionNetherrack", "Determines whether veinminer can break netherrack", false);

		ToolConfig.abilityHammer = config.get(CATEGORY_TOOLS, "11.03_hammerAbility", true, "Allows AoE ability").getBoolean(true);
		ToolConfig.abilityVein = config.get(CATEGORY_TOOLS, "11.04_abilityVein", true, "Allows veinminer ability").getBoolean(true);
		ToolConfig.abilityLuck = config.get(CATEGORY_TOOLS, "11.05_abilityLuck", true, "Allow luck (fortune) ability").getBoolean(true);
		ToolConfig.abilitySilk = config.get(CATEGORY_TOOLS, "11.06_abilitySilk", true, "Allow silk touch ability").getBoolean(true);
		ToolConfig.abilityFurnace = config.get(CATEGORY_TOOLS, "11.07_abilityFurnace", true, "Allow auto-smelter ability").getBoolean(true);
		ToolConfig.abilityShredder = config.get(CATEGORY_TOOLS, "11.08_abilityShredder", true, "Allow auto-shredder ability").getBoolean(true);
		ToolConfig.abilityCentrifuge = config.get(CATEGORY_TOOLS, "11.09_abilityCentrifuge", true, "Allow auto-centrifuge ability").getBoolean(true);
		ToolConfig.abilityCrystallizer = config.get(CATEGORY_TOOLS, "11.10_abilityCrystallizer", true, "Allow auto-crystallizer ability").getBoolean(true);
		ToolConfig.abilityMercury = config.get(CATEGORY_TOOLS, "11.11_abilityMercury", true, "Allow mercury touch ability (digging redstone gives mercury)").getBoolean(true);
		ToolConfig.abilityExplosion = config.get(CATEGORY_TOOLS, "11.12_abilityExplosion", true, "Allow explosion ability").getBoolean(true);
	}
}
