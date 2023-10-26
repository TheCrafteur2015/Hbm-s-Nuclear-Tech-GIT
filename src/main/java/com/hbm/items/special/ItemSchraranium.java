package com.hbm.items.special;

import java.util.List;

import com.hbm.config.GeneralConfig;
import com.hbm.items.ItemCustomLore;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemSchraranium extends ItemCustomLore {

	IIcon nikonium;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		this.itemIcon = reg.registerIcon(getIconString());
		this.nikonium = reg.registerIcon("hbm:ingot_nikonium");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {

		if(GeneralConfig.enableLBSM && GeneralConfig.enableLBSMFullSchrab)
			return this.nikonium;
		return this.itemIcon;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {

		if(GeneralConfig.enableLBSM && GeneralConfig.enableLBSMFullSchrab)
			return "Nikonium Ingot";
		else
			return super.getItemStackDisplayName(stack);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		if(GeneralConfig.enableLBSM && GeneralConfig.enableLBSMFullSchrab)
			list.add("pankæk");
		else
			super.addInformation(itemstack, player, list, bool);
	}
}
