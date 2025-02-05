package com.hbm.inventory;

public class OreNames {
	
	/*
	 * PREFIXES
	 */
	public static final String ANY = "any";
	public static final String NUGGET = "nugget";
	public static final String TINY = "tiny";
	public static final String INGOT = "ingot";
	public static final String DUSTTINY = "dustTiny";
	public static final String DUST = "dust";
	public static final String GEM = "gem";
	public static final String CRYSTAL = "crystal";
	public static final String PLATE = "plate";
	public static final String PLATECAST = "plateTriple"; //cast plates are solid plates made from 3 ingots, turns out that's literally just a GT triple plate
	public static final String PLATEWELDED = "plateSextuple";
	public static final String WIREDENSE = "wireDense";
	public static final String BILLET = "billet";
	public static final String BLOCK = "block";
	public static final String ORE = "ore";
	public static final String ORENETHER = "oreNether";
	public static final String HEAVY_COMPONENT = "componentHeavy";
	
	public static final String[] prefixes = new String[] {
		OreNames.ANY, OreNames.NUGGET, OreNames.TINY, OreNames.INGOT,
		OreNames.DUSTTINY, OreNames.DUST, OreNames.GEM, OreNames.CRYSTAL,
		OreNames.PLATE, OreNames.PLATECAST, OreNames.BILLET, OreNames.BLOCK,
		OreNames.ORE, OreNames.ORENETHER, OreNames.HEAVY_COMPONENT, OreNames.WIREDENSE
	};
}
