// Date: 06.04.2016 17:39:42
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX






package com.hbm.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelAt4 extends ModelBase
{
  //fields
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape4;
    ModelRenderer Shape5;
    ModelRenderer Shape6;
    ModelRenderer Shape7;
    ModelRenderer Shape8;
    ModelRenderer Shape9;
  
  public ModelAt4()
  {
    this.textureWidth = 64;
    this.textureHeight = 32;
    
      this.Shape1 = new ModelRenderer(this, 0, 0);
      this.Shape1.addBox(0F, 0F, 0F, 18, 3, 2);
      this.Shape1.setRotationPoint(-8F, 0F, 0F);
      this.Shape1.setTextureSize(64, 32);
      this.Shape1.mirror = true;
      setRotation(this.Shape1, 0F, 0F, 0F);
      this.Shape2 = new ModelRenderer(this, 0, 5);
      this.Shape2.addBox(0F, 0F, 0F, 18, 2, 3);
      this.Shape2.setRotationPoint(-8F, 0.5F, -0.5F);
      this.Shape2.setTextureSize(64, 32);
      this.Shape2.mirror = true;
      setRotation(this.Shape2, 0F, 0F, 0F);
      this.Shape3 = new ModelRenderer(this, 0, 10);
      this.Shape3.addBox(0F, 0F, 0F, 3, 4, 4);
      this.Shape3.setRotationPoint(10F, -0.5F, -1F);
      this.Shape3.setTextureSize(64, 32);
      this.Shape3.mirror = true;
      setRotation(this.Shape3, 0F, 0F, 0F);
      this.Shape4 = new ModelRenderer(this, 0, 18);
      this.Shape4.addBox(0F, 0F, 0F, 1, 3, 3);
      this.Shape4.setRotationPoint(-9F, 0F, -0.5F);
      this.Shape4.setTextureSize(64, 32);
      this.Shape4.mirror = true;
      setRotation(this.Shape4, 0F, 0F, 0F);
      this.Shape5 = new ModelRenderer(this, 14, 10);
      this.Shape5.addBox(0F, 0F, 0F, 1, 4, 4);
      this.Shape5.setRotationPoint(-10F, -0.5F, -1F);
      this.Shape5.setTextureSize(64, 32);
      this.Shape5.mirror = true;
      setRotation(this.Shape5, 0F, 0F, 0F);
      this.Shape6 = new ModelRenderer(this, 0, 24);
      this.Shape6.addBox(0F, 0F, 0F, 1, 3, 1);
      this.Shape6.setRotationPoint(-6F, 3F, 0.5F);
      this.Shape6.setTextureSize(64, 32);
      this.Shape6.mirror = true;
      setRotation(this.Shape6, 0F, 0F, 0F);
      this.Shape7 = new ModelRenderer(this, 4, 24);
      this.Shape7.addBox(0F, 0F, 0F, 1, 2, 1);
      this.Shape7.setRotationPoint(-3F, 3F, 0.5F);
      this.Shape7.setTextureSize(64, 32);
      this.Shape7.mirror = true;
      setRotation(this.Shape7, 0F, 0F, 0F);
      this.Shape8 = new ModelRenderer(this, 8, 18);
      this.Shape8.addBox(0F, 0F, 0F, 3, 1, 1);
      this.Shape8.setRotationPoint(-6F, -0.5F, -2F);
      this.Shape8.setTextureSize(64, 32);
      this.Shape8.mirror = true;
      setRotation(this.Shape8, 0F, 0F, 0F);
      this.Shape9 = new ModelRenderer(this, 0, 28);
      this.Shape9.addBox(0F, 0F, 0F, 1, 1, 2);
      this.Shape9.setRotationPoint(-5F, 0F, -1.5F);
      this.Shape9.setTextureSize(64, 32);
      this.Shape9.mirror = true;
      setRotation(this.Shape9, 0F, 0F, 0F);
  }
  
  @Override
public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.Shape1.render(f5);
    this.Shape2.render(f5);
    this.Shape3.render(f5);
    this.Shape4.render(f5);
    this.Shape5.render(f5);
    this.Shape6.render(f5);
    this.Shape7.render(f5);
    this.Shape8.render(f5);
    this.Shape9.render(f5);
  }
  
  public void renderModel(float f5)
  {
    this.Shape1.render(f5);
    this.Shape2.render(f5);
    this.Shape3.render(f5);
    this.Shape4.render(f5);
    this.Shape5.render(f5);
    this.Shape6.render(f5);
    this.Shape7.render(f5);
    this.Shape8.render(f5);
    this.Shape9.render(f5);
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
