package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Heatable;
import com.hbm.inventory.fluid.trait.FT_Heatable.HeatingStep;
import com.hbm.inventory.fluid.trait.FT_Heatable.HeatingType;
import com.hbm.lib.Library;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineHephaestus extends TileEntityLoadedBase implements INBTPacketReceiver, IFluidStandardTransceiver {

	public FluidTank input;
	public FluidTank output;
	public int bufferedHeat;
	
	public float rot;
	public float prevRot;

	public TileEntityMachineHephaestus() {
		this.input = new FluidTank(Fluids.OIL, 24_000);
		this.output = new FluidTank(Fluids.HOTOIL, 24_000);
	}
	
	private int[] heat = new int[10];
	private long fissureScanTime;
	
	@Override
	public void updateEntity() {

		if(!this.worldObj.isRemote) {
			
			setupTanks();
			
			if(this.worldObj.getTotalWorldTime() % 20 == 0) {
				updateConnections();
			}
			
			int height = (int) (this.worldObj.getTotalWorldTime() % 10);
			int range = 7;
			int y = this.yCoord - 1 - height;
			
			this.heat[height] = 0;
			
			if(y >= 0) {
				for(int x = -range; x <= range; x++) {
					for(int z = -range; z <= range; z++) {
						this.heat[height] += heatFromBlock(this.xCoord + x, y, this.zCoord + z);
					}
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			this.input.writeToNBT(data, "i");
			
			heatFluid();
			
			this.output.writeToNBT(data, "o");
			
			if(this.output.getFill() > 0) {
				for(DirPos pos : getConPos()) {
					this.sendFluid(this.output, this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				}
			}
			data.setInteger("heat", getTotalHeat());
			INBTPacketReceiver.networkPack(this, data, 150);
			
		} else {
			
			this.prevRot = this.rot;
			
			if(this.bufferedHeat > 0) {
				this.rot += 0.5F;
				
				if(this.worldObj.rand.nextInt(7) == 0) {
					double x = this.worldObj.rand.nextGaussian() * 2;
					double y = this.worldObj.rand.nextGaussian() * 3;
					double z = this.worldObj.rand.nextGaussian() * 2;
					this.worldObj.spawnParticle("cloud", this.xCoord + 0.5 + x, this.yCoord + 6 + y, this.zCoord + 0.5 + z, 0, 0, 0);
				}
			}
			
			if(this.rot >= 360F) {
				this.prevRot -= 360F;
				this.rot -= 360F;
			}
		}
	}
	
	protected void heatFluid() {
		
		FluidType type = this.input.getTankType();
		
		if(type.hasTrait(FT_Heatable.class)) {
			FT_Heatable trait = type.getTrait(FT_Heatable.class);
			int heat = getTotalHeat();
			HeatingStep step = trait.getFirstStep();
			
			int inputOps = this.input.getFill() / step.amountReq;
			int outputOps = (this.output.getMaxFill() - this.output.getFill()) / step.amountProduced;
			int heatOps = heat / step.heatReq;
			int ops = Math.min(Math.min(inputOps, outputOps), heatOps);

			this.input.setFill(this.input.getFill() - step.amountReq * ops);
			this.output.setFill(this.output.getFill() + step.amountProduced * ops);
			this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
		}
	}
	
	protected void setupTanks() {
		
		FluidType type = this.input.getTankType();
		
		if(type.hasTrait(FT_Heatable.class)) {
			FT_Heatable trait = type.getTrait(FT_Heatable.class);
			
			if(trait.getEfficiency(HeatingType.HEATEXCHANGER) > 0) {
				FluidType outType = trait.getFirstStep().typeProduced;
				this.output.setTankType(outType);
				return;
			}
		}

		this.input.setTankType(Fluids.NONE);
		this.output.setTankType(Fluids.NONE);
	}
	
	protected int heatFromBlock(int x, int y, int z) {
		Block b = this.worldObj.getBlock(x, y, z);
		
		if(b == Blocks.lava || b == Blocks.flowing_lava)	return 5;
		if(b == ModBlocks.volcanic_lava_block)				return 150;
		
		if(b == ModBlocks.ore_volcano) {
			this.fissureScanTime = this.worldObj.getTotalWorldTime();
			return 300;
		}
		
		return 0;
	}
	
	public int getTotalHeat() {
		boolean fissure = this.worldObj.getTotalWorldTime() - this.fissureScanTime < 20;
		int heat = 0;
		
		for(int h : this.heat) {
			heat += h;
		}
		
		if(fissure) {
			heat *= 3;
		}
		
		return heat;
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.input.readFromNBT(nbt, "i");
		this.output.readFromNBT(nbt, "o");
		
		this.bufferedHeat = nbt.getInteger("heat");
	}
	
	private void updateConnections() {
		
		if(this.input.getTankType() == Fluids.NONE) return;
		
		for(DirPos pos : getConPos()) {
			trySubscribe(this.input.getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	private DirPos[] getConPos() {
		
		return new DirPos[] {
				new DirPos(this.xCoord + 2, this.yCoord, this.zCoord, Library.POS_X),
				new DirPos(this.xCoord - 2, this.yCoord, this.zCoord, Library.NEG_X),
				new DirPos(this.xCoord, this.yCoord, this.zCoord + 2, Library.POS_Z),
				new DirPos(this.xCoord, this.yCoord, this.zCoord - 2, Library.NEG_Z),
				new DirPos(this.xCoord + 2, this.yCoord + 11, this.zCoord, Library.POS_X),
				new DirPos(this.xCoord - 2, this.yCoord + 11, this.zCoord, Library.NEG_X),
				new DirPos(this.xCoord, this.yCoord + 11, this.zCoord + 2, Library.POS_Z),
				new DirPos(this.xCoord, this.yCoord + 11, this.zCoord - 2, Library.NEG_Z)
		};
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.input.readFromNBT(nbt, "0");
		this.output.readFromNBT(nbt, "1");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		this.input.writeToNBT(nbt, "0");
		this.output.writeToNBT(nbt, "1");
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {this.input, this.output};
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {this.output};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {this.input};
	}
	
	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN && dir != ForgeDirection.UP && dir != ForgeDirection.DOWN;
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
					this.yCoord + 12,
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
}
