package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.Random;

import com.hbm.items.ItemAmmoEnums.Ammo12Gauge;
import com.hbm.items.ItemAmmoEnums.Ammo20Gauge;
import com.hbm.items.ItemAmmoEnums.Ammo357Magnum;
import com.hbm.items.ItemAmmoEnums.Ammo9mm;
import com.hbm.items.ItemAmmoEnums.AmmoGrenade;
import com.hbm.items.ItemAmmoEnums.AmmoRocket;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockAmmoCrate extends Block {

	@SideOnly(Side.CLIENT) private IIcon iconTop;
	@SideOnly(Side.CLIENT) private IIcon iconBottom;

	public BlockAmmoCrate(Material mat) {
		super(mat);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":crate_ammo_top");
		this.iconBottom = iconRegister.registerIcon(RefStrings.MODID + ":crate_ammo_bottom");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":crate_ammo_side");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 0 ? this.iconBottom : (side == 1 ? this.iconTop : this.blockIcon);
	}
	
	Random rand = new Random();
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
	
		ArrayList<ItemStack> ret = new ArrayList<>();
			
		ret.add(new ItemStack(ModItems.cap_nuka, 12 + this.rand.nextInt(21)));
		ret.add(new ItemStack(ModItems.syringe_metal_stimpak, 1 + this.rand.nextInt(3)));
		
		if(this.rand.nextBoolean()) ret.add(new ItemStack(ModItems.ammo_22lr, 16 + this.rand.nextInt(17)));
		if(this.rand.nextBoolean()) ret.add(new ItemStack(ModItems.ammo_9mm, 6 + this.rand.nextInt(13)));
		if(this.rand.nextBoolean()) ret.add(new ItemStack(ModItems.ammo_12gauge, 6 + this.rand.nextInt(4)));
		if(this.rand.nextBoolean()) ret.add(new ItemStack(ModItems.ammo_20gauge, 3 + this.rand.nextInt(4)));
		if(this.rand.nextBoolean()) ret.add(new ItemStack(ModItems.ammo_357, 10 + this.rand.nextInt(11)));
		if(this.rand.nextBoolean()) ret.add(new ItemStack(ModItems.ammo_357, 12 + this.rand.nextInt(15), Ammo357Magnum.IRON.ordinal()));
		if(this.rand.nextBoolean()) ret.add(new ItemStack(ModItems.ammo_50bmg, 2 + this.rand.nextInt(7)));
		if(this.rand.nextBoolean()) ret.add(new ItemStack(ModItems.ammo_rocket, 1));
		if(this.rand.nextBoolean()) ret.add(new ItemStack(ModItems.ammo_grenade, 1 + this.rand.nextInt(2)));
			
		if(this.rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_12gauge, 3, Ammo12Gauge.INCENDIARY.ordinal()));
		if(this.rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_20gauge, 3, Ammo20Gauge.INCENDIARY.ordinal()));
		if(this.rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_20gauge, 3, Ammo20Gauge.CAUSTIC.ordinal()));
		if(this.rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_20gauge, 3, Ammo20Gauge.FLECHETTE.ordinal()));
		if(this.rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_9mm, 7, Ammo9mm.AP.ordinal()));
		if(this.rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_rocket, 1, AmmoRocket.INCENDIARY.ordinal()));
		if(this.rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_rocket, 1, AmmoRocket.SLEEK.ordinal()));
		if(this.rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_grenade, 1, AmmoGrenade.HE.ordinal()));
		if(this.rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_grenade, 1, AmmoGrenade.INCENDIARY.ordinal()));
		if(this.rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_grenade, 1, AmmoGrenade.SLEEK.ordinal()));
		if(this.rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.syringe_metal_super, 2));
		
		return ret;
	}
}
