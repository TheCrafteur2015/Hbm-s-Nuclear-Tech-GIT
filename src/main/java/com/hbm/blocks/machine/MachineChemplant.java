package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineChemplant;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineChemplant extends BlockDummyable {

	public MachineChemplant(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineChemplant();
		if(meta >= 6) return new TileEntityProxyCombo(false, true, true);
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {2, 0, 2, 1, 2, 1};
	}

	@Override
	public int getOffset() {
		return 1;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		
		x -= dir.offsetX;
		z -= dir.offsetZ;
		
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		makeExtra(world, x + rot.offsetX * 2,					y,	z + rot.offsetZ * 2);
		makeExtra(world, x - rot.offsetX * 1,					y,	z - rot.offsetZ * 1);
		makeExtra(world, x + rot.offsetX * 2 - dir.offsetX,	y,	z + rot.offsetZ * 2 - dir.offsetZ);
		makeExtra(world, x - rot.offsetX * 1 - dir.offsetX,	y,	z - rot.offsetZ * 1 - dir.offsetZ);
	}
}
