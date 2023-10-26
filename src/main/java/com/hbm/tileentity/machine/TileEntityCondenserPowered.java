package com.hbm.tileentity.machine;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyUser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCondenserPowered extends TileEntityCondenser implements IEnergyUser {
	
	public long power;
	public static final long maxPower = 10_000_000;
	public float spin;
	public float lastSpin;
	
	public TileEntityCondenserPowered() {
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.SPENTSTEAM, 100_000);
		this.tanks[1] = new FluidTank(Fluids.WATER, 100_000);
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(this.worldObj.isRemote) {
			
			this.lastSpin = this.spin;
			
			if(this.waterTimer > 0) {
				this.spin += 30F;
				
				if(this.spin >= 360F) {
					this.spin -= 360F;
					this.lastSpin -= 360F;
				}
				
				if(this.worldObj.getTotalWorldTime() % 4 == 0) {
					ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - 10);
					this.worldObj.spawnParticle("cloud", this.xCoord + 0.5 + dir.offsetX * 1.5, this.yCoord + 1.5, this.zCoord + 0.5 + dir.offsetZ * 1.5, dir.offsetX * 0.1, 0, dir.offsetZ * 0.1);
					this.worldObj.spawnParticle("cloud", this.xCoord + 0.5 - dir.offsetX * 1.5, this.yCoord + 1.5, this.zCoord + 0.5 - dir.offsetZ * 1.5, dir.offsetX * -0.1, 0, dir.offsetZ * -0.1);
				}
			}
		}
	}

	@Override
	public void packExtra(NBTTagCompound data) {
		data.setLong("power", this.power);
	}
	
	@Override
	public boolean extraCondition(int convert) {
		return this.power >= convert * 10;
	}

	@Override
	public void postConvert(int convert) {
		this.power -= convert * 10;
		if(this.power < 0) this.power = 0;
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.tanks[0].readFromNBT(nbt, "0");
		this.tanks[1].readFromNBT(nbt, "1");
		this.waterTimer = nbt.getByte("timer");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.power = nbt.getLong("power");
		this.tanks[0].readFromNBT(nbt, "water");
		this.tanks[1].readFromNBT(nbt, "steam");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", this.power);
		this.tanks[0].writeToNBT(nbt, "water");
		this.tanks[1].writeToNBT(nbt, "steam");
	}

	@Deprecated @Override public void fillFluidInit(FluidType type) { }

	@Override
	public void subscribeToAllAround(FluidType type, TileEntity te) {
		for(DirPos pos : getConPos()) {
			this.trySubscribe(this.tanks[0].getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}

	@Override
	public void sendFluidToAll(FluidTank tank, TileEntity te) {
		for(DirPos pos : getConPos()) {
			this.sendFluid(this.tanks[1], this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	public DirPos[] getConPos() {
		
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		return new DirPos[] {
				new DirPos(this.xCoord + rot.offsetX * 4, this.yCoord + 1, this.zCoord + rot.offsetZ * 4, rot),
				new DirPos(this.xCoord - rot.offsetX * 4, this.yCoord + 1, this.zCoord - rot.offsetZ * 4, rot.getOpposite()),
				new DirPos(this.xCoord + dir.offsetX * 2 - rot.offsetX, this.yCoord + 1, this.zCoord + dir.offsetZ * 2 - rot.offsetZ, dir),
				new DirPos(this.xCoord + dir.offsetX * 2 + rot.offsetX, this.yCoord + 1, this.zCoord + dir.offsetZ * 2 + rot.offsetZ, dir),
				new DirPos(this.xCoord - dir.offsetX * 2 - rot.offsetX, this.yCoord + 1, this.zCoord - dir.offsetZ * 2 - rot.offsetZ, dir.getOpposite()),
				new DirPos(this.xCoord - dir.offsetX * 2 + rot.offsetX, this.yCoord + 1, this.zCoord - dir.offsetZ * 2 + rot.offsetZ, dir.getOpposite())
		};
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(
					this.xCoord - 3,
					this.yCoord,
					this.zCoord - 3,
					this.xCoord + 4,
					this.yCoord + 3,
					this.zCoord + 4
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
	public long getPower() {
		return this.power;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getMaxPower() {
		return TileEntityCondenserPowered.maxPower;
	}
}
