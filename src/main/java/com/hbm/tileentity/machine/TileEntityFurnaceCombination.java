package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.container.ContainerFurnaceCombo;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIFurnaceCombo;
import com.hbm.inventory.recipes.CombinationRecipes;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachinePolluting;
import com.hbm.util.Tuple.Pair;

import api.hbm.fluid.IFluidStandardSender;
import api.hbm.tile.IHeatSource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFurnaceCombination extends TileEntityMachinePolluting implements IFluidStandardSender, IGUIProvider {

	public boolean wasOn;
	public int progress;
	public static int processTime = 20_000;
	
	public int heat;
	public static int maxHeat = 100_000;
	public static double diffusion = 0.25D;
	
	public FluidTank tank;

	public TileEntityFurnaceCombination() {
		super(4, 50);
		this.tank = new FluidTank(Fluids.NONE, 24_000);
	}

	@Override
	public String getName() {
		return "container.furnaceCombination";
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			tryPullHeat();
			
			if(this.worldObj.getTotalWorldTime() % 20 == 0) {
				for(int i = 2; i < 6; i++) {
					ForgeDirection dir = ForgeDirection.getOrientation(i);
					ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
					
					for(int y = this.yCoord; y <= this.yCoord + 1; y++) {
						for(int j = -1; j <= 1; j++) {
							if(this.tank.getFill() > 0) this.sendFluid(this.tank, this.worldObj, this.xCoord + dir.offsetX * 2 + rot.offsetX * j, y, this.zCoord + dir.offsetZ * 2 + rot.offsetZ * j, dir);
							sendSmoke(this.xCoord + dir.offsetX * 2 + rot.offsetX * j, y, this.zCoord + dir.offsetZ * 2 + rot.offsetZ * j, dir);
						}
					}
				}
	
				for(int x = this.xCoord - 1; x <= this.xCoord + 1; x++) {
					for(int z = this.zCoord - 1; z <= this.zCoord + 1; z++) {
						if(this.tank.getFill() > 0) this.sendFluid(this.tank, this.worldObj, x, this.yCoord + 2, z, ForgeDirection.UP);
						sendSmoke(x, this.yCoord + 2, z, ForgeDirection.UP);
					}
				}
			}
			
			this.wasOn = false;
			
			this.tank.unloadTank(2, 3, this.slots);
			
			if(canSmelt()) {
				int burn = this.heat / 100;
				
				if(burn > 0) {
					this.wasOn = true;
					this.progress += burn;
					this.heat -= burn;
					
					if(this.progress >= TileEntityFurnaceCombination.processTime) {
						markChanged();
						this.progress -= TileEntityFurnaceCombination.processTime;
						
						Pair<ItemStack, FluidStack> pair = CombinationRecipes.getOutput(this.slots[0]);
						ItemStack out = pair.getKey();
						FluidStack fluid = pair.getValue();
						
						if(out != null)  {
							if(this.slots[1] == null) {
								this.slots[1] = out.copy();
							} else {
								this.slots[1].stackSize += out.stackSize;
							}
						}
						
						if(fluid != null) {
							if(this.tank.getTankType() != fluid.type) {
								this.tank.setTankType(fluid.type);
							}
							
							this.tank.setFill(this.tank.getFill() + fluid.fill);
						}
						
						decrStackSize(0, 1);
					}
					
					List<Entity> entities = this.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(this.xCoord - 0.5, this.yCoord + 2, this.zCoord - 0.5, this.xCoord + 1.5, this.yCoord + 4, this.zCoord + 1.5));
					
					for(Entity e : entities) e.setFire(5);
					
					if(this.worldObj.getTotalWorldTime() % 10 == 0) this.worldObj.playSoundEffect(this.xCoord, this.yCoord + 1, this.zCoord, "hbm:weapon.flamethrowerShoot", 0.25F, 0.5F);
					if(this.worldObj.getTotalWorldTime() % 20 == 0) pollute(PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND * 3);
				}
			} else {
				this.progress = 0;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("wasOn", this.wasOn);
			data.setInteger("heat", this.heat);
			data.setInteger("progress", this.progress);
			this.tank.writeToNBT(data, "t");
			networkPack(data, 50);
		} else {
			
			if(this.wasOn && this.worldObj.rand.nextInt(15) == 0) {
				this.worldObj.spawnParticle("lava", this.xCoord + 0.5 + this.worldObj.rand.nextGaussian() * 0.5, this.yCoord + 2, this.zCoord + 0.5 + this.worldObj.rand.nextGaussian() * 0.5, 0, 0, 0);
			}
		}
	}
	
	public boolean canSmelt() {
		if(this.slots[0] == null) return false;
		Pair<ItemStack, FluidStack> pair = CombinationRecipes.getOutput(this.slots[0]);
		
		if(pair == null) return false;
		
		ItemStack out = pair.getKey();
		FluidStack fluid = pair.getValue();
		
		if(out != null) {
			if(this.slots[1] != null) {
				if(!out.isItemEqual(this.slots[1]) || (out.stackSize + this.slots[1].stackSize > this.slots[1].getMaxStackSize())) return false;
			}
		}
		
		if(fluid != null) {
			if((this.tank.getTankType() != fluid.type && this.tank.getFill() > 0) || (this.tank.getTankType() == fluid.type && this.tank.getFill()  + fluid.fill > this.tank.getMaxFill())) return false;
		}
		
		return true;
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.wasOn = nbt.getBoolean("wasOn");
		this.heat = nbt.getInteger("heat");
		this.progress = nbt.getInteger("progress");
		this.tank.readFromNBT(nbt, "t");
	}
	
	protected void tryPullHeat() {
		
		if(this.heat >= TileEntityFurnaceCombination.maxHeat) return;
		
		TileEntity con = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord);
		
		if(con instanceof IHeatSource) {
			IHeatSource source = (IHeatSource) con;
			int diff = source.getHeatStored() - this.heat;
			
			if(diff == 0) {
				return;
			}
			
			if(diff > 0) {
				diff = (int) Math.ceil(diff * TileEntityFurnaceCombination.diffusion);
				source.useUpHeat(diff);
				this.heat += diff;
				if(this.heat > TileEntityFurnaceCombination.maxHeat)
					this.heat = TileEntityFurnaceCombination.maxHeat;
				return;
			}
		}
		
		this.heat = Math.max(this.heat - Math.max(this.heat / 1000, 1), 0);
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int meta) {
		return new int[] { 0, 1 };
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return i == 0 && CombinationRecipes.getOutput(itemStack) != null;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 1;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.tank.readFromNBT(nbt, "tank");
		this.progress = nbt.getInteger("prog");
		this.heat = nbt.getInteger("heat");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.tank.writeToNBT(nbt, "tank");
		nbt.setInteger("prog", this.progress);
		nbt.setInteger("heat", this.heat);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerFurnaceCombo(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIFurnaceCombo(player.inventory, this);
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
					this.yCoord + 2.125,
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
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {this.tank};
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {this.tank, this.smoke, this.smoke_leaded, this.smoke_poison};
	}
}
