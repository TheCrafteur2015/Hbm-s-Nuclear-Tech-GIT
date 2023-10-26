package com.hbm.entity.mob;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.world.feature.GlyphidHive;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityGlyphidScout extends EntityGlyphid {
	
	public boolean hasHome = false;
	public double homeX;
	public double homeY;
	public double homeZ;

	public EntityGlyphidScout(World world) {
		super(world);
		setSize(1.25F, 0.75F);
	}
	
	@Override
	public float getDamageThreshold() {
		return 0.0F;
	}

	@Override
	public ResourceLocation getSkin() {
		return ResourceManager.glyphid_scout_tex;
	}

	@Override
	public double getScale() {
		return 0.75D;
	}

	@Override
	public int getArmorBreakChance(float amount) {
		return 1;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1.5D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2D);
	}

	@Override
	protected boolean canDespawn() {
		return true;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(!this.worldObj.isRemote) {
			
			if(!this.hasHome) {
				this.homeX = this.posX;
				this.homeY = this.posY;
				this.homeZ = this.posZ;
				this.hasHome = true;
			}
			
			if(this.rand.nextInt(20) == 0) this.fleeingTick = 2;

			if(this.ticksExisted > 0 && this.ticksExisted % 1200 == 0 && Vec3.createVectorHelper(this.posX - this.homeX, this.posY - this.homeY, this.posZ - this.homeZ).lengthVector() > 8) {
				
				Block b = this.worldObj.getBlock((int) Math.floor(this.posX), (int) Math.floor(this.posY - 1), (int) Math.floor(this.posZ));
				
				int accuracy = 16;
				for(int i = 0; i < accuracy; i++) {
					float angle = (float) Math.toRadians(360D / accuracy * i);
					Vec3 rot = Vec3.createVectorHelper(0, 0, 16);
					rot.rotateAroundY(angle);
					Vec3 pos = Vec3.createVectorHelper(this.posX, this.posY + 1, this.posZ);
					Vec3 nextPos = Vec3.createVectorHelper(this.posX + rot.xCoord, this.posY + 1, this.posZ + rot.zCoord);
					MovingObjectPosition mop = this.worldObj.rayTraceBlocks(pos, nextPos);
					
					if(mop != null && mop.typeOfHit == MovingObjectType.BLOCK) {
						
						Block block = this.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
						
						if(block == ModBlocks.glyphid_base) {
							return;
						}
					}
				}
				
				if(b.getMaterial() != Material.air && b.isNormalCube() && b != ModBlocks.glyphid_base) {
					setDead();
					this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 5F, false, false);
					GlyphidHive.generate(this.worldObj, (int) Math.floor(this.posX), (int) Math.floor(this.posY), (int) Math.floor(this.posZ), this.rand);
				}
			}
		}
	}

	@Override
	protected void updateWanderPath() {
		this.worldObj.theProfiler.startSection("stroll");
		boolean flag = false;
		int pathX = -1;
		int pathY = -1;
		int pathZ = -1;
		float maxWeight = -99999.0F;

		for(int l = 0; l < 5; ++l) {
			int x = MathHelper.floor_double(this.posX + (double) this.rand.nextInt(25) - 12.0D);
			int y = MathHelper.floor_double(this.posY + (double) this.rand.nextInt(11) - 5.0D);
			int z = MathHelper.floor_double(this.posZ + (double) this.rand.nextInt(25) - 12.0D);
			float weight = getBlockPathWeight(x, y, z);

			if(weight > maxWeight) {
				maxWeight = weight;
				pathX = x;
				pathY = y;
				pathZ = z;
				flag = true;
			}
		}

		if(flag) {
			setPathToEntity(this.worldObj.getEntityPathToXYZ(this, pathX, pathY, pathZ, 10.0F, true, false, false, true));
		}

		this.worldObj.theProfiler.endSection();
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setBoolean("hasHome", this.hasHome);
		nbt.setDouble("homeX", this.homeX);
		nbt.setDouble("homeY", this.homeY);
		nbt.setDouble("homeZ", this.homeZ);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.hasHome = nbt.getBoolean("hasHome");
		this.homeX = nbt.getDouble("homeX");
		this.homeY = nbt.getDouble("homeY");
		this.homeZ = nbt.getDouble("homeZ");
	}
}
