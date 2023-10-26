package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.DummyBlockBlast;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEVaultPacket;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityBlastDoor extends TileEntityLockableBase {
	
	public boolean isOpening = false;
	//0: closed, 1: opening/closing, 2:open
	public int state = 0;
	public long sysTime;
	private int timer = 0;
	public boolean redstoned = false;
	
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
    public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			if(!isLocked() && this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord) || this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord + 6, this.zCoord)) {
				
				if(!this.redstoned) {
					tryToggle();
				}
				this.redstoned = true;
				
			} else {
				this.redstoned = false;
			}
	    			
	    	if(this.state != 1) {
	    		this.timer = 0;
	    	} else {
	    		this.timer++;
    			
    			if(this.isOpening) {
    				if(this.timer >= 0) {
    					removeDummy(this.xCoord, this.yCoord + 1, this.zCoord);
    				}
    				if(this.timer >= 20) {
    					removeDummy(this.xCoord, this.yCoord + 2, this.zCoord);
    				}
    				if(this.timer >= 40) {
    					removeDummy(this.xCoord, this.yCoord + 3, this.zCoord);
    				}
    				if(this.timer >= 60) {
    					removeDummy(this.xCoord, this.yCoord + 4, this.zCoord);
    				}
    				if(this.timer >= 80) {
    					removeDummy(this.xCoord, this.yCoord + 5, this.zCoord);
    				}
    			} else {
    				if(this.timer >= 20) {
    					placeDummy(this.xCoord, this.yCoord + 5, this.zCoord);
    				}
    				if(this.timer >= 40) {
    					placeDummy(this.xCoord, this.yCoord + 4, this.zCoord);
    				}
    				if(this.timer >= 60) {
    					placeDummy(this.xCoord, this.yCoord + 3, this.zCoord);
    				}
    				if(this.timer >= 80) {
    					placeDummy(this.xCoord, this.yCoord + 2, this.zCoord);
    				}
    				if(this.timer >= 100) {
    					placeDummy(this.xCoord, this.yCoord + 1, this.zCoord);
    				}
    			}
	    		
	    		if(this.timer >= 100) {
	    			
	    			if(this.isOpening)
	    				finishOpen();
	    			else
	    				finishClose();
	    		}
	    	}
	    	
	    	PacketDispatcher.wrapper.sendToAllAround(new TEVaultPacket(this.xCoord, this.yCoord, this.zCoord, this.isOpening, this.state, 0, 0), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 250));
		}
    }
	
	public void open() {
		if(this.state == 0) {
	    	PacketDispatcher.wrapper.sendToAllAround(new TEVaultPacket(this.xCoord, this.yCoord, this.zCoord, this.isOpening, this.state, 1, 0), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 250));
			this.isOpening = true;
			this.state = 1;

			this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.reactorStart", 0.5F,
					0.75F);
		}
	}
	
	public void finishOpen() {
		this.state = 2;

		this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.reactorStop", 0.5F,
				1.0F);
	}
	
	public void close() {
		if(this.state == 2) {
	    	PacketDispatcher.wrapper.sendToAll(new TEVaultPacket(this.xCoord, this.yCoord, this.zCoord, this.isOpening, this.state, 1, 0));
			this.isOpening = false;
			this.state = 1;

			this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.reactorStart", 0.5F,
					0.75F);
		}
	}
	
	public void finishClose() {
		this.state = 0;

		this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.reactorStop", 0.5F,
				1.0F);
	}
	
	public void openNeigh() {

		TileEntity te0 = this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord);
		TileEntity te1 = this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord);
		TileEntity te2 = this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord + 1);
		TileEntity te3 = this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord - 1);
		
		if(te0 instanceof TileEntityBlastDoor) {
			
			if(((TileEntityBlastDoor)te0).canOpen() && (!((TileEntityBlastDoor)te0).isLocked() || ((TileEntityBlastDoor)te0).lock == this.lock)) {
				((TileEntityBlastDoor)te0).open();
				((TileEntityBlastDoor)te0).openNeigh();
			}
		}
		
		if(te1 instanceof TileEntityBlastDoor) {
			
			if(((TileEntityBlastDoor)te1).canOpen() && (!((TileEntityBlastDoor)te1).isLocked() || ((TileEntityBlastDoor)te1).lock == this.lock)) {
				((TileEntityBlastDoor)te1).open();
				((TileEntityBlastDoor)te1).openNeigh();
			}
		}
		
		if(te2 instanceof TileEntityBlastDoor) {
			
			if(((TileEntityBlastDoor)te2).canOpen() && (!((TileEntityBlastDoor)te2).isLocked() || ((TileEntityBlastDoor)te2).lock == this.lock)) {
				((TileEntityBlastDoor)te2).open();
				((TileEntityBlastDoor)te2).openNeigh();
			}
		}
		
		if(te3 instanceof TileEntityBlastDoor) {
			
			if(((TileEntityBlastDoor)te3).canOpen() && (!((TileEntityBlastDoor)te3).isLocked() || ((TileEntityBlastDoor)te3).lock == this.lock)) {
				((TileEntityBlastDoor)te3).open();
				((TileEntityBlastDoor)te3).openNeigh();
			}
		}
	}
	
	@Override
	public void lock() {
		super.lock();
		lockNeigh();
	}
	
	public void closeNeigh() {

		TileEntity te0 = this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord);
		TileEntity te1 = this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord);
		TileEntity te2 = this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord + 1);
		TileEntity te3 = this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord - 1);
		
		if(te0 instanceof TileEntityBlastDoor) {
			
			if(((TileEntityBlastDoor)te0).canClose() && (!((TileEntityBlastDoor)te0).isLocked() || ((TileEntityBlastDoor)te0).lock == this.lock)) {
				((TileEntityBlastDoor)te0).close();
				((TileEntityBlastDoor)te0).closeNeigh();
			}
		}
		
		if(te1 instanceof TileEntityBlastDoor) {
			
			if(((TileEntityBlastDoor)te1).canClose() && (!((TileEntityBlastDoor)te1).isLocked() || ((TileEntityBlastDoor)te1).lock == this.lock)) {
				((TileEntityBlastDoor)te1).close();
				((TileEntityBlastDoor)te1).closeNeigh();
			}
		}
		
		if(te2 instanceof TileEntityBlastDoor) {
			
			if(((TileEntityBlastDoor)te2).canClose() && (!((TileEntityBlastDoor)te2).isLocked() || ((TileEntityBlastDoor)te2).lock == this.lock)) {
				((TileEntityBlastDoor)te2).close();
				((TileEntityBlastDoor)te2).closeNeigh();
			}
		}
		
		if(te3 instanceof TileEntityBlastDoor) {
			
			if(((TileEntityBlastDoor)te3).canClose() && (!((TileEntityBlastDoor)te3).isLocked() || ((TileEntityBlastDoor)te3).lock == this.lock)) {
				((TileEntityBlastDoor)te3).close();
				((TileEntityBlastDoor)te3).closeNeigh();
			}
		}
	}
	
	public void lockNeigh() {

		TileEntity te0 = this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord);
		TileEntity te1 = this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord);
		TileEntity te2 = this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord + 1);
		TileEntity te3 = this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord - 1);
		
		if(te0 instanceof TileEntityBlastDoor) {
			
			if(!((TileEntityBlastDoor)te0).isLocked()) {
				((TileEntityBlastDoor)te0).setPins(this.lock);
				((TileEntityBlastDoor)te0).lock();
				((TileEntityBlastDoor)te0).setMod(this.lockMod);
			}
		}
		
		if(te1 instanceof TileEntityBlastDoor) {

			if(!((TileEntityBlastDoor)te1).isLocked()) {
				((TileEntityBlastDoor)te1).setPins(this.lock);
				((TileEntityBlastDoor)te1).lock();
				((TileEntityBlastDoor)te1).setMod(this.lockMod);
			}
		}
		
		if(te2 instanceof TileEntityBlastDoor) {

			if(!((TileEntityBlastDoor)te2).isLocked()) {
				((TileEntityBlastDoor)te2).setPins(this.lock);
				((TileEntityBlastDoor)te2).lock();
				((TileEntityBlastDoor)te2).setMod(this.lockMod);
			}
		}
		
		if(te3 instanceof TileEntityBlastDoor) {

			if(!((TileEntityBlastDoor)te3).isLocked()) {
				((TileEntityBlastDoor)te3).setPins(this.lock);
				((TileEntityBlastDoor)te3).lock();
				((TileEntityBlastDoor)te3).setMod(this.lockMod);
			}
		}
	}
	
	public boolean canOpen() {
		return this.state == 0;
	}
	
	public boolean canClose() {
		return this.state == 2;
	}
	
	public void tryToggle() {

		if(canOpen()) {
			open();
			openNeigh();
		} else if(canClose()) {
			close();
			closeNeigh();
		}
	}
	
	public boolean placeDummy(int x, int y, int z) {
		
		if(!this.worldObj.getBlock(x, y, z).isReplaceable(this.worldObj, x, y, z))
			return false;
		
		this.worldObj.setBlock(x, y, z, ModBlocks.dummy_block_blast);
		
		TileEntity te = this.worldObj.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityDummy) {
			TileEntityDummy dummy = (TileEntityDummy)te;
			dummy.targetX = this.xCoord;
			dummy.targetY = this.yCoord;
			dummy.targetZ = this.zCoord;
		}
		
		return true;
	}
	
	public void removeDummy(int x, int y, int z) {
		
		if(this.worldObj.getBlock(x, y, z) == ModBlocks.dummy_block_blast) {
			DummyBlockBlast.safeBreak = true;
			this.worldObj.setBlock(x, y, z, Blocks.air);
			DummyBlockBlast.safeBreak = false;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.isOpening = nbt.getBoolean("isOpening");
		this.state = nbt.getInteger("state");
		this.sysTime = nbt.getLong("sysTime");
		this.timer = nbt.getInteger("timer");
		this.redstoned = nbt.getBoolean("redstoned");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setBoolean("isOpening", this.isOpening);
		nbt.setInteger("state", this.state);
		nbt.setLong("sysTime", this.sysTime);
		nbt.setInteger("timer", this.timer);
		nbt.setBoolean("redstoned", this.redstoned);
	}

}
