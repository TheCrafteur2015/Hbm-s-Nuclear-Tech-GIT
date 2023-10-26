package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.projectile.EntityBulletBaseNT;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.gui.GUITurretRichard;
import com.hbm.items.ItemAmmoEnums.AmmoRocket;
import com.hbm.items.ModItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityTurretRichard extends TileEntityTurretBaseNT {

	static List<Integer> configs = new ArrayList<>();
	
	static {
		TileEntityTurretRichard.configs.add(BulletConfigSyncingUtil.ROCKET_NORMAL);
		TileEntityTurretRichard.configs.add(BulletConfigSyncingUtil.ROCKET_HE);
		TileEntityTurretRichard.configs.add(BulletConfigSyncingUtil.ROCKET_INCENDIARY);
		TileEntityTurretRichard.configs.add(BulletConfigSyncingUtil.ROCKET_SHRAPNEL);
		TileEntityTurretRichard.configs.add(BulletConfigSyncingUtil.ROCKET_EMP);
		TileEntityTurretRichard.configs.add(BulletConfigSyncingUtil.ROCKET_GLARE);
		TileEntityTurretRichard.configs.add(BulletConfigSyncingUtil.ROCKET_SLEEK);
		TileEntityTurretRichard.configs.add(BulletConfigSyncingUtil.ROCKET_NUKE);
		TileEntityTurretRichard.configs.add(BulletConfigSyncingUtil.ROCKET_CHAINSAW);
		TileEntityTurretRichard.configs.add(BulletConfigSyncingUtil.ROCKET_TOXIC);
		TileEntityTurretRichard.configs.add(BulletConfigSyncingUtil.ROCKET_PHOSPHORUS);
		TileEntityTurretRichard.configs.add(BulletConfigSyncingUtil.ROCKET_CANISTER);
	}
	
	@Override
	protected List<Integer> getAmmoList() {
		return TileEntityTurretRichard.configs;
	}

	@Override
	public String getName() {
		return "container.turretRichard";
	}

	@Override
	public double getTurretDepression() {
		return 25D;
	}

	@Override
	public double getTurretElevation() {
		return 25D;
	}

	@Override
	public double getBarrelLength() {
		return 1.25D;
	}

	@Override
	public long getMaxPower() {
		return 10000;
	}
	
	@Override
	public double getDecetorGrace() {
		return 8D;
	}

	@Override
	public double getDecetorRange() {
		return 64D;
	}
	
	int timer;
	public int loaded;
	int reload;
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(!this.worldObj.isRemote) {
			
			if(this.reload > 0) {
				this.reload--;
				
				if(this.reload == 0)
					this.loaded = 17;
			}
			
			if(this.loaded <= 0 && this.reload <= 0 && getFirstConfigLoaded() != null) {
				this.reload = 100;
			}
			
			if(getFirstConfigLoaded() == null) {
				this.loaded = 0;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("loaded", this.loaded);
			networkPack(data, 250);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		
		if(nbt.hasKey("loaded"))
			this.loaded = nbt.getInteger("loaded");
		else
			super.networkUnpack(nbt);
	}

	@Override
	public void updateFiringTick() {
		
		if(this.reload > 0)
			return;
		
		this.timer++;
		
		if(this.timer > 0 && this.timer % 10 == 0) {
			
			BulletConfiguration conf = getFirstConfigLoaded();
			
			if(conf != null) {
				spawnBullet(conf);
				conusmeAmmo(conf.ammo);
				this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:turret.richard_fire", 2.0F, 1.0F);
				this.loaded--;
				
				if(conf.ammo.equals(new ComparableStack(ModItems.ammo_rocket.stackFromEnum(AmmoRocket.NUCLEAR))))
					this.timer = -50;
				
			} else {
				this.loaded = 0;
			}
		}
	}

	@Override
	public void spawnBullet(BulletConfiguration bullet) {
		
		Vec3 pos = getTurretPos();
		Vec3 vec = Vec3.createVectorHelper(getBarrelLength(), 0, 0);
		vec.rotateAroundZ((float) -this.rotationPitch);
		vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
		
		EntityBulletBaseNT proj = new EntityBulletBaseNT(this.worldObj, BulletConfigSyncingUtil.getKey(bullet));
		proj.setPositionAndRotation(pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord, 0.0F, 0.0F);
		
		proj.setThrowableHeading(vec.xCoord, vec.yCoord, vec.zCoord, bullet.velocity * 0.75F, bullet.spread);
		this.worldObj.spawnEntityInWorld(proj);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.loaded = nbt.getInteger("loaded");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("loaded", this.loaded);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUITurretRichard(player.inventory, this);
	}
}
