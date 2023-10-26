package com.hbm.entity.logic;

import org.apache.logging.log4j.Level;

import com.hbm.config.GeneralConfig;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.explosion.ExplosionTom;
import com.hbm.main.MainRegistry;
import com.hbm.saveddata.TomSaveData;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityTomBlast extends EntityExplosionChunkloading {

	public int age = 0;
	public int destructionRange = 0;
	public ExplosionTom exp;
	public int speed = 1;
	public boolean did = false;

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.age = nbt.getInteger("age");
		this.destructionRange = nbt.getInteger("destructionRange");
		this.speed = nbt.getInteger("speed");
		this.did = nbt.getBoolean("did");

		this.exp = new ExplosionTom((int) this.posX, (int) this.posY, (int) this.posZ, this.worldObj, this.destructionRange);
		this.exp.readFromNbt(nbt, "exp_");

		this.did = true;

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("age", this.age);
		nbt.setInteger("destructionRange", this.destructionRange);
		nbt.setInteger("speed", this.speed);
		nbt.setBoolean("did", this.did);

		if(this.exp != null)
			this.exp.saveToNbt(nbt, "exp_");

	}

	public EntityTomBlast(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if(!this.worldObj.isRemote) loadChunk((int) Math.floor(this.posX / 16D), (int) Math.floor(this.posZ / 16D));

		if(!this.did) {

			if(GeneralConfig.enableExtendedLogging && !this.worldObj.isRemote)
				MainRegistry.logger.log(Level.INFO, "[NUKE] Initialized TOM explosion at " + this.posX + " / " + this.posY + " / " + this.posZ + " with strength " + this.destructionRange + "!");

			this.exp = new ExplosionTom((int) this.posX, (int) this.posY, (int) this.posZ, this.worldObj, this.destructionRange);

			this.did = true;
		}

		this.speed += 1; // increase speed to keep up with expansion

		boolean flag = false;
		for(int i = 0; i < this.speed; i++) {
			flag = this.exp.update();

			if(flag) {
				setDead();
				TomSaveData data = TomSaveData.forWorld(this.worldObj);
				data.impact = true;
				data.fire = 1F;
				data.markDirty();
			}
		}

		if(this.rand.nextInt(5) == 0)
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);

		if(!flag) {
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
			ExplosionNukeGeneric.dealDamage(this.worldObj, this.posX, this.posY, this.posZ, this.destructionRange * 2);
		}

		this.age++;
	}
}
