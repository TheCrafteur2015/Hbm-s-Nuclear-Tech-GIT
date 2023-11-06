package com.hbm.tileentity.deco;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.TrappedBrick.Trap;
import com.hbm.entity.projectile.EntityBulletBaseNT;
import com.hbm.entity.projectile.EntityRubble;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.items.ModItems;

import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityTrappedBrick extends TileEntity {
	
	AxisAlignedBB detector = null;
	ForgeDirection dir = ForgeDirection.UNKNOWN;
	
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			if(this.detector == null) {
				setDetector();
			}
			
			List players = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.detector);
			
			if(!players.isEmpty())
				trigger();
		}
	}
	
	@SuppressWarnings("incomplete-switch")
	private void trigger() {
		
		Trap trap = Trap.get(getBlockMetadata());
		
		switch(trap) {
		case FALLING_ROCKS:
			for(int x = 0; x < 3; x++) {
				for(int z = 0; z < 3; z++) {
					EntityRubble rubble = new EntityRubble(this.worldObj, this.xCoord - 0.5 + x, this.yCoord - 0.5, this.zCoord - 0.5 + z);
					rubble.setMetaBasedOnBlock(ModBlocks.reinforced_stone, 0);
					this.worldObj.spawnEntityInWorld(rubble);
				}
			}
			break;
		case ARROW:
			EntityArrow arrow = new EntityArrow(this.worldObj);
			arrow.setPosition(this.xCoord + 0.5 + this.dir.offsetX, this.yCoord + 0.5, this.zCoord + 0.5 + this.dir.offsetZ);
			arrow.motionX = this.dir.offsetX;
			arrow.motionZ = this.dir.offsetZ;
			this.worldObj.spawnEntityInWorld(arrow);
			break;
		case FLAMING_ARROW:
			EntityArrow farrow = new EntityArrow(this.worldObj);
			farrow.setPosition(this.xCoord + 0.5 + this.dir.offsetX, this.yCoord + 0.5, this.zCoord + 0.5 + this.dir.offsetZ);
			farrow.motionX = this.dir.offsetX;
			farrow.motionZ = this.dir.offsetZ;
			farrow.setFire(60);
			this.worldObj.spawnEntityInWorld(farrow);
			break;
		case PILLAR:
			for(int i = 0; i < 3; i++)
				this.worldObj.setBlock(this.xCoord, this.yCoord - 1 - i, this.zCoord, ModBlocks.concrete_pillar);
			break;
		case POISON_DART:
			EntityBulletBaseNT dart = new EntityBulletBaseNT(this.worldObj, BulletConfigSyncingUtil.G20_CAUSTIC);
			dart.setPosition(this.xCoord + 0.5 + this.dir.offsetX, this.yCoord + 0.5, this.zCoord + 0.5 + this.dir.offsetZ);
			dart.motionX = this.dir.offsetX;
			dart.motionZ = this.dir.offsetZ;
			this.worldObj.spawnEntityInWorld(dart);
			break;
		case ZOMBIE:
			EntityZombie zombie = new EntityZombie(this.worldObj);
			zombie.setPosition(this.xCoord + 0.5, this.yCoord + 1, this.zCoord + 0.5);
			switch(this.worldObj.rand.nextInt(3)) {
			case 0: zombie.setCurrentItemOrArmor(0, new ItemStack(ModItems.chernobylsign)); break;
			case 1: zombie.setCurrentItemOrArmor(0, new ItemStack(ModItems.cobalt_sword)); break;
			case 2: zombie.setCurrentItemOrArmor(0, new ItemStack(ModItems.cmb_hoe)); break;
			}
			zombie.setEquipmentDropChance(0, 1.0F);
			this.worldObj.spawnEntityInWorld(zombie);
			break;
		case SPIDERS:
			for(int i = 0; i < 3; i++) {
				EntityCaveSpider spider = new EntityCaveSpider(this.worldObj);
				spider.setPosition(this.xCoord + 0.5, this.yCoord - 1, this.zCoord + 0.5);
				this.worldObj.spawnEntityInWorld(spider);
			}
			break;
		}

		this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, "random.click", 0.3F, 0.6F);
		this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, ModBlocks.brick_jungle);
	}
	
	@SuppressWarnings("incomplete-switch")
	private void setDetector() {
		
		Trap trap = Trap.get(getBlockMetadata());
		
		switch(trap) {
		case FALLING_ROCKS:
			this.detector = AxisAlignedBB.getBoundingBox(this.xCoord - 1, this.yCoord - 3, this.zCoord - 1, this.xCoord + 2, this.yCoord, this.zCoord + 2);
			return;
			
		case PILLAR: 
			this.detector = AxisAlignedBB.getBoundingBox(this.xCoord + 0.2, this.yCoord - 3, this.zCoord + 0.2, this.xCoord + 0.8, this.yCoord, this.zCoord + 0.8);
			return;
			
		case ARROW:
		case FLAMING_ARROW:
		case POISON_DART:
			setDetectorDirectional();
			return;
			
		case ZOMBIE:
			this.detector = AxisAlignedBB.getBoundingBox(this.xCoord - 1, this.yCoord + 1, this.zCoord - 1, this.xCoord + 2, this.yCoord + 2, this.zCoord + 2);
			return;
			
		case SPIDERS:
			this.detector = AxisAlignedBB.getBoundingBox(this.xCoord - 1, this.yCoord - 3, this.zCoord - 1, this.xCoord + 2, this.yCoord, this.zCoord + 2);
			return;
		}
		
		this.detector = AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1);
	}
	
	private void setDetectorDirectional() {
		@SuppressWarnings({ "unchecked", "serial", "rawtypes" })
		List<ForgeDirection> dirs = new ArrayList() {{
			add(ForgeDirection.NORTH);
			add(ForgeDirection.SOUTH);
			add(ForgeDirection.EAST);
			add(ForgeDirection.WEST);
		}};
		
		Collections.shuffle(dirs);
		
		for(ForgeDirection dir : dirs) {
			
			if(this.worldObj.getBlock(this.xCoord + dir.offsetX, this.yCoord, this.zCoord + dir.offsetZ) == Blocks.air) {

				double minX = this.xCoord + 0.4;
				double minY = this.yCoord + 0.4;
				double minZ = this.zCoord + 0.4;
				double maxX = this.xCoord + 0.6;
				double maxY = this.yCoord + 0.6;
				double maxZ = this.zCoord + 0.6;
				
				if(dir.offsetX > 0)
					maxX += 3;
				else if(dir.offsetX < 0)
					minX -= 3;
				
				if(dir.offsetZ > 0)
					maxZ += 3;
				else if(dir.offsetZ < 0)
					minZ -= 3;
				
				this.detector = AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
				this.dir = dir;
				return;
			}
		}
		
		this.detector = AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1);
	}
}
