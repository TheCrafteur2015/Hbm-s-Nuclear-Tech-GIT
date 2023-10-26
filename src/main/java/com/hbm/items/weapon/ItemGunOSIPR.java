package com.hbm.items.weapon;

import com.hbm.entity.projectile.EntityCombineBallNT;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGunOSIPR extends ItemGunBase {

	public ItemGunOSIPR(GunConfiguration config, GunConfiguration alt) {
		super(config, alt);
	}
	
	@Override
	protected void altFire(ItemStack stack, World world, EntityPlayer player) {
		
		ItemGunOSIPR.setCharge(stack, 1);
		world.playSoundAtEntity(player, "hbm:weapon.osiprCharging", 1.0F, 1F);
	}

	@Override
	protected void updateServer(ItemStack stack, World world, EntityPlayer player, int slot, boolean isCurrentItem) {
		super.updateServer(stack, world, player, slot, isCurrentItem);
		
		if(!isCurrentItem) {
			ItemGunOSIPR.setCharge(stack, 0);
			return;
		}
		
		int i = ItemGunOSIPR.getCharge(stack);
		
		if(i >= 20) {
			EntityCombineBallNT energyBall = new EntityCombineBallNT(world, BulletConfigSyncingUtil.SPECIAL_OSIPR_CHARGED, player);
			world.spawnEntityInWorld(energyBall);
			world.playSoundAtEntity(player, this.altConfig.firingSound, 1.0F, 1F);
			ItemGunOSIPR.setCharge(stack, 0);
			ItemGunBase.setDelay(stack, this.altConfig.rateOfFire);
			player.inventory.consumeInventoryItem(ModItems.gun_osipr_ammo2);
			
		} else if(i > 0)
			ItemGunOSIPR.setCharge(stack, i + 1);
	}

	@Override
	protected boolean tryShoot(ItemStack stack, World world, EntityPlayer player, boolean main) {
		
		return super.tryShoot(stack, world, player, main) && ItemGunOSIPR.getCharge(stack) == 0;
	}
	
	/// CMB charge state ///
	public static void setCharge(ItemStack stack, int i) {
		ItemGunBase.writeNBT(stack, "cmb_charge", i);
	}
	
	public static int getCharge(ItemStack stack) {
		return ItemGunBase.readNBT(stack, "cmb_charge");
	}
}
