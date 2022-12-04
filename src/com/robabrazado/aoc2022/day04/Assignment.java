package com.robabrazado.aoc2022.day04;

public class Assignment {

	private int lower, upper;
	
	public Assignment(int lowerBound, int upperBound) {
		this.lower = lowerBound;
		this.upper = upperBound;
		return;
	}
	
	public boolean contains(Assignment other) {
		return (this.lower <= other.lower) && (this.upper >= other.upper);
	}
	
	public boolean overlaps(Assignment other) {
		return this.contains(other.lower) || this.contains(other.upper)
			|| other.contains(this.lower) || other.contains(this.upper);
	}
	
	private boolean contains(int bound) {
		return (bound >= this.lower) && (bound <= this.upper);
	}
	
	public static Assignment[] parseAssignmentPair(String s) {
		String[] bounds = s.split("[-,]");
		return new Assignment[] {
			new Assignment(Integer.parseInt(bounds[0]), Integer.parseInt(bounds[1])),
			new Assignment(Integer.parseInt(bounds[2]), Integer.parseInt(bounds[3]))
		};
	}
}
