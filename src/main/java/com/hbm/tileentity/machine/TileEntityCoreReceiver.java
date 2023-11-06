package com.hbm.tileentity.machine;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.container.ContainerCoreReceiver;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUICoreReceiver;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.block.ILaserable;
import api.hbm.energy.IEnergyGenerator;
import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityCoreReceiver extends TileEntityMachineBase implements IEnergyGenerator, IFluidAcceptor, ILaserable, IFluidStandardReceiver, SimpleComponent, IGUIProvider {
	
	public long power;
	public long joules;
	public FluidTank tank;

	public TileEntityCoreReceiver() {
		super(0);
		this.tank = new FluidTank(Fluids.CRYOGEL, 64000);
	}

	@Override
	public String getName() {
		return "container.dfcReceiver";
	}

	@Override
	public void updateEntity() {

		if (!this.worldObj.isRemote) {
			
			this.tank.updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
			this.subscribeToAllAround(this.tank.getTankType(), this);
			
			this.power = this.joules * 5000;
			
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
				sendPower(this.worldObj, this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ, dir);
			
			if(this.joules > 0) {

				if(this.tank.getFill() >= 20) {
					this.tank.setFill(this.tank.getFill() - 20);
				} else {
					this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Blocks.flowing_lava);
					return;
				}
			}

			NBTTagCompound data = new NBTTagCompound();
			data.setLong("joules", this.joules);
			networkPack(data, 50);
			
			this.joules = 0;
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.joules = data.getLong("joules");
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
	public boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN;
	}

	@Override
	public long getMaxPower() {
		return 0;
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
	public void addEnergy(World world, int x, int y, int z, long energy, ForgeDirection dir) {
		
		//only accept lasers from the front
		if(dir.getOpposite().ordinal() == getBlockMetadata()) {
			this.joules += energy;
		} else {
			this.worldObj.func_147480_a(this.xCoord, this.yCoord, this.zCoord, false);
			this.worldObj.createExplosion(null, this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, 2.5F, true);
		}
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
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.power = nbt.getLong("power");
		this.joules = nbt.getLong("joules");
		this.tank.readFromNBT(nbt, "tank");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", this.power);
		nbt.setLong("joules", this.joules);
		this.tank.writeToNBT(nbt, "tank");
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { this.tank };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { this.tank };
	}

	// do some opencomputer stuff
	@Override
	public String getComponentName() {
		return "dfc_receiver";
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getEnergyInfo(Context context, Arguments args) {
		return new Object[] {this.joules, getPower()}; //literally only doing this for the consistency between components
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCryogel(Context context, Arguments args) {
		return new Object[] {this.tank.getFill()};
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		return new Object[] {this.joules, getPower(), this.tank.getFill()};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCoreReceiver(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICoreReceiver(player.inventory, this);
	}
}
