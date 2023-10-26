package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerHeaterHeatex;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Coolable;
import com.hbm.inventory.fluid.trait.FT_Coolable.CoolingType;
import com.hbm.inventory.gui.GUIHeaterHeatex;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import api.hbm.tile.IHeatSource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityHeaterHeatex extends TileEntityMachineBase implements IHeatSource, INBTPacketReceiver, IFluidStandardTransceiver, IGUIProvider, IControlReceiver {
	
	public FluidTank[] tanks;
	public int amountToCool = 1;
	public int tickDelay = 1;
	public int heatEnergy;
	
	public TileEntityHeaterHeatex() {
		super(1);
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.COOLANT_HOT, 24_000, 0);
		this.tanks[1] = new FluidTank(Fluids.COOLANT, 24_000, 1);
	}

	@Override
	public String getName() {
		return "container.heaterHeatex";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			this.tanks[0].setType(0, this.slots);
			setupTanks();
			updateConnections();
			
			this.heatEnergy *= 0.999;
			
			NBTTagCompound data = new NBTTagCompound();
			this.tanks[0].writeToNBT(data, "0");
			tryConvert();
			this.tanks[1].writeToNBT(data, "1");
			data.setInteger("heat", this.heatEnergy);
			data.setInteger("toCool", this.amountToCool);
			data.setInteger("delay", this.tickDelay);
			INBTPacketReceiver.networkPack(this, data, 25);
			
			for(DirPos pos : getConPos()) {
				if(this.tanks[1].getFill() > 0) this.sendFluid(this.tanks[1], this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.tanks[0].readFromNBT(nbt, "0");
		this.tanks[1].readFromNBT(nbt, "1");
		this.heatEnergy = nbt.getInteger("heat");
		this.amountToCool = nbt.getInteger("toCool");
		this.tickDelay = nbt.getInteger("delay");
	}
	
	protected void setupTanks() {
		
		if(this.tanks[0].getTankType().hasTrait(FT_Coolable.class)) {
			FT_Coolable trait = this.tanks[0].getTankType().getTrait(FT_Coolable.class);
			if(trait.getEfficiency(CoolingType.HEATEXCHANGER) > 0) {
				this.tanks[1].setTankType(trait.coolsTo);
				return;
			}
		}

		this.tanks[0].setTankType(Fluids.NONE);
		this.tanks[1].setTankType(Fluids.NONE);
	}
	
	protected void updateConnections() {
		
		for(DirPos pos : getConPos()) {
			trySubscribe(this.tanks[0].getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	protected void tryConvert() {
		
		if(!this.tanks[0].getTankType().hasTrait(FT_Coolable.class)) return;
		if(this.tickDelay < 1) this.tickDelay = 1;
		if(this.worldObj.getTotalWorldTime() % this.tickDelay != 0) return;
		
		FT_Coolable trait = this.tanks[0].getTankType().getTrait(FT_Coolable.class);
		
		int inputOps = this.tanks[0].getFill() / trait.amountReq;
		int outputOps = (this.tanks[1].getMaxFill() - this.tanks[1].getFill()) / trait.amountProduced;
		int opCap = this.amountToCool;
		
		int ops = Math.min(inputOps, Math.min(outputOps, opCap));
		this.tanks[0].setFill(this.tanks[0].getFill() - trait.amountReq * ops);
		this.tanks[1].setFill(this.tanks[1].getFill() + trait.amountProduced * ops);
		this.heatEnergy += trait.heatEnergy * ops * trait.getEfficiency(CoolingType.HEATEXCHANGER);
		markChanged();
	}
	
	private DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		return new DirPos[] {
				new DirPos(this.xCoord + dir.offsetX * 2 + rot.offsetX, this.yCoord, this.zCoord + dir.offsetZ * 2 + rot.offsetZ, dir),
				new DirPos(this.xCoord + dir.offsetX * 2 - rot.offsetX, this.yCoord, this.zCoord + dir.offsetZ * 2 - rot.offsetZ, dir),
				new DirPos(this.xCoord - dir.offsetX * 2 + rot.offsetX, this.yCoord, this.zCoord - dir.offsetZ * 2 + rot.offsetZ, dir.getOpposite()),
				new DirPos(this.xCoord - dir.offsetX * 2 - rot.offsetX, this.yCoord, this.zCoord - dir.offsetZ * 2 - rot.offsetZ, dir.getOpposite())
		};
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.tanks[0].readFromNBT(nbt, "0");
		this.tanks[1].readFromNBT(nbt, "1");
		this.heatEnergy = nbt.getInteger("heatEnergy");
		this.amountToCool = nbt.getInteger("toCool");
		this.tickDelay = nbt.getInteger("delay");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		this.tanks[0].writeToNBT(nbt, "0");
		this.tanks[1].writeToNBT(nbt, "1");
		nbt.setInteger("heatEnergy", this.heatEnergy);
		nbt.setInteger("toCool", this.amountToCool);
		nbt.setInteger("delay", this.tickDelay);
	}

	@Override
	public int getHeatStored() {
		return this.heatEnergy;
	}

	@Override
	public void useUpHeat(int heat) {
		this.heatEnergy = Math.max(0, this.heatEnergy - heat);
	}

	@Override
	public FluidTank[] getAllTanks() {
		return this.tanks;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {this.tanks[1]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {this.tanks[0]};
	}

	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		ForgeDirection facing = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
		return dir == facing || dir == facing.getOpposite();
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerHeaterHeatex(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIHeaterHeatex(player.inventory, this);
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
					this.yCoord + 1,
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
	public boolean hasPermission(EntityPlayer player) {
		return player.getDistance(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5) < 16;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("toCool")) this.amountToCool = Math.max(data.getInteger("toCool"), 1);
		if(data.hasKey("delay")) this.tickDelay = Math.max(data.getInteger("delay"), 1);
		
		markChanged();
	}
}
