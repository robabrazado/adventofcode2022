package com.robabrazado.aoc2022.day22;

import java.util.NavigableSet;
import java.util.TreeSet;

public class LineBuilder {
	private final int low;
	private Integer high;
	private final NavigableSet<Integer> walls = new TreeSet<Integer>();
	
	public LineBuilder(int low) {
		this.low = low;
	}
	
	public void setHigh(int high) {
		this.high = Integer.valueOf(high);
		return;
	}
	
	public boolean hasHigh() {
		return this.high != null;
	}
	
	public void addWall(int cellNum) {
		this.walls.add(cellNum);
		return;
	}
	
	public Line toLine() {
		if (high == null) {
			throw new IllegalStateException("Line has not been completed; no high bound set");
		}
		return new Line(this.low, this.high.intValue(), this.walls);
	}
}
