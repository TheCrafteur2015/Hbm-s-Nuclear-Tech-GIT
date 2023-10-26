package com.hbm.hazard;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.hazard.modifier.HazardModifierFuelRadiation;
import com.hbm.hazard.modifier.HazardModifierRBMKHot;
import com.hbm.hazard.modifier.HazardModifierRBMKRadiation;
import com.hbm.hazard.modifier.HazardModifierRTGRadiation;
import com.hbm.hazard.transformer.HazardTransformerRadiationContainer;
import com.hbm.hazard.transformer.HazardTransformerRadiationME;
import com.hbm.hazard.transformer.HazardTransformerRadiationNBT;
import com.hbm.hazard.type.HazardTypeAsbestos;
import com.hbm.hazard.type.HazardTypeBase;
import com.hbm.hazard.type.HazardTypeBlinding;
import com.hbm.hazard.type.HazardTypeCoal;
import com.hbm.hazard.type.HazardTypeDigamma;
import com.hbm.hazard.type.HazardTypeExplosive;
import com.hbm.hazard.type.HazardTypeHot;
import com.hbm.hazard.type.HazardTypeHydroactive;
import com.hbm.hazard.type.HazardTypeRadiation;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBreedingRod.BreedingRodType;
import com.hbm.items.machine.ItemRTGPelletDepleted.DepletedRTGMaterial;
import com.hbm.items.machine.ItemWatzPellet.EnumWatzType;
import com.hbm.items.machine.ItemZirnoxRod.EnumZirnoxType;
import com.hbm.items.special.ItemHolotapeImage.EnumHoloImage;
import com.hbm.util.Compat;
import com.hbm.util.Compat.ReikaIsotope;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HazardRegistry {

	//CO60		             5a		β−	030.00Rad/s	Spicy
	//SR90		            29a		β−	015.00Rad/s Spicy
	//TC99		       211,000a		β−	002.75Rad/s	Spicy
	//I181		           192h		β−	150.00Rad/s	2 much spice :(
	//XE135		             9h		β−	aaaaaaaaaaaaaaaa
	//CS137		            30a		β−	020.00Rad/s	Spicy
	//AU198		            64h		β−	500.00Rad/s	2 much spice :(
	//PB209		             3h		β−	10,000.00Rad/s mama mia my face is melting off
	//AT209		             5h		β+	like 7.5k or sth idk bruv
	//PO210		           138d		α	075.00Rad/s	Spicy
	//RA226		         1,600a		α	007.50Rad/s
	//AC227		            22a		β−	030.00Rad/s Spicy
	//TH232		14,000,000,000a		α	000.10Rad/s
	//U233		       160,000a		α	005.00Rad/s
	//U235		   700,000,000a		α	001.00Rad/s
	//U238		 4,500,000,000a		α	000.25Rad/s
	//NP237		     2,100,000a		α	002.50Rad/s
	//PU238		            88a		α	010.00Rad/s	Spicy
	//PU239		        24,000a		α	005.00Rad/s
	//PU240		         6,600a		α	007.50Rad/s
	//PU241		            14a		β−	025.00Rad/s	Spicy
	//AM241		           432a		α	008.50Rad/s
	//AM242		           141a		β−	009.50Rad/s

	//simplified groups for ReC compat
	public static final float gen_S = 10_000F;
	public static final float gen_H = 2_000F;
	public static final float gen_10D = 100F;
	public static final float gen_100D = 80F;
	public static final float gen_1Y = 50F;
	public static final float gen_10Y = 30F;
	public static final float gen_100Y = 10F;
	public static final float gen_1K = 7.5F;
	public static final float gen_10K = 6.25F;
	public static final float gen_100K = 5F;
	public static final float gen_1M = 2.5F;
	public static final float gen_10M = 1.5F;
	public static final float gen_100M = 1F;
	public static final float gen_1B = 0.5F;
	public static final float gen_10B = 0.1F;

	public static final float co60 = 30.0F;
	public static final float sr90 = 15.0F;
	public static final float tc99 = 2.75F;
	public static final float i131 = 150.0F;
	public static final float xe135 = 1250.0F;
	public static final float cs137 = 20.0F;
	public static final float au198 = 500.0F;
	public static final float pb209 = 10000.0F;
	public static final float at209 = 7500.0F;
	public static final float po210 = 75.0F;
	public static final float ra226 = 7.5F;
	public static final float ac227 = 30.0F;
	public static final float th232 = 0.1F;
	public static final float thf = 1.75F;
	public static final float u = 0.35F;
	public static final float u233 = 5.0F;
	public static final float u235 = 1.0F;
	public static final float u238 = 0.25F;
	public static final float uf = 0.5F;
	public static final float np237 = 2.5F;
	public static final float npf = 1.5F;
	public static final float pu = 7.5F;
	public static final float purg = 6.25F;
	public static final float pu238 = 10.0F;
	public static final float pu239 = 5.0F;
	public static final float pu240 = 7.5F;
	public static final float pu241 = 25.0F;
	public static final float puf = 4.25F;
	public static final float am241 = 8.5F;
	public static final float am242 = 9.5F;
	public static final float amrg = 9.0F;
	public static final float amf = 4.75F;
	public static final float mox = 2.5F;
	public static final float sa326 = 15.0F;
	public static final float sa327 = 17.5F;
	public static final float saf = 5.85F;
	public static final float sas3 = 5F;
	public static final float gh336 = 5.0F;
	public static final float mud = 1.0F;
	public static final float radsource_mult = 3.0F;
	public static final float pobe = HazardRegistry.po210 * HazardRegistry.radsource_mult;
	public static final float rabe = HazardRegistry.ra226 * HazardRegistry.radsource_mult;
	public static final float pube = HazardRegistry.pu238 * HazardRegistry.radsource_mult;
	public static final float zfb_bi = HazardRegistry.u235 * 0.35F;
	public static final float zfb_pu241 = HazardRegistry.pu241 * 0.5F;
	public static final float zfb_am_mix = HazardRegistry.amrg * 0.5F;
	public static final float bf = 300_000.0F;
	public static final float bfb = 500_000.0F;

	public static final float sr = HazardRegistry.sa326 * 0.1F;
	public static final float sb = HazardRegistry.sa326 * 0.1F;
	public static final float trx = 25.0F;
	public static final float trn = 0.1F;
	public static final float wst = 15.0F;
	public static final float wstv = 7.5F;
	public static final float yc = HazardRegistry.u;
	public static final float fo = 10F;

	public static final float nugget = 0.1F;
	public static final float ingot = 1.0F;
	public static final float gem = 1.0F;
	public static final float plate = HazardRegistry.ingot;
	public static final float plateCast = HazardRegistry.plate * 3;
	public static final float powder_mult = 3.0F;
	public static final float powder = HazardRegistry.ingot * HazardRegistry.powder_mult;
	public static final float powder_tiny = HazardRegistry.nugget * HazardRegistry.powder_mult;
	public static final float ore = HazardRegistry.ingot;
	public static final float block = 10.0F;
	public static final float crystal = HazardRegistry.block;
	public static final float billet = 0.5F;
	public static final float rtg = HazardRegistry.billet * 3;
	public static final float rod = 0.5F;
	public static final float rod_dual = HazardRegistry.rod * 2;
	public static final float rod_quad = HazardRegistry.rod * 4;
	public static final float rod_rbmk = HazardRegistry.rod * 8;

	public static final HazardTypeBase RADIATION = new HazardTypeRadiation();
	public static final HazardTypeBase DIGAMMA = new HazardTypeDigamma();
	public static final HazardTypeBase HOT = new HazardTypeHot();
	public static final HazardTypeBase BLINDING = new HazardTypeBlinding();
	public static final HazardTypeBase ASBESTOS = new HazardTypeAsbestos();
	public static final HazardTypeBase COAL = new HazardTypeCoal();
	public static final HazardTypeBase HYDROACTIVE = new HazardTypeHydroactive();
	public static final HazardTypeBase EXPLOSIVE = new HazardTypeExplosive();
	
	public static void registerItems() {
		
		HazardSystem.register(Items.gunpowder, HazardRegistry.makeData(HazardRegistry.EXPLOSIVE, 1F));
		HazardSystem.register(Blocks.tnt, HazardRegistry.makeData(HazardRegistry.EXPLOSIVE, 4F));
		HazardSystem.register(Items.pumpkin_pie, HazardRegistry.makeData(HazardRegistry.EXPLOSIVE, 1F));
		
		HazardSystem.register(ModItems.ball_dynamite, HazardRegistry.makeData(HazardRegistry.EXPLOSIVE, 2F));
		HazardSystem.register(ModItems.stick_dynamite, HazardRegistry.makeData(HazardRegistry.EXPLOSIVE, 1F));
		HazardSystem.register(ModItems.stick_tnt, HazardRegistry.makeData(HazardRegistry.EXPLOSIVE, 1.5F));
		HazardSystem.register(ModItems.stick_semtex, HazardRegistry.makeData(HazardRegistry.EXPLOSIVE, 2.5F));
		HazardSystem.register(ModItems.stick_c4, HazardRegistry.makeData(HazardRegistry.EXPLOSIVE, 2.5F));

		HazardSystem.register(ModItems.cordite, HazardRegistry.makeData(HazardRegistry.EXPLOSIVE, 2F));
		HazardSystem.register(ModItems.ballistite, HazardRegistry.makeData(HazardRegistry.EXPLOSIVE, 1F));

		HazardSystem.register("dustCoal", HazardRegistry.makeData(HazardRegistry.COAL, HazardRegistry.powder));
		HazardSystem.register("dustTinyCoal", HazardRegistry.makeData(HazardRegistry.COAL, HazardRegistry.powder_tiny));
		HazardSystem.register("dustLignite", HazardRegistry.makeData(HazardRegistry.COAL, HazardRegistry.powder));
		HazardSystem.register("dustTinyLignite", HazardRegistry.makeData(HazardRegistry.COAL, HazardRegistry.powder_tiny));
		
		HazardSystem.register(ModItems.insert_polonium, HazardRegistry.makeData(HazardRegistry.RADIATION, 100F));

		HazardSystem.register(ModItems.demon_core_open, HazardRegistry.makeData(HazardRegistry.RADIATION, 5F));
		HazardSystem.register(ModItems.demon_core_closed, HazardRegistry.makeData(HazardRegistry.RADIATION, 100_000F));
		HazardSystem.register(ModBlocks.lamp_demon, HazardRegistry.makeData(HazardRegistry.RADIATION, 100_000F));

		HazardSystem.register(ModItems.cell_tritium, HazardRegistry.makeData(HazardRegistry.RADIATION, 0.001F));
		HazardSystem.register(ModItems.cell_sas3, HazardRegistry.makeData().addEntry(HazardRegistry.RADIATION, HazardRegistry.sas3).addEntry(HazardRegistry.BLINDING, 60F));
		HazardSystem.register(ModItems.cell_balefire, HazardRegistry.makeData(HazardRegistry.RADIATION, 50F));
		HazardSystem.register(ModItems.powder_balefire, HazardRegistry.makeData(HazardRegistry.RADIATION, 500F));
		HazardSystem.register(ModItems.egg_balefire_shard, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.bf * HazardRegistry.nugget));
		HazardSystem.register(ModItems.egg_balefire, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.bf * HazardRegistry.ingot));

		HazardSystem.register(ModItems.solid_fuel_bf, HazardRegistry.makeData(HazardRegistry.RADIATION, 1000)); //roughly the amount of the balefire shard diluted in 250mB of rocket fuel
		HazardSystem.register(ModItems.solid_fuel_presto_bf, HazardRegistry.makeData(HazardRegistry.RADIATION, 2000));
		HazardSystem.register(ModItems.solid_fuel_presto_triplet_bf, HazardRegistry.makeData(HazardRegistry.RADIATION, 6000));

		HazardSystem.register(ModItems.nuclear_waste_long, HazardRegistry.makeData(HazardRegistry.RADIATION, 5F));
		HazardSystem.register(ModItems.nuclear_waste_long_tiny, HazardRegistry.makeData(HazardRegistry.RADIATION, 0.5F));
		HazardSystem.register(ModItems.nuclear_waste_short, HazardRegistry.makeData().addEntry(HazardRegistry.RADIATION, 30F).addEntry(HazardRegistry.HOT, 5F));
		HazardSystem.register(ModItems.nuclear_waste_short_tiny, HazardRegistry.makeData().addEntry(HazardRegistry.RADIATION, 3F).addEntry(HazardRegistry.HOT, 5F));
		HazardSystem.register(ModItems.nuclear_waste_long_depleted, HazardRegistry.makeData(HazardRegistry.RADIATION, 0.5F));
		HazardSystem.register(ModItems.nuclear_waste_long_depleted_tiny, HazardRegistry.makeData(HazardRegistry.RADIATION, 0.05F));
		HazardSystem.register(ModItems.nuclear_waste_short_depleted, HazardRegistry.makeData(HazardRegistry.RADIATION, 3F));
		HazardSystem.register(ModItems.nuclear_waste_short_depleted_tiny, HazardRegistry.makeData(HazardRegistry.RADIATION, 0.3F));

		HazardSystem.register(ModItems.scrap_nuclear, HazardRegistry.makeData(HazardRegistry.RADIATION, 1F));
		HazardSystem.register(ModItems.trinitite, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.trn * HazardRegistry.ingot));
		HazardSystem.register(ModBlocks.block_trinitite, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.trn * HazardRegistry.block));
		HazardSystem.register(ModItems.nuclear_waste, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.wst * HazardRegistry.ingot));
		HazardSystem.register(ModBlocks.yellow_barrel, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.wst * HazardRegistry.ingot * 10));
		HazardSystem.register(ModItems.billet_nuclear_waste, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.wst * HazardRegistry.billet));
		HazardSystem.register(ModItems.nuclear_waste_tiny, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.wst * HazardRegistry.nugget));
		HazardSystem.register(ModItems.nuclear_waste_vitrified, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.wstv * HazardRegistry.ingot));
		HazardSystem.register(ModItems.nuclear_waste_vitrified_tiny, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.wstv * HazardRegistry.nugget));
		HazardSystem.register(ModBlocks.block_waste, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.wst * HazardRegistry.block));
		HazardSystem.register(ModBlocks.block_waste_painted, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.wst * HazardRegistry.block));
		HazardSystem.register(ModBlocks.block_waste_vitrified, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.wstv * HazardRegistry.block));
		HazardSystem.register(ModBlocks.ancient_scrap, HazardRegistry.makeData(HazardRegistry.RADIATION, 150F));
		HazardSystem.register(ModBlocks.block_corium, HazardRegistry.makeData(HazardRegistry.RADIATION, 150F));
		HazardSystem.register(ModBlocks.block_corium_cobble, HazardRegistry.makeData(HazardRegistry.RADIATION, 150F));
		HazardSystem.register(ModBlocks.sand_gold198, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.au198 * HazardRegistry.block * HazardRegistry.powder_mult));
		
		HazardSystem.register(new ItemStack(ModBlocks.sellafield, 1, 0), HazardRegistry.makeData(HazardRegistry.RADIATION, 0.5F));
		HazardSystem.register(new ItemStack(ModBlocks.sellafield, 1, 1), HazardRegistry.makeData(HazardRegistry.RADIATION, 1F));
		HazardSystem.register(new ItemStack(ModBlocks.sellafield, 1, 2), HazardRegistry.makeData(HazardRegistry.RADIATION, 2.5F));
		HazardSystem.register(new ItemStack(ModBlocks.sellafield, 1, 3), HazardRegistry.makeData(HazardRegistry.RADIATION, 4F));
		HazardSystem.register(new ItemStack(ModBlocks.sellafield, 1, 4), HazardRegistry.makeData(HazardRegistry.RADIATION, 5F));	
		HazardSystem.register(new ItemStack(ModBlocks.sellafield, 1, 5), HazardRegistry.makeData(HazardRegistry.RADIATION, 10F));
		
		HazardRegistry.registerOtherFuel(ModItems.rod_zirnox, EnumZirnoxType.NATURAL_URANIUM_FUEL.ordinal(), HazardRegistry.u * HazardRegistry.rod_dual, HazardRegistry.wst * HazardRegistry.rod_dual * 11.5F, false);
		HazardRegistry.registerOtherFuel(ModItems.rod_zirnox, EnumZirnoxType.URANIUM_FUEL.ordinal(), HazardRegistry.uf * HazardRegistry.rod_dual, HazardRegistry.wst * HazardRegistry.rod_dual * 10F, false);
		HazardRegistry.registerOtherFuel(ModItems.rod_zirnox, EnumZirnoxType.TH232.ordinal(), HazardRegistry.th232 * HazardRegistry.rod_dual, HazardRegistry.thf * HazardRegistry.rod_dual, false);
		HazardRegistry.registerOtherFuel(ModItems.rod_zirnox, EnumZirnoxType.THORIUM_FUEL.ordinal(), HazardRegistry.thf * HazardRegistry.rod_dual, HazardRegistry.wst * HazardRegistry.rod_dual * 7.5F, false);
		HazardRegistry.registerOtherFuel(ModItems.rod_zirnox, EnumZirnoxType.MOX_FUEL.ordinal(), HazardRegistry.mox * HazardRegistry.rod_dual, HazardRegistry.wst * HazardRegistry.rod_dual * 10F, false);
		HazardRegistry.registerOtherFuel(ModItems.rod_zirnox, EnumZirnoxType.PLUTONIUM_FUEL.ordinal(), HazardRegistry.puf * HazardRegistry.rod_dual, HazardRegistry.wst * HazardRegistry.rod_dual * 12.5F, false);
		HazardRegistry.registerOtherFuel(ModItems.rod_zirnox, EnumZirnoxType.U233_FUEL.ordinal(), HazardRegistry.u233 * HazardRegistry.rod_dual, HazardRegistry.wst * HazardRegistry.rod_dual * 10F, false);
		HazardRegistry.registerOtherFuel(ModItems.rod_zirnox, EnumZirnoxType.U235_FUEL.ordinal(), HazardRegistry.u235 * HazardRegistry.rod_dual, HazardRegistry.wst * HazardRegistry.rod_dual * 11F, false);
		HazardRegistry.registerOtherFuel(ModItems.rod_zirnox, EnumZirnoxType.LES_FUEL.ordinal(), HazardRegistry.saf * HazardRegistry.rod_dual, HazardRegistry.wst * HazardRegistry.rod_dual * 15F, false);
		HazardRegistry.registerOtherFuel(ModItems.rod_zirnox, EnumZirnoxType.LITHIUM.ordinal(), 0, 0.001F * HazardRegistry.rod_dual, false);
		HazardRegistry.registerOtherFuel(ModItems.rod_zirnox, EnumZirnoxType.ZFB_MOX.ordinal(), HazardRegistry.mox * HazardRegistry.rod_dual, HazardRegistry.wst * HazardRegistry.rod_dual * 5F, false);
		
		HazardSystem.register(ModItems.rod_zirnox_natural_uranium_fuel_depleted, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.wst * HazardRegistry.rod_dual * 11.5F));
		HazardSystem.register(ModItems.rod_zirnox_uranium_fuel_depleted, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.wst * HazardRegistry.rod_dual * 10F));
		HazardSystem.register(ModItems.rod_zirnox_thorium_fuel_depleted, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.wst * HazardRegistry.rod_dual * 7.5F));
		HazardSystem.register(ModItems.rod_zirnox_mox_fuel_depleted, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.wst * HazardRegistry.rod_dual * 10F));
		HazardSystem.register(ModItems.rod_zirnox_plutonium_fuel_depleted, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.wst * HazardRegistry.rod_dual * 12.5F));
		HazardSystem.register(ModItems.rod_zirnox_u233_fuel_depleted, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.wst * HazardRegistry.rod_dual * 10F));
		HazardSystem.register(ModItems.rod_zirnox_u235_fuel_depleted, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.wst * HazardRegistry.rod_dual * 11F));
		HazardSystem.register(ModItems.rod_zirnox_les_fuel_depleted, HazardRegistry.makeData().addEntry(HazardRegistry.RADIATION, HazardRegistry.wst * HazardRegistry.rod_dual * 15F).addEntry(HazardRegistry.BLINDING, 20F));
		HazardSystem.register(ModItems.rod_zirnox_tritium, HazardRegistry.makeData(HazardRegistry.RADIATION, 0.001F * HazardRegistry.rod_dual));
		HazardSystem.register(ModItems.rod_zirnox_zfb_mox_depleted, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.wst * HazardRegistry.rod_dual * 5F));
		
		HazardRegistry.registerOtherWaste(ModItems.waste_natural_uranium, HazardRegistry.wst * HazardRegistry.billet * 11.5F);
		HazardRegistry.registerOtherWaste(ModItems.waste_uranium, HazardRegistry.wst * HazardRegistry.billet * 10F);
		HazardRegistry.registerOtherWaste(ModItems.waste_thorium, HazardRegistry.wst * HazardRegistry.billet * 7.5F);
		HazardRegistry.registerOtherWaste(ModItems.waste_mox, HazardRegistry.wst * HazardRegistry.billet * 10F);
		HazardRegistry.registerOtherWaste(ModItems.waste_plutonium, HazardRegistry.wst * HazardRegistry.billet * 12.5F);
		HazardRegistry.registerOtherWaste(ModItems.waste_u233, HazardRegistry.wst * HazardRegistry.billet * 10F);
		HazardRegistry.registerOtherWaste(ModItems.waste_u235, HazardRegistry.wst * HazardRegistry.billet * 11F);
		HazardRegistry.registerOtherWaste(ModItems.waste_schrabidium, HazardRegistry.wst * HazardRegistry.billet * 15F);
		HazardRegistry.registerOtherWaste(ModItems.waste_zfb_mox, HazardRegistry.wst * HazardRegistry.billet * 5F);
		
		HazardRegistry.registerOtherFuel(ModItems.pellet_schrabidium, HazardRegistry.sa326 * HazardRegistry.ingot * 5, HazardRegistry.wst * HazardRegistry.ingot * 100, true);
		HazardRegistry.registerOtherFuel(ModItems.pellet_hes, HazardRegistry.saf * HazardRegistry.ingot * 5, HazardRegistry.wst * HazardRegistry.ingot * 75, true);
		HazardRegistry.registerOtherFuel(ModItems.pellet_mes, HazardRegistry.saf * HazardRegistry.ingot * 5, HazardRegistry.wst * HazardRegistry.ingot * 50, true);
		HazardRegistry.registerOtherFuel(ModItems.pellet_les, HazardRegistry.saf * HazardRegistry.ingot * 5, HazardRegistry.wst * HazardRegistry.ingot * 20, false);
		HazardRegistry.registerOtherFuel(ModItems.pellet_beryllium, 0F, 10F, false);
		HazardRegistry.registerOtherFuel(ModItems.pellet_neptunium, HazardRegistry.np237 * HazardRegistry.ingot * 5, HazardRegistry.wst * HazardRegistry.ingot * 10, false);
		HazardRegistry.registerOtherFuel(ModItems.pellet_lead, 0F, 15F, false);
		HazardRegistry.registerOtherFuel(ModItems.pellet_advanced, 0F, 20F, false);
		
		HazardRegistry.registerOtherFuel(ModItems.plate_fuel_u233, HazardRegistry.u233 * HazardRegistry.ingot, HazardRegistry.wst * HazardRegistry.ingot * 13F, false);
		HazardRegistry.registerOtherFuel(ModItems.plate_fuel_u235, HazardRegistry.u235 * HazardRegistry.ingot, HazardRegistry.wst * HazardRegistry.ingot * 10F, false);
		HazardRegistry.registerOtherFuel(ModItems.plate_fuel_mox, HazardRegistry.mox * HazardRegistry.ingot, HazardRegistry.wst * HazardRegistry.ingot * 16F, false);
		HazardRegistry.registerOtherFuel(ModItems.plate_fuel_pu239, HazardRegistry.pu239 * HazardRegistry.ingot, HazardRegistry.wst * HazardRegistry.ingot * 13.5F, false);
		HazardRegistry.registerOtherFuel(ModItems.plate_fuel_sa326, HazardRegistry.sa326 * HazardRegistry.ingot, HazardRegistry.wst * HazardRegistry.ingot * 10F, true);
		HazardRegistry.registerOtherFuel(ModItems.plate_fuel_ra226be, HazardRegistry.rabe * HazardRegistry.billet, HazardRegistry.pobe * HazardRegistry.nugget * 3, false);
		HazardRegistry.registerOtherFuel(ModItems.plate_fuel_pu238be, HazardRegistry.pube * HazardRegistry.billet, HazardRegistry.pube * HazardRegistry.nugget * 1, false);
		
		HazardRegistry.registerOtherWaste(ModItems.waste_plate_u233, HazardRegistry.wst * HazardRegistry.ingot * 13F);
		HazardRegistry.registerOtherWaste(ModItems.waste_plate_u235, HazardRegistry.wst * HazardRegistry.ingot * 10F);
		HazardRegistry.registerOtherWaste(ModItems.waste_plate_mox, HazardRegistry.wst * HazardRegistry.ingot * 16F);
		HazardRegistry.registerOtherWaste(ModItems.waste_plate_pu239, HazardRegistry.wst * HazardRegistry.ingot * 13.5F);
		HazardRegistry.registerOtherWaste(ModItems.waste_plate_sa326, HazardRegistry.wst * HazardRegistry.ingot * 10F);
		HazardRegistry.registerRadSourceWaste(ModItems.waste_plate_ra226be, HazardRegistry.pobe * HazardRegistry.nugget * 3);
		HazardRegistry.registerRadSourceWaste(ModItems.waste_plate_pu238be, HazardRegistry.pube * HazardRegistry.nugget * 1);
		
		HazardSystem.register(ModItems.debris_graphite, HazardRegistry.makeData().addEntry(HazardRegistry.RADIATION, 70F).addEntry(HazardRegistry.HOT, 5F));
		HazardSystem.register(ModItems.debris_metal, HazardRegistry.makeData(HazardRegistry.RADIATION, 5F));
		HazardSystem.register(ModItems.debris_fuel, HazardRegistry.makeData().addEntry(HazardRegistry.RADIATION, 500F).addEntry(HazardRegistry.HOT, 5F));
		HazardSystem.register(ModItems.debris_concrete, HazardRegistry.makeData(HazardRegistry.RADIATION, 30F));
		HazardSystem.register(ModItems.debris_exchanger, HazardRegistry.makeData(HazardRegistry.RADIATION, 25F));
		HazardSystem.register(ModItems.debris_shrapnel, HazardRegistry.makeData(HazardRegistry.RADIATION, 2.5F));
		HazardSystem.register(ModItems.debris_element, HazardRegistry.makeData(HazardRegistry.RADIATION, 100F));
		
		HazardSystem.register(ModItems.nugget_uranium_fuel, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.uf * HazardRegistry.nugget));
		HazardSystem.register(ModItems.billet_uranium_fuel, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.uf * HazardRegistry.billet));
		HazardSystem.register(ModItems.ingot_uranium_fuel, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.uf * HazardRegistry.ingot));
		HazardSystem.register(ModBlocks.block_uranium_fuel, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.uf * HazardRegistry.block));
		
		HazardSystem.register(ModItems.nugget_plutonium_fuel, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.puf * HazardRegistry.nugget));
		HazardSystem.register(ModItems.billet_plutonium_fuel, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.puf * HazardRegistry.billet));
		HazardSystem.register(ModItems.ingot_plutonium_fuel, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.puf * HazardRegistry.ingot));
		HazardSystem.register(ModBlocks.block_plutonium_fuel, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.puf * HazardRegistry.block));
		
		HazardSystem.register(ModItems.nugget_thorium_fuel, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.thf * HazardRegistry.nugget));
		HazardSystem.register(ModItems.billet_thorium_fuel, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.thf * HazardRegistry.billet));
		HazardSystem.register(ModItems.ingot_thorium_fuel, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.thf * HazardRegistry.ingot));
		HazardSystem.register(ModBlocks.block_thorium_fuel, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.thf * HazardRegistry.block));
		
		HazardSystem.register(ModItems.nugget_neptunium_fuel, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.npf * HazardRegistry.nugget));
		HazardSystem.register(ModItems.billet_neptunium_fuel, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.npf * HazardRegistry.billet));
		HazardSystem.register(ModItems.ingot_neptunium_fuel, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.npf * HazardRegistry.ingot));
		
		HazardSystem.register(ModItems.nugget_mox_fuel, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.mox * HazardRegistry.nugget));
		HazardSystem.register(ModItems.billet_mox_fuel, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.mox * HazardRegistry.billet));
		HazardSystem.register(ModItems.ingot_mox_fuel, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.mox * HazardRegistry.ingot));
		HazardSystem.register(ModBlocks.block_mox_fuel, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.mox * HazardRegistry.block));
		
		HazardSystem.register(ModItems.nugget_americium_fuel, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.amf * HazardRegistry.nugget));
		HazardSystem.register(ModItems.billet_americium_fuel, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.amf * HazardRegistry.billet));
		HazardSystem.register(ModItems.ingot_americium_fuel, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.amf * HazardRegistry.ingot));
		
		HazardSystem.register(ModItems.nugget_schrabidium_fuel, HazardRegistry.makeData().addEntry(HazardRegistry.RADIATION, HazardRegistry.saf * HazardRegistry.nugget).addEntry(HazardRegistry.BLINDING, 5F * HazardRegistry.nugget));
		HazardSystem.register(ModItems.billet_schrabidium_fuel, HazardRegistry.makeData().addEntry(HazardRegistry.RADIATION, HazardRegistry.saf * HazardRegistry.billet).addEntry(HazardRegistry.BLINDING, 5F * HazardRegistry.billet));
		HazardSystem.register(ModItems.ingot_schrabidium_fuel, HazardRegistry.makeData().addEntry(HazardRegistry.RADIATION, HazardRegistry.saf * HazardRegistry.ingot).addEntry(HazardRegistry.BLINDING, 5F * HazardRegistry.ingot));
		HazardSystem.register(ModBlocks.block_schrabidium_fuel, HazardRegistry.makeData().addEntry(HazardRegistry.RADIATION, HazardRegistry.saf * HazardRegistry.block).addEntry(HazardRegistry.BLINDING, 5F * HazardRegistry.block));
		
		HazardSystem.register(ModItems.nugget_hes, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.saf * HazardRegistry.nugget));
		HazardSystem.register(ModItems.billet_hes, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.saf * HazardRegistry.billet));
		HazardSystem.register(ModItems.ingot_hes, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.saf * HazardRegistry.ingot));
		
		HazardSystem.register(ModItems.nugget_les, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.saf * HazardRegistry.nugget));
		HazardSystem.register(ModItems.billet_les, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.saf * HazardRegistry.billet));
		HazardSystem.register(ModItems.ingot_les, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.saf * HazardRegistry.ingot));

		HazardSystem.register(ModItems.billet_balefire_gold, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.au198 * HazardRegistry.billet));
		HazardSystem.register(ModItems.billet_flashlead, HazardRegistry.makeData().addEntry(HazardRegistry.RADIATION, HazardRegistry.pb209 * 1.25F * HazardRegistry.billet).addEntry(HazardRegistry.HOT, 7F));
		HazardSystem.register(ModItems.billet_po210be, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.pobe * HazardRegistry.billet));
		HazardSystem.register(ModItems.billet_ra226be, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.rabe * HazardRegistry.billet));
		HazardSystem.register(ModItems.billet_pu238be, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.pube * HazardRegistry.billet));
		
		HazardRegistry.registerRTGPellet(ModItems.pellet_rtg, HazardRegistry.pu238 * HazardRegistry.rtg, 0, 3F);
		HazardRegistry.registerRTGPellet(ModItems.pellet_rtg_radium, HazardRegistry.ra226 * HazardRegistry.rtg, 0);
		HazardRegistry.registerRTGPellet(ModItems.pellet_rtg_weak, (HazardRegistry.pu238 + (HazardRegistry.u238 * 2)) * HazardRegistry.billet, 0);
		HazardRegistry.registerRTGPellet(ModItems.pellet_rtg_strontium, HazardRegistry.sr90 * HazardRegistry.rtg, 0);
		HazardRegistry.registerRTGPellet(ModItems.pellet_rtg_cobalt, HazardRegistry.co60 * HazardRegistry.rtg, 0);
		HazardRegistry.registerRTGPellet(ModItems.pellet_rtg_actinium, HazardRegistry.ac227 * HazardRegistry.rtg, 0);
		HazardRegistry.registerRTGPellet(ModItems.pellet_rtg_polonium, HazardRegistry.po210 * HazardRegistry.rtg, 0, 3F);
		HazardRegistry.registerRTGPellet(ModItems.pellet_rtg_lead, HazardRegistry.pb209 * HazardRegistry.rtg, 0, 7F, 50F);
		HazardRegistry.registerRTGPellet(ModItems.pellet_rtg_gold, HazardRegistry.au198 * HazardRegistry.rtg, 0, 5F);
		HazardRegistry.registerRTGPellet(ModItems.pellet_rtg_americium, HazardRegistry.am241 * HazardRegistry.rtg, 0);
		HazardSystem.register(new ItemStack(ModItems.pellet_rtg_depleted, 1, DepletedRTGMaterial.NEPTUNIUM.ordinal()), HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.np237 * HazardRegistry.rtg));
		
		HazardSystem.register(ModItems.pile_rod_uranium, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.u * HazardRegistry.billet * 3));
		HazardSystem.register(ModItems.pile_rod_pu239, HazardRegistry.makeData(HazardRegistry.RADIATION, !GeneralConfig.enable528 ? HazardRegistry.purg * HazardRegistry.billet + HazardRegistry.pu239 * HazardRegistry.billet + HazardRegistry.u * HazardRegistry.billet : HazardRegistry.purg * HazardRegistry.billet + HazardRegistry.pu239 * HazardRegistry.billet + HazardRegistry.wst * HazardRegistry.billet));
		HazardSystem.register(ModItems.pile_rod_plutonium, HazardRegistry.makeData(HazardRegistry.RADIATION, !GeneralConfig.enable528 ? HazardRegistry.purg * HazardRegistry.billet * 2 + HazardRegistry.u * HazardRegistry.billet : HazardRegistry.purg * HazardRegistry.billet * 2 + HazardRegistry.wst * HazardRegistry.billet));
		HazardSystem.register(ModItems.pile_rod_source, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.rabe * HazardRegistry.billet * 3));
		
		HazardRegistry.registerBreedingRodRadiation(BreedingRodType.TRITIUM, 0.001F);
		HazardRegistry.registerBreedingRodRadiation(BreedingRodType.CO60, HazardRegistry.co60);
		HazardRegistry.registerBreedingRodRadiation(BreedingRodType.RA226, HazardRegistry.ra226);
		HazardRegistry.registerBreedingRodRadiation(BreedingRodType.AC227, HazardRegistry.ac227);
		HazardRegistry.registerBreedingRodRadiation(BreedingRodType.TH232, HazardRegistry.th232);
		HazardRegistry.registerBreedingRodRadiation(BreedingRodType.THF, HazardRegistry.thf);
		HazardRegistry.registerBreedingRodRadiation(BreedingRodType.U235, HazardRegistry.u235);
		HazardRegistry.registerBreedingRodRadiation(BreedingRodType.NP237, HazardRegistry.np237);
		HazardRegistry.registerBreedingRodRadiation(BreedingRodType.U238, HazardRegistry.u238);
		HazardRegistry.registerBreedingRodRadiation(BreedingRodType.PU238, HazardRegistry.pu238); //it's in a container :)
		HazardRegistry.registerBreedingRodRadiation(BreedingRodType.PU239, HazardRegistry.pu239);
		HazardRegistry.registerBreedingRodRadiation(BreedingRodType.RGP, HazardRegistry.purg);
		HazardRegistry.registerBreedingRodRadiation(BreedingRodType.WASTE, HazardRegistry.wst);
		HazardRegistry.registerBreedingRodRadiation(BreedingRodType.URANIUM, HazardRegistry.u);

		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_ueu, HazardRegistry.u * HazardRegistry.rod_rbmk, HazardRegistry.wst * HazardRegistry.rod_rbmk * 20F);
		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_meu, HazardRegistry.uf * HazardRegistry.rod_rbmk, HazardRegistry.wst * HazardRegistry.rod_rbmk * 21.5F);
		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_heu233, HazardRegistry.u233 * HazardRegistry.rod_rbmk, HazardRegistry.wst * HazardRegistry.rod_rbmk * 31F);
		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_heu235, HazardRegistry.u235 * HazardRegistry.rod_rbmk, HazardRegistry.wst * HazardRegistry.rod_rbmk * 30F);
		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_thmeu, HazardRegistry.thf * HazardRegistry.rod_rbmk, HazardRegistry.wst * HazardRegistry.rod_rbmk * 17.5F);
		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_lep, HazardRegistry.puf * HazardRegistry.rod_rbmk, HazardRegistry.wst * HazardRegistry.rod_rbmk * 25F);
		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_mep, HazardRegistry.purg * HazardRegistry.rod_rbmk, HazardRegistry.wst * HazardRegistry.rod_rbmk * 30F);
		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_hep239, HazardRegistry.pu239 * HazardRegistry.rod_rbmk, HazardRegistry.wst * HazardRegistry.rod_rbmk * 32.5F);
		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_hep241, HazardRegistry.pu241 * HazardRegistry.rod_rbmk, HazardRegistry.wst * HazardRegistry.rod_rbmk * 35F);
		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_lea, HazardRegistry.amf * HazardRegistry.rod_rbmk, HazardRegistry.wst * HazardRegistry.rod_rbmk * 26F);
		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_mea, HazardRegistry.amrg * HazardRegistry.rod_rbmk, HazardRegistry.wst * HazardRegistry.rod_rbmk * 30.5F);
		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_hea241, HazardRegistry.am241 * HazardRegistry.rod_rbmk, HazardRegistry.wst * HazardRegistry.rod_rbmk * 33.5F);
		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_hea242, HazardRegistry.am242 * HazardRegistry.rod_rbmk, HazardRegistry.wst * HazardRegistry.rod_rbmk * 34F);
		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_men, HazardRegistry.npf * HazardRegistry.rod_rbmk, HazardRegistry.wst * HazardRegistry.rod_rbmk * 22.5F);
		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_hen, HazardRegistry.np237 * HazardRegistry.rod_rbmk, HazardRegistry.wst * HazardRegistry.rod_rbmk * 30F);
		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_mox, HazardRegistry.mox * HazardRegistry.rod_rbmk, HazardRegistry.wst * HazardRegistry.rod_rbmk * 25.5F);
		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_les, HazardRegistry.saf * HazardRegistry.rod_rbmk, HazardRegistry.wst * HazardRegistry.rod_rbmk * 24.5F);
		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_mes, HazardRegistry.saf * HazardRegistry.rod_rbmk, HazardRegistry.wst * HazardRegistry.rod_rbmk * 30F);
		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_hes, HazardRegistry.saf * HazardRegistry.rod_rbmk, HazardRegistry.wst * HazardRegistry.rod_rbmk * 50F);
		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_leaus, 0F, HazardRegistry.wst * HazardRegistry.rod_rbmk * 37.5F);
		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_heaus, 0F, HazardRegistry.wst * HazardRegistry.rod_rbmk * 32.5F);
		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_po210be, HazardRegistry.pobe * HazardRegistry.rod_rbmk, HazardRegistry.pobe * HazardRegistry.rod_rbmk * 0.1F, true);
		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_ra226be, HazardRegistry.rabe * HazardRegistry.rod_rbmk, HazardRegistry.rabe * HazardRegistry.rod_rbmk * 0.4F, true);
		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_pu238be, HazardRegistry.pube * HazardRegistry.rod_rbmk, HazardRegistry.wst * HazardRegistry.rod_rbmk * 2.5F);
		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_balefire_gold, HazardRegistry.au198 * HazardRegistry.rod_rbmk, HazardRegistry.bf * HazardRegistry.rod_rbmk * 0.5F, true);
		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_flashlead, HazardRegistry.pb209 * 1.25F * HazardRegistry.rod_rbmk, HazardRegistry.pb209 * HazardRegistry.nugget * 0.05F * HazardRegistry.rod_rbmk, true);
		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_balefire, HazardRegistry.bf * HazardRegistry.rod_rbmk, HazardRegistry.bf * HazardRegistry.rod_rbmk * 100F, true);
		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_zfb_bismuth, HazardRegistry.pu241 * HazardRegistry.rod_rbmk * 0.1F, HazardRegistry.wst * HazardRegistry.rod_rbmk * 5F);
		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_zfb_pu241, HazardRegistry.pu239 * HazardRegistry.rod_rbmk * 0.1F, HazardRegistry.wst * HazardRegistry.rod_rbmk * 7.5F);
		HazardRegistry.registerRBMKRod(ModItems.rbmk_fuel_zfb_am_mix, HazardRegistry.pu241 * HazardRegistry.rod_rbmk * 0.1F, HazardRegistry.wst * HazardRegistry.rod_rbmk * 10F);
		HazardRegistry.registerRBMK(ModItems.rbmk_fuel_drx, HazardRegistry.bf * HazardRegistry.rod_rbmk, HazardRegistry.bf * HazardRegistry.rod_rbmk * 100F, true, true, 0, 1F/3F);
		
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_ueu, HazardRegistry.u * HazardRegistry.billet, HazardRegistry.wst * HazardRegistry.billet * 20F);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_meu, HazardRegistry.uf * HazardRegistry.billet, HazardRegistry.wst * HazardRegistry.billet * 21.5F);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_heu233, HazardRegistry.u233 * HazardRegistry.billet, HazardRegistry.wst * HazardRegistry.billet * 31F);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_heu235, HazardRegistry.u235 * HazardRegistry.billet, HazardRegistry.wst * HazardRegistry.billet * 30F);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_thmeu, HazardRegistry.thf * HazardRegistry.billet, HazardRegistry.wst * HazardRegistry.billet * 17.5F);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_lep, HazardRegistry.puf * HazardRegistry.billet, HazardRegistry.wst * HazardRegistry.billet * 25F);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_mep, HazardRegistry.purg * HazardRegistry.billet, HazardRegistry.wst * HazardRegistry.billet * 30F);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_hep239, HazardRegistry.pu239 * HazardRegistry.billet, HazardRegistry.wst * HazardRegistry.billet * 32.5F);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_hep241, HazardRegistry.pu241 * HazardRegistry.billet, HazardRegistry.wst * HazardRegistry.billet * 35F);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_lea, HazardRegistry.amf * HazardRegistry.billet, HazardRegistry.wst * HazardRegistry.billet * 26F);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_mea, HazardRegistry.amrg * HazardRegistry.billet, HazardRegistry.wst * HazardRegistry.billet * 30.5F);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_hea241, HazardRegistry.am241 * HazardRegistry.billet, HazardRegistry.wst * HazardRegistry.billet * 33.5F);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_hea242, HazardRegistry.am242 * HazardRegistry.billet, HazardRegistry.wst * HazardRegistry.billet * 34F);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_men, HazardRegistry.npf * HazardRegistry.billet, HazardRegistry.wst * HazardRegistry.billet * 22.5F);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_hen, HazardRegistry.np237 * HazardRegistry.billet, HazardRegistry.wst * HazardRegistry.billet * 30F);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_mox, HazardRegistry.mox * HazardRegistry.billet, HazardRegistry.wst * HazardRegistry.billet * 25.5F);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_les, HazardRegistry.saf * HazardRegistry.billet, HazardRegistry.wst * HazardRegistry.billet * 24.5F);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_mes, HazardRegistry.saf * HazardRegistry.billet, HazardRegistry.wst * HazardRegistry.billet * 30F);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_hes, HazardRegistry.saf * HazardRegistry.billet, HazardRegistry.wst * HazardRegistry.billet * 50F);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_leaus, 0F, HazardRegistry.wst * HazardRegistry.billet * 37.5F);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_heaus, 0F, HazardRegistry.wst * HazardRegistry.billet * 32.5F);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_po210be, HazardRegistry.pobe * HazardRegistry.billet, HazardRegistry.pobe * HazardRegistry.billet * 0.1F, true);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_ra226be, HazardRegistry.rabe * HazardRegistry.billet, HazardRegistry.rabe * HazardRegistry.billet * 0.4F, true);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_pu238be, HazardRegistry.pube * HazardRegistry.billet, HazardRegistry.wst * 1.5F);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_balefire_gold, HazardRegistry.au198 * HazardRegistry.billet, HazardRegistry.bf * HazardRegistry.billet * 0.5F, true);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_flashlead, HazardRegistry.pb209 * 1.25F * HazardRegistry.billet, HazardRegistry.pb209 * HazardRegistry.nugget * 0.05F, true);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_balefire, HazardRegistry.bf * HazardRegistry.billet, HazardRegistry.bf * HazardRegistry.billet * 100F, true);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_zfb_bismuth, HazardRegistry.pu241 * HazardRegistry.billet * 0.1F, HazardRegistry.wst * HazardRegistry.billet * 5F);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_zfb_pu241, HazardRegistry.pu239 * HazardRegistry.billet * 0.1F, HazardRegistry.wst * HazardRegistry.billet * 7.5F);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_zfb_am_mix, HazardRegistry.pu241 * HazardRegistry.billet * 0.1F, HazardRegistry.wst * HazardRegistry.billet * 10F);
		HazardRegistry.registerRBMKPellet(ModItems.rbmk_pellet_drx, HazardRegistry.bf * HazardRegistry.billet, HazardRegistry.bf * HazardRegistry.billet * 100F, true, 0F, 1F/24F);
		
		HazardSystem.register(DictFrame.fromOne(ModItems.watz_pellet, EnumWatzType.SCHRABIDIUM), HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.sa326 * HazardRegistry.ingot * 4));
		HazardSystem.register(DictFrame.fromOne(ModItems.watz_pellet, EnumWatzType.HES), HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.saf * HazardRegistry.ingot * 4));
		HazardSystem.register(DictFrame.fromOne(ModItems.watz_pellet, EnumWatzType.MES), HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.saf * HazardRegistry.ingot * 4));
		HazardSystem.register(DictFrame.fromOne(ModItems.watz_pellet, EnumWatzType.LES), HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.saf * HazardRegistry.ingot * 4));
		HazardSystem.register(DictFrame.fromOne(ModItems.watz_pellet, EnumWatzType.HEN), HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.np237 * HazardRegistry.ingot * 4));
		HazardSystem.register(DictFrame.fromOne(ModItems.watz_pellet, EnumWatzType.MEU), HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.uf * HazardRegistry.ingot * 4));
		HazardSystem.register(DictFrame.fromOne(ModItems.watz_pellet, EnumWatzType.MEP), HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.purg * HazardRegistry.ingot * 4));
		HazardSystem.register(DictFrame.fromOne(ModItems.watz_pellet, EnumWatzType.DU), HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.u238 * HazardRegistry.ingot * 4));
		HazardSystem.register(DictFrame.fromOne(ModItems.watz_pellet, EnumWatzType.NQD), HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.u235 * HazardRegistry.ingot * 4));
		HazardSystem.register(DictFrame.fromOne(ModItems.watz_pellet, EnumWatzType.NQR), HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.pu239 * HazardRegistry.ingot * 4));
		
		HazardSystem.register(ModItems.powder_yellowcake, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.yc * HazardRegistry.powder));
		HazardSystem.register(ModBlocks.block_yellowcake, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.yc * HazardRegistry.block * HazardRegistry.powder_mult));
		HazardSystem.register(ModItems.fallout, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.fo * HazardRegistry.powder));
		HazardSystem.register(ModBlocks.fallout, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.fo * HazardRegistry.powder * 2));
		HazardSystem.register(ModBlocks.block_fallout, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.yc * HazardRegistry.block * HazardRegistry.powder_mult));
		HazardSystem.register(ModItems.powder_caesium, HazardRegistry.makeData().addEntry(HazardRegistry.HYDROACTIVE, 1F).addEntry(HazardRegistry.HOT, 3F));
		
		HazardSystem.register(ModItems.wire_schrabidium, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.sa326 * HazardRegistry.nugget));
		
		HazardSystem.register(ModBlocks.brick_asbestos, HazardRegistry.makeData(HazardRegistry.ASBESTOS, 1F));
		HazardSystem.register(ModBlocks.tile_lab_broken, HazardRegistry.makeData(HazardRegistry.ASBESTOS, 1F));
		HazardSystem.register(ModItems.powder_coltan_ore, HazardRegistry.makeData(HazardRegistry.ASBESTOS, 3F));
		
		//crystals
		HazardSystem.register(ModItems.crystal_uranium, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.u * HazardRegistry.crystal));
		HazardSystem.register(ModItems.crystal_thorium, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.th232 * HazardRegistry.crystal));
		HazardSystem.register(ModItems.crystal_plutonium, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.pu * HazardRegistry.crystal));
		HazardSystem.register(ModItems.crystal_schraranium, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.sr * HazardRegistry.crystal));
		HazardSystem.register(ModItems.crystal_schrabidium, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.sa326 * HazardRegistry.crystal));
		HazardSystem.register(ModItems.crystal_phosphorus, HazardRegistry.makeData(HazardRegistry.HOT, 2F * HazardRegistry.crystal));
		HazardSystem.register(ModItems.crystal_lithium, HazardRegistry.makeData(HazardRegistry.HYDROACTIVE, 1F * HazardRegistry.crystal));
		HazardSystem.register(ModItems.crystal_trixite, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.trx * HazardRegistry.crystal));
		
		//nuke parts
		HazardSystem.register(ModItems.boy_propellant, HazardRegistry.makeData(HazardRegistry.EXPLOSIVE, 2F));
		
		HazardSystem.register(ModItems.gadget_core, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.pu239 * HazardRegistry.nugget * 10));
		HazardSystem.register(ModItems.boy_target, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.u235 * HazardRegistry.ingot * 2));
		HazardSystem.register(ModItems.boy_bullet, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.u235 * HazardRegistry.ingot));
		HazardSystem.register(ModItems.man_core, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.pu239 * HazardRegistry.nugget * 10));
		HazardSystem.register(ModItems.mike_core, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.u238 * HazardRegistry.nugget * 10));
		HazardSystem.register(ModItems.tsar_core, HazardRegistry.makeData(HazardRegistry.RADIATION, HazardRegistry.pu239 * HazardRegistry.nugget * 15));
		
		HazardSystem.register(ModItems.fleija_propellant, HazardRegistry.makeData().addEntry(HazardRegistry.RADIATION, 15F).addEntry(HazardRegistry.EXPLOSIVE, 8F).addEntry(HazardRegistry.BLINDING, 50F));
		HazardSystem.register(ModItems.fleija_core, HazardRegistry.makeData(HazardRegistry.RADIATION, 10F));
		
		HazardSystem.register(ModItems.solinium_propellant, HazardRegistry.makeData(HazardRegistry.EXPLOSIVE, 10F));
		HazardSystem.register(ModItems.solinium_core, HazardRegistry.makeData().addEntry(HazardRegistry.RADIATION, HazardRegistry.sa327 * HazardRegistry.nugget * 8).addEntry(HazardRegistry.BLINDING, 45F));

		HazardSystem.register(ModBlocks.nuke_fstbmb, HazardRegistry.makeData(HazardRegistry.DIGAMMA, 0.01F));
		HazardSystem.register(DictFrame.fromOne(ModItems.holotape_image, EnumHoloImage.HOLO_RESTORED), HazardRegistry.makeData(HazardRegistry.DIGAMMA, 1F));
		HazardSystem.register(ModItems.holotape_damaged, HazardRegistry.makeData(HazardRegistry.DIGAMMA, 1_000F));
		
		/*
		 * Blacklist
		 */
		for(String ore : OreDictManager.TH232.ores()) HazardSystem.blacklist(ore);
		for(String ore : OreDictManager.U.ores()) HazardSystem.blacklist(ore);

		
		/*
		 * ReC compat
		 */
		Item recWaste = Compat.tryLoadItem(Compat.MOD_REC, "reactorcraft_item_waste");
		if(recWaste != null) {
			for(ReikaIsotope i : ReikaIsotope.values()) {
				if(i.getRad() > 0) {
					HazardSystem.register(new ItemStack(recWaste, 1, i.ordinal()), HazardRegistry.makeData(HazardRegistry.RADIATION, i.getRad()));
				}
			}
		}
		
		if(Compat.isModLoaded(Compat.MOD_GT6)) {
			
			Object[][] data = new Object[][] {
				{"Naquadah", HazardRegistry.u},
				{"Naquadah-Enriched", HazardRegistry.u235},
				{"Naquadria", HazardRegistry.pu239},
			};
			
			for(MaterialShapes shape : MaterialShapes.allShapes) {
				for(String prefix : shape.prefixes) {
					for(Object[] o : data) {
						HazardSystem.register(prefix + o[0], new HazardData().setMutex(0b1).addEntry(new HazardEntry(HazardRegistry.RADIATION, (float) o[1] * shape.q(1) / MaterialShapes.INGOT.q(1))));
					}
				}
			}
		}
	}
	
	public static void registerTrafos() {
		HazardSystem.trafos.add(new HazardTransformerRadiationNBT());
		
		if(!(GeneralConfig.enableLBSM && GeneralConfig.enableLBSMSafeCrates))	HazardSystem.trafos.add(new HazardTransformerRadiationContainer());
		if(!(GeneralConfig.enableLBSM && GeneralConfig.enableLBSMSafeMEDrives))	HazardSystem.trafos.add(new HazardTransformerRadiationME());
	}
	
	private static HazardData makeData() { return new HazardData(); }
	@SuppressWarnings("unused")
	private static HazardData makeData(HazardTypeBase hazard) { return new HazardData().addEntry(hazard); }
	private static HazardData makeData(HazardTypeBase hazard, float level) { return new HazardData().addEntry(hazard, level); }
	@SuppressWarnings("unused")
	private static HazardData makeData(HazardTypeBase hazard, float level, boolean override) { return new HazardData().addEntry(hazard, level, override); }
	
	private static void registerRBMKPellet(Item pellet, float base, float dep) { HazardRegistry.registerRBMKPellet(pellet, base, dep, false, 0F, 0F); }
	private static void registerRBMKPellet(Item pellet, float base, float dep, boolean linear) { HazardRegistry.registerRBMKPellet(pellet, base, dep, linear, 0F, 0F); }
	private static void registerRBMKPellet(Item pellet, float base, float dep, boolean linear, float blinding, float digamma) {
		
		HazardData data = new HazardData();
		data.addEntry(new HazardEntry(HazardRegistry.RADIATION, base).addMod(new HazardModifierRBMKRadiation(dep, linear)));
		if(blinding > 0) data.addEntry(new HazardEntry(HazardRegistry.BLINDING, blinding));
		if(digamma > 0) data.addEntry(new HazardEntry(HazardRegistry.DIGAMMA, digamma));
		HazardSystem.register(pellet, data);
	}
	
	private static void registerRBMKRod(Item rod, float base, float dep) { HazardRegistry.registerRBMK(rod, base, dep, true, false, 0F, 0F); }
	@SuppressWarnings("unused")
	private static void registerRBMKRod(Item rod, float base, float dep, float blinding) { HazardRegistry.registerRBMK(rod, base, dep, true, false, blinding, 0F); }
	private static void registerRBMKRod(Item rod, float base, float dep, boolean linear) { HazardRegistry.registerRBMK(rod, base, dep, true, linear, 0F, 0F); }
	
	private static void registerRBMK(Item rod, float base, float dep, boolean hot, boolean linear, float blinding, float digamma) {
		
		HazardData data = new HazardData();
		data.addEntry(new HazardEntry(HazardRegistry.RADIATION, base).addMod(new HazardModifierRBMKRadiation(dep, linear)));
		if(hot) data.addEntry(new HazardEntry(HazardRegistry.HOT, 0).addMod(new HazardModifierRBMKHot()));
		if(blinding > 0) data.addEntry(new HazardEntry(HazardRegistry.BLINDING, blinding));
		if(digamma > 0) data.addEntry(new HazardEntry(HazardRegistry.DIGAMMA, digamma));
		HazardSystem.register(rod, data);
	}
	
	private static void registerBreedingRodRadiation(BreedingRodType type, float base) {
		HazardSystem.register(new ItemStack(ModItems.rod, 1, type.ordinal()), HazardRegistry.makeData(HazardRegistry.RADIATION, base));
		HazardSystem.register(new ItemStack(ModItems.rod_dual, 1, type.ordinal()), HazardRegistry.makeData(HazardRegistry.RADIATION, base * HazardRegistry.rod_dual));
		HazardSystem.register(new ItemStack(ModItems.rod_quad, 1, type.ordinal()), HazardRegistry.makeData(HazardRegistry.RADIATION, base * HazardRegistry.rod_quad));
	}
	
	private static void registerOtherFuel(Item fuel, float base, float target, boolean blinding) {
		
		HazardData data = new HazardData();
		data.addEntry(new HazardEntry(HazardRegistry.RADIATION, base).addMod(new HazardModifierFuelRadiation(target)));
		if(blinding)
			data.addEntry(HazardRegistry.BLINDING, 20F);
		HazardSystem.register(fuel, data);
	}
	
	private static void registerOtherFuel(Item fuel, int meta, float base, float target, boolean blinding) {
		
		HazardData data = new HazardData();
		data.addEntry(new HazardEntry(HazardRegistry.RADIATION, base).addMod(new HazardModifierFuelRadiation(target)));
		if(blinding)
			data.addEntry(HazardRegistry.BLINDING, 20F);
		HazardSystem.register(new ItemStack(fuel, 1, meta), data);
	}
	
	private static void registerRTGPellet(Item pellet, float base, float target) { HazardRegistry.registerRTGPellet(pellet, base, target, 0, 0); }
	private static void registerRTGPellet(Item pellet, float base, float target, float hot) { HazardRegistry.registerRTGPellet(pellet, base, target, hot, 0); }
	
	private static void registerRTGPellet(Item pellet, float base, float target, float hot, float blinding) {
		HazardData data = new HazardData();
		data.addEntry(new HazardEntry(HazardRegistry.RADIATION, base).addMod(new HazardModifierRTGRadiation(target)));
		if(hot > 0) data.addEntry(new HazardEntry(HazardRegistry.HOT, hot));
		if(blinding > 0) data.addEntry(new HazardEntry(HazardRegistry.BLINDING, blinding));
		HazardSystem.register(pellet, data);
	}
	
	private static void registerOtherWaste(Item waste, float base) {
		HazardSystem.register(new ItemStack(waste, 1, 0), HazardRegistry.makeData(HazardRegistry.RADIATION, base * 0.075F));
		
		HazardData data = new HazardData();
		data.addEntry(new HazardEntry(HazardRegistry.RADIATION, base));
		data.addEntry(new HazardEntry(HazardRegistry.HOT, 5F));
		HazardSystem.register(new ItemStack(waste, 1, 1), data);
	}
	
	private static void registerRadSourceWaste(Item waste, float base) {
		HazardSystem.register(new ItemStack(waste, 1, 0), HazardRegistry.makeData(HazardRegistry.RADIATION, base));
		
		HazardData data = new HazardData();
		data.addEntry(new HazardEntry(HazardRegistry.RADIATION, base));
		data.addEntry(new HazardEntry(HazardRegistry.HOT, 5F));
		HazardSystem.register(new ItemStack(waste, 1, 1), data);
	}
}
