package com.hbm.items.weapon;

import java.util.List;

import com.hbm.entity.grenade.EntityGrenadeASchrab;
import com.hbm.entity.grenade.EntityGrenadeBlackHole;
import com.hbm.entity.grenade.EntityGrenadeBreach;
import com.hbm.entity.grenade.EntityGrenadeBurst;
import com.hbm.entity.grenade.EntityGrenadeCloud;
import com.hbm.entity.grenade.EntityGrenadeCluster;
import com.hbm.entity.grenade.EntityGrenadeDynamite;
import com.hbm.entity.grenade.EntityGrenadeElectric;
import com.hbm.entity.grenade.EntityGrenadeFire;
import com.hbm.entity.grenade.EntityGrenadeFlare;
import com.hbm.entity.grenade.EntityGrenadeFrag;
import com.hbm.entity.grenade.EntityGrenadeGas;
import com.hbm.entity.grenade.EntityGrenadeGascan;
import com.hbm.entity.grenade.EntityGrenadeGeneric;
import com.hbm.entity.grenade.EntityGrenadeIFBouncy;
import com.hbm.entity.grenade.EntityGrenadeIFBrimstone;
import com.hbm.entity.grenade.EntityGrenadeIFConcussion;
import com.hbm.entity.grenade.EntityGrenadeIFGeneric;
import com.hbm.entity.grenade.EntityGrenadeIFHE;
import com.hbm.entity.grenade.EntityGrenadeIFHopwire;
import com.hbm.entity.grenade.EntityGrenadeIFImpact;
import com.hbm.entity.grenade.EntityGrenadeIFIncendiary;
import com.hbm.entity.grenade.EntityGrenadeIFMystery;
import com.hbm.entity.grenade.EntityGrenadeIFNull;
import com.hbm.entity.grenade.EntityGrenadeIFSpark;
import com.hbm.entity.grenade.EntityGrenadeIFSticky;
import com.hbm.entity.grenade.EntityGrenadeIFToxic;
import com.hbm.entity.grenade.EntityGrenadeLemon;
import com.hbm.entity.grenade.EntityGrenadeMIRV;
import com.hbm.entity.grenade.EntityGrenadeMk2;
import com.hbm.entity.grenade.EntityGrenadeNuclear;
import com.hbm.entity.grenade.EntityGrenadeNuke;
import com.hbm.entity.grenade.EntityGrenadePC;
import com.hbm.entity.grenade.EntityGrenadePlasma;
import com.hbm.entity.grenade.EntityGrenadePoison;
import com.hbm.entity.grenade.EntityGrenadePulse;
import com.hbm.entity.grenade.EntityGrenadeSchrabidium;
import com.hbm.entity.grenade.EntityGrenadeShrapnel;
import com.hbm.entity.grenade.EntityGrenadeSmart;
import com.hbm.entity.grenade.EntityGrenadeStrong;
import com.hbm.entity.grenade.EntityGrenadeTau;
import com.hbm.entity.grenade.EntityGrenadeZOMG;
import com.hbm.entity.grenade.EntityWastePearl;
import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemGrenade extends Item {
	
	public int fuse = 4;

	public ItemGrenade(int fuse) {
		this.maxStackSize = 16;
		this.fuse = fuse;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
		if (!p_77659_3_.capabilities.isCreativeMode) {
			--p_77659_1_.stackSize;
		}

		p_77659_2_.playSoundAtEntity(p_77659_3_, "random.bow", 0.5F, 0.4F / (Item.itemRand.nextFloat() * 0.4F + 0.8F));

		//TODO:
		/*
		 * kill all this stupid bullshit
		 * make a PROPER grenade entity base class
		 * have all the grenade items be an NBT stat in the entity instead of having new entities for every fucking grenade type
		 * register explosion effects with some lambdas to save on LOC
		 * jesus christ why do i keep doing this
		 */
		if (!p_77659_2_.isRemote) {
			if (this == ModItems.grenade_generic) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeGeneric(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_strong) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeStrong(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_frag) {
				EntityGrenadeFrag frag = new EntityGrenadeFrag(p_77659_2_, p_77659_3_);
				frag.shooter = p_77659_3_;
				p_77659_2_.spawnEntityInWorld(frag);
			}
			if (this == ModItems.grenade_fire) {
				EntityGrenadeFire fire = new EntityGrenadeFire(p_77659_2_, p_77659_3_);
				fire.shooter = p_77659_3_;
				p_77659_2_.spawnEntityInWorld(fire);
			}
			if (this == ModItems.grenade_cluster) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeCluster(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_flare) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeFlare(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_electric) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeElectric(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_poison) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadePoison(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_gas) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeGas(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_schrabidium) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeSchrabidium(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_nuke) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeNuke(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_nuclear) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeNuclear(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_pulse) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadePulse(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_plasma) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadePlasma(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_tau) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeTau(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_lemon) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeLemon(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_mk2) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeMk2(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_aschrab) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeASchrab(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_zomg) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeZOMG(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_shrapnel) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeShrapnel(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_black_hole) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeBlackHole(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_gascan) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeGascan(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_cloud) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeCloud(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_pink_cloud) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadePC(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_smart) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeSmart(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_mirv) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeMIRV(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_breach) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeBreach(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_burst) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeBurst(p_77659_2_, p_77659_3_));
			}

			if (this == ModItems.grenade_if_generic) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeIFGeneric(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_if_he) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeIFHE(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_if_bouncy) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeIFBouncy(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_if_sticky) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeIFSticky(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_if_impact) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeIFImpact(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_if_incendiary) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeIFIncendiary(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_if_toxic) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeIFToxic(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_if_concussion) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeIFConcussion(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_if_brimstone) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeIFBrimstone(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_if_mystery) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeIFMystery(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_if_spark) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeIFSpark(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_if_hopwire) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeIFHopwire(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.grenade_if_null) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeIFNull(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.nuclear_waste_pearl) {
				p_77659_2_.spawnEntityInWorld(new EntityWastePearl(p_77659_2_, p_77659_3_));
			}
			if (this == ModItems.stick_dynamite) {
				p_77659_2_.spawnEntityInWorld(new EntityGrenadeDynamite(p_77659_2_, p_77659_3_));
			}
		}

		return p_77659_1_;
	}

	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {

		if (this == ModItems.grenade_schrabidium || this == ModItems.grenade_aschrab || this == ModItems.grenade_cloud) {
			return EnumRarity.rare;
		}

		if (this == ModItems.grenade_plasma || this == ModItems.grenade_zomg || this == ModItems.grenade_black_hole || this == ModItems.grenade_pink_cloud) {
			return EnumRarity.epic;
		}

		if (this == ModItems.grenade_nuke || this == ModItems.grenade_nuclear || this == ModItems.grenade_tau || this == ModItems.grenade_lemon || this == ModItems.grenade_mk2 || this == ModItems.grenade_pulse || this == ModItems.grenade_gascan) {
			return EnumRarity.uncommon;
		}

		return EnumRarity.common;
	}
	
	private String translateFuse() {
		if(this.fuse == -1)
			return "Impact";
		
		if(this.fuse == 0)
			return "Instant";
		
		return this.fuse + "s";
	}

	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		list.add("Fuse: " + translateFuse());

		if (this == ModItems.grenade_smart) {
			list.add("");
			list.add("\"Why did it not blow up????\"");
			list.add(EnumChatFormatting.ITALIC + "If it didn't blow up it means it worked.");
		}

		if (this == ModItems.grenade_if_generic) {
			list.add("");
			list.add(EnumChatFormatting.ITALIC + "\"How do you like " + EnumChatFormatting.RESET + EnumChatFormatting.GRAY + "them" + EnumChatFormatting.ITALIC + " apples?\"");
		}
		if (this == ModItems.grenade_if_he) {
			list.add("");
			list.add(EnumChatFormatting.ITALIC + "\"You better run, you better take cover!\"");
		}
		if (this == ModItems.grenade_if_bouncy) {
			list.add("");
			list.add(EnumChatFormatting.ITALIC + "\"Boing!\"");
		}
		if (this == ModItems.grenade_if_sticky) {
			list.add("");
			list.add(EnumChatFormatting.ITALIC + "\"This one is the booger grenade.\"");
		}
		if (this == ModItems.grenade_if_impact) {
			list.add("");
			list.add(EnumChatFormatting.ITALIC + "\"Tossable boom.\"");
		}
		if (this == ModItems.grenade_if_incendiary) {
			list.add("");
			list.add(EnumChatFormatting.ITALIC + "\"Flaming wheel of destruction!\"");
		}
		if (this == ModItems.grenade_if_toxic) {
			list.add("");
			list.add(EnumChatFormatting.ITALIC + "\"TOXIC SHOCK\"");
		}
		if (this == ModItems.grenade_if_concussion) {
			list.add("");
			list.add(EnumChatFormatting.ITALIC + "\"Oof ouch owie, my bones!\"");
		}
		if (this == ModItems.grenade_if_brimstone) {
			list.add("");
			list.add(EnumChatFormatting.ITALIC + "\"Zoop!\"");
		}
		if (this == ModItems.grenade_if_mystery) {
			list.add("");
			list.add(EnumChatFormatting.ITALIC + "\"It's a mystery!\"");
		}
		if (this == ModItems.grenade_if_spark) {
			list.add("");
			//list.add(EnumChatFormatting.ITALIC + "\"31-31-31-31-31-31-31-31-31-31-31-31-31\"");
			list.add(EnumChatFormatting.ITALIC + "\"We can't rewind, we've gone too far.\"");
		}
		if (this == ModItems.grenade_if_hopwire) {
			list.add("");
			list.add(EnumChatFormatting.ITALIC + "\"All I ever wished for was a bike that didn't fall over.\"");
		}
		if (this == ModItems.grenade_if_null) {
			list.add("");
			list.add(EnumChatFormatting.ITALIC + "java.lang.NullPointerException");
		}
	}
	
	public static int getFuseTicks(Item grenade) {
		return ((ItemGrenade)grenade).fuse * 20;
	}
}
