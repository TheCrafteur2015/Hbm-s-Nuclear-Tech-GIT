package com.hbm.entity.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityMinecartCommandBlock;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.item.EntityMinecartEmpty;
import net.minecraft.entity.item.EntityMinecartFurnace;
import net.minecraft.entity.item.EntityMinecartHopper;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.IMinecartCollisionHandler;

/**
 * Not to be used for any minecarts! This class only exists for analyzing the vanilla minecart code!
 *
 */
public abstract class EntityMinecartDeobf extends Entity {
	private boolean isInReverse;
	private String entityName;
	/** Minecart rotational logic matrix */
	private static final int[][][] matrix = new int[][][] {
		{ { 0, 0, -1 }, { 0, 0, 1 } },		//straight -Z to +Z
		{ { -1, 0, 0 }, { 1, 0, 0 } },		//straight -X to +X
		{ { -1, -1, 0 }, { 1, 0, 0 } },		//slope -X up to +X
		{ { -1, 0, 0 }, { 1, -1, 0 } },		//slope +X up to -X
		{ { 0, 0, -1 }, { 0, -1, 1 } },		//slope +Z up to -Z
		{ { 0, -1, -1 }, { 0, 0, 1 } },		//slope -Z up to +Z
		{ { 0, 0, 1 }, { 1, 0, 0 } },		//curve +X to +Z
		{ { 0, 0, 1 }, { -1, 0, 0 } },		//curve -X to +Z
		{ { 0, 0, -1 }, { -1, 0, 0 } },		//curve -X to -Z
		{ { 0, 0, -1 }, { 1, 0, 0 } } };	//curve +X to -Z
	/** appears to be the progress of the turn */
	private int turnProgress;
	private double syncPosX;
	private double syncPosY;
	private double syncPosZ;
	private double minecartYaw;
	private double minecartPitch;
	@SideOnly(Side.CLIENT)
	private double velocityX;
	@SideOnly(Side.CLIENT)
	private double velocityY;
	@SideOnly(Side.CLIENT)
	private double velocityZ;

	/* Forge: Minecart Compatibility Layer Integration. */
	public static float defaultMaxSpeedAirLateral = 0.4f;
	public static float defaultMaxSpeedAirVertical = -1f;
	public static double defaultDragAir = 0.95D;
	protected boolean canUseRail = true;
	protected boolean canBePushed = true;
	private static IMinecartCollisionHandler collisionHandler = null;

	/* Instance versions of the above physics properties */
	private float currentSpeedRail = getMaxCartSpeedOnRail();
	protected float maxSpeedAirLateral = EntityMinecartDeobf.defaultMaxSpeedAirLateral;
	protected float maxSpeedAirVertical = EntityMinecartDeobf.defaultMaxSpeedAirVertical;
	protected double dragAir = EntityMinecartDeobf.defaultDragAir;

	public EntityMinecartDeobf(World p_i1712_1_) {
		super(p_i1712_1_);
		this.preventEntitySpawning = true;
		setSize(0.98F, 0.7F);
		this.yOffset = this.height / 2.0F;
	}

	/**
	 * Creates a new minecart of the specified type in the specified location in
	 * the given world. par0World - world to create the minecart in, double
	 * par1,par3,par5 represent x,y,z respectively. int par7 specifies the type:
	 * 1 for MinecartChest, 2 for MinecartFurnace, 3 for MinecartTNT, 4 for
	 * MinecartMobSpawner, 5 for MinecartHopper and 0 for a standard empty
	 * minecart
	 */
	public static EntityMinecart createMinecart(World p_94090_0_, double p_94090_1_, double p_94090_3_, double p_94090_5_, int p_94090_7_) {
		switch(p_94090_7_) {
		case 1:
			return new EntityMinecartChest(p_94090_0_, p_94090_1_, p_94090_3_, p_94090_5_);
		case 2:
			return new EntityMinecartFurnace(p_94090_0_, p_94090_1_, p_94090_3_, p_94090_5_);
		case 3:
			return new EntityMinecartTNT(p_94090_0_, p_94090_1_, p_94090_3_, p_94090_5_);
		case 4:
			return new EntityMinecartMobSpawner(p_94090_0_, p_94090_1_, p_94090_3_, p_94090_5_);
		case 5:
			return new EntityMinecartHopper(p_94090_0_, p_94090_1_, p_94090_3_, p_94090_5_);
		case 6:
			return new EntityMinecartCommandBlock(p_94090_0_, p_94090_1_, p_94090_3_, p_94090_5_);
		default:
			return new EntityMinecartEmpty(p_94090_0_, p_94090_1_, p_94090_3_, p_94090_5_);
		}
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they
	 * walk on. used for spiders and wolves to prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(17, 0);
		this.dataWatcher.addObject(18, 1);
		this.dataWatcher.addObject(19, 0.0F);
		this.dataWatcher.addObject(20, 0);
		this.dataWatcher.addObject(21, 6);
		this.dataWatcher.addObject(22, Byte.valueOf((byte) 0));
	}

	/**
	 * Returns a boundingBox used to collide the entity with other entities and
	 * blocks. This enables the entity to be pushable on contact, like boats or
	 * minecarts.
	 */
	@Override
	public AxisAlignedBB getCollisionBox(Entity entity) {
		return entity.canBePushed() ? entity.boundingBox : null;
	}

	/**
	 * returns the bounding box for this entity
	 */
	@Override
	public AxisAlignedBB getBoundingBox() {
		return null;
	}

	/**
	 * Returns true if this entity should push and be pushed by other entities
	 * when colliding.
	 */
	@Override
	public boolean canBePushed() {
		return this.canBePushed;
	}

	public EntityMinecartDeobf(World p_i1713_1_, double p_i1713_2_, double p_i1713_4_, double p_i1713_6_) {
		this(p_i1713_1_);
		setPosition(p_i1713_2_, p_i1713_4_, p_i1713_6_);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = p_i1713_2_;
		this.prevPosY = p_i1713_4_;
		this.prevPosZ = p_i1713_6_;
	}

	/**
	 * Returns the Y offset from the entity's position for any entity riding
	 * this one.
	 */
	@Override
	public double getMountedYOffset() {
		return this.height * 0.0D - 0.3D;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if(!this.worldObj.isRemote && !this.isDead) {
			if(isEntityInvulnerable()) {
				return false;
			} else {
				setRollingDirection(-getRollingDirection());
				setRollingAmplitude(10);
				setBeenAttacked();
				setDamage(getDamage() + amount * 10.0F);
				boolean flag = source.getEntity() instanceof EntityPlayer && ((EntityPlayer) source.getEntity()).capabilities.isCreativeMode;

				if(flag || getDamage() > 40.0F) {
					if(this.riddenByEntity != null) {
						this.riddenByEntity.mountEntity(this);
					}

					if(flag && !hasCustomInventoryName()) {
						setDead();
					} else {
						killMinecart(source);
					}
				}

				return true;
			}
		} else {
			return true;
		}
	}

	public void killMinecart(DamageSource source) {
		setDead();
		ItemStack itemstack = new ItemStack(Items.minecart, 1);

		if(this.entityName != null) {
			itemstack.setStackDisplayName(this.entityName);
		}

		entityDropItem(itemstack, 0.0F);
	}

	/**
	 * Setups the entity to do the hurt animation. Only used by packets in
	 * multiplayer.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void performHurtAnimation() {
		setRollingDirection(-getRollingDirection());
		setRollingAmplitude(10);
		setDamage(getDamage() + getDamage() * 10.0F);
	}

	/**
	 * Returns true if other Entities should be prevented from moving through
	 * this Entity.
	 */
	@Override
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	/**
	 * Will get destroyed next tick.
	 */
	@Override
	public void setDead() {
		super.setDead();
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		if(getRollingAmplitude() > 0) {
			setRollingAmplitude(getRollingAmplitude() - 1);
		}

		if(getDamage() > 0.0F) {
			setDamage(getDamage() - 1.0F);
		}

		if(this.posY < -64.0D) {
			kill();
		}

		if(!this.worldObj.isRemote && this.worldObj instanceof WorldServer) {
			this.worldObj.theProfiler.startSection("portal");
			MinecraftServer minecraftserver = ((WorldServer) this.worldObj).func_73046_m();
			int portalTime = getMaxInPortalTime();

			if(this.inPortal) {
				if(minecraftserver.getAllowNether()) {
					if(this.ridingEntity == null && this.portalCounter++ >= portalTime) {
						this.portalCounter = portalTime;
						this.timeUntilPortal = getPortalCooldown();
						byte destination;

						if(this.worldObj.provider.dimensionId == -1) {
							destination = 0;
						} else {
							destination = -1;
						}

						travelToDimension(destination);
					}

					this.inPortal = false;
				}
			} else {
				if(this.portalCounter > 0) {
					this.portalCounter -= 4;
				}

				if(this.portalCounter < 0) {
					this.portalCounter = 0;
				}
			}

			if(this.timeUntilPortal > 0) {
				--this.timeUntilPortal;
			}

			this.worldObj.theProfiler.endSection();
		}

		/*
		 * MAJOR SECTION: CLIENT-SIDE TURNING
		 * The only thing the client actually does is using the synchronized values
		 * Copy of EntityLivingBase's position and rotation approximation code
		 */
		if(this.worldObj.isRemote) {
			if(this.turnProgress > 0) {
				double interpX = this.posX + (this.syncPosX - this.posX) / this.turnProgress;
				double interpY = this.posY + (this.syncPosY - this.posY) / this.turnProgress;
				double interpZ = this.posZ + (this.syncPosZ - this.posZ) / this.turnProgress;
				double deltaYaw = MathHelper.wrapAngleTo180_double(this.minecartYaw - this.rotationYaw);
				this.rotationYaw = (float) (this.rotationYaw + deltaYaw / this.turnProgress);
				this.rotationPitch = (float) (this.rotationPitch + (this.minecartPitch - this.rotationPitch) / this.turnProgress);
				--this.turnProgress;
				setPosition(interpX, interpY, interpZ);
				setRotation(this.rotationYaw, this.rotationPitch);
			} else {
				setPosition(this.posX, this.posY, this.posZ);
				setRotation(this.rotationYaw, this.rotationPitch);
			}
		} else {
			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;
			this.motionY -= 0.04D;
			int railX = MathHelper.floor_double(this.posX);
			int railY = MathHelper.floor_double(this.posY);
			int railZ = MathHelper.floor_double(this.posZ);

			if(BlockRailBase.func_150049_b_(this.worldObj, railX, railY - 1, railZ) /* b instanceof BlockRail */) {
				--railY; //if the block below the current one is a rail use that one. used for downward slopes i'd imagine
			}

			double groundSpeedNoRail = 0.4D;
			Block block = this.worldObj.getBlock(railX, railY, railZ);

			if(canUseRail() && BlockRailBase.func_150051_a(block) /* b instanceof BlockRail */) {
				/*
				 * MAJOR SECION: THE CART IS ON A RAIL
				 */
				float railMaxSpeed = 1F; //((BlockRailBase) block).getRailMaxSpeed(worldObj, this, l, i, i1);
				double maxSpeed = Math.min(railMaxSpeed, getCurrentCartSpeedCapOnRail());
				useRail(railX, railY, railZ, maxSpeed, getSlopeAdjustment(), block, this.worldObj.getBlockMetadata(railX, railY, railZ));

				if(block == Blocks.activator_rail) {
					onActivatorRailPass(railX, railY, railZ, (this.worldObj.getBlockMetadata(railX, railY, railZ) & 8) != 0);
				}
			} else {
				limitSpeedApplyDragAndMoveWithoutRail(this.onGround ? groundSpeedNoRail : getMaxSpeedAirLateral());
			}

			func_145775_I(); //collide with blocks (Entity.class)
			this.rotationPitch = 0.0F;
			double d8 = this.prevPosX - this.posX;
			double d4 = this.prevPosZ - this.posZ;

			if(d8 * d8 + d4 * d4 > 0.001D) {
				this.rotationYaw = (float) (Math.atan2(d4, d8) * 180.0D / Math.PI);

				if(this.isInReverse) {
					this.rotationYaw += 180.0F;
				}
			}

			double deltaYaw = MathHelper.wrapAngleTo180_float(this.rotationYaw - this.prevRotationYaw);

			if(deltaYaw < -170.0D || deltaYaw >= 170.0D) {
				this.rotationYaw += 180.0F;
				this.isInReverse = !this.isInReverse;
			}

			setRotation(this.rotationYaw, this.rotationPitch);

			AxisAlignedBB box = this.boundingBox.expand(0.2D, 0.0D, 0.2D);

			List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, box);

			if(list != null && !list.isEmpty()) {
				for (Object element : list) {
					Entity entity = (Entity) element;

					if(entity != this.riddenByEntity && entity.canBePushed() && entity instanceof EntityMinecart) {
						entity.applyEntityCollision(this);
					}
				}
			}

			if(this.riddenByEntity != null && this.riddenByEntity.isDead) {
				if(this.riddenByEntity.ridingEntity == this) {
					this.riddenByEntity.ridingEntity = null;
				}

				this.riddenByEntity = null;
			}

			//MinecraftForge.EVENT_BUS.post(new MinecartUpdateEvent(this, l, i, i1));
		}
	}

	/**
	 * Called every tick the minecart is on an activator rail. Args: x, y, z, is
	 * the rail receiving power
	 */
	public void onActivatorRailPass(int x, int y, int z, boolean powered) { }

	protected void limitSpeedApplyDragAndMoveWithoutRail(double maxSpeed) {
		if(this.motionX < -maxSpeed) {
			this.motionX = -maxSpeed;
		}

		if(this.motionX > maxSpeed) {
			this.motionX = maxSpeed;
		}

		if(this.motionZ < -maxSpeed) {
			this.motionZ = -maxSpeed;
		}

		if(this.motionZ > maxSpeed) {
			this.motionZ = maxSpeed;
		}

		double moveY = this.motionY;
		if(getMaxSpeedAirVertical() > 0 && this.motionY > getMaxSpeedAirVertical()) {
			moveY = getMaxSpeedAirVertical();
			if(Math.abs(this.motionX) < 0.3f && Math.abs(this.motionZ) < 0.3f) {
				moveY = 0.15f;
				this.motionY = moveY;
			}
		}

		if(this.onGround) {
			this.motionX *= 0.5D;
			this.motionY *= 0.5D;
			this.motionZ *= 0.5D;
		}

		moveEntity(this.motionX, moveY, this.motionZ);

		if(!this.onGround) {
			this.motionX *= getDragAir();
			this.motionY *= getDragAir();
			this.motionZ *= getDragAir();
		}
	}

	protected void useRail(int railX, int railY, int railZ, double maxSpeed, double slopeAdjustment, Block rail, int meta) {
		this.fallDistance = 0.0F;
		Vec3 vec3 = getClosestPositionOnRail(this.posX, this.posY, this.posZ);
		this.posY = railY;
		boolean flag = false;
		boolean flag1 = false;

		if(rail == Blocks.golden_rail) {
			flag = (this.worldObj.getBlockMetadata(railX, railY, railZ) & 8) != 0;
			flag1 = !flag;
		}

		if(((BlockRailBase) rail).isPowered()) {
			meta &= 7;
		}

		if(meta >= 2 && meta <= 5) {
			this.posY = railY + 1;
		}

		if(meta == 2) {
			this.motionX -= slopeAdjustment;
		}

		if(meta == 3) {
			this.motionX += slopeAdjustment;
		}

		if(meta == 4) {
			this.motionZ += slopeAdjustment;
		}

		if(meta == 5) {
			this.motionZ -= slopeAdjustment;
		}

		int[][] curveData = EntityMinecartDeobf.matrix[meta];
		double sideDeltaX = curveData[1][0] - curveData[0][0];
		double sideDeltaZ = curveData[1][2] - curveData[0][2];
		double sideDelta = Math.sqrt(sideDeltaX * sideDeltaX + sideDeltaZ * sideDeltaZ);
		double d5 = this.motionX * sideDeltaX + this.motionZ * sideDeltaZ;

		if(d5 < 0.0D) {
			sideDeltaX = -sideDeltaX;
			sideDeltaZ = -sideDeltaZ;
		}

		double d6 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

		if(d6 > 2.0D) {
			d6 = 2.0D;
		}

		this.motionX = d6 * sideDeltaX / sideDelta;
		this.motionZ = d6 * sideDeltaZ / sideDelta;
		double motion;
		double d8;
		double d9;
		double d10;

		if(this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase) {
			motion = ((EntityLivingBase) this.riddenByEntity).moveForward;

			if(motion > 0.0D) {
				d8 = -Math.sin(this.riddenByEntity.rotationYaw * (float) Math.PI / 180.0F);
				d9 = Math.cos(this.riddenByEntity.rotationYaw * (float) Math.PI / 180.0F);
				d10 = this.motionX * this.motionX + this.motionZ * this.motionZ;

				if(d10 < 0.01D) {
					this.motionX += d8 * 0.1D;
					this.motionZ += d9 * 0.1D;
					flag1 = false;
				}
			}
		}

		if(flag1 && shouldDoRailFunctions()) {
			motion = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

			if(motion < 0.03D) {
				this.motionX *= 0.0D;
				this.motionY *= 0.0D;
				this.motionZ *= 0.0D;
			} else {
				this.motionX *= 0.5D;
				this.motionY *= 0.0D;
				this.motionZ *= 0.5D;
			}
		}

		motion = 0.0D;
		d8 = railX + 0.5D + curveData[0][0] * 0.5D;
		d9 = railZ + 0.5D + curveData[0][2] * 0.5D;
		d10 = railX + 0.5D + curveData[1][0] * 0.5D;
		double d11 = railZ + 0.5D + curveData[1][2] * 0.5D;
		sideDeltaX = d10 - d8;
		sideDeltaZ = d11 - d9;
		double d12;
		double d13;

		if(sideDeltaX == 0.0D) {
			this.posX = railX + 0.5D;
			motion = this.posZ - railZ;
		} else if(sideDeltaZ == 0.0D) {
			this.posZ = railZ + 0.5D;
			motion = this.posX - railX;
		} else {
			d12 = this.posX - d8;
			d13 = this.posZ - d9;
			motion = (d12 * sideDeltaX + d13 * sideDeltaZ) * 2.0D;
		}

		this.posX = d8 + sideDeltaX * motion;
		this.posZ = d9 + sideDeltaZ * motion;
		setPosition(this.posX, this.posY + this.yOffset, this.posZ);

		moveMinecartOnRail(railX, railY, railZ, maxSpeed);

		if(curveData[0][1] != 0 && MathHelper.floor_double(this.posX) - railX == curveData[0][0] && MathHelper.floor_double(this.posZ) - railZ == curveData[0][2]) {
			setPosition(this.posX, this.posY + curveData[0][1], this.posZ);
		} else if(curveData[1][1] != 0 && MathHelper.floor_double(this.posX) - railX == curveData[1][0] && MathHelper.floor_double(this.posZ) - railZ == curveData[1][2]) {
			setPosition(this.posX, this.posY + curveData[1][1], this.posZ);
		}

		applyDrag();
		Vec3 vec31 = getClosestPositionOnRail(this.posX, this.posY, this.posZ);

		if(vec31 != null && vec3 != null) {
			double d14 = (vec3.yCoord - vec31.yCoord) * 0.05D;
			d6 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

			if(d6 > 0.0D) {
				this.motionX = this.motionX / d6 * (d6 + d14);
				this.motionZ = this.motionZ / d6 * (d6 + d14);
			}

			setPosition(this.posX, vec31.yCoord, this.posZ);
		}

		int j1 = MathHelper.floor_double(this.posX);
		int i1 = MathHelper.floor_double(this.posZ);

		if(j1 != railX || i1 != railZ) {
			d6 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.motionX = d6 * (j1 - railX);
			this.motionZ = d6 * (i1 - railZ);
		}

		if(shouldDoRailFunctions()) {
			//((BlockRailBase) p_145821_8_).onMinecartPass(worldObj, this, p_145821_1_, p_145821_2_, p_145821_3_);
		}

		if(flag && shouldDoRailFunctions()) {
			double d15 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

			if(d15 > 0.01D) {
				double d16 = 0.06D;
				this.motionX += this.motionX / d15 * d16;
				this.motionZ += this.motionZ / d15 * d16;
			} else if(meta == 1) {
				if(this.worldObj.getBlock(railX - 1, railY, railZ).isNormalCube()) {
					this.motionX = 0.02D;
				} else if(this.worldObj.getBlock(railX + 1, railY, railZ).isNormalCube()) {
					this.motionX = -0.02D;
				}
			} else if(meta == 0) {
				if(this.worldObj.getBlock(railX, railY, railZ - 1).isNormalCube()) {
					this.motionZ = 0.02D;
				} else if(this.worldObj.getBlock(railX, railY, railZ + 1).isNormalCube()) {
					this.motionZ = -0.02D;
				}
			}
		}
	}

	protected void applyDrag() {
		if(this.riddenByEntity != null) {
			this.motionX *= 0.997D;
			this.motionY *= 0.0D;
			this.motionZ *= 0.997D;
		} else {
			this.motionX *= 0.96D;
			this.motionY *= 0.0D;
			this.motionZ *= 0.96D;
		}
	}

	@SideOnly(Side.CLIENT)
	public Vec3 getPositionVectorForRendering(double interpX, double interpY, double interpZ, double constant /* either 0.3 or -0.3 */) {
		int x = MathHelper.floor_double(interpX);
		int y = MathHelper.floor_double(interpY);
		int z  = MathHelper.floor_double(interpZ);

		if(BlockRailBase.func_150049_b_(this.worldObj, x, y - 1, z)) {
			--y;
		}

		Block block = this.worldObj.getBlock(x, y, z);

		if(!BlockRailBase.func_150051_a(block)) {
			return null;
		} else {
			int meta = this.worldObj.getBlockMetadata(x, y, z);

			interpY = y;

			if(meta >= 2 && meta <= 5) {
				interpY = y + 1;
			}

			int[][] curveData = EntityMinecartDeobf.matrix[meta];
			double curveX = curveData[1][0] - curveData[0][0];
			double curveZ = curveData[1][2] - curveData[0][2];
			double curveSq = Math.sqrt(curveX * curveX + curveZ * curveZ);
			curveX /= curveSq;
			curveZ /= curveSq;
			interpX += curveX * constant;
			interpZ += curveZ * constant;

			if(curveData[0][1] != 0 && MathHelper.floor_double(interpX) - x == curveData[0][0] && MathHelper.floor_double(interpZ) - z == curveData[0][2]) {
				interpY += curveData[0][1];
			} else if(curveData[1][1] != 0 && MathHelper.floor_double(interpX) - x == curveData[1][0] && MathHelper.floor_double(interpZ) - z == curveData[1][2]) {
				interpY += curveData[1][1];
			}

			return getClosestPositionOnRail(interpX, interpY, interpZ);
		}
	}

	public Vec3 getClosestPositionOnRail(double x, double y, double z) {
		int railX = MathHelper.floor_double(x);
		int railY = MathHelper.floor_double(y);
		int railZ = MathHelper.floor_double(z);

		if(BlockRailBase.func_150049_b_(this.worldObj, railX, railY - 1, railZ)) {
			--railY;
		}

		Block block = this.worldObj.getBlock(railX, railY, railZ);

		if(BlockRailBase.func_150051_a(block)) {
			int l = this.worldObj.getBlockMetadata(railX, railY, railZ);
			y = railY;

			if(l >= 2 && l <= 5) {
				y = railY + 1;
			}

			int[][] aint = EntityMinecartDeobf.matrix[l];
			double delta = 0.0D;
			double side1X = railX + 0.5D + aint[0][0] * 0.5D;
			double side1Y = railY + 0.5D + aint[0][1] * 0.5D;
			double side1Z = railZ + 0.5D + aint[0][2] * 0.5D;
			double side2X = railX + 0.5D + aint[1][0] * 0.5D;
			double side2Y = railY + 0.5D + aint[1][1] * 0.5D;
			double side2Z = railZ + 0.5D + aint[1][2] * 0.5D;
			double sideDeltaX = side2X - side1X;
			double sideDeltaYx2 = (side2Y - side1Y) * 2.0D;
			double sideDeltaZ = side2Z - side1Z;

			if(sideDeltaX == 0.0D) { /* straight path along Z */
				x = railX + 0.5D;
				delta = z - railZ;
			} else if(sideDeltaZ == 0.0D) { /* straight path along X */
				z = railZ + 0.5D;
				delta = x - railX;
			} else {
				double deltaX = x - side1X;
				double deltaZ = z - side1Z;
				delta = (deltaX * sideDeltaX + deltaZ * sideDeltaZ) * 2.0D;
			}

			x = side1X + sideDeltaX * delta;
			y = side1Y + sideDeltaYx2 * delta;
			z = side1Z + sideDeltaZ * delta;

			if(sideDeltaYx2 < 0.0D) {
				++y;
			}

			if(sideDeltaYx2 > 0.0D) {
				y += 0.5D;
			}

			return Vec3.createVectorHelper(x, y, z);
		} else {
			return null;
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		if(p_70037_1_.getBoolean("CustomDisplayTile")) {
			func_145819_k(p_70037_1_.getInteger("DisplayTile"));
			setDisplayTileData(p_70037_1_.getInteger("DisplayData"));
			setDisplayTileOffset(p_70037_1_.getInteger("DisplayOffset"));
		}

		if(p_70037_1_.hasKey("CustomName", 8) && p_70037_1_.getString("CustomName").length() > 0) {
			this.entityName = p_70037_1_.getString("CustomName");
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		if(hasDisplayTile()) {
			p_70014_1_.setBoolean("CustomDisplayTile", true);
			p_70014_1_.setInteger("DisplayTile", func_145820_n().getMaterial() == Material.air ? 0 : Block.getIdFromBlock(func_145820_n()));
			p_70014_1_.setInteger("DisplayData", getDisplayTileData());
			p_70014_1_.setInteger("DisplayOffset", getDisplayTileOffset());
		}

		if(this.entityName != null && this.entityName.length() > 0) {
			p_70014_1_.setString("CustomName", this.entityName);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 0.0F;
	}

	/**
	 * Applies a velocity to each of the entities pushing them away from each
	 * other. Args: entity
	 */
	@Override
	public void applyEntityCollision(Entity entity) {
		//MinecraftForge.EVENT_BUS.post(new MinecartCollisionEvent(this, p_70108_1_));
		if(!this.worldObj.isRemote) {
			if(entity != this.riddenByEntity) {
				if(entity instanceof EntityLivingBase && !(entity instanceof EntityPlayer) && !(entity instanceof EntityIronGolem) && canBeRidden()
						&& this.motionX * this.motionX + this.motionZ * this.motionZ > 0.01D && this.riddenByEntity == null && entity.ridingEntity == null) {
					entity.mountEntity(this);
				}

				double deltaX = entity.posX - this.posX;
				double deltaZ = entity.posZ - this.posZ;
				double delta = deltaX * deltaX + deltaZ * deltaZ;

				if(delta >= 10E-5D) {
					
					delta = MathHelper.sqrt_double(delta);
					deltaX /= delta;
					deltaZ /= delta;
					double pushForce = 1.0D / delta;

					if(pushForce > 1.0D) {
						pushForce = 1.0D;
					}

					deltaX *= pushForce;
					deltaZ *= pushForce;
					deltaX *= 0.1D;
					deltaZ *= 0.1D;
					deltaX *= 1.0F - this.entityCollisionReduction;
					deltaZ *= 1.0F - this.entityCollisionReduction;
					deltaX *= 0.5D;
					deltaZ *= 0.5D;

					if(entity instanceof EntityMinecart) {
						double d4 = entity.posX - this.posX;
						double d5 = entity.posZ - this.posZ;
						Vec3 vec3 = Vec3.createVectorHelper(d4, 0.0D, d5).normalize();
						Vec3 vec31 = Vec3
								.createVectorHelper(MathHelper.cos(this.rotationYaw * (float) Math.PI / 180.0F), 0.0D, MathHelper.sin(this.rotationYaw * (float) Math.PI / 180.0F))
								.normalize();
						double d6 = Math.abs(vec3.dotProduct(vec31));

						if(d6 < 0.8D) {
							return;
						}

						double totalMotionX = entity.motionX + this.motionX;
						double totalMotionZ = entity.motionZ + this.motionZ;

						//pusher is powered, this is unpowered
						if(((EntityMinecart) entity).isPoweredCart() && !isPoweredCart()) {
							this.motionX *= 0.2D;
							this.motionZ *= 0.2D;
							addVelocity(entity.motionX - deltaX, 0.0D, entity.motionZ - deltaZ);
							entity.motionX *= 0.95D;
							entity.motionZ *= 0.95D;
						//the same condition again for some reason. might make sense if the conditions were swapped
						} else if(((EntityMinecart) entity).isPoweredCart() && !isPoweredCart()) {
							entity.motionX *= 0.2D;
							entity.motionZ *= 0.2D;
							entity.addVelocity(this.motionX + deltaX, 0.0D, this.motionZ + deltaZ);
							this.motionX *= 0.95D;
							this.motionZ *= 0.95D;
						} else {
							totalMotionX /= 2.0D;
							totalMotionZ /= 2.0D;
							this.motionX *= 0.2D;
							this.motionZ *= 0.2D;
							addVelocity(totalMotionX - deltaX, 0.0D, totalMotionZ - deltaZ);
							entity.motionX *= 0.2D;
							entity.motionZ *= 0.2D;
							entity.addVelocity(totalMotionX + deltaX, 0.0D, totalMotionZ + deltaZ);
						}
					} else {
						addVelocity(-deltaX, 0.0D, -deltaZ);
						entity.addVelocity(deltaX / 4.0D, 0.0D, deltaZ / 4.0D);
					}
				}
			}
		}
	}

	/**
	 * Sets the position and rotation. Only difference from the other one is no
	 * bounding on the rotation. Args: posX, posY, posZ, yaw, pitch
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int theNumberThree) {
		this.syncPosX = x;
		this.syncPosY = y;
		this.syncPosZ = z;
		this.minecartYaw = yaw;
		this.minecartPitch = pitch;
		/* According to EntityLivingBase.class: The number of updates over which the new position and rotation are to be applied to the entity.
		 * The packet always passes the */
		this.turnProgress = theNumberThree + 2;
		this.motionX = this.velocityX;
		this.motionY = this.velocityY;
		this.motionZ = this.velocityZ;
	}

	/**
	 * Sets the current amount of damage the minecart has taken. Decreases over
	 * time. The cart breaks when this is over 40.
	 */
	public void setDamage(float p_70492_1_) {
		this.dataWatcher.updateObject(19, Float.valueOf(p_70492_1_));
	}

	/**
	 * Sets the velocity to the args. Args: x, y, z
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void setVelocity(double p_70016_1_, double p_70016_3_, double p_70016_5_) {
		this.velocityX = this.motionX = p_70016_1_;
		this.velocityY = this.motionY = p_70016_3_;
		this.velocityZ = this.motionZ = p_70016_5_;
	}

	/**
	 * Gets the current amount of damage the minecart has taken. Decreases over
	 * time. The cart breaks when this is over 40.
	 */
	public float getDamage() {
		return this.dataWatcher.getWatchableObjectFloat(19);
	}

	/**
	 * Sets the rolling amplitude the cart rolls while being attacked.
	 */
	public void setRollingAmplitude(int p_70497_1_) {
		this.dataWatcher.updateObject(17, Integer.valueOf(p_70497_1_));
	}

	/**
	 * Gets the rolling amplitude the cart rolls while being attacked.
	 */
	public int getRollingAmplitude() {
		return this.dataWatcher.getWatchableObjectInt(17);
	}

	/**
	 * Sets the rolling direction the cart rolls while being attacked. Can be 1
	 * or -1.
	 */
	public void setRollingDirection(int p_70494_1_) {
		this.dataWatcher.updateObject(18, Integer.valueOf(p_70494_1_));
	}

	/**
	 * Gets the rolling direction the cart rolls while being attacked. Can be 1
	 * or -1.
	 */
	public int getRollingDirection() {
		return this.dataWatcher.getWatchableObjectInt(18);
	}

	public abstract int getMinecartType();

	public Block func_145820_n() {
		if(!hasDisplayTile()) {
			return func_145817_o();
		} else {
			int i = getDataWatcher().getWatchableObjectInt(20) & 65535;
			return Block.getBlockById(i);
		}
	}

	public Block func_145817_o() {
		return Blocks.air;
	}

	public int getDisplayTileData() {
		return !hasDisplayTile() ? getDefaultDisplayTileData() : getDataWatcher().getWatchableObjectInt(20) >> 16;
	}

	public int getDefaultDisplayTileData() {
		return 0;
	}

	public int getDisplayTileOffset() {
		return !hasDisplayTile() ? getDefaultDisplayTileOffset() : getDataWatcher().getWatchableObjectInt(21);
	}

	public int getDefaultDisplayTileOffset() {
		return 6;
	}

	public void func_145819_k(int p_145819_1_) {
		getDataWatcher().updateObject(20, Integer.valueOf(p_145819_1_ & 65535 | getDisplayTileData() << 16));
		setHasDisplayTile(true);
	}

	public void setDisplayTileData(int p_94092_1_) {
		getDataWatcher().updateObject(20, Integer.valueOf(Block.getIdFromBlock(func_145820_n()) & 65535 | p_94092_1_ << 16));
		setHasDisplayTile(true);
	}

	public void setDisplayTileOffset(int p_94086_1_) {
		getDataWatcher().updateObject(21, Integer.valueOf(p_94086_1_));
		setHasDisplayTile(true);
	}

	public boolean hasDisplayTile() {
		return getDataWatcher().getWatchableObjectByte(22) == 1;
	}

	public void setHasDisplayTile(boolean p_94096_1_) {
		getDataWatcher().updateObject(22, Byte.valueOf((byte) (p_94096_1_ ? 1 : 0)));
	}

	/**
	 * Sets the minecart's name.
	 */
	public void setMinecartName(String p_96094_1_) {
		this.entityName = p_96094_1_;
	}

	/**
	 * Gets the name of this command sender (usually username, but possibly
	 * "Rcon")
	 */
	@Override
	public String getCommandSenderName() {
		return this.entityName != null ? this.entityName : super.getCommandSenderName();
	}

	/**
	 * Returns if the inventory is named
	 */
	public boolean hasCustomInventoryName() {
		return this.entityName != null;
	}

	public String func_95999_t() {
		return this.entityName;
	}

	/*
	 * =================================== FORGE START
	 * ===========================================
	 */
	/**
	 * Moved to allow overrides. This code handles minecart movement and speed
	 * capping when on a rail.
	 */
	public void moveMinecartOnRail(int x, int y, int z, double maxSpeed) {
		double motionX = this.motionX;
		double motionZ = this.motionZ;

		if(this.riddenByEntity != null) {
			motionX *= 0.75D;
			motionZ *= 0.75D;
		}

		if(motionX < -maxSpeed) {
			motionX = -maxSpeed;
		}

		if(motionX > maxSpeed) {
			motionX = maxSpeed;
		}

		if(motionZ < -maxSpeed) {
			motionZ = -maxSpeed;
		}

		if(motionZ > maxSpeed) {
			motionZ = maxSpeed;
		}

		moveEntity(motionX, 0.0D, motionZ);
	}

	/**
	 * Gets the current global Minecart Collision handler if none is registered,
	 * returns null
	 * 
	 * @return The collision handler or null
	 */
	public static IMinecartCollisionHandler getCollisionHandler() {
		return EntityMinecartDeobf.collisionHandler;
	}

	/**
	 * Sets the global Minecart Collision handler, overwrites any that is
	 * currently set.
	 * 
	 * @param handler
	 *            The new handler
	 */
	public static void setCollisionHandler(IMinecartCollisionHandler handler) {
		EntityMinecartDeobf.collisionHandler = handler;
	}

	/**
	 * This function returns an ItemStack that represents this cart. This should
	 * be an ItemStack that can be used by the player to place the cart, but is
	 * not necessary the item the cart drops when destroyed.
	 * 
	 * @return An ItemStack that can be used to place the cart.
	 */
	public ItemStack getCartItem() {
		return new ItemStack(Items.minecart);
	}

	/**
	 * Returns true if this cart can currently use rails. This function is
	 * mainly used to gracefully detach a minecart from a rail.
	 * 
	 * @return True if the minecart can use rails.
	 */
	public boolean canUseRail() {
		return this.canUseRail;
	}

	/**
	 * Set whether the minecart can use rails. This function is mainly used to
	 * gracefully detach a minecart from a rail.
	 * 
	 * @param use
	 *            Whether the minecart can currently use rails.
	 */
	public void setCanUseRail(boolean use) {
		this.canUseRail = use;
	}

	/**
	 * Return false if this cart should not call onMinecartPass() and should
	 * ignore Powered Rails.
	 * 
	 * @return True if this cart should call onMinecartPass().
	 */
	public boolean shouldDoRailFunctions() {
		return true;
	}

	/**
	 * Returns true if this cart is self propelled.
	 * 
	 * @return True if powered.
	 */
	public boolean isPoweredCart() {
		return getMinecartType() == 2;
	}

	/**
	 * Returns true if this cart can be ridden by an Entity.
	 * 
	 * @return True if this cart can be ridden.
	 */
	public boolean canBeRidden() {
		return false;
	}

	public final float getCurrentCartSpeedCapOnRail() {
		return this.currentSpeedRail;
	}

	public final void setCurrentCartSpeedCapOnRail(float value) {
		value = Math.min(value, getMaxCartSpeedOnRail());
		this.currentSpeedRail = value;
	}

	/**
	 * Getters/setters for physics variables
	 */

	/**
	 * Returns the carts max speed when traveling on rails. Carts going faster
	 * than 1.1 cause issues with chunk loading. Carts cant traverse slopes or
	 * corners at greater than 0.5 - 0.6. This value is compared with the rails
	 * max speed and the carts current speed cap to determine the carts current
	 * max speed. A normal rail's max speed is 0.4.
	 *
	 * @return Carts max speed.
	 */
	public float getMaxCartSpeedOnRail() {
		return 1.2f;
	}

	public float getMaxSpeedAirLateral() {
		return this.maxSpeedAirLateral;
	}

	public void setMaxSpeedAirLateral(float value) {
		this.maxSpeedAirLateral = value;
	}

	public float getMaxSpeedAirVertical() {
		return this.maxSpeedAirVertical;
	}

	public void setMaxSpeedAirVertical(float value) {
		this.maxSpeedAirVertical = value;
	}

	public double getDragAir() {
		return this.dragAir;
	}

	public void setDragAir(double value) {
		this.dragAir = value;
	}

	public double getSlopeAdjustment() {
		return 0.0078125D;
	}
	/*
	 * =================================== FORGE END
	 * ===========================================
	 */
}
