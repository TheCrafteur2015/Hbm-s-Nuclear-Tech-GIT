package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.DummyBlockVault;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEVaultPacket;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityVaultDoor extends TileEntityLockableBase {
	
	public boolean isOpening = false;
	//0: closed, 1: opening/closing, 2:open
	public int state = 0;
	public long sysTime;
	private int timer = 0;
	public int type;
	public static final int maxTypes = 7;
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
			
			if(!isLocked()) {
				boolean flagX = false;
				boolean flagZ = false;

				for(int x = this.xCoord - 2; x <= this.xCoord + 2; x++)
					for(int y = this.yCoord; y <= this.yCoord + 5; y++)
						if(this.worldObj.isBlockIndirectlyGettingPowered(x, y, this.zCoord)) {
							flagX = true;
							break;
						}
				
				for(int z = this.zCoord - 2; z <= this.zCoord + 2; z++)
					for(int y = this.yCoord; y <= this.yCoord + 5; y++)
						if(this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, y, z)) {
							flagZ = true;
							break;
						}

				if(this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord) == 2 || this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord) == 3) {
					if(flagX) {
						
						if(!this.redstoned) {
							tryToggle();
						}
						
						this.redstoned = true;
					} else {
						
						this.redstoned = false;
					}
				}
				if(this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord) == 4 || this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord) == 5) {
					if(flagZ) {
						
						if(!this.redstoned) {
							tryToggle();
						}
						
						this.redstoned = true;
					} else {
						
						this.redstoned = false;
					}
				}
			}

	    	if(this.isOpening && this.state == 1) {
				
	    		if(this.timer == 0)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultScrapeNew", 1.0F, 1.0F);
	    		if(this.timer == 45)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		if(this.timer == 55)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		if(this.timer == 65)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		if(this.timer == 75)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		if(this.timer == 85)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		if(this.timer == 95)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		if(this.timer == 105)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		if(this.timer == 115)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    	}
	    	if(!this.isOpening && this.state == 1) {

	    		if(this.timer == 0)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		if(this.timer == 10)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		if(this.timer == 20)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		if(this.timer == 30)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		if(this.timer == 40)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		if(this.timer == 50)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		if(this.timer == 60)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		if(this.timer == 70)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultThudNew", 1.0F, 1.0F);
	    		
	    		if(this.timer == 80)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.vaultScrapeNew", 1.0F, 1.0F);
	    	}	
	    			
	    	if(this.state != 1) {
	    		this.timer = 0;
	    	} else {
	    		this.timer++;
	    		
	    		if(this.timer >= 120) {
	    			
	    			if(this.isOpening)
	    				finishOpen();
	    			else
	    				finishClose();
	    		}
	    	}
	    	
	    	PacketDispatcher.wrapper.sendToAllAround(new TEVaultPacket(this.xCoord, this.yCoord, this.zCoord, this.isOpening, this.state, 0, this.type), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 250));
		}
    }
	
	public void open() {
		if(this.state == 0) {
	    	PacketDispatcher.wrapper.sendToAllAround(new TEVaultPacket(this.xCoord, this.yCoord, this.zCoord, this.isOpening, this.state, 1, this.type), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 250));
			this.isOpening = true;
			this.state = 1;
			
			openHatch();
		}
	}
	
	public void finishOpen() {
		this.state = 2;
	}
	
	public void close() {
		if(this.state == 2) {
	    	PacketDispatcher.wrapper.sendToAllAround(new TEVaultPacket(this.xCoord, this.yCoord, this.zCoord, this.isOpening, this.state, 1, this.type), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 250));
			this.isOpening = false;
			this.state = 1;
			
			closeHatch();
		}
	}
	
	public void finishClose() {
		this.state = 0;
	}
	
	public boolean canOpen() {
		return this.state == 0;
	}
	
	public boolean canClose() {
		return this.state == 2 && isHatchFree();
	}
	
	public void tryToggle() {

		if(canOpen())
			open();
		else if(canClose())
			close();
	}
	
	public boolean placeDummy(int x, int y, int z) {
		
		if(!this.worldObj.getBlock(x, y, z).isReplaceable(this.worldObj, x, y, z))
			return false;
		
		this.worldObj.setBlock(x, y, z, ModBlocks.dummy_block_vault);
		
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
		
		if(this.worldObj.getBlock(x, y, z) == ModBlocks.dummy_block_vault) {
			DummyBlockVault.safeBreak = true;
			this.worldObj.setBlock(x, y, z, Blocks.air);
			DummyBlockVault.safeBreak = false;
		}
	}
	
	private boolean isHatchFree() {

		if(getBlockMetadata() == 2 || getBlockMetadata() == 3)
			return checkNS();
		else if(getBlockMetadata() == 4 || getBlockMetadata() == 5)
			return checkEW();
		else
			return true;
	}
	
	private void closeHatch() {

		if(getBlockMetadata() == 2 || getBlockMetadata() == 3)
			fillNS();
		else if(getBlockMetadata() == 4 || getBlockMetadata() == 5)
			fillEW();
	}
	
	private void openHatch() {

		if(getBlockMetadata() == 2 || getBlockMetadata() == 3)
			removeNS();
		else if(getBlockMetadata() == 4 || getBlockMetadata() == 5)
			removeEW();
	}
	
	private boolean checkNS() {
		return this.worldObj.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord).isReplaceable(this.worldObj, this.xCoord - 1, this.yCoord + 1, this.zCoord) &&
				this.worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord).isReplaceable(this.worldObj, this.xCoord, this.yCoord + 1, this.zCoord) &&
				this.worldObj.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord).isReplaceable(this.worldObj, this.xCoord + 1, this.yCoord + 1, this.zCoord) &&
				this.worldObj.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord).isReplaceable(this.worldObj, this.xCoord - 1, this.yCoord + 2, this.zCoord) &&
				this.worldObj.getBlock(this.xCoord, this.yCoord + 2, this.zCoord).isReplaceable(this.worldObj, this.xCoord, this.yCoord + 2, this.zCoord) &&
				this.worldObj.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord).isReplaceable(this.worldObj, this.xCoord + 1, this.yCoord + 2, this.zCoord) &&
				this.worldObj.getBlock(this.xCoord - 1, this.yCoord + 3, this.zCoord).isReplaceable(this.worldObj, this.xCoord - 1, this.yCoord + 3, this.zCoord) &&
				this.worldObj.getBlock(this.xCoord, this.yCoord + 3, this.zCoord).isReplaceable(this.worldObj, this.xCoord, this.yCoord + 3, this.zCoord) &&
				this.worldObj.getBlock(this.xCoord + 1, this.yCoord + 3, this.zCoord).isReplaceable(this.worldObj, this.xCoord + 1, this.yCoord + 3, this.zCoord);
	}
	
	private boolean checkEW() {
		return this.worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord - 1).isReplaceable(this.worldObj, this.xCoord, this.yCoord + 1, this.zCoord -1) &&
				this.worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord).isReplaceable(this.worldObj, this.xCoord, this.yCoord, this.zCoord) &&
				this.worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord + 1).isReplaceable(this.worldObj, this.xCoord, this.yCoord + 1, this.zCoord + 1) &&
				this.worldObj.getBlock(this.xCoord, this.yCoord + 2, this.zCoord - 1).isReplaceable(this.worldObj, this.xCoord, this.yCoord + 2, this.zCoord - 1) &&
				this.worldObj.getBlock(this.xCoord, this.yCoord + 2, this.zCoord).isReplaceable(this.worldObj, this.xCoord, this.yCoord + 2, this.zCoord) &&
				this.worldObj.getBlock(this.xCoord, this.yCoord + 2, this.zCoord + 1).isReplaceable(this.worldObj, this.xCoord, this.yCoord + 2, this.zCoord + 1) &&
				this.worldObj.getBlock(this.xCoord, this.yCoord + 3, this.zCoord - 1).isReplaceable(this.worldObj, this.xCoord, this.yCoord + 3, this.zCoord - 1) &&
				this.worldObj.getBlock(this.xCoord, this.yCoord + 3, this.zCoord).isReplaceable(this.worldObj, this.xCoord, this.yCoord + 3, this.zCoord) &&
				this.worldObj.getBlock(this.xCoord, this.yCoord + 3, this.zCoord + 1).isReplaceable(this.worldObj, this.xCoord, this.yCoord + 3, this.zCoord + 1);
	}
	
	private void fillNS() {

		placeDummy(this.xCoord - 1, this.yCoord + 1, this.zCoord);
		placeDummy(this.xCoord - 1, this.yCoord + 2, this.zCoord);
		placeDummy(this.xCoord - 1, this.yCoord + 3, this.zCoord);
		placeDummy(this.xCoord, this.yCoord + 1, this.zCoord);
		placeDummy(this.xCoord, this.yCoord + 2, this.zCoord);
		placeDummy(this.xCoord, this.yCoord + 3, this.zCoord);
		placeDummy(this.xCoord + 1, this.yCoord + 1, this.zCoord);
		placeDummy(this.xCoord + 1, this.yCoord + 2, this.zCoord);
		placeDummy(this.xCoord + 1, this.yCoord + 3, this.zCoord);
	}
	
	private void fillEW() {

		placeDummy(this.xCoord, this.yCoord + 1, this.zCoord - 1);
		placeDummy(this.xCoord, this.yCoord + 2, this.zCoord - 1);
		placeDummy(this.xCoord, this.yCoord + 3, this.zCoord - 1);
		placeDummy(this.xCoord, this.yCoord + 1, this.zCoord);
		placeDummy(this.xCoord, this.yCoord + 2, this.zCoord);
		placeDummy(this.xCoord, this.yCoord + 3, this.zCoord);
		placeDummy(this.xCoord, this.yCoord + 1, this.zCoord + 1);
		placeDummy(this.xCoord, this.yCoord + 2, this.zCoord + 1);
		placeDummy(this.xCoord, this.yCoord + 3, this.zCoord + 1);
	}
	
	private void removeNS() {

		removeDummy(this.xCoord - 1, this.yCoord + 1, this.zCoord);
		removeDummy(this.xCoord - 1, this.yCoord + 2, this.zCoord);
		removeDummy(this.xCoord - 1, this.yCoord + 3, this.zCoord);
		removeDummy(this.xCoord, this.yCoord + 1, this.zCoord);
		removeDummy(this.xCoord, this.yCoord + 2, this.zCoord);
		removeDummy(this.xCoord, this.yCoord + 3, this.zCoord);
		removeDummy(this.xCoord + 1, this.yCoord + 1, this.zCoord);
		removeDummy(this.xCoord + 1, this.yCoord + 2, this.zCoord);
		removeDummy(this.xCoord + 1, this.yCoord + 3, this.zCoord);
	}
	
	private void removeEW() {

		removeDummy(this.xCoord, this.yCoord + 1, this.zCoord - 1);
		removeDummy(this.xCoord, this.yCoord + 2, this.zCoord - 1);
		removeDummy(this.xCoord, this.yCoord + 3, this.zCoord - 1);
		removeDummy(this.xCoord, this.yCoord + 1, this.zCoord);
		removeDummy(this.xCoord, this.yCoord + 2, this.zCoord);
		removeDummy(this.xCoord, this.yCoord + 3, this.zCoord);
		removeDummy(this.xCoord, this.yCoord + 1, this.zCoord + 1);
		removeDummy(this.xCoord, this.yCoord + 2, this.zCoord + 1);
		removeDummy(this.xCoord, this.yCoord + 3, this.zCoord + 1);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.isOpening = nbt.getBoolean("isOpening");
		this.state = nbt.getInteger("state");
		this.sysTime = nbt.getLong("sysTime");
		this.timer = nbt.getInteger("timer");
		this.type = nbt.getInteger("type");
		this.redstoned = nbt.getBoolean("redstoned");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setBoolean("isOpening", this.isOpening);
		nbt.setInteger("state", this.state);
		nbt.setLong("sysTime", this.sysTime);
		nbt.setInteger("timer", this.timer);
		nbt.setInteger("type", this.type);
		nbt.setBoolean("redstoned", this.redstoned);
	}
}
