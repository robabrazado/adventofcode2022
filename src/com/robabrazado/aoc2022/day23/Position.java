package com.robabrazado.aoc2022.day23;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Position {
	private int x;
	private int y;
	
	private Integer hashCodeCache = null;
	private Position[] neighborsCache = null;
	
	public Position(int x, int y) {
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
	
	/*
	 *      0 1 2
	 *      7 * 3
	 *      6 5 4
	 */
	public List<Position> getNeighbors() {
		if (this.neighborsCache == null) {
			this.neighborsCache = new Position[] {
				new Position(this.x - 1, this.y - 1),
				new Position(this.x, this.y - 1),
				new Position(this.x + 1, this.y - 1),
				new Position(this.x + 1, this.y),
				new Position(this.x + 1, this.y + 1),
				new Position(this.x, this.y + 1),
				new Position(this.x - 1, this.y + 1),
				new Position(this.x - 1, this.y)
			};
		}
		return Arrays.asList(this.neighborsCache);
	}
	
	public List<Position> getNeighbors(Direction dir) {
		List<Position> allNeighbors = this.getNeighbors();
		List<Position> retVal;
		switch (dir) {
		case NORTH:
			retVal = allNeighbors.subList(0, 3);
			break;
		case EAST:
			retVal = allNeighbors.subList(2, 5);
			break;
		case SOUTH:
			retVal = allNeighbors.subList(4, 7);
			break;
		case WEST:
			retVal = new ArrayList<Position>(allNeighbors.subList(6, 8));
			retVal.add(allNeighbors.get(0));
			break;
		default:
			throw new RuntimeException("Unrecognized direction: " + dir);
		}
		
		return retVal;
	}
	
	@Override
	public boolean equals(Object o) {
		boolean equal = false;
		
		if (o instanceof Position) {
			Position other = (Position) o;
			equal = this.x == other.x && this.y == other.y;
		}
		
		return equal;
	}
	
	@Override
	public int hashCode() {
		if (this.hashCodeCache == null) {
			this.hashCodeCache = Integer.valueOf(this.x ^ this.y);
		}
		return this.hashCodeCache.intValue();
	}
	
	@Override
	public String toString() {
		return "(" + Integer.toString(this.x) + "," + Integer.toString(this.y) + ")";  
	}
}
