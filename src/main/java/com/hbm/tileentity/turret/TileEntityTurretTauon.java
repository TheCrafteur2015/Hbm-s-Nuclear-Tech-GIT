package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.inventory.gui.GUITurretTauon;
import com.hbm.lib.ModDamageSource;
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

public class TileEntityTurretTauon extends TileEntityTurretBaseNT {

	static List<Integer> configs = new ArrayList<>();
	
	static {
		TileEntityTurretTauon.configs.add(BulletConfigSyncingUtil.SPECIAL_GAUSS);
	}
	
	@Override
	protected List<Integer> getAmmoList() {
		return TileEntityTurretTauon.configs;
	}

	@Override
	public String getName() {
		return "container.turretTauon";
	}

	@Override
	public double getDecetorGrace() {
		return 3D;
	}

	@Override
	public double getTurretYawSpeed() {
		return 9D;
	}

	@Override
	public double getTurretPitchSpeed() {
		return 6D;
	}

	@Override
	public double getTurretElevation() {
		return 35D;
	}

	@Override
	public double getTurretDepression() {
		return 35D;
	}

	@Override
	public double getDecetorRange() {
		return 128D;
	}

	@Override
	public double getBarrelLength() {
		return 2.0D - 0.0625D;
	}

	@Override
	public long getMaxPower() {
		return 100000;
	}

	@Override
	public long getConsumption() {
		return 1000;
	}
	
	int timer;
	public int beam;
	public float spin;
	public float lastSpin;
	public double lastDist;
	
	@Override
	public void updateEntity() {
		
		if(this.worldObj.isRemote) {
			
			if(this.tPos != null) {
				Vec3 pos = getTurretPos();
				double length = Vec3.createVectorHelper(this.tPos.xCoord - pos.xCoord, this.tPos.yCoord - pos.yCoord, this.tPos.zCoord - pos.zCoord).lengthVector();
				this.lastDist = length;
			}
			
			if(this.beam > 0)
				this.beam--;
			
			this.lastSpin = this.spin;
			
			if(this.tPos != null) {
				this.spin += 45;
			}
			
			if(this.spin >= 360F) {
				this.spin -= 360F;
				this.lastSpin -= 360F;
			}
		}
		
		super.updateEntity();
	}

	@Override
	public void updateFiringTick() {
		
		this.timer++;
		
		if(this.timer % 5 == 0) {
			
			BulletConfiguration conf = getFirstConfigLoaded();
			
			if(conf != null && this.target != null) {
				this.target.attackEntityFrom(ModDamageSource.electricity, 30F + this.worldObj.rand.nextInt(11));
				conusmeAmmo(conf.ammo);
				this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:weapon.tauShoot", 4.0F, 0.9F + this.worldObj.rand.nextFloat() * 0.3F);
				
				NBTTagCompound data = new NBTTagCompound();
				data.setBoolean("shot", true);
				networkPack(data, 250);
				
				Vec3 pos = getTurretPos();
				Vec3 vec = Vec3.createVectorHelper(getBarrelLength(), 0, 0);
				vec.rotateAroundZ((float) -this.rotationPitch);
				vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
				
				NBTTagCompound dPart = new NBTTagCompound();
				dPart.setString("type", "tau");
				dPart.setByte("count", (byte)5);
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(dPart, pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
			}
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		
		if(nbt.hasKey("shot"))
			this.beam = 3;
		else
			super.networkUnpack(nbt);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUITurretTauon(player.inventory, this);
	}
}
