package com.hbm.items.machine;

import java.util.List;

import com.hbm.util.BobMathUtil;

import api.hbm.energy.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemSelfcharger extends Item implements IBatteryItem {
	
	long charge;
	
	public ItemSelfcharger(long charge) {
		this.charge = charge;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add(EnumChatFormatting.YELLOW + "" + BobMathUtil.getShortNumber(this.charge) + "HE/t");
	}

	@Override
	public void chargeBattery(ItemStack stack, long i) { }

	@Override
	public void setCharge(ItemStack stack, long i) { }

	@Override
	public void dischargeBattery(ItemStack stack, long i) { }

	@Override
	public long getCharge(ItemStack stack) {
		return this.charge;
	}

	@Override
	public long getMaxCharge() {
		return this.charge;
	}

	@Override
	public long getChargeRate() {
		return 0;
	}

	@Override
	public long getDischargeRate() {
		return this.charge;
	}

}
