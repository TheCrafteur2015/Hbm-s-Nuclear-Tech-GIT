package com.hbm.blocks.machine;

import com.hbm.tileentity.machine.TileEntityMachineCoal;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

@Deprecated
public class MachineCoal extends BlockContainer {

	public MachineCoal(boolean blockState) {
		super(Material.iron);
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityMachineCoal();
	}
	
}