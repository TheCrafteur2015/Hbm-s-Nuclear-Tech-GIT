package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.container.ContainerMachinePress;
import com.hbm.inventory.gui.GUIMachinePress;
import com.hbm.inventory.recipes.PressRecipes;
import com.hbm.items.machine.ItemStamp;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachinePress extends TileEntityMachineBase implements IGUIProvider {

	public int speed = 0; // speed ticks up once (or four times if preheated) when operating
	public static final int maxSpeed = 400; // max speed ticks for acceleration
	public static final int progressAtMax = 25; // max progress speed when hot
	public int burnTime = 0; // burn ticks of the loaded fuel, 200 ticks equal one operation

	public int press; // extension of the press, operation is completed if maxPress is reached
	public double renderPress; // client-side version of the press var, a double for smoother rendering
	public double lastPress; // for interp
	private int syncPress; // for interp
	private int turnProgress; // for interp 3: revenge of the sith
	public final static int maxPress = 200; // max tick count per operation assuming speed is 1
	boolean isRetracting = false; // direction the press is currently going
	private int delay; // delay between direction changes to look a bit more appealing
	
	public ItemStack syncStack;
	
	public TileEntityMachinePress() {
		super(4);
	}

	@Override
	public String getName() {
		return "container.press";
	}
	
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			boolean preheated = false;
			
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				if(this.worldObj.getBlock(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ) == ModBlocks.press_preheater) {
					preheated = true;
					break;
				}
			}
			
			boolean canProcess = canProcess();
			
			if((canProcess || this.isRetracting) && this.burnTime >= 200) {
				this.speed += preheated ? 4 : 1;
				
				if(this.speed > TileEntityMachinePress.maxSpeed) {
					this.speed = TileEntityMachinePress.maxSpeed;
				}
			} else {
				this.speed -= 1;
				if(this.speed < 0) {
					this.speed = 0;
				}
			}
			
			if(this.delay <= 0) {
				
				int stampSpeed = this.speed * TileEntityMachinePress.progressAtMax / TileEntityMachinePress.maxSpeed;
				
				if(this.isRetracting) {
					this.press -= stampSpeed;
					
					if(this.press <= 0) {
						this.isRetracting = false;
						this.delay = 5;
					}
				} else if(canProcess) {
					this.press += stampSpeed;
					
					if(this.press >= TileEntityMachinePress.maxPress) {
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
						this.delay = 5;
						if(this.burnTime >= 200) {
							this.burnTime -= 200; // only subtract fuel if operation was actually successful
						}
						
						markDirty();
					}
				} else if(this.press > 0){
					this.isRetracting = true;
				}
			} else {
				this.delay--;
			}
			
			if(this.slots[0] != null && this.burnTime < 200 && TileEntityFurnace.getItemBurnTime(this.slots[0]) > 0) { // less than one operation stored? burn more fuel!
				this.burnTime += TileEntityFurnace.getItemBurnTime(this.slots[0]);
				
				if(this.slots[0].stackSize == 1 && this.slots[0].getItem().hasContainerItem(this.slots[0])) {
					this.slots[0] = this.slots[0].getItem().getContainerItem(this.slots[0]).copy();
				} else {
					decrStackSize(0, 1);
				}
				markChanged();
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("speed", this.speed);
			data.setInteger("burnTime", this.burnTime);
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
		this.speed = nbt.getInteger("speed");
		this.burnTime = nbt.getInteger("burnTime");
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
		if((this.burnTime < 200) || this.slots[1] == null || this.slots[2] == null) return false;
		
		ItemStack output = PressRecipes.getOutput(this.slots[2], this.slots[1]);
		
		if(output == null) return false;
		
		if(this.slots[3] == null) return true;
		if(this.slots[3].stackSize + output.stackSize <= this.slots[3].getMaxStackSize() && this.slots[3].getItem() == output.getItem() && this.slots[3].getItemDamage() == output.getItemDamage()) return true;
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		
		if(stack.getItem() instanceof ItemStamp)
			return i == 1;
		
		if(TileEntityFurnace.getItemBurnTime(stack) > 0 && i == 0)
			return true;
		
		return i == 2;
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0, 1, 2, 3 };
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
		this.burnTime = nbt.getInteger("burnTime");
		this.speed = nbt.getInteger("speed");
		this.isRetracting = nbt.getBoolean("ret");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("press", this.press);
		nbt.setInteger("burnTime", this.burnTime);
		nbt.setInteger("speed", this.speed);
		nbt.setBoolean("ret", this.isRetracting);
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
		return new ContainerMachinePress(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachinePress(player.inventory, this);
	}
}
