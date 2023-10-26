package com.hbm.tileentity.machine;

import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Heatable;
import com.hbm.inventory.fluid.trait.FT_Heatable.HeatingStep;
import com.hbm.inventory.fluid.trait.FT_Heatable.HeatingType;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.saveddata.TomSaveData;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import api.hbm.tile.IHeatSource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.EnumSkyBlock;

public class TileEntityHeatBoilerIndustrial extends TileEntityLoadedBase implements INBTPacketReceiver, IFluidStandardTransceiver, IConfigurableMachine {

	public int heat;
	public FluidTank[] tanks;
	public boolean isOn;
	
	private AudioWrapper audio;
	private int audioTime;
	
	/* CONFIGURABLE */
	public static int maxHeat = 12_800_000;
	public static double diffusion = 0.1D;

	public TileEntityHeatBoilerIndustrial() {
		this.tanks = new FluidTank[2];

		this.tanks[0] = new FluidTank(Fluids.WATER, 64_000, 0);
		this.tanks[1] = new FluidTank(Fluids.STEAM, 64_000 * 100, 1);
	}
	
	@Override
	public void updateEntity() {

		if(!this.worldObj.isRemote) {
			
			NBTTagCompound data = new NBTTagCompound();
			setupTanks();
			updateConnections();
			tryPullHeat();
			int lastHeat = this.heat;
			
			int light = this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, this.xCoord, this.yCoord, this.zCoord);
			if(light > 7 && TomSaveData.forWorld(this.worldObj).fire > 1e-5) {
				this.heat += ((TileEntityHeatBoilerIndustrial.maxHeat - this.heat) * 0.000005D); //constantly heat up 0.0005% of the remaining heat buffer for rampant but diminishing heating
			}
			
			data.setInteger("heat", lastHeat);

			this.tanks[0].writeToNBT(data, "0");
			this.isOn = false;
			tryConvert();
			this.tanks[1].writeToNBT(data, "1");
			
			if(this.tanks[1].getFill() > 0) {
				this.sendFluid();
			}

			data.setBoolean("isOn", this.isOn);
			INBTPacketReceiver.networkPack(this, data, 25);
		} else {
			
			if(this.isOn) this.audioTime = 20;
			
			if(this.audioTime > 0) {
				
				this.audioTime--;
				
				if(this.audio == null) {
					this.audio = createAudioLoop();
					this.audio.startSound();
				} else if(!this.audio.isPlaying()) {
					this.audio = rebootAudio(this.audio);
				}
				
				this.audio.keepAlive();
				
			} else {
				
				if(this.audio != null) {
					this.audio.stopSound();
					this.audio = null;
				}
			}
		}
	}
	
	@Override
	public AudioWrapper createAudioLoop() {
		return MainRegistry.proxy.getLoopedSound("hbm:block.boiler", this.xCoord, this.yCoord, this.zCoord, 0.125F, 10F, 1.0F, 20);
	}

	@Override
	public void onChunkUnload() {

		if(this.audio != null) {
			this.audio.stopSound();
			this.audio = null;
		}
	}

	@Override
	public void invalidate() {

		super.invalidate();

		if(this.audio != null) {
			this.audio.stopSound();
			this.audio = null;
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.heat = nbt.getInteger("heat");
		this.tanks[0].readFromNBT(nbt, "0");
		this.tanks[1].readFromNBT(nbt, "1");
		this.isOn = nbt.getBoolean("isOn");
	}
	
	protected void tryPullHeat() {
		TileEntity con = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord);
		
		if(con instanceof IHeatSource) {
			IHeatSource source = (IHeatSource) con;
			int diff = source.getHeatStored() - this.heat;
			
			if(diff == 0) {
				return;
			}
			
			if(diff > 0) {
				diff = (int) Math.ceil(diff * TileEntityHeatBoilerIndustrial.diffusion);
				source.useUpHeat(diff);
				this.heat += diff;
				if(this.heat > TileEntityHeatBoilerIndustrial.maxHeat)
					this.heat = TileEntityHeatBoilerIndustrial.maxHeat;
				return;
			}
		}
		
		this.heat = Math.max(this.heat - Math.max(this.heat / 1000, 1), 0);
	}
	
	protected void setupTanks() {
		
		if(this.tanks[0].getTankType().hasTrait(FT_Heatable.class)) {
			FT_Heatable trait = this.tanks[0].getTankType().getTrait(FT_Heatable.class);
			if(trait.getEfficiency(HeatingType.BOILER) > 0) {
				HeatingStep entry = trait.getFirstStep();
				this.tanks[1].setTankType(entry.typeProduced);
				this.tanks[1].changeTankSize(this.tanks[0].getMaxFill() * entry.amountProduced / entry.amountReq);
				return;
			}
		}

		this.tanks[0].setTankType(Fluids.NONE);
		this.tanks[1].setTankType(Fluids.NONE);
	}
	
	protected void tryConvert() {
		
		if(this.tanks[0].getTankType().hasTrait(FT_Heatable.class)) {
			FT_Heatable trait = this.tanks[0].getTankType().getTrait(FT_Heatable.class);
			if(trait.getEfficiency(HeatingType.BOILER) > 0) {
				
				HeatingStep entry = trait.getFirstStep();
				int inputOps = this.tanks[0].getFill() / entry.amountReq;
				int outputOps = (this.tanks[1].getMaxFill() - this.tanks[1].getFill()) / entry.amountProduced;
				int heatOps = this.heat / entry.heatReq;
				
				int ops = Math.min(inputOps, Math.min(outputOps, heatOps));

				this.tanks[0].setFill(this.tanks[0].getFill() - entry.amountReq * ops);
				this.tanks[1].setFill(this.tanks[1].getFill() + entry.amountProduced * ops);
				this.heat -= entry.heatReq * ops;
				
				if(ops > 0 && this.worldObj.rand.nextInt(400) == 0) {
					this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 2, this.zCoord + 0.5, "hbm:block.boilerGroan", 0.5F, 1.0F);
				}
				
				if(ops > 0) {
					this.isOn = true;
				}
			}
		}
	}
	
	private void updateConnections() {
		
		for(DirPos pos : getConPos()) {
			trySubscribe(this.tanks[0].getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	private void sendFluid() {
		
		for(DirPos pos : getConPos()) {
			this.sendFluid(this.tanks[1], this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir().getOpposite());
		}
	}
	
	private DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(this.xCoord + 2, this.yCoord, this.zCoord, Library.POS_X),
				new DirPos(this.xCoord - 2, this.yCoord, this.zCoord, Library.NEG_X),
				new DirPos(this.xCoord, this.yCoord, this.zCoord + 2, Library.POS_Z),
				new DirPos(this.xCoord, this.yCoord, this.zCoord - 2, Library.NEG_Z),
				new DirPos(this.xCoord, this.yCoord + 5, this.zCoord, Library.POS_Y),
		};
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.tanks[0].readFromNBT(nbt, "water");
		this.tanks[1].readFromNBT(nbt, "steam");
		this.heat = nbt.getInteger("heat");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.tanks[0].writeToNBT(nbt, "water");
		this.tanks[1].writeToNBT(nbt, "steam");
		nbt.setInteger("heat", this.heat);
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

	@Override
	public String getConfigName() {
		return "boilerIndustrial";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		TileEntityHeatBoilerIndustrial.maxHeat = IConfigurableMachine.grab(obj, "I:maxHeat", TileEntityHeatBoilerIndustrial.maxHeat);
		TileEntityHeatBoilerIndustrial.diffusion = IConfigurableMachine.grab(obj, "D:diffusion", TileEntityHeatBoilerIndustrial.diffusion);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("I:maxHeat").value(TileEntityHeatBoilerIndustrial.maxHeat);
		writer.name("D:diffusion").value(TileEntityHeatBoilerIndustrial.diffusion);
	}
}
