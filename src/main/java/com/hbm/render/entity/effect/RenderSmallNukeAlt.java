package com.hbm.render.entity.effect;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.lib.RefStrings;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderSmallNukeAlt extends Render {

	private static final ResourceLocation objTesterModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/NukeCloudSmall.obj");
	private IModelCustom blastModel;
    private ResourceLocation blastTexture;
	private static final ResourceLocation ringModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/Ring.obj");
	private IModelCustom ringModel;
    private ResourceLocation ringTexture;
	private static final ResourceLocation ringBigModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/RingBig.obj");
	private IModelCustom ringBigModel;
    private ResourceLocation ringBigTexture;
    public float scale = 0;
    public float ring = 0;
    
    public RenderSmallNukeAlt() {
    	this.blastModel = AdvancedModelLoader.loadModel(RenderSmallNukeAlt.objTesterModelRL);
    	this.blastTexture = new ResourceLocation(RefStrings.MODID, "textures/models/NukeCloudFire.png");
    	this.ringModel = AdvancedModelLoader.loadModel(RenderSmallNukeAlt.ringModelRL);
    	this.ringTexture = new ResourceLocation(RefStrings.MODID, "textures/models/Ring2.png");
    	this.ringBigModel = AdvancedModelLoader.loadModel(RenderSmallNukeAlt.ringBigModelRL);
    	this.ringBigTexture = new ResourceLocation(RefStrings.MODID, "textures/models/Ring2.png");
    	this.scale = 0;
    	this.ring = 0;
    }

	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
		if(((EntityNukeCloudSmall)p_76986_1_).age > 100)
		{
			renderMush((EntityNukeCloudSmall)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
			renderCloud((EntityNukeCloudSmall)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
		} else {
			renderFlare((EntityNukeCloudSmall)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
		}
		renderRing((EntityNukeCloudSmall)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
	
	public void renderMush(EntityNukeCloudSmall p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {

		GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        if(p_76986_1_.age < 150)
        {
        	//GL11.glTranslatef(0.0F, -60F + ((p_76986_1_.age - 100) * 60 / 50), 0.0F);
        	GL11.glTranslatef(0.0F, p_76986_1_.height, 0.0F);
        }
        GL11.glScalef(4.0F, 4.0F, 4.0F);
        
        bindTexture(this.blastTexture);
        this.blastModel.renderAll();
        GL11.glPopMatrix();
	}
	
	public void renderCloud(EntityNukeCloudSmall p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {

		GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
    	GL11.glTranslatef(0.0F, 60F, 0.0F);
        /*if(scale < 1.5)
        {
        	scale += 0.02;
        }*/
        //GL11.glScalef(p_76986_1_.scale, 1.0F, p_76986_1_.scale);
        GL11.glScalef(50F, 25.0F, 50F);
        
        bindTexture(this.ringBigTexture);
        this.ringBigModel.renderAll();
        GL11.glPopMatrix();
	}
	
	public void renderRing(EntityNukeCloudSmall p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {

		GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
    	GL11.glTranslatef(0.0F, 18F, 0.0F);
    	//ring += 0.1F;
        
        bindTexture(this.ringTexture);
        this.ringModel.renderAll();
        GL11.glPopMatrix();
	}
	
	public void renderFlare(EntityNukeCloudSmall p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {

        Tessellator tessellator = Tessellator.instance;
		RenderHelper.disableStandardItemLighting();
        float f1 = (p_76986_1_.age + 2.0F) / 200.0F;
        float f2 = 0.0F;
        int count = 250;
        
        if(p_76986_1_.age < 250)
        {
        	count = p_76986_1_.age * 3;
        }

        if (f1 > 0.8F)
        {
            f2 = (f1 - 0.8F) / 0.2F;
        }

        Random random = new Random(432L);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDepthMask(false);
        GL11.glPushMatrix();
        //GL11.glTranslatef(0.0F, -1.0F, -2.0F);
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_ + 15, (float)p_76986_6_);
        GL11.glScalef(5F, 5F, 5F);
        
        //for (int i = 0; (float)i < (f1 + f1 * f1) / 2.0F * 60.0F; ++i)
        for(int i = 0; i < count; i++)
        {
            GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F + f1 * 90.0F, 0.0F, 0.0F, 1.0F);
            tessellator.startDrawing(6);
            float f3 = random.nextFloat() * 20.0F + 5.0F + f2 * 10.0F;
            float f4 = random.nextFloat() * 2.0F + 1.0F + f2 * 2.0F;
            //tessellator.setColorRGBA_I(16777215, (int)(255.0F * (1.0F - f2)));
            tessellator.setColorRGBA_I(59345715, (int)(255.0F * (1.0F - f2)));
            tessellator.addVertex(0.0D, 0.0D, 0.0D);
            //tessellator.setColorRGBA_I(16711935, 0);
            tessellator.setColorRGBA_I(59345735, 0);
            tessellator.addVertex(-0.866D * f4, f3, -0.5F * f4);
            tessellator.addVertex(0.866D * f4, f3, -0.5F * f4);
            tessellator.addVertex(0.0D, f3, 1.0F * f4);
            tessellator.addVertex(-0.866D * f4, f3, -0.5F * f4);
            tessellator.draw();
        }

        GL11.glPopMatrix();
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        RenderHelper.enableStandardItemLighting();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return null;
	}

}
