package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.lib.RefStrings;
import com.hbm.render.block.ct.CT;
import com.hbm.render.block.ct.CTStitchReceiver;
import com.hbm.render.block.ct.IBlockCT;
import com.hbm.tileentity.machine.TileEntityPWRController;

import api.hbm.fluid.IFluidConnector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockPWR extends BlockContainer implements IBlockCT {
	
	@SideOnly(Side.CLIENT) protected IIcon iconPort;
	
	public BlockPWR(Material mat) {
		super(mat);
	}

	@Override
	public int getRenderType() {
		return CT.renderID;
	}
	
	@Override
	public Item getItemDropped(int i, Random rand, int j)  {
		return null;
	}

	@SideOnly(Side.CLIENT) public CTStitchReceiver rec;
	@SideOnly(Side.CLIENT) public CTStitchReceiver recPort;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);
		this.iconPort = reg.registerIcon(RefStrings.MODID + ":pwr_casing_port");
		this.rec = IBlockCT.primeReceiver(reg, this.blockIcon.getIconName(), this.blockIcon);
		this.recPort = IBlockCT.primeReceiver(reg, this.iconPort.getIconName(), this.iconPort);
	}

	@Override
	public IIcon[] getFragments(IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == 1) return this.recPort.fragCache;
		return this.rec.fragCache;
	}

	@Override
	public boolean canConnect(IBlockAccess world, int x, int y, int z, Block block) {
		return block == ModBlocks.pwr_block || block == ModBlocks.pwr_controller;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityBlockPWR();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(tile instanceof TileEntityBlockPWR) {
			TileEntityBlockPWR pwr = (TileEntityBlockPWR) tile;
			world.removeTileEntity(x, y, z);
			if(pwr.block != null) {
				world.setBlock(x, y, z, pwr.block);
				TileEntity controller = world.getTileEntity(pwr.coreX, pwr.coreY, pwr.coreZ);
				
				if(controller instanceof TileEntityPWRController) {
					((TileEntityPWRController) controller).assembled = false;
				}
			}
		} else {
			world.removeTileEntity(x, y, z);
		}
		super.breakBlock(world, x, y, z, block, meta);
	}
	
	public static class TileEntityBlockPWR extends TileEntity implements IFluidConnector, ISidedInventory {
		
		public Block block;
		public int coreX;
		public int coreY;
		public int coreZ;
		
		@Override
		public void updateEntity() {
			
			if(!this.worldObj.isRemote) {
				
				if(this.worldObj.getTotalWorldTime() % 20 == 0 && this.block != null) {
					
					TileEntityPWRController controller = getCore();
					
					if(controller != null) {
						if(!controller.assembled) {
							getBlockType().breakBlock(this.worldObj, this.xCoord, this.yCoord, this.zCoord, getBlockType(), getBlockMetadata());
						}
					} else if(this.worldObj.getChunkProvider().chunkExists(this.coreX >> 4, this.coreZ >> 4)) {
						getBlockType().breakBlock(this.worldObj, this.xCoord, this.yCoord, this.zCoord, getBlockType(), getBlockMetadata());
					}
				}
			}
		}
		
		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			
			this.block = Block.getBlockById(nbt.getInteger("block"));
			if(this.block != Blocks.air) {
				this.coreX = nbt.getInteger("cX");
				this.coreY = nbt.getInteger("cY");
				this.coreZ = nbt.getInteger("cZ");
			} else {
				this.block = null;
			}
		}
		
		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);

			if(this.block != null) {
				nbt.setInteger("block", Block.getIdFromBlock(this.block));
				nbt.setInteger("cX", this.coreX);
				nbt.setInteger("cY", this.coreY);
				nbt.setInteger("cZ", this.coreZ);
			}
		}
		
		@Override
		public void markDirty() {
			if(this.worldObj != null) {
				this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
			}
		}
		
		public TileEntityPWRController cachedCore;
		
		protected TileEntityPWRController getCore() {
			
			if(this.cachedCore != null && !this.cachedCore.isInvalid()) return this.cachedCore;

			if(this.worldObj.getChunkProvider().chunkExists(this.coreX >> 4, this.coreZ >> 4)) {
				
				TileEntity tile = this.worldObj.getTileEntity(this.coreX, this.coreY, this.coreZ);
				if(tile instanceof TileEntityPWRController) {
					TileEntityPWRController controller = (TileEntityPWRController) tile;
					this.cachedCore = controller;
					return controller;
				}
			}
			
			return null;
		}

		@Override
		public long transferFluid(FluidType type, int pressure, long fluid) {
			
			if((getBlockMetadata() != 1) || (this.block == null)) return fluid;
			TileEntityPWRController controller = getCore();
			if(controller != null) return controller.transferFluid(type, pressure, fluid);
			
			return fluid;
		}

		@Override
		public long getDemand(FluidType type, int pressure) {
			
			if((getBlockMetadata() != 1) || (this.block == null)) return 0;
			TileEntityPWRController controller = getCore();
			if(controller != null) return controller.getDemand(type, pressure);
			
			return 0;
		}

		@Override
		public boolean canConnect(FluidType type, ForgeDirection dir) {
			return getBlockMetadata() == 1;
		}

		@Override
		public int getSizeInventory() {
			
			if((getBlockMetadata() != 1) || (this.block == null)) return 0;
			TileEntityPWRController controller = getCore();
			if(controller != null) return controller.getSizeInventory();
			
			return 0;
		}

		@Override
		public ItemStack getStackInSlot(int slot) {
			
			if((getBlockMetadata() != 1) || (this.block == null)) return null;
			TileEntityPWRController controller = getCore();
			if(controller != null) return controller.getStackInSlot(slot);
			
			return null;
		}

		@Override
		public ItemStack decrStackSize(int slot, int amount) {
			
			if((getBlockMetadata() != 1) || (this.block == null)) return null;
			TileEntityPWRController controller = getCore();
			if(controller != null) return controller.decrStackSize(slot, amount);
			
			return null;
		}

		@Override
		public ItemStack getStackInSlotOnClosing(int slot) {
			
			if((getBlockMetadata() != 1) || (this.block == null)) return null;
			TileEntityPWRController controller = getCore();
			if(controller != null) return controller.getStackInSlotOnClosing(slot);
			
			return null;
		}

		@Override
		public void setInventorySlotContents(int slot, ItemStack stack) {
			
			if((getBlockMetadata() != 1) || (this.block == null)) return;
			TileEntityPWRController controller = getCore();
			if(controller != null) controller.setInventorySlotContents(slot, stack);
		}

		@Override
		public int getInventoryStackLimit() {
			
			if((getBlockMetadata() != 1) || (this.block == null)) return 0;
			TileEntityPWRController controller = getCore();
			if(controller != null) return controller.getInventoryStackLimit();
			
			return 0;
		}

		@Override public boolean isUseableByPlayer(EntityPlayer player) { return false; }
		@Override public void openInventory() { }
		@Override public void closeInventory() { }
		@Override public String getInventoryName() { return ""; }
		@Override public boolean hasCustomInventoryName() { return false; }

		@Override
		public boolean isItemValidForSlot(int slot, ItemStack stack) {
			
			if((getBlockMetadata() != 1) || (this.block == null)) return false;
			TileEntityPWRController controller = getCore();
			if(controller != null) return controller.isItemValidForSlot(slot, stack);
			
			return false;
		}

		@Override
		public int[] getAccessibleSlotsFromSide(int side) {
			
			if((getBlockMetadata() != 1) || (this.block == null)) return new int[0];
			TileEntityPWRController controller = getCore();
			if(controller != null) return controller.getAccessibleSlotsFromSide(side);
			
			return new int[0];
		}

		@Override
		public boolean canInsertItem(int slot, ItemStack stack, int side) {
			
			if((getBlockMetadata() != 1) || (this.block == null)) return false;
			TileEntityPWRController controller = getCore();
			if(controller != null) return controller.canInsertItem(slot, stack, side);
			
			return false;
		}

		@Override
		public boolean canExtractItem(int slot, ItemStack stack, int side) {
			
			if((getBlockMetadata() != 1) || (this.block == null)) return false;
			TileEntityPWRController controller = getCore();
			if(controller != null) return controller.canExtractItem(slot, stack, side);
			
			return false;
		}
	}
}
