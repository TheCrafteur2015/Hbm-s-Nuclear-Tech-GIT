package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.config.VersatileConfig;
import com.hbm.hazard.HazardRegistry;
import com.hbm.hazard.HazardSystem;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.container.ContainerStorageDrum;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIStorageDrum;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemWasteLong;
import com.hbm.items.special.ItemWasteShort;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import api.hbm.fluid.IFluidStandardSender;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityStorageDrum extends TileEntityMachineBase implements IFluidSource, IFluidStandardSender, IGUIProvider {

	public FluidTank[] tanks;
	private static final int[] slots_arr = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23 };
	public List<IFluidAcceptor> list = new ArrayList<>();
	public List<IFluidAcceptor> list2 = new ArrayList<>();
	public int age = 0;

	public TileEntityStorageDrum() {
		super(24);
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.WASTEFLUID, 16000);
		this.tanks[1] = new FluidTank(Fluids.WASTEGAS, 16000);
	}

	@Override
	public String getName() {
		return "container.storageDrum";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			float rad = 0;
			
			int liquid = 0;
			int gas = 0;
			
			for(int i = 0; i < 24; i++) {
				
				if(this.slots[i] != null) {
					
					Item item = this.slots[i].getItem();
					
					if(this.worldObj.getTotalWorldTime() % 20 == 0) {
						rad += HazardSystem.getHazardLevelFromStack(this.slots[i], HazardRegistry.RADIATION);
					}
					
					int meta = this.slots[i].getItemDamage();
					
					if(item == ModItems.nuclear_waste_long && this.worldObj.rand.nextInt(VersatileConfig.getLongDecayChance()) == 0) {
						ItemWasteLong.WasteClass wasteClass = ItemWasteLong.WasteClass.values()[ItemWasteLong.rectify(meta)];
						liquid += wasteClass.liquid;
						gas += wasteClass.gas;
						this.slots[i] = new ItemStack(ModItems.nuclear_waste_long_depleted, 1, meta);
					}
					
					if(item == ModItems.nuclear_waste_long_tiny && this.worldObj.rand.nextInt(VersatileConfig.getLongDecayChance() / 10) == 0) {
						ItemWasteLong.WasteClass wasteClass = ItemWasteLong.WasteClass.values()[ItemWasteLong.rectify(meta)];
						liquid += wasteClass.liquid / 10;
						gas += wasteClass.gas / 10;
						this.slots[i] = new ItemStack(ModItems.nuclear_waste_long_depleted_tiny, 1, meta);
					}
					
					if(item == ModItems.nuclear_waste_short && this.worldObj.rand.nextInt(VersatileConfig.getShortDecayChance()) == 0) {
						ItemWasteShort.WasteClass wasteClass = ItemWasteShort.WasteClass.values()[ItemWasteLong.rectify(meta)];
						liquid += wasteClass.liquid;
						gas += wasteClass.gas;
						this.slots[i] = new ItemStack(ModItems.nuclear_waste_short_depleted, 1, meta);
					}
					
					if(item == ModItems.nuclear_waste_short_tiny && this.worldObj.rand.nextInt(VersatileConfig.getShortDecayChance() / 10) == 0) {
						ItemWasteShort.WasteClass wasteClass = ItemWasteShort.WasteClass.values()[ItemWasteLong.rectify(meta)];
						liquid += wasteClass.liquid / 10;
						gas += wasteClass.gas / 10;
						this.slots[i] = new ItemStack(ModItems.nuclear_waste_short_depleted_tiny, 1, meta);
					}
					
					if(item == ModItems.ingot_au198 && this.worldObj.rand.nextInt(VersatileConfig.getShortDecayChance() / 20) == 0) {
						this.slots[i] = new ItemStack(ModItems.ingot_mercury, 1, meta);
					}
					if(item == ModItems.nugget_au198 && this.worldObj.rand.nextInt(VersatileConfig.getShortDecayChance() / 100) == 0) {
						this.slots[i] = new ItemStack(ModItems.nugget_mercury, 1, meta);
					}
					
					if(item == ModItems.ingot_pb209 && this.worldObj.rand.nextInt(VersatileConfig.getShortDecayChance() / 10) == 0) {
						this.slots[i] = new ItemStack(ModItems.ingot_bismuth, 1, meta);
					}
					if(item == ModItems.nugget_pb209 && this.worldObj.rand.nextInt(VersatileConfig.getShortDecayChance() / 50) == 0) {
						this.slots[i] = new ItemStack(ModItems.nugget_bismuth, 1, meta);
					}

					if(item == ModItems.powder_sr90 && this.worldObj.rand.nextInt(VersatileConfig.getShortDecayChance() / 10) == 0) {
						this.slots[i] = new ItemStack(ModItems.powder_zirconium, 1, meta);
					}
					if(item == ModItems.nugget_sr90 && this.worldObj.rand.nextInt(VersatileConfig.getShortDecayChance() / 50) == 0) {
						this.slots[i] = new ItemStack(ModItems.nugget_zirconium, 1, meta);
					}
				}
			}

			this.tanks[0].setFill(this.tanks[0].getFill() + liquid);
			this.tanks[1].setFill(this.tanks[1].getFill() + gas);
			
			for(int i = 0; i < 2; i++) {
				
				int overflow = Math.max(this.tanks[i].getFill() - this.tanks[i].getMaxFill(), 0);
				
				if(overflow > 0) {
					this.tanks[i].setFill(this.tanks[i].getFill() - overflow);
					this.tanks[i].getTankType().onFluidRelease(this, this.tanks[i], overflow);
				}
			}
			
			this.age++;
			
			if(this.age >= 20)
				this.age -= 20;
			
			if(this.age == 9 || this.age == 19) {
				fillFluidInit(this.tanks[0].getTankType());
			}
			if(this.age == 8 || this.age == 18) {
				fillFluidInit(this.tanks[1].getTankType());
			}

			this.sendFluidToAll(this.tanks[0], this);
			this.sendFluidToAll(this.tanks[1], this);

			this.tanks[0].updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
			this.tanks[1].updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
			
			if(rad > 0) {
				radiate(this.worldObj, this.xCoord, this.yCoord, this.zCoord, rad);
			}
		}
	}
	
	
	private void radiate(World world, int x, int y, int z, float rads) {
		
		double range = 32D;
		
		List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(x + 0.5, y + 0.5, z + 0.5, x + 0.5, y + 0.5, z + 0.5).expand(range, range, range));
		
		for(EntityLivingBase e : entities) {
			
			Vec3 vec = Vec3.createVectorHelper(e.posX - (x + 0.5), (e.posY + e.getEyeHeight()) - (y + 0.5), e.posZ - (z + 0.5));
			double len = vec.lengthVector();
			vec = vec.normalize();
			
			float res = 0;
			
			for(int i = 1; i < len; i++) {

				int ix = (int)Math.floor(x + 0.5 + vec.xCoord * i);
				int iy = (int)Math.floor(y + 0.5 + vec.yCoord * i);
				int iz = (int)Math.floor(z + 0.5 + vec.zCoord * i);
				
				res += world.getBlock(ix, iy, iz).getExplosionResistance(null);
			}
			
			if(res < 1)
				res = 1;
			
			float eRads = rads;
			eRads /= res;
			eRads /= (float)(len * len);
			
			ContaminationUtil.contaminate(e, HazardType.RADIATION, ContaminationType.CREATIVE, eRads);
		}
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		
		Item item = itemStack.getItem();
		
		if(item == ModItems.nuclear_waste_long || 
				item == ModItems.nuclear_waste_long_tiny || 
				item == ModItems.nuclear_waste_short || 
				item == ModItems.nuclear_waste_short_tiny || 
				item == ModItems.ingot_au198)
			return true;
		
		return false;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {

		Item item = itemStack.getItem();
		
		if(item == ModItems.nuclear_waste_long_depleted || 
				item == ModItems.nuclear_waste_long_depleted_tiny || 
				item == ModItems.nuclear_waste_short_depleted || 
				item == ModItems.nuclear_waste_short_depleted_tiny || 
				item == ModItems.ingot_mercury)
			return true;
		
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return TileEntityStorageDrum.slots_arr;
	}

	@Override
	public boolean getTact() {
		return this.age < 10;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord - 1, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord + 1, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord - 1, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord + 1, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, this.worldObj, type);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type == this.tanks[0].getTankType())
			return this.tanks[0].getFill();
		else if(type == this.tanks[1].getTankType())
			return this.tanks[1].getFill();

		return 0;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type == this.tanks[0].getTankType())
			this.tanks[0].setFill(i);
		else if(type == this.tanks[1].getTankType())
			this.tanks[1].setFill(i);
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		if(type == this.tanks[0].getTankType())
			return this.list;
		if(type == this.tanks[1].getTankType())
			return this.list2;
		
		return new ArrayList<>();
	}

	@Override
	public void clearFluidList(FluidType type) {
		if(type == this.tanks[0].getTankType())
			this.list.clear();
		if(type == this.tanks[1].getTankType())
			this.list2.clear();
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if(index < 2 && this.tanks[index] != null)
			this.tanks[index].setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if(index < 2 && this.tanks[index] != null)
			this.tanks[index].setTankType(type);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.tanks[0].readFromNBT(nbt, "liquid");
		this.tanks[1].readFromNBT(nbt, "gas");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.tanks[0].writeToNBT(nbt, "liquid");
		this.tanks[1].writeToNBT(nbt, "gas");
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { this.tanks[0], this.tanks[1] };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return this.tanks;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerStorageDrum(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIStorageDrum(player.inventory, this);
	}
}
