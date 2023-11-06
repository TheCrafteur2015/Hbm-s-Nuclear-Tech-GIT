package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.lib.ModDamageSource;
import com.hbm.packet.LoopedSoundPacket;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityBroadcaster extends TileEntity {
	
	
	@Override
	public void updateEntity() {
		
		List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(this.xCoord + 0.5 - 25, this.yCoord + 0.5 - 25, this.zCoord + 0.5 - 25, this.xCoord + 0.5 + 25, this.yCoord + 0.5 + 25, this.zCoord + 0.5 + 25));
		
		for (Entity element : list) {
			if(element instanceof EntityLivingBase) {
				EntityLivingBase e = (EntityLivingBase)element;
				double d = Math.sqrt(Math.pow(e.posX - (this.xCoord + 0.5), 2) + Math.pow(e.posY - (this.yCoord + 0.5), 2) + Math.pow(e.posZ - (this.zCoord + 0.5), 2));
				
				if(d <= 25) {
					if(e.getActivePotionEffect(Potion.confusion) == null || e.getActivePotionEffect(Potion.confusion).getDuration() < 100)
						e.addPotionEffect(new PotionEffect(Potion.confusion.id, 300, 0));
				}
				
				if(d <= 15) {
					double t = (15 - d) / 15 * 10;
					e.attackEntityFrom(ModDamageSource.broadcast, (float) t);
				}
			}
		}

		if(!this.worldObj.isRemote) {
			PacketDispatcher.wrapper.sendToAllAround(new LoopedSoundPacket(this.xCoord, this.yCoord, this.zCoord), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 150));
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

}
