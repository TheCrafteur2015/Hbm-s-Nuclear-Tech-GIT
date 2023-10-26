package com.hbm.tileentity.machine;

import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerAssemfac;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIAssemfac;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineAssemfac extends TileEntityMachineAssemblerBase implements IFluidStandardTransceiver {
	
	public AssemblerArm[] arms;

	public FluidTank water;
	public FluidTank steam;

	public TileEntityMachineAssemfac() {
		super(14 * 8 + 4 + 1); //8 assembler groups with 14 slots, 4 upgrade slots, 1 battery slot
		
		this.arms = new AssemblerArm[6];
		for(int i = 0; i < this.arms.length; i++) {
			this.arms[i] = new AssemblerArm(i % 3 == 1 ? 1 : 0); //the second of every group of three becomes a welder
		}

		this.water = new FluidTank(Fluids.WATER, 64_000);
		this.steam = new FluidTank(Fluids.SPENTSTEAM, 64_000);
	}

	@Override
	public String getName() {
		return "container.assemfac";
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		super.setInventorySlotContents(i, stack);
		
		if(stack != null && i >= 1 && i <= 4 && stack.getItem() instanceof ItemMachineUpgrade) {
			this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, "hbm:item.upgradePlug", 1.0F, 1.0F);
		}
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(!this.worldObj.isRemote) {
			
			if(this.worldObj.getTotalWorldTime() % 20 == 0) {
				updateConnections();
			}
			
			this.speed = 100;
			this.consumption = 100;
			
			UpgradeManager.eval(this.slots, 1, 4);

			int speedLevel = Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 6);
			int powerLevel = Math.min(UpgradeManager.getLevel(UpgradeType.POWER), 3);
			int overLevel = UpgradeManager.getLevel(UpgradeType.OVERDRIVE);
			
			this.speed -= speedLevel * 15;
			this.consumption += speedLevel * 300;
			this.speed += powerLevel * 5;
			this.consumption -= powerLevel * 30;
			this.speed /= (overLevel + 1);
			this.consumption *= (overLevel + 1);
			
			for(DirPos pos : getConPos()) {
				this.sendFluid(this.steam, this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setIntArray("progress", this.progress);
			data.setIntArray("maxProgress", this.maxProgress);
			data.setBoolean("isProgressing", this.isProgressing);
			
			this.water.writeToNBT(data, "w");
			this.steam.writeToNBT(data, "s");
			
			networkPack(data, 150);
			
		} else {
			
			for(AssemblerArm arm : this.arms) {
				arm.updateInterp();
				if(this.isProgressing) {
					arm.updateArm();
				}
			}
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.progress = nbt.getIntArray("progress");
		this.maxProgress = nbt.getIntArray("maxProgress");
		this.isProgressing = nbt.getBoolean("isProgressing");
		
		this.water.readFromNBT(nbt, "w");
		this.steam.readFromNBT(nbt, "s");
	}
	
	private int getWaterRequired() {
		return 1000 / this.speed;
	}

	@Override
	protected boolean canProcess(int index) {
		return super.canProcess(index) && this.water.getFill() >= getWaterRequired() && this.steam.getFill() + getWaterRequired() <= this.steam.getMaxFill();
	}

	@Override
	protected void process(int index) {
		super.process(index);
		this.water.setFill(this.water.getFill() - getWaterRequired());
		this.steam.setFill(this.steam.getFill() + getWaterRequired());
	}
	
	private void updateConnections() {
		for(DirPos pos : getConPos()) {
			this.trySubscribe(this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(this.water.getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	public DirPos[] getConPos() {
		
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		return new DirPos[] {
				new DirPos(this.xCoord - dir.offsetX * 3 + rot.offsetX * 5, this.yCoord, this.zCoord - dir.offsetZ * 3 + rot.offsetZ * 5, rot),
				new DirPos(this.xCoord + dir.offsetX * 2 + rot.offsetX * 5, this.yCoord, this.zCoord + dir.offsetZ * 2 + rot.offsetZ * 5, rot),
				new DirPos(this.xCoord - dir.offsetX * 3 - rot.offsetX * 4, this.yCoord, this.zCoord - dir.offsetZ * 3 - rot.offsetZ * 4, rot.getOpposite()),
				new DirPos(this.xCoord + dir.offsetX * 2 - rot.offsetX * 4, this.yCoord, this.zCoord + dir.offsetZ * 2 - rot.offsetZ * 4, rot.getOpposite()),
				new DirPos(this.xCoord - dir.offsetX * 5 + rot.offsetX * 3, this.yCoord, this.zCoord - dir.offsetZ * 5 + rot.offsetZ * 3, dir.getOpposite()),
				new DirPos(this.xCoord - dir.offsetX * 5 - rot.offsetX * 2, this.yCoord, this.zCoord - dir.offsetZ * 5 - rot.offsetZ * 2, dir.getOpposite()),
				new DirPos(this.xCoord + dir.offsetX * 4 + rot.offsetX * 3, this.yCoord, this.zCoord + dir.offsetZ * 4 + rot.offsetZ * 3, dir),
				new DirPos(this.xCoord + dir.offsetX * 4 - rot.offsetX * 2, this.yCoord, this.zCoord + dir.offsetZ * 4 - rot.offsetZ * 2, dir)
		};
	}
	
	public static class AssemblerArm {
		public double[] angles = new double[4];
		public double[] prevAngles = new double[4];
		public double[] targetAngles = new double[4];
		public double[] speed = new double[4];
		
		Random rand = new Random();
		
		int actionMode;
		ArmActionState state;
		int actionDelay = 0;
		
		public AssemblerArm(int actionMode) {
			this.actionMode = actionMode;
			
			if(this.actionMode == 0) {
				this.speed[0] = 15;	//Pivot
				this.speed[1] = 15;	//Arm
				this.speed[2] = 15;	//Piston
				this.speed[3] = 0.5;	//Striker
			} else if(this.actionMode == 1) {
				this.speed[0] = 3;		//Pivot
				this.speed[1] = 3;		//Arm
				this.speed[2] = 1;		//Piston
				this.speed[3] = 0.125;	//Striker
			}
			
			this.state = ArmActionState.ASSUME_POSITION;
			chooseNewArmPoistion();
			this.actionDelay = this.rand.nextInt(20);
		}
		
		public void updateArm() {
			
			if(this.actionDelay > 0) {
				this.actionDelay--;
				return;
			}
			
			switch(this.state) {
			//Move. If done moving, set a delay and progress to EXTEND
			case ASSUME_POSITION:
				if(move()) {
					if(this.actionMode == 0) {
						this.actionDelay = 2;
					} else if(this.actionMode == 1) {
						this.actionDelay = 10;
					}
					this.state = ArmActionState.EXTEND_STRIKER;
					this.targetAngles[3] = 1D;
				}
				break;
			case EXTEND_STRIKER:
				if(move()) {
					if(this.actionMode == 0) {
						this.state = ArmActionState.RETRACT_STRIKER;
						this.targetAngles[3] = 0D;
					} else if(this.actionMode == 1) {
						this.state = ArmActionState.WELD;
						this.targetAngles[2] -= 20;
						this.actionDelay = 5 + this.rand.nextInt(5);
					}
				}
				break;
			case WELD:
				if(move()) {
					this.state = ArmActionState.RETRACT_STRIKER;
					this.targetAngles[3] = 0D;
					this.actionDelay = 10 + this.rand.nextInt(5);
				}
				break;
			case RETRACT_STRIKER:
				if(move()) {
					if(this.actionMode == 0) {
						this.actionDelay = 2 + this.rand.nextInt(5);
					} else if(this.actionMode == 1) {
						this.actionDelay = 5 + this.rand.nextInt(3);
					}
					chooseNewArmPoistion();
					this.state = ArmActionState.ASSUME_POSITION;
				}
				break;
			
			}
		}
		
		public void chooseNewArmPoistion() {
			
			if(this.actionMode == 0) {
				this.targetAngles[0] = -this.rand.nextInt(50);		//Pivot
				this.targetAngles[1] = -this.targetAngles[0];			//Arm
				this.targetAngles[2] = this.rand.nextInt(30) - 15;	//Piston
			} else if(this.actionMode == 1) {
				this.targetAngles[0] = -this.rand.nextInt(30) + 10;	//Pivot
				this.targetAngles[1] = -this.targetAngles[0];			//Arm
				this.targetAngles[2] = this.rand.nextInt(10) + 10;	//Piston
			}
		}
		
		private void updateInterp() {
			for(int i = 0; i < this.angles.length; i++) {
				this.prevAngles[i] = this.angles[i];
			}
		}
		
		/**
		 * @return True when it has finished moving
		 */
		private boolean move() {
			boolean didMove = false;
			
			for(int i = 0; i < this.angles.length; i++) {
				if(this.angles[i] == this.targetAngles[i])
					continue;
				
				didMove = true;
				
				double angle = this.angles[i];
				double target = this.targetAngles[i];
				double turn = this.speed[i];
				double delta = Math.abs(angle - target);
				
				if(delta <= turn) {
					this.angles[i] = this.targetAngles[i];
					continue;
				}
				
				if(angle < target) {
					this.angles[i] += turn;
				} else {
					this.angles[i] -= turn;
				}
			}
			
			return !didMove;
		}
		
		public static enum ArmActionState {
			ASSUME_POSITION,
			EXTEND_STRIKER,
			WELD,
			RETRACT_STRIKER
		}
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(
					this.xCoord - 5,
					this.yCoord,
					this.zCoord - 5,
					this.xCoord + 5,
					this.yCoord + 4,
					this.zCoord + 5
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
	public long getMaxPower() {
		return 10_000_000;
	}

	@Override
	public int getRecipeCount() {
		return 8;
	}

	@Override
	public int getTemplateIndex(int index) {
		return 17 + index * 14;
	}

	@Override
	public int[] getSlotIndicesFromIndex(int index) {
		return new int[] { 5 + index * 14, 16 + index * 14, 18 + index * 14};
	}

	DirPos[] inpos;
	DirPos[] outpos;
	
	@Override
	public DirPos[] getInputPositions() {
		
		if(this.inpos != null)
			return this.inpos;
		
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		this.inpos = new DirPos[] {
				new DirPos(this.xCoord + dir.offsetX * 4 - rot.offsetX * 1, this.yCoord, this.zCoord + dir.offsetZ * 4 - rot.offsetZ * 1, dir),
				new DirPos(this.xCoord - dir.offsetX * 5 + rot.offsetX * 2, this.yCoord, this.zCoord - dir.offsetZ * 5 + rot.offsetZ * 2, dir.getOpposite()),
				new DirPos(this.xCoord - dir.offsetX * 2 - rot.offsetX * 4, this.yCoord, this.zCoord - dir.offsetZ * 2 - rot.offsetZ * 4, rot.getOpposite()),
				new DirPos(this.xCoord + dir.offsetX * 1 + rot.offsetX * 5, this.yCoord, this.zCoord + dir.offsetZ * 1 + rot.offsetZ * 5, rot)
		};
		
		return this.inpos;
	}

	@Override
	public DirPos[] getOutputPositions() {
		
		if(this.outpos != null)
			return this.outpos;
		
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		this.outpos = new DirPos[] {
				new DirPos(this.xCoord + dir.offsetX * 4 + rot.offsetX * 2, this.yCoord, this.zCoord + dir.offsetZ * 4 + rot.offsetZ * 2, dir),
				new DirPos(this.xCoord - dir.offsetX * 5 - rot.offsetX * 1, this.yCoord, this.zCoord - dir.offsetZ * 5 - rot.offsetZ * 1, dir.getOpposite()),
				new DirPos(this.xCoord + dir.offsetX * 1 - rot.offsetX * 4, this.yCoord, this.zCoord + dir.offsetZ * 1 - rot.offsetZ * 4, rot.getOpposite()),
				new DirPos(this.xCoord - dir.offsetX * 2 + rot.offsetX * 5, this.yCoord, this.zCoord - dir.offsetZ * 2 + rot.offsetZ * 5, rot)
		};
		
		return this.outpos;
	}

	@Override
	public int getPowerSlot() {
		return 0;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { this.steam };
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { this.water };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { this.water, this.steam };
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerAssemfac(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIAssemfac(player.inventory, this);
	}
}
