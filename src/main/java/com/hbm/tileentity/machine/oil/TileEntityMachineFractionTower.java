package com.hbm.tileentity.machine.oil;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.recipes.FractionRecipes;
import com.hbm.lib.Library;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.Tuple.Pair;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineFractionTower extends TileEntityLoadedBase implements IFluidSource, IFluidAcceptor, INBTPacketReceiver, IFluidStandardTransceiver {
	
	public FluidTank[] tanks;
	public List<IFluidAcceptor> list1 = new ArrayList<>();
	public List<IFluidAcceptor> list2 = new ArrayList<>();
	
	public TileEntityMachineFractionTower() {
		this.tanks = new FluidTank[3];
		this.tanks[0] = new FluidTank(Fluids.HEAVYOIL, 4000, 0);
		this.tanks[1] = new FluidTank(Fluids.BITUMEN, 4000, 1);
		this.tanks[2] = new FluidTank(Fluids.SMEAR, 4000, 2);
	}
	
	@Override
	public void updateEntity() {

		if(!this.worldObj.isRemote) {
			
			TileEntity stack = this.worldObj.getTileEntity(this.xCoord, this.yCoord + 3, this.zCoord);
			
			if(stack instanceof TileEntityMachineFractionTower) {
				TileEntityMachineFractionTower frac = (TileEntityMachineFractionTower) stack;
				
				//make types equal
				for(int i = 0; i < 3; i++) {
					frac.tanks[i].setTankType(this.tanks[i].getTankType());
				}
				
				//calculate transfer
				int oil = Math.min(this.tanks[0].getFill(), frac.tanks[0].getMaxFill() - frac.tanks[0].getFill());
				int left = Math.min(frac.tanks[1].getFill(), this.tanks[1].getMaxFill() - this.tanks[1].getFill());
				int right = Math.min(frac.tanks[2].getFill(), this.tanks[2].getMaxFill() - this.tanks[2].getFill());
				
				//move oil up, pull fractions down
				this.tanks[0].setFill(this.tanks[0].getFill() - oil);
				this.tanks[1].setFill(this.tanks[1].getFill() + left);
				this.tanks[2].setFill(this.tanks[2].getFill() + right);
				frac.tanks[0].setFill(frac.tanks[0].getFill() + oil);
				frac.tanks[1].setFill(frac.tanks[1].getFill() - left);
				frac.tanks[2].setFill(frac.tanks[2].getFill() - right);
			}
			
			setupTanks();
			updateConnections();
			
			if(this.worldObj.getTotalWorldTime() % 20 == 0)
				fractionate();
			
			if(this.worldObj.getTotalWorldTime() % 10 == 0) {
				fillFluidInit(this.tanks[1].getTankType());
				fillFluidInit(this.tanks[2].getTankType());
			}
			
			this.sendFluid();
			
			NBTTagCompound data = new NBTTagCompound();

			for(int i = 0; i < 3; i++)
				this.tanks[i].writeToNBT(data, "tank" + i);
			
			INBTPacketReceiver.networkPack(this, data, 50);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		for(int i = 0; i < 3; i++)
			this.tanks[i].readFromNBT(nbt, "tank" + i);
	}
	
	private void updateConnections() {
		
		for(DirPos pos : getConPos()) {
			trySubscribe(this.tanks[0].getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	private void sendFluid() {
		
		for(DirPos pos : getConPos()) {
			this.sendFluid(this.tanks[1], this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.sendFluid(this.tanks[2], this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	private DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(this.xCoord + 2, this.yCoord, this.zCoord, Library.POS_X),
				new DirPos(this.xCoord - 2, this.yCoord, this.zCoord, Library.NEG_X),
				new DirPos(this.xCoord, this.yCoord, this.zCoord + 2, Library.POS_Z),
				new DirPos(this.xCoord, this.yCoord, this.zCoord - 2, Library.NEG_Z)
		};
	}
	
	private void setupTanks() {
		
		Pair<FluidStack, FluidStack> quart = FractionRecipes.getFractions(this.tanks[0].getTankType());
		
		if(quart != null) {
			this.tanks[1].setTankType(quart.getKey().type);
			this.tanks[2].setTankType(quart.getValue().type);
		} else {
			this.tanks[0].setTankType(Fluids.NONE);
			this.tanks[1].setTankType(Fluids.NONE);
			this.tanks[2].setTankType(Fluids.NONE);
		}
	}
	
	private void fractionate() {
		
		Pair<FluidStack, FluidStack> quart = FractionRecipes.getFractions(this.tanks[0].getTankType());
		
		if(quart != null) {
			
			int left = quart.getKey().fill;
			int right = quart.getValue().fill;
			
			if(this.tanks[0].getFill() >= 100 && hasSpace(left, right)) {
				this.tanks[0].setFill(this.tanks[0].getFill() - 100);
				this.tanks[1].setFill(this.tanks[1].getFill() + left);
				this.tanks[2].setFill(this.tanks[2].getFill() + right);
			}
		}
	}
	
	private boolean hasSpace(int left, int right) {
		return this.tanks[1].getFill() + left <= this.tanks[1].getMaxFill() && this.tanks[2].getFill() + right <= this.tanks[2].getMaxFill();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		for(int i = 0; i < 3; i++)
			this.tanks[i].readFromNBT(nbt, "tank" + i);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		for(int i = 0; i < 3; i++)
			this.tanks[i].writeToNBT(nbt, "tank" + i);
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if(index < 3 && this.tanks[index] != null)
			this.tanks[index].setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		for(FluidTank tank : this.tanks) {
			if(tank.getTankType() == type) {
				tank.setFill(fill);
			}
		}
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		this.tanks[index].setTankType(type);
	}

	@Override
	public int getFluidFill(FluidType type) {
		for(FluidTank tank : this.tanks) {
			if(tank.getTankType() == type) {
				return tank.getFill();
			}
		}
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type == this.tanks[0].getTankType())
			return this.tanks[0].getMaxFill();
		else
			return 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		
		for(int i = 2; i < 6; i++) {
			ForgeDirection dir = ForgeDirection.getOrientation(i);
			fillFluid(this.xCoord + dir.offsetX * 2, this.yCoord, this.zCoord + dir.offsetZ * 2, getTact(), type);
		}
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, this.worldObj, type);
	}

	@Override
	public boolean getTact() {
		return this.worldObj.getTotalWorldTime() % 20 < 10;
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		if(type == this.tanks[1].getTankType()) return this.list1;
		if(type == this.tanks[2].getTankType()) return this.list2;
		return new ArrayList<>();
	}

	@Override
	public void clearFluidList(FluidType type) {
		if(type == this.tanks[1].getTankType()) this.list1.clear();
		if(type == this.tanks[2].getTankType()) this.list2.clear();
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(
					this.xCoord - 1,
					this.yCoord,
					this.zCoord - 1,
					this.xCoord + 2,
					this.yCoord + 3,
					this.zCoord + 2
					);
		}
		
		return this.bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { this.tanks[1], this.tanks[2] };
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { this.tanks[0] };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return this.tanks;
	}
}
