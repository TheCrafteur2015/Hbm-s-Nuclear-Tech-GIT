package com.hbm.blocks.bomb;

import java.util.Arrays;
import java.util.List;

import com.hbm.blocks.IBlockMulti;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.blocks.ModBlocks;
import com.hbm.entity.projectile.EntityShrapnel;
import com.hbm.explosion.ExplosionNT;
import com.hbm.explosion.ExplosionNT.ExAttrib;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class BlockVolcano extends BlockContainer implements ITooltipProvider, IBlockMulti {

	public BlockVolcano() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityVolcanoCore();
	}

	@Override
	public int getSubCount() {
		return 5;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < 5; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean ext) {
		
		int meta = stack.getItemDamage();
		
		if(meta == BlockVolcano.META_SMOLDERING) {
			list.add(EnumChatFormatting.GOLD + "SHIELD VOLCANO");
			return;
		}
		
		list.add(BlockVolcano.isGrowing(meta) ? (EnumChatFormatting.RED + "DOES GROW") : (EnumChatFormatting.DARK_GRAY + "DOES NOT GROW"));
		list.add(BlockVolcano.isExtinguishing(meta) ? (EnumChatFormatting.RED + "DOES EXTINGUISH") : (EnumChatFormatting.DARK_GRAY + "DOES NOT EXTINGUISH"));
	}

	public static final int META_STATIC_ACTIVE = 0;
	public static final int META_STATIC_EXTINGUISHING = 1;
	public static final int META_GROWING_ACTIVE = 2;
	public static final int META_GROWING_EXTINGUISHING = 3;
	public static final int META_SMOLDERING = 4;
	
	public static boolean isGrowing(int meta) {
		return meta == BlockVolcano.META_GROWING_ACTIVE || meta == BlockVolcano.META_GROWING_EXTINGUISHING;
	}
	
	public static boolean isExtinguishing(int meta) {
		return meta == BlockVolcano.META_STATIC_EXTINGUISHING || meta == BlockVolcano.META_GROWING_EXTINGUISHING;
	}
	
	public static class TileEntityVolcanoCore extends TileEntity {
		
		private static List<ExAttrib> volcanoExplosion = Arrays.asList(new ExAttrib[] {ExAttrib.NODROP, ExAttrib.LAVA_V, ExAttrib.NOSOUND, ExAttrib.ALLMOD, ExAttrib.NOHURT});
		
		public int volcanoTimer;
		
		@Override
		public void updateEntity() {
			
			if(!this.worldObj.isRemote) {
				this.volcanoTimer++;

				if(this.volcanoTimer % 10 == 0) {
					//if that type has a vertical channel, blast it open and raise the magma
					if(hasVerticalChannel()) {
						blastMagmaChannel();
						raiseMagma();
					}
					
					double magmaChamber = magmaChamberSize();
					if(magmaChamber > 0) blastMagmaChamber(magmaChamber);
					
					Object[] melting = surfaceMeltingParams();
					if(melting != null) meltSurface((int)melting[0], (double)melting[1], (double)melting[2]);
					
					//self-explanatory
					if(isSpewing()) spawnBlobs();
					if(isSmoking()) spawnSmoke();
					
					//generates a 3x3x3 cube of lava
					surroundLava();
				}
				
				if(this.volcanoTimer >= getUpdateRate()) {
					this.volcanoTimer = 0;
					
					if(shouldGrow()) {
						this.worldObj.setBlock(this.xCoord, this.yCoord + 1, this.zCoord, getBlockType(), getBlockMetadata(), 3);
						this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, ModBlocks.volcanic_lava_block);
						return;
					} else if(this.isExtinguishing()) {
						this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, ModBlocks.volcanic_lava_block);
						return;
					}
				}
			}
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			this.volcanoTimer = nbt.getInteger("timer");
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setInteger("tier", this.volcanoTimer);
		}
		
		private boolean shouldGrow() {
			return isGrowing() && this.yCoord < 200;
		}
		
		private boolean isGrowing() {
			int meta = getBlockMetadata();
			return meta == BlockVolcano.META_GROWING_ACTIVE || meta == BlockVolcano.META_GROWING_EXTINGUISHING;
		}
		
		private boolean isExtinguishing() {
			int meta = getBlockMetadata();
			return meta == BlockVolcano.META_STATIC_EXTINGUISHING || meta == BlockVolcano.META_GROWING_EXTINGUISHING;
		}
		
		private boolean isSmoking() {
			return getBlockMetadata() != BlockVolcano.META_SMOLDERING;
		}
		
		private boolean isSpewing() {
			return getBlockMetadata() != BlockVolcano.META_SMOLDERING;
		}
		
		private boolean hasVerticalChannel() {
			return getBlockMetadata() != BlockVolcano.META_SMOLDERING;
		}
		
		private double magmaChamberSize() {
			return getBlockMetadata() == BlockVolcano.META_SMOLDERING ? 15 : 0;
		}
		
		/* count per tick, radius, depth */
		private Object[] surfaceMeltingParams() {
			return getBlockMetadata() == BlockVolcano.META_SMOLDERING ? new Object[] {50, 50D, 10D} : null;
		}
		
		private int getUpdateRate() {
			switch(getBlockMetadata()) {
			case META_STATIC_EXTINGUISHING: return 60 * 60 * 20; //once per hour
			case META_GROWING_ACTIVE:
			case META_GROWING_EXTINGUISHING: return 60 * 60 * 20 / 250; //250x per hour
			default: return 10;
			}
		}
		
		/* TODO */
		@SuppressWarnings("unused")
		private boolean doesPyroclastic() {
			return false;
		}
		
		@SuppressWarnings("unused")
		private double getPyroclasticRange() {
			return 0D;
		}
		
		/** Causes two magma explosions, one from bedrock to the core and one from the core to 15 blocks above. */
		private void blastMagmaChannel() {
			ExplosionNT explosion = new ExplosionNT(this.worldObj, null, this.xCoord + 0.5, this.yCoord + this.worldObj.rand.nextInt(15) + 1.5, this.zCoord + 0.5, 7);
			explosion.addAllAttrib(TileEntityVolcanoCore.volcanoExplosion).explode();
			ExplosionNT explosion2 = new ExplosionNT(this.worldObj, null, this.xCoord + 0.5 + this.worldObj.rand.nextGaussian() * 3, this.worldObj.rand.nextInt(this.yCoord + 1), this.zCoord + 0.5 + this.worldObj.rand.nextGaussian() * 3, 10);
			explosion2.addAllAttrib(TileEntityVolcanoCore.volcanoExplosion).explode();
		}
		
		/** Causes two magma explosions at a random position around the core, one at normal and one at half range. */
		private void blastMagmaChamber(double size) {
			
			for(int i = 0; i < 2; i++) {
				double dist = size / (i + 1);
				ExplosionNT explosion = new ExplosionNT(this.worldObj, null, this.xCoord + 0.5 + this.worldObj.rand.nextGaussian() * dist, this.yCoord + 0.5 + this.worldObj.rand.nextGaussian() * dist, this.zCoord + 0.5 + this.worldObj.rand.nextGaussian() * dist, 7);
				explosion.addAllAttrib(TileEntityVolcanoCore.volcanoExplosion).explode();
			}
		}
		
		/** Randomly selects surface blocks and converts them into lava if solid or air if not solid. */
		private void meltSurface(int count, double radius, double depth) {
			
			for(int i = 0; i < count; i++) {
				int x = (int) Math.floor(this.xCoord + this.worldObj.rand.nextGaussian() * radius);
				int z = (int) Math.floor(this.zCoord + this.worldObj.rand.nextGaussian() * radius);
				//gaussian distribution makes conversions more likely on the surface and rarer at the bottom
				int y = this.worldObj.getHeightValue(x, z) + 1 - (int) Math.floor(Math.abs(this.worldObj.rand.nextGaussian() * depth));
				
				Block b = this.worldObj.getBlock(x, y, z);
				
				if(!b.isAir(this.worldObj, x, y, z) && b.getExplosionResistance(null) < Blocks.obsidian.getExplosionResistance(null)) {
					//turn into lava if solid block, otherwise just break
					this.worldObj.setBlock(x, y, z, b.isNormalCube() ? ModBlocks.volcanic_lava_block : Blocks.air);
				}
			}
		}
		
		/** Increases the magma level in a small radius around the core. */
		private void raiseMagma() {

			int rX = this.xCoord - 10 + this.worldObj.rand.nextInt(21);
			int rY = this.yCoord + this.worldObj.rand.nextInt(11);
			int rZ = this.zCoord - 10 + this.worldObj.rand.nextInt(21);
			
			if(this.worldObj.getBlock(rX, rY, rZ) == Blocks.air && this.worldObj.getBlock(rX, rY - 1, rZ) == ModBlocks.volcanic_lava_block)
				this.worldObj.setBlock(rX, rY, rZ, ModBlocks.volcanic_lava_block);
		}
		
		/** Creates a 3x3x3 lava sphere around the core. */
		private void surroundLava() {
			
			for(int i = -1; i <= 1; i++) {
				for(int j = -1; j <= 1; j++) {
					for(int k = -1; k <= 1; k++) {
						
						if(i != 0 || j != 0 || k != 0) {
							this.worldObj.setBlock(this.xCoord + i, this.yCoord + j, this.zCoord + k, ModBlocks.volcanic_lava_block);
						}
					}
				}
			}
		}
		
		/** Spews specially tagged shrapnels which create volcanic lava and monoxide clouds. */
		private void spawnBlobs() {
			
			for(int i = 0; i < 3; i++) {
				EntityShrapnel frag = new EntityShrapnel(this.worldObj);
				frag.setLocationAndAngles(this.xCoord + 0.5, this.yCoord + 1.5, this.zCoord + 0.5, 0.0F, 0.0F);
				frag.motionY = 1D + this.worldObj.rand.nextDouble();
				frag.motionX = this.worldObj.rand.nextGaussian() * 0.2D;
				frag.motionZ = this.worldObj.rand.nextGaussian() * 0.2D;
				frag.setVolcano(true);
				this.worldObj.spawnEntityInWorld(frag);
			}
		}
		
		/** I SEE SMOKE, AND WHERE THERE'S SMOKE THERE'S FIRE! */
		private void spawnSmoke() {
			NBTTagCompound dPart = new NBTTagCompound();
			dPart.setString("type", "vanillaExt");
			dPart.setString("mode", "volcano");
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(dPart, this.xCoord + 0.5, this.yCoord + 10, this.zCoord + 0.5), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord + 0.5, this.yCoord + 10, this.zCoord + 0.5, 250));
		}
	}
}
