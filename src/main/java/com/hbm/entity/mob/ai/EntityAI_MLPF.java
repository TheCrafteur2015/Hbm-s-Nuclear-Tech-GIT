package com.hbm.entity.mob.ai;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public class EntityAI_MLPF extends EntityAIBase {

	private Class<?> targetClass;
    private EntityLivingBase target;
	private EntityLiving mover;
    private final Sorter theNearestAttackableTargetSorter;
	private int range;
	private int distance;
	private static final int vertical = 10;
    private double speed;
	
	public EntityAI_MLPF(EntityLiving entity, Class<?> targetClass, int range, double speed, int distance)
	{
		this.mover = entity;
		this.targetClass = targetClass;
		this.range = range;
		this.speed = speed;
		this.distance = distance;
        this.theNearestAttackableTargetSorter = new Sorter(entity);
	}
	
	@Override
	public boolean shouldExecute() {
		
		//roll the dice for targetiing if there's nothing to track
		if(this.mover.getRNG().nextInt(100) < 5 && this.mover.getAttackTarget() == null) {
			//System.out.println("Randomizer fired!");
			//load potential targets
			calculateTarget();
			
			//start if there is a valid target
			return this.target != null;
		}
		
		return false;
	}
	
    @Override
	public void startExecuting() {

    	//create a path line from mover to target
    	Vec3 vec = Vec3.createVectorHelper(
    			this.target.posX - this.mover.posX,
    			this.target.posY - this.mover.posY,
    			this.target.posZ - this.mover.posZ);
    	
    	
    	//line length is capped so the pathfinder can manage it
    	int range = this.distance;

    	if(vec.lengthVector() < 16)
    		this.mover.setAttackTarget(this.target);

    	vec = vec.normalize();
    	vec.xCoord *= range;
    	vec.yCoord *= range;
    	vec.zCoord *= range;
    	
    	//target positions are set (with randomized Y-offset)
    	double x = this.mover.posX + vec.xCoord;
    	double y = this.mover.posY + vec.yCoord - 5 + this.mover.getRNG().nextInt(11);
    	double z = this.mover.posZ + vec.zCoord;
    	
    	//System.out.println("Routing to " + x + "/" + y + "/" + z);
    	
		//this is where the magic happens
        this.mover.getNavigator().tryMoveToXYZ(x, y, z, this.speed);
        
        //System.out.println("Start successful? " + success);
    }
	
	@Override
	public boolean continueExecuting() {

		//only continue if the path is valid
        return !this.mover.getNavigator().noPath();
	}
	
	@Override
	public void resetTask()
	{
		//once the task is complete, remove target
		this.target = null;
	}
	
	//scans the area and determines a new target entity
	
	private void calculateTarget() {
		
		List<Entity> list = this.mover.worldObj.getEntitiesWithinAABB(this.targetClass, AxisAlignedBB.getBoundingBox(
				this.mover.posX - this.range,
				this.mover.posY - EntityAI_MLPF.vertical,
				this.mover.posZ - this.range,
				this.mover.posX + this.range,
				this.mover.posY + EntityAI_MLPF.vertical,
				this.mover.posZ + this.range));
		
		Collections.sort(list, this.theNearestAttackableTargetSorter);
		
		if (!list.isEmpty())
        {
			this.target = (EntityLivingBase)list.get(0);
        }
	}

	public static class Sorter implements Comparator<Object> {
		private final Entity theEntity;

		public Sorter(Entity p_i1662_1_) {
			this.theEntity = p_i1662_1_;
		}

		public int compare(Entity p_compare_1_, Entity p_compare_2_) {
			double d0 = this.theEntity.getDistanceSqToEntity(p_compare_1_);
			double d1 = this.theEntity.getDistanceSqToEntity(p_compare_2_);
			return d0 < d1 ? -1 : (d0 > d1 ? 1 : 0);
		}

		@Override
		public int compare(Object p_compare_1_, Object p_compare_2_) {
			return this.compare((Entity) p_compare_1_, (Entity) p_compare_2_);
		}
	}
}
