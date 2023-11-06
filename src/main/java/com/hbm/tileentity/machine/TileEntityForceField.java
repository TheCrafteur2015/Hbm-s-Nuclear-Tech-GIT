package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.container.ContainerForceField;
import com.hbm.inventory.gui.GUIForceField;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEFFPacket;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyUser;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityForceField extends TileEntityLoadedBase implements ISidedInventory, IEnergyUser, IGUIProvider {

	private ItemStack slots[];
	
	public int health = 100;
	public int maxHealth = 100;
	public long power;
	public int powerCons;
	public int cooldown = 0;
	public int blink = 0;
	public float radius = 16;
	public boolean isOn = false;
	public int color = 0x0000FF;
	public final int baseCon = 1000;
	public final int radCon = 500;
	public final int shCon = 250;
	public static final long maxPower = 1000000;
	
	private static final int[] slots_top = new int[] {0};
	private static final int[] slots_bottom = new int[] {0};
	private static final int[] slots_side = new int[] {0};
	
	private String customName;
	
	public TileEntityForceField() {
		this.slots = new ItemStack[3];
	}

	@Override
	public int getSizeInventory() {
		return this.slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return this.slots[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if(this.slots[i] != null)
		{
			ItemStack itemStack = this.slots[i];
			this.slots[i] = null;
			return itemStack;
		} else {
		return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		this.slots[i] = itemStack;
		if(itemStack != null && itemStack.stackSize > getInventoryStackLimit())
		{
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return hasCustomInventoryName() ? this.customName : "container.forceField";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}
	
	public void setCustomName(String name) {
		this.customName = name;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this)
		{
			return false;
		}else{
			return player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <=64;
		}
	}
	
	//You scrubs aren't needed for anything (right now)
	@Override
	public void openInventory() {}
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		if(i == 0)
			if(itemStack.getItem() instanceof IBatteryItem)
				return true;
		
		if(i == 1)
			return true;
		
		return false;
	}
	
	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(this.slots[i] != null)
		{
			if(this.slots[i].stackSize <= j)
			{
				ItemStack itemStack = this.slots[i];
				this.slots[i] = null;
				return itemStack;
			}
			ItemStack itemStack1 = this.slots[i].splitStack(j);
			if (this.slots[i].stackSize == 0)
			{
				this.slots[i] = null;
			}
			
			return itemStack1;
		} else {
			return null;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);
		
		this.power = nbt.getLong("powerTime");
		this.health = nbt.getInteger("health");
		this.maxHealth = nbt.getInteger("maxHealth");
		this.cooldown = nbt.getInteger("cooldown");
		this.blink = nbt.getInteger("blink");
		this.radius = nbt.getFloat("radius");
		this.isOn = nbt.getBoolean("isOn");
		
		this.slots = new ItemStack[getSizeInventory()];
		
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if(b0 >= 0 && b0 < this.slots.length)
			{
				this.slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("powerTime", this.power);
		nbt.setInteger("health", this.health);
		nbt.setInteger("maxHealth", this.maxHealth);
		nbt.setInteger("cooldown", this.cooldown);
		nbt.setInteger("blink", this.blink);
		nbt.setFloat("radius", this.radius);
		nbt.setBoolean("isOn", this.isOn);
		
		NBTTagList list = new NBTTagList();
		
		for(int i = 0; i < this.slots.length; i++)
		{
			if(this.slots[i] != null)
			{
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte)i);
				this.slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_)
    {
        return p_94128_1_ == 0 ? TileEntityForceField.slots_bottom : (p_94128_1_ == 1 ? TileEntityForceField.slots_top : TileEntityForceField.slots_side);
    }

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return false;
	}
	
	public int getHealthScaled(int i) {
		return (this.health * i) / this.maxHealth;
	}
	
	public long getPowerScaled(long i) {
		return (this.power * i) / TileEntityForceField.maxPower;
	}
	
	@Override
	public void updateEntity() {

		if(!this.worldObj.isRemote) {
			
			updateConnections();
			
			int rStack = 0;
			int hStack = 0;
			this.radius = 16;
			this.maxHealth = 100;
			
			if(this.slots[1] != null && this.slots[1].getItem() == ModItems.upgrade_radius) {
				rStack = this.slots[1].stackSize;
				this.radius += rStack * 16;
			}
			
			if(this.slots[2] != null && this.slots[2].getItem() == ModItems.upgrade_health) {
				hStack = this.slots[2].stackSize;
				this.maxHealth += hStack * 50;
			}
			
			this.powerCons = this.baseCon + rStack * this.radCon + hStack * this.shCon;
			
			this.power = Library.chargeTEFromItems(this.slots, 0, this.power, TileEntityForceField.maxPower);
			
			if(this.blink > 0) {
				this.blink--;
				this.color = 0xFF0000;
			} else {
				this.color = 0x00FF00;
			}
		}
		
		if(this.cooldown > 0) {
			this.cooldown--;
		} else {
			if(this.health < this.maxHealth)
				this.health += this.maxHealth / 100;
			
			if(this.health > this.maxHealth)
				this.health = this.maxHealth;
		}
		
		if(this.isOn && this.cooldown == 0 && this.health > 0 && this.power >= this.powerCons) {
			doField(this.radius);
			
			if(!this.worldObj.isRemote) {
				this.power -= this.powerCons;
			}
		} else {
			this.outside.clear();
			this.inside.clear();
		}

		if(!this.worldObj.isRemote) {
			if(this.power < this.powerCons)
				this.power = 0;
		}
		
		if(!this.worldObj.isRemote) {
			PacketDispatcher.wrapper.sendToAllAround(new TEFFPacket(this.xCoord, this.yCoord, this.zCoord, this.radius, this.health, this.maxHealth, (int) this.power, this.isOn, this.color, this.cooldown), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 500));
		}
	}
	
	private int impact(Entity e) {
		
		double mass = e.height * e.width * e.width;
		double speed = getMotionWithFallback(e);
		return (int)(mass * speed * 50);
	}
	
	private void damage(int ouch) {
		this.health -= ouch;
		
		if(ouch >= (this.maxHealth / 250))
		this.blink = 5;
		
		if(this.health <= 0) {
			this.health = 0;
			this.cooldown = (int) (100 + this.radius);
		}
	}

	List<Entity> outside = new ArrayList<>();
	List<Entity> inside = new ArrayList<>();
	
	
	private void doField(float rad) {

		List<Entity> oLegacy = new ArrayList<>(this.outside);
		List<Entity> iLegacy = new ArrayList<>(this.inside);

		this.outside.clear();
		this.inside.clear();
		
		List<Object> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(this.xCoord + 0.5 - (rad + 25), this.yCoord + 0.5 - (rad + 25), this.zCoord + 0.5 - (rad + 25), this.xCoord + 0.5 + (rad + 25), this.yCoord + 0.5 + (rad + 25), this.zCoord + 0.5 + (rad + 25)));
		
		for(Object o : list) {
			
			if(o instanceof Entity && !(o instanceof EntityPlayer)) {
				Entity entity = (Entity)o;
				
				double dist = Math.sqrt(Math.pow(this.xCoord + 0.5 - entity.posX, 2) + Math.pow(this.yCoord + 0.5 - entity.posY, 2) + Math.pow(this.zCoord + 0.5 - entity.posZ, 2));
				
				boolean out = dist > rad;
				
				//if the entity has not been registered yet
				if(!oLegacy.contains(entity) && !iLegacy.contains(entity)) {
					if(out) {
						this.outside.add(entity);
					} else {
						this.inside.add(entity);
					}
					
				//if the entity has been detected before
				} else {
					
					//if the entity has crossed inwards
					if(oLegacy.contains(entity) && !out) {
						Vec3 vec = Vec3.createVectorHelper(this.xCoord + 0.5 - entity.posX, this.yCoord + 0.5 - entity.posY, this.zCoord + 0.5 - entity.posZ);
						vec = vec.normalize();
						
						double mx = -vec.xCoord * (rad + 1);
						double my = -vec.yCoord * (rad + 1);
						double mz = -vec.zCoord * (rad + 1);
						
						entity.setLocationAndAngles(this.xCoord + 0.5 + mx, this.yCoord + 0.5 + my, this.zCoord + 0.5 + mz, 0, 0);
						
						double mo = Math.sqrt(Math.pow(entity.motionX, 2) + Math.pow(entity.motionY, 2) + Math.pow(entity.motionZ, 2));

						entity.motionX = vec.xCoord * -mo;
						entity.motionY = vec.yCoord * -mo;
						entity.motionZ = vec.zCoord * -mo;

						entity.posX -= entity.motionX;
						entity.posY -= entity.motionY;
						entity.posZ -= entity.motionZ;

			    		this.worldObj.playSoundAtEntity(entity, "hbm:weapon.sparkShoot", 2.5F, 1.0F);
						this.outside.add(entity);
						
						if(!this.worldObj.isRemote) {
							damage(impact(entity));
						}
						
					} else
					
					//if the entity has crossed outwards
					if(iLegacy.contains(entity) && out) {
						Vec3 vec = Vec3.createVectorHelper(this.xCoord + 0.5 - entity.posX, this.yCoord + 0.5 - entity.posY, this.zCoord + 0.5 - entity.posZ);
						vec = vec.normalize();
						
						double mx = -vec.xCoord * (rad - 1);
						double my = -vec.yCoord * (rad - 1);
						double mz = -vec.zCoord * (rad - 1);

						entity.setLocationAndAngles(this.xCoord + 0.5 + mx, this.yCoord + 0.5 + my, this.zCoord + 0.5 + mz, 0, 0);
						
						double mo = Math.sqrt(Math.pow(entity.motionX, 2) + Math.pow(entity.motionY, 2) + Math.pow(entity.motionZ, 2));

						entity.motionX = vec.xCoord * mo;
						entity.motionY = vec.yCoord * mo;
						entity.motionZ = vec.zCoord * mo;

						entity.posX -= entity.motionX;
						entity.posY -= entity.motionY;
						entity.posZ -= entity.motionZ;

			    		this.worldObj.playSoundAtEntity(entity, "hbm:weapon.sparkShoot", 2.5F, 1.0F);
						this.inside.add(entity);
						
						if(!this.worldObj.isRemote) {
							damage(impact(entity));
						}
						
					} else {
						
						if(out) {
							this.outside.add(entity);
						} else {
							this.inside.add(entity);
						}
					}
				}
			}
		}
	}
	
	private double getMotionWithFallback(Entity e) {

		Vec3 v1 = Vec3.createVectorHelper(e.motionX, e.motionY, e.motionZ);
		Vec3 v2 = Vec3.createVectorHelper(e.posX - e.prevPosY, e.posY - e.prevPosY, e.posZ - e.prevPosZ);

		double s1 = v1.lengthVector();
		double s2 = v2.lengthVector();
		
		if(s1 == 0)
			return s2;
		
		if(s2 == 0)
			return s1;
		
		return Math.min(s1, s2);
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
		return TileEntityForceField.maxPower;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.UP && dir != ForgeDirection.UNKNOWN;
	}
	
	private void updateConnections() {
		trySubscribe(this.worldObj, this.xCoord + 1, this.yCoord, this.zCoord, Library.POS_X);
		trySubscribe(this.worldObj, this.xCoord - 1, this.yCoord, this.zCoord, Library.NEG_X);
		trySubscribe(this.worldObj, this.xCoord, this.yCoord, this.zCoord + 1, Library.POS_Z);
		trySubscribe(this.worldObj, this.xCoord, this.yCoord, this.zCoord - 1, Library.NEG_Z);
		trySubscribe(this.worldObj, this.xCoord, this.yCoord - 1, this.zCoord, Library.NEG_Y);
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerForceField(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIForceField(player.inventory, this);
	}
}
