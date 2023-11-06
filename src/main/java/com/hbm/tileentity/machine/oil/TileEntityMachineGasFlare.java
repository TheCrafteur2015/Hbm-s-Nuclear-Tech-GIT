package com.hbm.tileentity.machine.oil;

import java.util.List;

import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerMachineGasFlare;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Gaseous;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Gaseous_ART;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Leaded;
import com.hbm.inventory.gui.GUIMachineGasFlare;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.ParticleUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyGenerator;
import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class TileEntityMachineGasFlare extends TileEntityMachineBase implements IEnergyGenerator, IFluidContainer, IFluidAcceptor, IFluidStandardReceiver, IControlReceiver, IGUIProvider {

	public long power;
	public static final long maxPower = 100000;
	public FluidTank tank;
	public boolean isOn = false;
	public boolean doesBurn = false;

	public TileEntityMachineGasFlare() {
		super(6);
		this.tank = new FluidTank(Fluids.GAS, 64000);
	}

	@Override
	public String getName() {
		return "container.gasFlare";
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.power = nbt.getLong("powerTime");
		this.tank.readFromNBT(nbt, "gas");
		this.isOn = nbt.getBoolean("isOn");
		this.doesBurn = nbt.getBoolean("doesBurn");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("powerTime", this.power);
		this.tank.writeToNBT(nbt, "gas");
		nbt.setBoolean("isOn", this.isOn);
		nbt.setBoolean("doesBurn", this.doesBurn);
	}

	public long getPowerScaled(long i) {
		return (this.power * i) / TileEntityMachineGasFlare.maxPower;
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return player.getDistanceSq(this.xCoord, this.yCoord, this.zCoord) <= 256;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("valve")) this.isOn = !this.isOn;
		if(data.hasKey("dial")) this.doesBurn = !this.doesBurn;
		this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
	}

	@Override
	public void updateEntity() {

		if(!this.worldObj.isRemote) {

			for(DirPos pos : getConPos()) {
				sendPower(this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				this.trySubscribe(this.tank.getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}

			this.tank.setType(3, this.slots);
			this.tank.loadTank(1, 2, this.slots);
			this.tank.updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
			
			int maxVent = 50;
			int maxBurn = 10;
			
			if(this.isOn && this.tank.getFill() > 0) {
				
				UpgradeManager.eval(this.slots, 4, 5);
				int burn = Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3);
				int yield = Math.min(UpgradeManager.getLevel(UpgradeType.EFFECT), 3);

				maxVent += maxVent * burn;
				maxBurn += maxBurn * burn;
				
				if(!this.doesBurn || !(this.tank.getTankType().hasTrait(FT_Flammable.class))) {
					
					if(this.tank.getTankType().hasTrait(FT_Gaseous.class) || this.tank.getTankType().hasTrait(FT_Gaseous_ART.class)) {
						int eject = Math.min(maxVent, this.tank.getFill());
						this.tank.setFill(this.tank.getFill() - eject);
						this.tank.getTankType().onFluidRelease(this, this.tank, eject);
						
						if(this.worldObj.getTotalWorldTime() % 7 == 0)
							this.worldObj.playSoundEffect(this.xCoord, this.yCoord + 11, this.zCoord, "random.fizz", 1.5F, 0.5F);
					}
				} else {
					
					if(this.tank.getTankType().hasTrait(FT_Flammable.class)) {
						int eject = Math.min(maxBurn, this.tank.getFill());
						this.tank.setFill(this.tank.getFill() - eject);
						
						int penalty = 2;
						if(!this.tank.getTankType().hasTrait(FT_Gaseous.class) && !this.tank.getTankType().hasTrait(FT_Gaseous_ART.class))
							penalty = 10;
						
						long powerProd = this.tank.getTankType().getTrait(FT_Flammable.class).getHeatEnergy() * eject / 1_000; // divided by 1000 per mB
						powerProd /= penalty;
						powerProd += powerProd * yield / 3;
						
						this.power += powerProd;
						
						if(this.power > TileEntityMachineGasFlare.maxPower)
							this.power = TileEntityMachineGasFlare.maxPower;
						
						ParticleUtil.spawnGasFlame(this.worldObj, this.xCoord + 0.5F, this.yCoord + 11.75F, this.zCoord + 0.5F, this.worldObj.rand.nextGaussian() * 0.15, 0.2, this.worldObj.rand.nextGaussian() * 0.15);
						
						List<Entity> list = this.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(this.xCoord - 1, this.yCoord + 12, this.zCoord - 2, this.xCoord + 2, this.yCoord + 17, this.zCoord + 2));
						for(Entity e : list) {
							e.setFire(5);
							e.attackEntityFrom(DamageSource.onFire, 5F);
						}
						
						if(this.worldObj.getTotalWorldTime() % 3 == 0)
							this.worldObj.playSoundEffect(this.xCoord, this.yCoord + 11, this.zCoord, "hbm:weapon.flamethrowerShoot", 1.5F, 0.75F);

						if(this.worldObj.getTotalWorldTime() % 20 == 0) {
							PollutionHandler.incrementPollution(this.worldObj, this.xCoord, this.yCoord, this.zCoord, PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND * 5);
							if(this.tank.getTankType().hasTrait(FT_Leaded.class)) PollutionHandler.incrementPollution(this.worldObj, this.xCoord, this.yCoord, this.zCoord, PollutionType.HEAVYMETAL, PollutionHandler.HEAVY_METAL_PER_SECOND * 5);
						}
					}
				}
			}

			this.power = Library.chargeItemsFromTE(this.slots, 0, this.power, TileEntityMachineGasFlare.maxPower);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setBoolean("isOn", this.isOn);
			data.setBoolean("doesBurn", this.doesBurn);
			networkPack(data, 50);

		} else {
			
			if(this.isOn && this.tank.getFill() > 0) {
							
				if((!this.doesBurn || !(this.tank.getTankType().hasTrait(FT_Flammable.class))) && (this.tank.getTankType().hasTrait(FT_Gaseous.class) || this.tank.getTankType().hasTrait(FT_Gaseous_ART.class))) {
						
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "tower");
					data.setFloat("lift", 1F);
					data.setFloat("base", 0.25F);
					data.setFloat("max", 3F);
					data.setInteger("life", 150 + this.worldObj.rand.nextInt(20));
					data.setInteger("color", this.tank.getTankType().getColor());

					data.setDouble("posX", this.xCoord + 0.5);
					data.setDouble("posZ", this.zCoord + 0.5);
					data.setDouble("posY", this.yCoord + 11);
						
					MainRegistry.proxy.effectNT(data);
					
				}
				
				if(this.doesBurn && this.tank.getTankType().hasTrait(FT_Flammable.class) && MainRegistry.proxy.me().getDistanceSq(this.xCoord, this.yCoord + 10, this.zCoord) <= 1024) {
					
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "vanillaExt");
					data.setString("mode", "smoke");
					data.setBoolean("noclip", true);
					data.setInteger("overrideAge", 50);

					if(this.worldObj.getTotalWorldTime() % 2 == 0) {
						data.setDouble("posX", this.xCoord + 1.5);
						data.setDouble("posZ", this.zCoord + 1.5);
						data.setDouble("posY", this.yCoord + 10.75);
					} else {
						data.setDouble("posX", this.xCoord + 1.125);
						data.setDouble("posZ", this.zCoord - 0.5);
						data.setDouble("posY", this.yCoord + 11.75);
					}
					
					MainRegistry.proxy.effectNT(data);
				}
			}
		}
	}
	
	public DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(this.xCoord + 2, this.yCoord, this.zCoord, Library.POS_X),
				new DirPos(this.xCoord - 2, this.yCoord, this.zCoord, Library.NEG_X),
				new DirPos(this.xCoord, this.yCoord, this.zCoord + 2, Library.POS_Z),
				new DirPos(this.xCoord, this.yCoord, this.zCoord - 2, Library.NEG_Z)
		};
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.isOn = nbt.getBoolean("isOn");
		this.doesBurn = nbt.getBoolean("doesBurn");
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

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return TileEntityMachineGasFlare.maxPower;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
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
		return type.getName().equals(this.tank.getTankType().getName()) ? this.tank.getMaxFill() : 0;
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
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { this.tank };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { this.tank };
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineGasFlare(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineGasFlare(player.inventory, this);
	}
}
