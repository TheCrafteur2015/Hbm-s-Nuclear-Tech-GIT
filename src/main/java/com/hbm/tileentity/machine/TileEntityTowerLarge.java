package com.hbm.tileentity.machine;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityTowerLarge extends TileEntityCondenser {
	
	public TileEntityTowerLarge() {
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.SPENTSTEAM, 10000);
		this.tanks[1] = new FluidTank(Fluids.WATER, 10000);
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(this.worldObj.isRemote) {
			
			if(this.waterTimer > 0 && this.worldObj.getTotalWorldTime() % 4 == 0) {
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "tower");
				data.setFloat("lift", 0.5F);
				data.setFloat("base", 1F);
				data.setFloat("max", 10F);
				data.setInteger("life", 750 + this.worldObj.rand.nextInt(250));
	
				data.setDouble("posX", this.xCoord + 0.5 + this.worldObj.rand.nextDouble() * 3 - 1.5);
				data.setDouble("posZ", this.zCoord + 0.5 + this.worldObj.rand.nextDouble() * 3 - 1.5);
				data.setDouble("posY", this.yCoord + 1);
				
				MainRegistry.proxy.effectNT(data);
			}
		}
	}

	@Override
	public void fillFluidInit(FluidType type) {
		
		for(int i = 2; i < 6; i++) {
			ForgeDirection dir = ForgeDirection.getOrientation(i);
			ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
			fillFluid(this.xCoord + dir.offsetX * 5, this.yCoord, this.zCoord + dir.offsetZ * 5, getTact(), type);
			fillFluid(this.xCoord + dir.offsetX * 5 + rot.offsetX * 3, this.yCoord, this.zCoord + dir.offsetZ * 5 + rot.offsetZ * 3, getTact(), type);
			fillFluid(this.xCoord + dir.offsetX * 5 + rot.offsetX * -3, this.yCoord, this.zCoord + dir.offsetZ * 5 + rot.offsetZ * -3, getTact(), type);
		}
	}

	@Override
	public void subscribeToAllAround(FluidType type, TileEntity te) {
		
		for(int i = 2; i < 6; i++) {
			ForgeDirection dir = ForgeDirection.getOrientation(i);
			ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
			trySubscribe(this.tanks[0].getTankType(), this.worldObj, this.xCoord + dir.offsetX * 5, this.yCoord, this.zCoord + dir.offsetZ * 5, dir);
			trySubscribe(this.tanks[0].getTankType(), this.worldObj, this.xCoord + dir.offsetX * 5 + rot.offsetX * 3, this.yCoord, this.zCoord + dir.offsetZ * 5 + rot.offsetZ * 3, dir);
			trySubscribe(this.tanks[0].getTankType(),this.worldObj,  this.xCoord + dir.offsetX * 5 + rot.offsetX * -3, this.yCoord, this.zCoord + dir.offsetZ * 5 + rot.offsetZ * -3, dir);
		}
	}

	@Override
	public void sendFluidToAll(FluidTank tank, TileEntity te) {
		
		for(int i = 2; i < 6; i++) {
			ForgeDirection dir = ForgeDirection.getOrientation(i);
			ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
			this.sendFluid(this.tanks[1], this.worldObj, this.xCoord + dir.offsetX * 5, this.yCoord, this.zCoord + dir.offsetZ * 5, dir);
			this.sendFluid(this.tanks[1], this.worldObj, this.xCoord + dir.offsetX * 5 + rot.offsetX * 3, this.yCoord, this.zCoord + dir.offsetZ * 5 + rot.offsetZ * 3, dir);
			this.sendFluid(this.tanks[1], this.worldObj,  this.xCoord + dir.offsetX * 5 + rot.offsetX * -3, this.yCoord, this.zCoord + dir.offsetZ * 5 + rot.offsetZ * -3, dir);
		}
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(
					this.xCoord - 4,
					this.yCoord,
					this.zCoord - 4,
					this.xCoord + 5,
					this.yCoord + 13,
					this.zCoord + 5
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
