package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyUser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCharger extends TileEntityLoadedBase implements IEnergyUser, INBTPacketReceiver {
	
	private List<EntityPlayer> players = new ArrayList<>();
	private long charge = 0;
	private int lastOp = 0;
	
	boolean particles = false;
	
	public int usingTicks;
	public int lastUsingTicks;
	public static final int delay = 20;

	
	@Override
	public void updateEntity() {
		
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata()).getOpposite();
		
		if(!this.worldObj.isRemote) {
			trySubscribe(this.worldObj, this.xCoord + dir.offsetX, this.yCoord, this.zCoord + dir.offsetZ, dir);
			
			this.players = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(this.xCoord + 0.5, this.yCoord, this.zCoord + 0.5, this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5).expand(0.5, 0.0, 0.5));
			
			this.charge = 0;
			
			for(EntityPlayer player : this.players) {
				
				for(int i = 0; i < 5; i++) {
					
					ItemStack stack = player.getEquipmentInSlot(i);
					
					if(stack != null && stack.getItem() instanceof IBatteryItem) {
						IBatteryItem battery = (IBatteryItem) stack.getItem();
						this.charge += Math.min(battery.getMaxCharge() - battery.getCharge(stack), battery.getChargeRate());
					}
				}
			}
			
			this.particles = this.lastOp > 0;
			
			if(this.particles) {
				
				this.lastOp--;
				
				if(this.worldObj.getTotalWorldTime() % 20 == 0)
					this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, "random.fizz", 0.2F, 0.5F);
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("c", this.charge);
			data.setBoolean("p", this.particles);
			INBTPacketReceiver.networkPack(this, data, 50);
		}
		
		this.lastUsingTicks = this.usingTicks;
		
		if((this.charge > 0 || this.particles) && this.usingTicks < TileEntityCharger.delay) {
			this.usingTicks++;
			if(this.usingTicks == 2)
				this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, "tile.piston.out", 0.5F, 0.5F);
		}
		if((this.charge <= 0 && !this.particles) && this.usingTicks > 0) {
			this.usingTicks--;
			if(this.usingTicks == 4)
				this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, "tile.piston.in", 0.5F, 0.5F);
		}
		
		if(this.particles) {
			Random rand = this.worldObj.rand;
			this.worldObj.spawnParticle("magicCrit",
					this.xCoord + 0.5 + rand.nextDouble() * 0.0625 + dir.offsetX * 0.75,
					this.yCoord + 0.1,
					this.zCoord + 0.5 + rand.nextDouble() * 0.0625 + dir.offsetZ * 0.75,
					-dir.offsetX + rand.nextGaussian() * 0.1,
					0,
					-dir.offsetZ + rand.nextGaussian() * 0.1);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.charge = nbt.getLong("c");
		this.particles = nbt.getBoolean("p");
	}

	@Override
	public long getPower() {
		return 0;
	}

	@Override
	public long getMaxPower() {
		return this.charge;
	}

	@Override
	public void setPower(long power) { }
	
	@Override
	public long transferPower(long power) {
		
		if(this.usingTicks < TileEntityCharger.delay || power == 0)
			return power;
		
		for(EntityPlayer player : this.players) {
			
			for(int i = 0; i < 5; i++) {
				
				ItemStack stack = player.getEquipmentInSlot(i);
				
				if(stack != null && stack.getItem() instanceof IBatteryItem) {
					IBatteryItem battery = (IBatteryItem) stack.getItem();
					
					long toCharge = Math.min(battery.getMaxCharge() - battery.getCharge(stack), battery.getChargeRate());
					toCharge = Math.min(toCharge, power / 5);
					battery.chargeBattery(stack, toCharge);
					power -= toCharge;
					
					this.lastOp = 4;
				}
			}
		}
		
		return power;
	}
}
