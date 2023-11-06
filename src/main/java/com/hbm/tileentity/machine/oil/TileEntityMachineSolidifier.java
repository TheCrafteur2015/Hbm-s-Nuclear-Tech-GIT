package com.hbm.tileentity.machine.oil;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerSolidifier;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUISolidifier;
import com.hbm.inventory.recipes.SolidificationRecipes;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.Tuple.Pair;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyUser;
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

public class TileEntityMachineSolidifier extends TileEntityMachineBase implements IEnergyUser, IFluidAcceptor, IFluidStandardReceiver, IGUIProvider {

	public long power;
	public static final long maxPower = 100000;
	public static final int usageBase = 500;
	public int usage;
	public int progress;
	public static final int processTimeBase = 200;
	public int processTime;
	
	public FluidTank tank;

	public TileEntityMachineSolidifier() {
		super(5);
		this.tank = new FluidTank(Fluids.NONE, 24000);
	}

	@Override
	public String getName() {
		return "container.machineSolidifier";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			this.power = Library.chargeTEFromItems(this.slots, 1, this.power, TileEntityMachineSolidifier.maxPower);
			this.tank.setType(4, this.slots);
			this.tank.updateTank(this);

			updateConnections();

			UpgradeManager.eval(this.slots, 2, 3);
			int speed = Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3);
			int power = Math.min(UpgradeManager.getLevel(UpgradeType.POWER), 3);

			this.processTime = TileEntityMachineSolidifier.processTimeBase - (TileEntityMachineSolidifier.processTimeBase / 4) * speed;
			this.usage = (TileEntityMachineSolidifier.usageBase + (TileEntityMachineSolidifier.usageBase * speed))  / (power + 1);
			
			if(canProcess())
				process();
			else
				this.progress = 0;
			
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
			this.trySubscribe(this.tank.getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
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
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot == 0;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0 };
	}
	
	public boolean canProcess() {
		
		if(this.power < this.usage)
			return false;
		
		Pair<Integer, ItemStack> out = SolidificationRecipes.getOutput(this.tank.getTankType());
		
		if(out == null)
			return false;
		
		int req = out.getKey();
		ItemStack stack = out.getValue();
		
		if(req > this.tank.getFill())
			return false;
		
		if(this.slots[0] != null) {
			
			if((this.slots[0].getItem() != stack.getItem()) || (this.slots[0].getItemDamage() != stack.getItemDamage()) || (this.slots[0].stackSize + stack.stackSize > this.slots[0].getMaxStackSize()))
				return false;
		}
		
		return true;
	}
	
	public void process() {
		
		this.power -= this.usage;
		
		this.progress++;
		
		if(this.progress >= this.processTime) {
			
			Pair<Integer, ItemStack> out = SolidificationRecipes.getOutput(this.tank.getTankType());
			int req = out.getKey();
			ItemStack stack = out.getValue();
			this.tank.setFill(this.tank.getFill() - req);
			
			if(this.slots[0] == null) {
				this.slots[0] = stack.copy();
			} else {
				this.slots[0].stackSize += stack.stackSize;
			}
			
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
		return TileEntityMachineSolidifier.maxPower;
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
		return this.tank.getTankType() == type ? this.tank.getFill() : 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		return this.tank.getTankType() == type ? this.tank.getMaxFill() : 0;
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
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { this.tank };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { this.tank };
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerSolidifier(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUISolidifier(player.inventory, this);
	}
}
