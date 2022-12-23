package com.robabrazado.aoc2022.day22;

import java.util.Collections;
import java.util.NavigableSet;
import java.util.TreeSet;

public class Line {
	private final int low;
	private final int high;
	private final NavigableSet<Integer> walls;
	
	public Line(String inputLine) {
		int len = inputLine.length();
		int i = 0;
		
		// Record any leading spaces as no-tile columns
		while (i < len && inputLine.charAt(i) == PathBoard.SPACE_CHAR) {
			i++;
		}
		
		// Coordinates are +1 offset from array index
		this.low = i + 1;
		
		NavigableSet<Integer> tmpWalls = new TreeSet<Integer>();
		// Record walls
		while (i < len) {
			if (inputLine.charAt(i) == PathBoard.WALL_CHAR) {
				tmpWalls.add(i + 1);
			}
			i++;
		}
		this.walls = Collections.unmodifiableNavigableSet(tmpWalls);
		this.high = i; 
		return;
	}
	
	Line(int low, int high, NavigableSet<Integer> walls) {
		this.low = low;
		this.high = high;
		this.walls = Collections.unmodifiableNavigableSet(walls);
		return;
	}
	
	public int getNewPositionAsc(int start, int len) {
		if (this.walls.contains(start)) {
			throw new IllegalArgumentException("Start position " + Integer.toString(start) + " is in a wall");
		}
		int newPos = start + len;
		Integer nextWall = this.walls.higher(start);
		if (nextWall == null) {
			// No wall until edge
			if (newPos > this.high) {
				if (this.walls.contains(this.low)) {
					// Wrap would hit wall
					newPos = this.high;
				} else {
					// Wrap
					newPos = this.getNewPositionAsc(this.low, newPos - this.high - 1);
				}
			}
		} else {
			if (newPos >= nextWall) {
				// Hit a wall before move completed
				newPos = nextWall - 1;
			}
		}
		return newPos;
	}
	
	public int getNewPositionDesc(int start, int len) {
		if (this.walls.contains(start)) {
			throw new IllegalArgumentException("Start position " + Integer.toString(start) + " is in a wall");
		}
		int newPos = start - len;
		Integer nextWall = this.walls.lower(start);
		if (nextWall == null) {
			// No wall until edge
			if (newPos < this.low) {
				if (this.walls.contains(this.high)) {
					// Wrap would hit wall
					newPos = this.low;
				} else {
					// Wrap
					newPos = this.getNewPositionDesc(this.high, len - (start - this.low) - 1);  
				}
			}
		} else {
			if (newPos <= nextWall) {
				// Hit a wall before move completed
				newPos = nextWall + 1;
			}
		}
		return newPos;
	}
	
	public int getFirstOpenTile() {
		int firstOpen = this.low;
		while (this.walls.contains(firstOpen)) {
			firstOpen++;
		}
		return firstOpen;
	}
	
	@Override
	public String toString() {
		StringBuilder strb = new StringBuilder();
		strb.append("[").append(this.low).append(",").append(this.high).append("]");
		
		if (this.walls.size() > 0) {
			for (Integer i : this.walls) {
				strb.append(" ").append(i).append(",");
			}
			strb.deleteCharAt(strb.length() - 1);
		}
		return strb.toString();
	}
}
