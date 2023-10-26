package com.hbm.tileentity.machine;

import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityChimneyIndustrial extends TileEntityChimneyBase {
	
	@Override
	public void spawnParticles() {

		if(this.worldObj.getTotalWorldTime() % 2 == 0) {
			NBTTagCompound fx = new NBTTagCompound();
			fx.setString("type", "tower");
			fx.setFloat("lift", 10F);
			fx.setFloat("base", 0.75F);
			fx.setFloat("max", 3F);
			fx.setInteger("life", 250 + this.worldObj.rand.nextInt(50));
			fx.setInteger("color",0x404040);
			fx.setDouble("posX", this.xCoord + 0.5);
			fx.setDouble("posY", this.yCoord + 22);
			fx.setDouble("posZ", this.zCoord + 0.5);
			MainRegistry.proxy.effectNT(fx);
		}
	}

	@Override
	public double getPollutionMod() {
		return 0.1D;
	}

	@Override
	public boolean cpaturesSoot() {
		return true;
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(
					this.xCoord - 1,
					this.yCoord,
					this.zCoord - 1,
					this.xCoord + 2,
					this.yCoord + 23,
					this.zCoord + 2
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
