package com.hbm.items;

import java.util.List;
import java.util.Locale;

import com.hbm.lib.RefStrings;
import com.hbm.util.EnumUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemEnumMulti extends Item {
	
	//hell yes, now we're thinking with enums!
	protected Class<? extends Enum<?>> theEnum;
	protected boolean multiName;
	protected boolean multiTexture;

	public ItemEnumMulti(Class<? extends Enum<?>> theEnum, boolean multiName, boolean multiTexture) {
		setHasSubtypes(true);
		this.theEnum = theEnum;
		this.multiName = multiName;
		this.multiTexture = multiTexture;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < this.theEnum.getEnumConstants().length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	public Item setUnlocalizedName(String unlocalizedName) {
		super.setUnlocalizedName(unlocalizedName);
		setTextureName(RefStrings.MODID + ":"+ unlocalizedName);
		return this;
	}
	
	protected IIcon[] icons;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		
		if(this.multiTexture) {
			Enum<?>[] enums = this.theEnum.getEnumConstants();
			this.icons = new IIcon[enums.length];
			
			for(int i = 0; i < this.icons.length; i++) {
				Enum<?> num = enums[i];
				this.icons[i] = reg.registerIcon(getIconString() + "." + num.name().toLowerCase(Locale.US));
			}
		} else {
			this.itemIcon = reg.registerIcon(getIconString());
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		
		if(this.multiTexture) {
			Enum<?> num = EnumUtil.grabEnumSafely(this.theEnum, meta);
			return this.icons[num.ordinal()];
		} else {
			return this.itemIcon;
		}
	}
	
	/** Returns null when the wrong enum is passed. Only really used for recipes anyway so it's good. */
	public ItemStack stackFromEnum(int count, Enum<?> num) {
		
		if(num.getClass() != this.theEnum)
			return null;
		
		return new ItemStack(this, count, num.ordinal());
	}
	
	public ItemStack stackFromEnum(Enum<?> num) {
		return stackFromEnum(1, num);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		
		if(this.multiName) {
			Enum<?> num = EnumUtil.grabEnumSafely(this.theEnum, stack.getItemDamage());
			return super.getUnlocalizedName() + "." + num.name().toLowerCase(Locale.US);
		} else {
			return super.getUnlocalizedName(stack);
		}
	}
}
