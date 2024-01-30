package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.inventory.container.ContainerCrystallizer;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUICrystallizer;
import com.hbm.inventory.recipes.CrystallizerRecipes;
import com.hbm.inventory.recipes.CrystallizerRecipes.CrystallizerRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineCrystallizer extends TileEntityMachineBase implements IEnergyUser, IFluidStandardReceiver, IGUIProvider {
	
	public long power;
	public static final long maxPower = 1000000;
	public static final int demand = 1000;
	public short progress;
	public short duration = 600;
	
	public float angle;
	public float prevAngle;
	
	public FluidTank tank;

	public TileEntityMachineCrystallizer() {
		super(8);
		this.tank = new FluidTank(Fluids.ACID, 8000);
	}

	@Override
	public String getName() {
		return "container.crystallizer";
	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			updateConnections();
			
			this.power = Library.chargeTEFromItems(this.slots, 1, this.power, TileEntityMachineCrystallizer.maxPower);
			this.tank.setType(7, this.slots);
			this.tank.loadTank(3, 4, this.slots);
			
			for(int i = 0; i < getCycleCount(); i++) {
				
				if(canProcess()) {
					
					this.progress++;
					this.power -= getPowerRequired();
					
					if(this.progress > getDuration()) {
						this.progress = 0;
						processItem();
						
						markDirty();
					}
					
				} else {
					this.progress = 0;
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setShort("progress", this.progress);
			data.setShort("duration", getDuration());
			data.setLong("power", this.power);
			this.tank.writeToNBT(data, "t");
			networkPack(data, 25);
		} else {
			
			this.prevAngle = this.angle;
			
			if(this.progress > 0) {
				this.angle += 5F * getCycleCount();
				
				if(this.angle >= 360) {
					this.angle -= 360;
					this.prevAngle -= 360;
				}
			}
		}
	}
	
	private void updateConnections() {
		
		for(DirPos pos : getConPos()) {
			this.trySubscribe(this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(this.tank.getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	protected DirPos[] getConPos() {

		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);

		if(dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH) {
			
			return new DirPos[] {
				new DirPos(this.xCoord + 2, this.yCoord + 5, this.zCoord, Library.POS_X),
				new DirPos(this.xCoord - 2, this.yCoord + 5, this.zCoord, Library.NEG_X)
			};
		}

		if(dir == ForgeDirection.EAST || dir == ForgeDirection.WEST) {
			
			return new DirPos[] {
				new DirPos(this.xCoord, this.yCoord + 5, this.zCoord + 2, Library.POS_Z),
				new DirPos(this.xCoord, this.yCoord + 5, this.zCoord - 2, Library.NEG_Z)
			};
		}
		
		return new DirPos[0];
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {
		
		this.power = data.getLong("power");
		this.progress = data.getShort("progress");
		this.duration = data.getShort("duration");
		this.tank.readFromNBT(data, "t");
	}
	
	private void processItem() {

		CrystallizerRecipe result = CrystallizerRecipes.getOutput(this.slots[0], this.tank.getTankType());
		
		if(result == null) //never happens but you can't be sure enough
			return;
		
		ItemStack stack = result.output.copy();
		
		if(this.slots[2] == null)
			this.slots[2] = stack;
		else if(this.slots[2].stackSize + stack.stackSize <= this.slots[2].getMaxStackSize())
			this.slots[2].stackSize += stack.stackSize;
		
		this.tank.setFill(this.tank.getFill() - result.acidAmount);
		
		float freeChance = getFreeChance();
		
		if(freeChance == 0 || freeChance < this.worldObj.rand.nextFloat())
			decrStackSize(0, result.itemAmount);
	}
	
	private boolean canProcess() {
		
		//Is there no input?
		if((this.slots[0] == null) || (this.power < getPowerRequired()))
			return false;
		
		CrystallizerRecipe result = CrystallizerRecipes.getOutput(this.slots[0], this.tank.getTankType());
		
		//Or output?
		if(result == null)
			return false;
		
		//Not enough of the input item?
		if(this.slots[0].stackSize < result.itemAmount)
			return false;
		
		if(this.tank.getFill() < result.acidAmount) return false;
		
		ItemStack stack = result.output.copy();
		
		//Does the output not match?
		if(this.slots[2] != null && (this.slots[2].getItem() != stack.getItem() || this.slots[2].getItemDamage() != stack.getItemDamage()))
			return false;
		
		//Or is the output slot already full?
		if(this.slots[2] != null && this.slots[2].stackSize + stack.stackSize > this.slots[2].getMaxStackSize())
			return false;
		
		return true;
	}
	
	public int getRequiredAcid(int base) {
		
		for(int i = 5; i <= 6; i++) {

			if(this.slots[i] != null && this.slots[i].getItem() == ModItems.upgrade_effect_1)
				base *= 3;
			if(this.slots[i] != null && this.slots[i].getItem() == ModItems.upgrade_effect_2)
				base *= 4;
			if(this.slots[i] != null && this.slots[i].getItem() == ModItems.upgrade_effect_3)
				base *= 5;
		}
		
		return base;
	}
	
	public float getFreeChance() {
		
		float chance = 0.0F;
		
		for(int i = 5; i <= 6; i++) {

			if(this.slots[i] != null && this.slots[i].getItem() == ModItems.upgrade_effect_1)
				chance += 0.05F;
			if(this.slots[i] != null && this.slots[i].getItem() == ModItems.upgrade_effect_2)
				chance += 0.1F;
			if(this.slots[i] != null && this.slots[i].getItem() == ModItems.upgrade_effect_3)
				chance += 0.15F;
		}
		
		return Math.min(chance, 0.15F);
	}
	
	public short getDuration() {
		
		float durationMod = 1;
		CrystallizerRecipe result = CrystallizerRecipes.getOutput(this.slots[0], this.tank.getTankType());
		
		int base = result != null ? result.duration : 600;
		
		for(int i = 5; i <= 6; i++) {

			if(this.slots[i] != null && this.slots[i].getItem() == ModItems.upgrade_speed_1)
				durationMod -= 0.25F;
			if(this.slots[i] != null && this.slots[i].getItem() == ModItems.upgrade_speed_2)
				durationMod -= 0.50F;
			if(this.slots[i] != null && this.slots[i].getItem() == ModItems.upgrade_speed_3)
				durationMod -= 0.75F;
		}
		
		return (short) Math.ceil((base * Math.max(durationMod, 0.25F)));
	}
	
	public int getPowerRequired() {
		
		int consumption = 0;
		
		for(int i = 5; i <= 6; i++) {

			if(this.slots[i] != null && this.slots[i].getItem() == ModItems.upgrade_speed_1)
				consumption += 1000;
			if(this.slots[i] != null && this.slots[i].getItem() == ModItems.upgrade_speed_2)
				consumption += 2000;
			if(this.slots[i] != null && this.slots[i].getItem() == ModItems.upgrade_speed_3)
				consumption += 3000;
		}
		
		return (int) (TileEntityMachineCrystallizer.demand + Math.min(consumption, 3000));
	}
	
	public float getCycleCount() {
		
		int cycles = 1;
		
		for(int i = 5; i <= 6; i++) {

			if(this.slots[i] != null && this.slots[i].getItem() == ModItems.upgrade_overdrive_1)
				cycles += 2;
			if(this.slots[i] != null && this.slots[i].getItem() == ModItems.upgrade_overdrive_2)
				cycles += 4;
			if(this.slots[i] != null && this.slots[i].getItem() == ModItems.upgrade_overdrive_3)
				cycles += 6;
		}
		
		return Math.min(cycles, 4);
	}
	
	public long getPowerScaled(int i) {
		return (this.power * i) / TileEntityMachineCrystallizer.maxPower;
	}
	
	public int getProgressScaled(int i) {
		return (this.progress * i) / this.duration;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return TileEntityMachineCrystallizer.maxPower;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.power = nbt.getLong("power");
		this.tank.readFromNBT(nbt, "tank");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", this.power);
		this.tank.writeToNBT(nbt, "tank");
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		
		CrystallizerRecipe recipe = CrystallizerRecipes.getOutput(itemStack, this.tank.getTankType());
		if((i == 0 && recipe != null) || (i == 1 && itemStack.getItem() instanceof IBatteryItem))
			return true;
		
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 2;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		
		return side == 0 ? new int[] { 2 } : new int[] { 0, 2 };
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		super.setInventorySlotContents(i, stack);
		
		if(stack != null && i >= 5 && i <= 6 && stack.getItem() instanceof ItemMachineUpgrade) {
			this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, "hbm:item.upgradePlug", 1.0F, 1.0F);
		}
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {this.tank};
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { this.tank };
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCrystallizer(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICrystallizer(player.inventory, this);
	}
}
