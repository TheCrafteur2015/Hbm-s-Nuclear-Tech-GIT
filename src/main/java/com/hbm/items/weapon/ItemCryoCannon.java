package com.hbm.items.weapon;

import com.hbm.entity.projectile.EntityChemical;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.packet.GunAnimationPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemCryoCannon extends ItemGunBase {

	public ItemCryoCannon(GunConfiguration config) {
		super(config);
	}

	@Override
	protected void fire(ItemStack stack, World world, EntityPlayer player) {
		
		if((getPressure(stack) >= 1000) || (getTurbine(stack) < 100)) return;

		BulletConfiguration config = null;
		
		if(this.mainConfig.reloadType == GunConfiguration.RELOAD_NONE) {
			config = ItemGunBase.getBeltCfg(player, stack, true);
		} else {
			config = BulletConfigSyncingUtil.pullConfig(this.mainConfig.config.get(ItemGunBase.getMagType(stack)));
		}
		
		int bullets = config.bulletsMin;
		
		for(int k = 0; k < this.mainConfig.roundsPerCycle; k++) {
			
			if(!hasAmmo(stack, player, true))
				break;
			
			if(config.bulletsMax > config.bulletsMin)
				bullets += world.rand.nextInt(config.bulletsMax - config.bulletsMin);
			
			for(int i = 0; i < bullets; i++) {
				spawnProjectile(world, player, stack, BulletConfigSyncingUtil.getKey(config));
			}
			
			useUpAmmo(player, stack, true);
			player.inventoryContainer.detectAndSendChanges();
			
			int wear = (int) Math.ceil(config.wear / (1F + EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack)));
			ItemGunBase.setItemWear(stack, ItemGunBase.getItemWear(stack) + wear);
		}
		
		world.playSoundAtEntity(player, this.mainConfig.firingSound, this.mainConfig.firingVolume, this.mainConfig.firingPitch);
		
		if(this.mainConfig.ejector != null && !this.mainConfig.ejector.getAfterReload())
			ItemGunBase.queueCasing(player, this.mainConfig.ejector, config, stack);
	}

	@Override
	protected void spawnProjectile(World world, EntityPlayer player, ItemStack stack, int config) {
		
		EntityChemical chem = new EntityChemical(world, player);
		chem.setFluid(Fluids.OXYGEN);
		world.spawnEntityInWorld(chem);

		int pressure = getPressure(stack);
		pressure += 5;
		pressure = MathHelper.clamp_int(pressure, 0, 1000);
		setPressure(stack, pressure);
		
		if(player instanceof EntityPlayerMP) PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(AnimType.CYCLE.ordinal()), (EntityPlayerMP) player);
	}

	@Override
	protected void updateServer(ItemStack stack, World world, EntityPlayer player, int slot, boolean isCurrentItem) {
		
		int turbine = getTurbine(stack);
		int pressure = getPressure(stack);
		
		if(getIsMouseDown(stack)) {
			turbine += 10;
		} else {
			turbine -= 5;
			pressure -= 5;
		}

		turbine = MathHelper.clamp_int(turbine, 0, 100);
		pressure = MathHelper.clamp_int(pressure, 0, 1000);
		setTurbine(stack, turbine);
		setPressure(stack, pressure);
		
		super.updateServer(stack, world, player, slot, isCurrentItem);
	}
	
	public static void setTurbine(ItemStack stack, int i) {
		ItemGunBase.writeNBT(stack, "turbine", i);
	}
	
	public static int getTurbine(ItemStack stack) {
		return ItemGunBase.readNBT(stack, "turbine");
	}
	
	public static void setPressure(ItemStack stack, int i) {
		ItemGunBase.writeNBT(stack, "pressure", i);
	}
	
	public static int getPressure(ItemStack stack) {
		return ItemGunBase.readNBT(stack, "pressure");
	}
}
