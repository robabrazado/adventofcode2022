package com.robabrazado.aoc2022.day09;

public enum Direction {
	U(0, 1),
	D(0, -1),
	L(-1, 0),
	R(1, 0);
	
	private XY offset;
	
	Direction(int x, int y) {
		this.offset = new XY(x, y);
	}
	
	public XY offset() {
		return (XY) this.offset.clone();
	}
}
