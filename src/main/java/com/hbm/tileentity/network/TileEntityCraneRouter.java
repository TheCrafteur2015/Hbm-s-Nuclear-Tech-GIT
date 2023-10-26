package com.hbm.tileentity.network;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerCraneRouter;
import com.hbm.inventory.gui.GUICraneRouter;
import com.hbm.module.ModulePatternMatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityCraneRouter extends TileEntityMachineBase implements IGUIProvider, IControlReceiver {
	
	public ModulePatternMatcher[] patterns = new ModulePatternMatcher[6]; //why did i make six matchers???
	public int[] modes = new int[6];
	public static final int MODE_NONE = 0;
	public static final int MODE_WHITELIST = 1;
	public static final int MODE_BLACKLIST = 2;
	public static final int MODE_WILDCARD = 3;

	public TileEntityCraneRouter() {
		super(5 * 6);
		
		for(int i = 0; i < this.patterns.length; i++) {
			this.patterns[i] = new ModulePatternMatcher(5);
		}
	}

	@Override
	public String getName() {
		return "container.craneRouter";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {

			NBTTagCompound data = new NBTTagCompound();
			for(int i = 0; i < this.patterns.length; i++) {
				NBTTagCompound compound = new NBTTagCompound();
				this.patterns[i].writeToNBT(compound);
				data.setTag("pattern" + i, compound);
			}
			data.setIntArray("modes", this.modes);
			networkPack(data, 15);
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {
		for(int i = 0; i < this.patterns.length; i++) {
			NBTTagCompound compound = data.getCompoundTag("pattern" + i);
			this.patterns[i].readFromNBT(compound);
		}
		this.modes = data.getIntArray("modes");
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCraneRouter(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICraneRouter(player.inventory, this);
	}

	public void nextMode(int index) {
		
		int matcher = index / 5;
		int mIndex = index % 5;
		
		this.patterns[matcher].nextMode(this.worldObj, this.slots[index], mIndex);
	}

	public void initPattern(ItemStack stack, int index) {
		
		int matcher = index / 5;
		int mIndex = index % 5;
		
		this.patterns[matcher].initPatternSmart(this.worldObj, stack, mIndex);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		for(int i = 0; i < this.patterns.length; i++) {
			NBTTagCompound compound = nbt.getCompoundTag("pattern" + i);
			this.patterns[i].readFromNBT(compound);
		}
		this.modes = nbt.getIntArray("modes");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		for(int i = 0; i < this.patterns.length; i++) {
			NBTTagCompound compound = new NBTTagCompound();
			this.patterns[i].writeToNBT(compound);
			nbt.setTag("pattern" + i, compound);
		}
		nbt.setIntArray("modes", this.modes);
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(this.xCoord - player.posX, this.yCoord - player.posY, this.zCoord - player.posZ).lengthVector() < 20;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		int i = data.getInteger("toggle");
		this.modes[i]++;
		if(this.modes[i] > 3)
			this.modes [i] = 0;
	}
}
