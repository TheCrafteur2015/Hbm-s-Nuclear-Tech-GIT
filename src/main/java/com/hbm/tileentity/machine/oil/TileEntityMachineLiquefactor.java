package com.hbm.tileentity.machine.oil;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerLiquefactor;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUILiquefactor;
import com.hbm.inventory.recipes.LiquefactionRecipes;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardSender;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityMachineLiquefactor extends TileEntityMachineBase implements IEnergyUser, IFluidSource, IFluidStandardSender, IGUIProvider {

	public long power;
	public static final long maxPower = 100000;
	public static final int usageBase = 500;
	public int usage;
	public int progress;
	public static final int processTimeBase = 200;
	public int processTime;
	
	public FluidTank tank;
	
	public TileEntityMachineLiquefactor() {
		super(4);
		this.tank = new FluidTank(Fluids.NONE, 24000, 0);
	}

	@Override
	public String getName() {
		return "container.machineLiquefactor";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			this.power = Library.chargeTEFromItems(this.slots, 1, this.power, TileEntityMachineLiquefactor.maxPower);
			this.tank.updateTank(this);
			
			updateConnections();

			UpgradeManager.eval(this.slots, 2, 3);
			int speed = Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3);
			int power = Math.min(UpgradeManager.getLevel(UpgradeType.POWER), 3);

			this.processTime = TileEntityMachineLiquefactor.processTimeBase - (TileEntityMachineLiquefactor.processTimeBase / 4) * speed;
			this.usage = (TileEntityMachineLiquefactor.usageBase + (TileEntityMachineLiquefactor.usageBase * speed)) / (power + 1);
			
			if(canProcess())
				process();
			else
				this.progress = 0;
			
			if(this.worldObj.getTotalWorldTime() % 10 == 0) {
				fillFluidInit(this.tank.getTankType());
			}
			
			this.sendFluid();
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setInteger("progress", this.progress);
			data.setInteger("usage", this.usage);
			data.setInteger("processTime", this.processTime);
			networkPack(data, 50);
		}
	}
	
	private void updateConnections() {
		for(DirPos pos : getConPos()) {
			this.trySubscribe(this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	private void sendFluid() {
		for(DirPos pos : getConPos()) {
			this.sendFluid(this.tank, this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	private DirPos[] getConPos() {
		return new DirPos[] {
			new DirPos(this.xCoord, this.yCoord + 4, this.zCoord, Library.POS_Y),
			new DirPos(this.xCoord, this.yCoord - 1, this.zCoord, Library.NEG_Y),
			new DirPos(this.xCoord + 2, this.yCoord + 1, this.zCoord, Library.POS_X),
			new DirPos(this.xCoord - 2, this.yCoord + 1, this.zCoord, Library.NEG_X),
			new DirPos(this.xCoord, this.yCoord + 1, this.zCoord + 2, Library.POS_Z),
			new DirPos(this.xCoord, this.yCoord + 1, this.zCoord - 2, Library.NEG_Z)
		};
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return i == 0 && LiquefactionRecipes.getOutput(itemStack) != null;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0 };
	}
	
	public boolean canProcess() {
		
		if((this.power < this.usage) || (this.slots[0] == null))
			return false;
		
		FluidStack out = LiquefactionRecipes.getOutput(this.slots[0]);
		
		if(out == null)
			return false;
		
		if(out.type != this.tank.getTankType() && this.tank.getFill() > 0)
			return false;
		
		if(out.fill + this.tank.getFill() > this.tank.getMaxFill())
			return false;
		
		return true;
	}
	
	public void process() {
		
		this.power -= this.usage;
		
		this.progress++;
		
		if(this.progress >= this.processTime) {
			
			FluidStack out = LiquefactionRecipes.getOutput(this.slots[0]);
			this.tank.setTankType(out.type);
			this.tank.setFill(this.tank.getFill() + out.fill);
			decrStackSize(0, 1);
			
			this.progress = 0;
			
			markDirty();
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.progress = nbt.getInteger("progress");
		this.usage = nbt.getInteger("usage");
		this.processTime = nbt.getInteger("processTime");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.tank.readFromNBT(nbt, "tank");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.tank.writeToNBT(nbt, "tank");
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
		return TileEntityMachineLiquefactor.maxPower;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		this.tank.setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		if(type == this.tank.getTankType())
			this.tank.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		this.tank.setTankType(type);
	}

	@Override
	public int getFluidFill(FluidType type) {
		return type == this.tank.getTankType() ? this.tank.getFill() : 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(this.xCoord, this.yCoord - 1, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord + 4, this.zCoord, getTact(), type);
		fillFluid(this.xCoord + 2, this.yCoord + 1, this.zCoord, getTact(), type);
		fillFluid(this.xCoord - 2, this.yCoord + 1, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord + 1, this.zCoord + 2, getTact(), type);
		fillFluid(this.xCoord, this.yCoord + 1, this.zCoord - 2, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, this.worldObj, type);
	}

	@Override
	public boolean getTact() {
		return this.worldObj.getTotalWorldTime() % 20 < 10;
	}

	private List<IFluidAcceptor> consumers = new ArrayList<>();
	
	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return this.consumers;
	}

	@Override
	public void clearFluidList(FluidType type) {
		this.consumers.clear();
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
					this.yCoord + 4,
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

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { this.tank };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { this.tank };
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerLiquefactor(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUILiquefactor(player.inventory, this);
	}
}
