package com.hbm.tileentity.deco;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.particle.EntityChlorineFX;
import com.hbm.entity.particle.EntityCloudFX;
import com.hbm.entity.particle.EntityPinkCloudFX;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public class TileEntityVent extends TileEntity {
	
	Random rand = new Random();

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote && this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord)) {
			Block b = this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord);

			if(b == ModBlocks.vent_chlorine) {
				//if(rand.nextInt(1) == 0) {
					double x = this.rand.nextGaussian() * 1.5;
					double y = this.rand.nextGaussian() * 1.5;
					double z = this.rand.nextGaussian() * 1.5;
					
					if(!this.worldObj.getBlock(this.xCoord + (int)x, this.yCoord + (int)y, this.zCoord + (int)z).isNormalCube()) {
						this.worldObj.spawnEntityInWorld(new EntityChlorineFX(this.worldObj, this.xCoord + (int)x, this.yCoord + (int)y, this.zCoord + (int)z, x/2, y/2, z/2));
					}
				//}
			}
			if(b == ModBlocks.vent_cloud) {
				//if(rand.nextInt(50) == 0) {
				double x = this.rand.nextGaussian() * 1.75;
				double y = this.rand.nextGaussian() * 1.75;
				double z = this.rand.nextGaussian() * 1.75;
				
				if(!this.worldObj.getBlock(this.xCoord + (int)x, this.yCoord + (int)y, this.zCoord + (int)z).isNormalCube()) {
					this.worldObj.spawnEntityInWorld(new EntityCloudFX(this.worldObj, this.xCoord + (int)x, this.yCoord + (int)y, this.zCoord + (int)z, x/2, y/2, z/2));
					}
				//}
			}
			if(b == ModBlocks.vent_pink_cloud) {
				//if(rand.nextInt(65) == 0) {
				double x = this.rand.nextGaussian() * 2;
				double y = this.rand.nextGaussian() * 2;
				double z = this.rand.nextGaussian() * 2;
				
				if(!this.worldObj.getBlock(this.xCoord + (int)x, this.yCoord + (int)y, this.zCoord + (int)z).isNormalCube()) {
					this.worldObj.spawnEntityInWorld(new EntityPinkCloudFX(this.worldObj, this.xCoord + (int)x, this.yCoord + (int)y, this.zCoord + (int)z, x/2, y/2, z/2));
					}
				//}
			}
		}
		
		/*if(worldObj.isRemote) {

			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "exhaust");
			data.setString("mode", "soyuz");
			data.setInteger("count", 1);
			data.setDouble("width", 0);
			data.setDouble("posX", xCoord + 0.5);
			data.setDouble("posY", yCoord - 1);
			data.setDouble("posZ", zCoord + 0.5);
			
			MainRegistry.proxy.effectNT(data);
		}*/
	}
}
