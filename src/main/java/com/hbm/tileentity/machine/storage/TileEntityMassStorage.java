package com.hbm.tileentity.machine.storage;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerMassStorage;
import com.hbm.inventory.gui.GUIMassStorage;
import com.hbm.items.ModItems;
import com.hbm.tileentity.INBTPacketReceiver;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityMassStorage extends TileEntityCrateBase implements INBTPacketReceiver, IControlReceiver {
	
	private int stack = 0;
	public boolean output = false;
	private int capacity;
	public int redstone = 0;
	
	@SideOnly(Side.CLIENT) public ItemStack type;
	
	public TileEntityMassStorage() {
		super(3);
	}
	
	public TileEntityMassStorage(int capacity) {
		this();
		this.capacity = capacity;
	}

	@Override
	public String getInventoryName() {
		return "container.massStorage";
	}
	
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			int newRed = getStockpile() * 15 / this.capacity;
			
			if(newRed != this.redstone) {
				this.redstone = newRed;
				markDirty();
			}
			
			if(this.slots[0] != null && this.slots[0].getItem() == ModItems.fluid_barrel_infinite) {
				this.stack = getCapacity();
			}
			
			if(getType() == null)
				this.stack = 0;
			
			if(getType() != null && getStockpile() < getCapacity() && this.slots[0] != null && this.slots[0].isItemEqual(getType()) && ItemStack.areItemStackTagsEqual(this.slots[0], getType())) {
				
				int remaining = getCapacity() - getStockpile();
				int toRemove = Math.min(remaining, this.slots[0].stackSize);
				decrStackSize(0, toRemove);
				this.stack += toRemove;
				this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
			}
			
			if(this.output && getType() != null) {
				
				if(this.slots[2] != null && !(this.slots[2].isItemEqual(getType()) && ItemStack.areItemStackTagsEqual(this.slots[2], getType()))) {
					return;
				}
				
				int amount = Math.min(getStockpile(), getType().getMaxStackSize());
				
				if(amount > 0) {
					if(this.slots[2] == null) {
						this.slots[2] = this.slots[1].copy();
						this.slots[2].stackSize = amount;
						this.stack -= amount;
					} else {
						amount = Math.min(amount, this.slots[2].getMaxStackSize() - this.slots[2].stackSize);
						this.slots[2].stackSize += amount;
						this.stack -= amount;
					}
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("stack", getStockpile());
			data.setBoolean("output", this.output);
			if(this.slots[1] != null) this.slots[1].writeToNBT(data);
			INBTPacketReceiver.networkPack(this, data, 15);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.stack = nbt.getInteger("stack");
		this.output = nbt.getBoolean("output");
		this.type = ItemStack.loadItemStackFromNBT(nbt);
	}
	
	public int getCapacity() {
		return this.capacity;
	}
	
	public ItemStack getType() {
		return this.slots[1] == null ? null : this.slots[1].copy();
	}
	
	public int getStockpile() {
		return this.stack;
	}
	
	public void setStockpile(int stack) {
		this.stack = stack;
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(this.xCoord - player.posX, this.yCoord - player.posY, this.zCoord - player.posZ).lengthVector() < 20;
	}

	@Override
	public void openInventory() {
		this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, "hbm:block.storageOpen", 1.0F, 1.0F);
	}

	@Override
	public void closeInventory() {
		this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, "hbm:block.storageClose", 1.0F, 1.0F);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.stack = nbt.getInteger("stack");
		this.output = nbt.getBoolean("output");
		this.capacity = nbt.getInteger("capacity");
		this.redstone = nbt.getByte("redstone");
		
		if(this.capacity <= 0) {
			this.capacity = 10_000;
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("stack", this.stack);
		nbt.setBoolean("output", this.output);
		nbt.setInteger("capacity", this.capacity);
		nbt.setByte("redstone", (byte) this.redstone);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("provide") && this.slots[1] != null) {
			
			if(getStockpile() == 0) {
				return;
			}
			
			int amount = data.getBoolean("provide") ? this.slots[1].getMaxStackSize() : 1;
			amount = Math.min(amount, getStockpile());
			
			if(this.slots[2] != null && !(this.slots[2].isItemEqual(getType()) && ItemStack.areItemStackTagsEqual(this.slots[2], getType()))) {
				return;
			}
			
			if(this.slots[2] == null) {
				this.slots[2] = this.slots[1].copy();
				this.slots[2].stackSize = amount;
				this.stack -= amount;
			} else {
				amount = Math.min(amount, this.slots[2].getMaxStackSize() - this.slots[2].stackSize);
				this.slots[2].stackSize += amount;
				this.stack -= amount;
			}
		}
		
		if(data.hasKey("toggle")) {
			this.output = !this.output;
		}
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return !isLocked() && i == 0 && (getType() == null || (getType().isItemEqual(itemStack) && ItemStack.areItemStackTagsEqual(itemStack, getType())));
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return !isLocked() && i == 2;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0, 2 };
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMassStorage(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMassStorage(player.inventory, this);
	}
}
