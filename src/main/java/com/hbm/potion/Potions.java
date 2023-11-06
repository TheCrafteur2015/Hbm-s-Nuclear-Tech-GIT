package com.hbm.potion;

import net.minecraft.potion.Potion;

/**
 * @author  Gabriel Roche
 * @since   
 * @version 
 */
public class Potions extends Potion {
	
	public static Potion speed = new Potions(25, false, 8171462).setPotionName("potion.moveSpeed").setIconIndex(0, 0);
	
	protected Potions(int id, boolean isBad, int color) {
		super(id, isBad, color);
	}
	
	@Override
	public Potions setPotionName(String name) {
		return (Potions) super.setPotionName(name);
	}
	
	@Override
	protected Potions setIconIndex(int a, int b) {
		return (Potions) super.setIconIndex(a, b);
	}
	
}