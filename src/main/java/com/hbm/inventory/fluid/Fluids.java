package com.hbm.inventory.fluid;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.fluid.trait.FT_Combustible;
import com.hbm.inventory.fluid.trait.FT_Combustible.FuelGrade;
import com.hbm.inventory.fluid.trait.FT_Coolable;
import com.hbm.inventory.fluid.trait.FT_Coolable.CoolingType;
import com.hbm.inventory.fluid.trait.FT_Corrosive;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.inventory.fluid.trait.FT_Heatable;
import com.hbm.inventory.fluid.trait.FT_Heatable.HeatingType;
import com.hbm.inventory.fluid.trait.FT_PWRModerator;
import com.hbm.inventory.fluid.trait.FT_Poison;
import com.hbm.inventory.fluid.trait.FT_Toxin;
import com.hbm.inventory.fluid.trait.FT_Toxin.ToxinDirectDamage;
import com.hbm.inventory.fluid.trait.FT_Toxin.ToxinEffects;
import com.hbm.inventory.fluid.trait.FT_VentRadiation;
import com.hbm.inventory.fluid.trait.FluidTrait;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Amat;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Delicious;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Gaseous;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Gaseous_ART;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_LeadContainer;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Leaded;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Liquid;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_NoContainer;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_NoID;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Plasma;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Viscous;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.potion.HbmPotion;
import com.hbm.render.util.EnumSymbol;
import com.hbm.util.ArmorRegistry.HazardClass;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

@SuppressWarnings("deprecation")
public class Fluids {

	public static final Gson gson = new Gson();

	public static FluidType NONE;
	public static FluidType WATER;
	public static FluidType STEAM;
	public static FluidType HOTSTEAM;
	public static FluidType SUPERHOTSTEAM;
	public static FluidType ULTRAHOTSTEAM;
	public static FluidType COOLANT;
	public static FluidType COOLANT_HOT;
	public static FluidType LAVA;
	public static FluidType DEUTERIUM;
	public static FluidType TRITIUM;
	public static FluidType OIL;
	public static FluidType CRACKOIL;
	public static FluidType COALOIL;
	public static FluidType HOTOIL;
	public static FluidType HOTCRACKOIL;
	public static FluidType HEAVYOIL;
	public static FluidType BITUMEN;
	public static FluidType SMEAR;
	public static FluidType HEATINGOIL;
	public static FluidType RECLAIMED;
	public static FluidType LUBRICANT;
	public static FluidType NAPHTHA;
	public static FluidType NAPHTHA_CRACK;
	public static FluidType DIESEL;
	public static FluidType DIESEL_CRACK;
	public static FluidType LIGHTOIL;
	public static FluidType LIGHTOIL_CRACK;
	public static FluidType KEROSENE;
	public static FluidType GAS;
	public static FluidType PETROLEUM;
	public static FluidType LPG;
	public static FluidType AROMATICS;			//anything from benzene to phenol and toluene
	public static FluidType UNSATURATEDS;		//collection of various basic unsaturated compounds like ethylene, acetylene and whatnot
	public static FluidType BIOGAS;
	public static FluidType BIOFUEL;
	public static FluidType NITAN;
	public static FluidType UF6;
	public static FluidType PUF6;
	public static FluidType SAS3;
	public static FluidType SCHRABIDIC;
	public static FluidType AMAT;
	public static FluidType ASCHRAB;
	public static FluidType ACID;
	public static FluidType WATZ;
	public static FluidType CRYOGEL;
	public static FluidType HYDROGEN;
	public static FluidType OXYGEN;
	public static FluidType XENON;
	public static FluidType BALEFIRE;
	public static FluidType MERCURY;
	public static FluidType PAIN;				//tantalite solution
	public static FluidType WASTEFLUID;
	public static FluidType WASTEGAS;
	public static FluidType PETROIL;
	public static FluidType PETROIL_LEADED;
	public static FluidType GASOLINE;
	public static FluidType GASOLINE_LEADED;
	public static FluidType COALGAS;			//coal-based gasoline
	public static FluidType COALGAS_LEADED;
	public static FluidType SPENTSTEAM;
	public static FluidType FRACKSOL;
	public static FluidType PLASMA_DT;
	public static FluidType PLASMA_HD;
	public static FluidType PLASMA_HT;
	public static FluidType PLASMA_DH3;
	public static FluidType PLASMA_XM;
	public static FluidType PLASMA_BF;
	public static FluidType CARBONDIOXIDE;
	public static FluidType HELIUM3;
	public static FluidType DEATH;				//osmiridium solution
	public static FluidType ETHANOL;
	public static FluidType HEAVYWATER;
	public static FluidType SALIENT;
	public static FluidType XPJUICE;
	public static FluidType ENDERJUICE;
	public static FluidType SULFURIC_ACID;
	public static FluidType MUG;
	public static FluidType MUG_HOT;
	public static FluidType WOODOIL;
	public static FluidType COALCREOSOTE;
	public static FluidType SEEDSLURRY;
	public static FluidType NITRIC_ACID;
	public static FluidType SOLVENT;			//oranic solvent in fact
	public static FluidType BLOOD;				//BLOOD ORB! BLOOD ORB! BLOOD ORB!
	public static FluidType BLOOD_HOT;
	public static FluidType SYNGAS;
	public static FluidType OXYHYDROGEN;
	public static FluidType RADIOSOLVENT;		//DCM-ish made by wacky radio cracking
	public static FluidType CHLORINE;			//everone's favorite!
	public static FluidType HEAVYOIL_VACUUM;
	public static FluidType REFORMATE;
	public static FluidType LIGHTOIL_VACUUM;
	public static FluidType SOURGAS;
	public static FluidType XYLENE;				//BTX: benzene, terephthalate and xylene
	public static FluidType HEATINGOIL_VACUUM;
	public static FluidType DIESEL_REFORM;
	public static FluidType DIESEL_CRACK_REFORM;
	public static FluidType KEROSENE_REFORM;
	public static FluidType REFORMGAS;			//MAPD: propyne, propadiene
	public static FluidType COLLOID;
	public static FluidType PHOSGENE;
	public static FluidType MUSTARDGAS;
	public static FluidType IONGEL;
	public static FluidType OIL_COKER;			//heavy fractions from coking, mostly bitumen
	public static FluidType NAPHTHA_COKER;		//medium fractions from coking, aromatics and fuel oil
	public static FluidType GAS_COKER;			//light fractions from coking, natgas and co2
	public static FluidType EGG;
	public static FluidType CHOLESTEROL;
	public static FluidType ESTRADIOL;
	public static FluidType FISHOIL;
	public static FluidType SUNFLOWEROIL;
	public static FluidType NITROGLYCERIN;
	public static FluidType REDMUD;
	public static FluidType CHLOROCALCITE_SOLUTION;
	public static FluidType CHLOROCALCITE_MIX;
	public static FluidType CHLOROCALCITE_CLEANED;
	public static FluidType POTASSIUM_CHLORIDE;
	public static FluidType CALCIUM_CHLORIDE;
	public static FluidType CALCIUM_SOLUTION;
	public static FluidType SMOKE;
	public static FluidType SMOKE_LEADED;
	public static FluidType SMOKE_POISON;
	public static FluidType HELIUM4;
	public static FluidType HEAVYWATER_HOT;
	public static FluidType SODIUM;
	public static FluidType SODIUM_HOT;
	public static FluidType THORIUM_SALT;
	public static FluidType THORIUM_SALT_HOT;
	public static FluidType THORIUM_SALT_DEPLETED;
	public static FluidType FULLERENE;
	
	public static List<FluidType> customFluids = new ArrayList<>();

	private static final HashMap<Integer, FluidType> idMapping = new HashMap();
	private static final HashMap<String, FluidType> nameMapping = new HashMap();
	protected static final List<FluidType> registerOrder = new ArrayList<>();
	protected static final List<FluidType> metaOrder = new ArrayList<>();

	public static final FT_Liquid LIQUID = new FT_Liquid();
	public static final FT_Viscous VISCOUS = new FT_Viscous();
	public static final FT_Gaseous_ART EVAP = new FT_Gaseous_ART();
	public static final FT_Gaseous GASEOUS = new FT_Gaseous();
	public static final FT_Plasma PLASMA = new FT_Plasma();
	public static final FT_Amat ANTI = new FT_Amat();
	public static final FT_LeadContainer LEADCON = new FT_LeadContainer();
	public static final FT_NoContainer NOCON = new FT_NoContainer();
	public static final FT_NoID NOID = new FT_NoID();
	public static final FT_Delicious DELICIOUS = new FT_Delicious();
	public static final FT_Leaded LEADED = new FT_Leaded();
	
	public static void init() {
		
		// ##### ##### ##### ##### ##  # ##### #   # ##### ##  # #####
		// #   #   #     #   #     ##  # #     #   # #   # ##  # #
		// #####   #     #   ###   # # # ##### ##### #   # # # # ###
		// #   #   #     #   #     #  ##     # #   # #   # #  ## #
		// #   #   #     #   ##### #  ## ##### #   # ##### #  ## #####
		
		/*
		 * The mapping ID is set in the CTOR, which is the static, never shifting ID that is used to save the fluid type.
		 * Therefore, ALWAYS append new fluid entries AT THE BOTTOM to avoid unnecessary ID shifting.
		 * In addition, you have to add your fluid to 'metaOrder' which is what is used to sort fluid identifiers and whatnot in the inventory.
		 * You may screw with metaOrder as much as you like, as long as you keep all fluids in the list exactly once.
		 */
		
		Fluids.NONE =					new FluidType("NONE",				0x888888, 0, 0, 0, EnumSymbol.NONE);
		Fluids.WATER =					new FluidType("WATER",				0x3333FF, 0, 0, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID);
		Fluids.STEAM =					new FluidType("STEAM",				0xe5e5e5, 3, 0, 0, EnumSymbol.NONE).setTemp(100).addTraits(Fluids.GASEOUS);
		Fluids.HOTSTEAM =				new FluidType("HOTSTEAM",			0xE7D6D6, 4, 0, 0, EnumSymbol.NONE).setTemp(300).addTraits(Fluids.GASEOUS);
		Fluids.SUPERHOTSTEAM =			new FluidType("SUPERHOTSTEAM",		0xE7B7B7, 4, 0, 0, EnumSymbol.NONE).setTemp(450).addTraits(Fluids.GASEOUS);
		Fluids.ULTRAHOTSTEAM =			new FluidType("ULTRAHOTSTEAM",		0xE39393, 4, 0, 0, EnumSymbol.NONE).setTemp(600).addTraits(Fluids.GASEOUS);
		Fluids.COOLANT =				new FluidType("COOLANT",			0xd8fcff, 1, 0, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID);
		Fluids.LAVA =					new FluidType("LAVA",				0xFF3300, 4, 0, 0, EnumSymbol.NOWATER).setTemp(1200).addTraits(Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.DEUTERIUM =				new FluidType("DEUTERIUM",			0x0000FF, 3, 4, 0, EnumSymbol.NONE).addTraits(new FT_Flammable(5_000), new FT_Combustible(FuelGrade.HIGH, 10_000), Fluids.GASEOUS);
		Fluids.TRITIUM =				new FluidType("TRITIUM",			0x000099, 3, 4, 0, EnumSymbol.RADIATION).addTraits(new FT_Flammable(5_000), new FT_Combustible(FuelGrade.HIGH, 10_000), Fluids.GASEOUS, new FT_VentRadiation(0.001F));
		Fluids.OIL =					new FluidType("OIL",				0x020202, 2, 1, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x424242)).addTraits(new FT_Flammable(10_000), Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.HOTOIL =				new FluidType("HOTOIL",				0x300900, 2, 3, 0, EnumSymbol.NONE).setTemp(350).addTraits(new FT_Flammable(10_000), Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.HEAVYOIL =				new FluidType("HEAVYOIL",			0x141312, 2, 1, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x513F39)).addTraits(new FT_Flammable(50_000), new FT_Combustible(FuelGrade.LOW, 25_000), Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.BITUMEN =				new FluidType("BITUMEN",			0x1f2426, 2, 0, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x5A5877)).addTraits(Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.SMEAR =					new FluidType("SMEAR",				0x190f01, 2, 1, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x624F3B)).addTraits(new FT_Flammable(50_000), Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.HEATINGOIL =			new FluidType("HEATINGOIL",			0x211806, 2, 2, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x694235)).addTraits(new FT_Flammable(150_000), new FT_Combustible(FuelGrade.LOW, 100_000), Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.RECLAIMED =				new FluidType("RECLAIMED",			0x332b22, 2, 2, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0xF65723)).addTraits(new FT_Flammable(100_000), new FT_Combustible(FuelGrade.LOW, 200_000), Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.PETROIL =				new FluidType("PETROIL",			0x44413d, 1, 3, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x2369F6)).addTraits(new FT_Flammable(125_000), new FT_Combustible(FuelGrade.MEDIUM, 300_000), Fluids.LIQUID);
		Fluids.LUBRICANT =				new FluidType("LUBRICANT",			0x606060, 2, 1, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0xF1CC05)).addTraits(Fluids.LIQUID);
		Fluids.NAPHTHA =				new FluidType("NAPHTHA",			0x595744, 2, 1, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x5F6D44)).addTraits(new FT_Flammable(125_000), new FT_Combustible(FuelGrade.MEDIUM, 200_000), Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.DIESEL =				new FluidType("DIESEL",				0xf2eed5, 1, 2, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0xFF2C2C)).addTraits(new FT_Flammable(200_000), new FT_Combustible(FuelGrade.HIGH, 500_000), Fluids.LIQUID);
		Fluids.LIGHTOIL =				new FluidType("LIGHTOIL",			0x8c7451, 1, 2, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0xB46B52)).addTraits(new FT_Flammable(200_000), new FT_Combustible(FuelGrade.MEDIUM, 500_000), Fluids.LIQUID);
		Fluids.KEROSENE =				new FluidType("KEROSENE",			0xffa5d2, 1, 2, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0xFF377D)).addTraits(new FT_Flammable(300_000), new FT_Combustible(FuelGrade.AERO, 1_250_000), Fluids.LIQUID);
		Fluids.GAS =					new FluidType("GAS",				0xfffeed, 1, 4, 1, EnumSymbol.NONE).addContainers(new CD_Gastank(0xFF4545, 0xFFE97F)).addTraits(new FT_Flammable(10_000), Fluids.GASEOUS);
		Fluids.PETROLEUM = 			new FluidType("PETROLEUM",			0x7cb7c9, 1, 4, 1, EnumSymbol.NONE).addContainers(new CD_Gastank(0x5E7CFF, 0xFFE97F)).addTraits(new FT_Flammable(25_000), Fluids.GASEOUS);
		Fluids.LPG =					new FluidType("LPG",				0x4747EA, 1, 3, 1, EnumSymbol.NONE).addTraits(new FT_Flammable(200_000), new FT_Combustible(FuelGrade.HIGH, 400_000), Fluids.LIQUID);
		Fluids.BIOGAS =				new FluidType("BIOGAS",				0xbfd37c, 1, 4, 1, EnumSymbol.NONE).addContainers(new CD_Gastank(0xC8FF1F, 0x303030)).addTraits(new FT_Flammable(25_000), Fluids.GASEOUS);
		Fluids.BIOFUEL =				new FluidType("BIOFUEL",			0xeef274, 1, 2, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x9EB623)).addTraits(new FT_Flammable(150_000), new FT_Combustible(FuelGrade.HIGH, 400_000), Fluids.LIQUID);
		Fluids.NITAN =					new FluidType("NITAN",				0x8018ad, 2, 4, 1, EnumSymbol.NONE).addContainers(new CD_Canister(0x6B238C)).addTraits(new FT_Flammable(2_000_000), new FT_Combustible(FuelGrade.HIGH, 5_000_000), Fluids.LIQUID);
		Fluids.UF6 =					new FluidType("UF6",				0xD1CEBE, 4, 0, 2, EnumSymbol.RADIATION).addTraits(new FT_VentRadiation(0.2F), new FT_Corrosive(15), Fluids.GASEOUS);
		Fluids.PUF6 =					new FluidType("PUF6",				0x4C4C4C, 4, 0, 4, EnumSymbol.RADIATION).addTraits(new FT_VentRadiation(0.1F), new FT_Corrosive(15), Fluids.GASEOUS);
		Fluids.SAS3 =					new FluidType("SAS3",				0x4ffffc, 5, 0, 4, EnumSymbol.RADIATION).addTraits(new FT_VentRadiation(1F), new FT_Corrosive(30), Fluids.LIQUID);
		Fluids.SCHRABIDIC =			new FluidType("SCHRABIDIC",			0x006B6B, 5, 0, 5, EnumSymbol.ACID).addTraits(new FT_VentRadiation(1F), new FT_Corrosive(75), new FT_Poison(true, 2), Fluids.LIQUID);
		Fluids.AMAT =					new FluidType("AMAT",				0x010101, 5, 0, 5, EnumSymbol.ANTIMATTER).addTraits(Fluids.ANTI, Fluids.GASEOUS);
		Fluids.ASCHRAB =				new FluidType("ASCHRAB",			0xb50000, 5, 0, 5, EnumSymbol.ANTIMATTER).addTraits(Fluids.ANTI, Fluids.GASEOUS);
		Fluids.ACID =					new FluidType("ACID",				0xfff7aa, 3, 0, 3, EnumSymbol.OXIDIZER).addTraits(new FT_Corrosive(40), Fluids.LIQUID);
		Fluids.WATZ =					new FluidType("WATZ",				0x86653E, 4, 0, 3, EnumSymbol.ACID).addTraits(new FT_Corrosive(60), new FT_VentRadiation(0.1F), Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.CRYOGEL =				new FluidType("CRYOGEL",			0x32ffff, 2, 0, 0, EnumSymbol.CROYGENIC).setTemp(-170).addTraits(Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.HYDROGEN =				new FluidType("HYDROGEN",			0x4286f4, 3, 4, 0, EnumSymbol.CROYGENIC).setTemp(-260).addContainers(new CD_Gastank(0x4286f4, 0xffffff)).addTraits(new FT_Flammable(5_000), new FT_Combustible(FuelGrade.HIGH, 10_000), Fluids.LIQUID, Fluids.EVAP);
		Fluids.OXYGEN =				new FluidType("OXYGEN",				0x98bdf9, 3, 0, 0, EnumSymbol.CROYGENIC).setTemp(-100).addContainers(new CD_Gastank(0x98bdf9, 0xffffff)).addTraits(Fluids.LIQUID, Fluids.EVAP);
		Fluids.XENON =					new FluidType("XENON",				0xba45e8, 0, 0, 0, EnumSymbol.ASPHYXIANT).addContainers(new CD_Gastank(0x8C21FF, 0x303030)).addTraits(Fluids.GASEOUS);
		Fluids.BALEFIRE =				new FluidType("BALEFIRE",			0x28e02e, 4, 4, 3, EnumSymbol.RADIATION).setTemp(1500).addTraits(new FT_Corrosive(50), new FT_Flammable(1_000_000), new FT_Combustible(FuelGrade.HIGH, 2_500_000), Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.MERCURY =				new FluidType("MERCURY",			0x808080, 2, 0, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID, new FT_Poison(false, 2)); // TODO: Change to FT_Toxin
		Fluids.PAIN =					new FluidType("PAIN",				0x938541, 2, 0, 1, EnumSymbol.ACID).setTemp(300).addTraits(new FT_Corrosive(30), new FT_Poison(true, 2), Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.WASTEFLUID =			new FluidType("WASTEFLUID",			0x544400, 2, 0, 1, EnumSymbol.RADIATION).addTraits(new FT_VentRadiation(0.5F), Fluids.NOCON, Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.WASTEGAS =				new FluidType("WASTEGAS",			0xB8B8B8, 2, 0, 1, EnumSymbol.RADIATION).addTraits(new FT_VentRadiation(0.5F), Fluids.NOCON, Fluids.GASEOUS);
		Fluids.GASOLINE =				new FluidType("GASOLINE",			0x445772, 1, 2, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x2F7747)).addTraits(new FT_Flammable(400_000), new FT_Combustible(FuelGrade.HIGH, 1_000_000), Fluids.LIQUID);
		Fluids.COALGAS =				new FluidType("COALGAS",			0x445772, 1, 2, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x2E155F)).addTraits(new FT_Flammable(75_000), new FT_Combustible(FuelGrade.MEDIUM, 150_000), Fluids.LIQUID);
		Fluids.SPENTSTEAM =			new FluidType("SPENTSTEAM",			0x445772, 2, 0, 0, EnumSymbol.NONE).addTraits(Fluids.NOCON, Fluids.GASEOUS);
		Fluids.FRACKSOL =				new FluidType("FRACKSOL",			0x798A6B, 1, 3, 3, EnumSymbol.ACID).addContainers(new CD_Canister(0x4F887F)).addTraits(new FT_Corrosive(15), new FT_Poison(false, 0), Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.PLASMA_DT =				new FluidType("PLASMA_DT",			0xF7AFDE, 0, 4, 0, EnumSymbol.RADIATION).setTemp(3250).addTraits(Fluids.NOCON, Fluids.NOID, Fluids.PLASMA);
		Fluids.PLASMA_HD =				new FluidType("PLASMA_HD",			0xF0ADF4, 0, 4, 0, EnumSymbol.RADIATION).setTemp(2500).addTraits(Fluids.NOCON, Fluids.NOID, Fluids.PLASMA);
		Fluids.PLASMA_HT =				new FluidType("PLASMA_HT",			0xD1ABF2, 0, 4, 0, EnumSymbol.RADIATION).setTemp(3000).addTraits(Fluids.NOCON, Fluids.NOID, Fluids.PLASMA);
		Fluids.PLASMA_XM =				new FluidType("PLASMA_XM",			0xC6A5FF, 0, 4, 1, EnumSymbol.RADIATION).setTemp(4250).addTraits(Fluids.NOCON, Fluids.NOID, Fluids.PLASMA);
		Fluids.PLASMA_BF =				new FluidType("PLASMA_BF",			0xA7F1A3, 4, 5, 4, EnumSymbol.ANTIMATTER).setTemp(8500).addTraits(Fluids.NOCON, Fluids.NOID, Fluids.PLASMA);
		Fluids.CARBONDIOXIDE =			new FluidType("CARBONDIOXIDE",		0x404040, 3, 0, 0, EnumSymbol.ASPHYXIANT).addTraits(Fluids.GASEOUS);
		Fluids.PLASMA_DH3 =			new FluidType("PLASMA_DH3",			0xFF83AA, 0, 4, 0, EnumSymbol.RADIATION).setTemp(3480).addTraits(Fluids.NOCON, Fluids.NOID, Fluids.PLASMA);
		Fluids.HELIUM3 =				new FluidType("HELIUM3",			0xFCF0C4, 0, 0, 0, EnumSymbol.ASPHYXIANT).addTraits(Fluids.GASEOUS);
		Fluids.DEATH =					new FluidType("DEATH",				0x717A88, 2, 0, 1, EnumSymbol.ACID).setTemp(300).addTraits(new FT_Corrosive(80), new FT_Poison(true, 4), Fluids.LEADCON, Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.ETHANOL =				new FluidType("ETHANOL",			0xe0ffff, 2, 3, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0xEAFFF3)).addTraits(new FT_Flammable(75_000), new FT_Combustible(FuelGrade.HIGH, 200_000), Fluids.LIQUID);
		Fluids.HEAVYWATER =			new FluidType("HEAVYWATER",			0x00a0b0, 1, 0, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID);
		Fluids.CRACKOIL =				new FluidType("CRACKOIL",			0x020202, 2, 1, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x424242)).addTraits(new FT_Flammable(10_000), Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.COALOIL =				new FluidType("COALOIL",			0x020202, 2, 1, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x424242)).addTraits(new FT_Flammable(10_000), Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.HOTCRACKOIL =			new FluidType("HOTCRACKOIL",		0x300900, 2, 3, 0, EnumSymbol.NONE).setTemp(350).addContainers(new CD_Canister(0x424242)).addTraits(new FT_Flammable(10_000), Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.NAPHTHA_CRACK =			new FluidType("NAPHTHA_CRACK",		0x595744, 2, 1, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x5F6D44)).addTraits(new FT_Flammable(125_000), new FT_Combustible(FuelGrade.MEDIUM, 200_000), Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.LIGHTOIL_CRACK =		new FluidType("LIGHTOIL_CRACK",		0x8c7451, 1, 2, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0xB46B52)).addTraits(new FT_Flammable(200_000), new FT_Combustible(FuelGrade.MEDIUM, 500_000), Fluids.LIQUID);
		Fluids.DIESEL_CRACK =			new FluidType("DIESEL_CRACK",		0xf2eed5, 1, 2, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0xFF2C2C)).addTraits(new FT_Flammable(200_000), new FT_Combustible(FuelGrade.HIGH, 450_000), Fluids.LIQUID);
		Fluids.AROMATICS =				new FluidType("AROMATICS",			0x68A09A, 1, 4, 1, EnumSymbol.NONE).addTraits(new FT_Flammable(25_000), Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.UNSATURATEDS =			new FluidType("UNSATURATEDS",		0x628FAE, 1, 4, 1, EnumSymbol.NONE).addTraits(new FT_Flammable(1_000_000), Fluids.GASEOUS); //acetylene burns as hot as satan's asshole
		Fluids.SALIENT =				new FluidType("SALIENT",			0x457F2D, 0, 0, 0, EnumSymbol.NONE).addTraits(Fluids.DELICIOUS, Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.XPJUICE =				new FluidType("XPJUICE",			0xBBFF09, 0, 0, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.ENDERJUICE =			new FluidType("ENDERJUICE",			0x127766, 0, 0, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.PETROIL_LEADED =		new FluidType("PETROIL_LEADED",		0x44413d, 1, 3, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x2331F6)).addTraits(new FT_Flammable(125_000), new FT_Combustible(FuelGrade.MEDIUM, 450_000), Fluids.LIQUID, Fluids.LEADED);
		Fluids.GASOLINE_LEADED =		new FluidType("GASOLINE_LEADED",	0x445772, 1, 2, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x2F775A)).addTraits(new FT_Flammable(400_000), new FT_Combustible(FuelGrade.HIGH, 1_500_000), Fluids.LIQUID, Fluids.LEADED);
		Fluids.COALGAS_LEADED =		new FluidType("COALGAS_LEADED",		0x445772, 1, 2, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x1E155F)).addTraits(new FT_Flammable(75_000), new FT_Combustible(FuelGrade.MEDIUM, 250_000), Fluids.LIQUID, Fluids.LEADED);
		Fluids.SULFURIC_ACID =			new FluidType("SULFURIC_ACID",		0xB0AA64, 3, 0, 2, EnumSymbol.ACID).addTraits(new FT_Corrosive(50), Fluids.LIQUID);
		Fluids.COOLANT_HOT =			new FluidType("COOLANT_HOT",		0x99525E, 1, 0, 0, EnumSymbol.NONE).setTemp(600).addTraits(Fluids.LIQUID);
		Fluids.MUG =					new FluidType("MUG",				0x4B2D28, 0, 0, 0, EnumSymbol.NONE).addTraits(Fluids.DELICIOUS, Fluids.LIQUID);
		Fluids.MUG_HOT =				new FluidType("MUG_HOT",			0x6B2A20, 0, 0, 0, EnumSymbol.NONE).setTemp(500).addTraits(Fluids.DELICIOUS, Fluids.LIQUID);
		Fluids.WOODOIL =				new FluidType("WOODOIL",			0x847D54, 2, 2, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0xBF7E4F)).addTraits(Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.COALCREOSOTE =			new FluidType("COALCREOSOTE",		0x51694F, 3, 2, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x285A3F)).addTraits(Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.SEEDSLURRY =			new FluidType("SEEDSLURRY",			0x7CC35E, 0, 0, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x7CC35E)).addTraits(Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.NITRIC_ACID =			new FluidType("NITRIC_ACID",		0xBB7A1E, 3, 0, 2, EnumSymbol.OXIDIZER).addTraits(Fluids.LIQUID, new FT_Corrosive(60));
		Fluids.SOLVENT =				new FluidType("SOLVENT",			0xE4E3EF, 2, 3, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0xE4E3EF)).addTraits(Fluids.LIQUID, new FT_Corrosive(30));
		Fluids.BLOOD =					new FluidType("BLOOD",				0xB22424, 0, 0, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.BLOOD_HOT =				new FluidType("BLOOD_HOT",			0xF22419, 3, 0, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID, Fluids.VISCOUS).setTemp(666); //it's funny because it's the satan number
		Fluids.SYNGAS =				new FluidType("SYNGAS",				0x131313, 1, 4, 2, EnumSymbol.NONE).addContainers(new CD_Gastank(0xFFFFFF, 0x131313)).addTraits(Fluids.GASEOUS);
		Fluids.OXYHYDROGEN =			new FluidType("OXYHYDROGEN",		0x483FC1, 0, 4, 2, EnumSymbol.NONE).addTraits(Fluids.GASEOUS);
		Fluids.RADIOSOLVENT =			new FluidType("RADIOSOLVENT",		0xA4D7DD, 3, 3, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID, Fluids.LEADCON, new FT_Corrosive(50), new FT_VentRadiation(0.01F));
		Fluids.CHLORINE =				new FluidType("CHLORINE",			0xBAB572, 3, 0, 0, EnumSymbol.OXIDIZER).addContainers(new CD_Gastank(0xBAB572, 0x887B34)).addTraits(Fluids.GASEOUS, new FT_Corrosive(25));
		Fluids.HEAVYOIL_VACUUM =		new FluidType("HEAVYOIL_VACUUM",	0x131214, 2, 1, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID, Fluids.VISCOUS).addContainers(new CD_Canister(0x513F39));
		Fluids.REFORMATE =				new FluidType("REFORMATE",			0x835472, 2, 2, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID, Fluids.VISCOUS).addContainers(new CD_Canister(0xD180D6));
		Fluids.LIGHTOIL_VACUUM =		new FluidType("LIGHTOIL_VACUUM",	0x8C8851, 1, 2, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID).addContainers(new CD_Canister(0xB46B52));
		Fluids.SOURGAS =				new FluidType("SOURGAS",			0xC9BE0D, 4, 4, 0, EnumSymbol.ACID).addContainers(new CD_Gastank(0xC9BE0D, 0x303030)).addTraits(Fluids.GASEOUS, new FT_Corrosive(10), new FT_Poison(false, 1));
		Fluids.XYLENE =				new FluidType("XYLENE",				0x5C4E76, 2, 3, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID, Fluids.VISCOUS).addContainers(new CD_Canister(0xA380D6));
		Fluids.HEATINGOIL_VACUUM =		new FluidType("HEATINGOIL_VACUUM",	0x211D06, 2, 2, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID, Fluids.VISCOUS).addContainers(new CD_Canister(0x694235));
		Fluids.DIESEL_REFORM =			new FluidType("DIESEL_REFORM",		0xCDC3C6, 1, 2, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID).addContainers(new CD_Canister(0xFFC500));
		Fluids.DIESEL_CRACK_REFORM =	new FluidType("DIESEL_CRACK_REFORM",0xCDC3CC, 1, 2, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID).addContainers(new CD_Canister(0xFFC500));
		Fluids.KEROSENE_REFORM =		new FluidType("KEROSENE_REFORM",	0xFFA5F3, 1, 2, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID).addContainers(new CD_Canister(0xFF377D));
		Fluids.REFORMGAS =				new FluidType("REFORMGAS",			0x6362AE, 1, 4, 1, EnumSymbol.NONE).addContainers(new CD_Gastank(0x9392FF, 0xFFB992)).addTraits(Fluids.GASEOUS);
		Fluids.COLLOID =				new FluidType("COLLOID",			0x787878, 0, 0, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.PHOSGENE =				new FluidType("PHOSGENE",			0xCFC4A4, 4, 0, 1, EnumSymbol.NONE).addContainers(new CD_Gastank(0xCFC4A4, 0x361414)).addTraits(Fluids.GASEOUS);
		Fluids.MUSTARDGAS =			new FluidType("MUSTARDGAS",			0xBAB572, 4, 1, 1, EnumSymbol.NONE).addContainers(new CD_Gastank(0xBAB572, 0x361414)).addTraits(Fluids.GASEOUS);
		Fluids.IONGEL =				new FluidType("IONGEL",				0xB8FFFF, 1, 0, 4, EnumSymbol.NONE).addTraits(Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.OIL_COKER =				new FluidType("OIL_COKER",			0x001802, 2, 1, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.NAPHTHA_COKER =			new FluidType("NAPHTHA_COKER",		0x495944, 2, 1, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID, Fluids.VISCOUS);
		Fluids.GAS_COKER =				new FluidType("GAS_COKER",			0xDEF4CA, 1, 4, 0, EnumSymbol.NONE).addTraits(Fluids.GASEOUS);
		Fluids.EGG =					new FluidType("EGG",				0xD2C273, 0, 0, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID);
		Fluids.CHOLESTEROL =			new FluidType("CHOLESTEROL",		0xD6D2BD, 0, 0, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID);
		Fluids.ESTRADIOL =				new FluidType("ESTRADIOL",			0xCDD5D8, 0, 0, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID);
		Fluids.FISHOIL =				new FluidType("FISHOIL",			0x4B4A45, 0, 1, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID);
		Fluids.SUNFLOWEROIL =			new FluidType("SUNFLOWEROIL",		0xCBAD45, 0, 1, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID);
		Fluids.NITROGLYCERIN =			new FluidType("NITROGLYCERIN",		0x92ACA6, 0, 4, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID);
		Fluids.REDMUD =				new FluidType("REDMUD",				0xD85638, 3, 0, 4, EnumSymbol.NONE).addTraits(Fluids.LIQUID, Fluids.VISCOUS, Fluids.LEADCON, new FT_Corrosive(60), new FT_Flammable(1_000));
		Fluids.CHLOROCALCITE_SOLUTION = new FluidType("CHLOROCALCITE_SOLUTION", 0x808080, 0, 0, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID, Fluids.NOCON, new FT_Corrosive(60));
		Fluids.CHLOROCALCITE_MIX =		new FluidType("CHLOROCALCITE_MIX",	0x808080, 0, 0, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID, Fluids.NOCON, new FT_Corrosive(60));
		Fluids.CHLOROCALCITE_CLEANED =	new FluidType("CHLOROCALCITE_CLEANED", 0x808080, 0, 0, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID, Fluids.NOCON, new FT_Corrosive(60));
		Fluids.POTASSIUM_CHLORIDE =	new FluidType("POTASSIUM_CHLORIDE",	0x808080, 0, 0, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID, Fluids.NOCON, new FT_Corrosive(60));
		Fluids.CALCIUM_CHLORIDE =		new FluidType("CALCIUM_CHLORIDE",	0x808080, 0, 0, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID, Fluids.NOCON, new FT_Corrosive(60));
		Fluids.CALCIUM_SOLUTION =		new FluidType("CALCIUM_SOLUTION",	0x808080, 0, 0, 0, EnumSymbol.NONE).addTraits(Fluids.LIQUID, Fluids.NOCON, new FT_Corrosive(60));
		Fluids.SMOKE =					new FluidType("SMOKE",				0x808080, 0, 0, 0, EnumSymbol.NONE).addTraits(Fluids.GASEOUS, Fluids.NOID, Fluids.NOCON);
		Fluids.SMOKE_LEADED =			new FluidType("SMOKE_LEADED",		0x808080, 0, 0, 0, EnumSymbol.NONE).addTraits(Fluids.GASEOUS, Fluids.NOID, Fluids.NOCON);
		Fluids.SMOKE_POISON =			new FluidType("SMOKE_POISON",		0x808080, 0, 0, 0, EnumSymbol.NONE).addTraits(Fluids.GASEOUS, Fluids.NOID, Fluids.NOCON);
		Fluids.HELIUM4 =				new FluidType("HELIUM4",			0xE54B0A, 0, 0, 0, EnumSymbol.ASPHYXIANT).addTraits(Fluids.GASEOUS);
		Fluids.HEAVYWATER_HOT =		new FluidType("HEAVYWATER_HOT",		0x4D007B, 1, 0, 0, EnumSymbol.NONE).setTemp(600).addTraits(Fluids.LIQUID);
		Fluids.SODIUM =				new FluidType("SODIUM",				0xCCD4D5, 1, 2, 3, EnumSymbol.NONE).setTemp(400).addTraits(Fluids.LIQUID);
		Fluids.SODIUM_HOT =			new FluidType("SODIUM_HOT",			0xE2ADC1, 1, 2, 3, EnumSymbol.NONE).setTemp(1200).addTraits(Fluids.LIQUID);
		Fluids.THORIUM_SALT =			new FluidType("THORIUM_SALT",		0x7A5542, 2, 0, 3, EnumSymbol.NONE).setTemp(800).addTraits(Fluids.LIQUID, new FT_Corrosive(65));
		Fluids.THORIUM_SALT_HOT =		new FluidType("THORIUM_SALT_HOT",	0x3E3627, 2, 0, 3, EnumSymbol.NONE).setTemp(1600).addTraits(Fluids.LIQUID, new FT_Corrosive(65));
		Fluids.THORIUM_SALT_DEPLETED =	new FluidType("THORIUM_SALT_DEPLETED",	0x302D1C, 2, 0, 3, EnumSymbol.NONE).setTemp(800).addTraits(Fluids.LIQUID, new FT_Corrosive(65));
		Fluids.FULLERENE =				new FluidType(130, "FULLERENE",		0xFF7FED, 3, 3, 3, EnumSymbol.NONE).addTraits(Fluids.LIQUID, new FT_Corrosive(65));
		
		// ^ ^ ^ ^ ^ ^ ^ ^
		//ADD NEW FLUIDS HERE
		
		File folder = MainRegistry.configHbmDir;
		File customTypes = new File(folder.getAbsolutePath() + File.separatorChar + "hbmFluidTypes.json");
		if(!customTypes.exists()) Fluids.initDefaultFluids(customTypes);
		Fluids.readCustomFluids(customTypes);
		
		//AND DON'T FORGET THE META DOWN HERE
		// V V V V V V V V
		
		//null
		Fluids.metaOrder.add(Fluids.NONE);
		//vanilla
		Fluids.metaOrder.add(Fluids.WATER);
		Fluids.metaOrder.add(Fluids.HEAVYWATER);
		Fluids.metaOrder.add(Fluids.HEAVYWATER_HOT);
		Fluids.metaOrder.add(Fluids.LAVA);
		//steams
		Fluids.metaOrder.add(Fluids.STEAM);
		Fluids.metaOrder.add(Fluids.HOTSTEAM);
		Fluids.metaOrder.add(Fluids.SUPERHOTSTEAM);
		Fluids.metaOrder.add(Fluids.ULTRAHOTSTEAM);
		Fluids.metaOrder.add(Fluids.SPENTSTEAM);
		//coolants
		Fluids.metaOrder.add(Fluids.CARBONDIOXIDE);
		Fluids.metaOrder.add(Fluids.COOLANT);
		Fluids.metaOrder.add(Fluids.COOLANT_HOT);
		Fluids.metaOrder.add(Fluids.CRYOGEL);
		Fluids.metaOrder.add(Fluids.MUG);
		Fluids.metaOrder.add(Fluids.MUG_HOT);
		Fluids.metaOrder.add(Fluids.BLOOD);
		Fluids.metaOrder.add(Fluids.BLOOD_HOT);
		Fluids.metaOrder.add(Fluids.SODIUM);
		Fluids.metaOrder.add(Fluids.SODIUM_HOT);
		Fluids.metaOrder.add(Fluids.THORIUM_SALT);
		Fluids.metaOrder.add(Fluids.THORIUM_SALT_HOT);
		Fluids.metaOrder.add(Fluids.THORIUM_SALT_DEPLETED);
		//pure elements, cyogenic gasses
		Fluids.metaOrder.add(Fluids.HYDROGEN);
		Fluids.metaOrder.add(Fluids.DEUTERIUM);
		Fluids.metaOrder.add(Fluids.TRITIUM);
		Fluids.metaOrder.add(Fluids.HELIUM3);
		Fluids.metaOrder.add(Fluids.HELIUM4);
		Fluids.metaOrder.add(Fluids.OXYGEN);
		Fluids.metaOrder.add(Fluids.XENON);
		Fluids.metaOrder.add(Fluids.CHLORINE);
		Fluids.metaOrder.add(Fluids.MERCURY);
		//oils, fuels
		Fluids.metaOrder.add(Fluids.OIL);
		Fluids.metaOrder.add(Fluids.CRACKOIL);
		Fluids.metaOrder.add(Fluids.COALOIL);
		Fluids.metaOrder.add(Fluids.OIL_COKER);
		Fluids.metaOrder.add(Fluids.HOTOIL);
		Fluids.metaOrder.add(Fluids.HOTCRACKOIL);
		Fluids.metaOrder.add(Fluids.HEAVYOIL);
		Fluids.metaOrder.add(Fluids.HEAVYOIL_VACUUM);
		Fluids.metaOrder.add(Fluids.NAPHTHA);
		Fluids.metaOrder.add(Fluids.NAPHTHA_CRACK);
		Fluids.metaOrder.add(Fluids.NAPHTHA_COKER);
		Fluids.metaOrder.add(Fluids.REFORMATE);
		Fluids.metaOrder.add(Fluids.LIGHTOIL);
		Fluids.metaOrder.add(Fluids.LIGHTOIL_CRACK);
		Fluids.metaOrder.add(Fluids.LIGHTOIL_VACUUM);
		Fluids.metaOrder.add(Fluids.BITUMEN);
		Fluids.metaOrder.add(Fluids.SMEAR);
		Fluids.metaOrder.add(Fluids.HEATINGOIL);
		Fluids.metaOrder.add(Fluids.HEATINGOIL_VACUUM);
		Fluids.metaOrder.add(Fluids.RECLAIMED);
		Fluids.metaOrder.add(Fluids.LUBRICANT);
		Fluids.metaOrder.add(Fluids.GAS);
		Fluids.metaOrder.add(Fluids.GAS_COKER);
		Fluids.metaOrder.add(Fluids.PETROLEUM);
		Fluids.metaOrder.add(Fluids.SOURGAS);
		Fluids.metaOrder.add(Fluids.LPG);
		Fluids.metaOrder.add(Fluids.SYNGAS);
		Fluids.metaOrder.add(Fluids.OXYHYDROGEN);
		Fluids.metaOrder.add(Fluids.AROMATICS);
		Fluids.metaOrder.add(Fluids.UNSATURATEDS);
		Fluids.metaOrder.add(Fluids.XYLENE);
		Fluids.metaOrder.add(Fluids.REFORMGAS);
		Fluids.metaOrder.add(Fluids.DIESEL);
		Fluids.metaOrder.add(Fluids.DIESEL_REFORM);
		Fluids.metaOrder.add(Fluids.DIESEL_CRACK);
		Fluids.metaOrder.add(Fluids.DIESEL_CRACK_REFORM);
		Fluids.metaOrder.add(Fluids.KEROSENE);
		Fluids.metaOrder.add(Fluids.KEROSENE_REFORM);
		Fluids.metaOrder.add(Fluids.PETROIL);
		Fluids.metaOrder.add(Fluids.PETROIL_LEADED);
		Fluids.metaOrder.add(Fluids.GASOLINE);
		Fluids.metaOrder.add(Fluids.GASOLINE_LEADED);
		Fluids.metaOrder.add(Fluids.COALGAS);
		Fluids.metaOrder.add(Fluids.COALGAS_LEADED);
		Fluids.metaOrder.add(Fluids.COALCREOSOTE);
		Fluids.metaOrder.add(Fluids.WOODOIL);
		Fluids.metaOrder.add(Fluids.BIOGAS);
		Fluids.metaOrder.add(Fluids.BIOFUEL);
		Fluids.metaOrder.add(Fluids.ETHANOL);
		Fluids.metaOrder.add(Fluids.FISHOIL);
		Fluids.metaOrder.add(Fluids.SUNFLOWEROIL);
		Fluids.metaOrder.add(Fluids.NITAN);
		Fluids.metaOrder.add(Fluids.BALEFIRE);
		//processing fluids
		Fluids.metaOrder.add(Fluids.SALIENT);
		Fluids.metaOrder.add(Fluids.SEEDSLURRY);
		Fluids.metaOrder.add(Fluids.COLLOID);
		Fluids.metaOrder.add(Fluids.IONGEL);
		Fluids.metaOrder.add(Fluids.ACID);
		Fluids.metaOrder.add(Fluids.SULFURIC_ACID);
		Fluids.metaOrder.add(Fluids.NITRIC_ACID);
		Fluids.metaOrder.add(Fluids.SOLVENT);
		Fluids.metaOrder.add(Fluids.RADIOSOLVENT);
		Fluids.metaOrder.add(Fluids.SCHRABIDIC);
		Fluids.metaOrder.add(Fluids.UF6);
		Fluids.metaOrder.add(Fluids.PUF6);
		Fluids.metaOrder.add(Fluids.SAS3);
		Fluids.metaOrder.add(Fluids.PAIN);
		Fluids.metaOrder.add(Fluids.DEATH);
		Fluids.metaOrder.add(Fluids.WATZ);
		Fluids.metaOrder.add(Fluids.REDMUD);
		Fluids.metaOrder.add(Fluids.FULLERENE);
		Fluids.metaOrder.add(Fluids.EGG);
		Fluids.metaOrder.add(Fluids.CHOLESTEROL);
		Fluids.metaOrder.add(Fluids.CHLOROCALCITE_SOLUTION);
		Fluids.metaOrder.add(Fluids.CHLOROCALCITE_MIX);
		Fluids.metaOrder.add(Fluids.CHLOROCALCITE_CLEANED);
		Fluids.metaOrder.add(Fluids.POTASSIUM_CHLORIDE);
		Fluids.metaOrder.add(Fluids.CALCIUM_CHLORIDE);
		Fluids.metaOrder.add(Fluids.CALCIUM_SOLUTION);
		//solutions and working fluids
		Fluids.metaOrder.add(Fluids.FRACKSOL);
		//the fun guys
		Fluids.metaOrder.add(Fluids.PHOSGENE);
		Fluids.metaOrder.add(Fluids.MUSTARDGAS);
		Fluids.metaOrder.add(Fluids.ESTRADIOL);
		Fluids.metaOrder.add(Fluids.NITROGLYCERIN);
		//antimatter
		Fluids.metaOrder.add(Fluids.AMAT);
		Fluids.metaOrder.add(Fluids.ASCHRAB);
		//nuclear waste
		Fluids.metaOrder.add(Fluids.WASTEFLUID);
		Fluids.metaOrder.add(Fluids.WASTEGAS);
		//garbage
		Fluids.metaOrder.add(Fluids.XPJUICE);
		Fluids.metaOrder.add(Fluids.ENDERJUICE);
		//plasma
		Fluids.metaOrder.add(Fluids.PLASMA_DT);
		Fluids.metaOrder.add(Fluids.PLASMA_HD);
		Fluids.metaOrder.add(Fluids.PLASMA_HT);
		Fluids.metaOrder.add(Fluids.PLASMA_DH3);
		Fluids.metaOrder.add(Fluids.PLASMA_XM);
		Fluids.metaOrder.add(Fluids.PLASMA_BF);
		//smoke
		Fluids.metaOrder.add(Fluids.SMOKE);
		Fluids.metaOrder.add(Fluids.SMOKE_LEADED);
		Fluids.metaOrder.add(Fluids.SMOKE_POISON);
		
		for(FluidType custom : Fluids.customFluids) Fluids.metaOrder.add(custom);

		Fluids.CHLORINE.addTraits(new FT_Toxin().addEntry(new ToxinDirectDamage(ModDamageSource.cloud, 2F, 20, HazardClass.GAS_CHLORINE, false)));
		Fluids.PHOSGENE.addTraits(new FT_Toxin().addEntry(new ToxinDirectDamage(ModDamageSource.cloud, 4F, 20, HazardClass.GAS_CHLORINE, false)));
		Fluids.MUSTARDGAS.addTraits(new FT_Toxin().addEntry(new ToxinDirectDamage(ModDamageSource.cloud, 4F, 10, HazardClass.GAS_CORROSIVE, false))
				.addEntry(new ToxinEffects(HazardClass.GAS_CORROSIVE, true).add(new PotionEffect(Potion.wither.id, 100, 1), new PotionEffect(Potion.confusion.id, 100, 0))));
		Fluids.ESTRADIOL.addTraits(new FT_Toxin().addEntry(new ToxinEffects(HazardClass.PARTICLE_FINE, false).add(new PotionEffect(HbmPotion.death.id, 60 * 60 * 20, 0))));
		Fluids.REDMUD.addTraits(new FT_Toxin().addEntry(new ToxinEffects(HazardClass.GAS_CORROSIVE, false).add(new PotionEffect(Potion.wither.id, 30 * 20, 2))));

		double eff_steam_boil = 1.0D;
		double eff_steam_heatex = 0.25D;
		
		Fluids.WATER.addTraits(new FT_Heatable().setEff(HeatingType.BOILER, eff_steam_boil).setEff(HeatingType.HEATEXCHANGER, eff_steam_heatex)
				.addStep(200, 1, Fluids.STEAM, 100)
				.addStep(220, 1, Fluids.HOTSTEAM, 10)
				.addStep(238, 1, Fluids.SUPERHOTSTEAM, 1)
				.addStep(2500, 10, Fluids.ULTRAHOTSTEAM, 1));

		Fluids.STEAM.addTraits(new FT_Heatable().setEff(HeatingType.BOILER, eff_steam_boil).setEff(HeatingType.HEATEXCHANGER, eff_steam_heatex).addStep(2, 10, Fluids.HOTSTEAM, 1));
		Fluids.HOTSTEAM.addTraits(new FT_Heatable().setEff(HeatingType.BOILER, eff_steam_boil).setEff(HeatingType.HEATEXCHANGER, eff_steam_heatex).addStep(18, 10, Fluids.SUPERHOTSTEAM, 1));
		Fluids.SUPERHOTSTEAM.addTraits(new FT_Heatable().setEff(HeatingType.BOILER, eff_steam_boil).setEff(HeatingType.HEATEXCHANGER, eff_steam_heatex).addStep(120, 10, Fluids.ULTRAHOTSTEAM, 1));

		double eff_steam_turbine = 1.0D;
		double eff_steam_cool = 0.5D;
		Fluids.STEAM.addTraits(new FT_Coolable(Fluids.SPENTSTEAM, 100, 1, 200).setEff(CoolingType.TURBINE, eff_steam_turbine).setEff(CoolingType.HEATEXCHANGER, eff_steam_cool));
		Fluids.HOTSTEAM.addTraits(new FT_Coolable(Fluids.STEAM, 1, 10, 2).setEff(CoolingType.TURBINE, eff_steam_turbine).setEff(CoolingType.HEATEXCHANGER, eff_steam_cool));
		Fluids.SUPERHOTSTEAM.addTraits(new FT_Coolable(Fluids.HOTSTEAM, 1, 10, 18).setEff(CoolingType.TURBINE, eff_steam_turbine).setEff(CoolingType.HEATEXCHANGER, eff_steam_cool));
		Fluids.ULTRAHOTSTEAM.addTraits(new FT_Coolable(Fluids.SUPERHOTSTEAM, 1, 10, 120).setEff(CoolingType.TURBINE, eff_steam_turbine).setEff(CoolingType.HEATEXCHANGER, eff_steam_cool));
		
		Fluids.OIL.addTraits(new FT_Heatable().setEff(HeatingType.BOILER, 1.0D).setEff(HeatingType.HEATEXCHANGER, 1.0D).addStep(10, 1, Fluids.HOTOIL, 1));
		Fluids.CRACKOIL.addTraits(new FT_Heatable().setEff(HeatingType.BOILER, 1.0D).setEff(HeatingType.HEATEXCHANGER, 1.0D).addStep(10, 1, Fluids.HOTCRACKOIL, 1));

		Fluids.HOTOIL.addTraits(new FT_Coolable(Fluids.OIL, 1, 1, 10).setEff(CoolingType.HEATEXCHANGER, 1.0D));
		Fluids.HOTCRACKOIL.addTraits(new FT_Coolable(Fluids.CRACKOIL, 1, 1, 10).setEff(CoolingType.HEATEXCHANGER, 1.0D));

		Fluids.COOLANT.addTraits(new FT_Heatable().setEff(HeatingType.HEATEXCHANGER, 1.0D).setEff(HeatingType.PWR, 1.0D).addStep(300, 1, Fluids.COOLANT_HOT, 1));
		Fluids.COOLANT_HOT.addTraits(new FT_Coolable(Fluids.COOLANT, 1, 1, 300).setEff(CoolingType.HEATEXCHANGER, 1.0D));
		
		Fluids.MUG.addTraits(new FT_Heatable().setEff(HeatingType.HEATEXCHANGER, 1.0D).setEff(HeatingType.PWR, 1.0D).addStep(400, 1, Fluids.MUG_HOT, 1), new FT_PWRModerator(1.15D));
		Fluids.MUG_HOT.addTraits(new FT_Coolable(Fluids.MUG, 1, 1, 400).setEff(CoolingType.HEATEXCHANGER, 1.0D));
		
		Fluids.BLOOD.addTraits(new FT_Heatable().setEff(HeatingType.HEATEXCHANGER, 1.0D).addStep(500, 1, Fluids.BLOOD_HOT, 1));
		Fluids.BLOOD_HOT.addTraits(new FT_Coolable(Fluids.BLOOD, 1, 1, 500).setEff(CoolingType.HEATEXCHANGER, 1.0D));
		
		Fluids.HEAVYWATER.addTraits(new FT_Heatable().setEff(HeatingType.PWR, 1.0D).addStep(300, 1, Fluids.HEAVYWATER_HOT, 1), new FT_PWRModerator(1.25D));
		Fluids.HEAVYWATER_HOT.addTraits(new FT_Coolable(Fluids.HEAVYWATER, 1, 1, 300).setEff(CoolingType.HEATEXCHANGER, 1.0D));
		
		Fluids.SODIUM.addTraits(new FT_Heatable().setEff(HeatingType.PWR, 2.5D).addStep(400, 1, Fluids.SODIUM_HOT, 1));
		Fluids.SODIUM_HOT.addTraits(new FT_Coolable(Fluids.SODIUM, 1, 1, 400).setEff(CoolingType.HEATEXCHANGER, 1.0D));
		
		Fluids.THORIUM_SALT.addTraits(new FT_Heatable().setEff(HeatingType.PWR, 1.0D).addStep(400, 1, Fluids.THORIUM_SALT_HOT, 1), new FT_PWRModerator(2.5D));
		Fluids.THORIUM_SALT_HOT.addTraits(new FT_Coolable(Fluids.THORIUM_SALT_DEPLETED, 1, 1, 400).setEff(CoolingType.HEATEXCHANGER, 1.0D));
		
		if(Fluids.idMapping.size() != Fluids.metaOrder.size()) {
			throw new IllegalStateException("A severe error has occoured during NTM's fluid registering process! The MetaOrder and Mappings are inconsistent! Mapping size: " + Fluids.idMapping.size()+ " / MetaOrder size: " + Fluids.metaOrder.size());
		}
		
		
		/// EXPERIMENTAL ///
		
		long baseline = 100_000L; //we do not know
		double demandVeryLow = 0.5D;
		double demandLow = 1.0D;
		double demandMedium = 1.5D;
		double demandHigh = 2.0D;
		double complexityRefinery = 1.1D;
		double complexityFraction = 1.05D;
		double complexityCracking = 1.25D;
		double complexityCoker = 1.25D;
		double complexityChemplant = 1.1D;
		double complexityLubed = 1.15D;
		double complexityLeaded = 1.5D;
		double complexityVacuum = 3.0D;
		double complexityReform = 2.5D;
		double flammabilityLow = 0.25D;
		double flammabilityNormal = 1.0D;
		double flammabilityHigh = 2.0D;

		/// the allmighty excel spreadsheet has spoken! ///
		Fluids.registerCalculatedFuel(Fluids.OIL, (baseline / 1D * flammabilityLow * demandLow), 0, null);
		Fluids.registerCalculatedFuel(Fluids.CRACKOIL, (baseline / 1D * flammabilityLow * demandLow * complexityCracking), 0, null);
		Fluids.registerCalculatedFuel(Fluids.OIL_COKER, (baseline / 1D * flammabilityLow * demandLow * complexityCoker), 0, null);
		Fluids.registerCalculatedFuel(Fluids.GAS, (baseline / 1D * flammabilityNormal * demandVeryLow), 1.25, FuelGrade.GAS);
		Fluids.registerCalculatedFuel(Fluids.GAS_COKER, (baseline / 1D * flammabilityNormal * demandVeryLow * complexityCoker), 1.25, FuelGrade.GAS);
		Fluids.registerCalculatedFuel(Fluids.HEAVYOIL, (baseline / 0.5 * flammabilityLow * demandLow * complexityRefinery), 1.25D, FuelGrade.LOW);
		Fluids.registerCalculatedFuel(Fluids.SMEAR, (baseline / 0.35 * flammabilityLow * demandLow * complexityRefinery * complexityFraction), 1.25D, FuelGrade.LOW);
		Fluids.registerCalculatedFuel(Fluids.RECLAIMED, (baseline / 0.28 * flammabilityLow * demandLow * complexityRefinery * complexityFraction * complexityChemplant), 1.25D, FuelGrade.LOW);
		Fluids.registerCalculatedFuel(Fluids.PETROIL, (baseline / 0.28 * flammabilityLow * demandLow * complexityRefinery * complexityFraction * complexityChemplant * complexityLubed), 1.5D, FuelGrade.MEDIUM);
		Fluids.registerCalculatedFuel(Fluids.PETROIL_LEADED, (baseline / 0.28 * flammabilityLow * demandLow * complexityRefinery * complexityFraction * complexityChemplant * complexityLubed * complexityLeaded), 1.5D, FuelGrade.MEDIUM);
		Fluids.registerCalculatedFuel(Fluids.HEATINGOIL, (baseline / 0.31 * flammabilityNormal * demandLow * complexityRefinery * complexityFraction * complexityFraction), 1.25D, FuelGrade.LOW);
		Fluids.registerCalculatedFuel(Fluids.NAPHTHA, (baseline / 0.25 * flammabilityLow * demandLow * complexityRefinery), 1.5D, FuelGrade.MEDIUM);
		Fluids.registerCalculatedFuel(Fluids.NAPHTHA_CRACK, (baseline / 0.40 * flammabilityLow * demandLow * complexityRefinery * complexityCracking), 1.5D, FuelGrade.MEDIUM);
		Fluids.registerCalculatedFuel(Fluids.NAPHTHA_COKER, (baseline / 0.25 * flammabilityLow * demandLow * complexityCoker), 1.5D, FuelGrade.MEDIUM);
		Fluids.registerCalculatedFuel(Fluids.GASOLINE, (baseline / 0.20 * flammabilityNormal * demandLow * complexityRefinery * complexityChemplant), 2.5D, FuelGrade.HIGH);
		Fluids.registerCalculatedFuel(Fluids.GASOLINE_LEADED, (baseline / 0.20 * flammabilityNormal * demandLow * complexityRefinery * complexityChemplant * complexityLeaded), 2.5D, FuelGrade.HIGH);
		Fluids.registerCalculatedFuel(Fluids.DIESEL, (baseline / 0.21 * flammabilityNormal * demandLow * complexityRefinery * complexityFraction), 2.5D, FuelGrade.HIGH);
		Fluids.registerCalculatedFuel(Fluids.DIESEL_CRACK, (baseline / 0.28 * flammabilityNormal * demandLow * complexityRefinery * complexityCracking * complexityFraction), 2.5D, FuelGrade.HIGH);
		Fluids.registerCalculatedFuel(Fluids.LIGHTOIL, (baseline / 0.15 * flammabilityNormal * demandHigh * complexityRefinery), 1.5D, FuelGrade.MEDIUM);
		Fluids.registerCalculatedFuel(Fluids.LIGHTOIL_CRACK, (baseline / 0.30 * flammabilityNormal * demandHigh * complexityRefinery * complexityCracking), 1.5D, FuelGrade.MEDIUM);
		Fluids.registerCalculatedFuel(Fluids.KEROSENE, (baseline / 0.09 * flammabilityNormal * demandHigh * complexityRefinery * complexityFraction), 1.5D, FuelGrade.AERO);
		Fluids.registerCalculatedFuel(Fluids.PETROLEUM, (baseline / 0.10 * flammabilityNormal * demandMedium * complexityRefinery), 1.25, FuelGrade.GAS);
		Fluids.registerCalculatedFuel(Fluids.AROMATICS, (baseline / 0.15 * flammabilityLow * demandHigh * complexityRefinery * complexityCracking), 0, null);
		Fluids.registerCalculatedFuel(Fluids.UNSATURATEDS, (baseline / 0.15 * flammabilityHigh * demandHigh * complexityRefinery * complexityCracking), 0, null);
		Fluids.registerCalculatedFuel(Fluids.LPG, (baseline / 0.05 * flammabilityNormal * demandMedium * complexityRefinery * complexityChemplant), 2.5, FuelGrade.HIGH);
		Fluids.registerCalculatedFuel(Fluids.NITAN, Fluids.KEROSENE.getTrait(FT_Flammable.class).getHeatEnergy() * 25L, 2.5, FuelGrade.HIGH);
		Fluids.registerCalculatedFuel(Fluids.BALEFIRE, Fluids.KEROSENE.getTrait(FT_Flammable.class).getHeatEnergy() * 100L, 2.5, FuelGrade.HIGH);
		Fluids.registerCalculatedFuel(Fluids.HEAVYOIL_VACUUM, (baseline / 0.4 * flammabilityLow * demandLow * complexityVacuum), 1.25D, FuelGrade.LOW);
		Fluids.registerCalculatedFuel(Fluids.REFORMATE, (baseline / 0.25 * flammabilityNormal * demandHigh * complexityVacuum), 2.5D, FuelGrade.HIGH);
		Fluids.registerCalculatedFuel(Fluids.LIGHTOIL_VACUUM, (baseline / 0.20 * flammabilityNormal * demandHigh * complexityVacuum), 1.5D, FuelGrade.MEDIUM);
		Fluids.registerCalculatedFuel(Fluids.SOURGAS, (baseline / 0.15 * flammabilityLow * demandVeryLow * complexityVacuum), 0, null);
		Fluids.registerCalculatedFuel(Fluids.XYLENE, (baseline / 0.15 * flammabilityNormal * demandMedium * complexityVacuum * complexityFraction), 2.5D, FuelGrade.HIGH);
		Fluids.registerCalculatedFuel(Fluids.HEATINGOIL_VACUUM, (baseline / 0.24 * flammabilityNormal * demandLow * complexityVacuum * complexityFraction), 1.25D, FuelGrade.LOW);
		Fluids.registerCalculatedFuel(Fluids.DIESEL_REFORM, Fluids.DIESEL.getTrait(FT_Flammable.class).getHeatEnergy() * complexityReform, 2.5D, FuelGrade.HIGH);
		Fluids.registerCalculatedFuel(Fluids.DIESEL_CRACK_REFORM, Fluids.DIESEL_CRACK.getTrait(FT_Flammable.class).getHeatEnergy() * complexityReform, 2.5D, FuelGrade.HIGH);
		Fluids.registerCalculatedFuel(Fluids.KEROSENE_REFORM, Fluids.KEROSENE.getTrait(FT_Flammable.class).getHeatEnergy() * complexityReform, 1.5D, FuelGrade.AERO);
		Fluids.registerCalculatedFuel(Fluids.REFORMGAS, (baseline / 0.06 * flammabilityHigh * demandLow * complexityVacuum * complexityFraction), 1.25D, FuelGrade.GAS);
		
		//all hail the spreadsheet
		//the spreadsheet must not be questioned
		//none may enter the orb- i mean the spreadsheet
		
		int coalHeat = 400_000; // 200TU/t for 2000 ticks
		Fluids.registerCalculatedFuel(Fluids.COALOIL, (coalHeat * (1000 /* bucket */ / 100 /* mB per coal */) * flammabilityLow * demandLow * complexityChemplant), 0, null);
		long coaloil = Fluids.COALOIL.getTrait(FT_Flammable.class).getHeatEnergy();
		Fluids.registerCalculatedFuel(Fluids.COALGAS, (coaloil / 0.3 * flammabilityNormal * demandMedium * complexityChemplant * complexityFraction), 1.5, FuelGrade.MEDIUM);
		Fluids.registerCalculatedFuel(Fluids.COALGAS_LEADED, (coaloil / 0.3 * flammabilityNormal * demandMedium * complexityChemplant * complexityFraction * complexityLeaded), 1.5, FuelGrade.MEDIUM);

		Fluids.registerCalculatedFuel(Fluids.ETHANOL, 275_000D /* diesel / 2 */, 2.5D, FuelGrade.HIGH);

		Fluids.registerCalculatedFuel(Fluids.BIOGAS, 250_000D * flammabilityLow /* biofuel with half compression, terrible flammability */, 1.25, FuelGrade.GAS);
		Fluids.registerCalculatedFuel(Fluids.BIOFUEL, 500_000D /* slightly below diesel */, 2.5D, FuelGrade.HIGH);

		Fluids.registerCalculatedFuel(Fluids.WOODOIL, 110_000 /* 20_000 TU per 250mB + a bonus */, 0, null);
		Fluids.registerCalculatedFuel(Fluids.COALCREOSOTE, 250_000 /* 20_000 TU per 100mB + a bonus */, 0, null);
		Fluids.registerCalculatedFuel(Fluids.FISHOIL, 75_000, 0, null);
		Fluids.registerCalculatedFuel(Fluids.SUNFLOWEROIL, 50_000, 0, null);

		Fluids.registerCalculatedFuel(Fluids.SOLVENT, 100_000, 0, null); // flammable, sure, but not combustable
		Fluids.registerCalculatedFuel(Fluids.RADIOSOLVENT, 150_000, 0, null);

		Fluids.registerCalculatedFuel(Fluids.SYNGAS, (coalHeat * (1000 /* bucket */ / 100 /* mB per coal */) * flammabilityLow * demandLow * complexityChemplant) * 1.5, 1.25, FuelGrade.GAS); //same as coal oil, +50% bonus
		Fluids.registerCalculatedFuel(Fluids.OXYHYDROGEN, 5_000, 3, FuelGrade.GAS); // whatever
		
		File config = new File(folder.getAbsolutePath() + File.separatorChar + "hbmFluidTraits.json");
		File template = new File(folder.getAbsolutePath() + File.separatorChar + "_hbmFluidTraits.json");
		
		if(!config.exists()) {
			Fluids.writeDefaultTraits(template);
		} else {
			Fluids.readTraits(config);
		}
	}
	
	private static void initDefaultFluids(File file) {
		
		try {
			JsonWriter writer = new JsonWriter(new FileWriter(file));
			writer.setIndent("  ");
			writer.beginObject();
			
			writer.name("CUSTOM_DEMO").beginObject();
			writer.name("name").value("Custom Fluid Demo");
			writer.name("id").value(1000);
			writer.name("color").value(0xff0000);
			writer.name("tint").value(0xff0000);
			writer.name("p").value(1).name("f").value(2).name("r").value(0);
			writer.name("symbol").value(EnumSymbol.OXIDIZER.name());
			writer.name("texture").value("custom_water");
			writer.name("temperature").value(20);
			writer.endObject();
			
			writer.endObject();
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void readCustomFluids(File file) {
		
		try {
			JsonObject json = Fluids.gson.fromJson(new FileReader(file), JsonObject.class);
			
			for(Entry<String, JsonElement> entry : json.entrySet()) {
				
				JsonObject obj = (JsonObject) entry.getValue();

				String name = entry.getKey();
				int id = obj.get("id").getAsInt();
				String displayName = obj.get("name").getAsString();
				int color = obj.get("color").getAsInt();
				int tint = obj.get("tint").getAsInt();
				int p = obj.get("p").getAsInt();
				int f = obj.get("f").getAsInt();
				int r = obj.get("r").getAsInt();
				EnumSymbol symbol = EnumSymbol.valueOf(obj.get("symbol").getAsString());
				String texture = obj.get("texture").getAsString();
				int temperature = obj.get("temperature").getAsInt();
				
				FluidType type = new FluidType(name, color, p, f, r, symbol, texture, tint, id, displayName).setTemp(temperature);
				Fluids.customFluids.add(type);
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private static void writeDefaultTraits(File file) {

		try {
			JsonWriter writer = new JsonWriter(new FileWriter(file));
			writer.setIndent("  ");
			writer.beginObject();
			
			for(FluidType type : Fluids.metaOrder) {
				writer.name(type.getName()).beginObject();
				
				for(Entry<Class<? extends FluidTrait>, FluidTrait> entry : type.traits.entrySet()) {
					writer.name(FluidTrait.traitNameMap.inverse().get(entry.getKey())).beginObject();
					entry.getValue().serializeJSON(writer);
					writer.endObject();
				}
				
				writer.endObject();
			}
			
			writer.endObject();
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void readTraits(File config) {
		
		try {
			JsonObject json = Fluids.gson.fromJson(new FileReader(config), JsonObject.class);
			
			for(FluidType type : Fluids.metaOrder) {
				
				JsonElement element = json.get(type.getName());
				if(element != null) {
					type.traits.clear();
					JsonObject obj = element.getAsJsonObject();
					
					for(Entry<String, JsonElement> entry : obj.entrySet()) {
						Class<? extends FluidTrait> traitClass = FluidTrait.traitNameMap.get(entry.getKey());
						try {
							FluidTrait trait = traitClass.newInstance();
							trait.deserializeJSON(entry.getValue().getAsJsonObject());
							type.addTraits(trait);
						} catch(Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private static void registerCalculatedFuel(FluidType type, double base, double combustMult, FuelGrade grade) {
		
		long flammable = (long) base;
		long combustible = (long) (base * combustMult);

		flammable = Fluids.round(flammable);
		combustible = Fluids.round(combustible);

		type.addTraits(new FT_Flammable(flammable));
		
		if(combustible > 0 && grade != null)
			type.addTraits(new FT_Combustible(grade, combustible));
	}
	
	/** ugly but it does the thing well enough */
	private static long round(long l) {
		if(l > 10_000_000L) return l - (l % 100_000L);
		if(l > 1_000_000L) return l - (l % 10_000L);
		if(l > 100_000L) return l - (l % 1_000L);
		if(l > 10_000L) return l - (l % 100L);
		if(l > 1_000L) return l - (l % 10L);
		
		return l;
	}
	
	protected static int registerSelf(FluidType fluid) {
		int id = Fluids.idMapping.size();
		Fluids.idMapping.put(id, fluid);
		Fluids.registerOrder.add(fluid);
		Fluids.nameMapping.put(fluid.getName(), fluid);
		return id;
	}
	
	protected static void register(FluidType fluid, int id) {
		Fluids.idMapping.put(id, fluid);
		Fluids.registerOrder.add(fluid);
		Fluids.nameMapping.put(fluid.getName(), fluid);
	}
	
	public static FluidType fromID(int id) {
		FluidType fluid = Fluids.idMapping.get(id);
		
		if(fluid == null)
			fluid = Fluids.NONE;
		
		return fluid;
	}
	
	public static FluidType fromName(String name) {
		FluidType fluid = Fluids.nameMapping.get(name);
		
		if(fluid == null)
			fluid = Fluids.NONE;
		
		return fluid;
	}
	
	public static FluidType[] getAll() {
		return Fluids.getInOrder(false);
	}
	
	public static FluidType[] getInNiceOrder() {
		return Fluids.getInOrder(true);
	}
	
	private static FluidType[] getInOrder(final boolean nice) {
		FluidType[] all = new FluidType[Fluids.idMapping.size()];
		
		for(int i = 0; i < all.length; i++) {
			FluidType type = nice ? Fluids.metaOrder.get(i) : Fluids.registerOrder.get(i);
			
			if(type == null) {
				throw new IllegalStateException("A severe error has occoured with NTM's fluid system! Fluid of the ID " + i + " has returned NULL in the registry!");
			}
			
			all[i] = type;
		}
		
		return all;
	}
	
	public static class CD_Canister {
		public int color;
		public CD_Canister(int color) { this.color = color; }
	}
	
	public static class CD_Gastank {
		public int bottleColor, labelColor;
		public CD_Gastank(int color1, int color2) { this.bottleColor = color1; this.labelColor = color2; }
	}
}