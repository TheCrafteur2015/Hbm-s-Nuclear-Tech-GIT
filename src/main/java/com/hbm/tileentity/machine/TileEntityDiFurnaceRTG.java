package com.hbm.tileentity.machine;

import com.hbm.blocks.machine.MachineDiFurnaceRTG;
import com.hbm.inventory.container.ContainerMachineDiFurnaceRTG;
import com.hbm.inventory.gui.GUIMachineDiFurnaceRTG;
import com.hbm.inventory.recipes.BlastFurnaceRecipes;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.RTGUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileEntityDiFurnaceRTG extends TileEntityMachineBase implements IGUIProvider
{
	public short progress;
	private short processSpeed = 0;
	// Edit as needed
	private static final short timeRequired = 1200;
	private static final int[] rtgIn = new int[] {3, 4, 5, 6, 7, 8};
	private String name;
	
	public TileEntityDiFurnaceRTG() {
		super(9);
	}

	public boolean canProcess() {
		if ((this.slots[0] == null || this.slots[1] == null) && !hasPower())
			return false;
		
		ItemStack recipeResult = BlastFurnaceRecipes.getOutput(this.slots[0], this.slots[1]);
		if (recipeResult == null)
			return false;
		else if (this.slots[2] == null)
			return true;
		else if (!this.slots[2].isItemEqual(recipeResult))
			return false;
		else if (this.slots[2].stackSize + recipeResult.stackSize > getInventoryStackLimit())
			return false;
		else if (this.slots[2].stackSize < getInventoryStackLimit() && this.slots[2].stackSize < this.slots[2].getMaxStackSize())
			return true;
		else
			return this.slots[2].stackSize < recipeResult.getMaxStackSize();
	}
	
	@Override
	public void updateEntity() {
		
		if(this.worldObj.isRemote)
			return;
		
		if(canProcess() && hasPower()) {
			this.progress += this.processSpeed;
			if(this.progress >= TileEntityDiFurnaceRTG.timeRequired) {
				processItem();
				this.progress = 0;
			}
		} else {
			this.progress = 0;
		}
		
		MachineDiFurnaceRTG.updateBlockState(isProcessing() || (canProcess() && hasPower()), getWorldObj(), this.xCoord, this.yCoord, this.zCoord);

		NBTTagCompound data = new NBTTagCompound();
		data.setShort("progress", this.progress);
		data.setShort("speed", this.processSpeed);
		networkPack(data, 10);
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.progress = nbt.getShort("progress");
		this.processSpeed = nbt.getShort("speed");
	}
	
	private void processItem() {
		
		if(canProcess()) {
			ItemStack recipeOut = BlastFurnaceRecipes.getOutput(this.slots[0], this.slots[1]);
			if(this.slots[2] == null)
				this.slots[2] = recipeOut.copy();
			else if(this.slots[2].isItemEqual(recipeOut))
				this.slots[2].stackSize += recipeOut.stackSize;

			for(int i = 0; i < 2; i++) {
				if(this.slots[i].stackSize <= 0)
					this.slots[i] = new ItemStack(this.slots[i].getItem().setFull3D());
				else
					this.slots[i].stackSize--;
				if(this.slots[i].stackSize <= 0)
					this.slots[i] = null;
			}
			markDirty();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.progress = nbt.getShort("progress");
		this.processSpeed = nbt.getShort("speed");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setShort("progress", this.progress);
		nbt.setShort("speed", this.processSpeed);
	}

	public int getDiFurnaceProgressScaled(int i) {
		return (this.progress * i) / TileEntityDiFurnaceRTG.timeRequired;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		this.slots[i] = stack;
		if(stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public void setCustomName(String name) {
		this.name = name;
	}

	public boolean hasPower() {
		this.processSpeed = (short) RTGUtil.updateRTGs(this.slots, TileEntityDiFurnaceRTG.rtgIn);
		return this.processSpeed >= 15;
	}

	public int getPower() {
		return this.processSpeed;
	}

	public boolean isProcessing() {
		return this.progress > 0;
	}

	@Override
	public String getInventoryName() {
		return hasCustomInventoryName() ? this.name : "container.diFurnaceRTG";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.name != null && this.name.length() > 0;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		if(i == 2) {
			return false;
		}
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 0 ? new int[] {2} : side == 1 ? new int[] {0} : new int[] {1};
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot == 2;
	}

	@Override
	public String getName() {
		return "container.diFurnaceRTG";
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineDiFurnaceRTG(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineDiFurnaceRTG(player.inventory, this);
	}

}
