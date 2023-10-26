package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerCompressor;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUICompressor;
import com.hbm.inventory.recipes.CompressorRecipes;
import com.hbm.inventory.recipes.CompressorRecipes.CompressorRecipe;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.Tuple.Pair;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineCompressor extends TileEntityMachineBase implements IGUIProvider, IControlReceiver, IEnergyUser, IFluidStandardTransceiver {
	
	public FluidTank[] tanks;
	public long power;
	public static final long maxPower = 100_000;
	public boolean isOn;
	public int progress;
	public int processTime = 100;
	public static final int processTimeBase = 100;
	public int powerRequirement;
	public static final int powerRequirementBase = 2_500;
	
	public float fanSpin;
	public float prevFanSpin;
	public float piston;
	public float prevPiston;
	public boolean pistonDir;

	public TileEntityMachineCompressor() {
		super(4);
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.NONE, 16_000);
		this.tanks[1] = new FluidTank(Fluids.NONE, 16_000).withPressure(1);
	}

	@Override
	public String getName() {
		return "container.machineCompressor";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			if(this.worldObj.getTotalWorldTime() % 20 == 0) {
				updateConnections();
			}
			
			this.power = Library.chargeTEFromItems(this.slots, 1, this.power, TileEntityMachineCompressor.maxPower);
			this.tanks[0].setType(0, this.slots);
			setupTanks();
			
			UpgradeManager.eval(this.slots, 1, 3);

			int speedLevel = Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3);
			int powerLevel = Math.min(UpgradeManager.getLevel(UpgradeType.POWER), 3);
			int overLevel = UpgradeManager.getLevel(UpgradeType.OVERDRIVE);
			
			CompressorRecipe rec = CompressorRecipes.recipes.get(new Pair<>(this.tanks[0].getTankType(), this.tanks[0].getPressure()));
			int timeBase = TileEntityMachineCompressor.processTimeBase;
			if(rec != null) timeBase = rec.duration;

			//there is a reason to do this but i'm not telling you
			if(timeBase == TileEntityMachineCompressor.processTimeBase) this.processTime = speedLevel == 3 ? 10 : speedLevel == 2 ? 20 : speedLevel == 1 ? 60 : timeBase;
			else this.processTime = timeBase / (speedLevel + 1);
			this.powerRequirement = TileEntityMachineCompressor.powerRequirementBase / (powerLevel + 1);
			this.processTime = this.processTime / (overLevel + 1);
			this.powerRequirement = this.powerRequirement * ((overLevel * 2) + 1);
			
			if(this.processTime <= 0) this.processTime = 1;
			
			if(canProcess()) {
				this.progress++;
				this.isOn = true;
				this.power -= this.powerRequirement;
				
				if(this.progress >= this.processTime) {
					this.progress = 0;
					process();
					markChanged();
				}
				
			} else {
				this.progress = 0;
				this.isOn = false;
			}
			
			for(DirPos pos : getConPos()) {
				this.sendFluid(this.tanks[1], this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("progress", this.progress);
			data.setInteger("processTime", this.processTime);
			data.setInteger("powerRequirement", this.powerRequirement);
			data.setLong("power", this.power);
			this.tanks[0].writeToNBT(data, "0");
			this.tanks[1].writeToNBT(data, "1");
			data.setBoolean("isOn", this.isOn);
			networkPack(data, 100);
			
		} else {
			
			this.prevFanSpin = this.fanSpin;
			this.prevPiston = this.piston;
			
			if(this.isOn) {
				this.fanSpin += 15;
				
				if(this.fanSpin >= 360) {
					this.prevFanSpin -= 360;
					this.fanSpin -= 360;
				}
				
				if(this.pistonDir) {
					this.piston -= this.randSpeed;
					if(this.piston <= 0) {
						MainRegistry.proxy.playSoundClient(this.xCoord, this.yCoord, this.zCoord, "hbm:item.boltgun", 0.5F, 0.75F);
						this.pistonDir = !this.pistonDir;
					}
				} else {
					this.piston += 0.05F;
					if(this.piston >= 1) {
						this.randSpeed = 0.085F + this.worldObj.rand.nextFloat() * 0.03F;
						this.pistonDir = !this.pistonDir;
					}
				}
				
				this.piston = MathHelper.clamp_float(this.piston, 0F, 1F);
			}
		}
	}
	
	private float randSpeed = 0.1F;
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.progress = nbt.getInteger("progress");
		this.processTime = nbt.getInteger("processTime");
		this.powerRequirement = nbt.getInteger("powerRequirement");
		this.power = nbt.getLong("power");
		this.tanks[0].readFromNBT(nbt, "0");
		this.tanks[1].readFromNBT(nbt, "1");
		this.isOn = nbt.getBoolean("isOn");
	}
	
	private void updateConnections() {
		for(DirPos pos : getConPos()) {
			this.trySubscribe(this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(this.tanks[0].getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	public DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		return new DirPos[] {
				new DirPos(this.xCoord + rot.offsetX * 2, this.yCoord, this.zCoord + rot.offsetZ * 2, rot),
				new DirPos(this.xCoord - rot.offsetX * 2, this.yCoord, this.zCoord - rot.offsetZ * 2, rot.getOpposite()),
				new DirPos(this.xCoord - dir.offsetX * 2, this.yCoord, this.zCoord - dir.offsetZ * 2, dir.getOpposite()),
		};
	}
	
	public boolean canProcess() {
		
		if(this.power <= this.powerRequirement) return false;
		
		CompressorRecipe recipe = CompressorRecipes.recipes.get(new Pair<>(this.tanks[0].getTankType(), this.tanks[0].getPressure()));
		
		if(recipe == null) {
			return this.tanks[0].getFill() >= 1000 && this.tanks[1].getFill() + 1000 <= this.tanks[1].getMaxFill();
		}
		
		return this.tanks[0].getFill() > recipe.inputAmount && this.tanks[1].getFill() + recipe.output.fill <= this.tanks[1].getMaxFill();
	}
	
	public void process() {
		
		CompressorRecipe recipe = CompressorRecipes.recipes.get(new Pair<>(this.tanks[0].getTankType(), this.tanks[0].getPressure()));
		
		if(recipe == null) {
			this.tanks[0].setFill(this.tanks[0].getFill() - 1_000);
			this.tanks[1].setFill(this.tanks[1].getFill() + 1_000);
		} else {
			this.tanks[0].setFill(this.tanks[0].getFill() - recipe.inputAmount);
			this.tanks[1].setFill(this.tanks[1].getFill() + recipe.output.fill);
		}
	}
	
	protected void setupTanks() {
		
		CompressorRecipe recipe = CompressorRecipes.recipes.get(new Pair<>(this.tanks[0].getTankType(), this.tanks[0].getPressure()));
		
		if(recipe == null) {
			this.tanks[1].withPressure(this.tanks[0].getPressure() + 1).setTankType(this.tanks[0].getTankType());
		} else {
			this.tanks[1].withPressure(recipe.output.pressure).setTankType(recipe.output.type);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.power = nbt.getLong("power");
		this.progress = nbt.getInteger("progress");
		this.tanks[0].readFromNBT(nbt, "0");
		this.tanks[1].readFromNBT(nbt, "1");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", this.power);
		nbt.setInteger("progress", this.progress);
		this.tanks[0].writeToNBT(nbt, "0");
		this.tanks[1].writeToNBT(nbt, "1");
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCompressor(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICompressor(player.inventory, this);
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		int compression = data.getInteger("compression");
		
		if(compression != this.tanks[0].getPressure()) {
			this.tanks[0].withPressure(compression);
			
			CompressorRecipe recipe = CompressorRecipes.recipes.get(new Pair<>(this.tanks[0].getTankType(), compression));
			
			if(recipe == null) {
				this.tanks[1].withPressure(compression + 1);
			} else {
				this.tanks[1].withPressure(recipe.output.pressure).setTankType(recipe.output.type);
			}
			
			markChanged();
		}
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getMaxPower() {
		return TileEntityMachineCompressor.maxPower;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return this.tanks;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {this.tanks[1]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {this.tanks[0]};
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
					this.yCoord + 9,
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
}
