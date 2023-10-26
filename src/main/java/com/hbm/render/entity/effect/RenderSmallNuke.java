package com.hbm.render.entity.effect;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.lib.RefStrings;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderSmallNuke extends Render {

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
    public float color = 0;
    public int alpha = 0;
    
    public RenderSmallNuke() {
    	this.blastModel = AdvancedModelLoader.loadModel(RenderSmallNuke.objTesterModelRL);
    	this.blastTexture = new ResourceLocation(RefStrings.MODID, "textures/models/NukeCloudFire.png");
    	this.ringModel = AdvancedModelLoader.loadModel(RenderSmallNuke.ringModelRL);
    	this.ringTexture = new ResourceLocation(RefStrings.MODID, "textures/models/Ring2.png");
    	this.ringBigModel = AdvancedModelLoader.loadModel(RenderSmallNuke.ringBigModelRL);
    	this.ringBigTexture = new ResourceLocation(RefStrings.MODID, "textures/models/Ring2.png");
    	this.scale = 0;
    	this.ring = 0;
        this.color = 0;
        this.alpha = 0;
    }

	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {

		GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glScalef(4.0F, 4.0F, 4.0F);
        if(this.scale < 1)
        	this.scale += 0.01F;
        {
            GL11.glScalef(this.scale, this.scale, this.scale);
        }
        
        if(((EntityNukeCloudSmall)p_76986_1_).age > ((EntityNukeCloudSmall)p_76986_1_).maxAge - 150)
        {
        	if(this.color < 0.75)
        	this.color += 0.005;
        }
        
        GL11.glColor4f(1.0f - this.color, 1.0f - this.color, 1.0f - this.color, 1.0F - this.alpha);
        
        //GL11.glTranslatef(0.0F, -2.5F, 0.0F);
        //GL11.glTranslatef(0.0F, 11F, 0.0F);

        bindTexture(this.blastTexture);
        this.blastModel.renderAll();
        
        GL11.glTranslatef(0, 15F, 0);
        GL11.glScalef(20.0F, 5.0F, 20.0F);
        
        bindTexture(this.ringBigTexture);
        this.ringBigModel.renderAll();

        GL11.glScalef(1/this.scale, 1/this.scale, 1/this.scale);
        GL11.glTranslatef(0, -4.75F, 0);
        //GL11.glScalef(1/20.0F, 1/5.0F, 1/20.0F);
    	this.ring += 0.1F;
        {
            GL11.glScalef(this.ring, 5, this.ring);
        }

        GL11.glTranslatef(0, 1F, 0);
        bindTexture(this.ringTexture);
        this.ringModel.renderAll();
        
		GL11.glPopMatrix();
		
		if(((EntityNukeCloudSmall)p_76986_1_).age >= ((EntityNukeCloudSmall)p_76986_1_).maxAge - 1)
		{
			this.scale = 0;
			this.ring = 0;
			this.color = 0;
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return null;
	}

}
