package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerMachineMiningDrill;
import com.hbm.inventory.gui.GUIMachineMiningDrill;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.LoopedSoundPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEDrillPacket;
import com.hbm.sound.SoundLoopMachine;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.block.IDrillInteraction;
import api.hbm.block.IMiningDrill;
import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyUser;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityMachineMiningDrill extends TileEntityMachineBase implements IEnergyUser, IMiningDrill, IGUIProvider {

	public long power;
	public int warning;
	public static final long maxPower = 100000;
	int age = 0;
	int timer = 50;
	int radius = 100;
	int consumption = 100;
	int fortune = 0;
	boolean flag = true;
	public float torque;
	public float rotation;
	SoundLoopMachine sound;
	
	public TileEntityMachineMiningDrill() {
		super(13);
	}

	@Override
	public String getName() {
		return "container.miningDrill";
	}

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
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.power = nbt.getLong("power");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", this.power);
	}
	
	public long getPowerScaled(long i) {
		return (this.power * i) / TileEntityMachineMiningDrill.maxPower;
	}
	
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			updateConnections();
			
			this.consumption = 100;
			this.timer = 50;
			this.radius = 1;
			this.fortune = 0;
			
			UpgradeManager.eval(this.slots, 10, 12);
			this.radius += Math.min(UpgradeManager.getLevel(UpgradeType.EFFECT), 3);
			this.consumption += Math.min(UpgradeManager.getLevel(UpgradeType.EFFECT), 3) * 80;
			
			this.timer -= Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3) * 15;
			this.consumption += Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3) * 300;
			
			this.consumption -= Math.min(UpgradeManager.getLevel(UpgradeType.POWER), 3) * 30;
			this.timer += Math.min(UpgradeManager.getLevel(UpgradeType.POWER), 3) * 5;
			
			this.fortune += Math.min(UpgradeManager.getLevel(UpgradeType.FORTUNE), 3);
			this.timer += Math.min(UpgradeManager.getLevel(UpgradeType.FORTUNE), 3) * 15;
			
			this.age++;
			if(this.age >= this.timer)
				this.age -= this.timer;
			
			this.power = Library.chargeTEFromItems(this.slots, 0, this.power, TileEntityMachineMiningDrill.maxPower);
			
			if(this.power >= this.consumption) {
				
				//operation start
				
				if(this.age == this.timer - 1) {
					this.warning = 0;
					
					//warning 0, green: drill is operational
					//warning 1, red: drill is full, has no power or the drill is jammed
					//warning 2, yellow: drill has reached max depth
					
					for(int i = this.yCoord - 1; i > this.yCoord - 1 - 100; i--) {
						
						if(i <= 3) {
							//Code 2: The drilling ended
							this.warning = 2;
							break;
						}
						
						if(this.worldObj.getBlock(this.xCoord, i, this.zCoord) != ModBlocks.drill_pipe) {
							
							if(this.worldObj.getBlock(this.xCoord, i, this.zCoord).isReplaceable(this.worldObj, this.xCoord, i, this.zCoord) || tryDrill(this.xCoord, i, this.zCoord)) {
								
								if(this.worldObj.getBlock(this.xCoord, i, this.zCoord).isReplaceable(this.worldObj, this.xCoord, i, this.zCoord)) {
									this.worldObj.setBlock(this.xCoord, i, this.zCoord, ModBlocks.drill_pipe);
								}
								
								break;
								
							} else {
								this.warning = 1;
								break;
							}
						}
						
						if(drill(this.xCoord, i, this.zCoord, this.radius))
							break;
					}
				}
				
				//operation end
				
				this.power -= this.consumption;
			} else {
				this.warning = 1;
			}
			
			int meta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
			TileEntity te = null;
			if(meta == 2) {
				te = this.worldObj.getTileEntity(this.xCoord - 2, this.yCoord, this.zCoord);
				//worldObj.setBlock(xCoord - 2, yCoord, zCoord, Blocks.dirt);
			}
			if(meta == 3) {
				te = this.worldObj.getTileEntity(this.xCoord + 2, this.yCoord, this.zCoord);
				//worldObj.setBlock(xCoord - 2, yCoord, zCoord, Blocks.dirt);
			}
			if(meta == 4) {
				te = this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord + 2);
				//worldObj.setBlock(xCoord - 2, yCoord, zCoord, Blocks.dirt);
			}
			if(meta == 5) {
				te = this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord - 2);
				//worldObj.setBlock(xCoord - 2, yCoord, zCoord, Blocks.dirt);
			}
			
			if(te != null && te instanceof IInventory) {
				IInventory chest = (IInventory)te;
				
				for(int i = 1; i < 10; i++)
					if(tryFillContainer(chest, i))
						break;
			}
			
			if(this.warning == 0) {
				this.torque += 0.1;
				if(this.torque > (100/this.timer))
					this.torque = (100/this.timer);
			} else {
				this.torque -= 0.1F;
				if(this.torque < -(100/this.timer))
					this.torque = -(100/this.timer);
			}
			
			if(this.torque < 0) {
				this.torque = 0;
			}
			this.rotation += this.torque;
			if(this.rotation >= 360)
				this.rotation -= 360;

			PacketDispatcher.wrapper.sendToAllAround(new TEDrillPacket(this.xCoord, this.yCoord, this.zCoord, this.rotation, this.torque), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 150));
			PacketDispatcher.wrapper.sendToAllAround(new LoopedSoundPacket(this.xCoord, this.yCoord, this.zCoord), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(this.xCoord, this.yCoord, this.zCoord, this.power), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
		}
	}
	
	private void updateConnections()  {
		getBlockMetadata();
		
		if(this.blockMetadata == 5 || this.blockMetadata == 4) {
			trySubscribe(this.worldObj, this.xCoord + 2, this.yCoord, this.zCoord, Library.POS_X);
			trySubscribe(this.worldObj, this.xCoord - 2, this.yCoord, this.zCoord, Library.NEG_X);
			
		} else if(this.blockMetadata == 3 || this.blockMetadata == 2) {
			trySubscribe(this.worldObj, this.xCoord, this.yCoord, this.zCoord + 2, Library.POS_Z);
			trySubscribe(this.worldObj, this.xCoord, this.yCoord, this.zCoord - 2, Library.NEG_Z);
		}
	}
	
	public boolean tryFillContainer(IInventory inventory, int slot) {
		
		int size = inventory.getSizeInventory();

		for(int i = 0; i < size; i++) {
			if(inventory.getStackInSlot(i) != null) {
				
				if(this.slots[slot] == null)
					return false;
				
				ItemStack sta1 = inventory.getStackInSlot(i).copy();
				ItemStack sta2 = this.slots[slot].copy();
				
				if(!inventory.isItemValidForSlot(i, sta2))
					continue;
				
				if(sta1 != null && sta2 != null) {
					sta1.stackSize = 1;
					sta2.stackSize = 1;
				
					if(ItemStack.areItemStacksEqual(sta1, sta2) && ItemStack.areItemStackTagsEqual(sta1, sta2) && inventory.getStackInSlot(i).stackSize < inventory.getStackInSlot(i).getMaxStackSize()) {
						this.slots[slot].stackSize--;
						
						if(this.slots[slot].stackSize <= 0)
							this.slots[slot] = null;
						
						ItemStack sta3 = inventory.getStackInSlot(i).copy();
						sta3.stackSize++;
						inventory.setInventorySlotContents(i, sta3);
					
						return true;
					}
				}
			}
		}
		for(int i = 0; i < size; i++) {
			
			if(this.slots[slot] == null)
				return false;
			
			ItemStack sta2 = this.slots[slot].copy();
			
			if(!inventory.isItemValidForSlot(i, sta2))
				continue;
			
			if(inventory.getStackInSlot(i) == null && sta2 != null) {
				sta2.stackSize = 1;
				this.slots[slot].stackSize--;
				
				if(this.slots[slot].stackSize <= 0)
					this.slots[slot] = null;
				
				inventory.setInventorySlotContents(i, sta2);
					
				return true;
			}
		}
		
		return false;
	}
	
	//Method: isOre
	//"make it oreo!"
	//"ok"
	public boolean isOreo(int x, int y, int z) {
		
		Block b = this.worldObj.getBlock(x, y, z);
		float hardness = b.getBlockHardness(this.worldObj, x, y, z);
		
		return hardness < 70 && hardness >= 0;
	}
	
	public boolean isMinableOreo(int x, int y, int z) {
		
		Block b = this.worldObj.getBlock(x, y, z);
		float hardness = b.getBlockHardness(this.worldObj, x, y, z);
		
		return (hardness < 70 && hardness >= 0) || b instanceof IDrillInteraction;
	}
	
	/**
	 * returns true if there has been a successful mining operation
	 * returns false if no block could be mined and the drill is ready to extend
	 * */
	public boolean drill(int x, int y, int z, int rad) {
		
		if(!this.flag)
			return false;
		
		for(int ix = x - rad; ix <= x + rad; ix++) {
			for(int iz = z - rad; iz <= z + rad; iz++) {
				
				if(ix != x || iz != z)
					if(tryDrill(ix, y, iz))
						return true;
			}
		}
					
		return false;
	}

	/**
	 * returns true if there has been a successful mining operation
	 * returns false if no block could be mined, as it is either air or unmineable
	 * */
	public boolean tryDrill(int x, int y, int z) {

		if(this.worldObj.getBlock(x, y, z).isAir(this.worldObj, x, y, z) || !isMinableOreo(x, y, z))
			return false;
		if(this.worldObj.getBlock(x, y, z).getMaterial().isLiquid()) {
			this.worldObj.func_147480_a(x, y, z, false);
			return false;
		}
		
		Block b = this.worldObj.getBlock(x, y, z);
		int meta = this.worldObj.getBlockMetadata(x, y, z);
		
		if(b instanceof IDrillInteraction) {
			IDrillInteraction in = (IDrillInteraction) b;
			
			ItemStack sta = in.extractResource(this.worldObj, x, y, z, meta, this);

			if(sta != null && hasSpace(sta)) {
				addItemToInventory(sta);
			}
			
			if(!in.canBreak(this.worldObj, x, y, z, meta, this))
				return true; //true because the block is still there and mining should continue
		}
		
		ItemStack stack = new ItemStack(b.getItemDropped(meta, this.worldObj.rand, this.fortune), b.quantityDropped(meta, this.fortune, this.worldObj.rand), b.damageDropped(meta));

		//yup that worked
		if(stack != null && stack.getItem() == null) {
			this.worldObj.func_147480_a(x, y, z, false);
			return true;
		}
		
		if(hasSpace(stack)) {
			addItemToInventory(stack);
			this.worldObj.func_147480_a(x, y, z, false);
			return true;
		}
		
		return true;
	}
	
	public boolean hasSpace(ItemStack stack) {

		ItemStack st = stack.copy();
		
		if(st == null)
			return true;
		
		for(int i = 1; i < 10; i++) {
			if(this.slots[i] == null)
				return true;
		}
		
		st.stackSize = 1;
		
		ItemStack[] fakeArray = this.slots.clone();
		boolean flag = true;
		for(int i = 0; i < stack.stackSize; i++) {
			if(!canAddItemToArray(st, fakeArray))
				flag = false;
		}
		
		return flag;
	}
	
	public void addItemToInventory(ItemStack stack) {

		ItemStack st = stack.copy();
		
		if(st == null)
			return;
		
		int size = st.stackSize;
		st.stackSize = 1;
		
		for(int i = 0; i < size; i++)
			canAddItemToArray(st, this.slots);
		
	}
	
	public boolean canAddItemToArray(ItemStack stack, ItemStack[] array) {

		ItemStack st = stack.copy();
		
		if(stack == null || st == null)
			return true;
		
		for(int i = 1; i < 10; i++) {
			
			if(array[i] != null) {
				ItemStack sta = array[i].copy();
				
				if(stack == null || st == null)
					return true;
				
				if(sta != null && sta.getItem() == st.getItem() && sta.stackSize < st.getMaxStackSize()) {
					array[i].stackSize++;
					return true;
				}
			}
		}
		
		for(int i = 1; i < 10; i++) {
			if(array[i] == null) {
				array[i] = stack.copy();
				return true;
			}
		}
		
		return false;
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
		return TileEntityMachineMiningDrill.maxPower;
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
	public DrillType getDrillTier() {
		return DrillType.INDUSTRIAL;
	}

	@Override
	public int getDrillRating() {
		return 50;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineMiningDrill(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineMiningDrill(player.inventory, this);
	}
}
