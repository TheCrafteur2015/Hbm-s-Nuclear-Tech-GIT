package com.hbm.tileentity.machine;

import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.container.ContainerMachineWoodBurner;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.inventory.gui.GUIMachineWoodBurner;
import com.hbm.items.ItemEnums.EnumAshType;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.module.ModuleBurnTime;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyGenerator;
import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineWoodBurner extends TileEntityMachineBase implements IFluidStandardReceiver, IControlReceiver, IEnergyGenerator, IGUIProvider {

	public long power;
	public static final long maxPower = 100_000;
	public int burnTime;
	public int maxBurnTime;
	public boolean liquidBurn = false;
	public boolean isOn = false;

	public FluidTank tank;

	public static ModuleBurnTime burnModule = new ModuleBurnTime().setLogTimeMod(4).setWoodTimeMod(2);

	public int ashLevelWood;
	public int ashLevelCoal;
	public int ashLevelMisc;

	public TileEntityMachineWoodBurner() {
		super(6);
		this.tank = new FluidTank(Fluids.WOODOIL, 16_000);
	}

	@Override
	public String getName() {
		return "container.machineWoodBurner";
	}

	@Override
	public void updateEntity() {

		if(!this.worldObj.isRemote) {

			this.tank.setType(2, this.slots);
			this.tank.loadTank(3, 4, this.slots);
			this.power = Library.chargeItemsFromTE(this.slots, 5, this.power, TileEntityMachineWoodBurner.maxPower);

			for(DirPos pos : this.getConPos()) {
				if(this.power > 0) this.sendPower(this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				if(this.worldObj.getTotalWorldTime() % 20 == 0) this.trySubscribe(this.tank.getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}

			if(!this.liquidBurn) {

				if(this.burnTime <= 0) {

					if(this.slots[0] != null) {
						int burn = TileEntityMachineWoodBurner.burnModule.getBurnTime(this.slots[0]);
						if(burn > 0) {
							EnumAshType type = TileEntityFireboxBase.getAshFromFuel(this.slots[0]);
							if(type == EnumAshType.WOOD) this.ashLevelWood += burn;
							if(type == EnumAshType.COAL) this.ashLevelCoal += burn;
							if(type == EnumAshType.MISC) this.ashLevelMisc += burn;
							int threshold = 2000;
							if(this.processAsh(this.ashLevelWood, EnumAshType.WOOD, threshold)) this.ashLevelWood -= threshold;
							if(this.processAsh(this.ashLevelCoal, EnumAshType.COAL, threshold)) this.ashLevelCoal -= threshold;
							if(this.processAsh(this.ashLevelMisc, EnumAshType.MISC, threshold)) this.ashLevelMisc -= threshold;

							this.maxBurnTime = this.burnTime = burn;
							this.decrStackSize(0, 1);
							this.markChanged();
						}
					}

				} else if(this.power < TileEntityMachineWoodBurner.maxPower && this.isOn){
					this.burnTime--;
					this.power += 100;
					if(this.power > TileEntityMachineWoodBurner.maxPower) this.power = TileEntityMachineWoodBurner.maxPower;
					if(this.worldObj.getTotalWorldTime() % 20 == 0) PollutionHandler.incrementPollution(this.worldObj, this.xCoord, this.yCoord, this.zCoord, PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND);
				}

			} else {

				if(this.power < TileEntityMachineWoodBurner.maxPower && this.tank.getFill() > 0 && this.isOn) {
					FT_Flammable trait = this.tank.getTankType().getTrait(FT_Flammable.class);

					if(trait != null) {

						int toBurn = Math.min(this.tank.getFill(), 2);

						if(toBurn > 0) {
							this.power += trait.getHeatEnergy() * toBurn / 2_000L;
							this.tank.setFill(this.tank.getFill() - toBurn);
							if(this.worldObj.getTotalWorldTime() % 20 == 0) PollutionHandler.incrementPollution(this.worldObj, this.xCoord, this.yCoord, this.zCoord, PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND * toBurn / 2F);
						}
					}
				}
			}

			if(this.power > TileEntityMachineWoodBurner.maxPower) this.power = TileEntityMachineWoodBurner.maxPower;

			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setInteger("burnTime", this.burnTime);
			data.setInteger("maxBurnTime", this.maxBurnTime);
			data.setBoolean("isOn", this.isOn);
			data.setBoolean("liquidBurn", this.liquidBurn);
			this.tank.writeToNBT(data, "t");
			this.networkPack(data, 25);
		} else {

			if(this.isOn && ((!this.liquidBurn && this.burnTime > 0) || (this.liquidBurn && this.tank.getTankType().hasTrait(FT_Flammable.class) && this.tank.getFill() > 0))) {
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
				ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
				this.worldObj.spawnParticle("smoke", this.xCoord + 0.5 - dir.offsetX + rot.offsetX, this.yCoord + 4, this.zCoord + 0.5 - dir.offsetZ + rot.offsetZ, 0, 0.05, 0);
			}
		}
	}

	private DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		return new DirPos[] {
				new DirPos(this.xCoord - dir.offsetX * 2, this.yCoord, this.zCoord - dir.offsetZ * 2, dir.getOpposite()),
				new DirPos(this.xCoord - dir.offsetX * 2 + rot.offsetX, this.yCoord, this.zCoord - dir.offsetZ * 2 + rot.offsetX, dir.getOpposite())
		};
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.burnTime = nbt.getInteger("burnTime");
		this.maxBurnTime = nbt.getInteger("maxBurnTime");
		this.isOn = nbt.getBoolean("isOn");
		this.liquidBurn = nbt.getBoolean("liquidBurn");
		this.tank.readFromNBT(nbt, "t");
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.power = nbt.getLong("power");
		this.burnTime = nbt.getInteger("burnTime");
		this.maxBurnTime = nbt.getInteger("maxBurnTime");
		this.isOn = nbt.getBoolean("isOn");
		this.liquidBurn = nbt.getBoolean("liquidBurn");
		this.tank.readFromNBT(nbt, "t");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", this.power);
		nbt.setInteger("burnTime", this.burnTime);
		nbt.setInteger("maxBurnTime", this.maxBurnTime);
		nbt.setBoolean("isOn", this.isOn);
		nbt.setBoolean("liquidBurn", this.liquidBurn);
		this.tank.writeToNBT(nbt, "t");
	}

	protected boolean processAsh(int level, EnumAshType type, int threshold) {

		if(level >= threshold) {
			if(this.slots[1] == null) {
				this.slots[1] = DictFrame.fromOne(ModItems.powder_ash, type);
				this.ashLevelWood -= threshold;
				return true;
			} else if(this.slots[1].stackSize < this.slots[1].getMaxStackSize() && this.slots[1].getItem() == ModItems.powder_ash && this.slots[1].getItemDamage() == type.ordinal()) {
				this.slots[1].stackSize++;
				return true;
			}
		}

		return false;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("toggle")) {
			this.isOn = !this.isOn;
			this.markChanged();
		}
		if(data.hasKey("switch")) {
			this.liquidBurn = !this.liquidBurn;
			this.markChanged();
		}
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineWoodBurner(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineWoodBurner(player.inventory, this);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int meta) {
		return new int[] { 0, 1 };
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return i == 0 && TileEntityMachineWoodBurner.burnModule.getBurnTime(itemStack) > 0;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
		return slot == 1;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return TileEntityMachineWoodBurner.maxPower;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		ForgeDirection rot = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		return dir == rot.getOpposite();
	}

	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		ForgeDirection rot = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		return dir == rot.getOpposite();
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {this.tank};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {this.tank};
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
					this.yCoord + 6,
					this.zCoord + 2
					);
		}

		return this.bb;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
