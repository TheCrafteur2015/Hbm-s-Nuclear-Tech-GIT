package com.hbm.test;

import com.github.thecrafteur2015.util.Reflector;
import com.hbm.entity.mob.EntityPigeon;

import net.minecraft.entity.EntityLiving;

/**
 * @author  Gabriel Roche
 * @since   
 * @version 
 */
public class ClassTest {
	
	public static void main(String[] args) {
		System.out.println(Reflector.isChildOf(EntityPigeon.class, EntityLiving.class));
	}
	
}