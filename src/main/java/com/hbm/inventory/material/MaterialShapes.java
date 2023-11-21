package com.hbm.inventory.material;

import java.util.ArrayList;
import java.util.List;

import com.hbm.util.Compat;

public class MaterialShapes {
	
	public static final List<MaterialShapes> allShapes = new ArrayList<>();
	
	public static final MaterialShapes QUANTUM = new MaterialShapes(1); // 1/72 of an ingot, allows the ingot to be divisible through 2, 4, 6, 8, 9, 12, 24 and 36
	public static final MaterialShapes NUGGET = new MaterialShapes(8, "nugget");
	public static final MaterialShapes DUSTTINY = new MaterialShapes(MaterialShapes.NUGGET.quantity, "dustTiny");
	public static final MaterialShapes WIRE = new MaterialShapes(9);
	public static final MaterialShapes BILLET = new MaterialShapes(MaterialShapes.NUGGET.quantity * 6, "billet");
	public static final MaterialShapes INGOT = new MaterialShapes(MaterialShapes.NUGGET.quantity * 9, "ingot");
	public static final MaterialShapes GEM = new MaterialShapes(MaterialShapes.INGOT.quantity, "gem");
	public static final MaterialShapes CRYSTAL = new MaterialShapes(MaterialShapes.INGOT.quantity, "crystal");
	public static final MaterialShapes DUST = new MaterialShapes(MaterialShapes.INGOT.quantity, "dust");
	public static final MaterialShapes PLATE = new MaterialShapes(MaterialShapes.INGOT.quantity, "plate");
	public static final MaterialShapes CASTPLATE = new MaterialShapes(MaterialShapes.INGOT.quantity * 3, "plateTriple");
	public static final MaterialShapes WELDEDPLATE = new MaterialShapes(MaterialShapes.INGOT.quantity * 6, "plateSextuple");
	public static final MaterialShapes DENSEWIRE = new MaterialShapes(MaterialShapes.INGOT.quantity, "wireDense");
	public static final MaterialShapes QUART = new MaterialShapes(162);
	public static final MaterialShapes BLOCK = new MaterialShapes(MaterialShapes.INGOT.quantity * 9, "block");
	public static final MaterialShapes HEAVY_COMPONENT = new MaterialShapes(MaterialShapes.CASTPLATE.quantity * 256, "componentHeavy");
	
	public static void registerCompatShapes() {

		if(Compat.isModLoaded(Compat.MOD_GT6)) {
			new MaterialShapes(MaterialShapes.BLOCK.q(1), "blockDust");
			new MaterialShapes(MaterialShapes.BLOCK.q(1), "blockGem");
			new MaterialShapes(MaterialShapes.BLOCK.q(1), "blockIngot");
			new MaterialShapes(MaterialShapes.BLOCK.q(1), "blockSolid");
			new MaterialShapes(MaterialShapes.INGOT.q(9, 8), "crushed");
			new MaterialShapes(MaterialShapes.INGOT.q(9, 72), "crushedTiny");
			new MaterialShapes(MaterialShapes.INGOT.q(10, 8), "crushedPurified");
			new MaterialShapes(MaterialShapes.INGOT.q(10, 72), "crushedPurifiedTiny");
			new MaterialShapes(MaterialShapes.INGOT.q(11, 8), "crushedCentrifuged");
			new MaterialShapes(MaterialShapes.INGOT.q(11, 72), "crushedCentrifugedTiny");
			new MaterialShapes(MaterialShapes.INGOT.q(1, 4), "dustSmall");
			new MaterialShapes(MaterialShapes.INGOT.q(1, 72), "dustDiv72");
			new MaterialShapes(MaterialShapes.INGOT.q(10, 9), "dustImpure");
			new MaterialShapes(MaterialShapes.INGOT.q(11, 9), "dustPure");
			new MaterialShapes(MaterialShapes.INGOT.q(12, 9), "dustRefined");
		}
	}
	
	private int quantity;
	public final String[] prefixes;
	
	private MaterialShapes(int quantity, String... prefixes) {
		this.quantity = quantity;
		this.prefixes = prefixes;
		
		for(String prefix : prefixes) {
			Mats.prefixByName.put(prefix, this);
		}
		
		MaterialShapes.allShapes.add(this);
	}
	
	public int q(int amount) {
		return this.quantity * amount;
	}
	
	public int q(int unitsUsed, int itemsProduced) { //eg rails: INOGT.q(6, 16) since the recipe uses 6 iron ingots producing 16 individual rail blocks
		return this.quantity * unitsUsed / itemsProduced;
	}

	public String name() {
		return (this.prefixes != null && this.prefixes.length > 0) ? this.prefixes[0] : "unknown";
	}
}
