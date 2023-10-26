package com.hbm.tileentity.machine;

import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerOilburner;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Leaded;
import com.hbm.inventory.gui.GUIOilburner;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachinePolluting;
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

public class TileEntityHeaterOilburner extends TileEntityMachinePolluting implements IGUIProvider, IFluidStandardTransceiver, IHeatSource, IControlReceiver {
	
	public boolean isOn = false;
	public FluidTank tank;
	public int setting = 1;

	public int heatEnergy;
	public static final int maxHeatEnergy = 100_000;

	public TileEntityHeaterOilburner() {
		super(3, 100);
		this.tank = new FluidTank(Fluids.HEATINGOIL, 16000);
	}

	@Override
	public String getName() {
		return "container.heaterOilburner";
	}
	
	public DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(this.xCoord + 2, this.yCoord, this.zCoord, Library.POS_X),
				new DirPos(this.xCoord - 2, this.yCoord, this.zCoord, Library.NEG_X),
				new DirPos(this.xCoord, this.yCoord, this.zCoord + 2, Library.POS_Z),
				new DirPos(this.xCoord, this.yCoord, this.zCoord - 2, Library.NEG_Z)
		};
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			this.tank.loadTank(0, 1, this.slots);
			this.tank.setType(2, this.slots);

			for(DirPos pos : getConPos()) {
				trySubscribe(this.tank.getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				sendSmoke(pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
			
			boolean shouldCool = true;
			
			if(this.isOn && this.heatEnergy < TileEntityHeaterOilburner.maxHeatEnergy) {
				
				if(this.tank.getTankType().hasTrait(FT_Flammable.class)) {
					FT_Flammable type = this.tank.getTankType().getTrait(FT_Flammable.class);
					
					int burnRate = this.setting;
					int toBurn = Math.min(burnRate, this.tank.getFill());
					
					this.tank.setFill(this.tank.getFill() - toBurn);
					
					int heat = (int)(type.getHeatEnergy() / 1000);
					
					this.heatEnergy += heat * toBurn;

					if(this.worldObj.getTotalWorldTime() % 20 == 0 && toBurn > 0) {
						pollute(PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND * burnRate * 0.5F);
						if(this.tank.getTankType().hasTrait(FT_Leaded.class)) pollute(PollutionType.HEAVYMETAL, PollutionHandler.HEAVY_METAL_PER_SECOND * burnRate * 0.5F);
					}
					
					shouldCool = false;
				}
			}
			
			if(this.heatEnergy >= TileEntityHeaterOilburner.maxHeatEnergy)
				shouldCool = false;
			
			if(shouldCool)
				this.heatEnergy = Math.max(this.heatEnergy - Math.max(this.heatEnergy / 1000, 1), 0);
			
			NBTTagCompound data = new NBTTagCompound();
			this.tank.writeToNBT(data, "tank");
			data.setBoolean("isOn", this.isOn);
			data.setInteger("h", this.heatEnergy);
			data.setByte("s", (byte) this.setting);
			networkPack(data, 25);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.tank.readFromNBT(nbt, "tank");
		this.isOn = nbt.getBoolean("isOn");
		this.heatEnergy = nbt.getInteger("h");
		this.setting = nbt.getByte("s");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.tank.readFromNBT(nbt, "tank");
		this.isOn = nbt.getBoolean("isOn");
		this.heatEnergy = nbt.getInteger("heatEnergy");
		this.setting = nbt.getByte("setting");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.tank.writeToNBT(nbt, "tank");
		nbt.setBoolean("isOn", this.isOn);
		nbt.setInteger("heatEnergy", this.heatEnergy);
		nbt.setByte("setting", (byte) this.setting);
	}
	
	public void toggleSetting() {
		this.setting++;
		
		if(this.setting > 10)
			this.setting = 1;
	}

	@Override
	public FluidTank[] getReceivingTanks()  {
		return new FluidTank[] { this.tank };
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerOilburner(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIOilburner(player.inventory, this);
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
	public boolean hasPermission(EntityPlayer player) {
		return player.getDistanceSq(this.xCoord, this.yCoord, this.zCoord) <= 256;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("toggle")) {
			this.isOn = !this.isOn;
		}
		markChanged();
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
					this.yCoord + 2,
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
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { this.tank };
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return getSmokeTanks();
	}
}
