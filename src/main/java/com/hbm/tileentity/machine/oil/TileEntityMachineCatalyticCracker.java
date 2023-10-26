package com.hbm.tileentity.machine.oil;

import com.hbm.blocks.BlockDummyable;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.recipes.CrackingRecipes;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.Tuple.Pair;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineCatalyticCracker extends TileEntityLoadedBase implements INBTPacketReceiver, IFluidStandardTransceiver {
	
	public FluidTank[] tanks;
	
	public TileEntityMachineCatalyticCracker() {
		this.tanks = new FluidTank[5];
		this.tanks[0] = new FluidTank(Fluids.BITUMEN, 4000);
		this.tanks[1] = new FluidTank(Fluids.STEAM, 8000);
		this.tanks[2] = new FluidTank(Fluids.OIL, 4000);
		this.tanks[3] = new FluidTank(Fluids.PETROLEUM, 4000);
		this.tanks[4] = new FluidTank(Fluids.SPENTSTEAM, 800);
	}
	
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {

			this.worldObj.theProfiler.startSection("catalyticCracker_setup_tanks");
			setupTanks();
			this.worldObj.theProfiler.endStartSection("catalyticCracker_update_connections");
			updateConnections();

			this.worldObj.theProfiler.endStartSection("catalyticCracker_do_recipe");
			if(this.worldObj.getTotalWorldTime() % 5 == 0)
				crack();

			this.worldObj.theProfiler.endStartSection("catalyticCracker_send_fluid");
			if(this.worldObj.getTotalWorldTime() % 10 == 0) {
				
				for(DirPos pos : getConPos()) {
					for(int i = 2; i <= 4; i++) {
						if(this.tanks[i].getFill() > 0) this.sendFluid(this.tanks[i], this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
					}
				}
				
				NBTTagCompound data = new NBTTagCompound();

				for(int i = 0; i < 5; i++)
					this.tanks[i].writeToNBT(data, "tank" + i);
				
				INBTPacketReceiver.networkPack(this, data, 50);
			}
			this.worldObj.theProfiler.endSection();
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		for(int i = 0; i < 5; i++)
			this.tanks[i].readFromNBT(nbt, "tank" + i);
	}
	
	private void updateConnections() {
		
		for(DirPos pos : getConPos()) {
			trySubscribe(this.tanks[0].getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			trySubscribe(this.tanks[1].getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	private void crack() {
		
		Pair<FluidStack, FluidStack> quart = CrackingRecipes.getCracking(this.tanks[0].getTankType());
		
		if(quart != null) {
			
			int left = quart.getKey().fill;
			int right = quart.getValue().fill;
			
			for(int i = 0; i < 2; i++) {
				if(this.tanks[0].getFill() >= 100 && this.tanks[1].getFill() >= 200 && hasSpace(left, right)) {
					this.tanks[0].setFill(this.tanks[0].getFill() - 100);
					this.tanks[1].setFill(this.tanks[1].getFill() - 200);
					this.tanks[2].setFill(this.tanks[2].getFill() + left);
					this.tanks[3].setFill(this.tanks[3].getFill() + right);
					this.tanks[4].setFill(this.tanks[4].getFill() + 2); //LPS has the density of WATER not STEAM (1%!)
				}
			}
		}
	}
	
	private boolean hasSpace(int left, int right) {
		return this.tanks[2].getFill() + left <= this.tanks[2].getMaxFill() && this.tanks[3].getFill() + right <= this.tanks[3].getMaxFill() && this.tanks[4].getFill() + 2 <= this.tanks[4].getMaxFill();
	}
	
	private void setupTanks() {
		
		Pair<FluidStack, FluidStack> quart = CrackingRecipes.getCracking(this.tanks[0].getTankType());
		
		if(quart != null) {
			this.tanks[1].setTankType(Fluids.STEAM);
			this.tanks[2].setTankType(quart.getKey().type);
			this.tanks[3].setTankType(quart.getValue().type);
			this.tanks[4].setTankType(Fluids.SPENTSTEAM);
		} else {
			this.tanks[0].setTankType(Fluids.NONE);
			this.tanks[1].setTankType(Fluids.NONE);
			this.tanks[2].setTankType(Fluids.NONE);
			this.tanks[3].setTankType(Fluids.NONE);
			this.tanks[4].setTankType(Fluids.NONE);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		for(int i = 0; i < 5; i++)
			this.tanks[i].readFromNBT(nbt, "tank" + i);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		for(int i = 0; i < 5; i++)
			this.tanks[i].writeToNBT(nbt, "tank" + i);
	}
	
	protected DirPos[] getConPos() {
		
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		return new DirPos[] {
				new DirPos(this.xCoord + dir.offsetX * 4 + rot.offsetX * 1, this.yCoord, this.zCoord + dir.offsetZ * 4 + rot.offsetZ * 1, dir),
				new DirPos(this.xCoord + dir.offsetX * 4 - rot.offsetX * 2, this.yCoord, this.zCoord + dir.offsetZ * 4 - rot.offsetZ * 2, dir),
				new DirPos(this.xCoord - dir.offsetX * 4 + rot.offsetX * 1, this.yCoord, this.zCoord - dir.offsetZ * 4 + rot.offsetZ * 1, dir.getOpposite()),
				new DirPos(this.xCoord - dir.offsetX * 4 - rot.offsetX * 2, this.yCoord, this.zCoord - dir.offsetZ * 4 - rot.offsetZ * 2, dir.getOpposite()),
				new DirPos(this.xCoord + dir.offsetX * 2 + rot.offsetX * 3, this.yCoord, this.zCoord + dir.offsetZ * 2 + rot.offsetZ * 3, rot),
				new DirPos(this.xCoord + dir.offsetX * 2 - rot.offsetX * 4, this.yCoord, this.zCoord + dir.offsetZ * 2 - rot.offsetZ * 4, rot),
				new DirPos(this.xCoord - dir.offsetX * 2 + rot.offsetX * 3, this.yCoord, this.zCoord - dir.offsetZ * 2 + rot.offsetZ * 3, rot.getOpposite()),
				new DirPos(this.xCoord - dir.offsetX * 2 - rot.offsetX * 4, this.yCoord, this.zCoord - dir.offsetZ * 2 - rot.offsetZ * 4, rot.getOpposite())
		};
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(
					this.xCoord - 3,
					this.yCoord,
					this.zCoord - 3,
					this.xCoord + 4,
					this.yCoord + 16,
					this.zCoord + 4
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
		return new FluidTank[] {this.tanks[2], this.tanks[3], this.tanks[4]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {this.tanks[0], this.tanks[1]};
	}

	@Override
	public FluidTank[] getAllTanks() {
		return this.tanks;
	}
}
