package com.hbm.tileentity.deco;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.missile.EntityBobmazon;
import com.hbm.extprop.HbmPlayerProps;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemKitCustom;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.IRepairable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityLanternBehemoth extends TileEntity implements INBTPacketReceiver, IRepairable {
	
	public boolean isBroken = false;
	public int comTimer = -1;

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {

			if(this.comTimer == 360) this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.hornNearSingle", 10F, 1F);
			if(this.comTimer == 280) this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.hornFarSingle", 10000F, 1F);
			if(this.comTimer == 220) this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.hornNearDual", 10F, 1F);
			if(this.comTimer == 100) this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.hornFarDual", 10000F, 1F);
			
			if(comTimer == 0) {
				List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord - 10, yCoord - 10, zCoord - 10, xCoord + 11, yCoord + 11, zCoord + 11));
				EntityPlayer first = players.isEmpty() ? null : players.get(0);
				boolean bonus = first == null ? false : (HbmPlayerProps.getData(first).reputation >= 10);
				EntityBobmazon shuttle = new EntityBobmazon(worldObj);
				shuttle.posX = xCoord + 0.5 + worldObj.rand.nextGaussian() * 10;
				shuttle.posY = 300;
				shuttle.posZ = this.zCoord + 0.5 + this.worldObj.rand.nextGaussian() * 10;
				ItemStack payload = ItemKitCustom.create("Supplies", null, 0xffffff, 0x008000,
						new ItemStack(ModItems.circuit_aluminium, 4 + worldObj.rand.nextInt(4)),
						new ItemStack(ModItems.circuit_copper, 4 + worldObj.rand.nextInt(2)),
						new ItemStack(ModItems.circuit_red_copper, 2 + worldObj.rand.nextInt(3)),
						new ItemStack(ModItems.circuit_gold, 1 + worldObj.rand.nextInt(2)),
						bonus ? new ItemStack(ModItems.gem_alexandrite) : new ItemStack(Items.diamond, 6 + worldObj.rand.nextInt(6)),
						new ItemStack(Blocks.red_flower));
				shuttle.payload = payload;
				
				this.worldObj.spawnEntityInWorld(shuttle);
			}
			
			if(this.comTimer >= 0) {
				this.comTimer--;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("isBroken", this.isBroken);
			INBTPacketReceiver.networkPack(this, data, 250);
		}
	}
	
	@Override
	public void invalidate() {
		super.invalidate();
		List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord - 50, yCoord - 50, zCoord - 50, xCoord + 51, yCoord + 51, zCoord + 51));
		for(EntityPlayer player : players) {
			HbmPlayerProps props = HbmPlayerProps.getData(player);
			if(props.reputation > -10) props.reputation--;
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.isBroken = nbt.getBoolean("isBroken");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.isBroken = nbt.getBoolean("isBroken");
		this.comTimer = nbt.getInteger("comTimer");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("isBroken", this.isBroken);
		nbt.setInteger("comTimer", this.comTimer);
	}

	@Override
	public boolean isDamaged() {
		return this.isBroken;
	}
	
	List<AStack> repair = new ArrayList<>();
	@Override
	public List<AStack> getRepairMaterials() {
		
		if(!this.repair.isEmpty())
			return this.repair;

		this.repair.add(new OreDictStack(OreDictManager.STEEL.plate(), 2));
		this.repair.add(new ComparableStack(ModItems.circuit_copper, 1));
		return this.repair;
	}

	@Override
	public void repair() {
		this.isBroken = false;
		this.comTimer = 400;
		markDirty();
	}

	@Override public void tryExtinguish(World world, int x, int y, int z, EnumExtinguishType type) { }
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(
					this.xCoord,
					this.yCoord,
					this.zCoord,
					this.xCoord + 1,
					this.yCoord + 6,
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
}
