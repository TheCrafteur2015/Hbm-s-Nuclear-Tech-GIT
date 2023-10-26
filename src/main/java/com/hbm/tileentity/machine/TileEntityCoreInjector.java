package com.hbm.tileentity.machine;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.container.ContainerCoreInjector;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUICoreInjector;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

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
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityCoreInjector extends TileEntityMachineBase implements IFluidAcceptor, IFluidStandardReceiver, SimpleComponent, IGUIProvider {
	
	public FluidTank[] tanks;
	public static final int range = 15;
	public int beam;

	public TileEntityCoreInjector() {
		super(4);
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.DEUTERIUM, 128000, 0);
		this.tanks[1] = new FluidTank(Fluids.TRITIUM, 128000, 1);
	}

	@Override
	public String getName() {
		return "container.dfcInjector";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			this.subscribeToAllAround(this.tanks[0].getTankType(), this);
			this.subscribeToAllAround(this.tanks[1].getTankType(), this);

			this.tanks[0].setType(0, 1, this.slots);
			this.tanks[1].setType(2, 3, this.slots);
			
			this.beam = 0;
			
			ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata());
			for(int i = 1; i <= TileEntityCoreInjector.range; i++) {

				int x = this.xCoord + dir.offsetX * i;
				int y = this.yCoord + dir.offsetY * i;
				int z = this.zCoord + dir.offsetZ * i;
				
				TileEntity te = this.worldObj.getTileEntity(x, y, z);
				
				if(te instanceof TileEntityCore) {
					
					TileEntityCore core = (TileEntityCore)te;
					
					for(int t = 0; t < 2; t++) {
						
						if(core.tanks[t].getTankType() == this.tanks[t].getTankType()) {
							
							int f = Math.min(this.tanks[t].getFill(), core.tanks[t].getMaxFill() - core.tanks[t].getFill());

							this.tanks[t].setFill(this.tanks[t].getFill() - f);
							core.tanks[t].setFill(core.tanks[t].getFill() + f);
							core.markDirty();
							
						} else if(core.tanks[t].getFill() == 0) {
							
							core.tanks[t].setTankType(this.tanks[t].getTankType());
							int f = Math.min(this.tanks[t].getFill(), core.tanks[t].getMaxFill() - core.tanks[t].getFill());

							this.tanks[t].setFill(this.tanks[t].getFill() - f);
							core.tanks[t].setFill(core.tanks[t].getFill() + f);
							core.markDirty();
						}
					}
					
					this.beam = i;
					break;
				}
				
				if(!this.worldObj.getBlock(x, y, z).isAir(this.worldObj, x, y, z))
					break;
			}
			
			markDirty();

			this.tanks[0].updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
			this.tanks[1].updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);

			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("beam", this.beam);
			networkPack(data, 250);
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.beam = data.getInteger("beam");
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
	}

	@Override
	public int getFluidFill(FluidType type) {
		if (type.name().equals(this.tanks[0].getTankType().name()))
			return this.tanks[0].getFill();
		else if (type.name().equals(this.tanks[1].getTankType().name()))
			return this.tanks[1].getFill();
		else
			return 0;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if (index < 2 && this.tanks[index] != null)
			this.tanks[index].setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if (index < 2 && this.tanks[index] != null)
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
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.tanks[0].readFromNBT(nbt, "fuel1");
		this.tanks[1].readFromNBT(nbt, "fuel2");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		this.tanks[0].writeToNBT(nbt, "fuel1");
		this.tanks[1].writeToNBT(nbt, "fuel2");
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {this.tanks[0], this.tanks[1]};
	}

	@Override
	public FluidTank[] getAllTanks() {
		return this.tanks;
	}
	
	// do some opencomputer stuff
	@Override
	public String getComponentName() {
		return "dfc_injector";
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getFuel(Context context, Arguments args) {
		return new Object[] {this.tanks[0].getFill(), this.tanks[1].getFill()};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getTypes(Context context, Arguments args) {
		return new Object[] {this.tanks[0].getTankType().getName(), this.tanks[1].getTankType().getName()};
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		return new Object[] {this.tanks[0].getFill(), this.tanks[0].getTankType().getName(), this.tanks[1].getFill(), this.tanks[1].getTankType().getName()};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCoreInjector(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICoreInjector(player.inventory, this);
	}
}
