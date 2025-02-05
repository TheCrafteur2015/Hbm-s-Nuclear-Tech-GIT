package com.hbm.entity.item;

import com.hbm.entity.logic.IChunkLoader;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

public class EntityDeliveryDrone extends EntityDroneBase implements IInventory, IChunkLoader {
	
	protected ItemStack[] slots = new ItemStack[getSizeInventory()];
	public FluidStack fluid;
	
	private Ticket loaderTicket;
	public boolean isChunkLoading = false;
	
	public EntityDeliveryDrone(World world) {
		super(world);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(11, (byte) 0);
	}
	
	public EntityDeliveryDrone setChunkLoading() {
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, this.worldObj, Type.ENTITY));
		return this;
	}

	@Override
	public void onUpdate() {
		
		if(!this.worldObj.isRemote) {
			loadNeighboringChunks((int)Math.floor(this.posX / 16D), (int)Math.floor(this.posZ / 16D));
		}
		
		super.onUpdate();
	}

	@Override
	public double getSpeed() {
		return this.dataWatcher.getWatchableObjectByte(11) == 1 ? 0.375 : 0.125;
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		
		NBTTagList nbttaglist = new NBTTagList();

		for(int i = 0; i < this.slots.length; ++i) {
			if(this.slots[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.slots[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbt.setTag("Items", nbttaglist);
		
		if(this.fluid != null) {
			nbt.setString("fluidType", this.fluid.type.getName());
			nbt.setInteger("fluidAmount", this.fluid.fill);
		}

		nbt.setByte("load", this.dataWatcher.getWatchableObjectByte(11));
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		this.slots = new ItemStack[getSizeInventory()];

		for(int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 255;

			if(j >= 0 && j < this.slots.length) {
				this.slots[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
		
		if(nbt.hasKey("fluidType")) {
			this.fluid = new FluidStack(Fluids.fromName(nbt.getString("fluidType")), nbt.getInteger("fluidAmount"));
		}

		this.dataWatcher.updateObject(11, nbt.getByte("load"));
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.slots[slot];
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if(this.slots[slot] != null) {
			ItemStack itemstack;

			if(this.slots[slot].stackSize <= amount) {
				itemstack = this.slots[slot];
				this.slots[slot] = null;
				return itemstack;
			} else {
				itemstack = this.slots[slot].splitStack(amount);

				if(this.slots[slot].stackSize == 0) {
					this.slots[slot] = null;
				}

				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if(this.slots[slot] != null) {
			ItemStack itemstack = this.slots[slot];
			this.slots[slot] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.slots[slot] = stack;

		if(stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override public int getSizeInventory() { return 18; }
	@Override public String getInventoryName() { return "container.drone"; }
	@Override public int getInventoryStackLimit() { return 64; }
	@Override public boolean hasCustomInventoryName() { return false; }
	@Override public boolean isUseableByPlayer(EntityPlayer player) { return false; }
	@Override public boolean isItemValidForSlot(int slot, ItemStack stack) { return false; }

	@Override public void markDirty() { }
	@Override public void openInventory() { }
	@Override public void closeInventory() { }

	public void loadNeighboringChunks(int newChunkX, int newChunkZ) {
		if(!this.worldObj.isRemote && this.loaderTicket != null) {
			clearChunkLoader();
			ForgeChunkManager.forceChunk(this.loaderTicket, new ChunkCoordIntPair(newChunkX, newChunkZ));
			ForgeChunkManager.forceChunk(this.loaderTicket, new ChunkCoordIntPair(newChunkX + (int) Math.ceil((this.posX + this.motionX) / 16D), newChunkZ + (int) Math.ceil((this.posZ + this.motionZ) / 16D)));
		}
	}
	
	@Override
	public void setDead() {
		super.setDead();
		clearChunkLoader();
	}
	
	public void clearChunkLoader() {
		if(!this.worldObj.isRemote && this.loaderTicket != null) {
			for(ChunkCoordIntPair chunk : this.loaderTicket.getChunkList()) {
				ForgeChunkManager.unforceChunk(this.loaderTicket, chunk);
			}
		}
	}

	@Override
	public void init(Ticket ticket) {
		if(!this.worldObj.isRemote && ticket != null) {
			if(this.loaderTicket == null) {
				this.loaderTicket = ticket;
				this.loaderTicket.bindEntity(this);
				this.loaderTicket.getModData();
			}
			ForgeChunkManager.forceChunk(this.loaderTicket, new ChunkCoordIntPair(this.chunkCoordX, this.chunkCoordZ));
		}
	}
}
