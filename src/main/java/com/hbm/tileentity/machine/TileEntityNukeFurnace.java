package com.hbm.tileentity.machine;

import java.util.HashMap;

import com.hbm.blocks.machine.MachineNukeFurnace;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.container.ContainerNukeFurnace;
import com.hbm.inventory.gui.GUINukeFurnace;
import com.hbm.items.ItemCustomLore;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBreedingRod.BreedingRodType;
import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityNukeFurnace extends TileEntity implements ISidedInventory, IGUIProvider {

	private ItemStack slots[];
	
	public int dualCookTime;
	public int dualPower;
	public static final int maxPower = 1000;
	public static final int processingSpeed = 25;
	
	private static final int[] slots_top = new int[] {1};
	private static final int[] slots_bottom = new int[] {2, 0};
	private static final int[] slots_side = new int[] {0};
	
	private String customName;
	
	public TileEntityNukeFurnace() {
		this.slots = new ItemStack[3];
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
		return hasCustomInventoryName() ? this.customName : "container.nukeFurnace";
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
		return 64;
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
	
	//You scrubs aren't needed for anything (right now)
	@Override
	public void openInventory() {}
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return true;
	}
	
	public boolean hasItemPower(ItemStack itemStack) {
		return TileEntityNukeFurnace.getItemPower(itemStack) > 0;
	}
	
	private static int getItemPower(ItemStack stack) {
		if(stack == null) {
			return 0;
		} else {

			int power = TileEntityNukeFurnace.getFuelValue(stack);
			
			return power;
		}
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
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);
		
		this.dualPower = nbt.getShort("powerTime");
		this.dualCookTime = nbt.getShort("CookTime");
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
		nbt.setShort("powerTime", (short) this.dualPower);
		nbt.setShort("cookTime", (short) this.dualCookTime);
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
	
	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_)
    {
        return p_94128_1_ == 0 ? TileEntityNukeFurnace.slots_bottom : (p_94128_1_ == 1 ? TileEntityNukeFurnace.slots_top : TileEntityNukeFurnace.slots_side);
    }

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		if(i == 0)
		{
			if(itemStack.getItem() instanceof ItemCustomLore)
			{
				return true;
			}
			
			return false;
		}
		
		return true;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		if(i == 0)
		{
			if(itemStack.getItem() == ModItems.rod_empty || itemStack.getItem() == ModItems.rod_dual_empty || itemStack.getItem() == ModItems.rod_quad_empty)
			{
				return true;
			}
			
			return false;
		}
		
		return true;
	}
	
	public int getDiFurnaceProgressScaled(int i) {
		return (this.dualCookTime * i) / TileEntityNukeFurnace.processingSpeed;
	}
	
	public int getPowerRemainingScaled(int i) {
		return (this.dualPower * i) / TileEntityNukeFurnace.maxPower;
	}
	
	public boolean canProcess() {
		if(this.slots[1] == null)
		{
			return false;
		}
        ItemStack itemStack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[1]);
		if(itemStack == null)
		{
			return false;
		}
		
		if(this.slots[2] == null)
		{
			return true;
		}
		
		if(!this.slots[2].isItemEqual(itemStack)) {
			return false;
		}
		
		if(this.slots[2].stackSize < getInventoryStackLimit() && this.slots[2].stackSize < this.slots[2].getMaxStackSize()) {
			return true;
		}else{
			return this.slots[2].stackSize < itemStack.getMaxStackSize();
		}
	}
	
	private void processItem() {
		if(canProcess()) {
	        ItemStack itemStack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[1]);
			
			if(this.slots[2] == null)
			{
				this.slots[2] = itemStack.copy();
			}else if(this.slots[2].isItemEqual(itemStack)) {
				this.slots[2].stackSize += itemStack.stackSize;
			}
			
			for(int i = 1; i < 2; i++)
			{
				if(this.slots[i].stackSize <= 0)
				{
					this.slots[i] = new ItemStack(this.slots[i].getItem().setFull3D());
				}else{
					this.slots[i].stackSize--;
				}
				if(this.slots[i].stackSize <= 0)
				{
					this.slots[i] = null;
				}
			}
			
			{
				this.dualPower--;
			}
		}
	}
	
	public boolean hasPower() {
		return this.dualPower > 0;
	}
	
	public boolean isProcessing() {
		return this.dualCookTime > 0;
	}
	
	@Override
	public void updateEntity() {
		hasPower();
		boolean flag1 = false;
		
		if(!this.worldObj.isRemote)
		{
			if(hasItemPower(this.slots[0]) && this.dualPower == 0)
			{
				this.dualPower += TileEntityNukeFurnace.getItemPower(this.slots[0]);
				if(this.slots[0] != null)
				{
					flag1 = true;
					this.slots[0].stackSize--;
					if(this.slots[0].stackSize == 0)
					{
						this.slots[0] = this.slots[0].getItem().getContainerItem(this.slots[0]);
					}
				}
			}
			
			if(hasPower() && canProcess())
			{
				this.dualCookTime++;
				
				if(this.dualCookTime == TileEntityNukeFurnace.processingSpeed)
				{
					this.dualCookTime = 0;
					processItem();
					flag1 = true;
				}
			}else{
				this.dualCookTime = 0;
			}
			
			boolean trigger = true;
			
			if(hasPower() && canProcess() && this.dualCookTime == 0)
			{
				trigger = false;
			}
			
			if(trigger)
            {
                flag1 = true;
                MachineNukeFurnace.updateBlockState(this.dualCookTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
		}
		
		if(flag1)
		{
			markDirty();
		}
	}
	
	private static HashMap<ComparableStack, Integer> fuels = new HashMap<>();
	//for the int array: [0] => level (1-4) [1] => amount of operations
	
	/* 
	 * I really don't want to have to do this, but it's better then making a new class, for one TE, for not even recipes but just *fuels*
	 * 
	 * Who even uses this furnace? Nobody, but it's better then removing it without prior approval
	 */
	public static void registerFuels() {
		TileEntityNukeFurnace.setRecipe(BreedingRodType.TRITIUM, 5);
		TileEntityNukeFurnace.setRecipe(BreedingRodType.CO60, 10);
		TileEntityNukeFurnace.setRecipe(BreedingRodType.THF, 30);
		TileEntityNukeFurnace.setRecipe(BreedingRodType.U235, 50);
		TileEntityNukeFurnace.setRecipe(BreedingRodType.NP237, 30);
		TileEntityNukeFurnace.setRecipe(BreedingRodType.PU238, 20);
		TileEntityNukeFurnace.setRecipe(BreedingRodType.PU239, 50);
		TileEntityNukeFurnace.setRecipe(BreedingRodType.RGP, 30);
		TileEntityNukeFurnace.setRecipe(BreedingRodType.WASTE, 20);
	}
	
	/** Sets power for single, dual, and quad rods **/
	public static void setRecipe(BreedingRodType type, int power) {
		TileEntityNukeFurnace.fuels.put(new ComparableStack(new ItemStack(ModItems.rod, 1, type.ordinal())), power);
		TileEntityNukeFurnace.fuels.put(new ComparableStack(new ItemStack(ModItems.rod_dual, 1, type.ordinal())), power * 2);
		TileEntityNukeFurnace.fuels.put(new ComparableStack(new ItemStack(ModItems.rod_quad, 1, type.ordinal())), power * 4);
	}
	
	/**
	 * Returns an integer array of the fuel value of a certain stack
	 * @param stack
	 * @return an integer array (possibly null) with two fields, the HEAT value and the amount of operations
	 */
	public static int getFuelValue(ItemStack stack) {
		
		if(stack == null)
			return 0;
		
		ComparableStack sta = new ComparableStack(stack).makeSingular();
		if(TileEntityNukeFurnace.fuels.get(sta) != null)
			return TileEntityNukeFurnace.fuels.get(sta);
		
		return 0;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerNukeFurnace(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUINukeFurnace(player.inventory, this);
	}
}
