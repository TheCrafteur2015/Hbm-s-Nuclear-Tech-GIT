package com.hbm.entity.mob;

import java.util.List;

import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.explosion.ExplosionNukeSmall;
import com.hbm.items.ItemAmmoEnums.AmmoFatman;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityCreeperNuclear extends EntityCreeper {

	public EntityCreeperNuclear(World world) {
		super(world);
		this.fuseTime = 75;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		
		// for some reason the nuclear explosion would damage the already dead entity, reviving it and forcing it to play the death animation
		if(this.isDead) return false;

		if(source == ModDamageSource.radiation || source == ModDamageSource.mudPoisoning) {
			if(isEntityAlive()) heal(amount);
			return false;
		}

		return super.attackEntityFrom(source, amount);
	}

	@Override
	protected Item getDropItem() {
		return Item.getItemFromBlock(Blocks.tnt);
	}

	@Override
	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {

		super.dropFewItems(p_70628_1_, p_70628_2_);

		if(this.rand.nextInt(3) == 0)
			dropItem(ModItems.coin_creeper, 1);
	}

	@Override
	public void onDeath(DamageSource p_70645_1_) {
		super.onDeath(p_70645_1_);

		List<EntityPlayer> players = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(50, 50, 50));

		for(EntityPlayer player : players) {
			player.triggerAchievement(MainRegistry.bossCreeper);
		}

		if(p_70645_1_.getEntity() instanceof EntitySkeleton || (p_70645_1_.isProjectile() && p_70645_1_.getEntity() instanceof EntityArrow && ((EntityArrow) (p_70645_1_.getEntity())).shootingEntity == null)) {
			entityDropItem(ModItems.ammo_nuke.stackFromEnum(AmmoFatman.STOCK), 1);
		}
	}

	@Override
	public void onUpdate() {

		List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(5, 5, 5));

		for(Entity e : list) {
			if(e instanceof EntityLivingBase) {
				ContaminationUtil.contaminate((EntityLivingBase) e, HazardType.RADIATION, ContaminationType.CREATIVE, 0.25F);
			}
		}
		
		super.onUpdate();
		
		if(isEntityAlive() && getHealth() < getMaxHealth() && this.ticksExisted % 10 == 0) {
			heal(1.0F);
		}
	}

	@Override
	public void func_146077_cc() {
		if(!this.worldObj.isRemote) {

			setDead();
			
			boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");

			if(getPowered()) {

				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "muke");
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, this.posX, this.posY + 0.5, this.posZ), new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 250));
				this.worldObj.playSoundEffect(this.posX, this.posY + 0.5, this.posZ, "hbm:weapon.mukeExplosion", 15.0F, 1.0F);

				if(flag) {
					this.worldObj.spawnEntityInWorld(EntityNukeExplosionMK5.statFac(this.worldObj, 50, this.posX, this.posY, this.posZ).mute());
				} else {
					ExplosionNukeGeneric.dealDamage(this.worldObj, this.posX, this.posY + 0.5, this.posZ, 100);
				}
			} else {

				if(flag) {
					ExplosionNukeSmall.explode(this.worldObj, this.posX, this.posY + 0.5, this.posZ, ExplosionNukeSmall.PARAMS_MEDIUM);
				} else {
					ExplosionNukeSmall.explode(this.worldObj, this.posX, this.posY + 0.5, this.posZ, ExplosionNukeSmall.PARAMS_SAFE);
				}
			}
		}
	}
}
