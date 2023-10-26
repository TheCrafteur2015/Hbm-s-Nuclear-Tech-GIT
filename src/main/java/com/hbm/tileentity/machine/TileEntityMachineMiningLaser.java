package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerMiningLaser;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMiningLaser;
import com.hbm.inventory.recipes.CentrifugeRecipes;
import com.hbm.inventory.recipes.CrystallizerRecipes;
import com.hbm.inventory.recipes.CrystallizerRecipes.CrystallizerRecipe;
import com.hbm.inventory.recipes.ShredderRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.InventoryUtil;

import api.hbm.block.IDrillInteraction;
import api.hbm.block.IMiningDrill;
import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardSender;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineMiningLaser extends TileEntityMachineBase implements IEnergyUser, IFluidSource, IMiningDrill, IFluidStandardSender, IGUIProvider {
	
	public long power;
	public int age = 0;
	public static final long maxPower = 100000000;
	public static final int consumption = 10000;
	public FluidTank tank;
	public List<IFluidAcceptor> list = new ArrayList<>();

	public boolean isOn;
	public int targetX;
	public int targetY;
	public int targetZ;
	public int lastTargetX;
	public int lastTargetY;
	public int lastTargetZ;
	public boolean beam;
	boolean lock = false;
	double breakProgress;

	public TileEntityMachineMiningLaser() {
		
		//slot 0: battery
		//slots 1 - 8: upgrades
		//slots 9 - 29: output
		super(30);
		this.tank = new FluidTank(Fluids.OIL, 64000, 0);
	}

	@Override
	public String getName() {
		return "container.miningLaser";
	}

	@Override
	public void updateEntity() {
		
		if(!this.worldObj.isRemote) {
			
			updateConnections();

			this.age++;
			if (this.age >= 20) {
				this.age = 0;
			}

			if (this.age == 9 || this.age == 19)
				fillFluidInit(this.tank.getTankType());

			this.sendFluid(this.tank, this.worldObj, this.xCoord + 2, this.yCoord, this.zCoord, Library.POS_X);
			this.sendFluid(this.tank, this.worldObj, this.xCoord - 2, this.yCoord, this.zCoord, Library.NEG_X);
			this.sendFluid(this.tank, this.worldObj, this.xCoord, this.yCoord + 2, this.zCoord, Library.POS_Z);
			this.sendFluid(this.tank, this.worldObj, this.xCoord, this.yCoord - 2, this.zCoord, Library.NEG_Z);
			
			this.power = Library.chargeTEFromItems(this.slots, 0, this.power, TileEntityMachineMiningLaser.maxPower);
			this.tank.updateTank(this.xCoord, this.yCoord, this.zCoord, this.worldObj.provider.dimensionId);
			
			//reset progress if the position changes
			if(this.lastTargetX != this.targetX ||
					this.lastTargetY != this.targetY ||
					this.lastTargetZ != this.targetZ)
				this.breakProgress = 0;
			
			//set last positions for interpolation and the like
			this.lastTargetX = this.targetX;
			this.lastTargetY = this.targetY;
			this.lastTargetZ = this.targetZ;
			
			double clientBreakProgress = 0;
			
			if(this.isOn) {
				
				UpgradeManager.eval(this.slots, 1, 8);
				int cycles = 1 + UpgradeManager.getLevel(UpgradeType.OVERDRIVE);
				int speed = 1 + Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 12);
				int range = 1 + Math.min(UpgradeManager.getLevel(UpgradeType.EFFECT) * 2, 24);
				int fortune = Math.min(UpgradeManager.getLevel(UpgradeType.FORTUNE), 3);
				int consumption = TileEntityMachineMiningLaser.consumption
						- (TileEntityMachineMiningLaser.consumption * Math.min(UpgradeManager.getLevel(UpgradeType.POWER), 12) / 16)
						+ (TileEntityMachineMiningLaser.consumption * Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 12) / 16);
				
				for(int i = 0; i < cycles; i++) {
					
					if(this.power < consumption) {
						this.beam = false;
						break;
					}
					
					this.power -= consumption;
					
					if(this.targetY <= 0)
						this.targetY = this.yCoord - 2;
					
					scan(range);
					
					
					Block block = this.worldObj.getBlock(this.targetX, this.targetY, this.targetZ);
					
					if(block.getMaterial().isLiquid()) {
						this.worldObj.setBlockToAir(this.targetX, this.targetY, this.targetZ);
						buildDam();
						continue;
					}
					
					if(this.beam && canBreak(block, this.targetX, this.targetY, this.targetZ)) {
						
						this.breakProgress += getBreakSpeed(speed);
						clientBreakProgress = Math.min(this.breakProgress, 1);
						
						if(this.breakProgress < 1) {
							this.worldObj.destroyBlockInWorldPartially(-1, this.targetX, this.targetY, this.targetZ, (int) Math.floor(this.breakProgress * 10));
						} else {
							breakBlock(fortune);
							buildDam();
						}
					}
				}
			} else {
				this.targetY = this.yCoord - 2;
				this.beam = false;
			}

			tryFillContainer(this.xCoord + 2, this.yCoord, this.zCoord);
			tryFillContainer(this.xCoord - 2, this.yCoord, this.zCoord);
			tryFillContainer(this.xCoord, this.yCoord, this.zCoord + 2);
			tryFillContainer(this.xCoord, this.yCoord, this.zCoord - 2);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setInteger("lastX", this.lastTargetX);
			data.setInteger("lastY", this.lastTargetY);
			data.setInteger("lastZ", this.lastTargetZ);
			data.setInteger("x", this.targetX);
			data.setInteger("y", this.targetY);
			data.setInteger("z", this.targetZ);
			data.setBoolean("beam", this.beam);
			data.setBoolean("isOn", this.isOn);
			data.setDouble("progress", clientBreakProgress);
			
			networkPack(data, 250);
		}
	}
	
	private void updateConnections() {
		this.trySubscribe(this.worldObj, this.xCoord, this.yCoord + 2, this.zCoord, ForgeDirection.UP);
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {

		this.power = data.getLong("power");
		this.lastTargetX = data.getInteger("lastX");
		this.lastTargetY = data.getInteger("lastY");
		this.lastTargetZ = data.getInteger("lastZ");
		this.targetX = data.getInteger("x");
		this.targetY = data.getInteger("y");
		this.targetZ = data.getInteger("z");
		this.beam = data.getBoolean("beam");
		this.isOn = data.getBoolean("isOn");
		this.breakProgress = data.getDouble("progress");
	}
	
	private void buildDam() {

		if(this.worldObj.getBlock(this.targetX + 1, this.targetY, this.targetZ).getMaterial().isLiquid())
			this.worldObj.setBlock(this.targetX + 1, this.targetY, this.targetZ, ModBlocks.barricade);
		if(this.worldObj.getBlock(this.targetX - 1, this.targetY, this.targetZ).getMaterial().isLiquid())
			this.worldObj.setBlock(this.targetX - 1, this.targetY, this.targetZ, ModBlocks.barricade);
		if(this.worldObj.getBlock(this.targetX, this.targetY, this.targetZ + 1).getMaterial().isLiquid())
			this.worldObj.setBlock(this.targetX, this.targetY, this.targetZ + 1, ModBlocks.barricade);
		if(this.worldObj.getBlock(this.targetX, this.targetY, this.targetZ - 1).getMaterial().isLiquid())
			this.worldObj.setBlock(this.targetX, this.targetY, this.targetZ - 1, ModBlocks.barricade);
	}
	
	private void tryFillContainer(int x, int y, int z) {
		
		Block b = this.worldObj.getBlock(x, y, z);
		if(b != Blocks.chest && b != Blocks.trapped_chest && b != ModBlocks.crate_iron && b != ModBlocks.crate_desh &&
				b != ModBlocks.crate_steel && b != ModBlocks.safe && b != Blocks.hopper)
			return;
		
		IInventory inventory = (IInventory)this.worldObj.getTileEntity(x, y, z);
		if(inventory == null)
			return;
		
		for(int i = 9; i <= 29; i++) {
			
			if(this.slots[i] != null) {
				int prev = this.slots[i].stackSize;
				this.slots[i] = InventoryUtil.tryAddItemToInventory(inventory, 0, inventory.getSizeInventory() - 1, this.slots[i]);
				
				if(this.slots[i] == null || this.slots[i].stackSize < prev)
					return;
			}
		}
	}
	
	private void breakBlock(int fortune) {
		
		Block b = this.worldObj.getBlock(this.targetX, this.targetY, this.targetZ);
		int meta = this.worldObj.getBlockMetadata(this.targetX, this.targetY, this.targetZ);
		boolean normal = true;
		boolean doesBreak = true;
		
		if(b == Blocks.lit_redstone_ore)
			b = Blocks.redstone_ore;
		
		ItemStack stack = new ItemStack(b, 1, meta);
		
		if(stack != null && stack.getItem() != null) {
			if(hasCrystallizer()) {

				CrystallizerRecipe result = CrystallizerRecipes.getOutput(stack, Fluids.ACID);
				if(result == null) result = CrystallizerRecipes.getOutput(stack, Fluids.SULFURIC_ACID);
				
				if(result != null) {
					this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.targetX + 0.5, this.targetY + 0.5, this.targetZ + 0.5, result.output.copy()));
					normal = false;
				}
				
			} else if(hasCentrifuge()) {
				
				ItemStack[] result = CentrifugeRecipes.getOutput(stack);
				if(result != null) {
					for(ItemStack sta : result) {
						
						if(sta != null) {
							this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.targetX + 0.5, this.targetY + 0.5, this.targetZ + 0.5, sta.copy()));
							normal = false;
						}
					}
				}
				
			} else if(hasShredder()) {
				
				ItemStack result = ShredderRecipes.getShredderResult(stack);
				if(result != null && result.getItem() != ModItems.scrap) {
					this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.targetX + 0.5, this.targetY + 0.5, this.targetZ + 0.5, result.copy()));
					normal = false;
				}
				
			} else if(hasSmelter()) {
				
				ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(stack);
				if(result != null) {
					this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.targetX + 0.5, this.targetY + 0.5, this.targetZ + 0.5, result.copy()));
					normal = false;
				}
			}
		}
		
		if(normal && b instanceof IDrillInteraction) {
			IDrillInteraction in = (IDrillInteraction) b;
			ItemStack drop = in.extractResource(this.worldObj, this.targetX, this.targetY, this.targetZ, meta, this);
			
			if(drop != null) {
				this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.targetX + 0.5, this.targetY + 0.5, this.targetZ + 0.5, drop.copy()));
			}
			
			doesBreak = in.canBreak(this.worldObj, this.targetX, this.targetY, this.targetZ, meta, this);
		}
		
		if(doesBreak) {
			if(normal) b.dropBlockAsItem(this.worldObj, this.targetX, this.targetY, this.targetZ, meta, fortune);
			this.worldObj.func_147480_a(this.targetX, this.targetY, this.targetZ, false);
		}
		
		suckDrops();

		if(doesScream()) {
			this.worldObj.playSoundEffect(this.targetX + 0.5, this.targetY + 0.5, this.targetZ + 0.5, "hbm:block.screm", 2000.0F, 1.0F);
		}
		
		this.breakProgress = 0;
	}
	
	private static final Set<Item> bad = Sets.newHashSet(new Item[] {
			Item.getItemFromBlock(Blocks.dirt),
			Item.getItemFromBlock(Blocks.stone),
			Item.getItemFromBlock(Blocks.cobblestone),
			Item.getItemFromBlock(Blocks.sand),
			Item.getItemFromBlock(Blocks.sandstone),
			Item.getItemFromBlock(Blocks.gravel),
			Item.getItemFromBlock(ModBlocks.basalt),
			Item.getItemFromBlock(ModBlocks.stone_gneiss),
			Items.flint,
			Items.snowball,
			Items.wheat_seeds
			});
	
	//hahahahahahahaha he said "suck"
	@SuppressWarnings("unchecked")
	private void suckDrops() {
		
		int rangeHor = 3;
		int rangeVer = 1;
		boolean nullifier = hasNullifier();
		
		List<EntityItem> items = this.worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(
				this.targetX + 0.5 - rangeHor,
				this.targetY + 0.5 - rangeVer,
				this.targetZ + 0.5 - rangeHor,
				this.targetX + 0.5 + rangeHor,
				this.targetY + 0.5 + rangeVer,
				this.targetZ + 0.5 + rangeHor
				));
		
		for(EntityItem item : items) {
			
			if(item.isDead) continue;
			
			if(nullifier && TileEntityMachineMiningLaser.bad.contains(item.getEntityItem().getItem())) {
				item.setDead();
				continue;
			}
			
			if(item.getEntityItem().getItem() == Item.getItemFromBlock(ModBlocks.ore_oil)) {
				
				this.tank.setTankType(Fluids.OIL); //just to be sure
				
				this.tank.setFill(this.tank.getFill() + 500);
				if(this.tank.getFill() > this.tank.getMaxFill())
					this.tank.setFill(this.tank.getMaxFill());
				
				item.setDead();
				continue;
			}
			
			ItemStack stack = InventoryUtil.tryAddItemToInventory(this.slots, 9, 29, item.getEntityItem().copy());
			
			if(stack == null)
				item.setDead();
			else
				item.setEntityItemStack(stack.copy()); //copy is not necessary but i'm paranoid due to the kerfuffle of the old drill
		}
		
		List<EntityLivingBase> mobs = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(
				this.targetX + 0.5 - 1,
				this.targetY + 0.5 - 1,
				this.targetZ + 0.5 - 1,
				this.targetX + 0.5 + 1,
				this.targetY + 0.5 + 1,
				this.targetZ + 0.5 + 1
				));
		
		for(EntityLivingBase mob : mobs) {
			mob.setFire(5);
		}
	}
	
	public double getBreakSpeed(int speed) {
		
		float hardness = this.worldObj.getBlock(this.targetX, this.targetY, this.targetZ).getBlockHardness(this.worldObj, this.targetX, this.targetY, this.targetZ) * 15 / speed;
		
		if(hardness == 0)
			return 1;
		
		return 1 / hardness;
	}
	
	public void scan(int range) {
		
		for(int x = -range; x <= range; x++) {
			for(int z = -range; z <= range; z++) {
				
				if(this.worldObj.getBlock(x + this.xCoord, this.targetY, z + this.zCoord).getMaterial().isLiquid()) {
					continue;
				}
				
				if(canBreak(this.worldObj.getBlock(x + this.xCoord, this.targetY, z + this.zCoord), x + this.xCoord, this.targetY, z + this.zCoord)) {
					this.targetX = x + this.xCoord;
					this.targetZ = z + this.zCoord;
					this.beam = true;
					return;
				}
			}
		}
		
		this.beam = false;
		this.targetY--;
	}
	
	private boolean canBreak(Block block, int x, int y, int z) {
		return !block.isAir(this.worldObj, x, y, z) && block.getBlockHardness(this.worldObj, x, y, z) >= 0 && !block.getMaterial().isLiquid() && block != Blocks.bedrock;
	}
	
	public int getRange() {
		
		int range = 1;
		
		for(int i = 1; i < 9; i++) {
			
			if(this.slots[i] != null) {
				
				if(this.slots[i].getItem() == ModItems.upgrade_effect_1)
					range += 2;
				else if(this.slots[i].getItem() == ModItems.upgrade_effect_2)
					range += 4;
				else if(this.slots[i].getItem() == ModItems.upgrade_effect_3)
					range += 6;
			}
		}
		
		return Math.min(range, 25);
	}
	
	public boolean hasNullifier() {
		
		for(int i = 1; i < 9; i++) {
			
			if(this.slots[i] != null) {
				
				if(this.slots[i].getItem() == ModItems.upgrade_nullifier)
					return true;
			}
		}
		
		return false;
	}
	
	public boolean hasSmelter() {
		
		for(int i = 1; i < 9; i++) {
			
			if(this.slots[i] != null) {
				
				if(this.slots[i].getItem() == ModItems.upgrade_smelter)
					return true;
			}
		}
		
		return false;
	}
	
	public boolean hasShredder() {
		
		for(int i = 1; i < 9; i++) {
			
			if(this.slots[i] != null) {
				
				if(this.slots[i].getItem() == ModItems.upgrade_shredder)
					return true;
			}
		}
		
		return false;
	}
	
	public boolean hasCentrifuge() {
		
		for(int i = 1; i < 9; i++) {
			
			if(this.slots[i] != null) {
				
				if(this.slots[i].getItem() == ModItems.upgrade_centrifuge)
					return true;
			}
		}
		
		return false;
	}
	
	public boolean hasCrystallizer() {
		
		for(int i = 1; i < 9; i++) {
			
			if(this.slots[i] != null) {
				
				if(this.slots[i].getItem() == ModItems.upgrade_crystallizer)
					return true;
			}
		}
		
		return false;
	}
	
	public boolean doesScream() {
		
		for(int i = 1; i < 9; i++) {
			
			if(this.slots[i] != null) {
				
				if(this.slots[i].getItem() == ModItems.upgrade_screm)
					return true;
			}
		}
		
		return false;
	}
	
	public int getConsumption() {
		return TileEntityMachineMiningLaser.consumption;
	}
	
	public int getWidth() {
		
		return 1 + getRange() * 2;
	}

	public int getPowerScaled(int i) {
		return (int)((this.power * i) / TileEntityMachineMiningLaser.maxPower);
	}

	public int getProgressScaled(int i) {
		return (int) (this.breakProgress * i);
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
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i >= 9 && i <= 29;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int slot) {
		
		int[] slots = new int[21];
		
		for(int i = 0; i < 21; i++) {
			slots[i] = i + 9;
		}
		
		return slots;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		super.setInventorySlotContents(i, stack);
		
		if(stack != null && i >= 1 && i <= 8 && stack.getItem() instanceof ItemMachineUpgrade)
			this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 1.5, this.zCoord + 0.5, "hbm:item.upgradePlug", 1.0F, 1.0F);
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
		return TileEntityMachineMiningLaser.maxPower;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		this.tank.setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		if(type == Fluids.OIL)
			this.tank.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		this.tank.setTankType(type);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type == Fluids.OIL)
			return this.tank.getFill();
		return 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {

		fillFluid(this.xCoord + 2, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord - 2, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord + 2, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord - 2, getTact(), type);
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
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.tank.readFromNBT(nbt, "oil");
		this.isOn = nbt.getBoolean("isOn");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		this.tank.writeToNBT(nbt, "oil");
		nbt.setBoolean("isOn", this.isOn);
	}

	@Override
	public DrillType getDrillTier() {
		return DrillType.HITECH;
	}

	@Override
	public int getDrillRating() {
		return 100;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { this.tank };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { this.tank };
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMiningLaser(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMiningLaser(player.inventory, this);
	}
}
