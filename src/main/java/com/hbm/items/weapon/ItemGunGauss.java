package com.hbm.items.weapon;

import com.hbm.entity.projectile.EntityBulletBaseNT;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.packet.GunAnimationPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.sound.AudioWrapper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGunGauss extends ItemGunBase {
	
	private AudioWrapper chargeLoop;

	public ItemGunGauss(GunConfiguration config, GunConfiguration alt) {
		super(config, alt);
	}
	
	@Override
	public void endAction(ItemStack stack, World world, EntityPlayer player, boolean main) {

		if(ItemGunGauss.getHasShot(stack)) {
			world.playSoundAtEntity(player, "hbm:weapon.sparkShoot", 2.0F, 1.0F);
			ItemGunGauss.setHasShot(stack, false);
		}
		
		if(!main && ItemGunGauss.getStored(stack) > 0) {
			EntityBulletBaseNT bullet = new EntityBulletBaseNT(world, this.altConfig.config.get(0), player);
			bullet.overrideDamage = Math.max(ItemGunGauss.getStored(stack), 1) * 10F;
			world.spawnEntityInWorld(bullet);
			world.playSoundAtEntity(player, "hbm:weapon.tauShoot", 0.5F, 0.75F);
			ItemGunBase.setItemWear(stack, ItemGunBase.getItemWear(stack) + (ItemGunGauss.getCharge(stack)) * 2);
			ItemGunGauss.setCharge(stack, 0);
			
			if(player instanceof EntityPlayerMP)
				PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(AnimType.CYCLE.ordinal()), (EntityPlayerMP) player);
		}
	}
	
	@Override
	public void endActionClient(ItemStack stack, World world, EntityPlayer player, boolean main) {

		if(this.chargeLoop != null) {
			this.chargeLoop.stopSound();
			this.chargeLoop = null;
		}
	}
	
	@Override
	protected void altFire(ItemStack stack, World world, EntityPlayer player) {
		ItemGunGauss.setCharge(stack, 1);
	}
	
	@Override
	public void startActionClient(ItemStack stack, World world, EntityPlayer player, boolean main) {

		if(!main && ItemGunBase.getItemWear(stack) < this.mainConfig.durability && player.inventory.hasItem(ModItems.gun_xvl1456_ammo)) {
			this.chargeLoop = MainRegistry.proxy.getLoopedSound("hbm:weapon.tauChargeLoop2", (float)player.posX, (float)player.posY, (float)player.posZ, 1.0F, 5F, 0.75F);
			world.playSoundAtEntity(player, "hbm:weapon.tauChargeLoop2", 1.0F, 0.75F);
			
			if(this.chargeLoop != null) {
				this.chargeLoop.startSound();
			}
		}
	}
	
	@Override
	protected void updateServer(ItemStack stack, World world, EntityPlayer player, int slot, boolean isCurrentItem) {
		
		super.updateServer(stack, world, player, slot, isCurrentItem);
		
		if(ItemGunBase.getIsAltDown(stack) && ItemGunBase.getItemWear(stack) < this.mainConfig.durability) {
			
			int c = ItemGunGauss.getCharge(stack);
			
			if(c > 200) {
				ItemGunGauss.setCharge(stack, 0);
				ItemGunBase.setItemWear(stack, this.mainConfig.durability);
				player.attackEntityFrom(ModDamageSource.tauBlast, 1000);
				world.newExplosion(player, player.posX, player.posY + player.eyeHeight, player.posZ, 5.0F, true, true);
				return;
			}
			
			if(c > 0) {
				ItemGunGauss.setCharge(stack, c + 1);
				
				if(c % 10 == 1 && c < 140 && c > 2) {
					
					if(player.inventory.hasItem(ModItems.gun_xvl1456_ammo)) {
						player.inventory.consumeInventoryItem(ModItems.gun_xvl1456_ammo);
						ItemGunGauss.setStored(stack, ItemGunGauss.getStored(stack) + 1);
					} else {
						ItemGunGauss.setCharge(stack, 0);
						ItemGunGauss.setStored(stack, 0);
					}
				}
			} else {
				ItemGunGauss.setStored(stack, 0);
			}
		} else {
			ItemGunGauss.setCharge(stack, 0);
			ItemGunGauss.setStored(stack, 0);
		}
	}
	
	@Override
	protected void updateClient(ItemStack stack, World world, EntityPlayer player, int slot, boolean isCurrentItem) {
		super.updateClient(stack, world, player, slot, isCurrentItem);

		if(this.chargeLoop != null) {
			if(!this.chargeLoop.isPlaying()) {
				this.chargeLoop = rebootAudio(this.chargeLoop, player);
			}
			this.chargeLoop.updatePosition((float)player.posX, (float)player.posY, (float)player.posZ);
			this.chargeLoop.updatePitch(1 + (ItemGunGauss.getCharge(stack)) * 0.01F);
		}
	}
	
	public AudioWrapper rebootAudio(AudioWrapper wrapper, EntityPlayer player) {
		wrapper.stopSound();
		AudioWrapper audio = MainRegistry.proxy.getLoopedSound("hbm:weapon.tauChargeLoop2", (float)player.posX, (float)player.posY, (float)player.posZ, wrapper.getVolume(), wrapper.getRange(), wrapper.getPitch());
		audio.startSound();
		return audio;
	}
	
	@Override
	protected void spawnProjectile(World world, EntityPlayer player, ItemStack stack, int config) {
		
		super.spawnProjectile(world, player, stack, config);
		ItemGunGauss.setHasShot(stack, true);
	}
	
	public static void setHasShot(ItemStack stack, boolean b) {
		ItemGunBase.writeNBT(stack, "hasShot", b ? 1 : 0);
	}
	
	public static boolean getHasShot(ItemStack stack) {
		return ItemGunBase.readNBT(stack, "hasShot") == 1;
	}
	
	/// gauss charge state ///
	public static void setCharge(ItemStack stack, int i) {
		ItemGunBase.writeNBT(stack, "gauss_charge", i);
	}
	
	public static int getCharge(ItemStack stack) {
		return ItemGunBase.readNBT(stack, "gauss_charge");
	}
	
	public static void setStored(ItemStack stack, int i) {
		ItemGunBase.writeNBT(stack, "gauss_stored", i);
	}
	
	public static int getStored(ItemStack stack) {
		return ItemGunBase.readNBT(stack, "gauss_stored");
	}
}
