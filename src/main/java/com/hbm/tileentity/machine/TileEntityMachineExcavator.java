package com.hbm.tileentity.machine;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockBedrockOreTE.TileEntityBedrockOre;
import com.hbm.blocks.network.CraneInserter;
import com.hbm.entity.item.EntityMovingItem;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerMachineExcavator;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineExcavator;
import com.hbm.inventory.recipes.ShredderRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemDrillbit;
import com.hbm.items.machine.ItemDrillbit.EnumDrillType;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.Compat;
import com.hbm.util.EnumUtil;
import com.hbm.util.ItemStackUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.conveyor.IConveyorBelt;
import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineExcavator extends TileEntityMachineBase implements IEnergyUser, IFluidStandardReceiver, IControlReceiver, IGUIProvider {

	public static final long maxPower = 1_000_000;
	public long power;
	public boolean operational = false;
	
	public boolean enableDrill = false;
	public boolean enableCrusher = false;
	public boolean enableWalling = false;
	public boolean enableVeinMiner = false;
	public boolean enableSilkTouch = false;
	
	protected int ticksWorked = 0;
	protected int targetDepth = 0; //0 is the first block below null position
	protected boolean bedrockDrilling = false;

	public float drillRotation = 0F;
	public float prevDrillRotation = 0F;
	public float drillExtension = 0F;
	public float prevDrillExtension = 0F;
	public float crusherRotation = 0F;
	public float prevCrusherRotation = 0F;
	public int chuteTimer = 0;
	
	public double speed = 1.0D;
	public final long baseConsumption = 10_000L;
	public long consumption = this.baseConsumption;
	
	public FluidTank tank;

	public TileEntityMachineExcavator() {
		super(14);
		this.tank = new FluidTank(Fluids.SULFURIC_ACID, 16_000);
	}

	@Override
	public String getName() {
		return "container.machineExcavator";
	}

	@Override
	public void updateEntity() {
		
		//needs to happen on client too for GUI rendering
		UpgradeManager.eval(this.slots, 2, 3);
		int speedLevel = Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3);
		int powerLevel = Math.min(UpgradeManager.getLevel(UpgradeType.POWER), 3);
		
		this.consumption = this.baseConsumption * (1 + speedLevel);
		this.consumption /= (1 + powerLevel);
		
		if(!this.worldObj.isRemote) {
			
			this.tank.setType(1, this.slots);
			
			if(this.worldObj.getTotalWorldTime() % 20 == 0) {
				tryEjectBuffer();
				
				for(DirPos pos : getConPos()) {
					this.trySubscribe(this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
					this.trySubscribe(this.tank.getTankType(), this.worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				}
			}
			
			if(this.chuteTimer > 0) this.chuteTimer--;
			
			this.power = Library.chargeTEFromItems(this.slots, 0, getPower(), getMaxPower());
			this.operational = false;
			int radiusLevel = Math.min(UpgradeManager.getLevel(UpgradeType.EFFECT), 3);
			
			EnumDrillType type = getInstalledDrill();
			if(this.enableDrill && type != null && this.power >= getPowerConsumption()) {
				
				this.operational = true;
				this.power -= getPowerConsumption();
				
				this.speed = type.speed;
				this.speed *= (1 + speedLevel / 2D);
				
				int maxDepth = this.yCoord - 4;

				if((this.bedrockDrilling || this.targetDepth <= maxDepth) && tryDrill(1 + radiusLevel * 2)) {
					this.targetDepth++;
					
					if(this.targetDepth > maxDepth) {
						this.enableDrill = false;
					}
				}
			} else {
				this.targetDepth = 0;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("d", this.enableDrill);
			data.setBoolean("c", this.enableCrusher);
			data.setBoolean("w", this.enableWalling);
			data.setBoolean("v", this.enableVeinMiner);
			data.setBoolean("s", this.enableSilkTouch);
			data.setBoolean("o", this.operational);
			data.setInteger("t", this.targetDepth);
			data.setInteger("g", this.chuteTimer);
			data.setLong("p", this.power);
			this.tank.writeToNBT(data, "tank");
			networkPack(data, 150);
			
		} else {
			
			this.prevDrillExtension = this.drillExtension;
			
			if(this.drillExtension != this.targetDepth) {
				float diff = Math.abs(this.drillExtension - this.targetDepth);
				float speed = Math.max(0.15F, diff / 10F);
				
				if(diff <= speed) {
					this.drillExtension = this.targetDepth;
				} else {
					float sig = Math.signum(this.drillExtension - this.targetDepth);
					this.drillExtension -= sig * speed;
				}
			}

			this.prevDrillRotation = this.drillRotation;
			this.prevCrusherRotation = this.crusherRotation;
			
			if(this.operational) {
				this.drillRotation += 15F;
				
				if(this.enableCrusher) {
					this.crusherRotation += 15F;
				}
			}
			
			if(this.drillRotation >= 360F) {
				this.drillRotation -= 360F;
				this.prevDrillRotation -= 360F;
			}
			
			if(this.crusherRotation >= 360F) {
				this.crusherRotation -= 360F;
				this.prevCrusherRotation -= 360F;
			}
		}
	}
	
	protected DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		return new DirPos[] {
				new DirPos(this.xCoord + dir.offsetX * 4 + rot.offsetX, this.yCoord + 1, this.zCoord + dir.offsetZ * 4 + rot.offsetZ, dir),
				new DirPos(this.xCoord + dir.offsetX * 4 - rot.offsetX, this.yCoord + 1, this.zCoord + dir.offsetZ * 4 - rot.offsetZ, dir),
				new DirPos(this.xCoord + rot.offsetX * 4, this.yCoord + 1, this.zCoord + rot.offsetZ * 4, rot),
				new DirPos(this.xCoord - rot.offsetX * 4, this.yCoord + 1, this.zCoord - rot.offsetZ * 4, rot.getOpposite())
		};
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.enableDrill = nbt.getBoolean("d");
		this.enableCrusher = nbt.getBoolean("c");
		this.enableWalling = nbt.getBoolean("w");
		this.enableVeinMiner = nbt.getBoolean("v");
		this.enableSilkTouch = nbt.getBoolean("s");
		this.operational = nbt.getBoolean("o");
		this.targetDepth = nbt.getInteger("t");
		this.chuteTimer = nbt.getInteger("g");
		this.power = nbt.getLong("p");
		this.tank.readFromNBT(nbt, "tank");
	}
	
	protected int getY() {
		return this.yCoord - this.targetDepth - 4;
	}
	
	/** Works outwards and tries to break a ring, returns true if all rings are broken (or ignorable) and the drill should extend. */
	protected boolean tryDrill(int radius) {
		int y = getY();

		if(this.targetDepth == 0 || y == 0) {
			radius = 1;
		}
		
		for(int ring = 1; ring <= radius; ring++) {
			
			boolean ignoreAll = true;
			float combinedHardness = 0F;
			BlockPos bedrockOre = null;
			this.bedrockDrilling = false;
			
			for(int x = this.xCoord - ring; x <= this.xCoord + ring; x++) {
				for(int z = this.zCoord - ring; z <= this.zCoord + ring; z++) {
					
					/* Process blocks either if we are in the inner ring (1 = 3x3) or if the target block is on the outer edge */
					if(ring == 1 || (x == this.xCoord - ring || x == this.xCoord + ring || z == this.zCoord - ring || z == this.zCoord + ring)) {
						
						Block b = this.worldObj.getBlock(x, y, z);
						
						if(b == ModBlocks.ore_bedrock) {
							combinedHardness = 60 * 20;
							bedrockOre = new BlockPos(x, y, z);
							this.bedrockDrilling = true;
							this.enableCrusher = false;
							ignoreAll = false;
							break;
						}
						
						if(shouldIgnoreBlock(b, x, y ,z)) continue;
						
						ignoreAll = false;
						
						combinedHardness += b.getBlockHardness(this.worldObj, x, y, z);
					}
				}
			}
			
			if(!ignoreAll) {
				this.ticksWorked++;
				
				int ticksToWork = (int) Math.ceil(combinedHardness / this.speed);
				
				if(this.ticksWorked >= ticksToWork) {
					
					if(bedrockOre == null) {
						breakBlocks(ring);
						buildWall(ring + 1, ring == radius && this.enableWalling);
						if(ring == radius) mineOuterOres(ring + 1);
						tryCollect(radius + 1);
					} else {
						collectBedrock(bedrockOre);
					}
					this.ticksWorked = 0;
				}
				
				return false;
			} else {
				tryCollect(radius + 1);
			}
		}

		buildWall(radius + 1, this.enableWalling);
		this.ticksWorked = 0;
		return true;
	}
	
	protected void collectBedrock(BlockPos pos) {
		TileEntity oreTile = Compat.getTileStandard(this.worldObj, pos.getX(), pos.getY(), pos.getZ());
		
		if(oreTile instanceof TileEntityBedrockOre) {
			TileEntityBedrockOre ore = (TileEntityBedrockOre) oreTile;
			
			if((ore.resource == null) || (ore.tier > getInstalledDrill().tier)) return;
			if(ore.acidRequirement != null) {
				
				if(ore.acidRequirement.type != this.tank.getTankType() || ore.acidRequirement.fill > this.tank.getFill()) return;
				
				this.tank.setFill(this.tank.getFill() - ore.acidRequirement.fill);
			}
			
			ItemStack stack = ore.resource.copy();
			List<ItemStack> stacks = new ArrayList<>();
			stacks.add(stack);

			ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - 10);

			int x = this.xCoord + dir.offsetX * 4;
			int y = this.yCoord - 3;
			int z = this.zCoord + dir.offsetZ * 4;
			
			/* try to insert into a valid container */
			TileEntity tile = this.worldObj.getTileEntity(x, y, z);
			if(tile instanceof IInventory) {
				supplyContainer((IInventory) tile, stacks, dir.getOpposite());
			}
			
			if(stack.stackSize <= 0) return;
			
			/* try to place on conveyor belt */
			Block b = this.worldObj.getBlock(x, y, z);
			if(b instanceof IConveyorBelt) {
				supplyConveyor((IConveyorBelt) b, stacks, x, y, z);
			}
			
			if(stack.stackSize <= 0) return;
			
			for(int i = 5; i < 14; i++) {
				
				if(this.slots[i] != null && this.slots[i].stackSize < this.slots[i].getMaxStackSize() && stack.isItemEqual(this.slots[i]) && ItemStack.areItemStackTagsEqual(stack, this.slots[i])) {
					int toAdd = Math.min(this.slots[i].getMaxStackSize() - this.slots[i].stackSize, stack.stackSize);
					this.slots[i].stackSize += toAdd;
					stack.stackSize -= toAdd;
					
					this.chuteTimer = 40;
					
					if(stack.stackSize <= 0) {
						return;
					}
				}
			}
			
			/* add leftovers to empty slots */
			for(int i = 5; i < 14; i++) {
				
				if(this.slots[i] == null) {
					
					this.chuteTimer = 40;
					
					this.slots[i] = stack.copy();
					return;
				}
			}
		}
	}
	
	/** breaks and drops all blocks in the specified ring */
	protected void breakBlocks(int ring) {
		int y = getY();
		
		for(int x = this.xCoord - ring; x <= this.xCoord + ring; x++) {
			for(int z = this.zCoord - ring; z <= this.zCoord + ring; z++) {
				
				if(ring == 1 || (x == this.xCoord - ring || x == this.xCoord + ring || z == this.zCoord - ring || z == this.zCoord + ring)) {
					
					Block b = this.worldObj.getBlock(x, y, z);
					
					if(!shouldIgnoreBlock(b, x, y, z)) {
						tryMineAtLocation(x, y, z);
					}
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void tryMineAtLocation(int x ,int y, int z) {

		Block b = this.worldObj.getBlock(x, y, z);
		
		if(this.enableVeinMiner && getInstalledDrill().vein) {
			
			if(isOre(x, y, z, b)) {
				this.minX = x;
				this.minY = y;
				this.minZ = z;
				this.maxX = x;
				this.maxY = y;
				this.maxZ = z;
				breakRecursively(x, y, z, 10);
				this.recursionBrake.clear();
				
				/* move all excavated items to the last drillable position which is also within collection range */
				List<EntityItem> items = this.worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(this.minX, this.minY, this.minZ, this.maxX + 1, this.maxY + 1, this.maxZ + 1));
				for(EntityItem item : items) item.setPosition(x + 0.5, y + 0.5, z + 0.5);
				
				return;
			}
		}
		breakSingleBlock(b, x, y, z);
	}
	
	protected boolean isOre(int x ,int y, int z, Block b) {
		
		/* doing this isn't terribly accurate but just for figuring out if there's OD it works */
		Item blockItem = Item.getItemFromBlock(b);
		
		if(blockItem != null) {
			List<String> names = ItemStackUtil.getOreDictNames(new ItemStack(blockItem));
			
			for(String name : names) {
				if(name.startsWith("ore")) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private HashSet<BlockPos> recursionBrake = new HashSet<>();
	private int minX = 0, minY = 0, minZ = 0, maxX = 0, maxY = 0, maxZ = 0;
	protected void breakRecursively(int x ,int y, int z, int depth) {
		
		if(depth < 0) return;
		BlockPos pos = new BlockPos(x, y, z);
		if(this.recursionBrake.contains(pos)) return;
		this.recursionBrake.add(pos);
		
		Block b = this.worldObj.getBlock(x, y, z);
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			int ix = x + dir.offsetX;
			int iy = y + dir.offsetY;
			int iz = z + dir.offsetZ;
			
			if(this.worldObj.getBlock(ix, iy, iz) == b) {
				breakRecursively(ix, iy, iz, depth - 1);
			}
		}

		breakSingleBlock(b, x, y, z);

		if(x < this.minX) this.minX = x;
		if(x > this.maxX) this.maxX = x;
		if(y < this.minY) this.minY = y;
		if(y > this.maxY) this.maxY = y;
		if(z < this.minZ) this.minZ = z;
		if(z > this.maxZ) this.maxZ = z;
		
		if(this.enableWalling) {
			this.worldObj.setBlock(x, y, z, ModBlocks.barricade);
		}
	}
	
	protected void breakSingleBlock(Block b, int x ,int y, int z) {
		
		List<ItemStack> items = b.getDrops(this.worldObj, x, y, z, this.worldObj.getBlockMetadata(x, y, z), getFortuneLevel());
		
		if(canSilkTouch()) {
			
			try {
				Method createStackedBlock = ReflectionHelper.findMethod(Block.class, b, new String[] {"createStackedBlock", "func_149644_j"}, int.class);
				ItemStack result = (ItemStack) createStackedBlock.invoke(b, this.worldObj.getBlockMetadata(x, y, z));
				
				if(result != null) {
					items.clear();
					items.add(result.copy());
				}
			} catch(Exception ex) { }
		}
		
		if(this.enableCrusher) {
			
			List<ItemStack> list = new ArrayList<>();
			
			for(ItemStack stack : items) {
				ItemStack crushed = ShredderRecipes.getShredderResult(stack).copy();
				
				if(crushed.getItem() == ModItems.scrap || crushed.getItem() == ModItems.dust) {
					list.add(stack);
				} else {
					crushed.stackSize *= stack.stackSize;
					list.add(crushed);
				}
			}
			
			items = list;
		}
		
		if(b == ModBlocks.barricade)
			items.clear();
		
		for(ItemStack item : items) {
			this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, x + 0.5, y + 0.5, z + 0.5, item));
		}
		
		this.worldObj.func_147480_a(x, y, z, false);
	}
	
	/** builds a wall along the specified ring, replacing fluid blocks. if wallEverything is set, it will also wall off replacable blocks like air or grass */
	protected void buildWall(int ring, boolean wallEverything) {
		int y = getY();
		
		for(int x = this.xCoord - ring; x <= this.xCoord + ring; x++) {
			for(int z = this.zCoord - ring; z <= this.zCoord + ring; z++) {
				
				Block b = this.worldObj.getBlock(x, y, z);
				
				if(x == this.xCoord - ring || x == this.xCoord + ring || z == this.zCoord - ring || z == this.zCoord + ring) {
					
					if(b.isReplaceable(this.worldObj, x, y, z) && (wallEverything || b.getMaterial().isLiquid())) {
						this.worldObj.setBlock(x, y, z, ModBlocks.barricade);
					}
				} else {
					
					if(b.getMaterial().isLiquid()) {
						this.worldObj.setBlockToAir(x, y, z);
						continue;
					}
				}
			}
		}
	}
	protected void mineOuterOres(int ring) {
		int y = getY();
		
		for(int x = this.xCoord - ring; x <= this.xCoord + ring; x++) {
			for(int z = this.zCoord - ring; z <= this.zCoord + ring; z++) {
				
				if(ring == 1 || (x == this.xCoord - ring || x == this.xCoord + ring || z == this.zCoord - ring || z == this.zCoord + ring)) {
					
					Block b = this.worldObj.getBlock(x, y, z);
					
					if(!shouldIgnoreBlock(b, x, y, z) && isOre(x, y, z, b)) {
						tryMineAtLocation(x, y, z);
					}
				}
			}
		}
	}
	
	protected void tryEjectBuffer() {

		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - 10);

		int x = this.xCoord + dir.offsetX * 4;
		int y = this.yCoord - 3;
		int z = this.zCoord + dir.offsetZ * 4;
		
		List<ItemStack> items = new ArrayList<>();

		for(int i = 5; i < 14; i++) {
			ItemStack stack = this.slots[i];
			
			if(stack != null) {
				items.add(stack.copy());
			}
		}
		
		TileEntity tile = this.worldObj.getTileEntity(x, y, z);
		if(tile instanceof IInventory) {
			supplyContainer((IInventory) tile, items, dir.getOpposite());
		}
		
		Block b = this.worldObj.getBlock(x, y, z);
		if(b instanceof IConveyorBelt) {
			supplyConveyor((IConveyorBelt) b, items, x, y, z);
		}
		
		items.removeIf(i -> i == null || i.stackSize <= 0);

		for(int i = 5; i < 14; i++) {
			int index = i - 5;
			
			if(items.size() > index) {
				this.slots[i] = items.get(index).copy();
			} else {
				this.slots[i] = null;
			}
		}
	}
	
	/** pulls up an AABB around the drillbit and tries to either conveyor output or buffer collected items */
	@SuppressWarnings("unchecked")
	protected void tryCollect(int radius) {
		int yLevel = getY();
		
		List<EntityItem> items = this.worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(this.xCoord - radius, yLevel - 1, this.zCoord - radius, this.xCoord + radius + 1, yLevel + 2, this.zCoord + radius + 1));
		
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - 10);

		int x = this.xCoord + dir.offsetX * 4;
		int y = this.yCoord - 3;
		int z = this.zCoord + dir.offsetZ * 4;
		
		List<ItemStack> stacks = new ArrayList<>();
		items.forEach(i -> stacks.add(i.getEntityItem()));
		
		/* try to insert into a valid container */
		TileEntity tile = this.worldObj.getTileEntity(x, y, z);
		if(tile instanceof IInventory) {
			supplyContainer((IInventory) tile, stacks, dir.getOpposite());
		}
		
		/* try to place on conveyor belt */
		Block b = this.worldObj.getBlock(x, y, z);
		if(b instanceof IConveyorBelt) {
			supplyConveyor((IConveyorBelt) b, stacks, x, y, z);
		}
		
		items.removeIf(i -> i.isDead || i.getEntityItem().stackSize <= 0);
		
		/* collect remaining items in internal buffer */
		outer:
		for(EntityItem item : items) {
			
			ItemStack stack = item.getEntityItem();
			
			/* adding items to existing stacks */
			for(int i = 5; i < 14; i++) {
				
				if(this.slots[i] != null && this.slots[i].stackSize < this.slots[i].getMaxStackSize() && stack.isItemEqual(this.slots[i]) && ItemStack.areItemStackTagsEqual(stack, this.slots[i])) {
					int toAdd = Math.min(this.slots[i].getMaxStackSize() - this.slots[i].stackSize, stack.stackSize);
					this.slots[i].stackSize += toAdd;
					stack.stackSize -= toAdd;
					
					this.chuteTimer = 40;
					
					if(stack.stackSize <= 0) {
						item.setDead();
						continue outer;
					}
				}
			}
			
			/* add leftovers to empty slots */
			for(int i = 5; i < 14; i++) {
				
				if(this.slots[i] == null) {
					
					this.chuteTimer = 40;
					
					this.slots[i] = stack.copy();
					item.setDead();
					break;
				}
			}
		}
	}
	
	/** places all items into a connected container, if possible */
	protected void supplyContainer(IInventory inv, List<ItemStack> items, ForgeDirection dir) {
		
		int side = dir.ordinal();
		int[] access = null;
		
		if(inv instanceof ISidedInventory) {
			ISidedInventory sided = (ISidedInventory) inv;
			access = CraneInserter.masquerade(sided, dir.ordinal());
		}
		
		for(ItemStack item : items) {
			
			if(item.stackSize <= 0) continue;
			
			CraneInserter.addToInventory(inv, access, item, side);
			this.chuteTimer = 40;
		}
	}
	
	/** moves all items onto a connected conveyor belt */
	protected void supplyConveyor(IConveyorBelt belt, List<ItemStack> items, int x, int y, int z) {
		
		Random rand = this.worldObj.rand;
		
		for(ItemStack item : items) {
			
			if(item.stackSize <= 0) continue;
			
			Vec3 base = Vec3.createVectorHelper(x + rand.nextDouble(), y + 0.5, z + rand.nextDouble());
			Vec3 vec = belt.getClosestSnappingPosition(this.worldObj, x, y, z, base);
			
			EntityMovingItem moving = new EntityMovingItem(this.worldObj);
			moving.setPosition(base.xCoord, vec.yCoord, base.zCoord);
			moving.setItemStack(item.copy());
			this.worldObj.spawnEntityInWorld(moving);
			item.stackSize = 0;
			
			this.chuteTimer = 40;
		}
	}
	
	public long getPowerConsumption() {
		return this.consumption;
	}
	
	public int getFortuneLevel() {
		EnumDrillType type = getInstalledDrill();
		
		if(type != null) return type.fortune;
		return 0;
	}
	
	public boolean shouldIgnoreBlock(Block block, int x, int y, int z) {
		return block.isAir(this.worldObj, x, y, z) || block.getMaterial() == ModBlocks.materialGas || block.getBlockHardness(this.worldObj, x, y, z) < 0 || block.getMaterial().isLiquid() || block == Blocks.bedrock;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("drill")) this.enableDrill = !this.enableDrill;
		if(data.hasKey("crusher")) this.enableCrusher = !this.enableCrusher;
		if(data.hasKey("walling")) this.enableWalling = !this.enableWalling;
		if(data.hasKey("veinminer")) this.enableVeinMiner = !this.enableVeinMiner;
		if(data.hasKey("silktouch")) this.enableSilkTouch = !this.enableSilkTouch;
		
		markChanged();
	}
	
	public EnumDrillType getInstalledDrill() {
		if(this.slots[4] != null && this.slots[4].getItem() instanceof ItemDrillbit) {
			return EnumUtil.grabEnumSafely(EnumDrillType.class, this.slots[4].getItemDamage());
		}
		
		return null;
	}
	
	public boolean canVeinMine() {
		EnumDrillType type = getInstalledDrill();
		return this.enableVeinMiner && type != null && type.vein;
	}
	
	public boolean canSilkTouch() {
		EnumDrillType type = getInstalledDrill();
		return this.enableSilkTouch && type != null && type.silk;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.enableDrill = nbt.getBoolean("d");
		this.enableCrusher = nbt.getBoolean("c");
		this.enableWalling = nbt.getBoolean("w");
		this.enableVeinMiner = nbt.getBoolean("v");
		this.enableSilkTouch = nbt.getBoolean("s");
		this.targetDepth = nbt.getInteger("t");
		this.power = nbt.getLong("p");
		this.tank.readFromNBT(nbt, "tank");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setBoolean("d", this.enableDrill);
		nbt.setBoolean("c", this.enableCrusher);
		nbt.setBoolean("w", this.enableWalling);
		nbt.setBoolean("v", this.enableVeinMiner);
		nbt.setBoolean("s", this.enableSilkTouch);
		nbt.setInteger("t", this.targetDepth);
		nbt.setLong("p", this.power);
		this.tank.writeToNBT(nbt, "tank");
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return isUseableByPlayer(player);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineExcavator(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineExcavator(player.inventory, this);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(this.bb == null) {
			this.bb = AxisAlignedBB.getBoundingBox(
					this.xCoord - 3,
					0,
					this.zCoord - 3,
					this.xCoord + 4,
					this.yCoord + 5,
					this.zCoord + 4
					);
		}
		
		return this.bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getMaxPower() {
		return TileEntityMachineExcavator.maxPower;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {this.tank};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {this.tank};
	}
}
