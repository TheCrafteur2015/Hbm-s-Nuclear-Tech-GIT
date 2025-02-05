package com.hbm.particle;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleContrail extends EntityFX {

	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/particle/contrail.png");
	private TextureManager theRenderEngine;
	private int age;
	private int maxAge;

	public ParticleContrail(TextureManager p_i1213_1_, World p_i1218_1_, double p_i1218_2_, double p_i1218_4_, double p_i1218_6_) {
		super(p_i1218_1_, p_i1218_2_, p_i1218_4_, p_i1218_6_);
		this.theRenderEngine = p_i1213_1_;
		this.maxAge = 100 + this.rand.nextInt(40);
		
        this.particleRed = this.particleGreen = this.particleBlue = 0;
        this.particleScale = 1F;
	}

	public ParticleContrail(TextureManager p_i1213_1_, World p_i1218_1_, double p_i1218_2_, double p_i1218_4_, double p_i1218_6_, float red, float green, float blue, float scale) {
		super(p_i1218_1_, p_i1218_2_, p_i1218_4_, p_i1218_6_);
		this.theRenderEngine = p_i1213_1_;
		this.maxAge = 100 + this.rand.nextInt(40);

        this.particleRed = red;
        this.particleGreen = green;
        this.particleBlue = blue;
        
        this.particleScale = scale;
	}

	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		
		this.particleAlpha = 1 - ((float) this.age / (float) this.maxAge);
		
		++this.age;
		

		if (this.age == this.maxAge) {
			setDead();
		}
	}

	@Override
	public int getFXLayer() {
		return 3;
	}

	@Override
	public void renderParticle(Tessellator p_70539_1_, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {
		
		this.theRenderEngine.bindTexture(ParticleContrail.texture);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		RenderHelper.disableStandardItemLighting();
		
		Random urandom = new Random(getEntityId());
		
		for(int i = 0; i < 6; i++) {
			
			p_70539_1_.startDrawingQuads();

			
			float mod = urandom.nextFloat() * 0.2F + 0.2F;
			p_70539_1_.setColorRGBA_F(this.particleRed + mod, this.particleGreen + mod, this.particleBlue + mod, this.particleAlpha);
			p_70539_1_.setNormal(0.0F, 1.0F, 0.0F);
			p_70539_1_.setBrightness(240);
			
			float scale = this.particleAlpha + 0.5F * this.particleScale;
	        float pX = (float) ((this.prevPosX + (this.posX - this.prevPosX) * (double)p_70539_2_ - EntityFX.interpPosX) + urandom.nextGaussian() * 0.5);
	        float pY = (float) ((this.prevPosY + (this.posY - this.prevPosY) * (double)p_70539_2_ - EntityFX.interpPosY) + urandom.nextGaussian() * 0.5);
	        float pZ = (float) ((this.prevPosZ + (this.posZ - this.prevPosZ) * (double)p_70539_2_ - EntityFX.interpPosZ) + urandom.nextGaussian() * 0.5);
	        
			p_70539_1_.addVertexWithUV((double)(pX - p_70539_3_ * scale - p_70539_6_ * scale), (double)(pY - p_70539_4_ * scale), (double)(pZ - p_70539_5_ * scale - p_70539_7_ * scale), 1, 1);
			p_70539_1_.addVertexWithUV((double)(pX - p_70539_3_ * scale + p_70539_6_ * scale), (double)(pY + p_70539_4_ * scale), (double)(pZ - p_70539_5_ * scale + p_70539_7_ * scale), 1, 0);
			p_70539_1_.addVertexWithUV((double)(pX + p_70539_3_ * scale + p_70539_6_ * scale), (double)(pY + p_70539_4_ * scale), (double)(pZ + p_70539_5_ * scale + p_70539_7_ * scale), 0, 0);
			p_70539_1_.addVertexWithUV((double)(pX + p_70539_3_ * scale - p_70539_6_ * scale), (double)(pY - p_70539_4_ * scale), (double)(pZ + p_70539_5_ * scale - p_70539_7_ * scale), 0, 1);
			p_70539_1_.draw();
		}
		
		GL11.glPolygonOffset(0.0F, 0.0F);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
}
