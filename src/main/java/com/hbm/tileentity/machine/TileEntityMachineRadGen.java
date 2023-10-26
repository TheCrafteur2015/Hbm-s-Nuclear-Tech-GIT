package com.hbm.tileentity.machine;

import java.util.HashMap;

import com.hbm.blocks.BlockDummyable;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.container.ContainerMachineRadGen;
import com.hbm.inventory.gui.GUIMachineRadGen;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemWasteLong;
import com.hbm.items.special.ItemWasteShort;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.Tuple.Triplet;

import api.hbm.energy.IEnergyGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineRadGen extends TileEntityMachineBase implements IEnergyGenerator, IGUIProvider {

	public int[] progress = new int[12];
	public int[] maxProgress = new int[12];
	public int[] production = new int[12];
	public ItemStack[] processing = new ItemStack[12];
	
	public long power;
	public static final long maxPower = 1000000;
	
	public boolean isOn = false;

	public TileEntityMachineRadGen() {
		super(24);
	}

	@Override
	public String getName() {
		return "container.radGen";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {

			ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
			sendPower(this.worldObj, this.xCoord - dir.offsetX * 4, this.yCoord, this.zCoord - dir.offsetZ * 4, dir.getOpposite());
			
			//check if reload necessary for any queues
			for(int i = 0; i < 12; i++) {
				
				if(this.processing[i] == null && this.slots[i] != null && getDurationFromItem(this.slots[i]) > 0 &&
						(getOutputFromItem(this.slots[i]) == null || this.slots[i + 12] == null ||
						(getOutputFromItem(this.slots[i]).getItem() == this.slots[i + 12].getItem() && getOutputFromItem(this.slots[i]).getItemDamage() == this.slots[i + 12].getItemDamage() &&
						getOutputFromItem(this.slots[i]).stackSize + this.slots[i + 12].stackSize <= this.slots[i + 12].getMaxStackSize()))) {
					
					this.progress[i] = 0;
					this.maxProgress[i] = getDurationFromItem(this.slots[i]);
					this.production[i] = getPowerFromItem(this.slots[i]);
					this.processing[i] = new ItemStack(this.slots[i].getItem(), 1, this.slots[i].getItemDamage());
					decrStackSize(i, 1);
					markDirty();
				}
			}
			
			this.isOn = false;
			
			for(int i = 0; i < 12; i++) {
				
				if(this.processing[i] != null) {
					
					this.isOn = true;
					this.power += this.production[i];
					this.progress[i]++;
					
					if(this.progress[i] >= this.maxProgress[i]) {
						this.progress[i] = 0;
						ItemStack out = getOutputFromItem(this.processing[i]);
						
						if(out != null) {
							
							if(this.slots[i + 12] == null) {
								this.slots[i + 12] = out;
							} else {
								this.slots[i + 12].stackSize += out.stackSize;
							}
						}
						
						this.processing[i] = null;
						markDirty();
					}
				}
			}
			
			if(this.power > TileEntityMachineRadGen.maxPower)
				this.power = TileEntityMachineRadGen.maxPower;
			
			NBTTagCompound data = new NBTTagCompound();
			data.setIntArray("progress", this.progress);
			data.setIntArray("maxProgress", this.maxProgress);
			data.setIntArray("production", this.production);
			data.setLong("power", this.power);
			data.setBoolean("isOn", this.isOn);
			networkPack(data, 50);
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.progress = nbt.getIntArray("progress");
		this.maxProgress = nbt.getIntArray("maxProgress");
		this.production = nbt.getIntArray("production");
		this.power = nbt.getLong("power");
		this.isOn = nbt.getBoolean("isOn");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.progress = nbt.getIntArray("progress");
		
		if(this.progress.length != 12) {
			this.progress = new int[12];
			return;
		}
		
		this.maxProgress = nbt.getIntArray("maxProgress");
		this.production = nbt.getIntArray("production");
		this.power = nbt.getLong("power");
		this.isOn = nbt.getBoolean("isOn");

		NBTTagList list = nbt.getTagList("progressing", 10);
		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if(b0 >= 0 && b0 < this.processing.length) {
				this.processing[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
		
		this.power = nbt.getLong("power");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setIntArray("progress", this.progress);
		nbt.setIntArray("maxProgress", this.maxProgress);
		nbt.setIntArray("production", this.production);
		nbt.setLong("power", this.power);
		nbt.setBoolean("isOn", this.isOn);
		
		NBTTagList list = new NBTTagList();
		for(int i = 0; i < this.processing.length; i++) {
			if(this.processing[i] != null) {
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte) i);
				this.processing[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("progressing", list);
		
		nbt.setLong("power", this.power);
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		
		if(i >= 12 || getDurationFromItem(stack) <= 0)
			return false;
		
		if(this.slots[i] == null)
			return true;
		
		int size = this.slots[i].stackSize;
		
		for(int j = 0; j < 12; j++) {
			if((this.slots[j] == null) || (this.slots[j].getItem() == stack.getItem() && this.slots[j].getItemDamage() == stack.getItemDamage() && this.slots[j].stackSize < size))
				return false;
		}
		
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int i) {
		return new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
				12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i >= 12;
	}
	
	public static final HashMap<ComparableStack, Triplet<Integer, Integer, ItemStack>> fuels = new HashMap<>();
	
	static {

		for(int i = 0; i < ItemWasteShort.WasteClass.values().length; i++) {
			TileEntityMachineRadGen.fuels.put(	new ComparableStack(ModItems.nuclear_waste_short, 1, i),		new Triplet<>(150,	30 * 60 * 20,		new ItemStack(ModItems.nuclear_waste_short_depleted, 1, i)));
			TileEntityMachineRadGen.fuels.put(	new ComparableStack(ModItems.nuclear_waste_short_tiny, 1, i),	new Triplet<>(15,	3 * 60 * 20,		new ItemStack(ModItems.nuclear_waste_short_depleted_tiny, 1, i)));
		}
		for(int i = 0; i < ItemWasteLong.WasteClass.values().length; i++) {
			TileEntityMachineRadGen.fuels.put(	new ComparableStack(ModItems.nuclear_waste_long, 1, i),			new Triplet<>(50,	2 * 60 * 60 * 20,	new ItemStack(ModItems.nuclear_waste_long_depleted, 1, i)));
			TileEntityMachineRadGen.fuels.put(	new ComparableStack(ModItems.nuclear_waste_long_tiny, 1, i),	new Triplet<>(5,		12 * 60 * 20,		new ItemStack(ModItems.nuclear_waste_long_depleted_tiny, 1, i)));
		}
		
		TileEntityMachineRadGen.fuels.put(		new ComparableStack(ModItems.scrap_nuclear),					new Triplet<Integer, Integer, ItemStack>(5,		5 * 60 * 20,		null));
	}
	
	private Triplet<Integer, Integer, ItemStack> grabResult(ItemStack stack) {
		return TileEntityMachineRadGen.fuels.get(new ComparableStack(stack).makeSingular());
	}
	
	private int getPowerFromItem(ItemStack stack) {
		Triplet<Integer, Integer, ItemStack> result = grabResult(stack);
		if(result == null)
			return 0;
		return result.getX();
	}
	
	private int getDurationFromItem(ItemStack stack) {
		Triplet<Integer, Integer, ItemStack> result = grabResult(stack);
		if(result == null)
			return 0;
		return result.getY();
	}
	
	private ItemStack getOutputFromItem(ItemStack stack) {
		Triplet<Integer, Integer, ItemStack> result = grabResult(stack);
		if((result == null) || (result.getZ() == null))
			return null;
		return result.getZ().copy();
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return TileEntityMachineRadGen.maxPower;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineRadGen(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineRadGen(player.inventory, this);
	}
}
