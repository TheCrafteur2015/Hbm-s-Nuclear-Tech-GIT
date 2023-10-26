package com.hbm.entity.projectile;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.potion.HbmPotion;
import com.hbm.tileentity.machine.rbmk.RBMKDials;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityRBMKDebris extends EntityDebrisBase {
	
	public float rot;
	public float lastRot;
	private boolean hasSizeSet = false;

	public EntityRBMKDebris(World world) {
		super(world);
	}

	public EntityRBMKDebris(World world, double x, double y, double z, DebrisType type) {
		super(world);
		setPosition(x, y, z);
		setType(type);
	}

	@Override
	public boolean interactFirst(EntityPlayer player) {

		if(!this.worldObj.isRemote) {
			
			switch(getType()) {
			case BLANK: if(player.inventory.addItemStackToInventory(new ItemStack(ModItems.debris_metal))) setDead(); break;
			case ELEMENT: if(player.inventory.addItemStackToInventory(new ItemStack(ModItems.debris_metal))) setDead(); break;
			case FUEL: if(player.inventory.addItemStackToInventory(new ItemStack(ModItems.debris_fuel))) setDead(); break;
			case GRAPHITE: if(player.inventory.addItemStackToInventory(new ItemStack(ModItems.debris_graphite))) setDead(); break;
			case LID: if(player.inventory.addItemStackToInventory(new ItemStack(ModItems.rbmk_lid))) setDead(); break;
			case ROD: if(player.inventory.addItemStackToInventory(new ItemStack(ModItems.debris_metal))) setDead(); break;
			}
			
			player.inventoryContainer.detectAndSendChanges();
		}

		return false;
	}

	@Override
	public void onUpdate() {
		
		if(!this.hasSizeSet) {
			
			switch(getType()) {
			case BLANK: setSize(0.5F, 0.5F); break;
			case ELEMENT: setSize(1F, 1F); break;
			case FUEL: setSize(0.25F, 0.25F); break;
			case GRAPHITE: setSize(0.25F, 0.25F); break;
			case LID: setSize(1F, 0.5F); break;
			case ROD: setSize(0.75F, 0.5F); break;
			}
			
			this.hasSizeSet = true;
		}
		
		if(!this.worldObj.isRemote) {
			if(getType() == DebrisType.LID && this.motionY > 0) {
	
				Vec3 pos = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
				Vec3 next = Vec3.createVectorHelper(this.posX + this.motionX * 2, this.posY + this.motionY * 2, this.posZ + this.motionZ * 2);
				MovingObjectPosition mop = this.worldObj.func_147447_a(pos, next, false, false, false);
				
				if(mop != null && mop.typeOfHit == MovingObjectType.BLOCK) {
	
					int x = mop.blockX;
					int y = mop.blockY;
					int z = mop.blockZ;
					
					for(int i = -1; i <= 1; i++) {
						for(int j = -1; j <= 1; j++) {
							for(int k = -1; k <= 1; k++) {
								
								int rn = Math.abs(i) + Math.abs(j) + Math.abs(k);
								
								if(rn <= 1 || this.rand.nextInt(rn) == 0)
									this.worldObj.setBlockToAir(x + i, y + j, z + k);
							}
						}
					}
					
					setDead();
				}
			}
			
			if(getType() == DebrisType.FUEL || getType() == DebrisType.GRAPHITE) {
				List<EntityLivingBase> entities = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(2.5, 2.5, 2.5));
				
				int level = getType() == DebrisType.FUEL ? 9 : 4;
				for(EntityLivingBase e : entities) {
					e.addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 60 * 20, level));
				}
			}
			
			if(!RBMKDials.getPermaScrap(this.worldObj) && this.ticksExisted > getLifetime() + getEntityId() % 50)
				setDead();
		}
		
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		
		this.motionY -= 0.04D;
		moveEntity(this.motionX, this.motionY, this.motionZ);
		
		this.lastRot = this.rot;

		if(this.onGround) {
			this.motionX *= 0.85D;
			this.motionZ *= 0.85D;
			this.motionY *= -0.5D;
			
		} else {
			
			this.rot += 10F;
			
			if(this.rot >= 360F) {
				this.rot -= 360F;
				this.lastRot -= 360F;
			}
		}
	}
	
	@Override
	protected int getLifetime() {
		
		switch(getType()) {
		case BLANK: return 3 * 60 * 20;
		case ELEMENT: return 3 * 60 * 20;
		case FUEL: return 10 * 60 * 20;
		case GRAPHITE: return 15 * 60 * 20;
		case LID: return 30 * 20;
		case ROD: return 60 * 20;
		default: return 0;
		}
	}
	
	public void setType(DebrisType type) {
		this.dataWatcher.updateObject(20, type.ordinal());
	}
	
	public DebrisType getType() {
		return DebrisType.values()[Math.abs(this.dataWatcher.getWatchableObjectInt(20)) % DebrisType.values().length];
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.dataWatcher.updateObject(20, nbt.getInteger("debtype"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("debtype", this.dataWatcher.getWatchableObjectInt(20));
	}
	
	public static enum DebrisType {
		BLANK,		//just a metal beam
		ELEMENT,	//the entire casing of a fuel assembly because fuck you
		FUEL,		//spicy
		ROD,		//solid boron rod
		GRAPHITE,	//spicy rock
		LID;		//the all destroying harbinger of annihilation
	}
}
