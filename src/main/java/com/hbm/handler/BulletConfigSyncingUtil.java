package com.hbm.handler;

import java.util.HashMap;
import java.util.Map.Entry;

import com.hbm.handler.guncfg.BulletConfigFactory;
import com.hbm.handler.guncfg.Gun12GaugeFactory;
import com.hbm.handler.guncfg.Gun20GaugeFactory;
import com.hbm.handler.guncfg.Gun22LRFactory;
import com.hbm.handler.guncfg.Gun357MagnumFactory;
import com.hbm.handler.guncfg.Gun44MagnumFactory;
import com.hbm.handler.guncfg.Gun45ACPFactory;
import com.hbm.handler.guncfg.Gun4GaugeFactory;
import com.hbm.handler.guncfg.Gun50AEFactory;
import com.hbm.handler.guncfg.Gun50BMGFactory;
import com.hbm.handler.guncfg.Gun556mmFactory;
import com.hbm.handler.guncfg.Gun5mmFactory;
import com.hbm.handler.guncfg.Gun75BoltFactory;
import com.hbm.handler.guncfg.Gun762mmFactory;
import com.hbm.handler.guncfg.Gun9mmFactory;
import com.hbm.handler.guncfg.GunCannonFactory;
import com.hbm.handler.guncfg.GunDGKFactory;
import com.hbm.handler.guncfg.GunDartFactory;
import com.hbm.handler.guncfg.GunDetonatorFactory;
import com.hbm.handler.guncfg.GunEnergyFactory;
import com.hbm.handler.guncfg.GunFatmanFactory;
import com.hbm.handler.guncfg.GunGaussFactory;
import com.hbm.handler.guncfg.GunGrenadeFactory;
import com.hbm.handler.guncfg.GunNPCFactory;
import com.hbm.handler.guncfg.GunOSIPRFactory;
import com.hbm.handler.guncfg.GunPoweredFactory;
import com.hbm.handler.guncfg.GunRocketFactory;
import com.hbm.handler.guncfg.GunRocketHomingFactory;
import com.hbm.items.ItemAmmoEnums.Ammo22LR;
import com.hbm.items.ItemAmmoEnums.Ammo44Magnum;
import com.hbm.items.ItemAmmoEnums.Ammo50AE;
import com.hbm.items.ItemAmmoEnums.Ammo50BMG;
import com.hbm.items.ItemAmmoEnums.Ammo556mm;
import com.hbm.items.ItemAmmoEnums.Ammo5mm;
import com.hbm.items.ItemAmmoEnums.Ammo9mm;
import com.hbm.items.ModItems;

public class BulletConfigSyncingUtil {

	private static HashMap<Integer, BulletConfiguration> configSet = new HashMap<>();

	static int i = 0;

	/// duplicate ids will cause wrong configs to be loaded ///
	public static int TEST_CONFIG = BulletConfigSyncingUtil.i++;
	public static int IRON_REVOLVER = BulletConfigSyncingUtil.i++;
	public static int STEEL_REVOLVER = BulletConfigSyncingUtil.i++;
	public static int LEAD_REVOLVER = BulletConfigSyncingUtil.i++;
	public static int GOLD_REVOLVER = BulletConfigSyncingUtil.i++;
	public static int CURSED_REVOLVER = BulletConfigSyncingUtil.i++;
	public static int SCHRABIDIUM_REVOLVER = BulletConfigSyncingUtil.i++;
	public static int NIGHT_REVOLVER = BulletConfigSyncingUtil.i++;
	public static int NIGHT2_REVOLVER = BulletConfigSyncingUtil.i++;
	public static int SATURNITE_REVOLVER = BulletConfigSyncingUtil.i++;
	public static int DESH_REVOLVER = BulletConfigSyncingUtil.i++;
	
	public static int IRON_HS = BulletConfigSyncingUtil.i++;
	public static int STEEL_HS = BulletConfigSyncingUtil.i++;
	public static int GOLD_HS = BulletConfigSyncingUtil.i++;
	public static int LEAD_HS = BulletConfigSyncingUtil.i++;
	public static int DESH_HS = BulletConfigSyncingUtil.i++;

	public static int G20_NORMAL = BulletConfigSyncingUtil.i++;
	public static int G20_SLUG = BulletConfigSyncingUtil.i++;
	public static int G20_FLECHETTE = BulletConfigSyncingUtil.i++;
	public static int G20_FIRE = BulletConfigSyncingUtil.i++;
	public static int G20_SHRAPNEL = BulletConfigSyncingUtil.i++;
	public static int G20_EXPLOSIVE = BulletConfigSyncingUtil.i++;
	public static int G20_CAUSTIC = BulletConfigSyncingUtil.i++;
	public static int G20_SHOCK = BulletConfigSyncingUtil.i++;
	public static int G20_WITHER = BulletConfigSyncingUtil.i++;
	public static int G20_SLEEK = BulletConfigSyncingUtil.i++;

	public static int ROCKET_NORMAL = BulletConfigSyncingUtil.i++;
	public static int ROCKET_HE = BulletConfigSyncingUtil.i++;
	public static int ROCKET_INCENDIARY = BulletConfigSyncingUtil.i++;
	public static int ROCKET_SHRAPNEL = BulletConfigSyncingUtil.i++;
	public static int ROCKET_EMP = BulletConfigSyncingUtil.i++;
	public static int ROCKET_GLARE = BulletConfigSyncingUtil.i++;
	public static int ROCKET_SLEEK = BulletConfigSyncingUtil.i++;
	public static int ROCKET_NUKE = BulletConfigSyncingUtil.i++;
	public static int ROCKET_CHAINSAW = BulletConfigSyncingUtil.i++;
	public static int ROCKET_TOXIC = BulletConfigSyncingUtil.i++;
	public static int ROCKET_PHOSPHORUS = BulletConfigSyncingUtil.i++;
	public static int ROCKET_CANISTER = BulletConfigSyncingUtil.i++;
	public static int ROCKET_ERROR = BulletConfigSyncingUtil.i++;

	public static int GRENADE_NORMAL = BulletConfigSyncingUtil.i++;
	public static int GRENADE_HE = BulletConfigSyncingUtil.i++;
	public static int GRENADE_INCENDIARY = BulletConfigSyncingUtil.i++;
	public static int GRENADE_CHEMICAL = BulletConfigSyncingUtil.i++;
	public static int GRENADE_SLEEK = BulletConfigSyncingUtil.i++;
	public static int GRENADE_CONCUSSION = BulletConfigSyncingUtil.i++;
	public static int GRENADE_FINNED = BulletConfigSyncingUtil.i++;
	public static int GRENADE_NUCLEAR = BulletConfigSyncingUtil.i++;
	public static int GRENADE_PHOSPHORUS = BulletConfigSyncingUtil.i++;
	public static int GRENADE_TRACER = BulletConfigSyncingUtil.i++;
	public static int GRENADE_KAMPF = BulletConfigSyncingUtil.i++;
	public static int GRENADE_LEADBURSTER = BulletConfigSyncingUtil.i++;

	public static int G12_NORMAL = BulletConfigSyncingUtil.i++;
	public static int G12_INCENDIARY = BulletConfigSyncingUtil.i++;
	public static int G12_SHRAPNEL = BulletConfigSyncingUtil.i++;
	public static int G12_DU = BulletConfigSyncingUtil.i++;
	public static int G12_AM = BulletConfigSyncingUtil.i++;
	public static int G12_SLEEK = BulletConfigSyncingUtil.i++;
	public static int G12_PERCUSSION = BulletConfigSyncingUtil.i++;

	public static int G12HS_NORMAL = BulletConfigSyncingUtil.i++;
	public static int G12HS_INCENDIARY = BulletConfigSyncingUtil.i++;
	public static int G12HS_SHRAPNEL = BulletConfigSyncingUtil.i++;
	public static int G12HS_DU = BulletConfigSyncingUtil.i++;
	public static int G12HS_AM = BulletConfigSyncingUtil.i++;
	public static int G12HS_SLEEK = BulletConfigSyncingUtil.i++;
	public static int G12HS_PERCUSSION = BulletConfigSyncingUtil.i++;

	public static int LR22_NORMAL = BulletConfigSyncingUtil.i++;
	public static int LR22_AP = BulletConfigSyncingUtil.i++;
	public static int LR22_NORMAL_FIRE = BulletConfigSyncingUtil.i++;
	public static int LR22_AP_FIRE = BulletConfigSyncingUtil.i++;

	public static int M44_NORMAL = BulletConfigSyncingUtil.i++;
	public static int M44_AP = BulletConfigSyncingUtil.i++;
	public static int M44_DU = BulletConfigSyncingUtil.i++;
	public static int M44_STAR = BulletConfigSyncingUtil.i++;
	public static int M44_PIP = BulletConfigSyncingUtil.i++;
	public static int M44_BJ = BulletConfigSyncingUtil.i++;
	public static int M44_SILVER = BulletConfigSyncingUtil.i++;
	public static int M44_ROCKET = BulletConfigSyncingUtil.i++;
	public static int M44_PHOSPHORUS = BulletConfigSyncingUtil.i++;

	public static int P9_NORMAL = BulletConfigSyncingUtil.i++;
	public static int P9_AP = BulletConfigSyncingUtil.i++;
	public static int P9_DU = BulletConfigSyncingUtil.i++;
	public static int P9_ROCKET = BulletConfigSyncingUtil.i++;

	public static int ACP_45 = BulletConfigSyncingUtil.i++;
	public static int ACP_45_AP = BulletConfigSyncingUtil.i++;
	public static int ACP_45_DU = BulletConfigSyncingUtil.i++;

	public static int BMG50_NORMAL = BulletConfigSyncingUtil.i++;
	public static int BMG50_INCENDIARY = BulletConfigSyncingUtil.i++;
	public static int BMG50_EXPLOSIVE = BulletConfigSyncingUtil.i++;
	public static int BMG50_AP = BulletConfigSyncingUtil.i++;
	public static int BMG50_DU = BulletConfigSyncingUtil.i++;
	public static int BMG50_STAR = BulletConfigSyncingUtil.i++;
	public static int BMG50_PHOSPHORUS = BulletConfigSyncingUtil.i++;
	public static int BMG50_SLEEK = BulletConfigSyncingUtil.i++;
	public static int BMG50_FLECHETTE_NORMAL = BulletConfigSyncingUtil.i++;
	public static int BMG50_FLECHETTE_AM = BulletConfigSyncingUtil.i++;
	public static int BMG50_FLECHETTE_PO = BulletConfigSyncingUtil.i++;
	
	public static int ROUND_LUNA_SNIPER_SABOT = BulletConfigSyncingUtil.i++;
	public static int ROUND_LUNA_SNIPER_INCENDIARY = BulletConfigSyncingUtil.i++;
	public static int ROUND_LUNA_SNIPER_EXPLOSIVE = BulletConfigSyncingUtil.i++;

	public static int R5_NORMAL = BulletConfigSyncingUtil.i++;
	public static int R5_EXPLOSIVE = BulletConfigSyncingUtil.i++;
	public static int R5_DU = BulletConfigSyncingUtil.i++;
	public static int R5_STAR = BulletConfigSyncingUtil.i++;
	public static int R5_NORMAL_BOLT = BulletConfigSyncingUtil.i++;
	public static int R5_EXPLOSIVE_BOLT = BulletConfigSyncingUtil.i++;
	public static int R5_DU_BOLT = BulletConfigSyncingUtil.i++;
	public static int R5_STAR_BOLT = BulletConfigSyncingUtil.i++;

	public static int AE50_NORMAL = BulletConfigSyncingUtil.i++;
	public static int AE50_AP = BulletConfigSyncingUtil.i++;
	public static int AE50_DU = BulletConfigSyncingUtil.i++;
	public static int AE50_STAR = BulletConfigSyncingUtil.i++;

	public static int G4_NORMAL = BulletConfigSyncingUtil.i++;
	public static int G4_SLUG = BulletConfigSyncingUtil.i++;
	public static int G4_FLECHETTE = BulletConfigSyncingUtil.i++;
	public static int G4_FLECHETTE_PHOSPHORUS = BulletConfigSyncingUtil.i++;
	public static int G4_EXPLOSIVE = BulletConfigSyncingUtil.i++;
	public static int G4_SEMTEX = BulletConfigSyncingUtil.i++;
	public static int G4_BALEFIRE = BulletConfigSyncingUtil.i++;
	public static int G4_KAMPF = BulletConfigSyncingUtil.i++;
	public static int G4_CANISTER = BulletConfigSyncingUtil.i++;
	public static int G4_CLAW = BulletConfigSyncingUtil.i++;
	public static int G4_VAMPIRE = BulletConfigSyncingUtil.i++;
	public static int G4_VOID = BulletConfigSyncingUtil.i++;
	public static int G4_TITAN = BulletConfigSyncingUtil.i++;
	public static int G4_SLEEK = BulletConfigSyncingUtil.i++;

	public static int SPECIAL_OSIPR = BulletConfigSyncingUtil.i++;
	public static int SPECIAL_OSIPR_CHARGED = BulletConfigSyncingUtil.i++;
	public static int SPECIAL_GAUSS = BulletConfigSyncingUtil.i++;
	public static int SPECIAL_GAUSS_CHARGED = BulletConfigSyncingUtil.i++;
	public static int SPECIAL_EMP = BulletConfigSyncingUtil.i++;

	public static int COIL_NORMAL = BulletConfigSyncingUtil.i++;
	public static int COIL_DU = BulletConfigSyncingUtil.i++;
	public static int COIL_RUBBER = BulletConfigSyncingUtil.i++;

	public static int FLAMER_NORMAL = BulletConfigSyncingUtil.i++;
	public static int FLAMER_NAPALM = BulletConfigSyncingUtil.i++;
	public static int FLAMER_WP = BulletConfigSyncingUtil.i++;
	public static int FLAMER_VAPORIZER = BulletConfigSyncingUtil.i++;
	public static int FLAMER_GAS = BulletConfigSyncingUtil.i++;

	public static int CRYO_NORMAL = BulletConfigSyncingUtil.i++;

	public static int FEXT_NORMAL = BulletConfigSyncingUtil.i++;
	public static int FEXT_FOAM = BulletConfigSyncingUtil.i++;
	public static int FEXT_SAND = BulletConfigSyncingUtil.i++;

	public static int R556_NORMAL = BulletConfigSyncingUtil.i++;
	public static int R556_GOLD = BulletConfigSyncingUtil.i++;
	public static int R556_PHOSPHORUS = BulletConfigSyncingUtil.i++;
	public static int R556_AP = BulletConfigSyncingUtil.i++;
	public static int R556_DU = BulletConfigSyncingUtil.i++;
	public static int R556_STAR = BulletConfigSyncingUtil.i++;
	public static int R556_SLEEK = BulletConfigSyncingUtil.i++;
	public static int R556_TRACER = BulletConfigSyncingUtil.i++;
	public static int R556_FLECHETTE = BulletConfigSyncingUtil.i++;
	public static int R556_FLECHETTE_INCENDIARY = BulletConfigSyncingUtil.i++;
	public static int R556_FLECHETTE_PHOSPHORUS = BulletConfigSyncingUtil.i++;
	public static int R556_FLECHETTE_DU = BulletConfigSyncingUtil.i++;
	public static int R556_FLECHETTE_SLEEK = BulletConfigSyncingUtil.i++;
	public static int R556_K = BulletConfigSyncingUtil.i++;
	
	public static int R762_NORMAL = BulletConfigSyncingUtil.i++;
	public static int R762_PHOSPHORUS = BulletConfigSyncingUtil.i++;
	public static int R762_AP = BulletConfigSyncingUtil.i++;
	public static int R762_DU = BulletConfigSyncingUtil.i++;
	public static int R762_TRACER = BulletConfigSyncingUtil.i++;
	public static int R762_K = BulletConfigSyncingUtil.i++;

	public static int B75_NORMAL = BulletConfigSyncingUtil.i++;
	public static int B75_INCENDIARY = BulletConfigSyncingUtil.i++;
	public static int B75_HE = BulletConfigSyncingUtil.i++;

	public static int NEEDLE_GPS = BulletConfigSyncingUtil.i++;
	public static int NEEDLE_NUKE = BulletConfigSyncingUtil.i++;
	public static int DART_NORMAL = BulletConfigSyncingUtil.i++;

	public static int G20_NORMAL_FIRE = BulletConfigSyncingUtil.i++;
	public static int G20_SHRAPNEL_FIRE = BulletConfigSyncingUtil.i++;
	public static int G20_SLUG_FIRE = BulletConfigSyncingUtil.i++;
	public static int G20_FLECHETTE_FIRE = BulletConfigSyncingUtil.i++;
	public static int G20_EXPLOSIVE_FIRE = BulletConfigSyncingUtil.i++;
	public static int G20_CAUSTIC_FIRE = BulletConfigSyncingUtil.i++;
	public static int G20_SHOCK_FIRE = BulletConfigSyncingUtil.i++;
	public static int G20_WITHER_FIRE = BulletConfigSyncingUtil.i++;

	public static int ROCKET_NORMAL_LASER = BulletConfigSyncingUtil.i++;
	public static int ROCKET_HE_LASER = BulletConfigSyncingUtil.i++;
	public static int ROCKET_INCENDIARY_LASER = BulletConfigSyncingUtil.i++;
	public static int ROCKET_SHRAPNEL_LASER = BulletConfigSyncingUtil.i++;
	public static int ROCKET_EMP_LASER = BulletConfigSyncingUtil.i++;
	public static int ROCKET_GLARE_LASER = BulletConfigSyncingUtil.i++;
	public static int ROCKET_SLEEK_LASER = BulletConfigSyncingUtil.i++;
	public static int ROCKET_NUKE_LASER = BulletConfigSyncingUtil.i++;
	public static int ROCKET_CHAINSAW_LASER = BulletConfigSyncingUtil.i++;
	public static int ROCKET_TOXIC_LASER = BulletConfigSyncingUtil.i++;
	public static int ROCKET_PHOSPHORUS_LASER = BulletConfigSyncingUtil.i++;
	
	public static int ROCKET_STINGER = BulletConfigSyncingUtil.i++;
	public static int ROCKET_STINGER_HE = BulletConfigSyncingUtil.i++;
	public static int ROCKET_STINGER_INCENDIARY = BulletConfigSyncingUtil.i++;
	public static int ROCKET_STINGER_NUCLEAR = BulletConfigSyncingUtil.i++;
	public static int ROCKET_STINGER_BONES = BulletConfigSyncingUtil.i++;

	public static int SHELL_NORMAL = BulletConfigSyncingUtil.i++;
	public static int SHELL_EXPLOSIVE = BulletConfigSyncingUtil.i++;
	public static int SHELL_AP = BulletConfigSyncingUtil.i++;
	public static int SHELL_DU = BulletConfigSyncingUtil.i++;
	public static int SHELL_W9 = BulletConfigSyncingUtil.i++;
	public static int DGK_NORMAL = BulletConfigSyncingUtil.i++;
	public static int FLA_NORMAL = BulletConfigSyncingUtil.i++;

	public static int NUKE_NORMAL = BulletConfigSyncingUtil.i++;
	public static int NUKE_LOW = BulletConfigSyncingUtil.i++;
	public static int NUKE_HIGH = BulletConfigSyncingUtil.i++;
	public static int NUKE_TOTS = BulletConfigSyncingUtil.i++;
	public static int NUKE_SAFE = BulletConfigSyncingUtil.i++;
	public static int NUKE_PUMPKIN = BulletConfigSyncingUtil.i++;
	public static int NUKE_BARREL = BulletConfigSyncingUtil.i++;
	public static int NUKE_PROTO_NORMAL = BulletConfigSyncingUtil.i++;
	public static int NUKE_PROTO_LOW = BulletConfigSyncingUtil.i++;
	public static int NUKE_PROTO_HIGH = BulletConfigSyncingUtil.i++;
	public static int NUKE_PROTO_TOTS = BulletConfigSyncingUtil.i++;
	public static int NUKE_PROTO_SAFE = BulletConfigSyncingUtil.i++;
	public static int NUKE_PROTO_PUMPKIN = BulletConfigSyncingUtil.i++;
	public static int NUKE_MIRV_NORMAL = BulletConfigSyncingUtil.i++;
	public static int NUKE_MIRV_LOW = BulletConfigSyncingUtil.i++;
	public static int NUKE_MIRV_HIGH = BulletConfigSyncingUtil.i++;
	public static int NUKE_MIRV_SAFE = BulletConfigSyncingUtil.i++;
	public static int NUKE_MIRV_SPECIAL = BulletConfigSyncingUtil.i++;

	public static int NUKE_AMAT = BulletConfigSyncingUtil.i++;
	
	public static int TWR_RAY = BulletConfigSyncingUtil.i++;
	public static int HLR_NORMAL = BulletConfigSyncingUtil.i++;
	public static int HLR_ALT = BulletConfigSyncingUtil.i++;

	public static int ZOMG_BOLT = BulletConfigSyncingUtil.i++;
	public static int DET_BOLT = BulletConfigSyncingUtil.i++;

	public static int TURBINE = BulletConfigSyncingUtil.i++;
	
	public static int GLASS_EMRADIO = BulletConfigSyncingUtil.i++;
	public static int GLASS_EMMICRO = BulletConfigSyncingUtil.i++;
	public static int GLASS_EMIR = BulletConfigSyncingUtil.i++;
	public static int GLASS_EMVISIBLE = BulletConfigSyncingUtil.i++;
	public static int GLASS_EMUV = BulletConfigSyncingUtil.i++;
	public static int GLASS_EMXRAY = BulletConfigSyncingUtil.i++;
	public static int GLASS_EMGAMMA = BulletConfigSyncingUtil.i++;

	public static int CHL_LR22 = BulletConfigSyncingUtil.i++;
	public static int CHL_LR22_FIRE = BulletConfigSyncingUtil.i++;
	public static int CHL_M44 = BulletConfigSyncingUtil.i++;
	public static int CHL_P9 = BulletConfigSyncingUtil.i++;
	public static int CHL_BMG50 = BulletConfigSyncingUtil.i++;
	public static int CHL_R5 = BulletConfigSyncingUtil.i++;
	public static int CHL_R5_BOLT = BulletConfigSyncingUtil.i++;
	public static int CHL_AE50 = BulletConfigSyncingUtil.i++;
	public static int CHL_R556 = BulletConfigSyncingUtil.i++;
	public static int CHL_R556_FLECHETTE = BulletConfigSyncingUtil.i++;

	public static int MASKMAN_BULLET = BulletConfigSyncingUtil.i++;
	public static int MASKMAN_ORB = BulletConfigSyncingUtil.i++;
	public static int MASKMAN_BOLT = BulletConfigSyncingUtil.i++;
	public static int MASKMAN_ROCKET = BulletConfigSyncingUtil.i++;
	public static int MASKMAN_TRACER = BulletConfigSyncingUtil.i++;
	public static int MASKMAN_METEOR = BulletConfigSyncingUtil.i++;

	public static int WORM_BOLT = BulletConfigSyncingUtil.i++;
	public static int WORM_LASER = BulletConfigSyncingUtil.i++;
	
	public static int UFO_ROCKET = BulletConfigSyncingUtil.i++;

	public static void loadConfigsForSync() {

		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.TEST_CONFIG, BulletConfigFactory.getTestConfig());

		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.IRON_REVOLVER, Gun357MagnumFactory.getRevIronConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.STEEL_REVOLVER, Gun357MagnumFactory.getRevLeadConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.LEAD_REVOLVER, Gun357MagnumFactory.getRevNuclearConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.GOLD_REVOLVER, Gun357MagnumFactory.getRevGoldConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.CURSED_REVOLVER, Gun357MagnumFactory.getRevCursedConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.SCHRABIDIUM_REVOLVER, Gun357MagnumFactory.getRevSchrabidiumConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.NIGHT_REVOLVER, Gun357MagnumFactory.getRevNightmare1Config());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.NIGHT2_REVOLVER, Gun357MagnumFactory.getRevNightmare2Config());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.SATURNITE_REVOLVER, Gun357MagnumFactory.getRevLeadConfig().setToFire(3));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.DESH_REVOLVER, Gun357MagnumFactory.getRevDeshConfig());

		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.IRON_HS, Gun357MagnumFactory.getRevIronConfig().setHeadshot(3F));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.STEEL_HS, Gun357MagnumFactory.getRevCursedConfig().setHeadshot(3F));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.GOLD_HS, Gun357MagnumFactory.getRevGoldConfig().setHeadshot(3F));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.LEAD_HS, Gun357MagnumFactory.getRevLeadConfig().setHeadshot(3F));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.DESH_HS, Gun357MagnumFactory.getRevDeshConfig().setHeadshot(3F));

		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G20_NORMAL, Gun20GaugeFactory.get20GaugeConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G20_SLUG, Gun20GaugeFactory.get20GaugeSlugConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G20_FLECHETTE, Gun20GaugeFactory.get20GaugeFlechetteConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G20_FIRE, Gun20GaugeFactory.get20GaugeFireConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G20_SHRAPNEL, Gun20GaugeFactory.get20GaugeShrapnelConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G20_EXPLOSIVE, Gun20GaugeFactory.get20GaugeExplosiveConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G20_CAUSTIC, Gun20GaugeFactory.get20GaugeCausticConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G20_SHOCK, Gun20GaugeFactory.get20GaugeShockConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G20_WITHER, Gun20GaugeFactory.get20GaugeWitherConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G20_SLEEK, Gun20GaugeFactory.get20GaugeSleekConfig());

		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROCKET_NORMAL, GunRocketFactory.getRocketConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROCKET_HE, GunRocketFactory.getRocketHEConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROCKET_INCENDIARY, GunRocketFactory.getRocketIncendiaryConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROCKET_PHOSPHORUS, GunRocketFactory.getRocketPhosphorusConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROCKET_SHRAPNEL, GunRocketFactory.getRocketShrapnelConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROCKET_EMP, GunRocketFactory.getRocketEMPConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROCKET_GLARE, GunRocketFactory.getRocketGlareConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROCKET_SLEEK, GunRocketFactory.getRocketSleekConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROCKET_NUKE, GunRocketFactory.getRocketNukeConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROCKET_CHAINSAW, GunRocketFactory.getRocketRPCConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROCKET_TOXIC, GunRocketFactory.getRocketChlorineConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROCKET_CANISTER, GunRocketFactory.getRocketCanisterConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROCKET_ERROR, GunRocketFactory.getRocketErrorConfig());
		
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROCKET_STINGER, GunRocketHomingFactory.getRocketStingerConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROCKET_STINGER_HE, GunRocketHomingFactory.getRocketStingerHEConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROCKET_STINGER_INCENDIARY, GunRocketHomingFactory.getRocketStingerIncendiaryConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROCKET_STINGER_NUCLEAR, GunRocketHomingFactory.getRocketStingerNuclearConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROCKET_STINGER_BONES, GunRocketHomingFactory.getRocketStingerBonesConfig());

		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.GRENADE_NORMAL, GunGrenadeFactory.getGrenadeConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.GRENADE_HE, GunGrenadeFactory.getGrenadeHEConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.GRENADE_INCENDIARY, GunGrenadeFactory.getGrenadeIncendirayConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.GRENADE_PHOSPHORUS, GunGrenadeFactory.getGrenadePhosphorusConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.GRENADE_CHEMICAL, GunGrenadeFactory.getGrenadeChlorineConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.GRENADE_SLEEK, GunGrenadeFactory.getGrenadeSleekConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.GRENADE_CONCUSSION, GunGrenadeFactory.getGrenadeConcussionConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.GRENADE_FINNED, GunGrenadeFactory.getGrenadeFinnedConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.GRENADE_NUCLEAR, GunGrenadeFactory.getGrenadeNuclearConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.GRENADE_TRACER, GunGrenadeFactory.getGrenadeTracerConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.GRENADE_KAMPF, GunGrenadeFactory.getGrenadeKampfConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.GRENADE_LEADBURSTER, GunGrenadeFactory.getGrenadeLeadbursterConfig());

		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G12_NORMAL, Gun12GaugeFactory.get12GaugeConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G12_INCENDIARY, Gun12GaugeFactory.get12GaugeFireConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G12_SHRAPNEL, Gun12GaugeFactory.get12GaugeShrapnelConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G12_DU, Gun12GaugeFactory.get12GaugeDUConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G12_AM, Gun12GaugeFactory.get12GaugeAMConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G12_SLEEK, Gun12GaugeFactory.get12GaugeSleekConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G12_PERCUSSION, Gun12GaugeFactory.get12GaugePercussionConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G12HS_NORMAL, Gun12GaugeFactory.get12GaugeConfig().setHeadshot(2F));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G12HS_INCENDIARY, Gun12GaugeFactory.get12GaugeFireConfig().setHeadshot(2F));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G12HS_SHRAPNEL, Gun12GaugeFactory.get12GaugeShrapnelConfig().setHeadshot(2F));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G12HS_DU, Gun12GaugeFactory.get12GaugeDUConfig().setHeadshot(2F));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G12HS_AM, Gun12GaugeFactory.get12GaugeAMConfig().setHeadshot(2F));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G12HS_SLEEK, Gun12GaugeFactory.get12GaugeSleekConfig().setHeadshot(2F));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G12HS_PERCUSSION, Gun12GaugeFactory.get12GaugePercussionConfig().setHeadshot(2F));

		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.LR22_NORMAL, Gun22LRFactory.get22LRConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.LR22_AP, Gun22LRFactory.get22LRAPConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.LR22_NORMAL_FIRE, Gun22LRFactory.get22LRConfig().setToFire(3));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.LR22_AP_FIRE, Gun22LRFactory.get22LRAPConfig().setToFire(3));

		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.M44_NORMAL, Gun44MagnumFactory.getNoPipConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.M44_AP, Gun44MagnumFactory.getNoPipAPConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.M44_DU, Gun44MagnumFactory.getNoPipDUConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.M44_PHOSPHORUS, Gun44MagnumFactory.getPhosphorusConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.M44_STAR, Gun44MagnumFactory.getNoPipStarConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.M44_PIP, Gun44MagnumFactory.getPipConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.M44_BJ, Gun44MagnumFactory.getBJConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.M44_SILVER, Gun44MagnumFactory.getSilverStormConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.M44_ROCKET, Gun44MagnumFactory.getRocketConfig());

		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.P9_NORMAL, Gun9mmFactory.get9mmConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.P9_AP, Gun9mmFactory.get9mmAPConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.P9_DU, Gun9mmFactory.get9mmDUConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.P9_ROCKET, Gun9mmFactory.get9mmRocketConfig());

		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ACP_45, Gun45ACPFactory.get45AutoConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ACP_45_AP, Gun45ACPFactory.get45AutoAPConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ACP_45_DU, Gun45ACPFactory.get45AutoDUConfig());
		
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.BMG50_NORMAL, Gun50BMGFactory.get50BMGConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.BMG50_INCENDIARY, Gun50BMGFactory.get50BMGFireConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.BMG50_PHOSPHORUS, Gun50BMGFactory.get50BMGPhosphorusConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.BMG50_EXPLOSIVE, Gun50BMGFactory.get50BMGExplosiveConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.BMG50_AP, Gun50BMGFactory.get50BMGAPConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.BMG50_DU, Gun50BMGFactory.get50BMGDUConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.BMG50_STAR, Gun50BMGFactory.get50BMGStarConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.BMG50_SLEEK, Gun50BMGFactory.get50BMGSleekConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.BMG50_FLECHETTE_NORMAL, Gun50BMGFactory.get50BMGFlechetteConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.BMG50_FLECHETTE_AM, Gun50BMGFactory.get50BMGFlechetteAMConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.BMG50_FLECHETTE_PO, Gun50BMGFactory.get50BMGFlechettePOConfig());

		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROUND_LUNA_SNIPER_SABOT, Gun50BMGFactory.getLunaticSabotRound());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROUND_LUNA_SNIPER_INCENDIARY, Gun50BMGFactory.getLunaticIncendiaryRound());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROUND_LUNA_SNIPER_EXPLOSIVE, Gun50BMGFactory.getLunaticExplosiveRound());
		
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.R5_NORMAL, Gun5mmFactory.get5mmConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.R5_EXPLOSIVE, Gun5mmFactory.get5mmExplosiveConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.R5_DU, Gun5mmFactory.get5mmDUConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.R5_STAR, Gun5mmFactory.get5mmStarConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.R5_NORMAL_BOLT, Gun5mmFactory.get5mmConfig().setToBolt(BulletConfiguration.BOLT_LACUNAE));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.R5_EXPLOSIVE_BOLT, Gun5mmFactory.get5mmExplosiveConfig().setToBolt(BulletConfiguration.BOLT_LACUNAE));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.R5_DU_BOLT, Gun5mmFactory.get5mmDUConfig().setToBolt(BulletConfiguration.BOLT_LACUNAE));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.R5_STAR_BOLT, Gun5mmFactory.get5mmStarConfig().setToBolt(BulletConfiguration.BOLT_LACUNAE));

		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.AE50_NORMAL, Gun50AEFactory.get50AEConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.AE50_AP, Gun50AEFactory.get50APConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.AE50_DU, Gun50AEFactory.get50DUConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.AE50_STAR, Gun50AEFactory.get50StarConfig());

		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G4_NORMAL, Gun4GaugeFactory.get4GaugeConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G4_SLUG, Gun4GaugeFactory.get4GaugeSlugConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G4_FLECHETTE, Gun4GaugeFactory.get4GaugeFlechetteConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G4_FLECHETTE_PHOSPHORUS, Gun4GaugeFactory.get4GaugeFlechettePhosphorusConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G4_EXPLOSIVE, Gun4GaugeFactory.get4GaugeExplosiveConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G4_SEMTEX, Gun4GaugeFactory.get4GaugeMiningConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G4_BALEFIRE, Gun4GaugeFactory.get4GaugeBalefireConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G4_KAMPF, Gun4GaugeFactory.getGrenadeKampfConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G4_CANISTER, Gun4GaugeFactory.getGrenadeCanisterConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G4_CLAW, Gun4GaugeFactory.get4GaugeClawConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G4_VAMPIRE, Gun4GaugeFactory.get4GaugeVampireConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G4_VOID, Gun4GaugeFactory.get4GaugeVoidConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G4_TITAN, Gun4GaugeFactory.get4GaugeQuackConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G4_SLEEK, Gun4GaugeFactory.get4GaugeSleekConfig());

		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.SPECIAL_OSIPR, GunOSIPRFactory.getPulseConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.SPECIAL_OSIPR_CHARGED, GunOSIPRFactory.getPulseChargedConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.SPECIAL_GAUSS, GunGaussFactory.getGaussConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.SPECIAL_GAUSS_CHARGED, GunGaussFactory.getAltConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.SPECIAL_EMP, GunEnergyFactory.getOrbusConfig());

		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.COIL_NORMAL, GunEnergyFactory.getCoilConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.COIL_DU, GunEnergyFactory.getCoilDUConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.COIL_RUBBER, GunEnergyFactory.getCoilRubberConfig());

		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.FLAMER_NORMAL, GunEnergyFactory.getFlameConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.FLAMER_NAPALM, GunEnergyFactory.getNapalmConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.FLAMER_WP, GunEnergyFactory.getPhosphorusConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.FLAMER_VAPORIZER, GunEnergyFactory.getVaporizerConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.FLAMER_GAS, GunEnergyFactory.getGasConfig());
		
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.CRYO_NORMAL, GunEnergyFactory.getCryoConfig());
		
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.FEXT_NORMAL, GunEnergyFactory.getFextConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.FEXT_FOAM, GunEnergyFactory.getFextFoamConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.FEXT_SAND, GunEnergyFactory.getFextSandConfig());

		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.R556_NORMAL, Gun556mmFactory.get556Config());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.R556_GOLD, Gun556mmFactory.get556GoldConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.R556_PHOSPHORUS, Gun556mmFactory.get556PhosphorusConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.R556_AP, Gun556mmFactory.get556APConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.R556_DU, Gun556mmFactory.get556DUConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.R556_STAR, Gun556mmFactory.get556StarConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.R556_SLEEK, Gun556mmFactory.get556SleekConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.R556_TRACER, Gun556mmFactory.get556TracerConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.R556_FLECHETTE, Gun556mmFactory.get556FlechetteConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.R556_FLECHETTE_INCENDIARY, Gun556mmFactory.get556FlechetteIncendiaryConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.R556_FLECHETTE_PHOSPHORUS, Gun556mmFactory.get556FlechettePhosphorusConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.R556_FLECHETTE_DU, Gun556mmFactory.get556FlechetteDUConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.R556_FLECHETTE_SLEEK, Gun556mmFactory.get556FlechetteSleekConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.R556_K, Gun556mmFactory.get556KConfig());

		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.R762_NORMAL, Gun762mmFactory.get762NATOConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.R762_PHOSPHORUS, Gun762mmFactory.get762WPConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.R762_AP, Gun762mmFactory.get762APConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.R762_DU, Gun762mmFactory.get762DUConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.R762_TRACER, Gun762mmFactory.get762TracerConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.R762_K, Gun762mmFactory.get762BlankConfig());
		
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.B75_NORMAL, Gun75BoltFactory.get75BoltConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.B75_INCENDIARY, Gun75BoltFactory.get75BoltIncConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.B75_HE, Gun75BoltFactory.get75BoltHEConfig());

		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.NEEDLE_GPS, GunDartFactory.getGPSConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.NEEDLE_NUKE, GunDartFactory.getNukeConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.DART_NORMAL, GunDartFactory.getNERFConfig());

		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G20_NORMAL_FIRE, Gun20GaugeFactory.get20GaugeConfig().setToFire(3));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G20_SHRAPNEL_FIRE, Gun20GaugeFactory.get20GaugeShrapnelConfig().setToFire(3));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G20_SLUG_FIRE, Gun20GaugeFactory.get20GaugeSlugConfig().setToFire(3));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G20_FLECHETTE_FIRE, Gun20GaugeFactory.get20GaugeFlechetteConfig().setToFire(3));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G20_EXPLOSIVE_FIRE, Gun20GaugeFactory.get20GaugeExplosiveConfig().setToFire(3));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G20_CAUSTIC_FIRE, Gun20GaugeFactory.get20GaugeCausticConfig().setToFire(3));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G20_SHOCK_FIRE, Gun20GaugeFactory.get20GaugeShockConfig().setToFire(3));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.G20_WITHER_FIRE, Gun20GaugeFactory.get20GaugeWitherConfig().setToFire(3));

		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROCKET_NORMAL_LASER, GunRocketFactory.getRocketConfig().setToGuided());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROCKET_HE_LASER, GunRocketFactory.getRocketHEConfig().setToGuided());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROCKET_INCENDIARY_LASER, GunRocketFactory.getRocketIncendiaryConfig().setToGuided());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROCKET_PHOSPHORUS_LASER, GunRocketFactory.getRocketPhosphorusConfig().setToGuided());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROCKET_SHRAPNEL_LASER, GunRocketFactory.getRocketShrapnelConfig().setToGuided());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROCKET_EMP_LASER, GunRocketFactory.getRocketEMPConfig().setToGuided());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROCKET_GLARE_LASER, GunRocketFactory.getRocketGlareConfig().setToGuided());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROCKET_SLEEK_LASER, GunRocketFactory.getRocketSleekConfig().setToGuided());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROCKET_NUKE_LASER, GunRocketFactory.getRocketNukeConfig().setToGuided());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROCKET_CHAINSAW_LASER, GunRocketFactory.getRocketRPCConfig().setToGuided());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ROCKET_TOXIC_LASER, GunRocketFactory.getRocketChlorineConfig().setToGuided());

		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.SHELL_NORMAL, GunCannonFactory.getShellConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.SHELL_EXPLOSIVE, GunCannonFactory.getShellExplosiveConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.SHELL_AP, GunCannonFactory.getShellAPConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.SHELL_DU, GunCannonFactory.getShellDUConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.SHELL_W9, GunCannonFactory.getShellW9Config());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.DGK_NORMAL, GunDGKFactory.getDGKConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.FLA_NORMAL, GunEnergyFactory.getTurretConfig());

		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.NUKE_NORMAL, GunFatmanFactory.getNukeConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.NUKE_LOW, GunFatmanFactory.getNukeLowConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.NUKE_HIGH, GunFatmanFactory.getNukeHighConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.NUKE_TOTS, GunFatmanFactory.getNukeTotsConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.NUKE_SAFE, GunFatmanFactory.getNukeSafeConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.NUKE_PUMPKIN, GunFatmanFactory.getNukePumpkinConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.NUKE_BARREL, GunFatmanFactory.getNukeBarrelConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.NUKE_PROTO_NORMAL, GunFatmanFactory.getNukeConfig().accuracyMod(20F));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.NUKE_PROTO_LOW, GunFatmanFactory.getNukeLowConfig().accuracyMod(20F));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.NUKE_PROTO_HIGH, GunFatmanFactory.getNukeHighConfig().accuracyMod(20F));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.NUKE_PROTO_TOTS, GunFatmanFactory.getNukeTotsConfig().accuracyMod(20F));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.NUKE_PROTO_SAFE, GunFatmanFactory.getNukeSafeConfig().accuracyMod(20F));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.NUKE_PROTO_PUMPKIN, GunFatmanFactory.getNukePumpkinConfig().accuracyMod(20F));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.NUKE_MIRV_NORMAL, GunFatmanFactory.getMirvConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.NUKE_MIRV_LOW, GunFatmanFactory.getMirvLowConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.NUKE_MIRV_HIGH, GunFatmanFactory.getMirvHighConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.NUKE_MIRV_SAFE, GunFatmanFactory.getMirvSafeConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.NUKE_MIRV_SPECIAL, GunFatmanFactory.getMirvSpecialConfig());

		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.NUKE_AMAT, GunFatmanFactory.getBalefireConfig());
		
		//configSet.put(TWR_RAY, GunEnergyFactory.getSingConfig());
		//configSet.put(HLR_NORMAL, GunEnergyFactory.getHLRPrecisionConfig());
		//configSet.put(HLR_ALT, GunEnergyFactory.getHLRScatterConfig());

		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.ZOMG_BOLT, GunEnergyFactory.getZOMGBoltConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.DET_BOLT, GunDetonatorFactory.getLaserConfig());

		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.TURBINE, GunEnergyFactory.getTurbineConfig());
		
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.GLASS_EMRADIO, GunPoweredFactory.getEMRadioConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.GLASS_EMMICRO, GunPoweredFactory.getEMMicroConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.GLASS_EMIR, GunPoweredFactory.getEMInfraredConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.GLASS_EMVISIBLE, GunPoweredFactory.getEMVisibleConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.GLASS_EMUV, GunPoweredFactory.getEMUVConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.GLASS_EMXRAY, GunPoweredFactory.getEMXrayConfig());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.GLASS_EMGAMMA, GunPoweredFactory.getEMGammaConfig());
		

		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.CHL_LR22, Gun22LRFactory.get22LRConfig().setToHoming(ModItems.ammo_22lr.stackFromEnum(Ammo22LR.CHLOROPHYTE)));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.CHL_LR22_FIRE, Gun22LRFactory.get22LRConfig().setToFire(3).setToHoming(ModItems.ammo_22lr.stackFromEnum(Ammo22LR.CHLOROPHYTE)));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.CHL_M44, Gun44MagnumFactory.getNoPipConfig().setToHoming(ModItems.ammo_44.stackFromEnum(Ammo44Magnum.CHLOROPHYTE)));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.CHL_P9, Gun9mmFactory.get9mmConfig().setToHoming(ModItems.ammo_9mm.stackFromEnum(Ammo9mm.CHLOROPHYTE)));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.CHL_BMG50, Gun50BMGFactory.get50BMGConfig().setToHoming(ModItems.ammo_50bmg.stackFromEnum(Ammo50BMG.CHLOROPHYTE)));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.CHL_R5, Gun5mmFactory.get5mmConfig().setToHoming(ModItems.ammo_5mm.stackFromEnum(Ammo5mm.CHLOROPHYTE)));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.CHL_R5_BOLT, Gun5mmFactory.get5mmConfig().setToBolt(BulletConfiguration.BOLT_LACUNAE).setToHoming(ModItems.ammo_5mm.stackFromEnum(Ammo5mm.CHLOROPHYTE)));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.CHL_AE50, Gun50AEFactory.get50AEConfig().setToHoming(ModItems.ammo_50ae.stackFromEnum(Ammo50AE.CHLOROPHYTE)));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.CHL_R556, Gun556mmFactory.get556Config().setToHoming(ModItems.ammo_556.stackFromEnum(Ammo556mm.CHLOROPHYTE)));
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.CHL_R556_FLECHETTE, Gun556mmFactory.get556FlechetteConfig().setToHoming(ModItems.ammo_556.stackFromEnum(Ammo556mm.FLECHETTE_CHLOROPHYTE)));

		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.MASKMAN_BULLET, GunNPCFactory.getMaskmanBullet());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.MASKMAN_ORB, GunNPCFactory.getMaskmanOrb());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.MASKMAN_BOLT, GunNPCFactory.getMaskmanBolt());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.MASKMAN_ROCKET, GunNPCFactory.getMaskmanRocket());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.MASKMAN_TRACER, GunNPCFactory.getMaskmanTracer());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.MASKMAN_METEOR, GunNPCFactory.getMaskmanMeteor());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.WORM_BOLT, GunNPCFactory.getWormBolt());
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.WORM_LASER, GunNPCFactory.getWormHeadBolt());
		
		BulletConfigSyncingUtil.configSet.put(BulletConfigSyncingUtil.UFO_ROCKET, GunNPCFactory.getRocketUFOConfig());
	}

	public static BulletConfiguration pullConfig(int key) {
		return BulletConfigSyncingUtil.configSet.get(key);
	}

	public static int getKey(BulletConfiguration config) {

		for(Entry<Integer, BulletConfiguration> e : BulletConfigSyncingUtil.configSet.entrySet()) {

			if(e.getValue() == config)
				return e.getKey();
		}

		return -1;
	}

}
