package com.hbm.entity.item;

import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.conveyor.IConveyorItem;
import api.hbm.conveyor.IEnterableBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class EntityMovingItem extends EntityMovingConveyorObject implements IConveyorItem {

	public EntityMovingItem(World p_i1582_1_) {
		super(p_i1582_1_);
		setSize(0.375F, 0.375F);
	}

	public void setItemStack(ItemStack stack) {
		getDataWatcher().updateObject(10, stack);
		getDataWatcher().setObjectWatched(10);
	}

	@Override
	public ItemStack getItemStack() {
		ItemStack stack = getDataWatcher().getWatchableObjectItemStack(10);
		return stack == null ? new ItemStack(Blocks.stone) : stack;
	}

	@Override
	public boolean interactFirst(EntityPlayer player) {

		if(!this.worldObj.isRemote && player.inventory.addItemStackToInventory(getItemStack().copy())) {
			setDead();
		}

		return false;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {

		if(!this.worldObj.isRemote) {
			setDead();
			this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, getItemStack()));
		}
		return true;
	}

	@Override
	protected void entityInit() {
		getDataWatcher().addObjectByDataType(10, 5);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {

		NBTTagCompound compound = nbt.getCompoundTag("Item");
		setItemStack(ItemStack.loadItemStackFromNBT(compound));

		ItemStack stack = getDataWatcher().getWatchableObjectItemStack(10);

		if(stack == null || stack.stackSize <= 0)
			setDead();
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {

		if(getItemStack() != null)
			nbt.setTag("Item", getItemStack().writeToNBT(new NBTTagCompound()));
	}

	@Override
	public void enterBlock(IEnterableBlock enterable, BlockPos pos, ForgeDirection dir) {
		
		if(enterable.canItemEnter(this.worldObj, pos.getX(), pos.getY(), pos.getZ(), dir, this)) {
			enterable.onItemEnter(this.worldObj, pos.getX(), pos.getY(), pos.getZ(), dir, this);
			setDead();
		}
	}

	@Override
	public boolean onLeaveConveyor() {
		
		setDead();
		EntityItem item = new EntityItem(this.worldObj, this.posX + this.motionX * 2, this.posY + this.motionY * 2, this.posZ + this.motionZ * 2, getItemStack());
		item.motionX = this.motionX * 2;
		item.motionY = 0.1;
		item.motionZ = this.motionZ * 2;
		item.velocityChanged = true;
		this.worldObj.spawnEntityInWorld(item);
		
		return true;
	}
}
