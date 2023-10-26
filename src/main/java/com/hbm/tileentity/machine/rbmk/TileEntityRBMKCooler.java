package com.hbm.tileentity.machine.rbmk;

import java.util.List;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.Library;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;

import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.common.Optional;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;

@SuppressWarnings("deprecation")
@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityRBMKCooler extends TileEntityRBMKBase implements IFluidAcceptor, IFluidStandardReceiver, SimpleComponent {

	private FluidTank tank;
	private int lastCooled;

	public TileEntityRBMKCooler() {
		super();
		this.tank = new FluidTank(Fluids.CRYOGEL, 8000);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateEntity() {

		if (!this.worldObj.isRemote) {

			if (this.worldObj.getTotalWorldTime() % 20 == 0)
				trySubscribe(this.tank.getTankType(), this.worldObj, this.xCoord, this.yCoord - 1, this.zCoord, Library.NEG_Y);

			if ((int) (this.heat) > 750) {

				int heatProvided = (int) (this.heat - 750D);
				int cooling = Math.min(heatProvided, this.tank.getFill());

				this.heat -= cooling;
				this.tank.setFill(this.tank.getFill() - cooling);

				this.lastCooled = cooling;

				if (this.lastCooled > 0) {
					List<Entity> entities = this.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord + 4, this.zCoord, this.xCoord + 1, this.yCoord + 8, this.zCoord + 1));

					for (Entity e : entities) {
						e.setFire(5);
						e.attackEntityFrom(DamageSource.inFire, 10);
					}
				}
			} else {
				this.lastCooled = 0;
			}

		} else {

			if (this.lastCooled > 100) {
				for (int i = 0; i < 2; i++) {
					this.worldObj.spawnParticle("flame", this.xCoord + 0.25 + this.worldObj.rand.nextDouble() * 0.5, this.yCoord + 4.5, this.zCoord + 0.25 + this.worldObj.rand.nextDouble() * 0.5, 0, 0.2, 0);
					this.worldObj.spawnParticle("smoke", this.xCoord + 0.25 + this.worldObj.rand.nextDouble() * 0.5, this.yCoord + 4.5, this.zCoord + 0.25 + this.worldObj.rand.nextDouble() * 0.5, 0, 0.2, 0);
				}

				if (this.worldObj.rand.nextInt(20) == 0)
					this.worldObj.spawnParticle("lava", this.xCoord + 0.25 + this.worldObj.rand.nextDouble() * 0.5, this.yCoord + 4.5, this.zCoord + 0.25 + this.worldObj.rand.nextDouble() * 0.5, 0, 0.0, 0);
			} else if (this.lastCooled > 50) {
				for (int i = 0; i < 2; i++) {
					this.worldObj.spawnParticle("cloud", this.xCoord + 0.25 + this.worldObj.rand.nextDouble() * 0.5, this.yCoord + 4.5, this.zCoord + 0.25 + this.worldObj.rand.nextDouble() * 0.5, this.worldObj.rand.nextGaussian() * 0.05, 0.2, this.worldObj.rand.nextGaussian() * 0.05);
				}
			} else if (this.lastCooled > 0) {

				if (this.worldObj.getTotalWorldTime() % 2 == 0)
					this.worldObj.spawnParticle("cloud", this.xCoord + 0.25 + this.worldObj.rand.nextDouble() * 0.5, this.yCoord + 4.5, this.zCoord + 0.25 + this.worldObj.rand.nextDouble() * 0.5, 0, 0.2, 0);

			}
		}

		super.updateEntity();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.tank.readFromNBT(nbt, "cryo");
		this.lastCooled = nbt.getInteger("cooled");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		this.tank.writeToNBT(nbt, "cryo");
		nbt.setInteger("cooled", this.lastCooled);
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.COOLER;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		this.tank.setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		if (type == this.tank.getTankType())
			this.tank.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		this.tank.setTankType(type);
	}

	@Override
	public int getFluidFill(FluidType type) {
		return type == this.tank.getTankType() ? this.tank.getFill() : 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		return type == this.tank.getTankType() ? this.tank.getMaxFill() : 0;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[]{this.tank};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[]{this.tank};
	}

	//do some opencomputers stuff

	@Override
	public String getComponentName() {
		return "rbmk_cooler";
	}

	@Callback(direct = true, limit = 8)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getHeat(Context context, Arguments args) {
		return new Object[]{this.heat};
	}

	@Callback(direct = true, limit = 8)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCryo(Context context, Arguments args) {
		return new Object[]{this.tank.getFill()};
	}

	@Callback(direct = true, limit = 8)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCryoMax(Context context, Arguments args) {
		return new Object[]{this.tank.getMaxFill()};
	}

	@Callback(direct = true, limit = 8)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCoordinates(Context context, Arguments args) {
		return new Object[] {this.xCoord, this.yCoord, this.zCoord};
	}

	@Callback(direct = true, limit = 8)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		return new Object[]{this.heat, this.tank.getFill(), this.tank.getMaxFill(), this.xCoord, this.yCoord, this.zCoord};
	}
}
