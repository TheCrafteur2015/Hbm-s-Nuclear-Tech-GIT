package com.hbm.particle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.main.ResourceManager;
import com.hbm.util.Tuple.Pair;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleSpentCasing extends EntityFX {
	
	public static final Random rand = new Random();
	private static float dScale = 0.05F, smokeJitter = 0.001F;

	private int maxSmokeGen = 120;
	private double smokeLift = 0.5D;
	private int nodeLife = 30;

	private final List<Pair<Vec3, Double>> smokeNodes = new ArrayList<>();

	private final TextureManager textureManager;

	private final SpentCasing config;
	private boolean isSmoking;

	private float momentumPitch, momentumYaw;
	private boolean onGroundPreviously = false;
	private double maxHeight;

	public ParticleSpentCasing(TextureManager textureManager, World world, double x, double y, double z, double mx, double my, double mz, float momentumPitch, float momentumYaw, SpentCasing config) {
		super(world, x, y, z, 0, 0, 0);
		this.textureManager = textureManager;
		this.momentumPitch = momentumPitch;
		this.momentumYaw = momentumYaw;
		this.config = config;

		this.particleMaxAge = config.getMaxAge();
		this.isSmoking = ParticleSpentCasing.rand.nextFloat() < config.getSmokeChance();
		this.maxSmokeGen = config.getSmokeDuration();
		this.smokeLift = config.getSmokeLift();
		this.nodeLife = config.getSmokeNodeLife();
		
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;

		this.motionX = mx;
		this.motionY = my;
		this.motionZ = mz;

		this.particleGravity = 8F;

		this.maxHeight = y;
	}

	@Override
	public int getFXLayer() {
		return 3;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if(this.motionY > 0 && this.posY > this.maxHeight)
			this.maxHeight = this.posY;

		if(!this.onGroundPreviously && this.onGround)
			tryPlayBounceSound();

		if(!this.onGroundPreviously && this.onGround) {
			
			this.onGroundPreviously = true;
			this.motionY = Math.log10(this.maxHeight - this.posY + 2);
			this.momentumPitch = (float) ParticleSpentCasing.rand.nextGaussian() * this.config.getBouncePitch();
			this.momentumYaw = (float) ParticleSpentCasing.rand.nextGaussian() * this.config.getBounceYaw();
			this.maxHeight = this.posY;
			
		} else if(this.onGroundPreviously && !this.onGround) {
			this.onGroundPreviously = false;
		}

		if(this.particleAge > this.maxSmokeGen && !this.smokeNodes.isEmpty())
			this.smokeNodes.clear();

		if(this.isSmoking && this.particleAge <= this.maxSmokeGen) {

			for(Pair<Vec3, Double> pair : this.smokeNodes) {
				Vec3 node = pair.getKey();

				node.xCoord += ParticleSpentCasing.rand.nextGaussian() * ParticleSpentCasing.smokeJitter;
				node.zCoord += ParticleSpentCasing.rand.nextGaussian() * ParticleSpentCasing.smokeJitter;
				node.yCoord += this.smokeLift * ParticleSpentCasing.dScale;
				
				pair.value = Math.max(0, pair.value - (1D / (double) this.nodeLife));
			}

			if(this.particleAge < this.maxSmokeGen || this.inWater) {
				this.smokeNodes.add(new Pair<>(Vec3.createVectorHelper(0, 0, 0), this.smokeNodes.isEmpty() ? 0.0D : 1D));
			}
		}

		this.prevRotationPitch = this.rotationPitch;
		this.prevRotationYaw = this.rotationYaw;

		if(this.onGround) {
			this.rotationPitch = 0;
		} else {
			this.rotationPitch += this.momentumPitch;
			this.rotationYaw += this.momentumYaw;
		}
	}

	/** Used for frame-perfect translation of smoke */
	private boolean setupDeltas = false;
	private double prevRenderX;
	private double prevRenderY;
	private double prevRenderZ;

	@Override
	public void renderParticle(Tessellator tessellator, float interp, float x, float y, float z, float tx, float tz) {

		GL11.glPushMatrix();
		RenderHelper.enableStandardItemLighting();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glDepthMask(true);

		double pX = this.prevPosX + (this.posX - this.prevPosX) * interp;
		double pY = this.prevPosY + (this.posY - this.prevPosY) * interp;
		double pZ = this.prevPosZ + (this.posZ - this.prevPosZ) * interp;
		
		if(!this.setupDeltas) {
			this.prevRenderX = pX;
			this.prevRenderY = pY;
			this.prevRenderZ = pZ;
			this.setupDeltas = true;
		}
		
		int brightness = this.worldObj.getLightBrightnessForSkyBlocks(MathHelper.floor_double(pX), MathHelper.floor_double(pY), MathHelper.floor_double(pZ), 0);
		int lX = brightness % 65536;
		int lY = brightness / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lX / 1.0F, (float)lY / 1.0F);

		this.textureManager.bindTexture(ResourceManager.casings_tex);
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		double dX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)interp;
		double dY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)interp;
		double dZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)interp;

		GL11.glTranslated(pX - dX, pY - dY - this.height / 4 + this.config.getScaleY() * 0.01, pZ - dZ);

		GL11.glScalef(ParticleSpentCasing.dScale, ParticleSpentCasing.dScale, ParticleSpentCasing.dScale);

		GL11.glRotatef(180 - this.rotationYaw, 0, 1, 0);
		GL11.glRotatef(-this.rotationPitch, 1, 0, 0);

		GL11.glScalef(this.config.getScaleX(), this.config.getScaleY(), this.config.getScaleZ());

		int index = 0;
		for(String name : this.config.getType().partNames) {
			int col = this.config.getColors()[index]; //unsafe on purpose, set your colors properly or else...!
			Color color = new Color(col);
			GL11.glColor3f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
			ResourceManager.casings.renderPart(name);
			index++;
		}
		
		GL11.glColor3f(1F, 1F, 1F);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(pX - dX, pY - dY - this.height / 4, pZ - dZ);
		//GL11.glScalef(dScale, dScale, dScale);
		//GL11.glScalef(config.getScaleX(), config.getScaleY(), config.getScaleZ());

		if(!this.smokeNodes.isEmpty()) {
			tessellator.startDrawingQuads();
			tessellator.setNormal(0F, 1F, 0F);
			
			float scale = this.config.getScaleX() * 0.5F * ParticleSpentCasing.dScale;
			Vec3 vec = Vec3.createVectorHelper(scale, 0, 0);
			float yaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * interp;
			vec.rotateAroundY((float) Math.toRadians(-yaw));
			
			double deltaX = this.prevRenderX - pX;
			double deltaY = this.prevRenderY - pY;
			double deltaZ = this.prevRenderZ - pZ;
			
			for(Pair<Vec3, Double> pair : this.smokeNodes) {
				Vec3 pos = pair.getKey();
				double mult = 1D;
				pos.xCoord += deltaX * mult;
				pos.yCoord += deltaY * mult;
				pos.zCoord += deltaZ * mult;
			}

			for(int i = 0; i < this.smokeNodes.size() - 1; i++) {
				final Pair<Vec3, Double> node = this.smokeNodes.get(i), past = this.smokeNodes.get(i + 1);
				final Vec3 nodeLoc = node.getKey(), pastLoc = past.getKey();
				float nodeAlpha = node.getValue().floatValue();
				float pastAlpha = past.getValue().floatValue();
				
				double timeAlpha = 1D - (double) this.particleAge / (double) this.maxSmokeGen;
				nodeAlpha *= timeAlpha;
				pastAlpha *= timeAlpha;

				tessellator.setNormal(0F, 1F, 0F);
				tessellator.setColorRGBA_F(1F, 1F, 1F, nodeAlpha);
				tessellator.addVertex(nodeLoc.xCoord, nodeLoc.yCoord, nodeLoc.zCoord);
				tessellator.setColorRGBA_F(1F, 1F, 1F, 0F);
				tessellator.addVertex(nodeLoc.xCoord + vec.xCoord, nodeLoc.yCoord, nodeLoc.zCoord + vec.zCoord);
				tessellator.setColorRGBA_F(1F, 1F, 1F, 0F);
				tessellator.addVertex(pastLoc.xCoord + vec.xCoord, pastLoc.yCoord, pastLoc.zCoord + vec.zCoord);
				tessellator.setColorRGBA_F(1F, 1F, 1F, pastAlpha);
				tessellator.addVertex(pastLoc.xCoord, pastLoc.yCoord, pastLoc.zCoord);

				tessellator.setColorRGBA_F(1F, 1F, 1F, nodeAlpha);
				tessellator.addVertex(nodeLoc.xCoord, nodeLoc.yCoord, nodeLoc.zCoord);
				tessellator.setColorRGBA_F(1F, 1F, 1F, 0F);
				tessellator.addVertex(nodeLoc.xCoord - vec.xCoord, nodeLoc.yCoord, nodeLoc.zCoord - vec.zCoord);
				tessellator.setColorRGBA_F(1F, 1F, 1F, 0F);
				tessellator.addVertex(pastLoc.xCoord - vec.xCoord, pastLoc.yCoord, pastLoc.zCoord - vec.zCoord);
				tessellator.setColorRGBA_F(1F, 1F, 1F, pastAlpha);
				tessellator.addVertex(pastLoc.xCoord, pastLoc.yCoord, pastLoc.zCoord);
			}

			GL11.glAlphaFunc(GL11.GL_GREATER, 0F);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_CULL_FACE);
			tessellator.draw();
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glAlphaFunc(GL11.GL_GEQUAL, 0.1F);
		}

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
		
		RenderHelper.disableStandardItemLighting();
		
		this.prevRenderX = pX;
		this.prevRenderY = pY;
		this.prevRenderZ = pZ;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float p_70070_1_) {
		int i = MathHelper.floor_double(this.posX);
		int j = MathHelper.floor_double(this.posZ);

		if(this.worldObj.blockExists(i, 0, j)) {
			double d0 = (this.boundingBox.maxY - this.boundingBox.minY) * 0.66D;
			int k = MathHelper.floor_double(this.posY - (double) this.yOffset + d0);
			return this.worldObj.getLightBrightnessForSkyBlocks(i, k, j, 0);
		} else {
			return 0;
		  }
    }

	private void tryPlayBounceSound() {

		String sound = this.config.getSound();
		
		if(sound != null && !sound.isEmpty()) {
			this.worldObj.playSoundAtEntity(this, sound, 2, 1);
		}
	}
}