package com.hbm.entity;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.cart.EntityMinecartBogie;
import com.hbm.entity.cart.EntityMinecartCrate;
import com.hbm.entity.cart.EntityMinecartDestroyer;
import com.hbm.entity.cart.EntityMinecartOre;
import com.hbm.entity.cart.EntityMinecartPowder;
import com.hbm.entity.cart.EntityMinecartSemtex;
import com.hbm.entity.effect.EntityBlackHole;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.effect.EntityCloudFleijaRainbow;
import com.hbm.entity.effect.EntityCloudSolinium;
import com.hbm.entity.effect.EntityCloudTom;
import com.hbm.entity.effect.EntityEMPBlast;
import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.entity.effect.EntityMist;
import com.hbm.entity.effect.EntityNukeCloudBig;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.effect.EntityQuasar;
import com.hbm.entity.effect.EntityRagingVortex;
import com.hbm.entity.effect.EntitySpear;
import com.hbm.entity.effect.EntityVortex;
import com.hbm.entity.grenade.EntityGrenadeASchrab;
import com.hbm.entity.grenade.EntityGrenadeBlackHole;
import com.hbm.entity.grenade.EntityGrenadeBouncyGeneric;
import com.hbm.entity.grenade.EntityGrenadeBreach;
import com.hbm.entity.grenade.EntityGrenadeBurst;
import com.hbm.entity.grenade.EntityGrenadeCloud;
import com.hbm.entity.grenade.EntityGrenadeCluster;
import com.hbm.entity.grenade.EntityGrenadeDynamite;
import com.hbm.entity.grenade.EntityGrenadeElectric;
import com.hbm.entity.grenade.EntityGrenadeFire;
import com.hbm.entity.grenade.EntityGrenadeFlare;
import com.hbm.entity.grenade.EntityGrenadeFrag;
import com.hbm.entity.grenade.EntityGrenadeGas;
import com.hbm.entity.grenade.EntityGrenadeGascan;
import com.hbm.entity.grenade.EntityGrenadeGeneric;
import com.hbm.entity.grenade.EntityGrenadeIFBouncy;
import com.hbm.entity.grenade.EntityGrenadeIFBrimstone;
import com.hbm.entity.grenade.EntityGrenadeIFConcussion;
import com.hbm.entity.grenade.EntityGrenadeIFGeneric;
import com.hbm.entity.grenade.EntityGrenadeIFHE;
import com.hbm.entity.grenade.EntityGrenadeIFHopwire;
import com.hbm.entity.grenade.EntityGrenadeIFImpact;
import com.hbm.entity.grenade.EntityGrenadeIFIncendiary;
import com.hbm.entity.grenade.EntityGrenadeIFMystery;
import com.hbm.entity.grenade.EntityGrenadeIFNull;
import com.hbm.entity.grenade.EntityGrenadeIFSpark;
import com.hbm.entity.grenade.EntityGrenadeIFSticky;
import com.hbm.entity.grenade.EntityGrenadeIFToxic;
import com.hbm.entity.grenade.EntityGrenadeImpactGeneric;
import com.hbm.entity.grenade.EntityGrenadeLemon;
import com.hbm.entity.grenade.EntityGrenadeMIRV;
import com.hbm.entity.grenade.EntityGrenadeMk2;
import com.hbm.entity.grenade.EntityGrenadeNuclear;
import com.hbm.entity.grenade.EntityGrenadeNuke;
import com.hbm.entity.grenade.EntityGrenadePC;
import com.hbm.entity.grenade.EntityGrenadePlasma;
import com.hbm.entity.grenade.EntityGrenadePoison;
import com.hbm.entity.grenade.EntityGrenadePulse;
import com.hbm.entity.grenade.EntityGrenadeSchrabidium;
import com.hbm.entity.grenade.EntityGrenadeShrapnel;
import com.hbm.entity.grenade.EntityGrenadeSmart;
import com.hbm.entity.grenade.EntityGrenadeStrong;
import com.hbm.entity.grenade.EntityGrenadeTau;
import com.hbm.entity.grenade.EntityGrenadeZOMG;
import com.hbm.entity.grenade.EntityWastePearl;
import com.hbm.entity.item.EntityDeliveryDrone;
import com.hbm.entity.item.EntityFallingBlockNT;
import com.hbm.entity.item.EntityFireworks;
import com.hbm.entity.item.EntityItemBuoyant;
import com.hbm.entity.item.EntityItemWaste;
import com.hbm.entity.item.EntityMagnusCartus;
import com.hbm.entity.item.EntityMinecartTest;
import com.hbm.entity.item.EntityMovingItem;
import com.hbm.entity.item.EntityMovingPackage;
import com.hbm.entity.item.EntityRequestDrone;
import com.hbm.entity.item.EntityTNTPrimedBase;
import com.hbm.entity.logic.EntityBalefire;
import com.hbm.entity.logic.EntityBomber;
import com.hbm.entity.logic.EntityDeathBlast;
import com.hbm.entity.logic.EntityEMP;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.entity.logic.EntityNukeExplosionNT;
import com.hbm.entity.logic.EntityTomBlast;
import com.hbm.entity.missile.EntityBobmazon;
import com.hbm.entity.missile.EntityBombletSelena;
import com.hbm.entity.missile.EntityBombletTheta;
import com.hbm.entity.missile.EntityBooster;
import com.hbm.entity.missile.EntityCarrier;
import com.hbm.entity.missile.EntityMIRV;
import com.hbm.entity.missile.EntityMinerRocket;
import com.hbm.entity.missile.EntityMissileAntiBallistic;
import com.hbm.entity.missile.EntityMissileBHole;
import com.hbm.entity.missile.EntityMissileBunkerBuster;
import com.hbm.entity.missile.EntityMissileBurst;
import com.hbm.entity.missile.EntityMissileBusterStrong;
import com.hbm.entity.missile.EntityMissileCluster;
import com.hbm.entity.missile.EntityMissileClusterStrong;
import com.hbm.entity.missile.EntityMissileCustom;
import com.hbm.entity.missile.EntityMissileDoomsday;
import com.hbm.entity.missile.EntityMissileDrill;
import com.hbm.entity.missile.EntityMissileEMP;
import com.hbm.entity.missile.EntityMissileEMPStrong;
import com.hbm.entity.missile.EntityMissileEndo;
import com.hbm.entity.missile.EntityMissileExo;
import com.hbm.entity.missile.EntityMissileGeneric;
import com.hbm.entity.missile.EntityMissileIncendiary;
import com.hbm.entity.missile.EntityMissileIncendiaryStrong;
import com.hbm.entity.missile.EntityMissileInferno;
import com.hbm.entity.missile.EntityMissileMicro;
import com.hbm.entity.missile.EntityMissileMirv;
import com.hbm.entity.missile.EntityMissileNuclear;
import com.hbm.entity.missile.EntityMissileRain;
import com.hbm.entity.missile.EntityMissileSchrabidium;
import com.hbm.entity.missile.EntityMissileShuttle;
import com.hbm.entity.missile.EntityMissileStrong;
import com.hbm.entity.missile.EntityMissileTaint;
import com.hbm.entity.missile.EntityMissileVolcano;
import com.hbm.entity.missile.EntitySiegeDropship;
import com.hbm.entity.missile.EntitySoyuz;
import com.hbm.entity.missile.EntitySoyuzCapsule;
import com.hbm.entity.missile.EntityTestMissile;
import com.hbm.entity.mob.EntityBlockSpider;
import com.hbm.entity.mob.EntityCreeperGold;
import com.hbm.entity.mob.EntityCreeperNuclear;
import com.hbm.entity.mob.EntityCreeperPhosgene;
import com.hbm.entity.mob.EntityCreeperTainted;
import com.hbm.entity.mob.EntityCreeperVolatile;
import com.hbm.entity.mob.EntityCyberCrab;
import com.hbm.entity.mob.EntityDuck;
import com.hbm.entity.mob.EntityFBI;
import com.hbm.entity.mob.EntityFBIDrone;
import com.hbm.entity.mob.EntityGhost;
import com.hbm.entity.mob.EntityGlyphid;
import com.hbm.entity.mob.EntityGlyphidBehemoth;
import com.hbm.entity.mob.EntityGlyphidBlaster;
import com.hbm.entity.mob.EntityGlyphidBombardier;
import com.hbm.entity.mob.EntityGlyphidBrawler;
import com.hbm.entity.mob.EntityGlyphidBrenda;
import com.hbm.entity.mob.EntityGlyphidNuclear;
import com.hbm.entity.mob.EntityGlyphidScout;
import com.hbm.entity.mob.EntityHunterChopper;
import com.hbm.entity.mob.EntityMaskMan;
import com.hbm.entity.mob.EntityPigeon;
import com.hbm.entity.mob.EntityPlasticBag;
import com.hbm.entity.mob.EntityQuackos;
import com.hbm.entity.mob.EntityRADBeast;
import com.hbm.entity.mob.EntityTaintCrab;
import com.hbm.entity.mob.EntityTeslaCrab;
import com.hbm.entity.mob.EntityUFO;
import com.hbm.entity.mob.botprime.EntityBOTPrimeBody;
import com.hbm.entity.mob.botprime.EntityBOTPrimeHead;
import com.hbm.entity.mob.siege.EntitySiegeCraft;
import com.hbm.entity.mob.siege.EntitySiegeSkeleton;
import com.hbm.entity.mob.siege.EntitySiegeTunneler;
import com.hbm.entity.mob.siege.EntitySiegeUFO;
import com.hbm.entity.mob.siege.EntitySiegeZombie;
import com.hbm.entity.particle.EntityBSmokeFX;
import com.hbm.entity.particle.EntityChlorineFX;
import com.hbm.entity.particle.EntityCloudFX;
import com.hbm.entity.particle.EntityDSmokeFX;
import com.hbm.entity.particle.EntityFogFX;
import com.hbm.entity.particle.EntityGasFX;
import com.hbm.entity.particle.EntityOilSpillFX;
import com.hbm.entity.particle.EntityOrangeFX;
import com.hbm.entity.particle.EntityPinkCloudFX;
import com.hbm.entity.particle.EntitySSmokeFX;
import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.entity.particle.EntityTSmokeFX;
import com.hbm.entity.projectile.EntityAAShell;
import com.hbm.entity.projectile.EntityAcidBomb;
import com.hbm.entity.projectile.EntityArtilleryRocket;
import com.hbm.entity.projectile.EntityArtilleryShell;
import com.hbm.entity.projectile.EntityBeamVortex;
import com.hbm.entity.projectile.EntityBombletZeta;
import com.hbm.entity.projectile.EntityBoxcar;
import com.hbm.entity.projectile.EntityBuilding;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.entity.projectile.EntityBulletBaseNT;
import com.hbm.entity.projectile.EntityBurningFOEQ;
import com.hbm.entity.projectile.EntityChemical;
import com.hbm.entity.projectile.EntityChopperMine;
import com.hbm.entity.projectile.EntityCog;
import com.hbm.entity.projectile.EntityCombineBall;
import com.hbm.entity.projectile.EntityDischarge;
import com.hbm.entity.projectile.EntityDuchessGambit;
import com.hbm.entity.projectile.EntityExplosiveBeam;
import com.hbm.entity.projectile.EntityFallingNuke;
import com.hbm.entity.projectile.EntityFire;
import com.hbm.entity.projectile.EntityLN2;
import com.hbm.entity.projectile.EntityLaser;
import com.hbm.entity.projectile.EntityLaserBeam;
import com.hbm.entity.projectile.EntityMeteor;
import com.hbm.entity.projectile.EntityMinerBeam;
import com.hbm.entity.projectile.EntityModBeam;
import com.hbm.entity.projectile.EntityNightmareBlast;
import com.hbm.entity.projectile.EntityOilSpill;
import com.hbm.entity.projectile.EntityPlasmaBeam;
import com.hbm.entity.projectile.EntityRBMKDebris;
import com.hbm.entity.projectile.EntityRainbow;
import com.hbm.entity.projectile.EntityRocket;
import com.hbm.entity.projectile.EntityRocketHoming;
import com.hbm.entity.projectile.EntityRubble;
import com.hbm.entity.projectile.EntitySawblade;
import com.hbm.entity.projectile.EntitySchrab;
import com.hbm.entity.projectile.EntityShrapnel;
import com.hbm.entity.projectile.EntitySiegeLaser;
import com.hbm.entity.projectile.EntitySparkBeam;
import com.hbm.entity.projectile.EntityTom;
import com.hbm.entity.projectile.EntityWaterSplash;
import com.hbm.entity.projectile.EntityZirnoxDebris;
import com.hbm.entity.train.EntityRailCarBase.BoundingBoxDummyEntity;
import com.hbm.entity.train.EntityRailCarRidable.SeatDummyEntity;
import com.hbm.entity.train.TrainCargoTram;
import com.hbm.entity.train.TrainCargoTramTrailer;
import com.hbm.entity.train.TrainTunnelBore;
import com.hbm.main.MainRegistry;
import com.hbm.util.Tuple.Quartet;

import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

@SuppressWarnings("deprecation")
public class EntityMappings {

	public static List<Quartet<Class<? extends Entity>, String, Integer, Boolean>> entityMappings = new ArrayList<>();
	public static List<Quartet<Class<? extends Entity>, String, Integer, Integer>> mobMappings = new ArrayList<>();

	public static void writeMappings() {

		EntityMappings.addEntity(EntityRocket.class, "entity_rocket", 250);
		EntityMappings.addEntity(EntityGrenadeGeneric.class, "entity_grenade_generic", 250);
		EntityMappings.addEntity(EntityGrenadeStrong.class, "entity_grenade_strong", 250);
		EntityMappings.addEntity(EntityGrenadeFrag.class, "entity_grenade_frag", 250);
		EntityMappings.addEntity(EntityGrenadeFire.class, "entity_grenade_fire", 250);
		EntityMappings.addEntity(EntityGrenadeCluster.class, "entity_grenade_cluster", 250);
		EntityMappings.addEntity(EntityTestMissile.class, "entity_test_missile", 1000);
		EntityMappings.addEntity(EntityNukeCloudSmall.class, "entity_nuke_cloud_small", 10000);
		EntityMappings.addEntity(EntityBullet.class, "entity_bullet", 250);
		EntityMappings.addEntity(EntityGrenadeFlare.class, "entity_grenade_flare", 500);
		EntityMappings.addEntity(EntityGrenadeElectric.class, "entity_grenade_electric", 500);
		EntityMappings.addEntity(EntityGrenadePoison.class, "entity_grenade_poison", 500);
		EntityMappings.addEntity(EntityGrenadeGas.class, "entity_grenade_gas", 500);
		EntityMappings.addEntity(EntityGrenadeSchrabidium.class, "entity_grenade_schrab", 500);
		EntityMappings.addEntity(EntityGrenadeNuke.class, "entity_grenade_nuke", 500);
		EntityMappings.addEntity(EntitySchrab.class, "entity_schrabnel", 500);
		EntityMappings.addEntity(EntityMissileGeneric.class, "entity_missile_generic", 1000);
		EntityMappings.addEntity(EntityMissileStrong.class, "entity_missile_strong", 1000);
		EntityMappings.addEntity(EntityMissileNuclear.class, "entity_missile_nuclear", 1000);
		EntityMappings.addEntity(EntityMissileCluster.class, "entity_missile_cluster", 1000);
		EntityMappings.addEntity(EntityMissileIncendiary.class, "entity_missile_incendiary", 1000);
		EntityMappings.addEntity(EntityMissileAntiBallistic.class, "entity_missile_anti", 1000);
		EntityMappings.addEntity(EntityMissileBunkerBuster.class, "entity_missile_buster", 1000);
		EntityMappings.addEntity(EntityMissileIncendiaryStrong.class, "entity_missile_incendiary_strong", 1000);
		EntityMappings.addEntity(EntityMissileClusterStrong.class, "entity_missile_cluster_strong", 1000);
		EntityMappings.addEntity(EntityMissileBusterStrong.class, "entity_missile_buster_strong", 1000);
		EntityMappings.addEntity(EntityMissileBurst.class, "entity_missile_burst", 1000);
		EntityMappings.addEntity(EntityMissileInferno.class, "entity_missile_inferno", 1000);
		EntityMappings.addEntity(EntityMissileRain.class, "entity_missile_rain", 1000);
		EntityMappings.addEntity(EntityMissileDrill.class, "entity_missile_drill", 1000);
		EntityMappings.addEntity(EntityMissileEndo.class, "entity_missile_endo", 1000);
		EntityMappings.addEntity(EntityMissileExo.class, "entity_missile_exo", 1000);
		EntityMappings.addEntity(EntityMissileMirv.class, "entity_missile_mirv", 1000);
		EntityMappings.addEntity(EntityMIRV.class, "entity_mirvlet", 1000);
		EntityMappings.addEntity(EntitySmokeFX.class, "entity_smoke_fx", 1000);
		EntityMappings.addEntity(EntityNukeCloudBig.class, "entity_nuke_cloud_big", 1000);
		EntityMappings.addEntity(EntityGrenadeNuclear.class, "entity_grenade_nuclear", 1000);
		EntityMappings.addEntity(EntityBSmokeFX.class, "entity_b_smoke_fx", 1000);
		EntityMappings.addEntity(EntityGrenadePlasma.class, "entity_grenade_plasma", 500);
		EntityMappings.addEntity(EntityGrenadeTau.class, "entity_grenade_tau", 500);
		EntityMappings.addEntity(EntityChopperMine.class, "entity_chopper_mine", 1000);
		EntityMappings.addEntity(EntityCombineBall.class, "entity_combine_ball", 1000);
		EntityMappings.addEntity(EntityRainbow.class, "entity_rainbow", 1000);
		EntityMappings.addEntity(EntityGrenadeLemon.class, "entity_grenade_lemon", 500);
		EntityMappings.addEntity(EntityCloudFleija.class, "entity_cloud_fleija", 500);
		EntityMappings.addEntity(EntityGrenadeMk2.class, "entity_grenade_mk2", 500);
		EntityMappings.addEntity(EntityGrenadeZOMG.class, "entity_grenade_zomg", 500);
		EntityMappings.addEntity(EntityGrenadeASchrab.class, "entity_grenade_aschrab", 500);
		EntityMappings.addEntity(EntityFalloutRain.class, "entity_fallout", 1000);
		EntityMappings.addEntity(EntityDischarge.class, "entity_emp_discharge", 500);
		EntityMappings.addEntity(EntityEMPBlast.class, "entity_emp_blast", 1000);
		EntityMappings.addEntity(EntityFire.class, "entity_fire", 1000);
		EntityMappings.addEntity(EntityPlasmaBeam.class, "entity_immolator_beam", 1000);
		EntityMappings.addEntity(EntityLN2.class, "entity_LN2", 1000);
		EntityMappings.addEntity(EntityNightmareBlast.class, "entity_ominous_bullet", 1000);
		EntityMappings.addEntity(EntityGrenadePulse.class, "entity_grenade_pulse", 1000);
		EntityMappings.addEntity(EntityLaserBeam.class, "entity_laser_beam", 1000);
		EntityMappings.addEntity(EntityMinerBeam.class, "entity_miner_beam", 1000);
		EntityMappings.addEntity(EntityRubble.class, "entity_rubble", 1000);
		EntityMappings.addEntity(EntityDSmokeFX.class, "entity_d_smoke_fx", 1000);
		EntityMappings.addEntity(EntitySSmokeFX.class, "entity_s_smoke_fx", 1000);
		EntityMappings.addEntity(EntityShrapnel.class, "entity_shrapnel", 1000);
		EntityMappings.addEntity(EntityGrenadeShrapnel.class, "entity_grenade_shrapnel", 250);
		EntityMappings.addEntity(EntityBlackHole.class, "entity_black_hole", 250);
		EntityMappings.addEntity(EntityGrenadeBlackHole.class, "entity_grenade_black_hole", 250);
		EntityMappings.addEntity(EntityOilSpillFX.class, "entity_spill_fx", 1000);
		EntityMappings.addEntity(EntityOilSpill.class, "entity_oil_spill", 1000);
		EntityMappings.addEntity(EntityGasFX.class, "entity_spill_fx", 1000);
		EntityMappings.addEntity(EntityMinecartTest.class, "entity_minecart_test", 1000);
		EntityMappings.addEntity(EntitySparkBeam.class, "entity_spark_beam", 1000);
		EntityMappings.addEntity(EntityMissileDoomsday.class, "entity_missile_doomsday", 1000);
		EntityMappings.addEntity(EntityBombletTheta.class, "entity_theta", 1000);
		EntityMappings.addEntity(EntityBombletSelena.class, "entity_selena", 1000);
		EntityMappings.addEntity(EntityTSmokeFX.class, "entity_t_smoke_fx", 1000);
		EntityMappings.addEntity(EntityNukeExplosionMK3.class, "entity_nuke_mk3", 1000);
		EntityMappings.addEntity(EntityVortex.class, "entity_vortex", 250);
		EntityMappings.addEntity(EntityMeteor.class, "entity_meteor", 250);
		EntityMappings.addEntity(EntityLaser.class, "entity_laser", 1000);
		EntityMappings.addEntity(EntityBoxcar.class, "entity_boxcar", 1000);
		EntityMappings.addEntity(EntityMissileTaint.class, "entity_missile_taint", 1000);
		EntityMappings.addEntity(EntityGrenadeGascan.class, "entity_grenade_gascan", 1000);
		EntityMappings.addEntity(EntityNukeExplosionMK5.class, "entity_nuke_mk5", 1000);
		EntityMappings.addEntity(EntityCloudFleijaRainbow.class, "entity_cloud_rainbow", 1000);
		EntityMappings.addEntity(EntityExplosiveBeam.class, "entity_beam_bomb", 1000);
		EntityMappings.addEntity(EntityAAShell.class, "entity_aa_shell", 1000);
		EntityMappings.addEntity(EntityRocketHoming.class, "entity_stinger", 1000);
		EntityMappings.addEntity(EntityMissileMicro.class, "entity_missile_micronuclear", 1000);
		EntityMappings.addEntity(EntityCloudSolinium.class, "entity_cloud_rainbow", 1000);
		EntityMappings.addEntity(EntityRagingVortex.class, "entity_raging_vortex", 250);
		EntityMappings.addEntity(EntityCarrier.class, "entity_missile_carrier", 1000);
		EntityMappings.addEntity(EntityBooster.class, "entity_missile_booster", 1000);
		EntityMappings.addEntity(EntityModBeam.class, "entity_beam_bang", 1000);
		EntityMappings.addEntity(EntityMissileBHole.class, "entity_missile_blackhole", 1000);
		EntityMappings.addEntity(EntityMissileSchrabidium.class, "entity_missile_schrabidium", 1000);
		EntityMappings.addEntity(EntityMissileEMP.class, "entity_missile_emp", 1000);
		EntityMappings.addEntity(EntityChlorineFX.class, "entity_chlorine_fx", 1000);
		EntityMappings.addEntity(EntityPinkCloudFX.class, "entity_pink_cloud_fx", 1000);
		EntityMappings.addEntity(EntityCloudFX.class, "entity_cloud_fx", 1000);
		EntityMappings.addEntity(EntityGrenadePC.class, "entity_grenade_pink_cloud", 250);
		EntityMappings.addEntity(EntityGrenadeCloud.class, "entity_grenade_cloud", 250);
		EntityMappings.addEntity(EntityBomber.class, "entity_bomber", 1000);
		EntityMappings.addEntity(EntityBombletZeta.class, "entity_zeta", 1000);
		EntityMappings.addEntity(EntityOrangeFX.class, "entity_agent_orange", 1000);
		EntityMappings.addEntity(EntityDeathBlast.class, "entity_laser_blast", 1000);
		EntityMappings.addEntity(EntityGrenadeSmart.class, "entity_grenade_smart", 250);
		EntityMappings.addEntity(EntityGrenadeMIRV.class, "entity_grenade_mirv", 250);
		EntityMappings.addEntity(EntityGrenadeBreach.class, "entity_grenade_breach", 250);
		EntityMappings.addEntity(EntityGrenadeBurst.class, "entity_grenade_burst", 250);
		EntityMappings.addEntity(EntityBurningFOEQ.class, "entity_burning_foeq", 1000);
		EntityMappings.addEntity(EntityGrenadeIFGeneric.class, "entity_grenade_ironshod", 250);
		EntityMappings.addEntity(EntityGrenadeIFHE.class, "entity_grenade_ironshod_he", 250);
		EntityMappings.addEntity(EntityGrenadeIFBouncy.class, "entity_grenade_ironshod_bouncy", 250);
		EntityMappings.addEntity(EntityGrenadeIFSticky.class, "entity_grenade_ironshod_sticky", 250);
		EntityMappings.addEntity(EntityGrenadeIFImpact.class, "entity_grenade_ironshod_impact", 250);
		EntityMappings.addEntity(EntityGrenadeIFIncendiary.class, "entity_grenade_ironshod_fire", 250);
		EntityMappings.addEntity(EntityGrenadeIFToxic.class, "entity_grenade_ironshod_toxic", 250);
		EntityMappings.addEntity(EntityGrenadeIFConcussion.class, "entity_grenade_ironshod_con", 250);
		EntityMappings.addEntity(EntityGrenadeIFBrimstone.class, "entity_grenade_ironshod_brim", 250);
		EntityMappings.addEntity(EntityGrenadeIFMystery.class, "entity_grenade_ironshod_m", 250);
		EntityMappings.addEntity(EntityGrenadeIFSpark.class, "entity_grenade_ironshod_s", 250);
		EntityMappings.addEntity(EntityGrenadeIFHopwire.class, "entity_grenade_ironshod_hopwire", 250);
		EntityMappings.addEntity(EntityGrenadeIFNull.class, "entity_grenade_ironshod_null", 250);
		EntityMappings.addEntity(EntityFallingNuke.class, "entity_falling_bomb", 1000);
		EntityMappings.addEntity(EntityBulletBaseNT.class, "entity_bullet_mk3", 250, false);
		EntityMappings.addEntity(EntityMinerRocket.class, "entity_miner_lander", 1000);
		EntityMappings.addEntity(EntityFogFX.class, "entity_nuclear_fog", 1000);
		EntityMappings.addEntity(EntityDuchessGambit.class, "entity_duchessgambit", 1000);
		EntityMappings.addEntity(EntityMissileEMPStrong.class, "entity_missile_emp_strong", 1000);
		EntityMappings.addEntity(EntityEMP.class, "entity_emp_logic", 1000);
		EntityMappings.addEntity(EntityWaterSplash.class, "entity_water_splash", 1000);
		EntityMappings.addEntity(EntityBobmazon.class, "entity_bobmazon_delivery", 1000);
		EntityMappings.addEntity(EntityMissileCustom.class, "entity_custom_missile", 1000);
		EntityMappings.addEntity(EntityBalefire.class, "entity_balefire", 1000);
		EntityMappings.addEntity(EntityTom.class, "entity_tom_the_moonstone", 1000);
		EntityMappings.addEntity(EntityTomBlast.class, "entity_tom_bust", 1000);
		EntityMappings.addEntity(EntityBuilding.class, "entity_falling_building", 1000);
		EntityMappings.addEntity(EntitySoyuz.class, "entity_soyuz", 1000);
		EntityMappings.addEntity(EntitySoyuzCapsule.class, "entity_soyuz_capsule", 1000);
		EntityMappings.addEntity(EntityMovingItem.class, "entity_c_item", 1000);
		EntityMappings.addEntity(EntityMovingPackage.class, "entity_c_package", 1000);
		EntityMappings.addEntity(EntityDeliveryDrone.class, "entity_delivery_drone", 250, false);
		EntityMappings.addEntity(EntityRequestDrone.class, "entity_request_drone", 250, false);
		EntityMappings.addEntity(EntityCloudTom.class, "entity_moonstone_blast", 1000);
		EntityMappings.addEntity(EntityBeamVortex.class, "entity_vortex_beam", 1000);
		EntityMappings.addEntity(EntityFireworks.class, "entity_firework_ball", 1000);
		EntityMappings.addEntity(EntityWastePearl.class, "entity_waste_pearl", 1000);
		EntityMappings.addEntity(EntityBOTPrimeHead.class, "entity_balls_o_tron",  1000);
		EntityMappings.addEntity(EntityBOTPrimeBody.class, "entity_balls_o_tron_seg", 1000);
		EntityMappings.addEntity(EntityBlockSpider.class, "entity_taintcrawler", 1000);
		EntityMappings.addEntity(EntityRBMKDebris.class, "entity_rbmk_debris", 1000);
		EntityMappings.addEntity(EntityUFO.class, "entity_ntm_ufo", 1000);
		EntityMappings.addEntity(EntityNukeExplosionNT.class, "entity_ntm_explosion_nt", 1000);
		EntityMappings.addEntity(EntityQuasar.class, "entity_digamma_quasar", 250);
		EntityMappings.addEntity(EntitySpear.class, "entity_digamma_spear", 1000);
		EntityMappings.addEntity(EntityMissileVolcano.class, "entity_missile_volcano", 1000);
		EntityMappings.addEntity(EntityMissileShuttle.class, "entity_missile_shuttle", 1000);
		EntityMappings.addEntity(EntityZirnoxDebris.class, "entity_zirnox_debris", 1000);
		EntityMappings.addEntity(EntityGhost.class, "entity_ntm_ghost", 1000);
		EntityMappings.addEntity(EntityGrenadeDynamite.class, "entity_grenade_dynamite", 250);
		EntityMappings.addEntity(EntitySiegeLaser.class, "entity_ntm_siege_laser", 1000);
		EntityMappings.addEntity(EntitySiegeDropship.class, "entity_ntm_siege_dropship", 1000);
		EntityMappings.addEntity(EntityTNTPrimedBase.class, "entity_ntm_tnt_primed", 1000);
		EntityMappings.addEntity(EntityGrenadeBouncyGeneric.class, "entity_grenade_bouncy_generic", 250);
		EntityMappings.addEntity(EntityGrenadeImpactGeneric.class, "entity_grenade_impact_generic", 250);
		EntityMappings.addEntity(EntityMinecartCrate.class, "entity_ntm_cart_crate", 250, false);
		EntityMappings.addEntity(EntityMinecartDestroyer.class, "entity_ntm_cart_crate", 250, false);
		EntityMappings.addEntity(EntityMinecartOre.class, "entity_ntm_cart_ore", 250, false);
		EntityMappings.addEntity(EntityMinecartBogie.class, "entity_ntm_cart_bogie", 250, false);
		EntityMappings.addEntity(EntityMagnusCartus.class, "entity_ntm_cart_chungoid", 250, false);
		EntityMappings.addEntity(EntityMinecartPowder.class, "entity_ntm_cart_powder", 250, false);
		EntityMappings.addEntity(EntityMinecartSemtex.class, "entity_ntm_cart_semtex", 250, false);
		EntityMappings.addEntity(EntityNukeTorex.class, "entity_effect_torex", 250, false);
		EntityMappings.addEntity(EntityArtilleryShell.class, "entity_artillery_shell", 1000);
		EntityMappings.addEntity(EntityArtilleryRocket.class, "entity_himars", 1000);
		EntityMappings.addEntity(EntitySiegeTunneler.class, "entity_meme_tunneler", 1000);
		EntityMappings.addEntity(EntityCog.class, "entity_stray_cog", 1000);
		EntityMappings.addEntity(EntitySawblade.class, "entity_stray_saw", 1000);
		EntityMappings.addEntity(EntityChemical.class, "entity_chemthrower_splash", 1000);
		EntityMappings.addEntity(EntityMist.class, "entity_mist", 250, false);
		EntityMappings.addEntity(EntityAcidBomb.class, "entity_acid_bomb", 1000);
		EntityMappings.addEntity(EntityFallingBlockNT.class, "entity_falling_block_nt", 1000);

		EntityMappings.addEntity(EntityItemWaste.class, "entity_item_waste", 100);
		EntityMappings.addEntity(EntityItemBuoyant.class, "entity_item_buoyant", 100);

		EntityMappings.addEntity(SeatDummyEntity.class, "entity_ntm_seat_dummy", 250, false);
		EntityMappings.addEntity(BoundingBoxDummyEntity.class, "entity_ntm_bounding_dummy", 250, false);
		EntityMappings.addEntity(TrainCargoTram.class, "entity_ntm_cargo_tram", 250, false);
		EntityMappings.addEntity(TrainCargoTramTrailer.class, "entity_ntm_cargo_tram_trailer", 250, false);
		EntityMappings.addEntity(TrainTunnelBore.class, "entity_ntm_tunnel_bore", 250, false);
		
		EntityMappings.addMob(EntityCreeperNuclear.class, "entity_mob_nuclear_creeper", 0x204131, 0x75CE00);
		EntityMappings.addMob(EntityCreeperTainted.class, "entity_mob_tainted_creeper", 0x813b9b, 0xd71fdd);
		EntityMappings.addMob(EntityCreeperPhosgene.class, "entity_mob_phosgene_creeper", 0xE3D398, 0xB8A06B);
		EntityMappings.addMob(EntityCreeperVolatile.class, "entity_mob_volatile_creeper", 0xC28153, 0x4D382C);
		EntityMappings.addMob(EntityCreeperGold.class, "entity_mob_gold_creeper", 0xECC136, 0x9E8B3E);
		EntityMappings.addMob(EntityHunterChopper.class, "entity_mob_hunter_chopper", 0x000020, 0x2D2D72);
		EntityMappings.addMob(EntityCyberCrab.class, "entity_cyber_crab", 0xAAAAAA, 0x444444);
		EntityMappings.addMob(EntityTeslaCrab.class, "entity_tesla_crab", 0xAAAAAA, 0x440000);
		EntityMappings.addMob(EntityTaintCrab.class, "entity_taint_crab", 0xAAAAAA, 0xFF00FF);
		EntityMappings.addMob(EntityMaskMan.class, "entity_mob_mask_man", 0x818572, 0xC7C1B7);
		EntityMappings.addMob(EntityDuck.class, "entity_fucc_a_ducc", 0xd0d0d0, 0xFFBF00);
		EntityMappings.addMob(EntityQuackos.class, "entity_elder_one", 0xd0d0d0, 0xFFBF00);
		EntityMappings.addMob(EntityPigeon.class, "entity_pigeon", 0xC8C9CD, 0x858894);
		EntityMappings.addMob(EntityFBI.class, "entity_ntm_fbi", 0x008000, 0x404040);
		EntityMappings.addMob(EntityFBIDrone.class, "entity_ntm_fbi_drone", 0x008000, 0x404040);
		EntityMappings.addMob(EntityRADBeast.class, "entity_ntm_radiation_blaze", 0x303030, 0x008000);
		EntityMappings.addMob(EntitySiegeZombie.class, "entity_meme_zombie", 0x303030, 0x008000);
		EntityMappings.addMob(EntitySiegeSkeleton.class, "entity_meme_skeleton", 0x303030, 0x000080);
		EntityMappings.addMob(EntitySiegeUFO.class, "entity_meme_ufo", 0x303030, 0x800000);
		EntityMappings.addMob(EntitySiegeCraft.class, "entity_meme_craft", 0x303030, 0x808000);
		EntityMappings.addMob(EntityGlyphid.class, "entity_glyphid", 0x724A21, 0xD2BB72);
		EntityMappings.addMob(EntityGlyphidBrawler.class, "entity_glyphid_brawler", 0x273038, 0xD2BB72);
		EntityMappings.addMob(EntityGlyphidBehemoth.class, "entity_glyphid_behemoth", 0x267F00, 0xD2BB72);
		EntityMappings.addMob(EntityGlyphidBrenda.class, "entity_glyphid_brenda", 0x4FC0C0, 0xA0A0A0);
		EntityMappings.addMob(EntityGlyphidBombardier.class, "entity_glyphid_bombardier", 0xDDD919, 0xDBB79D);
		EntityMappings.addMob(EntityGlyphidBlaster.class, "entity_glyphid_blaster", 0xD83737, 0xDBB79D);
		EntityMappings.addMob(EntityGlyphidScout.class, "entity_glyphid_scout", 0x273038, 0xB9E36B);
		EntityMappings.addMob(EntityGlyphidNuclear.class, "entity_glyphid_nuclear", 0x267F00, 0xA0A0A0);
		EntityMappings.addMob(EntityPlasticBag.class, "entity_plastic_bag", 0xd0d0d0, 0x808080);

		EntityMappings.addSpawn(EntityCreeperPhosgene.class, 5, 1, 1, EnumCreatureType.monster, BiomeGenBase.getBiomeGenArray());
		EntityMappings.addSpawn(EntityCreeperVolatile.class, 10, 1, 1, EnumCreatureType.monster, BiomeGenBase.getBiomeGenArray());
		EntityMappings.addSpawn(EntityCreeperGold.class, 1, 1, 1, EnumCreatureType.monster, BiomeGenBase.getBiomeGenArray());
		EntityMappings.addSpawn(EntityPlasticBag.class, 1, 1, 3, EnumCreatureType.waterCreature, BiomeDictionary.getBiomesForType(Type.OCEAN));
		EntityMappings.addSpawn(EntityPigeon.class, 1, 5, 10, EnumCreatureType.creature, BiomeDictionary.getBiomesForType(Type.PLAINS));
		
		int id = 0;
		for(Quartet<Class<? extends Entity>, String, Integer, Boolean> entry : EntityMappings.entityMappings) {
			EntityRegistry.registerModEntity(entry.getW(), entry.getX(), id++, MainRegistry.instance, entry.getY(), 1, entry.getZ());
		}
		
		for(Quartet<Class<? extends Entity>, String, Integer, Integer> entry : EntityMappings.mobMappings) {
			EntityRegistry.registerGlobalEntityID(entry.getW(), entry.getX(), EntityRegistry.findGlobalUniqueEntityId(), entry.getY(), entry.getZ());
		}
	}
	
	private static void addEntity(Class<? extends Entity> clazz, String name, int trackingRange) {
		EntityMappings.addEntity(clazz, name, trackingRange, true);
	}
	
	private static void addEntity(Class<? extends Entity> clazz, String name, int trackingRange, boolean velocityUpdates) {
		EntityMappings.entityMappings.add(new Quartet<>(clazz, name, trackingRange, velocityUpdates));
	}
	
	private static void addMob(Class<? extends Entity> clazz, String name, int color1, int color2) {
		EntityMappings.mobMappings.add(new Quartet<>(clazz, name, color1, color2));
	}

	
	public static void addSpawn(Class<? extends EntityLiving> entityClass, int weightedProb, int min, int max, EnumCreatureType typeOfCreature, BiomeGenBase... biomes) {
		
		for(BiomeGenBase biome : biomes) {
			
			if(biome == null) continue;
			
			List<SpawnListEntry> spawns = biome.getSpawnableList(typeOfCreature);

			for(SpawnListEntry entry : spawns) {
				// Adjusting an existing spawn entry
				if(entry.entityClass == entityClass) {
					entry.itemWeight = weightedProb;
					entry.minGroupCount = min;
					entry.maxGroupCount = max;
					break;
				}
			}

			spawns.add(new SpawnListEntry(entityClass, weightedProb, min, max));
		}
	}
}
