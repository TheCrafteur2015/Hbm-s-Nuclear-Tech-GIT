package com.hbm.tileentity.machine;

import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerMachineEPress;
import com.hbm.inventory.gui.GUIMachineEPress;
import com.hbm.inventory.recipes.PressRecipes;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.items.machine.ItemStamp;
import com.hbm.lib.Library;
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
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineEPress extends TileEntityMachineBase implements IEnergyUser, IGUIProvider {

	public long power = 0;
	public final static long maxPower = 50000;

	public int press;
	public double renderPress;
	public double lastPress;
	private int syncPress;
	private int turnProgress;
	public final static int maxPress = 200;
	boolean isRetracting = false;
	private int delay;
	
	public ItemStack syncStack;
	
	public TileEntityMachineEPress() {
		super(5);
	}

	@Override
	public String getName() {
		return "container.epress";
	}
	
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			updateConnections();
			this.power = Library.chargeTEFromItems(this.slots, 0, this.power, TileEntityMachineEPress.maxPower);
			
			boolean canProcess = canProcess();
			
			if((canProcess || this.isRetracting || this.delay > 0) && this.power >= 100) {
				
				this.power -= 100;
				
				if(this.delay <= 0) {
					
					UpgradeManager.eval(this.slots, 4, 4);
					int speed = 1 + Math.min(3, UpgradeManager.getLevel(UpgradeType.SPEED));
					
					int stampSpeed = this.isRetracting ? 20 : 45;
					stampSpeed *= (1D + (double) speed / 4D);
					
					if(this.isRetracting) {
						this.press -= stampSpeed;
						
						if(this.press <= 0) {
							this.isRetracting = false;
							this.delay = 5 - speed + 1;
						}
					} else if(canProcess) {
						this.press += stampSpeed;
						
						if(this.press >= TileEntityMachineEPress.maxPress) {
							this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.pressOperate", 1.5F, 1.0F);
							ItemStack output = PressRecipes.getOutput(this.slots[2], this.slots[1]);
							if(this.slots[3] == null) {
								this.slots[3] = output.copy();
							} else {
								this.slots[3].stackSize += output.stackSize;
							}
							decrStackSize(2, 1);
							
							if(this.slots[1].getMaxDamage() != 0) {
								this.slots[1].setItemDamage(this.slots[1].getItemDamage() + 1);
								if(this.slots[1].getItemDamage() >= this.slots[1].getMaxDamage()) {
									this.slots[1] = null;
								}
							}
							
							this.isRetracting = true;
							this.delay = 5 - speed + 1;
							
							markDirty();
						}
					} else if(this.press > 0){
						this.isRetracting = true;
					}
				} else {
					this.delay--;
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setInteger("press", this.press);
			if(this.slots[2] != null) {
				NBTTagCompound stack = new NBTTagCompound();
				this.slots[2].writeToNBT(stack);
				data.setTag("stack", stack);
			}
			
			networkPack(data, 50);
			
		} else {
			
			// approach-based interpolation, GO!
			this.lastPress = this.renderPress;
			
			if(this.turnProgress > 0) {
				this.renderPress = this.renderPress + ((this.syncPress - this.renderPress) / (double) this.turnProgress);
				--this.turnProgress;
			} else {
				this.renderPress = this.syncPress;
			}
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.syncPress = nbt.getInteger("press");
		
		if(nbt.hasKey("stack")) {
			NBTTagCompound stack = nbt.getCompoundTag("stack");
			this.syncStack = ItemStack.loadItemStackFromNBT(stack);
		} else {
			this.syncStack = null;
		}
		
		this.turnProgress = 2;
	}
	
	public boolean canProcess() {
		if((this.power < 100) || this.slots[1] == null || this.slots[2] == null) return false;
		
		ItemStack output = PressRecipes.getOutput(this.slots[2], this.slots[1]);
		
		if(output == null) return false;
		
		if(this.slots[3] == null) return true;
		if(this.slots[3].stackSize + output.stackSize <= this.slots[3].getMaxStackSize() && this.slots[3].getItem() == output.getItem() && this.slots[3].getItemDamage() == output.getItemDamage()) return true;
		return false;
	}
	
	private void updateConnections() {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			trySubscribe(this.worldObj, this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ, dir);
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		
		if(stack.getItem() instanceof ItemStamp)
			return i == 1;
		
		return i == 2;
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 1, 2, 3 };
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 3;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.press = nbt.getInteger("press");
		this.power = nbt.getInteger("power");
		this.isRetracting = nbt.getBoolean("ret");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("press", this.press);
		nbt.setLong("power", this.power);
		nbt.setBoolean("ret", this.isRetracting);
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
		return TileEntityMachineEPress.maxPower;
	}
	
	AxisAlignedBB aabb;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.aabb != null)
			return this.aabb;
		
		this.aabb = AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 3, this.zCoord + 1);
		return this.aabb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineEPress(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineEPress(player.inventory, this);
	}
}
