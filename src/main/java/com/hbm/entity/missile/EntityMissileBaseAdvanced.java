package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.logic.IChunkLoader;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacket;
import com.hbm.packet.PacketDispatcher;

import api.hbm.entity.IRadarDetectable;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

public abstract class EntityMissileBaseAdvanced extends Entity implements IChunkLoader, IRadarDetectable {
	
	int startX;
	int startZ;
	int targetX;
	int targetZ;
	public int velocity;
	double decelY;
	double accelXZ;
	boolean isCluster = false;
    private Ticket loaderTicket;
    public int health = 50;

	public EntityMissileBaseAdvanced(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
		this.startX = (int) this.posX;
		this.startZ = (int) this.posZ;
		this.targetX = (int) this.posX;
		this.targetZ = (int) this.posZ;
	}
	
	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_) {
		if(isEntityInvulnerable()) {
			return false;
		} else {
			if(!this.isDead && !this.worldObj.isRemote) {
				this.health -= p_70097_2_;

				if(this.health <= 0) {
					setDead();
					killMissile();
				}
			}

			return true;
		}
	}

	private void killMissile() {
		ExplosionLarge.explode(this.worldObj, this.posX, this.posY, this.posZ, 5, true, false, true);
		ExplosionLarge.spawnShrapnelShower(this.worldObj, this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ, 15, 0.075);
		ExplosionLarge.spawnMissileDebris(this.worldObj, this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ, 0.25, getDebris(), getDebrisRareDrop());
	}

	public EntityMissileBaseAdvanced(World world, float x, float y, float z, int a, int b) {
		super(world);
		this.ignoreFrustumCheck = true;
		setLocationAndAngles(x, y, z, 0, 0);
		this.startX = (int) x;
		this.startZ = (int) z;
		this.targetX = a;
		this.targetZ = b;
		this.motionY = 2;
		
		Vec3 vector = Vec3.createVectorHelper(this.targetX - this.startX, 0, this.targetZ - this.startZ);
		this.accelXZ = this.decelY = 1 / vector.lengthVector();
		this.decelY *= 2;

		this.velocity = 1;

		setSize(1.5F, 1.5F);
	}

	@Override
	protected void entityInit() {
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, this.worldObj, Type.ENTITY));
		this.dataWatcher.addObject(8, Integer.valueOf(this.health));
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.motionX = nbt.getDouble("moX");
		this.motionY = nbt.getDouble("moY");
		this.motionZ = nbt.getDouble("moZ");
		this.posX = nbt.getDouble("poX");
		this.posY = nbt.getDouble("poY");
		this.posZ = nbt.getDouble("poZ");
		this.decelY = nbt.getDouble("decel");
		this.accelXZ = nbt.getDouble("accel");
		this.targetX = nbt.getInteger("tX");
		this.targetZ = nbt.getInteger("tZ");
		this.startX = nbt.getInteger("sX");
		this.startZ = nbt.getInteger("sZ");
		this.velocity = nbt.getInteger("veloc");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setDouble("moX", this.motionX);
		nbt.setDouble("moY", this.motionY);
		nbt.setDouble("moZ", this.motionZ);
		nbt.setDouble("poX", this.posX);
		nbt.setDouble("poY", this.posY);
		nbt.setDouble("poZ", this.posZ);
		nbt.setDouble("decel", this.decelY);
		nbt.setDouble("accel", this.accelXZ);
		nbt.setInteger("tX", this.targetX);
		nbt.setInteger("tZ", this.targetZ);
		nbt.setInteger("sX", this.startX);
		nbt.setInteger("sZ", this.startZ);
		nbt.setInteger("veloc", this.velocity);
	}
	
	protected void rotation() {
        float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

        for (this.rotationPitch = (float)(Math.atan2(this.motionY, f2) * 180.0D / Math.PI) - 90; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
        {
            ;
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
        {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F)
        {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
        {
            this.prevRotationYaw += 360.0F;
        }
	}
	
	@Override
    public void onUpdate() {
		
		if(this.velocity < 1)
			this.velocity = 1;
		if(this.ticksExisted > 40)
			this.velocity = 3;
		else if(this.ticksExisted > 20)
			this.velocity = 2;
		
        this.dataWatcher.updateObject(8, Integer.valueOf(this.health));
        
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
		
        //TODO: instead of crappy skipping, implement a hitscan
		for(int i = 0; i < this.velocity; i++) {
	        //this.posX += this.motionX;
	        //this.posY += this.motionY;
	        //this.posZ += this.motionZ;
			setLocationAndAngles(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ, 0, 0);
	        
	        rotation();
	        
	        this.motionY -= this.decelY;
	        
	        Vec3 vector = Vec3.createVectorHelper(this.targetX - this.startX, 0, this.targetZ - this.startZ);
	        vector = vector.normalize();
	        vector.xCoord *= this.accelXZ;
	        vector.zCoord *= this.accelXZ;
	        
	        if(this.motionY > 0) {
	        	this.motionX += vector.xCoord;
	        	this.motionZ += vector.zCoord;
	        }
	        
	        if(this.motionY < 0) {
	        	this.motionX -= vector.xCoord;
	        	this.motionZ -= vector.zCoord;
	        }
	
			if(!this.worldObj.isRemote)
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacket(this.posX, this.posY, this.posZ, 2),
						new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 300));
	        
	        if(this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ) != Blocks.air && 
        			this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ) != Blocks.water && 
        			this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ) != Blocks.flowing_water) {
        	
				if(!this.worldObj.isRemote) {
					onImpact();
				}
				killAndClear();
				return;
			}

			loadNeighboringChunks((int) (this.posX / 16), (int) (this.posZ / 16));

			if(this.motionY < -1 && this.isCluster && !this.worldObj.isRemote) {
				cluster();
				setDead();
				return;
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return true;
	}

	public abstract void onImpact();

	public abstract List<ItemStack> getDebris();
	
	public abstract ItemStack getDebrisRareDrop();
	
	public void cluster() { }
	
	@Override
	public void init(Ticket ticket) {
		if(!this.worldObj.isRemote) {
			
            if(ticket != null) {
            	
                if(this.loaderTicket == null) {
                	
                	this.loaderTicket = ticket;
                	this.loaderTicket.bindEntity(this);
                	this.loaderTicket.getModData();
                }

                ForgeChunkManager.forceChunk(this.loaderTicket, new ChunkCoordIntPair(this.chunkCoordX, this.chunkCoordZ));
            }
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
}
