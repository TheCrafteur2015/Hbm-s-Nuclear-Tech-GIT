package com.hbm.entity.missile;

import com.hbm.calc.EasyLocation;

import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityTestMissile extends EntityThrowable {

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
	
	public int lengthX;
	public int lengthZ;
	public double lengthFlight;
	public int baseHeight = 0;
	public double missileSpeed = 3;
	
	public int phase = 0;

	public EntityTestMissile(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityTestMissile(World p_i1582_1_, int x, int z, double a, double b, double c) {
		super(p_i1582_1_);
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
		
		/*this.worldObj.setBlock((int)loc0.posX, (int)loc0.posY, (int)loc0.posZ, Blocks.dirt);
		this.worldObj.setBlock((int)loc1.posX, (int)loc1.posY, (int)loc1.posZ, Blocks.dirt);
		this.worldObj.setBlock((int)loc2.posX, (int)loc2.posY, (int)loc2.posZ, Blocks.dirt);
		this.worldObj.setBlock((int)loc3.posX, (int)loc3.posY, (int)loc3.posZ, Blocks.dirt);
		this.worldObj.setBlock((int)loc4.posX, (int)loc4.posY, (int)loc4.posZ, Blocks.stone);
		this.worldObj.setBlock((int)loc5.posX, (int)loc5.posY, (int)loc5.posZ, Blocks.stone);
		this.worldObj.setBlock((int)loc6.posX, (int)loc6.posY, (int)loc6.posZ, Blocks.stone);
		this.worldObj.setBlock((int)loc7.posX, (int)loc7.posY, (int)loc7.posZ, Blocks.stone);*/
		
		/*System.out.print("\n" + loc0.posX + " " + loc0.posY + " " + loc0.posZ);
		System.out.print("\n" + loc1.posX + " " + loc1.posY + " " + loc1.posZ);
		System.out.print("\n" + loc2.posX + " " + loc2.posY + " " + loc2.posZ);
		System.out.print("\n" + loc3.posX + " " + loc3.posY + " " + loc3.posZ);
		System.out.print("\n");
		System.out.print("\n" + loc4.posX + " " + loc4.posY + " " + loc4.posZ);
		System.out.print("\n" + loc5.posX + " " + loc5.posY + " " + loc5.posZ);
		System.out.print("\n" + loc6.posX + " " + loc6.posY + " " + loc6.posZ);
		System.out.print("\n" + loc7.posX + " " + loc7.posY + " " + loc7.posZ);*/
	}

	@Override
	protected void entityInit() {
		
	}
	
	@Override
    public void onUpdate()
    {
        //super.onUpdate();

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
        
        if(this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ) != Blocks.air)
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
	
	private void freePizzaGoddammit(EasyLocation loc) {
		double x = loc.posX - this.posX;
		double y = loc.posY - this.posY;
		double z = loc.posZ - this.posZ;
		this.lengthFlight = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
		
		this.motionX = x / this.lengthFlight * this.missileSpeed;
		this.motionY = y / this.lengthFlight * this.missileSpeed;
		this.motionZ = z / this.lengthFlight * this.missileSpeed;
	}
	
	private void rotation() {
		/*EasyVector vec0 = new EasyVector(this.motionX, this.motionZ);
		
		EasyVector vec1 = new EasyVector(this.motionY, vec0.getResult());
		
		this.rotationYaw = (float)Math.acos((vec0.a * 0 + vec0.b * 1) / (vec0.getResult() * 1));
		//this.rotationPitch = (float)Math.acos((vec0.a * vec1.a + vec0.b * vec1.b) / (vec0.getResult() * vec1.getResult())) * 10;

		this.rotationPitch = (float)Math.acos((vec0.a * vec1.a + vec0.b * vec1.b) / (vec0.getResult() * vec1.getResult())) * 100 - 90;
		if(this.rotationPitch < 0)
			this.rotationPitch += 180;
		
		System.out.print("\n" + this.rotationYaw);
		System.out.print("\n" + this.rotationPitch);*/
        float f2;
        f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
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

}
