package com.hbm.tileentity.machine;

import java.util.HashMap;

import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.container.ContainerMachineGasCent;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineGasCent;
import com.hbm.inventory.recipes.GasCentrifugeRecipes.PseudoFluidType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.lib.Library;
import com.hbm.packet.LoopedSoundPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.InventoryUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

//epic!
public class TileEntityMachineGasCent extends TileEntityMachineBase implements IEnergyUser, IFluidAcceptor, IFluidStandardReceiver, IGUIProvider {
	
	public long power;
	public int progress;
	public boolean isProgressing;
	public static final int maxPower = 100000;
	public static final int processingSpeed = 150;
	
	public FluidTank tank;
	public PseudoFluidTank inputTank;
	public PseudoFluidTank outputTank;
	
	private static final int[] slots_io = new int[] { 0, 1, 2, 3 };
	
	private static HashMap<FluidType, PseudoFluidType> fluidConversions = new HashMap<>();
	
	static {
		TileEntityMachineGasCent.fluidConversions.put(Fluids.UF6, PseudoFluidType.NUF6);
		TileEntityMachineGasCent.fluidConversions.put(Fluids.PUF6, PseudoFluidType.PF6);
		TileEntityMachineGasCent.fluidConversions.put(Fluids.WATZ, PseudoFluidType.MUD);
	}
	
	public TileEntityMachineGasCent() {
		super(7); 
		this.tank = new FluidTank(Fluids.UF6, 2000);
		this.inputTank = new PseudoFluidTank(PseudoFluidType.NUF6, 8000);
		this.outputTank = new PseudoFluidTank(PseudoFluidType.LEUF6, 8000);
	}
	
	@Override
	public String getName() {
		return "container.gasCentrifuge";
	}
	
	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i < 4;
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return TileEntityMachineGasCent.slots_io;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.power = nbt.getLong("power");
		this.progress = nbt.getShort("progress");
		this.tank.readFromNBT(nbt, "tank");
		this.inputTank.readFromNBT(nbt, "inputTank");
		this.outputTank.readFromNBT(nbt, "outputTank");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", this.power);
		nbt.setShort("progress", (short) this.progress);
		this.tank.writeToNBT(nbt, "tank");
		this.inputTank.writeToNBT(nbt, "inputTank");
		this.outputTank.writeToNBT(nbt, "outputTank");
	}
	
	public int getCentrifugeProgressScaled(int i) {
		return (this.progress * i) / getProcessingSpeed();
	}
	
	public long getPowerRemainingScaled(int i) {
		return (this.power * i) / TileEntityMachineGasCent.maxPower;
	}
	
	private boolean canEnrich() {
		if(this.power > 0 && this.inputTank.getFill() >= this.inputTank.getTankType().getFluidConsumed() && this.outputTank.getFill() + this.inputTank.getTankType().getFluidProduced() <= this.outputTank.getMaxFill()) {
			
			ItemStack[] list = this.inputTank.getTankType().getOutput();
			
			if(this.inputTank.getTankType().getIfHighSpeed())
				if(!(this.slots[6] != null && this.slots[6].getItem() == ModItems.upgrade_gc_speed))
					return false;
			
			if((list == null) || (list.length < 1))
				return false;
			
			if(InventoryUtil.doesArrayHaveSpace(this.slots, 0, 3, list))
				return true;
		}
		
		return false;
	}
	
	private void enrich() {
		ItemStack[] output = this.inputTank.getTankType().getOutput();
		
		this.progress = 0;
		this.inputTank.setFill(this.inputTank.getFill() - this.inputTank.getTankType().getFluidConsumed()); 
		this.outputTank.setFill(this.outputTank.getFill() + this.inputTank.getTankType().getFluidProduced()); 
		
		for(byte i = 0; i < output.length; i++)
			InventoryUtil.tryAddItemToInventory(this.slots, 0, 3, output[i].copy()); //reference types almost got me again
	}
	
	private void attemptConversion() {
		if(this.inputTank.getFill() < this.inputTank.getMaxFill() && this.tank.getFill() > 0) {
			int fill = Math.min(this.inputTank.getMaxFill() - this.inputTank.getFill(), this.tank.getFill());
			
			this.tank.setFill(this.tank.getFill() - fill);
			this.inputTank.setFill(this.inputTank.getFill() + fill);
		}
	}
	
	private boolean attemptTransfer(TileEntity te) {
		if(te instanceof TileEntityMachineGasCent) {
			TileEntityMachineGasCent cent = (TileEntityMachineGasCent) te;
			
			if(cent.tank.getFill() == 0 && cent.tank.getTankType() == this.tank.getTankType()) {
				if(cent.inputTank.getTankType() != this.outputTank.getTankType() && this.outputTank.getTankType() != PseudoFluidType.NONE) {
					cent.inputTank.setTankType(this.outputTank.getTankType());
					cent.outputTank.setTankType(this.outputTank.getTankType().getOutputType());
				}
				
				//God, why did I forget about the entirety of the fucking math library?
				if(cent.inputTank.getFill() < cent.inputTank.getMaxFill() && this.outputTank.getFill() > 0) {
					int fill = Math.min(cent.inputTank.getMaxFill() - cent.inputTank.getFill(), this.outputTank.getFill());
					
					this.outputTank.setFill(this.outputTank.getFill() - fill);
					cent.inputTank.setFill(cent.inputTank.getFill() + fill);
				}
				
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.power = data.getLong("power");
		this.progress = data.getInteger("progress");
		this.isProgressing = data.getBoolean("isProgressing");
		this.inputTank.setTankType(PseudoFluidType.valueOf(data.getString("inputType")));
		this.outputTank.setTankType(PseudoFluidType.valueOf(data.getString("outputType")));
		this.inputTank.setFill(data.getInteger("inputFill"));
		this.outputTank.setFill(data.getInteger("outputFill"));
	}
	
	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			updateConnections();

			this.power = Library.chargeTEFromItems(this.slots, 4, this.power, TileEntityMachineGasCent.maxPower);
			setTankType(5);
			this.tank.updateTank(this);
			
			if(TileEntityMachineGasCent.fluidConversions.containsValue(this.inputTank.getTankType())) {
				attemptConversion();
			}
			
			if(canEnrich()) {
				
				this.isProgressing = true;
				this.progress++;
				
				if(this.slots[6] != null && this.slots[6].getItem() == ModItems.upgrade_gc_speed)
					this.power -= 300;
				else
					this.power -= 200;
				
				if(this.power < 0) {
					this.power = 0;
					this.progress = 0;
				}
				
				if(this.progress >= getProcessingSpeed())
					enrich();
				
			} else {
				this.isProgressing = false;
				this.progress = 0;
			}
			
			if(this.worldObj.getTotalWorldTime() % 10 == 0) {
				ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
				TileEntity te = this.worldObj.getTileEntity(this.xCoord - dir.offsetX, this.yCoord, this.zCoord - dir.offsetZ);
				
				//*AT THE MOMENT*, there's not really any need for a dedicated method for this. Yet.
				if(!attemptTransfer(te) && this.inputTank.getTankType() == PseudoFluidType.LEUF6) {
					ItemStack[] converted = new ItemStack[] { new ItemStack(ModItems.nugget_uranium_fuel, 6), new ItemStack(ModItems.fluorite) };
					
					if(this.outputTank.getFill() >= 600 && InventoryUtil.doesArrayHaveSpace(this.slots, 0, 3, converted)) {
						this.outputTank.setFill(this.outputTank.getFill() - 600);
						for(ItemStack stack : converted)
							InventoryUtil.tryAddItemToInventory(this.slots, 0, 3, stack);
					}
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setInteger("progress", this.progress);
			data.setBoolean("isProgressing", this.isProgressing);
			data.setInteger("inputFill", this.inputTank.getFill());
			data.setInteger("outputFill", this.outputTank.getFill());
			data.setString("inputType", this.inputTank.getTankType().toString());
			data.setString("outputType", this.outputTank.getTankType().toString());
			networkPack(data, 50);

			PacketDispatcher.wrapper.sendToAllAround(new LoopedSoundPacket(this.xCoord, this.yCoord, this.zCoord), new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 50));
		}
	}
	
	private void updateConnections() {
		for(DirPos pos : getConPos()) {
			this.trySubscribe(this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			
			if(TileEntityMachineGasCent.fluidConversions.containsValue(this.inputTank.getTankType())) {
				this.trySubscribe(this.tank.getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
		}
	}
	
	private DirPos[] getConPos() {
		return new DirPos[] {
			new DirPos(this.xCoord, this.yCoord - 1, this.zCoord, Library.NEG_Y),
			new DirPos(this.xCoord + 1, this.yCoord, this.zCoord, Library.POS_X),
			new DirPos(this.xCoord - 1, this.yCoord, this.zCoord, Library.NEG_X),
			new DirPos(this.xCoord, this.yCoord, this.zCoord + 1, Library.POS_Z),
			new DirPos(this.xCoord, this.yCoord, this.zCoord - 1, Library.NEG_Z)
		};
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
		return TileEntityMachineGasCent.maxPower;
	}
	
	public int getProcessingSpeed() {
		if(this.slots[6] != null && this.slots[6].getItem() == ModItems.upgrade_gc_speed) {
			return TileEntityMachineGasCent.processingSpeed - 70;
		}
		return TileEntityMachineGasCent.processingSpeed;
	}
	
	public void setTankType(int in) {
		
		if(this.slots[in] != null && this.slots[in].getItem() instanceof IItemFluidIdentifier) {
			IItemFluidIdentifier id = (IItemFluidIdentifier) this.slots[in].getItem();
			FluidType newType = id.getType(null, 0, 0, 0, this.slots[in]);
			
			if(this.tank.getTankType() != newType) {
				PseudoFluidType pseudo = TileEntityMachineGasCent.fluidConversions.get(newType);
				
				if(pseudo != null) {
					this.inputTank.setTankType(pseudo);
					this.outputTank.setTankType(pseudo.getOutputType());
					this.tank.setTankType(newType);
				}
			}
			
		}
	}
	
	@Override
	public void setTypeForSync(FluidType type, int index) {
		this.tank.setTankType(type);
	}
	
	@Override
	public void setFillForSync(int fill, int index) {
		this.tank.setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		if(type == this.tank.getTankType())
			this.tank.setFill(fill);
	}
	
	@Override
	public int getFluidFill(FluidType type) {
		return this.tank.getTankType() == type ? this.tank.getFill() : 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		return this.tank.getTankType() == type ? this.tank.getMaxFill() : 0;
	}
	
	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { this.tank };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { this.tank };
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 5, this.zCoord + 1);
		}
		
		return this.bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	public class PseudoFluidTank {
		PseudoFluidType type;
		int fluid;
		int maxFluid;
		
		public PseudoFluidTank(PseudoFluidType type, int maxFluid) {
			this.type = type;
			this.maxFluid = maxFluid;
		}
		
		public void setFill(int i) {
			this.fluid = i;
		}
		
		public void setTankType(PseudoFluidType type) {
			
			if(this.type.equals(type))
				return;
			
			if(type == null)
				this.type = PseudoFluidType.NONE;
			else
				this.type = type;
			
			setFill(0);
		}
		
		public PseudoFluidType getTankType() {
			return this.type;
		}
		
		public int getFill() {
			return this.fluid;
		}
		
		public int getMaxFill() {
			return this.maxFluid;
		}
		
		//Called by TE to save fillstate
		public void writeToNBT(NBTTagCompound nbt, String s) {
			nbt.setInteger(s, this.fluid);
			nbt.setInteger(s + "_max", this.maxFluid);
			nbt.setString(s + "_type", this.type.toString());
		}
		
		//Called by TE to load fillstate
		public void readFromNBT(NBTTagCompound nbt, String s) {
			this.fluid = nbt.getInteger(s);
			int max = nbt.getInteger(s + "_max");
			if(max > 0)
				this.maxFluid = nbt.getInteger(s + "_max");
			this.type = PseudoFluidType.valueOf(nbt.getString(s + "_type"));
		}
		
		/*        ______      ______
		 *       _I____I_    _I____I_
		 *      /      \\\  /      \\\
		 *     |IF{    || ||     } || |
		 *     | IF{   || ||    }  || |
		 *     |  IF{  || ||   }   || |
		 *     |   IF{ || ||  }    || |
		 *     |    IF{|| || }     || |
		 *     |       || ||       || |
		 *     |     } || ||IF{    || |
		 *     |    }  || || IF{   || |
		 *     |   }   || ||  IF{  || |
		 *     |  }    || ||   IF{ || |
		 *     | }     || ||    IF{|| |
		 *     |IF{    || ||     } || |
		 *     | IF{   || ||    }  || |
		 *     |  IF{  || ||   }   || |
		 *     |   IF{ || ||  }    || |
		 *     |    IF{|| || }     || |
		 *     |       || ||       || |
		 *     |     } || ||IF{	   || |
		 *     |    }  || || IF{   || |
		 *     |   }   || ||  IF{  || |
		 *     |  }    || ||   IF{ || |
		 *     | }     || ||    IF{|| |
		 *     |IF{    || ||     } || |
		 *     | IF{   || ||    }  || |
		 *     |  IF{  || ||   }   || |
		 *     |   IF{ || ||  }    || |
		 *     |    IF{|| || }     || |
		 *     |       || ||       || |
		 *     |     } || ||IF{	   || |
		 *     |    }  || || IF{   || |
		 *     |   }   || ||  IF{  || |
		 *     |  }    || ||   IF{ || |
		 *     | }     || ||    IF{|| |
		 *    _|_______||_||_______||_|_
		 *   |                          |
		 *   |                          |
		 *   |       |==========|       |
		 *   |       |NESTED    |       |
		 *   |       |IF  (:    |       |
		 *   |       |STATEMENTS|       |
		 *   |       |==========|       |
		 *   |                          |
		 *   |                          |
		 *   ----------------------------
		 */
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineGasCent(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineGasCent(player.inventory, this);
	}
}
