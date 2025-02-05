package com.hbm.particle;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ParticleVortexGlow extends EntityFX {

	public static final ResourceLocation fresnel_ms = new ResourceLocation(RefStrings.MODID, "textures/particle/fresnel_ms.png");
	public float workingAlpha;

	public ParticleVortexGlow(World worldIn, double posXIn, double posYIn, double posZIn, float scale) {
		super(worldIn, posXIn, posYIn, posZIn);
		this.particleScale = scale;
	}

	public ParticleVortexGlow color(float colR, float colG, float colB, float colA) {
		this.particleRed = colR;
		this.particleGreen = colG;
		this.particleBlue = colB;
		this.particleAlpha = colA;
		this.workingAlpha = colA;
		return this;
	}

	public ParticleVortexGlow lifetime(int lifetime) {
		this.particleMaxAge = lifetime;
		return this;
	}

	@Override
	public void onUpdate() {
		this.particleAge++;
		if(this.particleAge >= this.particleMaxAge) {
			setDead();
		}
	}

	@Override
	public int getFXLayer() {
		return 3;
	}

	@Override
	public void renderParticle(Tessellator tess, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(ParticleVortexGlow.fresnel_ms);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		float timeScale = (this.particleAge + partialTicks) / (float) this.particleMaxAge;
		float shrink = MathHelper.clamp_float(1 - BobMathUtil.remap((float) MathHelper.clamp_float(timeScale, 0, 1), 0.6F, 1F, 0.6F, 1F), 0, 1);
		this.workingAlpha = shrink * this.particleAlpha;

		float f4 = 0.1F * (this.particleScale + shrink * this.particleScale * 4);

		float f5 = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) partialTicks - EntityFX.interpPosX);
		float f6 = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) partialTicks - EntityFX.interpPosY);
		float f7 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTicks - EntityFX.interpPosZ);
		Vec3[] avec3d = new Vec3[] {
				Vec3.createVectorHelper((double) (-rotationX * f4 - rotationXY * f4), (double) (-rotationZ * f4), (double) (-rotationYZ * f4 - rotationXZ * f4)),
				Vec3.createVectorHelper((double) (-rotationX * f4 + rotationXY * f4), (double) (rotationZ * f4), (double) (-rotationYZ * f4 + rotationXZ * f4)),
				Vec3.createVectorHelper((double) (rotationX * f4 + rotationXY * f4), (double) (rotationZ * f4), (double) (rotationYZ * f4 + rotationXZ * f4)),
				Vec3.createVectorHelper((double) (rotationX * f4 - rotationXY * f4), (double) (-rotationZ * f4), (double) (rotationYZ * f4 - rotationXZ * f4))
		};

		tess.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
		tess.setNormal(0.0F, 1.0F, 0.0F);
		tess.setBrightness(240);

		tess.startDrawingQuads();
		tess.addVertexWithUV((double) f5 + avec3d[0].xCoord, (double) f6 + avec3d[0].yCoord, (double) f7 + avec3d[0].zCoord, 1, 1);
		tess.addVertexWithUV((double) f5 + avec3d[1].xCoord, (double) f6 + avec3d[1].yCoord, (double) f7 + avec3d[1].zCoord, 1, 0);
		tess.addVertexWithUV((double) f5 + avec3d[2].xCoord, (double) f6 + avec3d[2].yCoord, (double) f7 + avec3d[2].zCoord, 0, 0);
		tess.addVertexWithUV((double) f5 + avec3d[3].xCoord, (double) f6 + avec3d[3].yCoord, (double) f7 + avec3d[3].zCoord, 0, 1);

		tess.draw();

		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
	}
}
