package com.hbm.blocks.monitors;

import com.hbm.main.MainRegistry;
import com.hbm.tileentity.monitors.TileEntityMonitorPWR;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * @author  Gabriel Roche
 * @since   
 * @version 
 */
public class MonitorPWR extends BlockContainer {
	
	public MonitorPWR(Material mat) {
		super(mat);
	}
	
	@Override
	public TileEntity createNewTileEntity(World arg0, int arg1) {
		return new TileEntityMonitorPWR();
	}
	
	@Override
	public boolean canCreatureSpawn(EnumCreatureType arg0, IBlockAccess arg1, int arg2, int arg3, int arg4) {
		return false;
	}
	
	@Override
	public boolean canHarvestBlock(EntityPlayer player, int arg1) {
		if (player.worldObj.isRemote)
			return false;
		return true;
	}
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float fX, float fY, float fZ, int meta) {
		return side;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float fX, float fY, float fZ) {
		if (world.isRemote)
			return false;
		if (player.isSneaking())
			return false;
		FMLNetworkHandler.openGui(player, MainRegistry.instance, meta, world, x, y, z);
		return true;
	}
	
}