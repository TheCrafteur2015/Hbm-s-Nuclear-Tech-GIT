package com.hbm.tileentity.machine;

import java.util.Random;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.inventory.container.ContainerAMSBase;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIAMSBase;
import com.hbm.items.ISatChip;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemCatalyst;
import com.hbm.items.special.ItemAMSCore;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.saveddata.SatelliteSavedData;
import com.hbm.saveddata.satellites.SatelliteResonator;
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

@SuppressWarnings("deprecation")
public class TileEntityAMSBase extends TileEntity implements ISidedInventory, IFluidContainer, IFluidAcceptor, IGUIProvider {

	private ItemStack slots[];

	public long power = 0;
	public static final long maxPower = 1000000000000000L;
	public int field = 0;
	public static final int maxField = 100;
	public int efficiency = 0;
	public static final int maxEfficiency = 100;
	public int heat = 0;
	public static final int maxHeat = 5000;
	public int age = 0;
	public int warning = 0;
	public int mode = 0;
	public boolean locked = false;
	public FluidTank[] tanks;
	public int color = -1;
	
	Random rand = new Random();

	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 0 };
	private static final int[] slots_side = new int[] { 0 };
	
	private String customName;
	
	public TileEntityAMSBase() {
		this.slots = new ItemStack[16];
		this.tanks = new FluidTank[4];
		this.tanks[0] = new FluidTank(Fluids.COOLANT, 8000);
		this.tanks[1] = new FluidTank(Fluids.CRYOGEL, 8000);
		this.tanks[2] = new FluidTank(Fluids.DEUTERIUM, 8000);
		this.tanks[3] = new FluidTank(Fluids.TRITIUM, 8000);
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
		return hasCustomInventoryName() ? this.customName : "container.amsBase";
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
			return player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <=128;
		}
	}
	
	@Override
	public void openInventory() {}
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		
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

		this.power = nbt.getLong("power");
		this.tanks[0].readFromNBT(nbt, "coolant1");
		this.tanks[1].readFromNBT(nbt, "coolant2");
		this.tanks[2].readFromNBT(nbt, "fuel1");
		this.tanks[3].readFromNBT(nbt, "fuel2");
		this.field = nbt.getInteger("field");
		this.efficiency = nbt.getInteger("efficiency");
		this.heat = nbt.getInteger("heat");
		this.locked = nbt.getBoolean("locked");
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
		this.tanks[0].writeToNBT(nbt, "coolant1");
		this.tanks[1].writeToNBT(nbt, "coolant2");
		this.tanks[2].writeToNBT(nbt, "fuel1");
		this.tanks[3].writeToNBT(nbt, "fuel2");
		nbt.setInteger("field", this.field);
		nbt.setInteger("efficiency", this.efficiency);
		nbt.setInteger("heat", this.heat);
		nbt.setBoolean("locked", this.locked);
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
	public int[] getAccessibleSlotsFromSide(int p_94128_1_)
    {
        return p_94128_1_ == 0 ? TileEntityAMSBase.slots_bottom : (p_94128_1_ == 1 ? TileEntityAMSBase.slots_top : TileEntityAMSBase.slots_side);
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

		if (!this.worldObj.isRemote) {
			
			for (FluidTank tank : this.tanks)
				tank.setFill(tank.getMaxFill());
			
			if(!this.locked) {
				
				this.age++;
				if(this.age >= 20)
				{
					this.age = 0;
				}

				this.tanks[0].setType(0, 1, this.slots);
				this.tanks[1].setType(2, 3, this.slots);
				this.tanks[2].setType(4, 5, this.slots);
				this.tanks[3].setType(6, 7, this.slots);
				
				for(int i = 0; i < 4; i++)
					this.tanks[i].updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
				
				int f1 = 0, f2 = 0, f3 = 0, f4 = 0;
				int booster = 0;

				if(this.worldObj.getTileEntity(this.xCoord + 6, this.yCoord, this.zCoord) instanceof TileEntityAMSLimiter) {
					TileEntityAMSLimiter te = (TileEntityAMSLimiter)this.worldObj.getTileEntity(this.xCoord + 6, this.yCoord, this.zCoord);
					if(!te.locked && this.worldObj.getBlockMetadata(this.xCoord + 6, this.yCoord, this.zCoord) == 4) {
						f1 = te.efficiency;
						if(te.mode == 2)
							booster++;
					}
				}
				if(this.worldObj.getTileEntity(this.xCoord - 6, this.yCoord, this.zCoord) instanceof TileEntityAMSLimiter) {
					TileEntityAMSLimiter te = (TileEntityAMSLimiter)this.worldObj.getTileEntity(this.xCoord - 6, this.yCoord, this.zCoord);
					if(!te.locked && this.worldObj.getBlockMetadata(this.xCoord - 6, this.yCoord, this.zCoord) == 5) {
						f2 = te.efficiency;
						if(te.mode == 2)
							booster++;
					}
				}
				if(this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord + 6) instanceof TileEntityAMSLimiter) {
					TileEntityAMSLimiter te = (TileEntityAMSLimiter)this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord + 6);
					if(!te.locked && this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord + 6) == 2) {
						f3 = te.efficiency;
						if(te.mode == 2)
							booster++;
					}
				}
				if(this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord - 6) instanceof TileEntityAMSLimiter) {
					TileEntityAMSLimiter te = (TileEntityAMSLimiter)this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord - 6);
					if(!te.locked && this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord - 6) == 3) {
						f4 = te.efficiency;
						if(te.mode == 2)
							booster++;
					}
				}
				
				this.field = Math.round(calcField(f1, f2, f3, f4));
				
				this.mode = 0;
				if(this.field > 0)
					this.mode = 1;
				if(booster > 0)
					this.mode = 2;
				
				if(this.worldObj.getTileEntity(this.xCoord, this.yCoord + 9, this.zCoord) instanceof TileEntityAMSEmitter) {
					TileEntityAMSEmitter te = (TileEntityAMSEmitter)this.worldObj.getTileEntity(this.xCoord, this.yCoord + 9, this.zCoord);
						this.efficiency = te.efficiency;
				}
				
				this.color = -1;
				
				float heatMod = 1;
				float fuelMod = 1;
				int heatBase = 0;
				int fuelBase = 0;
				
				if(this.slots[8] != null && this.slots[9] != null && this.slots[10] != null && this.slots[11] != null && this.slots[12] != null &&
						this.slots[8].getItem() instanceof ItemCatalyst && this.slots[9].getItem() instanceof ItemCatalyst &&
						this.slots[10].getItem() instanceof ItemCatalyst && this.slots[11].getItem() instanceof ItemCatalyst &&
						this.slots[12].getItem() instanceof ItemAMSCore && hasResonators() && this.efficiency > 0) {
					int a = ((ItemCatalyst)this.slots[8].getItem()).getColor();
					int b = ((ItemCatalyst)this.slots[9].getItem()).getColor();
					int c = ((ItemCatalyst)this.slots[10].getItem()).getColor();
					int d = ((ItemCatalyst)this.slots[11].getItem()).getColor();

					int e = calcAvgHex(a, b);
					int f = calcAvgHex(c, d);
					
					int g = calcAvgHex(e, f);
					
					this.color = g;

					
					for(int i = 8; i < 12; i++) {
						heatMod *= ItemCatalyst.getHeatMod(this.slots[i]);
						fuelMod *= ItemCatalyst.getFuelMod(this.slots[i]);
					}

					heatBase = ItemAMSCore.getHeatBase(this.slots[12]);
					fuelBase = ItemAMSCore.getFuelBase(this.slots[12]);
					
					heatBase *= Math.pow(1.25F, booster);
					heatBase *= (100 - this.field);
					
					if(getFuelPower(this.tanks[2].getTankType()) > 0 && getFuelPower(this.tanks[3].getTankType()) > 0 &&
							this.tanks[2].getFill() > 0 && this.tanks[3].getFill() > 0) {

						//power += (powerBase * powerMod * gauss(1, (heat - (maxHeat / 2)) / maxHeat)) / 1000 * getFuelPower(tanks[2].getTankType()) * getFuelPower(tanks[3].getTankType());
						this.heat += (heatBase * heatMod) / (this.field / 100F);
						this.tanks[2].setFill((int)(this.tanks[2].getFill() - fuelBase * fuelMod));
						this.tanks[3].setFill((int)(this.tanks[3].getFill() - fuelBase * fuelMod));
						if(this.tanks[2].getFill() <= 0)
							this.tanks[2].setFill(0);
						if(this.tanks[3].getFill() <= 0)
							this.tanks[3].setFill(0);
						
						if(this.heat > TileEntityAMSBase.maxHeat) {
							this.heat = TileEntityAMSBase.maxHeat;
						}
					}
				}
				
				if(this.power > TileEntityAMSBase.maxPower)
					this.power = TileEntityAMSBase.maxPower;
				
				
				if(this.heat > 0 && this.tanks[0].getFill() > 0 && this.tanks[1].getFill() > 0) {
					this.heat -= (getCoolingStrength(this.tanks[0].getTankType()) * getCoolingStrength(this.tanks[1].getTankType()));

					this.tanks[0].setFill(this.tanks[0].getFill() - 10);
					this.tanks[1].setFill(this.tanks[1].getFill() - 10);

					if(this.tanks[0].getFill() < 0)
						this.tanks[0].setFill(0);
					if(this.tanks[1].getFill() < 0)
						this.tanks[1].setFill(0);
					
					if(this.heat < 0)
						this.heat = 0;
				}
				
			} else {
				this.field = 0;
				this.efficiency = 0;
				this.power = 0;
				this.warning = 3;
			}

			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(this.xCoord, this.yCoord, this.zCoord, this.power), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 150));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(this.xCoord, this.yCoord, this.zCoord, this.locked ? 1 : 0, 0), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 150));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(this.xCoord, this.yCoord, this.zCoord, this.color, 1), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 150));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(this.xCoord, this.yCoord, this.zCoord, this.efficiency, 2), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 150));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(this.xCoord, this.yCoord, this.zCoord, this.field, 3), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 150));
		}
	}
	
	private int getCoolingStrength(FluidType type) {
		
		if(type == Fluids.WATER) return 5;
		if(type == Fluids.OIL) return 15;
		if(type == Fluids.COOLANT) return this.heat / 250;
		if(type == Fluids.CRYOGEL) return this.heat > this.heat/2 ? 25 : 5;
		return 0;
	}
	
	private int getFuelPower(FluidType type) {
		if(type == Fluids.DEUTERIUM) return 50;
		if(type == Fluids.TRITIUM) return 75;
		return 0;
	}
	
	private float calcField(int a, int b, int c, int d) {
		return (float)(a + b + c + d) * (a * 25 + b * 25 + c * 25 + d  * 25) / 40000;
	}
	
	private int calcAvgHex(int h1, int h2) {

		int r1 = ((h1 & 0xFF0000) >> 16);
		int g1 = ((h1 & 0x00FF00) >> 8);
		int b1 = ((h1 & 0x0000FF) >> 0);
		
		int r2 = ((h2 & 0xFF0000) >> 16);
		int g2 = ((h2 & 0x00FF00) >> 8);
		int b2 = ((h2 & 0x0000FF) >> 0);

		int r = (((r1 + r2) / 2) << 16);
		int g = (((g1 + g2) / 2) << 8);
		int b = (((b1 + b2) / 2) << 0);
		
		return r | g | b;
	}
	
	public long getPowerScaled(long i) {
		return (this.power * i) / TileEntityAMSBase.maxPower;
	}
	
	public int getEfficiencyScaled(int i) {
		return (this.efficiency * i) / TileEntityAMSBase.maxEfficiency;
	}
	
	public int getFieldScaled(int i) {
		return (this.field * i) / TileEntityAMSBase.maxField;
	}
	
	public int getHeatScaled(int i) {
		return (this.heat * i) / TileEntityAMSBase.maxHeat;
	}
	
	public boolean hasResonators() {
		
		if(this.slots[13] != null && this.slots[14] != null && this.slots[15] != null &&
				this.slots[13].getItem() == ModItems.sat_chip && this.slots[14].getItem() == ModItems.sat_chip && this.slots[15].getItem() == ModItems.sat_chip) {
			
		    SatelliteSavedData data = (SatelliteSavedData)this.worldObj.perWorldStorage.loadData(SatelliteSavedData.class, "satellites");
		    if(data == null) {
		        this.worldObj.perWorldStorage.setData("satellites", new SatelliteSavedData());
		        data = (SatelliteSavedData)this.worldObj.perWorldStorage.loadData(SatelliteSavedData.class, "satellites");
		    }
		    data.markDirty();

		    int i1 = ISatChip.getFreqS(this.slots[13]);
		    int i2 = ISatChip.getFreqS(this.slots[14]);
		    int i3 = ISatChip.getFreqS(this.slots[15]);
		    
		    if(data.getSatFromFreq(i1) != null && data.getSatFromFreq(i2) != null && data.getSatFromFreq(i3) != null &&
		    		data.getSatFromFreq(i1) instanceof SatelliteResonator && data.getSatFromFreq(i2) instanceof SatelliteResonator && data.getSatFromFreq(i3) instanceof SatelliteResonator &&
		    		i1 != i2 && i1 != i3 && i2 != i3)
		    	return true;
			
		}
		
		return true;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type.getName().equals(this.tanks[0].getTankType().getName()))
			return this.tanks[0].getMaxFill();
		else if(type.getName().equals(this.tanks[1].getTankType().getName()))
			return this.tanks[1].getMaxFill();
		else if(type.getName().equals(this.tanks[2].getTankType().getName()))
			return this.tanks[2].getMaxFill();
		else if(type.getName().equals(this.tanks[3].getTankType().getName()))
			return this.tanks[3].getMaxFill();
		else
			return 0;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type.getName().equals(this.tanks[0].getTankType().getName()))
			this.tanks[0].setFill(i);
		else if(type.getName().equals(this.tanks[1].getTankType().getName()))
			this.tanks[1].setFill(i);
		else if(type.getName().equals(this.tanks[2].getTankType().getName()))
			this.tanks[2].setFill(i);
		else if(type.getName().equals(this.tanks[3].getTankType().getName()))
			this.tanks[3].setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type.getName().equals(this.tanks[0].getTankType().getName()))
			return this.tanks[0].getFill();
		else if(type.getName().equals(this.tanks[1].getTankType().getName()))
			return this.tanks[1].getFill();
		else if(type.getName().equals(this.tanks[2].getTankType().getName()))
			return this.tanks[2].getFill();
		else if(type.getName().equals(this.tanks[3].getTankType().getName()))
			return this.tanks[3].getFill();
		else
			return 0;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if(index < 4 && this.tanks[index] != null)
			this.tanks[index].setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if(index < 4 && this.tanks[index] != null)
			this.tanks[index].setTankType(type);
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
		return new ContainerAMSBase(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIAMSBase(player.inventory, this);
	}
}
