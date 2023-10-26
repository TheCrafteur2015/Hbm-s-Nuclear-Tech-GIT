package com.hbm.entity.projectile;

import com.hbm.blocks.bomb.NukeCustom;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityFallingNuke extends EntityThrowable {
	
	float tnt;
	float nuke;
	float hydro;
	float amat;
	float dirty;
	float schrab;
	float euph;

	public EntityFallingNuke(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
        setSize(0.98F, 0.98F);
	}

	public EntityFallingNuke(World p_i1582_1_, float tnt, float nuke, float hydro, float amat, float dirty, float schrab, float euph) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
		
		this.tnt = tnt;
		this.nuke = nuke;
		this.hydro = hydro;
		this.amat = amat;
		this.dirty = dirty;
		this.schrab = schrab;
		this.euph = euph;
        this.prevRotationYaw = this.rotationYaw = 90;
        this.prevRotationPitch = this.rotationPitch = 90;
        
        setSize(0.98F, 0.98F);
	}

    @Override
	protected void entityInit() {
    	this.dataWatcher.addObject(20, Byte.valueOf((byte)0));
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
		
		this.motionX *= 0.99;
		this.motionZ *= 0.99;
		this.motionY -= 0.05D;
		
		if(this.motionY < -1)
			this.motionY = -1;
        
        rotation();
        
        if(this.worldObj.getBlock((int)Math.floor(this.posX), (int)Math.floor(this.posY), (int)Math.floor(this.posZ)) != Blocks.air)
        {
    		if(!this.worldObj.isRemote)
    		{
				NukeCustom.explodeCustom(this.worldObj, this.posX, this.posY, this.posZ, this.tnt, this.nuke, this.hydro, this.amat, this.dirty, this.schrab, this.euph);
	    		setDead();
    		}
        }
	}
	
	public void rotation() {

		this.prevRotationPitch = this.rotationPitch;

		if(this.rotationPitch > -75)
			this.rotationPitch -= 2;
	}

	@Override
	protected void onImpact(MovingObjectPosition p_70184_1_) { }

	@Override
	public void writeEntityToNBT(NBTTagCompound tag) {
		super.writeEntityToNBT(tag);
		tag.setFloat("tnt", this.tnt);
		tag.setFloat("nuke", this.nuke);
		tag.setFloat("hydro", this.hydro);
		tag.setFloat("amat", this.amat);
		tag.setFloat("dirty", this.dirty);
		tag.setFloat("schrab", this.schrab);
		tag.setFloat("euph", this.euph);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tag) {
		super.readEntityFromNBT(tag);
		this.tnt = tag.getFloat("tnt");
		this.nuke = tag.getFloat("nuke");
		this.hydro = tag.getFloat("hydro");
		this.amat = tag.getFloat("amat");
		this.dirty = tag.getFloat("dirty");
		this.schrab = tag.getFloat("schrab");
		this.euph = tag.getFloat("euph");
	}

	@Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 25000;
    }
}
