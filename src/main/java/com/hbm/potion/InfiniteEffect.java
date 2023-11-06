package com.hbm.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;

/**
 * @author  Gabriel Roche
 * @since   
 * @version 
 */
public class InfiniteEffect extends PotionEffect {
	
	public InfiniteEffect(int id) {
		this(id, 1);
	}
	
	public InfiniteEffect(int id, int amplifier) {
		this(id, amplifier, false);
	}
	
	public InfiniteEffect(int id, int amplifier, boolean ambiant) {
		super(id, -1, amplifier, ambiant);
	}
	
	@Override
	public boolean onUpdate(EntityLivingBase p_76455_1_) {
		this.performEffect(p_76455_1_);
		return true;
	}
	
}