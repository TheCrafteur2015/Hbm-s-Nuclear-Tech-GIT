package com.hbm.tileentity.machine.oil;

import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.inventory.container.ContainerMachineOilWell;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.gui.GUIMachineOilWell;
import com.hbm.lib.Library;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityMachineOilWell extends TileEntityOilDrillBase {

	protected static int maxPower = 100_000;
	protected static int consumption = 100;
	protected static int delay = 50;
	protected static int oilPerDepsoit = 500;
	protected static int gasPerDepositMin = 100;
	protected static int gasPerDepositMax = 500;
	protected static double drainChance = 0.05D;

	@Override
	public String getName() {
		return "container.oilWell";
	}

	@Override
	public long getMaxPower() {
		return TileEntityMachineOilWell.maxPower;
	}

	@Override
	public int getPowerReq() {
		return TileEntityMachineOilWell.consumption;
	}

	@Override
	public int getDelay() {
		return TileEntityMachineOilWell.delay;
	}

	@Override
	public void onDrill(int y) {
		Block b = this.worldObj.getBlock(this.xCoord, y, this.zCoord);
		ItemStack stack = new ItemStack(b);
		int[] ids = OreDictionary.getOreIDs(stack);
		for(Integer i : ids) {
			String name = OreDictionary.getOreName(i);
			
			if("oreUranium".equals(name)) {
				for(int j = -1; j <= 1; j++) {
					for(int k = -1; k <= 1; k++) {
						if(this.worldObj.getBlock(this.xCoord + j, this.yCoord + 7, this.zCoord + j).isReplaceable(this.worldObj, this.xCoord + j, this.yCoord + 7, this.zCoord + k)) {
							this.worldObj.setBlock(this.xCoord + k, this.yCoord + 7, this.zCoord + k, ModBlocks.gas_radon_dense);
						}
					}
				}
			}
			
			if("oreAsbestos".equals(name)) {
				for(int j = -1; j <= 1; j++) {
					for(int k = -1; k <= 1; k++) {
						if(this.worldObj.getBlock(this.xCoord + j, this.yCoord + 7, this.zCoord + j).isReplaceable(this.worldObj, this.xCoord + j, this.yCoord + 7, this.zCoord + k)) {
							this.worldObj.setBlock(this.xCoord + k, this.yCoord + 7, this.zCoord + k, ModBlocks.gas_asbestos);
						}
					}
				}
			}
		}
	}

	@Override
	public void onSuck(int x, int y, int z) {

		ExplosionLarge.spawnOilSpills(this.worldObj, this.xCoord + 0.5F, this.yCoord + 5.5F, this.zCoord + 0.5F, 3);
		this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "game.neutral.swim.splash", 2.0F, 0.5F);
		
		this.tanks[0].setFill(this.tanks[0].getFill() + TileEntityMachineOilWell.oilPerDepsoit);
		if(this.tanks[0].getFill() > this.tanks[0].getMaxFill()) this.tanks[0].setFill(this.tanks[0].getMaxFill());
		this.tanks[1].setFill(this.tanks[1].getFill() + (TileEntityMachineOilWell.gasPerDepositMin + this.worldObj.rand.nextInt((TileEntityMachineOilWell.gasPerDepositMax - TileEntityMachineOilWell.gasPerDepositMin + 1))));
		if(this.tanks[1].getFill() > this.tanks[1].getMaxFill()) this.tanks[1].setFill(this.tanks[1].getMaxFill());
		
		if(this.worldObj.rand.nextDouble() < TileEntityMachineOilWell.drainChance) {
			this.worldObj.setBlock(x, y, z, ModBlocks.ore_oil_empty);
		}
	}

	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(this.xCoord - 2, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord + 2, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord - 2, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord + 2, getTact(), type);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(
					this.xCoord - 1,
					this.yCoord,
					this.zCoord - 1,
					this.xCoord + 2,
					this.yCoord + 7,
					this.zCoord + 2
					);
		}
		
		return this.bb;
	}

	@Override
	public DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(this.xCoord + 2, this.yCoord, this.zCoord, Library.POS_X),
				new DirPos(this.xCoord - 2, this.yCoord, this.zCoord, Library.NEG_X),
				new DirPos(this.xCoord, this.yCoord, this.zCoord + 2, Library.POS_Z),
				new DirPos(this.xCoord, this.yCoord, this.zCoord - 2, Library.NEG_Z)
		};
	}

	@Override
	public String getConfigName() {
		return "derrick";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		TileEntityMachineOilWell.maxPower = IConfigurableMachine.grab(obj, "I:powerCap", TileEntityMachineOilWell.maxPower);
		TileEntityMachineOilWell.consumption = IConfigurableMachine.grab(obj, "I:consumption", TileEntityMachineOilWell.consumption);
		TileEntityMachineOilWell.delay = IConfigurableMachine.grab(obj, "I:delay", TileEntityMachineOilWell.delay);
		TileEntityMachineOilWell.oilPerDepsoit = IConfigurableMachine.grab(obj, "I:oilPerDeposit", TileEntityMachineOilWell.oilPerDepsoit);
		TileEntityMachineOilWell.gasPerDepositMin = IConfigurableMachine.grab(obj, "I:gasPerDepositMin", TileEntityMachineOilWell.gasPerDepositMin);
		TileEntityMachineOilWell.gasPerDepositMax = IConfigurableMachine.grab(obj, "I:gasPerDepositMax", TileEntityMachineOilWell.gasPerDepositMax);
		TileEntityMachineOilWell.drainChance = IConfigurableMachine.grab(obj, "D:drainChance", TileEntityMachineOilWell.drainChance);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("I:powerCap").value(TileEntityMachineOilWell.maxPower);
		writer.name("I:consumption").value(TileEntityMachineOilWell.consumption);
		writer.name("I:delay").value(TileEntityMachineOilWell.delay);
		writer.name("I:oilPerDeposit").value(TileEntityMachineOilWell.oilPerDepsoit);
		writer.name("I:gasPerDepositMin").value(TileEntityMachineOilWell.gasPerDepositMin);
		writer.name("I:gasPerDepositMax").value(TileEntityMachineOilWell.gasPerDepositMax);
		writer.name("D:drainChance").value(TileEntityMachineOilWell.drainChance);
	}
	
	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineOilWell(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineOilWell(player.inventory, this);
	}
}
