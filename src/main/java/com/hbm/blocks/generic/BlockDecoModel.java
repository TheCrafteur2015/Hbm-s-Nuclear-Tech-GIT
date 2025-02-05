package com.hbm.blocks.generic;

import com.hbm.blocks.BlockEnumMulti;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDecoModel extends BlockEnumMulti {
	
	public BlockDecoModel(Material mat, Class<? extends Enum<?>> theEnum, boolean multiName, boolean multiTexture) {
		super(mat, theEnum, multiName, multiTexture);
	}
	
	public static int renderID = RenderingRegistry.getNextAvailableRenderId();
	
	@Override
	public int getRenderType() {
		return BlockDecoModel.renderID;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	//Did somebody say - pain?
			//Alright fuckers, looks like 2/b010 = North, 3/b011 = South, 4/b100 = West, 5/b101 = East for sides.
			//I'll just opt for something similar (0/b00 North, 1/b01 South, 2/b10 West, 3/b11 East)
	
	//Assumes meta is using the third and fourth bits.
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		
		int meta;
		
		if((i & 1) != 1)
			meta = i >> 1; //For North(b00>b00) and South(b10>b01), shift bits right by one
		else {
			if(i == 3)
				meta = 2; //For West(b11>b10), just set to 2
			else
				meta = 3; //For East(b01>b11), just set to 3
		}
		
		world.setBlockMetadataWithNotify(x, y, z, (meta << 2) | stack.getItemDamage(), 2);
	}
	
	@Override
	public int damageDropped(int meta) {
		return meta & 3;
	}
	
	//These are separate because they have to be constant
	private float mnX = 0.0F; //min
	private float mnY = 0.0F;
	private float mnZ = 0.0F;
	private float mxX = 1.0F; //max
	private float mxY = 1.0F;
	private float mxZ = 1.0F;
	
	public BlockDecoModel setBlockBoundsTo(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
		this.mnX = minX;
		this.mnY = minY;
		this.mnZ = minZ;
		this.mxX = maxX;
		this.mxY = maxY;
		this.mxZ = maxZ;
		
		return this;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		switch(world.getBlockMetadata(x, y, z) >> 2) {
		case 0://North
			setBlockBounds(1 - this.mxX, this.mnY, 1 - this.mxZ, 1 - this.mnX, this.mxY, 1 - this.mnZ);
			break;
		case 1://South
			setBlockBounds(this.mnX, this.mnY, this.mnZ, this.mxX, this.mxY, this.mxZ);
			break;
		case 2://West
			setBlockBounds(1 - this.mxZ, this.mnY, this.mnX, 1 - this.mnZ, this.mxY, this.mxX);
			break;
		case 3://East
			setBlockBounds(this.mnZ, this.mnY, 1 - this.mxX, this.mxZ, this.mxY, 1 - this.mnX);
			break;
		}
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}
}