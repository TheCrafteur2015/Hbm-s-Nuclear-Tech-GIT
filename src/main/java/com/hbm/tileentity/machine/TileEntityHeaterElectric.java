package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IEnergyUser;
import api.hbm.tile.IHeatSource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityHeaterElectric extends TileEntityLoadedBase implements IHeatSource, IEnergyUser, INBTPacketReceiver {
	
	public long power;
	public int heatEnergy;
	public boolean isOn;
	protected int setting = 0;
	
	private AudioWrapper audio;

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			if(this.worldObj.getTotalWorldTime() % 20 == 0) { //doesn't have to happen constantly
				ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
				trySubscribe(this.worldObj, this.xCoord + dir.offsetX * 3, this.yCoord, this.zCoord + dir.offsetZ * 3, dir);
			}
			
			this.heatEnergy *= 0.999;
			
			tryPullHeat();

			this.isOn = false;
			if(this.setting > 0 && this.power >= getConsumption()) {
				this.power -= getConsumption();
				this.heatEnergy += getHeatGen();
				this.isOn = true;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setByte("s", (byte) this.setting);
			data.setInteger("h", this.heatEnergy);
			data.setBoolean("o", this.isOn);
			INBTPacketReceiver.networkPack(this, data, 25);
		} else {
			
			if(this.isOn) {
				
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
		return MainRegistry.proxy.getLoopedSound("hbm:block.electricHum", this.xCoord, this.yCoord, this.zCoord, 0.25F, 7.5F, 1.0F, 20);
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
		this.setting = nbt.getByte("s");
		this.heatEnergy = nbt.getInteger("h");
		this.isOn = nbt.getBoolean("o");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.power = nbt.getLong("power");
		this.setting = nbt.getInteger("setting");
		this.heatEnergy = nbt.getInteger("heatEnergy");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setLong("power", this.power);
		nbt.setInteger("setting", this.setting);
		nbt.setInteger("heatEnergy", this.heatEnergy);
	}
	
	protected void tryPullHeat() {
		TileEntity con = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord);
		
		if(con instanceof IHeatSource) {
			IHeatSource source = (IHeatSource) con;
			this.heatEnergy += source.getHeatStored() * 0.85;
			source.useUpHeat(source.getHeatStored());
		}
	}
	
	public void toggleSetting() {
		this.setting++;
		
		if(this.setting > 10)
			this.setting = 0;
	}

	@Override
	public long getPower() {
		return this.power;
	}
	
	public long getConsumption() {
		return (long) (Math.pow(this.setting, 1.4D) * 200D);
	}

	@Override
	public long getMaxPower() {
		return getConsumption() * 20;
	}
	
	public int getHeatGen() {
		return this.setting * 100;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public int getHeatStored() {
		return this.heatEnergy;
	}

	@Override
	public void useUpHeat(int heat) {
		this.heatEnergy = Math.max(0, this.heatEnergy - heat);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(
					this.xCoord - 2,
					this.yCoord,
					this.zCoord - 2,
					this.xCoord + 3,
					this.yCoord + 1,
					this.zCoord + 3
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
