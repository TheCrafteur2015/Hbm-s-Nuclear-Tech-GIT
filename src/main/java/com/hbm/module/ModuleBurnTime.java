package com.hbm.module;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.handler.FuelHandler;
import com.hbm.items.ModItems;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.util.ItemStackUtil;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

/**
 * A simple module for determining the burn time of a stack with added options to define bonuses
 * @author hbm
 *
 */
public class ModuleBurnTime {

	private static final int modLog = 0;
	private static final int modWood = 1;
	private static final int modCoal = 2;
	private static final int modLignite = 3;
	private static final int modCoke = 4;
	private static final int modSolid = 5;
	private static final int modRocket = 6;
	private static final int modBalefire = 7;

	private double[] modTime = new double[8];
	private double[] modHeat = new double[8];
	
	public ModuleBurnTime() {
		for(int i = 0; i < this.modTime.length; i++) {
			this.modTime[i] = 1.0D;
			this.modHeat[i] = 1.0D;
		}
	}

	public void readIfPresent(JsonObject obj) {
		this.modTime[ModuleBurnTime.modLog] = IConfigurableMachine.grab(obj, "D:timeLog", this.modTime[ModuleBurnTime.modLog]);
		this.modTime[ModuleBurnTime.modWood] = IConfigurableMachine.grab(obj, "D:timeWood", this.modTime[ModuleBurnTime.modWood]);
		this.modTime[ModuleBurnTime.modCoal] = IConfigurableMachine.grab(obj, "D:timeCoal", this.modTime[ModuleBurnTime.modCoal]);
		this.modTime[ModuleBurnTime.modLignite] = IConfigurableMachine.grab(obj, "D:timeLignite", this.modTime[ModuleBurnTime.modLignite]);
		this.modTime[ModuleBurnTime.modCoke] = IConfigurableMachine.grab(obj, "D:timeCoke", this.modTime[ModuleBurnTime.modCoke]);
		this.modTime[ModuleBurnTime.modSolid] = IConfigurableMachine.grab(obj, "D:timeSolid", this.modTime[ModuleBurnTime.modSolid]);
		this.modTime[ModuleBurnTime.modRocket] = IConfigurableMachine.grab(obj, "D:timeRocket", this.modTime[ModuleBurnTime.modRocket]);
		this.modTime[ModuleBurnTime.modBalefire] = IConfigurableMachine.grab(obj, "D:timeBalefire", this.modTime[ModuleBurnTime.modBalefire]);

		this.modHeat[ModuleBurnTime.modLog] = IConfigurableMachine.grab(obj, "D:heatLog", this.modHeat[ModuleBurnTime.modLog]);
		this.modHeat[ModuleBurnTime.modWood] = IConfigurableMachine.grab(obj, "D:heatWood", this.modHeat[ModuleBurnTime.modWood]);
		this.modHeat[ModuleBurnTime.modCoal] = IConfigurableMachine.grab(obj, "D:heatCoal", this.modHeat[ModuleBurnTime.modCoal]);
		this.modHeat[ModuleBurnTime.modLignite] = IConfigurableMachine.grab(obj, "D:heatLignite", this.modHeat[ModuleBurnTime.modLignite]);
		this.modHeat[ModuleBurnTime.modCoke] = IConfigurableMachine.grab(obj, "D:heatCoke", this.modHeat[ModuleBurnTime.modCoke]);
		this.modHeat[ModuleBurnTime.modSolid] = IConfigurableMachine.grab(obj, "D:heatSolid", this.modHeat[ModuleBurnTime.modSolid]);
		this.modHeat[ModuleBurnTime.modRocket] = IConfigurableMachine.grab(obj, "D:heatRocket", this.modHeat[ModuleBurnTime.modRocket]);
		this.modHeat[ModuleBurnTime.modBalefire] = IConfigurableMachine.grab(obj, "D:heatBalefie", this.modHeat[ModuleBurnTime.modBalefire]);
	}

	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("D:timeLog").value(this.modTime[ModuleBurnTime.modLog]);
		writer.name("D:timeWood").value(this.modTime[ModuleBurnTime.modWood]);
		writer.name("D:timeCoal").value(this.modTime[ModuleBurnTime.modCoal]);
		writer.name("D:timeLignite").value(this.modTime[ModuleBurnTime.modLignite]);
		writer.name("D:timeCoke").value(this.modTime[ModuleBurnTime.modCoke]);
		writer.name("D:timeSolid").value(this.modTime[ModuleBurnTime.modSolid]);
		writer.name("D:timeRocket").value(this.modTime[ModuleBurnTime.modRocket]);
		writer.name("D:timeBalefire").value(this.modTime[ModuleBurnTime.modBalefire]);
		writer.name("D:heatLog").value(this.modHeat[ModuleBurnTime.modLog]);
		writer.name("D:heatWood").value(this.modHeat[ModuleBurnTime.modWood]);
		writer.name("D:heatCoal").value(this.modHeat[ModuleBurnTime.modCoal]);
		writer.name("D:heatLignite").value(this.modHeat[ModuleBurnTime.modLignite]);
		writer.name("D:heatCoke").value(this.modHeat[ModuleBurnTime.modCoke]);
		writer.name("D:heatSolid").value(this.modHeat[ModuleBurnTime.modSolid]);
		writer.name("D:heatRocket").value(this.modHeat[ModuleBurnTime.modRocket]);
		writer.name("D:heatBalefie").value(this.modHeat[ModuleBurnTime.modBalefire]);
	}
	
	public int getBurnTime(ItemStack stack) {
		//int fuel = TileEntityFurnace.getItemBurnTime(stack);
		int fuel = FuelHandler.getBurnTimeFromCache(stack);
		
		if(fuel == 0)
			return 0;
		
		return (int) (fuel * getMod(stack, this.modTime));
	}
	
	public int getBurnHeat(int base, ItemStack stack) {
		return (int) (base * getMod(stack, this.modHeat));
	}
	
	public double getMod(ItemStack stack, double[] mod) {
		
		if(stack == null)
			return 0;

		if((stack.getItem() == ModItems.solid_fuel) || (stack.getItem() == ModItems.solid_fuel_presto) || (stack.getItem() == ModItems.solid_fuel_presto_triplet))		return mod[ModuleBurnTime.modSolid];

		if(stack.getItem() == ModItems.solid_fuel_bf)					return mod[ModuleBurnTime.modBalefire];
		if(stack.getItem() == ModItems.solid_fuel_presto_bf) 			return mod[ModuleBurnTime.modBalefire];
		if(stack.getItem() == ModItems.solid_fuel_presto_triplet_bf)	return mod[ModuleBurnTime.modBalefire];
		
		if(stack.getItem() == ModItems.rocket_fuel)						return mod[ModuleBurnTime.modRocket];
		
		List<String> names = ItemStackUtil.getOreDictNames(stack);
		
		for(String name : names) {
			if(name.contains("Coke"))		return mod[ModuleBurnTime.modCoke];
			if(name.contains("Coal"))		return mod[ModuleBurnTime.modCoal];
			if(name.contains("Lignite"))	return mod[ModuleBurnTime.modLignite];
			if(name.startsWith("log"))		return mod[ModuleBurnTime.modLog];
			if(name.contains("Wood"))		return mod[ModuleBurnTime.modWood];
		}
		
		return 1;
	}
	
	public List<String> getDesc() {
		List<String> desc = new ArrayList<>();
		desc.addAll(getTimeDesc());
		desc.addAll(getHeatDesc());
		return desc;
	}
	
	public List<String> getTimeDesc() {
		List<String> list = new ArrayList<>();

		list.add(EnumChatFormatting.GOLD + "Burn time bonuses:");
		
		addIf(list, "Logs", this.modTime[ModuleBurnTime.modLog]);
		addIf(list, "Wood", this.modTime[ModuleBurnTime.modWood]);
		addIf(list, "Coal", this.modTime[ModuleBurnTime.modCoal]);
		addIf(list, "Lignite", this.modTime[ModuleBurnTime.modLignite]);
		addIf(list, "Coke", this.modTime[ModuleBurnTime.modCoke]);
		addIf(list, "Solid Fuel", this.modTime[ModuleBurnTime.modSolid]);
		addIf(list, "Rocket Fuel", this.modTime[ModuleBurnTime.modRocket]);
		addIf(list, "Balefire", this.modTime[ModuleBurnTime.modBalefire]);
		
		if(list.size() == 1)
			list.clear();
		
		return list;
	}
	
	public List<String> getHeatDesc() {
		List<String> list = new ArrayList<>();

		list.add(EnumChatFormatting.RED + "Burn heat bonuses:");
		
		addIf(list, "Logs", this.modHeat[ModuleBurnTime.modLog]);
		addIf(list, "Wood", this.modHeat[ModuleBurnTime.modWood]);
		addIf(list, "Coal", this.modHeat[ModuleBurnTime.modCoal]);
		addIf(list, "Lignite", this.modHeat[ModuleBurnTime.modLignite]);
		addIf(list, "Coke", this.modHeat[ModuleBurnTime.modCoke]);
		addIf(list, "Solid Fuel", this.modHeat[ModuleBurnTime.modSolid]);
		addIf(list, "Rocket Fuel", this.modHeat[ModuleBurnTime.modRocket]);
		addIf(list, "Balefire", this.modHeat[ModuleBurnTime.modBalefire]);
		
		if(list.size() == 1)
			list.clear();
		
		return list;
	}
	
	private void addIf(List<String> list, String name, double mod) {
		
		if(mod != 1.0D)
			list.add(EnumChatFormatting.YELLOW + "- " + name + ": " + getPercent(mod));
	}
	
	private String getPercent(double mod) {
		mod -= 1D;
		String num = ((int) (mod * 100)) + "%";
		
		if(mod < 0)
			num = EnumChatFormatting.RED + num;
		else
			num = EnumChatFormatting.GREEN + "+" + num;
		
		return num;
	}
	
	public ModuleBurnTime setLogTimeMod(double mod) { this.modTime[ModuleBurnTime.modLog] = mod; return this; }
	public ModuleBurnTime setWoodTimeMod(double mod) { this.modTime[ModuleBurnTime.modWood] = mod; return this; }
	public ModuleBurnTime setCoalTimeMod(double mod) { this.modTime[ModuleBurnTime.modCoal] = mod; return this; }
	public ModuleBurnTime setLigniteTimeMod(double mod) { this.modTime[ModuleBurnTime.modLignite] = mod; return this; }
	public ModuleBurnTime setCokeTimeMod(double mod) { this.modTime[ModuleBurnTime.modCoke] = mod; return this; }
	public ModuleBurnTime setSolidTimeMod(double mod) { this.modTime[ModuleBurnTime.modSolid] = mod; return this; }
	public ModuleBurnTime setRocketTimeMod(double mod) { this.modTime[ModuleBurnTime.modRocket] = mod; return this; }
	public ModuleBurnTime setBalefireTimeMod(double mod) { this.modTime[ModuleBurnTime.modBalefire] = mod; return this; }
	
	public ModuleBurnTime setLogHeatMod(double mod) { this.modHeat[ModuleBurnTime.modLog] = mod; return this; }
	public ModuleBurnTime setWoodHeatMod(double mod) { this.modHeat[ModuleBurnTime.modWood] = mod; return this; }
	public ModuleBurnTime setCoalHeatMod(double mod) { this.modHeat[ModuleBurnTime.modCoal] = mod; return this; }
	public ModuleBurnTime setLigniteHeatMod(double mod) { this.modHeat[ModuleBurnTime.modLignite] = mod; return this; }
	public ModuleBurnTime setCokeHeatMod(double mod) { this.modHeat[ModuleBurnTime.modCoke] = mod; return this; }
	public ModuleBurnTime setSolidHeatMod(double mod) { this.modHeat[ModuleBurnTime.modSolid] = mod; return this; }
	public ModuleBurnTime setRocketHeatMod(double mod) { this.modHeat[ModuleBurnTime.modRocket] = mod; return this; }
	public ModuleBurnTime setBalefireHeatMod(double mod) { this.modHeat[ModuleBurnTime.modBalefire] = mod; return this; }
}
