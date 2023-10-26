package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerCombustionEngine;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Combustible;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Leaded;
import com.hbm.inventory.gui.GUICombustionEngine;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemPistons.EnumPistonType;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachinePolluting;
import com.hbm.util.EnumUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyGenerator;
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

public class TileEntityMachineCombustionEngine extends TileEntityMachinePolluting implements IEnergyGenerator, IFluidStandardTransceiver, IControlReceiver, IGUIProvider {
	
	public boolean isOn = false;
	public static long maxPower = 2_500_000;
	public long power;
	private int playersUsing = 0;
	public int setting = 0;
	public boolean wasOn = false;
	
	public float doorAngle = 0;
	public float prevDoorAngle = 0;
	
	private AudioWrapper audio;
	
	public FluidTank tank;
	public int tenth = 0;

	public TileEntityMachineCombustionEngine() {
		super(5, 50);
		this.tank = new FluidTank(Fluids.DIESEL, 24_000, 0);
	}

	@Override
	public String getName() {
		return "container.combustionEngine";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {

			this.tank.loadTank(0, 1, this.slots);
			if(this.tank.setType(4, this.slots)) {
				this.tenth = 0;
			}
			
			this.wasOn = false;

			int fill = this.tank.getFill() * 10 + this.tenth;
			if(this.isOn && this.setting > 0 && this.slots[2] != null && this.slots[2].getItem() == ModItems.piston_set && fill > 0 && this.tank.getTankType().hasTrait(FT_Combustible.class)) {
				EnumPistonType piston = EnumUtil.grabEnumSafely(EnumPistonType.class, this.slots[2].getItemDamage());
				FT_Combustible trait = this.tank.getTankType().getTrait(FT_Combustible.class);
				
				double eff = piston.eff[trait.getGrade().ordinal()];
				
				if(eff > 0) {
					int speed = this.setting * 2;
					
					int toBurn = Math.min(fill, speed);
					this.power += toBurn * (trait.getCombustionEnergy() / 10_000D) * eff;
					fill -= toBurn;
					
					if(this.worldObj.getTotalWorldTime() % 20 == 0) {
						pollute(PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND * this.setting * 0.1F);
						if(this.tank.getTankType().hasTrait(FT_Leaded.class)) pollute(PollutionType.HEAVYMETAL, PollutionHandler.HEAVY_METAL_PER_SECOND * this.setting * 0.1F);
					}
					
					if(toBurn > 0) {
						this.wasOn = true;
					}
					
					this.tank.setFill(fill / 10);
					this.tenth = fill % 10;
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", Math.min(this.power, TileEntityMachineCombustionEngine.maxPower));
			
			this.power = Library.chargeItemsFromTE(this.slots, 3, this.power, this.power);
			
			for(DirPos pos : getConPos()) {
				sendPower(this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				this.trySubscribe(this.tank.getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				sendSmoke(pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
			
			if(this.power > TileEntityMachineCombustionEngine.maxPower)
				this.power = TileEntityMachineCombustionEngine.maxPower;
			
			data.setInteger("playersUsing", this.playersUsing);
			data.setInteger("setting", this.setting);
			data.setBoolean("isOn", this.isOn);
			data.setBoolean("wasOn", this.wasOn);
			this.tank.writeToNBT(data, "tank");
			networkPack(data, 50);
			
		} else {
			this.prevDoorAngle = this.doorAngle;
			float swingSpeed = (this.doorAngle / 10F) + 3;
			
			if(this.playersUsing > 0) {
				this.doorAngle += swingSpeed;
			} else {
				this.doorAngle -= swingSpeed;
			}
			
			this.doorAngle = MathHelper.clamp_float(this.doorAngle, 0F, 135F);

			if(this.wasOn) {
				
				if(this.audio == null) {
					this.audio = createAudioLoop();
					this.audio.startSound();
				} else if(!this.audio.isPlaying()) {
					this.audio = rebootAudio(this.audio);
				}
				
				this.audio.keepAlive();
				
			} else {
				
				if(this.audio != null) {
					this.audio.stopSound();
					this.audio = null;
				}
			}
		}
	}
	
	private DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		return new DirPos[] {
				new DirPos(this.xCoord + dir.offsetX * 1 + rot.offsetX, this.yCoord, this.zCoord + dir.offsetZ * 1 + rot.offsetZ, dir),
				new DirPos(this.xCoord + dir.offsetX * 1 - rot.offsetX, this.yCoord, this.zCoord + dir.offsetZ * 1 - rot.offsetZ, dir),
				new DirPos(this.xCoord - dir.offsetX * 2 + rot.offsetX, this.yCoord, this.zCoord - dir.offsetZ * 2 + rot.offsetZ, dir.getOpposite()),
				new DirPos(this.xCoord - dir.offsetX * 2 - rot.offsetX, this.yCoord, this.zCoord - dir.offsetZ * 2 - rot.offsetZ, dir.getOpposite())
		};
	}
	
	@Override
	public AudioWrapper createAudioLoop() {
		return MainRegistry.proxy.getLoopedSound("hbm:block.igeneratorOperate", this.xCoord, this.yCoord, this.zCoord, 1.0F, 10F, 1.0F, 20);
	}

	@Override
	public void onChunkUnload() {

		if(this.audio != null) {
			this.audio.stopSound();
			this.audio = null;
		}
	}

	@Override
	public void invalidate() {

		super.invalidate();

		if(this.audio != null) {
			this.audio.stopSound();
			this.audio = null;
		}
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.DOWN;
	}

	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir != ForgeDirection.DOWN;
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.playersUsing = nbt.getInteger("playersUsing");
		this.setting = nbt.getInteger("setting");
		this.power = nbt.getLong("power");
		this.isOn = nbt.getBoolean("isOn");
		this.wasOn = nbt.getBoolean("wasOn");
		this.tank.readFromNBT(nbt, "tank");
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.setting = nbt.getInteger("setting");
		this.power = nbt.getLong("power");
		this.isOn = nbt.getBoolean("isOn");
		this.tank.readFromNBT(nbt, "tank");
		this.tenth = nbt.getInteger("tenth");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("setting", this.setting);
		nbt.setLong("power", this.power);
		nbt.setBoolean("isOn", this.isOn);
		this.tank.writeToNBT(nbt, "tank");
		nbt.setInteger("tenth", this.tenth);
	}
	
	@Override
	public void openInventory() {
		if(!this.worldObj.isRemote) this.playersUsing++;
	}
	
	@Override
	public void closeInventory() {
		if(!this.worldObj.isRemote) this.playersUsing--;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return TileEntityMachineCombustionEngine.maxPower;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCombustionEngine(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICombustionEngine(player.inventory, this);
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {this.tank};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {this.tank};
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return getSmokeTanks();
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(
					this.xCoord - 3,
					this.yCoord,
					this.zCoord - 3,
					this.xCoord + 4,
					this.yCoord + 2,
					this.zCoord + 4
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
	public boolean hasPermission(EntityPlayer player) {
		return player.getDistance(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5) < 25;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("turnOn")) this.isOn = !this.isOn;
		if(data.hasKey("setting")) this.setting = data.getInteger("setting");
		
		markChanged();
	}
}
