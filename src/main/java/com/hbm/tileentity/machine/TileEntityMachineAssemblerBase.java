package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.recipes.AssemblerRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemAssemblyTemplate;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.machine.storage.TileEntityCrateTemplate;
import com.hbm.util.InventoryUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyUser;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityMachineAssemblerBase extends TileEntityMachineBase implements IEnergyUser, IGUIProvider {

	public long power;
	public int[] progress;
	public int[] maxProgress;
	public boolean isProgressing;
	public boolean[] needsTemplateSwitch;
	
	int consumption = 100;
	int speed = 100;

	public TileEntityMachineAssemblerBase(int scount) {
		super(scount);
		
		int count = getRecipeCount();

		this.progress = new int[count];
		this.maxProgress = new int[count];
		this.needsTemplateSwitch = new boolean[count];
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			int count = getRecipeCount();
			
			this.isProgressing = false;
			this.power = Library.chargeTEFromItems(this.slots, getPowerSlot(), this.power, getMaxPower());
			
			for(int i = 0; i < count; i++) {
				unloadItems(i);
				loadItems(i);
			}

			
			for(int i = 0; i < count; i++) {
				if(!canProcess(i)) {
					this.progress[i] = 0;
				} else {
					this.isProgressing = true;
					process(i);
				}
			}
		}
	}
	
	protected boolean canProcess(int index) {
		
		int template = getTemplateIndex(index);
		
		if(this.slots[template] == null || this.slots[template].getItem() != ModItems.assembly_template)
			return false;

		List<AStack> recipe = AssemblerRecipes.getRecipeFromTempate(this.slots[template]);
		ItemStack output = AssemblerRecipes.getOutputFromTempate(this.slots[template]);
		
		if((recipe == null) || (this.power < this.consumption) || !hasRequiredItems(recipe, index) || !hasSpaceForItems(output, index)) return false;
		
		return true;
	}
	
	private boolean hasRequiredItems(List<AStack> recipe, int index) {
		int[] indices = getSlotIndicesFromIndex(index);
		return InventoryUtil.doesArrayHaveIngredients(this.slots, indices[0], indices[1], recipe.toArray(new AStack[0]));
	}
	
	private boolean hasSpaceForItems(ItemStack recipe, int index) {
		int[] indices = getSlotIndicesFromIndex(index);
		return InventoryUtil.doesArrayHaveSpace(this.slots, indices[2], indices[2], new ItemStack[] { recipe });
	}
	
	protected void process(int index) {
		
		this.power -= this.consumption;
		this.progress[index]++;
		
		if(this.slots[0] != null && this.slots[0].getItem() == ModItems.meteorite_sword_alloyed)
			this.slots[0] = new ItemStack(ModItems.meteorite_sword_machined); //fisfndmoivndlmgindgifgjfdnblfm
		
		int template = getTemplateIndex(index);

		List<AStack> recipe = AssemblerRecipes.getRecipeFromTempate(this.slots[template]);
		ItemStack output = AssemblerRecipes.getOutputFromTempate(this.slots[template]);
		int time = ItemAssemblyTemplate.getProcessTime(this.slots[template]);
		
		this.maxProgress[index] = time * this.speed / 100;
		
		if(this.progress[index] >= this.maxProgress[index]) {
			consumeItems(recipe, index);
			produceItems(output, index);
			this.progress[index] = 0;
			this.needsTemplateSwitch[index] = true;
			markDirty();
		}
	}
	
	private void consumeItems(List<AStack> recipe, int index) {
		
		int[] indices = getSlotIndicesFromIndex(index);
		
		for(AStack in : recipe) {
			if(in != null)
				InventoryUtil.tryConsumeAStack(this.slots, indices[0], indices[1], in);
		}
	}
	
	private void produceItems(ItemStack out, int index) {
		
		int[] indices = getSlotIndicesFromIndex(index);
		
		if(out != null) {
			InventoryUtil.tryAddItemToInventory(this.slots, indices[2], indices[2], out.copy());
		}
	}
	
	private void loadItems(int index) {
		
		int template = getTemplateIndex(index);

		DirPos[] positions = getInputPositions();
		int[] indices = getSlotIndicesFromIndex(index);

		for(DirPos coord : positions) {

			TileEntity te = this.worldObj.getTileEntity(coord.getX(), coord.getY(), coord.getZ());
				
			if(te instanceof IInventory) {

				IInventory inv = (IInventory) te;
				ISidedInventory sided = inv instanceof ISidedInventory ? (ISidedInventory) inv : null;
				int[] access = sided != null ? sided.getAccessibleSlotsFromSide(coord.getDir().ordinal()) : null;
				boolean templateCrate = te instanceof TileEntityCrateTemplate;

				if(templateCrate && this.slots[template] == null) {

					for(int i = 0; i < (access != null ? access.length : inv.getSizeInventory()); i++) {
						int slot = access != null ? access[i] : i;
						ItemStack stack = inv.getStackInSlot(slot);

						if(stack != null && stack.getItem() == ModItems.assembly_template && (sided == null || sided.canExtractItem(slot, stack, 0))) {
							this.slots[template] = stack.copy();
							sided.setInventorySlotContents(slot, null);
							this.needsTemplateSwitch[index] = false;
							break;
						}
					}
				}
					
				boolean noTemplate = this.slots[template] == null || this.slots[template].getItem() != ModItems.assembly_template;

				if(!noTemplate) {

					List<AStack> recipe = AssemblerRecipes.getRecipeFromTempate(this.slots[template]);

					if(recipe != null) {

						for(AStack ingredient : recipe) {

							outer: while(!InventoryUtil.doesArrayHaveIngredients(this.slots, indices[0], indices[1], ingredient)) {

								boolean found = false;

								for(int i = 0; i < (access != null ? access.length : inv.getSizeInventory()); i++) {
									
									int slot = access != null ? access[i] : i;
									ItemStack stack = inv.getStackInSlot(slot);
									if(ingredient.matchesRecipe(stack, true) && (sided == null || sided.canExtractItem(slot, stack, 0))) {
										found = true;

										for(int j = indices[0]; j <= indices[1]; j++) {

											if(this.slots[j] != null && this.slots[j].stackSize < this.slots[j].getMaxStackSize() & InventoryUtil.doesStackDataMatch(this.slots[j], stack)) {
												inv.decrStackSize(slot, 1);
												this.slots[j].stackSize++;
												continue outer;
											}
										}

										for(int j = indices[0]; j <= indices[1]; j++) {

											if(this.slots[j] == null) {
												this.slots[j] = stack.copy();
												this.slots[j].stackSize = 1;
												inv.decrStackSize(slot, 1);
												continue outer;
											}
										}
									}
								}

								if(!found) break outer;
							}
						}
					}
				}
			}
		}
	}
	
	private void unloadItems(int index) {

		DirPos[] positions = getOutputPositions();
		int[] indices = getSlotIndicesFromIndex(index);
		
		for(DirPos coord : positions) {
			
			TileEntity te = this.worldObj.getTileEntity(coord.getX(), coord.getY(), coord.getZ());
			
			if(te instanceof IInventory) {
				
				IInventory inv = (IInventory) te;
				ISidedInventory sided = inv instanceof ISidedInventory ? (ISidedInventory) inv : null;
				int[] access = sided != null ? sided.getAccessibleSlotsFromSide(coord.getDir().ordinal()) : null;
				
				int i = indices[2];
				ItemStack out = this.slots[i];
				
				int template = getTemplateIndex(index);
				if(this.needsTemplateSwitch[index] && te instanceof TileEntityCrateTemplate && this.slots[template] != null) {
					out = this.slots[template];
					i = template;
				}

				if(out != null) {

					for(int j = 0; j < (access != null ? access.length : inv.getSizeInventory()); j++) {

						int slot = access != null ? access[j] : j;
						
						if(!(sided != null ? sided.canInsertItem(slot, out, coord.getDir().ordinal()) : inv.isItemValidForSlot(slot, out)))
							continue;
						
						ItemStack target = inv.getStackInSlot(slot);

						if(InventoryUtil.doesStackDataMatch(out, target) && target.stackSize < target.getMaxStackSize() && target.stackSize < inv.getInventoryStackLimit()) {
							decrStackSize(i, 1);
							target.stackSize++;
							return;
						}
					}

					for(int j = 0; j < (access != null ? access.length : inv.getSizeInventory()); j++) {

						int slot = access != null ? access[j] : j;
						
						if(!inv.isItemValidForSlot(slot, out))
							continue;

						if(inv.getStackInSlot(slot) == null && (sided != null ? sided.canInsertItem(slot, out, coord.getDir().ordinal()) : inv.isItemValidForSlot(slot, out))) {
							ItemStack copy = out.copy();
							copy.stackSize = 1;
							inv.setInventorySlotContents(slot, copy);
							decrStackSize(i, 1);
							return;
						}
					}
				}
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.power = nbt.getLong("power");
		if(nbt.hasKey("progress")) this.progress = nbt.getIntArray("progress");
		if(nbt.hasKey("maxProgress")) this.maxProgress = nbt.getIntArray("maxProgress");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", this.power);
		nbt.setIntArray("progress", this.progress);
		nbt.setIntArray("maxProgress", this.maxProgress);
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	public abstract int getRecipeCount();
	public abstract int getTemplateIndex(int index);
	
	/**
	 * @param index
	 * @return A size 3 int array containing min input, max input and output indices in that order.
	 */
	public abstract int[] getSlotIndicesFromIndex(int index);
	public abstract DirPos[] getInputPositions();
	public abstract DirPos[] getOutputPositions();
	public abstract int getPowerSlot();
}
