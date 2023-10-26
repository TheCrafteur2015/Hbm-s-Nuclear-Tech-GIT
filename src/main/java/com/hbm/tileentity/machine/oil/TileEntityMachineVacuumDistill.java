package com.hbm.tileentity.machine.oil;

import com.hbm.inventory.container.ContainerMachineVacuumDistill;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineVacuumDistill;
import com.hbm.inventory.recipes.RefineryRecipes;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IPersistentNBT;
import com.hbm.tileentity.TileEntityMachineBase;
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
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineVacuumDistill extends TileEntityMachineBase implements IEnergyUser, IFluidStandardTransceiver, IPersistentNBT, IGUIProvider {
	
	public long power;
	public static final long maxPower = 1_000_000;
	
	public FluidTank[] tanks;
	
	private AudioWrapper audio;
	private int audioTime;
	public boolean isOn;

	public TileEntityMachineVacuumDistill() {
		super(11);
		
		this.tanks = new FluidTank[5];
		this.tanks[0] = new FluidTank(Fluids.OIL, 64_000).withPressure(2);
		this.tanks[1] = new FluidTank(Fluids.HEAVYOIL_VACUUM, 24_000);
		this.tanks[2] = new FluidTank(Fluids.REFORMATE, 24_000);
		this.tanks[3] = new FluidTank(Fluids.LIGHTOIL_VACUUM, 24_000);
		this.tanks[4] = new FluidTank(Fluids.SOURGAS, 24_000);
	}

	@Override
	public String getName() {
		return "container.vacuumDistill";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			this.isOn = false;
			
			updateConnections();
			this.power = Library.chargeTEFromItems(this.slots, 0, this.power, TileEntityMachineVacuumDistill.maxPower);
			this.tanks[0].loadTank(1, 2, this.slots);
			
			refine();

			this.tanks[1].unloadTank(3, 4, this.slots);
			this.tanks[2].unloadTank(5, 6, this.slots);
			this.tanks[3].unloadTank(7, 8, this.slots);
			this.tanks[4].unloadTank(9, 10, this.slots);
			
			for(DirPos pos : getConPos()) {
				for(int i = 1; i < 5; i++) {
					if(this.tanks[i].getFill() > 0) {
						this.sendFluid(this.tanks[i], this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
					}
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setBoolean("isOn", this.isOn);
			for(int i = 0; i < 5; i++) this.tanks[i].writeToNBT(data, "" + i);
			networkPack(data, 150);
		} else {
			
			if(this.isOn) this.audioTime = 20;
			
			if(this.audioTime > 0) {
				
				this.audioTime--;
				
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
	
	@Override
	public AudioWrapper createAudioLoop() {
		return MainRegistry.proxy.getLoopedSound("hbm:block.boiler", this.xCoord, this.yCoord, this.zCoord, 0.25F, 15F, 1.0F, 20);
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
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.isOn = nbt.getBoolean("isOn");
		for(int i = 0; i < 5; i++) this.tanks[i].readFromNBT(nbt, "" + i);
	}
	
	private void refine() {
		
		if((this.power < 10_000) || (this.tanks[0].getFill() < 100) || (this.tanks[1].getFill() + RefineryRecipes.vac_frac_heavy > this.tanks[1].getMaxFill()) || (this.tanks[2].getFill() + RefineryRecipes.vac_frac_reform > this.tanks[2].getMaxFill())) return;
		if(this.tanks[3].getFill() + RefineryRecipes.vac_frac_light > this.tanks[3].getMaxFill()) return;
		if(this.tanks[4].getFill() + RefineryRecipes.vac_frac_sour > this.tanks[4].getMaxFill()) return;

		this.isOn = true;
		this.power -= 10_000;
		this.tanks[0].setFill(this.tanks[0].getFill() - 100);
		this.tanks[1].setFill(this.tanks[1].getFill() + RefineryRecipes.vac_frac_heavy);
		this.tanks[2].setFill(this.tanks[2].getFill() + RefineryRecipes.vac_frac_reform);
		this.tanks[3].setFill(this.tanks[3].getFill() + RefineryRecipes.vac_frac_light);
		this.tanks[4].setFill(this.tanks[4].getFill() + RefineryRecipes.vac_frac_sour);
	}
	
	private void updateConnections() {
		for(DirPos pos : getConPos()) {
			this.trySubscribe(this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(this.tanks[0].getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	public DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(this.xCoord + 2, this.yCoord, this.zCoord + 1, Library.POS_X),
				new DirPos(this.xCoord + 2, this.yCoord, this.zCoord - 1, Library.POS_X),
				new DirPos(this.xCoord - 2, this.yCoord, this.zCoord + 1, Library.NEG_X),
				new DirPos(this.xCoord - 2, this.yCoord, this.zCoord - 1, Library.NEG_X),
				new DirPos(this.xCoord + 1, this.yCoord, this.zCoord + 2, Library.POS_Z),
				new DirPos(this.xCoord - 1, this.yCoord, this.zCoord + 2, Library.POS_Z),
				new DirPos(this.xCoord + 1, this.yCoord, this.zCoord - 2, Library.NEG_Z),
				new DirPos(this.xCoord - 1, this.yCoord, this.zCoord - 2, Library.NEG_Z)
		};
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.power = nbt.getLong("power");
		this.tanks[0].readFromNBT(nbt, "input");
		this.tanks[1].readFromNBT(nbt, "heavy");
		this.tanks[2].readFromNBT(nbt, "reformate");
		this.tanks[3].readFromNBT(nbt, "light");
		this.tanks[4].readFromNBT(nbt, "gas");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", this.power);
		this.tanks[0].writeToNBT(nbt, "input");
		this.tanks[1].writeToNBT(nbt, "heavy");
		this.tanks[2].writeToNBT(nbt, "reformate");
		this.tanks[3].writeToNBT(nbt, "light");
		this.tanks[4].writeToNBT(nbt, "gas");
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(
					this.xCoord - 1,
					this.yCoord,
					this.zCoord - 1,
					this.xCoord + 2,
					this.yCoord + 9,
					this.zCoord + 2
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
	public long getPower() {
		return this.power;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getMaxPower() {
		return TileEntityMachineVacuumDistill.maxPower;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return this.tanks;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {this.tanks[1], this.tanks[2], this.tanks[3], this.tanks[4]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {this.tanks[0]};
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN && dir != ForgeDirection.DOWN;
	}

	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN && dir != ForgeDirection.DOWN;
	}

	@Override
	public void writeNBT(NBTTagCompound nbt) {
		if(this.tanks[0].getFill() == 0 && this.tanks[1].getFill() == 0 && this.tanks[2].getFill() == 0 && this.tanks[3].getFill() == 0 && this.tanks[4].getFill() == 0) return;
		NBTTagCompound data = new NBTTagCompound();
		for(int i = 0; i < 5; i++) this.tanks[i].writeToNBT(data, "" + i);
		nbt.setTag(IPersistentNBT.NBT_PERSISTENT_KEY, data);
	}

	@Override
	public void readNBT(NBTTagCompound nbt) {
		NBTTagCompound data = nbt.getCompoundTag(IPersistentNBT.NBT_PERSISTENT_KEY);
		for(int i = 0; i < 5; i++) this.tanks[i].readFromNBT(data, "" + i);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineVacuumDistill(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineVacuumDistill(player.inventory, this);
	}
}
