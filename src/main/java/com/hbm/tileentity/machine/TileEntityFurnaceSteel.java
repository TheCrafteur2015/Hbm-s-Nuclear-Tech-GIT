package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.inventory.container.ContainerFurnaceSteel;
import com.hbm.inventory.gui.GUIFurnaceSteel;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.ItemStackUtil;

import api.hbm.tile.IHeatSource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFurnaceSteel extends TileEntityMachineBase implements IGUIProvider {

	public int[] progress = new int[3];
	public int[] bonus = new int[3];
	public static final int processTime = 40_000; // assuming vanilla furnace rules with 200 ticks of coal fire burning at 200HU/t
	
	public int heat;
	public static final int maxHeat = 100_000;
	public static final double diffusion = 0.05D;
	
	private ItemStack[] lastItems = new ItemStack[3];
	
	public boolean wasOn = false;
	
	public TileEntityFurnaceSteel() {
		super(6);
	}

	@Override
	public String getName() {
		return "container.furnaceSteel";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			tryPullHeat();
			
			this.wasOn = false;
			
			int burn = (this.heat - TileEntityFurnaceSteel.maxHeat / 3) / 10;
			
			for(int i = 0; i < 3; i++) {
				
				if(this.slots[i] == null || this.lastItems[i] == null || !this.slots[i].isItemEqual(this.lastItems[i])) {
					this.progress[i] = 0;
					this.bonus[i] = 0;
				}
				
				if(canSmelt(i)) {
					this.progress[i] += burn;
					this.heat -= burn;
					this.wasOn = true;
					if(this.worldObj.getTotalWorldTime() % 20 == 0) PollutionHandler.incrementPollution(this.worldObj, this.xCoord, this.yCoord, this.zCoord, PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND * 2);
				}
				
				this.lastItems[i] = this.slots[i];
				
				if(this.progress[i] >= TileEntityFurnaceSteel.processTime) {
					ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(this.slots[i]);
					
					if(this.slots[i + 3] == null) {
						this.slots[i + 3] = result.copy();
					} else {
						this.slots[i + 3].stackSize += result.stackSize;
					}
					
					addBonus(this.slots[i], i);
					
					while(this.bonus[i] >= 100) {
						this.slots[i + 3].stackSize =  Math.min(this.slots[i + 3].getMaxStackSize(), this.slots[i + 3].stackSize + result.stackSize);
						this.bonus[i] -= 100;
					}
					
					decrStackSize(i, 1);
					
					this.progress[i] = 0;
					
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setIntArray("progress", this.progress);
			data.setIntArray("bonus", this.bonus);
			data.setInteger("heat", this.heat);
			data.setBoolean("wasOn", this.wasOn);
			networkPack(data, 50);
		} else {
			
			if(this.wasOn) {
				ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - 10);
				ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
				
				this.worldObj.spawnParticle("smoke", this.xCoord + 0.5 - dir.offsetX * 1.125 - rot.offsetX * 0.75, this.yCoord + 2.625, this.zCoord + 0.5 - dir.offsetZ * 1.125 - rot.offsetZ * 0.75, 0.0, 0.05, 0.0);
				
				if(this.worldObj.rand.nextInt(20) == 0)
					this.worldObj.spawnParticle("cloud", this.xCoord + 0.5 + dir.offsetX * 0.75, this.yCoord + 2, this.zCoord + 0.5 + dir.offsetZ * 0.75, 0.0, 0.05, 0.0);

				if(this.worldObj.rand.nextInt(15) == 0)
					this.worldObj.spawnParticle("lava", this.xCoord + 0.5 + dir.offsetX * 1.5 + rot.offsetX * (this.worldObj.rand.nextDouble() - 0.5), this.yCoord + 0.75, this.zCoord + 0.5 + dir.offsetZ * 1.5 + rot.offsetZ * (this.worldObj.rand.nextDouble() - 0.5), dir.offsetX * 0.5D, 0.05, dir.offsetZ * 0.5D);

			}
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.progress = nbt.getIntArray("progress");
		this.bonus = nbt.getIntArray("bonus");
		this.heat = nbt.getInteger("heat");
		this.wasOn = nbt.getBoolean("wasOn");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.progress = nbt.getIntArray("progress");
		this.bonus = nbt.getIntArray("bonus");
		this.heat = nbt.getInteger("heat");
		
		NBTTagList list = nbt.getTagList("lastItems", 10);
		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("lastItem");
			if(b0 >= 0 && b0 < this.lastItems.length) {
				this.lastItems[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setIntArray("progress", this.progress);
		nbt.setIntArray("bonus", this.bonus);
		nbt.setInteger("heat", this.heat);
		
		NBTTagList list = new NBTTagList();
		for(int i = 0; i < this.lastItems.length; i++) {
			if(this.lastItems[i] != null) {
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("lastItem", (byte) i);
				this.lastItems[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("lastItems", list);
	}
	
	protected void addBonus(ItemStack stack, int index) {
		
		List<String> names = ItemStackUtil.getOreDictNames(stack);
		
		for(String name : names) {
			if(name.startsWith("ore")) { this.bonus[index] += 25; return; }
			if(name.startsWith("log") || name.equals("anyTar")) { this.bonus[index] += 50; return; }
		}
	}
	
	protected void tryPullHeat() {
		
		if(this.heat >= TileEntityFurnaceSteel.maxHeat) return;
		
		TileEntity con = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord);
		
		if(con instanceof IHeatSource) {
			IHeatSource source = (IHeatSource) con;
			int diff = source.getHeatStored() - this.heat;
			
			if(diff == 0) {
				return;
			}
			
			if(diff > 0) {
				diff = (int) Math.ceil(diff * TileEntityFurnaceSteel.diffusion);
				source.useUpHeat(diff);
				this.heat += diff;
				if(this.heat > TileEntityFurnaceSteel.maxHeat)
					this.heat = TileEntityFurnaceSteel.maxHeat;
				return;
			}
		}
		
		this.heat = Math.max(this.heat - Math.max(this.heat / 1000, 1), 0);
	}
	
	public boolean canSmelt(int index) {
		
		if((this.heat < TileEntityFurnaceSteel.maxHeat / 3) || (this.slots[index] == null)) return false;
		
		ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(this.slots[index]);
		
		if(result == null) return false;
		if(this.slots[index + 3] == null) return true;
		
		if(!result.isItemEqual(this.slots[index + 3])) return false;
		if(result.stackSize + this.slots[index + 3].stackSize > this.slots[index + 3].getMaxStackSize()) return false;
		
		return true;
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int meta) {
		return new int[] { 0, 1, 2, 3, 4, 5 };
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		
		if(i < 3)
			return FurnaceRecipes.smelting().getSmeltingResult(itemStack) != null;
		
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i > 2;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerFurnaceSteel(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIFurnaceSteel(player.inventory, this);
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
