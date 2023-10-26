package com.hbm.entity.mob;

import com.hbm.entity.particle.EntityBSmokeFX;
import com.hbm.items.ModItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * BOW
 */
public class EntityQuackos extends EntityDuck implements IBossDisplayData {

	/**
	 * BOW
	 */
	public EntityQuackos(World world) {
		super(world);
		setSize(0.3F * 25, 0.7F * 25);
	}

	/**
	 * BOW
	 */
	@Override
	protected String getLivingSound() {
		return "hbm:entity.megaquacc";
	}

	/**
	 * BOW
	 */
	@Override
	protected String getHurtSound() {
		return "hbm:entity.megaquacc";
	}

	/**
	 * BOW
	 */
	@Override
	protected String getDeathSound() {
		return "hbm:entity.megaquacc";
	}

	/**
	 * BOW
	 */
	@Override
	public EntityQuackos createChild(EntityAgeable entity) {
		return new EntityQuackos(this.worldObj);
	}

	/**
	 * BOW
	 */
	@Override
	public boolean isEntityInvulnerable() {
		return true;
	}

	/**
	 * BOW
	 */
	@Override
	public void setDead() {
		if(this.worldObj.isRemote)
			super.setDead();
	} //prank'd

	/**
	 * BOW
	 */
	@Override
	public void setHealth(float f) {
		super.setHealth(getMaxHealth());
	} //prank'd

	/**
	 * BOW
	 */
	@Override
	public boolean interact(EntityPlayer player) {

		if(super.interact(player)) {
			return true;

		} else {

			if(!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == player)) {
				player.mountEntity(this);
				return true;
			}

			return false;
		}
	}
	
	/**
	 * BOW
	 */
	public void despawn() {
		
		if(!this.worldObj.isRemote) {
			for(int i = 0; i < 150; i++) {
				
				EntityBSmokeFX fx = new EntityBSmokeFX(this.worldObj);
				fx.setPositionAndRotation(this.posX + this.rand.nextDouble() * 20 - 10, this.posY + this.rand.nextDouble() * 25, this.posZ + this.rand.nextDouble() * 20 - 10, 0, 0);
				this.worldObj.spawnEntityInWorld(fx);
			}
			
			dropItem(ModItems.spawn_duck, 3);
		}
		this.isDead = true;
	}

	/**
	 * BOW
	 */
	@Override
	public void updateRiderPosition() {

		super.updateRiderPosition();
		float f = MathHelper.sin(this.renderYawOffset * (float) Math.PI / 180.0F);
		float f1 = MathHelper.cos(this.renderYawOffset * (float) Math.PI / 180.0F);
		float f2 = 0.1F;
		float f3 = 0.0F;
		this.riddenByEntity.setPosition(this.posX + (double) (f2 * f), this.posY + (double) (this.height - 0.125F) + this.riddenByEntity.getYOffset() + (double) f3, this.posZ - (double) (f2 * f1));

		if(this.riddenByEntity instanceof EntityLivingBase) {
			((EntityLivingBase) this.riddenByEntity).renderYawOffset = this.renderYawOffset;
		}
	}

	/**
	 * BOW
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 7.5F;
	}

	/**
	 * BOW
	 */
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		
		if(!this.worldObj.isRemote && this.posY < -30) {
			setPosition(this.posX + this.rand.nextGaussian() * 30, 256, this.posZ + this.rand.nextGaussian() * 30);
		}
	}

	/**
	 * BOW
	 */
	@Override
	public void onDeath(DamageSource sourceOrRatherLackThereof) { }

	@Override
	public boolean allowLeashing() {
		return false;
	}
}
