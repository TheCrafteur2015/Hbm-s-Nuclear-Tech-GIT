package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.CasingEjector;
import com.hbm.inventory.gui.GUITurretJeremy;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityTurretJeremy extends TileEntityTurretBaseNT {

	static List<Integer> configs = new ArrayList<>();
	
	static {
		TileEntityTurretJeremy.configs.add(BulletConfigSyncingUtil.SHELL_NORMAL);
		TileEntityTurretJeremy.configs.add(BulletConfigSyncingUtil.SHELL_EXPLOSIVE);
		TileEntityTurretJeremy.configs.add(BulletConfigSyncingUtil.SHELL_AP);
		TileEntityTurretJeremy.configs.add(BulletConfigSyncingUtil.SHELL_DU);
		TileEntityTurretJeremy.configs.add(BulletConfigSyncingUtil.SHELL_W9);
	}
	
	@Override
	protected List<Integer> getAmmoList() {
		return TileEntityTurretJeremy.configs;
	}

	@Override
	public String getName() {
		return "container.turretJeremy";
	}
	
	@Override
	public double getDecetorGrace() {
		return 16D;
	}

	@Override
	public double getTurretDepression() {
		return 45D;
	}

	@Override
	public long getMaxPower() {
		return 10000;
	}

	@Override
	public double getBarrelLength() {
		return 4.25D;
	}

	@Override
	public double getDecetorRange() {
		return 80D;
	}
	
	int timer;
	int reload;
	
	@Override
	public void updateEntity() {
		
		if(this.reload > 0)
			this.reload--;
		
		if(this.reload == 1)
			this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:turret.jeremy_reload", 2.0F, 1.0F);
		
		super.updateEntity();
	}

	@Override
	public void updateFiringTick() {
		
		this.timer++;
		
		if(this.timer % 40 == 0) {
			
			BulletConfiguration conf = getFirstConfigLoaded();
			
			if(conf != null) {
				this.cachedCasingConfig = conf.spentCasing;
				spawnBullet(conf);
				conusmeAmmo(conf.ammo);
				this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:turret.jeremy_fire", 4.0F, 1.0F);
				Vec3 pos = getTurretPos();
				Vec3 vec = Vec3.createVectorHelper(getBarrelLength(), 0, 0);
				vec.rotateAroundZ((float) -this.rotationPitch);
				vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
				
				this.reload = 20;
				
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "vanillaExt");
				data.setString("mode", "largeexplode");
				data.setFloat("size", 0F);
				data.setByte("count", (byte)5);
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
			}
		}
	}

	@Override
	protected Vec3 getCasingSpawnPos() {
		
		Vec3 pos = getTurretPos();
		Vec3 vec = Vec3.createVectorHelper(-2, 0, 0);
		vec.rotateAroundZ((float) -this.rotationPitch);
		vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
		
		return Vec3.createVectorHelper(pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord);
	}

	protected static CasingEjector ejector = new CasingEjector().setAngleRange(0.01F, 0.01F).setMotion(0, 0, -0.2);
	
	@Override
	protected CasingEjector getEjector() {
		return TileEntityTurretJeremy.ejector;
	}
	
	@Override
	public boolean usesCasings() {
		return true;
	}

	@Override
	public int casingDelay() {
		return 22;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUITurretJeremy(player.inventory, this);
	}
}
