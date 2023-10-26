package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.CasingEjector;
import com.hbm.inventory.gui.GUITurretChekhov;
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

public class TileEntityTurretChekhov extends TileEntityTurretBaseNT {

	static List<Integer> configs = new ArrayList<>();
	
	//because cramming it into the ArrayList's constructor with nested curly brackets and all that turned out to be not as pretty
	//also having a floaty `static` like this looks fun
	//idk if it's just me though
	static {
		TileEntityTurretChekhov.configs.add(BulletConfigSyncingUtil.BMG50_NORMAL);
		TileEntityTurretChekhov.configs.add(BulletConfigSyncingUtil.BMG50_INCENDIARY);
		TileEntityTurretChekhov.configs.add(BulletConfigSyncingUtil.BMG50_EXPLOSIVE);
		TileEntityTurretChekhov.configs.add(BulletConfigSyncingUtil.BMG50_AP);
		TileEntityTurretChekhov.configs.add(BulletConfigSyncingUtil.BMG50_DU);
		TileEntityTurretChekhov.configs.add(BulletConfigSyncingUtil.BMG50_STAR);
		TileEntityTurretChekhov.configs.add(BulletConfigSyncingUtil.BMG50_PHOSPHORUS);
		TileEntityTurretChekhov.configs.add(BulletConfigSyncingUtil.BMG50_SLEEK);
		TileEntityTurretChekhov.configs.add(BulletConfigSyncingUtil.CHL_BMG50);
	}
	
	@Override
	protected List<Integer> getAmmoList() {
		return TileEntityTurretChekhov.configs;
	}

	@Override
	public String getName() {
		return "container.turretChekhov";
	}

	@Override
	public double getTurretElevation() {
		return 45D;
	}

	@Override
	public long getMaxPower() {
		return 10000;
	}

	@Override
	public double getBarrelLength() {
		return 3.5D;
	}

	@Override
	public double getAcceptableInaccuracy() {
		return 15;
	}
	
	int timer;

	@Override
	public void updateFiringTick() {
		
		this.timer++;
		
		if(this.timer > 20 && this.timer % getDelay() == 0) {
			
			BulletConfiguration conf = getFirstConfigLoaded();
			
			if(conf != null) {
				this.cachedCasingConfig = conf.spentCasing;
				spawnBullet(conf);
				conusmeAmmo(conf.ammo);
				this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:turret.chekhov_fire", 2.0F, 1.0F);
				
				Vec3 pos = getTurretPos();
				Vec3 vec = Vec3.createVectorHelper(getBarrelLength(), 0, 0);
				vec.rotateAroundZ((float) -this.rotationPitch);
				vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
				
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "vanillaExt");
				data.setString("mode", "largeexplode");
				data.setFloat("size", 1.5F);
				data.setByte("count", (byte)1);
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
			}
		}
	}

	@Override
	protected Vec3 getCasingSpawnPos() {
		
		Vec3 pos = getTurretPos();
		Vec3 vec = Vec3.createVectorHelper(-1.125, 0.125, 0.25);
		vec.rotateAroundZ((float) -this.rotationPitch);
		vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
		
		return Vec3.createVectorHelper(pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord);
	}

	protected static CasingEjector ejector = new CasingEjector().setMotion(-0.8, 0.8, 0).setAngleRange(0.1F, 0.1F);
	
	@Override
	protected CasingEjector getEjector() {
		return TileEntityTurretChekhov.ejector;
	}
	
	public int getDelay() {
		return 2;
	}
	
	public float spin;
	public float lastSpin;
	private float accel;
	private boolean manual;
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(this.worldObj.isRemote) {
			
			if(this.tPos != null || this.manual) {
				this.accel = Math.min(45F, this.accel += 2);
			} else {
				this.accel = Math.max(0F, this.accel -= 2);
			}
			
			this.manual = false;
			
			this.lastSpin = this.spin;
			this.spin += this.accel;
			
			if(this.spin >= 360F) {
				this.spin -= 360F;
				this.lastSpin -= 360F;
			}
		} else {
			
			if(this.tPos == null && !this.manual) {
				
				this.timer--;
				
				if(this.timer > 20)
					this.timer = 20;
				
				if(this.timer < 0)
					this.timer = 0;
			}
		}
	}
	
	@Override
	public void manualSetup() {
		this.manual = true;
	}
	
	@Override
	public boolean usesCasings() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUITurretChekhov(player.inventory, this);
	}
}
