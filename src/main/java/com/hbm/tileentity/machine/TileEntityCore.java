package com.hbm.tileentity.machine;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityCloudFleijaRainbow;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.entity.logic.EntityNukeExplosionMK3.ATEntry;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.inventory.container.ContainerCore;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUICore;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemCatalyst;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.ArmorUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityCore extends TileEntityMachineBase implements IGUIProvider {
	
	public int field;
	public int heat;
	public int color;
	public FluidTank[] tanks;
	private boolean lastTickValid = false;
	public boolean meltdownTick = false;

	public TileEntityCore() {
		super(3);
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.DEUTERIUM, 128000);
		this.tanks[1] = new FluidTank(Fluids.TRITIUM, 128000);
	}

	@Override
	public String getName() {
		return "container.dfcCore";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			int chunkX = this.xCoord >> 4;
			int chunkZ = this.zCoord >> 4;
			
			this.meltdownTick = false;
			
			this.lastTickValid = this.worldObj.getChunkProvider().chunkExists(chunkX, chunkZ) &&
					this.worldObj.getChunkProvider().chunkExists(chunkX + 1, chunkZ + 1) &&
					this.worldObj.getChunkProvider().chunkExists(chunkX + 1, chunkZ - 1) &&
					this.worldObj.getChunkProvider().chunkExists(chunkX - 1, chunkZ + 1) &&
					this.worldObj.getChunkProvider().chunkExists(chunkX - 1, chunkZ - 1);
			
			if(this.lastTickValid && this.heat > 0 && this.heat >= this.field) {
				
				int fill = this.tanks[0].getFill() + this.tanks[1].getFill();
				int max = this.tanks[0].getMaxFill() + this.tanks[1].getMaxFill();
				int mod = this.heat * 10;
				
				int size = Math.max(Math.min(fill * mod / max, 1000), 50);
				
				boolean canExplode = true;
				Iterator<Entry<ATEntry, Long>> it = EntityNukeExplosionMK3.at.entrySet().iterator();
				while(it.hasNext()) {
					Entry<ATEntry, Long> next = it.next();
					if(next.getValue() < this.worldObj.getTotalWorldTime()) {
						it.remove();
						continue;
					}
					ATEntry entry = next.getKey();
					if(entry.dim != this.worldObj.provider.dimensionId)  continue;
					Vec3 vec = Vec3.createVectorHelper(this.xCoord + 0.5 - entry.x, this.yCoord + 0.5 - entry.y, this.zCoord + 0.5 - entry.z);
					if(vec.lengthVector() < 300) {
						canExplode = false;
						break;
					}
				}
				
				if(canExplode) {
					
					EntityNukeExplosionMK3 ex = new EntityNukeExplosionMK3(this.worldObj);
					ex.posX = this.xCoord + 0.5;
					ex.posY = this.yCoord + 0.5;
					ex.posZ = this.zCoord + 0.5;
					ex.destructionRange = size;
					ex.speed = BombConfig.blastSpeed;
					ex.coefficient = 1.0F;
					ex.waste = false;
					this.worldObj.spawnEntityInWorld(ex);
					
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "random.explode", 100000.0F, 1.0F);
					
					EntityCloudFleijaRainbow cloud = new EntityCloudFleijaRainbow(this.worldObj, size);
					cloud.posX = this.xCoord;
					cloud.posY = this.yCoord;
					cloud.posZ = this.zCoord;
					this.worldObj.spawnEntityInWorld(cloud);
					
				} else {
					this.meltdownTick = true;
					ChunkRadiationManager.proxy.incrementRad(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 100);
				}
			}
			
			if(this.slots[0] != null && this.slots[2] != null && this.slots[0].getItem() instanceof ItemCatalyst && this.slots[2].getItem() instanceof ItemCatalyst)
				this.color = calcAvgHex(
						((ItemCatalyst)this.slots[0].getItem()).getColor(),
						((ItemCatalyst)this.slots[2].getItem()).getColor()
				);
			else
				this.color = 0;
			
			if(this.heat > 0)
				radiation();
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("tank0", this.tanks[0].getTankType().getID());
			data.setInteger("tank1", this.tanks[1].getTankType().getID());
			data.setInteger("fill0", this.tanks[0].getFill());
			data.setInteger("fill1", this.tanks[1].getFill());
			data.setInteger("field", this.field);
			data.setInteger("heat", this.heat);
			data.setInteger("color", this.color);
			data.setBoolean("melt", this.meltdownTick);
			networkPack(data, 250);
			
			this.heat = 0;
			
			if(this.lastTickValid && this.field > 0) {
				this.field -= 1;
			}
			
			markDirty();
		} else {
			
			//TODO: sick particle effects
		}
		
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {

		this.tanks[0].setTankType(Fluids.fromID(data.getInteger("tank0")));
		this.tanks[1].setTankType(Fluids.fromID(data.getInteger("tank1")));
		this.tanks[0].setFill(data.getInteger("fill0"));
		this.tanks[1].setFill(data.getInteger("fill1"));
		this.field = data.getInteger("field");
		this.heat = data.getInteger("heat");
		this.color = data.getInteger("color");
		this.meltdownTick = data.getBoolean("melt");
	}
	
	private void radiation() {
		
		double scale = this.meltdownTick ? 5 : 3;
		double range = this.meltdownTick ? 50 : 10;
		
		List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5).expand(range, range, range));
		
		for(Entity e : list) {
			if(!(e instanceof EntityPlayer && ArmorUtil.checkForHazmat((EntityPlayer)e)))
				if(!Library.isObstructed(this.worldObj, this.xCoord + 0.5, this.yCoord + 0.5 + 6, this.zCoord + 0.5, e.posX, e.posY + e.getEyeHeight(), e.posZ)) {
					e.attackEntityFrom(ModDamageSource.ams, 1000);
					e.setFire(3);
				}
		}

		List<Entity> list2 = this.worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5).expand(scale, scale, scale));
		
		for(Entity e : list2) {
			if(!(e instanceof EntityPlayer && ArmorUtil.checkForHaz2((EntityPlayer)e)))
					e.attackEntityFrom(ModDamageSource.amsCore, 10000);
		}
	}
	
	public int getFieldScaled(int i) {
		return (this.field * i) / 100;
	}
	
	public int getHeatScaled(int i) {
		return (this.heat * i) / 100;
	}
	
	public boolean isReady() {
		
		if(!this.lastTickValid || (getCore() == 0) || (this.color == 0))
			return false;
		
		if(getFuelEfficiency(this.tanks[0].getTankType()) <= 0 || getFuelEfficiency(this.tanks[1].getTankType()) <= 0)
			return false;
		
		return true;
	}
	
	//100 emitter watt = 10000 joules = 1 heat = 10mB burned
	public long burn(long joules) {
		
		//check if a reaction can take place
		if(!isReady())
			return joules;
		
		int demand = (int)Math.ceil(joules / 1000D);
		
		//check if the reaction has enough valid fuel
		if(this.tanks[0].getFill() < demand || this.tanks[1].getFill() < demand)
			return joules;
		
		this.heat += (int)Math.ceil(joules / 10000D);

		this.tanks[0].setFill(this.tanks[0].getFill() - demand);
		this.tanks[1].setFill(this.tanks[1].getFill() - demand);
		
		return (long) (joules * getCore() * getFuelEfficiency(this.tanks[0].getTankType()) * getFuelEfficiency(this.tanks[1].getTankType()));
	}
	
	public float getFuelEfficiency(FluidType type) {
		if(type == Fluids.HYDROGEN)
			return 1.0F;
		if(type == Fluids.DEUTERIUM)
			return 1.5F;
		if(type == Fluids.TRITIUM)
			return 1.7F;
		if(type == Fluids.OXYGEN)
			return 1.2F;
		if(type == Fluids.ACID)
			return 1.4F;
		if(type == Fluids.XENON)
			return 1.5F;
		if(type == Fluids.SAS3)
			return 2.0F;
		if(type == Fluids.BALEFIRE)
			return 2.5F;
		if(type == Fluids.AMAT)
			return 2.2F;
		if(type == Fluids.ASCHRAB)
			return 2.7F;
		return 0;
	}
	
	//TODO: move stats to the AMSCORE class
	public int getCore() {
		
		if(this.slots[1] == null) {
			return 0;
		}
		
		if(this.slots[1].getItem() == ModItems.ams_core_sing)
			return 500;
		
		if(this.slots[1].getItem() == ModItems.ams_core_wormhole)
			return 650;
		
		if(this.slots[1].getItem() == ModItems.ams_core_eyeofharmony)
			return 800;
		
		if(this.slots[1].getItem() == ModItems.ams_core_thingy)
			return 2500;
		
		return 0;
	}
	
	private int calcAvgHex(int h1, int h2) {

		int r1 = ((h1 & 0xFF0000) >> 16);
		int g1 = ((h1 & 0x00FF00) >> 8);
		int b1 = ((h1 & 0x0000FF) >> 0);
		
		int r2 = ((h2 & 0xFF0000) >> 16);
		int g2 = ((h2 & 0x00FF00) >> 8);
		int b2 = ((h2 & 0x0000FF) >> 0);

		int r = (((r1 + r2) / 2) << 16);
		int g = (((g1 + g2) / 2) << 8);
		int b = (((b1 + b2) / 2) << 0);
		
		return r | g | b;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.tanks[0].readFromNBT(nbt, "fuel1");
		this.tanks[1].readFromNBT(nbt, "fuel2");
		this.field = nbt.getInteger("field");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		this.tanks[0].writeToNBT(nbt, "fuel1");
		this.tanks[1].writeToNBT(nbt, "fuel2");
		nbt.setInteger("field", this.field);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(
					this.xCoord + 0.5 - 4,
					this.yCoord + 0.5 - 4,
					this.zCoord + 0.5 - 4,
					this.xCoord + 0.5 + 5,
					this.yCoord + 0.5 + 5,
					this.zCoord + 0.5 + 5
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
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCore(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICore(player.inventory, this);
	}
}
