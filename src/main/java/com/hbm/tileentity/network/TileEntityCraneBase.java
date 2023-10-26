package com.hbm.tileentity.network;

import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityCraneBase extends TileEntityMachineBase {
	
	public TileEntityCraneBase(int scount) {
		super(scount);
	}

	// extension to the meta system
	// for compatibility purposes, normal meta values are still used by default
	private ForgeDirection outputOverride = ForgeDirection.UNKNOWN;

	// for extra stability in case the screwdriver action doesn't get synced to
	// other clients
	private ForgeDirection cachedOutputOverride = ForgeDirection.UNKNOWN;

	@Override
	public void updateEntity() {
		if(hasWorldObj() && this.worldObj.isRemote) {
			if(this.cachedOutputOverride != this.outputOverride) {
				this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
				this.cachedOutputOverride = this.outputOverride;
			}
		}
	}

	public ForgeDirection getInputSide() {
		return ForgeDirection.getOrientation(getBlockMetadata());
	}

	public ForgeDirection getOutputSide() {
		ForgeDirection override = getOutputOverride();
		return override != ForgeDirection.UNKNOWN ? override : ForgeDirection.getOrientation(getBlockMetadata()).getOpposite();
	}

	public ForgeDirection getOutputOverride() {
		return this.outputOverride;
	}

	public void setOutputOverride(ForgeDirection direction) {
		ForgeDirection oldSide = getOutputSide();
		if(oldSide == direction) direction = direction.getOpposite();

		this.outputOverride = direction;

		if(direction == getInputSide())
			setInput(oldSide);
		else
			onBlockChanged();
	}

	public void setInput(ForgeDirection direction) {
		this.outputOverride = getOutputSide(); // save the current output, if it isn't saved yet

		ForgeDirection oldSide = getInputSide();
		if(oldSide == direction) direction = direction.getOpposite();

		boolean needSwapOutput = direction == getOutputSide();
		this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, direction.ordinal(), needSwapOutput ? 4 : 3);

		if(needSwapOutput)
			setOutputOverride(oldSide);
	}

	protected void onBlockChanged() {
		if(!hasWorldObj()) return;
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		this.worldObj.notifyBlockChange(this.xCoord, this.yCoord, this.zCoord, getBlockType());
		markDirty();
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.func_148857_g());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if(nbt.hasKey("CraneOutputOverride", Constants.NBT.TAG_BYTE))
			this.outputOverride = ForgeDirection.getOrientation(nbt.getByte("CraneOutputOverride"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setByte("CraneOutputOverride", (byte) this.outputOverride.ordinal());
	}
}
