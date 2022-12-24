package com.robabrazado.aoc2022.day24;

public enum Direction {
	EAST	(1, 0),
	SOUTH	(0, 1),
	WEST	(-1, 0),
	NORTH	(0, -1),
	WAIT	(0, 0);
	
	private final int xOffset;
	private final int yOffset;
	
	Direction(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		return;
	}
	
	public int xOffset() {
		return this.xOffset;
	}
	
	public int yOffset() {
		return this.yOffset;
	}
}
