package com.hbm.tileentity.machine;

import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerCentrifuge;
import com.hbm.inventory.gui.GUIMachineCentrifuge;
import com.hbm.inventory.recipes.CentrifugeRecipes;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IEnergyUser;
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

public class TileEntityMachineCentrifuge extends TileEntityMachineBase implements IEnergyUser, IGUIProvider {
	
	public int progress;
	public long power;
	public boolean isProgressing;
	public static final int maxPower = 100000;
	public static final int processingSpeed = 200;
	private int audioDuration = 0;
	
	private AudioWrapper audio;

	/*
	 * So why do we do this now? You have a funny mekanism/thermal/whatever pipe and you want to output stuff from a side
	 * that isn't the bottom, what do? Answer: make all slots accessible from all sides and regulate in/output in the 
	 * dedicated methods. Duh.
	 */
	private static final int[] slot_io = new int[] { 0, 2, 3, 4, 5 };

	public TileEntityMachineCentrifuge() {
		super(8);
	}
	
	@Override
	public String getName() {
		return "container.centrifuge";
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return i == 0;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return TileEntityMachineCentrifuge.slot_io;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.power = nbt.getLong("power");
		this.progress = nbt.getShort("progress");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", this.power);
		nbt.setShort("progress", (short) this.progress);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i > 1;
	}

	public int getCentrifugeProgressScaled(int i) {
		return (this.progress * i) / TileEntityMachineCentrifuge.processingSpeed;
	}

	public long getPowerRemainingScaled(int i) {
		return (this.power * i) / TileEntityMachineCentrifuge.maxPower;
	}

	public boolean canProcess() {

		if(this.slots[0] == null) {
			return false;
		}
		ItemStack[] out = CentrifugeRecipes.getOutput(this.slots[0]);
		
		if(out == null) {
			return false;
		}

		for(int i = 0; i < Math.min(4, out.length); i++) {

			//either the slot is null, the output is null or the output can be added to the existing slot
			if((this.slots[i + 2] == null) || (out[i] == null) || (this.slots[i + 2].isItemEqual(out[i]) && this.slots[i + 2].stackSize + out[i].stackSize <= out[i].getMaxStackSize()))
				continue;

			return false;
		}

		return true;
	}

	private void processItem() {
		ItemStack[] out = CentrifugeRecipes.getOutput(this.slots[0]);

		for(int i = 0; i < Math.min(4, out.length); i++) {

			if(out[i] == null)
				continue;

			if(this.slots[i + 2] == null) {
				this.slots[i + 2] = out[i].copy();
			} else {
				this.slots[i + 2].stackSize += out[i].stackSize;
			}
		}

		decrStackSize(0, 1);
		markDirty();
	}

	public boolean hasPower() {
		return this.power > 0;
	}

	public boolean isProcessing() {
		return this.progress > 0;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void updateEntity() {

		if(!this.worldObj.isRemote) {
			
			this.updateStandardConnections(this.worldObj, this.xCoord, this.yCoord, this.zCoord);

			this.power = Library.chargeTEFromItems(this.slots, 1, this.power, TileEntityMachineCentrifuge.maxPower);
			
			int consumption = 200;
			int speed = 1;
			
			UpgradeManager.eval(this.slots, 6, 7);
			speed += Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3);
			consumption += Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3) * 200;
			
			speed *= (1 + Math.min(UpgradeManager.getLevel(UpgradeType.OVERDRIVE), 3) * 5);
			consumption += Math.min(UpgradeManager.getLevel(UpgradeType.OVERDRIVE), 3) * 10000;
			
			consumption /= (1 + Math.min(UpgradeManager.getLevel(UpgradeType.POWER), 3));

			if(hasPower() && isProcessing()) {
				this.power -= consumption;

				if(this.power < 0) {
					this.power = 0;
				}
			}

			if(hasPower() && canProcess()) {
				this.isProgressing = true;
			} else {
				this.isProgressing = false;
			}

			if(this.isProgressing) {
				this.progress += speed;

				if(this.progress >= TileEntityMachineCentrifuge.processingSpeed) {
					this.progress = 0;
					processItem();
				}
			} else {
				this.progress = 0;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setInteger("progress", this.progress);
			data.setBoolean("isProgressing", this.isProgressing);
			networkPack(data, 50);
		} else {
			
			if(this.isProgressing) {
				this.audioDuration += 2;
			} else {
				this.audioDuration -= 3;
			}
			
			this.audioDuration = MathHelper.clamp_int(this.audioDuration, 0, 60);
			
			if(this.audioDuration > 10) {
				
				if(this.audio == null) {
					this.audio = createAudioLoop();
					this.audio.startSound();
				} else if(!this.audio.isPlaying()) {
					this.audio = rebootAudio(this.audio);
				}
				
				this.audio.updatePitch((this.audioDuration - 10) / 100F + 0.5F);
				
			} else {
				
				if(this.audio != null) {
					this.audio.stopSound();
					this.audio = null;
				}
			}
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.power = data.getLong("power");
		this.progress = data.getInteger("progress");
		this.isProgressing = data.getBoolean("isProgressing");
	}

	@Override
	public AudioWrapper createAudioLoop() {
		return MainRegistry.proxy.getLoopedSound("hbm:block.centrifugeOperate", this.xCoord, this.yCoord, this.zCoord, 1.0F, 10F, 1.0F);
	}

	@Override
	public void onChunkUnload() {

		if(this.audio != null) {
			this.audio.stopSound();
			this.audio = null;
		}
	}

	@Override
	public void invalidate() {

		super.invalidate();

		if(this.audio != null) {
			this.audio.stopSound();
			this.audio = null;
		}
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(
					this.xCoord,
					this.yCoord,
					this.zCoord,
					this.xCoord + 1,
					this.yCoord + 4,
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

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getPower() {
		return this.power;

	}

	@Override
	public long getMaxPower() {
		return TileEntityMachineCentrifuge.maxPower;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCentrifuge(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineCentrifuge(player.inventory, this);
	}
}