package com.hbm.render.model;

import com.hbm.interfaces.IHoldableWeapon;
import com.hbm.render.loader.ModelRendererObj;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class ModelArmorBase extends ModelBiped {

	int type;

	public ModelRendererObj head;
	public ModelRendererObj body;
	public ModelRendererObj leftArm;
	public ModelRendererObj rightArm;
	public ModelRendererObj leftLeg;
	public ModelRendererObj rightLeg;
	public ModelRendererObj leftFoot;
	public ModelRendererObj rightFoot;

	public ModelArmorBase(int type) {
		this.type = type;

		// generate null defaults to prevent major breakage from using
		// incomplete models
		this.head = new ModelRendererObj(null);
		this.body = new ModelRendererObj(null);
		this.leftArm = new ModelRendererObj(null).setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.rightArm = new ModelRendererObj(null).setRotationPoint(5.0F, 2.0F, 0.0F);
		this.leftLeg = new ModelRendererObj(null).setRotationPoint(1.9F, 12.0F, 0.0F);
		this.rightLeg = new ModelRendererObj(null).setRotationPoint(-1.9F, 12.0F, 0.0F);
		this.leftFoot = new ModelRendererObj(null).setRotationPoint(1.9F, 12.0F, 0.0F);
		this.rightFoot = new ModelRendererObj(null).setRotationPoint(-1.9F, 12.0F, 0.0F);
	}

	@Override
	public void setRotationAngles(float walkCycle, float walkAmplitude, float idleCycle, float headYaw, float headPitch, float scale, Entity entity) {
		//super.setRotationAngles(walkCycle, walkAmplitude, idleCycle, headYaw, headPitch, scale, entity);

		this.head.rotateAngleY = headYaw / (180F / (float) Math.PI);
		this.head.rotateAngleX = headPitch / (180F / (float) Math.PI);
		this.rightArm.rotateAngleX = MathHelper.cos(walkCycle * 0.6662F + (float) Math.PI) * 2.0F * walkAmplitude * 0.5F;
		this.leftArm.rotateAngleX = MathHelper.cos(walkCycle * 0.6662F) * 2.0F * walkAmplitude * 0.5F;
		this.rightArm.rotateAngleZ = 0.0F;
		this.leftArm.rotateAngleZ = 0.0F;
		this.rightFoot.rotateAngleX = this.rightLeg.rotateAngleX = MathHelper.cos(walkCycle * 0.6662F) * 1.4F * walkAmplitude;
		this.leftFoot.rotateAngleX = this.leftLeg.rotateAngleX = MathHelper.cos(walkCycle * 0.6662F + (float) Math.PI) * 1.4F * walkAmplitude;
		this.rightFoot.rotateAngleY = this.rightLeg.rotateAngleY = 0.0F;
		this.leftFoot.rotateAngleY = this.leftLeg.rotateAngleY = 0.0F;

		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;

			this.aimedBow = false;

			if(player.getHeldItem() != null) {

				int hold = 1;

				if(player.getItemInUseCount() > 0) {

					EnumAction action = player.getHeldItem().getItemUseAction();

					if(action == EnumAction.block)
						hold = 3;

					if(action == EnumAction.bow)
						this.aimedBow = true;
				}

				if(player.getHeldItem().getItem() instanceof IHoldableWeapon)
					this.aimedBow = true;

				this.rightArm.rotateAngleX = this.rightArm.rotateAngleX * 0.5F - ((float) Math.PI / 10F) * hold;
			}

			this.isSneak = player.isSneaking();
			this.isRiding = player.isRiding();
		}

		if(this.isRiding) {
			this.rightArm.rotateAngleX += -((float) Math.PI / 5F);
			this.leftArm.rotateAngleX += -((float) Math.PI / 5F);
			this.rightFoot.rotateAngleX = this.rightLeg.rotateAngleX = -((float) Math.PI * 2F / 5F);
			this.leftFoot.rotateAngleX = this.leftLeg.rotateAngleX = -((float) Math.PI * 2F / 5F);
			this.rightFoot.rotateAngleY = this.rightLeg.rotateAngleY = ((float) Math.PI / 10F);
			this.leftFoot.rotateAngleY = this.leftLeg.rotateAngleY = -((float) Math.PI / 10F);
		}

		if(this.heldItemLeft != 0) {
			this.leftArm.rotateAngleX = this.leftArm.rotateAngleX * 0.5F - ((float) Math.PI / 10F) * (float) this.heldItemLeft;
		}

		if(this.heldItemRight != 0) {
			this.rightArm.rotateAngleX = this.rightArm.rotateAngleX * 0.5F - ((float) Math.PI / 10F) * (float) this.heldItemRight;
		}

		this.rightArm.rotateAngleY = 0.0F;
		this.leftArm.rotateAngleY = 0.0F;
		float f6;
		float f7;

		if(this.onGround > -9990.0F) {
			f6 = this.onGround;
			this.body.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f6) * (float) Math.PI * 2.0F) * 0.2F;
			this.rightArm.rotationPointZ = MathHelper.sin(this.body.rotateAngleY) * 5.0F;
			this.rightArm.rotationPointX = -MathHelper.cos(this.body.rotateAngleY) * 5.0F;
			this.leftArm.rotationPointZ = -MathHelper.sin(this.body.rotateAngleY) * 5.0F;
			this.leftArm.rotationPointX = MathHelper.cos(this.body.rotateAngleY) * 5.0F;
			this.rightArm.rotateAngleY += this.body.rotateAngleY;
			this.leftArm.rotateAngleY += this.body.rotateAngleY;
			this.leftArm.rotateAngleX += this.body.rotateAngleY;
			f6 = 1.0F - this.onGround;
			f6 *= f6;
			f6 *= f6;
			f6 = 1.0F - f6;
			f7 = MathHelper.sin(f6 * (float) Math.PI);
			float f8 = MathHelper.sin(this.onGround * (float) Math.PI) * -(this.head.rotateAngleX - 0.7F) * 0.75F;
			this.rightArm.rotateAngleX = (float) ((double) this.rightArm.rotateAngleX - ((double) f7 * 1.2D + (double) f8));
			this.rightArm.rotateAngleY += this.body.rotateAngleY * 2.0F;
			this.rightArm.rotateAngleZ = MathHelper.sin(this.onGround * (float) Math.PI) * -0.4F;
		}

		if(this.isSneak) {
			this.body.rotateAngleX = 0.5F;
			this.rightArm.rotateAngleX += 0.4F;
			this.leftArm.rotateAngleX += 0.4F;
			this.rightFoot.offsetZ = this.rightLeg.offsetZ = 4.0F;
			this.leftFoot.offsetZ = this.leftLeg.offsetZ = 4.0F;
			this.rightFoot.offsetY = this.rightLeg.offsetY = -3.0F;
			this.leftFoot.offsetY = this.leftLeg.offsetY = -3.0F;
			this.head.offsetY = 1.0F;
		} else {
			this.body.rotateAngleX = 0.0F;
			this.rightFoot.offsetZ = this.rightLeg.offsetZ = 0.1F;
			this.leftFoot.offsetZ = this.leftLeg.offsetZ = 0.1F;
			this.rightFoot.offsetY = this.rightLeg.offsetY = 0.0F;
			this.leftFoot.offsetY = this.leftLeg.offsetY = 0.0F;
			this.head.offsetY = 0.0F;
		}

		this.rightArm.rotateAngleZ += MathHelper.cos(idleCycle * 0.09F) * 0.05F + 0.05F;
		this.leftArm.rotateAngleZ -= MathHelper.cos(idleCycle * 0.09F) * 0.05F + 0.05F;
		this.rightArm.rotateAngleX += MathHelper.sin(idleCycle * 0.067F) * 0.05F;
		this.leftArm.rotateAngleX -= MathHelper.sin(idleCycle * 0.067F) * 0.05F;

		if(this.aimedBow) {
			f6 = 0.0F;
			f7 = 0.0F;
			this.rightArm.rotateAngleZ = 0.0F;
			this.leftArm.rotateAngleZ = 0.0F;
			this.rightArm.rotateAngleY = -(0.1F - f6 * 0.6F) + this.head.rotateAngleY;
			this.leftArm.rotateAngleY = 0.1F - f6 * 0.6F + this.head.rotateAngleY + 0.4F;
			this.rightArm.rotateAngleX = -((float) Math.PI / 2F) + this.head.rotateAngleX;
			this.leftArm.rotateAngleX = -((float) Math.PI / 2F) + this.head.rotateAngleX;
			this.rightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
			this.leftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
			this.rightArm.rotateAngleZ += MathHelper.cos(idleCycle * 0.09F) * 0.05F + 0.05F;
			this.leftArm.rotateAngleZ -= MathHelper.cos(idleCycle * 0.09F) * 0.05F + 0.05F;
			this.rightArm.rotateAngleX += MathHelper.sin(idleCycle * 0.067F) * 0.05F;
			this.leftArm.rotateAngleX -= MathHelper.sin(idleCycle * 0.067F) * 0.05F;
		}
	}

	protected void bindTexture(ResourceLocation loc) {
		Minecraft.getMinecraft().renderEngine.bindTexture(loc);
	}
}
