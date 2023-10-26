package com.hbm.entity.projectile;

import com.hbm.entity.effect.EntityCloudTom;
import com.hbm.entity.logic.EntityTomBlast;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityTom extends EntityThrowable {

	public EntityTom(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
	}

	@Override
	public void onUpdate() {

		this.lastTickPosX = this.prevPosX = this.posX;
		this.lastTickPosY = this.prevPosY = this.posY;
		this.lastTickPosZ = this.prevPosZ = this.posZ;
		setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

		if(this.ticksExisted % 100 == 0) {
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "hbm:alarm.chime", 10000, 1.0F);
		}

		this.motionY = -0.5;

		if(this.worldObj.getBlock((int) this.posX, (int) this.posY, (int) this.posZ) != Blocks.air) {
			if(!this.worldObj.isRemote) {
				EntityTomBlast tom = new EntityTomBlast(this.worldObj);
				tom.posX = this.posX;
				tom.posY = this.posY;
				tom.posZ = this.posZ;
				tom.destructionRange = 600;
				this.worldObj.spawnEntityInWorld(tom);

				EntityCloudTom cloud = new EntityCloudTom(this.worldObj, 500);
				cloud.setLocationAndAngles(this.posX, this.posY, this.posZ, 0, 0);
				this.worldObj.spawnEntityInWorld(cloud);
			}
			setDead();
		}
	}

	@Override
	protected void onImpact(MovingObjectPosition p_70184_1_) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return distance < 500000;
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