package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.entity.effect.EntityCloudFleijaRainbow;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.entity.projectile.EntityModBeam;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class GunB93 extends Item {

	Random rand = new Random();

	public int dmgMin = 16;
	public int dmgMax = 28;

	public GunB93() {

		this.maxStackSize = 1;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack p_77615_1_, World p_77615_2_, EntityPlayer p_77615_3_, int p_77615_4_) {
		if (!p_77615_3_.isSneaking()) {
			int j = getMaxItemUseDuration(p_77615_1_) - p_77615_4_;

			ArrowLooseEvent event = new ArrowLooseEvent(p_77615_3_, p_77615_1_, j);
			MinecraftForge.EVENT_BUS.post(event);
			j = event.charge;

			boolean flag = true;
			
			if (flag) {
				float f = j / 20.0F;
				f = (f * f + f * 2.0F) / 3.0F;

				if (j < 10.0D) {
					return;
				}

				if (j > 10.0F) {
					f = 10.0F;
				}

				if (!p_77615_2_.isRemote) {
					
					EntityModBeam entityarrow1;
					entityarrow1 = new EntityModBeam(p_77615_2_, p_77615_3_, 3.0F);
					entityarrow1.mode = GunB93.getPower(p_77615_1_) - 1;
					p_77615_1_.damageItem(1, p_77615_3_);

					p_77615_2_.spawnEntityInWorld(entityarrow1);

					p_77615_2_.playSoundAtEntity(p_77615_3_, "hbm:weapon.sparkShoot", 5.0F, 1.0F);
				}

				GunB93.setAnim(p_77615_1_, 1);
				GunB93.setPower(p_77615_1_, 0);
			}
		} else {
		}
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b) {
		int j = GunB93.getAnim(stack);

		if (j > 0) {
			if (j < 30)
				GunB93.setAnim(stack, j + 1);
			else
				GunB93.setAnim(stack, 0);

			if (j == 15) {
				world.playSoundAtEntity(entity, "hbm:weapon.b92Reload", 2F, 0.9F);
				GunB93.setPower(stack, GunB93.getPower(stack) + 1);
				
				if(GunB93.getPower(stack) > 10) {
					
					GunB93.setPower(stack, 0);
					
			    	if(!world.isRemote) {
			    		EntityNukeExplosionMK3 ex = EntityNukeExplosionMK3.statFacFleija(world, entity.posX, entity.posY, entity.posZ, 50);
			    		if(!ex.isDead) {
				    		world.playSoundEffect(entity.posX, entity.posY, entity.posZ, "random.explode", 100.0f, world.rand.nextFloat() * 0.1F + 0.9F);
				    		
							world.spawnEntityInWorld(ex);
				    		
				    		EntityCloudFleijaRainbow cloud = new EntityCloudFleijaRainbow(world, 50);
				    		cloud.posX = entity.posX;
				    		cloud.posY = entity.posY;
				    		cloud.posZ = entity.posZ;
				    		world.spawnEntityInWorld(cloud);
			    		}
			    	}
				}
			}
		}

	}

	@Override
	public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_) {
		return p_77654_1_;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 72000;
	}

	/**
	 * returns the action that specifies what animation to play when the items
	 * is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return EnumAction.bow;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
		if (!p_77659_3_.isSneaking() && GunB93.getPower(p_77659_1_) > 0) {
			ArrowNockEvent event = new ArrowNockEvent(p_77659_3_, p_77659_1_);
			MinecraftForge.EVENT_BUS.post(event);

			if (getAnim(p_77659_1_) == 0)
				p_77659_3_.setItemInUse(p_77659_1_, getMaxItemUseDuration(p_77659_1_));
		} else {
			if (GunB93.getAnim(p_77659_1_) == 0) {
				GunB93.setAnim(p_77659_1_, 1);
			}
		}

		return p_77659_1_;
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based
	 * on material.
	 */
	@Override
	public int getItemEnchantability() {
		return 1;
	}

	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		list.add("[LEGENDARY WEAPON]");
	}

	
	@Override
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
				new AttributeModifier(Item.field_111210_e, "Weapon modifier", 3.5, 0));
		return multimap;
	}

	private static int getAnim(ItemStack stack) {
		if (stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			return 0;
		}

		return stack.stackTagCompound.getInteger("animation");

	}

	private static void setAnim(ItemStack stack, int i) {
		if (stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}

		stack.stackTagCompound.setInteger("animation", i);

	}

	private static int getPower(ItemStack stack) {
		if (stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			return 0;
		}

		return stack.stackTagCompound.getInteger("energy");

	}

	private static void setPower(ItemStack stack, int i) {
		if (stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}

		stack.stackTagCompound.setInteger("energy", i);

	}

	public static float getRotationFromAnim(ItemStack stack) {
		float rad = 0.0174533F;
		rad *= 7.5F;
		int i = GunB93.getAnim(stack);

		if (i < 10)
			return 0;
		i -= 10;

		if (i < 6)
			return rad * i;
		if (i > 14)
			return rad * (5 - (i - 15));
		return rad * 5;
	}

	public static float getOffsetFromAnim(ItemStack stack) {
		float i = GunB93.getAnim(stack);

		if (i < 10)
			return 0;
		i -= 10;

		if (i < 10)
			return i / 10;
		else
			return 2 - (i / 10);
	}

	public static float getTransFromAnim(ItemStack stack) {
		float i = GunB93.getAnim(stack);

		if (i < 10)
			return 0;
		i -= 10;

		if (i > 4 && i < 10)
			return (i - 5) * 0.05F;

		if (i > 9 && i < 15)
			return (10 * 0.05F) - ((i - 5) * 0.05F);

		return 0;
	}

	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {

		return EnumRarity.uncommon;
	}
}
