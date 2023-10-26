package com.hbm.inventory.material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.material.NTMMaterial.SmeltingBehavior;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemScraps;
import com.hbm.util.I18nUtil;
import com.hbm.util.ItemStackUtil;

import net.minecraft.item.ItemStack;

/* with every new rewrite, optimization and improvement, the code becomes more gregian */

/**
 * Defines materials that wrap around DictFrames to more accurately describe that material.
 * Direct uses are the crucible and possibly item auto-gen, depending on what traits are set.
 * @author hbm
 */
public class Mats {

	public static List<NTMMaterial> orderedList = new ArrayList<>();
	public static HashMap<String, MaterialShapes> prefixByName = new HashMap();
	public static HashMap<Integer, NTMMaterial> matById = new HashMap();
	public static HashMap<String, NTMMaterial> matByName = new HashMap();
	public static HashMap<ComparableStack, List<MaterialStack>> materialEntries = new HashMap();
	public static HashMap<String, List<MaterialStack>> materialOreEntries = new HashMap();
	
	/*
	 * ItemStacks are saved with their metadata being truncated to a short, so the max meta is 32767
	 * Format for elements: Atomic number *100, plus the last two digits of the mass number. Mass number is 0 for generic/undefined/mixed materials.
	 * Vanilla numbers are in vanilla space (0-29), basic alloys use alloy space (30-99)
	 */
	
	/* Vanilla Space, up to 30 materials, */
	public static final int _VS = 0;
	/* Alloy Space, up to 70 materials. Use >20_000 as an extension.*/
	public static final int _AS = 30;
	
	//Vanilla and vanilla-like
	public static final NTMMaterial MAT_STONE			= Mats.makeSmeltable(Mats._VS + 00,	Mats.df("Stone"), 0x7F7F7F, 0x353535, 0x4D2F23);
	public static final NTMMaterial MAT_CARBON			= Mats.makeAdditive(	1499, 		Mats.df("Carbon"), 0x363636, 0x030303, 0x404040);
	public static final NTMMaterial MAT_COAL			= Mats.make(			1400, 		OreDictManager.COAL)		.setConversion(Mats.MAT_CARBON,  2, 1);
	public static final NTMMaterial MAT_LIGNITE			= Mats.make(			1401, 		OreDictManager.LIGNITE)	.setConversion(Mats.MAT_CARBON,  3, 1);
	public static final NTMMaterial MAT_COALCOKE		= Mats.make(			1410, 		OreDictManager.COALCOKE)	.setConversion(Mats.MAT_CARBON,  4, 3);
	public static final NTMMaterial MAT_PETCOKE			= Mats.make(			1411, 		OreDictManager.PETCOKE)	.setConversion(Mats.MAT_CARBON,  4, 3);
	public static final NTMMaterial MAT_LIGCOKE			= Mats.make(			1412, 		OreDictManager.LIGCOKE)	.setConversion(Mats.MAT_CARBON,  4, 3);
	public static final NTMMaterial MAT_GRAPHITE		= Mats.make(			1420, 		OreDictManager.GRAPHITE)	.setConversion(Mats.MAT_CARBON,  1, 1);
	public static final NTMMaterial MAT_IRON			= Mats.makeSmeltable(2600,		OreDictManager.IRON,		0xFFFFFF, 0x353535, 0xFFA259).setShapes(MaterialShapes.CASTPLATE, MaterialShapes.WELDEDPLATE);
	public static final NTMMaterial MAT_GOLD			= Mats.makeSmeltable(7900,		OreDictManager.GOLD,		0xFFFF8B, 0xC26E00, 0xE8D754).setShapes(MaterialShapes.CASTPLATE);
	public static final NTMMaterial MAT_REDSTONE		= Mats.makeSmeltable(Mats._VS + 01,	OreDictManager.REDSTONE,	0xE3260C, 0x700E06, 0xFF1000);
	public static final NTMMaterial MAT_OBSIDIAN		= Mats.makeSmeltable(Mats._VS + 02,	Mats.df("Obsidian"), 0x3D234D);
	public static final NTMMaterial MAT_HEMATITE		= Mats.makeAdditive(	2601, 		OreDictManager.HEMATITE,	0xDFB7AE, 0x5F372E, 0x6E463D);
	public static final NTMMaterial MAT_WROUGHTIRON		= Mats.makeSmeltable(2602,		Mats.df("WroughtIron"),	0xFAAB89);
	public static final NTMMaterial MAT_PIGIRON			= Mats.makeSmeltable(2603,		Mats.df("PigIron"),		0xFF8B59);
	public static final NTMMaterial MAT_METEORICIRON	= Mats.makeSmeltable(2604,		Mats.df("MeteoricIron"),	0x715347);
	public static final NTMMaterial MAT_MALACHITE		= Mats.makeAdditive(	2901, 		OreDictManager.MALACHITE,	0xA2F0C8, 0x227048, 0x61AF87);

	//Radioactive
	public static final NTMMaterial MAT_URANIUM		= Mats.makeSmeltable(9200,		OreDictManager.U,			0xC1C7BD, 0x2B3227, 0x9AA196).setShapes(MaterialShapes.NUGGET, MaterialShapes.BILLET, MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_U233		= Mats.makeSmeltable(9233,		OreDictManager.U233,		0xC1C7BD, 0x2B3227, 0x9AA196).setShapes(MaterialShapes.NUGGET, MaterialShapes.BILLET, MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_U235		= Mats.makeSmeltable(9235,		OreDictManager.U235,		0xC1C7BD, 0x2B3227, 0x9AA196).setShapes(MaterialShapes.NUGGET, MaterialShapes.BILLET, MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_U238		= Mats.makeSmeltable(9238,		OreDictManager.U238,		0xC1C7BD, 0x2B3227, 0x9AA196).setShapes(MaterialShapes.NUGGET, MaterialShapes.BILLET, MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_THORIUM		= Mats.makeSmeltable(9032,		OreDictManager.TH232,		0xBF825F, 0x1C0000, 0xBF825F).setShapes(MaterialShapes.NUGGET, MaterialShapes.BILLET, MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_PLUTONIUM	= Mats.makeSmeltable(9400,		OreDictManager.PU,			0x9AA3A0, 0x111A17, 0x78817E).setShapes(MaterialShapes.NUGGET, MaterialShapes.BILLET, MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_RGP			= Mats.makeSmeltable(9401,		OreDictManager.PURG,		0x9AA3A0, 0x111A17, 0x78817E).setShapes(MaterialShapes.NUGGET, MaterialShapes.BILLET, MaterialShapes.INGOT, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_PU238		= Mats.makeSmeltable(9438,		OreDictManager.PU238,		0xFFBC59, 0xFF8E2B, 0x78817E).setShapes(MaterialShapes.NUGGET, MaterialShapes.BILLET, MaterialShapes.INGOT, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_PU239		= Mats.makeSmeltable(9439,		OreDictManager.PU239,		0x9AA3A0, 0x111A17, 0x78817E).setShapes(MaterialShapes.NUGGET, MaterialShapes.BILLET, MaterialShapes.INGOT, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_PU240		= Mats.makeSmeltable(9440,		OreDictManager.PU240,		0x9AA3A0, 0x111A17, 0x78817E).setShapes(MaterialShapes.NUGGET, MaterialShapes.BILLET, MaterialShapes.INGOT, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_PU241		= Mats.makeSmeltable(9441,		OreDictManager.PU241,		0x9AA3A0, 0x111A17, 0x78817E).setShapes(MaterialShapes.NUGGET, MaterialShapes.BILLET, MaterialShapes.INGOT, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_RGA			= Mats.makeSmeltable(9501,		OreDictManager.AMRG,		0x93767B).setShapes(MaterialShapes.NUGGET, MaterialShapes.BILLET, MaterialShapes.INGOT, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_AM241		= Mats.makeSmeltable(9541,		OreDictManager.AM241,		0x93767B).setShapes(MaterialShapes.NUGGET, MaterialShapes.BILLET, MaterialShapes.INGOT, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_AM242		= Mats.makeSmeltable(9542,		OreDictManager.AM242,		0x93767B).setShapes(MaterialShapes.NUGGET, MaterialShapes.BILLET, MaterialShapes.INGOT, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_NEPTUNIUM	= Mats.makeSmeltable(9337,		OreDictManager.NP237,		0x647064).setShapes(MaterialShapes.NUGGET, MaterialShapes.BILLET, MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_POLONIUM	= Mats.makeSmeltable(8410,		OreDictManager.PO210,		0x563A26).setShapes(MaterialShapes.NUGGET, MaterialShapes.BILLET, MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_TECHNIETIUM	= Mats.makeSmeltable(4399,		OreDictManager.TC99,		0xFAFFFF, 0x576C6C, 0xCADFDF).setShapes(MaterialShapes.NUGGET, MaterialShapes.BILLET, MaterialShapes.INGOT, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_RADIUM		= Mats.makeSmeltable(8826,		OreDictManager.RA226,		0xE9FAF6).setShapes(MaterialShapes.NUGGET, MaterialShapes.BILLET, MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_ACTINIUM	= Mats.makeSmeltable(8927,		OreDictManager.AC227,		0x958989).setShapes(MaterialShapes.NUGGET, MaterialShapes.BILLET, MaterialShapes.INGOT);
	public static final NTMMaterial MAT_CO60		= Mats.makeSmeltable(2760,		OreDictManager.CO60,		0xC2D1EE, 0x353554, 0x8F72AE).setShapes(MaterialShapes.NUGGET, MaterialShapes.BILLET, MaterialShapes.INGOT, MaterialShapes.DUST);
	public static final NTMMaterial MAT_AU198		= Mats.makeSmeltable(7998,		OreDictManager.AU198,		0xFFFF8B, 0xC26E00, 0xE8D754).setShapes(MaterialShapes.NUGGET, MaterialShapes.BILLET, MaterialShapes.INGOT, MaterialShapes.DUST);
	public static final NTMMaterial MAT_PB209		= Mats.makeSmeltable(8209,		OreDictManager.PB209,		0x7B535D).setShapes(MaterialShapes.NUGGET, MaterialShapes.BILLET, MaterialShapes.INGOT, MaterialShapes.DUST);
	public static final NTMMaterial MAT_SCHRABIDIUM	= Mats.makeSmeltable(12626,		OreDictManager.SA326,		0x32FFFF, 0x005C5C, 0x32FFFF).setShapes(MaterialShapes.NUGGET, MaterialShapes.WIRE, MaterialShapes.BILLET, MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.PLATE, MaterialShapes.CASTPLATE, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_SOLINIUM	= Mats.makeSmeltable(12627,		OreDictManager.SA327,		0xA2E6E0, 0x00433D, 0x72B6B0).setShapes(MaterialShapes.NUGGET, MaterialShapes.BILLET, MaterialShapes.INGOT, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_SCHRABIDATE	= Mats.makeSmeltable(12600,		OreDictManager.SBD,		0x77C0D7, 0x39005E, 0x6589B4).setShapes(MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_SCHRARANIUM	= Mats.makeSmeltable(12601,		OreDictManager.SRN,		0x2B3227, 0x2B3227, 0x24AFAC).setShapes(MaterialShapes.INGOT, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_GHIORSIUM	= Mats.makeSmeltable(12836,		OreDictManager.GH336,		0xF4EFE1, 0x2A3306, 0xC6C6A1).setShapes(MaterialShapes.NUGGET, MaterialShapes.BILLET, MaterialShapes.INGOT, MaterialShapes.BLOCK);

	//Base metals
	public static final NTMMaterial MAT_TITANIUM	= Mats.makeSmeltable(2200,		OreDictManager.TI,			0xF7F3F2, 0x4F4C4B, 0xA99E79).setShapes(MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.PLATE, MaterialShapes.CASTPLATE, MaterialShapes.WELDEDPLATE, MaterialShapes.BLOCK, MaterialShapes.HEAVY_COMPONENT);
	public static final NTMMaterial MAT_COPPER		= Mats.makeSmeltable(2900,		OreDictManager.CU,			0xFDCA88, 0x601E0D, 0xC18336).setShapes(MaterialShapes.WIRE, MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.PLATE, MaterialShapes.CASTPLATE, MaterialShapes.BLOCK, MaterialShapes.HEAVY_COMPONENT);
	public static final NTMMaterial MAT_TUNGSTEN	= Mats.makeSmeltable(7400,		OreDictManager.W,			0x868686, 0x000000, 0x977474).setShapes(MaterialShapes.WIRE, MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.CASTPLATE, MaterialShapes.WELDEDPLATE, MaterialShapes.BLOCK, MaterialShapes.HEAVY_COMPONENT);
	public static final NTMMaterial MAT_ALUMINIUM	= Mats.makeSmeltable(1300,		OreDictManager.AL,			0xFFFFFF, 0x344550, 0xD0B8EB).setShapes(MaterialShapes.WIRE, MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.PLATE, MaterialShapes.CASTPLATE, MaterialShapes.BLOCK, MaterialShapes.HEAVY_COMPONENT);
	public static final NTMMaterial MAT_LEAD		= Mats.makeSmeltable(8200,		OreDictManager.PB,			0xA6A6B2, 0x03030F, 0x646470).setShapes(MaterialShapes.NUGGET, MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.PLATE, MaterialShapes.CASTPLATE, MaterialShapes.BLOCK, MaterialShapes.HEAVY_COMPONENT);
	public static final NTMMaterial MAT_BISMUTH		= Mats.makeSmeltable(8300,		OreDictManager.BI, 		0xB200FF).setShapes(MaterialShapes.NUGGET, MaterialShapes.BILLET, MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_ARSENIC		= Mats.makeSmeltable(3300,		OreDictManager.AS,			0x6CBABA, 0x242525, 0x558080).setShapes(MaterialShapes.NUGGET, MaterialShapes.INGOT);
	public static final NTMMaterial MAT_TANTALIUM	= Mats.makeSmeltable(7300,		OreDictManager.TA,			0xFFFFFF, 0x1D1D36, 0xA89B74).setShapes(MaterialShapes.NUGGET, MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_NIOBIUM		= Mats.makeSmeltable(4100,		OreDictManager.NB,			0xB76EC9, 0x2F2D42, 0xD576B1).setShapes(MaterialShapes.NUGGET, MaterialShapes.DUSTTINY, MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_BERYLLIUM	= Mats.makeSmeltable(400,		OreDictManager.BE,			0xB2B2A6, 0x0F0F03, 0xAE9572).setShapes(MaterialShapes.NUGGET, MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_COBALT		= Mats.makeSmeltable(2700,		OreDictManager.CO,			0xC2D1EE, 0x353554, 0x8F72AE).setShapes(MaterialShapes.NUGGET, MaterialShapes.DUSTTINY, MaterialShapes.BILLET, MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_BORON		= Mats.makeSmeltable(500,		OreDictManager.B,			0xBDC8D2, 0x29343E, 0xAD72AE).setShapes(MaterialShapes.DUSTTINY, MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_ZIRCONIUM	= Mats.makeSmeltable(4000,		OreDictManager.ZR,			0xE3DCBE, 0x3E3719, 0xADA688).setShapes(MaterialShapes.NUGGET, MaterialShapes.DUSTTINY, MaterialShapes.BILLET, MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.CASTPLATE, MaterialShapes.WELDEDPLATE, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_LITHIUM		= Mats.makeSmeltable(300,		OreDictManager.LI,			0xFFFFFF, 0x818181, 0xD6D6D6).setShapes(MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_CADMIUM		= Mats.makeSmeltable(4800,		OreDictManager.CD,			0xFFFADE, 0x350000, 0xA85600).setShapes(MaterialShapes.INGOT, MaterialShapes.DUST);
	public static final NTMMaterial MAT_OSMIRIDIUM	= Mats.makeSmeltable(7699,		OreDictManager.OSMIRIDIUM, 0xDBE3EF, 0x7891BE, 0xACBDD9).setShapes(MaterialShapes.NUGGET, MaterialShapes.INGOT, MaterialShapes.CASTPLATE, MaterialShapes.WELDEDPLATE);
	
	//Alloys
	public static final NTMMaterial MAT_STEEL		= Mats.makeSmeltable(Mats._AS + 0,	OreDictManager.STEEL,		0xAFAFAF, 0x0F0F0F, 0x4A4A4A).setShapes(MaterialShapes.DUSTTINY, MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.PLATE, MaterialShapes.CASTPLATE, MaterialShapes.WELDEDPLATE, MaterialShapes.BLOCK, MaterialShapes.HEAVY_COMPONENT);
	public static final NTMMaterial MAT_MINGRADE	= Mats.makeSmeltable(Mats._AS + 1,	OreDictManager.MINGRADE,	0xFFBA7D, 0xAF1700, 0xE44C0F).setShapes(MaterialShapes.WIRE, MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_ALLOY		= Mats.makeSmeltable(Mats._AS + 2,	OreDictManager.ALLOY,		0xFF8330, 0x700000, 0xFF7318).setShapes(MaterialShapes.WIRE, MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.PLATE, MaterialShapes.CASTPLATE, MaterialShapes.BLOCK, MaterialShapes.HEAVY_COMPONENT);
	public static final NTMMaterial MAT_DURA		= Mats.makeSmeltable(Mats._AS + 3,	OreDictManager.DURA,		0x183039, 0x030B0B, 0x376373).setShapes(MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_SATURN		= Mats.makeSmeltable(Mats._AS + 4,	OreDictManager.BIGMT,		0x4DA3AF, 0x00000C, 0x4DA3AF).setShapes(MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_DESH		= Mats.makeSmeltable(Mats._AS + 12,	OreDictManager.DESH,		0xFF6D6D, 0x720000, 0xF22929).setShapes(MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.CASTPLATE, MaterialShapes.BLOCK, MaterialShapes.HEAVY_COMPONENT);
	public static final NTMMaterial MAT_STAR		= Mats.makeSmeltable(Mats._AS + 5,	OreDictManager.STAR,		0xCCCCEA, 0x11111A, 0xA5A5D3).setShapes(MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_FERRO		= Mats.makeSmeltable(Mats._AS + 7,	OreDictManager.FERRO,		0xB7B7C9, 0x101022, 0x6B6B8B).setShapes(MaterialShapes.INGOT);
	public static final NTMMaterial MAT_TCALLOY		= Mats.makeSmeltable(Mats._AS + 6,	OreDictManager.TCALLOY,	0xD4D6D6, 0x323D3D, 0x9CA6A6).setShapes(MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.CASTPLATE, MaterialShapes.WELDEDPLATE, MaterialShapes.HEAVY_COMPONENT);
	public static final NTMMaterial MAT_CDALLOY		= Mats.makeSmeltable(Mats._AS + 13,	OreDictManager.CDALLOY,	0xF7DF8F, 0x604308, 0xFBD368).setShapes(MaterialShapes.INGOT, MaterialShapes.CASTPLATE, MaterialShapes.WELDEDPLATE, MaterialShapes.HEAVY_COMPONENT);
	public static final NTMMaterial MAT_MAGTUNG		= Mats.makeSmeltable(Mats._AS + 8,	OreDictManager.MAGTUNG,	0x22A2A2, 0x0F0F0F, 0x22A2A2).setShapes(MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_CMB			= Mats.makeSmeltable(Mats._AS + 9,	OreDictManager.CMB,		0x6F6FB4, 0x000011, 0x6F6FB4).setShapes(MaterialShapes.INGOT, MaterialShapes.DUST, MaterialShapes.PLATE, MaterialShapes.CASTPLATE, MaterialShapes.WELDEDPLATE, MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_FLUX		= Mats.makeAdditive(Mats._AS + 10,	Mats.df("Flux"),	0xF1E0BB, 0x6F6256, 0xDECCAD).setShapes(MaterialShapes.DUST);
	public static final NTMMaterial MAT_SLAG		= Mats.makeSmeltable(Mats._AS + 11,	OreDictManager.SLAG,		0x554940, 0x34281F, 0x6C6562).setShapes(MaterialShapes.BLOCK);
	public static final NTMMaterial MAT_MUD			= Mats.makeSmeltable(Mats._AS + 14,	OreDictManager.MUD,		0xBCB5A9, 0x481213, 0x96783B).setShapes(MaterialShapes.INGOT);
	
	@Deprecated public static NTMMaterial makeSmeltable(int id, DictFrame dict, int color) { return Mats.makeSmeltable(id, dict, color, color, color); }
	@Deprecated public static NTMMaterial makeAdditive(int id, DictFrame dict, int color) { return Mats.makeAdditive(id, dict, color, color, color); }

	public static NTMMaterial make(int id, DictFrame dict) {
		return new NTMMaterial(id, dict);
	}
	
	public static NTMMaterial makeSmeltable(int id, DictFrame dict, int solidColorLight, int solidColorDark, int moltenColor) {
		return new NTMMaterial(id, dict).smeltable(SmeltingBehavior.SMELTABLE).setSolidColor(solidColorLight, solidColorDark).setMoltenColor(moltenColor);
	}
	
	public static NTMMaterial makeAdditive(int id, DictFrame dict, int solidColorLight, int solidColorDark, int moltenColor) {
		return new NTMMaterial(id, dict).smeltable(SmeltingBehavior.ADDITIVE).setSolidColor(solidColorLight, solidColorDark).setMoltenColor(moltenColor);
	}
	
	public static DictFrame df(String string) {
		return new DictFrame(string);
	}
	
	/** will not respect stacksizes - all stacks will be treated as a singular */
	public static List<MaterialStack> getMaterialsFromItem(ItemStack stack) {
		List<MaterialStack> list = new ArrayList<>();
		List<String> names = ItemStackUtil.getOreDictNames(stack);
		
		if(!names.isEmpty()) {
			outer:
			for(String name : names) {
				
				List<MaterialStack> oreEntries = Mats.materialOreEntries.get(name);
				
				if(oreEntries != null) {
					list.addAll(oreEntries);
					break outer;
				}
				
				for(Entry<String, MaterialShapes> prefixEntry : Mats.prefixByName.entrySet()) {
					String prefix = prefixEntry.getKey();
						
					if(name.startsWith(prefix)) {
						String materialName = name.substring(prefix.length());
						NTMMaterial material = Mats.matByName.get(materialName);
						
						if(material != null) {
							list.add(new MaterialStack(material, prefixEntry.getValue().q(1)));
							break outer;
						}
					}
				}
			}
		}
		
		List<MaterialStack> entries = Mats.materialEntries.get(new ComparableStack(stack).makeSingular());
		
		if(entries != null) {
			list.addAll(entries);
		}
		
		if(stack.getItem() == ModItems.scraps) {
			list.add(ItemScraps.getMats(stack));
		}
		
		return list;
	}

	public static List<MaterialStack> getSmeltingMaterialsFromItem(ItemStack stack) {
		List<MaterialStack> baseMats = Mats.getMaterialsFromItem(stack);
		List<MaterialStack> smelting = new ArrayList<>();
		baseMats.forEach(x -> smelting.add(new MaterialStack(x.material.smeltsInto, (int) (x.amount * x.material.convOut / x.material.convIn))));
		return smelting;
	}
	
	public static class MaterialStack {
		//final fields to prevent accidental changing
		public final NTMMaterial material;
		public int amount;
		
		public MaterialStack(NTMMaterial material, int amount) {
			this.material = material;
			this.amount = amount;
		}
		
		public MaterialStack copy() {
			return new MaterialStack(this.material, this.amount);
		}
	}
	
	public static String formatAmount(int amount, boolean showInMb) {
		
		if(showInMb) {
			return (amount * 2) + "mB";
		}
		
		String format = "";
		
		int blocks = amount / MaterialShapes.BLOCK.q(1);
		amount -= MaterialShapes.BLOCK.q(blocks);
		int ingots = amount / MaterialShapes.INGOT.q(1);
		amount -= MaterialShapes.INGOT.q(ingots);
		int nuggets = amount / MaterialShapes.NUGGET.q(1);
		amount -= MaterialShapes.NUGGET.q(nuggets);
		int quanta = amount;
		
		if(blocks > 0) format += (blocks == 1 ? I18nUtil.resolveKey("matshape.block", blocks) : I18nUtil.resolveKey("matshape.blocks", blocks)) + " ";
		if(ingots > 0) format += (ingots == 1 ? I18nUtil.resolveKey("matshape.ingot", ingots) : I18nUtil.resolveKey("matshape.ingots", ingots)) + " ";
		if(nuggets > 0) format += (nuggets == 1 ? I18nUtil.resolveKey("matshape.nugget", nuggets) : I18nUtil.resolveKey("matshape.nuggets", nuggets)) + " ";
		if(quanta > 0) format += (quanta == 1 ? I18nUtil.resolveKey("matshape.quantum", quanta) : I18nUtil.resolveKey("matshape.quanta", quanta)) + " ";
		
		return format.trim();
	}
}
