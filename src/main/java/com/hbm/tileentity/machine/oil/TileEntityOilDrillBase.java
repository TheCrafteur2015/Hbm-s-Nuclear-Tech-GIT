package com.hbm.tileentity.machine.oil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IPersistentNBT;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.BobMathUtil;
import com.hbm.util.Tuple;
import com.hbm.util.Tuple.Triplet;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityOilDrillBase extends TileEntityMachineBase implements IEnergyUser, IFluidSource, IFluidStandardTransceiver, IConfigurableMachine, IPersistentNBT, IGUIProvider {

	public int indicator = 0;
	
	public long power;
	
	public List<IFluidAcceptor> list1 = new ArrayList<>();
	public List<IFluidAcceptor> list2 = new ArrayList<>();
	public FluidTank[] tanks;

	public TileEntityOilDrillBase() {
		super(8);
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.OIL, 64_000, 0);
		this.tanks[1] = new FluidTank(Fluids.GAS, 64_000, 1);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.power = nbt.getLong("power");
		for(int i = 0; i < this.tanks.length; i++)
			this.tanks[i].readFromNBT(nbt, "t" + i);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", this.power);
		for(int i = 0; i < this.tanks.length; i++)
			this.tanks[i].writeToNBT(nbt, "t" + i);
	}

	@Override
	public void writeNBT(NBTTagCompound nbt) {
		
		boolean empty = this.power == 0;
		for(FluidTank tank : this.tanks) if(tank.getFill() > 0) empty = false;
		
		if(!empty) {
			nbt.setLong("power", this.power);
			for(int i = 0; i < this.tanks.length; i++) {
				this.tanks[i].writeToNBT(nbt, "t" + i);
			}
		}
	}

	@Override
	public void readNBT(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		for(int i = 0; i < this.tanks.length; i++)
			this.tanks[i].readFromNBT(nbt, "t" + i);
	}

	public int speedLevel;
	public int energyLevel;
	public int overLevel;

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			updateConnections();
			
			this.tanks[0].unloadTank(1, 2, this.slots);
			this.tanks[1].unloadTank(3, 4, this.slots);
			
			UpgradeManager.eval(this.slots, 5, 7);
			this.speedLevel = Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3);
			this.energyLevel = Math.min(UpgradeManager.getLevel(UpgradeType.POWER), 3);
			this.overLevel = Math.min(UpgradeManager.getLevel(UpgradeType.OVERDRIVE), 3) + 1;
			int abLevel = Math.min(UpgradeManager.getLevel(UpgradeType.AFTERBURN), 3);
			
			for (FluidTank tank : this.tanks) {
				tank.updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
			}
			
			int toBurn = Math.min(this.tanks[1].getFill(), abLevel * 10);
			
			if(toBurn > 0) {
				this.tanks[1].setFill(this.tanks[1].getFill() - toBurn);
				this.power += toBurn * 5;
				
				if(this.power > getMaxPower())
					this.power = getMaxPower();
			}
			
			this.power = Library.chargeTEFromItems(this.slots, 0, this.power, getMaxPower());

			if(this.worldObj.getTotalWorldTime() % 10 == 0)
				fillFluidInit(this.tanks[0].getTankType());
			if(this.worldObj.getTotalWorldTime() % 10 == 5)
				fillFluidInit(this.tanks[1].getTankType());
			
			for(DirPos pos : getConPos()) {
				if(this.tanks[0].getFill() > 0) this.sendFluid(this.tanks[0], this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				if(this.tanks[1].getFill() > 0) this.sendFluid(this.tanks[1], this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
			
			if(this.power >= getPowerReqEff() && this.tanks[0].getFill() < this.tanks[0].getMaxFill() && this.tanks[1].getFill() < this.tanks[1].getMaxFill()) {
				
				this.power -= getPowerReqEff();
				
				if(this.worldObj.getTotalWorldTime() % getDelayEff() == 0) {
					this.indicator = 0;
					
					for(int y = this.yCoord - 1; y >= getDrillDepth(); y--) {
						
						if(this.worldObj.getBlock(this.xCoord, y, this.zCoord) != ModBlocks.oil_pipe) {
						
							if(trySuck(y)) {
								break;
							} else {
								tryDrill(y);
								break;
							}
						}
						
						if(y == getDrillDepth())
							this.indicator = 1;
					}
				}
				
			} else {
				this.indicator = 2;
			}
			
			sendUpdate();
		}
	}
	
	public void sendUpdate() {
		NBTTagCompound data = new NBTTagCompound();
		data.setLong("power", this.power);
		data.setInteger("indicator", this.indicator);
		networkPack(data, 25);
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.indicator = nbt.getInteger("indicator");
	}
	
	public boolean canPump() {
		return true;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		super.setInventorySlotContents(i, stack);
		
		if(stack != null && i >= 5 && i <= 7 && stack.getItem() instanceof ItemMachineUpgrade)
			this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 1.5, this.zCoord + 0.5, "hbm:item.upgradePlug", 1.0F, 1.0F);
	}
	
	public int getPowerReqEff() {
		int req = getPowerReq();
		return (req + (req / 4 * this.speedLevel) - (req / 4 * this.energyLevel)) * this.overLevel;
	}
	
	public int getDelayEff() {
		int delay = getDelay();
		return Math.max((delay - (delay / 4 * this.speedLevel) + (delay / 10 * this.energyLevel)) / this.overLevel, 1);
	}
	
	public abstract int getPowerReq();
	public abstract int getDelay();
	
	public void tryDrill(int y) {
		Block b = this.worldObj.getBlock(this.xCoord, y, this.zCoord);
		
		if(b.getExplosionResistance(null) < 1000) {
			onDrill(y);
			this.worldObj.setBlock(this.xCoord, y, this.zCoord, ModBlocks.oil_pipe);
		} else {
			this.indicator = 2;
		}
	}
	
	public void onDrill(int y) { }
	
	public int getDrillDepth() {
		return 5;
	}
	
	public boolean trySuck(int y) {
		
		Block b = this.worldObj.getBlock(this.xCoord, y, this.zCoord);
		
		if(!canSuckBlock(b))
			return false;
		
		if(!canPump())
			return true;
		
		this.trace.clear();
		
		return suckRec(this.xCoord, y, this.zCoord, 0);
	}
	
	public boolean canSuckBlock(Block b) {
		return b == ModBlocks.ore_oil || b == ModBlocks.ore_oil_empty;
	}
	
	protected HashSet<Tuple.Triplet<Integer, Integer, Integer>> trace = new HashSet<>();
	
	public boolean suckRec(int x, int y, int z, int layer) {
		
		Triplet<Integer, Integer, Integer> pos = new Triplet<>(x, y, z);
		
		if(this.trace.contains(pos))
			return false;
		
		this.trace.add(pos);
		
		if(layer > 64)
			return false;
		
		Block b = this.worldObj.getBlock(x, y, z);
		
		if(b == ModBlocks.ore_oil || b == ModBlocks.ore_bedrock_oil) {
			doSuck(x, y, z);
			return true;
		}
		
		if(b == ModBlocks.ore_oil_empty) {
			ForgeDirection[] dirs = BobMathUtil.getShuffledDirs();
			
			for(ForgeDirection dir : dirs) {
				if(suckRec(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, layer + 1))
					return true;
			}
		}
		
		return false;
	}
	
	public void doSuck(int x, int y, int z) {
		
		if(this.worldObj.getBlock(x, y, z) == ModBlocks.ore_oil) {
			onSuck(x, y, z);
		}
	}
	
	public abstract void onSuck(int x, int y, int z);

	@Override
	public boolean getTact() {
		return this.worldObj.getTotalWorldTime() % 20 < 10;
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, this.worldObj, type);
	}

	@Override
	public int getFluidFill(FluidType type) {
		for(FluidTank tank : this.tanks) {
			if(type == tank.getTankType()) {
				return tank.getFill();
			}
		}
		
		return 0;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		for(FluidTank tank : this.tanks) {
			if(type == tank.getTankType()) {
				tank.setFill(i);
				return;
			}
		}
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		if(type == this.tanks[0].getTankType()) return this.list1;
		if(type == this.tanks[1].getTankType()) return this.list2;
		return new ArrayList<>();
	}

	@Override
	public void clearFluidList(FluidType type) {
		if(type == this.tanks[0].getTankType()) this.list1.clear();
		if(type == this.tanks[1].getTankType()) this.list2.clear();
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if(index < this.tanks.length && this.tanks[index] != null)
			this.tanks[index].setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if(index < this.tanks.length && this.tanks[index] != null)
			this.tanks[index].setTankType(type);
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return this.tanks;
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[0];
	}

	@Override
	public FluidTank[] getAllTanks() {
		return this.tanks;
	}
	
	public abstract DirPos[] getConPos();

	protected void updateConnections() {
		for(DirPos pos : getConPos()) {
			this.trySubscribe(this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
}
