package com.hbm.tileentity.machine.storage;

import com.hbm.inventory.container.ContainerCrateTungsten;
import com.hbm.inventory.gui.GUICrateTungsten;
import com.hbm.items.ModItems;

import api.hbm.block.ILaserable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCrateTungsten extends TileEntityCrateBase implements ILaserable {
	private int heatTimer;

	public TileEntityCrateTungsten() {
		super(27);
	}

	@Override
	public String getInventoryName() {
		return hasCustomInventoryName() ? this.customName : "container.crateTungsten";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			if(this.heatTimer > 0)
				this.heatTimer--;
	
			if(this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord) != 1 && this.heatTimer > 0)
				this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 1, 3);
			
			if(this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord) != 0 && this.heatTimer <= 0)
				this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 0, 3);
		}
		
		if(this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord) == 1) {
			this.worldObj.spawnParticle("flame", this.xCoord + this.worldObj.rand.nextDouble(), this.yCoord + 1.1, this.zCoord + this.worldObj.rand.nextDouble(), 0.0, 0.0, 0.0);
			this.worldObj.spawnParticle("smoke", this.xCoord + this.worldObj.rand.nextDouble(), this.yCoord + 1.1, this.zCoord + this.worldObj.rand.nextDouble(), 0.0, 0.0, 0.0);
			
			this.worldObj.spawnParticle("flame", this.xCoord - 0.1, this.yCoord + this.worldObj.rand.nextDouble(), this.zCoord + this.worldObj.rand.nextDouble(), 0.0, 0.0, 0.0);
			this.worldObj.spawnParticle("smoke", this.xCoord - 0.1, this.yCoord + this.worldObj.rand.nextDouble(), this.zCoord + this.worldObj.rand.nextDouble(), 0.0, 0.0, 0.0);
			
			this.worldObj.spawnParticle("flame", this.xCoord + 1.1, this.yCoord + this.worldObj.rand.nextDouble(), this.zCoord + this.worldObj.rand.nextDouble(), 0.0, 0.0, 0.0);
			this.worldObj.spawnParticle("smoke", this.xCoord + 1.1, this.yCoord + this.worldObj.rand.nextDouble(), this.zCoord + this.worldObj.rand.nextDouble(), 0.0, 0.0, 0.0);
			
			this.worldObj.spawnParticle("flame", this.xCoord + this.worldObj.rand.nextDouble(), this.yCoord + this.worldObj.rand.nextDouble(), this.zCoord - 0.1, 0.0, 0.0, 0.0);
			this.worldObj.spawnParticle("smoke", this.xCoord + this.worldObj.rand.nextDouble(), this.yCoord + this.worldObj.rand.nextDouble(), this.zCoord - 0.1, 0.0, 0.0, 0.0);
			
			this.worldObj.spawnParticle("flame", this.xCoord + this.worldObj.rand.nextDouble(), this.yCoord + this.worldObj.rand.nextDouble(), this.zCoord + 1.1, 0.0, 0.0, 0.0);
			this.worldObj.spawnParticle("smoke", this.xCoord + this.worldObj.rand.nextDouble(), this.yCoord + this.worldObj.rand.nextDouble(), this.zCoord + 1.1, 0.0, 0.0, 0.0);
		}
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		if(isLocked() || (itemStack.getItem() == ModItems.billet_polonium) || (itemStack.getItem() == ModItems.crucible && itemStack.getItemDamage() > 0))
			return false;
		
		if(FurnaceRecipes.smelting().getSmeltingResult(itemStack) == null)
			return true;
		
		return false;
	}

	@Override
	public void addEnergy(World world, int x, int y, int z, long energy, ForgeDirection dir) {
		this.heatTimer = 5;
		
		for(int i = 0; i < this.slots.length; i++) {
			
			if(this.slots[i] == null)
				continue;
			
			ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(this.slots[i]);
			
			if(this.slots[i].getItem() == ModItems.billet_polonium && energy > 10000000)
				result = new ItemStack(ModItems.billet_yharonite);
			
			if(this.slots[i].getItem() == ModItems.crucible && this.slots[i].getItemDamage() > 0 && energy > 10000000)
				result = new ItemStack(ModItems.crucible, 1, 0);
			
			int size = this.slots[i].stackSize;
			
			if(result != null && result.stackSize * size <= result.getMaxStackSize()) {
				this.slots[i] = result.copy();
				this.slots[i].stackSize *= size;
			}
		}
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCrateTungsten(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICrateTungsten(player.inventory, this);
	}
}
