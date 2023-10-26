package com.hbm.entity.mob;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.BlockAllocatorBulkie;
import com.hbm.explosion.vanillant.standard.BlockMutatorBulkie;
import com.hbm.explosion.vanillant.standard.BlockProcessorStandard;
import com.hbm.explosion.vanillant.standard.EntityProcessorStandard;
import com.hbm.explosion.vanillant.standard.ExplosionEffectStandard;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.items.ModItems;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityCreeperVolatile extends EntityCreeper {

	public EntityCreeperVolatile(World world) {
		super(world);
	}

	@Override
	public void func_146077_cc() {
		
		if(!this.worldObj.isRemote) {
			setDead();
			
			ExplosionVNT vnt = new ExplosionVNT(this.worldObj, this.posX, this.posY, this.posZ, getPowered() ? 14 : 7, this);
			vnt.setBlockAllocator(new BlockAllocatorBulkie(60, getPowered() ? 32 : 16));
			vnt.setBlockProcessor(new BlockProcessorStandard().withBlockEffect(new BlockMutatorBulkie(ModBlocks.block_slag, 1)));
			vnt.setEntityProcessor(new EntityProcessorStandard().withRangeMod(0.5F));
			vnt.setPlayerProcessor(new PlayerProcessorStandard());
			vnt.setSFX(new ExplosionEffectStandard());
			vnt.explode();
		}
	}
	
	@Override
	public boolean getCanSpawnHere() {
		return super.getCanSpawnHere() && this.posY <= 40;
	}
	
	@Override
	protected void dropFewItems(boolean byPlayer, int looting) {
		entityDropItem(new ItemStack(ModItems.sulfur, 2 + this.rand.nextInt(3)), 0F);
		entityDropItem(new ItemStack(ModItems.stick_tnt, 1 + this.rand.nextInt(2)), 0F);
	}
}
