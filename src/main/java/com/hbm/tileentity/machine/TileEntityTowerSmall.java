package com.hbm.tileentity.machine;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityTowerSmall extends TileEntityCondenser {
	
	public TileEntityTowerSmall() {
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.SPENTSTEAM, 1000, 0);
		this.tanks[1] = new FluidTank(Fluids.WATER, 1000, 1);
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(this.worldObj.isRemote) {
			
			if(this.waterTimer > 0 && this.worldObj.getTotalWorldTime() % 2 == 0) {
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "tower");
				data.setFloat("lift", 1F);
				data.setFloat("base", 0.5F);
				data.setFloat("max", 4F);
				data.setInteger("life", 250 + this.worldObj.rand.nextInt(250));
	
				data.setDouble("posX", this.xCoord + 0.5);
				data.setDouble("posZ", this.zCoord + 0.5);
				data.setDouble("posY", this.yCoord + 18);
				
				MainRegistry.proxy.effectNT(data);
			}
		}
	}

	@Override
	public void subscribeToAllAround(FluidType type, TileEntity te) {
		trySubscribe(this.tanks[0].getTankType(), this.worldObj, this.xCoord + 3, this.yCoord, this.zCoord, Library.POS_X);
		trySubscribe(this.tanks[0].getTankType(), this.worldObj, this.xCoord - 3, this.yCoord, this.zCoord, Library.NEG_X);
		trySubscribe(this.tanks[0].getTankType(), this.worldObj, this.xCoord, this.yCoord, this.zCoord + 3, Library.POS_Z);
		trySubscribe(this.tanks[0].getTankType(), this.worldObj, this.xCoord, this.yCoord, this.zCoord - 3, Library.NEG_Z);
	}

	@Override
	public void sendFluidToAll(FluidTank tank, TileEntity te) {
		this.sendFluid(this.tanks[1], this.worldObj, this.xCoord + 3, this.yCoord, this.zCoord, Library.POS_X);
		this.sendFluid(this.tanks[1], this.worldObj, this.xCoord - 3, this.yCoord, this.zCoord, Library.NEG_X);
		this.sendFluid(this.tanks[1], this.worldObj, this.xCoord, this.yCoord, this.zCoord + 3, Library.POS_Z);
		this.sendFluid(this.tanks[1], this.worldObj, this.xCoord, this.yCoord, this.zCoord - 3, Library.NEG_Z);
	}

	@Override
	public void fillFluidInit(FluidType type) {
		
		for(int i = 2; i <= 6; i++) {
			ForgeDirection dir = ForgeDirection.getOrientation(i);
			fillFluid(this.xCoord + dir.offsetX * 3, this.yCoord, this.zCoord + dir.offsetZ * 3, getTact(), type);
		}
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(
					this.xCoord - 2,
					this.yCoord,
					this.zCoord - 2,
					this.xCoord + 3,
					this.yCoord + 20,
					this.zCoord + 3
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
