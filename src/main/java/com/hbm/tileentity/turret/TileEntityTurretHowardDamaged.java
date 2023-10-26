package com.hbm.tileentity.turret;

import com.hbm.config.WeaponConfig;
import com.hbm.handler.guncfg.GunDGKFactory;
import com.hbm.lib.ModDamageSource;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.util.EntityDamageUtil;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityTurretHowardDamaged extends TileEntityTurretHoward {

	@Override
	public boolean hasPower() { //does not need power
		return true;
	}

	@Override
	public boolean isOn() { //is always on
		return true;
	}

	@Override
	public double getTurretYawSpeed() {
		return 3D;
	}

	@Override
	public double getTurretPitchSpeed() {
		return 2D;
	}

	@Override
	public double getDecetorRange() {
		return 16D;
	}

	@Override
	public double getDecetorGrace() {
		return 5D;
	}

	@Override
	public boolean hasThermalVision() {
		return false;
	}
	
	@Override
	public boolean entityAcceptableTarget(Entity e) { //will fire at any living entity
		
		if(e instanceof EntityPlayer && ((EntityPlayer)e).capabilities.isCreativeMode)
			return false;
		
		return e instanceof EntityLivingBase;
	}

	@Override
	public void updateFiringTick() {
		
		this.timer++;
		
		if(this.tPos != null) {
			
			if(this.timer % 4 == 0) {
				
				this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:turret.howard_fire", 4.0F, 0.7F + this.worldObj.rand.nextFloat() * 0.3F);
				
				this.cachedCasingConfig = GunDGKFactory.CASINGDGK;
				spawnCasing();
				
				if(this.worldObj.rand.nextInt(100) + 1 <= WeaponConfig.ciwsHitrate * 0.5)
					EntityDamageUtil.attackEntityFromIgnoreIFrame(this.target, ModDamageSource.shrapnel, 2F + this.worldObj.rand.nextInt(2));
					
				Vec3 pos = getTurretPos();
				Vec3 vec = Vec3.createVectorHelper(getBarrelLength(), 0, 0);
				vec.rotateAroundZ((float) -this.rotationPitch);
				vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
				
				Vec3 hOff = Vec3.createVectorHelper(0, 0.25, 0);
				hOff.rotateAroundZ((float) -this.rotationPitch);
				hOff.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
					
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "vanillaExt");
				data.setString("mode", "largeexplode");
				data.setFloat("size", 1.5F);
				data.setByte("count", (byte)1);
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, pos.xCoord + vec.xCoord + hOff.xCoord, pos.yCoord + vec.yCoord + hOff.yCoord, pos.zCoord + vec.zCoord + hOff.zCoord), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
			}
		}
	}
	
	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
}
