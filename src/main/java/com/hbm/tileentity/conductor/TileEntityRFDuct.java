package com.hbm.tileentity.conductor;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRFDuct extends TileEntity implements IEnergyHandler {

	public ForgeDirection[] connections = new ForgeDirection[6];
	protected EnergyStorage storage;
	public int output;

	public TileEntityRFDuct(int output) {
		this.output = output;
		this.storage = new EnergyStorage(200);

		this.storage.setMaxReceive(output);
		this.storage.setMaxExtract(output);
		this.storage.setMaxTransfer(output);
	}

	@Override
	public void updateEntity() {
		updateConnections();

		if (this.storage.getEnergyStored() > 0) {
			for (int i = 0; i < 6; i++) {

				int targetX = this.xCoord + ForgeDirection.getOrientation(i).offsetX;
				int targetY = this.yCoord + ForgeDirection.getOrientation(i).offsetY;
				int targetZ = this.zCoord + ForgeDirection.getOrientation(i).offsetZ;

				TileEntity tile = this.worldObj.getTileEntity(targetX, targetY, targetZ);
				if (tile instanceof IEnergyReceiver) {
					int maxExtract = this.storage.getMaxExtract();
					int maxAvailable = this.storage.extractEnergy(maxExtract, true);
					int energyTransferred = ((IEnergyReceiver) tile)
							.receiveEnergy(ForgeDirection.getOrientation(i).getOpposite(), maxAvailable, false);

					this.storage.extractEnergy(energyTransferred, false);
				}
			}
		}

	}

	public void updateConnections() {
		if (this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord) instanceof IEnergyConnection)
			this.connections[0] = ForgeDirection.UP;
		else
			this.connections[0] = null;

		if (this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord) instanceof IEnergyConnection)
			this.connections[1] = ForgeDirection.DOWN;
		else
			this.connections[1] = null;

		if (this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord - 1) instanceof IEnergyConnection)
			this.connections[2] = ForgeDirection.NORTH;
		else
			this.connections[2] = null;

		if (this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord + 1) instanceof IEnergyConnection)
			this.connections[3] = ForgeDirection.SOUTH;
		else
			this.connections[3] = null;

		if (this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord) instanceof IEnergyConnection)
			this.connections[4] = ForgeDirection.EAST;
		else
			this.connections[4] = null;

		if (this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord) instanceof IEnergyConnection)
			this.connections[5] = ForgeDirection.WEST;
		else
			this.connections[5] = null;
	}

	public boolean onlyOneOpposite(ForgeDirection[] directions) {
		ForgeDirection mainDirection = null;
		boolean isOpposite = false;

		for (ForgeDirection direction : directions) {

			if (mainDirection == null && direction != null)
				mainDirection = direction;

			if (direction != null && mainDirection != direction) {
				if (!isOpposite(mainDirection, direction))
					return false;
				else
					isOpposite = true;
			}
		}

		return isOpposite;
	}

	public boolean isOpposite(ForgeDirection firstDirection, ForgeDirection secondDirection) {

		if ((firstDirection.equals(ForgeDirection.NORTH) && secondDirection.equals(ForgeDirection.SOUTH))
				|| firstDirection.equals(ForgeDirection.SOUTH) && secondDirection.equals(ForgeDirection.NORTH))
			return true;

		if ((firstDirection.equals(ForgeDirection.EAST) && secondDirection.equals(ForgeDirection.WEST))
				|| firstDirection.equals(ForgeDirection.WEST) && secondDirection.equals(ForgeDirection.EAST))
			return true;

		if ((firstDirection.equals(ForgeDirection.UP) && secondDirection.equals(ForgeDirection.DOWN))
				|| firstDirection.equals(ForgeDirection.DOWN) && secondDirection.equals(ForgeDirection.UP))
			return true;

		return false;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return this.storage.receiveEnergy(Math.min(this.output, maxReceive), simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {

		return this.storage.extractEnergy(this.storage.getMaxExtract(), simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return this.storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return this.storage.getMaxEnergyStored();
	}

}
