package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.CasingEjector;
import com.hbm.inventory.container.ContainerTurretBase;
import com.hbm.inventory.gui.GUITurretSentry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityTurretSentry extends TileEntityTurretBaseNT implements IGUIProvider {

	private boolean didJustShootLeft = false;
	private boolean retractingLeft = false;
	public double barrelLeftPos = 0;
	public double lastBarrelLeftPos = 0;
	private boolean didJustShootRight = false;
	private boolean retractingRight = false;
	public double barrelRightPos = 0;
	public double lastBarrelRightPos = 0;

	static List<Integer> configs = new ArrayList<>();
	
	static {
		TileEntityTurretSentry.configs.add(BulletConfigSyncingUtil.R5_NORMAL);
		TileEntityTurretSentry.configs.add(BulletConfigSyncingUtil.R5_EXPLOSIVE);
		TileEntityTurretSentry.configs.add(BulletConfigSyncingUtil.R5_DU);
		TileEntityTurretSentry.configs.add(BulletConfigSyncingUtil.R5_STAR);
		TileEntityTurretSentry.configs.add(BulletConfigSyncingUtil.CHL_R5);
	}
	
	@Override
	protected List<Integer> getAmmoList() {
		return TileEntityTurretSentry.configs;
	}

	@Override
	public String getName() {
		return "container.turretSentry";
	}
	
	@Override
	public double getTurretDepression() {
		return 20D;
	}

	@Override
	public double getTurretElevation() {
		return 20D;
	}

	@Override
	public int getDecetorInterval() {
		return 10;
	}

	@Override
	public double getDecetorRange() {
		return 24D;
	}

	@Override
	public double getDecetorGrace() {
		return 2D;
	}

	@Override
	public long getMaxPower() {
		return 1_000;
	}

	@Override
	public long getConsumption() {
		return 5;
	}

	@Override
	public double getBarrelLength() {
		return 1.25D;
	}

	@Override
	public double getAcceptableInaccuracy() {
		return 15;
	}

	@Override
	public boolean hasThermalVision() {
		return false;
	}

	@Override
	public Vec3 getHorizontalOffset() {
		return Vec3.createVectorHelper(0.5, 0, 0.5);
	}
	
	@Override
	public void updateEntity() {
		
		if(this.worldObj.isRemote) {
			this.lastBarrelLeftPos = this.barrelLeftPos;
			this.lastBarrelRightPos = this.barrelRightPos;

			float retractSpeed = 0.5F;
			float pushSpeed = 0.25F;
			
			if(this.retractingLeft) {
				this.barrelLeftPos += retractSpeed;
				
				if(this.barrelLeftPos >= 1) {
					this.retractingLeft = false;
				}
				
			} else {
				this.barrelLeftPos -= pushSpeed;
				if(this.barrelLeftPos < 0) {
					this.barrelLeftPos = 0;
				}
			}
			
			if(this.retractingRight) {
				this.barrelRightPos += retractSpeed;
				
				if(this.barrelRightPos >= 1) {
					this.retractingRight = false;
				}
				
			} else {
				this.barrelRightPos -= pushSpeed;
				if(this.barrelRightPos < 0) {
					this.barrelRightPos = 0;
				}
			}
		}
		
		super.updateEntity();
	}
	
	boolean shotSide = false;
	int timer;

	@Override
	public void updateFiringTick() {
		
		this.timer++;
		
		if(this.timer % 10 == 0) {
			
			BulletConfiguration conf = getFirstConfigLoaded();
			
			if(conf != null) {
				this.cachedCasingConfig = conf.spentCasing;
				spawnBullet(conf);
				conusmeAmmo(conf.ammo);
				this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:turret.sentry_fire", 2.0F, 1.0F);
				
				Vec3 pos = getTurretPos();
				Vec3 vec = Vec3.createVectorHelper(getBarrelLength(), 0, 0);
				vec.rotateAroundZ((float) -this.rotationPitch);
				vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
				
				Vec3 side = Vec3.createVectorHelper(0.125 * (this.shotSide ? 1 : -1), 0, 0);
				side.rotateAroundY((float) -(this.rotationYaw));
				
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "vanillaExt");
				data.setString("mode", "largeexplode");
				data.setFloat("size", 1F);
				data.setByte("count", (byte)1);
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, pos.xCoord + vec.xCoord + side.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord + side.zCoord), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
				
				if(this.shotSide) {
					this.didJustShootLeft = true;
				} else {
					this.didJustShootRight = true;
				}
				this.shotSide = !this.shotSide;
			}
		}
	}

	@Override
	protected Vec3 getCasingSpawnPos() {
		
		Vec3 pos = getTurretPos();
		Vec3 vec = Vec3.createVectorHelper(0, 0.25,-0.125);
		vec.rotateAroundZ((float) -this.rotationPitch);
		vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
		
		return Vec3.createVectorHelper(pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord);
	}

	protected static CasingEjector ejector = new CasingEjector().setMotion(-0.3, 0.6, 0).setAngleRange(0.01F, 0.01F);
	
	@Override
	protected CasingEjector getEjector() {
		return TileEntityTurretSentry.ejector.setMotion(0.3, 0.6, 0);
	}
	
	@Override
	public boolean usesCasings() {
		return true;
	}
	
	@Override
	protected void seekNewTarget() {
		Entity lastTarget = this.target;
		super.seekNewTarget();
		
		if(lastTarget != this.target && this.target != null) {
			this.worldObj.playSoundAtEntity(this.target, "hbm:turret.sentry_lockon", 2.0F, 1.5F);
		}
	}

	@Override
	protected NBTTagCompound writePacket() {
		NBTTagCompound data = super.writePacket();
		if(this.didJustShootLeft) data.setBoolean("justShotLeft", this.didJustShootLeft);
		if(this.didJustShootRight) data.setBoolean("justShotRight", this.didJustShootRight);
		this.didJustShootLeft = false;
		this.didJustShootRight = false;
		return data;
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		super.networkUnpack(nbt);
		if(nbt.getBoolean("justShotLeft")) this.retractingLeft = true;
		if(nbt.getBoolean("justShotRight")) this.retractingRight = true;
	}
	
	@Override
	protected void updateConnections() {
		trySubscribe(this.worldObj, this.xCoord, this.yCoord - 1, this.zCoord, ForgeDirection.DOWN);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerTurretBase(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUITurretSentry(player.inventory, this);
	}
}
