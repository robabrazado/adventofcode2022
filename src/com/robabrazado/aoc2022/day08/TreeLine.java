package com.robabrazado.aoc2022.day08;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TreeLine {
	private List<Integer> line;
	
	public TreeLine() {
		this.line = new ArrayList<Integer>();
		return;
	}
	
	public TreeLine(int[] heights) {
		this.line = new ArrayList<Integer>(heights.length);
		for (int height : heights) {
			this.line.add(Integer.valueOf(height));
		}
		return;
	}
	
	public void addHeight(int height) {
		this.line.add(Integer.valueOf(height));
		return;
	}
	
	public int getHeightAt(int index) {
		return this.line.get(index).intValue();
	}
	
	public int getTreeCount() {
		return this.line.size();
	}
	
	public boolean isVisibleInLine(int index) {
		boolean visible = false;
		int len = this.line.size();
		
		if (index == 0 || index == len - 1) {
			// Shortcut for edge trees
			visible = true;
		} else {
			// Index guaranteed to be non-edge
			Integer myHeight = this.line.get(index);
			visible = (Collections.max(this.line.subList(0, index)).compareTo(myHeight) < 0)
				|| (Collections.max(this.line.subList(index + 1, len)).compareTo(myHeight) < 0);
		}
		
		return visible;
	}
	
	public int getViewingDistanceProduct(int index) {
		return this.getViewingDistanceUp(index) * this.getViewingDistanceDown(index);
	}
	
	private int getViewingDistanceUp(int index) {
		return getViewingDistanceRemaining(this.line.get(index), this.line.subList(index + 1, this.line.size()));
	}
	
	private int getViewingDistanceDown(int index) {
		List<Integer> remaining = new ArrayList<Integer>();
		remaining.addAll(this.line.subList(0, index));
		Collections.reverse(remaining);
		return getViewingDistanceRemaining(this.line.get(index), remaining);
	}
	
	// restOfLine is heights ordered by distance from origin (i.e., order may be reverse of original line order)
	private int getViewingDistanceRemaining(Integer myHeight, List<Integer> restOfLine) {
		int maxDistance = restOfLine.size();
		boolean viewBlocked = false;
		int distanceLooked = 0;
		
		for (int i = 0; i < maxDistance && !viewBlocked; i++) {
			distanceLooked++;
			viewBlocked = restOfLine.get(i).compareTo(myHeight) >= 0;
		}
		
		return distanceLooked;
	}
}
