package com.hbm.render.loader;

public class RUVertice {
	
	public float x;
	public float y;
	public float z;

	public RUVertice(float X, float Y, float Z) {
		this.x = X;
		this.y = Y;
		this.z = Z;
	}

	public RUVertice normalize() {
		float l = (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
		this.x /= l;
		this.y /= l;
		this.z /= l;
		return this;
	}
}
