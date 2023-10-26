package com.hbm.entity.projectile;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.lib.ModDamageSource;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityDuchessGambit extends EntityThrowable {

	public EntityDuchessGambit(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
	}
	
	@Override
	public void onUpdate() {


		this.lastTickPosX = this.prevPosX = this.posX;
		this.lastTickPosY = this.prevPosY = this.posY;
		this.lastTickPosZ = this.prevPosZ = this.posZ;
		setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		
		/*this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		
		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;*/
		
		this.motionY -= 0.03;
		if(this.motionY < -1.5)
			this.motionY = -1.5;
        
        if(this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ) != Blocks.air)
        {
            this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "hbm:alarm.gambit", 10000.0F, 1F);
    		setDead();
    			
    		List<Entity> list = (List<Entity>)this.worldObj.getEntitiesWithinAABBExcludingEntity(null, 
    				AxisAlignedBB.getBoundingBox(this.posX - 5, this.posY - 2, this.posZ - 9, this.posX + 5, this.posY + 2, this.posZ + 9));
    			
    		for(Entity e : list) {
    			e.attackEntityFrom(ModDamageSource.boat, 1000);
    		}
    		
    		if(!this.worldObj.isRemote) {
        		ExplosionLarge.explode(this.worldObj, this.posX, this.posY, this.posZ - 6, 2, true, false, false);
        		ExplosionLarge.explode(this.worldObj, this.posX, this.posY, this.posZ - 3, 2, true, false, false);
        		ExplosionLarge.explode(this.worldObj, this.posX, this.posY, this.posZ, 2, true, false, false);
        		ExplosionLarge.explode(this.worldObj, this.posX, this.posY, this.posZ + 3, 2, true, false, false);
        		ExplosionLarge.explode(this.worldObj, this.posX, this.posY, this.posZ + 6, 2, true, false, false);
        		
    			this.worldObj.setBlock((int)(this.posX - 0.5), (int)(this.posY + 0.5), (int)(this.posZ - 0.5), ModBlocks.boat);
    		}
        	ExplosionLarge.spawnShock(this.worldObj, this.posX, this.posY + 1, this.posZ, 24, 3);
    		ExplosionLarge.spawnShock(this.worldObj, this.posX, this.posY + 1, this.posZ, 24, 2.5);
    		ExplosionLarge.spawnShock(this.worldObj, this.posX, this.posY + 1, this.posZ, 24, 2);
    		ExplosionLarge.spawnShock(this.worldObj, this.posX, this.posY + 1, this.posZ, 24, 1.5);
    		ExplosionLarge.spawnShock(this.worldObj, this.posX, this.posY + 1, this.posZ, 24, 1);
        }
    }

	@Override
	protected void onImpact(MovingObjectPosition p_70184_1_) {
		
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 25000;
    }
}
