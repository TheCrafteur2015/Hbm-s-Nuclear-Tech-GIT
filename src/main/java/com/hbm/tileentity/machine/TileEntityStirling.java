package com.hbm.tileentity.machine;

import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.entity.projectile.EntityCog;
import com.hbm.lib.Library;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyGenerator;
import api.hbm.tile.IHeatSource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityStirling extends TileEntityLoadedBase implements INBTPacketReceiver, IEnergyGenerator, IConfigurableMachine {
	
	public long powerBuffer;
	public int heat;
	private int warnCooldown = 0;
	private int overspeed = 0;
	public boolean hasCog = true;
	
	public float spin;
	public float lastSpin;
	
	/* CONFIGURABLE CONSTANTS */
	public static double diffusion = 0.1D;
	public static double efficiency = 0.5D;
	public static int maxHeatNormal = 300;
	public static int maxHeatSteel = 1500;
	public static int overspeedLimit = 300;

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			if(this.hasCog) {
				tryPullHeat();
				
				this.powerBuffer = (long) (this.heat * (isCreative() ? 1 : TileEntityStirling.efficiency));
				
				if(this.warnCooldown > 0)
					this.warnCooldown--;
				
				if(this.heat > maxHeat() && !isCreative()) {
					
					this.overspeed++;
					
					if(this.overspeed > 60 && this.warnCooldown == 0) {
						this.warnCooldown = 100;
						this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 1, this.zCoord + 0.5, "hbm:block.warnOverspeed", 2.0F, 1.0F);
					}
					
					if(this.overspeed > TileEntityStirling.overspeedLimit) {
						this.hasCog = false;
						this.worldObj.newExplosion(null, this.xCoord + 0.5, this.yCoord + 1, this.zCoord + 0.5, 5F, false, false);
						
						int orientation = getBlockMetadata() - BlockDummyable.offset;
						ForgeDirection dir = ForgeDirection.getOrientation(orientation);
						EntityCog cog = new EntityCog(this.worldObj, this.xCoord + 0.5 + dir.offsetX, this.yCoord + 1, this.zCoord + 0.5 + dir.offsetZ).setOrientation(orientation).setMeta(getGeatMeta());
						ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);
						
						cog.motionX = rot.offsetX;
						cog.motionY = 1 + (this.heat - maxHeat()) * 0.0001D;
						cog.motionZ = rot.offsetZ;
						this.worldObj.spawnEntityInWorld(cog);
						
						markDirty();
					}
					
				} else {
					this.overspeed = 0;
				}
			} else {
				this.overspeed = 0;
				this.warnCooldown = 0;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.powerBuffer);
			data.setInteger("heat", this.heat);
			data.setBoolean("hasCog", this.hasCog);
			INBTPacketReceiver.networkPack(this, data, 150);
			
			if(this.hasCog) {
				for(DirPos pos : getConPos()) {
					sendPower(this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				}
				this.powerBuffer = 0;
			} else {
				
				if(this.powerBuffer > 0)
					this.powerBuffer--;
			}
			
			this.heat = 0;
		} else {
			
			float momentum = this.powerBuffer * 50F / ((float) maxHeat());
			
			if(isCreative()) momentum = Math.min(momentum, 45F);
			
			this.lastSpin = this.spin;
			this.spin += momentum;
			
			if(this.spin >= 360F) {
				this.spin -= 360F;
				this.lastSpin -= 360F;
			}
		}
	}
	
	public int getGeatMeta() {
		return getBlockType() == ModBlocks.machine_stirling ? 0 : getBlockType() == ModBlocks.machine_stirling_creative ? 2 : 1;
	}
	
	public int maxHeat() {
		return getBlockType() == ModBlocks.machine_stirling ? 300 : 1500;
	}
	
	public boolean isCreative() {
		return getBlockType() == ModBlocks.machine_stirling_creative;
	}
	
	protected DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(this.xCoord + 2, this.yCoord, this.zCoord, Library.POS_X),
				new DirPos(this.xCoord - 2, this.yCoord, this.zCoord, Library.NEG_X),
				new DirPos(this.xCoord, this.yCoord, this.zCoord + 2, Library.POS_Z),
				new DirPos(this.xCoord, this.yCoord, this.zCoord - 2, Library.NEG_Z)
		};
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.powerBuffer = nbt.getLong("power");
		this.heat = nbt.getInteger("heat");
		this.hasCog = nbt.getBoolean("hasCog");
	}
	
	protected void tryPullHeat() {
		TileEntity con = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord);
		
		if(con instanceof IHeatSource) {
			IHeatSource source = (IHeatSource) con;
			int heatSrc = (int) (source.getHeatStored() * TileEntityStirling.diffusion);
			
			if(heatSrc > 0) {
				source.useUpHeat(heatSrc);
				this.heat += heatSrc;
				return;
			}
		}
		
		this.heat = Math.max(this.heat - Math.max(this.heat / 1000, 1), 0);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.powerBuffer = nbt.getLong("powerBuffer");
		this.hasCog = nbt.getBoolean("hasCog");
		this.overspeed = nbt.getInteger("overspeed");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setLong("powerBuffer", this.powerBuffer);
		nbt.setBoolean("hasCog", this.hasCog);
		nbt.setInteger("overspeed", this.overspeed);
	}

	@Override
	public void setPower(long power) {
		this.powerBuffer = power;
	}

	@Override
	public long getPower() {
		return this.powerBuffer;
	}

	@Override
	public long getMaxPower() {
		return this.powerBuffer;
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
	public String getConfigName() {
		return "stirling";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		TileEntityStirling.diffusion = IConfigurableMachine.grab(obj, "D:diffusion", TileEntityStirling.diffusion);
		TileEntityStirling.efficiency = IConfigurableMachine.grab(obj, "D:efficiency", TileEntityStirling.efficiency);
		TileEntityStirling.maxHeatNormal = IConfigurableMachine.grab(obj, "I:maxHeatNormal", TileEntityStirling.maxHeatNormal);
		TileEntityStirling.maxHeatSteel = IConfigurableMachine.grab(obj, "I:maxHeatSteel", TileEntityStirling.maxHeatSteel);
		TileEntityStirling.overspeedLimit = IConfigurableMachine.grab(obj, "I:overspeedLimit", TileEntityStirling.overspeedLimit);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("D:diffusion").value(TileEntityStirling.diffusion);
		writer.name("D:efficiency").value(TileEntityStirling.efficiency);
		writer.name("I:maxHeatNormal").value(TileEntityStirling.maxHeatNormal);
		writer.name("I:maxHeatSteel").value(TileEntityStirling.maxHeatSteel);
		writer.name("I:overspeedLimit").value(TileEntityStirling.overspeedLimit);
	}
}
