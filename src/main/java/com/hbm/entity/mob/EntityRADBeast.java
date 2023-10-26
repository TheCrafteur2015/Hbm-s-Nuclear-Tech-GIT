package com.hbm.entity.mob;

import java.util.List;

import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;

import api.hbm.entity.IRadiationImmune;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityRADBeast extends EntityMob implements IRadiationImmune {

	private float heightOffset = 0.5F;
	private int heightOffsetUpdateTime;

	public EntityRADBeast(World world) {
		super(world);
		this.isImmuneToFire = true;
		this.experienceValue = 30;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(120.0D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(16.0D);
	}

	public EntityRADBeast makeLeader() {
		setEquipmentDropChance(0, 1F);
		setCurrentItemOrArmor(0, new ItemStack(ModItems.coin_radiation));
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(360.0D);
		heal(getMaxHealth());
		return this;
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, (int) 0);
	}

	@Override
	protected String getLivingSound() {
		return "hbm:item.geiger" + (1 + this.rand.nextInt(6));
	}

	@Override
	protected String getHurtSound() {
		return "mob.blaze.hit";
	}

	@Override
	protected String getDeathSound() {
		return "hbm:step.iron";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float f) {
		return 15728880;
	}

	@Override
	public float getBrightness(float f) {
		return 1.0F;
	}

	@Override
	public int getTotalArmorValue() {
		return 8;
	}

	@Override
	public void onLivingUpdate() {

		if(!this.worldObj.isRemote) {

			if(isWet()) {
				attackEntityFrom(DamageSource.drown, 1.0F);
			}

			--this.heightOffsetUpdateTime;

			if(this.heightOffsetUpdateTime <= 0) {
				this.heightOffsetUpdateTime = 100;
				this.heightOffset = 0.5F + (float) this.rand.nextGaussian() * 3.0F;
			}

			if(getEntityToAttack() != null && getEntityToAttack().posY + (double) getEntityToAttack().getEyeHeight() > this.posY + (double) getEyeHeight() + (double) this.heightOffset) {
				this.motionY += (0.30000001192092896D - this.motionY) * 0.30000001192092896D;
			}

			if(this.entityToAttack != null && this.attackTime < 10) {

				if(this.dataWatcher.getWatchableObjectInt(16) != this.entityToAttack.getEntityId())
					this.dataWatcher.updateObject(16, this.entityToAttack.getEntityId());
			} else {
				this.dataWatcher.updateObject(16, 0);
			}
		}

		if(!this.onGround && this.motionY < 0.0D) {
			this.motionY *= 0.6D;
		}

		if(getMaxHealth() <= 150) {

			for(int i = 0; i < 6; i++) {
				this.worldObj.spawnParticle("townaura", this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width * 1.5, this.posY + this.rand.nextDouble() * (double) this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width * 1.5, 0.0D, 0.0D, 0.0D);
			}

			if(this.rand.nextInt(6) == 0) {

				this.worldObj.spawnParticle("flame", this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height * 0.75, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, 0.0D, 0.0D, 0.0D);
			}

		} else {
			this.worldObj.spawnParticle("lava", this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height * 0.75, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, 0.0D, 0.0D, 0.0D);
		}

		super.onLivingUpdate();
	}

	@Override
	protected void attackEntity(Entity target, float dist) {

		if(this.attackTime <= 0 && dist < 2.0F && target.boundingBox.maxY > this.boundingBox.minY && target.boundingBox.minY < this.boundingBox.maxY) {
			this.attackTime = 20;
			attackEntityAsMob(target);

		} else if(dist < 30.0F) {

			double deltaX = target.posX - this.posX;
			double deltaZ = target.posZ - this.posZ;

			if(this.attackTime == 0 && getEntityToAttack() != null) {
				
				ChunkRadiationManager.proxy.incrementRad(this.worldObj, (int) Math.floor(this.posX), (int) Math.floor(this.posY), (int) Math.floor(this.posZ), 100);
				target.attackEntityFrom(ModDamageSource.radiation, 16.0F);
				swingItem();
				playLivingSound();
				this.attackTime = 20;
			}

			this.rotationYaw = (float) (Math.atan2(deltaZ, deltaX) * 180.0D / Math.PI) - 90.0F;
			this.hasAttacked = true;
		}
	}

	public Entity getUnfortunateSoul() {

		int id = this.dataWatcher.getWatchableObjectInt(16);
		return this.worldObj.getEntityByID(id);
	}

	@Override
	protected void fall(float p_70069_1_) {
	}

	@Override
	protected Item getDropItem() {
		return ModItems.rod_zirnox_uranium_fuel_depleted;
	}

	@Override
	public void onDeath(DamageSource p_70645_1_) {
		super.onDeath(p_70645_1_);

		if(getMaxHealth() > 150) {
			List<EntityPlayer> players = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(50, 50, 50));

			for(EntityPlayer player : players) {
				player.triggerAchievement(MainRegistry.bossMeltdown);
			}
		}
	}

	@Override
	protected void dropFewItems(boolean beenHit, int looting) {

		if(beenHit) {

			if(looting > 0) {
				dropItem(ModItems.nugget_polonium, looting);
			}

			int count = this.rand.nextInt(3) + 1;

			for(int i = 0; i < count; i++) {

				int r = this.rand.nextInt(3);

				if(r == 0) {
					dropItem(isWet() ? ModItems.waste_uranium : ModItems.rod_zirnox_uranium_fuel_depleted, isWet() ? 2 : 1);

				} else if(r == 1) {
					dropItem(isWet() ? ModItems.waste_mox : ModItems.rod_zirnox_mox_fuel_depleted, isWet() ? 2 : 1);

				} else if(r == 2) {
					dropItem(isWet() ? ModItems.waste_plutonium : ModItems.rod_zirnox_plutonium_fuel_depleted, isWet() ? 2 : 1);

				}
			}
		}
	}
}
