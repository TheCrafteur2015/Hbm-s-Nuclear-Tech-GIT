package com.hbm.entity.projectile;

import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class EntityCog extends EntityThrowableInterp {

	public EntityCog(World world) {
		super(world);
		setSize(1F, 1F);
	}

	public EntityCog(World world, double x, double y, double z) {
		super(world, x, y, z);
		setSize(1F, 1F);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(10, 0);
		this.dataWatcher.addObject(11, 0);
	}
	
	public EntityCog setOrientation(int rot) {
		this.dataWatcher.updateObject(10, rot);
		return this;
	}
	
	public EntityCog setMeta(int meta) {
		this.dataWatcher.updateObject(11, meta);
		return this;
	}
	
	public int getOrientation() {
		return this.dataWatcher.getWatchableObjectInt(10);
	}
	
	public int getMeta() {
		return this.dataWatcher.getWatchableObjectInt(11);
	}

	@Override
	public boolean interactFirst(EntityPlayer player) {

		if(!this.worldObj.isRemote) {
			
			if(player.inventory.addItemStackToInventory(new ItemStack(ModItems.gear_large, 1, getMeta())))
				setDead();
			
			player.inventoryContainer.detectAndSendChanges();
		}

		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		
		if(this.worldObj != null && mop != null && mop.typeOfHit == MovingObjectType.ENTITY && mop.entityHit.isEntityAlive()) {
			Entity e = mop.entityHit;
			e.attackEntityFrom(ModDamageSource.rubble, 1000);
			if(!e.isEntityAlive() && e instanceof EntityLivingBase) {
				NBTTagCompound vdat = new NBTTagCompound();
				vdat.setString("type", "giblets");
				vdat.setInteger("ent", e.getEntityId());
				vdat.setInteger("cDiv", 5);
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(vdat, e.posX, e.posY + e.height * 0.5, e.posZ), new TargetPoint(e.dimension, e.posX, e.posY + e.height * 0.5, e.posZ, 150));
				
				this.worldObj.playSoundEffect(e.posX, e.posY, e.posZ, "mob.zombie.woodbreak", 2.0F, 0.95F + this.worldObj.rand.nextFloat() * 0.2F);
			}
		}
		
		if(this.ticksExisted > 1 && this.worldObj != null && mop != null && mop.typeOfHit == MovingObjectType.BLOCK) {
			
			int orientation = this.dataWatcher.getWatchableObjectInt(10);

			if(orientation < 6) {
				
				if(Vec3.createVectorHelper(this.motionX, this.motionY, this.motionZ).lengthVector() < 0.75) {
					this.dataWatcher.updateObject(10, orientation + 6);
					orientation += 6;
				} else {
					ForgeDirection side = ForgeDirection.getOrientation(mop.sideHit);
					this.motionX *= 1 - (Math.abs(side.offsetX) * 2);
					this.motionY *= 1 - (Math.abs(side.offsetY) * 2);
					this.motionZ *= 1 - (Math.abs(side.offsetZ) * 2);
					this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 3F, false);
					
					if(this.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ).getExplosionResistance(this) < 50) {
						this.worldObj.func_147480_a(mop.blockX, mop.blockY, mop.blockZ, false);
					}
				}
			}
			
			if(orientation >= 6) {
				this.motionX = 0;
				this.motionY = 0;
				this.motionZ = 0;
				this.inGround = true;
			}
		}
	}
	
	@Override
	public void onUpdate() {
		
		if(!this.worldObj.isRemote) {
			int orientation = this.dataWatcher.getWatchableObjectInt(10);
			if(orientation >= 6 && !this.inGround) {
				this.dataWatcher.updateObject(10, orientation - 6);
			}
		}
		
		super.onUpdate();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return true;
	}

	@Override
	public double getGravityVelocity() {
		return this.inGround ? 0 : 0.03D;
	}

	@Override
	protected int groundDespawn() {
		return 0;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("rot", getOrientation());
		nbt.setInteger("meta", getMeta());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setOrientation(nbt.getInteger("rot"));
		setMeta(nbt.getInteger("meta"));
	}
}
