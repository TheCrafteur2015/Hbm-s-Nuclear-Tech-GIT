package com.hbm.tileentity.machine.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.container.ContainerMachineFluidTank;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Corrosive;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Amat;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Gaseous;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Gaseous_ART;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Leaded;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Liquid;
import com.hbm.inventory.gui.GUIMachineFluidTank;
import com.hbm.lib.Library;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IOverpressurable;
import com.hbm.tileentity.IPersistentNBT;
import com.hbm.tileentity.IRepairable;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.ParticleUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@SuppressWarnings("deprecation")
@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")})
public class TileEntityMachineFluidTank extends TileEntityMachineBase implements IFluidContainer, SimpleComponent, IFluidSource, IFluidAcceptor, IFluidStandardTransceiver, IPersistentNBT, IOverpressurable, IGUIProvider, IRepairable {
	
	public FluidTank tank;
	public short mode = 0;
	public static final short modes = 4;
	public boolean hasExploded = false;
	protected boolean sendingBrake = false;
	public boolean onFire = false;
	public byte lastRedstone = 0;
	public Explosion lastExplosion = null;
	
	public int age = 0;
	public List<IFluidAcceptor> list = new ArrayList<>();
	
	public TileEntityMachineFluidTank() {
		super(6);
		this.tank = new FluidTank(Fluids.NONE, 256000);
	}

	@Override
	public String getName() {
		return "container.fluidtank";
	}

	public byte getComparatorPower() {
		if(this.tank.getFill() == 0) return 0;
		double frac = (double) this.tank.getFill() / (double) this.tank.getMaxFill() * 15D;
		return (byte) (MathHelper.clamp_int((int) frac + 1, 0, 15));
	}

	@Override
	public void updateEntity() {

		if(!this.worldObj.isRemote) {
			
			//meta below 12 means that it's an old multiblock configuration
			if(getBlockMetadata() < 12) {
				//get old direction
				ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata()).getRotation(ForgeDirection.DOWN);
				//remove tile from the world to prevent inventory dropping
				this.worldObj.removeTileEntity(this.xCoord, this.yCoord, this.zCoord);
				//use fillspace to create a new multiblock configuration
				this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, ModBlocks.machine_fluidtank, dir.ordinal() + 10, 3);
				MultiblockHandlerXR.fillSpace(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ((BlockDummyable) ModBlocks.machine_fluidtank).getDimensions(), ModBlocks.machine_fluidtank, dir);
				//load the tile data to restore the old values
				NBTTagCompound data = new NBTTagCompound();
				writeToNBT(data);
				this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord).readFromNBT(data);
				return;
			}
			
			if(!this.hasExploded) {
				this.age++;
				
				if(this.age >= 20) {
					this.age = 0;
					markChanged();
				}
				
				this.sendingBrake = true;
				this.tank.setFill(TileEntityBarrel.transmitFluidFairly(this.worldObj, this.tank, this, this.tank.getFill(), this.mode == 0 || this.mode == 1, this.mode == 1 || this.mode == 2, getConPos()));
				this.sendingBrake = false;
				
				if((this.mode == 1 || this.mode == 2) && (this.age == 9 || this.age == 19))
					fillFluidInit(this.tank.getTankType());
				
				this.tank.loadTank(2, 3, this.slots);
				this.tank.setType(0, 1, this.slots);
			}

			byte comp = getComparatorPower(); //comparator shit
			if(comp != this.lastRedstone)
				markDirty();
			this.lastRedstone = comp;

			if(this.tank.getFill() > 0) {
				if(this.tank.getTankType().isAntimatter()) {
					new ExplosionVNT(this.worldObj, this.xCoord + 0.5, this.yCoord + 1.5, this.zCoord + 0.5, 5F).makeAmat().setBlockAllocator(null).setBlockProcessor(null).explode();
					this.explode();
					this.tank.setFill(0);
				}
				
				if(this.tank.getTankType().hasTrait(FT_Corrosive.class) && this.tank.getTankType().getTrait(FT_Corrosive.class).isHighlyCorrosive()) {
					this.explode();
				}
				
				if(this.hasExploded) {

					int leaking = 0;
					if(this.tank.getTankType().isAntimatter()) {
						leaking = this.tank.getFill();
					} else if(this.tank.getTankType().hasTrait(FT_Gaseous.class) || this.tank.getTankType().hasTrait(FT_Gaseous_ART.class)) {
						leaking = Math.min(this.tank.getFill(), this.tank.getMaxFill() / 100);
					} else {
						leaking = Math.min(this.tank.getFill(), this.tank.getMaxFill() / 10000);
					}
					
					updateLeak(leaking);
				}
			}
			
			this.tank.unloadTank(4, 5, this.slots);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setShort("mode", this.mode);
			data.setBoolean("hasExploded", this.hasExploded);
			this.tank.writeToNBT(data, "t");
			networkPack(data, 150);
		}
	}
	
	/** called when the tank breaks due to hazardous materials or external force, can be used to quickly void part of the tank or spawn a mushroom cloud */
	public void explode() {
		this.hasExploded = true;
		this.onFire = this.tank.getTankType().hasTrait(FT_Flammable.class);
		markChanged();
	}
	
	/** called every tick post explosion, used for leaking fluid and spawning particles */
	
	public void updateLeak(int amount) {
		if(!this.hasExploded || (amount <= 0)) return;
		
		this.tank.getTankType().onFluidRelease(this, this.tank, amount);
		this.tank.setFill(Math.max(0, this.tank.getFill() - amount));
		
		FluidType type = this.tank.getTankType();
		
		if(type.hasTrait(FT_Amat.class)) {
			new ExplosionVNT(this.worldObj, this.xCoord + 0.5, this.yCoord + 1.5, this.zCoord + 0.5, 5F).makeAmat().setBlockAllocator(null).setBlockProcessor(null).explode();
			
		} else if(type.hasTrait(FT_Flammable.class) && this.onFire) {
			List<Entity> affected = this.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(this.xCoord - 1.5, this.yCoord, this.zCoord - 1.5, this.xCoord + 2.5, this.yCoord + 5, this.zCoord + 2.5));
			for(Entity e : affected) e.setFire(5);
			Random rand = this.worldObj.rand;
			ParticleUtil.spawnGasFlame(this.worldObj, this.xCoord + rand.nextDouble(), this.yCoord + 0.5 + rand.nextDouble(), this.zCoord + rand.nextDouble(), rand.nextGaussian() * 0.2, 0.1, rand.nextGaussian() * 0.2);
			
			if(this.worldObj.getTotalWorldTime() % 20 == 0) {
				PollutionHandler.incrementPollution(this.worldObj, this.xCoord, this.yCoord, this.zCoord, PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND * 50);
				if(type.hasTrait(FT_Leaded.class)) PollutionHandler.incrementPollution(this.worldObj, this.xCoord, this.yCoord, this.zCoord, PollutionType.HEAVYMETAL, PollutionHandler.HEAVY_METAL_PER_SECOND * 50);
			}
			
		} else if(type.hasTrait(FT_Gaseous.class) || type.hasTrait(FT_Gaseous_ART.class)) {
			
			if(this.worldObj.getTotalWorldTime() % 5 == 0) {
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "tower");
				data.setFloat("lift", 1F);
				data.setFloat("base", 1F);
				data.setFloat("max", 5F);
				data.setInteger("life", 100 + this.worldObj.rand.nextInt(20));
				data.setInteger("color", this.tank.getTankType().getColor());
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, this.xCoord + 0.5, this.yCoord + 1, this.zCoord + 0.5), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 150));
			}
		}
	}

	@Override
	public void explode(World world, int x, int y, int z) {
		
		if(this.hasExploded) return;
		this.onFire = this.tank.getTankType().hasTrait(FT_Flammable.class);
		this.hasExploded = true;
		markChanged();
	}

	@Override
	public void tryExtinguish(World world, int x, int y, int z, EnumExtinguishType type) {
		if(!this.hasExploded || !this.onFire) return;
		
		if(type == EnumExtinguishType.WATER) {
			if(this.tank.getTankType().hasTrait(FT_Liquid.class)) { // extinguishing oil with water is a terrible idea!
				this.worldObj.newExplosion(null, this.xCoord + 0.5, this.yCoord + 1.5, this.zCoord + 0.5, 5F, true, true);
			} else {
				this.onFire = false;
				markChanged();
				return;
			}
		}
		
		if(type == EnumExtinguishType.FOAM || type == EnumExtinguishType.CO2) {
			this.onFire = false;
			markChanged();
		}
	}
	
	protected DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(this.xCoord + 2, this.yCoord, this.zCoord - 1, Library.POS_X),
				new DirPos(this.xCoord + 2, this.yCoord, this.zCoord + 1, Library.POS_X),
				new DirPos(this.xCoord - 2, this.yCoord, this.zCoord - 1, Library.NEG_X),
				new DirPos(this.xCoord - 2, this.yCoord, this.zCoord + 1, Library.NEG_X),
				new DirPos(this.xCoord - 1, this.yCoord, this.zCoord + 2, Library.POS_Z),
				new DirPos(this.xCoord + 1, this.yCoord, this.zCoord + 2, Library.POS_Z),
				new DirPos(this.xCoord - 1, this.yCoord, this.zCoord - 2, Library.NEG_Z),
				new DirPos(this.xCoord + 1, this.yCoord, this.zCoord - 2, Library.NEG_Z)
		};
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.mode = data.getShort("mode");
		this.hasExploded = data.getBoolean("hasExploded");
		this.tank.readFromNBT(data, "t");
	}
	
	@Override
	public void handleButtonPacket(int value, int meta) {
		this.mode = (short) ((this.mode + 1) % TileEntityMachineFluidTank.modes);
		markChanged();
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(
					this.xCoord - 2,
					this.yCoord,
					this.zCoord - 2,
					this.xCoord + 3,
					this.yCoord + 3,
					this.zCoord + 3
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
	public void setFillForSync(int fill, int index) {
		this.tank.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		this.tank.setTankType(type);
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		
		if(this.mode == 2 || this.mode == 3 || this.sendingBrake)
			return 0;
		
		return type.getName().equals(this.tank.getTankType().getName()) ? this.tank.getMaxFill() : 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(this.xCoord + 2, this.yCoord, this.zCoord - 1, getTact(), type);
		fillFluid(this.xCoord + 2, this.yCoord, this.zCoord + 1, getTact(), type);
		fillFluid(this.xCoord - 2, this.yCoord, this.zCoord - 1, getTact(), type);
		fillFluid(this.xCoord - 2, this.yCoord, this.zCoord + 1, getTact(), type);
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord + 2, getTact(), type);
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord + 2, getTact(), type);
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord - 2, getTact(), type);
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord - 2, getTact(), type);
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
		return type.getName().equals(this.tank.getTankType().getName()) ? this.tank.getFill() : 0;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type.getName().equals(this.tank.getTankType().getName()))
			this.tank.setFill(i);
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
		this.hasExploded = nbt.getBoolean("exploded");
		this.onFire = nbt.getBoolean("onFire");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setShort("mode", this.mode);
		this.tank.writeToNBT(nbt, "tank");
		nbt.setBoolean("exploded", this.hasExploded);
		nbt.setBoolean("onFire", this.onFire);
	}

	@Override
	public long transferFluid(FluidType type, int pressure, long fluid) {
		long toTransfer = Math.min(getDemand(type, pressure), fluid);
		this.tank.setFill(this.tank.getFill() + (int) toTransfer);
		return fluid - toTransfer;
	}

	@Override
	public long getDemand(FluidType type, int pressure) {
		
		if(this.mode == 2 || this.mode == 3 || this.sendingBrake || (this.tank.getPressure() != pressure)) return 0;
		
		return type == this.tank.getTankType() ? this.tank.getMaxFill() - this.tank.getFill() : 0;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { this.tank };
	}

	@Override
	public void writeNBT(NBTTagCompound nbt) {
		if(this.tank.getFill() == 0 && !this.hasExploded) return;
		NBTTagCompound data = new NBTTagCompound();
		this.tank.writeToNBT(data, "tank");
		data.setShort("mode", this.mode);
		data.setBoolean("hasExploded", this.hasExploded);
		data.setBoolean("onFire", this.onFire);
		nbt.setTag(IPersistentNBT.NBT_PERSISTENT_KEY, data);
	}

	@Override
	public void readNBT(NBTTagCompound nbt) {
		NBTTagCompound data = nbt.getCompoundTag(IPersistentNBT.NBT_PERSISTENT_KEY);
		this.tank.readFromNBT(data, "tank");
		this.mode = data.getShort("mode");
		this.hasExploded = data.getBoolean("hasExploded");
		this.onFire = data.getBoolean("onFire");
	}

	@Override
	public FluidTank[] getSendingTanks() {
		if(this.hasExploded) return new FluidTank[0];
		return (this.mode == 1 || this.mode == 2) ? new FluidTank[] {this.tank} : new FluidTank[0];
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		if(this.hasExploded || this.sendingBrake) return new FluidTank[0];
		return (this.mode == 0 || this.mode == 1) ? new FluidTank[] {this.tank} : new FluidTank[0];
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineFluidTank(player.inventory, (TileEntityMachineFluidTank) world.getTileEntity(x, y, z));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineFluidTank(player.inventory, (TileEntityMachineFluidTank) world.getTileEntity(x, y, z));
	}

	@Override
	public boolean isDamaged() {
		return this.hasExploded;
	}
	
	List<AStack> repair = new ArrayList<>();
	@Override
	public List<AStack> getRepairMaterials() {
		
		if(!this.repair.isEmpty())
			return this.repair;
		
		this.repair.add(new OreDictStack(OreDictManager.STEEL.plate(), 6));
		return this.repair;
	}

	@Override
	public void repair() {
		this.hasExploded = false;
		markChanged();
	}

	@Override
	public String getComponentName() {
		return "ntm_tank";
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