package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Coolable;
import com.hbm.inventory.fluid.trait.FT_Coolable.CoolingType;
import com.hbm.lib.Library;
import com.hbm.packet.NBTPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyGenerator;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityChungus extends TileEntityLoadedBase implements IFluidAcceptor, IFluidSource, IEnergyGenerator, INBTPacketReceiver, IFluidStandardTransceiver {

	public long power;
	public static final long maxPower = 100000000000L;
	private int turnTimer;
	public float rotor;
	public float lastRotor;
	public float fanAcceleration = 0F;
	
	public List<IFluidAcceptor> list2 = new ArrayList<>();
	
	public FluidTank[] tanks;
	
	public TileEntityChungus() {
		
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.STEAM, 1000000000);
		this.tanks[1] = new FluidTank(Fluids.SPENTSTEAM, 1000000000);
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			boolean operational = false;
			FluidType in = this.tanks[0].getTankType();
			boolean valid = false;
			if(in.hasTrait(FT_Coolable.class)) {
				FT_Coolable trait = in.getTrait(FT_Coolable.class);
				double eff = trait.getEfficiency(CoolingType.TURBINE) * 0.85D; //85% efficiency
				if(eff > 0) {
					this.tanks[1].setTankType(trait.coolsTo);
					int inputOps = this.tanks[0].getFill() / trait.amountReq;
					int outputOps = (this.tanks[1].getMaxFill() - this.tanks[1].getFill()) / trait.amountProduced;
					int ops = Math.min(inputOps, outputOps);
					this.tanks[0].setFill(this.tanks[0].getFill() - ops * trait.amountReq);
					this.tanks[1].setFill(this.tanks[1].getFill() + ops * trait.amountProduced);
					this.power += (ops * trait.heatEnergy * eff);
					valid = true;
					operational = ops > 0;
				}
			}
			
			if(!valid) this.tanks[1].setTankType(Fluids.NONE);
			if(this.power > TileEntityChungus.maxPower) this.power = TileEntityChungus.maxPower;
			
			ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
			sendPower(this.worldObj, this.xCoord - dir.offsetX * 11, this.yCoord, this.zCoord - dir.offsetZ * 11, dir.getOpposite());
			
			for(DirPos pos : getConPos()) {
				this.sendFluid(this.tanks[1], this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				this.trySubscribe(this.tanks[0].getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
			
			if(this.power > TileEntityChungus.maxPower)
				this.power = TileEntityChungus.maxPower;
			
			this.turnTimer--;
			
			if(operational)
				this.turnTimer = 25;
			
			fillFluidInit(this.tanks[1].getTankType());
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setInteger("type", this.tanks[0].getTankType().getID());
			data.setInteger("operational", this.turnTimer);
			networkPack(data, 150);
			
		} else {
			
			this.lastRotor = this.rotor;
			this.rotor += this.fanAcceleration;
				
			if(this.rotor >= 360) {
				this.rotor -= 360;
				this.lastRotor -= 360;
			}
			
			if(this.turnTimer > 0) {

				this.fanAcceleration = Math.max(0F, Math.min(25F, this.fanAcceleration += 0.1F));

				Random rand = this.worldObj.rand;
				ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
				ForgeDirection side = dir.getRotation(ForgeDirection.UP);
				
				for(int i = 0; i < 10; i++) {
					this.worldObj.spawnParticle("cloud",
							this.xCoord + 0.5 + dir.offsetX * (rand.nextDouble() + 1.25) + rand.nextGaussian() * side.offsetX * 0.65,
							this.yCoord + 2.5 + rand.nextGaussian() * 0.65,
							this.zCoord + 0.5 + dir.offsetZ * (rand.nextDouble() + 1.25) + rand.nextGaussian() * side.offsetZ * 0.65,
							-dir.offsetX * 0.2, 0, -dir.offsetZ * 0.2);
				}
			}
			if(this.turnTimer < 0) {
				this.fanAcceleration = Math.max(0F, Math.min(25F, this.fanAcceleration -= 0.1F));
			}	
		}
	}
	
	public void onLeverPull(FluidType previous) {
		for(BlockPos pos : getConPos()) {
			this.tryUnsubscribe(previous, this.worldObj, pos.getX(), pos.getY(), pos.getZ());
		}
	}
	
	public DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		return new DirPos[] {
				new DirPos(this.xCoord + dir.offsetX * 5, this.yCoord + 2, this.zCoord + dir.offsetZ * 5, dir),
				new DirPos(this.xCoord + rot.offsetX * 3, this.yCoord, this.zCoord + rot.offsetZ * 3, rot),
				new DirPos(this.xCoord - rot.offsetX * 3, this.yCoord, this.zCoord - rot.offsetZ * 3, rot.getOpposite())
		};
	}
	
	public void networkPack(NBTTagCompound nbt, int range) {
		PacketDispatcher.wrapper.sendToAllAround(new NBTPacket(nbt, this.xCoord, this.yCoord, this.zCoord), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, range));
	}

	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.power = data.getLong("power");
		this.turnTimer = data.getInteger("operational");
		this.tanks[0].setTankType(Fluids.fromID(data.getInteger("type")));
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

		fillFluid(this.xCoord + dir.offsetX * 3, this.yCoord, this.zCoord + dir.offsetZ * 3, getTact(), type);
		fillFluid(this.xCoord + dir.offsetX * -3, this.yCoord, this.zCoord + dir.offsetZ * -3, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, this.worldObj, type);
	}
	
	@Override
	public boolean getTact() {
		return this.worldObj.getTotalWorldTime() % 2 == 0;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type == this.tanks[0].getTankType())
			this.tanks[0].setFill(i);
		else if(type == this.tanks[1].getTankType())
			this.tanks[1].setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type == this.tanks[0].getTankType())
			return this.tanks[0].getFill();
		else if(type == this.tanks[1].getTankType())
			return this.tanks[1].getFill();
		
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type == this.tanks[0].getTankType())
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
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.UP && dir != ForgeDirection.DOWN && dir != ForgeDirection.UNKNOWN;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return TileEntityChungus.maxPower;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
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
	public FluidTank[] getAllTanks() {
		return this.tanks;
	}
}
