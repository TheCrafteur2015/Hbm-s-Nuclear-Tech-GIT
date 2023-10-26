package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.gui.GUITurretMaxwell;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;
import com.hbm.util.EntityDamageUtil;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityTurretMaxwell extends TileEntityTurretBaseNT {

	@Override
	public String getName() {
		return "container.turretMaxwell";
	}

	@Override
	protected List<Integer> getAmmoList() {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public List<ItemStack> getAmmoTypesForDisplay() {
		
		if(this.ammoStacks != null)
			return this.ammoStacks;
		
		this.ammoStacks = new ArrayList<>();

		this.ammoStacks.add(new ItemStack(ModItems.upgrade_speed_1));
		this.ammoStacks.add(new ItemStack(ModItems.upgrade_speed_2));
		this.ammoStacks.add(new ItemStack(ModItems.upgrade_speed_3));
		this.ammoStacks.add(new ItemStack(ModItems.upgrade_effect_1));
		this.ammoStacks.add(new ItemStack(ModItems.upgrade_effect_2));
		this.ammoStacks.add(new ItemStack(ModItems.upgrade_effect_3));
		this.ammoStacks.add(new ItemStack(ModItems.upgrade_power_1));
		this.ammoStacks.add(new ItemStack(ModItems.upgrade_power_2));
		this.ammoStacks.add(new ItemStack(ModItems.upgrade_power_3));
		this.ammoStacks.add(new ItemStack(ModItems.upgrade_afterburn_1));
		this.ammoStacks.add(new ItemStack(ModItems.upgrade_afterburn_2));
		this.ammoStacks.add(new ItemStack(ModItems.upgrade_afterburn_3));
		this.ammoStacks.add(new ItemStack(ModItems.upgrade_overdrive_1));
		this.ammoStacks.add(new ItemStack(ModItems.upgrade_overdrive_2));
		this.ammoStacks.add(new ItemStack(ModItems.upgrade_overdrive_3));
		this.ammoStacks.add(new ItemStack(ModItems.upgrade_5g));
		this.ammoStacks.add(new ItemStack(ModItems.upgrade_screm));
		
		return this.ammoStacks;
	}
	
	@Override
	public double getAcceptableInaccuracy() {
		return 2;
	}

	@Override
	public double getDecetorGrace() {
		return 5D;
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
		return 40D;
	}

	@Override
	public double getTurretDepression() {
		return 35D;
	}

	@Override
	public double getDecetorRange() {
		return 64D + this.greenLevel * 3;
	}

	@Override
	public long getMaxPower() {
		return 10000000;
	}

	@Override
	public long getConsumption() {
		return this._5g ? 10 : 10000 - this.blueLevel * 300;
	}

	@Override
	public double getBarrelLength() {
		return 2.125D;
	}

	@Override
	public double getHeightOffset() {
		return 2D;
	}
	
	public int beam;
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
		} else {
			
			if(this.checkDelay <= 0) {
				this.checkDelay = 20;
				
				this.redLevel = 0;
				this.greenLevel = 0;
				this.blueLevel = 0;
				this.blackLevel = 0;
				this.pinkLevel = 0;
				this._5g = false;
				this.screm = false;
				
				for(int i = 1; i < 10; i++) {
					if(this.slots[i] != null) {
						Item item = this.slots[i].getItem();
						
						if(item == ModItems.upgrade_speed_1) this.redLevel += 1;
						if(item == ModItems.upgrade_speed_2) this.redLevel += 2;
						if(item == ModItems.upgrade_speed_3) this.redLevel += 3;
						if(item == ModItems.upgrade_effect_1) this.greenLevel += 1;
						if(item == ModItems.upgrade_effect_2) this.greenLevel += 2;
						if(item == ModItems.upgrade_effect_3) this.greenLevel += 3;
						if(item == ModItems.upgrade_power_1) this.blueLevel += 1;
						if(item == ModItems.upgrade_power_2) this.blueLevel += 2;
						if(item == ModItems.upgrade_power_3) this.blueLevel += 3;
						if(item == ModItems.upgrade_afterburn_1) this.pinkLevel += 1;
						if(item == ModItems.upgrade_afterburn_2) this.pinkLevel += 2;
						if(item == ModItems.upgrade_afterburn_3) this.pinkLevel += 3;
						if(item == ModItems.upgrade_overdrive_1) this.blackLevel += 1;
						if(item == ModItems.upgrade_overdrive_2) this.blackLevel += 2;
						if(item == ModItems.upgrade_overdrive_3) this.blackLevel += 3;
						if(item == ModItems.upgrade_5g) this._5g = true;
						if(item == ModItems.upgrade_screm) this.screm = true;
					}
				}
			}
			
			this.checkDelay--;
		}
		
		super.updateEntity();
	}
	
	int redLevel;
	int greenLevel;
	int blueLevel;
	int blackLevel;
	int pinkLevel;
	boolean _5g;
	boolean screm;
	
	int checkDelay;

	@Override
	public void updateFiringTick() {
		
		long demand = getConsumption() * 10;
		
		if(this.target != null && getPower() >= demand) {

			if(this._5g && this.target instanceof EntityPlayer) {
				EntityPlayer living = (EntityPlayer) this.target;
				living.addPotionEffect(new PotionEffect(HbmPotion.death.id, 30 * 60 * 20, 0, true));
			} else {
				EntityDamageUtil.attackEntityFromIgnoreIFrame(this.target, ModDamageSource.microwave, (this.blackLevel * 10 + this.redLevel + 1F) * 0.25F);
			}
			
			if(this.pinkLevel > 0)
				this.target.setFire(this.pinkLevel * 3);
			
			if(!this.target.isEntityAlive() && this.target instanceof EntityLivingBase) {
				NBTTagCompound vdat = new NBTTagCompound();
				vdat.setString("type", "giblets");
				vdat.setInteger("ent", this.target.getEntityId());
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(vdat, this.target.posX, this.target.posY + this.target.height * 0.5, this.target.posZ), new TargetPoint(this.target.dimension, this.target.posX, this.target.posY + this.target.height * 0.5, this.target.posZ, 150));
				
				if(this.screm)
					this.worldObj.playSoundEffect(this.target.posX, this.target.posY, this.target.posZ, "hbm:block.screm", 20.0F, 1.0F);
				else
					this.worldObj.playSoundEffect(this.target.posX, this.target.posY, this.target.posZ, "mob.zombie.woodbreak", 2.0F, 0.95F + this.worldObj.rand.nextFloat() * 0.2F);
			}
			
			this.power -= demand;
			
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("shot", true);
			networkPack(data, 250);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		
		if(nbt.hasKey("shot"))
			this.beam = 5;
		else
			super.networkUnpack(nbt);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUITurretMaxwell(player.inventory, this);
	}
}
