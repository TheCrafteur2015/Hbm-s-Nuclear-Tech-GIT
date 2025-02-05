package com.hbm.blocks.generic;

import com.hbm.blocks.IBlockMultiPass;
import com.hbm.lib.RefStrings;
import com.hbm.render.block.RenderBlockMultipass;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class BlockFissure extends Block implements IBlockMultiPass {

	private IIcon overlay;

	public BlockFissure() {
		super(Material.rock);
		setBlockTextureName("bedrock");
		setBlockUnbreakable();
		setResistance(1_000_000);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		
		this.blockIcon = reg.registerIcon("bedrock");
		this.overlay = reg.registerIcon(RefStrings.MODID + ":molten_overlay");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		
		if(RenderBlockMultipass.currentPass == 0)
			return Blocks.bedrock.getIcon(0, 0);
		
		return this.overlay;
	}

	@Override
	public int getPasses() {
		return 2;
	}
	
	@Override
	public int getRenderType(){
		return IBlockMultiPass.getRenderType();
	}
}
