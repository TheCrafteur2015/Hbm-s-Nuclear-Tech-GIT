package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.IPersistentInfoProvider;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.IPersistentNBT;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.energy.IEnergyUser;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineCapacitor extends BlockContainer implements ILookOverlay, IPersistentInfoProvider, ITooltipProvider {

	@SideOnly(Side.CLIENT) public IIcon iconTop;
	@SideOnly(Side.CLIENT) public IIcon iconSide;
	@SideOnly(Side.CLIENT) public IIcon iconBottom;
	@SideOnly(Side.CLIENT) public IIcon iconInnerTop;
	@SideOnly(Side.CLIENT) public IIcon iconInnerSide;
	
	protected long power;
	String name;

	public MachineCapacitor(Material mat, long power, String name) {
		super(mat);
		this.power = power;
		this.name = name;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":capacitor_" + this.name + "_top");
		this.iconSide = iconRegister.registerIcon(RefStrings.MODID + ":capacitor_" + this.name + "_side");
		this.iconBottom = iconRegister.registerIcon(RefStrings.MODID + ":capacitor_" + this.name + "_bottom");
		this.iconInnerTop = iconRegister.registerIcon(RefStrings.MODID + ":capacitor_" + this.name + "_inner_top");
		this.iconInnerSide = iconRegister.registerIcon(RefStrings.MODID + ":capacitor_" + this.name + "_inner_side");
	}

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override public int getRenderType() { return MachineCapacitor.renderID; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float fX, float fY, float fZ, int meta) {
		return side;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCapacitor(this.power);
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(!(te instanceof TileEntityCapacitor))
			return;
		
		TileEntityCapacitor battery = (TileEntityCapacitor) te;
		List<String> text = new ArrayList<>();
		text.add(BobMathUtil.getShortNumber(battery.getPower()) + " / " + BobMathUtil.getShortNumber(battery.getMaxPower()) + "HE");
		
		double percent = (double) battery.getPower() / (double) battery.getMaxPower();
		int charge = (int) Math.floor(percent * 10_000D);
		int color = ((int) (0xFF - 0xFF * percent)) << 16 | ((int)(0xFF * percent) << 8);
		text.add("&[" + color + "&]" + (charge / 100D) + "%");
		text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + "+" + BobMathUtil.getShortNumber(battery.powerReceived) + "HE/t");
		text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + "-" + BobMathUtil.getShortNumber(battery.powerSent) + "HE/t");
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}

	
	@Override
	public void addInformation(ItemStack stack, NBTTagCompound persistentTag, EntityPlayer player, List list, boolean ext) {
		list.add(EnumChatFormatting.YELLOW + "" + BobMathUtil.getShortNumber(persistentTag.getLong("power")) + "/" + BobMathUtil.getShortNumber(persistentTag.getLong("maxPower")) + "HE");
	}

	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			for(String s : I18nUtil.resolveKeyArray("tile.capacitor.desc")) list.add(EnumChatFormatting.YELLOW + s);
		} else {
			list.add(EnumChatFormatting.DARK_GRAY + "" + EnumChatFormatting.ITALIC +"Hold <" +
					EnumChatFormatting.YELLOW + "" + EnumChatFormatting.ITALIC + "LSHIFT" +
					EnumChatFormatting.DARK_GRAY + "" + EnumChatFormatting.ITALIC + "> to display more info");
		}
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return IPersistentNBT.getDrops(world, x, y, z, this);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		IPersistentNBT.restoreData(world, x, y, z, itemStack);
	}

	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
		
		if(!player.capabilities.isCreativeMode) {
			this.harvesters.set(player);
			this.dropBlockAsItem(world, x, y, z, meta, 0);
			this.harvesters.set(null);
		}
	}
	
	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
		player.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(this)], 1);
		player.addExhaustion(0.025F);
	}

	public static class TileEntityCapacitor extends TileEntityLoadedBase implements IEnergyUser, INBTPacketReceiver, IPersistentNBT {
		
		public long power;
		protected long maxPower;
		public long prevPower;
		public long powerReceived;
		public long powerSent;
		
		public TileEntityCapacitor() { }
		
		public TileEntityCapacitor(long maxPower) {
			this.maxPower = maxPower;
		}
		
		@Override
		public void updateEntity() {
			
			if(!this.worldObj.isRemote) {
				
				long gain = this.power - this.prevPower;

				ForgeDirection opp = ForgeDirection.getOrientation(getBlockMetadata());
				ForgeDirection dir = opp.getOpposite();
				
				BlockPos pos = new BlockPos(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);
				
				boolean didStep = false;
				ForgeDirection last = null;
				
				while(this.worldObj.getBlock(pos.getX(), pos.getY(), pos.getZ()) == ModBlocks.capacitor_bus) {
					ForgeDirection current = ForgeDirection.getOrientation(this.worldObj.getBlockMetadata(pos.getX(), pos.getY(), pos.getZ()));
					if(!didStep) last = current;
					didStep = true;
					
					if(last != current) {
						pos = null;
						break;
					}
					
					pos = pos.offset(current);
				}
				
				long preSend = this.power;
				if(pos != null && last != null) {
					tryUnsubscribe(this.worldObj, pos.getX(), pos.getY(), pos.getZ());
					sendPower(this.worldObj, pos.getX(), pos.getY(), pos.getZ(), last);
				}
				long sent = preSend - this.power;
				
				trySubscribe(this.worldObj, this.xCoord + opp.offsetX, this.yCoord+ opp.offsetY, this.zCoord + opp.offsetZ, opp);
				
				NBTTagCompound data = new NBTTagCompound();
				data.setLong("power", this.power);
				data.setLong("maxPower", this.maxPower);
				data.setLong("rec", gain);
				data.setLong("sent", sent);
				INBTPacketReceiver.networkPack(this, data, 15);
				
				this.prevPower = this.power;
			}
		}

		@Override
		public void networkUnpack(NBTTagCompound nbt) { 
			this.power = nbt.getLong("power");
			this.maxPower = nbt.getLong("maxPower");
			this.powerReceived = nbt.getLong("rec");
			this.powerSent = nbt.getLong("sent");
		}

		@Override
		public long getPower() {
			return this.power;
		}

		@Override
		public long getMaxPower() {
			return this.maxPower;
		}

		@Override
		public ConnectionPriority getPriority() {
			return ConnectionPriority.LOW;
		}

		@Override
		public void setPower(long power) {
			this.power = power;
		}
		
		@Override
		public boolean canConnect(ForgeDirection dir) {
			return dir == ForgeDirection.getOrientation(getBlockMetadata());
		}

		@Override
		public void writeNBT(NBTTagCompound nbt) {
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setLong("maxPower", this.maxPower);
			nbt.setTag(IPersistentNBT.NBT_PERSISTENT_KEY, data);
		}

		@Override
		public void readNBT(NBTTagCompound nbt) {
			NBTTagCompound data = nbt.getCompoundTag(IPersistentNBT.NBT_PERSISTENT_KEY);
			this.power = data.getLong("power");
			this.maxPower = data.getLong("maxPower");
		}
		
		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			this.power = nbt.getLong("power");
			this.maxPower = nbt.getLong("maxPower");
		}
		
		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setLong("power", this.power);
			nbt.setLong("maxPower", this.maxPower);
		}
	}
}
