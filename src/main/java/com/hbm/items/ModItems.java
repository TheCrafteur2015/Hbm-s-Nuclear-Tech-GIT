package com.hbm.items;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.VersatileConfig;
import com.hbm.handler.BucketHandler;
import com.hbm.handler.ToolAbility;
import com.hbm.handler.ToolAbility.LuckAbility;
import com.hbm.handler.WeaponAbility;
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
import com.hbm.handler.guncfg.GunDartFactory;
import com.hbm.handler.guncfg.GunDetonatorFactory;
import com.hbm.handler.guncfg.GunEnergyFactory;
import com.hbm.handler.guncfg.GunFatmanFactory;
import com.hbm.handler.guncfg.GunGaussFactory;
import com.hbm.handler.guncfg.GunGrenadeFactory;
import com.hbm.handler.guncfg.GunOSIPRFactory;
import com.hbm.handler.guncfg.GunPoweredFactory;
import com.hbm.handler.guncfg.GunRocketFactory;
import com.hbm.handler.guncfg.GunRocketHomingFactory;
import com.hbm.interfaces.ICustomWarhead.SaltedFuel.HalfLifeType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.items.ItemAmmoEnums.Ammo12Gauge;
import com.hbm.items.ItemAmmoEnums.Ammo20Gauge;
import com.hbm.items.ItemAmmoEnums.Ammo22LR;
import com.hbm.items.ItemAmmoEnums.Ammo240Shell;
import com.hbm.items.ItemAmmoEnums.Ammo357Magnum;
import com.hbm.items.ItemAmmoEnums.Ammo44Magnum;
import com.hbm.items.ItemAmmoEnums.Ammo45ACP;
import com.hbm.items.ItemAmmoEnums.Ammo4Gauge;
import com.hbm.items.ItemAmmoEnums.Ammo50AE;
import com.hbm.items.ItemAmmoEnums.Ammo50BMG;
import com.hbm.items.ItemAmmoEnums.Ammo556mm;
import com.hbm.items.ItemAmmoEnums.Ammo5mm;
import com.hbm.items.ItemAmmoEnums.Ammo75Bolt;
import com.hbm.items.ItemAmmoEnums.Ammo762NATO;
import com.hbm.items.ItemAmmoEnums.Ammo9mm;
import com.hbm.items.ItemAmmoEnums.AmmoCoilgun;
import com.hbm.items.ItemAmmoEnums.AmmoDart;
import com.hbm.items.ItemAmmoEnums.AmmoFatman;
import com.hbm.items.ItemAmmoEnums.AmmoFireExt;
import com.hbm.items.ItemAmmoEnums.AmmoFlamethrower;
import com.hbm.items.ItemAmmoEnums.AmmoGrenade;
import com.hbm.items.ItemAmmoEnums.AmmoLunaticSniper;
import com.hbm.items.ItemAmmoEnums.AmmoMisc;
import com.hbm.items.ItemAmmoEnums.AmmoRocket;
import com.hbm.items.ItemAmmoEnums.AmmoStinger;
import com.hbm.items.ItemEnums.EnumAshType;
import com.hbm.items.ItemEnums.EnumBriquetteType;
import com.hbm.items.ItemEnums.EnumCokeType;
import com.hbm.items.ItemEnums.EnumLegendaryType;
import com.hbm.items.ItemEnums.EnumPlantType;
import com.hbm.items.ItemEnums.EnumTarType;
import com.hbm.items.armor.ArmorAJR;
import com.hbm.items.armor.ArmorAJRO;
import com.hbm.items.armor.ArmorAshGlasses;
import com.hbm.items.armor.ArmorAustralium;
import com.hbm.items.armor.ArmorBJ;
import com.hbm.items.armor.ArmorBJJetpack;
import com.hbm.items.armor.ArmorBismuth;
import com.hbm.items.armor.ArmorDNT;
import com.hbm.items.armor.ArmorDesh;
import com.hbm.items.armor.ArmorDiesel;
import com.hbm.items.armor.ArmorDigamma;
import com.hbm.items.armor.ArmorEnvsuit;
import com.hbm.items.armor.ArmorEuphemium;
import com.hbm.items.armor.ArmorFSB;
import com.hbm.items.armor.ArmorGasMask;
import com.hbm.items.armor.ArmorHEV;
import com.hbm.items.armor.ArmorHat;
import com.hbm.items.armor.ArmorHazmat;
import com.hbm.items.armor.ArmorHazmatMask;
import com.hbm.items.armor.ArmorLiquidator;
import com.hbm.items.armor.ArmorLiquidatorMask;
import com.hbm.items.armor.ArmorModel;
import com.hbm.items.armor.ArmorNo9;
import com.hbm.items.armor.ArmorRPA;
import com.hbm.items.armor.ArmorT45;
import com.hbm.items.armor.ArmorTest;
import com.hbm.items.armor.ArmorTrenchmaster;
import com.hbm.items.armor.IArmorDisableModel.EnumPlayerPart;
import com.hbm.items.armor.ItemModAuto;
import com.hbm.items.armor.ItemModBandaid;
import com.hbm.items.armor.ItemModBathwater;
import com.hbm.items.armor.ItemModCharm;
import com.hbm.items.armor.ItemModCladding;
import com.hbm.items.armor.ItemModDefuser;
import com.hbm.items.armor.ItemModGasmask;
import com.hbm.items.armor.ItemModHealth;
import com.hbm.items.armor.ItemModInk;
import com.hbm.items.armor.ItemModInsert;
import com.hbm.items.armor.ItemModIron;
import com.hbm.items.armor.ItemModKnife;
import com.hbm.items.armor.ItemModLens;
import com.hbm.items.armor.ItemModLodestone;
import com.hbm.items.armor.ItemModMedal;
import com.hbm.items.armor.ItemModMilk;
import com.hbm.items.armor.ItemModMorningGlory;
import com.hbm.items.armor.ItemModNightVision;
import com.hbm.items.armor.ItemModObsidian;
import com.hbm.items.armor.ItemModPads;
import com.hbm.items.armor.ItemModPolish;
import com.hbm.items.armor.ItemModQuartz;
import com.hbm.items.armor.ItemModRevive;
import com.hbm.items.armor.ItemModSensor;
import com.hbm.items.armor.ItemModSerum;
import com.hbm.items.armor.ItemModServos;
import com.hbm.items.armor.ItemModShackles;
import com.hbm.items.armor.ItemModTesla;
import com.hbm.items.armor.ItemModTwoKick;
import com.hbm.items.armor.ItemModV1;
import com.hbm.items.armor.ItemModWD40;
import com.hbm.items.armor.JetpackBooster;
import com.hbm.items.armor.JetpackBreak;
import com.hbm.items.armor.JetpackRegular;
import com.hbm.items.armor.JetpackVectorized;
import com.hbm.items.armor.MaskOfInfamy;
import com.hbm.items.armor.ModArmor;
import com.hbm.items.armor.WingsMurk;
import com.hbm.items.bomb.ItemFleija;
import com.hbm.items.bomb.ItemMissileShuttle;
import com.hbm.items.bomb.ItemN2;
import com.hbm.items.bomb.ItemSolinium;
import com.hbm.items.food.ItemAppleEuphemium;
import com.hbm.items.food.ItemAppleSchrabidium;
import com.hbm.items.food.ItemBDCL;
import com.hbm.items.food.ItemCanteen;
import com.hbm.items.food.ItemConserve;
import com.hbm.items.food.ItemCottonCandy;
import com.hbm.items.food.ItemCrayon;
import com.hbm.items.food.ItemEnergy;
import com.hbm.items.food.ItemFlask;
import com.hbm.items.food.ItemLemon;
import com.hbm.items.food.ItemMarshmallow;
import com.hbm.items.food.ItemMuchoMango;
import com.hbm.items.food.ItemNugget;
import com.hbm.items.food.ItemPancake;
import com.hbm.items.food.ItemPill;
import com.hbm.items.food.ItemSchnitzelVegan;
import com.hbm.items.food.ItemTemFlakes;
import com.hbm.items.food.ItemWaffle;
import com.hbm.items.machine.ItemAssemblyTemplate;
import com.hbm.items.machine.ItemBattery;
import com.hbm.items.machine.ItemBlades;
import com.hbm.items.machine.ItemBreedingRod;
import com.hbm.items.machine.ItemCanister;
import com.hbm.items.machine.ItemCapacitor;
import com.hbm.items.machine.ItemCassette;
import com.hbm.items.machine.ItemCatalyst;
import com.hbm.items.machine.ItemChemicalDye;
import com.hbm.items.machine.ItemChemistryIcon;
import com.hbm.items.machine.ItemChemistryTemplate;
import com.hbm.items.machine.ItemCrucibleTemplate;
import com.hbm.items.machine.ItemDepletedFuel;
import com.hbm.items.machine.ItemDrillbit;
import com.hbm.items.machine.ItemFELCrystal;
import com.hbm.items.machine.ItemFELCrystal.EnumWavelengths;
import com.hbm.items.machine.ItemFluidDuct;
import com.hbm.items.machine.ItemFluidIDMulti;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.items.machine.ItemFluidIdentifier;
import com.hbm.items.machine.ItemFluidTank;
import com.hbm.items.machine.ItemGasTank;
import com.hbm.items.machine.ItemGear;
import com.hbm.items.machine.ItemInfiniteFluid;
import com.hbm.items.machine.ItemLens;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.items.machine.ItemMetaUpgrade;
import com.hbm.items.machine.ItemMold;
import com.hbm.items.machine.ItemPWRFuel;
import com.hbm.items.machine.ItemPWRFuel.EnumPWRFuel;
import com.hbm.items.machine.ItemPileRod;
import com.hbm.items.machine.ItemPistons;
import com.hbm.items.machine.ItemPlateFuel;
import com.hbm.items.machine.ItemPlateFuel.FunctionEnum;
import com.hbm.items.machine.ItemRBMKLid;
import com.hbm.items.machine.ItemRBMKPellet;
import com.hbm.items.machine.ItemRBMKRod;
import com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc;
import com.hbm.items.machine.ItemRBMKRod.EnumDepleteFunc;
import com.hbm.items.machine.ItemRTGPellet;
import com.hbm.items.machine.ItemRTGPelletDepleted;
import com.hbm.items.machine.ItemRTGPelletDepleted.DepletedRTGMaterial;
import com.hbm.items.machine.ItemReactorSensor;
import com.hbm.items.machine.ItemSatChip;
import com.hbm.items.machine.ItemScraps;
import com.hbm.items.machine.ItemSelfcharger;
import com.hbm.items.machine.ItemStamp;
import com.hbm.items.machine.ItemStamp.StampType;
import com.hbm.items.machine.ItemTemplateFolder;
import com.hbm.items.machine.ItemTurretChip;
import com.hbm.items.machine.ItemWatzPellet;
import com.hbm.items.machine.ItemZirnoxRod;
import com.hbm.items.machine.ItemZirnoxRod.EnumZirnoxType;
import com.hbm.items.special.ItemAMSCore;
import com.hbm.items.special.ItemAlexandrite;
import com.hbm.items.special.ItemAutogen;
import com.hbm.items.special.ItemBedrockOre;
import com.hbm.items.special.ItemBook;
import com.hbm.items.special.ItemBookLore;
import com.hbm.items.special.ItemByproduct;
import com.hbm.items.special.ItemChopper;
import com.hbm.items.special.ItemCigarette;
import com.hbm.items.special.ItemCircuitStarComponent;
import com.hbm.items.special.ItemDebugStick;
import com.hbm.items.special.ItemDemonCore;
import com.hbm.items.special.ItemDigamma;
import com.hbm.items.special.ItemDrop;
import com.hbm.items.special.ItemFusionShield;
import com.hbm.items.special.ItemGlitch;
import com.hbm.items.special.ItemHolotapeImage;
import com.hbm.items.special.ItemHot;
import com.hbm.items.special.ItemHotDusted;
import com.hbm.items.special.ItemKitCustom;
import com.hbm.items.special.ItemKitNBT;
import com.hbm.items.special.ItemLootCrate;
import com.hbm.items.special.ItemModRecord;
import com.hbm.items.special.ItemNuclearWaste;
import com.hbm.items.special.ItemPlasticScrap;
import com.hbm.items.special.ItemPlasticScrap.ScrapType;
import com.hbm.items.special.ItemPolaroid;
import com.hbm.items.special.ItemPotatos;
import com.hbm.items.special.ItemRag;
import com.hbm.items.special.ItemSchraranium;
import com.hbm.items.special.ItemSimpleConsumable;
import com.hbm.items.special.ItemSlidingBlastDoorSkin;
import com.hbm.items.special.ItemSoyuz;
import com.hbm.items.special.ItemStarmetal;
import com.hbm.items.special.ItemStarterKit;
import com.hbm.items.special.ItemSyringe;
import com.hbm.items.special.ItemTeleLink;
import com.hbm.items.special.ItemTrain;
import com.hbm.items.special.ItemUnstable;
import com.hbm.items.special.ItemWasteLong;
import com.hbm.items.special.ItemWasteShort;
import com.hbm.items.special.WatzFuel;
import com.hbm.items.tool.BigSword;
import com.hbm.items.tool.HoeSchrabidium;
import com.hbm.items.tool.ItemAmatExtractor;
import com.hbm.items.tool.ItemAmmoContainer;
import com.hbm.items.tool.ItemAnalysisTool;
import com.hbm.items.tool.ItemAnchorRemote;
import com.hbm.items.tool.ItemBalefireMatch;
import com.hbm.items.tool.ItemBlowtorch;
import com.hbm.items.tool.ItemBoltgun;
import com.hbm.items.tool.ItemBombCaller;
import com.hbm.items.tool.ItemCMStructure;
import com.hbm.items.tool.ItemCatalog;
import com.hbm.items.tool.ItemChainsaw;
import com.hbm.items.tool.ItemColtanCompass;
import com.hbm.items.tool.ItemCounterfitKeys;
import com.hbm.items.tool.ItemCouplingTool;
import com.hbm.items.tool.ItemCraftingDegradation;
import com.hbm.items.tool.ItemCrateCaller;
import com.hbm.items.tool.ItemDesignatorArtyRange;
import com.hbm.items.tool.ItemDesingator;
import com.hbm.items.tool.ItemDesingatorManual;
import com.hbm.items.tool.ItemDesingatorRange;
import com.hbm.items.tool.ItemDetonator;
import com.hbm.items.tool.ItemDigammaDiagnostic;
import com.hbm.items.tool.ItemDiscord;
import com.hbm.items.tool.ItemDosimeter;
import com.hbm.items.tool.ItemDrone;
import com.hbm.items.tool.ItemDroneLinker;
import com.hbm.items.tool.ItemDyatlov;
import com.hbm.items.tool.ItemFertilizer;
import com.hbm.items.tool.ItemFilter;
import com.hbm.items.tool.ItemFusionCore;
import com.hbm.items.tool.ItemGeigerCounter;
import com.hbm.items.tool.ItemGuideBook;
import com.hbm.items.tool.ItemKey;
import com.hbm.items.tool.ItemLaserDetonator;
import com.hbm.items.tool.ItemLeadBox;
import com.hbm.items.tool.ItemLock;
import com.hbm.items.tool.ItemMS;
import com.hbm.items.tool.ItemMatch;
import com.hbm.items.tool.ItemMeteorRemote;
import com.hbm.items.tool.ItemMirrorTool;
import com.hbm.items.tool.ItemModBucket;
import com.hbm.items.tool.ItemModDoor;
import com.hbm.items.tool.ItemModMinecart;
import com.hbm.items.tool.ItemMultiDetonator;
import com.hbm.items.tool.ItemMultitoolPassive;
import com.hbm.items.tool.ItemMultitoolTool;
import com.hbm.items.tool.ItemOilDetector;
import com.hbm.items.tool.ItemPeas;
import com.hbm.items.tool.ItemPlasticBag;
import com.hbm.items.tool.ItemPollutionDetector;
import com.hbm.items.tool.ItemPowerNetTool;
import com.hbm.items.tool.ItemRBMKTool;
import com.hbm.items.tool.ItemSatDesignator;
import com.hbm.items.tool.ItemSatInterface;
import com.hbm.items.tool.ItemStructurePattern;
import com.hbm.items.tool.ItemStructureRandomized;
import com.hbm.items.tool.ItemStructureRandomly;
import com.hbm.items.tool.ItemStructureSingle;
import com.hbm.items.tool.ItemStructureSolid;
import com.hbm.items.tool.ItemSurveyScanner;
import com.hbm.items.tool.ItemSwordAbility;
import com.hbm.items.tool.ItemSwordAbilityPower;
import com.hbm.items.tool.ItemSwordMeteorite;
import com.hbm.items.tool.ItemTitaniumFilter;
import com.hbm.items.tool.ItemToolAbility;
import com.hbm.items.tool.ItemToolAbility.EnumToolType;
import com.hbm.items.tool.ItemToolAbilityPower;
import com.hbm.items.tool.ItemTooling;
import com.hbm.items.tool.ItemToolingWeapon;
import com.hbm.items.tool.ItemTurretControl;
import com.hbm.items.tool.ItemWand;
import com.hbm.items.tool.ItemWandD;
import com.hbm.items.tool.ItemWandS;
import com.hbm.items.tool.ItemWiring;
import com.hbm.items.tool.ModHoe;
import com.hbm.items.tool.ModSword;
import com.hbm.items.tool.RedstoneSword;
import com.hbm.items.tool.WeaponSpecial;
import com.hbm.items.weapon.GunB92;
import com.hbm.items.weapon.GunB92Cell;
import com.hbm.items.weapon.GunB93;
import com.hbm.items.weapon.GunCryolator;
import com.hbm.items.weapon.GunDampfmaschine;
import com.hbm.items.weapon.GunDash;
import com.hbm.items.weapon.GunDefabricator;
import com.hbm.items.weapon.GunEuthanasia;
import com.hbm.items.weapon.GunFolly;
import com.hbm.items.weapon.GunHP;
import com.hbm.items.weapon.GunImmolator;
import com.hbm.items.weapon.GunJack;
import com.hbm.items.weapon.GunLeverActionS;
import com.hbm.items.weapon.GunSpark;
import com.hbm.items.weapon.GunSuicide;
import com.hbm.items.weapon.ItemAmmo;
import com.hbm.items.weapon.ItemAmmoArty;
import com.hbm.items.weapon.ItemAmmoHIMARS;
import com.hbm.items.weapon.ItemClip;
import com.hbm.items.weapon.ItemCoilgun;
import com.hbm.items.weapon.ItemCrucible;
import com.hbm.items.weapon.ItemCryoCannon;
import com.hbm.items.weapon.ItemCustomMissile;
import com.hbm.items.weapon.ItemGrenade;
import com.hbm.items.weapon.ItemGrenadeFishing;
import com.hbm.items.weapon.ItemGrenadeKyiv;
import com.hbm.items.weapon.ItemGunBase;
import com.hbm.items.weapon.ItemGunBio;
import com.hbm.items.weapon.ItemGunChemthrower;
import com.hbm.items.weapon.ItemGunCongo;
import com.hbm.items.weapon.ItemGunDart;
import com.hbm.items.weapon.ItemGunDetonator;
import com.hbm.items.weapon.ItemGunGauss;
import com.hbm.items.weapon.ItemGunLacunae;
import com.hbm.items.weapon.ItemGunOSIPR;
import com.hbm.items.weapon.ItemGunShotty;
import com.hbm.items.weapon.ItemGunVortex;
import com.hbm.items.weapon.ItemMissile;
import com.hbm.items.weapon.ItemMissile.FuelType;
import com.hbm.items.weapon.ItemMissile.PartSize;
import com.hbm.items.weapon.ItemMissile.Rarity;
import com.hbm.items.weapon.ItemMissile.WarheadType;
import com.hbm.items.weapon.WeaponizedCell;
import com.hbm.items.weapon.gununified.ItemEnergyGunBase;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.potion.HbmPotion;
import com.hbm.tileentity.machine.rbmk.IRBMKFluxReceiver.NType;
import com.hbm.util.EnchantmentUtil;
import com.hbm.util.RTGUtil;

import api.hbm.block.IToolable.ToolType;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

@SuppressWarnings("deprecation")
public class ModItems {

	public static void mainRegistry() {
		ModItems.initializeItem();
		ModItems.registerItem();
	}

	public static Item redstone_sword;
	public static Item big_sword;

	public static Item test_helmet;
	public static Item test_chestplate;
	public static Item test_leggings;
	public static Item test_boots;

	public static Item ingot_th232;
	public static Item ingot_uranium;
	public static Item ingot_u233;
	public static Item ingot_u235;
	public static Item ingot_u238;
	public static Item ingot_u238m2;
	public static Item ingot_plutonium;
	public static Item ingot_pu238;
	public static Item ingot_pu239;
	public static Item ingot_pu240;
	public static Item ingot_pu241;
	public static Item ingot_pu_mix;
	public static Item ingot_am241;
	public static Item ingot_am242;
	public static Item ingot_am_mix;
	public static Item ingot_neptunium;
	public static Item ingot_polonium;
	public static Item ingot_technetium;
	public static Item ingot_co60;
	public static Item ingot_sr90;
	public static Item ingot_au198;
	public static Item ingot_pb209;
	public static Item ingot_ra226;
	public static Item ingot_titanium;
	public static Item ingot_cobalt;
	public static Item sulfur;

	public static Item coke;
	public static Item lignite;
	public static Item powder_lignite;
	public static Item briquette;
	public static Item coal_infernal;
	public static Item cinnebar;
	public static Item powder_ash;

	public static Item niter;
	public static Item ingot_copper;
	public static Item ingot_red_copper;
	public static Item ingot_tungsten;
	public static Item ingot_aluminium;
	public static Item fluorite;
	public static Item ingot_beryllium;
	public static Item ingot_schraranium;
	public static Item ingot_schrabidium;
	public static Item ingot_schrabidate;
	public static Item ingot_plutonium_fuel;
	public static Item ingot_neptunium_fuel;
	public static Item ingot_uranium_fuel;
	public static Item ingot_mox_fuel;
	public static Item ingot_americium_fuel;
	public static Item ingot_schrabidium_fuel;
	public static Item ingot_thorium_fuel;
	public static Item nugget_uranium_fuel;
	public static Item nugget_thorium_fuel;
	public static Item nugget_plutonium_fuel;
	public static Item nugget_neptunium_fuel;
	public static Item nugget_mox_fuel;
	public static Item nugget_americium_fuel;
	public static Item nugget_schrabidium_fuel;
	public static Item ingot_advanced_alloy;
	public static Item ingot_tcalloy;
	public static Item ingot_cdalloy;
	public static Item lithium;
	public static Item ingot_zirconium;
	public static Item ingot_hes;
	public static Item ingot_les;
	public static Item nugget_hes;
	public static Item nugget_les;
	public static Item ingot_magnetized_tungsten;
	public static Item ingot_combine_steel;
	public static Item ingot_solinium;
	public static Item nugget_solinium;
	public static Item ingot_phosphorus;
	public static Item ingot_semtex;
	public static Item ingot_c4;
	public static Item ingot_boron;
	public static Item ingot_graphite;
	public static Item ingot_firebrick;
	public static Item ingot_smore;

	public static Item ingot_gh336;
	public static Item nugget_gh336;

	public static Item ingot_australium;
	public static Item ingot_weidanium;
	public static Item ingot_reiium;
	public static Item ingot_unobtainium;
	public static Item ingot_daffergon;
	public static Item ingot_verticium;
	public static Item nugget_australium;
	public static Item nugget_australium_lesser;
	public static Item nugget_australium_greater;
	public static Item nugget_weidanium;
	public static Item nugget_reiium;
	public static Item nugget_unobtainium;
	public static Item nugget_daffergon;
	public static Item nugget_verticium;

	public static Item ingot_desh;
	public static Item nugget_desh;
	public static Item ingot_dineutronium;
	public static Item nugget_dineutronium;
	public static Item powder_dineutronium;
	public static Item ingot_tetraneutronium;
	public static Item nugget_tetraneutronium;
	public static Item powder_tetraneutronium;
	public static Item ingot_starmetal;
	public static Item ingot_saturnite;
	public static Item plate_saturnite;
	public static Item ingot_ferrouranium;
	public static Item ingot_electronium;
	public static Item nugget_zirconium;
	public static Item nugget_mercury;
	public static Item ingot_mercury; // It's to prevent any ambiguity, as it was treated as a full ingot in the past
										// anyway
	public static Item bottle_mercury;

	public static Item ore_byproduct; // byproduct of variable purity and quantity, can be treated as a nugget, might
										// require shredding or acidizing, depends on the type

	public static Item ore_bedrock;
	public static Item ore_centrifuged;
	public static Item ore_cleaned;
	public static Item ore_separated;
	public static Item ore_purified;
	public static Item ore_nitrated;
	public static Item ore_nitrocrystalline;
	public static Item ore_deepcleaned;
	public static Item ore_seared;
	// public static Item ore_radcleaned;
	public static Item ore_enriched; // final stage

	public static Item billet_uranium;
	public static Item billet_u233;
	public static Item billet_u235;
	public static Item billet_u238;
	public static Item billet_th232;
	public static Item billet_plutonium;
	public static Item billet_pu238;
	public static Item billet_pu239;
	public static Item billet_pu240;
	public static Item billet_pu241;
	public static Item billet_pu_mix;
	public static Item billet_am241;
	public static Item billet_am242;
	public static Item billet_am_mix;
	public static Item billet_neptunium;
	public static Item billet_polonium;
	public static Item billet_technetium;
	public static Item billet_cobalt;
	public static Item billet_co60;
	public static Item billet_sr90;
	public static Item billet_au198;
	public static Item billet_pb209;
	public static Item billet_ra226;
	public static Item billet_actinium;
	public static Item billet_schrabidium;
	public static Item billet_solinium;
	public static Item billet_gh336;
	public static Item billet_australium;
	public static Item billet_australium_lesser;
	public static Item billet_australium_greater;
	public static Item billet_uranium_fuel;
	public static Item billet_thorium_fuel;
	public static Item billet_plutonium_fuel;
	public static Item billet_neptunium_fuel;
	public static Item billet_mox_fuel;
	public static Item billet_americium_fuel;
	public static Item billet_les;
	public static Item billet_schrabidium_fuel;
	public static Item billet_hes;
	public static Item billet_po210be;
	public static Item billet_ra226be;
	public static Item billet_pu238be;
	public static Item billet_yharonite;
	public static Item billet_balefire_gold;
	public static Item billet_flashlead;
	public static Item billet_zfb_bismuth;
	public static Item billet_zfb_pu241;
	public static Item billet_zfb_am_mix;
	public static Item billet_beryllium;
	public static Item billet_bismuth;
	public static Item billet_zirconium;
	public static Item billet_nuclear_waste;

	public static Item nugget_th232;
	public static Item nugget_uranium;
	public static Item nugget_u233;
	public static Item nugget_u235;
	public static Item nugget_u238;
	public static Item nugget_plutonium;
	public static Item nugget_pu238;
	public static Item nugget_pu239;
	public static Item nugget_pu240;
	public static Item nugget_pu241;
	public static Item nugget_pu_mix;
	public static Item nugget_am241;
	public static Item nugget_am242;
	public static Item nugget_am_mix;
	public static Item nugget_neptunium;
	public static Item nugget_polonium;
	public static Item nugget_technetium;
	public static Item nugget_cobalt;
	public static Item nugget_co60;
	public static Item nugget_sr90;
	public static Item nugget_au198;
	public static Item nugget_pb209;
	public static Item nugget_ra226;
	public static Item nugget_actinium;
	public static Item plate_titanium;
	public static Item plate_aluminium;
	public static Item wire_red_copper;
	public static Item wire_tungsten;
	public static Item neutron_reflector;
	public static Item ingot_steel;
	public static Item plate_steel;
	public static Item plate_iron;
	public static Item ingot_lead;
	public static Item nugget_lead;
	public static Item ingot_bismuth;
	public static Item nugget_bismuth;
	public static Item ingot_arsenic;
	public static Item nugget_arsenic;
	public static Item ingot_tantalium;
	public static Item nugget_tantalium;
	public static Item ingot_niobium;
	public static Item ingot_osmiridium;
	public static Item nugget_osmiridium;
	public static Item plate_lead;
	public static Item nugget_schrabidium;
	public static Item plate_schrabidium;
	public static Item plate_copper;
	public static Item nugget_beryllium;
	public static Item plate_gold;
	public static Item hazmat_cloth;
	public static Item hazmat_cloth_red;
	public static Item hazmat_cloth_grey;
	public static Item asbestos_cloth;
	public static Item rag;
	public static Item rag_damp;
	public static Item rag_piss;
	public static Item filter_coal;
	public static Item plate_advanced_alloy;
	public static Item plate_combine_steel;
	public static Item plate_mixed;
	public static Item plate_paa;
	public static Item board_copper;
	public static Item bolt_dura_steel;
	public static Item pipes_steel;
	public static Item drill_titanium;
	public static Item plate_dalekanium;
	public static Item plate_euphemium;
	public static Item bolt_tungsten;
	public static Item bolt_compound;
	public static Item plate_polymer;
	public static Item plate_kevlar;
	public static Item plate_dineutronium;
	public static Item plate_desh;
	public static Item plate_bismuth;
	public static Item photo_panel;
	public static Item sat_base;
	public static Item thruster_nuclear;
	public static Item safety_fuse;
	public static Item part_generic;
	public static Item chemical_dye;
	public static Item crayon;

	public static Item undefined;

	public static Item ingot_dura_steel;
	public static Item ingot_polymer;
	public static Item ingot_bakelite;
	public static Item ingot_rubber;
	public static Item ingot_pet;
	public static Item ingot_pc;
	public static Item ingot_pvc;

	public static Item ingot_fiberglass;
	public static Item ingot_asbestos;
	public static Item powder_asbestos;
	public static Item ingot_calcium;
	public static Item powder_calcium;
	public static Item ingot_cadmium;
	public static Item powder_cadmium;
	public static Item powder_bismuth;
	public static Item ingot_mud;
	public static Item ingot_cft;

	public static Item ingot_lanthanium;
	public static Item ingot_actinium;

	public static Item ingot_meteorite;
	public static Item ingot_meteorite_forged;
	public static Item blade_meteorite;
	public static Item ingot_steel_dusted;
	public static Item ingot_chainsteel;

	public static Item plate_armor_titanium;
	public static Item plate_armor_ajr;
	public static Item plate_armor_hev;
	public static Item plate_armor_lunar;
	public static Item plate_armor_fau;
	public static Item plate_armor_dnt;

	public static Item oil_tar;
	public static Item solid_fuel;
	public static Item solid_fuel_presto;
	public static Item solid_fuel_presto_triplet;
	public static Item solid_fuel_bf;
	public static Item solid_fuel_presto_bf;
	public static Item solid_fuel_presto_triplet_bf;
	public static Item rocket_fuel;

	public static Item crystal_coal;
	public static Item crystal_iron;
	public static Item crystal_gold;
	public static Item crystal_redstone;
	public static Item crystal_lapis;
	public static Item crystal_diamond;
	public static Item crystal_uranium;
	public static Item crystal_thorium;
	public static Item crystal_plutonium;
	public static Item crystal_titanium;
	public static Item crystal_sulfur;
	public static Item crystal_niter;
	public static Item crystal_copper;
	public static Item crystal_tungsten;
	public static Item crystal_aluminium;
	public static Item crystal_fluorite;
	public static Item crystal_beryllium;
	public static Item crystal_lead;
	public static Item crystal_schraranium;
	public static Item crystal_schrabidium;
	public static Item crystal_rare;
	public static Item crystal_phosphorus;
	public static Item crystal_lithium;
	public static Item crystal_cobalt;
	public static Item crystal_starmetal;
	public static Item crystal_cinnebar;
	public static Item crystal_trixite;
	public static Item crystal_osmiridium;

	public static Item gem_sodalite;
	public static Item gem_tantalium;
	public static Item gem_volcanic;
	public static Item gem_alexandrite;

	public static Item powder_lead;
	public static Item powder_tantalium;
	public static Item powder_neptunium;
	public static Item powder_polonium;
	public static Item powder_co60;
	public static Item powder_sr90;
	public static Item powder_sr90_tiny;
	public static Item powder_au198;
	public static Item powder_ra226;
	public static Item powder_i131;
	public static Item powder_i131_tiny;
	public static Item powder_xe135;
	public static Item powder_xe135_tiny;
	public static Item powder_cs137;
	public static Item powder_cs137_tiny;
	public static Item powder_at209;
	public static Item powder_schrabidium;
	public static Item powder_schrabidate;

	public static Item powder_aluminium;
	public static Item powder_beryllium;
	public static Item powder_copper;
	public static Item powder_gold;
	public static Item powder_iron;
	public static Item powder_titanium;
	public static Item powder_tungsten;
	public static Item powder_uranium;
	public static Item powder_plutonium;
	public static Item dust;
	public static Item dust_tiny;
	public static Item fallout;
	public static Item powder_power;

	public static Item powder_thorium;
	public static Item powder_iodine;
	public static Item powder_neodymium;
	public static Item powder_astatine;
	public static Item powder_caesium;

	public static Item powder_strontium;
	public static Item powder_cobalt;
	public static Item powder_bromine;
	public static Item powder_niobium;
	public static Item powder_tennessine;
	public static Item powder_cerium;

	public static Item powder_advanced_alloy;
	public static Item powder_tcalloy;
	public static Item powder_coal;
	public static Item powder_coal_tiny;
	public static Item powder_combine_steel;
	public static Item powder_diamond;
	public static Item powder_emerald;
	public static Item powder_lapis;
	public static Item powder_quartz;
	public static Item powder_magnetized_tungsten;
	public static Item powder_chlorophyte;
	public static Item powder_red_copper;
	public static Item powder_steel;
	public static Item powder_lithium;
	public static Item powder_zirconium;
	public static Item powder_sodium;
	public static Item redstone_depleted;

	public static Item powder_australium;
	public static Item powder_weidanium;
	public static Item powder_reiium;
	public static Item powder_unobtainium;
	public static Item powder_daffergon;
	public static Item powder_verticium;

	public static Item powder_dura_steel;
	public static Item powder_polymer;
	public static Item powder_bakelite;
	public static Item powder_euphemium;
	public static Item powder_meteorite;

	public static Item powder_steel_tiny;
	public static Item powder_lithium_tiny;
	public static Item powder_neodymium_tiny;
	public static Item powder_cobalt_tiny;
	public static Item powder_niobium_tiny;
	public static Item powder_cerium_tiny;
	public static Item powder_lanthanium_tiny;
	public static Item powder_actinium_tiny;
	public static Item powder_boron_tiny;
	public static Item powder_meteorite_tiny;

	public static Item powder_coltan_ore;
	public static Item powder_coltan;
	public static Item powder_tektite;
	public static Item powder_paleogenite;
	public static Item powder_paleogenite_tiny;
	public static Item powder_impure_osmiridium;
	public static Item powder_borax;
	public static Item powder_chlorocalcite;

	public static Item powder_lanthanium;
	public static Item powder_actinium;
	public static Item powder_boron;
	public static Item powder_desh;
	public static Item powder_semtex_mix;
	public static Item powder_desh_mix;
	public static Item powder_desh_ready;
	public static Item powder_nitan_mix;
	public static Item powder_spark_mix;
	public static Item powder_yellowcake;
	public static Item powder_magic;
	public static Item powder_cloud;
	public static Item powder_balefire;
	public static Item powder_sawdust;
	public static Item powder_flux;
	public static Item powder_fertilizer;

	public static Item fragment_neodymium;
	public static Item fragment_cobalt;
	public static Item fragment_niobium;
	public static Item fragment_cerium;
	public static Item fragment_lanthanium;
	public static Item fragment_actinium;
	public static Item fragment_boron;
	public static Item fragment_meteorite;
	public static Item fragment_coltan;

	public static Item biomass;
	public static Item biomass_compressed;
	public static Item bio_wafer;
	public static Item plant_item;

	public static Item coil_copper;
	public static Item coil_copper_torus;
	public static Item coil_tungsten;
	public static Item tank_steel;
	public static Item motor;
	public static Item motor_desh;
	public static Item motor_bismuth;
	public static Item centrifuge_element;
	// public static Item centrifuge_tower;
	public static Item reactor_core;
	public static Item rtg_unit;
	// public static Item thermo_unit_empty;
	// public static Item thermo_unit_endo;
	// public static Item thermo_unit_exo;
	public static Item levitation_unit;
	public static Item wire_aluminium;
	public static Item wire_copper;
	public static Item wire_gold;
	public static Item wire_schrabidium;
	public static Item wire_advanced_alloy;
	public static Item coil_advanced_alloy;
	public static Item coil_advanced_torus;
	public static Item wire_magnetized_tungsten;
	public static Item coil_magnetized_tungsten;
	public static Item coil_gold;
	public static Item coil_gold_torus;
	// public static Item magnet_dee;
	public static Item magnet_circular;
	// public static Item cyclotron_tower;
	public static Item component_limiter;
	public static Item component_emitter;
	public static Item chlorine_pinwheel;
	public static Item deuterium_filter;

	public static Item parts_legendary;

	public static Item circuit_raw;
	public static Item circuit_aluminium;
	public static Item circuit_copper;
	public static Item circuit_red_copper;
	public static Item circuit_gold;
	public static Item circuit_schrabidium;
	public static Item circuit_bismuth_raw;
	public static Item circuit_bismuth;
	public static Item circuit_arsenic_raw;
	public static Item circuit_arsenic;
	public static Item circuit_tantalium_raw;
	public static Item circuit_tantalium;
	public static Item crt_display;
	public static ItemEnumMulti circuit_star_piece;
	public static ItemEnumMulti circuit_star_component;
	public static Item circuit_star;

	public static Item mechanism_revolver_1;
	public static Item mechanism_revolver_2;
	public static Item mechanism_rifle_1;
	public static Item mechanism_rifle_2;
	public static Item mechanism_launcher_1;
	public static Item mechanism_launcher_2;
	public static Item mechanism_special;

	public static Item casing_357;
	public static Item casing_44;
	public static Item casing_9;
	public static Item casing_50;
	public static Item casing_buckshot;
	public static Item assembly_iron;
	public static Item assembly_steel;
	public static Item assembly_lead;
	public static Item assembly_gold;
	public static Item assembly_schrabidium;
	public static Item assembly_nightmare;
	public static Item assembly_desh;
	// public static Item assembly_pip;
	public static Item assembly_nopip;
	public static Item assembly_smg;
	public static Item assembly_556;
	public static Item assembly_762;
	public static Item assembly_45;
	public static Item assembly_uzi;
	public static Item assembly_actionexpress;
	public static Item assembly_calamity;
	public static Item assembly_lacunae;
	public static Item assembly_nuke;
	public static Item assembly_luna;

	public static Item folly_shell;
	public static Item folly_bullet;
	public static Item folly_bullet_nuclear;
	public static Item folly_bullet_du;

	public static Item circuit_targeting_tier1;
	public static Item circuit_targeting_tier2;
	public static Item circuit_targeting_tier3;
	public static Item circuit_targeting_tier4;
	public static Item circuit_targeting_tier5;
	public static Item circuit_targeting_tier6;

	public static Item wiring_red_copper;

	public static Item hull_small_steel;
	public static Item hull_small_aluminium;
	public static Item hull_big_steel;
	public static Item hull_big_aluminium;
	public static Item hull_big_titanium;
	public static Item fins_flat;
	public static Item fins_small_steel;
	public static Item fins_big_steel;
	public static Item fins_tri_steel;
	public static Item fins_quad_titanium;
	public static Item sphere_steel;
	public static Item pedestal_steel;
	public static Item dysfunctional_reactor;
	public static Item rotor_steel;
	public static Item generator_steel;
	public static Item blade_titanium;
	public static Item turbine_titanium;
	public static Item generator_front;
	public static Item blade_tungsten;
	public static Item turbine_tungsten;
	public static Item pellet_coal;
	public static Item ring_starmetal;
	public static Item flywheel_beryllium;

	public static Item gear_large;
	public static Item sawblade;

	public static Item toothpicks;
	public static Item ducttape;
	public static Item catalyst_clay;

	public static Item warhead_generic_small;
	public static Item warhead_generic_medium;
	public static Item warhead_generic_large;
	public static Item warhead_incendiary_small;
	public static Item warhead_incendiary_medium;
	public static Item warhead_incendiary_large;
	public static Item warhead_cluster_small;
	public static Item warhead_cluster_medium;
	public static Item warhead_cluster_large;
	public static Item warhead_buster_small;
	public static Item warhead_buster_medium;
	public static Item warhead_buster_large;
	public static Item warhead_nuclear;
	public static Item warhead_mirvlet;
	public static Item warhead_mirv;
	public static Item warhead_volcano;
	public static Item warhead_thermo_endo;
	public static Item warhead_thermo_exo;

	public static Item fuel_tank_small;
	public static Item fuel_tank_medium;
	public static Item fuel_tank_large;

	public static Item thruster_small;
	public static Item thruster_medium;
	public static Item thruster_large;

	public static Item sat_head_mapper;
	public static Item sat_head_scanner;
	public static Item sat_head_radar;
	public static Item sat_head_laser;
	public static Item sat_head_resonator;

	public static Item seg_10;
	public static Item seg_15;
	public static Item seg_20;

	public static Item chopper_head;
	public static Item chopper_gun;
	public static Item chopper_torso;
	public static Item chopper_tail;
	public static Item chopper_wing;
	public static Item chopper_blades;
	public static Item combine_scrap;

	public static Item shimmer_head;
	public static Item shimmer_axe_head;
	public static Item shimmer_handle;

	// public static Item telepad;
	public static Item entanglement_kit;

	public static Item stamp_stone_flat;
	public static Item stamp_stone_plate;
	public static Item stamp_stone_wire;
	public static Item stamp_stone_circuit;
	public static Item stamp_iron_flat;
	public static Item stamp_iron_plate;
	public static Item stamp_iron_wire;
	public static Item stamp_iron_circuit;
	public static Item stamp_steel_flat;
	public static Item stamp_steel_plate;
	public static Item stamp_steel_wire;
	public static Item stamp_steel_circuit;
	public static Item stamp_titanium_flat;
	public static Item stamp_titanium_plate;
	public static Item stamp_titanium_wire;
	public static Item stamp_titanium_circuit;
	public static Item stamp_obsidian_flat;
	public static Item stamp_obsidian_plate;
	public static Item stamp_obsidian_wire;
	public static Item stamp_obsidian_circuit;
	public static Item stamp_desh_flat;
	public static Item stamp_desh_plate;
	public static Item stamp_desh_wire;
	public static Item stamp_desh_circuit;

	public static Item stamp_357;
	public static Item stamp_44;
	public static Item stamp_9;
	public static Item stamp_50;

	public static Item stamp_desh_357;
	public static Item stamp_desh_44;
	public static Item stamp_desh_9;
	public static Item stamp_desh_50;

	public static Item blades_steel;
	public static Item blades_titanium;
	public static Item blades_advanced_alloy;
	public static Item blades_desh;

	public static Item mold_base;
	public static Item mold;
	public static Item scraps;
	public static Item plate_cast;
	public static Item plate_welded;
	public static Item heavy_component;
	public static Item wire_dense;

	public static Item part_lithium;
	public static Item part_beryllium;
	public static Item part_carbon;
	public static Item part_copper;
	public static Item part_plutonium;

	public static Item laser_crystal_co2;
	public static Item laser_crystal_bismuth;
	public static Item laser_crystal_cmb;
	public static Item laser_crystal_dnt;
	public static Item laser_crystal_digamma;

	public static Item thermo_element;

	public static Item catalytic_converter;
	public static Item crackpipe;

	public static Item pellet_rtg_depleted;

	public static Item pellet_rtg_radium;
	public static Item pellet_rtg_weak;
	public static Item pellet_rtg;
	public static Item pellet_rtg_strontium;
	public static Item pellet_rtg_cobalt;
	public static Item pellet_rtg_actinium;
	public static Item pellet_rtg_polonium;
	public static Item pellet_rtg_americium;
	public static Item pellet_rtg_berkelium;
	public static Item pellet_rtg_gold;
	public static Item pellet_rtg_lead;

	public static Item tritium_deuterium_cake;

	public static Item pellet_schrabidium;
	public static Item pellet_hes;
	public static Item pellet_mes;
	public static Item pellet_les;
	public static Item pellet_beryllium;
	public static Item pellet_neptunium;
	public static Item pellet_lead;
	public static Item pellet_advanced;

	public static Item piston_selenium;
	public static Item piston_set;
	public static Item drillbit;

	// public static Item crystal_energy;
	// public static Item pellet_coolant;

	public static Item rune_blank;
	public static Item rune_isa;
	public static Item rune_dagaz;
	public static Item rune_hagalaz;
	public static Item rune_jera;
	public static Item rune_thurisaz;

	public static Item ams_catalyst_blank;
	public static Item ams_catalyst_aluminium;
	public static Item ams_catalyst_beryllium;
	public static Item ams_catalyst_caesium;
	public static Item ams_catalyst_cerium;
	public static Item ams_catalyst_cobalt;
	public static Item ams_catalyst_copper;
	public static Item ams_catalyst_dineutronium;
	public static Item ams_catalyst_euphemium;
	public static Item ams_catalyst_iron;
	public static Item ams_catalyst_lithium;
	public static Item ams_catalyst_niobium;
	public static Item ams_catalyst_schrabidium;
	public static Item ams_catalyst_strontium;
	public static Item ams_catalyst_thorium;
	public static Item ams_catalyst_tungsten;

	public static Item ams_focus_blank;
	public static Item ams_focus_limiter;
	public static Item ams_focus_booster;

	public static Item ams_muzzle;

	public static Item ams_lens;

	public static Item ams_core_sing;
	public static Item ams_core_wormhole;
	public static Item ams_core_eyeofharmony;
	public static Item ams_core_thingy;

	public static Item fusion_shield_tungsten;
	public static Item fusion_shield_desh;
	public static Item fusion_shield_chlorophyte;
	public static Item fusion_shield_vaporwave;

	public static Item cell_empty;
	public static Item cell_uf6;
	public static Item cell_puf6;
	public static Item cell_deuterium;
	public static Item cell_tritium;
	public static Item cell_sas3;
	public static Item cell_antimatter;
	public static Item cell_anti_schrabidium;
	public static Item cell_balefire;

	public static Item demon_core_open;
	public static Item demon_core_closed;

	public static Item particle_empty;
	public static Item particle_hydrogen;
	public static Item particle_copper;
	public static Item particle_lead;
	public static Item particle_aproton;
	public static Item particle_aelectron;
	public static Item particle_amat;
	public static Item particle_aschrab;
	public static Item particle_higgs;
	public static Item particle_muon;
	public static Item particle_tachyon;
	public static Item particle_strange;
	public static Item particle_dark;
	public static Item particle_sparkticle;
	public static Item particle_digamma;
	public static Item particle_lutece;

	public static Item pellet_antimatter;
	public static Item singularity_micro;
	public static Item singularity;
	public static Item singularity_counter_resonant;
	public static Item singularity_super_heated;
	public static Item black_hole;
	public static Item singularity_spark;
	public static Item crystal_xen;
	public static Item inf_water;
	public static Item inf_water_mk2;

	public static Item antiknock;

	public static Item canister_empty;
	public static Item canister_full;
	public static Item canister_napalm;

	public static Item gas_empty;
	public static Item gas_full;

	public static Item fluid_tank_full;
	public static Item fluid_tank_empty;
	public static Item fluid_tank_lead_full;
	public static Item fluid_tank_lead_empty;
	public static Item fluid_barrel_full;
	public static Item fluid_barrel_empty;
	public static Item fluid_barrel_infinite;

	public static Item syringe_empty;
	public static Item syringe_antidote;
	public static Item syringe_poison;
	public static Item syringe_awesome;
	public static Item syringe_metal_empty;
	public static Item syringe_metal_stimpak;
	public static Item syringe_metal_medx;
	public static Item syringe_metal_psycho;
	public static Item syringe_metal_super;
	public static Item syringe_taint;
	public static Item syringe_mkunicorn;
	public static Item iv_empty;
	public static Item iv_blood;
	public static Item iv_xp_empty;
	public static Item iv_xp;
	public static Item radaway;
	public static Item radaway_strong;
	public static Item radaway_flush;
	public static Item radx;
	public static Item siox;
	public static Item pill_herbal;
	public static Item xanax;
	public static Item fmn;
	public static Item five_htp;
	public static Item med_bag;
	public static Item pill_iodine;
	public static Item plan_c;
	public static Item pill_red;
	public static Item stealth_boy;
	public static Item gas_mask_filter;
	public static Item gas_mask_filter_mono;
	public static Item gas_mask_filter_combo;
	public static Item gas_mask_filter_rag;
	public static Item gas_mask_filter_piss;
	public static Item jetpack_tank;
	public static Item gun_kit_1;
	public static Item gun_kit_2;
	public static Item cbt_device;
	public static Item cigarette;

	public static Item can_empty;
	public static Item can_smart;
	public static Item can_creature;
	public static Item can_redbomb;
	public static Item can_mrsugar;
	public static Item can_overcharge;
	public static Item can_luna;
	public static Item can_bepis;
	public static Item can_breen;
	public static Item can_mug;
	public static Item mucho_mango;
	public static Item bottle_empty;
	public static Item bottle_nuka;
	public static Item bottle_cherry;
	public static Item bottle_quantum;
	public static Item bottle_sparkle;
	public static Item bottle_rad;
	public static Item bottle2_empty;
	public static Item bottle2_korl;
	public static Item bottle2_fritz;
	public static Item bottle2_korl_special;
	public static Item bottle2_fritz_special;
	public static Item bottle2_sunset;
	public static Item flask_empty;
	public static Item flask_infusion;
	public static Item chocolate_milk;
	public static Item coffee;
	public static Item coffee_radium;
	public static Item chocolate;
	public static Item cap_nuka;
	public static Item cap_quantum;
	public static Item cap_sparkle;
	public static Item cap_rad;
	public static Item cap_korl;
	public static Item cap_fritz;
	public static Item cap_sunset;
	public static Item cap_star;
	public static Item ring_pull;
	public static Item bdcl;
	// public static Item canned_beef;
	// public static Item canned_tuna;
	// public static Item canned_mystery;
	// public static Item canned_pashtet;
	// public static Item canned_cheese;
	// public static Item canned_jizz;
	// public static Item canned_milk;
	// public static Item canned_ass;
	// public static Item canned_pizza;
	// public static Item canned_tube;
	// public static Item canned_tomato;
	// public static Item canned_asbestos;
	// public static Item canned_bhole;
	// public static Item canned_hotdogs;
	// public static Item canned_leftovers;
	// public static Item canned_yogurt;
	// public static Item canned_stew;
	// public static Item canned_chinese;
	// public static Item canned_oil;
	// public static Item canned_fist;
	// public static Item canned_spam;
	// public static Item canned_fried;
	// public static Item canned_napalm;
	// public static Item canned_diesel;
	// public static Item canned_kerosene;
	// public static Item canned_recursion;
	// public static Item canned_bark;
	public static ItemEnumMulti canned_conserve;
	public static Item can_key;

	public static Item cart;
	public static Item train;
	public static Item drone;

	public static Item coin_creeper;
	public static Item coin_radiation;
	public static Item coin_maskman;
	public static Item coin_worm;
	public static Item coin_ufo;
	// public static Item coin_siege;
	// public static Item source;

	public static Item rod_empty;
	public static Item rod;
	public static Item rod_dual_empty;
	public static Item rod_dual;
	public static Item rod_quad_empty;
	public static Item rod_quad;

	public static Item rod_zirnox_empty;
	public static Item rod_zirnox_tritium;
	public static ItemEnumMulti rod_zirnox;

	public static Item rod_zirnox_natural_uranium_fuel_depleted;
	public static Item rod_zirnox_uranium_fuel_depleted;
	public static Item rod_zirnox_thorium_fuel_depleted;
	public static Item rod_zirnox_mox_fuel_depleted;
	public static Item rod_zirnox_plutonium_fuel_depleted;
	public static Item rod_zirnox_u233_fuel_depleted;
	public static Item rod_zirnox_u235_fuel_depleted;
	public static Item rod_zirnox_les_fuel_depleted;
	public static Item rod_zirnox_zfb_mox_depleted;

	public static Item waste_natural_uranium;
	public static Item waste_uranium;
	public static Item waste_thorium;
	public static Item waste_mox;
	public static Item waste_plutonium;
	public static Item waste_u233;
	public static Item waste_u235;
	public static Item waste_schrabidium;
	public static Item waste_zfb_mox; // TODO: remind me to smite these useless waste items and condense em like the
										// rbmk waste

	public static Item waste_plate_u233;
	public static Item waste_plate_u235;
	public static Item waste_plate_mox;
	public static Item waste_plate_pu239;
	public static Item waste_plate_sa326;
	public static Item waste_plate_ra226be;
	public static Item waste_plate_pu238be;

	public static Item pile_rod_uranium;
	public static Item pile_rod_pu239;
	public static Item pile_rod_plutonium;
	public static Item pile_rod_source;
	public static Item pile_rod_boron;
	public static Item pile_rod_lithium;
	public static Item pile_rod_detector;

	public static Item plate_fuel_u233;
	public static Item plate_fuel_u235;
	public static Item plate_fuel_mox;
	public static Item plate_fuel_pu239;
	public static Item plate_fuel_sa326;
	public static Item plate_fuel_ra226be;
	public static Item plate_fuel_pu238be;

	public static Item pwr_fuel;
	public static Item pwr_fuel_hot;
	public static Item pwr_fuel_depleted;

	public static Item rbmk_lid;
	public static Item rbmk_lid_glass;
	public static Item rbmk_fuel_empty;
	public static ItemRBMKRod rbmk_fuel_ueu;
	public static ItemRBMKRod rbmk_fuel_meu;
	public static ItemRBMKRod rbmk_fuel_heu233;
	public static ItemRBMKRod rbmk_fuel_heu235;
	public static ItemRBMKRod rbmk_fuel_thmeu;
	public static ItemRBMKRod rbmk_fuel_lep;
	public static ItemRBMKRod rbmk_fuel_mep;
	public static ItemRBMKRod rbmk_fuel_hep239;
	public static ItemRBMKRod rbmk_fuel_hep241;
	public static ItemRBMKRod rbmk_fuel_lea;
	public static ItemRBMKRod rbmk_fuel_mea;
	public static ItemRBMKRod rbmk_fuel_hea241;
	public static ItemRBMKRod rbmk_fuel_hea242;
	public static ItemRBMKRod rbmk_fuel_men;
	public static ItemRBMKRod rbmk_fuel_hen;
	public static ItemRBMKRod rbmk_fuel_mox;
	public static ItemRBMKRod rbmk_fuel_les;
	public static ItemRBMKRod rbmk_fuel_mes;
	public static ItemRBMKRod rbmk_fuel_hes;
	public static ItemRBMKRod rbmk_fuel_leaus;
	public static ItemRBMKRod rbmk_fuel_heaus;
	public static ItemRBMKRod rbmk_fuel_po210be;
	public static ItemRBMKRod rbmk_fuel_ra226be;
	public static ItemRBMKRod rbmk_fuel_pu238be;
	public static ItemRBMKRod rbmk_fuel_balefire_gold;
	public static ItemRBMKRod rbmk_fuel_flashlead;
	public static ItemRBMKRod rbmk_fuel_balefire;
	public static ItemRBMKRod rbmk_fuel_zfb_bismuth;
	public static ItemRBMKRod rbmk_fuel_zfb_pu241;
	public static ItemRBMKRod rbmk_fuel_zfb_am_mix;
	public static ItemRBMKRod rbmk_fuel_drx;
	public static ItemRBMKRod rbmk_fuel_test;
	public static ItemRBMKPellet rbmk_pellet_ueu;
	public static ItemRBMKPellet rbmk_pellet_meu;
	public static ItemRBMKPellet rbmk_pellet_heu233;
	public static ItemRBMKPellet rbmk_pellet_heu235;
	public static ItemRBMKPellet rbmk_pellet_thmeu;
	public static ItemRBMKPellet rbmk_pellet_lep;
	public static ItemRBMKPellet rbmk_pellet_mep;
	public static ItemRBMKPellet rbmk_pellet_hep239;
	public static ItemRBMKPellet rbmk_pellet_hep241;
	public static ItemRBMKPellet rbmk_pellet_lea;
	public static ItemRBMKPellet rbmk_pellet_mea;
	public static ItemRBMKPellet rbmk_pellet_hea241;
	public static ItemRBMKPellet rbmk_pellet_hea242;
	public static ItemRBMKPellet rbmk_pellet_men;
	public static ItemRBMKPellet rbmk_pellet_hen;
	public static ItemRBMKPellet rbmk_pellet_mox;
	public static ItemRBMKPellet rbmk_pellet_les;
	public static ItemRBMKPellet rbmk_pellet_mes;
	public static ItemRBMKPellet rbmk_pellet_hes;
	public static ItemRBMKPellet rbmk_pellet_leaus;
	public static ItemRBMKPellet rbmk_pellet_heaus;
	public static ItemRBMKPellet rbmk_pellet_po210be;
	public static ItemRBMKPellet rbmk_pellet_ra226be;
	public static ItemRBMKPellet rbmk_pellet_pu238be;
	public static ItemRBMKPellet rbmk_pellet_balefire_gold;
	public static ItemRBMKPellet rbmk_pellet_flashlead;
	public static ItemRBMKPellet rbmk_pellet_balefire;
	public static ItemRBMKPellet rbmk_pellet_zfb_bismuth;
	public static ItemRBMKPellet rbmk_pellet_zfb_pu241;
	public static ItemRBMKPellet rbmk_pellet_zfb_am_mix;
	public static ItemRBMKPellet rbmk_pellet_drx;

	public static Item watz_pellet;
	public static Item watz_pellet_depleted;

	public static Item scrap_plastic;
	public static Item scrap;
	public static Item scrap_oil;
	public static Item scrap_nuclear;
	public static Item trinitite;
	public static Item nuclear_waste_long;
	public static Item nuclear_waste_long_tiny;
	public static Item nuclear_waste_short;
	public static Item nuclear_waste_short_tiny;
	public static Item nuclear_waste_long_depleted;
	public static Item nuclear_waste_long_depleted_tiny;
	public static Item nuclear_waste_short_depleted;
	public static Item nuclear_waste_short_depleted_tiny;
	public static Item nuclear_waste;
	public static Item nuclear_waste_tiny;
	public static Item nuclear_waste_vitrified;
	public static Item nuclear_waste_vitrified_tiny;

	public static Item debris_graphite;
	public static Item debris_metal;
	public static Item debris_fuel;
	public static Item debris_concrete;
	public static Item debris_exchanger;
	public static Item debris_shrapnel;
	public static Item debris_element;

	public static Item containment_box;
	public static Item plastic_bag;

	public static Item test_nuke_igniter;
	public static Item test_nuke_propellant;
	public static Item test_nuke_tier1_shielding;
	public static Item test_nuke_tier2_shielding;
	public static Item test_nuke_tier1_bullet;
	public static Item test_nuke_tier2_bullet;
	public static Item test_nuke_tier1_target;
	public static Item test_nuke_tier2_target;

	public static Item cordite;
	public static Item ballistite;
	public static Item ball_dynamite;
	public static Item ball_tnt;
	public static Item ball_tatb;
	public static Item ball_fireclay;

	public static Item pellet_cluster;
	public static Item powder_fire;
	public static Item powder_ice;
	public static Item powder_poison;
	public static Item powder_thermite;
	public static Item pellet_gas;
	public static Item magnetron;
	public static Item pellet_buckshot;
	public static Item pellet_flechette;
	public static Item pellet_chlorophyte;
	public static Item pellet_mercury;
	public static Item pellet_meteorite;
	public static Item pellet_canister;
	public static Item pellet_claws;
	public static Item pellet_charged;

	public static Item designator;
	public static Item designator_range;
	public static Item designator_manual;
	public static Item designator_arty_range;
	public static Item linker;
	public static Item reactor_sensor;
	public static Item oil_detector;
	public static Item dosimeter;
	public static Item geiger_counter;
	public static Item digamma_diagnostic;
	public static Item pollution_detector;
	public static Item survey_scanner;
	public static Item mirror_tool;
	public static Item rbmk_tool;
	public static Item coltan_tool;
	public static Item power_net_tool;
	public static Item analysis_tool;
	public static Item coupling_tool;
	public static Item drone_linker;

	public static Item template_folder;
	public static Item journal_pip;
	public static Item journal_bj;
	public static Item journal_silver;
	public static Item assembly_template;
	public static Item chemistry_template;
	public static Item chemistry_icon;
	public static Item crucible_template;
	public static Item fluid_identifier;
	public static Item fluid_identifier_multi;
	public static Item fluid_icon;
	public static Item siren_track;
	public static Item fluid_duct;

	public static Item bobmazon_materials;
	public static Item bobmazon_machines;
	public static Item bobmazon_weapons;
	public static Item bobmazon_tools;
	public static Item bobmazon_hidden;

	public static Item missile_assembly;
	public static Item missile_generic;
	public static Item missile_anti_ballistic;
	public static Item missile_incendiary;
	public static Item missile_cluster;
	public static Item missile_buster;
	public static Item missile_strong;
	public static Item missile_incendiary_strong;
	public static Item missile_cluster_strong;
	public static Item missile_buster_strong;
	public static Item missile_emp_strong;
	public static Item missile_burst;
	public static Item missile_inferno;
	public static Item missile_rain;
	public static Item missile_drill;
	public static Item missile_nuclear;
	public static Item missile_nuclear_cluster;
	public static Item missile_volcano;
	public static Item missile_endo;
	public static Item missile_exo;
	public static Item missile_doomsday;
	public static Item missile_taint;
	public static Item missile_micro;
	public static Item missile_bhole;
	public static Item missile_schrabidium;
	public static Item missile_emp;
	public static Item missile_shuttle;

	public static Item mp_thruster_10_kerosene;
	public static Item mp_thruster_10_kerosene_tec;
	public static Item mp_thruster_10_solid;
	public static Item mp_thruster_10_xenon;
	public static Item mp_thruster_15_kerosene;
	public static Item mp_thruster_15_kerosene_tec;
	public static Item mp_thruster_15_kerosene_dual;
	public static Item mp_thruster_15_kerosene_triple;
	public static Item mp_thruster_15_solid;
	public static Item mp_thruster_15_solid_hexdecuple;
	public static Item mp_thruster_15_hydrogen;
	public static Item mp_thruster_15_hydrogen_dual;
	public static Item mp_thruster_15_balefire_short;
	public static Item mp_thruster_15_balefire;
	public static Item mp_thruster_15_balefire_large;
	public static Item mp_thruster_15_balefire_large_rad;
	public static Item mp_thruster_20_kerosene;
	public static Item mp_thruster_20_kerosene_dual;
	public static Item mp_thruster_20_kerosene_triple;
	public static Item mp_thruster_20_solid;
	public static Item mp_thruster_20_solid_multi;
	public static Item mp_thruster_20_solid_multier;

	public static Item mp_stability_10_flat;
	public static Item mp_stability_10_cruise;
	public static Item mp_stability_10_space;
	public static Item mp_stability_15_flat;
	public static Item mp_stability_15_thin;
	public static Item mp_stability_15_soyuz;
	public static Item mp_stability_20_flat;

	public static Item mp_fuselage_10_kerosene;
	public static Item mp_fuselage_10_kerosene_camo;
	public static Item mp_fuselage_10_kerosene_desert;
	public static Item mp_fuselage_10_kerosene_sky;
	public static Item mp_fuselage_10_kerosene_flames;
	public static Item mp_fuselage_10_kerosene_insulation;
	public static Item mp_fuselage_10_kerosene_sleek;
	public static Item mp_fuselage_10_kerosene_metal;
	public static Item mp_fuselage_10_kerosene_taint;

	public static Item mp_fuselage_10_solid;
	public static Item mp_fuselage_10_solid_flames;
	public static Item mp_fuselage_10_solid_insulation;
	public static Item mp_fuselage_10_solid_sleek;
	public static Item mp_fuselage_10_solid_soviet_glory;
	public static Item mp_fuselage_10_solid_cathedral;
	public static Item mp_fuselage_10_solid_moonlit;
	public static Item mp_fuselage_10_solid_battery;
	public static Item mp_fuselage_10_solid_duracell;

	public static Item mp_fuselage_10_xenon;
	public static Item mp_fuselage_10_xenon_bhole;

	public static Item mp_fuselage_10_long_kerosene;
	public static Item mp_fuselage_10_long_kerosene_camo;
	public static Item mp_fuselage_10_long_kerosene_desert;
	public static Item mp_fuselage_10_long_kerosene_sky;
	public static Item mp_fuselage_10_long_kerosene_flames;
	public static Item mp_fuselage_10_long_kerosene_insulation;
	public static Item mp_fuselage_10_long_kerosene_sleek;
	public static Item mp_fuselage_10_long_kerosene_metal;
	public static Item mp_fuselage_10_long_kerosene_taint;
	public static Item mp_fuselage_10_long_kerosene_dash;
	public static Item mp_fuselage_10_long_kerosene_vap;

	public static Item mp_fuselage_10_long_solid;
	public static Item mp_fuselage_10_long_solid_flames;
	public static Item mp_fuselage_10_long_solid_insulation;
	public static Item mp_fuselage_10_long_solid_sleek;
	public static Item mp_fuselage_10_long_solid_soviet_glory;
	public static Item mp_fuselage_10_long_solid_bullet;
	public static Item mp_fuselage_10_long_solid_silvermoonlight;

	public static Item mp_fuselage_10_15_kerosene;
	public static Item mp_fuselage_10_15_solid;
	public static Item mp_fuselage_10_15_hydrogen;
	public static Item mp_fuselage_10_15_balefire;

	public static Item mp_fuselage_15_kerosene;
	public static Item mp_fuselage_15_kerosene_camo;
	public static Item mp_fuselage_15_kerosene_desert;
	public static Item mp_fuselage_15_kerosene_sky;
	public static Item mp_fuselage_15_kerosene_insulation;
	public static Item mp_fuselage_15_kerosene_metal;
	public static Item mp_fuselage_15_kerosene_decorated;
	public static Item mp_fuselage_15_kerosene_steampunk;
	public static Item mp_fuselage_15_kerosene_polite;
	public static Item mp_fuselage_15_kerosene_blackjack;
	public static Item mp_fuselage_15_kerosene_lambda;
	public static Item mp_fuselage_15_kerosene_minuteman;
	public static Item mp_fuselage_15_kerosene_pip;
	public static Item mp_fuselage_15_kerosene_taint;
	public static Item mp_fuselage_15_kerosene_yuck;

	public static Item mp_fuselage_15_solid;
	public static Item mp_fuselage_15_solid_insulation;
	public static Item mp_fuselage_15_solid_desh;
	public static Item mp_fuselage_15_solid_soviet_glory;
	public static Item mp_fuselage_15_solid_soviet_stank;
	public static Item mp_fuselage_15_solid_faust;
	public static Item mp_fuselage_15_solid_silvermoonlight;
	public static Item mp_fuselage_15_solid_snowy;
	public static Item mp_fuselage_15_solid_panorama;
	public static Item mp_fuselage_15_solid_roses;
	public static Item mp_fuselage_15_solid_mimi;

	public static Item mp_fuselage_15_hydrogen;
	public static Item mp_fuselage_15_hydrogen_cathedral;

	public static Item mp_fuselage_15_balefire;

	public static Item mp_fuselage_15_20_kerosene;
	public static Item mp_fuselage_15_20_kerosene_magnusson;
	public static Item mp_fuselage_15_20_solid;

	public static Item mp_fuselage_20_kerosene;

	public static Item mp_warhead_10_he;
	public static Item mp_warhead_10_incendiary;
	public static Item mp_warhead_10_buster;
	public static Item mp_warhead_10_nuclear;
	public static Item mp_warhead_10_nuclear_large;
	public static Item mp_warhead_10_taint;
	public static Item mp_warhead_10_cloud;
	public static Item mp_warhead_15_he;
	public static Item mp_warhead_15_incendiary;
	public static Item mp_warhead_15_nuclear;
	public static Item mp_warhead_15_nuclear_shark;
	public static Item mp_warhead_15_nuclear_mimi;
	public static Item mp_warhead_15_boxcar;
	public static Item mp_warhead_15_n2;
	public static Item mp_warhead_15_balefire;
	public static Item mp_warhead_15_turbine;
	public static Item mp_warhead_20_he;

	public static Item mp_chip_1;
	public static Item mp_chip_2;
	public static Item mp_chip_3;
	public static Item mp_chip_4;
	public static Item mp_chip_5;

	public static Item missile_skin_camo;
	public static Item missile_skin_desert;
	public static Item missile_skin_flames;
	public static Item missile_skin_manly_pink;
	public static Item missile_skin_orange_insulation;
	public static Item missile_skin_sleek;
	public static Item missile_skin_soviet_glory;
	public static Item missile_skin_soviet_stank;
	public static Item missile_skin_metal;

	public static Item missile_custom;

	public static Item missile_carrier;
	public static Item missile_soyuz;
	public static Item missile_soyuz_lander;
	public static Item sat_mapper;
	public static Item sat_scanner;
	public static Item sat_radar;
	public static Item sat_laser;
	public static Item sat_foeq;
	public static Item sat_resonator;
	public static Item sat_miner;
	public static Item sat_lunar_miner;
	public static Item sat_gerald;
	public static Item sat_chip;
	public static Item sat_interface;
	public static Item sat_coord;
	public static Item sat_designator;

	public static ItemEnumMulti ammo_misc;
	public static ItemEnumMulti ammo_12gauge;
	public static ItemEnumMulti ammo_20gauge;
	public static ItemEnumMulti ammo_4gauge;
	public static ItemEnumMulti ammo_357;
	public static ItemEnumMulti ammo_44;
	public static ItemEnumMulti ammo_5mm;
	public static ItemEnumMulti ammo_9mm;
	public static ItemEnumMulti ammo_45;
	public static ItemEnumMulti ammo_556;
	public static ItemEnumMulti ammo_762;
	public static ItemEnumMulti ammo_22lr;
	public static ItemEnumMulti ammo_50ae;
	public static ItemEnumMulti ammo_50bmg;
	public static ItemEnumMulti ammo_75bolt;
	public static ItemEnumMulti ammo_rocket;
	public static ItemEnumMulti ammo_grenade;
	public static ItemEnumMulti ammo_shell;
	public static ItemEnumMulti ammo_nuke;
	public static ItemEnumMulti ammo_fuel;
	public static ItemEnumMulti ammo_fireext;
	public static ItemEnumMulti ammo_dart;
	public static ItemEnumMulti ammo_stinger_rocket;
	public static ItemEnumMulti ammo_luna_sniper;
	public static ItemEnumMulti ammo_coilgun;

	public static Item ammo_cell;

	public static Item ammo_folly;
	public static Item ammo_folly_nuclear;
	public static Item ammo_folly_du;
	public static Item ammo_dgk;
	public static Item ammo_arty;
	public static Item ammo_himars;

	public static Item gun_rpg;
	public static Item gun_karl;
	public static Item gun_panzerschreck;
	public static Item gun_quadro;
	public static Item gun_hk69;
	public static Item gun_congolake;
	public static Item gun_stinger;
	public static Item gun_skystinger;
	public static Item gun_revolver;
	public static Item gun_revolver_saturnite;
	public static Item gun_revolver_gold;
	public static Item gun_revolver_schrabidium;
	public static Item gun_revolver_cursed;
	public static Item gun_revolver_nightmare;
	public static Item gun_revolver_nightmare2;
	public static Item gun_revolver_pip;
	public static Item gun_revolver_nopip;
	public static Item gun_revolver_blackjack;
	public static Item gun_revolver_silver;
	public static Item gun_revolver_red;
	public static Item gun_bio_revolver;
	public static Item gun_deagle;
	public static Item gun_flechette;
	public static Item gun_ar15;
	public static Item gun_calamity;
	public static Item gun_minigun;
	public static Item gun_avenger;
	public static Item gun_lacunae;
	public static Item gun_folly;
	public static Item gun_fatman;
	public static Item gun_proto;
	public static Item gun_mirv;
	public static Item gun_bf;
	public static Item gun_chemthrower;
	public static Item gun_mp40;
	public static Item gun_thompson;
	public static Item gun_uzi;
	public static Item gun_uzi_silencer;
	public static Item gun_uzi_saturnite;
	public static Item gun_uzi_saturnite_silencer;
	public static Item gun_uboinik;
	public static Item gun_remington;
	public static Item gun_spas12;
	public static Item gun_supershotgun;
	public static Item gun_benelli;
	public static Item gun_ks23;
	public static Item gun_sauer;
	public static Item gun_lever_action;
	public static Item gun_lever_action_dark;
	public static Item gun_lever_action_sonata;
	public static Item gun_bolt_action;
	public static Item gun_bolt_action_green;
	public static Item gun_bolt_action_saturnite;
	public static Item gun_mymy;
	public static Item gun_b92;
	public static Item gun_b92_ammo;
	public static Item gun_b93;
	public static Item gun_coilgun;
	public static Item gun_xvl1456;
	public static Item gun_xvl1456_ammo;
	public static Item gun_osipr;
	public static Item gun_osipr_ammo;
	public static Item gun_osipr_ammo2;
	public static Item gun_immolator;
	public static Item gun_immolator_ammo;
	public static Item gun_flamer;
	public static Item gun_cryolator;
	public static Item gun_cryocannon;
	public static Item gun_cryolator_ammo;
	public static Item gun_fireext;
	public static Item gun_mp;
	public static Item gun_bolter;
	public static Item gun_bolter_digamma;
	public static Item gun_zomg;
	public static Item gun_super_shotgun;
	public static Item gun_moist_nugget;
	public static Item gun_revolver_inverted;
	public static Item gun_emp;
	public static Item gun_emp_ammo;
	public static Item gun_jack;
	public static Item gun_jack_ammo;
	public static Item gun_spark;
	public static Item gun_spark_ammo;
	public static Item gun_hp;
	public static Item gun_hp_ammo;
	public static Item gun_euthanasia;
	public static Item gun_euthanasia_ammo;
	public static Item gun_dash;
	public static Item gun_dash_ammo;
	public static Item gun_twigun;
	public static Item gun_twigun_ammo;
	public static Item gun_defabricator;
	public static Item gun_defabricator_ammo;
	public static Item gun_vortex;
	public static Item gun_dampfmaschine;
	public static Item gun_waluigi;
	public static Item gun_darter;
	public static Item gun_detonator;
	public static Item gun_glass_cannon;
	public static Item gun_m2;
	public static Item gun_lunatic_marksman;
	public static Item gun_uac_pistol;

	// We'll figure this part out later
	// public static Item gun_llr, gun_mlr, gun_hlr, gun_twr, gun_lunatic,
	// gun_lunatic_shotty;
	// public static Item gun_uac_pistol, gun_uac_dmr, gun_uac_carbine, gun_uac_lmg;
	// public static Item gun_benelli, gun_benelli_mod, gun_g36, spear_bishamonten,
	// pagoda;

	public static Item crucible;

	public static Item stick_dynamite;
	public static Item stick_dynamite_fishing;
	public static Item stick_tnt;
	public static Item stick_semtex;
	public static Item stick_c4;

	public static Item grenade_generic;
	public static Item grenade_strong;
	public static Item grenade_frag;
	public static Item grenade_fire;
	public static Item grenade_shrapnel;
	public static Item grenade_cluster;
	public static Item grenade_flare;
	public static Item grenade_electric;
	public static Item grenade_poison;
	public static Item grenade_gas;
	public static Item grenade_pulse;
	public static Item grenade_plasma;
	public static Item grenade_tau;
	public static Item grenade_schrabidium;
	public static Item grenade_lemon;
	public static Item grenade_gascan;
	public static Item grenade_kyiv;
	public static Item grenade_mk2;
	public static Item grenade_aschrab;
	public static Item grenade_nuke;
	public static Item grenade_nuclear;
	public static Item grenade_zomg;
	public static Item grenade_black_hole;
	public static Item grenade_cloud;
	public static Item grenade_pink_cloud;
	public static Item ullapool_caber;

	public static Item grenade_if_generic;
	public static Item grenade_if_he;
	public static Item grenade_if_bouncy;
	public static Item grenade_if_sticky;
	public static Item grenade_if_impact;
	public static Item grenade_if_incendiary;
	public static Item grenade_if_toxic;
	public static Item grenade_if_concussion;
	public static Item grenade_if_brimstone;
	public static Item grenade_if_mystery;
	public static Item grenade_if_spark;
	public static Item grenade_if_hopwire;
	public static Item grenade_if_null;

	public static Item grenade_smart;
	public static Item grenade_mirv;
	public static Item grenade_breach;
	public static Item grenade_burst;

	public static Item nuclear_waste_pearl;

	public static Item weaponized_starblaster_cell;

	public static Item bomb_waffle;
	public static Item schnitzel_vegan;
	public static Item cotton_candy;
	public static Item apple_lead;
	public static Item apple_schrabidium;
	public static Item tem_flakes;
	public static Item glowing_stew;
	public static Item balefire_scrambled;
	public static Item balefire_and_ham;
	public static Item lemon;
	public static Item definitelyfood;
	public static Item loops;
	public static Item loop_stew;
	public static Item spongebob_macaroni;
	public static Item fooditem;
	public static Item twinkie;
	public static Item static_sandwich;
	public static Item pudding;
	public static Item pancake;
	public static Item nugget;
	public static Item peas;
	public static Item marshmallow;
	public static Item cheese;
	public static Item quesadilla;
	public static Item glyphid_meat;
	public static Item glyphid_meat_grilled;
	public static Item egg_glyphid;

	public static Item med_ipecac;
	public static Item med_ptsd;
	public static Item med_schizophrenia;

	public static Item canteen_13;
	public static Item canteen_vodka;
	public static Item canteen_fab;

	public static Item defuser;
	public static Item reacher;
	public static Item bismuth_tool;
	public static Item meltdown_tool;

	public static Item flame_pony;
	public static Item flame_conspiracy;
	public static Item flame_politics;
	public static Item flame_opinion;

	// public static Item gadget_explosive;
	public static Item early_explosive_lenses;
	public static Item explosive_lenses;
	public static Item gadget_wireing;
	public static Item gadget_core;
	public static Item boy_igniter;
	public static Item boy_propellant;
	public static Item boy_bullet;
	public static Item boy_target;
	public static Item boy_shielding;
	// public static Item man_explosive;
	public static Item man_igniter;
	public static Item man_core;
	public static Item mike_core;
	public static Item mike_deut;
	public static Item mike_cooling_unit;
	public static Item tsar_core;
	public static Item fleija_igniter;
	public static Item fleija_propellant;
	public static Item fleija_core;
	public static Item solinium_igniter;
	public static Item solinium_propellant;
	public static Item solinium_core;
	public static Item n2_charge;
	public static Item egg_balefire_shard;
	public static Item egg_balefire;

	public static Item custom_tnt;
	public static Item custom_nuke;
	public static Item custom_hydro;
	public static Item custom_amat;
	public static Item custom_dirty;
	public static Item custom_schrab;
	public static Item custom_fall;

	public static Item battery_generic;
	public static Item battery_advanced;
	public static Item battery_lithium;
	public static Item battery_schrabidium;
	public static Item battery_spark;
	public static Item battery_trixite;
	public static Item battery_creative;

	public static Item battery_red_cell;
	public static Item battery_red_cell_6;
	public static Item battery_red_cell_24;
	public static Item battery_advanced_cell;
	public static Item battery_advanced_cell_4;
	public static Item battery_advanced_cell_12;
	public static Item battery_lithium_cell;
	public static Item battery_lithium_cell_3;
	public static Item battery_lithium_cell_6;
	public static Item battery_schrabidium_cell;
	public static Item battery_schrabidium_cell_2;
	public static Item battery_schrabidium_cell_4;
	public static Item battery_spark_cell_6;
	public static Item battery_spark_cell_25;
	public static Item battery_spark_cell_100;
	public static Item battery_spark_cell_1000;
	public static Item battery_spark_cell_2500;
	public static Item battery_spark_cell_10000;
	public static Item battery_spark_cell_power;
	public static Item cube_power;

	public static Item battery_sc_uranium;
	public static Item battery_sc_technetium;
	public static Item battery_sc_plutonium;
	public static Item battery_sc_polonium;
	public static Item battery_sc_gold;
	public static Item battery_sc_lead;
	public static Item battery_sc_americium;

	public static Item battery_su;
	public static Item battery_su_l;
	public static Item battery_potato;
	public static Item battery_potatos;
	public static Item battery_steam;
	public static Item battery_steam_large;
	public static Item hev_battery;
	public static Item fusion_core;
	public static Item fusion_core_infinite;
	public static Item energy_core;
	public static Item fuse;
	public static Item redcoil_capacitor;
	public static Item euphemium_capacitor;
	public static Item titanium_filter;
	// by using these in crafting table recipes, i'm running the risk of making my
	// recipes too greg-ian (which i don't like)
	// in the event that i forget about the meaning of the word "sparingly", please
	// throw a brick at my head
	public static Item screwdriver;
	public static Item screwdriver_desh;
	public static Item hand_drill;
	public static Item hand_drill_desh;
	public static Item wrench_archineer;
	public static Item chemistry_set;
	public static Item chemistry_set_boron;
	public static Item blowtorch;
	public static Item acetylene_torch;
	public static Item boltgun;
	public static Item overfuse;
	public static Item arc_electrode;
	public static Item arc_electrode_burnt;
	public static Item arc_electrode_desh;

	/*
	 * public static Item factory_core_titanium; public static Item
	 * factory_core_advanced;
	 */

	public static Item upgrade_template;
	public static Item upgrade_speed_1;
	public static Item upgrade_speed_2;
	public static Item upgrade_speed_3;
	public static Item upgrade_effect_1;
	public static Item upgrade_effect_2;
	public static Item upgrade_effect_3;
	public static Item upgrade_power_1;
	public static Item upgrade_power_2;
	public static Item upgrade_power_3;
	public static Item upgrade_fortune_1;
	public static Item upgrade_fortune_2;
	public static Item upgrade_fortune_3;
	public static Item upgrade_afterburn_1;
	public static Item upgrade_afterburn_2;
	public static Item upgrade_afterburn_3;
	public static Item upgrade_overdrive_1;
	public static Item upgrade_overdrive_2;
	public static Item upgrade_overdrive_3;
	public static Item upgrade_radius;
	public static Item upgrade_health;
	public static Item upgrade_smelter;
	public static Item upgrade_shredder;
	public static Item upgrade_centrifuge;
	public static Item upgrade_crystallizer;
	public static Item upgrade_nullifier;
	public static Item upgrade_screm;
	public static Item upgrade_gc_speed;
	public static Item upgrade_5g;
	public static Item upgrade_stack;
	public static Item upgrade_ejector;

	public static Item ingot_euphemium;
	public static Item nugget_euphemium;
	public static Item euphemium_helmet;
	public static Item euphemium_plate;
	public static Item euphemium_legs;
	public static Item euphemium_boots;
	public static Item apple_euphemium;
	public static Item watch;
	public static Item euphemium_stopper;

	public static Item goggles;
	public static Item ashglasses;
	public static Item gas_mask;
	public static Item gas_mask_m65;
	public static Item gas_mask_mono;
	public static Item gas_mask_olde;
	public static Item mask_rag;
	public static Item mask_piss;
	public static Item oxy_mask;
	public static Item hat;
	public static Item beta;
	public static Item no9;

	public static Item t45_helmet;
	public static Item t45_plate;
	public static Item t45_legs;
	public static Item t45_boots;
	public static Item steamsuit_helmet;
	public static Item steamsuit_plate;
	public static Item steamsuit_legs;
	public static Item steamsuit_boots;
	public static Item dieselsuit_helmet;
	public static Item dieselsuit_plate;
	public static Item dieselsuit_legs;
	public static Item dieselsuit_boots;

	public static Item chainsaw;

	public static Item schrabidium_helmet;
	public static Item schrabidium_plate;
	public static Item schrabidium_legs;
	public static Item schrabidium_boots;
	public static Item titanium_helmet;
	public static Item titanium_plate;
	public static Item titanium_legs;
	public static Item titanium_boots;
	public static Item steel_helmet;
	public static Item steel_plate;
	public static Item steel_legs;
	public static Item steel_boots;
	public static Item alloy_helmet;
	public static Item alloy_plate;
	public static Item alloy_legs;
	public static Item alloy_boots;
	public static Item cmb_helmet;
	public static Item cmb_plate;
	public static Item cmb_legs;
	public static Item cmb_boots;
	public static Item paa_plate;
	public static Item paa_legs;
	public static Item paa_boots;
	public static Item asbestos_helmet;
	public static Item asbestos_plate;
	public static Item asbestos_legs;
	public static Item asbestos_boots;
	public static Item security_helmet;
	public static Item security_plate;
	public static Item security_legs;
	public static Item security_boots;
	public static Item cobalt_helmet;
	public static Item cobalt_plate;
	public static Item cobalt_legs;
	public static Item cobalt_boots;
	public static Item starmetal_helmet;
	public static Item starmetal_plate;
	public static Item starmetal_legs;
	public static Item starmetal_boots;
	public static Item dnt_helmet;
	public static Item dnt_plate;
	public static Item dnt_legs;
	public static Item dnt_boots;
	public static Item ajr_helmet;
	public static Item ajr_plate;
	public static Item ajr_legs;
	public static Item ajr_boots;
	public static Item ajro_helmet;
	public static Item ajro_plate;
	public static Item ajro_legs;
	public static Item ajro_boots;
	public static Item rpa_helmet;
	public static Item rpa_plate;
	public static Item rpa_legs;
	public static Item rpa_boots;
	public static Item bismuth_helmet;
	public static Item bismuth_plate;
	public static Item bismuth_legs;
	public static Item bismuth_boots;
	public static Item bj_helmet;
	public static Item bj_plate;
	public static Item bj_plate_jetpack;
	public static Item bj_legs;
	public static Item bj_boots;
	public static Item envsuit_helmet;
	public static Item envsuit_plate;
	public static Item envsuit_legs;
	public static Item envsuit_boots;
	public static Item hev_helmet;
	public static Item hev_plate;
	public static Item hev_legs;
	public static Item hev_boots;
	public static Item fau_helmet;
	public static Item fau_plate;
	public static Item fau_legs;
	public static Item fau_boots;
	public static Item dns_helmet;
	public static Item dns_plate;
	public static Item dns_legs;
	public static Item dns_boots;
	public static Item trenchmaster_helmet;
	public static Item trenchmaster_plate;
	public static Item trenchmaster_legs;
	public static Item trenchmaster_boots;
	public static Item zirconium_legs;
	public static Item robes_helmet;
	public static Item robes_plate;
	public static Item robes_legs;
	public static Item robes_boots;

	public static Item australium_iii;
	public static Item australium_iv;
	public static Item australium_v;

	public static Item jetpack_boost;
	public static Item jetpack_break;
	public static Item jetpack_fly;
	public static Item jetpack_vector;
	public static Item wings_limp;
	public static Item wings_murk;

	public static Item jackt;
	public static Item jackt2;

	public static Item schrabidium_sword;
	public static Item schrabidium_pickaxe;
	public static Item schrabidium_axe;
	public static Item schrabidium_shovel;
	public static Item schrabidium_hoe;
	public static Item titanium_sword;
	public static Item titanium_pickaxe;
	public static Item titanium_axe;
	public static Item titanium_shovel;
	public static Item titanium_hoe;
	public static Item steel_sword;
	public static Item steel_pickaxe;
	public static Item steel_axe;
	public static Item steel_shovel;
	public static Item steel_hoe;
	public static Item alloy_sword;
	public static Item alloy_pickaxe;
	public static Item alloy_axe;
	public static Item alloy_shovel;
	public static Item alloy_hoe;
	public static Item cmb_sword;
	public static Item cmb_pickaxe;
	public static Item cmb_axe;
	public static Item cmb_shovel;
	public static Item cmb_hoe;
	public static Item elec_sword;
	public static Item elec_pickaxe;
	public static Item elec_axe;
	public static Item elec_shovel;
	public static Item desh_sword;
	public static Item desh_pickaxe;
	public static Item desh_axe;
	public static Item desh_shovel;
	public static Item desh_hoe;
	public static Item cobalt_sword;
	public static Item cobalt_pickaxe;
	public static Item cobalt_axe;
	public static Item cobalt_shovel;
	public static Item cobalt_hoe;
	public static Item cobalt_decorated_sword;
	public static Item cobalt_decorated_pickaxe;
	public static Item cobalt_decorated_axe;
	public static Item cobalt_decorated_shovel;
	public static Item cobalt_decorated_hoe;
	public static Item starmetal_sword;
	public static Item starmetal_pickaxe;
	public static Item starmetal_axe;
	public static Item starmetal_shovel;
	public static Item starmetal_hoe;
	public static Item smashing_hammer;
	public static Item centri_stick;
	public static Item drax;
	public static Item drax_mk2;
	public static Item drax_mk3;
	public static Item bismuth_pickaxe;
	public static Item volcanic_pickaxe;
	public static Item chlorophyte_pickaxe;
	public static Item mese_pickaxe;
	public static Item dnt_sword;

	public static Item meteorite_sword;
	public static Item meteorite_sword_seared;
	public static Item meteorite_sword_reforged;
	public static Item meteorite_sword_hardened;
	public static Item meteorite_sword_alloyed;
	public static Item meteorite_sword_machined;
	public static Item meteorite_sword_treated;
	public static Item meteorite_sword_etched;
	public static Item meteorite_sword_bred;
	public static Item meteorite_sword_irradiated;
	public static Item meteorite_sword_fused;
	public static Item meteorite_sword_baleful;

	public static Item matchstick;
	public static Item balefire_and_steel;

	public static Item mask_of_infamy;

	public static Item schrabidium_hammer;
	public static Item shimmer_sledge;
	public static Item shimmer_axe;
	public static Item bottle_opener;
	public static Item pch; // for compat please do not hit me
	public static Item wood_gavel;
	public static Item lead_gavel;
	public static Item diamond_gavel;
	public static Item mese_gavel;

	public static Item crowbar;

	public static Item wrench;
	public static Item wrench_flipped;
	public static Item memespoon;

	public static Item multitool_hit;
	public static Item multitool_dig;
	public static Item multitool_silk;
	public static Item multitool_ext;
	public static Item multitool_miner;
	public static Item multitool_beam;
	public static Item multitool_sky;
	public static Item multitool_mega;
	public static Item multitool_joule;
	public static Item multitool_decon;

	public static Item saw;
	public static Item bat;
	public static Item bat_nail;
	public static Item golf_club;
	public static Item pipe_rusty;
	public static Item pipe_lead;
	public static Item reer_graar;
	public static Item stopsign;
	public static Item sopsign;
	public static Item chernobylsign;

	public static Item crystal_horn;
	public static Item crystal_charred;

	public static Item attachment_mask;
	public static Item attachment_mask_mono;
	public static Item back_tesla;
	public static Item servo_set;
	public static Item servo_set_desh;
	public static Item pads_rubber;
	public static Item pads_slime;
	public static Item pads_static;
	public static Item cladding_paint;
	public static Item cladding_rubber;
	public static Item cladding_lead;
	public static Item cladding_desh;
	public static Item cladding_ghiorsium;
	public static Item cladding_iron;
	public static Item cladding_obsidian;
	public static Item insert_kevlar;
	public static Item insert_sapi;
	public static Item insert_esapi;
	public static Item insert_xsapi;
	public static Item insert_steel;
	public static Item insert_du;
	public static Item insert_polonium;
	public static Item insert_ghiorsium;
	public static Item insert_era;
	public static Item insert_yharonite;
	public static Item insert_doxium;
	public static Item armor_polish;
	public static Item bandaid;
	public static Item serum;
	public static Item quartz_plutonium;
	public static Item morning_glory;
	public static Item lodestone;
	public static Item horseshoe_magnet;
	public static Item industrial_magnet;
	public static Item bathwater;
	public static Item bathwater_mk2;
	public static Item spider_milk;
	public static Item ink;
	public static Item heart_piece;
	public static Item heart_container;
	public static Item heart_booster;
	public static Item heart_fab;
	public static Item black_diamond;
	public static Item wd40;
	public static Item scrumpy;
	public static Item wild_p;
	public static Item fabsols_vodka;
	public static Item shackles;
	public static Item injector_5htp;
	public static Item injector_knife;
	public static Item medal_liquidator;
	public static Item v1;
	public static Item protection_charm;
	public static Item meteor_charm;
	public static Item neutrino_lens;
	public static Item gas_tester;
	public static Item defuser_gold;
	public static Item ballistic_gauntlet;
	public static Item night_vision;

	public static Item hazmat_helmet;
	public static Item hazmat_plate;
	public static Item hazmat_legs;
	public static Item hazmat_boots;
	public static Item hazmat_helmet_red;
	public static Item hazmat_plate_red;
	public static Item hazmat_legs_red;
	public static Item hazmat_boots_red;
	public static Item hazmat_helmet_grey;
	public static Item hazmat_plate_grey;
	public static Item hazmat_legs_grey;
	public static Item hazmat_boots_grey;
	public static Item liquidator_helmet;
	public static Item liquidator_plate;
	public static Item liquidator_legs;
	public static Item liquidator_boots;

	public static Item hazmat_paa_helmet;
	public static Item hazmat_paa_plate;
	public static Item hazmat_paa_legs;
	public static Item hazmat_paa_boots;

	public static Item wand;
	public static Item wand_s;
	public static Item wand_d;

	public static Item structure_single;
	public static Item structure_solid;
	public static Item structure_pattern;
	public static Item structure_randomized;
	public static Item structure_randomly;
	public static Item structure_custommachine;

	public static Item rod_of_discord;

	public static Item cape_test;
	public static Item cape_radiation;
	public static Item cape_gasmask;
	public static Item cape_schrabidium;
	public static Item cape_hidden;
	/*
	 * public static Item cape_hbm; public static Item cape_dafnik; public static
	 * Item cape_lpkukin; public static Item cape_vertice; public static Item
	 * cape_codered_; public static Item cape_ayy; public static Item
	 * cape_nostalgia;
	 */

	public static Item nuke_starter_kit;
	public static Item nuke_advanced_kit;
	public static Item nuke_commercially_kit;
	public static Item nuke_electric_kit;
	public static Item gadget_kit;
	public static Item boy_kit;
	public static Item man_kit;
	public static Item mike_kit;
	public static Item tsar_kit;
	public static Item multi_kit;
	public static Item custom_kit;
	public static Item grenade_kit;
	public static Item fleija_kit;
	public static Item prototype_kit;
	public static Item missile_kit;
	public static Item t45_kit;
	public static Item euphemium_kit;
	public static Item solinium_kit;
	public static Item hazmat_kit;
	public static Item hazmat_red_kit;
	public static Item hazmat_grey_kit;
	public static Item kit_custom;
	public static Item kit_toolbox_empty;
	public static Item kit_toolbox;

	public static Item loot_10;
	public static Item loot_15;
	public static Item loot_misc;

	public static Item clip_revolver_iron;
	public static Item clip_revolver;
	public static Item clip_revolver_gold;
	public static Item clip_revolver_lead;
	public static Item clip_revolver_schrabidium;
	public static Item clip_revolver_cursed;
	public static Item clip_revolver_nightmare;
	public static Item clip_revolver_nightmare2;
	public static Item clip_revolver_pip;
	public static Item clip_revolver_nopip;
	public static Item clip_rpg;
	public static Item clip_stinger;
	public static Item clip_fatman;
	public static Item clip_mirv;
	public static Item clip_bf;
	public static Item clip_mp40;
	public static Item clip_uzi;
	public static Item clip_uboinik;
	public static Item clip_lever_action;
	public static Item clip_bolt_action;
	public static Item clip_osipr;
	public static Item clip_immolator;
	public static Item clip_cryolator;
	public static Item clip_mp;
	public static Item clip_xvl1456;
	public static Item clip_emp;
	public static Item clip_jack;
	public static Item clip_spark;
	public static Item clip_hp;
	public static Item clip_euthanasia;
	public static Item clip_defabricator;

	public static Item ammo_container;

	public static Item igniter;
	public static Item detonator;
	public static Item detonator_multi;
	public static Item detonator_laser;
	public static Item detonator_deadman;
	public static Item detonator_de;
	public static Item crate_caller;
	public static Item bomb_caller;
	public static Item meteor_remote;
	public static Item anchor_remote;
	public static Item remote;
	public static Item turret_control;
	public static Item turret_chip;
	// public static Item turret_biometry;

	public static Item spawn_chopper;
	public static Item spawn_worm;
	public static Item spawn_ufo;
	public static Item spawn_duck;

	public static Item key;
	public static Item key_red;
	public static Item key_kit;
	public static Item key_fake;
	public static Item pin;
	public static Item padlock_rusty;
	public static Item padlock;
	public static Item padlock_reinforced;
	public static Item padlock_unbreakable;

	public static Item mech_key;

	public static Item bucket_mud;
	public static Item bucket_acid;
	public static Item bucket_toxic;
	public static Item bucket_schrabidic_acid;
	public static Item bucket_sulfuric_acid;

	public static Item door_metal;
	public static Item door_office;
	public static Item door_bunker;

	public static Item sliding_blast_door_skin;

	public static Item record_lc;
	public static Item record_ss;
	public static Item record_vc;
	public static Item record_glass;

	public static Item book_guide;
	public static Item book_lore;
	public static Item holotape_image;
	public static Item holotape_damaged;

	public static Item polaroid;
	public static Item glitch;
	public static Item letter;
	public static Item book_secret;
	public static Item book_of_;
	public static Item burnt_bark;

	public static Item smoke1;
	public static Item smoke2;
	public static Item smoke3;
	public static Item smoke4;
	public static Item smoke5;
	public static Item smoke6;
	public static Item smoke7;
	public static Item smoke8;
	public static Item b_smoke1;
	public static Item b_smoke2;
	public static Item b_smoke3;
	public static Item b_smoke4;
	public static Item b_smoke5;
	public static Item b_smoke6;
	public static Item b_smoke7;
	public static Item b_smoke8;
	public static Item d_smoke1;
	public static Item d_smoke2;
	public static Item d_smoke3;
	public static Item d_smoke4;
	public static Item d_smoke5;
	public static Item d_smoke6;
	public static Item d_smoke7;
	public static Item d_smoke8;
	public static Item spill1;
	public static Item spill2;
	public static Item spill3;
	public static Item spill4;
	public static Item spill5;
	public static Item spill6;
	public static Item spill7;
	public static Item spill8;
	public static Item gas1;
	public static Item gas2;
	public static Item gas3;
	public static Item gas4;
	public static Item gas5;
	public static Item gas6;
	public static Item gas7;
	public static Item gas8;
	public static Item chlorine1;
	public static Item chlorine2;
	public static Item chlorine3;
	public static Item chlorine4;
	public static Item chlorine5;
	public static Item chlorine6;
	public static Item chlorine7;
	public static Item chlorine8;
	public static Item pc1;
	public static Item pc2;
	public static Item pc3;
	public static Item pc4;
	public static Item pc5;
	public static Item pc6;
	public static Item pc7;
	public static Item pc8;
	public static Item cloud1;
	public static Item cloud2;
	public static Item cloud3;
	public static Item cloud4;
	public static Item cloud5;
	public static Item cloud6;
	public static Item cloud7;
	public static Item cloud8;
	public static Item orange1;
	public static Item orange2;
	public static Item orange3;
	public static Item orange4;
	public static Item orange5;
	public static Item orange6;
	public static Item orange7;
	public static Item orange8;
	/*
	 * public static Item gasflame1; public static Item gasflame2; public static
	 * Item gasflame3; public static Item gasflame4; public static Item gasflame5;
	 * public static Item gasflame6; public static Item gasflame7; public static
	 * Item gasflame8;
	 */
	public static Item energy_ball;
	public static Item discharge;
	public static Item empblast;
	public static Item flame_1;
	public static Item flame_2;
	public static Item flame_3;
	public static Item flame_4;
	public static Item flame_5;
	public static Item flame_6;
	public static Item flame_7;
	public static Item flame_8;
	public static Item flame_9;
	public static Item flame_10;
	public static Item ln2_1;
	public static Item ln2_2;
	public static Item ln2_3;
	public static Item ln2_4;
	public static Item ln2_5;
	public static Item ln2_6;
	public static Item ln2_7;
	public static Item ln2_8;
	public static Item ln2_9;
	public static Item ln2_10;
	public static Item nothing;
	public static Item void_anim;

	public static Item achievement_icon;
	public static Item bob_metalworks;
	public static Item bob_assembly;
	public static Item bob_chemistry;
	public static Item bob_oil;
	public static Item bob_nuclear;

	public static Item mysteryshovel;
	public static Item memory;

	public static Item debug_wand;

	public static void initializeItem() {
		ModItems.redstone_sword = new RedstoneSword(ToolMaterial.STONE).setUnlocalizedName("redstone_sword")
				.setCreativeTab(CreativeTabs.tabCombat).setTextureName(RefStrings.MODID + ":redstone_sword");
		ModItems.big_sword = new BigSword(ToolMaterial.EMERALD).setUnlocalizedName("big_sword")
				.setCreativeTab(CreativeTabs.tabCombat).setTextureName(RefStrings.MODID + ":big_sword");

		ModItems.test_helmet = new ArmorTest(MainRegistry.enumArmorMaterialEmerald, 0).setUnlocalizedName("test_helmet")
				.setCreativeTab(null).setTextureName(RefStrings.MODID + ":test_helmet");
		ModItems.test_chestplate = new ArmorTest(MainRegistry.enumArmorMaterialEmerald, 1)
				.setUnlocalizedName("test_chestplate").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":test_chestplate");
		ModItems.test_leggings = new ArmorTest(MainRegistry.enumArmorMaterialEmerald, 2)
				.setUnlocalizedName("test_leggings").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":test_leggings");
		ModItems.test_boots = new ArmorTest(MainRegistry.enumArmorMaterialEmerald, 3).setUnlocalizedName("test_boots")
				.setCreativeTab(null).setTextureName(RefStrings.MODID + ":test_boots");

		ModItems.test_nuke_igniter = new Item().setUnlocalizedName("test_nuke_igniter").setMaxStackSize(1)
				.setCreativeTab(null).setTextureName(RefStrings.MODID + ":test_nuke_igniter");
		ModItems.test_nuke_propellant = new Item().setUnlocalizedName("test_nuke_propellant").setMaxStackSize(1)
				.setCreativeTab(null).setTextureName(RefStrings.MODID + ":test_nuke_propellant");
		ModItems.test_nuke_tier1_shielding = new Item().setUnlocalizedName("test_nuke_tier1_shielding")
				.setMaxStackSize(1).setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":test_nuke_tier1_shielding");
		ModItems.test_nuke_tier2_shielding = new Item().setUnlocalizedName("test_nuke_tier2_shielding")
				.setMaxStackSize(1).setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":test_nuke_tier2_shielding");
		ModItems.test_nuke_tier1_bullet = new Item().setUnlocalizedName("test_nuke_tier1_bullet").setMaxStackSize(1)
				.setCreativeTab(null).setTextureName(RefStrings.MODID + ":test_nuke_tier1_bullet");
		ModItems.test_nuke_tier2_bullet = new Item().setUnlocalizedName("test_nuke_tier2_bullet").setMaxStackSize(1)
				.setCreativeTab(null).setTextureName(RefStrings.MODID + ":test_nuke_tier2_bullet");
		ModItems.test_nuke_tier1_target = new Item().setUnlocalizedName("test_nuke_tier1_target").setMaxStackSize(1)
				.setCreativeTab(null).setTextureName(RefStrings.MODID + ":test_nuke_tier1_target");
		ModItems.test_nuke_tier2_target = new Item().setUnlocalizedName("test_nuke_tier2_target").setMaxStackSize(1)
				.setCreativeTab(null).setTextureName(RefStrings.MODID + ":test_nuke_tier2_target");

		ModItems.ingot_th232 = new Item().setUnlocalizedName("ingot_th232").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_th232");
		ModItems.ingot_uranium = new Item().setUnlocalizedName("ingot_uranium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_uranium");
		ModItems.ingot_u233 = new Item().setUnlocalizedName("ingot_u233").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_u233");
		ModItems.ingot_u235 = new Item().setUnlocalizedName("ingot_u235").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_u235");
		ModItems.ingot_u238 = new Item().setUnlocalizedName("ingot_u238").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_u238");
		ModItems.ingot_u238m2 = new ItemUnstable(350, 200).setUnlocalizedName("ingot_u238m2").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":ingot_u238m2");
		ModItems.ingot_plutonium = new Item().setUnlocalizedName("ingot_plutonium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_plutonium");
		ModItems.ingot_pu238 = new Item().setUnlocalizedName("ingot_pu238").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_pu238");
		ModItems.ingot_pu239 = new Item().setUnlocalizedName("ingot_pu239").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_pu239");
		ModItems.ingot_pu240 = new Item().setUnlocalizedName("ingot_pu240").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_pu240");
		ModItems.ingot_pu241 = new Item().setUnlocalizedName("ingot_pu241").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_pu241");
		ModItems.ingot_pu_mix = new Item().setUnlocalizedName("ingot_pu_mix").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_pu_mix");
		ModItems.ingot_am241 = new Item().setUnlocalizedName("ingot_am241").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_am241");
		ModItems.ingot_am242 = new Item().setUnlocalizedName("ingot_am242").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_am242");
		ModItems.ingot_am_mix = new Item().setUnlocalizedName("ingot_am_mix").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_am_mix");
		ModItems.ingot_neptunium = new ItemCustomLore().setUnlocalizedName("ingot_neptunium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_neptunium");
		ModItems.ingot_polonium = new Item().setUnlocalizedName("ingot_polonium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_polonium");
		ModItems.ingot_technetium = new Item().setUnlocalizedName("ingot_technetium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_technetium");
		ModItems.ingot_co60 = new Item().setUnlocalizedName("ingot_co60").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_co60");
		ModItems.ingot_sr90 = new Item().setUnlocalizedName("ingot_sr90").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_sr90");
		ModItems.ingot_au198 = new Item().setUnlocalizedName("ingot_au198").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_au198");
		ModItems.ingot_pb209 = new Item().setUnlocalizedName("ingot_pb209").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_pb209");
		ModItems.ingot_ra226 = new Item().setUnlocalizedName("ingot_ra226").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_ra226");
		ModItems.ingot_titanium = new Item().setUnlocalizedName("ingot_titanium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_titanium");
		ModItems.ingot_cobalt = new Item().setUnlocalizedName("ingot_cobalt").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_cobalt");
		ModItems.ingot_boron = new Item().setUnlocalizedName("ingot_boron").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_boron");
		ModItems.ingot_graphite = new Item().setUnlocalizedName("ingot_graphite").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_graphite");
		ModItems.ingot_firebrick = new Item().setUnlocalizedName("ingot_firebrick")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_firebrick");
		ModItems.ingot_smore = new ItemFood(10, 20F, false).setUnlocalizedName("ingot_smore")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_smore");
		ModItems.sulfur = new Item().setUnlocalizedName("sulfur").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":sulfur");

		ModItems.ingot_uranium_fuel = new Item().setUnlocalizedName("ingot_uranium_fuel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_uranium_fuel");
		ModItems.ingot_plutonium_fuel = new Item().setUnlocalizedName("ingot_plutonium_fuel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_plutonium_fuel");
		ModItems.ingot_neptunium_fuel = new Item().setUnlocalizedName("ingot_neptunium_fuel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_neptunium_fuel");
		ModItems.ingot_mox_fuel = new Item().setUnlocalizedName("ingot_mox_fuel").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_mox_fuel");
		ModItems.ingot_americium_fuel = new Item().setUnlocalizedName("ingot_americium_fuel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_americium_fuel");
		ModItems.ingot_schrabidium_fuel = new Item().setUnlocalizedName("ingot_schrabidium_fuel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_schrabidium_fuel");
		ModItems.ingot_thorium_fuel = new Item().setUnlocalizedName("ingot_thorium_fuel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_thorium_fuel");
		ModItems.nugget_uranium_fuel = new Item().setUnlocalizedName("nugget_uranium_fuel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_uranium_fuel");
		ModItems.nugget_thorium_fuel = new Item().setUnlocalizedName("nugget_thorium_fuel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_thorium_fuel");
		ModItems.nugget_plutonium_fuel = new Item().setUnlocalizedName("nugget_plutonium_fuel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_plutonium_fuel");
		ModItems.nugget_neptunium_fuel = new Item().setUnlocalizedName("nugget_neptunium_fuel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_neptunium_fuel");
		ModItems.nugget_mox_fuel = new Item().setUnlocalizedName("nugget_mox_fuel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_mox_fuel");
		ModItems.nugget_americium_fuel = new Item().setUnlocalizedName("nugget_americium_fuel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_americium_fuel");
		ModItems.nugget_schrabidium_fuel = new Item().setUnlocalizedName("nugget_schrabidium_fuel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_schrabidium_fuel");
		ModItems.ingot_advanced_alloy = new Item().setUnlocalizedName("ingot_advanced_alloy")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_advanced_alloy");
		ModItems.ingot_tcalloy = new Item().setUnlocalizedName("ingot_tcalloy").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_tcalloy");
		ModItems.ingot_cdalloy = new Item().setUnlocalizedName("ingot_cdalloy").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_cdalloy");

		ModItems.niter = new Item().setUnlocalizedName("niter").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":salpeter");
		ModItems.ingot_copper = new Item().setUnlocalizedName("ingot_copper").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_copper");
		ModItems.ingot_red_copper = new Item().setUnlocalizedName("ingot_red_copper")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_red_copper");
		ModItems.ingot_tungsten = new Item().setUnlocalizedName("ingot_tungsten").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_tungsten");
		ModItems.ingot_aluminium = new Item().setUnlocalizedName("ingot_aluminium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_aluminium");
		ModItems.fluorite = new Item().setUnlocalizedName("fluorite").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":fluorite");
		ModItems.ingot_beryllium = new Item().setUnlocalizedName("ingot_beryllium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_beryllium");
		ModItems.ingot_steel = new Item().setUnlocalizedName("ingot_steel").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_steel");
		ModItems.plate_steel = new Item().setUnlocalizedName("plate_steel").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":plate_steel");
		ModItems.plate_iron = new Item().setUnlocalizedName("plate_iron").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":plate_iron");
		ModItems.ingot_lead = new Item().setUnlocalizedName("ingot_lead").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_lead");
		ModItems.plate_lead = new Item().setUnlocalizedName("plate_lead").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":plate_lead");
		ModItems.ingot_schraranium = new ItemSchraranium().setUnlocalizedName("ingot_schraranium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_schraranium");
		ModItems.ingot_schrabidium = new ItemCustomLore().setRarity(EnumRarity.rare)
				.setUnlocalizedName("ingot_schrabidium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_schrabidium");
		ModItems.ingot_schrabidate = new ItemCustomLore().setRarity(EnumRarity.rare)
				.setUnlocalizedName("ingot_schrabidate").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_schrabidate");
		ModItems.plate_schrabidium = new ItemCustomLore().setRarity(EnumRarity.rare)
				.setUnlocalizedName("plate_schrabidium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":plate_schrabidium");
		ModItems.plate_copper = new Item().setUnlocalizedName("plate_copper").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":plate_copper");
		ModItems.plate_gold = new Item().setUnlocalizedName("plate_gold").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":plate_gold");
		ModItems.plate_advanced_alloy = new Item().setUnlocalizedName("plate_advanced_alloy")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_advanced_alloy");
		ModItems.lithium = new Item().setUnlocalizedName("lithium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":lithium");
		ModItems.ingot_zirconium = new Item().setUnlocalizedName("ingot_zirconium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_zirconium");
		ModItems.ingot_semtex = new ItemLemon(4, 5, true).setUnlocalizedName("ingot_semtex")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_semtex");
		ModItems.ingot_c4 = new Item().setUnlocalizedName("ingot_c4").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_c4");
		ModItems.ingot_phosphorus = new Item().setUnlocalizedName("ingot_phosphorus")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_phosphorus");
		ModItems.wire_advanced_alloy = new Item().setUnlocalizedName("wire_advanced_alloy")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":wire_advanced_alloy");
		ModItems.coil_advanced_alloy = new Item().setUnlocalizedName("coil_advanced_alloy")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":coil_advanced_alloy");
		ModItems.coil_advanced_torus = new Item().setUnlocalizedName("coil_advanced_torus")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":coil_advanced_torus");
		ModItems.ingot_magnetized_tungsten = new Item().setUnlocalizedName("ingot_magnetized_tungsten")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_magnetized_tungsten");
		ModItems.ingot_combine_steel = new ItemCustomLore().setUnlocalizedName("ingot_combine_steel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_combine_steel");
		ModItems.plate_mixed = new Item().setUnlocalizedName("plate_mixed").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":plate_mixed");
		ModItems.plate_paa = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("plate_paa")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_paa");
		ModItems.board_copper = new Item().setUnlocalizedName("board_copper").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":board_copper");
		ModItems.bolt_dura_steel = new Item().setUnlocalizedName("bolt_dura_steel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":bolt_dura_steel");
		ModItems.pipes_steel = new Item().setUnlocalizedName("pipes_steel").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":pipes_steel");
		ModItems.drill_titanium = new Item().setUnlocalizedName("drill_titanium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":drill_titanium");
		ModItems.plate_dalekanium = new Item().setUnlocalizedName("plate_dalekanium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_dalekanium");
		ModItems.plate_euphemium = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("plate_euphemium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_euphemium");
		ModItems.bolt_tungsten = new Item().setUnlocalizedName("bolt_tungsten").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":bolt_tungsten");
		ModItems.bolt_compound = new Item().setUnlocalizedName("bolt_compound").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":bolt_compound");
		ModItems.plate_polymer = new Item().setUnlocalizedName("plate_polymer").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":plate_polymer");
		ModItems.plate_kevlar = new Item().setUnlocalizedName("plate_kevlar").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":plate_kevlar");
		ModItems.plate_dineutronium = new Item().setUnlocalizedName("plate_dineutronium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_dineutronium");
		ModItems.plate_desh = new Item().setUnlocalizedName("plate_desh").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":plate_desh");
		ModItems.plate_bismuth = new ItemCustomLore().setUnlocalizedName("plate_bismuth")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_bismuth");
		ModItems.ingot_solinium = new Item().setUnlocalizedName("ingot_solinium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_solinium");
		ModItems.nugget_solinium = new Item().setUnlocalizedName("nugget_solinium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_solinium");
		ModItems.photo_panel = new Item().setUnlocalizedName("photo_panel").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":photo_panel");
		ModItems.sat_base = new Item().setUnlocalizedName("sat_base").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":sat_base");
		ModItems.thruster_nuclear = new Item().setUnlocalizedName("thruster_nuclear")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":thruster_nuclear");
		ModItems.safety_fuse = new Item().setUnlocalizedName("safety_fuse").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":safety_fuse");
		ModItems.part_generic = new ItemGenericPart().setUnlocalizedName("part_generic")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":part_generic");
		ModItems.chemical_dye = new ItemChemicalDye().setUnlocalizedName("chemical_dye")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":chemical_dye");
		ModItems.crayon = new ItemCrayon().setUnlocalizedName("crayon").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":crayon");

		ModItems.undefined = new ItemCustomLore().setUnlocalizedName("undefined").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":undefined");

		ModItems.billet_uranium = new Item().setUnlocalizedName("billet_uranium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_uranium");
		ModItems.billet_u233 = new Item().setUnlocalizedName("billet_u233").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_u233");
		ModItems.billet_u235 = new Item().setUnlocalizedName("billet_u235").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_u235");
		ModItems.billet_u238 = new Item().setUnlocalizedName("billet_u238").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_u238");
		ModItems.billet_th232 = new Item().setUnlocalizedName("billet_th232").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_th232");
		ModItems.billet_plutonium = new Item().setUnlocalizedName("billet_plutonium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_plutonium");
		ModItems.billet_pu238 = new Item().setUnlocalizedName("billet_pu238").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_pu238");
		ModItems.billet_pu239 = new Item().setUnlocalizedName("billet_pu239").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_pu239");
		ModItems.billet_pu240 = new Item().setUnlocalizedName("billet_pu240").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_pu240");
		ModItems.billet_pu241 = new Item().setUnlocalizedName("billet_pu241").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_pu241");
		ModItems.billet_pu_mix = new Item().setUnlocalizedName("billet_pu_mix").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_pu_mix");
		ModItems.billet_am241 = new Item().setUnlocalizedName("billet_am241").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_am241");
		ModItems.billet_am242 = new Item().setUnlocalizedName("billet_am242").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_am242");
		ModItems.billet_am_mix = new Item().setUnlocalizedName("billet_am_mix").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_am_mix");
		ModItems.billet_neptunium = new Item().setUnlocalizedName("billet_neptunium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_neptunium");
		ModItems.billet_polonium = new Item().setUnlocalizedName("billet_polonium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_polonium");
		ModItems.billet_technetium = new Item().setUnlocalizedName("billet_technetium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_technetium");
		ModItems.billet_cobalt = new Item().setUnlocalizedName("billet_cobalt").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_cobalt");
		ModItems.billet_co60 = new Item().setUnlocalizedName("billet_co60").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_co60");
		ModItems.billet_sr90 = new Item().setUnlocalizedName("billet_sr90").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_sr90");
		ModItems.billet_au198 = new Item().setUnlocalizedName("billet_au198").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_au198");
		ModItems.billet_pb209 = new Item().setUnlocalizedName("billet_pb209").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_pb209");
		ModItems.billet_ra226 = new Item().setUnlocalizedName("billet_ra226").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_ra226");
		ModItems.billet_actinium = new Item().setUnlocalizedName("billet_actinium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_actinium");
		ModItems.billet_schrabidium = new ItemCustomLore().setRarity(EnumRarity.rare)
				.setUnlocalizedName("billet_schrabidium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_schrabidium");
		ModItems.billet_solinium = new Item().setUnlocalizedName("billet_solinium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_solinium");
		ModItems.billet_gh336 = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("billet_gh336")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_gh336");
		ModItems.billet_australium = new ItemCustomLore().setRarity(EnumRarity.uncommon)
				.setUnlocalizedName("billet_australium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_australium");
		ModItems.billet_australium_lesser = new ItemCustomLore().setRarity(EnumRarity.uncommon)
				.setUnlocalizedName("billet_australium_lesser").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_australium_lesser");
		ModItems.billet_australium_greater = new ItemCustomLore().setRarity(EnumRarity.uncommon)
				.setUnlocalizedName("billet_australium_greater").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_australium_greater");
		ModItems.billet_uranium_fuel = new Item().setUnlocalizedName("billet_uranium_fuel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_uranium_fuel");
		ModItems.billet_thorium_fuel = new Item().setUnlocalizedName("billet_thorium_fuel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_thorium_fuel");
		ModItems.billet_plutonium_fuel = new Item().setUnlocalizedName("billet_plutonium_fuel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_plutonium_fuel");
		ModItems.billet_neptunium_fuel = new Item().setUnlocalizedName("billet_neptunium_fuel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_neptunium_fuel");
		ModItems.billet_mox_fuel = new Item().setUnlocalizedName("billet_mox_fuel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_mox_fuel");
		ModItems.billet_americium_fuel = new Item().setUnlocalizedName("billet_americium_fuel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_americium_fuel");
		ModItems.billet_les = new Item().setUnlocalizedName("billet_les").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_les");
		ModItems.billet_schrabidium_fuel = new Item().setUnlocalizedName("billet_schrabidium_fuel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_schrabidium_fuel");
		ModItems.billet_hes = new Item().setUnlocalizedName("billet_hes").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_hes");
		ModItems.billet_po210be = new Item().setUnlocalizedName("billet_po210be").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_po210be");
		ModItems.billet_ra226be = new Item().setUnlocalizedName("billet_ra226be").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_ra226be");
		ModItems.billet_pu238be = new Item().setUnlocalizedName("billet_pu238be").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_pu238be");
		ModItems.billet_beryllium = new Item().setUnlocalizedName("billet_beryllium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_beryllium");
		ModItems.billet_bismuth = new Item().setUnlocalizedName("billet_bismuth").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_bismuth");
		ModItems.billet_zirconium = new Item().setUnlocalizedName("billet_zirconium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_zirconium");
		ModItems.billet_yharonite = new Item().setUnlocalizedName("billet_yharonite")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_yharonite");
		ModItems.billet_balefire_gold = new ItemCustomLore().setRarity(EnumRarity.uncommon)
				.setUnlocalizedName("billet_balefire_gold").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_balefire_gold");
		ModItems.billet_flashlead = new ItemCustomLore().setRarity(EnumRarity.uncommon)
				.setUnlocalizedName("billet_flashlead").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":billet_flashlead");
		ModItems.billet_zfb_bismuth = new Item().setUnlocalizedName("billet_zfb_bismuth")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_zfb_bismuth");
		ModItems.billet_zfb_pu241 = new Item().setUnlocalizedName("billet_zfb_pu241")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_zfb_pu241");
		ModItems.billet_zfb_am_mix = new Item().setUnlocalizedName("billet_zfb_am_mix")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_zfb_am_mix");
		ModItems.billet_nuclear_waste = new Item().setUnlocalizedName("billet_nuclear_waste")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_nuclear_waste");

		ModItems.ingot_dura_steel = new ItemCustomLore().setUnlocalizedName("ingot_dura_steel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_dura_steel");
		ModItems.ingot_polymer = new ItemCustomLore().setUnlocalizedName("ingot_polymer")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_polymer");
		ModItems.ingot_bakelite = new ItemCustomLore().setUnlocalizedName("ingot_bakelite")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_bakelite");
		ModItems.ingot_rubber = new ItemCustomLore().setUnlocalizedName("ingot_rubber")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_rubber");
		// ingot_pet = new
		// ItemCustomLore().setUnlocalizedName("ingot_pet").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID
		// + ":ingot_pet");
		ModItems.ingot_pc = new ItemCustomLore().setUnlocalizedName("ingot_pc").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_pc");
		ModItems.ingot_pvc = new ItemCustomLore().setUnlocalizedName("ingot_pvc").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_pvc");
		ModItems.ingot_desh = new ItemCustomLore().setUnlocalizedName("ingot_desh")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_desh");
		ModItems.nugget_desh = new ItemCustomLore().setUnlocalizedName("nugget_desh")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_desh");
		ModItems.ingot_dineutronium = new ItemCustomLore().setUnlocalizedName("ingot_dineutronium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_dineutronium");
		ModItems.nugget_dineutronium = new ItemCustomLore().setUnlocalizedName("nugget_dineutronium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_dineutronium");
		ModItems.powder_dineutronium = new ItemCustomLore().setUnlocalizedName("powder_dineutronium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_dineutronium");
		ModItems.ingot_starmetal = new ItemStarmetal().setUnlocalizedName("ingot_starmetal")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_starmetal");
		ModItems.ingot_saturnite = new ItemCustomLore().setRarity(EnumRarity.rare).setUnlocalizedName("ingot_saturnite")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_saturnite");
		ModItems.plate_saturnite = new ItemCustomLore().setRarity(EnumRarity.rare).setUnlocalizedName("plate_saturnite")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_saturnite");
		ModItems.ingot_ferrouranium = new ItemCustomLore().setUnlocalizedName("ingot_ferrouranium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_ferrouranium");
		ModItems.ingot_fiberglass = new ItemCustomLore().setUnlocalizedName("ingot_fiberglass")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_fiberglass");
		ModItems.ingot_asbestos = new ItemCustomLore().setUnlocalizedName("ingot_asbestos")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_asbestos");
		ModItems.powder_asbestos = new ItemCustomLore().setUnlocalizedName("powder_asbestos")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_asbestos");
		ModItems.ingot_electronium = new ItemCustomLore().setUnlocalizedName("ingot_electronium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_electronium");
		ModItems.nugget_zirconium = new ItemCustomLore().setUnlocalizedName("nugget_zirconium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_zirconium");
		ModItems.nugget_mercury = new Item().setUnlocalizedName("nugget_mercury_tiny")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_mercury_tiny");
		ModItems.ingot_mercury = new ItemCustomLore().setUnlocalizedName("nugget_mercury")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_mercury");
		ModItems.bottle_mercury = new ItemCustomLore().setUnlocalizedName("bottle_mercury")
				.setContainerItem(Items.glass_bottle).setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":bottle_mercury");
		ModItems.ingot_calcium = new Item().setUnlocalizedName("ingot_calcium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_calcium");
		ModItems.powder_calcium = new Item().setUnlocalizedName("powder_calcium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_calcium");
		ModItems.ingot_cadmium = new Item().setUnlocalizedName("ingot_cadmium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_cadmium");
		ModItems.powder_cadmium = new Item().setUnlocalizedName("powder_cadmium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_cadmium");
		ModItems.powder_bismuth = new Item().setUnlocalizedName("powder_bismuth").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_bismuth");
		ModItems.ingot_mud = new Item().setUnlocalizedName("ingot_mud").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_mud");
		ModItems.ingot_cft = new Item().setUnlocalizedName("ingot_cft").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_cft");

		ModItems.ore_byproduct = new ItemByproduct().setUnlocalizedName("ore_byproduct")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":byproduct");

		ModItems.ore_bedrock = new ItemBedrockOre().setUnlocalizedName("ore_bedrock")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ore_bedrock");
		ModItems.ore_centrifuged = new ItemBedrockOre().setUnlocalizedName("ore_centrifuged")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ore_centrifuged");
		ModItems.ore_cleaned = new ItemBedrockOre().setUnlocalizedName("ore_cleaned")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ore_cleaned");
		ModItems.ore_separated = new ItemBedrockOre().setUnlocalizedName("ore_separated")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ore_separated");
		ModItems.ore_purified = new ItemBedrockOre().setUnlocalizedName("ore_purified")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ore_purified");
		ModItems.ore_nitrated = new ItemBedrockOre().setUnlocalizedName("ore_nitrated")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ore_nitrated");
		ModItems.ore_nitrocrystalline = new ItemBedrockOre().setUnlocalizedName("ore_nitrocrystalline")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ore_nitrocrystalline");
		ModItems.ore_deepcleaned = new ItemBedrockOre().setUnlocalizedName("ore_deepcleaned")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ore_deepcleaned");
		ModItems.ore_seared = new ItemBedrockOre().setUnlocalizedName("ore_seared")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ore_seared");
		// ore_radcleaned = new
		// ItemBedrockOre().setUnlocalizedName("ore_radcleaned").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID
		// + ":ore_radcleaned");
		ModItems.ore_enriched = new ItemBedrockOre().setUnlocalizedName("ore_enriched")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ore_enriched");

		ModItems.ingot_lanthanium = new ItemCustomLore().setUnlocalizedName("ingot_lanthanium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_lanthanium");
		ModItems.ingot_actinium = new ItemCustomLore().setUnlocalizedName("ingot_actinium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_actinium");

		ModItems.ingot_meteorite = new ItemHot(200).setUnlocalizedName("ingot_meteorite")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_meteorite");
		ModItems.ingot_meteorite_forged = new ItemHot(200).setUnlocalizedName("ingot_meteorite_forged")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_meteorite_forged");
		ModItems.blade_meteorite = new ItemHot(200).setUnlocalizedName("blade_meteorite")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":blade_meteorite");
		ModItems.ingot_steel_dusted = new ItemHotDusted(200).setUnlocalizedName("ingot_steel_dusted")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_steel_dusted");
		ModItems.ingot_chainsteel = new ItemHot(100).setUnlocalizedName("ingot_chainsteel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_chainsteel");

		ModItems.plate_armor_titanium = new Item().setUnlocalizedName("plate_armor_titanium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_armor_titanium");
		ModItems.plate_armor_ajr = new Item().setUnlocalizedName("plate_armor_ajr")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_armor_ajr");
		ModItems.plate_armor_hev = new Item().setUnlocalizedName("plate_armor_hev")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_armor_hev");
		ModItems.plate_armor_lunar = new Item().setUnlocalizedName("plate_armor_lunar")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_armor_lunar");
		ModItems.plate_armor_fau = new Item().setUnlocalizedName("plate_armor_fau")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_armor_fau");
		ModItems.plate_armor_dnt = new Item().setUnlocalizedName("plate_armor_dnt")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_armor_dnt");

		ModItems.oil_tar = new ItemEnumMulti(EnumTarType.class, true, true).setUnlocalizedName("oil_tar")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":oil_tar");
		ModItems.solid_fuel = new Item().setUnlocalizedName("solid_fuel").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":solid_fuel");
		ModItems.solid_fuel_presto = new Item().setUnlocalizedName("solid_fuel_presto")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":solid_fuel_presto");
		ModItems.solid_fuel_presto_triplet = new Item().setUnlocalizedName("solid_fuel_presto_triplet")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":solid_fuel_presto_triplet");
		ModItems.solid_fuel_bf = new Item().setUnlocalizedName("solid_fuel_bf").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":solid_fuel_bf");
		ModItems.solid_fuel_presto_bf = new Item().setUnlocalizedName("solid_fuel_presto_bf")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":solid_fuel_presto_bf");
		ModItems.solid_fuel_presto_triplet_bf = new Item().setUnlocalizedName("solid_fuel_presto_triplet_bf")
				.setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":solid_fuel_presto_triplet_bf");
		ModItems.rocket_fuel = new Item().setUnlocalizedName("rocket_fuel").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":rocket_fuel");
		ModItems.coke = new ItemEnumMulti(EnumCokeType.class, true, true).setUnlocalizedName("coke")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":coke");
		ModItems.lignite = new Item().setUnlocalizedName("lignite").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":lignite");
		ModItems.briquette = new ItemEnumMulti(EnumBriquetteType.class, true, true).setUnlocalizedName("briquette")
				.setCreativeTab(MainRegistry.partsTab);
		ModItems.powder_lignite = new Item().setUnlocalizedName("powder_lignite").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_lignite");
		ModItems.coal_infernal = new Item().setUnlocalizedName("coal_infernal").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":coal_infernal");
		ModItems.cinnebar = new Item().setUnlocalizedName("cinnebar").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":cinnebar");
		ModItems.powder_ash = new ItemEnumMulti(EnumAshType.class, true, true).setUnlocalizedName("powder_ash")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_ash");

		ModItems.ingot_gh336 = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("ingot_gh336")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_gh336");
		ModItems.nugget_gh336 = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("nugget_gh336")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_gh336");

		ModItems.ingot_australium = new ItemCustomLore().setRarity(EnumRarity.uncommon)
				.setUnlocalizedName("ingot_australium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_australium");
		ModItems.ingot_weidanium = new ItemCustomLore().setRarity(EnumRarity.uncommon)
				.setUnlocalizedName("ingot_weidanium").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":ingot_weidanium");
		ModItems.ingot_reiium = new ItemCustomLore().setUnlocalizedName("ingot_reiium").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":ingot_reiium");
		ModItems.ingot_unobtainium = new ItemCustomLore().setRarity(EnumRarity.uncommon)
				.setUnlocalizedName("ingot_unobtainium").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":ingot_unobtainium");
		ModItems.ingot_daffergon = new ItemCustomLore().setRarity(EnumRarity.uncommon)
				.setUnlocalizedName("ingot_daffergon").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":ingot_daffergon");
		ModItems.ingot_verticium = new ItemCustomLore().setRarity(EnumRarity.uncommon)
				.setUnlocalizedName("ingot_verticium").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":ingot_verticium");
		ModItems.nugget_australium = new ItemCustomLore().setRarity(EnumRarity.uncommon)
				.setUnlocalizedName("nugget_australium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_australium");
		ModItems.nugget_australium_lesser = new ItemCustomLore().setRarity(EnumRarity.uncommon)
				.setUnlocalizedName("nugget_australium_lesser").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_australium_lesser");
		ModItems.nugget_australium_greater = new ItemCustomLore().setRarity(EnumRarity.uncommon)
				.setUnlocalizedName("nugget_australium_greater").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_australium_greater");
		ModItems.nugget_weidanium = new ItemCustomLore().setRarity(EnumRarity.uncommon)
				.setUnlocalizedName("nugget_weidanium").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":nugget_weidanium");
		ModItems.nugget_reiium = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("nugget_reiium")
				.setCreativeTab(null).setTextureName(RefStrings.MODID + ":nugget_reiium");
		ModItems.nugget_unobtainium = new ItemCustomLore().setRarity(EnumRarity.uncommon)
				.setUnlocalizedName("nugget_unobtainium").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":nugget_unobtainium");
		ModItems.nugget_daffergon = new ItemCustomLore().setRarity(EnumRarity.uncommon)
				.setUnlocalizedName("nugget_daffergon").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":nugget_daffergon");
		ModItems.nugget_verticium = new ItemCustomLore().setRarity(EnumRarity.uncommon)
				.setUnlocalizedName("nugget_verticium").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":nugget_verticium");

		ModItems.nugget_th232 = new Item().setUnlocalizedName("nugget_th232").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_th232");
		ModItems.nugget_uranium = new Item().setUnlocalizedName("nugget_uranium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_uranium");
		ModItems.nugget_u233 = new Item().setUnlocalizedName("nugget_u233").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_u233");
		ModItems.nugget_u235 = new Item().setUnlocalizedName("nugget_u235").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_u235");
		ModItems.nugget_u238 = new Item().setUnlocalizedName("nugget_u238").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_u238");
		ModItems.nugget_plutonium = new Item().setUnlocalizedName("nugget_plutonium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_plutonium");
		ModItems.nugget_pu238 = new Item().setUnlocalizedName("nugget_pu238").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_pu238");
		ModItems.nugget_pu239 = new Item().setUnlocalizedName("nugget_pu239").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_pu239");
		ModItems.nugget_pu240 = new Item().setUnlocalizedName("nugget_pu240").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_pu240");
		ModItems.nugget_pu241 = new Item().setUnlocalizedName("nugget_pu241").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_pu241");
		ModItems.nugget_pu_mix = new Item().setUnlocalizedName("nugget_pu_mix").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_pu_mix");
		ModItems.nugget_am241 = new Item().setUnlocalizedName("nugget_am241").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_am241");
		ModItems.nugget_am242 = new Item().setUnlocalizedName("nugget_am242").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_am242");
		ModItems.nugget_am_mix = new Item().setUnlocalizedName("nugget_am_mix").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_am_mix");
		ModItems.nugget_neptunium = new Item().setUnlocalizedName("nugget_neptunium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_neptunium");
		ModItems.nugget_polonium = new Item().setUnlocalizedName("nugget_polonium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_polonium");
		ModItems.nugget_technetium = new Item().setUnlocalizedName("nugget_technetium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_technetium");
		ModItems.nugget_cobalt = new Item().setUnlocalizedName("nugget_cobalt").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_cobalt");
		ModItems.nugget_co60 = new Item().setUnlocalizedName("nugget_co60").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_co60");
		ModItems.nugget_sr90 = new Item().setUnlocalizedName("nugget_sr90").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_sr90");
		ModItems.nugget_au198 = new Item().setUnlocalizedName("nugget_au198").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_au198");
		ModItems.nugget_pb209 = new Item().setUnlocalizedName("nugget_pb209").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_pb209");
		ModItems.nugget_ra226 = new Item().setUnlocalizedName("nugget_ra226").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_ra226");
		ModItems.nugget_actinium = new Item().setUnlocalizedName("nugget_actinium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_actinium");
		ModItems.plate_titanium = new Item().setUnlocalizedName("plate_titanium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":plate_titanium");
		ModItems.plate_aluminium = new Item().setUnlocalizedName("plate_aluminium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_aluminium");
		ModItems.wire_red_copper = new Item().setUnlocalizedName("wire_red_copper")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":wire_red_copper");
		ModItems.wire_tungsten = new ItemCustomLore().setUnlocalizedName("wire_tungsten")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":wire_tungsten");
		ModItems.neutron_reflector = new Item().setUnlocalizedName("neutron_reflector")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":neutron_reflector");
		ModItems.nugget_lead = new Item().setUnlocalizedName("nugget_lead").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_lead");
		ModItems.ingot_bismuth = new ItemCustomLore().setUnlocalizedName("ingot_bismuth")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_bismuth");
		ModItems.nugget_bismuth = new Item().setUnlocalizedName("nugget_bismuth").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_bismuth");
		ModItems.ingot_arsenic = new ItemCustomLore().setUnlocalizedName("ingot_arsenic")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_arsenic");
		ModItems.nugget_arsenic = new Item().setUnlocalizedName("nugget_arsenic").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_arsenic");
		ModItems.ingot_tantalium = new ItemCustomLore().setUnlocalizedName("ingot_tantalium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_tantalium");
		ModItems.nugget_tantalium = new ItemCustomLore().setUnlocalizedName("nugget_tantalium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_tantalium");
		ModItems.ingot_niobium = new Item().setUnlocalizedName("ingot_niobium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_niobium");
		ModItems.ingot_osmiridium = new ItemCustomLore().setRarity(EnumRarity.rare)
				.setUnlocalizedName("ingot_osmiridium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_osmiridium");
		ModItems.nugget_osmiridium = new ItemCustomLore().setRarity(EnumRarity.rare)
				.setUnlocalizedName("nugget_osmiridium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_osmiridium");
		ModItems.nugget_schrabidium = new ItemCustomLore().setRarity(EnumRarity.rare)
				.setUnlocalizedName("nugget_schrabidium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_schrabidium");
		ModItems.nugget_beryllium = new Item().setUnlocalizedName("nugget_beryllium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_beryllium");
		ModItems.hazmat_cloth = new Item().setUnlocalizedName("hazmat_cloth").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":hazmat_cloth");
		ModItems.hazmat_cloth_red = new Item().setUnlocalizedName("hazmat_cloth_red")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":hazmat_cloth_red");
		ModItems.hazmat_cloth_grey = new Item().setUnlocalizedName("hazmat_cloth_grey")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":hazmat_cloth_grey");
		ModItems.asbestos_cloth = new Item().setUnlocalizedName("asbestos_cloth").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":asbestos_cloth");
		ModItems.rag = new ItemRag().setUnlocalizedName("rag").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":rag");
		ModItems.rag_damp = new Item().setUnlocalizedName("rag_damp").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":rag_damp");
		ModItems.rag_piss = new Item().setUnlocalizedName("rag_piss").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":rag_piss");
		ModItems.filter_coal = new Item().setUnlocalizedName("filter_coal").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":filter_coal");
		ModItems.ingot_hes = new Item().setUnlocalizedName("ingot_hes").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_hes");
		ModItems.ingot_les = new Item().setUnlocalizedName("ingot_les").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ingot_les");
		ModItems.nugget_hes = new Item().setUnlocalizedName("nugget_hes").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_hes");
		ModItems.nugget_les = new Item().setUnlocalizedName("nugget_les").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_les");
		ModItems.plate_combine_steel = new Item().setUnlocalizedName("plate_combine_steel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_combine_steel");

		ModItems.crystal_coal = new Item().setUnlocalizedName("crystal_coal").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":crystal_coal");
		ModItems.crystal_iron = new Item().setUnlocalizedName("crystal_iron").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":crystal_iron");
		ModItems.crystal_gold = new Item().setUnlocalizedName("crystal_gold").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":crystal_gold");
		ModItems.crystal_redstone = new Item().setUnlocalizedName("crystal_redstone")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_redstone");
		ModItems.crystal_lapis = new Item().setUnlocalizedName("crystal_lapis").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":crystal_lapis");
		ModItems.crystal_diamond = new Item().setUnlocalizedName("crystal_diamond")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_diamond");
		ModItems.crystal_uranium = new Item().setUnlocalizedName("crystal_uranium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_uranium");
		ModItems.crystal_thorium = new Item().setUnlocalizedName("crystal_thorium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_thorium");
		ModItems.crystal_plutonium = new Item().setUnlocalizedName("crystal_plutonium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_plutonium");
		ModItems.crystal_titanium = new Item().setUnlocalizedName("crystal_titanium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_titanium");
		ModItems.crystal_sulfur = new Item().setUnlocalizedName("crystal_sulfur").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":crystal_sulfur");
		ModItems.crystal_niter = new Item().setUnlocalizedName("crystal_niter").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":crystal_niter");
		ModItems.crystal_copper = new Item().setUnlocalizedName("crystal_copper").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":crystal_copper");
		ModItems.crystal_tungsten = new Item().setUnlocalizedName("crystal_tungsten")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_tungsten");
		ModItems.crystal_aluminium = new Item().setUnlocalizedName("crystal_aluminium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_aluminium");
		ModItems.crystal_fluorite = new Item().setUnlocalizedName("crystal_fluorite")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_fluorite");
		ModItems.crystal_beryllium = new Item().setUnlocalizedName("crystal_beryllium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_beryllium");
		ModItems.crystal_lead = new Item().setUnlocalizedName("crystal_lead").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":crystal_lead");
		ModItems.crystal_schraranium = new ItemCustomLore().setRarity(EnumRarity.rare)
				.setUnlocalizedName("crystal_schraranium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":crystal_schraranium");
		ModItems.crystal_schrabidium = new ItemCustomLore().setRarity(EnumRarity.rare)
				.setUnlocalizedName("crystal_schrabidium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":crystal_schrabidium");
		ModItems.crystal_rare = new Item().setUnlocalizedName("crystal_rare").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":crystal_rare");
		ModItems.crystal_phosphorus = new Item().setUnlocalizedName("crystal_phosphorus")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_phosphorus");
		ModItems.crystal_lithium = new Item().setUnlocalizedName("crystal_lithium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_lithium");
		ModItems.crystal_cobalt = new Item().setUnlocalizedName("crystal_cobalt").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":crystal_cobalt");
		ModItems.crystal_starmetal = new Item().setUnlocalizedName("crystal_starmetal")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_starmetal");
		ModItems.crystal_cinnebar = new Item().setUnlocalizedName("crystal_cinnebar")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_cinnebar");
		ModItems.crystal_trixite = new Item().setUnlocalizedName("crystal_trixite")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_trixite");
		ModItems.crystal_osmiridium = new Item().setUnlocalizedName("crystal_osmiridium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_osmiridium");
		ModItems.gem_sodalite = new ItemCustomLore().setUnlocalizedName("gem_sodalite")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":gem_sodalite");
		ModItems.gem_tantalium = new ItemCustomLore().setUnlocalizedName("gem_tantalium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":gem_tantalium");
		ModItems.gem_volcanic = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("gem_volcanic")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":gem_volcanic");
		ModItems.gem_alexandrite = new ItemAlexandrite().setUnlocalizedName("gem_alexandrite")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":gem_alexandrite");

		ModItems.powder_lead = new Item().setUnlocalizedName("powder_lead").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_lead");
		ModItems.powder_tantalium = new ItemCustomLore().setUnlocalizedName("powder_tantalium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_tantalium");
		ModItems.powder_neptunium = new Item().setUnlocalizedName("powder_neptunium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_neptunium");
		ModItems.powder_polonium = new Item().setUnlocalizedName("powder_polonium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_polonium");
		ModItems.powder_co60 = new Item().setUnlocalizedName("powder_co60").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_co60");
		ModItems.powder_sr90 = new Item().setUnlocalizedName("powder_sr90").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_sr90");
		ModItems.powder_sr90_tiny = new Item().setUnlocalizedName("powder_sr90_tiny")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_sr90_tiny");
		ModItems.powder_i131 = new Item().setUnlocalizedName("powder_i131").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_i131");
		ModItems.powder_i131_tiny = new Item().setUnlocalizedName("powder_i131_tiny")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_i131_tiny");
		ModItems.powder_xe135 = new Item().setUnlocalizedName("powder_xe135").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_xe135");
		ModItems.powder_xe135_tiny = new Item().setUnlocalizedName("powder_xe135_tiny")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_xe135_tiny");
		ModItems.powder_cs137 = new Item().setUnlocalizedName("powder_cs137").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_cs137");
		ModItems.powder_cs137_tiny = new Item().setUnlocalizedName("powder_cs137_tiny")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_cs137_tiny");
		ModItems.powder_au198 = new Item().setUnlocalizedName("powder_au198").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_au198");
		ModItems.powder_ra226 = new Item().setUnlocalizedName("powder_ra226").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_ra226");
		ModItems.powder_at209 = new Item().setUnlocalizedName("powder_at209").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_at209");
		ModItems.powder_schrabidium = new ItemCustomLore().setRarity(EnumRarity.rare)
				.setUnlocalizedName("powder_schrabidium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_schrabidium");
		ModItems.powder_schrabidate = new ItemCustomLore().setRarity(EnumRarity.rare)
				.setUnlocalizedName("powder_schrabidate").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_schrabidate");
		ModItems.powder_aluminium = new Item().setUnlocalizedName("powder_aluminium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_aluminium");
		ModItems.powder_beryllium = new Item().setUnlocalizedName("powder_beryllium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_beryllium");
		ModItems.powder_copper = new Item().setUnlocalizedName("powder_copper").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_copper");
		ModItems.powder_gold = new Item().setUnlocalizedName("powder_gold").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_gold");
		ModItems.powder_iron = new Item().setUnlocalizedName("powder_iron").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_iron");
		ModItems.powder_titanium = new Item().setUnlocalizedName("powder_titanium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_titanium");
		ModItems.powder_tungsten = new Item().setUnlocalizedName("powder_tungsten")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_tungsten");
		ModItems.powder_uranium = new Item().setUnlocalizedName("powder_uranium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_uranium");
		ModItems.powder_plutonium = new Item().setUnlocalizedName("powder_plutonium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_plutonium");
		ModItems.dust = new ItemCustomLore().setUnlocalizedName("dust").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":dust");
		ModItems.dust_tiny = new Item().setUnlocalizedName("dust_tiny").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":dust_tiny");
		ModItems.fallout = new Item().setUnlocalizedName("fallout").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":fallout");
		ModItems.powder_advanced_alloy = new Item().setUnlocalizedName("powder_advanced_alloy")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_advanced_alloy");
		ModItems.powder_tcalloy = new Item().setUnlocalizedName("powder_tcalloy").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_tcalloy");
		ModItems.powder_coal = new Item().setUnlocalizedName("powder_coal").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_coal");
		ModItems.powder_coal_tiny = new Item().setUnlocalizedName("powder_coal_tiny")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_coal_tiny");
		ModItems.powder_combine_steel = new Item().setUnlocalizedName("powder_combine_steel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_combine_steel");
		ModItems.powder_diamond = new Item().setUnlocalizedName("powder_diamond").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_diamond");
		ModItems.powder_emerald = new Item().setUnlocalizedName("powder_emerald").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_emerald");
		ModItems.powder_lapis = new Item().setUnlocalizedName("powder_lapis").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_lapis");
		ModItems.powder_quartz = new Item().setUnlocalizedName("powder_quartz").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_quartz");
		ModItems.powder_magnetized_tungsten = new Item().setUnlocalizedName("powder_magnetized_tungsten")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_magnetized_tungsten");
		ModItems.powder_chlorophyte = new Item().setUnlocalizedName("powder_chlorophyte")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_chlorophyte");
		ModItems.powder_red_copper = new Item().setUnlocalizedName("powder_red_copper")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_red_copper");
		ModItems.powder_steel = new Item().setUnlocalizedName("powder_steel").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_steel");
		ModItems.powder_lithium = new Item().setUnlocalizedName("powder_lithium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_lithium");
		ModItems.powder_zirconium = new Item().setUnlocalizedName("powder_zirconium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_zirconium");
		ModItems.powder_sodium = new Item().setUnlocalizedName("powder_sodium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_sodium");
		ModItems.redstone_depleted = new Item().setUnlocalizedName("redstone_depleted")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":redstone_depleted");
		ModItems.powder_power = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("powder_power")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_energy_alt");
		ModItems.powder_iodine = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("powder_iodine")
				.setCreativeTab(null).setTextureName(RefStrings.MODID + ":powder_iodine");
		ModItems.powder_thorium = new ItemCustomLore().setRarity(EnumRarity.uncommon)
				.setUnlocalizedName("powder_thorium").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":powder_thorium");
		ModItems.powder_neodymium = new ItemCustomLore().setRarity(EnumRarity.epic)
				.setUnlocalizedName("powder_neodymium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_neodymium");
		ModItems.powder_astatine = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("powder_astatine")
				.setCreativeTab(null).setTextureName(RefStrings.MODID + ":powder_astatine");
		ModItems.powder_caesium = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("powder_caesium")
				.setCreativeTab(null).setTextureName(RefStrings.MODID + ":powder_caesium");
		ModItems.powder_australium = new ItemCustomLore().setRarity(EnumRarity.uncommon)
				.setUnlocalizedName("powder_australium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_australium");
		ModItems.powder_weidanium = new ItemCustomLore().setRarity(EnumRarity.uncommon)
				.setUnlocalizedName("powder_weidanium").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":powder_weidanium");
		ModItems.powder_reiium = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("powder_reiium")
				.setCreativeTab(null).setTextureName(RefStrings.MODID + ":powder_reiium");
		ModItems.powder_unobtainium = new ItemCustomLore().setRarity(EnumRarity.uncommon)
				.setUnlocalizedName("powder_unobtainium").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":powder_unobtainium");
		ModItems.powder_daffergon = new ItemCustomLore().setRarity(EnumRarity.uncommon)
				.setUnlocalizedName("powder_daffergon").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":powder_daffergon");
		ModItems.powder_verticium = new ItemCustomLore().setRarity(EnumRarity.uncommon)
				.setUnlocalizedName("powder_verticium").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":powder_verticium");
		ModItems.powder_strontium = new ItemCustomLore().setRarity(EnumRarity.epic)
				.setUnlocalizedName("powder_strontium").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":powder_strontium");
		ModItems.powder_cobalt = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("powder_cobalt")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_cobalt");
		ModItems.powder_bromine = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("powder_bromine")
				.setCreativeTab(null).setTextureName(RefStrings.MODID + ":powder_bromine");
		ModItems.powder_niobium = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("powder_niobium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_niobium");
		ModItems.powder_tennessine = new ItemCustomLore().setRarity(EnumRarity.epic)
				.setUnlocalizedName("powder_tennessine").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":powder_tennessine");
		ModItems.powder_cerium = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("powder_cerium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_cerium");
		ModItems.powder_dura_steel = new ItemCustomLore().setUnlocalizedName("powder_dura_steel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_dura_steel");
		ModItems.powder_polymer = new ItemCustomLore().setUnlocalizedName("powder_polymer")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_polymer");
		ModItems.powder_bakelite = new ItemCustomLore().setUnlocalizedName("powder_bakelite")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_bakelite");
		ModItems.powder_euphemium = new ItemCustomLore().setRarity(EnumRarity.epic)
				.setUnlocalizedName("powder_euphemium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_euphemium");
		ModItems.powder_meteorite = new Item().setUnlocalizedName("powder_meteorite")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_meteorite");
		ModItems.powder_lanthanium = new ItemCustomLore().setRarity(EnumRarity.epic)
				.setUnlocalizedName("powder_lanthanium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_lanthanium");
		ModItems.powder_actinium = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("powder_actinium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_actinium");
		ModItems.powder_boron = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("powder_boron")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_boron");
		ModItems.powder_semtex_mix = new Item().setUnlocalizedName("powder_semtex_mix")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_semtex_mix");
		ModItems.powder_desh_mix = new Item().setUnlocalizedName("powder_desh_mix")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_desh_mix");
		ModItems.powder_desh_ready = new Item().setUnlocalizedName("powder_desh_ready")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_desh_ready");
		ModItems.powder_nitan_mix = new Item().setUnlocalizedName("powder_nitan_mix")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_nitan_mix");
		ModItems.powder_spark_mix = new Item().setUnlocalizedName("powder_spark_mix")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_spark_mix");
		ModItems.powder_desh = new Item().setUnlocalizedName("powder_desh").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_desh");
		ModItems.powder_steel_tiny = new Item().setUnlocalizedName("powder_steel_tiny")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_steel_tiny");
		ModItems.powder_lithium_tiny = new Item().setUnlocalizedName("powder_lithium_tiny")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_lithium_tiny");
		ModItems.powder_neodymium_tiny = new Item().setUnlocalizedName("powder_neodymium_tiny")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_neodymium_tiny");
		ModItems.powder_cobalt_tiny = new Item().setUnlocalizedName("powder_cobalt_tiny")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_cobalt_tiny");
		ModItems.powder_niobium_tiny = new Item().setUnlocalizedName("powder_niobium_tiny")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_niobium_tiny");
		ModItems.powder_cerium_tiny = new Item().setUnlocalizedName("powder_cerium_tiny")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_cerium_tiny");
		ModItems.powder_lanthanium_tiny = new Item().setUnlocalizedName("powder_lanthanium_tiny")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_lanthanium_tiny");
		ModItems.powder_actinium_tiny = new Item().setUnlocalizedName("powder_actinium_tiny")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_actinium_tiny");
		ModItems.powder_boron_tiny = new Item().setUnlocalizedName("powder_boron_tiny")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_boron_tiny");
		ModItems.powder_meteorite_tiny = new Item().setUnlocalizedName("powder_meteorite_tiny")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_meteorite_tiny");
		ModItems.powder_yellowcake = new Item().setUnlocalizedName("powder_yellowcake")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_yellowcake");
		ModItems.powder_magic = new Item().setUnlocalizedName("powder_magic").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_magic");
		ModItems.powder_cloud = new Item().setUnlocalizedName("powder_cloud").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_cloud");
		ModItems.powder_balefire = new Item().setUnlocalizedName("powder_balefire")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_balefire");
		ModItems.powder_sawdust = new Item().setUnlocalizedName("powder_sawdust").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_sawdust");
		ModItems.powder_flux = new Item().setUnlocalizedName("powder_flux").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_flux");
		ModItems.powder_fertilizer = new ItemFertilizer().setUnlocalizedName("powder_fertilizer")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_fertilizer");
		ModItems.powder_coltan_ore = new Item().setUnlocalizedName("powder_coltan_ore")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_coltan_ore");
		ModItems.powder_coltan = new Item().setUnlocalizedName("powder_coltan").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_coltan");
		ModItems.powder_tektite = new Item().setUnlocalizedName("powder_tektite").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_tektite");
		ModItems.powder_paleogenite = new Item().setUnlocalizedName("powder_paleogenite")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_paleogenite");
		ModItems.powder_paleogenite_tiny = new Item().setUnlocalizedName("powder_paleogenite_tiny")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_paleogenite_tiny");
		ModItems.powder_impure_osmiridium = new Item().setUnlocalizedName("powder_impure_osmiridium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_impure_osmiridium");
		ModItems.powder_borax = new Item().setUnlocalizedName("powder_borax").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":powder_borax");
		ModItems.powder_chlorocalcite = new Item().setUnlocalizedName("powder_chlorocalcite")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_chlorocalcite");

		ModItems.fragment_neodymium = new Item().setUnlocalizedName("fragment_neodymium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fragment_neodymium");
		ModItems.fragment_cobalt = new Item().setUnlocalizedName("fragment_cobalt")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fragment_cobalt");
		ModItems.fragment_niobium = new Item().setUnlocalizedName("fragment_niobium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fragment_niobium");
		ModItems.fragment_cerium = new Item().setUnlocalizedName("fragment_cerium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fragment_cerium");
		ModItems.fragment_lanthanium = new Item().setUnlocalizedName("fragment_lanthanium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fragment_lanthanium");
		ModItems.fragment_actinium = new Item().setUnlocalizedName("fragment_actinium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fragment_actinium");
		ModItems.fragment_boron = new Item().setUnlocalizedName("fragment_boron").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":fragment_boron");
		ModItems.fragment_meteorite = new Item().setUnlocalizedName("fragment_meteorite")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fragment_meteorite");
		ModItems.fragment_coltan = new Item().setUnlocalizedName("fragment_coltan")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fragment_coltan");

		ModItems.biomass = new Item().setUnlocalizedName("biomass").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":biomass");
		ModItems.biomass_compressed = new Item().setUnlocalizedName("biomass_compressed")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":biomass_compressed");
		ModItems.bio_wafer = new ItemLemon(4, 2F, false).setUnlocalizedName("bio_wafer")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":bio_wafer");
		ModItems.plant_item = new ItemEnumMulti(EnumPlantType.class, true, true).setUnlocalizedName("plant_item")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plant_item");

		ModItems.coil_copper = new Item().setUnlocalizedName("coil_copper").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":coil_copper");
		ModItems.coil_copper_torus = new Item().setUnlocalizedName("coil_copper_torus")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":coil_copper_torus");
		ModItems.coil_tungsten = new Item().setUnlocalizedName("coil_tungsten").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":coil_tungsten");
		ModItems.tank_steel = new Item().setUnlocalizedName("tank_steel").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":tank_steel");
		ModItems.motor = new Item().setUnlocalizedName("motor").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":motor");
		ModItems.motor_desh = new Item().setUnlocalizedName("motor_desh").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":motor_desh");
		ModItems.motor_bismuth = new Item().setUnlocalizedName("motor_bismuth").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":motor_bismuth");
		ModItems.centrifuge_element = new Item().setUnlocalizedName("centrifuge_element")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":centrifuge_element");
		// centrifuge_tower = new
		// Item().setUnlocalizedName("centrifuge_tower").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID
		// + ":centrifuge_tower");
		ModItems.reactor_core = new Item().setUnlocalizedName("reactor_core").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":reactor_core");
		ModItems.rtg_unit = new Item().setUnlocalizedName("rtg_unit").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":rtg_unit");
		// thermo_unit_empty = new
		// Item().setUnlocalizedName("thermo_unit_empty").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID
		// + ":thermo_unit_empty");
		// thermo_unit_endo= new
		// Item().setUnlocalizedName("thermo_unit_endo").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID
		// + ":thermo_unit_endo");
		// thermo_unit_exo = new
		// Item().setUnlocalizedName("thermo_unit_exo").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID
		// + ":thermo_unit_exo");
		ModItems.levitation_unit = new Item().setUnlocalizedName("levitation_unit")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":levitation_unit");
		ModItems.wire_aluminium = new Item().setUnlocalizedName("wire_aluminium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":wire_aluminium");
		ModItems.wire_copper = new Item().setUnlocalizedName("wire_copper").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":wire_copper");
		ModItems.wire_gold = new Item().setUnlocalizedName("wire_gold").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":wire_gold");
		ModItems.wire_schrabidium = new ItemCustomLore().setRarity(EnumRarity.rare)
				.setUnlocalizedName("wire_schrabidium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":wire_schrabidium");
		ModItems.wire_magnetized_tungsten = new Item().setUnlocalizedName("wire_magnetized_tungsten")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":wire_magnetized_tungsten");
		ModItems.coil_magnetized_tungsten = new Item().setUnlocalizedName("coil_magnetized_tungsten")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":coil_magnetized_tungsten");
		ModItems.coil_gold = new Item().setUnlocalizedName("coil_gold").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":coil_gold");
		ModItems.coil_gold_torus = new Item().setUnlocalizedName("coil_gold_torus")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":coil_gold_torus");
		// magnet_dee = new
		// Item().setUnlocalizedName("magnet_dee").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID
		// + ":magnet_dee");
		ModItems.magnet_circular = new Item().setUnlocalizedName("magnet_circular")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":magnet_circular");
		// cyclotron_tower = new
		// Item().setUnlocalizedName("cyclotron_tower").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID
		// + ":cyclotron_tower");
		ModItems.pellet_coal = new Item().setUnlocalizedName("pellet_coal").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":pellet_coal");
		ModItems.component_limiter = new Item().setUnlocalizedName("component_limiter")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":component_limiter");
		ModItems.component_emitter = new Item().setUnlocalizedName("component_emitter")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":component_emitter");
		ModItems.chlorine_pinwheel = new ItemInfiniteFluid(Fluids.CHLORINE, 1, 2)
				.setUnlocalizedName("chlorine_pinwheel").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":chlorine_pinwheel");
		ModItems.ring_starmetal = new Item().setUnlocalizedName("ring_starmetal").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ring_starmetal");
		ModItems.flywheel_beryllium = new Item().setUnlocalizedName("flywheel_beryllium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":flywheel_beryllium");
		ModItems.deuterium_filter = new Item().setUnlocalizedName("deuterium_filter")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":deuterium_filter");
		ModItems.parts_legendary = new ItemEnumMulti(EnumLegendaryType.class, false, true)
				.setUnlocalizedName("parts_legendary").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":parts_legendary");

		ModItems.gear_large = new ItemGear().setUnlocalizedName("gear_large").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":gear_large");
		ModItems.sawblade = new Item().setUnlocalizedName("sawblade").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":sawblade");

		ModItems.hull_small_steel = new Item().setUnlocalizedName("hull_small_steel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":hull_small_steel");
		ModItems.hull_small_aluminium = new ItemCustomLore().setUnlocalizedName("hull_small_aluminium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":hull_small_aluminium");
		ModItems.hull_big_steel = new Item().setUnlocalizedName("hull_big_steel").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":hull_big_steel");
		ModItems.hull_big_aluminium = new Item().setUnlocalizedName("hull_big_aluminium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":hull_big_aluminium");
		ModItems.hull_big_titanium = new Item().setUnlocalizedName("hull_big_titanium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":hull_big_titanium");
		ModItems.fins_flat = new Item().setUnlocalizedName("fins_flat").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":fins_flat");
		ModItems.fins_small_steel = new Item().setUnlocalizedName("fins_small_steel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fins_small_steel");
		ModItems.fins_big_steel = new Item().setUnlocalizedName("fins_big_steel").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":fins_big_steel");
		ModItems.fins_tri_steel = new Item().setUnlocalizedName("fins_tri_steel").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":fins_tri_steel");
		ModItems.fins_quad_titanium = new Item().setUnlocalizedName("fins_quad_titanium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fins_quad_titanium");
		ModItems.sphere_steel = new Item().setUnlocalizedName("sphere_steel").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":sphere_steel");
		ModItems.pedestal_steel = new Item().setUnlocalizedName("pedestal_steel").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":pedestal_steel");
		ModItems.dysfunctional_reactor = new Item().setUnlocalizedName("dysfunctional_reactor")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":dysfunctional_reactor");
		ModItems.rotor_steel = new Item().setUnlocalizedName("rotor_steel").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":rotor_steel");
		ModItems.generator_steel = new Item().setUnlocalizedName("generator_steel")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":generator_steel");
		ModItems.blade_titanium = new Item().setUnlocalizedName("blade_titanium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":blade_titanium");
		ModItems.turbine_titanium = new Item().setUnlocalizedName("turbine_titanium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":turbine_titanium");
		ModItems.generator_front = new Item().setUnlocalizedName("generator_front")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":generator_front");
		ModItems.blade_tungsten = new Item().setUnlocalizedName("blade_tungsten").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":blade_tungsten");
		ModItems.turbine_tungsten = new Item().setUnlocalizedName("turbine_tungsten")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":turbine_tungsten");

		ModItems.toothpicks = new Item().setUnlocalizedName("toothpicks").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":toothpicks");
		ModItems.ducttape = new Item().setUnlocalizedName("ducttape").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ducttape");
		ModItems.catalyst_clay = new Item().setUnlocalizedName("catalyst_clay").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":catalyst_clay");

//		ModItems.warhead_generic_small = new Item().setUnlocalizedName("warhead_generic_small")
//				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_generic_small");
//		ModItems.warhead_generic_medium = new Item().setUnlocalizedName("warhead_generic_medium")
//				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_generic_medium");
//		ModItems.warhead_generic_large = new Item().setUnlocalizedName("warhead_generic_large")
//				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_generic_large");
//		ModItems.warhead_incendiary_small = new Item().setUnlocalizedName("warhead_incendiary_small")
//				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_incendiary_small");
//		ModItems.warhead_incendiary_medium = new Item().setUnlocalizedName("warhead_incendiary_medium")
//				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_incendiary_medium");
//		ModItems.warhead_incendiary_large = new Item().setUnlocalizedName("warhead_incendiary_large")
//				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_incendiary_large");
//		ModItems.warhead_cluster_small = new Item().setUnlocalizedName("warhead_cluster_small")
//				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_cluster_small");
//		ModItems.warhead_cluster_medium = new Item().setUnlocalizedName("warhead_cluster_medium")
//				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_cluster_medium");
//		ModItems.warhead_cluster_large = new Item().setUnlocalizedName("warhead_cluster_large")
//				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_cluster_large");
//		ModItems.warhead_buster_small = new Item().setUnlocalizedName("warhead_buster_small")
//				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_buster_small");
//		ModItems.warhead_buster_medium = new Item().setUnlocalizedName("warhead_buster_medium")
//				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_buster_medium");
//		ModItems.warhead_buster_large = new Item().setUnlocalizedName("warhead_buster_large")
//				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_buster_large");
//		ModItems.warhead_nuclear = new Item().setUnlocalizedName("warhead_nuclear")
//				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_nuclear");
//		ModItems.warhead_mirvlet = new Item().setUnlocalizedName("warhead_mirvlet").setCreativeTab(null)
//				.setTextureName(RefStrings.MODID + ":warhead_mirvlet");
//		ModItems.warhead_mirv = new Item().setUnlocalizedName("warhead_mirv").setCreativeTab(MainRegistry.partsTab)
//				.setTextureName(RefStrings.MODID + ":warhead_mirv");
//		ModItems.warhead_volcano = new Item().setUnlocalizedName("warhead_volcano")
//				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_volcano");
//		ModItems.warhead_thermo_endo = new Item().setUnlocalizedName("warhead_thermo_endo")
//				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_thermo_endo");
//		ModItems.warhead_thermo_exo = new Item().setUnlocalizedName("warhead_thermo_exo")
//				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_thermo_exo");
		
		ModItems.mold_base = new Item().setUnlocalizedName("mold_base").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":mold_base");
		ModItems.mold = new ItemMold().setUnlocalizedName("mold").setCreativeTab(MainRegistry.controlTab);
		ModItems.scraps = new ItemScraps().aot(Mats.MAT_BISMUTH, "scraps_bismuth").setUnlocalizedName("scraps").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":scraps");
		ModItems.plate_cast = new ItemAutogen(MaterialShapes.CASTPLATE).aot(Mats.MAT_BISMUTH, "plate_cast_bismuth").setUnlocalizedName("plate_cast").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_cast");
		ModItems.plate_welded = new ItemAutogen(MaterialShapes.WELDEDPLATE).setUnlocalizedName("plate_welded").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_welded");
		ModItems.heavy_component = new ItemAutogen(MaterialShapes.HEAVY_COMPONENT).setUnlocalizedName("heavy_component").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":heavy_component");
		ModItems.wire_dense = new ItemAutogen(MaterialShapes.DENSEWIRE).setUnlocalizedName("wire_dense").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":wire_dense");
		
		ModItems.fuel_tank_small = new Item().setUnlocalizedName("fuel_tank_small")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fuel_tank_small");
		ModItems.fuel_tank_medium = new Item().setUnlocalizedName("fuel_tank_medium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fuel_tank_medium");
		ModItems.fuel_tank_large = new Item().setUnlocalizedName("fuel_tank_large")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fuel_tank_large");

		ModItems.thruster_small = new Item().setUnlocalizedName("thruster_small").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":thruster_small");
		ModItems.thruster_medium = new Item().setUnlocalizedName("thruster_medium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":thruster_medium");
		ModItems.thruster_large = new Item().setUnlocalizedName("thruster_large").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":thruster_large");

		ModItems.sat_head_mapper = new Item().setUnlocalizedName("sat_head_mapper")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":sat_head_mapper");
		ModItems.sat_head_scanner = new Item().setUnlocalizedName("sat_head_scanner")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":sat_head_scanner");
		ModItems.sat_head_radar = new Item().setUnlocalizedName("sat_head_radar").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":sat_head_radar");
		ModItems.sat_head_laser = new Item().setUnlocalizedName("sat_head_laser").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":sat_head_laser");
		ModItems.sat_head_resonator = new Item().setUnlocalizedName("sat_head_resonator")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":sat_head_resonator");

		ModItems.seg_10 = new Item().setUnlocalizedName("seg_10").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":seg_10");
		ModItems.seg_15 = new Item().setUnlocalizedName("seg_15").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":seg_15");
		ModItems.seg_20 = new Item().setUnlocalizedName("seg_20").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":seg_20");

		ModItems.chopper_head = new Item().setUnlocalizedName("chopper_head").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":chopper_head");
		ModItems.chopper_gun = new Item().setUnlocalizedName("chopper_gun").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":chopper_gun");
		ModItems.chopper_torso = new Item().setUnlocalizedName("chopper_torso").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":chopper_torso");
		ModItems.chopper_tail = new Item().setUnlocalizedName("chopper_tail").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":chopper_tail");
		ModItems.chopper_wing = new Item().setUnlocalizedName("chopper_wing").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":chopper_wing");
		ModItems.chopper_blades = new Item().setUnlocalizedName("chopper_blades").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":chopper_blades");
		ModItems.combine_scrap = new Item().setUnlocalizedName("combine_scrap").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":combine_scrap");

		ModItems.shimmer_head = new Item().setUnlocalizedName("shimmer_head").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":shimmer_head_original");
		ModItems.shimmer_axe_head = new Item().setUnlocalizedName("shimmer_axe_head")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":shimmer_axe_head");
		ModItems.shimmer_handle = new Item().setUnlocalizedName("shimmer_handle").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":shimmer_handle");

		// telepad = new
		// Item().setUnlocalizedName("telepad").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID
		// + ":telepad");
		ModItems.entanglement_kit = new ItemCustomLore().setUnlocalizedName("entanglement_kit")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":entanglement_kit");

		ModItems.circuit_raw = new Item().setUnlocalizedName("circuit_raw").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":circuit_raw");
		ModItems.circuit_aluminium = new Item().setUnlocalizedName("circuit_aluminium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":circuit_aluminium");
		ModItems.circuit_copper = new Item().setUnlocalizedName("circuit_copper").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":circuit_copper");
		ModItems.circuit_red_copper = new Item().setUnlocalizedName("circuit_red_copper")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":circuit_red_copper");
		ModItems.circuit_gold = new Item().setUnlocalizedName("circuit_gold").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":circuit_gold");
		ModItems.circuit_schrabidium = new ItemCustomLore().setRarity(EnumRarity.rare)
				.setUnlocalizedName("circuit_schrabidium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":circuit_schrabidium");
		ModItems.circuit_bismuth_raw = new Item().setUnlocalizedName("circuit_bismuth_raw")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":circuit_bismuth_raw");
		ModItems.circuit_bismuth = new ItemCustomLore().setRarity(EnumRarity.uncommon)
				.setUnlocalizedName("circuit_bismuth").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":circuit_bismuth");
		ModItems.circuit_arsenic_raw = new Item().setUnlocalizedName("circuit_arsenic_raw")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":circuit_arsenic_raw");
		ModItems.circuit_arsenic = new ItemCustomLore().setRarity(EnumRarity.uncommon)
				.setUnlocalizedName("circuit_arsenic").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":circuit_arsenic");
		ModItems.circuit_tantalium_raw = new Item().setUnlocalizedName("circuit_tantalium_raw")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":circuit_tantalium_raw");
		ModItems.circuit_tantalium = new ItemCustomLore().setRarity(EnumRarity.uncommon)
				.setUnlocalizedName("circuit_tantalium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":circuit_tantalium");
		ModItems.crt_display = new Item().setUnlocalizedName("crt_display").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":crt_display");
		ModItems.circuit_star_piece = (ItemEnumMulti) new ItemEnumMulti(ScrapType.class, true, true)
				.setUnlocalizedName("circuit_star_piece").setCreativeTab(null);
		ModItems.circuit_star_component = (ItemEnumMulti) new ItemCircuitStarComponent()
				.setUnlocalizedName("circuit_star_component").setCreativeTab(null);
		ModItems.circuit_star = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("circuit_star")
				.setCreativeTab(null).setTextureName(RefStrings.MODID + ":circuit_star");
		ModItems.circuit_targeting_tier1 = new Item().setUnlocalizedName("circuit_targeting_tier1")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":circuit_targeting_tier1");
		ModItems.circuit_targeting_tier2 = new Item().setUnlocalizedName("circuit_targeting_tier2")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":circuit_targeting_tier2");
		ModItems.circuit_targeting_tier3 = new Item().setUnlocalizedName("circuit_targeting_tier3")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":circuit_targeting_tier3");
		ModItems.circuit_targeting_tier4 = new Item().setUnlocalizedName("circuit_targeting_tier4")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":circuit_targeting_tier4");
		ModItems.circuit_targeting_tier5 = new Item().setUnlocalizedName("circuit_targeting_tier5")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":circuit_targeting_tier5");
		ModItems.circuit_targeting_tier6 = new Item().setUnlocalizedName("circuit_targeting_tier6")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":circuit_targeting_tier6");
		ModItems.mechanism_revolver_1 = new Item().setUnlocalizedName("mechanism_revolver_1")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":mechanism_1");
		ModItems.mechanism_revolver_2 = new Item().setUnlocalizedName("mechanism_revolver_2")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":mechanism_3");
		ModItems.mechanism_rifle_1 = new Item().setUnlocalizedName("mechanism_rifle_1")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":mechanism_2");
		ModItems.mechanism_rifle_2 = new Item().setUnlocalizedName("mechanism_rifle_2")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":mechanism_4");
		ModItems.mechanism_launcher_1 = new Item().setUnlocalizedName("mechanism_launcher_1")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":mechanism_5");
		ModItems.mechanism_launcher_2 = new Item().setUnlocalizedName("mechanism_launcher_2")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":mechanism_6");
		ModItems.mechanism_special = new Item().setUnlocalizedName("mechanism_special")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":mechanism_7");
		ModItems.casing_357 = new Item().setUnlocalizedName("casing_357").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":casing_357");
		ModItems.casing_44 = new Item().setUnlocalizedName("casing_44").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":casing_44");
		ModItems.casing_9 = new Item().setUnlocalizedName("casing_9").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":casing_9");
		ModItems.casing_50 = new Item().setUnlocalizedName("casing_50").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":casing_50");
		ModItems.casing_buckshot = new Item().setUnlocalizedName("casing_buckshot")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":casing_buckshot");
		ModItems.assembly_iron = new Item().setUnlocalizedName("assembly_iron").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":assembly_iron");
		ModItems.assembly_steel = new Item().setUnlocalizedName("assembly_steel").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":assembly_steel");
		ModItems.assembly_lead = new Item().setUnlocalizedName("assembly_lead").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":assembly_lead");
		ModItems.assembly_gold = new Item().setUnlocalizedName("assembly_gold").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":assembly_gold");
		ModItems.assembly_schrabidium = new Item().setUnlocalizedName("assembly_schrabidium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":assembly_schrabidium");
		ModItems.assembly_nightmare = new Item().setUnlocalizedName("assembly_nightmare")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":assembly_nightmare");
		ModItems.assembly_desh = new Item().setUnlocalizedName("assembly_desh").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":assembly_desh");
		// assembly_pip = new
		// Item().setUnlocalizedName("assembly_pip").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID
		// + ":assembly_pip");
		ModItems.assembly_nopip = new Item().setUnlocalizedName("assembly_nopip").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":assembly_nopip");
		ModItems.assembly_smg = new Item().setUnlocalizedName("assembly_smg").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":assembly_smg");
		ModItems.assembly_556 = new Item().setUnlocalizedName("assembly_556").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":assembly_556");
		ModItems.assembly_762 = new Item().setUnlocalizedName("assembly_762").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":assembly_762");
		ModItems.assembly_45 = new Item().setUnlocalizedName("assembly_45").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":assembly_45");
		ModItems.assembly_uzi = new Item().setUnlocalizedName("assembly_uzi").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":assembly_uzi");
		ModItems.assembly_actionexpress = new Item().setUnlocalizedName("assembly_actionexpress")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":assembly_actionexpress");
		ModItems.assembly_calamity = new Item().setUnlocalizedName("assembly_calamity")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":assembly_calamity");
		ModItems.assembly_lacunae = new Item().setUnlocalizedName("assembly_lacunae")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":assembly_lacunae");
		ModItems.assembly_nuke = new Item().setUnlocalizedName("assembly_nuke").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":assembly_nuke");
		ModItems.assembly_luna = new Item().setUnlocalizedName("assembly_luna").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":assembly_luna");
		ModItems.folly_shell = new Item().setUnlocalizedName("folly_shell").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":folly_shell");
		ModItems.folly_bullet = new Item().setUnlocalizedName("folly_bullet").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":folly_bullet");
		ModItems.folly_bullet_nuclear = new Item().setUnlocalizedName("folly_bullet_nuclear")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":folly_bullet_nuclear");
		ModItems.folly_bullet_du = new Item().setUnlocalizedName("folly_bullet_du")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":folly_bullet_du");

		ModItems.wiring_red_copper = new ItemWiring().setUnlocalizedName("wiring_red_copper")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":wiring_red_copper");

		ModItems.pellet_rtg_depleted = new ItemRTGPelletDepleted().setContainerItem(ModItems.plate_iron)
				.setUnlocalizedName("pellet_rtg_depleted").setCreativeTab(MainRegistry.controlTab);

		ModItems.pellet_rtg_radium = new ItemRTGPellet(3)
				.setDecays(DepletedRTGMaterial.LEAD,
						(long) (RTGUtil.getLifespan(16.0F, HalfLifeType.LONG, false) * 1.5))
				.setUnlocalizedName("pellet_rtg_radium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":pellet_rtg_radium");
		ModItems.pellet_rtg_weak = new ItemRTGPellet(5)
				.setDecays(DepletedRTGMaterial.LEAD, (long) (RTGUtil.getLifespan(1.0F, HalfLifeType.LONG, false) * 1.5))
				.setUnlocalizedName("pellet_rtg_weak").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":pellet_rtg_weak");
		ModItems.pellet_rtg = new ItemRTGPellet(10)
				.setDecays(DepletedRTGMaterial.LEAD,
						(long) (RTGUtil.getLifespan(87.7F, HalfLifeType.MEDIUM, false) * 1.5))
				.setUnlocalizedName("pellet_rtg").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":pellet_rtg");
		ModItems.pellet_rtg_strontium = new ItemRTGPellet(15)
				.setDecays(DepletedRTGMaterial.ZIRCONIUM,
						(long) (RTGUtil.getLifespan(29.0F, HalfLifeType.MEDIUM, false) * 1.5))
				.setUnlocalizedName("pellet_rtg_strontium").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":pellet_rtg_strontium");
		ModItems.pellet_rtg_cobalt = new ItemRTGPellet(15)
				.setDecays(DepletedRTGMaterial.NICKEL,
						(long) (RTGUtil.getLifespan(5.3F, HalfLifeType.MEDIUM, false) * 1.5))
				.setUnlocalizedName("pellet_rtg_cobalt").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":pellet_rtg_cobalt");
		ModItems.pellet_rtg_actinium = new ItemRTGPellet(20)
				.setDecays(DepletedRTGMaterial.LEAD,
						(long) (RTGUtil.getLifespan(21.8F, HalfLifeType.MEDIUM, false) * 1.5))
				.setUnlocalizedName("pellet_rtg_actinium").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":pellet_rtg_actinium");
		ModItems.pellet_rtg_americium = new ItemRTGPellet(20)
				.setDecays(DepletedRTGMaterial.NEPTUNIUM,
						(long) (RTGUtil.getLifespan(4.7F, HalfLifeType.LONG, false) * 1.5))
				.setUnlocalizedName("pellet_rtg_americium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":pellet_rtg_americium");
		ModItems.pellet_rtg_berkelium = new ItemRTGPellet(20).setUnlocalizedName("pellet_rtg_berkelium")
				.setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":pellet_rtg_berkelium");
		ModItems.pellet_rtg_polonium = new ItemRTGPellet(50)
				.setDecays(DepletedRTGMaterial.LEAD,
						(long) (RTGUtil.getLifespan(138.0F, HalfLifeType.SHORT, false) * 1.5))
				.setUnlocalizedName("pellet_rtg_polonium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":pellet_rtg_polonium");
		ModItems.pellet_rtg_gold = new ItemRTGPellet(VersatileConfig.rtgDecay() ? 200 : 100)
				.setDecays(DepletedRTGMaterial.MERCURY,
						(long) (RTGUtil.getLifespan(2.7F, HalfLifeType.SHORT, false) * 1.5))
				.setUnlocalizedName("pellet_rtg_gold").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":pellet_rtg_gold");
		ModItems.pellet_rtg_lead = new ItemRTGPellet(VersatileConfig.rtgDecay() ? 600 : 200)
				.setDecays(DepletedRTGMaterial.BISMUTH,
						(long) (RTGUtil.getLifespan(0.3F, HalfLifeType.SHORT, false) * 1.5))
				.setUnlocalizedName("pellet_rtg_lead").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":pellet_rtg_lead");

		ModItems.tritium_deuterium_cake = new ItemCustomLore().setUnlocalizedName("tritium_deuterium_cake")
				.setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":tritium_deuterium_cake");

		ModItems.piston_selenium = new Item().setUnlocalizedName("piston_selenium")
				.setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":piston_selenium");
		ModItems.piston_set = new ItemPistons().setUnlocalizedName("piston_set").setCreativeTab(MainRegistry.controlTab)
				.setMaxStackSize(1);
		ModItems.drillbit = new ItemDrillbit().setUnlocalizedName("drillbit").setCreativeTab(MainRegistry.controlTab)
				.setMaxStackSize(1);

		// crystal_energy = new
		// ItemCustomLore().setUnlocalizedName("crystal_energy").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID
		// + ":crystal_energy");
		// pellet_coolant = new
		// ItemCustomLore().setUnlocalizedName("pellet_coolant").setMaxDamage(41400).setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID
		// + ":pellet_coolant");

		ModItems.rune_blank = new ItemCustomLore().setUnlocalizedName("rune_blank")
				.setCreativeTab(MainRegistry.partsTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":rune_blank");
		ModItems.rune_isa = new ItemCustomLore().setUnlocalizedName("rune_isa").setCreativeTab(MainRegistry.partsTab)
				.setMaxStackSize(1).setTextureName(RefStrings.MODID + ":rune_isa");
		ModItems.rune_dagaz = new ItemCustomLore().setUnlocalizedName("rune_dagaz")
				.setCreativeTab(MainRegistry.partsTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":rune_dagaz");
		ModItems.rune_hagalaz = new ItemCustomLore().setUnlocalizedName("rune_hagalaz")
				.setCreativeTab(MainRegistry.partsTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":rune_hagalaz");
		ModItems.rune_jera = new ItemCustomLore().setUnlocalizedName("rune_jera").setCreativeTab(MainRegistry.partsTab)
				.setMaxStackSize(1).setTextureName(RefStrings.MODID + ":rune_jera");
		ModItems.rune_thurisaz = new ItemCustomLore().setUnlocalizedName("rune_thurisaz")
				.setCreativeTab(MainRegistry.partsTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":rune_thurisaz");

		ModItems.ams_catalyst_blank = new Item().setUnlocalizedName("ams_catalyst_blank")
				.setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":ams_catalyst_blank");
		ModItems.ams_catalyst_aluminium = new ItemCatalyst(0xCCCCCC, 1000000, 1.15F, 0.85F, 1.15F)
				.setUnlocalizedName("ams_catalyst_aluminium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":ams_catalyst_aluminium");
		ModItems.ams_catalyst_beryllium = new ItemCatalyst(0x97978B, 0, 1.25F, 0.95F, 1.05F)
				.setUnlocalizedName("ams_catalyst_beryllium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":ams_catalyst_beryllium");
		ModItems.ams_catalyst_caesium = new ItemCatalyst(0x6400FF, 2500000, 1.00F, 0.85F, 1.15F)
				.setUnlocalizedName("ams_catalyst_caesium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":ams_catalyst_caesium");
		ModItems.ams_catalyst_cerium = new ItemCatalyst(0x1D3FFF, 1000000, 1.15F, 1.15F, 0.85F)
				.setUnlocalizedName("ams_catalyst_cerium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":ams_catalyst_cerium");
		ModItems.ams_catalyst_cobalt = new ItemCatalyst(0x789BBE, 0, 1.25F, 1.05F, 0.95F)
				.setUnlocalizedName("ams_catalyst_cobalt").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":ams_catalyst_cobalt");
		ModItems.ams_catalyst_copper = new ItemCatalyst(0xAADE29, 0, 1.25F, 1.00F, 1.00F)
				.setUnlocalizedName("ams_catalyst_copper").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":ams_catalyst_copper");
		ModItems.ams_catalyst_dineutronium = new ItemCatalyst(0x334077, 2500000, 1.00F, 1.15F, 0.85F)
				.setUnlocalizedName("ams_catalyst_dineutronium").setCreativeTab(MainRegistry.controlTab)
				.setMaxStackSize(1).setTextureName(RefStrings.MODID + ":ams_catalyst_dineutronium");
		ModItems.ams_catalyst_euphemium = new ItemCatalyst(0xFF9CD2, 2500000, 1.00F, 1.00F, 1.00F)
				.setUnlocalizedName("ams_catalyst_euphemium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":ams_catalyst_euphemium");
		ModItems.ams_catalyst_iron = new ItemCatalyst(0xFF7E22, 1000000, 1.15F, 0.95F, 1.05F)
				.setUnlocalizedName("ams_catalyst_iron").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":ams_catalyst_iron");
		ModItems.ams_catalyst_lithium = new ItemCatalyst(0xFF2727, 0, 1.25F, 0.85F, 1.15F)
				.setUnlocalizedName("ams_catalyst_lithium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":ams_catalyst_lithium");
		ModItems.ams_catalyst_niobium = new ItemCatalyst(0x3BF1B6, 1000000, 1.15F, 1.05F, 0.95F)
				.setUnlocalizedName("ams_catalyst_niobium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":ams_catalyst_niobium");
		ModItems.ams_catalyst_schrabidium = new ItemCatalyst(0x32FFFF, 2500000, 1.00F, 1.05F, 0.95F)
				.setUnlocalizedName("ams_catalyst_schrabidium").setCreativeTab(MainRegistry.controlTab)
				.setMaxStackSize(1).setTextureName(RefStrings.MODID + ":ams_catalyst_schrabidium");
		ModItems.ams_catalyst_strontium = new ItemCatalyst(0xDD0D35, 1000000, 1.15F, 1.00F, 1.00F)
				.setUnlocalizedName("ams_catalyst_strontium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":ams_catalyst_strontium");
		ModItems.ams_catalyst_thorium = new ItemCatalyst(0x653B22, 2500000, 1.00F, 0.95F, 1.05F)
				.setUnlocalizedName("ams_catalyst_thorium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":ams_catalyst_thorium");
		ModItems.ams_catalyst_tungsten = new ItemCatalyst(0xF5FF48, 0, 1.25F, 1.15F, 0.85F)
				.setUnlocalizedName("ams_catalyst_tungsten").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":ams_catalyst_tungsten");

		ModItems.cell_empty = new Item().setUnlocalizedName("cell_empty").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":cell_empty");
		ModItems.cell_uf6 = new Item().setUnlocalizedName("cell_uf6").setCreativeTab(MainRegistry.controlTab)
				.setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":cell_uf6");
		ModItems.cell_puf6 = new Item().setUnlocalizedName("cell_puf6").setCreativeTab(MainRegistry.controlTab)
				.setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":cell_puf6");
		ModItems.cell_antimatter = new ItemDrop().setUnlocalizedName("cell_antimatter")
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty)
				.setTextureName(RefStrings.MODID + ":cell_antimatter");
		ModItems.cell_deuterium = new Item().setUnlocalizedName("cell_deuterium")
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty)
				.setTextureName(RefStrings.MODID + ":cell_deuterium");
		ModItems.cell_tritium = new Item().setUnlocalizedName("cell_tritium").setCreativeTab(MainRegistry.controlTab)
				.setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":cell_tritium");
		ModItems.cell_sas3 = new ItemCustomLore().setRarity(EnumRarity.rare).setUnlocalizedName("cell_sas3")
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty)
				.setTextureName(RefStrings.MODID + ":cell_sas3");
		ModItems.cell_anti_schrabidium = new ItemDrop().setUnlocalizedName("cell_anti_schrabidium")
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty)
				.setTextureName(RefStrings.MODID + ":cell_anti_schrabidium");
		ModItems.cell_balefire = new Item().setUnlocalizedName("cell_balefire").setCreativeTab(MainRegistry.controlTab)
				.setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":cell_balefire");

		ModItems.demon_core_open = new ItemDemonCore().setUnlocalizedName("demon_core_open")
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":demon_core_open");
		ModItems.demon_core_closed = new Item().setUnlocalizedName("demon_core_closed")
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":demon_core_closed");

		ModItems.particle_empty = new Item().setUnlocalizedName("particle_empty")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":particle_empty");
		ModItems.particle_hydrogen = new Item().setUnlocalizedName("particle_hydrogen")
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty)
				.setTextureName(RefStrings.MODID + ":particle_hydrogen");
		ModItems.particle_copper = new Item().setUnlocalizedName("particle_copper")
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty)
				.setTextureName(RefStrings.MODID + ":particle_copper");
		ModItems.particle_lead = new Item().setUnlocalizedName("particle_lead").setCreativeTab(MainRegistry.controlTab)
				.setContainerItem(ModItems.particle_empty).setTextureName(RefStrings.MODID + ":particle_lead");
		ModItems.particle_aproton = new Item().setUnlocalizedName("particle_aproton")
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty)
				.setTextureName(RefStrings.MODID + ":particle_aproton");
		ModItems.particle_aelectron = new Item().setUnlocalizedName("particle_aelectron")
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty)
				.setTextureName(RefStrings.MODID + ":particle_aelectron");
		ModItems.particle_amat = new Item().setUnlocalizedName("particle_amat").setCreativeTab(MainRegistry.controlTab)
				.setContainerItem(ModItems.particle_empty).setTextureName(RefStrings.MODID + ":particle_amat");
		ModItems.particle_aschrab = new Item().setUnlocalizedName("particle_aschrab")
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty)
				.setTextureName(RefStrings.MODID + ":particle_aschrab");
		ModItems.particle_higgs = new Item().setUnlocalizedName("particle_higgs")
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty)
				.setTextureName(RefStrings.MODID + ":particle_higgs");
		ModItems.particle_muon = new Item().setUnlocalizedName("particle_muon").setCreativeTab(MainRegistry.controlTab)
				.setContainerItem(ModItems.particle_empty).setTextureName(RefStrings.MODID + ":particle_muon");
		ModItems.particle_tachyon = new Item().setUnlocalizedName("particle_tachyon")
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty)
				.setTextureName(RefStrings.MODID + ":particle_tachyon");
		ModItems.particle_strange = new Item().setUnlocalizedName("particle_strange")
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty)
				.setTextureName(RefStrings.MODID + ":particle_strange");
		ModItems.particle_dark = new Item().setUnlocalizedName("particle_dark").setCreativeTab(MainRegistry.controlTab)
				.setContainerItem(ModItems.particle_empty).setTextureName(RefStrings.MODID + ":particle_dark");
		ModItems.particle_sparkticle = new Item().setUnlocalizedName("particle_sparkticle")
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty)
				.setTextureName(RefStrings.MODID + ":particle_sparkticle");
		ModItems.particle_digamma = new ItemDigamma(60).setUnlocalizedName("particle_digamma")
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty)
				.setTextureName(RefStrings.MODID + ":particle_digamma");
		ModItems.particle_lutece = new Item().setUnlocalizedName("particle_lutece")
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty)
				.setTextureName(RefStrings.MODID + ":particle_lutece");
		ModItems.singularity_micro = new ItemDrop().setUnlocalizedName("singularity_micro")
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.nuclear_waste)
				.setTextureName(RefStrings.MODID + ":singularity_micro");

		ModItems.singularity = new ItemDrop().setUnlocalizedName("singularity").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.nuclear_waste)
				.setTextureName(RefStrings.MODID + ":singularity");
		ModItems.singularity_counter_resonant = new ItemDrop().setUnlocalizedName("singularity_counter_resonant")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.nuclear_waste)
				.setTextureName(RefStrings.MODID + ":singularity_alt");
		ModItems.singularity_super_heated = new ItemDrop().setUnlocalizedName("singularity_super_heated")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.nuclear_waste)
				.setTextureName(RefStrings.MODID + ":singularity_5");
		ModItems.black_hole = new ItemDrop().setUnlocalizedName("black_hole").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.nuclear_waste)
				.setTextureName(RefStrings.MODID + ":singularity_4");
		ModItems.singularity_spark = new ItemDrop().setUnlocalizedName("singularity_spark").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.nuclear_waste)
				.setTextureName(RefStrings.MODID + ":singularity_spark_alt");
		ModItems.pellet_antimatter = new ItemDrop().setUnlocalizedName("pellet_antimatter")
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty)
				.setTextureName(RefStrings.MODID + ":pellet_antimatter");
		ModItems.crystal_xen = new ItemDrop().setUnlocalizedName("crystal_xen").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":crystal_xen");
		ModItems.inf_water = new ItemInfiniteFluid(Fluids.WATER, 50).setUnlocalizedName("inf_water").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":inf_water");
		ModItems.inf_water_mk2 = new ItemInfiniteFluid(Fluids.WATER, 500).setUnlocalizedName("inf_water_mk2")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":inf_water_mk2");

		ModItems.stamp_stone_flat = new ItemStamp(10, StampType.FLAT).setUnlocalizedName("stamp_stone_flat")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":stamp_stone_flat");
		ModItems.stamp_stone_plate = new ItemStamp(10, StampType.PLATE).setUnlocalizedName("stamp_stone_plate")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":stamp_stone_plate");
		ModItems.stamp_stone_wire = new ItemStamp(10, StampType.WIRE).setUnlocalizedName("stamp_stone_wire")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":stamp_stone_wire");
		ModItems.stamp_stone_circuit = new ItemStamp(10, StampType.CIRCUIT).setUnlocalizedName("stamp_stone_circuit")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":stamp_stone_circuit");
		ModItems.stamp_iron_flat = new ItemStamp(50, StampType.FLAT).setUnlocalizedName("stamp_iron_flat")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":stamp_iron_flat");
		ModItems.stamp_iron_plate = new ItemStamp(50, StampType.PLATE).setUnlocalizedName("stamp_iron_plate")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":stamp_iron_plate");
		ModItems.stamp_iron_wire = new ItemStamp(50, StampType.WIRE).setUnlocalizedName("stamp_iron_wire")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":stamp_iron_wire");
		ModItems.stamp_iron_circuit = new ItemStamp(50, StampType.CIRCUIT).setUnlocalizedName("stamp_iron_circuit")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":stamp_iron_circuit");
		ModItems.stamp_steel_flat = new ItemStamp(100, StampType.FLAT).setUnlocalizedName("stamp_steel_flat")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":stamp_steel_flat");
		ModItems.stamp_steel_plate = new ItemStamp(100, StampType.PLATE).setUnlocalizedName("stamp_steel_plate")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":stamp_steel_plate");
		ModItems.stamp_steel_wire = new ItemStamp(100, StampType.WIRE).setUnlocalizedName("stamp_steel_wire")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":stamp_steel_wire");
		ModItems.stamp_steel_circuit = new ItemStamp(100, StampType.CIRCUIT).setUnlocalizedName("stamp_steel_circuit")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":stamp_steel_circuit");
		ModItems.stamp_titanium_flat = new ItemStamp(150, StampType.FLAT).setUnlocalizedName("stamp_titanium_flat")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":stamp_titanium_flat");
		ModItems.stamp_titanium_plate = new ItemStamp(150, StampType.PLATE).setUnlocalizedName("stamp_titanium_plate")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":stamp_titanium_plate");
		ModItems.stamp_titanium_wire = new ItemStamp(150, StampType.WIRE).setUnlocalizedName("stamp_titanium_wire")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":stamp_titanium_wire");
		ModItems.stamp_titanium_circuit = new ItemStamp(150, StampType.CIRCUIT)
				.setUnlocalizedName("stamp_titanium_circuit").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":stamp_titanium_circuit");
		ModItems.stamp_obsidian_flat = new ItemStamp(170, StampType.FLAT).setUnlocalizedName("stamp_obsidian_flat")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":stamp_obsidian_flat");
		ModItems.stamp_obsidian_plate = new ItemStamp(170, StampType.PLATE).setUnlocalizedName("stamp_obsidian_plate")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":stamp_obsidian_plate");
		ModItems.stamp_obsidian_wire = new ItemStamp(170, StampType.WIRE).setUnlocalizedName("stamp_obsidian_wire")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":stamp_obsidian_wire");
		ModItems.stamp_obsidian_circuit = new ItemStamp(170, StampType.CIRCUIT)
				.setUnlocalizedName("stamp_obsidian_circuit").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":stamp_obsidian_circuit");
		ModItems.stamp_desh_flat = new ItemStamp(0, StampType.FLAT).setUnlocalizedName("stamp_desh_flat")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":stamp_desh_flat");
		ModItems.stamp_desh_plate = new ItemStamp(0, StampType.PLATE).setUnlocalizedName("stamp_desh_plate")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":stamp_desh_plate");
		ModItems.stamp_desh_wire = new ItemStamp(0, StampType.WIRE).setUnlocalizedName("stamp_desh_wire")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":stamp_desh_wire");
		ModItems.stamp_desh_circuit = new ItemStamp(0, StampType.CIRCUIT).setUnlocalizedName("stamp_desh_circuit")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":stamp_desh_circuit");
		ModItems.stamp_357 = new ItemStamp(1000, StampType.C357).setUnlocalizedName("stamp_357").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_357");
		ModItems.stamp_44 = new ItemStamp(1000, StampType.C44).setUnlocalizedName("stamp_44").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_44");
		ModItems.stamp_9 = new ItemStamp(1000, StampType.C9).setUnlocalizedName("stamp_9").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_9");
		ModItems.stamp_50 = new ItemStamp(1000, StampType.C50).setUnlocalizedName("stamp_50").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_50");

		ModItems.stamp_desh_357 = new ItemStamp(0, StampType.C357).setUnlocalizedName("stamp_desh_357")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":stamp_357_desh");
		ModItems.stamp_desh_44 = new ItemStamp(0, StampType.C44).setUnlocalizedName("stamp_desh_44").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_44_desh");
		ModItems.stamp_desh_9 = new ItemStamp(0, StampType.C9).setUnlocalizedName("stamp_desh_9").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_9_desh");
		ModItems.stamp_desh_50 = new ItemStamp(0, StampType.C50).setUnlocalizedName("stamp_desh_50").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_50_desh");

		ModItems.blades_steel = new ItemBlades(200).setUnlocalizedName("blades_steel").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":blades_steel");
		ModItems.blades_titanium = new ItemBlades(350).setUnlocalizedName("blades_titanium").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":blades_titanium");
		ModItems.blades_advanced_alloy = new ItemBlades(700).setUnlocalizedName("blades_advanced_alloy")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":blades_advanced_alloy");
		ModItems.blades_desh = new ItemBlades(0).setUnlocalizedName("blades_desh").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":blades_desh");

		ModItems.mold_base = new Item().setUnlocalizedName("mold_base").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":mold_base");
		ModItems.mold = new ItemMold().setUnlocalizedName("mold").setCreativeTab(MainRegistry.controlTab);
		ModItems.scraps = new ItemScraps().aot(Mats.MAT_BISMUTH, "scraps_bismuth").setUnlocalizedName("scraps")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":scraps");
		ModItems.plate_cast = new ItemAutogen(MaterialShapes.CASTPLATE).aot(Mats.MAT_BISMUTH, "plate_cast_bismuth")
				.setUnlocalizedName("plate_cast").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":plate_cast");
		ModItems.plate_welded = new ItemAutogen(MaterialShapes.WELDEDPLATE).setUnlocalizedName("plate_welded")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_welded");
		ModItems.heavy_component = new ItemAutogen(MaterialShapes.HEAVY_COMPONENT).setUnlocalizedName("heavy_component")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":heavy_component");

		ModItems.part_lithium = new Item().setUnlocalizedName("part_lithium").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":part_lithium");
		ModItems.part_beryllium = new Item().setUnlocalizedName("part_beryllium")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":part_beryllium");
		ModItems.part_carbon = new Item().setUnlocalizedName("part_carbon").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":part_carbon");
		ModItems.part_copper = new Item().setUnlocalizedName("part_copper").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":part_copper");
		ModItems.part_plutonium = new Item().setUnlocalizedName("part_plutonium")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":part_plutonium");

		ModItems.laser_crystal_co2 = new ItemFELCrystal(EnumWavelengths.IR).setUnlocalizedName("laser_crystal_co2")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":laser_crystal_co2");
		ModItems.laser_crystal_bismuth = new ItemFELCrystal(EnumWavelengths.VISIBLE)
				.setUnlocalizedName("laser_crystal_bismuth").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":laser_crystal_bismuth");
		ModItems.laser_crystal_cmb = new ItemFELCrystal(EnumWavelengths.UV).setUnlocalizedName("laser_crystal_cmb")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":laser_crystal_cmb");
		ModItems.laser_crystal_dnt = new ItemFELCrystal(EnumWavelengths.GAMMA).setUnlocalizedName("laser_crystal_dnt")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":laser_crystal_dnt");
		ModItems.laser_crystal_digamma = new ItemFELCrystal(EnumWavelengths.DRX)
				.setUnlocalizedName("laser_crystal_digamma").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":laser_crystal_digamma");

		ModItems.thermo_element = new Item().setUnlocalizedName("thermo_element")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":thermo_element");
		ModItems.catalytic_converter = new Item().setUnlocalizedName("catalytic_converter").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":catalytic_converter");

		ModItems.antiknock = new Item().setUnlocalizedName("antiknock").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":antiknock");

		ModItems.canister_empty = new ItemCustomLore().setUnlocalizedName("canister_empty")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":canister_empty");
		ModItems.canister_full = new ItemCanister().setUnlocalizedName("canister_full")
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.canister_empty)
				.setTextureName(RefStrings.MODID + ":canister_empty");
		ModItems.canister_napalm = new ItemCustomLore().setUnlocalizedName("canister_napalm")
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.canister_empty)
				.setTextureName(RefStrings.MODID + ":canister_napalm");
		ModItems.gas_empty = new Item().setUnlocalizedName("gas_empty").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":gas_empty");
		ModItems.gas_full = new ItemGasTank().setUnlocalizedName("gas_full").setCreativeTab(MainRegistry.controlTab)
				.setContainerItem(ModItems.gas_empty).setTextureName(RefStrings.MODID + ":gas_empty");

		ModItems.syringe_empty = new Item().setUnlocalizedName("syringe_empty").setFull3D()
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":syringe_empty");
		ModItems.syringe_antidote = new ItemSyringe().setUnlocalizedName("syringe_antidote").setFull3D()
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":syringe_antidote");
		ModItems.syringe_poison = new ItemSyringe().setUnlocalizedName("syringe_poison").setFull3D()
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":syringe_poison");
		ModItems.syringe_awesome = new ItemSyringe().setUnlocalizedName("syringe_awesome").setFull3D()
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":syringe_awesome");
		ModItems.syringe_metal_empty = new Item().setUnlocalizedName("syringe_metal_empty").setFull3D()
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":syringe_metal_empty");
		ModItems.syringe_metal_stimpak = new ItemSyringe().setUnlocalizedName("syringe_metal_stimpak").setFull3D()
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":syringe_metal_stimpak");
		ModItems.syringe_metal_medx = new ItemSyringe().setUnlocalizedName("syringe_metal_medx").setFull3D()
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":syringe_metal_medx");
		ModItems.syringe_metal_psycho = new ItemSyringe().setUnlocalizedName("syringe_metal_psycho").setFull3D()
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":syringe_metal_psycho");
		ModItems.syringe_metal_super = new ItemSyringe().setUnlocalizedName("syringe_metal_super").setFull3D()
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":syringe_metal_super");
		ModItems.syringe_taint = new ItemSyringe().setUnlocalizedName("syringe_taint").setFull3D()
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":syringe_taint");
		ModItems.syringe_mkunicorn = new ItemSyringe().setUnlocalizedName("syringe_mkunicorn").setFull3D()
				.setCreativeTab(null).setTextureName(RefStrings.MODID + ":syringe_mkunicorn");

		ModItems.iv_empty = new ItemSimpleConsumable().setUseActionServer((stack, user) -> {
			if (user.hurtResistantTime <= 0) {
				ItemSimpleConsumable.giveSoundAndDecrement(stack, user, "hbm:item.syringe",
						new ItemStack(ModItems.iv_blood));
				user.attackEntityFrom(DamageSource.magic, 5F);
			}
		}).setUnlocalizedName("iv_empty").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":iv_empty");

		ModItems.iv_blood = new ItemSimpleConsumable().setUseActionServer((stack, user) -> {
			ItemSimpleConsumable.giveSoundAndDecrement(stack, user, "hbm:item.radaway",
					new ItemStack(ModItems.iv_empty));
			user.heal(5F);
		}).setUnlocalizedName("iv_blood").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":iv_blood");

		ModItems.iv_xp_empty = new ItemSimpleConsumable().setUseActionServer((stack, user) -> {
			if (EnchantmentUtil.getTotalExperience(user) >= 100) {
				ItemSimpleConsumable.giveSoundAndDecrement(stack, user, "hbm:item.syringe",
						new ItemStack(ModItems.iv_xp));
				EnchantmentUtil.setExperience(user, EnchantmentUtil.getTotalExperience(user) - 100);
			}
		}).setUnlocalizedName("iv_xp_empty").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":iv_xp_empty");

		ModItems.iv_xp = new ItemSimpleConsumable().setUseActionServer((stack, user) -> {
			ItemSimpleConsumable.giveSoundAndDecrement(stack, user, "random.orb", new ItemStack(ModItems.iv_xp_empty));
			EnchantmentUtil.addExperience(user, 100, false);
		}).setUnlocalizedName("iv_xp").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":iv_xp");

		ModItems.radaway = new ItemSimpleConsumable().setUseActionServer((stack, user) -> {
			ItemSimpleConsumable.giveSoundAndDecrement(stack, user, "hbm:item.radaway",
					new ItemStack(ModItems.iv_empty));
			ItemSimpleConsumable.addPotionEffect(user, HbmPotion.radaway, 140, 0);
		}).setUnlocalizedName("radaway").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":radaway");

		ModItems.radaway_strong = new ItemSimpleConsumable().setUseActionServer((stack, user) -> {
			ItemSimpleConsumable.giveSoundAndDecrement(stack, user, "hbm:item.radaway",
					new ItemStack(ModItems.iv_empty));
			ItemSimpleConsumable.addPotionEffect(user, HbmPotion.radaway, 350, 0);
		}).setUnlocalizedName("radaway_strong").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":radaway_strong");

		ModItems.radaway_flush = new ItemSimpleConsumable().setUseActionServer((stack, user) -> {
			ItemSimpleConsumable.giveSoundAndDecrement(stack, user, "hbm:item.radaway",
					new ItemStack(ModItems.iv_empty));
			ItemSimpleConsumable.addPotionEffect(user, HbmPotion.radaway, 500, 2);
		}).setUnlocalizedName("radaway_flush").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":radaway_flush");

		ModItems.med_bag = new ItemSyringe().setUnlocalizedName("med_bag").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":med_bag");
		ModItems.radx = new ItemPill(0).setUnlocalizedName("radx").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":radx");
		ModItems.siox = new ItemPill(0).setUnlocalizedName("siox").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":siox");
		ModItems.pill_herbal = new ItemPill(0).setUnlocalizedName("pill_herbal")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":pill_herbal");
		ModItems.xanax = new ItemPill(0).setUnlocalizedName("xanax").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":xanax_2");
		ModItems.fmn = new ItemPill(0).setUnlocalizedName("fmn").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":tablet");
		ModItems.five_htp = new ItemPill(0).setUnlocalizedName("five_htp").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":5htp");
		ModItems.pill_iodine = new ItemPill(0).setUnlocalizedName("pill_iodine")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":pill_iodine");
		ModItems.plan_c = new ItemPill(0).setUnlocalizedName("plan_c").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":plan_c");
		ModItems.pill_red = new ItemPill(0).setUnlocalizedName("pill_red").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":pill_red");
		ModItems.stealth_boy = new ItemStarterKit().setUnlocalizedName("stealth_boy")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":stealth_boy");
		ModItems.gas_mask_filter = new ItemFilter().setUnlocalizedName("gas_mask_filter")
				.setTextureName(RefStrings.MODID + ":gas_mask_filter");
		ModItems.gas_mask_filter_mono = new ItemFilter().setUnlocalizedName("gas_mask_filter_mono")
				.setTextureName(RefStrings.MODID + ":gas_mask_filter_mono");
		ModItems.gas_mask_filter_combo = new ItemFilter().setUnlocalizedName("gas_mask_filter_combo")
				.setTextureName(RefStrings.MODID + ":gas_mask_filter_combo");
		ModItems.gas_mask_filter_rag = new ItemFilter().setUnlocalizedName("gas_mask_filter_rag")
				.setTextureName(RefStrings.MODID + ":gas_mask_filter_rag");
		ModItems.gas_mask_filter_piss = new ItemFilter().setUnlocalizedName("gas_mask_filter_piss")
				.setTextureName(RefStrings.MODID + ":gas_mask_filter_piss");
		ModItems.jetpack_tank = new ItemSyringe().setUnlocalizedName("jetpack_tank").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":jetpack_tank");
		ModItems.gun_kit_1 = new ItemSyringe().setUnlocalizedName("gun_kit_1").setMaxStackSize(16)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":gun_kit_1");
		ModItems.gun_kit_2 = new ItemSyringe().setUnlocalizedName("gun_kit_2").setMaxStackSize(16)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":gun_kit_2");
		ModItems.cbt_device = new ItemSyringe().setUnlocalizedName("cbt_device").setMaxStackSize(1).setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":cbt_device");
		ModItems.cigarette = new ItemCigarette().setUnlocalizedName("cigarette").setFull3D().setMaxStackSize(16)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":cigarette");
		ModItems.crackpipe = new ItemCigarette().setUnlocalizedName("crackpipe").setFull3D().setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":crackpipe");
		ModItems.bdcl = new ItemBDCL().setUnlocalizedName("bdcl").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":bdcl");

		ModItems.attachment_mask = new ItemModGasmask().setUnlocalizedName("attachment_mask")
				.setTextureName(RefStrings.MODID + ":attachment_mask");
		ModItems.attachment_mask_mono = new ItemModGasmask().setUnlocalizedName("attachment_mask_mono")
				.setTextureName(RefStrings.MODID + ":attachment_mask_mono");
		ModItems.back_tesla = new ItemModTesla().setUnlocalizedName("back_tesla")
				.setTextureName(RefStrings.MODID + ":back_tesla");
		ModItems.servo_set = new ItemModServos().setUnlocalizedName("servo_set")
				.setTextureName(RefStrings.MODID + ":servo_set");
		ModItems.servo_set_desh = new ItemModServos().setUnlocalizedName("servo_set_desh")
				.setTextureName(RefStrings.MODID + ":servo_set_desh");
		ModItems.pads_rubber = new ItemModPads(0.5F).setUnlocalizedName("pads_rubber")
				.setTextureName(RefStrings.MODID + ":pads_rubber");
		ModItems.pads_slime = new ItemModPads(0.25F).setUnlocalizedName("pads_slime")
				.setTextureName(RefStrings.MODID + ":pads_slime");
		ModItems.pads_static = new ItemModPads(0.75F).setUnlocalizedName("pads_static")
				.setTextureName(RefStrings.MODID + ":pads_static");
		ModItems.cladding_paint = new ItemModCladding(0.025).setUnlocalizedName("cladding_paint")
				.setTextureName(RefStrings.MODID + ":cladding_paint");
		ModItems.cladding_rubber = new ItemModCladding(0.005).setUnlocalizedName("cladding_rubber")
				.setTextureName(RefStrings.MODID + ":cladding_rubber");
		ModItems.cladding_lead = new ItemModCladding(0.1).setUnlocalizedName("cladding_lead")
				.setTextureName(RefStrings.MODID + ":cladding_lead");
		ModItems.cladding_desh = new ItemModCladding(0.2).setUnlocalizedName("cladding_desh")
				.setTextureName(RefStrings.MODID + ":cladding_desh");
		ModItems.cladding_ghiorsium = new ItemModCladding(0.5).setUnlocalizedName("cladding_ghiorsium")
				.setTextureName(RefStrings.MODID + ":cladding_ghiorsium");
		ModItems.cladding_iron = new ItemModIron().setUnlocalizedName("cladding_iron")
				.setTextureName(RefStrings.MODID + ":cladding_iron");
		ModItems.cladding_obsidian = new ItemModObsidian().setUnlocalizedName("cladding_obsidian")
				.setTextureName(RefStrings.MODID + ":cladding_obsidian");
		ModItems.insert_kevlar = new ItemModInsert(1500, 1F, 0.9F, 1F, 1F).setUnlocalizedName("insert_kevlar")
				.setTextureName(RefStrings.MODID + ":insert_kevlar");
		ModItems.insert_sapi = new ItemModInsert(1750, 1F, 0.85F, 1F, 1F).setUnlocalizedName("insert_sapi")
				.setTextureName(RefStrings.MODID + ":insert_sapi");
		ModItems.insert_esapi = new ItemModInsert(2000, 0.95F, 0.8F, 1F, 1F).setUnlocalizedName("insert_esapi")
				.setTextureName(RefStrings.MODID + ":insert_esapi");
		ModItems.insert_xsapi = new ItemModInsert(2500, 0.9F, 0.75F, 1F, 1F).setUnlocalizedName("insert_xsapi")
				.setTextureName(RefStrings.MODID + ":insert_xsapi");
		ModItems.insert_steel = new ItemModInsert(1000, 1F, 0.95F, 0.75F, 0.95F).setUnlocalizedName("insert_steel")
				.setTextureName(RefStrings.MODID + ":insert_steel");
		ModItems.insert_du = new ItemModInsert(1500, 0.9F, 0.85F, 0.5F, 0.9F).setUnlocalizedName("insert_du")
				.setTextureName(RefStrings.MODID + ":insert_du");
		ModItems.insert_polonium = new ItemModInsert(500, 0.9F, 1F, 0.95F, 0.9F).setUnlocalizedName("insert_polonium")
				.setTextureName(RefStrings.MODID + ":insert_polonium");
		ModItems.insert_ghiorsium = new ItemModInsert(2000, 0.8F, 0.75F, 0.35F, 0.9F)
				.setUnlocalizedName("insert_ghiorsium").setTextureName(RefStrings.MODID + ":insert_ghiorsium");
		ModItems.insert_era = new ItemModInsert(25, 0.5F, 1F, 0.25F, 1F).setUnlocalizedName("insert_era")
				.setTextureName(RefStrings.MODID + ":insert_era");
		ModItems.insert_yharonite = new ItemModInsert(9999, 0.01F, 1F, 1F, 1F).setUnlocalizedName("insert_yharonite")
				.setTextureName(RefStrings.MODID + ":insert_yharonite");
		ModItems.insert_doxium = new ItemModInsert(9999, 5.0F, 1F, 1F, 1F).setUnlocalizedName("insert_doxium")
				.setTextureName(RefStrings.MODID + ":insert_doxium");
		ModItems.armor_polish = new ItemModPolish().setUnlocalizedName("armor_polish")
				.setTextureName(RefStrings.MODID + ":armor_polish");
		ModItems.bandaid = new ItemModBandaid().setUnlocalizedName("bandaid")
				.setTextureName(RefStrings.MODID + ":bandaid");
		ModItems.serum = new ItemModSerum().setUnlocalizedName("serum").setTextureName(RefStrings.MODID + ":serum");
		ModItems.quartz_plutonium = new ItemModQuartz().setUnlocalizedName("quartz_plutonium")
				.setTextureName(RefStrings.MODID + ":quartz_plutonium");
		ModItems.morning_glory = new ItemModMorningGlory().setUnlocalizedName("morning_glory")
				.setTextureName(RefStrings.MODID + ":morning_glory");
		ModItems.lodestone = new ItemModLodestone(5).setUnlocalizedName("lodestone")
				.setTextureName(RefStrings.MODID + ":lodestone");
		ModItems.horseshoe_magnet = new ItemModLodestone(8).setUnlocalizedName("horseshoe_magnet")
				.setTextureName(RefStrings.MODID + ":horseshoe_magnet");
		ModItems.industrial_magnet = new ItemModLodestone(12).setUnlocalizedName("industrial_magnet")
				.setTextureName(RefStrings.MODID + ":industrial_magnet");
		ModItems.bathwater = new ItemModBathwater().setUnlocalizedName("bathwater")
				.setTextureName(RefStrings.MODID + ":bathwater");
		ModItems.bathwater_mk2 = new ItemModBathwater().setUnlocalizedName("bathwater_mk2")
				.setTextureName(RefStrings.MODID + ":bathwater_mk2");
		ModItems.spider_milk = new ItemModMilk().setUnlocalizedName("spider_milk")
				.setTextureName(RefStrings.MODID + ":spider_milk");
		ModItems.ink = new ItemModInk().setUnlocalizedName("ink").setTextureName(RefStrings.MODID + ":ink");
		ModItems.heart_piece = new ItemModHealth(5F).setUnlocalizedName("heart_piece")
				.setTextureName(RefStrings.MODID + ":heart_piece");
		ModItems.heart_container = new ItemModHealth(20F).setUnlocalizedName("heart_container")
				.setTextureName(RefStrings.MODID + ":heart_container");
		ModItems.heart_booster = new ItemModHealth(40F).setUnlocalizedName("heart_booster")
				.setTextureName(RefStrings.MODID + ":heart_booster");
		ModItems.heart_fab = new ItemModHealth(60F).setUnlocalizedName("heart_fab")
				.setTextureName(RefStrings.MODID + ":heart_fab");
		ModItems.black_diamond = new ItemModHealth(40F).setUnlocalizedName("black_diamond")
				.setTextureName(RefStrings.MODID + ":black_diamond");
		ModItems.wd40 = new ItemModWD40().setUnlocalizedName("wd40").setTextureName(RefStrings.MODID + ":wd40");
		ModItems.scrumpy = new ItemModRevive(1).setUnlocalizedName("scrumpy")
				.setTextureName(RefStrings.MODID + ":scrumpy");
		ModItems.wild_p = new ItemModRevive(3).setUnlocalizedName("wild_p")
				.setTextureName(RefStrings.MODID + ":wild_p");
		ModItems.fabsols_vodka = new ItemModRevive(9999).setUnlocalizedName("fabsols_vodka")
				.setTextureName(RefStrings.MODID + ":fabsols_vodka");
		ModItems.shackles = new ItemModShackles().setUnlocalizedName("shackles")
				.setTextureName(RefStrings.MODID + ":shackles");
		ModItems.injector_5htp = new ItemModAuto().setUnlocalizedName("injector_5htp")
				.setTextureName(RefStrings.MODID + ":injector_5htp");
		ModItems.injector_knife = new ItemModKnife().setUnlocalizedName("injector_knife")
				.setTextureName(RefStrings.MODID + ":injector_knife");
		ModItems.medal_liquidator = new ItemModMedal().setUnlocalizedName("medal_liquidator")
				.setTextureName(RefStrings.MODID + ":medal_liquidator");
		ModItems.v1 = new ItemModV1().setUnlocalizedName("v1").setTextureName(RefStrings.MODID + ":v1");
		ModItems.protection_charm = new ItemModCharm().setUnlocalizedName("protection_charm")
				.setTextureName(RefStrings.MODID + ":protection_charm");
		ModItems.meteor_charm = new ItemModCharm().setUnlocalizedName("meteor_charm")
				.setTextureName(RefStrings.MODID + ":meteor_charm");
		ModItems.neutrino_lens = new ItemModLens().setUnlocalizedName("neutrino_lens")
				.setTextureName(RefStrings.MODID + ":neutrino_lens");
		ModItems.gas_tester = new ItemModSensor().setUnlocalizedName("gas_tester")
				.setTextureName(RefStrings.MODID + ":gas_tester");
		ModItems.defuser_gold = new ItemModDefuser().setUnlocalizedName("defuser_gold")
				.setTextureName(RefStrings.MODID + ":defuser_gold");
		ModItems.ballistic_gauntlet = new ItemModTwoKick().setUnlocalizedName("ballistic_gauntlet")
				.setTextureName(RefStrings.MODID + ":ballistic_gauntlet");
		ModItems.night_vision = new ItemModNightVision().setUnlocalizedName("night_vision")
				.setTextureName(RefStrings.MODID + ":night_vision");

		ModItems.cap_nuka = new Item().setUnlocalizedName("cap_nuka").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":cap_nuka");
		ModItems.cap_quantum = new Item().setUnlocalizedName("cap_quantum").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":cap_quantum");
		ModItems.cap_sparkle = new Item().setUnlocalizedName("cap_sparkle").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":cap_sparkle");
		ModItems.cap_rad = new Item().setUnlocalizedName("cap_rad").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":cap_rad");
		ModItems.cap_korl = new Item().setUnlocalizedName("cap_korl").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":cap_korl");
		ModItems.cap_fritz = new Item().setUnlocalizedName("cap_fritz").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":cap_fritz");
		ModItems.cap_sunset = new Item().setUnlocalizedName("cap_sunset").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":cap_sunset");
		ModItems.cap_star = new Item().setUnlocalizedName("cap_star").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":cap_star");
		ModItems.ring_pull = new Item().setUnlocalizedName("ring_pull").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":ring_pull");

		ModItems.can_empty = new Item().setUnlocalizedName("can_empty").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":can_empty");
		ModItems.can_smart = new ItemEnergy().makeCan().setUnlocalizedName("can_smart")
				.setTextureName(RefStrings.MODID + ":can_smart");
		ModItems.can_creature = new ItemEnergy().makeCan().setUnlocalizedName("can_creature")
				.setTextureName(RefStrings.MODID + ":can_creature");
		ModItems.can_redbomb = new ItemEnergy().makeCan().setUnlocalizedName("can_redbomb")
				.setTextureName(RefStrings.MODID + ":can_redbomb");
		ModItems.can_mrsugar = new ItemEnergy().makeCan().setUnlocalizedName("can_mrsugar")
				.setTextureName(RefStrings.MODID + ":can_mrsugar");
		ModItems.can_overcharge = new ItemEnergy().makeCan().setUnlocalizedName("can_overcharge")
				.setTextureName(RefStrings.MODID + ":can_overcharge");
		ModItems.can_luna = new ItemEnergy().makeCan().setUnlocalizedName("can_luna")
				.setTextureName(RefStrings.MODID + ":can_luna");
		ModItems.can_bepis = new ItemEnergy().makeCan().setUnlocalizedName("can_bepis")
				.setTextureName(RefStrings.MODID + ":can_bepis");
		ModItems.can_breen = new ItemEnergy().makeCan().setUnlocalizedName("can_breen")
				.setTextureName(RefStrings.MODID + ":can_breen");
		ModItems.can_mug = new ItemEnergy().makeCan().setUnlocalizedName("can_mug")
				.setTextureName(RefStrings.MODID + ":can_mug");
		ModItems.bottle_empty = new Item().setUnlocalizedName("bottle_empty")
				.setTextureName(RefStrings.MODID + ":bottle_empty");
		ModItems.bottle_nuka = new ItemEnergy().makeBottle(ModItems.bottle_empty, ModItems.cap_nuka)
				.setUnlocalizedName("bottle_nuka").setTextureName(RefStrings.MODID + ":bottle_nuka");
		ModItems.bottle_cherry = new ItemEnergy().makeBottle(ModItems.bottle_empty, ModItems.cap_nuka)
				.setUnlocalizedName("bottle_cherry").setContainerItem(ModItems.bottle_empty)
				.setTextureName(RefStrings.MODID + ":bottle_cherry");
		ModItems.bottle_quantum = new ItemEnergy().makeBottle(ModItems.bottle_empty, ModItems.cap_quantum)
				.setUnlocalizedName("bottle_quantum").setContainerItem(ModItems.bottle_empty)
				.setTextureName(RefStrings.MODID + ":bottle_quantum");
		ModItems.bottle_sparkle = new ItemEnergy().makeBottle(ModItems.bottle_empty, ModItems.cap_sparkle)
				.setUnlocalizedName("bottle_sparkle").setContainerItem(ModItems.bottle_empty)
				.setTextureName(RefStrings.MODID + ":bottle_sparkle");
		ModItems.bottle_rad = new ItemEnergy().makeBottle(ModItems.bottle_empty, ModItems.cap_rad)
				.setUnlocalizedName("bottle_rad").setContainerItem(ModItems.bottle_empty)
				.setTextureName(RefStrings.MODID + ":bottle_rad");
		ModItems.bottle2_empty = new Item().setUnlocalizedName("bottle2_empty")
				.setTextureName(RefStrings.MODID + ":bottle2_empty");
		ModItems.bottle2_korl = new ItemEnergy().makeBottle(ModItems.bottle2_empty, ModItems.cap_korl)
				.setUnlocalizedName("bottle2_korl").setContainerItem(ModItems.bottle2_empty)
				.setTextureName(RefStrings.MODID + ":bottle2_korl");
		ModItems.bottle2_fritz = new ItemEnergy().makeBottle(ModItems.bottle2_empty, ModItems.cap_fritz)
				.setUnlocalizedName("bottle2_fritz").setContainerItem(ModItems.bottle2_empty)
				.setTextureName(RefStrings.MODID + ":bottle2_fritz");
		ModItems.bottle2_korl_special = new ItemEnergy().makeBottle(ModItems.bottle2_empty, ModItems.cap_korl)
				.setUnlocalizedName("bottle2_korl_special").setContainerItem(ModItems.bottle2_empty)
				.setTextureName(RefStrings.MODID + ":bottle2_korl");
		ModItems.bottle2_fritz_special = new ItemEnergy().makeBottle(ModItems.bottle2_empty, ModItems.cap_fritz)
				.setUnlocalizedName("bottle2_fritz_special").setContainerItem(ModItems.bottle2_empty)
				.setTextureName(RefStrings.MODID + ":bottle2_fritz");
		ModItems.bottle2_sunset = new ItemEnergy().makeBottle(ModItems.bottle2_empty, ModItems.cap_sunset)
				.setUnlocalizedName("bottle2_sunset").setContainerItem(ModItems.bottle2_empty)
				.setTextureName(RefStrings.MODID + ":bottle2_sunset");
		ModItems.flask_infusion = new ItemFlask().setUnlocalizedName("flask_infusion")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":flask");
		ModItems.chocolate_milk = new ItemEnergy().setUnlocalizedName("chocolate_milk")
				.setTextureName(RefStrings.MODID + ":chocolate_milk");
		ModItems.coffee = new ItemEnergy().setUnlocalizedName("coffee").setTextureName(RefStrings.MODID + ":coffee");
		ModItems.coffee_radium = new ItemEnergy().setUnlocalizedName("coffee_radium")
				.setTextureName(RefStrings.MODID + ":coffee_radium");
		ModItems.chocolate = new ItemPill(0).setUnlocalizedName("chocolate").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":chocolate");
		ModItems.canned_conserve = (ItemEnumMulti) new ItemConserve().setUnlocalizedName("canned_conserve")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canned");
		ModItems.can_key = new Item().setUnlocalizedName("can_key").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":can_key");

		ModItems.cart = new ItemModMinecart().setUnlocalizedName("cart");
		ModItems.train = new ItemTrain().setUnlocalizedName("train");
		ModItems.drone = new ItemDrone().setUnlocalizedName("drone");

		ModItems.coin_creeper = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("coin_creeper")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":coin_creeper");
		ModItems.coin_radiation = new ItemCustomLore().setRarity(EnumRarity.uncommon)
				.setUnlocalizedName("coin_radiation").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":coin_radiation");
		ModItems.coin_maskman = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("coin_maskman")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":coin_maskman");
		ModItems.coin_worm = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("coin_worm")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":coin_worm");
		ModItems.coin_ufo = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("coin_ufo")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":coin_ufo");

		ModItems.rod_empty = new Item().setUnlocalizedName("rod_empty").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":rod_empty");
		ModItems.rod = new ItemBreedingRod().setUnlocalizedName("rod").setContainerItem(ModItems.rod_empty)
				.setCreativeTab(MainRegistry.controlTab);
		ModItems.rod_dual_empty = new Item().setUnlocalizedName("rod_dual_empty")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":rod_dual_empty");
		ModItems.rod_dual = new ItemBreedingRod().setUnlocalizedName("rod_dual")
				.setContainerItem(ModItems.rod_dual_empty).setCreativeTab(MainRegistry.controlTab);
		ModItems.rod_quad_empty = new Item().setUnlocalizedName("rod_quad_empty")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":rod_quad_empty");
		ModItems.rod_quad = new ItemBreedingRod().setUnlocalizedName("rod_quad")
				.setContainerItem(ModItems.rod_quad_empty).setCreativeTab(MainRegistry.controlTab);

		ModItems.rod_zirnox_empty = new Item().setUnlocalizedName("rod_zirnox_empty").setMaxStackSize(64)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":rod_zirnox_empty");
		// rod_zirnox_natural_uranium_fuel = new ItemZirnoxRodDeprecated(250000,
		// 30).setUnlocalizedName("rod_zirnox_natural_uranium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_zirnox_empty).setTextureName(RefStrings.MODID
		// + ":rod_zirnox_natural_uranium_fuel");
		// rod_zirnox_uranium_fuel = new ItemZirnoxRodDeprecated(200000,
		// 50).setUnlocalizedName("rod_zirnox_uranium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_zirnox_empty).setTextureName(RefStrings.MODID
		// + ":rod_zirnox_uranium_fuel");
		// rod_zirnox_th232 = new ItemZirnoxBreedingRod(20000,
		// 0).setUnlocalizedName("rod_zirnox_th232").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID
		// + ":rod_zirnox_th232");
		// rod_zirnox_thorium_fuel = new ItemZirnoxRodDeprecated(200000,
		// 40).setUnlocalizedName("rod_zirnox_thorium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID
		// + ":rod_zirnox_thorium_fuel");
		// rod_zirnox_mox_fuel = new ItemZirnoxRodDeprecated(165000,
		// 75).setUnlocalizedName("rod_zirnox_mox_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID
		// + ":rod_zirnox_mox_fuel");
		// rod_zirnox_plutonium_fuel = new ItemZirnoxRodDeprecated(175000,
		// 65).setUnlocalizedName("rod_zirnox_plutonium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID
		// + ":rod_zirnox_plutonium_fuel");
		// rod_zirnox_u233_fuel = new ItemZirnoxRodDeprecated(150000,
		// 100).setUnlocalizedName("rod_zirnox_u233_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID
		// + ":rod_zirnox_u233_fuel");
		// rod_zirnox_u235_fuel = new ItemZirnoxRodDeprecated(165000,
		// 85).setUnlocalizedName("rod_zirnox_u235_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID
		// + ":rod_zirnox_u235_fuel");
		// rod_zirnox_les_fuel = new ItemZirnoxRodDeprecated(150000,
		// 150).setUnlocalizedName("rod_zirnox_les_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID
		// + ":rod_zirnox_les_fuel");
		// rod_zirnox_lithium = new ItemZirnoxBreedingRod(20000,
		// 0).setUnlocalizedName("rod_zirnox_lithium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID
		// + ":rod_zirnox_lithium");
		ModItems.rod_zirnox_tritium = new Item().setUnlocalizedName("rod_zirnox_tritium").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_zirnox_empty)
				.setTextureName(RefStrings.MODID + ":rod_zirnox_tritium");
		// rod_zirnox_zfb_mox = new ItemZirnoxRodDeprecated(50000,
		// 35).setUnlocalizedName("rod_zirnox_zfb_mox").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID
		// + ":rod_zirnox_zfb_mox");
		ModItems.rod_zirnox = (ItemEnumMulti) new ItemZirnoxRod().setUnlocalizedName("rod_zirnox")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":rod_zirnox");

		ModItems.rod_zirnox_natural_uranium_fuel_depleted = new Item()
				.setUnlocalizedName("rod_zirnox_natural_uranium_fuel_depleted").setCreativeTab(MainRegistry.controlTab)
				.setContainerItem(ModItems.rod_zirnox_empty)
				.setTextureName(RefStrings.MODID + ":rod_zirnox_uranium_fuel_depleted");
		ModItems.rod_zirnox_uranium_fuel_depleted = new Item().setUnlocalizedName("rod_zirnox_uranium_fuel_depleted")
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_zirnox_empty)
				.setTextureName(RefStrings.MODID + ":rod_zirnox_uranium_fuel_depleted");
		ModItems.rod_zirnox_thorium_fuel_depleted = new Item().setUnlocalizedName("rod_zirnox_thorium_fuel_depleted")
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_zirnox_empty)
				.setTextureName(RefStrings.MODID + ":rod_zirnox_thorium_fuel_depleted");
		ModItems.rod_zirnox_mox_fuel_depleted = new Item().setUnlocalizedName("rod_zirnox_mox_fuel_depleted")
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_zirnox_empty)
				.setTextureName(RefStrings.MODID + ":rod_zirnox_mox_fuel_depleted");
		ModItems.rod_zirnox_plutonium_fuel_depleted = new Item()
				.setUnlocalizedName("rod_zirnox_plutonium_fuel_depleted").setCreativeTab(MainRegistry.controlTab)
				.setContainerItem(ModItems.rod_zirnox_empty)
				.setTextureName(RefStrings.MODID + ":rod_zirnox_plutonium_fuel_depleted");
		ModItems.rod_zirnox_u233_fuel_depleted = new Item().setUnlocalizedName("rod_zirnox_u233_fuel_depleted")
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_zirnox_empty)
				.setTextureName(RefStrings.MODID + ":rod_zirnox_u233_fuel_depleted");
		ModItems.rod_zirnox_u235_fuel_depleted = new Item().setUnlocalizedName("rod_zirnox_u235_fuel_depleted")
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_zirnox_empty)
				.setTextureName(RefStrings.MODID + ":rod_zirnox_u235_fuel_depleted");
		ModItems.rod_zirnox_les_fuel_depleted = new Item().setUnlocalizedName("rod_zirnox_les_fuel_depleted")
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_zirnox_empty)
				.setTextureName(RefStrings.MODID + ":rod_zirnox_les_fuel_depleted");
		ModItems.rod_zirnox_zfb_mox_depleted = new Item().setUnlocalizedName("rod_zirnox_zfb_mox_depleted")
				.setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_zirnox_empty)
				.setTextureName(RefStrings.MODID + ":rod_zirnox_zfb_mox_depleted");

		ModItems.waste_natural_uranium = new ItemDepletedFuel().setUnlocalizedName("waste_natural_uranium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_uranium");
		ModItems.waste_uranium = new ItemDepletedFuel().setUnlocalizedName("waste_uranium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_uranium");
		ModItems.waste_thorium = new ItemDepletedFuel().setUnlocalizedName("waste_thorium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_thorium");
		ModItems.waste_mox = new ItemDepletedFuel().setUnlocalizedName("waste_mox")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_mox");
		ModItems.waste_plutonium = new ItemDepletedFuel().setUnlocalizedName("waste_plutonium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_plutonium");
		ModItems.waste_u233 = new ItemDepletedFuel().setUnlocalizedName("waste_u233")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_uranium");
		ModItems.waste_u235 = new ItemDepletedFuel().setUnlocalizedName("waste_u235")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_uranium");
		ModItems.waste_schrabidium = new ItemDepletedFuel().setUnlocalizedName("waste_schrabidium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_schrabidium");
		ModItems.waste_zfb_mox = new ItemDepletedFuel().setUnlocalizedName("waste_zfb_mox")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_zfb_mox");

		ModItems.waste_plate_u233 = new ItemDepletedFuel().setUnlocalizedName("waste_plate_u233")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_plate_uranium");
		ModItems.waste_plate_u235 = new ItemDepletedFuel().setUnlocalizedName("waste_plate_u235")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_plate_uranium");
		ModItems.waste_plate_mox = new ItemDepletedFuel().setUnlocalizedName("waste_plate_mox")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_plate_mox");
		ModItems.waste_plate_pu239 = new ItemDepletedFuel().setUnlocalizedName("waste_plate_pu239")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_plate_mox");
		ModItems.waste_plate_sa326 = new ItemDepletedFuel().setUnlocalizedName("waste_plate_sa326")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_plate_sa326");
		ModItems.waste_plate_ra226be = new ItemDepletedFuel().setUnlocalizedName("waste_plate_ra226be")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_plate_ra226be");
		ModItems.waste_plate_pu238be = new ItemDepletedFuel().setUnlocalizedName("waste_plate_pu238be")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_plate_pu238be");

		ModItems.pile_rod_uranium = new ItemPileRod().setUnlocalizedName("pile_rod_uranium")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pile_rod_uranium");
		ModItems.pile_rod_pu239 = new ItemPileRod().setUnlocalizedName("pile_rod_pu239")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pile_rod_pu239");
		ModItems.pile_rod_plutonium = new ItemPileRod().setUnlocalizedName("pile_rod_plutonium")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pile_rod_plutonium");
		ModItems.pile_rod_source = new ItemPileRod().setUnlocalizedName("pile_rod_source")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pile_rod_source");
		ModItems.pile_rod_boron = new ItemPileRod().setUnlocalizedName("pile_rod_boron")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pile_rod_boron");
		ModItems.pile_rod_lithium = new ItemPileRod().setUnlocalizedName("pile_rod_lithium")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pile_rod_lithium");
		ModItems.pile_rod_detector = new ItemPileRod().setUnlocalizedName("pile_rod_detector")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pile_rod_detector");

		ModItems.plate_fuel_u233 = new ItemPlateFuel(2200000).setFunction(FunctionEnum.SQUARE_ROOT, 50)
				.setUnlocalizedName("plate_fuel_u233").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":plate_fuel_u233");
		ModItems.plate_fuel_u235 = new ItemPlateFuel(2200000).setFunction(FunctionEnum.SQUARE_ROOT, 40)
				.setUnlocalizedName("plate_fuel_u235").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":plate_fuel_u235");
		ModItems.plate_fuel_mox = new ItemPlateFuel(2400000).setFunction(FunctionEnum.LOGARITHM, 50)
				.setUnlocalizedName("plate_fuel_mox").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":plate_fuel_mox");
		ModItems.plate_fuel_pu239 = new ItemPlateFuel(2000000).setFunction(FunctionEnum.NEGATIVE_QUADRATIC, 50)
				.setUnlocalizedName("plate_fuel_pu239").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":plate_fuel_pu239");
		ModItems.plate_fuel_sa326 = new ItemPlateFuel(2000000).setFunction(FunctionEnum.LINEAR, 80)
				.setUnlocalizedName("plate_fuel_sa326").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":plate_fuel_sa326");
		ModItems.plate_fuel_ra226be = new ItemPlateFuel(1300000).setFunction(FunctionEnum.PASSIVE, 30)
				.setUnlocalizedName("plate_fuel_ra226be").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":plate_fuel_ra226be");
		ModItems.plate_fuel_pu238be = new ItemPlateFuel(1000000).setFunction(FunctionEnum.PASSIVE, 50)
				.setUnlocalizedName("plate_fuel_pu238be").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":plate_fuel_pu238be");

		ModItems.pwr_fuel = new ItemPWRFuel().setUnlocalizedName("pwr_fuel").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":pwr_fuel");
		ModItems.pwr_fuel_hot = new ItemEnumMulti(EnumPWRFuel.class, true, false).setUnlocalizedName("pwr_fuel_hot")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pwr_fuel_hot");
		ModItems.pwr_fuel_depleted = new ItemEnumMulti(EnumPWRFuel.class, true, false)
				.setUnlocalizedName("pwr_fuel_depleted").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":pwr_fuel_depleted");

		ModItems.rbmk_lid = new ItemRBMKLid().setUnlocalizedName("rbmk_lid").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":rbmk_lid");
		ModItems.rbmk_lid_glass = new ItemRBMKLid().setUnlocalizedName("rbmk_lid_glass")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":rbmk_lid_glass");

		ModItems.rbmk_pellet_ueu = (ItemRBMKPellet) new ItemRBMKPellet("Unenriched Uranium")
				.setUnlocalizedName("rbmk_pellet_ueu").setTextureName(RefStrings.MODID + ":rbmk_pellet_ueu");
		ModItems.rbmk_pellet_meu = (ItemRBMKPellet) new ItemRBMKPellet("Medium Enriched Uranium-235")
				.setUnlocalizedName("rbmk_pellet_meu").setTextureName(RefStrings.MODID + ":rbmk_pellet_meu");
		ModItems.rbmk_pellet_heu233 = (ItemRBMKPellet) new ItemRBMKPellet("Highly Enriched Uranium-233")
				.setUnlocalizedName("rbmk_pellet_heu233").setTextureName(RefStrings.MODID + ":rbmk_pellet_heu233");
		ModItems.rbmk_pellet_heu235 = (ItemRBMKPellet) new ItemRBMKPellet("Highly Enriched Uranium-235")
				.setUnlocalizedName("rbmk_pellet_heu235").setTextureName(RefStrings.MODID + ":rbmk_pellet_heu235");
		ModItems.rbmk_pellet_thmeu = (ItemRBMKPellet) new ItemRBMKPellet("Thorium with MEU Driver Fuel")
				.setUnlocalizedName("rbmk_pellet_thmeu").setTextureName(RefStrings.MODID + ":rbmk_pellet_thmeu");
		ModItems.rbmk_pellet_lep = (ItemRBMKPellet) new ItemRBMKPellet("Low Enriched Plutonium-239")
				.setUnlocalizedName("rbmk_pellet_lep").setTextureName(RefStrings.MODID + ":rbmk_pellet_lep");
		ModItems.rbmk_pellet_mep = (ItemRBMKPellet) new ItemRBMKPellet("Medium Enriched Plutonium-239")
				.setUnlocalizedName("rbmk_pellet_mep").setTextureName(RefStrings.MODID + ":rbmk_pellet_mep");
		ModItems.rbmk_pellet_hep239 = (ItemRBMKPellet) new ItemRBMKPellet("Highly Enriched Plutonium-239")
				.setUnlocalizedName("rbmk_pellet_hep239").setTextureName(RefStrings.MODID + ":rbmk_pellet_hep239");
		ModItems.rbmk_pellet_hep241 = (ItemRBMKPellet) new ItemRBMKPellet("Highly Enriched Plutonium-241")
				.setUnlocalizedName("rbmk_pellet_hep241").setTextureName(RefStrings.MODID + ":rbmk_pellet_hep241");
		ModItems.rbmk_pellet_lea = (ItemRBMKPellet) new ItemRBMKPellet("Low Enriched Americium-242")
				.setUnlocalizedName("rbmk_pellet_lea").setTextureName(RefStrings.MODID + ":rbmk_pellet_lea");
		ModItems.rbmk_pellet_mea = (ItemRBMKPellet) new ItemRBMKPellet("Medium Enriched Americium-242")
				.setUnlocalizedName("rbmk_pellet_mea").setTextureName(RefStrings.MODID + ":rbmk_pellet_mea");
		ModItems.rbmk_pellet_hea241 = (ItemRBMKPellet) new ItemRBMKPellet("Highly Enriched Americium-241")
				.setUnlocalizedName("rbmk_pellet_hea241").setTextureName(RefStrings.MODID + ":rbmk_pellet_hea241");
		ModItems.rbmk_pellet_hea242 = (ItemRBMKPellet) new ItemRBMKPellet("Highly Enriched Americium-242")
				.setUnlocalizedName("rbmk_pellet_hea242").setTextureName(RefStrings.MODID + ":rbmk_pellet_hea242");
		ModItems.rbmk_pellet_men = (ItemRBMKPellet) new ItemRBMKPellet("Medium Enriched Neptunium-237")
				.setUnlocalizedName("rbmk_pellet_men").setTextureName(RefStrings.MODID + ":rbmk_pellet_men");
		ModItems.rbmk_pellet_hen = (ItemRBMKPellet) new ItemRBMKPellet("Highly Enriched Neptunium-237")
				.setUnlocalizedName("rbmk_pellet_hen").setTextureName(RefStrings.MODID + ":rbmk_pellet_hen");
		ModItems.rbmk_pellet_mox = (ItemRBMKPellet) new ItemRBMKPellet("Mixed MEU & LEP Oxide")
				.setUnlocalizedName("rbmk_pellet_mox").setTextureName(RefStrings.MODID + ":rbmk_pellet_mox");
		ModItems.rbmk_pellet_les = (ItemRBMKPellet) new ItemRBMKPellet("Low Enriched Schrabidium-326")
				.setUnlocalizedName("rbmk_pellet_les").setTextureName(RefStrings.MODID + ":rbmk_pellet_les");
		ModItems.rbmk_pellet_mes = (ItemRBMKPellet) new ItemRBMKPellet("Medium Enriched Schrabidium-326")
				.setUnlocalizedName("rbmk_pellet_mes").setTextureName(RefStrings.MODID + ":rbmk_pellet_mes");
		ModItems.rbmk_pellet_hes = (ItemRBMKPellet) new ItemRBMKPellet("Highly Enriched Schrabidium-326")
				.setUnlocalizedName("rbmk_pellet_hes").setTextureName(RefStrings.MODID + ":rbmk_pellet_hes");
		ModItems.rbmk_pellet_leaus = (ItemRBMKPellet) new ItemRBMKPellet("Low Enriched Australium (Tasmanite)")
				.setUnlocalizedName("rbmk_pellet_leaus").setTextureName(RefStrings.MODID + ":rbmk_pellet_leaus");
		ModItems.rbmk_pellet_heaus = (ItemRBMKPellet) new ItemRBMKPellet("Highly Enriched Australium (Ayerite)")
				.setUnlocalizedName("rbmk_pellet_heaus").setTextureName(RefStrings.MODID + ":rbmk_pellet_heaus");
		ModItems.rbmk_pellet_po210be = (ItemRBMKPellet) new ItemRBMKPellet("Polonium-210 & Beryllium Neutron Source")
				.disableXenon().setUnlocalizedName("rbmk_pellet_po210be")
				.setTextureName(RefStrings.MODID + ":rbmk_pellet_po210be");
		ModItems.rbmk_pellet_ra226be = (ItemRBMKPellet) new ItemRBMKPellet("Radium-226 & Beryllium Neutron Source")
				.disableXenon().setUnlocalizedName("rbmk_pellet_ra226be")
				.setTextureName(RefStrings.MODID + ":rbmk_pellet_ra226be");
		ModItems.rbmk_pellet_pu238be = (ItemRBMKPellet) new ItemRBMKPellet("Plutonium-238 & Beryllium Neutron Source")
				.setUnlocalizedName("rbmk_pellet_pu238be").setTextureName(RefStrings.MODID + ":rbmk_pellet_pu238be");
		ModItems.rbmk_pellet_balefire_gold = (ItemRBMKPellet) new ItemRBMKPellet(
				"Antihydrogen in a Magnetized Gold-198 Lattice").disableXenon()
				.setUnlocalizedName("rbmk_pellet_balefire_gold")
				.setTextureName(RefStrings.MODID + ":rbmk_pellet_balefire_gold");
		ModItems.rbmk_pellet_flashlead = (ItemRBMKPellet) new ItemRBMKPellet(
				"Antihydrogen confined by a Magnetized Gold-198 and Lead-209 Lattice").disableXenon()
				.setUnlocalizedName("rbmk_pellet_flashlead")
				.setTextureName(RefStrings.MODID + ":rbmk_pellet_flashlead");
		ModItems.rbmk_pellet_balefire = (ItemRBMKPellet) new ItemRBMKPellet("Draconic Flames").disableXenon()
				.setUnlocalizedName("rbmk_pellet_balefire").setTextureName(RefStrings.MODID + ":rbmk_pellet_balefire");
		ModItems.rbmk_pellet_zfb_bismuth = (ItemRBMKPellet) new ItemRBMKPellet(
				"Zirconium Fast Breeder - LEU/HEP-241#Bi").setUnlocalizedName("rbmk_pellet_zfb_bismuth")
				.setTextureName(RefStrings.MODID + ":rbmk_pellet_zfb_bismuth");
		ModItems.rbmk_pellet_zfb_pu241 = (ItemRBMKPellet) new ItemRBMKPellet(
				"Zirconium Fast Breeder - HEU-235/HEP-240#Pu-241").setUnlocalizedName("rbmk_pellet_zfb_pu241")
				.setTextureName(RefStrings.MODID + ":rbmk_pellet_zfb_pu241");
		ModItems.rbmk_pellet_zfb_am_mix = (ItemRBMKPellet) new ItemRBMKPellet("Zirconium Fast Breeder - HEP-241#MEA")
				.setUnlocalizedName("rbmk_pellet_zfb_am_mix")
				.setTextureName(RefStrings.MODID + ":rbmk_pellet_zfb_am_mix");
		ModItems.rbmk_pellet_drx = (ItemRBMKPellet) new ItemRBMKPellet(
				EnumChatFormatting.OBFUSCATED + "can't you hear, can't you hear the thunder?")
				.setUnlocalizedName("rbmk_pellet_drx").setTextureName(RefStrings.MODID + ":rbmk_pellet_drx");

		ModItems.rbmk_fuel_empty = new Item().setUnlocalizedName("rbmk_fuel_empty")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":rbmk_fuel_empty");
		ModItems.rbmk_fuel_ueu = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_ueu).setYield(100000000D)
				.setStats(15).setFunction(EnumBurnFunc.LOG_TEN).setDepletionFunction(EnumDepleteFunc.RAISING_SLOPE)
				.setHeat(0.65) // 0.5 is too much of a nerf in heat; pu239 buildup justifies it being on par
								// with MEU ig
				.setMeltingPoint(2865).setUnlocalizedName("rbmk_fuel_ueu")
				.setTextureName(RefStrings.MODID + ":rbmk_fuel_ueu");
		ModItems.rbmk_fuel_meu = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_meu).setYield(100000000D)
				.setStats(20).setFunction(EnumBurnFunc.LOG_TEN).setDepletionFunction(EnumDepleteFunc.RAISING_SLOPE)
				.setHeat(0.65) // 0.75 was a bit too much...
				.setMeltingPoint(2865).setUnlocalizedName("rbmk_fuel_meu")
				.setTextureName(RefStrings.MODID + ":rbmk_fuel_meu");
		ModItems.rbmk_fuel_heu233 = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_heu233).setYield(100000000D)
				.setStats(27.5D).setFunction(EnumBurnFunc.LINEAR).setHeat(1.25D).setMeltingPoint(2865)
				.setUnlocalizedName("rbmk_fuel_heu233").setTextureName(RefStrings.MODID + ":rbmk_fuel_heu233");
		ModItems.rbmk_fuel_heu235 = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_heu235).setYield(100000000D)
				.setStats(50) // Consistency with HEN; its critical mass is too high to justify a linear
								// function
				.setFunction(EnumBurnFunc.SQUARE_ROOT).setMeltingPoint(2865).setUnlocalizedName("rbmk_fuel_heu235")
				.setTextureName(RefStrings.MODID + ":rbmk_fuel_heu235");
		ModItems.rbmk_fuel_thmeu = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_thmeu).setYield(100000000D)
				.setStats(20).setFunction(EnumBurnFunc.PLATEU).setDepletionFunction(EnumDepleteFunc.BOOSTED_SLOPE)
				.setHeat(0.65D) // Consistency with MEU
				.setMeltingPoint(3350).setUnlocalizedName("rbmk_fuel_thmeu")
				.setTextureName(RefStrings.MODID + ":rbmk_fuel_thmeu");
		ModItems.rbmk_fuel_lep = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_lep).setYield(100000000D)
				.setStats(35).setFunction(EnumBurnFunc.LOG_TEN).setDepletionFunction(EnumDepleteFunc.RAISING_SLOPE)
				.setHeat(0.75D).setMeltingPoint(2744).setUnlocalizedName("rbmk_fuel_lep")
				.setTextureName(RefStrings.MODID + ":rbmk_fuel_lep");
		ModItems.rbmk_fuel_mep = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_mep).setYield(100000000D)
				.setStats(35, 20).setFunction(EnumBurnFunc.SQUARE_ROOT).setMeltingPoint(2744)
				.setUnlocalizedName("rbmk_fuel_mep").setTextureName(RefStrings.MODID + ":rbmk_fuel_mep");
		ModItems.rbmk_fuel_hep239 = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_hep239).setYield(100000000D)
				.setStats(30).setFunction(EnumBurnFunc.LINEAR).setHeat(1.25D).setMeltingPoint(2744)
				.setUnlocalizedName("rbmk_fuel_hep").setTextureName(RefStrings.MODID + ":rbmk_fuel_hep");
		ModItems.rbmk_fuel_hep241 = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_hep241).setYield(100000000D)
				.setStats(40).setFunction(EnumBurnFunc.LINEAR).setHeat(1.75D).setMeltingPoint(2744)
				.setUnlocalizedName("rbmk_fuel_hep241").setTextureName(RefStrings.MODID + ":rbmk_fuel_hep241");
		ModItems.rbmk_fuel_lea = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_lea).setYield(100000000D)
				.setStats(60, 10).setFunction(EnumBurnFunc.SQUARE_ROOT)
				.setDepletionFunction(EnumDepleteFunc.RAISING_SLOPE).setHeat(1.5D).setMeltingPoint(2386)
				.setUnlocalizedName("rbmk_fuel_lea").setTextureName(RefStrings.MODID + ":rbmk_fuel_lea");
		ModItems.rbmk_fuel_mea = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_mea).setYield(100000000D)
				.setStats(35D, 20).setFunction(EnumBurnFunc.ARCH).setHeat(1.75D).setMeltingPoint(2386)
				.setUnlocalizedName("rbmk_fuel_mea").setTextureName(RefStrings.MODID + ":rbmk_fuel_mea");
		ModItems.rbmk_fuel_hea241 = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_hea241).setYield(100000000D)
				.setStats(65, 15).setFunction(EnumBurnFunc.SQUARE_ROOT).setHeat(1.85D).setMeltingPoint(2386)
				.setNeutronTypes(NType.FAST, NType.FAST).setUnlocalizedName("rbmk_fuel_hea241")
				.setTextureName(RefStrings.MODID + ":rbmk_fuel_hea241");
		ModItems.rbmk_fuel_hea242 = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_hea242).setYield(100000000D)
				.setStats(45).setFunction(EnumBurnFunc.LINEAR).setHeat(2D).setMeltingPoint(2386)
				.setUnlocalizedName("rbmk_fuel_hea242").setTextureName(RefStrings.MODID + ":rbmk_fuel_hea242");
		ModItems.rbmk_fuel_men = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_men).setYield(100000000D)
				.setStats(30).setFunction(EnumBurnFunc.SQUARE_ROOT).setDepletionFunction(EnumDepleteFunc.RAISING_SLOPE)
				.setHeat(0.75).setMeltingPoint(2800).setNeutronTypes(NType.ANY, NType.FAST) // Build-up of Pu-239 leads
																							// to both speeds of
																							// neutrons grooving
				.setUnlocalizedName("rbmk_fuel_men").setTextureName(RefStrings.MODID + ":rbmk_fuel_men");
		ModItems.rbmk_fuel_hen = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_hen).setYield(100000000D)
				.setStats(40).setFunction(EnumBurnFunc.SQUARE_ROOT).setMeltingPoint(2800)
				.setNeutronTypes(NType.FAST, NType.FAST).setUnlocalizedName("rbmk_fuel_hen")
				.setTextureName(RefStrings.MODID + ":rbmk_fuel_hen");
		ModItems.rbmk_fuel_mox = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_mox).setYield(100000000D)
				.setStats(40).setFunction(EnumBurnFunc.LOG_TEN).setDepletionFunction(EnumDepleteFunc.RAISING_SLOPE)
				.setMeltingPoint(2815).setUnlocalizedName("rbmk_fuel_mox")
				.setTextureName(RefStrings.MODID + ":rbmk_fuel_mox");
		ModItems.rbmk_fuel_les = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_les).setYield(100000000D)
				.setStats(50).setFunction(EnumBurnFunc.SQUARE_ROOT).setHeat(1.25D).setMeltingPoint(2500)
				.setNeutronTypes(NType.SLOW, NType.SLOW) // Beryllium Moderation
				.setUnlocalizedName("rbmk_fuel_les").setTextureName(RefStrings.MODID + ":rbmk_fuel_les");
		ModItems.rbmk_fuel_mes = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_mes).setYield(100000000D)
				.setStats(75D).setFunction(EnumBurnFunc.ARCH).setHeat(1.5D).setMeltingPoint(2750)
				.setUnlocalizedName("rbmk_fuel_mes").setTextureName(RefStrings.MODID + ":rbmk_fuel_mes");
		ModItems.rbmk_fuel_hes = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_hes).setYield(100000000D)
				.setStats(90).setFunction(EnumBurnFunc.LINEAR).setDepletionFunction(EnumDepleteFunc.LINEAR)
				.setHeat(1.75D).setMeltingPoint(3000).setUnlocalizedName("rbmk_fuel_hes")
				.setTextureName(RefStrings.MODID + ":rbmk_fuel_hes");
		ModItems.rbmk_fuel_leaus = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_leaus).setYield(100000000D)
				.setStats(30).setFunction(EnumBurnFunc.SIGMOID).setDepletionFunction(EnumDepleteFunc.LINEAR)
				.setXenon(0.05D, 50D).setHeat(1.5D).setMeltingPoint(7029).setUnlocalizedName("rbmk_fuel_leaus")
				.setTextureName(RefStrings.MODID + ":rbmk_fuel_leaus");
		ModItems.rbmk_fuel_heaus = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_heaus).setYield(100000000D)
				.setStats(35).setFunction(EnumBurnFunc.SQUARE_ROOT).setXenon(0.05D, 50D).setHeat(2D)
				.setMeltingPoint(5211).setUnlocalizedName("rbmk_fuel_heaus")
				.setTextureName(RefStrings.MODID + ":rbmk_fuel_heaus");
		ModItems.rbmk_fuel_po210be = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_po210be).setYield(25000000D)
				.setStats(0D, 50).setFunction(EnumBurnFunc.PASSIVE).setDepletionFunction(EnumDepleteFunc.LINEAR)
				.setXenon(0.0D, 50D).setHeat(0.1D).setDiffusion(0.05D).setMeltingPoint(1287)
				.setNeutronTypes(NType.SLOW, NType.SLOW) // Beryllium Moderation
				.setUnlocalizedName("rbmk_fuel_po210be").setTextureName(RefStrings.MODID + ":rbmk_fuel_po210be");
		ModItems.rbmk_fuel_ra226be = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_ra226be).setYield(100000000D)
				.setStats(0D, 20).setFunction(EnumBurnFunc.PASSIVE).setDepletionFunction(EnumDepleteFunc.LINEAR)
				.setXenon(0.0D, 50D).setHeat(0.035D).setDiffusion(0.5D).setMeltingPoint(700)
				.setNeutronTypes(NType.SLOW, NType.SLOW) // Beryllium Moderation
				.setUnlocalizedName("rbmk_fuel_ra226be").setTextureName(RefStrings.MODID + ":rbmk_fuel_ra226be");
		ModItems.rbmk_fuel_pu238be = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_pu238be).setYield(50000000D)
				.setStats(40, 40).setFunction(EnumBurnFunc.SQUARE_ROOT).setHeat(0.1D).setDiffusion(0.05D)
				.setMeltingPoint(1287).setNeutronTypes(NType.SLOW, NType.SLOW) // Beryllium Moderation
				.setUnlocalizedName("rbmk_fuel_pu238be").setTextureName(RefStrings.MODID + ":rbmk_fuel_pu238be");
		ModItems.rbmk_fuel_balefire_gold = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_balefire_gold)
				.setYield(100000000D).setStats(50, 10).setFunction(EnumBurnFunc.ARCH)
				.setDepletionFunction(EnumDepleteFunc.LINEAR).setXenon(0.0D, 50D).setMeltingPoint(2000)
				.setUnlocalizedName("rbmk_fuel_balefire_gold")
				.setTextureName(RefStrings.MODID + ":rbmk_fuel_balefire_gold");
		ModItems.rbmk_fuel_flashlead = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_flashlead)
				.setYield(250000000D).setStats(40, 50).setFunction(EnumBurnFunc.ARCH)
				.setDepletionFunction(EnumDepleteFunc.LINEAR).setXenon(0.0D, 50D).setMeltingPoint(2050)
				.setUnlocalizedName("rbmk_fuel_flashlead").setTextureName(RefStrings.MODID + ":rbmk_fuel_flashlead");
		ModItems.rbmk_fuel_balefire = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_balefire).setYield(100000000D)
				.setStats(100, 35).setFunction(EnumBurnFunc.LINEAR).setXenon(0.0D, 50D).setHeat(3D)
				.setMeltingPoint(3652).setUnlocalizedName("rbmk_fuel_balefire")
				.setTextureName(RefStrings.MODID + ":rbmk_fuel_balefire");
		ModItems.rbmk_fuel_zfb_bismuth = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_zfb_bismuth)
				.setYield(50000000D).setStats(20).setFunction(EnumBurnFunc.SQUARE_ROOT).setHeat(1.75D)
				.setMeltingPoint(2744).setUnlocalizedName("rbmk_fuel_zfb_bismuth")
				.setTextureName(RefStrings.MODID + ":rbmk_fuel_zfb_bismuth");
		ModItems.rbmk_fuel_zfb_pu241 = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_zfb_pu241).setYield(50000000D)
				.setStats(20).setFunction(EnumBurnFunc.SQUARE_ROOT).setMeltingPoint(2865)
				.setUnlocalizedName("rbmk_fuel_zfb_pu241").setTextureName(RefStrings.MODID + ":rbmk_fuel_zfb_pu241");
		ModItems.rbmk_fuel_zfb_am_mix = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_zfb_am_mix)
				.setYield(50000000D).setStats(20).setFunction(EnumBurnFunc.LINEAR).setHeat(1.75D).setMeltingPoint(2744)
				.setUnlocalizedName("rbmk_fuel_zfb_am_mix").setTextureName(RefStrings.MODID + ":rbmk_fuel_zfb_am_mix");
		ModItems.rbmk_fuel_drx = (ItemRBMKRod) new ItemRBMKRod(ModItems.rbmk_pellet_drx).setYield(1000000D)
				.setStats(1000, 10).setFunction(EnumBurnFunc.QUADRATIC).setHeat(0.1D).setMeltingPoint(100000)
				.setUnlocalizedName("rbmk_fuel_drx").setTextureName(RefStrings.MODID + ":rbmk_fuel_drx");
		ModItems.rbmk_fuel_test = (ItemRBMKRod) new ItemRBMKRod("THE VOICES").setYield(1000000D).setStats(100)
				.setFunction(EnumBurnFunc.EXPERIMENTAL).setHeat(1.0D).setMeltingPoint(100000)
				.setUnlocalizedName("rbmk_fuel_test").setTextureName(RefStrings.MODID + ":rbmk_fuel_test");

		ModItems.watz_pellet = new ItemWatzPellet().setUnlocalizedName("watz_pellet")
				.setTextureName(RefStrings.MODID + ":watz_pellet");
		ModItems.watz_pellet_depleted = new ItemWatzPellet().setUnlocalizedName("watz_pellet_depleted")
				.setTextureName(RefStrings.MODID + ":watz_pellet");

		ModItems.trinitite = new ItemNuclearWaste().setUnlocalizedName("trinitite")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":trinitite_new");
		ModItems.nuclear_waste_long = new ItemWasteLong().setUnlocalizedName("nuclear_waste_long")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste_long");
		ModItems.nuclear_waste_long_tiny = new ItemWasteLong().setUnlocalizedName("nuclear_waste_long_tiny")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste_long_tiny");
		ModItems.nuclear_waste_short = new ItemWasteShort().setUnlocalizedName("nuclear_waste_short")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste_short");
		ModItems.nuclear_waste_short_tiny = new ItemWasteShort().setUnlocalizedName("nuclear_waste_short_tiny")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste_short_tiny");
		ModItems.nuclear_waste_long_depleted = new ItemWasteLong().setUnlocalizedName("nuclear_waste_long_depleted")
				.setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nuclear_waste_long_depleted");
		ModItems.nuclear_waste_long_depleted_tiny = new ItemWasteLong()
				.setUnlocalizedName("nuclear_waste_long_depleted_tiny").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nuclear_waste_long_depleted_tiny");
		ModItems.nuclear_waste_short_depleted = new ItemWasteShort().setUnlocalizedName("nuclear_waste_short_depleted")
				.setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nuclear_waste_short_depleted");
		ModItems.nuclear_waste_short_depleted_tiny = new ItemWasteShort()
				.setUnlocalizedName("nuclear_waste_short_depleted_tiny").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nuclear_waste_short_depleted_tiny");
		ModItems.nuclear_waste = new ItemNuclearWaste().setUnlocalizedName("nuclear_waste")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste");
		ModItems.nuclear_waste_tiny = new ItemNuclearWaste().setUnlocalizedName("nuclear_waste_tiny")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste_tiny");
		ModItems.nuclear_waste_vitrified = new ItemNuclearWaste().setUnlocalizedName("nuclear_waste_vitrified")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste_vitrified");
		ModItems.nuclear_waste_vitrified_tiny = new ItemNuclearWaste()
				.setUnlocalizedName("nuclear_waste_vitrified_tiny").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nuclear_waste_vitrified_tiny");
		ModItems.scrap_plastic = new ItemPlasticScrap().setUnlocalizedName("scrap_plastic")
				.setTextureName(RefStrings.MODID + ":scrap_plastic");
		ModItems.scrap = new Item().setUnlocalizedName("scrap").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":scrap");
		ModItems.scrap_oil = new Item().setUnlocalizedName("scrap_oil").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":scrap_oil");
		ModItems.scrap_nuclear = new Item().setUnlocalizedName("scrap_nuclear").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":scrap_nuclear");
		ModItems.containment_box = new ItemLeadBox().setUnlocalizedName("containment_box")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":containment_box");
		ModItems.plastic_bag = new ItemPlasticBag().setUnlocalizedName("plastic_bag")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":plastic_bag");

		ModItems.debris_graphite = new Item().setUnlocalizedName("debris_graphite")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":debris_graphite");
		ModItems.debris_metal = new Item().setUnlocalizedName("debris_metal").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":debris_metal");
		ModItems.debris_fuel = new Item().setUnlocalizedName("debris_fuel").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":debris_fuel");
		ModItems.debris_concrete = new Item().setUnlocalizedName("debris_concrete")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":debris_concrete");
		ModItems.debris_exchanger = new Item().setUnlocalizedName("debris_exchanger")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":debris_exchanger");
		ModItems.debris_shrapnel = new Item().setUnlocalizedName("debris_shrapnel")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":debris_shrapnel");
		ModItems.debris_element = new Item().setUnlocalizedName("debris_element")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":debris_element");

		ModItems.pellet_cluster = new ItemCustomLore().setUnlocalizedName("pellet_cluster")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pellet_cluster");
		ModItems.powder_fire = new ItemCustomLore().setUnlocalizedName("powder_fire")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_red_phosphorus");
		ModItems.powder_ice = new ItemCustomLore().setUnlocalizedName("powder_ice")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_ice");
		ModItems.powder_poison = new ItemCustomLore().setUnlocalizedName("powder_poison")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_poison");
		ModItems.powder_thermite = new ItemCustomLore().setUnlocalizedName("powder_thermite")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_thermite");
		ModItems.cordite = new Item().setUnlocalizedName("cordite").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":cordite");
		ModItems.ballistite = new Item().setUnlocalizedName("ballistite").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ballistite");
		ModItems.ball_dynamite = new Item().setUnlocalizedName("ball_dynamite").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ball_dynamite");
		ModItems.ball_tnt = new Item().setUnlocalizedName("ball_tnt").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ball_tnt");
		ModItems.ball_tatb = new Item().setUnlocalizedName("ball_tatb").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ball_tatb");
		ModItems.ball_fireclay = new Item().setUnlocalizedName("ball_fireclay").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":ball_fireclay");
		ModItems.pellet_gas = new ItemCustomLore().setUnlocalizedName("pellet_gas")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pellet_gas");
		ModItems.magnetron = new ItemCustomLore().setUnlocalizedName("magnetron").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":magnetron_alt");
		ModItems.pellet_buckshot = new Item().setUnlocalizedName("pellet_buckshot")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pellets_lead");
		ModItems.pellet_flechette = new Item().setUnlocalizedName("pellet_flechette")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pellets_flechette");
		ModItems.pellet_chlorophyte = new Item().setUnlocalizedName("pellet_chlorophyte")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pellets_chlorophyte");
		ModItems.pellet_mercury = new Item().setUnlocalizedName("pellet_mercury").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":pellets_mercury");
		ModItems.pellet_meteorite = new Item().setUnlocalizedName("pellet_meteorite")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pellets_meteorite");
		ModItems.pellet_canister = new Item().setUnlocalizedName("pellet_canister")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pellets_canister");
		ModItems.pellet_claws = new Item().setUnlocalizedName("pellet_claws").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":pellets_claws");
		ModItems.pellet_charged = new Item().setUnlocalizedName("pellet_charged").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":pellets_charged");

		ModItems.pellet_schrabidium = new WatzFuel(50000, 140000, 0.975F, 200, 1.05F, 1.05F)
				.setUnlocalizedName("pellet_schrabidium").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":pellet_schrabidium").setMaxStackSize(1);
		ModItems.pellet_hes = new WatzFuel(108000, 65000, 1F, 85, 1, 1.025F).setUnlocalizedName("pellet_hes")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pellet_hes")
				.setMaxStackSize(1);
		ModItems.pellet_mes = new WatzFuel(216000, 23000, 1.025F, 50, 1, 1F).setUnlocalizedName("pellet_mes")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pellet_mes")
				.setMaxStackSize(1);
		ModItems.pellet_les = new WatzFuel(432000, 7000, 1.05F, 15, 1, 0.975F).setUnlocalizedName("pellet_les")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pellet_les")
				.setMaxStackSize(1);
		ModItems.pellet_beryllium = new WatzFuel(864000, 50, 1.05F, 0, 0.95F, 1.025F)
				.setUnlocalizedName("pellet_beryllium").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":pellet_beryllium").setMaxStackSize(1);
		ModItems.pellet_neptunium = new WatzFuel(216000, 3000, 1.1F, 25, 1.1F, 1.005F)
				.setUnlocalizedName("pellet_neptunium").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":pellet_neptunium").setMaxStackSize(1);
		ModItems.pellet_lead = new WatzFuel(1728000, 0, 0.95F, 0, 0.95F, 0.95F).setUnlocalizedName("pellet_lead")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pellet_lead")
				.setMaxStackSize(1);
		ModItems.pellet_advanced = new WatzFuel(216000, 1000, 1.1F, 0, 0.995F, 0.99F)
				.setUnlocalizedName("pellet_advanced").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":pellet_advanced").setMaxStackSize(1);

		ModItems.designator = new ItemDesingator().setUnlocalizedName("designator").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":designator");
		ModItems.designator_range = new ItemDesingatorRange().setUnlocalizedName("designator_range").setFull3D()
				.setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab)
				.setTextureName(RefStrings.MODID + ":designator_range_alt");
		ModItems.designator_manual = new ItemDesingatorManual().setUnlocalizedName("designator_manual")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab)
				.setTextureName(RefStrings.MODID + ":designator_manual");
		ModItems.designator_arty_range = new ItemDesignatorArtyRange().setUnlocalizedName("designator_arty_range")
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":designator_arty_range");
		ModItems.missile_assembly = new Item().setUnlocalizedName("missile_assembly").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":missile_assembly");
		ModItems.missile_generic = new Item().setUnlocalizedName("missile_generic").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_generic");
		ModItems.missile_anti_ballistic = new Item().setUnlocalizedName("missile_anti_ballistic").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_anti_ballistic");
		ModItems.missile_incendiary = new Item().setUnlocalizedName("missile_incendiary").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_incendiary");
		ModItems.missile_cluster = new Item().setUnlocalizedName("missile_cluster").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_cluster");
		ModItems.missile_buster = new Item().setUnlocalizedName("missile_buster").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_buster");
		ModItems.missile_strong = new Item().setUnlocalizedName("missile_strong").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_strong");
		ModItems.missile_incendiary_strong = new Item().setUnlocalizedName("missile_incendiary_strong")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab)
				.setTextureName(RefStrings.MODID + ":missile_incendiary_strong");
		ModItems.missile_cluster_strong = new Item().setUnlocalizedName("missile_cluster_strong").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_cluster_strong");
		ModItems.missile_buster_strong = new Item().setUnlocalizedName("missile_buster_strong").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_buster_strong");
		ModItems.missile_emp_strong = new Item().setUnlocalizedName("missile_emp_strong").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_emp_strong");
		ModItems.missile_burst = new Item().setUnlocalizedName("missile_burst").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_burst");
		ModItems.missile_inferno = new Item().setUnlocalizedName("missile_inferno").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_inferno");
		ModItems.missile_rain = new Item().setUnlocalizedName("missile_rain").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_rain");
		ModItems.missile_drill = new Item().setUnlocalizedName("missile_drill").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_drill");
		ModItems.missile_nuclear = new Item().setUnlocalizedName("missile_nuclear").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_nuclear");
		ModItems.missile_nuclear_cluster = new Item().setUnlocalizedName("missile_nuclear_cluster").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_nuclear_cluster");
		ModItems.missile_volcano = new ItemCustomLore().setUnlocalizedName("missile_volcano").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_volcano");
		ModItems.missile_endo = new Item().setUnlocalizedName("missile_endo").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_endo");
		ModItems.missile_exo = new Item().setUnlocalizedName("missile_exo").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_exo");
		ModItems.missile_doomsday = new Item().setUnlocalizedName("missile_doomsday").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_doomsday");
		ModItems.missile_taint = new Item().setUnlocalizedName("missile_taint").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_taint");
		ModItems.missile_micro = new Item().setUnlocalizedName("missile_micro").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_micro");
		ModItems.missile_bhole = new Item().setUnlocalizedName("missile_bhole").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_bhole");
		ModItems.missile_schrabidium = new Item().setUnlocalizedName("missile_schrabidium").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_schrabidium");
		ModItems.missile_emp = new Item().setUnlocalizedName("missile_emp").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_emp");
		ModItems.missile_shuttle = new ItemMissileShuttle().setUnlocalizedName("missile_shuttle").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_shuttle");
		ModItems.missile_carrier = new Item().setUnlocalizedName("missile_carrier").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_carrier");
		ModItems.missile_soyuz = new ItemSoyuz().setUnlocalizedName("missile_soyuz").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":soyuz");
		ModItems.missile_soyuz_lander = new ItemCustomLore().setUnlocalizedName("missile_soyuz_lander")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab)
				.setTextureName(RefStrings.MODID + ":soyuz_lander");
		ModItems.missile_custom = new ItemCustomMissile().setUnlocalizedName("missile_custom").setMaxStackSize(1)
				.setCreativeTab(null).setTextureName(RefStrings.MODID + ":missile_custom");
		ModItems.sat_mapper = new ItemSatChip().setUnlocalizedName("sat_mapper").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_mapper");
		ModItems.sat_scanner = new ItemSatChip().setUnlocalizedName("sat_scanner").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_scanner");
		ModItems.sat_radar = new ItemSatChip().setUnlocalizedName("sat_radar").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_radar");
		ModItems.sat_laser = new ItemSatChip().setUnlocalizedName("sat_laser").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_laser");
		ModItems.sat_foeq = new ItemSatChip().setUnlocalizedName("sat_foeq").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_foeq");
		ModItems.sat_resonator = new ItemSatChip().setUnlocalizedName("sat_resonator").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_resonator");
		ModItems.sat_miner = new ItemSatChip().setUnlocalizedName("sat_miner").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_miner");
		ModItems.sat_lunar_miner = new ItemSatChip().setUnlocalizedName("sat_lunar_miner").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_lunar_miner");
		ModItems.sat_gerald = new ItemSatChip().setUnlocalizedName("sat_gerald").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_gerald");
		ModItems.sat_chip = new ItemSatChip().setUnlocalizedName("sat_chip").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_chip");
		ModItems.sat_interface = new ItemSatInterface().setUnlocalizedName("sat_interface").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_interface");
		ModItems.sat_coord = new ItemSatInterface().setUnlocalizedName("sat_coord").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_coord");
		ModItems.sat_designator = new ItemSatDesignator().setUnlocalizedName("sat_designator").setFull3D()
				.setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab)
				.setTextureName(RefStrings.MODID + ":sat_designator");

		ModItems.mp_thruster_10_kerosene = new ItemMissile().makeThruster(FuelType.KEROSENE, 1F, 1.5F, PartSize.SIZE_10)
				.setHealth(10F).setUnlocalizedName("mp_thruster_10_kerosene");
		ModItems.mp_thruster_10_kerosene_tec = new ItemMissile()
				.makeThruster(FuelType.KEROSENE, 1F, 1.5F, PartSize.SIZE_10).setHealth(15F).setRarity(Rarity.COMMON)
				.setUnlocalizedName("mp_thruster_10_kerosene_tec");
		ModItems.mp_thruster_10_solid = new ItemMissile().makeThruster(FuelType.SOLID, 1F, 1.5F, PartSize.SIZE_10)
				.setHealth(15F).setUnlocalizedName("mp_thruster_10_solid");
		ModItems.mp_thruster_10_xenon = new ItemMissile().makeThruster(FuelType.XENON, 1F, 1.5F, PartSize.SIZE_10)
				.setHealth(5F).setUnlocalizedName("mp_thruster_10_xenon");
		ModItems.mp_thruster_15_kerosene = new ItemMissile().makeThruster(FuelType.KEROSENE, 1F, 7.5F, PartSize.SIZE_15)
				.setHealth(15F).setUnlocalizedName("mp_thruster_15_kerosene");
		ModItems.mp_thruster_15_kerosene_tec = new ItemMissile()
				.makeThruster(FuelType.KEROSENE, 1F, 7.5F, PartSize.SIZE_15).setHealth(20F).setRarity(Rarity.COMMON)
				.setUnlocalizedName("mp_thruster_15_kerosene_tec");
		ModItems.mp_thruster_15_kerosene_dual = new ItemMissile()
				.makeThruster(FuelType.KEROSENE, 1F, 2.5F, PartSize.SIZE_15).setHealth(15F)
				.setUnlocalizedName("mp_thruster_15_kerosene_dual");
		ModItems.mp_thruster_15_kerosene_triple = new ItemMissile()
				.makeThruster(FuelType.KEROSENE, 1F, 5F, PartSize.SIZE_15).setHealth(15F)
				.setUnlocalizedName("mp_thruster_15_kerosene_triple");
		ModItems.mp_thruster_15_solid = new ItemMissile().makeThruster(FuelType.SOLID, 1F, 5F, PartSize.SIZE_15)
				.setHealth(20F).setUnlocalizedName("mp_thruster_15_solid");
		ModItems.mp_thruster_15_solid_hexdecuple = new ItemMissile()
				.makeThruster(FuelType.SOLID, 1F, 5F, PartSize.SIZE_15).setHealth(25F).setRarity(Rarity.UNCOMMON)
				.setUnlocalizedName("mp_thruster_15_solid_hexdecuple");
		ModItems.mp_thruster_15_hydrogen = new ItemMissile().makeThruster(FuelType.HYDROGEN, 1F, 7.5F, PartSize.SIZE_15)
				.setHealth(20F).setUnlocalizedName("mp_thruster_15_hydrogen");
		ModItems.mp_thruster_15_hydrogen_dual = new ItemMissile()
				.makeThruster(FuelType.HYDROGEN, 1F, 2.5F, PartSize.SIZE_15).setHealth(15F)
				.setUnlocalizedName("mp_thruster_15_hydrogen_dual");
		ModItems.mp_thruster_15_balefire_short = new ItemMissile()
				.makeThruster(FuelType.BALEFIRE, 1F, 5F, PartSize.SIZE_15).setHealth(25F)
				.setUnlocalizedName("mp_thruster_15_balefire_short");
		ModItems.mp_thruster_15_balefire = new ItemMissile().makeThruster(FuelType.BALEFIRE, 1F, 5F, PartSize.SIZE_15)
				.setHealth(25F).setUnlocalizedName("mp_thruster_15_balefire");
		ModItems.mp_thruster_15_balefire_large = new ItemMissile()
				.makeThruster(FuelType.BALEFIRE, 1F, 7.5F, PartSize.SIZE_15).setHealth(35F)
				.setUnlocalizedName("mp_thruster_15_balefire_large");
		ModItems.mp_thruster_15_balefire_large_rad = new ItemMissile()
				.makeThruster(FuelType.BALEFIRE, 1F, 7.5F, PartSize.SIZE_15).setAuthor("The Master").setHealth(35F)
				.setRarity(Rarity.UNCOMMON).setUnlocalizedName("mp_thruster_15_balefire_large_rad");
		ModItems.mp_thruster_20_kerosene = new ItemMissile().makeThruster(FuelType.KEROSENE, 1F, 100F, PartSize.SIZE_20)
				.setHealth(30F).setUnlocalizedName("mp_thruster_20_kerosene");
		ModItems.mp_thruster_20_kerosene_dual = new ItemMissile()
				.makeThruster(FuelType.KEROSENE, 1F, 100F, PartSize.SIZE_20).setHealth(30F)
				.setUnlocalizedName("mp_thruster_20_kerosene_dual");
		ModItems.mp_thruster_20_kerosene_triple = new ItemMissile()
				.makeThruster(FuelType.KEROSENE, 1F, 100F, PartSize.SIZE_20).setHealth(30F)
				.setUnlocalizedName("mp_thruster_20_kerosene_triple");
		ModItems.mp_thruster_20_solid = new ItemMissile().makeThruster(FuelType.SOLID, 1F, 100F, PartSize.SIZE_20)
				.setHealth(35F).setWittyText("It's basically just a big hole at the end of the fuel tank.")
				.setUnlocalizedName("mp_thruster_20_solid");
		ModItems.mp_thruster_20_solid_multi = new ItemMissile().makeThruster(FuelType.SOLID, 1F, 100F, PartSize.SIZE_20)
				.setHealth(35F).setUnlocalizedName("mp_thruster_20_solid_multi");
		ModItems.mp_thruster_20_solid_multier = new ItemMissile()
				.makeThruster(FuelType.SOLID, 1F, 100F, PartSize.SIZE_20).setHealth(35F)
				.setWittyText("Did I miscount? Hope not.").setUnlocalizedName("mp_thruster_20_solid_multier");

		ModItems.mp_stability_10_flat = new ItemMissile().makeStability(0.5F, PartSize.SIZE_10).setHealth(10F)
				.setUnlocalizedName("mp_stability_10_flat");
		ModItems.mp_stability_10_cruise = new ItemMissile().makeStability(0.25F, PartSize.SIZE_10).setHealth(5F)
				.setUnlocalizedName("mp_stability_10_cruise");
		ModItems.mp_stability_10_space = new ItemMissile().makeStability(0.35F, PartSize.SIZE_10).setHealth(5F)
				.setRarity(Rarity.COMMON)
				.setWittyText("Standing there alone, the ship is waiting / All systems are go, are you sure?")
				.setUnlocalizedName("mp_stability_10_space");
		ModItems.mp_stability_15_flat = new ItemMissile().makeStability(0.5F, PartSize.SIZE_15).setHealth(10F)
				.setUnlocalizedName("mp_stability_15_flat");
		ModItems.mp_stability_15_thin = new ItemMissile().makeStability(0.35F, PartSize.SIZE_15).setHealth(5F)
				.setUnlocalizedName("mp_stability_15_thin");
		ModItems.mp_stability_15_soyuz = new ItemMissile().makeStability(0.25F, PartSize.SIZE_15).setHealth(15F)
				.setRarity(Rarity.COMMON).setWittyText("Союз!").setUnlocalizedName("mp_stability_15_soyuz");
		ModItems.mp_stability_20_flat = new ItemMissile().makeStability(0.5F, PartSize.SIZE_20)
				.setUnlocalizedName("mp_s_20");

		ModItems.mp_fuselage_10_kerosene = new ItemMissile()
				.makeFuselage(FuelType.KEROSENE, 2500F, PartSize.SIZE_10, PartSize.SIZE_10).setAuthor("Hoboy")
				.setHealth(20F).setUnlocalizedName("mp_fuselage_10_kerosene");
		ModItems.mp_fuselage_10_kerosene_camo = ((ItemMissile) ModItems.mp_fuselage_10_kerosene).copy()
				.setRarity(Rarity.COMMON).setTitle("Camo").setUnlocalizedName("mp_fuselage_10_kerosene_camo");
		ModItems.mp_fuselage_10_kerosene_desert = ((ItemMissile) ModItems.mp_fuselage_10_kerosene).copy()
				.setRarity(Rarity.COMMON).setTitle("Desert Camo").setUnlocalizedName("mp_fuselage_10_kerosene_desert");
		ModItems.mp_fuselage_10_kerosene_sky = ((ItemMissile) ModItems.mp_fuselage_10_kerosene).copy()
				.setRarity(Rarity.COMMON).setTitle("Sky Camo").setUnlocalizedName("mp_fuselage_10_kerosene_sky");
		ModItems.mp_fuselage_10_kerosene_flames = ((ItemMissile) ModItems.mp_fuselage_10_kerosene).copy()
				.setRarity(Rarity.UNCOMMON).setTitle("Sick Flames")
				.setUnlocalizedName("mp_fuselage_10_kerosene_flames");
		ModItems.mp_fuselage_10_kerosene_insulation = ((ItemMissile) ModItems.mp_fuselage_10_kerosene).copy()
				.setRarity(Rarity.COMMON).setTitle("Orange Insulation").setHealth(25F)
				.setUnlocalizedName("mp_fuselage_10_kerosene_insulation");
		ModItems.mp_fuselage_10_kerosene_sleek = ((ItemMissile) ModItems.mp_fuselage_10_kerosene).copy()
				.setRarity(Rarity.RARE).setTitle("IF-R&D").setHealth(35F)
				.setUnlocalizedName("mp_fuselage_10_kerosene_sleek");
		ModItems.mp_fuselage_10_kerosene_metal = ((ItemMissile) ModItems.mp_fuselage_10_kerosene).copy()
				.setRarity(Rarity.UNCOMMON).setTitle("Bolted Metal").setHealth(30F).setAuthor("Hoboy")
				.setUnlocalizedName("mp_fuselage_10_kerosene_metal");
		ModItems.mp_fuselage_10_kerosene_taint = ((ItemMissile) ModItems.mp_fuselage_10_kerosene).copy()
				.setRarity(Rarity.UNCOMMON).setAuthor("Sam").setTitle("Tainted")
				.setUnlocalizedName("mp_fuselage_10_kerosene_taint");

		ModItems.mp_fuselage_10_solid = new ItemMissile()
				.makeFuselage(FuelType.SOLID, 2500F, PartSize.SIZE_10, PartSize.SIZE_10).setHealth(25F)
				.setUnlocalizedName("mp_fuselage_10_solid");
		ModItems.mp_fuselage_10_solid_flames = ((ItemMissile) ModItems.mp_fuselage_10_solid).copy()
				.setRarity(Rarity.UNCOMMON).setTitle("Sick Flames").setUnlocalizedName("mp_fuselage_10_solid_flames");
		ModItems.mp_fuselage_10_solid_insulation = ((ItemMissile) ModItems.mp_fuselage_10_solid).copy()
				.setRarity(Rarity.COMMON).setTitle("Orange Insulation").setHealth(30F)
				.setUnlocalizedName("mp_fuselage_10_solid_insulation");
		ModItems.mp_fuselage_10_solid_sleek = ((ItemMissile) ModItems.mp_fuselage_10_solid).copy()
				.setRarity(Rarity.RARE).setTitle("IF-R&D").setHealth(35F)
				.setUnlocalizedName("mp_fuselage_10_solid_sleek");
		ModItems.mp_fuselage_10_solid_soviet_glory = ((ItemMissile) ModItems.mp_fuselage_10_solid).copy()
				.setRarity(Rarity.EPIC).setAuthor("Hoboy").setHealth(35F).setTitle("Soviet Glory")
				.setUnlocalizedName("mp_fuselage_10_solid_soviet_glory");
		ModItems.mp_fuselage_10_solid_cathedral = ((ItemMissile) ModItems.mp_fuselage_10_solid).copy()
				.setRarity(Rarity.RARE).setAuthor("Satan").setTitle("Unholy Cathedral").setWittyText("Quakeesque!")
				.setUnlocalizedName("mp_fuselage_10_solid_cathedral");
		ModItems.mp_fuselage_10_solid_moonlit = ((ItemMissile) ModItems.mp_fuselage_10_solid).copy()
				.setRarity(Rarity.UNCOMMON).setAuthor("The Master & Hoboy").setTitle("Moonlit")
				.setUnlocalizedName("mp_fuselage_10_solid_moonlit");
		ModItems.mp_fuselage_10_solid_battery = ((ItemMissile) ModItems.mp_fuselage_10_solid).copy()
				.setRarity(Rarity.UNCOMMON).setAuthor("wolfmonster222").setHealth(30F).setTitle("Ecstatic")
				.setWittyText("I got caught eating batteries again :(")
				.setUnlocalizedName("mp_fuselage_10_solid_battery");
		ModItems.mp_fuselage_10_solid_duracell = ((ItemMissile) ModItems.mp_fuselage_10_solid).copy()
				.setRarity(Rarity.RARE).setAuthor("Hoboy").setTitle("Duracell").setHealth(30F)
				.setWittyText("The crunchiest battery on the market!")
				.setUnlocalizedName("mp_fuselage_10_solid_duracell");

		ModItems.mp_fuselage_10_xenon = new ItemMissile()
				.makeFuselage(FuelType.XENON, 5000F, PartSize.SIZE_10, PartSize.SIZE_10).setHealth(20F)
				.setUnlocalizedName("mp_fuselage_10_xenon");
		ModItems.mp_fuselage_10_xenon_bhole = ((ItemMissile) ModItems.mp_fuselage_10_xenon).copy()
				.setRarity(Rarity.RARE).setAuthor("Sten89").setTitle("Morceus-1457")
				.setUnlocalizedName("mp_fuselage_10_xenon_bhole");

		ModItems.mp_fuselage_10_long_kerosene = new ItemMissile()
				.makeFuselage(FuelType.KEROSENE, 5000F, PartSize.SIZE_10, PartSize.SIZE_10).setAuthor("Hoboy")
				.setHealth(30F).setUnlocalizedName("mp_fuselage_10_long_kerosene");
		ModItems.mp_fuselage_10_long_kerosene_camo = ((ItemMissile) ModItems.mp_fuselage_10_long_kerosene).copy()
				.setRarity(Rarity.COMMON).setTitle("Camo").setUnlocalizedName("mp_fuselage_10_long_kerosene_camo");
		ModItems.mp_fuselage_10_long_kerosene_desert = ((ItemMissile) ModItems.mp_fuselage_10_long_kerosene).copy()
				.setRarity(Rarity.COMMON).setTitle("Desert Camo")
				.setUnlocalizedName("mp_fuselage_10_long_kerosene_desert");
		ModItems.mp_fuselage_10_long_kerosene_sky = ((ItemMissile) ModItems.mp_fuselage_10_long_kerosene).copy()
				.setRarity(Rarity.COMMON).setTitle("Sky Camo").setUnlocalizedName("mp_fuselage_10_long_kerosene_sky");
		ModItems.mp_fuselage_10_long_kerosene_flames = ((ItemMissile) ModItems.mp_fuselage_10_long_kerosene).copy()
				.setRarity(Rarity.UNCOMMON).setTitle("Sick Flames")
				.setUnlocalizedName("mp_fuselage_10_long_kerosene_flames");
		ModItems.mp_fuselage_10_long_kerosene_insulation = ((ItemMissile) ModItems.mp_fuselage_10_long_kerosene).copy()
				.setRarity(Rarity.COMMON).setTitle("Orange Insulation").setHealth(35F)
				.setUnlocalizedName("mp_fuselage_10_long_kerosene_insulation");
		ModItems.mp_fuselage_10_long_kerosene_sleek = ((ItemMissile) ModItems.mp_fuselage_10_long_kerosene).copy()
				.setRarity(Rarity.RARE).setTitle("IF-R&D").setHealth(40F)
				.setUnlocalizedName("mp_fuselage_10_long_kerosene_sleek");
		ModItems.mp_fuselage_10_long_kerosene_metal = ((ItemMissile) ModItems.mp_fuselage_10_long_kerosene).copy()
				.setRarity(Rarity.UNCOMMON).setAuthor("Hoboy").setHealth(35F)
				.setUnlocalizedName("mp_fuselage_10_long_kerosene_metal");
		ModItems.mp_fuselage_10_long_kerosene_dash = ((ItemMissile) ModItems.mp_fuselage_10_long_kerosene).copy()
				.setRarity(Rarity.EPIC).setAuthor("Sam").setTitle("Dash").setWittyText("I wash my hands of it.")
				.setCreativeTab(null).setUnlocalizedName("mp_fuselage_10_long_kerosene_dash");
		ModItems.mp_fuselage_10_long_kerosene_taint = ((ItemMissile) ModItems.mp_fuselage_10_long_kerosene).copy()
				.setRarity(Rarity.UNCOMMON).setAuthor("Sam").setTitle("Tainted")
				.setUnlocalizedName("mp_fuselage_10_long_kerosene_taint");
		ModItems.mp_fuselage_10_long_kerosene_vap = ((ItemMissile) ModItems.mp_fuselage_10_long_kerosene).copy()
				.setRarity(Rarity.EPIC).setAuthor("VT-6/24").setTitle("Minty Contrail").setWittyText("Upper rivet!")
				.setUnlocalizedName("mp_fuselage_10_long_kerosene_vap");

		ModItems.mp_fuselage_10_long_solid = new ItemMissile()
				.makeFuselage(FuelType.SOLID, 5000F, PartSize.SIZE_10, PartSize.SIZE_10).setHealth(35F)
				.setUnlocalizedName("mp_fuselage_10_long_solid");
		ModItems.mp_fuselage_10_long_solid_flames = ((ItemMissile) ModItems.mp_fuselage_10_long_solid).copy()
				.setRarity(Rarity.UNCOMMON).setTitle("Sick Flames")
				.setUnlocalizedName("mp_fuselage_10_long_solid_flames");
		ModItems.mp_fuselage_10_long_solid_insulation = ((ItemMissile) ModItems.mp_fuselage_10_long_solid).copy()
				.setRarity(Rarity.COMMON).setTitle("Orange Insulation").setHealth(40F)
				.setUnlocalizedName("mp_fuselage_10_long_solid_insulation");
		ModItems.mp_fuselage_10_long_solid_sleek = ((ItemMissile) ModItems.mp_fuselage_10_long_solid).copy()
				.setRarity(Rarity.RARE).setTitle("IF-R&D").setHealth(45F)
				.setUnlocalizedName("mp_fuselage_10_long_solid_sleek");
		ModItems.mp_fuselage_10_long_solid_soviet_glory = ((ItemMissile) ModItems.mp_fuselage_10_long_solid).copy()
				.setRarity(Rarity.EPIC).setAuthor("Hoboy").setHealth(45F).setTitle("Soviet Glory")
				.setWittyText("Fully Automated Luxury Gay Space Communism!")
				.setUnlocalizedName("mp_fuselage_10_long_solid_soviet_glory");
		ModItems.mp_fuselage_10_long_solid_bullet = ((ItemMissile) ModItems.mp_fuselage_10_long_solid).copy()
				.setRarity(Rarity.COMMON).setAuthor("Sam").setTitle("Bullet Bill")
				.setUnlocalizedName("mp_fuselage_10_long_solid_bullet");
		ModItems.mp_fuselage_10_long_solid_silvermoonlight = ((ItemMissile) ModItems.mp_fuselage_10_long_solid).copy()
				.setRarity(Rarity.UNCOMMON).setAuthor("The Master").setTitle("Silver Moonlight")
				.setUnlocalizedName("mp_fuselage_10_long_solid_silvermoonlight");

		ModItems.mp_fuselage_10_15_kerosene = new ItemMissile()
				.makeFuselage(FuelType.KEROSENE, 10000F, PartSize.SIZE_10, PartSize.SIZE_15).setHealth(40F)
				.setUnlocalizedName("mp_fuselage_10_15_kerosene");
		ModItems.mp_fuselage_10_15_solid = new ItemMissile()
				.makeFuselage(FuelType.SOLID, 10000F, PartSize.SIZE_10, PartSize.SIZE_15).setHealth(40F)
				.setUnlocalizedName("mp_fuselage_10_15_solid");
		ModItems.mp_fuselage_10_15_hydrogen = new ItemMissile()
				.makeFuselage(FuelType.HYDROGEN, 10000F, PartSize.SIZE_10, PartSize.SIZE_15).setHealth(40F)
				.setUnlocalizedName("mp_fuselage_10_15_hydrogen");
		ModItems.mp_fuselage_10_15_balefire = new ItemMissile()
				.makeFuselage(FuelType.BALEFIRE, 10000F, PartSize.SIZE_10, PartSize.SIZE_15).setHealth(40F)
				.setUnlocalizedName("mp_fuselage_10_15_balefire");

		ModItems.mp_fuselage_15_kerosene = new ItemMissile()
				.makeFuselage(FuelType.KEROSENE, 15000F, PartSize.SIZE_15, PartSize.SIZE_15).setAuthor("Hoboy")
				.setHealth(50F).setUnlocalizedName("mp_fuselage_15_kerosene");
		ModItems.mp_fuselage_15_kerosene_camo = ((ItemMissile) ModItems.mp_fuselage_15_kerosene).copy()
				.setRarity(Rarity.COMMON).setTitle("Camo").setUnlocalizedName("mp_fuselage_15_kerosene_camo");
		ModItems.mp_fuselage_15_kerosene_desert = ((ItemMissile) ModItems.mp_fuselage_15_kerosene).copy()
				.setRarity(Rarity.COMMON).setTitle("Desert Camo").setUnlocalizedName("mp_fuselage_15_kerosene_desert");
		ModItems.mp_fuselage_15_kerosene_sky = ((ItemMissile) ModItems.mp_fuselage_15_kerosene).copy()
				.setRarity(Rarity.COMMON).setTitle("Sky Camo").setUnlocalizedName("mp_fuselage_15_kerosene_sky");
		ModItems.mp_fuselage_15_kerosene_insulation = ((ItemMissile) ModItems.mp_fuselage_15_kerosene).copy()
				.setRarity(Rarity.COMMON).setTitle("Orange Insulation").setHealth(55F)
				.setWittyText("Rest in spaghetti Columbia :(").setUnlocalizedName("mp_fuselage_15_kerosene_insulation");
		ModItems.mp_fuselage_15_kerosene_metal = ((ItemMissile) ModItems.mp_fuselage_15_kerosene).copy()
				.setRarity(Rarity.UNCOMMON).setAuthor("Hoboy").setTitle("Bolted Metal").setHealth(60F)
				.setWittyText("Metal frame with metal plating reinforced with bolted metal sheets and metal.")
				.setUnlocalizedName("mp_fuselage_15_kerosene_metal");
		ModItems.mp_fuselage_15_kerosene_decorated = ((ItemMissile) ModItems.mp_fuselage_15_kerosene).copy()
				.setRarity(Rarity.UNCOMMON).setAuthor("Hoboy").setTitle("Decorated").setHealth(60F)
				.setUnlocalizedName("mp_fuselage_15_kerosene_decorated");
		ModItems.mp_fuselage_15_kerosene_steampunk = ((ItemMissile) ModItems.mp_fuselage_15_kerosene).copy()
				.setRarity(Rarity.RARE).setAuthor("Hoboy").setTitle("Steampunk").setHealth(60F)
				.setUnlocalizedName("mp_fuselage_15_kerosene_steampunk");
		ModItems.mp_fuselage_15_kerosene_polite = ((ItemMissile) ModItems.mp_fuselage_15_kerosene).copy()
				.setRarity(Rarity.LEGENDARY).setAuthor("Hoboy").setTitle("Polite").setHealth(60F)
				.setUnlocalizedName("mp_fuselage_15_kerosene_polite");
		ModItems.mp_fuselage_15_kerosene_blackjack = ((ItemMissile) ModItems.mp_fuselage_15_kerosene).copy()
				.setRarity(Rarity.LEGENDARY).setTitle("Queen Whiskey").setHealth(100F)
				.setUnlocalizedName("mp_fuselage_15_kerosene_blackjack");
		ModItems.mp_fuselage_15_kerosene_lambda = ((ItemMissile) ModItems.mp_fuselage_15_kerosene).copy()
				.setRarity(Rarity.RARE).setAuthor("VT-6/24").setTitle("Lambda Complex").setHealth(75F)
				.setWittyText("MAGNIFICENT MICROWAVE CASSEROLE").setUnlocalizedName("mp_fuselage_15_kerosene_lambda");
		ModItems.mp_fuselage_15_kerosene_minuteman = ((ItemMissile) ModItems.mp_fuselage_15_kerosene).copy()
				.setRarity(Rarity.UNCOMMON).setAuthor("Spexta").setTitle("MX 1702")
				.setUnlocalizedName("mp_fuselage_15_kerosene_minuteman");
		ModItems.mp_fuselage_15_kerosene_pip = ((ItemMissile) ModItems.mp_fuselage_15_kerosene).copy()
				.setRarity(Rarity.EPIC).setAuthor("The Doctor").setTitle("LittlePip").setWittyText("31!")
				.setCreativeTab(null).setUnlocalizedName("mp_fuselage_15_kerosene_pip");
		ModItems.mp_fuselage_15_kerosene_taint = ((ItemMissile) ModItems.mp_fuselage_15_kerosene).copy()
				.setRarity(Rarity.UNCOMMON).setAuthor("Sam").setTitle("Tainted").setWittyText("DUN-DUN!")
				.setUnlocalizedName("mp_fuselage_15_kerosene_taint");
		ModItems.mp_fuselage_15_kerosene_yuck = ((ItemMissile) ModItems.mp_fuselage_15_kerosene).copy()
				.setRarity(Rarity.EPIC).setAuthor("Hoboy").setTitle("Flesh")
				.setWittyText("Note: Never clean DNA vials with your own spit.").setHealth(60F)
				.setUnlocalizedName("mp_fuselage_15_kerosene_yuck");

		ModItems.mp_fuselage_15_solid = new ItemMissile()
				.makeFuselage(FuelType.SOLID, 15000F, PartSize.SIZE_15, PartSize.SIZE_15).setHealth(60F)
				.setUnlocalizedName("mp_fuselage_15_solid").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab)
				.setTextureName(RefStrings.MODID + ":mp_fuselage");
		ModItems.mp_fuselage_15_solid_insulation = ((ItemMissile) ModItems.mp_fuselage_15_solid).copy()
				.setRarity(Rarity.COMMON).setTitle("Orange Insulation").setHealth(65F)
				.setUnlocalizedName("mp_fuselage_15_solid_insulation");
		ModItems.mp_fuselage_15_solid_desh = ((ItemMissile) ModItems.mp_fuselage_15_solid).copy().setRarity(Rarity.RARE)
				.setAuthor("Hoboy").setTitle("Desh Plating").setHealth(80F)
				.setUnlocalizedName("mp_fuselage_15_solid_desh");
		ModItems.mp_fuselage_15_solid_soviet_glory = ((ItemMissile) ModItems.mp_fuselage_15_solid).copy()
				.setRarity(Rarity.RARE).setAuthor("Hoboy").setTitle("Soviet Glory").setHealth(70F)
				.setUnlocalizedName("mp_fuselage_15_solid_soviet_glory");
		ModItems.mp_fuselage_15_solid_soviet_stank = ((ItemMissile) ModItems.mp_fuselage_15_solid).copy()
				.setRarity(Rarity.EPIC).setAuthor("Hoboy").setTitle("Soviet Stank").setHealth(15F)
				.setWittyText("Aged like a fine wine! Well, almost.")
				.setUnlocalizedName("mp_fuselage_15_solid_soviet_stank");
		ModItems.mp_fuselage_15_solid_faust = ((ItemMissile) ModItems.mp_fuselage_15_solid).copy()
				.setRarity(Rarity.LEGENDARY).setAuthor("Dr.Nostalgia").setTitle("Mighty Lauren").setHealth(250F)
				.setWittyText("Welcome to Subway, may I take your order?")
				.setUnlocalizedName("mp_fuselage_15_solid_faust");
		ModItems.mp_fuselage_15_solid_silvermoonlight = ((ItemMissile) ModItems.mp_fuselage_15_solid).copy()
				.setRarity(Rarity.UNCOMMON).setAuthor("The Master").setTitle("Silver Moonlight")
				.setUnlocalizedName("mp_fuselage_15_solid_silvermoonlight");
		ModItems.mp_fuselage_15_solid_snowy = ((ItemMissile) ModItems.mp_fuselage_15_solid).copy()
				.setRarity(Rarity.UNCOMMON).setAuthor("Dr.Nostalgia").setTitle("Chilly Day")
				.setUnlocalizedName("mp_fuselage_15_solid_snowy");
		ModItems.mp_fuselage_15_solid_panorama = ((ItemMissile) ModItems.mp_fuselage_15_solid).copy()
				.setRarity(Rarity.RARE).setAuthor("Hoboy").setTitle("Panorama")
				.setUnlocalizedName("mp_fuselage_15_solid_panorama");
		ModItems.mp_fuselage_15_solid_roses = ((ItemMissile) ModItems.mp_fuselage_15_solid).copy()
				.setRarity(Rarity.UNCOMMON).setAuthor("Hoboy").setTitle("Bed of roses")
				.setUnlocalizedName("mp_fuselage_15_solid_roses");
		ModItems.mp_fuselage_15_solid_mimi = ((ItemMissile) ModItems.mp_fuselage_15_solid).copy().setRarity(Rarity.RARE)
				.setTitle("Mimi-chan").setUnlocalizedName("mp_fuselage_15_solid_mimi");

		ModItems.mp_fuselage_15_hydrogen = new ItemMissile()
				.makeFuselage(FuelType.HYDROGEN, 15000F, PartSize.SIZE_15, PartSize.SIZE_15).setHealth(50F)
				.setUnlocalizedName("mp_fuselage_15_hydrogen").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_fuselage");
		ModItems.mp_fuselage_15_hydrogen_cathedral = ((ItemMissile) ModItems.mp_fuselage_15_hydrogen).copy()
				.setRarity(Rarity.UNCOMMON).setAuthor("Satan").setTitle("Unholy Cathedral")
				.setUnlocalizedName("mp_fuselage_15_hydrogen_cathedral");

		ModItems.mp_fuselage_15_balefire = new ItemMissile()
				.makeFuselage(FuelType.BALEFIRE, 15000F, PartSize.SIZE_15, PartSize.SIZE_15).setHealth(75F)
				.setUnlocalizedName("mp_fuselage_15_balefire").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_fuselage");

		ModItems.mp_fuselage_15_20_kerosene = new ItemMissile()
				.makeFuselage(FuelType.KEROSENE, 20000, PartSize.SIZE_15, PartSize.SIZE_20).setAuthor("Hoboy")
				.setHealth(70F).setUnlocalizedName("mp_fuselage_15_20_kerosene").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_fuselage");
		ModItems.mp_fuselage_15_20_kerosene_magnusson = ((ItemMissile) ModItems.mp_fuselage_15_20_kerosene).copy()
				.setRarity(Rarity.RARE).setAuthor("VT-6/24").setTitle("White Forest Rocket")
				.setWittyText("And get your cranio-conjugal parasite away from my nose cone!")
				.setUnlocalizedName("mp_fuselage_15_20_kerosene_magnusson");
		ModItems.mp_fuselage_15_20_solid = new ItemMissile()
				.makeFuselage(FuelType.SOLID, 20000, PartSize.SIZE_15, PartSize.SIZE_20).setHealth(70F)
				.setUnlocalizedName("mp_fuselage_15_20_solid").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_fuselage");

		ModItems.mp_fuselage_20_kerosene = new ItemMissile()
				.makeFuselage(FuelType.KEROSENE, 1000F, PartSize.SIZE_20, PartSize.SIZE_20)
				.setUnlocalizedName("mp_f_20").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab)
				.setTextureName(RefStrings.MODID + ":mp_fuselage");

		ModItems.mp_warhead_10_he = new ItemMissile().makeWarhead(WarheadType.HE, 15F, 1.5F, PartSize.SIZE_10)
				.setHealth(5F).setUnlocalizedName("mp_warhead_10_he").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		ModItems.mp_warhead_10_incendiary = new ItemMissile().makeWarhead(WarheadType.INC, 15F, 1.5F, PartSize.SIZE_10)
				.setHealth(5F).setUnlocalizedName("mp_warhead_10_incendiary").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		ModItems.mp_warhead_10_buster = new ItemMissile().makeWarhead(WarheadType.BUSTER, 5F, 1.5F, PartSize.SIZE_10)
				.setHealth(5F).setUnlocalizedName("mp_warhead_10_buster").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		ModItems.mp_warhead_10_nuclear = new ItemMissile().makeWarhead(WarheadType.NUCLEAR, 35F, 1.5F, PartSize.SIZE_10)
				.setTitle("Tater Tot").setHealth(10F).setUnlocalizedName("mp_warhead_10_nuclear").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		ModItems.mp_warhead_10_nuclear_large = new ItemMissile()
				.makeWarhead(WarheadType.NUCLEAR, 75F, 2.5F, PartSize.SIZE_10).setTitle("Chernobyl Boris")
				.setHealth(15F).setUnlocalizedName("mp_warhead_10_nuclear_large").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		ModItems.mp_warhead_10_taint = new ItemMissile().makeWarhead(WarheadType.TAINT, 15F, 1.5F, PartSize.SIZE_10)
				.setHealth(20F).setRarity(Rarity.UNCOMMON)
				.setWittyText("Eat my taint! Bureaucracy is dead and we killed it!")
				.setUnlocalizedName("mp_warhead_10_taint").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab)
				.setTextureName(RefStrings.MODID + ":mp_warhead");
		ModItems.mp_warhead_10_cloud = new ItemMissile().makeWarhead(WarheadType.CLOUD, 15F, 1.5F, PartSize.SIZE_10)
				.setHealth(20F).setRarity(Rarity.RARE).setUnlocalizedName("mp_warhead_10_cloud").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		ModItems.mp_warhead_15_he = new ItemMissile().makeWarhead(WarheadType.HE, 50F, 2.5F, PartSize.SIZE_15)
				.setHealth(10F).setUnlocalizedName("mp_warhead_15_he").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		ModItems.mp_warhead_15_incendiary = new ItemMissile().makeWarhead(WarheadType.INC, 35F, 2.5F, PartSize.SIZE_15)
				.setHealth(10F).setUnlocalizedName("mp_warhead_15_incendiary").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		ModItems.mp_warhead_15_nuclear = new ItemMissile().makeWarhead(WarheadType.NUCLEAR, 125F, 5F, PartSize.SIZE_15)
				.setTitle("Auntie Bertha").setHealth(15F).setUnlocalizedName("mp_warhead_15_nuclear").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		ModItems.mp_warhead_15_nuclear_shark = ((ItemMissile) ModItems.mp_warhead_15_nuclear).copy()
				.setRarity(Rarity.UNCOMMON).setTitle("Discount Bullet Bill")
				.setWittyText("Nose art on a cannon bullet? Who does that?")
				.setUnlocalizedName("mp_warhead_15_nuclear_shark");
		ModItems.mp_warhead_15_nuclear_mimi = ((ItemMissile) ModItems.mp_warhead_15_nuclear).copy()
				.setRarity(Rarity.RARE).setTitle("FASHIONABLE MISSILE")
				.setUnlocalizedName("mp_warhead_15_nuclear_mimi");
		ModItems.mp_warhead_15_boxcar = new ItemMissile().makeWarhead(WarheadType.TX, 250F, 7.5F, PartSize.SIZE_15)
				.setWittyText("?!?!").setHealth(35F).setRarity(Rarity.LEGENDARY)
				.setUnlocalizedName("mp_warhead_15_boxcar").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab)
				.setTextureName(RefStrings.MODID + ":mp_warhead");
		ModItems.mp_warhead_15_n2 = new ItemMissile().makeWarhead(WarheadType.N2, 100F, 5F, PartSize.SIZE_15)
				.setWittyText("[screams geometrically]").setHealth(20F).setRarity(Rarity.RARE)
				.setUnlocalizedName("mp_warhead_15_n2").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab)
				.setTextureName(RefStrings.MODID + ":mp_warhead");
		ModItems.mp_warhead_15_balefire = new ItemMissile()
				.makeWarhead(WarheadType.BALEFIRE, 100F, 7.5F, PartSize.SIZE_15).setRarity(Rarity.LEGENDARY)
				.setAuthor("VT-6/24").setHealth(15F).setWittyText("Hightower, never forgetti.")
				.setUnlocalizedName("mp_warhead_15_balefire").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab)
				.setTextureName(RefStrings.MODID + ":mp_warhead");
		ModItems.mp_warhead_15_turbine = new ItemMissile().makeWarhead(WarheadType.TURBINE, 200F, 5F, PartSize.SIZE_15)
				.setRarity(Rarity.SEWS_CLOTHES_AND_SUCKS_HORSE_COCK).setHealth(250F)
				.setUnlocalizedName("mp_warhead_15_turbine").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab)
				.setTextureName(RefStrings.MODID + ":mp_warhead");
		ModItems.mp_warhead_20_he = new ItemMissile().makeWarhead(WarheadType.HE, 15F, 1F, PartSize.SIZE_20)
				.setUnlocalizedName("mp_w_20").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab)
				.setTextureName(RefStrings.MODID + ":mp_warhead");

		ModItems.mp_chip_1 = new ItemMissile().makeChip(0.1F).setUnlocalizedName("mp_c_1").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_c_1");
		ModItems.mp_chip_2 = new ItemMissile().makeChip(0.05F).setUnlocalizedName("mp_c_2").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_c_2");
		ModItems.mp_chip_3 = new ItemMissile().makeChip(0.01F).setUnlocalizedName("mp_c_3").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_c_3");
		ModItems.mp_chip_4 = new ItemMissile().makeChip(0.005F).setUnlocalizedName("mp_c_4").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_c_4");
		ModItems.mp_chip_5 = new ItemMissile().makeChip(0.0F).setUnlocalizedName("mp_c_5").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_c_5");

		ModItems.missile_skin_camo = new ItemCustomLore().setUnlocalizedName("missile_skin_camo").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_skin_camo");
		ModItems.missile_skin_desert = new ItemCustomLore().setUnlocalizedName("missile_skin_desert").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_skin_desert");
		ModItems.missile_skin_flames = new ItemCustomLore().setUnlocalizedName("missile_skin_flames").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_skin_flames");
		ModItems.missile_skin_manly_pink = new ItemCustomLore().setUnlocalizedName("missile_skin_manly_pink")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab)
				.setTextureName(RefStrings.MODID + ":missile_skin_manly_pink");
		ModItems.missile_skin_orange_insulation = new ItemCustomLore()
				.setUnlocalizedName("missile_skin_orange_insulation").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab)
				.setTextureName(RefStrings.MODID + ":missile_skin_orange_insulation");
		ModItems.missile_skin_sleek = new ItemCustomLore().setUnlocalizedName("missile_skin_sleek").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_skin_sleek");
		ModItems.missile_skin_soviet_glory = new ItemCustomLore().setUnlocalizedName("missile_skin_soviet_glory")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab)
				.setTextureName(RefStrings.MODID + ":missile_skin_soviet_glory");
		ModItems.missile_skin_soviet_stank = new ItemCustomLore().setUnlocalizedName("missile_skin_soviet_stank")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab)
				.setTextureName(RefStrings.MODID + ":missile_skin_soviet_stank");
		ModItems.missile_skin_metal = new ItemCustomLore().setUnlocalizedName("missile_skin_metal").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_skin_metal");

		ModItems.ammo_12gauge = new ItemAmmo(Ammo12Gauge.class).setUnlocalizedName("ammo_12gauge");
		ModItems.ammo_20gauge = new ItemAmmo(Ammo20Gauge.class).setUnlocalizedName("ammo_20gauge");
		ModItems.ammo_4gauge = new ItemAmmo(Ammo4Gauge.class).setUnlocalizedName("ammo_4gauge");
		ModItems.ammo_5mm = new ItemAmmo(Ammo5mm.class).setUnlocalizedName("ammo_5mm");
		ModItems.ammo_9mm = new ItemAmmo(Ammo9mm.class).setUnlocalizedName("ammo_9mm");
		ModItems.ammo_45 = new ItemAmmo(Ammo45ACP.class).setUnlocalizedName("ammo_45");
		ModItems.ammo_556 = new ItemAmmo(Ammo556mm.class).setUnlocalizedName("ammo_556");
		ModItems.ammo_762 = new ItemAmmo(Ammo762NATO.class).setUnlocalizedName("ammo_762");
		ModItems.ammo_50ae = new ItemAmmo(Ammo50AE.class).setUnlocalizedName("ammo_50ae");
		ModItems.ammo_50bmg = new ItemAmmo(Ammo50BMG.class).setUnlocalizedName("ammo_50bmg");
		ModItems.ammo_75bolt = new ItemAmmo(Ammo75Bolt.class).setUnlocalizedName("ammo_75bolt");
		ModItems.ammo_357 = new ItemAmmo(Ammo357Magnum.class).setUnlocalizedName("ammo_357");
		ModItems.ammo_44 = new ItemAmmo(Ammo44Magnum.class).setUnlocalizedName("ammo_44");
		ModItems.ammo_22lr = new ItemAmmo(Ammo22LR.class).setUnlocalizedName("ammo_22lr");
		ModItems.ammo_rocket = new ItemAmmo(AmmoRocket.class).setUnlocalizedName("ammo_rocket");
		ModItems.ammo_grenade = new ItemAmmo(AmmoGrenade.class).setUnlocalizedName("ammo_grenade");
		ModItems.ammo_shell = new ItemAmmo(Ammo240Shell.class).setUnlocalizedName("ammo_shell");
		ModItems.ammo_dgk = new ItemCustomLore().setUnlocalizedName("ammo_dgk").setCreativeTab(MainRegistry.weaponTab);
		ModItems.ammo_nuke = new ItemAmmo(AmmoFatman.class).setUnlocalizedName("ammo_nuke");
		ModItems.ammo_fuel = new ItemAmmo(AmmoFlamethrower.class).setUnlocalizedName("ammo_fuel");
		ModItems.ammo_fireext = new ItemAmmo(AmmoFireExt.class).setUnlocalizedName("ammo_fireext");
		ModItems.ammo_cell = new ItemCustomLore().setCreativeTab(MainRegistry.weaponTab).setUnlocalizedName("ammo_cell")
				.setMaxStackSize(16);
		ModItems.ammo_coilgun = new ItemAmmo(AmmoCoilgun.class).setUnlocalizedName("ammo_coilgun");
		ModItems.ammo_dart = (ItemEnumMulti) new ItemAmmo(AmmoDart.class).setUnlocalizedName("ammo_dart")
				.setMaxStackSize(16);
		ModItems.ammo_stinger_rocket = new ItemAmmo(AmmoStinger.class).setUnlocalizedName("ammo_stinger_rocket");
		ModItems.ammo_luna_sniper = new ItemAmmo(AmmoLunaticSniper.class).setUnlocalizedName("ammo_luna_sniper");
		ModItems.ammo_misc = new ItemAmmo(AmmoMisc.class).setUnlocalizedName("ammo_misc");
		ModItems.ammo_folly = new ItemCustomLore().setUnlocalizedName("ammo_folly");
		ModItems.ammo_folly_nuclear = new ItemCustomLore().setUnlocalizedName("ammo_folly_nuclear");
		ModItems.ammo_folly_du = new ItemCustomLore().setUnlocalizedName("ammo_folly_du");
		ModItems.ammo_arty = new ItemAmmoArty().setUnlocalizedName("ammo_arty");
		ModItems.ammo_himars = new ItemAmmoHIMARS().setUnlocalizedName("ammo_himars");

		ModItems.gun_rpg = new ItemGunBase(GunRocketFactory.getGustavConfig()).setUnlocalizedName("gun_rpg")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_rpg");
		ModItems.gun_karl = new ItemGunBase(GunRocketFactory.getKarlConfig()).setUnlocalizedName("gun_karl")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_karl");
		ModItems.gun_panzerschreck = new ItemGunBase(GunRocketFactory.getPanzConfig())
				.setUnlocalizedName("gun_panzerschreck").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_panzerschreck");
		ModItems.gun_quadro = new ItemGunBase(GunRocketFactory.getQuadroConfig()).setUnlocalizedName("gun_quadro")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_quadro");
		ModItems.gun_hk69 = new ItemGunBase(GunGrenadeFactory.getHK69Config()).setUnlocalizedName("gun_hk69")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_hk69");
		ModItems.gun_congolake = new ItemGunCongo(GunGrenadeFactory.getCongoConfig())
				.setUnlocalizedName("gun_congolake").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_congolake");
		ModItems.gun_stinger = new ItemGunBase(GunRocketHomingFactory.getStingerConfig())
				.setUnlocalizedName("gun_stinger").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_stinger");
		ModItems.gun_skystinger = new ItemGunBase(GunRocketHomingFactory.getSkyStingerConfig())
				.setUnlocalizedName("gun_skystinger").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_skystinger");
		ModItems.gun_revolver = new ItemGunBase(Gun357MagnumFactory.getRevolverConfig())
				.setUnlocalizedName("gun_revolver").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_revolver");
		ModItems.gun_revolver_saturnite = new ItemGunBase(Gun357MagnumFactory.getRevolverSaturniteConfig())
				.setUnlocalizedName("gun_revolver_saturnite").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_revolver_saturnite");
		ModItems.gun_revolver_gold = new ItemGunBase(Gun357MagnumFactory.getRevolverGoldConfig())
				.setUnlocalizedName("gun_revolver_gold").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_revolver_gold");
		ModItems.gun_revolver_schrabidium = new ItemGunBase(Gun357MagnumFactory.getRevolverSchrabidiumConfig())
				.setUnlocalizedName("gun_revolver_schrabidium").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_revolver_schrabidium");
		ModItems.gun_revolver_cursed = new ItemGunBase(Gun357MagnumFactory.getRevolverCursedConfig())
				.setUnlocalizedName("gun_revolver_cursed").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_revolver_cursed");
		ModItems.gun_revolver_nightmare = new ItemGunBase(Gun357MagnumFactory.getRevolverNightmareConfig())
				.setUnlocalizedName("gun_revolver_nightmare").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_revolver_nightmare");
		ModItems.gun_revolver_nightmare2 = new ItemGunBase(Gun357MagnumFactory.getRevolverNightmare2Config())
				.setUnlocalizedName("gun_revolver_nightmare2").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_revolver_nightmare2");
		ModItems.gun_revolver_pip = new ItemGunBase(Gun44MagnumFactory.getMacintoshConfig())
				.setUnlocalizedName("gun_revolver_pip").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_revolver_pip");
		ModItems.gun_revolver_nopip = new ItemGunBase(Gun44MagnumFactory.getNovacConfig())
				.setUnlocalizedName("gun_revolver_nopip").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_revolver_nopip");
		ModItems.gun_revolver_blackjack = new ItemGunBase(Gun44MagnumFactory.getBlackjackConfig())
				.setUnlocalizedName("gun_revolver_blackjack").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_revolver_blackjack");
		ModItems.gun_revolver_silver = new ItemGunBase(Gun44MagnumFactory.getSilverConfig())
				.setUnlocalizedName("gun_revolver_silver").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_revolver_silver");
		ModItems.gun_revolver_red = new ItemGunBase(Gun44MagnumFactory.getRedConfig())
				.setUnlocalizedName("gun_revolver_red").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_revolver_red");
		ModItems.gun_deagle = new ItemGunBase(Gun50AEFactory.getDeagleConfig()).setUnlocalizedName("gun_deagle")
				.setFull3D().setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_deagle");
		ModItems.gun_bio_revolver = new ItemGunBio(Gun357MagnumFactory.getRevolverBioConfig())
				.setUnlocalizedName("gun_bio_revolver").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_bio_revolver");
		ModItems.gun_flechette = new ItemGunBase(Gun556mmFactory.getSPIWConfig(), Gun556mmFactory.getGLauncherConfig())
				.setUnlocalizedName("gun_flechette").setFull3D().setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_flechette");
		ModItems.gun_ar15 = new ItemGunBase(Gun50BMGFactory.getAR15Config(), Gun50BMGFactory.getAR15BurstConfig())
				.setUnlocalizedName("gun_ar15").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_ar15");
		ModItems.gun_calamity = new ItemGunBase(Gun762mmFactory.getCalamityConfig()).setUnlocalizedName("gun_calamity")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_calamity");
		ModItems.gun_minigun = new ItemGunLacunae(Gun5mmFactory.get53Config()).setUnlocalizedName("gun_minigun")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_minigun");
		ModItems.gun_avenger = new ItemGunLacunae(Gun5mmFactory.get57Config()).setUnlocalizedName("gun_avenger")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_avenger");
		ModItems.gun_lacunae = new ItemGunLacunae(Gun5mmFactory.getLacunaeConfig()).setUnlocalizedName("gun_lacunae")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_lacunae");
		ModItems.gun_folly = new GunFolly().setUnlocalizedName("gun_folly").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_folly");
		ModItems.gun_fatman = new ItemGunBase(GunFatmanFactory.getFatmanConfig()).setUnlocalizedName("gun_fatman")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_fatman");
		ModItems.gun_proto = new ItemGunBase(GunFatmanFactory.getProtoConfig()).setUnlocalizedName("gun_proto")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_fatman");
		ModItems.gun_mirv = new ItemGunBase(GunFatmanFactory.getMIRVConfig()).setUnlocalizedName("gun_mirv")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_mirv");
		ModItems.gun_bf = new ItemGunBase(GunFatmanFactory.getBELConfig()).setUnlocalizedName("gun_bf")
				.setCreativeTab(null).setTextureName(RefStrings.MODID + ":gun_bf");
		ModItems.gun_chemthrower = new ItemGunChemthrower().setUnlocalizedName("gun_chemthrower")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_fatman");
		ModItems.gun_mp40 = new ItemGunBase(Gun9mmFactory.getMP40Config()).setUnlocalizedName("gun_mp40")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_mp40");
		ModItems.gun_thompson = new ItemGunBase(Gun45ACPFactory.getThompsonConfig()).setUnlocalizedName("gun_thompson")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_thompson");
		ModItems.gun_uzi = new ItemGunBase(Gun22LRFactory.getUziConfig()).setUnlocalizedName("gun_uzi")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_uzi");
		ModItems.gun_uzi_silencer = new ItemGunBase(Gun22LRFactory.getUziConfig().silenced())
				.setUnlocalizedName("gun_uzi_silencer").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_uzi_silencer");
		ModItems.gun_uzi_saturnite = new ItemGunBase(Gun22LRFactory.getSaturniteConfig())
				.setUnlocalizedName("gun_uzi_saturnite").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_uzi_saturnite");
		ModItems.gun_uzi_saturnite_silencer = new ItemGunBase(Gun22LRFactory.getSaturniteConfig().silenced())
				.setUnlocalizedName("gun_uzi_saturnite_silencer").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_uzi_saturnite_silencer");
		ModItems.gun_uboinik = new ItemGunBase(Gun12GaugeFactory.getUboinikConfig()).setUnlocalizedName("gun_uboinik")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_uboinik");
		ModItems.gun_remington = new ItemGunBase(Gun12GaugeFactory.getRemington870Config())
				.setUnlocalizedName("gun_remington").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_spas12");
		ModItems.gun_spas12 = new ItemGunBase(Gun12GaugeFactory.getSpas12Config(),
				Gun12GaugeFactory.getSpas12AltConfig()).setUnlocalizedName("gun_spas12")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_spas12");
		ModItems.gun_benelli = new ItemGunBase(Gun12GaugeFactory.getBenelliModConfig())
				.setUnlocalizedName("gun_benelli").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_spas12");
		ModItems.gun_supershotgun = new ItemGunShotty(Gun12GaugeFactory.getShottyConfig())
				.setUnlocalizedName("gun_supershotgun").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_uboinik");
		ModItems.gun_ks23 = new ItemGunBase(Gun4GaugeFactory.getKS23Config()).setUnlocalizedName("gun_ks23")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_uboinik");
		ModItems.gun_sauer = new ItemGunBase(Gun4GaugeFactory.getSauerConfig()).setUnlocalizedName("gun_sauer")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_uboinik");
		ModItems.gun_lever_action = new ItemGunBase(Gun20GaugeFactory.getMareConfig())
				.setUnlocalizedName("gun_lever_action").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_lever_action");
		ModItems.gun_lever_action_dark = new ItemGunBase(Gun20GaugeFactory.getMareDarkConfig())
				.setUnlocalizedName("gun_lever_action_dark").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_lever_action_dark");
		ModItems.gun_lever_action_sonata = new GunLeverActionS().setUnlocalizedName("gun_lever_action_sonata")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_lever_action_sonata");
		ModItems.gun_bolt_action = new ItemGunBase(Gun762mmFactory.getBoltConfig())
				.setUnlocalizedName("gun_bolt_action").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_bolt_action");
		ModItems.gun_bolt_action_green = new ItemGunBase(Gun762mmFactory.getBoltGreenConfig())
				.setUnlocalizedName("gun_bolt_action_green").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_bolt_action_green");
		ModItems.gun_bolt_action_saturnite = new ItemGunBase(Gun762mmFactory.getBoltSaturniteConfig())
				.setUnlocalizedName("gun_bolt_action_saturnite").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_bolt_action_saturnite");
		ModItems.gun_mymy = new ItemGunBase(GunDartFactory.getMymyConfig()).setUnlocalizedName("gun_mymy").setFull3D()
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_mymy");
		ModItems.gun_b92_ammo = new GunB92Cell().setUnlocalizedName("gun_b92_ammo").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_b92_ammo_alt");
		ModItems.gun_b92 = new GunB92().setUnlocalizedName("gun_b92").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_b92");
		ModItems.gun_b93 = new GunB93().setUnlocalizedName("gun_b93").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_b93");
		ModItems.gun_coilgun = new ItemCoilgun(GunEnergyFactory.getCoilgunConfig()).setUnlocalizedName("gun_coilgun")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_coilgun");
		ModItems.gun_xvl1456_ammo = new Item().setUnlocalizedName("gun_xvl1456_ammo")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_xvl1456_ammo");
		ModItems.gun_xvl1456 = new ItemGunGauss(GunGaussFactory.getXVLConfig(), GunGaussFactory.getChargedConfig())
				.setUnlocalizedName("gun_xvl1456").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_xvl1456");
		ModItems.gun_osipr_ammo = new Item().setUnlocalizedName("gun_osipr_ammo").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_osipr_ammo");
		ModItems.gun_osipr_ammo2 = new Item().setUnlocalizedName("gun_osipr_ammo2")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_osipr_ammo2");
		ModItems.gun_osipr = new ItemGunOSIPR(GunOSIPRFactory.getOSIPRConfig(), GunOSIPRFactory.getAltConfig())
				.setUnlocalizedName("gun_osipr").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_osipr");
		ModItems.gun_immolator_ammo = new Item().setUnlocalizedName("gun_immolator_ammo")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_immolator_ammo");
		ModItems.gun_immolator = new GunImmolator().setUnlocalizedName("gun_immolator")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_immolator");
		ModItems.gun_flamer = new ItemGunBase(GunEnergyFactory.getFlamerConfig()).setUnlocalizedName("gun_flamer")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_flamer");
		ModItems.gun_cryolator_ammo = new Item().setUnlocalizedName("gun_cryolator_ammo")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_cryolator_ammo");
		ModItems.gun_cryolator = new GunCryolator().setUnlocalizedName("gun_cryolator")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_cryolator");
		ModItems.gun_cryocannon = new ItemCryoCannon(GunEnergyFactory.getCryoCannonConfig())
				.setUnlocalizedName("gun_cryocannon").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_cryocannon");
		ModItems.gun_fireext = new ItemGunBase(GunEnergyFactory.getExtConfig()).setUnlocalizedName("gun_fireext")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_fireext");
		ModItems.gun_mp = new ItemGunBase(Gun556mmFactory.getEuphieConfig()).setUnlocalizedName("gun_mp")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_pm");
		ModItems.gun_bolter = new ItemGunBase(Gun75BoltFactory.getBolterConfig()).setUnlocalizedName("gun_bolter")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_bolter");
		ModItems.gun_bolter_digamma = new ItemGunBase(Gun75BoltFactory.getBolterConfig())
				.setUnlocalizedName("gun_bolter_digamma").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_bolter_digamma");
		ModItems.gun_zomg = new ItemGunBase(GunEnergyFactory.getZOMGConfig()).setUnlocalizedName("gun_zomg")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_zomg");
		ModItems.gun_revolver_inverted = new GunSuicide().setUnlocalizedName("gun_revolver_inverted")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_inverted");
		ModItems.gun_emp_ammo = new Item().setUnlocalizedName("gun_emp_ammo").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_emp_ammo");
		ModItems.gun_emp = new ItemGunBase(GunEnergyFactory.getEMPConfig()).setUnlocalizedName("gun_emp")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_emp");
		ModItems.gun_jack_ammo = new Item().setUnlocalizedName("gun_jack_ammo").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_jack_ammo");
		ModItems.gun_jack = new GunJack().setUnlocalizedName("gun_jack").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_jack");
		ModItems.gun_spark_ammo = new Item().setUnlocalizedName("gun_spark_ammo").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_spark_ammo");
		ModItems.gun_spark = new GunSpark().setUnlocalizedName("gun_spark").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_spark");
		ModItems.gun_hp_ammo = new Item().setUnlocalizedName("gun_hp_ammo").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_hp_ammo");
		ModItems.gun_hp = new GunHP().setUnlocalizedName("gun_hp").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_hp");
		ModItems.gun_euthanasia_ammo = new Item().setUnlocalizedName("gun_euthanasia_ammo")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_euthanasia_ammo");
		ModItems.gun_euthanasia = new GunEuthanasia().setUnlocalizedName("gun_euthanasia")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_euthanasia");
		ModItems.gun_dash_ammo = new Item().setUnlocalizedName("gun_dash_ammo").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_dash_ammo");
		ModItems.gun_dash = new GunDash().setUnlocalizedName("gun_dash").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_dash");
		ModItems.gun_twigun_ammo = new Item().setUnlocalizedName("gun_twigun_ammo")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_twigun_ammo");
		ModItems.gun_twigun = new GunEuthanasia().setUnlocalizedName("gun_twigun")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_twigun");
		ModItems.gun_defabricator_ammo = new Item().setUnlocalizedName("gun_defabricator_ammo")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_defabricator_ammo");
		ModItems.gun_defabricator = new GunDefabricator().setUnlocalizedName("gun_defabricator")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_defabricator");
		ModItems.gun_vortex = new ItemGunVortex(Gun556mmFactory.getEuphieConfig()).setUnlocalizedName("gun_vortex")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_vortex");
		ModItems.gun_super_shotgun = new ItemCustomLore().setUnlocalizedName("gun_super_shotgun").setMaxStackSize(1)
				.setFull3D().setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_super_shotgun");
		ModItems.gun_moist_nugget = new ItemNugget(3, false).setUnlocalizedName("gun_moist_nugget")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_moist_nugget");
		ModItems.gun_dampfmaschine = new GunDampfmaschine().setUnlocalizedName("gun_dampfmaschine")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_dampfmaschine");
		ModItems.gun_darter = new ItemGunDart(GunDartFactory.getDarterConfig()).setFull3D()
				.setUnlocalizedName("gun_darter").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_darter");
		ModItems.gun_detonator = new ItemGunDetonator(GunDetonatorFactory.getDetonatorConfig()).setFull3D()
				.setUnlocalizedName("gun_detonator").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_darter");
		ModItems.gun_glass_cannon = new ItemEnergyGunBase(GunPoweredFactory.getGlassCannonConfig()).setFull3D()
				.setUnlocalizedName("gun_glass_cannon").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_darter");
		ModItems.gun_m2 = new ItemGunBase(Gun50BMGFactory.getM2Config()).setFull3D().setUnlocalizedName("gun_m2")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_darter");
		ModItems.gun_lunatic_marksman = new ItemGunBase(Gun50BMGFactory.getLunaticMarksman()).setFull3D()
				.setUnlocalizedName("gun_lunatic_marksman").setCreativeTab(MainRegistry.weaponTab);
		ModItems.gun_uac_pistol = new ItemGunBase(Gun45ACPFactory.getUACPistolConfig(),
				Gun45ACPFactory.getUACPistolBurstConfig()).setFull3D().setUnlocalizedName("gun_uac_pistol")
				.setCreativeTab(MainRegistry.weaponTab);

		ToolMaterial matCrucible = EnumHelper.addToolMaterial("CRUCIBLE", 10, 3, 50.0F, 100.0F, 0);
		ModItems.crucible = new ItemCrucible(5000, 1F, matCrucible).setUnlocalizedName("crucible")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":crucible");

		ModItems.stick_dynamite = new ItemGrenade(3).setUnlocalizedName("stick_dynamite")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":stick_dynamite");
		ModItems.stick_dynamite_fishing = new ItemGrenadeFishing(3).setUnlocalizedName("stick_dynamite_fishing")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":stick_dynamite_fishing");
		ModItems.stick_tnt = new Item().setUnlocalizedName("stick_tnt").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":stick_tnt");
		ModItems.stick_semtex = new Item().setUnlocalizedName("stick_semtex").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":stick_semtex");
		ModItems.stick_c4 = new Item().setUnlocalizedName("stick_c4").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":stick_c4");

		ModItems.grenade_generic = new ItemGrenade(4).setUnlocalizedName("grenade_generic")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_generic");
		ModItems.grenade_strong = new ItemGrenade(5).setUnlocalizedName("grenade_strong")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_strong");
		ModItems.grenade_frag = new ItemGrenade(4).setUnlocalizedName("grenade_frag")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_frag_alt");
		ModItems.grenade_fire = new ItemGrenade(4).setUnlocalizedName("grenade_fire")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_fire_alt");
		ModItems.grenade_shrapnel = new ItemGrenade(4).setUnlocalizedName("grenade_shrapnel")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_shrapnel");
		ModItems.grenade_cluster = new ItemGrenade(5).setUnlocalizedName("grenade_cluster")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_cluster_alt");
		ModItems.grenade_flare = new ItemGrenade(0).setUnlocalizedName("grenade_flare")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_flare_alt");
		ModItems.grenade_electric = new ItemGrenade(5).setUnlocalizedName("grenade_electric")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_electric_alt");
		ModItems.grenade_poison = new ItemGrenade(4).setUnlocalizedName("grenade_poison")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_poison_alt");
		ModItems.grenade_gas = new ItemGrenade(4).setUnlocalizedName("grenade_gas")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_gas_alt");
		ModItems.grenade_pulse = new ItemGrenade(4).setUnlocalizedName("grenade_pulse")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_pulse");
		ModItems.grenade_plasma = new ItemGrenade(5).setUnlocalizedName("grenade_plasma")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_plasma_alt");
		ModItems.grenade_tau = new ItemGrenade(5).setUnlocalizedName("grenade_tau")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_tau_alt");
		ModItems.grenade_schrabidium = new ItemGrenade(7).setUnlocalizedName("grenade_schrabidium")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_schrabidium_alt");
		ModItems.grenade_lemon = new ItemGrenade(4).setUnlocalizedName("grenade_lemon")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_lemon");
		ModItems.grenade_gascan = new ItemGrenade(-1).setUnlocalizedName("grenade_gascan")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_gascan");
		ModItems.grenade_kyiv = new ItemGrenadeKyiv(-1).setUnlocalizedName("grenade_kyiv")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_kyiv");
		ModItems.grenade_mk2 = new ItemGrenade(5).setUnlocalizedName("grenade_mk2")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_mk2_alt");
		ModItems.grenade_aschrab = new ItemGrenade(-1).setUnlocalizedName("grenade_aschrab")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_aschrab");
		ModItems.grenade_nuke = new ItemGrenade(-1).setUnlocalizedName("grenade_nuke")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_nuke_alt");
		ModItems.grenade_nuclear = new ItemGrenade(7).setUnlocalizedName("grenade_nuclear")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_nuclear");
		ModItems.grenade_zomg = new ItemGrenade(7).setUnlocalizedName("grenade_zomg")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_zomg");
		ModItems.grenade_black_hole = new ItemGrenade(7).setUnlocalizedName("grenade_black_hole")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_black_hole");
		ModItems.grenade_cloud = new ItemGrenade(-1).setUnlocalizedName("grenade_cloud")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_cloud");
		ModItems.grenade_pink_cloud = new ItemGrenade(-1).setUnlocalizedName("grenade_pink_cloud").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":grenade_pink_cloud");
		ModItems.ullapool_caber = new WeaponSpecial(MainRegistry.tMatSteel).setUnlocalizedName("ullapool_caber")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ullapool_caber");

		ModItems.grenade_if_generic = new ItemGrenade(4).setUnlocalizedName("grenade_if_generic")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_if_generic");
		ModItems.grenade_if_he = new ItemGrenade(5).setUnlocalizedName("grenade_if_he")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_if_he");
		ModItems.grenade_if_bouncy = new ItemGrenade(4).setUnlocalizedName("grenade_if_bouncy")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_if_bouncy");
		ModItems.grenade_if_sticky = new ItemGrenade(4).setUnlocalizedName("grenade_if_sticky")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_if_sticky");
		ModItems.grenade_if_impact = new ItemGrenade(-1).setUnlocalizedName("grenade_if_impact")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_if_impact");
		ModItems.grenade_if_incendiary = new ItemGrenade(4).setUnlocalizedName("grenade_if_incendiary")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_if_incendiary");
		ModItems.grenade_if_toxic = new ItemGrenade(4).setUnlocalizedName("grenade_if_toxic")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_if_toxic");
		ModItems.grenade_if_concussion = new ItemGrenade(4).setUnlocalizedName("grenade_if_concussion")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_if_concussion");
		ModItems.grenade_if_brimstone = new ItemGrenade(5).setUnlocalizedName("grenade_if_brimstone")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_if_brimstone");
		ModItems.grenade_if_mystery = new ItemGrenade(5).setUnlocalizedName("grenade_if_mystery")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_if_mystery");
		ModItems.grenade_if_spark = new ItemGrenade(7).setUnlocalizedName("grenade_if_spark")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_if_spark");
		ModItems.grenade_if_hopwire = new ItemGrenade(7).setUnlocalizedName("grenade_if_hopwire")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_if_hopwire");
		ModItems.grenade_if_null = new ItemGrenade(7).setUnlocalizedName("grenade_if_null")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_if_null");

		ModItems.grenade_smart = new ItemGrenade(-1).setUnlocalizedName("grenade_smart")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_smart");
		ModItems.grenade_mirv = new ItemGrenade(1).setUnlocalizedName("grenade_mirv")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_mirv");
		ModItems.grenade_breach = new ItemGrenade(-1).setUnlocalizedName("grenade_breach")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_breach");
		ModItems.grenade_burst = new ItemGrenade(1).setUnlocalizedName("grenade_burst")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_burst");
		ModItems.nuclear_waste_pearl = new ItemGrenade(-1).setUnlocalizedName("nuclear_waste_pearl")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":nuclear_waste_pearl");

		ModItems.weaponized_starblaster_cell = new WeaponizedCell().setUnlocalizedName("weaponized_starblaster_cell")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":gun_b92_ammo_weaponized");

		ModItems.bomb_waffle = new ItemWaffle(20, false).setUnlocalizedName("bomb_waffle")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":bomb_waffle");
		ModItems.schnitzel_vegan = new ItemSchnitzelVegan(0, true).setUnlocalizedName("schnitzel_vegan")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":schnitzel_vegan");
		ModItems.cotton_candy = new ItemCottonCandy(5, false).setUnlocalizedName("cotton_candy")
				.setCreativeTab(MainRegistry.consumableTab).setFull3D()
				.setTextureName(RefStrings.MODID + ":cotton_candy");
		ModItems.apple_lead = new ItemAppleSchrabidium(5, 0, false).setUnlocalizedName("apple_lead")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":apple_lead");
		ModItems.apple_schrabidium = new ItemAppleSchrabidium(20, 100, false).setUnlocalizedName("apple_schrabidium")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":apple_schrabidium");
		ModItems.tem_flakes = new ItemTemFlakes(0, 0, false).setUnlocalizedName("tem_flakes")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":tem_flakes");
		ModItems.glowing_stew = new ItemSoup(6).setUnlocalizedName("glowing_stew")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":glowing_stew");
		ModItems.balefire_scrambled = new ItemSoup(6).setUnlocalizedName("balefire_scrambled")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":balefire_scrambled");
		ModItems.balefire_and_ham = new ItemSoup(6).setUnlocalizedName("balefire_and_ham")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":balefire_and_ham");
		ModItems.lemon = new ItemLemon(3, 5, false).setUnlocalizedName("lemon")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":lemon");
		ModItems.definitelyfood = new ItemLemon(2, 5, false).setUnlocalizedName("definitelyfood")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":definitelyfood");
		ModItems.med_ipecac = new ItemLemon(0, 0, false).setUnlocalizedName("med_ipecac")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":med_ipecac_new");
		ModItems.med_ptsd = new ItemLemon(0, 0, false).setUnlocalizedName("med_ptsd")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":med_ptsd_new");
		ModItems.med_schizophrenia = new ItemLemon(0, 0, false).setUnlocalizedName("med_schizophrenia")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":med_schizophrenia_new");
		ModItems.loops = new ItemLemon(4, 5, false).setUnlocalizedName("loops")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":loops");
		ModItems.loop_stew = new ItemLemon(10, 10, false).setUnlocalizedName("loop_stew").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":loop_stew");
		ModItems.spongebob_macaroni = new ItemLemon(5, 5, false).setUnlocalizedName("spongebob_macaroni")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":spongebob_macaroni");
		ModItems.fooditem = new ItemLemon(2, 5, false).setUnlocalizedName("fooditem")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":fooditem");
		ModItems.twinkie = new ItemLemon(3, 5, false).setUnlocalizedName("twinkie")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":twinkie");
		ModItems.static_sandwich = new ItemLemon(6, 5, false).setUnlocalizedName("static_sandwich")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":static_sandwich");
		ModItems.pudding = new ItemLemon(6, 15, false).setUnlocalizedName("pudding")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":pudding");
		ModItems.canteen_13 = new ItemCanteen(1 * 60).setUnlocalizedName("canteen_13")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canteen_13");
		ModItems.canteen_vodka = new ItemCanteen(3 * 60).setUnlocalizedName("canteen_vodka")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canteen_vodka");
		ModItems.canteen_fab = new ItemCanteen(2 * 60).setUnlocalizedName("canteen_fab")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canteen_fab");
		ModItems.pancake = new ItemPancake(20, 20, false).setUnlocalizedName("pancake")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":pancake");
		ModItems.nugget = new ItemLemon(200, 200, false).setUnlocalizedName("nugget")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":nugget");
		ModItems.peas = new ItemPeas().setUnlocalizedName("peas").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":peas");
		ModItems.marshmallow = new ItemMarshmallow().setUnlocalizedName("marshmallow").setMaxStackSize(1).setFull3D()
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":marshmallow");
		ModItems.cheese = new ItemLemon(5, 10, false).setUnlocalizedName("cheese")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":cheese");
		ModItems.quesadilla = new ItemLemon(8, 10, false).setUnlocalizedName("cheese_quesadilla")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":quesadilla");
		ModItems.mucho_mango = new ItemMuchoMango(10).setUnlocalizedName("mucho_mango")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":mucho_mango");
		ModItems.glyphid_meat = new ItemLemon(3, 3, true).setUnlocalizedName("glyphid_meat")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":glyphid_meat");
		ModItems.glyphid_meat_grilled = new ItemLemon(8, 8, true).setPotionEffect(Potion.damageBoost.id, 180, 1, 1F)
				.setUnlocalizedName("glyphid_meat_grilled").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":glyphid_meat_grilled");
		ModItems.egg_glyphid = new Item().setUnlocalizedName("egg_glyphid").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":egg_glyphid");

		ModItems.defuser = new ItemTooling(ToolType.DEFUSER, 100).setUnlocalizedName("defuser").setMaxStackSize(1)
				.setFull3D().setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":defuser");
		ModItems.reacher = new Item().setUnlocalizedName("reacher").setMaxStackSize(1).setFull3D()
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":reacher");
		ModItems.bismuth_tool = new ItemAmatExtractor().setUnlocalizedName("bismuth_tool").setMaxStackSize(1)
				.setFull3D().setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":bismuth_tool");
		ModItems.meltdown_tool = new ItemDyatlov().setUnlocalizedName("meltdown_tool").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":meltdown_tool");

		ModItems.flame_pony = new ItemCustomLore().setUnlocalizedName("flame_pony")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":flame_pony");
		ModItems.flame_conspiracy = new ItemCustomLore().setUnlocalizedName("flame_conspiracy")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":flame_conspiracy");
		ModItems.flame_politics = new ItemCustomLore().setUnlocalizedName("flame_politics")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":flame_politics");
		ModItems.flame_opinion = new ItemCustomLore().setUnlocalizedName("flame_opinion")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":flame_opinion");

		// gadget_explosive = new
		// Item().setUnlocalizedName("gadget_explosive").setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID
		// + ":gadget_explosive");
		ModItems.early_explosive_lenses = new ItemCustomLore().setUnlocalizedName("early_explosive_lenses")
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":gadget_explosive8");
		ModItems.explosive_lenses = new ItemCustomLore().setUnlocalizedName("explosive_lenses")
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":man_explosive8");
		ModItems.gadget_wireing = new Item().setUnlocalizedName("gadget_wireing").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":gadget_wireing");
		ModItems.gadget_core = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("gadget_core")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab)
				.setTextureName(RefStrings.MODID + ":gadget_core");

		ModItems.boy_igniter = new Item().setUnlocalizedName("boy_igniter").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":boy_igniter");
		ModItems.boy_propellant = new Item().setUnlocalizedName("boy_propellant").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":boy_propellant");
		ModItems.boy_bullet = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("boy_bullet")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab)
				.setTextureName(RefStrings.MODID + ":boy_bullet");
		ModItems.boy_target = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("boy_target")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab)
				.setTextureName(RefStrings.MODID + ":boy_target");
		ModItems.boy_shielding = new Item().setUnlocalizedName("boy_shielding").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":boy_shielding");

		// man_explosive = new
		// Item().setUnlocalizedName("man_explosive").setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID
		// + ":man_explosive");
		ModItems.man_igniter = new Item().setUnlocalizedName("man_igniter").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":man_igniter");
		ModItems.man_core = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("man_core")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":man_core");

		ModItems.mike_core = new Item().setUnlocalizedName("mike_core").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":mike_core");
		ModItems.mike_deut = new Item().setUnlocalizedName("mike_deut").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setContainerItem(ModItems.tank_steel)
				.setTextureName(RefStrings.MODID + ":mike_deut");
		ModItems.mike_cooling_unit = new Item().setUnlocalizedName("mike_cooling_unit").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":mike_cooling_unit");

		ModItems.tsar_core = new Item().setUnlocalizedName("tsar_core").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":tsar_core");

		ModItems.fleija_igniter = new ItemFleija().setUnlocalizedName("fleija_igniter").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":fleija_igniter");
		ModItems.fleija_propellant = new ItemFleija().setUnlocalizedName("fleija_propellant").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":fleija_propellant");
		ModItems.fleija_core = new ItemFleija().setUnlocalizedName("fleija_core").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":fleija_core");

		ModItems.solinium_igniter = new ItemSolinium().setUnlocalizedName("solinium_igniter").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":solinium_igniter");
		ModItems.solinium_propellant = new ItemSolinium().setUnlocalizedName("solinium_propellant").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":solinium_propellant");
		ModItems.solinium_core = new ItemSolinium().setUnlocalizedName("solinium_core").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":solinium_core");

		ModItems.n2_charge = new ItemN2().setUnlocalizedName("n2_charge").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":n2_charge");

		ModItems.egg_balefire_shard = new Item().setUnlocalizedName("egg_balefire_shard").setMaxStackSize(16)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":egg_balefire_shard");
		ModItems.egg_balefire = new Item().setUnlocalizedName("egg_balefire").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":egg_balefire");

		ModItems.custom_tnt = new ItemCustomLore().setUnlocalizedName("custom_tnt").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":custom_tnt");
		ModItems.custom_nuke = new ItemCustomLore().setUnlocalizedName("custom_nuke").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":custom_nuke");
		ModItems.custom_hydro = new ItemCustomLore().setUnlocalizedName("custom_hydro").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":custom_hydro");
		ModItems.custom_amat = new ItemCustomLore().setUnlocalizedName("custom_amat").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":custom_amat");
		ModItems.custom_dirty = new ItemCustomLore().setUnlocalizedName("custom_dirty").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":custom_dirty");
		ModItems.custom_schrab = new ItemCustomLore().setUnlocalizedName("custom_schrab").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":custom_schrab");
		ModItems.custom_fall = new ItemCustomLore().setUnlocalizedName("custom_fall").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":custom_fall");

		ModItems.battery_generic = new ItemBattery(5000, 100, 100).setUnlocalizedName("battery_generic")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":battery_generic_new");
		ModItems.battery_advanced = new ItemBattery(20000, 500, 500).setUnlocalizedName("battery_advanced")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":battery_advanced_new");
		ModItems.battery_lithium = new ItemBattery(250000, 1000, 1000).setUnlocalizedName("battery_lithium")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":battery_lithium");
		ModItems.battery_schrabidium = new ItemBattery(1000000, 5000, 5000).setUnlocalizedName("battery_schrabidium")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":battery_schrabidium_new");
		ModItems.battery_spark = new ItemBattery(100000000, 2000000, 2000000).setUnlocalizedName("battery_spark")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":battery_spark");
		ModItems.battery_trixite = new ItemBattery(5000000, 40000, 200000).setUnlocalizedName("battery_trixite")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":battery_trixite");
		ModItems.battery_creative = new Item().setUnlocalizedName("battery_creative").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_creative_new");

		ModItems.battery_red_cell = new ItemBattery(15000, 100, 100).setUnlocalizedName("battery_red_cell")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":battery_red_cell");
		ModItems.battery_red_cell_6 = new ItemBattery(15000 * 6, 100, 100).setUnlocalizedName("battery_red_cell_6")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":battery_red_cell_6");
		ModItems.battery_red_cell_24 = new ItemBattery(15000 * 24, 100, 100).setUnlocalizedName("battery_red_cell_24")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":battery_red_cell_24");
		ModItems.battery_advanced_cell = new ItemBattery(60000, 500, 500).setUnlocalizedName("battery_advanced_cell")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":battery_advanced_cell");
		ModItems.battery_advanced_cell_4 = new ItemBattery(60000 * 4, 500, 500)
				.setUnlocalizedName("battery_advanced_cell_4").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_advanced_cell_4");
		ModItems.battery_advanced_cell_12 = new ItemBattery(60000 * 12, 500, 500)
				.setUnlocalizedName("battery_advanced_cell_12").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_advanced_cell_12");
		ModItems.battery_lithium_cell = new ItemBattery(750000, 1000, 1000).setUnlocalizedName("battery_lithium_cell")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":battery_lithium_cell");
		ModItems.battery_lithium_cell_3 = new ItemBattery(750000 * 3, 1000, 1000)
				.setUnlocalizedName("battery_lithium_cell_3").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":battery_lithium_cell_3");
		ModItems.battery_lithium_cell_6 = new ItemBattery(750000 * 6, 1000, 1000)
				.setUnlocalizedName("battery_lithium_cell_6").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":battery_lithium_cell_6");
		ModItems.battery_schrabidium_cell = new ItemBattery(3000000, 5000, 5000)
				.setUnlocalizedName("battery_schrabidium_cell").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_schrabidium_cell");
		ModItems.battery_schrabidium_cell_2 = new ItemBattery(3000000 * 2, 5000, 5000)
				.setUnlocalizedName("battery_schrabidium_cell_2").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":battery_schrabidium_cell_2");
		ModItems.battery_schrabidium_cell_4 = new ItemBattery(3000000 * 4, 5000, 5000)
				.setUnlocalizedName("battery_schrabidium_cell_4").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":battery_schrabidium_cell_4");
		ModItems.battery_spark_cell_6 = new ItemBattery(100000000L * 6L, 2000000, 2000000)
				.setUnlocalizedName("battery_spark_cell_6").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":battery_spark_cell_6");
		ModItems.battery_spark_cell_25 = new ItemBattery(100000000L * 25L, 2000000, 2000000)
				.setUnlocalizedName("battery_spark_cell_25").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":battery_spark_cell_25");
		ModItems.battery_spark_cell_100 = new ItemBattery(100000000L * 100L, 2000000, 2000000)
				.setUnlocalizedName("battery_spark_cell_100").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":battery_spark_cell_100");
		ModItems.battery_spark_cell_1000 = new ItemBattery(100000000L * 1000L, 20000000, 20000000)
				.setUnlocalizedName("battery_spark_cell_1000").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_spark_cell_1000");
		ModItems.battery_spark_cell_2500 = new ItemBattery(100000000L * 2500L, 20000000, 20000000)
				.setUnlocalizedName("battery_spark_cell_2500").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_spark_cell_2500");
		ModItems.battery_spark_cell_10000 = new ItemBattery(100000000L * 10000L, 200000000, 200000000)
				.setUnlocalizedName("battery_spark_cell_10000").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_spark_cell_10000");
		ModItems.battery_spark_cell_power = new ItemBattery(100000000L * 1000000L, 200000000, 200000000)
				.setUnlocalizedName("battery_spark_cell_power").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_spark_cell_power");
		ModItems.cube_power = new ItemBattery(1000000000000000000L, 1000000000000000L, 1000000000000000L)
				.setUnlocalizedName("cube_power").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":cube_power");

		ModItems.battery_sc_uranium = new ItemSelfcharger(5).setUnlocalizedName("battery_sc_uranium").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_sc_uranium");
		ModItems.battery_sc_technetium = new ItemSelfcharger(25).setUnlocalizedName("battery_sc_technetium")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":battery_sc_technetium");
		ModItems.battery_sc_plutonium = new ItemSelfcharger(100).setUnlocalizedName("battery_sc_plutonium")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":battery_sc_plutonium");
		ModItems.battery_sc_polonium = new ItemSelfcharger(500).setUnlocalizedName("battery_sc_polonium")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":battery_sc_polonium");
		ModItems.battery_sc_gold = new ItemSelfcharger(2500).setUnlocalizedName("battery_sc_gold").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_sc_gold");
		ModItems.battery_sc_lead = new ItemSelfcharger(5000).setUnlocalizedName("battery_sc_lead").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_sc_lead");
		ModItems.battery_sc_americium = new ItemSelfcharger(10000).setUnlocalizedName("battery_sc_americium")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":battery_sc_americium");

		ModItems.battery_potato = new ItemBattery(1000, 0, 100).setUnlocalizedName("battery_potato").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_potato");
		ModItems.battery_potatos = new ItemPotatos(500000, 0, 100).setUnlocalizedName("battery_potatos")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":battery_potatos");
		ModItems.battery_su = new ItemBattery(50000, 0, 1000).setUnlocalizedName("battery_su").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_su");
		ModItems.battery_su_l = new ItemBattery(150000, 0, 1000).setUnlocalizedName("battery_su_l").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_su_l");
		ModItems.battery_steam = new ItemBattery(60000, 300, 6000).setUnlocalizedName("battery_steam")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":battery_steam");
		ModItems.battery_steam_large = new ItemBattery(100000, 500, 10000).setUnlocalizedName("battery_steam_large")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":battery_steam_large");
		ModItems.hev_battery = new ItemFusionCore(150000).setUnlocalizedName("hev_battery").setMaxStackSize(4)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":hev_battery");
		ModItems.fusion_core = new ItemFusionCore(2500000).setUnlocalizedName("fusion_core").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fusion_core");
		ModItems.fusion_core_infinite = new Item().setUnlocalizedName("fusion_core_infinite").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fusion_core_infinite");
		ModItems.energy_core = new ItemBattery(10000000, 0, 1000).setUnlocalizedName("energy_core").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":energy_core");
		ModItems.fuse = new ItemCustomLore().setUnlocalizedName("fuse").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":fuse");
		ModItems.redcoil_capacitor = new ItemCapacitor(10).setUnlocalizedName("redcoil_capacitor").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":redcoil_capacitor");
		ModItems.euphemium_capacitor = new ItemCustomLore().setRarity(EnumRarity.epic)
				.setUnlocalizedName("euphemium_capacitor").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":redcoil_capacitor_euphemium");
		ModItems.titanium_filter = new ItemTitaniumFilter(6 * 60 * 60 * 20).setUnlocalizedName("titanium_filter")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":titanium_filter");
		ModItems.screwdriver = new ItemTooling(ToolType.SCREWDRIVER, 100).setUnlocalizedName("screwdriver");
		ModItems.screwdriver_desh = new ItemTooling(ToolType.SCREWDRIVER, 0).setUnlocalizedName("screwdriver_desh");
		ModItems.hand_drill = new ItemTooling(ToolType.HAND_DRILL, 100).setUnlocalizedName("hand_drill");
		ModItems.hand_drill_desh = new ItemTooling(ToolType.HAND_DRILL, 0).setUnlocalizedName("hand_drill_desh");
		ModItems.wrench_archineer = new ItemToolingWeapon(ToolType.WRENCH, 1000, 12F)
				.setUnlocalizedName("wrench_archineer").setTextureName(RefStrings.MODID + ":wrench_archineer_hd");
		ModItems.chemistry_set = new ItemCraftingDegradation(100).setUnlocalizedName("chemistry_set");
		ModItems.chemistry_set_boron = new ItemCraftingDegradation(0).setUnlocalizedName("chemistry_set_boron");
		ModItems.blowtorch = new ItemBlowtorch().setUnlocalizedName("blowtorch");
		ModItems.acetylene_torch = new ItemBlowtorch().setUnlocalizedName("acetylene_torch");
		ModItems.boltgun = new ItemBoltgun().setUnlocalizedName("boltgun");
		ModItems.overfuse = new ItemCustomLore().setUnlocalizedName("overfuse").setMaxStackSize(1).setFull3D()
				.setTextureName(RefStrings.MODID + ":overfuse");
		ModItems.arc_electrode = new ItemCustomLore().setUnlocalizedName("arc_electrode").setMaxDamage(250)
				.setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setFull3D().setTextureName(RefStrings.MODID + ":arc_electrode");
		ModItems.arc_electrode_burnt = new Item().setUnlocalizedName("arc_electrode_burnt").setMaxStackSize(1)
				.setFull3D().setTextureName(RefStrings.MODID + ":arc_electrode_burnt");
		ModItems.arc_electrode_desh = new ItemCustomLore().setUnlocalizedName("arc_electrode_desh").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setFull3D()
				.setTextureName(RefStrings.MODID + ":arc_electrode_desh");

		ModItems.ams_focus_blank = new Item().setUnlocalizedName("ams_focus_blank").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":ams_focus_blank");
		ModItems.ams_focus_limiter = new ItemCustomLore().setUnlocalizedName("ams_focus_limiter").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":ams_focus_limiter");
		ModItems.ams_focus_booster = new ItemCustomLore().setUnlocalizedName("ams_focus_booster").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":ams_focus_booster");
		ModItems.ams_muzzle = new ItemCustomLore().setUnlocalizedName("ams_muzzle").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":ams_muzzle");
		ModItems.ams_lens = new ItemLens(60 * 60 * 60 * 20 * 100).setUnlocalizedName("ams_lens").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":ams_lens");
		ModItems.ams_core_sing = new ItemAMSCore(1000000000L, 200, 10).setUnlocalizedName("ams_core_sing")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":ams_core_sing");
		ModItems.ams_core_wormhole = new ItemAMSCore(1500000000L, 200, 15).setUnlocalizedName("ams_core_wormhole")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":ams_core_wormhole");
		ModItems.ams_core_eyeofharmony = new ItemAMSCore(2500000000L, 300, 10)
				.setUnlocalizedName("ams_core_eyeofharmony").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":ams_core_eyeofharmony");
		ModItems.ams_core_thingy = new ItemAMSCore(5000000000L, 250, 5).setUnlocalizedName("ams_core_thingy")
				.setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":ams_core_thingy");

		ModItems.fusion_shield_tungsten = new ItemFusionShield(60 * 60 * 60 * 5, 3500)
				.setUnlocalizedName("fusion_shield_tungsten").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":fusion_shield_tungsten");
		ModItems.fusion_shield_desh = new ItemFusionShield(60 * 60 * 60 * 10, 4500)
				.setUnlocalizedName("fusion_shield_desh").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":fusion_shield_desh");
		ModItems.fusion_shield_chlorophyte = new ItemFusionShield(60 * 60 * 60 * 15, 9000)
				.setUnlocalizedName("fusion_shield_chlorophyte").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":fusion_shield_chlorophyte");
		ModItems.fusion_shield_vaporwave = new ItemFusionShield(60 * 60 * 60 * 10, 1916169)
				.setUnlocalizedName("fusion_shield_vaporwave").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fusion_shield_vaporwave");

		ModItems.upgrade_template = new ItemCustomLore().setUnlocalizedName("upgrade_template")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":upgrade_template");
		ModItems.upgrade_speed_1 = new ItemMachineUpgrade(UpgradeType.SPEED, 1).setUnlocalizedName("upgrade_speed_1")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_speed_1");
		ModItems.upgrade_speed_2 = new ItemMachineUpgrade(UpgradeType.SPEED, 2).setUnlocalizedName("upgrade_speed_2")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_speed_2");
		ModItems.upgrade_speed_3 = new ItemMachineUpgrade(UpgradeType.SPEED, 3).setUnlocalizedName("upgrade_speed_3")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_speed_3");
		ModItems.upgrade_effect_1 = new ItemMachineUpgrade(UpgradeType.EFFECT, 1).setUnlocalizedName("upgrade_effect_1")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_effect_1");
		ModItems.upgrade_effect_2 = new ItemMachineUpgrade(UpgradeType.EFFECT, 2).setUnlocalizedName("upgrade_effect_2")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_effect_2");
		ModItems.upgrade_effect_3 = new ItemMachineUpgrade(UpgradeType.EFFECT, 3).setUnlocalizedName("upgrade_effect_3")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_effect_3");
		ModItems.upgrade_power_1 = new ItemMachineUpgrade(UpgradeType.POWER, 1).setUnlocalizedName("upgrade_power_1")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_power_1");
		ModItems.upgrade_power_2 = new ItemMachineUpgrade(UpgradeType.POWER, 2).setUnlocalizedName("upgrade_power_2")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_power_2");
		ModItems.upgrade_power_3 = new ItemMachineUpgrade(UpgradeType.POWER, 3).setUnlocalizedName("upgrade_power_3")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_power_3");
		ModItems.upgrade_fortune_1 = new ItemMachineUpgrade(UpgradeType.FORTUNE, 1)
				.setUnlocalizedName("upgrade_fortune_1").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":upgrade_fortune_1");
		ModItems.upgrade_fortune_2 = new ItemMachineUpgrade(UpgradeType.FORTUNE, 2)
				.setUnlocalizedName("upgrade_fortune_2").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":upgrade_fortune_2");
		ModItems.upgrade_fortune_3 = new ItemMachineUpgrade(UpgradeType.FORTUNE, 3)
				.setUnlocalizedName("upgrade_fortune_3").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":upgrade_fortune_3");
		ModItems.upgrade_afterburn_1 = new ItemMachineUpgrade(UpgradeType.AFTERBURN, 1)
				.setUnlocalizedName("upgrade_afterburn_1").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":upgrade_afterburn_1");
		ModItems.upgrade_afterburn_2 = new ItemMachineUpgrade(UpgradeType.AFTERBURN, 2)
				.setUnlocalizedName("upgrade_afterburn_2").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":upgrade_afterburn_2");
		ModItems.upgrade_afterburn_3 = new ItemMachineUpgrade(UpgradeType.AFTERBURN, 3)
				.setUnlocalizedName("upgrade_afterburn_3").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":upgrade_afterburn_3");
		ModItems.upgrade_overdrive_1 = new ItemMachineUpgrade(UpgradeType.OVERDRIVE, 1)
				.setUnlocalizedName("upgrade_overdrive_1").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":upgrade_overdrive_1");
		ModItems.upgrade_overdrive_2 = new ItemMachineUpgrade(UpgradeType.OVERDRIVE, 2)
				.setUnlocalizedName("upgrade_overdrive_2").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":upgrade_overdrive_2");
		ModItems.upgrade_overdrive_3 = new ItemMachineUpgrade(UpgradeType.OVERDRIVE, 3)
				.setUnlocalizedName("upgrade_overdrive_3").setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":upgrade_overdrive_3");
		ModItems.upgrade_radius = new ItemMachineUpgrade().setUnlocalizedName("upgrade_radius").setMaxStackSize(16)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_radius");
		ModItems.upgrade_health = new ItemMachineUpgrade().setUnlocalizedName("upgrade_health").setMaxStackSize(16)
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_health");
		ModItems.upgrade_smelter = new ItemMachineUpgrade().setUnlocalizedName("upgrade_smelter")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_smelter");
		ModItems.upgrade_shredder = new ItemMachineUpgrade().setUnlocalizedName("upgrade_shredder")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_shredder");
		ModItems.upgrade_centrifuge = new ItemMachineUpgrade().setUnlocalizedName("upgrade_centrifuge")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_centrifuge");
		ModItems.upgrade_crystallizer = new ItemMachineUpgrade().setUnlocalizedName("upgrade_crystallizer")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_crystallizer");
		ModItems.upgrade_nullifier = new ItemMachineUpgrade().setUnlocalizedName("upgrade_nullifier")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_nullifier");
		ModItems.upgrade_screm = new ItemMachineUpgrade().setUnlocalizedName("upgrade_screm")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_screm");
		ModItems.upgrade_gc_speed = new ItemMachineUpgrade().setUnlocalizedName("upgrade_gc_speed")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_gc_speed");
		ModItems.upgrade_5g = new ItemMachineUpgrade().setUnlocalizedName("upgrade_5g")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_5g");
		ModItems.upgrade_stack = new ItemMetaUpgrade(3).setUnlocalizedName("upgrade_stack")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_stack");
		ModItems.upgrade_ejector = new ItemMetaUpgrade(3).setUnlocalizedName("upgrade_ejector")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_ejector");

		ModItems.wand = new ItemWand().setUnlocalizedName("wand_k").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setFull3D().setTextureName(RefStrings.MODID + ":wand");
		ModItems.wand_s = new ItemWandS().setUnlocalizedName("wand_s").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setFull3D().setTextureName(RefStrings.MODID + ":wand_s");
		ModItems.wand_d = new ItemWandD().setUnlocalizedName("wand_d").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setFull3D().setTextureName(RefStrings.MODID + ":wand_d");

		ModItems.structure_single = new ItemStructureSingle().setUnlocalizedName("structure_single").setMaxStackSize(1)
				.setCreativeTab(null).setFull3D().setTextureName(RefStrings.MODID + ":structure_single");
		ModItems.structure_solid = new ItemStructureSolid().setUnlocalizedName("structure_solid").setMaxStackSize(1)
				.setCreativeTab(null).setFull3D().setTextureName(RefStrings.MODID + ":structure_solid");
		ModItems.structure_pattern = new ItemStructurePattern().setUnlocalizedName("structure_pattern")
				.setMaxStackSize(1).setCreativeTab(null).setFull3D()
				.setTextureName(RefStrings.MODID + ":structure_pattern");
		ModItems.structure_randomized = new ItemStructureRandomized().setUnlocalizedName("structure_randomized")
				.setMaxStackSize(1).setCreativeTab(null).setFull3D()
				.setTextureName(RefStrings.MODID + ":structure_randomized");
		ModItems.structure_randomly = new ItemStructureRandomly().setUnlocalizedName("structure_randomly")
				.setMaxStackSize(1).setCreativeTab(null).setFull3D()
				.setTextureName(RefStrings.MODID + ":structure_randomly");
		ModItems.structure_custommachine = new ItemCMStructure().setUnlocalizedName("structure_custommachine")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setFull3D()
				.setTextureName(RefStrings.MODID + ":structure_custommachine");

		ModItems.rod_of_discord = new ItemDiscord().setUnlocalizedName("rod_of_discord").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setFull3D()
				.setTextureName(RefStrings.MODID + ":rod_of_discord");

		ModItems.nuke_starter_kit = new ItemStarterKit().setUnlocalizedName("nuke_starter_kit").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":nuke_starter_kit");
		ModItems.nuke_advanced_kit = new ItemStarterKit().setUnlocalizedName("nuke_advanced_kit").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":nuke_advanced_kit");
		ModItems.nuke_commercially_kit = new ItemStarterKit().setUnlocalizedName("nuke_commercially_kit")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":nuke_commercially_kit");
		ModItems.nuke_electric_kit = new ItemStarterKit().setUnlocalizedName("nuke_electric_kit").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":nuke_electric_kit");
		ModItems.gadget_kit = new ItemStarterKit().setUnlocalizedName("gadget_kit").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":gadget_kit");
		ModItems.boy_kit = new ItemStarterKit().setUnlocalizedName("boy_kit").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":boy_kit");
		ModItems.man_kit = new ItemStarterKit().setUnlocalizedName("man_kit").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":man_kit");
		ModItems.mike_kit = new ItemStarterKit().setUnlocalizedName("mike_kit").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":mike_kit");
		ModItems.tsar_kit = new ItemStarterKit().setUnlocalizedName("tsar_kit").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":tsar_kit");
		ModItems.multi_kit = new ItemStarterKit().setUnlocalizedName("multi_kit").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":multi_kit");
		ModItems.custom_kit = new ItemStarterKit().setUnlocalizedName("custom_kit").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":custom_kit");
		ModItems.grenade_kit = new ItemStarterKit().setUnlocalizedName("grenade_kit").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_kit");
		ModItems.fleija_kit = new ItemStarterKit().setUnlocalizedName("fleija_kit").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":fleija_kit");
		ModItems.prototype_kit = new ItemStarterKit().setUnlocalizedName("prototype_kit").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":prototype_kit");
		ModItems.missile_kit = new ItemStarterKit().setUnlocalizedName("missile_kit").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_kit");
		ModItems.t45_kit = new ItemStarterKit().setUnlocalizedName("t45_kit").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":t45_kit");
		ModItems.euphemium_kit = new ItemStarterKit().setUnlocalizedName("euphemium_kit").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":code");
		ModItems.solinium_kit = new ItemStarterKit().setUnlocalizedName("solinium_kit").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":solinium_kit");
		ModItems.hazmat_kit = new ItemStarterKit().setUnlocalizedName("hazmat_kit").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":hazmat_kit");
		ModItems.hazmat_red_kit = new ItemStarterKit().setUnlocalizedName("hazmat_red_kit").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":hazmat_red_kit");
		ModItems.hazmat_grey_kit = new ItemStarterKit().setUnlocalizedName("hazmat_grey_kit").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":hazmat_grey_kit");
		ModItems.kit_custom = new ItemKitCustom().setUnlocalizedName("kit_custom")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":kit");
		ModItems.kit_toolbox_empty = new Item().setUnlocalizedName("kit_toolbox_empty")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":kit_toolbox_empty");
		ModItems.kit_toolbox = new ItemKitNBT().setUnlocalizedName("kit_toolbox")
				.setCreativeTab(MainRegistry.consumableTab).setContainerItem(ModItems.kit_toolbox_empty)
				.setTextureName(RefStrings.MODID + ":kit_toolbox");

		ModItems.loot_10 = new ItemLootCrate().setUnlocalizedName("loot_10").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":loot_10");
		ModItems.loot_15 = new ItemLootCrate().setUnlocalizedName("loot_15").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":loot_15");
		ModItems.loot_misc = new ItemLootCrate().setUnlocalizedName("loot_misc").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":loot_misc");

		ModItems.clip_revolver_iron = new ItemClip(ModItems.ammo_357.stackFromEnum(20, Ammo357Magnum.IRON))
				.setUnlocalizedName("clip_revolver_iron").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":clip_revolver_iron");
		ModItems.clip_revolver = new ItemClip(ModItems.ammo_357.stackFromEnum(12, Ammo357Magnum.LEAD))
				.setUnlocalizedName("clip_revolver").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":clip_revolver");
		ModItems.clip_revolver_gold = new ItemClip(ModItems.ammo_357.stackFromEnum(6, Ammo357Magnum.GOLD))
				.setUnlocalizedName("clip_revolver_gold").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":clip_revolver_gold");
		ModItems.clip_revolver_lead = new ItemClip(ModItems.ammo_357.stackFromEnum(6, Ammo357Magnum.NUCLEAR))
				.setUnlocalizedName("clip_revolver_lead").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":clip_revolver_lead");
		ModItems.clip_revolver_schrabidium = new ItemClip(ModItems.ammo_357.stackFromEnum(2, Ammo357Magnum.SCHRABIDIUM))
				.setUnlocalizedName("clip_revolver_schrabidium").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":clip_revolver_schrabidium");
		ModItems.clip_revolver_cursed = new ItemClip(ModItems.ammo_357.stackFromEnum(17, Ammo357Magnum.STEEL))
				.setUnlocalizedName("clip_revolver_cursed").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":clip_revolver_cursed");
		ModItems.clip_revolver_nightmare = new ItemClip(ModItems.ammo_357.stackFromEnum(6, Ammo357Magnum.NIGHTMARE1))
				.setUnlocalizedName("clip_revolver_nightmare").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":clip_revolver_nightmare");
		ModItems.clip_revolver_nightmare2 = new ItemClip(ModItems.ammo_357.stackFromEnum(6, Ammo357Magnum.NIGHTMARE2))
				.setUnlocalizedName("clip_revolver_nightmare2").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":clip_revolver_nightmare2");
		ModItems.clip_revolver_pip = new ItemClip(ModItems.ammo_44.stackFromEnum(6, Ammo44Magnum.PIP))
				.setUnlocalizedName("clip_revolver_pip").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":clip_revolver_pip");
		ModItems.clip_revolver_nopip = new ItemClip(ModItems.ammo_44.stackFromEnum(6, Ammo44Magnum.STOCK))
				.setUnlocalizedName("clip_revolver_nopip").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":clip_revolver_nopip");
		ModItems.clip_rpg = new ItemClip(ModItems.ammo_rocket.stackFromEnum(4, AmmoRocket.STOCK))
				.setUnlocalizedName("clip_rpg").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":clip_rpg_alt");
		ModItems.clip_stinger = new ItemClip(ModItems.ammo_stinger_rocket.stackFromEnum(4, AmmoStinger.STOCK))
				.setUnlocalizedName("clip_stinger").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":clip_stinger");
		ModItems.clip_fatman = new ItemClip(ModItems.ammo_nuke.stackFromEnum(6, AmmoFatman.STOCK))
				.setUnlocalizedName("clip_fatman").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":clip_fatman");
		ModItems.clip_mirv = new ItemClip(ModItems.ammo_nuke.stackFromEnum(3, AmmoFatman.MIRV))
				.setUnlocalizedName("clip_mirv").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":clip_mirv");
		ModItems.clip_bf = new ItemClip(ModItems.ammo_nuke.stackFromEnum(2, AmmoFatman.BALEFIRE))
				.setUnlocalizedName("clip_bf").setCreativeTab(null).setTextureName(RefStrings.MODID + ":clip_bf");
		ModItems.clip_mp40 = new ItemClip(ModItems.ammo_9mm.stackFromEnum(32, Ammo9mm.STOCK))
				.setUnlocalizedName("clip_mp40").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":clip_mp40");
		ModItems.clip_uzi = new ItemClip(ModItems.ammo_22lr.stackFromEnum(32, Ammo22LR.STOCK))
				.setUnlocalizedName("clip_uzi").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":clip_uzi");
		ModItems.clip_uboinik = new ItemClip(ModItems.ammo_12gauge.stackFromEnum(12, Ammo12Gauge.STOCK))
				.setUnlocalizedName("clip_uboinik").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":clip_uboinik");
		ModItems.clip_lever_action = new ItemClip(ModItems.ammo_20gauge.stackFromEnum(12, Ammo20Gauge.STOCK))
				.setUnlocalizedName("clip_lever_action").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":clip_lever_action");
		ModItems.clip_bolt_action = new ItemClip(ModItems.ammo_20gauge.stackFromEnum(12, Ammo20Gauge.SLUG))
				.setUnlocalizedName("clip_bolt_action").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":clip_bolt_action");
		ModItems.clip_osipr = new ItemClip(new ItemStack(ModItems.gun_osipr_ammo, 3)).setUnlocalizedName("clip_osipr")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_osipr");
		ModItems.clip_immolator = new ItemClip(new ItemStack(ModItems.gun_immolator_ammo, 60))
				.setUnlocalizedName("clip_immolator").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":clip_immolator");
		ModItems.clip_cryolator = new ItemClip(new ItemStack(ModItems.gun_cryolator_ammo, 60))
				.setUnlocalizedName("clip_cryolator").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":clip_cryolator");
		ModItems.clip_mp = new ItemClip(ModItems.ammo_556.stackFromEnum(2, Ammo556mm.GOLD))
				.setUnlocalizedName("clip_mp").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":clip_mp");
		ModItems.clip_xvl1456 = new ItemClip(new ItemStack(ModItems.gun_xvl1456_ammo, 50))
				.setUnlocalizedName("clip_xvl1456").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":clip_xvl1456");
		ModItems.clip_emp = new ItemClip(new ItemStack(ModItems.gun_emp_ammo, 12)).setUnlocalizedName("clip_emp")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_emp");
		ModItems.clip_jack = new ItemClip(new ItemStack(ModItems.gun_jack_ammo, 12)).setUnlocalizedName("clip_jack")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_jack");
		ModItems.clip_spark = new ItemClip(new ItemStack(ModItems.gun_spark_ammo, 12)).setUnlocalizedName("clip_spark")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_spark");
		ModItems.clip_hp = new ItemClip(new ItemStack(ModItems.gun_hp_ammo, 24)).setUnlocalizedName("clip_hp")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_hp");
		ModItems.clip_euthanasia = new ItemClip(new ItemStack(ModItems.gun_euthanasia_ammo, 32))
				.setUnlocalizedName("clip_euthanasia").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":clip_euthanasia");
		ModItems.clip_defabricator = new ItemClip(new ItemStack(ModItems.gun_defabricator_ammo, 50))
				.setUnlocalizedName("clip_defabricator").setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":clip_defabricator");

		ModItems.ammo_container = new ItemAmmoContainer().setUnlocalizedName("ammo_container")
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_container");

		ModItems.ingot_euphemium = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("ingot_euphemium")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_euphemium");
		ModItems.nugget_euphemium = new ItemCustomLore().setRarity(EnumRarity.epic)
				.setUnlocalizedName("nugget_euphemium").setCreativeTab(MainRegistry.partsTab)
				.setTextureName(RefStrings.MODID + ":nugget_euphemium");
		ModItems.watch = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("watch").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":watch");
		ModItems.apple_euphemium = new ItemAppleEuphemium(20, 100, false).setUnlocalizedName("apple_euphemium")
				.setMaxStackSize(1).setTextureName(RefStrings.MODID + ":apple_euphemium");

		ModItems.igniter = new ItemCustomLore().setUnlocalizedName("igniter").setMaxStackSize(1).setFull3D()
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":trigger");
		ModItems.detonator = new ItemDetonator().setUnlocalizedName("detonator").setMaxStackSize(1).setFull3D()
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":detonator");
		ModItems.detonator_multi = new ItemMultiDetonator().setUnlocalizedName("detonator_multi").setMaxStackSize(1)
				.setFull3D().setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":detonator_multi");
		ModItems.detonator_laser = new ItemLaserDetonator().setUnlocalizedName("detonator_laser").setMaxStackSize(1)
				.setFull3D().setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":detonator_laser");
		ModItems.detonator_deadman = new ItemDrop().setUnlocalizedName("detonator_deadman").setMaxStackSize(1)
				.setFull3D().setCreativeTab(MainRegistry.nukeTab)
				.setTextureName(RefStrings.MODID + ":detonator_deadman");
		ModItems.detonator_de = new ItemDrop().setUnlocalizedName("detonator_de").setMaxStackSize(1).setFull3D()
				.setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":detonator_de");
		ModItems.crate_caller = new ItemCrateCaller().setUnlocalizedName("crate_caller").setMaxStackSize(1).setFull3D()
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":crate_caller");
		ModItems.bomb_caller = new ItemBombCaller().setUnlocalizedName("bomb_caller").setMaxStackSize(1).setFull3D()
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":bomb_caller");
		ModItems.meteor_remote = new ItemMeteorRemote().setUnlocalizedName("meteor_remote").setMaxStackSize(1)
				.setFull3D().setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":meteor_remote");
		ModItems.anchor_remote = new ItemAnchorRemote().setUnlocalizedName("anchor_remote").setMaxStackSize(1)
				.setFull3D().setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":anchor_remote");
		ModItems.spawn_chopper = new ItemChopper().setUnlocalizedName("chopper").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":chopper");
		ModItems.spawn_worm = new ItemChopper().setUnlocalizedName("spawn_worm").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":spawn_worm");
		ModItems.spawn_ufo = new ItemChopper().setUnlocalizedName("spawn_ufo").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":spawn_ufo");
		ModItems.spawn_duck = new ItemChopper().setUnlocalizedName("spawn_duck").setMaxStackSize(16)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":spawn_duck");
		ModItems.linker = new ItemTeleLink().setUnlocalizedName("linker").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":linker");
		ModItems.reactor_sensor = new ItemReactorSensor().setUnlocalizedName("reactor_sensor").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":reactor_sensor");
		ModItems.oil_detector = new ItemOilDetector().setUnlocalizedName("oil_detector").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":oil_detector");
		ModItems.turret_control = new ItemTurretControl().setUnlocalizedName("turret_control").setFull3D()
				.setMaxStackSize(1).setCreativeTab(MainRegistry.weaponTab)
				.setTextureName(RefStrings.MODID + ":turret_control");
		ModItems.turret_chip = new ItemTurretChip().setUnlocalizedName("turret_chip").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":turret_chip");
		// turret_biometry = new
		// ItemTurretBiometry().setUnlocalizedName("turret_biometry").setFull3D().setMaxStackSize(1).setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID
		// + ":rei_scanner");
		ModItems.dosimeter = new ItemDosimeter().setUnlocalizedName("dosimeter").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":dosimeter");
		ModItems.geiger_counter = new ItemGeigerCounter().setUnlocalizedName("geiger_counter").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":geiger_counter");
		ModItems.digamma_diagnostic = new ItemDigammaDiagnostic().setUnlocalizedName("digamma_diagnostic")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":digamma_diagnostic");
		ModItems.pollution_detector = new ItemPollutionDetector().setUnlocalizedName("pollution_detector")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":pollution_detector");
		ModItems.survey_scanner = new ItemSurveyScanner().setUnlocalizedName("survey_scanner").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":survey_scanner");
		ModItems.mirror_tool = new ItemMirrorTool().setUnlocalizedName("mirror_tool").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":mirror_tool");
		ModItems.rbmk_tool = new ItemRBMKTool().setUnlocalizedName("rbmk_tool").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":rbmk_tool");
		ModItems.coltan_tool = new ItemColtanCompass().setUnlocalizedName("coltan_tool").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":coltass");
		ModItems.power_net_tool = new ItemPowerNetTool().setUnlocalizedName("power_net_tool").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":power_net_tool");
		ModItems.analysis_tool = new ItemAnalysisTool().setUnlocalizedName("analysis_tool").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":analysis_tool");
		ModItems.coupling_tool = new ItemCouplingTool().setUnlocalizedName("coupling_tool").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":coupling_tool");
		ModItems.drone_linker = new ItemDroneLinker().setUnlocalizedName("drone_linker").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":drone_linker");

		ModItems.key = new ItemKey().setUnlocalizedName("key").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":key");
		ModItems.key_red = new ItemCustomLore().setUnlocalizedName("key_red").setMaxStackSize(1).setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":key_red");
		ModItems.key_kit = new ItemCounterfitKeys().setUnlocalizedName("key_kit").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":key_pair");
		ModItems.key_fake = new ItemKey().setUnlocalizedName("key_fake").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":key_gold");
		ModItems.pin = new ItemCustomLore().setUnlocalizedName("pin").setMaxStackSize(8)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":pin");
		ModItems.padlock_rusty = new ItemLock(1).setUnlocalizedName("padlock_rusty").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":padlock_rusty");
		ModItems.padlock = new ItemLock(0.1).setUnlocalizedName("padlock").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":padlock");
		ModItems.padlock_reinforced = new ItemLock(0.02).setUnlocalizedName("padlock_reinforced").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":padlock_reinforced");
		ModItems.padlock_unbreakable = new ItemLock(0).setUnlocalizedName("padlock_unbreakable").setMaxStackSize(1)
				.setCreativeTab(null).setTextureName(RefStrings.MODID + ":padlock_unbreakable");

		ModItems.mech_key = new ItemCustomLore().setUnlocalizedName("mech_key").setMaxStackSize(1).setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":mech_key");

		ModItems.template_folder = new ItemTemplateFolder().setUnlocalizedName("template_folder").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":template_folder");
		ModItems.journal_pip = new ItemTemplateFolder().setUnlocalizedName("journal_pip").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":journal_pip");
		ModItems.journal_bj = new ItemTemplateFolder().setUnlocalizedName("journal_bj").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":journal_bj");
		ModItems.journal_silver = new ItemTemplateFolder().setUnlocalizedName("journal_silver").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":journal_silver");
		ModItems.assembly_template = new ItemAssemblyTemplate().setUnlocalizedName("assembly_template")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab)
				.setTextureName(RefStrings.MODID + ":assembly_template");
		ModItems.chemistry_template = new ItemChemistryTemplate().setUnlocalizedName("chemistry_template")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab)
				.setTextureName(RefStrings.MODID + ":chemistry_template");
		ModItems.chemistry_icon = new ItemChemistryIcon().setUnlocalizedName("chemistry_icon").setMaxStackSize(1)
				.setCreativeTab(null);
		ModItems.crucible_template = new ItemCrucibleTemplate().setUnlocalizedName("crucible_template")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab)
				.setTextureName(RefStrings.MODID + ":crucible_template");
		ModItems.fluid_identifier = new ItemFluidIdentifier().setUnlocalizedName("fluid_identifier").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":fluid_identifier");
		ModItems.fluid_identifier_multi = new ItemFluidIDMulti().setUnlocalizedName("fluid_identifier_multi")
				.setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab)
				.setTextureName(RefStrings.MODID + ":fluid_identifier_multi");
		ModItems.fluid_icon = new ItemFluidIcon().setUnlocalizedName("fluid_icon").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":fluid_icon");
		ModItems.fluid_tank_empty = new Item().setUnlocalizedName("fluid_tank_empty")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fluid_tank");
		ModItems.fluid_tank_full = new ItemFluidTank().setUnlocalizedName("fluid_tank_full")
				.setContainerItem(ModItems.fluid_tank_empty).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":fluid_tank");
		ModItems.fluid_tank_lead_empty = new Item().setUnlocalizedName("fluid_tank_lead_empty")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fluid_tank_lead");
		ModItems.fluid_tank_lead_full = new ItemFluidTank().setUnlocalizedName("fluid_tank_lead_full")
				.setContainerItem(ModItems.fluid_tank_lead_empty).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":fluid_tank_lead");
		ModItems.fluid_barrel_full = new ItemFluidTank().setUnlocalizedName("fluid_barrel_full")
				.setContainerItem(ModItems.fluid_barrel_empty).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":fluid_barrel");
		ModItems.fluid_barrel_empty = new Item().setUnlocalizedName("fluid_barrel_empty")
				.setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fluid_barrel");
		ModItems.fluid_barrel_infinite = new ItemInfiniteFluid(null, 1_000_000_000)
				.setUnlocalizedName("fluid_barrel_infinite").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab)
				.setTextureName(RefStrings.MODID + ":fluid_barrel_infinite");
		ModItems.siren_track = new ItemCassette().setUnlocalizedName("siren_track").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":cassette");
		ModItems.fluid_duct = new ItemFluidDuct().setUnlocalizedName("fluid_duct")
				.setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":duct");

		ModItems.bobmazon_materials = new ItemCatalog().setUnlocalizedName("bobmazon_materials").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":bobmazon_materials");
		ModItems.bobmazon_machines = new ItemCatalog().setUnlocalizedName("bobmazon_machines").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":bobmazon_machines");
		ModItems.bobmazon_weapons = new ItemCatalog().setUnlocalizedName("bobmazon_weapons").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":bobmazon_weapons");
		ModItems.bobmazon_tools = new ItemCatalog().setUnlocalizedName("bobmazon_tools").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":bobmazon_tools");
		ModItems.bobmazon_hidden = new ItemCatalog().setUnlocalizedName("bobmazon_hidden").setMaxStackSize(1)
				.setCreativeTab(null).setTextureName(RefStrings.MODID + ":bobmazon_special");

		ModItems.euphemium_helmet = new ArmorEuphemium(MainRegistry.aMatEuph, 0).setUnlocalizedName("euphemium_helmet")
				.setMaxStackSize(1).setTextureName(RefStrings.MODID + ":euphemium_helmet");
		ModItems.euphemium_plate = new ArmorEuphemium(MainRegistry.aMatEuph, 1).setUnlocalizedName("euphemium_plate")
				.setMaxStackSize(1).setTextureName(RefStrings.MODID + ":euphemium_plate");
		ModItems.euphemium_legs = new ArmorEuphemium(MainRegistry.aMatEuph, 2).setUnlocalizedName("euphemium_legs")
				.setMaxStackSize(1).setTextureName(RefStrings.MODID + ":euphemium_legs");
		ModItems.euphemium_boots = new ArmorEuphemium(MainRegistry.aMatEuph, 3).setUnlocalizedName("euphemium_boots")
				.setMaxStackSize(1).setTextureName(RefStrings.MODID + ":euphemium_boots");

		ArmorMaterial aMatRags = EnumHelper.addArmorMaterial("HBM_RAGS", 150, new int[] { 1, 1, 1, 1 }, 0);
		aMatRags.customCraftingMaterial = ModItems.rag;

		ModItems.goggles = new ArmorModel(ArmorMaterial.IRON, 0).setUnlocalizedName("goggles").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":goggles");
		ModItems.ashglasses = new ArmorAshGlasses(ArmorMaterial.IRON, 0).setUnlocalizedName("ashglasses")
				.setMaxStackSize(1).setTextureName(RefStrings.MODID + ":ashglasses");
		ModItems.gas_mask = new ArmorGasMask().setUnlocalizedName("gas_mask").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":gas_mask");
		ModItems.gas_mask_m65 = new ArmorGasMask().setUnlocalizedName("gas_mask_m65").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":gas_mask_m65");
		ModItems.gas_mask_mono = new ArmorGasMask().setUnlocalizedName("gas_mask_mono").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":gas_mask_mono");
		ModItems.gas_mask_olde = new ArmorGasMask().setUnlocalizedName("gas_mask_olde").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":gas_mask_olde");
		ModItems.mask_rag = new ModArmor(aMatRags, 0).setUnlocalizedName("mask_rag").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":mask_rag");
		ModItems.mask_piss = new ModArmor(aMatRags, 0).setUnlocalizedName("mask_piss").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":mask_piss");
		ModItems.hat = new ArmorHat(MainRegistry.aMatAlloy, 0).setUnlocalizedName("nossy_hat").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":hat");
		ModItems.no9 = new ArmorNo9(MainRegistry.aMatSteel, 0).setUnlocalizedName("no9").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":no9");
		ModItems.beta = new ItemDrop().setUnlocalizedName("beta").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":beta");
		// oxy_mask = new ArmorModel(ArmorMaterial.IRON, 7,
		// 0).setUnlocalizedName("oxy_mask").setMaxStackSize(1).setTextureName(RefStrings.MODID
		// + ":oxy_mask");

		ModItems.schrabidium_helmet = new ArmorFSB(MainRegistry.aMatSchrab, 0,
				RefStrings.MODID + ":textures/armor/schrabidium_1.png").setCap(4F).setMod(0.1F)
				.addEffect(new PotionEffect(Potion.digSpeed.id, 20, 2))
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 2))
				.addEffect(new PotionEffect(Potion.jump.id, 20, 1))
				.addEffect(new PotionEffect(Potion.moveSpeed.id, 20, 2)).setFireproof(true)
				.setUnlocalizedName("schrabidium_helmet").setTextureName(RefStrings.MODID + ":schrabidium_helmet");
		ModItems.schrabidium_plate = new ArmorFSB(MainRegistry.aMatSchrab, 1,
				RefStrings.MODID + ":textures/armor/schrabidium_1.png")
				.cloneStats((ArmorFSB) ModItems.schrabidium_helmet).setUnlocalizedName("schrabidium_plate")
				.setTextureName(RefStrings.MODID + ":schrabidium_plate");
		ModItems.schrabidium_legs = new ArmorFSB(MainRegistry.aMatSchrab, 2,
				RefStrings.MODID + ":textures/armor/schrabidium_2.png")
				.cloneStats((ArmorFSB) ModItems.schrabidium_helmet).setCap(4F).setMod(0.1F)
				.setUnlocalizedName("schrabidium_legs").setTextureName(RefStrings.MODID + ":schrabidium_legs");
		ModItems.schrabidium_boots = new ArmorFSB(MainRegistry.aMatSchrab, 3,
				RefStrings.MODID + ":textures/armor/schrabidium_1.png")
				.cloneStats((ArmorFSB) ModItems.schrabidium_helmet).setCap(4F).setMod(0.1F)
				.setUnlocalizedName("schrabidium_boots").setTextureName(RefStrings.MODID + ":schrabidium_boots");
		ModItems.bismuth_helmet = new ArmorBismuth(MainRegistry.aMatBismuth, 0,
				RefStrings.MODID + ":textures/armor/starmetal_1.png").setCap(8F).setMod(0.3F).addResistance("fall", 0)
				.addEffect(new PotionEffect(Potion.jump.id, 20, 6))
				.addEffect(new PotionEffect(Potion.moveSpeed.id, 20, 6))
				.addEffect(new PotionEffect(Potion.regeneration.id, 20, 1))
				.addEffect(new PotionEffect(Potion.nightVision.id, 15 * 20, 0)).setDashCount(3)
				.setUnlocalizedName("bismuth_helmet").setTextureName(RefStrings.MODID + ":bismuth_helmet");
		ModItems.bismuth_plate = new ArmorBismuth(MainRegistry.aMatBismuth, 1,
				RefStrings.MODID + ":textures/armor/starmetal_2.png").cloneStats((ArmorFSB) ModItems.bismuth_helmet)
				.setCap(8F).setMod(0.3F).setUnlocalizedName("bismuth_plate")
				.setTextureName(RefStrings.MODID + ":bismuth_plate");
		ModItems.bismuth_legs = new ArmorBismuth(MainRegistry.aMatBismuth, 2,
				RefStrings.MODID + ":textures/armor/starmetal_1.png").cloneStats((ArmorFSB) ModItems.bismuth_helmet)
				.setCap(8F).setMod(0.3F).setUnlocalizedName("bismuth_legs")
				.setTextureName(RefStrings.MODID + ":bismuth_legs");
		ModItems.bismuth_boots = new ArmorBismuth(MainRegistry.aMatBismuth, 3,
				RefStrings.MODID + ":textures/armor/starmetal_1.png").cloneStats((ArmorFSB) ModItems.bismuth_helmet)
				.setCap(8F).setMod(0.3F).setUnlocalizedName("bismuth_boots")
				.setTextureName(RefStrings.MODID + ":bismuth_boots");
		ModItems.titanium_helmet = new ArmorFSB(MainRegistry.aMatTitan, 0,
				RefStrings.MODID + ":textures/armor/titanium_1.png").setMod(0.85F).setUnlocalizedName("titanium_helmet")
				.setMaxStackSize(1).setTextureName(RefStrings.MODID + ":titanium_helmet");
		ModItems.titanium_plate = new ArmorFSB(MainRegistry.aMatTitan, 1,
				RefStrings.MODID + ":textures/armor/titanium_1.png").cloneStats((ArmorFSB) ModItems.titanium_helmet)
				.setUnlocalizedName("titanium_plate").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":titanium_plate");
		ModItems.titanium_legs = new ArmorFSB(MainRegistry.aMatTitan, 2,
				RefStrings.MODID + ":textures/armor/titanium_2.png").cloneStats((ArmorFSB) ModItems.titanium_helmet)
				.setUnlocalizedName("titanium_legs").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":titanium_legs");
		ModItems.titanium_boots = new ArmorFSB(MainRegistry.aMatTitan, 3,
				RefStrings.MODID + ":textures/armor/titanium_1.png").cloneStats((ArmorFSB) ModItems.titanium_helmet)
				.setUnlocalizedName("titanium_boots").setTextureName(RefStrings.MODID + ":titanium_boots");
		ModItems.steel_helmet = new ArmorFSB(MainRegistry.aMatSteel, 0,
				RefStrings.MODID + ":textures/armor/steel_1.png").setMod(0.9F).setUnlocalizedName("steel_helmet")
				.setTextureName(RefStrings.MODID + ":steel_helmet");
		ModItems.steel_plate = new ArmorFSB(MainRegistry.aMatSteel, 1, RefStrings.MODID + ":textures/armor/steel_1.png")
				.cloneStats((ArmorFSB) ModItems.steel_helmet).setUnlocalizedName("steel_plate")
				.setTextureName(RefStrings.MODID + ":steel_plate");
		ModItems.steel_legs = new ArmorFSB(MainRegistry.aMatSteel, 2, RefStrings.MODID + ":textures/armor/steel_2.png")
				.cloneStats((ArmorFSB) ModItems.steel_helmet).setUnlocalizedName("steel_legs")
				.setTextureName(RefStrings.MODID + ":steel_legs");
		ModItems.steel_boots = new ArmorFSB(MainRegistry.aMatSteel, 3, RefStrings.MODID + ":textures/armor/steel_1.png")
				.cloneStats((ArmorFSB) ModItems.steel_helmet).setUnlocalizedName("steel_boots")
				.setTextureName(RefStrings.MODID + ":steel_boots");
		ModItems.alloy_helmet = new ArmorFSB(MainRegistry.aMatAlloy, 0,
				RefStrings.MODID + ":textures/armor/alloy_1.png").setMod(0.75F).setUnlocalizedName("alloy_helmet")
				.setTextureName(RefStrings.MODID + ":alloy_helmet");
		ModItems.alloy_plate = new ArmorFSB(MainRegistry.aMatAlloy, 1, RefStrings.MODID + ":textures/armor/alloy_1.png")
				.cloneStats((ArmorFSB) ModItems.alloy_helmet).setUnlocalizedName("alloy_plate")
				.setTextureName(RefStrings.MODID + ":alloy_plate");
		ModItems.alloy_legs = new ArmorFSB(MainRegistry.aMatAlloy, 2, RefStrings.MODID + ":textures/armor/alloy_2.png")
				.cloneStats((ArmorFSB) ModItems.alloy_helmet).setUnlocalizedName("alloy_legs")
				.setTextureName(RefStrings.MODID + ":alloy_legs");
		ModItems.alloy_boots = new ArmorFSB(MainRegistry.aMatAlloy, 3, RefStrings.MODID + ":textures/armor/alloy_1.png")
				.cloneStats((ArmorFSB) ModItems.alloy_helmet).setUnlocalizedName("alloy_boots")
				.setTextureName(RefStrings.MODID + ":alloy_boots");
		ModItems.cmb_helmet = new ArmorFSB(MainRegistry.aMatCMB, 0, RefStrings.MODID + ":textures/armor/cmb_1.png")
				.setCap(2F).setThreshold(2F).setMod(0.05F).addEffect(new PotionEffect(Potion.moveSpeed.id, 20, 2))
				.addEffect(new PotionEffect(Potion.digSpeed.id, 20, 2))
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 4)).setFireproof(true)
				.setUnlocalizedName("cmb_helmet").setTextureName(RefStrings.MODID + ":cmb_helmet");
		ModItems.cmb_plate = new ArmorFSB(MainRegistry.aMatCMB, 1, RefStrings.MODID + ":textures/armor/cmb_1.png")
				.cloneStats((ArmorFSB) ModItems.cmb_helmet).setUnlocalizedName("cmb_plate")
				.setTextureName(RefStrings.MODID + ":cmb_plate");
		ModItems.cmb_legs = new ArmorFSB(MainRegistry.aMatCMB, 2, RefStrings.MODID + ":textures/armor/cmb_2.png")
				.cloneStats((ArmorFSB) ModItems.cmb_helmet).setUnlocalizedName("cmb_legs")
				.setTextureName(RefStrings.MODID + ":cmb_legs");
		ModItems.cmb_boots = new ArmorFSB(MainRegistry.aMatCMB, 3, RefStrings.MODID + ":textures/armor/cmb_1.png")
				.cloneStats((ArmorFSB) ModItems.cmb_helmet).setUnlocalizedName("cmb_boots")
				.setTextureName(RefStrings.MODID + ":cmb_boots");
		ModItems.paa_plate = new ArmorFSB(MainRegistry.aMatPaa, 1, RefStrings.MODID + ":textures/armor/paa_1.png")
				.setCap(6F).setMod(0.3F).setNoHelmet(true).addEffect(new PotionEffect(Potion.digSpeed.id, 20, 0))
				.setUnlocalizedName("paa_plate").setTextureName(RefStrings.MODID + ":paa_plate");
		ModItems.paa_legs = new ArmorFSB(MainRegistry.aMatPaa, 2, RefStrings.MODID + ":textures/armor/paa_2.png")
				.cloneStats((ArmorFSB) ModItems.paa_plate).setUnlocalizedName("paa_legs")
				.setTextureName(RefStrings.MODID + ":paa_legs");
		ModItems.paa_boots = new ArmorFSB(MainRegistry.aMatPaa, 3, RefStrings.MODID + ":textures/armor/paa_1.png")
				.cloneStats((ArmorFSB) ModItems.paa_plate).setUnlocalizedName("paa_boots")
				.setTextureName(RefStrings.MODID + ":paa_boots");
		ModItems.asbestos_helmet = new ArmorFSB(MainRegistry.aMatAsbestos, 0,
				RefStrings.MODID + ":textures/armor/asbestos_1.png").setFireproof(true)
				.setOverlay(RefStrings.MODID + ":textures/misc/overlay_asbestos.png")
				.setUnlocalizedName("asbestos_helmet").setTextureName(RefStrings.MODID + ":asbestos_helmet");
		ModItems.asbestos_plate = new ArmorFSB(MainRegistry.aMatAsbestos, 1,
				RefStrings.MODID + ":textures/armor/asbestos_1.png").setFireproof(true)
				.setUnlocalizedName("asbestos_plate").setTextureName(RefStrings.MODID + ":asbestos_plate");
		ModItems.asbestos_legs = new ArmorFSB(MainRegistry.aMatAsbestos, 2,
				RefStrings.MODID + ":textures/armor/asbestos_2.png").setFireproof(true)
				.setUnlocalizedName("asbestos_legs").setTextureName(RefStrings.MODID + ":asbestos_legs");
		ModItems.asbestos_boots = new ArmorFSB(MainRegistry.aMatAsbestos, 3,
				RefStrings.MODID + ":textures/armor/asbestos_1.png").setFireproof(true)
				.setUnlocalizedName("asbestos_boots").setTextureName(RefStrings.MODID + ":asbestos_boots");
		ModItems.security_helmet = new ArmorFSB(MainRegistry.aMatSecurity, 0,
				RefStrings.MODID + ":textures/armor/security_1.png").setMod(0.65F).setProjectileProtection(0.5F)
				.setUnlocalizedName("security_helmet").setTextureName(RefStrings.MODID + ":security_helmet");
		ModItems.security_plate = new ArmorFSB(MainRegistry.aMatSecurity, 1,
				RefStrings.MODID + ":textures/armor/security_1.png").cloneStats((ArmorFSB) ModItems.security_helmet)
				.setUnlocalizedName("security_plate").setTextureName(RefStrings.MODID + ":security_plate");
		ModItems.security_legs = new ArmorFSB(MainRegistry.aMatSecurity, 2,
				RefStrings.MODID + ":textures/armor/security_2.png").cloneStats((ArmorFSB) ModItems.security_helmet)
				.setUnlocalizedName("security_legs").setTextureName(RefStrings.MODID + ":security_legs");
		ModItems.security_boots = new ArmorFSB(MainRegistry.aMatSecurity, 3,
				RefStrings.MODID + ":textures/armor/security_1.png").cloneStats((ArmorFSB) ModItems.security_helmet)
				.setUnlocalizedName("security_boots").setTextureName(RefStrings.MODID + ":security_boots");
		ModItems.cobalt_helmet = new ArmorFSB(MainRegistry.aMatCobalt, 0,
				RefStrings.MODID + ":textures/armor/cobalt_1.png").setMod(0.7F).setUnlocalizedName("cobalt_helmet")
				.setTextureName(RefStrings.MODID + ":cobalt_helmet");
		ModItems.cobalt_plate = new ArmorFSB(MainRegistry.aMatCobalt, 1,
				RefStrings.MODID + ":textures/armor/cobalt_1.png").cloneStats((ArmorFSB) ModItems.cobalt_helmet)
				.setUnlocalizedName("cobalt_plate").setTextureName(RefStrings.MODID + ":cobalt_plate");
		ModItems.cobalt_legs = new ArmorFSB(MainRegistry.aMatCobalt, 2,
				RefStrings.MODID + ":textures/armor/cobalt_2.png").cloneStats((ArmorFSB) ModItems.cobalt_helmet)
				.setUnlocalizedName("cobalt_legs").setTextureName(RefStrings.MODID + ":cobalt_legs");
		ModItems.cobalt_boots = new ArmorFSB(MainRegistry.aMatCobalt, 3,
				RefStrings.MODID + ":textures/armor/cobalt_1.png").cloneStats((ArmorFSB) ModItems.cobalt_helmet)
				.setUnlocalizedName("cobalt_boots").setTextureName(RefStrings.MODID + ":cobalt_boots");
		ModItems.starmetal_helmet = new ArmorFSB(MainRegistry.aMatStarmetal, 0,
				RefStrings.MODID + ":textures/armor/starmetal_1.png").setMod(0.5F).setCap(15F).setFireproof(true)
				.setUnlocalizedName("starmetal_helmet").setTextureName(RefStrings.MODID + ":starmetal_helmet");
		ModItems.starmetal_plate = new ArmorFSB(MainRegistry.aMatStarmetal, 1,
				RefStrings.MODID + ":textures/armor/starmetal_1.png").cloneStats((ArmorFSB) ModItems.starmetal_helmet)
				.setUnlocalizedName("starmetal_plate").setTextureName(RefStrings.MODID + ":starmetal_plate");
		ModItems.starmetal_legs = new ArmorFSB(MainRegistry.aMatStarmetal, 2,
				RefStrings.MODID + ":textures/armor/starmetal_2.png").cloneStats((ArmorFSB) ModItems.starmetal_helmet)
				.setUnlocalizedName("starmetal_legs").setTextureName(RefStrings.MODID + ":starmetal_legs");
		ModItems.starmetal_boots = new ArmorFSB(MainRegistry.aMatStarmetal, 3,
				RefStrings.MODID + ":textures/armor/starmetal_1.png").cloneStats((ArmorFSB) ModItems.starmetal_helmet)
				.setUnlocalizedName("starmetal_boots").setTextureName(RefStrings.MODID + ":starmetal_boots");

		ModItems.robes_helmet = new ArmorFSB(ArmorMaterial.CHAIN, 0, RefStrings.MODID + ":textures/armor/robes_1.png")
				.setThreshold(1.0F).setUnlocalizedName("robes_helmet")
				.setTextureName(RefStrings.MODID + ":robes_helmet");
		ModItems.robes_plate = new ArmorFSB(ArmorMaterial.CHAIN, 1, RefStrings.MODID + ":textures/armor/robes_1.png")
				.cloneStats((ArmorFSB) ModItems.robes_helmet).setUnlocalizedName("robes_plate")
				.setTextureName(RefStrings.MODID + ":robes_plate");
		ModItems.robes_legs = new ArmorFSB(ArmorMaterial.CHAIN, 2, RefStrings.MODID + ":textures/armor/robes_2.png")
				.cloneStats((ArmorFSB) ModItems.robes_helmet).setUnlocalizedName("robes_legs")
				.setTextureName(RefStrings.MODID + ":robes_legs");
		ModItems.robes_boots = new ArmorFSB(ArmorMaterial.CHAIN, 3, RefStrings.MODID + ":textures/armor/robes_1.png")
				.cloneStats((ArmorFSB) ModItems.robes_helmet).setUnlocalizedName("robes_boots")
				.setTextureName(RefStrings.MODID + ":robes_boots");

		ModItems.debug_wand = new ItemDebugStick().setUnlocalizedName("debug_stick").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":debug_stick");
		ModItems.initializeItem2();
	}

	public static void initializeItem2() {
		
		ArmorMaterial aMatZirconium = EnumHelper.addArmorMaterial("HBM_ZIRCONIUM", 1000, new int[] { 2, 5, 3, 1 }, 100);
		aMatZirconium.customCraftingMaterial = ModItems.ingot_zirconium;
		ModItems.zirconium_legs = new ArmorFSB(aMatZirconium, 2, RefStrings.MODID + ":textures/armor/zirconium_2.png")
				.setMod(0.0F).setUnlocalizedName("zirconium_legs").setTextureName(RefStrings.MODID + ":zirconium_legs");

		ArmorMaterial aMatDNT = EnumHelper.addArmorMaterial("HBM_DNT_LOLOLOL", 3, new int[] { 1, 1, 1, 1 }, 0);
		aMatDNT.customCraftingMaterial = ModItems.ingot_dineutronium;
		ModItems.dnt_helmet = new ArmorFSB(aMatDNT, 0, RefStrings.MODID + ":textures/armor/dnt_1.png").setMod(5F)
				.setUnlocalizedName("dnt_helmet").setTextureName(RefStrings.MODID + ":dnt_helmet");
		ModItems.dnt_plate = new ArmorFSB(aMatDNT, 1, RefStrings.MODID + ":textures/armor/dnt_1.png")
				.cloneStats((ArmorFSB) ModItems.dnt_helmet).setUnlocalizedName("dnt_plate")
				.setTextureName(RefStrings.MODID + ":dnt_plate");
		ModItems.dnt_legs = new ArmorFSB(aMatDNT, 2, RefStrings.MODID + ":textures/armor/dnt_2.png")
				.cloneStats((ArmorFSB) ModItems.dnt_helmet).setUnlocalizedName("dnt_legs")
				.setTextureName(RefStrings.MODID + ":dnt_legs");
		ModItems.dnt_boots = new ArmorFSB(aMatDNT, 3, RefStrings.MODID + ":textures/armor/dnt_1.png")
				.cloneStats((ArmorFSB) ModItems.dnt_helmet).setUnlocalizedName("dnt_boots")
				.setTextureName(RefStrings.MODID + ":dnt_boots");

		ArmorMaterial aMatT45 = EnumHelper.addArmorMaterial("HBM_T45", 150, new int[] { 3, 8, 6, 3 }, 0);
		aMatT45.customCraftingMaterial = ModItems.plate_armor_titanium;
		ModItems.t45_helmet = new ArmorT45(aMatT45, 0, 1000000, 10000, 1000, 5).setCap(10F).setMod(0.5F)
				.setFireproof(true).enableVATS(true).setHasGeigerSound(true).setHasHardLanding(true)
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 0)).setBlastProtection(0.5F)
				.addResistance("monoxide", 0F).addResistance("fall", 0).hides(EnumPlayerPart.HAT)
				.setUnlocalizedName("t45_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":t45_helmet");
		ModItems.t45_plate = new ArmorT45(aMatT45, 1, 1000000, 10000, 1000, 5)
				.cloneStats((ArmorFSB) ModItems.t45_helmet).setUnlocalizedName("t45_plate").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":t45_plate");
		ModItems.t45_legs = new ArmorT45(aMatT45, 2, 1000000, 10000, 1000, 5).cloneStats((ArmorFSB) ModItems.t45_helmet)
				.setUnlocalizedName("t45_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":t45_legs");
		ModItems.t45_boots = new ArmorT45(aMatT45, 3, 1000000, 10000, 1000, 5)
				.cloneStats((ArmorFSB) ModItems.t45_helmet).setUnlocalizedName("t45_boots").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":t45_boots");

		ArmorMaterial aMatDesh = EnumHelper.addArmorMaterial("HBM_DESH", 150, new int[] { 3, 8, 6, 3 }, 0);
		aMatDesh.customCraftingMaterial = ModItems.ingot_desh;
		ModItems.steamsuit_helmet = new ArmorDesh(aMatDesh, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png",
				Fluids.STEAM, 64_000, 500, 50, 1).setThreshold(5F).setMod(0.8F).setFireproof(true)
				.setHasHardLanding(true).addEffect(new PotionEffect(Potion.digSpeed.id, 20, 4)).setBlastProtection(0.5F)
				.addResistance("monoxide", 0F).addResistance("fall", 0).hides(EnumPlayerPart.HAT)
				.setUnlocalizedName("steamsuit_helmet").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":steamsuit_helmet");
		ModItems.steamsuit_plate = new ArmorDesh(aMatDesh, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png",
				Fluids.STEAM, 64_000, 500, 50, 1).cloneStats((ArmorFSB) ModItems.steamsuit_helmet)
				.setUnlocalizedName("steamsuit_plate").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":steamsuit_plate");
		ModItems.steamsuit_legs = new ArmorDesh(aMatDesh, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png",
				Fluids.STEAM, 64_000, 500, 50, 1).cloneStats((ArmorFSB) ModItems.steamsuit_helmet)
				.setUnlocalizedName("steamsuit_legs").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":steamsuit_legs");
		ModItems.steamsuit_boots = new ArmorDesh(aMatDesh, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png",
				Fluids.STEAM, 64_000, 500, 50, 1).cloneStats((ArmorFSB) ModItems.steamsuit_helmet)
				.setUnlocalizedName("steamsuit_boots").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":steamsuit_boots");

		ArmorMaterial aMatDiesel = EnumHelper.addArmorMaterial("HBM_BNUUY", 150, new int[] { 3, 8, 6, 3 }, 0);
		aMatDiesel.customCraftingMaterial = ModItems.plate_copper;
		ModItems.dieselsuit_helmet = new ArmorDiesel(aMatDiesel, 0,
				RefStrings.MODID + ":textures/armor/starmetal_1.png", Fluids.DIESEL, 64_000, 500, 50, 1)
				.setThreshold(2F).setMod(0.7F).addEffect(new PotionEffect(Potion.moveSpeed.id, 20, 2))
				.addEffect(new PotionEffect(Potion.jump.id, 20, 2)).enableThermalSight(true).enableVATS(true)
				.addResistance("fall", 0).setUnlocalizedName("dieselsuit_helmet").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":dieselsuit_helmet");
		ModItems.dieselsuit_plate = new ArmorDiesel(aMatDiesel, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png",
				Fluids.DIESEL, 64_000, 500, 50, 1).cloneStats((ArmorFSB) ModItems.dieselsuit_helmet)
				.setUnlocalizedName("dieselsuit_plate").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":dieselsuit_plate");
		ModItems.dieselsuit_legs = new ArmorDiesel(aMatDiesel, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png",
				Fluids.DIESEL, 64_000, 500, 50, 1).cloneStats((ArmorFSB) ModItems.dieselsuit_helmet)
				.setUnlocalizedName("dieselsuit_legs").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":dieselsuit_legs");
		ModItems.dieselsuit_boots = new ArmorDiesel(aMatDiesel, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png",
				Fluids.DIESEL, 64_000, 500, 50, 1).cloneStats((ArmorFSB) ModItems.dieselsuit_helmet)
				.setUnlocalizedName("dieselsuit_boots").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":dieselsuit_boots");

		ArmorMaterial aMatAJR = EnumHelper.addArmorMaterial("HBM_T45AJR", 150, new int[] { 3, 8, 6, 3 }, 100);
		aMatAJR.customCraftingMaterial = ModItems.plate_armor_ajr;
		ModItems.ajr_helmet = new ArmorAJR(aMatAJR, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000,
				10000, 2000, 25).setMod(0.25F).setCap(6.0F).setThreshold(4F).setFireproof(true).enableVATS(true)
				.setHasGeigerSound(true).setHasHardLanding(true).addEffect(new PotionEffect(Potion.jump.id, 20, 0))
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 0)).setBlastProtection(0.25F)
				.setStep("hbm:step.metal").setJump("hbm:step.iron_jump").setFall("hbm:step.iron_land")
				.addResistance("monoxide", 0F).addResistance("fall", 0).hides(EnumPlayerPart.HAT)
				.setUnlocalizedName("ajr_helmet").setTextureName(RefStrings.MODID + ":ajr_helmet");
		ModItems.ajr_plate = new ArmorAJR(aMatAJR, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000,
				10000, 2000, 25).cloneStats((ArmorFSB) ModItems.ajr_helmet).setUnlocalizedName("ajr_plate")
				.setTextureName(RefStrings.MODID + ":ajr_plate");
		ModItems.ajr_legs = new ArmorAJR(aMatAJR, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", 2500000,
				10000, 2000, 25).cloneStats((ArmorFSB) ModItems.ajr_helmet).setUnlocalizedName("ajr_legs")
				.setTextureName(RefStrings.MODID + ":ajr_legs");
		ModItems.ajr_boots = new ArmorAJR(aMatAJR, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000,
				10000, 2000, 25).cloneStats((ArmorFSB) ModItems.ajr_helmet).setUnlocalizedName("ajr_boots")
				.setTextureName(RefStrings.MODID + ":ajr_boots");

		ModItems.ajro_helmet = new ArmorAJRO(aMatAJR, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000,
				10000, 2000, 25).setMod(0.25F).setCap(6.0F).setThreshold(4F).setFireproof(true).enableVATS(true)
				.setHasGeigerSound(true).setHasHardLanding(true).addEffect(new PotionEffect(Potion.jump.id, 20, 0))
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 0)).setBlastProtection(0.25F)
				.setStep("hbm:step.metal").setJump("hbm:step.iron_jump").setFall("hbm:step.iron_land")
				.addResistance("monoxide", 0F).addResistance("fall", 0).hides(EnumPlayerPart.HAT)
				.setUnlocalizedName("ajro_helmet").setTextureName(RefStrings.MODID + ":ajro_helmet");
		ModItems.ajro_plate = new ArmorAJRO(aMatAJR, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000,
				10000, 2000, 25).cloneStats((ArmorFSB) ModItems.ajro_helmet).setUnlocalizedName("ajro_plate")
				.setTextureName(RefStrings.MODID + ":ajro_plate");
		ModItems.ajro_legs = new ArmorAJRO(aMatAJR, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", 2500000,
				10000, 2000, 25).cloneStats((ArmorFSB) ModItems.ajro_helmet).setUnlocalizedName("ajro_legs")
				.setTextureName(RefStrings.MODID + ":ajro_legs");
		ModItems.ajro_boots = new ArmorAJRO(aMatAJR, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000,
				10000, 2000, 25).cloneStats((ArmorFSB) ModItems.ajro_helmet).setUnlocalizedName("ajro_boots")
				.setTextureName(RefStrings.MODID + ":ajro_boots");

		ModItems.rpa_helmet = new ArmorRPA(aMatAJR, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000,
				10000, 2000, 25).setMod(0.1F).setCap(6.0F).setThreshold(20F).setFireproof(true).enableVATS(true)
				.setHasGeigerSound(true).setHasHardLanding(true)
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 3)).setBlastProtection(0.25F)
				.setProjectileProtection(0.25F).setProtectionLevel(1500).setStep("hbm:step.powered")
				.setJump("hbm:step.powered").setFall("hbm:step.powered").addResistance("fall", 0)
				.hides(EnumPlayerPart.HAT).setUnlocalizedName("rpa_helmet")
				.setTextureName(RefStrings.MODID + ":rpa_helmet");
		ModItems.rpa_plate = new ArmorRPA(aMatAJR, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000,
				10000, 2000, 25).cloneStats((ArmorFSB) ModItems.rpa_helmet).setUnlocalizedName("rpa_plate")
				.setTextureName(RefStrings.MODID + ":rpa_plate");
		ModItems.rpa_legs = new ArmorRPA(aMatAJR, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", 2500000,
				10000, 2000, 25).cloneStats((ArmorFSB) ModItems.rpa_helmet).setUnlocalizedName("rpa_legs")
				.setTextureName(RefStrings.MODID + ":rpa_legs");
		ModItems.rpa_boots = new ArmorRPA(aMatAJR, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000,
				10000, 2000, 25).cloneStats((ArmorFSB) ModItems.rpa_helmet).setUnlocalizedName("rpa_boots")
				.setTextureName(RefStrings.MODID + ":rpa_boots");

		ArmorMaterial aMatBJ = EnumHelper.addArmorMaterial("HBM_BLACKJACK", 150, new int[] { 3, 8, 6, 3 }, 100);
		aMatBJ.customCraftingMaterial = ModItems.plate_armor_lunar;
		ModItems.bj_helmet = new ArmorBJ(aMatBJ, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 10000000,
				10000, 1000, 100).setMod(0.25F).setCap(4.0F).setThreshold(4F).setFireproof(true).enableVATS(true)
				.enableThermalSight(true).setHasGeigerSound(true).setHasHardLanding(true)
				.addEffect(new PotionEffect(Potion.moveSpeed.id, 20, 1))
				.addEffect(new PotionEffect(Potion.jump.id, 20, 0))
				.addEffect(new PotionEffect(Potion.field_76443_y.id, 20, 0))
				.addEffect(new PotionEffect(HbmPotion.radx.id, 20, 0)).setBlastProtection(0.5F).setProtectionLevel(500F)
				.setStep("hbm:step.metal").setJump("hbm:step.iron_jump").setFall("hbm:step.iron_land")
				.addResistance("fall", 0).setUnlocalizedName("bj_helmet")
				.setTextureName(RefStrings.MODID + ":bj_helmet");
		ModItems.bj_plate = new ArmorBJ(aMatBJ, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 10000000,
				10000, 1000, 100).cloneStats((ArmorFSB) ModItems.bj_helmet).setUnlocalizedName("bj_plate")
				.setTextureName(RefStrings.MODID + ":bj_plate");
		ModItems.bj_plate_jetpack = new ArmorBJJetpack(aMatBJ, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png",
				10000000, 10000, 1000, 100).cloneStats((ArmorFSB) ModItems.bj_helmet)
				.setUnlocalizedName("bj_plate_jetpack").setTextureName(RefStrings.MODID + ":bj_plate_jetpack");
		ModItems.bj_legs = new ArmorBJ(aMatBJ, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", 10000000, 10000,
				1000, 100).cloneStats((ArmorFSB) ModItems.bj_helmet).setUnlocalizedName("bj_legs")
				.setTextureName(RefStrings.MODID + ":bj_legs");
		ModItems.bj_boots = new ArmorBJ(aMatBJ, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", 10000000,
				10000, 1000, 100).cloneStats((ArmorFSB) ModItems.bj_helmet).setUnlocalizedName("bj_boots")
				.setTextureName(RefStrings.MODID + ":bj_boots");

		ArmorMaterial aMatEnv = EnumHelper.addArmorMaterial("HBM_ENV", 150, new int[] { 3, 8, 6, 3 }, 100);
		aMatEnv.customCraftingMaterial = ModItems.plate_armor_hev;
		ModItems.envsuit_helmet = new ArmorEnvsuit(aMatEnv, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png",
				100_000, 1_000, 250, 0).setMod(0.5F).setThreshold(2.0F)
				.addEffect(new PotionEffect(Potion.moveSpeed.id, 20, 1))
				.addEffect(new PotionEffect(Potion.jump.id, 20, 0)).addResistance("fall", 0.25F)
				.addResistance("monoxide", 0F).addResistance("onFire", 0F).hides(EnumPlayerPart.HAT)
				.setUnlocalizedName("envsuit_helmet").setTextureName(RefStrings.MODID + ":envsuit_helmet");
		ModItems.envsuit_plate = new ArmorEnvsuit(aMatEnv, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png",
				100_000, 1_000, 250, 0).cloneStats((ArmorFSB) ModItems.envsuit_helmet)
				.setUnlocalizedName("envsuit_plate").setTextureName(RefStrings.MODID + ":envsuit_plate");
		ModItems.envsuit_legs = new ArmorEnvsuit(aMatEnv, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png",
				100_000, 1_000, 250, 0).cloneStats((ArmorFSB) ModItems.envsuit_helmet)
				.setUnlocalizedName("envsuit_legs").setTextureName(RefStrings.MODID + ":envsuit_legs");
		ModItems.envsuit_boots = new ArmorEnvsuit(aMatEnv, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png",
				100_000, 1_000, 250, 0).cloneStats((ArmorFSB) ModItems.envsuit_helmet)
				.setUnlocalizedName("envsuit_boots").setTextureName(RefStrings.MODID + ":envsuit_boots");

		ArmorMaterial aMatHEV = EnumHelper.addArmorMaterial("HBM_HEV", 150, new int[] { 3, 8, 6, 3 }, 100);
		aMatHEV.customCraftingMaterial = ModItems.plate_armor_hev;
		ModItems.hev_helmet = new ArmorHEV(aMatHEV, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 1000000,
				10000, 2500, 0).setMod(0.20F).setCap(4.0F).setThreshold(2.0F)
				.addEffect(new PotionEffect(Potion.moveSpeed.id, 20, 1))
				.addEffect(new PotionEffect(Potion.jump.id, 20, 0)).setBlastProtection(0.25F).setProtectionLevel(500F)
				.setHasGeigerSound(true).setHasCustomGeiger(true).addResistance("fall", 0.5F)
				.addResistance("monoxide", 0F).addResistance("onFire", 0F).hides(EnumPlayerPart.HAT)
				.setUnlocalizedName("hev_helmet").setTextureName(RefStrings.MODID + ":hev_helmet");
		ModItems.hev_plate = new ArmorHEV(aMatHEV, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 1000000,
				10000, 2500, 0).cloneStats((ArmorFSB) ModItems.hev_helmet).setUnlocalizedName("hev_plate")
				.setTextureName(RefStrings.MODID + ":hev_plate");
		ModItems.hev_legs = new ArmorHEV(aMatHEV, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", 1000000,
				10000, 2500, 0).cloneStats((ArmorFSB) ModItems.hev_helmet).setUnlocalizedName("hev_legs")
				.setTextureName(RefStrings.MODID + ":hev_legs");
		ModItems.hev_boots = new ArmorHEV(aMatHEV, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", 1000000,
				10000, 2500, 0).cloneStats((ArmorFSB) ModItems.hev_helmet).setUnlocalizedName("hev_boots")
				.setTextureName(RefStrings.MODID + ":hev_boots");

		ModItems.jackt = new ModArmor(MainRegistry.aMatSteel, 1).setUnlocalizedName("jackt")
				.setTextureName(RefStrings.MODID + ":jackt");
		ModItems.jackt2 = new ModArmor(MainRegistry.aMatSteel, 1).setUnlocalizedName("jackt2")
				.setTextureName(RefStrings.MODID + ":jackt2");

		ArmorMaterial aMatFau = EnumHelper.addArmorMaterial("HBM_DIGAMMA", 150, new int[] { 3, 8, 6, 3 }, 100);
		aMatFau.customCraftingMaterial = ModItems.plate_armor_fau;
		ModItems.fau_helmet = new ArmorDigamma(aMatFau, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png",
				10000000, 10000, 2500, 0).setMod(0.25F).setCap(4.0F).setThreshold(2.0F)
				.addEffect(new PotionEffect(Potion.jump.id, 20, 1)).setBlastProtection(0.05F).setMod(0.05F)
				.setHasGeigerSound(true).enableThermalSight(true).setHasHardLanding(true).setStep("hbm:step.metal")
				.setJump("hbm:step.iron_jump").setFall("hbm:step.iron_land").setProtectionLevel(1000F)
				.addResistance("fall", 0F).addResistance("monoxide", 0F).setFireproof(true).hides(EnumPlayerPart.HAT)
				.setUnlocalizedName("fau_helmet").setTextureName(RefStrings.MODID + ":fau_helmet");
		ModItems.fau_plate = new ArmorDigamma(aMatFau, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png",
				10000000, 10000, 2500, 0).cloneStats((ArmorFSB) ModItems.fau_helmet).setFullSetForHide()
				.setUnlocalizedName("fau_plate").setTextureName(RefStrings.MODID + ":fau_plate");
		ModItems.fau_legs = new ArmorDigamma(aMatFau, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", 10000000,
				10000, 2500, 0).cloneStats((ArmorFSB) ModItems.fau_helmet)
				.hides(EnumPlayerPart.LEFT_LEG, EnumPlayerPart.RIGHT_LEG).setFullSetForHide()
				.setUnlocalizedName("fau_legs").setTextureName(RefStrings.MODID + ":fau_legs");
		ModItems.fau_boots = new ArmorDigamma(aMatFau, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png",
				10000000, 10000, 2500, 0).cloneStats((ArmorFSB) ModItems.fau_helmet).setUnlocalizedName("fau_boots")
				.setTextureName(RefStrings.MODID + ":fau_boots");

		ArmorMaterial aMatDNS = EnumHelper.addArmorMaterial("HBM_DNT_NANO", 150, new int[] { 3, 8, 6, 3 }, 100);
		aMatDNS.customCraftingMaterial = ModItems.plate_armor_dnt;
		ModItems.dns_helmet = new ArmorDNT(aMatDNS, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 1000000000,
				1000000, 100000, 115).addEffect(new PotionEffect(Potion.damageBoost.id, 20, 9))
				.addEffect(new PotionEffect(Potion.digSpeed.id, 20, 7))
				.addEffect(new PotionEffect(Potion.jump.id, 20, 2)).setHasGeigerSound(true).enableVATS(true)
				.enableThermalSight(true).setHasHardLanding(true).setStep("hbm:step.metal")
				.setJump("hbm:step.iron_jump").setFall("hbm:step.iron_land").setFireproof(true)
				.hides(EnumPlayerPart.HAT).setUnlocalizedName("dns_helmet")
				.setTextureName(RefStrings.MODID + ":dns_helmet");
		ModItems.dns_plate = new ArmorDNT(aMatDNS, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 1000000000,
				1000000, 100000, 115).cloneStats((ArmorFSB) ModItems.dns_helmet).setUnlocalizedName("dns_plate")
				.setTextureName(RefStrings.MODID + ":dns_plate");
		ModItems.dns_legs = new ArmorDNT(aMatDNS, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", 1000000000,
				1000000, 100000, 115).cloneStats((ArmorFSB) ModItems.dns_helmet).setUnlocalizedName("dns_legs")
				.setTextureName(RefStrings.MODID + ":dns_legs");
		ModItems.dns_boots = new ArmorDNT(aMatDNS, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", 1000000000,
				1000000, 100000, 115).cloneStats((ArmorFSB) ModItems.dns_helmet).setUnlocalizedName("dns_boots")
				.setTextureName(RefStrings.MODID + ":dns_boots");

		ArmorMaterial aMatTrench = EnumHelper.addArmorMaterial("HBM_TRENCH", 150, new int[] { 3, 8, 6, 3 }, 100);
		aMatTrench.customCraftingMaterial = ModItems.plate_iron;
		ModItems.trenchmaster_helmet = new ArmorTrenchmaster(aMatTrench, 0,
				RefStrings.MODID + ":textures/armor/starmetal_1.png").setMod(0.25F).setThreshold(5.0F)
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 2))
				.addEffect(new PotionEffect(Potion.digSpeed.id, 20, 1))
				.addEffect(new PotionEffect(Potion.jump.id, 20, 1))
				.addEffect(new PotionEffect(Potion.moveSpeed.id, 20, 0)).enableVATS(true).addResistance("fall", 0F)
				.setFireproof(true).setStepSize(1).hides(EnumPlayerPart.HAT).setUnlocalizedName("trenchmaster_helmet")
				.setTextureName(RefStrings.MODID + ":trenchmaster_helmet");
		ModItems.trenchmaster_plate = new ArmorTrenchmaster(aMatTrench, 1,
				RefStrings.MODID + ":textures/armor/starmetal_1.png")
				.cloneStats((ArmorFSB) ModItems.trenchmaster_helmet).setUnlocalizedName("trenchmaster_plate")
				.setTextureName(RefStrings.MODID + ":trenchmaster_plate");
		ModItems.trenchmaster_legs = new ArmorTrenchmaster(aMatTrench, 2,
				RefStrings.MODID + ":textures/armor/starmetal_2.png")
				.cloneStats((ArmorFSB) ModItems.trenchmaster_helmet).setUnlocalizedName("trenchmaster_legs")
				.setTextureName(RefStrings.MODID + ":trenchmaster_legs");
		ModItems.trenchmaster_boots = new ArmorTrenchmaster(aMatTrench, 3,
				RefStrings.MODID + ":textures/armor/starmetal_1.png")
				.cloneStats((ArmorFSB) ModItems.trenchmaster_helmet).setUnlocalizedName("trenchmaster_boots")
				.setTextureName(RefStrings.MODID + ":trenchmaster_boots");

		ModItems.jackt = new ModArmor(MainRegistry.aMatSteel, 1).setUnlocalizedName("jackt")
				.setTextureName(RefStrings.MODID + ":jackt");
		ModItems.jackt2 = new ModArmor(MainRegistry.aMatSteel, 1).setUnlocalizedName("jackt2")
				.setTextureName(RefStrings.MODID + ":jackt2");

		ModItems.chainsaw = new ItemChainsaw(25, -0.05, MainRegistry.tMatChainsaw, EnumToolType.AXE, 5000, 1, 250,
				Fluids.DIESEL, Fluids.DIESEL_CRACK, Fluids.KEROSENE, Fluids.BIOFUEL, Fluids.GASOLINE,
				Fluids.GASOLINE_LEADED, Fluids.PETROIL, Fluids.PETROIL_LEADED, Fluids.COALGAS, Fluids.COALGAS_LEADED)
				.addBreakAbility(new ToolAbility.SilkAbility()).addBreakAbility(new ToolAbility.RecursionAbility(5))
				.addHitAbility(new WeaponAbility.ChainsawAbility(4)).addHitAbility(new WeaponAbility.BeheaderAbility())
				.setShears().setUnlocalizedName("chainsaw").setTextureName(RefStrings.MODID + ":chainsaw");

		ModItems.schrabidium_sword = new ItemSwordAbility(150, 0, MainRegistry.tMatSchrab)
				.addHitAbility(new WeaponAbility.RadiationAbility(50F))
				.addHitAbility(new WeaponAbility.VampireAbility(2F)).setRarity(EnumRarity.rare)
				.setUnlocalizedName("schrabidium_sword").setTextureName(RefStrings.MODID + ":schrabidium_sword");

		ModItems.schrabidium_pickaxe = new ItemToolAbility(20, 0, MainRegistry.tMatSchrab, EnumToolType.PICKAXE)
				.addHitAbility(new WeaponAbility.RadiationAbility(15F))
				.addBreakAbility(new ToolAbility.HammerAbility(2)).addBreakAbility(new ToolAbility.RecursionAbility(10))
				.addBreakAbility(new ToolAbility.SilkAbility()).addBreakAbility(new LuckAbility(5))
				.addBreakAbility(new ToolAbility.SmelterAbility()).addBreakAbility(new ToolAbility.ShredderAbility())
				.setRarity(EnumRarity.rare).setUnlocalizedName("schrabidium_pickaxe")
				.setTextureName(RefStrings.MODID + ":schrabidium_pickaxe");

		ModItems.schrabidium_axe = new ItemToolAbility(25, 0, MainRegistry.tMatSchrab, EnumToolType.AXE)
				.addHitAbility(new WeaponAbility.RadiationAbility(15F))
				.addBreakAbility(new ToolAbility.HammerAbility(2)).addBreakAbility(new ToolAbility.RecursionAbility(10))
				.addBreakAbility(new ToolAbility.SilkAbility()).addBreakAbility(new LuckAbility(5))
				.addBreakAbility(new ToolAbility.SmelterAbility()).addBreakAbility(new ToolAbility.ShredderAbility())
				.addHitAbility(new WeaponAbility.BeheaderAbility()).setRarity(EnumRarity.rare)
				.setUnlocalizedName("schrabidium_axe").setTextureName(RefStrings.MODID + ":schrabidium_axe");

		ModItems.schrabidium_shovel = new ItemToolAbility(15, 0, MainRegistry.tMatSchrab, EnumToolType.SHOVEL)
				.addHitAbility(new WeaponAbility.RadiationAbility(15F))
				.addBreakAbility(new ToolAbility.HammerAbility(2)).addBreakAbility(new ToolAbility.RecursionAbility(10))
				.addBreakAbility(new ToolAbility.SilkAbility()).addBreakAbility(new LuckAbility(5))
				.addBreakAbility(new ToolAbility.SmelterAbility()).addBreakAbility(new ToolAbility.ShredderAbility())
				.setRarity(EnumRarity.rare).setUnlocalizedName("schrabidium_shovel")
				.setTextureName(RefStrings.MODID + ":schrabidium_shovel");

		ModItems.schrabidium_hoe = new HoeSchrabidium(MainRegistry.tMatSchrab).setUnlocalizedName("schrabidium_hoe")
				.setTextureName(RefStrings.MODID + ":schrabidium_hoe");

		ModItems.titanium_sword = new ItemSwordAbility(6.5F, 0, MainRegistry.tMatTitan)
				.setUnlocalizedName("titanium_sword").setTextureName(RefStrings.MODID + ":titanium_sword");
		ModItems.titanium_pickaxe = new ItemToolAbility(4.5F, 0, MainRegistry.tMatTitan, EnumToolType.PICKAXE)
				.setUnlocalizedName("titanium_pickaxe").setTextureName(RefStrings.MODID + ":titanium_pickaxe");
		ModItems.titanium_axe = new ItemToolAbility(5.5F, 0, MainRegistry.tMatTitan, EnumToolType.AXE)
				.addHitAbility(new WeaponAbility.BeheaderAbility()).setUnlocalizedName("titanium_axe")
				.setTextureName(RefStrings.MODID + ":titanium_axe");
		ModItems.titanium_shovel = new ItemToolAbility(3.5F, 0, MainRegistry.tMatTitan, EnumToolType.SHOVEL)
				.setUnlocalizedName("titanium_shovel").setTextureName(RefStrings.MODID + ":titanium_shovel");
		ModItems.titanium_hoe = new ModHoe(MainRegistry.tMatTitan).setUnlocalizedName("titanium_hoe")
				.setTextureName(RefStrings.MODID + ":titanium_hoe");
		ModItems.steel_sword = new ItemSwordAbility(6F, 0, MainRegistry.tMatSteel).setUnlocalizedName("steel_sword")
				.setTextureName(RefStrings.MODID + ":steel_sword");
		ModItems.steel_pickaxe = new ItemToolAbility(4F, 0, MainRegistry.tMatSteel, EnumToolType.PICKAXE)
				.setUnlocalizedName("steel_pickaxe").setTextureName(RefStrings.MODID + ":steel_pickaxe");
		ModItems.steel_axe = new ItemToolAbility(5F, 0, MainRegistry.tMatSteel, EnumToolType.AXE)
				.addHitAbility(new WeaponAbility.BeheaderAbility()).setUnlocalizedName("steel_axe")
				.setTextureName(RefStrings.MODID + ":steel_axe");
		ModItems.steel_shovel = new ItemToolAbility(3F, 0, MainRegistry.tMatSteel, EnumToolType.SHOVEL)
				.setUnlocalizedName("steel_shovel").setTextureName(RefStrings.MODID + ":steel_shovel");
		ModItems.steel_hoe = new ModHoe(MainRegistry.tMatSteel).setUnlocalizedName("steel_hoe")
				.setTextureName(RefStrings.MODID + ":steel_hoe");

		ModItems.alloy_sword = new ItemSwordAbility(9F, 0, MainRegistry.tMatAlloy)
				.addHitAbility(new WeaponAbility.StunAbility(2)).setUnlocalizedName("alloy_sword")
				.setTextureName(RefStrings.MODID + ":alloy_sword");

		ModItems.alloy_pickaxe = new ItemToolAbility(6F, 0, MainRegistry.tMatAlloy, EnumToolType.PICKAXE)
				.addBreakAbility(new ToolAbility.RecursionAbility(3)).setUnlocalizedName("alloy_pickaxe")
				.setTextureName(RefStrings.MODID + ":alloy_pickaxe");

		ModItems.alloy_axe = new ItemToolAbility(7F, 0, MainRegistry.tMatAlloy, EnumToolType.AXE)
				.addBreakAbility(new ToolAbility.RecursionAbility(3)).addHitAbility(new WeaponAbility.BeheaderAbility())
				.setUnlocalizedName("alloy_axe").setTextureName(RefStrings.MODID + ":alloy_axe");

		ModItems.alloy_shovel = new ItemToolAbility(5F, 0, MainRegistry.tMatAlloy, EnumToolType.SHOVEL)
				.addBreakAbility(new ToolAbility.RecursionAbility(3)).setUnlocalizedName("alloy_shovel")
				.setTextureName(RefStrings.MODID + ":alloy_shovel");

		ModItems.alloy_hoe = new ModHoe(MainRegistry.tMatAlloy).setUnlocalizedName("alloy_hoe")
				.setTextureName(RefStrings.MODID + ":alloy_hoe");

		ModItems.cmb_sword = new ItemSwordAbility(50F, 0, MainRegistry.tMatCMB)
				.addHitAbility(new WeaponAbility.StunAbility(2)).addHitAbility(new WeaponAbility.VampireAbility(2F))
				.setUnlocalizedName("cmb_sword").setTextureName(RefStrings.MODID + ":cmb_sword");

		ModItems.cmb_pickaxe = new ItemToolAbility(10F, 0, MainRegistry.tMatCMB, EnumToolType.PICKAXE)
				.addBreakAbility(new ToolAbility.RecursionAbility(5)).addBreakAbility(new ToolAbility.SmelterAbility())
				.addBreakAbility(new ToolAbility.SilkAbility()).addBreakAbility(new LuckAbility(3))
				.setUnlocalizedName("cmb_pickaxe").setTextureName(RefStrings.MODID + ":cmb_pickaxe");

		ModItems.cmb_axe = new ItemToolAbility(12.5F, 0, MainRegistry.tMatCMB, EnumToolType.AXE)
				.addBreakAbility(new ToolAbility.RecursionAbility(5)).addBreakAbility(new ToolAbility.SmelterAbility())
				.addBreakAbility(new ToolAbility.SilkAbility()).addBreakAbility(new LuckAbility(3))
				.addHitAbility(new WeaponAbility.BeheaderAbility()).setUnlocalizedName("cmb_axe")
				.setTextureName(RefStrings.MODID + ":cmb_axe");

		ModItems.cmb_shovel = new ItemToolAbility(8F, 0, MainRegistry.tMatCMB, EnumToolType.SHOVEL)
				.addBreakAbility(new ToolAbility.RecursionAbility(5)).addBreakAbility(new ToolAbility.SmelterAbility())
				.addBreakAbility(new ToolAbility.SilkAbility()).addBreakAbility(new LuckAbility(3))
				.setUnlocalizedName("cmb_shovel").setTextureName(RefStrings.MODID + ":cmb_shovel");

		ModItems.cmb_hoe = new ModHoe(MainRegistry.tMatCMB).setUnlocalizedName("cmb_hoe")
				.setTextureName(RefStrings.MODID + ":cmb_hoe");

		ModItems.elec_sword = new ItemSwordAbilityPower(15F, 0, MainRegistry.tMatElec, 500000, 1000, 100)
				.addHitAbility(new WeaponAbility.StunAbility(5)).setUnlocalizedName("elec_sword")
				.setTextureName(RefStrings.MODID + ":elec_sword_anim");

		ModItems.elec_pickaxe = new ItemToolAbilityPower(10F, 0, MainRegistry.tMatElec, EnumToolType.PICKAXE, 500000,
				1000, 100).addBreakAbility(new ToolAbility.HammerAbility(2))
				.addBreakAbility(new ToolAbility.RecursionAbility(5)).addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new LuckAbility(2)).setUnlocalizedName("elec_pickaxe")
				.setTextureName(RefStrings.MODID + ":elec_drill_anim");

		ModItems.elec_axe = new ItemToolAbilityPower(12.5F, 0, MainRegistry.tMatElec, EnumToolType.AXE, 500000, 1000,
				100).addBreakAbility(new ToolAbility.HammerAbility(2))
				.addBreakAbility(new ToolAbility.RecursionAbility(5)).addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new LuckAbility(2)).addHitAbility(new WeaponAbility.ChainsawAbility(6))
				.addHitAbility(new WeaponAbility.BeheaderAbility()).setShears().setUnlocalizedName("elec_axe")
				.setTextureName(RefStrings.MODID + ":elec_chainsaw_anim");

		ModItems.elec_shovel = new ItemToolAbilityPower(7.5F, 0, MainRegistry.tMatElec, EnumToolType.SHOVEL, 500000,
				1000, 100).addBreakAbility(new ToolAbility.HammerAbility(2))
				.addBreakAbility(new ToolAbility.RecursionAbility(5)).addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new LuckAbility(2)).setUnlocalizedName("elec_shovel")
				.setTextureName(RefStrings.MODID + ":elec_shovel_anim");

		ModItems.desh_sword = new ItemSwordAbility(15F, 0, MainRegistry.tMatDesh)
				.addHitAbility(new WeaponAbility.StunAbility(2)).setUnlocalizedName("desh_sword")
				.setTextureName(RefStrings.MODID + ":desh_sword");

		ModItems.desh_pickaxe = new ItemToolAbility(5F, -0.05, MainRegistry.tMatDesh, EnumToolType.PICKAXE)
				.addBreakAbility(new ToolAbility.HammerAbility(1)).addBreakAbility(new ToolAbility.RecursionAbility(3))
				.addBreakAbility(new ToolAbility.SilkAbility()).addBreakAbility(new LuckAbility(2))
				.setUnlocalizedName("desh_pickaxe").setTextureName(RefStrings.MODID + ":desh_pickaxe");

		ModItems.desh_axe = new ItemToolAbility(6.5F, -0.05, MainRegistry.tMatDesh, EnumToolType.AXE)
				.addBreakAbility(new ToolAbility.HammerAbility(1)).addBreakAbility(new ToolAbility.RecursionAbility(3))
				.addBreakAbility(new ToolAbility.SilkAbility()).addBreakAbility(new LuckAbility(2))
				.addHitAbility(new WeaponAbility.BeheaderAbility()).setUnlocalizedName("desh_axe")
				.setTextureName(RefStrings.MODID + ":desh_axe");

		ModItems.desh_shovel = new ItemToolAbility(4F, -0.05, MainRegistry.tMatDesh, EnumToolType.SHOVEL)
				.addBreakAbility(new ToolAbility.HammerAbility(1)).addBreakAbility(new ToolAbility.RecursionAbility(3))
				.addBreakAbility(new ToolAbility.SilkAbility()).addBreakAbility(new LuckAbility(2))
				.setUnlocalizedName("desh_shovel").setTextureName(RefStrings.MODID + ":desh_shovel");

		ModItems.desh_hoe = new ModHoe(MainRegistry.tMatDesh).setUnlocalizedName("desh_hoe")
				.setTextureName(RefStrings.MODID + ":desh_hoe");

		ModItems.cobalt_sword = new ItemSwordAbility(12F, 0, MainRegistry.tMatCobalt).setUnlocalizedName("cobalt_sword")
				.setTextureName(RefStrings.MODID + ":cobalt_sword");
		ModItems.cobalt_pickaxe = new ItemToolAbility(4F, 0, MainRegistry.tMatCobalt, EnumToolType.PICKAXE)
				.addBreakAbility(new ToolAbility.RecursionAbility(4)).addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new LuckAbility(1)).setUnlocalizedName("cobalt_pickaxe")
				.setTextureName(RefStrings.MODID + ":cobalt_pickaxe");
		ModItems.cobalt_axe = new ItemToolAbility(6F, 0, MainRegistry.tMatCobalt, EnumToolType.AXE)
				.addBreakAbility(new ToolAbility.RecursionAbility(4)).addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new LuckAbility(1)).addHitAbility(new WeaponAbility.BeheaderAbility())
				.setUnlocalizedName("cobalt_axe").setTextureName(RefStrings.MODID + ":cobalt_axe");
		ModItems.cobalt_shovel = new ItemToolAbility(3.5F, 0, MainRegistry.tMatCobalt, EnumToolType.SHOVEL)
				.addBreakAbility(new ToolAbility.RecursionAbility(4)).addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new LuckAbility(1)).setUnlocalizedName("cobalt_shovel")
				.setTextureName(RefStrings.MODID + ":cobalt_shovel");
		ModItems.cobalt_hoe = new ModHoe(MainRegistry.tMatCobalt).setUnlocalizedName("cobalt_hoe")
				.setTextureName(RefStrings.MODID + ":cobalt_hoe");

		ToolMaterial matDecCobalt = EnumHelper.addToolMaterial("HBM_COBALT2", 3, 2500, 15.0F, 2.5F, 75)
				.setRepairItem(new ItemStack(ModItems.ingot_cobalt));
		ModItems.cobalt_decorated_sword = new ItemSwordAbility(15F, 0, matDecCobalt)
				.addHitAbility(new WeaponAbility.BobbleAbility()).setUnlocalizedName("cobalt_decorated_sword")
				.setTextureName(RefStrings.MODID + ":cobalt_decorated_sword");
		ModItems.cobalt_decorated_pickaxe = new ItemToolAbility(6F, 0, matDecCobalt, EnumToolType.PICKAXE)
				.addBreakAbility(new ToolAbility.RecursionAbility(4)).addBreakAbility(new ToolAbility.HammerAbility(1))
				.addBreakAbility(new ToolAbility.SilkAbility()).addBreakAbility(new LuckAbility(3))
				.setUnlocalizedName("cobalt_decorated_pickaxe")
				.setTextureName(RefStrings.MODID + ":cobalt_decorated_pickaxe");
		ModItems.cobalt_decorated_axe = new ItemToolAbility(8F, 0, matDecCobalt, EnumToolType.AXE)
				.addBreakAbility(new ToolAbility.RecursionAbility(4)).addBreakAbility(new ToolAbility.HammerAbility(1))
				.addBreakAbility(new ToolAbility.SilkAbility()).addBreakAbility(new LuckAbility(3))
				.addHitAbility(new WeaponAbility.BeheaderAbility()).setUnlocalizedName("cobalt_decorated_axe")
				.setTextureName(RefStrings.MODID + ":cobalt_decorated_axe");
		ModItems.cobalt_decorated_shovel = new ItemToolAbility(5F, 0, matDecCobalt, EnumToolType.SHOVEL)
				.addBreakAbility(new ToolAbility.RecursionAbility(4)).addBreakAbility(new ToolAbility.HammerAbility(1))
				.addBreakAbility(new ToolAbility.SilkAbility()).addBreakAbility(new LuckAbility(3))
				.setUnlocalizedName("cobalt_decorated_shovel")
				.setTextureName(RefStrings.MODID + ":cobalt_decorated_shovel");
		ModItems.cobalt_decorated_hoe = new ModHoe(matDecCobalt).setUnlocalizedName("cobalt_decorated_hoe")
				.setTextureName(RefStrings.MODID + ":cobalt_decorated_hoe");

		ToolMaterial matStarmetal = EnumHelper.addToolMaterial("HBM_STARMETAL", 3, 3000, 20.0F, 2.5F, 100)
				.setRepairItem(new ItemStack(ModItems.ingot_starmetal));
		ModItems.starmetal_sword = new ItemSwordAbility(25F, 0, matStarmetal)
				.addHitAbility(new WeaponAbility.BeheaderAbility()).addHitAbility(new WeaponAbility.StunAbility(3))
				.addHitAbility(new WeaponAbility.BobbleAbility()).setUnlocalizedName("starmetal_sword")
				.setTextureName(RefStrings.MODID + ":starmetal_sword");
		ModItems.starmetal_pickaxe = new ItemToolAbility(8F, 0, matStarmetal, EnumToolType.PICKAXE)
				.addBreakAbility(new ToolAbility.RecursionAbility(6)).addBreakAbility(new ToolAbility.HammerAbility(2))
				.addBreakAbility(new ToolAbility.SilkAbility()).addBreakAbility(new LuckAbility(5))
				.addHitAbility(new WeaponAbility.StunAbility(3)).setUnlocalizedName("starmetal_pickaxe")
				.setTextureName(RefStrings.MODID + ":starmetal_pickaxe");
		ModItems.starmetal_axe = new ItemToolAbility(12F, 0, matStarmetal, EnumToolType.AXE)
				.addBreakAbility(new ToolAbility.RecursionAbility(6)).addBreakAbility(new ToolAbility.HammerAbility(2))
				.addBreakAbility(new ToolAbility.SilkAbility()).addBreakAbility(new LuckAbility(5))
				.addHitAbility(new WeaponAbility.BeheaderAbility()).addHitAbility(new WeaponAbility.StunAbility(3))
				.setUnlocalizedName("starmetal_axe").setTextureName(RefStrings.MODID + ":starmetal_axe");
		ModItems.starmetal_shovel = new ItemToolAbility(7F, 0, matStarmetal, EnumToolType.SHOVEL)
				.addBreakAbility(new ToolAbility.RecursionAbility(6)).addBreakAbility(new ToolAbility.HammerAbility(2))
				.addBreakAbility(new ToolAbility.SilkAbility()).addBreakAbility(new LuckAbility(5))
				.addHitAbility(new WeaponAbility.StunAbility(3)).setUnlocalizedName("starmetal_shovel")
				.setTextureName(RefStrings.MODID + ":starmetal_shovel");
		ModItems.starmetal_hoe = new ModHoe(matStarmetal).setUnlocalizedName("starmetal_hoe")
				.setTextureName(RefStrings.MODID + ":starmetal_hoe");

		ModItems.centri_stick = new ItemToolAbility(3F, 0, MainRegistry.tMatElec, EnumToolType.MINER)
				.addBreakAbility(new ToolAbility.CentrifugeAbility()).setMaxDamage(50)
				.setUnlocalizedName("centri_stick").setTextureName(RefStrings.MODID + ":centri_stick");
		ModItems.smashing_hammer = new ItemToolAbility(12F, -0.1, MainRegistry.tMatSteel, EnumToolType.MINER)
				.addBreakAbility(new ToolAbility.ShredderAbility()).setMaxDamage(2500)
				.setUnlocalizedName("smashing_hammer").setTextureName(RefStrings.MODID + ":smashing_hammer");
		ModItems.drax = new ItemToolAbilityPower(15F, -0.05, MainRegistry.tMatElec, EnumToolType.MINER, 500000000,
				100000, 5000).addBreakAbility(new ToolAbility.SmelterAbility())
				.addBreakAbility(new ToolAbility.ShredderAbility()).addBreakAbility(new ToolAbility.LuckAbility(2))
				.addBreakAbility(new ToolAbility.HammerAbility(1)).addBreakAbility(new ToolAbility.HammerAbility(2))
				.addBreakAbility(new ToolAbility.RecursionAbility(5)).setUnlocalizedName("drax")
				.setTextureName(RefStrings.MODID + ":drax");
		ModItems.drax_mk2 = new ItemToolAbilityPower(20F, -0.05, MainRegistry.tMatElec, EnumToolType.MINER, 1000000000,
				250000, 7500).addBreakAbility(new ToolAbility.SmelterAbility())
				.addBreakAbility(new ToolAbility.ShredderAbility()).addBreakAbility(new ToolAbility.CentrifugeAbility())
				.addBreakAbility(new ToolAbility.LuckAbility(3)).addBreakAbility(new ToolAbility.HammerAbility(1))
				.addBreakAbility(new ToolAbility.HammerAbility(2)).addBreakAbility(new ToolAbility.HammerAbility(3))
				.addBreakAbility(new ToolAbility.RecursionAbility(7)).setUnlocalizedName("drax_mk2")
				.setTextureName(RefStrings.MODID + ":drax_mk2");
		ModItems.drax_mk3 = new ItemToolAbilityPower(20F, -0.05, MainRegistry.tMatElec, EnumToolType.MINER, 2500000000L,
				500000, 10000).addBreakAbility(new ToolAbility.SmelterAbility())
				.addBreakAbility(new ToolAbility.ShredderAbility()).addBreakAbility(new ToolAbility.CentrifugeAbility())
				.addBreakAbility(new ToolAbility.CrystallizerAbility()).addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new ToolAbility.LuckAbility(4)).addBreakAbility(new ToolAbility.HammerAbility(1))
				.addBreakAbility(new ToolAbility.HammerAbility(2)).addBreakAbility(new ToolAbility.HammerAbility(3))
				.addBreakAbility(new ToolAbility.HammerAbility(4)).addBreakAbility(new ToolAbility.RecursionAbility(9))
				.setUnlocalizedName("drax_mk3").setTextureName(RefStrings.MODID + ":drax_mk3");

		ToolMaterial matBismuth = EnumHelper.addToolMaterial("HBM_BISMUTH", 4, 0, 50F, 0.0F, 200)
				.setRepairItem(new ItemStack(ModItems.ingot_bismuth));
		ModItems.bismuth_pickaxe = new ItemToolAbility(15F, 0, matBismuth, EnumToolType.MINER)
				.addBreakAbility(new ToolAbility.HammerAbility(2)).addBreakAbility(new ToolAbility.RecursionAbility(4))
				.addBreakAbility(new ToolAbility.ShredderAbility()).addBreakAbility(new ToolAbility.LuckAbility(2))
				.addBreakAbility(new ToolAbility.SilkAbility()).addHitAbility(new WeaponAbility.StunAbility(5))
				.addHitAbility(new WeaponAbility.VampireAbility(2F)).addHitAbility(new WeaponAbility.BeheaderAbility())
				.setDepthRockBreaker().setUnlocalizedName("bismuth_pickaxe")
				.setTextureName(RefStrings.MODID + ":bismuth_pickaxe");

		ToolMaterial matVolcano = EnumHelper.addToolMaterial("HBM_VOLCANIC", 4, 0, 50F, 0.0F, 200)
				.setRepairItem(new ItemStack(ModItems.ingot_bismuth));
		ModItems.volcanic_pickaxe = new ItemToolAbility(15F, 0, matVolcano, EnumToolType.MINER)
				.addBreakAbility(new ToolAbility.HammerAbility(2)).addBreakAbility(new ToolAbility.RecursionAbility(4))
				.addBreakAbility(new ToolAbility.SmelterAbility()).addBreakAbility(new ToolAbility.LuckAbility(3))
				.addBreakAbility(new ToolAbility.SilkAbility()).addHitAbility(new WeaponAbility.FireAbility(5))
				.addHitAbility(new WeaponAbility.VampireAbility(2F)).addHitAbility(new WeaponAbility.BeheaderAbility())
				.setDepthRockBreaker().setUnlocalizedName("volcanic_pickaxe")
				.setTextureName(RefStrings.MODID + ":volcanic_pickaxe");

		ToolMaterial matChlorophyte = EnumHelper.addToolMaterial("HBM_CHLOROPHYTE", 4, 0, 75F, 0.0F, 200)
				.setRepairItem(new ItemStack(ModItems.powder_chlorophyte));
		ModItems.chlorophyte_pickaxe = new ItemToolAbility(20F, 0, matChlorophyte, EnumToolType.MINER)
				.addBreakAbility(new ToolAbility.HammerAbility(2)).addBreakAbility(new ToolAbility.RecursionAbility(4))
				.addBreakAbility(new ToolAbility.LuckAbility(4)).addBreakAbility(new ToolAbility.CentrifugeAbility())
				.addBreakAbility(new ToolAbility.MercuryAbility()).addHitAbility(new WeaponAbility.StunAbility(10))
				.addHitAbility(new WeaponAbility.VampireAbility(5F)).addHitAbility(new WeaponAbility.BeheaderAbility())
				.setDepthRockBreaker().setUnlocalizedName("chlorophyte_pickaxe")
				.setTextureName(RefStrings.MODID + ":chlorophyte_pickaxe");

		ToolMaterial matMese = EnumHelper.addToolMaterial("HBM_MESE", 4, 0, 100F, 0.0F, 200)
				.setRepairItem(new ItemStack(ModItems.plate_paa));
		ModItems.mese_pickaxe = new ItemToolAbility(35F, 0, matMese, EnumToolType.MINER)
				.addBreakAbility(new ToolAbility.HammerAbility(3)).addBreakAbility(new ToolAbility.RecursionAbility(5))
				.addBreakAbility(new ToolAbility.CrystallizerAbility()).addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new ToolAbility.LuckAbility(9)).addBreakAbility(new ToolAbility.ExplosionAbility(2.5F))
				.addBreakAbility(new ToolAbility.ExplosionAbility(5F))
				.addBreakAbility(new ToolAbility.ExplosionAbility(10F))
				.addBreakAbility(new ToolAbility.ExplosionAbility(15F)).addHitAbility(new WeaponAbility.StunAbility(10))
				.addHitAbility(new WeaponAbility.PhosphorusAbility(60))
				.addHitAbility(new WeaponAbility.BeheaderAbility()).setDepthRockBreaker()
				.setUnlocalizedName("mese_pickaxe").setTextureName(RefStrings.MODID + ":mese_pickaxe");

		ModItems.dnt_sword = new ItemSwordAbility(12F, 0, matMese).setUnlocalizedName("dnt_sword")
				.setTextureName(RefStrings.MODID + ":dnt_sword");

		ToolMaterial matMeteorite = EnumHelper.addToolMaterial("HBM_METEORITE", 4, 0, 50F, 0.0F, 200)
				.setRepairItem(new ItemStack(ModItems.plate_paa));
		ModItems.meteorite_sword = new ItemSwordMeteorite(10F, 0, matMeteorite).setUnlocalizedName("meteorite_sword")
				.setTextureName(RefStrings.MODID + ":meteorite_sword");
		ModItems.meteorite_sword_seared = new ItemSwordMeteorite(15F, 0, matMeteorite)
				.setUnlocalizedName("meteorite_sword_seared").setTextureName(RefStrings.MODID + ":meteorite_sword");
		ModItems.meteorite_sword_reforged = new ItemSwordMeteorite(25F, 0, matMeteorite)
				.setUnlocalizedName("meteorite_sword_reforged").setTextureName(RefStrings.MODID + ":meteorite_sword");
		ModItems.meteorite_sword_hardened = new ItemSwordMeteorite(35F, 0, matMeteorite)
				.setUnlocalizedName("meteorite_sword_hardened").setTextureName(RefStrings.MODID + ":meteorite_sword");
		ModItems.meteorite_sword_alloyed = new ItemSwordMeteorite(50F, 0, matMeteorite)
				.setUnlocalizedName("meteorite_sword_alloyed").setTextureName(RefStrings.MODID + ":meteorite_sword");
		ModItems.meteorite_sword_machined = new ItemSwordMeteorite(65F, 0, matMeteorite)
				.setUnlocalizedName("meteorite_sword_machined").setTextureName(RefStrings.MODID + ":meteorite_sword");
		ModItems.meteorite_sword_treated = new ItemSwordMeteorite(80F, 0, matMeteorite)
				.setUnlocalizedName("meteorite_sword_treated").setTextureName(RefStrings.MODID + ":meteorite_sword");
		ModItems.meteorite_sword_etched = new ItemSwordMeteorite(100F, 0, matMeteorite)
				.setUnlocalizedName("meteorite_sword_etched").setTextureName(RefStrings.MODID + ":meteorite_sword");
		ModItems.meteorite_sword_bred = new ItemSwordMeteorite(125F, 0, matMeteorite)
				.setUnlocalizedName("meteorite_sword_bred").setTextureName(RefStrings.MODID + ":meteorite_sword");
		ModItems.meteorite_sword_irradiated = new ItemSwordMeteorite(175F, 0, matMeteorite)
				.setUnlocalizedName("meteorite_sword_irradiated").setTextureName(RefStrings.MODID + ":meteorite_sword");
		ModItems.meteorite_sword_fused = new ItemSwordMeteorite(250F, 0, matMeteorite)
				.setUnlocalizedName("meteorite_sword_fused").setTextureName(RefStrings.MODID + ":meteorite_sword");
		ModItems.meteorite_sword_baleful = new ItemSwordMeteorite(500F, 0, matMeteorite)
				.setUnlocalizedName("meteorite_sword_baleful").setTextureName(RefStrings.MODID + ":meteorite_sword");

		ModItems.mask_of_infamy = new MaskOfInfamy(ArmorMaterial.IRON, 0).setUnlocalizedName("mask_of_infamy")
				.setMaxStackSize(1).setTextureName(RefStrings.MODID + ":mask_of_infamy");

		ModItems.hazmat_helmet = new ArmorHazmatMask(MainRegistry.aMatHaz, 0,
				RefStrings.MODID + ":textures/armor/hazmat_1.png").setUnlocalizedName("hazmat_helmet")
				.setTextureName(RefStrings.MODID + ":hazmat_helmet");
		ModItems.hazmat_plate = new ArmorHazmat(MainRegistry.aMatHaz, 1,
				RefStrings.MODID + ":textures/armor/hazmat_1.png").setUnlocalizedName("hazmat_plate")
				.setTextureName(RefStrings.MODID + ":hazmat_plate");
		ModItems.hazmat_legs = new ArmorHazmat(MainRegistry.aMatHaz, 2,
				RefStrings.MODID + ":textures/armor/hazmat_2.png").setUnlocalizedName("hazmat_legs")
				.setTextureName(RefStrings.MODID + ":hazmat_legs");
		ModItems.hazmat_boots = new ArmorHazmat(MainRegistry.aMatHaz, 3,
				RefStrings.MODID + ":textures/armor/hazmat_1.png").setUnlocalizedName("hazmat_boots")
				.setTextureName(RefStrings.MODID + ":hazmat_boots");
		ModItems.hazmat_helmet_red = new ArmorHazmatMask(MainRegistry.aMatHaz2, 0,
				"hbm:textures/models/ModelHazRed.png").setUnlocalizedName("hazmat_helmet_red")
				.setTextureName(RefStrings.MODID + ":hazmat_helmet_red");
		ModItems.hazmat_plate_red = new ArmorHazmat(MainRegistry.aMatHaz2, 1,
				RefStrings.MODID + ":textures/armor/hazmat_1_red.png").setUnlocalizedName("hazmat_plate_red")
				.setTextureName(RefStrings.MODID + ":hazmat_plate_red");
		ModItems.hazmat_legs_red = new ArmorHazmat(MainRegistry.aMatHaz2, 2,
				RefStrings.MODID + ":textures/armor/hazmat_2_red.png").setUnlocalizedName("hazmat_legs_red")
				.setTextureName(RefStrings.MODID + ":hazmat_legs_red");
		ModItems.hazmat_boots_red = new ArmorHazmat(MainRegistry.aMatHaz2, 3,
				RefStrings.MODID + ":textures/armor/hazmat_1_red.png").setUnlocalizedName("hazmat_boots_red")
				.setTextureName(RefStrings.MODID + ":hazmat_boots_red");
		ModItems.hazmat_helmet_grey = new ArmorHazmatMask(MainRegistry.aMatHaz3, 0,
				"hbm:textures/models/ModelHazGrey.png").setFireproof(true).setUnlocalizedName("hazmat_helmet_grey")
				.setTextureName(RefStrings.MODID + ":hazmat_helmet_grey");
		ModItems.hazmat_plate_grey = new ArmorHazmat(MainRegistry.aMatHaz3, 1,
				RefStrings.MODID + ":textures/armor/hazmat_1_grey.png")
				.cloneStats((ArmorFSB) ModItems.hazmat_helmet_grey).setUnlocalizedName("hazmat_plate_grey")
				.setTextureName(RefStrings.MODID + ":hazmat_plate_grey");
		ModItems.hazmat_legs_grey = new ArmorHazmat(MainRegistry.aMatHaz3, 2,
				RefStrings.MODID + ":textures/armor/hazmat_2_grey.png")
				.cloneStats((ArmorFSB) ModItems.hazmat_helmet_grey).setUnlocalizedName("hazmat_legs_grey")
				.setTextureName(RefStrings.MODID + ":hazmat_legs_grey");
		ModItems.hazmat_boots_grey = new ArmorHazmat(MainRegistry.aMatHaz3, 3,
				RefStrings.MODID + ":textures/armor/hazmat_1_grey.png")
				.cloneStats((ArmorFSB) ModItems.hazmat_helmet_grey).setUnlocalizedName("hazmat_boots_grey")
				.setTextureName(RefStrings.MODID + ":hazmat_boots_grey");
		ModItems.hazmat_paa_helmet = new ArmorHazmatMask(MainRegistry.aMatPaa, 0,
				RefStrings.MODID + ":textures/armor/hazmat_paa_1.png").setFireproof(true)
				.setUnlocalizedName("hazmat_paa_helmet").setTextureName(RefStrings.MODID + ":hazmat_paa_helmet");
		ModItems.hazmat_paa_plate = new ArmorHazmat(MainRegistry.aMatPaa, 1,
				RefStrings.MODID + ":textures/armor/hazmat_paa_1.png").cloneStats((ArmorFSB) ModItems.hazmat_paa_helmet)
				.setUnlocalizedName("hazmat_paa_plate").setTextureName(RefStrings.MODID + ":hazmat_paa_plate");
		ModItems.hazmat_paa_legs = new ArmorHazmat(MainRegistry.aMatPaa, 2,
				RefStrings.MODID + ":textures/armor/hazmat_paa_2.png").cloneStats((ArmorFSB) ModItems.hazmat_paa_helmet)
				.setUnlocalizedName("hazmat_paa_legs").setTextureName(RefStrings.MODID + ":hazmat_paa_legs");
		ModItems.hazmat_paa_boots = new ArmorHazmat(MainRegistry.aMatPaa, 3,
				RefStrings.MODID + ":textures/armor/hazmat_paa_1.png").cloneStats((ArmorFSB) ModItems.hazmat_paa_helmet)
				.setUnlocalizedName("hazmat_paa_boots").setTextureName(RefStrings.MODID + ":hazmat_paa_boots");

		ArmorMaterial aMatLiquidator = EnumHelper.addArmorMaterial("HBM_LIQUIDATOR", 750, new int[] { 3, 8, 6, 3 }, 10);
		aMatLiquidator.customCraftingMaterial = ModItems.plate_lead;
		ModItems.liquidator_helmet = new ArmorLiquidatorMask(aMatLiquidator, 0,
				RefStrings.MODID + ":textures/armor/liquidator_helmet.png").setThreshold(1.0F).setBlastProtection(0.25F)
				.setFireproof(true).setStep("hbm:step.metal").setJump("hbm:step.iron_jump")
				.setFall("hbm:step.iron_land").setUnlocalizedName("liquidator_helmet").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":liquidator_helmet");
		ModItems.liquidator_plate = new ArmorLiquidator(aMatLiquidator, 1,
				RefStrings.MODID + ":textures/armor/liquidator_1.png").cloneStats((ArmorFSB) ModItems.liquidator_helmet)
				.setUnlocalizedName("liquidator_plate").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":liquidator_plate");
		ModItems.liquidator_legs = new ArmorLiquidator(aMatLiquidator, 2,
				RefStrings.MODID + ":textures/armor/liquidator_2.png").cloneStats((ArmorFSB) ModItems.liquidator_helmet)
				.setUnlocalizedName("liquidator_legs").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":liquidator_legs");
		ModItems.liquidator_boots = new ArmorLiquidator(aMatLiquidator, 3,
				RefStrings.MODID + ":textures/armor/liquidator_1.png").cloneStats((ArmorFSB) ModItems.liquidator_helmet)
				.setUnlocalizedName("liquidator_boots").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":liquidator_boots");

		ModItems.australium_iii = new ArmorAustralium(MainRegistry.aMatAus3, 1).setUnlocalizedName("australium_iii")
				.setMaxStackSize(1).setTextureName(RefStrings.MODID + ":australium_iii");

		ModItems.jetpack_boost = new JetpackBooster(Fluids.BALEFIRE, 32000).setUnlocalizedName("jetpack_boost")
				.setCreativeTab(CreativeTabs.tabCombat).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":jetpack_boost");
		ModItems.jetpack_break = new JetpackBreak(Fluids.KEROSENE, 12000).setUnlocalizedName("jetpack_break")
				.setCreativeTab(CreativeTabs.tabCombat).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":jetpack_break");
		ModItems.jetpack_fly = new JetpackRegular(Fluids.KEROSENE, 12000).setUnlocalizedName("jetpack_fly")
				.setCreativeTab(CreativeTabs.tabCombat).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":jetpack_fly");
		ModItems.jetpack_vector = new JetpackVectorized(Fluids.KEROSENE, 16000).setUnlocalizedName("jetpack_vector")
				.setCreativeTab(CreativeTabs.tabCombat).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":jetpack_vector");
		ModItems.wings_murk = new WingsMurk(MainRegistry.aMatCobalt).setUnlocalizedName("wings_murk")
				.setCreativeTab(CreativeTabs.tabCombat).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":wings_murk");
		ModItems.wings_limp = new WingsMurk(MainRegistry.aMatCobalt).setUnlocalizedName("wings_limp")
				.setCreativeTab(CreativeTabs.tabCombat).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":wings_limp");

		ModItems.cape_test = new ArmorModel(MainRegistry.enumArmorMaterialEmerald, 1).setUnlocalizedName("cape_test")
				.setCreativeTab(null).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cape_test");
		ModItems.cape_radiation = new ArmorModel(ArmorMaterial.CHAIN, 1).setUnlocalizedName("cape_radiation")
				.setCreativeTab(MainRegistry.consumableTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":cape_radiation");
		ModItems.cape_gasmask = new ArmorModel(ArmorMaterial.CHAIN, 1).setUnlocalizedName("cape_gasmask")
				.setCreativeTab(MainRegistry.consumableTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":cape_gasmask");
		ModItems.cape_schrabidium = new ArmorModel(MainRegistry.aMatSchrab, 1).setUnlocalizedName("cape_schrabidium")
				.setCreativeTab(MainRegistry.consumableTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":cape_schrabidium");
		ModItems.cape_hidden = new ArmorModel(ArmorMaterial.CHAIN, 1).setUnlocalizedName("cape_hidden")
				.setCreativeTab(MainRegistry.consumableTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":cape_unknown");

		ModItems.schrabidium_hammer = new WeaponSpecial(MainRegistry.tMatHammmer)
				.setUnlocalizedName("schrabidium_hammer").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":schrabidium_hammer");
		ModItems.shimmer_sledge = new WeaponSpecial(MainRegistry.enumToolMaterialSledge)
				.setUnlocalizedName("shimmer_sledge").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":shimmer_sledge_original");
		ModItems.shimmer_axe = new WeaponSpecial(MainRegistry.enumToolMaterialSledge).setUnlocalizedName("shimmer_axe")
				.setMaxStackSize(1).setTextureName(RefStrings.MODID + ":shimmer_axe");
		ModItems.bottle_opener = new WeaponSpecial(MainRegistry.enumToolMaterialBottleOpener)
				.setUnlocalizedName("bottle_opener").setCreativeTab(MainRegistry.consumableTab).setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":bottle_opener");
		ModItems.pch = new WeaponSpecial(MainRegistry.tMatHammmer).setUnlocalizedName("pch").setMaxStackSize(1)
				.setCreativeTab(null).setTextureName(RefStrings.MODID + ":schrabidium_hammer");
		ModItems.euphemium_stopper = new ItemSyringe().setUnlocalizedName("euphemium_stopper").setMaxStackSize(1)
				.setFull3D().setTextureName(RefStrings.MODID + ":euphemium_stopper");
		ModItems.matchstick = new ItemMatch().setUnlocalizedName("matchstick").setCreativeTab(CreativeTabs.tabTools)
				.setFull3D().setTextureName(RefStrings.MODID + ":matchstick");
		ModItems.balefire_and_steel = new ItemBalefireMatch().setUnlocalizedName("balefire_and_steel")
				.setCreativeTab(CreativeTabs.tabTools).setFull3D()
				.setTextureName(RefStrings.MODID + ":balefire_and_steel");
		ModItems.crowbar = new ModSword(MainRegistry.tMatSteel).setUnlocalizedName("crowbar").setFull3D()
				.setTextureName(RefStrings.MODID + ":crowbar");
		ModItems.wrench = new WeaponSpecial(MainRegistry.tMatSteel).setUnlocalizedName("wrench").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":wrench");
		ModItems.wrench_flipped = new WeaponSpecial(MainRegistry.tMatElec).setUnlocalizedName("wrench_flipped")
				.setMaxStackSize(1).setTextureName(RefStrings.MODID + ":wrench_flipped");
		ModItems.memespoon = new WeaponSpecial(MainRegistry.tMatSteel).setUnlocalizedName("memespoon")
				.setMaxStackSize(1).setTextureName(RefStrings.MODID + ":memespoon");
		ModItems.wood_gavel = new WeaponSpecial(ToolMaterial.WOOD).setUnlocalizedName("wood_gavel").setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":wood_gavel");
		ModItems.lead_gavel = new WeaponSpecial(MainRegistry.tMatSteel).setUnlocalizedName("lead_gavel")
				.setMaxStackSize(1).setTextureName(RefStrings.MODID + ":lead_gavel");
		ModItems.diamond_gavel = new WeaponSpecial(ToolMaterial.EMERALD).setUnlocalizedName("diamond_gavel")
				.setMaxStackSize(1).setTextureName(RefStrings.MODID + ":diamond_gavel");
		ToolMaterial matMeseGavel = EnumHelper.addToolMaterial("HBM_MESEGAVEL", 4, 0, 50F, 0.0F, 200)
				.setRepairItem(new ItemStack(ModItems.plate_paa));
		ModItems.mese_gavel = new ItemSwordAbility(250, 1.5, matMeseGavel)
				.addHitAbility(new WeaponAbility.PhosphorusAbility(60))
				.addHitAbility(new WeaponAbility.RadiationAbility(500)).addHitAbility(new WeaponAbility.StunAbility(10))
				.addHitAbility(new WeaponAbility.VampireAbility(50)).addHitAbility(new WeaponAbility.BeheaderAbility())
				.setUnlocalizedName("mese_gavel").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":mese_gavel");

		ModItems.multitool_hit = new ItemMultitoolPassive().setUnlocalizedName("multitool_hit").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":multitool_fist");
		ModItems.multitool_dig = new ItemMultitoolTool(4.0F, MainRegistry.enumToolMaterialMultitool,
				ItemMultitoolTool.getAllBlocks()).setFull3D().setUnlocalizedName("multitool_dig")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":multitool_claw");
		ModItems.multitool_silk = new ItemMultitoolTool(4.0F, MainRegistry.enumToolMaterialMultitool,
				ItemMultitoolTool.getAllBlocks()).setFull3D().setUnlocalizedName("multitool_silk").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":multitool_claw");
		ModItems.multitool_ext = new ItemMultitoolPassive().setUnlocalizedName("multitool_ext").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":multitool_open");
		ModItems.multitool_miner = new ItemMultitoolPassive().setUnlocalizedName("multitool_miner").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":multitool_pointer");
		ModItems.multitool_beam = new ItemMultitoolPassive().setUnlocalizedName("multitool_beam").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":multitool_pointer");
		ModItems.multitool_sky = new ItemMultitoolPassive().setUnlocalizedName("multitool_sky").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":multitool_open");
		ModItems.multitool_mega = new ItemMultitoolPassive().setUnlocalizedName("multitool_mega").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":multitool_fist");
		ModItems.multitool_joule = new ItemMultitoolPassive().setUnlocalizedName("multitool_joule").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":multitool_fist");
		ModItems.multitool_decon = new ItemMultitoolPassive().setUnlocalizedName("multitool_decon").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":multitool_fist");

		ModItems.saw = new ModSword(MainRegistry.enumToolMaterialSaw).setUnlocalizedName("weapon_saw").setFull3D()
				.setTextureName(RefStrings.MODID + ":saw");
		ModItems.bat = new ModSword(MainRegistry.enumToolMaterialBat).setUnlocalizedName("weapon_bat").setFull3D()
				.setTextureName(RefStrings.MODID + ":bat");
		ModItems.bat_nail = new ModSword(MainRegistry.enumToolMaterialBatNail).setUnlocalizedName("weapon_bat_nail")
				.setFull3D().setTextureName(RefStrings.MODID + ":bat_nail");
		ModItems.golf_club = new ModSword(MainRegistry.enumToolMaterialGolfClub).setUnlocalizedName("weapon_golf_club")
				.setFull3D().setTextureName(RefStrings.MODID + ":golf_club");
		ModItems.pipe_rusty = new ModSword(MainRegistry.enumToolMaterialPipeRusty)
				.setUnlocalizedName("weapon_pipe_rusty").setFull3D().setTextureName(RefStrings.MODID + ":pipe_rusty");
		ModItems.pipe_lead = new ModSword(MainRegistry.enumToolMaterialPipeLead).setUnlocalizedName("weapon_pipe_lead")
				.setFull3D().setTextureName(RefStrings.MODID + ":pipe_lead");
		ModItems.reer_graar = new ModSword(MainRegistry.tMatTitan).setUnlocalizedName("reer_graar").setFull3D()
				.setTextureName(RefStrings.MODID + ":reer_graar_hd");
		ModItems.stopsign = new WeaponSpecial(MainRegistry.tMatAlloy).setUnlocalizedName("stopsign")
				.setTextureName(RefStrings.MODID + ":stopsign");
		ModItems.sopsign = new WeaponSpecial(MainRegistry.tMatAlloy).setUnlocalizedName("sopsign")
				.setTextureName(RefStrings.MODID + ":sopsign");
		ModItems.chernobylsign = new WeaponSpecial(MainRegistry.tMatAlloy).setUnlocalizedName("chernobylsign")
				.setTextureName(RefStrings.MODID + ":chernobylsign");

		ModItems.crystal_horn = new ItemCustomLore().setUnlocalizedName("crystal_horn")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_horn");
		ModItems.crystal_charred = new ItemCustomLore().setUnlocalizedName("crystal_charred")
				.setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_charred");

		ModItems.bucket_mud = new ItemModBucket(ModBlocks.mud_block).setUnlocalizedName("bucket_mud")
				.setContainerItem(Items.bucket).setCreativeTab(MainRegistry.blockTab)
				.setTextureName(RefStrings.MODID + ":bucket_mud");
		ModItems.bucket_acid = new ItemModBucket(ModBlocks.acid_block).setUnlocalizedName("bucket_acid")
				.setContainerItem(Items.bucket).setCreativeTab(MainRegistry.blockTab)
				.setTextureName(RefStrings.MODID + ":bucket_acid");
		ModItems.bucket_toxic = new ItemModBucket(ModBlocks.toxic_block).setUnlocalizedName("bucket_toxic")
				.setContainerItem(Items.bucket).setCreativeTab(MainRegistry.blockTab)
				.setTextureName(RefStrings.MODID + ":bucket_toxic");
		ModItems.bucket_schrabidic_acid = new ItemModBucket(ModBlocks.schrabidic_block)
				.setUnlocalizedName("bucket_schrabidic_acid").setContainerItem(Items.bucket)
				.setCreativeTab(MainRegistry.blockTab).setTextureName(RefStrings.MODID + ":bucket_schrabidic_acid");
		ModItems.bucket_sulfuric_acid = new ItemModBucket(ModBlocks.sulfuric_acid_block)
				.setUnlocalizedName("bucket_sulfuric_acid").setContainerItem(Items.bucket)
				.setCreativeTab(MainRegistry.blockTab).setTextureName(RefStrings.MODID + ":bucket_sulfuric_acid");

		ModItems.door_metal = new ItemModDoor().setUnlocalizedName("door_metal").setCreativeTab(MainRegistry.blockTab)
				.setTextureName(RefStrings.MODID + ":door_metal");
		ModItems.door_office = new ItemModDoor().setUnlocalizedName("door_office").setCreativeTab(MainRegistry.blockTab)
				.setTextureName(RefStrings.MODID + ":door_office");
		ModItems.door_bunker = new ItemModDoor().setUnlocalizedName("door_bunker").setCreativeTab(MainRegistry.blockTab)
				.setTextureName(RefStrings.MODID + ":door_bunker");

		ModItems.sliding_blast_door_skin = new ItemSlidingBlastDoorSkin().setUnlocalizedName("sliding_blast_door_skin")
				.setCreativeTab(CreativeTabs.tabMisc).setTextureName(RefStrings.MODID + ":sliding_blast_door_default");

		ModItems.record_lc = new ItemModRecord("lc").setUnlocalizedName("record_lc")
				.setCreativeTab(CreativeTabs.tabMisc).setTextureName(RefStrings.MODID + ":record_lc");
		ModItems.record_ss = new ItemModRecord("ss").setUnlocalizedName("record_ss")
				.setCreativeTab(CreativeTabs.tabMisc).setTextureName(RefStrings.MODID + ":record_ss");
		ModItems.record_vc = new ItemModRecord("vc").setUnlocalizedName("record_vc")
				.setCreativeTab(CreativeTabs.tabMisc).setTextureName(RefStrings.MODID + ":record_vc");
		ModItems.record_glass = new ItemModRecord("glass").setUnlocalizedName("record_glass").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":record_glass");

		ModItems.book_guide = new ItemGuideBook().setUnlocalizedName("book_guide")
				.setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":book_guide");
		ModItems.book_lore = new ItemBookLore().setUnlocalizedName("book_lore").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":book_pages");
		ModItems.holotape_image = new ItemHolotapeImage().setUnlocalizedName("holotape_image").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":holotape");
		ModItems.holotape_damaged = new Item().setUnlocalizedName("holotape_damaged").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":holotape_damaged");

		ModItems.polaroid = new ItemPolaroid().setUnlocalizedName("polaroid").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":polaroid_" + MainRegistry.polaroidID);
		ModItems.glitch = new ItemGlitch().setUnlocalizedName("glitch").setMaxStackSize(1)
				.setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":glitch_" + MainRegistry.polaroidID);
		ModItems.letter = new ItemStarterKit().setUnlocalizedName("letter").setCreativeTab(MainRegistry.consumableTab)
				.setTextureName(RefStrings.MODID + ":letter");
		ModItems.book_secret = new ItemCustomLore().setUnlocalizedName("book_secret")
				.setCreativeTab(MainRegistry.polaroidID == 11 ? MainRegistry.consumableTab : null)
				.setTextureName(RefStrings.MODID + ":book_secret");
		ModItems.book_of_ = new ItemBook().setUnlocalizedName("book_of_").setMaxStackSize(1).setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":book_of_");
		ModItems.burnt_bark = new ItemCustomLore().setUnlocalizedName("burnt_bark").setCreativeTab(null)
				.setTextureName(RefStrings.MODID + ":burnt_bark");

		ModItems.smoke1 = new Item().setUnlocalizedName("smoke1").setTextureName(RefStrings.MODID + ":smoke1");
		ModItems.smoke2 = new Item().setUnlocalizedName("smoke2").setTextureName(RefStrings.MODID + ":smoke2");
		ModItems.smoke3 = new Item().setUnlocalizedName("smoke3").setTextureName(RefStrings.MODID + ":smoke3");
		ModItems.smoke4 = new Item().setUnlocalizedName("smoke4").setTextureName(RefStrings.MODID + ":smoke4");
		ModItems.smoke5 = new Item().setUnlocalizedName("smoke5").setTextureName(RefStrings.MODID + ":smoke5");
		ModItems.smoke6 = new Item().setUnlocalizedName("smoke6").setTextureName(RefStrings.MODID + ":smoke6");
		ModItems.smoke7 = new Item().setUnlocalizedName("smoke7").setTextureName(RefStrings.MODID + ":smoke7");
		ModItems.smoke8 = new Item().setUnlocalizedName("smoke8").setTextureName(RefStrings.MODID + ":smoke8");
		ModItems.b_smoke1 = new Item().setUnlocalizedName("b_smoke1").setTextureName(RefStrings.MODID + ":b_smoke1");
		ModItems.b_smoke2 = new Item().setUnlocalizedName("b_smoke2").setTextureName(RefStrings.MODID + ":b_smoke2");
		ModItems.b_smoke3 = new Item().setUnlocalizedName("b_smoke3").setTextureName(RefStrings.MODID + ":b_smoke3");
		ModItems.b_smoke4 = new Item().setUnlocalizedName("b_smoke4").setTextureName(RefStrings.MODID + ":b_smoke4");
		ModItems.b_smoke5 = new Item().setUnlocalizedName("b_smoke5").setTextureName(RefStrings.MODID + ":b_smoke5");
		ModItems.b_smoke6 = new Item().setUnlocalizedName("b_smoke6").setTextureName(RefStrings.MODID + ":b_smoke6");
		ModItems.b_smoke7 = new Item().setUnlocalizedName("b_smoke7").setTextureName(RefStrings.MODID + ":b_smoke7");
		ModItems.b_smoke8 = new Item().setUnlocalizedName("b_smoke8").setTextureName(RefStrings.MODID + ":b_smoke8");
		ModItems.d_smoke1 = new Item().setUnlocalizedName("d_smoke1").setTextureName(RefStrings.MODID + ":d_smoke1");
		ModItems.d_smoke2 = new Item().setUnlocalizedName("d_smoke2").setTextureName(RefStrings.MODID + ":d_smoke2");
		ModItems.d_smoke3 = new Item().setUnlocalizedName("d_smoke3").setTextureName(RefStrings.MODID + ":d_smoke3");
		ModItems.d_smoke4 = new Item().setUnlocalizedName("d_smoke4").setTextureName(RefStrings.MODID + ":d_smoke4");
		ModItems.d_smoke5 = new Item().setUnlocalizedName("d_smoke5").setTextureName(RefStrings.MODID + ":d_smoke5");
		ModItems.d_smoke6 = new Item().setUnlocalizedName("d_smoke6").setTextureName(RefStrings.MODID + ":d_smoke6");
		ModItems.d_smoke7 = new Item().setUnlocalizedName("d_smoke7").setTextureName(RefStrings.MODID + ":d_smoke7");
		ModItems.d_smoke8 = new Item().setUnlocalizedName("d_smoke8").setTextureName(RefStrings.MODID + ":d_smoke8");
		ModItems.spill1 = new Item().setUnlocalizedName("spill1").setTextureName(RefStrings.MODID + ":spill1");
		ModItems.spill2 = new Item().setUnlocalizedName("spill2").setTextureName(RefStrings.MODID + ":spill2");
		ModItems.spill3 = new Item().setUnlocalizedName("spill3").setTextureName(RefStrings.MODID + ":spill3");
		ModItems.spill4 = new Item().setUnlocalizedName("spill4").setTextureName(RefStrings.MODID + ":spill4");
		ModItems.spill5 = new Item().setUnlocalizedName("spill5").setTextureName(RefStrings.MODID + ":spill5");
		ModItems.spill6 = new Item().setUnlocalizedName("spill6").setTextureName(RefStrings.MODID + ":spill6");
		ModItems.spill7 = new Item().setUnlocalizedName("spill7").setTextureName(RefStrings.MODID + ":spill7");
		ModItems.spill8 = new Item().setUnlocalizedName("spill8").setTextureName(RefStrings.MODID + ":spill8");
		ModItems.gas1 = new Item().setUnlocalizedName("gas1").setTextureName(RefStrings.MODID + ":gas1");
		ModItems.gas2 = new Item().setUnlocalizedName("gas2").setTextureName(RefStrings.MODID + ":gas2");
		ModItems.gas3 = new Item().setUnlocalizedName("gas3").setTextureName(RefStrings.MODID + ":gas3");
		ModItems.gas4 = new Item().setUnlocalizedName("gas4").setTextureName(RefStrings.MODID + ":gas4");
		ModItems.gas5 = new Item().setUnlocalizedName("gas5").setTextureName(RefStrings.MODID + ":gas5");
		ModItems.gas6 = new Item().setUnlocalizedName("gas6").setTextureName(RefStrings.MODID + ":gas6");
		ModItems.gas7 = new Item().setUnlocalizedName("gas7").setTextureName(RefStrings.MODID + ":gas7");
		ModItems.gas8 = new Item().setUnlocalizedName("gas8").setTextureName(RefStrings.MODID + ":gas8");
		ModItems.chlorine1 = new Item().setUnlocalizedName("chlorine1").setTextureName(RefStrings.MODID + ":chlorine1");
		ModItems.chlorine2 = new Item().setUnlocalizedName("chlorine2").setTextureName(RefStrings.MODID + ":chlorine2");
		ModItems.chlorine3 = new Item().setUnlocalizedName("chlorine3").setTextureName(RefStrings.MODID + ":chlorine3");
		ModItems.chlorine4 = new Item().setUnlocalizedName("chlorine4").setTextureName(RefStrings.MODID + ":chlorine4");
		ModItems.chlorine5 = new Item().setUnlocalizedName("chlorine5").setTextureName(RefStrings.MODID + ":chlorine5");
		ModItems.chlorine6 = new Item().setUnlocalizedName("chlorine6").setTextureName(RefStrings.MODID + ":chlorine6");
		ModItems.chlorine7 = new Item().setUnlocalizedName("chlorine7").setTextureName(RefStrings.MODID + ":chlorine7");
		ModItems.chlorine8 = new Item().setUnlocalizedName("chlorine8").setTextureName(RefStrings.MODID + ":chlorine8");
		ModItems.pc1 = new Item().setUnlocalizedName("pc1").setTextureName(RefStrings.MODID + ":pc1");
		ModItems.pc2 = new Item().setUnlocalizedName("pc2").setTextureName(RefStrings.MODID + ":pc2");
		ModItems.pc3 = new Item().setUnlocalizedName("pc3").setTextureName(RefStrings.MODID + ":pc3");
		ModItems.pc4 = new Item().setUnlocalizedName("pc4").setTextureName(RefStrings.MODID + ":pc4");
		ModItems.pc5 = new Item().setUnlocalizedName("pc5").setTextureName(RefStrings.MODID + ":pc5");
		ModItems.pc6 = new Item().setUnlocalizedName("pc6").setTextureName(RefStrings.MODID + ":pc6");
		ModItems.pc7 = new Item().setUnlocalizedName("pc7").setTextureName(RefStrings.MODID + ":pc7");
		ModItems.pc8 = new Item().setUnlocalizedName("pc8").setTextureName(RefStrings.MODID + ":pc8");
		ModItems.cloud1 = new Item().setUnlocalizedName("cloud1").setTextureName(RefStrings.MODID + ":cloud1");
		ModItems.cloud2 = new Item().setUnlocalizedName("cloud2").setTextureName(RefStrings.MODID + ":cloud2");
		ModItems.cloud3 = new Item().setUnlocalizedName("cloud3").setTextureName(RefStrings.MODID + ":cloud3");
		ModItems.cloud4 = new Item().setUnlocalizedName("cloud4").setTextureName(RefStrings.MODID + ":cloud4");
		ModItems.cloud5 = new Item().setUnlocalizedName("cloud5").setTextureName(RefStrings.MODID + ":cloud5");
		ModItems.cloud6 = new Item().setUnlocalizedName("cloud6").setTextureName(RefStrings.MODID + ":cloud6");
		ModItems.cloud7 = new Item().setUnlocalizedName("cloud7").setTextureName(RefStrings.MODID + ":cloud7");
		ModItems.cloud8 = new Item().setUnlocalizedName("cloud8").setTextureName(RefStrings.MODID + ":cloud8");
		ModItems.orange1 = new Item().setUnlocalizedName("orange1").setTextureName(RefStrings.MODID + ":orange1");
		ModItems.orange2 = new Item().setUnlocalizedName("orange2").setTextureName(RefStrings.MODID + ":orange2");
		ModItems.orange3 = new Item().setUnlocalizedName("orange3").setTextureName(RefStrings.MODID + ":orange3");
		ModItems.orange4 = new Item().setUnlocalizedName("orange4").setTextureName(RefStrings.MODID + ":orange4");
		ModItems.orange5 = new Item().setUnlocalizedName("orange5").setTextureName(RefStrings.MODID + ":orange5");
		ModItems.orange6 = new Item().setUnlocalizedName("orange6").setTextureName(RefStrings.MODID + ":orange6");
		ModItems.orange7 = new Item().setUnlocalizedName("orange7").setTextureName(RefStrings.MODID + ":orange7");
		ModItems.orange8 = new Item().setUnlocalizedName("orange8").setTextureName(RefStrings.MODID + ":orange8");
		ModItems.energy_ball = new Item().setUnlocalizedName("energy_ball")
				.setTextureName(RefStrings.MODID + ":energy_ball");
		ModItems.discharge = new Item().setUnlocalizedName("discharge").setTextureName(RefStrings.MODID + ":discharge");
		ModItems.empblast = new Item().setUnlocalizedName("empblast").setTextureName(RefStrings.MODID + ":empblast");
		ModItems.flame_1 = new Item().setUnlocalizedName("flame_1").setTextureName(RefStrings.MODID + ":flame_1");
		ModItems.flame_2 = new Item().setUnlocalizedName("flame_2").setTextureName(RefStrings.MODID + ":flame_2");
		ModItems.flame_3 = new Item().setUnlocalizedName("flame_3").setTextureName(RefStrings.MODID + ":flame_3");
		ModItems.flame_4 = new Item().setUnlocalizedName("flame_4").setTextureName(RefStrings.MODID + ":flame_4");
		ModItems.flame_5 = new Item().setUnlocalizedName("flame_5").setTextureName(RefStrings.MODID + ":flame_5");
		ModItems.flame_6 = new Item().setUnlocalizedName("flame_6").setTextureName(RefStrings.MODID + ":flame_6");
		ModItems.flame_7 = new Item().setUnlocalizedName("flame_7").setTextureName(RefStrings.MODID + ":flame_7");
		ModItems.flame_8 = new Item().setUnlocalizedName("flame_8").setTextureName(RefStrings.MODID + ":flame_8");
		ModItems.flame_9 = new Item().setUnlocalizedName("flame_9").setTextureName(RefStrings.MODID + ":flame_9");
		ModItems.flame_10 = new Item().setUnlocalizedName("flame_10").setTextureName(RefStrings.MODID + ":flame_10");
		ModItems.ln2_1 = new Item().setUnlocalizedName("ln2_1").setTextureName(RefStrings.MODID + ":ln2_1");
		ModItems.ln2_2 = new Item().setUnlocalizedName("ln2_2").setTextureName(RefStrings.MODID + ":ln2_2");
		ModItems.ln2_3 = new Item().setUnlocalizedName("ln2_3").setTextureName(RefStrings.MODID + ":ln2_3");
		ModItems.ln2_4 = new Item().setUnlocalizedName("ln2_4").setTextureName(RefStrings.MODID + ":ln2_4");
		ModItems.ln2_5 = new Item().setUnlocalizedName("ln2_5").setTextureName(RefStrings.MODID + ":ln2_5");
		ModItems.ln2_6 = new Item().setUnlocalizedName("ln2_6").setTextureName(RefStrings.MODID + ":ln2_6");
		ModItems.ln2_7 = new Item().setUnlocalizedName("ln2_7").setTextureName(RefStrings.MODID + ":ln2_7");
		ModItems.ln2_8 = new Item().setUnlocalizedName("ln2_8").setTextureName(RefStrings.MODID + ":ln2_8");
		ModItems.ln2_9 = new Item().setUnlocalizedName("ln2_9").setTextureName(RefStrings.MODID + ":ln2_9");
		ModItems.ln2_10 = new Item().setUnlocalizedName("ln2_10").setTextureName(RefStrings.MODID + ":ln2_10");
		ModItems.nothing = new Item().setUnlocalizedName("nothing").setTextureName(RefStrings.MODID + ":nothing");
		ModItems.void_anim = new Item().setUnlocalizedName("void_anim").setTextureName(RefStrings.MODID + ":void_anim");

		ModItems.achievement_icon = new ItemEnumMulti(ItemEnums.EnumAchievementType.class, true, true)
				.setUnlocalizedName("achievement_icon");
		ModItems.bob_metalworks = new Item().setUnlocalizedName("bob_metalworks")
				.setTextureName(RefStrings.MODID + ":bob_metalworks");
		ModItems.bob_assembly = new Item().setUnlocalizedName("bob_assembly")
				.setTextureName(RefStrings.MODID + ":bob_assembly");
		ModItems.bob_chemistry = new Item().setUnlocalizedName("bob_chemistry")
				.setTextureName(RefStrings.MODID + ":bob_chemistry");
		ModItems.bob_oil = new Item().setUnlocalizedName("bob_oil").setTextureName(RefStrings.MODID + ":bob_oil");
		ModItems.bob_nuclear = new Item().setUnlocalizedName("bob_nuclear")
				.setTextureName(RefStrings.MODID + ":bob_nuclear");

		ModItems.mysteryshovel = new ItemMS().setUnlocalizedName("mysteryshovel").setFull3D().setMaxStackSize(1)
				.setTextureName(RefStrings.MODID + ":cursed_shovel");
		ModItems.memory = new ItemBattery(Long.MAX_VALUE / 100L, 100000000000000L, 100000000000000L)
				.setUnlocalizedName("memory").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":mo8_anim");

		FluidContainerRegistry.registerFluidContainer(new FluidStack(ModBlocks.mud_fluid, 1000),
				new ItemStack(ModItems.bucket_mud), new ItemStack(Items.bucket));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(ModBlocks.acid_fluid, 1000),
				new ItemStack(ModItems.bucket_acid), new ItemStack(Items.bucket));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(ModBlocks.toxic_fluid, 1000),
				new ItemStack(ModItems.bucket_toxic), new ItemStack(Items.bucket));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(ModBlocks.schrabidic_fluid, 1000),
				new ItemStack(ModItems.bucket_schrabidic_acid), new ItemStack(Items.bucket));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(ModBlocks.sulfuric_acid_fluid, 1000),
				new ItemStack(ModItems.bucket_sulfuric_acid), new ItemStack(Items.bucket));
		BucketHandler.INSTANCE.buckets.put(ModBlocks.mud_block, ModItems.bucket_mud);
		BucketHandler.INSTANCE.buckets.put(ModBlocks.acid_block, ModItems.bucket_acid);
		BucketHandler.INSTANCE.buckets.put(ModBlocks.toxic_block, ModItems.bucket_toxic);
		BucketHandler.INSTANCE.buckets.put(ModBlocks.schrabidic_block, ModItems.bucket_schrabidic_acid);
		BucketHandler.INSTANCE.buckets.put(ModBlocks.sulfuric_acid_block, ModItems.bucket_sulfuric_acid);
		MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);
	}
	
	// FIXME Delete duplicate entries
	private static void registerItem() {
		// Weapons
		GameRegistry.registerItem(ModItems.redstone_sword, ModItems.redstone_sword.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.big_sword, ModItems.big_sword.getUnlocalizedName());

		// Test Armor
		GameRegistry.registerItem(ModItems.test_helmet, ModItems.test_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.test_chestplate, ModItems.test_chestplate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.test_leggings, ModItems.test_leggings.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.test_boots, ModItems.test_boots.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cape_test, ModItems.cape_test.getUnlocalizedName());

		// Test Nuke
		GameRegistry.registerItem(ModItems.test_nuke_igniter, ModItems.test_nuke_igniter.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.test_nuke_propellant, ModItems.test_nuke_propellant.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.test_nuke_tier1_shielding,
				ModItems.test_nuke_tier1_shielding.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.test_nuke_tier2_shielding,
				ModItems.test_nuke_tier2_shielding.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.test_nuke_tier1_bullet,
				ModItems.test_nuke_tier1_bullet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.test_nuke_tier2_bullet,
				ModItems.test_nuke_tier2_bullet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.test_nuke_tier1_target,
				ModItems.test_nuke_tier1_target.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.test_nuke_tier2_target,
				ModItems.test_nuke_tier2_target.getUnlocalizedName());

		// Ingots
		GameRegistry.registerItem(ModItems.ingot_uranium, ModItems.ingot_uranium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_u233, ModItems.ingot_u233.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_u235, ModItems.ingot_u235.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_u238, ModItems.ingot_u238.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_u238m2, ModItems.ingot_u238m2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_th232, ModItems.ingot_th232.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_plutonium, ModItems.ingot_plutonium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_pu238, ModItems.ingot_pu238.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_pu239, ModItems.ingot_pu239.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_pu240, ModItems.ingot_pu240.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_pu241, ModItems.ingot_pu241.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_pu_mix, ModItems.ingot_pu_mix.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_am241, ModItems.ingot_am241.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_am242, ModItems.ingot_am242.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_am_mix, ModItems.ingot_am_mix.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_neptunium, ModItems.ingot_neptunium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_polonium, ModItems.ingot_polonium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_technetium, ModItems.ingot_technetium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_co60, ModItems.ingot_co60.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_sr90, ModItems.ingot_sr90.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_au198, ModItems.ingot_au198.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_pb209, ModItems.ingot_pb209.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_ra226, ModItems.ingot_ra226.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_titanium, ModItems.ingot_titanium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_copper, ModItems.ingot_copper.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_red_copper, ModItems.ingot_red_copper.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_advanced_alloy, ModItems.ingot_advanced_alloy.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_tungsten, ModItems.ingot_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_aluminium, ModItems.ingot_aluminium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_steel, ModItems.ingot_steel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_tcalloy, ModItems.ingot_tcalloy.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_cdalloy, ModItems.ingot_cdalloy.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_lead, ModItems.ingot_lead.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_bismuth, ModItems.ingot_bismuth.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_arsenic, ModItems.ingot_arsenic.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_calcium, ModItems.ingot_calcium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_cadmium, ModItems.ingot_cadmium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_tantalium, ModItems.ingot_tantalium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_niobium, ModItems.ingot_niobium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_beryllium, ModItems.ingot_beryllium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_cobalt, ModItems.ingot_cobalt.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_boron, ModItems.ingot_boron.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_graphite, ModItems.ingot_graphite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_firebrick, ModItems.ingot_firebrick.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_dura_steel, ModItems.ingot_dura_steel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_polymer, ModItems.ingot_polymer.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_bakelite, ModItems.ingot_bakelite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_rubber, ModItems.ingot_rubber.getUnlocalizedName());
		// GameRegistry.registerItem(ingot_pet, ingot_pet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_pc, ModItems.ingot_pc.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_pvc, ModItems.ingot_pvc.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_mud, ModItems.ingot_mud.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_cft, ModItems.ingot_cft.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_schraranium, ModItems.ingot_schraranium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_schrabidium, ModItems.ingot_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_schrabidate, ModItems.ingot_schrabidate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_magnetized_tungsten,
				ModItems.ingot_magnetized_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_combine_steel, ModItems.ingot_combine_steel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_solinium, ModItems.ingot_solinium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_gh336, ModItems.ingot_gh336.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_uranium_fuel, ModItems.ingot_uranium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_thorium_fuel, ModItems.ingot_thorium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_plutonium_fuel, ModItems.ingot_plutonium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_neptunium_fuel, ModItems.ingot_neptunium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_mox_fuel, ModItems.ingot_mox_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_americium_fuel, ModItems.ingot_americium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_schrabidium_fuel,
				ModItems.ingot_schrabidium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_hes, ModItems.ingot_hes.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_les, ModItems.ingot_les.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_australium, ModItems.ingot_australium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_weidanium, ModItems.ingot_weidanium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_reiium, ModItems.ingot_reiium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_unobtainium, ModItems.ingot_unobtainium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_daffergon, ModItems.ingot_daffergon.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_verticium, ModItems.ingot_verticium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_lanthanium, ModItems.ingot_lanthanium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_actinium, ModItems.ingot_actinium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_desh, ModItems.ingot_desh.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_ferrouranium, ModItems.ingot_ferrouranium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_starmetal, ModItems.ingot_starmetal.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_saturnite, ModItems.ingot_saturnite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_euphemium, ModItems.ingot_euphemium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_dineutronium, ModItems.ingot_dineutronium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_electronium, ModItems.ingot_electronium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_smore, ModItems.ingot_smore.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_osmiridium, ModItems.ingot_osmiridium.getUnlocalizedName());

		// Meteorite Ingots
		GameRegistry.registerItem(ModItems.ingot_steel_dusted, ModItems.ingot_steel_dusted.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_chainsteel, ModItems.ingot_chainsteel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_meteorite, ModItems.ingot_meteorite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_meteorite_forged,
				ModItems.ingot_meteorite_forged.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.blade_meteorite, ModItems.blade_meteorite.getUnlocalizedName());
		
		// Misc Ingots
		GameRegistry.registerItem(ModItems.ingot_phosphorus, ModItems.ingot_phosphorus.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.lithium, ModItems.lithium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_zirconium, ModItems.ingot_zirconium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_semtex, ModItems.ingot_semtex.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_c4, ModItems.ingot_c4.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.oil_tar, ModItems.oil_tar.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.solid_fuel, ModItems.solid_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.solid_fuel_presto, ModItems.solid_fuel_presto.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.solid_fuel_presto_triplet,
				ModItems.solid_fuel_presto_triplet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.solid_fuel_bf, ModItems.solid_fuel_bf.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.solid_fuel_presto_bf, ModItems.solid_fuel_presto_bf.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.solid_fuel_presto_triplet_bf,
				ModItems.solid_fuel_presto_triplet_bf.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rocket_fuel, ModItems.rocket_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_fiberglass, ModItems.ingot_fiberglass.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_asbestos, ModItems.ingot_asbestos.getUnlocalizedName());
		
		//Plates
		GameRegistry.registerItem(plate_iron, plate_iron.getUnlocalizedName());
		GameRegistry.registerItem(plate_gold, plate_gold.getUnlocalizedName());
		GameRegistry.registerItem(plate_titanium, plate_titanium.getUnlocalizedName());
		GameRegistry.registerItem(plate_aluminium, plate_aluminium.getUnlocalizedName());
		GameRegistry.registerItem(plate_steel, plate_steel.getUnlocalizedName());
		GameRegistry.registerItem(plate_lead, plate_lead.getUnlocalizedName());
		GameRegistry.registerItem(plate_copper, plate_copper.getUnlocalizedName());
		GameRegistry.registerItem(plate_advanced_alloy, plate_advanced_alloy.getUnlocalizedName());
		GameRegistry.registerItem(neutron_reflector, neutron_reflector.getUnlocalizedName());
		GameRegistry.registerItem(plate_schrabidium, plate_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(plate_combine_steel, plate_combine_steel.getUnlocalizedName());
		GameRegistry.registerItem(plate_mixed, plate_mixed.getUnlocalizedName());
		GameRegistry.registerItem(plate_saturnite, plate_saturnite.getUnlocalizedName());
		GameRegistry.registerItem(plate_paa, plate_paa.getUnlocalizedName());
		GameRegistry.registerItem(plate_polymer, plate_polymer.getUnlocalizedName());
		GameRegistry.registerItem(plate_kevlar, plate_kevlar.getUnlocalizedName());
		GameRegistry.registerItem(plate_dalekanium, plate_dalekanium.getUnlocalizedName());
		GameRegistry.registerItem(plate_desh, plate_desh.getUnlocalizedName());
		GameRegistry.registerItem(plate_bismuth, plate_bismuth.getUnlocalizedName());
		GameRegistry.registerItem(plate_euphemium, plate_euphemium.getUnlocalizedName());
		GameRegistry.registerItem(plate_dineutronium, plate_dineutronium.getUnlocalizedName());
		
		//Armor Plates
		GameRegistry.registerItem(plate_armor_titanium, plate_armor_titanium.getUnlocalizedName());
		GameRegistry.registerItem(plate_armor_ajr, plate_armor_ajr.getUnlocalizedName());
		GameRegistry.registerItem(plate_armor_hev, plate_armor_hev.getUnlocalizedName());
		GameRegistry.registerItem(plate_armor_lunar, plate_armor_lunar.getUnlocalizedName());
		GameRegistry.registerItem(plate_armor_fau, plate_armor_fau.getUnlocalizedName());
		GameRegistry.registerItem(plate_armor_dnt, plate_armor_dnt.getUnlocalizedName());
		
		//Heavy/Cast Plate
		GameRegistry.registerItem(plate_cast, plate_cast.getUnlocalizedName());
		GameRegistry.registerItem(plate_welded, plate_welded.getUnlocalizedName());
		GameRegistry.registerItem(heavy_component, heavy_component.getUnlocalizedName());
		
		//Boards
		GameRegistry.registerItem(board_copper, board_copper.getUnlocalizedName());
		
		//Bolts
		GameRegistry.registerItem(bolt_dura_steel, bolt_dura_steel.getUnlocalizedName());
		GameRegistry.registerItem(bolt_tungsten, bolt_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(bolt_compound, bolt_compound.getUnlocalizedName());
		
		//Cloth
		GameRegistry.registerItem(hazmat_cloth, hazmat_cloth.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_cloth_red, hazmat_cloth_red.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_cloth_grey, hazmat_cloth_grey.getUnlocalizedName());
		GameRegistry.registerItem(asbestos_cloth, asbestos_cloth.getUnlocalizedName());
		GameRegistry.registerItem(rag, rag.getUnlocalizedName());
		GameRegistry.registerItem(rag_damp, rag_damp.getUnlocalizedName());
		GameRegistry.registerItem(rag_piss, rag_piss.getUnlocalizedName());
		GameRegistry.registerItem(filter_coal, filter_coal.getUnlocalizedName());
		
		//Wires
		GameRegistry.registerItem(wire_aluminium, wire_aluminium.getUnlocalizedName());
		GameRegistry.registerItem(wire_copper, wire_copper.getUnlocalizedName());
		GameRegistry.registerItem(wire_tungsten, wire_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(wire_red_copper, wire_red_copper.getUnlocalizedName());
		GameRegistry.registerItem(wire_advanced_alloy, wire_advanced_alloy.getUnlocalizedName());
		GameRegistry.registerItem(wire_gold, wire_gold.getUnlocalizedName());
		GameRegistry.registerItem(wire_schrabidium, wire_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(wire_magnetized_tungsten, wire_magnetized_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(wire_dense, wire_dense.getUnlocalizedName());
		
		//Parts
		GameRegistry.registerItem(coil_copper, coil_copper.getUnlocalizedName());
		GameRegistry.registerItem(coil_copper_torus, coil_copper_torus.getUnlocalizedName());
		GameRegistry.registerItem(coil_advanced_alloy, coil_advanced_alloy.getUnlocalizedName());
		GameRegistry.registerItem(coil_advanced_torus, coil_advanced_torus.getUnlocalizedName());
		GameRegistry.registerItem(coil_gold, coil_gold.getUnlocalizedName());
		GameRegistry.registerItem(coil_gold_torus, coil_gold_torus.getUnlocalizedName());
		GameRegistry.registerItem(coil_tungsten, coil_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(coil_magnetized_tungsten, coil_magnetized_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(safety_fuse, safety_fuse.getUnlocalizedName());
		GameRegistry.registerItem(tank_steel, tank_steel.getUnlocalizedName());
		GameRegistry.registerItem(motor, motor.getUnlocalizedName());
		GameRegistry.registerItem(motor_desh, motor_desh.getUnlocalizedName());
		GameRegistry.registerItem(motor_bismuth, motor_bismuth.getUnlocalizedName());
		GameRegistry.registerItem(centrifuge_element, centrifuge_element.getUnlocalizedName());
		//GameRegistry.registerItem(centrifuge_tower, centrifuge_tower.getUnlocalizedName());
		//GameRegistry.registerItem(magnet_dee, magnet_dee.getUnlocalizedName());
		GameRegistry.registerItem(magnet_circular, magnet_circular.getUnlocalizedName());
		//GameRegistry.registerItem(cyclotron_tower, cyclotron_tower.getUnlocalizedName());
		GameRegistry.registerItem(reactor_core, reactor_core.getUnlocalizedName());
		GameRegistry.registerItem(rtg_unit, rtg_unit.getUnlocalizedName());
		//GameRegistry.registerItem(thermo_unit_empty, thermo_unit_empty.getUnlocalizedName());
		//GameRegistry.registerItem(thermo_unit_endo, thermo_unit_endo.getUnlocalizedName());
		//GameRegistry.registerItem(thermo_unit_exo, thermo_unit_exo.getUnlocalizedName());
		GameRegistry.registerItem(levitation_unit, levitation_unit.getUnlocalizedName());
		GameRegistry.registerItem(pipes_steel, pipes_steel.getUnlocalizedName());
		GameRegistry.registerItem(drill_titanium, drill_titanium.getUnlocalizedName());
		GameRegistry.registerItem(photo_panel, photo_panel.getUnlocalizedName());
		GameRegistry.registerItem(chlorine_pinwheel, chlorine_pinwheel.getUnlocalizedName());
		GameRegistry.registerItem(ring_starmetal, ring_starmetal.getUnlocalizedName());
		GameRegistry.registerItem(deuterium_filter, deuterium_filter.getUnlocalizedName());
		GameRegistry.registerItem(chemical_dye, chemical_dye.getUnlocalizedName());
		GameRegistry.registerItem(crayon, crayon.getUnlocalizedName());
		GameRegistry.registerItem(part_generic, part_generic.getUnlocalizedName());
		GameRegistry.registerItem(parts_legendary, parts_legendary.getUnlocalizedName());
		GameRegistry.registerItem(gear_large, gear_large.getUnlocalizedName());
		GameRegistry.registerItem(sawblade, sawblade.getUnlocalizedName());
		
		//Plant Products
		GameRegistry.registerItem(plant_item, plant_item.getUnlocalizedName());
		
		//Teleporter Parts
		//GameRegistry.registerItem(telepad, telepad.getUnlocalizedName());
		GameRegistry.registerItem(entanglement_kit, entanglement_kit.getUnlocalizedName());
		
		//AMS Parts
		GameRegistry.registerItem(component_limiter, component_limiter.getUnlocalizedName());
		GameRegistry.registerItem(component_emitter, component_emitter.getUnlocalizedName());
		
		//Bomb Parts
		GameRegistry.registerItem(hull_small_steel, hull_small_steel.getUnlocalizedName());
		GameRegistry.registerItem(hull_small_aluminium, hull_small_aluminium.getUnlocalizedName());
		GameRegistry.registerItem(hull_big_steel, hull_big_steel.getUnlocalizedName());
		GameRegistry.registerItem(hull_big_aluminium, hull_big_aluminium.getUnlocalizedName());
		GameRegistry.registerItem(hull_big_titanium, hull_big_titanium.getUnlocalizedName());
		GameRegistry.registerItem(fins_flat, fins_flat.getUnlocalizedName());
		GameRegistry.registerItem(fins_small_steel, fins_small_steel.getUnlocalizedName());
		GameRegistry.registerItem(fins_big_steel, fins_big_steel.getUnlocalizedName());
		GameRegistry.registerItem(fins_tri_steel, fins_tri_steel.getUnlocalizedName());
		GameRegistry.registerItem(fins_quad_titanium, fins_quad_titanium.getUnlocalizedName());
		GameRegistry.registerItem(sphere_steel, sphere_steel.getUnlocalizedName());
		GameRegistry.registerItem(pedestal_steel, pedestal_steel.getUnlocalizedName());
		GameRegistry.registerItem(dysfunctional_reactor, dysfunctional_reactor.getUnlocalizedName());
		GameRegistry.registerItem(rotor_steel, rotor_steel.getUnlocalizedName());
		GameRegistry.registerItem(generator_steel, generator_steel.getUnlocalizedName());
		GameRegistry.registerItem(blade_titanium, blade_titanium.getUnlocalizedName());
		GameRegistry.registerItem(blade_tungsten, blade_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(turbine_titanium, turbine_titanium.getUnlocalizedName());
		GameRegistry.registerItem(turbine_tungsten, turbine_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(flywheel_beryllium, flywheel_beryllium.getUnlocalizedName());
		GameRegistry.registerItem(generator_front, generator_front.getUnlocalizedName());
		GameRegistry.registerItem(toothpicks, toothpicks.getUnlocalizedName());
		GameRegistry.registerItem(ducttape, ducttape.getUnlocalizedName());
		GameRegistry.registerItem(catalyst_clay, catalyst_clay.getUnlocalizedName());
		GameRegistry.registerItem(missile_assembly, missile_assembly.getUnlocalizedName());
		GameRegistry.registerItem(warhead_generic_small, warhead_generic_small.getUnlocalizedName());
		GameRegistry.registerItem(warhead_generic_medium, warhead_generic_medium.getUnlocalizedName());
		GameRegistry.registerItem(warhead_generic_large, warhead_generic_large.getUnlocalizedName());
		GameRegistry.registerItem(warhead_incendiary_small, warhead_incendiary_small.getUnlocalizedName());
		GameRegistry.registerItem(warhead_incendiary_medium, warhead_incendiary_medium.getUnlocalizedName());
		GameRegistry.registerItem(warhead_incendiary_large, warhead_incendiary_large.getUnlocalizedName());
		GameRegistry.registerItem(warhead_cluster_small, warhead_cluster_small.getUnlocalizedName());
		GameRegistry.registerItem(warhead_cluster_medium, warhead_cluster_medium.getUnlocalizedName());
		GameRegistry.registerItem(warhead_cluster_large, warhead_cluster_large.getUnlocalizedName());
		GameRegistry.registerItem(warhead_buster_small, warhead_buster_small.getUnlocalizedName());
		GameRegistry.registerItem(warhead_buster_medium, warhead_buster_medium.getUnlocalizedName());
		GameRegistry.registerItem(warhead_buster_large, warhead_buster_large.getUnlocalizedName());
		GameRegistry.registerItem(warhead_nuclear, warhead_nuclear.getUnlocalizedName());
		GameRegistry.registerItem(warhead_mirvlet, warhead_mirvlet.getUnlocalizedName());
		GameRegistry.registerItem(warhead_mirv, warhead_mirv.getUnlocalizedName());
		GameRegistry.registerItem(warhead_volcano, warhead_volcano.getUnlocalizedName());
		GameRegistry.registerItem(warhead_thermo_endo, warhead_thermo_endo.getUnlocalizedName());
		GameRegistry.registerItem(warhead_thermo_exo, warhead_thermo_exo.getUnlocalizedName());
		GameRegistry.registerItem(fuel_tank_small, fuel_tank_small.getUnlocalizedName());
		GameRegistry.registerItem(fuel_tank_medium, fuel_tank_medium.getUnlocalizedName());
		GameRegistry.registerItem(fuel_tank_large, fuel_tank_large.getUnlocalizedName());
		GameRegistry.registerItem(thruster_small, thruster_small.getUnlocalizedName());
		GameRegistry.registerItem(thruster_medium, thruster_medium.getUnlocalizedName());
		GameRegistry.registerItem(thruster_large, thruster_large.getUnlocalizedName());
		GameRegistry.registerItem(thruster_nuclear, thruster_nuclear.getUnlocalizedName());
		GameRegistry.registerItem(sat_base, sat_base.getUnlocalizedName());
		GameRegistry.registerItem(sat_head_mapper, sat_head_mapper.getUnlocalizedName());
		GameRegistry.registerItem(sat_head_scanner, sat_head_scanner.getUnlocalizedName());
		GameRegistry.registerItem(sat_head_radar, sat_head_radar.getUnlocalizedName());
		GameRegistry.registerItem(sat_head_laser, sat_head_laser.getUnlocalizedName());
		GameRegistry.registerItem(sat_head_resonator, sat_head_resonator.getUnlocalizedName());
		GameRegistry.registerItem(seg_10, seg_10.getUnlocalizedName());
		GameRegistry.registerItem(seg_15, seg_15.getUnlocalizedName());
		GameRegistry.registerItem(seg_20, seg_20.getUnlocalizedName());
		
		//Hammer Parts
		GameRegistry.registerItem(shimmer_head, shimmer_head.getUnlocalizedName());
		GameRegistry.registerItem(shimmer_axe_head, shimmer_axe_head.getUnlocalizedName());
		GameRegistry.registerItem(shimmer_handle, shimmer_handle.getUnlocalizedName());
		
		//Circuits
		GameRegistry.registerItem(circuit_raw, circuit_raw.getUnlocalizedName());
		GameRegistry.registerItem(circuit_aluminium, circuit_aluminium.getUnlocalizedName());
		GameRegistry.registerItem(circuit_copper, circuit_copper.getUnlocalizedName());
		GameRegistry.registerItem(circuit_red_copper, circuit_red_copper.getUnlocalizedName());
		GameRegistry.registerItem(circuit_gold, circuit_gold.getUnlocalizedName());
		GameRegistry.registerItem(circuit_schrabidium, circuit_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(circuit_bismuth_raw, circuit_bismuth_raw.getUnlocalizedName());
		GameRegistry.registerItem(circuit_bismuth, circuit_bismuth.getUnlocalizedName());
		GameRegistry.registerItem(circuit_arsenic_raw, circuit_arsenic_raw.getUnlocalizedName());
		GameRegistry.registerItem(circuit_arsenic, circuit_arsenic.getUnlocalizedName());
		GameRegistry.registerItem(circuit_tantalium_raw, circuit_tantalium_raw.getUnlocalizedName());
		GameRegistry.registerItem(circuit_tantalium, circuit_tantalium.getUnlocalizedName());
		GameRegistry.registerItem(crt_display, crt_display.getUnlocalizedName());
		GameRegistry.registerItem(circuit_star_piece, circuit_star_piece.getUnlocalizedName());
		GameRegistry.registerItem(circuit_star_component, circuit_star_component.getUnlocalizedName());
		GameRegistry.registerItem(circuit_star, circuit_star.getUnlocalizedName());
		
		//Military Circuits
		GameRegistry.registerItem(circuit_targeting_tier1, circuit_targeting_tier1.getUnlocalizedName());
		GameRegistry.registerItem(circuit_targeting_tier2, circuit_targeting_tier2.getUnlocalizedName());
		GameRegistry.registerItem(circuit_targeting_tier3, circuit_targeting_tier3.getUnlocalizedName());
		GameRegistry.registerItem(circuit_targeting_tier4, circuit_targeting_tier4.getUnlocalizedName());
		GameRegistry.registerItem(circuit_targeting_tier5, circuit_targeting_tier5.getUnlocalizedName());
		GameRegistry.registerItem(circuit_targeting_tier6, circuit_targeting_tier6.getUnlocalizedName());
		
		//Gun Mechanisms
		GameRegistry.registerItem(mechanism_revolver_1, mechanism_revolver_1.getUnlocalizedName());
		GameRegistry.registerItem(mechanism_revolver_2, mechanism_revolver_2.getUnlocalizedName());
		GameRegistry.registerItem(mechanism_rifle_1, mechanism_rifle_1.getUnlocalizedName());
		GameRegistry.registerItem(mechanism_rifle_2, mechanism_rifle_2.getUnlocalizedName());
		GameRegistry.registerItem(mechanism_launcher_1, mechanism_launcher_1.getUnlocalizedName());
		GameRegistry.registerItem(mechanism_launcher_2, mechanism_launcher_2.getUnlocalizedName());
		GameRegistry.registerItem(mechanism_special, mechanism_special.getUnlocalizedName());
		
		//Casings
		GameRegistry.registerItem(casing_357, casing_357.getUnlocalizedName());
		GameRegistry.registerItem(casing_44, casing_44.getUnlocalizedName());
		GameRegistry.registerItem(casing_9, casing_9.getUnlocalizedName());
		GameRegistry.registerItem(casing_50, casing_50.getUnlocalizedName());
		GameRegistry.registerItem(casing_buckshot, casing_buckshot.getUnlocalizedName());
		
		//Bullet Assemblies
		GameRegistry.registerItem(assembly_iron, assembly_iron.getUnlocalizedName());
		GameRegistry.registerItem(assembly_steel, assembly_steel.getUnlocalizedName());
		GameRegistry.registerItem(assembly_lead, assembly_lead.getUnlocalizedName());
		GameRegistry.registerItem(assembly_gold, assembly_gold.getUnlocalizedName());
		GameRegistry.registerItem(assembly_schrabidium, assembly_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(assembly_nightmare, assembly_nightmare.getUnlocalizedName());
		GameRegistry.registerItem(assembly_desh, assembly_desh.getUnlocalizedName());
		//GameRegistry.registerItem(assembly_pip, assembly_pip.getUnlocalizedName());
		GameRegistry.registerItem(assembly_nopip, assembly_nopip.getUnlocalizedName());
		GameRegistry.registerItem(assembly_smg, assembly_smg.getUnlocalizedName());
		GameRegistry.registerItem(assembly_556, assembly_556.getUnlocalizedName());
		GameRegistry.registerItem(assembly_762, assembly_762.getUnlocalizedName());
		GameRegistry.registerItem(assembly_45, assembly_45.getUnlocalizedName());
		GameRegistry.registerItem(assembly_uzi, assembly_uzi.getUnlocalizedName());
		GameRegistry.registerItem(assembly_lacunae, assembly_lacunae.getUnlocalizedName());
		GameRegistry.registerItem(assembly_actionexpress, assembly_actionexpress.getUnlocalizedName());
		GameRegistry.registerItem(assembly_calamity, assembly_calamity.getUnlocalizedName());
		GameRegistry.registerItem(assembly_nuke, assembly_nuke.getUnlocalizedName());
		GameRegistry.registerItem(assembly_luna, assembly_luna.getUnlocalizedName());
		
		//Folly Parts
		GameRegistry.registerItem(folly_shell, folly_shell.getUnlocalizedName());
		GameRegistry.registerItem(folly_bullet, folly_bullet.getUnlocalizedName());
		GameRegistry.registerItem(folly_bullet_nuclear, folly_bullet_nuclear.getUnlocalizedName());
		GameRegistry.registerItem(folly_bullet_du, folly_bullet_du.getUnlocalizedName());
		
		//Wiring
		GameRegistry.registerItem(wiring_red_copper, wiring_red_copper.getUnlocalizedName());
		
		//Flame War in a Box
		GameRegistry.registerItem(flame_pony, flame_pony.getUnlocalizedName());
		GameRegistry.registerItem(flame_conspiracy, flame_conspiracy.getUnlocalizedName());
		GameRegistry.registerItem(flame_politics, flame_politics.getUnlocalizedName());
		GameRegistry.registerItem(flame_opinion, flame_opinion.getUnlocalizedName());
		
		//Pellets
		GameRegistry.registerItem(pellet_rtg_radium, pellet_rtg_radium.getUnlocalizedName());
		GameRegistry.registerItem(pellet_rtg_weak, pellet_rtg_weak.getUnlocalizedName());
		GameRegistry.registerItem(pellet_rtg, pellet_rtg.getUnlocalizedName());
		GameRegistry.registerItem(pellet_rtg_strontium, pellet_rtg_strontium.getUnlocalizedName());
		GameRegistry.registerItem(pellet_rtg_cobalt, pellet_rtg_cobalt.getUnlocalizedName());
		GameRegistry.registerItem(pellet_rtg_actinium, pellet_rtg_actinium.getUnlocalizedName());
		GameRegistry.registerItem(pellet_rtg_polonium, pellet_rtg_polonium.getUnlocalizedName());
		GameRegistry.registerItem(pellet_rtg_americium, pellet_rtg_americium.getUnlocalizedName());
		GameRegistry.registerItem(pellet_rtg_berkelium, pellet_rtg_berkelium.getUnlocalizedName());
		GameRegistry.registerItem(pellet_rtg_gold, pellet_rtg_gold.getUnlocalizedName());
		GameRegistry.registerItem(pellet_rtg_lead, pellet_rtg_lead.getUnlocalizedName());
		GameRegistry.registerItem(pellet_rtg_depleted, pellet_rtg_depleted.getUnlocalizedName());
		GameRegistry.registerItem(tritium_deuterium_cake, tritium_deuterium_cake.getUnlocalizedName());
		GameRegistry.registerItem(pellet_cluster, pellet_cluster.getUnlocalizedName());
		GameRegistry.registerItem(pellet_buckshot, pellet_buckshot.getUnlocalizedName());
		GameRegistry.registerItem(pellet_flechette, pellet_flechette.getUnlocalizedName());
		GameRegistry.registerItem(pellet_chlorophyte, pellet_chlorophyte.getUnlocalizedName());
		GameRegistry.registerItem(pellet_mercury, pellet_mercury.getUnlocalizedName());
		GameRegistry.registerItem(pellet_meteorite, pellet_meteorite.getUnlocalizedName());
		GameRegistry.registerItem(pellet_canister, pellet_canister.getUnlocalizedName());
		GameRegistry.registerItem(pellet_claws, pellet_claws.getUnlocalizedName());
		GameRegistry.registerItem(pellet_charged, pellet_charged.getUnlocalizedName());
		GameRegistry.registerItem(pellet_gas, pellet_gas.getUnlocalizedName());

		// Billets
		GameRegistry.registerItem(ModItems.billet_uranium, ModItems.billet_uranium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_u233, ModItems.billet_u233.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_u235, ModItems.billet_u235.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_u238, ModItems.billet_u238.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_th232, ModItems.billet_th232.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_plutonium, ModItems.billet_plutonium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_pu238, ModItems.billet_pu238.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_pu239, ModItems.billet_pu239.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_pu240, ModItems.billet_pu240.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_pu241, ModItems.billet_pu241.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_pu_mix, ModItems.billet_pu_mix.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_am241, ModItems.billet_am241.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_am242, ModItems.billet_am242.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_am_mix, ModItems.billet_am_mix.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_neptunium, ModItems.billet_neptunium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_polonium, ModItems.billet_polonium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_technetium, ModItems.billet_technetium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_cobalt, ModItems.billet_cobalt.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_co60, ModItems.billet_co60.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_sr90, ModItems.billet_sr90.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_au198, ModItems.billet_au198.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_pb209, ModItems.billet_pb209.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_ra226, ModItems.billet_ra226.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_actinium, ModItems.billet_actinium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_schrabidium, ModItems.billet_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_solinium, ModItems.billet_solinium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_gh336, ModItems.billet_gh336.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_australium, ModItems.billet_australium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_australium_lesser,
				ModItems.billet_australium_lesser.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_australium_greater,
				ModItems.billet_australium_greater.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_uranium_fuel, ModItems.billet_uranium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_thorium_fuel, ModItems.billet_thorium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_plutonium_fuel, ModItems.billet_plutonium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_neptunium_fuel, ModItems.billet_neptunium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_mox_fuel, ModItems.billet_mox_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_americium_fuel, ModItems.billet_americium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_les, ModItems.billet_les.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_schrabidium_fuel,
				ModItems.billet_schrabidium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_hes, ModItems.billet_hes.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_po210be, ModItems.billet_po210be.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_ra226be, ModItems.billet_ra226be.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_pu238be, ModItems.billet_pu238be.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_beryllium, ModItems.billet_beryllium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_bismuth, ModItems.billet_bismuth.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_zirconium, ModItems.billet_zirconium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_zfb_bismuth, ModItems.billet_zfb_bismuth.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_zfb_pu241, ModItems.billet_zfb_pu241.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_zfb_am_mix, ModItems.billet_zfb_am_mix.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_yharonite, ModItems.billet_yharonite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_balefire_gold, ModItems.billet_balefire_gold.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_flashlead, ModItems.billet_flashlead.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.billet_nuclear_waste, ModItems.billet_nuclear_waste.getUnlocalizedName());

		// Dusts & Other
		GameRegistry.registerItem(ModItems.cinnebar, ModItems.cinnebar.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_mercury, ModItems.nugget_mercury.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ingot_mercury, ModItems.ingot_mercury.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bottle_mercury, ModItems.bottle_mercury.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.coke, ModItems.coke.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.lignite, ModItems.lignite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.coal_infernal, ModItems.coal_infernal.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.briquette, ModItems.briquette.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.sulfur, ModItems.sulfur.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.niter, ModItems.niter.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fluorite, ModItems.fluorite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_coal, ModItems.powder_coal.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_coal_tiny, ModItems.powder_coal_tiny.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_iron, ModItems.powder_iron.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_gold, ModItems.powder_gold.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_lapis, ModItems.powder_lapis.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_quartz, ModItems.powder_quartz.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_diamond, ModItems.powder_diamond.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_emerald, ModItems.powder_emerald.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_uranium, ModItems.powder_uranium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_plutonium, ModItems.powder_plutonium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_neptunium, ModItems.powder_neptunium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_polonium, ModItems.powder_polonium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_co60, ModItems.powder_co60.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_sr90, ModItems.powder_sr90.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_sr90_tiny, ModItems.powder_sr90_tiny.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_i131, ModItems.powder_i131.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_i131_tiny, ModItems.powder_i131_tiny.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_xe135, ModItems.powder_xe135.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_xe135_tiny, ModItems.powder_xe135_tiny.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_cs137, ModItems.powder_cs137.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_cs137_tiny, ModItems.powder_cs137_tiny.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_au198, ModItems.powder_au198.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_ra226, ModItems.powder_ra226.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_at209, ModItems.powder_at209.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_titanium, ModItems.powder_titanium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_copper, ModItems.powder_copper.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_red_copper, ModItems.powder_red_copper.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_advanced_alloy, ModItems.powder_advanced_alloy.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_tungsten, ModItems.powder_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_aluminium, ModItems.powder_aluminium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_steel, ModItems.powder_steel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_steel_tiny, ModItems.powder_steel_tiny.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_tcalloy, ModItems.powder_tcalloy.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_lead, ModItems.powder_lead.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_bismuth, ModItems.powder_bismuth.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_calcium, ModItems.powder_calcium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_cadmium, ModItems.powder_cadmium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_coltan_ore, ModItems.powder_coltan_ore.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_coltan, ModItems.powder_coltan.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_tantalium, ModItems.powder_tantalium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_tektite, ModItems.powder_tektite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_paleogenite, ModItems.powder_paleogenite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_paleogenite_tiny,
				ModItems.powder_paleogenite_tiny.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_impure_osmiridium,
				ModItems.powder_impure_osmiridium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_borax, ModItems.powder_borax.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_chlorocalcite, ModItems.powder_chlorocalcite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_yellowcake, ModItems.powder_yellowcake.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_beryllium, ModItems.powder_beryllium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_dura_steel, ModItems.powder_dura_steel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_polymer, ModItems.powder_polymer.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_bakelite, ModItems.powder_bakelite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_schrabidium, ModItems.powder_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_schrabidate, ModItems.powder_schrabidate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_magnetized_tungsten,
				ModItems.powder_magnetized_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_chlorophyte, ModItems.powder_chlorophyte.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_combine_steel, ModItems.powder_combine_steel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_lithium, ModItems.powder_lithium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_lithium_tiny, ModItems.powder_lithium_tiny.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_zirconium, ModItems.powder_zirconium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_sodium, ModItems.powder_sodium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_lignite, ModItems.powder_lignite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_iodine, ModItems.powder_iodine.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_thorium, ModItems.powder_thorium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_neodymium, ModItems.powder_neodymium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_neodymium_tiny, ModItems.powder_neodymium_tiny.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_astatine, ModItems.powder_astatine.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_caesium, ModItems.powder_caesium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_australium, ModItems.powder_australium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_weidanium, ModItems.powder_weidanium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_reiium, ModItems.powder_reiium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_unobtainium, ModItems.powder_unobtainium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_daffergon, ModItems.powder_daffergon.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_verticium, ModItems.powder_verticium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_strontium, ModItems.powder_strontium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_cobalt, ModItems.powder_cobalt.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_cobalt_tiny, ModItems.powder_cobalt_tiny.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_bromine, ModItems.powder_bromine.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_niobium, ModItems.powder_niobium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_niobium_tiny, ModItems.powder_niobium_tiny.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_tennessine, ModItems.powder_tennessine.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_cerium, ModItems.powder_cerium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_cerium_tiny, ModItems.powder_cerium_tiny.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_lanthanium, ModItems.powder_lanthanium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_lanthanium_tiny,
				ModItems.powder_lanthanium_tiny.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_actinium, ModItems.powder_actinium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_actinium_tiny, ModItems.powder_actinium_tiny.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_boron, ModItems.powder_boron.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_boron_tiny, ModItems.powder_boron_tiny.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_asbestos, ModItems.powder_asbestos.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_magic, ModItems.powder_magic.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_cloud, ModItems.powder_cloud.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_sawdust, ModItems.powder_sawdust.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_flux, ModItems.powder_flux.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_fertilizer, ModItems.powder_fertilizer.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_balefire, ModItems.powder_balefire.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_semtex_mix, ModItems.powder_semtex_mix.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_desh_mix, ModItems.powder_desh_mix.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_desh_ready, ModItems.powder_desh_ready.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_desh, ModItems.powder_desh.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_nitan_mix, ModItems.powder_nitan_mix.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_spark_mix, ModItems.powder_spark_mix.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_meteorite, ModItems.powder_meteorite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_meteorite_tiny, ModItems.powder_meteorite_tiny.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_euphemium, ModItems.powder_euphemium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_dineutronium, ModItems.powder_dineutronium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.redstone_depleted, ModItems.redstone_depleted.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.dust, ModItems.dust.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.dust_tiny, ModItems.dust_tiny.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fallout, ModItems.fallout.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_ash, ModItems.powder_ash.getUnlocalizedName());

		// Powders
		GameRegistry.registerItem(ModItems.powder_fire, ModItems.powder_fire.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_ice, ModItems.powder_ice.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_poison, ModItems.powder_poison.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_thermite, ModItems.powder_thermite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.powder_power, ModItems.powder_power.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cordite, ModItems.cordite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ballistite, ModItems.ballistite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ball_dynamite, ModItems.ball_dynamite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ball_tnt, ModItems.ball_tnt.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ball_tatb, ModItems.ball_tatb.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ball_fireclay, ModItems.ball_fireclay.getUnlocalizedName());

		// Ores
		GameRegistry.registerItem(ModItems.ore_bedrock, ModItems.ore_bedrock.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ore_centrifuged, ModItems.ore_centrifuged.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ore_cleaned, ModItems.ore_cleaned.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ore_separated, ModItems.ore_separated.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ore_purified, ModItems.ore_purified.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ore_nitrated, ModItems.ore_nitrated.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ore_nitrocrystalline, ModItems.ore_nitrocrystalline.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ore_deepcleaned, ModItems.ore_deepcleaned.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ore_seared, ModItems.ore_seared.getUnlocalizedName());
		// GameRegistry.registerItem(ore_radcleaned,
		// ore_radcleaned.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ore_enriched, ModItems.ore_enriched.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ore_byproduct, ModItems.ore_byproduct.getUnlocalizedName());

		// Crystals
		GameRegistry.registerItem(ModItems.crystal_coal, ModItems.crystal_coal.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crystal_iron, ModItems.crystal_iron.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crystal_gold, ModItems.crystal_gold.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crystal_redstone, ModItems.crystal_redstone.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crystal_lapis, ModItems.crystal_lapis.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crystal_diamond, ModItems.crystal_diamond.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crystal_uranium, ModItems.crystal_uranium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crystal_thorium, ModItems.crystal_thorium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crystal_plutonium, ModItems.crystal_plutonium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crystal_titanium, ModItems.crystal_titanium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crystal_sulfur, ModItems.crystal_sulfur.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crystal_niter, ModItems.crystal_niter.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crystal_copper, ModItems.crystal_copper.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crystal_tungsten, ModItems.crystal_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crystal_aluminium, ModItems.crystal_aluminium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crystal_fluorite, ModItems.crystal_fluorite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crystal_beryllium, ModItems.crystal_beryllium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crystal_lead, ModItems.crystal_lead.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crystal_schraranium, ModItems.crystal_schraranium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crystal_schrabidium, ModItems.crystal_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crystal_rare, ModItems.crystal_rare.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crystal_phosphorus, ModItems.crystal_phosphorus.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crystal_lithium, ModItems.crystal_lithium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crystal_cobalt, ModItems.crystal_cobalt.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crystal_starmetal, ModItems.crystal_starmetal.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crystal_cinnebar, ModItems.crystal_cinnebar.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crystal_trixite, ModItems.crystal_trixite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crystal_osmiridium, ModItems.crystal_osmiridium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gem_sodalite, ModItems.gem_sodalite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gem_tantalium, ModItems.gem_tantalium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gem_volcanic, ModItems.gem_volcanic.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gem_alexandrite, ModItems.gem_alexandrite.getUnlocalizedName());

		// Fragments
		GameRegistry.registerItem(ModItems.fragment_neodymium, ModItems.fragment_neodymium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fragment_cobalt, ModItems.fragment_cobalt.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fragment_niobium, ModItems.fragment_niobium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fragment_cerium, ModItems.fragment_cerium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fragment_lanthanium, ModItems.fragment_lanthanium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fragment_actinium, ModItems.fragment_actinium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fragment_boron, ModItems.fragment_boron.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fragment_meteorite, ModItems.fragment_meteorite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fragment_coltan, ModItems.fragment_coltan.getUnlocalizedName());

		// Things that look like rotten flesh but aren't
		GameRegistry.registerItem(ModItems.biomass, ModItems.biomass.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.biomass_compressed, ModItems.biomass_compressed.getUnlocalizedName());
		// delicious!
		GameRegistry.registerItem(ModItems.bio_wafer, ModItems.bio_wafer.getUnlocalizedName());

		// Nuggets
		GameRegistry.registerItem(ModItems.nugget_uranium, ModItems.nugget_uranium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_u233, ModItems.nugget_u233.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_u235, ModItems.nugget_u235.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_u238, ModItems.nugget_u238.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_th232, ModItems.nugget_th232.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_plutonium, ModItems.nugget_plutonium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_pu238, ModItems.nugget_pu238.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_pu239, ModItems.nugget_pu239.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_pu240, ModItems.nugget_pu240.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_pu241, ModItems.nugget_pu241.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_pu_mix, ModItems.nugget_pu_mix.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_am241, ModItems.nugget_am241.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_am242, ModItems.nugget_am242.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_am_mix, ModItems.nugget_am_mix.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_neptunium, ModItems.nugget_neptunium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_polonium, ModItems.nugget_polonium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_cobalt, ModItems.nugget_cobalt.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_co60, ModItems.nugget_co60.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_sr90, ModItems.nugget_sr90.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_technetium, ModItems.nugget_technetium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_au198, ModItems.nugget_au198.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_pb209, ModItems.nugget_pb209.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_ra226, ModItems.nugget_ra226.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_actinium, ModItems.nugget_actinium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_lead, ModItems.nugget_lead.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_bismuth, ModItems.nugget_bismuth.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_arsenic, ModItems.nugget_arsenic.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_tantalium, ModItems.nugget_tantalium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_beryllium, ModItems.nugget_beryllium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_schrabidium, ModItems.nugget_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_solinium, ModItems.nugget_solinium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_gh336, ModItems.nugget_gh336.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_thorium_fuel, ModItems.nugget_thorium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_neptunium_fuel, ModItems.nugget_neptunium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_americium_fuel, ModItems.nugget_americium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_schrabidium_fuel,
				ModItems.nugget_schrabidium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_hes, ModItems.nugget_hes.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_les, ModItems.nugget_les.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_zirconium, ModItems.nugget_zirconium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_australium, ModItems.nugget_australium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_australium_lesser,
				ModItems.nugget_australium_lesser.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_australium_greater,
				ModItems.nugget_australium_greater.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_weidanium, ModItems.nugget_weidanium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_reiium, ModItems.nugget_reiium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_unobtainium, ModItems.nugget_unobtainium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_daffergon, ModItems.nugget_daffergon.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_verticium, ModItems.nugget_verticium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_desh, ModItems.nugget_desh.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_euphemium, ModItems.nugget_euphemium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_dineutronium, ModItems.nugget_dineutronium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget_osmiridium, ModItems.nugget_osmiridium.getUnlocalizedName());
		
		// Armor Plates
		GameRegistry.registerItem(ModItems.plate_armor_titanium, ModItems.plate_armor_titanium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.plate_armor_ajr, ModItems.plate_armor_ajr.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.plate_armor_hev, ModItems.plate_armor_hev.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.plate_armor_lunar, ModItems.plate_armor_lunar.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.plate_armor_fau, ModItems.plate_armor_fau.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.plate_armor_dnt, ModItems.plate_armor_dnt.getUnlocalizedName());

		// Heavy/Cast Plate
		GameRegistry.registerItem(ModItems.plate_cast, ModItems.plate_cast.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.plate_welded, ModItems.plate_welded.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.heavy_component, ModItems.heavy_component.getUnlocalizedName());

		// Parts
		GameRegistry.registerItem(ModItems.coil_copper, ModItems.coil_copper.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.coil_copper_torus, ModItems.coil_copper_torus.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.coil_advanced_alloy, ModItems.coil_advanced_alloy.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.coil_advanced_torus, ModItems.coil_advanced_torus.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.coil_gold, ModItems.coil_gold.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.coil_gold_torus, ModItems.coil_gold_torus.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.coil_tungsten, ModItems.coil_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.coil_magnetized_tungsten,
				ModItems.coil_magnetized_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.safety_fuse, ModItems.safety_fuse.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.tank_steel, ModItems.tank_steel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.motor, ModItems.motor.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.motor_desh, ModItems.motor_desh.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.motor_bismuth, ModItems.motor_bismuth.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.centrifuge_element, ModItems.centrifuge_element.getUnlocalizedName());
		// GameRegistry.registerItem(centrifuge_tower,
		// centrifuge_tower.getUnlocalizedName());
		// GameRegistry.registerItem(magnet_dee, magnet_dee.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.magnet_circular, ModItems.magnet_circular.getUnlocalizedName());
		// GameRegistry.registerItem(cyclotron_tower,
		// cyclotron_tower.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.reactor_core, ModItems.reactor_core.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rtg_unit, ModItems.rtg_unit.getUnlocalizedName());
		// GameRegistry.registerItem(thermo_unit_empty,
		// thermo_unit_empty.getUnlocalizedName());
		// GameRegistry.registerItem(thermo_unit_endo,
		// thermo_unit_endo.getUnlocalizedName());
		// GameRegistry.registerItem(thermo_unit_exo,
		// thermo_unit_exo.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.levitation_unit, ModItems.levitation_unit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pipes_steel, ModItems.pipes_steel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.drill_titanium, ModItems.drill_titanium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.photo_panel, ModItems.photo_panel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.chlorine_pinwheel, ModItems.chlorine_pinwheel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ring_starmetal, ModItems.ring_starmetal.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.deuterium_filter, ModItems.deuterium_filter.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.chemical_dye, ModItems.chemical_dye.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crayon, ModItems.crayon.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.part_generic, ModItems.part_generic.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.parts_legendary, ModItems.parts_legendary.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gear_large, ModItems.gear_large.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.sawblade, ModItems.sawblade.getUnlocalizedName());

		// Plant Products
		GameRegistry.registerItem(ModItems.plant_item, ModItems.plant_item.getUnlocalizedName());

		// Teleporter Parts
		// GameRegistry.registerItem(telepad, telepad.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.entanglement_kit, ModItems.entanglement_kit.getUnlocalizedName());
		
		// Chopper parts
		GameRegistry.registerItem(ModItems.chopper_head, ModItems.chopper_head.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.chopper_gun, ModItems.chopper_gun.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.chopper_torso, ModItems.chopper_torso.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.chopper_tail, ModItems.chopper_tail.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.chopper_wing, ModItems.chopper_wing.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.chopper_blades, ModItems.chopper_blades.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.combine_scrap, ModItems.combine_scrap.getUnlocalizedName());

		// Hammer Parts
		GameRegistry.registerItem(ModItems.shimmer_head, ModItems.shimmer_head.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.shimmer_axe_head, ModItems.shimmer_axe_head.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.shimmer_handle, ModItems.shimmer_handle.getUnlocalizedName());

		// Circuits
		GameRegistry.registerItem(ModItems.circuit_raw, ModItems.circuit_raw.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.circuit_aluminium, ModItems.circuit_aluminium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.circuit_copper, ModItems.circuit_copper.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.circuit_red_copper, ModItems.circuit_red_copper.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.circuit_gold, ModItems.circuit_gold.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.circuit_schrabidium, ModItems.circuit_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.circuit_bismuth_raw, ModItems.circuit_bismuth_raw.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.circuit_bismuth, ModItems.circuit_bismuth.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.circuit_arsenic_raw, ModItems.circuit_arsenic_raw.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.circuit_arsenic, ModItems.circuit_arsenic.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.circuit_tantalium_raw, ModItems.circuit_tantalium_raw.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.circuit_tantalium, ModItems.circuit_tantalium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crt_display, ModItems.crt_display.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.circuit_star_piece, ModItems.circuit_star_piece.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.circuit_star_component,
				ModItems.circuit_star_component.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.circuit_star, ModItems.circuit_star.getUnlocalizedName());

		// Military Circuits
		GameRegistry.registerItem(ModItems.circuit_targeting_tier1,
				ModItems.circuit_targeting_tier1.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.circuit_targeting_tier2,
				ModItems.circuit_targeting_tier2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.circuit_targeting_tier3,
				ModItems.circuit_targeting_tier3.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.circuit_targeting_tier4,
				ModItems.circuit_targeting_tier4.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.circuit_targeting_tier5,
				ModItems.circuit_targeting_tier5.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.circuit_targeting_tier6,
				ModItems.circuit_targeting_tier6.getUnlocalizedName());

		// Gun Mechanisms
		GameRegistry.registerItem(ModItems.mechanism_revolver_1, ModItems.mechanism_revolver_1.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mechanism_revolver_2, ModItems.mechanism_revolver_2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mechanism_rifle_1, ModItems.mechanism_rifle_1.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mechanism_rifle_2, ModItems.mechanism_rifle_2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mechanism_launcher_1, ModItems.mechanism_launcher_1.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mechanism_launcher_2, ModItems.mechanism_launcher_2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mechanism_special, ModItems.mechanism_special.getUnlocalizedName());

		// Casings
		GameRegistry.registerItem(ModItems.casing_357, ModItems.casing_357.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.casing_44, ModItems.casing_44.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.casing_9, ModItems.casing_9.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.casing_50, ModItems.casing_50.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.casing_buckshot, ModItems.casing_buckshot.getUnlocalizedName());

		// Bullet Assemblies
		GameRegistry.registerItem(ModItems.assembly_iron, ModItems.assembly_iron.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.assembly_steel, ModItems.assembly_steel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.assembly_lead, ModItems.assembly_lead.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.assembly_gold, ModItems.assembly_gold.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.assembly_schrabidium, ModItems.assembly_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.assembly_nightmare, ModItems.assembly_nightmare.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.assembly_desh, ModItems.assembly_desh.getUnlocalizedName());
		// GameRegistry.registerItem(assembly_pip, assembly_pip.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.assembly_nopip, ModItems.assembly_nopip.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.assembly_smg, ModItems.assembly_smg.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.assembly_556, ModItems.assembly_556.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.assembly_762, ModItems.assembly_762.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.assembly_45, ModItems.assembly_45.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.assembly_uzi, ModItems.assembly_uzi.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.assembly_lacunae, ModItems.assembly_lacunae.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.assembly_actionexpress,
				ModItems.assembly_actionexpress.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.assembly_calamity, ModItems.assembly_calamity.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.assembly_nuke, ModItems.assembly_nuke.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.assembly_luna, ModItems.assembly_luna.getUnlocalizedName());

		// Folly Parts
		GameRegistry.registerItem(ModItems.folly_shell, ModItems.folly_shell.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.folly_bullet, ModItems.folly_bullet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.folly_bullet_nuclear, ModItems.folly_bullet_nuclear.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.folly_bullet_du, ModItems.folly_bullet_du.getUnlocalizedName());

		// Wiring
		GameRegistry.registerItem(ModItems.wiring_red_copper, ModItems.wiring_red_copper.getUnlocalizedName());

		// Flame War in a Box
		GameRegistry.registerItem(ModItems.flame_pony, ModItems.flame_pony.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.flame_conspiracy, ModItems.flame_conspiracy.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.flame_politics, ModItems.flame_politics.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.flame_opinion, ModItems.flame_opinion.getUnlocalizedName());

		// Pellets
		GameRegistry.registerItem(ModItems.pellet_rtg_radium, ModItems.pellet_rtg_radium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pellet_rtg_weak, ModItems.pellet_rtg_weak.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pellet_rtg, ModItems.pellet_rtg.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pellet_rtg_strontium, ModItems.pellet_rtg_strontium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pellet_rtg_cobalt, ModItems.pellet_rtg_cobalt.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pellet_rtg_actinium, ModItems.pellet_rtg_actinium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pellet_rtg_polonium, ModItems.pellet_rtg_polonium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pellet_rtg_americium, ModItems.pellet_rtg_americium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pellet_rtg_berkelium, ModItems.pellet_rtg_berkelium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pellet_rtg_gold, ModItems.pellet_rtg_gold.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pellet_rtg_lead, ModItems.pellet_rtg_lead.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pellet_rtg_depleted, ModItems.pellet_rtg_depleted.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.tritium_deuterium_cake,
				ModItems.tritium_deuterium_cake.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pellet_cluster, ModItems.pellet_cluster.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pellet_buckshot, ModItems.pellet_buckshot.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pellet_flechette, ModItems.pellet_flechette.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pellet_chlorophyte, ModItems.pellet_chlorophyte.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pellet_mercury, ModItems.pellet_mercury.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pellet_meteorite, ModItems.pellet_meteorite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pellet_canister, ModItems.pellet_canister.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pellet_claws, ModItems.pellet_claws.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pellet_charged, ModItems.pellet_charged.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pellet_gas, ModItems.pellet_gas.getUnlocalizedName());

		GameRegistry.registerItem(ModItems.magnetron, ModItems.magnetron.getUnlocalizedName());

		GameRegistry.registerItem(ModItems.pellet_coal, ModItems.pellet_coal.getUnlocalizedName());

		// Watz Pellets
		GameRegistry.registerItem(ModItems.pellet_schrabidium, ModItems.pellet_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pellet_hes, ModItems.pellet_hes.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pellet_mes, ModItems.pellet_mes.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pellet_les, ModItems.pellet_les.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pellet_beryllium, ModItems.pellet_beryllium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pellet_neptunium, ModItems.pellet_neptunium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pellet_lead, ModItems.pellet_lead.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pellet_advanced, ModItems.pellet_advanced.getUnlocalizedName());

		// Engine Pieces
		GameRegistry.registerItem(ModItems.piston_selenium, ModItems.piston_selenium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.piston_set, ModItems.piston_set.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.drillbit, ModItems.drillbit.getUnlocalizedName());

		// Cells
		GameRegistry.registerItem(ModItems.cell_empty, ModItems.cell_empty.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cell_uf6, ModItems.cell_uf6.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cell_puf6, ModItems.cell_puf6.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cell_deuterium, ModItems.cell_deuterium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cell_tritium, ModItems.cell_tritium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cell_sas3, ModItems.cell_sas3.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cell_antimatter, ModItems.cell_antimatter.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cell_anti_schrabidium, ModItems.cell_anti_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cell_balefire, ModItems.cell_balefire.getUnlocalizedName());

		// DEMON CORE
		GameRegistry.registerItem(ModItems.demon_core_open, ModItems.demon_core_open.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.demon_core_closed, ModItems.demon_core_closed.getUnlocalizedName());

		// Particle Containers
		GameRegistry.registerItem(ModItems.particle_empty, ModItems.particle_empty.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.particle_hydrogen, ModItems.particle_hydrogen.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.particle_copper, ModItems.particle_copper.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.particle_lead, ModItems.particle_lead.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.particle_aproton, ModItems.particle_aproton.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.particle_aelectron, ModItems.particle_aelectron.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.particle_amat, ModItems.particle_amat.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.particle_aschrab, ModItems.particle_aschrab.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.particle_higgs, ModItems.particle_higgs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.particle_muon, ModItems.particle_muon.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.particle_tachyon, ModItems.particle_tachyon.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.particle_strange, ModItems.particle_strange.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.particle_dark, ModItems.particle_dark.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.particle_sparkticle, ModItems.particle_sparkticle.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.particle_digamma, ModItems.particle_digamma.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.particle_lutece, ModItems.particle_lutece.getUnlocalizedName());

		// Singularities, black holes and other cosmic horrors
		GameRegistry.registerItem(ModItems.singularity_micro, ModItems.singularity_micro.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.singularity, ModItems.singularity.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.singularity_counter_resonant,
				ModItems.singularity_counter_resonant.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.singularity_super_heated,
				ModItems.singularity_super_heated.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.black_hole, ModItems.black_hole.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.singularity_spark, ModItems.singularity_spark.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crystal_xen, ModItems.crystal_xen.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pellet_antimatter, ModItems.pellet_antimatter.getUnlocalizedName());

		// Infinite Tanks
		GameRegistry.registerItem(ModItems.inf_water, ModItems.inf_water.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.inf_water_mk2, ModItems.inf_water_mk2.getUnlocalizedName());

		// Canisters
		GameRegistry.registerItem(ModItems.antiknock, ModItems.antiknock.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.canister_empty, ModItems.canister_empty.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.canister_full, ModItems.canister_full.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.canister_napalm, ModItems.canister_napalm.getUnlocalizedName());

		// Gas Tanks
		GameRegistry.registerItem(ModItems.gas_empty, ModItems.gas_empty.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gas_full, ModItems.gas_full.getUnlocalizedName());

		// Universal Tank
		GameRegistry.registerItem(ModItems.fluid_tank_empty, ModItems.fluid_tank_empty.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fluid_tank_full, ModItems.fluid_tank_full.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fluid_tank_lead_empty, ModItems.fluid_tank_lead_empty.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fluid_tank_lead_full, ModItems.fluid_tank_lead_full.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fluid_barrel_empty, ModItems.fluid_barrel_empty.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fluid_barrel_full, ModItems.fluid_barrel_full.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fluid_barrel_infinite, ModItems.fluid_barrel_infinite.getUnlocalizedName());

		// Batteries
		GameRegistry.registerItem(ModItems.battery_generic, ModItems.battery_generic.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_red_cell, ModItems.battery_red_cell.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_red_cell_6, ModItems.battery_red_cell_6.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_red_cell_24, ModItems.battery_red_cell_24.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_advanced, ModItems.battery_advanced.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_advanced_cell, ModItems.battery_advanced_cell.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_advanced_cell_4,
				ModItems.battery_advanced_cell_4.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_advanced_cell_12,
				ModItems.battery_advanced_cell_12.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_lithium, ModItems.battery_lithium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_lithium_cell, ModItems.battery_lithium_cell.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_lithium_cell_3,
				ModItems.battery_lithium_cell_3.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_lithium_cell_6,
				ModItems.battery_lithium_cell_6.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_schrabidium, ModItems.battery_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_schrabidium_cell,
				ModItems.battery_schrabidium_cell.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_schrabidium_cell_2,
				ModItems.battery_schrabidium_cell_2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_schrabidium_cell_4,
				ModItems.battery_schrabidium_cell_4.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_spark, ModItems.battery_spark.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_trixite, ModItems.battery_trixite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_spark_cell_6, ModItems.battery_spark_cell_6.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_spark_cell_25, ModItems.battery_spark_cell_25.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_spark_cell_100,
				ModItems.battery_spark_cell_100.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_spark_cell_1000,
				ModItems.battery_spark_cell_1000.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_spark_cell_2500,
				ModItems.battery_spark_cell_2500.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_spark_cell_10000,
				ModItems.battery_spark_cell_10000.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_spark_cell_power,
				ModItems.battery_spark_cell_power.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cube_power, ModItems.cube_power.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_creative, ModItems.battery_creative.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_su, ModItems.battery_su.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_su_l, ModItems.battery_su_l.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_potato, ModItems.battery_potato.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_potatos, ModItems.battery_potatos.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_steam, ModItems.battery_steam.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_steam_large, ModItems.battery_steam_large.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_sc_uranium, ModItems.battery_sc_uranium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_sc_technetium, ModItems.battery_sc_technetium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_sc_plutonium, ModItems.battery_sc_plutonium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_sc_polonium, ModItems.battery_sc_polonium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_sc_gold, ModItems.battery_sc_gold.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_sc_lead, ModItems.battery_sc_lead.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.battery_sc_americium, ModItems.battery_sc_americium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.hev_battery, ModItems.hev_battery.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fusion_core, ModItems.fusion_core.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.energy_core, ModItems.energy_core.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fusion_core_infinite, ModItems.fusion_core_infinite.getUnlocalizedName());

		// Folders
		GameRegistry.registerItem(ModItems.template_folder, ModItems.template_folder.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.journal_pip, ModItems.journal_pip.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.journal_bj, ModItems.journal_bj.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.journal_silver, ModItems.journal_silver.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bobmazon_materials, ModItems.bobmazon_materials.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bobmazon_machines, ModItems.bobmazon_machines.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bobmazon_weapons, ModItems.bobmazon_weapons.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bobmazon_tools, ModItems.bobmazon_tools.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bobmazon_hidden, ModItems.bobmazon_hidden.getUnlocalizedName());

		// Hydraulic Press Stamps
		GameRegistry.registerItem(ModItems.stamp_stone_flat, ModItems.stamp_stone_flat.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_stone_plate, ModItems.stamp_stone_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_stone_wire, ModItems.stamp_stone_wire.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_stone_circuit, ModItems.stamp_stone_circuit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_iron_flat, ModItems.stamp_iron_flat.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_iron_plate, ModItems.stamp_iron_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_iron_wire, ModItems.stamp_iron_wire.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_iron_circuit, ModItems.stamp_iron_circuit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_steel_flat, ModItems.stamp_steel_flat.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_steel_plate, ModItems.stamp_steel_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_steel_wire, ModItems.stamp_steel_wire.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_steel_circuit, ModItems.stamp_steel_circuit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_titanium_flat, ModItems.stamp_titanium_flat.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_titanium_plate, ModItems.stamp_titanium_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_titanium_wire, ModItems.stamp_titanium_wire.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_titanium_circuit,
				ModItems.stamp_titanium_circuit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_obsidian_flat, ModItems.stamp_obsidian_flat.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_obsidian_plate, ModItems.stamp_obsidian_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_obsidian_wire, ModItems.stamp_obsidian_wire.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_obsidian_circuit,
				ModItems.stamp_obsidian_circuit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_desh_flat, ModItems.stamp_desh_flat.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_desh_plate, ModItems.stamp_desh_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_desh_wire, ModItems.stamp_desh_wire.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_desh_circuit, ModItems.stamp_desh_circuit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_357, ModItems.stamp_357.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_44, ModItems.stamp_44.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_9, ModItems.stamp_9.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_50, ModItems.stamp_50.getUnlocalizedName());

		GameRegistry.registerItem(ModItems.stamp_desh_357, ModItems.stamp_desh_357.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_desh_44, ModItems.stamp_desh_44.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_desh_9, ModItems.stamp_desh_9.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stamp_desh_50, ModItems.stamp_desh_50.getUnlocalizedName());

		// Molds
		GameRegistry.registerItem(ModItems.mold_base, ModItems.mold_base.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mold, ModItems.mold.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.scraps, ModItems.scraps.getUnlocalizedName());

		// Machine Upgrades
		GameRegistry.registerItem(ModItems.upgrade_template, ModItems.upgrade_template.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_speed_1, ModItems.upgrade_speed_1.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_speed_2, ModItems.upgrade_speed_2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_speed_3, ModItems.upgrade_speed_3.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_effect_1, ModItems.upgrade_effect_1.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_effect_2, ModItems.upgrade_effect_2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_effect_3, ModItems.upgrade_effect_3.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_power_1, ModItems.upgrade_power_1.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_power_2, ModItems.upgrade_power_2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_power_3, ModItems.upgrade_power_3.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_fortune_1, ModItems.upgrade_fortune_1.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_fortune_2, ModItems.upgrade_fortune_2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_fortune_3, ModItems.upgrade_fortune_3.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_afterburn_1, ModItems.upgrade_afterburn_1.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_afterburn_2, ModItems.upgrade_afterburn_2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_afterburn_3, ModItems.upgrade_afterburn_3.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_overdrive_1, ModItems.upgrade_overdrive_1.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_overdrive_2, ModItems.upgrade_overdrive_2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_overdrive_3, ModItems.upgrade_overdrive_3.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_radius, ModItems.upgrade_radius.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_health, ModItems.upgrade_health.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_smelter, ModItems.upgrade_smelter.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_shredder, ModItems.upgrade_shredder.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_centrifuge, ModItems.upgrade_centrifuge.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_crystallizer, ModItems.upgrade_crystallizer.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_nullifier, ModItems.upgrade_nullifier.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_screm, ModItems.upgrade_screm.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_gc_speed, ModItems.upgrade_gc_speed.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_5g, ModItems.upgrade_5g.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_stack, ModItems.upgrade_stack.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.upgrade_ejector, ModItems.upgrade_ejector.getUnlocalizedName());

		// Machine Templates
		GameRegistry.registerItem(ModItems.siren_track, ModItems.siren_track.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fluid_identifier, ModItems.fluid_identifier.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fluid_identifier_multi,
				ModItems.fluid_identifier_multi.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fluid_icon, ModItems.fluid_icon.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fluid_duct, ModItems.fluid_duct.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.assembly_template, ModItems.assembly_template.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.chemistry_template, ModItems.chemistry_template.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.chemistry_icon, ModItems.chemistry_icon.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crucible_template, ModItems.crucible_template.getUnlocalizedName());

		// Machine Items
		GameRegistry.registerItem(ModItems.fuse, ModItems.fuse.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.redcoil_capacitor, ModItems.redcoil_capacitor.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.euphemium_capacitor, ModItems.euphemium_capacitor.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.titanium_filter, ModItems.titanium_filter.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.screwdriver, ModItems.screwdriver.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.screwdriver_desh, ModItems.screwdriver_desh.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.hand_drill, ModItems.hand_drill.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.hand_drill_desh, ModItems.hand_drill_desh.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.chemistry_set, ModItems.chemistry_set.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.chemistry_set_boron, ModItems.chemistry_set_boron.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.blowtorch, ModItems.blowtorch.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.acetylene_torch, ModItems.acetylene_torch.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.boltgun, ModItems.boltgun.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.overfuse, ModItems.overfuse.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.arc_electrode, ModItems.arc_electrode.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.arc_electrode_burnt, ModItems.arc_electrode_burnt.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.arc_electrode_desh, ModItems.arc_electrode_desh.getUnlocalizedName());

		// Particle Collider Fuel
		GameRegistry.registerItem(ModItems.part_lithium, ModItems.part_lithium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.part_beryllium, ModItems.part_beryllium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.part_carbon, ModItems.part_carbon.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.part_copper, ModItems.part_copper.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.part_plutonium, ModItems.part_plutonium.getUnlocalizedName());

		// FEL laser crystals
		GameRegistry.registerItem(ModItems.laser_crystal_co2, ModItems.laser_crystal_co2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.laser_crystal_bismuth, ModItems.laser_crystal_bismuth.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.laser_crystal_cmb, ModItems.laser_crystal_cmb.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.laser_crystal_dnt, ModItems.laser_crystal_dnt.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.laser_crystal_digamma, ModItems.laser_crystal_digamma.getUnlocalizedName());

		// Catalyst Rune Sigils
		GameRegistry.registerItem(ModItems.rune_blank, ModItems.rune_blank.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rune_isa, ModItems.rune_isa.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rune_dagaz, ModItems.rune_dagaz.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rune_hagalaz, ModItems.rune_hagalaz.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rune_jera, ModItems.rune_jera.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rune_thurisaz, ModItems.rune_thurisaz.getUnlocalizedName());

		// AMS Catalysts
		GameRegistry.registerItem(ModItems.ams_catalyst_blank, ModItems.ams_catalyst_blank.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ams_catalyst_aluminium,
				ModItems.ams_catalyst_aluminium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ams_catalyst_beryllium,
				ModItems.ams_catalyst_beryllium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ams_catalyst_caesium, ModItems.ams_catalyst_caesium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ams_catalyst_cerium, ModItems.ams_catalyst_cerium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ams_catalyst_cobalt, ModItems.ams_catalyst_cobalt.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ams_catalyst_copper, ModItems.ams_catalyst_copper.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ams_catalyst_euphemium,
				ModItems.ams_catalyst_euphemium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ams_catalyst_dineutronium,
				ModItems.ams_catalyst_dineutronium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ams_catalyst_iron, ModItems.ams_catalyst_iron.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ams_catalyst_lithium, ModItems.ams_catalyst_lithium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ams_catalyst_niobium, ModItems.ams_catalyst_niobium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ams_catalyst_schrabidium,
				ModItems.ams_catalyst_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ams_catalyst_strontium,
				ModItems.ams_catalyst_strontium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ams_catalyst_thorium, ModItems.ams_catalyst_thorium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ams_catalyst_tungsten, ModItems.ams_catalyst_tungsten.getUnlocalizedName());

		// Shredder Blades
		GameRegistry.registerItem(ModItems.blades_steel, ModItems.blades_steel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.blades_titanium, ModItems.blades_titanium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.blades_advanced_alloy, ModItems.blades_advanced_alloy.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.blades_desh, ModItems.blades_desh.getUnlocalizedName());

		// Generator Stuff
		GameRegistry.registerItem(ModItems.thermo_element, ModItems.thermo_element.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.catalytic_converter, ModItems.catalytic_converter.getUnlocalizedName());

		// AMS Components
		GameRegistry.registerItem(ModItems.ams_focus_blank, ModItems.ams_focus_blank.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ams_focus_limiter, ModItems.ams_focus_limiter.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ams_focus_booster, ModItems.ams_focus_booster.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ams_muzzle, ModItems.ams_muzzle.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ams_lens, ModItems.ams_lens.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ams_core_sing, ModItems.ams_core_sing.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ams_core_wormhole, ModItems.ams_core_wormhole.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ams_core_eyeofharmony, ModItems.ams_core_eyeofharmony.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ams_core_thingy, ModItems.ams_core_thingy.getUnlocalizedName());

		// Fusion Shields
		GameRegistry.registerItem(ModItems.fusion_shield_tungsten,
				ModItems.fusion_shield_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fusion_shield_desh, ModItems.fusion_shield_desh.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fusion_shield_chlorophyte,
				ModItems.fusion_shield_chlorophyte.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fusion_shield_vaporwave,
				ModItems.fusion_shield_vaporwave.getUnlocalizedName());

		// Breeding Rods
		GameRegistry.registerItem(ModItems.rod_empty, ModItems.rod_empty.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rod, ModItems.rod.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rod_dual_empty, ModItems.rod_dual_empty.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rod_dual, ModItems.rod_dual.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rod_quad_empty, ModItems.rod_quad_empty.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rod_quad, ModItems.rod_quad.getUnlocalizedName());

		// ZIRNOX parts
		GameRegistry.registerItem(ModItems.rod_zirnox_empty, ModItems.rod_zirnox_empty.getUnlocalizedName());
		// GameRegistry.registerItem(rod_zirnox_natural_uranium_fuel,
		// rod_zirnox_natural_uranium_fuel.getUnlocalizedName());
		// GameRegistry.registerItem(rod_zirnox_uranium_fuel,
		// rod_zirnox_uranium_fuel.getUnlocalizedName());
		// GameRegistry.registerItem(rod_zirnox_th232,
		// rod_zirnox_th232.getUnlocalizedName());
		// GameRegistry.registerItem(rod_zirnox_thorium_fuel,
		// rod_zirnox_thorium_fuel.getUnlocalizedName());
		// GameRegistry.registerItem(rod_zirnox_mox_fuel,
		// rod_zirnox_mox_fuel.getUnlocalizedName());
		// GameRegistry.registerItem(rod_zirnox_plutonium_fuel,
		// rod_zirnox_plutonium_fuel.getUnlocalizedName());
		// GameRegistry.registerItem(rod_zirnox_u233_fuel,
		// rod_zirnox_u233_fuel.getUnlocalizedName());
		// GameRegistry.registerItem(rod_zirnox_u235_fuel,
		// rod_zirnox_u235_fuel.getUnlocalizedName());
		// GameRegistry.registerItem(rod_zirnox_les_fuel,
		// rod_zirnox_les_fuel.getUnlocalizedName());
		// GameRegistry.registerItem(rod_zirnox_lithium,
		// rod_zirnox_lithium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rod_zirnox_tritium, ModItems.rod_zirnox_tritium.getUnlocalizedName());
		// GameRegistry.registerItem(rod_zirnox_zfb_mox,
		// rod_zirnox_zfb_mox.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rod_zirnox, ModItems.rod_zirnox.getUnlocalizedName());

		GameRegistry.registerItem(ModItems.rod_zirnox_natural_uranium_fuel_depleted,
				ModItems.rod_zirnox_natural_uranium_fuel_depleted.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rod_zirnox_uranium_fuel_depleted,
				ModItems.rod_zirnox_uranium_fuel_depleted.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rod_zirnox_thorium_fuel_depleted,
				ModItems.rod_zirnox_thorium_fuel_depleted.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rod_zirnox_mox_fuel_depleted,
				ModItems.rod_zirnox_mox_fuel_depleted.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rod_zirnox_plutonium_fuel_depleted,
				ModItems.rod_zirnox_plutonium_fuel_depleted.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rod_zirnox_u233_fuel_depleted,
				ModItems.rod_zirnox_u233_fuel_depleted.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rod_zirnox_u235_fuel_depleted,
				ModItems.rod_zirnox_u235_fuel_depleted.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rod_zirnox_les_fuel_depleted,
				ModItems.rod_zirnox_les_fuel_depleted.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rod_zirnox_zfb_mox_depleted,
				ModItems.rod_zirnox_zfb_mox_depleted.getUnlocalizedName());

		// Depleted Fuel
		GameRegistry.registerItem(ModItems.waste_natural_uranium, ModItems.waste_natural_uranium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.waste_uranium, ModItems.waste_uranium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.waste_thorium, ModItems.waste_thorium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.waste_mox, ModItems.waste_mox.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.waste_plutonium, ModItems.waste_plutonium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.waste_u233, ModItems.waste_u233.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.waste_u235, ModItems.waste_u235.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.waste_schrabidium, ModItems.waste_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.waste_zfb_mox, ModItems.waste_zfb_mox.getUnlocalizedName());

		GameRegistry.registerItem(ModItems.waste_plate_u233, ModItems.waste_plate_u233.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.waste_plate_u235, ModItems.waste_plate_u235.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.waste_plate_mox, ModItems.waste_plate_mox.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.waste_plate_pu239, ModItems.waste_plate_pu239.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.waste_plate_ra226be, ModItems.waste_plate_ra226be.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.waste_plate_sa326, ModItems.waste_plate_sa326.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.waste_plate_pu238be, ModItems.waste_plate_pu238be.getUnlocalizedName());

		// Pile parts
		GameRegistry.registerItem(ModItems.pile_rod_uranium, ModItems.pile_rod_uranium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pile_rod_pu239, ModItems.pile_rod_pu239.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pile_rod_plutonium, ModItems.pile_rod_plutonium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pile_rod_source, ModItems.pile_rod_source.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pile_rod_boron, ModItems.pile_rod_boron.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pile_rod_lithium, ModItems.pile_rod_lithium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pile_rod_detector, ModItems.pile_rod_detector.getUnlocalizedName());

		// Plate Fuels
		GameRegistry.registerItem(ModItems.plate_fuel_u233, ModItems.plate_fuel_u233.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.plate_fuel_u235, ModItems.plate_fuel_u235.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.plate_fuel_mox, ModItems.plate_fuel_mox.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.plate_fuel_pu239, ModItems.plate_fuel_pu239.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.plate_fuel_sa326, ModItems.plate_fuel_sa326.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.plate_fuel_ra226be, ModItems.plate_fuel_ra226be.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.plate_fuel_pu238be, ModItems.plate_fuel_pu238be.getUnlocalizedName());

		// PWR Parts
		GameRegistry.registerItem(ModItems.pwr_fuel, ModItems.pwr_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pwr_fuel_hot, ModItems.pwr_fuel_hot.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pwr_fuel_depleted, ModItems.pwr_fuel_depleted.getUnlocalizedName());

		// RBMK parts
		GameRegistry.registerItem(ModItems.rbmk_lid, ModItems.rbmk_lid.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_lid_glass, ModItems.rbmk_lid_glass.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_empty, ModItems.rbmk_fuel_empty.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_ueu, ModItems.rbmk_fuel_ueu.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_meu, ModItems.rbmk_fuel_meu.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_heu233, ModItems.rbmk_fuel_heu233.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_heu235, ModItems.rbmk_fuel_heu235.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_thmeu, ModItems.rbmk_fuel_thmeu.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_lep, ModItems.rbmk_fuel_lep.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_mep, ModItems.rbmk_fuel_mep.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_hep239, ModItems.rbmk_fuel_hep239.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_hep241, ModItems.rbmk_fuel_hep241.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_lea, ModItems.rbmk_fuel_lea.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_mea, ModItems.rbmk_fuel_mea.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_hea241, ModItems.rbmk_fuel_hea241.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_hea242, ModItems.rbmk_fuel_hea242.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_men, ModItems.rbmk_fuel_men.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_hen, ModItems.rbmk_fuel_hen.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_mox, ModItems.rbmk_fuel_mox.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_les, ModItems.rbmk_fuel_les.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_mes, ModItems.rbmk_fuel_mes.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_hes, ModItems.rbmk_fuel_hes.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_leaus, ModItems.rbmk_fuel_leaus.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_heaus, ModItems.rbmk_fuel_heaus.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_po210be, ModItems.rbmk_fuel_po210be.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_ra226be, ModItems.rbmk_fuel_ra226be.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_pu238be, ModItems.rbmk_fuel_pu238be.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_balefire_gold,
				ModItems.rbmk_fuel_balefire_gold.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_flashlead, ModItems.rbmk_fuel_flashlead.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_balefire, ModItems.rbmk_fuel_balefire.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_zfb_bismuth, ModItems.rbmk_fuel_zfb_bismuth.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_zfb_pu241, ModItems.rbmk_fuel_zfb_pu241.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_zfb_am_mix, ModItems.rbmk_fuel_zfb_am_mix.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_drx, ModItems.rbmk_fuel_drx.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_fuel_test, ModItems.rbmk_fuel_test.getUnlocalizedName());

		GameRegistry.registerItem(ModItems.rbmk_pellet_ueu, ModItems.rbmk_pellet_ueu.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_meu, ModItems.rbmk_pellet_meu.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_heu233, ModItems.rbmk_pellet_heu233.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_heu235, ModItems.rbmk_pellet_heu235.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_thmeu, ModItems.rbmk_pellet_thmeu.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_lep, ModItems.rbmk_pellet_lep.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_mep, ModItems.rbmk_pellet_mep.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_hep239, ModItems.rbmk_pellet_hep239.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_hep241, ModItems.rbmk_pellet_hep241.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_lea, ModItems.rbmk_pellet_lea.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_mea, ModItems.rbmk_pellet_mea.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_hea241, ModItems.rbmk_pellet_hea241.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_hea242, ModItems.rbmk_pellet_hea242.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_men, ModItems.rbmk_pellet_men.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_hen, ModItems.rbmk_pellet_hen.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_mox, ModItems.rbmk_pellet_mox.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_les, ModItems.rbmk_pellet_les.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_mes, ModItems.rbmk_pellet_mes.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_hes, ModItems.rbmk_pellet_hes.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_leaus, ModItems.rbmk_pellet_leaus.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_heaus, ModItems.rbmk_pellet_heaus.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_po210be, ModItems.rbmk_pellet_po210be.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_ra226be, ModItems.rbmk_pellet_ra226be.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_pu238be, ModItems.rbmk_pellet_pu238be.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_balefire_gold,
				ModItems.rbmk_pellet_balefire_gold.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_flashlead, ModItems.rbmk_pellet_flashlead.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_balefire, ModItems.rbmk_pellet_balefire.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_zfb_bismuth,
				ModItems.rbmk_pellet_zfb_bismuth.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_zfb_pu241, ModItems.rbmk_pellet_zfb_pu241.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_zfb_am_mix,
				ModItems.rbmk_pellet_zfb_am_mix.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_pellet_drx, ModItems.rbmk_pellet_drx.getUnlocalizedName());

		GameRegistry.registerItem(ModItems.watz_pellet, ModItems.watz_pellet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.watz_pellet_depleted, ModItems.watz_pellet_depleted.getUnlocalizedName());

		GameRegistry.registerItem(ModItems.debris_graphite, ModItems.debris_graphite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.debris_metal, ModItems.debris_metal.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.debris_fuel, ModItems.debris_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.debris_concrete, ModItems.debris_concrete.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.debris_exchanger, ModItems.debris_exchanger.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.debris_shrapnel, ModItems.debris_shrapnel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.debris_element, ModItems.debris_element.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.undefined, ModItems.undefined.getUnlocalizedName());

		GameRegistry.registerItem(ModItems.scrap_plastic, ModItems.scrap_plastic.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.scrap, ModItems.scrap.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.scrap_oil, ModItems.scrap_oil.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.scrap_nuclear, ModItems.scrap_nuclear.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.trinitite, ModItems.trinitite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nuclear_waste_long, ModItems.nuclear_waste_long.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nuclear_waste_long_tiny,
				ModItems.nuclear_waste_long_tiny.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nuclear_waste_short, ModItems.nuclear_waste_short.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nuclear_waste_short_tiny,
				ModItems.nuclear_waste_short_tiny.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nuclear_waste_long_depleted,
				ModItems.nuclear_waste_long_depleted.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nuclear_waste_long_depleted_tiny,
				ModItems.nuclear_waste_long_depleted_tiny.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nuclear_waste_short_depleted,
				ModItems.nuclear_waste_short_depleted.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nuclear_waste_short_depleted_tiny,
				ModItems.nuclear_waste_short_depleted_tiny.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nuclear_waste, ModItems.nuclear_waste.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nuclear_waste_tiny, ModItems.nuclear_waste_tiny.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nuclear_waste_vitrified,
				ModItems.nuclear_waste_vitrified.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nuclear_waste_vitrified_tiny,
				ModItems.nuclear_waste_vitrified_tiny.getUnlocalizedName());

		// Spawners
		GameRegistry.registerItem(ModItems.spawn_chopper, ModItems.spawn_chopper.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.spawn_worm, ModItems.spawn_worm.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.spawn_ufo, ModItems.spawn_ufo.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.spawn_duck, ModItems.spawn_duck.getUnlocalizedName());

		// Computer Tools
		GameRegistry.registerItem(ModItems.designator, ModItems.designator.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.designator_range, ModItems.designator_range.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.designator_manual, ModItems.designator_manual.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.designator_arty_range, ModItems.designator_arty_range.getUnlocalizedName());
		GameRegistry.registerItem(turret_control, turret_control.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.turret_chip, ModItems.turret_chip.getUnlocalizedName());
		// GameRegistry.registerItem(turret_biometry,
		// turret_biometry.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.linker, ModItems.linker.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.reactor_sensor, ModItems.reactor_sensor.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.oil_detector, ModItems.oil_detector.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.survey_scanner, ModItems.survey_scanner.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mirror_tool, ModItems.mirror_tool.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rbmk_tool, ModItems.rbmk_tool.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.drone_linker, ModItems.drone_linker.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.coltan_tool, ModItems.coltan_tool.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.power_net_tool, ModItems.power_net_tool.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.analysis_tool, ModItems.analysis_tool.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.coupling_tool, ModItems.coupling_tool.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.dosimeter, ModItems.dosimeter.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.geiger_counter, ModItems.geiger_counter.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.digamma_diagnostic, ModItems.digamma_diagnostic.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pollution_detector, ModItems.pollution_detector.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.containment_box, ModItems.containment_box.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.plastic_bag, ModItems.plastic_bag.getUnlocalizedName());

		// Keys and Locks
		GameRegistry.registerItem(ModItems.key, ModItems.key.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.key_red, ModItems.key_red.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.key_kit, ModItems.key_kit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.key_fake, ModItems.key_fake.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mech_key, ModItems.mech_key.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pin, ModItems.pin.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.padlock_rusty, ModItems.padlock_rusty.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.padlock, ModItems.padlock.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.padlock_reinforced, ModItems.padlock_reinforced.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.padlock_unbreakable, ModItems.padlock_unbreakable.getUnlocalizedName());

		// Missiles
		GameRegistry.registerItem(ModItems.missile_generic, ModItems.missile_generic.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_anti_ballistic,
				ModItems.missile_anti_ballistic.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_incendiary, ModItems.missile_incendiary.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_cluster, ModItems.missile_cluster.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_buster, ModItems.missile_buster.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_strong, ModItems.missile_strong.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_incendiary_strong,
				ModItems.missile_incendiary_strong.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_cluster_strong,
				ModItems.missile_cluster_strong.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_buster_strong, ModItems.missile_buster_strong.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_emp_strong, ModItems.missile_emp_strong.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_burst, ModItems.missile_burst.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_inferno, ModItems.missile_inferno.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_rain, ModItems.missile_rain.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_drill, ModItems.missile_drill.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_nuclear, ModItems.missile_nuclear.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_nuclear_cluster,
				ModItems.missile_nuclear_cluster.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_volcano, ModItems.missile_volcano.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_endo, ModItems.missile_endo.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_exo, ModItems.missile_exo.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_doomsday, ModItems.missile_doomsday.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_taint, ModItems.missile_taint.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_micro, ModItems.missile_micro.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_bhole, ModItems.missile_bhole.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_schrabidium, ModItems.missile_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_emp, ModItems.missile_emp.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_shuttle, ModItems.missile_shuttle.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_carrier, ModItems.missile_carrier.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_soyuz, ModItems.missile_soyuz.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_soyuz_lander, ModItems.missile_soyuz_lander.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_custom, ModItems.missile_custom.getUnlocalizedName());

		// Missile Parts
		GameRegistry.registerItem(ModItems.mp_thruster_10_kerosene,
				ModItems.mp_thruster_10_kerosene.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_thruster_10_kerosene_tec,
				ModItems.mp_thruster_10_kerosene_tec.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_thruster_10_solid, ModItems.mp_thruster_10_solid.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_thruster_10_xenon, ModItems.mp_thruster_10_xenon.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_thruster_15_kerosene,
				ModItems.mp_thruster_15_kerosene.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_thruster_15_kerosene_tec,
				ModItems.mp_thruster_15_kerosene_tec.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_thruster_15_kerosene_dual,
				ModItems.mp_thruster_15_kerosene_dual.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_thruster_15_kerosene_triple,
				ModItems.mp_thruster_15_kerosene_triple.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_thruster_15_solid, ModItems.mp_thruster_15_solid.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_thruster_15_solid_hexdecuple,
				ModItems.mp_thruster_15_solid_hexdecuple.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_thruster_15_hydrogen,
				ModItems.mp_thruster_15_hydrogen.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_thruster_15_hydrogen_dual,
				ModItems.mp_thruster_15_hydrogen_dual.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_thruster_15_balefire_short,
				ModItems.mp_thruster_15_balefire_short.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_thruster_15_balefire,
				ModItems.mp_thruster_15_balefire.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_thruster_15_balefire_large,
				ModItems.mp_thruster_15_balefire_large.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_thruster_15_balefire_large_rad,
				ModItems.mp_thruster_15_balefire_large_rad.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_thruster_20_kerosene,
				ModItems.mp_thruster_20_kerosene.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_thruster_20_kerosene_dual,
				ModItems.mp_thruster_20_kerosene_dual.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_thruster_20_kerosene_triple,
				ModItems.mp_thruster_20_kerosene_triple.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_thruster_20_solid, ModItems.mp_thruster_20_solid.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_thruster_20_solid_multi,
				ModItems.mp_thruster_20_solid_multi.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_thruster_20_solid_multier,
				ModItems.mp_thruster_20_solid_multier.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_stability_10_flat, ModItems.mp_stability_10_flat.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_stability_10_cruise,
				ModItems.mp_stability_10_cruise.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_stability_10_space, ModItems.mp_stability_10_space.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_stability_15_flat, ModItems.mp_stability_15_flat.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_stability_15_thin, ModItems.mp_stability_15_thin.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_stability_15_soyuz, ModItems.mp_stability_15_soyuz.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_stability_20_flat, ModItems.mp_stability_20_flat.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_kerosene,
				ModItems.mp_fuselage_10_kerosene.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_kerosene_camo,
				ModItems.mp_fuselage_10_kerosene_camo.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_kerosene_desert,
				ModItems.mp_fuselage_10_kerosene_desert.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_kerosene_sky,
				ModItems.mp_fuselage_10_kerosene_sky.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_kerosene_flames,
				ModItems.mp_fuselage_10_kerosene_flames.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_kerosene_insulation,
				ModItems.mp_fuselage_10_kerosene_insulation.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_kerosene_sleek,
				ModItems.mp_fuselage_10_kerosene_sleek.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_kerosene_metal,
				ModItems.mp_fuselage_10_kerosene_metal.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_kerosene_taint,
				ModItems.mp_fuselage_10_kerosene_taint.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_solid, ModItems.mp_fuselage_10_solid.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_solid_flames,
				ModItems.mp_fuselage_10_solid_flames.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_solid_insulation,
				ModItems.mp_fuselage_10_solid_insulation.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_solid_sleek,
				ModItems.mp_fuselage_10_solid_sleek.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_solid_soviet_glory,
				ModItems.mp_fuselage_10_solid_soviet_glory.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_solid_cathedral,
				ModItems.mp_fuselage_10_solid_cathedral.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_solid_moonlit,
				ModItems.mp_fuselage_10_solid_moonlit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_solid_battery,
				ModItems.mp_fuselage_10_solid_battery.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_solid_duracell,
				ModItems.mp_fuselage_10_solid_duracell.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_xenon, ModItems.mp_fuselage_10_xenon.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_xenon_bhole,
				ModItems.mp_fuselage_10_xenon_bhole.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_long_kerosene,
				ModItems.mp_fuselage_10_long_kerosene.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_long_kerosene_camo,
				ModItems.mp_fuselage_10_long_kerosene_camo.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_long_kerosene_desert,
				ModItems.mp_fuselage_10_long_kerosene_desert.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_long_kerosene_sky,
				ModItems.mp_fuselage_10_long_kerosene_sky.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_long_kerosene_flames,
				ModItems.mp_fuselage_10_long_kerosene_flames.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_long_kerosene_insulation,
				ModItems.mp_fuselage_10_long_kerosene_insulation.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_long_kerosene_sleek,
				ModItems.mp_fuselage_10_long_kerosene_sleek.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_long_kerosene_metal,
				ModItems.mp_fuselage_10_long_kerosene_metal.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_long_kerosene_dash,
				ModItems.mp_fuselage_10_long_kerosene_dash.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_long_kerosene_taint,
				ModItems.mp_fuselage_10_long_kerosene_taint.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_long_kerosene_vap,
				ModItems.mp_fuselage_10_long_kerosene_vap.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_long_solid,
				ModItems.mp_fuselage_10_long_solid.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_long_solid_flames,
				ModItems.mp_fuselage_10_long_solid_flames.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_long_solid_insulation,
				ModItems.mp_fuselage_10_long_solid_insulation.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_long_solid_sleek,
				ModItems.mp_fuselage_10_long_solid_sleek.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_long_solid_soviet_glory,
				ModItems.mp_fuselage_10_long_solid_soviet_glory.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_long_solid_bullet,
				ModItems.mp_fuselage_10_long_solid_bullet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_long_solid_silvermoonlight,
				ModItems.mp_fuselage_10_long_solid_silvermoonlight.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_15_kerosene,
				ModItems.mp_fuselage_10_15_kerosene.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_15_solid,
				ModItems.mp_fuselage_10_15_solid.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_15_hydrogen,
				ModItems.mp_fuselage_10_15_hydrogen.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_10_15_balefire,
				ModItems.mp_fuselage_10_15_balefire.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_kerosene,
				ModItems.mp_fuselage_15_kerosene.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_kerosene_camo,
				ModItems.mp_fuselage_15_kerosene_camo.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_kerosene_desert,
				ModItems.mp_fuselage_15_kerosene_desert.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_kerosene_sky,
				ModItems.mp_fuselage_15_kerosene_sky.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_kerosene_insulation,
				ModItems.mp_fuselage_15_kerosene_insulation.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_kerosene_metal,
				ModItems.mp_fuselage_15_kerosene_metal.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_kerosene_decorated,
				ModItems.mp_fuselage_15_kerosene_decorated.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_kerosene_steampunk,
				ModItems.mp_fuselage_15_kerosene_steampunk.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_kerosene_polite,
				ModItems.mp_fuselage_15_kerosene_polite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_kerosene_blackjack,
				ModItems.mp_fuselage_15_kerosene_blackjack.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_kerosene_lambda,
				ModItems.mp_fuselage_15_kerosene_lambda.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_kerosene_minuteman,
				ModItems.mp_fuselage_15_kerosene_minuteman.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_kerosene_pip,
				ModItems.mp_fuselage_15_kerosene_pip.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_kerosene_taint,
				ModItems.mp_fuselage_15_kerosene_taint.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_kerosene_yuck,
				ModItems.mp_fuselage_15_kerosene_yuck.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_solid, ModItems.mp_fuselage_15_solid.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_solid_insulation,
				ModItems.mp_fuselage_15_solid_insulation.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_solid_desh,
				ModItems.mp_fuselage_15_solid_desh.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_solid_soviet_glory,
				ModItems.mp_fuselage_15_solid_soviet_glory.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_solid_soviet_stank,
				ModItems.mp_fuselage_15_solid_soviet_stank.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_solid_faust,
				ModItems.mp_fuselage_15_solid_faust.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_solid_silvermoonlight,
				ModItems.mp_fuselage_15_solid_silvermoonlight.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_solid_snowy,
				ModItems.mp_fuselage_15_solid_snowy.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_solid_panorama,
				ModItems.mp_fuselage_15_solid_panorama.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_solid_roses,
				ModItems.mp_fuselage_15_solid_roses.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_solid_mimi,
				ModItems.mp_fuselage_15_solid_mimi.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_hydrogen,
				ModItems.mp_fuselage_15_hydrogen.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_hydrogen_cathedral,
				ModItems.mp_fuselage_15_hydrogen_cathedral.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_balefire,
				ModItems.mp_fuselage_15_balefire.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_20_kerosene,
				ModItems.mp_fuselage_15_20_kerosene.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_20_kerosene_magnusson,
				ModItems.mp_fuselage_15_20_kerosene_magnusson.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_15_20_solid,
				ModItems.mp_fuselage_15_20_solid.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_fuselage_20_kerosene,
				ModItems.mp_fuselage_20_kerosene.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_warhead_10_he, ModItems.mp_warhead_10_he.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_warhead_10_incendiary,
				ModItems.mp_warhead_10_incendiary.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_warhead_10_buster, ModItems.mp_warhead_10_buster.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_warhead_10_nuclear, ModItems.mp_warhead_10_nuclear.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_warhead_10_nuclear_large,
				ModItems.mp_warhead_10_nuclear_large.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_warhead_10_taint, ModItems.mp_warhead_10_taint.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_warhead_10_cloud, ModItems.mp_warhead_10_cloud.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_warhead_15_he, ModItems.mp_warhead_15_he.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_warhead_15_incendiary,
				ModItems.mp_warhead_15_incendiary.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_warhead_15_nuclear, ModItems.mp_warhead_15_nuclear.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_warhead_15_nuclear_shark,
				ModItems.mp_warhead_15_nuclear_shark.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_warhead_15_nuclear_mimi,
				ModItems.mp_warhead_15_nuclear_mimi.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_warhead_15_boxcar, ModItems.mp_warhead_15_boxcar.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_warhead_15_n2, ModItems.mp_warhead_15_n2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_warhead_15_balefire,
				ModItems.mp_warhead_15_balefire.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_warhead_15_turbine, ModItems.mp_warhead_15_turbine.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_warhead_20_he, ModItems.mp_warhead_20_he.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_chip_1, ModItems.mp_chip_1.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_chip_2, ModItems.mp_chip_2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_chip_3, ModItems.mp_chip_3.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_chip_4, ModItems.mp_chip_4.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mp_chip_5, ModItems.mp_chip_5.getUnlocalizedName());

		/*
		 * GameRegistry.registerItem(missile_skin_camo,
		 * missile_skin_camo.getUnlocalizedName());
		 * GameRegistry.registerItem(missile_skin_desert,
		 * missile_skin_desert.getUnlocalizedName());
		 * GameRegistry.registerItem(missile_skin_flames,
		 * missile_skin_flames.getUnlocalizedName());
		 * GameRegistry.registerItem(missile_skin_manly_pink,
		 * missile_skin_manly_pink.getUnlocalizedName());
		 * GameRegistry.registerItem(missile_skin_orange_insulation,
		 * missile_skin_orange_insulation.getUnlocalizedName());
		 * GameRegistry.registerItem(missile_skin_sleek,
		 * missile_skin_sleek.getUnlocalizedName());
		 * GameRegistry.registerItem(missile_skin_soviet_glory,
		 * missile_skin_soviet_glory.getUnlocalizedName());
		 * GameRegistry.registerItem(missile_skin_soviet_stank,
		 * missile_skin_soviet_stank.getUnlocalizedName());
		 * GameRegistry.registerItem(missile_skin_metal,
		 * missile_skin_metal.getUnlocalizedName());
		 */

		// Satellites
		GameRegistry.registerItem(ModItems.sat_mapper, ModItems.sat_mapper.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.sat_scanner, ModItems.sat_scanner.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.sat_radar, ModItems.sat_radar.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.sat_laser, ModItems.sat_laser.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.sat_foeq, ModItems.sat_foeq.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.sat_resonator, ModItems.sat_resonator.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.sat_miner, ModItems.sat_miner.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.sat_lunar_miner, ModItems.sat_lunar_miner.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.sat_gerald, ModItems.sat_gerald.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.sat_chip, ModItems.sat_chip.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.sat_interface, ModItems.sat_interface.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.sat_coord, ModItems.sat_coord.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.sat_designator, ModItems.sat_designator.getUnlocalizedName());

		// Guns
		GameRegistry.registerItem(ModItems.gun_revolver, ModItems.gun_revolver.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_revolver_saturnite,
				ModItems.gun_revolver_saturnite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_revolver_gold, ModItems.gun_revolver_gold.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_revolver_schrabidium,
				ModItems.gun_revolver_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_revolver_cursed, ModItems.gun_revolver_cursed.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_revolver_nightmare,
				ModItems.gun_revolver_nightmare.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_revolver_nightmare2,
				ModItems.gun_revolver_nightmare2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_revolver_pip, ModItems.gun_revolver_pip.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_revolver_nopip, ModItems.gun_revolver_nopip.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_revolver_blackjack,
				ModItems.gun_revolver_blackjack.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_revolver_silver, ModItems.gun_revolver_silver.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_revolver_red, ModItems.gun_revolver_red.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_bio_revolver, ModItems.gun_bio_revolver.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_deagle, ModItems.gun_deagle.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_uac_pistol, ModItems.gun_uac_pistol.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_flechette, ModItems.gun_flechette.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_ar15, ModItems.gun_ar15.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_calamity, ModItems.gun_calamity.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_m2, ModItems.gun_m2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_minigun, ModItems.gun_minigun.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_avenger, ModItems.gun_avenger.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_lacunae, ModItems.gun_lacunae.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_folly, ModItems.gun_folly.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_b92, ModItems.gun_b92.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_b93, ModItems.gun_b93.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_rpg, ModItems.gun_rpg.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_karl, ModItems.gun_karl.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_panzerschreck, ModItems.gun_panzerschreck.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_quadro, ModItems.gun_quadro.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_hk69, ModItems.gun_hk69.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_congolake, ModItems.gun_congolake.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_stinger, ModItems.gun_stinger.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_fatman, ModItems.gun_fatman.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_proto, ModItems.gun_proto.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_mirv, ModItems.gun_mirv.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_bf, ModItems.gun_bf.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_chemthrower, ModItems.gun_chemthrower.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_mp40, ModItems.gun_mp40.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_thompson, ModItems.gun_thompson.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_uzi, ModItems.gun_uzi.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_uzi_silencer, ModItems.gun_uzi_silencer.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_uzi_saturnite, ModItems.gun_uzi_saturnite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_uzi_saturnite_silencer,
				ModItems.gun_uzi_saturnite_silencer.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_uboinik, ModItems.gun_uboinik.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_remington, ModItems.gun_remington.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_spas12, ModItems.gun_spas12.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_benelli, ModItems.gun_benelli.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_supershotgun, ModItems.gun_supershotgun.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_ks23, ModItems.gun_ks23.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_sauer, ModItems.gun_sauer.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_lever_action, ModItems.gun_lever_action.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_lever_action_dark, ModItems.gun_lever_action_dark.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_lever_action_sonata,
				ModItems.gun_lever_action_sonata.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_bolt_action, ModItems.gun_bolt_action.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_bolt_action_green, ModItems.gun_bolt_action_green.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_bolt_action_saturnite,
				ModItems.gun_bolt_action_saturnite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_mymy, ModItems.gun_mymy.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_coilgun, ModItems.gun_coilgun.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_xvl1456, ModItems.gun_xvl1456.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_osipr, ModItems.gun_osipr.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_immolator, ModItems.gun_immolator.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_flamer, ModItems.gun_flamer.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_cryolator, ModItems.gun_cryolator.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_cryocannon, ModItems.gun_cryocannon.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_fireext, ModItems.gun_fireext.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_mp, ModItems.gun_mp.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_bolter, ModItems.gun_bolter.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_bolter_digamma, ModItems.gun_bolter_digamma.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_zomg, ModItems.gun_zomg.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_emp, ModItems.gun_emp.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_revolver_inverted, ModItems.gun_revolver_inverted.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_jack, ModItems.gun_jack.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_spark, ModItems.gun_spark.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_hp, ModItems.gun_hp.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_euthanasia, ModItems.gun_euthanasia.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_skystinger, ModItems.gun_skystinger.getUnlocalizedName());
		// GameRegistry.registerItem(gun_dash, gun_dash.getUnlocalizedName());
		// GameRegistry.registerItem(gun_twigun, gun_twigun.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_defabricator, ModItems.gun_defabricator.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_vortex, ModItems.gun_vortex.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_super_shotgun, ModItems.gun_super_shotgun.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_moist_nugget, ModItems.gun_moist_nugget.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_dampfmaschine, ModItems.gun_dampfmaschine.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_darter, ModItems.gun_darter.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_detonator, ModItems.gun_detonator.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crucible, ModItems.crucible.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_glass_cannon, ModItems.gun_glass_cannon.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_lunatic_marksman, ModItems.gun_lunatic_marksman.getUnlocalizedName());

		// Ammo
		/*
		 * GameRegistry.registerItem(gun_revolver_iron_ammo,
		 * gun_revolver_iron_ammo.getUnlocalizedName());
		 * GameRegistry.registerItem(gun_revolver_ammo,
		 * gun_revolver_ammo.getUnlocalizedName());
		 * GameRegistry.registerItem(gun_revolver_gold_ammo,
		 * gun_revolver_gold_ammo.getUnlocalizedName());
		 * GameRegistry.registerItem(gun_revolver_lead_ammo,
		 * gun_revolver_lead_ammo.getUnlocalizedName());
		 * GameRegistry.registerItem(gun_revolver_schrabidium_ammo,
		 * gun_revolver_schrabidium_ammo.getUnlocalizedName());
		 * GameRegistry.registerItem(gun_revolver_cursed_ammo,
		 * gun_revolver_cursed_ammo.getUnlocalizedName());
		 * GameRegistry.registerItem(gun_revolver_nightmare_ammo,
		 * gun_revolver_nightmare_ammo.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_357_desh, ammo_357_desh.getUnlocalizedName());
		 * GameRegistry.registerItem(gun_revolver_nightmare2_ammo,
		 * gun_revolver_nightmare2_ammo.getUnlocalizedName());
		 */
		// GameRegistry.registerItem(gun_revolver_pip_ammo,
		// gun_revolver_pip_ammo.getUnlocalizedName());
		// GameRegistry.registerItem(gun_revolver_nopip_ammo,
		// gun_revolver_nopip_ammo.getUnlocalizedName());
		// GameRegistry.registerItem(gun_calamity_ammo,
		// gun_calamity_ammo.getUnlocalizedName());
		// GameRegistry.registerItem(gun_lacunae_ammo,
		// gun_lacunae_ammo.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_b92_ammo, ModItems.gun_b92_ammo.getUnlocalizedName());
		// GameRegistry.registerItem(gun_rpg_ammo, gun_rpg_ammo.getUnlocalizedName());
		// GameRegistry.registerItem(gun_stinger_ammo,
		// gun_stinger_ammo.getUnlocalizedName());
		// GameRegistry.registerItem(gun_fatman_ammo,
		// gun_fatman_ammo.getUnlocalizedName());
		// GameRegistry.registerItem(gun_mirv_ammo, gun_mirv_ammo.getUnlocalizedName());
		// GameRegistry.registerItem(gun_mp40_ammo, gun_mp40_ammo.getUnlocalizedName());
		// GameRegistry.registerItem(gun_uzi_ammo, gun_uzi_ammo.getUnlocalizedName());
		// GameRegistry.registerItem(gun_uboinik_ammo,
		// gun_uboinik_ammo.getUnlocalizedName());
		// GameRegistry.registerItem(gun_lever_action_ammo,
		// gun_lever_action_ammo.getUnlocalizedName());
		// GameRegistry.registerItem(gun_bolt_action_ammo,
		// gun_bolt_action_ammo.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_xvl1456_ammo, ModItems.gun_xvl1456_ammo.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_osipr_ammo, ModItems.gun_osipr_ammo.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_osipr_ammo2, ModItems.gun_osipr_ammo2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_immolator_ammo, ModItems.gun_immolator_ammo.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_cryolator_ammo, ModItems.gun_cryolator_ammo.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_emp_ammo, ModItems.gun_emp_ammo.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_jack_ammo, ModItems.gun_jack_ammo.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_spark_ammo, ModItems.gun_spark_ammo.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_hp_ammo, ModItems.gun_hp_ammo.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_defabricator_ammo, ModItems.gun_defabricator_ammo.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_euthanasia_ammo, ModItems.gun_euthanasia_ammo.getUnlocalizedName());

		GameRegistry.registerItem(ModItems.ammo_12gauge, ModItems.ammo_12gauge.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ammo_20gauge, ModItems.ammo_20gauge.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ammo_4gauge, ModItems.ammo_4gauge.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ammo_357, ModItems.ammo_357.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ammo_44, ModItems.ammo_44.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ammo_45, ModItems.ammo_45.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ammo_5mm, ModItems.ammo_5mm.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ammo_9mm, ModItems.ammo_9mm.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ammo_556, ModItems.ammo_556.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ammo_762, ModItems.ammo_762.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ammo_22lr, ModItems.ammo_22lr.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ammo_50ae, ModItems.ammo_50ae.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ammo_50bmg, ModItems.ammo_50bmg.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ammo_75bolt, ModItems.ammo_75bolt.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ammo_nuke, ModItems.ammo_nuke.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ammo_fuel, ModItems.ammo_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ammo_fireext, ModItems.ammo_fireext.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ammo_cell, ModItems.ammo_cell.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ammo_dart, ModItems.ammo_dart.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ammo_rocket, ModItems.ammo_rocket.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ammo_stinger_rocket, ModItems.ammo_stinger_rocket.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ammo_luna_sniper, ModItems.ammo_luna_sniper.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ammo_coilgun, ModItems.ammo_coilgun.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ammo_grenade, ModItems.ammo_grenade.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ammo_shell, ModItems.ammo_shell.getUnlocalizedName());

		/*
		 * GameRegistry.registerItem(ammo_12gauge, ammo_12gauge.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_12gauge_incendiary,
		 * ammo_12gauge_incendiary.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_12gauge_shrapnel,
		 * ammo_12gauge_shrapnel.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_12gauge_du,
		 * ammo_12gauge_du.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_12gauge_sleek,
		 * ammo_12gauge_sleek.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_12gauge_marauder,
		 * ammo_12gauge_marauder.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_20gauge, ammo_20gauge.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_20gauge_slug,
		 * ammo_20gauge_slug.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_20gauge_flechette,
		 * ammo_20gauge_flechette.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_20gauge_incendiary,
		 * ammo_20gauge_incendiary.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_20gauge_shrapnel,
		 * ammo_20gauge_shrapnel.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_20gauge_explosive,
		 * ammo_20gauge_explosive.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_20gauge_caustic,
		 * ammo_20gauge_caustic.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_20gauge_shock,
		 * ammo_20gauge_shock.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_20gauge_wither,
		 * ammo_20gauge_wither.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_20gauge_sleek,
		 * ammo_20gauge_sleek.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_4gauge, ammo_4gauge.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_4gauge_slug,
		 * ammo_4gauge_slug.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_4gauge_flechette,
		 * ammo_4gauge_flechette.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_4gauge_flechette_phosphorus,
		 * ammo_4gauge_flechette_phosphorus.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_4gauge_explosive,
		 * ammo_4gauge_explosive.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_4gauge_semtex,
		 * ammo_4gauge_semtex.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_4gauge_balefire,
		 * ammo_4gauge_balefire.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_4gauge_kampf,
		 * ammo_4gauge_kampf.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_4gauge_canister,
		 * ammo_4gauge_canister.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_4gauge_claw,
		 * ammo_4gauge_claw.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_4gauge_vampire,
		 * ammo_4gauge_vampire.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_4gauge_void,
		 * ammo_4gauge_void.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_4gauge_titan,
		 * ammo_4gauge_titan.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_4gauge_sleek,
		 * ammo_4gauge_sleek.getUnlocalizedName()); GameRegistry.registerItem(ammo_44,
		 * ammo_44.getUnlocalizedName()); GameRegistry.registerItem(ammo_44_ap,
		 * ammo_44_ap.getUnlocalizedName()); GameRegistry.registerItem(ammo_44_du,
		 * ammo_44_du.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_44_phosphorus,
		 * ammo_44_phosphorus.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_44_star, ammo_44_star.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_44_chlorophyte,
		 * ammo_44_chlorophyte.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_44_pip, ammo_44_pip.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_44_bj, ammo_44_bj.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_44_silver,
		 * ammo_44_silver.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_44_rocket,
		 * ammo_44_rocket.getUnlocalizedName()); GameRegistry.registerItem(ammo_5mm,
		 * ammo_5mm.getUnlocalizedName()); GameRegistry.registerItem(ammo_5mm_explosive,
		 * ammo_5mm_explosive.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_5mm_du, ammo_5mm_du.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_5mm_star, ammo_5mm_star.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_5mm_chlorophyte,
		 * ammo_5mm_chlorophyte.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_9mm, ammo_9mm.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_9mm_ap, ammo_9mm_ap.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_9mm_du, ammo_9mm_du.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_9mm_chlorophyte,
		 * ammo_9mm_chlorophyte.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_9mm_rocket,
		 * ammo_9mm_rocket.getUnlocalizedName()); GameRegistry.registerItem(ammo_556,
		 * ammo_556.getUnlocalizedName()); GameRegistry.registerItem(ammo_566_gold,
		 * ammo_566_gold.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_556_phosphorus,
		 * ammo_556_phosphorus.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_556_ap, ammo_556_ap.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_556_du, ammo_556_du.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_556_star, ammo_556_star.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_556_chlorophyte,
		 * ammo_556_chlorophyte.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_556_sleek,
		 * ammo_556_sleek.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_556_tracer,
		 * ammo_556_tracer.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_556_flechette,
		 * ammo_556_flechette.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_556_flechette_incendiary,
		 * ammo_556_flechette_incendiary.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_556_flechette_phosphorus,
		 * ammo_556_flechette_phosphorus.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_556_flechette_du,
		 * ammo_556_flechette_du.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_556_flechette_chlorophyte,
		 * ammo_556_flechette_chlorophyte.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_556_flechette_sleek,
		 * ammo_556_flechette_sleek.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_556_k, ammo_556_k.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_22lr, ammo_22lr.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_22lr_ap, ammo_22lr_ap.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_22lr_chlorophyte,
		 * ammo_22lr_chlorophyte.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_50ae, ammo_50ae.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_50ae_ap, ammo_50ae_ap.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_50ae_du, ammo_50ae_du.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_50ae_star,
		 * ammo_50ae_star.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_50ae_chlorophyte,
		 * ammo_50ae_chlorophyte.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_50bmg, ammo_50bmg.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_50bmg_incendiary,
		 * ammo_50bmg_incendiary.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_50bmg_phosphorus,
		 * ammo_50bmg_phosphorus.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_50bmg_explosive,
		 * ammo_50bmg_explosive.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_50bmg_ap, ammo_50bmg_ap.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_50bmg_du, ammo_50bmg_du.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_50bmg_star,
		 * ammo_50bmg_star.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_50bmg_chlorophyte,
		 * ammo_50bmg_chlorophyte.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_50bmg_flechette,
		 * ammo_50bmg_flechette.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_50bmg_flechette_am,
		 * ammo_50bmg_flechette_am.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_50bmg_flechette_po,
		 * ammo_50bmg_flechette_po.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_50bmg_sleek,
		 * ammo_50bmg_sleek.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_75bolt, ammo_75bolt.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_75bolt_incendiary,
		 * ammo_75bolt_incendiary.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_75bolt_he,
		 * ammo_75bolt_he.getUnlocalizedName()); GameRegistry.registerItem(ammo_fuel,
		 * ammo_fuel.getUnlocalizedName()); GameRegistry.registerItem(ammo_fuel_napalm,
		 * ammo_fuel_napalm.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_fuel_phosphorus,
		 * ammo_fuel_phosphorus.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_fuel_vaporizer,
		 * ammo_fuel_vaporizer.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_fuel_gas, ammo_fuel_gas.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_fireext, ammo_fireext.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_fireext_foam,
		 * ammo_fireext_foam.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_fireext_sand,
		 * ammo_fireext_sand.getUnlocalizedName()); GameRegistry.registerItem(ammo_cell,
		 * ammo_cell.getUnlocalizedName()); GameRegistry.registerItem(ammo_dart,
		 * ammo_dart.getUnlocalizedName()); GameRegistry.registerItem(ammo_dart_nuclear,
		 * ammo_dart_nuclear.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_dart_nerf,
		 * ammo_dart_nerf.getUnlocalizedName()); GameRegistry.registerItem(ammo_rocket,
		 * ammo_rocket.getUnlocalizedName()); GameRegistry.registerItem(ammo_rocket_he,
		 * ammo_rocket_he.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_rocket_incendiary,
		 * ammo_rocket_incendiary.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_rocket_phosphorus,
		 * ammo_rocket_phosphorus.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_rocket_shrapnel,
		 * ammo_rocket_shrapnel.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_rocket_emp,
		 * ammo_rocket_emp.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_rocket_glare,
		 * ammo_rocket_glare.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_rocket_toxic,
		 * ammo_rocket_toxic.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_rocket_canister,
		 * ammo_rocket_canister.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_rocket_sleek,
		 * ammo_rocket_sleek.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_rocket_nuclear,
		 * ammo_rocket_nuclear.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_rocket_rpc,
		 * ammo_rocket_rpc.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_rocket_digamma,
		 * ammo_rocket_digamma.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_stinger_rocket,
		 * ammo_stinger_rocket.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_stinger_rocket_he,
		 * ammo_stinger_rocket_he.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_stinger_rocket_incendiary,
		 * ammo_stinger_rocket_incendiary.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_stinger_rocket_nuclear,
		 * ammo_stinger_rocket_nuclear.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_stinger_rocket_bones,
		 * ammo_stinger_rocket_bones.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_grenade, ammo_grenade.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_grenade_he,
		 * ammo_grenade_he.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_grenade_incendiary,
		 * ammo_grenade_incendiary.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_grenade_phosphorus,
		 * ammo_grenade_phosphorus.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_grenade_toxic,
		 * ammo_grenade_toxic.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_grenade_concussion,
		 * ammo_grenade_concussion.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_grenade_finned,
		 * ammo_grenade_finned.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_grenade_sleek,
		 * ammo_grenade_sleek.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_grenade_nuclear,
		 * ammo_grenade_nuclear.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_grenade_tracer,
		 * ammo_grenade_tracer.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_grenade_kampf,
		 * ammo_grenade_kampf.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_shell, ammo_shell.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_shell_explosive,
		 * ammo_shell_explosive.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_shell_apfsds_t,
		 * ammo_shell_apfsds_t.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_shell_apfsds_du,
		 * ammo_shell_apfsds_du.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_shell_w9, ammo_shell_w9.getUnlocalizedName());
		 */
		GameRegistry.registerItem(ModItems.ammo_dgk, ModItems.ammo_dgk.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ammo_arty, ModItems.ammo_arty.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ammo_himars, ModItems.ammo_himars.getUnlocalizedName());
		/*
		 * GameRegistry.registerItem(ammo_nuke, ammo_nuke.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_nuke_low, ammo_nuke_low.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_nuke_high,
		 * ammo_nuke_high.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_nuke_tots,
		 * ammo_nuke_tots.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_nuke_safe,
		 * ammo_nuke_safe.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_nuke_pumpkin,
		 * ammo_nuke_pumpkin.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_nuke_barrel,
		 * ammo_nuke_barrel.getUnlocalizedName()); GameRegistry.registerItem(ammo_mirv,
		 * ammo_mirv.getUnlocalizedName()); GameRegistry.registerItem(ammo_mirv_low,
		 * ammo_mirv_low.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_mirv_high,
		 * ammo_mirv_high.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_mirv_safe,
		 * ammo_mirv_safe.getUnlocalizedName());
		 * GameRegistry.registerItem(ammo_mirv_special,
		 * ammo_mirv_special.getUnlocalizedName());
		 */
		GameRegistry.registerItem(ModItems.ammo_folly, ModItems.ammo_folly.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ammo_folly_nuclear, ModItems.ammo_folly_nuclear.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ammo_folly_du, ModItems.ammo_folly_du.getUnlocalizedName());

		// -C-l-i-p-s- Magazines
		GameRegistry.registerItem(ModItems.clip_revolver_iron, ModItems.clip_revolver_iron.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_revolver, ModItems.clip_revolver.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_revolver_gold, ModItems.clip_revolver_gold.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_revolver_lead, ModItems.clip_revolver_lead.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_revolver_schrabidium,
				ModItems.clip_revolver_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_revolver_cursed, ModItems.clip_revolver_cursed.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_revolver_nightmare,
				ModItems.clip_revolver_nightmare.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_revolver_nightmare2,
				ModItems.clip_revolver_nightmare2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_revolver_pip, ModItems.clip_revolver_pip.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_revolver_nopip, ModItems.clip_revolver_nopip.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_rpg, ModItems.clip_rpg.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_stinger, ModItems.clip_stinger.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_fatman, ModItems.clip_fatman.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_mirv, ModItems.clip_mirv.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_bf, ModItems.clip_bf.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_mp40, ModItems.clip_mp40.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_uzi, ModItems.clip_uzi.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_uboinik, ModItems.clip_uboinik.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_lever_action, ModItems.clip_lever_action.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_bolt_action, ModItems.clip_bolt_action.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_xvl1456, ModItems.clip_xvl1456.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_osipr, ModItems.clip_osipr.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_immolator, ModItems.clip_immolator.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_cryolator, ModItems.clip_cryolator.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_mp, ModItems.clip_mp.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_emp, ModItems.clip_emp.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_jack, ModItems.clip_jack.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_spark, ModItems.clip_spark.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_hp, ModItems.clip_hp.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_euthanasia, ModItems.clip_euthanasia.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.clip_defabricator, ModItems.clip_defabricator.getUnlocalizedName());

		GameRegistry.registerItem(ModItems.ammo_container, ModItems.ammo_container.getUnlocalizedName());

		// Grenades
		GameRegistry.registerItem(ModItems.stick_dynamite, ModItems.stick_dynamite.getUnlocalizedName()); // heave-ho!
		GameRegistry.registerItem(ModItems.stick_dynamite_fishing,
				ModItems.stick_dynamite_fishing.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stick_tnt, ModItems.stick_tnt.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stick_semtex, ModItems.stick_semtex.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stick_c4, ModItems.stick_c4.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_generic, ModItems.grenade_generic.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_strong, ModItems.grenade_strong.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_frag, ModItems.grenade_frag.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_fire, ModItems.grenade_fire.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_shrapnel, ModItems.grenade_shrapnel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_cluster, ModItems.grenade_cluster.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_flare, ModItems.grenade_flare.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_electric, ModItems.grenade_electric.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_poison, ModItems.grenade_poison.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_gas, ModItems.grenade_gas.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_cloud, ModItems.grenade_cloud.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_pink_cloud, ModItems.grenade_pink_cloud.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_smart, ModItems.grenade_smart.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_mirv, ModItems.grenade_mirv.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_breach, ModItems.grenade_breach.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_burst, ModItems.grenade_burst.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_pulse, ModItems.grenade_pulse.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_plasma, ModItems.grenade_plasma.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_tau, ModItems.grenade_tau.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_schrabidium, ModItems.grenade_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_nuke, ModItems.grenade_nuke.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_lemon, ModItems.grenade_lemon.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_gascan, ModItems.grenade_gascan.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_kyiv, ModItems.grenade_kyiv.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_mk2, ModItems.grenade_mk2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_aschrab, ModItems.grenade_aschrab.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_nuclear, ModItems.grenade_nuclear.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_zomg, ModItems.grenade_zomg.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_black_hole, ModItems.grenade_black_hole.getUnlocalizedName());

		GameRegistry.registerItem(ModItems.grenade_if_generic, ModItems.grenade_if_generic.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_if_he, ModItems.grenade_if_he.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_if_bouncy, ModItems.grenade_if_bouncy.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_if_sticky, ModItems.grenade_if_sticky.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_if_impact, ModItems.grenade_if_impact.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_if_incendiary, ModItems.grenade_if_incendiary.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_if_toxic, ModItems.grenade_if_toxic.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_if_concussion, ModItems.grenade_if_concussion.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_if_brimstone, ModItems.grenade_if_brimstone.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_if_mystery, ModItems.grenade_if_mystery.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_if_spark, ModItems.grenade_if_spark.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_if_hopwire, ModItems.grenade_if_hopwire.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_if_null, ModItems.grenade_if_null.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nuclear_waste_pearl, ModItems.nuclear_waste_pearl.getUnlocalizedName());

		GameRegistry.registerItem(ModItems.ullapool_caber, ModItems.ullapool_caber.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.weaponized_starblaster_cell,
				ModItems.weaponized_starblaster_cell.getUnlocalizedName());

		// Capes
		GameRegistry.registerItem(ModItems.cape_radiation, ModItems.cape_radiation.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cape_gasmask, ModItems.cape_gasmask.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cape_schrabidium, ModItems.cape_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cape_hidden, ModItems.cape_hidden.getUnlocalizedName());

		// Tools
		GameRegistry.registerItem(ModItems.schrabidium_sword, ModItems.schrabidium_sword.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.schrabidium_hammer, ModItems.schrabidium_hammer.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.shimmer_sledge, ModItems.shimmer_sledge.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.shimmer_axe, ModItems.shimmer_axe.getUnlocalizedName());
		// GameRegistry.registerItem(pch, pch.getUnlocalizedName()); //sike, nevermind
		GameRegistry.registerItem(ModItems.wood_gavel, ModItems.wood_gavel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.lead_gavel, ModItems.lead_gavel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.diamond_gavel, ModItems.diamond_gavel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mese_gavel, ModItems.mese_gavel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.schrabidium_pickaxe, ModItems.schrabidium_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.schrabidium_axe, ModItems.schrabidium_axe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.schrabidium_shovel, ModItems.schrabidium_shovel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.schrabidium_hoe, ModItems.schrabidium_hoe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.steel_sword, ModItems.steel_sword.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.steel_pickaxe, ModItems.steel_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.steel_axe, ModItems.steel_axe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.steel_shovel, ModItems.steel_shovel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.steel_hoe, ModItems.steel_hoe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.titanium_sword, ModItems.titanium_sword.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.titanium_pickaxe, ModItems.titanium_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.titanium_axe, ModItems.titanium_axe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.titanium_shovel, ModItems.titanium_shovel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.titanium_hoe, ModItems.titanium_hoe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cobalt_sword, ModItems.cobalt_sword.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cobalt_pickaxe, ModItems.cobalt_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cobalt_axe, ModItems.cobalt_axe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cobalt_shovel, ModItems.cobalt_shovel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cobalt_hoe, ModItems.cobalt_hoe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cobalt_decorated_sword,
				ModItems.cobalt_decorated_sword.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cobalt_decorated_pickaxe,
				ModItems.cobalt_decorated_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cobalt_decorated_axe, ModItems.cobalt_decorated_axe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cobalt_decorated_shovel,
				ModItems.cobalt_decorated_shovel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cobalt_decorated_hoe, ModItems.cobalt_decorated_hoe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.starmetal_sword, ModItems.starmetal_sword.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.starmetal_pickaxe, ModItems.starmetal_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.starmetal_axe, ModItems.starmetal_axe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.starmetal_shovel, ModItems.starmetal_shovel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.starmetal_hoe, ModItems.starmetal_hoe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.alloy_sword, ModItems.alloy_sword.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.alloy_pickaxe, ModItems.alloy_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.alloy_axe, ModItems.alloy_axe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.alloy_shovel, ModItems.alloy_shovel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.alloy_hoe, ModItems.alloy_hoe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cmb_sword, ModItems.cmb_sword.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cmb_pickaxe, ModItems.cmb_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cmb_axe, ModItems.cmb_axe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cmb_shovel, ModItems.cmb_shovel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cmb_hoe, ModItems.cmb_hoe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.desh_sword, ModItems.desh_sword.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.desh_pickaxe, ModItems.desh_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.desh_axe, ModItems.desh_axe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.desh_shovel, ModItems.desh_shovel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.desh_hoe, ModItems.desh_hoe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.elec_sword, ModItems.elec_sword.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.elec_pickaxe, ModItems.elec_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.elec_axe, ModItems.elec_axe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.elec_shovel, ModItems.elec_shovel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.dnt_sword, ModItems.dnt_sword.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.smashing_hammer, ModItems.smashing_hammer.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.centri_stick, ModItems.centri_stick.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.drax, ModItems.drax.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.drax_mk2, ModItems.drax_mk2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.drax_mk3, ModItems.drax_mk3.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bismuth_pickaxe, ModItems.bismuth_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.volcanic_pickaxe, ModItems.volcanic_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.chlorophyte_pickaxe, ModItems.chlorophyte_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mese_pickaxe, ModItems.mese_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.matchstick, ModItems.matchstick.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.balefire_and_steel, ModItems.balefire_and_steel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crowbar, ModItems.crowbar.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.wrench, ModItems.wrench.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.wrench_archineer, ModItems.wrench_archineer.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.wrench_flipped, ModItems.wrench_flipped.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.memespoon, ModItems.memespoon.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.saw, ModItems.saw.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bat, ModItems.bat.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bat_nail, ModItems.bat_nail.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.golf_club, ModItems.golf_club.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pipe_rusty, ModItems.pipe_rusty.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pipe_lead, ModItems.pipe_lead.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.reer_graar, ModItems.reer_graar.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stopsign, ModItems.stopsign.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.sopsign, ModItems.sopsign.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.chernobylsign, ModItems.chernobylsign.getUnlocalizedName());

		GameRegistry.registerItem(ModItems.meteorite_sword, ModItems.meteorite_sword.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.meteorite_sword_seared,
				ModItems.meteorite_sword_seared.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.meteorite_sword_reforged,
				ModItems.meteorite_sword_reforged.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.meteorite_sword_hardened,
				ModItems.meteorite_sword_hardened.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.meteorite_sword_alloyed,
				ModItems.meteorite_sword_alloyed.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.meteorite_sword_machined,
				ModItems.meteorite_sword_machined.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.meteorite_sword_treated,
				ModItems.meteorite_sword_treated.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.meteorite_sword_etched,
				ModItems.meteorite_sword_etched.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.meteorite_sword_bred, ModItems.meteorite_sword_bred.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.meteorite_sword_irradiated,
				ModItems.meteorite_sword_irradiated.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.meteorite_sword_fused, ModItems.meteorite_sword_fused.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.meteorite_sword_baleful,
				ModItems.meteorite_sword_baleful.getUnlocalizedName());

		// Multitool
		GameRegistry.registerItem(ModItems.multitool_hit, ModItems.multitool_hit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.multitool_dig, ModItems.multitool_dig.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.multitool_silk, ModItems.multitool_silk.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.multitool_ext, ModItems.multitool_ext.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.multitool_miner, ModItems.multitool_miner.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.multitool_beam, ModItems.multitool_beam.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.multitool_sky, ModItems.multitool_sky.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.multitool_mega, ModItems.multitool_mega.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.multitool_joule, ModItems.multitool_joule.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.multitool_decon, ModItems.multitool_decon.getUnlocalizedName());

		// Syringes & Pills
		GameRegistry.registerItem(ModItems.syringe_empty, ModItems.syringe_empty.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.syringe_antidote, ModItems.syringe_antidote.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.syringe_poison, ModItems.syringe_poison.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.syringe_awesome, ModItems.syringe_awesome.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.syringe_metal_empty, ModItems.syringe_metal_empty.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.syringe_metal_stimpak, ModItems.syringe_metal_stimpak.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.syringe_metal_medx, ModItems.syringe_metal_medx.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.syringe_metal_psycho, ModItems.syringe_metal_psycho.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.syringe_metal_super, ModItems.syringe_metal_super.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.syringe_taint, ModItems.syringe_taint.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.syringe_mkunicorn, ModItems.syringe_mkunicorn.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.med_bag, ModItems.med_bag.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.iv_empty, ModItems.iv_empty.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.iv_blood, ModItems.iv_blood.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.iv_xp_empty, ModItems.iv_xp_empty.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.iv_xp, ModItems.iv_xp.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.radaway, ModItems.radaway.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.radaway_strong, ModItems.radaway_strong.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.radaway_flush, ModItems.radaway_flush.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.radx, ModItems.radx.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.siox, ModItems.siox.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pill_herbal, ModItems.pill_herbal.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pill_iodine, ModItems.pill_iodine.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.xanax, ModItems.xanax.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fmn, ModItems.fmn.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.five_htp, ModItems.five_htp.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.plan_c, ModItems.plan_c.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pill_red, ModItems.pill_red.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.stealth_boy, ModItems.stealth_boy.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gas_mask_filter, ModItems.gas_mask_filter.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gas_mask_filter_mono, ModItems.gas_mask_filter_mono.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gas_mask_filter_combo, ModItems.gas_mask_filter_combo.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gas_mask_filter_rag, ModItems.gas_mask_filter_rag.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gas_mask_filter_piss, ModItems.gas_mask_filter_piss.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.jetpack_tank, ModItems.jetpack_tank.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_kit_1, ModItems.gun_kit_1.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gun_kit_2, ModItems.gun_kit_2.getUnlocalizedName());

		// Food
		GameRegistry.registerItem(ModItems.bomb_waffle, ModItems.bomb_waffle.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.schnitzel_vegan, ModItems.schnitzel_vegan.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cotton_candy, ModItems.cotton_candy.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.apple_lead, ModItems.apple_lead.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.apple_schrabidium, ModItems.apple_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.tem_flakes, ModItems.tem_flakes.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.glowing_stew, ModItems.glowing_stew.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.balefire_scrambled, ModItems.balefire_scrambled.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.balefire_and_ham, ModItems.balefire_and_ham.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.lemon, ModItems.lemon.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.definitelyfood, ModItems.definitelyfood.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.loops, ModItems.loops.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.loop_stew, ModItems.loop_stew.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.spongebob_macaroni, ModItems.spongebob_macaroni.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fooditem, ModItems.fooditem.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.twinkie, ModItems.twinkie.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.static_sandwich, ModItems.static_sandwich.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pudding, ModItems.pudding.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pancake, ModItems.pancake.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nugget, ModItems.nugget.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.peas, ModItems.peas.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.marshmallow, ModItems.marshmallow.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cheese, ModItems.cheese.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.quesadilla, ModItems.quesadilla.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.glyphid_meat, ModItems.glyphid_meat.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.glyphid_meat_grilled, ModItems.glyphid_meat_grilled.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.egg_glyphid, ModItems.egg_glyphid.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.med_ipecac, ModItems.med_ipecac.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.med_ptsd, ModItems.med_ptsd.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.canteen_13, ModItems.canteen_13.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.canteen_vodka, ModItems.canteen_vodka.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.canteen_fab, ModItems.canteen_fab.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mucho_mango, ModItems.mucho_mango.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.chocolate, ModItems.chocolate.getUnlocalizedName());

		// Energy Drinks
		GameRegistry.registerItem(ModItems.can_empty, ModItems.can_empty.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.can_smart, ModItems.can_smart.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.can_creature, ModItems.can_creature.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.can_redbomb, ModItems.can_redbomb.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.can_mrsugar, ModItems.can_mrsugar.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.can_overcharge, ModItems.can_overcharge.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.can_luna, ModItems.can_luna.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.can_bepis, ModItems.can_bepis.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.can_breen, ModItems.can_breen.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.can_mug, ModItems.can_mug.getUnlocalizedName());

		// Coffee
		GameRegistry.registerItem(ModItems.coffee, ModItems.coffee.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.coffee_radium, ModItems.coffee_radium.getUnlocalizedName());

		// Cola
		GameRegistry.registerItem(ModItems.bottle_empty, ModItems.bottle_empty.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bottle_nuka, ModItems.bottle_nuka.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bottle_cherry, ModItems.bottle_cherry.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bottle_quantum, ModItems.bottle_quantum.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bottle_sparkle, ModItems.bottle_sparkle.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bottle_rad, ModItems.bottle_rad.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bottle2_empty, ModItems.bottle2_empty.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bottle2_korl, ModItems.bottle2_korl.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bottle2_fritz, ModItems.bottle2_fritz.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bottle2_korl_special, ModItems.bottle2_korl_special.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bottle2_fritz_special, ModItems.bottle2_fritz_special.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bottle2_sunset, ModItems.bottle2_sunset.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bottle_opener, ModItems.bottle_opener.getUnlocalizedName());

		// Flasks
		GameRegistry.registerItem(ModItems.flask_infusion, ModItems.flask_infusion.getUnlocalizedName());

		// Canned Food
		// GameRegistry.registerItem(canned_beef, canned_beef.getUnlocalizedName());
		// GameRegistry.registerItem(canned_tuna, canned_tuna.getUnlocalizedName());
		// GameRegistry.registerItem(canned_mystery,
		// canned_mystery.getUnlocalizedName());
		// GameRegistry.registerItem(canned_pashtet,
		// canned_pashtet.getUnlocalizedName());
		// GameRegistry.registerItem(canned_cheese, canned_cheese.getUnlocalizedName());
		// GameRegistry.registerItem(canned_jizz, canned_jizz.getUnlocalizedName());
		// GameRegistry.registerItem(canned_milk, canned_milk.getUnlocalizedName());
		// GameRegistry.registerItem(canned_ass, canned_ass.getUnlocalizedName());
		// GameRegistry.registerItem(canned_pizza, canned_pizza.getUnlocalizedName());
		// GameRegistry.registerItem(canned_tube, canned_tube.getUnlocalizedName());
		// GameRegistry.registerItem(canned_tomato, canned_tomato.getUnlocalizedName());
		// GameRegistry.registerItem(canned_asbestos,
		// canned_asbestos.getUnlocalizedName());
		// GameRegistry.registerItem(canned_bhole, canned_bhole.getUnlocalizedName());
		// GameRegistry.registerItem(canned_hotdogs,
		// canned_hotdogs.getUnlocalizedName());
		// GameRegistry.registerItem(canned_leftovers,
		// canned_leftovers.getUnlocalizedName());
		// GameRegistry.registerItem(canned_yogurt, canned_yogurt.getUnlocalizedName());
		// GameRegistry.registerItem(canned_stew, canned_stew.getUnlocalizedName());
		// GameRegistry.registerItem(canned_chinese,
		// canned_chinese.getUnlocalizedName());
		// GameRegistry.registerItem(canned_oil, canned_oil.getUnlocalizedName());
		// GameRegistry.registerItem(canned_fist, canned_fist.getUnlocalizedName());
		// GameRegistry.registerItem(canned_spam, canned_spam.getUnlocalizedName());
		// GameRegistry.registerItem(canned_fried, canned_fried.getUnlocalizedName());
		// GameRegistry.registerItem(canned_napalm, canned_napalm.getUnlocalizedName());
		// GameRegistry.registerItem(canned_diesel, canned_diesel.getUnlocalizedName());
		// GameRegistry.registerItem(canned_kerosene,
		// canned_kerosene.getUnlocalizedName());
		// GameRegistry.registerItem(canned_recursion,
		// canned_recursion.getUnlocalizedName());
		// GameRegistry.registerItem(canned_bark, canned_bark.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.canned_conserve, ModItems.canned_conserve.getUnlocalizedName());

		// Money
		GameRegistry.registerItem(ModItems.cap_nuka, ModItems.cap_nuka.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cap_quantum, ModItems.cap_quantum.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cap_sparkle, ModItems.cap_sparkle.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cap_rad, ModItems.cap_rad.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cap_korl, ModItems.cap_korl.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cap_fritz, ModItems.cap_fritz.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cap_sunset, ModItems.cap_sunset.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cap_star, ModItems.cap_star.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ring_pull, ModItems.ring_pull.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.can_key, ModItems.can_key.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.coin_creeper, ModItems.coin_creeper.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.coin_radiation, ModItems.coin_radiation.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.coin_maskman, ModItems.coin_maskman.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.coin_worm, ModItems.coin_worm.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.coin_ufo, ModItems.coin_ufo.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.medal_liquidator, ModItems.medal_liquidator.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.v1, ModItems.v1.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.protection_charm, ModItems.protection_charm.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.meteor_charm, ModItems.meteor_charm.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.neutrino_lens, ModItems.neutrino_lens.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gas_tester, ModItems.gas_tester.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.defuser_gold, ModItems.defuser_gold.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ballistic_gauntlet, ModItems.ballistic_gauntlet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.night_vision, ModItems.night_vision.getUnlocalizedName());

		// Chaos
		GameRegistry.registerItem(ModItems.chocolate_milk, ModItems.chocolate_milk.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cbt_device, ModItems.cbt_device.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cigarette, ModItems.cigarette.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crackpipe, ModItems.crackpipe.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bdcl, ModItems.bdcl.getUnlocalizedName());

		// Armor mods
		GameRegistry.registerItem(ModItems.attachment_mask, ModItems.attachment_mask.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.attachment_mask_mono, ModItems.attachment_mask_mono.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.back_tesla, ModItems.back_tesla.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.servo_set, ModItems.servo_set.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.servo_set_desh, ModItems.servo_set_desh.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pads_rubber, ModItems.pads_rubber.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pads_slime, ModItems.pads_slime.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pads_static, ModItems.pads_static.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cladding_paint, ModItems.cladding_paint.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cladding_rubber, ModItems.cladding_rubber.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cladding_lead, ModItems.cladding_lead.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cladding_desh, ModItems.cladding_desh.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cladding_ghiorsium, ModItems.cladding_ghiorsium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cladding_iron, ModItems.cladding_iron.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cladding_obsidian, ModItems.cladding_obsidian.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.insert_kevlar, ModItems.insert_kevlar.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.insert_sapi, ModItems.insert_sapi.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.insert_esapi, ModItems.insert_esapi.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.insert_xsapi, ModItems.insert_xsapi.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.insert_steel, ModItems.insert_steel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.insert_du, ModItems.insert_du.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.insert_polonium, ModItems.insert_polonium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.insert_ghiorsium, ModItems.insert_ghiorsium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.insert_era, ModItems.insert_era.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.insert_yharonite, ModItems.insert_yharonite.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.insert_doxium, ModItems.insert_doxium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.armor_polish, ModItems.armor_polish.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bandaid, ModItems.bandaid.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.serum, ModItems.serum.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.quartz_plutonium, ModItems.quartz_plutonium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.morning_glory, ModItems.morning_glory.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.lodestone, ModItems.lodestone.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.horseshoe_magnet, ModItems.horseshoe_magnet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.industrial_magnet, ModItems.industrial_magnet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bathwater, ModItems.bathwater.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bathwater_mk2, ModItems.bathwater_mk2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.spider_milk, ModItems.spider_milk.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ink, ModItems.ink.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.heart_piece, ModItems.heart_piece.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.heart_container, ModItems.heart_container.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.heart_booster, ModItems.heart_booster.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.heart_fab, ModItems.heart_fab.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.black_diamond, ModItems.black_diamond.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.wd40, ModItems.wd40.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.scrumpy, ModItems.scrumpy.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.wild_p, ModItems.wild_p.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fabsols_vodka, ModItems.fabsols_vodka.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.shackles, ModItems.shackles.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.injector_5htp, ModItems.injector_5htp.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.injector_knife, ModItems.injector_knife.getUnlocalizedName());

		// Minecarts
		GameRegistry.registerItem(ModItems.cart, ModItems.cart.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.train, ModItems.train.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.drone, ModItems.drone.getUnlocalizedName());

		// High Explosive Lenses
		GameRegistry.registerItem(ModItems.early_explosive_lenses,
				ModItems.early_explosive_lenses.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.explosive_lenses, ModItems.explosive_lenses.getUnlocalizedName());

		// The Gadget
		// GameRegistry.registerItem(gadget_explosive,
		// gadget_explosive.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gadget_wireing, ModItems.gadget_wireing.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gadget_core, ModItems.gadget_core.getUnlocalizedName());

		// Little Boy
		GameRegistry.registerItem(ModItems.boy_shielding, ModItems.boy_shielding.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.boy_target, ModItems.boy_target.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.boy_bullet, ModItems.boy_bullet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.boy_propellant, ModItems.boy_propellant.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.boy_igniter, ModItems.boy_igniter.getUnlocalizedName());
		;

		// Fat Man
		// GameRegistry.registerItem(man_explosive, man_explosive.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.man_igniter, ModItems.man_igniter.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.man_core, ModItems.man_core.getUnlocalizedName());

		// Ivy Mike
		GameRegistry.registerItem(ModItems.mike_core, ModItems.mike_core.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mike_deut, ModItems.mike_deut.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mike_cooling_unit, ModItems.mike_cooling_unit.getUnlocalizedName());

		// Tsar Bomba
		GameRegistry.registerItem(ModItems.tsar_core, ModItems.tsar_core.getUnlocalizedName());

		// FLEIJA
		GameRegistry.registerItem(ModItems.fleija_igniter, ModItems.fleija_igniter.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fleija_propellant, ModItems.fleija_propellant.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fleija_core, ModItems.fleija_core.getUnlocalizedName());

		// Solinium
		GameRegistry.registerItem(ModItems.solinium_igniter, ModItems.solinium_igniter.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.solinium_propellant, ModItems.solinium_propellant.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.solinium_core, ModItems.solinium_core.getUnlocalizedName());

		// N2
		GameRegistry.registerItem(ModItems.n2_charge, ModItems.n2_charge.getUnlocalizedName());

		// FSTBMB
		GameRegistry.registerItem(ModItems.egg_balefire_shard, ModItems.egg_balefire_shard.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.egg_balefire, ModItems.egg_balefire.getUnlocalizedName());

		// Conventional Armor
		GameRegistry.registerItem(ModItems.goggles, ModItems.goggles.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ashglasses, ModItems.ashglasses.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gas_mask, ModItems.gas_mask.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gas_mask_m65, ModItems.gas_mask_m65.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gas_mask_mono, ModItems.gas_mask_mono.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gas_mask_olde, ModItems.gas_mask_olde.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mask_rag, ModItems.mask_rag.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mask_piss, ModItems.mask_piss.getUnlocalizedName());
		// GameRegistry.registerItem(oxy_mask, oxy_mask.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.hat, ModItems.hat.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.beta, ModItems.beta.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.no9, ModItems.no9.getUnlocalizedName());

		GameRegistry.registerItem(ModItems.steel_helmet, ModItems.steel_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.steel_plate, ModItems.steel_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.steel_legs, ModItems.steel_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.steel_boots, ModItems.steel_boots.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.titanium_helmet, ModItems.titanium_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.titanium_plate, ModItems.titanium_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.titanium_legs, ModItems.titanium_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.titanium_boots, ModItems.titanium_boots.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.alloy_helmet, ModItems.alloy_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.alloy_plate, ModItems.alloy_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.alloy_legs, ModItems.alloy_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.alloy_boots, ModItems.alloy_boots.getUnlocalizedName());

		// Custom Rods
		GameRegistry.registerItem(ModItems.custom_tnt, ModItems.custom_tnt.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.custom_nuke, ModItems.custom_nuke.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.custom_hydro, ModItems.custom_hydro.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.custom_amat, ModItems.custom_amat.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.custom_dirty, ModItems.custom_dirty.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.custom_schrab, ModItems.custom_schrab.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.custom_fall, ModItems.custom_fall.getUnlocalizedName());

		// Power Armor
		GameRegistry.registerItem(ModItems.steamsuit_helmet, ModItems.steamsuit_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.steamsuit_plate, ModItems.steamsuit_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.steamsuit_legs, ModItems.steamsuit_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.steamsuit_boots, ModItems.steamsuit_boots.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.dieselsuit_helmet, ModItems.dieselsuit_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.dieselsuit_plate, ModItems.dieselsuit_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.dieselsuit_legs, ModItems.dieselsuit_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.dieselsuit_boots, ModItems.dieselsuit_boots.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.t45_helmet, ModItems.t45_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.t45_plate, ModItems.t45_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.t45_legs, ModItems.t45_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.t45_boots, ModItems.t45_boots.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ajr_helmet, ModItems.ajr_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ajr_plate, ModItems.ajr_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ajr_legs, ModItems.ajr_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ajr_boots, ModItems.ajr_boots.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ajro_helmet, ModItems.ajro_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ajro_plate, ModItems.ajro_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ajro_legs, ModItems.ajro_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ajro_boots, ModItems.ajro_boots.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rpa_helmet, ModItems.rpa_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rpa_plate, ModItems.rpa_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rpa_legs, ModItems.rpa_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rpa_boots, ModItems.rpa_boots.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bj_helmet, ModItems.bj_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bj_plate, ModItems.bj_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bj_plate_jetpack, ModItems.bj_plate_jetpack.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bj_legs, ModItems.bj_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bj_boots, ModItems.bj_boots.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.envsuit_helmet, ModItems.envsuit_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.envsuit_plate, ModItems.envsuit_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.envsuit_legs, ModItems.envsuit_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.envsuit_boots, ModItems.envsuit_boots.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.hev_helmet, ModItems.hev_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.hev_plate, ModItems.hev_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.hev_legs, ModItems.hev_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.hev_boots, ModItems.hev_boots.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fau_helmet, ModItems.fau_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fau_plate, ModItems.fau_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fau_legs, ModItems.fau_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fau_boots, ModItems.fau_boots.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.dns_helmet, ModItems.dns_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.dns_plate, ModItems.dns_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.dns_legs, ModItems.dns_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.dns_boots, ModItems.dns_boots.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.trenchmaster_helmet, ModItems.trenchmaster_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.trenchmaster_plate, ModItems.trenchmaster_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.trenchmaster_legs, ModItems.trenchmaster_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.trenchmaster_boots, ModItems.trenchmaster_boots.getUnlocalizedName());

		// Nobody will ever read this anyway, so it shouldn't matter.
		GameRegistry.registerItem(ModItems.chainsaw, ModItems.chainsaw.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.igniter, ModItems.igniter.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.detonator, ModItems.detonator.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.detonator_multi, ModItems.detonator_multi.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.detonator_laser, ModItems.detonator_laser.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.detonator_deadman, ModItems.detonator_deadman.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.detonator_de, ModItems.detonator_de.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crate_caller, ModItems.crate_caller.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bomb_caller, ModItems.bomb_caller.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.meteor_remote, ModItems.meteor_remote.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.anchor_remote, ModItems.anchor_remote.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.defuser, ModItems.defuser.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.reacher, ModItems.reacher.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bismuth_tool, ModItems.bismuth_tool.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.meltdown_tool, ModItems.meltdown_tool.getUnlocalizedName());

		GameRegistry.registerItem(ModItems.hazmat_helmet, ModItems.hazmat_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.hazmat_plate, ModItems.hazmat_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.hazmat_legs, ModItems.hazmat_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.hazmat_boots, ModItems.hazmat_boots.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.hazmat_helmet_red, ModItems.hazmat_helmet_red.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.hazmat_plate_red, ModItems.hazmat_plate_red.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.hazmat_legs_red, ModItems.hazmat_legs_red.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.hazmat_boots_red, ModItems.hazmat_boots_red.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.hazmat_helmet_grey, ModItems.hazmat_helmet_grey.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.hazmat_plate_grey, ModItems.hazmat_plate_grey.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.hazmat_legs_grey, ModItems.hazmat_legs_grey.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.hazmat_boots_grey, ModItems.hazmat_boots_grey.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.hazmat_paa_helmet, ModItems.hazmat_paa_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.hazmat_paa_plate, ModItems.hazmat_paa_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.hazmat_paa_legs, ModItems.hazmat_paa_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.hazmat_paa_boots, ModItems.hazmat_paa_boots.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.liquidator_helmet, ModItems.liquidator_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.liquidator_plate, ModItems.liquidator_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.liquidator_legs, ModItems.liquidator_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.liquidator_boots, ModItems.liquidator_boots.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cmb_helmet, ModItems.cmb_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cmb_plate, ModItems.cmb_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cmb_legs, ModItems.cmb_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cmb_boots, ModItems.cmb_boots.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.paa_plate, ModItems.paa_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.paa_legs, ModItems.paa_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.paa_boots, ModItems.paa_boots.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.asbestos_helmet, ModItems.asbestos_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.asbestos_plate, ModItems.asbestos_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.asbestos_legs, ModItems.asbestos_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.asbestos_boots, ModItems.asbestos_boots.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.security_helmet, ModItems.security_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.security_plate, ModItems.security_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.security_legs, ModItems.security_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.security_boots, ModItems.security_boots.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cobalt_helmet, ModItems.cobalt_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cobalt_plate, ModItems.cobalt_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cobalt_legs, ModItems.cobalt_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cobalt_boots, ModItems.cobalt_boots.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.starmetal_helmet, ModItems.starmetal_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.starmetal_plate, ModItems.starmetal_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.starmetal_legs, ModItems.starmetal_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.starmetal_boots, ModItems.starmetal_boots.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.zirconium_legs, ModItems.zirconium_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.dnt_helmet, ModItems.dnt_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.dnt_plate, ModItems.dnt_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.dnt_legs, ModItems.dnt_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.dnt_boots, ModItems.dnt_boots.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.schrabidium_helmet, ModItems.schrabidium_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.schrabidium_plate, ModItems.schrabidium_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.schrabidium_legs, ModItems.schrabidium_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.schrabidium_boots, ModItems.schrabidium_boots.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bismuth_helmet, ModItems.bismuth_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bismuth_plate, ModItems.bismuth_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bismuth_legs, ModItems.bismuth_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bismuth_boots, ModItems.bismuth_boots.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.euphemium_helmet, ModItems.euphemium_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.euphemium_plate, ModItems.euphemium_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.euphemium_legs, ModItems.euphemium_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.euphemium_boots, ModItems.euphemium_boots.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.robes_helmet, ModItems.robes_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.robes_plate, ModItems.robes_plate.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.robes_legs, ModItems.robes_legs.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.robes_boots, ModItems.robes_boots.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.apple_euphemium, ModItems.apple_euphemium.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.watch, ModItems.watch.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mask_of_infamy, ModItems.mask_of_infamy.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.australium_iii, ModItems.australium_iii.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.jackt, ModItems.jackt.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.jackt2, ModItems.jackt2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.jetpack_fly, ModItems.jetpack_fly.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.jetpack_break, ModItems.jetpack_break.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.jetpack_vector, ModItems.jetpack_vector.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.jetpack_boost, ModItems.jetpack_boost.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.wings_limp, ModItems.wings_limp.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.wings_murk, ModItems.wings_murk.getUnlocalizedName());
		// GameRegistry.registerItem(australium_iv, australium_iv.getUnlocalizedName());
		// GameRegistry.registerItem(australium_v, australium_v.getUnlocalizedName());

		// Expensive Ass Shit
		GameRegistry.registerItem(ModItems.crystal_horn, ModItems.crystal_horn.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.crystal_charred, ModItems.crystal_charred.getUnlocalizedName());

		// OP Tools
		GameRegistry.registerItem(ModItems.wand, ModItems.wand.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.wand_s, ModItems.wand_s.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.wand_d, ModItems.wand_d.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.structure_single, ModItems.structure_single.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.structure_solid, ModItems.structure_solid.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.structure_pattern, ModItems.structure_pattern.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.structure_randomized, ModItems.structure_randomized.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.structure_randomly, ModItems.structure_randomly.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.structure_custommachine,
				ModItems.structure_custommachine.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.rod_of_discord, ModItems.rod_of_discord.getUnlocalizedName());
		// GameRegistry.registerItem(analyzer, analyzer.getUnlocalizedName());
		// GameRegistry.registerItem(remote, remote.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.euphemium_stopper, ModItems.euphemium_stopper.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.polaroid, ModItems.polaroid.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.glitch, ModItems.glitch.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.book_secret, ModItems.book_secret.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.book_of_, ModItems.book_of_.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.burnt_bark, ModItems.burnt_bark.getUnlocalizedName());

		// Kits
		GameRegistry.registerItem(ModItems.nuke_starter_kit, ModItems.nuke_starter_kit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nuke_advanced_kit, ModItems.nuke_advanced_kit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nuke_commercially_kit, ModItems.nuke_commercially_kit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nuke_electric_kit, ModItems.nuke_electric_kit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gadget_kit, ModItems.gadget_kit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.boy_kit, ModItems.boy_kit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.man_kit, ModItems.man_kit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mike_kit, ModItems.mike_kit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.tsar_kit, ModItems.tsar_kit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.prototype_kit, ModItems.prototype_kit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.fleija_kit, ModItems.fleija_kit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.solinium_kit, ModItems.solinium_kit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.multi_kit, ModItems.multi_kit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.custom_kit, ModItems.custom_kit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.missile_kit, ModItems.missile_kit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.grenade_kit, ModItems.grenade_kit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.t45_kit, ModItems.t45_kit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.hazmat_kit, ModItems.hazmat_kit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.hazmat_red_kit, ModItems.hazmat_red_kit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.hazmat_grey_kit, ModItems.hazmat_grey_kit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.kit_custom, ModItems.kit_custom.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.euphemium_kit, ModItems.euphemium_kit.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.kit_toolbox_empty, ModItems.kit_toolbox_empty.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.kit_toolbox, ModItems.kit_toolbox.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.letter, ModItems.letter.getUnlocalizedName());

		// Misile Loot Boxes
		GameRegistry.registerItem(ModItems.loot_10, ModItems.loot_10.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.loot_15, ModItems.loot_15.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.loot_misc, ModItems.loot_misc.getUnlocalizedName());

		// THIS is a bucket.
		GameRegistry.registerItem(ModItems.bucket_mud, ModItems.bucket_mud.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bucket_acid, ModItems.bucket_acid.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bucket_toxic, ModItems.bucket_toxic.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bucket_schrabidic_acid,
				ModItems.bucket_schrabidic_acid.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bucket_sulfuric_acid, ModItems.bucket_sulfuric_acid.getUnlocalizedName());

		// Door Items
		GameRegistry.registerItem(ModItems.door_metal, ModItems.door_metal.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.door_office, ModItems.door_office.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.door_bunker, ModItems.door_bunker.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.sliding_blast_door_skin,
				ModItems.sliding_blast_door_skin.getUnlocalizedName());

		// Records
		GameRegistry.registerItem(ModItems.record_lc, ModItems.record_lc.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.record_ss, ModItems.record_ss.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.record_vc, ModItems.record_vc.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.record_glass, ModItems.record_glass.getUnlocalizedName());

		// wow we're far down the item registry, is this the cellar?
		GameRegistry.registerItem(ModItems.book_guide, ModItems.book_guide.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.book_lore, ModItems.book_lore.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.holotape_image, ModItems.holotape_image.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.holotape_damaged, ModItems.holotape_damaged.getUnlocalizedName());

		// Technical Items
		GameRegistry.registerItem(ModItems.smoke1, ModItems.smoke1.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.smoke2, ModItems.smoke2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.smoke3, ModItems.smoke3.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.smoke4, ModItems.smoke4.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.smoke5, ModItems.smoke5.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.smoke6, ModItems.smoke6.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.smoke7, ModItems.smoke7.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.smoke8, ModItems.smoke8.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.b_smoke1, ModItems.b_smoke1.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.b_smoke2, ModItems.b_smoke2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.b_smoke3, ModItems.b_smoke3.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.b_smoke4, ModItems.b_smoke4.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.b_smoke5, ModItems.b_smoke5.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.b_smoke6, ModItems.b_smoke6.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.b_smoke7, ModItems.b_smoke7.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.b_smoke8, ModItems.b_smoke8.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.d_smoke1, ModItems.d_smoke1.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.d_smoke2, ModItems.d_smoke2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.d_smoke3, ModItems.d_smoke3.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.d_smoke4, ModItems.d_smoke4.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.d_smoke5, ModItems.d_smoke5.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.d_smoke6, ModItems.d_smoke6.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.d_smoke7, ModItems.d_smoke7.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.d_smoke8, ModItems.d_smoke8.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.spill1, ModItems.spill1.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.spill2, ModItems.spill2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.spill3, ModItems.spill3.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.spill4, ModItems.spill4.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.spill5, ModItems.spill5.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.spill6, ModItems.spill6.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.spill7, ModItems.spill7.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.spill8, ModItems.spill8.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gas1, ModItems.gas1.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gas2, ModItems.gas2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gas3, ModItems.gas3.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gas4, ModItems.gas4.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gas5, ModItems.gas5.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gas6, ModItems.gas6.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gas7, ModItems.gas7.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.gas8, ModItems.gas8.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.chlorine1, ModItems.chlorine1.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.chlorine2, ModItems.chlorine2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.chlorine3, ModItems.chlorine3.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.chlorine4, ModItems.chlorine4.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.chlorine5, ModItems.chlorine5.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.chlorine6, ModItems.chlorine6.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.chlorine7, ModItems.chlorine7.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.chlorine8, ModItems.chlorine8.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pc1, ModItems.pc1.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pc2, ModItems.pc2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pc3, ModItems.pc3.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pc4, ModItems.pc4.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pc5, ModItems.pc5.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pc6, ModItems.pc6.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pc7, ModItems.pc7.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.pc8, ModItems.pc8.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cloud1, ModItems.cloud1.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cloud2, ModItems.cloud2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cloud3, ModItems.cloud3.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cloud4, ModItems.cloud4.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cloud5, ModItems.cloud5.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cloud6, ModItems.cloud6.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cloud7, ModItems.cloud7.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.cloud8, ModItems.cloud8.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.orange1, ModItems.orange1.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.orange2, ModItems.orange2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.orange3, ModItems.orange3.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.orange4, ModItems.orange4.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.orange5, ModItems.orange5.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.orange6, ModItems.orange6.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.orange7, ModItems.orange7.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.orange8, ModItems.orange8.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.energy_ball, ModItems.energy_ball.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.discharge, ModItems.discharge.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.empblast, ModItems.empblast.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.flame_1, ModItems.flame_1.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.flame_2, ModItems.flame_2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.flame_3, ModItems.flame_3.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.flame_4, ModItems.flame_4.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.flame_5, ModItems.flame_5.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.flame_6, ModItems.flame_6.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.flame_7, ModItems.flame_7.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.flame_8, ModItems.flame_8.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.flame_9, ModItems.flame_9.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.flame_10, ModItems.flame_10.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ln2_1, ModItems.ln2_1.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ln2_2, ModItems.ln2_2.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ln2_3, ModItems.ln2_3.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ln2_4, ModItems.ln2_4.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ln2_5, ModItems.ln2_5.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ln2_6, ModItems.ln2_6.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ln2_7, ModItems.ln2_7.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ln2_8, ModItems.ln2_8.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ln2_9, ModItems.ln2_9.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.ln2_10, ModItems.ln2_10.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.nothing, ModItems.nothing.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.void_anim, ModItems.void_anim.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.achievement_icon, ModItems.achievement_icon.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bob_metalworks, ModItems.bob_metalworks.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bob_assembly, ModItems.bob_assembly.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bob_chemistry, ModItems.bob_chemistry.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bob_oil, ModItems.bob_oil.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.bob_nuclear, ModItems.bob_nuclear.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.mysteryshovel, ModItems.mysteryshovel.getUnlocalizedName());
		GameRegistry.registerItem(ModItems.memory, ModItems.memory.getUnlocalizedName());

		GameRegistry.registerItem(ModItems.debug_wand, ModItems.debug_wand.getUnlocalizedName());

		ModItems.addRemap("rod_zirnox_natural_uranium_fuel", ModItems.rod_zirnox, EnumZirnoxType.NATURAL_URANIUM_FUEL);
		ModItems.addRemap("rod_zirnox_uranium_fuel", ModItems.rod_zirnox, EnumZirnoxType.URANIUM_FUEL);
		ModItems.addRemap("rod_zirnox_th232", ModItems.rod_zirnox, EnumZirnoxType.TH232);
		ModItems.addRemap("rod_zirnox_thorium_fuel", ModItems.rod_zirnox, EnumZirnoxType.THORIUM_FUEL);
		ModItems.addRemap("rod_zirnox_mox_fuel", ModItems.rod_zirnox, EnumZirnoxType.MOX_FUEL);
		ModItems.addRemap("rod_zirnox_plutonium_fuel", ModItems.rod_zirnox, EnumZirnoxType.PLUTONIUM_FUEL);
		ModItems.addRemap("rod_zirnox_u233_fuel", ModItems.rod_zirnox, EnumZirnoxType.U233_FUEL);
		ModItems.addRemap("rod_zirnox_u235_fuel", ModItems.rod_zirnox, EnumZirnoxType.U235_FUEL);
		ModItems.addRemap("rod_zirnox_les_fuel", ModItems.rod_zirnox, EnumZirnoxType.LES_FUEL);
		ModItems.addRemap("rod_zirnox_lithium", ModItems.rod_zirnox, EnumZirnoxType.LITHIUM);
		ModItems.addRemap("rod_zirnox_zfb_mox", ModItems.rod_zirnox, EnumZirnoxType.ZFB_MOX);

		ModItems.addRemap("gas_petroleum", ModItems.gas_full, Fluids.PETROLEUM.getID());
		ModItems.addRemap("gas_biogas", ModItems.gas_full, Fluids.BIOGAS.getID());
		ModItems.addRemap("gas_lpg", ModItems.gas_full, Fluids.LPG.getID());

		ModItems.addRemap("gun_coilgun_ammo", ModItems.ammo_coilgun, AmmoCoilgun.STOCK);
	}

	public static void addRemap(String unloc, Item item, Enum<?> sub) {
		ModItems.addRemap(unloc, item, sub.ordinal());
	}

	public static void addRemap(String unloc, Item item, int meta) {
		Item remap = new ItemRemap(item, meta).setUnlocalizedName(unloc)
				.setTextureName(RefStrings.MODID + ":plate_armor_titanium");
		GameRegistry.registerItem(remap, remap.getUnlocalizedName());
	}
}
