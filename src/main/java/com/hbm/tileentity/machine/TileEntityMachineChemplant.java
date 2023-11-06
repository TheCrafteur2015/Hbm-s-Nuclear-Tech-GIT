package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerMachineChemplant;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineChemplant;
import com.hbm.inventory.recipes.ChemplantRecipes;
import com.hbm.inventory.recipes.ChemplantRecipes.ChemRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.InventoryUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@SuppressWarnings("deprecation")
public class TileEntityMachineChemplant extends TileEntityMachineBase implements IEnergyUser, IFluidSource, IFluidAcceptor, IFluidStandardTransceiver, IGUIProvider {

	public long power;
	public static final long maxPower = 100000;
	public int progress;
	public int maxProgress = 100;
	public boolean isProgressing;
	
	private AudioWrapper audio;
	
	public FluidTank[] tanks;
	
	//upgraded stats
	int consumption = 100;
	int speed = 100;

	public TileEntityMachineChemplant() {
		super(21);
		/*
		 * 0 Battery
		 * 1-3 Upgrades
		 * 4 Schematic
		 * 5-8 Output
		 * 9-10 FOut In
		 * 11-12 FOut Out
		 * 13-16 Input
		 * 17-18 FIn In
		 * 19-20 FIn Out
		 */
		
		this.tanks = new FluidTank[4];
		for(int i = 0; i < 4; i++) {
			this.tanks[i] = new FluidTank(Fluids.NONE, 24_000, i);
		}
	}

	@Override
	public String getName() {
		return "container.chemplant";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			this.speed = 100;
			this.consumption = 100;
			
			this.isProgressing = false;
			this.power = Library.chargeTEFromItems(this.slots, 0, this.power, TileEntityMachineChemplant.maxPower);

			if(!this.tanks[0].loadTank(17, 19, this.slots) && (this.slots[17] == null || this.slots[17].getItem() != ModItems.fluid_barrel_infinite)) this.tanks[0].unloadTank(17, 19, this.slots);
			if(!this.tanks[1].loadTank(18, 20, this.slots) && (this.slots[18] == null || this.slots[18].getItem() != ModItems.fluid_barrel_infinite)) this.tanks[1].unloadTank(18, 20, this.slots);
			
			this.tanks[2].unloadTank(9, 11, this.slots);
			this.tanks[3].unloadTank(10, 12, this.slots);
			
			loadItems();
			unloadItems();
			
			if(this.worldObj.getTotalWorldTime() % 10 == 0) {
				fillFluidInit(this.tanks[2].getTankType());
				fillFluidInit(this.tanks[3].getTankType());
			}
			if(this.worldObj.getTotalWorldTime() % 20 == 0) {
				updateConnections();
			}
			
			for(DirPos pos : getConPos()) {
				if(this.tanks[2].getFill() > 0) this.sendFluid(this.tanks[2], this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				if(this.tanks[3].getFill() > 0) this.sendFluid(this.tanks[3], this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
			
			UpgradeManager.eval(this.slots, 1, 3);

			int speedLevel = Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3);
			int powerLevel = Math.min(UpgradeManager.getLevel(UpgradeType.POWER), 3);
			int overLevel = UpgradeManager.getLevel(UpgradeType.OVERDRIVE);
			
			this.speed -= speedLevel * 25;
			this.consumption += speedLevel * 300;
			this.speed += powerLevel * 5;
			this.consumption -= powerLevel * 30;
			this.speed /= (overLevel + 1);
			this.consumption *= (overLevel + 1);
			
			if(this.speed <= 0) {
				this.speed = 1;
			}
			
			if(!canProcess()) {
				this.progress = 0;
			} else {
				this.isProgressing = true;
				process();
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setInteger("progress", this.progress);
			data.setInteger("maxProgress", this.maxProgress);
			data.setBoolean("isProgressing", this.isProgressing);
			
			for(int i = 0; i < this.tanks.length; i++) {
				this.tanks[i].writeToNBT(data, "t" + i);
			}
			
			networkPack(data, 150);
		} else {
			
			if(this.isProgressing && this.worldObj.getTotalWorldTime() % 3 == 0) {
				
				ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset).getOpposite();
				ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
				double x = this.xCoord + 0.5 + dir.offsetX * 1.125 + rot.offsetX * 0.125;
				double y = this.yCoord + 3;
				double z = this.zCoord + 0.5 + dir.offsetZ * 1.125 + rot.offsetZ * 0.125;
				this.worldObj.spawnParticle("cloud", x, y, z, 0.0, 0.1, 0.0);
			}
			
			float volume = 1;//this.getVolume(2);
			
			if(this.isProgressing && volume > 0) {
				
				if(this.audio == null) {
					this.audio = createAudioLoop();
					this.audio.updateVolume(volume);
					this.audio.startSound();
				} else if(!this.audio.isPlaying()) {
					this.audio = rebootAudio(this.audio);
					this.audio.updateVolume(volume);
				}
				
			} else {
				
				if(this.audio != null) {
					this.audio.stopSound();
					this.audio = null;
				}
			}
		}
	}
	
	@Override
	public AudioWrapper createAudioLoop() {
		return MainRegistry.proxy.getLoopedSound("hbm:block.chemplantOperate", this.xCoord, this.yCoord, this.zCoord, 1.0F, 10F, 1.0F);
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.progress = nbt.getInteger("progress");
		this.maxProgress = nbt.getInteger("maxProgress");
		this.isProgressing = nbt.getBoolean("isProgressing");

		for(int i = 0; i < this.tanks.length; i++) {
			this.tanks[i].readFromNBT(nbt, "t" + i);
		}
	}

	@Override
	public void onChunkUnload() {

		if(this.audio != null) {
			this.audio.stopSound();
			this.audio = null;
		}
	}

	@Override
	public void invalidate() {

		super.invalidate();

		if(this.audio != null) {
			this.audio.stopSound();
			this.audio = null;
		}
	}
	
	private void updateConnections() {
		
		for(DirPos pos : getConPos()) {
			this.trySubscribe(this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(this.tanks[0].getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(this.tanks[1].getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	public DirPos[] getConPos() {

		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset).getOpposite();
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);
		
		return new DirPos[] {
				new DirPos(this.xCoord + rot.offsetX * 3,				this.yCoord,	this.zCoord + rot.offsetZ * 3,				rot),
				new DirPos(this.xCoord - rot.offsetX * 2,				this.yCoord,	this.zCoord - rot.offsetZ * 2,				rot.getOpposite()),
				new DirPos(this.xCoord + rot.offsetX * 3 + dir.offsetX,	this.yCoord,	this.zCoord + rot.offsetZ * 3 + dir.offsetZ, rot),
				new DirPos(this.xCoord - rot.offsetX * 2 + dir.offsetX,	this.yCoord,	this.zCoord - rot.offsetZ * 2 + dir.offsetZ, rot.getOpposite())
		};
	}
	
	private boolean canProcess() {
		
		if(this.slots[4] == null || this.slots[4].getItem() != ModItems.chemistry_template)
			return false;
		
		ChemRecipe recipe = ChemplantRecipes.indexMapping.get(this.slots[4].getItemDamage());
		
		if(recipe == null)
			return false;
		
		setupTanks(recipe);
		
		if((this.power < this.consumption) || !hasRequiredFluids(recipe) || !hasSpaceForFluids(recipe) || !hasRequiredItems(recipe)) return false;
		if(!hasSpaceForItems(recipe)) return false;
		
		return true;
	}
	
	private void setupTanks(ChemRecipe recipe) {
		if(recipe.inputFluids[0] != null) this.tanks[0].withPressure(recipe.inputFluids[0].pressure).setTankType(recipe.inputFluids[0].type);	else this.tanks[0].setTankType(Fluids.NONE);
		if(recipe.inputFluids[1] != null) this.tanks[1].withPressure(recipe.inputFluids[1].pressure).setTankType(recipe.inputFluids[1].type);	else this.tanks[1].setTankType(Fluids.NONE);
		if(recipe.outputFluids[0] != null) this.tanks[2].withPressure(recipe.outputFluids[0].pressure).setTankType(recipe.outputFluids[0].type);	else this.tanks[2].setTankType(Fluids.NONE);
		if(recipe.outputFluids[1] != null) this.tanks[3].withPressure(recipe.outputFluids[1].pressure).setTankType(recipe.outputFluids[1].type);	else this.tanks[3].setTankType(Fluids.NONE);
	}
	
	private boolean hasRequiredFluids(ChemRecipe recipe) {
		if((recipe.inputFluids[0] != null && this.tanks[0].getFill() < recipe.inputFluids[0].fill) || (recipe.inputFluids[1] != null && this.tanks[1].getFill() < recipe.inputFluids[1].fill)) return false;
		return true;
	}
	
	private boolean hasSpaceForFluids(ChemRecipe recipe) {
		if((recipe.outputFluids[0] != null && this.tanks[2].getFill() + recipe.outputFluids[0].fill > this.tanks[2].getMaxFill()) || (recipe.outputFluids[1] != null && this.tanks[3].getFill() + recipe.outputFluids[1].fill > this.tanks[3].getMaxFill())) return false;
		return true;
	}
	
	private boolean hasRequiredItems(ChemRecipe recipe) {
		return InventoryUtil.doesArrayHaveIngredients(this.slots, 13, 16, recipe.inputs);
	}
	
	private boolean hasSpaceForItems(ChemRecipe recipe) {
		return InventoryUtil.doesArrayHaveSpace(this.slots, 5, 8, recipe.outputs);
	}
	
	private void process() {
		
		this.power -= this.consumption;
		this.progress++;
		
		if(this.slots[0] != null && this.slots[0].getItem() == ModItems.meteorite_sword_machined)
			this.slots[0] = new ItemStack(ModItems.meteorite_sword_treated); //fisfndmoivndlmgindgifgjfdnblfm
		
		ChemRecipe recipe = ChemplantRecipes.indexMapping.get(this.slots[4].getItemDamage());
		
		this.maxProgress = recipe.getDuration() * this.speed / 100;
		
		if(this.maxProgress <= 0) this.maxProgress = 1;
		
		if(this.progress >= this.maxProgress) {
			consumeFluids(recipe);
			produceFluids(recipe);
			consumeItems(recipe);
			produceItems(recipe);
			this.progress = 0;
			markDirty();
		}
	}
	
	private void consumeFluids(ChemRecipe recipe) {
		if(recipe.inputFluids[0] != null) this.tanks[0].setFill(this.tanks[0].getFill() - recipe.inputFluids[0].fill);
		if(recipe.inputFluids[1] != null) this.tanks[1].setFill(this.tanks[1].getFill() - recipe.inputFluids[1].fill);
	}
	
	private void produceFluids(ChemRecipe recipe) {
		if(recipe.outputFluids[0] != null) this.tanks[2].setFill(this.tanks[2].getFill() + recipe.outputFluids[0].fill);
		if(recipe.outputFluids[1] != null) this.tanks[3].setFill(this.tanks[3].getFill() + recipe.outputFluids[1].fill);
	}
	
	private void consumeItems(ChemRecipe recipe) {
		
		for(AStack in : recipe.inputs) {
			if(in != null)
				InventoryUtil.tryConsumeAStack(this.slots, 13, 16, in);
		}
	}
	
	private void produceItems(ChemRecipe recipe) {
		
		for(ItemStack out : recipe.outputs) {
			if(out != null)
				InventoryUtil.tryAddItemToInventory(this.slots, 5, 8, out.copy());
		}
	}
	
	//TODO: move this into a util class
	private void loadItems() {
		
		if(this.slots[4] == null || this.slots[4].getItem() != ModItems.chemistry_template)
			return;
		
		ChemRecipe recipe = ChemplantRecipes.indexMapping.get(this.slots[4].getItemDamage());
		
		if(recipe != null) {
			
			ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset).getOpposite();

			int x = this.xCoord - dir.offsetX * 2;
			int z = this.zCoord - dir.offsetZ * 2;
			
			TileEntity te = this.worldObj.getTileEntity(x, this.yCoord, z);
			
			if(te instanceof IInventory) {
				
				IInventory inv = (IInventory) te;
				ISidedInventory sided = inv instanceof ISidedInventory ? (ISidedInventory) inv : null;
				int[] access = sided != null ? sided.getAccessibleSlotsFromSide(dir.ordinal()) : null;
				
				for(AStack ingredient : recipe.inputs) {
					
					outer:
					while(!InventoryUtil.doesArrayHaveIngredients(this.slots, 13, 16, ingredient)) {
						
						boolean found = false;
						
						for(int i = 0; i < (access != null ? access.length : inv.getSizeInventory()); i++) {

							int slot = access != null ? access[i] : i;
							ItemStack stack = inv.getStackInSlot(slot);
							
							if(ingredient.matchesRecipe(stack, true) && (sided == null || sided.canExtractItem(slot, stack, 0))) {
								
								for(int j = 13; j <= 16; j++) {
									
									if(this.slots[j] != null && this.slots[j].stackSize < this.slots[j].getMaxStackSize() & InventoryUtil.doesStackDataMatch(this.slots[j], stack)) {
										inv.decrStackSize(slot, 1);
										this.slots[j].stackSize++;
										continue outer;
									}
								}
								
								for(int j = 13; j <= 16; j++) {
									
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
	
	private void unloadItems() {
		
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset).getOpposite();
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);

		int x = this.xCoord + dir.offsetX * 3 + rot.offsetX;
		int z = this.zCoord + dir.offsetZ * 3 + rot.offsetZ;
		
		TileEntity te = this.worldObj.getTileEntity(x, this.yCoord, z);
		
		if(te instanceof IInventory) {
			
			IInventory inv = (IInventory) te;
			ISidedInventory sided = inv instanceof ISidedInventory ? (ISidedInventory) inv : null;
			int[] access = sided != null ? sided.getAccessibleSlotsFromSide(dir.ordinal()) : null;
			
			for(int i = 5; i <= 8; i++) {
				
				ItemStack out = this.slots[i];
				
				if(out != null) {
					
					for(int j = 0; j < (access != null ? access.length : inv.getSizeInventory()); j++) {

						int slot = access != null ? access[j] : j;
						
						if(!inv.isItemValidForSlot(slot, out))
							continue;
							
						ItemStack target = inv.getStackInSlot(slot);
						
						if(InventoryUtil.doesStackDataMatch(out, target) && target.stackSize < target.getMaxStackSize()) {
							decrStackSize(i, 1);
							target.stackSize++;
							return;
						}
					}
					
					for(int j = 0; j < (access != null ? access.length : inv.getSizeInventory()); j++) {

						int slot = access != null ? access[j] : j;
						
						if(!inv.isItemValidForSlot(slot, out))
							continue;
						
						if(inv.getStackInSlot(slot) == null && (sided != null ? sided.canInsertItem(slot, out, dir.ordinal()) : inv.isItemValidForSlot(slot, out))) {
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
	public long getPower() {
		return this.power;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getMaxPower() {
		return TileEntityMachineChemplant.maxPower;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if(index >= 0 && index < this.tanks.length) this.tanks[index].setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		
		for(FluidTank tank : this.tanks) {
			if(tank.getTankType() == type) {
				tank.setFill(fill);
				return;
			}
		}
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if(index >= 0 && index < this.tanks.length) this.tanks[index].setTankType(type);
	}

	@Override
	public int getFluidFill(FluidType type) {
		
		for(FluidTank tank : this.tanks) {
			if(tank.getTankType() == type) {
				return tank.getFill();
			}
		}
		
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		
		for(int i = 0; i < 2; i++) {
			if(this.tanks[i].getTankType() == type) {
				return this.tanks[i].getMaxFill();
			}
		}
		
		return 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		
		/*
		 *  ####
		 * X####X
		 * X##O#X
		 *  ####
		 */
		
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset).getOpposite();
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);

		fillFluid(this.xCoord + rot.offsetX * 3,					this.yCoord,	this.zCoord + rot.offsetZ * 3,				getTact(), type);
		fillFluid(this.xCoord - rot.offsetX * 2,					this.yCoord,	this.zCoord - rot.offsetZ * 2,				getTact(), type);
		fillFluid(this.xCoord + rot.offsetX * 3 + dir.offsetX,	this.yCoord,	this.zCoord + rot.offsetZ * 3 + dir.offsetZ,	getTact(), type);
		fillFluid(this.xCoord - rot.offsetX * 2 + dir.offsetX,	this.yCoord,	this.zCoord - rot.offsetZ * 2 + dir.offsetZ,	getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, this.worldObj, type);
	}

	@Override
	public boolean getTact() {
		return this.worldObj.getTotalWorldTime() % 20 < 10;
	}
	
	
	List<IFluidAcceptor>[] lists = new List[] {
		new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
	};

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		
		for(int i = 0; i < this.tanks.length; i++) {
			if(this.tanks[i].getTankType() == type) {
				return this.lists[i];
			}
		}
		
		return new ArrayList<>();
	}

	@Override
	public void clearFluidList(FluidType type) {
		
		for(int i = 0; i < this.tanks.length; i++) {
			if(this.tanks[i].getTankType() == type) {
				this.lists[i].clear();
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.power = nbt.getLong("power");
		this.progress = nbt.getInteger("progress");
		
		for(int i = 0; i < this.tanks.length; i++) {
			this.tanks[i].readFromNBT(nbt, "t" + i);
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", this.power);
		nbt.setInteger("progress", this.progress);
		
		for(int i = 0; i < this.tanks.length; i++) {
			this.tanks[i].writeToNBT(nbt, "t" + i);
		}
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(
					this.xCoord - 2,
					this.yCoord,
					this.zCoord - 2,
					this.xCoord + 3,
					this.yCoord + 4,
					this.zCoord + 3
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
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {this.tanks[2], this.tanks[3]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {this.tanks[0], this.tanks[1]};
	}

	@Override
	public FluidTank[] getAllTanks() {
		return this.tanks;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineChemplant(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineChemplant(player.inventory, this);
	}
}
