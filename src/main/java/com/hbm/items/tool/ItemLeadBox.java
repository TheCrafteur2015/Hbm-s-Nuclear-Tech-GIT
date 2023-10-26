package com.hbm.items.tool;

import com.hbm.inventory.container.ContainerLeadBox;
import com.hbm.inventory.gui.GUILeadBox;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.util.ItemStackUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemLeadBox extends Item implements IGUIProvider {

	public ItemLeadBox() {
		setMaxStackSize(1);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 1;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(!world.isRemote) player.openGui(MainRegistry.instance, 0, world, 0, 0, 0);
		return stack;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerLeadBox(player.inventory, new InventoryLeadBox(player, player.getHeldItem()));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUILeadBox(player.inventory, new InventoryLeadBox(player, player.getHeldItem()));
	}
	
	public static class InventoryLeadBox implements IInventory {
		
		public final EntityPlayer player;
		public final ItemStack box;
		public ItemStack[] slots;
		
		public InventoryLeadBox(EntityPlayer player, ItemStack box) {
			this.player = player;
			this.box = box;
			this.slots = new ItemStack[getSizeInventory()];
			
			if(!box.hasTagCompound())
				box.setTagCompound(new NBTTagCompound());
			
			ItemStack[] fromNBT = ItemStackUtil.readStacksFromNBT(box, this.slots.length);
			
			if(fromNBT != null) {
				for(int i = 0; i < this.slots.length; i++) {
					this.slots[i] = fromNBT[i];
				}
			}
		}

		@Override
		public int getSizeInventory() {
			return 20;
		}

		@Override
		public ItemStack getStackInSlot(int slot) {
			return this.slots[slot];
		}

		@Override
		public ItemStack decrStackSize(int slot, int amount) {
			ItemStack stack = getStackInSlot(slot);
			if (stack != null) {
				if (stack.stackSize > amount) {
					stack = stack.splitStack(amount);
					markDirty();
				} else {
					setInventorySlotContents(slot, null);
				}
			}
			return stack;
		}

		@Override
		public ItemStack getStackInSlotOnClosing(int slot) {
			ItemStack stack = getStackInSlot(slot);
			setInventorySlotContents(slot, null);
			return stack;
		}

		@Override
		public void setInventorySlotContents(int slot, ItemStack stack) {
			
			if(stack != null) {
				stack.stackSize = Math.min(stack.stackSize, getInventoryStackLimit());
			}
			
			this.slots[slot] = stack;
			markDirty();
		}

		@Override
		public String getInventoryName() {
			return "container.leadBox";
		}

		@Override
		public boolean hasCustomInventoryName() {
			return this.box.hasDisplayName();
		}

		@Override
		public int getInventoryStackLimit() {
			return 1;
		}

		@Override
		public void markDirty() {
			
			for(int i = 0; i < getSizeInventory(); ++i) {
				if(getStackInSlot(i) != null && getStackInSlot(i).stackSize == 0) {
					this.slots[i] = null;
				}
			}
			
			ItemStackUtil.addStacksToNBT(this.box, this.slots);
		}

		@Override
		public boolean isUseableByPlayer(EntityPlayer player) {
			return true;
		}

		@Override
		public void openInventory() {
			this.player.worldObj.playSoundEffect(this.player.posX, this.player.posY, this.player.posZ, "hbm:block.crateOpen", 1.0F, 0.8F);
		}

		@Override
		public void closeInventory() {
			this.player.worldObj.playSoundEffect(this.player.posX, this.player.posY, this.player.posZ, "hbm:block.crateClose", 1.0F, 0.8F);
		}

		@Override
		public boolean isItemValidForSlot(int slot, ItemStack stack) {
			return true;
		}
	}
}
