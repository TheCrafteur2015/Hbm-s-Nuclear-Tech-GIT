// Date: 07.11.2016 21:36:37
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX






package com.hbm.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelEMPRay extends ModelBase
{
  //fields
    ModelRenderer Body;
    ModelRenderer BodyConnector;
    ModelRenderer BodyFront;
    ModelRenderer BodyPlateLeft;
    ModelRenderer BodyPlateRight;
    ModelRenderer BodyPlateBottom;
    ModelRenderer Rib1;
    ModelRenderer Rib2;
    ModelRenderer Rib3;
    ModelRenderer Rib4;
    ModelRenderer Rib5;
    ModelRenderer Rib6;
    ModelRenderer Rib7;
    ModelRenderer Stock;
    ModelRenderer StockBottom;
    ModelRenderer StockPlate;
    ModelRenderer CrankPivot;
    ModelRenderer CrankPlate;
    ModelRenderer CrankHandleBase;
    ModelRenderer CrankHandle;
    ModelRenderer HandlePivot;
    ModelRenderer HandleLeft;
    ModelRenderer HandleRight;
    ModelRenderer Handle;
    ModelRenderer ShieldTop;
    ModelRenderer ShieldBottom;
    ModelRenderer Battery;
    ModelRenderer BatteryTop;
    ModelRenderer BatteryBottom;
    ModelRenderer Wire;
    ModelRenderer SpindelPivot;
    ModelRenderer SpindelBase1;
    ModelRenderer Coil1;
    ModelRenderer SpindelTip1;
    ModelRenderer SpindelBase2;
    ModelRenderer Coil2;
    ModelRenderer SpindelTip2;
    ModelRenderer SpindelBase3;
    ModelRenderer Coil3;
    ModelRenderer SpindelTip3;
    ModelRenderer WireRight;
    ModelRenderer WireLeft;
  
  public ModelEMPRay()
  {
    this.textureWidth = 256;
    this.textureHeight = 128;
    
      this.Body = new ModelRenderer(this, 0, 96);
      this.Body.addBox(0F, 0F, 0F, 20, 16, 16);
      this.Body.setRotationPoint(0F, 0F, -8F);
      this.Body.setTextureSize(64, 32);
      this.Body.mirror = true;
      setRotation(this.Body, 0F, 0F, 0F);
      this.BodyConnector = new ModelRenderer(this, 0, 80);
      this.BodyConnector.addBox(0F, 0F, 0F, 15, 8, 8);
      this.BodyConnector.setRotationPoint(-15F, 0F, -4F);
      this.BodyConnector.setTextureSize(64, 32);
      this.BodyConnector.mirror = true;
      setRotation(this.BodyConnector, 0F, 0F, 0F);
      this.BodyFront = new ModelRenderer(this, 72, 96);
      this.BodyFront.addBox(0F, 0F, 0F, 5, 16, 16);
      this.BodyFront.setRotationPoint(-20F, 0F, -8F);
      this.BodyFront.setTextureSize(64, 32);
      this.BodyFront.mirror = true;
      setRotation(this.BodyFront, 0F, 0F, 0F);
      this.BodyPlateLeft = new ModelRenderer(this, 46, 84);
      this.BodyPlateLeft.addBox(-16F, 0F, 0F, 16, 8, 4);
      this.BodyPlateLeft.setRotationPoint(0F, 0F, -8F);
      this.BodyPlateLeft.setTextureSize(64, 32);
      this.BodyPlateLeft.mirror = true;
      setRotation(this.BodyPlateLeft, 0F, 0.2617994F, 0F);
      this.BodyPlateRight = new ModelRenderer(this, 86, 84);
      this.BodyPlateRight.addBox(-16F, 0F, -4F, 16, 8, 4);
      this.BodyPlateRight.setRotationPoint(0F, 0F, 8F);
      this.BodyPlateRight.setTextureSize(64, 32);
      this.BodyPlateRight.mirror = true;
      setRotation(this.BodyPlateRight, 0F, -0.2617994F, 0F);
      this.BodyPlateBottom = new ModelRenderer(this, 0, 65);
      this.BodyPlateBottom.addBox(-18F, -7F, 0F, 18, 7, 8);
      this.BodyPlateBottom.setRotationPoint(0F, 16F, -4F);
      this.BodyPlateBottom.setTextureSize(64, 32);
      this.BodyPlateBottom.mirror = true;
      setRotation(this.BodyPlateBottom, 0F, 0F, 0.4886922F);
      this.Rib1 = new ModelRenderer(this, 240, 0);
      this.Rib1.addBox(0F, 0F, 0F, 0, 8, 8);
      this.Rib1.setRotationPoint(-13.5F, 8F, -4F);
      this.Rib1.setTextureSize(64, 32);
      this.Rib1.mirror = true;
      setRotation(this.Rib1, 0F, 0F, 0F);
      this.Rib2 = new ModelRenderer(this, 222, 0);
      this.Rib2.addBox(0F, 0F, 0F, 0, 8, 9);
      this.Rib2.setRotationPoint(-11.5F, 8F, -4.5F);
      this.Rib2.setTextureSize(64, 32);
      this.Rib2.mirror = true;
      setRotation(this.Rib2, 0F, 0F, 0F);
      this.Rib3 = new ModelRenderer(this, 202, 0);
      this.Rib3.addBox(0F, 0F, 0F, 0, 8, 10);
      this.Rib3.setRotationPoint(-9.5F, 8F, -5F);
      this.Rib3.setTextureSize(64, 32);
      this.Rib3.mirror = true;
      setRotation(this.Rib3, 0F, 0F, 0F);
      this.Rib4 = new ModelRenderer(this, 180, 0);
      this.Rib4.addBox(0F, 0F, 0F, 0, 8, 11);
      this.Rib4.setRotationPoint(-7.5F, 8F, -5.5F);
      this.Rib4.setTextureSize(64, 32);
      this.Rib4.mirror = true;
      setRotation(this.Rib4, 0F, 0F, 0F);
      this.Rib5 = new ModelRenderer(this, 156, 0);
      this.Rib5.addBox(0F, 0F, 0F, 0, 8, 12);
      this.Rib5.setRotationPoint(-5.5F, 8F, -6F);
      this.Rib5.setTextureSize(64, 32);
      this.Rib5.mirror = true;
      setRotation(this.Rib5, 0F, 0F, 0F);
      this.Rib6 = new ModelRenderer(this, 130, 0);
      this.Rib6.addBox(0F, 0F, 0F, 0, 8, 13);
      this.Rib6.setRotationPoint(-3.5F, 8F, -6.5F);
      this.Rib6.setTextureSize(64, 32);
      this.Rib6.mirror = true;
      setRotation(this.Rib6, 0F, 0F, 0F);
      this.Rib7 = new ModelRenderer(this, 102, 0);
      this.Rib7.addBox(0F, 0F, 0F, 0, 8, 14);
      this.Rib7.setRotationPoint(-1.5F, 8F, -7F);
      this.Rib7.setTextureSize(64, 32);
      this.Rib7.mirror = true;
      setRotation(this.Rib7, 0F, 0F, 0F);
      this.Stock = new ModelRenderer(this, 114, 116);
      this.Stock.addBox(0F, 0F, 0F, 25, 6, 6);
      this.Stock.setRotationPoint(20F, 10F, -3F);
      this.Stock.setTextureSize(64, 32);
      this.Stock.mirror = true;
      setRotation(this.Stock, 0F, 0F, 0F);
      this.StockBottom = new ModelRenderer(this, 114, 104);
      this.StockBottom.addBox(0F, 0F, 0F, 4, 6, 6);
      this.StockBottom.setRotationPoint(41F, 16F, -3F);
      this.StockBottom.setTextureSize(64, 32);
      this.StockBottom.mirror = true;
      setRotation(this.StockBottom, 0F, 0F, 0F);
      this.StockPlate = new ModelRenderer(this, 134, 92);
      this.StockPlate.addBox(0F, -18F, 0F, 6, 18, 6);
      this.StockPlate.setRotationPoint(41F, 22F, -3F);
      this.StockPlate.setTextureSize(64, 32);
      this.StockPlate.mirror = true;
      setRotation(this.StockPlate, 0F, 0F, -1.22173F);
      this.CrankPivot = new ModelRenderer(this, 0, 63);
      this.CrankPivot.addBox(0F, -0.5F, -0.5F, 3, 1, 1);
      this.CrankPivot.setRotationPoint(20F, 2F, 0F);
      this.CrankPivot.setTextureSize(64, 32);
      this.CrankPivot.mirror = true;
      setRotation(this.CrankPivot, -0.4363323F, 0F, 0F);
      this.CrankPlate = new ModelRenderer(this, 0, 55);
      this.CrankPlate.addBox(0F, -1F, -1F, 1, 6, 2);
      this.CrankPlate.setRotationPoint(21.5F, 2F, 0F);
      this.CrankPlate.setTextureSize(64, 32);
      this.CrankPlate.mirror = true;
      setRotation(this.CrankPlate, -0.4363323F, 0F, 0F);
      this.CrankHandleBase = new ModelRenderer(this, 0, 53);
      this.CrankHandleBase.addBox(0F, 3.5F, -0.5F, 2, 1, 1);
      this.CrankHandleBase.setRotationPoint(22F, 2F, 0F);
      this.CrankHandleBase.setTextureSize(64, 32);
      this.CrankHandleBase.mirror = true;
      setRotation(this.CrankHandleBase, -0.4363323F, 0F, 0F);
      this.CrankHandle = new ModelRenderer(this, 0, 49);
      this.CrankHandle.addBox(0F, 3F, -1F, 6, 2, 2);
      this.CrankHandle.setRotationPoint(24F, 2F, 0F);
      this.CrankHandle.setTextureSize(64, 32);
      this.CrankHandle.mirror = true;
      setRotation(this.CrankHandle, -0.4363323F, 0F, 0F);
      this.HandlePivot = new ModelRenderer(this, 52, 60);
      this.HandlePivot.addBox(-1F, -1F, 0F, 2, 2, 22);
      this.HandlePivot.setRotationPoint(7F, 7F, -11F);
      this.HandlePivot.setTextureSize(64, 32);
      this.HandlePivot.mirror = true;
      setRotation(this.HandlePivot, 0F, 0F, 0.4363323F);
      this.HandleLeft = new ModelRenderer(this, 100, 67);
      this.HandleLeft.addBox(-1.5F, -14F, 0F, 3, 16, 1);
      this.HandleLeft.setRotationPoint(7F, 7F, -9.5F);
      this.HandleLeft.setTextureSize(64, 32);
      this.HandleLeft.mirror = true;
      setRotation(this.HandleLeft, 0F, 0F, 0.4363323F);
      this.HandleRight = new ModelRenderer(this, 108, 67);
      this.HandleRight.addBox(-1.5F, -14F, 0F, 3, 16, 1);
      this.HandleRight.setRotationPoint(7F, 7F, 8.5F);
      this.HandleRight.setTextureSize(64, 32);
      this.HandleRight.mirror = true;
      setRotation(this.HandleRight, 0F, 0F, 0.4363323F);
      this.Handle = new ModelRenderer(this, 52, 31);
      this.Handle.addBox(-2.5F, -19F, 0F, 5, 5, 24);
      this.Handle.setRotationPoint(7F, 7F, -12F);
      this.Handle.setTextureSize(64, 32);
      this.Handle.mirror = true;
      setRotation(this.Handle, 0F, 0F, 0.4363323F);
      this.ShieldTop = new ModelRenderer(this, 64, 0);
      this.ShieldTop.addBox(0F, 0F, 0F, 0, 12, 18);
      this.ShieldTop.setRotationPoint(-11F, -14F, -9F);
      this.ShieldTop.setTextureSize(64, 32);
      this.ShieldTop.mirror = true;
      setRotation(this.ShieldTop, 0F, 0F, 0F);
      this.ShieldBottom = new ModelRenderer(this, 36, 0);
      this.ShieldBottom.addBox(0F, 0F, 0F, 0, 9, 14);
      this.ShieldBottom.setRotationPoint(-11F, -2F, -7F);
      this.ShieldBottom.setTextureSize(64, 32);
      this.ShieldBottom.mirror = true;
      setRotation(this.ShieldBottom, 0F, 0F, -0.6981317F);
      this.Battery = new ModelRenderer(this, 10, 56);
      this.Battery.addBox(0F, 0F, 0F, 4, 6, 3);
      this.Battery.setRotationPoint(13F, 6F, -11F);
      this.Battery.setTextureSize(64, 32);
      this.Battery.mirror = true;
      setRotation(this.Battery, 0F, 0F, 0F);
      this.BatteryTop = new ModelRenderer(this, 24, 60);
      this.BatteryTop.addBox(0F, 0F, 0F, 5, 1, 4);
      this.BatteryTop.setRotationPoint(12.5F, 5F, -11.5F);
      this.BatteryTop.setTextureSize(64, 32);
      this.BatteryTop.mirror = true;
      setRotation(this.BatteryTop, 0F, 0F, 0F);
      this.BatteryBottom = new ModelRenderer(this, 24, 55);
      this.BatteryBottom.addBox(0F, 0F, 0F, 5, 1, 4);
      this.BatteryBottom.setRotationPoint(12.5F, 12F, -11.5F);
      this.BatteryBottom.setTextureSize(64, 32);
      this.BatteryBottom.mirror = true;
      setRotation(this.BatteryBottom, 0F, 0F, 0F);
      this.Wire = new ModelRenderer(this, 0, 44);
      this.Wire.addBox(0F, -4F, 0F, 2, 4, 1);
      this.Wire.setRotationPoint(14F, 5F, -10F);
      this.Wire.setTextureSize(64, 32);
      this.Wire.mirror = true;
      setRotation(this.Wire, -0.5235988F, 0F, 0F);
      this.SpindelPivot = new ModelRenderer(this, 0, 36);
      this.SpindelPivot.addBox(0F, 0F, 0F, 4, 3, 3);
      this.SpindelPivot.setRotationPoint(-24F, 8.5F, -1.5F);
      this.SpindelPivot.setTextureSize(64, 32);
      this.SpindelPivot.mirror = true;
      setRotation(this.SpindelPivot, 0F, 0F, 0F);
      this.SpindelBase1 = new ModelRenderer(this, 0, 27);
      this.SpindelBase1.addBox(0F, -5F, -1F, 2, 5, 2);
      this.SpindelBase1.setRotationPoint(-23F, 10F, 0F);
      this.SpindelBase1.setTextureSize(64, 32);
      this.SpindelBase1.mirror = true;
      setRotation(this.SpindelBase1, 0F, 0F, 0F);
      this.Coil1 = new ModelRenderer(this, 0, 17);
      this.Coil1.addBox(0F, -6F, -2F, 12, 4, 4);
      this.Coil1.setRotationPoint(-35F, 10F, 0F);
      this.Coil1.setTextureSize(64, 32);
      this.Coil1.mirror = true;
      setRotation(this.Coil1, 0F, 0F, 0F);
      this.SpindelTip1 = new ModelRenderer(this, 0, 11);
      this.SpindelTip1.addBox(0F, -5F, -1F, 1, 2, 2);
      this.SpindelTip1.setRotationPoint(-36F, 10F, 0F);
      this.SpindelTip1.setTextureSize(64, 32);
      this.SpindelTip1.mirror = true;
      setRotation(this.SpindelTip1, 0F, 0F, 0F);
      this.SpindelBase2 = new ModelRenderer(this, 0, 27);
      this.SpindelBase2.addBox(0F, -5F, -1F, 2, 5, 2);
      this.SpindelBase2.setRotationPoint(-23F, 10F, 0F);
      this.SpindelBase2.setTextureSize(64, 32);
      this.SpindelBase2.mirror = true;
      setRotation(this.SpindelBase2, 2.094395F, 0F, 0F);
      this.Coil2 = new ModelRenderer(this, 0, 17);
      this.Coil2.addBox(0F, -6F, -2F, 12, 4, 4);
      this.Coil2.setRotationPoint(-35F, 10F, 0F);
      this.Coil2.setTextureSize(64, 32);
      this.Coil2.mirror = true;
      setRotation(this.Coil2, 2.094395F, 0F, 0F);
      this.SpindelTip2 = new ModelRenderer(this, 0, 11);
      this.SpindelTip2.addBox(0F, -5F, -1F, 1, 2, 2);
      this.SpindelTip2.setRotationPoint(-36F, 10F, 0F);
      this.SpindelTip2.setTextureSize(64, 32);
      this.SpindelTip2.mirror = true;
      setRotation(this.SpindelTip2, 2.094395F, 0F, 0F);
      this.SpindelBase3 = new ModelRenderer(this, 0, 27);
      this.SpindelBase3.addBox(0F, -5F, -1F, 2, 5, 2);
      this.SpindelBase3.setRotationPoint(-23F, 10F, 0F);
      this.SpindelBase3.setTextureSize(64, 32);
      this.SpindelBase3.mirror = true;
      setRotation(this.SpindelBase3, -2.094395F, 0F, 0F);
      this.Coil3 = new ModelRenderer(this, 0, 17);
      this.Coil3.addBox(0F, -6F, -2F, 12, 4, 4);
      this.Coil3.setRotationPoint(-35F, 10F, 0F);
      this.Coil3.setTextureSize(64, 32);
      this.Coil3.mirror = true;
      setRotation(this.Coil3, -2.094395F, 0F, 0F);
      this.SpindelTip3 = new ModelRenderer(this, 0, 11);
      this.SpindelTip3.addBox(0F, -5F, -1F, 1, 2, 2);
      this.SpindelTip3.setRotationPoint(-36F, 10F, 0F);
      this.SpindelTip3.setTextureSize(64, 32);
      this.SpindelTip3.mirror = true;
      setRotation(this.SpindelTip3, -2.094395F, 0F, 0F);
      this.WireRight = new ModelRenderer(this, 0, 2);
      this.WireRight.addBox(0F, 0F, 0F, 15, 1, 1);
      this.WireRight.setRotationPoint(-15F, 13F, 4F);
      this.WireRight.setTextureSize(64, 32);
      this.WireRight.mirror = true;
      setRotation(this.WireRight, 0F, 0F, 0F);
      this.WireLeft = new ModelRenderer(this, 0, 0);
      this.WireLeft.addBox(0F, 0F, 0F, 15, 1, 1);
      this.WireLeft.setRotationPoint(-15F, 13F, -5F);
      this.WireLeft.setTextureSize(64, 32);
      this.WireLeft.mirror = true;
      setRotation(this.WireLeft, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, float rot)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.Body.render(f5);
    this.BodyConnector.render(f5);
    this.BodyFront.render(f5);
    this.BodyPlateLeft.render(f5);
    this.BodyPlateRight.render(f5);
    this.BodyPlateBottom.render(f5);
    this.Rib1.render(f5);
    this.Rib2.render(f5);
    this.Rib3.render(f5);
    this.Rib4.render(f5);
    this.Rib5.render(f5);
    this.Rib6.render(f5);
    this.Rib7.render(f5);
    this.Stock.render(f5);
    this.StockBottom.render(f5);
    this.StockPlate.render(f5);
    this.CrankPivot.render(f5);
    this.CrankPlate.render(f5);
    this.CrankHandleBase.render(f5);
    this.CrankHandle.render(f5);
    this.HandlePivot.render(f5);
    this.HandleLeft.render(f5);
    this.HandleRight.render(f5);
    this.Handle.render(f5);
    this.ShieldTop.render(f5);
    this.ShieldBottom.render(f5);
    this.Battery.render(f5);
    this.BatteryTop.render(f5);
    this.BatteryBottom.render(f5);
    this.Wire.render(f5);
    this.SpindelPivot.render(f5);
    this.SpindelBase1.rotateAngleX += rot;
    this.Coil1.rotateAngleX += rot;
    this.SpindelTip1.rotateAngleX += rot;
    this.SpindelBase2.rotateAngleX += rot;
    this.Coil2.rotateAngleX += rot;
    this.SpindelTip2.rotateAngleX += rot;
    this.SpindelBase3.rotateAngleX += rot;
    this.Coil3.rotateAngleX += rot;
    this.SpindelTip3.rotateAngleX += rot;
    this.SpindelBase1.render(f5);
    this.Coil1.render(f5);
    this.SpindelTip1.render(f5);
    this.SpindelBase2.render(f5);
    this.Coil2.render(f5);
    this.SpindelTip2.render(f5);
    this.SpindelBase3.render(f5);
    this.Coil3.render(f5);
    this.SpindelTip3.render(f5);
    this.WireRight.render(f5);
    this.WireLeft.render(f5);
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
