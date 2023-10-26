package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.projectile.EntityArtilleryRocket;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.container.ContainerTurretBase;
import com.hbm.inventory.gui.GUITurretHIMARS;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemAmmoHIMARS;
import com.hbm.items.weapon.ItemAmmoHIMARS.HIMARSRocket;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityTurretHIMARS extends TileEntityTurretBaseArtillery implements IGUIProvider {
	
	public short mode = 0;
	public static final short MODE_AUTO = 0;
	public static final short MODE_MANUAL = 1;
	
	public int typeLoaded = -1;
	public int ammo = 0;
	public float crane;
	public float lastCrane;
	
	@Override
	@SideOnly(Side.CLIENT)
	public List<ItemStack> getAmmoTypesForDisplay() {
		
		if(this.ammoStacks != null)
			return this.ammoStacks;
		
		this.ammoStacks = new ArrayList<>();

		List<ItemStack> list = new ArrayList<>();
		ModItems.ammo_himars.getSubItems(ModItems.ammo_himars, MainRegistry.weaponTab, list);
		this.ammoStacks.addAll(list);
		
		return this.ammoStacks;
	}

	@Override
	protected List<Integer> getAmmoList() {
		return new ArrayList<>();
	}

	@Override
	public String getName() {
		return "container.turretHIMARS";
	}

	@Override
	public long getMaxPower() {
		return 1_000_000;
	}

	@Override
	public double getBarrelLength() {
		return 0.5D;
	}

	@Override
	public double getAcceptableInaccuracy() {
		return 5D; //they're guided missiles so who gives a shit
	}
	
	@Override
	public double getHeightOffset() {
		return 5D;
	}

	@Override
	public double getDecetorRange() {
		return 5000D;
	}
	
	@Override
	public double getDecetorGrace() {
		return 32D;
	}

	@Override
	public double getTurretYawSpeed() {
		return 1D;
	}

	@Override
	public double getTurretPitchSpeed() {
		return 0.5D;
	}

	@Override
	public boolean doLOSCheck() {
		return false;
	}
	
	@Override
	protected void alignTurret() {

		Vec3 pos = getTurretPos();
		
		Vec3 delta = Vec3.createVectorHelper(this.tPos.xCoord - pos.xCoord, this.tPos.yCoord - pos.yCoord, this.tPos.zCoord - pos.zCoord);
		double targetYaw = -Math.atan2(delta.xCoord, delta.zCoord);
		double targetPitch = Math.PI / 4D;
		
		turnTowardsAngle(targetPitch, targetYaw);
	}
	
	public int getSpareRocket() {
		
		for(int i = 1; i < 10; i++) {
			if(this.slots[i] != null) {
				if(this.slots[i].getItem() == ModItems.ammo_himars) {
					return this.slots[i].getItemDamage();
				}
			}
		}
		
		return -1;
	}
	
	@Override
	public void updateEntity() {
		
		this.lastCrane = this.crane;
		
		if(this.mode == TileEntityTurretHIMARS.MODE_MANUAL) {
			if(!this.targetQueue.isEmpty()) {
				this.tPos = this.targetQueue.get(0);
			}
		} else {
			this.targetQueue.clear();
		}
		
		if(this.worldObj.isRemote) {
			this.lastRotationPitch = this.rotationPitch;
			this.lastRotationYaw = this.rotationYaw;
		}

		this.aligned = false;
		
		if(!this.worldObj.isRemote) {
			
			updateConnections();
			
			if(this.target != null && !this.target.isEntityAlive()) {
				this.target = null;
				this.stattrak++;
			}
		}
		
		if(this.target != null && this.mode != TileEntityTurretHIMARS.MODE_MANUAL) {
			if(!entityInLOS(this.target)) {
				this.target = null;
			}
		}
		
		if(!this.worldObj.isRemote) {
			
			if(this.target != null) {
				this.tPos = getEntityPos(this.target);
			} else {
				if(this.mode != TileEntityTurretHIMARS.MODE_MANUAL) {
					this.tPos = null;
				}
			}
		}
		
		if(isOn() && hasPower()) {
			
			if(!hasAmmo() || this.crane > 0) {
				
				turnTowardsAngle(0, this.rotationYaw);
				
				if(this.aligned) {
					
					if(hasAmmo()) {
						this.crane -= 0.0125F;
					} else {
						this.crane += 0.0125F;
						
						if(this.crane >= 1F && !this.worldObj.isRemote) {
							int available = getSpareRocket();
							
							if(available != -1) {
								HIMARSRocket type = ItemAmmoHIMARS.itemTypes[available];
								this.typeLoaded = available;
								this.ammo = type.amount;
								conusmeAmmo(new ComparableStack(ModItems.ammo_himars, 1, available));
							}
						}
					}
				}
				
				this.crane = MathHelper.clamp_float(this.crane, 0F, 1F);
				
			} else {
				
				if(this.tPos != null) {
					alignTurret();
				}
			}
			
		} else {

			this.target = null;
			this.tPos = null;
		}
		
		if(!this.worldObj.isRemote) {
			
			if(this.target != null && !this.target.isEntityAlive()) {
				this.target = null;
				this.tPos = null;
				this.stattrak++;
			}
			
			if(isOn() && hasPower()) {
				this.searchTimer--;
				
				setPower(getPower() - getConsumption());
				
				if(this.searchTimer <= 0) {
					this.searchTimer = getDecetorInterval();
					
					if(this.target == null && this.mode != TileEntityTurretHIMARS.MODE_MANUAL)
						seekNewTarget();
				}
			} else {
				this.searchTimer = 0;
			}
			
			if(this.aligned && this.crane <= 0) {
				updateFiringTick();
			}
			
			this.power = Library.chargeTEFromItems(this.slots, 10, this.power, getMaxPower());
			
			NBTTagCompound data = writePacket();
			networkPack(data, 250);
			
		} else {
			
			Vec3 vec = Vec3.createVectorHelper(getBarrelLength(), 0, 0);
			vec.rotateAroundZ((float) -this.rotationPitch);
			vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
			
			//this will fix the interpolation error when the turret crosses the 360° point
			if(Math.abs(this.lastRotationYaw - this.rotationYaw) > Math.PI) {
				
				if(this.lastRotationYaw < this.rotationYaw)
					this.lastRotationYaw += Math.PI * 2;
				else
					this.lastRotationYaw -= Math.PI * 2;
			}
		}
	}

	@Override
	protected NBTTagCompound writePacket() {
		NBTTagCompound data = super.writePacket();
		data.setShort("mode", this.mode);
		data.setInteger("type", this.typeLoaded);
		data.setInteger("ammo", this.ammo);
		return data;
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		super.networkUnpack(nbt);
		this.mode = nbt.getShort("mode");
		this.typeLoaded = nbt.getShort("type");
		this.ammo = nbt.getInteger("ammo");
	}
	
	public boolean hasAmmo() {
		return this.typeLoaded >= 0 && this.ammo > 0;
	}

	int timer;
	
	@Override
	public void updateFiringTick() {
		
		this.timer++;
		
		int delay = 40;
		
		if(this.timer % delay == 0) {
			
			if(hasAmmo() && this.tPos != null) {
				spawnShell(this.typeLoaded);
				this.ammo--;
				this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:weapon.rocketFlame", 25.0F, 1.0F);
			}
			
			if(this.mode == TileEntityTurretHIMARS.MODE_MANUAL && !this.targetQueue.isEmpty()) {
				this.targetQueue.remove(0);
				this.tPos = null;
			}
		}
	}

	public void spawnShell(int type) {
		
		Vec3 pos = getTurretPos();
		Vec3 vec = Vec3.createVectorHelper(getBarrelLength(), 0, 0);
		vec.rotateAroundZ((float) -this.rotationPitch);
		vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
		
		EntityArtilleryRocket proj = new EntityArtilleryRocket(this.worldObj);
		proj.setPositionAndRotation(pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord, 0.0F, 0.0F);
		proj.setThrowableHeading(vec.xCoord, vec.yCoord, vec.zCoord, 25F, 0.0F);
		
		if(this.target != null)
			proj.setTarget(this.target);
		else
			proj.setTarget(this.tPos.xCoord, this.tPos.yCoord, this.tPos.zCoord);
		
		proj.setType(type);
		
		this.worldObj.spawnEntityInWorld(proj);
	}

	@Override
	public void handleButtonPacket(int value, int meta) {
		if(meta == 5) {
			this.mode++;
			if(this.mode > 1)
				this.mode = 0;
			
			this.tPos = null;
			this.targetQueue.clear();
			
		} else{
			super.handleButtonPacket(value, meta);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.mode = nbt.getShort("mode");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setShort("mode", this.mode);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerTurretBase(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUITurretHIMARS(player.inventory, this);
	}
}
