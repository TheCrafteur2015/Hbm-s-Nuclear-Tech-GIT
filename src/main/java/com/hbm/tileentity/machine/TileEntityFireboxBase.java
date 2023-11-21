package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.items.ItemEnums.EnumAshType;
import com.hbm.module.ModuleBurnTime;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachinePolluting;
import com.hbm.util.ItemStackUtil;

import api.hbm.fluid.IFluidStandardSender;
import api.hbm.tile.IHeatSource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityFireboxBase extends TileEntityMachinePolluting implements IFluidStandardSender, IGUIProvider, IHeatSource {

	public int maxBurnTime;
	public int burnTime;
	public int burnHeat;
	public boolean wasOn = false;
	private int playersUsing = 0;
	
	public float doorAngle = 0;
	public float prevDoorAngle = 0;

	public int heatEnergy;


	public TileEntityFireboxBase() {
		super(2, 50);
	}
	
	@Override
	public void openInventory() {
		if(!this.worldObj.isRemote) this.playersUsing++;
	}
	
	@Override
	public void closeInventory() {
		if(!this.worldObj.isRemote) this.playersUsing--;
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			for(int i = 2; i < 6; i++) {
				ForgeDirection dir = ForgeDirection.getOrientation(i);
				ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
				
				for(int j = -1; j <= 1; j++) {
					sendSmoke(this.xCoord + dir.offsetX * 2 + rot.offsetX * j, this.yCoord, this.zCoord + dir.offsetZ * 2 + rot.offsetZ * j, dir);
				}
			}
			
			this.wasOn = false;
			
			if(this.burnTime <= 0) {
				
				for(int i = 0; i < 2; i++) {
					if(this.slots[i] != null) {
						
						int baseTime = getModule().getBurnTime(this.slots[i]);
						
						if(baseTime > 0) {
							int fuel = (int) (baseTime * getTimeMult());
							
							TileEntity below = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord);
							
							if(below instanceof TileEntityAshpit) {
								TileEntityAshpit ashpit = (TileEntityAshpit) below;
								EnumAshType type = getAshFromFuel(this.slots[i]);
								if(type == EnumAshType.WOOD) ashpit.ashLevelWood += baseTime;
								if(type == EnumAshType.COAL) ashpit.ashLevelCoal += baseTime;
								if(type == EnumAshType.MISC) ashpit.ashLevelMisc += baseTime;
							}
							
							this.maxBurnTime = this.burnTime = fuel;
							this.burnHeat = getModule().getBurnHeat(getBaseHeat(), this.slots[i]);
							this.slots[i].stackSize--;

							if(this.slots[i].stackSize == 0) {
								this.slots[i] = this.slots[i].getItem().getContainerItem(this.slots[i]);
							}

							this.wasOn = true;
							break;
						}
					}
				} 
			} else {
				
				if(this.heatEnergy < getMaxHeat()) {
					this.burnTime--;
					if(this.worldObj.getTotalWorldTime() % 20 == 0) pollute(PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND * 3);
				}
				this.wasOn = true;
				
				if(this.worldObj.rand.nextInt(15) == 0) {
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "fire.fire", 1.0F, 0.5F + this.worldObj.rand.nextFloat() * 0.5F);
				}
			}
			
			if(this.wasOn) {
				this.heatEnergy = Math.min(this.heatEnergy + this.burnHeat, getMaxHeat());
			} else {
				this.heatEnergy = Math.max(this.heatEnergy - Math.max(this.heatEnergy / 1000, 1), 0);
				this.burnHeat = 0;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("maxBurnTime", this.maxBurnTime);
			data.setInteger("burnTime", this.burnTime);
			data.setInteger("burnHeat", this.burnHeat);
			data.setInteger("heatEnergy", this.heatEnergy);
			data.setInteger("playersUsing", this.playersUsing);
			data.setBoolean("wasOn", this.wasOn);
			networkPack(data, 50);
		} else {
			this.prevDoorAngle = this.doorAngle;
			float swingSpeed = (this.doorAngle / 10F) + 3;
			
			if(this.playersUsing > 0) {
				this.doorAngle += swingSpeed;
			} else {
				this.doorAngle -= swingSpeed;
			}
			
			this.doorAngle = MathHelper.clamp_float(this.doorAngle, 0F, 135F);
			
			if(this.wasOn && this.worldObj.getTotalWorldTime() % 5 == 0) {
				ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
				double x = this.xCoord + 0.5 + dir.offsetX;
				double y = this.yCoord + 0.25;
				double z = this.zCoord + 0.5 + dir.offsetZ;
				this.worldObj.spawnParticle("flame", x + this.worldObj.rand.nextDouble() * 0.5 - 0.25, y + this.worldObj.rand.nextDouble() * 0.25, z + this.worldObj.rand.nextDouble() * 0.5 - 0.25, 0, 0, 0);
			}
		}
	}
	
	public static EnumAshType getAshFromFuel(ItemStack stack) {

		List<String> names = ItemStackUtil.getOreDictNames(stack);
		
		for(String name : names) {
			if(name.contains("Coke") || name.contains("Coal") || name.contains("Lignite"))	return EnumAshType.COAL;
			if(name.startsWith("log"))		return EnumAshType.WOOD;
			if(name.contains("Wood"))		return EnumAshType.WOOD;
			if(name.contains("Sapling"))	return EnumAshType.WOOD;
		}

		return EnumAshType.MISC;
	}

	public abstract ModuleBurnTime getModule();
	public abstract int getBaseHeat();
	public abstract double getTimeMult();
	public abstract int getMaxHeat();

	@Override
	public int[] getAccessibleSlotsFromSide(int meta) {
		return new int[] { 0, 1 };
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return getModule().getBurnTime(itemStack) > 0;
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.maxBurnTime = nbt.getInteger("maxBurnTime");
		this.burnTime = nbt.getInteger("burnTime");
		this.burnHeat = nbt.getInteger("burnHeat");
		this.heatEnergy = nbt.getInteger("heatEnergy");
		this.playersUsing = nbt.getInteger("playersUsing");
		this.wasOn = nbt.getBoolean("wasOn");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.maxBurnTime = nbt.getInteger("maxBurnTime");
		this.burnTime = nbt.getInteger("burnTime");
		this.burnHeat = nbt.getInteger("burnHeat");
		this.heatEnergy = nbt.getInteger("heatEnergy");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("maxBurnTime", this.maxBurnTime);
		nbt.setInteger("burnTime", this.burnTime);
		nbt.setInteger("burnHeat", this.burnHeat);
		nbt.setInteger("heatEnergy", this.heatEnergy);
	}

	@Override
	public int getHeatStored() {
		return this.heatEnergy;
	}

	@Override
	public void useUpHeat(int heat) {
		this.heatEnergy = Math.max(0, this.heatEnergy - heat);
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
					this.yCoord + 1,
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
		return new FluidTank[0];
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return getSmokeTanks();
	}
	
	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN && dir != ForgeDirection.DOWN;
	}
}
