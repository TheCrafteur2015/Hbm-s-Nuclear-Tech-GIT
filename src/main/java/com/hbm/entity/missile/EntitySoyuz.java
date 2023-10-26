package com.hbm.entity.missile;

import java.util.List;

import com.hbm.explosion.ExplosionLarge;
import com.hbm.items.ISatChip;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.saveddata.satellites.Satellite;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class EntitySoyuz extends Entity {

	double acceleration = 0.00D;
	public int mode;
	public int targetX;
	public int targetZ;
	boolean memed = false;

	private ItemStack[] payload;

	public EntitySoyuz(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
        setSize(5.0F, 50.0F);
        this.payload = new ItemStack[18];
	}
	
	@Override
	public void onUpdate() {
		
		if(this.motionY < 2.0D) {
			this.acceleration += 0.00025D;
			this.motionY += this.acceleration;
		}
		
		setLocationAndAngles(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ, 0, 0);
		
		if(!this.worldObj.isRemote) {
			
			List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getBoundingBox(this.posX - 5, this.posY - 15, this.posZ - 5, this.posX + 5, this.posY, this.posZ + 5));
			
			for(Entity e : list) {
				e.setFire(15);
				e.attackEntityFrom(ModDamageSource.exhaust, 100.0F);
				
				if(e instanceof EntityPlayer) {
					if(!this.memed) {
						this.memed = true;
						this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "hbm:alarm.soyuzed", 100, 1.0F);
					}
					
					((EntityPlayer)e).triggerAchievement(MainRegistry.achSoyuz);
				}
			}
		}
		
		if(this.worldObj.isRemote) {
			spawnExhaust(this.posX, this.posY, this.posZ);
			spawnExhaust(this.posX + 2.75, this.posY, this.posZ);
			spawnExhaust(this.posX - 2.75, this.posY, this.posZ);
			spawnExhaust(this.posX, this.posY, this.posZ + 2.75);
			spawnExhaust(this.posX, this.posY, this.posZ - 2.75);
		}
		
		if(this.posY > 600) {
			deployPayload();
		}
	}
	
	private void spawnExhaust(double x, double y, double z) {

		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "exhaust");
		data.setString("mode", "soyuz");
		data.setInteger("count", 1);
		data.setDouble("width", this.worldObj.rand.nextDouble() * 0.25 - 0.5);
		data.setDouble("posX", x);
		data.setDouble("posY", y);
		data.setDouble("posZ", z);
		
		MainRegistry.proxy.effectNT(data);
	}
	
	private void deployPayload() {

		if(this.mode == 0 && this.payload != null) {

			if(this.payload[0] != null) {
				
				ItemStack load = this.payload[0];
				
				if(load.getItem() == ModItems.flame_pony) {
					ExplosionLarge.spawnTracers(this.worldObj, this.posX, this.posY, this.posZ, 25);
					for(Object p : this.worldObj.playerEntities)
						((EntityPlayer)p).triggerAchievement(MainRegistry.achSpace);
				}
				
				if(load.getItem() == ModItems.sat_foeq) {
					for(Object p : this.worldObj.playerEntities)
						((EntityPlayer)p).triggerAchievement(MainRegistry.achFOEQ);
				}
				
				if(load.getItem() instanceof ISatChip) {
					
				    int freq = ISatChip.getFreqS(load);
			    	
			    	Satellite.orbit(this.worldObj, Satellite.getIDFromItem(load.getItem()), freq, this.posX, this.posY, this.posZ);
				}
			}
		}
		
		if(this.mode == 1) {
			
			EntitySoyuzCapsule capsule = new EntitySoyuzCapsule(this.worldObj);
			capsule.payload = this.payload;
			capsule.soyuz = getSkin();
			capsule.setPosition(this.targetX + 0.5, 600, this.targetZ + 0.5);
			
			IChunkProvider provider = this.worldObj.getChunkProvider();
			provider.loadChunk(this.targetX >> 4, this.targetZ >> 4);
			
			this.worldObj.spawnEntityInWorld(capsule);
		}
		
		setDead();
	}

	@Override
	protected void entityInit() {
        this.dataWatcher.addObject(8, 0);
	}
	
	public void setSat(ItemStack stack) {
		this.payload[0] = stack;
	}
	
	public void setPayload(List<ItemStack> payload) {
		
		for(int i = 0; i < payload.size(); i++) {
			this.payload[i] = payload.get(i);
		}
	}
	
	public void setSkin(int i) {
		this.dataWatcher.updateObject(8, i);
	}
	
	public int getSkin() {
		return this.dataWatcher.getWatchableObjectInt(8);
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 500000;
    }

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {

		NBTTagList list = nbt.getTagList("items", 10);
		
		setSkin(nbt.getInteger("skin"));
		this.targetX = nbt.getInteger("targetX");
		this.targetZ = nbt.getInteger("targetZ");
		this.mode = nbt.getInteger("mode");

		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if (b0 >= 0 && b0 < this.payload.length) {
				this.payload[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {

		NBTTagList list = new NBTTagList();

		nbt.setInteger("skin", getSkin());
		nbt.setInteger("targetX", this.targetX);
		nbt.setInteger("targetZ", this.targetZ);
		nbt.setInteger("mode", this.mode);

		for (int i = 0; i < this.payload.length; i++) {
			if (this.payload[i] != null) {
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte) i);
				this.payload[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}
}
