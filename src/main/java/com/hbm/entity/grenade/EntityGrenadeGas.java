package com.hbm.entity.grenade;

import java.util.Random;

import com.hbm.entity.effect.EntityMist;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGrenade;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityGrenadeGas extends EntityGrenadeBouncyBase {
	Random rand = new Random();

	public EntityGrenadeGas(World p_i1773_1_) {
		super(p_i1773_1_);
	}

	public EntityGrenadeGas(World p_i1774_1_, EntityLivingBase p_i1774_2_) {
		super(p_i1774_1_, p_i1774_2_);
	}

	public EntityGrenadeGas(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_) {
		super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
	}

	@Override
	public void explode() {

		if (!this.worldObj.isRemote) {
			setDead();
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 0.0F, true);
			
			EntityMist mist = new EntityMist(this.worldObj);
			mist.setType(Fluids.CHLORINE);
			mist.setPosition(this.posX, this.posY - 5, this.posZ);
			mist.setArea(15, 10);
			this.worldObj.spawnEntityInWorld(mist);
		}
	}

	@Override
	protected int getMaxTimer() {
		return ItemGrenade.getFuseTicks(ModItems.grenade_gas);
	}

	@Override
	protected double getBounceMod() {
		return 0.25D;
	}

}
