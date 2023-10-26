package com.hbm.blocks.network;

import com.hbm.blocks.IBlockMultiPass;
import com.hbm.lib.RefStrings;
import com.hbm.render.block.RenderBlockMultipass;
import com.hbm.tileentity.network.TileEntityCableBaseNT;

import api.hbm.block.IToolable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCablePaintable extends BlockContainer implements IToolable, IBlockMultiPass {

	@SideOnly(Side.CLIENT) protected IIcon overlay;

	public BlockCablePaintable() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCablePaintable();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(RefStrings.MODID + ":red_cable_base");
		this.overlay = reg.registerIcon(RefStrings.MODID + ":red_cable_overlay");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(tile instanceof TileEntityCablePaintable) {
			TileEntityCablePaintable pipe = (TileEntityCablePaintable) tile;
			
			if(pipe.block != null) {
				if(RenderBlockMultipass.currentPass == 1) {
					return this.overlay;
				} else {
					return pipe.block.getIcon(side, pipe.meta);
				}
			}
		}
		
		return RenderBlockMultipass.currentPass == 1 ? this.overlay : this.blockIcon;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float fX, float fY, float fZ) {
		
		ItemStack stack = player.getHeldItem();
		
		if(stack != null && stack.getItem() instanceof ItemBlock) {
			ItemBlock ib = (ItemBlock) stack.getItem();
			Block block = ib.field_150939_a;
			
			if(block.renderAsNormalBlock() && block != this) {
				
				TileEntity tile = world.getTileEntity(x, y, z);
				
				if(tile instanceof TileEntityCablePaintable) {
					TileEntityCablePaintable pipe = (TileEntityCablePaintable) tile;
					
					if(pipe.block == null) {
						pipe.block = block;
						pipe.meta = stack.getItemDamage() & 15;
						world.markBlockForUpdate(x, y, z);
						pipe.markDirty();
						return true;
					}
				}
			}
		}
		
		return super.onBlockActivated(world, x, y, z, player, side, fX, fY, fZ);
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		
		if(tool != ToolType.SCREWDRIVER) return false;
		
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(tile instanceof TileEntityCablePaintable) {
			TileEntityCablePaintable pipe = (TileEntityCablePaintable) tile;
			
			if(pipe.block != null) {
				pipe.block = null;
				world.markBlockForUpdate(x, y, z);
				pipe.markDirty();
				return true;
			}
		}
		
		return false;
	}

	@Override
	public int getPasses() {
		return 2;
	}
	
	@Override
	public int getRenderType(){
		return IBlockMultiPass.getRenderType();
	}

	public static class TileEntityCablePaintable extends TileEntityCableBaseNT {

		private Block block;
		private int meta;
		private Block lastBlock;
		private int lastMeta;

		@Override
		public void updateEntity() {
			super.updateEntity();

			if(this.worldObj.isRemote && (this.lastBlock != this.block || this.lastMeta != this.meta)) {
				this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
				this.lastBlock = this.block;
				this.lastMeta = this.meta;
			}
		}

		@Override
		public Packet getDescriptionPacket() {
			NBTTagCompound nbt = new NBTTagCompound();
			writeToNBT(nbt);
			return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
		}
		
		@Override
		public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
			readFromNBT(pkt.func_148857_g());
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			int id = nbt.getInteger("block");
			this.block = id == 0 ? null : Block.getBlockById(id);
			this.meta = nbt.getInteger("meta");
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			if(this.block != null) nbt.setInteger("block", Block.getIdFromBlock(this.block));
			nbt.setInteger("meta", this.meta);
		}
	}
}
