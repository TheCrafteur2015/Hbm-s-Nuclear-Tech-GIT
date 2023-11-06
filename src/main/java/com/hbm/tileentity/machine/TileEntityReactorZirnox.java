package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.MobConfig;
import com.hbm.entity.projectile.EntityZirnoxDebris;
import com.hbm.entity.projectile.EntityZirnoxDebris.DebrisType;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.container.ContainerReactorZirnox;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIReactorZirnox;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemZirnoxRod;
import com.hbm.items.machine.ItemZirnoxRod.EnumZirnoxType;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.EnumUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityReactorZirnox extends TileEntityMachineBase implements IFluidContainer, IFluidAcceptor, IFluidSource, IControlReceiver, IFluidStandardTransceiver, SimpleComponent, IGUIProvider {

	public int heat;
	public static final int maxHeat = 100000;
	public int pressure;
	public static final int maxPressure = 100000;
	public boolean isOn = false;

	public List<IFluidAcceptor> list = new ArrayList<>();
	public byte age;

	public FluidTank steam;
	public FluidTank carbonDioxide;
	public FluidTank water;
	
	private static final int[] slots_io = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23 };

	public static final HashMap<ComparableStack, ItemStack> fuelMap = new HashMap<>();
	static {
		TileEntityReactorZirnox.fuelMap.put(new ComparableStack(ModItems.rod_zirnox, 1, EnumZirnoxType.NATURAL_URANIUM_FUEL.ordinal()), new ItemStack(ModItems.rod_zirnox_natural_uranium_fuel_depleted));
		TileEntityReactorZirnox.fuelMap.put(new ComparableStack(ModItems.rod_zirnox, 1, EnumZirnoxType.URANIUM_FUEL.ordinal()), new ItemStack(ModItems.rod_zirnox_uranium_fuel_depleted));
		TileEntityReactorZirnox.fuelMap.put(new ComparableStack(ModItems.rod_zirnox, 1, EnumZirnoxType.TH232.ordinal()), new ItemStack(ModItems.rod_zirnox, 1, EnumZirnoxType.THORIUM_FUEL.ordinal()));
		TileEntityReactorZirnox.fuelMap.put(new ComparableStack(ModItems.rod_zirnox, 1, EnumZirnoxType.THORIUM_FUEL.ordinal()), new ItemStack(ModItems.rod_zirnox_thorium_fuel_depleted));
		TileEntityReactorZirnox.fuelMap.put(new ComparableStack(ModItems.rod_zirnox, 1, EnumZirnoxType.MOX_FUEL.ordinal()), new ItemStack(ModItems.rod_zirnox_mox_fuel_depleted));
		TileEntityReactorZirnox.fuelMap.put(new ComparableStack(ModItems.rod_zirnox, 1, EnumZirnoxType.PLUTONIUM_FUEL.ordinal()), new ItemStack(ModItems.rod_zirnox_plutonium_fuel_depleted));
		TileEntityReactorZirnox.fuelMap.put(new ComparableStack(ModItems.rod_zirnox, 1, EnumZirnoxType.U233_FUEL.ordinal()), new ItemStack(ModItems.rod_zirnox_u233_fuel_depleted));
		TileEntityReactorZirnox.fuelMap.put(new ComparableStack(ModItems.rod_zirnox, 1, EnumZirnoxType.U235_FUEL.ordinal()), new ItemStack(ModItems.rod_zirnox_u235_fuel_depleted));
		TileEntityReactorZirnox.fuelMap.put(new ComparableStack(ModItems.rod_zirnox, 1, EnumZirnoxType.LES_FUEL.ordinal()), new ItemStack(ModItems.rod_zirnox_les_fuel_depleted));
		TileEntityReactorZirnox.fuelMap.put(new ComparableStack(ModItems.rod_zirnox, 1, EnumZirnoxType.LITHIUM.ordinal()), new ItemStack(ModItems.rod_zirnox_tritium));
		TileEntityReactorZirnox.fuelMap.put(new ComparableStack(ModItems.rod_zirnox, 1, EnumZirnoxType.ZFB_MOX.ordinal()), new ItemStack(ModItems.rod_zirnox_zfb_mox_depleted));
	}

	public TileEntityReactorZirnox() {
		super(28);
		this.steam = new FluidTank(Fluids.SUPERHOTSTEAM, 8000);
		this.carbonDioxide = new FluidTank(Fluids.CARBONDIOXIDE, 16000);
		this.water = new FluidTank(Fluids.WATER, 32000);
	}

	@Override
	public String getName() {
		return "container.zirnox";
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return TileEntityReactorZirnox.slots_io;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return i < 24 && stack.getItem() instanceof ItemZirnoxRod;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		return i < 24 && !(stack.getItem() instanceof ItemZirnoxRod);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.heat = nbt.getInteger("heat");
		this.pressure = nbt.getInteger("pressure");
		this.isOn = nbt.getBoolean("isOn");
		this.steam.readFromNBT(nbt, "steam");
		this.carbonDioxide.readFromNBT(nbt, "carbondioxide");
		this.water.readFromNBT(nbt, "water");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("heat", this.heat);
		nbt.setInteger("pressure", this.pressure);
		nbt.setBoolean("isOn", this.isOn);
		this.steam.writeToNBT(nbt, "steam");
		this.carbonDioxide.writeToNBT(nbt, "carbondioxide");
		this.water.writeToNBT(nbt, "water");

	}

	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.heat = data.getInteger("heat");
		this.pressure = data.getInteger("pressure");
		this.isOn = data.getBoolean("isOn");
	}

	public int getGaugeScaled(int i, int type) {
		switch (type) {
			case 0: return (this.steam.getFill() * i) / this.steam.getMaxFill();
			case 1: return (this.carbonDioxide.getFill() * i) / this.carbonDioxide.getMaxFill();
			case 2: return (this.water.getFill() * i) / this.water.getMaxFill();
			case 3: return (this.heat * i) / TileEntityReactorZirnox.maxHeat;
			case 4: return (this.pressure * i)  / TileEntityReactorZirnox.maxPressure;
			default: return 1;
		}
	}

	private int[] getNeighbouringSlots(int id) {

		switch(id) {
		case 0: return new int[] { 1, 7 };
		case 1: return new int[] { 0, 2, 8 };
		case 2: return new int[] { 1, 9 };
		case 3: return new int[] { 4, 10 };
		case 4: return new int[] { 3, 5, 11 };
		case 5: return new int[] { 4, 6, 12 };
		case 6: return new int[] { 5, 13 };
		case 7: return new int[] { 0, 8, 14 };
		case 8: return new int[] { 1, 7, 9, 15 };
		case 9: return new int[] { 2, 8, 16};
		case 10: return new int[] { 3, 11, 17 };
		case 11: return new int[] { 4, 10, 12, 18 };
		case 12: return new int[] { 5, 11, 13, 19 };
		case 13: return new int[] { 6, 12, 20 };
		case 14: return new int[] { 7, 15, 21 }; 
		case 15: return new int[] { 8, 14, 16, 22 };
		case 16: return new int[] { 9, 15, 23 };
		case 17: return new int[] { 10, 18 };
		case 18: return new int[] { 11, 17, 19 };
		case 19: return new int[] { 12, 18, 20 };
		case 20: return new int[] { 13, 19 };
		case 21: return new int[] { 14, 22 };
		case 22: return new int[] { 15, 21, 23 };
		case 23: return new int[] { 16, 22 };
		}

		return null;
	}

	@Override
	public void updateEntity() {

		if(!this.worldObj.isRemote) {

			this.age++;

			if (this.age >= 20) {
				this.age = 0;
			}

			if(this.age == 9 || this.age == 19) {
				fillFluidInit(this.steam.getTankType());
			}
			
			if(this.worldObj.getTotalWorldTime() % 20 == 0) {
				updateConnections();
			}
			
			this.carbonDioxide.loadTank(24, 26, this.slots);
			this.water.loadTank(25, 27, this.slots);
			
			if(this.isOn) {
				for(int i = 0; i < 24; i++) {

					if(this.slots[i] != null) {
						if(this.slots[i].getItem() instanceof ItemZirnoxRod)
							decay(i);
						else if(this.slots[i].getItem() == ModItems.meteorite_sword_bred)
							this.slots[i] = new ItemStack(ModItems.meteorite_sword_irradiated);
					}
				}
			}
			
			//2(fill) + (x * fill%)
			this.pressure = (this.carbonDioxide.getFill() * 2) + (int)(this.heat * ((float)this.carbonDioxide.getFill() / (float)this.carbonDioxide.getMaxFill()));

			if(this.heat > 0 && this.heat < TileEntityReactorZirnox.maxHeat) {
				if(this.water.getFill() > 0 && this.carbonDioxide.getFill() > 0 && this.steam.getFill() < this.steam.getMaxFill()) {
					generateSteam();
					//(x * pressure) / 1,000,000
					this.heat -= (int) ((float)this.heat * (float)this.pressure / 1000000F);
				} else {
					this.heat -= 10;
				}
				
			}
			
			for(DirPos pos : getConPos()) {
				this.sendFluid(this.steam, this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}

			checkIfMeltdown();
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("heat", this.heat);
			data.setInteger("pressure", this.pressure);
			data.setBoolean("isOn", this.isOn);
			networkPack(data, 150);
			
			this.steam.updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
			this.carbonDioxide.updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
			this.water.updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
		}
	}

	private void generateSteam() {
		
		// function of SHS produced per tick
		// (heat - 10256)/100000 * steamFill (max efficiency at 14b) * 25 * 5 (should get rid of any rounding errors)
		if(this.heat > 10256) {
			int Water = (int)(((this.heat - 10256F) / TileEntityReactorZirnox.maxHeat) * Math.min((this.carbonDioxide.getFill() / 14000F), 1F) * 25F * 5F);
			int Steam = Water * 1;
			
			this.water.setFill(this.water.getFill() - Water);
			this.steam.setFill(this.steam.getFill() + Steam);
			
			if(this.water.getFill() < 0)
				this.water.setFill(0);

			if(this.steam.getFill() > this.steam.getMaxFill())
				this.steam.setFill(this.steam.getMaxFill());
		}
	}

	private boolean hasFuelRod(int id) {
		if(this.slots[id] != null) {
			if(this.slots[id].getItem() instanceof ItemZirnoxRod) {
				final EnumZirnoxType num = EnumUtil.grabEnumSafely(EnumZirnoxType.class, this.slots[id].getItemDamage());
				return !num.breeding;
			}
		}

		return false;
	}

	private int getNeighbourCount(int id) {

		int[] neighbours = getNeighbouringSlots(id);

		if(neighbours == null)
			return 0;

		int count = 0;

		for (int neighbour : neighbours)
			if(hasFuelRod(neighbour))
				count++;

		return count;

	}

	// itemstack in slots[id] has to contain ItemZirnoxRod
	private void decay(int id) {
		int decay = getNeighbourCount(id);
		final EnumZirnoxType num = EnumUtil.grabEnumSafely(EnumZirnoxType.class, this.slots[id].getItemDamage());

		if(!num.breeding)
			decay++;

		for(int i = 0; i < decay; i++) {
			this.heat += num.heat;
			ItemZirnoxRod.incrementLifeTime(this.slots[id]);
			
			if(ItemZirnoxRod.getLifeTime(this.slots[id]) > num.maxLife) {
				this.slots[id] = TileEntityReactorZirnox.fuelMap.get(new ComparableStack(getStackInSlot(id))).copy();
				break;
			}
		}
	}

	private void checkIfMeltdown() {
		if (this.pressure > TileEntityReactorZirnox.maxPressure || this.heat > TileEntityReactorZirnox.maxHeat) {
			meltdown();
		}
	}

	private void spawnDebris(DebrisType type) {

		EntityZirnoxDebris debris = new EntityZirnoxDebris(this.worldObj, this.xCoord + 0.5D, this.yCoord + 4D, this.zCoord + 0.5D, type);
		debris.motionX = this.worldObj.rand.nextGaussian() * 0.75D;
		debris.motionZ = this.worldObj.rand.nextGaussian() * 0.75D;
		debris.motionY = 0.01D + this.worldObj.rand.nextDouble() * 1.25D;

		if(type == DebrisType.CONCRETE) {
			debris.motionX *= 0.25D;
			debris.motionY += this.worldObj.rand.nextDouble();
			debris.motionZ *= 0.25D;
		}

		if(type == DebrisType.EXCHANGER) {
			debris.motionX += 0.5D;
			debris.motionY *= 0.1D;
			debris.motionZ += 0.5D;
		}

		this.worldObj.spawnEntityInWorld(debris);
	}

	private void zirnoxDebris() {
		
		for(int i = 0; i < 2; i++) {
			spawnDebris(DebrisType.EXCHANGER);
		}
		
		for(int i = 0; i < 20; i++) {
			spawnDebris(DebrisType.CONCRETE);
			spawnDebris(DebrisType.BLANK);
		}
		
		for(int i = 0; i < 10; i++) {
			spawnDebris(DebrisType.ELEMENT);
			spawnDebris(DebrisType.GRAPHITE);
			spawnDebris(DebrisType.SHRAPNEL);
		}

	}

	private void meltdown() {

		for(int i = 0; i < this.slots.length; i++) {
			this.slots[i] = null;
		}

		int[] dimensions = {1, 0, 2, 2, 2, 2,};
		this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, ModBlocks.zirnox_destroyed, getBlockMetadata(), 3);
		MultiblockHandlerXR.fillSpace(this.worldObj, this.xCoord, this.yCoord, this.zCoord, dimensions, ModBlocks.zirnox_destroyed, ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset));
		this.worldObj.playSoundEffect(this.xCoord, this.yCoord + 2, this.zCoord, "hbm:block.rbmk_explosion", 10.0F, 1.0F);
		this.worldObj.createExplosion(null, this.xCoord, this.yCoord + 3, this.zCoord, 12.0F, true);
		zirnoxDebris();
		ExplosionNukeGeneric.waste(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 35);
		
		List<EntityPlayer> players = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class,
				AxisAlignedBB.getBoundingBox(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5).expand(100, 100, 100));
		
		for(EntityPlayer player : players) {
			player.triggerAchievement(MainRegistry.achZIRNOXBoom);
		}
		
		if(MobConfig.enableElementals) {
			for(EntityPlayer player : players) {
				player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).setBoolean("radMark", true);
			}
		}
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, this.worldObj, type);
	}

	@Override
	public void fillFluidInit(FluidType type) {
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		fillFluid(this.xCoord + rot.offsetX * 3, this.yCoord + 1, this.zCoord + rot.offsetZ * 3, getTact(), type);
		fillFluid(this.xCoord + rot.offsetX * 3, this.yCoord + 3, this.zCoord + rot.offsetZ * 3, getTact(), type);

		fillFluid(this.xCoord + rot.offsetX * -3, this.yCoord + 1, this.zCoord + rot.offsetZ * -3, getTact(), type);
		fillFluid(this.xCoord + rot.offsetX * -3, this.yCoord + 3, this.zCoord + rot.offsetZ * -3, getTact(), type);
	}
	
	private void updateConnections() {
		for(DirPos pos : getConPos()) {
			trySubscribe(this.water.getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			trySubscribe(this.carbonDioxide.getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	private DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		return new DirPos[] {
				new DirPos(this.xCoord + rot.offsetX * 3, this.yCoord + 1, this.zCoord + rot.offsetZ * 3, rot),
				new DirPos(this.xCoord + rot.offsetX * 3, this.yCoord + 3, this.zCoord + rot.offsetZ * 3, rot),
				new DirPos(this.xCoord + rot.offsetX * -3, this.yCoord + 1, this.zCoord + rot.offsetZ * -3, rot.getOpposite()),
				new DirPos(this.xCoord + rot.offsetX * -3, this.yCoord + 3, this.zCoord + rot.offsetZ * -3, rot.getOpposite())
		};
	}

	@Override
	public boolean getTact() {
		if(this.age >= 0 && this.age < 10) {
			return true;
		}

		return false;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type == Fluids.SUPERHOTSTEAM) return 0;
		if(type == Fluids.CARBONDIOXIDE) return this.carbonDioxide.getMaxFill();
		if(type == Fluids.WATER) return this.water.getMaxFill();
		
		return 0;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type == Fluids.SUPERHOTSTEAM) this.steam.setFill(i);
		if(type == Fluids.CARBONDIOXIDE) this.carbonDioxide.setFill(i);
		if(type == Fluids.WATER) this.water.setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type == Fluids.SUPERHOTSTEAM) return this.steam.getFill();
		if(type == Fluids.CARBONDIOXIDE) return this.carbonDioxide.getFill();
		if(type == Fluids.WATER) return this.water.getFill();
		return 0;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		switch (index) {
		case 0: this.steam.setFill(fill);
			break;
		case 1: this.carbonDioxide.setFill(fill);
			break;
		case 2: this.water.setFill(fill);
			break;
		default: break;
		}
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		switch (index) {
		case 0: this.steam.setTankType(type);
			break;
		case 1: this.carbonDioxide.setTankType(type);
			break;
		case 2: this.water.setTankType(type);
			break;
		default: break;
		}
	}

	public List<FluidTank> getTanks() {
		List<FluidTank> list = new ArrayList<>();
		list.add(this.steam);
		list.add(this.carbonDioxide);
		list.add(this.water);

		return list;
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return this.list;
	}

	@Override
	public void clearFluidList(FluidType type) {
		this.list.clear();
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(this.xCoord - 2, this.yCoord, this.zCoord - 2, this.xCoord + 3, this.yCoord + 5, this.zCoord + 3);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(this.xCoord - player.posX, this.yCoord - player.posY, this.zCoord - player.posZ).lengthVector() < 20;
	}
	
	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("control")) {
			this.isOn = !this.isOn;
		}
		
		if(data.hasKey("vent")) {
			int fill = this.carbonDioxide.getFill();
			this.carbonDioxide.setFill(fill - 1000);
			if(this.carbonDioxide.getFill() < 0)
				this.carbonDioxide.setFill(0);
		}
		
		markDirty();
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { this.steam };
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { this.water, this.carbonDioxide };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { this.water, this.steam, this.carbonDioxide };
	}
  
	// do some opencomputer stuff
	@Override
	public String getComponentName() {
		return "zirnox_reactor";
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getTemp(Context context, Arguments args) {
		return new Object[] {this.heat};
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getPressure(Context context, Arguments args) {
		return new Object[] {this.pressure};
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getWater(Context context, Arguments args) {
		return new Object[] {this.water.getFill()};
	}
	
	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getSteam(Context context, Arguments args) {
		return new Object[] {this.steam.getFill()};
	}	

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCarbonDioxide(Context context, Arguments args) {
		return new Object[] {this.carbonDioxide.getFill()};
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] isActive(Context context, Arguments args) {
		return new Object[] {this.isOn};
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		return new Object[] {this.heat, this.pressure, this.water.getFill(), this.steam.getFill(), this.carbonDioxide.getFill(), this.isOn};
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] setActive(Context context, Arguments args) {
		this.isOn = args.checkBoolean(0);
		return new Object[] {};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerReactorZirnox(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIReactorZirnox(player.inventory, this);
	}
}
