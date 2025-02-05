package com.hbm.blocks.network;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.IBlockMulti;
import com.hbm.blocks.ILookOverlay;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.network.TileEntityPipeBaseNT;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class FluidDuctBox extends FluidDuctBase implements IBlockMulti, ILookOverlay {

	@SideOnly(Side.CLIENT) public IIcon[] iconStraight;
	@SideOnly(Side.CLIENT) public IIcon[] iconEnd;
	@SideOnly(Side.CLIENT) public IIcon[] iconCurveTL;
	@SideOnly(Side.CLIENT) public IIcon[] iconCurveTR;
	@SideOnly(Side.CLIENT) public IIcon[] iconCurveBL;
	@SideOnly(Side.CLIENT) public IIcon[] iconCurveBR;
	@SideOnly(Side.CLIENT) public IIcon[] iconJunction;
	
	private static final String[] materials = new String[] { "silver", "copper", "white" };

	public FluidDuctBox(Material mat) {
		super(mat);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);

		int count = FluidDuctBox.materials.length;
		this.iconStraight = new IIcon[count];
		this.iconEnd = new IIcon[count];
		this.iconCurveTL = new IIcon[count];
		this.iconCurveTR = new IIcon[count];
		this.iconCurveBL = new IIcon[count];
		this.iconCurveBR = new IIcon[count];
		this.iconJunction = new IIcon[count];

		for(int i = 0; i < count; i++) {
			this.iconStraight[i] = iconRegister.registerIcon(RefStrings.MODID + ":boxduct_" + FluidDuctBox.materials[i] + "_straight");
			this.iconEnd[i] = iconRegister.registerIcon(RefStrings.MODID + ":boxduct_" + FluidDuctBox.materials[i] + "_end");
			this.iconCurveTL[i] = iconRegister.registerIcon(RefStrings.MODID + ":boxduct_" + FluidDuctBox.materials[i] + "_curve_tl");
			this.iconCurveTR[i] = iconRegister.registerIcon(RefStrings.MODID + ":boxduct_" + FluidDuctBox.materials[i] + "_curve_tr");
			this.iconCurveBL[i] = iconRegister.registerIcon(RefStrings.MODID + ":boxduct_" + FluidDuctBox.materials[i] + "_curve_bl");
			this.iconCurveBR[i] = iconRegister.registerIcon(RefStrings.MODID + ":boxduct_" + FluidDuctBox.materials[i] + "_curve_br");
			this.iconJunction[i] = iconRegister.registerIcon(RefStrings.MODID + ":boxduct_" + FluidDuctBox.materials[i] + "_junction");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {

		TileEntity te = world.getTileEntity(x, y, z);

		boolean nX = canConnectTo(world, x, y, z, Library.NEG_X, te);
		boolean pX = canConnectTo(world, x, y, z, Library.POS_X, te);
		boolean nY = canConnectTo(world, x, y, z, Library.NEG_Y, te);
		boolean pY = canConnectTo(world, x, y, z, Library.POS_Y, te);
		boolean nZ = canConnectTo(world, x, y, z, Library.NEG_Z, te);
		boolean pZ = canConnectTo(world, x, y, z, Library.POS_Z, te);
		
		int mask = 0 + (pX ? 32 : 0) + (nX ? 16 : 0) + (pY ? 8 : 0) + (nY ? 4 : 0) + (pZ ? 2 : 0) + (nZ ? 1 : 0);
		int count = 0 + (pX ? 1 : 0) + (nX ? 1 : 0) + (pY ? 1 : 0) + (nY ? 1 : 0) + (pZ ? 1 : 0) + (nZ ? 1 : 0);
		
		int m = rectify(world.getBlockMetadata(x, y, z));
		
		if((mask & 0b001111) == 0 && mask > 0) {
			return (side == 4 || side == 5) ? this.iconEnd[m] : this.iconStraight[m];
		} else if((mask & 0b111100) == 0 && mask > 0) {
			return (side == 2 || side == 3) ? this.iconEnd[m] : this.iconStraight[m];
		} else if((mask & 0b110011) == 0 && mask > 0) {
			return (side == 0 || side == 1) ? this.iconEnd[m] : this.iconStraight[m];
		} else if(count == 2) {

			if(side == 0 && nY || side == 1 && pY || side == 2 && nZ || side == 3 && pZ || side == 4 && nX || side == 5 && pX)
				return this.iconEnd[m];
			if(side == 1 && nY || side == 0 && pY || side == 3 && nZ || side == 2 && pZ || side == 5 && nX || side == 4 && pX)
				return this.iconStraight[m];

			if(nY && pZ) return side == 4 ? this.iconCurveBR[m] : this.iconCurveBL[m];
			if(nY && nZ) return side == 5 ? this.iconCurveBR[m] : this.iconCurveBL[m];
			if(nY && pX) return side == 3 ? this.iconCurveBR[m] : this.iconCurveBL[m];
			if(nY && nX) return side == 2 ? this.iconCurveBR[m] : this.iconCurveBL[m];
			if(pY && pZ) return side == 4 ? this.iconCurveTR[m] : this.iconCurveTL[m];
			if(pY && nZ) return side == 5 ? this.iconCurveTR[m] : this.iconCurveTL[m];
			if(pY && pX) return side == 3 ? this.iconCurveTR[m] : this.iconCurveTL[m];
			if(pY && nX) return side == 2 ? this.iconCurveTR[m] : this.iconCurveTL[m];

			if(pX && nZ) return side == 0 ? this.iconCurveTR[m] : this.iconCurveTR[m];
			if(pX && pZ) return side == 0 ? this.iconCurveBR[m] : this.iconCurveBR[m];
			if(nX && nZ) return side == 0 ? this.iconCurveTL[m] : this.iconCurveTL[m];
			if(nX && pZ) return side == 0 ? this.iconCurveBL[m] : this.iconCurveBL[m];
			
			return this.iconJunction[m];
		}
		
		return this.iconJunction[m];
	}
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < 15; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	public int damageDropped(int meta) {
		return meta % 15;
	}
	
	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderType() {
		return FluidDuctBox.renderID;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
		return true;
	}

	@Override
	public int getSubCount() {
		return 3;
	}

	
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB entityBounding, List list, Entity entity) {
		
		List<AxisAlignedBB> bbs = new ArrayList<>();

		TileEntity te = world.getTileEntity(x, y, z);

		double lower = 0.125D;
		double upper = 0.875D;
		double jLower = 0.0625D;
		double jUpper = 0.9375D;
		int meta = world.getBlockMetadata(x, y, z);
		
		for(int i = 2; i < 13; i += 3) {
			
			if(meta > i) {
				lower += 0.0625D;
				upper -= 0.0625D;
				jLower += 0.0625D;
				jUpper -= 0.0625D;
			}
		}
	
		boolean nX = canConnectTo(world, x, y, z, Library.NEG_X, te);
		boolean pX = canConnectTo(world, x, y, z, Library.POS_X, te);
		boolean nY = canConnectTo(world, x, y, z, Library.NEG_Y, te);
		boolean pY = canConnectTo(world, x, y, z, Library.POS_Y, te);
		boolean nZ = canConnectTo(world, x, y, z, Library.NEG_Z, te);
		boolean pZ = canConnectTo(world, x, y, z, Library.POS_Z, te);
		int mask = 0 + (pX ? 32 : 0) + (nX ? 16 : 0) + (pY ? 8 : 0) + (nY ? 4 : 0) + (pZ ? 2 : 0) + (nZ ? 1 : 0);
		int count = 0 + (pX ? 1 : 0) + (nX ? 1 : 0) + (pY ? 1 : 0) + (nY ? 1 : 0) + (pZ ? 1 : 0) + (nZ ? 1 : 0);
		
		if(mask == 0) {
			bbs.add(AxisAlignedBB.getBoundingBox(x + jLower, y + jLower, z + jLower, x + jUpper, y + jUpper, z + jUpper));
		} else if(mask == 0b100000 || mask == 0b010000 || mask == 0b110000) {
			bbs.add(AxisAlignedBB.getBoundingBox(x + 0.0D, y + lower, z + lower, x + 1.0D, y + upper, z + upper));
		} else if(mask == 0b001000 || mask == 0b000100 || mask == 0b001100) {
			bbs.add(AxisAlignedBB.getBoundingBox(x + lower, y + 0.0D, z + lower, x + upper, y + 1.0D, z + upper));
		} else if(mask == 0b000010 || mask == 0b000001 || mask == 0b000011) {
			bbs.add(AxisAlignedBB.getBoundingBox(x + lower, y + lower, z + 0.0D, x + upper, y + upper, z + 1.0D));
		} else {
			
			if(count != 2) {
				bbs.add(AxisAlignedBB.getBoundingBox(x + jLower, y + jLower, z + jLower, x + jUpper, y + jUpper, z + jUpper));
			} else {
				bbs.add(AxisAlignedBB.getBoundingBox(x + lower, y + lower, z + lower, x + upper, y + upper, z + upper));
			}
			
			if(pX) bbs.add(AxisAlignedBB.getBoundingBox(x + upper, y + lower, z + lower, x + 1.0D, y + upper, z + upper));
			if(nX) bbs.add(AxisAlignedBB.getBoundingBox(x + 0.0D, y + lower, z + lower, x + lower, y + upper, z + upper));
			if(pY) bbs.add(AxisAlignedBB.getBoundingBox(x + lower, y + upper, z + lower, x + upper, y + 1.0D, z + upper));
			if(nY) bbs.add(AxisAlignedBB.getBoundingBox(x + lower, y + 0.0D, z + lower, x + upper, y + lower, z + upper));
			if(pZ) bbs.add(AxisAlignedBB.getBoundingBox(x + lower, y + lower, z + upper, x + upper, y + upper, z + 1.0D));
			if(nZ) bbs.add(AxisAlignedBB.getBoundingBox(x + lower, y + lower, z + 0.0D, x + upper, y + upper, z + lower));
		}
		
		for(AxisAlignedBB bb : bbs) {
			if(entityBounding.intersectsWith(bb)) {
				list.add(bb);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {

		TileEntity te = world.getTileEntity(x, y, z);

		float lower = 0.125F;
		float upper = 0.875F;
		float jLower = 0.0625F;
		float jUpper = 0.9375F;
		int meta = world.getBlockMetadata(x, y, z);
		
		for(int i = 2; i < 13; i += 3) {
			
			if(meta > i) {
				lower += 0.0625F;
				upper -= 0.0625F;
				jLower += 0.0625F;
				jUpper -= 0.0625F;
			}
		}
	
		boolean nX = canConnectTo(world, x, y, z, Library.NEG_X, te);
		boolean pX = canConnectTo(world, x, y, z, Library.POS_X, te);
		boolean nY = canConnectTo(world, x, y, z, Library.NEG_Y, te);
		boolean pY = canConnectTo(world, x, y, z, Library.POS_Y, te);
		boolean nZ = canConnectTo(world, x, y, z, Library.NEG_Z, te);
		boolean pZ = canConnectTo(world, x, y, z, Library.POS_Z, te);
		int mask = 0 + (pX ? 32 : 0) + (nX ? 16 : 0) + (pY ? 8 : 0) + (nY ? 4 : 0) + (pZ ? 2 : 0) + (nZ ? 1 : 0);
		int count = 0 + (pX ? 1 : 0) + (nX ? 1 : 0) + (pY ? 1 : 0) + (nY ? 1 : 0) + (pZ ? 1 : 0) + (nZ ? 1 : 0);
		
		if(mask == 0) {
			setBlockBounds(jLower, jLower, jLower, jUpper, jUpper, jUpper);
		} else if(mask == 0b100000 || mask == 0b010000 || mask == 0b110000) {
			setBlockBounds(0F, lower, lower, 1F, upper, upper);
		} else if(mask == 0b001000 || mask == 0b000100 || mask == 0b001100) {
			setBlockBounds(lower, 0F, lower, upper, 1F, upper);
		} else if(mask == 0b000010 || mask == 0b000001 || mask == 0b000011) {
			setBlockBounds(lower, lower, 0F, upper, upper, 1F);
		} else {
			
			if(count != 2) {
				setBlockBounds(
						nX ? 0F : jLower,
						nY ? 0F : jLower,
						nZ ? 0F : jLower,
						pX ? 1F : jUpper,
						pY ? 1F : jUpper,
						pZ ? 1F : jUpper);
			} else {
				setBlockBounds(
						nX ? 0F : lower,
						nY ? 0F : lower,
						nZ ? 0F : lower,
						pX ? 1F : upper,
						pY ? 1F : upper,
						pZ ? 1F : upper);
			}
		}
	}
	
	public boolean canConnectTo(IBlockAccess world, int x, int y, int z, ForgeDirection dir, TileEntity tile) {
		if(tile instanceof TileEntityPipeBaseNT) {
			return Library.canConnectFluid(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir, ((TileEntityPipeBaseNT) tile).getType());
		}
		return false;
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(!(te instanceof TileEntityPipeBaseNT))
			return;
		
		TileEntityPipeBaseNT duct = (TileEntityPipeBaseNT) te;
		
		List<String> text = new ArrayList<>();
		text.add("&[" + duct.getType().getColor() + "&]" + duct.getType().getLocalizedName());
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
	
	public static int cachedColor = 0xffffff;
	
	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		return FluidDuctBox.cachedColor;
	}
}
