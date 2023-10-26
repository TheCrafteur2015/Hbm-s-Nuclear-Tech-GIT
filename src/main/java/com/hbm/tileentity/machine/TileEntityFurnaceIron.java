package com.hbm.tileentity.machine;

import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerFurnaceIron;
import com.hbm.inventory.gui.GUIFurnaceIron;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.module.ModuleBurnTime;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFurnaceIron extends TileEntityMachineBase implements IGUIProvider {
	
	public int maxBurnTime;
	public int burnTime;
	public boolean wasOn = false;

	public int progress;
	public int processingTime;
	public static final int baseTime = 200;
	
	public ModuleBurnTime burnModule;

	public TileEntityFurnaceIron() {
		super(5);
		
		this.burnModule = new ModuleBurnTime()
				.setLigniteTimeMod(1.25)
				.setCoalTimeMod(1.25)
				.setCokeTimeMod(1.5)
				.setSolidTimeMod(2)
				.setRocketTimeMod(2)
				.setBalefireTimeMod(2);
	}

	@Override
	public String getName() {
		return "container.furnaceIron";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			UpgradeManager.eval(this.slots, 4, 4);
			this.processingTime = TileEntityFurnaceIron.baseTime - (100 * Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3) / 3);
			
			this.wasOn = false;
			
			if(this.burnTime <= 0) {
				
				for(int i = 1; i < 3; i++) {
					if(this.slots[i] != null) {
						
						int fuel = this.burnModule.getBurnTime(this.slots[i]);
						
						if(fuel > 0) {
							this.maxBurnTime = this.burnTime = fuel;
							this.slots[i].stackSize--;

							if(this.slots[i].stackSize == 0) {
								this.slots[i] = this.slots[i].getItem().getContainerItem(this.slots[i]);
							}
							
							break;
						}
					}
				} 
			}
			
			if(canSmelt()) {
				this.wasOn = true;
				this.progress++;
				this.burnTime--;
				
				if(this.progress % 15 == 0) {
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "fire.fire", 1.0F, 0.5F + this.worldObj.rand.nextFloat() * 0.5F);
				}
				
				if(this.progress >= this.processingTime) {
					ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(this.slots[0]);
					
					if(this.slots[3] == null) {
						this.slots[3] = result.copy();
					} else {
						this.slots[3].stackSize += result.stackSize;
					}
					
					decrStackSize(0, 1);
					
					this.progress = 0;
					markDirty();
				}
				if(this.worldObj.getTotalWorldTime() % 20 == 0) PollutionHandler.incrementPollution(this.worldObj, this.xCoord, this.yCoord, this.zCoord, PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND);
			} else {
				this.progress = 0;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("maxBurnTime", this.maxBurnTime);
			data.setInteger("burnTime", this.burnTime);
			data.setInteger("progress", this.progress);
			data.setInteger("processingTime", this.processingTime);
			data.setBoolean("wasOn", this.wasOn);
			networkPack(data, 50);
		} else {
			
			if(this.progress > 0) {
				ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - 10);
				ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
				
				double offset = this.progress % 2 == 0 ? 1 : 0.5;
				this.worldObj.spawnParticle("smoke", this.xCoord + 0.5 - dir.offsetX * offset - rot.offsetX * 0.1875, this.yCoord + 2, this.zCoord + 0.5 - dir.offsetZ * offset - rot.offsetZ * 0.1875, 0.0, 0.01, 0.0);
				
				if(this.progress % 5 == 0) {
					double rand = this.worldObj.rand.nextDouble();
					this.worldObj.spawnParticle("flame", this.xCoord + 0.5 + dir.offsetX * 0.25 + rot.offsetX * rand, this.yCoord + 0.25 + this.worldObj.rand.nextDouble() * 0.25, this.zCoord + 0.5 + dir.offsetZ * 0.25 + rot.offsetZ * rand, 0.0, 0.0, 0.0);
				}
			}
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.maxBurnTime = nbt.getInteger("maxBurnTime");
		this.burnTime = nbt.getInteger("burnTime");
		this.progress = nbt.getInteger("progress");
		this.processingTime = nbt.getInteger("processingTime");
		this.wasOn = nbt.getBoolean("wasOn");
	}
	
	public boolean canSmelt() {
		
		if((this.burnTime <= 0) || (this.slots[0] == null)) return false;
		
		ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(this.slots[0]);
		
		if(result == null) return false;
		if(this.slots[3] == null) return true;
		
		if(!result.isItemEqual(this.slots[3])) return false;
		if(result.stackSize + this.slots[3].stackSize > this.slots[3].getMaxStackSize()) return false;
		
		return true;
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int meta) {
		return new int[] { 0, 1, 2, 3 };
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		
		if(i == 0)
			return FurnaceRecipes.smelting().getSmeltingResult(itemStack) != null;
		
		if(i < 3)
			return this.burnModule.getBurnTime(itemStack) > 0;
			
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 3;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.maxBurnTime = nbt.getInteger("maxBurnTime");
		this.burnTime = nbt.getInteger("burnTime");
		this.progress = nbt.getInteger("progress");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("maxBurnTime", this.maxBurnTime);
		nbt.setInteger("burnTime", this.burnTime);
		nbt.setInteger("progress", this.progress);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerFurnaceIron(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIFurnaceIron(player.inventory, this);
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
					this.yCoord + 3,
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
}
