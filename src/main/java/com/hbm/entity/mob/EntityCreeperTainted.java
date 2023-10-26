package com.hbm.entity.mob;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;

import api.hbm.entity.IRadiationImmune;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityCreeperTainted extends EntityCreeper implements IRadiationImmune {

	public EntityCreeperTainted(World world) {
		super(world);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35D);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(isEntityAlive()) {

			if(getHealth() < getMaxHealth() && this.ticksExisted % 10 == 0) {
				heal(1.0F);
			}
		}
	}

	@Override
	protected Item getDropItem() {
		return Item.getItemFromBlock(Blocks.tnt);
	}

	@Override
	public void func_146077_cc() {
		if(!this.worldObj.isRemote) {
			boolean griefing = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");

			this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 5.0F, false, false);

			if(griefing) {
				if(getPowered()) {
	
					for(int i = 0; i < 255; i++) {
						int a = this.rand.nextInt(15) + (int) this.posX - 7;
						int b = this.rand.nextInt(15) + (int) this.posY - 7;
						int c = this.rand.nextInt(15) + (int) this.posZ - 7;
						
						if(this.worldObj.getBlock(a, b, c).isReplaceable(this.worldObj, a, b, c) && EntityCreeperTainted.hasPosNeightbour(this.worldObj, a, b, c)) {
							if(!GeneralConfig.enableHardcoreTaint) {
								this.worldObj.setBlock(a, b, c, ModBlocks.taint, this.rand.nextInt(3) + 5, 2);
							} else {
								this.worldObj.setBlock(a, b, c, ModBlocks.taint, this.rand.nextInt(3), 2);
							}
						}
					}
	
				} else {
	
					for(int i = 0; i < 85; i++) {
						int a = this.rand.nextInt(7) + (int) this.posX - 3;
						int b = this.rand.nextInt(7) + (int) this.posY - 3;
						int c = this.rand.nextInt(7) + (int) this.posZ - 3;
						
						if(this.worldObj.getBlock(a, b, c).isReplaceable(this.worldObj, a, b, c) && EntityCreeperTainted.hasPosNeightbour(this.worldObj, a, b, c)) {
							if(!GeneralConfig.enableHardcoreTaint) {
								this.worldObj.setBlock(a, b, c, ModBlocks.taint, this.rand.nextInt(6) + 10, 2);
							} else {
								this.worldObj.setBlock(a, b, c, ModBlocks.taint, this.rand.nextInt(3) + 4, 2);
							}
						}
					}
				}
			}

			setDead();
		}
	}

	public static boolean hasPosNeightbour(World world, int x, int y, int z) {
		Block b0 = world.getBlock(x + 1, y, z);
		Block b1 = world.getBlock(x, y + 1, z);
		Block b2 = world.getBlock(x, y, z + 1);
		Block b3 = world.getBlock(x - 1, y, z);
		Block b4 = world.getBlock(x, y - 1, z);
		Block b5 = world.getBlock(x, y, z - 1);
		boolean b = (b0.renderAsNormalBlock() && b0.getMaterial().isOpaque()) || (b1.renderAsNormalBlock() && b1.getMaterial().isOpaque()) || (b2.renderAsNormalBlock() && b2.getMaterial().isOpaque()) || (b3.renderAsNormalBlock() && b3.getMaterial().isOpaque()) || (b4.renderAsNormalBlock() && b4.getMaterial().isOpaque()) || (b5.renderAsNormalBlock() && b5.getMaterial().isOpaque());
		return b;
	}
}
