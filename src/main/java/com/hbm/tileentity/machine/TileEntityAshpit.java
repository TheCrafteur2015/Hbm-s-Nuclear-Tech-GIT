package com.hbm.tileentity.machine;

import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.container.ContainerAshpit;
import com.hbm.inventory.gui.GUIAshpit;
import com.hbm.items.ItemEnums.EnumAshType;
import com.hbm.items.ModItems;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

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

public class TileEntityAshpit extends TileEntityMachineBase implements IGUIProvider {
	
	private int playersUsing = 0;
	public float doorAngle = 0;
	public float prevDoorAngle = 0;
	public boolean isFull;

	public int ashLevelWood;
	public int ashLevelCoal;
	public int ashLevelMisc;
	public int ashLevelFly;
	public int ashLevelSoot;
	
	public TileEntityAshpit() {
		super(5);
	}
	
	@Override
	public void openInventory() {
		if(!this.worldObj.isRemote) this.playersUsing++;
	}
	
	@Override
	public void closeInventory() {
		if(!this.worldObj.isRemote) this.playersUsing--;
	}

	@Override
	public String getName() {
		return "container.ashpit";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {

			int threshold = 2000;

			if(processAsh(this.ashLevelWood, EnumAshType.WOOD, threshold)) this.ashLevelWood -= threshold;
			if(processAsh(this.ashLevelCoal, EnumAshType.COAL, threshold)) this.ashLevelCoal -= threshold;
			if(processAsh(this.ashLevelMisc, EnumAshType.MISC, threshold)) this.ashLevelMisc -= threshold;
			if(processAsh(this.ashLevelFly, EnumAshType.FLY, threshold)) this.ashLevelFly -= threshold;
			if(processAsh(this.ashLevelSoot, EnumAshType.SOOT, threshold * 4)) this.ashLevelSoot -= threshold * 4;
			
			this.isFull = false;
			
			for(int i = 0; i < 5; i++) {
				if(this.slots[i] != null) this.isFull = true;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("playersUsing", this.playersUsing);
			data.setBoolean("isFull", this.isFull);
			networkPack(data, 50);
			
		} else {
			this.prevDoorAngle = this.doorAngle;
			float swingSpeed = (this.doorAngle / 10F) + 3;
			
			if(this.playersUsing > 0) {
				this.doorAngle += swingSpeed;
			} else {
				this.doorAngle -= swingSpeed;
			}
			
			this.doorAngle = MathHelper.clamp_float(this.doorAngle, 0F, 135F);
		}
	}
	
	protected boolean processAsh(int level, EnumAshType type, int threshold) {
		
		if(level >= threshold) {
			for(int i = 0; i < 5; i++) {
				if(this.slots[i] == null) {
					this.slots[i] = DictFrame.fromOne(ModItems.powder_ash, type);
					this.ashLevelWood -= threshold;
					return true;
				} else if(this.slots[i].stackSize < this.slots[i].getMaxStackSize() && this.slots[i].getItem() == ModItems.powder_ash && this.slots[i].getItemDamage() == type.ordinal()) {
					this.slots[i].stackSize++;
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.playersUsing = nbt.getInteger("playersUsing");
		this.isFull = nbt.getBoolean("isFull");
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int meta) {
		return new int[] { 0, 1, 2, 3, 4 };
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return true;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.ashLevelWood = nbt.getInteger("ashLevelWood");
		this.ashLevelCoal = nbt.getInteger("ashLevelCoal");
		this.ashLevelMisc = nbt.getInteger("ashLevelMisc");
		this.ashLevelFly = nbt.getInteger("ashLevelFly");
		this.ashLevelSoot = nbt.getInteger("ashLevelSoot");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("ashLevelWood", this.ashLevelWood);
		nbt.setInteger("ashLevelCoal", this.ashLevelCoal);
		nbt.setInteger("ashLevelMisc", this.ashLevelMisc);
		nbt.setInteger("ashLevelFly", this.ashLevelFly);
		nbt.setInteger("ashLevelSoot", this.ashLevelSoot);
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
					this.yCoord + 1,
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

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerAshpit(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIAshpit(player.inventory, this);
	}
}
