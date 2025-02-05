package com.hbm.tileentity.network;

import com.hbm.blocks.network.CraneInserter;
import com.hbm.inventory.container.ContainerCraneInserter;
import com.hbm.inventory.gui.GUICraneInserter;
import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCraneInserter extends TileEntityCraneBase implements IGUIProvider {
	
	public static final int[] access = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };

	public TileEntityCraneInserter() {
		super(21);
	}

	@Override
	public String getName() {
		return "container.craneInserter";
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if(!this.worldObj.isRemote) {

			ForgeDirection outputSide = getOutputSide();
			TileEntity te = this.worldObj.getTileEntity(this.xCoord + outputSide.offsetX, this.yCoord + outputSide.offsetY, this.zCoord + outputSide.offsetZ);
			
			int[] access = null;
			
			if(te instanceof ISidedInventory) {
				ISidedInventory sided = (ISidedInventory) te;
				//access = sided.getAccessibleSlotsFromSide(dir.ordinal());
				access = CraneInserter.masquerade(sided, outputSide.getOpposite().ordinal());
			}
			
			if(te instanceof IInventory) {
				for(int i = 0; i < this.slots.length; i++) {
					
					ItemStack stack = this.slots[i];
					
					if(stack != null) {
						ItemStack ret = CraneInserter.addToInventory((IInventory) te, access, stack.copy(), outputSide.getOpposite().ordinal());
						
						if(ret == null || ret.stackSize != stack.stackSize) {
							this.slots[i] = ret;
							markDirty();
							return;
						}
					}
				}
				
				//if the previous operation fails, repeat but use single items instead of the whole stack instead
				//this should fix cases where the inserter can't insert into something that has a stack size limitation
				for(int i = 0; i < this.slots.length; i++) {
					
					ItemStack stack = this.slots[i];
					
					if(stack != null) {
						stack = stack.copy();
						stack.stackSize = 1;
						ItemStack ret = CraneInserter.addToInventory((IInventory) te, access, stack.copy(), outputSide.getOpposite().ordinal());
						
						if(ret == null || ret.stackSize != stack.stackSize) {
							decrStackSize(i, 1);
							markDirty();
							return;
						}
					}
				}
			}
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return TileEntityCraneInserter.access;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return true;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return true;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCraneInserter(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICraneInserter(player.inventory, this);
	}
}
