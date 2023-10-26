package com.hbm.tileentity.machine.rbmk;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.blocks.machine.rbmk.RBMKRod;
import com.hbm.entity.projectile.EntityRBMKDebris.DebrisType;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.inventory.container.ContainerRBMKRod;
import com.hbm.inventory.gui.GUIRBMKRod;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemRBMKRod;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;
import com.hbm.util.Compat;
import com.hbm.util.ParticleUtil;

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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityRBMKRod extends TileEntityRBMKSlottedBase implements IRBMKFluxReceiver, IRBMKLoadable, SimpleComponent {
	
	//amount of "neutron energy" buffered for the next tick to use for the reaction
	public double fluxFast;
	public double fluxSlow;
	public boolean hasRod;

	public TileEntityRBMKRod() {
		super(1);
	}

	@Override
	public String getName() {
		return "container.rbmkRod";
	}
	
	@Override
	public boolean isModerated() {
		return ((RBMKRod)getBlockType()).moderated;
	}

	@SuppressWarnings("incomplete-switch") //shut the fuck up
	@Override
	public void receiveFlux(NType type, double flux) {
		
		switch(type) {
		case FAST: this.fluxFast += flux; break;
		case SLOW: this.fluxSlow += flux; break;
		}
	}
	
	@Override
	public void updateEntity() {

		if(!this.worldObj.isRemote) {
			
			if(this.slots[0] != null && this.slots[0].getItem() instanceof ItemRBMKRod) {
				
				ItemRBMKRod rod = ((ItemRBMKRod)this.slots[0].getItem());
				
				double fluxIn = fluxFromType(rod.nType);
				double fluxOut = rod.burn(this.worldObj, this.slots[0], fluxIn);
				NType rType = rod.rType;
				
				rod.updateHeat(this.worldObj, this.slots[0], 1.0D);
				this.heat += rod.provideHeat(this.worldObj, this.slots[0], this.heat, 1.0D);
				
				if(!hasLid()) {
					ChunkRadiationManager.proxy.incrementRad(this.worldObj, this.xCoord, this.yCoord, this.zCoord, (float) ((this.fluxFast + this.fluxSlow) * 0.05F));
				}
				
				super.updateEntity();
				
				if(this.heat > maxHeat()) {
					
					if(RBMKDials.getMeltdownsDisabled(this.worldObj)) {
						ParticleUtil.spawnGasFlame(this.worldObj, this.xCoord + 0.5, this.yCoord + RBMKDials.getColumnHeight(this.worldObj) + 0.5, this.zCoord + 0.5, 0, 0.2, 0);
					} else {
						meltdown();
					}
					this.fluxFast = 0;
					this.fluxSlow = 0;
					return;
				}
				
				if(this.heat > 10_000) this.heat = 10_000;
				
				//for spreading, we want the buffered flux to be 0 because we want to know exactly how much gets reflected back
				this.fluxFast = 0;
				this.fluxSlow = 0;

				this.worldObj.theProfiler.startSection("rbmkRod_flux_spread");
				spreadFlux(rType, fluxOut);
				this.worldObj.theProfiler.endSection();
				
				this.hasRod = true;
				
			} else {

				this.fluxFast = 0;
				this.fluxSlow = 0;
				
				this.hasRod = false;
				
				super.updateEntity();
			}
		}
	}
	
	/**
	 * SLOW: full efficiency for slow neutrons, fast neutrons have half efficiency
	 * FAST: fast neutrons have 100% efficiency, slow only 30%
	 * ANY: just add together whatever we have because who cares
	 * @param type
	 * @return
	 */
	
	private double fluxFromType(NType type) {
		
		switch(type) {
		case SLOW: return this.fluxFast * 0.5D + this.fluxSlow;
		case FAST: return this.fluxFast + this.fluxSlow * 0.3D;
		case ANY: return this.fluxFast + this.fluxSlow;
		}
		
		return 0.0D;
	}
	
	public static final ForgeDirection[] fluxDirs = new ForgeDirection[] {
			ForgeDirection.NORTH,
			ForgeDirection.EAST,
			ForgeDirection.SOUTH,
			ForgeDirection.WEST
	};
	
	protected static NType stream;
	
	protected void spreadFlux(NType type, double fluxOut) {
		
		int range = RBMKDials.getFluxRange(this.worldObj);
		
		for(ForgeDirection dir : TileEntityRBMKRod.fluxDirs) {
			
			TileEntityRBMKRod.stream = type;
			double flux = fluxOut;
			
			for(int i = 1; i <= range; i++) {
				
				flux = runInteraction(this.xCoord + dir.offsetX * i, this.yCoord, this.zCoord + dir.offsetZ * i, flux);
				
				if(flux <= 0)
					break;
			}
		}
	}
	
	protected double runInteraction(int x, int y, int z, double flux) {
		
		TileEntity te = Compat.getTileStandard(this.worldObj, x, y, z);
		
		if(te instanceof TileEntityRBMKBase) {
			TileEntityRBMKBase base = (TileEntityRBMKBase) te;
			
			if(!base.hasLid())
				ChunkRadiationManager.proxy.incrementRad(this.worldObj, this.xCoord, this.yCoord, this.zCoord, (float) (flux * 0.05F));
			
			if(base.isModerated()) {
				TileEntityRBMKRod.stream = NType.SLOW;
			}
		}

		//burn baby burn
		if(te instanceof TileEntityRBMKRod) {
			TileEntityRBMKRod rod = (TileEntityRBMKRod)te;
			
			if(rod.getStackInSlot(0) != null && rod.getStackInSlot(0).getItem() instanceof ItemRBMKRod) {
				rod.receiveFlux(TileEntityRBMKRod.stream, flux);
				return 0;
			} else {
				return flux;
			}
		}
		
		if(te instanceof TileEntityRBMKOutgasser) {
			TileEntityRBMKOutgasser rod = (TileEntityRBMKOutgasser)te;
			
			if(!rod.canProcess()) {
				return flux;
			}
		}
		
		if(te instanceof IRBMKFluxReceiver) {
			IRBMKFluxReceiver rod = (IRBMKFluxReceiver)te;
			rod.receiveFlux(TileEntityRBMKRod.stream, flux);
			return 0;
		}
		
		//multiply neutron count with rod setting
		if(te instanceof TileEntityRBMKControl) {
			TileEntityRBMKControl control = (TileEntityRBMKControl)te;
			
			if(control.getMult() == 0.0D)
				return 0;
			
			flux *= control.getMult();
			
			return flux;
		}
		
		//set neutrons to slow
		if(te instanceof TileEntityRBMKModerator) {
			TileEntityRBMKRod.stream = NType.SLOW;
			return flux;
		}
		
		//return the neutrons back to this with no further action required
		if(te instanceof TileEntityRBMKReflector) {
			receiveFlux(isModerated() ? NType.SLOW : TileEntityRBMKRod.stream, flux);
			return 0;
		}
		
		//break the neutron flow and nothign else
		if(te instanceof TileEntityRBMKAbsorber) {
			return 0;
		}
		
		if(te instanceof TileEntityRBMKBase) {
			return flux;
		}
		
		int limit = RBMKDials.getColumnHeight(this.worldObj);
		int hits = 0;
		for(int h = 0; h <= limit; h++) {
			
			if(!this.worldObj.getBlock(x, y + h, z).isOpaqueCube())
				hits++;
		}
		
		if(hits > 0)
			ChunkRadiationManager.proxy.incrementRad(this.worldObj, this.xCoord, this.yCoord, this.zCoord, (float) (flux * 0.05F * hits / (float)limit));
		
		return 0;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.fluxFast = nbt.getDouble("fluxFast");
		this.fluxSlow = nbt.getDouble("fluxSlow");
		this.hasRod = nbt.getBoolean("hasRod");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setDouble("fluxFast", this.fluxFast);
		nbt.setDouble("fluxSlow", this.fluxSlow);
		nbt.setBoolean("hasRod", this.hasRod);
	}
	
	@Override
	public void getDiagData(NBTTagCompound nbt) {
		writeToNBT(nbt);
		
		if(this.slots[0] != null && this.slots[0].getItem() instanceof ItemRBMKRod) {
			
			ItemRBMKRod rod = ((ItemRBMKRod)this.slots[0].getItem());

			nbt.setString("f_yield", ItemRBMKRod.getYield(this.slots[0]) + " / " + rod.yield + " (" + (ItemRBMKRod.getEnrichment(this.slots[0]) * 100) + "%)");
			nbt.setString("f_xenon", ItemRBMKRod.getPoison(this.slots[0]) + "%");
			nbt.setString("f_heat", ItemRBMKRod.getCoreHeat(this.slots[0]) + " / " + ItemRBMKRod.getHullHeat(this.slots[0])  + " / " + rod.meltingPoint);
		}
	}
	
	@Override
	public void onMelt(int reduce) {

		boolean moderated = isModerated();
		int h = RBMKDials.getColumnHeight(this.worldObj);
		reduce = MathHelper.clamp_int(reduce, 1, h);
		
		if(this.worldObj.rand.nextInt(3) == 0)
			reduce++;
		
		boolean corium = this.slots[0] != null && this.slots[0].getItem() instanceof ItemRBMKRod;
		
		if(corium && this.slots[0].getItem() == ModItems.rbmk_fuel_drx) 
			RBMKBase.digamma = true;
		
		this.slots[0] = null;

		if(corium) {
			
			for(int i = h; i >= 0; i--) {
				this.worldObj.setBlock(this.xCoord, this.yCoord + i, this.zCoord, ModBlocks.corium_block, 5, 3);
				this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord + i, this.zCoord);
			}
			
			int count = 1 + this.worldObj.rand.nextInt(RBMKDials.getColumnHeight(this.worldObj));
			
			for(int i = 0; i < count; i++) {
				spawnDebris(DebrisType.FUEL);
			}
		} else {
			standardMelt(reduce);
		}
		
		if(moderated) {
			
			int count = 2 + this.worldObj.rand.nextInt(2);
			
			for(int i = 0; i < count; i++) {
				spawnDebris(DebrisType.GRAPHITE);
			}
		}
		
		spawnDebris(DebrisType.ELEMENT);
		
		if(getBlockMetadata() == RBMKBase.DIR_NORMAL_LID.ordinal() + BlockDummyable.offset)
			spawnDebris(DebrisType.LID);
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.FUEL;
	}

	@Override
	public NBTTagCompound getNBTForConsole() {
		NBTTagCompound data = new NBTTagCompound();
		
		if(this.slots[0] != null && this.slots[0].getItem() instanceof ItemRBMKRod) {
			
			ItemRBMKRod rod = ((ItemRBMKRod)this.slots[0].getItem());
			data.setDouble("enrichment", ItemRBMKRod.getEnrichment(this.slots[0]));
			data.setDouble("xenon", ItemRBMKRod.getPoison(this.slots[0]));
			data.setDouble("c_heat", ItemRBMKRod.getHullHeat(this.slots[0]));
			data.setDouble("c_coreHeat", ItemRBMKRod.getCoreHeat(this.slots[0]));
			data.setDouble("c_maxHeat", rod.meltingPoint);
		}
		
		return data;
	}

	@Override
	public boolean canLoad(ItemStack toLoad) {
		return toLoad != null && this.slots[0] == null;
	}

	@Override
	public void load(ItemStack toLoad) {
		this.slots[0] = toLoad.copy();
		markDirty();
	}

	@Override
	public boolean canUnload() {
		return this.slots[0] != null;
	}

	@Override
	public ItemStack provideNext() {
		return this.slots[0];
	}

	@Override
	public void unload() {
		this.slots[0] = null;
		markDirty();
	}
	
	// do some opencomputer stuff
	@Override
	public String getComponentName() {
		return "rbmk_fuel_rod";
	}

	@Callback(direct = true, limit = 16)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getHeat(Context context, Arguments args) {
		return new Object[] {this.heat};
	}

	@Callback(direct = true, limit = 16)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getFluxSlow(Context context, Arguments args) {
		return new Object[] {this.fluxSlow};
	}

	@Callback(direct = true, limit = 16)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getFluxFast(Context context, Arguments args) {
		return new Object[] {this.fluxFast};
	}
	
	@Callback(direct = true, limit = 16)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getDepletion(Context context, Arguments args) {
		if(this.slots[0] != null && this.slots[0].getItem() instanceof ItemRBMKRod) {
			return new Object[] {ItemRBMKRod.getEnrichment(this.slots[0])};
		}
		return new Object[] {"N/A"};
	}

	@Callback(direct = true, limit = 16)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getXenonPoison(Context context, Arguments args) {
		if(this.slots[0] != null && this.slots[0].getItem() instanceof ItemRBMKRod) {
			return new Object[] {ItemRBMKRod.getPoison(this.slots[0])};
		}
		return new Object[] {"N/A"};
	}

	@Callback(direct = true, limit = 16)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCoreHeat(Context context, Arguments args) {
		if(this.slots[0] != null && this.slots[0].getItem() instanceof ItemRBMKRod) {
			return new Object[] {ItemRBMKRod.getCoreHeat(this.slots[0])};
		}
		return new Object[] {"N/A"};
	}

	@Callback(direct = true, limit = 16)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getSkinHeat(Context context, Arguments args) {
		if(this.slots[0] != null && this.slots[0].getItem() instanceof ItemRBMKRod) {
			return new Object[] {ItemRBMKRod.getHullHeat(this.slots[0])};
		}
		return new Object[] {"N/A"};
	}

	@Callback(direct = true, limit = 16)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		Object OC_enrich_buf;
		Object OC_poison_buf;
		Object OC_hull_buf;
		Object OC_core_buf;
		if(this.slots[0] != null && this.slots[0].getItem() instanceof ItemRBMKRod) {
			OC_enrich_buf = ItemRBMKRod.getEnrichment(this.slots[0]);
			OC_poison_buf = ItemRBMKRod.getPoison(this.slots[0]);
			OC_hull_buf = ItemRBMKRod.getHullHeat(this.slots[0]);
			OC_core_buf = ItemRBMKRod.getCoreHeat(this.slots[0]);
		} else {
			OC_enrich_buf = "N/A";
			OC_poison_buf = "N/A";
			OC_hull_buf = "N/A";
			OC_core_buf = "N/A";
		}
		return new Object[] {this.heat, OC_hull_buf, OC_core_buf, this.fluxSlow, this.fluxFast, OC_enrich_buf, OC_poison_buf, ((RBMKRod)getBlockType()).moderated, this.xCoord, this.yCoord, this.zCoord};
	}

	@Callback(direct = true, limit = 16)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getModerated(Context context, Arguments args) {
		return new Object[] {((RBMKRod)getBlockType()).moderated};
	}

	@Callback(direct = true, limit = 16)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCoordinates(Context context, Arguments args) {
		return new Object[] {this.xCoord, this.yCoord, this.zCoord};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerRBMKRod(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIRBMKRod(player.inventory, this);
	}
}
