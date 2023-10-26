package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMissileVolcano extends EntityMissileBaseAdvanced {

	public EntityMissileVolcano(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileVolcano(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
		
		ExplosionLarge.explode(this.worldObj, this.posX, this.posY, this.posZ, 10.0F, true, true, true);
		
		for(int x = -1; x <= 1; x++) {
			for(int y = -1; y <= 1; y++) {
				for(int z = -1; z <= 1; z++) {
					this.worldObj.setBlock((int)Math.floor(this.posX + x), (int)Math.floor(this.posY + y), (int)Math.floor(this.posZ + z), ModBlocks.volcanic_lava_block);
				}
			}
		}
		
		this.worldObj.setBlock((int)Math.floor(this.posX), (int)Math.floor(this.posY), (int)Math.floor(this.posZ), ModBlocks.volcano_core);
	}

	@Override
	public List<ItemStack> getDebris() {
		List<ItemStack> list = new ArrayList<>();

		list.add(new ItemStack(ModItems.plate_titanium, 16));
		list.add(new ItemStack(ModItems.plate_steel, 20));
		list.add(new ItemStack(ModItems.plate_aluminium, 12));
		list.add(new ItemStack(ModItems.thruster_large, 1));
		list.add(new ItemStack(ModItems.circuit_targeting_tier4, 1));

		return list;
	}

	@Override
	public ItemStack getDebrisRareDrop() {
		return new ItemStack(ModItems.warhead_volcano);
	}

	@Override
	public RadarTargetType getTargetType() {
		return RadarTargetType.MISSILE_TIER4;
	}
}
