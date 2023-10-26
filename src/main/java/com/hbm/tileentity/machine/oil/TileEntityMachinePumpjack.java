package com.hbm.tileentity.machine.oil;

import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.container.ContainerMachineOilWell;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.gui.GUIMachineOilWell;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityMachinePumpjack extends TileEntityOilDrillBase {

	protected static int maxPower = 250_000;
	protected static int consumption = 200;
	protected static int delay = 25;
	protected static int oilPerDepsoit = 750;
	protected static int gasPerDepositMin = 50;
	protected static int gasPerDepositMax = 250;
	protected static double drainChance = 0.025D;
	
	public float rot = 0;
	public float prevRot = 0;
	public float speed = 0;

	@Override
	public String getName() {
		return "container.pumpjack";
	}

	@Override
	public long getMaxPower() {
		return TileEntityMachinePumpjack.maxPower;
	}

	@Override
	public int getPowerReq() {
		return TileEntityMachinePumpjack.consumption;
	}

	@Override
	public int getDelay() {
		return TileEntityMachinePumpjack.delay;
	}

	@Override
	public void onDrill(int y) {
		Block b = this.worldObj.getBlock(this.xCoord, y, this.zCoord);
		ItemStack stack = new ItemStack(b);
		int[] ids = OreDictionary.getOreIDs(stack);
		for(Integer i : ids) {
			String name = OreDictionary.getOreName(i);
			
			if("oreUranium".equals(name)) {
				for(int j = 2; j < 6; j++) {
					ForgeDirection dir = ForgeDirection.getOrientation(j);
					if(this.worldObj.getBlock(this.xCoord + dir.offsetX, this.yCoord, this.zCoord + dir.offsetZ).isReplaceable(this.worldObj, this.xCoord + dir.offsetX, this.yCoord, this.zCoord + dir.offsetZ)) {
						this.worldObj.setBlock(this.xCoord + dir.offsetX, this.yCoord, this.zCoord + dir.offsetZ, ModBlocks.gas_radon_dense);
					}
				}
			}
			
			if("oreAsbestos".equals(name)) {
				for(int j = 2; j < 6; j++) {
					ForgeDirection dir = ForgeDirection.getOrientation(j);
					if(this.worldObj.getBlock(this.xCoord + dir.offsetX, this.yCoord, this.zCoord + dir.offsetZ).isReplaceable(this.worldObj, this.xCoord + dir.offsetX, this.yCoord, this.zCoord + dir.offsetZ)) {
						this.worldObj.setBlock(this.xCoord + dir.offsetX, this.yCoord, this.zCoord + dir.offsetZ, ModBlocks.gas_asbestos);
					}
				}
			}
		}
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(this.worldObj.isRemote) {

			this.prevRot = this.rot;
			
			if(this.indicator == 0) {
				this.rot += this.speed;
			}
			
			if(this.rot >= 360) {
				this.prevRot -= 360;
				this.rot -= 360;
			}
		}
	}
	
	@Override
	public void sendUpdate() {
		NBTTagCompound data = new NBTTagCompound();
		data.setLong("power", this.power);
		data.setInteger("indicator", this.indicator);
		data.setFloat("speed", this.indicator == 0 ? (5F + (2F * this.speedLevel)) + (this.overLevel - 1F) * 10: 0F);
		networkPack(data, 25);
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.indicator = nbt.getInteger("indicator");
		this.speed = nbt.getFloat("speed");
	}

	@Override
	public void onSuck(int x, int y, int z) {
		
		this.tanks[0].setFill(this.tanks[0].getFill() + TileEntityMachinePumpjack.oilPerDepsoit);
		if(this.tanks[0].getFill() > this.tanks[0].getMaxFill()) this.tanks[0].setFill(this.tanks[0].getMaxFill());
		this.tanks[1].setFill(this.tanks[1].getFill() + (TileEntityMachinePumpjack.gasPerDepositMin + this.worldObj.rand.nextInt((TileEntityMachinePumpjack.gasPerDepositMax - TileEntityMachinePumpjack.gasPerDepositMin + 1))));
		if(this.tanks[1].getFill() > this.tanks[1].getMaxFill()) this.tanks[1].setFill(this.tanks[1].getMaxFill());
		
		if(this.worldObj.rand.nextDouble() < TileEntityMachinePumpjack.drainChance) {
			this.worldObj.setBlock(x, y, z, ModBlocks.ore_oil_empty);
		}
	}

	@Override
	public void fillFluidInit(FluidType type) {
		
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);

		int pX2 = this.xCoord + rot.offsetX * 2;
		int pZ2 = this.zCoord + rot.offsetZ * 2;
		int pX4 = this.xCoord + rot.offsetX * 4;
		int pZ4 = this.zCoord + rot.offsetZ * 4;
		int oX = Math.abs(dir.offsetX) * 2;
		int oZ = Math.abs(dir.offsetZ) * 2;
		
		fillFluid(pX2 + oX, this.yCoord, pZ2 + oZ, getTact(), type);
		fillFluid(pX2 - oX, this.yCoord, pZ2 - oZ, getTact(), type);
		fillFluid(pX4 + oX, this.yCoord, pZ4 + oZ, getTact(), type);
		fillFluid(pX4 - oX, this.yCoord, pZ4 - oZ, getTact(), type);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(
					this.xCoord - 7,
					this.yCoord,
					this.zCoord - 7,
					this.xCoord + 8,
					this.yCoord + 6,
					this.zCoord + 8
					);
		}
		
		return this.bb;
	}

	@Override
	public DirPos[] getConPos() {
		getBlockMetadata();
		ForgeDirection dir = ForgeDirection.getOrientation(this.blockMetadata - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);
		
		return new DirPos[] {
			new DirPos(this.xCoord + rot.offsetX * 2 + dir.offsetX * 2, this.yCoord, this.zCoord + rot.offsetZ * 2 + dir.offsetZ * 2, dir),
			new DirPos(this.xCoord + rot.offsetX * 2 + dir.offsetX * 2, this.yCoord, this.zCoord + rot.offsetZ * 4 - dir.offsetZ * 2, dir.getOpposite()),
			new DirPos(this.xCoord + rot.offsetX * 4 - dir.offsetX * 2, this.yCoord, this.zCoord + rot.offsetZ * 4 + dir.offsetZ * 2, dir),
			new DirPos(this.xCoord + rot.offsetX * 4 - dir.offsetX * 2, this.yCoord, this.zCoord + rot.offsetZ * 2 - dir.offsetZ * 2, dir.getOpposite())
		};
	}

	@Override
	public String getConfigName() {
		return "pumpjack";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		TileEntityMachinePumpjack.maxPower = IConfigurableMachine.grab(obj, "I:powerCap", TileEntityMachinePumpjack.maxPower);
		TileEntityMachinePumpjack.consumption = IConfigurableMachine.grab(obj, "I:consumption", TileEntityMachinePumpjack.consumption);
		TileEntityMachinePumpjack.delay = IConfigurableMachine.grab(obj, "I:delay", TileEntityMachinePumpjack.delay);
		TileEntityMachinePumpjack.oilPerDepsoit = IConfigurableMachine.grab(obj, "I:oilPerDeposit", TileEntityMachinePumpjack.oilPerDepsoit);
		TileEntityMachinePumpjack.gasPerDepositMin = IConfigurableMachine.grab(obj, "I:gasPerDepositMin", TileEntityMachinePumpjack.gasPerDepositMin);
		TileEntityMachinePumpjack.gasPerDepositMax = IConfigurableMachine.grab(obj, "I:gasPerDepositMax", TileEntityMachinePumpjack.gasPerDepositMax);
		TileEntityMachinePumpjack.drainChance = IConfigurableMachine.grab(obj, "D:drainChance", TileEntityMachinePumpjack.drainChance);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("I:powerCap").value(TileEntityMachinePumpjack.maxPower);
		writer.name("I:consumption").value(TileEntityMachinePumpjack.consumption);
		writer.name("I:delay").value(TileEntityMachinePumpjack.delay);
		writer.name("I:oilPerDeposit").value(TileEntityMachinePumpjack.oilPerDepsoit);
		writer.name("I:gasPerDepositMin").value(TileEntityMachinePumpjack.gasPerDepositMin);
		writer.name("I:gasPerDepositMax").value(TileEntityMachinePumpjack.gasPerDepositMax);
		writer.name("D:drainChance").value(TileEntityMachinePumpjack.drainChance);
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
