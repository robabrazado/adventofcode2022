package com.robabrazado.aoc2022.day09;

public class XY implements Cloneable {
	private int x;
	private int y;
	
	public XY(int x, int y) {
		this.x = x;
		this.y = y;
		return;
	}
	
	public XY() {
		this(0, 0);
		return;
	}
	
	public int x() {
		return this.x;
	}
	
	public int y() {
		return this.y;
	}
	
	public XY move(XY offset) {
		this.x += offset.x;
		this.y += offset.y;
		return (XY) this.clone();
	}
	
	// Returns offset such that this.move(offset).equals(target)
	public XY getOffsetFrom(XY target) {
		return new XY(target.x - this.x, target.y - this.y);
	}
	
	public boolean equals(Object o) {
		boolean equal = false;
		
		if (o instanceof XY) {
			XY other = (XY) o;
			equal = this.x == other.x && this.y == other.y;
		}
		
		return equal;
	}
	
	public int hashCode() {
		return Integer.valueOf(this.x).hashCode() ^ Integer.valueOf(this.y).hashCode();
	}
	
	public Object clone() {
		return new XY(this.x, this.y);
	}
	
	public String toString() {
		return String.valueOf(this.x) + "," + String.valueOf(this.y);
	}
}
