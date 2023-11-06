package com.hbm.tileentity.machine.oil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.container.ContainerMachineRefinery;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineRefinery;
import com.hbm.inventory.recipes.RefineryRecipes;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IOverpressurable;
import com.hbm.tileentity.IPersistentNBT;
import com.hbm.tileentity.IRepairable;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.ParticleUtil;
import com.hbm.util.Tuple.Quintet;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineRefinery extends TileEntityMachineBase implements IEnergyUser, IFluidContainer, IFluidAcceptor, IFluidSource, IControlReceiver, IOverpressurable, IPersistentNBT, IRepairable, IFluidStandardTransceiver, IGUIProvider {

	public long power = 0;
	public int sulfur = 0;
	public static final int maxSulfur = 100;
	public static final long maxPower = 1000;
	public FluidTank[] tanks;
	public List<IFluidAcceptor> list1 = new ArrayList<>();
	public List<IFluidAcceptor> list2 = new ArrayList<>();
	public List<IFluidAcceptor> list3 = new ArrayList<>();
	public List<IFluidAcceptor> list4 = new ArrayList<>();
	
	public boolean hasExploded = false;
	public boolean onFire = false;
	public Explosion lastExplosion = null;
	
	private AudioWrapper audio;
	private int audioTime;
	public boolean isOn;

	private static final int[] slot_access = new int[] {11};
	
	public TileEntityMachineRefinery() {
		super(12);
		this.tanks = new FluidTank[5];
		this.tanks[0] = new FluidTank(Fluids.HOTOIL, 64_000);
		this.tanks[1] = new FluidTank(Fluids.HEAVYOIL, 24_000);
		this.tanks[2] = new FluidTank(Fluids.NAPHTHA, 24_000);
		this.tanks[3] = new FluidTank(Fluids.LIGHTOIL, 24_000);
		this.tanks[4] = new FluidTank(Fluids.PETROLEUM, 24_000);
	}

	@Override
	public String getName() {
		return "container.machineRefinery";
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.power = nbt.getLong("power");
		this.tanks[0].readFromNBT(nbt, "input");
		this.tanks[1].readFromNBT(nbt, "heavy");
		this.tanks[2].readFromNBT(nbt, "naphtha");
		this.tanks[3].readFromNBT(nbt, "light");
		this.tanks[4].readFromNBT(nbt, "petroleum");
		this.sulfur = nbt.getInteger("sulfur");
		this.hasExploded = nbt.getBoolean("exploded");
		this.onFire = nbt.getBoolean("onFire");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", this.power);
		this.tanks[0].writeToNBT(nbt, "input");
		this.tanks[1].writeToNBT(nbt, "heavy");
		this.tanks[2].writeToNBT(nbt, "naphtha");
		this.tanks[3].writeToNBT(nbt, "light");
		this.tanks[4].writeToNBT(nbt, "petroleum");
		nbt.setInteger("sulfur", this.sulfur);
		nbt.setBoolean("exploded", this.hasExploded);
		nbt.setBoolean("onFire", this.onFire);
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return TileEntityMachineRefinery.slot_access;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 11;
	}
	
	@Override
	public void updateEntity() {

		if(!this.worldObj.isRemote) {
			
			this.isOn = false;
			
			if(getBlockMetadata() < 12) {
				ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata()).getRotation(ForgeDirection.DOWN);
				this.worldObj.removeTileEntity(this.xCoord, this.yCoord, this.zCoord);
				this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, ModBlocks.machine_refinery, dir.ordinal() + 10, 3);
				MultiblockHandlerXR.fillSpace(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ((BlockDummyable) ModBlocks.machine_refinery).getDimensions(), ModBlocks.machine_refinery, dir);
				NBTTagCompound data = new NBTTagCompound();
				writeToNBT(data);
				this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord).readFromNBT(data);
				return;
			}
			
			if(!this.hasExploded) {
				
				updateConnections();
				
				this.power = Library.chargeTEFromItems(this.slots, 0, this.power, TileEntityMachineRefinery.maxPower);
				
				if(this.worldObj.getTotalWorldTime() % 10 == 0) {
					fillFluidInit(this.tanks[1].getTankType());
					fillFluidInit(this.tanks[2].getTankType());
					fillFluidInit(this.tanks[3].getTankType());
					fillFluidInit(this.tanks[4].getTankType());
				}
				
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
			} else if(this.onFire){
				
				boolean hasFuel = false;
				for(int i = 0; i < 5; i++) {
					if(this.tanks[i].getFill() > 0) {
						this.tanks[i].setFill(Math.max(this.tanks[i].getFill() - 10, 0));
						hasFuel = true;
					}
				}
				
				if(hasFuel) {
					List<Entity> affected = this.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(this.xCoord - 1.5, this.yCoord, this.zCoord - 1.5, this.xCoord + 2.5, this.yCoord + 8, this.zCoord + 2.5));
					for(Entity e : affected) e.setFire(5);
					Random rand = this.worldObj.rand;
					ParticleUtil.spawnGasFlame(this.worldObj, this.xCoord + rand.nextDouble(), this.yCoord + 1.5 + rand.nextDouble() * 3, this.zCoord + rand.nextDouble(), rand.nextGaussian() * 0.05, 0.1, rand.nextGaussian() * 0.05);

					if(this.worldObj.getTotalWorldTime() % 20 == 0) {
						PollutionHandler.incrementPollution(this.worldObj, this.xCoord, this.yCoord, this.zCoord, PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND * 70);
					}
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			for(int i = 0; i < 5; i++) this.tanks[i].writeToNBT(data, "" + i);
			data.setBoolean("exploded", this.hasExploded);
			data.setBoolean("onFire", this.onFire);
			data.setBoolean("isOn", this.isOn);
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
		for(int i = 0; i < 5; i++) this.tanks[i].readFromNBT(nbt, "" + i);
		this.hasExploded = nbt.getBoolean("exploded");
		this.onFire = nbt.getBoolean("onFire");
		this.isOn = nbt.getBoolean("isOn");
	}
	
	private void refine() {
		Quintet<FluidStack, FluidStack, FluidStack, FluidStack, ItemStack> refinery = RefineryRecipes.getRefinery(this.tanks[0].getTankType());
		
		if(refinery == null) //usually not possible
			return;
		
		FluidStack[] stacks = new FluidStack[] {refinery.getV(), refinery.getW(), refinery.getX(), refinery.getY()};
		
		for(int i = 0; i < stacks.length; i++)
			this.tanks[i + 1].setTankType(stacks[i].type);
		
		if(this.power < 5 || this.tanks[0].getFill() < 100)
			return;

		for(int i = 0; i < stacks.length; i++) {
			if(this.tanks[i + 1].getFill() + stacks[i].fill > this.tanks[i + 1].getMaxFill()) {
				return;
			}
		}
		
		this.isOn = true;
		this.tanks[0].setFill(this.tanks[0].getFill() - 100);

		for(int i = 0; i < stacks.length; i++)
			this.tanks[i + 1].setFill(this.tanks[i + 1].getFill() + stacks[i].fill);
		
		this.sulfur++;
		
		if(this.sulfur >= TileEntityMachineRefinery.maxSulfur) {
			this.sulfur -= TileEntityMachineRefinery.maxSulfur;
			
			ItemStack out = refinery.getZ();
			
			if(out != null) {
				
				if(this.slots[11] == null) {
					this.slots[11] = out.copy();
				} else {
					
					if(out.getItem() == this.slots[11].getItem() && out.getItemDamage() == this.slots[11].getItemDamage() && this.slots[11].stackSize + out.stackSize <= this.slots[11].getMaxStackSize()) {
						this.slots[11].stackSize += out.stackSize;
					}
				}
			}
			
			markDirty();
		}

		if(this.worldObj.getTotalWorldTime() % 20 == 0) PollutionHandler.incrementPollution(this.worldObj, this.xCoord, this.yCoord, this.zCoord, PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND * 5);
		
		this.power -= 5;
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
	
	public long getPowerScaled(long i) {
		return (this.power * i) / TileEntityMachineRefinery.maxPower;
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
		return TileEntityMachineRefinery.maxPower;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord - 2, getTact(), type);
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord + 2, getTact(), type);
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord - 2, getTact(), type);
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord + 2, getTact(), type);
		
		fillFluid(this.xCoord - 2, this.yCoord, this.zCoord + 1, getTact(), type);
		fillFluid(this.xCoord + 2, this.yCoord, this.zCoord + 1, getTact(), type);
		fillFluid(this.xCoord - 2, this.yCoord, this.zCoord - 1, getTact(), type);
		fillFluid(this.xCoord + 2, this.yCoord, this.zCoord - 1, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, this.worldObj, type);
	}

	@Override
	public boolean getTact() {
		return this.worldObj.getTotalWorldTime() % 20 < 10;
	}

	@Override
	public int getFluidFill(FluidType type) {
		
		for(int i = 0; i < 5; i++) {
			if(type == this.tanks[i].getTankType()) {
				return this.tanks[i].getFill();
			}
		}
		
		return 0;
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		
		for(int i = 0; i < 5; i++) {
			if(type == this.tanks[i].getTankType()) {
				this.tanks[i].setFill(fill);
			}
		}
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		if(type == this.tanks[1].getTankType()) return this.list1;
		if(type == this.tanks[2].getTankType()) return this.list2;
		if(type == this.tanks[3].getTankType()) return this.list3;
		if(type == this.tanks[4].getTankType()) return this.list4;
		return new ArrayList<>();
	}

	@Override
	public void clearFluidList(FluidType type) {
		if(type == this.tanks[1].getTankType()) this.list1.clear();
		if(type == this.tanks[2].getTankType()) this.list2.clear();
		if(type == this.tanks[3].getTankType()) this.list3.clear();
		if(type == this.tanks[4].getTankType()) this.list4.clear();
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type == this.tanks[0].getTankType())
			return this.tanks[0].getMaxFill();
		else
			return 0;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if(index < 5 && this.tanks[index] != null)
			this.tanks[index].setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if(index < 5 && this.tanks[index] != null)
			this.tanks[index].setTankType(type);
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
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(this.xCoord - player.posX, this.yCoord - player.posY, this.zCoord - player.posZ).lengthVector() < 25;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		
		if(data.hasKey("toggle")) {
			
			for(DirPos pos : getConPos()) {
				this.tryUnsubscribe(this.tanks[0].getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ());
			}
			
			if(this.tanks[0].getTankType() == Fluids.HOTOIL) {
				this.tanks[0].setTankType(Fluids.HOTCRACKOIL);
			} else {
				this.tanks[0].setTankType(Fluids.HOTOIL);
			}
		}
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { this.tanks[1], this.tanks[2], this.tanks[3], this.tanks[4] };
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { this.tanks[0] };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return this.tanks;
	}

	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN && dir != ForgeDirection.DOWN;
	}

	@Override
	public void explode(World world, int x, int y, int z) {
		
		if(this.hasExploded) return;
		
		this.hasExploded = true;
		this.onFire = true;
		markChanged();
	}

	@Override
	public void tryExtinguish(World world, int x, int y, int z, EnumExtinguishType type) {
		if(!this.hasExploded || !this.onFire) return;
		
		if(type == EnumExtinguishType.FOAM || type == EnumExtinguishType.CO2) {
			this.onFire = false;
			markChanged();
			return;
		}
		
		if(type == EnumExtinguishType.WATER) {
			for(FluidTank tank : this.tanks) {
				if(tank.getFill() > 0) {
					this.worldObj.newExplosion(null, this.xCoord + 0.5, this.yCoord + 1.5, this.zCoord + 0.5, 5F, true, true);
					return;
				}
			}
		}
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

		this.repair.add(new OreDictStack(OreDictManager.STEEL.plate(), 8));
		this.repair.add(new ComparableStack(ModItems.ducttape, 4));
		return this.repair;
	}

	@Override
	public void repair() {
		this.hasExploded = false;
		markChanged();
	}

	@Override
	public void writeNBT(NBTTagCompound nbt) {
		if(this.tanks[0].getFill() == 0 && this.tanks[1].getFill() == 0 && this.tanks[2].getFill() == 0 && this.tanks[3].getFill() == 0 && this.tanks[4].getFill() == 0 && !this.hasExploded) return;
		NBTTagCompound data = new NBTTagCompound();
		for(int i = 0; i < 5; i++) this.tanks[i].writeToNBT(data, "" + i);
		data.setBoolean("hasExploded", this.hasExploded);
		data.setBoolean("onFire", this.onFire);
		nbt.setTag(IPersistentNBT.NBT_PERSISTENT_KEY, data);
	}

	@Override
	public void readNBT(NBTTagCompound nbt) {
		NBTTagCompound data = nbt.getCompoundTag(IPersistentNBT.NBT_PERSISTENT_KEY);
		for(int i = 0; i < 5; i++) this.tanks[i].readFromNBT(data, "" + i);
		this.hasExploded = data.getBoolean("hasExploded");
		this.onFire = data.getBoolean("onFire");
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineRefinery(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineRefinery(player.inventory, this);
	}
}
