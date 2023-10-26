package com.hbm.module;

import java.util.List;

import com.hbm.util.ItemStackUtil;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ModulePatternMatcher {

	public static final String MODE_EXACT = "exact";
	public static final String MODE_WILDCARD = "wildcard";
	public String[] modes;
	
	public ModulePatternMatcher() {
		this.modes = new String[1];
	}
	
	public ModulePatternMatcher(int count) {
		this.modes = new String[count];
	}
	
	public void initPatternSmart(World world, ItemStack stack, int i) {
		
		if(world.isRemote) return;
		
		if(stack == null) {
			this.modes[i] = null;
			return;
		}
		
		List<String> names = ItemStackUtil.getOreDictNames(stack);

		if(iterateAndCheck(names, i ,"ingot") || iterateAndCheck(names, i ,"block") || iterateAndCheck(names, i ,"dust") || iterateAndCheck(names, i ,"nugget")) return;
		if(iterateAndCheck(names, i ,"plate")) return;
		
		if(stack.getHasSubtypes()) {
			this.modes[i] = ModulePatternMatcher.MODE_EXACT;
		} else {
			this.modes[i] = ModulePatternMatcher.MODE_WILDCARD;
		}
	}
	
	private boolean iterateAndCheck(List<String> names, int i, String prefix) {
		
		for(String s : names) {
			if(s.startsWith(prefix)) {
				this.modes[i] = s;
				return true;
			}
		}
		
		return false;
	}
	
	public void initPatternStandard(World world, ItemStack stack, int i) {
		
		if(world.isRemote) return;
		
		if(stack == null) {
			this.modes[i] = null;
			return;
		}
		
		if(stack.getHasSubtypes()) {
			this.modes[i] = ModulePatternMatcher.MODE_EXACT;
		} else {
			this.modes[i] = ModulePatternMatcher.MODE_WILDCARD;
		}
	}
	
	public void nextMode(World world, ItemStack pattern, int i) {
		
		if(world.isRemote) return;
		
		if(pattern == null) {
			this.modes[i] = null;
			return;
		}
		
		if(this.modes[i] == null) {
			this.modes[i] = ModulePatternMatcher.MODE_EXACT;
		} else if(ModulePatternMatcher.MODE_EXACT.equals(this.modes[i])) {
			this.modes[i] = ModulePatternMatcher.MODE_WILDCARD;
		} else if(ModulePatternMatcher.MODE_WILDCARD.equals(this.modes[i])) {
			
			List<String> names = ItemStackUtil.getOreDictNames(pattern);
			
			if(names.isEmpty()) {
				this.modes[i] = ModulePatternMatcher.MODE_EXACT;
			} else {
				this.modes[i] = names.get(0);
			}
		} else {
			
			List<String> names = ItemStackUtil.getOreDictNames(pattern);
			
			if(names.size() < 2 || this.modes[i].equals(names.get(names.size() - 1))) {
				this.modes[i] = ModulePatternMatcher.MODE_EXACT;
			} else {
				
				for(int j = 0; j < names.size() - 1; j++) {
					
					if(this.modes[i].equals(names.get(j))) {
						this.modes[i] = names.get(j + 1);
						return;
					}
				}
			}
		}
	}
	
	public boolean isValidForFilter(ItemStack filter, int index, ItemStack input) {
		
		String mode = this.modes[index];
		
		if(mode == null) {
			this.modes[index] = mode = ModulePatternMatcher.MODE_EXACT;
		}
		
		switch(mode) {
		case MODE_EXACT: return input.isItemEqual(filter) && ItemStack.areItemStackTagsEqual(input, filter);
		case MODE_WILDCARD: return input.getItem() == filter.getItem() && ItemStack.areItemStackTagsEqual(input, filter);
		default:
			List<String> keys = ItemStackUtil.getOreDictNames(input);
			return keys.contains(mode);
		}
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		
		for(int i = 0; i < this.modes.length; i++) {
			if(nbt.hasKey("mode" + i)) {
				this.modes[i] = nbt.getString("mode" + i);
			} else {
				this.modes[i] = null;
			}
		}
	}
	
	public void writeToNBT(NBTTagCompound nbt) {

		for(int i = 0; i < this.modes.length; i++) {
			if(this.modes[i] != null) {
				nbt.setString("mode" + i, this.modes[i]);
			}
		}
	}
}
