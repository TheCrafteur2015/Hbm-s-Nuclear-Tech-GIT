package com.hbm.items.weapon;

import com.hbm.handler.GunConfiguration;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGunLacunae extends ItemGunBase {
	
	public ItemGunLacunae(GunConfiguration config) {
		super(config);
	}
	
	@Override
	public void startAction(ItemStack stack, World world, EntityPlayer player, boolean main) {
		
		if(main) {
			ItemGunBase.setDelay(stack, 20);
			world.playSoundAtEntity(player, "hbm:weapon.lacunaeSpinup", 1.0F, 1.0F);
		}
	}
	
	@Override
	public void endAction(ItemStack stack, World world, EntityPlayer player, boolean main) {
		
		if(main)
			world.playSoundAtEntity(player, "hbm:weapon.lacunaeSpindown", 1.0F, 1.0F);
	}
	
	@Override
	protected void updateServer(ItemStack stack, World world, EntityPlayer player, int slot, boolean isCurrentItem) {
		
		super.updateServer(stack, world, player, slot, isCurrentItem);
		
		if(ItemGunBase.getIsMouseDown(stack)) {
			
			int rot = ItemGunBase.readNBT(stack, "rot") % 360;
			rot += 25;
			ItemGunBase.writeNBT(stack, "rot", rot);
		}
	}
}
