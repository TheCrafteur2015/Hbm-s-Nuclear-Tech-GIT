package com.hbm.blocks.machine.rbmk;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.BossSpawnHandler;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKStorage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class RBMKStorage extends RBMKBase {

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= BlockDummyable.offset)
			return new TileEntityRBMKStorage();
		
		return new TileEntityProxyCombo(true, false, false);
	}
	
	@Override
	public int getRenderType(){
		return RBMKBase.renderIDPassive;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		BossSpawnHandler.markFBI(player);
		return openInv(world, x, y, z, player);
	}
}
