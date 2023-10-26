package com.hbm.tileentity.machine.oil;

import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.container.ContainerMachineCoker;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineCoker;
import com.hbm.inventory.recipes.CokerRecipes;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.Tuple.Triplet;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import api.hbm.tile.IHeatSource;
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

public class TileEntityMachineCoker extends TileEntityMachineBase implements IFluidStandardTransceiver, IGUIProvider {

	public boolean wasOn;
	public int progress;
	public static int processTime = 20_000;
	
	public int heat;
	public static int maxHeat = 100_000;
	public static double diffusion = 0.25D;
	
	public FluidTank[] tanks;

	public TileEntityMachineCoker() {
		super(2);
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.HEAVYOIL, 16_000);
		this.tanks[1] = new FluidTank(Fluids.OIL_COKER, 8_000);
	}

	@Override
	public String getName() {
		return "container.machineCoker";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {

			tryPullHeat();
			this.tanks[0].setType(0, this.slots);

			if(this.worldObj.getTotalWorldTime() % 20 == 0) {
				for(DirPos pos : getConPos()) {
					trySubscribe(this.tanks[0].getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				}
			}
			
			this.wasOn = false;
			
			if(canProcess()) {
				int burn = this.heat / 100;
						
				if(burn > 0) {
					this.wasOn = true;
					this.progress += burn;
					this.heat -= burn;
					
					if(this.progress >= TileEntityMachineCoker.processTime) {
						markChanged();
						this.progress -= TileEntityMachineCoker.processTime;
						
						Triplet<Integer, ItemStack, FluidStack> recipe = CokerRecipes.getOutput(this.tanks[0].getTankType());
						int fillReq = recipe.getX();
						ItemStack output = recipe.getY();
						FluidStack byproduct = recipe.getZ();
						
						if(output != null) {
							if(this.slots[1] == null) {
								this.slots[1] = output.copy();
							} else {
								this.slots[1].stackSize += output.stackSize;
							}
						}
						
						if(byproduct != null) {
							this.tanks[1].setFill(this.tanks[1].getFill() + byproduct.fill);
						}
						
						this.tanks[0].setFill(this.tanks[0].getFill() - fillReq);
					}
				}

				if(this.worldObj.getTotalWorldTime() % 20 == 0) PollutionHandler.incrementPollution(this.worldObj, this.xCoord, this.yCoord, this.zCoord, PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND * 20);
			}
			
			for(DirPos pos : getConPos()) {
				if(this.tanks[1].getFill() > 0) this.sendFluid(this.tanks[1], this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("wasOn", this.wasOn);
			data.setInteger("heat", this.heat);
			data.setInteger("progress", this.progress);
			this.tanks[0].writeToNBT(data, "t0");
			this.tanks[1].writeToNBT(data, "t1");
			networkPack(data, 25);
		} else {
			
			if(this.wasOn) {

				if(this.worldObj.getTotalWorldTime() % 2 == 0) {
					NBTTagCompound fx = new NBTTagCompound();
					fx.setString("type", "tower");
					fx.setFloat("lift", 10F);
					fx.setFloat("base", 0.75F);
					fx.setFloat("max", 3F);
					fx.setInteger("life", 200 + this.worldObj.rand.nextInt(50));
					fx.setInteger("color",0x404040);
					fx.setDouble("posX", this.xCoord + 0.5);
					fx.setDouble("posY", this.yCoord + 22);
					fx.setDouble("posZ", this.zCoord + 0.5);
					MainRegistry.proxy.effectNT(fx);
				}
			}
		}
	}
	
	public DirPos[] getConPos() {
		
		return new DirPos[] {
				new DirPos(this.xCoord + 2, this.yCoord, this.zCoord + 1, Library.POS_X),
				new DirPos(this.xCoord + 2, this.yCoord, this.zCoord - 1, Library.POS_X),
				new DirPos(this.xCoord - 2, this.yCoord, this.zCoord + 1, Library.NEG_X),
				new DirPos(this.xCoord - 2, this.yCoord, this.zCoord - 1, Library.NEG_X),
				new DirPos(this.xCoord + 1, this.yCoord, this.zCoord + 2, Library.POS_Z),
				new DirPos(this.xCoord - 1, this.yCoord, this.zCoord + 2, Library.POS_Z),
				new DirPos(this.xCoord + 1, this.yCoord, this.zCoord - 2, Library.NEG_Z),
				new DirPos(this.xCoord - 1, this.yCoord, this.zCoord - 2, Library.NEG_Z)
		};
	}
	
	public boolean canProcess() {
		Triplet<Integer, ItemStack, FluidStack> recipe = CokerRecipes.getOutput(this.tanks[0].getTankType());
		
		if(recipe == null) return false;
		
		int fillReq = recipe.getX();
		ItemStack output = recipe.getY();
		FluidStack byproduct = recipe.getZ();
		
		if(byproduct != null) this.tanks[1].setTankType(byproduct.type);
		
		if((this.tanks[0].getFill() < fillReq) || (byproduct != null && byproduct.fill + this.tanks[1].getFill() > this.tanks[1].getMaxFill())) return false;
		
		if(output != null && this.slots[1] != null) {
			if(output.getItem() != this.slots[1].getItem()) return false;
			if(output.getItemDamage() != this.slots[1].getItemDamage()) return false;
			if(output.stackSize + this.slots[1].stackSize > output.getMaxStackSize()) return false;
		}
		
		return true;
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.wasOn = nbt.getBoolean("wasOn");
		this.heat = nbt.getInteger("heat");
		this.progress = nbt.getInteger("progress");
		this.tanks[0].readFromNBT(nbt, "t0");
		this.tanks[1].readFromNBT(nbt, "t1");
	}
	
	protected void tryPullHeat() {
		
		if(this.heat >= TileEntityMachineCoker.maxHeat) return;
		
		TileEntity con = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord);
		
		if(con instanceof IHeatSource) {
			IHeatSource source = (IHeatSource) con;
			int diff = source.getHeatStored() - this.heat;
			
			if(diff == 0) {
				return;
			}
			
			if(diff > 0) {
				diff = (int) Math.ceil(diff * TileEntityMachineCoker.diffusion);
				source.useUpHeat(diff);
				this.heat += diff;
				if(this.heat > TileEntityMachineCoker.maxHeat)
					this.heat = TileEntityMachineCoker.maxHeat;
				return;
			}
		}
		
		this.heat = Math.max(this.heat - Math.max(this.heat / 1000, 1), 0);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 1 };
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.tanks[0].readFromNBT(nbt, "t0");
		this.tanks[1].readFromNBT(nbt, "t1");
		this.progress = nbt.getInteger("prog");
		this.heat = nbt.getInteger("heat");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.tanks[0].writeToNBT(nbt, "t0");
		this.tanks[1].writeToNBT(nbt, "t1");
		nbt.setInteger("prog", this.progress);
		nbt.setInteger("heat", this.heat);
	}

	@Override
	public FluidTank[] getAllTanks() {
		return this.tanks;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { this.tanks[1] };
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { this.tanks[0] };
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
					this.yCoord + 23,
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
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineCoker(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineCoker(player.inventory, this);
	}
}
