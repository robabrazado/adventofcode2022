package com.robabrazado.aoc2022.day14;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class BrokenRow {
	private SortedSet<Range> ranges; // In order of low bound
	
	public BrokenRow() {
		this.ranges = new TreeSet<Range>((Range a, Range b) -> {
			int result = a.low() - b.low();
			
			if (result == 0) {
				result = a.high() - b.high();
			}
			
			return result;
		});
		return;
	}
	
	// Any order ok
	public void addRange(int bound1, int bound2) {
		this.addRange(new Range(bound1, bound2));
	}
	
	public void addPoint(int point) {
		this.addRange(new Range(point, point));
	}
	
	public void addRange(Range newRange) {
		if (this.ranges.isEmpty()) {
			this.ranges.add(newRange);
		} else {
			// Find all existing ranges that overlap with the new range
			List<Range> overlaps = new ArrayList<Range>();
			for (Range r : this.ranges) {
				if (r.overlaps(newRange)) {
					overlaps.add(r);
				}
			}
			// Remove them from the set
			this.ranges.removeAll(overlaps);
			// Union them all
			for (Range r : overlaps) {
				newRange = newRange.union(r);
			}
			// Add new uberrange back to set
			this.ranges.add(newRange);
		}
		return;
	}
	
	public boolean contains(int point) {
		boolean matched = false;
		Iterator<Range> it = this.ranges.iterator();
		while (!matched && it.hasNext()) {
			matched = it.next().contains(point);
		}
		return matched;
	}
}
