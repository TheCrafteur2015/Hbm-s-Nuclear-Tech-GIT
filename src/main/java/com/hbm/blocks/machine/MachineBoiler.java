package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityMachineBoiler;
import com.hbm.tileentity.machine.TileEntityMachineBoilerElectric;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MachineBoiler extends BlockContainer {

    private final Random field_149933_a = new Random();
	private final boolean isActive;
	private static boolean keepInventory;
	
	@SideOnly(Side.CLIENT)
	private IIcon iconFront;
	@SideOnly(Side.CLIENT)
	private IIcon iconSide;

	public MachineBoiler(boolean blockState) {
		super(Material.iron);
		this.isActive = blockState;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {

		if(this == ModBlocks.machine_boiler_off || this == ModBlocks.machine_boiler_on) {
			this.iconFront = iconRegister.registerIcon(RefStrings.MODID + (this.isActive ? ":machine_boiler_front_lit" : ":machine_boiler_front"));
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":machine_boiler_base");
			this.iconSide = iconRegister.registerIcon(RefStrings.MODID + ":machine_boiler_side");
		}

		if(this == ModBlocks.machine_boiler_electric_off || this == ModBlocks.machine_boiler_electric_on) {
			this.iconFront = iconRegister.registerIcon(RefStrings.MODID + (this.isActive ? ":machine_boiler_electric_front_lit" : ":machine_boiler_electric_front"));
			this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":machine_boiler_port");
			this.iconSide = iconRegister.registerIcon(RefStrings.MODID + ":machine_boiler_side");
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		
		if(side == 0 || side == 1)
			return this.blockIcon;
		
		return metadata == 0 && side == 3 ? this.iconFront : (side == metadata ? this.iconFront : this.iconSide);
	}
	
	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
		if(this == ModBlocks.machine_boiler_off || this == ModBlocks.machine_boiler_on)
			return Item.getItemFromBlock(ModBlocks.machine_boiler_off);
		if(this == ModBlocks.machine_boiler_electric_off || this == ModBlocks.machine_boiler_electric_on)
			return Item.getItemFromBlock(ModBlocks.machine_boiler_electric_off);
		
		return null;
    }
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		setDefaultDirection(world, x, y, z);
	}
	
	private void setDefaultDirection(World world, int x, int y, int z) {
		if(!world.isRemote)
		{
			Block block1 = world.getBlock(x, y, z - 1);
			Block block2 = world.getBlock(x, y, z + 1);
			Block block3 = world.getBlock(x - 1, y, z);
			Block block4 = world.getBlock(x + 1, y, z);
			
			byte b0 = 3;
			
			if(block1.func_149730_j() && !block2.func_149730_j())
			{
				b0 = 3;
			}
			if(block2.func_149730_j() && !block1.func_149730_j())
			{
				b0 = 2;
			}
			if(block3.func_149730_j() && !block4.func_149730_j())
			{
				b0 = 5;
			}
			if(block4.func_149730_j() && !block3.func_149730_j())
			{
				b0 = 4;
			}
			
			world.setBlockMetadataWithNotify(x, y, z, b0, 2);
		}
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		
		if(i == 0)
		{
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
		if(i == 1)
		{
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		if(i == 2)
		{
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if(i == 3)
		{
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
		
		if(itemStack.hasDisplayName())
		{
			((TileEntityMachineBoiler)world.getTileEntity(x, y, z)).setCustomName(itemStack.getDisplayName());
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {

		if(this == ModBlocks.machine_boiler_off || this == ModBlocks.machine_boiler_on)
			return new TileEntityMachineBoiler();
		if(this == ModBlocks.machine_boiler_electric_off || this == ModBlocks.machine_boiler_electric_on)
			return new TileEntityMachineBoilerElectric();
		
		return null;
	}

	public static void updateBlockState(boolean isProcessing, World world, int x, int y, int z) {
		int i = world.getBlockMetadata(x, y, z);
		Block block = world.getBlock(x, y, z);
		TileEntity entity = world.getTileEntity(x, y, z);
		MachineBoiler.keepInventory = true;

		if(block == ModBlocks.machine_boiler_off || block == ModBlocks.machine_boiler_on)
			if(isProcessing)
			{
				world.setBlock(x, y, z, ModBlocks.machine_boiler_on);
			} else {
				world.setBlock(x, y, z, ModBlocks.machine_boiler_off);
			}

		if(block == ModBlocks.machine_boiler_electric_off || block == ModBlocks.machine_boiler_electric_on)
			if(isProcessing)
			{
				world.setBlock(x, y, z, ModBlocks.machine_boiler_electric_on);
			} else {
				world.setBlock(x, y, z, ModBlocks.machine_boiler_electric_off);
			}
		
		MachineBoiler.keepInventory = false;
		world.setBlockMetadataWithNotify(x, y, z, i, 3);
		
		if(entity != null) {
			entity.validate();
			world.setTileEntity(x, y, z, entity);
		}
	}
	
	@Override
	public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
    {
        if (!MachineBoiler.keepInventory)
        {
        	ISidedInventory tileentityfurnace = (ISidedInventory)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);

            if (tileentityfurnace != null)
            {
                for (int i1 = 0; i1 < tileentityfurnace.getSizeInventory(); ++i1)
                {
                    ItemStack itemstack = tileentityfurnace.getStackInSlot(i1);

                    if (itemstack != null)
                    {
                        float f = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
                        float f1 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
                        float f2 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;

                        while (itemstack.stackSize > 0)
                        {
                            int j1 = this.field_149933_a.nextInt(21) + 10;

                            if (j1 > itemstack.stackSize)
                            {
                                j1 = itemstack.stackSize;
                            }

                            itemstack.stackSize -= j1;
                            EntityItem entityitem = new EntityItem(p_149749_1_, p_149749_2_ + f, p_149749_3_ + f1, p_149749_4_ + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                            if (itemstack.hasTagCompound())
                            {
                                entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                            }

                            float f3 = 0.05F;
                            entityitem.motionX = (float)this.field_149933_a.nextGaussian() * f3;
                            entityitem.motionY = (float)this.field_149933_a.nextGaussian() * f3 + 0.2F;
                            entityitem.motionZ = (float)this.field_149933_a.nextGaussian() * f3;
                            p_149749_1_.spawnEntityInWorld(entityitem);
                        }
                    }
                }

                p_149749_1_.func_147453_f(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
            }
        }

        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(World p_149734_1_, int x, int y, int z, Random rand)
    {
        if (this.isActive) {
        	
        	if(this == ModBlocks.machine_boiler_on) {
	            int l = p_149734_1_.getBlockMetadata(x, y, z);
	            float f = x + 0.5F;
	            float f1 = y + 0.25F + rand.nextFloat() * 6.0F / 16.0F;
	            float f2 = z + 0.5F;
	            float f3 = 0.52F;
	            float f4 = rand.nextFloat() * 0.6F - 0.3F;
	
	            if (l == 4)
	            {
	                p_149734_1_.spawnParticle("smoke", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
	                p_149734_1_.spawnParticle("flame", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
	            }
	            else if (l == 5)
	            {
	                p_149734_1_.spawnParticle("smoke", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
	                p_149734_1_.spawnParticle("flame", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
	            }
	            else if (l == 2)
	            {
	                p_149734_1_.spawnParticle("smoke", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
	                p_149734_1_.spawnParticle("flame", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
	            }
	            else if (l == 3)
	            {
	                p_149734_1_.spawnParticle("smoke", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
	                p_149734_1_.spawnParticle("flame", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
	            }
        	} else {
	            int l = p_149734_1_.getBlockMetadata(x, y, z);
	            float f = x + 0.5F;
	            float f1 = y + 0.25F + rand.nextFloat() * 6.0F / 16.0F;
	            float f2 = z + 0.5F;
	            float f3 = 0.52F;
	            float f4 = rand.nextFloat() * 0.6F - 0.3F;
	
	            if (l == 4)
	            {
	                p_149734_1_.spawnParticle("reddust", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
	            }
	            else if (l == 5)
	            {
	                p_149734_1_.spawnParticle("reddust", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
	            }
	            else if (l == 2)
	            {
	                p_149734_1_.spawnParticle("reddust", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
	            }
	            else if (l == 3)
	            {
	                p_149734_1_.spawnParticle("reddust", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
	            }
        	}
        }
    }
}
