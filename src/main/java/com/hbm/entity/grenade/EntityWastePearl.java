package com.hbm.entity.grenade;

import com.hbm.blocks.ModBlocks;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class EntityWastePearl extends EntityGrenadeBase {

	public EntityWastePearl(World p_i1773_1_) {
		super(p_i1773_1_);
	}

	public EntityWastePearl(World p_i1774_1_, EntityLivingBase p_i1774_2_) {
		super(p_i1774_1_, p_i1774_2_);
	}

	public EntityWastePearl(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_) {
		super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
	}

	@Override
	public void explode() {

		if(!this.worldObj.isRemote) {
			setDead();

			int x = (int)Math.floor(this.posX);
			int y = (int)Math.floor(this.posY);
			int z = (int)Math.floor(this.posZ);
			
			for(int ix = x - 3; ix <= x + 3; ix++) {
				for(int iy = y - 3; iy <= y + 3; iy++) {
					for(int iz = z - 3; iz <= z + 3; iz++) {
						
						if(this.worldObj.rand.nextInt(3) == 0 && this.worldObj.getBlock(ix, iy, iz).isReplaceable(this.worldObj, ix, iy, iz) && ModBlocks.fallout.canPlaceBlockAt(this.worldObj, ix, iy, iz)) {
							this.worldObj.setBlock(ix, iy, iz, ModBlocks.fallout);
						} else if(this.worldObj.getBlock(ix, iy, iz) == Blocks.air) {
							
							if(this.rand.nextBoolean())
								this.worldObj.setBlock(ix, iy, iz, ModBlocks.gas_radon);
							else
								this.worldObj.setBlock(ix, iy, iz, ModBlocks.gas_radon_dense);
						}
					}
				}
			}
		}
	}
}
