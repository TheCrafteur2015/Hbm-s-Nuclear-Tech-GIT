package com.hbm.tileentity.machine.rbmk;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.projectile.EntityRBMKDebris.DebrisType;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.container.ContainerRBMKGeneric;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIRBMKBoiler;
import com.hbm.lib.Library;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import api.hbm.fluid.IFluidUser;
import api.hbm.fluid.IPipeNet;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;


@SuppressWarnings("deprecation")
@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityRBMKBoiler extends TileEntityRBMKSlottedBase implements IFluidAcceptor, IFluidSource, IControlReceiver, IFluidStandardTransceiver, SimpleComponent {
	
	public FluidTank feed;
	public FluidTank steam;
	public List<IFluidAcceptor> list = new ArrayList<>();
	
	public TileEntityRBMKBoiler() {
		super(0);

		this.feed = new FluidTank(Fluids.WATER, 10000);
		this.steam = new FluidTank(Fluids.STEAM, 1000000);
	}

	@Override
	public String getName() {
		return "container.rbmkBoiler";
	}
	
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			this.feed.updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
			this.steam.updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
			
			double heatCap = getHeatFromSteam(this.steam.getTankType());
			double heatProvided = this.heat - heatCap;
			
			if(heatProvided > 0) {
				double HEAT_PER_MB_WATER = RBMKDials.getBoilerHeatConsumption(this.worldObj);
				double steamFactor = TileEntityRBMKBoiler.getFactorFromSteam(this.steam.getTankType());
				int waterUsed;
				int steamProduced;
				
				if(this.steam.getTankType() == Fluids.ULTRAHOTSTEAM) {
					steamProduced = (int)Math.floor((heatProvided / HEAT_PER_MB_WATER) * 100D / steamFactor);
					waterUsed = (int)Math.floor(steamProduced / 100D * steamFactor);
					
					if(this.feed.getFill() < waterUsed) {
						steamProduced = (int)Math.floor(this.feed.getFill() * 100D / steamFactor);
						waterUsed = (int)Math.floor(steamProduced / 100D * steamFactor);
					}
				} else {
					waterUsed = (int)Math.floor(heatProvided / HEAT_PER_MB_WATER);
					waterUsed = Math.min(waterUsed, this.feed.getFill());
					steamProduced = (int)Math.floor((waterUsed * 100D) / steamFactor);
				}
				
				this.feed.setFill(this.feed.getFill() - waterUsed);
				this.steam.setFill(this.steam.getFill() + steamProduced);
				
				if(this.steam.getFill() > this.steam.getMaxFill())
					this.steam.setFill(this.steam.getMaxFill());
				
				this.heat -= waterUsed * HEAT_PER_MB_WATER;
			}
			
			fillFluidInit(this.steam.getTankType());
			
			trySubscribe(this.feed.getTankType(), this.worldObj, this.xCoord, this.yCoord - 1, this.zCoord, Library.NEG_Y);
			for(DirPos pos : getOutputPos()) {
				if(this.steam.getFill() > 0) this.sendFluid(this.steam, this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
		}
		
		super.updateEntity();
	}
	
	public static double getHeatFromSteam(FluidType type) {
		if(type == Fluids.STEAM) return 100D;
		if(type == Fluids.HOTSTEAM) return 300D;
		if(type == Fluids.SUPERHOTSTEAM) return 450D;
		if(type == Fluids.ULTRAHOTSTEAM) return 600D;
		return 0D;
	}
	
	public static double getFactorFromSteam(FluidType type) {
		if(type == Fluids.STEAM) return 1D;
		if(type == Fluids.HOTSTEAM) return 10D;
		if(type == Fluids.SUPERHOTSTEAM) return 100D;
		if(type == Fluids.ULTRAHOTSTEAM) return 1000D;
		return 0D;
	}

	@Override
	public void fillFluidInit(FluidType type) {

		fillFluid(this.xCoord, this.yCoord + RBMKDials.getColumnHeight(this.worldObj) + 1, this.zCoord, getTact(), type);
		
		if(this.worldObj.getBlock(this.xCoord, this.yCoord - 1, this.zCoord) == ModBlocks.rbmk_loader) {

			fillFluid(this.xCoord + 1, this.yCoord - 1, this.zCoord, getTact(), type);
			fillFluid(this.xCoord - 1, this.yCoord - 1, this.zCoord, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 1, this.zCoord + 1, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 1, this.zCoord - 1, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 2, this.zCoord, getTact(), type);
		}
		
		if(this.worldObj.getBlock(this.xCoord, this.yCoord - 2, this.zCoord) == ModBlocks.rbmk_loader) {

			fillFluid(this.xCoord + 1, this.yCoord - 2, this.zCoord, getTact(), type);
			fillFluid(this.xCoord - 1, this.yCoord - 2, this.zCoord, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 2, this.zCoord + 1, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 2, this.zCoord - 1, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 1, this.zCoord, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 3, this.zCoord, getTact(), type);
		}
	}
	
	protected DirPos[] getOutputPos() {
		
		if(this.worldObj.getBlock(this.xCoord, this.yCoord - 1, this.zCoord) == ModBlocks.rbmk_loader) {
			return new DirPos[] {
					new DirPos(this.xCoord, this.yCoord + RBMKDials.getColumnHeight(this.worldObj) + 1, this.zCoord, Library.POS_Y),
					new DirPos(this.xCoord + 1, this.yCoord - 1, this.zCoord, Library.POS_X),
					new DirPos(this.xCoord - 1, this.yCoord - 1, this.zCoord, Library.NEG_X),
					new DirPos(this.xCoord, this.yCoord - 1, this.zCoord + 1, Library.POS_Z),
					new DirPos(this.xCoord, this.yCoord - 1, this.zCoord - 1, Library.NEG_Z),
					new DirPos(this.xCoord, this.yCoord - 2, this.zCoord, Library.NEG_Y)
			};
		} else if(this.worldObj.getBlock(this.xCoord, this.yCoord - 2, this.zCoord) == ModBlocks.rbmk_loader) {
			return new DirPos[] {
					new DirPos(this.xCoord, this.yCoord + RBMKDials.getColumnHeight(this.worldObj) + 1, this.zCoord, Library.POS_Y),
					new DirPos(this.xCoord + 1, this.yCoord - 2, this.zCoord, Library.POS_X),
					new DirPos(this.xCoord - 1, this.yCoord - 2, this.zCoord, Library.NEG_X),
					new DirPos(this.xCoord, this.yCoord - 2, this.zCoord + 1, Library.POS_Z),
					new DirPos(this.xCoord, this.yCoord - 2, this.zCoord - 1, Library.NEG_Z),
					new DirPos(this.xCoord, this.yCoord - 3, this.zCoord, Library.NEG_Y)
			};
		} else {
			return new DirPos[] {
					new DirPos(this.xCoord, this.yCoord + RBMKDials.getColumnHeight(this.worldObj) + 1, this.zCoord, Library.POS_Y)
			};
		}
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, this.worldObj, type);
	}
	
	@Override
	@Deprecated //why are we still doing this?
	public boolean getTact() { return this.worldObj.getTotalWorldTime() % 2 == 0; }

	@Override
	public void setFluidFill(int i, FluidType type) {
		
		if(type == this.feed.getTankType())
			this.feed.setFill(i);
		else if(type == this.steam.getTankType())
			this.steam.setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		
		if(type == this.feed.getTankType())
			return this.feed.getFill();
		else if(type == this.steam.getTankType())
			return this.steam.getFill();
		
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		
		if(type == this.feed.getTankType())
			return this.feed.getMaxFill();
		else if(type == this.steam.getTankType())
			return this.steam.getMaxFill();
		
		return 0;
	}

	@Override
	public void setFillForSync(int fill, int index) {

		if(index == 0)
			this.feed.setFill(fill);
		else if(index == 1)
			this.steam.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {

		if(index == 0)
			this.feed.setTankType(type);
		else if(index == 1)
			this.steam.setTankType(type);
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
		
		this.feed.readFromNBT(nbt, "feed");
		this.steam.readFromNBT(nbt, "steam");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		this.feed.writeToNBT(nbt, "feed");
		this.steam.writeToNBT(nbt, "steam");
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(this.xCoord - player.posX, this.yCoord - player.posY, this.zCoord - player.posZ).lengthVector() < 20;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		
		if(data.hasKey("compression")) {
			
			FluidType type = this.steam.getTankType();
			if(type == Fluids.STEAM) {			this.steam.setTankType(Fluids.HOTSTEAM);			this.steam.setFill(this.steam.getFill() / 10); }
			if(type == Fluids.HOTSTEAM) {		this.steam.setTankType(Fluids.SUPERHOTSTEAM);	this.steam.setFill(this.steam.getFill() / 10); }
			if(type == Fluids.SUPERHOTSTEAM) {	this.steam.setTankType(Fluids.ULTRAHOTSTEAM);	this.steam.setFill(this.steam.getFill() / 10); }
			if(type == Fluids.ULTRAHOTSTEAM) {	this.steam.setTankType(Fluids.STEAM);			this.steam.setFill(Math.min(this.steam.getFill() * 1000, this.steam.getMaxFill())); }
			
			markDirty();
		}
	}
	
	@Override
	public void onMelt(int reduce) {
		
		int count = 1 + this.worldObj.rand.nextInt(2);
		
		for(int i = 0; i < count; i++) {
			spawnDebris(DebrisType.BLANK);
		}
		
		if(RBMKDials.getOverpressure(this.worldObj)) {
			for(DirPos pos : getOutputPos()) {
				IPipeNet net = IFluidUser.getPipeNet(this.worldObj, pos.getX(), pos.getY(), pos.getZ(), this.steam.getTankType());
				if(net != null) {
					TileEntityRBMKBase.pipes.add(net);
				}
			}
		}
		
		super.onMelt(reduce);
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.BOILER;
	}

	@Override
	public NBTTagCompound getNBTForConsole() {
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("water", this.feed.getFill());
		data.setInteger("maxWater", this.feed.getMaxFill());
		data.setInteger("steam", this.steam.getFill());
		data.setInteger("maxSteam", this.steam.getMaxFill());
		data.setShort("type", (short)this.steam.getTankType().getID());
		return data;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {this.feed, this.steam};
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {this.steam};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {this.feed};
	}
	
	// do some opencomputer stuff
	@Override
	public String getComponentName() {
		return "rbmk_boiler";
	}
	
	@Callback(direct = true, limit = 16)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getHeat(Context context, Arguments args) {
		return new Object[] {this.heat};
	}
	
	@Callback(direct = true, limit = 16)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getSteam(Context context, Arguments args) {
		return new Object[] {this.steam.getFill()};
	}
	@Callback(direct = true, limit = 16)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getSteamMax(Context context, Arguments args) {
		return new Object[] {this.steam.getMaxFill()};
	}
	
	@Callback(direct = true, limit = 16)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getWater(Context context, Arguments args) {
		return new Object[] {this.feed.getFill()};
	}
	@Callback(direct = true, limit = 16)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getWaterMax(Context context, Arguments args) {
		return new Object[] {this.feed.getMaxFill()};
	}

	@Callback(direct = true, limit = 16)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCoordinates(Context context, Arguments args) {
		return new Object[] {this.xCoord, this.yCoord, this.zCoord};
	}

	@Callback(direct = true, limit = 16)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		FluidType type = this.steam.getTankType();
		Object type_1;
		if(type == Fluids.STEAM) {type_1 = "0";}
		else if(type == Fluids.HOTSTEAM) {type_1 = "1";}
		else if(type == Fluids.SUPERHOTSTEAM) {type_1 = "2";}
		else if(type == Fluids.ULTRAHOTSTEAM) {type_1 = "3";}
		else {type_1 = "Unknown Error";}
		return new Object[] {this.heat, this.steam.getFill(), this.steam.getMaxFill(), this.feed.getFill(), this.feed.getMaxFill(), type_1, this.xCoord, this.yCoord, this.zCoord};
	}

	@Callback(direct = true, limit = 16)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getSteamType(Context context, Arguments args) {
		FluidType type = this.steam.getTankType();
		if(type == Fluids.STEAM) {return new Object[] {0};}
		else if(type == Fluids.HOTSTEAM) {return new Object[] {1};}
		else if(type == Fluids.SUPERHOTSTEAM) {return new Object[] {2};}
		else if(type == Fluids.ULTRAHOTSTEAM) {return new Object[] {3};}
		else {return new Object[] {"Unknown Error"};}
	}

	@Callback(direct = true, limit = 16)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setSteamType(Context context, Arguments args) {
		int type = args.checkInteger(0);
		if(type > 3) {
			type = 3;
		} else if(type < 0) {
			type = 0;
		}
		if(type == 0) {
			this.steam.setTankType(Fluids.STEAM);
			return new Object[] {true};
		} else if(type == 1) {
			this.steam.setTankType(Fluids.HOTSTEAM);
			return new Object[] {true};
		} else if(type == 2) {
			this.steam.setTankType(Fluids.SUPERHOTSTEAM);
			return new Object[] {true};
		} else {
			this.steam.setTankType(Fluids.ULTRAHOTSTEAM);
			return new Object[] {true};
		}
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerRBMKGeneric(player.inventory);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIRBMKBoiler(player.inventory, this);
	}
}
