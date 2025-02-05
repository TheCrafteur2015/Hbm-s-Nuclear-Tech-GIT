package com.hbm.particle;

import com.hbm.main.ModEventHandlerClient;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleDeadLeaf extends EntityFX {

	public ParticleDeadLeaf(TextureManager texman, World world, double x, double y, double z) {
		super(world, x, y, z);
		this.particleIcon = ModEventHandlerClient.particleLeaf;
		this.particleRed =  this.particleGreen = this.particleBlue = 1F - world.rand.nextFloat() * 0.2F;
		this.particleScale = 0.1F;
		this.particleMaxAge = 200 + world.rand.nextInt(50);
		this.particleGravity = 0.2F;
	}

	@Override
	public int getFXLayer() {
		return 1;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if(!this.onGround) {
			this.motionX += this.rand.nextGaussian() * 0.002D;
			this.motionZ += this.rand.nextGaussian() * 0.002D;
			
			if(this.motionY < -0.025D)
				this.motionY = -0.025D;
		}
	}

	@Override
	public void renderParticle(Tessellator tess, float interp, float fX, float fY, float fZ, float sX, float sZ) {

		tess.setNormal(0.0F, 1.0F, 0.0F);

		tess.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);

		float scale = this.particleScale;
		float pX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) interp - EntityFX.interpPosX);
		float pY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) interp - EntityFX.interpPosY);
		float pZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) interp - EntityFX.interpPosZ);

		boolean flipU = getEntityId() % 2 == 0;
		boolean flipV = getEntityId() % 4 < 2;

		double minU = flipU ? this.particleIcon.getMaxU() : this.particleIcon.getMinU();
		double maxU = flipU ? this.particleIcon.getMinU() : this.particleIcon.getMaxU();
		double minV = flipV ? this.particleIcon.getMaxV() : this.particleIcon.getMinV();
		double maxV = flipV ? this.particleIcon.getMinV() : this.particleIcon.getMaxV();

		tess.addVertexWithUV((double) (pX - fX * scale - sX * scale), (double) (pY - fY * scale), (double) (pZ - fZ * scale - sZ * scale), maxU, maxV);
		tess.addVertexWithUV((double) (pX - fX * scale + sX * scale), (double) (pY + fY * scale), (double) (pZ - fZ * scale + sZ * scale), maxU, minV);
		tess.addVertexWithUV((double) (pX + fX * scale + sX * scale), (double) (pY + fY * scale), (double) (pZ + fZ * scale + sZ * scale), minU, minV);
		tess.addVertexWithUV((double) (pX + fX * scale - sX * scale), (double) (pY - fY * scale), (double) (pZ + fZ * scale - sZ * scale), minU, maxV);
	}
}