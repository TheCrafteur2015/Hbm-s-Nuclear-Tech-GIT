package com.hbm.tileentity.conductor;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.calc.UnionOfTileEntitiesAndBooleansForFluids;
import com.hbm.interfaces.IFluidDuct;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.Library;
import com.hbm.tileentity.network.TileEntityPipeBaseNT;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFluidDuctSimple extends TileEntity implements IFluidDuct {

	protected FluidType type = Fluids.NONE;
	public List<UnionOfTileEntitiesAndBooleansForFluids> uoteab = new ArrayList<>();
	
	
	public ForgeDirection[] connections = new ForgeDirection[6];

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
		this.type = Fluids.fromID(nbt.getInteger("fluid"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("fluid", this.type.getID());
	}
	
	@Override
	public boolean setType(FluidType type) {

		if(this.type == type)
			return true;
		
		this.type = type;
		markDirty();
		
		if(this.worldObj instanceof WorldServer) {
			WorldServer world = (WorldServer) this.worldObj;
			world.getPlayerManager().markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
		
		return true;
	}

	@Override
	public FluidType getType() {
		return this.type;
	}
	
	@Override
	public void updateEntity() {
		/*this.updateConnections();

		if(lastType != type) {
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			lastType = type;
		}*/

		if(getBlockType() == ModBlocks.fluid_duct) this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, ModBlocks.fluid_duct_neo);
		if(getBlockType() == ModBlocks.fluid_duct_solid) this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, ModBlocks.fluid_duct_paintable);
		
		TileEntity tile = this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord);
		if(tile instanceof TileEntityPipeBaseNT) {
			((TileEntityPipeBaseNT) tile).setType(this.type);
		}
	}
	
	public void updateConnections() {
		if(Library.checkFluidConnectables(this.worldObj, this.xCoord, this.yCoord + 1, this.zCoord, this.type)) this.connections[0] = ForgeDirection.UP;
		else this.connections[0] = null;
		if(Library.checkFluidConnectables(this.worldObj, this.xCoord, this.yCoord - 1, this.zCoord, this.type)) this.connections[1] = ForgeDirection.DOWN;
		else this.connections[1] = null;
		if(Library.checkFluidConnectables(this.worldObj, this.xCoord, this.yCoord, this.zCoord - 1, this.type)) this.connections[2] = ForgeDirection.NORTH;
		else this.connections[2] = null;
		if(Library.checkFluidConnectables(this.worldObj, this.xCoord + 1, this.yCoord, this.zCoord, this.type)) this.connections[3] = ForgeDirection.EAST;
		else this.connections[3] = null;
		if(Library.checkFluidConnectables(this.worldObj, this.xCoord, this.yCoord, this.zCoord + 1, this.type)) this.connections[4] = ForgeDirection.SOUTH;
		else this.connections[4] = null;
		if(Library.checkFluidConnectables(this.worldObj, this.xCoord - 1, this.yCoord, this.zCoord, this.type)) this.connections[5] = ForgeDirection.WEST;
		else this.connections[5] = null;
	}
}
