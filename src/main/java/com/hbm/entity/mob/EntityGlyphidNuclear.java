package com.hbm.entity.mob;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.BlockAllocatorStandard;
import com.hbm.explosion.vanillant.standard.BlockMutatorDebris;
import com.hbm.explosion.vanillant.standard.BlockProcessorStandard;
import com.hbm.explosion.vanillant.standard.EntityProcessorStandard;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityGlyphidNuclear extends EntityGlyphid {
	
	public int deathTicks;

	public EntityGlyphidNuclear(World world) {
		super(world);
		setSize(2.5F, 1.75F);
		this.isImmuneToFire = true;
	}
	
	@Override
	public ResourceLocation getSkin() {
		return ResourceManager.glyphid_nuclear_tex;
	}

	@Override
	public double getScale() {
		return 2D;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.8D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(50D);
	}

	@Override
	public int getArmorBreakChance(float amount) {
		return amount < 25 ? 100 : amount > 1000 ? 1 : 10;
	}

	@Override
	public float calculateDamage(float amount) {

		byte armor = this.dataWatcher.getWatchableObjectByte(17);
		int divisor = 1;
		
		for(int i = 0; i < 5; i++) {
			if((armor & (1 << i)) > 0) {
				divisor += 5;
			}
		}
		
		amount /= divisor;
		
		return amount;
	}

	@Override
	public float getDamageThreshold() {
		return 10F;
	}

	@Override
	protected void onDeathUpdate() {
		++this.deathTicks;

		if(this.deathTicks == 100) {
			
			if(!this.worldObj.isRemote) {
				ExplosionVNT vnt = new ExplosionVNT(this.worldObj, this.posX, this.posY, this.posZ, 25, this);
				vnt.setBlockAllocator(new BlockAllocatorStandard(24));
				vnt.setBlockProcessor(new BlockProcessorStandard().withBlockEffect(new BlockMutatorDebris(ModBlocks.volcanic_lava_block, 0)).setNoDrop());
				vnt.setEntityProcessor(new EntityProcessorStandard().withRangeMod(1.5F));
				vnt.setPlayerProcessor(new PlayerProcessorStandard());
				vnt.explode();
				
				this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "hbm:weapon.mukeExplosion", 15.0F, 1.0F);
	
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "muke");
				// if the FX type is "muke", apply random BF effect
				if(MainRegistry.polaroidID == 11 || this.rand.nextInt(100) == 0) {
					data.setBoolean("balefire", true);
				}
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, this.posX, this.posY + 0.5, this.posZ), new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 250));
			}
			
			setDead();
		} else {
			if(!this.worldObj.isRemote && this.deathTicks % 10 == 0) {
				this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "hbm:weapon.fstbmbPing", 5.0F, 1.0F);
			}
		}
	}
}
