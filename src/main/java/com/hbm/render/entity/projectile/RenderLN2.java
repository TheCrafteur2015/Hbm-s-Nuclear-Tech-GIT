package com.hbm.render.entity.projectile;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.entity.projectile.EntityLN2;
import com.hbm.items.ModItems;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class RenderLN2 extends Render {
    private Item field_94151_a;
    public RenderLN2(Item p_i1259_1_, int p_i1259_2_)
    {
        this.field_94151_a = p_i1259_1_;
    }

    public RenderLN2(Item p_i1260_1_)
    {
        this(p_i1260_1_, 0);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    @Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
    	if(p_76986_1_ instanceof EntityLN2)
    	{
    		EntityLN2 fx = (EntityLN2)p_76986_1_;

    		if(fx.ticksExisted <= fx.maxAge && fx.ticksExisted >= fx.maxAge / 10 * 9)
    		{
    			this.field_94151_a = ModItems.ln2_10;
    		}

    		if(fx.ticksExisted < fx.maxAge / 10 * 9 && fx.ticksExisted >= fx.maxAge / 10 * 8)
    		{
    			this.field_94151_a = ModItems.ln2_9;
    		}

    		if(fx.ticksExisted < fx.maxAge / 10 * 8 && fx.ticksExisted >= fx.maxAge / 10 * 7)
    		{
    			this.field_94151_a = ModItems.ln2_8;
    		}

    		if(fx.ticksExisted < fx.maxAge / 10 * 7 && fx.ticksExisted >= fx.maxAge / 10 * 6)
    		{
    			this.field_94151_a = ModItems.ln2_7;
    		}

    		if(fx.ticksExisted < fx.maxAge / 10 * 6 && fx.ticksExisted >= fx.maxAge / 10 * 5)
    		{
    			this.field_94151_a = ModItems.ln2_6;
    		}

    		if(fx.ticksExisted < fx.maxAge / 10 * 5 && fx.ticksExisted >= fx.maxAge / 10 * 4)
    		{
    			this.field_94151_a = ModItems.ln2_5;
    		}

    		if(fx.ticksExisted < fx.maxAge / 10 * 4 && fx.ticksExisted >= fx.maxAge / 10 * 3)
    		{
    			this.field_94151_a = ModItems.ln2_4;
    		}

    		if(fx.ticksExisted < fx.maxAge / 10 * 3 && fx.ticksExisted >= fx.maxAge / 10 * 2)
    		{
    			this.field_94151_a = ModItems.ln2_3;
    		}

    		if(fx.ticksExisted < fx.maxAge / 10 * 2 && fx.ticksExisted >= fx.maxAge / 10 * 1)
    		{
    			this.field_94151_a = ModItems.ln2_2;
    		}
    		
    		if(fx.ticksExisted < fx.maxAge / 10 && fx.ticksExisted >= 0 && !fx.isDead)
    		{
    			this.field_94151_a = ModItems.ln2_1;
    		}
    		
    		IIcon iicon = this.field_94151_a.getIconFromDamage(0);

            if (iicon != null)
            {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                GL11.glScalef(0.5F, 0.5F, 0.5F);
                GL11.glScalef(7.5F, 7.5F, 7.5F);
                GL11.glTranslatef(0.0F, -0.25F, 0.0F);
                bindEntityTexture(p_76986_1_);
                Tessellator tessellator = Tessellator.instance;

                func_77026_a(tessellator, iicon);
                GL11.glDisable(GL12.GL_RESCALE_NORMAL);
                GL11.glPopMatrix();
            }
    	}
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
        return TextureMap.locationItemsTexture;
    }

    private void func_77026_a(Tessellator p_77026_1_, IIcon p_77026_2_)
    {
        float f = p_77026_2_.getMinU();
        float f1 = p_77026_2_.getMaxU();
        float f2 = p_77026_2_.getMinV();
        float f3 = p_77026_2_.getMaxV();
        float f4 = 1.0F;
        float f5 = 0.5F;
        float f6 = 0.25F;
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        p_77026_1_.startDrawingQuads();
        p_77026_1_.setNormal(0.0F, 1.0F, 0.0F);
        p_77026_1_.addVertexWithUV(0.0F - f5, 0.0F - f6, 0.0D, f, f3);
        p_77026_1_.addVertexWithUV(f4 - f5, 0.0F - f6, 0.0D, f1, f3);
        p_77026_1_.addVertexWithUV(f4 - f5, f4 - f6, 0.0D, f1, f2);
        p_77026_1_.addVertexWithUV(0.0F - f5, f4 - f6, 0.0D, f, f2);
        p_77026_1_.draw();
    }

}
