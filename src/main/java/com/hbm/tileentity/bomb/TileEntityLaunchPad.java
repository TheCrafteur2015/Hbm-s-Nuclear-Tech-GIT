package com.hbm.tileentity.bomb;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.LaunchPad;
import com.hbm.inventory.container.ContainerLaunchPadTier1;
import com.hbm.inventory.gui.GUILaunchPadTier1;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEMissilePacket;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IEnergyUser;
import api.hbm.item.IDesignatorItem;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityLaunchPad extends TileEntityLoadedBase implements ISidedInventory, IEnergyUser, SimpleComponent, IGUIProvider {

	public ItemStack slots[];
	
	public long power;
	public final long maxPower = 100000;
	
	private static final int[] slots_top = new int[] {0};
	private static final int[] slots_bottom = new int[] { 0, 1, 2};
	private static final int[] slots_side = new int[] {0};
	public int state = 0;
	private String customName;
	
	public TileEntityLaunchPad() {
		this.slots = new ItemStack[3];
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
		return hasCustomInventoryName() ? this.customName : "container.launchPad";
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
		return true;
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
		this.power = nbt.getLong("power");
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
		NBTTagList list = new NBTTagList();
		nbt.setLong("power", this.power);
		
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
	public int[] getAccessibleSlotsFromSide(int p_94128_1_)
    {
        return p_94128_1_ == 0 ? TileEntityLaunchPad.slots_bottom : (p_94128_1_ == 1 ? TileEntityLaunchPad.slots_top : TileEntityLaunchPad.slots_side);
    }

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return false;
	}

	public long getPowerScaled(long i) {
		return (this.power * i) / this.maxPower;
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			this.power = Library.chargeTEFromItems(this.slots, 2, this.power, this.maxPower);
			updateConnections();
			
			PacketDispatcher.wrapper.sendToAllAround(new TEMissilePacket(this.xCoord, this.yCoord, this.zCoord, this.slots[0]), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 250));
			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(this.xCoord, this.yCoord, this.zCoord, this.power), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
		}
	}
	
	private void updateConnections() {
		trySubscribe(this.worldObj, this.xCoord + 1, this.yCoord, this.zCoord, Library.POS_X);
		trySubscribe(this.worldObj, this.xCoord - 1, this.yCoord, this.zCoord, Library.NEG_X);
		trySubscribe(this.worldObj, this.xCoord, this.yCoord, this.zCoord + 1, Library.POS_Z);
		trySubscribe(this.worldObj, this.xCoord, this.yCoord, this.zCoord - 1, Library.NEG_Z);
		trySubscribe(this.worldObj, this.xCoord, this.yCoord - 1, this.zCoord, Library.NEG_Y);
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
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
		return this.maxPower;
	}
	
	@Override
	public long transferPower(long power) {
		
		this.power += power;
		
		if(this.power > getMaxPower()) {
			
			long overshoot = this.power - getMaxPower();
			this.power = getMaxPower();
			return overshoot;
		}
		
		return 0;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.UP && dir != ForgeDirection.UNKNOWN;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
	
	// do some opencomputer stuff
	@Override
	public String getComponentName() {
		return "launch_pad";
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getEnergyInfo(Context context, Arguments args) {
		return new Object[] {getPower(), getMaxPower()};
	}
	
	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCoords(Context context, Arguments args) {
		if (this.slots[1] != null && this.slots[1].getItem() instanceof IDesignatorItem) {
			int xCoord2;
			int zCoord2;
			if (this.slots[1].stackTagCompound != null) {
				xCoord2 = this.slots[1].stackTagCompound.getInteger("xCoord");
				zCoord2 = this.slots[1].stackTagCompound.getInteger("zCoord");
			} else
				return new Object[] {false};

			// Not sure if i should have this
			/*
			if(xCoord2 == xCoord && zCoord2 == zCoord) {
				xCoord2 += 1;
			}
			*/

			return new Object[] {xCoord2, zCoord2};
		}
		return new Object[] {false, "Designator not found"};
	}
	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] setCoords(Context context, Arguments args) {
		if (this.slots[1] != null && this.slots[1].getItem() instanceof IDesignatorItem) {
			this.slots[1].stackTagCompound = new NBTTagCompound();
			this.slots[1].stackTagCompound.setInteger("xCoord", args.checkInteger(0));
			this.slots[1].stackTagCompound.setInteger("zCoord", args.checkInteger(1));

			return new Object[] {true};
		}
		return new Object[] {false, "Designator not found"};
	}
	
	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] launch(Context context, Arguments args) {
		//worldObj.getBlock(xCoord, yCoord, zCoord).explode(worldObj, xCoord, yCoord, zCoord);	
		((LaunchPad) ModBlocks.launch_pad).explode(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
		return new Object[] {};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerLaunchPadTier1(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUILaunchPadTier1(player.inventory, this);
	}
}
