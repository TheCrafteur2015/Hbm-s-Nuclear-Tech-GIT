package com.hbm.entity.logic;

import java.util.List;

import org.apache.logging.log4j.Level;

import com.hbm.config.BombConfig;
import com.hbm.config.GeneralConfig;
import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.explosion.ExplosionNukeRayBatched;
import com.hbm.main.MainRegistry;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityNukeExplosionMK5 extends EntityExplosionChunkloading {
	
	//Strength of the blast
	public int strength;
	//How many rays are calculated per tick
	public int speed;
	public int length;
	
	public boolean mute = false;
	
	public boolean fallout = true;
	private int falloutAdd = 0;
	
	ExplosionNukeRayBatched explosion;

	public EntityNukeExplosionMK5(World p_i1582_1_) {
		super(p_i1582_1_);
	}
	
	public EntityNukeExplosionMK5(World world, int strength, int speed, int length) {
		super(world);
		this.strength = strength;
		this.speed = speed;
		this.length = length;
	}
	
	@Override
	public void onUpdate() {
		
		if(this.strength == 0) {
			clearChunkLoader();
			setDead();
			return;
		}

		if(!this.worldObj.isRemote) loadChunk((int) Math.floor(this.posX / 16D), (int) Math.floor(this.posZ / 16D));
		
		for(Object player : this.worldObj.playerEntities) {
			((EntityPlayer)player).triggerAchievement(MainRegistry.achManhattan);
		}
		
		if(!this.worldObj.isRemote && this.fallout && this.explosion != null && this.ticksExisted < 10) {
			radiate(500_000, this.length * 2);
		}
		
		if(!this.mute) {
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
			if(this.rand.nextInt(5) == 0)
				this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
		}
		
		ExplosionNukeGeneric.dealDamage(this.worldObj, this.posX, this.posY, this.posZ, this.length * 2);
		
		if(this.explosion == null) {
			this.explosion = new ExplosionNukeRayBatched(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, this.strength, this.speed, this.length);
		}
		
		if(!this.explosion.isAusf3Complete) {
			this.explosion.collectTip(this.speed * 10);
		} else if(this.explosion.perChunk.size() > 0) {
			long start = System.currentTimeMillis();
			
			while(this.explosion.perChunk.size() > 0 && System.currentTimeMillis() < start + BombConfig.mk5) this.explosion.processChunk();
			
		} else if(this.fallout) {

			EntityFalloutRain fallout = new EntityFalloutRain(this.worldObj);
			fallout.posX = this.posX;
			fallout.posY = this.posY;
			fallout.posZ = this.posZ;
			fallout.setScale((int)(this.length * 2.5 + this.falloutAdd) * BombConfig.falloutRange / 100);

			this.worldObj.spawnEntityInWorld(fallout);

			clearChunkLoader();
			setDead();
		} else {
			clearChunkLoader();
			setDead();
		}
	}
	
	private void radiate(float rads, double range) {
		
		List<EntityLivingBase> entities = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(this.posX, this.posY, this.posZ, this.posX, this.posY, this.posZ).expand(range, range, range));
		
		for(EntityLivingBase e : entities) {
			
			Vec3 vec = Vec3.createVectorHelper(e.posX - this.posX, (e.posY + e.getEyeHeight()) - this.posY, e.posZ - this.posZ);
			double len = vec.lengthVector();
			vec = vec.normalize();
			
			float res = 0;
			
			for(int i = 1; i < len; i++) {

				int ix = (int)Math.floor(this.posX + vec.xCoord * i);
				int iy = (int)Math.floor(this.posY + vec.yCoord * i);
				int iz = (int)Math.floor(this.posZ + vec.zCoord * i);
				
				res += this.worldObj.getBlock(ix, iy, iz).getExplosionResistance(null);
			}
			
			if(res < 1)
				res = 1;
			
			float eRads = rads;
			eRads /= (float)res;
			eRads /= (float)(len * len);
			
			ContaminationUtil.contaminate(e, HazardType.RADIATION, ContaminationType.CREATIVE, eRads);
		}
	}

	@Override
	protected void entityInit() { }

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.ticksExisted = nbt.getInteger("ticksExisted");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("ticksExisted", this.ticksExisted);
	}
	
	public static EntityNukeExplosionMK5 statFac(World world, int r, double x, double y, double z) {
		
		if(GeneralConfig.enableExtendedLogging && !world.isRemote)
			MainRegistry.logger.log(Level.INFO, "[NUKE] Initialized explosion at " + x + " / " + y + " / " + z + " with strength " + r + "!");
		
		if(r == 0)
			r = 25;
		
		r *= 2;
		
		EntityNukeExplosionMK5 mk5 = new EntityNukeExplosionMK5(world);
		mk5.strength = (int)(r);
		mk5.speed = (int)Math.ceil(100000 / mk5.strength);
		mk5.setPosition(x, y, z);
		mk5.length = mk5.strength / 2;
		return mk5;
	}
	
	public static EntityNukeExplosionMK5 statFacNoRad(World world, int r, double x, double y, double z) {
		
		EntityNukeExplosionMK5 mk5 = EntityNukeExplosionMK5.statFac(world, r, x, y ,z);
		mk5.fallout = false;
		return mk5;
	}
	
	public EntityNukeExplosionMK5 moreFallout(int fallout) {
		this.falloutAdd = fallout;
		return this;
	}
	
	public EntityNukeExplosionMK5 mute() {
		this.mute = true;
		return this;
	}
}
