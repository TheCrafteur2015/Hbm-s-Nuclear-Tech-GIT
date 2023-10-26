package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.BlockTaint;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityBalefire;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.entity.logic.IChunkLoader;
import com.hbm.entity.projectile.EntityBulletBaseNT;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.MissileStruct;
import com.hbm.items.weapon.ItemMissile;
import com.hbm.items.weapon.ItemMissile.FuelType;
import com.hbm.items.weapon.ItemMissile.PartSize;
import com.hbm.items.weapon.ItemMissile.WarheadType;
import com.hbm.main.MainRegistry;

import api.hbm.entity.IRadarDetectable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

public class EntityMissileCustom extends Entity implements IChunkLoader, IRadarDetectable {

	int startX;
	int startZ;
	int targetX;
	int targetZ;
	double velocity;
	double decelY;
	double accelXZ;
	float fuel;
	float consumption;
	private Ticket loaderTicket;
	public int health = 50;
	MissileStruct template;

	public EntityMissileCustom(World p_i1582_1_) {
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
	}

	public EntityMissileCustom(World world, float x, float y, float z, int a, int b, MissileStruct template) {
		super(world);
		this.ignoreFrustumCheck = true;
		/*
		 * this.posX = x; this.posY = y; this.posZ = z;
		 */
		setLocationAndAngles(x, y, z, 0, 0);
		this.startX = (int) x;
		this.startZ = (int) z;
		this.targetX = a;
		this.targetZ = b;
		this.motionY = 2;

		this.template = template;

		this.dataWatcher.updateObject(9, Item.getIdFromItem(template.warhead));
		this.dataWatcher.updateObject(10, Item.getIdFromItem(template.fuselage));
		if(template.fins != null)
			this.dataWatcher.updateObject(11, Item.getIdFromItem(template.fins));
		else
			this.dataWatcher.updateObject(11, Integer.valueOf(0));
		this.dataWatcher.updateObject(12, Item.getIdFromItem(template.thruster));

		Vec3 vector = Vec3.createVectorHelper(this.targetX - this.startX, 0, this.targetZ - this.startZ);
		this.accelXZ = this.decelY = 1 / vector.lengthVector();
		this.decelY *= 2;

		this.velocity = 0.0;

		ItemMissile fuselage = (ItemMissile) template.fuselage;
		ItemMissile thruster = (ItemMissile) template.thruster;

		this.fuel = (Float) fuselage.attributes[1];
		this.consumption = (Float) thruster.attributes[1];

		setSize(1.5F, 1.5F);
	}

	@Override
	protected void entityInit() {
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, this.worldObj, Type.ENTITY));
		this.dataWatcher.addObject(8, Integer.valueOf(this.health));

		if(this.template != null) {
			this.dataWatcher.addObject(9, Integer.valueOf(Item.getIdFromItem(this.template.warhead)));
			this.dataWatcher.addObject(10, Integer.valueOf(Item.getIdFromItem(this.template.fuselage)));

			if(this.template.fins != null)
				this.dataWatcher.addObject(11, Integer.valueOf(Item.getIdFromItem(this.template.fins)));
			else
				this.dataWatcher.addObject(11, Integer.valueOf(0));

			this.dataWatcher.addObject(12, Integer.valueOf(Item.getIdFromItem(this.template.thruster)));
		} else {
			this.dataWatcher.addObject(9, Integer.valueOf(0));
			this.dataWatcher.addObject(10, Integer.valueOf(0));
			this.dataWatcher.addObject(11, Integer.valueOf(0));
			this.dataWatcher.addObject(12, Integer.valueOf(0));
		}
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
		this.fuel = nbt.getFloat("fuel");
		this.consumption = nbt.getFloat("consumption");
		this.dataWatcher.updateObject(9, nbt.getInteger("warhead"));
		this.dataWatcher.updateObject(10, nbt.getInteger("fuselage"));
		this.dataWatcher.updateObject(11, nbt.getInteger("fins"));
		this.dataWatcher.updateObject(12, nbt.getInteger("thruster"));
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
		nbt.setDouble("veloc", this.velocity);
		nbt.setFloat("fuel", this.fuel);
		nbt.setFloat("consumption", this.consumption);
		nbt.setInteger("warhead", this.dataWatcher.getWatchableObjectInt(9));
		nbt.setInteger("fuselage", this.dataWatcher.getWatchableObjectInt(10));
		nbt.setInteger("fins", this.dataWatcher.getWatchableObjectInt(11));
		nbt.setInteger("thruster", this.dataWatcher.getWatchableObjectInt(12));
	}

	protected void rotation() {
		float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

		for(this.rotationPitch = (float) (Math.atan2(this.motionY, f2) * 180.0D / Math.PI) - 90; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
			;
		}

		while(this.rotationPitch - this.prevRotationPitch >= 180.0F) {
			this.prevRotationPitch += 360.0F;
		}

		while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
			this.prevRotationYaw -= 360.0F;
		}

		while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
			this.prevRotationYaw += 360.0F;
		}
	}

	@Override
	public void onUpdate() {
		this.dataWatcher.updateObject(8, Integer.valueOf(this.health));

		setLocationAndAngles(this.posX + this.motionX * this.velocity, this.posY + this.motionY * this.velocity, this.posZ + this.motionZ * this.velocity, 0, 0);

		rotation();

		if(this.fuel > 0 || this.worldObj.isRemote) {

			this.fuel -= this.consumption;

			this.motionY -= this.decelY * this.velocity;

			Vec3 vector = Vec3.createVectorHelper(this.targetX - this.startX, 0, this.targetZ - this.startZ);
			vector = vector.normalize();
			vector.xCoord *= this.accelXZ * this.velocity;
			vector.zCoord *= this.accelXZ * this.velocity;

			if(this.motionY > 0) {
				this.motionX += vector.xCoord;
				this.motionZ += vector.zCoord;
			}

			if(this.motionY < 0) {
				this.motionX -= vector.xCoord;
				this.motionZ -= vector.zCoord;
			}

			if(this.velocity < 5)
				this.velocity += 0.01;
		} else {

			this.motionX *= 0.99;
			this.motionZ *= 0.99;

			if(this.motionY > -1.5)
				this.motionY -= 0.05;
		}

		if(this.worldObj.getBlock((int) this.posX, (int) this.posY, (int) this.posZ) != Blocks.air && this.worldObj.getBlock((int) this.posX, (int) this.posY, (int) this.posZ) != Blocks.water && this.worldObj.getBlock((int) this.posX, (int) this.posY, (int) this.posZ) != Blocks.flowing_water) {

			if(!this.worldObj.isRemote) {
				onImpact();
			}
			setDead();
			return;
		}

		if(this.worldObj.isRemote) {

			Vec3 v = Vec3.createVectorHelper(this.motionX, this.motionY, this.motionZ);
			v = v.normalize();

			String smoke = "";

			ItemMissile part = (ItemMissile) Item.getItemById(this.dataWatcher.getWatchableObjectInt(10));
			FuelType type = (FuelType) part.attributes[0];

			switch(type) {
			case BALEFIRE:
				smoke = "exBalefire";
				break;
			case HYDROGEN:
				smoke = "exHydrogen";
				break;
			case KEROSENE:
				smoke = "exKerosene";
				break;
			case SOLID:
				smoke = "exSolid";
				break;
			case XENON:
				break;
			}

			for(int i = 0; i < this.velocity; i++)
				MainRegistry.proxy.spawnParticle(this.posX - v.xCoord * i, this.posY - v.yCoord * i, this.posZ - v.zCoord * i, smoke, null);
		}

		loadNeighboringChunks((int) (this.posX / 16), (int) (this.posZ / 16));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return distance < 2500000;
	}

	public void onImpact() {

		ItemMissile part = (ItemMissile) Item.getItemById(this.dataWatcher.getWatchableObjectInt(9));

		WarheadType type = (WarheadType) part.attributes[0];
		float strength = (Float) part.attributes[1];

		switch(type) {
		case HE:
			ExplosionLarge.explode(this.worldObj, this.posX, this.posY, this.posZ, strength, true, false, true);
			ExplosionLarge.jolt(this.worldObj, this.posX, this.posY, this.posZ, strength, (int) (strength * 50), 0.25);
			break;
		case INC:
			ExplosionLarge.explodeFire(this.worldObj, this.posX, this.posY, this.posZ, strength, true, false, true);
			ExplosionLarge.jolt(this.worldObj, this.posX, this.posY, this.posZ, strength * 1.5, (int) (strength * 50), 0.25);
			break;
		case CLUSTER:
			break;
		case BUSTER:
			ExplosionLarge.buster(this.worldObj, this.posX, this.posY, this.posZ, Vec3.createVectorHelper(this.motionX, this.motionY, this.motionZ), strength, strength * 4);
			break;
		case NUCLEAR:
		case TX:
			this.worldObj.spawnEntityInWorld(EntityNukeExplosionMK5.statFac(this.worldObj, (int) strength, this.posX, this.posY, this.posZ));
			EntityNukeTorex.statFac(this.worldObj, this.posX, this.posY, this.posZ, strength);
			break;
		case BALEFIRE:
			EntityBalefire bf = new EntityBalefire(this.worldObj);
			bf.posX = this.posX;
			bf.posY = this.posY;
			bf.posZ = this.posZ;
			bf.destructionRange = (int) strength;
			this.worldObj.spawnEntityInWorld(bf);
			EntityNukeTorex.statFacBale(this.worldObj, this.posX, this.posY, this.posZ, strength);
			break;
		case N2:
			this.worldObj.spawnEntityInWorld(EntityNukeExplosionMK5.statFacNoRad(this.worldObj, (int) strength, this.posX, this.posY, this.posZ));
			EntityNukeTorex.statFac(this.worldObj, this.posX, this.posY, this.posZ, strength);
			break;
		case TAINT:
			int r = (int) strength;
			for(int i = 0; i < r * 10; i++) {
				int a = this.rand.nextInt(r) + (int) this.posX - (r / 2 - 1);
				int b = this.rand.nextInt(r) + (int) this.posY - (r / 2 - 1);
				int c = this.rand.nextInt(r) + (int) this.posZ - (r / 2 - 1);
				if(this.worldObj.getBlock(a, b, c).isReplaceable(this.worldObj, a, b, c) && BlockTaint.hasPosNeightbour(this.worldObj, a, b, c)) {
					this.worldObj.setBlock(a, b, c, ModBlocks.taint, this.rand.nextInt(3) + 4, 2);
				}
			}
			break;
		case CLOUD:
			this.worldObj.playAuxSFX(2002, (int) Math.round(this.posX), (int) Math.round(this.posY), (int) Math.round(this.posZ), 0);
			ExplosionChaos.spawnChlorine(this.worldObj, this.posX - this.motionX, this.posY - this.motionY, this.posZ - this.motionZ, 750, 2.5, 2);
			break;
		case TURBINE:
			ExplosionLarge.explode(this.worldObj, this.posX, this.posY, this.posZ, 10, true, false, true);
			int count = (int) strength;
			Vec3 vec = Vec3.createVectorHelper(0.5, 0, 0);

			for(int i = 0; i < count; i++) {
				EntityBulletBaseNT blade = new EntityBulletBaseNT(this.worldObj, BulletConfigSyncingUtil.TURBINE);
				blade.setPositionAndRotation(this.posX - this.motionX, this.posY - this.motionY + this.rand.nextGaussian(), this.posZ - this.motionZ, 0, 0);
				blade.motionX = vec.xCoord;
				blade.motionZ = vec.zCoord;
				this.worldObj.spawnEntityInWorld(blade);
				vec.rotateAroundY((float) (Math.PI * 2F / (float) count));
			}

			break;
		default:
			break;

		}
	}

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
			for(ChunkCoordIntPair chunk : this.loadedChunks) {
				ForgeChunkManager.unforceChunk(this.loaderTicket, chunk);
			}

			this.loadedChunks.clear();
			this.loadedChunks.add(new ChunkCoordIntPair(newChunkX, newChunkZ));
			this.loadedChunks.add(new ChunkCoordIntPair(newChunkX + 1, newChunkZ + 1));
			this.loadedChunks.add(new ChunkCoordIntPair(newChunkX - 1, newChunkZ - 1));
			this.loadedChunks.add(new ChunkCoordIntPair(newChunkX + 1, newChunkZ - 1));
			this.loadedChunks.add(new ChunkCoordIntPair(newChunkX - 1, newChunkZ + 1));
			this.loadedChunks.add(new ChunkCoordIntPair(newChunkX + 1, newChunkZ));
			this.loadedChunks.add(new ChunkCoordIntPair(newChunkX, newChunkZ + 1));
			this.loadedChunks.add(new ChunkCoordIntPair(newChunkX - 1, newChunkZ));
			this.loadedChunks.add(new ChunkCoordIntPair(newChunkX, newChunkZ - 1));

			for(ChunkCoordIntPair chunk : this.loadedChunks) {
				ForgeChunkManager.forceChunk(this.loaderTicket, chunk);
			}
		}
	}

	@Override
	public RadarTargetType getTargetType() {

		ItemMissile part = (ItemMissile) Item.getItemById(this.dataWatcher.getWatchableObjectInt(10));

		PartSize top = part.top;
		PartSize bottom = part.bottom;

		if(top == PartSize.SIZE_10 && bottom == PartSize.SIZE_10)
			return RadarTargetType.MISSILE_10;
		if(top == PartSize.SIZE_10 && bottom == PartSize.SIZE_15)
			return RadarTargetType.MISSILE_10_15;
		if(top == PartSize.SIZE_15 && bottom == PartSize.SIZE_15)
			return RadarTargetType.MISSILE_15;
		if(top == PartSize.SIZE_15 && bottom == PartSize.SIZE_20)
			return RadarTargetType.MISSILE_15_20;
		if(top == PartSize.SIZE_20 && bottom == PartSize.SIZE_20)
			return RadarTargetType.MISSILE_20;

		return RadarTargetType.PLAYER;
	}
}
