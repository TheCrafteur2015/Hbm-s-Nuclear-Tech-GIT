package com.hbm.handler;

public class ThreeInts implements Comparable {
	
	public int x;
	public int y;
	public int z;
	
	public ThreeInts(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.x;
		result = prime * result + this.y;
		result = prime * result + this.z;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		ThreeInts other = (ThreeInts) obj;
		if (this.x != other.x)
			return false;
		if (this.y != other.y)
			return false;
		if (this.z != other.z)
			return false;
		return true;
	}

	@Override
	public int compareTo(Object o) {
		
		return equals(o) ? 0 : 1;
	}
}
