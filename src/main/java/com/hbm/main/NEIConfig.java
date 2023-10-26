package com.hbm.main;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockMotherOfAllOres.TileEntityRandomOre;
import com.hbm.config.CustomMachineConfigJSON;
import com.hbm.config.CustomMachineConfigJSON.MachineConfiguration;
import com.hbm.config.VersatileConfig;
import com.hbm.handler.nei.AlloyFurnaceRecipeHandler;
import com.hbm.handler.nei.AnvilRecipeHandler;
import com.hbm.handler.nei.ArcWelderHandler;
import com.hbm.handler.nei.AshpitHandler;
import com.hbm.handler.nei.AssemblerRecipeHandler;
import com.hbm.handler.nei.BoilerRecipeHandler;
import com.hbm.handler.nei.BoilingHandler;
import com.hbm.handler.nei.BookRecipeHandler;
import com.hbm.handler.nei.BreederRecipeHandler;
import com.hbm.handler.nei.CentrifugeRecipeHandler;
import com.hbm.handler.nei.ChemplantRecipeHandler;
import com.hbm.handler.nei.CokingHandler;
import com.hbm.handler.nei.CombinationHandler;
import com.hbm.handler.nei.ConstructionHandler;
import com.hbm.handler.nei.CrackingHandler;
import com.hbm.handler.nei.CrucibleAlloyingHandler;
import com.hbm.handler.nei.CrucibleCastingHandler;
import com.hbm.handler.nei.CrucibleSmeltingHandler;
import com.hbm.handler.nei.CrystallizerRecipeHandler;
import com.hbm.handler.nei.CustomMachineHandler;
import com.hbm.handler.nei.CyclotronRecipeHandler;
import com.hbm.handler.nei.ElectrolyserFluidHandler;
import com.hbm.handler.nei.ElectrolyserMetalHandler;
import com.hbm.handler.nei.FluidRecipeHandler;
import com.hbm.handler.nei.FractioningHandler;
import com.hbm.handler.nei.FuelPoolHandler;
import com.hbm.handler.nei.FusionRecipeHandler;
import com.hbm.handler.nei.GasCentrifugeRecipeHandler;
import com.hbm.handler.nei.HadronRecipeHandler;
import com.hbm.handler.nei.LiquefactionHandler;
import com.hbm.handler.nei.MixerHandler;
import com.hbm.handler.nei.OutgasserHandler;
import com.hbm.handler.nei.PressRecipeHandler;
import com.hbm.handler.nei.RTGRecipeHandler;
import com.hbm.handler.nei.RadiolysisRecipeHandler;
import com.hbm.handler.nei.RefineryRecipeHandler;
import com.hbm.handler.nei.ReformingHandler;
import com.hbm.handler.nei.SILEXRecipeHandler;
import com.hbm.handler.nei.SawmillHandler;
import com.hbm.handler.nei.ShredderRecipeHandler;
import com.hbm.handler.nei.SmithingRecipeHandler;
import com.hbm.handler.nei.SolidificationHandler;
import com.hbm.handler.nei.ToolingHandler;
import com.hbm.handler.nei.VacuumRecipeHandler;
import com.hbm.handler.nei.ZirnoxRecipeHandler;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBattery;
import com.hbm.lib.RefStrings;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.api.IHighlightHandler;
import codechicken.nei.api.ItemInfo.Layout;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.recipe.IUsageHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class NEIConfig implements IConfigureNEI {

	@Override
	public void loadConfig() {
		NEIConfig.registerHandler(new AlloyFurnaceRecipeHandler());
		NEIConfig.registerHandler(new ShredderRecipeHandler());
		NEIConfig.registerHandler(new PressRecipeHandler());
		NEIConfig.registerHandler(new CentrifugeRecipeHandler());
		NEIConfig.registerHandler(new GasCentrifugeRecipeHandler());
		NEIConfig.registerHandler(new BreederRecipeHandler());
		NEIConfig.registerHandler(new CyclotronRecipeHandler());
		NEIConfig.registerHandler(new AssemblerRecipeHandler());
		NEIConfig.registerHandler(new RefineryRecipeHandler());
		NEIConfig.registerHandler(new VacuumRecipeHandler());
		NEIConfig.registerHandler(new CrackingHandler());
		NEIConfig.registerHandler(new ReformingHandler());
		NEIConfig.registerHandler(new BoilerRecipeHandler());
		NEIConfig.registerHandler(new ChemplantRecipeHandler());
		NEIConfig.registerHandler(new CrystallizerRecipeHandler());
		NEIConfig.registerHandler(new BookRecipeHandler());
		NEIConfig.registerHandler(new FusionRecipeHandler());
		NEIConfig.registerHandler(new HadronRecipeHandler());
		NEIConfig.registerHandler(new SILEXRecipeHandler());
		NEIConfig.registerHandler(new SmithingRecipeHandler());
		NEIConfig.registerHandler(new AnvilRecipeHandler());
		NEIConfig.registerHandler(new FuelPoolHandler());
		NEIConfig.registerHandler(new RadiolysisRecipeHandler());
		NEIConfig.registerHandler(new CrucibleSmeltingHandler());
		NEIConfig.registerHandler(new CrucibleAlloyingHandler());
		NEIConfig.registerHandler(new CrucibleCastingHandler());
		NEIConfig.registerHandler(new ToolingHandler());
		NEIConfig.registerHandler(new ConstructionHandler());
		
		//universal boyes
		NEIConfig.registerHandler(new ZirnoxRecipeHandler());
		if(VersatileConfig.rtgDecay()) {
			NEIConfig.registerHandler(new RTGRecipeHandler());
		}
		NEIConfig.registerHandler(new LiquefactionHandler());
		NEIConfig.registerHandler(new SolidificationHandler());
		NEIConfig.registerHandler(new CokingHandler());
		NEIConfig.registerHandler(new FractioningHandler());
		NEIConfig.registerHandler(new BoilingHandler());
		NEIConfig.registerHandler(new CombinationHandler());
		NEIConfig.registerHandler(new SawmillHandler());
		NEIConfig.registerHandler(new MixerHandler());
		NEIConfig.registerHandler(new OutgasserHandler());
		NEIConfig.registerHandler(new ElectrolyserFluidHandler());
		NEIConfig.registerHandler(new ElectrolyserMetalHandler());
		NEIConfig.registerHandler(new AshpitHandler());
		NEIConfig.registerHandler(new ArcWelderHandler());

		for(MachineConfiguration conf : CustomMachineConfigJSON.niceList) NEIConfig.registerHandlerBypass(new CustomMachineHandler(conf));
		
		//fluids
		NEIConfig.registerHandler(new FluidRecipeHandler());

		//Some things are even beyond my control...or are they?
		API.hideItem(ItemBattery.getEmptyBattery(ModItems.memory));
		API.hideItem(ItemBattery.getFullBattery(ModItems.memory));
		
		API.hideItem(new ItemStack(ModBlocks.machine_coal_on));
		API.hideItem(new ItemStack(ModBlocks.machine_electric_furnace_on));
		API.hideItem(new ItemStack(ModBlocks.machine_difurnace_on));
		API.hideItem(new ItemStack(ModBlocks.machine_nuke_furnace_on));
		API.hideItem(new ItemStack(ModBlocks.machine_rtg_furnace_on));
		API.hideItem(new ItemStack(ModBlocks.reinforced_lamp_on));
		API.hideItem(new ItemStack(ModBlocks.statue_elb));
		API.hideItem(new ItemStack(ModBlocks.statue_elb_g));
		API.hideItem(new ItemStack(ModBlocks.statue_elb_w));
		API.hideItem(new ItemStack(ModBlocks.statue_elb_f));
		API.hideItem(new ItemStack(ModBlocks.cheater_virus));
		API.hideItem(new ItemStack(ModBlocks.cheater_virus_seed));
		API.hideItem(new ItemStack(ModBlocks.transission_hatch));
		API.hideItem(new ItemStack(ModItems.euphemium_kit));
		API.hideItem(new ItemStack(ModItems.bobmazon_hidden));
		API.hideItem(new ItemStack(ModItems.book_lore)); //the broken nbt-less one shouldn't show up in normal play anyway
		if(MainRegistry.polaroidID != 11) {
			API.hideItem(new ItemStack(ModItems.book_secret));
			API.hideItem(new ItemStack(ModItems.book_of_));
			API.hideItem(new ItemStack(ModItems.burnt_bark));
			API.hideItem(new ItemStack(ModItems.ams_core_thingy));
		}
		API.hideItem(new ItemStack(ModBlocks.dummy_block_drill));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_ams_base));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_ams_emitter));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_ams_limiter));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_vault));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_blast));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_uf6));
		API.hideItem(new ItemStack(ModBlocks.dummy_block_puf6));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_drill));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_ams_base));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_ams_emitter));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_ams_limiter));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_compact_launcher));
		API.hideItem(new ItemStack(ModBlocks.dummy_port_launch_table));
		API.hideItem(new ItemStack(ModBlocks.dummy_plate_compact_launcher));
		API.hideItem(new ItemStack(ModBlocks.dummy_plate_launch_table));
		API.hideItem(new ItemStack(ModBlocks.dummy_plate_cargo));

		API.hideItem(new ItemStack(ModBlocks.pink_log));
		API.hideItem(new ItemStack(ModBlocks.pink_planks));
		API.hideItem(new ItemStack(ModBlocks.pink_slab));
		API.hideItem(new ItemStack(ModBlocks.pink_double_slab));
		API.hideItem(new ItemStack(ModBlocks.pink_stairs));
		
		API.registerHighlightIdentifier(ModBlocks.ore_random, new IHighlightHandler() {

			@Override
			public ItemStack identifyHighlight(World world, EntityPlayer player, MovingObjectPosition mop) {
				int x = mop.blockX;
				int y = mop.blockY;
				int z = mop.blockZ;
				
				TileEntity te = world.getTileEntity(x, y, z);
				
				if(te instanceof TileEntityRandomOre) {
					TileEntityRandomOre ore = (TileEntityRandomOre) te;
					return new ItemStack(ModBlocks.ore_random, 1, ore.getStackId());
				}
				
				return null;
			}

			@Override
			public List<String> handleTextData(ItemStack itemStack, World world, EntityPlayer player, MovingObjectPosition mop, List<String> currenttip, Layout layout) {
				return currenttip;
			}
			
		});
	}
	
	public static void registerHandler(Object o) {
		API.registerRecipeHandler((ICraftingHandler) o);
		API.registerUsageHandler((IUsageHandler) o);
	}
	
	/** Bypasses the utterly useless restriction of one registered handler per class */
	public static void registerHandlerBypass(Object o) {
		GuiCraftingRecipe.craftinghandlers.add((ICraftingHandler) o);
		GuiUsageRecipe.usagehandlers.add((IUsageHandler) o);
	}

	@Override
	public String getName() {
		return "Nuclear Tech NEI Plugin";
	}

	@Override
	public String getVersion() {
		return RefStrings.VERSION;
	}
}
