package com.hbm.util;

import java.util.HashMap;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ItemAmmoEnums;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;

public class AchievementHandler {

	public static HashMap<ComparableStack, Achievement> craftingAchievements = new HashMap<>();
	
	public static void register() {
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModItems.gun_mp40), MainRegistry.achFreytag);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModItems.piston_selenium), MainRegistry.achSelenium);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModItems.gun_b92), MainRegistry.achSelenium);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModItems.battery_potatos), MainRegistry.achPotato);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModItems.gun_revolver_pip), MainRegistry.achC44);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModBlocks.machine_press), MainRegistry.achBurnerPress);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModItems.rbmk_fuel_empty), MainRegistry.achRBMK);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModBlocks.machine_chemplant), MainRegistry.achChemplant);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModBlocks.concrete_smooth), MainRegistry.achConcrete);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModBlocks.concrete_asbestos), MainRegistry.achConcrete);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModItems.ingot_polymer), MainRegistry.achPolymer);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModItems.ingot_desh), MainRegistry.achDesh);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModItems.gem_tantalium), MainRegistry.achTantalum);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModBlocks.machine_gascent), MainRegistry.achGasCent);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModBlocks.machine_centrifuge), MainRegistry.achCentrifuge);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModItems.ingot_schrabidium), MainRegistry.achSchrab);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModItems.nugget_schrabidium), MainRegistry.achSchrab);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModBlocks.machine_crystallizer), MainRegistry.achAcidizer);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModBlocks.machine_silex), MainRegistry.achSILEX);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModItems.nugget_technetium), MainRegistry.achTechnetium);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModBlocks.struct_watz_core), MainRegistry.achWatz);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModItems.nugget_bismuth), MainRegistry.achBismuth);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModItems.nugget_am241), MainRegistry.achBreeding);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModItems.nugget_am242), MainRegistry.achBreeding);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModItems.missile_nuclear), MainRegistry.achRedBalloons);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModItems.missile_nuclear_cluster), MainRegistry.achRedBalloons);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModItems.missile_doomsday), MainRegistry.achRedBalloons);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModItems.mp_warhead_10_nuclear), MainRegistry.achRedBalloons);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModItems.mp_warhead_10_nuclear_large), MainRegistry.achRedBalloons);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModItems.mp_warhead_15_nuclear), MainRegistry.achRedBalloons);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModItems.mp_warhead_15_nuclear_shark), MainRegistry.achRedBalloons);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModItems.mp_warhead_15_boxcar), MainRegistry.achRedBalloons);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModBlocks.struct_iter_core), MainRegistry.achFusion);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModBlocks.machine_difurnace_off), MainRegistry.achBlastFurnace);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModBlocks.machine_assembler), MainRegistry.achAssembly);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModItems.billet_pu_mix), MainRegistry.achChicagoPile);
		AchievementHandler.craftingAchievements.put(new ComparableStack(ModItems.ammo_4gauge, 1, ItemAmmoEnums.Ammo4Gauge.VAMPIRE.ordinal()), MainRegistry.achWitchtaunter);
	}
	
	public static void fire(EntityPlayer player, ItemStack stack) {
		if(player.worldObj.isRemote) return;
		ComparableStack comp = new ComparableStack(stack).makeSingular();
		Achievement achievement = AchievementHandler.craftingAchievements.get(comp);
		if(achievement != null) {
			player.triggerAchievement(achievement);
		}
	}
}
