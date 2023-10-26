package com.hbm.tileentity.machine;

import com.hbm.handler.MissileStruct;
import com.hbm.inventory.container.ContainerMachineMissileAssembly;
import com.hbm.inventory.gui.GUIMachineMissileAssembly;
import com.hbm.items.weapon.ItemCustomMissile;
import com.hbm.items.weapon.ItemMissile;
import com.hbm.items.weapon.ItemMissile.FuelType;
import com.hbm.items.weapon.ItemMissile.PartType;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEMissileMultipartPacket;
import com.hbm.tileentity.IGUIProvider;

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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityMachineMissileAssembly extends TileEntity implements ISidedInventory, IGUIProvider {

	private ItemStack slots[];
	
	public MissileStruct load;

	private static final int[] access = new int[] { 0 };

	private String customName;

	public TileEntityMachineMissileAssembly() {
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
		if (this.slots[i] != null) {
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
		if (itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return hasCustomInventoryName() ? this.customName : "container.missileAssembly";
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
		if (this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this) {
			return false;
		} else {
			return player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64;
		}
	}

	// You scrubs aren't needed for anything (right now)
	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return false;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (this.slots[i] != null) {
			if (this.slots[i].stackSize <= j) {
				ItemStack itemStack = this.slots[i];
				this.slots[i] = null;
				return itemStack;
			}
			ItemStack itemStack1 = this.slots[i].splitStack(j);
			if (this.slots[i].stackSize == 0) {
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

		this.slots = new ItemStack[getSizeInventory()];

		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if (b0 >= 0 && b0 < this.slots.length) {
				this.slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		NBTTagList list = new NBTTagList();

		for (int i = 0; i < this.slots.length; i++) {
			if (this.slots[i] != null) {
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte) i);
				this.slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return TileEntityMachineMissileAssembly.access;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return false;
	}
	
	@Override
	public void updateEntity() {

		if(!this.worldObj.isRemote) {
			
			MissileStruct multipart = new MissileStruct(this.slots[1], this.slots[2], this.slots[3], this.slots[4]);
			
			PacketDispatcher.wrapper.sendToAllAround(new TEMissileMultipartPacket(this.xCoord, this.yCoord, this.zCoord, multipart), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 250));
		}
	}
	
	public int fuselageState() {
		
		if(this.slots[2] != null && this.slots[2].getItem() instanceof ItemMissile) {
			
			ItemMissile part = (ItemMissile)this.slots[2].getItem();
			
			if(part.type == PartType.FUSELAGE)
				return 1;
		}
		
		return 0;
	}

	public int chipState() {
		
		if(this.slots[0] != null && this.slots[0].getItem() instanceof ItemMissile) {
			
			ItemMissile part = (ItemMissile)this.slots[0].getItem();
			
			if(part.type == PartType.CHIP)
				return 1;
		}
		
		return 0;
	}

	public int warheadState() {
		
		if(this.slots[1] != null && this.slots[1].getItem() instanceof ItemMissile &&
				this.slots[2] != null && this.slots[2].getItem() instanceof ItemMissile &&
				this.slots[4] != null && this.slots[4].getItem() instanceof ItemMissile) {

			ItemMissile part = (ItemMissile)this.slots[1].getItem();
			ItemMissile fuselage = (ItemMissile)this.slots[2].getItem();
			ItemMissile thruster = (ItemMissile)this.slots[4].getItem();

			if(part.type == PartType.WARHEAD && fuselage.type == PartType.FUSELAGE && thruster.type == PartType.THRUSTER) {
				float weight = (Float)part.attributes[2];
				float thrust = (Float)thruster.attributes[2];
				
				if(part.bottom == fuselage.top && weight <= thrust)
					return 1;
			}
		}
		
		return 0;
	}

	public int stabilityState() {
		
		if(this.slots[3] == null)
			return -1;
		
		if(this.slots[3] != null && this.slots[3].getItem() instanceof ItemMissile &&
				this.slots[2] != null && this.slots[2].getItem() instanceof ItemMissile) {

			ItemMissile part = (ItemMissile)this.slots[3].getItem();
			ItemMissile fuselage = (ItemMissile)this.slots[2].getItem();
			
			if(part.top == fuselage.bottom && part.type == PartType.FINS)
				return 1;
		}
		
		return 0;
	}

	public int thrusterState() {
		
		if(this.slots[4] != null && this.slots[4].getItem() instanceof ItemMissile &&
				this.slots[2] != null && this.slots[2].getItem() instanceof ItemMissile) {

			ItemMissile part = (ItemMissile)this.slots[4].getItem();
			ItemMissile fuselage = (ItemMissile)this.slots[2].getItem();
			
			if(part.type == PartType.THRUSTER && fuselage.type == PartType.FUSELAGE &&
					part.top == fuselage.bottom && (FuelType)part.attributes[0] == (FuelType)fuselage.attributes[0]) {
				return 1;
			}
		}
		
		return 0;
	}
	
	public boolean canBuild() {
		
		if(this.slots[5] == null && chipState() == 1 && warheadState() == 1 && fuselageState() == 1 && thrusterState() == 1) {
			return stabilityState() != 0;
		}
		
		return false;
	}

	public void construct() {
		
		if(!canBuild())
			return;
		
		this.slots[5] = ItemCustomMissile.buildMissile(this.slots[0], this.slots[1], this.slots[2], this.slots[3], this.slots[4]).copy();
		
		if(stabilityState() == 1)
			this.slots[3] = null;

		this.slots[0] = null;
		this.slots[1] = null;
		this.slots[2] = null;
		this.slots[4] = null;

		this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.missileAssembly2", 1F, 1F);
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
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineMissileAssembly(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineMissileAssembly(player.inventory, this);
	}
}
