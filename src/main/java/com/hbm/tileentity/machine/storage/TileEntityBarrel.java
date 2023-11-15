package com.hbm.tileentity.machine.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidContainerRegistry;
import com.hbm.inventory.container.ContainerBarrel;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Corrosive;
import com.hbm.inventory.gui.GUIBarrel;
import com.hbm.lib.Library;
import com.hbm.saveddata.TomSaveData;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IPersistentNBT;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidConductor;
import api.hbm.fluid.IFluidConnector;
import api.hbm.fluid.IFluidStandardTransceiver;
import api.hbm.fluid.IPipeNet;
import api.hbm.fluid.PipeNet;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")})
public class TileEntityBarrel extends TileEntityMachineBase implements IFluidAcceptor, IFluidSource, SimpleComponent, IFluidStandardTransceiver, IPersistentNBT, IGUIProvider {
	
	public FluidTank tank;
	public short mode = 0;
	public static final short modes = 4;
	public int age = 0;
	public List<IFluidAcceptor> list = new ArrayList<>();
	protected boolean sendingBrake = false;
	public byte lastRedstone = 0;

	public TileEntityBarrel() {
		this(0);
	}

	public TileEntityBarrel(int capacity) {
		super(6);
		this.tank = new FluidTank(Fluids.NONE, capacity);
	}

	@Override
	public String getName() {
		return "container.barrel";
	}

	public byte getComparatorPower() {
		if(this.tank.getFill() == 0) return 0;
		double frac = (double) this.tank.getFill() / (double) this.tank.getMaxFill() * 15D;
		return (byte) (MathHelper.clamp_int((int) frac + 1, 0, 15));
	}

	@Override
	public long getDemand(FluidType type, int pressure) {
		
		if(this.mode == 2 || this.mode == 3 || this.sendingBrake || (this.tank.getPressure() != pressure)) return 0;
		
		return type == this.tank.getTankType() ? this.tank.getMaxFill() - this.tank.getFill() : 0;
	}

	@Override
	public long transferFluid(FluidType type, int pressure, long fluid) {
		long toTransfer = Math.min(getDemand(type, pressure), fluid);
		this.tank.setFill(this.tank.getFill() + (int) toTransfer);
		return fluid - toTransfer;
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {

			byte comp = getComparatorPower(); //do comparator shenanigans
			if(comp != this.lastRedstone)
				markDirty();
			this.lastRedstone = comp;

			this.tank.setType(0, 1, this.slots);
			this.tank.loadTank(2, 3, this.slots);
			this.tank.unloadTank(4, 5, this.slots);
			this.tank.updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
			
			this.sendingBrake = true;
			this.tank.setFill(TileEntityBarrel.transmitFluidFairly(this.worldObj, this.tank, this, this.tank.getFill(), this.mode == 0 || this.mode == 1, this.mode == 1 || this.mode == 2, getConPos()));
			this.sendingBrake = false;
			
			this.age++;
			if(this.age >= 20) {
				this.age = 0;
				markChanged();
			}
			
			if((this.mode == 1 || this.mode == 2) && (this.age == 9 || this.age == 19))
				fillFluidInit(this.tank.getTankType());
			
			if(this.tank.getFill() > 0) {
				checkFluidInteraction();
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setShort("mode", this.mode);
			networkPack(data, 50);
		}
	}
	
	protected DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(this.xCoord + 1, this.yCoord, this.zCoord, Library.POS_X),
				new DirPos(this.xCoord - 1, this.yCoord, this.zCoord, Library.NEG_X),
				new DirPos(this.xCoord, this.yCoord + 1, this.zCoord, Library.POS_Y),
				new DirPos(this.xCoord, this.yCoord - 1, this.zCoord, Library.NEG_Y),
				new DirPos(this.xCoord, this.yCoord, this.zCoord + 1, Library.POS_Z),
				new DirPos(this.xCoord, this.yCoord, this.zCoord - 1, Library.NEG_Z)
		};
	}
	
	protected static int transmitFluidFairly(World world, FluidTank tank, IFluidConnector that, int fill, boolean connect, boolean send, DirPos[] connections) {
		
		Set<IPipeNet> nets = new HashSet<>();
		Set<IFluidConnector> consumers = new HashSet<>();
		FluidType type = tank.getTankType();
		int pressure = tank.getPressure();
		
		for(DirPos pos : connections) {
			
			TileEntity te = world.getTileEntity(pos.getX(), pos.getY(), pos.getZ());
			
			if(te instanceof IFluidConductor) {
				IFluidConductor con = (IFluidConductor) te;
				if(con.getPipeNet(type) != null) {
					nets.add(con.getPipeNet(type));
					con.getPipeNet(type).unsubscribe(that);
					consumers.addAll(con.getPipeNet(type).getSubscribers());
				}
				
			//if it's just a consumer, buffer it as a subscriber
			} else if(te instanceof IFluidConnector) {
				consumers.add((IFluidConnector) te);
			}
		}
		
		consumers.remove(that);

		if(fill > 0 && send) {
			List<IFluidConnector> con = new ArrayList<>();
			con.addAll(consumers);

			con.removeIf(x -> x == null || !(x instanceof TileEntity) || ((TileEntity)x).isInvalid());
			
			if(PipeNet.trackingInstances == null) {
				PipeNet.trackingInstances = new ArrayList<>();
			}
			
			PipeNet.trackingInstances.clear();
			nets.forEach(x -> {
				if(x instanceof PipeNet) PipeNet.trackingInstances.add((PipeNet) x);
			});
			
			fill = (int) PipeNet.fairTransfer(con, type, pressure, fill);
		}
		
		//resubscribe to buffered nets, if necessary
		if(connect) {
			nets.forEach(x -> x.subscribe(that));
		}
		
		return fill;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		ItemStack full = FluidContainerRegistry.getFullContainer(itemStack, this.tank.getTankType());
		//if fillable and the fill being possible for this tank size
		if(i == 4 && full != null && FluidContainerRegistry.getFluidContent(full, this.tank.getTankType()) <= this.tank.getMaxFill())
			return true;
		int content = FluidContainerRegistry.getFluidContent(itemStack, this.tank.getTankType());
		//if content is above 0 but still within capacity
		if(i == 2 && content > 0 && content <= this.tank.getMaxFill())
			return true;
		
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 3 || i == 5;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[] {2, 3, 4, 5};
	}
	
	public void checkFluidInteraction() {
		
		Block b = getBlockType();
		
		//for when you fill antimatter into a matter tank
		if(b != ModBlocks.barrel_antimatter && this.tank.getTankType().isAntimatter()) {
			this.worldObj.func_147480_a(this.xCoord, this.yCoord, this.zCoord, false);
			this.worldObj.newExplosion(null, this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, 5, true, true);
		}
		
		//for when you fill hot or corrosive liquids into a plastic tank
		if(b == ModBlocks.barrel_plastic && (this.tank.getTankType().isCorrosive() || this.tank.getTankType().isHot())) {
			this.worldObj.func_147480_a(this.xCoord, this.yCoord, this.zCoord, false);
			this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, "random.fizz", 1.0F, 1.0F);
		}
		
		//for when you fill corrosive liquid into an iron tank
		if((b == ModBlocks.barrel_iron && this.tank.getTankType().isCorrosive()) ||
				(b == ModBlocks.barrel_steel && this.tank.getTankType().hasTrait(FT_Corrosive.class) && this.tank.getTankType().getTrait(FT_Corrosive.class).getRating() > 50)) {
			ItemStack[] copy = this.slots.clone();
			this.slots = new ItemStack[6];
			this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, ModBlocks.barrel_corroded);
			TileEntityBarrel barrel = (TileEntityBarrel)this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord);
			
			if(barrel != null) {
				barrel.tank.setTankType(this.tank.getTankType());
				barrel.tank.setFill(Math.min(barrel.tank.getMaxFill(), this.tank.getFill()));
				barrel.slots = copy;
			}
			
			this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, "random.fizz", 1.0F, 1.0F);
		}
		
		if(b == ModBlocks.barrel_corroded && this.worldObj.rand.nextInt(3) == 0) {
			this.tank.setFill(this.tank.getFill() - 1);
		}
		
		//For when Tom's firestorm hits a barrel full of water
		if(this.tank.getTankType() == Fluids.WATER && TomSaveData.forWorld(this.worldObj).fire > 1e-5) {
			int light = this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, this.xCoord, this.yCoord, this.zCoord);
			
			if(light > 7) {
				this.worldObj.newExplosion(null, this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, 5, true, true);
			}
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.mode = data.getShort("mode");
	}

	@Override
	public void setFillForSync(int fill, int index) {
		this.tank.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		this.tank.setTankType(type);
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		
		if(this.mode == 2 || this.mode == 3)
			return 0;
		
		return type == this.tank.getTankType() ? this.tank.getMaxFill() : 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord + 1, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord - 1, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord + 1, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord - 1, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, this.worldObj, type);
	}

	@Override
	public boolean getTact() {
		if (this.age >= 0 && this.age < 10) {
			return true;
		}

		return false;
	}

	@Override
	public int getFluidFill(FluidType type) {
		return type == this.tank.getTankType() ? this.tank.getFill() : 0;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type == this.tank.getTankType()) this.tank.setFill(i);
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
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.mode = nbt.getShort("mode");
		this.tank.readFromNBT(nbt, "tank");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setShort("mode", this.mode);
		this.tank.writeToNBT(nbt, "tank");
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return (this.mode == 1 || this.mode == 2) ? new FluidTank[] {this.tank} : new FluidTank[0];
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return (this.mode == 0 || this.mode == 1) && !this.sendingBrake ? new FluidTank[] {this.tank} : new FluidTank[0];
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { this.tank };
	}

	@Override
	public void writeNBT(NBTTagCompound nbt) {
		if(this.tank.getFill() == 0) return;
		NBTTagCompound data = new NBTTagCompound();
		this.tank.writeToNBT(data, "tank");
		data.setShort("mode", this.mode);
		nbt.setTag(IPersistentNBT.NBT_PERSISTENT_KEY, data);
	}

	@Override
	public void readNBT(NBTTagCompound nbt) {
		NBTTagCompound data = nbt.getCompoundTag(IPersistentNBT.NBT_PERSISTENT_KEY);
		this.tank.readFromNBT(data, "tank");
		this.mode = data.getShort("nbt");
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerBarrel(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIBarrel(player.inventory, this);
	}

	@Override
	public String getComponentName() {
		return "ntm_fluid_tank";
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getFluidStored(Context context, Arguments args) {
		return new Object[] {this.tank.getFill()};
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getMaxStored(Context context, Arguments args) {
		return new Object[] {this.tank.getMaxFill()};
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getTypeStored(Context context, Arguments args) {
		return new Object[] {this.tank.getTankType().getName()};
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		return new Object[]{this.tank.getFill(), this.tank.getMaxFill(), this.tank.getTankType().getName()};
	}
}
