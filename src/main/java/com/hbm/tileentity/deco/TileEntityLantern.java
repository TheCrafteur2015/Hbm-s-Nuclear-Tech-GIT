package com.hbm.tileentity.deco;

import java.util.List;

import com.hbm.entity.mob.EntityGlyphid;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityLantern extends TileEntity {
	
	
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote && this.worldObj.getTotalWorldTime() % 20 == 0) {
			
			List<EntityGlyphid> glyphids = this.worldObj.getEntitiesWithinAABB(EntityGlyphid.class, AxisAlignedBB.getBoundingBox(this.xCoord + 0.5, this.yCoord + 5.5, this.zCoord + 0.5, this.xCoord + 0.5, this.yCoord + 5.5, this.zCoord + 0.5).expand(7.5, 7.5, 7.5));
			
			for(EntityGlyphid glyphid : glyphids) {
				glyphid.addPotionEffect(new PotionEffect(Potion.blindness.id, 100, 0));
			}
		}
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(
					this.xCoord,
					this.yCoord,
					this.zCoord,
					this.xCoord + 1,
					this.yCoord + 6,
					this.zCoord + 1
					);
		}
		
		return this.bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
