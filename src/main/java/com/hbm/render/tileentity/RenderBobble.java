package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.blocks.generic.BlockBobble.BobbleType;
import com.hbm.blocks.generic.BlockBobble.TileEntityBobble;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.model.ModelUboinik;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderBobble extends TileEntitySpecialRenderer {
	
	public static RenderBobble instance = new RenderBobble();
	
	public static final IModelCustom bobble = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/trinkets/bobble.obj"));
	public static final ResourceLocation socket = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/socket.png");

	public static final ResourceLocation bobble_vaultboy = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/vaultboy.png");
	public static final ResourceLocation bobble_hbm = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/hbm.png");
	public static final ResourceLocation bobble_pu238 = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/pellet.png");
	public static final ResourceLocation bobble_frizzle = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/frizzle.png");
	public static final ResourceLocation bobble_vt = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/vt.png");
	public static final ResourceLocation bobble_doc = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/doctor17ph.png");
	public static final ResourceLocation bobble_blue = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/thebluehat.png");
	public static final ResourceLocation bobble_pheo = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/pheo.png");
	public static final ResourceLocation bobble_adam = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/adam29.png");
	public static final ResourceLocation bobble_uffr = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/uffr.png");
	public static final ResourceLocation bobble_vaer = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/vaer.png");
	public static final ResourceLocation bobble_nos = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/nos.png");
	public static final ResourceLocation bobble_drillgon = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/drillgon200.png");
	public static final ResourceLocation bobble_cirno = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/cirno.png");
	public static final ResourceLocation bobble_microwave = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/microwave.png");
	public static final ResourceLocation bobble_peep = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/peep.png");

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float intero) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		
		double scale = 0.25D;
		GL11.glScaled(scale, scale, scale);
		
		TileEntityBobble te = (TileEntityBobble) tile;
		BobbleType type = te.type;
		
		GL11.glRotated(22.5D * tile.getBlockMetadata() + 90, 0, -1, 0);
		
		renderBobble(type);
		
		GL11.glPopMatrix();
	}
	
	public void renderBobble(BobbleType type) {
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		
		bindTexture(RenderBobble.socket);
		RenderBobble.bobble.renderPart("Socket");
		
		switch(type) {
		case STRENGTH:
		case PERCEPTION:
		case ENDURANCE:
		case CHARISMA:
		case INTELLIGENCE:
		case AGILITY:
		case LUCK:		bindTexture(RenderBobble.bobble_vaultboy); break;
			
		case BOB:		bindTexture(RenderBobble.bobble_hbm); break;
		case PU238:		bindTexture(RenderBobble.bobble_pu238); break;
		case FRIZZLE:	bindTexture(RenderBobble.bobble_frizzle); break;
		case VT:		bindTexture(RenderBobble.bobble_vt); break;
		case DOC:		bindTexture(RenderBobble.bobble_doc); break;
		case BLUEHAT:	bindTexture(RenderBobble.bobble_blue); break;
		case PHEO:		bindTexture(RenderBobble.bobble_pheo); break;
		case CIRNO:		bindTexture(RenderBobble.bobble_cirno); break;
		case ADAM29:	bindTexture(RenderBobble.bobble_adam); break;
		case UFFR:		bindTexture(RenderBobble.bobble_uffr); break;
		case VAER:		bindTexture(RenderBobble.bobble_vaer); break;
		case NOS:		bindTexture(RenderBobble.bobble_nos); break;
		case DRILLGON:	bindTexture(RenderBobble.bobble_drillgon); break;
		case MICROWAVE:	bindTexture(RenderBobble.bobble_microwave); break;
		case PEEP:		bindTexture(RenderBobble.bobble_peep); break;
		default:		bindTexture(ResourceManager.universal);
		}
		
		switch(type) {
		case PU238:		renderPellet(type); break;
		case UFFR:		renderFumo(type); break;
		case DRILLGON:	renderDrillgon(type); break;
		default: renderGuy(type);
		}
		
		GL11.glPushMatrix();
		renderPost(type);
		GL11.glPopMatrix();
		
		renderSocket(type);
	}
	
	/*
	 * RENDER STANDARD PLAYER MODEL
	 */

	public static double[] rotLeftArm = {0, 0, 0};
	public static double[] rotRightArm = {0, 0, 0};
	public static double[] rotLeftLeg = {0, 0, 0};
	public static double[] rotRightLeg = {0, 0, 0};
	public static double rotBody = 0;
	public static double[] rotHead = {0, 0, 0};
	
	public void resetFigurineRotation() {
		RenderBobble.rotLeftArm = new double[]{0, 0, 0};
		RenderBobble.rotRightArm = new double[]{0, 0, 0};
		RenderBobble.rotLeftLeg = new double[]{0, 0, 0};
		RenderBobble.rotRightLeg = new double[]{0, 0, 0};
		RenderBobble.rotBody = 0;
		RenderBobble.rotHead = new double[]{0, 0, 0};
	}
	
	@SuppressWarnings("incomplete-switch") // shut up
	public void setupFigurineRotation(BobbleType type) {
		switch(type) {
		case STRENGTH:
			RenderBobble.rotLeftArm = new double[]{0, 25, 135};
			RenderBobble.rotRightArm = new double[]{0, -45, 135};
			RenderBobble.rotLeftLeg = new double[]{0, 0, -5};
			RenderBobble.rotRightLeg = new double[]{0, 0, 5};
			RenderBobble.rotHead = new double[]{15, 0, 0};
			break;
		case PERCEPTION:
			RenderBobble.rotLeftArm = new double[]{0, -15, 135};
			RenderBobble.rotRightArm = new double[]{-5, 0, 0};
			break;
		case ENDURANCE:
			RenderBobble.rotBody = 45;
			RenderBobble.rotLeftArm = new double[]{0, -25, 30};
			RenderBobble.rotRightArm = new double[]{0, 45, 30};
			RenderBobble.rotHead = new double[]{0, -45, 0};
			break;
		case CHARISMA:
			RenderBobble.rotBody = 45;
			RenderBobble.rotRightArm = new double[]{0, -45, 90};
			RenderBobble.rotLeftLeg = new double[]{0, 0, -5};
			RenderBobble.rotRightLeg = new double[]{0, 0, 5};
			RenderBobble.rotHead = new double[]{-5, -45, 0};
			break;
		case INTELLIGENCE:
			RenderBobble.rotHead = new double[]{0, 30, 0};
			RenderBobble.rotLeftArm = new double[]{5, 0, 0};
			RenderBobble.rotRightArm = new double[]{15, 0, 170};
			break;
		case AGILITY:
			RenderBobble.rotLeftArm = new double[]{0, 0, 60};
			RenderBobble.rotRightArm = new double[]{0, 0, -45};
			RenderBobble.rotLeftLeg = new double[]{0, 0, -15};
			RenderBobble.rotRightLeg = new double[]{0, 0, 45};
			break;
		case LUCK:
			RenderBobble.rotLeftArm = new double[]{135, 45, 0};
			RenderBobble.rotRightArm = new double[]{-135, -45, 0};
			RenderBobble.rotRightLeg = new double[]{-5, 0, 0};
			break;
		case VT:
			RenderBobble.rotLeftArm = new double[]{0, -45, 60};
			RenderBobble.rotRightArm = new double[]{0, 0, 45};
			RenderBobble.rotLeftLeg = new double[]{2, 0, 0};
			RenderBobble.rotRightLeg = new double[]{-2, 0, 0};
			break;
		case BLUEHAT:
			RenderBobble.rotLeftArm = new double[]{0, 90, 60};
			break;
		case FRIZZLE:
			RenderBobble.rotLeftArm = new double[]{0, 15, 45};
			RenderBobble.rotRightArm = new double[]{0, 0, 80};
			break;
		case ADAM29:
			RenderBobble.rotRightArm = new double[]{0, 0, 60};
			break;
		case PHEO:
			RenderBobble.rotLeftArm = new double[]{0, 0, 80};
			RenderBobble.rotRightArm = new double[]{0, 0, 45};
			break;
		case VAER:
			RenderBobble.rotLeftArm = new double[]{0, -5, 45};
			RenderBobble.rotRightArm = new double[]{0, 15, 45};
		case PEEP:
			RenderBobble.rotLeftArm = new double[]{0, 0, 1};
			RenderBobble.rotRightArm = new double[]{0, 0, 1};
			break;
		}
	}
	
	public void renderGuy(BobbleType type) {

		resetFigurineRotation();
		setupFigurineRotation(type);

		GL11.glPushMatrix();
		GL11.glRotated(RenderBobble.rotBody, 0, 1, 0);
		
		if(type == BobbleType.PEEP) RenderBobble.bobble.renderPart("PeepTail");
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		String suffix = type.skinLayers ? "" : "17";
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		//LEFT LEG//
		GL11.glPushMatrix();
			GL11.glTranslated(0, 1, -0.125);
			GL11.glRotated(RenderBobble.rotLeftLeg[0], 1, 0, 0);
			GL11.glRotated(RenderBobble.rotLeftLeg[1], 0, 1, 0);
			GL11.glRotated(RenderBobble.rotLeftLeg[2], 0, 0, 1);
			GL11.glTranslated(0, -1, 0.125);
			RenderBobble.bobble.renderPart("LL" + suffix);
		GL11.glPopMatrix();

		//RIGHT LEG//
		GL11.glPushMatrix();
			GL11.glTranslated(0, 1, 0.125);
			GL11.glRotated(RenderBobble.rotRightLeg[0], 1, 0, 0);
			GL11.glRotated(RenderBobble.rotRightLeg[1], 0, 1, 0);
			GL11.glRotated(RenderBobble.rotRightLeg[2], 0, 0, 1);
			GL11.glTranslated(0, -1, -0.125);
			RenderBobble.bobble.renderPart("RL" + suffix);
		GL11.glPopMatrix();
		
		//renderOrigin();

		//LEFT ARM//
		GL11.glPushMatrix();
			GL11.glTranslated(0, 1.625, -0.25);
			GL11.glRotated(RenderBobble.rotLeftArm[0], 1, 0, 0);
			GL11.glRotated(RenderBobble.rotLeftArm[1], 0, 1, 0);
			GL11.glRotated(RenderBobble.rotLeftArm[2], 0, 0, 1);
			GL11.glTranslated(0, -1.625, 0.25);
			RenderBobble.bobble.renderPart("LA" + suffix);
		GL11.glPopMatrix();

		//RIGHT ARM//
		GL11.glPushMatrix();
			GL11.glTranslated(0, 1.625, 0.25);
			GL11.glRotated(RenderBobble.rotRightArm[0], 1, 0, 0);
			GL11.glRotated(RenderBobble.rotRightArm[1], 0, 1, 0);
			GL11.glRotated(RenderBobble.rotRightArm[2], 0, 0, 1);
			GL11.glTranslated(0, -1.625, -0.25);
			RenderBobble.bobble.renderPart("RA" + suffix);
		GL11.glPopMatrix();

		//BODY//
		RenderBobble.bobble.renderPart("Body" + suffix);

		//HEAD//
		double speed = 0.005;
		double amplitude = 1;
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.75, 0);
		GL11.glRotated(Math.sin(System.currentTimeMillis() * speed) * amplitude, 1, 0, 0);
		GL11.glRotated(Math.sin(System.currentTimeMillis() * speed + (Math.PI * 0.5)) * amplitude, 0, 0, 1);
		
		GL11.glRotated(RenderBobble.rotHead[0], 1, 0, 0);
		GL11.glRotated(RenderBobble.rotHead[1], 0, 1, 0);
		GL11.glRotated(RenderBobble.rotHead[2], 0, 0, 1);
		
		GL11.glTranslated(0, -1.75, 0);
		RenderBobble.bobble.renderPart("Head" + suffix);

		if(type == BobbleType.VT) RenderBobble.bobble.renderPart("Horn");
		if(type == BobbleType.PEEP) RenderBobble.bobble.renderPart("PeepHat");
		
		if(type == BobbleType.VAER) {
			GL11.glTranslated(0.25, 1.9, 0.075);
			GL11.glRotated(-60, 0, 0, 1);
			GL11.glScaled(0.5, 0.5, 0.5);
			renderItem(new ItemStack(ModItems.cigarette));
		}
		
		if(type == BobbleType.NOS) {
			GL11.glTranslated(0, 1.75, 0);
			GL11.glRotated(180, 1, 0, 0);
			double scale = 0.095D;
			GL11.glScaled(scale, scale, scale);
			bindTexture(ResourceManager.hat);
			ResourceManager.armor_hat.renderAll();
		}
		
		GL11.glPopMatrix();
		
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glDisable(GL11.GL_BLEND);
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glPopMatrix();
	}
	
	public void renderPellet(BobbleType type) {

		GL11.glEnable(GL11.GL_CULL_FACE);

		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		GL11.glDisable(GL11.GL_LIGHTING);
		RenderBobble.bobble.renderPart("Pellet");

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);

		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(1.0F, 1.0F, 0.0F, 0.1F + (float) Math.sin(System.currentTimeMillis() * 0.001D) * 0.05F);
		RenderBobble.bobble.renderPart("PelletShine");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}
	
	public void renderFumo(BobbleType type) {

		GL11.glEnable(GL11.GL_CULL_FACE);
		RenderBobble.bobble.renderPart("Fumo");
		
		double speed = 0.005;
		double amplitude = 1;
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0.75, 0);
		GL11.glRotated(Math.sin(System.currentTimeMillis() * speed) * amplitude, 1, 0, 0);
		GL11.glRotated(Math.sin(System.currentTimeMillis() * speed + (Math.PI * 0.5)) * amplitude, 0, 0, 1);
		GL11.glTranslated(0, -0.75, 0);

		GL11.glDisable(GL11.GL_CULL_FACE);
		RenderBobble.bobble.renderPart("FumoHead");
		
		GL11.glPopMatrix();
	}
	
	public void renderDrillgon(BobbleType type) {
		RenderBobble.bobble.renderPart("Drillgon");
	}

	private ModelUboinik shotgun = new ModelUboinik();
	private ResourceLocation shot_tex = new ResourceLocation(RefStrings.MODID +":textures/models/ModelUboinik.png");
	
	/*
	 * RENDER ADDITIONAL ITEMS
	 */
	@SuppressWarnings("incomplete-switch")
	public void renderPost(BobbleType type) {
		switch(type) {
		case BLUEHAT:
			double scale = 0.0625D;
			GL11.glTranslated(0D, 0.875D, -0.5D);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glRotated(-160, 0, 0, 1);
			GL11.glScaled(scale, scale, scale);
			bindTexture(ResourceManager.hev_helmet);
			ResourceManager.armor_hev.renderPart("Head");
			break;
		case FRIZZLE:
			GL11.glPushMatrix();
			GL11.glTranslated(0.7, 1.7, 0.4);
			GL11.glScaled(0.5, 0.5, 0.5);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glRotated(-10, 1, 0, 0);
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.ff_gun_dark); ResourceManager.ff_nightmare.renderPart("Grip");
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.ff_gun_normal); ResourceManager.ff_nightmare.renderPart("Dark");
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.ff_gun_bright); ResourceManager.ff_nightmare.renderPart("Light");
			GL11.glPopMatrix();
			
			GL11.glTranslated(0.3, 1.4, -0.2);
			GL11.glRotated(-100, 1, 0, 0);
			GL11.glScaled(0.5, 0.5, 0.5);
			renderItem(new ItemStack(ModItems.coin_maskman, 1, 5));
			break;
		case ADAM29:
			GL11.glTranslated(0.4, 1.15, 0.4);
			GL11.glScaled(0.5, 0.5, 0.5);
			renderItem(new ItemStack(ModItems.can_redbomb));
			break;
		case PHEO:
			GL11.glTranslated(0.5, 1.15, 0.45);
			GL11.glRotated(-60, 1, 0, 0);
			GL11.glScaled(2, 2, 2);
			bindTexture(ResourceManager.shimmer_axe_tex);
			ResourceManager.shimmer_axe.renderAll();
			break;
		case BOB:
			GL11.glShadeModel(GL11.GL_SMOOTH);
			bindTexture(ResourceManager.mini_nuke_tex);
			GL11.glScaled(0.5, 0.5, 0.5);
			GL11.glPushMatrix();
			GL11.glTranslated(0.75, 1, 0.9);
			for(int i = 0; i < 3; i++) {
				ResourceManager.projectiles.renderPart("MiniNuke");
				GL11.glTranslated(-0.75, 0, 0);
			}
			GL11.glPopMatrix();
			bindTexture(ResourceManager.mini_mirv_tex);
			GL11.glTranslated(0, 0.75, -0.9);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glRotated(90, 1, 0, 0);
			ResourceManager.projectiles.renderPart("MiniMIRV");
			GL11.glShadeModel(GL11.GL_FLAT);
			break;
		case VAER:
			bindTexture(this.shot_tex);
			GL11.glTranslated(0.6, 1.5, 0);
			GL11.glRotated(140, 0, 0, 1);
			GL11.glRotated(-60, 0, 1, 0);
			GL11.glTranslated(-0.2, 0, 0);
			GL11.glScaled(0.5, 0.5, 0.5);
			this.shotgun.renderDud(0.0625F);
			break;
		}
	}
	
	private void renderItem(ItemStack stack) {
		bindTexture(TextureMap.locationItemsTexture);
		IIcon icon = stack.getIconIndex();
		float f14 = icon.getMinU();
		float f15 = icon.getMaxU();
		float f4 = icon.getMinV();
		float f5 = icon.getMaxV();
		ItemRenderer.renderItemIn2D(Tessellator.instance, f15, f4, f14, f5, icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
	}
	
	/*
	 * Creates a small diamond at 0/0, useful for figuring out where the translation is at
	 * to determine the rotation point
	 */
	public void renderOrigin() {

		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Tessellator tess = Tessellator.instance;
		tess.setColorOpaque_F(1F, 0F, 0F);
		tess.startDrawing(GL11.GL_TRIANGLES);
		
		double d = 0.125D;
		tess.addVertex(0, d, 0);
		tess.addVertex(d, 0, 0);
		tess.addVertex(0, 0, d);
		
		tess.addVertex(0, d, 0);
		tess.addVertex(-d, 0, 0);
		tess.addVertex(0, 0, d);
		
		tess.addVertex(0, d, 0);
		tess.addVertex(d, 0, 0);
		tess.addVertex(0, 0, -d);
		
		tess.addVertex(0, d, 0);
		tess.addVertex(-d, 0, 0);
		tess.addVertex(0, 0, -d);
		
		tess.addVertex(0, -d, 0);
		tess.addVertex(d, 0, 0);
		tess.addVertex(0, 0, d);
		
		tess.addVertex(0, -d, 0);
		tess.addVertex(d, 0, 0);
		tess.addVertex(0, 0, -d);
		
		tess.addVertex(0, -d, 0);
		tess.addVertex(-d, 0, 0);
		tess.addVertex(0, 0, d);
		
		tess.addVertex(0, -d, 0);
		tess.addVertex(-d, 0, 0);
		tess.addVertex(0, 0, -d);
		
		tess.draw();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	/*
	 * RENDER BASE PEDESTAL
	 */
	public void renderSocket(BobbleType type) {

		GL11.glDisable(GL11.GL_LIGHTING);
		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		float f3 = 0.01F;
		GL11.glTranslated(0.63, 0.175F, 0.0);
		GL11.glScalef(f3, -f3, f3);
		GL11.glTranslated(0, 0, font.getStringWidth(type.label) * 0.5D);
		GL11.glRotatef(90, 0, 1, 0);
		GL11.glDepthMask(false);
		GL11.glTranslatef(0, 1, 0);
		font.drawString(type.label, 0, 0, type == BobbleType.VT ? 0xff0000 : 0xffffff);
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	/*
	 * Override with Minecraft.getMinecraft()'s texman to prevent NPEs when invoked statically
	 */
	@Override
	protected void bindTexture(ResourceLocation loc) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(loc);
	}
}
