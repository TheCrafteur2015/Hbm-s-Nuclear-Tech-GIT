package com.hbm.tileentity.machine.storage;

import com.hbm.blocks.BlockDummyable;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineOrbus extends TileEntityBarrel {

	public TileEntityMachineOrbus() {
		super(512000);
	}
	
	@Override
	public String getName() {
		return "container.orbus";
	}
	
	@Override
	public void checkFluidInteraction() { } //NO!

	@Override
	public void fillFluidInit(FluidType type) {
		
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset).getOpposite();
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);

		for(int i = -1; i < 6; i += 6) {
			fillFluid(this.xCoord, this.yCoord + i, this.zCoord, getTact(), this.tank.getTankType());
			fillFluid(this.xCoord + dir.offsetX, this.yCoord + i, this.zCoord + dir.offsetZ, getTact(), this.tank.getTankType());
			fillFluid(this.xCoord + rot.offsetX, this.yCoord + i, this.zCoord + rot.offsetZ, getTact(), this.tank.getTankType());
			fillFluid(this.xCoord + dir.offsetX + rot.offsetX, this.yCoord + i, this.zCoord + dir.offsetZ + rot.offsetZ, getTact(), this.tank.getTankType());
		}
	}
	
	protected DirPos[] conPos;
	
	@Override
	protected DirPos[] getConPos() {
		
		if(this.conPos != null)
			return this.conPos;
		
		this.conPos = new DirPos[8];
		
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset).getOpposite();
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);

		for(int i = -1; i < 6; i += 6) {
			ForgeDirection out = i == -1 ? ForgeDirection.DOWN : ForgeDirection.UP;
			int index = i == -1 ? 0 : 4;
			this.conPos[index + 0] = new DirPos(this.xCoord,								this.yCoord + i,	this.zCoord,								out);
			this.conPos[index + 1] = new DirPos(this.xCoord + dir.offsetX,				this.yCoord + i,	this.zCoord + dir.offsetZ,				out);
			this.conPos[index + 2] = new DirPos(this.xCoord + rot.offsetX,				this.yCoord + i,	this.zCoord + rot.offsetZ,				out);
			this.conPos[index + 3] = new DirPos(this.xCoord + dir.offsetX + rot.offsetX,	this.yCoord + i,	this.zCoord + dir.offsetZ + rot.offsetZ,	out);
		}
		
		return this.conPos;
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(
					this.xCoord - 2,
					this.yCoord,
					this.zCoord - 2,
					this.xCoord + 2,
					this.yCoord + 5,
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
