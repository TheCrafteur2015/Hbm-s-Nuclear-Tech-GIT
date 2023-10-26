package com.hbm.tileentity.network;

import com.hbm.entity.item.EntityMovingPackage;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerCraneBoxer;
import com.hbm.inventory.gui.GUICraneBoxer;
import com.hbm.tileentity.IGUIProvider;

import api.hbm.conveyor.IConveyorBelt;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCraneBoxer extends TileEntityCraneBase implements IGUIProvider, IControlReceiver {
	
	public byte mode = 0;
	public static final byte MODE_4 = 0;
	public static final byte MODE_8 = 1;
	public static final byte MODE_16 = 2;
	public static final byte MODE_REDSTONE = 3;
	
	private boolean lastRedstone = false;
	

	public TileEntityCraneBoxer() {
		super(7 * 3);
	}

	@Override
	public String getName() {
		return "container.craneBoxer";
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if(!this.worldObj.isRemote) {
			
			boolean redstone = this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord);
			
			if(this.mode == TileEntityCraneBoxer.MODE_REDSTONE && redstone && !this.lastRedstone) {
				
				ForgeDirection outputSide = getOutputSide();
				Block b = this.worldObj.getBlock(this.xCoord + outputSide.offsetX, this.yCoord + outputSide.offsetY, this.zCoord + outputSide.offsetZ);
				IConveyorBelt belt = null;
				
				if(b instanceof IConveyorBelt) {
					belt = (IConveyorBelt) b;
				}
				
				int pack = 0;

				for (ItemStack slot : this.slots) {
					if(slot != null) {
						pack++;
					}
				}
				
				if(belt != null && pack > 0) {
					
					ItemStack[] box = new ItemStack[pack];
					
					for(int i = 0; i < this.slots.length && pack > 0; i++) {
						
						if(this.slots[i] != null) {
							pack--;
							box[pack] = this.slots[i].copy();
							this.slots[i] = null;
						}
					}
					
					EntityMovingPackage moving = new EntityMovingPackage(this.worldObj);
					Vec3 pos = Vec3.createVectorHelper(this.xCoord + 0.5 + outputSide.offsetX * 0.55, this.yCoord + 0.5 + outputSide.offsetY * 0.55, this.zCoord + 0.5 + outputSide.offsetZ * 0.55);
					Vec3 snap = belt.getClosestSnappingPosition(this.worldObj, this.xCoord + outputSide.offsetX, this.yCoord + outputSide.offsetY, this.zCoord + outputSide.offsetZ, pos);
					moving.setPosition(snap.xCoord, snap.yCoord, snap.zCoord);
					moving.setItemStacks(box);
					this.worldObj.spawnEntityInWorld(moving);
				}
			}
					
			this.lastRedstone = redstone;
			
			if(this.mode != TileEntityCraneBoxer.MODE_REDSTONE && this.worldObj.getTotalWorldTime() % 2 == 0) {
				int pack = 1;
				
				switch(this.mode) {
				case MODE_4: pack = 4; break;
				case MODE_8: pack = 8; break;
				case MODE_16: pack = 16; break;
				}
				
				int fullStacks = 0;
				
				// NO!
				/*StorageManifest manifest = new StorageManifest(); //i wrote some of this for a feature that i scrapped so why not make proper use of it?
				
				for(int i = 0; i < slots.length; i++) {
					if(slots[i] != null) {
						manifest.writeStack(slots[i]);
					}
				}*/
				
				for (ItemStack slot : this.slots) {
					
					if(slot != null && slot.stackSize == slot.getMaxStackSize()) {
						fullStacks++;
					}
				}
				
				ForgeDirection outputSide = getOutputSide();
				Block b = this.worldObj.getBlock(this.xCoord + outputSide.offsetX, this.yCoord + outputSide.offsetY, this.zCoord + outputSide.offsetZ);
				IConveyorBelt belt = null;
				
				if(b instanceof IConveyorBelt) {
					belt = (IConveyorBelt) b;
				}
				
				if(belt != null && fullStacks >= pack) {
					
					ItemStack[] box = new ItemStack[pack];
					
					for(int i = 0; i < this.slots.length && pack > 0; i++) {
						
						if(this.slots[i] != null && this.slots[i].stackSize == this.slots[i].getMaxStackSize()) {
							pack--;
							box[pack] = this.slots[i].copy();
							this.slots[i] = null;
						}
					}
					
					EntityMovingPackage moving = new EntityMovingPackage(this.worldObj);
					Vec3 pos = Vec3.createVectorHelper(this.xCoord + 0.5 + outputSide.offsetX * 0.55, this.yCoord + 0.5 + outputSide.offsetY * 0.55, this.zCoord + 0.5 + outputSide.offsetZ * 0.55);
					Vec3 snap = belt.getClosestSnappingPosition(this.worldObj, this.xCoord + outputSide.offsetX, this.yCoord + outputSide.offsetY, this.zCoord + outputSide.offsetZ, pos);
					moving.setPosition(snap.xCoord, snap.yCoord, snap.zCoord);
					moving.setItemStacks(box);
					this.worldObj.spawnEntityInWorld(moving);
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setByte("mode", this.mode);
			networkPack(data, 15);
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.mode = nbt.getByte("mode");
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return true;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.mode = nbt.getByte("mode");
		this.lastRedstone = nbt.getBoolean("lastRedstone");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setByte("mode", this.mode);
		nbt.setBoolean("lastRedstone", this.lastRedstone);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCraneBoxer(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICraneBoxer(player.inventory, this);
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(this.xCoord - player.posX, this.yCoord - player.posY, this.zCoord - player.posZ).lengthVector() < 20;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("toggle")) {
			this.mode = (byte) ((this.mode + 1) % 4);
		}
	}
}
