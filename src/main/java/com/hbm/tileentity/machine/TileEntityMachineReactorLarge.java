package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.MobConfig;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.container.ContainerReactorMultiblock;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIReactorMultiblock;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFuelRod;
import com.hbm.lib.Library;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineReactorLarge extends TileEntityLoadedBase implements ISidedInventory, IFluidContainer, IFluidAcceptor, IFluidSource, IFluidStandardTransceiver, IGUIProvider {

	private ItemStack slots[];

	public int hullHeat;
	public final int maxHullHeat = 100000;
	public int coreHeat;
	public final int maxCoreHeat = 50000;
	public int rods;
	public final int rodsMax = 100;
	public int age = 0;
	public List<IFluidAcceptor> list = new ArrayList<>();
	public FluidTank[] tanks;
	public ReactorFuelType type;
	public int fuel;
	public int maxFuel = 240 * TileEntityMachineReactorLarge.fuelMult;
	public int waste;
	public int maxWaste = 240 * TileEntityMachineReactorLarge.fuelMult;

	public static int fuelMult = 1000;
	public static int cycleDuration = 24000;
	private static int fuelBase = 240 * TileEntityMachineReactorLarge.fuelMult;
	private static int waterBase = 128 * 1000;
	private static int coolantBase = 64 * 1000;
	private static int steamBase = 32 * 1000;

	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 16 };
	private static final int[] slots_side = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 16 };

	private String customName;

	public TileEntityMachineReactorLarge() {
		this.slots = new ItemStack[8];
		this.tanks = new FluidTank[3];
		this.tanks[0] = new FluidTank(Fluids.WATER, 128000, 0);
		this.tanks[1] = new FluidTank(Fluids.COOLANT, 64000, 1);
		this.tanks[2] = new FluidTank(Fluids.STEAM, 32000, 2);
		this.type = ReactorFuelType.URANIUM;
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
		return hasCustomInventoryName() ? this.customName : "container.reactorLarge";
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
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
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

		this.coreHeat = nbt.getInteger("heat");
		this.hullHeat = nbt.getInteger("hullHeat");
		this.rods = nbt.getInteger("rods");
		this.fuel = nbt.getInteger("fuel");
		this.waste = nbt.getInteger("waste");
		this.slots = new ItemStack[getSizeInventory()];
		this.tanks[0].readFromNBT(nbt, "water");
		this.tanks[1].readFromNBT(nbt, "coolant");
		this.tanks[2].readFromNBT(nbt, "steam");
		this.type = ReactorFuelType.getEnum(nbt.getInteger("type"));

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
		nbt.setInteger("heat", this.coreHeat);
		nbt.setInteger("hullHeat", this.hullHeat);
		nbt.setInteger("rods", this.rods);
		nbt.setInteger("fuel", this.fuel);
		nbt.setInteger("waste", this.waste);
		NBTTagList list = new NBTTagList();
		this.tanks[0].writeToNBT(nbt, "water");
		this.tanks[1].writeToNBT(nbt, "coolant");
		this.tanks[2].writeToNBT(nbt, "steam");
		nbt.setInteger("type", this.type.getID());

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
		return p_94128_1_ == 0 ? TileEntityMachineReactorLarge.slots_bottom : (p_94128_1_ == 1 ? TileEntityMachineReactorLarge.slots_top : TileEntityMachineReactorLarge.slots_side);
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return false;
	
	}

	public int getCoreHeatScaled(int i) {
		return (this.coreHeat * i) / this.maxCoreHeat;
	}

	public int getHullHeatScaled(int i) {
		return (this.hullHeat * i) / this.maxHullHeat;
	}

	public int getFuelScaled(int i) {
		return (this.fuel * i) / this.maxFuel;
	}

	public int getWasteScaled(int i) {
		return (this.waste * i) / this.maxWaste;
	}

	public int getSteamScaled(int i) {
		return (this.tanks[2].getFill() * i) / this.tanks[2].getMaxFill();
	}

	public boolean hasCoreHeat() {
		return this.coreHeat > 0;
	}

	public boolean hasHullHeat() {
		return this.hullHeat > 0;
	}
	
	public boolean checkBody() {
		
		return this.worldObj.getBlock(this.xCoord + 1, this.yCoord, this.zCoord + 1) == ModBlocks.reactor_element &&
				this.worldObj.getBlock(this.xCoord - 1, this.yCoord, this.zCoord + 1) == ModBlocks.reactor_element &&
				this.worldObj.getBlock(this.xCoord - 1, this.yCoord, this.zCoord - 1) == ModBlocks.reactor_element &&
				this.worldObj.getBlock(this.xCoord + 1, this.yCoord, this.zCoord - 1) == ModBlocks.reactor_element &&
				this.worldObj.getBlock(this.xCoord + 1, this.yCoord, this.zCoord) == ModBlocks.reactor_control &&
				this.worldObj.getBlock(this.xCoord - 1, this.yCoord, this.zCoord) == ModBlocks.reactor_control &&
				this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord + 1) == ModBlocks.reactor_control &&
				this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord - 1) == ModBlocks.reactor_control;
	}
	
	public boolean checkSegment(int offset) {
		
		return this.worldObj.getBlock(this.xCoord + 1, this.yCoord + offset, this.zCoord + 1) == ModBlocks.reactor_element &&
				this.worldObj.getBlock(this.xCoord - 1, this.yCoord + offset, this.zCoord + 1) == ModBlocks.reactor_element &&
				this.worldObj.getBlock(this.xCoord - 1, this.yCoord + offset, this.zCoord - 1) == ModBlocks.reactor_element &&
				this.worldObj.getBlock(this.xCoord + 1, this.yCoord + offset, this.zCoord - 1) == ModBlocks.reactor_element &&
				this.worldObj.getBlock(this.xCoord + 1, this.yCoord + offset, this.zCoord) == ModBlocks.reactor_control &&
				this.worldObj.getBlock(this.xCoord - 1, this.yCoord + offset, this.zCoord) == ModBlocks.reactor_control &&
				this.worldObj.getBlock(this.xCoord, this.yCoord + offset, this.zCoord + 1) == ModBlocks.reactor_control &&
				this.worldObj.getBlock(this.xCoord, this.yCoord + offset, this.zCoord - 1) == ModBlocks.reactor_control &&
				this.worldObj.getBlock(this.xCoord, this.yCoord + offset, this.zCoord) == ModBlocks.reactor_conductor;
	}
	
	private float checkHull() {
		
		float max = getSize() * 12;
		float count = 0;
		
		for(int y = this.yCoord - this.depth; y <= this.yCoord + this.height; y++) {

			if(blocksRad(this.xCoord - 1, y, this.zCoord + 2))
				count++;
			if(blocksRad(this.xCoord, y, this.zCoord + 2))
				count++;
			if(blocksRad(this.xCoord + 1, y, this.zCoord + 2))
				count++;

			if(blocksRad(this.xCoord - 1, y, this.zCoord - 2))
				count++;
			if(blocksRad(this.xCoord, y, this.zCoord - 2))
				count++;
			if(blocksRad(this.xCoord + 1, y, this.zCoord - 2))
				count++;

			if(blocksRad(this.xCoord + 2, y, this.zCoord - 1))
				count++;
			if(blocksRad(this.xCoord + 2, y, this.zCoord))
				count++;
			if(blocksRad(this.xCoord + 2, y, this.zCoord + 1))
				count++;
			
			if(blocksRad(this.xCoord - 2, y, this.zCoord - 1))
				count++;
			if(blocksRad(this.xCoord - 2, y, this.zCoord))
				count++;
			if(blocksRad(this.xCoord - 2, y, this.zCoord + 1))
				count++;
		}
		
		if(count == 0)
			return 1;

		//System.out.println(count + "/" + max);
		
		return 1 - (count / max);
	}
	
	private boolean blocksRad(int x, int y, int z) {
		
		Block b = this.worldObj.getBlock(x, y, z);
		
		if(b == ModBlocks.block_lead || b == ModBlocks.block_desh || b == ModBlocks.brick_concrete || (b.getExplosionResistance(null) >= 100))
			return true;
		
		return false;
	}
	
	int height;
	int depth;
	public int size;
	
	private void caluclateSize() {
		
		this.height = 0;
		this.depth = 0;
		
		for(int i = 0; i < 7; i++) {
			
			if(checkSegment(i + 1))
				this.height++;
			else
				break;
		}
		
		for(int i = 0; i < 7; i++) {
			
			if(checkSegment(-i - 1))
				this.depth++;
			else
				break;
		}
		
		this.size = this.height + this.depth + 1;
	}
	
	private int getSize() {
		return this.size;
	}
	
	private void generate() {
		
		int consumption = (int) (((double)this.maxFuel / TileEntityMachineReactorLarge.cycleDuration) * this.rods / 100);
		
		if(consumption > this.fuel)
			consumption = this.fuel;
		
		if(consumption + this.waste > this.maxWaste)
			consumption = this.maxWaste - this.waste;
		
		this.fuel -= consumption;
		this.waste += consumption;
		
		int heat = (int) (((double)consumption / this.size) * this.type.heat / TileEntityMachineReactorLarge.fuelMult);
		
		this.coreHeat += heat;
		
	}

	@Override
	public void updateEntity() {

		if (!this.worldObj.isRemote && checkBody()) {

			this.age++;
			if (this.age >= 20) {
				this.age = 0;
			}
			
			fillFluidInit(this.tanks[2].getTankType());
			
			caluclateSize();
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(this.xCoord, this.yCoord, this.zCoord, this.size, 3), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
		}

		this.tanks[0].changeTankSize(TileEntityMachineReactorLarge.waterBase * getSize());
		this.tanks[1].changeTankSize(TileEntityMachineReactorLarge.coolantBase * getSize());
		this.tanks[2].changeTankSize(TileEntityMachineReactorLarge.steamBase * getSize());
		
		this.maxWaste = this.maxFuel = TileEntityMachineReactorLarge.fuelBase * getSize();
			
		if(!this.worldObj.isRemote) {
			
			if(this.waste > this.maxWaste)
				this.waste = this.maxWaste;
			
			if(this.fuel > this.maxFuel)
				this.fuel = this.maxFuel;
			
			this.tanks[0].loadTank(0, 1, this.slots);
			this.tanks[1].loadTank(2, 3, this.slots);
			
			//Change fuel type if empty
			if(this.fuel == 0) {
				
				if(this.slots[4] != null && !TileEntityMachineReactorLarge.getFuelType(this.slots[4].getItem()).toString().equals(ReactorFuelType.UNKNOWN.toString())) {
					
					this.type = TileEntityMachineReactorLarge.getFuelType(this.slots[4].getItem());
					this.waste = 0;
					
				}
			}
			
			//Meteorite sword
			if(this.slots[4] != null && this.coreHeat > 0 && this.slots[4].getItem() == ModItems.meteorite_sword_bred)
				this.slots[4] = new ItemStack(ModItems.meteorite_sword_irradiated);
			
			//Load fuel
			if(this.slots[4] != null && TileEntityMachineReactorLarge.getFuelContent(this.slots[4], this.type) > 0) {
				
				int cont = TileEntityMachineReactorLarge.getFuelContent(this.slots[4], this.type) * TileEntityMachineReactorLarge.fuelMult;
				
				if(this.fuel + cont <= this.maxFuel) {
					
					if(!this.slots[4].getItem().hasContainerItem()) {
						
						this.slots[4].stackSize--;
						this.fuel += cont;
						
					} else if(this.slots[5] == null) {
						
						this.slots[5] = new ItemStack(this.slots[4].getItem().getContainerItem());
						this.slots[4].stackSize--;
						this.fuel += cont;
						
					} else if(this.slots[4].getItem().getContainerItem() == this.slots[5].getItem() && this.slots[5].stackSize < this.slots[5].getMaxStackSize()) {
						
						this.slots[4].stackSize--;
						this.slots[5].stackSize++;
						this.fuel += cont;
						
					}
					
					if(this.slots[4].stackSize == 0)
						this.slots[4] = null;
				}
			}
			
			//Unload waste
			if(this.slots[6] != null && TileEntityMachineReactorLarge.getWasteAbsorbed(this.slots[6].getItem(), this.type) > 0) {
				
				int absorbed = TileEntityMachineReactorLarge.getWasteAbsorbed(this.slots[6].getItem(), this.type) * TileEntityMachineReactorLarge.fuelMult;
				
				if(absorbed <= this.waste) {
					
					if(this.slots[7] == null) {

						this.waste -= absorbed;
						this.slots[7] = new ItemStack(TileEntityMachineReactorLarge.getWaste(this.slots[6].getItem(), this.type));
						this.slots[6].stackSize--;
						
					} else if(this.slots[7] != null && this.slots[7].getItem() == TileEntityMachineReactorLarge.getWaste(this.slots[6].getItem(), this.type) && this.slots[7].stackSize < this.slots[7].getMaxStackSize()) {

						this.waste -= absorbed;
						this.slots[7].stackSize++;
						this.slots[6].stackSize--;
					}
					
					if(this.slots[6].stackSize == 0)
						this.slots[6] = null;
				}
				
			}
			
			if(this.rods > 0)
				generate();

			if (this.coreHeat > 0 && this.tanks[1].getFill() > 0 && this.hullHeat < this.maxHullHeat) {
				this.hullHeat += this.coreHeat * 0.175;
				this.coreHeat -= this.coreHeat * 0.1;

				this.tanks[1].setFill(this.tanks[1].getFill() - 10);

				if (this.tanks[1].getFill() < 0)
					this.tanks[1].setFill(0);
			}

			if (this.hullHeat > this.maxHullHeat) {
				this.hullHeat = this.maxHullHeat;
			}

			if (this.hullHeat > 0 && this.tanks[0].getFill() > 0) {
				generateSteam();
				this.hullHeat -= this.hullHeat * 0.085;
			}

			if (this.coreHeat > this.maxCoreHeat) {
				explode();
			}

			if (this.rods > 0 && this.coreHeat > 0 && this.age == 5) {

				float rad = (float)this.coreHeat / (float)this.maxCoreHeat * 50F;
				rad *= checkHull();
				ChunkRadiationManager.proxy.incrementRad(this.worldObj, this.xCoord, this.yCoord, this.zCoord, rad);
			}

			for (int i = 0; i < 3; i++)
				this.tanks[i].updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);

			if(this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord - 2) == ModBlocks.reactor_ejector && this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord - 2) == 2)
				tryEjectInto(this.xCoord, this.yCoord, this.zCoord - 3);
			if(this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord + 2) == ModBlocks.reactor_ejector && this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord + 2) == 3)
				tryEjectInto(this.xCoord, this.yCoord, this.zCoord + 3);
			if(this.worldObj.getBlock(this.xCoord - 2, this.yCoord, this.zCoord) == ModBlocks.reactor_ejector && this.worldObj.getBlockMetadata(this.xCoord - 2, this.yCoord, this.zCoord) == 4)
				tryEjectInto(this.xCoord - 3, this.yCoord, this.zCoord);
			if(this.worldObj.getBlock(this.xCoord + 2, this.yCoord, this.zCoord) == ModBlocks.reactor_ejector && this.worldObj.getBlockMetadata(this.xCoord + 2, this.yCoord, this.zCoord) == 5)
				tryEjectInto(this.xCoord + 3, this.yCoord, this.zCoord);

			if(this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord - 2) == ModBlocks.reactor_inserter && this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord - 2) == 2)
				tryInsertFrom(this.xCoord, this.yCoord, this.zCoord - 3);
			if(this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord + 2) == ModBlocks.reactor_inserter && this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord + 2) == 3)
				tryInsertFrom(this.xCoord, this.yCoord, this.zCoord + 3);
			if(this.worldObj.getBlock(this.xCoord - 2, this.yCoord, this.zCoord) == ModBlocks.reactor_inserter && this.worldObj.getBlockMetadata(this.xCoord - 2, this.yCoord, this.zCoord) == 4)
				tryInsertFrom(this.xCoord - 3, this.yCoord, this.zCoord);
			if(this.worldObj.getBlock(this.xCoord + 2, this.yCoord, this.zCoord) == ModBlocks.reactor_inserter && this.worldObj.getBlockMetadata(this.xCoord + 2, this.yCoord, this.zCoord) == 5)
				tryInsertFrom(this.xCoord + 3, this.yCoord, this.zCoord);

			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(this.xCoord, this.yCoord, this.zCoord, this.rods, 0), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(this.xCoord, this.yCoord, this.zCoord, this.coreHeat, 1), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(this.xCoord, this.yCoord, this.zCoord, this.hullHeat, 2), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(this.xCoord, this.yCoord, this.zCoord, this.fuel, 4), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(this.xCoord, this.yCoord, this.zCoord, this.waste, 5), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(this.xCoord, this.yCoord, this.zCoord, this.type.getID(), 6), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
		}
	}
	
	private void tryEjectInto(int x, int y, int z) {
		
		int wSize = this.type.toString().equals(ReactorFuelType.SCHRABIDIUM.toString()) ? 60 * TileEntityMachineReactorLarge.fuelMult : 6 * TileEntityMachineReactorLarge.fuelMult;
		
		if(this.waste < wSize)
			return;
		
		TileEntity te = this.worldObj.getTileEntity(x, y, z);
		
		if(te instanceof IInventory) {
			
			IInventory chest = (IInventory)te;
			
			Item waste = ModItems.waste_uranium;
			
			switch(this.type) {
			case PLUTONIUM:
				waste = ModItems.waste_plutonium;
				break;
			case MOX:
				waste = ModItems.waste_mox;
				break;
			case SCHRABIDIUM:
				waste = ModItems.waste_schrabidium;
				break;
			case THORIUM:
				waste = ModItems.waste_thorium;
				break;
			default:
				waste = ModItems.waste_uranium;
				break;
			}
			
			for(int i = 0; i < chest.getSizeInventory(); i++) {
				
				if(chest.isItemValidForSlot(i, new ItemStack(waste, 1, 1)) && chest.getStackInSlot(i) != null && chest.getStackInSlot(i).getItem() == waste && chest.getStackInSlot(i).stackSize < chest.getStackInSlot(i).getMaxStackSize()) {
					chest.setInventorySlotContents(i, new ItemStack(waste, chest.getStackInSlot(i).stackSize + 1, 1));
					this.waste -= wSize;
					return;
				}
			}
			
			for(int i = 0; i < chest.getSizeInventory(); i++) {
				
				if(chest.isItemValidForSlot(i, new ItemStack(waste, 1, 1)) && chest.getStackInSlot(i) == null) {
					chest.setInventorySlotContents(i, new ItemStack(waste, 1, 1));
					this.waste -= wSize;
					return;
				}
			}
		}
	}
	
	private void tryInsertFrom(int x, int y, int z) {
		
		TileEntity te = this.worldObj.getTileEntity(x, y, z);
		
		if(te instanceof IInventory) {
			
			IInventory chest = (IInventory)te;
			
			if(this.fuel > 0) {
				for(int i = 0; i < chest.getSizeInventory(); i++) {
					
					if(chest.getStackInSlot(i) != null) {
						int cont = TileEntityMachineReactorLarge.getFuelContent(chest.getStackInSlot(i), this.type) * TileEntityMachineReactorLarge.fuelMult;
						
						if(cont > 0 && this.fuel + cont <= this.maxFuel) {
							
							Item container =  chest.getStackInSlot(i).getItem().getContainerItem();
							
							chest.decrStackSize(i, 1);
							this.fuel += cont;
							
							if(chest.getStackInSlot(i) == null && container != null)
								chest.setInventorySlotContents(i, new ItemStack(container));
						}
					}
				}
			} else {
				for(int i = 0; i < chest.getSizeInventory(); i++) {
					
					if(chest.getStackInSlot(i) != null) {
						int cont = TileEntityMachineReactorLarge.getFuelContent(chest.getStackInSlot(i), TileEntityMachineReactorLarge.getFuelType(chest.getStackInSlot(i).getItem())) * TileEntityMachineReactorLarge.fuelMult;
						
						if(cont > 0 && this.fuel + cont <= this.maxFuel) {
							
							Item container =  chest.getStackInSlot(i).getItem().getContainerItem();
							
							this.type = TileEntityMachineReactorLarge.getFuelType(chest.getStackInSlot(i).getItem());
							chest.decrStackSize(i, 1);
							this.fuel += cont;
							
							if(chest.getStackInSlot(i) == null && container != null)
								chest.setInventorySlotContents(i, new ItemStack(container));
						}
					}
				}
			}
		}
	}
	
	private void generateSteam() {

		//function of SHS produced per tick
		//maxes out at heat% * tank capacity / 20
		
		double statSteMaFiFiLe = 8000;
		
		double steam = (((double)this.hullHeat / (double)this.maxHullHeat) * (/*(double)tanks[2].getMaxFill()*/statSteMaFiFiLe / 50D)) * this.size;
		
		double water = steam;
		
		FluidType type = this.tanks[2].getTankType();
		if(type == Fluids.STEAM) water /= 100D;
		if(type == Fluids.HOTSTEAM) water /= 10;
		
		this.tanks[0].setFill(this.tanks[0].getFill() - (int)Math.ceil(water));
		this.tanks[2].setFill(this.tanks[2].getFill() + (int)Math.floor(steam));
		
		if(this.tanks[0].getFill() < 0)
			this.tanks[0].setFill(0);
		
		if(this.tanks[2].getFill() > this.tanks[2].getMaxFill())
			this.tanks[2].setFill(this.tanks[2].getMaxFill());
		
	}

	@SuppressWarnings("unchecked")
	private void explode() {
		for (int i = 0; i < this.slots.length; i++) {
			this.slots[i] = null;
		}

		int rad = (int)((this.fuel) * 25000L / (TileEntityMachineReactorLarge.fuelBase * 15L));
		
		ChunkRadiationManager.proxy.incrementRad(this.worldObj, this.xCoord, this.yCoord, this.zCoord, rad);

		this.worldObj.createExplosion(null, this.xCoord, this.yCoord, this.zCoord, 7.5F, true);
		ExplosionNukeGeneric.waste(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 35);
		
		for(int i = this.yCoord - this.depth; i <= this.yCoord + this.height; i++) {

			if(this.worldObj.rand.nextInt(2) == 0) {
				randomizeRadBlock(this.xCoord + 1, i, this.zCoord + 1);
			}
			if(this.worldObj.rand.nextInt(2) == 0) {
				randomizeRadBlock(this.xCoord + 1, i, this.zCoord - 1);
			}
			if(this.worldObj.rand.nextInt(2) == 0) {
				randomizeRadBlock(this.xCoord - 1, i, this.zCoord - 1);
			}
			if(this.worldObj.rand.nextInt(2) == 0) {
				randomizeRadBlock(this.xCoord - 1, i, this.zCoord + 1);
			}
			
			if(this.worldObj.rand.nextInt(5) == 0) {
				this.worldObj.createExplosion(null, this.xCoord, this.yCoord, this.zCoord, 5.0F, true);
			}
		}
		
		this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, ModBlocks.sellafield, 5, 3);
		
		if(MobConfig.enableElementals) {
			List<EntityPlayer> players = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5).expand(100, 100, 100));
			
			for(EntityPlayer player : players) {
				player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).setBoolean("radMark", true);
			}
		}
	}
	
	private void randomizeRadBlock(int x, int y, int z) {
		
		int rand = this.worldObj.rand.nextInt(20);
		
		if(rand < 7)
			this.worldObj.setBlock(x, y, z, ModBlocks.toxic_block);
		else if(rand < 10)
			this.worldObj.setBlock(x, y, z, ModBlocks.sellafield, 0, 3);
		else if(rand < 14)
			this.worldObj.setBlock(x, y, z, ModBlocks.sellafield, 1, 3);
		else if(rand < 17)
			this.worldObj.setBlock(x, y, z, ModBlocks.sellafield, 2, 3);
		else if(rand < 19)
			this.worldObj.setBlock(x, y, z, ModBlocks.sellafield, 3, 3);
		else
			this.worldObj.setBlock(x, y, z, ModBlocks.sellafield, 4, 3);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, this.worldObj, type);
	}

	@Override
	public void fillFluidInit(FluidType type) {
		
		for(ForgeDirection dir : new ForgeDirection[] {Library.POS_X, Library.NEG_X, Library.POS_Z, Library.NEG_Z}) {
			
			if(this.worldObj.getBlock(this.xCoord + dir.offsetX * 2, this.yCoord, this.zCoord + dir.offsetZ * 2) == ModBlocks.reactor_hatch) {
				fillFluid(this.xCoord + dir.offsetX * 3, this.yCoord, this.zCoord + dir.offsetZ * 3, getTact(), type);
				for(int i = 0; i < 2; i++) trySubscribe(this.tanks[i].getTankType(), this.worldObj, this.xCoord + dir.offsetX * 3, this.yCoord, this.zCoord + dir.offsetZ * 3, Library.NEG_X);
				this.sendFluid(this.tanks[2], this.worldObj, this.xCoord + dir.offsetX * 3, this.yCoord, this.zCoord + dir.offsetZ * 3, Library.NEG_X);
			} else {
				for(int i = 0; i < 2; i++) tryUnsubscribe(this.tanks[i].getTankType(), this.worldObj, this.xCoord + dir.offsetX * 3, this.yCoord, this.zCoord + dir.offsetZ * 3);
			}
		}

		fillFluid(this.xCoord, this.yCoord + this.height + 1, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord - this.depth - 1, this.zCoord, getTact(), type);
		
		this.sendFluid(this.tanks[2], this.worldObj, this.xCoord, this.yCoord + this.height + 1, this.zCoord, Library.POS_Y);
		this.sendFluid(this.tanks[2], this.worldObj, this.xCoord, this.yCoord - this.depth - 1, this.zCoord, Library.NEG_Y);
	}

	@Override
	public boolean getTact() {
		return this.worldObj.getTotalWorldTime() % 2 == 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if (type.name().equals(this.tanks[0].getTankType().name()))
			return this.tanks[0].getMaxFill();
		else if (type.name().equals(this.tanks[1].getTankType().name()))
			return this.tanks[1].getMaxFill();
		else
			return 0;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if (type.name().equals(this.tanks[0].getTankType().name()))
			this.tanks[0].setFill(i);
		else if (type.name().equals(this.tanks[1].getTankType().name()))
			this.tanks[1].setFill(i);
		else if (type.name().equals(this.tanks[2].getTankType().name()))
			this.tanks[2].setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if (type.name().equals(this.tanks[0].getTankType().name()))
			return this.tanks[0].getFill();
		else if (type.name().equals(this.tanks[1].getTankType().name()))
			return this.tanks[1].getFill();
		else if (type.name().equals(this.tanks[2].getTankType().name()))
			return this.tanks[2].getFill();
		else
			return 0;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if (index < 3 && this.tanks[index] != null)
			this.tanks[index].setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if (index < 3 && this.tanks[index] != null)
			this.tanks[index].setTankType(type);
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return this.list;
	}

	@Override
	public void clearFluidList(FluidType type) {
		this.list.clear();
	}
	
	public enum ReactorFuelType {

		URANIUM(250000),
		THORIUM(200000),
		PLUTONIUM(312500),
		MOX(250000),
		SCHRABIDIUM(2085000),
		UNKNOWN(1);
		
		private ReactorFuelType(int i) {
			this.heat = i;
		}
		
		//Heat per nugget burned
		private int heat;
		
		public int getHeat() {
			return this.heat;
		}
		
		public int getID() {
			return Arrays.asList(ReactorFuelType.values()).indexOf(this);
		}
		
		public static ReactorFuelType getEnum(int i) {
			if(i < ReactorFuelType.values().length)
				return ReactorFuelType.values()[i];
			else
				return ReactorFuelType.URANIUM;
		}
	}
	
	static class ReactorFuelEntry {
		
		int value;
		ReactorFuelType type;
		Item item;
		
		public ReactorFuelEntry(int value, ReactorFuelType type, Item item) {
			this.value = value;
			this.type = type;
			this.item = item;
		}
	}
	
	static class ReactorWasteEntry {
		
		int value;
		ReactorFuelType type;
		Item in;
		Item out;
		
		public ReactorWasteEntry(int value, ReactorFuelType type, Item in, Item out) {
			this.value = value;
			this.type = type;
			this.in = in;
			this.out = out;
		}
	}

	//TODO: turn this steaming hot garbage into hashmaps
	static List<ReactorFuelEntry> fuels = new ArrayList<>();
	static List<ReactorWasteEntry> wastes = new ArrayList<>();
	
	public static void registerAll() {

		TileEntityMachineReactorLarge.registerFuelEntry(1, ReactorFuelType.URANIUM, ModItems.nugget_uranium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(9, ReactorFuelType.URANIUM, ModItems.ingot_uranium_fuel);

		TileEntityMachineReactorLarge.registerFuelEntry(1, ReactorFuelType.PLUTONIUM, ModItems.nugget_plutonium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(9, ReactorFuelType.PLUTONIUM, ModItems.ingot_plutonium_fuel);
		
		TileEntityMachineReactorLarge.registerFuelEntry(1, ReactorFuelType.MOX, ModItems.nugget_mox_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(9, ReactorFuelType.MOX, ModItems.ingot_mox_fuel);

		TileEntityMachineReactorLarge.registerFuelEntry(10, ReactorFuelType.SCHRABIDIUM, ModItems.nugget_schrabidium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(90, ReactorFuelType.SCHRABIDIUM, ModItems.ingot_schrabidium_fuel);

		TileEntityMachineReactorLarge.registerFuelEntry(1, ReactorFuelType.THORIUM, ModItems.nugget_thorium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(9, ReactorFuelType.THORIUM, ModItems.ingot_thorium_fuel);
	}
	
	public static void registerFuelEntry(int nuggets, ReactorFuelType type, Item fuel) {
		
		TileEntityMachineReactorLarge.fuels.add(new ReactorFuelEntry(nuggets, type, fuel));
	}
	
	public static void registerWasteEntry(int nuggets, ReactorFuelType type, Item in, Item out) {
		
		TileEntityMachineReactorLarge.wastes.add(new ReactorWasteEntry(nuggets, type, in, out));
	}
	
	public static int getFuelContent(ItemStack item, ReactorFuelType type) {
		
		if(item == null)
			return 0;
		
		for(ReactorFuelEntry ent : TileEntityMachineReactorLarge.fuels) {
			if(ent.item == item.getItem() && type.toString().equals(ent.type.toString())) {
				
				int value = ent.value;
				
				//if it's a fuel rod that has been used up, multiply by damage and floor it
				if(item.getItem() instanceof ItemFuelRod) {
					
					double mult = 1D - ((double)ItemFuelRod.getLifeTime(item) / (double)((ItemFuelRod)item.getItem()).lifeTime);
					return (int)Math.floor(mult * value);
				}
				
				return value;
			}
		}
			
		return 0;
	}
	
	public static ReactorFuelType getFuelType(Item item) {
		
		for(ReactorFuelEntry ent : TileEntityMachineReactorLarge.fuels) {
			if(ent.item == item)
				return ent.type;
		}
			
		return ReactorFuelType.UNKNOWN;
	}
	
	public static Item getWaste(Item item, ReactorFuelType type) {
		
		for(ReactorWasteEntry ent : TileEntityMachineReactorLarge.wastes) {
			if(ent.in == item && type.toString().equals(ent.type.toString()))
				return ent.out;
		}
			
		return null;
	}
	
	public static int getWasteAbsorbed(Item item, ReactorFuelType type) {
		
		for(ReactorWasteEntry ent : TileEntityMachineReactorLarge.wastes) {
			if(ent.in == item && type.toString().equals(ent.type.toString()))
				return ent.value;
		}
			
		return 0;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return this.tanks;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {this.tanks[2]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {this.tanks[0], this.tanks[1]};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerReactorMultiblock(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIReactorMultiblock(player.inventory, this);
	}
}
