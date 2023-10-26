package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.recipes.ChemplantRecipes;
import com.hbm.inventory.recipes.ChemplantRecipes.ChemRecipe;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.InventoryUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidUser;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Base class for single and multi chemplants.
 * Most stuff should be handled by this class automatically, given the slots and indices are defined correctly
 * Does not sync automatically, nor handle upgrades
 * Slot indices are mostly free game, but battery has to be slot 0
 * Tanks follow the order R1(I1, I2, O1, O2), R2(I1, I2, O1, O2) ...
 * @author hbm
 */
public abstract class TileEntityMachineChemplantBase extends TileEntityMachineBase implements IEnergyUser, IFluidUser, IGUIProvider {

	public long power;
	public int[] progress;
	public int[] maxProgress;
	public boolean isProgressing;
	
	public FluidTank[] tanks;
	
	int consumption = 100;
	int speed = 100;

	public TileEntityMachineChemplantBase(int scount) {
		super(scount);
		
		int count = getRecipeCount();

		this.progress = new int[count];
		this.maxProgress = new int[count];
		
		this.tanks = new FluidTank[4 * count];
		for(int i = 0; i < 4 * count; i++) {
			this.tanks[i] = new FluidTank(Fluids.NONE, getTankCapacity(), i);
		}
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			int count = getRecipeCount();
			
			this.isProgressing = false;
			this.power = Library.chargeTEFromItems(this.slots, 0, this.power, getMaxPower());
			
			for(int i = 0; i < count; i++) {
				loadItems(i);
				unloadItems(i);
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
		
		if(this.slots[template] == null || this.slots[template].getItem() != ModItems.chemistry_template)
			return false;
		
		ChemRecipe recipe = ChemplantRecipes.indexMapping.get(this.slots[template].getItemDamage());
		
		if(recipe == null)
			return false;
		
		setupTanks(recipe, index);
		
		if((this.power < this.consumption) || !hasRequiredFluids(recipe, index) || !hasSpaceForFluids(recipe, index) || !hasRequiredItems(recipe, index)) return false;
		if(!hasSpaceForItems(recipe, index)) return false;
		
		return true;
	}
	
	private void setupTanks(ChemRecipe recipe, int index) {
		if(recipe.inputFluids[0] != null) this.tanks[index * 4].withPressure(recipe.inputFluids[0].pressure).setTankType(recipe.inputFluids[0].type);		else this.tanks[index * 4].setTankType(Fluids.NONE);
		if(recipe.inputFluids[1] != null) this.tanks[index * 4 + 1].withPressure(recipe.inputFluids[1].pressure).setTankType(recipe.inputFluids[1].type);	else this.tanks[index * 4 + 1].setTankType(Fluids.NONE);
		if(recipe.outputFluids[0] != null) this.tanks[index * 4 + 2].withPressure(recipe.outputFluids[0].pressure).setTankType(recipe.outputFluids[0].type);	else this.tanks[index * 4 + 2].setTankType(Fluids.NONE);
		if(recipe.outputFluids[1] != null) this.tanks[index * 4 + 3].withPressure(recipe.outputFluids[1].pressure).setTankType(recipe.outputFluids[1].type);	else this.tanks[index * 4 + 3].setTankType(Fluids.NONE);
	}
	
	private boolean hasRequiredFluids(ChemRecipe recipe, int index) {
		if((recipe.inputFluids[0] != null && this.tanks[index * 4].getFill() < recipe.inputFluids[0].fill) || (recipe.inputFluids[1] != null && this.tanks[index * 4 + 1].getFill() < recipe.inputFluids[1].fill)) return false;
		return true;
	}
	
	private boolean hasSpaceForFluids(ChemRecipe recipe, int index) {
		if((recipe.outputFluids[0] != null && this.tanks[index * 4 + 2].getFill() + recipe.outputFluids[0].fill > this.tanks[index * 4 + 2].getMaxFill()) || (recipe.outputFluids[1] != null && this.tanks[index * 4 + 3].getFill() + recipe.outputFluids[1].fill > this.tanks[index * 4 + 3].getMaxFill())) return false;
		return true;
	}
	
	private boolean hasRequiredItems(ChemRecipe recipe, int index) {
		int[] indices = getSlotIndicesFromIndex(index);
		return InventoryUtil.doesArrayHaveIngredients(this.slots, indices[0], indices[1], recipe.inputs);
	}
	
	private boolean hasSpaceForItems(ChemRecipe recipe, int index) {
		int[] indices = getSlotIndicesFromIndex(index);
		return InventoryUtil.doesArrayHaveSpace(this.slots, indices[2], indices[3], recipe.outputs);
	}
	
	protected void process(int index) {
		
		this.power -= this.consumption;
		this.progress[index]++;
		
		if(this.slots[0] != null && this.slots[0].getItem() == ModItems.meteorite_sword_machined)
			this.slots[0] = new ItemStack(ModItems.meteorite_sword_treated); //fisfndmoivndlmgindgifgjfdnblfm
		
		int template = getTemplateIndex(index);
		ChemRecipe recipe = ChemplantRecipes.indexMapping.get(this.slots[template].getItemDamage());
		
		this.maxProgress[index] = recipe.getDuration() * this.speed / 100;
		
		if(this.maxProgress[index] <= 0) this.maxProgress[index] = 1;
		
		if(this.progress[index] >= this.maxProgress[index]) {
			consumeFluids(recipe, index);
			produceFluids(recipe, index);
			consumeItems(recipe, index);
			produceItems(recipe, index);
			this.progress[index] = 0;
			markDirty();
		}
	}
	
	private void consumeFluids(ChemRecipe recipe, int index) {
		if(recipe.inputFluids[0] != null) this.tanks[index * 4].setFill(this.tanks[index * 4].getFill() - recipe.inputFluids[0].fill);
		if(recipe.inputFluids[1] != null) this.tanks[index * 4 + 1].setFill(this.tanks[index * 4 + 1].getFill() - recipe.inputFluids[1].fill);
	}
	
	private void produceFluids(ChemRecipe recipe, int index) {
		if(recipe.outputFluids[0] != null) this.tanks[index * 4 + 2].setFill(this.tanks[index * 4 + 2].getFill() + recipe.outputFluids[0].fill);
		if(recipe.outputFluids[1] != null) this.tanks[index * 4 + 3].setFill(this.tanks[index * 4 + 3].getFill() + recipe.outputFluids[1].fill);
	}
	
	private void consumeItems(ChemRecipe recipe, int index) {
		
		int[] indices = getSlotIndicesFromIndex(index);
		
		for(AStack in : recipe.inputs) {
			if(in != null)
				InventoryUtil.tryConsumeAStack(this.slots, indices[0], indices[1], in);
		}
	}
	
	private void produceItems(ChemRecipe recipe, int index) {
		
		int[] indices = getSlotIndicesFromIndex(index);
		
		for(ItemStack out : recipe.outputs) {
			if(out != null)
				InventoryUtil.tryAddItemToInventory(this.slots, indices[2], indices[3], out.copy());
		}
	}
	
	private void loadItems(int index) {
		
		int template = getTemplateIndex(index);
		if(this.slots[template] == null || this.slots[template].getItem() != ModItems.chemistry_template)
			return;
		
		ChemRecipe recipe = ChemplantRecipes.indexMapping.get(this.slots[template].getItemDamage());
		
		if(recipe != null) {
			
			DirPos[] positions = getInputPositions();
			int[] indices = getSlotIndicesFromIndex(index);
			
			for(DirPos coord : positions) {

				TileEntity te = this.worldObj.getTileEntity(coord.getX(), coord.getY(), coord.getZ());
				
				if(te instanceof IInventory) {
					
					IInventory inv = (IInventory) te;
					ISidedInventory sided = inv instanceof ISidedInventory ? (ISidedInventory) inv : null;
					int[] access = sided != null ? sided.getAccessibleSlotsFromSide(coord.getDir().ordinal()) : null;
					
					for(AStack ingredient : recipe.inputs) {
						
						outer:
						while(!InventoryUtil.doesArrayHaveIngredients(this.slots, indices[0], indices[1], ingredient)) {
							
							boolean found = false;
							
							for(int i = 0; i < (access != null ? access.length : inv.getSizeInventory()); i++) {

								int slot = access != null ? access[i] : i;
								ItemStack stack = inv.getStackInSlot(slot);
								if(ingredient.matchesRecipe(stack, true) && (sided == null || sided.canExtractItem(slot, stack, 0))) {
									
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
	
	private void unloadItems(int index) {

		DirPos[] positions = getOutputPositions();
		int[] indices = getSlotIndicesFromIndex(index);
		
		for(DirPos coord : positions) {

			TileEntity te = this.worldObj.getTileEntity(coord.getX(), coord.getY(), coord.getZ());
			
			if(te instanceof IInventory) {
				
				IInventory inv = (IInventory) te;
				ISidedInventory sided = inv instanceof ISidedInventory ? (ISidedInventory) inv : null;
				int[] access = sided != null ? sided.getAccessibleSlotsFromSide(coord.getDir().ordinal()) : null;
				
				for(int i = indices[2]; i <= indices[3]; i++) {
					
					ItemStack out = this.slots[i];
					
					if(out != null) {
						
						for(int j = 0; j < (access != null ? access.length : inv.getSizeInventory()); j++) {

							int slot = access != null ? access[j] : j;
							
							if(!inv.isItemValidForSlot(slot, out))
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
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	/*public int getFluidFill(FluidType type) {
		
		int fill = 0;
		
		for(FluidTank tank : inTanks()) {
			if(tank.getTankType() == type) {
				fill += tank.getFill();
			}
		}
		
		for(FluidTank tank : outTanks()) {
			if(tank.getTankType() == type) {
				fill += tank.getFill();
			}
		}
		
		return fill;
	}*/

	/* For input only! */
	public int getMaxFluidFill(FluidType type) {
		
		int maxFill = 0;
		
		for(FluidTank tank : inTanks()) {
			if(tank.getTankType() == type) {
				maxFill += tank.getMaxFill();
			}
		}
		
		return maxFill;
	}
	
	protected List<FluidTank> inTanks() {

		List<FluidTank> inTanks = new ArrayList<>();
		
		for (FluidTank tank : this.tanks) {
			if(tank.index % 4 < 2) {
				inTanks.add(tank);
			}
		}
		
		return inTanks;
	}

	/*public void receiveFluid(int amount, FluidType type) {
		
		if(amount <= 0)
			return;
		
		List<FluidTank> rec = new ArrayList<>();
		
		for(FluidTank tank : inTanks()) {
			if(tank.getTankType() == type) {
				rec.add(tank);
			}
		}
		
		if(rec.size() == 0)
			return;
		
		int demand = 0;
		List<Integer> weight = new ArrayList<>();
		
		for(FluidTank tank : rec) {
			int fillWeight = tank.getMaxFill() - tank.getFill();
			demand += fillWeight;
			weight.add(fillWeight);
		}
		
		for(int i = 0; i < rec.size(); i++) {
			
			if(demand <= 0)
				break;
			
			FluidTank tank = rec.get(i);
			int fillWeight = weight.get(i);
			int part = (int) (Math.min((long)amount, (long)demand) * (long)fillWeight / (long)demand);
			
			tank.setFill(tank.getFill() + part);
		}
	}*/

	public int getFluidFillForTransfer(FluidType type, int pressure) {
		
		int fill = 0;
		
		for(FluidTank tank : outTanks()) {
			if(tank.getTankType() == type && tank.getPressure() == pressure) {
				fill += tank.getFill();
			}
		}
		
		return fill;
	}
	
	public void transferFluid(int amount, FluidType type, int pressure) {
		
		/*
		 * this whole new fluid mumbo jumbo extra abstraction layer might just be a bandaid
		 * on the gushing wound that is the current fluid systemm but i'll be damned if it
		 * didn't at least do what it's supposed to. half a decade and we finally have multi
		 * tank support for tanks with matching fluid types!!
		 */
		if(amount <= 0)
			return;
		
		List<FluidTank> send = new ArrayList<>();
		
		for(FluidTank tank : outTanks()) {
			if(tank.getTankType() == type && tank.getPressure() == pressure) {
				send.add(tank);
			}
		}
		
		if(send.size() == 0)
			return;
		
		int offer = 0;
		List<Integer> weight = new ArrayList<>();
		
		for(FluidTank tank : send) {
			int fillWeight = tank.getFill();
			offer += fillWeight;
			weight.add(fillWeight);
		}
		
		int tracker = amount;
		
		for(int i = 0; i < send.size(); i++) {
			
			FluidTank tank = send.get(i);
			int fillWeight = weight.get(i);
			int part = amount * fillWeight / offer;
			
			tank.setFill(tank.getFill() - part);
			tracker -= part;
		}
		
		//making sure to properly deduct even the last mB lost by rounding errors
		for(int i = 0; i < 100 && tracker > 0; i++) {
			
			FluidTank tank = send.get(i % send.size());
			
			if(tank.getFill() > 0) {
				int total = Math.min(tank.getFill(), tracker);
				tracker -= total;
				tank.setFill(tank.getFill() - total);
			}
		}
	}
	
	protected List<FluidTank> outTanks() {
		
		List<FluidTank> outTanks = new ArrayList<>();
		
		for (FluidTank tank : this.tanks) {
			if(tank.index % 4 > 1) {
				outTanks.add(tank);
			}
		}
		
		return outTanks;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return this.tanks;
	}

	@Override
	public long transferFluid(FluidType type, int pressure, long fluid) {
		int amount = (int) fluid;
		
		if(amount <= 0)
			return 0;
		
		List<FluidTank> rec = new ArrayList<>();
		
		for(FluidTank tank : inTanks()) {
			if(tank.getTankType() == type && tank.getPressure() == pressure) {
				rec.add(tank);
			}
		}
		
		if(rec.size() == 0)
			return fluid;
		
		int demand = 0;
		List<Integer> weight = new ArrayList<>();
		
		for(FluidTank tank : rec) {
			int fillWeight = tank.getMaxFill() - tank.getFill();
			demand += fillWeight;
			weight.add(fillWeight);
		}
		
		for(int i = 0; i < rec.size(); i++) {
			
			if(demand <= 0)
				break;
			
			FluidTank tank = rec.get(i);
			int fillWeight = weight.get(i);
			int part = (int) (Math.min((long)amount, (long)demand) * (long)fillWeight / (long)demand);
			
			tank.setFill(tank.getFill() + part);
			fluid -= part;
		}
		
		return fluid;
	}

	@Override
	public long getDemand(FluidType type, int pressure) {
		return getMaxFluidFill(type) - getFluidFillForTransfer(type, pressure);
	}

	@Override
	public long getTotalFluidForSend(FluidType type, int pressure) {
		return getFluidFillForTransfer(type, pressure);
	}

	@Override
	public void removeFluidForTransfer(FluidType type, int pressure, long amount) {
		this.transferFluid((int) amount, type, pressure);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.power = nbt.getLong("power");
		this.progress = nbt.getIntArray("progress");
		
		if(this.progress.length == 0)
			this.progress = new int[getRecipeCount()];
		
		for(int i = 0; i < this.tanks.length; i++) {
			this.tanks[i].readFromNBT(nbt, "t" + i);
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", this.power);
		nbt.setIntArray("progress", this.progress);
		
		for(int i = 0; i < this.tanks.length; i++) {
			this.tanks[i].writeToNBT(nbt, "t" + i);
		}
	}

	public abstract int getRecipeCount();
	public abstract int getTankCapacity();
	public abstract int getTemplateIndex(int index);
	
	/**
	 * @param index
	 * @return A size 4 int array containing min input, max input, min output and max output indices in that order.
	 */
	public abstract int[] getSlotIndicesFromIndex(int index);
	public abstract DirPos[] getInputPositions();
	public abstract DirPos[] getOutputPositions();
}
