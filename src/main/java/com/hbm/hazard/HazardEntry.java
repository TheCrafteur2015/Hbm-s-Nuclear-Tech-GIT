package com.hbm.hazard;

import java.util.ArrayList;
import java.util.List;

import com.hbm.hazard.modifier.HazardModifier;
import com.hbm.hazard.type.HazardTypeBase;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class HazardEntry {

	HazardTypeBase type;
	float baseLevel;
	
	/*
	 * Modifiers are evaluated in the order they're being applied to the entry.
	 */
	List<HazardModifier> mods = new ArrayList<>();
	
	public HazardEntry(HazardTypeBase type) {
		this(type, 1F);
	}
	
	public HazardEntry(HazardTypeBase type, float level) {
		this.type = type;
		this.baseLevel = level;
	}
	
	public HazardEntry addMod(HazardModifier mod) {
		this.mods.add(mod);
		return this;
	}
	
	public void applyHazard(ItemStack stack, EntityLivingBase entity) {
		this.type.onUpdate(entity, HazardModifier.evalAllModifiers(stack, entity, this.baseLevel, this.mods), stack);
	}
	
	public HazardTypeBase getType() {
		return this.type;
	}
	
	@Override
	public HazardEntry clone() {
		return clone(1F);
	}
	
	public HazardEntry clone(float mult) {
		HazardEntry clone = new HazardEntry(this.type, this.baseLevel * mult);
		clone.mods = this.mods;
		return clone;
	}
}
