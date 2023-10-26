package com.hbm.blocks.fluid;

import java.util.Random;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;

public class GenericFiniteFluid extends BlockFluidFinite {

	@SideOnly(Side.CLIENT)
	public static IIcon stillIcon;
	@SideOnly(Side.CLIENT)
	public static IIcon flowingIcon;
	public Random rand = new Random();
	
	private String stillName;
	private String flowingName;

	public GenericFiniteFluid(Fluid fluid, Material material, String still, String flowing) {
		super(fluid, material);
		setCreativeTab(null);
		this.stillName = still;
		this.flowingName = flowing;
		this.displacements.put(this, false);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return (side == 0 || side == 1) ? GenericFiniteFluid.stillIcon : GenericFiniteFluid.flowingIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		GenericFiniteFluid.stillIcon = register.registerIcon(RefStrings.MODID + ":" + this.stillName);
		GenericFiniteFluid.flowingIcon = register.registerIcon(RefStrings.MODID + ":" + this.flowingName);
	}
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		return this.quantaPerBlock - 1;
	}
}
