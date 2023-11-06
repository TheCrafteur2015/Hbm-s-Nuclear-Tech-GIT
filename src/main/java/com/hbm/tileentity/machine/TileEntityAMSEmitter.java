package com.hbm.tileentity.machine;

import java.util.Random;

import com.hbm.explosion.ExplosionLarge;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.inventory.container.ContainerAMSEmitter;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIAMSEmitter;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;
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

public class TileEntityAMSEmitter extends TileEntity implements ISidedInventory, IFluidContainer, IFluidAcceptor, IGUIProvider {

	private ItemStack slots[];

	public long power = 0;
	public static final long maxPower = 100000000;
	public int efficiency = 0;
	public static final int maxEfficiency = 100;
	public int heat = 0;
	public static final int maxHeat = 2500;
	public int age = 0;
	public int warning = 0;
	public boolean locked = false;
	public FluidTank tank;
	
	Random rand = new Random();

	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 0 };
	private static final int[] slots_side = new int[] { 0 };
	
	private String customName;
	
	public TileEntityAMSEmitter() {
		this.slots = new ItemStack[4];
		this.tank = new FluidTank(Fluids.COOLANT, 16000);
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
		return hasCustomInventoryName() ? this.customName : "container.amsEmitter";
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
		this.tank.readFromNBT(nbt, "coolant");
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
		this.tank.writeToNBT(nbt, "coolant");
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
        return p_94128_1_ == 0 ? TileEntityAMSEmitter.slots_bottom : (p_94128_1_ == 1 ? TileEntityAMSEmitter.slots_top : TileEntityAMSEmitter.slots_side);
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
			
			if(!this.locked) {
				
				this.tank.setType(0, 1, this.slots);
				this.tank.updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
				
				if(this.power > 0) {
					//" - (maxHeat / 2)" offsets center to 50% instead of 0%
					this.efficiency = Math.round(calcEffect(this.power, this.heat - (TileEntityAMSEmitter.maxHeat / 2)) * 100);
					this.power -= Math.ceil(this.power * 0.025);
					this.warning = 0;
				} else {
					this.efficiency = 0;
					this.warning = 1;
				}
				
				if(this.tank.getTankType() == Fluids.CRYOGEL) {
					
					if(this.tank.getFill() >= 15) {
						if(this.heat > 0)
							this.tank.setFill(this.tank.getFill() - 15);

						if(this.heat <= TileEntityAMSEmitter.maxHeat / 2)
							if(this.efficiency > 0)
								this.heat += this.efficiency;
							else
								for(int i = 0; i < 10; i++)
									if(this.heat > 0)
										this.heat--;
						
						for(int i = 0; i < 10; i++)
							if(this.heat > TileEntityAMSEmitter.maxHeat / 2)
								this.heat--;
					} else {
						this.heat += this.efficiency;
					}
				} else if(this.tank.getTankType() == Fluids.COOLANT) {
					
					if(this.tank.getFill() >= 15) {
						if(this.heat > 0)
							this.tank.setFill(this.tank.getFill() - 15);

						if(this.heat <= TileEntityAMSEmitter.maxHeat / 4)
							if(this.efficiency > 0)
								this.heat += this.efficiency;
							else
								for(int i = 0; i < 5; i++)
									if(this.heat > 0)
										this.heat--;
						
						for(int i = 0; i < 5; i++)
							if(this.heat > TileEntityAMSEmitter.maxHeat / 4)
								this.heat--;
					} else {
						this.heat += this.efficiency;
					}
				} else if(this.tank.getTankType() == Fluids.WATER) {
					
					if(this.tank.getFill() >= 45) {
						if(this.heat > 0)
							this.tank.setFill(this.tank.getFill() - 45);

						if(this.heat <= TileEntityAMSEmitter.maxHeat * 0.85)
							if(this.efficiency > 0)
								this.heat += this.efficiency;
							else
								for(int i = 0; i < 2; i++)
									if(this.heat > 0)
										this.heat--;
						
						for(int i = 0; i < 2; i++)
							if(this.heat > TileEntityAMSEmitter.maxHeat * 0.85)
								this.heat--;
					} else {
						this.heat += this.efficiency;
					}
				} else {
					this.heat += this.efficiency;
					this.warning = 2;
				}
				
				if(this.slots[2] != null) {
					if(this.slots[2].getItem() != ModItems.ams_muzzle) {
						this.efficiency = 0;
						this.warning = 2;
					}
				} else {
					this.efficiency = 0;
					this.warning = 2;
				}
				
				if(this.tank.getFill() <= 5 || this.heat > TileEntityAMSEmitter.maxHeat * 0.9)
					this.warning = 2;
				
				if(this.heat > TileEntityAMSEmitter.maxHeat) {
					this.heat = TileEntityAMSEmitter.maxHeat;
					this.locked = true;
					ExplosionLarge.spawnBurst(this.worldObj, this.xCoord + 0.5, this.yCoord, this.zCoord + 0.5, 36, 3);
					ExplosionLarge.spawnBurst(this.worldObj, this.xCoord + 0.5, this.yCoord, this.zCoord + 0.5, 36, 2.5);
					ExplosionLarge.spawnBurst(this.worldObj, this.xCoord + 0.5, this.yCoord, this.zCoord + 0.5, 36, 2);
					ExplosionLarge.spawnBurst(this.worldObj, this.xCoord + 0.5, this.yCoord, this.zCoord + 0.5, 36, 1.5);
					ExplosionLarge.spawnBurst(this.worldObj, this.xCoord + 0.5, this.yCoord, this.zCoord + 0.5, 36, 1);
		            this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:entity.oldExplosion", 10.0F, 1);
			        this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.shutdown", 10.0F, 1.0F);
				}
	
				this.power = Library.chargeTEFromItems(this.slots, 3, this.power, TileEntityAMSEmitter.maxPower);
				
			} else {
				//fire particles n stuff
				ExplosionLarge.spawnBurst(this.worldObj, this.xCoord + 0.5, this.yCoord - 0.5, this.zCoord + 0.5, this.rand.nextInt(10), 1);
				
				this.efficiency = 0;
				this.power = 0;
				this.warning = 3;
			}

			this.tank.setTankType(Fluids.CRYOGEL);
			this.tank.setFill(this.tank.getMaxFill());

			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(this.xCoord, this.yCoord, this.zCoord, this.power), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(this.xCoord, this.yCoord, this.zCoord, this.locked ? 1 : 0, 0), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 250));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(this.xCoord, this.yCoord, this.zCoord, this.efficiency, 1), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 250));
		}
	}
	
	private float gauss(float a, float x) {
		
		//Greater values -> less difference of temperate impact
		double amplifier = 0.10;
		
		return (float) ( (1/Math.sqrt(a * Math.PI)) * Math.pow(Math.E, -1 * Math.pow(x, 2)/amplifier) );
	}
	
	private float calcEffect(float a, float x) {
		return (float) (gauss( 1 / a, x / TileEntityAMSEmitter.maxHeat) * Math.sqrt(Math.PI * 2) / (Math.sqrt(2) * Math.sqrt(TileEntityAMSEmitter.maxPower)));
	}
	
	public long getPowerScaled(long i) {
		return (this.power * i) / TileEntityAMSEmitter.maxPower;
	}
	
	public int getEfficiencyScaled(int i) {
		return (this.efficiency * i) / TileEntityAMSEmitter.maxEfficiency;
	}
	
	public int getHeatScaled(int i) {
		return (this.heat * i) / TileEntityAMSEmitter.maxHeat;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type.getName().equals(this.tank.getTankType().getName()))
			this.tank.setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type.getName().equals(this.tank.getTankType().getName()))
			return this.tank.getFill();
		else
			return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type.getName().equals(this.tank.getTankType().getName()))
			return this.tank.getMaxFill();
		else
			return 0;
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
		return new ContainerAMSEmitter(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIAMSEmitter(player.inventory, this);
	}
}
