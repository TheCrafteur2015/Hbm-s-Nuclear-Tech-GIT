package com.hbm.tileentity.bomb;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.LaunchPad;
import com.hbm.entity.missile.EntityMissileCustom;
import com.hbm.handler.MissileStruct;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.inventory.container.ContainerLaunchTable;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineLaunchTable;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemCustomMissile;
import com.hbm.items.weapon.ItemMissile;
import com.hbm.items.weapon.ItemMissile.FuelType;
import com.hbm.items.weapon.ItemMissile.PartSize;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEMissileMultipartPacket;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardReceiver;
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
public class TileEntityLaunchTable extends TileEntityLoadedBase implements ISidedInventory, IEnergyUser, IFluidContainer, IFluidAcceptor, IFluidStandardReceiver, IGUIProvider, SimpleComponent {

	private ItemStack slots[];

	public long power;
	public static final long maxPower = 100000;
	public int solid;
	public static final int maxSolid = 100000;
	public FluidTank[] tanks;
	public PartSize padSize;
	public int height;
	
	public MissileStruct load;

	private static final int[] access = new int[] { 0 };

	private String customName;

	public TileEntityLaunchTable() {
		this.slots = new ItemStack[8];
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.NONE, 100000);
		this.tanks[1] = new FluidTank(Fluids.NONE, 100000);
		this.padSize = PartSize.SIZE_10;
		this.height = 10;
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
		return hasCustomInventoryName() ? this.customName : "container.launchTable";
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
	
	public long getPowerScaled(long i) {
		return (this.power * i) / TileEntityLaunchTable.maxPower;
	}
	
	public int getSolidScaled(int i) {
		return (this.solid * i) / TileEntityLaunchTable.maxSolid;
	}

	@Override
	public void updateEntity() {

		if (!this.worldObj.isRemote) {
			
			updateTypes();
			
			if(this.worldObj.getTotalWorldTime() % 20 == 0)
				updateConnections();

			this.tanks[0].loadTank(2, 6, this.slots);
			this.tanks[1].loadTank(3, 7, this.slots);

			for (int i = 0; i < 2; i++)
				this.tanks[i].updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
			
			this.power = Library.chargeTEFromItems(this.slots, 5, this.power, TileEntityLaunchTable.maxPower);
			
			if(this.slots[4] != null && this.slots[4].getItem() == ModItems.rocket_fuel && this.solid + 250 <= TileEntityLaunchTable.maxSolid) {
				
				decrStackSize(4, 1);
				this.solid += 250;
			}
			
			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(this.xCoord, this.yCoord, this.zCoord, this.power), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(this.xCoord, this.yCoord, this.zCoord, this.solid, 0), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(this.xCoord, this.yCoord, this.zCoord, this.padSize.ordinal(), 1), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 250));
			
			MissileStruct multipart = TileEntityLaunchTable.getStruct(this.slots[0]);
			
			if(multipart != null)
				PacketDispatcher.wrapper.sendToAllAround(new TEMissileMultipartPacket(this.xCoord, this.yCoord, this.zCoord, multipart), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 250));
			else
				PacketDispatcher.wrapper.sendToAllAround(new TEMissileMultipartPacket(this.xCoord, this.yCoord, this.zCoord, new MissileStruct()), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 250));

			outer:
			for(int x = -4; x <= 4; x++) {
				for(int z = -4; z <= 4; z++) {
					
					if(this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord + x, this.yCoord, this.zCoord + z) && canLaunch()) {
						launch();
						break outer;
					}
				}
			}
		} else {
			
			List<EntityMissileCustom> entities = this.worldObj.getEntitiesWithinAABB(EntityMissileCustom.class, AxisAlignedBB.getBoundingBox(this.xCoord - 0.5, this.yCoord, this.zCoord - 0.5, this.xCoord + 1.5, this.yCoord + 10, this.zCoord + 1.5));
			
			if(!entities.isEmpty()) {
				for(int i = 0; i < 15; i++) {

					boolean dir = this.worldObj.rand.nextBoolean();
					float moX = (float) (dir ? 0 : this.worldObj.rand.nextGaussian() * 0.65F);
					float moZ = (float) (!dir ? 0 : this.worldObj.rand.nextGaussian() * 0.65F);
					
					MainRegistry.proxy.spawnParticle(this.xCoord + 0.5, this.yCoord + 0.25, this.zCoord + 0.5, "launchsmoke", new float[] {moX, 0, moZ});
				}
			}
		}
	}
	
	private void updateConnections() {

		for(int i = -4; i <= 4; i++) {
			this.trySubscribe(this.worldObj, this.xCoord + i, this.yCoord, this.zCoord + 5, Library.POS_Z);
			this.trySubscribe(this.worldObj, this.xCoord + i, this.yCoord, this.zCoord - 5, Library.NEG_Z);
			this.trySubscribe(this.worldObj, this.xCoord + 5, this.yCoord, this.zCoord + i, Library.POS_X);
			this.trySubscribe(this.worldObj, this.xCoord - 5, this.yCoord, this.zCoord + i, Library.NEG_X);
			
			for(int j = 0; j < 2; j++) {
				this.trySubscribe(this.tanks[j].getTankType(), this.worldObj, this.xCoord + i, this.yCoord, this.zCoord + 5, Library.POS_Z);
				this.trySubscribe(this.tanks[j].getTankType(), this.worldObj, this.xCoord + i, this.yCoord, this.zCoord - 5, Library.NEG_Z);
				this.trySubscribe(this.tanks[j].getTankType(), this.worldObj, this.xCoord + 5, this.yCoord, this.zCoord + i, Library.POS_X);
				this.trySubscribe(this.tanks[j].getTankType(), this.worldObj, this.xCoord - 5, this.yCoord, this.zCoord + i, Library.NEG_X);
			}
		}
	}
	
	public boolean canLaunch() {
		
		if(this.power >= TileEntityLaunchTable.maxPower * 0.75 && isMissileValid() && hasDesignator() && hasFuel())
			return true;
		
		return false;
	}
	
	public void launch() {

		this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:weapon.missileTakeOff", 10.0F, 1.0F);

		int tX = this.slots[1].stackTagCompound.getInteger("xCoord");
		int tZ = this.slots[1].stackTagCompound.getInteger("zCoord");
		
		EntityMissileCustom missile = new EntityMissileCustom(this.worldObj, this.xCoord + 0.5F, this.yCoord + 2.5F, this.zCoord + 0.5F, tX, tZ, TileEntityLaunchTable.getStruct(this.slots[0]));
		this.worldObj.spawnEntityInWorld(missile);
		
		subtractFuel();
		
		this.slots[0] = null;
	}
	
	private boolean hasFuel() {

		return solidState() != 0 && liquidState() != 0 && oxidizerState() != 0;
	}
	
	private void subtractFuel() {
		
		MissileStruct multipart = TileEntityLaunchTable.getStruct(this.slots[0]);
		
		if(multipart == null || multipart.fuselage == null)
			return;
		
		ItemMissile fuselage = multipart.fuselage;
		
		float f = (Float)fuselage.attributes[1];
		int fuel = (int)f;
		
		switch((FuelType)fuselage.attributes[0]) {
			case KEROSENE:
				this.tanks[0].setFill(this.tanks[0].getFill() - fuel);
				this.tanks[1].setFill(this.tanks[1].getFill() - fuel);
				break;
			case HYDROGEN:
				this.tanks[0].setFill(this.tanks[0].getFill() - fuel);
				this.tanks[1].setFill(this.tanks[1].getFill() - fuel);
				break;
			case XENON:
				this.tanks[0].setFill(this.tanks[0].getFill() - fuel);
				break;
			case BALEFIRE:
				this.tanks[0].setFill(this.tanks[0].getFill() - fuel);
				this.tanks[1].setFill(this.tanks[1].getFill() - fuel);
				break;
			case SOLID:
				this.solid -= fuel; break;
			default: break;
		}
		
		this.power -= TileEntityLaunchTable.maxPower * 0.75;
	}
	
	public static MissileStruct getStruct(ItemStack stack) {
		
		return ItemCustomMissile.getStruct(stack);
	}
	
	public boolean isMissileValid() {
		
		MissileStruct multipart = TileEntityLaunchTable.getStruct(this.slots[0]);
		
		if(multipart == null || multipart.fuselage == null)
			return false;
		
		ItemMissile fuselage = multipart.fuselage;
		
		return fuselage.top == this.padSize;
	}
	
	public boolean hasDesignator() {
		
		if(this.slots[1] != null && this.slots[1].getItem() instanceof IDesignatorItem && ((IDesignatorItem)this.slots[1].getItem()).isReady(this.worldObj, this.slots[1], this.xCoord, this.yCoord, this.zCoord)) {
			return true;
		}
		
		return false;
	}
	
	public int solidState() {
		
		MissileStruct multipart = TileEntityLaunchTable.getStruct(this.slots[0]);
		
		if(multipart == null || multipart.fuselage == null)
			return -1;
		
		ItemMissile fuselage = multipart.fuselage;
		
		if((FuelType)fuselage.attributes[0] == FuelType.SOLID) {
			
			if(this.solid >= (Float)fuselage.attributes[1])
				return 1;
			else
				return 0;
		}
		
		return -1;
	}
	
	public int liquidState() {
		
		MissileStruct multipart = TileEntityLaunchTable.getStruct(this.slots[0]);
		
		if(multipart == null || multipart.fuselage == null)
			return -1;
		
		ItemMissile fuselage = multipart.fuselage;
		
		switch((FuelType)fuselage.attributes[0]) {
			case KEROSENE:
			case HYDROGEN:
			case XENON:
			case BALEFIRE:
				
				if(this.tanks[0].getFill() >= (Float)fuselage.attributes[1])
					return 1;
				else
					return 0;
			default: break;
		}
		
		return -1;
	}
	
	public int oxidizerState() {
		
		MissileStruct multipart = TileEntityLaunchTable.getStruct(this.slots[0]);
		
		if(multipart == null || multipart.fuselage == null)
			return -1;
		
		ItemMissile fuselage = multipart.fuselage;
		
		switch((FuelType)fuselage.attributes[0]) {
			case KEROSENE:
			case HYDROGEN:
			case BALEFIRE:
				
				if(this.tanks[1].getFill() >= (Float)fuselage.attributes[1])
					return 1;
				else
					return 0;
			default: break;
		}
		
		return -1;
	}
	
	public void updateTypes() {
		
		MissileStruct multipart = TileEntityLaunchTable.getStruct(this.slots[0]);
		
		if(multipart == null || multipart.fuselage == null)
			return;
		
		ItemMissile fuselage = multipart.fuselage;
		
		switch((FuelType)fuselage.attributes[0]) {
			case KEROSENE:
				this.tanks[0].setTankType(Fluids.KEROSENE);
				this.tanks[1].setTankType(Fluids.ACID);
				break;
			case HYDROGEN:
				this.tanks[0].setTankType(Fluids.HYDROGEN);
				this.tanks[1].setTankType(Fluids.OXYGEN);
				break;
			case XENON:
				this.tanks[0].setTankType(Fluids.XENON);
				break;
			case BALEFIRE:
				this.tanks[0].setTankType(Fluids.BALEFIRE);
				this.tanks[1].setTankType(Fluids.ACID);
				break;
			default: break;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);

		this.tanks[0].readFromNBT(nbt, "fuel");
		this.tanks[1].readFromNBT(nbt, "oxidizer");
		this.solid = nbt.getInteger("solidfuel");
		this.power = nbt.getLong("power");
		this.padSize = PartSize.values()[nbt.getInteger("padSize")];

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

		this.tanks[0].writeToNBT(nbt, "fuel");
		this.tanks[1].writeToNBT(nbt, "oxidizer");
		nbt.setInteger("solidfuel", this.solid);
		nbt.setLong("power", this.power);
		nbt.setInteger("padSize", this.padSize.ordinal());

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
		return TileEntityLaunchTable.access;
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
	public int getMaxFluidFill(FluidType type) {
		if (type.getName().equals(this.tanks[0].getTankType().getName()))
			return this.tanks[0].getMaxFill();
		else if (type.getName().equals(this.tanks[1].getTankType().getName()))
			return this.tanks[1].getMaxFill();
		else
			return 0;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if (index < 2 && this.tanks[index] != null)
			this.tanks[index].setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		if (type.getName().equals(this.tanks[0].getTankType().getName()))
			this.tanks[0].setFill(fill);
		else if (type.getName().equals(this.tanks[1].getTankType().getName()))
			this.tanks[1].setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if (index < 2 && this.tanks[index] != null)
			this.tanks[index].setTankType(type);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if (type.getName().equals(this.tanks[0].getTankType().getName()))
			return this.tanks[0].getFill();
		else if (type.getName().equals(this.tanks[1].getTankType().getName()))
			return this.tanks[1].getFill();
		else
			return 0;
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
		return TileEntityLaunchTable.maxPower;
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
		return dir != ForgeDirection.UP && dir != ForgeDirection.DOWN && dir != ForgeDirection.UNKNOWN;
	}

	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir != ForgeDirection.UP && dir != ForgeDirection.DOWN && dir != ForgeDirection.UNKNOWN;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return this.tanks;
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return this.tanks;
	}

	// do some opencomputer stuff
	@Override
	public String getComponentName() {
		return "large_launch_pad";
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getEnergyInfo(Context context, Arguments args) {
		return new Object[] {getPower(), getMaxPower()};
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getContents(Context context, Arguments args) {
		return new Object[] {this.tanks[0].getFill(), this.tanks[0].getMaxFill(), this.tanks[0].getTankType().getName(), this.tanks[1].getFill(), this.tanks[1].getMaxFill(), this.tanks[1].getTankType().getName(), this.solid, TileEntityLaunchTable.maxSolid};
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getLaunchInfo(Context context, Arguments args) {
		return new Object[] {canLaunch(), isMissileValid(), hasDesignator(), hasFuel()};
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
		return new ContainerLaunchTable(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineLaunchTable(player.inventory, this);
	}
}
