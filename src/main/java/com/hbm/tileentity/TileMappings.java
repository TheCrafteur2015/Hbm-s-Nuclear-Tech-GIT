package com.hbm.tileentity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.bomb.BlockVolcano.TileEntityVolcanoCore;
import com.hbm.blocks.generic.BlockBedrockOreTE.TileEntityBedrockOre;
import com.hbm.blocks.generic.BlockBobble.TileEntityBobble;
import com.hbm.blocks.generic.BlockDynamicSlag.TileEntitySlag;
import com.hbm.blocks.generic.BlockEmitter.TileEntityEmitter;
import com.hbm.blocks.generic.BlockGlyphidSpawner.TileEntityGlpyhidSpawner;
import com.hbm.blocks.generic.BlockLoot.TileEntityLoot;
import com.hbm.blocks.generic.BlockMotherOfAllOres.TileEntityRandomOre;
import com.hbm.blocks.generic.BlockSnowglobe.TileEntitySnowglobe;
import com.hbm.blocks.generic.PartEmitter.TileEntityPartEmitter;
import com.hbm.blocks.machine.BlockPWR.TileEntityBlockPWR;
import com.hbm.blocks.machine.MachineCapacitor.TileEntityCapacitor;
import com.hbm.blocks.machine.MachineFan.TileEntityFan;
import com.hbm.blocks.machine.PistonInserter.TileEntityPistonInserter;
import com.hbm.blocks.machine.WatzPump.TileEntityWatzPump;
import com.hbm.blocks.network.BlockCableGauge.TileEntityCableGauge;
import com.hbm.blocks.network.BlockCablePaintable.TileEntityCablePaintable;
import com.hbm.blocks.network.CableDiode.TileEntityDiode;
import com.hbm.blocks.network.FluidDuctGauge.TileEntityPipeGauge;
import com.hbm.blocks.network.FluidDuctPaintable.TileEntityPipePaintable;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.tileentity.bomb.TileEntityBombMulti;
import com.hbm.tileentity.bomb.TileEntityCharge;
import com.hbm.tileentity.bomb.TileEntityCompactLauncher;
import com.hbm.tileentity.bomb.TileEntityCrashedBomb;
import com.hbm.tileentity.bomb.TileEntityFireworks;
import com.hbm.tileentity.bomb.TileEntityLandmine;
import com.hbm.tileentity.bomb.TileEntityLaunchPad;
import com.hbm.tileentity.bomb.TileEntityLaunchTable;
import com.hbm.tileentity.bomb.TileEntityNukeBalefire;
import com.hbm.tileentity.bomb.TileEntityNukeBoy;
import com.hbm.tileentity.bomb.TileEntityNukeCustom;
import com.hbm.tileentity.bomb.TileEntityNukeFleija;
import com.hbm.tileentity.bomb.TileEntityNukeGadget;
import com.hbm.tileentity.bomb.TileEntityNukeMan;
import com.hbm.tileentity.bomb.TileEntityNukeMike;
import com.hbm.tileentity.bomb.TileEntityNukeN2;
import com.hbm.tileentity.bomb.TileEntityNukeN45;
import com.hbm.tileentity.bomb.TileEntityNukePrototype;
import com.hbm.tileentity.bomb.TileEntityNukeSolinium;
import com.hbm.tileentity.bomb.TileEntityNukeTsar;
import com.hbm.tileentity.bomb.TileEntityRedBarrel;
import com.hbm.tileentity.bomb.TileEntitySellafield;
import com.hbm.tileentity.bomb.TileEntityTestBombAdvanced;
import com.hbm.tileentity.bomb.TileEntityTestNuke;
import com.hbm.tileentity.conductor.TileEntityFluidDuct;
import com.hbm.tileentity.conductor.TileEntityFluidDuctSimple;
import com.hbm.tileentity.conductor.TileEntityRFDuct;
import com.hbm.tileentity.deco.TileEntityBomber;
import com.hbm.tileentity.deco.TileEntityDecoBlock;
import com.hbm.tileentity.deco.TileEntityDecoBlockAltF;
import com.hbm.tileentity.deco.TileEntityDecoBlockAltG;
import com.hbm.tileentity.deco.TileEntityDecoBlockAltW;
import com.hbm.tileentity.deco.TileEntityDecoPoleSatelliteReceiver;
import com.hbm.tileentity.deco.TileEntityDecoPoleTop;
import com.hbm.tileentity.deco.TileEntityDecoSteelPoles;
import com.hbm.tileentity.deco.TileEntityDecoTapeRecorder;
import com.hbm.tileentity.deco.TileEntityGeysir;
import com.hbm.tileentity.deco.TileEntityLantern;
import com.hbm.tileentity.deco.TileEntityLanternBehemoth;
import com.hbm.tileentity.deco.TileEntityObjTester;
import com.hbm.tileentity.deco.TileEntityTestRender;
import com.hbm.tileentity.deco.TileEntityTrappedBrick;
import com.hbm.tileentity.deco.TileEntityVent;
import com.hbm.tileentity.deco.TileEntityYellowBarrel;
import com.hbm.tileentity.machine.*;
import com.hbm.tileentity.machine.oil.TileEntityMachineCatalyticCracker;
import com.hbm.tileentity.machine.oil.TileEntityMachineCatalyticReformer;
import com.hbm.tileentity.machine.oil.TileEntityMachineCoker;
import com.hbm.tileentity.machine.oil.TileEntityMachineFrackingTower;
import com.hbm.tileentity.machine.oil.TileEntityMachineFractionTower;
import com.hbm.tileentity.machine.oil.TileEntityMachineGasFlare;
import com.hbm.tileentity.machine.oil.TileEntityMachineLiquefactor;
import com.hbm.tileentity.machine.oil.TileEntityMachineOilWell;
import com.hbm.tileentity.machine.oil.TileEntityMachinePumpjack;
import com.hbm.tileentity.machine.oil.TileEntityMachineRefinery;
import com.hbm.tileentity.machine.oil.TileEntityMachineSolidifier;
import com.hbm.tileentity.machine.oil.TileEntityMachineVacuumDistill;
import com.hbm.tileentity.machine.oil.TileEntitySpacer;
import com.hbm.tileentity.machine.pile.TileEntityPileBreedingFuel;
import com.hbm.tileentity.machine.pile.TileEntityPileFuel;
import com.hbm.tileentity.machine.pile.TileEntityPileNeutronDetector;
import com.hbm.tileentity.machine.pile.TileEntityPileSource;
import com.hbm.tileentity.machine.rbmk.TileEntityCraneConsole;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKAbsorber;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBlank;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBoiler;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKControlAuto;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKControlManual;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKCooler;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKHeater;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKInlet;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKModerator;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKOutgasser;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKOutlet;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKReflector;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKRod;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKRodReaSim;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKStorage;
import com.hbm.tileentity.machine.storage.TileEntityBarrel;
import com.hbm.tileentity.machine.storage.TileEntityCrateDesh;
import com.hbm.tileentity.machine.storage.TileEntityCrateIron;
import com.hbm.tileentity.machine.storage.TileEntityCrateSteel;
import com.hbm.tileentity.machine.storage.TileEntityCrateTemplate;
import com.hbm.tileentity.machine.storage.TileEntityCrateTungsten;
import com.hbm.tileentity.machine.storage.TileEntityFileCabinet;
import com.hbm.tileentity.machine.storage.TileEntityMachineBAT9000;
import com.hbm.tileentity.machine.storage.TileEntityMachineBattery;
import com.hbm.tileentity.machine.storage.TileEntityMachineFENSU;
import com.hbm.tileentity.machine.storage.TileEntityMachineFluidTank;
import com.hbm.tileentity.machine.storage.TileEntityMachineOrbus;
import com.hbm.tileentity.machine.storage.TileEntityMachinePuF6Tank;
import com.hbm.tileentity.machine.storage.TileEntityMachineUF6Tank;
import com.hbm.tileentity.machine.storage.TileEntityMassStorage;
import com.hbm.tileentity.machine.storage.TileEntitySafe;
import com.hbm.tileentity.machine.storage.TileEntitySoyuzCapsule;
import com.hbm.tileentity.network.TileEntityCableBaseNT;
import com.hbm.tileentity.network.TileEntityCableSwitch;
import com.hbm.tileentity.network.TileEntityConnector;
import com.hbm.tileentity.network.TileEntityConverterHeRf;
import com.hbm.tileentity.network.TileEntityConverterRfHe;
import com.hbm.tileentity.network.TileEntityCraneBoxer;
import com.hbm.tileentity.network.TileEntityCraneExtractor;
import com.hbm.tileentity.network.TileEntityCraneGrabber;
import com.hbm.tileentity.network.TileEntityCraneInserter;
import com.hbm.tileentity.network.TileEntityCraneRouter;
import com.hbm.tileentity.network.TileEntityCraneSplitter;
import com.hbm.tileentity.network.TileEntityCraneUnboxer;
import com.hbm.tileentity.network.TileEntityDroneCrate;
import com.hbm.tileentity.network.TileEntityDroneDock;
import com.hbm.tileentity.network.TileEntityDroneProvider;
import com.hbm.tileentity.network.TileEntityDroneRequester;
import com.hbm.tileentity.network.TileEntityDroneWaypoint;
import com.hbm.tileentity.network.TileEntityDroneWaypointRequest;
import com.hbm.tileentity.network.TileEntityFluidValve;
import com.hbm.tileentity.network.TileEntityPipeBaseNT;
import com.hbm.tileentity.network.TileEntityPipeExhaust;
import com.hbm.tileentity.network.TileEntityPylon;
import com.hbm.tileentity.network.TileEntityPylonLarge;
import com.hbm.tileentity.network.TileEntityRadioTelex;
import com.hbm.tileentity.network.TileEntityRadioTorchCounter;
import com.hbm.tileentity.network.TileEntityRadioTorchReceiver;
import com.hbm.tileentity.network.TileEntityRadioTorchSender;
import com.hbm.tileentity.network.TileEntitySubstation;
import com.hbm.tileentity.turret.TileEntityTurretArty;
import com.hbm.tileentity.turret.TileEntityTurretBrandon;
import com.hbm.tileentity.turret.TileEntityTurretChekhov;
import com.hbm.tileentity.turret.TileEntityTurretFriendly;
import com.hbm.tileentity.turret.TileEntityTurretFritz;
import com.hbm.tileentity.turret.TileEntityTurretHIMARS;
import com.hbm.tileentity.turret.TileEntityTurretHoward;
import com.hbm.tileentity.turret.TileEntityTurretHowardDamaged;
import com.hbm.tileentity.turret.TileEntityTurretJeremy;
import com.hbm.tileentity.turret.TileEntityTurretMaxwell;
import com.hbm.tileentity.turret.TileEntityTurretRichard;
import com.hbm.tileentity.turret.TileEntityTurretSentry;
import com.hbm.tileentity.turret.TileEntityTurretTauon;
import com.hbm.util.LoggingUtil;

import api.hbm.fluid.IFluidConnector;
import net.minecraft.tileentity.TileEntity;

public class TileMappings {

	public static HashMap<Class<? extends TileEntity>, String[]> map = new HashMap<>();
	public static List<Class<? extends IConfigurableMachine>> configurables = new ArrayList<>();
	
	public static void writeMappings() {
		TileMappings.put(TileEntityTestBombAdvanced.class, "tilentity_testbombadvanced");
		TileMappings.put(TileEntityDiFurnace.class, "tilentity_diFurnace");
		TileMappings.put(TileEntityTestNuke.class, "tilentity_testnuke");
		TileMappings.put(TileEntityTestRender.class, "tilentity_testrenderer");
		TileMappings.put(TileEntityObjTester.class, "tilentity_objtester");
		TileMappings.put(TileEntityMachineCentrifuge.class, "tileentity_centrifuge");
		TileMappings.put(TileEntityNukeMan.class, "tileentity_nukeman");
		TileMappings.put(TileEntityMachineUF6Tank.class, "tileentity_uf6_tank");
		TileMappings.put(TileEntityMachinePuF6Tank.class, "tileentity_puf6_tank");
		TileMappings.put(TileEntityMachineReactorBreeding.class, "tileentity_reactor");
		TileMappings.put(TileEntityNukeFurnace.class, "tileentity_nukefurnace");
		TileMappings.put(TileEntityRtgFurnace.class, "tileentity_rtgfurnace");
		TileMappings.put(TileEntityMachineElectricFurnace.class, "tileentity_electric_furnace");
		TileMappings.put(TileEntityDecoTapeRecorder.class, "tileentity_taperecorder");
		TileMappings.put(TileEntityDecoSteelPoles.class, "tileentity_steelpoles");
		TileMappings.put(TileEntityDecoPoleTop.class, "tileentity_poletop");
		TileMappings.put(TileEntityDecoPoleSatelliteReceiver.class, "tileentity_satellitereceicer");
		TileMappings.put(TileEntityMachineBattery.class, "tileentity_battery");
		TileMappings.put(TileEntityCapacitor.class, "tileentity_capacitor");
		TileMappings.put(TileEntityMachineCoal.class, "tileentity_coal");
		TileMappings.put(TileEntityRedBarrel.class, "tileentity_barrel");
		TileMappings.put(TileEntityYellowBarrel.class, "tileentity_nukebarrel");
		TileMappings.put(TileEntityLaunchPad.class, "tileentity_launch1");
		TileMappings.put(TileEntityDecoBlock.class, "tileentity_deco");
		TileMappings.put(TileEntityDecoBlockAltW.class, "tileentity_deco_w");
		TileMappings.put(TileEntityDecoBlockAltG.class, "tileentity_deco_g");
		TileMappings.put(TileEntityDecoBlockAltF.class, "tileentity_deco_f");
		TileMappings.put(TileEntityCrashedBomb.class, "tileentity_crashed_balefire");
		TileMappings.put(TileEntityConverterHeRf.class, "tileentity_converter_herf");
		TileMappings.put(TileEntityConverterRfHe.class, "tileentity_converter_rfhe");
		TileMappings.put(TileEntityMachineSchrabidiumTransmutator.class, "tileentity_schrabidium_transmutator");
		TileMappings.put(TileEntityMachineDiesel.class, "tileentity_diesel_generator");
		TileMappings.put(TileEntityWatzCore.class, "tileentity_watz_multiblock");
		TileMappings.put(TileEntityMachineShredder.class, "tileentity_machine_shredder");
		TileMappings.put(TileEntityMachineCMBFactory.class, "tileentity_machine_cmb");
		TileMappings.put(TileEntityFWatzCore.class, "tileentity_fwatz_multiblock");
		TileMappings.put(TileEntityMachineTeleporter.class, "tileentity_teleblock");
		TileMappings.put(TileEntityHatch.class, "tileentity_seal_lid");
		TileMappings.put(TileEntityMachineIGenerator.class, "tileentity_igenerator");
		TileMappings.put(TileEntityPartEmitter.class, "tileentity_partemitter");
		TileMappings.put(TileEntityDummy.class, "tileentity_dummy");
		TileMappings.put(TileEntityMachineCyclotron.class, "tileentity_cyclotron");
		TileMappings.put(TileEntityMachineRTG.class, "tileentity_machine_rtg");
		TileMappings.put(TileEntityStructureMarker.class, "tileentity_structure_marker");
		TileMappings.put(TileEntityMachineMiningDrill.class, "tileentity_mining_drill");
		TileMappings.put(TileEntityMachineExcavator.class, "tileentity_ntm_excavator");
		TileMappings.put(TileEntityFluidDuctSimple.class, "tileentity_universal_duct_simple");
		TileMappings.put(TileEntityFluidDuct.class, "tileentity_universal_duct");
		TileMappings.put(TileEntityMachineFluidTank.class, "tileentity_fluid_tank");
		TileMappings.put(TileEntityMachineTurbofan.class, "tileentity_machine_turbofan");
		TileMappings.put(TileEntityMachineTurbineGas.class, "tileentity_machine_gasturbine");
		TileMappings.put(TileEntityCrateTemplate.class, "tileentity_crate_template");
		TileMappings.put(TileEntityCrateIron.class, "tileentity_crate_iron");
		TileMappings.put(TileEntityCrateSteel.class, "tileentity_crate_steel");
		TileMappings.put(TileEntityCrateDesh.class, "tileentity_crate_desh");
		TileMappings.put(TileEntityMassStorage.class, "tileentity_mass_storage");
		TileMappings.put(TileEntityMachinePress.class, "tileentity_press");
		TileMappings.put(TileEntityAMSBase.class, "tileentity_ams_base");
		TileMappings.put(TileEntityAMSEmitter.class, "tileentity_ams_emitter");
		TileMappings.put(TileEntityAMSLimiter.class, "tileentity_ams_limiter");
		TileMappings.put(TileEntityMachineSiren.class, "tileentity_siren");
		TileMappings.put(TileEntityMachineSPP.class, "tileentity_spp");
		TileMappings.put(TileEntityMachineRadGen.class, "tileentity_radgen");
		TileMappings.put(TileEntityMachineTransformer.class, "tileentity_transformer");
		TileMappings.put(TileEntityMachineRadar.class, "tileentity_radar");
		TileMappings.put(TileEntityBroadcaster.class, "tileentity_pink_cloud_broadcaster");
		TileMappings.put(TileEntityMachineSeleniumEngine.class, "tileentity_selenium_engine");
		TileMappings.put(TileEntityMachineSatLinker.class, "tileentity_satlinker");
		TileMappings.put(TileEntityReactorResearch.class, "tileentity_small_reactor");
		TileMappings.put(TileEntityVaultDoor.class, "tileentity_vault_door");
		TileMappings.put(TileEntityRadiobox.class, "tileentity_radio_broadcaster");
		TileMappings.put(TileEntityRadioRec.class, "tileentity_radio_receiver");
		TileMappings.put(TileEntityVent.class, "tileentity_vent");
		TileMappings.put(TileEntityLandmine.class, "tileentity_landmine");
		TileMappings.put(TileEntityBomber.class, "tileentity_bomber");
		TileMappings.put(TileEntityMachineKeyForge.class, "tileentity_key_forge");
		TileMappings.put(TileEntitySellafield.class, "tileentity_sellafield_core");
		TileMappings.put(TileEntityNukeN45.class, "tileentity_n45");
		TileMappings.put(TileEntityBlastDoor.class, "tileentity_blast_door");
		TileMappings.put(TileEntitySafe.class, "tileentity_safe");
		TileMappings.put(TileEntityMachineGasCent.class, "tileentity_gas_centrifuge");
		TileMappings.put(TileEntityMachineBoiler.class, "tileentity_boiler");
		TileMappings.put(TileEntityMachineBoilerElectric.class, "tileentity_electric_boiler");
		TileMappings.put(TileEntityGeiger.class, "tileentity_geiger");
		TileMappings.put(TileEntityFF.class, "tileentity_forcefield");
		TileMappings.put(TileEntityForceField.class, "tileentity_machine_field");
		TileMappings.put(TileEntityMachineShredderLarge.class, "tileentity_machine_big_shredder");
		TileMappings.put(TileEntityRFDuct.class, "tileentity_hbm_rfduct");
		TileMappings.put(TileEntityReactorControl.class, "tileentity_reactor_remote_control");
		TileMappings.put(TileEntityMachineReactorLarge.class, "tileentity_large_reactor");
		TileMappings.put(TileEntityWasteDrum.class, "tileentity_waste_drum");
		TileMappings.put(TileEntityDecon.class, "tileentity_decon");
		TileMappings.put(TileEntityMachineSatDock.class, "tileentity_miner_dock");
		TileMappings.put(TileEntityMachineEPress.class, "tileentity_electric_press");
		TileMappings.put(TileEntityConveyorPress.class, "tileentity_conveyor_press");
		TileMappings.put(TileEntityCoreEmitter.class, "tileentity_v0_emitter");
		TileMappings.put(TileEntityCoreReceiver.class, "tileentity_v0_receiver");
		TileMappings.put(TileEntityCoreInjector.class, "tileentity_v0_injector");
		TileMappings.put(TileEntityCoreStabilizer.class, "tileentity_v0_stabilizer");
		TileMappings.put(TileEntityCore.class, "tileentity_v0");
		TileMappings.put(TileEntityMachineArcFurnace.class, "tileentity_arc_furnace");
		TileMappings.put(TileEntityMachineAmgen.class, "tileentity_amgen");
		TileMappings.put(TileEntityMachineHephaestus.class, "tileentity_hephaestus");
		TileMappings.put(TileEntityGeysir.class, "tileentity_geysir");
		TileMappings.put(TileEntityMachineMissileAssembly.class, "tileentity_missile_assembly");
		TileMappings.put(TileEntityLaunchTable.class, "tileentity_large_launch_table");
		TileMappings.put(TileEntityCompactLauncher.class, "tileentity_small_launcher");
		TileMappings.put(TileEntityMultiblock.class, "tileentity_multi_core");
		TileMappings.put(TileEntityChlorineSeal.class, "tileentity_chlorine_seal");
		TileMappings.put(TileEntitySoyuzLauncher.class, "tileentity_soyuz_launcher");
		TileMappings.put(TileEntityTesla.class, "tileentity_tesla_coil");
		TileMappings.put(TileEntityBarrel.class, "tileentity_fluid_barrel");
		TileMappings.put(TileEntityCyberCrab.class, "tileentity_crabs");
		TileMappings.put(TileEntitySoyuzCapsule.class, "tileentity_soyuz_capsule");
		TileMappings.put(TileEntityMachineCrystallizer.class, "tileentity_acidomatic");
		TileMappings.put(TileEntitySoyuzStruct.class, "tileentity_soyuz_struct");
		TileMappings.put(TileEntityITERStruct.class, "tileentity_iter_struct");
		TileMappings.put(TileEntityMachineMiningLaser.class, "tileentity_mining_laser");
		TileMappings.put(TileEntityNukeBalefire.class, "tileentity_nuke_fstbmb");
		TileMappings.put(TileEntityMicrowave.class, "tileentity_microwave");
		TileMappings.put(TileEntityMachineMiniRTG.class, "tileentity_mini_rtg");
		TileMappings.put(TileEntityITER.class, "tileentity_iter");
		TileMappings.put(TileEntityMachinePlasmaHeater.class, "tileentity_plasma_heater");
		TileMappings.put(TileEntityMachineFENSU.class, "tileentity_fensu");
		TileMappings.put(TileEntityTrappedBrick.class, "tileentity_trapped_brick");
		TileMappings.put(TileEntityPlasmaStruct.class, "tileentity_plasma_struct");
		TileMappings.put(TileEntityWatzStruct.class, "tileentity_watz_struct");
		TileMappings.put(TileEntityHadronDiode.class, "tileentity_hadron_diode");
		TileMappings.put(TileEntityHadronPower.class, "tileentity_hadron_power");
		TileMappings.put(TileEntityHadron.class, "tileentity_hadron");
		TileMappings.put(TileEntitySolarBoiler.class, "tileentity_solarboiler");
		TileMappings.put(TileEntitySolarMirror.class, "tileentity_solarmirror");
		TileMappings.put(TileEntityMachineDetector.class, "tileentity_he_detector");
		TileMappings.put(TileEntityFireworks.class, "tileentity_firework_box");
		TileMappings.put(TileEntityCrateTungsten.class, "tileentity_crate_hot");
		TileMappings.put(TileEntitySILEX.class, "tileentity_silex");
		TileMappings.put(TileEntityFEL.class, "tileentity_fel");
		TileMappings.put(TileEntityDemonLamp.class, "tileentity_demonlamp");
		TileMappings.put(TileEntityLantern.class, "tileentity_lantern_ordinary");
		TileMappings.put(TileEntityLanternBehemoth.class, "tileentity_lantern_behemoth");
		TileMappings.put(TileEntityStorageDrum.class, "tileentity_waste_storage_drum");
		TileMappings.put(TileEntityDeaerator.class, "tileentity_deaerator");
		TileMappings.put(TileEntityCableBaseNT.class, "tileentity_ohgod"); // what?
		TileMappings.put(TileEntityCablePaintable.class, "tileentity_cable_paintable");
		TileMappings.put(TileEntityCableGauge.class, "tileentity_cable_gauge");
		TileMappings.put(TileEntityPipeBaseNT.class, "tileentity_pipe_base");
		TileMappings.put(TileEntityPipePaintable.class, "tileentity_pipe_paintable");
		TileMappings.put(TileEntityPipeGauge.class, "tileentity_pipe_gauge");
		TileMappings.put(TileEntityPipeExhaust.class, "tileentity_pipe_exhaust");
		TileMappings.put(TileEntityFluidValve.class, "tileentity_pipe_valve");
		TileMappings.put(TileEntityMachineBAT9000.class, "tileentity_bat9000");
		TileMappings.put(TileEntityMachineOrbus.class, "tileentity_orbus");
		TileMappings.put(TileEntityGlpyhidSpawner.class, "tileentity_glyphid_spawner");
		TileMappings.put(TileEntityCustomMachine.class, "tileentity_custom_machine");
		
		TileMappings.put(TileEntityLoot.class, "tileentity_ntm_loot");
		TileMappings.put(TileEntityBobble.class, "tileentity_ntm_bobblehead");
		TileMappings.put(TileEntitySnowglobe.class, "tileentity_ntm_snowglobe");
		TileMappings.put(TileEntityEmitter.class, "tileentity_ntm_emitter");

		TileMappings.put(TileEntityDoorGeneric.class, "tileentity_ntm_door");

		TileMappings.put(TileEntityCharger.class, "tileentity_ntm_charger");
		
		TileMappings.put(TileEntityFileCabinet.class, "tileentity_file_cabinet");
		
		TileMappings.put(TileEntityProxyInventory.class, "tileentity_proxy_inventory");
		TileMappings.put(TileEntityProxyEnergy.class, "tileentity_proxy_power");
		TileMappings.put(TileEntityProxyCombo.class, "tileentity_proxy_combo");
		TileMappings.put(TileEntityProxyConductor.class, "tileentity_proxy_conductor");

		TileMappings.put(TileEntityRandomOre.class, "tileentity_mother_of_all_ores");
		TileMappings.put(TileEntityBedrockOre.class, "tileentity_bedrock_ore");

		TileMappings.put(TileEntityBlockPWR.class, "tileentity_block_pwr");
		TileMappings.put(TileEntityPWRController.class, "tileentity_pwr_controller");
		
		TileMappings.putNetwork();
		TileMappings.putBombs();
		TileMappings.putTurrets();
		TileMappings.putMachines();
		TileMappings.putPile();
		TileMappings.putRBMK();
	}
	
	private static void putBombs() {
		TileMappings.put(TileEntityBombMulti.class, "tileentity_bombmulti");
		TileMappings.put(TileEntityNukeGadget.class, "tilentity_nukegadget");
		TileMappings.put(TileEntityNukeBoy.class, "tilentity_nukeboy");
		TileMappings.put(TileEntityNukeMike.class, "tileentity_nukemike");
		TileMappings.put(TileEntityNukeTsar.class, "tileentity_nuketsar");
		TileMappings.put(TileEntityNukeFleija.class, "tileentity_nukefleija");
		TileMappings.put(TileEntityNukePrototype.class, "tileentity_nukeproto");
		TileMappings.put(TileEntityNukeSolinium.class, "tileentity_nuke_solinium");
		TileMappings.put(TileEntityNukeN2.class, "tileentity_nuke_n2");
		TileMappings.put(TileEntityNukeCustom.class, "tileentity_nuke_custom");
		TileMappings.put(TileEntityCharge.class, "tileentity_explosive_charge");
		TileMappings.put(TileEntityVolcanoCore.class, "tileentity_volcano_core");
	}
	
	private static void putTurrets() {
		TileMappings.put(TileEntityTurretChekhov.class, "tileentity_turret_chekhov");
		TileMappings.put(TileEntityTurretJeremy.class, "tileentity_turret_jeremy");
		TileMappings.put(TileEntityTurretTauon.class, "tileentity_turret_tauon");
		TileMappings.put(TileEntityTurretFriendly.class, "tileentity_turret_friendly");
		TileMappings.put(TileEntityTurretRichard.class, "tileentity_turret_richard");
		TileMappings.put(TileEntityTurretHoward.class, "tileentity_turret_howard");
		TileMappings.put(TileEntityTurretHowardDamaged.class, "tileentity_turret_howard_damaged");
		TileMappings.put(TileEntityTurretMaxwell.class, "tileentity_turret_maxwell");
		TileMappings.put(TileEntityTurretFritz.class, "tileentity_turret_fritz");
		TileMappings.put(TileEntityTurretBrandon.class, "tileentity_turret_brandon");
		TileMappings.put(TileEntityTurretArty.class, "tileentity_turret_arty");
		TileMappings.put(TileEntityTurretHIMARS.class, "tileentity_turret_himars");
		TileMappings.put(TileEntityTurretSentry.class, "tileentity_turret_sentry");
	}
	
	private static void putMachines() {
		TileMappings.put(TileEntityHeaterFirebox.class, "tileentity_firebox");
		TileMappings.put(TileEntityHeaterOven.class, "tileentity_heating_oven");
		TileMappings.put(TileEntityAshpit.class, "tileentity_ashpit");
		TileMappings.put(TileEntityHeaterOilburner.class, "tileentity_oilburner");
		TileMappings.put(TileEntityHeaterElectric.class, "tileentity_electric_heater");
		TileMappings.put(TileEntityHeaterHeatex.class, "tileentity_heater_heatex");
		TileMappings.put(TileEntityFurnaceIron.class, "tileentity_furnace_iron");
		TileMappings.put(TileEntityFurnaceSteel.class, "tileentity_furnace_steel");
		TileMappings.put(TileEntityFurnaceCombination.class, "tileentity_combination_oven");
		TileMappings.put(TileEntityStirling.class, "tileentity_stirling");
		TileMappings.put(TileEntitySawmill.class, "tileentity_sawmill");
		TileMappings.put(TileEntityCrucible.class, "tileentity_crucible");
		TileMappings.put(TileEntityHeatBoiler.class, "tileentity_heat_boiler");
		TileMappings.put(TileEntityHeatBoilerIndustrial.class, "tileentity_heat_boiler_industrial");

		TileMappings.put(TileEntityMachinePumpSteam.class, "tileentity_steam_pump");
		TileMappings.put(TileEntityMachinePumpElectric.class, "tileentity_electric_pump");
		
		TileMappings.put(TileEntityFoundryMold.class, "tileentity_foundry_mold");
		TileMappings.put(TileEntityFoundryBasin.class, "tileentity_foundry_basin");
		TileMappings.put(TileEntityFoundryChannel.class, "tileentity_foundry_channel");
		TileMappings.put(TileEntityFoundryTank.class, "tileentity_foundry_tank");
		TileMappings.put(TileEntityFoundryOutlet.class, "tileentity_foundry_outlet");
		TileMappings.put(TileEntityFoundrySlagtap.class, "tileentity_foundry_slagtap");
		TileMappings.put(TileEntitySlag.class, "tileentity_foundry_slag");
		
		TileMappings.put(TileEntityMachineAutocrafter.class, "tileentity_autocrafter");
		TileMappings.put(TileEntityDiFurnaceRTG.class, "tileentity_rtg_difurnace");
		TileMappings.put(TileEntityMachineRadiolysis.class, "tileentity_radiolysis");
		TileMappings.put(TileEntityUVLamp.class, "tileentity_uv_lamp");
		TileMappings.put(TileEntityMachineAutosaw.class, "tileentity_autosaw");
		
		TileMappings.put(TileEntityCondenser.class, "tileentity_condenser");
		TileMappings.put(TileEntityTowerSmall.class, "tileentity_cooling_tower_small");
		TileMappings.put(TileEntityTowerLarge.class, "tileentity_cooling_tower_large");
		TileMappings.put(TileEntityCondenserPowered.class, "tileentity_condenser_powered");
		TileMappings.put(TileEntityDeuteriumExtractor.class, "tileentity_deuterium_extractor");
		TileMappings.put(TileEntityDeuteriumTower.class, "tileentity_deuterium_tower");
		TileMappings.put(TileEntityMachineLiquefactor.class, "tileentity_liquefactor");
		TileMappings.put(TileEntityMachineSolidifier.class, "tileentity_solidifier");
		TileMappings.put(TileEntityMachineCompressor.class, "tileentity_compressor");
		TileMappings.put(TileEntityElectrolyser.class, "tileentity_electrolyser");
		TileMappings.put(TileEntityMachineMixer.class, "tileentity_mixer");
		TileMappings.put(TileEntityMachineArcWelder.class, "tileentity_arc_welder");

		TileMappings.put(TileEntitySteamEngine.class, "tileentity_steam_engine");
		TileMappings.put(TileEntityMachineTurbine.class, "tileentity_turbine");
		TileMappings.put(TileEntityMachineLargeTurbine.class, "tileentity_industrial_turbine");
		TileMappings.put(TileEntityChungus.class, "tileentity_chungus");

		TileMappings.put(TileEntityMachineCombustionEngine.class, "tileentity_combustion_engine");
		
		TileMappings.put(TileEntityMachineAssembler.class, "tileentity_assembly_machine");
		TileMappings.put(TileEntityMachineAssemfac.class, "tileentity_assemfac");
		TileMappings.put(TileEntityMachineChemplant.class, "tileentity_chemical_plant");
		TileMappings.put(TileEntityMachineChemfac.class, "tileentity_chemfac");
		
		TileMappings.put(TileEntityMachineOilWell.class, "tileentity_derrick");
		TileMappings.put(TileEntityMachinePumpjack.class, "tileentity_machine_pumpjack");
		TileMappings.put(TileEntityMachineFrackingTower.class, "tileentity_fracking_tower");
		TileMappings.put(TileEntityMachineGasFlare.class, "tileentity_gasflare");
		TileMappings.put(TileEntityMachineRefinery.class, "tileentity_refinery");
		TileMappings.put(TileEntityMachineVacuumDistill.class, "tileentity_vacuuum_distill");
		TileMappings.put(TileEntityMachineFractionTower.class, "tileentity_fraction_tower");
		TileMappings.put(TileEntitySpacer.class, "tileentity_fraction_spacer");
		TileMappings.put(TileEntityMachineCatalyticCracker.class, "tileentity_catalytic_cracker");
		TileMappings.put(TileEntityMachineCatalyticReformer.class, "tileentity_catalytic_reformer");
		TileMappings.put(TileEntityMachineCoker.class, "tileentity_coker");
		TileMappings.put(TileEntityChimneyBrick.class, "tileentity_chimney_brick");
		TileMappings.put(TileEntityChimneyIndustrial.class, "tileentity_chimney_industrial");
		
		TileMappings.put(TileEntityReactorZirnox.class, "tileentity_zirnox");
		TileMappings.put(TileEntityZirnoxDestroyed.class, "tileentity_zirnox_destroyed");

		TileMappings.put(TileEntityWatz.class, "tileentity_watz");
		TileMappings.put(TileEntityWatzPump.class, "tileentity_watz_pump");
	}
	
	private static void putPile() {
		TileMappings.put(TileEntityPileFuel.class, "tileentity_pile_fuel");
		TileMappings.put(TileEntityPileSource.class, "tileentity_pile_source");
		TileMappings.put(TileEntityPileBreedingFuel.class, "tileentity_pile_breedingfuel");
		TileMappings.put(TileEntityPileNeutronDetector.class, "tileentity_pile_neutrondetector");
	}
	
	private static void putRBMK() {
		TileMappings.put(TileEntityRBMKRod.class, "tileentity_rbmk_rod");
		TileMappings.put(TileEntityRBMKRodReaSim.class, "tileentity_rbmk_rod_reasim");
		TileMappings.put(TileEntityRBMKControlManual.class, "tileentity_rbmk_control");
		TileMappings.put(TileEntityRBMKControlAuto.class, "tileentity_rbmk_control_auto");
		TileMappings.put(TileEntityRBMKBlank.class, "tileentity_rbmk_blank");
		TileMappings.put(TileEntityRBMKBoiler.class, "tileentity_rbmk_boiler");
		TileMappings.put(TileEntityRBMKReflector.class, "tileentity_rbmk_reflector");
		TileMappings.put(TileEntityRBMKAbsorber.class, "tileentity_rbmk_absorber");
		TileMappings.put(TileEntityRBMKModerator.class, "tileentity_rbmk_moderator");
		TileMappings.put(TileEntityRBMKOutgasser.class, "tileentity_rbmk_outgasser");
		TileMappings.put(TileEntityRBMKCooler.class, "tileentity_rbmk_cooler");
		TileMappings.put(TileEntityRBMKHeater.class, "tileentity_rbmk_heater");
		TileMappings.put(TileEntityRBMKStorage.class, "tileentity_rbmk_storage");
		TileMappings.put(TileEntityCraneConsole.class, "tileentity_rbmk_crane_console");
		TileMappings.put(TileEntityRBMKConsole.class, "tileentity_rbmk_console");
		TileMappings.put(TileEntityRBMKInlet.class, "tileentity_rbmk_inlet");
		TileMappings.put(TileEntityRBMKOutlet.class, "tileentity_rbmk_outlet");
	}
	
	private static void putNetwork() {
		TileMappings.put(TileEntityCableBaseNT.class, "tileentity_cable", "tileentity_wirecoated");
		TileMappings.put(TileEntityCableSwitch.class, "tileentity_cable_switch");
		TileMappings.put(TileEntityDiode.class, "tileentity_cable_diode");
		
		TileMappings.put(TileEntityConnector.class, "tileentity_connector_redwire");
		TileMappings.put(TileEntityPylon.class, "tileentity_pylon_redwire");
		TileMappings.put(TileEntityPylonLarge.class, "tileentity_pylon_large");
		TileMappings.put(TileEntitySubstation.class, "tileentity_substation");

		TileMappings.put(TileEntityCraneInserter.class, "tileentity_inserter");
		TileMappings.put(TileEntityCraneExtractor.class, "tileentity_extractor");
		TileMappings.put(TileEntityCraneGrabber.class, "tileentity_grabber");
		TileMappings.put(TileEntityCraneBoxer.class, "tileentity_boxer");
		TileMappings.put(TileEntityCraneUnboxer.class, "tileentity_unboxer");
		TileMappings.put(TileEntityCraneRouter.class, "tileentity_router");
		TileMappings.put(TileEntityCraneSplitter.class, "tileentity_splitter");
		TileMappings.put(TileEntityFan.class, "tileentity_fan");
		TileMappings.put(TileEntityPistonInserter.class, "tileentity_piston_inserter");

		TileMappings.put(TileEntityRadioTorchSender.class, "tileentity_rtty_sender");
		TileMappings.put(TileEntityRadioTorchReceiver.class, "tileentity_rtty_rec");
		TileMappings.put(TileEntityRadioTorchCounter.class, "tileentity_rtty_counter");
		TileMappings.put(TileEntityRadioTelex.class, "tileentity_rtty_telex");
		
		TileMappings.put(TileEntityDroneWaypoint.class, "tileentity_drone_waypoint");
		TileMappings.put(TileEntityDroneCrate.class, "tileentity_drone_crate");
		TileMappings.put(TileEntityDroneWaypointRequest.class, "tileentity_drone_waypoint_request");
		TileMappings.put(TileEntityDroneDock.class, "tileentity_drone_dock");
		TileMappings.put(TileEntityDroneProvider.class, "tileentity_drone_provider");
		TileMappings.put(TileEntityDroneRequester.class, "tileentity_drone_requester");
	}
	
	
	private static void put(Class<? extends TileEntity> clazz, String... names) {
		TileMappings.map.put(clazz, names);

		if((IFluidSource.class.isAssignableFrom(clazz) || IFluidAcceptor.class.isAssignableFrom(clazz)) && !IFluidConnector.class.isAssignableFrom(clazz)) {
			LoggingUtil.errorWithHighlight(clazz.getCanonicalName() + " implements the old interfaces but not IFluidConnector!");
		}
		
		if(IConfigurableMachine.class.isAssignableFrom(clazz)) {
			TileMappings.configurables.add((Class<? extends IConfigurableMachine>) clazz);
		}
	}
}
