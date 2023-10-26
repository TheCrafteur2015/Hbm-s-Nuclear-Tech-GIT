package com.hbm.entity.projectile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.logic.IChunkLoader;
import com.hbm.entity.projectile.rocketbehavior.IRocketSteeringBehavior;
import com.hbm.entity.projectile.rocketbehavior.IRocketTargetingBehavior;
import com.hbm.entity.projectile.rocketbehavior.RocketSteeringBallisticArc;
import com.hbm.entity.projectile.rocketbehavior.RocketTargetingPredictive;
import com.hbm.items.weapon.ItemAmmoHIMARS;
import com.hbm.items.weapon.ItemAmmoHIMARS.HIMARSRocket;
import com.hbm.main.MainRegistry;

import api.hbm.entity.IRadarDetectable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

public class EntityArtilleryRocket extends EntityThrowableInterp implements IChunkLoader, IRadarDetectable {

	private Ticket loaderTicket;
	
	//TODO: find satisfying solution for when an entity is unloaded and reloaded, possibly a custom entity lookup using persistent UUIDs
	public Entity targetEntity = null;
	public Vec3 lastTargetPos;
	
	public IRocketTargetingBehavior targeting;
	public IRocketSteeringBehavior steering;
	
	public EntityArtilleryRocket(World world) {
		super(world);
		this.ignoreFrustumCheck = true;

		this.targeting = new RocketTargetingPredictive();
		this.steering = new RocketSteeringBallisticArc();
	}

	@Override
	protected void entityInit() {
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, this.worldObj, Type.ENTITY));
		this.dataWatcher.addObject(10, new Integer(0));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return true;
	}
	
	public EntityArtilleryRocket setType(int type) {
		this.dataWatcher.updateObject(10, type);
		return this;
	}
	
	public HIMARSRocket getType() {
		try {
			return ItemAmmoHIMARS.itemTypes[this.dataWatcher.getWatchableObjectInt(10)];
		} catch(Exception ex) {
			return ItemAmmoHIMARS.itemTypes[0];
		}
	}
	
	public EntityArtilleryRocket setTarget(Entity target) {
		this.targetEntity = target;
		setTarget(target.posX, target.posY - target.yOffset + target.height / 2D, target.posZ);
		return this;
	}
	
	public EntityArtilleryRocket setTarget(double x, double y, double z) {
		this.lastTargetPos = Vec3.createVectorHelper(x, y, z);
		return this;
	}
	
	public Vec3 getLastTarget() {
		return this.lastTargetPos;
	}
	
	@Override
	public void onUpdate() {
		
		if(this.worldObj.isRemote) {
			this.lastTickPosX = this.posX;
			this.lastTickPosY = this.posY;
			this.lastTickPosZ = this.posZ;
		}
		
		super.onUpdate();
		
		if(!this.worldObj.isRemote) {
			
			if(this.targetEntity == null) {
				Vec3 delta = Vec3.createVectorHelper(this.lastTargetPos.xCoord - this.posX, this.lastTargetPos.yCoord - this.posY, this.lastTargetPos.zCoord - this.posZ);
				if(delta.lengthVector() <= 15D) {
					this.targeting = null;
					this.steering = null;
				}
			}
			
			if(this.targeting != null && this.targetEntity != null) this.targeting.recalculateTargetPosition(this, this.targetEntity);
			if(this.steering != null) this.steering.adjustCourse(this, 25D, 15D);
			
			loadNeighboringChunks((int)Math.floor(this.posX / 16D), (int)Math.floor(this.posZ / 16D));
			getType().onUpdate(this);
		} else {
			
			Vec3 v = Vec3.createVectorHelper(this.lastTickPosX - this.posX, this.lastTickPosY - this.posY, this.lastTickPosZ - this.posZ);
			double velocity = v.lengthVector();
			v = v.normalize();
			
			int offset = 6;
			if(velocity > 1) for(int i = offset; i < velocity + offset; i++) MainRegistry.proxy.spawnParticle(this.posX + v.xCoord * i, this.posY + v.yCoord * i, this.posZ + v.zCoord * i, "exKerosene", null);
		}
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		
		if(!this.worldObj.isRemote) {
			getType().onImpact(this, mop);
		}
	}

	@Override
	public void init(Ticket ticket) {
		if(!this.worldObj.isRemote && ticket != null) {
			if(this.loaderTicket == null) {
				this.loaderTicket = ticket;
				this.loaderTicket.bindEntity(this);
				this.loaderTicket.getModData();
			}
			ForgeChunkManager.forceChunk(this.loaderTicket, new ChunkCoordIntPair(this.chunkCoordX, this.chunkCoordZ));
		}
	}

	List<ChunkCoordIntPair> loadedChunks = new ArrayList<>();

	public void loadNeighboringChunks(int newChunkX, int newChunkZ) {
		if(!this.worldObj.isRemote && this.loaderTicket != null) {
			
			clearChunkLoader();

			this.loadedChunks.clear();
			this.loadedChunks.add(new ChunkCoordIntPair(newChunkX, newChunkZ));
			this.loadedChunks.add(new ChunkCoordIntPair(newChunkX + (int) Math.ceil((this.posX + this.motionX) / 16D), newChunkZ + (int) Math.ceil((this.posZ + this.motionZ) / 16D)));

			for(ChunkCoordIntPair chunk : this.loadedChunks) {
				ForgeChunkManager.forceChunk(this.loaderTicket, chunk);
			}
		}
	}
	
	public void killAndClear() {
		setDead();
		clearChunkLoader();
	}
	
	public void clearChunkLoader() {
		if(!this.worldObj.isRemote && this.loaderTicket != null) {
			for(ChunkCoordIntPair chunk : this.loadedChunks) {
				ForgeChunkManager.unforceChunk(this.loaderTicket, chunk);
			}
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		
		if(this.lastTargetPos == null) {
			this.lastTargetPos = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
		}
		
		nbt.setDouble("targetX", this.lastTargetPos.xCoord);
		nbt.setDouble("targetY", this.lastTargetPos.yCoord);
		nbt.setDouble("targetZ", this.lastTargetPos.zCoord);
		
		nbt.setInteger("type", this.dataWatcher.getWatchableObjectInt(10));
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);

		this.lastTargetPos = Vec3.createVectorHelper(nbt.getDouble("targetX"), nbt.getDouble("targetY"), nbt.getDouble("targetZ"));
		
		this.dataWatcher.updateObject(10, nbt.getInteger("type"));
	}

	@Override
	protected float getAirDrag() {
		return 1.0F;
	}

	@Override
	public double getGravityVelocity() {
		return this.steering != null ? 0D : 0.01D;
	}

	@Override
	public RadarTargetType getTargetType() {
		return RadarTargetType.ARTILLERY;
	}
	
	@Override
	public int approachNum() {
		return 0; //
	}
}
