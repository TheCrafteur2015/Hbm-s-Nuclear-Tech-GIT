package com.hbm.blocks.machine;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.oil.TileEntityMachineSolidifier;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineSolidifier extends BlockDummyable implements ITooltipProvider {

	public MachineSolidifier() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12)
			return new TileEntityMachineSolidifier();
		
		if(meta >= BlockDummyable.extra)
			return new TileEntityProxyCombo(true, true, true);
		
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {3, 0, 1, 1, 1, 1};
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override
	public int getOffset() {
		return 1;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		
		x = x + dir.offsetX * o;
		z = z + dir.offsetZ * o;

		makeExtra(world, x, y + 3, z);
		
		makeExtra(world, x + 1, y + 1, z);
		makeExtra(world, x - 1, y + 1, z);
		makeExtra(world, x, y + 1, z + 1);
		makeExtra(world, x, y + 1, z - 1);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		addStandardInfo(stack, player, list, ext);
	}
}
