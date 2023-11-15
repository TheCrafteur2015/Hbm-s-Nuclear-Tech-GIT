package com.hbm.tileentity.machine.storage;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.lib.Library;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMachineBAT9000 extends TileEntityBarrel {
	
	AxisAlignedBB bb = null;
	
	public TileEntityMachineBAT9000() {
		super(2048000);
		this.bb = AxisAlignedBB.getBoundingBox(
			this.xCoord - 2, this.yCoord,
			this.zCoord - 2, this.xCoord + 3,
			this.yCoord + 5, this.zCoord + 3);
	}
	
	@Override
	public String getName() {
		return "container.bat9000";
	}
	
	@Override
	public void checkFluidInteraction() {
		if(this.tank.getTankType().isAntimatter()) {
			this.worldObj.func_147480_a(this.xCoord, this.yCoord, this.zCoord, false);
			this.worldObj.newExplosion(null, this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, 10, true, true);
		}
	}
	
	@Override
	protected DirPos[] getConPos() {
		return new DirPos[] {
			new DirPos(this.xCoord + 1, this.yCoord, this.zCoord + 3, Library.POS_Z),
			new DirPos(this.xCoord - 1, this.yCoord, this.zCoord + 3, Library.POS_Z),
			new DirPos(this.xCoord + 1, this.yCoord, this.zCoord - 3, Library.NEG_Z),
			new DirPos(this.xCoord - 1, this.yCoord, this.zCoord - 3, Library.NEG_Z),
			new DirPos(this.xCoord + 3, this.yCoord, this.zCoord + 1, Library.POS_X),
			new DirPos(this.xCoord - 3, this.yCoord, this.zCoord + 1, Library.NEG_X),
			new DirPos(this.xCoord + 3, this.yCoord, this.zCoord - 1, Library.POS_X),
			new DirPos(this.xCoord - 3, this.yCoord, this.zCoord - 1, Library.NEG_X)
		};
	}

	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord + 3, getTact(), type);
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord + 3, getTact(), type);
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord - 3, getTact(), type);
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord - 3, getTact(), type);
		fillFluid(this.xCoord + 3, this.yCoord, this.zCoord + 1, getTact(), type);
		fillFluid(this.xCoord - 3, this.yCoord, this.zCoord + 1, getTact(), type);
		fillFluid(this.xCoord + 3, this.yCoord, this.zCoord - 1, getTact(), type);
		fillFluid(this.xCoord - 3, this.yCoord, this.zCoord - 1, getTact(), type);
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return this.bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
