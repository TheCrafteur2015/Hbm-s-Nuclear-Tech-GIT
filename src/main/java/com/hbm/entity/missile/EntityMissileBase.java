package com.hbm.entity.missile;

import com.hbm.calc.EasyLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityMissileBase extends EntityThrowable {

	EasyLocation origin;
	EasyLocation loc0;
	EasyLocation loc1;
	EasyLocation loc2;
	EasyLocation loc3;
	EasyLocation loc4;
	EasyLocation loc5;
	EasyLocation loc6;
	EasyLocation loc7;
	EasyLocation target;
	
	public int phase = 0;
	
	public int targetPoint = 0;
	public int lengthX;
	public int lengthZ;
	public double lengthFlight;
	public int baseHeight = 50;
	public double missileSpeed = 1.5;

	public EntityMissileBase(World p_i1776_1_) {
		super(p_i1776_1_);
		this.ignoreFrustumCheck = true;
	}

	public EntityMissileBase(World p_i1582_1_, int x, int z, double a, double b, double c) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
		this.posX = a;
		this.posY = b;
		this.posZ = c;
		
		this.motionY = 0.1;
		
		this.lengthX = (int) (x - this.posX);
		this.lengthZ = (int) (z - this.posZ);
		this.lengthFlight = Math.sqrt(Math.pow(this.lengthX, 2) + Math.pow(this.lengthZ, 2));
		
		
		this.origin = new EasyLocation(this.posX, this.posY, this.posZ);
		
		this.loc0 = new EasyLocation(this.posX, this.posY + this.baseHeight, this.posZ);
		this.loc1 = new EasyLocation(this.posX + this.lengthX/this.lengthFlight * 10, this.posY + this.baseHeight + 20, this.posZ + this.lengthZ/this.lengthFlight * 10);
		this.loc2 = new EasyLocation(this.posX + this.lengthX/this.lengthFlight * 30, this.posY + this.baseHeight + 40, this.posZ + this.lengthZ/this.lengthFlight * 30);
		this.loc3 = new EasyLocation(this.posX + this.lengthX/this.lengthFlight * 50, this.posY + this.baseHeight + 50, this.posZ + this.lengthZ/this.lengthFlight * 50);
			
		this.loc4 = new EasyLocation(x - (this.lengthX/this.lengthFlight * 50), this.posY + this.baseHeight + 50, z - (this.lengthZ/this.lengthFlight * 50));
		this.loc5 = new EasyLocation(x - (this.lengthX/this.lengthFlight * 30), this.posY + this.baseHeight + 40, z - (this.lengthZ/this.lengthFlight * 30));
		this.loc6 = new EasyLocation(x - (this.lengthX/this.lengthFlight * 10), this.posY + this.baseHeight + 20, z - (this.lengthZ/this.lengthFlight * 10));
		this.loc7 = new EasyLocation(x, this.posY + this.baseHeight, z);

		
		this.target = new EasyLocation(x, 0, z);
	}
	
	protected void freePizzaGoddammit(EasyLocation loc) {
		double x = loc.posX - this.posX;
		double y = loc.posY - this.posY;
		double z = loc.posZ - this.posZ;
		this.lengthFlight = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
		
		this.motionX = x / this.lengthFlight * this.missileSpeed;
		this.motionY = y / this.lengthFlight * this.missileSpeed;
		this.motionZ = z / this.lengthFlight * this.missileSpeed;
	}
	
	protected void rotation() {
        float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

        for (this.rotationPitch = (float)(Math.atan2(this.motionY, f2) * 180.0D / Math.PI) - 90; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
        {
            ;
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
        {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F)
        {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
        {
            this.prevRotationYaw += 360.0F;
        }
	}
	
	@Override
    public void onUpdate()
    {

        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        
        rotation();
        
        switch(this.phase)
        {
        case 0:
        	if(this.loc0 != null)
        	{
        		freePizzaGoddammit(this.loc0);
        		if(this.loc0.posX + 2 > this.posX && this.loc0.posX - 2 < this.posX &&
        			this.loc0.posY + 2 > this.posY && this.loc0.posY - 2 < this.posY &&
        			this.loc0.posZ + 2 > this.posZ && this.loc0.posZ - 2 < this.posZ)
        		{
        			this.phase = 1;
        		}
        	}
        	break;
        case 1:
        	if(this.loc1 != null)
        	{
        		freePizzaGoddammit(this.loc1);
        		if(this.loc1.posX + 2 > this.posX && this.loc1.posX - 2 < this.posX &&
        				this.loc1.posY + 2 > this.posY && this.loc1.posY - 2 < this.posY &&
        				this.loc1.posZ + 2 > this.posZ && this.loc1.posZ - 2 < this.posZ)
        		{
        			this.phase = 2;
        		}
        	}
        	break;
        case 2:
        	if(this.loc2 != null)
        	{
        		freePizzaGoddammit(this.loc2);
        		if(this.loc2.posX + 2 > this.posX && this.loc2.posX - 2 < this.posX &&
        				this.loc2.posY + 2 > this.posY && this.loc2.posY - 2 < this.posY &&
        				this.loc2.posZ + 2 > this.posZ && this.loc2.posZ - 2 < this.posZ)
        		{
        			this.phase = 3;
        		}
        	}
        	break;
        case 3:
        	if(this.loc3 != null)
        	{
        		freePizzaGoddammit(this.loc3);
        		if(this.loc3.posX + 2 > this.posX && this.loc3.posX - 2 < this.posX &&
        				this.loc3.posY + 2 > this.posY && this.loc3.posY - 2 < this.posY &&
        				this.loc3.posZ + 2 > this.posZ && this.loc3.posZ - 2 < this.posZ)
        		{
        			this.phase = 4;
        		}
        	}
        	break;
        case 4:
        	if(this.loc4 != null)
        	{
        		freePizzaGoddammit(this.loc4);
        		if(this.loc4.posX + 2 > this.posX && this.loc4.posX - 2 < this.posX &&
        				this.loc4.posY + 2 > this.posY && this.loc4.posY - 2 < this.posY &&
        				this.loc4.posZ + 2 > this.posZ && this.loc4.posZ - 2 < this.posZ)
        		{
        			this.phase = 5;
        		}
        	}
        	break;
        case 5:
        	if(this.loc5 != null)
        	{
        		freePizzaGoddammit(this.loc5);
        		if(this.loc5.posX + 2 > this.posX && this.loc5.posX - 2 < this.posX &&
        				this.loc5.posY + 2 > this.posY && this.loc5.posY - 2 < this.posY &&
        				this.loc5.posZ + 2 > this.posZ && this.loc5.posZ - 2 < this.posZ)
        		{
        			this.phase = 6;
        		}
        	}
        	break;
        case 6:
        	if(this.loc6 != null)
        	{
        		freePizzaGoddammit(this.loc6);
        		if(this.loc6.posX + 2 > this.posX && this.loc6.posX - 2 < this.posX &&
        				this.loc6.posY + 2 > this.posY && this.loc6.posY - 2 < this.posY &&
        				this.loc6.posZ + 2 > this.posZ && this.loc6.posZ - 2 < this.posZ)
        		{
        			this.phase = 7;
        		}
        	}
        	break;
        case 7:
        	if(this.loc7 != null)
        	{
        		freePizzaGoddammit(this.loc7);
        		if(this.loc7.posX + 2 > this.posX && this.loc7.posX - 2 < this.posX &&
        				this.loc7.posY + 2 > this.posY && this.loc7.posY - 2 < this.posY &&
        				this.loc7.posZ + 2 > this.posZ && this.loc7.posZ - 2 < this.posZ)
        		{
        			this.phase = 8;
        		}
        	}
        	break;
        case 8:
        	if(this.target != null)
        	{
        		freePizzaGoddammit(this.target);
        		if(this.target.posX + 2 > this.posX && this.target.posX - 2 < this.posX &&
        				this.target.posY + 2 > this.posY && this.target.posY - 2 < this.posY &&
        				this.target.posZ + 2 > this.posZ && this.target.posZ - 2 < this.posZ)
        		{
        			this.phase = -1;
        		}
        	}
        	break;
        }
        
        if(this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ) != Blocks.air && this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ) != Blocks.water && this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ) != Blocks.flowing_water)
        {
    		if(!this.worldObj.isRemote)
    		{
    			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 5.0F, true);
    		}
    		setDead();
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
