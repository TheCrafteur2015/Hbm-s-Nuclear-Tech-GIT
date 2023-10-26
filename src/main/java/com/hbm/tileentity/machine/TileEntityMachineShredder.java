package com.hbm.tileentity.machine;

import com.hbm.inventory.container.ContainerMachineShredder;
import com.hbm.inventory.gui.GUIMachineShredder;
import com.hbm.inventory.recipes.ShredderRecipes;
import com.hbm.items.machine.ItemBlades;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyUser;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineShredder extends TileEntityLoadedBase implements ISidedInventory, IEnergyUser, IGUIProvider {

	private ItemStack slots[];

	public long power;
	public int progress;
	public int soundCycle = 0;
	public static final long maxPower = 10000;
	public static final int processingSpeed = 60;
	
	private static final int[] slots_io = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29};
	
	private String customName;
	
	public TileEntityMachineShredder() {
		this.slots = new ItemStack[30];
	}

	@Override
	public int getSizeInventory() {
		return this.slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return this.slots[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if(this.slots[i] != null)
		{
			ItemStack itemStack = this.slots[i];
			this.slots[i] = null;
			return itemStack;
		} else {
		return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		this.slots[i] = itemStack;
		if(itemStack != null && itemStack.stackSize > getInventoryStackLimit())
		{
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return hasCustomInventoryName() ? this.customName : "container.machineShredder";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}
	
	public void setCustomName(String name) {
		this.customName = name;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this)
		{
			return false;
		}else{
			return player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <=64;
		}
	}
	
	//You scrubs aren't needed for anything (right now)
	@Override
	public void openInventory() {}
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		if(i < 9) return ShredderRecipes.getShredderResult(stack) != null && !(stack.getItem() instanceof ItemBlades);
		if(i == 29) return stack.getItem() instanceof IBatteryItem;
		if(i == 27 || i == 28) return stack.getItem() instanceof ItemBlades;
		
		return false;
	}
	
	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(this.slots[i] != null)
		{
			if(this.slots[i].stackSize <= j)
			{
				ItemStack itemStack = this.slots[i];
				this.slots[i] = null;
				return itemStack;
			}
			ItemStack itemStack1 = this.slots[i].splitStack(j);
			if (this.slots[i].stackSize == 0)
			{
				this.slots[i] = null;
			}
			
			return itemStack1;
		} else {
			return null;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);
		
		this.power = nbt.getLong("powerTime");
		this.slots = new ItemStack[getSizeInventory()];
		
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if(b0 >= 0 && b0 < this.slots.length)
			{
				this.slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("powerTime", this.power);
		NBTTagList list = new NBTTagList();
		
		for(int i = 0; i < this.slots.length; i++)
		{
			if(this.slots[i] != null)
			{
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte)i);
				this.slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return TileEntityMachineShredder.slots_io;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemStack, int side) {
		if((slot >= 9 && slot != 27 && slot != 28) || !isItemValidForSlot(slot, itemStack))
			return false;
		
		if(this.slots[slot] == null)
			return true;
		
		int size = this.slots[slot].stackSize;
		
		for(int k = 0; k < 9; k++) {
			if((this.slots[k] == null) || (this.slots[k].getItem() == itemStack.getItem() && this.slots[k].getItemDamage() == itemStack.getItemDamage() && this.slots[k].stackSize < size))
				return false;
		}
		
		return true;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		if(i >= 9 && i <= 26) return true;
		if(i >= 27 && i <= 28) if(itemStack.getItemDamage() == itemStack.getMaxDamage() && itemStack.getMaxDamage() > 0) return true;
		
		return false;
	}
	
	public int getDiFurnaceProgressScaled(int i) {
		return (this.progress * i) / TileEntityMachineShredder.processingSpeed;
	}
	
	public boolean hasPower() {
		return this.power > 0;
	}
	
	public boolean isProcessing() {
		return this.progress > 0;
	}
	
	@Override
	public void updateEntity() {
		boolean flag1 = false;
		
		if(!this.worldObj.isRemote) {
			
			updateConnections();
			
			if(hasPower() && canProcess())
			{
				this.progress++;
				
				this.power -= 5;
				
				if(this.progress == TileEntityMachineShredder.processingSpeed)
				{
					for(int i = 27; i <= 28; i++)
						if(this.slots[i].getMaxDamage() > 0)
							this.slots[i].setItemDamage(this.slots[i].getItemDamage() + 1);
					
					this.progress = 0;
					processItem();
					flag1 = true;
				}
				if(this.soundCycle == 0)
		        	this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "minecart.base", 1.0F, 0.75F);
				this.soundCycle++;
				
				if(this.soundCycle >= 50)
					this.soundCycle = 0;
			}else{
				this.progress = 0;
			}
			
			boolean trigger = true;
			
			if(hasPower() && canProcess() && this.progress == 0)
			{
				trigger = false;
			}
			
			if(trigger)
            {
                flag1 = true;
            }
			
			this.power = Library.chargeTEFromItems(this.slots, 29, this.power, TileEntityMachineShredder.maxPower);
			
			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(this.xCoord, this.yCoord, this.zCoord, this.power), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
		}
		
		if(flag1)
		{
			markDirty();
		}
	}
	
	private void updateConnections() {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			trySubscribe(this.worldObj, this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ, dir);
	}
	
	public void processItem() {
		
		for(int inpSlot = 0; inpSlot < 9; inpSlot++)
		{
			if(this.slots[inpSlot] != null && hasSpace(this.slots[inpSlot]))
			{
				ItemStack inp = this.slots[inpSlot];
				ItemStack outp = ShredderRecipes.getShredderResult(inp);
				
				boolean flag = false;
				
				for (int outSlot = 9; outSlot < 27; outSlot++)
				{
					if (this.slots[outSlot] != null && this.slots[outSlot].getItem() == outp.getItem() && 
							this.slots[outSlot].getItemDamage() == outp.getItemDamage() &&
							this.slots[outSlot].stackSize + outp.stackSize <= outp.getMaxStackSize()) {
						
						this.slots[outSlot].stackSize += outp.stackSize;
						this.slots[inpSlot].stackSize -= 1;
						flag = true;
						break;
					}
				}
				
				if(!flag)
					for (int outSlot = 9; outSlot < 27; outSlot++)
					{
						if (this.slots[outSlot] == null) {
							this.slots[outSlot] = outp.copy();
							this.slots[inpSlot].stackSize -= 1;
							break;
						}
					}
				
				if(this.slots[inpSlot].stackSize <= 0)
					this.slots[inpSlot] = null;
			}
		}
	}
	
	public boolean canProcess() {
		if(this.slots[27] != null && this.slots[28] != null && 
				getGearLeft() > 0 && getGearLeft() < 3 && 
				getGearRight() > 0 && getGearRight() < 3) {
			
			for(int i = 0; i < 9; i++)
			{
				if(this.slots[i] != null && this.slots[i].stackSize > 0 && hasSpace(this.slots[i]))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean hasSpace(ItemStack stack) {
		
		ItemStack result = ShredderRecipes.getShredderResult(stack);
		
		if (result != null)
			for (int i = 9; i < 27; i++) {
				if ((this.slots[i] == null) || (this.slots[i] != null && this.slots[i].getItem().equals(result.getItem())
						&& this.slots[i].stackSize + result.stackSize <= result.getMaxStackSize())) {
					return true;
				}
			}
		
		return false;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
		
	}
	
	public long getPowerScaled(long i) {
		return (this.power * i) / TileEntityMachineShredder.maxPower;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return TileEntityMachineShredder.maxPower;
	}
	
	public int getGearLeft() {
		
		if(this.slots[27] != null && this.slots[27].getItem() instanceof ItemBlades)
		{
			if(this.slots[27].getMaxDamage() == 0)
				return 1;
			
			if(this.slots[27].getItemDamage() < this.slots[27].getItem().getMaxDamage()/2)
			{
				return 1;
			} else if(this.slots[27].getItemDamage() != this.slots[27].getItem().getMaxDamage()) {
				return 2;
			} else {
				return 3;
			}
		}
		
		return 0;
	}
	
	public int getGearRight() {
		
		if(this.slots[28] != null && this.slots[28].getItem() instanceof ItemBlades)
		{
			if(this.slots[28].getMaxDamage() == 0)
				return 1;
			
			if(this.slots[28].getItemDamage() < this.slots[28].getItem().getMaxDamage()/2)
			{
				return 1;
			} else if(this.slots[28].getItemDamage() != this.slots[28].getItem().getMaxDamage()) {
				return 2;
			} else {
				return 3;
			}
		}
		
		return 0;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineShredder(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineShredder(player.inventory, this);
	}
}
