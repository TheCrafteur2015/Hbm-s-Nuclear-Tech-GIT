package com.hbm.tileentity.machine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.inventory.container.ContainerCrucible;
import com.hbm.inventory.gui.GUICrucible;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.inventory.recipes.CrucibleRecipes;
import com.hbm.inventory.recipes.CrucibleRecipes.CrucibleRecipe;
import com.hbm.items.ModItems;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.CrucibleUtil;

import api.hbm.block.ICrucibleAcceptor;
import api.hbm.tile.IHeatSource;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCrucible extends TileEntityMachineBase implements IGUIProvider, ICrucibleAcceptor, IConfigurableMachine {

	public int heat;
	public int progress;
	
	public List<MaterialStack> recipeStack = new ArrayList<>();
	public List<MaterialStack> wasteStack = new ArrayList<>();

	/* CONFIGURABLE CONSTANTS */
	//because eclipse's auto complete is dumb as a fucking rock, it's now called "ZCapacity" so it's listed AFTER the actual stacks in the auto complete list.
	//also martin i know you read these: no i will not switch to intellij after using eclipse for 8 years.
	public static int recipeZCapacity = MaterialShapes.BLOCK.q(16);
	public static int wasteZCapacity = MaterialShapes.BLOCK.q(16);
	public static int processTime = 20_000;
	public static double diffusion = 0.25D;
	public static int maxHeat = 100_000;

	@Override
	public String getConfigName() {
		return "crucible";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		TileEntityCrucible.recipeZCapacity = IConfigurableMachine.grab(obj, "I:recipeCapacity", TileEntityCrucible.recipeZCapacity);
		TileEntityCrucible.wasteZCapacity = IConfigurableMachine.grab(obj, "I:wasteCapacity", TileEntityCrucible.wasteZCapacity);
		TileEntityCrucible.processTime = IConfigurableMachine.grab(obj, "I:processHeat", TileEntityCrucible.processTime);
		TileEntityCrucible.diffusion = IConfigurableMachine.grab(obj, "D:diffusion", TileEntityCrucible.diffusion);
		TileEntityCrucible.maxHeat = IConfigurableMachine.grab(obj, "I:heatCap", TileEntityCrucible.maxHeat);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("I:recipeCapacity").value(TileEntityCrucible.recipeZCapacity);
		writer.name("I:wasteCapacity").value(TileEntityCrucible.wasteZCapacity);
		writer.name("I:processHeat").value(TileEntityCrucible.processTime);
		writer.name("D:diffusion").value(TileEntityCrucible.diffusion);
		writer.name("I:heatCap").value(TileEntityCrucible.maxHeat);
	}

	public TileEntityCrucible() {
		super(10);
	}

	@Override
	public String getName() {
		return "container.machineCrucible";
	}

	@Override
	public int getInventoryStackLimit() {
		return 1; //prevents clogging
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			tryPullHeat();
			
			/* collect items */
			if(this.worldObj.getTotalWorldTime() % 5 == 0) {
				List<EntityItem> list = this.worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(this.xCoord - 0.5, this.yCoord + 0.5, this.zCoord - 0.5, this.xCoord + 1.5, this.yCoord + 1, this.zCoord + 1.5));
				
				for(EntityItem item : list) {
					ItemStack stack = item.getEntityItem();
					if(isItemSmeltable(stack)) {
						
						for(int i = 1; i < 10; i++) {
							if(this.slots[i] == null) {
								
								if(stack.stackSize == 1) {
									this.slots[i] = stack.copy();
									item.setDead();
									break;
								} else {
									this.slots[i] = stack.copy();
									this.slots[i].stackSize = 1;
									stack.stackSize--;
								}
								
								markChanged();
							}
						}
					}
				}
			}

			int totalCap = TileEntityCrucible.recipeZCapacity + TileEntityCrucible.wasteZCapacity;
			int totalMass = 0;

			for(MaterialStack stack : this.recipeStack) totalMass += stack.amount;
			for(MaterialStack stack : this.wasteStack) totalMass += stack.amount;
			
			double level = ((double) totalMass / (double) totalCap) * 0.875D;
			
			List<EntityLivingBase> living = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, this.xCoord + 0.5, this.yCoord + 0.5 + level, this.zCoord + 0.5).expand(1, 0, 1));
			for(EntityLivingBase entity : living) {
				entity.attackEntityFrom(DamageSource.lava, 5F);
				entity.setFire(5);
			}
			
			/* smelt items from buffer */
			if(!trySmelt()) {
				this.progress = 0;
			}
			
			tryRecipe();
			
			/* pour waste stack */
			if(!this.wasteStack.isEmpty()) {
				
				ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset).getOpposite();
				Vec3 impact = Vec3.createVectorHelper(0, 0, 0);
				MaterialStack didPour = CrucibleUtil.pourFullStack(this.worldObj, this.xCoord + 0.5D + dir.offsetX * 1.875D, this.yCoord + 0.25D, this.zCoord + 0.5D + dir.offsetZ * 1.875D, 6, true, this.wasteStack, MaterialShapes.NUGGET.q(2), impact);
				
				if(didPour != null) {
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "foundry");
					data.setInteger("color", didPour.material.moltenColor);
					data.setByte("dir", (byte) dir.ordinal());
					data.setFloat("off", 0.625F);
					data.setFloat("base", 0.625F);
					data.setFloat("len", Math.max(1F, this.yCoord - (float) (Math.ceil(impact.yCoord) - 0.875)));
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, this.xCoord + 0.5D + dir.offsetX * 1.875D, this.yCoord, this.zCoord + 0.5D + dir.offsetZ * 1.875D), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord + 0.5, this.yCoord + 1, this.zCoord + 0.5, 50));
				
				}

				PollutionHandler.incrementPollution(this.worldObj, this.xCoord, this.yCoord, this.zCoord, PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND / 2F);
			}
			
			/* pour recipe stack */
			if(!this.recipeStack.isEmpty()) {
				
				ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
				List<MaterialStack> toCast = new ArrayList<>();
				
				CrucibleRecipe recipe = getLoadedRecipe();
				//if no recipe is loaded, everything from the recipe stack will be drainable
				if(recipe == null) {
					toCast.addAll(this.recipeStack);
				} else {
					
					for(MaterialStack stack : this.recipeStack) {
						for(MaterialStack output : recipe.output) {
							if(stack.material == output.material) {
								toCast.add(stack);
								break;
							}
						}
					}
				}
				
				Vec3 impact = Vec3.createVectorHelper(0, 0, 0);
				MaterialStack didPour = CrucibleUtil.pourFullStack(this.worldObj, this.xCoord + 0.5D + dir.offsetX * 1.875D, this.yCoord + 0.25D, this.zCoord + 0.5D + dir.offsetZ * 1.875D, 6, true, toCast, MaterialShapes.NUGGET.q(2), impact);

				if(didPour != null) {
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "foundry");
					data.setInteger("color", didPour.material.moltenColor);
					data.setByte("dir", (byte) dir.ordinal());
					data.setFloat("off", 0.625F);
					data.setFloat("base", 0.625F);
					data.setFloat("len", Math.max(1F, this.yCoord - (float) (Math.ceil(impact.yCoord) - 0.875)));
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, this.xCoord + 0.5D + dir.offsetX * 1.875D, this.yCoord, this.zCoord + 0.5D + dir.offsetZ * 1.875D), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord + 0.5, this.yCoord + 1, this.zCoord + 0.5, 50));
				
				}

				PollutionHandler.incrementPollution(this.worldObj, this.xCoord, this.yCoord, this.zCoord, PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND / 2F);
			}

			/* clean up stacks */
			this.recipeStack.removeIf(o -> o.amount <= 0);
			this.wasteStack.removeIf(x -> x.amount <= 0);
			
			/* sync */
			NBTTagCompound data = new NBTTagCompound();
			int[] rec = new int[this.recipeStack.size() * 2];
			int[] was = new int[this.wasteStack.size() * 2];
			for(int i = 0; i < this.recipeStack.size(); i++) { MaterialStack sta = this.recipeStack.get(i); rec[i * 2] = sta.material.id; rec[i * 2 + 1] = sta.amount; }
			for(int i = 0; i < this.wasteStack.size(); i++) { MaterialStack sta = this.wasteStack.get(i); was[i * 2] = sta.material.id; was[i * 2 + 1] = sta.amount; }
			data.setIntArray("rec", rec);
			data.setIntArray("was", was);
			data.setInteger("progress", this.progress);
			data.setInteger("heat", this.heat);
			networkPack(data, 25);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {

		this.recipeStack.clear();
		this.wasteStack.clear();
		
		int[] rec = nbt.getIntArray("rec");
		for(int i = 0; i < rec.length / 2; i++) {
			this.recipeStack.add(new MaterialStack(Mats.matById.get(rec[i * 2]), rec[i * 2 + 1]));
		}
		
		int[] was = nbt.getIntArray("was");
		for(int i = 0; i < was.length / 2; i++) {
			this.wasteStack.add(new MaterialStack(Mats.matById.get(was[i * 2]), was[i * 2 + 1]));
		}
		
		this.progress = nbt.getInteger("progress");
		this.heat = nbt.getInteger("heat");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		int[] rec = nbt.getIntArray("rec");
		for(int i = 0; i < rec.length / 2; i++) {
			this.recipeStack.add(new MaterialStack(Mats.matById.get(rec[i * 2]), rec[i * 2 + 1]));
		}
		
		int[] was = nbt.getIntArray("was");
		for(int i = 0; i < was.length / 2; i++) {
			this.wasteStack.add(new MaterialStack(Mats.matById.get(was[i * 2]), was[i * 2 + 1]));
		}
		
		this.progress = nbt.getInteger("progress");
		this.heat = nbt.getInteger("heat");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		int[] rec = new int[this.recipeStack.size() * 2];
		int[] was = new int[this.wasteStack.size() * 2];
		for(int i = 0; i < this.recipeStack.size(); i++) { MaterialStack sta = this.recipeStack.get(i); rec[i * 2] = sta.material.id; rec[i * 2 + 1] = sta.amount; }
		for(int i = 0; i < this.wasteStack.size(); i++) { MaterialStack sta = this.wasteStack.get(i); was[i * 2] = sta.material.id; was[i * 2 + 1] = sta.amount; }
		nbt.setIntArray("rec", rec);
		nbt.setIntArray("was", was);
		nbt.setInteger("progress", this.progress);
		nbt.setInteger("heat", this.heat);
	}
	
	protected void tryPullHeat() {
		
		if(this.heat >= TileEntityCrucible.maxHeat) return;
		
		TileEntity con = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord);
		
		if(con instanceof IHeatSource) {
			IHeatSource source = (IHeatSource) con;
			int diff = source.getHeatStored() - this.heat;
			
			if(diff == 0) {
				return;
			}
			
			if(diff > 0) {
				diff = (int) Math.ceil(diff * TileEntityCrucible.diffusion);
				source.useUpHeat(diff);
				this.heat += diff;
				if(this.heat > TileEntityCrucible.maxHeat)
					this.heat = TileEntityCrucible.maxHeat;
				return;
			}
		}
		
		this.heat = Math.max(this.heat - Math.max(this.heat / 1000, 1), 0);
	}
	
	protected boolean trySmelt() {
		
		if(this.heat < TileEntityCrucible.maxHeat / 2) return false;
		
		int slot = getFirstSmeltableSlot();
		if(slot == -1) return false;
		
		int delta = this.heat - (TileEntityCrucible.maxHeat / 2);
		delta *= 0.05;
		
		this.progress += delta;
		this.heat -= delta;
		
		if(this.progress >= TileEntityCrucible.processTime) {
			this.progress = 0;
			
			List<MaterialStack> materials = Mats.getSmeltingMaterialsFromItem(this.slots[slot]);
			CrucibleRecipe recipe = getLoadedRecipe();
			
			for(MaterialStack material : materials) {
				boolean mainStack = recipe != null && (getQuantaFromType(recipe.input, material.material) > 0 || getQuantaFromType(recipe.output, material.material) > 0);
				
				if(mainStack) {
					addToStack(this.recipeStack, material);
				} else {
					addToStack(this.wasteStack, material);
				}
			}
			
			decrStackSize(slot, 1);
		}
		
		return true;
	}
	
	protected void tryRecipe() {
		CrucibleRecipe recipe = getLoadedRecipe();
		
		if((recipe == null) || (this.worldObj.getTotalWorldTime() % recipe.frequency > 0)) return;
		
		for(MaterialStack stack : recipe.input) {
			if(getQuantaFromType(this.recipeStack, stack.material) < stack.amount) return;
		}
		
		for(MaterialStack stack : this.recipeStack) {
			stack.amount -= getQuantaFromType(recipe.input, stack.material);
		}
		
		outer:
		for(MaterialStack out : recipe.output) {
			
			for(MaterialStack stack : this.recipeStack) {
				if(stack.material == out.material) {
					stack.amount += out.amount;
					continue outer;
				}
			}
			
			this.recipeStack.add(out.copy());
		}
	}
	
	protected int getFirstSmeltableSlot() {
		
		for(int i = 1; i < 10; i++) {
			
			ItemStack stack = this.slots[i];
			
			if(stack != null && isItemSmeltable(stack)) {
				return i;
			}
		}
		
		return -1;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		
		if(i == 0) {
			return stack.getItem() == ModItems.crucible_template;
		}
		
		return isItemSmeltable(stack);
	}
	
	public boolean isItemSmeltable(ItemStack stack) {
		
		List<MaterialStack> materials = Mats.getSmeltingMaterialsFromItem(stack);
		
		//if there's no materials in there at all, don't smelt
		if(materials.isEmpty())
			return false;
		
		CrucibleRecipe recipe = getLoadedRecipe();
		
		//needs to be true, will always be true if there's no recipe loaded
		boolean matchesRecipe = recipe == null;
		
		//the amount of material in the entire recipe input
		int recipeContent = recipe != null ? recipe.getInputAmount() : 0;
		//the total amount of the current waste stack, used for simulation
		int recipeAmount = getQuantaFromType(this.recipeStack, null);
		int wasteAmount = getQuantaFromType(this.wasteStack, null);
		
		for(MaterialStack mat : materials) {
			//if no recipe is loaded, everything will land in the waste stack
			int recipeInputRequired = recipe != null ? getQuantaFromType(recipe.input, mat.material) : 0;
			
			//this allows pouring the ouput material back into the crucible
			if(recipe != null && getQuantaFromType(recipe.output, mat.material) > 0) {
				recipeAmount += mat.amount;
				matchesRecipe = true;
				continue;
			}
			
			if(recipeInputRequired == 0) {
				//if this type isn't required by the recipe, add it to the waste stack
				wasteAmount += mat.amount;
			} else {
				
				//the maximum is the recipe's ratio scaled up to the recipe stack's capacity
				int matMaximum = recipeInputRequired * TileEntityCrucible.recipeZCapacity / recipeContent;
				int amountStored = getQuantaFromType(this.recipeStack, mat.material);
				
				matchesRecipe = true;
				
				recipeAmount += mat.amount;
				
				//if the amount of that input would exceed the amount dictated by the recipe, return false
				if(recipe != null && amountStored + mat.amount > matMaximum)
					return false;
			}
		}
		
		//if the amount doesn't exceed the capacity and the recipe matches (or isn't null), return true
		return recipeAmount <= TileEntityCrucible.recipeZCapacity && wasteAmount <= TileEntityCrucible.wasteZCapacity && matchesRecipe;
	}
	
	public void addToStack(List<MaterialStack> stack, MaterialStack matStack) {
		
		for(MaterialStack mat : stack) {
			if(mat.material == matStack.material) {
				mat.amount += matStack.amount;
				return;
			}
		}
		
		stack.add(matStack.copy());
	}
	
	public CrucibleRecipe getLoadedRecipe() {
		
		if(this.slots[0] != null && this.slots[0].getItem() == ModItems.crucible_template) {
			return CrucibleRecipes.indexMapping.get(this.slots[0].getItemDamage());
		}
		
		return null;
	}
	
	/* "Arrays and Lists don't have a common ancestor" my fucking ass */
	public int getQuantaFromType(MaterialStack[] stacks, NTMMaterial mat) {
		for(MaterialStack stack : stacks) {
			if(mat == null || stack.material == mat) {
				return stack.amount;
			}
		}
		return 0;
	}
	
	public int getQuantaFromType(List<MaterialStack> stacks, NTMMaterial mat) {
		int sum = 0;
		for(MaterialStack stack : stacks) {
			if(stack.material == mat) {
				return stack.amount;
			}
			if(mat == null) {
				sum += stack.amount;
			}
		}
		return sum;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int meta) {
		return new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCrucible(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICrucible(player.inventory, this);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(
					this.xCoord - 1,
					this.yCoord,
					this.zCoord - 1,
					this.xCoord + 2,
					this.yCoord + 2,
					this.zCoord + 2
					);
		}
		
		return this.bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public boolean canAcceptPartialPour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
		
		CrucibleRecipe recipe = getLoadedRecipe();
		
		if(recipe == null) {
			return getQuantaFromType(this.wasteStack, null) < TileEntityCrucible.wasteZCapacity;
		}
		
		int recipeContent = recipe.getInputAmount();
		int recipeInputRequired = getQuantaFromType(recipe.input, stack.material);
		int matMaximum = recipeInputRequired * TileEntityCrucible.recipeZCapacity / recipeContent;
		int amountStored = getQuantaFromType(this.recipeStack, stack.material);
		
		return amountStored < matMaximum && getQuantaFromType(this.recipeStack, null) < TileEntityCrucible.recipeZCapacity;
	}

	@Override
	public MaterialStack pour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
		
		CrucibleRecipe recipe = getLoadedRecipe();
		
		if(recipe == null) {
			
			int amount = getQuantaFromType(this.wasteStack, null);
			
			if(amount + stack.amount <= TileEntityCrucible.wasteZCapacity) {
				addToStack(this.wasteStack, stack.copy());
				return null;
			} else {
				int toAdd = TileEntityCrucible.wasteZCapacity - amount;
				addToStack(this.wasteStack, new MaterialStack(stack.material, toAdd));
				return new MaterialStack(stack.material, stack.amount - toAdd);
			}
		}
		
		int recipeContent = recipe.getInputAmount();
		int recipeInputRequired = getQuantaFromType(recipe.input, stack.material);
		int matMaximum = recipeInputRequired * TileEntityCrucible.recipeZCapacity / recipeContent;
		
		if(recipeInputRequired + stack.amount <= matMaximum) {
			addToStack(this.recipeStack, stack.copy());
			return null;
		}
		
		int toAdd = matMaximum - stack.amount;
		toAdd = Math.min(toAdd, TileEntityCrucible.recipeZCapacity - getQuantaFromType(this.recipeStack, null));
		addToStack(this.recipeStack, new MaterialStack(stack.material, toAdd));
		return new MaterialStack(stack.material, stack.amount - toAdd);
	}

	@Override public boolean canAcceptPartialFlow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) { return false; }
	@Override public MaterialStack flow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) { return null; }
}
