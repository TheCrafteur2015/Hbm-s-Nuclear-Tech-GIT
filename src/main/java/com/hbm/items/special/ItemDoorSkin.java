package com.hbm.items.special;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemDoorSkin extends Item {
    protected final IIcon[] icons;

    public ItemDoorSkin(int skinCount) {
        setMaxStackSize(1);
        this.icons = new IIcon[skinCount];
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        for(int i = 0; i < this.icons.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
	@SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        return this.icons[meta];
    }
}
