package com.hbm.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.hbm.blocks.BlockEnums.EnumStoneType;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.hazard.HazardData;
import com.hbm.hazard.HazardEntry;
import com.hbm.hazard.HazardRegistry;
import com.hbm.hazard.HazardSystem;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.inventory.material.NTMMaterial.SmeltingBehavior;
import com.hbm.items.ItemEnums.EnumAshType;
import com.hbm.items.ItemEnums.EnumBriquetteType;
import com.hbm.items.ItemEnums.EnumCokeType;
import com.hbm.items.ItemEnums.EnumTarType;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemBedrockOre.EnumBedrockOre;
import com.hbm.main.MainRegistry;
import com.hbm.util.Compat;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

//the more i optimize this, the more it starts looking like gregtech
@SuppressWarnings("all")
public class OreDictManager {
	
	/** Alternate, additional names for ore dict registration. Used mostly for DictGroups */
	private static final HashMap<String, HashSet<String>> reRegistration = new HashMap();

	/*
	 * Standard keys
	 */
	public static final String KEY_STICK = "stickWood";					//if there's no "any" or "<shape>Any" prefix required, simply use a String key instead of a DictFrame
	public static final String KEY_ANYGLASS = "blockGlass";
	public static final String KEY_CLEARGLASS = "blockGlassColorless";
	public static final String KEY_ANYPANE = "paneGlass";
	public static final String KEY_CLEARPANE = "paneGlassColorless";
	public static final String KEY_BRICK = "ingotBrick";
	public static final String KEY_NETHERBRICK = "ingotBrickNether";
	public static final String KEY_SLIME = "slimeball";
	public static final String KEY_LOG = "logWood";
	public static final String KEY_PLANKS = "plankWood";
	public static final String KEY_SLAB = "slabWood";
	public static final String KEY_LEAVES = "treeLeaves";
	public static final String KEY_SAPLING = "treeSapling";
	public static final String KEY_SAND = "sand";
	public static final String KEY_COBBLESTONE = "cobblestone";
	
	public static final String KEY_BLACK = "dyeBlack";
	public static final String KEY_RED = "dyeRed";
	public static final String KEY_GREEN = "dyeGreen";
	public static final String KEY_BROWN = "dyeBrown";
	public static final String KEY_BLUE = "dyeBlue";
	public static final String KEY_PURPLE = "dyePurple";
	public static final String KEY_CYAN = "dyeCyan";
	public static final String KEY_LIGHTGRAY = "dyeLightGray";
	public static final String KEY_GRAY = "dyeGray";
	public static final String KEY_PINK = "dyePink";
	public static final String KEY_LIME = "dyeLime";
	public static final String KEY_YELLOW = "dyeYellow";
	public static final String KEY_LIGHTBLUE = "dyeLightBlue";
	public static final String KEY_MAGENTA = "dyeMagenta";
	public static final String KEY_ORANGE = "dyeOrange";
	public static final String KEY_WHITE = "dyeWhite";

	public static final String KEY_OIL_TAR = "oiltar";
	public static final String KEY_CRACK_TAR = "cracktar";
	public static final String KEY_COAL_TAR = "coaltar";
	public static final String KEY_WOOD_TAR = "woodtar";

	public static final String KEY_UNIVERSAL_TANK = "ntmuniversaltank";
	public static final String KEY_HAZARD_TANK = "ntmhazardtank";
	public static final String KEY_UNIVERSAL_BARREL = "ntmuniversalbarrel";

	public static final String KEY_TOOL_SCREWDRIVER = "ntmscrewdriver";
	public static final String KEY_TOOL_HANDDRILL = "ntmhanddrill";
	public static final String KEY_TOOL_CHEMISTRYSET = "ntmchemistryset";
	public static final String KEY_TOOL_TORCH = "ntmtorch";

	public static final String KEY_CIRCUIT_BISMUTH = "circuitVersatile";

	/*
	 * MATERIALS
	 */
	/*
	 * VANILLA
	 */
	public static final DictFrame COAL = new DictFrame("Coal");
	public static final DictFrame IRON = new DictFrame("Iron");
	public static final DictFrame GOLD = new DictFrame("Gold");
	public static final DictFrame LAPIS = new DictFrame("Lapis");
	public static final DictFrame REDSTONE = new DictFrame("Redstone");
	public static final DictFrame NETHERQUARTZ = new DictFrame("NetherQuartz");
	public static final DictFrame DIAMOND = new DictFrame("Diamond");
	public static final DictFrame EMERALD = new DictFrame("Emerald");
	/*
	 * RADIOACTIVE
	 */
	public static final DictFrame U = new DictFrame(Compat.isModLoaded(Compat.MOD_GT6) ? "Uraninite" : "Uranium");
	public static final DictFrame U233 = new DictFrame("Uranium233", "U233");
	public static final DictFrame U235 = new DictFrame("Uranium235", "U235");
	public static final DictFrame U238 = new DictFrame("Uranium238", "U238");
	public static final DictFrame TH232 = new DictFrame("Thorium232", "Th232", "Thorium");
	public static final DictFrame PU = new DictFrame("Plutonium");
	public static final DictFrame PURG = new DictFrame("PlutoniumRG");
	public static final DictFrame PU238 = new DictFrame("Plutonium238", "Pu238");
	public static final DictFrame PU239 = new DictFrame("Plutonium239", "Pu239");
	public static final DictFrame PU240 = new DictFrame("Plutonium240", "Pu240");
	public static final DictFrame PU241 = new DictFrame("Plutonium241", "Pu241");
	public static final DictFrame AM241 = new DictFrame("Americium241", "Am241");
	public static final DictFrame AM242 = new DictFrame("Americium242", "Am242");
	public static final DictFrame AMRG = new DictFrame("AmericiumRG");
	public static final DictFrame NP237 = new DictFrame("Neptunium237", "Np237", "Neptunium");
	public static final DictFrame PO210 = new DictFrame("Polonium210", "Po210", "Polonium");
	public static final DictFrame TC99 = new DictFrame("Technetium99", "Tc99");
	public static final DictFrame RA226 = new DictFrame("Radium226", "Ra226");
	public static final DictFrame AC227 = new DictFrame("Actinium227", "Ac227");
	public static final DictFrame CO60 = new DictFrame("Cobalt60", "Co60");
	public static final DictFrame AU198 = new DictFrame("Gold198", "Au198");
	public static final DictFrame PB209 = new DictFrame("Lead209", "Pb209");
	public static final DictFrame SA326 = new DictFrame("Schrabidium");
	public static final DictFrame SA327 = new DictFrame("Solinium");
	public static final DictFrame SBD = new DictFrame("Schrabidate");
	public static final DictFrame SRN = new DictFrame("Schraranium");
	public static final DictFrame GH336 = new DictFrame("Ghiorsium336", "Gh336");
	public static final DictFrame MUD = new DictFrame("WatzMud");
	/*
	 * STABLE
	 */
	/** TITANIUM */ 
	public static final DictFrame TI = new DictFrame("Titanium");
	/** COPPER */ 
	public static final DictFrame CU = new DictFrame("Copper");
	public static final DictFrame MINGRADE = new DictFrame("Mingrade");
	public static final DictFrame ALLOY = new DictFrame("AdvancedAlloy");
	/** TUNGSTEN */ 
	public static final DictFrame W = new DictFrame("Tungsten");
	/** ALUMINUM */ 
	public static final DictFrame AL = new DictFrame("Aluminum");
	public static final DictFrame STEEL = new DictFrame("Steel");
	/** TECHNETIUM STEEL */ 
	public static final DictFrame TCALLOY = new DictFrame("TcAlloy");
	/** CADMIUM STEEL */
	public static final DictFrame CDALLOY = new DictFrame("CdAlloy");
	/** LEAD */ 
	public static final DictFrame PB = new DictFrame("Lead");
	public static final DictFrame BI = new DictFrame("Bismuth");
	public static final DictFrame AS = new DictFrame("Arsenic");
	public static final DictFrame CA = new DictFrame("Calcium");
	public static final DictFrame CD = new DictFrame("Cadmium");
	/** TANTALUM */ 
	public static final DictFrame TA = new DictFrame("Tantalum");
	public static final DictFrame COLTAN = new DictFrame("Coltan");
	/** NIOBIUM */ 
	public static final DictFrame NB = new DictFrame("Niobium");
	/** BERYLLIUM */ 
	public static final DictFrame BE = new DictFrame("Beryllium");
	/** COBALT */ 
	public static final DictFrame CO = new DictFrame("Cobalt");
	/** BORON */ 
	public static final DictFrame B = new DictFrame("Boron");
	public static final DictFrame GRAPHITE = new DictFrame("Graphite");
	public static final DictFrame DURA = new DictFrame("DuraSteel");
	public static final DictFrame POLYMER = new DictFrame("Polymer");
	public static final DictFrame BAKELITE = new DictFrame("Bakelite");
	public static final DictFrame PET = new DictFrame("PET");
	public static final DictFrame PC = new DictFrame("Polycarbonate");
	public static final DictFrame PVC = new DictFrame("PVC");
	public static final DictFrame RUBBER = new DictFrame("Rubber");
	public static final DictFrame MAGTUNG = new DictFrame("MagnetizedTungsten");
	public static final DictFrame CMB = new DictFrame("CMBSteel");
	public static final DictFrame DESH = new DictFrame("WorkersAlloy");
	public static final DictFrame STAR = new DictFrame("Starmetal");
	public static final DictFrame BIGMT = new DictFrame("Saturnite");
	public static final DictFrame FERRO = new DictFrame("Ferrouranium");
	public static final DictFrame EUPH = new DictFrame("Euphemium");
	public static final DictFrame DNT = new DictFrame("Dineutronium");
	public static final DictFrame FIBER = new DictFrame("Fiberglass");
	public static final DictFrame ASBESTOS = new DictFrame("Asbestos");
	public static final DictFrame OSMIRIDIUM = new DictFrame("Osmiridium");
	/*
	 * DUST AND GEM ORES
	 */
	/** SULFUR */ 
	public static final DictFrame S = new DictFrame("Sulfur");
	/** SALTPETER/NITER */ 
	public static final DictFrame KNO = new DictFrame("Saltpeter");
	/** FLUORITE */ 
	public static final DictFrame F = new DictFrame("Fluorite");
	public static final DictFrame LIGNITE = new DictFrame("Lignite");
	public static final DictFrame COALCOKE = new DictFrame("CoalCoke");
	public static final DictFrame PETCOKE = new DictFrame("PetCoke");
	public static final DictFrame LIGCOKE = new DictFrame("LigniteCoke");
	public static final DictFrame CINNABAR = new DictFrame("Cinnabar");
	public static final DictFrame BORAX = new DictFrame("Borax");
	public static final DictFrame CHLOROCALCITE = new DictFrame("Chlorocalcite");
	public static final DictFrame SODALITE = new DictFrame("Sodalite");
	public static final DictFrame VOLCANIC = new DictFrame("Volcanic");
	public static final DictFrame HEMATITE = new DictFrame("Hematite");
	public static final DictFrame MALACHITE = new DictFrame("Malachite");
	public static final DictFrame SLAG = new DictFrame("Slag");
	/*
	 * HAZARDS, MISC
	 */
	/** LITHIUM */ 
	public static final DictFrame LI = new DictFrame("Lithium");
	/** SODIUM */
	public static final DictFrame NA = new DictFrame("Sodium");
	/*
	 * PHOSPHORUS
	 */
	public static final DictFrame P_WHITE = new DictFrame("WhitePhosphorus");
	public static final DictFrame P_RED = new DictFrame("RedPhosphorus");
	/*
	 * RARE METALS
	 */
	public static final DictFrame AUSTRALIUM = new DictFrame("Australium");
	public static final DictFrame REIIUM = new DictFrame("Reiium");
	public static final DictFrame WEIDANIUM = new DictFrame("Weidanium");
	public static final DictFrame UNOBTAINIUM = new DictFrame("Unobtainium");
	public static final DictFrame VERTICIUM = new DictFrame("Verticium");
	public static final DictFrame DAFFERGON = new DictFrame("Daffergon");
	/*
	 * RARE EARTHS
	 */
	/** LANTHANUM */ 
	public static final DictFrame LA = new DictFrame("Lanthanum");
	/** ZIRCONIUM */ 
	public static final DictFrame ZR = new DictFrame("Zirconium");
	/** NEODYMIUM */ 
	public static final DictFrame ND = new DictFrame("Neodymium");
	/** CERIUM */ 
	public static final DictFrame CE = new DictFrame("Cerium");
	/*
	 * NITAN
	 */
	/** IODINE */ 
	public static final DictFrame I = new DictFrame("Iodine");
	/** ASTATINE */ 
	public static final DictFrame AT = new DictFrame("Astatine");
	/** CAESIUM */ 
	public static final DictFrame CS = new DictFrame("Caesium");
	/** STRONTIUM */ 
	public static final DictFrame ST = new DictFrame("Strontium");
	/** BROMINE */ 
	public static final DictFrame BR = new DictFrame("Bromine");
	/** TENNESSINE */ 
	public static final DictFrame TS = new DictFrame("Tennessine") ;
	/*
	 * FISSION FRAGMENTS
	 */
	public static final DictFrame SR90 = new DictFrame("Strontium90", "Sr90");
	public static final DictFrame I131 = new DictFrame("Iodine131", "I131");
	public static final DictFrame XE135 = new DictFrame("Xenon135", "Xe135");
	public static final DictFrame CS137 = new DictFrame("Caesium137", "Cs137");
	public static final DictFrame AT209 = new DictFrame("Astatine209", "At209");
	
	/*
	 * COLLECTIONS
	 */
	/** Any post oil polymer like teflon ("polymer") or bakelite */
	public static final DictGroup ANY_PLASTIC = new DictGroup("AnyPlastic", OreDictManager.POLYMER, OreDictManager.BAKELITE);		//using the Any prefix means that it's just the secondary prefix, and that shape prefixes are applicable
	/** Any post vacuum polymer like PET or PVC */
	public static final DictGroup ANY_HARDPLASTIC = new DictGroup("AnyHardPlastic", OreDictManager.PC, OreDictManager.PVC);
	/** Any post nuclear steel like TCA or CDA */
	public static final DictGroup ANY_RESISTANTALLOY = new DictGroup("AnyResistantAlloy", OreDictManager.TCALLOY, OreDictManager.CDALLOY);
	/** Any "powder" propellant like gunpowder, ballistite and cordite */
	public static final DictFrame ANY_GUNPOWDER = new DictFrame("AnyPropellant");
	/** Any smokeless powder like ballistite and cordite */
	public static final DictFrame ANY_SMOKELESS = new DictFrame("AnySmokeless");
	/** Any plastic explosive like semtex H or C-4 */
	public static final DictFrame ANY_PLASTICEXPLOSIVE = new DictFrame("AnyPlasticexplosive");
	/** Any higher tier high explosive (therefore excluding dynamite) like TNT */
	public static final DictFrame ANY_HIGHEXPLOSIVE = new DictFrame("AnyHighexplosive");
	public static final DictFrame ANY_COKE = new DictFrame("AnyCoke", "Coke");
	public static final DictFrame ANY_CONCRETE = new DictFrame("Concrete");			//no any prefix means that any has to be appended with the any() or anys() getters, registering works with the any (i.e. no shape) setter
	public static final DictGroup ANY_TAR = new DictGroup("Tar", OreDictManager.KEY_OIL_TAR, OreDictManager.KEY_COAL_TAR, OreDictManager.KEY_CRACK_TAR, OreDictManager.KEY_WOOD_TAR);
	/** Any special post-RBMK gating material, namely bismuth and arsenic */
	public static final DictFrame ANY_BISMOID = new DictFrame("AnyBismoid");
	public static final DictFrame ANY_ASH = new DictFrame("Ash");
	
	public static void registerOres() {

		/*
		 * VANILLA
		 */
		OreDictManager.COAL.gem(Items.coal).dustSmall(ModItems.powder_coal_tiny).dust(ModItems.powder_coal);
		OreDictManager.IRON.plate(ModItems.plate_iron).dust(ModItems.powder_iron).ore(ModBlocks.ore_gneiss_iron);
		OreDictManager.GOLD.plate(ModItems.plate_gold).dust(ModItems.powder_gold).ore(ModBlocks.ore_gneiss_gold);
		OreDictManager.LAPIS.dust(ModItems.powder_lapis);
		OreDictManager.NETHERQUARTZ.gem(Items.quartz).dust(ModItems.powder_quartz).ore(Blocks.quartz_ore);
		OreDictManager.DIAMOND.dust(ModItems.powder_diamond).ore(ModBlocks.gravel_diamond);
		OreDictManager.EMERALD.dust(ModItems.powder_emerald);
		
		/*
		 * RADIOACTIVE
		 */
		OreDictManager.U		.rad(HazardRegistry.u)								.nugget(ModItems.nugget_uranium)		.billet(ModItems.billet_uranium)		.ingot(ModItems.ingot_uranium)		.dust(ModItems.powder_uranium)									.block(ModBlocks.block_uranium)		.ore(ModBlocks.ore_uranium, ModBlocks.ore_uranium_scorched, ModBlocks.ore_gneiss_uranium, ModBlocks.ore_gneiss_uranium_scorched, ModBlocks.ore_nether_uranium, ModBlocks.ore_nether_uranium_scorched, ModBlocks.ore_meteor_uranium)	.oreNether(ModBlocks.ore_nether_uranium, ModBlocks.ore_nether_uranium_scorched);
		OreDictManager.U233	.rad(HazardRegistry.u233)							.nugget(ModItems.nugget_u233)		.billet(ModItems.billet_u233)		.ingot(ModItems.ingot_u233)																	.block(ModBlocks.block_u233);
		OreDictManager.U235	.rad(HazardRegistry.u235)							.nugget(ModItems.nugget_u235)		.billet(ModItems.billet_u235)		.ingot(ModItems.ingot_u235)																	.block(ModBlocks.block_u235);
		OreDictManager.U238	.rad(HazardRegistry.u238)							.nugget(ModItems.nugget_u238)		.billet(ModItems.billet_u238)		.ingot(ModItems.ingot_u238)																	.block(ModBlocks.block_u238);
		OreDictManager.TH232	.rad(HazardRegistry.th232)							.nugget(ModItems.nugget_th232)		.billet(ModItems.billet_th232)		.ingot(ModItems.ingot_th232)			.dust(ModItems.powder_thorium)									.block(ModBlocks.block_thorium)		.ore(ModBlocks.ore_thorium, ModBlocks.ore_meteor_thorium);
		OreDictManager.PU		.rad(HazardRegistry.pu)								.nugget(ModItems.nugget_plutonium)	.billet(ModItems.billet_plutonium)	.ingot(ModItems.ingot_plutonium)		.dust(ModItems.powder_plutonium)									.block(ModBlocks.block_plutonium)		.ore(ModBlocks.ore_nether_plutonium)	.oreNether(ModBlocks.ore_nether_plutonium);
		OreDictManager.PURG	.rad(HazardRegistry.purg)							.nugget(ModItems.nugget_pu_mix)		.billet(ModItems.billet_pu_mix)		.ingot(ModItems.ingot_pu_mix)																.block(ModBlocks.block_pu_mix);
		OreDictManager.PU238	.rad(HazardRegistry.pu238)	.hot(3F)				.nugget(ModItems.nugget_pu238)		.billet(ModItems.billet_pu238)		.ingot(ModItems.ingot_pu238)																	.block(ModBlocks.block_pu238);
		OreDictManager.PU239	.rad(HazardRegistry.pu239)							.nugget(ModItems.nugget_pu239)		.billet(ModItems.billet_pu239)		.ingot(ModItems.ingot_pu239)																	.block(ModBlocks.block_pu239);
		OreDictManager.PU240	.rad(HazardRegistry.pu240)							.nugget(ModItems.nugget_pu240)		.billet(ModItems.billet_pu240)		.ingot(ModItems.ingot_pu240)																	.block(ModBlocks.block_pu240);
		OreDictManager.PU241	.rad(HazardRegistry.pu241)							.nugget(ModItems.nugget_pu241)		.billet(ModItems.billet_pu241)		.ingot(ModItems.ingot_pu241);																//.block(block_pu241);
		OreDictManager.AM241	.rad(HazardRegistry.am241)							.nugget(ModItems.nugget_am241)		.billet(ModItems.billet_am241)		.ingot(ModItems.ingot_am241);
		OreDictManager.AM242	.rad(HazardRegistry.am242)							.nugget(ModItems.nugget_am242)		.billet(ModItems.billet_am242)		.ingot(ModItems.ingot_am242);
		OreDictManager.AMRG	.rad(HazardRegistry.amrg)							.nugget(ModItems.nugget_am_mix)		.billet(ModItems.billet_am_mix)		.ingot(ModItems.ingot_am_mix);
		OreDictManager.NP237	.rad(HazardRegistry.np237)							.nugget(ModItems.nugget_neptunium)	.billet(ModItems.billet_neptunium)	.ingot(ModItems.ingot_neptunium)		.dust(ModItems.powder_neptunium)									.block(ModBlocks.block_neptunium);
		OreDictManager.PO210	.rad(HazardRegistry.po210)	.hot(3)					.nugget(ModItems.nugget_polonium)	.billet(ModItems.billet_polonium)	.ingot(ModItems.ingot_polonium)		.dust(ModItems.powder_polonium)									.block(ModBlocks.block_polonium);
		OreDictManager.TC99	.rad(HazardRegistry.tc99)							.nugget(ModItems.nugget_technetium)	.billet(ModItems.billet_technetium)	.ingot(ModItems.ingot_technetium);
		OreDictManager.RA226	.rad(HazardRegistry.ra226)							.nugget(ModItems.nugget_ra226)		.billet(ModItems.billet_ra226)		.ingot(ModItems.ingot_ra226)			.dust(ModItems.powder_ra226)										.block(ModBlocks.block_ra226);
		OreDictManager.AC227	.rad(HazardRegistry.ac227)							.nugget(ModItems.nugget_actinium)	.billet(ModItems.billet_actinium)	.ingot(ModItems.ingot_actinium)		.dust(ModItems.powder_actinium)									.block(ModBlocks.block_actinium)		.dustSmall(ModItems.powder_actinium_tiny);
		OreDictManager.CO60	.rad(HazardRegistry.co60)	.hot(1)					.nugget(ModItems.nugget_co60)		.billet(ModItems.billet_co60)		.ingot(ModItems.ingot_co60)			.dust(ModItems.powder_co60);
		OreDictManager.AU198	.rad(HazardRegistry.au198)	.hot(5)					.nugget(ModItems.nugget_au198)		.billet(ModItems.billet_au198)		.ingot(ModItems.ingot_au198)			.dust(ModItems.powder_au198);
		OreDictManager.PB209	.rad(HazardRegistry.pb209)	.blinding(50F)	.hot(7)	.nugget(ModItems.nugget_pb209)		.billet(ModItems.billet_pb209)		.ingot(ModItems.ingot_pb209);
		OreDictManager.SA326	.rad(HazardRegistry.sa326)	.blinding(50F)			.nugget(ModItems.nugget_schrabidium)	.billet(ModItems.billet_schrabidium)	.ingot(ModItems.ingot_schrabidium)	.dust(ModItems.powder_schrabidium).plate(ModItems.plate_schrabidium).plateCast(Mats.MAT_SCHRABIDIUM.make(ModItems.plate_cast)).block(ModBlocks.block_schrabidium).ore(ModBlocks.ore_schrabidium, ModBlocks.ore_gneiss_schrabidium, ModBlocks.ore_nether_schrabidium)	.oreNether(ModBlocks.ore_nether_schrabidium);
		OreDictManager.SA327	.rad(HazardRegistry.sa327)	.blinding(50F)			.nugget(ModItems.nugget_solinium)	.billet(ModItems.billet_solinium)	.ingot(ModItems.ingot_solinium)																.block(ModBlocks.block_solinium);
		OreDictManager.SBD		.rad(HazardRegistry.sb)		.blinding(50F)																	.ingot(ModItems.ingot_schrabidate)	.dust(ModItems.powder_schrabidate)								.block(ModBlocks.block_schrabidate);
		OreDictManager.SRN		.rad(HazardRegistry.sr)		.blinding(50F)																	.ingot(ModItems.ingot_schraranium)															.block(ModBlocks.block_schraranium);
		OreDictManager.GH336	.rad(HazardRegistry.gh336)							.nugget(ModItems.nugget_gh336)		.billet(ModItems.billet_gh336)		.ingot(ModItems.ingot_gh336);
		OreDictManager.MUD		.rad(HazardRegistry.mud)																					.ingot(ModItems.ingot_mud);
		
		/*
		 * STABLE
		 */
		OreDictManager.TI																	.ingot(ModItems.ingot_titanium)												.dust(ModItems.powder_titanium)			.plate(ModItems.plate_titanium)			.block(ModBlocks.block_titanium)		.ore(ModBlocks.ore_titanium, ModBlocks.ore_meteor_titanium);
		OreDictManager.CU																	.ingot(ModItems.ingot_copper)												.dust(ModItems.powder_copper)			.plate(ModItems.plate_copper)			.block(ModBlocks.block_copper)		.ore(ModBlocks.ore_copper, ModBlocks.ore_gneiss_copper, ModBlocks.ore_meteor_copper);
		OreDictManager.MINGRADE															.ingot(ModItems.ingot_red_copper)											.dust(ModItems.powder_red_copper)										.block(ModBlocks.block_red_copper);
		OreDictManager.ALLOY																.ingot(ModItems.ingot_advanced_alloy)										.dust(ModItems.powder_advanced_alloy)	.plate(ModItems.plate_advanced_alloy)	.block(ModBlocks.block_advanced_alloy);
		OreDictManager.W																	.ingot(ModItems.ingot_tungsten)												.dust(ModItems.powder_tungsten)											.block(ModBlocks.block_tungsten)		.ore(ModBlocks.ore_tungsten, ModBlocks.ore_nether_tungsten, ModBlocks.ore_meteor_tungsten)	.oreNether(ModBlocks.ore_nether_tungsten);
		OreDictManager.AL																	.ingot(ModItems.ingot_aluminium)												.dust(ModItems.powder_aluminium)			.plate(ModItems.plate_aluminium)			.block(ModBlocks.block_aluminium)		.ore(ModBlocks.ore_aluminium, ModBlocks.ore_meteor_aluminium);
		OreDictManager.STEEL																.ingot(ModItems.ingot_steel)				.dustSmall(ModItems.powder_steel_tiny)		.dust(ModItems.powder_steel)				.plate(ModItems.plate_steel)				.block(ModBlocks.block_steel);
		OreDictManager.TCALLOY																.ingot(ModItems.ingot_tcalloy)												.dust(ModItems.powder_tcalloy)											.block(ModBlocks.block_tcalloy);
		OreDictManager.CDALLOY																.ingot(ModItems.ingot_cdalloy)																												.block(ModBlocks.block_cdalloy);
		OreDictManager.PB			.nugget(ModItems.nugget_lead)									.ingot(ModItems.ingot_lead)													.dust(ModItems.powder_lead)				.plate(ModItems.plate_lead)				.block(ModBlocks.block_lead)			.ore(ModBlocks.ore_lead, ModBlocks.ore_meteor_lead);
		OreDictManager.BI			.nugget(ModItems.nugget_bismuth)									.ingot(ModItems.ingot_bismuth)												.dust(ModItems.powder_bismuth);
		OreDictManager.AS			.nugget(ModItems.nugget_arsenic)									.ingot(ModItems.ingot_arsenic);
		OreDictManager.CA																	.ingot(ModItems.ingot_calcium)												.dust(ModItems.powder_calcium);
		OreDictManager.CD																	.ingot(ModItems.ingot_cadmium)												.dust(ModItems.powder_cadmium)											.block(ModBlocks.block_cadmium);
		OreDictManager.TA			.nugget(ModItems.nugget_tantalium)	.gem(ModItems.gem_tantalium)			.ingot(ModItems.ingot_tantalium)												.dust(ModItems.powder_tantalium)											.block(ModBlocks.block_tantalium);
		OreDictManager.COLTAN																.ingot(ModItems.fragment_coltan)												.dust(ModItems.powder_coltan_ore)										.block(ModBlocks.block_coltan)		.ore(ModBlocks.ore_coltan);
		OreDictManager.NB			.nugget(ModItems.fragment_niobium)								.ingot(ModItems.ingot_niobium)			.dustSmall(ModItems.powder_niobium_tiny)		.dust(ModItems.powder_niobium)											.block(ModBlocks.block_niobium);
		OreDictManager.BE			.nugget(ModItems.nugget_beryllium)	.billet(ModItems.billet_beryllium)	.ingot(ModItems.ingot_beryllium)												.dust(ModItems.powder_beryllium)											.block(ModBlocks.block_beryllium)		.ore(ModBlocks.ore_beryllium);
		OreDictManager.CO			.nugget(ModItems.fragment_cobalt)	.nugget(ModItems.nugget_cobalt)		.billet(ModItems.billet_cobalt)			.ingot(ModItems.ingot_cobalt)				.dust(ModItems.powder_cobalt)			.dustSmall(ModItems.powder_cobalt_tiny)	.block(ModBlocks.block_cobalt)		.ore(ModBlocks.ore_cobalt, ModBlocks.ore_nether_cobalt);
		OreDictManager.B			.nugget(ModItems.fragment_boron)									.ingot(ModItems.ingot_boron)				.dustSmall(ModItems.powder_boron_tiny)		.dust(ModItems.powder_boron)												.block(ModBlocks.block_boron);
		OreDictManager.GRAPHITE															.ingot(ModItems.ingot_graphite)																												.block(ModBlocks.block_graphite);
		OreDictManager.DURA																.ingot(ModItems.ingot_dura_steel)											.dust(ModItems.powder_dura_steel)										.block(ModBlocks.block_dura_steel);
		OreDictManager.POLYMER																.ingot(ModItems.ingot_polymer)												.dust(ModItems.powder_polymer)											.block(ModBlocks.block_polymer);
		OreDictManager.BAKELITE															.ingot(ModItems.ingot_bakelite)												.dust(ModItems.powder_bakelite)											.block(ModBlocks.block_bakelite);
		OreDictManager.RUBBER																.ingot(ModItems.ingot_rubber)																												.block(ModBlocks.block_rubber);
		//PET																	.ingot(ingot_pet);
		OreDictManager.PC																	.ingot(ModItems.ingot_pc);
		OreDictManager.PVC																	.ingot(ModItems.ingot_pvc);
		OreDictManager.MAGTUNG																.ingot(ModItems.ingot_magnetized_tungsten)									.dust(ModItems.powder_magnetized_tungsten)								.block(ModBlocks.block_magnetized_tungsten);
		OreDictManager.CMB																	.ingot(ModItems.ingot_combine_steel)											.dust(ModItems.powder_combine_steel)		.plate(ModItems.plate_combine_steel)		.block(ModBlocks.block_combine_steel);
		OreDictManager.DESH		.nugget(ModItems.nugget_desh)									.ingot(ModItems.ingot_desh)													.dust(ModItems.powder_desh)												.block(ModBlocks.block_desh);
		OreDictManager.STAR																.ingot(ModItems.ingot_starmetal)																												.block(ModBlocks.block_starmetal)		.ore(ModBlocks.ore_meteor_starmetal);
		OreDictManager.BIGMT																.ingot(ModItems.ingot_saturnite)																				.plate(ModItems.plate_saturnite);
		OreDictManager.FERRO																.ingot(ModItems.ingot_ferrouranium);
		OreDictManager.EUPH		.nugget(ModItems.nugget_euphemium)								.ingot(ModItems.ingot_euphemium)												.dust(ModItems.powder_euphemium)											.block(ModBlocks.block_euphemium);
		OreDictManager.DNT			.nugget(ModItems.nugget_dineutronium)							.ingot(ModItems.ingot_dineutronium)											.dust(ModItems.powder_dineutronium)										.block(ModBlocks.block_dineutronium);
		OreDictManager.FIBER																.ingot(ModItems.ingot_fiberglass)																											.block(ModBlocks.block_fiberglass);
		OreDictManager.ASBESTOS	.asbestos(1F)											.ingot(ModItems.ingot_asbestos)												.dust(ModItems.powder_asbestos)											.block(ModBlocks.block_asbestos)		.ore(ModBlocks.ore_asbestos, ModBlocks.ore_gneiss_asbestos, ModBlocks.basalt_asbestos, DictFrame.fromOne(ModBlocks.stone_resource, EnumStoneType.ASBESTOS));
		OreDictManager.OSMIRIDIUM	.nugget(ModItems.nugget_osmiridium)								.ingot(ModItems.ingot_osmiridium);

		/*
		 * DUST AND GEM ORES
		 */
		OreDictManager.S																				.dust(ModItems.sulfur)			.block(ModBlocks.block_sulfur)	.ore(ModBlocks.ore_sulfur, ModBlocks.ore_nether_sulfur, ModBlocks.basalt_sulfur, ModBlocks.ore_meteor_sulfur, DictFrame.fromOne(ModBlocks.stone_resource, EnumStoneType.SULFUR))	.oreNether(ModBlocks.ore_nether_sulfur);
		OreDictManager.KNO																				.dust(ModItems.niter)			.block(ModBlocks.block_niter)		.ore(ModBlocks.ore_niter);
		OreDictManager.F																				.dust(ModItems.fluorite)			.block(ModBlocks.block_fluorite)	.ore(ModBlocks.ore_fluorite, ModBlocks.basalt_fluorite);
		OreDictManager.LIGNITE							.gem(ModItems.lignite)									.dust(ModItems.powder_lignite)							.ore(ModBlocks.ore_lignite);
		OreDictManager.COALCOKE						.gem(DictFrame.fromOne(ModItems.coke, EnumCokeType.COAL))									.block(DictFrame.fromOne(ModBlocks.block_coke, EnumCokeType.COAL));
		OreDictManager.PETCOKE							.gem(DictFrame.fromOne(ModItems.coke, EnumCokeType.PETROLEUM))								.block(DictFrame.fromOne(ModBlocks.block_coke, EnumCokeType.PETROLEUM));
		OreDictManager.LIGCOKE							.gem(DictFrame.fromOne(ModItems.coke, EnumCokeType.LIGNITE))								.block(DictFrame.fromOne(ModBlocks.block_coke, EnumCokeType.LIGNITE));
		OreDictManager.CINNABAR	.crystal(ModItems.cinnebar)	.gem(ModItems.cinnebar)																					.ore(ModBlocks.ore_cinnebar, ModBlocks.ore_depth_cinnebar);
		OreDictManager.BORAX																			.dust(ModItems.powder_borax)								.ore(ModBlocks.ore_depth_borax);
		OreDictManager.CHLOROCALCITE																	.dust(ModItems.powder_chlorocalcite);
		OreDictManager.SODALITE						.gem(ModItems.gem_sodalite);
		OreDictManager.VOLCANIC						.gem(ModItems.gem_volcanic)																				.ore(ModBlocks.basalt_gem);
		OreDictManager.HEMATITE																														.ore(DictFrame.fromOne(ModBlocks.stone_resource, EnumStoneType.HEMATITE));
		OreDictManager.MALACHITE																														.ore(DictFrame.fromOne(ModBlocks.stone_resource, EnumStoneType.MALACHITE));
		OreDictManager.SLAG																									.block(ModBlocks.block_slag);
		
		/*
		 * HAZARDS, MISC
		 */
		OreDictManager.LI	.hydro(1F)	.ingot(ModItems.lithium)	.dustSmall(ModItems.powder_lithium_tiny)	.dust(ModItems.powder_lithium)	.block(ModBlocks.block_lithium)	.ore(ModBlocks.ore_gneiss_lithium, ModBlocks.ore_meteor_lithium);
		OreDictManager.NA	.hydro(1F)													.dust(ModItems.powder_sodium);

		/*
		 * PHOSPHORUS
		 */
		OreDictManager.P_WHITE	.hot(5)	.ingot(ModItems.ingot_phosphorus)	.block(ModBlocks.block_white_phosphorus);
		OreDictManager.P_RED	.hot(2)	.dust(ModItems.powder_fire)			.block(ModBlocks.block_red_phosphorus);
		
		/*
		 * RARE METALS
		 */
		OreDictManager.AUSTRALIUM	.nugget(ModItems.nugget_australium)	.billet(ModItems.billet_australium)	.ingot(ModItems.ingot_australium)	.dust(ModItems.powder_australium)	.block(ModBlocks.block_australium)	.ore(ModBlocks.ore_australium);
		OreDictManager.REIIUM		.nugget(ModItems.nugget_reiium)									.ingot(ModItems.ingot_reiium)		.dust(ModItems.powder_reiium)		.block(ModBlocks.block_reiium)		.ore(ModBlocks.ore_reiium);
		OreDictManager.WEIDANIUM	.nugget(ModItems.nugget_weidanium)								.ingot(ModItems.ingot_weidanium)		.dust(ModItems.powder_weidanium)		.block(ModBlocks.block_weidanium)		.ore(ModBlocks.ore_weidanium);
		OreDictManager.UNOBTAINIUM	.nugget(ModItems.nugget_unobtainium)								.ingot(ModItems.ingot_unobtainium)	.dust(ModItems.powder_unobtainium)	.block(ModBlocks.block_unobtainium)	.ore(ModBlocks.ore_unobtainium);
		OreDictManager.VERTICIUM	.nugget(ModItems.nugget_verticium)								.ingot(ModItems.ingot_verticium)		.dust(ModItems.powder_verticium)		.block(ModBlocks.block_verticium)		.ore(ModBlocks.ore_verticium);
		OreDictManager.DAFFERGON	.nugget(ModItems.nugget_daffergon)								.ingot(ModItems.ingot_daffergon)		.dust(ModItems.powder_daffergon)		.block(ModBlocks.block_daffergon)		.ore(ModBlocks.ore_daffergon);

		/*
		 * RARE EARTHS
		 */
		OreDictManager.LA	.nugget(ModItems.fragment_lanthanium)	.ingot(ModItems.ingot_lanthanium)										.dustSmall(ModItems.powder_lanthanium_tiny)	.dust(ModItems.powder_lanthanium)	.block(ModBlocks.block_lanthanium);
		OreDictManager.ZR	.nugget(ModItems.nugget_zirconium)		.ingot(ModItems.ingot_zirconium)		.billet(ModItems.billet_zirconium)												.dust(ModItems.powder_zirconium)		.block(ModBlocks.block_zirconium)		.ore(ModBlocks.ore_depth_zirconium);
		OreDictManager.ND	.nugget(ModItems.fragment_neodymium)																		.dustSmall(ModItems.powder_neodymium_tiny)	.dust(ModItems.powder_neodymium)									.ore(ModBlocks.ore_depth_nether_neodymium)	.oreNether(ModBlocks.ore_depth_nether_neodymium);
		OreDictManager.CE	.nugget(ModItems.fragment_cerium)																		.dustSmall(ModItems.powder_cerium_tiny)		.dust(ModItems.powder_cerium);
		
		/*
		 * NITAN
		 */
		OreDictManager.I	.dust(ModItems.powder_iodine);
		OreDictManager.AT	.dust(ModItems.powder_astatine);
		OreDictManager.CS	.dust(ModItems.powder_caesium);
		OreDictManager.ST	.dust(ModItems.powder_strontium);
		OreDictManager.BR	.dust(ModItems.powder_bromine);
		OreDictManager.TS	.dust(ModItems.powder_tennessine);

		/*
		 * FISSION FRAGMENTS
		 */
		OreDictManager.SR90	.rad(HazardRegistry.sr90)	.hot(1F)	.hydro(1F)	.dustSmall(ModItems.powder_sr90_tiny)	.dust(ModItems.powder_sr90)	.ingot(ModItems.ingot_sr90)	.billet(ModItems.billet_sr90)	.nugget(ModItems.nugget_sr90);
		OreDictManager.I131	.rad(HazardRegistry.i131)	.hot(1F)				.dustSmall(ModItems.powder_i131_tiny)	.dust(ModItems.powder_i131);
		OreDictManager.XE135	.rad(HazardRegistry.xe135)	.hot(10F)				.dustSmall(ModItems.powder_xe135_tiny)	.dust(ModItems.powder_xe135);
		OreDictManager.CS137	.rad(HazardRegistry.cs137)	.hot(3F)	.hydro(3F)	.dustSmall(ModItems.powder_cs137_tiny)	.dust(ModItems.powder_cs137);
		OreDictManager.AT209	.rad(HazardRegistry.at209)	.hot(20F)												.dust(ModItems.powder_at209);
		
		/*
		 * COLLECTIONS
		 */
		OreDictManager.ANY_GUNPOWDER			.dust(Items.gunpowder, ModItems.ballistite, ModItems.cordite);
		OreDictManager.ANY_SMOKELESS			.dust(ModItems.ballistite, ModItems.cordite);
		OreDictManager.ANY_PLASTICEXPLOSIVE	.ingot(ModItems.ingot_semtex, ModItems.ingot_c4);
		OreDictManager.ANY_HIGHEXPLOSIVE		.ingot(ModItems.ball_tnt).ingot(ModItems.ball_tatb);
		OreDictManager.ANY_CONCRETE			.any(ModBlocks.concrete, ModBlocks.concrete_smooth, ModBlocks.concrete_asbestos, ModBlocks.ducrete, ModBlocks.ducrete_smooth);
		for(int i = 0; i < 16; i++) { OreDictManager.ANY_CONCRETE.any(new ItemStack(ModBlocks.concrete_colored, 1, i)); }
		for(int i = 0; i < 16; i++) { OreDictManager.ANY_CONCRETE.any(new ItemStack(ModBlocks.concrete_colored_ext, 1, i)); }
		OreDictManager.ANY_COKE				.gem(DictFrame.fromAll(ModItems.coke, EnumCokeType.class)).block(DictFrame.fromAll(ModBlocks.block_coke, EnumCokeType.class));
		OreDictManager.ANY_BISMOID				.ingot(ModItems.ingot_bismuth, ModItems.ingot_arsenic).nugget(ModItems.nugget_bismuth, ModItems.nugget_arsenic).block(ModBlocks.block_bismuth);
		OreDictManager.ANY_ASH					.any(DictFrame.fromOne(ModItems.powder_ash, EnumAshType.WOOD), DictFrame.fromOne(ModItems.powder_ash, EnumAshType.COAL), DictFrame.fromOne(ModItems.powder_ash, EnumAshType.MISC), DictFrame.fromOne(ModItems.powder_ash, EnumAshType.FLY), DictFrame.fromOne(ModItems.powder_ash, EnumAshType.SOOT));

		/*
		 * TAR
		 */
		OreDictionary.registerOre(OreDictManager.KEY_OIL_TAR, DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRUDE));
		OreDictionary.registerOre(OreDictManager.KEY_CRACK_TAR, DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRACK));
		OreDictionary.registerOre(OreDictManager.KEY_COAL_TAR, DictFrame.fromOne(ModItems.oil_tar, EnumTarType.COAL));
		OreDictionary.registerOre(OreDictManager.KEY_WOOD_TAR, DictFrame.fromOne(ModItems.oil_tar, EnumTarType.WOOD));

		/*
		 * TANKS
		 */
		OreDictionary.registerOre(OreDictManager.KEY_UNIVERSAL_TANK, new ItemStack(ModItems.fluid_tank_full, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre(OreDictManager.KEY_HAZARD_TANK, new ItemStack(ModItems.fluid_tank_lead_full, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre(OreDictManager.KEY_UNIVERSAL_BARREL, new ItemStack(ModItems.fluid_barrel_full, 1, OreDictionary.WILDCARD_VALUE));

		/*
		 * TOOLS
		 */
		OreDictionary.registerOre(OreDictManager.KEY_TOOL_SCREWDRIVER, new ItemStack(ModItems.screwdriver, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre(OreDictManager.KEY_TOOL_SCREWDRIVER, new ItemStack(ModItems.screwdriver_desh, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre(OreDictManager.KEY_TOOL_HANDDRILL, new ItemStack(ModItems.hand_drill, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre(OreDictManager.KEY_TOOL_HANDDRILL, new ItemStack(ModItems.hand_drill_desh, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre(OreDictManager.KEY_TOOL_CHEMISTRYSET, new ItemStack(ModItems.chemistry_set, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre(OreDictManager.KEY_TOOL_CHEMISTRYSET, new ItemStack(ModItems.chemistry_set_boron, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre(OreDictManager.KEY_TOOL_TORCH, new ItemStack(ModItems.blowtorch, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre(OreDictManager.KEY_TOOL_TORCH, new ItemStack(ModItems.acetylene_torch, 1, OreDictionary.WILDCARD_VALUE));

		/*
		 * CIRCUITS
		 */
		OreDictionary.registerOre(OreDictManager.KEY_CIRCUIT_BISMUTH, ModItems.circuit_bismuth);
		OreDictionary.registerOre(OreDictManager.KEY_CIRCUIT_BISMUTH, ModItems.circuit_arsenic);
		
		for(NTMMaterial mat : Mats.orderedList) {
			if(mat.smeltable == SmeltingBehavior.SMELTABLE) {
				if(mat.shapes.contains(MaterialShapes.CASTPLATE)) for(String name : mat.names) OreDictionary.registerOre(MaterialShapes.CASTPLATE.name() + name, new ItemStack(ModItems.plate_cast, 1, mat.id));
				if(mat.shapes.contains(MaterialShapes.WELDEDPLATE)) for(String name : mat.names) OreDictionary.registerOre(MaterialShapes.WELDEDPLATE.name() + name, new ItemStack(ModItems.plate_welded, 1, mat.id));
				if(mat.shapes.contains(MaterialShapes.HEAVY_COMPONENT)) for(String name : mat.names) OreDictionary.registerOre(MaterialShapes.HEAVY_COMPONENT.name() + name, new ItemStack(ModItems.heavy_component, 1, mat.id));
				if(mat.shapes.contains(MaterialShapes.DENSEWIRE)) for(String name : mat.names) OreDictionary.registerOre(MaterialShapes.DENSEWIRE.name() + name, new ItemStack(ModItems.wire_dense, 1, mat.id));
			}
		}
		
		for(EnumBedrockOre ore : EnumBedrockOre.values()) {
			OreDictionary.registerOre("ore" + ore.oreName, new ItemStack(ModItems.ore_enriched, 1, ore.ordinal()));
		}

		OreDictionary.registerOre("itemRubber", ModItems.ingot_rubber);

		OreDictionary.registerOre("coalCoke", DictFrame.fromOne(ModItems.coke, EnumCokeType.COAL));
		
		for(String name : new String[] {"fuelCoke", "coke"}) {
			OreDictionary.registerOre(name, DictFrame.fromOne(ModItems.coke, EnumCokeType.COAL));
			OreDictionary.registerOre(name, DictFrame.fromOne(ModItems.coke, EnumCokeType.LIGNITE));
			OreDictionary.registerOre(name, DictFrame.fromOne(ModItems.coke, EnumCokeType.PETROLEUM));
		}
		
		OreDictionary.registerOre("briquetteCoal", DictFrame.fromOne(ModItems.briquette, EnumBriquetteType.COAL));
		OreDictionary.registerOre("briquetteLignite", DictFrame.fromOne(ModItems.briquette, EnumBriquetteType.LIGNITE));
		OreDictionary.registerOre("briquetteWood", DictFrame.fromOne(ModItems.briquette, EnumBriquetteType.WOOD));
		
		OreDictionary.registerOre(OreDictManager.getReflector(), ModItems.neutron_reflector);
		OreDictionary.registerOre("oreRareEarth", ModBlocks.ore_rare);
		OreDictionary.registerOre("oreRareEarth", ModBlocks.ore_gneiss_rare);

		OreDictionary.registerOre("logWood", ModBlocks.pink_log);
		OreDictionary.registerOre("logWoodPink", ModBlocks.pink_log);
		OreDictionary.registerOre("plankWood", ModBlocks.pink_planks);
		OreDictionary.registerOre("plankWoodPink", ModBlocks.pink_planks);
		OreDictionary.registerOre("slabWood", ModBlocks.pink_slab);
		OreDictionary.registerOre("slabWoodPink", ModBlocks.pink_slab);
		OreDictionary.registerOre("stairWood", ModBlocks.pink_stairs);
		OreDictionary.registerOre("stairWoodPink", ModBlocks.pink_stairs);
		
		String[] dyes = { "Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "LightGray", "Gray", "Pink", "Lime", "Yellow", "LightBlue", "Magenta", "Orange", "White" };
		for(int i = 0; i < 16; i++) {
			String dyeName = "dye" + dyes[i];
			
			OreDictionary.registerOre(dyeName, new ItemStack(ModItems.chemical_dye, 1, i));
			
			OreDictionary.registerOre(dyeName, new ItemStack(ModItems.crayon, 1, i));
		}
		OreDictionary.registerOre("dye", new ItemStack(ModItems.chemical_dye, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("dye", new ItemStack(ModItems.crayon, 1, OreDictionary.WILDCARD_VALUE));

		OreDictionary.registerOre("dyeRed", ModItems.cinnebar);
		OreDictionary.registerOre("dye", ModItems.cinnebar);
		OreDictionary.registerOre("dyeYellow", ModItems.sulfur);
		OreDictionary.registerOre("dye", ModItems.sulfur);
		OreDictionary.registerOre("dyeBlack", ModItems.powder_coal);
		OreDictionary.registerOre("dye", ModItems.powder_coal);
		OreDictionary.registerOre("dyeBrown", ModItems.powder_lignite);
		OreDictionary.registerOre("dye", ModItems.powder_lignite);
		OreDictionary.registerOre("dyeLightGray", ModItems.powder_titanium);
		OreDictionary.registerOre("dye", ModItems.powder_titanium);
		OreDictionary.registerOre("dyeWhite", ModItems.fluorite);
		OreDictionary.registerOre("dye", ModItems.fluorite);
		OreDictionary.registerOre("dyeBlue", ModItems.powder_lapis);
		OreDictionary.registerOre("dye", ModItems.powder_lapis);
		OreDictionary.registerOre("dyeBlack", DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRUDE));
		OreDictionary.registerOre("dyeBlack", DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRACK));
		OreDictionary.registerOre("dyeGray", DictFrame.fromOne(ModItems.oil_tar, EnumTarType.COAL));
		OreDictionary.registerOre("dyeBrown", DictFrame.fromOne(ModItems.oil_tar, EnumTarType.WOOD));
		OreDictionary.registerOre("dyeCyan", DictFrame.fromOne(ModItems.oil_tar, EnumTarType.WAX));
		OreDictionary.registerOre("dyeWhite", DictFrame.fromOne(ModItems.oil_tar, EnumTarType.PARAFFIN));
		OreDictionary.registerOre("dye", new ItemStack(ModItems.oil_tar, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("dyeOrange", ModItems.powder_cadmium);
		OreDictionary.registerOre("dye", ModItems.powder_cadmium);
		OreDictionary.registerOre("dyeLightGray", DictFrame.fromOne(ModItems.powder_ash, EnumAshType.WOOD));
		OreDictionary.registerOre("dyeBlack", DictFrame.fromOne(ModItems.powder_ash, EnumAshType.COAL));
		OreDictionary.registerOre("dyeGray", DictFrame.fromOne(ModItems.powder_ash, EnumAshType.MISC));
		OreDictionary.registerOre("dyeBrown", DictFrame.fromOne(ModItems.powder_ash, EnumAshType.FLY));
		OreDictionary.registerOre("dyeBlack", DictFrame.fromOne(ModItems.powder_ash, EnumAshType.SOOT));
		OreDictionary.registerOre("dyeMagenta", DictFrame.fromOne(ModItems.powder_ash, EnumAshType.FULLERENE));
		OreDictionary.registerOre("dye", new ItemStack(ModItems.powder_ash, 1, OreDictionary.WILDCARD_VALUE));

		OreDictionary.registerOre("blockGlass", ModBlocks.glass_boron);
		OreDictionary.registerOre("blockGlass", ModBlocks.glass_lead);
		OreDictionary.registerOre("blockGlass", ModBlocks.glass_uranium);
		OreDictionary.registerOre("blockGlass", ModBlocks.glass_trinitite);
		OreDictionary.registerOre("blockGlass", ModBlocks.glass_polonium);
		OreDictionary.registerOre("blockGlass", ModBlocks.glass_ash);
		OreDictionary.registerOre("blockGlassYellow", ModBlocks.glass_uranium);
		OreDictionary.registerOre("blockGlassLime", ModBlocks.glass_trinitite);
		OreDictionary.registerOre("blockGlassRed", ModBlocks.glass_polonium);
		OreDictionary.registerOre("blockGlassBlack", ModBlocks.glass_ash);

		OreDictionary.registerOre("container1000lubricant", ModItems.bdcl);
		
		MaterialShapes.registerCompatShapes();
	}
	
	public static String getReflector() {
		return GeneralConfig.enableReflectorCompat ? "plateDenseLead" : "plateTungCar"; //let's just mangle the name into "tungCar" so that it can't conflict with anything ever
	}
	
	public static void registerGroups() {
		OreDictManager.ANY_PLASTIC.addPrefix(OreNames.INGOT, true).addPrefix(OreNames.DUST, true).addPrefix(OreNames.BLOCK, true);
		OreDictManager.ANY_HARDPLASTIC.addPrefix(OreNames.INGOT, true);
		OreDictManager.ANY_RESISTANTALLOY.addPrefix(OreNames.INGOT, true).addPrefix(OreNames.DUST, true).addPrefix(OreNames.PLATECAST, true).addPrefix(OreNames.PLATEWELDED, true).addPrefix(OreNames.HEAVY_COMPONENT, true).addPrefix(OreNames.BLOCK, true);
		OreDictManager.ANY_TAR.addPrefix(OreNames.ANY, false);
	}
	
	private static boolean recursionBrake = false;
	
	@SubscribeEvent
	public void onRegisterOre(OreRegisterEvent event) {
		
		if(OreDictManager.recursionBrake)
			return;
		
		OreDictManager.recursionBrake = true;
		
		HashSet<String> strings = OreDictManager.reRegistration.get(event.Name);
		
		if(strings != null) {
			for(String name : strings) {
				OreDictionary.registerOre(name, event.Ore);
				MainRegistry.logger.info("Re-registration for " + event.Name + " to " + name);
			}
		}
		
		OreDictManager.recursionBrake = false;
	}
	
	public static class DictFrame {
		public String[] mats;
		float hazMult = 1.0F;
		List<HazardEntry> hazards = new ArrayList<>();
		
		public DictFrame(String... mats) {
			this.mats = mats;
		}

		/*
		 * Quick access methods to grab ore names for recipes.
		 */
		
		public String any() {			return OreNames.ANY				+ this.mats[0]; }
		public String nugget() {		return OreNames.NUGGET			+ this.mats[0]; }
		public String tiny() {			return OreNames.TINY			+ this.mats[0]; }
		public String ingot() {			return OreNames.INGOT			+ this.mats[0]; }
		public String dustTiny() {		return OreNames.DUSTTINY		+ this.mats[0]; }
		public String dust() {			return OreNames.DUST			+ this.mats[0]; }
		public String gem() {			return OreNames.GEM				+ this.mats[0]; }
		public String crystal() {		return OreNames.CRYSTAL			+ this.mats[0]; }
		public String plate() {			return OreNames.PLATE			+ this.mats[0]; }
		public String plateCast() {		return OreNames.PLATECAST		+ this.mats[0]; }
		public String plateWelded() {	return OreNames.PLATEWELDED		+ this.mats[0]; }
		public String heavyComp() {		return OreNames.HEAVY_COMPONENT	+ this.mats[0]; }
		public String wireDense() {		return OreNames.WIREDENSE		+ this.mats[0]; }
		public String billet() {		return OreNames.BILLET			+ this.mats[0]; }
		public String block() {			return OreNames.BLOCK			+ this.mats[0]; }
		public String ore() {			return OreNames.ORE				+ this.mats[0]; }
		public String[] anys() {		return appendToAll(OreNames.ANY); }
		public String[] nuggets() {		return appendToAll(OreNames.NUGGET); }
		public String[] tinys() {		return appendToAll(OreNames.TINY); }
		public String[] allNuggets() {	return appendToAll(OreNames.NUGGET, OreNames.TINY); }
		public String[] ingots() {		return appendToAll(OreNames.INGOT); }
		public String[] dustTinys() {	return appendToAll(OreNames.DUSTTINY); }
		public String[] dusts() {		return appendToAll(OreNames.DUST); }
		public String[] gems() {		return appendToAll(OreNames.GEM); }
		public String[] crystals() {	return appendToAll(OreNames.CRYSTAL); }
		public String[] plates() {		return appendToAll(OreNames.PLATE); }
		public String[] plateCasts() {	return appendToAll(OreNames.PLATECAST); }
		public String[] billets() {		return appendToAll(OreNames.BILLET); }
		public String[] blocks() {		return appendToAll(OreNames.BLOCK); }
		public String[] ores() {		return appendToAll(OreNames.ORE); }
		
		/** Returns cast (triple) plates if 528 mode is enabled or normal plates if not */
		public String plate528() { return GeneralConfig.enable528 ? plateCast() : plate(); }
		
		private String[] appendToAll(String... prefix) {
			
			String[] names = new String[this.mats.length * prefix.length];
			
			for(int i = 0; i < this.mats.length; i++) {
				for(int j = 0; j < prefix.length; j++) {
					names[i * prefix.length + j] = prefix[j] + this.mats[i];
				}
			}
			return names;
		}

		public DictFrame rad(float rad) {		return haz(new HazardEntry(HazardRegistry.RADIATION, rad)); }
		public DictFrame hot(float time) {		return haz(new HazardEntry(HazardRegistry.HOT, time)); }
		public DictFrame blinding(float time) {	return haz(new HazardEntry(HazardRegistry.BLINDING, time)); }
		public DictFrame asbestos(float asb) {	return haz(new HazardEntry(HazardRegistry.ASBESTOS, asb)); }
		public DictFrame hydro(float h) {		return haz(new HazardEntry(HazardRegistry.HYDROACTIVE, h)); }
		
		public DictFrame haz(HazardEntry hazard) {
			this.hazards.add(hazard);
			return this;
		}
		
		/** Returns an ItemStack composed of the supplied item with the meta being the enum's ordinal. Purely syntactic candy */
		public static ItemStack fromOne(Item item, Enum en) {
			return new ItemStack(item, 1, en.ordinal());
		}
		public static ItemStack fromOne(Block block, Enum en) {
			return new ItemStack(block, 1, en.ordinal());
		}
		public static ItemStack fromOne(Item item, Enum en, int stacksize) {
			return new ItemStack(item, stacksize, en.ordinal());
		}
		public static ItemStack fromOne(Block block, Enum en, int stacksize) {
			return new ItemStack(block, stacksize, en.ordinal());
		}
		/** Same as fromOne but with an array of ItemStacks. The array type is Object[] so that the ODM methods work with it. Generates ItemStacks for the entire enum class. */
		public static Object[] fromAll(Item item, Class<? extends Enum> en) {
			Enum[] vals = en.getEnumConstants();
			Object[] stacks = new Object[vals.length];
			
			for(int i = 0; i < vals.length; i++) {
				stacks[i] = new ItemStack(item, 1, vals[i].ordinal());
			}
			return stacks;
		}
		public static Object[] fromAll(Block block, Class<? extends Enum> en) {
			Enum[] vals = en.getEnumConstants();
			Object[] stacks = new Object[vals.length];
			
			for(int i = 0; i < vals.length; i++) {
				stacks[i] = new ItemStack(block, 1, vals[i].ordinal());
			}
			return stacks;
		}
		
		public DictFrame any(Object... thing) {
			return makeObject(OreNames.ANY, thing);
		}
		public DictFrame nugget(Object... nugget) {
			this.hazMult = HazardRegistry.nugget;
			return makeObject(OreNames.NUGGET, nugget).makeObject(OreNames.TINY, nugget);
		}
		public DictFrame ingot(Object... ingot) {
			this.hazMult = HazardRegistry.ingot;
			return makeObject(OreNames.INGOT, ingot);
		}
		public DictFrame dustSmall(Object... dustSmall) {
			this.hazMult = HazardRegistry.powder_tiny;
			return makeObject(OreNames.DUSTTINY, dustSmall);
		}
		public DictFrame dust(Object... dust) {
			this.hazMult = HazardRegistry.powder;
			return makeObject(OreNames.DUST, dust);
		}
		public DictFrame gem(Object... gem) {
			this.hazMult = HazardRegistry.gem;
			return makeObject(OreNames.GEM, gem);
		}
		public DictFrame crystal(Object... crystal) {
			this.hazMult = HazardRegistry.gem;
			return makeObject(OreNames.CRYSTAL, crystal);
		}
		public DictFrame plate(Object... plate) {
			this.hazMult = HazardRegistry.plate;
			return makeObject(OreNames.PLATE, plate);
		}
		public DictFrame plateCast(Object... plate) {
			this.hazMult = HazardRegistry.plateCast;
			return makeObject(OreNames.PLATECAST, plate);
		}
		public DictFrame billet(Object... billet) {
			this.hazMult = HazardRegistry.billet;
			return makeObject(OreNames.BILLET, billet);
		}
		
		public DictFrame block(Object... block) {
			this.hazMult = HazardRegistry.block;
			return makeObject(OreNames.BLOCK, block);
		}
		public DictFrame ore(Object... ore) {
			this.hazMult = HazardRegistry.ore;
			return makeObject(OreNames.ORE, ore);
		}
		public DictFrame oreNether(Object... oreNether) {
			this.hazMult = HazardRegistry.ore;
			return makeObject(OreNames.ORENETHER, oreNether);
		}

		public DictFrame makeObject(String tag, Object... objects) {
			
			for(Object o : objects) {
				if(o instanceof Item)		registerStack(tag, new ItemStack((Item) o));
				if(o instanceof Block)		registerStack(tag, new ItemStack((Block) o));
				if(o instanceof ItemStack)	registerStack(tag, (ItemStack) o);
			}
			
			return this;
		}
		
		public DictFrame makeItem(String tag, Item... items) {
			for(Item i : items) registerStack(tag, new ItemStack(i));
			return this;
		}
		public DictFrame makeStack(String tag, ItemStack... stacks) {
			for(ItemStack s : stacks) registerStack(tag, s);
			return this;
		}
		public DictFrame makeBlocks(String tag, Block... blocks) {
			for(Block b : blocks) registerStack(tag, new ItemStack(b));
			return this;
		}
		
		public static void registerHazards(List<HazardEntry> hazards, float hazMult, String dictKey) {
			
			if(!hazards.isEmpty() && hazMult > 0F) {
				HazardData data = new HazardData().setMutex(0b1);
				
				for(HazardEntry hazard : hazards) {
					data.addEntry(hazard.clone(hazMult));
				}
				
				HazardSystem.register(dictKey, data);
			}
		}
		
		public void registerStack(String tag, ItemStack stack) {
			for(String mat : this.mats) {
				OreDictionary.registerOre(tag + mat, stack);
				DictFrame.registerHazards(this.hazards, this.hazMult, tag + mat);
			}
			
			/*
			 * Fix for a small oddity in nuclearcraft: many radioactive elements do not have an ore prefix and the sizes
			 * seem generally inconsistent (TH and U are 20 "tiny"s per ingot while boron is 12), so we assume those to be ingots.
			 * Therefore we register all ingots a second time but without prefix. TODO: add a config option to disable this compat.
			 * I'd imagine greg's OD system might not like things without prefixes.
			 */
			if("ingot".equals(tag)) {
				registerStack("", stack);
			}
		}
	}
	
	public static class DictGroup {
		
		private String groupName;
		private HashSet<String> names = new HashSet();
		
		public DictGroup(String groupName) {
			this.groupName = groupName;
		}
		public DictGroup(String groupName, String... names) {
			this(groupName);
			addNames(names);
		}
		public DictGroup(String groupName, DictFrame... frames) {
			this(groupName);
			addFrames(frames);
		}
		
		public DictGroup addNames(String... names) {
			for(String mat : names) this.names.add(mat);
			return this;
		}
		public DictGroup addFrames(DictFrame... frames) {
			for(DictFrame frame : frames) addNames(frame.mats);
			return this;
		}
		
		/**
		 * Will add a reregistration entry for every mat name of every added DictFrame for the given prefix
		 * @param prefix The prefix of both the input and result of the reregistration
		 * @return
		 */
		public DictGroup addPrefix(String prefix, boolean inputPrefix) {
			
			String group = prefix + this.groupName;
			
			for(String name : this.names) {
				String original = (inputPrefix ? prefix : "") + name;
				OreDictManager.addReRegistration(original, group);
			}
			
			return this;
		}
		/**
		 * Same thing as addPrefix, but the input for the reregistration is not bound by the prefix or any mat names
		 * @param prefix The prefix for the resulting reregistration entry (in full: prefix + group name)
		 * @param original The full original ore dict key, not bound by any naming conventions
		 * @return
		 */
		public DictGroup addFixed(String prefix, String original) {
			
			String group = prefix + this.groupName;
			OreDictManager.addReRegistration(original, group);
			return this;
		}
		
		public String any() {			return OreNames.ANY				+ this.groupName; }
		public String nugget() {		return OreNames.NUGGET			+ this.groupName; }
		public String tiny() {			return OreNames.TINY			+ this.groupName; }
		public String ingot() {			return OreNames.INGOT			+ this.groupName; }
		public String dustTiny() {		return OreNames.DUSTTINY		+ this.groupName; }
		public String dust() {			return OreNames.DUST			+ this.groupName; }
		public String gem() {			return OreNames.GEM				+ this.groupName; }
		public String crystal() {		return OreNames.CRYSTAL			+ this.groupName; }
		public String plate() {			return OreNames.PLATE			+ this.groupName; }
		public String plateCast() {		return OreNames.PLATECAST		+ this.groupName; }
		public String plateWelded() {	return OreNames.PLATEWELDED		+ this.groupName; }
		public String heavyComp() {		return OreNames.HEAVY_COMPONENT	+ this.groupName; }
		public String wireDense() {		return OreNames.WIREDENSE		+ this.groupName; }
		public String billet() {		return OreNames.BILLET			+ this.groupName; }
		public String block() {			return OreNames.BLOCK			+ this.groupName; }
		public String ore() {			return OreNames.ORE				+ this.groupName; }
	}
	
	private static void addReRegistration(String original, String additional) {
		
		HashSet<String> strings = OreDictManager.reRegistration.get(original);
		
		if(strings == null)
			strings = new HashSet();
		
		strings.add(additional);
		
		OreDictManager.reRegistration.put(original, strings);
	}
}
