package com.hbm.render.util;

import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.render.model.ModelArmorSolstice;
import com.hbm.render.model.ModelArmorWings;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class RenderAccessoryUtility {

	private static ResourceLocation hbm = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeHbm3.png");
	private static ResourceLocation hbm2 = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeHbm2.png");
	private static ResourceLocation drillgon = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeDrillgon.png");
	private static ResourceLocation dafnik = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeDafnik.png");
	private static ResourceLocation lpkukin = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeShield.png");
	private static ResourceLocation vertice = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeVertice_2.png");
	private static ResourceLocation red = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeRed.png");
	private static ResourceLocation ayy = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeAyy.png");
	private static ResourceLocation nostalgia = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeNostalgia.png");
	private static ResourceLocation nostalgia2 = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeNostalgia2.png");
	private static ResourceLocation sam = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeSam.png");
	private static ResourceLocation hoboy = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeHoboy_mk3.png");
	private static ResourceLocation master = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeMaster.png");
	private static ResourceLocation mek = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeMek.png");
	private static ResourceLocation zippy = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeZippySqrl.png");
	private static ResourceLocation test = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeTest.png");
	private static ResourceLocation schrabby = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeSchrabbyAlt.png");
	private static ResourceLocation swiggs = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeSweatySwiggs.png");
	private static ResourceLocation doctor17 = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeDoctor17.png");
	private static ResourceLocation shimmeringblaze = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeBlaze.png");
	private static ResourceLocation blaze2 = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeBlaze2.png");
	private static ResourceLocation wiki = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeWiki.png");
	private static ResourceLocation leftnugget = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeLeftNugget.png");
	private static ResourceLocation rightnugget = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeRightNugget.png");
	private static ResourceLocation tankish = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeTankish.png");
	private static ResourceLocation frizzlefrazzle = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeFrizzleFrazzle.png");
	private static ResourceLocation pheo = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapePheo.png");
	private static ResourceLocation vaer = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeVaer.png");
	private static ResourceLocation adam = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeAdam.png");
	
	public static ResourceLocation getCloakFromPlayer(EntityPlayer player) {
		
		String uuid = player.getUniqueID().toString();
		String name = player.getDisplayName();

		if(uuid.equals(Library.HbMinecraft)) {
			return (MainRegistry.polaroidID == 11 ? RenderAccessoryUtility.hbm : RenderAccessoryUtility.hbm2);
		}

		if(uuid.equals(Library.Drillgon)) {
			return RenderAccessoryUtility.drillgon;
		}
		if(uuid.equals(Library.Dafnik)) {
			return RenderAccessoryUtility.dafnik;
		}
		if(uuid.equals(Library.LPkukin)) {
			return RenderAccessoryUtility.lpkukin;
		}
		if(uuid.equals(Library.LordVertice)) {
			return RenderAccessoryUtility.vertice;
		}
		if(uuid.equals(Library.CodeRed_)) {
			return RenderAccessoryUtility.red;
		}
		if(uuid.equals(Library.dxmaster769)) {
			return RenderAccessoryUtility.ayy;
		}
		if(uuid.equals(Library.Dr_Nostalgia)) {
			return (MainRegistry.polaroidID == 11 ? RenderAccessoryUtility.nostalgia2 : RenderAccessoryUtility.nostalgia);
		}
		if(uuid.equals(Library.Samino2)) {
			return RenderAccessoryUtility.sam;
		}
		if(uuid.equals(Library.Hoboy03new)) {
			return RenderAccessoryUtility.hoboy;
		}
		if(uuid.equals(Library.Dragon59MC)) {
			return RenderAccessoryUtility.master;
		}
		if(uuid.equals(Library.Steelcourage)) {
			return RenderAccessoryUtility.mek;
		}
		if(uuid.equals(Library.ZippySqrl)) {
			return RenderAccessoryUtility.zippy;
		}
		if(uuid.equals(Library.Schrabby)) {
			return RenderAccessoryUtility.schrabby;
		}
		if(uuid.equals(Library.SweatySwiggs)) {
			return RenderAccessoryUtility.swiggs;
		}
		if(uuid.equals(Library.Doctor17) || uuid.equals(Library.Doctor17PH)) {
			return RenderAccessoryUtility.doctor17;
		}
		if(uuid.equals(Library.ShimmeringBlaze)) {
			return (MainRegistry.polaroidID == 11 ? RenderAccessoryUtility.blaze2 : RenderAccessoryUtility.shimmeringblaze);
		}
		if(uuid.equals(Library.FifeMiner)) {
			return RenderAccessoryUtility.leftnugget;
		}
		if(uuid.equals(Library.lag_add)) {
			return RenderAccessoryUtility.rightnugget;
		}
		if(uuid.equals(Library.Tankish)) {
			return RenderAccessoryUtility.tankish;
		}
		if(uuid.equals(Library.FrizzleFrazzle)) {
			return RenderAccessoryUtility.frizzlefrazzle;
		}
		if(uuid.equals(Library.Barnaby99_x)) {
			return RenderAccessoryUtility.pheo;
		}
		if(uuid.equals(Library.Ma118)) {
			return RenderAccessoryUtility.vaer;
		}
		if(uuid.equals(Library.Adam29Adam29)) {
			return RenderAccessoryUtility.adam;
		}
		if(Library.contributors.contains(uuid)) {
			return RenderAccessoryUtility.wiki;
		}
		if(name.startsWith("Player")) {
			return RenderAccessoryUtility.test;
		}
		
		return null;
	}
	
	private static ModelBiped solModel;
	public static void renderSol(RenderPlayerEvent.SetArmorModel event) {

		if(RenderAccessoryUtility.solModel == null)
			RenderAccessoryUtility.solModel = new ModelArmorSolstice();
		
		RenderPlayer renderer = event.renderer;
		ModelBiped model = renderer.modelArmor;
		EntityPlayer player = event.entityPlayer;

		RenderAccessoryUtility.solModel.isSneak = model.isSneak;
		
		float interp = event.partialRenderTick;
		float yawHead = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * interp;
		float yawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * interp;
		float yaw = yawHead - yawOffset;
		float yawWrapped = MathHelper.wrapAngleTo180_float(yawHead - yawOffset);
		float pitch = player.rotationPitch;
		
		RenderAccessoryUtility.solModel.render(event.entityPlayer, 0.0F, 0.0F, yawWrapped, yaw, pitch, 0.0625F);
	}
	
	private static ModelBiped[] wingModels = new ModelBiped[10];
	public static void renderWings(RenderPlayerEvent.SetArmorModel event, int mode) {

		if(RenderAccessoryUtility.wingModels[mode] == null)
			RenderAccessoryUtility.wingModels[mode] = new ModelArmorWings(mode);
		
		RenderPlayer renderer = event.renderer;
		ModelBiped model = renderer.modelArmor;
		EntityPlayer player = event.entityPlayer;

		RenderAccessoryUtility.wingModels[mode].isSneak = model.isSneak;
		
		float interp = event.partialRenderTick;
		float yawHead = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * interp;
		float yawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * interp;
		float yaw = yawHead - yawOffset;
		float yawWrapped = MathHelper.wrapAngleTo180_float(yawHead - yawOffset);
		float pitch = player.rotationPitch;
		
		RenderAccessoryUtility.wingModels[mode].render(event.entityPlayer, 0.0F, 0.0F, yawWrapped, yaw, pitch, 0.0625F);
	}
}
