package com.hbm.inventory;

import java.util.HashMap;

import com.hbm.interfaces.Untested;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;

import net.minecraft.item.ItemStack;

public class UpgradeManager {

	private static HashMap<UpgradeType, Integer> upgrades = new HashMap();
	private static UpgradeType mutexType = null;
	
	@Untested
	public static void eval(ItemStack[] slots, int start, int end) {
		
		UpgradeManager.upgrades.clear();
		
		for(int i = start; i <= end; i++) {
			
			if(slots[i] != null && slots[i].getItem() instanceof ItemMachineUpgrade) {
				ItemMachineUpgrade item = (ItemMachineUpgrade) slots[i].getItem();
				
				if(item.type.mutex) {
					
					if(UpgradeManager.mutexType == null || UpgradeManager.mutexType.ordinal() < item.type.ordinal()) {
						UpgradeManager.mutexType = item.type;
					}
					
				} else {
					Integer up = UpgradeManager.upgrades.get(item.type);
					int upgrade = (up == null ? 0 : up);
					upgrade += item.tier;
					UpgradeManager.upgrades.put(item.type, upgrade);
				}
			}
		}
	}
	
	public static int getLevel(UpgradeType type) {
		Integer up = UpgradeManager.upgrades.get(type);
		return up == null ? 0 : up;
	}
	
	public static UpgradeType getMinerMutex() {
		return UpgradeManager.mutexType;
	}
}
