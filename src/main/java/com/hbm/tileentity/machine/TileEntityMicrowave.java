package com.hbm.tileentity.machine;

import com.hbm.inventory.container.ContainerMicrowave;
import com.hbm.inventory.gui.GUIMicrowave;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IEnergyUser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityMicrowave extends TileEntityMachineBase implements IEnergyUser, IGUIProvider {
	
	public long power;
	public static final long maxPower = 50000;
	public static final int consumption = 50;
	public static final int maxTime = 300;
	public int time;
	public int speed;
	public static final int maxSpeed = 5;

	public TileEntityMicrowave() {
		super(3);
	}

	@Override
	public String getName() {
		return "container.microwave";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			this.updateStandardConnections(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			
			this.power = Library.chargeTEFromItems(this.slots, 2, this.power, TileEntityMicrowave.maxPower);
			
			if(canProcess()) {
				
				if(this.speed >= TileEntityMicrowave.maxSpeed) {
					this.worldObj.func_147480_a(this.xCoord, this.yCoord, this.zCoord, false);
					this.worldObj.newExplosion(null, this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, 7.5F, true, true);
					return;
				}
				
				if(this.time >= TileEntityMicrowave.maxTime) {
					process();
					this.time = 0;
				}
				
				if(canProcess()) {
					this.power -= TileEntityMicrowave.consumption;
					this.time += this.speed * 2;
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setInteger("time", this.time);
			data.setInteger("speed", this.speed);
			networkPack(data, 50);
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.power = data.getLong("power");
		this.time = data.getInteger("time");
		this.speed = data.getInteger("speed");
	}
	
	@Override
	public void handleButtonPacket(int value, int meta) {
		
		if(value == 0)
			this.speed++;
		
		if(value == 1)
			this.speed--;
		
		if(this.speed < 0)
			this.speed = 0;
		
		if(this.speed > TileEntityMicrowave.maxSpeed)
			this.speed = TileEntityMicrowave.maxSpeed;
	}
	
	private void process() {
		
		ItemStack stack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[0]).copy();
		
		if(this.slots[1] == null) {
			this.slots[1] = stack;
		} else {
			this.slots[1].stackSize += stack.stackSize;
		}
		
		decrStackSize(0, 1);
		
		markDirty();
	}
	
	private boolean canProcess() {
		
		if((this.speed  == 0) || (this.power < TileEntityMicrowave.consumption))
			return false;
		
		if(this.slots[0] != null && FurnaceRecipes.smelting().getSmeltingResult(this.slots[0]) != null) {
			
			ItemStack stack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[0]);
			
			if(this.slots[1] == null)
				return true;
			
			if(!stack.isItemEqual(this.slots[1]))
				return false;
			
			return stack.stackSize + this.slots[1].stackSize <= stack.getMaxStackSize();
		}
		
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return i == 0 && FurnaceRecipes.smelting().getSmeltingResult(itemStack) != null;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 1;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 0 ? new int[] { 1 } : new int[] { 0 };
	}
	
	public long getPowerScaled(int i) {
		return (this.power * i) / TileEntityMicrowave.maxPower;
	}
	
	public int getProgressScaled(int i) {
		return (this.time * i) / TileEntityMicrowave.maxTime;
	}
	
	public int getSpeedScaled(int i) {
		return (this.speed * i) / TileEntityMicrowave.maxSpeed;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
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
		return TileEntityMicrowave.maxPower;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.power = nbt.getLong("power");
		this.speed = nbt.getInteger("speed");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setLong("power", this.power);
		nbt.setInteger("speed", this.speed);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMicrowave(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMicrowave(player.inventory, this);
	}
}
