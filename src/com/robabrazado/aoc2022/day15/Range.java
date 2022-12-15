package com.robabrazado.aoc2022.day15;

public class Range {
	private int low;
	private int high;
	
	// Both bounds inclusive; can be in any order
	public Range(int bound1, int bound2) {
		this.low = Integer.min(bound1, bound2);
		this.high = Integer.max(bound1, bound2);
		return;
	}
	
	public int low() {
		return this.low;
	}
	
	public int high() {
		return this.high;
	}
	
	public boolean contains(int point) {
		return point >= this.low && point <= this.high;
	}
	
	// True if argument is contained by this range. Contrast with hasContainment(Range)
	public boolean contains(Range other) {
		return this.low <= other.low && this.high >= other.high;
	}
	
	public boolean overlaps(Range other) {
		return !this.disjointedWith(other);
	}
	
	public boolean disjointedWith(Range other) {
		return this.low > other.high || this.high < other.low;
	}
	
	// True if this range contains argument OR argument contains this range. Contrast with contains(Range)
	public boolean hasContainment(Range other) {
		return this.contains(other) || other.contains(this);
	}
	
	public boolean adjacent(Range other) {
		return this.high == other.low - 1 || this.low - 1 == other.high;
	}
	
	public Range union(Range other) {
		if (this.disjointedWith(other) && !this.adjacent(other)) {
			throw new IllegalArgumentException("Ranges are disjointed or non-adjacent");
		}
		
		return new Range(Integer.min(this.low, other.low), Integer.max(this.high, other.high));
	}
	
	public int size() {
		return this.high - this.low + 1;
	}
	
	@Override
	public boolean equals(Object o) {
		boolean equal = false;
		
		if (o instanceof Range) {
			Range other = (Range) o;
			equal = this.low == other.low && this.high == other.high;
		}
		
		return equal;
	}
	
	@Override
	public int hashCode() {
		return Integer.valueOf(this.low).hashCode() ^ Integer.valueOf(this.high).hashCode();
	}
	
	@Override
	public String toString() {
		return "[" + Integer.toString(this.low) + "," + Integer.toString(this.high)+ "]";
	}
}
