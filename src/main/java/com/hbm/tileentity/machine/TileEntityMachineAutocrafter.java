package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.container.ContainerAutocrafter;
import com.hbm.inventory.gui.GUIAutocrafter;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.ItemStackUtil;

import api.hbm.energy.IEnergyUser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileEntityMachineAutocrafter extends TileEntityMachineBase implements IEnergyUser, IGUIProvider {

	public static final String MODE_EXACT = "exact";
	public static final String MODE_WILDCARD = "wildcard";
	public String[] modes = new String[9];
	
	public List<IRecipe> recipes = new ArrayList<>();
	public int recipeIndex;
	public int recipeCount;

	public TileEntityMachineAutocrafter() {
		super(21);
	}
	
	public void initPattern(ItemStack stack, int i) {
		
		if(this.worldObj.isRemote) return;
		
		if(stack == null) {
			this.modes[i] = null;
			return;
		}
		
		List<String> names = ItemStackUtil.getOreDictNames(stack);

		if(iterateAndCheck(names, i ,"ingot") || iterateAndCheck(names, i ,"block") || iterateAndCheck(names, i ,"dust") || iterateAndCheck(names, i ,"nugget")) return;
		if(iterateAndCheck(names, i ,"plate")) return;
		
		if(stack.getHasSubtypes()) {
			this.modes[i] = TileEntityMachineAutocrafter.MODE_EXACT;
		} else {
			this.modes[i] = TileEntityMachineAutocrafter.MODE_WILDCARD;
		}
	}
	
	private boolean iterateAndCheck(List<String> names, int i, String prefix) {
		
		for(String s : names) {
			if(s.startsWith(prefix)) {
				this.modes[i] = s;
				return true;
			}
		}
		
		return false;
	}
	
	public void nextMode(int i) {
		
		if(this.worldObj.isRemote) return;
		
		ItemStack stack = this.slots[i];
		
		if(stack == null) {
			this.modes[i] = null;
			return;
		}
		
		if(this.modes[i] == null) {
			this.modes[i] = TileEntityMachineAutocrafter.MODE_EXACT;
		} else if(TileEntityMachineAutocrafter.MODE_EXACT.equals(this.modes[i])) {
			this.modes[i] = TileEntityMachineAutocrafter.MODE_WILDCARD;
		} else if(TileEntityMachineAutocrafter.MODE_WILDCARD.equals(this.modes[i])) {
			
			List<String> names = ItemStackUtil.getOreDictNames(stack);
			
			if(names.isEmpty()) {
				this.modes[i] = TileEntityMachineAutocrafter.MODE_EXACT;
			} else {
				this.modes[i] = names.get(0);
			}
		} else {
			
			List<String> names = ItemStackUtil.getOreDictNames(stack);
			
			if(names.size() < 2 || this.modes[i].equals(names.get(names.size() - 1))) {
				this.modes[i] = TileEntityMachineAutocrafter.MODE_EXACT;
			} else {
				
				for(int j = 0; j < names.size() - 1; j++) {
					
					if(this.modes[i].equals(names.get(j))) {
						this.modes[i] = names.get(j + 1);
						return;
					}
				}
			}
		}
	}
	
	public void nextTemplate() {
		
		if(this.worldObj.isRemote) return;
		
		this.recipeIndex++;
		
		if(this.recipeIndex >= this.recipes.size())
			this.recipeIndex = 0;
		
		if(!this.recipes.isEmpty()) {
			this.slots[9] = this.recipes.get(this.recipeIndex).getCraftingResult(getTemplateGrid());
		} else {
			this.slots[9] = null;
		}
	}

	@Override
	public String getName() {
		return "container.autocrafter";
	}
	
	protected InventoryCraftingAuto craftingInventory = new InventoryCraftingAuto(3, 3);

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			this.power = Library.chargeTEFromItems(this.slots, 20, this.power, TileEntityMachineAutocrafter.maxPower);
			this.updateStandardConnections(this.worldObj, this);
			
			if(!this.recipes.isEmpty() && this.power >= TileEntityMachineAutocrafter.consumption) {
				IRecipe recipe = this.recipes.get(this.recipeIndex);
				
				if(recipe.matches(getRecipeGrid(), this.worldObj)) {
					ItemStack stack = recipe.getCraftingResult(getRecipeGrid());
					
					if(stack != null) {
						
						boolean didCraft = false;
						
						if(this.slots[19] == null) {
							this.slots[19] = stack.copy();
							didCraft = true;
						} else if(this.slots[19].isItemEqual(stack) && ItemStack.areItemStackTagsEqual(stack, this.slots[19]) && this.slots[19].stackSize + stack.stackSize <= this.slots[19].getMaxStackSize()) {
							this.slots[19].stackSize += stack.stackSize;
							didCraft = true;
						}
						
						if(didCraft) {
							for(int i = 10; i < 19; i++) {
								
								ItemStack ingredient = getStackInSlot(i);

								if(ingredient != null) {
									decrStackSize(i, 1);

									if(this.slots[i] == null && ingredient.getItem().hasContainerItem(ingredient)) {
										ItemStack container = ingredient.getItem().getContainerItem(ingredient);

										if(container != null && container.isItemStackDamageable() && container.getItemDamage() > container.getMaxDamage()) {
											continue;
										}

										setInventorySlotContents(i, container);
									}
								}
							}
							
							this.power -= TileEntityMachineAutocrafter.consumption;
						}
					}
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			for(int i = 0; i < 9; i++) {
				if(this.modes[i] != null) {
					data.setString("mode" + i, this.modes[i]);
				}
			}
			data.setInteger("count", this.recipeCount);
			data.setInteger("rec", this.recipeIndex);
			networkPack(data, 15);
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.power = data.getLong("power");
		
		this.modes = new String[9];
		for(int i = 0; i < 9; i++) {
			if(data.hasKey("mode" + i)) {
				this.modes[i] = data.getString("mode" + i);
			}
		}
		this.recipeCount = data.getInteger("count");
		this.recipeIndex = data.getInteger("rec");
	}
	
	public void updateTemplateGrid() {

		this.recipes = getMatchingRecipes(getTemplateGrid());
		this.recipeCount = this.recipes.size();
		this.recipeIndex = 0;
		
		if(!this.recipes.isEmpty()) {
			this.slots[9] = this.recipes.get(this.recipeIndex).getCraftingResult(getTemplateGrid());
		} else {
			this.slots[9] = null;
		}
	}
	
	public List<IRecipe> getMatchingRecipes(InventoryCrafting grid) {
		List<IRecipe> recipes = new ArrayList<>();
		
		for(Object o : CraftingManager.getInstance().getRecipeList()) {
			IRecipe recipe = (IRecipe) o;
			
			if(recipe.matches(grid, this.worldObj)) {
				recipes.add(recipe);
			}
		}
		
		return recipes;
	}

	public int[] access = new int[] { 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 };
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return this.access;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		if(i == 19)
			return true;
		
		if(i > 9 && i < 19) {
			ItemStack filter = this.slots[i - 10];
			String mode = this.modes[i - 10];
			
			if(filter == null || mode == null || mode.isEmpty()) return true;
			
			if(isValidForFilter(filter, mode, stack)) {
				return false;
			}
			
			return true;
		}
		
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {

		//automatically prohibit any stacked item with a container
		//only allow insertion for the nine recipe slots
		if((stack.stackSize > 1 && stack.getItem().hasContainerItem(stack)) || slot < 10 || slot > 18)
			return false;
		
		//is the filter at this space null? no input.
		if(this.slots[slot - 10] == null)
			return false;
		
		//let's find all slots that this item could potentially go in
		List<Integer> validSlots = new ArrayList<>();
		for(int i = 0; i < 9; i++) {
			ItemStack filter = this.slots[i];
			String mode = this.modes[i];
			
			if(filter == null || mode == null || mode.isEmpty()) continue;
			
			if(isValidForFilter(filter, mode, stack)) {
				validSlots.add(i + 10);
				
				//if the current slot is valid and has no item in it, shortcut to true [*]
				if(i + 10 == slot && this.slots[slot] == null) {
					return true;
				}
			}
		}
		
		//if the slot we are looking at isn't valid, skip
		if(!validSlots.contains(slot)) {
			return false;
		}
		
		//assumption from [*]: the slot has to be valid by now, and it cannot be null
		int size = this.slots[slot].stackSize;
		
		//now we decide based on stacksize, woohoo
		for(Integer i : validSlots) {
			ItemStack valid = this.slots[i];
			
			if(valid == null) return false; //null? since slots[slot] is not null by now, this other slot needs the item more
			if(!(valid.isItemEqual(stack) && ItemStack.areItemStackTagsEqual(valid, stack))) continue; //different item anyway? out with it
			
			//if there is another slot that actually does need the same item more, cancel
			if(valid.stackSize < size)
				return false;
		}
		
		//prevent items with containers from stacking
		if(stack.getItem().hasContainerItem(stack))
			return false;
		
		//by now, we either already have filled the slot (if valid by filter and null) or weeded out all other options, which means it is good to go
		return true;
	}
	
	private boolean isValidForFilter(ItemStack filter, String mode, ItemStack input) {
		
		switch(mode) {
		case MODE_EXACT: return input.isItemEqual(filter) && ItemStack.areItemStackTagsEqual(input, filter);
		case MODE_WILDCARD: return input.getItem() == filter.getItem() && ItemStack.areItemStackTagsEqual(input, filter);
		default:
			List<String> keys = ItemStackUtil.getOreDictNames(input);
			return keys.contains(mode);
		}
	}
	
	public InventoryCrafting getTemplateGrid() {
		this.craftingInventory.loadIventory(this.slots, 0);
		return this.craftingInventory;
	}
	
	public InventoryCrafting getRecipeGrid() {
		this.craftingInventory.loadIventory(this.slots, 10);
		return this.craftingInventory;
	}
	
	public static class InventoryCraftingAuto extends InventoryCrafting {

		public InventoryCraftingAuto(int width, int height) {
			super(new ContainerBlank() /* "can't be null boo hoo" */, width, height);
		}
		
		public void loadIventory(ItemStack[] slots, int start) {
			
			for(int i = 0; i < getSizeInventory(); i++) {
				setInventorySlotContents(i, slots[start + i]);
			}
		}
		
		public static class ContainerBlank extends Container {
			@Override public void onCraftMatrixChanged(IInventory inventory) { }
			@Override public boolean canInteractWith(EntityPlayer player) { return false; }
		}
	}
	
	public static int consumption = 100;
	public static long maxPower = TileEntityMachineAutocrafter.consumption * 100;
	public long power;

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return TileEntityMachineAutocrafter.maxPower;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.power = nbt.getLong("power");
		
		for(int i = 0; i < 9; i++) {
			if(nbt.hasKey("mode" + i)) {
				this.modes[i] = nbt.getString("mode" + i);
			}
		}
		
		this.recipes = getMatchingRecipes(getTemplateGrid());
		this.recipeCount = this.recipes.size();
		this.recipeIndex = nbt.getInteger("rec");
		
		if(!this.recipes.isEmpty()) {
			this.slots[9] = this.recipes.get(this.recipeIndex).getCraftingResult(getTemplateGrid());
		} else {
			this.slots[9] = null;
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", this.power);
		
		for(int i = 0; i < 9; i++) {
			if(this.modes[i] != null) {
				nbt.setString("mode" + i, this.modes[i]);
			}
		}
		
		nbt.setInteger("rec", this.recipeIndex);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerAutocrafter(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIAutocrafter(player.inventory, this);
	}
}
