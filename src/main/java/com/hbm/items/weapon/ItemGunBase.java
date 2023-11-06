package com.hbm.items.weapon;

import java.util.List;

import org.lwjgl.input.Mouse;

import com.hbm.config.GeneralConfig;
import com.hbm.entity.projectile.EntityBulletBaseNT;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.CasingEjector;
import com.hbm.handler.GunConfiguration;
import com.hbm.handler.HbmKeybinds;
import com.hbm.interfaces.IHoldableWeapon;
import com.hbm.interfaces.IItemHUD;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.IEquipReceiver;
import com.hbm.items.ModItems;
import com.hbm.items.armor.ArmorFSB;
import com.hbm.lib.HbmCollection;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.GunAnimationPacket;
import com.hbm.packet.GunButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.util.RenderScreenOverlay;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;
import com.hbm.util.I18nUtil;
import com.hbm.util.InventoryUtil;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class ItemGunBase extends Item implements IHoldableWeapon, IItemHUD, IEquipReceiver {

	public GunConfiguration mainConfig;
	public GunConfiguration altConfig;
	
	@SideOnly(Side.CLIENT)
	public boolean m1;// = false;
	@SideOnly(Side.CLIENT)
	public boolean m2;// = false;

	public ItemGunBase(GunConfiguration config) {
		this.mainConfig = config;
		setMaxStackSize(1);
	}
	
	public ItemGunBase(GunConfiguration config, GunConfiguration alt) {
		this(config);
		this.altConfig = alt;
	}

	@Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isCurrentItem) {
		
		if(entity instanceof EntityPlayer) {
			
			isCurrentItem = ((EntityPlayer)entity).getHeldItem() == stack;
			
			if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT && world.isRemote) {
				updateClient(stack, world, (EntityPlayer)entity, slot, isCurrentItem);
			} else {
				updateServer(stack, world, (EntityPlayer)entity, slot, isCurrentItem);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	protected void updateClient(ItemStack stack, World world, EntityPlayer entity, int slot, boolean isCurrentItem) {
		
		if(!world.isRemote)
			return;
		
		boolean clickLeft = Mouse.isButtonDown(0);
		boolean clickRight = Mouse.isButtonDown(1);
		boolean left = this.m1;
		boolean right = this.m2;
		
		if(isCurrentItem) {
			if(left && right) {
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 0));
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 1));
				this.m1 = false;
				this.m2 = false;
			}
			
			if(left && !clickLeft) {
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 0));
				this.m1 = false;
				endActionClient(stack, world, entity, true);
			}
			
			if(right && !clickRight) {
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 1));
				this.m2 = false;
				endActionClient(stack, world, entity, false);
			}
			
			if(this.mainConfig.reloadType != GunConfiguration.RELOAD_NONE || (this.altConfig != null && this.altConfig.reloadType != 0)) {
				
				if(GameSettings.isKeyDown(HbmKeybinds.reloadKey) && Minecraft.getMinecraft().currentScreen == null && (ItemGunBase.getMag(stack) < this.mainConfig.ammoCap || hasInfinity(stack, this.mainConfig))) {
					PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(true, (byte) 2));
					ItemGunBase.setIsReloading(stack, true);
					ItemGunBase.resetReloadCycle(entity, stack);
				}
			}
		}
	}
	
	protected void updateServer(ItemStack stack, World world, EntityPlayer player, int slot, boolean isCurrentItem) {
		
		if(ItemGunBase.getDelay(stack) > 0 && isCurrentItem)
			ItemGunBase.setDelay(stack, ItemGunBase.getDelay(stack) - 1);

		if(ItemGunBase.getIsMouseDown(stack) && !(player.getHeldItem() == stack)) {
			ItemGunBase.setIsMouseDown(stack, false);
		}

		int burstDuration = ItemGunBase.getBurstDuration(stack);
		if(burstDuration > 0) {
			
			if(this.altConfig == null) {
				if (burstDuration % this.mainConfig.firingDuration == 0 && tryShoot(stack, world, player, true)) {
					fire(stack, world, player);
				}
			} else {
				boolean canFire = this.altConfig.firingDuration == 1 ||  burstDuration % this.altConfig.firingDuration == 0;
				if (canFire && tryShoot(stack, world, player, false)) {
					altFire(stack, world, player);
				}
			}

			ItemGunBase.setBurstDuration(stack, ItemGunBase.getBurstDuration(stack) - 1);
			if(ItemGunBase.getBurstDuration(stack) == 0) ItemGunBase.setDelay(stack, this.mainConfig.rateOfFire);
		}
		if(ItemGunBase.getIsAltDown(stack) && !isCurrentItem) {
			ItemGunBase.setIsAltDown(stack, false);
		}
			
		if(GeneralConfig.enableGuns && this.mainConfig.firingMode == 1 && ItemGunBase.getIsMouseDown(stack) && tryShoot(stack, world, player, isCurrentItem)) {
			fire(stack, world, player);
			ItemGunBase.setDelay(stack, this.mainConfig.rateOfFire);
		}
		
		if(ItemGunBase.getIsReloading(stack) && isCurrentItem) {
			reload2(stack, world, player);
		}
		
		BulletConfiguration queued = ItemGunBase.getCasing(stack);
		int timer = ItemGunBase.getCasingTimer(stack);
		
		if(queued != null && timer > 0) {
			
			timer--;
			
			if(timer <= 0) {
				ItemGunBase.trySpawnCasing(player, this.mainConfig.ejector, queued, stack);
			}
			
			ItemGunBase.setCasingTimer(stack, timer);
		}
	}
	
	//whether or not the gun can shoot in its current state
	protected boolean tryShoot(ItemStack stack, World world, EntityPlayer player, boolean main) {
		
		//cancel reload when trying to shoot if it's a single reload weapon and at least one round is loaded
		if(ItemGunBase.getIsReloading(stack) && this.mainConfig.reloadType == GunConfiguration.RELOAD_SINGLE && ItemGunBase.getMag(stack) > 0) {
			ItemGunBase.setReloadCycle(stack, 0);
			ItemGunBase.setIsReloading(stack, false);
		}
		
		if(main && ItemGunBase.getDelay(stack) == 0 && !ItemGunBase.getIsReloading(stack) && ItemGunBase.getItemWear(stack) < this.mainConfig.durability) {
			return hasAmmo(stack, player, main);
		}
		
		if(!main && this.altConfig != null && ItemGunBase.getDelay(stack) == 0 && !ItemGunBase.getIsReloading(stack) && ItemGunBase.getItemWear(stack) < this.mainConfig.durability) {
			
			return hasAmmo(stack, player, false);
		}
		
		return false;
	}
	
	public boolean hasAmmo(ItemStack stack, EntityPlayer player, boolean main) {
		
		GunConfiguration config = this.mainConfig;
		
		if(!main)
			config = this.altConfig;
		
		if(config.reloadType == GunConfiguration.RELOAD_NONE) {
			return ItemGunBase.getBeltSize(player, ItemGunBase.getBeltType(player, stack, main)) > 0;
			
		} else {
			//return getMag(stack) >= 0 + config.roundsPerCycle;
			return ItemGunBase.getMag(stack) > 0;
		}
	}
	
	//called every time the gun shoots successfully, calls spawnProjectile(), sets item wear
	protected void fire(ItemStack stack, World world, EntityPlayer player) {

		BulletConfiguration config = null;
		
		if(this.mainConfig.reloadType == GunConfiguration.RELOAD_NONE) {
			config = ItemGunBase.getBeltCfg(player, stack, true);
		} else {
			config = BulletConfigSyncingUtil.pullConfig(this.mainConfig.config.get(ItemGunBase.getMagType(stack)));
		}
		
		int bullets = config.bulletsMin;
		
		for(int k = 0; k < this.mainConfig.roundsPerCycle; k++) {
			
			if(!hasAmmo(stack, player, true))
				break;
			
			if(config.bulletsMax > config.bulletsMin)
				bullets += world.rand.nextInt(config.bulletsMax - config.bulletsMin);
			
			for(int i = 0; i < bullets; i++) {
				spawnProjectile(world, player, stack, BulletConfigSyncingUtil.getKey(config));
			}
			
			useUpAmmo(player, stack, true);
			player.inventoryContainer.detectAndSendChanges();
			
			int wear = (int) Math.ceil(config.wear / (1F + EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack)));
			ItemGunBase.setItemWear(stack, ItemGunBase.getItemWear(stack) + wear);
		}
		
		world.playSoundAtEntity(player, this.mainConfig.firingSound, this.mainConfig.firingVolume, this.mainConfig.firingPitch);
		
		if(this.mainConfig.ejector != null && !this.mainConfig.ejector.getAfterReload())
			ItemGunBase.queueCasing(player, this.mainConfig.ejector, config, stack);
	}
	
	//unlike fire(), being called does not automatically imply success, some things may still have to be handled before spawning the projectile
	protected void altFire(ItemStack stack, World world, EntityPlayer player) {
		
		if(this.altConfig == null)
			return;

		BulletConfiguration config = this.altConfig.reloadType == GunConfiguration.RELOAD_NONE ? ItemGunBase.getBeltCfg(player, stack, false) : BulletConfigSyncingUtil.pullConfig(this.altConfig.config.get(ItemGunBase.getMagType(stack)));
		
		int bullets = config.bulletsMin;
		
		for(int k = 0; k < this.altConfig.roundsPerCycle; k++) {
			
			if(this.altConfig.reloadType != GunConfiguration.RELOAD_NONE && !hasAmmo(stack, player, true))
				break;
			
			if(config.bulletsMax > config.bulletsMin)
				bullets += world.rand.nextInt(config.bulletsMax - config.bulletsMin);
			
			for(int i = 0; i < bullets; i++) {
				spawnProjectile(world, player, stack, BulletConfigSyncingUtil.getKey(config));
			}
			
			useUpAmmo(player, stack, false);
			player.inventoryContainer.detectAndSendChanges();
			
			ItemGunBase.setItemWear(stack, ItemGunBase.getItemWear(stack) + config.wear);
		}
		
		world.playSoundAtEntity(player, this.altConfig.firingSound, this.mainConfig.firingVolume, this.altConfig.firingPitch);
		
		if(this.altConfig.ejector != null)
			ItemGunBase.queueCasing(player, this.altConfig.ejector, config, stack);
	}
	
	//spawns the actual projectile, can be overridden to change projectile entity
	protected void spawnProjectile(World world, EntityPlayer player, ItemStack stack, int config) {
		
		EntityBulletBaseNT bullet = new EntityBulletBaseNT(world, config, player);
		world.spawnEntityInWorld(bullet);
		
		if(player instanceof EntityPlayerMP)
			PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(AnimType.CYCLE.ordinal()), (EntityPlayerMP) player);
			
	}
	
	//called on click (server side, called by mouse packet) for semi-automatics and specific events
	public void startAction(ItemStack stack, World world, EntityPlayer player, boolean main) {

		boolean validConfig = this.mainConfig.firingMode == GunConfiguration.FIRE_MANUAL || this.mainConfig.firingMode == GunConfiguration.FIRE_BURST;

		if(validConfig && main && tryShoot(stack, world, player, main)) {

			if(this.mainConfig.firingMode == GunConfiguration.FIRE_BURST){
				if(ItemGunBase.getBurstDuration(stack) <= 0)
					ItemGunBase.setBurstDuration(stack,this.mainConfig.firingDuration * this.mainConfig.roundsPerBurst);
			} else {
				fire(stack, world, player);
				ItemGunBase.setDelay(stack, this.mainConfig.rateOfFire);
			}

			//setMag(stack, getMag(stack) - 1);
			//useUpAmmo(player, stack, main);
			//player.inventoryContainer.detectAndSendChanges();
		}
		
		if(!main && this.altConfig != null && tryShoot(stack, world, player, main)) {

			if(this.altConfig.firingMode == GunConfiguration.FIRE_BURST && ItemGunBase.getBurstDuration(stack) <= 0){
				ItemGunBase.setBurstDuration(stack,this.altConfig.firingDuration * this.altConfig.roundsPerBurst);
			} else {
				altFire(stack, world, player);
				ItemGunBase.setDelay(stack, this.altConfig.rateOfFire);
			}

			//useUpAmmo(player, stack, main);
			//player.inventoryContainer.detectAndSendChanges();
		}
	}
	
	//called on click (client side, called by mouse click event)
	public void startActionClient(ItemStack stack, World world, EntityPlayer player, boolean main) { }
	
	//called on click release (server side, called by mouse packet) for release actions like charged shots
	public void endAction(ItemStack stack, World world, EntityPlayer player, boolean main) { }
	
	//called on click release (client side, called by update cycle)
	public void endActionClient(ItemStack stack, World world, EntityPlayer player, boolean main) { }
	
	//current reload
	protected void reload2(ItemStack stack, World world, EntityPlayer player) {
		
		if(ItemGunBase.getMag(stack) >= this.mainConfig.ammoCap) {
			ItemGunBase.setIsReloading(stack, false);
			return;
		}
		
		if(ItemGunBase.getReloadCycle(stack) <= 0) {
			
			BulletConfiguration prevCfg = BulletConfigSyncingUtil.pullConfig(this.mainConfig.config.get(ItemGunBase.getMagType(stack)));
			
			if(ItemGunBase.getMag(stack) == 0)
				resetAmmoType(stack, world, player);
			
			BulletConfiguration cfg = BulletConfigSyncingUtil.pullConfig(this.mainConfig.config.get(ItemGunBase.getMagType(stack)));
			ComparableStack ammo = (ComparableStack) cfg.ammo.copy();
			
			final int countNeeded = (this.mainConfig.reloadType == GunConfiguration.RELOAD_FULL) ? this.mainConfig.ammoCap - ItemGunBase.getMag(stack) : 1;
			final int availableStacks = InventoryUtil.countAStackMatches(player, ammo, true);
			final int availableFills = availableStacks * cfg.ammoCount;
			final boolean hasLoaded = availableFills > 0;
			final int toAdd = Math.min(availableFills * cfg.ammoCount, countNeeded);
			final int toConsume = (int) Math.ceil((double) toAdd / cfg.ammoCount);
			
			// Skip logic if cannot reload
			if(availableFills == 0) {
				ItemGunBase.setIsReloading(stack, false);
				return;
			}
			
			ammo.stacksize = toConsume;
			ItemGunBase.setMag(stack, ItemGunBase.getMag(stack) + toAdd);
			if (ItemGunBase.getMag(stack) >= this.mainConfig.ammoCap)
				ItemGunBase.setIsReloading(stack, false);
			else
				ItemGunBase.resetReloadCycle(player, stack);
			
			if(hasLoaded && this.mainConfig.reloadSoundEnd)
				world.playSoundAtEntity(player, this.mainConfig.reloadSound, 1.0F, 1.0F);
			
			if(this.mainConfig.ejector != null && this.mainConfig.ejector.getAfterReload())
				ItemGunBase.queueCasing(player, this.mainConfig.ejector, prevCfg, stack);
			
			InventoryUtil.tryConsumeAStack(player.inventory.mainInventory, 0, player.inventory.mainInventory.length - 1, ammo);
		} else {
			ItemGunBase.setReloadCycle(stack, ItemGunBase.getReloadCycle(stack) - 1);
		}
		
		if(stack != player.getHeldItem()) {
			ItemGunBase.setReloadCycle(stack, 0);
			ItemGunBase.setIsReloading(stack, false);
		}
	}
	
	//initiates a reload
	public void startReloadAction(ItemStack stack, World world, EntityPlayer player) {
		
		if(player.isSneaking() && hasInfinity(stack, this.mainConfig)) {
			
			if(ItemGunBase.getMag(stack) == this.mainConfig.ammoCap) {
				ItemGunBase.setMag(stack, 0);
				resetAmmoType(stack, world, player);
				world.playSoundAtEntity(player, "tile.piston.out", 1.0F, 1.0F);
			}
			
			return;
		}
		
		if((ItemGunBase.getMag(stack) == this.mainConfig.ammoCap) || ItemGunBase.getIsReloading(stack))
			return;
		
		if(!this.mainConfig.reloadSoundEnd)
			world.playSoundAtEntity(player, this.mainConfig.reloadSound, 1.0F, 1.0F);
		
		if(!world.isRemote)
			PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(AnimType.RELOAD.ordinal()), (EntityPlayerMP) player);
		
		ItemGunBase.setIsReloading(stack, true);
		ItemGunBase.resetReloadCycle(player, stack);
	}
	
	public boolean canReload(ItemStack stack, World world, EntityPlayer player) {
		
		if(ItemGunBase.getMag(stack) == this.mainConfig.ammoCap && hasInfinity(stack, this.mainConfig))
			return true;

		if(ItemGunBase.getMag(stack) == 0) {
			
			for(int config : this.mainConfig.config) {
				if(InventoryUtil.doesPlayerHaveAStack(player, BulletConfigSyncingUtil.pullConfig(config).ammo, false, false)) {
					return true;
				}
			}
			
		} else {
			ComparableStack ammo = BulletConfigSyncingUtil.pullConfig(this.mainConfig.config.get(ItemGunBase.getMagType(stack))).ammo;
			return InventoryUtil.doesPlayerHaveAStack(player, ammo, false, false);
		}
		
		return false;
	}
	
	//searches the player's inv for next fitting ammo type and changes the gun's mag
	protected void resetAmmoType(ItemStack stack, World world, EntityPlayer player) {

		for(int config : this.mainConfig.config) {
			BulletConfiguration cfg = BulletConfigSyncingUtil.pullConfig(config);
			
			if(InventoryUtil.doesPlayerHaveAStack(player, cfg.ammo, false, false)) {
				ItemGunBase.setMagType(stack, this.mainConfig.config.indexOf(config));
				break;
			}
		}
	}
	
	//item mouseover text
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		ComparableStack ammo = BulletConfigSyncingUtil.pullConfig(this.mainConfig.config.get(ItemGunBase.getMagType(stack))).ammo;
		
		list.add(I18nUtil.resolveKey(HbmCollection.ammo, this.mainConfig.ammoCap > 0 ? I18nUtil.resolveKey(HbmCollection.ammoMag, ItemGunBase.getMag(stack), this.mainConfig.ammoCap) : I18nUtil.resolveKey(HbmCollection.ammoBelt)));
		
		try {
			list.add(I18nUtil.resolveKey(HbmCollection.ammoType, ammo.toStack().getDisplayName()));

			if(this.altConfig != null && this.altConfig.ammoCap == 0) {
				ComparableStack ammo2 = BulletConfigSyncingUtil.pullConfig(this.altConfig.config.get(0)).ammo;
				if(!ammo.isApplicable(ammo2)) {
					list.add(I18nUtil.resolveKey(HbmCollection.altAmmoType, ammo2.toStack().getDisplayName()));
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			list.add("Error: " + e + " has occurred!");
		}

		addAdditionalInformation(stack, list);
	}
	
	protected void addAdditionalInformation(ItemStack stack, List<String> list)
	{
		final BulletConfiguration bulletConfig = BulletConfigSyncingUtil.pullConfig(this.mainConfig.config.get(ItemGunBase.getMagType(stack)));
		list.add(I18nUtil.resolveKey(HbmCollection.gunDamage, bulletConfig.dmgMin, bulletConfig.dmgMax));
		if(bulletConfig.bulletsMax != 1)
			list.add(I18nUtil.resolveKey(HbmCollection.gunPellets, bulletConfig.bulletsMin, bulletConfig.bulletsMax));
		int dura = Math.max(this.mainConfig.durability - ItemGunBase.getItemWear(stack), 0);
		
		list.add(I18nUtil.resolveKey(HbmCollection.durability, dura + " / " + this.mainConfig.durability));
		
		list.add("");
		String unloc = "gun.name." + this.mainConfig.name;
		String loc = I18nUtil.resolveKey(unloc);
		list.add(I18nUtil.resolveKey(HbmCollection.gunName, unloc.equals(loc) ? this.mainConfig.name : loc));
		list.add(I18nUtil.resolveKey(HbmCollection.gunMaker, I18nUtil.resolveKey(this.mainConfig.manufacturer.getKey())));
		
		if(!this.mainConfig.comment.isEmpty()) {
			list.add("");
			for(String s : this.mainConfig.comment)
				list.add(EnumChatFormatting.ITALIC + s);
		}
		if(GeneralConfig.enableExtendedLogging) {
			list.add("");
			list.add("Type: " + ItemGunBase.getMagType(stack));
			list.add("Is Reloading: " + ItemGunBase.getIsReloading(stack));
			list.add("Reload Cycle: " + ItemGunBase.getReloadCycle(stack));
			list.add("RoF Cooldown: " + ItemGunBase.getDelay(stack));
		}
	}
	
	//returns ammo item of belt-weapons
	public static ComparableStack getBeltType(EntityPlayer player, ItemStack stack, boolean main) {
		ItemGunBase gun = (ItemGunBase)stack.getItem();
		GunConfiguration guncfg = main ? gun.mainConfig : (gun.altConfig != null ? gun.altConfig : gun.mainConfig);

		for(Integer config : guncfg.config) {
			
			BulletConfiguration cfg = BulletConfigSyncingUtil.pullConfig(config);
			
			if(InventoryUtil.doesPlayerHaveAStack(player, cfg.ammo, false, true)) {
				return cfg.ammo;
			}
		}
		
		return BulletConfigSyncingUtil.pullConfig(guncfg.config.get(0)).ammo;
	}
	
	//returns BCFG of belt-weapons
	public static BulletConfiguration getBeltCfg(EntityPlayer player, ItemStack stack, boolean main) {
		ItemGunBase gun = (ItemGunBase)stack.getItem();
		GunConfiguration guncfg = main ? gun.mainConfig : (gun.altConfig != null ? gun.altConfig : gun.mainConfig);
		ItemGunBase.getBeltType(player, stack, main);
	
		for(int config : guncfg.config) {
			
			BulletConfiguration cfg = BulletConfigSyncingUtil.pullConfig(config);
			
			if(InventoryUtil.doesPlayerHaveAStack(player, cfg.ammo, false, false)) {
				return cfg;
			}
		}
	
		return BulletConfigSyncingUtil.pullConfig(guncfg.config.get(0));
	}

	//returns ammo capacity of belt-weapons for current ammo
	public static int getBeltSize(EntityPlayer player, ComparableStack ammo) {
		
		int amount = 0;
		
		for(ItemStack stack : player.inventory.mainInventory) {
			if(stack != null && ammo.matchesRecipe(stack, true)) {
				amount += stack.stackSize;
			}
		}
		
		return amount;
	}
	
	//reduces ammo count for mag and belt-based weapons, should be called AFTER firing
	public void useUpAmmo(EntityPlayer player, ItemStack stack, boolean main) {
		
		if(!main && this.altConfig == null)
			return;
		
		GunConfiguration config = this.mainConfig;
		
		if(!main)
			config = this.altConfig;
		
		if(hasInfinity(stack, config) || (ItemGunBase.isTrenchMaster(player) && player.getRNG().nextInt(3) == 0)) return;

		if(config.reloadType != GunConfiguration.RELOAD_NONE) {
			ItemGunBase.setMag(stack, ItemGunBase.getMag(stack) - 1);
		} else {
			InventoryUtil.doesPlayerHaveAStack(player, ItemGunBase.getBeltType(player, stack, main), true, false);
		}
	}
	
	public boolean hasInfinity(ItemStack stack, GunConfiguration config) {
		return config.allowsInfinity && EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
	}
	
	/// sets reload cycle to config defult ///
	public static void resetReloadCycle(EntityPlayer player, ItemStack stack) {
		ItemGunBase.writeNBT(stack, "reload", ItemGunBase.getReloadDuration(player, stack));
	}
	
	/// if reloading routine is active ///
	public static void setIsReloading(ItemStack stack, boolean b) {
		ItemGunBase.writeNBT(stack, "isReloading", b ? 1 : 0);
	}
	
	public static boolean getIsReloading(ItemStack stack) {
		return ItemGunBase.readNBT(stack, "isReloading") == 1;
	}
	
	/// if left mouse button is down ///
	public static void setIsMouseDown(ItemStack stack, boolean b) {
		ItemGunBase.writeNBT(stack, "isMouseDown", b ? 1 : 0);
	}
	
	public static boolean getIsMouseDown(ItemStack stack) {
		return ItemGunBase.readNBT(stack, "isMouseDown") == 1;
	}
	
	/// if alt mouse button is down ///
	public static void setIsAltDown(ItemStack stack, boolean b) {
		ItemGunBase.writeNBT(stack, "isAltDown", b ? 1 : 0);
	}
	
	public static boolean getIsAltDown(ItemStack stack) {
		return ItemGunBase.readNBT(stack, "isAltDown") == 1;
	}
	
	/// RoF cooldown ///
	public static void setDelay(ItemStack stack, int i) {
		ItemGunBase.writeNBT(stack, "dlay", i);
	}
	
	public static int getDelay(ItemStack stack) {
		return ItemGunBase.readNBT(stack, "dlay");
	}
	
	/// Gun wear ///
	public static void setItemWear(ItemStack stack, int i) {
		ItemGunBase.writeNBT(stack, "wear", i);
	}
	
	public static int getItemWear(ItemStack stack) {
		return ItemGunBase.readNBT(stack, "wear");
	}
	
	/// R/W cycle animation timer ///
	public static void setCycleAnim(ItemStack stack, int i) {
		ItemGunBase.writeNBT(stack, "cycle", i);
	}
	
	public static int getCycleAnim(ItemStack stack) {
		return ItemGunBase.readNBT(stack, "cycle");
	}
	
	/// R/W reload animation timer ///
	public static void setReloadCycle(ItemStack stack, int i) {
		ItemGunBase.writeNBT(stack, "reload", i);
	}
	
	public static int getReloadCycle(ItemStack stack) {
		return ItemGunBase.readNBT(stack, "reload");
	}
	
	/// magazine capacity ///
	public static void setMag(ItemStack stack, int i) {
		ItemGunBase.writeNBT(stack, "magazine", i);
	}
	
	public static int getMag(ItemStack stack) {
		return ItemGunBase.readNBT(stack, "magazine");
	}
	
	/// magazine type (int specified by index in bullet config list) ///
	public static void setMagType(ItemStack stack, int i) {
		ItemGunBase.writeNBT(stack, "magazineType", i);
	}
	
	public static int getMagType(ItemStack stack) {
		return ItemGunBase.readNBT(stack, "magazineType");
	}
	/// Sets how long a burst fires for, only useful for burst fire weapons ///
	public static void setBurstDuration(ItemStack stack, int i) {
		ItemGunBase.writeNBT(stack, "bduration", i);
	}

	public static int getBurstDuration(ItemStack stack) {
		return ItemGunBase.readNBT(stack, "bduration");
	}
	
	/// queued casing for ejection ///
	public static void setCasing(ItemStack stack, BulletConfiguration bullet) {
		ItemGunBase.writeNBT(stack, "casing", BulletConfigSyncingUtil.getKey(bullet));
	}
	
	public static BulletConfiguration getCasing(ItemStack stack) {
		return BulletConfigSyncingUtil.pullConfig(ItemGunBase.readNBT(stack, "casing"));
	}
	
	/// timer for ejecting casing ///
	public static void setCasingTimer(ItemStack stack, int i) {
		ItemGunBase.writeNBT(stack, "casingTimer", i);
	}
	
	public static int getCasingTimer(ItemStack stack) {
		return ItemGunBase.readNBT(stack, "casingTimer");
	}
	
	/// NBT utility ///
	public static void writeNBT(ItemStack stack, String key, int value) {
		
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.stackTagCompound.setInteger(key, value);
	}
	
	public static int readNBT(ItemStack stack, String key) {
		
		if(!stack.hasTagCompound())
			return 0;
		
		return stack.stackTagCompound.getInteger(key);
	}

	@Override
	public Crosshair getCrosshair() {
		return this.mainConfig.crosshair;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderHUD(Pre event, ElementType type, EntityPlayer player, ItemStack stack) {
		
		ItemGunBase gun = ((ItemGunBase)stack.getItem());
		GunConfiguration gcfg = gun.mainConfig;
		
		if(type == ElementType.HOTBAR) {
			int mag = ItemGunBase.getMagType(stack);
			if(gun.mainConfig.config.size() == 0) return;
			BulletConfiguration bcfg = BulletConfigSyncingUtil.pullConfig(gun.mainConfig.config.get(mag < gun.mainConfig.config.size() ? mag : 0));
			
			if(bcfg == null) {
				return;
			}
			
			ComparableStack ammo = bcfg.ammo;
			int count = ItemGunBase.getMag(stack);
			int max = gcfg.ammoCap;
			boolean showammo = gcfg.showAmmo;
			
			if(gcfg.reloadType == GunConfiguration.RELOAD_NONE) {
				ammo = ItemGunBase.getBeltType(player, stack, true);
				count = ItemGunBase.getBeltSize(player, ammo);
				max = -1;
			}
			
			int dura = ItemGunBase.getItemWear(stack) * 50 / gcfg.durability;
			
			RenderScreenOverlay.renderAmmo(event.resolution, Minecraft.getMinecraft().ingameGUI, ammo.toStack(), count, max, dura, showammo);
			
			if(gun.altConfig != null && gun.altConfig.reloadType == GunConfiguration.RELOAD_NONE) {
				ComparableStack oldAmmo = ammo;
				ammo = ItemGunBase.getBeltType(player, stack, false);
				
				if(!ammo.isApplicable(oldAmmo)) {
					count = ItemGunBase.getBeltSize(player, ammo);
					RenderScreenOverlay.renderAmmoAlt(event.resolution, Minecraft.getMinecraft().ingameGUI, ammo.toStack(), count);
				}
			}
		}
		
		if(type == ElementType.CROSSHAIRS && GeneralConfig.enableCrosshairs) {

			event.setCanceled(true);
			
			if(!(gcfg.hasSights && player.isSneaking()))
				RenderScreenOverlay.renderCustomCrosshairs(event.resolution, Minecraft.getMinecraft().ingameGUI, ((IHoldableWeapon)player.getHeldItem().getItem()).getCrosshair());
			else
				RenderScreenOverlay.renderCustomCrosshairs(event.resolution, Minecraft.getMinecraft().ingameGUI, Crosshair.NONE);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public BusAnimation getAnimation(ItemStack stack, AnimType type) {
		GunConfiguration config = ((ItemGunBase) stack.getItem()).mainConfig;
		return config.animations.get(type);
	}
	
	@Override
	public void onEquip(EntityPlayer player) {
		if(!this.mainConfig.equipSound.isEmpty() && !player.worldObj.isRemote) {
			player.worldObj.playSoundAtEntity(player, this.mainConfig.equipSound, 1, 1);
		}
	}
	
	protected static void queueCasing(Entity entity, CasingEjector ejector, BulletConfiguration bullet, ItemStack stack) {
		
		if(ejector == null || bullet == null || bullet.spentCasing == null) return;
		
		if(ejector.getDelay() <= 0) {
			ItemGunBase.trySpawnCasing(entity, ejector, bullet, stack);
		} else {
			ItemGunBase.setCasing(stack, bullet);
			ItemGunBase.setCasingTimer(stack, ejector.getDelay());
		}
	}
	
	protected static void trySpawnCasing(Entity entity, CasingEjector ejector, BulletConfiguration bullet, ItemStack stack) {
		
		 //abort if the gun can't eject bullets at all
		 //abort if there's no valid bullet cfg
		if((ejector == null) || (bullet == null) || (bullet.spentCasing == null)) return; //abort if the bullet is caseless
		
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "casing");
		data.setFloat("pitch", (float) Math.toRadians(entity.rotationPitch));
		data.setFloat("yaw", (float) Math.toRadians(entity.rotationYaw));
		data.setBoolean("crouched", entity.isSneaking());
		data.setString("name", bullet.spentCasing.getName());
		data.setInteger("ej", ejector.getId());
		PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ), new TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 50));
	}
	
	public static int getReloadDuration(EntityPlayer player, ItemStack stack) {
		int cycle = ((ItemGunBase) stack.getItem()).mainConfig.reloadDuration;
		if(ItemGunBase.isTrenchMaster(player)) return Math.max(1, cycle / 2);
		return cycle;
	}
	
	public static boolean isTrenchMaster(EntityPlayer player) {
		return player.inventory.armorInventory[2] != null && player.inventory.armorInventory[2].getItem() == ModItems.trenchmaster_plate && ArmorFSB.hasFSBArmor(player);
	}
}
