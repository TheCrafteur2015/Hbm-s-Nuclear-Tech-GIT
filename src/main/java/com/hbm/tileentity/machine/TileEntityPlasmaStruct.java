package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachinePlasmaHeater;
import com.hbm.handler.MultiblockHandlerXR;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPlasmaStruct extends TileEntity {
	
	int age;
	
	@Override
	public void updateEntity() {
		
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata());
		
		if(this.worldObj.isRemote) {
			this.worldObj.spawnParticle("reddust",
					this.xCoord + 0.5 + dir.offsetX * -11 + this.worldObj.rand.nextGaussian() * 0.1,
					this.yCoord + 2.5 + this.worldObj.rand.nextGaussian() * 0.1,
					this.zCoord + 0.5 + dir.offsetZ * -11 + this.worldObj.rand.nextGaussian() * 0.1,
					0.9, 0.3, 0.7);
			return;
		}
		
		this.age++;
		
		if(this.age < 20)
			return;
		
		this.age = 0;
		
		MachinePlasmaHeater plas = (MachinePlasmaHeater)ModBlocks.plasma_heater;
		
		int[] rot = MultiblockHandlerXR.rotate(plas.getDimensions(), dir);

		for(int a = this.xCoord - rot[4]; a <= this.xCoord + rot[5]; a++) {
			for(int b = this.yCoord - rot[1]; b <= this.yCoord + rot[0]; b++) {
				for(int c = this.zCoord - rot[2]; c <= this.zCoord + rot[3]; c++) {
					
					if(a == this.xCoord && b == this.yCoord && c == this.zCoord)
						continue;
					
					if(this.worldObj.getBlock(a, b, c) != ModBlocks.fusion_heater)
						return;
				}
			}
		}
		
		rot = MultiblockHandlerXR.rotate(new int[] {4, -3, 2, 1, 1, 1}, dir);

		for(int a = this.xCoord - rot[4]; a <= this.xCoord + rot[5]; a++) {
			for(int b = this.yCoord - rot[1]; b <= this.yCoord + rot[0]; b++) {
				for(int c = this.zCoord - rot[2]; c <= this.zCoord + rot[3]; c++) {
					
					if(a == this.xCoord && b == this.yCoord && c == this.zCoord)
						continue;
					
					if(this.worldObj.getBlock(a, b, c) != ModBlocks.fusion_heater)
						return;
				}
			}
		}


        for(int i = 9; i <= 10; i++)
            for(int j = 1; j <= 2; j++)
			if(this.worldObj.getBlock(this.xCoord - dir.offsetX * i, this.yCoord + j, this.zCoord - dir.offsetZ * i) != ModBlocks.fusion_heater)
				return;
		
		BlockDummyable.safeRem = true;
		this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, ModBlocks.plasma_heater, getBlockMetadata() + BlockDummyable.offset, 3);
		plas.fillSpace(this.worldObj, this.xCoord + dir.offsetX, this.yCoord, this.zCoord + dir.offsetZ, dir, -plas.getOffset());
		BlockDummyable.safeRem = false;
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
}
