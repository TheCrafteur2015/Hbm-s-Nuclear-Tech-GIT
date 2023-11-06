package com.hbm.tileentity.machine;

import java.util.List;
import java.util.Map.Entry;

import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityBlackHole;
import com.hbm.entity.logic.EntityBalefire;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionThermo;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.container.ContainerMachineCyclotron;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineCyclotron;
import com.hbm.inventory.recipes.CyclotronRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.lib.Library;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IConditionalInvAccess;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.Tuple.Pair;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineCyclotron extends TileEntityMachineBase implements IFluidSource, IFluidAcceptor, IEnergyUser, IFluidStandardTransceiver, IGUIProvider, IConditionalInvAccess {
	
	public long power;
	public static final long maxPower = 100000000;
	public int consumption = 1000000;
	
	public boolean isOn;
	
	private int age;
	private int countdown;
	
	private byte plugs; 
	
	public int progress;
	public static final int duration = 690;
	
	public FluidTank coolant;
	public FluidTank amat;

	public TileEntityMachineCyclotron() {
		super(16);

		this.coolant = new FluidTank(Fluids.COOLANT, 32000);
		this.amat = new FluidTank(Fluids.AMAT, 8000);
	}

	@Override
	public String getName() {
		return "container.cyclotron";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			updateConnections();

			this.age++;
			if(this.age >= 20)
			{
				this.age = 0;
			}
			
			if(this.age == 9 || this.age == 19)
				fillFluidInit(this.amat.getTankType());

			this.power = Library.chargeTEFromItems(this.slots, 13, this.power, TileEntityMachineCyclotron.maxPower);
			this.coolant.loadTank(11, 12, this.slots);
			this.amat.unloadTank(9, 10, this.slots);
			
			if(this.isOn) {
				
				int defConsumption = this.consumption - 100000 * getConsumption();
				
				if(canProcess() && this.power >= defConsumption) {
					
					this.progress += getSpeed();
					this.power -= defConsumption;
					
					if(this.progress >= TileEntityMachineCyclotron.duration) {
						process();
						this.progress = 0;
						markDirty();
					}
					
					if(this.coolant.getFill() > 0) {

			    		this.countdown = 0;
						
						if(this.worldObj.rand.nextInt(3) == 0)
							this.coolant.setFill(this.coolant.getFill() - 1);
						
					} else if(this.worldObj.rand.nextInt(getSafety()) == 0) {
						
						this.countdown++;
						
						int chance = 7 - Math.min((int) Math.ceil(this.countdown / 200D), 6);
						
						if(this.worldObj.rand.nextInt(chance) == 0)
							ExplosionLarge.spawnTracers(this.worldObj, this.xCoord + 0.5, this.yCoord + 3.25, this.zCoord + 0.5, 1);
						
						if(this.countdown > 1000) {
							ExplosionThermo.setEntitiesOnFire(this.worldObj, this.xCoord + 0.5, this.yCoord + 1.5, this.zCoord + 0.5, 25);
							ExplosionThermo.scorchLight(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 7);
							
							if(this.countdown % 4 == 0)
								ExplosionLarge.spawnBurst(this.worldObj, this.xCoord + 0.5, this.yCoord + 3.25, this.zCoord + 0.5, 18, 1);
							
						} else if(this.countdown > 600) {
							ExplosionThermo.setEntitiesOnFire(this.worldObj, this.xCoord + 0.5, this.yCoord + 1.5, this.zCoord + 0.5, 10);
						}
						
						if(this.countdown == 1140)
							this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 1.5, this.zCoord + 0.5, "hbm:block.shutdown", 10.0F, 1.0F);
						
						if(this.countdown > 1200)
							explode();
					}
					
				} else {
					this.progress = 0;
				}
				
			} else {
				this.progress = 0;
			}
			
			this.sendFluid();
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setInteger("progress", this.progress);
			data.setBoolean("isOn", this.isOn);
			data.setByte("plugs", this.plugs);
			networkPack(data, 25);
			
			this.coolant.updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
			this.amat.updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
		}
	}
	
	private void updateConnections()  {
		for(DirPos pos : getConPos()) {
			this.trySubscribe(this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(this.coolant.getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	private void sendFluid() {
		for(DirPos pos : getConPos()) {
			this.sendFluid(this.amat, this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	public DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(this.xCoord + 3, this.yCoord, this.zCoord + 1, Library.POS_X),
				new DirPos(this.xCoord + 3, this.yCoord, this.zCoord - 1, Library.POS_X),
				new DirPos(this.xCoord - 3, this.yCoord, this.zCoord + 1, Library.NEG_X),
				new DirPos(this.xCoord - 3, this.yCoord, this.zCoord - 1, Library.NEG_X),
				new DirPos(this.xCoord + 1, this.yCoord, this.zCoord + 3, Library.POS_Z),
				new DirPos(this.xCoord - 1, this.yCoord, this.zCoord + 3, Library.POS_Z),
				new DirPos(this.xCoord + 1, this.yCoord, this.zCoord - 3, Library.NEG_Z),
				new DirPos(this.xCoord - 1, this.yCoord, this.zCoord - 3, Library.NEG_Z)
		};
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.isOn = data.getBoolean("isOn");
		this.power = data.getLong("power");
		this.progress = data.getInteger("progress");
		this.plugs = data.getByte("plugs");
	}
	
	@Override
	public void handleButtonPacket(int value, int meta) {
		
		this.isOn = !this.isOn;
	}
	
	private void explode() {

		ExplosionLarge.explodeFire(this.worldObj, this.xCoord + 0.5, this.yCoord + 1.5, this.zCoord + 0.5, 25, true, false, true);
		
		int rand = this.worldObj.rand.nextInt(10);

		if(rand < 2) {
			
			this.worldObj.spawnEntityInWorld(EntityNukeExplosionMK5.statFac(this.worldObj, (int)(BombConfig.fatmanRadius * 1.5), this.xCoord + 0.5, this.yCoord + 1.5, this.zCoord + 0.5).mute());
			
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "muke");
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, 250));
			this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, "hbm:weapon.mukeExplosion", 15.0F, 1.0F);
			
		} else if(rand < 4) {
			
			EntityBalefire bf = new EntityBalefire(this.worldObj).mute();
			bf.posX = this.xCoord + 0.5;
			bf.posY = this.yCoord + 1.5;
			bf.posZ = this.zCoord + 0.5;
			bf.destructionRange = (int)(BombConfig.fatmanRadius * 1.5);
			this.worldObj.spawnEntityInWorld(bf);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "muke");
			data.setBoolean("balefire", true);
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, 250));
			this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, "hbm:weapon.mukeExplosion", 15.0F, 1.0F);
			
		} else if(rand < 5) {
			
			EntityBlackHole bl = new EntityBlackHole(this.worldObj, 1.5F + this.worldObj.rand.nextFloat());
			bl.posX = this.xCoord + 0.5F;
			bl.posY = this.yCoord + 1.5F;
			bl.posZ = this.zCoord + 0.5F;
			this.worldObj.spawnEntityInWorld(bl);
		}
	}
	
	public boolean canProcess() {
		
		for(int i = 0; i < 3; i++) {
			
			Object[] res = CyclotronRecipes.getOutput(this.slots[i + 3], this.slots[i]);
			
			if(res == null)
				continue;
			
			ItemStack out = (ItemStack)res[0];
			
			if(out == null)
				continue;
			
			if((this.slots[i + 6] == null) || (this.slots[i + 6].getItem() == out.getItem() && this.slots[i + 6].getItemDamage() == out.getItemDamage() && this.slots[i + 6].stackSize < out.getMaxStackSize()))
				return true;
		}
		
		return false;
	}
	
	public void process() {
		
		for(int i = 0; i < 3; i++) {
			
			Object[] res = CyclotronRecipes.getOutput(this.slots[i + 3], this.slots[i]);
			
			if(res == null)
				continue;
			
			ItemStack out = (ItemStack)res[0];
			
			if(out == null)
				continue;
			
			if(this.slots[i + 6] == null) {
				
				decrStackSize(i, 1);
				decrStackSize(i + 3, 1);
				this.slots[i + 6] = out;
				
				this.amat.setFill(this.amat.getFill() + (Integer)res[1]);
				
				continue;
			}
			
			if(this.slots[i + 6].getItem() == out.getItem() && this.slots[i + 6].getItemDamage() == out.getItemDamage() && this.slots[i + 6].stackSize < out.getMaxStackSize()) {
				
				decrStackSize(i, 1);
				decrStackSize(i + 3, 1);
				this.slots[i + 6].stackSize++;
				
				this.amat.setFill(this.amat.getFill() + (Integer)res[1]);
			}
		}
		
		if(this.amat.getFill() > this.amat.getMaxFill())
			this.amat.setFill(this.amat.getMaxFill());
	}
	
	public int getSpeed() {
		
		int speed = 1;
		
		for(int i = 14; i < 16; i++) {
			
			if(this.slots[i] != null) {
				
				if(this.slots[i].getItem() == ModItems.upgrade_speed_1)
					speed += 1;
				else if(this.slots[i].getItem() == ModItems.upgrade_speed_2)
					speed += 2;
				else if(this.slots[i].getItem() == ModItems.upgrade_speed_3)
					speed += 3;
			}
		}
		
		return Math.min(speed, 4);
	}
	
	public int getConsumption() {
		
		int speed = 0;
		
		for(int i = 14; i < 16; i++) {
			
			if(this.slots[i] != null) {
				
				if(this.slots[i].getItem() == ModItems.upgrade_power_1)
					speed += 1;
				else if(this.slots[i].getItem() == ModItems.upgrade_power_2)
					speed += 2;
				else if(this.slots[i].getItem() == ModItems.upgrade_power_3)
					speed += 3;
			}
		}
		
		return Math.min(speed, 3);
	}
	
	public int getSafety() {
		
		int speed = 1;
		
		for(int i = 14; i < 16; i++) {
			
			if(this.slots[i] != null) {
				
				if(this.slots[i].getItem() == ModItems.upgrade_effect_1)
					speed += 1;
				else if(this.slots[i].getItem() == ModItems.upgrade_effect_2)
					speed += 2;
				else if(this.slots[i].getItem() == ModItems.upgrade_effect_3)
					speed += 3;
			}
		}
		
		return Math.min(speed, 4);
	}

	public long getPowerScaled(long i) {
		return (this.power * i) / TileEntityMachineCyclotron.maxPower;
	}

	public int getProgressScaled(int i) {
		return (this.progress * i) / TileEntityMachineCyclotron.duration;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		
		if(index == 0)
			this.coolant.setFill(fill);
		else if(index == 1)
			this.amat.setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		if(type == Fluids.COOLANT)
			this.coolant.setFill(fill);
		else if(type == Fluids.AMAT)
			this.amat.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if(index == 0)
			this.coolant.setTankType(type);
		else if(index == 1)
			this.amat.setTankType(type);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type == Fluids.COOLANT)
			return this.coolant.getFill();
		else if(type == Fluids.AMAT)
			return this.amat.getFill();
		
		return 0;
	}

	@Override public void fillFluidInit(FluidType type) { }
	@Override public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) { }
	@Override public boolean getTact() { return false; }
	@Override public List<IFluidAcceptor> getFluidList(FluidType type) { return null; }
	@Override public void clearFluidList(FluidType type) { }

	@Override
	public int getMaxFluidFill(FluidType type) {
		
		if(type == Fluids.COOLANT)
			return this.coolant.getMaxFill();
		
		return 0;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(this.xCoord - 2, this.yCoord, this.zCoord - 2, this.xCoord + 3, this.yCoord + 4, this.zCoord + 3);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.coolant.readFromNBT(nbt, "coolant");
		this.amat.readFromNBT(nbt, "amat");
		
		this.isOn = nbt.getBoolean("isOn");
		this.countdown = nbt.getInteger("countdown");
		this.progress = nbt.getInteger("progress");
		this.power = nbt.getLong("power");
		this.plugs = nbt.getByte("plugs");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		this.coolant.writeToNBT(nbt, "coolant");
		this.amat.writeToNBT(nbt, "amat");
		
		nbt.setBoolean("isOn", this.isOn);
		nbt.setInteger("countdown", this.countdown);
		nbt.setInteger("progress", this.progress);
		nbt.setLong("power", this.power);
		nbt.setByte("plugs", this.plugs);
	}
	
	public void setPlug(int index) {
		this.plugs |= (1 << index);
		markDirty();
	}
	
	public boolean getPlug(int index) {
		return (this.plugs & (1 << index)) > 0;
	}
	
	public static Item getItemForPlug(int i) {
		
		switch(i) {
		case 0: return ModItems.powder_balefire;
		case 1: return ModItems.book_of_;
		case 2: return ModItems.diamond_gavel;
		case 3: return ModItems.coin_maskman;
		}
		
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		super.setInventorySlotContents(i, stack);
		
		if(stack != null && i >= 14 && i <= 15 && stack.getItem() instanceof ItemMachineUpgrade)
			this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 1.5, this.zCoord + 0.5, "hbm:item.upgradePlug", 1.5F, 1.0F);
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
		return TileEntityMachineCyclotron.maxPower;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { this.amat };
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { this.coolant };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { this.amat, this.coolant };
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineCyclotron(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineCyclotron(player.inventory, this);
	}

	@Override
	public boolean isItemValidForSlot(int x, int y, int z, int slot, ItemStack stack) {
		
		if(slot < 3) {
			for(Entry<Pair<ComparableStack, AStack>, Pair<ItemStack, Integer>> entry : CyclotronRecipes.recipes.entrySet()) {
				if(entry.getKey().getKey().matchesRecipe(stack, true)) return true;
			}
		} else if(slot < 6) {

			for(Entry<Pair<ComparableStack, AStack>, Pair<ItemStack, Integer>> entry : CyclotronRecipes.recipes.entrySet()) {
				if(entry.getKey().getValue().matchesRecipe(stack, true)) return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean canInsertItem(int x, int y, int z, int slot, ItemStack stack, int side) {
		return this.isItemValidForSlot(x, y, z, slot, stack);
	}

	@Override
	public boolean canExtractItem(int x, int y, int z, int slot, ItemStack stack, int side) {
		return slot >= 6 && slot <= 8;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int x, int y, int z, int side) {
		
		for(int i = 2; i < 6; i++) {
			ForgeDirection dir = ForgeDirection.getOrientation(i);
			ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

			if(x == this.xCoord + dir.offsetX * 2 + rot.offsetX && z == this.zCoord + dir.offsetZ * 2 + rot.offsetZ) return new int[] {0, 3, 6, 7, 8};
			if(x == this.xCoord + dir.offsetX * 2 && z == this.zCoord + dir.offsetZ * 2) return new int[] {1, 4, 6, 7, 8};
			if(x == this.xCoord + dir.offsetX * 2 - rot.offsetX && z == this.zCoord + dir.offsetZ * 2 - rot.offsetZ) return new int[] {2, 5, 6, 7, 8};
		}
		
		return new int[] {6, 7, 8};
	}
}
