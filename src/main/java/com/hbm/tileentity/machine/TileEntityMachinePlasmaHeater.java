package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineITER;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.container.ContainerPlasmaHeater;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIPlasmaHeater;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachinePlasmaHeater extends TileEntityMachineBase implements IFluidAcceptor, IEnergyUser, IFluidStandardReceiver, IGUIProvider {
	
	public long power;
	public static final long maxPower = 100000000;
	
	public FluidTank[] tanks;
	public FluidTank plasma;

	public TileEntityMachinePlasmaHeater() {
		super(5);
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.DEUTERIUM, 16000);
		this.tanks[1] = new FluidTank(Fluids.TRITIUM, 16000);
		this.plasma = new FluidTank(Fluids.PLASMA_DT, 64000);
	}

	@Override
	public String getName() {
		return "container.plasmaHeater";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			if(this.worldObj.getTotalWorldTime() % 20 == 0)
				updateConnections();

			/// START Managing all the internal stuff ///
			this.power = Library.chargeTEFromItems(this.slots, 0, this.power, TileEntityMachinePlasmaHeater.maxPower);
			this.tanks[0].setType(1, 2, this.slots);
			this.tanks[1].setType(3, 4, this.slots);
			
			updateType();
			
			int maxConv = 50;
			int powerReq = 10000;
			
			int convert = Math.min(this.tanks[0].getFill(), this.tanks[1].getFill());
			convert = Math.min(convert, (this.plasma.getMaxFill() - this.plasma.getFill()) / 2);
			convert = Math.min(convert, maxConv);
			convert = (int) Math.min(convert, this.power / powerReq);
			convert = Math.max(0, convert);
			
			if(convert > 0 && this.plasma.getTankType() != Fluids.NONE) {

				this.tanks[0].setFill(this.tanks[0].getFill() - convert);
				this.tanks[1].setFill(this.tanks[1].getFill() - convert);
				
				this.plasma.setFill(this.plasma.getFill() + convert * 2);
				this.power -= convert * powerReq;
				
				markDirty();
			}
			/// END Managing all the internal stuff ///

			/// START Loading plasma into the ITER ///
			
			ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset).getOpposite();
			int dist = 11;
			
			if(this.worldObj.getBlock(this.xCoord + dir.offsetX * dist, this.yCoord + 2, this.zCoord + dir.offsetZ * dist) == ModBlocks.iter) {
				int[] pos = ((MachineITER)ModBlocks.iter).findCore(this.worldObj, this.xCoord + dir.offsetX * dist, this.yCoord + 2, this.zCoord + dir.offsetZ * dist);
				
				if(pos != null) {
					TileEntity te = this.worldObj.getTileEntity(pos[0], pos[1], pos[2]);
					
					if(te instanceof TileEntityITER) {
						TileEntityITER iter = (TileEntityITER)te;
							
						if(iter.plasma.getFill() == 0 && this.plasma.getTankType() != Fluids.NONE) {
							iter.plasma.setTankType(this.plasma.getTankType());
						}
							
							if(iter.isOn) {
							
							if(iter.plasma.getTankType() == this.plasma.getTankType()) {
								
								int toLoad = Math.min(iter.plasma.getMaxFill() - iter.plasma.getFill(), this.plasma.getFill());
								toLoad = Math.min(toLoad, 40);
								this.plasma.setFill(this.plasma.getFill() - toLoad);
								iter.plasma.setFill(iter.plasma.getFill() + toLoad);
								markDirty();
								iter.markDirty();
							}
						}
					}
				}
			}
			
			/// END Loading plasma into the ITER ///

			/// START Notif packets ///
			for (FluidTank tank : this.tanks)
				tank.updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
			this.plasma.updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			networkPack(data, 50);
			/// END Notif packets ///
		}
	}
	
	private void updateConnections()  {
		
		getBlockMetadata();
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.blockMetadata - BlockDummyable.offset);
		ForgeDirection side = dir.getRotation(ForgeDirection.UP);
		
		for(int i = 1; i < 4; i++) {
			for(int j = -1; j < 2; j++) {
				this.trySubscribe(this.worldObj, this.xCoord + side.offsetX * j + dir.offsetX * 2, this.yCoord + i, this.zCoord + side.offsetZ * j + dir.offsetZ * 2, j < 0 ? ForgeDirection.DOWN : ForgeDirection.UP);
				this.trySubscribe(this.tanks[0].getTankType(), this.worldObj, this.xCoord + side.offsetX * j + dir.offsetX * 2, this.yCoord + i, this.zCoord + side.offsetZ * j + dir.offsetZ * 2, j < 0 ? ForgeDirection.DOWN : ForgeDirection.UP);
				this.trySubscribe(this.tanks[1].getTankType(), this.worldObj, this.xCoord + side.offsetX * j + dir.offsetX * 2, this.yCoord + i, this.zCoord + side.offsetZ * j + dir.offsetZ * 2, j < 0 ? ForgeDirection.DOWN : ForgeDirection.UP);
			}
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
	}
	
	@SuppressWarnings({ "serial" })
	private void updateType() {
		
		List<FluidType> types = new ArrayList() {{ add(TileEntityMachinePlasmaHeater.this.tanks[0].getTankType()); add(TileEntityMachinePlasmaHeater.this.tanks[1].getTankType()); }};

		if(types.contains(Fluids.DEUTERIUM) && types.contains(Fluids.TRITIUM)) {
			this.plasma.setTankType(Fluids.PLASMA_DT);
			return;
		}
		if(types.contains(Fluids.DEUTERIUM) && types.contains(Fluids.HELIUM3)) {
			this.plasma.setTankType(Fluids.PLASMA_DH3);
			return;
		}
		if(types.contains(Fluids.DEUTERIUM) && types.contains(Fluids.HYDROGEN)) {
			this.plasma.setTankType(Fluids.PLASMA_HD);
			return;
		}
		if(types.contains(Fluids.HYDROGEN) && types.contains(Fluids.TRITIUM)) {
			this.plasma.setTankType(Fluids.PLASMA_HT);
			return;
		}
		if(types.contains(Fluids.HELIUM4) && types.contains(Fluids.OXYGEN)) {
			this.plasma.setTankType(Fluids.PLASMA_XM);
			return;
		}
		if(types.contains(Fluids.BALEFIRE) && types.contains(Fluids.AMAT)) {
			this.plasma.setTankType(Fluids.PLASMA_BF);
			return;
		}
		
		this.plasma.setTankType(Fluids.NONE);
	}
	
	public long getPowerScaled(int i) {
		return (this.power * i) / TileEntityMachinePlasmaHeater.maxPower;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.power = nbt.getLong("power");
		this.tanks[0].readFromNBT(nbt, "fuel_1");
		this.tanks[1].readFromNBT(nbt, "fuel_2");
		this.plasma.readFromNBT(nbt, "plasma");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setLong("power", this.power);
		this.tanks[0].writeToNBT(nbt, "fuel_1");
		this.tanks[1].writeToNBT(nbt, "fuel_2");
		this.plasma.writeToNBT(nbt, "plasma");
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if (type.getName().equals(this.tanks[0].getTankType().getName()))
			return this.tanks[0].getMaxFill();
		else if (type.getName().equals(this.tanks[1].getTankType().getName()))
			return this.tanks[1].getMaxFill();
		else if (type.getName().equals(this.plasma.getTankType().getName()))
			return this.plasma.getMaxFill();
		else
			return 0;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if (type.getName().equals(this.tanks[0].getTankType().getName()))
			this.tanks[0].setFill(i);
		else if (type.getName().equals(this.tanks[1].getTankType().getName()))
			this.tanks[1].setFill(i);
		else if (type.getName().equals(this.plasma.getTankType().getName()))
			this.plasma.setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if (type.getName().equals(this.tanks[0].getTankType().getName()))
			return this.tanks[0].getFill();
		else if (type.getName().equals(this.tanks[1].getTankType().getName()))
			return this.tanks[1].getFill();
		else if (type.getName().equals(this.plasma.getTankType().getName()))
			return this.plasma.getFill();
		else
			return 0;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if (index < 2 && this.tanks[index] != null)
			this.tanks[index].setFill(fill);
		
		if(index == 2)
			this.plasma.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if (index < 2 && this.tanks[index] != null)
			this.tanks[index].setTankType(type);
		
		if(index == 2)
			this.plasma.setTankType(type);
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
		return TileEntityMachinePlasmaHeater.maxPower;
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
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {this.tanks[0], this.tanks[1], this.plasma};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return this.tanks;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerPlasmaHeater(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIPlasmaHeater(player.inventory, this);
	}
}
