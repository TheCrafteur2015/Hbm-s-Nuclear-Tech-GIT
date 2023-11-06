package com.hbm.tileentity.machine;

import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.MobConfig;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.container.ContainerReactorResearch;
import com.hbm.inventory.gui.GUIReactorResearch;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFuelRod;
import com.hbm.items.machine.ItemPlateFuel;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
//TODO: fix reactor control;
public class TileEntityReactorResearch extends TileEntityMachineBase implements IControlReceiver, SimpleComponent, IGUIProvider {
	
	@SideOnly(Side.CLIENT)
	public double lastLevel;
	public double level;
	public double speed = 0.04;
	public double targetLevel;
	
	public int heat;
	public byte water;
	public final int maxHeat = 50000;
	public int[] slotFlux = new int[12];
	public int totalFlux = 0;
	
	private static final int[] slot_io = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };

	public TileEntityReactorResearch() {
		super(12);
	}
	
	private static final HashMap<ComparableStack, ItemStack> fuelMap = new HashMap<>();
	static {
		TileEntityReactorResearch.fuelMap.put(new ComparableStack(ModItems.plate_fuel_u233), new ItemStack(ModItems.waste_plate_u233, 1, 1));
		TileEntityReactorResearch.fuelMap.put(new ComparableStack(ModItems.plate_fuel_u235), new ItemStack(ModItems.waste_plate_u235, 1, 1));
		TileEntityReactorResearch.fuelMap.put(new ComparableStack(ModItems.plate_fuel_mox), new ItemStack(ModItems.waste_plate_mox, 1, 1));
		TileEntityReactorResearch.fuelMap.put(new ComparableStack(ModItems.plate_fuel_pu239), new ItemStack(ModItems.waste_plate_pu239, 1, 1));
		TileEntityReactorResearch.fuelMap.put(new ComparableStack(ModItems.plate_fuel_sa326), new ItemStack(ModItems.waste_plate_sa326, 1, 1));
		TileEntityReactorResearch.fuelMap.put(new ComparableStack(ModItems.plate_fuel_ra226be), new ItemStack(ModItems.waste_plate_ra226be, 1, 1));
		TileEntityReactorResearch.fuelMap.put(new ComparableStack(ModItems.plate_fuel_pu238be), new ItemStack(ModItems.waste_plate_pu238be, 1, 1));
	}
	
	@Override
	public String getName() {
		return "container.reactorResearch";
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		if(i < 12 && i <= 0)
			if(itemStack.getItem().getClass() == ItemPlateFuel.class)
				return true;
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.heat = nbt.getInteger("heat");
		this.water = nbt.getByte("water");
		this.level = nbt.getDouble("level");
		this.targetLevel = nbt.getDouble("targetLevel");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("heat", this.heat);
		nbt.setByte("water", this.water);
		nbt.setDouble("level", this.level);
		nbt.setDouble("targetLevel", this.targetLevel);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return TileEntityReactorResearch.slot_io;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		if(i < 12 && i >= 0)
			if(TileEntityReactorResearch.fuelMap.containsValue(stack))
				return true;
		
		return false;

	}
	
	@Override
	public void updateEntity() {
		
		rodControl();

		if(!this.worldObj.isRemote) {
			this.totalFlux = 0;
			
			if(this.level > 0) {
				reaction();
			}
						
			if(this.heat > 0) {
				this.water = getWater();
				
				if(this.water > 0) {
					this.heat -= (this.heat * (float) 0.07 * this.water / 12);
				} else if(this.water == 0) {
					this.heat -= 1;
				}
			
				if(this.heat < 0)
					this.heat = 0;
			}

			if(this.heat > this.maxHeat) {
				explode();
			}

			if(this.level > 0 && this.heat > 0 && !(blocksRad(this.xCoord + 1, this.yCoord + 1, this.zCoord) && blocksRad(this.xCoord - 1, this.yCoord + 1, this.zCoord) && blocksRad(this.xCoord, this.yCoord + 1, this.zCoord + 1) && blocksRad(this.xCoord, this.yCoord + 1, this.zCoord - 1))) {
				float rad = (float) this.heat / (float) this.maxHeat * 50F;
				ChunkRadiationManager.proxy.incrementRad(this.worldObj, this.xCoord, this.yCoord, this.zCoord, rad);
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("heat", this.heat);
			data.setByte("water", this.water);
			data.setDouble("level", this.level);
			data.setDouble("targetLevel", this.targetLevel);
			data.setIntArray("slotFlux", this.slotFlux);
			data.setInteger("totalFlux", this.totalFlux);
			networkPack(data, 150);
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.heat = data.getInteger("heat");
		this.water = data.getByte("water");
		this.level = data.getDouble("level");
		this.targetLevel = data.getDouble("targetLevel");
		this.slotFlux = data.getIntArray("slotFlux");
		this.totalFlux = data.getInteger("totalFlux");
	}
	
	public byte getWater() {
		byte water = 0;
		
		for(byte d = 0; d < 6; d++) {
			ForgeDirection dir = ForgeDirection.getOrientation(d);
			if(d < 2) {
				if(this.worldObj.getBlock(this.xCoord, this.yCoord + 1 + dir.offsetY * 2, this.zCoord).getMaterial() == Material.water)
					water++;
			} else {
				for(byte i = 0; i < 3; i++) {
					if(this.worldObj.getBlock(this.xCoord + dir.offsetX, this.yCoord + i, this.zCoord + dir.offsetZ).getMaterial() == Material.water)
						water++;
				}
			}
		}
		
		return water;
	}
	
	public boolean isSubmerged() {
		
		return this.worldObj.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord).getMaterial() == Material.water ||
				this.worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord + 1).getMaterial() == Material.water ||
				this.worldObj.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord).getMaterial() == Material.water ||
				this.worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord - 1).getMaterial() == Material.water;
	}
	
	/*private void getInteractions() {
		getInteractionForBlock(xCoord + 1, yCoord + 1, zCoord);
		getInteractionForBlock(xCoord - 1, yCoord + 1, zCoord);
		getInteractionForBlock(xCoord, yCoord + 1, zCoord + 1);
		getInteractionForBlock(xCoord, yCoord + 1, zCoord - 1);
	}

	private void getInteractionForBlock(int x, int y, int z) {

		Block b = worldObj.getBlock(x, y, z);
		TileEntity te = worldObj.getTileEntity(x, y, z);
	}*/

	private boolean blocksRad(int x, int y, int z) {

		Block b = this.worldObj.getBlock(x, y, z);

		if(b == ModBlocks.block_lead || b == ModBlocks.block_desh || b == ModBlocks.reactor_research || b == ModBlocks.machine_reactor_breeding)
			return true;

		if(b.getExplosionResistance(null) >= 100)
			return true;

		return false;
	}
	
	private int[] getNeighboringSlots(int id) {

		switch(id) {
		case 0:
			return new int[] { 1, 5 };
		case 1:
			return new int[] { 0, 6 };
		case 2:
			return new int[] { 3, 7 };
		case 3:
			return new int[] { 2, 4, 8 };
		case 4:
			return new int[] { 3, 9 };
		case 5:
			return new int[] { 0, 6, 0xA };
		case 6:
			return new int[] { 1, 5, 0xB };
		case 7:
			return new int[] { 2, 8 };
		case 8:
			return new int[] { 3, 7, 9 };
		case 9:
			return new int[] { 4, 8 };
		case 10:
			return new int[] { 5, 0xB };
		case 11:
			return new int[] { 6, 0xA };
		}

		return null;
	}
	
	private void reaction() {
		for(byte i = 0; i < 12; i++) {
			if(this.slots[i] == null)  {
				this.slotFlux[i] = 0;
				continue;
			}
			
			if(this.slots[i].getItem() instanceof ItemPlateFuel) {
				ItemPlateFuel rod = (ItemPlateFuel) this.slots[i].getItem();
				
				int outFlux = rod.react(this.worldObj, this.slots[i], this.slotFlux[i]);
				this.heat += outFlux * 2;
				this.slotFlux[i] = 0;
				this.totalFlux += outFlux;
				
				int[] neighborSlots = getNeighboringSlots(i);
				
				if(ItemFuelRod.getLifeTime(this.slots[i]) > rod.lifeTime) {
					this.slots[i] = TileEntityReactorResearch.fuelMap.get(new ComparableStack(this.slots[i])).copy();
				}
				
				for(byte j = 0; j < neighborSlots.length; j++) {
					this.slotFlux[neighborSlots[j]] += (int) (outFlux * this.level);
				}
				continue;
			}
			
			if(this.slots[i].getItem() == ModItems.meteorite_sword_bred)
				this.slots[i] = new ItemStack(ModItems.meteorite_sword_irradiated);
			
			this.slotFlux[i] = 0;
		}
	}

	
	private void explode() {
		
		for(int i = 0; i < this.slots.length; i++) {
			this.slots[i] = null;
		}
		
		this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
		
		for(byte d = 0; d < 6; d++) {
			ForgeDirection dir = ForgeDirection.getOrientation(d);
			if(d < 2) {
				if(this.worldObj.getBlock(this.xCoord, this.yCoord + 1 + dir.offsetY * 2, this.zCoord).getMaterial() == Material.water)
					this.worldObj.setBlockToAir(this.xCoord, this.yCoord + 1 + dir.offsetY * 2, this.zCoord);
			} else {
				for(byte i = 0; i < 3; i++) {
					if(this.worldObj.getBlock(this.xCoord + dir.offsetX, this.yCoord + i, this.zCoord + dir.offsetZ).getMaterial() == Material.water)
						this.worldObj.setBlockToAir(this.xCoord + dir.offsetX, this.yCoord + i, this.zCoord + dir.offsetZ);
				}
			}
		}
		
		this.worldObj.createExplosion(null, this.xCoord, this.yCoord, this.zCoord, 18.0F, true);
		this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, ModBlocks.deco_steel);
		this.worldObj.setBlock(this.xCoord, this.yCoord + 1, this.zCoord, ModBlocks.corium_block);
		this.worldObj.setBlock(this.xCoord, this.yCoord + 2, this.zCoord, ModBlocks.deco_steel);

		ChunkRadiationManager.proxy.incrementRad(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 50);
		
		if(MobConfig.enableElementals) {
			List<EntityPlayer> players = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5).expand(100, 100, 100));
			
			for(EntityPlayer player : players) {
				player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).setBoolean("radMark", true);
			}
		}
	}
	
	//Control Rods
	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(this.xCoord - player.posX, this.yCoord - player.posY, this.zCoord - player.posZ).lengthVector() < 20;
	}
	
	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("level")) {
			setTarget(data.getDouble("level"));
		}
		
		markDirty();
	}
	
	public void setTarget(double target) {
		this.targetLevel = target;
	}
	
	public void rodControl() {
		if(this.worldObj.isRemote) {
			
			this.lastLevel = this.level;
		
		} else {
			
			if(this.level < this.targetLevel) {
				
				this.level += this.speed;
				
				if(this.level >= this.targetLevel)
					this.level = this.targetLevel;
			}
			
			if(this.level > this.targetLevel) {
				
				this.level -= this.speed;
				
				if(this.level <= this.targetLevel)
					this.level = this.targetLevel;
			}
		}
	}
	
	public int[] getDisplayData() {
		int[] data = new int[2];
		data[0] = this.totalFlux;
		data[1] = (int) Math.round((this.heat) * 0.00002 * 980 + 20);
		return data;
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	// do some opencomputer stuff
	@Override
	public String getComponentName() {
		return "research_reactor";
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getTemp(Context context, Arguments args) { // or getHeat, whatever.
		return new Object[] {this.heat};
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getLevel(Context context, Arguments args) {
		return new Object[] {this.level * 100};
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getTargetLevel(Context context, Arguments args) {
		return new Object[] {this.targetLevel};
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getFlux(Context context, Arguments args) {
		return new Object[] {this.totalFlux};
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		return new Object[] {this.heat, this.level, this.targetLevel, this.totalFlux};
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] setLevel(Context context, Arguments args) {
		double newLevel = args.checkDouble(0)/100.0;
		if (newLevel > 1.0) {
			newLevel = 1.0;
		} else if (newLevel < 0.0) {
			newLevel = 0.0;
		}
		this.targetLevel = newLevel;
		return new Object[] {};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerReactorResearch(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIReactorResearch(player.inventory, this);
	}
}
