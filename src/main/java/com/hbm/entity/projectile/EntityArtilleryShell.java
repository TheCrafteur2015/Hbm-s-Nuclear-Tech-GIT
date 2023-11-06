package com.hbm.entity.projectile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.logic.IChunkLoader;
import com.hbm.items.weapon.ItemAmmoArty;
import com.hbm.items.weapon.ItemAmmoArty.ArtilleryShell;
import com.hbm.main.MainRegistry;

import api.hbm.entity.IRadarDetectable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

public class EntityArtilleryShell extends EntityThrowableNT implements IChunkLoader, IRadarDetectable {

	private Ticket loaderTicket;
	
	private int turnProgress;
	private double syncPosX;
	private double syncPosY;
	private double syncPosZ;
	private double syncYaw;
	private double syncPitch;
	@SideOnly(Side.CLIENT)
	private double velocityX;
	@SideOnly(Side.CLIENT)
	private double velocityY;
	@SideOnly(Side.CLIENT)
	private double velocityZ;

	private double targetX;
	private double targetY;
	private double targetZ;
	private boolean shouldWhistle = false;
	private boolean didWhistle = false;
	
	private ItemStack cargo = null;
	
	public EntityArtilleryShell(World world) {
		super(world);
		this.ignoreFrustumCheck = true;
		setSize(0.5F, 0.5F);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, this.worldObj, Type.ENTITY));
		this.dataWatcher.addObject(10, 0);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return true;
	}
	
	public EntityArtilleryShell setType(int type) {
		this.dataWatcher.updateObject(10, type);
		return this;
	}
	
	public ArtilleryShell getType() {
		try {
			return ItemAmmoArty.itemTypes[this.dataWatcher.getWatchableObjectInt(10)];
		} catch(Exception ex) {
			return ItemAmmoArty.itemTypes[0];
		}
	}
	
	public double[] getTarget() {
		return new double[] { this.targetX, this.targetY, this.targetZ };
	}
	
	public void setTarget(double x, double y, double z) {
		this.targetX = x;
		this.targetY = y;
		this.targetZ = z;
	}
	
	public double getTargetHeight() {
		return this.targetY;
	}
	
	public void setWhistle(boolean whistle) {
		this.shouldWhistle = whistle;
	}
	
	public boolean getWhistle() {
		return this.shouldWhistle;
	}
	
	public boolean didWhistle() {
		return this.didWhistle;
	}
	
	@Override
	public void onUpdate() {
		
		if(!this.worldObj.isRemote) {
			super.onUpdate();
			
			if(!this.didWhistle && this.shouldWhistle) {
				double speed = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
				double deltaX = this.posX - this.targetX;
				double deltaZ = this.posZ - this.targetZ;
				double dist = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
				
				if(speed * 18 > dist) {
					this.worldObj.playSoundEffect(this.targetX, this.targetY, this.targetZ, "hbm:turret.mortarWhistle", 15.0F, 0.9F + this.rand.nextFloat() * 0.2F);
					this.didWhistle = true;
				}
			}

			loadNeighboringChunks((int)Math.floor(this.posX / 16D), (int)Math.floor(this.posZ / 16D));
			getType().onUpdate(this);
			
		} else {
			if(this.turnProgress > 0) {
				double interpX = this.posX + (this.syncPosX - this.posX) / this.turnProgress;
				double interpY = this.posY + (this.syncPosY - this.posY) / this.turnProgress;
				double interpZ = this.posZ + (this.syncPosZ - this.posZ) / this.turnProgress;
				double d = MathHelper.wrapAngleTo180_double(this.syncYaw - this.rotationYaw);
				this.rotationYaw = (float) (this.rotationYaw + d / this.turnProgress);
				this.rotationPitch = (float)(this.rotationPitch + (this.syncPitch - this.rotationPitch) / this.turnProgress);
				--this.turnProgress;
				setPosition(interpX, interpY, interpZ);
			} else {
				setPosition(this.posX, this.posY, this.posZ);
			}
			
			if(Vec3.createVectorHelper(this.syncPosX - this.posX, this.syncPosY - this.posY, this.syncPosZ - this.posZ).lengthVector() < 0.2) {
				this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5, this.posZ, 0.0, 0.1, 0.0);
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void setVelocity(double p_70016_1_, double p_70016_3_, double p_70016_5_) {
		this.velocityX = this.motionX = p_70016_1_;
		this.velocityY = this.motionY = p_70016_3_;
		this.velocityZ = this.motionZ = p_70016_5_;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int theNumberThree) {
		this.syncPosX = x;
		this.syncPosY = y;
		this.syncPosZ = z;
		this.syncYaw = yaw;
		this.syncPitch = pitch;
		this.turnProgress = theNumberThree;
		this.motionX = this.velocityX;
		this.motionY = this.velocityY;
		this.motionZ = this.velocityZ;
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		
		if(!this.worldObj.isRemote) {
			
			if(mop.typeOfHit == MovingObjectType.ENTITY && mop.entityHit instanceof EntityArtilleryShell) return;
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
		
		nbt.setInteger("type", this.dataWatcher.getWatchableObjectInt(10));
		nbt.setBoolean("shouldWhistle", this.shouldWhistle);
		nbt.setBoolean("didWhistle", this.didWhistle);
		nbt.setDouble("targetX", this.targetX);
		nbt.setDouble("targetY", this.targetY);
		nbt.setDouble("targetZ", this.targetZ);

		if(this.cargo != null)
			nbt.setTag("cargo", this.cargo.writeToNBT(new NBTTagCompound()));
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);

		this.dataWatcher.updateObject(10, nbt.getInteger("type"));
		this.shouldWhistle = nbt.getBoolean("shouldWhistle");
		this.didWhistle = nbt.getBoolean("didWhistle");
		this.targetX = nbt.getDouble("targetX");
		this.targetY = nbt.getDouble("targetY");
		this.targetZ = nbt.getDouble("targetZ");

		NBTTagCompound compound = nbt.getCompoundTag("cargo");
		setCargo(ItemStack.loadItemStackFromNBT(compound));
	}

	@Override
	protected float getAirDrag() {
		return 1.0F;
	}

	@Override
	public double getGravityVelocity() {
		return 9.81 * 0.05;
	}

	@Override
	protected int groundDespawn() {
		return this.cargo != null ? 0 : 1200;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public boolean canAttackWithItem() {
		return true;
	}
	
	public void setCargo(ItemStack stack) {
		this.cargo = stack;
	}

	@Override
	public boolean interactFirst(EntityPlayer player) {

		if(!this.worldObj.isRemote) {
			if(this.cargo != null) {
				player.inventory.addItemStackToInventory(this.cargo.copy());
				player.inventoryContainer.detectAndSendChanges();
			}
			setDead();
		}

		return false;
	}

	@Override
	public RadarTargetType getTargetType() {
		return RadarTargetType.ARTILLERY;
	}
}
