package com.hbm.animloader;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.util.BobMathUtil;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.util.MathHelper;

public class AnimatedModel {

	public static FloatBuffer auxGLMatrix = GLAllocation.createDirectFloatBuffer(16);

	//Pointless...
	public AnimationController controller;

	public String name = "";

	public float[] transform;

	boolean hasGeometry = true;
	boolean hasTransform = false;

	public String geo_name = "";
	public AnimatedModel parent;
	public List<AnimatedModel> children = new ArrayList<>();
	int callList;

	public AnimatedModel() {
	}

	public void renderAnimated(long sysTime) {
		renderAnimated(sysTime, null);
	}
	
	public void renderAnimated(long sysTime, IAnimatedModelCallback c) {
		if(this.controller.activeAnim == AnimationWrapper.EMPTY) {
			render(c);
			return;
		}

		AnimationWrapper activeAnim = this.controller.activeAnim;
		int numKeyFrames = activeAnim.anim.numKeyFrames;
		int diff = (int) (sysTime - activeAnim.startTime);
		diff *= activeAnim.speedScale;
		if(diff > activeAnim.anim.length) {
			int diff2 = diff % activeAnim.anim.length;
			switch(activeAnim.endResult.type) {
			case END:
				this.controller.activeAnim = AnimationWrapper.EMPTY;
				render(c);
				return;
			case REPEAT:
				activeAnim.startTime = sysTime - diff2;
				break;
			case REPEAT_REVERSE:
				activeAnim.startTime = sysTime - diff2;
				activeAnim.reverse = !activeAnim.reverse;
				break;
			case START_NEW:
				activeAnim.cloneStats(activeAnim.endResult.next);
				activeAnim.startTime = sysTime - diff2;
				break;
			case STAY:
				activeAnim.startTime = sysTime - activeAnim.anim.length;
				break;
			}
		}
		diff = (int) (sysTime - activeAnim.startTime);
		if(activeAnim.reverse)
			diff = activeAnim.anim.length - diff;
		diff *= activeAnim.speedScale;
		float remappedTime = MathHelper.clamp_float(BobMathUtil.remap(diff, 0, activeAnim.anim.length, 0, numKeyFrames - 1), 0, numKeyFrames - 1);
		float diffN = BobMathUtil.remap01_clamp(diff, 0, activeAnim.anim.length);
		int index = (int) remappedTime;
		int first = index;
		int next;
		if(index < numKeyFrames - 1) {
			next = index + 1;
		} else {
			next = first;
		}
		
		renderWithIndex((float) AnimatedModel.fract(remappedTime), first, next, diffN, c);
		this.controller.activeAnim.prevFrame = first;
	}

	protected void renderWithIndex(float inter, int firstIndex, int nextIndex, float diffN, IAnimatedModelCallback c) {
		GL11.glPushMatrix();
		boolean hidden = false;
		if(this.hasTransform) {
			Transform[] transforms = this.controller.activeAnim.anim.objectTransforms.get(this.name);
			if(transforms != null) {
				hidden = transforms[firstIndex].hidden;
				transforms[firstIndex].interpolateAndApply(transforms[nextIndex], inter);
			} else {
				AnimatedModel.auxGLMatrix.put(this.transform);
				AnimatedModel.auxGLMatrix.rewind();
				GL11.glMultMatrix(AnimatedModel.auxGLMatrix);
			}
		}
		if(c != null)
			hidden |= c.onRender(this.controller.activeAnim.prevFrame, firstIndex, this.callList, diffN, this.name);
		if(this.hasGeometry && !hidden) {
			GL11.glCallList(this.callList);
		}
		if(c != null)
			c.postRender(this.controller.activeAnim.prevFrame, firstIndex, this.callList, diffN, this.name);
		for(AnimatedModel m : this.children) {
			m.renderWithIndex(inter, firstIndex, nextIndex, diffN, c);
		}
		GL11.glPopMatrix();
	}

	public void render() {
		render(null);
	}
	
	public void render(IAnimatedModelCallback c) {
		GL11.glPushMatrix();
		if(this.hasTransform) {
			AnimatedModel.auxGLMatrix.put(this.transform);
			AnimatedModel.auxGLMatrix.rewind();
			GL11.glMultMatrix(AnimatedModel.auxGLMatrix);
		}
		boolean hidden = false;
		if(c != null)
			hidden = c.onRender(-1, -1, this.callList, -1, this.name);
		if(this.hasGeometry && !hidden) {
			GL11.glCallList(this.callList);
		}
		if(c != null)
			c.postRender(-1, -1, this.callList, -1, this.name);
		for(AnimatedModel m : this.children) {
			m.render(c);
		}
		GL11.glPopMatrix();
	}

	private static float fract(float number) {
		return (float) (number - Math.floor(number));
	}
	
	public static interface IAnimatedModelCallback {
		//(prevFrame, currentFrame, model, diffN, modelName)
		public boolean onRender(int prevFrame, int currentFrame, int model, float diffN, String modelName);
		public default void postRender(int prevFrame, int currentFrame, int model, float diffN, String modelName){};
	}
}