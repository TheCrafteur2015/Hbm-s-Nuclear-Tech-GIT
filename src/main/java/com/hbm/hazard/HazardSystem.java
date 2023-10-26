package com.hbm.hazard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.hbm.hazard.modifier.HazardModifier;
import com.hbm.hazard.transformer.HazardTransformerBase;
import com.hbm.hazard.type.HazardTypeBase;
import com.hbm.interfaces.Untested;
import com.hbm.inventory.RecipesCommon.ComparableStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

@Untested
public class HazardSystem {

	/*
	 * Map for OreDict entries, always evaluated first. Avoid registering HazardData with 'doesOverride', as internal order is based on the item's ore dict keys.
	 */
	public static final HashMap<String, HazardData> oreMap = new HashMap();
	/*
	 * Map for items, either with wildcard meta or stuff that's expected to have a variety of damage values, like tools.
	 */
	public static final HashMap<Item, HazardData> itemMap = new HashMap();
	/*
	 * Very specific stacks with item and meta matching. ComparableStack does not support NBT matching, to scale hazards with NBT please use HazardModifiers.
	 */
	public static final HashMap<ComparableStack, HazardData> stackMap = new HashMap();
	/*
	 * For items that should, for whichever reason, be completely exempt from the hazard system.
	 */
	public static final HashSet<ComparableStack> stackBlacklist = new HashSet();
	public static final HashSet<String> dictBlacklist = new HashSet();
	/*
	 * List of hazard transformers, called in order before and after unrolling all the HazardEntries.
	 */
	public static final List<HazardTransformerBase> trafos = new ArrayList<>();
	
	/**
	 * Automatically casts the first parameter and registers it to the HazSys
	 * @param o
	 * @param data
	 */
	public static void register(Object o, HazardData data) {

		if(o instanceof String)
			HazardSystem.oreMap.put((String)o, data);
		if(o instanceof Item)
			HazardSystem.itemMap.put((Item)o, data);
		if(o instanceof Block)
			HazardSystem.itemMap.put(Item.getItemFromBlock((Block)o), data);
		if(o instanceof ItemStack)
			HazardSystem.stackMap.put(new ComparableStack((ItemStack)o), data);
		if(o instanceof ComparableStack)
			HazardSystem.stackMap.put((ComparableStack)o, data);
	}
	
	/**
	 * Prevents the stack from returning any HazardData
	 * @param stack
	 */
	public static void blacklist(Object o) {
		
		if(o instanceof ItemStack) {
			HazardSystem.stackBlacklist.add(new ComparableStack((ItemStack) o).makeSingular());
		} else if(o instanceof String) {
			HazardSystem.dictBlacklist.add((String) o);
		}
	}
	
	public static boolean isItemBlacklisted(ItemStack stack) {
		
		if(HazardSystem.stackBlacklist.contains(new ComparableStack(stack).makeSingular()))
			return true;

		int[] ids = OreDictionary.getOreIDs(stack);
		for(int id : ids) {
			String name = OreDictionary.getOreName(id);
			
			if(HazardSystem.dictBlacklist.contains(name))
				return true;
		}
		
		return false;
	}
	
	/**
	 * Will return a full list of applicable HazardEntries for this stack.
	 * <br><br>ORDER:
	 * <ol>
	 * <li>ore dict (if multiple keys, in order of the ore dict keys for this stack)
	 * <li>item
	 * <li>item stack
	 * </ol>
	 * 
	 * "Applicable" means that entries that are overridden or excluded via mutex are not in this list.
	 * Entries that are marked as "overriding" will delete all fetched entries that came before it.
	 * Entries that use mutex will prevent subsequent entries from being considered, shall they collide. The mutex system already assumes that
	 * two keys are the same in priority, so the flipped order doesn't matter.
	 * @param stack
	 * @return
	 */
	public static List<HazardEntry> getHazardsFromStack(ItemStack stack) {
		
		if(HazardSystem.isItemBlacklisted(stack)) {
			return new ArrayList<>();
		}
		
		List<HazardData> chronological = new ArrayList<>();
		
		/// ORE DICT ///
		int[] ids = OreDictionary.getOreIDs(stack);
		for(int id : ids) {
			String name = OreDictionary.getOreName(id);
			
			if(HazardSystem.oreMap.containsKey(name))
				chronological.add(HazardSystem.oreMap.get(name));
		}
		
		/// ITEM ///
		if(HazardSystem.itemMap.containsKey(stack.getItem()))
			chronological.add(HazardSystem.itemMap.get(stack.getItem()));
		
		/// STACK ///
		ComparableStack comp = new ComparableStack(stack).makeSingular();
		if(HazardSystem.stackMap.containsKey(comp))
			chronological.add(HazardSystem.stackMap.get(comp));
		
		List<HazardEntry> entries = new ArrayList<>();
		
		for(HazardTransformerBase trafo : HazardSystem.trafos) {
			trafo.transformPre(stack, entries);
		}
		
		int mutex = 0;
		
		for(HazardData data : chronological) {
			//if the current data is marked as an override, purge all previous data
			if(data.doesOverride)
				entries.clear();
			
			if((data.getMutex() & mutex) == 0) {
				entries.addAll(data.entries);
				mutex = mutex | data.getMutex();
			}
		}
		
		for(HazardTransformerBase trafo : HazardSystem.trafos) {
			trafo.transformPost(stack, entries);
		}
		
		return entries;
	}
	
	public static float getHazardLevelFromStack(ItemStack stack, HazardTypeBase hazard) {
		List<HazardEntry> entries = HazardSystem.getHazardsFromStack(stack);
		
		for(HazardEntry entry : entries) {
			if(entry.type == hazard) {
				return HazardModifier.evalAllModifiers(stack, null, entry.baseLevel, entry.mods);
			}
		}
		
		return 0F;
	}
	
	/**
	 * Will grab and iterate through all assigned hazards of the given stack and apply their effects to the holder.
	 * @param stack
	 * @param entity
	 */
	public static void applyHazards(ItemStack stack, EntityLivingBase entity) {
		List<HazardEntry> hazards = HazardSystem.getHazardsFromStack(stack);
		
		for(HazardEntry hazard : hazards) {
			hazard.applyHazard(stack, entity);
		}
	}
	
	/**
	 * Will apply the effects of all carried items, including the armor inventory.
	 * @param player
	 */
	public static void updatePlayerInventory(EntityPlayer player) {

		for(int i = 0; i < player.inventory.mainInventory.length; i++) {
			
			ItemStack stack = player.inventory.mainInventory[i];
			if(stack != null) {
				HazardSystem.applyHazards(stack, player);
				
				if(stack.stackSize == 0) {
					player.inventory.mainInventory[i] = null;
				}
			}
		}
		
		for(ItemStack stack : player.inventory.armorInventory) {
			if(stack != null) {
				HazardSystem.applyHazards(stack, player);
			}
		}
	}

	public static void updateLivingInventory(EntityLivingBase entity) {
		
		for(int i = 0; i < 5; i++) {
			ItemStack stack = entity.getEquipmentInSlot(i);

			if(stack != null) {
				HazardSystem.applyHazards(stack, entity);
			}
		}
	}

	public static void updateDroppedItem(EntityItem entity) {
		
		ItemStack stack = entity.getEntityItem();
		
		if(entity.isDead || stack == null || stack.getItem() == null || stack.stackSize <= 0) return;
		
		List<HazardEntry> hazards = HazardSystem.getHazardsFromStack(stack);
		for(HazardEntry entry : hazards) {
			entry.type.updateEntity(entity, HazardModifier.evalAllModifiers(stack, null, entry.baseLevel, entry.mods));
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void addFullTooltip(ItemStack stack, EntityPlayer player, List list) {
		
		List<HazardEntry> hazards = HazardSystem.getHazardsFromStack(stack);
		
		for(HazardEntry hazard : hazards) {
			hazard.type.addHazardInformation(player, list, hazard.baseLevel, stack, hazard.mods);
		}
	}
}
