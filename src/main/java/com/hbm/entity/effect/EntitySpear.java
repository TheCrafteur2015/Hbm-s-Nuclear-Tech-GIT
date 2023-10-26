package com.hbm.entity.effect;

import java.util.ArrayList;
import java.util.List;

import com.hbm.explosion.ExplosionNT;
import com.hbm.explosion.ExplosionNT.ExAttrib;
import com.hbm.main.MainRegistry;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntitySpear extends Entity {
	
	public int ticksInGround;

	public EntitySpear(World p_i1582_1_) {
		super(p_i1582_1_);
		setSize(2F, 10F);
		this.isImmuneToFire = true;
		this.ignoreFrustumCheck = true;
	}

	@Override
	protected void entityInit() { }

	@SuppressWarnings("unchecked")
	@Override
	public void onUpdate() {
		
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		
		this.motionX = 0;
		this.motionY = -0.2;
		this.motionZ = 0;

		int x = (int) Math.floor(this.posX);
		int y = (int) Math.floor(this.posY);
		int z = (int) Math.floor(this.posZ);
		
		if(this.worldObj.getBlock(x, y - 1, z).getMaterial() == Material.air) {
			setPositionAndRotation(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ, 0, 0);

			if(!this.worldObj.isRemote) {
				double ix = this.posX + this.rand.nextGaussian() * 25;
				double iz = this.posZ + this.rand.nextGaussian() * 25;
				double iy = this.worldObj.getHeightValue((int)Math.floor(ix), (int)Math.floor(iz)) + 2;
				
				ExAttrib at = Vec3.createVectorHelper(ix - this.posX, 0, iz - this.posZ).lengthVector() < 20 ? ExAttrib.DIGAMMA_CIRCUIT : ExAttrib.DIGAMMA;
				
				new ExplosionNT(this.worldObj, this, ix, iy, iz, 7.5F)
				.addAttrib(ExAttrib.NOHURT)
				.addAttrib(ExAttrib.NOPARTICLE)
				.addAttrib(ExAttrib.NODROP)
				.addAttrib(ExAttrib.NOSOUND)
				.addAttrib(at).explode();
				
				for(Object obj : this.worldObj.playerEntities) {
					EntityPlayer player = (EntityPlayer) obj;
					ContaminationUtil.contaminate(player, HazardType.DIGAMMA, ContaminationType.DIGAMMA, 0.05F);
					player.triggerAchievement(MainRegistry.digammaKauaiMoho);
				}
			}
			
			if(this.worldObj.isRemote) {
				
				double dy = this.worldObj.getHeightValue((int)Math.floor(this.posX), (int)Math.floor(this.posZ)) + 2;
				
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "smoke");
				data.setString("mode", "radialDigamma");
				data.setInteger("count", 5);
				data.setDouble("posX", this.posX);
				data.setDouble("posY", dy);
				data.setDouble("posZ", this.posZ);
				MainRegistry.proxy.effectNT(data);
			}

			
			if(this.worldObj.getBlock(x, y - 3, z).getMaterial() == Material.air)
				this.ticksInGround = 0;
			
		} else {
			
			this.ticksInGround++;
			
			if(!this.worldObj.isRemote && this.ticksInGround > 100) {
				
				List entities =  new ArrayList<>(this.worldObj.loadedEntityList);
				for(Object obj : entities) {
					
					if(obj instanceof EntityLivingBase)
						ContaminationUtil.contaminate((EntityLivingBase) obj, HazardType.DIGAMMA, ContaminationType.DIGAMMA2, 10F);
				}
				setDead();
				
				this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "hbm:weapon.dFlash", 25000.0F, 1.0F);
				
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "smoke");
				data.setString("mode", "radialDigamma");
				data.setInteger("count", 100);
				data.setDouble("posX", this.posX);
				data.setDouble("posY", this.posY + 7);
				data.setDouble("posZ", this.posZ);
				MainRegistry.proxy.effectNT(data);
			}
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) { }

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) { }
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return distance < 25000;
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
}
