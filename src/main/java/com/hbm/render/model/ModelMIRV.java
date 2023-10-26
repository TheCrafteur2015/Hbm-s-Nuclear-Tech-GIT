// Date: 13.11.2016 12:38:48
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX






package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMIRV extends ModelBase
{
  //fields
    ModelRenderer Shape9;
    ModelRenderer Shape10;
    ModelRenderer Shape11;
    ModelRenderer Shape12;
  
  public ModelMIRV()
  {
    this.textureWidth = 64;
    this.textureHeight = 32;
    
      this.Shape9 = new ModelRenderer(this, 0, 0);
      this.Shape9.addBox(0F, 0F, 0F, 10, 4, 2);
      this.Shape9.setRotationPoint(-3F, -2F, -1F);
      this.Shape9.setTextureSize(64, 32);
      this.Shape9.mirror = true;
      setRotation(this.Shape9, 0F, 0F, 0F);
      this.Shape10 = new ModelRenderer(this, 0, 6);
      this.Shape10.addBox(0F, 0F, 0F, 10, 2, 4);
      this.Shape10.setRotationPoint(-3F, -1F, -2F);
      this.Shape10.setTextureSize(64, 32);
      this.Shape10.mirror = true;
      setRotation(this.Shape10, 0F, 0F, 0F);
      this.Shape11 = new ModelRenderer(this, 0, 12);
      this.Shape11.addBox(0F, 0F, 0F, 10, 3, 3);
      this.Shape11.setRotationPoint(-3F, -1.5F, -1.5F);
      this.Shape11.setTextureSize(64, 32);
      this.Shape11.mirror = true;
      setRotation(this.Shape11, 0F, 0F, 0F);
      this.Shape12 = new ModelRenderer(this, 0, 18);
      this.Shape12.addBox(0F, 0F, 0F, 4, 1, 1);
      this.Shape12.setRotationPoint(0F, -3F, -1F);
      this.Shape12.setTextureSize(64, 32);
      this.Shape12.mirror = true;
      setRotation(this.Shape12, 0F, 0F, 0F);
  }
  
  public void renderAll(float f5)
  {
    this.Shape9.render(f5);
    this.Shape10.render(f5);
    this.Shape11.render(f5);
	GL11.glDisable(GL11.GL_CULL_FACE);
    this.Shape12.render(f5);
	GL11.glEnable(GL11.GL_CULL_FACE);
  }
  
  @Override
public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.Shape9.render(f5);
    this.Shape10.render(f5);
    this.Shape11.render(f5);
	GL11.glDisable(GL11.GL_CULL_FACE);
    this.Shape12.render(f5);
	GL11.glEnable(GL11.GL_CULL_FACE);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  @Override
public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }

}
