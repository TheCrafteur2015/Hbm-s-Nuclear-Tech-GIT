package com.hbm.tileentity.machine;

import com.hbm.inventory.container.ContainerCoreStabilizer;
import com.hbm.inventory.gui.GUICoreStabilizer;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemLens;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IEnergyUser;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityCoreStabilizer extends TileEntityMachineBase implements IEnergyUser, SimpleComponent, IGUIProvider {

	public long power;
	public static final long maxPower = 2500000000L;
	public int watts;
	public int beam;
	
	public static final int range = 15;

	public TileEntityCoreStabilizer() {
		super(1);
	}

	@Override
	public String getName() {
		return "container.dfcStabilizer";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			updateConnections();
			
			this.watts = MathHelper.clamp_int(this.watts, 1, 100);
			int demand = (int) Math.pow(this.watts, 4);

			this.beam = 0;
			
			if(this.power >= demand && this.slots[0] != null && this.slots[0].getItem() == ModItems.ams_lens && ItemLens.getLensDamage(this.slots[0]) < ((ItemLens)ModItems.ams_lens).maxDamage) {
				
				ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata());
				for(int i = 1; i <= TileEntityCoreStabilizer.range; i++) {
	
					int x = this.xCoord + dir.offsetX * i;
					int y = this.yCoord + dir.offsetY * i;
					int z = this.zCoord + dir.offsetZ * i;
					
					TileEntity te = this.worldObj.getTileEntity(x, y, z);
	
					if(te instanceof TileEntityCore) {
						
						TileEntityCore core = (TileEntityCore)te;
						core.field = Math.max(core.field, this.watts);
						this.power -= demand;
						this.beam = i;
						
						long dmg = ItemLens.getLensDamage(this.slots[0]);
						dmg += this.watts;
						
						if(dmg >= ((ItemLens)ModItems.ams_lens).maxDamage)
							this.slots[0] = null;
						else
							ItemLens.setLensDamage(this.slots[0], dmg);
						
						break;
					}
					
					if(!this.worldObj.getBlock(x, y, z).isAir(this.worldObj, x, y, z))
						break;
				}
			}

			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setInteger("watts", this.watts);
			data.setInteger("beam", this.beam);
			networkPack(data, 250);
		}
	}
	
	private void updateConnections() {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			trySubscribe(this.worldObj, this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ, dir);
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {

		this.power = data.getLong("power");
		this.watts = data.getInteger("watts");
		this.beam = data.getInteger("beam");
	}
	
	public long getPowerScaled(long i) {
		return (this.power * i) / TileEntityCoreStabilizer.maxPower;
	}
	
	public int getWattsScaled(int i) {
		return (this.watts * i) / 100;
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
		return TileEntityCoreStabilizer.maxPower;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN;
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
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.power = nbt.getLong("power");
		this.watts = nbt.getInteger("watts");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", this.power);
		nbt.setInteger("watts", this.watts);
	}

	// do some opencomputer stuff
	@Override
	public String getComponentName() {
		return "dfc_stabilizer";
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getEnergyInfo(Context context, Arguments args) {
		return new Object[] {getPower(), getMaxPower()};
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInput(Context context, Arguments args) {
		return new Object[] {this.watts};
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getDurability(Context context, Arguments args) {
		if(this.slots[0] != null && this.slots[0].getItem() == ModItems.ams_lens && ItemLens.getLensDamage(this.slots[0]) < ((ItemLens)ModItems.ams_lens).maxDamage) {
			return new Object[] {ItemLens.getLensDamage(this.slots[0])};
		}
		return new Object[] {"N/A"};
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		Object lens_damage_buf;
		if(this.slots[0] != null && this.slots[0].getItem() == ModItems.ams_lens && ItemLens.getLensDamage(this.slots[0]) < ((ItemLens)ModItems.ams_lens).maxDamage) {
			lens_damage_buf = ItemLens.getLensDamage(this.slots[0]);
		} else {
			lens_damage_buf = "N/A";
		}
		return new Object[] {this.power, TileEntityCoreStabilizer.maxPower, this.watts, lens_damage_buf};
	}

	@Callback(direct = true, limit = 2)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setInput(Context context, Arguments args) {
		int newOutput = args.checkInteger(0);
		if (newOutput > 100) {
			newOutput = 100;
		} else if (newOutput < 0) {
			newOutput = 0;
		}
		this.watts = newOutput;
		return new Object[] {};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCoreStabilizer(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICoreStabilizer(player.inventory, this);
	}
}
