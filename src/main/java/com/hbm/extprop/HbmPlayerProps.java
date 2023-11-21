package com.hbm.extprop;

import com.hbm.entity.train.EntityRailCarBase;
import com.hbm.handler.HbmKeybinds.EnumKeybind;
import com.hbm.main.MainRegistry;
import com.hbm.main.ServerProxy;
import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class HbmPlayerProps implements IExtendedEntityProperties {
	
	public static final String key = "NTM_EXT_PLAYER";
	public EntityPlayer player;
	
	public boolean hasReceivedBook = false;
	
	public boolean enableHUD = true;
	public boolean enableBackpack = true;
	
	private boolean[] keysPressed = new boolean[EnumKeybind.values().length];
	
	public boolean dashActivated = true;
	
	public static final int dashCooldownLength = 5;
	public int dashCooldown = 0;
	
	public int totalDashCount = 0;
	public int stamina = 0;
	
	public static final int plinkCooldownLength = 10;
	public int plinkCooldown = 0;
	
	public float shield = 0;
	public float maxShield = 0;
	public int lastDamage = 0;
	public static final float shieldCap = 100;
	
	public int reputation;
	
	public HbmPlayerProps(EntityPlayer player) {
		this.player = player;
	}
	
	public static HbmPlayerProps registerData(EntityPlayer player) {
		player.registerExtendedProperties(HbmPlayerProps.key, new HbmPlayerProps(player));
		return (HbmPlayerProps) player.getExtendedProperties(HbmPlayerProps.key);
	}
	
	public static HbmPlayerProps getData(EntityPlayer player) {
		HbmPlayerProps props = (HbmPlayerProps) player.getExtendedProperties(HbmPlayerProps.key);
		return props != null ? props : HbmPlayerProps.registerData(player);
	}
	
	public boolean getKeyPressed(EnumKeybind key) {
		return this.keysPressed[key.ordinal()];
	}
	
	public boolean isJetpackActive() {
		return this.enableBackpack && getKeyPressed(EnumKeybind.JETPACK);
	}
	
	public void setKeyPressed(EnumKeybind key, boolean pressed) {
		
		if(!getKeyPressed(key) && pressed) {
			
			if(key == EnumKeybind.TOGGLE_JETPACK) {
				
				if(!this.player.worldObj.isRemote) {
					this.enableBackpack = !this.enableBackpack;
					
					if(this.enableBackpack)
						MainRegistry.proxy.displayTooltip(EnumChatFormatting.GREEN + "Jetpack ON", ServerProxy.ID_JETPACK);
					else
						MainRegistry.proxy.displayTooltip(EnumChatFormatting.RED + "Jetpack OFF", ServerProxy.ID_JETPACK);
				}
			}
			if(key == EnumKeybind.TOGGLE_HEAD) {
				
				if(!this.player.worldObj.isRemote) {
					this.enableHUD = !this.enableHUD;
					
					if(this.enableHUD)
						MainRegistry.proxy.displayTooltip(EnumChatFormatting.GREEN + "HUD ON", ServerProxy.ID_HUD);
					else
						MainRegistry.proxy.displayTooltip(EnumChatFormatting.RED + "HUD OFF", ServerProxy.ID_HUD);
				}
			}
			
			if(key == EnumKeybind.TRAIN) {
				
				if(!this.player.worldObj.isRemote) {

					if(this.player.ridingEntity != null && this.player.ridingEntity instanceof EntityRailCarBase && this.player.ridingEntity instanceof IGUIProvider) {
						FMLNetworkHandler.openGui(this.player, MainRegistry.instance, 0, this.player.worldObj, this.player.ridingEntity.getEntityId(), 0, 0);
					}
				}
			}
		}
		
		this.keysPressed[key.ordinal()] = pressed;
	}
	
	public void setDashCooldown(int cooldown) {
		this.dashCooldown = cooldown;
		return;
	}
	
	public int getDashCooldown() {
		return this.dashCooldown;
	}
	
	public void setStamina(int stamina) {
		this.stamina = stamina;
		return;
	}
	
	public int getStamina() {
		return this.stamina;
	}
	
	public void setDashCount(int count) {
		this.totalDashCount = count;
		return;
	}
	
	public int getDashCount() {
		return this.totalDashCount;
	}
	
	public static void plink(EntityPlayer player, String sound, float volume, float pitch) {
		HbmPlayerProps props = HbmPlayerProps.getData(player);
		
		if(props.plinkCooldown <= 0) {
			player.worldObj.playSoundAtEntity(player, sound, volume, pitch);
			props.plinkCooldown = HbmPlayerProps.plinkCooldownLength;
		}
	}
	
	public float getMaxShield() {
		return this.maxShield;
	}

	@Override
	public void init(Entity entity, World world) { }

	@Override
	public void saveNBTData(NBTTagCompound nbt) {
		
		NBTTagCompound props = new NBTTagCompound();
		
		props.setBoolean("hasReceivedBook", hasReceivedBook);
		props.setFloat("shield", shield);
		props.setFloat("maxShield", maxShield);
		props.setBoolean("enableBackpack", enableBackpack);
		props.setBoolean("enableHUD", enableHUD);
		props.setInteger("reputation", reputation);
		
		nbt.setTag("HbmPlayerProps", props);
	}

	@Override
	public void loadNBTData(NBTTagCompound nbt) {
		
		NBTTagCompound props = (NBTTagCompound) nbt.getTag("HbmPlayerProps");
		
		if(props != null) {
			this.hasReceivedBook = props.getBoolean("hasReceivedBook");
			this.shield = props.getFloat("shield");
			this.maxShield = props.getFloat("maxShield");
			this.enableBackpack = props.getBoolean("enableBackpack");
			this.enableHUD = props.getBoolean("enableHUD");
			this.reputation = props.getInteger("reputation");
		}
	}
}
