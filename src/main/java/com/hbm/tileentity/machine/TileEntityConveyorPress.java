package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.entity.item.EntityMovingItem;
import com.hbm.inventory.recipes.PressRecipes;
import com.hbm.items.machine.ItemStamp;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyUser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityConveyorPress extends TileEntityMachineBase implements IEnergyUser {

	public int usage = 100;
	public long power = 0;
	public final static long maxPower = 50000;

	public double speed = 0.125;
	public double press;
	public double renderPress;
	public double lastPress;
	private double syncPress;
	private int turnProgress;
	protected boolean isRetracting = false;
	private int delay;
	
	public ItemStack syncStack;

	public TileEntityConveyorPress() {
		super(1);
	}

	@Override
	public String getName() {
		return "";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			updateConnections();
			
			if(this.delay <= 0) {
				
				if(this.isRetracting) {
					
					if(canRetract()) {
						this.press -= this.speed;
						this.power -= this.usage;
						
						if(this.press <= 0) {
							this.press = 0;
							this.isRetracting = false;
							this.delay = 0;
						}
					}
					
				} else {
					
					if(canExtend()) {
						this.press += this.speed;
						this.power -= this.usage;
						
						if(this.press >= 1) {
							this.press = 1;
							this.isRetracting = true;
							this.delay = 5;
							process();
						}
					}
				}
				
			} else {
				this.delay--;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setDouble("press", this.press);
			if(this.slots[0] != null) {
				NBTTagCompound stack = new NBTTagCompound();
				this.slots[0].writeToNBT(stack);
				data.setTag("stack", stack);
			}
			
			networkPack(data, 50);
		} else {
			
			// approach-based interpolation, GO!
			this.lastPress = this.renderPress;
			
			if(this.turnProgress > 0) {
				this.renderPress = this.renderPress + ((this.syncPress - this.renderPress) / this.turnProgress);
				--this.turnProgress;
			} else {
				this.renderPress = this.syncPress;
			}
		}
	}
	
	protected void updateConnections() {
		for(DirPos pos : getConPos()) trySubscribe(this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
	}
	
	protected DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(this.xCoord + 1, this.yCoord, this.zCoord, Library.POS_X),
				new DirPos(this.xCoord - 1, this.yCoord, this.zCoord, Library.NEG_X),
				new DirPos(this.xCoord, this.yCoord, this.zCoord + 1, Library.POS_Z),
				new DirPos(this.xCoord, this.yCoord, this.zCoord - 1, Library.NEG_Z),
		};
	}
	
	
	public boolean canExtend() {
		
		if((this.power < this.usage) || (this.slots[0] == null)) return false;
		
		List<EntityMovingItem> items = this.worldObj.getEntitiesWithinAABB(EntityMovingItem.class, AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord + 1, this.zCoord, this.xCoord + 1, this.yCoord + 1.5, this.zCoord + 1));
		if(items.isEmpty()) return false;
		
		for(EntityMovingItem item : items) {
			ItemStack stack = item.getItemStack();
			if(PressRecipes.getOutput(stack, this.slots[0]) != null && stack.stackSize == 1) {
				
				double d0 = 0.35;
				double d1 = 0.65;
				if(item.posX > this.xCoord + d0 && item.posX < this.xCoord + d1 && item.posZ > this.zCoord + d0 && item.posZ < this.zCoord + d1) {
					item.setPosition(this.xCoord + 0.5, item.posY, this.zCoord + 0.5);
				}
				
				return true;
			}
		}
		
		return false;
	}
	
	
	public void process() {
		
		List<EntityMovingItem> items = this.worldObj.getEntitiesWithinAABB(EntityMovingItem.class, AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord + 1, this.zCoord, this.xCoord + 1, this.yCoord + 1.5, this.zCoord + 1));
		
		for(EntityMovingItem item : items) {
			ItemStack stack = item.getItemStack();
			ItemStack output = PressRecipes.getOutput(stack, this.slots[0]);
			
			if(output != null && stack.stackSize == 1) {
				item.setDead();
				EntityMovingItem out = new EntityMovingItem(this.worldObj);
				out.setPosition(item.posX, item.posY, item.posZ);
				out.setItemStack(output.copy());
				this.worldObj.spawnEntityInWorld(out);
			}
		}
		
		this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.pressOperate", 1.5F, 1.0F);
		
		if(this.slots[0].getMaxDamage() != 0) {
			this.slots[0].setItemDamage(this.slots[0].getItemDamage() + 1);
			if(this.slots[0].getItemDamage() >= this.slots[0].getMaxDamage()) {
				this.slots[0] = null;
			}
		}
	}
	
	public boolean canRetract() {
		if(this.power < this.usage) return false;
		return true;
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

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return stack.getItem() instanceof ItemStamp;
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0 };
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return isItemValidForSlot(i, itemStack);
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return TileEntityConveyorPress.maxPower;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.DOWN;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.power = nbt.getLong("power");
		this.press = nbt.getDouble("press");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", this.power);
		nbt.setDouble("press", this.press);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(
					this.xCoord - 1,
					this.yCoord,
					this.zCoord - 1,
					this.xCoord + 2,
					this.yCoord + 3,
					this.zCoord + 2
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
