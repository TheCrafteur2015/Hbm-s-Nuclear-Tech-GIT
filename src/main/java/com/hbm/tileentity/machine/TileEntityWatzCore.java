package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.IFluidSource;
import com.hbm.interfaces.IReactor;
import com.hbm.inventory.container.ContainerWatzCore;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIWatzCore;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFuelRod;
import com.hbm.items.special.WatzFuel;
import com.hbm.items.tool.ItemTitaniumFilter;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IEnergyGenerator;
import api.hbm.fluid.IFluidStandardSender;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Deprecated
public class TileEntityWatzCore extends TileEntityLoadedBase implements ISidedInventory, IReactor, IEnergyGenerator, IFluidContainer, IFluidSource, IFluidStandardSender, IGUIProvider {

	public long power;
	public final static long maxPower = 100000000;
	public int heat;
	
	public int heatMultiplier;
	public int powerMultiplier;
	public int decayMultiplier;
	
	public int heatList;
	public int wasteList;
	public int powerList;
	
	Random rand = new Random();
	
	private ItemStack slots[];
	public int age = 0;
	public List<IFluidAcceptor> list1 = new ArrayList<>();
	public FluidTank tank;
	
	private String customName;

	public TileEntityWatzCore() {
		this.slots = new ItemStack[40];
		this.tank = new FluidTank(Fluids.WATZ, 64000, 0);
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
		return hasCustomInventoryName() ? this.customName : "container.watzPowerplant";
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
			return true;
		}
	}
	
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
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return null;
	}

	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
		return false;
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
		return false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);

		this.power = nbt.getLong("power");
		this.tank.readFromNBT(nbt, "watz");
		
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
		
		nbt.setLong("power", this.power);
		this.tank.writeToNBT(nbt, "watz");
		
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
	public boolean isStructureValid(World world) {
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 3, this.yCoord + i, this.zCoord - 1) != ModBlocks.reinforced_brick)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 3, this.yCoord + i, this.zCoord + 1) != ModBlocks.reinforced_brick)
				return false;
		}
		
		
		
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 2, this.yCoord + i, this.zCoord - 2) != ModBlocks.reinforced_brick)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 2, this.yCoord + i, this.zCoord - 1) != ModBlocks.watz_element)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 2, this.yCoord + i, this.zCoord) != ModBlocks.watz_control)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 2, this.yCoord + i, this.zCoord + 1) != ModBlocks.watz_element)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 2, this.yCoord + i, this.zCoord + 2) != ModBlocks.reinforced_brick)
				return false;
		}
		
		
		
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 1, this.yCoord + i, this.zCoord - 3) != ModBlocks.reinforced_brick)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 1, this.yCoord + i, this.zCoord - 2) != ModBlocks.watz_element)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 1, this.yCoord + i, this.zCoord - 1) != ModBlocks.watz_control)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 1, this.yCoord + i, this.zCoord) != ModBlocks.watz_cooler)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 1, this.yCoord + i, this.zCoord + 1) != ModBlocks.watz_control)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 1, this.yCoord + i, this.zCoord + 2) != ModBlocks.watz_element)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 1, this.yCoord + i, this.zCoord + 3) != ModBlocks.reinforced_brick)
				return false;
		}
		
		
		
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 0, this.yCoord + i, this.zCoord - 2) != ModBlocks.watz_control)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 0, this.yCoord + i, this.zCoord - 1) != ModBlocks.watz_cooler)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 0, this.yCoord + i, this.zCoord + 1) != ModBlocks.watz_cooler)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 0, this.yCoord + i, this.zCoord + 2) != ModBlocks.watz_control)
				return false;
		}
		
		
		
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 1, this.yCoord + i, this.zCoord - 3) != ModBlocks.reinforced_brick)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 1, this.yCoord + i, this.zCoord - 2) != ModBlocks.watz_element)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 1, this.yCoord + i, this.zCoord - 1) != ModBlocks.watz_control)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 1, this.yCoord + i, this.zCoord) != ModBlocks.watz_cooler)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 1, this.yCoord + i, this.zCoord + 1) != ModBlocks.watz_control)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 1, this.yCoord + i, this.zCoord + 2) != ModBlocks.watz_element)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 1, this.yCoord + i, this.zCoord + 3) != ModBlocks.reinforced_brick)
				return false;
		}
		
		
		
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 2, this.yCoord + i, this.zCoord - 2) != ModBlocks.reinforced_brick)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 2, this.yCoord + i, this.zCoord - 1) != ModBlocks.watz_element)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 2, this.yCoord + i, this.zCoord) != ModBlocks.watz_control)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 2, this.yCoord + i, this.zCoord + 1) != ModBlocks.watz_element)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 2, this.yCoord + i, this.zCoord + 2) != ModBlocks.reinforced_brick)
				return false;
		}
		
		
		
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 3, this.yCoord + i, this.zCoord - 1) != ModBlocks.reinforced_brick)
				return false;
		}
		for(int i = -5; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 3, this.yCoord + i, this.zCoord + 1) != ModBlocks.reinforced_brick)
				return false;
		}
		
		

		for(int i = -5; i <= -1; i++)
		{
			if(world.getBlock(this.xCoord, this.yCoord + i, this.zCoord) != ModBlocks.watz_conductor)
				return false;
		}
		for(int i = 1; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord, this.yCoord + i, this.zCoord) != ModBlocks.watz_conductor)
				return false;
		}

		for(int i = -5; i <= -1; i++)
		{
			if(world.getBlock(this.xCoord + 3, this.yCoord + i, this.zCoord) != ModBlocks.reinforced_brick)
				return false;
		}
		for(int i = 1; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord + 3, this.yCoord + i, this.zCoord) != ModBlocks.reinforced_brick)
				return false;
		}

		for(int i = -5; i <= -1; i++)
		{
			if(world.getBlock(this.xCoord - 3, this.yCoord + i, this.zCoord) != ModBlocks.reinforced_brick)
				return false;
		}
		for(int i = 1; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord - 3, this.yCoord + i, this.zCoord) != ModBlocks.reinforced_brick)
				return false;
		}

		for(int i = -5; i <= -1; i++)
		{
			if(world.getBlock(this.xCoord, this.yCoord + i, this.zCoord + 3) != ModBlocks.reinforced_brick)
				return false;
		}
		for(int i = 1; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord, this.yCoord + i, this.zCoord + 3) != ModBlocks.reinforced_brick)
				return false;
		}

		for(int i = -5; i <= -1; i++)
		{
			if(world.getBlock(this.xCoord, this.yCoord + i, this.zCoord - 3) != ModBlocks.reinforced_brick)
				return false;
		}
		for(int i = 1; i <= 5; i++)
		{
			if(world.getBlock(this.xCoord, this.yCoord + i, this.zCoord - 3) != ModBlocks.reinforced_brick)
				return false;
		}

		if((world.getBlock(this.xCoord + 3, this.yCoord, this.zCoord) != ModBlocks.watz_hatch) || (world.getBlock(this.xCoord - 3, this.yCoord, this.zCoord) != ModBlocks.watz_hatch) || (world.getBlock(this.xCoord, this.yCoord, this.zCoord + 3) != ModBlocks.watz_hatch) || (world.getBlock(this.xCoord, this.yCoord, this.zCoord - 3) != ModBlocks.watz_hatch))
			return false;

		for(int i = -3; i <= 3; i++)
		{
			for(int j = -3; j <= 3; j++)
			{
				if(world.getBlock(this.xCoord + i, this.yCoord + 6, this.zCoord + j) != ModBlocks.watz_end && world.getBlock(this.xCoord + i, this.yCoord + 6, this.zCoord + j) != ModBlocks.watz_conductor)
					return false;
			}
		}
		for(int i = -3; i <= 3; i++)
		{
			for(int j = -3; j <= 3; j++)
			{
				if(world.getBlock(this.xCoord + i, this.yCoord - 6, this.zCoord + j) != ModBlocks.watz_end && world.getBlock(this.xCoord + i, this.yCoord - 6, this.zCoord + j) != ModBlocks.watz_conductor)
					return false;
			}
		}
		
		return true;
	}

	@Override
	public boolean isCoatingValid(World world) {
		{
			return true;
		}
		
		//return false;
	}

	@Override
	public boolean hasFuse() {
		return this.slots[38] != null && this.slots[38].getItem() == ModItems.titanium_filter && ItemTitaniumFilter.getDura(this.slots[38]) > 0;
	}
	
	@Override
	public int getWaterScaled(int i) {
		return 0;
	}
	
	@Override
	public long getPowerScaled(long i) {
		return (this.power/100 * i) / (TileEntityWatzCore.maxPower/100);
	}
	
	@Override
	public int getCoolantScaled(int i) {
		return 0;
	}
	
	@Override
	public int getHeatScaled(int i) {
		return 0;
	}

	@Override
	public void updateEntity() {
		
		if(isStructureValid(this.worldObj)) {

			this.powerMultiplier = 100;
			this.heatMultiplier = 100;
			this.decayMultiplier = 100;
			this.powerList = 0;
			this.heatList = 0;
			this.heat = 0;

			if (hasFuse()) {
				
				//Adds power and heat
				for (int i = 0; i < 36; i++) {
					surveyPellet(this.slots[i]);
				}
				//Calculates modifiers
				for (int i = 0; i < 36; i++) {
					surveyPelletAgain(this.slots[i]);
				}
				//Decays pellet by (DECAYMULTIPLIER * DEFAULTDECAY=100)/100 ticks
				for (int i = 0; i < 36; i++) {
					decayPellet(i);
				}
			}
			
			if(!this.worldObj.isRemote) {
	
				this.age++;
				if (this.age >= 20) {
					this.age = 0;
				}

				sendPower(this.worldObj, this.xCoord, this.yCoord + 7, this.zCoord, ForgeDirection.UP);
				sendPower(this.worldObj, this.xCoord, this.yCoord - 7, this.zCoord, ForgeDirection.DOWN);

				this.sendFluid(this.tank, this.worldObj, this.xCoord + 4, this.yCoord, this.zCoord, Library.POS_X);
				this.sendFluid(this.tank, this.worldObj, this.xCoord, this.yCoord, this.zCoord + 4, Library.POS_Z);
				this.sendFluid(this.tank, this.worldObj, this.xCoord - 4, this.yCoord, this.zCoord, Library.NEG_X);
				this.sendFluid(this.tank, this.worldObj, this.xCoord, this.yCoord, this.zCoord - 4, Library.NEG_Z);
	
				if (this.age == 9 || this.age == 19) {
					fillFluidInit(this.tank.getTankType());
				}
	
				//Only damages filter when heat is present (thus waste being created)
				if (this.heatList > 0) {
					ItemTitaniumFilter.setDura(this.slots[38], ItemTitaniumFilter.getDura(this.slots[38]) - 1);
				}
	
				this.heatList *= this.heatMultiplier;
				this.heatList /= 100;
				this.heat = this.heatList;
	
				this.powerList *= this.powerMultiplier;
				this.powerList /= 100;
				this.power += this.powerList;
	
				this.tank.setFill(this.tank.getFill() + ((this.decayMultiplier * this.heat) / 100) / 100);
				
				if(this.power > TileEntityWatzCore.maxPower)
					this.power = TileEntityWatzCore.maxPower;
				
				//Gets rid of 1/4 of the total waste, if at least one access hatch is not occupied
				if(this.tank.getFill() > this.tank.getMaxFill())
					emptyWaste();
				
				this.power = Library.chargeItemsFromTE(this.slots, 37, this.power, TileEntityWatzCore.maxPower);
				
				this.tank.updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
				this.tank.unloadTank(36, 39, this.slots);
	
				PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(this.xCoord, this.yCoord, this.zCoord, this.power), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
			}
		}
	}
	
	public void surveyPellet(ItemStack stack) {
		if(stack != null && stack.getItem() instanceof WatzFuel)
		{
			WatzFuel fuel = (WatzFuel)stack.getItem();
			this.powerList += fuel.power;
			this.heatList += fuel.heat;
		}
	}
	
	public void surveyPelletAgain(ItemStack stack) {
		if(stack != null && stack.getItem() instanceof WatzFuel)
		{
			WatzFuel fuel = (WatzFuel)stack.getItem();
			this.powerMultiplier *= fuel.powerMultiplier;
			this.heatMultiplier *= fuel.heatMultiplier;
			this.decayMultiplier *= fuel.decayMultiplier;
		}
	}
	
	public void decayPellet(int i) {
		if(this.slots[i] != null && this.slots[i].getItem() instanceof WatzFuel)
		{
			WatzFuel fuel = (WatzFuel)this.slots[i].getItem();
			ItemFuelRod.setLifeTime(this.slots[i], ItemFuelRod.getLifeTime(this.slots[i]) + this.decayMultiplier);
			WatzFuel.updateDamage(this.slots[i]);
			if(ItemFuelRod.getLifeTime(this.slots[i]) >= fuel.lifeTime)
			{
				if(this.slots[i].getItem() == ModItems.pellet_lead)
					this.slots[i] = new ItemStack(ModItems.powder_lead);
				else
					this.slots[i] = new ItemStack(ModItems.pellet_lead);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void emptyWaste() {
		this.tank.setFill(this.tank.getFill() / 4);
		this.tank.setFill(this.tank.getFill() * 3);
		if (!this.worldObj.isRemote) {
			if (this.worldObj.getBlock(this.xCoord + 4, this.yCoord, this.zCoord) == Blocks.air)
			{
				this.worldObj.setBlock(this.xCoord + 4, this.yCoord, this.zCoord, ModBlocks.mud_block);
				this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "game.neutral.swim.splash", 3.0F, 0.5F);
			}
			else if (this.worldObj.getBlock(this.xCoord - 4, this.yCoord, this.zCoord) == Blocks.air)
			{
				this.worldObj.setBlock(this.xCoord - 4, this.yCoord, this.zCoord, ModBlocks.mud_block);
				this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "game.neutral.swim.splash", 3.0F, 0.5F);
			}
			else if (this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord + 4) == Blocks.air)
			{
				this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord + 4, ModBlocks.mud_block);
				this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "game.neutral.swim.splash", 3.0F, 0.5F);
			}
			else if (this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord - 4) == Blocks.air)
			{
				this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord - 4, ModBlocks.mud_block);
				this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "game.neutral.swim.splash", 3.0F, 0.5F);
			}
			else {
				List<EntityPlayer> players = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class,
						AxisAlignedBB.getBoundingBox(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5).expand(50, 50, 50));
				
				for(EntityPlayer player : players) {
					player.triggerAchievement(MainRegistry.achWatzBoom);
				}
				
				if (this.rand.nextInt(10) != 0) {
					for (int i = -3; i <= 3; i++)
						for (int j = -5; j <= 5; j++)
							for (int k = -3; k <= 3; k++)
								if (this.rand.nextInt(2) == 0)
									this.worldObj.setBlock(this.xCoord + i, this.yCoord + j, this.zCoord + k,
											ModBlocks.mud_block);
					this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, ModBlocks.mud_block);
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "game.neutral.swim.splash", 3.0F, 0.5F);
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "random.explode", 3.0F, 0.75F);
				} else {
					EntityNukeExplosionMK3 ex = EntityNukeExplosionMK3.statFacFleija(this.worldObj, this.xCoord, this.yCoord, this.zCoord, BombConfig.fleijaRadius);
					if(!ex.isDead) {
						this.worldObj.spawnEntityInWorld(ex);
						EntityCloudFleija cloud = new EntityCloudFleija(this.worldObj, BombConfig.fleijaRadius);
						cloud.posX = this.xCoord + 0.5;
						cloud.posY = this.yCoord + 0.5;
						cloud.posZ = this.zCoord + 0.6;
						this.worldObj.spawnEntityInWorld(cloud);
					}
				}
			}
		}
	}
	
	@Override
	public boolean getTact() {
		if(this.age >= 0 && this.age < 10)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getMaxPower() {
		return TileEntityWatzCore.maxPower;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		this.tank.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		this.tank.setTankType(type);
	}
	
	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(this.xCoord + 4, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord - 4, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord + 4, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord - 4, getTact(), type);
		
	}
	
	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, this.worldObj, type);
	}
	
	@Override
	public int getFluidFill(FluidType type) {
		return this.tank.getFill();
	}
	
	@Override
	public void setFluidFill(int i, FluidType type) {
		this.tank.setFill(i);
	}
	
	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return this.list1;
	}
	
	@Override
	public void clearFluidList(FluidType type) {
		this.list1.clear();
	}
	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { this.tank };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { this.tank };
	}
	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerWatzCore(player.inventory, this);
	}
	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIWatzCore(player.inventory, this);
	}
}
