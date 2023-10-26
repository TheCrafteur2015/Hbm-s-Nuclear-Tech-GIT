package com.hbm.entity.projectile;

import com.hbm.config.WorldConfig;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.main.MainRegistry;
import com.hbm.world.feature.Meteorite;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityMeteor extends Entity {
	
	public boolean safe = false;

	public EntityMeteor(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
		setSize(4F, 4F);
	}

	@Override
	public void onUpdate() {
		
		if(!this.worldObj.isRemote && !WorldConfig.enableMeteorStrikes) {
			setDead();
			return;
		}

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		this.motionY -= 0.03;
		if(this.motionY < -2.5)
			this.motionY = -2.5;
		
		moveEntity(this.motionX, this.motionY, this.motionZ);

		if(!this.worldObj.isRemote && this.onGround && this.posY < 260) {
			
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 5 + this.rand.nextFloat(), !this.safe);
			if(WorldConfig.enableMeteorTails) {
				ExplosionLarge.spawnParticles(this.worldObj, this.posX, this.posY + 5, this.posZ, 75);
				ExplosionLarge.spawnParticles(this.worldObj, this.posX + 5, this.posY, this.posZ, 75);
				ExplosionLarge.spawnParticles(this.worldObj, this.posX - 5, this.posY, this.posZ, 75);
				ExplosionLarge.spawnParticles(this.worldObj, this.posX, this.posY, this.posZ + 5, 75);
				ExplosionLarge.spawnParticles(this.worldObj, this.posX, this.posY, this.posZ - 5, 75);
			}

			(new Meteorite()).generate(this.worldObj, this.rand, (int) Math.round(this.posX - 0.5D), (int) Math.round(this.posY - 0.5D), (int) Math.round(this.posZ - 0.5D), this.safe, true, true);
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "hbm:entity.oldExplosion", 10000.0F, 0.5F + this.rand.nextFloat() * 0.1F);
			setDead();
		}

		if(WorldConfig.enableMeteorTails && this.worldObj.isRemote) {

			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "exhaust");
			data.setString("mode", "meteor");
			data.setInteger("count", 10);
			data.setDouble("width", 1);
			data.setDouble("posX", this.posX - this.motionX);
			data.setDouble("posY", this.posY - this.motionY);
			data.setDouble("posZ", this.posZ - this.motionZ);

			MainRegistry.proxy.effectNT(data);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float p_70070_1_) {
		return 15728880;
	}

	@Override
	public float getBrightness(float p_70013_1_) {
		return 1.0F;
	}

	@Override
	protected void entityInit() { }

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.safe = nbt.getBoolean("safe");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setBoolean("safe", this.safe);
	}
}
