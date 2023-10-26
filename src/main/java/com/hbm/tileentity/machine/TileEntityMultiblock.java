package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMultiblock extends TileEntity {
	
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			if(getBlockType() == ModBlocks.struct_launcher_core && isCompact()) {
				buildCompact();
			}
			
			if(getBlockType() == ModBlocks.struct_launcher_core_large) {
				
				int meta = isTable();
				
				if(meta != -1)
					buildTable(meta);
			}
		}
	}
	
	private boolean isCompact() {
		
		for(int i = -1; i <= 1; i++)
			for(int j = -1; j <= 1; j++)
				if(!(i == 0 && j == 0))
					if(this.worldObj.getBlock(this.xCoord + i, this.yCoord, this.zCoord + j) != ModBlocks.struct_launcher)
						return false;
		
		return true;
	}
	
	private int isTable() {
		
		for(int i = -4; i <= 4; i++)
			for(int j = -4; j <= 4; j++)
				if(!(i == 0 && j == 0))
					if(this.worldObj.getBlock(this.xCoord + i, this.yCoord, this.zCoord + j) != ModBlocks.struct_launcher)
						return -1;
		
		boolean flag = true;
		
		for(int k = 1; k < 12; k++) {
			if(this.worldObj.getBlock(this.xCoord + 3, this.yCoord + k, this.zCoord) != ModBlocks.struct_scaffold)
				flag = false;
		}
		
		if(flag)
			return 0;
		flag = true;
		
		for(int k = 1; k < 12; k++) {
			if(this.worldObj.getBlock(this.xCoord - 3, this.yCoord + k, this.zCoord) != ModBlocks.struct_scaffold)
				flag = false;
		}
		
		if(flag)
			return 1;
		flag = true;
		
		for(int k = 1; k < 12; k++) {
			if(this.worldObj.getBlock(this.xCoord, this.yCoord + k, this.zCoord + 3) != ModBlocks.struct_scaffold)
				flag = false;
		}
		
		if(flag)
			return 2;
		flag = true;
		
		for(int k = 1; k < 12; k++) {
			if(this.worldObj.getBlock(this.xCoord, this.yCoord + k, this.zCoord - 3) != ModBlocks.struct_scaffold)
				flag = false;
		}
		
		if(flag)
			return 3;
		
		return -1;
		
	}
	
	private void buildCompact() {
		
		this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, ModBlocks.compact_launcher);

		placeDummy(this.xCoord + 1, this.yCoord, this.zCoord + 1, this.xCoord, this.yCoord, this.zCoord, ModBlocks.dummy_port_compact_launcher);
		placeDummy(this.xCoord + 1, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord, ModBlocks.dummy_plate_compact_launcher);
		placeDummy(this.xCoord + 1, this.yCoord, this.zCoord - 1, this.xCoord, this.yCoord, this.zCoord, ModBlocks.dummy_port_compact_launcher);
		placeDummy(this.xCoord, this.yCoord, this.zCoord - 1, this.xCoord, this.yCoord, this.zCoord, ModBlocks.dummy_plate_compact_launcher);
		placeDummy(this.xCoord - 1, this.yCoord, this.zCoord - 1, this.xCoord, this.yCoord, this.zCoord, ModBlocks.dummy_port_compact_launcher);
		placeDummy(this.xCoord - 1, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord, ModBlocks.dummy_plate_compact_launcher);
		placeDummy(this.xCoord - 1, this.yCoord, this.zCoord + 1, this.xCoord, this.yCoord, this.zCoord, ModBlocks.dummy_port_compact_launcher);
		placeDummy(this.xCoord, this.yCoord, this.zCoord + 1, this.xCoord, this.yCoord, this.zCoord, ModBlocks.dummy_plate_compact_launcher);
	}
	
	private void buildTable(int meta) {
		
		this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, ModBlocks.launch_table, meta, 2);
		
		switch(meta) {
		case 0:
			for(int i = 1; i < 12; i++)
				this.worldObj.setBlock(this.xCoord + 3, this.yCoord + i, this.zCoord, Blocks.air);
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(this.xCoord + i, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord, ModBlocks.dummy_port_launch_table);
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(this.xCoord, this.yCoord, this.zCoord + i, this.xCoord, this.yCoord, this.zCoord, ModBlocks.dummy_plate_launch_table);
			
			break;
			
		case 1:
			for(int i = 1; i < 12; i++)
				this.worldObj.setBlock(this.xCoord - 3, this.yCoord + i, this.zCoord, Blocks.air);
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(this.xCoord + i, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord, ModBlocks.dummy_port_launch_table);
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(this.xCoord, this.yCoord, this.zCoord + i, this.xCoord, this.yCoord, this.zCoord, ModBlocks.dummy_plate_launch_table);
			
			break;
			
		case 2:
			for(int i = 1; i < 12; i++)
				this.worldObj.setBlock(this.xCoord, this.yCoord + i, this.zCoord + 3, Blocks.air);
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(this.xCoord + i, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord, ModBlocks.dummy_plate_launch_table);
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(this.xCoord, this.yCoord, this.zCoord + i, this.xCoord, this.yCoord, this.zCoord, ModBlocks.dummy_port_launch_table);
			
			break;
			
		case 3:
			for(int i = 1; i < 12; i++)
				this.worldObj.setBlock(this.xCoord, this.yCoord + i, this.zCoord - 3, Blocks.air);
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(this.xCoord + i, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord, ModBlocks.dummy_plate_launch_table);
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(this.xCoord, this.yCoord, this.zCoord + i, this.xCoord, this.yCoord, this.zCoord, ModBlocks.dummy_port_launch_table);
			
			break;
			
		}

		for(int i = -4; i <= 4; i++)
			for(int j = -4; j <= 4; j++)
				if(i != 0 && j != 0)
					placeDummy(this.xCoord + i, this.yCoord, this.zCoord + j, this.xCoord, this.yCoord, this.zCoord, ModBlocks.dummy_port_launch_table);
					
		
	}
	
	private void placeDummy(int x, int y, int z, int xCoord, int yCoord, int zCoord, Block block) {
		
		this.worldObj.setBlock(x, y, z, block);
		
		TileEntity te = this.worldObj.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityDummy) {
			TileEntityDummy dummy = (TileEntityDummy)te;
			dummy.targetX = xCoord;
			dummy.targetY = yCoord;
			dummy.targetZ = zCoord;
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

}
