package com.hbm.blocks;

import com.hbm.blocks.BlockEnums.DecoCabinetEnum;
import com.hbm.blocks.BlockEnums.DecoComputerEnum;
import com.hbm.blocks.BlockEnums.EnumCMCircuit;
import com.hbm.blocks.BlockEnums.EnumCMEngines;
import com.hbm.blocks.BlockEnums.EnumCMMaterials;
import com.hbm.blocks.BlockEnums.TileType;
import com.hbm.blocks.bomb.Balefire;
import com.hbm.blocks.bomb.BlockC4;
import com.hbm.blocks.bomb.BlockChargeC4;
import com.hbm.blocks.bomb.BlockChargeDynamite;
import com.hbm.blocks.bomb.BlockChargeMiner;
import com.hbm.blocks.bomb.BlockChargeSemtex;
import com.hbm.blocks.bomb.BlockCloudResidue;
import com.hbm.blocks.bomb.BlockCrashedBomb;
import com.hbm.blocks.bomb.BlockDynamite;
import com.hbm.blocks.bomb.BlockFireworks;
import com.hbm.blocks.bomb.BlockFissureBomb;
import com.hbm.blocks.bomb.BlockPlasticExplosive;
import com.hbm.blocks.bomb.BlockSemtex;
import com.hbm.blocks.bomb.BlockTNT;
import com.hbm.blocks.bomb.BlockTaint;
import com.hbm.blocks.bomb.BlockVolcano;
import com.hbm.blocks.bomb.BombFlameWar;
import com.hbm.blocks.bomb.BombFloat;
import com.hbm.blocks.bomb.BombMulti;
import com.hbm.blocks.bomb.BombThermo;
import com.hbm.blocks.bomb.CheaterVirus;
import com.hbm.blocks.bomb.CheaterVirusSeed;
import com.hbm.blocks.bomb.CompactLauncher;
import com.hbm.blocks.bomb.CrystalPulsar;
import com.hbm.blocks.bomb.CrystalVirus;
import com.hbm.blocks.bomb.DetCord;
import com.hbm.blocks.bomb.DetMiner;
import com.hbm.blocks.bomb.DigammaFlame;
import com.hbm.blocks.bomb.DigammaMatter;
import com.hbm.blocks.bomb.ExplosiveCharge;
import com.hbm.blocks.bomb.Landmine;
import com.hbm.blocks.bomb.LaunchPad;
import com.hbm.blocks.bomb.LaunchTable;
import com.hbm.blocks.bomb.NukeBalefire;
import com.hbm.blocks.bomb.NukeBoy;
import com.hbm.blocks.bomb.NukeCustom;
import com.hbm.blocks.bomb.NukeFleija;
import com.hbm.blocks.bomb.NukeGadget;
import com.hbm.blocks.bomb.NukeMan;
import com.hbm.blocks.bomb.NukeMike;
import com.hbm.blocks.bomb.NukeN2;
import com.hbm.blocks.bomb.NukeN45;
import com.hbm.blocks.bomb.NukePrototype;
import com.hbm.blocks.bomb.NukeSolinium;
import com.hbm.blocks.bomb.NukeTsar;
import com.hbm.blocks.fluid.AcidBlock;
import com.hbm.blocks.fluid.AcidFluid;
import com.hbm.blocks.fluid.CoriumFinite;
import com.hbm.blocks.fluid.CoriumFluid;
import com.hbm.blocks.fluid.GenericFiniteFluid;
import com.hbm.blocks.fluid.GenericFluid;
import com.hbm.blocks.fluid.GenericFluidBlock;
import com.hbm.blocks.fluid.MudBlock;
import com.hbm.blocks.fluid.MudFluid;
import com.hbm.blocks.fluid.SchrabidicBlock;
import com.hbm.blocks.fluid.SchrabidicFluid;
import com.hbm.blocks.fluid.ToxicBlock;
import com.hbm.blocks.fluid.ToxicFluid;
import com.hbm.blocks.fluid.VolcanicBlock;
import com.hbm.blocks.fluid.VolcanicFluid;
import com.hbm.blocks.gas.BlockGasAsbestos;
import com.hbm.blocks.gas.BlockGasClorine;
import com.hbm.blocks.gas.BlockGasCoal;
import com.hbm.blocks.gas.BlockGasExplosive;
import com.hbm.blocks.gas.BlockGasFlammable;
import com.hbm.blocks.gas.BlockGasMeltdown;
import com.hbm.blocks.gas.BlockGasMonoxide;
import com.hbm.blocks.gas.BlockGasRadon;
import com.hbm.blocks.gas.BlockGasRadonDense;
import com.hbm.blocks.gas.BlockGasRadonTomb;
import com.hbm.blocks.gas.BlockVacuum;
import com.hbm.blocks.generic.*;
import com.hbm.blocks.generic.BlockHazard.ExtDisplayEffect;
import com.hbm.blocks.generic.BlockMotherOfAllOres.ItemRandomOreBlock;
import com.hbm.blocks.machine.*;
import com.hbm.blocks.machine.pile.BlockGraphite;
import com.hbm.blocks.machine.pile.BlockGraphiteBreedingFuel;
import com.hbm.blocks.machine.pile.BlockGraphiteBreedingProduct;
import com.hbm.blocks.machine.pile.BlockGraphiteDrilled;
import com.hbm.blocks.machine.pile.BlockGraphiteFuel;
import com.hbm.blocks.machine.pile.BlockGraphiteNeutronDetector;
import com.hbm.blocks.machine.pile.BlockGraphiteRod;
import com.hbm.blocks.machine.pile.BlockGraphiteSource;
import com.hbm.blocks.machine.rbmk.RBMKAbsorber;
import com.hbm.blocks.machine.rbmk.RBMKBlank;
import com.hbm.blocks.machine.rbmk.RBMKBoiler;
import com.hbm.blocks.machine.rbmk.RBMKConsole;
import com.hbm.blocks.machine.rbmk.RBMKControl;
import com.hbm.blocks.machine.rbmk.RBMKControlAuto;
import com.hbm.blocks.machine.rbmk.RBMKCooler;
import com.hbm.blocks.machine.rbmk.RBMKCraneConsole;
import com.hbm.blocks.machine.rbmk.RBMKDebris;
import com.hbm.blocks.machine.rbmk.RBMKDebrisBurning;
import com.hbm.blocks.machine.rbmk.RBMKDebrisDigamma;
import com.hbm.blocks.machine.rbmk.RBMKDebrisRadiating;
import com.hbm.blocks.machine.rbmk.RBMKHeater;
import com.hbm.blocks.machine.rbmk.RBMKHeatex;
import com.hbm.blocks.machine.rbmk.RBMKInlet;
import com.hbm.blocks.machine.rbmk.RBMKLoader;
import com.hbm.blocks.machine.rbmk.RBMKModerator;
import com.hbm.blocks.machine.rbmk.RBMKOutgasser;
import com.hbm.blocks.machine.rbmk.RBMKOutlet;
import com.hbm.blocks.machine.rbmk.RBMKReflector;
import com.hbm.blocks.machine.rbmk.RBMKRod;
import com.hbm.blocks.machine.rbmk.RBMKRodReaSim;
import com.hbm.blocks.machine.rbmk.RBMKStorage;
import com.hbm.blocks.network.BlockCable;
import com.hbm.blocks.network.BlockCableGauge;
import com.hbm.blocks.network.BlockCablePaintable;
import com.hbm.blocks.network.BlockConveyor;
import com.hbm.blocks.network.BlockConveyorChute;
import com.hbm.blocks.network.BlockConveyorDouble;
import com.hbm.blocks.network.BlockConveyorExpress;
import com.hbm.blocks.network.BlockConveyorLift;
import com.hbm.blocks.network.BlockConveyorTriple;
import com.hbm.blocks.network.BlockFluidDuct;
import com.hbm.blocks.network.BlockFluidDuctSolid;
import com.hbm.blocks.network.CableDetector;
import com.hbm.blocks.network.CableDiode;
import com.hbm.blocks.network.CableSwitch;
import com.hbm.blocks.network.ConnectorRedWire;
import com.hbm.blocks.network.CraneBoxer;
import com.hbm.blocks.network.CraneExtractor;
import com.hbm.blocks.network.CraneGrabber;
import com.hbm.blocks.network.CraneInserter;
import com.hbm.blocks.network.CraneRouter;
import com.hbm.blocks.network.CraneSplitter;
import com.hbm.blocks.network.CraneUnboxer;
import com.hbm.blocks.network.DroneCrate;
import com.hbm.blocks.network.DroneDock;
import com.hbm.blocks.network.DroneWaypoint;
import com.hbm.blocks.network.DroneWaypointRequest;
import com.hbm.blocks.network.FluidDuctBox;
import com.hbm.blocks.network.FluidDuctBoxExhaust;
import com.hbm.blocks.network.FluidDuctGauge;
import com.hbm.blocks.network.FluidDuctPaintable;
import com.hbm.blocks.network.FluidDuctStandard;
import com.hbm.blocks.network.FluidSwitch;
import com.hbm.blocks.network.FluidValve;
import com.hbm.blocks.network.PylonLarge;
import com.hbm.blocks.network.PylonRedWire;
import com.hbm.blocks.network.RadioTelex;
import com.hbm.blocks.network.RadioTorchCounter;
import com.hbm.blocks.network.RadioTorchReceiver;
import com.hbm.blocks.network.RadioTorchSender;
import com.hbm.blocks.network.Substation;
import com.hbm.blocks.network.WireCoated;
import com.hbm.blocks.rail.RailNarrowCurve;
import com.hbm.blocks.rail.RailNarrowStraight;
import com.hbm.blocks.rail.RailStandardBuffer;
import com.hbm.blocks.rail.RailStandardCurve;
import com.hbm.blocks.rail.RailStandardRamp;
import com.hbm.blocks.rail.RailStandardStraight;
import com.hbm.blocks.siege.SiegeCircuit;
import com.hbm.blocks.siege.SiegeHole;
import com.hbm.blocks.siege.SiegeInternal;
import com.hbm.blocks.siege.SiegeShield;
import com.hbm.blocks.test.TestBomb;
import com.hbm.blocks.test.TestBombAdvanced;
import com.hbm.blocks.test.TestCT;
import com.hbm.blocks.test.TestCharge;
import com.hbm.blocks.test.TestCore;
import com.hbm.blocks.test.TestEventTester;
import com.hbm.blocks.test.TestNuke;
import com.hbm.blocks.test.TestObjTester;
import com.hbm.blocks.test.TestPipe;
import com.hbm.blocks.test.TestRail;
import com.hbm.blocks.test.TestRender;
import com.hbm.blocks.turret.TurretArty;
import com.hbm.blocks.turret.TurretBrandon;
import com.hbm.blocks.turret.TurretChekhov;
import com.hbm.blocks.turret.TurretFriendly;
import com.hbm.blocks.turret.TurretFritz;
import com.hbm.blocks.turret.TurretHIMARS;
import com.hbm.blocks.turret.TurretHoward;
import com.hbm.blocks.turret.TurretHowardDamaged;
import com.hbm.blocks.turret.TurretJeremy;
import com.hbm.blocks.turret.TurretMaxwell;
import com.hbm.blocks.turret.TurretRichard;
import com.hbm.blocks.turret.TurretSentry;
import com.hbm.blocks.turret.TurretTauon;
import com.hbm.items.block.ItemBlockBase;
import com.hbm.items.block.ItemBlockBlastInfo;
import com.hbm.items.block.ItemBlockColoredConcrete;
import com.hbm.items.block.ItemBlockLore;
import com.hbm.items.block.ItemBlockMeta;
import com.hbm.items.block.ItemBlockNamedMeta;
import com.hbm.items.block.ItemBlockRemap;
import com.hbm.items.block.ItemCustomMachine;
import com.hbm.items.block.ItemGlyphBlock;
import com.hbm.items.block.ItemModSlab;
import com.hbm.items.block.ItemTaintBlock;
import com.hbm.items.block.ItemTrapBlock;
import com.hbm.items.bomb.ItemPrototypeBlock;
import com.hbm.items.special.ItemOreBlock;
import com.hbm.lib.ModDamageSource;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.DoorDecl;
import com.hbm.tileentity.machine.storage.TileEntityFileCabinet;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModBlocks {
	
	public static void mainRegistry() {
		ModBlocks.initializeBlock();
		ModBlocks.registerBlock();
	}
	
	public static Block test_render;
	public static Block test_bomb;
	public static Block test_bomb_advanced;
	public static Block test_nuke;
	public static Block event_tester;
	public static Block obj_tester;
	public static Block test_core;
	public static Block test_charge;
	public static Block test_pipe;
	public static Block test_ct;
	public static Block test_rail;
	public static Block structure_anchor;

	public static Block ore_uranium;
	public static Block ore_uranium_scorched;
	public static Block ore_titanium;
	public static Block ore_sulfur;
	public static Block ore_thorium;
	public static Block ore_niter;
	public static Block ore_copper;
	public static Block ore_tungsten;
	public static Block ore_aluminium;
	public static Block ore_fluorite;
	public static Block ore_lead;
	public static Block ore_schrabidium;
	public static Block ore_beryllium;
	public static Block ore_australium;
	public static Block ore_weidanium;
	public static Block ore_reiium;
	public static Block ore_unobtainium;
	public static Block ore_daffergon;
	public static Block ore_verticium;
	public static Block ore_rare;
	public static Block ore_cobalt;
	public static Block ore_cinnebar;
	public static Block ore_coltan;
	public static Block ore_alexandrite;

	public static Block ore_random;
	public static Block ore_bedrock;
	public static Block ore_volcano;
	
	public static Block ore_nether_coal;
	public static Block ore_nether_smoldering;
	public static Block ore_nether_uranium;
	public static Block ore_nether_uranium_scorched;
	public static Block ore_nether_plutonium;
	public static Block ore_nether_tungsten;
	public static Block ore_nether_sulfur;
	public static Block ore_nether_fire;
	public static Block ore_nether_cobalt;
	public static Block ore_nether_schrabidium;

	public static Block ore_meteor_uranium;
	public static Block ore_meteor_thorium;
	public static Block ore_meteor_titanium;
	public static Block ore_meteor_sulfur;
	public static Block ore_meteor_copper;
	public static Block ore_meteor_tungsten;
	public static Block ore_meteor_aluminium;
	public static Block ore_meteor_lead;
	public static Block ore_meteor_lithium;
	public static Block ore_meteor_starmetal;
	
	public static Block stone_gneiss;
	public static Block ore_gneiss_iron;
	public static Block ore_gneiss_gold;
	public static Block ore_gneiss_uranium;
	public static Block ore_gneiss_uranium_scorched;
	public static Block ore_gneiss_copper;
	public static Block ore_gneiss_asbestos;
	public static Block ore_gneiss_lithium;
	public static Block ore_gneiss_schrabidium;
	public static Block ore_gneiss_rare;
	public static Block ore_gneiss_gas;

	public static Block gneiss_brick;
	public static Block gneiss_tile;
	public static Block gneiss_chiseled;
	
	public static Block stone_depth;
	public static Block ore_depth_cinnebar;
	public static Block ore_depth_zirconium;
	public static Block ore_depth_borax;
	public static Block cluster_depth_iron;
	public static Block cluster_depth_titanium;
	public static Block cluster_depth_tungsten;
	
	public static Block stone_depth_nether;
	public static Block ore_depth_nether_neodymium;

	public static Block stone_porous;
	public static Block stone_resource;
	public static Block stalagmite;
	public static Block stalactite;
	public static Block stone_biome;
	public static Block stone_deep_cobble;

	public static Block depth_brick;
	public static Block depth_tiles;
	public static Block depth_nether_brick;
	public static Block depth_nether_tiles;
	public static Block depth_dnt;

	public static Block basalt;
	public static Block basalt_sulfur;
	public static Block basalt_fluorite;
	public static Block basalt_asbestos;
	public static Block basalt_gem;
	public static Block basalt_smooth;
	public static Block basalt_brick;
	public static Block basalt_polished;
	public static Block basalt_tiles;

	public static Block cluster_iron;
	public static Block cluster_titanium;
	public static Block cluster_aluminium;
	public static Block cluster_copper;

	public static Block ore_oil;
	public static Block ore_oil_empty;
	public static Block ore_oil_sand;
	public static Block ore_bedrock_oil;
	public static Block ore_lignite;
	public static Block ore_asbestos;
	public static Block ore_coal_oil;
	public static Block ore_coal_oil_burning;

	public static Block ore_tikite;

	public static Block crystal_power;
	public static Block crystal_energy;
	public static Block crystal_robust;
	public static Block crystal_trixite;

	public static Block block_thorium;
	public static Block block_thorium_fuel;
	public static Block block_uranium;
	public static Block block_u233;
	public static Block block_u235;
	public static Block block_u238;
	public static Block block_uranium_fuel;
	public static Block block_neptunium;
	public static Block block_polonium;
	public static Block block_mox_fuel;
	public static Block block_plutonium;
	public static Block block_pu238;
	public static Block block_pu239;
	public static Block block_pu240;
	public static Block block_pu_mix;
	public static Block block_plutonium_fuel;
	public static Block block_titanium;
	public static Block block_sulfur;
	public static Block block_niter;
	public static Block block_niter_reinforced;
	public static Block block_copper;
	public static Block block_red_copper;
	public static Block block_tungsten;
	public static Block block_aluminium;
	public static Block block_fluorite;
	public static Block block_steel;
	public static Block block_tcalloy;
	public static Block block_cdalloy;
	public static Block block_lead;
	public static Block block_bismuth;
	public static Block block_cadmium;
	public static Block block_coltan;
	public static Block block_tantalium;
	public static Block block_niobium;
	public static Block block_trinitite;
	public static Block block_waste;
	public static Block block_waste_painted;
	public static Block block_waste_vitrified;
	public static Block ancient_scrap;
	public static Block block_corium;
	public static Block block_corium_cobble;
	public static Block block_scrap;
	public static Block block_electrical_scrap;
	public static Block block_beryllium;
	public static Block block_schraranium;
	public static Block block_schrabidium;
	public static Block block_schrabidate;
	public static Block block_solinium;
	public static Block block_schrabidium_fuel;
	public static Block block_euphemium;
	public static Block block_schrabidium_cluster;
	public static Block block_euphemium_cluster;
	public static Block block_dineutronium;
	public static Block block_advanced_alloy;
	public static Block block_magnetized_tungsten;
	public static Block block_combine_steel;
	public static Block block_desh;
	public static Block block_dura_steel;
	public static Block block_starmetal;
	public static Block block_polymer;
	public static Block block_bakelite;
	public static Block block_rubber;
	public static Block block_yellowcake;
	public static Block block_insulator;
	public static Block block_fiberglass;
	public static Block block_asbestos;
	public static Block block_cobalt;
	public static Block block_lithium;
	public static Block block_zirconium;
	public static Block block_white_phosphorus;
	public static Block block_red_phosphorus;
	public static Block block_fallout;
	public static Block block_foam;
	public static Block block_coke;
	public static Block block_graphite;
	public static Block block_graphite_drilled;
	public static Block block_graphite_fuel;
	public static Block block_graphite_plutonium;
	public static Block block_graphite_rod;
	public static Block block_graphite_source;
	public static Block block_graphite_lithium;
	public static Block block_graphite_tritium;
	public static Block block_graphite_detector;
	public static Block block_boron;
	public static Block block_lanthanium;
	public static Block block_ra226;
	public static Block block_actinium;
	public static Block block_tritium;
	public static Block block_semtex;
	public static Block block_c4;
	public static Block block_smore;
	public static Block block_slag;

	public static Block block_australium;
	public static Block block_weidanium;
	public static Block block_reiium;
	public static Block block_unobtainium;
	public static Block block_daffergon;
	public static Block block_verticium;

	public static Block block_cap_nuka;
	public static Block block_cap_quantum;
	public static Block block_cap_rad;
	public static Block block_cap_sparkle;
	public static Block block_cap_korl;
	public static Block block_cap_fritz;
	public static Block block_cap_sunset;
	public static Block block_cap_star;

	public static Block deco_titanium;
	public static Block deco_red_copper;
	public static Block deco_tungsten;
	public static Block deco_aluminium;
	public static Block deco_steel;
	public static Block deco_lead;
	public static Block deco_beryllium;
	public static Block deco_asbestos;
	public static Block deco_rbmk;
	public static Block deco_rbmk_smooth;

	public static Block deco_emitter;
	public static Block part_emitter;
	public static Block deco_loot;
	public static Block bobblehead;
	public static Block snowglobe;

	public static Block hazmat;

	public static Block gravel_obsidian;
	public static Block gravel_diamond;
	public static Block asphalt;
	public static Block asphalt_light;

	public static Block reinforced_brick;
	public static Block reinforced_ducrete;
	public static Block reinforced_glass;
	public static Block reinforced_glass_pane;
	public static Block reinforced_light;
	public static Block reinforced_sand;
	public static Block reinforced_lamp_off;
	public static Block reinforced_lamp_on;
	public static Block reinforced_laminate;
	public static Block reinforced_laminate_pane;

	public static Block lamp_tritium_green_off;
	public static Block lamp_tritium_green_on;
	public static Block lamp_tritium_blue_off;
	public static Block lamp_tritium_blue_on;

	public static Block lamp_uv_off;
	public static Block lamp_uv_on;
	public static Block lamp_demon;

	public static Block lantern;
	public static Block lantern_behemoth;

	public static Block reinforced_stone;
	public static Block concrete_smooth;
	public static Block concrete_colored;
	public static Block concrete_colored_ext;
	public static Block concrete;
	public static Block concrete_asbestos;
	public static Block concrete_super;
	public static Block concrete_super_broken;
	public static Block ducrete_smooth;
	public static Block ducrete;
	public static Block concrete_pillar;
	public static Block brick_concrete;
	public static Block brick_concrete_mossy;
	public static Block brick_concrete_cracked;
	public static Block brick_concrete_broken;
	public static Block brick_concrete_marked;
	public static Block brick_ducrete;
	public static Block brick_obsidian;
	public static Block brick_light;
	public static Block brick_compound;
	public static Block brick_asbestos;
	public static Block brick_fire;

	public static Block concrete_slab;
	public static Block concrete_double_slab;
	public static Block concrete_brick_slab;
	public static Block concrete_brick_double_slab;
	public static Block brick_slab;
	public static Block brick_double_slab;

	public static Block concrete_smooth_stairs;
	public static Block concrete_stairs;
	public static Block concrete_asbestos_stairs;
	public static Block ducrete_smooth_stairs;
	public static Block ducrete_stairs;
	public static Block brick_concrete_stairs;
	public static Block brick_concrete_mossy_stairs;
	public static Block brick_concrete_cracked_stairs;
	public static Block brick_concrete_broken_stairs;
	public static Block brick_ducrete_stairs;
	public static Block reinforced_stone_stairs;
	public static Block reinforced_brick_stairs;
	public static Block brick_obsidian_stairs;
	public static Block brick_light_stairs;
	public static Block brick_compound_stairs;
	public static Block brick_asbestos_stairs;
	public static Block brick_fire_stairs;

	public static Block cmb_brick;
	public static Block cmb_brick_reinforced;
	
	public static Block vinyl_tile;
	
	public static Block tile_lab;
	public static Block tile_lab_cracked;
	public static Block tile_lab_broken;

	public static Block siege_shield;
	public static Block siege_internal;
	public static Block siege_circuit;
	public static Block siege_emergency;
	public static Block siege_hole;

	public static Block block_meteor;
	public static Block block_meteor_cobble;
	public static Block block_meteor_broken;
	public static Block block_meteor_molten;
	public static Block block_meteor_treasure;
	public static Block meteor_polished;
	public static Block meteor_brick;
	public static Block meteor_brick_mossy;
	public static Block meteor_brick_cracked;
	public static Block meteor_brick_chiseled;
	public static Block meteor_pillar;
	public static Block meteor_spawner;
	public static Block meteor_battery;
	
	public static Block moon_turf;

	public static Block brick_jungle;
	public static Block brick_jungle_cracked;
	public static Block brick_jungle_fragile;
	public static Block brick_jungle_lava;
	public static Block brick_jungle_ooze;
	public static Block brick_jungle_mystic;
	public static Block brick_jungle_trap;
	public static Block brick_jungle_glyph;
	public static Block brick_jungle_circle;

	public static Block brick_dungeon;
	public static Block brick_dungeon_flat;
	public static Block brick_dungeon_tile;
	public static Block brick_dungeon_circle;

	public static Block brick_forgotten;
	
	public static Block deco_computer;
	
	public static Block filing_cabinet;
	
	public static Block tape_recorder;
	public static Block steel_poles;
	public static Block pole_top;
	public static Block pole_satellite_receiver;
	public static Block steel_wall;
	public static Block steel_corner;
	public static Block steel_roof;
	public static Block steel_beam;
	public static Block steel_scaffold;
	public static Block steel_grate;
	public static Block steel_grate_wide;

	public static Block deco_pipe;
	public static Block deco_pipe_rusted;
	public static Block deco_pipe_green;
	public static Block deco_pipe_green_rusted;
	public static Block deco_pipe_red;
	public static Block deco_pipe_marked;
	public static Block deco_pipe_rim;
	public static Block deco_pipe_rim_rusted;
	public static Block deco_pipe_rim_green;
	public static Block deco_pipe_rim_green_rusted;
	public static Block deco_pipe_rim_red;
	public static Block deco_pipe_rim_marked;
	public static Block deco_pipe_framed;
	public static Block deco_pipe_framed_rusted;
	public static Block deco_pipe_framed_green;
	public static Block deco_pipe_framed_green_rusted;
	public static Block deco_pipe_framed_red;
	public static Block deco_pipe_framed_marked;
	public static Block deco_pipe_quad;
	public static Block deco_pipe_quad_rusted;
	public static Block deco_pipe_quad_green;
	public static Block deco_pipe_quad_green_rusted;
	public static Block deco_pipe_quad_red;
	public static Block deco_pipe_quad_marked;

	public static Block broadcaster_pc;
	public static Block geiger;
	public static Block hev_battery;

	public static Block fence_metal;

	public static Block sand_boron;
	public static Block sand_lead;
	public static Block sand_uranium;
	public static Block sand_polonium;
	public static Block sand_quartz;
	public static Block sand_gold;
	public static Block sand_gold198;
	public static Block ash_digamma;
	public static Block glass_boron;
	public static Block glass_lead;
	public static Block glass_uranium;
	public static Block glass_trinitite;
	public static Block glass_polonium;
	public static Block glass_ash;
	public static Block glass_quartz;
	
	public static Block mush;
	public static Block mush_block;
	public static Block mush_block_stem;

	public static Block glyphid_base;
	public static Block glyphid_spawner;

	public static Block plant_flower;
	public static Block plant_tall;
	public static Block plant_dead;
	public static Block reeds;

	public static Block waste_earth;
	public static Block waste_mycelium;
	public static Block waste_trinitite;
	public static Block waste_trinitite_red;
	public static Block waste_log;
	public static Block waste_leaves;
	public static Block waste_planks;
	public static Block frozen_dirt;
	public static Block frozen_grass;
	public static Block frozen_log;
	public static Block frozen_planks;
	public static Block dirt_dead;
	public static Block dirt_oily;
	public static Block sand_dirty;
	public static Block sand_dirty_red;
	public static Block stone_cracked;
	public static Block burning_earth;
	public static Block tektite;
	public static Block ore_tektite_osmiridium;
	public static Block impact_dirt;
	
	public static Block fallout;
	public static Block foam_layer;
	public static Block sand_boron_layer;
	public static Block leaves_layer;

	public static Block sellafield_slaked;
	public static Block sellafield;
	/*public static Block sellafield_0;
	public static Block sellafield_1;
	public static Block sellafield_2;
	public static Block sellafield_3;
	public static Block sellafield_4;
	public static Block sellafield_core;*/

	public static Block geysir_water;
	public static Block geysir_chlorine;
	public static Block geysir_vapor;
	public static Block geysir_nether;

	public static Block observer_off;
	public static Block observer_on;

	public static Block flame_war;
	public static Block float_bomb;
	public static Block therm_endo;
	public static Block therm_exo;
	public static Block emp_bomb;
	public static Block det_cord;
	public static Block det_charge;
	public static Block det_nuke;
	public static Block det_miner;
	public static Block red_barrel;
	public static Block pink_barrel;
	public static Block yellow_barrel;
	public static Block vitrified_barrel;
	public static Block lox_barrel;
	public static Block taint_barrel;
	public static Block crashed_balefire;
	public static Block rejuvinator;
	public static Block fireworks;
	public static Block dynamite;
	public static Block tnt;
	public static Block semtex;
	public static Block c4;
	public static Block fissure_bomb;

	public static Block charge_dynamite;
	public static Block charge_miner;
	public static Block charge_c4;
	public static Block charge_semtex;
	
	public static Block mine_ap;
	public static Block mine_he;
	public static Block mine_shrap;
	public static Block mine_fat;
	
	public static Block crate;
	public static Block crate_weapon;
	public static Block crate_lead;
	public static Block crate_metal;
	public static Block crate_red;
	public static Block crate_can;
	public static Block crate_ammo;
	public static Block crate_jungle;

	public static Block boxcar;
	public static Block boat;
	public static Block bomber;

	public static Block seal_frame;
	public static Block seal_controller;
	public static Block seal_hatch;

	public static Block vault_door;
	public static Block blast_door;
	public static Block sliding_blast_door;
	public static Block sliding_blast_door_2;
	public static Block sliding_blast_door_keypad;
	public static Block fire_door;
	public static Block transition_seal;

	public static Block door_metal;
	public static Block door_office;
	public static Block door_bunker;

	public static Block barbed_wire;
	public static Block barbed_wire_fire;
	public static Block barbed_wire_poison;
	public static Block barbed_wire_acid;
	public static Block barbed_wire_wither;
	public static Block barbed_wire_ultradeath;
	public static Block spikes;

	public static Block charger;
	
	public static Block tesla;

	public static Block marker_structure;

	public static Block muffler;

	public static Block sat_mapper;
	public static Block sat_scanner;
	public static Block sat_radar;
	public static Block sat_laser;
	public static Block sat_foeq;
	public static Block sat_resonator;

	public static Block sat_dock;
	
	public static Block soyuz_capsule;

	public static Block crate_iron;
	public static Block crate_steel;
	public static Block crate_desh;
	public static Block crate_tungsten;
	public static Block crate_template;
	public static Block safe;
	public static Block mass_storage;
	
	public static Block nuke_gadget;
	public static Block nuke_boy;
	public static Block nuke_man;
	public static Block nuke_mike;
	public static Block nuke_tsar;
	public static Block nuke_fleija;
	public static Block nuke_prototype;
	public static Block nuke_custom;
	public static Block nuke_solinium;
	public static Block nuke_n2;
	public static Block nuke_n45;
	public static Block nuke_fstbmb;
	public static Block bomb_multi;

	public static Block pump_steam;
	public static Block pump_electric;
	
	public static Block heater_firebox;
	public static Block heater_oven;
	public static Block heater_oilburner;
	public static Block heater_electric;
	public static Block heater_heatex;
	public static Block machine_ashpit;

	public static Block furnace_iron;
	public static Block furnace_steel;
	public static Block furnace_combination;
	public static Block machine_stirling;
	public static Block machine_stirling_steel;
	public static Block machine_stirling_creative;
	public static Block machine_sawmill;
	public static Block machine_crucible;
	public static Block machine_boiler;
	public static Block machine_industrial_boiler;

	public static Block foundry_mold;
	public static Block foundry_basin;
	public static Block foundry_channel;
	public static Block foundry_tank;
	public static Block foundry_outlet;
	public static Block foundry_slagtap;
	public static Block slag;
	
	public static Block machine_difurnace_off;
	public static Block machine_difurnace_on;
	public static Block machine_difurnace_extension;
	public static Block machine_difurnace_rtg_off;
	public static Block machine_difurnace_rtg_on;
	//public static final int guiID_test_difurnace = 1; historical
	
	public static Block machine_centrifuge;
	public static Block machine_gascent;
	
	public static Block machine_fel;
	public static Block machine_silex;
	
	public static Block machine_crystallizer;
	
	public static Block machine_uf6_tank;
	
	public static Block machine_puf6_tank;

	public static Block machine_reactor_breeding;
	
	public static Block machine_nuke_furnace_off;
	public static Block machine_nuke_furnace_on;
	
	public static Block machine_rtg_furnace_off;
	public static Block machine_rtg_furnace_on;
	
	public static Block machine_generator;

	public static Block machine_industrial_generator;
	
	public static Block machine_cyclotron;
	
	public static Block hadron_plating;
	public static Block hadron_plating_blue;
	public static Block hadron_plating_black;
	public static Block hadron_plating_yellow;
	public static Block hadron_plating_striped;
	public static Block hadron_plating_voltz;
	public static Block hadron_plating_glass;
	public static Block hadron_coil_alloy;
	public static Block hadron_coil_gold;
	public static Block hadron_coil_neodymium;
	public static Block hadron_coil_magtung;
	public static Block hadron_coil_schrabidium;
	public static Block hadron_coil_schrabidate;
	public static Block hadron_coil_starmetal;
	public static Block hadron_coil_chlorophyte;
	public static Block hadron_coil_mese;
	public static Block hadron_power;
	public static Block hadron_power_10m;
	public static Block hadron_power_100m;
	public static Block hadron_power_1g;
	public static Block hadron_power_10g;
	public static Block hadron_diode;
	public static Block hadron_analysis;
	public static Block hadron_analysis_glass;
	public static Block hadron_access;
	public static Block hadron_core;
	public static Block hadron_cooler;
	
	public static Block machine_electric_furnace_off;
	public static Block machine_electric_furnace_on;
	
	public static Block machine_microwave;
	
	public static Block machine_arc_furnace_off;
	public static Block machine_arc_furnace_on;
	
	//public static Block machine_deuterium;
	
	public static Block machine_battery_potato;
	public static Block machine_battery;
	public static Block machine_lithium_battery;
	public static Block machine_schrabidium_battery;
	public static Block machine_dineutronium_battery;
	public static Block machine_fensu;
	public static final int guiID_machine_fensu = 99;

	public static Block capacitor_bus;
	public static Block capacitor_copper;
	public static Block capacitor_gold;
	public static Block capacitor_niobium;
	public static Block capacitor_tantalium;
	public static Block capacitor_schrabidate;
	
	@Deprecated public static Block machine_coal_off;
	@Deprecated public static Block machine_coal_on;
	public static Block machine_wood_burner;
	
	public static Block red_wire_coated;
	public static Block red_cable;
	public static Block red_cable_classic;
	public static Block red_cable_paintable;
	public static Block red_cable_gauge;
	public static Block red_connector;
	public static Block red_pylon;
	public static Block red_pylon_large;
	public static Block substation;
	public static Block cable_switch;
	public static Block cable_detector;
	public static Block cable_diode;
	public static Block machine_detector;
	public static Block fluid_duct;
	public static Block fluid_duct_solid;
	public static Block fluid_duct_neo;
	public static Block fluid_duct_box;
	public static Block fluid_duct_paintable;
	public static Block fluid_duct_gauge;
	public static Block fluid_duct_exhaust;
	public static Block fluid_valve;
	public static Block fluid_switch;
	public static Block radio_torch_sender;
	public static Block radio_torch_receiver;
	public static Block radio_torch_counter;
	public static Block radio_telex;

	public static Block conveyor;
	public static Block conveyor_express;
	//public static Block conveyor_classic;
	public static Block conveyor_double;
	public static Block conveyor_triple;
	public static Block conveyor_chute;
	public static Block conveyor_lift;
	public static Block crane_extractor;
	public static Block crane_inserter;
	public static Block crane_grabber;
	public static Block crane_router;
	public static Block crane_boxer;
	public static Block crane_unboxer;
	public static Block crane_splitter;

	public static Block drone_waypoint;
	public static Block drone_crate;
	public static Block drone_waypoint_request;
	public static Block drone_dock;
	public static Block drone_crate_provider;
	public static Block drone_crate_requester;
	
	public static Block fan;
	
	public static Block piston_inserter;

	public static Block chain;

	public static Block ladder_sturdy;
	public static Block ladder_iron;
	public static Block ladder_gold;
	public static Block ladder_aluminium;
	public static Block ladder_copper;
	public static Block ladder_titanium;
	public static Block ladder_lead;
	public static Block ladder_cobalt;
	public static Block ladder_steel;
	public static Block ladder_tungsten;

	public static Block barrel_plastic;
	public static Block barrel_corroded;
	public static Block barrel_iron;
	public static Block barrel_steel;
	public static Block barrel_tcalloy;
	public static Block barrel_antimatter;
	
	public static Block machine_transformer;
	public static Block machine_transformer_20;
	public static Block machine_transformer_dnt;
	public static Block machine_transformer_dnt_20;

	public static Block bomb_multi_large;
	public static final int guiID_bomb_multi_large = 18;

	public static Block machine_solar_boiler;
	public static final int guiID_solar_boiler = 18;
	public static Block solar_mirror;

	public static Block struct_launcher;
	public static Block struct_scaffold;
	public static Block struct_launcher_core;
	public static Block struct_launcher_core_large;
	public static Block struct_soyuz_core;
	public static Block struct_iter_core;
	public static Block struct_plasma_core;
	public static Block struct_watz_core;
	
	public static Block factory_titanium_hull;
	@Deprecated public static Block factory_titanium_furnace;
	@Deprecated public static Block factory_titanium_conductor;
	
	public static Block factory_advanced_hull;
	@Deprecated public static Block factory_advanced_furnace;
	@Deprecated public static Block factory_advanced_conductor;

	public static Block cm_block;
	public static Block cm_sheet;
	public static Block cm_engine;
	public static Block cm_tank;
	public static Block cm_circuit;
	public static Block cm_port;
	public static Block custom_machine;
	public static Block cm_anchor;
	
	public static Block pwr_fuel;
	public static Block pwr_control;
	public static Block pwr_channel;
	public static Block pwr_heatex;
	public static Block pwr_neutron_source;
	public static Block pwr_reflector;
	public static Block pwr_casing;
	public static Block pwr_port;
	public static Block pwr_controller;
	public static Block pwr_block;

	@Deprecated public static Block reactor_element;
	@Deprecated public static Block reactor_control;
	@Deprecated public static Block reactor_hatch;
	@Deprecated public static Block reactor_ejector;
	@Deprecated public static Block reactor_inserter;
	@Deprecated public static Block reactor_conductor;
	@Deprecated public static Block reactor_computer;
	
	public static Block fusion_conductor;
	public static Block fusion_center;
	public static Block fusion_motor;
	public static Block fusion_heater;
	public static Block fusion_hatch;
	//public static Block fusion_core;
	public static Block plasma;

	public static Block iter;
	public static Block plasma_heater;

	public static Block watz;
	public static Block watz_pump;

	public static Block watz_element;
	public static Block watz_control;
	public static Block watz_cooler;
	public static Block watz_end;
	public static Block watz_hatch;
	public static Block watz_conductor;
	public static Block watz_core;
	
	public static Block fwatz_conductor;
	public static Block fwatz_cooler;
	public static Block fwatz_tank;
	public static Block fwatz_scaffold;
	public static Block fwatz_hatch;
	public static Block fwatz_computer;
	public static Block fwatz_core;
	public static Block fwatz_plasma;

	public static Block balefire;
	public static Block fire_digamma;
	public static Block digamma_matter;

	public static Block ams_base;
	public static Block ams_emitter;
	public static Block ams_limiter;
	
	public static Block dfc_emitter;
	public static Block dfc_injector;
	public static Block dfc_receiver;
	public static Block dfc_stabilizer;
	public static Block dfc_core;
	
	public static Block machine_converter_he_rf;
	public static final int guiID_converter_he_rf = 28;
	public static Block machine_converter_rf_he;

	public static Block machine_schrabidium_transmutator;
	
	public static Block machine_diesel;
	public static Block machine_combustion_engine;

	public static Block machine_shredder;

	public static Block machine_shredder_large;
	public static final int guiID_machine_shredder_large = 76;

	public static Block machine_combine_factory;
	
	public static Block machine_teleporter;
	public static Block teleanchor;
	public static Block field_disturber;

	public static Block machine_rtg_grey;
	public static Block machine_amgen;
	public static Block machine_geo;
	public static Block machine_minirtg;
	public static Block machine_powerrtg;
	public static Block machine_radiolysis;
	public static Block machine_hephaestus;
	
	public static Block machine_well;
	public static Block oil_pipe;
	public static Block machine_pumpjack;
	public static Block machine_fracking_tower;

	public static Block machine_flare;
	public static Block chimney_brick;
	public static Block chimney_industrial;
	
	public static Block machine_refinery;
	public static Block machine_vacuum_distill;
	public static Block machine_fraction_tower;
	public static Block fraction_spacer;
	public static Block machine_catalytic_cracker;
	public static Block machine_catalytic_reformer;
	public static Block machine_coker;

	public static Block machine_boiler_off;
	public static Block machine_boiler_on;
	
	public static Block machine_boiler_electric_off;
	public static Block machine_boiler_electric_on;
	
	public static Block machine_steam_engine;
	public static Block machine_turbine;
	public static Block machine_large_turbine;
	
	public static Block machine_deuterium_extractor;
	public static Block machine_deuterium_tower;

	public static Block machine_liquefactor;
	public static Block machine_solidifier;
	public static Block machine_compressor;

	public static Block machine_chungus;
	public static Block machine_condenser;
	public static Block machine_tower_small;
	public static Block machine_tower_large;
	public static Block machine_condenser_powered;
	
	public static Block machine_electrolyser;

	public static Block machine_deaerator;
	public static final int guiID_machine_deaerator = 74;

	public static Block machine_drill;
	public static Block drill_pipe;
	public static Block machine_excavator;
	
	public static Block machine_autosaw;

	public static Block machine_mining_laser;
	public static Block barricade; // a sand bag that drops nothing, for automated walling purposes
	
	public static Block machine_assembler;
	public static Block machine_assemfac;
	public static Block machine_arc_welder;

	public static Block machine_chemplant;
	public static Block machine_chemfac;
	public static Block machine_mixer;

	public static Block machine_fluidtank;
	public static Block machine_bat9000;
	public static Block machine_orbus;

	public static Block launch_pad;
	
	public static Block machine_missile_assembly;
	
	public static Block compact_launcher;
	
	public static Block launch_table;
	
	public static Block soyuz_launcher;
	
	public static Block machine_radar;
	
	public static Block machine_turbofan;
	public static Block machine_turbinegas;

	public static Block machine_selenium;
	
	public static Block press_preheater;
	public static Block machine_press;
	public static Block machine_epress;
	public static Block machine_conveyor_press;
	
	public static Block machine_siren;
	
	public static Block machine_radgen;
	
	public static Block machine_satlinker;
	public static Block machine_keyforge;
	
	public static Block machine_armor_table;
	
	public static Block reactor_research;
	public static Block reactor_zirnox;
	public static Block zirnox_destroyed;

	public static Block machine_controller;
	
	public static Block machine_spp_bottom;
	public static Block machine_spp_top;

	public static Block radiobox;
	public static Block radiorec;

	public static Block machine_forcefield;
	
	public static Block machine_waste_drum;
	public static Block machine_storage_drum;
	
	public static Block machine_autocrafter;
	
	public static Block anvil_iron;
	public static Block anvil_lead;
	public static Block anvil_steel;
	public static Block anvil_meteorite;
	public static Block anvil_starmetal;
	public static Block anvil_ferrouranium;
	public static Block anvil_bismuth;
	public static Block anvil_schrabidate;
	public static Block anvil_dnt;
	public static Block anvil_osmiridium;
	public static Block anvil_murky;
	
	public static Block turret_chekhov;
	public static Block turret_friendly;
	public static Block turret_jeremy;
	public static Block turret_tauon;
	public static Block turret_richard;
	public static Block turret_howard;
	public static Block turret_howard_damaged;
	public static Block turret_maxwell;
	public static Block turret_fritz;
	public static Block turret_brandon;
	public static Block turret_arty;
	public static Block turret_himars;
	public static Block turret_sentry;

	public static Block rbmk_rod;
	public static Block rbmk_rod_mod;
	public static Block rbmk_rod_reasim;
	public static Block rbmk_rod_reasim_mod;
	public static Block rbmk_control;
	public static Block rbmk_control_mod;
	public static Block rbmk_control_auto;
	public static Block rbmk_blank;
	public static Block rbmk_boiler;
	public static Block rbmk_reflector;
	public static Block rbmk_absorber;
	public static Block rbmk_moderator;
	public static Block rbmk_outgasser;
	public static Block rbmk_storage;
	public static Block rbmk_cooler;
	public static Block rbmk_heater;
	public static Block rbmk_console;
	public static Block rbmk_crane_console;
	public static Block rbmk_loader;
	public static Block rbmk_steam_inlet;
	public static Block rbmk_steam_outlet;
	public static Block rbmk_heatex;
	public static Block pribris;
	public static Block pribris_burning;
	public static Block pribris_radiating;
	public static Block pribris_digamma;

	public static Block book_guide;

	public static Block rail_wood;
	public static Block rail_narrow;
	public static Block rail_highspeed;
	public static Block rail_booster;

	public static Block rail_narrow_straight;
	public static Block rail_narrow_curve;
	public static Block rail_large_straight;
	public static Block rail_large_curve;
	public static Block rail_large_ramp;
	public static Block rail_large_buffer;
	
	public static Block statue_elb;
	public static Block statue_elb_g;
	public static Block statue_elb_w;
	public static Block statue_elb_f;

	public static Block cheater_virus;
	public static Block cheater_virus_seed;
	public static Block crystal_virus;
	public static Block crystal_hardened;
	public static Block crystal_pulsar;
	public static Block taint;
	public static Block residue;

	public static Block vent_chlorine;
	public static Block vent_cloud;
	public static Block vent_pink_cloud;
	public static Block vent_chlorine_seal;
	public static Block chlorine_gas;

	public static Block gas_radon;
	public static Block gas_radon_dense;
	public static Block gas_radon_tomb;
	public static Block gas_meltdown;
	public static Block gas_monoxide;
	public static Block gas_asbestos;
	public static Block gas_coal;
	public static Block gas_flammable;
	public static Block gas_explosive;
	public static Block vacuum;

	public static Block absorber;
	public static Block absorber_red;
	public static Block absorber_green;
	public static Block absorber_pink;
	public static Block decon;
	public static Block transission_hatch;

	public static Block mud_block;
	public static Fluid mud_fluid;
	public static final Material fluidmud = (new MaterialLiquid(MapColor.adobeColor));

	public static Block acid_block;
	public static Fluid acid_fluid;
	public static final Material fluidacid = (new MaterialLiquid(MapColor.purpleColor));

	public static Block toxic_block;
	public static Fluid toxic_fluid;
	public static final Material fluidtoxic = (new MaterialLiquid(MapColor.greenColor));

	public static Block schrabidic_block;
	public static Fluid schrabidic_fluid;
	public static final Material fluidschrabidic = (new MaterialLiquid(MapColor.cyanColor));

	public static Block corium_block;
	public static Fluid corium_fluid;
	public static final Material fluidcorium = (new MaterialLiquid(MapColor.brownColor) {
		
		@Override
		public boolean blocksMovement() {
			return true;
		}
		
		@Override
		public Material setImmovableMobility() {
			return super.setImmovableMobility();
		}
		
	}.setImmovableMobility());

	public static Block volcanic_lava_block;
	public static Fluid volcanic_lava_fluid;
	public static final Material fluidvolcanic = (new MaterialLiquid(MapColor.redColor));

	public static Block sulfuric_acid_block;
	public static Fluid sulfuric_acid_fluid;

	public static Block concrete_liquid;

	public static Block volcano_core;

	public static Block dummy_block_drill;
	public static Block dummy_port_drill;
	public static Block dummy_block_ams_limiter;
	public static Block dummy_port_ams_limiter;
	public static Block dummy_block_ams_emitter;
	public static Block dummy_port_ams_emitter;
	public static Block dummy_block_ams_base;
	public static Block dummy_port_ams_base;
	public static Block dummy_block_vault;
	public static Block dummy_block_blast;
	public static Block dummy_block_uf6;
	public static Block dummy_block_puf6;
	public static Block dummy_plate_compact_launcher;
	public static Block dummy_port_compact_launcher;
	public static Block dummy_plate_launch_table;
	public static Block dummy_port_launch_table;
	public static Block dummy_plate_cargo;

	public static Block ntm_dirt;

	public static Block pink_log;
	public static Block pink_planks;
	public static Block pink_slab;
	public static Block pink_double_slab;
	public static Block pink_stairs;
	
	public static Block ff;
	
	public static Material materialGas = new MaterialGas();
	
	@SuppressWarnings("deprecation")
	private static void initializeBlock() {
		
		ModBlocks.test_render = new TestRender(Material.rock).setBlockName("test_render").setCreativeTab(null);
		ModBlocks.test_bomb = new TestBomb(Material.tnt).setBlockName("test_bomb").setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":test_bomb");
		ModBlocks.test_bomb_advanced = new TestBombAdvanced(Material.tnt).setBlockName("test_bomb_advanced").setCreativeTab(null);
		ModBlocks.test_nuke = new TestNuke(Material.iron).setBlockName("test_nuke").setCreativeTab(null).setHardness(2.5F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":test_nuke");
		ModBlocks.event_tester = new TestEventTester(Material.iron).setBlockName("event_tester").setCreativeTab(null).setHardness(2.5F).setResistance(0.0F).setBlockTextureName(RefStrings.MODID + ":event_tester");
		ModBlocks.obj_tester = new TestObjTester(Material.iron).setBlockName("obj_tester").setCreativeTab(null).setHardness(2.5F).setResistance(10.0F);
		ModBlocks.test_core = new TestCore(Material.iron).setBlockName("test_core").setCreativeTab(null).setHardness(2.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":test_core");
		ModBlocks.test_charge = new TestCharge(Material.iron).setBlockName("test_charge").setCreativeTab(null).setHardness(2.5F).setResistance(10.0F);
		ModBlocks.test_pipe = new TestPipe(Material.iron).setBlockName("test_pipe").setCreativeTab(null).setHardness(2.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":pipe_neo");
		ModBlocks.test_ct = new TestCT(Material.iron).setBlockName("test_ct").setCreativeTab(null).setHardness(2.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":test_ct");
		ModBlocks.test_rail = new TestRail(Material.iron).setBlockName("test_rail").setCreativeTab(null).setHardness(2.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":test_rail");
		ModBlocks.structure_anchor = new BlockGeneric(Material.iron).setBlockName("structure_anchor").setCreativeTab(null).setHardness(2.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":structure_anchor");
		
		ModBlocks.ore_uranium = new BlockOutgas(Material.rock, true, 5, true).setBlockName("ore_uranium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_uranium");
		ModBlocks.ore_uranium_scorched = new BlockOutgas(Material.rock, true, 5, true).setBlockName("ore_uranium_scorched").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_uranium_scorched");
		ModBlocks.ore_titanium = new BlockGeneric(Material.rock).setBlockName("ore_titanium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_titanium");
		ModBlocks.ore_sulfur = new BlockOre(Material.rock).setBlockName("ore_sulfur").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_sulfur");
		ModBlocks.ore_thorium = new BlockGeneric(Material.rock).setBlockName("ore_thorium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_thorium");
		
		ModBlocks.ore_niter = new BlockOre(Material.rock).setBlockName("ore_niter").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_niter");
		ModBlocks.ore_copper = new BlockGeneric(Material.rock).setBlockName("ore_copper").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_copper");
		ModBlocks.ore_tungsten = new BlockGeneric(Material.rock).setBlockName("ore_tungsten").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_tungsten");
		ModBlocks.ore_aluminium = new BlockGeneric(Material.rock).setBlockName("ore_aluminium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_aluminium");
		ModBlocks.ore_fluorite = new BlockOre(Material.rock).setBlockName("ore_fluorite").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_fluorite");
		ModBlocks.ore_lead = new BlockGeneric(Material.rock).setBlockName("ore_lead").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_lead");
		ModBlocks.ore_schrabidium = new BlockOre(Material.rock, 0.1F, 0.5F).setBlockName("ore_schrabidium").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":ore_schrabidium");
//		ModBlocks.ore_schrabidium = new BlockHazard(Material.rock, 0.1F).setBlockName("ore_schrabidium").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":ore_schrabidium");
		ModBlocks.ore_beryllium = new BlockGeneric(Material.rock).setBlockName("ore_beryllium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":ore_beryllium");
		ModBlocks.ore_lignite = new BlockOre(Material.rock).setBlockName("ore_lignite").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":ore_lignite");
		ModBlocks.ore_asbestos = new BlockOutgas(Material.rock, true, 5, true).setBlockName("ore_asbestos").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":ore_asbestos");
		ModBlocks.ore_coal_oil = new BlockCoalOil(Material.rock).setBlockName("ore_coal_oil").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":ore_coal_oil");
		ModBlocks.ore_coal_oil_burning = new BlockCoalBurning(Material.rock).setBlockName("ore_coal_oil_burning").setCreativeTab(MainRegistry.blockTab).setLightLevel(10F/15F).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":ore_coal_oil_burning");
		
		ModBlocks.cluster_iron = new BlockCluster(Material.rock).setBlockName("cluster_iron").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":cluster_iron");
		ModBlocks.cluster_titanium = new BlockCluster(Material.rock).setBlockName("cluster_titanium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":cluster_titanium");
		ModBlocks.cluster_aluminium = new BlockCluster(Material.rock).setBlockName("cluster_aluminium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":cluster_aluminium");
		ModBlocks.cluster_copper = new BlockCluster(Material.rock).setBlockName("cluster_copper").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":cluster_copper");
		
		ModBlocks.ore_nether_coal = new BlockNetherCoal(Material.rock, false, 5, true).setBlockName("ore_nether_coal").setCreativeTab(MainRegistry.blockTab).setLightLevel(10F/15F).setHardness(0.4F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_coal");
		ModBlocks.ore_nether_smoldering = new BlockSmolder(Material.rock).setBlockName("ore_nether_smoldering").setCreativeTab(MainRegistry.blockTab).setLightLevel(1F).setHardness(0.4F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_smoldering");
		ModBlocks.ore_nether_uranium = new BlockOutgas(Material.rock, true, 5, true).setBlockName("ore_nether_uranium").setCreativeTab(MainRegistry.blockTab).setHardness(0.4F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_uranium");
		ModBlocks.ore_nether_uranium_scorched = new BlockOutgas(Material.rock, true, 5, true).setBlockName("ore_nether_uranium_scorched").setCreativeTab(MainRegistry.blockTab).setHardness(0.4F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_uranium_scorched");
		ModBlocks.ore_nether_plutonium = new BlockGeneric(Material.rock).setBlockName("ore_nether_plutonium").setCreativeTab(MainRegistry.blockTab).setHardness(0.4F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_plutonium");
		ModBlocks.ore_nether_tungsten = new BlockGeneric(Material.rock).setBlockName("ore_nether_tungsten").setCreativeTab(MainRegistry.blockTab).setHardness(0.4F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_tungsten");
		ModBlocks.ore_nether_sulfur = new BlockOre(Material.rock).setBlockName("ore_nether_sulfur").setCreativeTab(MainRegistry.blockTab).setHardness(0.4F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_sulfur");
		ModBlocks.ore_nether_fire = new BlockOre(Material.rock).setBlockName("ore_nether_fire").setCreativeTab(MainRegistry.blockTab).setHardness(0.4F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_fire");
		ModBlocks.ore_nether_cobalt = new BlockOre(Material.rock).setBlockName("ore_nether_cobalt").setCreativeTab(MainRegistry.blockTab).setHardness(0.4F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_cobalt");
		ModBlocks.ore_nether_schrabidium = new BlockGeneric(Material.rock).setBlockName("ore_nether_schrabidium").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_schrabidium");

		ModBlocks.ore_meteor_uranium = new BlockOre(Material.rock).setBlockName("ore_meteor_uranium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_meteor_uranium");
		ModBlocks.ore_meteor_thorium = new BlockOre(Material.rock).setBlockName("ore_meteor_thorium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_meteor_thorium");
		ModBlocks.ore_meteor_titanium = new BlockOre(Material.rock).setBlockName("ore_meteor_titanium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_meteor_titanium");
		ModBlocks.ore_meteor_sulfur = new BlockOre(Material.rock).setBlockName("ore_meteor_sulfur").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_meteor_sulfur");
		ModBlocks.ore_meteor_copper = new BlockOre(Material.rock).setBlockName("ore_meteor_copper").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_meteor_copper");
		ModBlocks.ore_meteor_tungsten = new BlockOre(Material.rock).setBlockName("ore_meteor_tungsten").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_meteor_tungsten");
		ModBlocks.ore_meteor_aluminium = new BlockOre(Material.rock).setBlockName("ore_meteor_aluminium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_meteor_aluminium");
		ModBlocks.ore_meteor_lead = new BlockOre(Material.rock).setBlockName("ore_meteor_lead").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_meteor_lead");
		ModBlocks.ore_meteor_lithium = new BlockOre(Material.rock).setBlockName("ore_meteor_lithium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_meteor_lithium");
		ModBlocks.ore_meteor_starmetal = new BlockOre(Material.rock).setBlockName("ore_meteor_starmetal").setCreativeTab(MainRegistry.blockTab).setHardness(10.0F).setResistance(100.0F).setBlockTextureName(RefStrings.MODID + ":ore_meteor_starmetal");

		ModBlocks.stone_gneiss = new BlockGeneric(Material.rock).setBlockName("stone_gneiss").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":stone_gneiss_var");
		ModBlocks.ore_gneiss_iron = new BlockOre(Material.rock).setBlockName("ore_gneiss_iron").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_gneiss_iron");
		ModBlocks.ore_gneiss_gold = new BlockOre(Material.rock).setBlockName("ore_gneiss_gold").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_gneiss_gold");
		ModBlocks.ore_gneiss_uranium = new BlockOutgas(Material.rock, true, 5, true).setBlockName("ore_gneiss_uranium").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_gneiss_uranium");
		ModBlocks.ore_gneiss_uranium_scorched = new BlockOutgas(Material.rock, true, 5, true).setBlockName("ore_gneiss_uranium_scorched").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_gneiss_uranium_scorched");
		ModBlocks.ore_gneiss_copper = new BlockOre(Material.rock).setBlockName("ore_gneiss_copper").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_gneiss_copper");
		ModBlocks.ore_gneiss_asbestos = new BlockOutgas(Material.rock, true, 5, true).setBlockName("ore_gneiss_asbestos").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_gneiss_asbestos");
		ModBlocks.ore_gneiss_lithium = new BlockOre(Material.rock).setBlockName("ore_gneiss_lithium").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_gneiss_lithium");
		ModBlocks.ore_gneiss_schrabidium = new BlockOre(Material.rock).setBlockName("ore_gneiss_schrabidium").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_gneiss_schrabidium");
		ModBlocks.ore_gneiss_rare = new BlockOre(Material.rock).setBlockName("ore_gneiss_rare").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_gneiss_rare");
		ModBlocks.ore_gneiss_gas = new BlockOre(Material.rock).setBlockName("ore_gneiss_gas").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_gneiss_gas");

		ModBlocks.gneiss_brick = new BlockGeneric(Material.rock).setBlockName("gneiss_brick").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":gneiss_brick");
		ModBlocks.gneiss_tile = new BlockGeneric(Material.rock).setBlockName("gneiss_tile").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":gneiss_tile");
		ModBlocks.gneiss_chiseled = new BlockPillar(Material.rock, RefStrings.MODID + ":gneiss_tile").setBlockName("gneiss_chiseled").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":gneiss_chiseled");
		
		ModBlocks.stone_depth = new BlockDepth().setBlockName("stone_depth").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":stone_depth");
		ModBlocks.ore_depth_cinnebar = new BlockDepthOre().setBlockName("ore_depth_cinnebar").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ore_depth_cinnebar");
		ModBlocks.ore_depth_zirconium = new BlockDepthOre().setBlockName("ore_depth_zirconium").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ore_depth_zirconium");
		ModBlocks.ore_depth_borax = new BlockDepthOre().setBlockName("ore_depth_borax").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ore_depth_borax");
		ModBlocks.cluster_depth_iron = new BlockDepthOre().setBlockName("cluster_depth_iron").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":cluster_depth_iron");
		ModBlocks.cluster_depth_titanium = new BlockDepthOre().setBlockName("cluster_depth_titanium").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":cluster_depth_titanium");
		ModBlocks.cluster_depth_tungsten = new BlockDepthOre().setBlockName("cluster_depth_tungsten").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":cluster_depth_tungsten");
		ModBlocks.ore_alexandrite = new BlockDepthOre().setBlockName("ore_alexandrite").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ore_alexandrite");
		
		ModBlocks.ore_random = new BlockMotherOfAllOres().setBlockName("ore_random").setCreativeTab(MainRegistry.blockTab);
		ModBlocks.ore_bedrock = new BlockBedrockOreTE().setBlockName("ore_bedrock").setCreativeTab(null);
		ModBlocks.ore_volcano = new BlockFissure().setBlockName("ore_volcano").setLightLevel(1F).setCreativeTab(MainRegistry.blockTab);

		ModBlocks.depth_brick = new BlockDepth().setBlockName("depth_brick").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":depth_brick");
		ModBlocks.depth_tiles = new BlockDepth().setBlockName("depth_tiles").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":depth_tiles");
		ModBlocks.depth_nether_brick = new BlockDepth().setBlockName("depth_nether_brick").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":depth_nether_brick");
		ModBlocks.depth_nether_tiles = new BlockDepth().setBlockName("depth_nether_tiles").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":depth_nether_tiles");
		ModBlocks.depth_dnt = new BlockDepth().setBlockName("depth_dnt").setCreativeTab(MainRegistry.blockTab).setResistance(60000.0F).setBlockTextureName(RefStrings.MODID + ":depth_dnt");
		
		ModBlocks.stone_depth_nether = new BlockDepth().setBlockName("stone_depth_nether").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":stone_depth_nether");
		ModBlocks.ore_depth_nether_neodymium = new BlockDepthOre().setBlockName("ore_depth_nether_neodymium").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ore_depth_nether_neodymium");
		
		ModBlocks.stone_porous = new BlockPorous().setBlockName("stone_porous").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":stone_porous");
		ModBlocks.stone_resource = new BlockResourceStone().setBlockName("stone_resource").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
		ModBlocks.stalagmite = new BlockStalagmite().setBlockName("stalagmite").setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(2.0F);
		ModBlocks.stalactite = new BlockStalagmite().setBlockName("stalactite").setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(2.0F);
		ModBlocks.stone_biome = new BlockBiomeStone().setBlockName("stone_biome").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
		ModBlocks.stone_deep_cobble = new BlockDeepCobble().setBlockName("stone_deep_cobble").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(30.0F);

		ModBlocks.basalt = new BlockGeneric(Material.rock).setBlockName("basalt").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":basalt");
		ModBlocks.basalt_sulfur = new BlockOre(Material.rock).setBlockName("basalt_sulfur").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":basalt_sulfur");
		ModBlocks.basalt_fluorite = new BlockOre(Material.rock).setBlockName("basalt_fluorite").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":basalt_fluorite");
		ModBlocks.basalt_asbestos = new BlockOutgas(Material.rock, true, 5, true).setBlockName("basalt_asbestos").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":basalt_asbestos");
		ModBlocks.basalt_gem = new BlockCluster(Material.rock).setBlockName("basalt_gem").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":basalt_gem");
		ModBlocks.basalt_smooth = new BlockGeneric(Material.rock).setBlockName("basalt_smooth").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":basalt_smooth");
		ModBlocks.basalt_brick = new BlockGeneric(Material.rock).setBlockName("basalt_brick").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":basalt_brick");
		ModBlocks.basalt_polished = new BlockGeneric(Material.rock).setBlockName("basalt_polished").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":basalt_polished");
		ModBlocks.basalt_tiles = new BlockGeneric(Material.rock).setBlockName("basalt_tiles").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":basalt_tiles");
		
		ModBlocks.ore_australium = new BlockGeneric(Material.rock).setBlockName("ore_australium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_australium");
		ModBlocks.ore_weidanium = new BlockGeneric(Material.rock).setBlockName("ore_weidanium").setCreativeTab(null).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_weidanium");
		ModBlocks.ore_reiium = new BlockGeneric(Material.rock).setBlockName("ore_reiium").setCreativeTab(null).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_reiium");
		ModBlocks.ore_unobtainium = new BlockGeneric(Material.rock).setBlockName("ore_unobtainium").setCreativeTab(null).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_unobtainium");
		ModBlocks.ore_daffergon = new BlockGeneric(Material.rock).setBlockName("ore_daffergon").setCreativeTab(null).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_daffergon");
		ModBlocks.ore_verticium = new BlockGeneric(Material.rock).setBlockName("ore_verticium").setCreativeTab(null).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_verticium");
		ModBlocks.ore_rare = new BlockOre(Material.rock).setBlockName("ore_rare").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_rare");
		ModBlocks.ore_cobalt = new BlockOre(Material.rock).setBlockName("ore_cobalt").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_cobalt");
		ModBlocks.ore_cinnebar = new BlockOre(Material.rock).setBlockName("ore_cinnebar").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_cinnebar");
		ModBlocks.ore_coltan = new BlockOre(Material.rock).setBlockName("ore_coltan").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_coltan");
		
		ModBlocks.ore_oil = new BlockOre(Material.rock).setBlockName("ore_oil").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_oil");
		ModBlocks.ore_oil_empty = new BlockGeneric(Material.rock).setBlockName("ore_oil_empty").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_oil_empty");
		ModBlocks.ore_oil_sand = new BlockFalling(Material.sand).setBlockName("ore_oil_sand").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeSand).setHardness(0.5F).setResistance(1.0F).setBlockTextureName(RefStrings.MODID + ":ore_oil_sand_alt");
		ModBlocks.ore_bedrock_oil = new BlockGeneric(Material.rock).setBlockName("ore_bedrock_oil").setCreativeTab(MainRegistry.blockTab).setBlockUnbreakable().setResistance(1_000_000).setBlockTextureName(RefStrings.MODID + ":ore_bedrock_oil");
		
		ModBlocks.ore_tikite = new BlockDragonProof(Material.rock).setBlockName("ore_tikite").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_tikite_alt");

		ModBlocks.crystal_power = new BlockCrystal(Material.glass).setBlockName("crystal_power").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(1.0F).setBlockTextureName(RefStrings.MODID + ":crystal_power");
		ModBlocks.crystal_energy = new BlockCrystal(Material.glass).setBlockName("crystal_energy").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(1.0F).setBlockTextureName(RefStrings.MODID + ":crystal_energy");
		ModBlocks.crystal_robust = new BlockCrystal(Material.glass).setBlockName("crystal_robust").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.blockTab).setHardness(10.0F).setResistance(100.0F).setBlockTextureName(RefStrings.MODID + ":crystal_robust");
		ModBlocks.crystal_trixite = new BlockCrystal(Material.glass).setBlockName("crystal_trixite").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(1.0F).setBlockTextureName(RefStrings.MODID + ":crystal_trixite");
		
		ModBlocks.block_uranium = new BlockHazard().makeBeaconable().setBlockName("block_uranium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_uranium");
		ModBlocks.block_u233 = new BlockHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.RADFOG).setBlockName("block_u233").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_u233");
		ModBlocks.block_u235 = new BlockHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.RADFOG).setBlockName("block_u235").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_u235");
		ModBlocks.block_u238 = new BlockHazard().makeBeaconable().setBlockName("block_u238").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_u238");
		ModBlocks.block_uranium_fuel = new BlockHazard().makeBeaconable().setBlockName("block_uranium_fuel").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_uranium_fuel");
		ModBlocks.block_thorium = new BlockHazard().makeBeaconable().setBlockName("block_thorium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_thorium");
		ModBlocks.block_thorium_fuel = new BlockHazard().makeBeaconable().setBlockName("block_thorium_fuel").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_thorium_fuel");
		ModBlocks.block_neptunium = new BlockHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.RADFOG).setBlockName("block_neptunium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(60.0F).setBlockTextureName(RefStrings.MODID + ":block_neptunium");
		ModBlocks.block_polonium = new BlockHotHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.RADFOG).setBlockName("block_polonium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_polonium");
		ModBlocks.block_mox_fuel = new BlockHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.RADFOG).setBlockName("block_mox_fuel").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_mox_fuel");
		ModBlocks.block_plutonium = new BlockHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.RADFOG).setBlockName("block_plutonium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_plutonium");
		ModBlocks.block_pu238 = new BlockHotHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.RADFOG).setBlockName("block_pu238").setCreativeTab(MainRegistry.blockTab).setLightLevel(5F/15F).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_pu238");
		ModBlocks.block_pu239 = new BlockHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.RADFOG).setBlockName("block_pu239").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_pu239");
		ModBlocks.block_pu240 = new BlockHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.RADFOG).setBlockName("block_pu240").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_pu240");
		ModBlocks.block_pu_mix = new BlockHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.RADFOG).setBlockName("block_pu_mix").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_pu_mix");
		ModBlocks.block_plutonium_fuel = new BlockHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.RADFOG).setBlockName("block_plutonium_fuel").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_plutonium_fuel");
		ModBlocks.block_titanium = new BlockBeaconable(Material.iron).setBlockName("block_titanium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_titanium");
		ModBlocks.block_sulfur = new BlockBeaconable(Material.iron).setBlockName("block_sulfur").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_sulfur");
		ModBlocks.block_niter = new BlockBeaconable(Material.iron).setBlockName("block_niter").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_niter");
		ModBlocks.block_niter_reinforced = new BlockBeaconable(Material.iron).setBlockName("block_niter_reinforced").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(100.0F).setBlockTextureName(RefStrings.MODID + ":block_niter_reinforced");
		ModBlocks.block_copper = new BlockBeaconable(Material.iron).setBlockName("block_copper").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(20.0F).setBlockTextureName(RefStrings.MODID + ":block_copper");
		ModBlocks.block_red_copper = new BlockBeaconable(Material.iron).setBlockName("block_red_copper").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(25.0F).setBlockTextureName(RefStrings.MODID + ":block_red_copper");
		ModBlocks.block_tungsten = new BlockBeaconable(Material.iron).setBlockName("block_tungsten").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(20.0F).setBlockTextureName(RefStrings.MODID + ":block_tungsten");
		ModBlocks.block_aluminium = new BlockBeaconable(Material.iron).setBlockName("block_aluminium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(20.0F).setBlockTextureName(RefStrings.MODID + ":block_aluminium");
		ModBlocks.block_fluorite = new BlockBeaconable(Material.iron).setBlockName("block_fluorite").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_fluorite");
		ModBlocks.block_steel = new BlockBeaconable(Material.iron).setBlockName("block_steel").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.block_tcalloy = new BlockBeaconable(Material.iron).setBlockName("block_tcalloy").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(70.0F).setBlockTextureName(RefStrings.MODID + ":block_tcalloy");
		ModBlocks.block_cdalloy = new BlockBeaconable(Material.iron).setBlockName("block_cdalloy").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(70.0F).setBlockTextureName(RefStrings.MODID + ":block_cdalloy");
		ModBlocks.block_lead = new BlockBeaconable(Material.iron).setBlockName("block_lead").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_lead");
		ModBlocks.block_bismuth = new BlockBeaconable(Material.iron).setBlockName("block_bismuth").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(90.0F).setBlockTextureName(RefStrings.MODID + ":block_bismuth");
		ModBlocks.block_cadmium = new BlockBeaconable(Material.iron).setBlockName("block_cadmium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(90.0F).setBlockTextureName(RefStrings.MODID + ":block_cadmium");
		ModBlocks.block_coltan = new BlockBeaconable(Material.iron).setBlockName("block_coltan").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_coltan");
		ModBlocks.block_tantalium = new BlockBeaconable(Material.iron).setBlockName("block_tantalium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_tantalium");
		ModBlocks.block_niobium = new BlockBeaconable(Material.iron).setBlockName("block_niobium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F);
		ModBlocks.block_trinitite = new BlockHazard().makeBeaconable().setBlockName("block_trinitite").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_trinitite");
		ModBlocks.block_waste = new BlockNuclearWaste().makeBeaconable().setDisplayEffect(ExtDisplayEffect.RADFOG).setBlockName("block_waste").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_waste");
		ModBlocks.block_waste_painted = new BlockNuclearWaste().makeBeaconable().setDisplayEffect(ExtDisplayEffect.RADFOG).setBlockName("block_waste_painted").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_waste_painted");
		ModBlocks.block_waste_vitrified = new BlockNuclearWaste().makeBeaconable().setDisplayEffect(ExtDisplayEffect.RADFOG).setBlockName("block_waste_vitrified").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_waste_vitrified");
		ModBlocks.ancient_scrap = new BlockOutgas(Material.iron, true, 1, true, true).setBlockName("ancient_scrap").setCreativeTab(MainRegistry.blockTab).setHardness(100.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":ancient_scrap");
		ModBlocks.block_corium = new BlockHazard(Material.iron).setBlockName("block_corium").setCreativeTab(MainRegistry.blockTab).setHardness(100.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":block_corium");
		ModBlocks.block_corium_cobble = new BlockOutgas(Material.iron, true, 1, true, true).setBlockName("block_corium_cobble").setCreativeTab(MainRegistry.blockTab).setHardness(100.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":block_corium_cobble");
		ModBlocks.block_scrap = new BlockFalling(Material.sand).setBlockName("block_scrap").setCreativeTab(MainRegistry.blockTab).setHardness(2.5F).setResistance(5.0F).setStepSound(Block.soundTypeGravel).setBlockTextureName(RefStrings.MODID + ":block_scrap");
		ModBlocks.block_electrical_scrap = new BlockFalling(Material.iron).setBlockName("block_electrical_scrap").setCreativeTab(MainRegistry.blockTab).setHardness(2.5F).setResistance(5.0F).setStepSound(Block.soundTypeMetal).setBlockTextureName(RefStrings.MODID + ":electrical_scrap_alt2");
		ModBlocks.block_beryllium = new BlockBeaconable(Material.iron).setBlockName("block_beryllium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(20.0F).setBlockTextureName(RefStrings.MODID + ":block_beryllium");
		ModBlocks.block_schraranium = new BlockHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.SCHRAB).setBlockName("block_schraranium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(250.0F).setBlockTextureName(RefStrings.MODID + ":block_schraranium");
		ModBlocks.block_schrabidium = new BlockHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.SCHRAB).setBlockName("block_schrabidium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":block_schrabidium");
		ModBlocks.block_schrabidate = new BlockHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.SCHRAB).setBlockName("block_schrabidate").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":block_schrabidate");
		ModBlocks.block_solinium = new BlockHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.SCHRAB).setBlockName("block_solinium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":block_solinium");
		ModBlocks.block_schrabidium_fuel = new BlockHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.SCHRAB).setBlockName("block_schrabidium_fuel").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":block_schrabidium_fuel");
		ModBlocks.block_euphemium = new BlockBeaconable(Material.iron).setBlockName("block_euphemium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(60000.0F).setBlockTextureName(RefStrings.MODID + ":block_euphemium");
		ModBlocks.block_dineutronium = new BlockBeaconable(Material.iron).setBlockName("block_dineutronium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(60000.0F).setBlockTextureName(RefStrings.MODID + ":block_dineutronium");
		ModBlocks.block_schrabidium_cluster = new BlockRotatablePillar(Material.rock, RefStrings.MODID + ":block_schrabidium_cluster_top").setBlockName("block_schrabidium_cluster").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(60000.0F).setBlockTextureName(RefStrings.MODID + ":block_schrabidium_cluster_side");
		ModBlocks.block_euphemium_cluster = new BlockRotatablePillar(Material.rock, RefStrings.MODID + ":block_euphemium_cluster_top").setBlockName("block_euphemium_cluster").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(60000.0F).setBlockTextureName(RefStrings.MODID + ":block_euphemium_cluster_side");
		ModBlocks.block_advanced_alloy = new BlockBeaconable(Material.iron).setBlockName("block_advanced_alloy").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(60.0F).setBlockTextureName(RefStrings.MODID + ":block_advanced_alloy");
		ModBlocks.block_magnetized_tungsten = new BlockBeaconable(Material.iron).setBlockName("block_magnetized_tungsten").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(75.0F).setBlockTextureName(RefStrings.MODID + ":block_magnetized_tungsten");
		ModBlocks.block_combine_steel = new BlockBeaconable(Material.iron).setBlockName("block_combine_steel").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":block_combine_steel");
		ModBlocks.block_desh = new BlockBeaconable(Material.iron).setBlockName("block_desh").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(300.0F).setBlockTextureName(RefStrings.MODID + ":block_desh");
		ModBlocks.block_dura_steel = new BlockBeaconable(Material.iron).setBlockName("block_dura_steel").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(200.0F).setBlockTextureName(RefStrings.MODID + ":block_dura_steel");
		ModBlocks.block_starmetal = new BlockBeaconable(Material.iron).setBlockName("block_starmetal").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(400.0F).setBlockTextureName(RefStrings.MODID + ":block_starmetal");
		ModBlocks.block_polymer = new BlockBeaconable(Material.rock).setBlockName("block_polymer").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypePiston).setHardness(3.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_polymer");
		ModBlocks.block_bakelite = new BlockBeaconable(Material.rock).setBlockName("block_bakelite").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypePiston).setHardness(3.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":block_bakelite");
		ModBlocks.block_rubber = new BlockBeaconable(Material.rock).setBlockName("block_rubber").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypePiston).setHardness(3.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":block_rubber");
		ModBlocks.block_yellowcake = new BlockHazardFalling().makeBeaconable().setBlockName("block_yellowcake").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeSand).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_yellowcake");
		ModBlocks.block_insulator = new BlockRotatablePillar(Material.cloth, RefStrings.MODID + ":block_insulator_top").setBlockName("block_insulator").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeCloth).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_insulator_side");
		ModBlocks.block_fiberglass = new BlockRotatablePillar(Material.cloth, RefStrings.MODID + ":block_fiberglass_top").setBlockName("block_fiberglass").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeCloth).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":block_fiberglass_side");
		ModBlocks.block_asbestos = new BlockOutgas(Material.cloth, true, 5, true).setBlockName("block_asbestos").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeCloth).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":block_asbestos");
		ModBlocks.block_cobalt = new BlockBeaconable(Material.iron).setBlockName("block_cobalt").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_cobalt");
		ModBlocks.block_lithium = new BlockLithium(Material.iron).setBlockName("block_lithium").setStepSound(Block.soundTypeMetal).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_lithium");
		ModBlocks.block_zirconium = new BlockBeaconable(Material.iron).setBlockName("block_zirconium").setStepSound(Block.soundTypeMetal).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(30.0F).setBlockTextureName(RefStrings.MODID + ":block_zirconium");
		ModBlocks.block_white_phosphorus = new BlockHazard(Material.rock).makeBeaconable().setBlockName("block_white_phosphorus").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_white_phosphorus");
		ModBlocks.block_red_phosphorus = new BlockHazardFalling().makeBeaconable().setStepSound(Block.soundTypeSand).setBlockName("block_red_phosphorus").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_red_phosphorus");
		ModBlocks.block_fallout = new BlockHazardFalling().setStepSound(Block.soundTypeGravel).setBlockName("block_fallout").setCreativeTab(MainRegistry.blockTab).setHardness(0.2F).setBlockTextureName(RefStrings.MODID + ":ash");
		ModBlocks.block_foam = new BlockGeneric(Material.craftedSnow).setBlockName("block_foam").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeSnow).setHardness(0.5F).setResistance(0.0F).setBlockTextureName(RefStrings.MODID + ":foam");
		ModBlocks.block_coke = new BlockCoke().setBlockName("block_coke").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F);
		ModBlocks.block_graphite = new BlockGraphite(Material.iron, 30, 5).setBlockName("block_graphite").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F);
		ModBlocks.block_graphite_drilled = new BlockGraphiteDrilled().setBlockName("block_graphite_drilled");
		ModBlocks.block_graphite_fuel = new BlockGraphiteFuel().setBlockName("block_graphite_fuel");
		ModBlocks.block_graphite_plutonium = new BlockGraphiteSource().setBlockName("block_graphite_plutonium");
		ModBlocks.block_graphite_rod = new BlockGraphiteRod().setBlockName("block_graphite_rod").setBlockTextureName(RefStrings.MODID + ":block_graphite_rod_in");
		ModBlocks.block_graphite_source = new BlockGraphiteSource().setBlockName("block_graphite_source");
		ModBlocks.block_graphite_lithium = new BlockGraphiteBreedingFuel().setBlockName("block_graphite_lithium");
		ModBlocks.block_graphite_tritium = new BlockGraphiteBreedingProduct().setBlockName("block_graphite_tritium");
		ModBlocks.block_graphite_detector = new BlockGraphiteNeutronDetector().setBlockName("block_graphite_detector");
		ModBlocks.block_boron = new BlockBeaconable(Material.iron).setBlockName("block_boron").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_boron");
		ModBlocks.block_lanthanium = new BlockBeaconable(Material.iron).setBlockName("block_lanthanium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_lanthanium");
		ModBlocks.block_ra226 = new BlockHazard().makeBeaconable().setBlockName("block_ra226").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_ra226");
		ModBlocks.block_actinium = new BlockHazard().makeBeaconable().setBlockName("block_actinium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_actinium");
		ModBlocks.block_tritium = new BlockRotatablePillar(Material.glass, RefStrings.MODID + ":block_tritium_top").setBlockName("block_tritium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeGlass).setHardness(3.0F).setResistance(2.0F).setBlockTextureName(RefStrings.MODID + ":block_tritium_side");
		ModBlocks.block_semtex = new BlockPlasticExplosive(Material.tnt).setBlockName("block_semtex").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(2.0F).setResistance(2.0F).setBlockTextureName(RefStrings.MODID + ":block_semtex");
		ModBlocks.block_c4 = new BlockPlasticExplosive(Material.tnt).setBlockName("block_c4").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(2.0F).setResistance(2.0F).setBlockTextureName(RefStrings.MODID + ":block_c4");
		ModBlocks.block_smore = new BlockPillar(Material.rock, RefStrings.MODID + ":block_smore_top").setBlockName("block_smore").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":block_smore_side");
		ModBlocks.block_slag = new BlockSlag(Material.rock).setBlockName("block_slag").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeStone).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_slag");
		
		ModBlocks.block_australium = new BlockBeaconable(Material.iron).setBlockName("block_australium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_australium");
		ModBlocks.block_weidanium = new BlockBeaconable(Material.iron).setBlockName("block_weidanium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_weidanium");
		ModBlocks.block_reiium = new BlockBeaconable(Material.iron).setBlockName("block_reiium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_reiium");
		ModBlocks.block_unobtainium = new BlockBeaconable(Material.iron).setBlockName("block_unobtainium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_unobtainium");
		ModBlocks.block_daffergon = new BlockBeaconable(Material.iron).setBlockName("block_daffergon").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_daffergon");
		ModBlocks.block_verticium = new BlockBeaconable(Material.iron).setBlockName("block_verticium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_verticium");

		ModBlocks.block_cap_nuka = new BlockCap(Material.iron, RefStrings.MODID + ":block_cap_nuka_top").setStepSound(Block.soundTypeMetal).setBlockName("block_cap_nuka").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":block_cap_nuka");
		ModBlocks.block_cap_quantum = new BlockCap(Material.iron, RefStrings.MODID + ":block_cap_quantum_top").setStepSound(Block.soundTypeMetal).setBlockName("block_cap_quantum").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":block_cap_quantum");
		ModBlocks.block_cap_rad = new BlockCap(Material.iron, RefStrings.MODID + ":block_cap_rad_top").setStepSound(Block.soundTypeMetal).setBlockName("block_cap_rad").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":block_cap_rad");
		ModBlocks.block_cap_sparkle = new BlockCap(Material.iron, RefStrings.MODID + ":block_cap_sparkle_top").setStepSound(Block.soundTypeMetal).setBlockName("block_cap_sparkle").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":block_cap_sparkle");
		ModBlocks.block_cap_korl = new BlockCap(Material.iron, RefStrings.MODID + ":block_cap_korl_top").setStepSound(Block.soundTypeMetal).setBlockName("block_cap_korl").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":block_cap_korl");
		ModBlocks.block_cap_fritz = new BlockCap(Material.iron, RefStrings.MODID + ":block_cap_fritz_top").setStepSound(Block.soundTypeMetal).setBlockName("block_cap_fritz").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":block_cap_fritz");
		ModBlocks.block_cap_sunset = new BlockCap(Material.iron, RefStrings.MODID + ":block_cap_sunset_top").setStepSound(Block.soundTypeMetal).setBlockName("block_cap_sunset").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":block_cap_sunset");
		ModBlocks.block_cap_star = new BlockCap(Material.iron, RefStrings.MODID + ":block_cap_star_top").setStepSound(Block.soundTypeMetal).setBlockName("block_cap_star").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":block_cap_star");
		
		ModBlocks.deco_titanium = new BlockOre(Material.iron).noFortune().setBlockName("deco_titanium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":deco_titanium");
		ModBlocks.deco_red_copper = new BlockDecoCT(Material.iron).noFortune().setBlockName("deco_red_copper").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":deco_red_copper");
		ModBlocks.deco_tungsten = new BlockDecoCT(Material.iron).noFortune().setBlockName("deco_tungsten").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":deco_tungsten");
		ModBlocks.deco_aluminium = new BlockDecoCT(Material.iron).noFortune().setBlockName("deco_aluminium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":deco_aluminium");
		ModBlocks.deco_steel = new BlockDecoCT(Material.iron).noFortune().setBlockName("deco_steel").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":deco_steel");
		ModBlocks.deco_lead = new BlockDecoCT(Material.iron).noFortune().setBlockName("deco_lead").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":deco_lead");
		ModBlocks.deco_beryllium = new BlockDecoCT(Material.iron).noFortune().setBlockName("deco_beryllium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":deco_beryllium");
		ModBlocks.deco_asbestos = new BlockOutgas(Material.cloth, true, 5, true).noFortune().setBlockName("deco_asbestos").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":deco_asbestos");
		ModBlocks.deco_rbmk = new BlockGeneric(Material.iron).setBlockName("deco_rbmk").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(100.0F).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_side");
		ModBlocks.deco_rbmk_smooth = new BlockGeneric(Material.iron).setBlockName("deco_rbmk_smooth").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(100.0F).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_top");
		
		ModBlocks.deco_emitter = new BlockEmitter().setBlockName("deco_emitter").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(20.0F).setBlockTextureName(RefStrings.MODID + ":emitter");
		ModBlocks.part_emitter = new PartEmitter().setBlockName("part_emitter").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(20.0F).setBlockTextureName(RefStrings.MODID + ":part_top");
		ModBlocks.deco_loot = new BlockLoot().setBlockName("deco_loot").setCreativeTab(null).setHardness(0.0F).setResistance(0.0F).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.bobblehead = new BlockBobble().setBlockName("bobblehead").setCreativeTab(MainRegistry.blockTab).setHardness(0.0F).setResistance(0.0F).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.snowglobe = new BlockSnowglobe().setBlockName("snowglobe").setCreativeTab(MainRegistry.blockTab).setHardness(0.0F).setResistance(0.0F).setBlockTextureName(RefStrings.MODID + ":glass_boron");
		ModBlocks.hazmat = new BlockGeneric(Material.cloth).setBlockName("hazmat").setStepSound(Block.soundTypeCloth).setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(60.0F).setBlockTextureName(RefStrings.MODID + ":hazmat");
		
		ModBlocks.gravel_obsidian = new BlockFalling(Material.iron).setBlockName("gravel_obsidian").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeGravel).setHardness(5.0F).setResistance(240.0F).setBlockTextureName(RefStrings.MODID + ":gravel_obsidian");
		ModBlocks.gravel_diamond = new BlockFalling(Material.sand).setBlockName("gravel_diamond").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeGravel).setHardness(0.6F).setBlockTextureName(RefStrings.MODID + ":gravel_diamond");
		ModBlocks.asphalt = new BlockSpeedy(Material.rock, 1.5).setBlockName("asphalt").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(120.0F).setBlockTextureName(RefStrings.MODID + ":asphalt");
		ModBlocks.asphalt_light = new BlockSpeedy(Material.rock, 1.5).setBlockName("asphalt_light").setCreativeTab(MainRegistry.blockTab).setLightLevel(1F).setHardness(15.0F).setResistance(120.0F).setBlockTextureName(RefStrings.MODID + ":asphalt_light");

		ModBlocks.reinforced_brick = new BlockGeneric(Material.rock).setBlockName("reinforced_brick").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(300.0F).setBlockTextureName(RefStrings.MODID + ":reinforced_brick");
		ModBlocks.reinforced_glass = new BlockNTMGlassCT(0, RefStrings.MODID + ":reinforced_glass", Material.rock).setBlockName("reinforced_glass").setCreativeTab(MainRegistry.blockTab).setLightOpacity(0).setHardness(15.0F).setResistance(25.0F);
		ModBlocks.reinforced_glass_pane = new BlockNTMGlassPane(0, RefStrings.MODID + ":reinforced_glass_pane", RefStrings.MODID + ":reinforced_glass_pane_edge", Material.rock, false).setBlockName("reinforced_glass_pane").setCreativeTab(MainRegistry.blockTab).setLightOpacity(1).setHardness(15.0F).setResistance(200.0F);
		ModBlocks.reinforced_light = new BlockGeneric(Material.rock).setBlockName("reinforced_light").setCreativeTab(MainRegistry.blockTab).setLightLevel(1.0F).setHardness(15.0F).setResistance(80.0F).setBlockTextureName(RefStrings.MODID + ":reinforced_light");
		ModBlocks.reinforced_sand = new BlockGeneric(Material.rock).setBlockName("reinforced_sand").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(40.0F).setBlockTextureName(RefStrings.MODID + ":reinforced_sand");
		ModBlocks.reinforced_lamp_off = new ReinforcedLamp(Material.rock, false).setBlockName("reinforced_lamp_off").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(80.0F).setBlockTextureName(RefStrings.MODID + ":reinforced_lamp_off");
		ModBlocks.reinforced_lamp_on = new ReinforcedLamp(Material.rock, true).setBlockName("reinforced_lamp_on").setHardness(15.0F).setResistance(80.0F).setBlockTextureName(RefStrings.MODID + ":reinforced_lamp_on");
		ModBlocks.reinforced_laminate = new BlockNTMGlassCT(1, RefStrings.MODID + ":reinforced_laminate", Material.rock, true).setBlockName("reinforced_laminate").setCreativeTab(MainRegistry.blockTab).setLightOpacity(0).setHardness(15.0F).setResistance(300.0F);
		ModBlocks.reinforced_laminate_pane = new BlockNTMGlassPane(1, RefStrings.MODID + ":reinforced_laminate_pane", RefStrings.MODID + ":reinforced_laminate_pane_edge", Material.rock, true).setBlockName("reinforced_laminate_pane").setCreativeTab(MainRegistry.blockTab).setLightOpacity(1).setHardness(15.0F).setResistance(300.0F);
		
		ModBlocks.lamp_tritium_green_off = new ReinforcedLamp(Material.redstoneLight, false).setBlockName("lamp_tritium_green_off").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.blockTab).setHardness(3.0F).setBlockTextureName(RefStrings.MODID + ":lamp_tritium_green_off");
		ModBlocks.lamp_tritium_green_on = new ReinforcedLamp(Material.redstoneLight, true).setBlockName("lamp_tritium_green_on").setStepSound(Block.soundTypeGlass).setHardness(3.0F).setBlockTextureName(RefStrings.MODID + ":lamp_tritium_green_on");
		ModBlocks.lamp_tritium_blue_off = new ReinforcedLamp(Material.redstoneLight, false).setBlockName("lamp_tritium_blue_off").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.blockTab).setHardness(3.0F).setBlockTextureName(RefStrings.MODID + ":lamp_tritium_blue_off");
		ModBlocks.lamp_tritium_blue_on = new ReinforcedLamp(Material.redstoneLight, true).setBlockName("lamp_tritium_blue_on").setStepSound(Block.soundTypeGlass).setHardness(3.0F).setBlockTextureName(RefStrings.MODID + ":lamp_tritium_blue_on");

		ModBlocks.lamp_uv_off = new UVLamp(false).setBlockName("lamp_uv_off").setCreativeTab(MainRegistry.blockTab);
		ModBlocks.lamp_uv_on = new UVLamp(true).setBlockName("lamp_uv_on").setCreativeTab(null);
		ModBlocks.lamp_demon = new DemonLamp().setBlockName("lamp_demon").setStepSound(Block.soundTypeMetal).setCreativeTab(MainRegistry.blockTab).setLightLevel(1F).setHardness(3.0F).setBlockTextureName(RefStrings.MODID + ":lamp_demon");
		ModBlocks.lantern = new BlockLantern().setBlockName("lantern").setStepSound(Block.soundTypeMetal).setCreativeTab(MainRegistry.blockTab).setLightLevel(1F).setHardness(3.0F).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.lantern_behemoth = new BlockLanternBehemoth().setBlockName("lantern_behemoth").setStepSound(Block.soundTypeMetal).setCreativeTab(null).setHardness(3.0F).setBlockTextureName(RefStrings.MODID + ":block_rust");
		
		ModBlocks.reinforced_stone = new BlockGeneric(Material.rock).setBlockName("reinforced_stone").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(100.0F).setBlockTextureName(RefStrings.MODID + ":reinforced_stone");
		ModBlocks.concrete_smooth = new BlockRadResistant(Material.rock).setBlockName("concrete_smooth").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(140.0F).setBlockTextureName(RefStrings.MODID + ":concrete");
		ModBlocks.concrete_colored = new BlockConcreteColored(Material.rock).setBlockName("concrete_colored").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(140.0F).setBlockTextureName(RefStrings.MODID + ":concrete");
		ModBlocks.concrete_colored_ext = new BlockConcreteColoredExt(Material.rock).setBlockName("concrete_colored_ext").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(140.0F).setBlockTextureName(RefStrings.MODID + ":concrete_colored_ext");
		ModBlocks.concrete = new BlockGeneric(Material.rock).setBlockName("concrete").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(140.0F).setBlockTextureName(RefStrings.MODID + ":concrete_tile");
		ModBlocks.concrete_asbestos = new BlockGeneric(Material.rock).setBlockName("concrete_asbestos").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(150.0F).setBlockTextureName(RefStrings.MODID + ":concrete_asbestos");
		ModBlocks.concrete_super = new BlockUberConcrete().setBlockName("concrete_super").setCreativeTab(MainRegistry.blockTab).setHardness(150.0F).setResistance(1000.0F);
		ModBlocks.concrete_super_broken = new BlockFalling(Material.rock).setBlockName("concrete_super_broken").setCreativeTab(MainRegistry.blockTab).setHardness(10.0F).setResistance(20.0F).setBlockTextureName(RefStrings.MODID + ":concrete_super_broken");
		ModBlocks.concrete_pillar = new BlockRotatablePillar(Material.rock, RefStrings.MODID + ":concrete_pillar_top").setBlockName("concrete_pillar").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(180.0F).setBlockTextureName(RefStrings.MODID + ":concrete_pillar_side");
		ModBlocks.brick_concrete = new BlockGeneric(Material.rock).setBlockName("brick_concrete").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(160.0F).setBlockTextureName(RefStrings.MODID + ":brick_concrete");
		ModBlocks.brick_concrete_mossy = new BlockGeneric(Material.rock).setBlockName("brick_concrete_mossy").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(160.0F).setBlockTextureName(RefStrings.MODID + ":brick_concrete_mossy");
		ModBlocks.brick_concrete_cracked = new BlockGeneric(Material.rock).setBlockName("brick_concrete_cracked").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(60.0F).setBlockTextureName(RefStrings.MODID + ":brick_concrete_cracked");
		ModBlocks.brick_concrete_broken = new BlockGeneric(Material.rock).setBlockName("brick_concrete_broken").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(45.0F).setBlockTextureName(RefStrings.MODID + ":brick_concrete_broken");
		ModBlocks.brick_concrete_marked = new BlockWriting(Material.rock, RefStrings.MODID + ":brick_concrete").setBlockName("brick_concrete_marked").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(160.0F).setBlockTextureName(RefStrings.MODID + ":brick_concrete_marked");
		ModBlocks.brick_obsidian = new BlockGeneric(Material.rock).setBlockName("brick_obsidian").setCreativeTab(MainRegistry.blockTab).setLightOpacity(15).setHardness(15.0F).setResistance(120.0F).setBlockTextureName(RefStrings.MODID + ":brick_obsidian");
		ModBlocks.brick_light = new BlockGeneric(Material.rock).setBlockName("brick_light").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(20.0F).setBlockTextureName(RefStrings.MODID + ":brick_light");
		ModBlocks.brick_compound = new BlockGeneric(Material.rock).setBlockName("brick_compound").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(400.0F).setBlockTextureName(RefStrings.MODID + ":brick_compound");
		ModBlocks.cmb_brick = new BlockGeneric(Material.rock).setBlockName("cmb_brick").setCreativeTab(MainRegistry.blockTab).setHardness(25.0F).setResistance(5000.0F).setBlockTextureName(RefStrings.MODID + ":cmb_brick");
		ModBlocks.cmb_brick_reinforced = new BlockGeneric(Material.rock).setBlockName("cmb_brick_reinforced").setCreativeTab(MainRegistry.blockTab).setHardness(25.0F).setResistance(50000.0F).setBlockTextureName(RefStrings.MODID + ":cmb_brick_reinforced");
		ModBlocks.brick_asbestos = new BlockOutgas(Material.rock, true, 5, true).setBlockName("brick_asbestos").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(1000.0F).setBlockTextureName(RefStrings.MODID + ":brick_asbestos");
		ModBlocks.brick_fire = new BlockGeneric(Material.rock).setBlockName("brick_fire").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(35.0F).setBlockTextureName(RefStrings.MODID + ":brick_fire");
		
		ModBlocks.ducrete_smooth = new BlockGeneric(Material.rock).setBlockName("ducrete_smooth").setCreativeTab(MainRegistry.blockTab).setHardness(20.0F).setResistance(500.0F).setBlockTextureName(RefStrings.MODID + ":ducrete");
		ModBlocks.ducrete = new BlockGeneric(Material.rock).setBlockName("ducrete").setCreativeTab(MainRegistry.blockTab).setHardness(20.0F).setResistance(500.0F).setBlockTextureName(RefStrings.MODID + ":ducrete_tile");
		ModBlocks.brick_ducrete = new BlockGeneric(Material.rock).setBlockName("brick_ducrete").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(750.0F).setBlockTextureName(RefStrings.MODID + ":brick_ducrete");
		ModBlocks.reinforced_ducrete = new BlockGeneric(Material.rock).setBlockName("reinforced_ducrete").setCreativeTab(MainRegistry.blockTab).setHardness(20.0F).setResistance(1000.0F).setBlockTextureName(RefStrings.MODID + ":reinforced_ducrete");

		ModBlocks.concrete_slab = new BlockMultiSlab(null, Material.rock, ModBlocks.concrete_smooth, ModBlocks.concrete, ModBlocks.concrete_asbestos, ModBlocks.ducrete_smooth, ModBlocks.ducrete).setBlockName("concrete_slab").setCreativeTab(MainRegistry.blockTab);
		ModBlocks.concrete_double_slab = new BlockMultiSlab(ModBlocks.concrete_slab, Material.rock, ModBlocks.concrete_smooth, ModBlocks.concrete, ModBlocks.concrete_asbestos, ModBlocks.ducrete_smooth, ModBlocks.ducrete).setBlockName("concrete_double_slab").setCreativeTab(MainRegistry.blockTab);
		ModBlocks.concrete_brick_slab = new BlockMultiSlab(null, Material.rock, ModBlocks.brick_concrete, ModBlocks.brick_concrete_mossy, ModBlocks.brick_concrete_cracked, ModBlocks.brick_concrete_broken, ModBlocks.brick_ducrete).setBlockName("concrete_brick_slab").setCreativeTab(MainRegistry.blockTab);
		ModBlocks.concrete_brick_double_slab = new BlockMultiSlab(ModBlocks.concrete_brick_slab, Material.rock, ModBlocks.brick_concrete, ModBlocks.brick_concrete_mossy, ModBlocks.brick_concrete_cracked, ModBlocks.brick_concrete_broken, ModBlocks.brick_ducrete).setBlockName("concrete_brick_double_slab").setCreativeTab(MainRegistry.blockTab);
		ModBlocks.brick_slab = new BlockMultiSlab(null, Material.rock, ModBlocks.reinforced_stone, ModBlocks.reinforced_brick, ModBlocks.brick_obsidian, ModBlocks.brick_light, ModBlocks.brick_compound, ModBlocks.brick_asbestos, ModBlocks.brick_fire).setBlockName("brick_slab").setCreativeTab(MainRegistry.blockTab);
		ModBlocks.brick_double_slab = new BlockMultiSlab(ModBlocks.brick_slab, Material.rock, ModBlocks.reinforced_stone, ModBlocks.reinforced_brick, ModBlocks.brick_obsidian, ModBlocks.brick_light, ModBlocks.brick_compound, ModBlocks.brick_asbestos, ModBlocks.brick_fire).setBlockName("brick_double_slab").setCreativeTab(MainRegistry.blockTab);
		
		ModBlocks.concrete_smooth_stairs = new BlockGenericStairs(ModBlocks.concrete_smooth, 0).setBlockName("concrete_smooth_stairs").setCreativeTab(MainRegistry.blockTab);
		ModBlocks.concrete_stairs = new BlockGenericStairs(ModBlocks.concrete, 0).setBlockName("concrete_stairs").setCreativeTab(MainRegistry.blockTab);
		ModBlocks.concrete_asbestos_stairs = new BlockGenericStairs(ModBlocks.concrete_asbestos, 0).setBlockName("concrete_asbestos_stairs").setCreativeTab(MainRegistry.blockTab);
		ModBlocks.ducrete_smooth_stairs = new BlockGenericStairs(ModBlocks.ducrete_smooth, 0).setBlockName("ducrete_smooth_stairs").setCreativeTab(MainRegistry.blockTab);
		ModBlocks.ducrete_stairs = new BlockGenericStairs(ModBlocks.ducrete, 0).setBlockName("ducrete_stairs").setCreativeTab(MainRegistry.blockTab);
		ModBlocks.brick_concrete_stairs = new BlockGenericStairs(ModBlocks.brick_concrete, 0).setBlockName("brick_concrete_stairs").setCreativeTab(MainRegistry.blockTab);
		ModBlocks.brick_concrete_mossy_stairs = new BlockGenericStairs(ModBlocks.brick_concrete_mossy, 0).setBlockName("brick_concrete_mossy_stairs").setCreativeTab(MainRegistry.blockTab);
		ModBlocks.brick_concrete_cracked_stairs = new BlockGenericStairs(ModBlocks.brick_concrete_cracked, 0).setBlockName("brick_concrete_cracked_stairs").setCreativeTab(MainRegistry.blockTab);
		ModBlocks.brick_concrete_broken_stairs = new BlockGenericStairs(ModBlocks.brick_concrete_broken, 0).setBlockName("brick_concrete_broken_stairs").setCreativeTab(MainRegistry.blockTab);
		ModBlocks.brick_ducrete_stairs = new BlockGenericStairs(ModBlocks.brick_ducrete, 0).setBlockName("brick_ducrete_stairs").setCreativeTab(MainRegistry.blockTab);
		ModBlocks.reinforced_stone_stairs = new BlockGenericStairs(ModBlocks.reinforced_stone, 0).setBlockName("reinforced_stone_stairs").setCreativeTab(MainRegistry.blockTab);
		ModBlocks.reinforced_brick_stairs = new BlockGenericStairs(ModBlocks.reinforced_brick, 0).setBlockName("reinforced_brick_stairs").setCreativeTab(MainRegistry.blockTab);
		ModBlocks.brick_obsidian_stairs = new BlockGenericStairs(ModBlocks.brick_obsidian, 0).setBlockName("brick_obsidian_stairs").setCreativeTab(MainRegistry.blockTab);
		ModBlocks.brick_light_stairs = new BlockGenericStairs(ModBlocks.brick_light, 0).setBlockName("brick_light_stairs").setCreativeTab(MainRegistry.blockTab);
		ModBlocks.brick_compound_stairs = new BlockGenericStairs(ModBlocks.brick_compound, 0).setBlockName("brick_compound_stairs").setCreativeTab(MainRegistry.blockTab);
		ModBlocks.brick_asbestos_stairs = new BlockGenericStairs(ModBlocks.brick_asbestos, 0).setBlockName("brick_asbestos_stairs").setCreativeTab(MainRegistry.blockTab);
		ModBlocks.brick_fire_stairs = new BlockGenericStairs(ModBlocks.brick_fire, 0).setBlockName("brick_fire_stairs").setCreativeTab(MainRegistry.blockTab);
		
		ModBlocks.vinyl_tile = new BlockEnumMulti(Material.rock, TileType.class, true, true).setBlockName("vinyl_tile").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.blockTab).setHardness(10.0F).setResistance(60.0F).setBlockTextureName(RefStrings.MODID + ":vinyl_tile");
		
		ModBlocks.tile_lab = new BlockOutgas(Material.rock, false, 5, true).setBlockName("tile_lab").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.blockTab).setHardness(1.0F).setResistance(20.0F).setBlockTextureName(RefStrings.MODID + ":tile_lab");
		ModBlocks.tile_lab_cracked = new BlockOutgas(Material.rock, false, 5, true).setBlockName("tile_lab_cracked").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.blockTab).setHardness(1.0F).setResistance(20.0F).setBlockTextureName(RefStrings.MODID + ":tile_lab_cracked");
		ModBlocks.tile_lab_broken = new BlockOutgas(Material.rock, true, 5, true).setBlockName("tile_lab_broken").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.blockTab).setHardness(1.0F).setResistance(20.0F).setBlockTextureName(RefStrings.MODID + ":tile_lab_broken");

		ModBlocks.siege_shield = new SiegeShield(Material.iron).setBlockName("siege_shield").setCreativeTab(MainRegistry.blockTab).setBlockUnbreakable().setResistance(900.0F);
		ModBlocks.siege_internal = new SiegeInternal(Material.iron).setBlockName("siege_internal").setCreativeTab(MainRegistry.blockTab).setBlockUnbreakable().setResistance(60.0F);
		ModBlocks.siege_circuit = new SiegeCircuit(Material.iron).setBlockName("siege_circuit").setCreativeTab(MainRegistry.blockTab).setBlockUnbreakable().setResistance(10.0F);
		ModBlocks.siege_emergency = new BlockBase(Material.iron).setBlockName("siege_emergency").setCreativeTab(MainRegistry.blockTab).setBlockUnbreakable().setResistance(20000.0F).setBlockTextureName(RefStrings.MODID + ":siege_emergency");
		ModBlocks.siege_hole = new SiegeHole(Material.iron).setBlockName("siege_hole").setCreativeTab(MainRegistry.blockTab).setBlockUnbreakable().setResistance(900.0F).setBlockTextureName(RefStrings.MODID + ":siege_hole");

		ModBlocks.block_meteor = new BlockOre(Material.rock).noFortune().setBlockName("block_meteor").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":meteor");
		ModBlocks.block_meteor_cobble = new BlockOre(Material.rock).noFortune().setBlockName("block_meteor_cobble").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":meteor_cobble");
		ModBlocks.block_meteor_broken = new BlockOre(Material.rock).noFortune().setBlockName("block_meteor_broken").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":meteor_crushed");
		ModBlocks.block_meteor_molten = new BlockOre(Material.rock, true).noFortune().setBlockName("block_meteor_molten").setLightLevel(0.75F).setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":meteor_cobble_molten");
		ModBlocks.block_meteor_treasure = new BlockOre(Material.rock).noFortune().setBlockName("block_meteor_treasure").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":meteor_treasure");
		ModBlocks.meteor_polished = new BlockGeneric(Material.rock).setBlockName("meteor_polished").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":meteor_polished");
		ModBlocks.meteor_brick = new BlockGeneric(Material.rock).setBlockName("meteor_brick").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":meteor_brick");
		ModBlocks.meteor_brick_mossy = new BlockGeneric(Material.rock).setBlockName("meteor_brick_mossy").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":meteor_brick_mossy");
		ModBlocks.meteor_brick_cracked = new BlockGeneric(Material.rock).setBlockName("meteor_brick_cracked").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":meteor_brick_cracked");
		ModBlocks.meteor_brick_chiseled = new BlockGeneric(Material.rock).setBlockName("meteor_brick_chiseled").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":meteor_brick_chiseled");
		ModBlocks.meteor_pillar = new BlockRotatablePillar(Material.rock, RefStrings.MODID + ":meteor_pillar_top").setBlockName("meteor_pillar").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":meteor_pillar");
		ModBlocks.meteor_spawner = new BlockCybercrab(Material.rock).setBlockName("meteor_spawner").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F);
		ModBlocks.meteor_battery = new BlockPillar(Material.rock, RefStrings.MODID + ":meteor_power").setBlockName("meteor_battery").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":meteor_spawner_side");
		ModBlocks.moon_turf = new BlockFalling(Material.sand).setBlockName("moon_turf").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":moon_turf");
		
		ModBlocks.brick_jungle = new BlockGeneric(Material.rock).setBlockName("brick_jungle").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":brick_jungle");
		ModBlocks.brick_jungle_cracked = new BlockGeneric(Material.rock).setBlockName("brick_jungle_cracked").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":brick_jungle_cracked");
		ModBlocks.brick_jungle_fragile = new FragileBrick(Material.rock).setBlockName("brick_jungle_fragile").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":brick_jungle_fragile");
		ModBlocks.brick_jungle_lava = new BlockGeneric(Material.rock).setBlockName("brick_jungle_lava").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setLightLevel(5F/15F).setBlockTextureName(RefStrings.MODID + ":brick_jungle_lava");
		ModBlocks.brick_jungle_ooze = new BlockOre(Material.rock).setBlockName("brick_jungle_ooze").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setLightLevel(5F/15F).setBlockTextureName(RefStrings.MODID + ":brick_jungle_ooze");
		ModBlocks.brick_jungle_mystic = new BlockOre(Material.rock).setBlockName("brick_jungle_mystic").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setLightLevel(5F/15F).setBlockTextureName(RefStrings.MODID + ":brick_jungle_mystic");
		ModBlocks.brick_jungle_trap = new TrappedBrick(Material.rock).setBlockName("brick_jungle_trap").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":brick_jungle_trap");
		ModBlocks.brick_jungle_glyph = new BlockGlyph(Material.rock).setBlockName("brick_jungle_glyph").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F);
		ModBlocks.brick_jungle_circle = new BlockBallsSpawner(Material.rock).setBlockName("brick_jungle_circle").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":brick_jungle_circle");

		ModBlocks.brick_dungeon = new BlockGeneric(Material.rock).setBlockName("brick_dungeon").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F).setBlockTextureName(RefStrings.MODID + ":brick_dungeon");
		ModBlocks.brick_dungeon_flat = new BlockGeneric(Material.rock).setBlockName("brick_dungeon_flat").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F).setBlockTextureName(RefStrings.MODID + ":brick_dungeon_flat");
		ModBlocks.brick_dungeon_tile = new BlockGeneric(Material.rock).setBlockName("brick_dungeon_tile").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F).setBlockTextureName(RefStrings.MODID + ":brick_dungeon_tile");
		ModBlocks.brick_dungeon_circle = new BlockGeneric(Material.rock).setBlockName("brick_dungeon_circle").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F).setBlockTextureName(RefStrings.MODID + ":brick_dungeon_circle");
		
		ModBlocks.brick_forgotten = new BlockGeneric(Material.rock).setBlockName("brick_forgotten").setCreativeTab(MainRegistry.blockTab).setBlockUnbreakable().setResistance(1000000).setBlockTextureName(RefStrings.MODID + ":brick_forgotten");
		
		ModBlocks.deco_computer = new BlockDecoModel(Material.iron, DecoComputerEnum.class, true, false).setBlockBoundsTo(.160749F, 0F, 0F, .839251F, .867849F, .622184F).setBlockName("deco_computer").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":deco_computer");
		ModBlocks.filing_cabinet = new BlockDecoContainer(Material.iron, DecoCabinetEnum.class, true, false, TileEntityFileCabinet.class).setBlockBoundsTo(.1875F, 0F, 0F, .8125F, 1F, .75F).setBlockName("filing_cabinet").setCreativeTab(MainRegistry.blockTab).setHardness(10.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":block_steel");
		
		ModBlocks.tape_recorder = new DecoTapeRecorder(Material.iron).setBlockName("tape_recorder").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":deco_tape_recorder");
		ModBlocks.steel_poles = new DecoSteelPoles(Material.iron).setBlockName("steel_poles").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":steel_beam");
		ModBlocks.pole_top = new DecoPoleTop(Material.iron).setBlockName("pole_top").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":deco_pole_top");
		ModBlocks.pole_satellite_receiver = new DecoPoleSatelliteReceiver(Material.iron).setBlockName("pole_satellite_receiver").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":deco_satellite_receiver");
		ModBlocks.steel_wall = new DecoBlock(Material.iron).setBlockName("steel_wall").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":steel_wall");
		ModBlocks.steel_corner = new DecoBlock(Material.iron).setBlockName("steel_corner").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":steel_corner");
		ModBlocks.steel_roof = new DecoBlock(Material.iron).setBlockName("steel_roof").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":steel_roof");
		ModBlocks.steel_beam = new DecoBlock(Material.iron).setBlockName("steel_beam").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":steel_beam");
		ModBlocks.steel_scaffold = new BlockScaffold().setBlockName("steel_scaffold").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":deco_steel_orig");
		ModBlocks.steel_grate = new BlockGrate(Material.iron).setBlockName("steel_grate").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F);
		ModBlocks.steel_grate_wide = new BlockGrate(Material.iron).setBlockName("steel_grate_wide").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F);
		
		ModBlocks.deco_pipe = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side", 0).setBlockName("deco_pipe").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top");
		ModBlocks.deco_pipe_rusted = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_rusty", 0).setBlockName("deco_pipe_rusted").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_rusty");
		ModBlocks.deco_pipe_green = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_green", 0).setBlockName("deco_pipe_green").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_green");
		ModBlocks.deco_pipe_green_rusted = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_green_rusty", 0).setBlockName("deco_pipe_green_rusted").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_green_rusty");
		ModBlocks.deco_pipe_red = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_red", 0).setBlockName("deco_pipe_red").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_red");
		ModBlocks.deco_pipe_marked = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_marked", 0).setBlockName("deco_pipe_marked").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_marked");
		ModBlocks.deco_pipe_rim = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side", 1).setBlockName("deco_pipe_rim").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top");
		ModBlocks.deco_pipe_rim_rusted = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_rusty", 1).setBlockName("deco_pipe_rim_rusted").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_rusty");
		ModBlocks.deco_pipe_rim_green = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_green", 1).setBlockName("deco_pipe_rim_green").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_green");
		ModBlocks.deco_pipe_rim_green_rusted = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_green_rusty", 1).setBlockName("deco_pipe_rim_green_rusted").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_green_rusty");
		ModBlocks.deco_pipe_rim_red = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_red", 1).setBlockName("deco_pipe_rim_red").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_red");
		ModBlocks.deco_pipe_rim_marked = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_marked", 1).setBlockName("deco_pipe_rim_marked").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_marked");
		ModBlocks.deco_pipe_framed = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side", 3).setBlockName("deco_pipe_framed").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top");
		ModBlocks.deco_pipe_framed_rusted = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_rusty", 3).setBlockName("deco_pipe_framed_rusted").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_rusty");
		ModBlocks.deco_pipe_framed_green = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_green", 3).setBlockName("deco_pipe_framed_green").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_green");
		ModBlocks.deco_pipe_framed_green_rusted = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_green_rusty", 3).setBlockName("deco_pipe_framed_green_rusted").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_green_rusty");
		ModBlocks.deco_pipe_framed_red = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_red", 3).setBlockName("deco_pipe_framed_red").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_red");
		ModBlocks.deco_pipe_framed_marked = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_marked", 3).setBlockName("deco_pipe_framed_marked").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_marked");
		ModBlocks.deco_pipe_quad = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side", 2).setBlockName("deco_pipe_quad").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top");
		ModBlocks.deco_pipe_quad_rusted = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_rusty", 2).setBlockName("deco_pipe_quad_rusted").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_rusty");
		ModBlocks.deco_pipe_quad_green = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_green", 2).setBlockName("deco_pipe_quad_green").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_green");
		ModBlocks.deco_pipe_quad_green_rusted = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_green_rusty", 2).setBlockName("deco_pipe_quad_green_rusted").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_green_rusty");
		ModBlocks.deco_pipe_quad_red = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_red", 2).setBlockName("deco_pipe_quad_red").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_red");
		ModBlocks.deco_pipe_quad_marked = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_marked", 2).setBlockName("deco_pipe_quad_marked").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_marked");
		
		ModBlocks.broadcaster_pc = new PinkCloudBroadcaster(Material.iron).setBlockName("broadcaster_pc").setCreativeTab(MainRegistry.machineTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":broadcaster_pc");
		ModBlocks.geiger = new GeigerCounter(Material.iron).setBlockName("geiger").setCreativeTab(MainRegistry.machineTab).setHardness(15.0F).setResistance(0.25F).setBlockTextureName(RefStrings.MODID + ":geiger");
		ModBlocks.hev_battery = new HEVBattery(Material.iron).setBlockName("hev_battery").setCreativeTab(MainRegistry.machineTab).setLightLevel(10F/15F).setHardness(0.5F).setResistance(0.25F).setBlockTextureName(RefStrings.MODID + ":hev_battery");
		
		ModBlocks.fence_metal = new BlockMetalFence(Material.iron).setBlockName("fence_metal").setCreativeTab(MainRegistry.machineTab).setHardness(15.0F).setResistance(0.25F).setBlockTextureName(RefStrings.MODID + ":fence_metal");
		
		ModBlocks.ash_digamma = new BlockAshes(Material.sand).setBlockName("ash_digamma").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.machineTab).setHardness(0.5F).setResistance(150.0F).setBlockTextureName(RefStrings.MODID + ":ash_digamma");
		ModBlocks.sand_boron = new BlockFalling(Material.sand).setBlockName("sand_boron").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.machineTab).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":sand_boron");
		ModBlocks.sand_lead = new BlockFalling(Material.sand).setBlockName("sand_lead").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.machineTab).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":sand_lead");
		ModBlocks.sand_uranium = new BlockFalling(Material.sand).setBlockName("sand_uranium").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.machineTab).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":sand_uranium");
		ModBlocks.sand_polonium = new BlockFalling(Material.sand).setBlockName("sand_polonium").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.machineTab).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":sand_polonium");
		ModBlocks.sand_quartz = new BlockFalling(Material.sand).setBlockName("sand_quartz").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.machineTab).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":sand_quartz");
		ModBlocks.sand_gold = new BlockGoldSand(Material.sand).setBlockName("sand_gold").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.machineTab).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":sand_gold");
		ModBlocks.sand_gold198 = new BlockGoldSand(Material.sand).setBlockName("sand_gold198").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.machineTab).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":sand_gold");
		ModBlocks.glass_boron = new BlockNTMGlassCT(0, RefStrings.MODID + ":glass_boron", Material.glass).setBlockName("glass_boron").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.machineTab).setHardness(0.3F);
		ModBlocks.glass_lead = new BlockNTMGlassCT(0, RefStrings.MODID + ":glass_lead", Material.glass).setBlockName("glass_lead").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.machineTab).setHardness(0.3F);
		ModBlocks.glass_uranium = new BlockNTMGlassCT(1, RefStrings.MODID + ":glass_uranium", Material.glass).setBlockName("glass_uranium").setLightLevel(5F/15F).setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.machineTab).setHardness(0.3F);
		ModBlocks.glass_trinitite = new BlockNTMGlassCT(1, RefStrings.MODID + ":glass_trinitite", Material.glass).setBlockName("glass_trinitite").setLightLevel(5F/15F).setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.machineTab).setHardness(0.3F);
		ModBlocks.glass_polonium = new BlockNTMGlassCT(1, RefStrings.MODID + ":glass_polonium", Material.glass).setBlockName("glass_polonium").setLightLevel(5F/15F).setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.machineTab).setHardness(0.3F);
		ModBlocks.glass_ash = new BlockNTMGlassCT(1, RefStrings.MODID + ":glass_ash", Material.glass).setBlockName("glass_ash").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.machineTab).setHardness(3F);
		ModBlocks.glass_quartz = new BlockNTMGlassCT(0, RefStrings.MODID + ":glass_quartz", Material.packedIce, true).setBlockName("glass_quartz").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeGlass).setHardness(1.0F).setResistance(40.0F).setBlockTextureName(RefStrings.MODID + "glass_quartz");
		
		ModBlocks.mush = new BlockMush(Material.plants).setBlockName("mush").setCreativeTab(MainRegistry.blockTab).setLightLevel(0.5F).setStepSound(Block.soundTypeGrass).setBlockTextureName(RefStrings.MODID + ":mush");
		ModBlocks.mush_block = new BlockMushHuge(Material.plants).setBlockName("mush_block").setLightLevel(1.0F).setStepSound(Block.soundTypeGrass).setHardness(0.2F).setBlockTextureName(RefStrings.MODID + ":mush_block_skin");
		ModBlocks.mush_block_stem = new BlockMushHuge(Material.plants).setBlockName("mush_block_stem").setLightLevel(1.0F).setStepSound(Block.soundTypeGrass).setHardness(0.2F).setBlockTextureName(RefStrings.MODID + ":mush_block_stem");
		ModBlocks.glyphid_base = new BlockBase(Material.coral).setBlockName("glyphid_base").setStepSound(Block.soundTypeCloth).setHardness(0.5F);
		ModBlocks.glyphid_spawner = new BlockGlyphidSpawner(Material.coral).setBlockName("glyphid_spawner").setStepSound(Block.soundTypeCloth).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":glyphid_base");
		
		ModBlocks.plant_flower = new BlockNTMFlower().setBlockName("plant_flower").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeGrass).setHardness(0.0F);
		ModBlocks.plant_tall = new BlockTallPlant().setBlockName("plant_tall").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeGrass).setHardness(0.0F);
		ModBlocks.plant_dead = new BlockDeadPlant().setBlockName("plant_dead").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeGrass).setHardness(0.0F);
		ModBlocks.reeds = new BlockReeds().setBlockName("plant_reeds").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeGrass).setHardness(0.0F);

		ModBlocks.waste_earth = new WasteEarth(Material.ground, true).setBlockName("waste_earth").setStepSound(Block.soundTypeGrass).setCreativeTab(MainRegistry.blockTab).setHardness(0.6F).setBlockTextureName(RefStrings.MODID + ":waste_earth");
		ModBlocks.waste_mycelium = new WasteEarth(Material.ground, true).setBlockName("waste_mycelium").setStepSound(Block.soundTypeGrass).setLightLevel(1F).setCreativeTab(MainRegistry.blockTab).setHardness(0.6F).setBlockTextureName(RefStrings.MODID + ":waste_mycelium_side");
		ModBlocks.waste_trinitite = new BlockOre(Material.sand).noFortune().setBlockName("waste_trinitite").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":waste_trinitite");
		ModBlocks.waste_trinitite_red = new BlockOre(Material.sand).noFortune().setBlockName("waste_trinitite_red").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":waste_trinitite_red");
		ModBlocks.waste_log = new WasteLog(Material.wood).setBlockName("waste_log").setStepSound(Block.soundTypeWood).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(2.5F);
		ModBlocks.waste_leaves = new WasteLeaves(Material.leaves).setBlockName("waste_leaves").setStepSound(Block.soundTypeGrass).setCreativeTab(MainRegistry.blockTab).setHardness(0.1F).setBlockTextureName(RefStrings.MODID + ":waste_leaves");
		ModBlocks.waste_planks = new BlockOre(Material.wood).setBlockName("waste_planks").setStepSound(Block.soundTypeWood).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":waste_planks");
		ModBlocks.frozen_dirt = new BlockOre(Material.ground).setBlockName("frozen_dirt").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":frozen_dirt");
		ModBlocks.frozen_grass = new WasteEarth(Material.ground, false).setBlockName("frozen_grass").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(2.5F);
		ModBlocks.frozen_log = new WasteLog(Material.wood).setBlockName("frozen_log").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(2.5F);
		ModBlocks.frozen_planks = new BlockOre(Material.wood).setBlockName("frozen_planks").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":frozen_planks");
		ModBlocks.fallout = new BlockFallout(Material.snow).setBlockName("fallout").setStepSound(Block.soundTypeGravel).setCreativeTab(MainRegistry.blockTab).setHardness(0.1F).setLightOpacity(0).setBlockTextureName(RefStrings.MODID + ":ash");
		ModBlocks.foam_layer = new BlockLayering(Material.snow).setBlockName("foam_layer").setStepSound(Block.soundTypeSnow).setCreativeTab(MainRegistry.blockTab).setHardness(0.1F).setLightOpacity(0).setBlockTextureName(RefStrings.MODID + ":foam");
		ModBlocks.sand_boron_layer = new BlockLayering(Material.sand).setBlockName("sand_boron_layer").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.blockTab).setHardness(0.1F).setLightOpacity(0).setBlockTextureName(RefStrings.MODID + ":sand_boron");
		ModBlocks.leaves_layer = new BlockLayering(Material.leaves).setBlockName("leaves_layer").setStepSound(Block.soundTypeGrass).setCreativeTab(MainRegistry.blockTab).setHardness(0.1F).setLightOpacity(0).setBlockTextureName(RefStrings.MODID + ":waste_leaves");
		
		ModBlocks.burning_earth = new WasteEarth(Material.ground, true).setBlockName("burning_earth").setStepSound(Block.soundTypeGrass).setCreativeTab(MainRegistry.blockTab).setHardness(0.6F).setBlockTextureName(RefStrings.MODID + ":burning_earth");
		ModBlocks.tektite = new BlockGeneric(Material.sand).setBlockName("tektite").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":tektite");
		ModBlocks.ore_tektite_osmiridium = new BlockGeneric(Material.sand).setBlockName("ore_tektite_osmiridium").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":ore_tektite_osmiridium");
		ModBlocks.impact_dirt = new BlockDirt(Material.ground, true).setBlockName("impact_dirt").setStepSound(Block.soundTypeGravel).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":waste_earth_bottom");		
		ModBlocks.dirt_dead = new BlockFalling(Material.ground).setBlockName("dirt_dead").setStepSound(Block.soundTypeGravel).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":dirt_dead");
		ModBlocks.dirt_oily = new BlockFalling(Material.ground).setBlockName("dirt_oily").setStepSound(Block.soundTypeGravel).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":dirt_oily");
		ModBlocks.sand_dirty = new BlockFalling(Material.sand).setBlockName("sand_dirty").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":sand_dirty");
		ModBlocks.sand_dirty_red = new BlockFalling(Material.sand).setBlockName("sand_dirty_red").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":sand_dirty_red");
		ModBlocks.stone_cracked = new BlockFalling(Material.rock).setBlockName("stone_cracked").setStepSound(Block.soundTypeStone).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setBlockTextureName(RefStrings.MODID + ":stone_cracked");
		
		ModBlocks.sellafield_slaked = new BlockGeneric(Material.rock).setBlockName("sellafield_slaked").setStepSound(Block.soundTypeStone).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setBlockTextureName(RefStrings.MODID + ":sellafield_slaked");
		ModBlocks.sellafield = new BlockSellafield(Material.rock).setBlockName("sellafield").setStepSound(Block.soundTypeStone).setHardness(5.0F).setBlockTextureName(RefStrings.MODID + ":sellafield_0");
		/*sellafield_0 = new BlockHazard(Material.rock).setBlockName("sellafield_0").setStepSound(Block.soundTypeStone).setHardness(5.0F).setBlockTextureName(RefStrings.MODID + ":sellafield_0");
		sellafield_1 = new BlockHazard(Material.rock).setBlockName("sellafield_1").setStepSound(Block.soundTypeStone).setHardness(5.0F).setBlockTextureName(RefStrings.MODID + ":sellafield_1");
		sellafield_2 = new BlockHazard(Material.rock).setBlockName("sellafield_2").setStepSound(Block.soundTypeStone).setHardness(5.0F).setBlockTextureName(RefStrings.MODID + ":sellafield_2");
		sellafield_3 = new BlockHazard(Material.rock).setBlockName("sellafield_3").setStepSound(Block.soundTypeStone).setHardness(5.0F).setBlockTextureName(RefStrings.MODID + ":sellafield_3");
		sellafield_4 = new BlockHazard(Material.rock).setBlockName("sellafield_4").setStepSound(Block.soundTypeStone).setHardness(5.0F).setBlockTextureName(RefStrings.MODID + ":sellafield_4");
		sellafield_core = new BlockHazard(Material.rock).setBlockName("sellafield_core").setStepSound(Block.soundTypeStone).setHardness(10.0F).setBlockTextureName(RefStrings.MODID + ":sellafield_core");*/
		
		ModBlocks.geysir_water = new BlockGeysir(Material.rock).setBlockName("geysir_water").setStepSound(Block.soundTypeStone).setHardness(5.0F);
		ModBlocks.geysir_chlorine = new BlockGeysir(Material.rock).setBlockName("geysir_chlorine").setStepSound(Block.soundTypeStone).setHardness(5.0F);
		ModBlocks.geysir_vapor = new BlockGeysir(Material.rock).setBlockName("geysir_vapor").setStepSound(Block.soundTypeStone).setHardness(5.0F);
		ModBlocks.geysir_nether = new BlockGeysir(Material.rock).setBlockName("geysir_nether").setLightLevel(1.0F).setStepSound(Block.soundTypeStone).setHardness(2.0F);
		
		ModBlocks.observer_off = new BlockObserver(Material.iron, false).setBlockName("observer_off").setStepSound(Block.soundTypeStone).setHardness(2.0F);
		ModBlocks.observer_on = new BlockObserver(Material.iron, true).setBlockName("observer_on").setStepSound(Block.soundTypeStone).setHardness(2.0F);
		
		ModBlocks.nuke_gadget = new NukeGadget(Material.iron).setBlockName("nuke_gadget").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F).setBlockTextureName(RefStrings.MODID + ":theGadget");
		ModBlocks.nuke_boy = new NukeBoy(Material.iron).setBlockName("nuke_boy").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F).setBlockTextureName(RefStrings.MODID + ":lilBoy");
		ModBlocks.nuke_man = new NukeMan(Material.iron).setBlockName("nuke_man").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F).setBlockTextureName(RefStrings.MODID + ":fatMan");
		ModBlocks.nuke_mike = new NukeMike(Material.iron).setBlockName("nuke_mike").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F).setBlockTextureName(RefStrings.MODID + ":ivyMike");
		ModBlocks.nuke_tsar = new NukeTsar(Material.iron).setBlockName("nuke_tsar").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F).setBlockTextureName(RefStrings.MODID + ":tsarBomba");
		ModBlocks.nuke_fleija = new NukeFleija(Material.iron).setBlockName("nuke_fleija").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F).setBlockTextureName(RefStrings.MODID + ":fleija");
		ModBlocks.nuke_prototype = new NukePrototype(Material.iron).setBlockName("nuke_prototype").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200).setBlockTextureName(RefStrings.MODID + ":prototype");
		ModBlocks.nuke_custom = new NukeCustom(Material.iron).setBlockName("nuke_custom").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F).setBlockTextureName(RefStrings.MODID + ":custom");
		ModBlocks.nuke_solinium = new NukeSolinium(Material.iron).setBlockName("nuke_solinium").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F).setBlockTextureName(RefStrings.MODID + ":nuke_solinium");
		ModBlocks.nuke_n2 = new NukeN2(Material.iron).setBlockName("nuke_n2").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F).setBlockTextureName(RefStrings.MODID + ":nuke_n2");
		ModBlocks.nuke_n45 = new NukeN45(Material.iron).setBlockName("nuke_n45").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F).setBlockTextureName(RefStrings.MODID + ":code");
		ModBlocks.nuke_fstbmb = new NukeBalefire(Material.iron).setBlockName("nuke_fstbmb").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F).setBlockTextureName(RefStrings.MODID + ":nuke_fstbmb");
		
		ModBlocks.bomb_multi = new BombMulti(Material.iron).setBlockName("bomb_multi").setCreativeTab(MainRegistry.nukeTab).setResistance(200.0F).setBlockTextureName(RefStrings.MODID + ":bomb_multi1");
		//bomb_multi_large = new BombMultiLarge(Material.iron).setBlockName("bomb_multi_large").setCreativeTab(MainRegistry.tabNuke).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":bomb_multi_large");
		
		ModBlocks.flame_war = new BombFlameWar(Material.iron).setBlockName("flame_war").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F).setBlockTextureName(RefStrings.MODID + ":flame_war");
		ModBlocks.float_bomb = new BombFloat(Material.iron).setBlockName("float_bomb").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F);
		ModBlocks.therm_endo = new BombThermo(Material.iron).setBlockName("therm_endo").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F);
		ModBlocks.therm_exo = new BombThermo(Material.iron).setBlockName("therm_exo").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F);
		ModBlocks.emp_bomb = new BombFloat(Material.iron).setBlockName("emp_bomb").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F);
		ModBlocks.det_cord = new DetCord(Material.iron).setBlockName("det_cord").setCreativeTab(MainRegistry.nukeTab).setHardness(0.1F).setResistance(0.0F).setBlockTextureName(RefStrings.MODID + ":det_cord");
		ModBlocks.det_charge = new ExplosiveCharge(Material.iron).setBlockName("det_charge").setCreativeTab(MainRegistry.nukeTab).setHardness(0.1F).setResistance(0.0F).setBlockTextureName(RefStrings.MODID + ":det_charge");
		ModBlocks.det_nuke = new ExplosiveCharge(Material.iron).setBlockName("det_nuke").setCreativeTab(MainRegistry.nukeTab).setHardness(0.1F).setResistance(0.0F).setBlockTextureName(RefStrings.MODID + ":det_nuke");
		ModBlocks.det_miner = new DetMiner(Material.iron, RefStrings.MODID + ":det_miner_top").setBlockName("det_miner").setCreativeTab(MainRegistry.nukeTab).setHardness(0.1F).setResistance(0.0F).setBlockTextureName(RefStrings.MODID + ":det_miner_side");
		ModBlocks.red_barrel = new RedBarrel(Material.iron).setBlockName("red_barrel").setCreativeTab(MainRegistry.nukeTab).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":barrel_red");
		ModBlocks.pink_barrel = new RedBarrel(Material.iron).setBlockName("pink_barrel").setCreativeTab(MainRegistry.nukeTab).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":barrel_pink");
		ModBlocks.yellow_barrel = new YellowBarrel(Material.iron).setBlockName("yellow_barrel").setCreativeTab(MainRegistry.nukeTab).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":barrel_yellow");
		ModBlocks.vitrified_barrel = new YellowBarrel(Material.iron).setBlockName("vitrified_barrel").setCreativeTab(MainRegistry.nukeTab).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":barrel_vitrified");
		ModBlocks.lox_barrel = new RedBarrel(Material.iron).setBlockName("lox_barrel").setCreativeTab(MainRegistry.nukeTab).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":barrel_lox");
		ModBlocks.taint_barrel = new RedBarrel(Material.iron).setBlockName("taint_barrel").setCreativeTab(MainRegistry.nukeTab).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":barrel_taint");
		ModBlocks.crashed_balefire = new BlockCrashedBomb(Material.iron).setBlockName("crashed_bomb").setCreativeTab(MainRegistry.nukeTab).setBlockUnbreakable().setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":crashed_balefire");
		ModBlocks.fireworks = new BlockFireworks(Material.iron).setBlockName("fireworks").setCreativeTab(MainRegistry.nukeTab).setResistance(5.0F);
		ModBlocks.charge_dynamite = new BlockChargeDynamite().setBlockName("charge_dynamite").setCreativeTab(MainRegistry.nukeTab).setResistance(1.0F);
		ModBlocks.charge_miner = new BlockChargeMiner().setBlockName("charge_miner").setCreativeTab(MainRegistry.nukeTab).setResistance(1.0F);
		ModBlocks.charge_c4 = new BlockChargeC4().setBlockName("charge_c4").setCreativeTab(MainRegistry.nukeTab).setResistance(1.0F);
		ModBlocks.charge_semtex = new BlockChargeSemtex().setBlockName("charge_semtex").setCreativeTab(MainRegistry.nukeTab).setResistance(1.0F);
		ModBlocks.mine_ap = new Landmine(Material.iron).setBlockName("mine_ap").setCreativeTab(MainRegistry.nukeTab).setHardness(1.0F).setBlockTextureName(RefStrings.MODID + ":mine_ap");
		ModBlocks.mine_he = new Landmine(Material.iron).setBlockName("mine_he").setCreativeTab(MainRegistry.nukeTab).setHardness(1.0F).setBlockTextureName(RefStrings.MODID + ":mine_he");
		ModBlocks.mine_shrap = new Landmine(Material.iron).setBlockName("mine_shrap").setCreativeTab(MainRegistry.nukeTab).setHardness(1.0F).setBlockTextureName(RefStrings.MODID + ":mine_shrap");
		ModBlocks.mine_fat = new Landmine(Material.iron).setBlockName("mine_fat").setCreativeTab(MainRegistry.nukeTab).setHardness(1.0F).setBlockTextureName(RefStrings.MODID + ":mine_fat");
		ModBlocks.dynamite = new BlockDynamite().setBlockName("dynamite").setStepSound(Block.soundTypeGrass).setCreativeTab(MainRegistry.nukeTab).setHardness(0.0F).setBlockTextureName(RefStrings.MODID + ":dynamite");
		ModBlocks.tnt = new BlockTNT().setBlockName("tnt_ntm").setStepSound(Block.soundTypeGrass).setCreativeTab(MainRegistry.nukeTab).setHardness(0.0F).setBlockTextureName(RefStrings.MODID + ":tnt");
		ModBlocks.semtex = new BlockSemtex().setBlockName("semtex").setStepSound(Block.soundTypeGrass).setCreativeTab(MainRegistry.nukeTab).setHardness(0.0F).setBlockTextureName(RefStrings.MODID + ":semtex");
		ModBlocks.c4 = new BlockC4().setBlockName("c4").setStepSound(Block.soundTypeGrass).setCreativeTab(MainRegistry.nukeTab).setHardness(0.0F).setBlockTextureName(RefStrings.MODID + ":c4");
		ModBlocks.fissure_bomb = new BlockFissureBomb().setBlockName("fissure_bomb").setStepSound(Block.soundTypeGrass).setCreativeTab(MainRegistry.nukeTab).setHardness(0.0F).setBlockTextureName(RefStrings.MODID + ":fissure_bomb");

		ModBlocks.pump_steam = new MachinePump().setBlockName("pump_steam").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_copper");
		ModBlocks.pump_electric = new MachinePump().setBlockName("pump_electric").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		
		ModBlocks.heater_firebox = new HeaterFirebox().setBlockName("heater_firebox").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.heater_oven = new HeaterOven().setBlockName("heater_oven").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":brick_fire");
		ModBlocks.heater_oilburner = new HeaterOilburner().setBlockName("heater_oilburner").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.heater_electric = new HeaterElectric().setBlockName("heater_electric").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.heater_heatex = new HeaterHeatex().setBlockName("heater_heatex").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.machine_ashpit = new MachineAshpit().setBlockName("machine_ashpit").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName("stonebrick");
		
		ModBlocks.furnace_iron = new FurnaceIron().setBlockName("furnace_iron").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_aluminium");
		ModBlocks.furnace_steel = new FurnaceSteel().setBlockName("furnace_steel").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.furnace_combination = new FurnaceCombination().setBlockName("furnace_combination").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":brick_light_alt");
		ModBlocks.machine_stirling = new MachineStirling().setBlockName("machine_stirling").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.machine_stirling_steel = new MachineStirling().setBlockName("machine_stirling_steel").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.machine_stirling_creative = new MachineStirling().setBlockName("machine_stirling_creative").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.machine_sawmill = new MachineSawmill().setBlockName("machine_sawmill").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.machine_crucible = new MachineCrucible().setBlockName("machine_crucible").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":brick_fire");
		ModBlocks.machine_boiler = new MachineHeatBoiler().setBlockName("machine_boiler").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_copper");
		ModBlocks.machine_industrial_boiler = new MachineHeatBoilerIndustrial().setBlockName("machine_industrial_boiler").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		
		ModBlocks.foundry_mold = new FoundryMold().setBlockName("foundry_mold").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":brick_fire");
		ModBlocks.foundry_basin = new FoundryBasin().setBlockName("foundry_basin").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":brick_fire");
		ModBlocks.foundry_channel = new FoundryChannel().setBlockName("foundry_channel").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":brick_fire");
		ModBlocks.foundry_tank = new FoundryTank().setBlockName("foundry_tank").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":brick_fire");
		ModBlocks.foundry_outlet = new FoundryOutlet().setBlockName("foundry_outlet").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":brick_fire");
		ModBlocks.foundry_slagtap = new FoundrySlagtap().setBlockName("foundry_slagtap").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":brick_fire");
		ModBlocks.slag = new BlockDynamicSlag().setBlockName("slag").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":slag");
		
		ModBlocks.machine_difurnace_off = new MachineDiFurnace(false).setBlockName("machine_difurnace_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.machine_difurnace_on = new MachineDiFurnace(true).setBlockName("machine_difurnace_on").setHardness(5.0F).setLightLevel(1.0F).setResistance(10.0F);
		ModBlocks.machine_difurnace_extension = new MachineDiFurnaceExtension().setBlockName("machine_difurnace_extension").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.machine_difurnace_rtg_off = new MachineDiFurnaceRTG(false).setBlockName("machine_difurnace_rtg_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.machine_difurnace_rtg_on = new MachineDiFurnaceRTG(true).setBlockName("machine_difurnace_rtg_on").setHardness(5.0F).setResistance(10.0F).setLightLevel(2.0F).setCreativeTab(null);
		
		ModBlocks.machine_centrifuge = new MachineCentrifuge(Material.iron).setBlockName("machine_centrifuge").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.machine_gascent = new MachineGasCent(Material.iron).setBlockName("machine_gascent").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.machine_fel = new MachineFEL(Material.iron).setBlockName("machine_fel").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.machine_silex = new MachineSILEX(Material.iron).setBlockName("machine_silex").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.machine_crystallizer = new MachineCrystallizer(Material.iron).setBlockName("machine_crystallizer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_crystallizer");
		
		ModBlocks.machine_uf6_tank = new MachineUF6Tank(Material.iron).setBlockName("machine_uf6_tank").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		
		ModBlocks.machine_puf6_tank = new MachinePuF6Tank(Material.iron).setBlockName("machine_puf6_tank").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		
		ModBlocks.machine_reactor_breeding = new MachineReactorBreeding(Material.iron).setBlockName("machine_reactor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_reactor");
		
		ModBlocks.machine_nuke_furnace_off = new MachineNukeFurnace(false).setBlockName("machine_nuke_furnace_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.machine_nuke_furnace_on = new MachineNukeFurnace(true).setBlockName("machine_nuke_furnace_on").setHardness(5.0F).setLightLevel(1.0F).setResistance(10.0F);
		
		ModBlocks.machine_rtg_furnace_off = new MachineRtgFurnace(false).setBlockName("machine_rtg_furnace_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.machine_rtg_furnace_on = new MachineRtgFurnace(true).setBlockName("machine_rtg_furnace_on").setHardness(5.0F).setLightLevel(1.0F).setResistance(10.0F);

		ModBlocks.machine_generator = new MachineGenerator(Material.iron).setBlockName("machine_generator").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
		ModBlocks.machine_industrial_generator = new MachineIGenerator(Material.iron).setBlockName("machine_industrial_generator").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":industrial_generator");
		ModBlocks.machine_cyclotron = new MachineCyclotron(Material.iron).setBlockName("machine_cyclotron").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":cyclotron");
		ModBlocks.machine_radgen = new MachineRadGen(Material.iron).setBlockName("machine_radgen").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_radgen");

		ModBlocks.hadron_plating = new BlockHadronPlating(Material.iron).setStepSound(Block.soundTypeMetal).setBlockName("hadron_plating").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_plating");
		ModBlocks.hadron_plating_blue = new BlockHadronPlating(Material.iron).setStepSound(Block.soundTypeMetal).setBlockName("hadron_plating_blue").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_plating_blue");
		ModBlocks.hadron_plating_black = new BlockHadronPlating(Material.iron).setStepSound(Block.soundTypeMetal).setBlockName("hadron_plating_black").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_plating_black");
		ModBlocks.hadron_plating_yellow = new BlockHadronPlating(Material.iron).setStepSound(Block.soundTypeMetal).setBlockName("hadron_plating_yellow").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_plating_yellow");
		ModBlocks.hadron_plating_striped = new BlockHadronPlating(Material.iron).setStepSound(Block.soundTypeMetal).setBlockName("hadron_plating_striped").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_plating_striped");
		ModBlocks.hadron_plating_voltz = new BlockHadronPlating(Material.iron).setStepSound(Block.soundTypeMetal).setBlockName("hadron_plating_voltz").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_plating_voltz");
		ModBlocks.hadron_plating_glass = new BlockNTMGlass(0, RefStrings.MODID + ":hadron_plating_glass", Material.iron).setStepSound(Block.soundTypeMetal).setBlockName("hadron_plating_glass").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_plating_glass");
		ModBlocks.hadron_coil_alloy = new BlockHadronCoil(Material.iron, 10).setStepSound(Block.soundTypeMetal).setBlockName("hadron_coil_alloy").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_coil_alloy");
		ModBlocks.hadron_coil_gold = new BlockHadronCoil(Material.iron, 25).setStepSound(Block.soundTypeMetal).setBlockName("hadron_coil_gold").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_coil_gold");
		ModBlocks.hadron_coil_neodymium = new BlockHadronCoil(Material.iron, 50).setStepSound(Block.soundTypeMetal).setBlockName("hadron_coil_neodymium").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_coil_neodymium");
		ModBlocks.hadron_coil_magtung = new BlockHadronCoil(Material.iron, 100).setStepSound(Block.soundTypeMetal).setBlockName("hadron_coil_magtung").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_coil_magtung");
		ModBlocks.hadron_coil_schrabidium = new BlockHadronCoil(Material.iron, 250).setStepSound(Block.soundTypeMetal).setBlockName("hadron_coil_schrabidium").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_coil_schrabidium");
		ModBlocks.hadron_coil_schrabidate = new BlockHadronCoil(Material.iron, 500).setStepSound(Block.soundTypeMetal).setBlockName("hadron_coil_schrabidate").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_coil_schrabidate");
		ModBlocks.hadron_coil_starmetal = new BlockHadronCoil(Material.iron, 1000).setStepSound(Block.soundTypeMetal).setBlockName("hadron_coil_starmetal").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_coil_starmetal");
		ModBlocks.hadron_coil_chlorophyte = new BlockHadronCoil(Material.iron, 2500).setStepSound(Block.soundTypeMetal).setBlockName("hadron_coil_chlorophyte").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_coil_chlorophyte");
		ModBlocks.hadron_coil_mese = new BlockHadronCoil(Material.iron, 10000).setStepSound(Block.soundTypeMetal).setBlockName("hadron_coil_mese").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_coil_mese");
		ModBlocks.hadron_power = new BlockHadronPower(Material.iron, 1000000L).setStepSound(Block.soundTypeMetal).setBlockName("hadron_power").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_power");
		ModBlocks.hadron_power_10m = new BlockHadronPower(Material.iron, 10000000L).setStepSound(Block.soundTypeMetal).setBlockName("hadron_power_10m").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_power");
		ModBlocks.hadron_power_100m = new BlockHadronPower(Material.iron, 100000000L).setStepSound(Block.soundTypeMetal).setBlockName("hadron_power_100m").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_power");
		ModBlocks.hadron_power_1g = new BlockHadronPower(Material.iron, 1000000000L).setStepSound(Block.soundTypeMetal).setBlockName("hadron_power_1g").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_power");
		ModBlocks.hadron_power_10g = new BlockHadronPower(Material.iron, 10000000000L).setStepSound(Block.soundTypeMetal).setBlockName("hadron_power_10g").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_power");
		ModBlocks.hadron_diode = new BlockHadronDiode(Material.iron).setStepSound(Block.soundTypeMetal).setBlockName("hadron_diode").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.hadron_analysis = new BlockHadronPlating(Material.iron).setStepSound(Block.soundTypeMetal).setBlockName("hadron_analysis").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_analysis");
		ModBlocks.hadron_analysis_glass = new BlockNTMGlass(0, RefStrings.MODID + ":hadron_analysis_glass", Material.iron).setStepSound(Block.soundTypeMetal).setBlockName("hadron_analysis_glass").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_analysis_glass");
		ModBlocks.hadron_access = new BlockHadronAccess(Material.iron).setStepSound(Block.soundTypeMetal).setBlockName("hadron_access").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_access");
		ModBlocks.hadron_core = new BlockHadronCore(Material.iron).setStepSound(Block.soundTypeMetal).setBlockName("hadron_core").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_core");
		ModBlocks.hadron_cooler = new BlockHadronCooler(Material.iron).setBlockName("hadron_cooler").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
		
		ModBlocks.machine_electric_furnace_off = new MachineElectricFurnace(false).setBlockName("machine_electric_furnace_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.machine_electric_furnace_on = new MachineElectricFurnace(true).setBlockName("machine_electric_furnace_on").setHardness(5.0F).setLightLevel(1.0F).setResistance(10.0F);
		ModBlocks.machine_arc_furnace_off = new MachineArcFurnace(false).setBlockName("machine_arc_furnace_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.machine_arc_furnace_on = new MachineArcFurnace(true).setBlockName("machine_arc_furnace_on").setHardness(5.0F).setLightLevel(1.0F).setResistance(10.0F);
		ModBlocks.machine_microwave = new MachineMicrowave(Material.iron).setBlockName("machine_microwave").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_microwave");

		//machine_deuterium = new MachineDeuterium(Material.iron).setBlockName("machine_deuterium").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);

		ModBlocks.machine_battery_potato = new MachineBattery(Material.iron, 10_000).setBlockName("machine_battery_potato").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.machine_battery = new MachineBattery(Material.iron, 1_000_000).setBlockName("machine_battery").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.machine_lithium_battery = new MachineBattery(Material.iron, 50_000_000).setBlockName("machine_lithium_battery").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.machine_schrabidium_battery = new MachineBattery(Material.iron, 25_000_000_000L).setBlockName("machine_schrabidium_battery").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.machine_dineutronium_battery = new MachineBattery(Material.iron, 1_000_000_000_000L).setBlockName("machine_dineutronium_battery").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.machine_fensu = new MachineFENSU(Material.iron).setBlockName("machine_fensu").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_fensu");
		
		ModBlocks.capacitor_bus = new MachineCapacitorBus(Material.iron).setBlockName("capacitor_bus").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.capacitor_copper = new MachineCapacitor(Material.iron, 1_000_000L, "copper").setBlockName("capacitor_copper").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_copper");
		ModBlocks.capacitor_gold = new MachineCapacitor(Material.iron, 5_000_000L, "gold").setBlockName("capacitor_gold").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName("gold_block");
		ModBlocks.capacitor_niobium = new MachineCapacitor(Material.iron, 25_000_000L, "niobium").setBlockName("capacitor_niobium").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_niobium");
		ModBlocks.capacitor_tantalium = new MachineCapacitor(Material.iron, 150_000_000L, "tantalium").setBlockName("capacitor_tantalium").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_tantalium");
		ModBlocks.capacitor_schrabidate = new MachineCapacitor(Material.iron, 50_000_000_000L, "schrabidate").setBlockName("capacitor_schrabidate").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_schrabidate");
		
		machine_coal_off = new MachineCoal(false).setBlockName("machine_coal_off").setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_copper");
		machine_coal_on = new MachineCoal(true).setBlockName("machine_coal_on").setHardness(5.0F).setLightLevel(1.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_copper");
		machine_wood_burner = new MachineWoodBurner(Material.iron).setBlockName("machine_wood_burner").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");

		ModBlocks.machine_diesel = new MachineDiesel().setBlockName("machine_diesel").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.machine_combustion_engine = new MachineCombustionEngine().setBlockName("machine_combustion_engine").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");

		ModBlocks.machine_shredder = new MachineShredder(Material.iron).setBlockName("machine_shredder").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.machine_shredder_large = new MachineShredderLarge(Material.iron).setBlockName("machine_shredder_large").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":code");

		ModBlocks.machine_combine_factory = new MachineCMBFactory(Material.iron).setBlockName("machine_combine_factory").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);

		ModBlocks.machine_teleporter = new MachineTeleporter(Material.iron).setBlockName("machine_teleporter").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.teleanchor = new MachineTeleanchor().setBlockName("teleanchor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.field_disturber = new MachineFieldDisturber().setBlockName("field_disturber").setHardness(5.0F).setResistance(200.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":field_disturber");

		ModBlocks.machine_rtg_grey = new MachineRTG(Material.iron).setBlockName("machine_rtg_grey").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rtg");
		ModBlocks.machine_amgen = new MachineAmgen(Material.iron).setBlockName("machine_amgen").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.machine_geo = new MachineAmgen(Material.iron).setBlockName("machine_geo").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.machine_minirtg = new MachineMiniRTG(Material.iron).setBlockName("machine_minirtg").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rtg_cell");
		ModBlocks.machine_powerrtg = new MachineMiniRTG(Material.iron).setBlockName("machine_powerrtg").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rtg_polonium");
		ModBlocks.machine_radiolysis = new MachineRadiolysis(Material.iron).setBlockName("machine_radiolysis").setHardness(10.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel_machine");
		ModBlocks.machine_hephaestus = new MachineHephaestus(Material.iron).setBlockName("machine_hephaestus").setHardness(10.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel_machine");

		ModBlocks.red_wire_coated = new WireCoated(Material.iron).setBlockName("red_wire_coated").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":red_wire_coated");
		ModBlocks.red_cable = new BlockCable(Material.iron).setBlockName("red_cable").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":cable_neo");
		ModBlocks.red_cable_classic = new BlockCable(Material.iron).setBlockName("red_cable_classic").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":red_cable_classic");
		ModBlocks.red_cable_paintable = new BlockCablePaintable().setBlockName("red_cable_paintable").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.red_cable_gauge = new BlockCableGauge().setBlockName("red_cable_gauge").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.red_connector = new ConnectorRedWire(Material.iron).setBlockName("red_connector").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":red_connector");
		ModBlocks.red_pylon = new PylonRedWire(Material.iron).setBlockName("red_pylon").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":red_pylon");
		ModBlocks.red_pylon_large = new PylonLarge(Material.iron).setBlockName("red_pylon_large").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":red_pylon_large");
		ModBlocks.substation = new Substation(Material.iron).setBlockName("substation").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":substation");
		ModBlocks.cable_switch = new CableSwitch(Material.iron).setBlockName("cable_switch").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.cable_detector = new CableDetector(Material.iron).setBlockName("cable_detector").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.cable_diode = new CableDiode(Material.iron).setBlockName("cable_diode").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":cable_diode");
		ModBlocks.machine_detector = new PowerDetector(Material.iron).setBlockName("machine_detector").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_detector_off");
		ModBlocks.fluid_duct = new BlockFluidDuct(Material.iron).setBlockName("fluid_duct").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":fluid_duct_icon");
		ModBlocks.fluid_duct_solid = new BlockFluidDuctSolid(Material.iron).setBlockName("fluid_duct_solid").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":fluid_duct_solid");
		ModBlocks.fluid_duct_neo = new FluidDuctStandard(Material.iron).setBlockName("fluid_duct_neo").setStepSound(ModSoundTypes.pipe).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":pipe_neo");
		ModBlocks.fluid_duct_box = new FluidDuctBox(Material.iron).setBlockName("fluid_duct_box").setStepSound(ModSoundTypes.pipe).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fluid_duct_box");
		ModBlocks.fluid_duct_exhaust = new FluidDuctBoxExhaust(Material.iron).setBlockName("fluid_duct_exhaust").setStepSound(ModSoundTypes.pipe).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fluid_duct_box");
		ModBlocks.fluid_duct_paintable = new FluidDuctPaintable().setBlockName("fluid_duct_paintable").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.fluid_duct_gauge = new FluidDuctGauge().setBlockName("fluid_duct_gauge").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.fluid_valve = new FluidValve(Material.iron).setBlockName("fluid_valve").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.fluid_switch = new FluidSwitch(Material.iron).setBlockName("fluid_switch").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.radio_torch_sender = new RadioTorchSender().setBlockName("radio_torch_sender").setHardness(0.1F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.radio_torch_receiver = new RadioTorchReceiver().setBlockName("radio_torch_receiver").setHardness(0.1F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.radio_torch_counter = new RadioTorchCounter().setBlockName("radio_torch_counter").setHardness(0.1F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rtty_counter");
		ModBlocks.radio_telex = new RadioTelex().setBlockName("radio_telex").setHardness(3F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":radio_telex");
		
		ModBlocks.conveyor = new BlockConveyor().setBlockName("conveyor").setHardness(2.0F).setResistance(2.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":conveyor");
		ModBlocks.conveyor_express = new BlockConveyorExpress().setBlockName("conveyor_express").setHardness(2.0F).setResistance(2.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":conveyor_express");
		//conveyor_classic = new BlockConveyorClassic().setBlockName("conveyor_classic").setHardness(2.0F).setResistance(2.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":conveyor");
		ModBlocks.conveyor_double = new BlockConveyorDouble().setBlockName("conveyor_double").setHardness(2.0F).setResistance(2.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":conveyor_double");
		ModBlocks.conveyor_triple = new BlockConveyorTriple().setBlockName("conveyor_triple").setHardness(2.0F).setResistance(2.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":conveyor_triple");
		ModBlocks.conveyor_chute = new BlockConveyorChute().setBlockName("conveyor_chute").setHardness(2.0F).setResistance(2.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":conveyor");
		ModBlocks.conveyor_lift = new BlockConveyorLift().setBlockName("conveyor_lift").setHardness(2.0F).setResistance(2.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":conveyor");
		ModBlocks.crane_extractor = new CraneExtractor().setBlockName("crane_extractor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.crane_inserter = new CraneInserter().setBlockName("crane_inserter").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.crane_grabber = new CraneGrabber().setBlockName("crane_grabber").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.crane_router = new CraneRouter().setBlockName("crane_router").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.crane_boxer = new CraneBoxer().setBlockName("crane_boxer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.crane_unboxer = new CraneUnboxer().setBlockName("crane_unboxer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.crane_splitter = new CraneSplitter().setBlockName("crane_splitter").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":crane_side");
		ModBlocks.fan = new MachineFan().setBlockName("fan").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.piston_inserter = new PistonInserter().setBlockName("piston_inserter").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		
		ModBlocks.drone_waypoint = new DroneWaypoint().setBlockName("drone_waypoint").setHardness(0.1F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":drone_waypoint");
		ModBlocks.drone_crate = new DroneCrate().setBlockName("drone_crate").setHardness(0.1F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.drone_waypoint_request = new DroneWaypointRequest().setBlockName("drone_waypoint_request").setHardness(0.1F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":drone_waypoint_request");
		ModBlocks.drone_dock = new DroneDock().setBlockName("drone_dock").setHardness(0.1F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":drone_dock");
		ModBlocks.drone_crate_provider = new DroneDock().setBlockName("drone_crate_provider").setHardness(0.1F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":drone_crate_provider");
		ModBlocks.drone_crate_requester = new DroneDock().setBlockName("drone_crate_requester").setHardness(0.1F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":drone_crate_requester");
		
		ModBlocks.chain = new BlockChain(Material.iron).setBlockName("dungeon_chain").setHardness(0.25F).setResistance(2.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":chain");

		ModBlocks.ladder_sturdy = new BlockNTMLadder().setBlockName("ladder_sturdy").setHardness(0.25F).setResistance(2.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ladder_sturdy");
		ModBlocks.ladder_iron = new BlockNTMLadder().setBlockName("ladder_iron").setHardness(0.25F).setResistance(2.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ladder_iron");
		ModBlocks.ladder_gold = new BlockNTMLadder().setBlockName("ladder_gold").setHardness(0.25F).setResistance(2.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ladder_gold");
		ModBlocks.ladder_aluminium = new BlockNTMLadder().setBlockName("ladder_aluminium").setHardness(0.25F).setResistance(2.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ladder_aluminium");
		ModBlocks.ladder_copper = new BlockNTMLadder().setBlockName("ladder_copper").setHardness(0.25F).setResistance(2.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ladder_copper");
		ModBlocks.ladder_titanium = new BlockNTMLadder().setBlockName("ladder_titanium").setHardness(0.25F).setResistance(2.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ladder_titanium");
		ModBlocks.ladder_lead = new BlockNTMLadder().setBlockName("ladder_lead").setHardness(0.25F).setResistance(2.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ladder_lead");
		ModBlocks.ladder_cobalt = new BlockNTMLadder().setBlockName("ladder_cobalt").setHardness(0.25F).setResistance(2.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ladder_cobalt");
		ModBlocks.ladder_steel = new BlockNTMLadder().setBlockName("ladder_steel").setHardness(0.25F).setResistance(2.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ladder_steel");
		ModBlocks.ladder_tungsten = new BlockNTMLadder().setBlockName("ladder_tungsten").setHardness(0.25F).setResistance(2.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ladder_tungsten");
		
		ModBlocks.barrel_plastic = new BlockFluidBarrel(Material.iron, 12000).setBlockName("barrel_plastic").setStepSound(Block.soundTypeStone).setHardness(2.0F).setResistance(5.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":barrel_plastic");
		ModBlocks.barrel_corroded = new BlockFluidBarrel(Material.iron, 6000).setBlockName("barrel_corroded").setStepSound(Block.soundTypeMetal).setHardness(2.0F).setResistance(5.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":barrel_corroded");
		ModBlocks.barrel_iron = new BlockFluidBarrel(Material.iron, 8000).setBlockName("barrel_iron").setStepSound(Block.soundTypeMetal).setHardness(2.0F).setResistance(5.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":barrel_iron");
		ModBlocks.barrel_steel = new BlockFluidBarrel(Material.iron, 16000).setBlockName("barrel_steel").setStepSound(Block.soundTypeMetal).setHardness(2.0F).setResistance(5.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":barrel_steel");
		ModBlocks.barrel_tcalloy = new BlockFluidBarrel(Material.iron, 24000).setBlockName("barrel_tcalloy").setStepSound(Block.soundTypeMetal).setHardness(2.0F).setResistance(5.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":barrel_tcalloy");
		ModBlocks.barrel_antimatter = new BlockFluidBarrel(Material.iron, 16000).setBlockName("barrel_antimatter").setStepSound(Block.soundTypeMetal).setHardness(2.0F).setResistance(5.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":barrel_antimatter");

		ModBlocks.machine_transformer = new MachineTransformer(Material.iron, 10000L, 1).setBlockName("machine_transformer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_transformer_iron");
		ModBlocks.machine_transformer_dnt = new MachineTransformer(Material.iron, 1000000000000000L, 1).setBlockName("machine_transformer_dnt").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_transformer");
		ModBlocks.machine_transformer_20 = new MachineTransformer(Material.iron, 10000L, 20).setBlockName("machine_transformer_20").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_transformer_iron");
		ModBlocks.machine_transformer_dnt_20 = new MachineTransformer(Material.iron, 1000000000000000L, 20).setBlockName("machine_transformer_dnt_20").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_transformer");
		
		ModBlocks.machine_satlinker = new MachineSatLinker(Material.iron).setBlockName("machine_satlinker").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":machine_satlinker_side");
		ModBlocks.machine_keyforge = new MachineKeyForge(Material.iron).setBlockName("machine_keyforge").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.consumableTab).setBlockTextureName(RefStrings.MODID + ":machine_keyforge_side");
		ModBlocks.machine_armor_table = new BlockArmorTable(Material.iron).setBlockName("machine_armor_table").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.consumableTab);

		ModBlocks.machine_solar_boiler = new MachineSolarBoiler(Material.iron).setBlockName("machine_solar_boiler").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_solar_boiler");
		ModBlocks.solar_mirror = new SolarMirror(Material.iron).setBlockName("solar_mirror").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":solar_mirror");
		
		ModBlocks.struct_launcher = new BlockGeneric(Material.iron).setBlockName("struct_launcher").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":struct_launcher");
		ModBlocks.struct_scaffold = new BlockGeneric(Material.iron).setBlockName("struct_scaffold").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":struct_scaffold");
		ModBlocks.struct_launcher_core = new BlockStruct(Material.iron).setBlockName("struct_launcher_core").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":struct_launcher_core");
		ModBlocks.struct_launcher_core_large = new BlockStruct(Material.iron).setBlockName("struct_launcher_core_large").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":struct_launcher_core_large");
		ModBlocks.struct_soyuz_core = new BlockSoyuzStruct(Material.iron).setBlockName("struct_soyuz_core").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":struct_soyuz_core");
		ModBlocks.struct_iter_core = new BlockITERStruct(Material.iron).setBlockName("struct_iter_core").setLightLevel(1F).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":struct_iter_core");
		ModBlocks.struct_plasma_core = new BlockPlasmaStruct(Material.iron).setBlockName("struct_plasma_core").setLightLevel(1F).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":struct_plasma_core");
		ModBlocks.struct_watz_core = new BlockWatzStruct(Material.iron).setBlockName("struct_watz_core").setLightLevel(1F).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":struct_watz_core");
		
		ModBlocks.factory_titanium_hull = new BlockGeneric(Material.iron).setBlockName("factory_titanium_hull").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":factory_titanium_hull");
		ModBlocks.factory_titanium_furnace = new FactoryHatch(Material.iron).setBlockName("factory_titanium_furnace").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":factory_titanium_furnace");
		ModBlocks.factory_titanium_conductor = new BlockPillar(Material.iron, RefStrings.MODID + ":factory_titanium_conductor").setBlockName("factory_titanium_conductor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":factory_titanium_hull");
		ModBlocks.factory_advanced_hull = new BlockGeneric(Material.iron).setBlockName("factory_advanced_hull").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":factory_advanced_hull");
		ModBlocks.factory_advanced_furnace = new FactoryHatch(Material.iron).setBlockName("factory_advanced_furnace").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":factory_advanced_furnace");
		ModBlocks.factory_advanced_conductor = new BlockPillar(Material.iron, RefStrings.MODID + ":factory_advanced_conductor").setBlockName("factory_advanced_conductor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":factory_advanced_hull");

		ModBlocks.cm_block = new BlockCM(Material.iron, EnumCMMaterials.class, true, true).setBlockName("cm_block").setCreativeTab(MainRegistry.machineTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":cm_block");
		ModBlocks.cm_sheet = new BlockCM(Material.iron, EnumCMMaterials.class, true, true).setBlockName("cm_sheet").setCreativeTab(MainRegistry.machineTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":cm_sheet");
		ModBlocks.cm_engine = new BlockCM(Material.iron, EnumCMEngines.class, true, true).setBlockName("cm_engine").setCreativeTab(MainRegistry.machineTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":cm_engine");
		ModBlocks.cm_tank = new BlockCMGlass(Material.iron, EnumCMMaterials.class, true, true).setBlockName("cm_tank").setCreativeTab(MainRegistry.machineTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":cm_tank");
		ModBlocks.cm_circuit = new BlockCM(Material.iron, EnumCMCircuit.class, true, true).setBlockName("cm_circuit").setCreativeTab(MainRegistry.machineTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":cm_circuit");
		ModBlocks.cm_port = new BlockCMPort(Material.iron, EnumCMMaterials.class, true, true).setBlockName("cm_port").setCreativeTab(MainRegistry.machineTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":cm_port");
		ModBlocks.custom_machine = new BlockCustomMachine().setBlockName("custom_machine").setCreativeTab(MainRegistry.machineTab).setLightLevel(1F).setHardness(5.0F).setResistance(10.0F);
		ModBlocks.cm_anchor = new BlockCMAnchor().setBlockName("custom_machine_anchor").setCreativeTab(MainRegistry.machineTab).setHardness(5.0F).setResistance(10.0F);

		ModBlocks.pwr_fuel = new BlockPillarPWR(Material.iron, RefStrings.MODID + ":pwr_fuel_top").setBlockName("pwr_fuel").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":pwr_fuel_side");
		ModBlocks.pwr_control = new BlockPillarPWR(Material.iron, RefStrings.MODID + ":pwr_control_top").setBlockName("pwr_control").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":pwr_control_side");
		ModBlocks.pwr_channel = new BlockPillarPWR(Material.iron, RefStrings.MODID + ":pwr_channel_top").setBlockName("pwr_channel").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":pwr_channel_side");
		ModBlocks.pwr_heatex = new BlockGenericPWR(Material.iron).setBlockName("pwr_heatex").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":pwr_heatex");
		ModBlocks.pwr_neutron_source = new BlockGenericPWR(Material.iron).setBlockName("pwr_neutron_source").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":pwr_neutron_source");
		ModBlocks.pwr_reflector = new BlockGenericPWR(Material.iron).setBlockName("pwr_reflector").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":pwr_reflector");
		ModBlocks.pwr_casing = new BlockGenericPWR(Material.iron).setBlockName("pwr_casing").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":pwr_casing");
		ModBlocks.pwr_port = new BlockGenericPWR(Material.iron).setBlockName("pwr_port").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":pwr_port");
		ModBlocks.pwr_controller = new MachinePWRController(Material.iron).setBlockName("pwr_controller").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":pwr_casing_blank");
		ModBlocks.pwr_block = new BlockPWR(Material.iron).setBlockName("pwr_block").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":pwr_block");
		
		reactor_element = new BlockPillar(Material.iron, RefStrings.MODID + ":reactor_element_top", RefStrings.MODID + ":reactor_element_base").setBlockName("reactor_element").setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":reactor_element_side");
		reactor_control = new BlockPillar(Material.iron, RefStrings.MODID + ":reactor_control_top").setBlockName("reactor_control").setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":reactor_control_side");
		reactor_hatch = new ReactorHatch(Material.iron).setBlockName("reactor_hatch").setHardness(5.0F).setResistance(1000.0F).setBlockTextureName(RefStrings.MODID + ":brick_concrete");
		reactor_ejector = new BlockRotatable(Material.iron).setBlockName("reactor_ejector").setHardness(5.0F).setResistance(1000.0F).setBlockTextureName(RefStrings.MODID + ":brick_concrete");
		reactor_inserter = new BlockRotatable(Material.iron).setBlockName("reactor_inserter").setHardness(5.0F).setResistance(1000.0F).setBlockTextureName(RefStrings.MODID + ":brick_concrete");
		reactor_conductor = new BlockPillar(Material.iron, RefStrings.MODID + ":reactor_conductor_top").setBlockName("reactor_conductor").setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":reactor_conductor_side");
		reactor_computer = new ReactorCore(Material.iron).setBlockName("reactor_computer").setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":reactor_computer");

		fusion_conductor = new BlockToolConversionPillar(Material.iron).addVariant("_welded").setBlockName("fusion_conductor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fusion_conductor");
		fusion_center = new BlockPillar(Material.iron, RefStrings.MODID + ":fusion_center_top_alt").setBlockName("fusion_center").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fusion_center_side_alt");
		fusion_motor = new BlockPillar(Material.iron, RefStrings.MODID + ":fusion_motor_top_alt").setBlockName("fusion_motor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fusion_motor_side_alt");
		fusion_heater = new BlockPillar(Material.iron, RefStrings.MODID + ":fusion_heater_top").setBlockName("fusion_heater").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fusion_heater_side");
		fusion_hatch = new FusionHatch(Material.iron).setBlockName("fusion_hatch").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fusion_hatch");
		plasma = new BlockPlasma(Material.iron).setBlockName("plasma").setHardness(5.0F).setResistance(6000.0F).setLightLevel(1.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":plasma");
		iter = new MachineITER().setBlockName("iter").setHardness(5.0F).setResistance(60.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":iter");
		plasma_heater = new MachinePlasmaHeater().setBlockName("plasma_heater").setHardness(5.0F).setResistance(60.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":plasma_heater");

		ModBlocks.watz_element = new BlockPillar(Material.iron, RefStrings.MODID + ":watz_element_top").setBlockName("watz_element").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":watz_element_side");
		ModBlocks.watz_control = new BlockPillar(Material.iron, RefStrings.MODID + ":watz_control_top").setBlockName("watz_control").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":watz_control_side");
		ModBlocks.watz_cooler = new BlockPillar(Material.iron, RefStrings.MODID + ":watz_cooler_top").setBlockName("watz_cooler").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":watz_cooler_side");
		ModBlocks.watz_end = new BlockToolConversion(Material.iron).addVariant("_bolted").setBlockName("watz_end").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":watz_casing");
		ModBlocks.watz_hatch = new WatzHatch(Material.iron).setBlockName("watz_hatch").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":watz_hatch");
		ModBlocks.watz_conductor = new BlockCableConnect(Material.iron).setBlockName("watz_conductor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":watz_conductor_top");
		ModBlocks.watz_core = new WatzCore(Material.iron).setBlockName("watz_core").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":watz_computer");
		ModBlocks.watz = new Watz().setBlockName("watz").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.watz_pump = new WatzPump().setBlockName("watz_pump").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");

		ModBlocks.fwatz_conductor = new BlockPillar(Material.iron, RefStrings.MODID + ":block_combine_steel").setBlockName("fwatz_conductor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fwatz_conductor_side");
		ModBlocks.fwatz_cooler = new BlockPillar(Material.iron, RefStrings.MODID + ":fwatz_cooler_top").setBlockName("fwatz_cooler").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fwatz_cooler");
		ModBlocks.fwatz_tank = new BlockNTMGlass(0, RefStrings.MODID + ":fwatz_tank", Material.iron).setBlockName("fwatz_tank").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.fwatz_scaffold = new BlockGeneric(Material.iron).setBlockName("fwatz_scaffold").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fwatz_scaffold");
		ModBlocks.fwatz_hatch = new FWatzHatch(Material.iron).setBlockName("fwatz_hatch").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fwatz_computer");
		ModBlocks.fwatz_computer = new BlockGeneric(Material.iron).setBlockName("fwatz_computer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fwatz_computer");
		ModBlocks.fwatz_core = new FWatzCore(Material.iron).setBlockName("fwatz_core").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fwatz_core");
		ModBlocks.fwatz_plasma = new BlockPlasma(Material.iron).setBlockName("fwatz_plasma").setHardness(5.0F).setResistance(6000.0F).setLightLevel(1.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fwatz_plasma");
		
		ModBlocks.balefire = new Balefire().setBlockName("balefire").setHardness(0.0F).setLightLevel(1.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":balefire");
		ModBlocks.fire_digamma = new DigammaFlame().setBlockName("fire_digamma").setHardness(0.0F).setResistance(150F).setLightLevel(1.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":fire_digamma");
		ModBlocks.digamma_matter = new DigammaMatter().setBlockName("digamma_matter").setBlockUnbreakable().setResistance(18000000).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":digamma_matter");

		ModBlocks.ams_base = new BlockAMSBase(Material.iron).setBlockName("ams_base").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":ams_base");
		ModBlocks.ams_emitter = new BlockAMSEmitter(Material.iron).setBlockName("ams_emitter").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":ams_emitter");
		ModBlocks.ams_limiter = new BlockAMSLimiter(Material.iron).setBlockName("ams_limiter").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":ams_limiter");

		ModBlocks.machine_converter_he_rf = new BlockConverterHeRf(Material.iron).setBlockName("machine_converter_he_rf").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_converter_he_rf");
		ModBlocks.machine_converter_rf_he = new BlockConverterRfHe(Material.iron).setBlockName("machine_converter_rf_he").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_converter_rf_he");

		ModBlocks.dfc_emitter = new CoreComponent(Material.iron).setBlockName("dfc_emitter").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":dfc_emitter");
		ModBlocks.dfc_injector = new CoreComponent(Material.iron).setBlockName("dfc_injector").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":dfc_injector");
		ModBlocks.dfc_receiver = new CoreComponent(Material.iron).setBlockName("dfc_receiver").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":dfc_receiver");
		ModBlocks.dfc_stabilizer = new CoreComponent(Material.iron).setBlockName("dfc_stabilizer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":dfc_stabilizer");
		ModBlocks.dfc_core = new CoreCore(Material.iron).setBlockName("dfc_core").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":dfc_core");

		ModBlocks.seal_frame = new BlockGeneric(Material.iron).setBlockName("seal_frame").setHardness(10.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":seal_frame");
		ModBlocks.seal_controller = new BlockSeal(Material.iron).setBlockName("seal_controller").setHardness(10.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.seal_hatch = new BlockHatch(Material.iron).setBlockName("seal_hatch").setHardness(Float.POSITIVE_INFINITY).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":seal_hatch_3");
		
		ModBlocks.vault_door = new VaultDoor(Material.iron).setBlockName("vault_door").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":vault_door");
		ModBlocks.blast_door = new BlastDoor(Material.iron).setBlockName("blast_door").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":blast_door");

		ModBlocks.sliding_blast_door = new BlockDoorGeneric(Material.iron, DoorDecl.SLIDE_DOOR).setBlockName("sliding_blast_door").setHardness(150.0F).setResistance(7500.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":sliding_blast_door");
		ModBlocks.sliding_blast_door_2 = new BlockDoorGeneric(Material.iron, DoorDecl.SLIDE_DOOR).setBlockName("sliding_blast_door_2").setHardness(250.0F).setResistance(15000.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":sliding_blast_door");
//		ModBlocks.sliding_blast_door_keypad = new BlockDoorGeneric(Material.iron, DoorDecl.SLIDE_DOOR).setBlockName("sliding_blast_door_2").setHardness(250.0F).setResistance(15000.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":sliding_blast_door");
		//  = new BlockSlidingBlastDoor(Material.IRON, "sliding_blast_door_keypad").setHardness(150.0F).setResistance(7500.0F).setCreativeTab(null);
		
		
		ModBlocks.fire_door = new BlockDoorGeneric(Material.iron, DoorDecl.FIRE_DOOR).setBlockName("fire_door").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fire_door");
		ModBlocks.transition_seal = new BlockDoorGeneric(Material.iron, DoorDecl.TRANSITION_SEAL).setBlockName("transition_seal").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":transition_seal");

		ModBlocks.door_metal = new BlockModDoor(Material.iron).setBlockName("door_metal").setHardness(5.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":door_metal");
		ModBlocks.door_office = new BlockModDoor(Material.iron).setBlockName("door_office").setHardness(10.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":door_office");
		ModBlocks.door_bunker = new BlockModDoor(Material.iron).setBlockName("door_bunker").setHardness(10.0F).setResistance(100.0F).setBlockTextureName(RefStrings.MODID + ":door_bunker");

		ModBlocks.barbed_wire = new BarbedWire(Material.iron).setBlockName("barbed_wire").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":barbed_wire_model");
		ModBlocks.barbed_wire_fire = new BarbedWire(Material.iron).setBlockName("barbed_wire_fire").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":barbed_wire_fire_model");
		ModBlocks.barbed_wire_poison = new BarbedWire(Material.iron).setBlockName("barbed_wire_poison").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":barbed_wire_poison_model");
		ModBlocks.barbed_wire_acid = new BarbedWire(Material.iron).setBlockName("barbed_wire_acid").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":barbed_wire_acid_model");
		ModBlocks.barbed_wire_wither = new BarbedWire(Material.iron).setBlockName("barbed_wire_wither").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":barbed_wire_wither_model");
		ModBlocks.barbed_wire_ultradeath = new BarbedWire(Material.iron).setBlockName("barbed_wire_ultradeath").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":barbed_wire_ultradeath_model");
		ModBlocks.spikes = new Spikes(Material.iron).setBlockName("spikes").setHardness(2.5F).setResistance(5.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":spikes");
		
		ModBlocks.charger = new Charger(Material.iron).setBlockName("charger").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		
		ModBlocks.tesla = new MachineTesla(Material.iron).setBlockName("tesla").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":tesla");

		ModBlocks.marker_structure = new BlockMarker(Material.iron).setBlockName("marker_structure").setHardness(0.1F).setResistance(0.1F).setLightLevel(1.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":marker_structure");

		ModBlocks.muffler = new BlockGeneric(Material.cloth).setBlockName("muffler").setHardness(0.8F).setStepSound(Block.soundTypeCloth).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":muffler");
		
		ModBlocks.launch_pad = new LaunchPad(Material.iron).setBlockName("launch_pad").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":launch_pad");
		ModBlocks.machine_radar = new MachineRadar(Material.iron).setBlockName("machine_radar").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":machine_radar");

		ModBlocks.machine_missile_assembly = new MachineMissileAssembly(Material.iron).setBlockName("machine_missile_assembly").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":machine_missile_assembly");
		ModBlocks.compact_launcher = new CompactLauncher(Material.iron).setBlockName("compact_launcher").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":compact_launcher");
		ModBlocks.launch_table = new LaunchTable(Material.iron).setBlockName("launch_table").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":launch_table");
		ModBlocks.soyuz_launcher = new SoyuzLauncher(Material.iron).setBlockName("soyuz_launcher").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":soyuz_launcher");
		
		ModBlocks.sat_mapper = new DecoBlock(Material.iron).setBlockName("sat_mapper").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":sat_mapper");
		ModBlocks.sat_radar = new DecoBlock(Material.iron).setBlockName("sat_radar").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":sat_radar");
		ModBlocks.sat_scanner = new DecoBlock(Material.iron).setBlockName("sat_scanner").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":sat_scanner");
		ModBlocks.sat_laser = new DecoBlock(Material.iron).setBlockName("sat_laser").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":sat_laser");
		ModBlocks.sat_foeq = new DecoBlock(Material.iron).setBlockName("sat_foeq").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":sat_foeq");
		ModBlocks.sat_resonator = new DecoBlock(Material.iron).setBlockName("sat_resonator").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":sat_resonator");
		
		ModBlocks.sat_dock = new MachineSatDock(Material.iron).setBlockName("sat_dock").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":sat_dock");
		ModBlocks.soyuz_capsule = new SoyuzCapsule(Material.iron).setBlockName("soyuz_capsule").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":soyuz_capsule");
		
		ModBlocks.turret_chekhov = new TurretChekhov(Material.iron).setBlockName("turret_chekhov").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.turret_friendly = new TurretFriendly(Material.iron).setBlockName("turret_friendly").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.turret_jeremy = new TurretJeremy(Material.iron).setBlockName("turret_jeremy").setHardness(5.0F).setResistance(600.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.turret_tauon = new TurretTauon(Material.iron).setBlockName("turret_tauon").setHardness(5.0F).setResistance(60.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.turret_richard = new TurretRichard(Material.iron).setBlockName("turret_richard").setHardness(5.0F).setResistance(600.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.turret_howard = new TurretHoward(Material.iron).setBlockName("turret_howard").setHardness(5.0F).setResistance(60.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.turret_howard_damaged = new TurretHowardDamaged(Material.iron).setBlockName("turret_howard_damaged").setHardness(5.0F).setResistance(600.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":block_rust");
		ModBlocks.turret_maxwell = new TurretMaxwell(Material.iron).setBlockName("turret_maxwell").setHardness(5.0F).setResistance(60.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.turret_fritz = new TurretFritz(Material.iron).setBlockName("turret_fritz").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.turret_brandon = new TurretBrandon(Material.iron).setBlockName("turret_brandon").setHardness(5.0F).setResistance(600.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.turret_arty = new TurretArty(Material.iron).setBlockName("turret_arty").setHardness(5.0F).setResistance(600.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.turret_himars = new TurretHIMARS(Material.iron).setBlockName("turret_himars").setHardness(5.0F).setResistance(600.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.turret_sentry = new TurretSentry().setBlockName("turret_sentry").setHardness(5.0F).setResistance(5.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":block_steel");

		ModBlocks.rbmk_rod = new RBMKRod(false).setBlockName("rbmk_rod").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_element");
		ModBlocks.rbmk_rod_mod = new RBMKRod(true).setBlockName("rbmk_rod_mod").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_element_mod");
		ModBlocks.rbmk_rod_reasim = new RBMKRodReaSim(false).setBlockName("rbmk_rod_reasim").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_element_reasim");
		ModBlocks.rbmk_rod_reasim_mod = new RBMKRodReaSim(true).setBlockName("rbmk_rod_reasim_mod").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_element_reasim_mod");
		ModBlocks.rbmk_control = new RBMKControl(false).setBlockName("rbmk_control").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_control");
		ModBlocks.rbmk_control_mod = new RBMKControl(true).setBlockName("rbmk_control_mod").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_control_mod");
		ModBlocks.rbmk_control_auto = new RBMKControlAuto().setBlockName("rbmk_control_auto").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_control_auto");
		ModBlocks.rbmk_blank = new RBMKBlank().setBlockName("rbmk_blank").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_blank");
		ModBlocks.rbmk_boiler = new RBMKBoiler().setBlockName("rbmk_boiler").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_boiler");
		ModBlocks.rbmk_reflector = new RBMKReflector().setBlockName("rbmk_reflector").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_reflector");
		ModBlocks.rbmk_absorber = new RBMKAbsorber().setBlockName("rbmk_absorber").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_absorber");
		ModBlocks.rbmk_moderator = new RBMKModerator().setBlockName("rbmk_moderator").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_moderator");
		ModBlocks.rbmk_outgasser = new RBMKOutgasser().setBlockName("rbmk_outgasser").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_outgasser");
		ModBlocks.rbmk_storage = new RBMKStorage().setBlockName("rbmk_storage").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_storage");
		ModBlocks.rbmk_cooler = new RBMKCooler().setBlockName("rbmk_cooler").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_cooler");
		ModBlocks.rbmk_heater = new RBMKHeater().setBlockName("rbmk_heater").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_heater");
		ModBlocks.rbmk_console = new RBMKConsole().setBlockName("rbmk_console").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_console");
		ModBlocks.rbmk_crane_console = new RBMKCraneConsole().setBlockName("rbmk_crane_console").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_crane_console");
		ModBlocks.rbmk_loader = new RBMKLoader(Material.iron).setBlockName("rbmk_loader").setCreativeTab(MainRegistry.machineTab).setHardness(50.0F).setResistance(60.0F).setBlockTextureName(RefStrings.MODID + ":rbmk_loader");
		ModBlocks.rbmk_steam_inlet = new RBMKInlet(Material.iron).setBlockName("rbmk_steam_inlet").setCreativeTab(MainRegistry.machineTab).setHardness(50.0F).setResistance(60.0F).setBlockTextureName(RefStrings.MODID + ":rbmk_steam_inlet");
		ModBlocks.rbmk_steam_outlet = new RBMKOutlet(Material.iron).setBlockName("rbmk_steam_outlet").setCreativeTab(MainRegistry.machineTab).setHardness(50.0F).setResistance(60.0F).setBlockTextureName(RefStrings.MODID + ":rbmk_steam_outlet");
		ModBlocks.rbmk_heatex = new RBMKHeatex(Material.iron).setBlockName("rbmk_heatex").setCreativeTab(null).setHardness(50.0F).setResistance(60.0F).setBlockTextureName(RefStrings.MODID + ":rbmk_heatex");
		ModBlocks.pribris = new RBMKDebris().setBlockName("pribris").setCreativeTab(MainRegistry.machineTab).setHardness(50.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_debris");
		ModBlocks.pribris_burning = new RBMKDebrisBurning().setBlockName("pribris_burning").setCreativeTab(MainRegistry.machineTab).setHardness(50.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_debris_burning");
		ModBlocks.pribris_radiating = new RBMKDebrisRadiating().setBlockName("pribris_radiating").setCreativeTab(MainRegistry.machineTab).setHardness(50.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_debris_radiating");
		ModBlocks.pribris_digamma = new RBMKDebrisDigamma().setBlockName("pribris_digamma").setCreativeTab(MainRegistry.machineTab).setHardness(50.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_debris_digamma");
		
		ModBlocks.book_guide = new Guide(Material.iron).setBlockName("book_guide").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.nukeTab);
		
		ModBlocks.rail_wood = new RailGeneric().setMaxSpeed(0.2F).setBlockName("rail_wood").setHardness(5.0F).setResistance(10.0F).setCreativeTab(CreativeTabs.tabTransport).setBlockTextureName(RefStrings.MODID + ":rail_wood");
		ModBlocks.rail_narrow = new RailGeneric().setBlockName("rail_narrow").setHardness(5.0F).setResistance(10.0F).setCreativeTab(CreativeTabs.tabTransport).setBlockTextureName(RefStrings.MODID + ":rail_narrow");
		ModBlocks.rail_highspeed = new RailGeneric().setMaxSpeed(1F).setFlexible(false).setBlockName("rail_highspeed").setHardness(5.0F).setResistance(10.0F).setCreativeTab(CreativeTabs.tabTransport).setBlockTextureName(RefStrings.MODID + ":rail_highspeed");
		ModBlocks.rail_booster = new RailBooster().setBlockName("rail_booster").setHardness(5.0F).setResistance(10.0F).setCreativeTab(CreativeTabs.tabTransport).setBlockTextureName(RefStrings.MODID + ":rail_booster");
		ModBlocks.rail_narrow_straight = new RailNarrowStraight().setBlockName("rail_narrow_straight").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":rail_narrow_neo");
		ModBlocks.rail_narrow_curve = new RailNarrowCurve().setBlockName("rail_narrow_curve").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":rail_narrow_neo");
		ModBlocks.rail_large_straight = new RailStandardStraight().setBlockName("rail_large_straight").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":rail_standard_straight");
		ModBlocks.rail_large_curve = new RailStandardCurve().setBlockName("rail_large_curve").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":rail_standard_straight");
		ModBlocks.rail_large_ramp = new RailStandardRamp().setBlockName("rail_large_ramp").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":rail_standard_straight");
		ModBlocks.rail_large_buffer = new RailStandardBuffer().setBlockName("rail_large_buffer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":rail_standard_buffer");

		ModBlocks.crate = new BlockCrate(Material.wood).setBlockName("crate").setStepSound(Block.soundTypeWood).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.consumableTab).setBlockTextureName(RefStrings.MODID + ":crate");
		ModBlocks.crate_weapon = new BlockCrate(Material.wood).setBlockName("crate_weapon").setStepSound(Block.soundTypeWood).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.consumableTab).setBlockTextureName(RefStrings.MODID + ":crate_weapon");
		ModBlocks.crate_lead = new BlockCrate(Material.iron).setBlockName("crate_lead").setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.consumableTab).setBlockTextureName(RefStrings.MODID + ":crate_lead");
		ModBlocks.crate_metal = new BlockCrate(Material.iron).setBlockName("crate_metal").setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.consumableTab).setBlockTextureName(RefStrings.MODID + ":crate_metal");
		ModBlocks.crate_red = new BlockCrate(Material.iron).setBlockName("crate_red").setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":crate_red");
		ModBlocks.crate_can = new BlockCanCrate(Material.wood).setBlockName("crate_can").setStepSound(Block.soundTypeWood).setHardness(1.0F).setResistance(2.5F).setCreativeTab(MainRegistry.consumableTab).setBlockTextureName(RefStrings.MODID + ":crate_can");
		ModBlocks.crate_ammo = new BlockAmmoCrate(Material.iron).setBlockName("crate_ammo").setStepSound(Block.soundTypeMetal).setHardness(1.0F).setResistance(2.5F).setCreativeTab(MainRegistry.consumableTab);
		ModBlocks.crate_jungle = new BlockJungleCrate(Material.rock).setBlockName("crate_jungle").setStepSound(Block.soundTypeStone).setHardness(1.0F).setResistance(2.5F).setCreativeTab(MainRegistry.consumableTab).setBlockTextureName(RefStrings.MODID + ":crate_jungle");
		ModBlocks.crate_iron = new BlockStorageCrate(Material.iron).setBlockName("crate_iron").setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.crate_steel = new BlockStorageCrate(Material.iron).setBlockName("crate_steel").setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.crate_desh = new BlockStorageCrate(Material.iron).setBlockName("crate_desh").setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.crate_tungsten = new BlockStorageCrate(Material.iron).setBlockName("crate_tungsten").setStepSound(Block.soundTypeMetal).setHardness(7.5F).setResistance(300.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.crate_template = new BlockStorageCrate(Material.iron).setBlockName("crate_template").setStepSound(Block.soundTypeMetal).setHardness(7.5F).setResistance(300.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.safe = new BlockStorageCrate(Material.iron).setBlockName("safe").setStepSound(Block.soundTypeMetal).setHardness(7.5F).setResistance(10000.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.mass_storage = new BlockMassStorage().setBlockName("mass_storage").setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		
		ModBlocks.boxcar = new DecoBlock(Material.iron).setBlockName("boxcar").setStepSound(Block.soundTypeMetal).setHardness(10.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":boxcar");
		ModBlocks.boat = new DecoBlock(Material.iron).setBlockName("boat").setStepSound(Block.soundTypeMetal).setHardness(10.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":boat");
		ModBlocks.bomber = new DecoBlock(Material.iron).setBlockName("bomber").setStepSound(Block.soundTypeMetal).setHardness(10.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":code");

		ModBlocks.machine_well = new MachineOilWell().setBlockName("machine_well").setHardness(5.0F).setResistance(60.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_well");
		ModBlocks.machine_pumpjack = new MachinePumpjack().setBlockName("machine_pumpjack").setHardness(5.0F).setResistance(60.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_pumpjack");
		ModBlocks.machine_fracking_tower = new MachineFrackingTower().setBlockName("machine_fracking_tower").setHardness(5.0F).setResistance(60.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.oil_pipe = new BlockNoDrop(Material.iron).setBlockName("oil_pipe").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":oil_pipe");
		ModBlocks.machine_flare = new MachineGasFlare(Material.iron).setBlockName("machine_flare").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.chimney_brick = new MachineChimneyBrick(Material.iron).setBlockName("chimney_brick").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":brick_fire");
		ModBlocks.chimney_industrial = new MachineChimneyIndustrial(Material.iron).setBlockName("chimney_industrial").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":concrete_colored_ext.machine");
		ModBlocks.machine_refinery = new MachineRefinery(Material.iron).setBlockName("machine_refinery").setHardness(5.0F).setResistance(20.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_refinery");
		ModBlocks.machine_vacuum_distill = new MachineVacuumDistill(Material.iron).setBlockName("machine_vacuum_distill").setHardness(5.0F).setResistance(20.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.machine_fraction_tower = new MachineFractionTower(Material.iron).setBlockName("machine_fraction_tower").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.fraction_spacer = new FractionSpacer(Material.iron).setBlockName("fraction_spacer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.machine_catalytic_cracker = new MachineCatalyticCracker(Material.iron).setBlockName("machine_catalytic_cracker").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.machine_catalytic_reformer = new MachineCatalyticReformer(Material.iron).setBlockName("machine_catalytic_reformer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.machine_coker = new MachineCoker(Material.iron).setBlockName("machine_coker").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.machine_autosaw = new MachineAutosaw().setBlockName("machine_autosaw").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.machine_drill = new MachineMiningDrill(Material.iron).setBlockName("machine_drill").setHardness(5.0F).setResistance(100.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":machine_drill");
		ModBlocks.machine_excavator = new MachineExcavator().setBlockName("machine_excavator").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.drill_pipe = new BlockNoDrop(Material.iron).setBlockName("drill_pipe").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":drill_pipe");
		ModBlocks.machine_mining_laser = new MachineMiningLaser(Material.iron).setBlockName("machine_mining_laser").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_mining_laser");
		ModBlocks.barricade = new BlockNoDrop(Material.sand).setBlockName("barricade").setHardness(1.0F).setResistance(2.5F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":barricade");
		ModBlocks.machine_assembler = new MachineAssembler(Material.iron).setBlockName("machine_assembler").setHardness(5.0F).setResistance(30.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_assembler");
		ModBlocks.machine_assemfac = new MachineAssemfac(Material.iron).setBlockName("machine_assemfac").setHardness(5.0F).setResistance(30.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.machine_arc_welder = new MachineArcWelder(Material.iron).setBlockName("machine_arc_welder").setHardness(5.0F).setResistance(30.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.machine_chemplant = new MachineChemplant(Material.iron).setBlockName("machine_chemplant").setHardness(5.0F).setResistance(30.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.machine_chemfac = new MachineChemfac(Material.iron).setBlockName("machine_chemfac").setHardness(5.0F).setResistance(30.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.machine_mixer = new MachineMixer(Material.iron).setBlockName("machine_mixer").setHardness(5.0F).setResistance(30.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.machine_fluidtank = new MachineFluidTank(Material.iron).setBlockName("machine_fluidtank").setHardness(5.0F).setResistance(20.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_fluidtank");
		ModBlocks.machine_bat9000 = new MachineBigAssTank9000(Material.iron).setBlockName("machine_bat9000").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.machine_orbus = new MachineOrbus(Material.iron).setBlockName("machine_orbus").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.machine_turbofan = new MachineTurbofan(Material.iron).setBlockName("machine_turbofan").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_turbofan");
		ModBlocks.machine_turbinegas = new MachineTurbineGas(Material.iron).setBlockName("machine_turbinegas").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.press_preheater = new BlockBase(Material.iron).setBlockName("press_preheater").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":press_preheater");
		ModBlocks.machine_press = new MachinePress(Material.iron).setBlockName("machine_press").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_press");
		ModBlocks.machine_epress = new MachineEPress(Material.iron).setBlockName("machine_epress").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_epress");
		ModBlocks.machine_conveyor_press = new MachineConveyorPress(Material.iron).setBlockName("machine_conveyor_press").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.machine_selenium = new MachineSeleniumEngine(Material.iron).setBlockName("machine_selenium").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_selenium");
		ModBlocks.reactor_research = new ReactorResearch(Material.iron).setBlockName("machine_reactor_small").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_reactor_small");
		ModBlocks.reactor_zirnox = new ReactorZirnox(Material.iron).setBlockName("machine_zirnox").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.zirnox_destroyed = new ZirnoxDestroyed(Material.iron).setBlockName("zirnox_destroyed").setHardness(100.0F).setResistance(800.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.machine_controller = new MachineReactorControl(Material.iron).setBlockName("machine_controller").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);

		ModBlocks.machine_boiler_off = new MachineBoiler(false).setBlockName("machine_boiler_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_boiler_off");
		ModBlocks.machine_boiler_on = new MachineBoiler(true).setBlockName("machine_boiler_on").setHardness(5.0F).setResistance(10.0F).setLightLevel(1.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":machine_boiler_on");
		ModBlocks.machine_boiler_electric_off = new MachineBoiler(false).setBlockName("machine_boiler_electric_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_boiler_electric_off");
		ModBlocks.machine_boiler_electric_on = new MachineBoiler(true).setBlockName("machine_boiler_electric_on").setHardness(5.0F).setResistance(10.0F).setLightLevel(1.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":machine_boiler_electric_on");
		
		ModBlocks.machine_steam_engine = new MachineSteamEngine().setBlockName("machine_steam_engine").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.machine_turbine = new MachineTurbine(Material.iron).setBlockName("machine_turbine").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_turbine");
		ModBlocks.machine_large_turbine = new MachineLargeTurbine(Material.iron).setBlockName("machine_large_turbine").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_large_turbine");
		ModBlocks.machine_chungus = new MachineChungus(Material.iron).setBlockName("machine_chungus").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_chungus");
		ModBlocks.machine_condenser = new MachineCondenser(Material.iron).setBlockName("machine_condenser").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":condenser");
		ModBlocks.machine_tower_small = new MachineTowerSmall(Material.iron).setBlockName("machine_tower_small").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":brick_concrete");
		ModBlocks.machine_tower_large = new MachineTowerLarge(Material.iron).setBlockName("machine_tower_large").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":concrete");
		ModBlocks.machine_condenser_powered = new MachineCondenserPowered(Material.iron).setBlockName("machine_condenser_powered").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel_machine");
		
		ModBlocks.machine_deuterium_extractor = new MachineDeuteriumExtractor(Material.iron).setBlockName("machine_deuterium_extractor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_deuterium_extractor_side");
		ModBlocks.machine_deuterium_tower = new DeuteriumTower(Material.iron).setBlockName("machine_deuterium_tower").setHardness(10.0F).setResistance(20.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":concrete");
		
		ModBlocks.machine_liquefactor = new MachineLiquefactor().setBlockName("machine_liquefactor").setHardness(10.0F).setResistance(20.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel_machine");
		ModBlocks.machine_solidifier = new MachineSolidifier().setBlockName("machine_solidifier").setHardness(10.0F).setResistance(20.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel_machine");
		ModBlocks.machine_compressor = new MachineCompressor().setBlockName("machine_compressor").setHardness(10.0F).setResistance(20.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel_machine");
		
		ModBlocks.machine_electrolyser = new MachineElectrolyser().setBlockName("machine_electrolyser").setHardness(10.0F).setResistance(20.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel_machine");
		
		ModBlocks.machine_autocrafter = new MachineAutocrafter().setBlockName("machine_autocrafter").setHardness(10.0F).setResistance(20.0F).setCreativeTab(MainRegistry.machineTab);
		
		ModBlocks.anvil_iron = new NTMAnvil(Material.iron, 1).setBlockName("anvil_iron").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":anvil_iron");
		ModBlocks.anvil_lead = new NTMAnvil(Material.iron, 1).setBlockName("anvil_lead").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":anvil_lead");
		ModBlocks.anvil_steel = new NTMAnvil(Material.iron, 2).setBlockName("anvil_steel").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":anvil_steel");
		ModBlocks.anvil_meteorite = new NTMAnvil(Material.iron, 3).setBlockName("anvil_meteorite").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":anvil_meteorite");
		ModBlocks.anvil_starmetal = new NTMAnvil(Material.iron, 3).setBlockName("anvil_starmetal").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":anvil_starmetal");
		ModBlocks.anvil_ferrouranium = new NTMAnvil(Material.iron, 4).setBlockName("anvil_ferrouranium").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":anvil_ferrouranium");
		ModBlocks.anvil_bismuth = new NTMAnvil(Material.iron, 5).setBlockName("anvil_bismuth").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":anvil_bismuth");
		ModBlocks.anvil_schrabidate = new NTMAnvil(Material.iron, 6).setBlockName("anvil_schrabidate").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":anvil_schrabidate");
		ModBlocks.anvil_dnt = new NTMAnvil(Material.iron, 7).setBlockName("anvil_dnt").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":anvil_dnt");
		ModBlocks.anvil_osmiridium = new NTMAnvil(Material.iron, 8).setBlockName("anvil_osmiridium").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":anvil_osmiridium");
		ModBlocks.anvil_murky = new NTMAnvil(Material.iron, 1916169).setBlockName("anvil_murky").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":anvil_steel");
		
		ModBlocks.machine_deaerator = new MachineDeaerator(Material.iron).setBlockName("machine_deaerator").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel_machine");
		
		ModBlocks.machine_waste_drum = new WasteDrum(Material.iron).setBlockName("machine_waste_drum").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":waste_drum");
		ModBlocks.machine_storage_drum = new StorageDrum(Material.iron).setBlockName("machine_storage_drum").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_storage_drum");

		ModBlocks.machine_schrabidium_transmutator = new MachineSchrabidiumTransmutator(Material.iron).setBlockName("machine_schrabidium_transmutator").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab);

		ModBlocks.machine_siren = new MachineSiren(Material.iron).setBlockName("machine_siren").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_siren");
		
		ModBlocks.machine_spp_bottom = new SPPBottom(Material.iron).setBlockName("machine_spp_bottom").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.machine_spp_top = new SPPTop(Material.iron).setBlockName("machine_spp_top").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		
		ModBlocks.radiobox = new Radiobox(Material.iron).setBlockName("radiobox").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":radiobox");
		ModBlocks.radiorec = new RadioRec(Material.iron).setBlockName("radiorec").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":radiorec");
		
		ModBlocks.machine_forcefield = new MachineForceField(Material.iron).setBlockName("machine_forcefield").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":machine_forcefield");

		ModBlocks.cheater_virus = new CheaterVirus(Material.iron).setBlockName("cheater_virus").setHardness(Float.POSITIVE_INFINITY).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":cheater_virus");
		ModBlocks.cheater_virus_seed = new CheaterVirusSeed(Material.iron).setBlockName("cheater_virus_seed").setHardness(Float.POSITIVE_INFINITY).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":cheater_virus_seed");
		ModBlocks.crystal_virus = new CrystalVirus(Material.iron).setBlockName("crystal_virus").setHardness(15.0F).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":crystal_virus");
		ModBlocks.crystal_hardened = new BlockGeneric(Material.iron).setBlockName("crystal_hardened").setHardness(15.0F).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":crystal_hardened");
		ModBlocks.crystal_pulsar = new CrystalPulsar(Material.iron).setBlockName("crystal_pulsar").setHardness(15.0F).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":crystal_pulsar");
		ModBlocks.taint = new BlockTaint(Material.iron).setBlockName("taint").setHardness(15.0F).setResistance(10.0F).setCreativeTab(null);
		ModBlocks.residue = new BlockCloudResidue(Material.iron).setBlockName("residue").setHardness(0.5F).setResistance(0.5F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":residue");

		ModBlocks.vent_chlorine = new BlockVent(Material.iron).setBlockName("vent_chlorine").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":vent_chlorine");
		ModBlocks.vent_cloud = new BlockVent(Material.iron).setBlockName("vent_cloud").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":vent_cloud");
		ModBlocks.vent_pink_cloud = new BlockVent(Material.iron).setBlockName("vent_pink_cloud").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":vent_pink_cloud");
		ModBlocks.vent_chlorine_seal = new BlockClorineSeal(Material.iron).setBlockName("vent_chlorine_seal").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		ModBlocks.chlorine_gas = new BlockGasClorine().setBlockName("chlorine_gas").setHardness(0.0F).setResistance(0.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":chlorine_gas");
		
		ModBlocks.gas_radon = new BlockGasRadon().setBlockName("gas_radon").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":gas_radon");
		ModBlocks.gas_radon_dense = new BlockGasRadonDense().setBlockName("gas_radon_dense").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":gas_radon_dense");
		ModBlocks.gas_radon_tomb = new BlockGasRadonTomb().setBlockName("gas_radon_tomb").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":gas_radon_tomb");
		ModBlocks.gas_meltdown = new BlockGasMeltdown().setBlockName("gas_meltdown").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":gas_meltdown");
		ModBlocks.gas_monoxide = new BlockGasMonoxide().setBlockName("gas_monoxide").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":gas_monoxide");
		ModBlocks.gas_asbestos = new BlockGasAsbestos().setBlockName("gas_asbestos").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":gas_asbestos");
		ModBlocks.gas_coal = new BlockGasCoal().setBlockName("gas_coal").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":gas_coal");
		ModBlocks.gas_flammable = new BlockGasFlammable().setBlockName("gas_flammable").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":gas_flammable");
		ModBlocks.gas_explosive = new BlockGasExplosive().setBlockName("gas_explosive").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":gas_explosive");
		ModBlocks.vacuum = new BlockVacuum().setBlockName("vacuum").setResistance(1000000F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":vacuum");

		ModBlocks.absorber = new BlockAbsorber(Material.iron, 2.5F).setBlockName("absorber").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":absorber");
		ModBlocks.absorber_red = new BlockAbsorber(Material.iron, 10F).setBlockName("absorber_red").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":absorber_red");
		ModBlocks.absorber_green = new BlockAbsorber(Material.iron, 100F).setBlockName("absorber_green").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":absorber_green");
		ModBlocks.absorber_pink = new BlockAbsorber(Material.iron, 10000F).setBlockName("absorber_pink").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":absorber_pink");
		ModBlocks.decon = new BlockDecon(Material.iron).setBlockName("decon").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":decon_side");
		ModBlocks.transission_hatch = new BlockTransission(Material.iron).setBlockName("transission_hatch").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":transission_hatch");
		
		ModBlocks.volcano_core = new BlockVolcano().setBlockName("volcano_core").setBlockUnbreakable().setResistance(10000.0F).setCreativeTab(MainRegistry.nukeTab).setBlockTextureName(RefStrings.MODID + ":volcano_core");

		ModBlocks.statue_elb = new DecoBlockAlt(Material.iron).setBlockName("#null").setHardness(Float.POSITIVE_INFINITY).setResistance(Float.POSITIVE_INFINITY);
		ModBlocks.statue_elb_g = new DecoBlockAlt(Material.iron).setBlockName("#void").setHardness(Float.POSITIVE_INFINITY).setResistance(Float.POSITIVE_INFINITY);
		ModBlocks.statue_elb_w = new DecoBlockAlt(Material.iron).setBlockName("#ngtv").setHardness(Float.POSITIVE_INFINITY).setResistance(Float.POSITIVE_INFINITY);
		ModBlocks.statue_elb_f = new DecoBlockAlt(Material.iron).setBlockName("#undef").setHardness(Float.POSITIVE_INFINITY).setLightLevel(1.0F).setResistance(Float.POSITIVE_INFINITY);

		ModBlocks.mud_fluid = new MudFluid().setDensity(2500).setViscosity(3000).setLuminosity(5).setTemperature(2773).setUnlocalizedName("mud_fluid");
		FluidRegistry.registerFluid(ModBlocks.mud_fluid);
		ModBlocks.mud_block = new MudBlock(ModBlocks.mud_fluid, ModBlocks.fluidmud.setReplaceable(), ModDamageSource.mudPoisoning).setBlockName("mud_block").setResistance(500F);

		ModBlocks.acid_fluid = new AcidFluid().setDensity(2500).setViscosity(1500).setLuminosity(5).setTemperature(2773).setUnlocalizedName("acid_fluid");
		FluidRegistry.registerFluid(ModBlocks.acid_fluid);
		ModBlocks.acid_block = new AcidBlock(ModBlocks.acid_fluid, ModBlocks.fluidacid.setReplaceable(), ModDamageSource.acid).setBlockName("acid_block").setResistance(500F);

		ModBlocks.toxic_fluid = new ToxicFluid().setDensity(2500).setViscosity(2000).setLuminosity(15).setTemperature(2773).setUnlocalizedName("toxic_fluid");
		FluidRegistry.registerFluid(ModBlocks.toxic_fluid);
		ModBlocks.toxic_block = new ToxicBlock(ModBlocks.toxic_fluid, ModBlocks.fluidtoxic.setReplaceable(), ModDamageSource.radiation).setBlockName("toxic_block").setResistance(500F);

		ModBlocks.schrabidic_fluid = new SchrabidicFluid().setDensity(31200).setViscosity(500).setTemperature(273).setUnlocalizedName("schrabidic_fluid");
		FluidRegistry.registerFluid(ModBlocks.schrabidic_fluid);
		ModBlocks.schrabidic_block = new SchrabidicBlock(ModBlocks.schrabidic_fluid, ModBlocks.fluidschrabidic.setReplaceable(), ModDamageSource.radiation).setBlockName("schrabidic_block").setResistance(500F);

		ModBlocks.corium_fluid = new CoriumFluid().setDensity(600000).setViscosity(12000).setLuminosity(10).setTemperature(1500).setUnlocalizedName("corium_fluid");
		FluidRegistry.registerFluid(ModBlocks.corium_fluid);
		ModBlocks.corium_block = new CoriumFinite(ModBlocks.corium_fluid, ModBlocks.fluidcorium).setBlockName("corium_block").setResistance(500F);

		ModBlocks.volcanic_lava_fluid = new VolcanicFluid().setLuminosity(15).setDensity(3000).setViscosity(3000).setTemperature(1300).setUnlocalizedName("volcanic_lava_fluid");
		FluidRegistry.registerFluid(ModBlocks.volcanic_lava_fluid);
		ModBlocks.volcanic_lava_block = new VolcanicBlock(ModBlocks.volcanic_lava_fluid, Material.lava).setBlockName("volcanic_lava_block").setResistance(500F);

		ModBlocks.sulfuric_acid_fluid = new GenericFluid("sulfuric_acid_fluid").setDensity(1840).setViscosity(1000).setTemperature(273);
		FluidRegistry.registerFluid(ModBlocks.sulfuric_acid_fluid);
		ModBlocks.sulfuric_acid_block = new GenericFluidBlock(ModBlocks.sulfuric_acid_fluid, Material.water, "sulfuric_acid_still", "sulfuric_acid_flowing").setDamage(ModDamageSource.acid, 5F).setBlockName("sulfuric_acid_block").setResistance(500F);
		
		Fluid liquidConcrete = new GenericFluid("concrete_liquid").setViscosity(2000);
		ModBlocks.concrete_liquid = new GenericFiniteFluid(liquidConcrete, Material.rock, "concrete_liquid", "concrete_liquid_flowing").setQuantaPerBlock(4).setBlockName("concrete_liquid").setResistance(500F);

		ModBlocks.dummy_block_drill = new DummyBlockDrill(Material.iron, false).setBlockName("dummy_block_drill").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_lead");
		ModBlocks.dummy_port_drill = new DummyBlockDrill(Material.iron, true).setBlockName("dummy_port_drill").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_lead");
		ModBlocks.dummy_block_ams_limiter = new DummyBlockAMSLimiter(Material.iron).setBlockName("dummy_block_ams_limiter").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_copper");
		ModBlocks.dummy_port_ams_limiter = new DummyBlockAMSLimiter(Material.iron).setBlockName("dummy_port_ams_limiter").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_copper");
		ModBlocks.dummy_block_ams_emitter = new DummyBlockAMSEmitter(Material.iron).setBlockName("dummy_block_ams_emitter").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_copper");
		ModBlocks.dummy_port_ams_emitter = new DummyBlockAMSEmitter(Material.iron).setBlockName("dummy_port_ams_emitter").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_copper");
		ModBlocks.dummy_block_ams_base = new DummyBlockAMSBase(Material.iron).setBlockName("dummy_block_ams_base").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_copper");
		ModBlocks.dummy_port_ams_base = new DummyBlockAMSBase(Material.iron).setBlockName("dummy_port_ams_base").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_copper");
		ModBlocks.dummy_block_vault = new DummyBlockVault(Material.iron).setBlockName("dummy_block_vault").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.dummy_block_blast = new DummyBlockBlast(Material.iron).setBlockName("dummy_block_blast").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.dummy_block_uf6 = new DummyBlockMachine(Material.iron, ModBlocks.machine_uf6_tank, false).setBlockName("dummy_block_uf6").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_titanium");
		ModBlocks.dummy_block_puf6 = new DummyBlockMachine(Material.iron, ModBlocks.machine_puf6_tank, false).setBlockName("dummy_block_puf6").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.dummy_plate_compact_launcher = new DummyBlockMachine(Material.iron, ModBlocks.compact_launcher, false).setBounds(0, 16, 0, 16, 16, 16).setBlockName("dummy_plate_compact_launcher").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.dummy_port_compact_launcher = new DummyBlockMachine(Material.iron, ModBlocks.compact_launcher, true).setBlockName("dummy_port_compact_launcher").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.dummy_plate_launch_table = new DummyBlockMachine(Material.iron, ModBlocks.launch_table, false).setBounds(0, 16, 0, 16, 16, 16).setBlockName("dummy_plate_launch_table").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.dummy_port_launch_table = new DummyBlockMachine(Material.iron, ModBlocks.launch_table, true).setBlockName("dummy_port_launch_table").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		ModBlocks.dummy_plate_cargo = new DummyBlockMachine(Material.iron, ModBlocks.sat_dock, false).setBounds(0, 0, 0, 16, 8, 16).setBlockName("dummy_plate_cargo").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		
		ModBlocks.ntm_dirt = new BlockNTMDirt().setBlockName("ntm_dirt").setHardness(0.5F).setStepSound(Block.soundTypeGravel).setCreativeTab(null).setBlockTextureName("dirt");

		ModBlocks.pink_log = new BlockPinkLog().setBlockName("pink_log").setHardness(0.5F).setStepSound(Block.soundTypeWood).setCreativeTab(null);
		ModBlocks.pink_planks = new BlockGeneric(Material.wood).setBlockName("pink_planks").setStepSound(Block.soundTypeWood).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":pink_planks");
		ModBlocks.pink_slab = new BlockPinkSlab(false, Material.wood).setBlockName("pink_slab").setStepSound(Block.soundTypeWood).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":pink_planks");
		ModBlocks.pink_double_slab = new BlockPinkSlab(true, Material.wood).setBlockName("pink_double_slab").setStepSound(Block.soundTypeWood).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":pink_planks");
		ModBlocks.pink_stairs = new BlockGenericStairs(ModBlocks.pink_planks, 0).setBlockName("pink_stairs").setStepSound(Block.soundTypeWood).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":pink_planks");
		
		ModBlocks.ff = new BlockFF(Material.iron).setBlockName("ff").setHardness(0.5F).setStepSound(Block.soundTypeGravel).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":code");
	}

	private static void registerBlock() {
		//Test
		GameRegistry.registerBlock(ModBlocks.test_render, ModBlocks.test_render.getUnlocalizedName());
		//GameRegistry.registerBlock(test_container, test_container.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.test_bomb, ModBlocks.test_bomb.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.test_bomb_advanced, ModBlocks.test_bomb_advanced.getUnlocalizedName());
		
		GameRegistry.registerBlock(ModBlocks.test_nuke, ModBlocks.test_nuke.getUnlocalizedName());
		
		GameRegistry.registerBlock(ModBlocks.event_tester, ModBlocks.event_tester.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.obj_tester, ModBlocks.obj_tester.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.test_core, ModBlocks.test_core.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.test_charge, ModBlocks.test_charge.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.test_pipe, ModBlocks.test_pipe.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.test_ct, ModBlocks.test_ct.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.test_rail, ModBlocks.test_rail.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.structure_anchor, ModBlocks.structure_anchor.getUnlocalizedName());

		//Ores
		GameRegistry.registerBlock(ModBlocks.ore_uranium, ModBlocks.ore_uranium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_uranium_scorched, ModBlocks.ore_uranium_scorched.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_thorium, ModBlocks.ore_thorium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_titanium, ModBlocks.ore_titanium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_sulfur, ModBlocks.ore_sulfur.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_niter, ModBlocks.ore_niter.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_copper, ModBlocks.ore_copper.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_tungsten, ModBlocks.ore_tungsten.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_aluminium, ModBlocks.ore_aluminium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_fluorite, ModBlocks.ore_fluorite.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_beryllium, ModBlocks.ore_beryllium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_lead, ModBlocks.ore_lead.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_oil, ItemBlockLore.class, ModBlocks.ore_oil.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_oil_empty, ModBlocks.ore_oil_empty.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_oil_sand, ModBlocks.ore_oil_sand.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_lignite, ModBlocks.ore_lignite.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_asbestos, ModBlocks.ore_asbestos.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_schrabidium, ItemBlockLore.class, ModBlocks.ore_schrabidium.getUnlocalizedName());
		
		//Rare Minerals
		GameRegistry.registerBlock(ModBlocks.ore_australium, ItemOreBlock.class, ModBlocks.ore_australium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_weidanium, ItemOreBlock.class, ModBlocks.ore_weidanium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_reiium, ItemOreBlock.class, ModBlocks.ore_reiium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_unobtainium, ItemOreBlock.class, ModBlocks.ore_unobtainium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_daffergon, ItemOreBlock.class, ModBlocks.ore_daffergon.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_verticium, ItemOreBlock.class, ModBlocks.ore_verticium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_rare, ItemOreBlock.class, ModBlocks.ore_rare.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_cobalt, ModBlocks.ore_cobalt.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_cinnebar, ModBlocks.ore_cinnebar.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_coltan, ModBlocks.ore_coltan.getUnlocalizedName());
		
		//Stone clusters
		GameRegistry.registerBlock(ModBlocks.cluster_iron, ItemBlockBase.class, ModBlocks.cluster_iron.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.cluster_titanium, ItemBlockBase.class, ModBlocks.cluster_titanium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.cluster_aluminium, ItemBlockBase.class, ModBlocks.cluster_aluminium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.cluster_copper, ItemBlockBase.class, ModBlocks.cluster_copper.getUnlocalizedName());
		
		//Bedrock ores
		GameRegistry.registerBlock(ModBlocks.ore_bedrock_oil, ModBlocks.ore_bedrock_oil.getUnlocalizedName());
		
		//Nice Meme
		GameRegistry.registerBlock(ModBlocks.ore_coal_oil, ModBlocks.ore_coal_oil.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_coal_oil_burning, ModBlocks.ore_coal_oil_burning.getUnlocalizedName());
		
		//Nether Ores
		GameRegistry.registerBlock(ModBlocks.ore_nether_coal, ModBlocks.ore_nether_coal.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_nether_smoldering, ModBlocks.ore_nether_smoldering.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_nether_uranium, ModBlocks.ore_nether_uranium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_nether_uranium_scorched, ModBlocks.ore_nether_uranium_scorched.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_nether_plutonium, ModBlocks.ore_nether_plutonium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_nether_tungsten, ModBlocks.ore_nether_tungsten.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_nether_sulfur, ModBlocks.ore_nether_sulfur.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_nether_fire, ModBlocks.ore_nether_fire.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_nether_cobalt, ModBlocks.ore_nether_cobalt.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_nether_schrabidium, ItemBlockLore.class, ModBlocks.ore_nether_schrabidium.getUnlocalizedName());
		
		//Meteor Ores
		GameRegistry.registerBlock(ModBlocks.ore_meteor_uranium, ModBlocks.ore_meteor_uranium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_meteor_thorium, ModBlocks.ore_meteor_thorium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_meteor_titanium, ModBlocks.ore_meteor_titanium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_meteor_sulfur, ModBlocks.ore_meteor_sulfur.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_meteor_copper, ModBlocks.ore_meteor_copper.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_meteor_tungsten, ModBlocks.ore_meteor_tungsten.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_meteor_aluminium, ModBlocks.ore_meteor_aluminium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_meteor_lead, ModBlocks.ore_meteor_lead.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_meteor_lithium, ModBlocks.ore_meteor_lithium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_meteor_starmetal, ModBlocks.ore_meteor_starmetal.getUnlocalizedName());
		
		//Gneiss Ores
		GameRegistry.registerBlock(ModBlocks.ore_gneiss_iron, ModBlocks.ore_gneiss_iron.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_gneiss_gold, ModBlocks.ore_gneiss_gold.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_gneiss_uranium, ModBlocks.ore_gneiss_uranium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_gneiss_uranium_scorched, ModBlocks.ore_gneiss_uranium_scorched.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_gneiss_copper, ModBlocks.ore_gneiss_copper.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_gneiss_asbestos, ModBlocks.ore_gneiss_asbestos.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_gneiss_lithium, ModBlocks.ore_gneiss_lithium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_gneiss_schrabidium, ItemBlockLore.class, ModBlocks.ore_gneiss_schrabidium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_gneiss_rare, ItemOreBlock.class, ModBlocks.ore_gneiss_rare.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_gneiss_gas, ModBlocks.ore_gneiss_gas.getUnlocalizedName());
		
		//Depth ores
		GameRegistry.registerBlock(ModBlocks.ore_depth_cinnebar, ItemBlockBase.class, ModBlocks.ore_depth_cinnebar.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_depth_zirconium, ItemBlockBase.class, ModBlocks.ore_depth_zirconium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_depth_borax, ItemBlockBase.class, ModBlocks.ore_depth_borax.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.cluster_depth_iron, ItemBlockBase.class, ModBlocks.cluster_depth_iron.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.cluster_depth_titanium, ItemBlockBase.class, ModBlocks.cluster_depth_titanium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.cluster_depth_tungsten, ItemBlockBase.class, ModBlocks.cluster_depth_tungsten.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_alexandrite, ItemBlockBase.class, ModBlocks.ore_alexandrite.getUnlocalizedName());
		
		//Nether depth ores
		GameRegistry.registerBlock(ModBlocks.ore_depth_nether_neodymium, ItemBlockBase.class, ModBlocks.ore_depth_nether_neodymium.getUnlocalizedName());
		
		//Basalt ores
		GameRegistry.registerBlock(ModBlocks.basalt_sulfur, ModBlocks.basalt_sulfur.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.basalt_fluorite, ModBlocks.basalt_fluorite.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.basalt_asbestos, ModBlocks.basalt_asbestos.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.basalt_gem, ItemBlockBase.class, ModBlocks.basalt_gem.getUnlocalizedName());
		
		//End Ores
		GameRegistry.registerBlock(ModBlocks.ore_tikite, ModBlocks.ore_tikite.getUnlocalizedName());
		
		//It's a meme you dip
		GameRegistry.registerBlock(ModBlocks.ore_random, ItemRandomOreBlock.class, ModBlocks.ore_random.getUnlocalizedName());
		
		//Bedrock ore
		ModBlocks.register(ModBlocks.ore_bedrock);
		ModBlocks.register(ModBlocks.ore_volcano);
		
		//Crystals
		GameRegistry.registerBlock(ModBlocks.crystal_power, ModBlocks.crystal_power.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.crystal_energy, ModBlocks.crystal_energy.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.crystal_robust, ModBlocks.crystal_robust.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.crystal_trixite, ModBlocks.crystal_trixite.getUnlocalizedName());
		
		//Resource-bearing Stones
		ModBlocks.register(ModBlocks.stone_resource);
		ModBlocks.register(ModBlocks.stalagmite);
		ModBlocks.register(ModBlocks.stalactite);
		ModBlocks.register(ModBlocks.stone_biome);
		
		//Stone Variants
		GameRegistry.registerBlock(ModBlocks.stone_porous, ModBlocks.stone_porous.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.stone_gneiss, ModBlocks.stone_gneiss.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.gneiss_brick, ModBlocks.gneiss_brick.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.gneiss_tile, ModBlocks.gneiss_tile.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.gneiss_chiseled, ModBlocks.gneiss_chiseled.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.stone_depth, ItemBlockBase.class, ModBlocks.stone_depth.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.depth_brick, ItemBlockBase.class, ModBlocks.depth_brick.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.depth_tiles, ItemBlockBase.class, ModBlocks.depth_tiles.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.stone_depth_nether, ItemBlockBase.class, ModBlocks.stone_depth_nether.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.depth_nether_brick, ItemBlockBase.class, ModBlocks.depth_nether_brick.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.depth_nether_tiles, ItemBlockBase.class, ModBlocks.depth_nether_tiles.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.depth_dnt, ItemBlockBase.class, ModBlocks.depth_dnt.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.basalt, ModBlocks.basalt.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.basalt_smooth, ModBlocks.basalt_smooth.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.basalt_brick, ModBlocks.basalt_brick.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.basalt_polished, ModBlocks.basalt_polished.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.basalt_tiles, ModBlocks.basalt_tiles.getUnlocalizedName());
		//GameRegistry.registerBlock(stone_deep_cobble, ItemBlockBase.class, stone_deep_cobble.getUnlocalizedName());
		
		//Blocks
		GameRegistry.registerBlock(ModBlocks.block_uranium, ModBlocks.block_uranium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_u233, ModBlocks.block_u233.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_u235, ModBlocks.block_u235.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_u238, ModBlocks.block_u238.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_uranium_fuel, ModBlocks.block_uranium_fuel.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_neptunium, ModBlocks.block_neptunium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_polonium, ModBlocks.block_polonium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_mox_fuel, ModBlocks.block_mox_fuel.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_plutonium, ModBlocks.block_plutonium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_pu238, ModBlocks.block_pu238.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_pu239, ModBlocks.block_pu239.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_pu240, ModBlocks.block_pu240.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_pu_mix, ModBlocks.block_pu_mix.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_plutonium_fuel, ModBlocks.block_plutonium_fuel.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_thorium, ModBlocks.block_thorium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_thorium_fuel, ModBlocks.block_thorium_fuel.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_titanium, ModBlocks.block_titanium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_sulfur, ModBlocks.block_sulfur.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_niter, ModBlocks.block_niter.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_niter_reinforced, ModBlocks.block_niter_reinforced.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_copper, ModBlocks.block_copper.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_red_copper, ModBlocks.block_red_copper.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_advanced_alloy, ModBlocks.block_advanced_alloy.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_tungsten, ModBlocks.block_tungsten.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_aluminium, ModBlocks.block_aluminium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_fluorite, ModBlocks.block_fluorite.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_beryllium, ModBlocks.block_beryllium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_cobalt, ModBlocks.block_cobalt.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_steel, ModBlocks.block_steel.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_tcalloy, ModBlocks.block_tcalloy.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_cdalloy, ModBlocks.block_cdalloy.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_lead, ModBlocks.block_lead.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_bismuth, ModBlocks.block_bismuth.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_cadmium, ModBlocks.block_cadmium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_coltan, ModBlocks.block_coltan.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_tantalium, ModBlocks.block_tantalium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_niobium, ModBlocks.block_niobium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_lithium, ModBlocks.block_lithium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_zirconium, ModBlocks.block_zirconium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_white_phosphorus, ModBlocks.block_white_phosphorus.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_red_phosphorus, ModBlocks.block_red_phosphorus.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_yellowcake, ModBlocks.block_yellowcake.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_scrap, ModBlocks.block_scrap.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_electrical_scrap, ModBlocks.block_electrical_scrap.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_fallout, ModBlocks.block_fallout.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_foam, ModBlocks.block_foam.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_graphite, ModBlocks.block_graphite.getUnlocalizedName());
		ModBlocks.register(ModBlocks.block_coke);
		GameRegistry.registerBlock(ModBlocks.block_graphite_drilled, ModBlocks.block_graphite_drilled.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_graphite_fuel, ModBlocks.block_graphite_fuel.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_graphite_rod, ModBlocks.block_graphite_rod.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_graphite_plutonium, ModBlocks.block_graphite_plutonium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_graphite_source, ModBlocks.block_graphite_source.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_graphite_lithium, ModBlocks.block_graphite_lithium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_graphite_tritium, ModBlocks.block_graphite_tritium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_graphite_detector, ModBlocks.block_graphite_detector.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_boron, ModBlocks.block_boron.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_insulator, ModBlocks.block_insulator.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_fiberglass, ModBlocks.block_fiberglass.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_asbestos, ModBlocks.block_asbestos.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_trinitite, ModBlocks.block_trinitite.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_waste, ModBlocks.block_waste.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_waste_painted, ModBlocks.block_waste_painted.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_waste_vitrified, ModBlocks.block_waste_vitrified.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ancient_scrap, ModBlocks.ancient_scrap.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_corium, ModBlocks.block_corium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_corium_cobble, ModBlocks.block_corium_cobble.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_schraranium, ItemBlockBase.class, ModBlocks.block_schraranium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_schrabidium, ItemBlockBase.class, ModBlocks.block_schrabidium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_schrabidate, ItemBlockBase.class, ModBlocks.block_schrabidate.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_solinium, ItemBlockBase.class, ModBlocks.block_solinium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_schrabidium_fuel, ItemBlockBase.class, ModBlocks.block_schrabidium_fuel.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_euphemium, ItemBlockLore.class, ModBlocks.block_euphemium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_schrabidium_cluster, ItemBlockBase.class, ModBlocks.block_schrabidium_cluster.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_euphemium_cluster, ItemBlockLore.class, ModBlocks.block_euphemium_cluster.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_dineutronium, ModBlocks.block_dineutronium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_magnetized_tungsten, ModBlocks.block_magnetized_tungsten.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_combine_steel, ModBlocks.block_combine_steel.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_desh, ModBlocks.block_desh.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_dura_steel, ModBlocks.block_dura_steel.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_starmetal, ModBlocks.block_starmetal.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_polymer, ModBlocks.block_polymer.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_bakelite, ModBlocks.block_bakelite.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_rubber, ModBlocks.block_rubber.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_australium, ItemOreBlock.class, ModBlocks.block_australium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_weidanium, ItemOreBlock.class, ModBlocks.block_weidanium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_reiium, ItemOreBlock.class, ModBlocks.block_reiium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_unobtainium, ItemOreBlock.class, ModBlocks.block_unobtainium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_daffergon, ItemOreBlock.class, ModBlocks.block_daffergon.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_verticium, ItemOreBlock.class, ModBlocks.block_verticium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_lanthanium, ModBlocks.block_lanthanium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_ra226, ModBlocks.block_ra226.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_actinium, ModBlocks.block_actinium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_tritium, ModBlocks.block_tritium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_semtex, ModBlocks.block_semtex.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_c4, ModBlocks.block_c4.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_smore, ModBlocks.block_smore.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_slag, ModBlocks.block_slag.getUnlocalizedName());

		//Bottlecap Blocks
		GameRegistry.registerBlock(ModBlocks.block_cap_nuka, ModBlocks.block_cap_nuka.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_cap_quantum, ModBlocks.block_cap_quantum.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_cap_rad, ModBlocks.block_cap_rad.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_cap_sparkle, ModBlocks.block_cap_sparkle.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_cap_korl, ModBlocks.block_cap_korl.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_cap_fritz, ModBlocks.block_cap_fritz.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_cap_sunset, ModBlocks.block_cap_sunset.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_cap_star, ModBlocks.block_cap_star.getUnlocalizedName());
		
		//Deco Blocks
		GameRegistry.registerBlock(ModBlocks.deco_titanium, ModBlocks.deco_titanium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_red_copper, ModBlocks.deco_red_copper.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_tungsten, ModBlocks.deco_tungsten.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_aluminium, ModBlocks.deco_aluminium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_steel, ModBlocks.deco_steel.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_lead, ModBlocks.deco_lead.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_beryllium, ModBlocks.deco_beryllium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_asbestos, ModBlocks.deco_asbestos.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_emitter, ItemBlockBase.class, ModBlocks.deco_emitter.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.part_emitter, ItemBlockBase.class, ModBlocks.part_emitter.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_loot, ModBlocks.deco_loot.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.bobblehead, ItemBlockMeta.class, ModBlocks.bobblehead.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.snowglobe, ItemBlockMeta.class, ModBlocks.snowglobe.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.hazmat, ModBlocks.hazmat.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_rbmk, ModBlocks.deco_rbmk.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_rbmk_smooth, ModBlocks.deco_rbmk_smooth.getUnlocalizedName());
		
		//Gravel
		GameRegistry.registerBlock(ModBlocks.gravel_obsidian, ItemBlockBlastInfo.class, ModBlocks.gravel_obsidian.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.gravel_diamond, ItemBlockLore.class, ModBlocks.gravel_diamond.getUnlocalizedName());
		
		//Lamps
		GameRegistry.registerBlock(ModBlocks.lamp_tritium_green_off, ModBlocks.lamp_tritium_green_off.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.lamp_tritium_green_on, ModBlocks.lamp_tritium_green_on.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.lamp_tritium_blue_off, ModBlocks.lamp_tritium_blue_off.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.lamp_tritium_blue_on, ModBlocks.lamp_tritium_blue_on.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.lamp_uv_off, ModBlocks.lamp_uv_off.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.lamp_uv_on, ModBlocks.lamp_uv_on.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.lamp_demon, ModBlocks.lamp_demon.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.lantern, ModBlocks.lantern.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.lantern_behemoth, ModBlocks.lantern_behemoth.getUnlocalizedName());

		//Reinforced Blocks
		GameRegistry.registerBlock(ModBlocks.asphalt, ItemBlockBlastInfo.class, ModBlocks.asphalt.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.asphalt_light, ItemBlockBlastInfo.class, ModBlocks.asphalt_light.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.reinforced_brick, ItemBlockBlastInfo.class, ModBlocks.reinforced_brick.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.reinforced_glass, ItemBlockBlastInfo.class, ModBlocks.reinforced_glass.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.reinforced_glass_pane, ItemBlockBlastInfo.class, ModBlocks.reinforced_glass_pane.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.reinforced_light, ItemBlockBlastInfo.class, ModBlocks.reinforced_light.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.reinforced_sand, ItemBlockBlastInfo.class, ModBlocks.reinforced_sand.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.reinforced_lamp_off, ItemBlockBlastInfo.class, ModBlocks.reinforced_lamp_off.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.reinforced_lamp_on, ItemBlockBlastInfo.class, ModBlocks.reinforced_lamp_on.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.reinforced_laminate, ItemBlockBlastInfo.class, ModBlocks.reinforced_laminate.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.reinforced_laminate_pane,ItemBlockBlastInfo.class, ModBlocks.reinforced_laminate_pane.getUnlocalizedName());

		//Bricks
		GameRegistry.registerBlock(ModBlocks.reinforced_stone, ItemBlockBlastInfo.class, ModBlocks.reinforced_stone.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.reinforced_ducrete, ItemBlockBlastInfo.class, ModBlocks.reinforced_ducrete.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.concrete_smooth, ItemBlockBlastInfo.class, ModBlocks.concrete_smooth.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.concrete_colored, ItemBlockColoredConcrete.class, ModBlocks.concrete_colored.getUnlocalizedName());
		ModBlocks.register(ModBlocks.concrete_colored_ext);
		GameRegistry.registerBlock(ModBlocks.concrete, ItemBlockBlastInfo.class, ModBlocks.concrete.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.concrete_asbestos, ItemBlockBlastInfo.class, ModBlocks.concrete_asbestos.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.concrete_super, ItemBlockBlastInfo.class, ModBlocks.concrete_super.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.concrete_super_broken, ItemBlockBlastInfo.class, ModBlocks.concrete_super_broken.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ducrete_smooth, ItemBlockBlastInfo.class, ModBlocks.ducrete_smooth.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ducrete, ItemBlockBlastInfo.class, ModBlocks.ducrete.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.concrete_pillar, ItemBlockBlastInfo.class, ModBlocks.concrete_pillar.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_concrete, ItemBlockBlastInfo.class, ModBlocks.brick_concrete.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_concrete_mossy, ItemBlockBlastInfo.class, ModBlocks.brick_concrete_mossy.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_concrete_cracked, ItemBlockBlastInfo.class, ModBlocks.brick_concrete_cracked.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_concrete_broken, ItemBlockBlastInfo.class, ModBlocks.brick_concrete_broken.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_concrete_marked, ItemBlockBlastInfo.class, ModBlocks.brick_concrete_marked.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_ducrete, ItemBlockBlastInfo.class, ModBlocks.brick_ducrete.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_obsidian, ItemBlockBlastInfo.class, ModBlocks.brick_obsidian.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_compound, ItemBlockBlastInfo.class, ModBlocks.brick_compound.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_light, ItemBlockBlastInfo.class, ModBlocks.brick_light.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_asbestos, ModBlocks.brick_asbestos.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_fire, ItemBlockBlastInfo.class, ModBlocks.brick_fire.getUnlocalizedName());

		GameRegistry.registerBlock(ModBlocks.concrete_slab, ItemModSlab.class, ModBlocks.concrete_slab.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.concrete_double_slab, ItemModSlab.class, ModBlocks.concrete_double_slab.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.concrete_brick_slab, ItemModSlab.class, ModBlocks.concrete_brick_slab.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.concrete_brick_double_slab, ItemModSlab.class, ModBlocks.concrete_brick_double_slab.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_slab, ItemModSlab.class, ModBlocks.brick_slab.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_double_slab, ItemModSlab.class, ModBlocks.brick_double_slab.getUnlocalizedName());

		GameRegistry.registerBlock(ModBlocks.concrete_smooth_stairs, ModBlocks.concrete_smooth_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.concrete_stairs, ModBlocks.concrete_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.concrete_asbestos_stairs, ModBlocks.concrete_asbestos_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ducrete_smooth_stairs, ModBlocks.ducrete_smooth_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_concrete_stairs, ModBlocks.brick_concrete_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_concrete_mossy_stairs, ModBlocks.brick_concrete_mossy_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_concrete_cracked_stairs, ModBlocks.brick_concrete_cracked_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_concrete_broken_stairs, ModBlocks.brick_concrete_broken_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_ducrete_stairs, ModBlocks.brick_ducrete_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.reinforced_stone_stairs, ModBlocks.reinforced_stone_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.reinforced_brick_stairs, ModBlocks.reinforced_brick_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_obsidian_stairs, ModBlocks.brick_obsidian_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_light_stairs, ModBlocks.brick_light_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_compound_stairs, ModBlocks.brick_compound_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_asbestos_stairs, ModBlocks.brick_asbestos_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_fire_stairs, ModBlocks.brick_fire_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ducrete_stairs, ModBlocks.ducrete_stairs.getUnlocalizedName());
		
		//CMB Building Elements
		GameRegistry.registerBlock(ModBlocks.cmb_brick, ItemBlockBlastInfo.class, ModBlocks.cmb_brick.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.cmb_brick_reinforced, ItemBlockBlastInfo.class, ModBlocks.cmb_brick_reinforced.getUnlocalizedName());
		
		//Tiles
		GameRegistry.registerBlock(ModBlocks.vinyl_tile, ItemBlockBlastInfo.class, ModBlocks.vinyl_tile.getUnlocalizedName()); //i would rather die than dip into fucking blocks with subtypes again
		
		GameRegistry.registerBlock(ModBlocks.tile_lab, ModBlocks.tile_lab.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.tile_lab_cracked, ModBlocks.tile_lab_cracked.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.tile_lab_broken, ModBlocks.tile_lab_broken.getUnlocalizedName());
		
		//Other defensive stuff
		GameRegistry.registerBlock(ModBlocks.barbed_wire, ModBlocks.barbed_wire.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.barbed_wire_fire, ModBlocks.barbed_wire_fire.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.barbed_wire_poison, ModBlocks.barbed_wire_poison.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.barbed_wire_acid, ModBlocks.barbed_wire_acid.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.barbed_wire_wither, ModBlocks.barbed_wire_wither.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.barbed_wire_ultradeath, ModBlocks.barbed_wire_ultradeath.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.spikes, ModBlocks.spikes.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.tesla, ModBlocks.tesla.getUnlocalizedName());
		
		//Charger
		GameRegistry.registerBlock(ModBlocks.charger, ModBlocks.charger.getUnlocalizedName());
		
		//Siege blocks
		GameRegistry.registerBlock(ModBlocks.siege_shield, ItemBlockLore.class, ModBlocks.siege_shield.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.siege_internal, ItemBlockLore.class, ModBlocks.siege_internal.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.siege_circuit, ItemBlockLore.class, ModBlocks.siege_circuit.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.siege_emergency, ModBlocks.siege_emergency.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.siege_hole, ModBlocks.siege_hole.getUnlocalizedName());
		
		//Decoration Blocks
		GameRegistry.registerBlock(ModBlocks.block_meteor, ModBlocks.block_meteor.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_meteor_cobble, ModBlocks.block_meteor_cobble.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_meteor_broken, ModBlocks.block_meteor_broken.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_meteor_molten, ModBlocks.block_meteor_molten.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.block_meteor_treasure, ModBlocks.block_meteor_treasure.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.meteor_polished, ModBlocks.meteor_polished.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.meteor_brick, ModBlocks.meteor_brick.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.meteor_brick_mossy, ModBlocks.meteor_brick_mossy.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.meteor_brick_cracked, ModBlocks.meteor_brick_cracked.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.meteor_brick_chiseled, ModBlocks.meteor_brick_chiseled.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.meteor_pillar, ModBlocks.meteor_pillar.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.meteor_spawner, ModBlocks.meteor_spawner.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.meteor_battery, ItemBlockLore.class, ModBlocks.meteor_battery.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_jungle, ModBlocks.brick_jungle.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_jungle_cracked, ModBlocks.brick_jungle_cracked.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_jungle_fragile, ModBlocks.brick_jungle_fragile.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_jungle_lava, ModBlocks.brick_jungle_lava.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_jungle_ooze, ModBlocks.brick_jungle_ooze.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_jungle_mystic, ModBlocks.brick_jungle_mystic.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_jungle_trap, ItemTrapBlock.class, ModBlocks.brick_jungle_trap.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_jungle_glyph, ItemGlyphBlock.class, ModBlocks.brick_jungle_glyph.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_jungle_circle, ModBlocks.brick_jungle_circle.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_dungeon, ModBlocks.brick_dungeon.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_dungeon_flat, ModBlocks.brick_dungeon_flat.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_dungeon_tile, ModBlocks.brick_dungeon_tile.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_dungeon_circle, ModBlocks.brick_dungeon_circle.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.brick_forgotten, ModBlocks.brick_forgotten.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_computer, ItemBlockBase.class, ModBlocks.deco_computer.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.filing_cabinet, ItemBlockBase.class, ModBlocks.filing_cabinet.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.tape_recorder, ModBlocks.tape_recorder.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.steel_poles, ModBlocks.steel_poles.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.pole_top, ModBlocks.pole_top.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.pole_satellite_receiver, ModBlocks.pole_satellite_receiver.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.steel_wall, ModBlocks.steel_wall.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.steel_corner, ModBlocks.steel_corner.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.steel_roof, ModBlocks.steel_roof.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.steel_beam, ModBlocks.steel_beam.getUnlocalizedName());
		ModBlocks.register(ModBlocks.steel_scaffold);
		GameRegistry.registerBlock(ModBlocks.steel_grate, ModBlocks.steel_grate.getUnlocalizedName());
		ModBlocks.register(ModBlocks.steel_grate_wide);
		GameRegistry.registerBlock(ModBlocks.deco_pipe, ItemBlockBase.class, ModBlocks.deco_pipe.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_pipe_rusted, ItemBlockBase.class, ModBlocks.deco_pipe_rusted.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_pipe_green, ItemBlockBase.class, ModBlocks.deco_pipe_green.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_pipe_green_rusted, ItemBlockBase.class, ModBlocks.deco_pipe_green_rusted.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_pipe_red, ItemBlockBase.class, ModBlocks.deco_pipe_red.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_pipe_marked, ItemBlockBase.class, ModBlocks.deco_pipe_marked.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_pipe_rim, ItemBlockBase.class, ModBlocks.deco_pipe_rim.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_pipe_rim_rusted, ItemBlockBase.class, ModBlocks.deco_pipe_rim_rusted.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_pipe_rim_green, ItemBlockBase.class, ModBlocks.deco_pipe_rim_green.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_pipe_rim_green_rusted, ItemBlockBase.class, ModBlocks.deco_pipe_rim_green_rusted.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_pipe_rim_red, ItemBlockBase.class, ModBlocks.deco_pipe_rim_red.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_pipe_rim_marked, ItemBlockBase.class, ModBlocks.deco_pipe_rim_marked.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_pipe_framed, ItemBlockBase.class, ModBlocks.deco_pipe_framed.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_pipe_framed_rusted, ItemBlockBase.class, ModBlocks.deco_pipe_framed_rusted.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_pipe_framed_green, ItemBlockBase.class, ModBlocks.deco_pipe_framed_green.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_pipe_framed_green_rusted, ItemBlockBase.class, ModBlocks.deco_pipe_framed_green_rusted.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_pipe_framed_red, ItemBlockBase.class, ModBlocks.deco_pipe_framed_red.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_pipe_framed_marked, ItemBlockBase.class, ModBlocks.deco_pipe_framed_marked.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_pipe_quad, ItemBlockBase.class, ModBlocks.deco_pipe_quad.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_pipe_quad_rusted, ItemBlockBase.class, ModBlocks.deco_pipe_quad_rusted.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_pipe_quad_green, ItemBlockBase.class, ModBlocks.deco_pipe_quad_green.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_pipe_quad_green_rusted, ItemBlockBase.class, ModBlocks.deco_pipe_quad_green_rusted.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_pipe_quad_red, ItemBlockBase.class, ModBlocks.deco_pipe_quad_red.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.deco_pipe_quad_marked, ItemBlockBase.class, ModBlocks.deco_pipe_quad_marked.getUnlocalizedName());
		ModBlocks.register(ModBlocks.plant_flower);
		ModBlocks.register(ModBlocks.plant_tall);
		ModBlocks.register(ModBlocks.plant_dead);
		ModBlocks.register(ModBlocks.reeds);
		GameRegistry.registerBlock(ModBlocks.mush, ModBlocks.mush.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.mush_block, ModBlocks.mush_block.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.mush_block_stem, ModBlocks.mush_block_stem.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.glyphid_base, ModBlocks.glyphid_base.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.glyphid_spawner, ModBlocks.glyphid_spawner.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.moon_turf, ModBlocks.moon_turf.getUnlocalizedName());
		
		//Waste
		GameRegistry.registerBlock(ModBlocks.waste_earth, ModBlocks.waste_earth.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.waste_mycelium, ModBlocks.waste_mycelium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.waste_trinitite, ModBlocks.waste_trinitite.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.waste_trinitite_red, ModBlocks.waste_trinitite_red.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.waste_log, ModBlocks.waste_log.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.waste_leaves, ModBlocks.waste_leaves.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.waste_planks, ModBlocks.waste_planks.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.frozen_grass, ModBlocks.frozen_grass.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.frozen_dirt, ModBlocks.frozen_dirt.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.frozen_log, ModBlocks.frozen_log.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.frozen_planks, ModBlocks.frozen_planks.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.dirt_dead, ModBlocks.dirt_dead.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.dirt_oily, ModBlocks.dirt_oily.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.sand_dirty, ModBlocks.sand_dirty.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.sand_dirty_red, ModBlocks.sand_dirty_red.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.stone_cracked, ModBlocks.stone_cracked.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.fallout, ModBlocks.fallout.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.foam_layer, ModBlocks.foam_layer.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.sand_boron_layer, ModBlocks.sand_boron_layer.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.leaves_layer, ModBlocks.leaves_layer.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.burning_earth, ModBlocks.burning_earth.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.tektite, ModBlocks.tektite.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ore_tektite_osmiridium, ModBlocks.ore_tektite_osmiridium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.impact_dirt, ModBlocks.impact_dirt.getUnlocalizedName());
		
		//RAD
		GameRegistry.registerBlock(ModBlocks.sellafield_slaked, ModBlocks.sellafield_slaked.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.sellafield, ItemBlockNamedMeta.class, ModBlocks.sellafield.getUnlocalizedName());

		//Geysirs
		GameRegistry.registerBlock(ModBlocks.geysir_water, ModBlocks.geysir_water.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.geysir_chlorine, ModBlocks.geysir_chlorine.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.geysir_vapor, ModBlocks.geysir_vapor.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.geysir_nether, ModBlocks.geysir_nether.getUnlocalizedName());

		//Nukes
		GameRegistry.registerBlock(ModBlocks.nuke_gadget, ModBlocks.nuke_gadget.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.nuke_boy, ModBlocks.nuke_boy.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.nuke_man, ModBlocks.nuke_man.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.nuke_mike, ModBlocks.nuke_mike.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.nuke_tsar, ModBlocks.nuke_tsar.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.nuke_prototype, ItemPrototypeBlock.class, ModBlocks.nuke_prototype.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.nuke_fleija, ModBlocks.nuke_fleija.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.nuke_solinium, ModBlocks.nuke_solinium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.nuke_n2, ModBlocks.nuke_n2.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.nuke_n45, ModBlocks.nuke_n45.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.nuke_fstbmb, ModBlocks.nuke_fstbmb.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.nuke_custom, ModBlocks.nuke_custom.getUnlocalizedName());
		
		//Generic Bombs
		GameRegistry.registerBlock(ModBlocks.bomb_multi, ModBlocks.bomb_multi.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.crashed_balefire, ModBlocks.crashed_balefire.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.fireworks, ModBlocks.fireworks.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.dynamite, ModBlocks.dynamite.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.tnt, ModBlocks.tnt.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.semtex, ModBlocks.semtex.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.c4, ModBlocks.c4.getUnlocalizedName());
		ModBlocks.register(ModBlocks.fissure_bomb);
		
		//Turrets
		GameRegistry.registerBlock(ModBlocks.turret_chekhov, ModBlocks.turret_chekhov.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.turret_friendly, ModBlocks.turret_friendly.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.turret_jeremy, ModBlocks.turret_jeremy.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.turret_tauon, ModBlocks.turret_tauon.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.turret_richard, ModBlocks.turret_richard.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.turret_howard, ModBlocks.turret_howard.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.turret_howard_damaged, ModBlocks.turret_howard_damaged.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.turret_maxwell, ModBlocks.turret_maxwell.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.turret_fritz, ModBlocks.turret_fritz.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.turret_brandon, ModBlocks.turret_brandon.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.turret_arty, ModBlocks.turret_arty.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.turret_himars, ModBlocks.turret_himars.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.turret_sentry, ModBlocks.turret_sentry.getUnlocalizedName());
		
		//Wall-mounted Explosives
		GameRegistry.registerBlock(ModBlocks.charge_dynamite, ItemBlockBase.class, ModBlocks.charge_dynamite.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.charge_miner, ItemBlockBase.class, ModBlocks.charge_miner.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.charge_c4, ItemBlockBase.class, ModBlocks.charge_c4.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.charge_semtex, ItemBlockBase.class, ModBlocks.charge_semtex.getUnlocalizedName());
		
		//Mines
		GameRegistry.registerBlock(ModBlocks.mine_ap, ModBlocks.mine_ap.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.mine_he, ModBlocks.mine_he.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.mine_shrap, ModBlocks.mine_shrap.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.mine_fat, ModBlocks.mine_fat.getUnlocalizedName());
		
		//Block Bombs
		GameRegistry.registerBlock(ModBlocks.flame_war, ModBlocks.flame_war.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.float_bomb, ModBlocks.float_bomb.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.therm_endo, ModBlocks.therm_endo.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.therm_exo, ModBlocks.therm_exo.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.emp_bomb, ModBlocks.emp_bomb.getUnlocalizedName());
		//GameRegistry.registerBlock(rejuvinator, rejuvinator.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.det_cord, ModBlocks.det_cord.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.det_charge, ModBlocks.det_charge.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.det_nuke, ModBlocks.det_nuke.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.det_miner, ModBlocks.det_miner.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.red_barrel, ItemBlockLore.class, ModBlocks.red_barrel.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.pink_barrel, ItemBlockLore.class, ModBlocks.pink_barrel.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.lox_barrel, ItemBlockLore.class, ModBlocks.lox_barrel.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.taint_barrel, ModBlocks.taint_barrel.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.yellow_barrel, ModBlocks.yellow_barrel.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.vitrified_barrel, ModBlocks.vitrified_barrel.getUnlocalizedName());
		
		//Siren
		GameRegistry.registerBlock(ModBlocks.machine_siren, ModBlocks.machine_siren.getUnlocalizedName());
		
		//This Thing
		GameRegistry.registerBlock(ModBlocks.broadcaster_pc, ModBlocks.broadcaster_pc.getUnlocalizedName());
		
		//Geiger Counter
		GameRegistry.registerBlock(ModBlocks.geiger, ModBlocks.geiger.getUnlocalizedName());
		
		//HEV Battery
		GameRegistry.registerBlock(ModBlocks.hev_battery, ModBlocks.hev_battery.getUnlocalizedName());
		
		//Chainlink Fence
		GameRegistry.registerBlock(ModBlocks.fence_metal, ModBlocks.fence_metal.getUnlocalizedName());
		
		//Sands, Glass
		GameRegistry.registerBlock(ModBlocks.ash_digamma, ModBlocks.ash_digamma.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.sand_boron, ModBlocks.sand_boron.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.sand_lead, ModBlocks.sand_lead.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.sand_uranium, ModBlocks.sand_uranium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.sand_polonium, ModBlocks.sand_polonium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.sand_quartz, ModBlocks.sand_quartz.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.sand_gold, ModBlocks.sand_gold.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.sand_gold198, ModBlocks.sand_gold198.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.glass_boron, ModBlocks.glass_boron.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.glass_lead, ModBlocks.glass_lead.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.glass_uranium, ModBlocks.glass_uranium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.glass_trinitite, ModBlocks.glass_trinitite.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.glass_polonium, ModBlocks.glass_polonium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.glass_ash, ModBlocks.glass_ash.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.glass_quartz, ModBlocks.glass_quartz.getUnlocalizedName());
		
		//Silo Hatch
		GameRegistry.registerBlock(ModBlocks.seal_frame, ModBlocks.seal_frame.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.seal_controller, ModBlocks.seal_controller.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.seal_hatch, ModBlocks.seal_hatch.getUnlocalizedName());
		
		//Vault Door
		GameRegistry.registerBlock(ModBlocks.vault_door, ModBlocks.vault_door.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.blast_door, ModBlocks.blast_door.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.fire_door, ModBlocks.fire_door.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.transition_seal, ModBlocks.transition_seal.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.sliding_blast_door, ModBlocks.sliding_blast_door.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.sliding_blast_door_2, ModBlocks.sliding_blast_door_2.getUnlocalizedName());
//		GameRegistry.registerBlock(ModBlocks.sliding_blast_door_keypad, ModBlocks.sliding_blast_door_keypad.getUnlocalizedName());

		//Doors
		GameRegistry.registerBlock(ModBlocks.door_metal, ModBlocks.door_metal.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.door_office, ModBlocks.door_office.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.door_bunker, ModBlocks.door_bunker.getUnlocalizedName());
		
		//Crates
		ModBlocks.register(ModBlocks.crate_iron);
		ModBlocks.register(ModBlocks.crate_steel);
		ModBlocks.register(ModBlocks.crate_desh);
		ModBlocks.register(ModBlocks.crate_tungsten);
		ModBlocks.register(ModBlocks.crate_template);
		ModBlocks.register(ModBlocks.safe);
		ModBlocks.register(ModBlocks.mass_storage);
		
		//Junk
		GameRegistry.registerBlock(ModBlocks.boxcar, ModBlocks.boxcar.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.boat, ModBlocks.boat.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.bomber, ModBlocks.bomber.getUnlocalizedName());
		
		//Machines
		//GameRegistry.registerBlock(observer_off, observer_off.getUnlocalizedName());
		//GameRegistry.registerBlock(observer_on, observer_on.getUnlocalizedName());
		
		GameRegistry.registerBlock(ModBlocks.machine_autocrafter, ItemBlockBase.class, ModBlocks.machine_autocrafter.getUnlocalizedName());
		
		GameRegistry.registerBlock(ModBlocks.anvil_iron, ItemBlockBase.class, ModBlocks.anvil_iron.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.anvil_lead, ItemBlockBase.class, ModBlocks.anvil_lead.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.anvil_steel, ItemBlockBase.class, ModBlocks.anvil_steel.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.anvil_meteorite, ItemBlockBase.class, ModBlocks.anvil_meteorite.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.anvil_starmetal, ItemBlockBase.class, ModBlocks.anvil_starmetal.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.anvil_ferrouranium, ItemBlockBase.class, ModBlocks.anvil_ferrouranium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.anvil_bismuth, ItemBlockBase.class, ModBlocks.anvil_bismuth.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.anvil_schrabidate, ItemBlockBase.class, ModBlocks.anvil_schrabidate.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.anvil_dnt, ItemBlockBase.class, ModBlocks.anvil_dnt.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.anvil_osmiridium, ItemBlockBase.class, ModBlocks.anvil_osmiridium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.anvil_murky, ItemBlockBase.class, ModBlocks.anvil_murky.getUnlocalizedName());
		
		// START
		GameRegistry.registerBlock(ModBlocks.press_preheater, ModBlocks.press_preheater.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_press, ModBlocks.machine_press.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_epress, ModBlocks.machine_epress.getUnlocalizedName());
		ModBlocks.register(ModBlocks.machine_conveyor_press);
		ModBlocks.register(ModBlocks.pump_steam);
		ModBlocks.register(ModBlocks.pump_electric);
		ModBlocks.register(ModBlocks.heater_firebox);
		ModBlocks.register(ModBlocks.heater_oven);
		ModBlocks.register(ModBlocks.machine_ashpit);
		ModBlocks.register(ModBlocks.heater_oilburner);
		ModBlocks.register(ModBlocks.heater_electric);
		ModBlocks.register(ModBlocks.heater_heatex);
		ModBlocks.register(ModBlocks.furnace_iron);
		ModBlocks.register(ModBlocks.furnace_steel);
		ModBlocks.register(ModBlocks.furnace_combination);
		ModBlocks.register(ModBlocks.machine_stirling);
		ModBlocks.register(ModBlocks.machine_stirling_steel);
		ModBlocks.register(ModBlocks.machine_stirling_creative);
		ModBlocks.register(ModBlocks.machine_sawmill);
		ModBlocks.register(ModBlocks.machine_crucible);
		ModBlocks.register(ModBlocks.machine_boiler);
		ModBlocks.register(ModBlocks.machine_industrial_boiler);
		ModBlocks.register(ModBlocks.foundry_mold);
		ModBlocks.register(ModBlocks.foundry_basin);
		ModBlocks.register(ModBlocks.foundry_channel);
		ModBlocks.register(ModBlocks.foundry_tank);
		ModBlocks.register(ModBlocks.foundry_outlet);
		ModBlocks.register(ModBlocks.foundry_slagtap);
		ModBlocks.register(ModBlocks.slag);
		ModBlocks.register(ModBlocks.machine_difurnace_off);
		ModBlocks.register(ModBlocks.machine_difurnace_on);
		ModBlocks.register(ModBlocks.machine_difurnace_extension);
		GameRegistry.registerBlock(ModBlocks.machine_difurnace_rtg_off, ModBlocks.machine_difurnace_rtg_off.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_difurnace_rtg_on, ModBlocks.machine_difurnace_rtg_on.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_centrifuge, ModBlocks.machine_centrifuge.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_gascent, ModBlocks.machine_gascent.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_fel, ModBlocks.machine_fel.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_silex, ModBlocks.machine_silex.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_crystallizer, ModBlocks.machine_crystallizer.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_uf6_tank, ModBlocks.machine_uf6_tank.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_puf6_tank, ModBlocks.machine_puf6_tank.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_reactor_breeding, ModBlocks.machine_reactor_breeding.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_nuke_furnace_off, ModBlocks.machine_nuke_furnace_off.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_nuke_furnace_on, ModBlocks.machine_nuke_furnace_on.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_rtg_furnace_off, ModBlocks.machine_rtg_furnace_off.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_rtg_furnace_on, ModBlocks.machine_rtg_furnace_on.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_coal_off, ModBlocks.machine_coal_off.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_coal_on, ModBlocks.machine_coal_on.getUnlocalizedName());
		ModBlocks.register(ModBlocks.machine_wood_burner);
		ModBlocks.register(ModBlocks.machine_diesel);
		ModBlocks.register(ModBlocks.machine_selenium);
		ModBlocks.register(ModBlocks.machine_combustion_engine);
		GameRegistry.registerBlock(ModBlocks.machine_generator, ModBlocks.machine_generator.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_controller, ModBlocks.machine_controller.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.reactor_research, ModBlocks.reactor_research.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.reactor_zirnox, ModBlocks.reactor_zirnox.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.zirnox_destroyed, ModBlocks.zirnox_destroyed.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_industrial_generator, ModBlocks.machine_industrial_generator.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_radgen, ModBlocks.machine_radgen.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_cyclotron, ModBlocks.machine_cyclotron.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_rtg_grey, ModBlocks.machine_rtg_grey.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_geo, ModBlocks.machine_geo.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_amgen, ModBlocks.machine_amgen.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_minirtg, ModBlocks.machine_minirtg.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_powerrtg, ModBlocks.machine_powerrtg.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_radiolysis, ModBlocks.machine_radiolysis.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_hephaestus, ModBlocks.machine_hephaestus.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_spp_bottom, ModBlocks.machine_spp_bottom.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_spp_top, ModBlocks.machine_spp_top.getUnlocalizedName());
		
		GameRegistry.registerBlock(ModBlocks.hadron_plating, ModBlocks.hadron_plating.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.hadron_plating_blue, ModBlocks.hadron_plating_blue.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.hadron_plating_black, ModBlocks.hadron_plating_black.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.hadron_plating_yellow, ModBlocks.hadron_plating_yellow.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.hadron_plating_striped, ModBlocks.hadron_plating_striped.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.hadron_plating_glass, ModBlocks.hadron_plating_glass.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.hadron_plating_voltz, ModBlocks.hadron_plating_voltz.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.hadron_coil_alloy, ItemBlockBase.class, ModBlocks.hadron_coil_alloy.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.hadron_coil_gold, ItemBlockBase.class, ModBlocks.hadron_coil_gold.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.hadron_coil_neodymium, ItemBlockBase.class, ModBlocks.hadron_coil_neodymium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.hadron_coil_magtung, ItemBlockBase.class, ModBlocks.hadron_coil_magtung.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.hadron_coil_schrabidium, ItemBlockBase.class, ModBlocks.hadron_coil_schrabidium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.hadron_coil_schrabidate, ItemBlockBase.class, ModBlocks.hadron_coil_schrabidate.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.hadron_coil_starmetal, ItemBlockBase.class, ModBlocks.hadron_coil_starmetal.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.hadron_coil_chlorophyte, ItemBlockBase.class, ModBlocks.hadron_coil_chlorophyte.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.hadron_coil_mese, ItemBlockBase.class, ModBlocks.hadron_coil_mese.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.hadron_power, ModBlocks.hadron_power.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.hadron_power_10m, ModBlocks.hadron_power_10m.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.hadron_power_100m, ModBlocks.hadron_power_100m.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.hadron_power_1g, ModBlocks.hadron_power_1g.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.hadron_power_10g, ModBlocks.hadron_power_10g.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.hadron_diode, ModBlocks.hadron_diode.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.hadron_analysis, ModBlocks.hadron_analysis.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.hadron_analysis_glass, ModBlocks.hadron_analysis_glass.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.hadron_access, ModBlocks.hadron_access.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.hadron_core, ModBlocks.hadron_core.getUnlocalizedName());
		ModBlocks.register(ModBlocks.hadron_cooler);
		
		GameRegistry.registerBlock(ModBlocks.rbmk_rod, ModBlocks.rbmk_rod.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.rbmk_rod_mod, ModBlocks.rbmk_rod_mod.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.rbmk_rod_reasim, ModBlocks.rbmk_rod_reasim.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.rbmk_rod_reasim_mod, ModBlocks.rbmk_rod_reasim_mod.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.rbmk_control, ModBlocks.rbmk_control.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.rbmk_control_mod, ModBlocks.rbmk_control_mod.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.rbmk_control_auto, ModBlocks.rbmk_control_auto.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.rbmk_blank, ModBlocks.rbmk_blank.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.rbmk_boiler, ModBlocks.rbmk_boiler.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.rbmk_reflector, ModBlocks.rbmk_reflector.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.rbmk_absorber, ModBlocks.rbmk_absorber.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.rbmk_moderator, ModBlocks.rbmk_moderator.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.rbmk_outgasser, ModBlocks.rbmk_outgasser.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.rbmk_storage, ModBlocks.rbmk_storage.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.rbmk_cooler, ModBlocks.rbmk_cooler.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.rbmk_heater, ModBlocks.rbmk_heater.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.rbmk_console, ModBlocks.rbmk_console.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.rbmk_crane_console, ModBlocks.rbmk_crane_console.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.rbmk_loader, ModBlocks.rbmk_loader.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.rbmk_steam_inlet, ModBlocks.rbmk_steam_inlet.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.rbmk_steam_outlet, ModBlocks.rbmk_steam_outlet.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.rbmk_heatex, ModBlocks.rbmk_heatex.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.pribris, ModBlocks.pribris.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.pribris_burning, ModBlocks.pribris_burning.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.pribris_radiating, ModBlocks.pribris_radiating.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.pribris_digamma, ModBlocks.pribris_digamma.getUnlocalizedName());
		
		GameRegistry.registerBlock(ModBlocks.red_cable, ModBlocks.red_cable.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.red_cable_classic, ModBlocks.red_cable_classic.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.red_cable_paintable, ModBlocks.red_cable_paintable.getUnlocalizedName());
		ModBlocks.register(ModBlocks.red_cable_gauge);
		GameRegistry.registerBlock(ModBlocks.red_wire_coated, ModBlocks.red_wire_coated.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.red_connector, ItemBlockBase.class, ModBlocks.red_connector.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.red_pylon, ItemBlockBase.class, ModBlocks.red_pylon.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.red_pylon_large, ItemBlockBase.class, ModBlocks.red_pylon_large.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.substation, ItemBlockBase.class, ModBlocks.substation.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.cable_switch, ModBlocks.cable_switch.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.cable_detector, ModBlocks.cable_detector.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.cable_diode, ItemBlockBase.class, ModBlocks.cable_diode.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_detector, ModBlocks.machine_detector.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.fluid_duct, ModBlocks.fluid_duct.getUnlocalizedName());
		ModBlocks.register(ModBlocks.fluid_duct_neo);
		ModBlocks.register(ModBlocks.fluid_duct_box);
		ModBlocks.register(ModBlocks.fluid_duct_exhaust);
		ModBlocks.register(ModBlocks.fluid_duct_paintable);
		ModBlocks.register(ModBlocks.fluid_duct_gauge);
		GameRegistry.registerBlock(ModBlocks.fluid_duct_solid, ModBlocks.fluid_duct_solid.getUnlocalizedName());
		ModBlocks.register(ModBlocks.fluid_valve);
		ModBlocks.register(ModBlocks.fluid_switch);
		ModBlocks.register(ModBlocks.radio_torch_sender);
		ModBlocks.register(ModBlocks.radio_torch_receiver);
		ModBlocks.register(ModBlocks.radio_torch_counter);
		ModBlocks.register(ModBlocks.radio_telex);

		ModBlocks.register(ModBlocks.crane_extractor);
		ModBlocks.register(ModBlocks.crane_inserter);
		ModBlocks.register(ModBlocks.crane_grabber);
		ModBlocks.register(ModBlocks.crane_router);
		ModBlocks.register(ModBlocks.crane_boxer);
		ModBlocks.register(ModBlocks.crane_unboxer);
		ModBlocks.register(ModBlocks.conveyor);
		ModBlocks.register(ModBlocks.conveyor_express);
		ModBlocks.register(ModBlocks.conveyor_double);
		ModBlocks.register(ModBlocks.conveyor_triple);
		ModBlocks.register(ModBlocks.conveyor_chute);
		ModBlocks.register(ModBlocks.conveyor_lift);
		ModBlocks.register(ModBlocks.crane_splitter);
		ModBlocks.register(ModBlocks.drone_waypoint);
		ModBlocks.register(ModBlocks.drone_crate);
		ModBlocks.register(ModBlocks.drone_waypoint_request);
		ModBlocks.register(ModBlocks.drone_dock);
		ModBlocks.register(ModBlocks.drone_crate_provider);
		ModBlocks.register(ModBlocks.drone_crate_requester);
		ModBlocks.register(ModBlocks.fan);
		ModBlocks.register(ModBlocks.piston_inserter);
		
		GameRegistry.registerBlock(ModBlocks.chain, ModBlocks.chain.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ladder_sturdy, ModBlocks.ladder_sturdy.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ladder_iron, ModBlocks.ladder_iron.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ladder_gold, ModBlocks.ladder_gold.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ladder_titanium, ModBlocks.ladder_titanium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ladder_copper, ModBlocks.ladder_copper.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ladder_tungsten, ModBlocks.ladder_tungsten.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ladder_aluminium, ModBlocks.ladder_aluminium.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ladder_steel, ModBlocks.ladder_steel.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ladder_lead, ModBlocks.ladder_lead.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ladder_cobalt, ModBlocks.ladder_cobalt.getUnlocalizedName());
		
		ModBlocks.register(ModBlocks.barrel_plastic);
		ModBlocks.register(ModBlocks.barrel_corroded);
		ModBlocks.register(ModBlocks.barrel_iron);
		ModBlocks.register(ModBlocks.barrel_steel);
		ModBlocks.register(ModBlocks.barrel_tcalloy);
		ModBlocks.register(ModBlocks.barrel_antimatter);
		ModBlocks.register(ModBlocks.machine_battery_potato);
		ModBlocks.register(ModBlocks.machine_battery);
		ModBlocks.register(ModBlocks.machine_lithium_battery);
		ModBlocks.register(ModBlocks.machine_schrabidium_battery);
		ModBlocks.register(ModBlocks.machine_dineutronium_battery);
		ModBlocks.register(ModBlocks.machine_fensu);
		ModBlocks.register(ModBlocks.capacitor_bus);
		ModBlocks.register(ModBlocks.capacitor_copper);
		ModBlocks.register(ModBlocks.capacitor_gold);
		ModBlocks.register(ModBlocks.capacitor_niobium);
		ModBlocks.register(ModBlocks.capacitor_tantalium);
		ModBlocks.register(ModBlocks.capacitor_schrabidate);
		GameRegistry.registerBlock(ModBlocks.machine_transformer, ModBlocks.machine_transformer.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_transformer_20, ModBlocks.machine_transformer_20.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_transformer_dnt, ModBlocks.machine_transformer_dnt.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_transformer_dnt_20, ModBlocks.machine_transformer_dnt_20.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_converter_he_rf, ModBlocks.machine_converter_he_rf.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_converter_rf_he, ModBlocks.machine_converter_rf_he.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_electric_furnace_off, ModBlocks.machine_electric_furnace_off.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_electric_furnace_on, ModBlocks.machine_electric_furnace_on.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_arc_furnace_off, ModBlocks.machine_arc_furnace_off.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_arc_furnace_on, ModBlocks.machine_arc_furnace_on.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_microwave, ModBlocks.machine_microwave.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_assembler, ModBlocks.machine_assembler.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_assemfac, ModBlocks.machine_assemfac.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_chemplant, ModBlocks.machine_chemplant.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_chemfac, ModBlocks.machine_chemfac.getUnlocalizedName());
		ModBlocks.register(ModBlocks.machine_arc_welder);
		ModBlocks.register(ModBlocks.machine_mixer);
		ModBlocks.register(ModBlocks.machine_fluidtank);
		ModBlocks.register(ModBlocks.machine_bat9000);
		ModBlocks.register(ModBlocks.machine_orbus);
		GameRegistry.registerBlock(ModBlocks.machine_boiler_off, ModBlocks.machine_boiler_off.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_boiler_on, ModBlocks.machine_boiler_on.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_boiler_electric_on, ModBlocks.machine_boiler_electric_on.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_boiler_electric_off, ModBlocks.machine_boiler_electric_off.getUnlocalizedName());
		ModBlocks.register(ModBlocks.machine_steam_engine);
		ModBlocks.register(ModBlocks.machine_turbine);
		ModBlocks.register(ModBlocks.machine_large_turbine);
		ModBlocks.register(ModBlocks.machine_chungus);
		GameRegistry.registerBlock(ModBlocks.machine_condenser, ModBlocks.machine_condenser.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_tower_small, ModBlocks.machine_tower_small.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_tower_large, ModBlocks.machine_tower_large.getUnlocalizedName());
		ModBlocks.register(ModBlocks.machine_condenser_powered);
		GameRegistry.registerBlock(ModBlocks.machine_deuterium_extractor, ModBlocks.machine_deuterium_extractor.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_deuterium_tower, ModBlocks.machine_deuterium_tower.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_liquefactor, ItemBlockBase.class, ModBlocks.machine_liquefactor.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_solidifier, ItemBlockBase.class, ModBlocks.machine_solidifier.getUnlocalizedName());
		ModBlocks.register(ModBlocks.machine_compressor);
		GameRegistry.registerBlock(ModBlocks.machine_electrolyser, ModBlocks.machine_electrolyser.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_deaerator, ModBlocks.machine_deaerator.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_waste_drum, ModBlocks.machine_waste_drum.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_storage_drum, ModBlocks.machine_storage_drum.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_shredder, ModBlocks.machine_shredder.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_shredder_large, ModBlocks.machine_shredder_large.getUnlocalizedName());
		ModBlocks.register(ModBlocks.machine_well);
		ModBlocks.register(ModBlocks.machine_pumpjack);
		ModBlocks.register(ModBlocks.machine_fracking_tower);
		ModBlocks.register(ModBlocks.machine_flare);
		ModBlocks.register(ModBlocks.chimney_brick);
		ModBlocks.register(ModBlocks.chimney_industrial);
		ModBlocks.register(ModBlocks.machine_refinery);
		ModBlocks.register(ModBlocks.machine_vacuum_distill);
		ModBlocks.register(ModBlocks.machine_fraction_tower);
		ModBlocks.register(ModBlocks.fraction_spacer);
		ModBlocks.register(ModBlocks.machine_catalytic_cracker);
		ModBlocks.register(ModBlocks.machine_catalytic_reformer);
		ModBlocks.register(ModBlocks.machine_coker);
		ModBlocks.register(ModBlocks.machine_drill);
		ModBlocks.register(ModBlocks.machine_autosaw);
		ModBlocks.register(ModBlocks.machine_excavator);
		ModBlocks.register(ModBlocks.machine_mining_laser);
		ModBlocks.register(ModBlocks.barricade);
		ModBlocks.register(ModBlocks.machine_turbofan);
		ModBlocks.register(ModBlocks.machine_turbinegas);
		GameRegistry.registerBlock(ModBlocks.machine_schrabidium_transmutator, ModBlocks.machine_schrabidium_transmutator.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_combine_factory, ModBlocks.machine_combine_factory.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_teleporter, ModBlocks.machine_teleporter.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.teleanchor, ModBlocks.teleanchor.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.field_disturber, ModBlocks.field_disturber.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_satlinker, ModBlocks.machine_satlinker.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_keyforge, ModBlocks.machine_keyforge.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_armor_table, ModBlocks.machine_armor_table.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_forcefield, ModBlocks.machine_forcefield.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.radiorec, ModBlocks.radiorec.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.radiobox, ModBlocks.radiobox.getUnlocalizedName());
		
		//Multiblock Helpers
		GameRegistry.registerBlock(ModBlocks.marker_structure, ModBlocks.marker_structure.getUnlocalizedName());
		
		//The muffler
		GameRegistry.registerBlock(ModBlocks.muffler, ModBlocks.muffler.getUnlocalizedName());
		
		//Multiblock Parts
		GameRegistry.registerBlock(ModBlocks.struct_launcher, ModBlocks.struct_launcher.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.struct_scaffold, ModBlocks.struct_scaffold.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.struct_launcher_core, ModBlocks.struct_launcher_core.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.struct_launcher_core_large, ModBlocks.struct_launcher_core_large.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.struct_soyuz_core, ModBlocks.struct_soyuz_core.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.struct_iter_core, ModBlocks.struct_iter_core.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.struct_plasma_core, ModBlocks.struct_plasma_core.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.struct_watz_core, ModBlocks.struct_watz_core.getUnlocalizedName());
		
		//Absorbers
		GameRegistry.registerBlock(ModBlocks.absorber, ModBlocks.absorber.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.absorber_red, ModBlocks.absorber_red.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.absorber_green, ModBlocks.absorber_green.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.absorber_pink, ModBlocks.absorber_pink.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.decon, ModBlocks.decon.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.transission_hatch, ModBlocks.transission_hatch.getUnlocalizedName());
		
		//Solar Tower Blocks
		GameRegistry.registerBlock(ModBlocks.machine_solar_boiler, ModBlocks.machine_solar_boiler.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.solar_mirror, ModBlocks.solar_mirror.getUnlocalizedName());
		
		//Literal fucking garbage
		GameRegistry.registerBlock(ModBlocks.factory_titanium_hull, ModBlocks.factory_titanium_hull.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.factory_advanced_hull, ModBlocks.factory_advanced_hull.getUnlocalizedName());
		
		//CM stuff
		ModBlocks.register(ModBlocks.custom_machine, ItemCustomMachine.class);
		ModBlocks.register(ModBlocks.cm_block);
		ModBlocks.register(ModBlocks.cm_sheet);
		ModBlocks.register(ModBlocks.cm_engine);
		ModBlocks.register(ModBlocks.cm_tank);
		ModBlocks.register(ModBlocks.cm_circuit);
		ModBlocks.register(ModBlocks.cm_port);
		ModBlocks.register(ModBlocks.cm_anchor);
		
		//PWR
		ModBlocks.register(ModBlocks.pwr_fuel);
		ModBlocks.register(ModBlocks.pwr_control);
		ModBlocks.register(ModBlocks.pwr_channel);
		ModBlocks.register(ModBlocks.pwr_heatex);
		ModBlocks.register(ModBlocks.pwr_neutron_source);
		ModBlocks.register(ModBlocks.pwr_reflector);
		ModBlocks.register(ModBlocks.pwr_casing);
		ModBlocks.register(ModBlocks.pwr_port);
		ModBlocks.register(ModBlocks.pwr_controller);
		ModBlocks.register(ModBlocks.pwr_block);
		
		//Multiblock Generators
		GameRegistry.registerBlock(ModBlocks.reactor_element, ModBlocks.reactor_element.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.reactor_control, ModBlocks.reactor_control.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.reactor_hatch, ModBlocks.reactor_hatch.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.reactor_ejector, ModBlocks.reactor_ejector.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.reactor_inserter, ModBlocks.reactor_inserter.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.reactor_conductor, ModBlocks.reactor_conductor.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.reactor_computer, ModBlocks.reactor_computer.getUnlocalizedName());

		ModBlocks.register(ModBlocks.fusion_conductor);
		GameRegistry.registerBlock(ModBlocks.fusion_center, ModBlocks.fusion_center.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.fusion_motor, ModBlocks.fusion_motor.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.fusion_heater, ModBlocks.fusion_heater.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.fusion_hatch, ModBlocks.fusion_hatch.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.plasma, ItemBlockLore.class, ModBlocks.plasma.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.iter, ModBlocks.iter.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.plasma_heater, ModBlocks.plasma_heater.getUnlocalizedName());

		GameRegistry.registerBlock(ModBlocks.watz_element, ModBlocks.watz_element.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.watz_control, ModBlocks.watz_control.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.watz_cooler, ModBlocks.watz_cooler.getUnlocalizedName());
		ModBlocks.register(ModBlocks.watz_end);
		GameRegistry.registerBlock(ModBlocks.watz_hatch, ModBlocks.watz_hatch.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.watz_conductor, ModBlocks.watz_conductor.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.watz_core, ModBlocks.watz_core.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.watz, ModBlocks.watz.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.watz_pump, ModBlocks.watz_pump.getUnlocalizedName());

		GameRegistry.registerBlock(ModBlocks.fwatz_conductor, ModBlocks.fwatz_conductor.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.fwatz_scaffold, ModBlocks.fwatz_scaffold.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.fwatz_hatch, ModBlocks.fwatz_hatch.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.fwatz_computer, ModBlocks.fwatz_computer.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.fwatz_core, ModBlocks.fwatz_core.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.fwatz_cooler, ModBlocks.fwatz_cooler.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.fwatz_tank, ModBlocks.fwatz_tank.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.fwatz_plasma, ModBlocks.fwatz_plasma.getUnlocalizedName());
		
		//E
		GameRegistry.registerBlock(ModBlocks.balefire, ModBlocks.balefire.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.fire_digamma, ModBlocks.fire_digamma.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.digamma_matter, ModBlocks.digamma_matter.getUnlocalizedName());
		ModBlocks.register(ModBlocks.volcano_core);
		
		//AMS
		GameRegistry.registerBlock(ModBlocks.ams_base, ModBlocks.ams_base.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ams_emitter, ModBlocks.ams_emitter.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ams_limiter, ModBlocks.ams_limiter.getUnlocalizedName());
		
		//Dark Fusion Core
		GameRegistry.registerBlock(ModBlocks.dfc_emitter, ModBlocks.dfc_emitter.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.dfc_injector, ModBlocks.dfc_injector.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.dfc_receiver, ModBlocks.dfc_receiver.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.dfc_stabilizer, ModBlocks.dfc_stabilizer.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.dfc_core, ModBlocks.dfc_core.getUnlocalizedName());
		
		//Missile Blocks
		GameRegistry.registerBlock(ModBlocks.machine_missile_assembly, ModBlocks.machine_missile_assembly.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.launch_pad, ModBlocks.launch_pad.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.compact_launcher, ModBlocks.compact_launcher.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.launch_table, ModBlocks.launch_table.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.soyuz_launcher, ModBlocks.soyuz_launcher.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.sat_dock, ModBlocks.sat_dock.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.soyuz_capsule, ModBlocks.soyuz_capsule.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.machine_radar, ModBlocks.machine_radar.getUnlocalizedName());
		
		//Guide
		GameRegistry.registerBlock(ModBlocks.book_guide, ModBlocks.book_guide.getUnlocalizedName());
		
		//Sat Blocks
		GameRegistry.registerBlock(ModBlocks.sat_mapper, ModBlocks.sat_mapper.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.sat_scanner, ModBlocks.sat_scanner.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.sat_radar, ModBlocks.sat_radar.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.sat_laser, ModBlocks.sat_laser.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.sat_foeq, ModBlocks.sat_foeq.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.sat_resonator, ModBlocks.sat_resonator.getUnlocalizedName());
		
		//Rails
		GameRegistry.registerBlock(ModBlocks.rail_wood, ItemBlockBase.class, ModBlocks.rail_wood.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.rail_narrow, ItemBlockBase.class, ModBlocks.rail_narrow.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.rail_highspeed, ItemBlockBase.class, ModBlocks.rail_highspeed.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.rail_booster, ItemBlockBase.class, ModBlocks.rail_booster.getUnlocalizedName());
		ModBlocks.register(ModBlocks.rail_narrow_straight);
		ModBlocks.register(ModBlocks.rail_narrow_curve);
		ModBlocks.register(ModBlocks.rail_large_straight);
		ModBlocks.register(ModBlocks.rail_large_curve);
		ModBlocks.register(ModBlocks.rail_large_ramp);
		ModBlocks.register(ModBlocks.rail_large_buffer);
		
		//Crate
		GameRegistry.registerBlock(ModBlocks.crate, ModBlocks.crate.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.crate_weapon, ModBlocks.crate_weapon.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.crate_lead, ModBlocks.crate_lead.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.crate_metal, ModBlocks.crate_metal.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.crate_red, ModBlocks.crate_red.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.crate_can, ModBlocks.crate_can.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.crate_ammo, ModBlocks.crate_ammo.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.crate_jungle, ModBlocks.crate_jungle.getUnlocalizedName());
		
		//ElB
		GameRegistry.registerBlock(ModBlocks.statue_elb, ModBlocks.statue_elb.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.statue_elb_g, ModBlocks.statue_elb_g.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.statue_elb_w, ModBlocks.statue_elb_w.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.statue_elb_f, ModBlocks.statue_elb_f.getUnlocalizedName());
		
		//Fluids
		GameRegistry.registerBlock(ModBlocks.mud_block, ModBlocks.mud_block.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.acid_block, ModBlocks.acid_block.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.toxic_block, ModBlocks.toxic_block.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.schrabidic_block, ModBlocks.schrabidic_block.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.corium_block, ModBlocks.corium_block.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.volcanic_lava_block, ModBlocks.volcanic_lava_block.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.sulfuric_acid_block, ModBlocks.sulfuric_acid_block.getUnlocalizedName());
		//GameRegistry.registerBlock(concrete_liquid, concrete_liquid.getUnlocalizedName());
		
		//Multiblock Dummy Blocks
		GameRegistry.registerBlock(ModBlocks.dummy_block_drill, ModBlocks.dummy_block_drill.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.dummy_port_drill, ModBlocks.dummy_port_drill.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.dummy_block_ams_limiter, ModBlocks.dummy_block_ams_limiter.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.dummy_port_ams_limiter, ModBlocks.dummy_port_ams_limiter.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.dummy_block_ams_emitter, ModBlocks.dummy_block_ams_emitter.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.dummy_port_ams_emitter, ModBlocks.dummy_port_ams_emitter.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.dummy_block_ams_base, ModBlocks.dummy_block_ams_base.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.dummy_port_ams_base, ModBlocks.dummy_port_ams_base.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.dummy_block_vault, ModBlocks.dummy_block_vault.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.dummy_block_blast, ModBlocks.dummy_block_blast.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.dummy_block_uf6, ModBlocks.dummy_block_uf6.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.dummy_block_puf6, ModBlocks.dummy_block_puf6.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.dummy_plate_compact_launcher, ModBlocks.dummy_plate_compact_launcher.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.dummy_port_compact_launcher, ModBlocks.dummy_port_compact_launcher.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.dummy_plate_launch_table, ModBlocks.dummy_plate_launch_table.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.dummy_port_launch_table, ModBlocks.dummy_port_launch_table.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.dummy_plate_cargo, ModBlocks.dummy_plate_cargo.getUnlocalizedName());
		
		//Other Technical Blocks
		GameRegistry.registerBlock(ModBlocks.oil_pipe, ModBlocks.oil_pipe.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.drill_pipe, ModBlocks.drill_pipe.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.vent_chlorine, ModBlocks.vent_chlorine.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.vent_cloud, ModBlocks.vent_cloud.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.vent_pink_cloud, ModBlocks.vent_pink_cloud.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.vent_chlorine_seal, ModBlocks.vent_chlorine_seal.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.chlorine_gas, ModBlocks.chlorine_gas.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.gas_radon, ModBlocks.gas_radon.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.gas_radon_dense, ModBlocks.gas_radon_dense.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.gas_radon_tomb, ModBlocks.gas_radon_tomb.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.gas_meltdown, ModBlocks.gas_meltdown.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.gas_monoxide, ModBlocks.gas_monoxide.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.gas_asbestos, ModBlocks.gas_asbestos.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.gas_coal, ModBlocks.gas_coal.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.gas_flammable, ModBlocks.gas_flammable.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.gas_explosive, ModBlocks.gas_explosive.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.vacuum, ModBlocks.vacuum.getUnlocalizedName());
		
		//???
		GameRegistry.registerBlock(ModBlocks.crystal_virus, ModBlocks.crystal_virus.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.crystal_hardened, ModBlocks.crystal_hardened.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.crystal_pulsar, ModBlocks.crystal_pulsar.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.taint, ItemTaintBlock.class, ModBlocks.taint.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.residue, ModBlocks.residue.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.cheater_virus, ModBlocks.cheater_virus.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.cheater_virus_seed, ModBlocks.cheater_virus_seed.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ntm_dirt, ModBlocks.ntm_dirt.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.pink_log, ModBlocks.pink_log.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.pink_planks, ModBlocks.pink_planks.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.pink_slab, ModBlocks.pink_slab.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.pink_double_slab, ModBlocks.pink_double_slab.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.pink_stairs, ModBlocks.pink_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(ModBlocks.ff, ModBlocks.ff.getUnlocalizedName());
	}
	
	private static void register(Block b) {
		GameRegistry.registerBlock(b, ItemBlockBase.class, b.getUnlocalizedName());
	}
	
	private static void register(Block b, Class<? extends ItemBlock> clazz) {
		GameRegistry.registerBlock(b, clazz, b.getUnlocalizedName());
	}
	
	public static void addRemap(String unloc, Block block, int meta) {
		Block remap = new BlockRemap(block, meta).setBlockName(unloc);
		ModBlocks.register(remap, ItemBlockRemap.class);
	}
}
