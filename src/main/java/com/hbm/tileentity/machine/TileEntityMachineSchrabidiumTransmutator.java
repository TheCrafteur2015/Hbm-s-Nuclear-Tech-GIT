package com.hbm.tileentity.machine;

import com.hbm.config.VersatileConfig;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.container.ContainerMachineSchrabidiumTransmutator;
import com.hbm.inventory.gui.GUIMachineSchrabidiumTransmutator;
import com.hbm.inventory.recipes.MachineRecipes;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyUser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineSchrabidiumTransmutator extends TileEntityMachineBase implements IEnergyUser, IGUIProvider {

	public long power = 0;
	public int process = 0;
	public static final long maxPower = 5000000;
	public static final int processSpeed = 600;
	
	private AudioWrapper audio;

	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 1, 2 };
	private static final int[] slots_side = new int[] { 3, 2 };

	public TileEntityMachineSchrabidiumTransmutator() {
		super(4);
	}

	@Override
	public String getName() {
		return "container.machine_schrabidium_transmutator";
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		switch (i) {
		case 0:
			if (MachineRecipes.mODE(stack, OreDictManager.U.ingot()))
				return true;
			break;
		case 2:
			if (stack.getItem() == ModItems.redcoil_capacitor || stack.getItem() == ModItems.euphemium_capacitor)
				return true;
			break;
		case 3:
			if (stack.getItem() instanceof IBatteryItem)
				return true;
			break;
		}
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.power = nbt.getLong("power");
		this.process = nbt.getInteger("process");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", this.power);
		nbt.setInteger("process", this.process);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return p_94128_1_ == 0 ? TileEntityMachineSchrabidiumTransmutator.slots_bottom : (p_94128_1_ == 1 ? TileEntityMachineSchrabidiumTransmutator.slots_top : TileEntityMachineSchrabidiumTransmutator.slots_side);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		
		if (i == 2 && stack.getItem() != null && (stack.getItem() == ModItems.redcoil_capacitor && stack.getItemDamage() == stack.getMaxDamage()) || stack.getItem() == ModItems.euphemium_capacitor) {
			return true;
		}

		if (i == 1) {
			return true;
		}

		if (i == 3) {
			if (stack.getItem() instanceof IBatteryItem && ((IBatteryItem)stack.getItem()).getCharge(stack) == 0)
				return true;
		}

		return false;
	}

	public long getPowerScaled(long i) {
		return (this.power * i) / TileEntityMachineSchrabidiumTransmutator.maxPower;
	}

	public int getProgressScaled(int i) {
		return (this.process * i) / TileEntityMachineSchrabidiumTransmutator.processSpeed;
	}

	public boolean canProcess() {
		if (this.power >= 4990000 && this.slots[0] != null && MachineRecipes.mODE(this.slots[0], OreDictManager.U.ingot()) && this.slots[2] != null
				&& (this.slots[2].getItem() == ModItems.redcoil_capacitor && this.slots[2].getItemDamage() < this.slots[2].getMaxDamage() || this.slots[2].getItem() == ModItems.euphemium_capacitor)
				&& (this.slots[1] == null || (this.slots[1] != null && this.slots[1].getItem() == VersatileConfig.getTransmutatorItem()
						&& this.slots[1].stackSize < this.slots[1].getMaxStackSize()))) {
			return true;
		}
		return false;
	}

	public boolean isProcessing() {
		return this.process > 0;
	}

	public void process() {
		this.process++;

		if (this.process >= TileEntityMachineSchrabidiumTransmutator.processSpeed) {

			this.power = 0;
			this.process = 0;

			this.slots[0].stackSize--;
			if (this.slots[0].stackSize <= 0) {
				this.slots[0] = null;
			}

			if (this.slots[1] == null) {
				this.slots[1] = new ItemStack(VersatileConfig.getTransmutatorItem());
			} else {
				this.slots[1].stackSize++;
			}
			if (this.slots[2] != null && this.slots[2].getItem() == ModItems.redcoil_capacitor) {
				this.slots[2].setItemDamage(this.slots[2].getItemDamage() + 1);
			}

			this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "ambient.weather.thunder", 10000.0F,
					0.8F + this.worldObj.rand.nextFloat() * 0.2F);
		}
	}

	@Override
	public void updateEntity() {

		if (!this.worldObj.isRemote) {
			
			updateConnections();
			
			this.power = Library.chargeTEFromItems(this.slots, 3, this.power, TileEntityMachineSchrabidiumTransmutator.maxPower);

			if(canProcess()) {
				process();
			} else {
				this.process = 0;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setInteger("progress", this.process);
			networkPack(data, 50);
			
		} else {

			if(this.process > 0) {
				
				if(this.audio == null) {
					this.audio = createAudioLoop();
					this.audio.startSound();
				} else if(!this.audio.isPlaying()) {
					this.audio = rebootAudio(this.audio);
				}
			} else {
				
				if(this.audio != null) {
					this.audio.stopSound();
					this.audio = null;
				}
			}
		}
	}
	
	@Override
	public AudioWrapper createAudioLoop() {
		return MainRegistry.proxy.getLoopedSound("hbm:weapon.tauChargeLoop", this.xCoord, this.yCoord, this.zCoord, 1.0F, 10F, 1.0F);
	}
	
	private void updateConnections() {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			trySubscribe(this.worldObj, this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ, dir);
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
	
	@Override
	public void networkUnpack(NBTTagCompound data) {

		this.power = data.getLong("power");
		this.process = data.getInteger("progress");
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
		return TileEntityMachineSchrabidiumTransmutator.maxPower;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineSchrabidiumTransmutator(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineSchrabidiumTransmutator(player.inventory, this);
	}
}
