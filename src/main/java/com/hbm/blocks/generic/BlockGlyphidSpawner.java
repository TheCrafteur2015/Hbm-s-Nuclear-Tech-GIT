package com.hbm.blocks.generic;

import java.util.List;
import java.util.Random;

import com.hbm.config.MobConfig;
import com.hbm.entity.mob.EntityGlyphid;
import com.hbm.entity.mob.EntityGlyphidBehemoth;
import com.hbm.entity.mob.EntityGlyphidBlaster;
import com.hbm.entity.mob.EntityGlyphidBombardier;
import com.hbm.entity.mob.EntityGlyphidBrawler;
import com.hbm.entity.mob.EntityGlyphidBrenda;
import com.hbm.entity.mob.EntityGlyphidNuclear;
import com.hbm.entity.mob.EntityGlyphidScout;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.items.ModItems;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class BlockGlyphidSpawner extends BlockContainer {

	public BlockGlyphidSpawner(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return ModItems.egg_glyphid;
	}

	@Override
	public int quantityDropped(int meta, int fortune, Random rand) {
		return 1 + rand.nextInt(3) + fortune;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityGlpyhidSpawner();
	}

	public static class TileEntityGlpyhidSpawner extends TileEntity {
		
		@SuppressWarnings("unchecked")
		@Override
		public void updateEntity() {
			
			if(!this.worldObj.isRemote && this.worldObj.getTotalWorldTime() % 60 == 0 && this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL) {
				
				int count = 0;
				
				for(Object e : this.worldObj.loadedEntityList) {
					if(e instanceof EntityGlyphid) {
						count++;
						if(count >= MobConfig.spawnMax) return;
					}
				}

				float soot = PollutionHandler.getPollution(this.worldObj, this.xCoord, this.yCoord, this.zCoord, PollutionType.SOOT);
				List<EntityGlyphid> list = this.worldObj.getEntitiesWithinAABB(EntityGlyphid.class, AxisAlignedBB.getBoundingBox(this.xCoord - 6, this.yCoord + 1, this.zCoord - 6, this.xCoord + 7, this.yCoord + 9, this.zCoord + 7));
				
				if(list.size() < 3) {
					EntityGlyphid glyphid = createGlyphid(soot);
					glyphid.setLocationAndAngles(this.xCoord + 0.5, this.yCoord + 1, this.zCoord + 0.5, this.worldObj.rand.nextFloat() * 360.0F, 0.0F);
					this.worldObj.spawnEntityInWorld(glyphid);
				}
				
				if(this.worldObj.rand.nextInt(20) == 0 && soot >= MobConfig.scoutThreshold) {
					EntityGlyphidScout scout = new EntityGlyphidScout(this.worldObj);
					scout.setLocationAndAngles(this.xCoord + 0.5, this.yCoord + 1, this.zCoord + 0.5, this.worldObj.rand.nextFloat() * 360.0F, 0.0F);
					this.worldObj.spawnEntityInWorld(scout);
				}
			}
		}
		
		public EntityGlyphid createGlyphid(float soot) {
			Random rand = new Random();

			if(soot < MobConfig.tier2Threshold) return rand.nextInt(5) == 0 ? new EntityGlyphidBombardier(this.worldObj) : new EntityGlyphid(this.worldObj);
			if(soot < MobConfig.tier3Threshold) return rand.nextInt(5) == 0 ? new EntityGlyphidBombardier(this.worldObj) : new EntityGlyphidBrawler(this.worldObj);
			if(soot < MobConfig.tier4Threshold) return rand.nextInt(5) == 0 ? new EntityGlyphidBlaster(this.worldObj) : new EntityGlyphidBehemoth(this.worldObj);
			if(soot < MobConfig.tier5Threshold) return rand.nextInt(5) == 0 ? new EntityGlyphidBlaster(this.worldObj) : new EntityGlyphidBrenda(this.worldObj);
			
			return rand.nextInt(3) == 0 ? new EntityGlyphidBlaster(this.worldObj) : new EntityGlyphidNuclear(this.worldObj);
		}
	}
}
