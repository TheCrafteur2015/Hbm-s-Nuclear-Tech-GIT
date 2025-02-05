package com.hbm.entity.item;

import com.hbm.util.ItemStackUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.conveyor.IConveyorPackage;
import api.hbm.conveyor.IEnterableBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class EntityMovingPackage extends EntityMovingConveyorObject implements IConveyorPackage {
	
	protected ItemStack[] contents = new ItemStack[0];

	public EntityMovingPackage(World world) {
		super(world);
		setSize(0.5F, 0.5F);
	}

	@Override
	protected void entityInit() { }

	public void setItemStacks(ItemStack[] stacks) {
		this.contents = ItemStackUtil.carefulCopyArray(stacks);
	}

	@Override
	public ItemStack[] getItemStacks() {
		return this.contents;
	}

	@Override
	public boolean interactFirst(EntityPlayer player) {

		if(!this.worldObj.isRemote) {
			
			for(ItemStack stack : this.contents) {
				if(!player.inventory.addItemStackToInventory(stack.copy())) {
					this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY + 0.125, this.posZ, stack));
				}
			}
			
			setDead();
		}

		return false;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {

		if(!this.worldObj.isRemote) {
			setDead();
			
			for(ItemStack stack : this.contents) {
				this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY + 0.125, this.posZ, stack));
			}
		}
		return true;
	}

	@Override
	public void enterBlock(IEnterableBlock enterable, BlockPos pos, ForgeDirection dir) {
		
		if(enterable.canPackageEnter(this.worldObj, pos.getX(), pos.getY(), pos.getZ(), dir, this)) {
			enterable.onPackageEnter(this.worldObj, pos.getX(), pos.getY(), pos.getZ(), dir, this);
			setDead();
		}
	}

	@Override
	public boolean onLeaveConveyor() {
		
		setDead();
		
		for(ItemStack stack : this.contents) {
			EntityItem item = new EntityItem(this.worldObj, this.posX + this.motionX * 2, this.posY + this.motionY * 2, this.posZ + this.motionZ * 2, stack);
			item.motionX = this.motionX * 2;
			item.motionY = 0.1;
			item.motionZ = this.motionZ * 2;
			item.velocityChanged = true;
			this.worldObj.spawnEntityInWorld(item);
		}
		
		return true;
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		NBTTagList nbttaglist = new NBTTagList();

		for(int i = 0; i < this.contents.length; ++i) {
			if(this.contents[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("slot", (byte) i);
				this.contents[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbt.setTag("contents", nbttaglist);
		nbt.setInteger("count", this.contents.length);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.contents = new ItemStack[nbt.getInteger("count")];
		NBTTagList nbttaglist = nbt.getTagList("contents", 10);

		for(int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound1.getByte("slot") & 255;

			if(j >= 0 && j < this.contents.length) {
				this.contents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}
}
