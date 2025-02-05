package com.hbm.entity.effect;

import java.util.ArrayList;

import com.hbm.util.TrackerUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/*
 * Toroidial Convection Simulation Explosion Effect
 * Tor                             Ex
 */
public class EntityNukeTorex extends Entity {

	public double coreHeight = 3;
	public double convectionHeight = 3;
	public double torusWidth = 3;
	public double rollerSize = 1;
	public double heat = 1;
	public double lastSpawnY = - 1;
	public ArrayList<Cloudlet> cloudlets = new ArrayList<>();
	//public static int cloudletLife = 200;

	public EntityNukeTorex(World world) {
		super(world);
		this.ignoreFrustumCheck = true;
		setSize(1F, 50F);
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(10, 1.0F);
		this.dataWatcher.addObject(11, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float p_70070_1_) {
		return 15728880;
	}

	@Override
	public float getBrightness(float p_70013_1_) {
		return 1.0F;
	}

	@Override
	public void onUpdate() {
		
		double s = getScale();
		double cs = 1.5;
		int maxAge = getMaxAge();
		
		if(this.worldObj.isRemote) {
			
			if(this.lastSpawnY == -1) {
				this.lastSpawnY = this.posY - 3;
			}
			
			int spawnTarget = Math.max(this.worldObj.getHeightValue((int) Math.floor(this.posX), (int) Math.floor(this.posZ)) - 3, 1);
			double moveSpeed = 0.5D;
			
			if(Math.abs(spawnTarget - this.lastSpawnY) < moveSpeed) {
				this.lastSpawnY = spawnTarget;
			} else {
				this.lastSpawnY += moveSpeed * Math.signum(spawnTarget - this.lastSpawnY);
			}
			
			// spawn mush clouds
			double range = (this.torusWidth - this.rollerSize) * 0.25;
			double simSpeed = getSimulationSpeed();
			int toSpawn = (int) Math.ceil(10 * simSpeed * simSpeed);
			int lifetime = Math.min((this.ticksExisted * this.ticksExisted) + 200, maxAge - this.ticksExisted + 200);
				
			for(int i = 0; i < toSpawn; i++) {
				double x = this.posX + this.rand.nextGaussian() * range;
				double z = this.posZ + this.rand.nextGaussian() * range;
				Cloudlet cloud = new Cloudlet(x, this.lastSpawnY, z, (float)(this.rand.nextDouble() * 2D * Math.PI), 0, lifetime);
				cloud.setScale(1F + this.ticksExisted * 0.005F * (float) cs, 5F * (float) cs);
				this.cloudlets.add(cloud);
			}
			
			// spawn shock clouds
			if(this.ticksExisted < 100) {
				
				int cloudCount = this.ticksExisted * 5;
				int shockLife = Math.max(300 - this.ticksExisted * 20, 50);
				
				for(int i = 0; i < cloudCount; i++) {
					Vec3 vec = Vec3.createVectorHelper((this.ticksExisted * 2 + this.rand.nextDouble()) * 2, 0, 0);
					float rot = (float) (Math.PI * 2 * this.rand.nextDouble());
					vec.rotateAroundY(rot);
					this.cloudlets.add(new Cloudlet(vec.xCoord + this.posX, this.worldObj.getHeightValue((int) (vec.xCoord + this.posX) + 1, (int) (vec.zCoord + this.posZ)), vec.zCoord + this.posZ, rot, 0, shockLife)
							.setScale(5F, 2F)
							.setMotion(0));
				}
			}
			
			// spawn ring clouds
			if(this.ticksExisted < 200) {
				for(int i = 0; i < 2; i++) {
					Cloudlet cloud = new Cloudlet(this.posX, this.posY + this.coreHeight, this.posZ, (float)(this.rand.nextDouble() * 2D * Math.PI), 0, lifetime, TorexType.RING);
					cloud.setScale(1F + this.ticksExisted * 0.005F * (float) cs * 0.5F, 3F * (float) (cs * s));
					this.cloudlets.add(cloud);
				}
			}
			
			for(Cloudlet cloud : this.cloudlets) {
				cloud.update();
			}
			this.coreHeight += 0.15/* * s*/;
			this.torusWidth += 0.05/* * s*/;
			this.rollerSize = this.torusWidth * 0.35;
			this.convectionHeight = this.coreHeight + this.rollerSize;
			
			int maxHeat = (int) (50 * s);
			this.heat = maxHeat - Math.pow((maxHeat * this.ticksExisted) / maxAge, 1);
			
			this.cloudlets.removeIf(x -> x.isDead);
		}
		
		if(!this.worldObj.isRemote && this.ticksExisted > maxAge) {
			setDead();
		}
	}
	
	public EntityNukeTorex setScale(float scale) {
		getDataWatcher().updateObject(10, scale);
		this.coreHeight = this.coreHeight / 1.5D * scale;
		this.convectionHeight = this.convectionHeight / 1.5D * scale;
		this.torusWidth = this.torusWidth / 1.5D * scale;
		this.rollerSize = this.rollerSize / 1.5D * scale;
		return this;
	}
	
	public EntityNukeTorex setType(int type) {
		this.dataWatcher.updateObject(11, type);
		return this;
	}
	
	public double getSimulationSpeed() {
		
		int lifetime = getMaxAge();
		int simSlow = lifetime / 4;
		int simStop = lifetime / 2;
		int life = EntityNukeTorex.this.ticksExisted;
		
		if(life > simStop) {
			return 0D;
		}
		
		if(life > simSlow) {
			return 1D - ((double)(life - simSlow) / (double)(simStop - simSlow));
		}
		
		return 1.0D;
	}
	
	public double getScale() {
		return this.dataWatcher.getWatchableObjectFloat(10);
	}
	
	public double getSaturation() {
		double d = (double) this.ticksExisted / (double) getMaxAge();
		return 1D - (d * d * d * d);
	}
	
	public double getGreying() {
		
		int lifetime = getMaxAge();
		int greying = lifetime * 3 / 4;
		
		if(this.ticksExisted > greying) {
			return 1 + ((double)(this.ticksExisted - greying) / (double)(lifetime - greying));
		}
		
		return 1D;
	}
	
	public float getAlpha() {
		
		int lifetime = getMaxAge();
		int fadeOut = lifetime * 3 / 4;
		int life = EntityNukeTorex.this.ticksExisted;
		
		if(life > fadeOut) {
			float fac = (float)(life - fadeOut) / (float)(lifetime - fadeOut);
			return 1F - fac;
		}
		
		return 1.0F;
	}
	
	public int getMaxAge() {
		double s = getScale();
		return (int) (45 * 20 * s);
	}

	public class Cloudlet {

		public double posX;
		public double posY;
		public double posZ;
		public double prevPosX;
		public double prevPosY;
		public double prevPosZ;
		public double motionX;
		public double motionY;
		public double motionZ;
		public int age;
		public int cloudletLife;
		public float angle;
		public boolean isDead = false;
		float rangeMod = 1.0F;
		public float colorMod = 1.0F;
		public Vec3 color;
		public Vec3 prevColor;
		public TorexType type;

		public Cloudlet(double posX, double posY, double posZ, float angle, int age, int maxAge) {
			this(posX, posY, posZ, angle, age, maxAge, TorexType.STANDARD);
		}

		public Cloudlet(double posX, double posY, double posZ, float angle, int age, int maxAge, TorexType type) {
			this.posX = posX;
			this.posY = posY;
			this.posZ = posZ;
			this.age = age;
			this.cloudletLife = maxAge;
			this.angle = angle;
			this.rangeMod = 0.3F + EntityNukeTorex.this.rand.nextFloat() * 0.7F;
			this.colorMod = 0.8F + EntityNukeTorex.this.rand.nextFloat() * 0.2F;
			this.type = type;
			
			updateColor();
		}
		
		private void update() {
			
			this.age++;
			
			if(this.age > this.cloudletLife) {
				this.isDead = true;
			}

			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;
			
			Vec3 simPos = Vec3.createVectorHelper(EntityNukeTorex.this.posX - this.posX, 0, EntityNukeTorex.this.posZ - this.posZ);
			double simPosX = EntityNukeTorex.this.posX + simPos.lengthVector();
			double simPosZ = EntityNukeTorex.this.posZ + 0D;
			
			if(this.type == TorexType.STANDARD) {
				Vec3 convection = getConvectionMotion(simPosX, simPosZ);
				Vec3 lift = getLiftMotion(simPosX, simPosZ);
				
				double factor = MathHelper.clamp_double((this.posY - EntityNukeTorex.this.posY) / EntityNukeTorex.this.coreHeight, 0, 1);
				this.motionX = convection.xCoord * factor + lift.xCoord * (1D - factor);
				this.motionY = convection.yCoord * factor + lift.yCoord * (1D - factor);
				this.motionZ = convection.zCoord * factor + lift.zCoord * (1D - factor);
			} else if(this.type == TorexType.RING) {
				Vec3 motion = getRingMotion(simPosX, simPosZ);
				this.motionX = motion.xCoord;
				this.motionY = motion.yCoord;
				this.motionZ = motion.zCoord;
			}
			
			double mult = this.motionMult * getSimulationSpeed();
			
			this.posX += this.motionX * mult;
			this.posY += this.motionY * mult;
			this.posZ += this.motionZ * mult;
			
			updateColor();
		}
		
		private Vec3 getRingMotion(double simPosX, double simPosZ) {
			
			/*Vec3 targetPos = Vec3.createVectorHelper(
					(EntityNukeTorex.this.posX + torusWidth * 1),
					(EntityNukeTorex.this.posY + coreHeight * 0.5),
					EntityNukeTorex.this.posZ);
			
			Vec3 delta = Vec3.createVectorHelper(targetPos.xCoord - simPosX, targetPos.yCoord - this.posY, targetPos.zCoord - simPosZ);
			
			double speed = 0.125D;
			delta.xCoord *= speed;
			delta.yCoord *= speed;
			delta.zCoord *= speed;
			
			delta.rotateAroundY(this.angle);
			return delta;*/
			
			if(simPosX > EntityNukeTorex.this.posX + EntityNukeTorex.this.torusWidth * 2)
				return Vec3.createVectorHelper(0, 0, 0);
			
			/* the position of the torus' outer ring center */
			Vec3 torusPos = Vec3.createVectorHelper(
					(EntityNukeTorex.this.posX + EntityNukeTorex.this.torusWidth),
					(EntityNukeTorex.this.posY + EntityNukeTorex.this.coreHeight * 0.5),
					EntityNukeTorex.this.posZ);
			
			/* the difference between the cloudlet and the torus' ring center */
			Vec3 delta = Vec3.createVectorHelper(torusPos.xCoord - simPosX, torusPos.yCoord - this.posY, torusPos.zCoord - simPosZ);
			
			/* the distance this cloudlet wants to achieve to the torus' ring center */
			double roller = EntityNukeTorex.this.rollerSize * this.rangeMod * 0.25;
			/* the distance between this cloudlet and the torus' outer ring perimeter */
			double dist = delta.lengthVector() / roller - 1D;
			
			/* euler function based on how far the cloudlet is away from the perimeter */
			double func = 1D - Math.pow(Math.E, -dist); // [0;1]
			/* just an approximation, but it's good enough */
			float angle = (float) (func * Math.PI * 0.5D); // [0;90°]
			
			/* vector going from the ring center in the direction of the cloudlet, stopping at the perimeter */
			Vec3 rot = Vec3.createVectorHelper(-delta.xCoord / dist, -delta.yCoord / dist, -delta.zCoord / dist);
			/* rotate by the approximate angle */
			rot.rotateAroundZ(angle);
			
			/* the direction from the cloudlet to the target position on the perimeter */
			Vec3 motion = Vec3.createVectorHelper(
					torusPos.xCoord + rot.xCoord - simPosX,
					torusPos.yCoord + rot.yCoord - this.posY,
					torusPos.zCoord + rot.zCoord - simPosZ);
			
			double speed = 0.001D;
			motion.xCoord *= speed;
			motion.yCoord *= speed;
			motion.zCoord *= speed;
			
			motion = motion.normalize();
			motion.rotateAroundY(this.angle);
			
			return motion;
		}
		
		/* simulated on a 2D-plane along the X/Y axis */
		private Vec3 getConvectionMotion(double simPosX, double simPosZ) {
			
			if(simPosX > EntityNukeTorex.this.posX + EntityNukeTorex.this.torusWidth * 2)
				return Vec3.createVectorHelper(0, 0, 0);
			
			/* the position of the torus' outer ring center */
			Vec3 torusPos = Vec3.createVectorHelper(
					(EntityNukeTorex.this.posX + EntityNukeTorex.this.torusWidth),
					(EntityNukeTorex.this.posY + EntityNukeTorex.this.coreHeight),
					EntityNukeTorex.this.posZ);
			
			/* the difference between the cloudlet and the torus' ring center */
			Vec3 delta = Vec3.createVectorHelper(torusPos.xCoord - simPosX, torusPos.yCoord - this.posY, torusPos.zCoord - simPosZ);
			
			/* the distance this cloudlet wants to achieve to the torus' ring center */
			double roller = EntityNukeTorex.this.rollerSize * this.rangeMod;
			/* the distance between this cloudlet and the torus' outer ring perimeter */
			double dist = delta.lengthVector() / roller - 1D;
			
			/* euler function based on how far the cloudlet is away from the perimeter */
			double func = 1D - Math.pow(Math.E, -dist); // [0;1]
			/* just an approximation, but it's good enough */
			float angle = (float) (func * Math.PI * 0.5D); // [0;90°]
			
			/* vector going from the ring center in the direction of the cloudlet, stopping at the perimeter */
			Vec3 rot = Vec3.createVectorHelper(-delta.xCoord / dist, -delta.yCoord / dist, -delta.zCoord / dist);
			/* rotate by the approximate angle */
			rot.rotateAroundZ(angle);
			
			/* the direction from the cloudlet to the target position on the perimeter */
			Vec3 motion = Vec3.createVectorHelper(
					torusPos.xCoord + rot.xCoord - simPosX,
					torusPos.yCoord + rot.yCoord - this.posY,
					torusPos.zCoord + rot.zCoord - simPosZ);
			
			motion = motion.normalize();
			motion.rotateAroundY(this.angle);
			
			return motion;
		}
		
		private Vec3 getLiftMotion(double simPosX, double simPosZ) {
			double scale = MathHelper.clamp_double(1D - (simPosX - (EntityNukeTorex.this.posX + EntityNukeTorex.this.torusWidth)), 0, 1);
			
			Vec3 motion = Vec3.createVectorHelper(EntityNukeTorex.this.posX - this.posX, (EntityNukeTorex.this.posY + EntityNukeTorex.this.convectionHeight) - this.posY, EntityNukeTorex.this.posZ - this.posZ);
			
			motion = motion.normalize();
			motion.xCoord *= scale;
			motion.yCoord *= scale;
			motion.zCoord *= scale;
			
			return motion;
		}
		
		private void updateColor() {
			this.prevColor = this.color;

			double exX = EntityNukeTorex.this.posX;
			double exY = EntityNukeTorex.this.posY + EntityNukeTorex.this.coreHeight;
			double exZ = EntityNukeTorex.this.posZ;

			double distX = exX - this.posX;
			double distY = exY - this.posY;
			double distZ = exZ - this.posZ;
			
			double distSq = distX * distX + distY * distY + distZ * distZ;
			distSq /= EntityNukeTorex.this.heat;
			double dist = Math.sqrt(distSq);
			
			dist = Math.max(dist, 1);
			double col = 2D / dist;
			
			int type = EntityNukeTorex.this.dataWatcher.getWatchableObjectInt(11);
			
			if(type == 1) {
				this.color = Vec3.createVectorHelper(
						Math.max(col * 1, 0.25),
						Math.max(col * 2, 0.25),
						Math.max(col * 0.5, 0.25)
						);
			} else {
				this.color = Vec3.createVectorHelper(
						Math.max(col * 2, 0.25),
						Math.max(col * 1.5, 0.25),
						Math.max(col * 0.5, 0.25)
						);
			}
		}
		
		public Vec3 getInterpPos(float interp) {
			return Vec3.createVectorHelper(
					this.prevPosX + (this.posX - this.prevPosX) * interp,
					this.prevPosY + (this.posY - this.prevPosY) * interp,
					this.prevPosZ + (this.posZ - this.prevPosZ) * interp);
		}
		
		public Vec3 getInterpColor(float interp) {
			double greying = getGreying();
			
			if(this.type == TorexType.RING) {
				greying += 1;
			}
			
			return Vec3.createVectorHelper(
					(this.prevColor.xCoord + (this.color.xCoord - this.prevColor.xCoord) * interp) * greying,
					(this.prevColor.yCoord + (this.color.yCoord - this.prevColor.yCoord) * interp) * greying,
					(this.prevColor.zCoord + (this.color.zCoord - this.prevColor.zCoord) * interp) * greying);
		}
		
		public float getAlpha() {
			return (1F - ((float)this.age / (float)this.cloudletLife)) * EntityNukeTorex.this.getAlpha();
		}
		
		private float startingScale = 1;
		private float growingScale = 5F;
		
		public float getScale() {
			return this.startingScale + ((float)this.age / (float)this.cloudletLife) * this.growingScale;
		}
		
		public Cloudlet setScale(float start, float grow) {
			this.startingScale = start;
			this.growingScale = grow;
			return this;
		}
		
		private double motionMult = 1F;
		
		public Cloudlet setMotion(double mult) {
			this.motionMult = mult;
			return this;
		}
	}
	
	public static enum TorexType {
		STANDARD,
		RING
	}

	@Override protected void writeEntityToNBT(NBTTagCompound nbt) { }
	@Override public boolean writeToNBTOptional(NBTTagCompound nbt) { return false; }
	@Override public void readEntityFromNBT(NBTTagCompound nbt) { setDead(); }
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return true;
	}
	
	public static void statFac(World world, double x, double y, double z, float scale) {
		EntityNukeTorex torex = new EntityNukeTorex(world).setScale(MathHelper.clamp_float(scale * 0.01F, 0.5F, 5F));
		torex.setPosition(x, y, z);
		world.spawnEntityInWorld(torex);
		TrackerUtil.setTrackingRange(world, torex, 1000);
	}
	
	public static void statFacBale(World world, double x, double y, double z, float scale) {
		EntityNukeTorex torex = new EntityNukeTorex(world).setScale(MathHelper.clamp_float(scale * 0.01F, 0.5F, 5F)).setType(1);
		torex.setPosition(x, y, z);
		world.spawnEntityInWorld(torex);
		TrackerUtil.setTrackingRange(world, torex, 1000);
	}
}
