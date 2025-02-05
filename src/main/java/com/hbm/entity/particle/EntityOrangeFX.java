package com.hbm.entity.particle;

import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionNukeGeneric;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityOrangeFX extends EntityModFX {

	public EntityOrangeFX(World world) {
		super(world, 0, 0, 0);
	}

	public EntityOrangeFX(World p_i1225_1_, double p_i1225_2_, double p_i1225_4_, double p_i1225_6_, double p_i1225_8_, double p_i1225_10_, double p_i1225_12_) {
		this(p_i1225_1_, p_i1225_2_, p_i1225_4_, p_i1225_6_, p_i1225_8_, p_i1225_10_, p_i1225_12_, 1.0F);
	}

	public EntityOrangeFX(World p_i1226_1_, double p_i1226_2_, double p_i1226_4_, double p_i1226_6_, double p_i1226_8_, double p_i1226_10_, double p_i1226_12_, float p_i1226_14_) {
		super(p_i1226_1_, p_i1226_2_, p_i1226_4_, p_i1226_6_, 0.0D, 0.0D, 0.0D);
		this.motionX *= 0.1D;
		this.motionY *= 0.1D;
		this.motionZ *= 0.1D;
		this.motionX += p_i1226_8_;
		this.motionY += p_i1226_10_;
		this.motionZ += p_i1226_12_;
		this.particleRed = this.particleGreen = this.particleBlue = (float) (Math.random() * 0.3D);
		this.particleScale *= 0.75F;
		this.particleScale *= p_i1226_14_;
		this.smokeParticleScale = this.particleScale;
		this.noClip = false;
	}

	@Override
	public void onUpdate() {

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if(this.maxAge < 900) {
			this.maxAge = this.rand.nextInt(301) + 900;
		}

		if(!this.worldObj.isRemote && this.rand.nextInt(50) == 0)
			ExplosionChaos.poison(this.worldObj, (int) this.posX, (int) this.posY, (int) this.posZ, 2);

		this.particleAge++;

		if(this.particleAge >= this.maxAge) {
			setDead();
		}

		this.motionX *= 0.86D;
		this.motionY *= 0.86D;
		this.motionZ *= 0.86D;

		this.motionY -= 0.1;

		double subdivisions = 4;

		for(int i = 0; i < subdivisions; i++) {

			this.posX += this.motionX / subdivisions;
			this.posY += this.motionY / subdivisions;
			this.posZ += this.motionZ / subdivisions;

			if(this.worldObj.getBlock((int) this.posX, (int) this.posY, (int) this.posZ).getMaterial() != Material.air) {
				setDead();

				for(int a = -1; a < 2; a++) {
					for(int b = -1; b < 2; b++) {
						for(int c = -1; c < 2; c++) {

							Block bl = this.worldObj.getBlock((int) this.posX + a, (int) this.posY + b, (int) this.posZ + c);
							if(bl == Blocks.grass) {
								this.worldObj.setBlock((int) this.posX + a, (int) this.posY + b, (int) this.posZ + c, Blocks.dirt, 1, 3);
							} else {
								ExplosionNukeGeneric.solinium(this.worldObj, (int) this.posX + a, (int) this.posY + b, (int) this.posZ + c);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public boolean writeToNBTOptional(NBTTagCompound nbt) {
		return false;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setDead();
	}
}
