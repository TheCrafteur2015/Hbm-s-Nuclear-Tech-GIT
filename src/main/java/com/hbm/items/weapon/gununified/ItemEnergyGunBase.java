package com.hbm.items.weapon.gununified;

import java.util.List;

import org.lwjgl.input.Mouse;

import com.hbm.config.GeneralConfig;
import com.hbm.entity.projectile.EntityBulletBaseNT;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IHoldableWeapon;
import com.hbm.items.weapon.ItemGunBase;
import com.hbm.packet.GunAnimationPacket;
import com.hbm.packet.GunButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.util.RenderScreenOverlay;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;
import com.hbm.util.BobMathUtil;
import com.hbm.util.ChatBuilder;

import api.hbm.energy.IBatteryItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class ItemEnergyGunBase extends ItemGunBase implements IBatteryItem {
	
	public ItemEnergyGunBase(GunConfiguration config) {
		super(config);
	}
	
	public ItemEnergyGunBase(GunConfiguration config, GunConfiguration alt) {
		super(config, alt);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add("Energy Stored: " + BobMathUtil.getShortNumber(getCharge(stack)) + "/" + BobMathUtil.getShortNumber(this.mainConfig.maxCharge) + "HE");
		list.add("Charge rate: " + BobMathUtil.getShortNumber(this.mainConfig.chargeRate) + "HE/t");
		
		addAdditionalInformation(stack, list);
	}
	
	@Override
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
		}
	}
	
	@Override
	protected void updateServer(ItemStack stack, World world, EntityPlayer player, int slot, boolean isCurrentItem) {
		
		if(ItemGunBase.getDelay(stack) > 0 && isCurrentItem)
			ItemGunBase.setDelay(stack, ItemGunBase.getDelay(stack) - 1);
		
		if(ItemGunBase.getIsMouseDown(stack) && !(player.getHeldItem() == stack)) {
			ItemGunBase.setIsMouseDown(stack, false);
		}
		
		if(ItemGunBase.getIsAltDown(stack) && !isCurrentItem) {
			ItemGunBase.setIsAltDown(stack, false);
		}
			
		if(GeneralConfig.enableGuns && this.mainConfig.firingMode == 1 && ItemGunBase.getIsMouseDown(stack) && tryShoot(stack, world, player, isCurrentItem)) {
			
			fire(stack, world, player);
			ItemGunBase.setDelay(stack, getConfig(stack).firingRate);
		}
	}
	
	@Override
	protected boolean tryShoot(ItemStack stack, World world, EntityPlayer player, boolean main) {
	
		
		if(main && ItemGunBase.getDelay(stack) == 0) {
			return getConfig(stack).dischargePerShot <= getCharge(stack);
		}
	
		return false;
	}
	
	@Override
	protected void fire(ItemStack stack, World world, EntityPlayer player) {
		
		BulletConfiguration config = getConfig(stack);
		
		int bullets = config.bulletsMin;
		
		for(int k = 0; k < this.mainConfig.roundsPerCycle; k++) {
			
			if(config.bulletsMax > config.bulletsMin)
				bullets += world.rand.nextInt(config.bulletsMax - config.bulletsMin);
			
			for(int i = 0; i < bullets; i++) {
				spawnProjectile(world, player, stack, BulletConfigSyncingUtil.getKey(config));
			}
			
			setCharge(stack, getCharge(stack) - config.dischargePerShot);;
		}
		
		world.playSoundAtEntity(player, this.mainConfig.firingSound, 1.0F, this.mainConfig.firingPitch);
	}
	
	@Override
	protected void spawnProjectile(World world, EntityPlayer player, ItemStack stack, int config) {
		
		EntityBulletBaseNT bullet = new EntityBulletBaseNT(world, config, player);
		world.spawnEntityInWorld(bullet);
		
		if(this.mainConfig.animations.containsKey(AnimType.CYCLE) && player instanceof EntityPlayerMP)
			PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(AnimType.CYCLE.ordinal()), (EntityPlayerMP) player);
			
	}
	
	@Override
	public void startAction(ItemStack stack, World world, EntityPlayer player, boolean main) {
		
		if(this.mainConfig.firingMode == GunConfiguration.FIRE_MANUAL && main && tryShoot(stack, world, player, main)) {
			fire(stack, world, player);
			ItemGunBase.setDelay(stack, this.mainConfig.rateOfFire);
			
		}
		
		if(!main && stack.getItem() instanceof ItemEnergyGunBase) {
			
			byte mode = stack.hasTagCompound() ? stack.getTagCompound().getByte("mode") : 0;
			
			if(!stack.hasTagCompound())
				stack.stackTagCompound = new NBTTagCompound();
			
			mode++;
			if(mode >= this.mainConfig.config.size()) {
				mode = 0;
			}
			
			stack.getTagCompound().setByte("mode", mode);
			
			if(!world.isRemote) {
				BulletConfiguration config = BulletConfigSyncingUtil.pullConfig(this.mainConfig.config.get(mode));
				//PacketDispatcher.wrapper.sendTo(new PlayerInformPacket("" + config.chatColour + config.modeName, MainRegistry.proxy.ID_GUN_MODE), (EntityPlayerMP)player);
				
				player.addChatMessage(ChatBuilder.start("")
						.nextTranslation("weapon.elecGun.modeChange").color(EnumChatFormatting.WHITE)
						.nextTranslation(" ")
						.nextTranslation(config.modeName).color(config.chatColour).flush());
			}
		}
	}
	
	// yummy boilerplate
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return 1D - (double) getCharge(stack) / (double) getMaxCharge();
	}

	@Override
	public void chargeBattery(ItemStack stack, long i) {
		if(stack.getItem() instanceof ItemEnergyGunBase) {
			if(stack.hasTagCompound()) {
				stack.stackTagCompound.setLong("charge", stack.stackTagCompound.getLong("charge") + i);
			} else {
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("charge", i);
			}
		}
	}

	@Override
	public void setCharge(ItemStack stack, long i) {
		if(stack.getItem() instanceof ItemEnergyGunBase) {
			if(stack.hasTagCompound()) {
				stack.stackTagCompound.setLong("charge", i);
			} else {
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("charge", i);
			}
		}
	}

	@Override
	public void dischargeBattery(ItemStack stack, long i) {
		if(stack.getItem() instanceof ItemEnergyGunBase) {
			if(stack.hasTagCompound()) {
				stack.stackTagCompound.setLong("charge", stack.stackTagCompound.getLong("charge") - i);
			} else {
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("charge", ((ItemEnergyGunBase)stack.getItem()).mainConfig.maxCharge - i);
			}
		}
	}

	@Override
	public long getCharge(ItemStack stack) {
		if(stack.getItem() instanceof ItemEnergyGunBase) {
			if(stack.hasTagCompound()) {
				return stack.stackTagCompound.getLong("charge");
			} else {
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("charge", ((ItemEnergyGunBase) stack.getItem()).mainConfig.maxCharge);
				return stack.stackTagCompound.getLong("charge");
			}
		}

		return 0;
	}

	@Override
	public long getMaxCharge() {
		return this.mainConfig.maxCharge;
	}

	@Override
	public long getChargeRate() {
		return this.mainConfig.chargeRate;
	}

	@Override
	public long getDischargeRate() {
		return 0;
	}
	
	public BulletConfiguration getConfig(ItemStack stack) {
		
		byte mode = 0;
		
		if(stack.hasTagCompound())
			mode = stack.getTagCompound().getByte("mode");
		
		return BulletConfigSyncingUtil.pullConfig(this.mainConfig.config.get(mode));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {

		ItemStack stack = new ItemStack(item);
		stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setLong("charge", ((ItemEnergyGunBase) item).getMaxCharge());

		list.add(stack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void renderHUD(Pre event, ElementType type, EntityPlayer player, ItemStack stack) {
		
		if(type == ElementType.CROSSHAIRS && GeneralConfig.enableCrosshairs) {

			event.setCanceled(true);
			
			if(!(this.mainConfig.hasSights && player.isSneaking()))
				RenderScreenOverlay.renderCustomCrosshairs(event.resolution, Minecraft.getMinecraft().ingameGUI, ((IHoldableWeapon)player.getHeldItem().getItem()).getCrosshair());
			else
				RenderScreenOverlay.renderCustomCrosshairs(event.resolution, Minecraft.getMinecraft().ingameGUI, Crosshair.NONE);
		}
	}

}
