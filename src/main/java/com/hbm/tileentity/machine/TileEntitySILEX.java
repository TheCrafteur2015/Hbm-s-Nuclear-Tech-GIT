package com.hbm.tileentity.machine;

import java.util.HashMap;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.container.ContainerSILEX;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUISILEX;
import com.hbm.inventory.recipes.SILEXRecipes;
import com.hbm.inventory.recipes.SILEXRecipes.SILEXRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFELCrystal.EnumWavelengths;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.InventoryUtil;
import com.hbm.util.WeightedRandomObject;

import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntitySILEX extends TileEntityMachineBase implements IFluidAcceptor, IFluidStandardReceiver, IGUIProvider {

	public EnumWavelengths mode = EnumWavelengths.NULL;
	public boolean hasLaser;
	public FluidTank tank;
	public ComparableStack current;
	public int currentFill;
	public static final int maxFill = 16000;
	public int progress;
	public final int processTime = 100;

	// 0: Input
	// 1: Fluid ID
	// 2-3: Fluid Containers
	// 4: Output
	// 5-10: Queue

	public TileEntitySILEX() {
		super(11);
		this.tank = new FluidTank(Fluids.ACID, 16000, 0);
	}

	@Override
	public String getName() {
		return "container.machineSILEX";
	}

	@Override
	public void updateEntity() {

		if(!this.worldObj.isRemote) {

			this.tank.setType(1, 1, this.slots);
			this.tank.loadTank(2, 3, this.slots);
			
			ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - 10).getRotation(ForgeDirection.UP);
			trySubscribe(this.tank.getTankType(), this.worldObj, this.xCoord + dir.offsetX * 2, this.yCoord + 1, this.zCoord + dir.offsetZ * 2, dir);
			trySubscribe(this.tank.getTankType(), this.worldObj, this.xCoord - dir.offsetX * 2, this.yCoord + 1, this.zCoord - dir.offsetZ * 2, dir.getOpposite());

			loadFluid();

			if(!process()) {
				this.progress = 0;
			}

			dequeue();

			if(this.currentFill <= 0) {
				this.current = null;
			}

			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("fill", this.currentFill);
			data.setInteger("progress", this.progress);
			data.setString("mode", this.mode.toString());

			if(this.current != null) {
				data.setInteger("item", Item.getIdFromItem(this.current.item));
				data.setInteger("meta", this.current.meta);
			}

			this.tank.updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
			networkPack(data, 50);

			this.mode = EnumWavelengths.NULL;
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {

		this.currentFill = nbt.getInteger("fill");
		this.progress = nbt.getInteger("progress");
		this.mode = EnumWavelengths.valueOf(nbt.getString("mode"));

		if(this.currentFill > 0) {
			this.current = new ComparableStack(Item.getItemById(nbt.getInteger("item")), 1, nbt.getInteger("meta"));

		} else {
			this.current = null;
		}
	}

	@Override
	public void handleButtonPacket(int value, int meta) {

		this.currentFill = 0;
		this.current = null;
	}

	public int getProgressScaled(int i) {
		return (this.progress * i) / this.processTime;
	}

	public int getFluidScaled(int i) {
		return (this.tank.getFill() * i) / this.tank.getMaxFill();
	}

	public int getFillScaled(int i) {
		return (this.currentFill * i) / TileEntitySILEX.maxFill;
	}

	public static final HashMap<FluidType, ComparableStack> fluidConversion = new HashMap<>();

	static {
		TileEntitySILEX.putFluid(Fluids.UF6);
		TileEntitySILEX.putFluid(Fluids.PUF6);
		TileEntitySILEX.putFluid(Fluids.DEATH);
	}

	private static void putFluid(FluidType fluid) {
		TileEntitySILEX.fluidConversion.put(fluid, new ComparableStack(ModItems.fluid_icon, 1, fluid.getID()));
	}

	int loadDelay;

	public void loadFluid() {

		ComparableStack conv = TileEntitySILEX.fluidConversion.get(this.tank.getTankType());

		if(conv != null) {

			if(this.currentFill == 0) {
				this.current = (ComparableStack) conv.copy();
			}

			if(this.current != null && this.current.equals(conv)) {

				int toFill = Math.min(50, Math.min(TileEntitySILEX.maxFill - this.currentFill, this.tank.getFill()));
				this.currentFill += toFill;
				this.tank.setFill(this.tank.getFill() - toFill);
			}
		} else {
			ComparableStack direct = new ComparableStack(ModItems.fluid_icon, 1, this.tank.getTankType().getID());
			
			if(SILEXRecipes.getOutput(direct.toStack()) != null) {

				if(this.currentFill == 0) {
					this.current = (ComparableStack) direct.copy();
				}

				if(this.current != null && this.current.equals(direct)) {

					int toFill = Math.min(50, Math.min(TileEntitySILEX.maxFill - this.currentFill, this.tank.getFill()));
					this.currentFill += toFill;
					this.tank.setFill(this.tank.getFill() - toFill);
				}
			}
		}

		this.loadDelay++;

		if(this.loadDelay > 20)
			this.loadDelay = 0;

		if(this.loadDelay == 0 && this.slots[0] != null && this.tank.getTankType() == Fluids.ACID && (this.current == null || this.current.equals(new ComparableStack(this.slots[0]).makeSingular()))) {
			SILEXRecipe recipe = SILEXRecipes.getOutput(this.slots[0]);

			if(recipe == null)
				return;

			int load = recipe.fluidProduced;

			if(load <= TileEntitySILEX.maxFill - this.currentFill && load <= this.tank.getFill()) {
				this.currentFill += load;
				this.current = new ComparableStack(this.slots[0]).makeSingular();
				this.tank.setFill(this.tank.getFill() - load);
				decrStackSize(0, 1);
			}
		}
	}

	private boolean process() {

		if(this.current == null || this.currentFill <= 0)
			return false;

		SILEXRecipe recipe = SILEXRecipes.getOutput(this.current.toStack());

		if((recipe == null) || (recipe.laserStrength.ordinal() > this.mode.ordinal()) || (this.currentFill < recipe.fluidConsumed) || (this.slots[4] != null))
			return false;

		int progressSpeed = (int) Math.pow(2, this.mode.ordinal() - recipe.laserStrength.ordinal() + 1) / 2;

		this.progress += progressSpeed;

		if(this.progress >= this.processTime) {

			this.currentFill -= recipe.fluidConsumed;

			ItemStack out = ((WeightedRandomObject) WeightedRandom.getRandomItem(this.worldObj.rand, recipe.outputs)).asStack();
			this.slots[4] = out.copy();
			this.progress = 0;
			markDirty();
		}

		return true;
	}

	private void dequeue() {

		if(this.slots[4] != null) {

			for(int i = 5; i < 11; i++) {

				if(this.slots[i] != null && this.slots[i].stackSize < this.slots[i].getMaxStackSize() && InventoryUtil.doesStackDataMatch(this.slots[4], this.slots[i])) {
					this.slots[i].stackSize++;
					decrStackSize(4, 1);
					return;
				}
			}

			for(int i = 5; i < 11; i++) {

				if(this.slots[i] == null) {
					this.slots[i] = this.slots[4].copy();
					this.slots[4] = null;
					return;
				}
			}
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[] { 0, 5, 6, 7, 8, 9, 10 };
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {

		if(i == 0)
			return SILEXRecipes.getOutput(itemStack) != null;

		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
		return slot >= 5;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.tank.readFromNBT(nbt, "tank");
		this.currentFill = nbt.getInteger("fill");
		this.mode = EnumWavelengths.valueOf(nbt.getString("mode"));

		if(this.currentFill > 0) {
			this.current = new ComparableStack(Item.getItemById(nbt.getInteger("item")), 1, nbt.getInteger("meta"));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.tank.writeToNBT(nbt, "tank");
		nbt.setInteger("fill", this.currentFill);
		nbt.setString("mode", this.mode.toString());

		if(this.current != null) {
			nbt.setInteger("item", Item.getIdFromItem(this.current.item));
			nbt.setInteger("meta", this.current.meta);
		}
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(this.xCoord - 1, this.yCoord, this.zCoord - 1, this.xCoord + 2, this.yCoord + 3, this.zCoord + 2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		this.tank.setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {

		if(type == this.tank.getTankType())
			this.tank.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		this.tank.setTankType(type);
	}

	@Override
	public int getFluidFill(FluidType type) {

		if(type == this.tank.getTankType())
			return this.tank.getFill();

		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {

		if(type == this.tank.getTankType())
			return this.tank.getMaxFill();

		return 0;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {this.tank};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {this.tank};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerSILEX(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUISILEX(player.inventory, this);
	}
}