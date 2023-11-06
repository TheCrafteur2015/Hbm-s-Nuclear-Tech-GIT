package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.container.ContainerMachineLargeTurbine;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Coolable;
import com.hbm.inventory.fluid.trait.FT_Coolable.CoolingType;
import com.hbm.inventory.gui.GUIMachineLargeTurbine;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyGenerator;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineLargeTurbine extends TileEntityMachineBase implements IFluidContainer, IFluidAcceptor, IFluidSource, IEnergyGenerator, IFluidStandardTransceiver, IGUIProvider {

	public long power;
	public static final long maxPower = 100000000;
	public int age = 0;
	public List<IFluidAcceptor> list2 = new ArrayList<>();
	public FluidTank[] tanks;
	
	private boolean shouldTurn;
	public float rotor;
	public float lastRotor;
	public float fanAcceleration = 0F;
	
	public TileEntityMachineLargeTurbine() {
		super(7);
		
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.STEAM, 512000);
		this.tanks[1] = new FluidTank(Fluids.SPENTSTEAM, 10240000);
	}

	@Override
	public String getName() {
		return "container.machineLargeTurbine";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			this.age++;
			if(this.age >= 2)
			{
				this.age = 0;
			}
			
			fillFluidInit(this.tanks[1].getTankType());
			
			ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
			sendPower(this.worldObj, this.xCoord + dir.offsetX * -4, this.yCoord, this.zCoord + dir.offsetZ * -4, dir.getOpposite());
			for(DirPos pos : getConPos()) this.trySubscribe(this.tanks[0].getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			for(DirPos pos : getConPos()) this.sendFluid(this.tanks[1], this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());

			this.tanks[0].setType(0, 1, this.slots);
			this.tanks[0].loadTank(2, 3, this.slots);
			this.power = Library.chargeItemsFromTE(this.slots, 4, this.power, TileEntityMachineLargeTurbine.maxPower);
			
			boolean operational = false;
			
			FluidType in = this.tanks[0].getTankType();
			boolean valid = false;
			if(in.hasTrait(FT_Coolable.class)) {
				FT_Coolable trait = in.getTrait(FT_Coolable.class);
				double eff = trait.getEfficiency(CoolingType.TURBINE); //100% efficiency
				if(eff > 0) {
					this.tanks[1].setTankType(trait.coolsTo);
					int inputOps = (int) Math.floor(this.tanks[0].getFill() / trait.amountReq); //amount of cycles possible with the entire input buffer
					int outputOps = (this.tanks[1].getMaxFill() - this.tanks[1].getFill()) / trait.amountProduced; //amount of cycles possible with the output buffer's remaining space
					int cap = (int) Math.ceil(this.tanks[0].getFill() / trait.amountReq / 5F); //amount of cycles by the "at least 20%" rule
					int ops = Math.min(inputOps, Math.min(outputOps, cap)); //defacto amount of cycles
					this.tanks[0].setFill(this.tanks[0].getFill() - ops * trait.amountReq);
					this.tanks[1].setFill(this.tanks[1].getFill() + ops * trait.amountProduced);
					this.power += (ops * trait.heatEnergy * eff);
					valid = true;
					operational = ops > 0;
				}
			}
			if(!valid) this.tanks[1].setTankType(Fluids.NONE);
			if(this.power > TileEntityMachineLargeTurbine.maxPower) this.power = TileEntityMachineLargeTurbine.maxPower;
			
			this.tanks[1].unloadTank(5, 6, this.slots);
			
			for(int i = 0; i < 2; i++)
				this.tanks[i].updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setBoolean("operational", operational);
			networkPack(data, 50);
		} else {
			this.lastRotor = this.rotor;
			this.rotor += this.fanAcceleration;
				
			if(this.rotor >= 360) {
				this.rotor -= 360;
				this.lastRotor -= 360;
			}
			
			if(this.shouldTurn) {

				this.fanAcceleration = Math.max(0F, Math.min(15F, this.fanAcceleration += 0.1F));
			}
			if(!this.shouldTurn) {
				this.fanAcceleration = Math.max(0F, Math.min(15F, this.fanAcceleration -= 0.1F));
			}
		}
	}
	
	protected DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		return new DirPos[] {
				new DirPos(this.xCoord + rot.offsetX * 2, this.yCoord, this.zCoord + rot.offsetZ * 2, rot),
				new DirPos(this.xCoord - rot.offsetX * 2, this.yCoord, this.zCoord - rot.offsetZ * 2, rot.getOpposite()),
				new DirPos(this.xCoord + dir.offsetX * 2, this.yCoord, this.zCoord + dir.offsetZ * 2, dir)
		};
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.power = data.getLong("power");
		this.shouldTurn = data.getBoolean("operational");
	}
	
	public long getPowerScaled(int i) {
		return (this.power * i) / TileEntityMachineLargeTurbine.maxPower;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.tanks[0].readFromNBT(nbt, "water");
		this.tanks[1].readFromNBT(nbt, "steam");
		this.power = nbt.getLong("power");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.tanks[0].writeToNBT(nbt, "water");
		this.tanks[1].writeToNBT(nbt, "steam");
		nbt.setLong("power", this.power);
	}

	@Override
	public void fillFluidInit(FluidType type) {
		
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
		dir = dir.getRotation(ForgeDirection.UP);

		fillFluid(this.xCoord + dir.offsetX * 2, this.yCoord, this.zCoord + dir.offsetZ * 2, getTact(), type);
		fillFluid(this.xCoord + dir.offsetX * -2, this.yCoord, this.zCoord + dir.offsetZ * -2, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, this.worldObj, type);
	}
	
	@Override
	public boolean getTact() {
		if(this.age == 0)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type.getName().equals(this.tanks[0].getTankType().getName()))
			this.tanks[0].setFill(i);
		else if(type.getName().equals(this.tanks[1].getTankType().getName()))
			this.tanks[1].setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type.getName().equals(this.tanks[0].getTankType().getName()))
			return this.tanks[0].getFill();
		else if(type.getName().equals(this.tanks[1].getTankType().getName()))
			return this.tanks[1].getFill();
		
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type.getName().equals(this.tanks[0].getTankType().getName()))
			return this.tanks[0].getMaxFill();
		return 0;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if(index < 2 && this.tanks[index] != null)
			this.tanks[index].setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if(index < 2 && this.tanks[index] != null)
			this.tanks[index].setTankType(type);
	}
	
	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return this.list2;
	}
	
	@Override
	public void clearFluidList(FluidType type) {
		this.list2.clear();
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getMaxPower() {
		return TileEntityMachineLargeTurbine.maxPower;
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
	public FluidTank[] getAllTanks() {
		return this.tanks;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {this.tanks[1]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {this.tanks[0]};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineLargeTurbine(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineLargeTurbine(player.inventory, this);
	}
}
