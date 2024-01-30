package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.ReactorResearch;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerReactorControl;
import com.hbm.inventory.gui.GUIReactorControl;
import com.hbm.items.ModItems;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityReactorControl extends TileEntityMachineBase implements IControlReceiver, IGUIProvider, SimpleComponent {

	public TileEntityReactorControl() {
		super(1);
	}
	
	@Override
	public String getName() {
		return "container.reactorControl";
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);

		this.isLinked = nbt.getBoolean("isLinked");
		this.levelLower = nbt.getDouble("levelLower");
		this.levelUpper = nbt.getDouble("levelUpper");
		this.heatLower = nbt.getDouble("heatLower");
		this.heatUpper = nbt.getDouble("heatUpper");
		this.function = RodFunction.values()[nbt.getInteger("function")];
		
		this.slots = new ItemStack[getSizeInventory()];
		
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if(b0 >= 0 && b0 < this.slots.length)
			{
				this.slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagList list = new NBTTagList();
		
		nbt.setBoolean("isLinked", this.isLinked);
		nbt.setDouble("levelLower", this.levelLower);
		nbt.setDouble("levelUpper", this.levelUpper);
		nbt.setDouble("heatLower", this.heatLower);
		nbt.setDouble("heatUpper", this.heatUpper);
		nbt.setInteger("function", this.function.ordinal());

		
		for(int i = 0; i < this.slots.length; i++)
		{
			if(this.slots[i] != null)
			{
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte)i);
				this.slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}
	
	public TileEntityReactorResearch reactor;
	
	public boolean isLinked;
	
	public int flux;
	public double level;
	public int heat;
	
	public double levelLower;
	public double levelUpper;
	public double heatLower;
	public double heatUpper;
	public RodFunction function = RodFunction.LINEAR;
	
	@SuppressWarnings("deprecation")
	@Override
	public void updateEntity() {

		if(!this.worldObj.isRemote) {

			this.isLinked = establishLink();
			
			if(this.isLinked) { 
				
				double fauxLevel = 0;

				double lowerBound = Math.min(this.heatLower, this.heatUpper);
				double upperBound = Math.max(this.heatLower, this.heatUpper);
				
				if(this.heat < lowerBound) {
					fauxLevel = this.levelLower;
					
				} else if(this.heat > upperBound) {
					fauxLevel = this.levelUpper;
					
				} else {
					fauxLevel = getTargetLevel(this.function, this.heat);
				}
				
				double level = MathHelper.clamp_double((fauxLevel * 0.01D), 0D, 1D);
				
				if(level != this.level) {
					this.reactor.setTarget(level);
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("heat", this.heat);
			data.setDouble("level", this.level);
			data.setInteger("flux", this.flux);
			data.setBoolean("isLinked", this.isLinked);
			data.setDouble("levelLower", this.levelLower);
			data.setDouble("levelUpper", this.levelUpper);
			data.setDouble("heatLower", this.heatLower);
			data.setDouble("heatUpper", this.heatUpper);
			data.setInteger("function", this.function.ordinal());
			networkPack(data, 150);
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.heat = data.getInteger("heat");
		this.level = data.getDouble("level");
		this.flux = data.getInteger("flux");
		this.isLinked = data.getBoolean("isLinked");
		this.levelLower = data.getDouble("levelLower");
		this.levelUpper = data.getDouble("levelUpper");
		this.heatLower = data.getDouble("heatLower");
		this.heatUpper = data.getDouble("heatUpper");
		this.function = RodFunction.values()[data.getInteger("function")];
	}
	
	private boolean establishLink() {
		if(this.slots[0] != null && this.slots[0].getItem() == ModItems.reactor_sensor && this.slots[0].stackTagCompound != null) {
			int xCoord = this.slots[0].stackTagCompound.getInteger("x");
    		int yCoord = this.slots[0].stackTagCompound.getInteger("y");
    		int zCoord = this.slots[0].stackTagCompound.getInteger("z");
    		
    		Block b = this.worldObj.getBlock(xCoord, yCoord, zCoord);
    		
    		if(b == ModBlocks.reactor_research) {
    			
    			int[] pos = ((ReactorResearch) ModBlocks.reactor_research).findCore(this.worldObj, xCoord, yCoord, zCoord);
    			
    			if(pos != null) {

					TileEntity tile = this.worldObj.getTileEntity(pos[0], pos[1], pos[2]);

					if(tile instanceof TileEntityReactorResearch) {
						this.reactor = (TileEntityReactorResearch) tile;
						
						this.flux = this.reactor.totalFlux;
						this.level = this.reactor.level;
						this.heat = this.reactor.heat;
						
						return true;
					}
				}
    		}
		}
		
		return false;
	}
	
	public double getTargetLevel(RodFunction function, int heat) {
		double fauxLevel = 0;
		
		switch(function) {
		case LINEAR:
			fauxLevel = (heat - this.heatLower) * ((this.levelUpper - this.levelLower) / (this.heatUpper - this.heatLower)) + this.levelLower;
			return fauxLevel;
		case LOG:
			fauxLevel = Math.pow((heat - this.heatUpper) / (this.heatLower - this.heatUpper), 2) * (this.levelLower - this.levelUpper) + this.levelUpper;
			return fauxLevel;
		case QUAD:
			fauxLevel = Math.pow((heat - this.heatLower) / (this.heatUpper - this.heatLower), 2) * (this.levelUpper - this.levelLower) + this.levelLower;
			return fauxLevel;
		default:
			return 0.0D;
		}
	}
	
	public int[] getDisplayData() {
		if(this.isLinked) {
			int[] data = new int[3];
			data[0] = (int) (this.level * 100);
			data[1] = this.flux;
			data[2] = (int) Math.round((this.heat) * 0.00002 * 980 + 20);
			return data;
		} else {
			return new int[] { 0, 0, 0 };
		}
	}
	
	@Override
	public void receiveControl(NBTTagCompound data) {
		
		if(data.hasKey("function")) {
			this.function = RodFunction.values()[data.getInteger("function")];
		} else {
			this.levelLower = data.getDouble("levelLower");
			this.levelUpper = data.getDouble("levelUpper");
			this.heatLower = data.getDouble("heatLower");
			this.heatUpper = data.getDouble("heatUpper");
		}
		
		markDirty();
	}
	
	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(this.xCoord - player.posX, this.yCoord - player.posY, this.zCoord - player.posZ).lengthVector() < 20;
	}
	
	public enum RodFunction {
		LINEAR,
		QUAD,
		LOG
	}

	// do some opencomputer stuff
	@Override
	public String getComponentName() {
		return "reactor_control";
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] isLinked(Context context, Arguments args) {
		return new Object[] {this.isLinked};
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getReactor(Context context, Arguments args) {
		return new Object[] {getDisplayData()};
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] setParams(Context context, Arguments args) { //i hate my life
		int newFunction = args.checkInteger(0);
		double newMaxheat = args.checkDouble(1);
		double newMinheat = args.checkDouble(2);
		double newMaxlevel = args.checkDouble(3)/100.0;
		double newMinlevel = args.checkDouble(4)/100.0;
		if (newFunction > 2) {    //no more out of bounds for you (and yes there's integer values for functions, sue me)
			newFunction = 0;
		} else if (newFunction < 0) {
			newFunction = 0;
		}
		if (newMaxheat < 0.0) {
			newMaxheat = 0.0;
		}
		if (newMinheat < 0.0) {
			newMinheat = 0.0;
		}
		if (newMaxlevel < 0.0) {
			newMaxlevel = 0.0;
		} else if (newMaxlevel > 1.0) {
			newMaxlevel = 1.0;
		}
		if (newMinlevel < 0.0) {
			newMinlevel = 0.0;
		} else if (newMinlevel > 1.0) {
			newMinlevel = 1.0;
		}
		this.function = RodFunction.values()[newFunction];
		this.heatUpper = newMaxheat;
		this.heatLower = newMinheat;
		this.levelUpper = newMaxlevel;
		this.levelLower = newMinlevel;
		return new Object[] {};
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getParams(Context context, Arguments args) {
		return new Object[] {this.function.ordinal(), this.heatUpper, this.heatLower, this.levelUpper, this.levelLower};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerReactorControl(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIReactorControl(player.inventory, this);
	}
}
