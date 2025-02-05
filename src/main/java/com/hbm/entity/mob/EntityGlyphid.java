package com.hbm.entity.mob;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.hbm.config.MobConfig;
import com.hbm.entity.pathfinder.PathFinderUtils;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.ResourceManager;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityGlyphid extends EntityMob {

	public EntityGlyphid(World world) {
		super(world);
		/*this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
		this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));*/
		setSize(1.75F, 1F);
	}
	
	public ResourceLocation getSkin() {
		return ResourceManager.glyphid_tex;
	}
	
	public double getScale() {
		return 1.0D;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, (byte) 0); //wall climbing
		this.dataWatcher.addObject(17, (byte) 0b11111); //armor
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5D);
	}
	
	@Override
	protected void dropFewItems(boolean byPlayer, int looting) {
		if(this.rand.nextInt(3) == 0) entityDropItem(new ItemStack(ModItems.glyphid_meat, 1 + this.rand.nextInt(2) + looting), 0F);
	}

	@Override
	protected Entity findPlayerToAttack() {
		if(this.isPotionActive(Potion.blindness)) return null;
		EntityPlayer entityplayer = this.worldObj.getClosestVulnerablePlayerToEntity(this, useExtendedTargeting() ? 128D : 16D);
		return entityplayer != null && canEntityBeSeen(entityplayer) ? entityplayer : null;
	}

	@Override
	protected void updateEntityActionState() {
		super.updateEntityActionState();
		
		if(this.isPotionActive(Potion.blindness)) {
			this.entityToAttack = null;
			setPathToEntity(null);
		} else {
			
			// hell yeah!!
			if(useExtendedTargeting() && this.entityToAttack != null && !hasPath()) {
				setPathToEntity(PathFinderUtils.getPathEntityToEntityPartial(this.worldObj, this, this.entityToAttack, 16F, true, false, false, true));
			}
		}

	}
	
	public boolean useExtendedTargeting() {
		return PollutionHandler.getPollution(this.worldObj, (int) Math.floor(this.posX), (int) Math.floor(this.posY), (int) Math.floor(this.posZ), PollutionType.SOOT) >= MobConfig.targetingThreshold;
	}

	@Override
	protected boolean canDespawn() {
		return this.entityToAttack == null;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		
		if(!source.isDamageAbsolute() && !source.isUnblockable() && !this.worldObj.isRemote && !source.isFireDamage() && !source.getDamageType().equals(ModDamageSource.s_cryolator)) {
			byte armor = this.dataWatcher.getWatchableObjectByte(17);
			
			if(armor != 0) { //if at least one bit of armor is present
				
				if(amount < getDamageThreshold()) return false;
				
				int chance = getArmorBreakChance(amount); //chances of armor being broken off
				if(this.rand.nextInt(chance) == 0 && amount > 1) {
					breakOffArmor();
					amount *= 0.25F;
				}
				
				amount -= getDamageThreshold();
				if(amount < 0) return true;
			}
			
			amount = calculateDamage(amount);
		}
		
		if(source.isFireDamage()) amount *= 4F;
		
		return super.attackEntityFrom(source, amount);
	}
	
	public int getArmorBreakChance(float amount) {
		return amount < 10 ? 5 : amount < 20 ? 3 : 2;
	}
	
	public float calculateDamage(float amount) {

		byte armor = this.dataWatcher.getWatchableObjectByte(17);
		int divisor = 1;
		
		for(int i = 0; i < 5; i++) {
			if((armor & (1 << i)) > 0) {
				divisor++;
			}
		}
		
		amount /= divisor;
		
		return amount;
	}
	
	public float getDamageThreshold() {
		return 0.5F;
	}
	
	public void breakOffArmor() {
		byte armor = this.dataWatcher.getWatchableObjectByte(17);
		List<Integer> indices = Arrays.asList(0, 1, 2, 3, 4);
		Collections.shuffle(indices);
		
		for(Integer i : indices) {
			byte bit = (byte) (1 << i);
			if((armor & bit) > 0) {
				armor &= ~bit;
				armor = (byte) (armor & 0b11111);
				this.dataWatcher.updateObject(17, armor);
				this.worldObj.playSoundAtEntity(this, "mob.zombie.woodbreak", 1.0F, 1.25F);
				break;
			}
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity victum) {
		if(this.isSwingInProgress) return false;
		swingItem();
		return super.attackEntityAsMob(victum);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if(!this.worldObj.isRemote) {
			setBesideClimbableBlock(this.isCollidedHorizontally);
			
			if(this.worldObj.getTotalWorldTime() % 200 == 0) {
				swingItem();
			}
		}
	}

	@Override
	protected void updateArmSwingProgress() {
		int i = swingDuration();

		if(this.isSwingInProgress) {
			++this.swingProgressInt;

			if(this.swingProgressInt >= i) {
				this.swingProgressInt = 0;
				this.isSwingInProgress = false;
			}
		} else {
			this.swingProgressInt = 0;
		}

		this.swingProgress = (float) this.swingProgressInt / (float) i;
	}
	
	public int swingDuration() {
		return 15;
	}

	@Override
	public void setInWeb() { }
	
	@Override
	public boolean isOnLadder() {
		return isBesideClimbableBlock();
	}
	
	public boolean isBesideClimbableBlock() {
		return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	public void setBesideClimbableBlock(boolean climbable) {
		byte watchable = this.dataWatcher.getWatchableObjectByte(16);

		if(climbable) {
			watchable = (byte) (watchable | 1);
		} else {
			watchable &= -2;
		}

		this.dataWatcher.updateObject(16, Byte.valueOf(watchable));
	}
	
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.ARTHROPOD;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setByte("armor", this.dataWatcher.getWatchableObjectByte(17));
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.dataWatcher.updateObject(17, nbt.getByte("armor"));
	}
}
