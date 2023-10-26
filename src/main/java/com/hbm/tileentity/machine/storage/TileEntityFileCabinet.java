package com.hbm.tileentity.machine.storage;

import com.hbm.inventory.container.ContainerFileCabinet;
import com.hbm.inventory.gui.GUIFileCabinet;
import com.hbm.packet.NBTPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.INBTPacketReceiver;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TileEntityFileCabinet extends TileEntityCrateBase implements IGUIProvider, INBTPacketReceiver {
	
	private int timer = 0;
	private int playersUsing = 0;
	//meh, it's literally just two extra variables
	public float lowerExtent = 0; //i don't know a term for how 'open' something is
	public float prevLowerExtent = 0;
	public float upperExtent = 0;
	public float prevUpperExtent = 0;
	
	public TileEntityFileCabinet() {
		super(8);
	}
	
	@Override
	public String getInventoryName() {
		return "container.fileCabinet";
	}
	
	@Override
	public void openInventory() {
		if(!this.worldObj.isRemote) this.playersUsing++;
	}

	@Override
	public void closeInventory() {
		if(!this.worldObj.isRemote) this.playersUsing--;
	}
	
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			if(this.playersUsing > 0) {
				if(this.timer < 10) {
					this.timer++;
				}
			} else
				this.timer = 0;
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("timer", this.timer);
			data.setInteger("playersUsing", this.playersUsing);
			PacketDispatcher.wrapper.sendToAllAround(new NBTPacket(data, this.xCoord, this.yCoord, this.zCoord), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 25));
		} else {
			this.prevLowerExtent = this.lowerExtent;
			this.prevUpperExtent = this.upperExtent;
		}
		
		float openSpeed = this.playersUsing > 0 ? 1F / 16F : 1F / 25F;
		float maxExtent = 0.8F;
		
		if(this.playersUsing > 0) {
			if(this.lowerExtent == 0F && this.upperExtent == 0F)
				this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, "hbm:block.crateOpen", 0.8F, 1.0F);
			else {
				if(this.upperExtent + openSpeed >= maxExtent && this.lowerExtent < maxExtent)
					this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, "hbm:block.crateOpen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.7F);
				
				if(this.lowerExtent + openSpeed >= maxExtent && this.lowerExtent < maxExtent)
					this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, "hbm:block.crateOpen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.7F);
			}
			
			this.lowerExtent += openSpeed;
			
			if(this.timer >= 10)
				this.upperExtent += openSpeed;
			
		} else if(this.lowerExtent > 0) {
			if(this.upperExtent - openSpeed < maxExtent / 2 && this.upperExtent >= maxExtent / 2 && this.upperExtent != this.lowerExtent)
				this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, "hbm:block.crateClose", 0.8F, 1.0F);
			
			if(this.lowerExtent - openSpeed < maxExtent / 2 && this.lowerExtent >= maxExtent / 2)
				this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, "hbm:block.crateClose", 0.8F, 1.0F);
			
			this.upperExtent -= openSpeed;
			this.lowerExtent -= openSpeed;
		}
		
		this.lowerExtent = MathHelper.clamp_float(this.lowerExtent, 0F, maxExtent);
		this.upperExtent = MathHelper.clamp_float(this.upperExtent, 0F, maxExtent);
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.timer = nbt.getInteger("timer");
		this.playersUsing = nbt.getInteger("playersUsing");
	}
	
	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerFileCabinet(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIFileCabinet(player.inventory, this);
	}

	//No automation, it's a filing cabinet.
	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return false;
	}
	
	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return false;
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(
					this.xCoord - 1,
					this.yCoord,
					this.zCoord - 1,
					this.xCoord + 1,
					this.yCoord + 1,
					this.zCoord + 1
					);
		}
		
		return this.bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
