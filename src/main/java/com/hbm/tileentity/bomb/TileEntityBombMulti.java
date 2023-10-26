package com.hbm.tileentity.bomb;

import com.hbm.inventory.container.ContainerBombMulti;
import com.hbm.inventory.gui.GUIBombMulti;
import com.hbm.items.ModItems;
import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityBombMulti extends TileEntity implements ISidedInventory, IGUIProvider {

	public ItemStack slots[];
	private String customName;

	public TileEntityBombMulti() {
		this.slots = new ItemStack[6];
	}

	@Override
	public int getSizeInventory() {
		return this.slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return this.slots[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(this.slots[i] != null)
		{
			if(this.slots[i].stackSize <= j)
			{
				ItemStack itemStack = this.slots[i];
				this.slots[i] = null;
				return itemStack;
			}
			ItemStack itemStack1 = this.slots[i].splitStack(j);
			if (this.slots[i].stackSize == 0)
			{
				this.slots[i] = null;
			}
			
			return itemStack1;
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if(this.slots[i] != null)
		{
			ItemStack itemStack = this.slots[i];
			this.slots[i] = null;
			return itemStack;
		} else {
		return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		this.slots[i] = itemStack;
		if(itemStack != null && itemStack.stackSize > getInventoryStackLimit())
		{
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return hasCustomInventoryName() ? this.customName : "container.bombMulti";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}
	
	public void setCustomName(String name) {
		this.customName = name;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this)
		{
			return false;
		}else{
			return player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <=64;
		}
	}

	@Override
	public void openInventory() {
		
	}

	@Override
	public void closeInventory() {
		
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return j != 0 || i != 1 || itemStack.getItem() == Items.bucket;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);
		this.slots = new ItemStack[getSizeInventory()];
		
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if(b0 >= 0 && b0 < this.slots.length)
			{
				this.slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagList list = new NBTTagList();
		
		for(int i = 0; i < this.slots.length; i++)
		{
			if(this.slots[i] != null)
			{
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte)i);
				this.slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}
	
	public boolean isLoaded(){
		
		if(this.slots[0] != null && this.slots[0].getItem() == Item.getItemFromBlock(Blocks.tnt) && 
				this.slots[1] != null && this.slots[1].getItem() == Item.getItemFromBlock(Blocks.tnt) && 
				this.slots[3] != null && this.slots[3].getItem() == Item.getItemFromBlock(Blocks.tnt) && 
				this.slots[4] != null && this.slots[4].getItem() == Item.getItemFromBlock(Blocks.tnt))
		{
			return true;
		}
			
		return false;
	}
	
	public int return2type() {

		if(this.slots[2] != null)
		{
		if(this.slots[2].getItem() == Items.gunpowder)
		{
			return 1;
		}
		
		if(this.slots[2].getItem() == Item.getItemFromBlock(Blocks.tnt))
		{
			return 2;
		}
		
		if(this.slots[2].getItem() == ModItems.pellet_cluster)
		{
			return 3;
		}
		
		if(this.slots[2].getItem() == ModItems.powder_fire)
		{
			return 4;
		}
		
		if(this.slots[2].getItem() == ModItems.powder_poison)
		{
			return 5;
		}
		
		if(this.slots[2].getItem() == ModItems.pellet_gas)
		{
			return 6;
		}
		}
		return 0;
	}
	
	public int return5type() {
		
		if(this.slots[5] != null)
		{
		if(this.slots[5].getItem() == Items.gunpowder)
		{
			return 1;
		}
		
		if(this.slots[5].getItem() == Item.getItemFromBlock(Blocks.tnt))
		{
			return 2;
		}
		
		if(this.slots[5].getItem() == ModItems.pellet_cluster)
		{
			return 3;
		}
		
		if(this.slots[5].getItem() == ModItems.powder_fire)
		{
			return 4;
		}
		
		if(this.slots[5].getItem() == ModItems.powder_poison)
		{
			return 5;
		}
		
		if(this.slots[5].getItem() == ModItems.pellet_gas)
		{
			return 6;
		}
		}
		return 0;
	}
	
	public void clearSlots() {
		for(int i = 0; i < this.slots.length; i++)
		{
			this.slots[i] = null;
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerBombMulti(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIBombMulti(player.inventory, this);
	}
}