package com.hbm.tileentity.deco;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.particle.EntityOrangeFX;
import com.hbm.entity.projectile.EntityShrapnel;
import com.hbm.entity.projectile.EntityWaterSplash;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityGeysir extends TileEntity {
	
	int timer;

	@Override
	public void updateEntity() {
		
		if (!this.worldObj.isRemote && this.worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord) == Blocks.air) {
			
			this.timer--;
			
			int meta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
			
			if(this.timer <= 0) {
				this.timer = getDelay();

				if(meta == 0)
					this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 1, 2);
				else
					this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 0, 2);
			}
			
			if(meta == 1) {
				perform();
			}
		}
	}
	
	private void water() {
		
		EntityWaterSplash fx = new EntityWaterSplash(this.worldObj, this.xCoord + 0.5, this.yCoord + 1.5, this.zCoord + 0.5);

		fx.motionX = this.worldObj.rand.nextGaussian() * 0.35;
		fx.motionZ = this.worldObj.rand.nextGaussian() * 0.35;
		fx.motionY = 2;
		
		this.worldObj.spawnEntityInWorld(fx);
	}
	
	private void chlorine() {
		
		for(int i = 0; i < 3; i++) {
			EntityOrangeFX fx = new EntityOrangeFX(this.worldObj, this.xCoord + 0.5, this.yCoord + 1.5, this.zCoord + 0.5, 0.0, 0.0, 0.0);
	
			fx.motionX = this.worldObj.rand.nextGaussian() * 0.45;
			fx.motionZ = this.worldObj.rand.nextGaussian() * 0.45;
			fx.motionY = this.timer * 0.3;
			
			this.worldObj.spawnEntityInWorld(fx);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void vapor() {

		List<Entity> entities = this.worldObj.getEntitiesWithinAABB(Entity.class,
				AxisAlignedBB.getBoundingBox(this.xCoord - 0.5, this.yCoord + 0.5, this.zCoord - 0.5, this.xCoord + 1.5,
						this.yCoord + 2, this.zCoord + 1.5));
		
		if (!entities.isEmpty()) {
			for (Entity e : entities) {

				if(e instanceof EntityLivingBase)
				((EntityLivingBase)e).addPotionEffect(new PotionEffect(Potion.regeneration.id, 20, 0));
			}
		}
	}
	
	private void fire() {
		
		int range = 32;
		if(this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5).expand(range, range, range)).isEmpty())
			return;
		
		if(this.worldObj.rand.nextInt(3) == 0) {
			EntityShrapnel fx = new EntityShrapnel(this.worldObj, this.xCoord + 0.5, this.yCoord + 1.5, this.zCoord + 0.5);
			fx.motionX = this.worldObj.rand.nextGaussian() * 0.05;
			fx.motionZ = this.worldObj.rand.nextGaussian() * 0.05;
			fx.motionY = 0.5 + this.worldObj.rand.nextDouble() * this.timer * 0.01;
			
			this.worldObj.spawnEntityInWorld(fx);
		}
		
		if(this.timer % 2 == 0) {
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "gasfire");
			data.setDouble("mX", this.worldObj.rand.nextGaussian() * 0.05);
			data.setDouble("mY", 0.2);
			data.setDouble("mZ", this.worldObj.rand.nextGaussian() * 0.05);
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, this.xCoord + 0.5F, this.yCoord + 1.1F, this.zCoord + 0.5F), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 75));
		}
	}
	
	private int getDelay() {
		
		Block b = this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord);
		int meta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
		Random rand = this.worldObj.rand;
		
		if(b == ModBlocks.geysir_water) {
			
			return (meta == 0 ? 30 : 100 + rand.nextInt(40));
			
		} else if(b == ModBlocks.geysir_chlorine) {
			
			return (meta == 0 ? 20 : 400 + rand.nextInt(100));
			
		} else if(b == ModBlocks.geysir_vapor) {
			
			return (meta == 0 ? 20 : 30 + rand.nextInt(20));
			
		} else if(b == ModBlocks.geysir_nether) {
			
			return (meta == 0 ? (rand.nextBoolean() ? 300 : 450) : 80 + rand.nextInt(60));
			
		}
		
		return 0;
	}
	
	private void perform() {

		Block b = this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord);
		
		if(b == ModBlocks.geysir_water) {
			water();
			
		} else if(b == ModBlocks.geysir_chlorine) {
			chlorine();
			
		} else if(b == ModBlocks.geysir_vapor) {
			vapor();
			
		} else if(b == ModBlocks.geysir_nether) {
			fire();
		}
	}

}
