package com.robabrazado.aoc2022.day15;

public class Coords {
	private int x;
	private int y;
	
	// "x=aa, y=bb"
	public Coords(String s) {
		String[] parts = s.split("(x|, y)=", 3);
		this.x = Integer.valueOf(parts[1]);
		this.y = Integer.valueOf(parts[2]);
		return;
	}
	
	public Coords(int x, int y) {
		this.x = x;
		this.y = y;
		return;
	}
	
	public int x() {
		return this.x;
	}
	
	public int y() {
		return this.y;
	}
	
	public int getManhattanDistance(Coords other) {
		return Math.abs(this.x - other.x) + Math.abs(this.y - other.y); 
	}
	
	@Override
	public boolean equals(Object o) {
		boolean equal = false;
		
		if (o instanceof Coords) {
			Coords other = (Coords) o;
			equal = this.x == other.x && this.y == other.y;
		}
		
		return equal;
	}
	
	@Override
	public int hashCode() {
		return Integer.valueOf(this.x).hashCode() ^ Integer.valueOf(this.y).hashCode();
	}
	
	@Override
	public String toString() {
		return Integer.toString(this.x) + "," + Integer.toString(this.y); 
	}
}
