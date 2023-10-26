package com.hbm.entity.mob.ai;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityAIBreaking extends EntityAIBase {

	EntityLivingBase target;
	int[] markedLoc;
	EntityLiving entityDigger;
	int digTick = 0;
	int scanTick = 0;
	
	public EntityAIBreaking(EntityLiving entity)
	{
		this.entityDigger = entity;
	}
	
	@Override
	public boolean shouldExecute()
	{
		this.target = this.entityDigger.getAttackTarget();
		
		if(this.target != null && this.entityDigger.getNavigator().noPath() && this.entityDigger.getDistanceToEntity(this.target) > 1D && (this.target.onGround || !this.entityDigger.canEntityBeSeen(this.target)))
		{
			MovingObjectPosition mop = GetNextObstical(this.entityDigger, 2D);
			
			if(mop == null || mop.typeOfHit != MovingObjectType.BLOCK)
			{
				return false;
			}

			Block block = this.entityDigger.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
			
			if(block.getBlockHardness(this.entityDigger.worldObj, mop.blockX, mop.blockY, mop.blockZ) >= 0) {
				this.markedLoc = new int[]{mop.blockX, mop.blockY, mop.blockZ};
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean continueExecuting()
	{
		//return target != null && entityDigger != null && target.isEntityAlive() && entityDigger.isEntityAlive() && markedLoc != null && entityDigger.getNavigator().noPath() && entityDigger.getDistanceToEntity(target) > 1D && (target.onGround || !entityDigger.canEntityBeSeen(target));
		
		if(this.markedLoc != null)  {
			
			Vec3 vector = Vec3.createVectorHelper(
					this.markedLoc[0] - this.entityDigger.posX,
					this.markedLoc[1] - (this.entityDigger.posY + this.entityDigger.getEyeHeight()),
					this.markedLoc[2] - this.entityDigger.posZ);

			return this.entityDigger != null && this.entityDigger.isEntityAlive() && vector.lengthVector() <= 4;
		}
		
		return false;
	}
	
	@Override
	public void updateTask()
	{
    	MovingObjectPosition mop = null;
    	
    	if(this.entityDigger.ticksExisted % 10 == 0)
    	{
    		mop = GetNextObstical(this.entityDigger, 2D);
    	}
		
		if(mop != null && mop.typeOfHit == MovingObjectType.BLOCK)
		{
			this.markedLoc = new int[]{mop.blockX, mop.blockY, mop.blockZ};
		}
		
		if(this.markedLoc == null || this.markedLoc.length != 3 || this.entityDigger.worldObj.getBlock(this.markedLoc[0], this.markedLoc[1], this.markedLoc[2]) == Blocks.air)
		{
			this.digTick = 0;
			return;
		}
		
		Block block = this.entityDigger.worldObj.getBlock(this.markedLoc[0], this.markedLoc[1], this.markedLoc[2]);
		this.digTick++;
		
		int health = (int) block.getBlockHardness(this.entityDigger.worldObj, this.markedLoc[0], this.markedLoc[1], this.markedLoc[2]) / 3;
		
		if(health < 0) {
			this.markedLoc = null;
			return;
		}
		
		float str = (this.digTick * 0.05F) / (float)health;
		
		if(str >= 1F)
		{
			this.digTick = 0;
			
			boolean canHarvest = false;
			this.entityDigger.worldObj.func_147480_a(this.markedLoc[0], this.markedLoc[1], this.markedLoc[2], canHarvest);
			this.markedLoc = null;
			
			if(this.target != null)
				this.entityDigger.getNavigator().setPath(this.entityDigger.getNavigator().getPathToEntityLiving(this.target), 1D);
		} else
		{
			if(this.digTick % 5 == 0)
			{
				this.entityDigger.worldObj.playSoundAtEntity(this.entityDigger, block.stepSound.getStepResourcePath(), block.stepSound.getVolume() + 1F, block.stepSound.getPitch());
				this.entityDigger.swingItem();
				this.entityDigger.worldObj.destroyBlockInWorldPartially(this.entityDigger.getEntityId(), this.markedLoc[0], this.markedLoc[1], this.markedLoc[2], (int)(str * 10F));
			}
		}
	}
	
	@Override
	public void resetTask()
	{
		this.markedLoc = null;
		this.digTick = 0;
	}
	
	/**
	 * Rolls through all the points in the bounding box of the entity and raycasts them toward it's current heading to return any blocks that may be obstructing it's path.
	 * The bigger the entity the longer this calculation will take due to the increased number of points (Generic bipeds should only need 2)
	 */
    public MovingObjectPosition GetNextObstical(EntityLivingBase entityLiving, double dist)
    {
    	// Returns true if something like Iguana Tweaks is nerfing the vanilla picks. This will then cause zombies to ignore the harvestability of blocks when holding picks
        float f = 1.0F;
        float f1 = entityLiving.prevRotationPitch + (entityLiving.rotationPitch - entityLiving.prevRotationPitch) * f;
        float f2 = entityLiving.prevRotationYaw + (entityLiving.rotationYaw - entityLiving.prevRotationYaw) * f;
        
        int digWidth = MathHelper.ceiling_double_int(entityLiving.width);
        int digHeight = MathHelper.ceiling_double_int(entityLiving.height);
        
        int passMax = digWidth * digWidth * digHeight;
        
        int x = this.scanTick%digWidth - (digWidth/2);
        int y = this.scanTick/(digWidth * digWidth);
        int z = (this.scanTick%(digWidth * digWidth))/digWidth - (digWidth/2);
        
		double rayX = x + entityLiving.posX;
		double rayY = y + entityLiving.posY;
		double rayZ = z + entityLiving.posZ;
		
    	MovingObjectPosition mop = EntityAIBreaking.RayCastBlocks(entityLiving.worldObj, rayX, rayY, rayZ, f2, f1, dist, false);
    	
    	if(mop != null && mop.typeOfHit == MovingObjectType.BLOCK)
    	{
    		Block block = entityLiving.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
    		
    		if(block.getBlockHardness(entityLiving.worldObj, mop.blockX, mop.blockY, mop.blockZ) >= 0)
    		{
    			this.scanTick = 0;
    			return mop;
    		} else
    		{
    			this.scanTick = (this.scanTick + 1)%passMax;
    			return null;
    		}
    	} else
    	{
			this.scanTick = (this.scanTick + 1)%passMax;
			return null;
    	}
    }
    
    public static MovingObjectPosition RayCastBlocks(World world, double x, double y, double z, float yaw, float pitch, double dist, boolean liquids)
    {
        Vec3 vec3 = Vec3.createVectorHelper(x, y, z);
        float f3 = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
        float f4 = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
        float f5 = -MathHelper.cos(-pitch * 0.017453292F);
        float f6 = MathHelper.sin(-pitch * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = dist; // Ray Distance
        Vec3 vec31 = vec3.addVector((double)f7 * d3, (double)f6 * d3, (double)f8 * d3);
        return EntityAIBreaking.RayCastBlocks(world, vec3, vec31, liquids);
    }
    
    public static MovingObjectPosition RayCastBlocks(World world, Vec3 vector1, Vec3 vector2, boolean liquids)
    {
        return world.func_147447_a(vector1, vector2, liquids, !liquids, false);
    }
}
