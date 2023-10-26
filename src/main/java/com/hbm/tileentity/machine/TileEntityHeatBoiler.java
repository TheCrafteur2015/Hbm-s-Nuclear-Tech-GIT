package com.hbm.tileentity.machine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.BlockDummyable;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.EntityProcessorStandard;
import com.hbm.explosion.vanillant.standard.ExplosionEffectStandard;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.fluid.FluidType;
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
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityHeatBoiler extends TileEntityLoadedBase implements IFluidSource, IFluidAcceptor, INBTPacketReceiver, IFluidStandardTransceiver, IConfigurableMachine {

	public int heat;
	public FluidTank[] tanks;
	public List<IFluidAcceptor> list = new ArrayList<>();
	public boolean isOn;
	public boolean hasExploded = false;
	
	private AudioWrapper audio;
	private int audioTime;
	
	/* CONFIGURABLE */
	public static int maxHeat = 3_200_000;
	public static double diffusion = 0.1D;
	public static boolean canExplode = true;

	public TileEntityHeatBoiler() {
		this.tanks = new FluidTank[2];

		this.tanks[0] = new FluidTank(Fluids.WATER, 16_000);
		this.tanks[1] = new FluidTank(Fluids.STEAM, 16_000 * 100);
	}
	
	@Override
	public void updateEntity() {

		if(!this.worldObj.isRemote) {
			
			NBTTagCompound data = new NBTTagCompound();
			
			if(!this.hasExploded) {
				setupTanks();
				updateConnections();
				tryPullHeat();
				int lastHeat = this.heat;
				
				int light = this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, this.xCoord, this.yCoord, this.zCoord);
				if(light > 7 && TomSaveData.forWorld(this.worldObj).fire > 1e-5) {
					this.heat += ((TileEntityHeatBoiler.maxHeat - this.heat) * 0.000005D); //constantly heat up 0.0005% of the remaining heat buffer for rampant but diminishing heating
				}
				
				data.setInteger("heat", lastHeat);

				this.tanks[0].writeToNBT(data, "0");
				this.isOn = false;
				tryConvert();
				this.tanks[1].writeToNBT(data, "1");
				
				if(this.tanks[1].getFill() > 0) {
					this.sendFluid();
					fillFluidInit(this.tanks[1].getTankType());
				}
			}

			data.setBoolean("exploded", this.hasExploded);
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
		this.hasExploded = nbt.getBoolean("exploded");
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
				diff = (int) Math.ceil(diff * TileEntityHeatBoiler.diffusion);
				source.useUpHeat(diff);
				this.heat += diff;
				if(this.heat > TileEntityHeatBoiler.maxHeat)
					this.heat = TileEntityHeatBoiler.maxHeat;
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
				
				if(outputOps == 0 && TileEntityHeatBoiler.canExplode) {
					this.hasExploded = true;
					BlockDummyable.safeRem = true;
					for(int x = this.xCoord - 1; x <= this.xCoord + 1; x++) {
						for(int y = this.yCoord + 2; y <= this.yCoord + 3; y++) {
							for(int z = this.zCoord - 1; z <= this.zCoord + 1; z++) {
								this.worldObj.setBlockToAir(x, y, z);
							}
						}
					}
					this.worldObj.setBlockToAir(this.xCoord, this.yCoord + 1, this.zCoord);
					
					ExplosionVNT xnt = new ExplosionVNT(this.worldObj, this.xCoord + 0.5, this.yCoord + 2, this.zCoord + 0.5, 5F);
					xnt.setEntityProcessor(new EntityProcessorStandard().withRangeMod(3F));
					xnt.setPlayerProcessor(new PlayerProcessorStandard());
					xnt.setSFX(new ExplosionEffectStandard());
					xnt.explode();
					
					BlockDummyable.safeRem = false;
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
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset).getRotation(ForgeDirection.UP);
		return new DirPos[] {
				new DirPos(this.xCoord + dir.offsetX * 2, this.yCoord, this.zCoord + dir.offsetZ * 2, dir),
				new DirPos(this.xCoord - dir.offsetX * 2, this.yCoord, this.zCoord - dir.offsetZ * 2, dir.getOpposite()),
				new DirPos(this.xCoord, this.yCoord + 4, this.zCoord, Library.POS_Y),
		};
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.tanks[0].readFromNBT(nbt, "water");
		this.tanks[1].readFromNBT(nbt, "steam");
		this.heat = nbt.getInteger("heat");
		this.hasExploded = nbt.getBoolean("exploded");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.tanks[0].writeToNBT(nbt, "water");
		this.tanks[1].writeToNBT(nbt, "steam");
		nbt.setInteger("heat", this.heat);
		nbt.setBoolean("exploded", this.hasExploded);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		for(FluidTank tank : this.tanks) {
			if(tank.getTankType() == type) {
				tank.setFill(fill);
				return;
			}
		}
	}

	@Override public void setFillForSync(int fill, int index) { }
	@Override public void setTypeForSync(FluidType type, int index) { }

	@Override
	public int getFluidFill(FluidType type) {
		for(FluidTank tank : this.tanks) {
			if(tank.getTankType() == type) {
				return tank.getFill();
			}
		}
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		return type == this.tanks[0].getTankType() ? this.tanks[0].getMaxFill() : 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset).getRotation(ForgeDirection.UP);
		fillFluid(this.xCoord + dir.offsetX * 2, this.yCoord, this.zCoord + dir.offsetZ * 2, getTact(), type);
		fillFluid(this.xCoord - dir.offsetX * 2, this.yCoord, this.zCoord - dir.offsetZ * 2, getTact(), type);
		fillFluid(this.xCoord, this.yCoord + 4, this.zCoord, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, this.worldObj, type);
	}

	@Override
	public boolean getTact() {
		return this.worldObj.getTotalWorldTime() % 2 == 0;
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return this.list;
	}

	@Override
	public void clearFluidList(FluidType type) {
		this.list.clear();
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
					this.yCoord + 4,
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
		return "boiler";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		TileEntityHeatBoiler.maxHeat = IConfigurableMachine.grab(obj, "I:maxHeat", TileEntityHeatBoiler.maxHeat);
		TileEntityHeatBoiler.diffusion = IConfigurableMachine.grab(obj, "D:diffusion", TileEntityHeatBoiler.diffusion);
		TileEntityHeatBoiler.canExplode = IConfigurableMachine.grab(obj, "B:canExplode", TileEntityHeatBoiler.canExplode);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("I:maxHeat").value(TileEntityHeatBoiler.maxHeat);
		writer.name("D:diffusion").value(TileEntityHeatBoiler.diffusion);
		writer.name("B:canExplode").value(TileEntityHeatBoiler.canExplode);
	}
}
