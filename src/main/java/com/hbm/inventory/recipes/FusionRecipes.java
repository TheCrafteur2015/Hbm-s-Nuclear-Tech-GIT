package com.hbm.inventory.recipes;

import java.util.HashMap;
import java.util.Map.Entry;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;

public class FusionRecipes {
	
	public static HashMap<FluidType, Integer> chances = new HashMap<>();
	static {
		FusionRecipes.chances.put(Fluids.PLASMA_DT, 1200);
		FusionRecipes.chances.put(Fluids.PLASMA_DH3, 600);
		FusionRecipes.chances.put(Fluids.PLASMA_HD, 1200);
		FusionRecipes.chances.put(Fluids.PLASMA_HT, 1200);
		FusionRecipes.chances.put(Fluids.PLASMA_XM, 1200);
		FusionRecipes.chances.put(Fluids.PLASMA_BF, 150);
	}
	
	public static int getByproductChance(FluidType plasma) {
		Integer chance = FusionRecipes.chances.get(plasma);
		return chance != null ? chance : 0;
	}
	
	public static HashMap<FluidType, Integer> levels = new HashMap<>();
	static {
		FusionRecipes.levels.put(Fluids.PLASMA_DT, 1000);
		FusionRecipes.levels.put(Fluids.PLASMA_DH3, 2000);
		FusionRecipes.levels.put(Fluids.PLASMA_HD, 1000);
		FusionRecipes.levels.put(Fluids.PLASMA_HT, 1000);
		FusionRecipes.levels.put(Fluids.PLASMA_XM, 3000);
		FusionRecipes.levels.put(Fluids.PLASMA_BF, 4000);
	}
	
	public static int getBreedingLevel(FluidType plasma) {
		Integer level = FusionRecipes.levels.get(plasma);
		return level != null ? level : 0;
	}
	
	public static HashMap<FluidType, ItemStack> byproducts = new HashMap<>();
	static {
		FusionRecipes.byproducts.put(Fluids.PLASMA_DT, new ItemStack(ModItems.pellet_charged));
		FusionRecipes.byproducts.put(Fluids.PLASMA_DH3, new ItemStack(ModItems.pellet_charged));
		FusionRecipes.byproducts.put(Fluids.PLASMA_HD, new ItemStack(ModItems.pellet_charged));
		FusionRecipes.byproducts.put(Fluids.PLASMA_HT, new ItemStack(ModItems.pellet_charged));
		FusionRecipes.byproducts.put(Fluids.PLASMA_XM, new ItemStack(ModItems.powder_chlorophyte));
		FusionRecipes.byproducts.put(Fluids.PLASMA_BF, new ItemStack(ModItems.powder_balefire));
	}
	
	public static ItemStack getByproduct(FluidType plasma) {
		ItemStack byproduct = FusionRecipes.byproducts.get(plasma);
		return byproduct != null ? byproduct.copy() : null;
	}
	
	public static HashMap<FluidType, Integer> steamprod = new HashMap<>();
	static {
		FusionRecipes.steamprod.put(Fluids.PLASMA_DT, 30);
		FusionRecipes.steamprod.put(Fluids.PLASMA_DH3, 50);
		FusionRecipes.steamprod.put(Fluids.PLASMA_HD, 20);
		FusionRecipes.steamprod.put(Fluids.PLASMA_HT, 25);
		FusionRecipes.steamprod.put(Fluids.PLASMA_XM, 60);
		FusionRecipes.steamprod.put(Fluids.PLASMA_BF, 160);
	}
	
	public static int getSteamProduction(FluidType plasma) {
		Integer steam = FusionRecipes.steamprod.get(plasma);
		return steam != null ? steam : 0;
	}
	
	public static HashMap<ItemStack, ItemStack> getRecipes() {
		
		HashMap<ItemStack, ItemStack> map = new HashMap<>();
		for(Entry<FluidType, ItemStack> entry : FusionRecipes.byproducts.entrySet()) {
			map.put(new ItemStack(ModItems.fluid_icon, 1, entry.getKey().getID()), entry.getValue().copy());
		}
		return map;
	}

}
