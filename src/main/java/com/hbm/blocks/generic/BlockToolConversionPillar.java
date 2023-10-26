package com.hbm.blocks.generic;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockToolConversionPillar extends BlockToolConversion {

	public IIcon[] topIcons;
	public IIcon topIcon;

	public BlockToolConversionPillar(Material mat) {
		super(mat);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {

		this.blockIcon = iconRegister.registerIcon(getTextureName() + "_side");
		this.topIcon = iconRegister.registerIcon(getTextureName() + "_top");
		
		if(this.names != null) {
			this.icons = new IIcon[this.names.length];
			this.topIcons = new IIcon[this.names.length];
			
			for(int i = 0; i < this.names.length; i++) {
				this.icons[i] = iconRegister.registerIcon(getTextureName() + "_side" + this.names[i]);
				this.topIcons[i] = iconRegister.registerIcon(getTextureName() + "_top" + this.names[i]);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		
		metadata -= 1;
		
		if(metadata == -1 || this.icons == null || metadata >= this.icons.length) {
			return side == 0 || side == 1 ? this.topIcon : this.blockIcon;
		}
		
		return side == 0 || side == 1 ? this.topIcons[metadata] : this.icons[metadata];
	}
}
