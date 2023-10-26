package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.Watz;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityWatzStruct extends TileEntity {
	
	@Override
	public void updateEntity() {
		
		

		/*
		 * skeptics may say that his is shit. i don't necessarily disagree, but it was both easy and quick to do
		 * and it remains readable and not terribly long, so who the fuck cares.
		 */
		if(this.worldObj.isRemote || (this.worldObj.getTotalWorldTime() % 20 != 0) || !cbr(ModBlocks.watz_cooler, 0, 1, 0) || !cbr(ModBlocks.watz_cooler, 0, 2, 0)) return;
		
		for(int i = 0; i < 3; i++) {
			if(!cbr(ModBlocks.watz_element, 1, i, 0)) return;
			if(!cbr(ModBlocks.watz_element, 2, i, 0)) return;
			if(!cbr(ModBlocks.watz_element, 0, i, 1)) return;
			if(!cbr(ModBlocks.watz_element, 0, i, 2)) return;
			if(!cbr(ModBlocks.watz_element, -1, i, 0)) return;
			if(!cbr(ModBlocks.watz_element, -2, i, 0)) return;
			if(!cbr(ModBlocks.watz_element, 0, i, -1)) return;
			if(!cbr(ModBlocks.watz_element, 0, i, -2)) return;
			if(!cbr(ModBlocks.watz_element, 1, i, 1)) return;
			if(!cbr(ModBlocks.watz_element, 1, i, -1)) return;
			if(!cbr(ModBlocks.watz_element, -1, i, 1)) return;
			if(!cbr(ModBlocks.watz_element, -1, i, -1)) return;
			if(!cbr(ModBlocks.watz_cooler, 2, i, 1)) return;
			if(!cbr(ModBlocks.watz_cooler, 2, i, -1)) return;
			if(!cbr(ModBlocks.watz_cooler, 1, i, 2)) return;
			if(!cbr(ModBlocks.watz_cooler, -1, i, 2)) return;
			if(!cbr(ModBlocks.watz_cooler, -2, i, 1)) return;
			if(!cbr(ModBlocks.watz_cooler, -2, i, -1)) return;
			if(!cbr(ModBlocks.watz_cooler, 1, i, -2)) return;
			if(!cbr(ModBlocks.watz_cooler, -1, i, -2)) return;
			
			for(int j = -1; j < 2; j++) {
				if(!cbr(ModBlocks.watz_end, 1, 3, i, j)) return;
				if(!cbr(ModBlocks.watz_end, 1, j, i, 3)) return;
				if(!cbr(ModBlocks.watz_end, 1, -3, i, j)) return;
				if(!cbr(ModBlocks.watz_end, 1, j, i, -3)) return;
			}
			if(!cbr(ModBlocks.watz_end, 1, 2, i, 2)) return;
			if(!cbr(ModBlocks.watz_end, 1, 2, i, -2)) return;
			if(!cbr(ModBlocks.watz_end, 1, -2, i, 2)) return;
			if(!cbr(ModBlocks.watz_end, 1, -2, i, -2)) return;
		}
		
		Watz watz = (Watz)ModBlocks.watz;
		BlockDummyable.safeRem = true;
		this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, ModBlocks.watz, 12, 3);
		watz.fillSpace(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.NORTH, 0);
		BlockDummyable.safeRem = false;
	}
	
	/** [G]et [B]lock at [R]elative position */
	private Block gbr(int x, int y, int z) {
		return this.worldObj.getBlock(this.xCoord + x, this.yCoord + y, this.zCoord + z);
	}
	
	/** [G]et [M]eta at [R]elative position */
	private int gmr(int x, int y, int z) {
		return this.worldObj.getBlockMetadata(this.xCoord + x, this.yCoord + y, this.zCoord + z);
	}
	
	/** [C]heck [B]lock at [R]elative position */
	private boolean cbr(Block b, int x, int y, int z) {
		return b == gbr(x, y, z);
	}
	private boolean cbr(Block b, int meta, int x, int y, int z) {
		return b == gbr(x, y, z) && meta == gmr(x, y, z);
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
}
