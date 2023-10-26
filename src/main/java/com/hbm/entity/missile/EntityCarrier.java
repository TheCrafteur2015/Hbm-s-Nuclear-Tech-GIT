package com.hbm.entity.missile;

import com.hbm.explosion.ExplosionLarge;
import com.hbm.items.ISatChip;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.saveddata.satellites.Satellite;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityCarrier extends EntityThrowable {

	double acceleration = 0.00D;
	
	private ItemStack payload;

	public EntityCarrier(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
        setSize(3.0F, 26.0F);
	}
	
	@Override
	public void onUpdate() {
		
		//this.setDead();
		
		if(this.motionY < 3.0D) {
			this.acceleration += 0.0005D;
			this.motionY += this.acceleration;
		}
		
		setLocationAndAngles(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ, 0, 0);
		
		if(!this.worldObj.isRemote) {
			for(int i = 0; i < 10; i++) {
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "gasfire");
				data.setDouble("mY", -0.2D);
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, this.posX + this.rand.nextGaussian() * 0.75D, this.posY - 0.25D, this.posZ + this.rand.nextGaussian() * 0.75D), new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 200));
			}
			
			if(this.dataWatcher.getWatchableObjectInt(8) == 1)
				for(int i = 0; i < 2; i++) {
					NBTTagCompound d1 = new NBTTagCompound();
					d1.setString("type", "gasfire");
					d1.setDouble("mY", -0.2D);
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(d1, this.posX + this.rand.nextGaussian() * 0.75D + 2.5, this.posY - 0.25D, this.posZ + this.rand.nextGaussian() * 0.75D), new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 200));
					
					NBTTagCompound d2 = new NBTTagCompound();
					d2.setString("type", "gasfire");
					d2.setDouble("mY", -0.2D);
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(d2, this.posX + this.rand.nextGaussian() * 0.75D - 2.5, this.posY - 0.25D, this.posZ + this.rand.nextGaussian() * 0.75D), new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 200));
					
					NBTTagCompound d3 = new NBTTagCompound();
					d3.setString("type", "gasfire");
					d3.setDouble("mY", -0.2D);
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(d3, this.posX + this.rand.nextGaussian() * 0.75D, this.posY - 0.25D, this.posZ + this.rand.nextGaussian() * 0.75D + 2.5), new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 200));
					
					NBTTagCompound d4 = new NBTTagCompound();
					d4.setString("type", "gasfire");
					d4.setDouble("mY", -0.2D);
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(d4, this.posX + this.rand.nextGaussian() * 0.75D, this.posY - 0.25D, this.posZ + this.rand.nextGaussian() * 0.75D - 2.5), new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 200));
				}
			
			
			if(this.ticksExisted < 20) {
				ExplosionLarge.spawnShock(this.worldObj, this.posX, this.posY, this.posZ, 13 + this.rand.nextInt(3), 4 + this.rand.nextGaussian() * 2);
			}
		}
		
		if(this.posY > 300 && this.dataWatcher.getWatchableObjectInt(8) == 1)
			disengageBoosters();
			//this.setDead();
		
		if(this.posY > 600) {
			deployPayload();
		}
	}
	
	private void deployPayload() {

		if(this.payload != null) {
			
			if(this.payload.getItem() == ModItems.flame_pony) {
				ExplosionLarge.spawnTracers(this.worldObj, this.posX, this.posY, this.posZ, 25);
				for(Object p : this.worldObj.playerEntities)
					((EntityPlayer)p).triggerAchievement(MainRegistry.achSpace);
			}
			
			if(this.payload.getItem() == ModItems.sat_foeq) {
				for(Object p : this.worldObj.playerEntities)
					((EntityPlayer)p).triggerAchievement(MainRegistry.achFOEQ);
			}
			
			if(this.payload.getItem() instanceof ISatChip) {
				
			    int freq = ISatChip.getFreqS(this.payload);
		    	
		    	Satellite.orbit(this.worldObj, Satellite.getIDFromItem(this.payload.getItem()), freq, this.posX, this.posY, this.posZ);
			}
		}
		
		setDead();
	}

	@Override
	protected void entityInit() {
        this.dataWatcher.addObject(8, 1);
	}
	
	public void setPayload(ItemStack stack) {
		this.payload = stack.copy();
	}
	
	private void disengageBoosters() {
		this.dataWatcher.updateObject(8, 0);
		
		if(!this.worldObj.isRemote) {
			EntityBooster boost1 = new EntityBooster(this.worldObj);
			boost1.posX = this.posX + 1.5D;
			boost1.posY = this.posY;
			boost1.posZ = this.posZ;
			boost1.motionX = 0.45D + this.rand.nextDouble() * 0.2D;
			boost1.motionY = this.motionY;
			boost1.motionZ = this.rand.nextGaussian() * 0.1D;
			this.worldObj.spawnEntityInWorld(boost1);
			
			EntityBooster boost2 = new EntityBooster(this.worldObj);
			boost2.posX = this.posX - 1.5D;
			boost2.posY = this.posY;
			boost2.posZ = this.posZ;
			boost2.motionX = -0.45D - this.rand.nextDouble() * 0.2D;
			boost2.motionY = this.motionY;
			boost2.motionZ = this.rand.nextGaussian() * 0.1D;
			this.worldObj.spawnEntityInWorld(boost2);
			
			EntityBooster boost3 = new EntityBooster(this.worldObj);
			boost3.posX = this.posX;
			boost3.posY = this.posY;
			boost3.posZ = this.posZ + 1.5D;
			boost3.motionZ = 0.45D + this.rand.nextDouble() * 0.2D;
			boost3.motionY = this.motionY;
			boost3.motionX = this.rand.nextGaussian() * 0.1D;
			this.worldObj.spawnEntityInWorld(boost3);
			
			EntityBooster boost4 = new EntityBooster(this.worldObj);
			boost4.posX = this.posX;
			boost4.posY = this.posY;
			boost4.posZ = this.posZ - 1.5D;
			boost4.motionZ = -0.45D - this.rand.nextDouble() * 0.2D;
			boost4.motionY = this.motionY;
			boost4.motionX = this.rand.nextGaussian() * 0.1D;
			this.worldObj.spawnEntityInWorld(boost4);
		}
	}

	@Override
	protected void onImpact(MovingObjectPosition p_70184_1_) {
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 500000;
    }
}
