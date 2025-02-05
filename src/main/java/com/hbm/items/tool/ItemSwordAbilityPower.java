package com.hbm.items.tool;

import java.util.List;

import com.hbm.util.BobMathUtil;

import api.hbm.energy.IBatteryItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemSwordAbilityPower extends ItemSwordAbility implements IBatteryItem {

	public long maxPower = 1;
	public long chargeRate;
	public long consumption;

	public ItemSwordAbilityPower(float damage, double movement, ToolMaterial material, long maxPower, long chargeRate, long consumption) {
		super(damage, movement, material);
		this.maxPower = maxPower;
		this.chargeRate = chargeRate;
		this.consumption = consumption;
		setMaxDamage(1);
	}

	@Override
    public void chargeBattery(ItemStack stack, long i) {
    	if(stack.getItem() instanceof ItemSwordAbilityPower) {
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
    	if(stack.getItem() instanceof ItemSwordAbilityPower) {
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
    	if(stack.getItem() instanceof ItemSwordAbilityPower) {
    		if(stack.hasTagCompound()) {
    			stack.stackTagCompound.setLong("charge", stack.stackTagCompound.getLong("charge") - i);
    		} else {
    			stack.stackTagCompound = new NBTTagCompound();
    			stack.stackTagCompound.setLong("charge", this.maxPower - i);
    		}
    		
    		if(stack.stackTagCompound.getLong("charge") < 0)
    			stack.stackTagCompound.setLong("charge", 0);
    	}
    }

	@Override
    public long getCharge(ItemStack stack) {
    	if(stack.getItem() instanceof ItemSwordAbilityPower) {
    		if(stack.hasTagCompound()) {
    			return stack.stackTagCompound.getLong("charge");
    		} else {
    			stack.stackTagCompound = new NBTTagCompound();
    			stack.stackTagCompound.setLong("charge", ((ItemSwordAbilityPower)stack.getItem()).maxPower);
    			return stack.stackTagCompound.getLong("charge");
    		}
    	}
    	
    	return 0;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
    	
    	list.add("Charge: " + BobMathUtil.getShortNumber(getCharge(stack)) + " / " + BobMathUtil.getShortNumber(this.maxPower));
    	
    	super.addInformation(stack, player, list, ext);
    }

	@Override
    public boolean showDurabilityBar(ItemStack stack) {
    	
        return getCharge(stack) < this.maxPower;
    }

	@Override
    public double getDurabilityForDisplay(ItemStack stack) {
    	
        return 1 - (double)getCharge(stack) / (double)this.maxPower;
    }

	@Override
    protected boolean canOperate(ItemStack stack) {
    	
    	return getCharge(stack) >= this.consumption;
    }

	@Override
    public long getMaxCharge() {
    	return this.maxPower;
    }

	@Override
    public long getChargeRate() {
    	return this.chargeRate;
    }

	@Override
	public long getDischargeRate() {
		return 0;
	}

	@Override
    public void setDamage(ItemStack stack, int damage)
    {
        dischargeBattery(stack, damage * this.consumption);
    }

	@Override
    public boolean isDamageable() {
    	return true;
    }
}
