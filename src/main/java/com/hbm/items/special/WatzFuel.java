package com.hbm.items.special;

import java.util.List;

import com.hbm.items.machine.ItemFuelRod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class WatzFuel extends ItemFuelRod {

	public int power;
	public float powerMultiplier;
	public int heat;
	public float heatMultiplier;
	public float decayMultiplier;
	
	/**
	 * Constructor for a new Watz fuel pellet
	 * @param lifeTime
	 * @param power
	 * @param powerMultiplier
	 * @param heat
	 * @param heatMultiplier
	 * @param decayMultiplier
	 */
	
	public WatzFuel(int lifeTime, int power, float powerMultiplier, int heat, float heatMultiplier, float decayMultiplier) {
		super(lifeTime * 100);
		this.power = power/10;
		this.powerMultiplier = powerMultiplier;
		this.heat = heat;
		this.heatMultiplier = heatMultiplier;
		this.decayMultiplier = decayMultiplier;
		setMaxDamage(100);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Max age:          " + this.lifeTime/100 + " ticks");
		list.add("Power per tick:  " + (this.power) + "HE");
		list.add("Power multiplier: " + (this.powerMultiplier >= 1 ? "+" : "") + (Math.round(this.powerMultiplier * 1000) * .10 - 100) + "%");
		list.add("Heat provided:   " + this.heat + " heat");
		list.add("Heat multiplier:   " + (this.heatMultiplier >= 1 ? "+" : "") + (Math.round(this.heatMultiplier * 1000) * .10 - 100) + "%");
		list.add("Decay multiplier: " + (this.decayMultiplier >= 1 ? "+" : "") + (Math.round(this.decayMultiplier * 1000) * .10 - 100) + "%");
		
		super.addInformation(itemstack, player, list, bool);
	}
	
	public static void updateDamage(ItemStack stack) {
		
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.setItemDamage((int)((double)ItemFuelRod.getLifeTime(stack) / (double)((WatzFuel)stack.getItem()).lifeTime * 100D));
	}
}
