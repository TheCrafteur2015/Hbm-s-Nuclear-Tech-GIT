package com.hbm.tileentity.network;

import java.util.List;

import com.hbm.entity.item.EntityDeliveryDrone;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.container.ContainerDroneCrate;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIDroneCrate;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityDroneCrate extends TileEntityMachineBase implements IGUIProvider, INBTPacketReceiver, IControlReceiver, IDroneLinkable, IFluidStandardTransceiver {
	
	public FluidTank tank;
	
	public int nextX = -1;
	public int nextY = -1;
	public int nextZ = -1;
	
	public boolean sendingMode = false;
	public boolean itemType = true;

	public TileEntityDroneCrate() {
		super(19);
		this.tank = new FluidTank(Fluids.NONE, 64_000);
	}

	@Override
	public String getName() {
		return "container.droneCrate";
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			this.tank.setType(18, this.slots);
			
			if(this.sendingMode && !this.itemType && this.worldObj.getTotalWorldTime() % 20 == 0) {
				this.subscribeToAllAround(this.tank.getTankType(), this);
			}
			
			if(!this.sendingMode && !this.itemType && this.worldObj.getTotalWorldTime() % 20 == 0) {
				this.sendFluidToAll(this.tank, this);
			}
			
			if(this.nextY != -1) {
				
				List<EntityDeliveryDrone> drones = this.worldObj.getEntitiesWithinAABB(EntityDeliveryDrone.class, AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord + 1, this.zCoord, this.xCoord + 1, this.yCoord + 2, this.zCoord + 1));
				for(EntityDeliveryDrone drone : drones) {
					if(Vec3.createVectorHelper(drone.motionX, drone.motionY, drone.motionZ).lengthVector() < 0.05) {
						drone.setTarget(this.nextX + 0.5, this.nextY, this.nextZ + 0.5);

						if(this.sendingMode && this.itemType) loadItems(drone);
						if(!this.sendingMode && this.itemType) unloadItems(drone);
						if(this.sendingMode && !this.itemType) loadFluid(drone);
						if(!this.sendingMode && !this.itemType) unloadFluid(drone);
					}
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setIntArray("pos", new int[] {this.nextX, this.nextY, this.nextZ});
			data.setBoolean("mode", this.sendingMode);
			data.setBoolean("type", this.itemType);
			this.tank.writeToNBT(data, "t");
			INBTPacketReceiver.networkPack(this, data, 25);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		int[] pos = nbt.getIntArray("pos");
		this.nextX = pos[0];
		this.nextY = pos[1];
		this.nextZ = pos[2];
		this.sendingMode = nbt.getBoolean("mode");
		this.itemType = nbt.getBoolean("type");
		this.tank.readFromNBT(nbt, "t");
	}
	
	protected void loadItems(EntityDeliveryDrone drone) {
		
		if(drone.getAppearance() != 0) return;
		
		boolean loaded = false;
		
		for(int i = 0; i < 18; i++) {
			if(this.slots[i] != null) {
				loaded = true;
				drone.setInventorySlotContents(i, this.slots[i].copy());
				this.slots[i] = null;
			}
		}
		
		if(loaded) {
			markDirty();
			drone.setAppearance(1);
			this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, "hbm:item.unpack", 0.5F, 0.75F);
		}
	}
	
	protected void unloadItems(EntityDeliveryDrone drone) {
		
		if(drone.getAppearance() != 1) return;
		
		boolean emptied = true;
		
		for(int i = 0; i < 18; i++) {
			ItemStack droneSlot = drone.getStackInSlot(i);
			
			if(this.slots[i] == null && droneSlot != null) {
				this.slots[i] = droneSlot.copy();
				drone.setInventorySlotContents(i, null);
			} else if(this.slots[i] != null && droneSlot != null) {
				emptied = false;
			}
		}
		
		markDirty();
		
		if(emptied) {
			drone.setAppearance(0);
			this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, "hbm:item.unpack", 0.5F, 0.75F);
		}
	}
	
	protected void loadFluid(EntityDeliveryDrone drone) {
		
		if(drone.getAppearance() != 0) return;
		
		if(this.tank.getFill() > 0) {
			drone.fluid = new FluidStack(this.tank.getTankType(), this.tank.getFill());
			this.tank.setFill(0);
			drone.setAppearance(2);
			this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, "hbm:item.unpack", 0.5F, 0.75F);
			
			markDirty();
		}
	}
	
	protected void unloadFluid(EntityDeliveryDrone drone) {
		
		if(drone.getAppearance() != 2) return;
		
		if(drone.fluid != null && drone.fluid.type == this.tank.getTankType()) {
			
			if(drone.fluid.fill + this.tank.getFill() <= this.tank.getMaxFill()) {
				this.tank.setFill(this.tank.getFill() + drone.fluid.fill);
				drone.fluid = null;
				drone.setAppearance(0);
			} else {
				int overshoot = drone.fluid.fill + this.tank.getFill() - this.tank.getMaxFill();
				this.tank.setFill(this.tank.getMaxFill());
				drone.fluid.fill = overshoot;
			}
			this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, "hbm:item.unpack", 0.5F, 0.75F);
			
			markDirty();
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 };
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return true;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return true;
	}

	@Override
	public BlockPos getPoint() {
		return new BlockPos(this.xCoord, this.yCoord + 1, this.zCoord);
	}

	@Override
	public void setNextTarget(int x, int y, int z) {
		this.nextX = x;
		this.nextY = y;
		this.nextZ = z;
		markDirty();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		int[] pos = nbt.getIntArray("pos");
		this.nextX = pos[0];
		this.nextY = pos[1];
		this.nextZ = pos[2];
		this.sendingMode = nbt.getBoolean("mode");
		this.itemType = nbt.getBoolean("type");
		this.tank.readFromNBT(nbt, "t");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setIntArray("pos", new int[] {this.nextX, this.nextY, this.nextZ});
		nbt.setBoolean("mode", this.sendingMode);
		nbt.setBoolean("type", this.itemType);
		this.tank.writeToNBT(nbt, "t");
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerDroneCrate(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIDroneCrate(player.inventory, this);
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		
		if(data.hasKey("mode")) {
			this.sendingMode = !this.sendingMode;
			markChanged();
		}
		
		if(data.hasKey("type")) {
			this.itemType = !this.itemType;
			markChanged();
		}
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { this.tank };
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return !this.sendingMode && !this.itemType ? new FluidTank[] { this.tank } : new FluidTank[0];
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return this.sendingMode && !this.itemType ? new FluidTank[] { this.tank } : new FluidTank[0];
	}
}
