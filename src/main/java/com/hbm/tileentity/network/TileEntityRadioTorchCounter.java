package com.hbm.tileentity.network;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.module.ModulePatternMatcher;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.Compat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRadioTorchCounter extends TileEntityMachineBase implements IControlReceiver {
	
	public String[] channel;
	public int[] lastCount;
	public boolean polling = false;
	public ModulePatternMatcher matcher;

	public TileEntityRadioTorchCounter() {
		super(3);
		this.channel = new String[3];
		for(int i = 0; i < 3; i++) this.channel[i] = "";
		this.lastCount = new int[3];
		this.matcher = new ModulePatternMatcher(3);
	}

	@Override
	public String getName() {
		return "container.rttyCounter";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata()).getOpposite();
			
			TileEntity tile = Compat.getTileStandard(this.worldObj, this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);
			if(tile instanceof IInventory) {
				IInventory inv = (IInventory) tile;
				ItemStack[] invSlots = new ItemStack[inv.getSizeInventory()];
				for(int i = 0; i < invSlots.length; i++) invSlots[i] = inv.getStackInSlot(i);
				
				for(int i = 0; i < 3; i++) {
					if(this.channel[i].isEmpty() || (this.slots[i] == null)) continue;
					ItemStack pattern = this.slots[i];
					
					int count = 0;

					for (ItemStack invSlot : invSlots) {
						if(invSlot != null && this.matcher.isValidForFilter(pattern, i, invSlot)) {
							count += invSlot.stackSize;
						}
					}
					
					if(this.polling || this.lastCount[i] != count) {
						RTTYSystem.broadcast(this.worldObj, this.channel[i], count);
					}
					
					this.lastCount[i] = count;
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("polling", this.polling);
			data.setIntArray("last", this.lastCount);
			this.matcher.writeToNBT(data);
			for(int i = 0; i < 3; i++) if(this.channel[i] != null) data.setString("c" + i, this.channel[i]);
			networkPack(data, 15);
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.polling = nbt.getBoolean("polling");
		this.lastCount = nbt.getIntArray("last");
		this.matcher.modes = new String[this.matcher.modes.length];
		this.matcher.readFromNBT(nbt);
		for(int i = 0; i < 3; i++) this.channel[i] = nbt.getString("c" + i);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.polling = nbt.getBoolean("p");
		for(int i = 0; i < 3; i++) {
			this.channel[i] = nbt.getString("c" + i);
			this.lastCount[i] = nbt.getInteger("l" + i);
		}
		this.matcher.readFromNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("p", this.polling);
		for(int i = 0; i < 3; i++) {
			if(this.channel[i] != null) nbt.setString("c" + i, this.channel[i]);
			nbt.setInteger("l" + i, this.lastCount[i]);
		}
		this.matcher.writeToNBT(nbt);
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("polling")) {
			this.polling = !this.polling;
			markChanged();
		} else {
			System.out.println("guh");
			for(int i = 0; i < 3; i++) {
				this.channel[i] = data.getString("c" + i);
			}
			markChanged();
		}
	}
}
