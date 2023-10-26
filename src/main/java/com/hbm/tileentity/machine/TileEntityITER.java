package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineITER;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNT;
import com.hbm.explosion.ExplosionNT.ExAttrib;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.container.ContainerITER;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIITER;
import com.hbm.inventory.recipes.BreederRecipes;
import com.hbm.inventory.recipes.BreederRecipes.BreederRecipe;
import com.hbm.inventory.recipes.FusionRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemFusionShield;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityITER extends TileEntityMachineBase implements IEnergyUser, IFluidAcceptor, IFluidSource, IFluidStandardTransceiver, IGUIProvider /* TODO: finish fluid API impl */ {
	
	public long power;
	public static final long maxPower = 10000000;
	public static final int powerReq = 100000;
	public int age = 0;
	public List<IFluidAcceptor> list = new ArrayList<>();
	public FluidTank[] tanks;
	public FluidTank plasma;
	
	public int progress;
	public static final int duration = 100;
	
	@SideOnly(Side.CLIENT)
	public int blanket;
	
	public float rotor;
	public float lastRotor;
	public boolean isOn;

	public TileEntityITER() {
		super(5);
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.WATER, 1280000, 0);
		this.tanks[1] = new FluidTank(Fluids.ULTRAHOTSTEAM, 128000, 1);
		this.plasma = new FluidTank(Fluids.PLASMA_DT, 16000, 2);
	}

	@Override
	public String getName() {
		return "container.machineITER";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			this.age++;
			if (this.age >= 20) {
				this.age = 0;
			}

			if (this.age == 9 || this.age == 19)
				fillFluidInit(this.tanks[1].getTankType());
			
			updateConnections();
			this.power = Library.chargeTEFromItems(this.slots, 0, this.power, TileEntityITER.maxPower);

			/// START Processing part ///
			
			if(!this.isOn) {
				this.plasma.setFill(0);	//jettison plasma if the thing is turned off
			}
			
			//explode either if there's plasma that is too hot or if the reactor is turned on but the magnets have no power
			if(this.plasma.getFill() > 0 && (this.plasma.getTankType().temperature >= getShield() || (this.isOn && this.power < TileEntityITER.powerReq))) {
				explode();
			}
			
			if(this.isOn && this.power >= TileEntityITER.powerReq) {
				this.power -= TileEntityITER.powerReq;
				
				if(this.plasma.getFill() > 0) {
					
					int chance = FusionRecipes.getByproductChance(this.plasma.getTankType());
					
					if(chance > 0 && this.worldObj.rand.nextInt(chance) == 0)
						produceByproduct();
				}
				
				if(this.plasma.getFill() > 0 && getShield() != 0) {
					
					ItemFusionShield.setShieldDamage(this.slots[3], ItemFusionShield.getShieldDamage(this.slots[3]) + 1);
					
					if(ItemFusionShield.getShieldDamage(this.slots[3]) > ((ItemFusionShield)this.slots[3].getItem()).maxDamage) {
						this.slots[3] = null;
						this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, "hbm:block.shutdown", 5F, 1F);
						this.isOn = false;
						markDirty();
					}
				}
				
				int prod = FusionRecipes.getSteamProduction(this.plasma.getTankType());
				
				for(int i = 0; i < 20; i++) {
					
					if(this.plasma.getFill() > 0) {
						
						if(this.tanks[0].getFill() >= prod * 10) {
							this.tanks[0].setFill(this.tanks[0].getFill() - prod * 10);
							this.tanks[1].setFill(this.tanks[1].getFill() + prod);
							
							if(this.tanks[1].getFill() > this.tanks[1].getMaxFill())
								this.tanks[1].setFill(this.tanks[1].getMaxFill());
						}
						
						this.plasma.setFill(this.plasma.getFill() - 1);
					}
				}
			}
			
			doBreederStuff();
			
			/// END Processing part ///

			/// START Notif packets ///
			for (FluidTank tank : this.tanks)
				tank.updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
			this.plasma.updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
			
			for(DirPos pos : getConPos()) {
				if(this.tanks[1].getFill() > 0) {
					this.sendFluid(this.tanks[1], this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("isOn", this.isOn);
			data.setLong("power", this.power);
			data.setInteger("progress", this.progress);
			
			if(this.slots[3] == null) {
				data.setInteger("blanket", 0);
			} else if(this.slots[3].getItem() == ModItems.fusion_shield_tungsten) {
				data.setInteger("blanket", 1);
			} else if(this.slots[3].getItem() == ModItems.fusion_shield_desh) {
				data.setInteger("blanket", 2);
			} else if(this.slots[3].getItem() == ModItems.fusion_shield_chlorophyte) {
				data.setInteger("blanket", 3);
			} else if(this.slots[3].getItem() == ModItems.fusion_shield_vaporwave) {
				data.setInteger("blanket", 4);
			}
			
			networkPack(data, 250);
			/// END Notif packets ///
			
		} else {
			
			this.lastRotor = this.rotor;
			
			if(this.isOn && this.power >= TileEntityITER.powerReq) {
				
				this.rotor += 15F;
				
				if(this.rotor >= 360) {
					this.rotor -= 360;
					this.lastRotor -= 360;
				}
			}
		}
	}
	
	protected List<DirPos> connections;
	
	private void updateConnections() {
		
		for(DirPos pos : getConPos()) {
			this.trySubscribe(this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(this.tanks[0].getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	protected List<DirPos> getConPos() {
		if(this.connections != null && !this.connections.isEmpty())
			return this.connections;
		
		this.connections = new ArrayList<>();

		this.connections.add(new DirPos(this.xCoord, this.yCoord + 3, this.zCoord, ForgeDirection.UP));
		this.connections.add(new DirPos(this.xCoord, this.yCoord - 3, this.zCoord, ForgeDirection.DOWN));
		
		Vec3 vec = Vec3.createVectorHelper(5.75, 0, 0);
		
		for(int i = 0; i < 16; i++) {
			vec.rotateAroundY((float) (Math.PI / 8));
			this.connections.add(new DirPos(this.xCoord + (int)vec.xCoord, this.yCoord + 3, this.zCoord + (int)vec.zCoord, ForgeDirection.UP));
			this.connections.add(new DirPos(this.xCoord + (int)vec.xCoord, this.yCoord - 3, this.zCoord + (int)vec.zCoord, ForgeDirection.DOWN));
		}
		
		return this.connections;
	}
	
	private void explode() {
		disassemble();
		
		if(this.plasma.getTankType() == Fluids.PLASMA_BF) {
			
			this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, "hbm:weapon.mukeExplosion", 15.0F, 1.0F);
			ExplosionLarge.spawnShrapnels(this.worldObj, this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, 50);
			
			ExplosionNT exp = new ExplosionNT(this.worldObj, null, this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, 20F)
					.addAttrib(ExAttrib.BALEFIRE)
					.addAttrib(ExAttrib.NOPARTICLE)
					.addAttrib(ExAttrib.NOSOUND)
					.addAttrib(ExAttrib.NODROP)
					.overrideResolution(64);
			exp.doExplosionA();
			exp.doExplosionB(false);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "muke");
			data.setBoolean("balefire", true);
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 250));
			
		} else {
			Vec3 vec = Vec3.createVectorHelper(5.5, 0, 0);
			vec.rotateAroundY(this.worldObj.rand.nextFloat() * (float)Math.PI * 2F);
			
			this.worldObj.newExplosion(null, this.xCoord + 0.5 + vec.xCoord, this.yCoord + 0.5 + this.worldObj.rand.nextGaussian() * 1.5D, this.zCoord + 0.5 + vec.zCoord, 2.5F, true, true);
		}
		
	}

	private void doBreederStuff() {
		
		if(this.plasma.getFill() == 0) {
			this.progress = 0;
			return;
		}
		
		BreederRecipe out = BreederRecipes.getOutput(this.slots[1]);
		
		if(this.slots[1] != null && this.slots[1].getItem() == ModItems.meteorite_sword_irradiated)
			out = new BreederRecipe(ModItems.meteorite_sword_fused, 1000);
		
		if(this.slots[1] != null && this.slots[1].getItem() == ModItems.meteorite_sword_fused)
			out = new BreederRecipe(ModItems.meteorite_sword_baleful, 4000);
		
		if((out == null) || (this.slots[2] != null && this.slots[2].stackSize >= this.slots[2].getMaxStackSize())) {
			this.progress = 0;
			return;
		}
		
		int level = FusionRecipes.getBreedingLevel(this.plasma.getTankType());
		
		if(out.flux > level) {
			this.progress = 0;
			return;
		}
		
		this.progress++;
		
		if(this.progress > TileEntityITER.duration) {
			
			this.progress = 0;
			
			if(this.slots[2] != null) {
				this.slots[2].stackSize++;
			} else {
				this.slots[2] = out.output.copy();
			}
			
			this.slots[1].stackSize--;
			
			if(this.slots[1].stackSize <= 0)
				this.slots[1] = null;
			
			markDirty();
		}
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[] { 1, 2, 4 };
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		
		if(i == 1 && BreederRecipes.getOutput(itemStack) != null)
			return true;
		
		return false;
	}
	
	private void produceByproduct() {
		
		ItemStack by = FusionRecipes.getByproduct(this.plasma.getTankType());
		
		if(by == null)
			return;
		
		if(this.slots[4] == null) {
			this.slots[4] = by;
			return;
		}
		
		if(this.slots[4].getItem() == by.getItem() && this.slots[4].getItemDamage() == by.getItemDamage() && this.slots[4].stackSize < this.slots[4].getMaxStackSize()) {
			this.slots[4].stackSize++;
		}
	}
	
	public int getShield() {
		
		if(this.slots[3] == null || !(this.slots[3].getItem() instanceof ItemFusionShield))
			return 0;
		
		return ((ItemFusionShield)this.slots[3].getItem()).maxTemp;
	}

	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.isOn = data.getBoolean("isOn");
		this.power = data.getLong("power");
		this.blanket = data.getInteger("blanket");
		this.progress = data.getInteger("progress"); //
	}

	@Override
	public void handleButtonPacket(int value, int meta) {
		
		if(meta == 0) {
			this.isOn = !this.isOn;
		}
	}

	public long getPowerScaled(long i) {
		return (this.power * i) / TileEntityITER.maxPower;
	}

	public long getProgressScaled(long i) {
		return (this.progress * i) / TileEntityITER.duration;
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
		return TileEntityITER.maxPower;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if (index < 2 && this.tanks[index] != null)
			this.tanks[index].setFill(fill);
		
		if(index == 2)
			this.plasma.setFill(fill);
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if (type.name().equals(this.tanks[0].getTankType().name()))
			this.tanks[0].setFill(i);
		else if (type.name().equals(this.tanks[1].getTankType().name()))
			this.tanks[1].setFill(i);
		else if (type.name().equals(this.plasma.getTankType().name()))
			this.plasma.setFill(i);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if (index < 2 && this.tanks[index] != null)
			this.tanks[index].setTankType(type);
		
		if(index == 2)
			this.plasma.setTankType(type);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if (type.name().equals(this.tanks[0].getTankType().name()))
			return this.tanks[0].getFill();
		else if (type.name().equals(this.tanks[1].getTankType().name()))
			return this.tanks[1].getFill();
		else if (type.name().equals(this.plasma.getTankType().name()))
			return this.plasma.getFill();
		else
			return 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(this.xCoord, this.yCoord - 3, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord + 3, this.zCoord, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, this.worldObj, type);
	}

	@Override
	public boolean getTact() {
		if (this.age >= 0 && this.age < 10) {
			return true;
		}

		return false;
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
	public int getMaxFluidFill(FluidType type) {
		if (type.name().equals(this.tanks[0].getTankType().name()))
			return this.tanks[0].getMaxFill();
		else if (type.name().equals(this.tanks[1].getTankType().name()))
			return this.tanks[1].getMaxFill();
		else if (type.name().equals(this.plasma.getTankType().name()))
			return this.plasma.getMaxFill();
		else
			return 0;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.power = nbt.getLong("power");
		this.isOn = nbt.getBoolean("isOn");

		this.tanks[0].readFromNBT(nbt, "water");
		this.tanks[1].readFromNBT(nbt, "steam");
		this.plasma.readFromNBT(nbt, "plasma");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", this.power);
		nbt.setBoolean("isOn", this.isOn);

		this.tanks[0].writeToNBT(nbt, "water");
		this.tanks[1].writeToNBT(nbt, "steam");
		this.plasma.writeToNBT(nbt, "plasma");
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(
					this.xCoord + 0.5 - 8,
					this.yCoord + 0.5 - 3,
					this.zCoord + 0.5 - 8,
					this.xCoord + 0.5 + 8,
					this.yCoord + 0.5 + 3,
					this.zCoord + 0.5 + 8
					);
		}
		
		return this.bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	@SuppressWarnings("unchecked")
	public void disassemble() {
		
		MachineITER.drop = false;
		
		int[][][] layout = TileEntityITERStruct.layout;
		
		for(int y = 0; y < 5; y++) {
			for(int x = 0; x < layout[0].length; x++) {
				for(int z = 0; z < layout[0][0].length; z++) {
					
					int ly = y > 2 ? 4 - y : y;
					
					int width = 7;
					
					if(x == width && y == 0 && z == width)
						continue;
					
					int b = layout[ly][x][z];
					
					switch(b) {
					case 1: this.worldObj.setBlock(this.xCoord - width + x, this.yCoord + y - 2, this.zCoord - width + z, ModBlocks.fusion_conductor, 1, 3); break;
					case 2: this.worldObj.setBlock(this.xCoord - width + x, this.yCoord + y - 2, this.zCoord - width + z, ModBlocks.fusion_center); break;
					case 3: this.worldObj.setBlock(this.xCoord - width + x, this.yCoord + y - 2, this.zCoord - width + z, ModBlocks.fusion_motor); break;
					case 4: this.worldObj.setBlock(this.xCoord - width + x, this.yCoord + y - 2, this.zCoord - width + z, ModBlocks.reinforced_glass); break;
					}
				}
			}
		}
		
		this.worldObj.setBlock(this.xCoord, this.yCoord - 2, this.zCoord, ModBlocks.struct_iter_core);
		
		MachineITER.drop = true;
		
		List<EntityPlayer> players = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class,
				AxisAlignedBB.getBoundingBox(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5).expand(50, 10, 50));
		
		for(EntityPlayer player : players) {
			player.triggerAchievement(MainRegistry.achMeltdown);
		}
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {this.tanks[1]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {this.tanks[0]};
	}

	@Override
	public FluidTank[] getAllTanks() {
		return this.tanks;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir == ForgeDirection.UP || dir == ForgeDirection.DOWN;
	}

	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir == ForgeDirection.UP || dir == ForgeDirection.DOWN;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerITER(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIITER(player.inventory, this);
	}
}
