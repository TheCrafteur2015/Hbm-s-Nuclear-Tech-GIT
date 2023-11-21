package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.util.BobMathUtil;

import api.hbm.energy.IBatteryItem;
import com.hbm.util.I18nUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemBattery extends Item implements IBatteryItem {

	protected long maxCharge;
	protected long chargeRate;
	protected long dischargeRate;

	public ItemBattery(long dura, long chargeRate, long dischargeRate) {
		this.maxCharge = dura;
		this.chargeRate = chargeRate;
		this.dischargeRate = dischargeRate;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		long charge = this.maxCharge;
		if(itemstack.hasTagCompound())
			charge = getCharge(itemstack);

		if(itemstack.getItem() != ModItems.fusion_core && itemstack.getItem() != ModItems.energy_core) {
			list.add(I18nUtil.resolveKey("desc.item.battery.charge",BobMathUtil.getShortNumber(charge),BobMathUtil.getShortNumber(maxCharge)));
		} else {
			String charge1 = BobMathUtil.getShortNumber((charge * 100) / this.maxCharge);
			list.add(I18nUtil.resolveKey("desc.item.battery.chargePerc", charge1));
			list.add("(" + BobMathUtil.getShortNumber(charge) + "/" + BobMathUtil.getShortNumber(maxCharge) + "HE)");
		}
		list.add(I18nUtil.resolveKey("desc.item.battery.chargeRate",BobMathUtil.getShortNumber(chargeRate)));
		list.add(I18nUtil.resolveKey("desc.item.battery.dischargeRate",BobMathUtil.getShortNumber(dischargeRate)));
	}

	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {

		if(this == ModItems.battery_schrabidium) {
			return EnumRarity.rare;
		}

		if(this == ModItems.fusion_core || this == ModItems.energy_core) {
			return EnumRarity.uncommon;
		}

		return EnumRarity.common;
	}

	@Override
	public void chargeBattery(ItemStack stack, long i) {
		if(stack.getItem() instanceof ItemBattery) {
			if(stack.hasTagCompound()) {
				stack.stackTagCompound.setLong("charge", stack.stackTagCompound.getLong("charge") + i);
			} else {
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("charge", i);
			}
		}
	}

	@Override
	public void setCharge(ItemStack stack, long i) {
		if(stack.getItem() instanceof ItemBattery) {
			if(stack.hasTagCompound()) {
				stack.stackTagCompound.setLong("charge", i);
			} else {
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("charge", i);
			}
		}
	}

	@Override
	public void dischargeBattery(ItemStack stack, long i) {
		if(stack.getItem() instanceof ItemBattery) {
			if(stack.hasTagCompound()) {
				stack.stackTagCompound.setLong("charge", stack.stackTagCompound.getLong("charge") - i);
			} else {
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("charge", this.maxCharge - i);
			}
		}
	}

	@Override
	public long getCharge(ItemStack stack) {
		if(stack.getItem() instanceof ItemBattery) {
			if(stack.hasTagCompound()) {
				return stack.stackTagCompound.getLong("charge");
			} else {
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("charge", ((ItemBattery) stack.getItem()).maxCharge);
				return stack.stackTagCompound.getLong("charge");
			}
		}

		return 0;
	}

	@Override
	public long getMaxCharge() {
		return this.maxCharge;
	}

	@Override
	public long getChargeRate() {
		return this.chargeRate;
	}

	@Override
	public long getDischargeRate() {
		return this.dischargeRate;
	}

	public static ItemStack getEmptyBattery(Item item) {

		if(item instanceof ItemBattery) {
			ItemStack stack = new ItemStack(item);
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setLong("charge", 0);
			return stack.copy();
		}

		return null;
	}

	public static ItemStack getFullBattery(Item item) {

		if(item instanceof ItemBattery) {
			ItemStack stack = new ItemStack(item);
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setLong("charge", ((ItemBattery) item).getMaxCharge());
			return stack.copy();
		}

		return new ItemStack(item);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return 1D - (double) getCharge(stack) / (double) getMaxCharge();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {

		if(this.chargeRate > 0) {
			list.add(ItemBattery.getEmptyBattery(item));
		}
		
		if(this.dischargeRate > 0) {
			list.add(ItemBattery.getFullBattery(item));
		}
	}
}
