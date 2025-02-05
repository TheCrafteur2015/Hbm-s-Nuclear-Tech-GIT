package com.hbm.blocks.network;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.IBlockMultiPass;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.lib.RefStrings;
import com.hbm.render.block.RenderBlockMultipass;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.network.TileEntityCableBaseNT;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class BlockCableGauge extends BlockContainer implements IBlockMultiPass, ILookOverlay, ITooltipProvider {
	
	@SideOnly(Side.CLIENT) protected IIcon overlayGauge;

	public BlockCableGauge() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCableGauge();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(RefStrings.MODID + ":deco_red_copper");
		this.overlayGauge = reg.registerIcon(RefStrings.MODID + ":cable_gauge");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		
		if(RenderBlockMultipass.currentPass == 0) {
			return this.blockIcon;
		}
		
		return side == world.getBlockMetadata(x, y, z) ? this.overlayGauge : this.blockIcon;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int l = BlockPistonBase.determineOrientation(world, x, y, z, player);
		world.setBlockMetadataWithNotify(x, y, z, l, 2);
	}

	@Override
	public int getPasses() {
		return 2;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		addStandardInfo(stack, player, list, ext);
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(!(te instanceof TileEntityCableGauge))
			return;
		
		TileEntityCableGauge duct = (TileEntityCableGauge) te;
		
		List<String> text = new ArrayList<>();
		text.add(BobMathUtil.getShortNumber(duct.deltaTick) + "HE/t");
		text.add(BobMathUtil.getShortNumber(duct.deltaLastSecond) + "HE/s");
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
	
	@Override
	public int getRenderType(){
		return IBlockMultiPass.getRenderType();
	}

	public static class TileEntityCableGauge extends TileEntityCableBaseNT implements INBTPacketReceiver {

		private BigInteger lastMeasurement = BigInteger.valueOf(10);
		private long deltaTick = 0;
		private long deltaSecond = 0;
		private long deltaLastSecond = 0;
		
		@Override
		public void updateEntity() {
			super.updateEntity();

			if(!this.worldObj.isRemote) {
				
				if(this.network != null) {
					BigInteger total = this.network.getTotalTransfer();
					BigInteger delta = total.subtract(this.lastMeasurement);
					this.lastMeasurement = total;
					
					try {
						this.deltaTick = delta.longValueExact();
						if(this.worldObj.getTotalWorldTime() % 20 == 0) {
							this.deltaLastSecond = this.deltaSecond;
							this.deltaSecond = 0;
						}
						this.deltaSecond += this.deltaTick;
						
					} catch(Exception ex) { }
				}
				
				NBTTagCompound data = new NBTTagCompound();
				data.setLong("deltaT", this.deltaTick);
				data.setLong("deltaS", this.deltaLastSecond);
				INBTPacketReceiver.networkPack(this, data, 25);
			}
		}

		@Override
		public void networkUnpack(NBTTagCompound nbt) {
			this.deltaTick = Math.max(nbt.getLong("deltaT"), 0);
			this.deltaLastSecond = Math.max(nbt.getLong("deltaS"), 0);
		}
	}
}
