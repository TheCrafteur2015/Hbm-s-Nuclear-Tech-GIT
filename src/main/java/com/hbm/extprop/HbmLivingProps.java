package com.hbm.extprop;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.hbm.config.RadiationConfig;
import com.hbm.entity.mob.EntityDuck;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.main.ServerProxy;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.PlayerInformPacket;
import com.hbm.util.ChatBuilder;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class HbmLivingProps implements IExtendedEntityProperties {
	
	public static final String key = "NTM_EXT_LIVING";
	public static final UUID digamma_UUID = UUID.fromString("2a3d8aec-5ab9-4218-9b8b-ca812bdf378b");
	public EntityLivingBase entity;
	
	/// VALS ///
	private float radiation;
	private float digamma;
	private int asbestos;
	public static final int maxAsbestos = 60 * 60 * 20;
	private int blacklung;
	public static final int maxBlacklung = 2 * 60 * 60 * 20;
	private float radEnv;
	private float radBuf;
	private int bombTimer;
	private int contagion;
	private int oil;
	private int temperature;
	private boolean frozen = false;
	private boolean burning = false;
	private List<ContaminationEffect> contamination = new ArrayList<>();
	
	public HbmLivingProps(EntityLivingBase entity) {
		this.entity = entity;
	}
	
	/// DATA ///
	public static HbmLivingProps registerData(EntityLivingBase entity) {
		
		entity.registerExtendedProperties(HbmLivingProps.key, new HbmLivingProps(entity));
		return (HbmLivingProps) entity.getExtendedProperties(HbmLivingProps.key);
	}
	
	public static HbmLivingProps getData(EntityLivingBase entity) {
		
		HbmLivingProps props = (HbmLivingProps) entity.getExtendedProperties(HbmLivingProps.key);
		return props != null ? props : HbmLivingProps.registerData(entity);
	}
	
	/// RADIATION ///
	public static float getRadiation(EntityLivingBase entity) {
		if(!RadiationConfig.enableContamination)
			return 0;

		return HbmLivingProps.getData(entity).radiation;
	}
	
	public static void setRadiation(EntityLivingBase entity, float rad) {
		if(RadiationConfig.enableContamination)
			HbmLivingProps.getData(entity).radiation = rad;
	}
	
	public static void incrementRadiation(EntityLivingBase entity, float rad) {
		if(!RadiationConfig.enableContamination)
			return;
		
		@SuppressWarnings("unused")
		HbmLivingProps data = HbmLivingProps.getData(entity);
		float radiation = HbmLivingProps.getData(entity).radiation + rad;
		
		if(radiation > 2500)
			radiation = 2500;
		if(radiation < 0)
			radiation = 0;
		
		HbmLivingProps.setRadiation(entity, radiation);
	}
	
	/// RAD ENV ///
	public static float getRadEnv(EntityLivingBase entity) {
		return HbmLivingProps.getData(entity).radEnv;
	}
	
	public static void setRadEnv(EntityLivingBase entity, float rad) {
		HbmLivingProps.getData(entity).radEnv = rad;
	}
	
	/// RAD BUF ///
	public static float getRadBuf(EntityLivingBase entity) {
		return HbmLivingProps.getData(entity).radBuf;
	}
	
	public static void setRadBuf(EntityLivingBase entity, float rad) {
		HbmLivingProps.getData(entity).radBuf = rad;
	}
	
	/// CONTAMINATION ///
	public static List<ContaminationEffect> getCont(EntityLivingBase entity) {
		return HbmLivingProps.getData(entity).contamination;
	}
	
	public static void addCont(EntityLivingBase entity, ContaminationEffect cont) {
		HbmLivingProps.getData(entity).contamination.add(cont);
	}
	
	/// DIGAMA ///
	public static float getDigamma(EntityLivingBase entity) {
		return HbmLivingProps.getData(entity).digamma;
	}
	
	public static void setDigamma(EntityLivingBase entity, float digamma) {
		
		if(entity.worldObj.isRemote)
			return;
		
		if(entity instanceof EntityDuck)
			digamma = 0.0F;
		
		HbmLivingProps.getData(entity).digamma = digamma;
		
		float healthMod = (float)Math.pow(0.5, digamma) - 1F;
		
		IAttributeInstance attributeinstance = entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);
		
		try {
			attributeinstance.removeModifier(attributeinstance.getModifier(HbmLivingProps.digamma_UUID));
		} catch(Exception ex) { }
		
		attributeinstance.applyModifier(new AttributeModifier(HbmLivingProps.digamma_UUID, "digamma", healthMod, 2));
		
		if(entity.getHealth() > entity.getMaxHealth() && entity.getMaxHealth() > 0) {
			entity.setHealth(entity.getMaxHealth());
		}
		
		if((entity.getMaxHealth() <= 0 || digamma >= 10.0F) && entity.isEntityAlive()) {
			entity.setAbsorptionAmount(0);
			entity.attackEntityFrom(ModDamageSource.digamma, 500F);
			entity.setHealth(0);
			entity.onDeath(ModDamageSource.digamma);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "sweat");
			data.setInteger("count", 50);
			data.setInteger("block", Block.getIdFromBlock(Blocks.soul_sand));
			data.setInteger("entity", entity.getEntityId());
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, 0, 0, 0),  new TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 50));
		}
		
		if(entity instanceof EntityPlayer) {
			
			float di = HbmLivingProps.getData(entity).digamma;

			if(di > 0F)
				((EntityPlayer) entity).triggerAchievement(MainRegistry.digammaSee);
			if(di >= 2F)
				((EntityPlayer) entity).triggerAchievement(MainRegistry.digammaFeel);
			if(di >= 10F)
				((EntityPlayer) entity).triggerAchievement(MainRegistry.digammaKnow);
		}
	}
	
	@SuppressWarnings("unused")
	public static void incrementDigamma(EntityLivingBase entity, float digamma) {
		
		if(entity instanceof EntityDuck)
			digamma = 0.0F;
		
		HbmLivingProps data = HbmLivingProps.getData(entity);
		float dRad = HbmLivingProps.getDigamma(entity) + digamma;
		
		if(dRad > 10)
			dRad = 10;
		if(dRad < 0)
			dRad = 0;
		
		HbmLivingProps.setDigamma(entity, dRad);
	}
	
	
	/// ASBESTOS ///
	public static int getAsbestos(EntityLivingBase entity) {
		if(RadiationConfig.disableAsbestos) return 0;
		return HbmLivingProps.getData(entity).asbestos;
	}
	
	public static void setAsbestos(EntityLivingBase entity, int asbestos) {
		if(RadiationConfig.disableAsbestos) return;
		HbmLivingProps.getData(entity).asbestos = asbestos;
		
		if(asbestos >= HbmLivingProps.maxAsbestos) {
			HbmLivingProps.getData(entity).asbestos = 0;
			entity.attackEntityFrom(ModDamageSource.asbestos, 1000);
		}
	}
	
	public static void incrementAsbestos(EntityLivingBase entity, int asbestos) {
		if(RadiationConfig.disableAsbestos) return;
		HbmLivingProps.setAsbestos(entity, HbmLivingProps.getAsbestos(entity) + asbestos);
		
		if(entity instanceof EntityPlayerMP) {
			PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(ChatBuilder.start("").nextTranslation("info.asbestos").color(EnumChatFormatting.RED).flush(), ServerProxy.ID_GAS_HAZARD, 3000), (EntityPlayerMP) entity);
		}
	}
	
	
	/// BLACK LUNG DISEASE ///
	public static int getBlackLung(EntityLivingBase entity) {
		if(RadiationConfig.disableCoal) return 0;
		return HbmLivingProps.getData(entity).blacklung;
	}
	
	public static void setBlackLung(EntityLivingBase entity, int blacklung) {
		if(RadiationConfig.disableCoal) return;
		HbmLivingProps.getData(entity).blacklung = blacklung;
		
		if(blacklung >= HbmLivingProps.maxBlacklung) {
			HbmLivingProps.getData(entity).blacklung = 0;
			entity.attackEntityFrom(ModDamageSource.blacklung, 1000);
		}
	}
	
	public static void incrementBlackLung(EntityLivingBase entity, int blacklung) {
		if(RadiationConfig.disableCoal) return;
		HbmLivingProps.setBlackLung(entity, HbmLivingProps.getBlackLung(entity) + blacklung);
		
		if(entity instanceof EntityPlayerMP) {
			PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(ChatBuilder.start("").nextTranslation("info.coaldust").color(EnumChatFormatting.RED).flush(), ServerProxy.ID_GAS_HAZARD, 3000), (EntityPlayerMP) entity);
		}
	}
	
	/// TIME BOMB ///
	public static int getTimer(EntityLivingBase entity) {
		return HbmLivingProps.getData(entity).bombTimer;
	}
	
	public static void setTimer(EntityLivingBase entity, int bombTimer) {
		HbmLivingProps.getData(entity).bombTimer = bombTimer;
	}
	
	/// CONTAGION ///
	public static int getContagion(EntityLivingBase entity) {
		return HbmLivingProps.getData(entity).contagion;
	}
	
	public static void setContagion(EntityLivingBase entity, int contageon) {
		HbmLivingProps.getData(entity).contagion = contageon;
	}
	
	/// OIL ///
	public static int getOil(EntityLivingBase entity) {
		return HbmLivingProps.getData(entity).oil;
	}
	
	public static void setOil(EntityLivingBase entity, int oil) {
		HbmLivingProps.getData(entity).oil = oil;
	}
	
	/// TEMPERATURE ///
	public static int getTemperature(EntityLivingBase entity) {
		return HbmLivingProps.getData(entity).temperature;
	}
	
	public static void setTemperature(EntityLivingBase entity, int temperature) {
		HbmLivingProps data = HbmLivingProps.getData(entity);
		temperature = MathHelper.clamp_int(temperature, -2500, 2500);
		data.temperature = temperature;
		if(temperature > 1000)  data.burning = true;
		if(temperature < 800)  data.burning = false;
		if(temperature < -1000)  data.frozen = true;
		if(temperature > -800)  data.frozen = false;
	}

	public static boolean isFrozen(EntityLivingBase entity) { return HbmLivingProps.getData(entity).frozen; };
	public static boolean isBurning(EntityLivingBase entity) { return HbmLivingProps.getData(entity).burning; };

	@Override
	public void init(Entity entity, World world) { }

	@Override
	public void saveNBTData(NBTTagCompound nbt) {
		
		NBTTagCompound props = new NBTTagCompound();
		
		props.setFloat("hfr_radiation", this.radiation);
		props.setFloat("hfr_digamma", this.digamma);
		props.setInteger("hfr_asbestos", this.asbestos);
		props.setInteger("hfr_bomb", this.bombTimer);
		props.setInteger("hfr_contagion", this.contagion);
		props.setInteger("hfr_blacklung", this.blacklung);
		props.setInteger("hfr_oil", this.oil);
		
		props.setInteger("hfr_cont_count", this.contamination.size());
		
		for(int i = 0; i < this.contamination.size(); i++) {
			this.contamination.get(i).save(props, i);
		}
		
		nbt.setTag("HbmLivingProps", props);
	}

	@Override
	public void loadNBTData(NBTTagCompound nbt) {
		
		NBTTagCompound props = (NBTTagCompound) nbt.getTag("HbmLivingProps");
		
		if(props != null) {
			this.radiation = props.getFloat("hfr_radiation");
			this.digamma = props.getFloat("hfr_digamma");
			this.asbestos = props.getInteger("hfr_asbestos");
			this.bombTimer = props.getInteger("hfr_bomb");
			this.contagion = props.getInteger("hfr_contagion");
			this.blacklung = props.getInteger("hfr_blacklung");
			this.oil = props.getInteger("hfr_oil");
			
			int cont = props.getInteger("hfr_cont_count");
			
			for(int i = 0; i < cont; i++) {
				this.contamination.add(ContaminationEffect.load(props, i));
			}
		}
	}
	
	public static class ContaminationEffect {
		
		public float maxRad;
		public int maxTime;
		public int time;
		public boolean ignoreArmor;
		
		public ContaminationEffect(float rad, int time, boolean ignoreArmor) {
			this.maxRad = rad;
			this.maxTime = this.time = time;
			this.ignoreArmor = ignoreArmor;
		}
		
		public float getRad() {
			return this.maxRad * ((float)this.time / (float)this.maxTime);
		}
		
		public void save(NBTTagCompound nbt, int index) {
			NBTTagCompound me = new NBTTagCompound();
			me.setFloat("maxRad", this.maxRad);
			me.setInteger("maxTime", this.maxTime);
			me.setInteger("time", this.time);
			me.setBoolean("ignoreArmor", this.ignoreArmor);
			nbt.setTag("cont_" + index, me);
		}
		
		public static ContaminationEffect load(NBTTagCompound nbt, int index) {
			NBTTagCompound me = (NBTTagCompound) nbt.getTag("cont_" + index);
			float maxRad = me.getFloat("maxRad");
			int maxTime = nbt.getInteger("maxTime");
			int time = nbt.getInteger("time");
			boolean ignoreArmor = nbt.getBoolean("ignoreArmor");
			
			ContaminationEffect effect = new ContaminationEffect(maxRad, maxTime, ignoreArmor);
			effect.time = time;
			return effect;
		}
	}
}
