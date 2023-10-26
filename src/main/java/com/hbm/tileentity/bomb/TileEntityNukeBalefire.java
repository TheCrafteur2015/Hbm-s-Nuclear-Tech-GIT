package com.hbm.tileentity.bomb;

import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityBalefire;
import com.hbm.inventory.container.ContainerNukeFstbmb;
import com.hbm.inventory.gui.GUINukeFstbmb;
import com.hbm.items.ModItems;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IBatteryItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityNukeBalefire extends TileEntityMachineBase implements IGUIProvider {

	public boolean loaded;
	public boolean started;
	public int timer;

	public TileEntityNukeBalefire() {
		super(2);
		this.timer = 18000;
	}

	@Override
	public String getName() {
		return "container.nukeFstbmb";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			if(!isLoaded()) {
				this.started = false;
			}
			
			if(this.started) {
				this.timer--;
				
				if(this.timer % 20 == 0)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:weapon.fstbmbPing", 5.0F, 1.0F);
			}
			
			if(this.timer <= 0) {
				explode();
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("timer", this.timer);
			data.setBoolean("loaded", isLoaded());
			data.setBoolean("started", this.started);
			networkPack(data, 250);
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {
		
		this.timer = data.getInteger("timer");
		this.started = data.getBoolean("started");
		this.loaded = data.getBoolean("loaded");
	}
	
	@Override
	public void handleButtonPacket(int value, int meta) {
		
		if(meta == 0 && isLoaded()) {
			this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:weapon.fstbmbStart", 5.0F, 1.0F);
			this.started = true;
		}
		
		if(meta == 1)
			this.timer = value * 20;
	}
	
	@Override
	public boolean isLoaded() {
		
		return hasEgg() && hasBattery();
	}
	
	public boolean hasEgg() {
		
		if(this.slots[0] != null && this.slots[0].getItem() == ModItems.egg_balefire) {
			return true;
		}
		
		return false;
	}
	
	public boolean hasBattery() {
		
		return getBattery() > 0;
	}
	
	public int getBattery() {
		
		if(this.slots[1] != null && this.slots[1].getItem() == ModItems.battery_spark &&
				((IBatteryItem)ModItems.battery_spark).getCharge(this.slots[1]) == ((IBatteryItem)ModItems.battery_spark).getMaxCharge()) {
			return 1;
		}
		
		if(this.slots[1] != null && this.slots[1].getItem() == ModItems.battery_trixite &&
				((IBatteryItem)ModItems.battery_trixite).getCharge(this.slots[1]) == ((IBatteryItem)ModItems.battery_trixite).getMaxCharge()) {
			return 2;
		}
		
		return 0;
	}
	
	public void explode() {
		
		for(int i = 0; i < this.slots.length; i++)
			this.slots[i] = null;
		
		this.worldObj.func_147480_a(this.xCoord, this.yCoord, this.zCoord, false);
		
		EntityBalefire bf = new EntityBalefire(this.worldObj);
		bf.posX = this.xCoord + 0.5;
		bf.posY = this.yCoord + 0.5;
		bf.posZ = this.zCoord + 0.5;
		bf.destructionRange = (int) 250;
		this.worldObj.spawnEntityInWorld(bf);
		EntityNukeTorex.statFacBale(this.worldObj, this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, 250);
	}
	
	public String getMinutes() {
		
		String mins = "" + (this.timer / 1200);
		
		if(mins.length() == 1)
			mins = "0" + mins;
		
		return mins;
	}
	
	public String getSeconds() {
		
		String mins = "" + ((this.timer / 20) % 60);
		
		if(mins.length() == 1)
			mins = "0" + mins;
		
		return mins;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.started = nbt.getBoolean("started");
		this.timer = nbt.getInteger("timer");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setBoolean("started", this.started);
		nbt.setInteger("timer", this.timer);
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerNukeFstbmb(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUINukeFstbmb(player.inventory, this);
	}
}
