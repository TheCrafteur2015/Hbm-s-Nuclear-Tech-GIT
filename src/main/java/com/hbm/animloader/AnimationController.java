package com.hbm.animloader;

public class AnimationController {
	
	//Drillgon200: You know what? I'm pretty sure this class is entirely pointless and just acts as a stupid getter/setter for the wrapper.
	//TODO delete

	protected AnimationWrapper activeAnim = AnimationWrapper.EMPTY;

	public void setAnim(AnimationWrapper w) {
		this.activeAnim = w;
	}

	public void stopAnim() {
		this.activeAnim = AnimationWrapper.EMPTY;
	}

	public AnimationWrapper getAnim() {
		return this.activeAnim;
	}

}