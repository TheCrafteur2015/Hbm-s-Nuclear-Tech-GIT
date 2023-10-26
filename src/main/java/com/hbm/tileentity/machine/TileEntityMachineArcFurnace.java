package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineArcFurnace;
import com.hbm.inventory.container.ContainerMachineArcFurnace;
import com.hbm.inventory.gui.GUIMachineArcFurnace;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IEnergyUser;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class TileEntityMachineArcFurnace extends TileEntityLoadedBase implements ISidedInventory, IEnergyUser, IGUIProvider {

	private ItemStack slots[];
	
	public int dualCookTime;
	public long power;
	public static final long maxPower = 50000;
	public static final int processingSpeed = 20;
	
	//0: i
	//1: o
	//2: 1
	//3: 2
	//4: 3
	//5: b
	private static final int[] slots_io = new int[] {0, 1, 2, 3, 4, 5};
	
	private String customName;
	
	public TileEntityMachineArcFurnace() {
		this.slots = new ItemStack[6];
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
		return hasCustomInventoryName() ? this.customName : "container.arcFurnace";
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
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		
		if(i == 2 || i == 3 || i == 4)
			return itemStack.getItem() == ModItems.arc_electrode || itemStack.getItem() == ModItems.arc_electrode_desh;
		
		if(i == 0)
			return FurnaceRecipes.smelting().getSmeltingResult(itemStack) != null;
		
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
		this.dualCookTime = nbt.getInteger("cookTime");
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
		nbt.setInteger("cookTime", this.dualCookTime);
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
		return TileEntityMachineArcFurnace.slots_io;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		
		if(i == 1)
			return true;
		
		if(i == 2 || i == 3 || i == 4)
			return itemStack.getItem() == ModItems.arc_electrode_burnt;
		
		return false;
	}
	
	public int getDiFurnaceProgressScaled(int i) {
		return (this.dualCookTime * i) / TileEntityMachineArcFurnace.processingSpeed;
	}
	
	public long getPowerRemainingScaled(long i) {
		return (this.power * i) / TileEntityMachineArcFurnace.maxPower;
	}
	
	public boolean hasPower() {
		return this.power >= 250;
	}
	
	public boolean isProcessing() {
		return this.dualCookTime > 0;
	}
	
	private boolean hasElectrodes() {
		
		if(this.slots[2] != null && this.slots[3] != null && this.slots[4] != null) {
			if((this.slots[2].getItem() == ModItems.arc_electrode || this.slots[2].getItem() == ModItems.arc_electrode_desh) &&
					(this.slots[3].getItem() == ModItems.arc_electrode || this.slots[3].getItem() == ModItems.arc_electrode_desh) &&
					(this.slots[4].getItem() == ModItems.arc_electrode || this.slots[4].getItem() == ModItems.arc_electrode_desh))
				return true;
		}
		
		return false;
	}
	
	public boolean canProcess() {
		
		if(!hasElectrodes() || (this.slots[0] == null))
		{
			return false;
		}
        ItemStack itemStack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[0]);
        
		if(itemStack == null)
		{
			return false;
		}
		
		if(this.slots[1] == null)
		{
			return true;
		}
		
		if(!this.slots[1].isItemEqual(itemStack)) {
			return false;
		}
		
		if(this.slots[1].stackSize < getInventoryStackLimit() && this.slots[1].stackSize < this.slots[1].getMaxStackSize()) {
			return true;
		}else{
			return this.slots[1].stackSize < itemStack.getMaxStackSize();
		}
	}
	
	private void processItem() {
		if(canProcess()) {
	        ItemStack itemStack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[0]);
			
			if(this.slots[1] == null)
			{
				this.slots[1] = itemStack.copy();
			}else if(this.slots[1].isItemEqual(itemStack)) {
				this.slots[1].stackSize += itemStack.stackSize;
			}
			
			for(int i = 0; i < 1; i++)
			{
				if(this.slots[i].stackSize <= 0)
				{
					this.slots[i] = new ItemStack(this.slots[i].getItem().setFull3D());
				}else{
					this.slots[i].stackSize--;
				}
				if(this.slots[i].stackSize <= 0)
				{
					this.slots[i] = null;
				}
			}
			
			for(int i = 2; i < 5; i++) {
				if(this.slots[i] != null && this.slots[i].getItem() == ModItems.arc_electrode) {
					if(this.slots[i].getItemDamage() < this.slots[i].getMaxDamage())
						this.slots[i].setItemDamage(this.slots[i].getItemDamage() + 1);
					else
						this.slots[i] = new ItemStack(ModItems.arc_electrode_burnt);
				}
			}
		}
	}
	
	//TODO: fix this punjabi trash
	@Override
	public void updateEntity() {
		boolean flag1 = false;
		
		if(!this.worldObj.isRemote) {
			
			this.updateStandardConnections(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			
			if(hasPower() && canProcess())
			{
				this.dualCookTime++;
				
				this.power -= 250;
				
				if(this.power < 0)
					this.power = 0;
				
				if(this.dualCookTime == TileEntityMachineArcFurnace.processingSpeed)
				{
					this.dualCookTime = 0;
					processItem();
					flag1 = true;
				}
			}else{
				this.dualCookTime = 0;
			}
			
			boolean trigger = true;
			
			if(hasPower() && canProcess() && this.dualCookTime == 0)
			{
				trigger = false;
			}
			
			if(trigger)
            {
                flag1 = true;
                MachineArcFurnace.updateBlockState(this.dualCookTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
			
			if(this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) == ModBlocks.machine_arc_furnace_off) {
				
				int meta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);

				if(hasElectrodes() && meta <= 5) {
					this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, meta + 4, 2);
				}
				if(!hasElectrodes() && meta > 5) {
					this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, meta - 4, 2);
				}
			}
			
			this.power = Library.chargeTEFromItems(this.slots, 5, this.power, TileEntityMachineArcFurnace.maxPower);

			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(this.xCoord, this.yCoord, this.zCoord, this.power), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(this.xCoord, this.yCoord, this.zCoord, this.dualCookTime, 0), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
		}
		
		
		if(flag1)
		{
			markDirty();
		}
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
		return TileEntityMachineArcFurnace.maxPower;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineArcFurnace(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineArcFurnace(player.inventory, this);
	}
}
