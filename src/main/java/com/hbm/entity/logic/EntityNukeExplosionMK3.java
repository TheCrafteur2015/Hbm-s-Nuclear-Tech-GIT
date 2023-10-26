package com.hbm.entity.logic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.logging.log4j.Level;

import com.hbm.config.BombConfig;
import com.hbm.config.GeneralConfig;
import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.explosion.ExplosionFleija;
import com.hbm.explosion.ExplosionHurtUtil;
import com.hbm.explosion.ExplosionNukeAdvanced;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.explosion.ExplosionSolinium;
import com.hbm.interfaces.Spaghetti;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

@Spaghetti("why???")
public class EntityNukeExplosionMK3 extends EntityExplosionChunkloading {
	
	public int age = 0;
	public int destructionRange = 0;
	public ExplosionNukeAdvanced exp;
	public ExplosionNukeAdvanced wst;
	public ExplosionNukeAdvanced vap;
	public ExplosionFleija expl;
	public ExplosionSolinium sol;
	public int speed = 1;
	public float coefficient = 1;
	public float coefficient2 = 1;
	public boolean did = false;
	public boolean did2 = false;
	public boolean waste = true;
	//Extended Type
	public int extType = 0;

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.age = nbt.getInteger("age");
		this.destructionRange = nbt.getInteger("destructionRange");
		this.speed = nbt.getInteger("speed");
		this.coefficient = nbt.getFloat("coefficient");
		this.coefficient2 = nbt.getFloat("coefficient2");
		this.did = nbt.getBoolean("did");
		this.did2 = nbt.getBoolean("did2");
		this.waste = nbt.getBoolean("waste");
		this.extType = nbt.getInteger("extType");
		
		long time = nbt.getLong("milliTime");
		
		if(BombConfig.limitExplosionLifespan > 0 && System.currentTimeMillis() - time > BombConfig.limitExplosionLifespan * 1000) {
			clearChunkLoader();
			setDead();
		}
		
		if(this.waste) {
			this.exp = new ExplosionNukeAdvanced((int) this.posX, (int) this.posY, (int) this.posZ, this.worldObj, this.destructionRange, this.coefficient, 0);
			this.exp.readFromNbt(nbt, "exp_");
			this.wst = new ExplosionNukeAdvanced((int) this.posX, (int) this.posY, (int) this.posZ, this.worldObj, (int) (this.destructionRange * 1.8), this.coefficient, 2);
			this.wst.readFromNbt(nbt, "wst_");
			this.vap = new ExplosionNukeAdvanced((int) this.posX, (int) this.posY, (int) this.posZ, this.worldObj, (int) (this.destructionRange * 2.5), this.coefficient, 1);
			this.vap.readFromNbt(nbt, "vap_");
		} else {

			if(this.extType == 0) {
				this.expl = new ExplosionFleija((int) this.posX, (int) this.posY, (int) this.posZ, this.worldObj, this.destructionRange, this.coefficient, this.coefficient2);
				this.expl.readFromNbt(nbt, "expl_");
			}
			if(this.extType == 1) {
				this.sol = new ExplosionSolinium((int) this.posX, (int) this.posY, (int) this.posZ, this.worldObj, this.destructionRange, this.coefficient, this.coefficient2);
				this.sol.readFromNbt(nbt, "sol_");
			}
		}

		this.did = true;

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("age", this.age);
		nbt.setInteger("destructionRange", this.destructionRange);
		nbt.setInteger("speed", this.speed);
		nbt.setFloat("coefficient", this.coefficient);
		nbt.setFloat("coefficient2", this.coefficient2);
		nbt.setBoolean("did", this.did);
		nbt.setBoolean("did2", this.did2);
		nbt.setBoolean("waste", this.waste);
		nbt.setInteger("extType", this.extType);
		
		nbt.setLong("milliTime", System.currentTimeMillis());
    	
		if(this.exp != null)
			this.exp.saveToNbt(nbt, "exp_");
		if(this.wst != null)
			this.wst.saveToNbt(nbt, "wst_");
		if(this.vap != null)
			this.vap.saveToNbt(nbt, "vap_");
		if(this.expl != null)
			this.expl.saveToNbt(nbt, "expl_");
		if(this.sol != null)
			this.sol.saveToNbt(nbt, "sol_");
		
	}

	public EntityNukeExplosionMK3(World p_i1582_1_) {
		super(p_i1582_1_);
	}

    @Override
	public void onUpdate() {
        super.onUpdate();

		if(!this.worldObj.isRemote) loadChunk((int) Math.floor(this.posX / 16D), (int) Math.floor(this.posZ / 16D));
        	
        if(!this.did)
        {
        	for(Object player : this.worldObj.playerEntities)
    			((EntityPlayer)player).triggerAchievement(MainRegistry.achManhattan);
        	
    		if(GeneralConfig.enableExtendedLogging && !this.worldObj.isRemote)
    			MainRegistry.logger.log(Level.INFO, "[NUKE] Initialized mk3 explosion at " + this.posX + " / " + this.posY + " / " + this.posZ + " with strength " + this.destructionRange + "!");
    		
        	if(this.waste)
        	{
            	this.exp = new ExplosionNukeAdvanced((int)this.posX, (int)this.posY, (int)this.posZ, this.worldObj, this.destructionRange, this.coefficient, 0);
        		this.wst = new ExplosionNukeAdvanced((int)this.posX, (int)this.posY, (int)this.posZ, this.worldObj, (int)(this.destructionRange * 1.8), this.coefficient, 2);
        		this.vap = new ExplosionNukeAdvanced((int)this.posX, (int)this.posY, (int)this.posZ, this.worldObj, (int)(this.destructionRange * 2.5), this.coefficient, 1);
        	} else {
        		if(this.extType == 0)
        			this.expl = new ExplosionFleija((int)this.posX, (int)this.posY, (int)this.posZ, this.worldObj, this.destructionRange, this.coefficient, this.coefficient2);
        		if(this.extType == 1)
        			this.sol = new ExplosionSolinium((int)this.posX, (int)this.posY, (int)this.posZ, this.worldObj, this.destructionRange, this.coefficient, this.coefficient2);
        	}
        	
        	this.did = true;
        }
        
        this.speed += 1;	//increase speed to keep up with expansion
        
        boolean flag = false;
        boolean flag3 = false;
        
		for(int i = 0; i < this.speed; i++) {
			if(this.waste) {
				flag = this.exp.update();
				this.wst.update();
				flag3 = this.vap.update();

				if(flag3) {
					clearChunkLoader();
					setDead();
				}
			} else {
				if(this.extType == 0) {
					if(this.expl.update()) {
						clearChunkLoader();
						setDead();
					}
				}
				if(this.extType == 1) {
					if(this.sol.update()) {
						clearChunkLoader();
						setDead();
					}
				}
			}
		}
        	
        if(!flag)
        {
        	this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
        	
        	if(this.waste || this.extType != 1) {
        		ExplosionNukeGeneric.dealDamage(this.worldObj, this.posX, this.posY, this.posZ, this.destructionRange * 2);
        	} else {
        		ExplosionHurtUtil.doRadiation(this.worldObj, this.posX, this.posY, this.posZ, 15000, 250000, this.destructionRange);
        	}
        	
        } else {
			if (!this.did2 && this.waste) {
				EntityFalloutRain fallout = new EntityFalloutRain(this.worldObj, (int)(this.destructionRange * 1.8) * 10);
				fallout.posX = this.posX;
				fallout.posY = this.posY;
				fallout.posZ = this.posZ;
				fallout.setScale((int)(this.destructionRange * 1.8));

				this.worldObj.spawnEntityInWorld(fallout);
				//this.worldObj.getWorldInfo().setRaining(true);
				
				this.did2 = true;
			}
		}

		this.age++;
	}
	
	public static HashMap<ATEntry, Long> at = new HashMap();
	
	public static EntityNukeExplosionMK3 statFacFleija(World world, double x, double y, double z, int range) {
		
		EntityNukeExplosionMK3 entity = new EntityNukeExplosionMK3(world);
		entity.posX = x;
		entity.posY = y;
		entity.posZ = z;
		entity.destructionRange = range;
		entity.speed = BombConfig.blastSpeed;
		entity.coefficient = 1.0F;
		entity.waste = false;
		
		Iterator<Entry<ATEntry, Long>> it = EntityNukeExplosionMK3.at.entrySet().iterator();
		
		while(it.hasNext()) {
			
			Entry<ATEntry, Long> next = it.next();
			if(next.getValue() < world.getTotalWorldTime()) {
				it.remove();
				continue;
			}
			
			ATEntry entry = next.getKey();
			if(entry.dim != world.provider.dimensionId)  continue;
			
			Vec3 vec = Vec3.createVectorHelper(x - entry.x, y - entry.y, z - entry.z);
			
			if(vec.lengthVector() < 300) {
				entity.setDead();

				/* just to make sure */
				if(!world.isRemote) {
					
					for(int i = 0; i < 2; i++) {
						double ix = i == 0 ? x : (entry.x + 0.5);
						double iy = i == 0 ? y : (entry.y + 0.5);
						double iz = i == 0 ? z : (entry.z + 0.5);
						
						world.playSoundEffect(ix, iy, iz, "hbm:entity.ufoBlast", 15.0F, 0.7F + world.rand.nextFloat() * 0.2F);
						
						NBTTagCompound data = new NBTTagCompound();
						data.setString("type", "plasmablast");
						data.setFloat("r", 0.0F);
						data.setFloat("g", 0.75F);
						data.setFloat("b", 1.0F);
						data.setFloat("scale", 7.5F);
						PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, ix, iy, iz), new TargetPoint(entry.dim, ix, iy, iz, 150));
					}
				}
				
				break;
			}
		}
		
		return entity;
	}
	
	public EntityNukeExplosionMK3 makeSol() {
		this.extType = 1;
		return this;
	}
	
	public static class ATEntry {
		public int dim;
		public int x;
		public int y;
		public int z;
		
		public ATEntry(int dim, int x, int y, int z) {
			this.dim = dim;
			this.x = x;
			this.y = y;
			this.z = z;
		}

		@Override
		public int hashCode() {
			final int prime = 27644437;
			int result = 1;
			result = prime * result + this.dim;
			result = prime * result + this.x;
			result = prime * result + this.y;
			result = prime * result + this.z;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if(this == obj)
				return true;
			if((obj == null) || (getClass() != obj.getClass()))
				return false;
			ATEntry other = (ATEntry) obj;
			if(this.dim != other.dim)
				return false;
			if(this.x != other.x)
				return false;
			if(this.y != other.y)
				return false;
			if(this.z != other.z)
				return false;
			return true;
		}
	}
}
