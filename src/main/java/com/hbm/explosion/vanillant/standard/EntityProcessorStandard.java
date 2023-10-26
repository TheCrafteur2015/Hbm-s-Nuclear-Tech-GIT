package com.hbm.explosion.vanillant.standard;

import java.util.HashMap;
import java.util.List;

import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.interfaces.ICustomDamageHandler;
import com.hbm.explosion.vanillant.interfaces.IEntityProcessor;
import com.hbm.explosion.vanillant.interfaces.IEntityRangeMutator;

import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class EntityProcessorStandard implements IEntityProcessor {

	protected IEntityRangeMutator range;
	protected ICustomDamageHandler damage;

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<EntityPlayer, Vec3> process(ExplosionVNT explosion, World world, double x, double y, double z, float size) {

		HashMap<EntityPlayer, Vec3> affectedPlayers = new HashMap<>();

		size *= 2.0F;
		
		if(this.range != null) {
			size = this.range.mutateRange(explosion, size);
		}
		
		double minX = x - size - 1.0D;
		double maxX = x + size + 1.0D;
		double minY = y - size - 1.0D;
		double maxY = y + size + 1.0D;
		double minZ = z - size - 1.0D;
		double maxZ = z + size + 1.0D;
		
		List list = world.getEntitiesWithinAABBExcludingEntity(explosion.exploder, AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ));
		
		ForgeEventFactory.onExplosionDetonate(world, explosion.compat, list, size);
		Vec3 vec3 = Vec3.createVectorHelper(x, y, z);

		for (Object element : list) {
			
			Entity entity = (Entity) element;
			double distanceScaled = entity.getDistance(x, y, z) / size;

			if(distanceScaled <= 1.0D) {
				
				double deltaX = entity.posX - x;
				double deltaY = entity.posY + entity.getEyeHeight() - y;
				double deltaZ = entity.posZ - z;
				double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);

				if(distance != 0.0D) {
					
					deltaX /= distance;
					deltaY /= distance;
					deltaZ /= distance;
					
					double density = world.getBlockDensity(vec3, entity.boundingBox);
					double knockback = (1.0D - distanceScaled) * density;
					
					entity.attackEntityFrom(DamageSource.setExplosionSource(explosion.compat), ((int) ((knockback * knockback + knockback) / 2.0D * 8.0D * size + 1.0D)));
					double enchKnockback = EnchantmentProtection.func_92092_a(entity, knockback);
					
					entity.motionX += deltaX * enchKnockback;
					entity.motionY += deltaY * enchKnockback;
					entity.motionZ += deltaZ * enchKnockback;

					if(entity instanceof EntityPlayer) {
						affectedPlayers.put((EntityPlayer) entity, Vec3.createVectorHelper(deltaX * knockback, deltaY * knockback, deltaZ * knockback));
					}
					
					if(this.damage != null) {
						this.damage.handleAttack(explosion, entity, distanceScaled);
					}
				}
			}
		}

		return affectedPlayers;
	}
	
	public EntityProcessorStandard withRangeMod(float mod) {
		this.range = new IEntityRangeMutator() {
			@Override
			public float mutateRange(ExplosionVNT explosion, float range) {
				return range * mod;
			}
		};
		return this;
	}
	
	public EntityProcessorStandard withDamageMod(ICustomDamageHandler damage) {
		this.damage = damage;
		return this;
	}
}
