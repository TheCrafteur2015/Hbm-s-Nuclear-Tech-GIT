package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.WeaponConfig;
import com.hbm.extprop.HbmLivingProps;
import com.hbm.inventory.container.ContainerMachineRadar;
import com.hbm.inventory.gui.GUIMachineRadar;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityTickingBase;

import api.hbm.energy.IEnergyUser;
import api.hbm.entity.IRadarDetectable;
import api.hbm.entity.IRadarDetectable.RadarTargetType;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityMachineRadar extends TileEntityTickingBase implements IEnergyUser, IGUIProvider, SimpleComponent {

	public List<Entity> entList = new ArrayList<>();
	public List<int[]> nearbyMissiles = new ArrayList<>();
	int pingTimer = 0;
	int lastPower;
	final static int maxTimer = 80;
	
	public boolean scanMissiles = true;
	public boolean scanPlayers = true;
	public boolean smartMode = true;
	public boolean redMode = true;
	
	public boolean jammed = false;

	public float prevRotation;
	public float rotation;

	public long power = 0;
	public static final int maxPower = 100000;

	@Override
	public String getInventoryName() {
		return "";
	}

	@Override
	public void updateEntity() {
		
		if(this.yCoord < WeaponConfig.radarAltitude)
			return;
		
		if(!this.worldObj.isRemote) {
			
			this.updateStandardConnections(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			
			this.nearbyMissiles.clear();
			
			if(this.power > 0) {
				
				allocateMissiles();
				
				this.power -= 500;
				
				if(this.power < 0)
					this.power = 0;
			}
			
			if(this.lastPower != getRedPower())
				this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, getBlockType());
			
			sendMissileData();
			this.lastPower = getRedPower();
			
			if(this.worldObj.getBlock(this.xCoord, this.yCoord - 1, this.zCoord) != ModBlocks.muffler) {
				
				this.pingTimer++;
				
				if(this.power > 0 && this.pingTimer >= TileEntityMachineRadar.maxTimer) {
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.sonarPing", 5.0F, 1.0F);
					this.pingTimer = 0;
				}
			}
		} else {

			this.prevRotation = this.rotation;
			
			if(this.power > 0) {
				this.rotation += 5F;
			}
			
			if(this.rotation >= 360) {
				this.rotation -= 360F;
				this.prevRotation -= 360F;
			}
		}
	}
	
	@Override
	public void handleButtonPacket(int value, int meta) {
		
		switch(meta) {
		case 0: this.scanMissiles = !this.scanMissiles; break;
		case 1: this.scanPlayers = !this.scanPlayers; break;
		case 2: this.smartMode = !this.smartMode; break;
		case 3: this.redMode = !this.redMode; break;
		}
	}
	
	
	private void allocateMissiles() {
		
		this.nearbyMissiles.clear();
		this.entList.clear();
		this.jammed = false;
		
		List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(this.xCoord + 0.5 - WeaponConfig.radarRange, 0, this.zCoord + 0.5 - WeaponConfig.radarRange, this.xCoord + 0.5 + WeaponConfig.radarRange, 5000, this.zCoord + 0.5 + WeaponConfig.radarRange));

		for(Entity e : list) {
			
			if(e.posY < this.yCoord + WeaponConfig.radarBuffer)
				continue;
			
			if(e instanceof EntityLivingBase && HbmLivingProps.getDigamma((EntityLivingBase) e) > 0.001) {
				this.jammed = true;
				this.nearbyMissiles.clear();
				this.entList.clear();
				return;
			}

			if(e instanceof EntityPlayer && this.scanPlayers) {
				this.nearbyMissiles.add(new int[] { (int)e.posX, (int)e.posZ, RadarTargetType.PLAYER.ordinal(), (int)e.posY });
				this.entList.add(e);
			}
			
			if(e instanceof IRadarDetectable && this.scanMissiles) {
				this.nearbyMissiles.add(new int[] { (int)e.posX, (int)e.posZ, ((IRadarDetectable)e).getTargetType().ordinal(), (int)e.posY });
				
				if(!this.smartMode || e.motionY <= 0)
					this.entList.add(e);
			}
		}
	}
	
	public int getRedPower() {
		
		if(!this.entList.isEmpty()) {
			
			/// PROXIMITY ///
			if(this.redMode) {
				
				double maxRange = WeaponConfig.radarRange * Math.sqrt(2D);
				
				int power = 0;
				
				for (Entity e : this.entList) {
					
					double dist = Math.sqrt(Math.pow(e.posX - this.xCoord, 2) + Math.pow(e.posZ - this.zCoord, 2));
					int p = 15 - (int)Math.floor(dist / maxRange * 15);
					
					if(p > power)
						power = p;
				}
				
				return power;
				
			/// TIER ///
			} else {
				
				int power = 0;
				
				for (int[] element : this.nearbyMissiles) {
					
					if(element[2] + 1 > power) {
						power = element[2] + 1;
					}
				}
				
				return power;
			}
		}
		
		return 0;
	}
	
	private void sendMissileData() {
		
		NBTTagCompound data = new NBTTagCompound();
		data.setLong("power", this.power);
		data.setBoolean("scanMissiles", this.scanMissiles);
		data.setBoolean("scanPlayers", this.scanPlayers);
		data.setBoolean("smartMode", this.smartMode);
		data.setBoolean("redMode", this.redMode);
		data.setBoolean("jammed", this.jammed);
		data.setInteger("count", this.nearbyMissiles.size());
		
		for(int i = 0; i < this.nearbyMissiles.size(); i++) {
			data.setInteger("x" + i, this.nearbyMissiles.get(i)[0]);
			data.setInteger("z" + i, this.nearbyMissiles.get(i)[1]);
			data.setInteger("type" + i, this.nearbyMissiles.get(i)[2]);
			data.setInteger("y" + i, this.nearbyMissiles.get(i)[3]);
		}
		
		networkPack(data, 15);
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {
		
		this.nearbyMissiles.clear();
		this.power = data.getLong("power");
		this.scanMissiles = data.getBoolean("scanMissiles");
		this.scanPlayers = data.getBoolean("scanPlayers");
		this.smartMode = data.getBoolean("smartMode");
		this.redMode = data.getBoolean("redMode");
		this.jammed = data.getBoolean("jammed");
		
		int count = data.getInteger("count");
		
		for(int i = 0; i < count; i++) {

			int x = data.getInteger("x" + i);
			int z = data.getInteger("z" + i);
			int type = data.getInteger("type" + i);
			int y = data.getInteger("y" + i);
			
			this.nearbyMissiles.add(new int[] {x, z, type, y});
		}
	}
	
	public long getPowerScaled(long i) {
		return (this.power * i) / TileEntityMachineRadar.maxPower;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return TileEntityMachineRadar.maxPower;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.power = nbt.getLong("power");
		this.scanMissiles = nbt.getBoolean("scanMissiles");
		this.scanPlayers = nbt.getBoolean("scanPlayers");
		this.smartMode = nbt.getBoolean("smartMode");
		this.redMode = nbt.getBoolean("redMode");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", this.power);
		nbt.setBoolean("scanMissiles", this.scanMissiles);
		nbt.setBoolean("scanPlayers", this.scanPlayers);
		nbt.setBoolean("smartMode", this.smartMode);
		nbt.setBoolean("redMode", this.redMode);
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

	// do some opencomputer stuff

	@Override
	public String getComponentName() {
		return "ntm_radar";
	}

	@Callback(direct = true, limit = 8)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getEnergyInfo(Context context, Arguments args) {
		return new Object[] {getPower(), getMaxPower()};
	}

	@Callback(direct = true, limit = 8)
	@Optional.Method(modid = "OpenComputers")
	public Object[] isJammed(Context context, Arguments args) {
		return new Object[] {this.jammed};
	}

	@Callback(direct = true, limit = 8)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getEntities(Context context, Arguments args) { //fuck fuck fuck
		if(!this.jammed) {
			List<Object> list = new ArrayList<>();
			list.add(this.entList.size());     // small header of how many entities in the list
			for (Entity e : this.entList) {
				list.add(e.posX);   	  //  positions
				list.add(e.posY);
				list.add(e.posZ);
				list.add(e.motionX);
				list.add(e.motionY);
				list.add(e.motionZ);
				list.add(e.rotationYaw); //  just do rotation so you can calculate DOT
				list.add(Math.sqrt(Math.pow(e.posX - this.xCoord, 2) + Math.pow(e.posZ - this.zCoord, 2))); //  distance
				boolean player = e instanceof EntityPlayer;
				list.add(player);         //  isPlayer boolean
				if(!player)			      //  missile tier
					list.add(((IRadarDetectable) e).getTargetType().ordinal());
				else 				      //  player name (hopefully)
					list.add(((EntityPlayer) e).getDisplayName());
			}
			return new Object[] {list};   // long-ass list (like 9 entries per entity)
		} else {
			return new Object[] {"Radar jammed!"};
		}
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineRadar(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineRadar(player.inventory, this);
	}
}
