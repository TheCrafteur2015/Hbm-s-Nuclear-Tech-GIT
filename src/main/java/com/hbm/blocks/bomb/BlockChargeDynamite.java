package com.hbm.blocks.bomb;

import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNT;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.world.World;

public class BlockChargeDynamite extends BlockChargeBase {

	@Override
	public BombReturnCode explode(World world, int x, int y, int z) {
		
		if(!world.isRemote) {
			BlockChargeBase.safe = true;
			world.setBlockToAir(x, y, z);
			BlockChargeBase.safe = false;
			ExplosionNT exp = new ExplosionNT(world, null, x + 0.5, y + 0.5, z + 0.5, 4F);
			exp.explode();
			ExplosionLarge.spawnParticles(world, x + 0.5, y + 0.5, z + 0.5, 20);
			
			return BombReturnCode.DETONATED;
		}
		
		return BombReturnCode.UNDEFINED;
	}

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderType() {
		return BlockChargeDynamite.renderID;
	}
}
