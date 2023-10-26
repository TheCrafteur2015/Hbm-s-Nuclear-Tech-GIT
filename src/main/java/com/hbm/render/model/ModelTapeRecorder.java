package com.hbm.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTapeRecorder extends ModelBase
{
    ModelRenderer Base;
    ModelRenderer Tape;
    ModelRenderer Part1;
    ModelRenderer Part2;
  
  public ModelTapeRecorder()
  {
    this.textureWidth = 128;
    this.textureHeight = 64;
    
    this.Base = new ModelRenderer(this, 0, 0);
    this.Base.addBox(0F, 0F, 0F, 16, 16, 12);
    this.Base.setRotationPoint(-8F, 8F, -4F);
    this.Base.setTextureSize(128, 64);
    this.Base.mirror = true;
    setRotation(this.Base, 0F, 0F, 0F);
    this.Tape = new ModelRenderer(this, 0, 28);
    this.Tape.addBox(0F, 0F, 0F, 8, 0, 2);
    this.Tape.setRotationPoint(-4F, 11F, -6F);
    this.Tape.setTextureSize(128, 64);
    this.Tape.mirror = true;
    setRotation(this.Tape, 0F, 0F, 0F);
    this.Part1 = new ModelRenderer(this, 9, 42);
    this.Part1.addBox(0F, 0F, 0F, 6, 6, 2);
    this.Part1.setRotationPoint(-7F, 11F, -6F);
    this.Part1.setTextureSize(128, 64);
    this.Part1.mirror = true;
    setRotation(this.Part1, 0F, 0F, 0F);
    this.Part2 = new ModelRenderer(this, 44, 42);
    this.Part2.addBox(0F, 0F, 0F, 6, 6, 2);
    this.Part2.setRotationPoint(1F, 11F, -6F);
    this.Part2.setTextureSize(128, 64);
    this.Part2.mirror = true;
    setRotation(this.Part2, 0F, 0F, 0F);
  }
  
  @Override
public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.Base.render(f5);
    this.Tape.render(f5);
    this.Part1.render(f5);
    this.Part2.render(f5);
  }
  
  public void renderModel(float f)
  {
	    this.Base.render(f);
	    this.Tape.render(f);
	    this.Part1.render(f);
	    this.Part2.render(f);
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
