package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.missile.EntitySoyuz;
import com.hbm.handler.MissileStruct;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.inventory.container.ContainerSoyuzLauncher;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUISoyuzLauncher;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardReceiver;
import api.hbm.item.IDesignatorItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntitySoyuzLauncher extends TileEntityMachineBase implements ISidedInventory, IEnergyUser, IFluidContainer, IFluidAcceptor, IFluidStandardReceiver, IGUIProvider {

	public long power;
	public static final long maxPower = 1000000;
	public FluidTank[] tanks;
	//0: sat, 1: cargo
	public byte mode;
	public boolean starting;
	public int countdown;
	public static final int maxCount = 600;
	public byte rocketType = -1;
	
	private AudioWrapper audio;
	
	public MissileStruct load;

	public TileEntitySoyuzLauncher() {
		super(27);
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.KEROSENE, 128000, 0);
		this.tanks[1] = new FluidTank(Fluids.OXYGEN, 128000, 1);
	}

	@Override
	public String getName() {
		return "container.soyuzLauncher";
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateEntity() {

		if (!this.worldObj.isRemote) {
			
			if(this.worldObj.getTotalWorldTime() % 20 == 0) {
				for(DirPos pos : getConPos()) {
					this.trySubscribe(this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
					this.trySubscribe(this.tanks[0].getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
					this.trySubscribe(this.tanks[1].getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				}
			}
			
			this.tanks[0].loadTank(4, 5, this.slots);
			this.tanks[1].loadTank(6, 7, this.slots);

			for (int i = 0; i < 2; i++)
				this.tanks[i].updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
			
			this.power = Library.chargeTEFromItems(this.slots, 8, this.power, TileEntitySoyuzLauncher.maxPower);
			
			if(!this.starting || !canLaunch()) {
				this.countdown = TileEntitySoyuzLauncher.maxCount;
				this.starting = false;
			} else if(this.countdown > 0) {
				this.countdown--;
				
				if(this.countdown % 100 == 0 && this.countdown > 0)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:alarm.hatch", 100F, 1.1F);
				
			} else {
				liftOff();
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setByte("mode", this.mode);
			data.setBoolean("starting", this.starting);
			data.setByte("type", getType());
			networkPack(data, 250);
		}
		
		if(this.worldObj.isRemote) {
			if(!this.starting || !canLaunch()) {
				
				if(this.audio != null) {
					this.audio.stopSound();
					this.audio = null;
				}
				
				this.countdown = TileEntitySoyuzLauncher.maxCount;
				
			} else if(this.countdown > 0) {

				if(this.audio == null) {
					this.audio = createAudioLoop();
					this.audio.updateVolume(100);
					this.audio.startSound();
				} else if(!this.audio.isPlaying()) {
					this.audio = rebootAudio(this.audio);
				}
				
				this.countdown--;
			}
			
			List<EntitySoyuz> entities = this.worldObj.getEntitiesWithinAABB(EntitySoyuz.class, AxisAlignedBB.getBoundingBox(this.xCoord - 0.5, this.yCoord, this.zCoord - 0.5, this.xCoord + 1.5, this.yCoord + 10, this.zCoord + 1.5));
			
			if(!entities.isEmpty()) {
				
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "smoke");
				data.setString("mode", "shockRand");
				data.setInteger("count", 50);
				data.setDouble("strength", this.worldObj.rand.nextGaussian() * 3 + 6);
				data.setDouble("posX", this.xCoord + 0.5);
				data.setDouble("posY", this.yCoord - 3);
				data.setDouble("posZ", this.zCoord + 0.5);
				
				MainRegistry.proxy.effectNT(data);
			}
		}
	}
	
	protected List<DirPos> conPos;
	protected List<DirPos> getConPos() {
		
		if(this.conPos != null)
			return this.conPos;
		
		this.conPos = new ArrayList<>();
		
		for(ForgeDirection dir : new ForgeDirection[] {Library.POS_X, Library.POS_Z, Library.NEG_X, Library.NEG_Z}) {
			ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
			
			for(int i = -6; i <= 6; i++) {
				this.conPos.add(new DirPos(this.xCoord + dir.offsetX * 7 + rot.offsetX * i, this.yCoord + 0, this.zCoord + dir.offsetZ * 7 + rot.offsetZ * i, dir));
				this.conPos.add(new DirPos(this.xCoord + dir.offsetX * 7 + rot.offsetX * i, this.yCoord - 1, this.zCoord + dir.offsetZ * 7 + rot.offsetZ * i, dir));
			}
		}
		
		return this.conPos;
	}
	
	@Override
	public AudioWrapper createAudioLoop() {
		return MainRegistry.proxy.getLoopedSound("hbm:block.soyuzReady", this.xCoord, this.yCoord, this.zCoord, 2.0F, 100F, 1.0F);
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
	public void networkUnpack(NBTTagCompound data) {
		this.power = data.getLong("power");
		this.mode = data.getByte("mode");
		this.starting = data.getBoolean("starting");
		this.rocketType = data.getByte("type");
	}
	
	public void startCountdown() {
		
		if(canLaunch())
			this.starting = true;
	}
	
	public void liftOff() {
		
		this.starting = false;
		
		int req = getFuelRequired();
		int pow = getPowerRequired();
		
		EntitySoyuz soyuz = new EntitySoyuz(this.worldObj);
		soyuz.setSkin(getType());
		soyuz.mode = this.mode;
		soyuz.setLocationAndAngles(this.xCoord + 0.5, this.yCoord + 1, this.zCoord + 0.5, 0, 0);
		this.worldObj.spawnEntityInWorld(soyuz);

		this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:entity.soyuzTakeoff", 100F, 1.1F);

		this.tanks[0].setFill(this.tanks[0].getFill() - req);
		this.tanks[1].setFill(this.tanks[1].getFill() - req);
		this.power -= pow;
		
		if(this.mode == 0) {
			soyuz.setSat(this.slots[2]);
			
			if(orbital() == 2)
				this.slots[3] = null;
			
			this.slots[2] = null;
		}
		
		if(this.mode == 1) {
			List<ItemStack> payload = new ArrayList<>();
			
			for(int i = 9; i < 27; i++) {
				payload.add(this.slots[i]);
				this.slots[i] = null;
			}

			soyuz.targetX = this.slots[1].stackTagCompound.getInteger("xCoord");
			soyuz.targetZ = this.slots[1].stackTagCompound.getInteger("zCoord");
			soyuz.setPayload(payload);
		}
		
		this.slots[0] = null;
	}
	
	public boolean canLaunch() {
		
		return hasRocket() && hasFuel() && hasRocket() && hasPower() && designator() != 1 && orbital() != 1 && satellite() != 1;
	}
	
	public boolean hasFuel() {
		
		return this.tanks[0].getFill() >= getFuelRequired();
	}
	
	public boolean hasOxy() {

		return this.tanks[1].getFill() >= getFuelRequired();
	}
	
	public int getFuelRequired() {
		
		if(this.mode == 1)
			return 20000 + getDist();
		
		return 128000;
	}
	
	public int getDist() {
		
		if(designator() == 2) {
			int x = this.slots[1].stackTagCompound.getInteger("xCoord");
			int z = this.slots[1].stackTagCompound.getInteger("zCoord");
			
			return (int) Vec3.createVectorHelper(this.xCoord - x, 0, this.zCoord - z).lengthVector();
		}
			
		return 0;
	}
	
	public boolean hasPower() {
		
		return this.power >= getPowerRequired();
	}
	
	public int getPowerRequired() {
		
		return (int) (TileEntitySoyuzLauncher.maxPower * 0.75);
	}
	
	private byte getType() {
		
		if(!hasRocket())
			return -1;
		
		return (byte) this.slots[0].getItemDamage();
	}
	
	public long getPowerScaled(long i) {
		return (this.power * i) / TileEntitySoyuzLauncher.maxPower;
	}
	
	public boolean hasRocket() {
		return this.slots[0] != null && this.slots[0].getItem() == ModItems.missile_soyuz;
	}
	
	//0: designator not required
	//1: designator required but not present
	//2: designator present
	public int designator() {
		
		if(this.mode == 0) {
			return 0;
		}
		if(this.slots[1] != null && this.slots[1].getItem() instanceof IDesignatorItem && ((IDesignatorItem)this.slots[1].getItem()).isReady(this.worldObj, this.slots[1], this.xCoord, this.yCoord, this.zCoord)) {
			return 2;
		}
		
		return 1;
	}
	
	//0: sat not required
	//1: sat required but not present
	//2: sat present
	public int satellite() {
		
		if(this.mode == 1)
			return 0;
		
		if(this.slots[2] != null) {
			return 2;
		}
		return 1;
	}

	//0: module not required
	//1: module required but not present
	//2: module present
	public int orbital() {
		
		if(this.mode == 1)
			return 0;
		
		if(this.slots[2] != null && (this.slots[2].getItem() == ModItems.sat_gerald || this.slots[2].getItem() == ModItems.sat_lunar_miner)) {
			if(this.slots[3] != null && this.slots[3].getItem() == ModItems.missile_soyuz_lander)
				return 2;
			return 1;
		}
		return 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);

		this.tanks[0].readFromNBT(nbt, "fuel");
		this.tanks[1].readFromNBT(nbt, "oxidizer");
		this.power = nbt.getLong("power");
		this.mode = nbt.getByte("mode");

		this.slots = new ItemStack[getSizeInventory()];

		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if (b0 >= 0 && b0 < this.slots.length) {
				this.slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		NBTTagList list = new NBTTagList();

		this.tanks[0].writeToNBT(nbt, "fuel");
		this.tanks[1].writeToNBT(nbt, "oxidizer");
		nbt.setLong("power", this.power);
		nbt.setByte("mode", this.mode);

		for (int i = 0; i < this.slots.length; i++) {
			if (this.slots[i] != null) {
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte) i);
				this.slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if (type.name().equals(this.tanks[0].getTankType().name()))
			return this.tanks[0].getMaxFill();
		else if (type.name().equals(this.tanks[1].getTankType().name()))
			return this.tanks[1].getMaxFill();
		else
			return 0;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if (index < 2 && this.tanks[index] != null)
			this.tanks[index].setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		if (type.name().equals(this.tanks[0].getTankType().name()))
			this.tanks[0].setFill(fill);
		else if (type.name().equals(this.tanks[1].getTankType().name()))
			this.tanks[1].setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if (index < 2 && this.tanks[index] != null)
			this.tanks[index].setTankType(type);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if (type.name().equals(this.tanks[0].getTankType().name()))
			return this.tanks[0].getFill();
		else if (type.name().equals(this.tanks[1].getTankType().name()))
			return this.tanks[1].getFill();
		else
			return 0;
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
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return TileEntitySoyuzLauncher.maxPower;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return this.tanks;
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return this.tanks;
	}
	
	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN && dir != ForgeDirection.UP && dir != ForgeDirection.DOWN;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerSoyuzLauncher(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUISoyuzLauncher(player.inventory, this);
	}
}
