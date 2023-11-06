package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.inventory.container.ContainerCoreEmitter;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUICoreEmitter;
import com.hbm.lib.ModDamageSource;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.block.ILaserable;
import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityCoreEmitter extends TileEntityMachineBase implements IEnergyUser, ILaserable, IFluidStandardReceiver, SimpleComponent, IGUIProvider {
	
	public long power;
	public static final long maxPower = 1000000000L;
	public int watts;
	public int beam;
	public long joules;
	public boolean isOn;
	public FluidTank tank;
	public long prev;
	
	public static final int range = 50;

	public TileEntityCoreEmitter() {
		super(0);
		this.tank = new FluidTank(Fluids.CRYOGEL, 64000);
	}

	@Override
	public String getName() {
		return "container.dfcEmitter";
	}

	
	@Override
	public void updateEntity() {

		if (!this.worldObj.isRemote) {
			
			this.updateStandardConnections(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			this.subscribeToAllAround(this.tank.getTankType(), this);
			
			this.watts = MathHelper.clamp_int(this.watts, 1, 100);
			long demand = TileEntityCoreEmitter.maxPower * this.watts / 2000;
			
			this.beam = 0;
			
			if(this.joules > 0 || this.prev > 0) {

				if(this.tank.getFill() >= 20) {
					this.tank.setFill(this.tank.getFill() - 20);
				} else {
					this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Blocks.flowing_lava);
					return;
				}
			}
			
			if(this.isOn) {
				
				//i.e. 50,000,000 HE = 10,000 SPK
				//1 SPK = 5,000HE
				
				if(this.power >= demand) {
					this.power -= demand;
					long add = this.watts * 100;
					this.joules += add;
				}
				this.prev = this.joules;
				
				if(this.joules > 0) {
					
					long out = this.joules * 98 / 100;
					
					ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata());
					for(int i = 1; i <= TileEntityCoreEmitter.range; i++) {
						
						this.beam = i;
		
						int x = this.xCoord + dir.offsetX * i;
						int y = this.yCoord + dir.offsetY * i;
						int z = this.zCoord + dir.offsetZ * i;
						
						Block block = this.worldObj.getBlock(x, y, z);
						TileEntity te = this.worldObj.getTileEntity(x, y, z);
						
						if(block instanceof ILaserable) {
							((ILaserable)block).addEnergy(this.worldObj, x, y, z, out * 98 / 100, dir);
							break;
						}
						
						if(te instanceof ILaserable) {
							((ILaserable)te).addEnergy(this.worldObj, x, y, z, out * 98 / 100, dir);
							break;
						}
						
						if(te instanceof TileEntityCore) {
							out = ((TileEntityCore)te).burn(out);
							continue;
						}
						
						Block b = this.worldObj.getBlock(x, y, z);
						
						if(!b.isAir(this.worldObj, x, y, z)) {
							
							if(b.getMaterial().isLiquid()) {
								this.worldObj.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "random.fizz", 1.0F, 1.0F);
								this.worldObj.setBlockToAir(x, y, z);
								break;
							}
							
							float hardness = b.getExplosionResistance(null);
							if(hardness < 6000 && this.worldObj.rand.nextInt(20) == 0) {
								this.worldObj.func_147480_a(x, y, z, false);
							}
							
							break;
						}
					}
					
					
					this.joules = 0;
		
					double blx = Math.min(this.xCoord, this.xCoord + dir.offsetX * this.beam) + 0.2;
					double bux = Math.max(this.xCoord, this.xCoord + dir.offsetX * this.beam) + 0.8;
					double bly = Math.min(this.yCoord, this.yCoord + dir.offsetY * this.beam) + 0.2;
					double buy = Math.max(this.yCoord, this.yCoord + dir.offsetY * this.beam) + 0.8;
					double blz = Math.min(this.zCoord, this.zCoord + dir.offsetZ * this.beam) + 0.2;
					double buz = Math.max(this.zCoord, this.zCoord + dir.offsetZ * this.beam) + 0.8;
					
					List<Entity> list = this.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(blx, bly, blz, bux, buy, buz));
					
					for(Entity e : list) {
						e.attackEntityFrom(ModDamageSource.amsCore, 50);
						e.setFire(10);
					}
				}
			} else {
				this.joules = 0;
				this.prev = 0;
			}
			
			markDirty();

			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setInteger("watts", this.watts);
			data.setLong("prev", this.prev);
			data.setInteger("beam", this.beam);
			data.setBoolean("isOn", this.isOn);
			this.tank.writeToNBT(data, "tank");
			networkPack(data, 250);
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {

		this.power = data.getLong("power");
		this.watts = data.getInteger("watts");
		this.prev = data.getLong("prev");
		this.beam = data.getInteger("beam");
		this.isOn = data.getBoolean("isOn");
		this.tank.readFromNBT(data, "tank");
	}
	
	public long getPowerScaled(long i) {
		return (this.power * i) / TileEntityCoreEmitter.maxPower;
	}
	
	public int getWattsScaled(int i) {
		return (this.watts * i) / 100;
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
		return TileEntityCoreEmitter.maxPower;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN;
	}

	@Override
	public void addEnergy(World world, int x, int y, int z, long energy, ForgeDirection dir) {
		
		//do not accept lasers from the front
		if(dir.getOpposite().ordinal() != getBlockMetadata())
			this.joules += energy;
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
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.power = nbt.getLong("power");
		this.watts = nbt.getInteger("watts");
		this.joules = nbt.getLong("joules");
		this.prev = nbt.getLong("prev");
		this.isOn = nbt.getBoolean("isOn");
		this.tank.readFromNBT(nbt, "tank");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", this.power);
		nbt.setInteger("watts", this.watts);
		nbt.setLong("joules", this.joules);
		nbt.setLong("prev", this.prev);
		nbt.setBoolean("isOn", this.isOn);
		this.tank.writeToNBT(nbt, "tank");
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { this.tank };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { this.tank };
	}
	
	// do some opencomputer stuff
	@Override
	public String getComponentName() {
		return "dfc_emitter";
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getEnergyInfo(Context context, Arguments args) {
		return new Object[] {getPower(), getMaxPower()};
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCryogel(Context context, Arguments args) {
		return new Object[] {this.tank.getFill()};
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInput(Context context, Arguments args) {
		return new Object[] {this.watts};
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		return new Object[] {getPower(), getMaxPower(), this.tank.getFill(), this.watts, this.isOn};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] isActive(Context context, Arguments args) {
		return new Object[] {this.isOn};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setActive(Context context, Arguments args) {
		this.isOn = args.checkBoolean(0);
		return new Object[] {};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setInput(Context context, Arguments args) {
		int newOutput = args.checkInteger(0);
		if (newOutput > 100) {
			newOutput = 100;
		} else if (newOutput < 0) {
			newOutput = 0;
		}
		this.watts = newOutput;
		return new Object[] {};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCoreEmitter(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICoreEmitter(player.inventory, this);
	}
}
