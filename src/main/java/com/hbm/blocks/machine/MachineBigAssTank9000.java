package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.IPersistentInfoProvider;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.items.machine.ItemFluidIdentifier;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IPersistentNBT;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.storage.TileEntityMachineBAT9000;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineBigAssTank9000 extends BlockDummyable implements IPersistentInfoProvider {

	public MachineBigAssTank9000(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12) return new TileEntityMachineBAT9000();
		if(meta >= 6) return new TileEntityProxyCombo(false, false, true);
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {4, 0, 2, 2, 1, 1};
	}

	@Override
	public int getOffset() {
		return 2;
	}
	
	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
//		ItemStack item = player.getHeldItem();
//		if (item.getItem() instanceof ItemFluidIdentifier) {
//			player.addChatMessage(new ChatComponentText("Debug=>" + item.getItemDamageForDisplay()));
//			int[] i = getDimensions();
//			for(int a = x - i[1]; a <= x + i[0]; a++) {
//				for(int b = y - i[3]; b <= y + i[2]; b++) {
//					for(int c = z - i[5]; c <= z + i[4]; c++) {
//						if(!(a == x && b == y && c == z)) {
//							TileEntityProxyCombo tile = (TileEntityProxyCombo) world.getTileEntity(x, y, z);
//							player.addChatMessage(new ChatComponentText("Debug (TileEntity)=>" + tile));
//						}
//					}
//				}
//			}
//		}
//		
		super.onBlockClicked(world, x, y, z, player);
	}
	
	

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {4, 0, 1, 1, 2, -2}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {4, 0, 1, 1, -2, 2}, this, dir);

		makeExtra(world, x + dir.offsetX * o + 1, y, z + dir.offsetZ * o + 2);
		makeExtra(world, x + dir.offsetX * o - 1, y, z + dir.offsetZ * o + 2);
		makeExtra(world, x + dir.offsetX * o + 1, y, z + dir.offsetZ * o - 2);
		makeExtra(world, x + dir.offsetX * o - 1, y, z + dir.offsetZ * o - 2);
		makeExtra(world, x + dir.offsetX * o + 2, y, z + dir.offsetZ * o + 1);
		makeExtra(world, x + dir.offsetX * o - 2, y, z + dir.offsetZ * o + 1);
		makeExtra(world, x + dir.offsetX * o + 2, y, z + dir.offsetZ * o - 1);
		makeExtra(world, x + dir.offsetX * o - 2, y, z + dir.offsetZ * o - 1);
	}

	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {

		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, getDimensions(), x, y, z, dir) || !MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {4, 0, 1, 1, 2, -2}, x, y, z, dir) || !MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {4, 0, 1, 1, -2, 2}, x, y, z, dir)) return false;
		
		return true;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
			return true;
		int[] pos = findCore(world, x, y, z);
		if(pos == null)
			return false;
		if(!player.isSneaking()) {
			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, pos[0], pos[1], pos[2]); //we can do this because nobody is stopping me from doing this
		} else {
			ItemStack item = player.getHeldItem();
			if (item.getItem() instanceof ItemFluidIdentifier) {
				TileEntityMachineBAT9000 tile = (TileEntityMachineBAT9000) world.getTileEntity(pos[0], pos[1], pos[2]);
				tile.setTypeForSync(Fluids.fromID(item.getItemDamage()), 0);
			}
		}
		return true;
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return IPersistentNBT.getDrops(world, x, y, z, this);
	}

	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int side) {

		TileEntity te = world.getTileEntity(x, y, z);

		if(!(te instanceof TileEntityMachineBAT9000))
			return 0;

		TileEntityMachineBAT9000 tank = (TileEntityMachineBAT9000) te;
		return tank.getComparatorPower();
	}

	@Override
	public void addInformation(ItemStack stack, NBTTagCompound persistentTag, EntityPlayer player, List list, boolean ext) {
		FluidTank tank = new FluidTank(Fluids.NONE, 0);
		tank.readFromNBT(persistentTag, "tank");
		list.add(EnumChatFormatting.YELLOW + "" + tank.getFill() + "/" + tank.getMaxFill() + "mB " + tank.getTankType().getLocalizedName());
	}
}
