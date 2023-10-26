package com.hbm.tileentity.machine;

import java.util.HashSet;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
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

public abstract class TileEntityMachinePumpBase extends TileEntityLoadedBase implements IFluidStandardTransceiver, INBTPacketReceiver {
	
	public static final HashSet<Block> validBlocks = new HashSet<>();
	
	static {
		TileEntityMachinePumpBase.validBlocks.add(Blocks.grass);
		TileEntityMachinePumpBase.validBlocks.add(Blocks.dirt);
		TileEntityMachinePumpBase.validBlocks.add(Blocks.sand);
		TileEntityMachinePumpBase.validBlocks.add(Blocks.mycelium);
		TileEntityMachinePumpBase.validBlocks.add(ModBlocks.waste_earth);
		TileEntityMachinePumpBase.validBlocks.add(ModBlocks.dirt_dead);
		TileEntityMachinePumpBase.validBlocks.add(ModBlocks.dirt_oily);
		TileEntityMachinePumpBase.validBlocks.add(ModBlocks.sand_dirty);
		TileEntityMachinePumpBase.validBlocks.add(ModBlocks.sand_dirty_red);
	}
	
	public FluidTank water;

	public boolean isOn = false;
	public float rotor;
	public float lastRotor;
	public boolean onGround = false;
	public int groundCheckDelay = 0;
	
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			for(DirPos pos : getConPos()) {
				if(this.water.getFill() > 0) this.sendFluid(this.water, this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
			
			if(this.groundCheckDelay > 0) {
				this.groundCheckDelay--;
			} else {
				this.onGround = checkGround();
			}
			
			this.isOn = false;
			if(canOperate() && this.yCoord <= 70 && this.onGround) {
				this.isOn = true;
				operate();
			}
			
			NBTTagCompound data = getSync();
			INBTPacketReceiver.networkPack(this, data, 150);
			
		} else {
			
			this.lastRotor = this.rotor;
			if(this.isOn) this.rotor += 10F;
			
			if(this.rotor >= 360F) {
				this.rotor -= 360F;
				this.lastRotor -= 360F;
				
				MainRegistry.proxy.playSoundClient(this.xCoord, this.yCoord, this.zCoord, "hbm:block.steamEngineOperate", 0.5F, 0.75F);
				MainRegistry.proxy.playSoundClient(this.xCoord, this.yCoord, this.zCoord, "game.neutral.swim.splash", 1F, 0.5F);
			}
		}
	}
	
	protected boolean checkGround() {
		
		if(this.worldObj.provider.hasNoSky) return false;
		
		int validBlocks = 0;
		int invalidBlocks = 0;
		
		for(int x = -1; x <= 1; x++) {
			for(int y = -1; y >= -4; y--) {
				for(int z = -1; z <= 1; z++) {
					
					Block b = this.worldObj.getBlock(this.xCoord + x, this.yCoord + y, this.zCoord + z);
					
					if(y == -1 && !b.isNormalCube()) return false; // first layer has to be full solid
					
					if(TileEntityMachinePumpBase.validBlocks.contains(b)) validBlocks++;
					else invalidBlocks ++;
				}
			}
		}
		
		return validBlocks >= invalidBlocks; // valid block count has to be at least 50%
	}
	
	protected NBTTagCompound getSync() {
		NBTTagCompound data = new NBTTagCompound();
		data.setBoolean("isOn", this.isOn);
		data.setBoolean("onGround", this.onGround);
		this.water.writeToNBT(data, "w");
		return data;
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.isOn = nbt.getBoolean("isOn");
		this.onGround = nbt.getBoolean("onGround");
		this.water.readFromNBT(nbt, "w");
	}

	protected abstract boolean canOperate();
	protected abstract void operate();
	
	protected DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(this.xCoord + 2, this.yCoord, this.zCoord, Library.POS_X),
				new DirPos(this.xCoord - 2, this.yCoord, this.zCoord, Library.NEG_X),
				new DirPos(this.xCoord, this.yCoord, this.zCoord + 2, Library.POS_Z),
				new DirPos(this.xCoord, this.yCoord, this.zCoord - 2, Library.NEG_Z)
		};
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {this.water};
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {this.water};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[0];
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
					this.yCoord + 5,
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
}
